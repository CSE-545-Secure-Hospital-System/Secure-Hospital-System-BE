package com.cse545.hospitalSystem.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "logs")
public class Logs{
    
    public Logs() {}
    
    public Logs(//Long user_id,
            String user_email,
            LocalDate date,
            LocalTime localTime) {
                 //this.user_id = user_id;
                 this.user_email = user_email;
                 this.date = date;
                 this.localTime = localTime;
                }


	@Id
    @Column(name = "log_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column
    private Long user_id;

    @Column
    private String user_email;

    @Column(name = "date")
    private LocalDate date;
    
    @Column(name = "time")
    private LocalTime localTime;  
    

}
