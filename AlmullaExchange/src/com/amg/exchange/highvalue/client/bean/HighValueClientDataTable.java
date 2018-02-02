package com.amg.exchange.highvalue.client.bean;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.primefaces.event.SelectEvent;

import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.util.Constants;
import com.sun.org.apache.bcel.internal.generic.Select;

public class HighValueClientDataTable {

	  private BigDecimal locationId;
	  private String locationName;
	  private Date logDate;
	  private BigDecimal customerReferenceNo;
	  private String customerName;
	  private BigDecimal mobileNumber;
	  private Date visitDate;
	  private String visitBy;
	  private BigDecimal totalTranscation;
	  private BigDecimal transcationAmount;
	  private Date minDate=new Date();
	  private Date effectiveMaxDate;
	 private Boolean booRead=false;
	 
	  
	  public BigDecimal getLocationId() {
	  	  return locationId;
	  }
	  public void setLocationId(BigDecimal locationId) {
	  	  this.locationId = locationId;
	  }
	  public String getLocationName() {
	  	  return locationName;
	  }
	  public void setLocationName(String locationName) {
	  	  this.locationName = locationName;
	  }
	  public Date getLogDate() {
	  	  return logDate;
	  }
	  public void setLogDate(Date logDate) {
	  	  this.logDate = logDate;
	  }
	  public BigDecimal getCustomerReferenceNo() {
	  	  return customerReferenceNo;
	  }
	  public void setCustomerReferenceNo(BigDecimal customerReferenceNo) {
	  	  this.customerReferenceNo = customerReferenceNo;
	  }
	  public String getCustomerName() {
	  	  return customerName;
	  }
	  public void setCustomerName(String customerName) {
	  	  this.customerName = customerName;
	  }
	  public BigDecimal getMobileNumber() {
	  	  return mobileNumber;
	  }
	  public void setMobileNumber(BigDecimal mobileNumber) {
	  	  this.mobileNumber = mobileNumber;
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
	  public BigDecimal getTotalTranscation() {
	  	  return totalTranscation;
	  }
	  public void setTotalTranscation(BigDecimal totalTranscation) {
	  	  this.totalTranscation = totalTranscation;
	  }
	  public BigDecimal getTranscationAmount() {
	  	  return transcationAmount;
	  }
	  public void setTranscationAmount(BigDecimal transcationAmount) {
	  	  this.transcationAmount = transcationAmount;
	  }
	  public Date getMinDate() {
	  	  return minDate;
	  }
	  public void setMinDate(Date minDate) {
	  	  this.minDate = minDate;
	  }
	  public Date getEffectiveMaxDate() {
		    Date now = new Date();

			Calendar cal = Calendar.getInstance();

			cal.setTime(now);

			cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(Constants.FX_DEAL_WITH_SUPPLIER_DEAL_DATE_ALLOW));

			Date tomorrow = cal.getTime();
			effectiveMaxDate=tomorrow;
			return effectiveMaxDate;
	  }
	  public void setEffectiveMaxDate(Date effectiveMaxDate) {
	  	  this.effectiveMaxDate = effectiveMaxDate;
	  }
	  public Boolean getBooRead() {
	  	  return booRead;
	  }
	  public void setBooRead(Boolean booRead) {
	  	  this.booRead = booRead;
	  }
	  
	  
	  
	  
	  
}
