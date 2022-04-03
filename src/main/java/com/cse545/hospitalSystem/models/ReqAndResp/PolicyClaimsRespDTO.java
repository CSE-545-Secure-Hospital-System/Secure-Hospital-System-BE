package com.cse545.hospitalSystem.models.ReqAndResp;

import com.cse545.hospitalSystem.enums.TransactionStatus;
import com.cse545.hospitalSystem.models.Insurance_Policies;

public class PolicyClaimsRespDTO {
	
	private Long claimId;
	
	private Long patientId;
	
	private String patientName;
	
	private Insurance_Policies policy;
	
	private Long appointmentId;
	
	private Double amountRequired;
	
	private TransactionStatus Status;

	public Long getClaimId() {
		return claimId;
	}

	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public Insurance_Policies getPolicy() {
		return policy;
	}

	public void setPolicy(Insurance_Policies policy) {
		this.policy = policy;
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
	
	

}
