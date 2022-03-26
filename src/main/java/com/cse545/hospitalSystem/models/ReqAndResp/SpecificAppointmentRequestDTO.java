package com.cse545.hospitalSystem.models.ReqAndResp;

import java.util.Date;

public class SpecificAppointmentRequestDTO {
    
    private Long patientId;
    private Long doctorId;
    private String startTime;
    private String date;
    
    public Long getPatientId() {
        return patientId;
    }
    public Long getDoctorId() {
        return doctorId;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getDate() {
        return date;
    }
    

}
