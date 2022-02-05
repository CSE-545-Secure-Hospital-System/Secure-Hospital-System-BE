package com.cse545.hospitalSystem.models.RespAndReq;

public class MessageResponse {
	
	private String message;
	private int statusCode;

	public MessageResponse(String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}

	public MessageResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	

}
