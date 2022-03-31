package com.cse545.hospitalSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cse545.hospitalSystem.models.Role;
import com.cse545.hospitalSystem.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
