package com.cse545.hospitalSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cse545.hospitalSystem.models.Support;

@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {

}
