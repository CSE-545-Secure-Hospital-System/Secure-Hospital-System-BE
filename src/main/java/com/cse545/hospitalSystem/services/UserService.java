package com.cse545.hospitalSystem.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.config.LoggerConfig;
import com.cse545.hospitalSystem.enums.RoleMapping;
import com.cse545.hospitalSystem.models.ConfirmationToken;
import com.cse545.hospitalSystem.models.Role;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.repositories.RoleRepository;
import com.cse545.hospitalSystem.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private RoleRepository roleRepo;
    
    public static Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    ConfirmationTokenService confirmationTokenService;
    
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
        logger.info("email is {}", user.getEmail());
        boolean userExists = userRepo.findByEmail(user.getEmail())
                .isPresent();
        
        logger.info("user exists? {}", userExists);
        
        if(userExists) {
            throw new IllegalStateException("Email already exists");
        }
        
        String encodedPassword = bCryptPasswordEncoder
                .encode(user.getPassword());     
        user.setPassword(encodedPassword);
        
        userRepo.save(user);
        
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5),
                user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        
        //TODO send email
        return token;
    }
    
    public void enableUser(String email) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(!optionalUser.isPresent()) {
            throw new IllegalStateException("User does not exist");
        }
        User user = optionalUser.get();
        user.setEnabled(true);
        userRepo.save(user);
        return;
    }

    public void updateRole(String email, Long userRole) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if(!userOptional.isPresent()) {
            throw new IllegalStateException("User does not exist, "
                    + "cannot update role");
        }
        Optional<Role> role = roleRepo.findById(userRole);
        User user = userOptional.get();
        if(!role.isPresent()) {
            throw new IllegalStateException("Requested role does not exist");
        }
        user.setRole(role.get());
        userRepo.save(user);
        return;  
        
    }
  
    
}

