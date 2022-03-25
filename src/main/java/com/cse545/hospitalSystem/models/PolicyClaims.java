package com.cse545.hospitalSystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "claims")
@Data
public class PolicyClaims {

	@Id
    @Column(name = "claim_id")
    private Long id;
	
	private int amountClaimed;
	
	private GenericStatus Status;

	public int getAmountClaimed() {
		return amountClaimed;
	}

	public void setAmountClaimed(int amountClaimed) {
		this.amountClaimed = amountClaimed;
	}

	public GenericStatus getStatus() {
		return Status;
	}

	public void setStatus(GenericStatus status) {
		Status = status;
	}
	
	
	
	
}
