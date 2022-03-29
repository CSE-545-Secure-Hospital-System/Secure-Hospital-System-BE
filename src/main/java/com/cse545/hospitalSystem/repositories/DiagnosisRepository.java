package com.cse545.hospitalSystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cse545.hospitalSystem.models.Diagnosis;


@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

//	public List<Diagnosis> findByPatientId(long patientId);
	
	

}
