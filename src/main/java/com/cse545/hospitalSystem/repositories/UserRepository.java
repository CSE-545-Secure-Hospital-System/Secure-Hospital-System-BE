package com.cse545.hospitalSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cse545.hospitalSystem.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByEmail(String email);

}
