package com.cse545.hospitalSystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.enums.TransactionStatus;
import com.cse545.hospitalSystem.models.LabTest;
import com.cse545.hospitalSystem.models.Transaction;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.repositories.TransactionRepository;
import com.cse545.hospitalSystem.services.TransactionService;

@RestController
@RequestMapping(path="api/transaction")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepo;
    
    @Autowired
    private TransactionService transactionService;
    
    @RequestMapping(value="/getAllTransactions", method = RequestMethod.GET)
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionRepo.findAll();
        return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
    }
    
    @RequestMapping(value="/updateTransactionStatus/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity<List<Transaction>> updateTransactionStatus(@RequestParam(name = "transactionId") Long transactionId) {
        transactionService.updateTransactionStatus(transactionId, TransactionStatus.COMPLETED);
        List<Transaction> transactions = transactionRepo.findAll();
        return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
    }

}
