package com.cse545.hospitalSystem.models.ReqAndResp;

public class NewPasswordRequestDTO {
    
    private String otp;
    private String password;
    private String email;
    
    public String getEmail() {
        return email;
    }
    public String getOtp() {
        return otp;
    }
    public String getPassword() {
        return password;
    }
    
    

}
