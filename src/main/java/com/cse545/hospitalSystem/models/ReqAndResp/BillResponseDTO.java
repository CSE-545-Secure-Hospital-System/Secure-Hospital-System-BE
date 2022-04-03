package com.cse545.hospitalSystem.models.ReqAndResp;

import com.cse545.hospitalSystem.enums.BillStatus;

public class BillResponseDTO {

	private Long billId;
	
	private Long appointmentId, patientId, staffId;
	
	private Double fee;
	
	private String patientName, staffName;
	
	private String billGeneratedTime; 
	
	private BillStatus billStatus;

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
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

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
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

	public String getBillGeneratedTime() {
		return billGeneratedTime;
	}

	public void setBillGeneratedTime(String billGeneratedTime) {
		this.billGeneratedTime = billGeneratedTime;
	}

	public BillStatus getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(BillStatus billStatus) {
		this.billStatus = billStatus;
	}
	
	
}
