package com.cse545.hospitalSystem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.models.PatientRecord;
import com.cse545.hospitalSystem.models.Role;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.PatientRecordRequestDTO;
import com.cse545.hospitalSystem.repositories.PatientRecordRepository;
import com.cse545.hospitalSystem.repositories.UserRepository;

@Service
public class PatientRecordService {
    
    @Autowired
    PatientRecordRepository patientRecordRepo;
    
    @Autowired
    UserRepository userRepo;
    
    public PatientRecord createPatientRecord(PatientRecordRequestDTO patientRecordRequest) {
        //Get User, validate that user is a patient and record hasnt been created
        //only then create the record
        User user = null;
        PatientRecord patientRecord = new PatientRecord();
        Optional<User> patientOptional = userRepo.findById(patientRecordRequest.getPatientId()); 
        if(!patientOptional.isPresent()) {
            return null; //ideally throw exception usernotfound
        }
        user = patientOptional.get();
        boolean isPatient = user.getRoles().stream().anyMatch(role->role.getRole().equals("PATIENT");)
        return patientRecord;
    }

}
