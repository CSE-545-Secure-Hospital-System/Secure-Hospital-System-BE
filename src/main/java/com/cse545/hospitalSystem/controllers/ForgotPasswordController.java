package com.cse545.hospitalSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.email.EmailService;
import com.cse545.hospitalSystem.models.ReqAndResp.ForgotPasswordRequestDTO;
import com.cse545.hospitalSystem.models.ReqAndResp.NewPasswordRequestDTO;
import com.cse545.hospitalSystem.services.OTPService;
import com.cse545.hospitalSystem.services.UserService;

@RestController
@RequestMapping(path="api/auth")
public class ForgotPasswordController {
    
    @Autowired
    private OTPService otpService;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/forgotpassword")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequestDTO forgotPasswordRequestDTO) {
        Integer otp = otpService.generateOTP(forgotPasswordRequestDTO.getEmail());
        emailService.sendOtpEmail(forgotPasswordRequestDTO.getEmail(), otp.toString());
        //should be redirect instead of otp
        return new ResponseEntity(otp, HttpStatus.OK);
    }
    
    @PostMapping("/forgotpassword/confirmotp")
    public ResponseEntity<?> confirmOtpForForgotPassword(@RequestBody NewPasswordRequestDTO newPasswordRequesDTO) {
        //should be redirect instead of otp
        boolean verified = otpService.verifyOtp(newPasswordRequesDTO.getEmail(), newPasswordRequesDTO.getOtp());
        if(!verified) {
            return new ResponseEntity("wrong otp try again", HttpStatus.BAD_REQUEST);
        }
        boolean passwordGenerated = userService.setNewPassword(newPasswordRequesDTO.getEmail(), newPasswordRequesDTO.getPassword());
        if(!passwordGenerated) {
            return new ResponseEntity("Password not generated, check if user exists", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity("new password generated, login again", HttpStatus.OK);
    }

}
