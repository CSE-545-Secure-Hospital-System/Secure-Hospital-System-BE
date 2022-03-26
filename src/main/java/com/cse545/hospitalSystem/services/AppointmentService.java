package com.cse545.hospitalSystem.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.GenericStatus;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.AppointmentSearchRequest;
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
    
    public List<Appointment> getAllAppointmentsForPatient(long patientId) {
        return appointmentRepo.findAllByPatientId(patientId);
    }
    
    public List<Appointment> getAllAppointmentsForPatientWithStatus(long patientId, GenericStatus status){
        return appointmentRepo.findAllByPatientIdAndStatus(patientId, status);
    }
    
    
    public Appointment getAppointmentById(long appointmentId) {
        Optional<Appointment> appointmentOptional = appointmentRepo.findById(appointmentId);
        if(!appointmentOptional.isPresent()) {
            return null;
        }
        return appointmentOptional.get();
    }
    
    public Appointment createSpecificAppointment(User patient, SpecificAppointmentRequestDTO appointmentRequest) {
        Appointment appointment = new Appointment();
        Optional<User> doctor = userRepo.findById(appointmentRequest.getDoctorId());
        if(!doctor.isPresent()) {
            return null;
        }
        appointment.setDoctor(doctor.get());
        appointment.setPatient(patient);
        if(appointmentRequest.getStartTime() == null || appointmentRequest.getDate() == null) {
            return null;
        }
        Date startDate=null;
        Date startTimeDate=null;
        try {
            startDate = new SimpleDateFormat("yy-MM-dd").parse(appointmentRequest.getDate());
            startTimeDate = new SimpleDateFormat("HH:mm:ss").parse(appointmentRequest.getStartTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        appointment.setStartTime(startTimeDate);
        appointment.setDate(startDate);
        appointment.setStatus(GenericStatus.REQUESTED);
        return appointmentRepo.save(appointment);
    }
    
    public Appointment createGeneralAppointment(User patient, GeneralAppointmentRequestDTO appointmentRequest) {
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        if(appointmentRequest.getStartTime() == null || appointmentRequest.getDate() == null) {
            return null;
        }
        Date startDate=null;
        Date startTimeDate=null;
        try {
            startDate = new SimpleDateFormat("yy-MM-dd").parse(appointmentRequest.getDate());
            startTimeDate = new SimpleDateFormat("HH:mm:ss").parse(appointmentRequest.getStartTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        appointment.setStartTime(startTimeDate);
        appointment.setDate(startDate);
        appointment.setStatus(GenericStatus.REQUESTED);
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
        if(appointment.getStatus() != GenericStatus.REQUESTED) {
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
        if(appointment.getStatus() != GenericStatus.REQUESTED) {
            return null;
        }
        appointment.setStatus(request.getStatus());
        return appointmentRepo.save(appointment);
    }

    public List<Appointment> getAllAppointmentsForDoctor(User user, AppointmentSearchRequest request) {
        List<Appointment> appointments = null;
        if(request.getDate() == null && request.getStatus() == null) {
            appointments = appointmentRepo.findAllByDoctorIds(user.getId());
        }
        else if(request.getDate() == null) {
            appointments = appointmentRepo.findAllByDoctorIdAndStatus(user.getId(), request.getStatus());
        }
        else if(request.getStatus() == null) {
            appointments = appointmentRepo.findAllByDoctorIdAndDate(user.getId(), request.getDate());
        }
        else {
            appointments = appointmentRepo.findAllByDoctorIdAndDateAndStatus(user.getId(), request.getDate(), request.getStatus());
        }
        return appointments;
    }

    public List<Appointment> getAllAppointmentsForStaff(User user, AppointmentSearchRequest request) {
        List<Appointment> appointments = null;
        if(request.getDate() == null && request.getStatus() == null) {
            appointments = appointmentRepo.findAllByStaffId(user.getId());
        }
        else if(request.getDate() == null) {
            appointments = appointmentRepo.findAllByStaffIdAndStatus(user.getId(), request.getStatus());
        }
        else if(request.getStatus() == null) {
            appointments = appointmentRepo.findAllByStaffIdAndDate(user.getId(), request.getDate());
        }
        else {
            appointments = appointmentRepo.findAllByStaffIdAndDateAndStatus(user.getId(), request.getDate(), request.getStatus());
        }
        return appointments;
    }

    public List<Appointment> getAllAppointmentsForPatient(User user, AppointmentSearchRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

}
