package com.cse545.hospitalSystem.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cse545.hospitalSystem.email.EmailService;
import com.cse545.hospitalSystem.services.OTPService;
import com.cse545.hospitalSystem.services.RegistrationService;

@Controller
@RequestMapping("api/otp")
public class OTPController {
    
    @Autowired
    private OTPService otpService;
    
    @Autowired
    private EmailService emailService;
    
    public static Logger logger = LoggerFactory.getLogger(OTPController.class);
    
    @GetMapping("/getotp")
    public ResponseEntity<String> getOtp(@RequestParam("email") String email){
        // TODO check if email is retrieved from security context vs request param
        int generatedOtp = otpService.generateOTP(email);
        logger.info("Generated OTP is: " + generatedOtp);
        try {
            emailService.sendOtpEmail(email, Integer.toString(generatedOtp));   
        } catch(Exception e) {
            return new ResponseEntity<>("Try again", HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<>("Sent OTP to your email", HttpStatus.OK);
    }
    
    @PostMapping("/confirmotp")
    public ResponseEntity<String> confirmOtp(@RequestParam("email")String email, @RequestParam("otp") String otp) {
       // TODO check if email is retrieved from security context vs request param 
       logger.info("inside confirm otp controller");
       boolean otpConfirmed = otpService.verifyOtp(email, otp);
       logger.info("is otp confirmed? {}", otpConfirmed);
       if(!otpConfirmed) {
           return new ResponseEntity<>("invalid otp", HttpStatus.NOT_ACCEPTABLE);
       }
       otpService.clearOTP(email);
       logger.info("at the end");
       return new ResponseEntity<>("authenticated", HttpStatus.OK);
    }
    
    

}
