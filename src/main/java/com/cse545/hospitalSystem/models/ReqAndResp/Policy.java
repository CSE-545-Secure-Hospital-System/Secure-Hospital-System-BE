package com.cse545.hospitalSystem.models.ReqAndResp;

import java.util.List;
import java.util.Set;

import com.cse545.hospitalSystem.enums.PolicyTypes;

public class Policy {
	
	private String policyName;
	private PolicyTypes policyType;
	private int policyClaimMaximumAmt;
	
	private Set<String> coverages;
	private String insuranceProviderName;
	
	public Policy(String policyName, PolicyTypes policyType, int policyClaimMaximumAmt,
			Set<String> coverages, String insuranceProviderName) {
		super();
		this.policyName = policyName;
		this.policyType = policyType;
		this.policyClaimMaximumAmt = policyClaimMaximumAmt;
		this.coverages = coverages;
		this.insuranceProviderName = insuranceProviderName;
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
	public Set<String> getCoverages() {
		return coverages;
	}
	public void setCoverages(Set<String> coverages) {
		this.coverages = coverages;
	}
	public String getInsuranceProviderName() {
		return insuranceProviderName;
	}
	public void setInsuranceProviderName(String insuranceProviderName) {
		this.insuranceProviderName = insuranceProviderName;
	}

}
