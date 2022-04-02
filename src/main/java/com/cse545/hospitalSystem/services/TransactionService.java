package com.cse545.hospitalSystem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.enums.TransactionStatus;
import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.Bill;
import com.cse545.hospitalSystem.models.Transaction;
import com.cse545.hospitalSystem.repositories.AppointmentRepository;
import com.cse545.hospitalSystem.repositories.TransactionRepository;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepo;
    
    @Autowired
    private AppointmentRepository appointmentRepo;
    
    public Transaction generateTransaction(Bill bill, Appointment appointment) {
        Transaction transaction = new Transaction();
        transaction.setBill(bill);
        transaction.setAppointment(appointment);
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        return transactionRepo.save(transaction);
    }
    
    
    public Transaction updateTransactionStatusByAppointment(Appointment appointment, TransactionStatus status) {
        Transaction transaction = appointment.getTransaction();
        transaction.setTransactionStatus(status);
        return transactionRepo.save(transaction);
    }
    
    public Transaction updateTransactionStatus(Long transactionId, TransactionStatus status) {
        Optional<Transaction> optional = transactionRepo.findById(transactionId);
        if(!optional.isPresent()) return null;
        optional.get().setTransactionStatus(status);
        return transactionRepo.save(optional.get());
    }


}
