package com.cse545.hospitalSystem.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cse545.hospitalSystem.models.Logs;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Long> {
    
    Optional<Logs> findById(Long logs);

}