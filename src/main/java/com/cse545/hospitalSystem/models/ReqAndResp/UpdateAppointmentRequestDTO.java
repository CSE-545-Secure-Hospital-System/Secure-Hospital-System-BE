package com.cse545.hospitalSystem.models.ReqAndResp;

import com.cse545.hospitalSystem.models.AppointmentStatus;

public class UpdateAppointmentRequestDTO {
    
    Long doctorId;
    Long appointmentId;
    Long staffId;
    AppointmentStatus status;
    public Long getDoctorId() {
        return doctorId;
    }
    public Long getAppointmentId() {
        return appointmentId;
    }
    public Long getStaffId() {
        return staffId;
    }
    public AppointmentStatus getStatus() {
        return status;
    }
    

}
