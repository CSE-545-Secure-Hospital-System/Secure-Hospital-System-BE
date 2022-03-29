package com.cse545.hospitalSystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.Diagnosis;
import com.cse545.hospitalSystem.services.DiagnosisService;

@RestController
@RequestMapping("/api/diagnosis")
public class DiagnosisController {
	
//	@Autowired
//	private DiagnosisService diagnosisService;
//
//	@CrossOrigin
//	@PostMapping("/createDiagnosis")
//	public ResponseEntity<String> createDiagnosis(@RequestBody Diagnosis diagnosis){
////		return diagnosisService.createDiagnosis(diagnosis);
//		return null;
//	}
//	
//	@CrossOrigin
//	@GetMapping("/getDiagnosisByUserId")
//	public ResponseEntity<List<Diagnosis>> getDiagnosisByUserId(@RequestParam long patientId){
////		return diagnosisService.getDiagnosesById(patientId);
//		return null;
//	}
}
