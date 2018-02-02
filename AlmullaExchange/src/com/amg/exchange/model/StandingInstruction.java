package com.amg.exchange.model;

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

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;

@Entity
@Table(name = "EX_STANDING_INSTRUCTION")
public class StandingInstruction implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal standingInstructionSeqId;
	private Document exDocumentId;
	private UserFinancialYear exUserFinancialYearByApplicationFinanceYear;
	private CountryMaster fsApplicationCountryMasterId;
	private CountryMaster fsCountryMasterByCorespondingCountryId;
	private RateAlertFrequency frequency;
	private CurrencyMaster exCurrencyMasterByForeignCurrencyId;
	private CurrencyMaster exCurrencyMasterByLocalTranxCurrencyId;
	private BankMaster exBankMaster;
	private BankBranch exBankBranchId;
	private CountryMaster fsCountryMasterByBankCountryId;
	private CompanyMaster fsCompanyMasterId;
	private BeneficaryMaster exbeneficaryMasterSeqId;
	private BeneficaryAccount exbeneficaryAccountSeqId;
	private Customer fsCustomer;
	private CountryBranch exCountryBranchId;
//	private RemittanceModeMaster exRemittanceMode;
//	private DeliveryMode exDeliveryMode;
	
	private BigDecimal documentNo;
	private BigDecimal customerRefNo;
//	private BigDecimal bankcode;
	private String remittancemode;
	private String deliverymode;
	private BigDecimal debitAccountNo;
	private BigDecimal exUserFinancialYearByDocumentFinanceYear;
	private Date standingInstructionDate;
	private Date effectiveFromDate;
	private BigDecimal effectiveduration;
	private BigDecimal repeatNoofTimes;
	private String alertEmail;
	private String alertSms;
	private BigDecimal exUserFinancialYearByTransactionFinanceYear;
	private BigDecimal exApplicationDocumentNo;
	private BigDecimal exTransactionDocumentNo;
