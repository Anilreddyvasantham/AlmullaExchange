package com.amg.exchange.cancelreissue.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "VW_INQURY_TRANSACTION")
public class ViewRemittanceInquiryTransaction implements Serializable{
	
	 
	private static final long serialVersionUID = 1L;
	private BigDecimal idNo;
	private BigDecimal applicationCountryId;
	private BigDecimal applicationDocumentNo;
	private BigDecimal applicationFinYear;
	private String bankCode;
	private BigDecimal bankBranchCode;
	private BigDecimal bankBranchId;
	private BigDecimal bankCountryId;
	private BigDecimal bankId;
	private String benificiaryAccountNo;
	private String benificiaryBank;
	private String benificiaryBranch;
	private String benificiaryInterBank1;
	private String benificiaryInterBank2;
	private String benificiaryName;
	private String benificiarySwiftBank1;
	private String benificiarySwiftBank2;
	private String beneficiaryTelephone;
	private String blackCreatedUser;
	private String blackListClear;
	private Date blackListDate;
	private String blackListIndicator;
	private String blackListReason;
	private String blackListRemarks;
	private String blackListUser;
	private BigDecimal branchId;
	private String branchName;
	private String cancellationStatus;
	private BigDecimal cancelDocCode;
	private Date cancelDocDate;
	private BigDecimal cancelDocFinanceYear;
	private BigDecimal cancelDocNo;
	private BigDecimal cancellationDocNo;
	private BigDecimal cancellationDocCode;
	private BigDecimal cancellationDocFinanceYear;
	private BigDecimal cancellationDocId;
	private BigDecimal companyId;
    private String correspondingBankName;
    private Date createdDate;
	private BigDecimal customerId;
	private BigDecimal customerRefNo;
	private BigDecimal deliveryModeId;
	private BigDecimal documentCode;
	private Date documentDate;
	private BigDecimal documentFinYear;
	private BigDecimal documentFinYearId;
	private BigDecimal documentId;
	private BigDecimal documentNo;
	private BigDecimal exchangeRateApplied;
	private String fileCreation;
	private String foreignCurrencyCode;
	private String foreignCurrencyDesc;
	private BigDecimal foreignCurrencyId;
	private BigDecimal foreignTrxAmount;
	private String instruction;
	private BigDecimal localAmount;
	private BigDecimal localChargeAmount;
	private BigDecimal localChargeCurrnecyId;
	private BigDecimal localCommisionAmount;
	private BigDecimal localCurrnecyCommisionId;
	private String localCurrencyCode;
	private String localCurrencyDesc;
	private BigDecimal localCurrnecyId;
	private BigDecimal localDeliveryAmount;
	private BigDecimal localDeliveryCurrnecyId;
	private BigDecimal localNetCurrnecyId;
	private BigDecimal localNetTrxAmount;
	private BigDecimal loyaltyPointEncashed;
	private String loyaltyPointInd;
	private String PayTo;
	private  String problemStatus;
	private BigDecimal remittanceModeId;
	private BigDecimal remittanceTransactionId;
	private BigDecimal sourceOfIncome;
	private  String spotRateInd;
	private BigDecimal stopDocCode;
	private Date stopDocDate;
	private BigDecimal stopDocFinanceYear;
	private BigDecimal stopDocNo;
	private  String transactionStatus;
	private String beneficiaryAddress;
	private String serviceGroupCode;
	private String serviceDroupDesc;
	private BigDecimal serviceGroupID;
	
	//new added columns
	private String remittanceDesc;
	private String deliveryDesc;
	private String countryBranchName;
	private String applcreatedBy;
	private String createdBy;
	private String highValueAuthUser;
	private Date highValueAuthDate;
	private String currencyQuoteName;
	private String identityInt;
	private BigDecimal identityTypeId;
	private String sourceOfIncomeDesc;
	private BigDecimal beneficiaryBankBranchId;
	private BigDecimal beneficiaryBankId;
	private String highValueTrnx;
	private String generalLedger;
	private Date accmmYY;
	private String firstName;
	private String lastName;
	private String middleName;
	private String contactNumber;
	private String bankStatus;
	private String bankStatusUser;
	private Date bankStatusDate;
	private String westernUnionMTCNo;
	private String amlStatus;
	private String webServiceStatus;
	
