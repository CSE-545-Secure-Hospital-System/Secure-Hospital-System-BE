package com.cse545.hospitalSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.repositories.UserRepository;
import com.cse545.hospitalSystem.config.LoggerConfig;

@Service
public class UserService extends LoggerConfig {
   
    @Autowired
    private AuthenticationService authenticationService;
    
    private UserRepository userRepo;
   
    public User createUser(User user){    
        logger.info("user email is {}", user.getEmail());
        return userRepo.save(user);
    }
    
    public User signIn(User user) {
        //TODO handle exceptions
        authenticationService.authenticateSignIn(user);
        
        return user;
    }
    
}
