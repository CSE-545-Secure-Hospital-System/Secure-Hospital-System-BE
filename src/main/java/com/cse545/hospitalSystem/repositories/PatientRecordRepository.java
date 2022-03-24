package com.cse545.hospitalSystem.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cse545.hospitalSystem.models.PatientRecord;

@Repository
public interface PatientRecordRepository extends JpaRepository<PatientRecord, Long> {

    Optional<PatientRecord> findByUserId(long id);

}
