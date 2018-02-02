package com.amg.exchange.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BankAccountTypeDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private BigDecimal bankAccountTypeId;
	private String bankAccountTypeCode;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String BankAccountTypeArabicDesc;
	private String BankAccountTypeEnglishDesc;
	private BigDecimal bankAccountTypeDescArabicId;
	private BigDecimal bankAccountTypeDescEnglishId;
	private BigDecimal arabicLanguageId;
	private BigDecimal englishLanguageId;
	
	private BigDecimal languageId;
	private String bankAccountTypeDesc;
	private BigDecimal bankAccountTypeDescId;
	
	public BigDecimal getBankAccountTypeId() {
		return bankAccountTypeId;
	}
	public void setBankAccountTypeId(BigDecimal bankAccountTypeId) {
		this.bankAccountTypeId = bankAccountTypeId;
	}
	public String getBankAccountTypeCode() {
		return bankAccountTypeCode;
	}
	public void setBankAccountTypeCode(String bankAccountTypeCode) {
		this.bankAccountTypeCode = bankAccountTypeCode;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	public String getBankAccountTypeArabicDesc() {
		return BankAccountTypeArabicDesc;
	}
	public void setBankAccountTypeArabicDesc(String bankAccountTypeArabicDesc) {
		BankAccountTypeArabicDesc = bankAccountTypeArabicDesc;
	}
	public String getBankAccountTypeEnglishDesc() {
		return BankAccountTypeEnglishDesc;
	}
	public void setBankAccountTypeEnglishDesc(String bankAccountTypeEnglishDesc) {
		BankAccountTypeEnglishDesc = bankAccountTypeEnglishDesc;
	}
	public BigDecimal getBankAccountTypeDescArabicId() {
		return bankAccountTypeDescArabicId;
	}
	public void setBankAccountTypeDescArabicId(
			BigDecimal bankAccountTypeDescArabicId) {
		this.bankAccountTypeDescArabicId = bankAccountTypeDescArabicId;
	}
	public BigDecimal getBankAccountTypeDescEnglishId() {
		return bankAccountTypeDescEnglishId;
	}
	public void setBankAccountTypeDescEnglishId(
			BigDecimal bankAccountTypeDescEnglishId) {
		this.bankAccountTypeDescEnglishId = bankAccountTypeDescEnglishId;
	}
	public BigDecimal getArabicLanguageId() {
		return arabicLanguageId;
	}
	public void setArabicLanguageId(BigDecimal arabicLanguageId) {
		this.arabicLanguageId = arabicLanguageId;
	}
	public BigDecimal getEnglishLanguageId() {
		return englishLanguageId;
	}
	public void setEnglishLanguageId(BigDecimal englishLanguageId) {
		this.englishLanguageId = englishLanguageId;
	}
	public BigDecimal getLanguageId() {
		return languageId;
	}
	public void setLanguageId(BigDecimal languageId) {
		this.languageId = languageId;
	}
	
	public BigDecimal getBankAccountTypeDescId() {
		return bankAccountTypeDescId;
	}
	public void setBankAccountTypeDescId(BigDecimal bankAccountTypeDescId) {
		this.bankAccountTypeDescId = bankAccountTypeDescId;
	}
	public String getBankAccountTypeDesc() {
		return bankAccountTypeDesc;
	}
	public void setBankAccountTypeDesc(String bankAccountTypeDesc) {
		this.bankAccountTypeDesc = bankAccountTypeDesc;
	}
	
	
	
}
