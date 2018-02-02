package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_EX_USER_TYPE")
public class ViewUserType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal slNo;
	private String userType;
	private String userDesc;
	
	@Id
	@Column(name="SLNO")
	public BigDecimal getSlNo() {
		return slNo;
	}
	public void setSlNo(BigDecimal slNo) {
		this.slNo = slNo;
	}
	@Column(name="USER_TYPE")
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@Column(name="USER_DESCRIPTION")
	public String getUserDesc() {
		return userDesc;
	}
	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}
	
	
	
	
}
