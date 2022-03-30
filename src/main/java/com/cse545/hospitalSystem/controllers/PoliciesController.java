package com.cse545.hospitalSystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.Insurance_Policies;
import com.cse545.hospitalSystem.models.ReqAndResp.Policy;
import com.cse545.hospitalSystem.services.InsuranceService;

@RestController
@RequestMapping("/api/insurance")
public class PoliciesController {
	
	@Autowired
	private InsuranceService insuranceService;
	
	@CrossOrigin
	@PostMapping("/createPolicy")
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
	public ResponseEntity<List<Insurance_Policies>> getAllPolicies(){
		return insuranceService.getAllPolicies();
	}
	
	

}