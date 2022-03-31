package com.cse545.hospitalSystem.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.Diagnosis;
import com.cse545.hospitalSystem.models.GenericStatus;
import com.cse545.hospitalSystem.models.LabResult;
import com.cse545.hospitalSystem.models.LabResultStatus;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.DiagnosisCreateRequestDTO;
import com.cse545.hospitalSystem.repositories.AppointmentRepository;
import com.cse545.hospitalSystem.repositories.DiagnosisRepository;
import com.cse545.hospitalSystem.repositories.LabResultRepository;
import com.cse545.hospitalSystem.repositories.LabTestRepository;
import com.cse545.hospitalSystem.repositories.UserRepository;

@Service
public class DiagnosisService {

	@Autowired
	private DiagnosisRepository diagnosisRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AppointmentRepository appointmentRepo;
	
	@Autowired
	private LabTestRepository labTestRep;
	
	@Autowired
	private LabResultRepository labResultRepo;
	

	public ResponseEntity<String> createDiagnosis(DiagnosisCreateRequestDTO diagnosisCreateRequestDTO) {
		try {
		Diagnosis d = new Diagnosis();
		Appointment ap = appointmentRepo.findById(diagnosisCreateRequestDTO.getAppointmentId()).get();
		ap.setStatus(GenericStatus.PENDING);
		User doctor = userRepo.findById(diagnosisCreateRequestDTO.getDoctorId()).get();
		User patient = userRepo.findById(diagnosisCreateRequestDTO.getPatientId()).get();
		d.setDiagnosis_info(diagnosisCreateRequestDTO.getDiagnosis_info());
		d.setPrescription(diagnosisCreateRequestDTO.getPrescription());
		d.setAppointment(ap);
		d.setDoctor(doctor);
		d.setPatient(patient);
		Set<LabResult> lb = new HashSet<>();
		// for each labtest create labresult
		diagnosisCreateRequestDTO.getLabTestIds().forEach(labTest -> {
			LabResult lr = new LabResult();
			lr.setDoctor(d.getDoctor());
			lr.setLabResultStatus(LabResultStatus.PENDING);
			lr.setLabtests(labTestRep.findById(labTest).get());
			lr.setPatient(patient);
			lr.setDoctor(doctor);
			lb.add(labResultRepo.save(lr));
		});
		d.setLabResult(lb);
		Diagnosis dbDiagnosis = diagnosisRepo.save(d);
		// saving in appointment object
		ap.setDiagnoisis(dbDiagnosis);
		
		// saving into user Object
		doctor.addDiagnosis(dbDiagnosis);
		patient.addDiagnosis(dbDiagnosis);
		userRepo.save(doctor);
		userRepo.save(patient);
		appointmentRepo.save(ap);
		} catch(Exception e) {
			return new ResponseEntity<String>("Bad Request or Internal Server Error!", HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok("Success");
		
	}



	public ResponseEntity<List<Diagnosis>> getAllDiagnosisById(long patientId) {
		return ResponseEntity.ok(diagnosisRepo.findByPatient(patientId));
	}
	
}
