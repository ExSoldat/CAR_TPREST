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

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.lille1_univ.car_tprest.jetty.model.UpweeCredentials;
import fr.lille1_univ.car_tprest.jetty.model.UpweeMessage;
import fr.lille1_univ.car_tprest.jetty.model.SandboxObject;
import fr.lille1_univ.car_tprest.jetty.model.UpweeUser;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableMessage;
import fr.lille1_univ.car_tprest.jetty.service.AuthenticationService;
import fr.lille1_univ.car_tprest.jetty.service.HelloWorldService;
import fr.lille1_univ.car_tprest.jetty.service.FileSystemService;
import fr.lille1_univ.car_tprest.jetty.service.SandboxService;
import fr.lille1_univ.car_tprest.jetty.utils.Logger;

@Controller
public class UpweeController {
	
	Logger l = new Logger("FileController");
	@Autowired
	private HelloWorldService helloWorldService;
	@Autowired
	private SandboxService sandboxService;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private FileSystemService fileSystemService;

	@RequestMapping("/")
	@ResponseBody
	public String helloWorld() {
		return this.helloWorldService.getHelloMessage();
	}
	
	@RequestMapping(method = {RequestMethod.POST},
			headers = {"Content-type=application/json"},
			value="/api/authenticate", 
			produces="application/json")
	public @ResponseBody String authenticate(
			@RequestBody String credentials) {
		return this.authenticationService.authenticate(UpweeCredentials.getFromJsonString(credentials));
	}
	
	@RequestMapping(method = {RequestMethod.GET},
			value="/api/files/**", 
			produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getList(HttpServletRequest request, @RequestParam(value="file", required = false, defaultValue = "") String filename) {
		return this.fileSystemService.getListFromPath(getFullFileRequestPath("/api/files/**", request, filename));
	}
	
	//inspiration : 
	//http://stackoverflow.com/questions/5673260/downloading-a-file-from-spring-controllers
	@RequestMapping(method = {RequestMethod.GET},
			value="/api/files/download/**", 
			produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void download(HttpServletRequest request, @RequestParam(value="file", required = false, defaultValue = "") String filename, HttpServletResponse response) {
		try {
			response.addHeader("Content-disposition", "attachment;filename=" + filename);
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			InputStream stream = this.fileSystemService.getFile(getFullFileRequestPath("/api/files/download/**", request, filename));
			
			IOUtils.copy(stream, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			l.e("Unable to get file");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Helper functions
	 */
	
	public String getFullFileRequestPath(String pattern, HttpServletRequest request, String filename) {
		String tail = new AntPathMatcher().extractPathWithinPattern(pattern, request.getRequestURI());
		return tail+="/" + filename;
	}
}
