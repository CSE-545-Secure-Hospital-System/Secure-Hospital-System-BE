package com.cse545.hospitalSystem.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.enums.TransactionStatus;
import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.GenericStatus;
import com.cse545.hospitalSystem.models.Insurance_Policies;
import com.cse545.hospitalSystem.models.PolicyClaim;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.ClaimCreateRequestDTO;
import com.cse545.hospitalSystem.models.ReqAndResp.PolicyClaimsRespDTO;
import com.cse545.hospitalSystem.models.ReqAndResp.TransactionCreateUpdateRequest;
import com.cse545.hospitalSystem.repositories.AppointmentRepository;
import com.cse545.hospitalSystem.repositories.ClaimRepository;
import com.cse545.hospitalSystem.repositories.InsuranacePoliciesRepo;
import com.cse545.hospitalSystem.repositories.UserRepository;

@Service
public class ClaimService {

	@Autowired
	private ClaimRepository claimRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private InsuranacePoliciesRepo insuranacePoliciesRepo;
	
	@Autowired
	private TransactionService transactionService;

	@Transactional
	public ResponseEntity<String> createClaim(ClaimCreateRequestDTO claimCreateRequestDTO) {
		if(claimCreateRequestDTO.getAppointmentId()!= null) {
			PolicyClaim c = claimRepository.findByAppointmentId(claimCreateRequestDTO.getAppointmentId());
			if(c!=null) {
				return ResponseEntity.ok("Already raised a Claim! Please wait for approval.");
			}else {
				try {
				User patient = userRepository.findById(claimCreateRequestDTO.getPatientId()).get();
				PolicyClaim c1 = new PolicyClaim(
						claimCreateRequestDTO.getPolicyId(),
						claimCreateRequestDTO.getPatientId(),
						claimCreateRequestDTO.getAppointmentId(),
						claimCreateRequestDTO.getAmountRequired(),
						TransactionStatus.PENDING
								);
				claimRepository.save(c1);
				patient.addClaim(c1);
				Appointment a = appointmentRepository.findById(claimCreateRequestDTO.getAppointmentId()).get();
				a.setStatus(GenericStatus.CLAIM_RAISED);
				appointmentRepository.save(a);
				userRepository.save(patient);
				} catch (Exception e) {
					return ResponseEntity.ok("Error Occured while creating the claim. Please try later");
				}
				
			}
		}
		return ResponseEntity.ok("Claim successfully created!");
	}

	public ResponseEntity<List<PolicyClaimsRespDTO>> getAllClaims() {
		List<PolicyClaim> claims = claimRepository.findAll();
		List<PolicyClaimsRespDTO> pres = new ArrayList<>();
		claims.forEach(claim -> {
			PolicyClaimsRespDTO p = new PolicyClaimsRespDTO();
			p.setAmountRequired(claim.getAmountRequired());
			p.setAppointmentId(claim.getAppointmentId());
			p.setClaimId(claim.getId());
			p.setPatientId(claim.getPatientId());
			p.setPolicy(insuranacePoliciesRepo.findById(claim.getPolicyId()).get());
			User patient = userRepository.findById(claim.getPatientId()).get();
			p.setPatientName(patient.getFirstName() + " " + patient.getLastName());
			p.setStatus(claim.getStatus());
			pres.add(p);
		});
		return ResponseEntity.ok(pres);
	}

	@Transactional
	public ResponseEntity<String> approveClaim(Long claimId) {
		PolicyClaim c = claimRepository.findById(claimId).get();
		try {
		if(c.getStatus().equals(TransactionStatus.APPROVED)) {
			return ResponseEntity.ok("Already Approved!");
		}else {
			c.setStatus(TransactionStatus.APPROVED);
			claimRepository.save(c);
			TransactionCreateUpdateRequest t = new TransactionCreateUpdateRequest();
			t.setAppointmentId(c.getAppointmentId());
			t.setPatientId(c.getPatientId());
			t.setTransactionStatus(TransactionStatus.PENDING);
			t.setClaimId(claimId);
			transactionService.createTransaction(t);
		}} catch (Exception e) {
			return ResponseEntity.ok("Error Occurred!");
		}
		return ResponseEntity.ok("Approved!");
	}

	@Transactional
	public ResponseEntity<String> rejectClaim(Long claimId) {
		PolicyClaim c = claimRepository.findById(claimId).get();
		try {
		if(c.getStatus().equals(TransactionStatus.REJECTED)) {
			return ResponseEntity.ok("Already Rejected!");
		}else {
			c.setStatus(TransactionStatus.REJECTED);
			claimRepository.save(c);
			TransactionCreateUpdateRequest t = new TransactionCreateUpdateRequest();
			t.setAppointmentId(c.getAppointmentId());
			t.setPatientId(c.getPatientId());
			t.setTransactionStatus(TransactionStatus.PENDING);
			t.setClaimId(claimId);
			transactionService.createTransaction(t);
		}} catch (Exception e) {
			return ResponseEntity.ok("Error Occurred!");
		}
		return ResponseEntity.ok("Rejected!");
	}
	

}
