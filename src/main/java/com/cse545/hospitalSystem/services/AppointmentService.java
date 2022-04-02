package com.cse545.hospitalSystem.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cse545.hospitalSystem.enums.AppointmentType;
import com.cse545.hospitalSystem.enums.RoleMapping;
import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.Bill;
import com.cse545.hospitalSystem.models.Diagnosis;
import com.cse545.hospitalSystem.models.GenericStatus;
import com.cse545.hospitalSystem.models.Transaction;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.AppointmentRequestDTO;
import com.cse545.hospitalSystem.models.ReqAndResp.AppointmentResponseDTO;
import com.cse545.hospitalSystem.models.ReqAndResp.AppointmentSearchRequest;
import com.cse545.hospitalSystem.models.ReqAndResp.UpdateAppointmentRequestDTO;
import com.cse545.hospitalSystem.repositories.AppointmentRepository;
import com.cse545.hospitalSystem.repositories.UserRepository;

@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepo;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private BillService billService;
    
    public List<String> create30minSlots(String date, AppointmentType type){
    	int interval = 30; //minutes interval
        List<String> timesList=new ArrayList<String>() ; // time array
        String timeFormat;
        try {
			LocalDate startDate = new SimpleDateFormat("yyyy-MM-dd").parse(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;
			System.out.println(startDate);
	        LocalDate today = LocalDate.now();
	        Date td = new Date();
	        System.out.println(today);
	        if(startDate.compareTo(today) < 0) {
	        	return timesList;
	        }
	        int h1 = 9;
	        if(type.equals(AppointmentType.SPECIFIC)) {
	        	if(startDate.compareTo(today) > 0) {
		        	h1 = 9;
		        }else if(startDate.compareTo(today) == 0) {
		        	int hours = td.getHours()+1;
			        h1 = Math.max(hours, 9);
		        }
	        }
	        for(int h=h1;h<17;h++)
	        {
	        for(int m=0;m<60;)
	        {
	            if(h<12)
	            {
	            timeFormat=String.format("%02d:%02d %s", h,m,"AM");
	            }
	            else
	            {
	            timeFormat=String.format("%02d:%02d %s", h,m,"PM");
	            }
	            timesList.add(timeFormat);
	            m=m+interval;
	            }
	     
	        }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //To match the time
//        timeFormat=String.format("%02d:%02d %s", 0,0,"AM");
//        timesList.add(timeFormat);
    	return timesList;
    }

    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentRepo.findAll());
    }
    
    public List<AppointmentResponseDTO> ConvertAppointmentsToAppointmentsResponseDTOs(List<Appointment> appointments) {
    	List<AppointmentResponseDTO> appointmentResponseDTOs = new ArrayList<>();
    	
    	appointments.forEach(appointment -> {
    		AppointmentResponseDTO appointmentResponseDTO = new AppointmentResponseDTO();
    		appointmentResponseDTO.setAppointment(appointment);
    		if(appointment.getDoctor() != null) {
    			appointmentResponseDTO.setDoctorFirstName(appointment.getDoctor().getFirstName());
    			appointmentResponseDTO.setDoctorLastName(appointment.getDoctor().getLastName());
    			appointmentResponseDTO.setDoctorId(appointment.getDoctor().getId());
    		}
    		if(appointment.getStaff() != null) {
    			appointmentResponseDTO.setStaffFirstName(appointment.getStaff().getFirstName());
    			appointmentResponseDTO.setStaffLastName(appointment.getStaff().getLastName());
    			appointmentResponseDTO.setStaffId(appointment.getStaff().getId());
    		}
    		if(appointment.getPatient() != null) {
    			appointmentResponseDTO.setPatientFirstName(appointment.getPatient().getFirstName());
    			appointmentResponseDTO.setPatientLastName(appointment.getPatient().getLastName());
    			appointmentResponseDTO.setPatientId(appointment.getPatient().getId());
    		}
    		appointmentResponseDTOs.add(appointmentResponseDTO);   		
    	});
    	return appointmentResponseDTOs;
    	
    }
    
    // for patient
    public List<AppointmentResponseDTO> getAllFutureAppointmentsForPatient(User user) {
    	return ConvertAppointmentsToAppointmentsResponseDTOs(appointmentRepo.findAllFutureAppointmentsByuserIdForPatient(user.getId(), DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH)));
    }
    
    // for  patient
    public List<AppointmentResponseDTO> getAllPastAppointmentsForPatient(User user) {
    	return ConvertAppointmentsToAppointmentsResponseDTOs(appointmentRepo.findAllPastAppointmentsByuserIdForPatient(user.getId(), DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH)));
    }
    
    // for doctor
    public List<AppointmentResponseDTO> getAllFutureAppointmentsForDoctor(User user) {
    	return ConvertAppointmentsToAppointmentsResponseDTOs(appointmentRepo.findAllFutureAppointmentsByuserIdForDoctor(user.getId(), DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH)));
    }
    
    // for doctor	
    public List<AppointmentResponseDTO> getAllPastAppointmentsForDoctor(User user) {
    	return ConvertAppointmentsToAppointmentsResponseDTOs(appointmentRepo.findAllPastAppointmentsByuserIdForDoctor(user.getId(), DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH)));
    }
    
    
    // for hospital staff
    public List<AppointmentResponseDTO> getAllPastAppointments(User user) {
    	return ConvertAppointmentsToAppointmentsResponseDTOs(appointmentRepo.findAllPastAppointmentsForHospitalStaff(DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH)));
    }
    
    // for hospital staff
    public List<AppointmentResponseDTO> getAllFutureAppointments(User user) {
    	return ConvertAppointmentsToAppointmentsResponseDTOs(appointmentRepo.findAllFutureAppointmentsForHospitalStaff(DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH)));
    }
   
    
    public Appointment getAppointmentById(long appointmentId) {
        Optional<Appointment> appointmentOptional = appointmentRepo.findById(appointmentId);
        if(!appointmentOptional.isPresent()) {
            return null;
        }
        return appointmentOptional.get();
    }
    
    public ResponseEntity<String> createAppointment(User patient, AppointmentRequestDTO appointmentRequest) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentType(appointmentRequest.getAppointmentType());
        appointment.setDiagnoses(null);
        
        Optional<User> doctor = null;
        if(appointmentRequest.getAppointmentType().equals(AppointmentType.SPECIFIC) && appointmentRequest.getDoctorId() != null) {
        	appointment.setStatus(GenericStatus.APPROVED);
        	doctor = userRepo.findById(appointmentRequest.getDoctorId());
            if(!doctor.isPresent()) {
                return null;
            }
            appointment.setDoctor(doctor.get());
        }
        
        if(appointmentRequest.getAppointmentType().equals(AppointmentType.GENERAL)) {
        	appointment.setStatus(GenericStatus.REQUESTED);
        }
        
        appointment.setPatient(patient);
        if(appointmentRequest.getStartTime() == null || appointmentRequest.getDate() == null) {
            return null;
        }
        Date startDate=null;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(appointmentRequest.getDate());
            appointment.setStartTime(appointmentRequest.getStartTime());
            appointment.setDate(startDate);
            appointment.setFees(100.0);
            
            Appointment appointmt = appointmentRepo.save(appointment);
            if(appointmentRequest.getAppointmentType().equals(AppointmentType.SPECIFIC) && appointmentRequest.getDoctorId() != null) {
            	doctor.get().addAppointment(appointmt);
                userRepo.save(doctor.get());
            }
            User p = userRepo.findById(patient.getId()).get();
            p.addAppointment(appointmt);
            userRepo.save(patient);
            
            return ResponseEntity.ok("Success");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return new ResponseEntity<String>("Error in booking!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    public ResponseEntity<String> cancelAppointment(long appointmentId){
    	Optional<Appointment> appointment = appointmentRepo.findById(appointmentId);
    	appointment.get().setStatus(GenericStatus.CANCELED);
    	appointmentRepo.save(appointment.get());
    	
    	return ResponseEntity.ok("Sucess!");
    }
    
