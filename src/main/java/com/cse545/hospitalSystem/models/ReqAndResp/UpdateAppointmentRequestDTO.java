package com.cse545.hospitalSystem.models.ReqAndResp;

import com.cse545.hospitalSystem.models.GenericStatus;

public class UpdateAppointmentRequestDTO {
    
    private Long doctorId;
    private Long appointmentId;
    private Long staffId;
    private GenericStatus status;
    private String staffNote;
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Long getStaffId() {
		return staffId;
	}
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	public GenericStatus getStatus() {
		return status;
	}
	public void setStatus(GenericStatus status) {
		this.status = status;
	}
	public String getStaffNote() {
		return staffNote;
	}
	public void setStaffNote(String staffNote) {
		this.staffNote = staffNote;
	}
    
   
    

}
