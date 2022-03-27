package com.cse545.hospitalSystem.models;

import com.cse545.hospitalSystem.enums.AppointmentType;

public class TimingsRequestDTO {
	
	private Long doctorId;
	private String date;
	private AppointmentType appointmentType;
	public TimingsRequestDTO(long doctorId, String date, AppointmentType appointmentType) {
		super();
		this.doctorId = doctorId;
		this.date = date;
		this.appointmentType = appointmentType;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public AppointmentType getAppointmentType() {
		return appointmentType;
	}
	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}
	
	

}
