package com.amg.exchange.registration.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ArticleLevelDataTable{

	private BigDecimal articleDetailId;
	private BigDecimal articleId;
	private String articleDesc;
	private String articleLocalDesc;
	private BigDecimal weekly;
	private BigDecimal monthly;
	private BigDecimal yearly;
	private String articleDetailCode;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String isActive;
	private BigDecimal languageId;
	private String dynamicLabelForActivateDeactivate;
	private Boolean remarksCheck;
	
	private BigDecimal languageEnglishId;
	private BigDecimal languageArabicId;
	
	private BigDecimal articleDetailsEnglishDescId;
	private BigDecimal articleDetailsArabicDescId;
	private BigDecimal articleDetailsDescId;
	
	private String articleName;
	

	private Boolean activateRecordCheck = false;
	
	
	
	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}

	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}

	public BigDecimal getArticleDetailId() {
		return articleDetailId;
	}

	public void setArticleDetailId(BigDecimal articleDetailId) {
		this.articleDetailId = articleDetailId;
	}

	public String getArticleDesc() {
		return articleDesc;
	}

	public void setArticleDesc(String articleDesc) {
		this.articleDesc = articleDesc;
	}

	public BigDecimal getWeekly() {
		return weekly;
	}

	public void setWeekly(BigDecimal weekly) {
		this.weekly = weekly;
	}

	public BigDecimal getMonthly() {
		return monthly;
	}

	public void setMonthly(BigDecimal monthly) {
		this.monthly = monthly;
	}

	public BigDecimal getYearly() {
		return yearly;
	}

	public void setYearly(BigDecimal yearly) {
		this.yearly = yearly;
	}

	public String getArticleDetailCode() {
		return articleDetailCode;
	}

	public void setArticleDetailCode(String articleDetailCode) {
		this.articleDetailCode = articleDetailCode;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getLanguageId() {
		return languageId;
	}

	public void setLanguageId(BigDecimal languageId) {
		this.languageId = languageId;
	}

	public String getArticleLocalDesc() {
		return articleLocalDesc;
	}

	public void setArticleLocalDesc(String articleLocalDesc) {
		this.articleLocalDesc = articleLocalDesc;
	}

	@Override
	public String toString() {
		return "ArticleLevelDataTable [toString()=" + super.toString() + "]";
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getRemarksCheck() {
		return remarksCheck;
	}

	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}

	public BigDecimal getLanguageEnglishId() {
		return languageEnglishId;
	}

	public void setLanguageEnglishId(BigDecimal languageEnglishId) {
		this.languageEnglishId = languageEnglishId;
	}

	public BigDecimal getLanguageArabicId() {
		return languageArabicId;
	}

	public void setLanguageArabicId(BigDecimal languageArabicId) {
		this.languageArabicId = languageArabicId;
	}

	public BigDecimal getArticleDetailsEnglishDescId() {
		return articleDetailsEnglishDescId;
	}

	public void setArticleDetailsEnglishDescId(
			BigDecimal articleDetailsEnglishDescId) {
		this.articleDetailsEnglishDescId = articleDetailsEnglishDescId;
	}

	public BigDecimal getArticleDetailsArabicDescId() {
		return articleDetailsArabicDescId;
	}

	public void setArticleDetailsArabicDescId(BigDecimal articleDetailsArabicDescId) {
		this.articleDetailsArabicDescId = articleDetailsArabicDescId;
	}

	public BigDecimal getArticleDetailsDescId() {
		return articleDetailsDescId;
	}

	public void setArticleDetailsDescId(BigDecimal articleDetailsDescId) {
		this.articleDetailsDescId = articleDetailsDescId;
	}

	public BigDecimal getArticleId() {
		return articleId;
	}

	public void setArticleId(BigDecimal articleId) {
		this.articleId = articleId;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}
	
	

}
