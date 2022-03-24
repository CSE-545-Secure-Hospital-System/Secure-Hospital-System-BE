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
import com.cse545.hospitalSystem.constants.SecureHospitalSystemConstants;

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
        PatientRecord patientRecord = null;
        Optional<User> patientOptional = userRepo.findById(patientRecordRequest.getPatientId()); 
        if(!patientOptional.isPresent()) {
            return null; //ideally throw exception usernotfound
        }
        user = patientOptional.get();
        if(!isUserPatient(user)) {
            return null;
        }
        Optional<PatientRecord> optional = patientRecordRepo.findByUserId(user.getId());
        if(optional.isPresent()) {
            return optional.get();
        }
        patientRecord = new PatientRecord();
        patientRecord.setAge(patientRecordRequest.getAge());
        patientRecord.setDateOfBirth(patientRecordRequest.getDateOfBirth());
        patientRecord.setDiseaseHistory(patientRecordRequest.getDiseaseHistory());
        //TODO//patientRecord.setInsuranceProvider(patientRecordRequest.getInsuranceProviderId());
        patientRecord.setPatient(user);
        return patientRecord;
    }

    public PatientRecord getPatientRecord(long patientId) {
        User user = null;
        Optional<PatientRecord> patientRecord = null;
        Optional<User> patientOptional = userRepo.findById(patientId);
        if(!patientOptional.isPresent()) {
            return null;
        }
        if(!isUserPatient(user)) {
            return null;
        }
        patientRecord = patientRecordRepo.findByUserId(patientId); 
        return patientRecord.get();
    }

    public PatientRecord updatePatientRecord(long patientId, PatientRecordRequestDTO patientRecordRequest) {
        User user = null;
        PatientRecord patientRecord = null;
        Optional<User> patientOptional = userRepo.findById(patientRecordRequest.getPatientId()); 
        if(!patientOptional.isPresent()) {
            return null;
        }
        user = patientOptional.get();
        boolean isPatient = user.getRoles().stream().anyMatch(role->role.getRole().equals(SecureHospitalSystemConstants.PATIENT_ROLE));
        if(!isUserPatient(user)) {
            return null;
        }
        Optional<PatientRecord> optional = patientRecordRepo.findByUserId(user.getId());
        if(optional.isPresent()) {
            return optional.get();
        }
        patientRecord = new PatientRecord();
        patientRecord.setAge(patientRecordRequest.getAge());
        patientRecord.setDateOfBirth(patientRecordRequest.getDateOfBirth());
        patientRecord.setDiseaseHistory(patientRecordRequest.getDiseaseHistory());
        //TODO//patientRecord.setInsuranceProvider(patientRecordRequest.getInsuranceProviderId());
        patientRecord.setPatient(user);
        return patientRecord;
    }
    
    private boolean isUserPatient(User user) {
        return user.getRoles().stream().anyMatch(role->role.getRole().equals(SecureHospitalSystemConstants.PATIENT_ROLE));
    }

}
