package com.cse545.hospitalSystem.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.cse545.hospitalSystem.enums.AppointmentType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "appointment")
public class Appointment {
    
    @Id
    @Column(name = "appointment_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    
    private AppointmentType appointmentType;
    
    @JsonIgnore
    @ManyToOne(targetEntity = User.class)
    @NotNull
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
    private String startTime;
    
    @Column(name = "date")
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @JsonIgnore
    @OneToOne(targetEntity = Bill.class)
    private Bill bill;
    
    @JsonIgnore
    @OneToOne(targetEntity = Transaction.class)
    private Transaction transaction;

    //enum
    private GenericStatus status;
    
    private Double fees;
    
    private String staffNote;
    
    @OneToOne(targetEntity = Diagnosis.class)
    private Diagnosis diagnoses;
    
	public Diagnosis getDiagnoses() {
		return diagnoses;
	}

	public void setDiagnoses(Diagnosis diagnoses) {
		this.diagnoses = diagnoses;
	}

	public void setDiagnoisis(Diagnosis diagnoisis) {
		this.diagnoses = diagnoisis;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public GenericStatus getStatus() {
        return status;
    }

    public void setStatus(GenericStatus status) {
        this.status = status;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AppointmentType getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}

	public String getStaffNote() {
		return staffNote;
	}

	public void setStaffNote(String staffNote) {
		this.staffNote = staffNote;
	}
	
	public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
    
    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    
    

}
