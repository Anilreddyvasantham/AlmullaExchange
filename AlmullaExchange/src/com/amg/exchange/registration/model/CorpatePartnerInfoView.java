package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Author Rahamathali Shaik
*/
@Entity
@Table(name= "VW_CORP_PARTNER_INFO")
public class CorpatePartnerInfoView implements Serializable{

	private static final long serialVersionUID = 1L;
	private BigDecimal corporatePartnerIdNumber;
	private BigDecimal customerId;
	private BigDecimal refCustomerId;
	private BigDecimal partnerPercentage;
	private Date validUpto;
	private Date effectiveDate;
	private String customerName;
	private String customerNameLocal;
	private String idNumber;
	private Date idProofExpDate;
	private String mobile;
	private String email;
	private String designation;
	
	
	
	
	@Column(name = "CUSTOMER_NAME_LOCAL")
	public String getCustomerNameLocal() {
		return customerNameLocal;
	}

	public void setCustomerNameLocal(String customerNameLocal) {
		this.customerNameLocal = customerNameLocal;
	}

	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	
	@Column(name = "CORPORATE_PARTNER_ID")
	public BigDecimal getCorporatePartnerIdNumber() {
		return corporatePartnerIdNumber;
	}

	@Id
	@Column(name = "REF_CUSTOMER_ID")
	public BigDecimal getRefCustomerId() {
		return refCustomerId;
	}
	@Column(name = "PARTNER_PERCENTAGE")
	public BigDecimal getPartnerPercentage() {
		return partnerPercentage;
	}
	@Column(name = "VALID_UPTO")
	public Date getValidUpto() {
		return validUpto;
	}
	@Column(name = "EFFECTIVE_DATE")
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	@Column(name = "CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}
	@Column(name = "IDNUMBER")
	public String getIdNumber() {
		return idNumber;
	}
	@Column(name = "IDPROOF_EXPIREDATE")
	public Date getIdProofExpDate() {
		return idProofExpDate;
	}
	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}
	@Column(name = "DESIGNATION")
	public String getDesignation() {
		return designation;
	}
	
	public void setCorporatePartnerIdNumber(BigDecimal corporatePartnerIdNumber) {
		this.corporatePartnerIdNumber = corporatePartnerIdNumber;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setRefCustomerId(BigDecimal refCustomerId) {
		this.refCustomerId = refCustomerId;
	}
	public void setPartnerPercentage(BigDecimal partnerPercentage) {
		this.partnerPercentage = partnerPercentage;
	}
	public void setValidUpto(Date validUpto) {
		this.validUpto = validUpto;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public void setIdProofExpDate(Date idProofExpDate) {
		this.idProofExpDate = idProofExpDate;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	

}
