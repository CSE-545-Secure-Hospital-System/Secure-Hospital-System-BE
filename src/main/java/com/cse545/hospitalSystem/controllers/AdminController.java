package com.cse545.hospitalSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cse545.hospitalSystem.services.AuthenticationService;
import com.cse545.hospitalSystem.services.UserService;

@Controller
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    AuthenticationService authenticationService;
    
    @Autowired
    UserService userService;
    

}