	private BigDecimal beneficiary_Id;
	private BigDecimal beneficiary_Account_Seq_Id;
	private BigDecimal customerReference;
	private String averageRate;
	private String authType;
	private String authorizedBy;
	private Date authModifiedDate;
	private String modifiedBy;
	private Date  modifiedDate;

	
	@Column(name = "WESTERN_UNION_MTCNO")
	public String getWesternUnionMTCNo() {
		return westernUnionMTCNo;
	}
	public void setWesternUnionMTCNo(String westernUnionMTCNo) {
		this.westernUnionMTCNo = westernUnionMTCNo;
	}
	
	@Column(name = "AML_STATUS")
	public String getAmlStatus() {
		return amlStatus;
	}
	public void setAmlStatus(String amlStatus) {
		this.amlStatus = amlStatus;
	}
	
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
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Column(name = "REMITTANCE_DESC")
	public String getRemittanceDesc() {
		return remittanceDesc;
	}
	public void setRemittanceDesc(String remittanceDesc) {
		this.remittanceDesc = remittanceDesc;
	}
	
	@Column(name = "DELIVERY_DESC")
	public String getDeliveryDesc() {
		return deliveryDesc;
	}
	public void setDeliveryDesc(String deliveryDesc) {
		this.deliveryDesc = deliveryDesc;
	}
	
	@Column(name = "COUNTRY_BRANCH_NAME")
	public String getCountryBranchName() {
		return countryBranchName;
	}
	public void setCountryBranchName(String countryBranchName) {
		this.countryBranchName = countryBranchName;
	}
	
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	
	@Column(name = "QUOTE_NAME")
	public String getCurrencyQuoteName() {
		return currencyQuoteName;
	}
	public void setCurrencyQuoteName(String currencyQuoteName) {
		this.currencyQuoteName = currencyQuoteName;
	}
	
	@Column(name = "IDENTITY_INT")
	public String getIdentityInt() {
		return identityInt;
	}
	public void setIdentityInt(String identityInt) {
		this.identityInt = identityInt;
	}
	
	@Column(name = "IDPROOF_TYPE_ID")
	public BigDecimal getIdentityTypeId() {
		return identityTypeId;
	}
	public void setIdentityTypeId(BigDecimal identityTypeId) {
		this.identityTypeId = identityTypeId;
	}

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
	
	@Column(name = "APPLICATION_DOCUMENT_NO")
	public BigDecimal getApplicationDocumentNo() {
		return applicationDocumentNo;
	}
	public void setApplicationDocumentNo(BigDecimal applicationDocumentNo) {
		this.applicationDocumentNo = applicationDocumentNo;
	}
	
	@Column(name = "APPLICATION_FINANCE_YEAR")
	public BigDecimal getApplicationFinYear() {
		return applicationFinYear;
	}
	public void setApplicationFinYear(BigDecimal applicationFinYear) {
		this.applicationFinYear = applicationFinYear;
	}
	
