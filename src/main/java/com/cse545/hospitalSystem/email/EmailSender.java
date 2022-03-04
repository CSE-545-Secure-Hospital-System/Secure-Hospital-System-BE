package com.cse545.hospitalSystem.email;

public interface EmailSender {
    
    void send(String to, String emailContent);

}
