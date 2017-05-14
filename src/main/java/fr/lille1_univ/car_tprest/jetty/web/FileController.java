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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.lille1_univ.car_tprest.jetty.model.Credentials;
import fr.lille1_univ.car_tprest.jetty.model.Message;
import fr.lille1_univ.car_tprest.jetty.model.SandboxObject;
import fr.lille1_univ.car_tprest.jetty.model.User;
import fr.lille1_univ.car_tprest.jetty.model.json.JSONRenderableMessage;
import fr.lille1_univ.car_tprest.jetty.service.AuthenticationService;
import fr.lille1_univ.car_tprest.jetty.service.HelloWorldService;
import fr.lille1_univ.car_tprest.jetty.service.SandboxService;
import fr.lille1_univ.car_tprest.jetty.utils.Logger;

@Controller
public class FileController {
	
	Logger l = new Logger("FileController");
	@Autowired
	private HelloWorldService helloWorldService;
	@Autowired
	private SandboxService sandboxService;
	
	@Autowired
	private AuthenticationService authenticationService;

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
		return this.authenticationService.authenticate(Credentials.getFromJsonString(credentials));
	}
	
	@RequestMapping(
			method = {RequestMethod.POST},
			headers = {"Content-type=application/json"},
			value="/api/sandbox", 
			produces="application/json")
	public @ResponseBody String sandbox(@RequestBody String str) {
		return new JSONRenderableMessage(new Message(1, str)).renderJSON();
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/sandbox", produces="application/json")
	public ResponseEntity<User> testVar(@RequestParam(value="varname") String name) {
		return new ResponseEntity<User>(new User("1", "2", "3", true), HttpStatus.OK);
	}
}