//	private BigDecimal foriegnTransAmount;
//	private BigDecimal localTransAmount;
	private String applicationStatus;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String isactive;
	private String tokenKey;
	private BigDecimal Amount;
	
	public StandingInstruction() {
		super();
	}
	


	@Id
	@GeneratedValue(generator="ex_standing_instruction_seq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_standing_instruction_seq",sequenceName="EX_STANDING_INSTRUCTION_SEQ",allocationSize=1)
	@Column(name = "STANDING_INSTRUCTION_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getStandingInstructionSeqId() {
		return standingInstructionSeqId;
	}

	public void setStandingInstructionSeqId(BigDecimal standingInstructionSeqId) {
		this.standingInstructionSeqId = standingInstructionSeqId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getFsApplicationCountryMasterId() {
		return fsApplicationCountryMasterId;
	}

	public void setFsApplicationCountryMasterId(
			CountryMaster fsApplicationCountryMasterId) {
		this.fsApplicationCountryMasterId = fsApplicationCountryMasterId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMasterId() {
		return fsCompanyMasterId;
	}

	public void setFsCompanyMasterId(CompanyMaster fsCompanyMasterId) {
		this.fsCompanyMasterId = fsCompanyMasterId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_ID")
	public Document getExDocumentId() {
		return exDocumentId;
	}

	public void setExDocumentId(Document exDocumentId) {
		this.exDocumentId = exDocumentId;
	}
	
	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getExCountryBranchId() {
		return exCountryBranchId;
	}

	public void setExCountryBranchId(CountryBranch exCountryBranchId) {
		this.exCountryBranchId = exCountryBranchId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getFsCustomer() {
		return fsCustomer;
	}
	public void setFsCustomer(Customer fsCustomer) {
		this.fsCustomer = fsCustomer;
	}
	
	@Column(name = "CUSTOMER_REF")
	public BigDecimal getCustomerRefNo() {
		return customerRefNo;
	}
	public void setCustomerRefNo(BigDecimal customerRefNo) {
		this.customerRefNo = customerRefNo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_COUNTRY_ID")
	public CountryMaster getFsCountryMasterByBankCountryId() {
		return fsCountryMasterByBankCountryId;
	}
	public void setFsCountryMasterByBankCountryId(CountryMaster fsCountryMasterByBankCountryId) {
		this.fsCountryMasterByBankCountryId = fsCountryMasterByBankCountryId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CORESPONDING_COUNTRY_ID")
	public CountryMaster getFsCountryMasterByCorespondingCountryId() {
		return fsCountryMasterByCorespondingCountryId;
	}
	public void setFsCountryMasterByCorespondingCountryId(
			CountryMaster fsCountryMasterByCorespondingCountryId) {
		this.fsCountryMasterByCorespondingCountryId = fsCountryMasterByCorespondingCountryId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getExBankMaster() {
		return exBankMaster;
	}
	public void setExBankMaster(BankMaster exBankMaster) {
		this.exBankMaster = exBankMaster;
	}
	
	/*@Column(name = "BANK_CODE")
	public BigDecimal getBankcode() {
		return bankcode;
	}
	public void setBankcode(BigDecimal bankcode) {
		this.bankcode = bankcode;
	}*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_BRANCH_ID")
	public BankBranch getExBankBranchId() {
		return exBankBranchId;
	}
	public void setExBankBranchId(BankBranch exBankBranchId) {
		this.exBankBranchId = exBankBranchId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FOREIGN_CURRENCY_ID")
	public CurrencyMaster getExCurrencyMasterByForeignCurrencyId() {
		return exCurrencyMasterByForeignCurrencyId;
	}
	public void setExCurrencyMasterByForeignCurrencyId(
			CurrencyMaster exCurrencyMasterByForeignCurrencyId) {
		this.exCurrencyMasterByForeignCurrencyId = exCurrencyMasterByForeignCurrencyId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOCAL_TRANX_CURRENCY_ID")
	public CurrencyMaster getExCurrencyMasterByLocalTranxCurrencyId() {
		return exCurrencyMasterByLocalTranxCurrencyId;
	}
	public void setExCurrencyMasterByLocalTranxCurrencyId(
			CurrencyMaster exCurrencyMasterByLocalTranxCurrencyId) {
		this.exCurrencyMasterByLocalTranxCurrencyId = exCurrencyMasterByLocalTranxCurrencyId;
	}
	
	
/*	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REMITTANCE_MODE_ID")
	public RemittanceModeMaster getExRemittanceMode() {
		return exRemittanceMode;
	}
	public void setExRemittanceMode(RemittanceModeMaster exRemittanceMode) {
		this.exRemittanceMode = exRemittanceMode;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DELIVERY_MODE_ID")
	public DeliveryMode getExDeliveryMode() {
		return exDeliveryMode;
	}
	public void setExDeliveryMode(DeliveryMode exDeliveryMode) {
		this.exDeliveryMode = exDeliveryMode;
	}*/
	
	
	
	@Column(name = "REMITTANCE_MODE_ID")
	public String getRemittancemode() {
		return remittancemode;
	}
	public void setRemittancemode(String remittancemode) {
		this.remittancemode = remittancemode;
	}

	@Column(name = "DELIVERY_MODE_ID")
	public String getDeliverymode() {
		return deliverymode;
	}
	public void setDeliverymode(String deliverymode) {
		this.deliverymode = deliverymode;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BENEFICARY_MASTER_SEQ_ID")
	public BeneficaryMaster getExbeneficaryMasterSeqId() {
		return exbeneficaryMasterSeqId;
	}
	public void setExbeneficaryMasterSeqId(BeneficaryMaster exbeneficaryMasterSeqId) {
		this.exbeneficaryMasterSeqId = exbeneficaryMasterSeqId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BENEFICARY_ACCOUNT_SEQ_ID")
	public BeneficaryAccount getExbeneficaryAccountSeqId() {
		return exbeneficaryAccountSeqId;
	}
	public void setExbeneficaryAccountSeqId(
			BeneficaryAccount exbeneficaryAccountSeqId) {
		this.exbeneficaryAccountSeqId = exbeneficaryAccountSeqId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICAITON_FINANCE_YEAR")
	public UserFinancialYear getExUserFinancialYearByApplicationFinanceYear() {
		return exUserFinancialYearByApplicationFinanceYear;
	}
	public void setExUserFinancialYearByApplicationFinanceYear(
			UserFinancialYear exUserFinancialYearByApplicationFinanceYear) {
		this.exUserFinancialYearByApplicationFinanceYear = exUserFinancialYearByApplicationFinanceYear;
	}
	
	@Column(name = "DEBIT_ACCOUNT_NO")
	public BigDecimal getDebitAccountNo() {
		return debitAccountNo;
	}
	public void setDebitAccountNo(BigDecimal debitAccountNo) {
		this.debitAccountNo = debitAccountNo;
	}
	
	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getExUserFinancialYearByDocumentFinanceYear() {
		return exUserFinancialYearByDocumentFinanceYear;
	}
	public void setExUserFinancialYearByDocumentFinanceYear(
			BigDecimal exUserFinancialYearByDocumentFinanceYear) {
		this.exUserFinancialYearByDocumentFinanceYear = exUserFinancialYearByDocumentFinanceYear;
	}

	@Column(name = "STANDING_INSTRUCTION_DATE")
	public Date getStandingInstructionDate() {
		return standingInstructionDate;
	}
	public void setStandingInstructionDate(Date standingInstructionDate) {
		this.standingInstructionDate = standingInstructionDate;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RATE_ALERT_FREQUENCY_ID")
	public RateAlertFrequency getFrequency() {
		return frequency;
	}

	public void setFrequency(RateAlertFrequency frequency) {
		this.frequency = frequency;
	}
	
	
	
/*	@Column(name = "FREQUENCY")
	public BigDecimal getFrequency() {
		return frequency;
	}
	public void setFrequency(BigDecimal frequency) {
		this.frequency = frequency;
	}*/
	
	@Column(name = "EFFECTIVE_FROM_DATE")
	public Date getEffectiveFromDate() {
		return effectiveFromDate;
	}
	public void setEffectiveFromDate(Date effectiveFromDate) {
		this.effectiveFromDate = effectiveFromDate;
	}
	
	@Column(name = "EFFECTIVE_DURATION")
	public BigDecimal getEffectiveduration() {
		return effectiveduration;
	}
	public void setEffectiveduration(BigDecimal effectiveduration) {
		this.effectiveduration = effectiveduration;
	}
	
	@Column(name = "REPEAT_NO_OF_TIMES")
	public BigDecimal getRepeatNoofTimes() {
		return repeatNoofTimes;
	}
	public void setRepeatNoofTimes(BigDecimal repeatNoofTimes) {
		this.repeatNoofTimes = repeatNoofTimes;
	}
	
	@Column(name = "ALERT_EMAIL")
	public String getAlertEmail() {
		return alertEmail;
	}
	public void setAlertEmail(String alertEmail) {
		this.alertEmail = alertEmail;
	}
	
	@Column(name = "ALERT_SMS")
	public String getAlertSms() {
		return alertSms;
	}
	public void setAlertSms(String alertSms) {
		this.alertSms = alertSms;
	}
	
	@Column(name = "APLLICAITON_DOCUMENT_NO")
	public BigDecimal getExApplicationDocumentNo() {
		return exApplicationDocumentNo;
	}
	public void setExApplicationDocumentNo(BigDecimal exApplicationDocumentNo) {
		this.exApplicationDocumentNo = exApplicationDocumentNo;
	}
	
	@Column(name = "TRANSACTION_FINANCE_YEAR")
	public BigDecimal getExUserFinancialYearByTransactionFinanceYear() {
		return exUserFinancialYearByTransactionFinanceYear;
	}

	public void setExUserFinancialYearByTransactionFinanceYear(
			BigDecimal exUserFinancialYearByTransactionFinanceYear) {
		this.exUserFinancialYearByTransactionFinanceYear = exUserFinancialYearByTransactionFinanceYear;
	}

	@Column(name = "TRANSACTION_DOCUMENT_NO")
	public BigDecimal getExTransactionDocumentNo() {
		return exTransactionDocumentNo;
	}

	public void setExTransactionDocumentNo(BigDecimal exTransactionDocumentNo) {
		this.exTransactionDocumentNo = exTransactionDocumentNo;
	}
	
/*	@Column(name = "FOREIGN_TRANX_AMOUNT")
	public BigDecimal getForiegnTransAmount() {
		return foriegnTransAmount;
	}


	public void setForiegnTransAmount(BigDecimal foriegnTransAmount) {
		this.foriegnTransAmount = foriegnTransAmount;
	}
	
	@Column(name = "LOCAL_TRANX_AMOUNT")
	public BigDecimal getLocalTransAmount() {
		return localTransAmount;
	}
	public void setLocalTransAmount(BigDecimal localTransAmount) {
		this.localTransAmount = localTransAmount;
	}*/
	
	@Column(name = "APPLICATION_STATUS")
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name = "ISACTIVE")
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	
	@Column(name = "TOKEN_KEY")
	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	@Column(name = "AMOUNT")
	public BigDecimal getAmount() {
		return Amount;
	}

	public void setAmount(BigDecimal amount) {
		Amount = amount;
	}

}
