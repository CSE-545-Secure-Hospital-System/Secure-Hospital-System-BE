
package com.cse545.hospitalSystem.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.models.LabResult;
import com.cse545.hospitalSystem.models.LabResultStatus;
import com.cse545.hospitalSystem.models.LabTest;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.LabReport;
import com.cse545.hospitalSystem.models.ReqAndResp.LabReportsResponse;
import com.cse545.hospitalSystem.models.ReqAndResp.UpdateLabTestRequest;
import com.cse545.hospitalSystem.repositories.LabResultRepository;
import com.cse545.hospitalSystem.repositories.LabTestRepository;
import com.cse545.hospitalSystem.repositories.UserRepository;

@Service
public class LabResultsService {
	
	@Autowired
	private LabResultRepository labResultRepository;
	
	@Autowired
	private LabTestRepository labTestRepository;
	
	@Autowired
	private UserRepository userRepository;

	public ResponseEntity<LabReportsResponse> getAllLabReportsByPatientId(long patientId) {
		LabReportsResponse labReportsResponse = new LabReportsResponse();
		List<LabTest> labTests = labTestRepository.findAll();
		Map<Long, LabTest> mp = new HashMap<>();
		labTests.forEach(lab -> {
			mp.put(lab.getId(), lab);
		});
		labReportsResponse.setLabTest(mp);
		List<LabResult> labResults = labResultRepository.findByPatient(patientId);
		List<LabReport> labReports = new ArrayList<>();
		labResults.forEach(labres -> {
			LabReport lr = new LabReport();
			if(labres.getDoctor() != null)
			lr.setDoctorName(labres.getDoctor().getFirstName() + " " + labres.getDoctor().getLastName());
			lr.setId(labres.getId());
			lr.setLabResultStatus(labres.getLabResultStatus());
			if(labres.getLabStaff() != null)
			lr.setLabStaffName(labres.getLabStaff().getFirstName() + " " + labres.getLabStaff().getLastName());
			lr.setLabStaffNotes(labres.getLabStaffNotes());
			lr.setLabTestId(labres.getLabtests().getId());
			if(labres.getPatient() != null)
			lr.setPatientName(labres.getPatient().getFirstName() + " " + labres.getPatient().getLastName());
			lr.setResult(labres.getResult());
			labReports.add(lr);
		});
		labReportsResponse.setLabReports(labReports);
		return ResponseEntity.ok(labReportsResponse);
	}

	public ResponseEntity<LabReportsResponse> getAllLabReports() {
		LabReportsResponse labReportsResponse = new LabReportsResponse();
		List<LabTest> labTests = labTestRepository.findAll();
		Map<Long, LabTest> mp = new HashMap<>();
		labTests.forEach(lab -> {
			mp.put(lab.getId(), lab);
		});
		labReportsResponse.setLabTest(mp);
		List<LabResult> labResults = labResultRepository.findAll();
		List<LabReport> labReports = new ArrayList<>();
		labResults.forEach(labres -> {
			LabReport lr = new LabReport();
			if(labres.getDoctor() != null)
			lr.setDoctorName(labres.getDoctor().getFirstName() + " " + labres.getDoctor().getLastName());
			lr.setId(labres.getId());
			lr.setLabResultStatus(labres.getLabResultStatus());
			if(labres.getLabStaff() != null)
			lr.setLabStaffName(labres.getLabStaff().getFirstName() + " " + labres.getLabStaff().getLastName());
			lr.setLabStaffNotes(labres.getLabStaffNotes());
			lr.setLabTestId(labres.getLabtests().getId());
			if(labres.getPatient() != null)
			lr.setPatientName(labres.getPatient().getFirstName() + " " + labres.getPatient().getLastName());
			lr.setResult(labres.getResult());
			labReports.add(lr);
		});
		labReportsResponse.setLabReports(labReports);
		return ResponseEntity.ok(labReportsResponse);
	}

	public ResponseEntity<String> updateLabReport(UpdateLabTestRequest updateLabTestRequest) {
		LabResult lr = labResultRepository.findById(updateLabTestRequest.getId()).get();
		User labStaff = userRepository.findById(updateLabTestRequest.getLabStaffId()).get();
		lr.setLabStaff(labStaff);
		lr.setLabResultStatus(LabResultStatus.COMPLETED);
		lr.setLabStaffNotes(updateLabTestRequest.getLabStaffNotes());
		lr.setResult(updateLabTestRequest.getResult());
		labResultRepository.save(lr);
		return ResponseEntity.ok("Success!");
	}
	

}
