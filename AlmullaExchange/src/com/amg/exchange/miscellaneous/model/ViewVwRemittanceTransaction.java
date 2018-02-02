package com.amg.exchange.miscellaneous.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created By Nazish Ehsan Hashmi
 * Created Date - 11-09-2015
 */
@Entity
@Table(name= "VW_REMITTANCE_TRANSACTION")
public class ViewVwRemittanceTransaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal idNo;
	private BigDecimal applicationCountryId;
	private BigDecimal documentFinYear;
	private Date documentDate;
	private BigDecimal applicationFinYear;
	private BigDecimal foreignTrxAmount;
	private BigDecimal localTrxCurrencyId;
	private BigDecimal localTrxAmount;
	private BigDecimal exchangeRateApplied;
	private BigDecimal localCommisionCurrencyId;
	private BigDecimal localCommisionAmount;
	private BigDecimal localChargeCurrnecyId;
	private BigDecimal localChargeAmount;
	private BigDecimal localDeliveryCurrnecyId;
	private BigDecimal localDeliveryAmount;
	private BigDecimal localNetCurrnecyId;
	private BigDecimal localNetTrxAmount;
	private String generalLedgerEntry;
	private String generalLedgerErr;
	private String bankReference;
	private String highValueTrx;
	private BigDecimal usdAmount;
	private BigDecimal documentNo;
	private BigDecimal documentId;
	private String sourceOfIncome;
	private String signatureSpecimen;
	private BigDecimal collectionDocCode;
	private BigDecimal collectionDocFinYear;
	private BigDecimal collectionDocNo;
	private String benificiaryBank;
	private String benificiaryBranch;
	private String benificiaryName;
	private String benificiaryAccountNo;
	private String benificiaryInterBank1;
	private String benificiaryInterBank2;
	private String benificiarySwift1;
	private String benificiarySwift2;
	private String benificiaryFirstName;
	private String benificiarySecondName;
	private String benificiaryThirdName;
	private String benificiaryFourthName;
	private String remittanceDesc;
	private String deliveryDesc;
	private String stateName;
	private String districtName;
	private String cityName;
	private Date collectDate;
	private BigDecimal paidAmount;
	private BigDecimal refundAmount;
	private BigDecimal netAmount;
	private Date generalledgerDate;
	private BigDecimal appFinanceYear;
	private BigDecimal appNo;
	private String firstName;
	private String lastName;
	private String middleName;
	private String contactNo;
	private String identityInt;
	private Date idExpiryDate;
	private BigDecimal customerId;
	private String applicationTypeDesc;
	private BigDecimal customerReference;
	private BigDecimal companyId;
	private BigDecimal foreignCurrencyId;
	private String quoteName;
	private BigDecimal countrBranchId;
	private String countryBranchName;
	private BigDecimal documentCode;
	private BigDecimal purposeId;
	private BigDecimal sourceOfIncomeId;
	private Date accountMMYYYY;
	private BigDecimal applicationDocumentNo;
	private String transactionType;
	private BigDecimal localOtherAdjCurrencyId;
	private String transctionStatus;
	private String transctionStatusDesc;
	// new added columns
	private Clob customerSignatureClob;
	private BigDecimal remittanceTransactionId;
	private BigDecimal remittanceModeId;
	private BigDecimal purposeOfTransaction;
	private String localCurrencyCode;
	private BigDecimal idProofTypeId;
	private String instruction;
	private String highValueAuthUser;
	private Date highValueAuthDate;
	private String foreignCurrencyCode;
	private BigDecimal deliveryModeId;
	private String debitAccountNo;
	private String createdBy;
	private String companyName;
	private String blackListIndicator;
	private BigDecimal branchPhoneNumber;
	private BigDecimal beneficiaryBankId;
	private BigDecimal beneficiaryBankBranchId;
	private BigDecimal bankId;
	private BigDecimal bankBranchId;



	@Id
	@Column(name = "IDNO")
	public BigDecimal getIdNo() {
		return idNo;
	}
	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinYear() {
		return documentFinYear;
	}
	public void setDocumentFinYear(BigDecimal documentFinYear) {
		this.documentFinYear = documentFinYear;
	}

	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name = "APPLICATION_FINANCE_YEAR")
	public BigDecimal getApplicationFinYear() {
		return applicationFinYear;
	}
	public void setApplicationFinYear(BigDecimal applicationFinYear) {
		this.applicationFinYear = applicationFinYear;
	}

	@Column(name = "FOREIGN_TRANX_AMOUNT")
	public BigDecimal getForeignTrxAmount() {
		return foreignTrxAmount;
	}
	public void setForeignTrxAmount(BigDecimal foreignTrxAmount) {
		this.foreignTrxAmount = foreignTrxAmount;
	}

	@Column(name = "LOCAL_TRANX_CURRENCY_ID")
	public BigDecimal getLocalTrxCurrencyId() {
		return localTrxCurrencyId;
	}
	public void setLocalTrxCurrencyId(BigDecimal localTrxCurrencyId) {
		this.localTrxCurrencyId = localTrxCurrencyId;
	}

	@Column(name = "LOCAL_TRANX_AMOUNT")
	public BigDecimal getLocalTrxAmount() {
		return localTrxAmount;
	}
	public void setLocalTrxAmount(BigDecimal localTrxAmount) {
		this.localTrxAmount = localTrxAmount;
	}

	@Column(name = "EXCHANGE_RATE_APPLIED")
	public BigDecimal getExchangeRateApplied() {
		return exchangeRateApplied;
	}
	public void setExchangeRateApplied(BigDecimal exchangeRateApplied) {
		this.exchangeRateApplied = exchangeRateApplied;
	}

	@Column(name = "LOCAL_COMMISION_CURRENCY_ID")
	public BigDecimal getLocalCommisionCurrencyId() {
		return localCommisionCurrencyId;
	}
	public void setLocalCommisionCurrencyId(BigDecimal localCommisionCurrencyId) {
		this.localCommisionCurrencyId = localCommisionCurrencyId;
	}

	@Column(name = "LOCAL_COMMISION_AMOUNT")
	public BigDecimal getLocalCommisionAmount() {
		return localCommisionAmount;
	}
	public void setLocalCommisionAmount(BigDecimal localCommisionAmount) {
		this.localCommisionAmount = localCommisionAmount;
	}

	@Column(name = "LOCAL_CHARGE_CURRENCY_ID")
	public BigDecimal getLocalChargeCurrnecyId() {
		return localChargeCurrnecyId;
	}
	public void setLocalChargeCurrnecyId(BigDecimal localChargeCurrnecyId) {
		this.localChargeCurrnecyId = localChargeCurrnecyId;
	}

	@Column(name = "LOCAL_CHARGE_AMOUNT")
	public BigDecimal getLocalChargeAmount() {
		return localChargeAmount;
	}
	public void setLocalChargeAmount(BigDecimal localChargeAmount) {
		this.localChargeAmount = localChargeAmount;
	}

	@Column(name = "LOCAL_DELIVERY_CURRENCY_ID")
	public BigDecimal getLocalDeliveryCurrnecyId() {
		return localDeliveryCurrnecyId;
	}
	public void setLocalDeliveryCurrnecyId(BigDecimal localDeliveryCurrnecyId) {
		this.localDeliveryCurrnecyId = localDeliveryCurrnecyId;
	}

	@Column(name = "LOCAL_DELIVERY_AMOUNT")
	public BigDecimal getLocalDeliveryAmount() {
		return localDeliveryAmount;
	}
	public void setLocalDeliveryAmount(BigDecimal localDeliveryAmount) {
		this.localDeliveryAmount = localDeliveryAmount;
	}

	@Column(name = "LOCAL_NET_CURRENCY_ID")
	public BigDecimal getLocalNetCurrnecyId() {
		return localNetCurrnecyId;
	}
	public void setLocalNetCurrnecyId(BigDecimal localNetCurrnecyId) {
		this.localNetCurrnecyId = localNetCurrnecyId;
	}

	@Column(name = "LOCAL_NET_TRANX_AMOUNT")
	public BigDecimal getLocalNetTrxAmount() {
		return localNetTrxAmount;
	}
	public void setLocalNetTrxAmount(BigDecimal localNetTrxAmount) {
		this.localNetTrxAmount = localNetTrxAmount;
	}

	@Column(name = "GENERAL_LEDGER_ENTRY")
	public String getGeneralLedgerEntry() {
		return generalLedgerEntry;
	}
	public void setGeneralLedgerEntry(String generalLedgerEntry) {
		this.generalLedgerEntry = generalLedgerEntry;
	}

	@Column(name = "GENERAL_LEDGER_ERR")
	public String getGeneralLedgerErr() {
		return generalLedgerErr;
	}
	public void setGeneralLedgerErr(String generalLedgerErr) {
		this.generalLedgerErr = generalLedgerErr;
	}

	@Column(name = "BANK_REFERENCE")
	public String getBankReference() {
		return bankReference;
	}
	public void setBankReference(String bankReference) {
		this.bankReference = bankReference;
	}

	@Column(name = "HIGH_VALUE_TRANX")
	public String getHighValueTrx() {
		return highValueTrx;
	}
	public void setHighValueTrx(String highValueTrx) {
		this.highValueTrx = highValueTrx;
	}

	@Column(name = "USD_AMOUNT")
	public BigDecimal getUsdAmount() {
		return usdAmount;
	}
	public void setUsdAmount(BigDecimal usdAmount) {
		this.usdAmount = usdAmount;
	}

	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	@Column(name = "SOURCE_OF_INCOME_DESC")
	public String getSourceOfIncome() {
		return sourceOfIncome;
	}
	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	@Column(name = "SIGNATURE_SPECIMEN")
	public String getSignatureSpecimen() {
		return signatureSpecimen;
	}
	public void setSignatureSpecimen(String signatureSpecimen) {
		this.signatureSpecimen = signatureSpecimen;
	}

	@Column(name = "COLLECTION_DOC_FINANCE_YEAR")
	public BigDecimal getCollectionDocFinYear() {
		return collectionDocFinYear;
	}
	public void setCollectionDocFinYear(BigDecimal collectionDocFinYear) {
		this.collectionDocFinYear = collectionDocFinYear;
	}

	@Column(name = "COLLECTION_DOCUMENT_NO")
	public BigDecimal getCollectionDocNo() {
		return collectionDocNo;
	}
	public void setCollectionDocNo(BigDecimal collectionDocNo) {
		this.collectionDocNo = collectionDocNo;
	}

	@Column(name = "BENEFICIARY_BANK")
	public String getBenificiaryBank() {
		return benificiaryBank;
	}
	public void setBenificiaryBank(String benificiaryBank) {
		this.benificiaryBank = benificiaryBank;
	}

	@Column(name = "BENEFICIARY_BRANCH")
	public String getBenificiaryBranch() {
		return benificiaryBranch;
	}
	public void setBenificiaryBranch(String benificiaryBranch) {
		this.benificiaryBranch = benificiaryBranch;
	}

	@Column(name = "BENEFICIARY_NAME")
	public String getBenificiaryName() {
		return benificiaryName;
	}
	public void setBenificiaryName(String benificiaryName) {
		this.benificiaryName = benificiaryName;
	}

	@Column(name = "BENEFICIARY_ACCOUNT_NO")
	public String getBenificiaryAccountNo() {
		return benificiaryAccountNo;
	}
	public void setBenificiaryAccountNo(String benificiaryAccountNo) {
		this.benificiaryAccountNo = benificiaryAccountNo;
	}

	@Column(name = "BENEFICIARY_INTER_BANK1")
	public String getBenificiaryInterBank1() {
		return benificiaryInterBank1;
	}
	public void setBenificiaryInterBank1(String benificiaryInterBank1) {
		this.benificiaryInterBank1 = benificiaryInterBank1;
	}

	@Column(name = "BENEFICIARY_INTER_BANK2")
	public String getBenificiaryInterBank2() {
		return benificiaryInterBank2;
	}
	public void setBenificiaryInterBank2(String benificiaryInterBank2) {
		this.benificiaryInterBank2 = benificiaryInterBank2;
	}

	@Column(name = "BENEFICIARY_SWIFT_BANK1")
	public String getBenificiarySwift1() {
		return benificiarySwift1;
	}
	public void setBenificiarySwift1(String benificiarySwift1) {
		this.benificiarySwift1 = benificiarySwift1;
	}

	@Column(name = "BENEFICIARY_SWIFT_BANK2")
	public String getBenificiarySwift2() {
		return benificiarySwift2;
	}
	public void setBenificiarySwift2(String benificiarySwift2) {
		this.benificiarySwift2 = benificiarySwift2;
	}

	@Column(name = "BENEFICIARY_FIRST_NAME")
	public String getBenificiaryFirstName() {
		return benificiaryFirstName;
	}
	public void setBenificiaryFirstName(String benificiaryFirstName) {
		this.benificiaryFirstName = benificiaryFirstName;
	}

	@Column(name = "BENEFICIARY_SECOND_NAME")
	public String getBenificiarySecondName() {
		return benificiarySecondName;
	}
	public void setBenificiarySecondName(String benificiarySecondName) {
		this.benificiarySecondName = benificiarySecondName;
	}

	@Column(name = "BENEFICIARY_THIRD_NAME")
	public String getBenificiaryThirdName() {
		return benificiaryThirdName;
	}
	public void setBenificiaryThirdName(String benificiaryThirdName) {
		this.benificiaryThirdName = benificiaryThirdName;
	}

	@Column(name = "BENEFICIARY_FOURTH_NAME")
	public String getBenificiaryFourthName() {
		return benificiaryFourthName;
	}
	public void setBenificiaryFourthName(String benificiaryFourthName) {
		this.benificiaryFourthName = benificiaryFourthName;
	}

	@Column(name = "REMITTANCE_DESCRIPTION")
	public String getRemittanceDesc() {
		return remittanceDesc;
	}
	public void setRemittanceDesc(String remittanceDesc) {
		this.remittanceDesc = remittanceDesc;
	}

	@Column(name = "DELIVERY_DESCRIPTION")
	public String getDeliveryDesc() {
		return deliveryDesc;
	}
	public void setDeliveryDesc(String deliveryDesc) {
		this.deliveryDesc = deliveryDesc;
	}

	@Column(name = "STATE_NAME")
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Column(name = "DISTRICT_NAME")
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	@Column(name = "CITY_NAME")
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "COLLECT_DATE")
	public Date getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	@Column(name = "PAID_AMOUNT")
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	@Column(name = "REFUNDED_AMOUNT")
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	@Column(name = "NET_AMOUNT")
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	@Column(name = "GENERAL_LEDGER_DATE")
	public Date getGeneralledgerDate() {
		return generalledgerDate;
	}
	public void setGeneralledgerDate(Date generalledgerDate) {
		this.generalledgerDate = generalledgerDate;
	}

	@Column(name = "APP_FINANCE_YEAR")
	public BigDecimal getAppFinanceYear() {
		return appFinanceYear;
	}
	public void setAppFinanceYear(BigDecimal appFinanceYear) {
		this.appFinanceYear = appFinanceYear;
	}

	@Column(name = "APP_NO")
	public BigDecimal getAppNo() {
		return appNo;
	}
	public void setAppNo(BigDecimal appNo) {
		this.appNo = appNo;
	}

	/*@Column(name = "DEAL_FIN_YEAR")
	public BigDecimal getDealFinYear() {
		return dealFinYear;
	}
	public void setDealFinYear(BigDecimal dealFinYear) {
		this.dealFinYear = dealFinYear;
	}
	@Column(name = "DEAL_ID")
	public BigDecimal getDealId() {
		return dealId;
	}
	public void setDealId(BigDecimal dealId) {
		this.dealId = dealId;
	}
	@Column(name = "COLLECTION_MODE")
	public String getCollectionMode() {
		return collectionMode;
	}
	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}*/

	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LAST_NAME")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "MIDDLE_NAME")
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Column(name = "CONTACT_NUMBER")
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	@Column(name = "IDENTITY_INT")
	public String getIdentityInt() {
		return identityInt;
	}
	public void setIdentityInt(String identityInt) {
		this.identityInt = identityInt;
	}

	@Column(name = "IDENTITY_EXPIRY_DATE")
	public Date getIdExpiryDate() {
		return idExpiryDate;
	}
	public void setIdExpiryDate(Date idExpiryDate) {
		this.idExpiryDate = idExpiryDate;
	}

	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	@Column(name = "APPLICATION_TYPE_DESC")
	public String getApplicationTypeDesc() {
		return applicationTypeDesc;
	}
	public void setApplicationTypeDesc(String applicationTypeDesc) {
		this.applicationTypeDesc = applicationTypeDesc;
	}

	@Column(name = "CUSTOMER_REFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	@Column(name = "FOREIGN_CURRENCY_ID")
	public BigDecimal getForeignCurrencyId() {
		return foreignCurrencyId;
	}
	public void setForeignCurrencyId(BigDecimal foreignCurrencyId) {
		this.foreignCurrencyId = foreignCurrencyId;
	}

	@Column(name = "QUOTE_NAME")
	public String getQuoteName() {
		return quoteName;
	}
	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}

	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountrBranchId() {
		return countrBranchId;
	}
	public void setCountrBranchId(BigDecimal countrBranchId) {
		this.countrBranchId = countrBranchId;
	}

	@Column(name = "COUNTRY_BRANCH_NAME")
	public String getCountryBranchName() {
		return countryBranchName;
	}
	public void setCountryBranchName(String countryBranchName) {
		this.countryBranchName = countryBranchName;
	}

	@Column(name = "ACCOUNT_MMYYYY")
	public Date getAccountMMYYYY() {
		return accountMMYYYY;
	}
	public void setAccountMMYYYY(Date accountMMYYYY) {
		this.accountMMYYYY = accountMMYYYY;
	}

	@Column(name = "APPLICATION_DOCUMENT_NO")
	public BigDecimal getApplicationDocumentNo() {
		return applicationDocumentNo;
	}
	public void setApplicationDocumentNo(BigDecimal applicationDocumentNo) {
		this.applicationDocumentNo = applicationDocumentNo;
	}

	@Column(name = "TRANSACTION_TYPE")
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Column(name = "LOCAL_OTH_ADJ_CURRENCY_ID")
	public BigDecimal getLocalOtherAdjCurrencyId() {
		return localOtherAdjCurrencyId;
	}
	public void setLocalOtherAdjCurrencyId(BigDecimal localOtherAdjCurrencyId) {
		this.localOtherAdjCurrencyId = localOtherAdjCurrencyId;
	}

	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "PURPOSE_ID")
	public BigDecimal getPurposeId() {
		return purposeId;
	}
	public void setPurposeId(BigDecimal purposeId) {
		this.purposeId = purposeId;
	}

	@Column(name = "SOURCE_OF_INCOME")
	public BigDecimal getSourceOfIncomeId() {
		return sourceOfIncomeId;
	}
	public void setSourceOfIncomeId(BigDecimal sourceOfIncomeId) {
		this.sourceOfIncomeId = sourceOfIncomeId;
	}

	@Column(name = "TRANSACTION_STATUS")
	public String getTransctionStatus() {
		return transctionStatus;
	}
	public void setTransctionStatus(String transctionStatus) {
		this.transctionStatus = transctionStatus;
	}

	@Column(name = "TRANSACTION_STATUS_DESC")
	public String getTransctionStatusDesc() {
		return transctionStatusDesc;
	}
	public void setTransctionStatusDesc(String transctionStatusDesc) {
		this.transctionStatusDesc = transctionStatusDesc;
	}

	@Column(name = "COLLECTION_DOC_CODE")
	public BigDecimal getCollectionDocCode() {
		return collectionDocCode;
	}
	public void setCollectionDocCode(BigDecimal collectionDocCode) {
		this.collectionDocCode = collectionDocCode;
	}
	
	@Column(name = "SIGNATURE_SPECIMEN_CLOB")
	public Clob getCustomerSignatureClob() {
		return customerSignatureClob;
	}
	public void setCustomerSignatureClob(Clob customerSignatureClob) {
		this.customerSignatureClob = customerSignatureClob;
	}
	
	@Column(name = "REMITTANCE_TRANSACTION_ID")
	public BigDecimal getRemittanceTransactionId() {
		return remittanceTransactionId;
	}
	public void setRemittanceTransactionId(BigDecimal remittanceTransactionId) {
		this.remittanceTransactionId = remittanceTransactionId;
	}
	
	@Column(name = "REMITTANCE_MODE_ID")
	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}
	
	@Column(name = "PURPOSE_OF_TRANSACTION")
	public BigDecimal getPurposeOfTransaction() {
		return purposeOfTransaction;
	}
	public void setPurposeOfTransaction(BigDecimal purposeOfTransaction) {
		this.purposeOfTransaction = purposeOfTransaction;
	}
	
	@Column(name = "LOCAL_CURRENCY_CODE")
	public String getLocalCurrencyCode() {
		return localCurrencyCode;
	}
	public void setLocalCurrencyCode(String localCurrencyCode) {
		this.localCurrencyCode = localCurrencyCode;
	}
	
	@Column(name = "IDPROOF_TYPE_ID")
	public BigDecimal getIdProofTypeId() {
		return idProofTypeId;
	}
	public void setIdProofTypeId(BigDecimal idProofTypeId) {
		this.idProofTypeId = idProofTypeId;
	}
	
	@Column(name = "INSTRUCTION")
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	@Column(name = "HIGH_VALUE_AUTHUSER")
	public String getHighValueAuthUser() {
		return highValueAuthUser;
	}
	public void setHighValueAuthUser(String highValueAuthUser) {
		this.highValueAuthUser = highValueAuthUser;
	}
	
	@Column(name = "HIGH_VALUE_AUTHDAT")
	public Date getHighValueAuthDate() {
		return highValueAuthDate;
	}
	public void setHighValueAuthDate(Date highValueAuthDate) {
		this.highValueAuthDate = highValueAuthDate;
	}

	@Column(name = "FOREIGN_CURRENCY_CODE")
	public String getForeignCurrencyCode() {
		return foreignCurrencyCode;
	}
	public void setForeignCurrencyCode(String foreignCurrencyCode) {
		this.foreignCurrencyCode = foreignCurrencyCode;
	}
	
	@Column(name = "DELIVERY_MODE_ID")
	public BigDecimal getDeliveryModeId() {
		return deliveryModeId;
	}
	public void setDeliveryModeId(BigDecimal deliveryModeId) {
		this.deliveryModeId = deliveryModeId;
	}
	
	@Column(name = "DEBIT_ACCOUNT_NO")
	public String getDebitAccountNo() {
		return debitAccountNo;
	}
	public void setDebitAccountNo(String debitAccountNo) {
		this.debitAccountNo = debitAccountNo;
	}
	
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "COMPANY_NAME")
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Column(name = "BLACK_LIST_INDICATOR")
	public String getBlackListIndicator() {
		return blackListIndicator;
	}
	public void setBlackListIndicator(String blackListIndicator) {
		this.blackListIndicator = blackListIndicator;
	}
	
	@Column(name = "BRANCH_PHONE_NUMBER")
	public BigDecimal getBranchPhoneNumber() {
		return branchPhoneNumber;
	}
	public void setBranchPhoneNumber(BigDecimal branchPhoneNumber) {
		this.branchPhoneNumber = branchPhoneNumber;
	}
	
	@Column(name = "BENEFICIARY_BANK_ID")
	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}
	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}
	
	@Column(name = "BENEFICIARY_BANK_BRANCH_ID")
	public BigDecimal getBeneficiaryBankBranchId() {
		return beneficiaryBankBranchId;
	}
	public void setBeneficiaryBankBranchId(BigDecimal beneficiaryBankBranchId) {
		this.beneficiaryBankBranchId = beneficiaryBankBranchId;
	}
	
	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	@Column(name = "BANK_BRANCH_ID")
	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}
	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	
	
	




}
