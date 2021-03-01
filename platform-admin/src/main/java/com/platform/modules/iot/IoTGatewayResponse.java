package com.platform.modules.iot;


import java.io.Serializable;


public abstract class IoTGatewayResponse implements Serializable {

	private static final long serialVersionUID = 5014379068811962022L;

	protected String status;
	protected String message;
	
	protected String body; // API响应JSON或XML串
	
	protected Boolean isSuccess;
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
