package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;




import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EX_AMTB_COUPON")
public class AmtbCoupon implements Serializable {
	

	
	private BigDecimal couponNo;
	private BigDecimal applDocumentId;
	private BigDecimal applDocumentNo;
	private BigDecimal applDocumentCode;
	private BigDecimal applDocumentFinancialyear;
	
	
	private BigDecimal remitDocumentId;
	private BigDecimal remitDocumentNo;
	private BigDecimal remitDocumentCode;
	private BigDecimal remitDocumentFinancialyear;
	
	private BigDecimal companyId;
	private BigDecimal applicationCountryId;
	private String civilId;
	
	private BigDecimal documentFinancialyear;
	private Date createdDate;
	
	
	
	
	@Id
	@Column(name="COUPON_NO")
	public BigDecimal getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(BigDecimal couponNo) {
		this.couponNo = couponNo;
	}

	
	@Id
	@Column(name="IDENTITY_INT")
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	
	@Id
	@Column(name="APPL_DOCUMENT_NO")
	public BigDecimal getApplDocumentNo() {
		return applDocumentNo;
	}
	public void setApplDocumentNo(BigDecimal applDocumentNo) {
		this.applDocumentNo = applDocumentNo;
	}
	@Column(name="APPL_DOCUMENT_CODE")
	public BigDecimal getApplDocumentCode() {
		return applDocumentCode;
	}
	public void setApplDocumentCode(BigDecimal applDocumentCode) {
		this.applDocumentCode = applDocumentCode;
	}
	@Column(name="APPL_DOCUMENT_FINANCE_YEAR")
	public BigDecimal getApplDocumentFinancialyear() {
		return applDocumentFinancialyear;
	}
	public void setApplDocumentFinancialyear(BigDecimal applDocumentFinancialyear) {
		this.applDocumentFinancialyear = applDocumentFinancialyear;
	}
	@Column(name="REMIT_DOCUMENT_NO")
	public BigDecimal getRemitDocumentNo() {
		return remitDocumentNo;
	}
	public void setRemitDocumentNo(BigDecimal remitDocumentNo) {
		this.remitDocumentNo = remitDocumentNo;
	}
	@Column(name="REMIT_DOCUMENT_CODE")
	public BigDecimal getRemitDocumentCode() {
		return remitDocumentCode;
	}
	public void setRemitDocumentCode(BigDecimal remitDocumentCode) {
		this.remitDocumentCode = remitDocumentCode;
	}
	@Column(name="REMIT_DOCUMENT_FINANCE_YEAR")
	public BigDecimal getRemitDocumentFinancialyear() {
		return remitDocumentFinancialyear;
	}
	public void setRemitDocumentFinancialyear(BigDecimal remitDocumentFinancialyear) {
		this.remitDocumentFinancialyear = remitDocumentFinancialyear;
	}
	@Column(name="COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	@Column(name="APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name="APPL_DOCUMENT_ID")
	public BigDecimal getApplDocumentId() {
		return applDocumentId;
	}
	public void setApplDocumentId(BigDecimal applDocumentId) {
		this.applDocumentId = applDocumentId;
	}
	@Column(name="REMIT_DOCUMENT_ID")
	public BigDecimal getRemitDocumentId() {
		return remitDocumentId;
	}
	public void setRemitDocumentId(BigDecimal remitDocumentId) {
		this.remitDocumentId = remitDocumentId;
	}
	@Column(name="DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinancialyear() {
		return documentFinancialyear;
	}
	public void setDocumentFinancialyear(BigDecimal documentFinancialyear) {
		this.documentFinancialyear = documentFinancialyear;
	}
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
