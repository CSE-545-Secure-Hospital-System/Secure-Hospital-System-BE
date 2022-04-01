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

import com.cse545.hospitalSystem.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "transaction")
public class Transaction {
    
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    
    @OneToOne(targetEntity = Bill.class)
    private Bill bill;
    
    @JsonIgnore
    @ManyToOne(targetEntity = User.class)
    private User patient;
    
    @JsonIgnore
    @ManyToOne(targetEntity = User.class)
    private User staff;
    
    @OneToOne(targetEntity = Appointment.class)
    private Appointment appointment;
    
    private TransactionStatus transactionStatus;

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
    
    

}
