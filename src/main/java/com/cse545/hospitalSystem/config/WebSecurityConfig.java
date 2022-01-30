package com.cse545.hospitalSystem.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cse545.hospitalSystem.services.MyUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {
	
    // 	We need to secure our API’s by restricting which roles are able to execute
	//  This is achieved by adding the annotation @EnableGlobalMethodSecurity(prePostEnabled = true}
	
	//  AuthenticationManager validates if a given user has the right credentials
	
	@Autowired
    private MyUserDetailsService userDetailsService;

//    @Autowired
//    private UnauthorizedEntryPoint unauthorizedEntryPoint;
    
    // The AuthenticationManager needs to know where the user’s username and password have been stored,
    // we know it from AuthenticationManagerBuilder

    // This is for Authentication
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    
    
    // This is for Authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().and().csrf().disable() 
                .authorizeRequests()
                .antMatchers("/api/user/signUp").permitAll()
                .anyRequest().authenticated();

        // before going into controller enter to Filter
//        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

//    @Bean
//    public BCryptPasswordEncoder encoder(){
//        return new BCryptPasswordEncoder();
//    }
    
    @SuppressWarnings("deprecation")
	@Bean
    public PasswordEncoder passwordEncoder() {
    	return NoOpPasswordEncoder.getInstance();
    }
    
    // The AuthenticationManager we just configured is added to the Spring Application Context and is added 
    // as a bean by overriding the authecationManagerBean method.

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
//        return new JwtAuthenticationFilter();
//    }

}
