package com.cse545.hospitalSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.services.AuthenticationService;
import com.cse545.hospitalSystem.services.UserService;



@RestController
@RequestMapping("/api/users")
public class UsersController{
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/getUserById", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public User getUserById(@RequestParam(name = "userId") Long userId) {
		return userService.getUserById(userId);
	}
	

}
