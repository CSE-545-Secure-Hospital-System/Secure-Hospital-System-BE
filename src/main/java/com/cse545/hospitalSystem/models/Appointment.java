package com.cse545.hospitalSystem.models;

import java.util.Date;

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
@Table(name = "appointment")
public class Appointment {
    
    @Id
    @Column(name = "appointment_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne(targetEntity = User.class)
    private User patient;
    
    @ManyToOne(targetEntity = User.class)
    @JsonIgnore
    private User doctor;
    
    //staff who decides
    @ManyToOne(targetEntity = User.class)
    @JsonIgnore
    private User staff;
    
    @Column(name = "startTime")
    @NotNull
    private Date startTime;
    
    @Column(name = "endTime")
    @NotNull
    private Date endTime;
    
    @OneToOne(targetEntity=Diagnosis.class)
    @JsonIgnore
    private Diagnosis diagnosis;
    
    //enum
    private AppointmentStatus status;
    
    private Double fees;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }
    
    
    

}
