package com.cse545.hospitalSystem.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.repositories.UserRepository;
import com.cse545.hospitalSystem.security.LoggerConfig;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    private final static String USER_NOT_FOUND = "user with email % not found";
   
    public User createUser(User user){    
        return userRepo.save(user);
    }
    
    public User signIn(User user) {
        
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        return userRepo.findByEmail(email)
                .orElseThrow(()->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }
    
    public String signUpUser(User user) {
        
        boolean userExists = userRepo.findByEmail(user.getEmail())
                .isPresent();
        
        if(userExists) {
            throw new IllegalStateException("Email already exists");
        }
        
        String encodedPassword = bCryptPasswordEncoder
                .encode(user.getPassword());     
        user.setPassword(encodedPassword);
        
        userRepo.save(user);
        
        
        //TODO: send confirmation token
        return "works";
    }
  
    
}

