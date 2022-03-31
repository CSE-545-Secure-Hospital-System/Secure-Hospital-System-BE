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
    
    private TransactionStatus transactionStatus;

}
