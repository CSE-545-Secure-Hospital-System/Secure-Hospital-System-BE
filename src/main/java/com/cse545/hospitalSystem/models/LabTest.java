package com.cse545.hospitalSystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Table(name = "labTests", uniqueConstraints = @UniqueConstraint( columnNames = { "labTestName" }))
public class LabTest {

	@Id
	@NotBlank
    @Column(name = "lab_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	@Column
	@NotBlank
	private String labTestName;
	
	@NotBlank
	@Column
	private String labTestDescription;
	
	@NotBlank
	@Column
	private Double labTestCost;

	public LabTest( String labTestName, String labTestDescription, Double labTestCost) {

		this.labTestName = labTestName;
		this.labTestDescription = labTestDescription;
		this.labTestCost = labTestCost;
	}

	public LabTest() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabTestName() {
		return labTestName;
	}

	public void setLabTestName(String labTestName) {
		this.labTestName = labTestName;
	}

	public String getLabTestDescription() {
		return labTestDescription;
	}

	public void setLabTestDescription(String labTestDescription) {
		this.labTestDescription = labTestDescription;
	}

	public Double getLabTestCost() {
		return labTestCost;
	}

	public void setLabTestCost(Double labTestCost) {
		this.labTestCost = labTestCost;
	}

}
