package com.cse545.hospitalSystem.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "labtests")
public class LabTests {
    
    @Id
    @Column(name = "labtests_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    
    private String description;
    
    private Double labTestFee;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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