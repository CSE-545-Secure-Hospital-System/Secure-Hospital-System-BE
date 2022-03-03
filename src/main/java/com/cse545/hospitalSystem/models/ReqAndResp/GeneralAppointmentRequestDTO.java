package com.cse545.hospitalSystem.models.ReqAndResp;

import java.util.Date;

public class GeneralAppointmentRequestDTO {
    
    private Long patientId;
    private Date startTime;
    private Date endTime;
    
    public Long getPatientId() {
        return patientId;
    }
    public Date getStartTime() {
        return startTime;
    }
    public Date getEndTime() {
        return endTime;
    }

}
