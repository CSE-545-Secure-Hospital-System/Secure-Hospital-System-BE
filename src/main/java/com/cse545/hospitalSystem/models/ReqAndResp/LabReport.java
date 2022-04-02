package com.cse545.hospitalSystem.models.ReqAndResp;

import java.util.List;

import javax.persistence.Column;

import com.cse545.hospitalSystem.models.LabResultStatus;
import com.cse545.hospitalSystem.models.LabTest;

public class LabReport {
	
	private long id;
	
	private String patientName, doctorName, labStaffName;
	
	private LabResultStatus labResultStatus;
	
	private String labStaffNotes;
	
	private String result;
	
	private Long labTestId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getLabStaffName() {
		return labStaffName;
	}

	public void setLabStaffName(String labStaffName) {
		this.labStaffName = labStaffName;
	}

	public LabResultStatus getLabResultStatus() {
		return labResultStatus;
	}

	public void setLabResultStatus(LabResultStatus labResultStatus) {
		this.labResultStatus = labResultStatus;
	}

	public String getLabStaffNotes() {
		return labStaffNotes;
	}

	public void setLabStaffNotes(String labStaffNotes) {
		this.labStaffNotes = labStaffNotes;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Long getLabTestId() {
		return labTestId;
	}

	public void setLabTestId(Long labTestId) {
		this.labTestId = labTestId;
	}
	
	

}
