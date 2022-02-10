package com.cse545.hospitalSystem.services;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cse545.hospitalSystem.config.LoggerConfig;
import com.cse545.hospitalSystem.enums.RoleMapping;
import com.cse545.hospitalSystem.forms.RegistrationRequest;
import com.cse545.hospitalSystem.models.ConfirmationToken;
import com.cse545.hospitalSystem.models.User;

@Service
public class RegistrationService {
    
    @Autowired
    private EmailValidatorService emailValidator;
    
    @Autowired
    private UserService userService;
    
    public static Logger logger = LoggerFactory.getLogger(LoggerConfig.class);
    
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail) {
            throw new IllegalStateException("Email is not valid");
        }
        return userService.signUpUser(new User(request.getFirstName(), request.getLastName(),
                request.getEmail(), request.getPassword()));
    }
    
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        // We are not expiring the token for simplicity
        
        
//        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
//
//        if (expiredAt.isBefore(LocalDateTime.now())) {
//            throw new IllegalStateException("token expired");
//        }

        confirmationTokenService.setConfirmedAt(token);
        logger.info("user is {}",  confirmationToken.getUser().getEmail());
        userService.enableUser(
                confirmationToken.getUser().getEmail());
        long roleMapping = 3;
        userService.updateRole(confirmationToken.getUser().getEmail(), roleMapping);
        return "confirmed";
    }
}
