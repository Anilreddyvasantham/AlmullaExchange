package com.amg.exchange.registration.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SearchEntityBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal nationalityId;	
	private BigDecimal idType;
	private String idNumber;
	private String firstName;
	private String lastName;
	private Date dob;
	private String mobileNumber;
	private String idNum;
	private BigDecimal customerRef;
	
	
	public SearchEntityBean() {
	}
	public SearchEntityBean(BigDecimal nationalityId, BigDecimal idType,
			String idNumber, String firstName, String lastName, Date dob,
			String mobileNumber,String idNum) {
		this.nationalityId = nationalityId;
		this.idType = idType;
		this.idNumber = idNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.mobileNumber = mobileNumber;
		this.idNum=idNum;
	}
	public BigDecimal getNationalityId() {
		return nationalityId;
	}
	public void setNationalityId(BigDecimal nationalityId) {
		this.nationalityId = nationalityId;
	}
	public BigDecimal getIdType() {
		return idType;
	}
	public void setIdType(BigDecimal idType) {
		this.idType = idType;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "SearchEntityBean [nationalityId=" + nationalityId + ", idType="
				+ idType + ", idNumber=" + idNumber + ", firstName="
				+ firstName + ", lastName=" + lastName + ", dob=" + dob
				+ ", mobileNumber=" + mobileNumber + "]";
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public BigDecimal getCustomerRef() {
		return customerRef;
	}
	public void setCustomerRef(BigDecimal customerRef) {
		this.customerRef = customerRef;
	}
	
	
	
}
