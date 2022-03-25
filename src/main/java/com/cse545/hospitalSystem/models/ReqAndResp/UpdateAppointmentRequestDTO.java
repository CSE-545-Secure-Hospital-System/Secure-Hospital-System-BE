package com.cse545.hospitalSystem.models.ReqAndResp;

import com.cse545.hospitalSystem.models.GenericStatus;

public class UpdateAppointmentRequestDTO {
    
    private Long doctorId;
    private Long appointmentId;
    private Long staffId;
    private GenericStatus status;
    
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
