package com.cse545.hospitalSystem.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.config.LoggerConfig;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.RespAndReq.LoginRequestDTO;
import com.cse545.hospitalSystem.services.AuthService;



@RestController
@RequestMapping("/api/auth/")
public class UsersController extends LoggerConfig {
	
	// never add logger here
	// this added just for testing
//	Logger logger = LoggerFactory.getLogger(UsersController.class);
	// default application level logger is info  
	

	@Autowired
	AuthService authService;
	
	@RequestMapping(value="/signUp", method = RequestMethod.POST)
	public ResponseEntity<?> signUp(@RequestBody User user) {
		return authService.signUp(user);
	}
	
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		return authService.login(loginRequestDTO);
	}
	

}
