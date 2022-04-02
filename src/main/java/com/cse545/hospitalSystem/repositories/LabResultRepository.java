package com.cse545.hospitalSystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.LabResult;

public interface LabResultRepository extends JpaRepository<LabResult, Long> {

	@Query(value="SELECT * FROM lab_results where patient_user_id = ?1", nativeQuery = true)
	public List<LabResult> findByPatient(Long patient);
}

