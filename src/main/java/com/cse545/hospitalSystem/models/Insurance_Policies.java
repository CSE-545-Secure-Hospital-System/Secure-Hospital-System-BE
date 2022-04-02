package com.cse545.hospitalSystem.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import java.util.Set;

import com.cse545.hospitalSystem.enums.PolicyTypes;

import lombok.Data;

@Entity
@Table(name = "insurance_policies", uniqueConstraints = @UniqueConstraint( columnNames = { "policyName" }))
@Data
public class Insurance_Policies {
	
	@Id
    @Column(name = "policy_id")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	@Column
	@NotNull
	private String policyName;
	
	@Column
	private PolicyTypes policyType;
	
	@Column
	private int policyClaimMaximumAmt;
	
	@Column
	@NotNull
	private String InsuranceProviderName;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "POLCIES_COVERAGES",
            joinColumns = {
            @JoinColumn(name = "POLICY_ID")
            },
            inverseJoinColumns = {
            @JoinColumn(name = "COVERAGE_ID") })
	private Set<Coverage> coverages;

	public Insurance_Policies() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Insurance_Policies(String policyName, PolicyTypes policyType, int policyClaimMaximumAmt,
			 String insuranceProviderName, Set<Coverage> coverages) {
		super();
		this.policyName = policyName;
		this.policyType = policyType;
		this.policyClaimMaximumAmt = policyClaimMaximumAmt;
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

	public int getPolicyClaimMaximumAmt() {
		return policyClaimMaximumAmt;
	}

	public void setPolicyClaimMaximumAmt(int policyClaimMaximumAmt) {
		this.policyClaimMaximumAmt = policyClaimMaximumAmt;
	}


	public String getInsuranceProviderName() {
		return InsuranceProviderName;
	}

	public void setInsuranceProviderName(String insuranceProviderName) {
		InsuranceProviderName = insuranceProviderName;
	}

	
	

}
