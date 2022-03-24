package com.cse545.hospitalSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cse545.hospitalSystem.models.PatientRecord;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.PatientRecordRequestDTO;
import com.cse545.hospitalSystem.services.PatientRecordService;

@Controller
@RequestMapping(value = "api/patientRecord")
public class PatientRecordController {
    
    @Autowired
    private PatientRecordService patientRecordService;

    @RequestMapping(value="/createRecord", method = RequestMethod.POST)
    @PreAuthorize("hasRole('HOSPITAL_STAFF')")
    public ResponseEntity<PatientRecord> createPatientRecord(PatientRecordRequestDTO patientRecordRequest) {
        PatientRecord patientRecord = patientRecordService.createPatientRecord(patientRecordRequest);
        if(patientRecord == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(patientRecord);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(patientRecord);
    }
    
    @RequestMapping(value="/getRecord/{patientId}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('HOSPITAL_STAFF')")
    public ResponseEntity<PatientRecord> getPatientRecord(@PathVariable long patientId) {
        PatientRecord patientRecord = patientRecordService.getPatientRecord(patientId);
        if(patientRecord == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(patientRecord);
        }
        return ResponseEntity.status(HttpStatus.OK)
        .body(patientRecord); 
    }
    
    @RequestMapping(value="/updateRecord/{patientId}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('HOSPITAL_STAFF')")
    public ResponseEntity<PatientRecord> updatePatientRecord(@PathVariable long patientId, PatientRecordRequestDTO patientRecordRequest) {
        PatientRecord patientRecord = patientRecordService.updatePatientRecord(patientId, patientRecordRequest);
        if(patientRecord == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(patientRecord);
        }
        return ResponseEntity.status(HttpStatus.OK)
        .body(patientRecord); 
    }
    
}
