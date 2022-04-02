package com.cse545.hospitalSystem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.enums.TransactionStatus;
import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.Bill;
import com.cse545.hospitalSystem.models.Transaction;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.TransactionCreateUpdateRequest;
import com.cse545.hospitalSystem.repositories.AppointmentRepository;
import com.cse545.hospitalSystem.repositories.BillRepository;
import com.cse545.hospitalSystem.repositories.TransactionRepository;
import com.cse545.hospitalSystem.repositories.UserRepository;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepo;
    
    @Autowired
    private AppointmentRepository appointmentRepo;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BillRepository billRepository;
    
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


	public ResponseEntity<String> createTransaction(TransactionCreateUpdateRequest transactionCreateUpdateRequest) {
		if(transactionCreateUpdateRequest.getAppointmentId()!=null) {
			Transaction t1 = transactionRepo.findByAppointmentById(transactionCreateUpdateRequest.getAppointmentId());
			if(t1!=null && t1.getTransactionStatus().equals(TransactionStatus.APPROVED)) {
				return ResponseEntity.ok("Bill paid already!");
			}else {
				try {
				Transaction t2 = new Transaction();
				Appointment a = appointmentRepo.findById(transactionCreateUpdateRequest.getAppointmentId()).get();
				User patient = userRepository.findById(transactionCreateUpdateRequest.getPatientId()).get();
				Bill b = billRepository.findByAppointmentId(transactionCreateUpdateRequest.getAppointmentId()).get();
				t2.setAppointment(a);
				t2.setBill(b);
				t2.setPatient(patient);
				transactionRepo.save(t2);
				patient.addTransaction(t2);
				userRepository.save(patient);
				a.setTransaction(t2);
				appointmentRepo.save(a);
				} catch (Exception e) {
					return ResponseEntity.ok("Some error has occured!");
				}
			}
		}
		return ResponseEntity.ok("Successfully intitated the transaction!");
	}


}
