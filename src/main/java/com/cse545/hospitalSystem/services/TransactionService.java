package com.cse545.hospitalSystem.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.enums.BillStatus;
import com.cse545.hospitalSystem.enums.RoleMapping;
import com.cse545.hospitalSystem.enums.TransactionStatus;
import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.Bill;
import com.cse545.hospitalSystem.models.GenericStatus;
import com.cse545.hospitalSystem.models.PolicyClaim;
import com.cse545.hospitalSystem.models.Transaction;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.TransactionCreateUpdateRequest;
import com.cse545.hospitalSystem.models.ReqAndResp.TransactionRespDTO;
import com.cse545.hospitalSystem.repositories.AppointmentRepository;
import com.cse545.hospitalSystem.repositories.BillRepository;
import com.cse545.hospitalSystem.repositories.ClaimRepository;
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
    
    @Autowired
    private ClaimRepository claimRepository;
    
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
			if(t1!=null) {
				return ResponseEntity.ok("Bill paid already!");
			}else {
				try {
				Transaction t2 = new Transaction();
				Appointment a = appointmentRepo.findById(transactionCreateUpdateRequest.getAppointmentId()).get();
				User patient = userRepository.findById(transactionCreateUpdateRequest.getPatientId()).get();
				Bill b = billRepository.findByAppointmentId(transactionCreateUpdateRequest.getAppointmentId()).get();
				if(transactionCreateUpdateRequest.getClaimId() != null) {
					PolicyClaim c = claimRepository.findById(transactionCreateUpdateRequest.getClaimId()).get();
					t2.setClaim(c);
				}
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			    Date date = new Date();
			    t2.setTransactionCompletionTime(dateFormat.format(date));
				t2.setAppointment(a);
				t2.setTransactionStatus(TransactionStatus.PENDING);
				t2.setBill(b);
				t2.setPatient(patient);
				transactionRepo.save(t2);
				patient.addTransaction(t2);
				userRepository.save(patient);
				a.setTransaction(t2);
				a.setStatus(GenericStatus.PAYMENT_AUTHORIZED);
				appointmentRepo.save(a);
				} catch (Exception e) {
					return ResponseEntity.ok("Some error has occured!");
				}
			}
		}
		return ResponseEntity.ok("Successfully intitated the transaction!");
	}


	public ResponseEntity<List<TransactionRespDTO>> getAllTransactions() {
		List<Transaction> t = transactionRepo.findAll();

		List<TransactionRespDTO> t1 = new ArrayList<>();
		t.forEach(tran -> {
			TransactionRespDTO te = new TransactionRespDTO();
			if(tran.getAppointment() != null)
				te.setAppointmentId(tran.getAppointment().getId());
			te.setTransactionId(tran.getId());
			te.setBill(tran.getBill());
			if(tran.getClaim() != null)
				te.setClaimId(tran.getClaim().getId());
			User patient = tran.getPatient();
			if(patient != null) {
				te.setPatientId(patient.getId());
				te.setPatientName(patient.getFirstName() + " " + patient.getLastName());
			}
			User staf = tran.getStaff();
			if(staf != null) {
				te.setStaffId(staf.getId());
				te.setStaffName(staf.getFirstName() + " " + staf.getLastName());
			}
			te.setTransactionCompletionTime(tran.getTransactionCompletionTime());
			te.setTransactionStatus(tran.getTransactionStatus());
			t1.add(te);
		});
		return ResponseEntity.ok(t1);
	}


	@Transactional
	public ResponseEntity<String> approveTransaction(Long staffId, Long transactionId, Long appointmentId) {
		try {
			User staff = userRepository.findById(staffId).get();
			Appointment a = appointmentRepo.findById(appointmentId).get();
			Transaction t = transactionRepo.findById(transactionId).get();
			Bill b = t.getBill();
			t.setStaff(staff);
			t.setTransactionStatus(TransactionStatus.APPROVED);
			b.setBillStatus(BillStatus.SETTLED);
			a.setStatus(GenericStatus.COMPLETED);
			billRepository.save(b);
			transactionRepo.save(t);
		}catch(Exception e) {
			return ResponseEntity.ok("Error while approving the transaction!");
		}
		return ResponseEntity.ok("Transaction is complete!");
	}


	public ResponseEntity<String> cancelTransaction(Long staffId, Long transactionId, Long appointmentId) {
		try {
			User staff = userRepository.findById(staffId).get();
			Appointment a = appointmentRepo.findById(appointmentId).get();
			Transaction t = transactionRepo.findById(transactionId).get();
			t.setStaff(staff);
			t.setTransactionStatus(TransactionStatus.REJECTED);
			transactionRepo.save(t);
		}catch(Exception e) {
			return ResponseEntity.ok("Error while approving the transaction!");
		}
		return ResponseEntity.ok("Transaction is Rejected!");
	}


}
