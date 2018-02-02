package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_REMITTANCE_TRANSACTION")
public class RemittanceTxnView implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="IDNO")
	private BigDecimal idno;

	@Column(name="FOREIGN_CURRENCY_ID")
	private BigDecimal foreignCurrencyId;

	@Column(name="COUNTRY_BRANCH_ID")
	private BigDecimal countryBranchId;

	@Column(name="COUNTRY_BRANCH_NAME")
	private String countryBranchName;

	@Column(name="APPLICATION_COUNTRY_ID")
	private BigDecimal applicationCountryId;

	@Column(name="DOCUMENT_FINANCE_YEAR")
	private BigDecimal documentFinancialYear;

	@Column(name="DOCUMENT_DATE")
	private Date documentDate ;

	@Column(name="APPLICATION_FINANCE_YEAR")
	private BigDecimal applicationFinancialYear;

	@Column(name="COLLECTION_DOCUMENT_NO")
	private BigDecimal collectionDocumentNo;

	@Column(name="FOREIGN_TRANX_AMOUNT")
	private BigDecimal foreignTransactionAmount;

	@Column(name="LOCAL_TRANX_CURRENCY_ID")
	private BigDecimal localTransactionCurrencyId;

	@Column(name="LOCAL_TRANX_AMOUNT")
	private BigDecimal localTransactionAmount;

	@Column(name="EXCHANGE_RATE_APPLIED")
	private BigDecimal exchangeRateApplied;

	@Column(name="LOCAL_COMMISION_CURRENCY_ID")
	private BigDecimal localCommissionCurrencyId;

	@Column(name="LOCAL_COMMISION_AMOUNT")
	private BigDecimal localCommissionAmount;

	@Column(name="LOCAL_CHARGE_CURRENCY_ID")
	private BigDecimal localChargeCurrencyId;	

	@Column(name="LOCAL_CHARGE_AMOUNT")
	private BigDecimal localChargeAmount;

	@Column(name="LOCAL_DELIVERY_CURRENCY_ID")
	private BigDecimal localDeliveryCurrencyId;

	@Column(name="LOCAL_DELIVERY_AMOUNT")
	private BigDecimal localDeliveryAmount;

	@Column(name="LOCAL_NET_CURRENCY_ID")
	private BigDecimal localNetCurrencyId;

	@Column(name="LOCAL_NET_TRANX_AMOUNT")
	private BigDecimal localNetTransactionAmount;

	@Column(name="GENERAL_LEDGER_ENTRY")
	private String generalLedgerEntery;

	@Column(name="GENERAL_LEDGER_ERR")
	private String generalLedgerErr;

	@Column(name="BANK_REFERENCE")
	private String bankReference;

	@Column(name="HIGH_VALUE_TRANX")
	private String highValueTransaction;

	@Column(name="USD_AMOUNT")
	private BigDecimal usdAmount;

	@Column(name="DOCUMENT_NO")
	private BigDecimal documentNo;

	@Column(name="DOCUMENT_ID")
	private BigDecimal documentId;

	@Column(name="SIGNATURE_SPECIMEN")
	private String customerSignature;

	@Column(name="SIGNATURE_SPECIMEN_CLOB")
	private Clob customerSignatureClob;

	@Column(name="BENEFICIARY_NAME")
	private String beneficiaryName;

	@Column(name="BENEFICIARY_BANK")
	private String beneficiaryBank;

	@Column(name="BENEFICIARY_BRANCH")
	private String benefeciaryBranch;

	@Column(name="BENEFICIARY_ACCOUNT_NO")
	private String benefeciaryAccountNo;

	@Column(name="BENEFICIARY_INTER_BANK1")
	private String benefeciaryInterBank1;

	@Column(name="BENEFICIARY_INTER_BANK2")
	private String benefeciaryInterBank2;

	@Column(name="BENEFICIARY_SWIFT_BANK1")
	private String benefeciarySwiftBank1;

	@Column(name="BENEFICIARY_SWIFT_BANK2")
	private String benefeciarySwiftBank2;

	@Column(name="BENEFICIARY_FIRST_NAME")
	private String benefeciaryFirstName;

	@Column(name="BENEFICIARY_SECOND_NAME")
	private String benefeciarySecondName;

	@Column(name="BENEFICIARY_THIRD_NAME")
	private String benefeciaryThirdName;

	@Column(name="BENEFICIARY_FOURTH_NAME")
	private String benefeciaryFourthName;

	@Column(name="COLLECT_DATE")
	private Date collectDate;

	@Column(name="PAID_AMOUNT")
	private BigDecimal paidAmount;

	@Column(name="REFUNDED_AMOUNT")
	private BigDecimal refundedAmount;

	@Column(name="NET_AMOUNT")
	private BigDecimal netAmount;

	@Column(name="GENERAL_LEDGER_DATE")
	private Date generalLedgerDate;

	@Column(name="APP_FINANCE_YEAR")
	private BigDecimal appFinancialYear;

	@Column(name="APP_NO")
	private BigDecimal appNo;

	@Column(name="DELIVERY_DESCRIPTION")
	private String deliveryDescription;

	@Column(name="REMITTANCE_DESCRIPTION")
	private String remittanceDescription;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="MIDDLE_NAME")
	private String middleName;

	@Column(name="CONTACT_NUMBER")
	private String contactNumber;

	@Column(name="IDENTITY_INT")
	private String identityInt;

	@Column(name="IDENTITY_EXPIRY_DATE")
	private Date identityExpiryDate;

	@Column(name="CUSTOMER_ID")
	private BigDecimal customerId;

	@Column(name="SOURCE_OF_INCOME")
	private BigDecimal sourceOfIncome;

	@Column(name="QUOTE_NAME")
	private String currencyQuoteName;

	@Column(name="CITY_NAME")
	private String BeneCityName;

	@Column(name="STATE_NAME")
	private String BeneStateName;

	@Column(name="DISTRICT_NAME")
	private String BeneDistrictName;

	@Column(name="APPLICATION_TYPE_DESC")
	private String applicationTypeDesc;

	@Column(name="CUSTOMER_REFERENCE")
	private BigDecimal customerReference;

	@Column(name="PURPOSE_OF_TRANSACTION")
	private String purposeOfTransaction;

	@Column(name="SOURCE_OF_INCOME_DESC")
	private String sourceOfIncomeDesc;

	@Column(name="INSTRUCTION")
	private String instructions;

	@Column(name="BRANCH_PHONE_NUMBER")
	private BigDecimal phoneNumber;

	@Column(name = "ACCOUNT_MMYYYY")
	private Date accountMMYYYY;

	@Column(name = "APPLICATION_DOCUMENT_NO")
	private BigDecimal applicationDocumentNo;

	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;

	@Column(name = "LOCAL_OTH_ADJ_CURRENCY_ID")
	private BigDecimal localOtherAdjCurrencyId;

	@Column(name ="COMPANY_ID")
	private BigDecimal companyId;

	@Column(name ="TRANSACTION_STATUS")
	private String transcationStatus;

	@Column(name ="TRANSACTION_STATUS_DESC")
	private String transcationStatusDesc;

	@Column(name ="DOCUMENT_CODE")
	private BigDecimal documentCode;

	@Column(name ="PURPOSE_ID")
	private BigDecimal purposeId;

	@Column(name ="FOREIGN_CURRENCY_CODE")
	private String foreignCurrencyCode;

	@Column(name ="LOCAL_CURRENCY_CODE")
	private String localCurrencyCode;

	@Column(name ="COLLECTION_DOC_FINANCE_YEAR")
	private BigDecimal collectionDocFinanceYear;

	@Column(name ="COMPANY_NAME")
	private String companyName;

	// new added columns
	@Column(name ="REMITTANCE_TRANSACTION_ID")
	private BigDecimal remittanceTransactionId;
	
	@Column(name ="REMITTANCE_MODE_ID")
	private BigDecimal remittanceModeId;
	
	@Column(name ="IDPROOF_TYPE_ID")
	private BigDecimal idProofTypeId;
	
	@Column(name ="HIGH_VALUE_AUTHUSER")
	private String highValueAuthUser;
	
	@Column(name ="HIGH_VALUE_AUTHDAT")
	private Date highValueAuthDate;
	
	@Column(name ="DELIVERY_MODE_ID")
	private BigDecimal deliveryModeId;
	
	@Column(name ="DEBIT_ACCOUNT_NO")
	private String debitAccountNo;
	
	@Column(name ="CREATED_BY")
	private String createdBy;
	
	@Column(name ="BLACK_LIST_INDICATOR")
	private String blackListIndicator;
	
	@Column(name ="BENEFICIARY_BANK_ID")
	private BigDecimal beneficiaryBankId;
	
	@Column(name = "BENEFICIARY_BANK_BRANCH_ID")
	private BigDecimal beneficiaryBankBranchId;
	
	@Column(name = "BANK_ID")
	private BigDecimal bankId;
	
	@Column(name ="BANK_BRANCH_ID")
	private BigDecimal bankBranchId;
	
	@Column(name ="COLLECTION_DOC_CODE")
	private BigDecimal collectionDocCode;



	public BigDecimal getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(BigDecimal phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getSourceOfIncomeDesc() {
		return sourceOfIncomeDesc;
	}

	public void setSourceOfIncomeDesc(String sourceOfIncomeDesc) {
		this.sourceOfIncomeDesc = sourceOfIncomeDesc;
	}

	public String getPurposeOfTransaction() {
		return purposeOfTransaction;
	}

	public void setPurposeOfTransaction(String purposeOfTransaction) {
		this.purposeOfTransaction = purposeOfTransaction;
	}

	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	public String getApplicationTypeDesc() {
		return applicationTypeDesc;
	}

	public void setApplicationTypeDesc(String applicationTypeDesc) {
		this.applicationTypeDesc = applicationTypeDesc;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getCollectionDocumentNo() {
		return collectionDocumentNo;
	}

	public void setCollectionDocumentNo(BigDecimal collectionDocumentNo) {
		this.collectionDocumentNo = collectionDocumentNo;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public BigDecimal getDocumentFinancialYear() {
		return documentFinancialYear;
	}

	public void setDocumentFinancialYear(BigDecimal documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public BigDecimal getApplicationFinancialYear() {
		return applicationFinancialYear;
	}

	public void setApplicationFinancialYear(BigDecimal applicationFinancialYear) {
		this.applicationFinancialYear = applicationFinancialYear;
	}

	public BigDecimal getForeignTransactionAmount() {
		return foreignTransactionAmount;
	}

	public void setForeignTransactionAmount(BigDecimal foreignTransactionAmount) {
		this.foreignTransactionAmount = foreignTransactionAmount;
	}

	public BigDecimal getLocalTransactionCurrencyId() {
		return localTransactionCurrencyId;
	}

	public void setLocalTransactionCurrencyId(BigDecimal localTransactionCurrencyId) {
		this.localTransactionCurrencyId = localTransactionCurrencyId;
	}

	public BigDecimal getLocalTransactionAmount() {
		return localTransactionAmount;
	}

	public void setLocalTransactionAmount(BigDecimal localTransactionAmount) {
		this.localTransactionAmount = localTransactionAmount;
	}

	public BigDecimal getExchangeRateApplied() {
		return exchangeRateApplied;
	}

	public void setExchangeRateApplied(BigDecimal exchangeRateApplied) {
		this.exchangeRateApplied = exchangeRateApplied;
	}

	public BigDecimal getLocalCommissionCurrencyId() {
		return localCommissionCurrencyId;
	}

	public void setLocalCommissionCurrencyId(BigDecimal localCommissionCurrencyId) {
		this.localCommissionCurrencyId = localCommissionCurrencyId;
	}

	public BigDecimal getLocalCommissionAmount() {
		return localCommissionAmount;
	}

	public void setLocalCommissionAmount(BigDecimal localCommissionAmount) {
		this.localCommissionAmount = localCommissionAmount;
	}

	public BigDecimal getLocalChargeCurrencyId() {
		return localChargeCurrencyId;
	}

	public void setLocalChargeCurrencyId(BigDecimal localChargeCurrencyId) {
		this.localChargeCurrencyId = localChargeCurrencyId;
	}

	public BigDecimal getLocalChargeAmount() {
		return localChargeAmount;
	}

	public void setLocalChargeAmount(BigDecimal localChargeAmount) {
		this.localChargeAmount = localChargeAmount;
	}

	public BigDecimal getLocalDeliveryCurrencyId() {
		return localDeliveryCurrencyId;
	}

	public void setLocalDeliveryCurrencyId(BigDecimal localDeliveryCurrencyId) {
		this.localDeliveryCurrencyId = localDeliveryCurrencyId;
	}

	public BigDecimal getLocalDeliveryAmount() {
		return localDeliveryAmount;
	}

	public void setLocalDeliveryAmount(BigDecimal localDeliveryAmount) {
		this.localDeliveryAmount = localDeliveryAmount;
	}

	public BigDecimal getLocalNetCurrencyId() {
		return localNetCurrencyId;
	}

	public void setLocalNetCurrencyId(BigDecimal localNetCurrencyId) {
		this.localNetCurrencyId = localNetCurrencyId;
	}

	public BigDecimal getLocalNetTransactionAmount() {
		return localNetTransactionAmount;
	}

	public void setLocalNetTransactionAmount(BigDecimal localNetTransactionAmount) {
		this.localNetTransactionAmount = localNetTransactionAmount;
	}

	public String getGeneralLedgerEntery() {
		return generalLedgerEntery;
	}

	public void setGeneralLedgerEntery(String generalLedgerEntery) {
		this.generalLedgerEntery = generalLedgerEntery;
	}

	public String getGeneralLedgerErr() {
		return generalLedgerErr;
	}

	public void setGeneralLedgerErr(String generalLedgerErr) {
		this.generalLedgerErr = generalLedgerErr;
	}

	public String getBankReference() {
		return bankReference;
	}

	public void setBankReference(String bankReference) {
		this.bankReference = bankReference;
	}

	public String getHighValueTransaction() {
		return highValueTransaction;
	}

	public void setHighValueTransaction(String highValueTransaction) {
		this.highValueTransaction = highValueTransaction;
	}

	public BigDecimal getUsdAmount() {
		return usdAmount;
	}

	public void setUsdAmount(BigDecimal usdAmount) {
		this.usdAmount = usdAmount;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public String getCustomerSignature() {
		return customerSignature;
	}

	public void setCustomerSignature(String customerSignature) {
		this.customerSignature = customerSignature;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getBeneficiaryBank() {
		return beneficiaryBank;
	}

	public void setBeneficiaryBank(String beneficiaryBank) {
		this.beneficiaryBank = beneficiaryBank;
	}

	public String getBenefeciaryBranch() {
		return benefeciaryBranch;
	}

	public void setBenefeciaryBranch(String benefeciaryBranch) {
		this.benefeciaryBranch = benefeciaryBranch;
	}

	public String getBenefeciaryAccountNo() {
		return benefeciaryAccountNo;
	}

	public void setBenefeciaryAccountNo(String benefeciaryAccountNo) {
		this.benefeciaryAccountNo = benefeciaryAccountNo;
	}

	public String getBenefeciaryInterBank1() {
		return benefeciaryInterBank1;
	}

	public void setBenefeciaryInterBank1(String benefeciaryInterBank1) {
		this.benefeciaryInterBank1 = benefeciaryInterBank1;
	}

	public String getBenefeciaryInterBank2() {
		return benefeciaryInterBank2;
	}

	public void setBenefeciaryInterBank2(String benefeciaryInterBank2) {
		this.benefeciaryInterBank2 = benefeciaryInterBank2;
	}

	public String getBenefeciarySwiftBank1() {
		return benefeciarySwiftBank1;
	}

	public void setBenefeciarySwiftBank1(String benefeciarySwiftBank1) {
		this.benefeciarySwiftBank1 = benefeciarySwiftBank1;
	}

	public String getBenefeciarySwiftBank2() {
		return benefeciarySwiftBank2;
	}

	public void setBenefeciarySwiftBank2(String benefeciarySwiftBank2) {
		this.benefeciarySwiftBank2 = benefeciarySwiftBank2;
	}

	public String getBenefeciaryFirstName() {
		return benefeciaryFirstName;
	}

	public void setBenefeciaryFirstName(String benefeciaryFirstName) {
		this.benefeciaryFirstName = benefeciaryFirstName;
	}

	public String getBenefeciarySecondName() {
		return benefeciarySecondName;
	}

	public void setBenefeciarySecondName(String benefeciarySecondName) {
		this.benefeciarySecondName = benefeciarySecondName;
	}

	public String getBenefeciaryThirdName() {
		return benefeciaryThirdName;
	}

	public void setBenefeciaryThirdName(String benefeciaryThirdName) {
		this.benefeciaryThirdName = benefeciaryThirdName;
	}

	public String getBenefeciaryFourthName() {
		return benefeciaryFourthName;
	}

	public void setBenefeciaryFourthName(String benefeciaryFourthName) {
		this.benefeciaryFourthName = benefeciaryFourthName;
	}

	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public BigDecimal getRefundedAmount() {
		return refundedAmount;
	}

	public void setRefundedAmount(BigDecimal refundedAmount) {
		this.refundedAmount = refundedAmount;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public Date getGeneralLedgerDate() {
		return generalLedgerDate;
	}

	public void setGeneralLedgerDate(Date generalLedgerDate) {
		this.generalLedgerDate = generalLedgerDate;
	}

	public BigDecimal getAppFinancialYear() {
		return appFinancialYear;
	}

	public void setAppFinancialYear(BigDecimal appFinancialYear) {
		this.appFinancialYear = appFinancialYear;
	}

	public BigDecimal getAppNo() {
		return appNo;
	}

	public void setAppNo(BigDecimal appNo) {
		this.appNo = appNo;
	}

	public String getDeliveryDescription() {
		return deliveryDescription;
	}

	public void setDeliveryDescription(String deliveryDescription) {
		this.deliveryDescription = deliveryDescription;
	}

	public String getRemittanceDescription() {
		return remittanceDescription;
	}

	public void setRemittanceDescription(String remittanceDescription) {
		this.remittanceDescription = remittanceDescription;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getIdentityInt() {
		return identityInt;
	}

	public void setIdentityInt(String identityInt) {
		this.identityInt = identityInt;
	}

	public Date getIdentityExpiryDate() {
		return identityExpiryDate;
	}

	public void setIdentityExpiryDate(Date identityExpiryDate) {
		this.identityExpiryDate = identityExpiryDate;
	}

	public BigDecimal getSourceOfIncome() {
		return sourceOfIncome;
	}

	public void setSourceOfIncome(BigDecimal sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	public String getCurrencyQuoteName() {
		return currencyQuoteName;
	}

	public void setCurrencyQuoteName(String currencyQuoteName) {
		this.currencyQuoteName = currencyQuoteName;
	}

	public String getBeneCityName() {
		return BeneCityName;
	}

	public void setBeneCityName(String beneCityName) {
		BeneCityName = beneCityName;
	}

	public String getBeneStateName() {
		return BeneStateName;
	}

	public void setBeneStateName(String beneStateName) {
		BeneStateName = beneStateName;
	}

	public String getBeneDistrictName() {
		return BeneDistrictName;
	}

	public void setBeneDistrictName(String beneDistrictName) {
		BeneDistrictName = beneDistrictName;
	}

	public BigDecimal getIdno() {
		return idno;
	}

	public void setIdno(BigDecimal idno) {
		this.idno = idno;
	}

	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public String getCountryBranchName() {
		return countryBranchName;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public void setCountryBranchName(String countryBranchName) {
		this.countryBranchName = countryBranchName;
	}

	public BigDecimal getForeignCurrencyId() {
		return foreignCurrencyId;
	}

	public void setForeignCurrencyId(BigDecimal foreignCurrencyId) {
		this.foreignCurrencyId = foreignCurrencyId;
	}

	public Clob getCustomerSignatureClob() {
		return customerSignatureClob;
	}

	public void setCustomerSignatureClob(Clob customerSignatureClob) {
		this.customerSignatureClob = customerSignatureClob;
	}
	public Date getAccountMMYYYY() {
		return accountMMYYYY;
	}

	public void setAccountMMYYYY(Date accountMMYYYY) {
		this.accountMMYYYY = accountMMYYYY;
	}

	public BigDecimal getApplicationDocumentNo() {
		return applicationDocumentNo;
	}

	public void setApplicationDocumentNo(BigDecimal applicationDocumentNo) {
		this.applicationDocumentNo = applicationDocumentNo;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getLocalOtherAdjCurrencyId() {
		return localOtherAdjCurrencyId;
	}

	public void setLocalOtherAdjCurrencyId(BigDecimal localOtherAdjCurrencyId) {
		this.localOtherAdjCurrencyId = localOtherAdjCurrencyId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getCollectionDocCode() {
		return collectionDocCode;
	}
	public void setCollectionDocCode(BigDecimal collectionDocCode) {
		this.collectionDocCode = collectionDocCode;
	}

	public BigDecimal getRemittanceTransactionId() {
		return remittanceTransactionId;
	}
	public void setRemittanceTransactionId(BigDecimal remittanceTransactionId) {
		this.remittanceTransactionId = remittanceTransactionId;
	}

	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}

	public BigDecimal getIdProofTypeId() {
		return idProofTypeId;
	}
	public void setIdProofTypeId(BigDecimal idProofTypeId) {
		this.idProofTypeId = idProofTypeId;
	}

	public String getHighValueAuthUser() {
		return highValueAuthUser;
	}
	public void setHighValueAuthUser(String highValueAuthUser) {
		this.highValueAuthUser = highValueAuthUser;
	}

	public Date getHighValueAuthDate() {
		return highValueAuthDate;
	}
	public void setHighValueAuthDate(Date highValueAuthDate) {
		this.highValueAuthDate = highValueAuthDate;
	}

	public BigDecimal getDeliveryModeId() {
		return deliveryModeId;
	}
	public void setDeliveryModeId(BigDecimal deliveryModeId) {
		this.deliveryModeId = deliveryModeId;
	}

	public String getDebitAccountNo() {
		return debitAccountNo;
	}
	public void setDebitAccountNo(String debitAccountNo) {
		this.debitAccountNo = debitAccountNo;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getBlackListIndicator() {
		return blackListIndicator;
	}
	public void setBlackListIndicator(String blackListIndicator) {
		this.blackListIndicator = blackListIndicator;
	}

	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}
	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}

	public BigDecimal getBeneficiaryBankBranchId() {
		return beneficiaryBankBranchId;
	}
	public void setBeneficiaryBankBranchId(BigDecimal beneficiaryBankBranchId) {
		this.beneficiaryBankBranchId = beneficiaryBankBranchId;
	}
	
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}
	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public String getTranscationStatus() {
		return transcationStatus;
	}
	public void setTranscationStatus(String transcationStatus) {
		this.transcationStatus = transcationStatus;
	}

	public String getTranscationStatusDesc() {
		return transcationStatusDesc;
	}
	public void setTranscationStatusDesc(String transcationStatusDesc) {
		this.transcationStatusDesc = transcationStatusDesc;
	}

	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	public BigDecimal getPurposeId() {
		return purposeId;
	}
	public void setPurposeId(BigDecimal purposeId) {
		this.purposeId = purposeId;
	}

	public String getForeignCurrencyCode() {
		return foreignCurrencyCode;
	}
	public void setForeignCurrencyCode(String foreignCurrencyCode) {
		this.foreignCurrencyCode = foreignCurrencyCode;
	}

	public String getLocalCurrencyCode() {
		return localCurrencyCode;
	}
	public void setLocalCurrencyCode(String localCurrencyCode) {
		this.localCurrencyCode = localCurrencyCode;
	}

	public BigDecimal getCollectionDocFinanceYear() {
		return collectionDocFinanceYear;
	}
	public void setCollectionDocFinanceYear(BigDecimal collectionDocFinanceYear) {
		this.collectionDocFinanceYear = collectionDocFinanceYear;
	}

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	

}
