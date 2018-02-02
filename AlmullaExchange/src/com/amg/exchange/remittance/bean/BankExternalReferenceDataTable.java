package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BankExternalReferenceDataTable {
	
	private String index;
	private BigDecimal bankExtRefDetailSeqId;
	private BigDecimal bankId;
	private String bankCode;
	private String bankName;
	private String bankExternalId;
	private  BigDecimal beneficaryBankId;
	private  String beneficaryBankCode;
	private String beneficaryBankName;
	private String createdBy ;
	private Date createdOn;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	
	private String approvedBy;
	private Date approvedDate;
	
	private BigDecimal countryId;
	private String countryName;
	
	private BigDecimal branchCode;
	private String branchDescription;
	
	private String branchExternalId;
	private String branchExternalName;
	private String remarks;
	private Boolean permanetDeleteCheck;
	
	
	//Added by Rabil on 06/02/2016
	private String flexField1;
	private String flexField2;
	private String flexField3;
	private String branchExternalYesNo;
	
	private Boolean updateStatus;
	
	
	private List<BankExternalReferenceDailogDataTable> dialogTable;
	
	
	private BigDecimal bankExtRefSeqId;
	
	private String dynamicLabelForActivateDeactivate;	
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public List<BankExternalReferenceDailogDataTable> getDialogTable() {
		return dialogTable;
	}

	public void setDialogTable(List<BankExternalReferenceDailogDataTable> dialogTable) {
		this.dialogTable = dialogTable;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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



	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public BankExternalReferenceDataTable() {
		super();
	}



	

	@Override
	public String toString() {
		return "BankExternalReferenceDataTable [index=" + index + ", bankExtRefDetailSeqId=" + bankExtRefDetailSeqId + ", bankId=" + bankId + ", bankCode=" + bankCode + ", bankName=" + bankName + ", bankExternalId=" + bankExternalId + ", beneficaryBankId=" + beneficaryBankId
				+ ", beneficaryBankCode=" + beneficaryBankCode + ", beneficaryBankName=" + beneficaryBankName + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", isActive=" + isActive + ", approvedBy=" + approvedBy
				+ ", approvedDate=" + approvedDate + ", countryId=" + countryId + ", countryName=" + countryName + ", branchCode=" + branchCode + ", branchDescription=" + branchDescription + ", branchExternalId=" + branchExternalId + ", branchExternalName=" + branchExternalName + ", remarks="
				+ remarks + ", permanetDeleteCheck=" + permanetDeleteCheck + ", dialogTable=" + dialogTable + ", bankExtRefSeqId=" + bankExtRefSeqId + ", dynamicLabelForActivateDeactivate=" + dynamicLabelForActivateDeactivate + "]";
	}

	public BankExternalReferenceDataTable(BigDecimal bankExtRefDetailSeqId, BigDecimal bankId, String bankCode, String bankName, String bankExternalId, BigDecimal beneficaryBankId,
			String beneficaryBankCode, String beneficaryBankName, String createdBy, Date createdOn, String modifiedBy, Date modifiedDate, String isActive, String approvedBy, Date approvedDate,
			BigDecimal countryId, String countryName, BigDecimal branchCode, String branchDescription, String branchExternalId, String branchExternalName, BigDecimal bankExtRefSeqId,
			String dynamicLabelForActivateDeactivate) {
		super();
		this.bankExtRefDetailSeqId = bankExtRefDetailSeqId;
		this.bankId = bankId;
		this.bankCode = bankCode;
		this.bankName = bankName;
		this.bankExternalId = bankExternalId;
		this.beneficaryBankId = beneficaryBankId;
		this.beneficaryBankCode = beneficaryBankCode;
		this.beneficaryBankName = beneficaryBankName;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.isActive = isActive;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.countryId = countryId;
		this.countryName = countryName;
		this.branchCode = branchCode;
		this.branchDescription = branchDescription;
		this.branchExternalId = branchExternalId;
		this.branchExternalName = branchExternalName;
		this.bankExtRefSeqId = bankExtRefSeqId;
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public BigDecimal getBankExtRefSeqId() {
		return bankExtRefSeqId;
	}




	public void setBankExtRefSeqId(BigDecimal bankExtRefSeqId) {
		this.bankExtRefSeqId = bankExtRefSeqId;
	}




	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getBeneficaryBankCode() {
		return beneficaryBankCode;
	}
	public void setBeneficaryBankCode(String beneficaryBankCode) {
		this.beneficaryBankCode = beneficaryBankCode;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public String getBranchExternalName() {
		return branchExternalName;
	}
	public void setBranchExternalName(String branchExternalName) {
		this.branchExternalName = branchExternalName;
	}
	public String getBankExternalId() {
		return bankExternalId;
	}
	public void setBankExternalId(String bankExternalId) {
		this.bankExternalId = bankExternalId;
	}
	public BigDecimal getBeneficaryBankId() {
		return beneficaryBankId;
	}
	public void setBeneficaryBankId(BigDecimal beneficaryBankId) {
		this.beneficaryBankId = beneficaryBankId;
	}
	public String getBeneficaryBankName() {
		return beneficaryBankName;
	}
	public void setBeneficaryBankName(String beneficaryBankName) {
		this.beneficaryBankName = beneficaryBankName;
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

	public Boolean getPermanetDeleteCheck() {
		return permanetDeleteCheck;
	}
	public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
		this.permanetDeleteCheck = permanetDeleteCheck;
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
