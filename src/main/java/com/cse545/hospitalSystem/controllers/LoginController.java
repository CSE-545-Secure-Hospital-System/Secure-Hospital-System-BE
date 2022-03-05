package com.cse545.hospitalSystem.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.config.security.JwtTokenProvider;
import com.cse545.hospitalSystem.models.Logs;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.AuthToken;
import com.cse545.hospitalSystem.models.ReqAndResp.LoginRequestDTO;
import com.cse545.hospitalSystem.repositories.LogsRepository;
import com.cse545.hospitalSystem.services.UserService;

/*
 * 
 * Should be used only for Login and related functionalities
 * 
 */

@RestController
@RequestMapping(path="api/auth")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LogsRepository logsRepo;
	
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
    	
        try {
    	final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		loginRequestDTO.getEmail(),
                		loginRequestDTO.getPassword()
                )
                
        );
    	
    	Logger logger = LogManager.getLogger(LoginController.class);
    	ZoneId zoneId = ZoneId.of("America/Phoenix");
        LocalTime localTime=LocalTime.now(zoneId);
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        //String formattedTime=localTime.format(formatter);
        LocalDate localDate = LocalDate.now();
    	//logger.info("logged in user " + loginRequestDTO.getEmail() + " at time " + formattedTime + " on date "+localDate);
    	Logs loginLog = new Logs(loginRequestDTO.getEmail(), localDate, localTime);
    	logsRepo.save(loginLog);
    	
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
        } catch(UsernameNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
            
        }
    }
    
    

}
