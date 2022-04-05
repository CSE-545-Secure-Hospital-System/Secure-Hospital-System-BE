package com.cse545.hospitalSystem.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.enums.RoleMapping;
import com.cse545.hospitalSystem.models.Insurance_Policies;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.Policy;
import com.cse545.hospitalSystem.services.InsuranceService;
import com.cse545.hospitalSystem.services.RoleService;
import com.cse545.hospitalSystem.services.UserService;

@RestController
@RequestMapping("/api/insurance")
public class PoliciesController {
	
	@Autowired
	private InsuranceService insuranceService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@CrossOrigin
	@PostMapping("/createPolicy")
	@PreAuthorize("hasAnyRole( 'INSURANCE_STAFF', 'ADMIN')")
	public ResponseEntity<String> createPolicy(@RequestBody Policy insurancePolicy){
		return insuranceService.createPolicy(insurancePolicy);
	}
	
	@CrossOrigin
	@GetMapping("/getPolicyTypes")
	public ResponseEntity<List<String>> getPolicyTypes(){
		return insuranceService.getPolicyTypes();
	}
	
	@CrossOrigin
	@GetMapping("/getAllPolicies")
	@PreAuthorize("hasAnyRole('PATIENT', 'INSURANCE_STAFF', 'ADMIN')")
	public ResponseEntity<List<Insurance_Policies>> getAllPolicies(){
		return insuranceService.getAllPolicies();
	}
	
	@CrossOrigin
	@GetMapping("/getAllPoliciesByuserId")
	public ResponseEntity<Set<Insurance_Policies>> getAllPoliciesByuserId(@RequestParam Long patientId,
	        Authentication authentication){
	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        ResponseEntity<User> resp = userService.getUserByEmailId(userEmail);
        User user =resp.getBody();
        if(roleService.findUserRole(user, RoleMapping.PATIENT)) {
            if(user.getId()!=patientId) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
		return insuranceService.getAllPoliciesByuserId(patientId);
	}
	
	
	@CrossOrigin
	@PostMapping("/addPolicyToUser")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<String> addPolicyToUser(@RequestParam long patientId, @RequestParam long policyId,
	        Authentication authentication){
	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        ResponseEntity<User> resp = userService.getUserByEmailId(userEmail);
        User user =resp.getBody();
        if(roleService.findUserRole(user, RoleMapping.PATIENT)) {
            if(user.getId()!=patientId) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
		return insuranceService.addPolicyToUser(patientId, policyId);
	}
	
	

}
