package com.cse545.hospitalSystem.models.ReqAndResp;

import java.util.Set;

import com.cse545.hospitalSystem.enums.AppointmentType;

public class AllDoctorsTimingRequest {

	private String date;
	private String startTime;
	private AppointmentType appointmentType;

	public AllDoctorsTimingRequest(String date, String startTime, AppointmentType appointmentType) {
		super();
		this.date = date;
		this.startTime = startTime;
		this.appointmentType = appointmentType;
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	
	
}