//    public Appointment createGeneralAppointment(User patient, GeneralAppointmentRequestDTO appointmentRequest) {
//        Appointment appointment = new Appointment();
//        appointment.setPatient(patient);
//        if(appointmentRequest.getStartTime() == null || appointmentRequest.getDate() == null) {
//            return null;
//        }
//        Date startDate=null;
//        Date startTimeDate=null;
//        try {
//            startDate = new SimpleDateFormat("yy-MM-dd").parse(appointmentRequest.getDate());
//            startTimeDate = new SimpleDateFormat("HH:mm:ss").parse(appointmentRequest.getStartTime());
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        
//        appointment.setStartTime(startTimeDate);
//        appointment.setDate(startDate);
//        appointment.setStatus(GenericStatus.REQUESTED);
//        return appointmentRepo.save(appointment);
//    }

//    public Appointment updateGeneralAppointmentStatus(UpdateAppointmentRequestDTO request, String username) {
//        Appointment appointment = null;
//        if(request.getAppointmentId() == null ||
//           request.getDoctorId() == null||
//           request.getStatus() == null) {
//            return null;
//        }
//        Optional<Appointment> appointmentOptional = appointmentRepo.findById(request.getAppointmentId());
//        if(!appointmentOptional.isPresent()) {
//            return null;
//        }
//        appointment = appointmentOptional.get();
//        Optional<User> doctorOptional = userRepo.findById(request.getDoctorId());
//        if(!doctorOptional.isPresent()) {
//            return null;
//        }
//        appointment.setDoctor(doctorOptional.get());
//        Optional<User> staffOptional = userRepo.findByEmail(username);
//        if(!staffOptional.isPresent()) {
//            return null;
//        }
//        appointment.setStaff(staffOptional.get());
//        if(appointment.getStatus() != GenericStatus.REQUESTED) {
//            return null;
//        }
//        appointment.setStatus(request.getStatus());
//        return appointmentRepo.save(appointment);
//    }
    
    //Doctor is already request and is present in patient.getDoctor()
