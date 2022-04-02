package com.cse545.hospitalSystem.models;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "users")
public class User implements UserDetails {
    


	public User() {}
    
    public User(String firstName,
            String lastName,
            String email,
            String password,
            Set<Role> roles,
            Date dob) {
                 this.firstName = firstName;
                 this.lastName = lastName;
                 this.email = email;
                 this.password = password;
                 this.roles = roles;
                 this.dob = dob;
                }

	@Id
    @Column(name = "user_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String firstName;
    
    @Column
    private String lastName;

    @Column
    private String sessionId;

    @Column
    @NotNull
    private String email;

    @Column
    @NotNull
    private String phone;
    
    //TODO handle password encryption and constraints later
    @Column
    @NotNull
    private String password;
    
	@Column
    private Boolean locked = false;
    
    @Column
    private Boolean enabled = false;
    
    @NotNull
    private Date dob;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
            @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_POLICIES",
    joinColumns = {
    @JoinColumn(name = "USER_ID")
    },
    inverseJoinColumns = {
    @JoinColumn(name = "POLICY_ID") })
    private Set<Insurance_Policies> policies;
    
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_CLAIMS",
    joinColumns = {
    @JoinColumn(name = "USER_ID")
    },
    inverseJoinColumns = {
    @JoinColumn(name = "CLAIM_ID") })
    private Set<PolicyClaim> claims;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_APPOINTMENTS",
    joinColumns = {
    @JoinColumn(name = "USER_ID")
    },
    inverseJoinColumns = {
    @JoinColumn(name = "APPOINTMENT_ID") })
    private Set<Appointment> appointments;
    
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_DIAGNOSIS",
    joinColumns = {
    @JoinColumn(name = "USER_ID")
    },
    inverseJoinColumns = {
    @JoinColumn(name = "DIAGNOSIS_ID") })
    private Set<Diagnosis> diagnoses;
    
    
    public void addAppointment(Appointment a) {
    	appointments.add(a);
    }
    
    public void addPolicy(Insurance_Policies e) {
    	policies.add(e);
    }
    
    
    public Set<PolicyClaim> getClaims() {
		return claims;
	}

	public void setClaims(Set<PolicyClaim> claims) {
		this.claims = claims;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Set<Insurance_Policies> getPolicies() {
		return policies;
	}

	public void setPolicies(Set<Insurance_Policies> policies) {
		this.policies = policies;
	}

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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
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

	public Set<Diagnosis> getDiagnoses() {
		return diagnoses;
	}

	public void setDiagnoses(Set<Diagnosis> diagnoses) {
		this.diagnoses = diagnoses;
	}
    
    public void addDiagnosis(Diagnosis diagnosis) {
    	this.diagnoses.add(diagnosis);
    }

}
