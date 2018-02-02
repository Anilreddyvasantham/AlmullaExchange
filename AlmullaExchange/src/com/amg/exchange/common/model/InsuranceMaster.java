package com.amg.exchange.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/*******************************************************************************************************************
 * File : InsuranceMaster.java
 * 
 * Project : AlMulla Exchange Online Remittance System
 * 
 * Package : com.amg.exchange.treasury.model
 * 
 * Created : Date : 08-07-2015 3:46:42  : Nagarjuna        Revision:
 * 
 * Last Change: Date : 2015-07-09 By    : Nagarjuna        Revision:
 * 
 * Description: TODO
 ********************************************************************************************************************/
@Entity
@Table(name="EX_INSURANCE_MASTER")
public class InsuranceMaster implements Serializable {
	
private static final long serialVersionUID = 1L;

private BigDecimal insuranceSetUpId;
private CountryMaster applicationCountryId;
private CompanyMaster fsCompanyMaster;
private Date fromDate;
private Date toDate;
private BigDecimal fromAmount;
private BigDecimal toAmount;
private BigDecimal insuranceDays;
private BigDecimal insuranceLoyaltyPoints;
 
private BigDecimal insuranceAmount;
private BigDecimal insuranceAdditionalAmount;
private String isActive;
private String createdBy;
private String modifiedBy;
private Date createdDate;
private Date modifiedDate; 
private String remarks;
private String approvedBy;
private Date approvedDate;



List<InsuranceMasterDescription> exInsuranceMasterDesc=new ArrayList<InsuranceMasterDescription>();


public  InsuranceMaster(){
	
}
public InsuranceMaster(BigDecimal insuranceSetUpId,
		CountryMaster applicationCountryId, CompanyMaster fsCompanyMaster,
		Date fromDate, Date toDate, BigDecimal fromAmount, BigDecimal toAmount,
		BigDecimal insuranceDays, BigDecimal insuranceLoyaltyPoints,
	 	BigDecimal insuranceAmount, BigDecimal insuranceAdditionalAmount,
		String isActive, String createdBy, String modifiedBy,
		  Date createdDate, Date modifiedDate, String remarks,List<InsuranceMasterDescription> exInsuranceMasterDesc) {
	super();
	this.insuranceSetUpId = insuranceSetUpId;
	this.applicationCountryId = applicationCountryId;
	this.fsCompanyMaster = fsCompanyMaster;
	this.fromDate = fromDate;
	this.toDate = toDate;
	this.fromAmount = fromAmount;
	this.toAmount = toAmount;
	this.insuranceDays = insuranceDays;
	this.insuranceLoyaltyPoints = insuranceLoyaltyPoints;
	this.insuranceAmount = insuranceAmount;
	this.insuranceAdditionalAmount = insuranceAdditionalAmount;
	this.isActive = isActive;
	this.createdBy = createdBy;
	this.modifiedBy = modifiedBy;
	this.createdDate = createdDate;
	this.modifiedDate = modifiedDate;
	this.remarks = remarks;
	this.exInsuranceMasterDesc=exInsuranceMasterDesc;
}
@Id
@GeneratedValue(generator="ex_insurance_master_seq",strategy=GenerationType.SEQUENCE)
@SequenceGenerator(name="ex_insurance_master_seq",sequenceName="EX_INSURANCE_MASTER_SEQ",allocationSize=1)
@Column(name = "INSURANCE_MASTER_ID", unique = true, nullable = false, precision = 22, scale = 0)
public BigDecimal getInsuranceSetUpId() {
	return insuranceSetUpId;
}
public void setInsuranceSetUpId(BigDecimal insuranceSetUpId) {
	this.insuranceSetUpId = insuranceSetUpId;
}
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "APPLICATION_COUNTRY_ID")
public CountryMaster getApplicationCountryId() {
	return applicationCountryId;
}
public void setApplicationCountryId(CountryMaster applicationCountryId) {
	this.applicationCountryId = applicationCountryId;
}
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "COMPANY_ID")
public CompanyMaster getFsCompanyMaster() {
	return fsCompanyMaster;
}
public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
	this.fsCompanyMaster = fsCompanyMaster;
}
@Column(name="FROM_DATE")
public Date getFromDate() {
	return fromDate;
}
public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
}
@Column(name="TO_DATE")
public Date getToDate() {
	return toDate;
}
public void setToDate(Date toDate) {
	this.toDate = toDate;
}
@Column(name="FROM_AMOUNT")
public BigDecimal getFromAmount() {
	return fromAmount;
}
public void setFromAmount(BigDecimal fromAmount) {
	this.fromAmount = fromAmount;
}
@Column(name="TO_AMOUNT")
public BigDecimal getToAmount() {
	return toAmount;
}
public void setToAmount(BigDecimal toAmount) {
	this.toAmount = toAmount;
}
@Column(name="INSURANCE_DAYS")
public BigDecimal getInsuranceDays() {
	return insuranceDays;
}
public void setInsuranceDays(BigDecimal insuranceDays) {
	this.insuranceDays = insuranceDays;
}
@Column(name="INSURANCE_LOYALTY_POINTS")
public BigDecimal getInsuranceLoyaltyPoints() {
	return insuranceLoyaltyPoints;
}
public void setInsuranceLoyaltyPoints(BigDecimal insuranceLoyaltyPoints) {
	this.insuranceLoyaltyPoints = insuranceLoyaltyPoints;
}
 @Column(name="INSURANCE_AMOUNT")
public BigDecimal getInsuranceAmount() {
	return insuranceAmount;
}
public void setInsuranceAmount(BigDecimal insuranceAmount) {
	this.insuranceAmount = insuranceAmount;
}
@Column(name="INSURANCE_ADDITIONAL_AMOUNT")
public BigDecimal getInsuranceAdditionalAmount() {
	return insuranceAdditionalAmount;
}
public void setInsuranceAdditionalAmount(BigDecimal insuranceAdditionalAmount) {
	this.insuranceAdditionalAmount = insuranceAdditionalAmount;
}
@Column(name="ISACTIVE")
public String getIsActive() {
	return isActive;
}
public void setIsActive(String isActive) {
	this.isActive = isActive;
}
@Column(name="CREATED_BY")
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
@Column(name="MODIFIED_BY")
public String getModifiedBy() {
	return modifiedBy;
}
public void setModifiedBy(String modifiedBy) {
	
	this.modifiedBy = modifiedBy;
}
 
 
@Column(name="CREATED_DATE")
public Date getCreatedDate() {
	return createdDate;
}
public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}
@Column(name="MODIFIED_DATE")
public Date getModifiedDate() {
	return modifiedDate;
}
public void setModifiedDate(Date modifiedDate) {
	this.modifiedDate = modifiedDate;
}
 
@Column(name="REMARKS")
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}
@OneToMany(fetch = FetchType.LAZY, mappedBy = "insuranceMasterId")
public List<InsuranceMasterDescription> getExInsuranceMasterDesc() {
	return exInsuranceMasterDesc;
}
public void setExInsuranceMasterDesc(
		List<InsuranceMasterDescription> exInsuranceMasterDesc) {
	this.exInsuranceMasterDesc = exInsuranceMasterDesc;
}
@Column(name="APPROVED_BY")
public String getApprovedBy() {
	return approvedBy;
}
public void setApprovedBy(String approvedBy) {
	this.approvedBy = approvedBy;
}
@Column(name="APPROVED_DATE")
public Date getApprovedDate() {
	return approvedDate;
}
public void setApprovedDate(Date approvedDate) {
	this.approvedDate = approvedDate;
}

}
