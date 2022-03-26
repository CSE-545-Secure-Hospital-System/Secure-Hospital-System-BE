package com.cse545.hospitalSystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cse545.hospitalSystem.models.LabTest;

@Repository
public interface labTestRepository extends JpaRepository<LabTest, Long>{
	
	LabTest findByLabTestName( String labTestName);

}
