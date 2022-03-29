package com.cse545.hospitalSystem.models.ReqAndResp;

public class AppointmentUpdateRequestDTO {
	
	private Long doctorId, hospitalStaffId, appointmentId;
	private String staffNote;
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public Long getHospitalStaffId() {
		return hospitalStaffId;
	}
	public void setHospitalStaffId(Long hospitalStaffId) {
		this.hospitalStaffId = hospitalStaffId;
	}
	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getStaffNote() {
		return staffNote;
	}
	public void setStaffNote(String staffNote) {
		this.staffNote = staffNote;
	}
	
	

}
