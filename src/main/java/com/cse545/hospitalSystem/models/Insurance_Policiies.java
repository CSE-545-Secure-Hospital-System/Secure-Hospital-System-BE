package com.cse545.hospitalSystem.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

import com.cse545.hospitalSystem.enums.PolicyTypes;

import lombok.Data;

@Entity
@Table(name = "insurance_policies")
@Data
public class Insurance_Policiies {
	
	@Id
    @Column(name = "policy_id")
    private Long id;
	
	@Column
	private String policyName;
	
	@Column
	private PolicyTypes policyType;
	
	@Column
	private Long policyClaimMaximumAmt;
	
	@Column
	private int coPayPercentage;
	
	@Column
	private String InsuranceProviderName;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "POLCIES_COVERAGES",
            joinColumns = {
            @JoinColumn(name = "POLICY_ID")
            },
            inverseJoinColumns = {
            @JoinColumn(name = "COVERAGE_ID") })
	private Set<Coverage> coverages;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "POLICIY_CLAIMS",
    joinColumns = {
    @JoinColumn(name = "POLICY_ID")
    },
    inverseJoinColumns = {
    @JoinColumn(name = "CLAIM_ID") })
	private Set<PolicyClaims> claims;
	
	
	

	public Insurance_Policiies() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Insurance_Policiies(String policyName, PolicyTypes policyType, Long policyClaimMaximumAmt,
			int coPayPercentage, String insuranceProviderName, Set<Coverage> coverages) {
		super();
		this.policyName = policyName;
		this.policyType = policyType;
		this.policyClaimMaximumAmt = policyClaimMaximumAmt;
		this.coPayPercentage = coPayPercentage;
		InsuranceProviderName = insuranceProviderName;
		this.coverages = coverages;
	}

	public Set<Coverage> getCoverages() {
		return coverages;
	}

	public void setCoverages(Set<Coverage> coverages) {
		this.coverages = coverages;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public PolicyTypes getPolicyType() {
		return policyType;
	}

	public void setPolicyType(PolicyTypes policyType) {
		this.policyType = policyType;
	}

	public Long getPolicyClaimMaximumAmt() {
		return policyClaimMaximumAmt;
	}

	public void setPolicyClaimMaximumAmt(Long policyClaimMaximumAmt) {
		this.policyClaimMaximumAmt = policyClaimMaximumAmt;
	}

	public int getCoPayPercentage() {
		return coPayPercentage;
	}

	public void setCoPayPercentage(int coPayPercentage) {
		this.coPayPercentage = coPayPercentage;
	}

	public String getInsuranceProviderName() {
		return InsuranceProviderName;
	}

	public void setInsuranceProviderName(String insuranceProviderName) {
		InsuranceProviderName = insuranceProviderName;
	}

	
	

}
