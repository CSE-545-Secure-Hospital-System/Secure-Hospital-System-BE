package com.cse545.hospitalSystem.controllers;

/*
 * 
 * General APIs goes here.
 * keep preAuthorize based on requirements.
 * 
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.services.UserService;



@RestController
@RequestMapping("/api/users")
public class UsersController{
	

	@Autowired
	private UserService userService;
	// never add logger here
	// this added just for testing
//	Logger logger = LoggerFactory.getLogger(UsersController.class);
	// default application level logger is info  

	
	@RequestMapping(value="/getUserById", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public User getUserById(@RequestParam(name = "userId") Long userId) {
		return userService.getUserById(userId);
	}
	
	@RequestMapping(value="/getUserByEmailId", method = RequestMethod.GET)
	public ResponseEntity<User> getUserByEmailId(@RequestParam(name = "emailId") String emailId) {
		return userService.getUserByEmailId(emailId);
	}

}
