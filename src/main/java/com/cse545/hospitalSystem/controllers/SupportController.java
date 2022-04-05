package com.cse545.hospitalSystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.Support;
import com.cse545.hospitalSystem.models.ReqAndResp.ClaimCreateRequestDTO;
import com.cse545.hospitalSystem.models.ReqAndResp.SupportRequestDTO;
import com.cse545.hospitalSystem.repositories.SupportRepository;

@RestController
@RequestMapping("/api/support")
public class SupportController {
    
    @Autowired
    private SupportRepository supportRepo;
    
    @CrossOrigin
    @PostMapping("/createSupportRequest")
    @PreAuthorize("hasAnyRole('PATIENT')")
    public ResponseEntity<String> createSupportRequest(@RequestBody SupportRequestDTO supportRequestDTO){
        Support support = new Support();
        support.setComment(supportRequestDTO.getHelpComment());
        support.setEmail(supportRequestDTO.getEmail());
        Support savedSupport = supportRepo.save(support);
        return new ResponseEntity<>("Succesfully created help request", HttpStatus.OK);
    }
    
    @CrossOrigin
    @GetMapping("/getSupportRequests")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<Support>> getSupportRequest(){
        List<Support> savedSupport = supportRepo.findAll();
        return new ResponseEntity<>(savedSupport, HttpStatus.OK);
    }

}
