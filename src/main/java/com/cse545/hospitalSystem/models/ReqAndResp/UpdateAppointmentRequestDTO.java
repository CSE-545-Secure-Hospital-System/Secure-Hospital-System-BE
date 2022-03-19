package com.cse545.hospitalSystem.models.ReqAndResp;

import com.cse545.hospitalSystem.models.GenericStatus;

public class UpdateAppointmentRequestDTO {
    
    Long doctorId;
    Long appointmentId;
    Long staffId;
    GenericStatus status;
    public Long getDoctorId() {
        return doctorId;
    }
    public Long getAppointmentId() {
        return appointmentId;
    }
    public Long getStaffId() {
        return staffId;
    }
    public GenericStatus getStatus() {
        return status;
    }
    

}
