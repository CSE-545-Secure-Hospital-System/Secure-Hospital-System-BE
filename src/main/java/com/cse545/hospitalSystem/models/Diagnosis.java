package com.cse545.hospitalSystem.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "diagnosis")
public class Diagnosis {
    
    @Id
    @Column(name = "diagnosis_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    
    private String description;
    
    @OneToOne(targetEntity = Appointment.class)
    @JsonIgnore
    private Appointment appointment;
    
    //TODO
    //has a many to one with labtests
    @ManyToOne(targetEntity = LabTests.class)
    @JsonIgnore
    private List<LabTests> labTests;
    
    @OneToMany(targetEntity = LabResults.class, mappedBy="diagnosis")
    @JsonIgnore
    private List<LabResults> labResults;
    
    private String prescription;

}
