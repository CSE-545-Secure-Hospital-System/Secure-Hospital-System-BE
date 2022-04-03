package com.cse545.hospitalSystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cse545.hospitalSystem.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "claims")
@Data
public class PolicyClaim {

	@Id
    @Column(name = "claim_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	private Long policyId;
	
	private Long PatientId;
	
	private Long appointmentId;
	
	private Double amountRequired;
	
	private TransactionStatus Status;

	public PolicyClaim() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public Long getPatientId() {
		return PatientId;
	}

	public void setPatientId(Long patientId) {
		PatientId = patientId;
	}

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Double getAmountRequired() {
		return amountRequired;
	}

	public void setAmountRequired(Double amountRequired) {
		this.amountRequired = amountRequired;
	}

	public TransactionStatus getStatus() {
		return Status;
	}

	public void setStatus(TransactionStatus status) {
		Status = status;
	}

	public PolicyClaim(Long policyId, Long patientId, Long appointmentId, Double amountRequired,
			TransactionStatus status) {
		super();
		this.policyId = policyId;
		PatientId = patientId;
		this.appointmentId = appointmentId;
		this.amountRequired = amountRequired;
		Status = status;
	}

	
	
}
