package com.cse545.hospitalSystem.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "diagnoses")
public class Diagnosis {
	
	@Id
    @Column(name = "diagnosis_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
	
	
	@JsonIgnore
    @ManyToOne(targetEntity = User.class)
	private User patient;
	
	@JsonIgnore
    @ManyToOne(targetEntity = User.class)
	private User doctor;
	
	@JsonIgnore
    @OneToOne(targetEntity = Appointment.class)
	private Appointment appointment;
	
	@Column
	private String diagnosis_info;
	
	@Column
	private String prescription;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "DIAGNOSIS_LABREPORTS",
    joinColumns = {
    @JoinColumn(name = "DIAGNOSIS_ID")
    },
    inverseJoinColumns = {
    @JoinColumn(name = "LABRESULT_ID") })
	private Set<LabResult> labResult;

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

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
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

	public Set<LabResult> getLabResult() {
		return labResult;
	}

	public void setLabResult(Set<LabResult> labResult) {
		this.labResult = labResult;
	}

	
	
	
    
    
}
