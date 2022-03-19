package com.cse545.hospitalSystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name = "coverages")
@Data
public class Coverages {
	
	@Id
    @Column(name = "coverage_id")
    private Long id;
	
	private String CoverageName;

	public Coverages(String coverageName) {
		super();
		CoverageName = coverageName;
	}

	public String getCoverageName() {
		return CoverageName;
	}

	public void setCoverageName(String coverageName) {
		CoverageName = coverageName;
	}
	
	

}
