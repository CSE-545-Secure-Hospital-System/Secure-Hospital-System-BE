package com.cse545.hospitalSystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.LabTest;
import com.cse545.hospitalSystem.repositories.labTestRepository;

@RestController
@RequestMapping("/api/lab")
public class LabTestController {
	
	@Autowired
	private labTestRepository labTestRepo;
	
	@CrossOrigin
	@PostMapping("/createLabTest")
	private ResponseEntity<String> createLabTest(@RequestBody LabTest labTest){
		labTestRepo.save(labTest);
		return ResponseEntity.ok("Sucess");
	}
	
	
	@CrossOrigin
	@GetMapping("/getAllLabTests")
	private ResponseEntity<List<LabTest>> getAllLabTest(){
		return new ResponseEntity<List<LabTest>>(labTestRepo.findAll(), HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/getLabTestByName")
	private ResponseEntity<LabTest> getLabTestByName(@RequestParam String labTestName){
		LabTest p = labTestRepo.findByLabTestName(labTestName);
		return new ResponseEntity<LabTest>(p, HttpStatus.OK);
	}
	
	

}
