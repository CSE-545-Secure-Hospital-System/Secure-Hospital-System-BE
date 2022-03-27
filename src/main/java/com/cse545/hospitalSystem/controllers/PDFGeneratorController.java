package com.cse545.hospitalSystem.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.LabResults;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.repositories.AppointmentRepository;
import com.cse545.hospitalSystem.repositories.UserRepository;
import com.cse545.hospitalSystem.services.AppointmentService;
import com.cse545.hospitalSystem.services.ReportService;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping(path="api")
public class PDFGeneratorController {
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private ReportService reportService;
    
    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private AppointmentRepository appointmentRepo;
    
    //@PreAuthorize()
    @GetMapping(value = "/report")
    public String viewPDF() {
        return "redirect:/otp/generateOtp/viewPDF";
    }

    @GetMapping(value = "/reportView/users", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateUsersReport() throws FileNotFoundException, JRException {
        
        byte data[] = reportService.exportReport(userRepo.findAll(), "users.jrxml");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(data);
    }
    
    @GetMapping(value = "/reportView/LabReports/{patientId}/{appointmentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateLabTestReports(Authentication authentication, @PathVariable long patientId, @PathVariable long appointmentId) throws FileNotFoundException, JRException {
        Optional<Appointment> appointmentOptional = appointmentRepo.findById(appointmentId);
        UserDetails user = (UserDetails)authentication.getPrincipal();
        if(appointmentOptional.isEmpty()) {
            return null;
        }
        user.getUsername();
        //patient can get lab result
        if(!appointmentOptional.get().getPatient().getEmail().equals(user.getUsername())) {
            return null;
        }
        List<LabResults>results = appointmentOptional.get().getDiagnosis().getLabResults();
        
        byte data[] = reportService.exportReport(results, "labresult.jrxml");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(data);
    }
}
