package com.cse545.hospitalSystem.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.email.EmailService;
import com.cse545.hospitalSystem.forms.RegistrationRequest;
import com.cse545.hospitalSystem.services.RegistrationService;

@RestController
@RequestMapping(path="api/auth")
public class RegistrationController {
    
    private final static Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    RegistrationService registrationService;
    
    @CrossOrigin
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String register(@RequestBody RegistrationRequest request) {
        logger.info("inside registration controller register method");
        return registrationService.register(request);
    }
    
    @CrossOrigin
    @RequestMapping(path = "/confirm", method = RequestMethod.GET)
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}
