package com.cse545.hospitalSystem.models.ReqAndResp;

import java.util.List;
import java.util.Map;

import com.cse545.hospitalSystem.models.LabTest;

public class LabReportsResponse {
	
	private Map<Long, LabTest> labTest;
	
	private List<LabReport> labReports;

	public Map<Long, LabTest> getLabTest() {
		return labTest;
	}

	public void setLabTest(Map<Long, LabTest> labTest) {
		this.labTest = labTest;
	}

	public List<LabReport> getLabReports() {
		return labReports;
	}

	public void setLabReports(List<LabReport> labReports) {
		this.labReports = labReports;
	}


	
	

}
