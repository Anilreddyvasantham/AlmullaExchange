package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Rahamathali Shaik
 * 
 */
@Entity
@Table(name = "VW_ALL_BENEFICIARY")
public class AllBeneficiaryView implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "BENE_NAME")
	private String beneName;
	
	@Column(name = "AR_BENE_NAME")
	private String arBeneName;
	
	@Column(name = "RELATIONSHIP_ID")
	private BigDecimal relationshipId;
	
	@Column(name = "RELATION_NAME")
	private String relationName;
	
	@Column(name = "BANK_ID")
	private BigDecimal bankId;
	
	@Column(name = "BANK_NAME")
	private String bankName;
	
	@Column(name = "BANK_BRANCH_ID")
	private BigDecimal bankBranchId;
	
	@Column(name = "BANK_BRANCH_NAME")
	private String bankBranchName;
	
	@Column(name = "BANK_ACCOUNT_NUMBER")
	private String bankAccountNumber;
	
	@Column(name = "CURRENCY_ID")
	private BigDecimal currecnyId;
	
	@Column(name = "CURRENCY_NAME")
	private String currencyName;
	
	@Column(name = "BENEFICARY_COUNTRY")
	private BigDecimal beneCountry;
	
	@Column(name = "BENEFICARY_BANK_COUNTRY_NAME")
	private String beneBankCountryName;
	
	@Column(name = "SERVICE_GROUP_CODE")
	private String serverviceGroupCode;
	
	@Column(name = "CURRENCY_QUOTE_NAME")
	private String currencyQuoteName;
	
	@Column(name = "BENEFICARY_ACCOUNT_SEQ_ID")
	private BigDecimal beneficiaryAccountSeqId;
	
	@Id
	@Column(name = "BENEFICARY_RELATIONSHIP_SEQ_ID")
	private BigDecimal beneficiaryRelationShipSeqId;
	
	@Column(name = "CUSTOMER_ID")
	private BigDecimal customerId;
	
	@Column(name = "BENEFICARY_MASTER_SEQ_ID")
	private BigDecimal beneficiaryMasterSeqId;
	
	@Column(name = "ISACTIVE")
	private String isActive;
	
	@Column(name = "ISACTIVE_DESC")
	private String isActiveDesc;
	
	@Column(name = "REMARKS")
	private String remarks;
	
	
	
	
	

	public String getBeneName() {
		return beneName;
	}

	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}

	public String getArBeneName() {
		return arBeneName;
	}

	public void setArBeneName(String arBeneName) {
		this.arBeneName = arBeneName;
	}

	public BigDecimal getRelationshipId() {
		return relationshipId;
	}

	public void setRelationshipId(BigDecimal relationshipId) {
		this.relationshipId = relationshipId;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public BigDecimal getCurrecnyId() {
		return currecnyId;
	}

	public void setCurrecnyId(BigDecimal currecnyId) {
		this.currecnyId = currecnyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public BigDecimal getBeneCountry() {
		return beneCountry;
	}

	public void setBeneCountry(BigDecimal beneCountry) {
		this.beneCountry = beneCountry;
	}

	public String getBeneBankCountryName() {
		return beneBankCountryName;
	}

	public void setBeneBankCountryName(String beneBankCountryName) {
		this.beneBankCountryName = beneBankCountryName;
	}

	public String getServerviceGroupCode() {
		return serverviceGroupCode;
	}

	public void setServerviceGroupCode(String serverviceGroupCode) {
		this.serverviceGroupCode = serverviceGroupCode;
	}

	public String getCurrencyQuoteName() {
		return currencyQuoteName;
	}

	public void setCurrencyQuoteName(String currencyQuoteName) {
		this.currencyQuoteName = currencyQuoteName;
	}

	public BigDecimal getBeneficiaryAccountSeqId() {
		return beneficiaryAccountSeqId;
	}

	public void setBeneficiaryAccountSeqId(BigDecimal beneficiaryAccountSeqId) {
		this.beneficiaryAccountSeqId = beneficiaryAccountSeqId;
	}

	public BigDecimal getBeneficiaryRelationShipSeqId() {
		return beneficiaryRelationShipSeqId;
	}

	public void setBeneficiaryRelationShipSeqId(
			BigDecimal beneficiaryRelationShipSeqId) {
		this.beneficiaryRelationShipSeqId = beneficiaryRelationShipSeqId;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getBeneficiaryMasterSeqId() {
		return beneficiaryMasterSeqId;
	}

	public void setBeneficiaryMasterSeqId(BigDecimal beneficiaryMasterSeqId) {
		this.beneficiaryMasterSeqId = beneficiaryMasterSeqId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsActiveDesc() {
		return isActiveDesc;
	}

	public void setIsActiveDesc(String isActiveDesc) {
		this.isActiveDesc = isActiveDesc;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	 
	
	
	
}
