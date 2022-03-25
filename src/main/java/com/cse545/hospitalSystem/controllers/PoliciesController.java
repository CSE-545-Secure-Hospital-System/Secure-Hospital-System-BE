package com.cse545.hospitalSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.Insurance_Policiies;
import com.cse545.hospitalSystem.models.ReqAndResp.Policy;
import com.cse545.hospitalSystem.services.InsuranceService;

@RestController
@RequestMapping("/api/insurance")
public class PoliciesController {
	
	@Autowired
	private InsuranceService insuranceService;
	
	@PostMapping("/createPolicy")
	public ResponseEntity<String> createPolicy(@RequestBody Policy insurancePolicy){
		return insuranceService.createPolicy(insurancePolicy);
	}

}
