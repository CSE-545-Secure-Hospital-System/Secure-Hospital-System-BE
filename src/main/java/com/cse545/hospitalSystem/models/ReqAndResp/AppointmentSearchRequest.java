package com.cse545.hospitalSystem.models.ReqAndResp;

import java.util.Date;

import com.cse545.hospitalSystem.enums.AppointmentStatus;

public class AppointmentSearchRequest {
    
    private Date date;
    private AppointmentStatus status;
    public Date getDate() {
        return date;
    }
    public AppointmentStatus getStatus() {
        return status;
    }

}
