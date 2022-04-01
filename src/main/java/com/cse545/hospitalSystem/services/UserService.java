package com.cse545.hospitalSystem.services;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.cse545.hospitalSystem.models.PolicyClaim;
import com.cse545.hospitalSystem.models.Role;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.AuthToken;
import com.cse545.hospitalSystem.models.ReqAndResp.UserReq;
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
    
    public static Logger logger = LoggerFactory.getLogger(UserService.class);
    

    @Autowired
    ConfirmationTokenService confirmationTokenService;
    
    
    private final static String USER_NOT_FOUND = "user with email % not found";
   
    public User createUser(User user){    
        return userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        // TODO Auto-generated method stub
        Optional<User> user = userRepo.findByEmail(email);
        if(!user.isPresent()) {
        	throw new UsernameNotFoundException("Invalid username or password.");
        }
        if(!user.get().isEnabled()) {
            throw new UsernameNotFoundException("Username has not been enabled");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), user.get().getAuthorities());
             
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

	public ResponseEntity<Set<User>> getAllUser(String searchTerm) {
		Set<User> users = new HashSet<>();
		if((searchTerm == null) || (searchTerm != null && searchTerm.length() == 0)) {
			List<User> allUsers = userRepo.findAll();
			for (User x : allUsers)
	            users.add(x);
			return ResponseEntity.ok(users);
		}
		String[] terms = searchTerm.split(" ");
		for(int i = 0; i < terms.length; i++) {
			userRepo.searchByTerm(terms[i]).forEach((user) -> {
				users.add(user);
			});
		}
		return ResponseEntity.ok(users);
	}

	public User getUserById(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if(user.isPresent()) {
			logger.info("getUserById API Call - Fetching the user - {}", user.get().getEmail());
			user.get().setPassword(null);
		}else {
			throw new IllegalStateException("User does not exist");
		}
		return user.get();
	}

	public ResponseEntity<User> getUserByEmailId(String emailId) {
		Optional<User> user = userRepo.findByEmail(emailId);
		if(user.isPresent()) {
			logger.info("getUserByEmailId API Call - Fetching the user details - {}", user.get().getEmail());
			user.get().setPassword(null);
		}else {
			throw new IllegalStateException("User does not exist");
		}
		return ResponseEntity.ok(user.get());
	}
	
	public User getUseEntityrByEmailId(String emailId) {
        Optional<User> user = userRepo.findByEmail(emailId);
        return user.get();
    }

	public ResponseEntity<String> updateUserByEmailId(UserReq user) {
		Optional<User> existinguser = this.isUserAlreadyExist(user.getEmail());
		if(existinguser == null) {
			return new ResponseEntity<String>("User is not Present in Database!", HttpStatus.NOT_FOUND);
		}
		if(user.getEmail() != null)
			existinguser.get().setEmail(user.getEmail());
		if(user.getFirstName() != null)
			existinguser.get().setFirstName(user.getFirstName());
		if(user.getLastName() != null)
			existinguser.get().setLastName(user.getLastName());
		if(user.getPassword() != null) {
			// encrypting the password
	        String encodedPassword = bCryptPasswordEncoder
	                .encode(user.getPassword());  
	        existinguser.get().setPassword(encodedPassword);
		}
		if(user.getPhone() != null)
			existinguser.get().setPhone(user.getPhone());
		if(user.getRoles() != null && user.getRoles().size() != 0) {
			existinguser.get().setRoles(user.getRoles());
		}
		userRepo.save(existinguser.get());
		return ResponseEntity.ok("Success");
	}

	public ResponseEntity<String> deleteUser(String emailId) {
		try {
			Optional<User> user = userRepo.findByEmail(emailId);
			if(user.isPresent()) {
				user.get().setEnabled(false);
//				userRepo.deleteById(user.get().getId());
				userRepo.save(user.get());
				return ResponseEntity.ok("Successfully blocked the account");
			}
		}catch (Exception e) {
			logger.error("Error in deleting the user!");
		}
		return new ResponseEntity<String>("Error in block the account!", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> activateAccount(String emailId) {
		try {
			Optional<User> user = userRepo.findByEmail(emailId);
			if(user.isPresent()) {
				user.get().setEnabled(true);
//				userRepo.deleteById(user.get().getId());
				userRepo.save(user.get());
				return ResponseEntity.ok("Successfully activated the account");
			}
		}catch (Exception e) {
			logger.error("Error in deleting the user!");
		}
		return new ResponseEntity<String>("Error in activating the account!", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> createClaim(PolicyClaim policyClaim) {
		return null;
	}

	public ResponseEntity<List<User>> getAllUserByRole(String role) {
		List<User> users;
		if(role != null && role.length() > 0)
			users = userRepo.searchByRole(role);
		else
			users = userRepo.findALLExecptAdmins();
		return new ResponseEntity(users, HttpStatus.OK);
	}
	
	public boolean setNewPassword(String email, String password) {
	 // encrypting the password
	    Optional<User> user = userRepo.findByEmail(email);
	    if(!user.isPresent()) return false;
        String encodedPassword = bCryptPasswordEncoder
                .encode(password);     
        user.get().setPassword(encodedPassword);
        userRepo.save(user.get());
        return true;
	}
  
    
}

