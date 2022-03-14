package com.cse545.hospitalSystem.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cse545.hospitalSystem.services.UserService;

import io.jsonwebtoken.*;

public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String header = request.getHeader(HEADER_STRING);
		String email = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
        	authToken = header.replace(TOKEN_PREFIX,"");
        	try {
                email = jwtTokenProvider.getUserNameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("An error occurred while fetching email from Token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("The token has expired", e);
            } catch(SignatureException e){
                logger.error("Authentication Failed. Username or Password not valid.");
            }
        	
        }else {
            logger.warn("Couldn't find bearer string, header will be ignored");
        }
        
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	UserDetails userDetails = userService.loadUserByUsername(email);
        	if (jwtTokenProvider.validateToken(authToken, userDetails)) {
        		UsernamePasswordAuthenticationToken authentication = jwtTokenProvider.getAuthenticationToken(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + email + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
        	}	
        }
 
		filterChain.doFilter(request, response);
	}
    
    
}