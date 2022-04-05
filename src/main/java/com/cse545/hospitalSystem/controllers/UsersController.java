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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.enums.RoleMapping;
import com.cse545.hospitalSystem.models.PolicyClaim;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.UserReq;
import com.cse545.hospitalSystem.services.AuthenticationService;
import com.cse545.hospitalSystem.services.RoleService;
import com.cse545.hospitalSystem.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UsersController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	// never add logger here
	// this added just for testing
//	Logger logger = LoggerFactory.getLogger(UsersController.class);
	// default application level logger is info  

	@CrossOrigin
	@RequestMapping(value="/getUserById", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('PATIENT', 'ADMIN', 'LAB_STAFF', 'DOCTOR', 'INSURANCE_STAFF', 'HOSPITAL_STAFF')")
	public ResponseEntity<User> getUserById(@RequestParam(name = "userId") Long userId, Authentication authentication) {
	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	    String userEmail = userDetails.getUsername();
	    ResponseEntity<User> resp = userService.getUserByEmailId(userEmail);
	    User user =resp.getBody();
	    if(!checkIfPatientAndValidId(user, userId)) {
	        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	    }
	    
		return userService.getUserById(userId);
	}
	
	@CrossOrigin
	@RequestMapping(value="/getUserByEmailId", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('PATIENT', 'LAB_STAFF', 'ADMIN', 'INSURANCE_STAFF', 'DOCTOR', 'HOSPITAL_STAFF')")
	public ResponseEntity<User> getUserByEmailId(@RequestParam(name = "emailId") String emailId, Authentication authentication) {
	    ResponseEntity<User> resp = userService.getUserByEmailId(emailId);
	    User user =resp.getBody();
	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
	    if(!checkIfPatientAndValidEmail(user, userEmail)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return resp;
	}
	
	@CrossOrigin
	@PostMapping(value="/updateUserByEmailId")
	@PreAuthorize("hasAnyRole('PATIENT', 'LAB_STAFF', 'ADMIN', 'INSURANCE_STAFF', 'DOCTOR', 'HOSPITAL_STAFF')")
	public ResponseEntity<String> updateUserByEmailId(@RequestBody UserReq user, Authentication authentication){
	    ResponseEntity<User> resp = userService.getUserByEmailId(user.getEmail());
        User userResp =resp.getBody();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        if(!checkIfPatientAndValidEmail(userResp, userEmail)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return userService.updateUserByEmailId(user);
	}
	
	@CrossOrigin
	@PostMapping(value = "/blockAccountByEmailId")
	@PreAuthorize("hasAnyRole('ADMIN', 'HOSPITAL_STAFF')")
	public ResponseEntity<String> deleteuser(@RequestParam(name = "emailId") String emailId){
		return userService.deleteUser(emailId);
	}
	
	@CrossOrigin
	@PostMapping(value = "/activateAccountByEmailId")
	@PreAuthorize("hasAnyRole('ADMIN', 'HOSPITAL_STAFF')")
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
	@PreAuthorize("hasAnyRole('ADMIN', 'PATIENT','LAB_STAFF', 'INSURANCE_STAFF', 'HOSPITAL_STAFF', 'DOCTOR')")
	public ResponseEntity<List<User>> getAllUsersByRole(@RequestParam(required = false) String role, @RequestParam(required = false) String searchTerm){
		return userService.getAllUserByRole(role, searchTerm);
	}
	
	@CrossOrigin
	@PostMapping(value="/createClaim")
	public ResponseEntity<String> createClaim(@RequestBody PolicyClaim policyClaim){
		return userService.createClaim(policyClaim);
	}
	
	private boolean checkIfPatientAndValidEmail(User user, String email) {
	    if(roleService.findUserRole(user, RoleMapping.PATIENT)) {
            if(user.getEmail().equals(email)) {
                return false;
            }
        }
	    return true;
	}
	
	private boolean checkIfPatientAndValidId(User user, long id) {
        if(roleService.findUserRole(user, RoleMapping.PATIENT)) {
            if(user.getId() ==  id) {
                return false;
            }
        }
        return true;
    }

	 
}
