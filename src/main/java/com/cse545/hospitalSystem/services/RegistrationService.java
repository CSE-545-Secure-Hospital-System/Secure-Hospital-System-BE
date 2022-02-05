package com.cse545.hospitalSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.forms.RegistrationRequest;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.security.UserService;

@Service
public class RegistrationService {
    
    @Autowired
    private EmailValidatorService emailValidator;
    
    @Autowired
    private UserService userService;
    
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail) {
            throw new IllegalStateException("Email is not valid");
        }
        return userService.signUpUser(new User(request.getFirstName(), request.getLastName(),
                request.getEmail(), request.getPassword()));
    }
}
