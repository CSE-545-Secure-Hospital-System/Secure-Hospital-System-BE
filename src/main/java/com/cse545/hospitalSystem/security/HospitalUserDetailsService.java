package com.cse545.hospitalSystem.security;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import com.cse545.hospitalSystem.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.repositories.UserRepository;

@Service
public class HospitalUserDetailsService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;
    
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    
	    Optional<User> user = userRepository.findByEmail(email);
	    if(user == null || !user.isPresent()) {
	        throw new UsernameNotFoundException("User was not found");
	    }
		// TODO Auto-generated method stub
		// return a static userName and password, but we need to fetch them from DB in real time
	    return new HospitalUserDetails(user.get());
	}

}
