package com.cse545.hospitalSystem.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.enums.TransactionStatus;
import com.cse545.hospitalSystem.models.LabTest;
import com.cse545.hospitalSystem.models.Transaction;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.TransactionCreateUpdateRequest;
import com.cse545.hospitalSystem.models.ReqAndResp.TransactionRespDTO;
import com.cse545.hospitalSystem.repositories.TransactionRepository;
import com.cse545.hospitalSystem.services.TransactionService;

@RestController
@RequestMapping(path="api/transaction")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepo;
    
    @Autowired
    private TransactionService transactionService;
    
    @CrossOrigin
    @RequestMapping(value="/getAllTransactions", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('PATIENT', 'LAB_STAFF', 'DOCTOR', 'INSURANCE_STAFF', 'ADMIN', 'HOSPITAL_STAFF')")
    public ResponseEntity<List<TransactionRespDTO>> getAllTransactions(@RequestParam(required = false) Long patientId ) {
        return transactionService.getAllTransactions(patientId);
    }
    
    @CrossOrigin
    @PostMapping("/createTransaction")
    @PreAuthorize("hasAnyRole('PATIENT')")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionCreateUpdateRequest transactionCreateUpdateRequest){
    	return transactionService.createTransaction(transactionCreateUpdateRequest);
    }
    
    @CrossOrigin
    @RequestMapping(value="/approveTransaction", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('HOSPITAL_STAFF', 'ADMIN')")
    public ResponseEntity<String> approveTransaction(@RequestParam Long staffId, @RequestParam Long transactionId, @RequestParam Long appointmentId) {
        return transactionService.approveTransaction(staffId, transactionId, appointmentId);
    }
    
    @CrossOrigin
    @RequestMapping(value="/cancelTransaction", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ADMIN', 'HOSPITAL_STAFF')")
    public ResponseEntity<String> cancelTransaction(@RequestParam Long staffId, @RequestParam Long transactionId, @RequestParam Long appointmentId) {
        return transactionService.cancelTransaction(staffId, transactionId, appointmentId);
    }

}
