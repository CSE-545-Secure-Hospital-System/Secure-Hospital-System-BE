package com.cse545.hospitalSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cse545.hospitalSystem.models.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

}
