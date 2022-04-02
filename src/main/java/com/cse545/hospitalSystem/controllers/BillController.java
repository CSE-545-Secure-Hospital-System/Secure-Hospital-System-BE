package com.cse545.hospitalSystem.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cse545.hospitalSystem.enums.TransactionStatus;
import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.Bill;
import com.cse545.hospitalSystem.models.Transaction;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.repositories.AppointmentRepository;
import com.cse545.hospitalSystem.repositories.BillRepository;
import com.cse545.hospitalSystem.repositories.TransactionRepository;
import com.cse545.hospitalSystem.repositories.UserRepository;
import com.cse545.hospitalSystem.services.BillService;
import com.cse545.hospitalSystem.services.TransactionService;

public class BillController {
    
    @Autowired
    private BillRepository billRepo;
    
    @Autowired
    private AppointmentRepository appointmentRepo;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private BillService billService;
    
    @RequestMapping(value="/getAllBills/{patientId}", method = RequestMethod.GET)
    public ResponseEntity<List<Bill>> getAllBillsForPatient(@RequestParam(name = "patientId") Long patientId) {
        Optional<User> optionalPatient = userRepo.findById(patientId);
        List<Bill> bills = null;
        if(!optionalPatient.isPresent()) {
            return new ResponseEntity<List<Bill>>(bills, HttpStatus.OK);
        }
        bills = new ArrayList<>();
        for (Bill b : optionalPatient.get().getBills()){
            bills.add(b);
        }
        return new ResponseEntity<List<Bill>>(bills, HttpStatus.OK);
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

}