//    public Appointment updateSpecificAppointmentStatus(UpdateAppointmentRequestDTO request, String username) {
//        Appointment appointment = null;
//        if(request.getAppointmentId() == null ||
//           request.getStatus() == null) {
//            return null;
//        }
//        Optional<Appointment> appointmentOptional = appointmentRepo.findById(request.getAppointmentId());
//        if(!appointmentOptional.isPresent()) {
//            return null;
//        }
//        appointment = appointmentOptional.get();
//        Optional<User> staffOptional = userRepo.findByEmail(username);
//        if(!staffOptional.isPresent()) {
//            return null;
//        }
//        appointment.setStaff(staffOptional.get());
//        if(appointment.getStatus() != GenericStatus.REQUESTED) {
//            return null;
//        }
//        appointment.setStatus(request.getStatus());
//        return appointmentRepo.save(appointment);
//    }

//    public List<Appointment> getAllAppointmentsForDoctor(User user, AppointmentSearchRequest request) {
//        List<Appointment> appointments = null;
//        if(request.getDate() == null && request.getStatus() == null) {
//            appointments = appointmentRepo.findAllByDoctorIds(user.getId());
//        }
//        else if(request.getDate() == null) {
//            appointments = appointmentRepo.findAllByDoctorIdAndStatus(user.getId(), request.getStatus());
//        }
//        else if(request.getStatus() == null) {
//            appointments = appointmentRepo.findAllByDoctorIdAndDate(user.getId(), request.getDate());
//        }
//        else {
//            appointments = appointmentRepo.findAllByDoctorIdAndDateAndStatus(user.getId(), request.getDate(), request.getStatus());
//        }
//        return appointments;
//    }
//
//    public List<Appointment> getAllAppointmentsForStaff(User user, AppointmentSearchRequest request) {
//        List<Appointment> appointments = null;
//        if(request.getDate() == null && request.getStatus() == null) {
//            appointments = appointmentRepo.findAllByStaffId(user.getId());
//        }
//        else if(request.getDate() == null) {
//            appointments = appointmentRepo.findAllByStaffIdAndStatus(user.getId(), request.getStatus());
//        }
//        else if(request.getStatus() == null) {
//            appointments = appointmentRepo.findAllByStaffIdAndDate(user.getId(), request.getDate());
//        }
//        else {
//            appointments = appointmentRepo.findAllByStaffIdAndDateAndStatus(user.getId(), request.getDate(), request.getStatus());
//        }
//        return appointments;
//    }

