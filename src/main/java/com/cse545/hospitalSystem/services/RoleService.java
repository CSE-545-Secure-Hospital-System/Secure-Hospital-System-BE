package com.cse545.hospitalSystem.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.models.Role;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.repositories.RoleRepository;

@Service
public class RoleService {

	@Autowired
    private RoleRepository roleRepository;

    public Optional<Role> findByName(String name) {
        Optional<Role> role = roleRepository.findRoleByRole(name);
        return role;
    }
    
    public boolean findUserRole(User user, String role) {
        boolean verifiedRole =user.getRoles().stream().anyMatch(r -> 
            r.equals(role)
        );
        return verifiedRole;
    }
}
