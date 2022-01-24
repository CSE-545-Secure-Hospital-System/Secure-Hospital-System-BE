package com.cse545.hospitalSystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.handler.UserApi;
import com.cse545.hospitalSystem.model.User;


@RestController
@RequestMapping("/api")
public class UsersController implements UserApi{
	
	
	@Override
	public ResponseEntity<User> users() {
		User user = new User();
		user.setEmailId("Test");
		user.setFirstName("Test");
		user.setLastName("Test");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	

}
