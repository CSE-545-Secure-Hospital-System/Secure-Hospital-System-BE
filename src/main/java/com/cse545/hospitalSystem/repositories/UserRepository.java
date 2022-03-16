package com.cse545.hospitalSystem.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cse545.hospitalSystem.enums.RoleMapping;
import com.cse545.hospitalSystem.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a "+
    "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableUser(String email);
    
    
    @Query("FROM User u WHERE (u.firstName LIKE %?1% OR u.lastName LIKE %?1% OR u.email LIKE %?1%)")
    List<User> searchByTerm(@Param("searchTerm") String searchTerm);


}
