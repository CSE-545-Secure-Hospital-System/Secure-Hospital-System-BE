package com.cse545.hospitalSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.Role;
import com.cse545.hospitalSystem.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query(value = "SELECT * from Transaction t WHERE t.appointment_appointment_id = ?1", nativeQuery = true)
	Transaction findByAppointmentById(Long appointmentId);


}
