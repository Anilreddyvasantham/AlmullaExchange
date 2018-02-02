package com.amg.exchange.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_CUSTOMER_DETAILS")
public class CutomerDetailsView implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUSTOMER_ID")
	private BigDecimal customerId;
	
	@Column(name="CUSTOMER_NAME")
	private String customerName;
	
	@Column(name="CONTCAT_NUMBER")
	private String contactNumber;
	
	@Column(name="ID_TYPE")
	private BigDecimal idType;
	
	@Column(name="ID_NUMBER")
	private String idNumber;
	
	@Column(name="ID_EXPIRY_DATE")
	private Date idExp;
	
	

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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

	public Date getIdExp() {
		return idExp;
	}

	public void setIdExp(Date idExp) {
		this.idExp = idExp;
	}
	
	
	
}
