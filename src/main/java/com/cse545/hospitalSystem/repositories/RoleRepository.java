package com.cse545.hospitalSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cse545.hospitalSystem.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Role findByRole(String role);

}
