package com.cse545.hospitalSystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.LabTest;
import com.cse545.hospitalSystem.repositories.LabTestRepository;

@RestController
@RequestMapping("/api/lab")
public class LabTestController {
	
	
	
	@Autowired
	private LabTestRepository labTestRepo;
	
	@CrossOrigin
	@PostMapping("/createLabTest")
	@PreAuthorize("hasAnyRole('LAB_STAFF', 'ADMIN')")
	public ResponseEntity<String> createLabTest(@RequestBody LabTest labTest){
		try {
			labTestRepo.save(labTest);
		} catch(Exception e) {
			return ResponseEntity.ok("Cannot save! Some issue in saving.");
		}
		return ResponseEntity.ok("Lab Test successfully recorded!");
	}
	
	
	@CrossOrigin
	@GetMapping("/getAllLabTests")
    @PreAuthorize("hasAnyRole('LAB_STAFF', 'ADMIN', 'DOCTOR')")
	public ResponseEntity<List<LabTest>> getAllLabTest(){
		return new ResponseEntity<List<LabTest>>(labTestRepo.findAll(), HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/getLabTestByName")
	private ResponseEntity<LabTest> getLabTestByName(@RequestParam String labTestName){
		LabTest p = labTestRepo.findByLabTestName(labTestName);
		return new ResponseEntity<LabTest>(p, HttpStatus.OK);
	}
	
	

}
