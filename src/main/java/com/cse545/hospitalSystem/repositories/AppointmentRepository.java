package com.cse545.hospitalSystem.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cse545.hospitalSystem.enums.AppointmentStatus;
import com.cse545.hospitalSystem.models.Appointment;
import com.cse545.hospitalSystem.models.GenericStatus;
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

    @Query(value = "SELECT u.start_time FROM Appointment u WHERE u.doctor_user_id = ?1 and u.date = ?2", nativeQuery = true)
	public List<String> findAllTimesOfDoctor(long doctorId, String date);

    // these two will be used for Doctors and patients.
    @Query(value = "SELECT * FROM Appointment u WHERE ((u.doctor_user_id = ?1 OR u.patient_user_id = ?1 OR u.staff_user_id = ?1) AND u.date >= ?2) ORDER BY u.date DESC", nativeQuery = true)
	public List<Appointment> findAllFutureAppointmentsByuserId(long id, Date date);
    
    @Query(value = "SELECT * FROM Appointment u WHERE ((u.doctor_user_id = ?1 OR u.patient_user_id = ?1 OR u.staff_user_id = ?1) AND u.date <= ?2) ORDER BY u.date DESC", nativeQuery = true)
	public List<Appointment> findAllPastAppointmentsByuserId(long id, Date date);



}
