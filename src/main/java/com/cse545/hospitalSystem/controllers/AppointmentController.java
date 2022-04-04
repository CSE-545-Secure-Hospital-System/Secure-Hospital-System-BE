package com.cse545.hospitalSystem.controllers;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.enums.RoleMapping;
import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.TimingsRequestDTO;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.AllDoctorsTimingRequest;
import com.cse545.hospitalSystem.models.ReqAndResp.AppointmentRequestDTO;
import com.cse545.hospitalSystem.models.ReqAndResp.AppointmentResponseDTO;
import com.cse545.hospitalSystem.models.ReqAndResp.AvailableDoctorsPerGivenTimeRquestDTO;
import com.cse545.hospitalSystem.models.ReqAndResp.UpdateAppointmentRequestDTO;
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
    @PostMapping(value = "/getAvailableSlotsOfDoctor")
    @PreAuthorize("hasAnyRole('PATIENT')")
    public ResponseEntity<List<String>> getAvailableSlotsOfDoctor(@RequestBody TimingsRequestDTO timingRequestDTO){
    	return ResponseEntity.ok(appointmentService.getDoctorAvailability(timingRequestDTO.getDoctorId(), timingRequestDTO.getDate(), timingRequestDTO.getAppointmentType()));
    }
    
    @CrossOrigin
    @PostMapping(value = "/getAllAvailableSlotOfDoctors")
    @PreAuthorize("hasAnyRole('PATIENT')")
    public ResponseEntity<Map<String, List<String>>> getDoctorsAvailability(@RequestBody AllDoctorsTimingRequest allDoctorsTimingRequest){
    	if(allDoctorsTimingRequest.getStartTime() == null)
    		return ResponseEntity.ok(appointmentService.getAllDoctorsAvailability(allDoctorsTimingRequest.getDate(), allDoctorsTimingRequest.getAppointmentType()));
    	return new ResponseEntity("Bad Request", HttpStatus.BAD_REQUEST);
    }
   
    @CrossOrigin
    @PostMapping(value = "/getAllAvailableDoctorsForaTimeSlot")
    public ResponseEntity<Map<Long, String>> getAllAvailableDoctorsForaTimeSlot(@RequestBody AllDoctorsTimingRequest allDoctorsTimingRequest){
    	return ResponseEntity.ok(appointmentService.getAllAvailableDoctorsForaTimeSlot(allDoctorsTimingRequest.getDate(), allDoctorsTimingRequest.getAppointmentType(), allDoctorsTimingRequest.getStartTime()));
    }
    
    // this method should only be allowed with role as staff or doctor
    @CrossOrigin
    @RequestMapping(value="/getAllFutureAppointments", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN', 'HOSPITAL_STAFF', 'PATIENT', 'DOCTOR')")
    public ResponseEntity<?>  getAllFutureAppointments(Authentication authentication, @RequestParam String searchTerm) {
        //need to change role to constant values
    	UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userService.getUseEntityrByEmailId(userDetails.getUsername());
        List<?> appointments = null;
        if(roleService.findUserRole(user, RoleMapping.HOSPITAL_STAFF) || roleService.findUserRole(user, RoleMapping.ADMIN) ) {
            //hospital staff
            appointments = appointmentService.getAllFutureAppointments(user);
        } else if(roleService.findUserRole(user, RoleMapping.DOCTOR)) {
        	appointments = appointmentService.getAllFutureAppointmentsForDoctor(user);
        }else if(roleService.findUserRole(user, RoleMapping.PATIENT)) {
            appointments = appointmentService.getAllFutureAppointmentsForPatient(user);
        } else {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not privileged for this access");
        }
       
        
        return ResponseEntity.ok().body(appointments);
    }
    
    
    @CrossOrigin
    @RequestMapping(value="/getAllPastAppointments", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN', 'HOSPITAL_STAFF','PATIENT', 'DOCTOR')")
    public ResponseEntity<?>  getAllPastAppointments(Authentication authentication, @RequestParam String searchTerm) {
        //need to change role to constant values
    	UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userService.getUseEntityrByEmailId(userDetails.getUsername());
        List<?> appointments = null;
        if (roleService.findUserRole(user, RoleMapping.HOSPITAL_STAFF) || roleService.findUserRole(user, RoleMapping.ADMIN)) {
        	appointments = appointmentService.getAllPastAppointments(user);
        }else 
        if(roleService.findUserRole(user, RoleMapping.DOCTOR)) {
        	appointments = appointmentService.getAllPastAppointmentsForDoctor(user);
        } else if(roleService.findUserRole(user, RoleMapping.PATIENT)) { 
            appointments = appointmentService.getAllPastAppointmentsForPatient(user);
        } else {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not privileged for this access");
        }
       
        
        return ResponseEntity.ok().body(appointments);
    }
    
    
    @CrossOrigin
    @RequestMapping(value="/bookAppointment", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('PATIENT')")
    public ResponseEntity<String> bookAppointment(Authentication authentication, @RequestBody AppointmentRequestDTO request) {
    	UserDetails userDetails = (UserDetails)authentication.getPrincipal();
      User user = userService.getUseEntityrByEmailId(userDetails.getUsername());
    	return appointmentService.createAppointment(user, request);
    }
    
    @CrossOrigin
    @PostMapping("/cancelAppointment")
    @PreAuthorize("hasAnyRole('PATIENT', 'HOSPITAL_STAFF')")
    public ResponseEntity<String> cancelAppointment(Authentication authentication, @RequestParam long appointmentId){
    	return appointmentService.cancelAppointment(appointmentId);
    }
    
    @CrossOrigin
    @PostMapping("/updateAppointment")
    @PreAuthorize("hasAnyRole('HOSPITAL_STAFF', 'ADMIN')")
    public ResponseEntity<String> updateAppointment(@RequestBody UpdateAppointmentRequestDTO updateAppointmentRequestDTO){
    	return appointmentService.updateAppointment(updateAppointmentRequestDTO);
    }
    
    
    @CrossOrigin
    @GetMapping("/getAllAppointments")
    public ResponseEntity<List<Appointment>> getAllAppointments(){
    	return appointmentService.getAllAppointments();
    }
    
	@CrossOrigin
	@PostMapping("/completeAppointment")
//	@PreAuthorize("hasRole('DOCTOR') hasRole('ADMIN')")
	public ResponseEntity<String> completeAppointment(@RequestParam long appointmentId){
		return appointmentService.completeAppointment(appointmentId);
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
