package com.cse545.hospitalSystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.AppointmentStatus;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.GeneralAppointmentRequestDTO;
import com.cse545.hospitalSystem.models.ReqAndResp.SpecificAppointmentRequestDTO;
import com.cse545.hospitalSystem.models.ReqAndResp.UpdateAppointmentRequestDTO;
import com.cse545.hospitalSystem.repositories.AppointmentRepository;
import com.cse545.hospitalSystem.repositories.UserRepository;

@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepo;
    
    @Autowired
    private UserRepository userRepo;

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }
    
    public List<Appointment> getAllAppointmentsForPatient(String patientId) {
        return appointmentRepo.findAllByPatientId(patientId);
    }
    
    public List<Appointment> getAllAppointmentsForPatientWithStatus(String patientId, AppointmentStatus status){
        return appointmentRepo.findAllByPatientIdAndStatus(patientId, status);
    }
    
    public List<Appointment> getAllAppointmentForDoctor(String doctorId){
        return appointmentRepo.findAllByDoctorId(doctorId);
    }
    
    public Appointment getAppointmentById(Long appointmentId) {
        Optional<Appointment> appointmentOptional = appointmentRepo.findById(appointmentId);
        if(!appointmentOptional.isPresent()) {
            return null;
        }
        return appointmentOptional.get();
    }
    
    public Appointment createSpecificAppointment(SpecificAppointmentRequestDTO appointmentRequest) {
        Appointment appointment = new Appointment();
        Optional<User> doctor = userRepo.findById(appointmentRequest.getDoctorId());
        if(!doctor.isPresent()) {
            return null;
        }
        appointment.setDoctor(doctor.get());
        Optional<User> patient = userRepo.findById(appointmentRequest.getPatientId());
        if(!patient.isPresent()) {
            return null;
        }
        appointment.setPatient(patient.get());
        if(appointmentRequest.getStartTime() == null || appointmentRequest.getEndTime() == null) {
            return null;
        }
        appointment.setStartTime(appointmentRequest.getStartTime());
        appointment.setEndTime(appointmentRequest.getEndTime());
        appointment.setStatus(AppointmentStatus.REQUESTED);
        return appointmentRepo.save(appointment);
    }
    
    public Appointment createGeneralAppointment(GeneralAppointmentRequestDTO appointmentRequest) {
        Appointment appointment = new Appointment();
        Optional<User> patient = userRepo.findById(appointmentRequest.getPatientId());
        if(!patient.isPresent()) {
            return null;
        }
        appointment.setPatient(patient.get());
        if(appointmentRequest.getStartTime() == null || appointmentRequest.getEndTime() == null) {
            return null;
        }
        appointment.setStartTime(appointmentRequest.getStartTime());
        appointment.setEndTime(appointmentRequest.getEndTime());
        appointment.setStatus(AppointmentStatus.REQUESTED);
        return appointmentRepo.save(appointment);
    }

    public Appointment updateGeneralAppointmentStatus(UpdateAppointmentRequestDTO request, String username) {
        Appointment appointment = null;
        if(request.getAppointmentId() == null ||
           request.getDoctorId() == null||
           request.getStatus() == null) {
            return null;
        }
        Optional<Appointment> appointmentOptional = appointmentRepo.findById(request.getAppointmentId());
        if(!appointmentOptional.isPresent()) {
            return null;
        }
        appointment = appointmentOptional.get();
        Optional<User> doctorOptional = userRepo.findById(request.getDoctorId());
        if(!doctorOptional.isPresent()) {
            return null;
        }
        appointment.setDoctor(doctorOptional.get());
        Optional<User> staffOptional = userRepo.findByEmail(username);
        if(!staffOptional.isPresent()) {
            return null;
        }
        appointment.setStaff(staffOptional.get());
        if(appointment.getStatus() != AppointmentStatus.REQUESTED) {
            return null;
        }
        appointment.setStatus(request.getStatus());
        return appointmentRepo.save(appointment);
    }
    
    //Doctor is already request and is present in patient.getDoctor()
    public Appointment updateSpecificAppointmentStatus(UpdateAppointmentRequestDTO request, String username) {
        Appointment appointment = null;
        if(request.getAppointmentId() == null ||
           request.getStatus() == null) {
            return null;
        }
        Optional<Appointment> appointmentOptional = appointmentRepo.findById(request.getAppointmentId());
        if(!appointmentOptional.isPresent()) {
            return null;
        }
        appointment = appointmentOptional.get();
        Optional<User> staffOptional = userRepo.findByEmail(username);
        if(!staffOptional.isPresent()) {
            return null;
        }
        appointment.setStaff(staffOptional.get());
        if(appointment.getStatus() != AppointmentStatus.REQUESTED) {
            return null;
        }
        appointment.setStatus(request.getStatus());
        return appointmentRepo.save(appointment);
    }

}
