package com.cse545.hospitalSystem.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
    
    
//    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.lastName LIKE %?1% OR u.firstName LIKE %?1% OR u.email LIKE %?1% or u.id LIKE %?1% OR r.role LIKE %?1%")
//    List<User> searchByTerm(String searchTerm);
    
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.role = ?1 and r.role != 'ADMIN'")
    List<User> searchByRole(String role);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.role != 'ADMIN'")
	List<User> findALLExecptAdmins();

    @Query("SELECT u FROM User u JOIN u.roles r WHERE (u.lastName LIKE %?2% OR u.firstName LIKE %?2% OR u.email LIKE %?2% or u.id LIKE %?2% OR r.role LIKE %?2%) AND r.role = ?1 AND r.role != 'ADMIN'")
	List<User> searchByRoleAndTerm(String role, String searchTerm);
    
    @Query("SELECT u FROM User u JOIN u.roles r WHERE (u.lastName LIKE %?1% OR u.firstName LIKE %?1% OR u.email LIKE %?1% or u.id LIKE %?1% OR r.role LIKE %?1%) AND r.role != 'ADMIN'")
	List<User> searchByRoleAdminAndTerm(String searchTerm);
    
//    @Query("SELECT u.firstName, u.lastName, u.email,  FROM User u JOIN u.appointments a WHERE u.id LIKE %?1% OR u.firstName LIKE %?1% OR u.email LIKE %?1% or u.id LIKE %?1% OR r.role LIKE %?1%")
//	public void searchByTermWithAppointments(long user_id, String searchTerm);
    
    @Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.id = ?2")
    @Modifying
    public void updateFailedAttempts(int failedAttempts, Long id);

}
