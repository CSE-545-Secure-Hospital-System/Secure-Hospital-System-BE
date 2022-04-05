package com.cse545.hospitalSystem.controllers;

import java.util.List;

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
import com.cse545.hospitalSystem.models.LabResult;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.LabReport;
import com.cse545.hospitalSystem.models.ReqAndResp.LabReportsResponse;
import com.cse545.hospitalSystem.models.ReqAndResp.UpdateLabTestRequest;
import com.cse545.hospitalSystem.services.LabResultsService;
import com.cse545.hospitalSystem.services.RoleService;
import com.cse545.hospitalSystem.services.UserService;

@RestController
@RequestMapping("/api/labResults")
public class LabResultsController {
	
	
	@Autowired
	private LabResultsService labResultsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@CrossOrigin
	@GetMapping("/getAllLabReportsByPatientId")
    @PreAuthorize("hasAnyRole('PATIENT', 'LAB_STAFF', 'DOCTOR', 'INSURANCE_STAFF', 'ADMIN', 'HOSPITAL_STAFF')")
	public ResponseEntity<LabReportsResponse> getAllLabReportsByPatientId(@RequestParam long patientId,
	        Authentication authentication){
	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        ResponseEntity<User> resp = userService.getUserByEmailId(userEmail);
        User user =resp.getBody();
        if(roleService.findUserRole(user, RoleMapping.PATIENT)) {
            if(user.getId()!=patientId) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
		return labResultsService.getAllLabReportsByPatientId(patientId);
	}
	
	@CrossOrigin
	@GetMapping("/getAllLabReports")
    @PreAuthorize("hasAnyRole('LAB_STAFF', 'ADMIN', 'DOCTOR')")
	public ResponseEntity<LabReportsResponse> getAllLabReportsByPatientId(){
		return labResultsService.getAllLabReports();
	}
	
	@CrossOrigin
	@PostMapping("/updateLabReportByPatientId")
	@PreAuthorize("hasRole('LAB_STAFF', 'ADMIN')")
	public ResponseEntity<String> updateLabReportByPatientId(@RequestBody UpdateLabTestRequest updateLabTestRequest){
		return labResultsService.updateLabReport(updateLabTestRequest);
	}

}
