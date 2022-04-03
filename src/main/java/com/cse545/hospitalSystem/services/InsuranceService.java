package com.cse545.hospitalSystem.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.config.LoggerConfig;
import com.cse545.hospitalSystem.enums.PolicyTypes;
import com.cse545.hospitalSystem.models.Coverage;
import com.cse545.hospitalSystem.models.Insurance_Policies;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.Policy;
import com.cse545.hospitalSystem.repositories.CoveragesRepository;
import com.cse545.hospitalSystem.repositories.InsuranacePoliciesRepo;
import com.cse545.hospitalSystem.repositories.UserRepository;

import net.bytebuddy.implementation.bind.annotation.Super.Instantiation;

@Service
public class InsuranceService {
	
	@Autowired
	private InsuranacePoliciesRepo insurancePoliciesRepo;
	
	@Autowired
	private CoveragesRepository coveragesRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private InsuranacePoliciesRepo insuranacePoliciesRepo;
	
	
	public static org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerConfig.class);
	
	
	public ResponseEntity<String> createPolicy(Policy insurancePolicy) {
		try {
			Insurance_Policies policy = new Insurance_Policies();
			Set<Coverage> coverages = new HashSet<Coverage>();
			insurancePolicy.getCoverages().forEach((coverage) -> {
				Optional<Coverage> c = coveragesRepository.findByCoverageName(coverage);
				if(c.isPresent())
					coverages.add(c.get());
			});
			policy.setCoverages(coverages);
			policy.setInsuranceProviderName(insurancePolicy.getInsuranceProviderName());
			policy.setPolicyName(insurancePolicy.getPolicyName());
			policy.setPolicyType(insurancePolicy.getPolicyType());
			policy.setPolicyClaimMaximumAmt(insurancePolicy.getPolicyClaimMaximumAmt());
			insurancePoliciesRepo.save(policy);
			return ResponseEntity.ok("Success");
		}catch (Exception e) {
			logger.error("Error occured while creating a Policy" + e.getMessage());
		}
		return new ResponseEntity<String>("Error while creating the Policy", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<List<String>> getPolicyTypes() {
		 PolicyTypes[] policyTypes = PolicyTypes.values();
		logger.info(policyTypes.toString());
		List<String> p = new ArrayList<>();
		for(int i = 0; i < policyTypes.length; i++) {
			p.add(policyTypes[i].toString());
		}
		return new ResponseEntity<List<String>>(p, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<List<Insurance_Policies>> getAllPolicies() {
		List<Insurance_Policies> policies = insurancePoliciesRepo.findAll();
		return new ResponseEntity<List<Insurance_Policies>>(policies, HttpStatus.OK);
	}

	public ResponseEntity<String> addPolicyToUser(long patientId, long policyId) {
		User patient = userRepository.findById(patientId).get();
		Insurance_Policies policy = insuranacePoliciesRepo.findById(policyId).get();
		for (Insurance_Policies p : patient.getPolicies()) {
			if(policy.getId() == p.getId()) {
				return ResponseEntity.ok("Already this policy is Taken!");
			}
		}
		patient.addPolicy(policy);
		userRepository.save(patient);
		return ResponseEntity.ok("Congratuation Policy is taken!");
	}

	public ResponseEntity<Set<Insurance_Policies>> getAllPoliciesByuserId(Long patientId) {
		User patient = userRepository.findById(patientId).get();
		return ResponseEntity.ok(patient.getPolicies());
	}


}
