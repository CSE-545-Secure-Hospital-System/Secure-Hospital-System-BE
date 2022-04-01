package com.cse545.hospitalSystem.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cse545.hospitalSystem.enums.AppointmentStatus;
import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.GenericStatus;
import com.cse545.hospitalSystem.models.User;
import com.cse545.hospitalSystem.models.ReqAndResp.AppointmentResponseDTO;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    public List<Appointment> findAllByPatientId(Long patientId);

    List<Appointment> findAllByPatientIdAndStatus(Long patientId, GenericStatus status);
    
    @Query(value = "SELECT u FROM Appointment u WHERE u.doctor_user_id = ?1", nativeQuery = true)
    public List<Appointment> findAllByDoctorIds(Long doctorId);

    @Query(value = "SELECT u FROM Appointment u WHERE u.doctor_user_id = ?1 and u.status LIKE %?2%", nativeQuery = true)
    public List<Appointment> findAllByDoctorIdAndStatus(Long userId, AppointmentStatus status);

    @Query(value = "SELECT u FROM Appointment u WHERE u.doctor_user_id = ?1 and u.date == %?2%", nativeQuery = true)
    public List<Appointment> findAllByDoctorIdAndDate(Long userId, Date date);

    @Query(value = "SELECT u FROM Appointment u WHERE u.doctor_user_id = ?1 and u.date == %?2% and u.status LIKE %?3%", nativeQuery = true)
    public List<Appointment> findAllByDoctorIdAndDateAndStatus(Long userId, Date date, AppointmentStatus status);

    @Query(value = "SELECT u FROM Appointment u WHERE u.staff_user_id = ?1", nativeQuery = true)
    public List<Appointment> findAllByStaffId(Long id);

    @Query(value = "SELECT u FROM Appointment u WHERE u.staff_user_id = ?1 and u.status LIKE %?2%", nativeQuery = true)
    public List<Appointment> findAllByStaffIdAndDate(Long id, Date date);

    @Query(value = "SELECT u FROM Appointment u WHERE u.staff_user_id = ?1 and u.date == %?2% and u.status LIKE %?3%", nativeQuery = true)
    public List<Appointment> findAllByStaffIdAndStatus(Long id, AppointmentStatus status);

    @Query(value = "SELECT u FROM Appointment u WHERE u.doctor_user_id = ?1 and u.date == %?2% and u.status LIKE %?3%", nativeQuery = true)
    public List<Appointment> findAllByStaffIdAndDateAndStatus(Long id, Date date, AppointmentStatus status);

    // ----------------------
    
    // doctors availability - u.staff_user_id IS NOT NULL - is this needed?
    @Query(value = "SELECT u.start_time FROM Appointment u WHERE u.doctor_user_id = ?1 and u.date = ?2 and u.status != 3", nativeQuery = true)
	public List<String> findAllTimesOfDoctor(long doctorId, String date);
    
    // doctors user whose start time is the given timeslot
    @Query(value = "SELECT Distinct u.doctor_user_id FROM appointment u WHERE u.date = ?1 and u.start_time = ?2 and u.status != 3", nativeQuery = true)
    public Set<Long> findAllTimesOfDoctorGivenTimeSlot(String date, String timeSlot);

    // these will be used for patients.
    @Query(value = "SELECT * FROM Appointment u WHERE ((u.doctor_user_id = ?1 OR u.patient_user_id = ?1 OR u.staff_user_id = ?1) AND u.date >= ?2) ORDER BY u.date DESC", nativeQuery = true)
	public List<Appointment> findAllFutureAppointmentsByuserIdForPatient(long id, Date date);
    
    // these will be used for  patients
    @Query(value = "SELECT * FROM Appointment u WHERE ((u.doctor_user_id = ?1 OR u.patient_user_id = ?1 OR u.staff_user_id = ?1) AND u.date < ?2) ORDER BY u.date DESC", nativeQuery = true)
	public List<Appointment> findAllPastAppointmentsByuserIdForPatient(long id, Date date);
    
    // these will be used for Doctors, removed - AND u.staff_user_id IS NOT NULL
    @Query(value = "SELECT * FROM Appointment u WHERE ((u.doctor_user_id = ?1 OR u.patient_user_id = ?1 OR u.staff_user_id = ?1) AND u.date >= ?2 ) ORDER BY u.date DESC", nativeQuery = true)
	public List<Appointment> findAllFutureAppointmentsByuserIdForDoctor(long id, Date date);
    
    // these will be used for Doctors, removed - AND u.staff_user_id IS NOT NULL
    @Query(value = "SELECT * FROM Appointment u WHERE ((u.doctor_user_id = ?1 OR u.patient_user_id = ?1 OR u.staff_user_id = ?1) AND u.date < ?2) ORDER BY u.date DESC", nativeQuery = true)
	public List<Appointment> findAllPastAppointmentsByuserIdForDoctor(long id, Date date);
    
    // these will be used for Hospital Staff
    @Query(value = "SELECT * FROM Appointment u WHERE u.date < ?1 ORDER BY u.date DESC", nativeQuery = true)
    public List<Appointment> findAllPastAppointmentsForHospitalStaff(Date date);
    
    // these will be used for Hospital Staff
    @Query(value = "SELECT * FROM Appointment u WHERE u.date >= ?1 ORDER BY u.date DESC", nativeQuery = true)
    public List<Appointment> findAllFutureAppointmentsForHospitalStaff(Date date);
    
    // update Appointment
    @Transactional
    @Modifying
    @Query(value = "Update Appointment u SET u.doctor_user_id = ?2, u.status = 1, u.staff_user_id = ?1, u.staff_note = ?3 WHERE u.appointment_id = ?4", nativeQuery = true)
    public int updateAppointment(long hospitalStaffId, long doctorId, String staffNote, long appointmentId);

    // for completing the appointment by doctor
    @Transactional
    @Modifying
    @Query(value = "Update Appointment u SET u.status = 3 WHERE u.id = ?1")
	public int completeAppointment(long appointmentId);

    public Optional<Appointment> findByAppointment(Long appointmentId);

}
