package com.cse545.hospitalSystem.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="patient_record")
public class PatientRecord {
    
    @Id
    @Column(name = "patient_record_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @OneToOne(mappedBy = "patientRecord")
    @JsonIgnore
    private User patient;
    
    @Column
    private Integer age;
    
    @Column
    private Date dateOfBirth;
    
    @Column
    private String diseaseHistory;
    
    @ManyToOne
    @JoinColumn(name = "insurance_provider_id", referencedColumnName = "insurance_provider_id")
    private InsuranceProvider insuranceProvider;

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDiseaseHistory() {
        return diseaseHistory;
    }

    public void setDiseaseHistory(String diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
    }

    public InsuranceProvider getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(InsuranceProvider insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public Long getId() {
        return id;
    }
    
    
    
}
