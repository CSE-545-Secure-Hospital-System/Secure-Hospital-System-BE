package com.cse545.hospitalSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.config.LoggerConfig;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.repositories.UserRepository;

@Service
public class AuthenticationService extends LoggerConfig{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired
	private UserRepository userRepo;

	public ResponseEntity<String> signUp(User user){
		
//		UserJwt userJwt = new UserJwt();
//		try {
//			authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(user.getEmailId(), user.getPassword())
//						);
//		}catch(BadCredentialsException bd){
//			logger.error("Bad credentials: " + bd.getMessage(), bd);
//			return new ResponseEntity<UserJwt>(userJwt, HttpStatus.UNAUTHORIZED);
//		}
//		
//		final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmailId());
//		String token = jwtTokenService.generateToken(userDetails);
//		userJwt.setJwtToken(token);
		logger.info("user email is {}", user.getEmail());
		userRepo.save(user);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	public User authenticateSignIn(User user) {
	    //TODO validate that db credentials and user credentials are the same
	    //TODO throw exceptions
	    return user;
	}
}
