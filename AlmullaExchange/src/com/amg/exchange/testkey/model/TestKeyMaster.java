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
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
@Entity
@Table(name = "EX_TESTKEY_MASTER")
public class TestKeyMaster implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  
	  private BigDecimal testKeyMasterId;
	  private BankMaster exBankMaster;
	  private BankBranch exBankBranch;
	  private CountryMaster fsCountryMaster;
	  private CurrencyMaster exCurrencyMaster;
	  private String currencyCode;
	  private BigDecimal branchCode;
	  private String bankAccountNumber;
	  private String bankCode;
	  private String sendReceieveIndicator;
	  private BigDecimal calculationOrederNo;
	  private String calculationType;
	  private String primaryTestKeyCode;
	  private String secondaryTestKeyCode;
	  private String serialIndicator;
	  private String srRule;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String isActive;
	  private String remarks;
	  private String accountDesc;
	 
	  private String primaryTestKeyName;
	  private String secondaryTestKeyName;
	  public TestKeyMaster() {
		    super();
	  }

	  public TestKeyMaster(BigDecimal testKeyMasterId, BankMaster exBankMaster, BankBranch exBankBranch, CountryMaster fsCountryMaster, BigDecimal branchCode, String bankAccountNumber, String bankCode, String sendReceieveIndicator,
			      BigDecimal calculationOrederNo, String calculationType, String primaryTestKeyCode, String secondaryTestKeyCode, String serialIndicator, String srRule, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String approvedBy, Date approvedDate,
			      String isActive,String remarks,CurrencyMaster exCurrencyMaster,String currencyCode,String accountDesc,String primaryTestKeyName,String secondaryTestKeyName) {
		    super();
		    this.testKeyMasterId = testKeyMasterId;
		    this.exBankMaster = exBankMaster;
		    this.exBankBranch = exBankBranch;
		    this.fsCountryMaster = fsCountryMaster;
		    this.branchCode = branchCode;
		    this.bankAccountNumber = bankAccountNumber;
		    this.bankCode = bankCode;
		    this.sendReceieveIndicator = sendReceieveIndicator;
		    this.calculationOrederNo = calculationOrederNo;
		    this.calculationType = calculationType;
		    this.primaryTestKeyCode = primaryTestKeyCode;
		    this.secondaryTestKeyCode = secondaryTestKeyCode;
		    this.serialIndicator = serialIndicator;
		    this.currencyCode=currencyCode;
		    this.exCurrencyMaster=exCurrencyMaster;
		    this.srRule = srRule;
		    this.createdBy = createdBy;
		    this.createdDate = createdDate;
		    this.modifiedBy = modifiedBy;
		    this.modifiedDate = modifiedDate;
		    this.approvedBy = approvedBy;
		    this.approvedDate = approvedDate;
		    this.isActive = isActive;
		    this.remarks=remarks;
		    this.accountDesc=accountDesc;
		    this.primaryTestKeyName=primaryTestKeyName;
		    this.secondaryTestKeyName=secondaryTestKeyName;
	  }
	  
	  @Id
	  @GeneratedValue(generator = "ex_testkey_master_seq", strategy = GenerationType.SEQUENCE)
	  @SequenceGenerator(name = "ex_testkey_master_seq", sequenceName = "EX_TESTKEY_MASTER_SEQ", allocationSize = 1)
	  @Column(name = "TESTKEY_MASTER_ID", nullable = false)
	  public BigDecimal getTestKeyMasterId() {
	  	  return testKeyMasterId;
	  }

	  public void setTestKeyMasterId(BigDecimal testKeyMasterId) {
	  	  this.testKeyMasterId = testKeyMasterId;
	  }
	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "BANK_ID")
	  public BankMaster getExBankMaster() {
	  	  return exBankMaster;
	  }

	  public void setExBankMaster(BankMaster exBankMaster) {
	  	  this.exBankMaster = exBankMaster;
	  }
	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "BRANCH_ID")
	  public BankBranch getExBankBranch() {
		  	  return exBankBranch;
		  }

	 public void setExBankBranch(BankBranch exBankBranch) {
		  	  this.exBankBranch = exBankBranch;
		  }

	  
	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "APPLICATION_COUNTRY_ID")
	  public CountryMaster getFsCountryMaster() {
	  	  return fsCountryMaster;
	  }

	  public void setFsCountryMaster(CountryMaster fsCountryMaster) {
	  	  this.fsCountryMaster = fsCountryMaster;
	  }
	  @Column(name = "BRANCH_CODE")
	  public BigDecimal getBranchCode() {
	  	  return branchCode;
	  }

	  public void setBranchCode(BigDecimal branchCode) {
	  	  this.branchCode = branchCode;
	  }

	  
	  @Column(name = "BANK_ACCOUNT_NUMBER")
	  public String getBankAccountNumber() {
	  	  return bankAccountNumber;
	  }

	  public void setBankAccountNumber(String bankAccountNumber) {
	  	  this.bankAccountNumber = bankAccountNumber;
	  }
	  @Column(name = "BANK_CODE")
	  public String getBankCode() {
	  	  return bankCode;
	  }

	  public void setBankCode(String bankCode) {
	  	  this.bankCode = bankCode;
	  }
	  @Column(name = "SEND_RECEIVE_INDICATOR")
	  public String getSendReceieveIndicator() {
	  	  return sendReceieveIndicator;
	  }

	  public void setSendReceieveIndicator(String sendReceieveIndicator) {
	  	  this.sendReceieveIndicator = sendReceieveIndicator;
	  }
	  @Column(name = "CALCULATION_ORDER_NO")
	  public BigDecimal getCalculationOrederNo() {
	  	  return calculationOrederNo;
	  }

	  public void setCalculationOrederNo(BigDecimal calculationOrederNo) {
	  	  this.calculationOrederNo = calculationOrederNo;
	  }
	  @Column(name = "CALCULATION_TYPE")
	  public String getCalculationType() {
	  	  return calculationType;
	  }

	  public void setCalculationType(String calculationType) {
	  	  this.calculationType = calculationType;
	  }
	  @Column(name = "PRIMARY_TEST_KEY_CODE")
	  public String getPrimaryTestKeyCode() {
	  	  return primaryTestKeyCode;
	  }

	  public void setPrimaryTestKeyCode(String primaryTestKeyCode) {
	  	  this.primaryTestKeyCode = primaryTestKeyCode;
	  }
	  @Column(name = "SECONDARY_TEST_KEY_CODE")
	  public String getSecondaryTestKeyCode() {
	  	  return secondaryTestKeyCode;
	  }

	  public void setSecondaryTestKeyCode(String secondaryTestKeyCode) {
	  	  this.secondaryTestKeyCode = secondaryTestKeyCode;
	  }
	  @Column(name = "SERIAL_INDICATOR")
	  public String getSerialIndicator() {
	  	  return serialIndicator;
	  }

	  public void setSerialIndicator(String serialIndicator) {
	  	  this.serialIndicator = serialIndicator;
	  }
	  @Column(name = "SR_RULE")
	  public String getSrRule() {
	  	  return srRule;
	  }

	  public void setSrRule(String srRule) {
	  	  this.srRule = srRule;
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
	  @Column(name = "IS_ACTIVE")
	  public String getIsActive() {
	  	  return isActive;
	  }

	  public void setIsActive(String isActive) {
	  	  this.isActive = isActive;
	  }
	  @Column(name = "REMARKS")
	  public String getRemarks() {
	  	  return remarks;
	  }

	  public void setRemarks(String remarks) {
	  	  this.remarks = remarks;
	  }
	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "CURRENCY_ID")
	  public CurrencyMaster getExCurrencyMaster() {
	  	  return exCurrencyMaster;
	  }

	  public void setExCurrencyMaster(CurrencyMaster exCurrencyMaster) {
	  	  this.exCurrencyMaster = exCurrencyMaster;
	  }
	  @Column(name = "CURRENCY_CODE")
	  public String getCurrencyCode() {
	  	  return currencyCode;
	  }

	  public void setCurrencyCode(String currencyCode) {
	  	  this.currencyCode = currencyCode;
	  }
	  @Column(name = "BANK_ACCOUNT_DESC")
	  public String getAccountDesc() {
	  	  return accountDesc;
	  }

	  public void setAccountDesc(String accountDesc) {
	  	  this.accountDesc = accountDesc;
	  }
	  @Column(name = "PRIMARY_TEST_KEY_DESC")
	  public String getPrimaryTestKeyName() {
	  	  return primaryTestKeyName;
	  }

	  public void setPrimaryTestKeyName(String primaryTestKeyName) {
	  	  this.primaryTestKeyName = primaryTestKeyName;
	  }
	  @Column(name = "SECONDARY_TEST_KEY_DESC")
	  public String getSecondaryTestKeyName() {
	  	  return secondaryTestKeyName;
	  }

	  public void setSecondaryTestKeyName(String secondaryTestKeyName) {
	  	  this.secondaryTestKeyName = secondaryTestKeyName;
	  }
	  
	  

}
