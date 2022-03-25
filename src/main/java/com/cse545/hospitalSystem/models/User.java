package com.cse545.hospitalSystem.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "users")
public class User implements UserDetails {
    
    public User() {}
    
    public User(String firstName,
            String lastName,
            String email,
            String password,
            Set<Role> roles) {
                 this.firstName = firstName;
                 this.lastName = lastName;
                 this.email = email;
                 this.password = password;
                 this.roles = roles;
                }

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
    private String email;

    @Column
    private String phone;
    
    //TODO handle password encryption and constraints later
    @Column
    @JsonIgnore
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
            @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID") })
    @JsonIgnore
    private Set<Role> roles;
    
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "patient_record_id", referencedColumnName = "patient_record_id")
    private PatientRecord patientRecord;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_POLICIES",
    joinColumns = {
    @JoinColumn(name = "USER_ID")
    },
    inverseJoinColumns = {
    @JoinColumn(name = "POLICY_ID") })
    private Set<Insurance_Policiies> policies;


	@Column
    private Boolean locked = false;
    
    @Column
    private Boolean enabled = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    	roles.forEach(role -> {
    		authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
    	});
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
	public PatientRecord getPatientRecord() {
        return patientRecord;
    }

    public void setPatientRecord(PatientRecord patientRecord) {
        this.patientRecord = patientRecord;
    }
    
    public Set<Insurance_Policiies> getPolicies() {
        return policies;
    }

    public void setPolicies(Set<Insurance_Policiies> policies) {
        this.policies = policies;
    }


}
