package com.cse545.hospitalSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cse545.hospitalSystem.models.PatientRecord;
import com.cse545.hospitalSystem.models.ReqAndResp.PatientRecordRequestDTO;
import com.cse545.hospitalSystem.services.PatientRecordService;

@Controller
@RequestMapping(value = "api/patientRecord")
public class PatientRecordController {
    
    @Autowired
    private PatientRecordService patientRecordService;

    @RequestMapping(value="/createRecord", method = RequestMethod.POST)
    @PreAuthorize("hasRole('STAFF')")
    public PatientRecord createPatientRecord(PatientRecordRequestDTO patientRecordRequest) {
        PatientRecord patientRecord = patientRecordService.createPatientRecord(patientRecordRequest);
        if(patientRecord == null) {
            //return notok
        }
        return patientRecord;
    }
    
}
