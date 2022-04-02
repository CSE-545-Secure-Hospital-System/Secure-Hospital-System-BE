package com.cse545.hospitalSystem.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.enums.BillStatus;
import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.Bill;
import com.cse545.hospitalSystem.models.LabResult;
import com.cse545.hospitalSystem.models.LabResultStatus;
import com.cse545.hospitalSystem.repositories.AppointmentRepository;
import com.cse545.hospitalSystem.repositories.BillRepository;

@Service
public class BillService {
    
    @Autowired
    private BillRepository billRepo;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    public Bill generateBill(Appointment appointment) {
        double price = 0;
        if(appointment.getBill() != null) {
        	 Optional<Bill> optional = billRepo.findById(appointment.getBill().getId());
             if(optional.isPresent()) {
                 return optional.get();
             }
        }
        Bill bill = new Bill();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        bill.setBillGeneratedTime(dateFormat.format(date));
        bill.setDoctor(appointment.getDoctor());
        bill.setPatient(appointment.getPatient());
        bill.setStaff(appointment.getStaff());
        bill.setAppointment(appointment);
        bill.setBillStatus(BillStatus.PENDING);
        price += appointment.getFees();
        if(appointment.getDiagnoses()!= null) {
        	Set<LabResult> labResults = appointment.getDiagnoses().getLabResult();
            for(LabResult result : labResults) {
            	if(result.getLabResultStatus().equals(LabResultStatus.COMPLETED))
            		price = price + result.getLabtests().getLabTestCost();
            }
            bill.setFee(price);
        }
        
        Bill finalBill = billRepo.save(bill);
        appointment.getPatient().addBill(finalBill);
        appointment.setBill(finalBill);
        appointmentRepository.save(appointment);
        return finalBill;
        
    }

    public Bill getBillById(long id) {
        return billRepo.findById(id).get();
    }

}
