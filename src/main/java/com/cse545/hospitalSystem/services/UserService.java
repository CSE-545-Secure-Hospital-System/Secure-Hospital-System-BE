package com.cse545.hospitalSystem.services;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.config.LoggerConfig;
import com.cse545.hospitalSystem.config.security.JwtTokenProvider;
import com.cse545.hospitalSystem.enums.RoleMapping;
import com.cse545.hospitalSystem.models.ConfirmationToken;
import com.cse545.hospitalSystem.models.Role;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.AuthToken;
import com.cse545.hospitalSystem.repositories.RoleRepository;
import com.cse545.hospitalSystem.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private RoleRepository roleRepo;
    
    public static Logger logger = LoggerFactory.getLogger(LoggerConfig.class);
    

    @Autowired
    ConfirmationTokenService confirmationTokenService;
    
    
    private final static String USER_NOT_FOUND = "user with email % not found";
   
    public User createUser(User user){    
        return userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User user = userRepo.findByEmail(email).get();
        if(user == null) {
        	throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
             
    }
    
    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        return (Set<SimpleGrantedAuthority>) user.getAuthorities();
    }
    
    public String signUpUser(User user) {
    	// DB - email existence check
        
        if(isUserAlreadyExist(user.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already exists");
        }
        
        // encrypting the password
        String encodedPassword = bCryptPasswordEncoder
                .encode(user.getPassword());     
        user.setPassword(encodedPassword);
        
        userRepo.save(user);
        
        // Creating a Token
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5),
                user);
        
        // Saving token in DB
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        
        //TODO send email
        return token;
    }
    
    public void enableUser(String email) {
        Optional<User> optionalUser = isUserAlreadyExist(email);
        if(!optionalUser.isPresent()) {
            throw new IllegalStateException("User does not exist");
        }
        User user = optionalUser.get();
        user.setEnabled(true);
        userRepo.save(user);
        return;
    }
    
    public Optional<User> isUserAlreadyExist(String email){
    	// DB - email existence check
    	logger.info("email is {}", email);
    	Optional<User> user = userRepo.findByEmail(email);
        logger.info("user exists? - {}", user.isPresent());
    	return user;
    }

	public List<User> getAllUser() {
		logger.info("Admin accessing all users");
		List<User> users = userRepo.findAll();
		logger.info(users.get(0).getFirstName());
		return users;
	}

	public User getUserById(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if(user.isPresent()) {
			logger.info("Fetching the user - {}", user.get().getEmail());
			user.get().setPassword(null);
		}else {
			throw new IllegalStateException("User does not exist");
		}
		return user.get();
	}
  
    
}

