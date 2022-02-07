package com.cse545.hospitalSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.config.LoggerConfig;
import com.cse545.hospitalSystem.handler.UserApi;
import com.cse545.hospitalSystem.model.User;
import com.cse545.hospitalSystem.model.UserJwt;
import com.cse545.hospitalSystem.services.AuthenticationService;



@RestController
@RequestMapping("/api")
public class UsersController extends LoggerConfig implements UserApi{
	
	// never add logger here
	// this added just for testing
//	Logger logger = LoggerFactory.getLogger(UsersController.class);
	// default application level logger is info  
	
	@Autowired
	private AuthenticationService authenticationService;
	
	
	@Override
	public ResponseEntity<UserJwt> signUp(User user) {
		return authenticationService.signUp(user);
	}
	
	

}
