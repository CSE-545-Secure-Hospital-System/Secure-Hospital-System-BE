package com.cse545.hospitalSystem.controllers;

import java.util.List;
import java.util.Set;

/*
 * 
 * General APIs goes here.
 * keep preAuthorize based on requirements.
 * 
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.PolicyClaim;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.UserReq;
import com.cse545.hospitalSystem.services.AuthenticationService;
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

	@CrossOrigin
	@RequestMapping(value="/getUserById", method = RequestMethod.GET)
//	@PreAuthorize("hasRole('ADMIN')")
	public User getUserById(@RequestParam(name = "userId") Long userId) {
		return userService.getUserById(userId);
	}
	
	@CrossOrigin
	@RequestMapping(value="/getUserByEmailId", method = RequestMethod.GET)
	public ResponseEntity<User> getUserByEmailId(@RequestParam(name = "emailId") String emailId) {
		return userService.getUserByEmailId(emailId);
	}
	
	@CrossOrigin
	@PostMapping(value="/updateUserByEmailId")
	public ResponseEntity<String> updateUserByEmailId(@RequestBody UserReq user){
		return userService.updateUserByEmailId(user);
	}
	
	@CrossOrigin
	@PostMapping(value = "/blockAccountByEmailId")
	public ResponseEntity<String> deleteuser(@RequestParam(name = "emailId") String emailId){
		return userService.deleteUser(emailId);
	}
	
	@CrossOrigin
	@PostMapping(value = "/activateAccountByEmailId")
	public ResponseEntity<String> activiteAccount(@RequestParam(name = "emailId") String emailId){
		return userService.activateAccount(emailId);
	}
	
//	@CrossOrigin
//	@RequestMapping(value="/getAllUsersBysearchTerm", method = RequestMethod.GET)
//	public ResponseEntity<Set<User>> getAllUsers(@RequestParam(required = false) String searchTerm){
//		return userService.getAllUser(searchTerm);
//	}
//	
	@CrossOrigin
	@RequestMapping(value="/getAllUsersByRole", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsersByRole(@RequestParam(required = false) String role, @RequestParam(required = false) String searchTerm){
		return userService.getAllUserByRole(role, searchTerm);
	}
	
	@CrossOrigin
	@PostMapping(value="/createClaim")
	public ResponseEntity<String> createClaim(@RequestBody PolicyClaim policyClaim){
		return userService.createClaim(policyClaim);
	}

	 
}
