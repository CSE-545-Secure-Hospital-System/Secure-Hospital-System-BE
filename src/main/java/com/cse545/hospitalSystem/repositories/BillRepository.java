package com.cse545.hospitalSystem.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cse545.hospitalSystem.models.Bill;
import com.cse545.hospitalSystem.models.Transaction;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

	@Query(value = "SELECT * FROM bill b WHERE b.patient_user_id = ?1", nativeQuery = true)
    List<Bill> findByPatientId(Long patientId);

    @Query(value = "SELECT * FROM bill b WHERE b.appointment_appointment_id = ?1", nativeQuery = true)
	Optional<Bill> findByAppointmentId(Long appointmentId);

}
