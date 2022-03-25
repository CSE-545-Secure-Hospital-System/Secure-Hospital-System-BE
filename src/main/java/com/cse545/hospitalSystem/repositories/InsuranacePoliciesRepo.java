package com.cse545.hospitalSystem.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cse545.hospitalSystem.models.Insurance_Policies;

@Repository
public interface InsuranacePoliciesRepo extends JpaRepository<Insurance_Policies, Long>{

}
