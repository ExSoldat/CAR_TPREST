/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.lille1_univ.car_tprest.jetty.web;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.http.HttpTester.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import fr.lille1_univ.car_tprest.jetty.model.UpweeFile;
import fr.lille1_univ.car_tprest.jetty.model.UpweeMessage;
import fr.lille1_univ.car_tprest.jetty.model.UpweeRegisteringCredentials;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableMessage;
import fr.lille1_univ.car_tprest.jetty.model.parameters.UpweeCredentials;
import fr.lille1_univ.car_tprest.jetty.model.parameters.UpweeMoveParams;
import fr.lille1_univ.car_tprest.jetty.service.AuthenticationService;
import fr.lille1_univ.car_tprest.jetty.service.FileSystemService;
import fr.lille1_univ.car_tprest.jetty.utils.Logger;

	/**
	* The controller of the server
	*/
@Controller
public class UpweeController {
	Logger l = new Logger("UpweeController");
	
	@Autowired
	private AuthenticationService authenticationService; //manage authentication
	
	@Autowired
	private FileSystemService fileSystemService; //manage files

	/**
	* A call that allows users to authenticate
	*/
	@CrossOrigin
	@RequestMapping(method = {RequestMethod.POST},
			headers = {"Content-type=application/json"},
			value="/api/authenticate", 
			produces="application/json")
	public @ResponseBody String authenticate(
			@RequestBody String credentials) {
		return this.authenticationService.authenticate(UpweeCredentials.getFromJsonString(credentials));
	}
	
	/**
	* A call that is used to register a new user
	*/
	@CrossOrigin
	@RequestMapping(method = {RequestMethod.POST},
			headers = {"Content-type=application/json"},
			value="/api/register", 
			produces="application/json")
	public @ResponseBody String register(
			@RequestBody String credentials) {
		return this.authenticationService.register(UpweeRegisteringCredentials.getFromJsonString(credentials));
	}
	
	/**
	* A call that is used to render the file list of the corresponding path
	*/
	@CrossOrigin
	@RequestMapping(method = {RequestMethod.GET},
			value="/api/files/**", 
			produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getList(HttpServletRequest request, @RequestParam(value="file", required = false, defaultValue = "") String filename) {
		String decodedfileName = "";
		try {
			decodedfileName = java.net.URLDecoder.decode(filename, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			decodedfileName = filename;
			e.printStackTrace();
		}
		return this.fileSystemService.getListFromPath(getFullFileRequestPath("/api/files/**", request, decodedfileName));
	}
	
	
	/**
	* A call that is used to download the file in the pat corresponding to the filename
	*/
	@CrossOrigin
	@RequestMapping(method = {RequestMethod.GET},
			value="/api/files/download/**", 
			produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void download(HttpServletRequest request, @RequestParam(value="file", required = true) String filename, HttpServletResponse response) {
		try {
			response.addHeader("Content-disposition", "attachment;filename=" + filename);
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			String decodedfileName = "";
			decodedfileName = java.net.URLDecoder.decode(filename, "UTF-8");
			InputStream stream = this.fileSystemService.getFile(getFullFileRequestPath("/api/files/download/**", request, decodedfileName));
			IOUtils.copy(stream, response.getOutputStream());
			response.flushBuffer();
			stream.close();
			
		} catch (Exception e) {
			l.e("Unable to get file");
			e.printStackTrace();
		}
	}
	
	/**
	* A call that is used to render the object with all his props
	*/
	@CrossOrigin
	@RequestMapping(method = {RequestMethod.DELETE},
			value="/api/files/delete/**", 
			produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String delete(HttpServletRequest request, @RequestParam(value="file", required = true, defaultValue="") String filename) {
		String decodedfileName = "";
		try {
			decodedfileName = java.net.URLDecoder.decode(filename, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			decodedfileName = filename;
			e.printStackTrace();
		}
		return this.fileSystemService.deleteFromPath(getFullFileRequestPath("/api/files/delete/**", request, decodedfileName));
	}
	
	/**
	* A call that is used to upload a new file to the server
	*/
	@CrossOrigin
	@RequestMapping(method = {RequestMethod.POST},
			value="/api/files/upload/**", 
			produces="application/json")
	public @ResponseBody String upload(
			 HttpServletRequest request,
			 @RequestParam("file") MultipartFile file) {
		try {
			return this.fileSystemService.uploadSingleFile(file, getFullFileRequestPath("/api/files/upload/**", request, ""));
		} catch (IllegalStateException e) { 
			return new JSONRenderableMessage(UpweeMessage.TOO_LARGE_FILE).renderJSON();
		}
	}
	
	/**
	* A call that is used to move a file to another path
	*/
	@CrossOrigin
	@RequestMapping(method = {RequestMethod.POST},
			headers = {"Content-type=application/json"},
			value="/api/files/move/**", 
			produces="application/json")
	public @ResponseBody String move(
			@RequestBody String parameters, HttpServletRequest request) {
		return this.fileSystemService.move(UpweeMoveParams.getFromJsonString(parameters), getFullFileRequestPath("/api/files/create/**", request, ""));
	}
	
	/**
	* A call that is used to create a new file
	*/
	@CrossOrigin
	@RequestMapping(method = {RequestMethod.POST},
			value="/api/files/create/**", 
			produces="application/json")
	public @ResponseBody String create(
			 HttpServletRequest request) throws Exception {
		return this.fileSystemService.createFolder(getFullFileRequestPath("/api/files/create/**", request, ""));
	}
	
	/**
	 * Helper functions
	 */
	/**
	* An helper function that is used to render the path from a request
	*/
	public String getFullFileRequestPath(String pattern, HttpServletRequest request, String filename) {
		String tail = new AntPathMatcher().extractPathWithinPattern(pattern, request.getRequestURI());
		return tail+="/" + filename;
	}
}
