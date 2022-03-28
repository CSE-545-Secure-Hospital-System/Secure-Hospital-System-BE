package com.cse545.hospitalSystem.config.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.LockedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.LoginRequestDTO;
import com.cse545.hospitalSystem.repositories.UserRepository;
import com.cse545.hospitalSystem.services.UserService;

import org.springframework.security.core.AuthenticationException;
import java.io.IOException;
import java.util.Optional;

@Component
public class CustomLoginFailureHandler {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepo;

    public void onAuthenticationFailure(AuthenticationException exception, LoginRequestDTO request) throws IOException, LockedException, ServletException {

        String email = request.getEmail();
        Optional<User> userByEmail = userRepo.findByEmail(email);

        if (userByEmail.isPresent()) {
            User user = userByEmail.get();
            if (user != null) {
                if (user.isEnabled() && user.isAccountNonLocked()) {
                    if (user.getFailedAttempt() < userService.MAX_FAILED_ATTEMPTS - 1) {
                        userService.increaseFailedAttempts(user);
                    } else {
                        userService.lock(user);
                        exception = new LockedException("Your account has been locked due to 3 failed attempts."
                                + " It will be unlocked after 15 minutes.");
                        throw exception;
                    }
                } else if (!user.isAccountNonLocked()) {
                    if (userService.unlockWhenTimeExpired(user)) {
                        exception = new LockedException("Your account has been unlocked. Please try to login with right credentials again.");
                        throw exception;
                    }
                }
                 
            }
             
        }

    }
}