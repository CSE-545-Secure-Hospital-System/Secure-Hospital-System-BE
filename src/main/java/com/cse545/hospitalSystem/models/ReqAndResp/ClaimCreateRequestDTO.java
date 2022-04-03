package com.cse545.hospitalSystem.models.ReqAndResp;

public class ClaimCreateRequestDTO {

	private Double amountRequired;
	private Long appointmentId, patientId, policyId;
	public Double getAmountRequired() {
		return amountRequired;
	}
	public void setAmountRequired(Double amountRequired) {
		this.amountRequired = amountRequired;
	}
	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public Long getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}
	
	
}
