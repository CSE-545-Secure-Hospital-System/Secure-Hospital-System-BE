package com.cse545.hospitalSystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import ch.qos.logback.classic.db.names.ColumnName;
import lombok.Data;


@Entity
@Table(name = "coverages", uniqueConstraints = @UniqueConstraint( columnNames = { "coverageName" }))
@Data
public class Coverage {
	
	@Id
    @Column(name = "coverage_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
	
	@Column(name = "coverageName")
	private String coverageName;
	
	@Column
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Coverage() {
		super();
	}

	public Coverage(Long id, String coverageName, String description) {
		super();
		this.id = id;
		this.coverageName = coverageName;
		this.description = description;
	}

	public Coverage(String coverageName) {
		super();
		this.coverageName = coverageName;
	}

	public String getCoverageName() {
		return this.coverageName;
	}

	public void setCoverageName(String coverageName) {
		this.coverageName = coverageName;
	}
	
	
	
	

}
