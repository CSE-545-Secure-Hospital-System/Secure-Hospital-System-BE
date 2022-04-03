package com.cse545.hospitalSystem.models.ReqAndResp;

import com.cse545.hospitalSystem.enums.TransactionStatus;
import com.cse545.hospitalSystem.models.Bill;

public class TransactionRespDTO {

	private Long TransactionId;
	
	private Long claimId, appointmentId, patientId, staffId;
	
	private Bill bill;
	
	private String patientName, staffName;
	
	private String transactionCompletionTime; 
	
	private TransactionStatus transactionStatus;

	public Long getTransactionId() {
		return TransactionId;
	}

	public void setTransactionId(Long transactionId) {
		TransactionId = transactionId;
	}

	public Long getClaimId() {
		return claimId;
	}

	public void setClaimId(Long claimId) {
		this.claimId = claimId;
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

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getTransactionCompletionTime() {
		return transactionCompletionTime;
	}

	public void setTransactionCompletionTime(String transactionCompletionTime) {
		this.transactionCompletionTime = transactionCompletionTime;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	
	
	
	
	
}
