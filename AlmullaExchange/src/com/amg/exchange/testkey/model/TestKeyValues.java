package com.amg.exchange.testkey.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;

@Entity
@Table(name = "EX_TESTKEY_VALUES")
public class TestKeyValues implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal testKeyValuesId;
	private CountryMaster fsApplicationCountryId;
	private BankMaster exbankMasterId;
	private String bankCode;
	private String bankAccountNumber;
	private BankBranch exBankBranchId;
	private BigDecimal bankBranchCode;
	private String sendReceiveIndicator;
	private BigDecimal calculationOrderNo;
	private String primaryTestKeyCode;
	private BigDecimal primaryTestKeyValue;
	private String secondaryTestKeyCode;
	private BigDecimal secondaryTestKeyValue;
	private BigDecimal keyValue;
	private String bankAccountDesc;
	private String currencyName;
	private String status;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	
	
	public TestKeyValues() {
		super();
	}

	public TestKeyValues(BigDecimal testKeyValuesId,
			CountryMaster fsApplicationCountryId, BankMaster exbankMasterId,
			String bankCode, String bankAccountNumber,
			BankBranch exBankBranchId, BigDecimal bankBranchCode,
			String sendReceiveIndicator, BigDecimal calculationOrderNo,
			String primaryTestKeyCode, BigDecimal primaryTestKeyValue,
			String secondaryTestKeyCode, BigDecimal secondaryTestKeyValue,
			BigDecimal keyValue, String bankAccountDesc, String currencyName,
			String status, String isActive, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate, String approvedBy,
			Date approvedDate, String remarks) {
		super();
		this.testKeyValuesId = testKeyValuesId;
		this.fsApplicationCountryId = fsApplicationCountryId;
		this.exbankMasterId = exbankMasterId;
		this.bankCode = bankCode;
		this.bankAccountNumber = bankAccountNumber;
		this.exBankBranchId = exBankBranchId;
		this.bankBranchCode = bankBranchCode;
		this.sendReceiveIndicator = sendReceiveIndicator;
		this.calculationOrderNo = calculationOrderNo;
		this.primaryTestKeyCode = primaryTestKeyCode;
		this.primaryTestKeyValue = primaryTestKeyValue;
		this.secondaryTestKeyCode = secondaryTestKeyCode;
		this.secondaryTestKeyValue = secondaryTestKeyValue;
		this.keyValue = keyValue;
		this.bankAccountDesc = bankAccountDesc;
		this.currencyName = currencyName;
		this.status = status;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
	}





	@Id
	@GeneratedValue(generator = "ex_testkey_values_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_testkey_values_seq", sequenceName = "EX_TESTKEY_VALUES_SEQ", allocationSize = 1)
	@Column(name = "TESTKEY_VALUES_ID", nullable = false)
	public BigDecimal getTestKeyValuesId() {
		return testKeyValuesId;
	}

	public void setTestKeyValuesId(BigDecimal testKeyValuesId) {
		this.testKeyValuesId = testKeyValuesId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getFsApplicationCountryId() {
		return fsApplicationCountryId;
	}

	public void setFsApplicationCountryId(CountryMaster fsApplicationCountryId) {
		this.fsApplicationCountryId = fsApplicationCountryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getExbankMasterId() {
		return exbankMasterId;
	}

	public void setExbankMasterId(BankMaster exbankMasterId) {
		this.exbankMasterId = exbankMasterId;
	}

	@Column(name = "BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "BANK_ACCOUNT_NUMBER")
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID")
	public BankBranch getExBankBranchId() {
		return exBankBranchId;
	}

	public void setExBankBranchId(BankBranch exBankBranchId) {
		this.exBankBranchId = exBankBranchId;
	}
	
	@Column(name = "BRANCH_CODE")
	public BigDecimal getBankBranchCode() {
		return bankBranchCode;
	}

	public void setBankBranchCode(BigDecimal bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}
	
	@Column(name = "SEND_RECEIVE_INDICATOR")
	public String getSendReceiveIndicator() {
		return sendReceiveIndicator;
	}

	public void setSendReceiveIndicator(String sendReceiveIndicator) {
		this.sendReceiveIndicator = sendReceiveIndicator;
	}

	@Column(name = "CALCULATION_ORDER_NO")
	public BigDecimal getCalculationOrderNo() {
		return calculationOrderNo;
	}

	public void setCalculationOrderNo(BigDecimal calculationOrderNo) {
		this.calculationOrderNo = calculationOrderNo;
	}

	@Column(name = "PRIMARY_TEST_KEY_CODE")
	public String getPrimaryTestKeyCode() {
		return primaryTestKeyCode;
	}

	public void setPrimaryTestKeyCode(String primaryTestKeyCode) {
		this.primaryTestKeyCode = primaryTestKeyCode;
	}

	@Column(name = "PRIMARY_TEST_KEY_VALUE")
	public BigDecimal getPrimaryTestKeyValue() {
		return primaryTestKeyValue;
	}

	public void setPrimaryTestKeyValue(BigDecimal primaryTestKeyValue) {
		this.primaryTestKeyValue = primaryTestKeyValue;
	}

	@Column(name = "SECONDARY_TEST_KEY_CODE")
	public String getSecondaryTestKeyCode() {
		return secondaryTestKeyCode;
	}

	public void setSecondaryTestKeyCode(String secondaryTestKeyCode) {
		this.secondaryTestKeyCode = secondaryTestKeyCode;
	}

	@Column(name = "SECONDARY_TEST_KEY_VALUE")
	public BigDecimal getSecondaryTestKeyValue() {
		return secondaryTestKeyValue;
	}

	public void setSecondaryTestKeyValue(BigDecimal secondaryTestKeyValue) {
		this.secondaryTestKeyValue = secondaryTestKeyValue;
	}

	@Column(name = "KEY_VALUE")
	public BigDecimal getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(BigDecimal keyValue) {
		this.keyValue = keyValue;
	}

	@Column(name = "BANK_ACCOUNT_DESC")
	public String getBankAccountDesc() {
		return bankAccountDesc;
	}

	public void setBankAccountDesc(String bankAccountDesc) {
		this.bankAccountDesc = bankAccountDesc;
	}

	@Column(name = "CURRENCY")
	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
