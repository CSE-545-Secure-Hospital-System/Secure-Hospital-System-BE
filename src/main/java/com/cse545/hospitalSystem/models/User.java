package com.cse545.hospitalSystem.models;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column
    private String firstName;
    
    @Column
    private String lastName;

    @Column
    private String sessionId;

    @Column
    private String emailId;

    @Column
    private String phone;

    @Column
    private String businessTitle;
    
    //TODO handle password encryption and constraints later
    @Column
    private String password;

    @ManyToOne(targetEntity = Role.class)
    private Role role;
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public String getBusinessTitle() {
        return businessTitle;
    }

    public Role getRole() {
        return role;
    }

}
