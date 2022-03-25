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
        if(user.getPatientRecord() != null) {
            //ideally send patient record already exists
            return user.getPatientRecord();
        }
        patientRecord = new PatientRecord();
        patientRecord.setAge(patientRecordRequest.getAge());
        patientRecord.setDateOfBirth(patientRecordRequest.getDateOfBirth());
        patientRecord.setDiseaseHistory(patientRecordRequest.getDiseaseHistory());
        //TODO//patientRecord.setInsuranceProvider(patientRecordRequest.getInsuranceProviderId());
        user.setPatientRecord(patientRecord);
        User savedUser = userRepo.save(user);
        return savedUser.getPatientRecord(); 
    }

    public PatientRecord getPatientRecord(long patientId) {
        Optional<PatientRecord> patientRecord = null;
        Optional<User> patientOptional = userRepo.findById(patientId);
        if(!patientOptional.isPresent()) {
            return null;
        }
        User user = patientOptional.get();
        if(!isUserPatient(user)) {
            return null;
        }
        return user.getPatientRecord();
    }

    public PatientRecord updatePatientRecord(PatientRecordRequestDTO patientRecordRequest) {
        User user = null;
        PatientRecord patientRecord = null;
        Optional<User> patientOptional = userRepo.findById(patientRecordRequest.getPatientId()); 
        if(!patientOptional.isPresent()) {
            return null;
        }
        user = patientOptional.get();
        boolean isPatient = user.getRoles().stream().anyMatch(role->role.getRole().equals(SecureHospitalSystemConstants.PATIENT_ROLE));
        if(!isUserPatient(user)) {
            //return user is not patient
            return null;
        }
        PatientRecord record = user.getPatientRecord();
        if(record == null) {
            return null;
        }
        record.setAge(patientRecordRequest.getAge());
        record.setDateOfBirth(patientRecordRequest.getDateOfBirth());
        record.setDiseaseHistory(patientRecordRequest.getDiseaseHistory());
        //TODO//patientRecord.setInsuranceProvider(patientRecordRequest.getInsuranceProviderId());
        user.setPatientRecord(record);
        User savedUser = userRepo.save(user);
        return savedUser.getPatientRecord();
    }
    
    private boolean isUserPatient(User user) {
        return user.getRoles().stream().anyMatch(role->role.getRole().equals(SecureHospitalSystemConstants.PATIENT_ROLE));
    }

}
