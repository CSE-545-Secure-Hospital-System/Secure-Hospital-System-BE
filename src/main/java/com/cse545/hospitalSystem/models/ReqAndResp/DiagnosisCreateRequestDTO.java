package com.cse545.hospitalSystem.models.ReqAndResp;

import java.util.Set;

public class DiagnosisCreateRequestDTO {
	
	private String diagnosis_info;
	
	private String prescription;
	
	private Set<Long> labTestIds;
	
	private long doctorId, patientId, appointmentId;

	public DiagnosisCreateRequestDTO(String diagnosis_info, String prescription, Set<Long> labTestIds, long doctorId,
			long patientId, long appointmentId) {
		super();
		this.diagnosis_info = diagnosis_info;
		this.prescription = prescription;
		this.labTestIds = labTestIds;
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.appointmentId = appointmentId;
	}

	public String getDiagnosis_info() {
		return diagnosis_info;
	}

	public void setDiagnosis_info(String diagnosis_info) {
		this.diagnosis_info = diagnosis_info;
	}

	public String getPrescription() {
		return prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public Set<Long> getLabTestIds() {
		return labTestIds;
	}

	public void setLabTestIds(Set<Long> labTestIds) {
		this.labTestIds = labTestIds;
	}

	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(long appointmentId) {
		this.appointmentId = appointmentId;
	}

	

}
