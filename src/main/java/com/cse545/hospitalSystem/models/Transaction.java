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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.cse545.hospitalSystem.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "transaction", uniqueConstraints = @UniqueConstraint( columnNames = { "appointment_appointment_id" }))
public class Transaction {
    
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    
    @JsonIgnore
    @OneToOne(targetEntity = Bill.class)
    private Bill bill;
    
    @JsonIgnore
    @ManyToOne(targetEntity = User.class)
    private User patient;
    
    @JsonIgnore
    @ManyToOne(targetEntity = User.class)
    private User staff;
    
    @JsonIgnore
    @OneToOne(targetEntity = PolicyClaim.class)
    private PolicyClaim claim;
    
    @JsonIgnore
    @ManyToOne(targetEntity = Appointment.class)
    private Appointment appointment;
    
    @Column(name = "transactionCompletionTime")
    @NotNull
    private String transactionCompletionTime;
    
    private TransactionStatus transactionStatus;

	public PolicyClaim getClaim() {
		return claim;
	}

	public void setClaim(PolicyClaim claim) {
		this.claim = claim;
	}

	public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public long getId() {
        return id;
    }

	public User getPatient() {
		return patient;
	}

	public void setPatient(User patient) {
		this.patient = patient;
	}

	public User getStaff() {
		return staff;
	}

	public void setStaff(User staff) {
		this.staff = staff;
	}

	public String getTransactionCompletionTime() {
		return transactionCompletionTime;
	}

	public void setTransactionCompletionTime(String transactionCompletionTime) {
		this.transactionCompletionTime = transactionCompletionTime;
	}
    
    
    
    

}
