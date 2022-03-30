package com.cse545.hospitalSystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.Diagnosis;
import com.cse545.hospitalSystem.models.ReqAndResp.DiagnosisCreateRequestDTO;
import com.cse545.hospitalSystem.services.DiagnosisService;

@RestController
@RequestMapping("/api/diagnosis")
public class DiagnosisController {
	
	@Autowired
	private DiagnosisService diagnosisService;

	@CrossOrigin
	@PostMapping("/createDiagnosis")
//	@PreAuthorize("hasRole('DOCTOR')")
	public ResponseEntity<String> createDiagnosis(@RequestBody DiagnosisCreateRequestDTO diagnosis){
		return diagnosisService.createDiagnosis(diagnosis);
	}
	
	@CrossOrigin
	@GetMapping("/getDiagnosisByUserId")
	public ResponseEntity<List<Diagnosis>> getDiagnosisByUserId(@RequestParam long patientId){
		return diagnosisService.getAllDiagnosisById(patientId);
	}
}
