package com.cse545.hospitalSystem.models.ReqAndResp;

import java.util.Date;

public class GeneralAppointmentRequestDTO {
    
    private Long patientId;
    private String startTime;
    private String date;
    
    public Long getPatientId() {
        return patientId;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getDate() {
        return date;
    }

}
