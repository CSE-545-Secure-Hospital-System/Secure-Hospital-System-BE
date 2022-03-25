package com.cse545.hospitalSystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cse545.hospitalSystem.models.Coverage;
import java.util.Optional;

@Repository
public interface CoveragesRepository extends JpaRepository<Coverage, Long>{
	
	@Query("SELECT c from Coverage c where c.coverageName = ?1")
	Optional<Coverage> findByCoverageName(String coverageName);
}
