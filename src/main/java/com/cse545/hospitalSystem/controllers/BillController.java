package com.cse545.hospitalSystem.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.enums.RoleMapping;
import com.cse545.hospitalSystem.enums.TransactionStatus;
import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.Bill;
import com.cse545.hospitalSystem.models.Transaction;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.BillResponseDTO;
import com.cse545.hospitalSystem.repositories.AppointmentRepository;
import com.cse545.hospitalSystem.repositories.BillRepository;
import com.cse545.hospitalSystem.repositories.TransactionRepository;
import com.cse545.hospitalSystem.repositories.UserRepository;
import com.cse545.hospitalSystem.services.BillService;
import com.cse545.hospitalSystem.services.RoleService;
import com.cse545.hospitalSystem.services.TransactionService;
import com.cse545.hospitalSystem.services.UserService;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    
    @Autowired
    private BillRepository billRepo;
    
    @Autowired
    private AppointmentRepository appointmentRepo;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BillService billService;
    
    @Autowired
    private RoleService roleService;
    
    @CrossOrigin
    @RequestMapping(value="/getAllBills", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('PATIENT', 'LAB_STAFF', 'DOCTOR', 'INSURANCE_STAFF', 'ADMIN', 'HOSPITAL_STAFF')")
    public ResponseEntity<List<BillResponseDTO>> getAllBillsForPatient(@RequestParam(name = "patientId") Long patientId,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        ResponseEntity<User> resp = userService.getUserByEmailId(userEmail);
        User user =resp.getBody();
        if(!checkIfPatientAndValidId(user, patientId)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return billService.getAllBills(patientId);
    }
    
    @RequestMapping(value="/getBill/{appointmentId}", method = RequestMethod.GET)
    public ResponseEntity<Bill> getAllTransactions(@RequestParam(name = "appointmentId") Long appointmentId) {
        Bill bill = null;
        Optional<Appointment> optional = appointmentRepo.findById(appointmentId);
        if(!optional.isPresent()) {
            return new ResponseEntity<Bill>(bill, HttpStatus.OK); 
        }
        Appointment appointment = optional.get();
        
        return new ResponseEntity<Bill>(appointment.getBill(), HttpStatus.OK);
    }
    
    private boolean checkIfPatientAndValidEmail(User user, String email) {
        if(roleService.findUserRole(user, RoleMapping.PATIENT)) {
            if(user.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean checkIfPatientAndValidId(User user, long id) {
        if(roleService.findUserRole(user, RoleMapping.PATIENT)) {
            if(user.getId() ==  id) {
                return false;
            }
        }
        return true;
    }


}
