package com.cse545.hospitalSystem.models.ReqAndResp;

import java.util.Date;

public class SpecificAppointmentRequestDTO {
    
    private Long patientId;
    private Long doctorId;
    private Date startTime;
    private Date endTime;
    
    public Long getPatientId() {
        return patientId;
    }
    public Long getDoctorId() {
        return doctorId;
    }
    public Date getStartTime() {
        return startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    

}
