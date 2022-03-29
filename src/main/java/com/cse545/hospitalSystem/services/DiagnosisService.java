package com.cse545.hospitalSystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.Diagnosis;
import com.cse545.hospitalSystem.repositories.AppointmentRepository;
import com.cse545.hospitalSystem.repositories.DiagnosisRepository;
import com.cse545.hospitalSystem.repositories.UserRepository;

@Service
public class DiagnosisService {

	@Autowired
	private DiagnosisRepository diagnosisRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AppointmentRepository appointmentRepo;

//	public ResponseEntity<String> createDiagnosis(DiagnosisCreateRequestDTO diagnosisCreateRequestDTO) {
//		Diagnosis d = diagnosisRepo.save(diagnosis);
//		Optional<Appointment> a = appointmentRepo.findById(diagnosis.getAppointmentId());
//		a.get().setDiagnoisis(d);
//		appointmentRepo.save(a.get());
//		return ResponseEntity.ok("Success");
//	}
//
//	public ResponseEntity<List<Diagnosis>> getDiagnosesById(long patientId) {
//		List<Diagnosis> ds = diagnosisRepo.findByPatientId(patientId);
//		return ResponseEntity.ok(ds);
//	}
	
}
