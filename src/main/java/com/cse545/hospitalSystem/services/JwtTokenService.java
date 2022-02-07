package com.cse545.hospitalSystem.services;

import java.io.Serializable;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function; 
import java.util.stream.Collectors;

@Service
public class JwtTokenService implements Serializable {

	@Value("${jwt.token.validity}")
    public long TOKEN_VALIDITY;

    @Value("${jwt.signing.key}")
    public String SIGNING_KEY;

    @Value("${jwt.authorities.key}")
    public String AUTHORITIES_KEY;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    // Spring Security Context holds the information of an authenticated user represented as an Authentication object. 
    // In order to construct this Authentication object, we need to provide a UsernamePasswordAuthenticationToken which 
    // will later be used by our AuthenticationManager (Which we configured previously) to Authenticate our user.
    
    // Generates token
    public String generateToken(UserDetails userDetails) {
         Map<String, Object> claims = new HashMap<>();
         claims.put("emailId", userDetails.getUsername());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }
    
    
    // validateToken basically checks if the username on the token payload matches the UserDetails. 
    // It also checks if the token has expired.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

//    UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final Authentication existingAuth, final UserDetails userDetails) {
//
//        final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);
//
//        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
//
//        final Claims claims = claimsJws.getBody();
//
//        final Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
//
//        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
//    }
}
