package com.amg.exchange.highvalue.client.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_HIGH_VALUE_CUSTOMER_TXN")
public class HighValueCustomerTxnView implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal idNo;
	private BigDecimal customerReference;
	private BigDecimal customerId;
	private Date logDate;
	private Date visitDate;
	private String visitBy;
	private BigDecimal locationId;
	private Date expiryDate;
	private Date profileDate;
	private BigDecimal totalRemittance;
	private BigDecimal totalTransaction;
	private BigDecimal civilId;
	private String firstName;

	public HighValueCustomerTxnView() {

	}

	public HighValueCustomerTxnView(BigDecimal idNo, BigDecimal customerId, Date logDate, Date visitDate, String visitBy, BigDecimal locationId, Date expiryDate, Date profileDate, BigDecimal totalRemittance, BigDecimal totalTransaction,String firstName) {
		super();
		this.idNo = idNo;
		this.customerId = customerId;
		this.logDate = logDate;
		this.visitDate = visitDate;
		this.visitBy = visitBy;
		this.locationId = locationId;
		this.expiryDate = expiryDate;
		this.profileDate = profileDate;
		this.totalRemittance = totalRemittance;
		this.totalTransaction = totalTransaction;
		this.firstName = firstName;
	}

	@Id
	@Column(name = "IDNO")
	public BigDecimal getIdNo() {
		return idNo;
	}

	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}

	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	@Column(name = "LOG_DATE")
	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	@Column(name = "VISIT_DATE")
	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	@Column(name = "VISIT_BY")
	public String getVisitBy() {
		return visitBy;
	}

	public void setVisitBy(String visitBy) {
		this.visitBy = visitBy;
	}

	@Column(name = "LOCATION_ID")
	public BigDecimal getLocationId() {
		return locationId;
	}

	public void setLocationId(BigDecimal locationId) {
		this.locationId = locationId;
	}

	@Column(name = "EXPIRY_DATE")
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Column(name = "PROFILE_DATE")
	public Date getProfileDate() {
		return profileDate;
	}

	public void setProfileDate(Date profileDate) {
		this.profileDate = profileDate;
	}

	@Column(name = "TOTAL_REMITTANCE")
	public BigDecimal getTotalRemittance() {
		return totalRemittance;
	}

	public void setTotalRemittance(BigDecimal totalRemittance) {
		this.totalRemittance = totalRemittance;
	}

	@Column(name = "TOTAL_TRANSACTION")
	public BigDecimal getTotalTransaction() {
		return totalTransaction;
	}

	public void setTotalTransaction(BigDecimal totalTransaction) {
		this.totalTransaction = totalTransaction;
	}
	
	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "CUSTOMER_REFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	
	@Column(name = "CIVIL_ID")
	public BigDecimal getCivilId() {
		return civilId;
	}

	public void setCivilId(BigDecimal civilId) {
		this.civilId = civilId;
	}

}
