package com.quiz.dto;

public class LoginDTO {

/**********************************************************************/
	private Integer userid;
	private String email;
	private String password;
	private Integer sessionId;
	private String message;
	private boolean LoginValidationStatus;
	
/**********************************************************************/
	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getSessionId() {
		return sessionId;
	}
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isLoginValidationStatus() {
		return LoginValidationStatus;
	}
	public void setLoginValidationStatus(boolean loginValidationStatus) {
		LoginValidationStatus = loginValidationStatus;
	}
}

/**********************************************************************/
