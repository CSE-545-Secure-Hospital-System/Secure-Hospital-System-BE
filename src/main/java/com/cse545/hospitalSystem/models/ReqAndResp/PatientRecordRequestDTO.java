package com.cse545.hospitalSystem.models.ReqAndResp;

import java.util.Date;

public class PatientRecordRequestDTO {
    
    private Long patientId;
    private Integer age;
    private Date dateOfBirth;
    private String diseaseHistory;
    private Long insuranceProviderId;
    
    public Long getInsuranceProviderId() {
        return insuranceProviderId;
    }
    
    public Long getPatientId() {
        return patientId;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    public String getDiseaseHistory() {
        return diseaseHistory;
    }
    
}
