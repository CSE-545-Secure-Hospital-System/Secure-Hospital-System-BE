package com.cse545.hospitalSystem.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.repositories.UserRepository;
import com.cse545.hospitalSystem.services.UserService;

@Component
public class CustomLoginSuccessHandler {
 
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepo;
    public void onAuthenticationSuccess(
            Authentication authentication) throws IOException, ServletException {
        
        UserDetails userDetails =  (UserDetails) authentication.getPrincipal();
        Optional<User> optional = userRepo.findByEmail(userDetails.getUsername());
        User user = optional.get();
        if(user.isCredentialsNonExpired())
        if (user.getFailedAttempt() > 0) {
            userService.resetFailedAttempts(user.getId());
        }
    }
     
}
