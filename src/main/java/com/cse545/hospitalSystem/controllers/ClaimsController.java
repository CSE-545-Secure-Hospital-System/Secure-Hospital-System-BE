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
import com.cse545.hospitalSystem.models.PolicyClaim;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.ClaimCreateRequestDTO;
import com.cse545.hospitalSystem.models.ReqAndResp.PolicyClaimsRespDTO;
import com.cse545.hospitalSystem.services.ClaimService;
import com.cse545.hospitalSystem.services.RoleService;
import com.cse545.hospitalSystem.services.UserService;

@RequestMapping("/api/claims")
@RestController
public class ClaimsController {
	
	@Autowired
	private ClaimService claimService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@CrossOrigin
	@PostMapping("/createClaim")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<String> createClaim(@RequestBody ClaimCreateRequestDTO claimCreateRequestDTO,
	        Authentication authentication){
	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        ResponseEntity<User> resp = userService.getUserByEmailId(userEmail);
        User user =resp.getBody();
	    if(!checkIfPatientAndValidId(user, claimCreateRequestDTO.getPatientId())){
	        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	    }
		return claimService.createClaim(claimCreateRequestDTO);
	}
	
	@CrossOrigin
	@GetMapping("/getAllClaims")
	@PreAuthorize("hasAnyRole('ADMIN', 'INSURANCE_STAFF')")
	public ResponseEntity<List<PolicyClaimsRespDTO>> getAllClaims(){
		return claimService.getAllClaims();
	}

	
	@CrossOrigin
	@GetMapping("/approveClaim")
	@PreAuthorize("hasAnyRole('ADMIN', 'INSURANCE_STAFF')")
	public ResponseEntity<String> approveClaim(@RequestParam Long claimId){
		return claimService.approveClaim(claimId);
	}
	
	@CrossOrigin
	@GetMapping("/rejectClaim")
	@PreAuthorize("hasAnyRole('ADMIN', 'INSURANCE_STAFF')")
	public ResponseEntity<String> rejectClaim(@RequestParam Long claimId){
		return claimService.rejectClaim(claimId);
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
