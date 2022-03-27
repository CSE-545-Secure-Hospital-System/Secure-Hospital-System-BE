package com.cse545.hospitalSystem.controllers;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.TimingsRequestDTO;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.AppointmentRequestDTO;
import com.cse545.hospitalSystem.services.AppointmentService;
import com.cse545.hospitalSystem.services.RoleService;
import com.cse545.hospitalSystem.services.UserService;

@RestController
@RequestMapping(path="api/appointment")
public class AppointmentController {
    
    public static Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private UserService userService;
    
    @CrossOrigin
    @PostMapping(value = "/getAvailableSlotOfDoctors")
    public ResponseEntity<List<String>> getDoctorsAvailability(@RequestBody TimingsRequestDTO timingRequestDTO){
    	return appointmentService.getDoctorsAvailability(timingRequestDTO.getDoctorId(), timingRequestDTO.getDate(), timingRequestDTO.getAppointmentType());
    }
   
    
    // this method should only be allowed with role as staff or doctor
//    @RequestMapping(value="/getAllAppointments", method = RequestMethod.GET)
//    public ResponseEntity<?>  getAllAppointments(Authentication authentication, AppointmentSearchRequest request) {
//        //need to change role to constant values
//        User user = (User) authentication.getPrincipal();
//        List<Appointment> appointments = null;
//        if(roleService.findUserRole(user, "ROLE_DOCTOR")) {
//            appointments = appointmentService.getAllAppointmentsForDoctor(user, request);
//        } else if(roleService.findUserRole(user, "ROLE_STAFF")) {
//            //hospital staff
//            appointments = appointmentService.getAllAppointmentsForStaff(user, request);
//        } else if(roleService.findUserRole(user, "ROLE_PATIENT")) {
//            appointments = appointmentService.getAllAppointmentsForPatient(user, request);
//        } else {
//            ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not privileged for this access");
//        }
//       
//        
//        return ResponseEntity.ok().body(appointments);
//    }
    
    //this method should only be allowed with role as patient
//    @RequestMapping(value="/createSpecificAppointment", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('PATIENT')")
//    public ResponseEntity<?>  createSpecificAppointment(Authentication authentication, @RequestBody SpecificAppointmentRequestDTO request) {
//        User user = (User)authentication.getPrincipal();
//        Appointment appointment = appointmentService.createSpecificAppointment(user, request);
//        if(appointment == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Appointment was not able "
//                    + "to be created due to a bad request");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(appointment);
//    }
    
    @CrossOrigin
    @RequestMapping(value="/bookAppointment", method = RequestMethod.POST)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<String> bookAppointment(Authentication authentication,@RequestBody AppointmentRequestDTO request) {
    	UserDetails userDetails = (UserDetails)authentication.getPrincipal();
      User user = userService.getUseEntityrByEmailId(userDetails.getUsername());
    	return appointmentService.createAppointment(user, request);
    }
    
//    //This method should only be allowed with role as patient
//    @RequestMapping(value="/createGeneralAppointment", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('PATIENT')")
//    public ResponseEntity<?>  createGeneralAppointment(Authentication authentication, @RequestBody GeneralAppointmentRequestDTO request) {
//        logger.info("inside controller");
//        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
//        User user = userService.getUseEntityrByEmailId(userDetails.getUsername());
//        Appointment appointment = appointmentService.createGeneralAppointment(user, request);
//        if(appointment == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Appointment was not able "
//                    + "to be created due to a bad request");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(appointment);
//    }
//    
//    //This method should only be allowed for role as STAFF
//    @RequestMapping(value="/updateGeneralAppointmentStatus", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('STAFF')")
//    public ResponseEntity<?>  updateGeneralAppointmentStatus (
//            @RequestBody UpdateAppointmentRequestDTO request) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User)authentication.getPrincipal();
//        Appointment appointment = appointmentService.updateGeneralAppointmentStatus(request, user.getUsername());
//        if(appointment == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Appointment was not able "
//                    + "to be created due to a bad request");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(appointment);
//    }
//    
    
    

}
