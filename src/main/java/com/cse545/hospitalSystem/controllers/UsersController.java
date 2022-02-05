package com.cse545.hospitalSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.services.AuthenticationService;



@RestController
@RequestMapping("/api/users")
public class UsersController{
	
	// never add logger here
	// this added just for testing
//	Logger logger = LoggerFactory.getLogger(UsersController.class);
	// default application level logger is info  
	

	
	

}
