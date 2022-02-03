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
import com.cse545.hospitalSystem.services.UserService;



@RestController
@RequestMapping("/api/users")
public class UsersController{
	
	// never add logger here
	// this added just for testing
//	Logger logger = LoggerFactory.getLogger(UsersController.class);
	// default application level logger is info  
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping(value = "/create")
	public ResponseEntity<Long> create(@RequestBody User user){
	    User createdUser = userService.createUser(user);
	    return new ResponseEntity<Long> (createdUser.getId(), HttpStatus.OK);
	    
	}
	
	@PostMapping(value = "/sign-in")
    public ResponseEntity<Long> signIn(@RequestBody User user){
	    User signedInUser = userService.signIn(user);
	    return new ResponseEntity<Long> (signedInUser.getId(), HttpStatus.OK); 
    }
	
	

}
