package com.cse545.hospitalSystem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.models.Role;
import com.cse545.hospitalSystem.repositories.RoleRepository;

@Service
public class RoleService {

	@Autowired
    private RoleRepository roleRepository;

    public Optional<Role> findByName(String name) {
        Optional<Role> role = roleRepository.findRoleByRole(name);
        return role;
    }
}