	@Column(name = "BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name = "BANK_BRANCH_CODE")
	public BigDecimal getBankBranchCode() {
		return bankBranchCode;
	}
	public void setBankBranchCode(BigDecimal bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}
	
	@Column(name = "BANK_BRANCH_ID")
	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}
	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}
	
	@Column(name = "BANK_COUNTRY_ID")
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}
	
	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	@Column(name = "BENEFICIARY_ACCOUNT_NO")
	public String getBenificiaryAccountNo() {
		return benificiaryAccountNo;
	}
	public void setBenificiaryAccountNo(String benificiaryAccountNo) {
		this.benificiaryAccountNo = benificiaryAccountNo;
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
	
	@Column(name = "BENEFICIARY_NAME")
	public String getBenificiaryName() {
		return benificiaryName;
	}
	public void setBenificiaryName(String benificiaryName) {
		this.benificiaryName = benificiaryName;
	}
	
	@Column(name = "BENEFICIARY_SWIFT_BANK1")
	public String getBenificiarySwiftBank1() {
		return benificiarySwiftBank1;
	}
	public void setBenificiarySwiftBank1(String benificiarySwiftBank1) {
		this.benificiarySwiftBank1 = benificiarySwiftBank1;
	}
	
	@Column(name = "BENEFICIARY_SWIFT_BANK2")
	public String getBenificiarySwiftBank2() {
		return benificiarySwiftBank2;
	}
	public void setBenificiarySwiftBank2(String benificiarySwiftBank2) {
		this.benificiarySwiftBank2 = benificiarySwiftBank2;
	}
	
	@Column(name = "BENE_TELEPHONE")
	public String getBeneficiaryTelephone() {
		return beneficiaryTelephone;
	}
	public void setBeneficiaryTelephone(String beneficiaryTelephone) {
		this.beneficiaryTelephone = beneficiaryTelephone;
	}
	
	@Column(name = "BLACK_CLEARED_USER")
	public String getBlackCreatedUser() {
		return blackCreatedUser;
	}
	public void setBlackCreatedUser(String blackCreatedUser) {
		this.blackCreatedUser = blackCreatedUser;
	}
	
	@Column(name = "BLACK_LIST_CLEAR")
	public String getBlackListClear() {
		return blackListClear;
	}
	public void setBlackListClear(String blackListClear) {
		this.blackListClear = blackListClear;
	}
	
	@Column(name = "BLACK_LIST_DATE")
	public Date getBlackListDate() {
		return blackListDate;
	}
	public void setBlackListDate(Date blackListDate) {
		this.blackListDate = blackListDate;
	}
	
	@Column(name = "BLACK_LIST_INDICATOR")
	public String getBlackListIndicator() {
		return blackListIndicator;
	}
	public void setBlackListIndicator(String blackListIndicator) {
		this.blackListIndicator = blackListIndicator;
	}
	
	@Column(name = "BLACK_LIST_REASON")
	public String getBlackListReason() {
		return blackListReason;
	}
	public void setBlackListReason(String blackListReason) {
		this.blackListReason = blackListReason;
	}
	
	@Column(name = "BLACK_LIST_REMARKS")
	public String getBlackListRemarks() {
		return blackListRemarks;
	}
	public void setBlackListRemarks(String blackListRemarks) {
		this.blackListRemarks = blackListRemarks;
	}
	
	@Column(name = "BLACK_LIST_USER")
	public String getBlackListUser() {
		return blackListUser;
	}
	public void setBlackListUser(String blackListUser) {
		this.blackListUser = blackListUser;
	}
	
	@Column(name = "BRANCH_ID")
	public BigDecimal getBranchId() {
		return branchId;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}
	
	@Column(name = "BRANCH_NAME")
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	@Column(name = "CANCELLATION_STATUS")
	public String getCancellationStatus() {
		return cancellationStatus;
	}
	public void setCancellationStatus(String cancellationStatus) {
		this.cancellationStatus = cancellationStatus;
	}
	
	@Column(name = "CANCEL_DOCUMENT_CODE")
	public BigDecimal getCancelDocCode() {
		return cancelDocCode;
	}
	public void setCancelDocCode(BigDecimal cancelDocCode) {
		this.cancelDocCode = cancelDocCode;
	}
	
	@Column(name = "CANCEL_DOCUMENT_DATE")
	public Date getCancelDocDate() {
		return cancelDocDate;
	}
	public void setCancelDocDate(Date cancelDocDate) {
		this.cancelDocDate = cancelDocDate;
	}
	
	@Column(name = "CANCEL_DOCUMENT_FINANCE_YEAR")
	public BigDecimal getCancelDocFinanceYear() {
		return cancelDocFinanceYear;
	}
	public void setCancelDocFinanceYear(BigDecimal cancelDocFinanceYear) {
		this.cancelDocFinanceYear = cancelDocFinanceYear;
	}
	
	@Column(name = "CANCEL_DOCUMENT_NO")
	public BigDecimal getCancelDocNo() {
		return cancelDocNo;
	}
	public void setCancelDocNo(BigDecimal cancelDocNo) {
		this.cancelDocNo = cancelDocNo;
	}

	@Column(name = "COLLECTION_DOCUMENT_NO")
	public BigDecimal getCancellationDocNo() {
		return cancellationDocNo;
	}
	public void setCancellationDocNo(BigDecimal cancellationDocNo) {
		this.cancellationDocNo = cancellationDocNo;
	}
	
	@Column(name = "COLLECTION_DOC_CODE")
	public BigDecimal getCancellationDocCode() {
		return cancellationDocCode;
	}
	public void setCancellationDocCode(BigDecimal cancellationDocCode) {
		this.cancellationDocCode = cancellationDocCode;
	}
	
	@Column(name = "COLLECTION_DOC_FINANCE_YEAR")
	public BigDecimal getCancellationDocFinanceYear() {
		return cancellationDocFinanceYear;
	}
	public void setCancellationDocFinanceYear(BigDecimal cancellationDocFinanceYear) {
		this.cancellationDocFinanceYear = cancellationDocFinanceYear;
	}
	
	@Column(name = "COLLECTION_DOC_ID")
	public BigDecimal getCancellationDocId() {
		return cancellationDocId;
	}
	public void setCancellationDocId(BigDecimal cancellationDocId) {
		this.cancellationDocId = cancellationDocId;
	}
	
	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	
	@Column(name = "CORRESPONDING_BANK_NAME")
	public String getCorrespondingBankName() {
		return correspondingBankName;
	}
	public void setCorrespondingBankName(String correspondingBankName) {
		this.correspondingBankName = correspondingBankName;
	}
	
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	@Column(name = "CUSTOMER_REFERENCE")
	public BigDecimal getCustomerRefNo() {
		return customerRefNo;
	}
	public void setCustomerRefNo(BigDecimal customerRefNo) {
		this.customerRefNo = customerRefNo;
	}
	
	@Column(name = "DELIVERY_MODE_ID")
	public BigDecimal getDeliveryModeId() {
		return deliveryModeId;
	}
	public void setDeliveryModeId(BigDecimal deliveryModeId) {
		this.deliveryModeId = deliveryModeId;
	}
	
	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}
	
	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinYear() {
		return documentFinYear;
	}
	public void setDocumentFinYear(BigDecimal documentFinYear) {
		this.documentFinYear = documentFinYear;
	}
	
	@Column(name = "DOCUMENT_FINANCE_YEAR_ID")
	public BigDecimal getDocumentFinYearId() {
		return documentFinYearId;
	}
	public void setDocumentFinYearId(BigDecimal documentFinYearId) {
		this.documentFinYearId = documentFinYearId;
	}
	
	@Column(name = "DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}
	
	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	@Column(name = "EXCHANGE_RATE_APPLIED")
	public BigDecimal getExchangeRateApplied() {
		return exchangeRateApplied;
	}
	public void setExchangeRateApplied(BigDecimal exchangeRateApplied) {
		this.exchangeRateApplied = exchangeRateApplied;
	}
	
	@Column(name = "FILE_CREATION")
	public String getFileCreation() {
		return fileCreation;
	}
	public void setFileCreation(String fileCreation) {
		this.fileCreation = fileCreation;
	}
	
	@Column(name = "FOREIGN_CURRENCY_CODE")
	public String getForeignCurrencyCode() {
		return foreignCurrencyCode;
	}
	public void setForeignCurrencyCode(String foreignCurrencyCode) {
		this.foreignCurrencyCode = foreignCurrencyCode;
	}
	
	@Column(name = "FOREIGN_CURRENCY_DESC")
	public String getForeignCurrencyDesc() {
		return foreignCurrencyDesc;
	}
	public void setForeignCurrencyDesc(String foreignCurrencyDesc) {
		this.foreignCurrencyDesc = foreignCurrencyDesc;
	}
	
	@Column(name = "FOREIGN_CURRENCY_ID")
	public BigDecimal getForeignCurrencyId() {
		return foreignCurrencyId;
	}
	public void setForeignCurrencyId(BigDecimal foreignCurrencyId) {
		this.foreignCurrencyId = foreignCurrencyId;
	}
	
	@Column(name = "FOREIGN_TRANX_AMOUNT")
	public BigDecimal getForeignTrxAmount() {
		return foreignTrxAmount;
	}
	public void setForeignTrxAmount(BigDecimal foreignTrxAmount) {
		this.foreignTrxAmount = foreignTrxAmount;
	}
	
	@Column(name = "INSTRUCTION")
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	@Column(name = "LOCAL_AMOUNT")
	public BigDecimal getLocalAmount() {
		return localAmount;
	}
	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}
	
	@Column(name = "LOCAL_CHARGE_AMOUNT")
	public BigDecimal getLocalChargeAmount() {
		return localChargeAmount;
	}
	public void setLocalChargeAmount(BigDecimal localChargeAmount) {
		this.localChargeAmount = localChargeAmount;
	}
	
	@Column(name = "LOCAL_CHARGE_CURRENCY_ID")
	public BigDecimal getLocalChargeCurrnecyId() {
		return localChargeCurrnecyId;
	}
	public void setLocalChargeCurrnecyId(BigDecimal localChargeCurrnecyId) {
		this.localChargeCurrnecyId = localChargeCurrnecyId;
	}
	
	@Column(name = "LOCAL_COMMISION_AMOUNT")
	public BigDecimal getLocalCommisionAmount() {
		return localCommisionAmount;
	}
	public void setLocalCommisionAmount(BigDecimal localCommisionAmount) {
		this.localCommisionAmount = localCommisionAmount;
	}
	
	@Column(name = "LOCAL_COMMISION_CURRENCY_ID")
	public BigDecimal getLocalCurrnecyCommisionId() {
		return localCurrnecyCommisionId;
	}
	public void setLocalCurrnecyCommisionId(BigDecimal localCurrnecyCommisionId) {
		this.localCurrnecyCommisionId = localCurrnecyCommisionId;
	}
	
	@Column(name = "LOCAL_CURRENCY_CODE")
	public String getLocalCurrencyCode() {
		return localCurrencyCode;
	}
	public void setLocalCurrencyCode(String localCurrencyCode) {
		this.localCurrencyCode = localCurrencyCode;
	}
	
	@Column(name = "LOCAL_CURRENCY_DESC")
	public String getLocalCurrencyDesc() {
		return localCurrencyDesc;
	}
	public void setLocalCurrencyDesc(String localCurrencyDesc) {
		this.localCurrencyDesc = localCurrencyDesc;
	}
	
	@Column(name = "LOCAL_CURRENCY_ID")
	public BigDecimal getLocalCurrnecyId() {
		return localCurrnecyId;
	}
	public void setLocalCurrnecyId(BigDecimal localCurrnecyId) {
		this.localCurrnecyId = localCurrnecyId;
	}
	
	@Column(name = "LOCAL_DELIVERY_AMOUNT")
	public BigDecimal getLocalDeliveryAmount() {
		return localDeliveryAmount;
	}
	public void setLocalDeliveryAmount(BigDecimal localDeliveryAmount) {
		this.localDeliveryAmount = localDeliveryAmount;
	}
	
	@Column(name = "LOCAL_DELIVERY_CURRENCY_ID")
	public BigDecimal getLocalDeliveryCurrnecyId() {
		return localDeliveryCurrnecyId;
	}
	public void setLocalDeliveryCurrnecyId(BigDecimal localDeliveryCurrnecyId) {
		this.localDeliveryCurrnecyId = localDeliveryCurrnecyId;
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
	
	@Column(name = "LOYALTY_POINTS_ENCASHED")
	public BigDecimal getLoyaltyPointEncashed() {
		return loyaltyPointEncashed;
	}
	public void setLoyaltyPointEncashed(BigDecimal loyaltyPointEncashed) {
		this.loyaltyPointEncashed = loyaltyPointEncashed;
	}
	
	@Column(name = "LOYALTY_POINTS_IND")
	public String getLoyaltyPointInd() {
		return loyaltyPointInd;
	}
	public void setLoyaltyPointInd(String loyaltyPointInd) {
		this.loyaltyPointInd = loyaltyPointInd;
	}
	
	@Column(name = "PAYTO")
	public String getPayTo() {
		return PayTo;
	}
	public void setPayTo(String payTo) {
		PayTo = payTo;
	}
	
	@Column(name = "PROBLEM_STATUS")
	public String getProblemStatus() {
		return problemStatus;
	}
	public void setProblemStatus(String problemStatus) {
		this.problemStatus = problemStatus;
	}
	
	@Column(name = "REMITTANCE_MODE_ID")
	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}
	
	@Column(name = "REMITTANCE_TRANSACTION_ID")
	public BigDecimal getRemittanceTransactionId() {
		return remittanceTransactionId;
	}
	public void setRemittanceTransactionId(BigDecimal remittanceTransactionId) {
		this.remittanceTransactionId = remittanceTransactionId;
	}
	
	@Column(name = "SOURCE_OF_INCOME")
	public BigDecimal getSourceOfIncome() {
		return sourceOfIncome;
	}
	public void setSourceOfIncome(BigDecimal sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}
	
	@Column(name = "SPOT_RATE_IND")
	public String getSpotRateInd() {
		return spotRateInd;
	}
	public void setSpotRateInd(String spotRateInd) {
		this.spotRateInd = spotRateInd;
	}
	
	@Column(name = "STOP_DOCUMENT_CODE")
	public BigDecimal getStopDocCode() {
		return stopDocCode;
	}
	public void setStopDocCode(BigDecimal stopDocCode) {
		this.stopDocCode = stopDocCode;
	}
	
	@Column(name = "STOP_DOCUMENT_DATE")
	public Date getStopDocDate() {
		return stopDocDate;
	}
	public void setStopDocDate(Date stopDocDate) {
		this.stopDocDate = stopDocDate;
	}
	
	@Column(name = "STOP_DOCUMENT_FINANCE_YEAR")
	public BigDecimal getStopDocFinanceYear() {
		return stopDocFinanceYear;
	}
	public void setStopDocFinanceYear(BigDecimal stopDocFinanceYear) {
		this.stopDocFinanceYear = stopDocFinanceYear;
	}
	
	@Column(name = "STOP_DOCUMENT_NO")
	public BigDecimal getStopDocNo() {
		return stopDocNo;
	}
	public void setStopDocNo(BigDecimal stopDocNo) {
		this.stopDocNo = stopDocNo;
	}
	
	@Column(name = "TRANSACTION_STATUS")
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	@Column(name = "BENE_ADDRESS")
	public String getBeneficiaryAddress() {
		return beneficiaryAddress;
	}
	public void setBeneficiaryAddress(String beneficiaryAddress) {
		this.beneficiaryAddress = beneficiaryAddress;
	}
	
	@Column(name = "SERVICE_GROUP_CODE")
	public String getServiceGroupCode() {
		return serviceGroupCode;
	}
	public void setServiceGroupCode(String serviceGroupCode) {
		this.serviceGroupCode = serviceGroupCode;
	}
	
	@Column(name = "SERVICE_GROUP_DESC")
	public String getServiceDroupDesc() {
		return serviceDroupDesc;
	}
	public void setServiceDroupDesc(String serviceDroupDesc) {
		this.serviceDroupDesc = serviceDroupDesc;
	}
	
	@Column(name = "SERVICE_GROUP_ID")
	public BigDecimal getServiceGroupID() {
		return serviceGroupID;
	}
	public void setServiceGroupID(BigDecimal serviceGroupID) {
		this.serviceGroupID = serviceGroupID;
	}
	
	@Column(name = "SOURCE_OF_INCOME_DESC")
	public String getSourceOfIncomeDesc() {
		return sourceOfIncomeDesc;
	}
	public void setSourceOfIncomeDesc(String sourceOfIncomeDesc) {
		this.sourceOfIncomeDesc = sourceOfIncomeDesc;
	}
	
	@Column(name = "BENEFICIARY_BANK_BRANCH_ID")
	public BigDecimal getBeneficiaryBankBranchId() {
		return beneficiaryBankBranchId;
	}
	public void setBeneficiaryBankBranchId(BigDecimal beneficiaryBankBranchId) {
		this.beneficiaryBankBranchId = beneficiaryBankBranchId;
	}
	
	@Column(name = "BENEFICIARY_BANK_ID")
	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}
	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}
	
	@Column(name = "HIGH_VALUE_TRANX")
	public String getHighValueTrnx() {
		return highValueTrnx;
	}
	public void setHighValueTrnx(String highValueTrnx) {
		this.highValueTrnx = highValueTrnx;
	}
	
	@Column(name = "GENERAL_LEDGER_ERR")
	public String getGeneralLedger() {
		return generalLedger;
	}
	public void setGeneralLedger(String generalLedger) {
		this.generalLedger = generalLedger;
	}
	
	@Column(name = "ACCOUNT_MMYYYY")
	public Date getAccmmYY() {
		return accmmYY;
	}
	public void setAccmmYY(Date accmmYY) {
		this.accmmYY = accmmYY;
	}
	
	@Column(name = "BANK_STATUS")
	public String getBankStatus() {
		return bankStatus;
	}
	public void setBankStatus(String bankStatus) {
		this.bankStatus = bankStatus;
	}
	
	@Column(name = "BANK_STATUS_USER")
	public String getBankStatusUser() {
		return bankStatusUser;
	}
	public void setBankStatusUser(String bankStatusUser) {
		this.bankStatusUser = bankStatusUser;
	}
	
	@Column(name = "BANK_STATUS_DATE")
	public Date getBankStatusDate() {
		return bankStatusDate;
	}
	public void setBankStatusDate(Date bankStatusDate) {
		this.bankStatusDate = bankStatusDate;
	}
	
	@Column(name = "WEB_SERVICE_STATUS")
	public String getWebServiceStatus() {
		return webServiceStatus;
	}
	public void setWebServiceStatus(String webServiceStatus) {
		this.webServiceStatus = webServiceStatus;
	}
	
	@Column(name = "APP_CREATED_BY")
	public String getApplcreatedBy() {
		return applcreatedBy;
	}
	public void setApplcreatedBy(String applcreatedBy) {
		this.applcreatedBy = applcreatedBy;
	}
	
	@Column(name = "BENEFICIARY_ID")
	public BigDecimal getBeneficiary_Id() {
		return beneficiary_Id;
	}
	public void setBeneficiary_Id(BigDecimal beneficiary_Id) {
		this.beneficiary_Id = beneficiary_Id;
	}
	
	@Column(name = "BENEFICARY_ACCOUNT_SEQ_ID")
	public BigDecimal getBeneficiary_Account_Seq_Id() {
		return beneficiary_Account_Seq_Id;
	}
	public void setBeneficiary_Account_Seq_Id(BigDecimal beneficiary_Account_Seq_Id) {
		this.beneficiary_Account_Seq_Id = beneficiary_Account_Seq_Id;
	}
	
	@Column(name = "CUSTOMERREFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	
	@Column(name = "AVERAGE_RATE")
	public String getAverageRate() {
		return averageRate;
	}
	public void setAverageRate(String averageRate) {
		this.averageRate = averageRate;
	}
	
	@Column(name = "AUTH_TYP")
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	
	@Column(name = "AUTHORIZED_BY")
	public String getAuthorizedBy() {
		return authorizedBy;
	}
	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
	}
	
	@Column(name = "AUTH_MODIFIED_DATE")
	public Date getAuthModifiedDate() {
		return authModifiedDate;
	}

	public void setAuthModifiedDate(Date authModifiedDate) {
		this.authModifiedDate = authModifiedDate;
	}
	
	@Column(name="TRANSX_MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
}
