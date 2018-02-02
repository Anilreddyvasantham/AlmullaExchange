package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BankExternalReferenceDailogDataTable {
	
	private BigDecimal bankExtRefDetailSeqId;
	private BigDecimal bankId;
	private String bankCode;
	private BigDecimal beneficaryBankId;
	private String beneficaryBankCode;
	private String branchExternalId;
	private BigDecimal branchId;
	private BigDecimal branchCode;
	private String createdBy; 
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal countryId;
	private String isActive;
	private BigDecimal bankExtRefSeqId;
	private String branchDescription;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String indic1;
	private String indic2;
	private String flexField1;
	private String flexField2;
	private String flexField3;
	
	private String dynamicLabelActivateDeactivate;
	private Boolean remarksCheck=false;
	private Boolean activeRecordCheck=false;
	private Boolean permentDeleteCheck=false;
	
	private Boolean updateStatus=false;
	
	private String branchExternalYesNo;
	
	public String getRemarks() {
		return remarks;
	}




	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public BigDecimal getBankExtRefDetailSeqId() {
		return bankExtRefDetailSeqId;
	}
	public void setBankExtRefDetailSeqId(BigDecimal bankExtRefDetailSeqId) {
		this.bankExtRefDetailSeqId = bankExtRefDetailSeqId;
	}
	public BigDecimal getBankExtRefSeqId() {
		return bankExtRefSeqId;
	}
	public void setBankExtRefSeqId(BigDecimal bankExtRefSeqId) {
		this.bankExtRefSeqId = bankExtRefSeqId;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBeneficaryBankCode() {
		return beneficaryBankCode;
	}
	public void setBeneficaryBankCode(String beneficaryBankCode) {
		this.beneficaryBankCode = beneficaryBankCode;
	}
	public BigDecimal getBranchId() {
		return branchId;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public BigDecimal getBeneficaryBankId() {
		return beneficaryBankId;
	}
	public void setBeneficaryBankId(BigDecimal beneficaryBankId) {
		this.beneficaryBankId = beneficaryBankId;
	}
	public BigDecimal getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchDescription() {
		return branchDescription;
	}
	public void setBranchDescription(String branchDescription) {
		this.branchDescription = branchDescription;
	}
	public String getBranchExternalId() {
		return branchExternalId;
	}
	public void setBranchExternalId(String branchExternalId) {
		this.branchExternalId = branchExternalId;
	}




	@Override
	public String toString() {
		return "BankExternalReferenceDailogDataTable [bankExtRefDetailSeqId=" + bankExtRefDetailSeqId + ", bankId=" + bankId + ", bankCode=" + bankCode + ", beneficaryBankId=" + beneficaryBankId
				+ ", beneficaryBankCode=" + beneficaryBankCode + ", branchExternalId=" + branchExternalId + ", branchId=" + branchId + ", branchCode=" + branchCode + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", countryId=" + countryId + ", isActive=" + isActive + ", bankExtRefSeqId="
				+ bankExtRefSeqId + ", branchDescription=" + branchDescription + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + ", remarks=" + remarks + "]";
	}




	public String getIndic1() {
		return indic1;
	}




	public void setIndic1(String indic1) {
		this.indic1 = indic1;
	}




	public String getIndic2() {
		return indic2;
	}




	public void setIndic2(String indic2) {
		this.indic2 = indic2;
	}




	public String getFlexField1() {
		return flexField1;
	}




	public void setFlexField1(String flexField1) {
		this.flexField1 = flexField1;
	}




	public String getFlexField2() {
		return flexField2;
	}




	public void setFlexField2(String flexField2) {
		this.flexField2 = flexField2;
	}




	public String getFlexField3() {
		return flexField3;
	}




	public void setFlexField3(String flexField3) {
		this.flexField3 = flexField3;
	}




	public String getDynamicLabelActivateDeactivate() {
		return dynamicLabelActivateDeactivate;
	}




	public void setDynamicLabelActivateDeactivate(
			String dynamicLabelActivateDeactivate) {
		this.dynamicLabelActivateDeactivate = dynamicLabelActivateDeactivate;
	}




	public Boolean getRemarksCheck() {
		return remarksCheck;
	}




	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}




	public Boolean getActiveRecordCheck() {
		return activeRecordCheck;
	}




	public void setActiveRecordCheck(Boolean activeRecordCheck) {
		this.activeRecordCheck = activeRecordCheck;
	}




	public Boolean getPermentDeleteCheck() {
		return permentDeleteCheck;
	}




	public void setPermentDeleteCheck(Boolean permentDeleteCheck) {
		this.permentDeleteCheck = permentDeleteCheck;
	}




	public String getBranchExternalYesNo() {
		return branchExternalYesNo;
	}




	public void setBranchExternalYesNo(String branchExternalYesNo) {
		this.branchExternalYesNo = branchExternalYesNo;
	}




	public Boolean getUpdateStatus() {
		return updateStatus;
	}




	public void setUpdateStatus(Boolean updateStatus) {
		this.updateStatus = updateStatus;
	}
	
	
	
	
}
