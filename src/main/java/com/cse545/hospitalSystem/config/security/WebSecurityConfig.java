package com.cse545.hospitalSystem.config.security;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cse545.hospitalSystem.services.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {

    @Autowired
    private UserService userService;
    
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider
                 = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return  provider;
    }

    // This is for Authentication
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    
    
    // This is for Authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().and().csrf().disable() 
                .authorizeRequests()
                .antMatchers("/api/registration/**").permitAll()
                .anyRequest().authenticated().and()
                .formLogin();

        // before going into controller enter to Filter
//        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
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
