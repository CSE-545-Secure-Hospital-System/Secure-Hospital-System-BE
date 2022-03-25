package com.cse545.hospitalSystem.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.Coverage;
import com.cse545.hospitalSystem.repositories.CoveragesRepository;

@RestController
@RequestMapping("/api/coverages")
public class CoveragesController {

	@Autowired
	private CoveragesRepository coveragesRepository;
	
	@CrossOrigin
	//@PreAuthorize("hasAnyRole('ADMIN', 'INSURANCE_STAFF')")
	@PostMapping("/createCoverage")
	public ResponseEntity<String> createCoverage(@RequestBody Coverage coverage){
		try {
			Coverage c = new Coverage(coverage.getCoverageName(), coverage.getDescription());
			coveragesRepository.save(c);
			return ResponseEntity.ok("Coverage saved!");
		}catch (Exception e) {
			System.out.print("Something went wrong! please check: " + e.getMessage());
		}
		return new ResponseEntity<String>("Failed creating coverage!", HttpStatus.BAD_REQUEST);
	}
	
	@CrossOrigin
	//@PreAuthorize("hasAnyRole('ADMIN', 'INSURANCE_STAFF')")
	@GetMapping("/getCoverages")
	public ResponseEntity<List<Coverage>> getCoverages(){
		try {
			List<Coverage> coverages = coveragesRepository.findAll();
			return ResponseEntity.ok(coverages);
		}catch (Exception e) {
			System.out.print("Something went wrong! please check");
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}
}
