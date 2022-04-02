package com.cse545.hospitalSystem.models.ReqAndResp;

public class UpdateLabTestRequest {

	private String labStaffNotes;
	public long getLabStaffId() {
		return labStaffId;
	}
	public void setLabStaffId(long labStaffId) {
		this.labStaffId = labStaffId;
	}
	private String doctorName;
    private long id;
    private String labResultStatus;
    private String labStaffName;
    private double labTestCost;
    private long labTestId;
    private String labTestName;
    private String patientName;
    private String result;
    private long labStaffId;
    
	public String getLabStaffNotes() {
		return labStaffNotes;
	}
	public void setLabStaffNotes(String labStaffNotes) {
		this.labStaffNotes = labStaffNotes;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLabResultStatus() {
		return labResultStatus;
	}
	public void setLabResultStatus(String labResultStatus) {
		this.labResultStatus = labResultStatus;
	}
	public String getLabStaffName() {
		return labStaffName;
	}
	public void setLabStaffName(String labStaffName) {
		this.labStaffName = labStaffName;
	}
	public double getLabTestCost() {
		return labTestCost;
	}
	public void setLabTestCost(double labTestCost) {
		this.labTestCost = labTestCost;
	}
	public long getLabTestId() {
		return labTestId;
	}
	public void setLabTestId(long labTestId) {
		this.labTestId = labTestId;
	}
	public String getLabTestName() {
		return labTestName;
	}
	public void setLabTestName(String labTestName) {
		this.labTestName = labTestName;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
    
    
    
    
}
