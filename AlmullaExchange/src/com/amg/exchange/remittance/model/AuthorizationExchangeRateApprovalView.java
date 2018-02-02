package com.amg.exchange.remittance.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_RAAU")
public class AuthorizationExchangeRateApprovalView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String fuDesc;
	private String oraUser;
	private String shDesc;
	private String usrPwd;
	
	@Id
	@Column(name = "CODE")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "FUDESC")
	public String getFuDesc() {
		return fuDesc;
	}
	public void setFuDesc(String fuDesc) {
		this.fuDesc = fuDesc;
	}
	
	@Column(name = "ORAUSER")
	public String getOraUser() {
		return oraUser;
	}
	public void setOraUser(String oraUser) {
		this.oraUser = oraUser;
	}
	
	@Column(name = "SHDESC")
	public String getShDesc() {
		return shDesc;
	}
	public void setShDesc(String shDesc) {
		this.shDesc = shDesc;
	}
	
	@Column(name = "USR_PWD")
	public String getUsrPwd() {
		return usrPwd;
	}
	public void setUsrPwd(String usrPwd) {
		this.usrPwd = usrPwd;
	}
	
}
