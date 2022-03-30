package com.cse545.hospitalSystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.LabResult;
import com.cse545.hospitalSystem.models.ReqAndResp.LabReport;
import com.cse545.hospitalSystem.models.ReqAndResp.LabReportsResponse;
import com.cse545.hospitalSystem.services.LabResultsService;

@RestController
@RequestMapping("/api/labResults")
public class LabResultsController {
	
	
	@Autowired
	private LabResultsService labResultsService;
	
	@CrossOrigin
	@GetMapping("/getAllLabReportsByPatientId")
	public ResponseEntity<LabReportsResponse> getAllLabReportsByPatientId(@RequestParam long patientId){
		return labResultsService.getAllLabReportsByPatientId(patientId);
	}

}
