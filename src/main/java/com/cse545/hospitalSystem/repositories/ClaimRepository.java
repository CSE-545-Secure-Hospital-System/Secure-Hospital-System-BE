package com.cse545.hospitalSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cse545.hospitalSystem.models.PolicyClaim;

@Repository
public interface ClaimRepository extends JpaRepository<PolicyClaim, Long> {

	@Query(value ="SELECT * from claims c where c.appointment_id = ?1", nativeQuery = true)
	PolicyClaim findByAppointmentId(Long appointmentId);

}
