package com.cse545.hospitalSystem.models.ReqAndResp;

import java.util.List;

public class AuthToken {
	
	private String token;
	private String message;
	
	

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AuthToken(String token, String message) {
		super();
		this.token = token;
		this.message = message;
	}

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
