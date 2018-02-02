package com.amg.exchange.highvalue.client.bean;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.amg.exchange.util.Constants;

public class HighValueCustomerTxnDataTable {

	private BigDecimal customerId;
	private Date logDate;
	private Date visitDate;
	private String visitBy;
	private BigDecimal locationId;
	private Date expiryDate;
	private Date profileDate;
	private BigDecimal totalRemittance;
	private BigDecimal totalTransaction;
	private String logoPath;
	private String firstName;
	private BigDecimal civilId;
	private BigDecimal customerReference;
	
	private Date fromDate;
	private Date toDate;
	
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public String getVisitBy() {
		return visitBy;
	}
	public void setVisitBy(String visitBy) {
		this.visitBy = visitBy;
	}
	public BigDecimal getLocationId() {
		return locationId;
	}
	public void setLocationId(BigDecimal locationId) {
		this.locationId = locationId;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Date getProfileDate() {
		return profileDate;
	}
	public void setProfileDate(Date profileDate) {
		this.profileDate = profileDate;
	}
	public BigDecimal getTotalRemittance() {
		return totalRemittance;
	}
	public void setTotalRemittance(BigDecimal totalRemittance) {
		this.totalRemittance = totalRemittance;
	}
	public BigDecimal getTotalTransaction() {
		return totalTransaction;
	}
	public void setTotalTransaction(BigDecimal totalTransaction) {
		this.totalTransaction = totalTransaction;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public BigDecimal getCivilId() {
		return civilId;
	}
	public void setCivilId(BigDecimal civilId) {
		this.civilId = civilId;
	}
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	
}
