package com.cse545.hospitalSystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "coverages", uniqueConstraints = @UniqueConstraint( columnNames = { "coverageName" }))
public class Coverage {
	
	@Id
	@Column(name = "coverage_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
	
	@NotNull
	private String coverageName;
	
	@NotNull
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	public Coverage() {
	}

	public Coverage(String coverageName, String description) {
		this.coverageName = coverageName;
		this.description = description;
	}

	public String getCoverageName() {
		return this.coverageName;
	}

	public void setCoverageName(String coverageName) {
		this.coverageName = coverageName;
	}
	
	
	
	

}
