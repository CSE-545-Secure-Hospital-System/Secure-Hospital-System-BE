package com.cse545.hospitalSystem.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TransactionRespDTO>> getAllTransactions() {
        return transactionService.getAllTransactions();
    }
    
    @CrossOrigin
    @PostMapping("/createTransaction")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionCreateUpdateRequest transactionCreateUpdateRequest){
    	return transactionService.createTransaction(transactionCreateUpdateRequest);
    }
    
    @CrossOrigin
    @RequestMapping(value="/approveTransaction", method = RequestMethod.POST)
    public ResponseEntity<String> approveTransaction(@RequestParam Long staffId, @RequestParam Long transactionId, @RequestParam Long appointmentId) {
        return transactionService.approveTransaction(staffId, transactionId, appointmentId);
    }
    
    @CrossOrigin
    @RequestMapping(value="/cancelTransaction", method = RequestMethod.POST)
    public ResponseEntity<String> cancelTransaction(@RequestParam Long staffId, @RequestParam Long transactionId, @RequestParam Long appointmentId) {
        return transactionService.cancelTransaction(staffId, transactionId, appointmentId);
    }

}
