package com.cse545.hospitalSystem.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cse545.hospitalSystem.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmailId(String emailId);
    
    Boolean existsByEmailId(String emailId);
    
    Boolean existsByUserName(String userName);
    
    Optional<User> findByUserName(String username);

}
