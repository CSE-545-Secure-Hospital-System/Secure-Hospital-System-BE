package com.cse545.hospitalSystem.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.ReqAndResp.GeneralAppointmentRequestDTO;
import com.cse545.hospitalSystem.models.ReqAndResp.SpecificAppointmentRequestDTO;
import com.cse545.hospitalSystem.services.AppointmentService;
import com.cse545.hospitalSystem.services.UserService;

@RestController
@RequestMapping(path="api/appointment")
public class AppointmentController {
    
    public static Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private AppointmentService appointmentService;
    
    @RequestMapping(value="/getAllAppointments", method = RequestMethod.GET)
    public ResponseEntity<?>  getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        
        return ResponseEntity.ok(appointments);
    }
    
    @RequestMapping(value="/createSpecificAppointment", method = RequestMethod.POST)
    public ResponseEntity<?>  createSpecificAppointment(@RequestBody SpecificAppointmentRequestDTO request) {
        Appointment appointment = appointmentService.createSpecificAppointment(request);
        return ResponseEntity.ok(appointment);
    }
    
    @RequestMapping(value="/createGeneralAppointment", method = RequestMethod.POST)
    public ResponseEntity<?>  createGeneralAppointment(@RequestBody GeneralAppointmentRequestDTO request) {
        logger.info("inside controller");
        Appointment appointment = appointmentService.createGeneralAppointment(request);
        return ResponseEntity.ok(appointment);
    }
    

}
