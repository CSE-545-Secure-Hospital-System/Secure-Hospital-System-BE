package com.cse545.hospitalSystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bill")
public class Bill {
    
    @Id
    @Column(name = "bill_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    
    private Double fee;
    
    @JsonIgnore
    @ManyToOne(targetEntity = User.class)
    private User patient;
    
    @ManyToOne(targetEntity = User.class)
    @JsonIgnore
    private User doctor;
    
    //staff who decides
    @ManyToOne(targetEntity = User.class)
    @JsonIgnore
    private User staff;
    
    @OneToOne(targetEntity = User.class)
    private Appointment appointment;
    
    @Column(name = "billGeneratedTime")
    @NotNull
    private String billGeneratedTime;

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public User getStaff() {
        return staff;
    }

    public void setStaff(User staff) {
        this.staff = staff;
    }

    public String getBillGeneratedTime() {
        return billGeneratedTime;
    }

    public void setBillGeneratedTime(String billGeneratedTime) {
        this.billGeneratedTime = billGeneratedTime;
    }

    public long getId() {
        return id;
    }
    
    

}
