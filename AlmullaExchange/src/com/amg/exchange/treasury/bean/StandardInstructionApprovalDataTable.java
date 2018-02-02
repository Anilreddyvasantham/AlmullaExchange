package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StandardInstructionApprovalDataTable implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigDecimal standardInstructionId;
	private BigDecimal fsCompanyMaster;
	private String companyName;
	private BigDecimal exBankMaster;
	private String bankName;
	private BigDecimal exCurrenyMaster;
	private String currencyName;
//	private BigDecimal standardInsructionNumber;
	private String instructionDescription;
	private String isActive;
	private String approveBy;
	private Date approveDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal companyId;
	
	private String instructionType;
	private BigDecimal accountDetailsId;
	private String accountNumber;
	
	private BigDecimal standardInstructionDetailsId = null;
	

	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getInstructionType() {
		return instructionType;
	}

	public BigDecimal getAccountDetailsId() {
		return accountDetailsId;
	}

	public void setInstructionType(String instructionType) {
		this.instructionType = instructionType;
	}

	public void setAccountDetailsId(BigDecimal accountDetailsId) {
		this.accountDetailsId = accountDetailsId;
	}

	public BigDecimal getStandardInstructionDetailsId() {
		return standardInstructionDetailsId;
	}

	public void setStandardInstructionDetailsId(
			BigDecimal standardInstructionDetailsId) {
		this.standardInstructionDetailsId = standardInstructionDetailsId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getStandardInstructionId() {
		return standardInstructionId;
	}
	
	public void setStandardInstructionId(BigDecimal standardInstructionId) {
		this.standardInstructionId = standardInstructionId;
	}
	
	public BigDecimal getFsCompanyMaster() {
		return fsCompanyMaster;
	}
	
	public void setFsCompanyMaster(BigDecimal fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}
	
	public BigDecimal getExBankMaster() {
		return exBankMaster;
	}
	
	public void setExBankMaster(BigDecimal exBankMaster) {
		this.exBankMaster = exBankMaster;
	}
	
	public BigDecimal getExCurrenyMaster() {
		return exCurrenyMaster;
	}
	
	public void setExCurrenyMaster(BigDecimal exCurrenyMaster) {
		this.exCurrenyMaster = exCurrenyMaster;
	}
	
	/*public BigDecimal getStandardInsructionNumber() {
		return standardInsructionNumber;
	}
	
	public void setStandardInsructionNumber(BigDecimal standardInsructionNumber) {
		this.standardInsructionNumber = standardInsructionNumber;
	}*/
	
	public String getInstructionDescription() {
		return instructionDescription;
	}
	
	public void setInstructionDescription(String instructionDescription) {
		this.instructionDescription = instructionDescription;
	}
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public String getApproveBy() {
		return approveBy;
	}
	
	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}
	
	public Date getApproveDate() {
		return approveDate;
	}
	
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
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
	
	
	
}
