package com.amg.exchange.aop;

public class Message {
	
	private String Message_Id;
	private String userName;
	private String password;
	private String agentId;
	private String authenticationCode;
	public String getMessage_Id() {
		return Message_Id;
	}
	public void setMessage_Id(String message_Id) {
		Message_Id = message_Id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAuthenticationCode() {
		return authenticationCode;
	}
	public void setAuthenticationCode(String authenticationCode) {
		this.authenticationCode = authenticationCode;
	}
	
	
	
	

}
