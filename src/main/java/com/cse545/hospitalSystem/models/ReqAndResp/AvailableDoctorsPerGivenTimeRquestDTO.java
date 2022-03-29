package com.cse545.hospitalSystem.models.ReqAndResp;

import java.util.Date;

import com.cse545.hospitalSystem.enums.AppointmentType;

public class AvailableDoctorsPerGivenTimeRquestDTO {
	
	private Date date;
	private String startTime;
	private AppointmentType appointmentType;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public AppointmentType getAppointmentType() {
		return appointmentType;
	}
	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}
	
	

}
