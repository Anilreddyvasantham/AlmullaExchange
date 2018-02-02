package com.amg.exchange.wuh2h.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EX_WU_RPT_TERMS_CONDITIONS")
public class WUReportTermsAndConditions implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal temsId;
	private BigDecimal lineNo;
	private String englishMessage;
	private String arabicMessage;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	
	public WUReportTermsAndConditions(){
		
	}	
	
	public WUReportTermsAndConditions(BigDecimal temsId, BigDecimal lineNo,
			String englishMessage, String arabicMessage, String isActive,
			String createdBy, Date createdDate) {
		super();
		this.temsId = temsId;
		this.lineNo = lineNo;
		this.englishMessage = englishMessage;
		this.arabicMessage = arabicMessage;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}


	@Id
	@Column(name = "EX_WU_RPT_TERMS_CONDITIONS_ID")
	public BigDecimal getTemsId() {
		return temsId;
	}
	public void setTemsId(BigDecimal temsId) {
		this.temsId = temsId;
	}
	
	@Column(name = "LINE_NO")
	public BigDecimal getLineNo() {
		return lineNo;
	}
	public void setLineNo(BigDecimal lineNo) {
		this.lineNo = lineNo;
	}
	
	@Column(name = "CONDITION_EN_MESSAGE")
	public String getEnglishMessage() {
		return englishMessage;
	}
	public void setEnglishMessage(String englishMessage) {
		this.englishMessage = englishMessage;
	}
	
	@Column(name = "CONDITION_AR_MESSAGE")
	public String getArabicMessage() {
		return arabicMessage;
	}
	public void setArabicMessage(String arabicMessage) {
		this.arabicMessage = arabicMessage;
	}
	
	@Column(name = "IS_ACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}	
		
}
