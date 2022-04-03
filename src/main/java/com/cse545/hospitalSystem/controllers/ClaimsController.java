package com.cse545.hospitalSystem.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.PolicyClaim;
import com.cse545.hospitalSystem.models.ReqAndResp.ClaimCreateRequestDTO;
import com.cse545.hospitalSystem.models.ReqAndResp.PolicyClaimsRespDTO;
import com.cse545.hospitalSystem.services.ClaimService;

@RequestMapping("/api/claims")
@RestController
public class ClaimsController {
	
	@Autowired
	private ClaimService claimService;
	
	@CrossOrigin
	@PostMapping("/createClaim")
	private ResponseEntity<String> createClaim(@RequestBody ClaimCreateRequestDTO claimCreateRequestDTO){
		return claimService.createClaim(claimCreateRequestDTO);
	}
	
	@CrossOrigin
	@GetMapping("/getAllClaims")
	private ResponseEntity<List<PolicyClaimsRespDTO>> getAllClaims(){
		return claimService.getAllClaims();
	}

	
	@CrossOrigin
	@GetMapping("/approveClaim")
	private ResponseEntity<String> approveClaim(@RequestParam Long claimId){
		return claimService.approveClaim(claimId);
	}
	
	@CrossOrigin
	@GetMapping("/rejectClaim")
	private ResponseEntity<String> rejectClaim(@RequestParam Long claimId){
		return claimService.rejectClaim(claimId);
	}
}
