package com.cse545.hospitalSystem.models.ReqAndResp;

import java.util.List;

import com.cse545.hospitalSystem.enums.PolicyTypes;

public class Policy {
	
	private String policyName;
	private PolicyTypes policyType;
	private int policyClaimMaximumAmt;
	
	private int coPayPercentage;
	private List<String> coverages;
	private String insuranceProviderName;
	
	public Policy(String policyName, PolicyTypes policyType, int policyClaimMaximumAmt, int coPayPercentage,
			List<String> coverages, String insuranceProviderName) {
		super();
		this.policyName = policyName;
		this.policyType = policyType;
		this.policyClaimMaximumAmt = policyClaimMaximumAmt;
		this.coPayPercentage = coPayPercentage;
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
	public int getCoPayPercentage() {
		return coPayPercentage;
	}
	public void setCoPayPercentage(int coPayPercentage) {
		this.coPayPercentage = coPayPercentage;
	}
	public List<String> getCoverages() {
		return coverages;
	}
	public void setCoverages(List<String> coverages) {
		this.coverages = coverages;
	}
	public String getInsuranceProviderName() {
		return insuranceProviderName;
	}
	public void setInsuranceProviderName(String insuranceProviderName) {
		this.insuranceProviderName = insuranceProviderName;
	}

}
