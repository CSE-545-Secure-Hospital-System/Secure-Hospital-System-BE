package com.cse545.hospitalSystem.models.ReqAndResp;

import java.util.List;

public class AuthToken {
	
	private String token;
	private String firstName;
	private String lastName;

    public AuthToken(){

    }

    public AuthToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
	
	

}
