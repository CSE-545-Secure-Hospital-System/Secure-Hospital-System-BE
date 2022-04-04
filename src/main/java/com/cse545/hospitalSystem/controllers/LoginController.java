package com.cse545.hospitalSystem.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;

import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.config.security.CustomLoginFailureHandler;
import com.cse545.hospitalSystem.config.security.CustomLoginSuccessHandler;
import com.cse545.hospitalSystem.config.security.JwtTokenProvider;
import com.cse545.hospitalSystem.models.Logs;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.AuthToken;
import com.cse545.hospitalSystem.models.ReqAndResp.LoginRequestDTO;
import com.cse545.hospitalSystem.repositories.LogsRepository;
import com.cse545.hospitalSystem.repositories.UserRepository;
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
	private UserRepository userRepo;
	
	@Autowired
	private LogsRepository logsRepo;
	
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private CustomLoginFailureHandler failureHandler;
    
    @Autowired
    private CustomLoginSuccessHandler successHandler;
    
    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) throws IOException, ServletException {
    	
        try {
    	final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		loginRequestDTO.getEmail(),
                		loginRequestDTO.getPassword()
                )
        );
    	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    	Optional<User> optional = userRepo.findByEmail(userDetails.getUsername());
    	User user = optional.get();
    	if(!user.isAccountNonLocked()) {
    	    if(!userService.unlockWhenTimeExpired(user)) {
    	        throw new LockedException("Your account has been locked due to 3 failed attempts."
                        + " It will be unlocked after 15 minutes with the right credentials."); 
    	    }
    	}
    	Logger logger = LogManager.getLogger(LoginController.class);
    	ZoneId zoneId = ZoneId.of("America/Phoenix");
        LocalTime localTime=LocalTime.now(zoneId);
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        //String formattedTime=localTime.format(formatter);
        LocalDate localDate = LocalDate.now();
    	//logger.info("logged in user " + loginRequestDTO.getEmail() + " at time " + formattedTime + " on date "+localDate);
    	Logs loginLog = new Logs(loginRequestDTO.getEmail(), localDate, localTime);
    	logsRepo.save(loginLog);
    	successHandler.onAuthenticationSuccess(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
        } catch(Exception e) {
            String newExceptionMessage = e.getMessage();
            try {
                failureHandler.onAuthenticationFailure(e, loginRequestDTO);
            } catch(Exception ex) {
                newExceptionMessage = newExceptionMessage + "-" + ex.getMessage();
            }
            return new ResponseEntity(new AuthToken(null, newExceptionMessage), HttpStatus.UNAUTHORIZED);
            
        }
        
    }
    
    

}
