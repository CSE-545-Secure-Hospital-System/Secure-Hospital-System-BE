package com.cse545.hospitalSystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cse545.hospitalSystem.models.LabTest;

@Repository
public interface LabTestRepository extends JpaRepository<LabTest, Long>{
	
	@Query(value= "SELECT * from lab_tests l WHERE l.lab_test_name = ?1", nativeQuery = true)
	public LabTest findByLabTestName(String labTestName); 

}
