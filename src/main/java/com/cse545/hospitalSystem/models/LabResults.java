package com.cse545.hospitalSystem.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "labresults")
public class LabResults {
    
    @Id
    @Column(name = "labresults_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne(targetEntity = LabTests.class)
    @JsonIgnore
    private LabTests labTest;
    
    @ManyToOne(targetEntity = User.class)
    @JsonIgnore
    private User patient;
    
    //TODO check on the diagnosis side of the bidrection,
    //need to implement this appropriately on both sides
    @ManyToOne(targetEntity = Diagnosis.class)
    @JoinColumn(name="diagnosis_id", nullable=false)
    @JsonIgnore
    private Diagnosis diagnosis;
    
    @OneToOne(targetEntity = User.class)
    @JsonIgnore
    private User approvedBy;
    
    
    private Double labTestFee;
    
    private LabResultStatus labResultStatus;


    public Double getLabTestFee() {
        return labTestFee;
    }

    public void setLabTestFee(Double labTestFee) {
        this.labTestFee = labTestFee;
    }

    public long getId() {
        return id;
    }

}