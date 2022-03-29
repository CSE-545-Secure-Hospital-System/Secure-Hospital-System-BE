package com.cse545.hospitalSystem.models.ReqAndResp;

import com.cse545.hospitalSystem.models.Appointment;

public class AppointmentResponseDTO {
	
	private Appointment appointment;
	
	private String doctorFirstName, doctorLastName, patientLastName, patientFirstName, staffFirstName, staffLastName;
	
	private long patientId, staffId, doctorId;
	
	

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public long getStaffId() {
		return staffId;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}

	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public String getDoctorFirstName() {
		return doctorFirstName;
	}

	public void setDoctorFirstName(String doctorFirstName) {
		this.doctorFirstName = doctorFirstName;
	}

	public String getDoctorLastName() {
		return doctorLastName;
	}

	public void setDoctorLastName(String doctorLastName) {
		this.doctorLastName = doctorLastName;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getStaffFirstName() {
		return staffFirstName;
	}

	public void setStaffFirstName(String staffFirstName) {
		this.staffFirstName = staffFirstName;
	}

	public String getStaffLastName() {
		return staffLastName;
	}

	public void setStaffLastName(String staffLastName) {
		this.staffLastName = staffLastName;
	}

	public AppointmentResponseDTO(Appointment appointment, String doctorFirstName, String doctorLastName,
			String patientLastName, String patientFirstName, String staffFirstName, String staffLastName) {
		super();
		this.appointment = appointment;
		this.doctorFirstName = doctorFirstName;
		this.doctorLastName = doctorLastName;
		this.patientLastName = patientLastName;
		this.patientFirstName = patientFirstName;
		this.staffFirstName = staffFirstName;
		this.staffLastName = staffLastName;
	}

	public AppointmentResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
