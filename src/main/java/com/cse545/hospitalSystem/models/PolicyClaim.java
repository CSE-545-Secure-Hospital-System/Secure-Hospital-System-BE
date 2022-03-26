package com.cse545.hospitalSystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "claims")
@Data
public class PolicyClaim {

	@Id
    @Column(name = "claim_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	private Long policy_id;
	
	private int amountRequired;
	
	private GenericStatus Status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPolicy_id() {
		return policy_id;
	}

	public void setPolicy_id(Long policy_id) {
		this.policy_id = policy_id;
	}

	public int getAmountRequired() {
		return amountRequired;
	}

	public void setAmountRequired(int amountRequired) {
		this.amountRequired = amountRequired;
	}

	public GenericStatus getStatus() {
		return Status;
	}

	public void setStatus(GenericStatus status) {
		Status = status;
	}

	public PolicyClaim(Long policy_id, int amountRequired, GenericStatus status) {
		super();
		this.policy_id = policy_id;
		this.amountRequired = amountRequired;
		Status = status;
	}

	public PolicyClaim() {
	}

	
	
	
	
}
