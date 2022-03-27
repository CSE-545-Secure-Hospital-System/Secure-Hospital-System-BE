package com.cse545.hospitalSystem.models.ReqAndResp;

import com.cse545.hospitalSystem.enums.AppointmentType;

public class AppointmentRequestDTO {

	private AppointmentType appointmentType;
	 private Long patientId;
	 private Long doctorId;
	 private String startTime;
	 private String date;
	 
	public AppointmentType getAppointmentType() {
		return appointmentType;
	}
	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public AppointmentRequestDTO(AppointmentType appointmentType, Long patientId, Long doctorId, String startTime,
			String date) {
		super();
		this.appointmentType = appointmentType;
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.startTime = startTime;
		this.date = date;
	}
	 
	
	 
	 
	    
}
