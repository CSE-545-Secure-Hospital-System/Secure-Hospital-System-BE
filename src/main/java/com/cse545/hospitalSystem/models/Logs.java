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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getLocalTime() {
		return localTime;
	}

	public void setLocalTime(LocalTime localTime) {
		this.localTime = localTime;
	} 
    
    
    

}
