package com.cse545.hospitalSystem.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.config.security.JwtUtils;
import com.cse545.hospitalSystem.models.ERole;
import com.cse545.hospitalSystem.models.Role;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.RespAndReq.LoginJWTResponse;
import com.cse545.hospitalSystem.models.RespAndReq.LoginRequestDTO;
import com.cse545.hospitalSystem.models.RespAndReq.MessageResponse;
import com.cse545.hospitalSystem.models.security.UserDetailsImpl;
import com.cse545.hospitalSystem.repositories.RoleRepository;
import com.cse545.hospitalSystem.repositories.UserRepository;


@Service
public class AuthService {
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	public ResponseEntity<?> signUp(User user) {
		
//		ModelMapper modelMapper = new ModelMapper();
//		User user = modelMapper.map(userDTO, User.class);
		if (userRepository.existsByUserName(user.getUserName())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!", 0));
		}

		if (userRepository.existsByEmailId(user.getEmailId())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!", 0));
		}

		user.setPassword(encoder.encode(user.getPassword()));

		if(user.getRoles().isEmpty()) {
			Set<Role> roles = new HashSet<>();
			Role userRole = roleRepository.findByName(ERole.ROLE_PATIENT)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
			user.setRoles(roles);
		}
		
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!", 200));
	}

	public ResponseEntity<?> login(LoginRequestDTO loginRequestDTO) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserName(), loginRequestDTO.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new LoginJWTResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

}
