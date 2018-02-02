package com.amg.exchange.registration.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "FS_LOGIN_LOGOUT_HISTORY" )
public class LoginLogoutHistory implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal loginLogoutId;
	private String loginType;
	private Timestamp loginTime;
	private Timestamp logoutTime;
	private String userName;
	private String visitedPage;
	
	public LoginLogoutHistory() {
	}
	
	@Id
	@GeneratedValue(generator="fs_login_logout_history_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_login_logout_history_seq",sequenceName="FS_LOGIN_LOGOUT_HISTORY_SEQ",allocationSize=1)
	@Column(name = "LOGIN_LOGOUT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getLoginLogoutId() {
		return loginLogoutId;
	}

	public void setLoginLogoutId(BigDecimal loginLogoutId) {
		this.loginLogoutId = loginLogoutId;
	}
	
	@Column(name = "LOGIN_TYPE", length = 1)
	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
	@Column(name = "LOGIN_TIME")
	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	
	@Column(name = "LOGOUT_TIME", length = 50)
	public Timestamp getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Timestamp logoutTime) {
		this.logoutTime = logoutTime;
	}
	
	@Column(name = "USER_NAME", length = 50)
	public String getUserName() {
		return userName;
	}
		
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "VISITED_PAGE", length = 100)
	public String getVisitedPage() {
		return visitedPage;
	}

	public void setVisitedPage(String visitedPage) {
		this.visitedPage = visitedPage;
	}

}
