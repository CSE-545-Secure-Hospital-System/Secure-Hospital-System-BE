package com.cse545.hospitalSystem.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    
    @RequestMapping(value="/getTransaction/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity<Transaction> getTransactions(@RequestParam(name = "transactionId") Long transactionId) {
        Optional<Transaction> optional = transactionRepo.findById(transactionId);
        Transaction transaction = optional.get();
        return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
    }
    
    @RequestMapping(value="/updateTransactionStatus/{transactionId}", method = RequestMethod.POST)
    public ResponseEntity<List<Transaction>> updateTransactionStatus(@RequestParam(name = "transactionId") Long transactionId, TransactionStatus status) {
        transactionService.updateTransactionStatus(transactionId, status);
        List<Transaction> transactions = transactionRepo.findAll();
        return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
    }
    
    @PostMapping("/createTransaction")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionCreateUpdateRequest transactionCreateUpdateRequest){
    	return transactionService.createTransaction(transactionCreateUpdateRequest);
    }

}
