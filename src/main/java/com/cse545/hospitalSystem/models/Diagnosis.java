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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "diagnoses")
public class Diagnosis {
	
	@Id
    @Column(name = "diagnosis_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
	
	private String diagnosis_info;
	
	private String prescription;
	
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "DIAGNOSIS_LABTESTS",
    joinColumns = {
    @JoinColumn(name = "USER_ID")
    },
    inverseJoinColumns = {
    @JoinColumn(name = "LAB_ID") })
	private Set<LabTest> labtests;
    
    @SuppressWarnings("unused")
	private void addLabTest(LabTest labtest) {
    	this.labtests.add(labtest);
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Set<LabTest> getLabtests() {
		return labtests;
	}

	public void setLabtests(Set<LabTest> labtests) {
		this.labtests = labtests;
	}
    
    
    
	

}
