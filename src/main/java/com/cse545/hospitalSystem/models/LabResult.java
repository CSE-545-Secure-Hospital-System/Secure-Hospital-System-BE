package com.cse545.hospitalSystem.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "labResults")
public class LabResult {
	
	@Id
	@NotBlank
    @Column(name = "labResult_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
	
	@JsonIgnore
    @ManyToOne(targetEntity = User.class)
	@NotNull
	private User patient;
	
	@JsonIgnore
    @ManyToOne(targetEntity = User.class)
	@NotNull
	private User LabStaff;
	
	@JsonIgnore
    @ManyToOne(targetEntity = User.class)
	private User doctor;
	
	@Column
	private LabResultStatus labResultStatus;
	
	@Column
	@NotBlank
	private String labStaffNotes;
	
	@Column
	@NotBlank
	private String result;
	
    @ManyToOne(targetEntity = LabTest.class)
	private LabTest labtests;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getPatient() {
		return patient;
	}

	public void setPatient(User patient) {
		this.patient = patient;
	}

	public User getLabStaff() {
		return LabStaff;
	}

	public void setLabStaff(User labStaff) {
		LabStaff = labStaff;
	}

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}

//	public Diagnosis getDiagnosis() {
//		return diagnosis;
//	}
//
//	public void setDiagnosis(Diagnosis diagnosis) {
//		this.diagnosis = diagnosis;
//	}

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

	public LabTest getLabtests() {
		return labtests;
	}

	public void setLabtests(LabTest labtests) {
		this.labtests = labtests;
	}
	
	
	

}
