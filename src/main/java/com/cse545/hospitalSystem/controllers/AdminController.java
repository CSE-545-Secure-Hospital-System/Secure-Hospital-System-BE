package com.cse545.hospitalSystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.services.AuthenticationService;
import com.cse545.hospitalSystem.services.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
//    @Autowired
//    AuthenticationService authenticationService;
	
	@Autowired
	private UserService userService;
    
	@PreAuthorize("hasRole('PATIENT')")
	@RequestMapping(value="/getAllUsers", method = RequestMethod.GET)
	public List<User> getAllUsers(){
		return userService.getAllUser();
	}

}