//    public List<Appointment> getAllAppointmentsForPatient(User user, String searchTerm) {
//    	userRepo.searchByTermWithAppointments(user.getId(),searchTerm);
//        return null;
//    }

	public List<String> getDoctorAvailability(Long doctorId, String date, AppointmentType type) {
		List<String> availableTimes = create30minSlots(date, type);
		if(type.equals(AppointmentType.SPECIFIC) && doctorId != null) {
			List<String> bookedTimes;
			bookedTimes = appointmentRepo.findAllTimesOfDoctor(doctorId, date);
			
			bookedTimes.forEach(time -> {
				availableTimes.remove(time);
			});
		}
			
		return availableTimes;
	}

	// not efficient
	public Map<String, List<String>> getAllDoctorsAvailability( String date,
			AppointmentType appointmentType) {
		List<User> doctors = userRepo.searchByRole(RoleMapping.DOCTOR.name());
		Map<String, List<String>> doctorsSlots = new HashMap<>();
		doctors.forEach(doctor -> {
			doctorsSlots.put(doctor.getId()+", "+doctor.getFirstName() + " " + doctor.getLastName(), this.getDoctorAvailability(doctor.getId(), date, appointmentType));
		});
		return doctorsSlots;
	}

	public ResponseEntity<String> updateAppointment(UpdateAppointmentRequestDTO updateAppointmentRequestDTO) {
		appointmentRepo.updateAppointment(updateAppointmentRequestDTO.getStaffId(), updateAppointmentRequestDTO.getDoctorId(), updateAppointmentRequestDTO.getStaffNote(), updateAppointmentRequestDTO.getAppointmentId());
		return ResponseEntity.ok("Success!");
	}

	// not efficient
	public Map<Long, String> getAllAvailableDoctorsForaTimeSlot(String date, AppointmentType appointmentType, String startTime) {
		List<User> doctors = userRepo.searchByRole(RoleMapping.DOCTOR.name());
		Map<Long, String> resdoctors = new HashMap<>();
		Set<Long> bookeddoctors = appointmentRepo.findAllTimesOfDoctorGivenTimeSlot(date, startTime);
		doctors.forEach(doctor -> {
			if(!bookeddoctors.contains(doctor.getId())) {
				resdoctors.put(doctor.getId(), doctor.getFirstName() + " " + doctor.getLastName());
			}
		});
		return resdoctors;
	}

	@Transactional
	public ResponseEntity<String> completeAppointment(long appointmentId) {
		int completed = appointmentRepo.completeAppointment(appointmentId);
		if(completed > 0) {
		    Appointment appointment = appointmentRepo.findById(appointmentId).get();
		    Bill bill = billService.generateBill(appointment);
		    Transaction transaction = transactionService.generateTransaction(bill, appointment);
		    Bill savedBill = billService.getBillById(bill.getId());
		}
		return ResponseEntity.ok("Success!");
	}

	

}
