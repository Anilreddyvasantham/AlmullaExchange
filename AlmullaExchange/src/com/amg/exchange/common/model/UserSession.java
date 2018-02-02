package com.amg.exchange.common.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_SESSION")
public class UserSession implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6904878199707242964L;

	
	@Id
	@Column(name = "USER_NAME", unique = true, nullable = false, precision = 22, scale = 0)
	private String userName;
	@Column(name="LOGIN_TIME")
	private Date date;
	@Column(name="USER_TOKEN")
	private Long userToken;
	@Column(name="IP_ADDRESS")
	private String ipaddress;
	
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Long getUserToken() {
		return userToken;
	}
	public void setUserToken(Long userToken) {
		this.userToken = userToken;
	}

}
