package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HighValueCustomerDataTable  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal remittanceTransactionId;
	private BigDecimal applicationCountryId;
	private BigDecimal companyId;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentId;
	private BigDecimal documentNo;
	private BigDecimal branchId;
	private Date documentDate;
	private BigDecimal applicationFinanceYear;
	private BigDecimal applicationDocumentNo;
	private BigDecimal customerId;
	private BigDecimal customerRef;
	private BigDecimal bankCountryId;
	private BigDecimal corespondingCountryId;
	private BigDecimal bankId;
	private BigDecimal bankBranchId;
	private String debitAccountNo;
	private BigDecimal foreignCurrencyId;
	private BigDecimal foreignTranxAmount;
	private BigDecimal localTranxCurrencyId;
	private BigDecimal localTranxAmount;
	private BigDecimal exchangeRateApplied;
	private BigDecimal localCommisionCurrencyId;
	private BigDecimal localCommisionAmount;
	private BigDecimal localChargeCurrencyId;
	private BigDecimal localChargeAmount;
	private BigDecimal localDeliveryCurrencyId;
	private BigDecimal localDeliveryAmount;
	private BigDecimal localNetCurrencyId;
	private BigDecimal localNetTranxAmount;
	private String transactionStatus;
	private String transactionUpdatedBy;
	private Date transactionUpdatedDate;
	private BigDecimal batchFinancialYear;
	private BigDecimal batchDocumentNumber;
	private String generalLedgerEntry;
	private String generalLedgerErr;
	private BigDecimal fileFinancialYear;
	private BigDecimal fileNumber;
	private String bankReference;
	private String transferMode;
	private String webServiceStatus;
	private String fileCreation;
	private BigDecimal smsSeqNumber;
	private String highValueTranx;
	private BigDecimal collectionDocId;
	private BigDecimal collectionDocFinanceYear;
	private BigDecimal collectionDocumentNo;
	private String blackListIndicator;
	private BigDecimal remittanceModeId;
	private BigDecimal deliveryModeId;
	private Date accountMmyyyy;
	private String westernUnionMtcno;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String isactive;
	private BigDecimal usdAmount;
	

	private Boolean selectedrecord = false;

	public HighValueCustomerDataTable() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getRemittanceTransactionId() {
		return remittanceTransactionId;
	}

	public void setRemittanceTransactionId(BigDecimal remittanceTransactionId) {
		this.remittanceTransactionId = remittanceTransactionId;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}

	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public BigDecimal getApplicationFinanceYear() {
		return applicationFinanceYear;
	}

	public void setApplicationFinanceYear(BigDecimal applicationFinanceYear) {
		this.applicationFinanceYear = applicationFinanceYear;
	}

	public BigDecimal getApplicationDocumentNo() {
		return applicationDocumentNo;
	}

	public void setApplicationDocumentNo(BigDecimal applicationDocumentNo) {
		this.applicationDocumentNo = applicationDocumentNo;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getCustomerRef() {
		return customerRef;
	}

	public void setCustomerRef(BigDecimal customerRef) {
		this.customerRef = customerRef;
	}

	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}

	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}

	public BigDecimal getCorespondingCountryId() {
		return corespondingCountryId;
	}

	public void setCorespondingCountryId(BigDecimal corespondingCountryId) {
		this.corespondingCountryId = corespondingCountryId;
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

	public String getDebitAccountNo() {
		return debitAccountNo;
	}

	public void setDebitAccountNo(String debitAccountNo) {
		this.debitAccountNo = debitAccountNo;
	}

	public BigDecimal getForeignCurrencyId() {
		return foreignCurrencyId;
	}

	public void setForeignCurrencyId(BigDecimal foreignCurrencyId) {
		this.foreignCurrencyId = foreignCurrencyId;
	}

	public BigDecimal getForeignTranxAmount() {
		return foreignTranxAmount;
	}

	public void setForeignTranxAmount(BigDecimal foreignTranxAmount) {
		this.foreignTranxAmount = foreignTranxAmount;
	}

	public BigDecimal getLocalTranxCurrencyId() {
		return localTranxCurrencyId;
	}

	public void setLocalTranxCurrencyId(BigDecimal localTranxCurrencyId) {
		this.localTranxCurrencyId = localTranxCurrencyId;
	}

	public BigDecimal getLocalTranxAmount() {
		return localTranxAmount;
	}

	public void setLocalTranxAmount(BigDecimal localTranxAmount) {
		this.localTranxAmount = localTranxAmount;
	}

	public BigDecimal getExchangeRateApplied() {
		return exchangeRateApplied;
	}

	public void setExchangeRateApplied(BigDecimal exchangeRateApplied) {
		this.exchangeRateApplied = exchangeRateApplied;
	}

	public BigDecimal getLocalCommisionCurrencyId() {
		return localCommisionCurrencyId;
	}

	public void setLocalCommisionCurrencyId(BigDecimal localCommisionCurrencyId) {
		this.localCommisionCurrencyId = localCommisionCurrencyId;
	}

	public BigDecimal getLocalCommisionAmount() {
		return localCommisionAmount;
	}

	public void setLocalCommisionAmount(BigDecimal localCommisionAmount) {
		this.localCommisionAmount = localCommisionAmount;
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

	public BigDecimal getLocalNetTranxAmount() {
		return localNetTranxAmount;
	}

	public void setLocalNetTranxAmount(BigDecimal localNetTranxAmount) {
		this.localNetTranxAmount = localNetTranxAmount;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getTransactionUpdatedBy() {
		return transactionUpdatedBy;
	}

	public void setTransactionUpdatedBy(String transactionUpdatedBy) {
		this.transactionUpdatedBy = transactionUpdatedBy;
	}

	public Date getTransactionUpdatedDate() {
		return transactionUpdatedDate;
	}

	public void setTransactionUpdatedDate(Date transactionUpdatedDate) {
		this.transactionUpdatedDate = transactionUpdatedDate;
	}

	public BigDecimal getBatchFinancialYear() {
		return batchFinancialYear;
	}

	public void setBatchFinancialYear(BigDecimal batchFinancialYear) {
		this.batchFinancialYear = batchFinancialYear;
	}

	public BigDecimal getBatchDocumentNumber() {
		return batchDocumentNumber;
	}

	public void setBatchDocumentNumber(BigDecimal batchDocumentNumber) {
		this.batchDocumentNumber = batchDocumentNumber;
	}

	public String getGeneralLedgerEntry() {
		return generalLedgerEntry;
	}

	public void setGeneralLedgerEntry(String generalLedgerEntry) {
		this.generalLedgerEntry = generalLedgerEntry;
	}

	public String getGeneralLedgerErr() {
		return generalLedgerErr;
	}

	public void setGeneralLedgerErr(String generalLedgerErr) {
		this.generalLedgerErr = generalLedgerErr;
	}

	public BigDecimal getFileFinancialYear() {
		return fileFinancialYear;
	}

	public void setFileFinancialYear(BigDecimal fileFinancialYear) {
		this.fileFinancialYear = fileFinancialYear;
	}

	public BigDecimal getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(BigDecimal fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getBankReference() {
		return bankReference;
	}

	public void setBankReference(String bankReference) {
		this.bankReference = bankReference;
	}

	public String getTransferMode() {
		return transferMode;
	}

	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
	}

	public String getWebServiceStatus() {
		return webServiceStatus;
	}

	public void setWebServiceStatus(String webServiceStatus) {
		this.webServiceStatus = webServiceStatus;
	}

	public String getFileCreation() {
		return fileCreation;
	}

	public void setFileCreation(String fileCreation) {
		this.fileCreation = fileCreation;
	}

	public BigDecimal getSmsSeqNumber() {
		return smsSeqNumber;
	}

	public void setSmsSeqNumber(BigDecimal smsSeqNumber) {
		this.smsSeqNumber = smsSeqNumber;
	}

	public String getHighValueTranx() {
		return highValueTranx;
	}

	public void setHighValueTranx(String highValueTranx) {
		this.highValueTranx = highValueTranx;
	}

	public BigDecimal getCollectionDocId() {
		return collectionDocId;
	}

	public void setCollectionDocId(BigDecimal collectionDocId) {
		this.collectionDocId = collectionDocId;
	}

	public BigDecimal getCollectionDocFinanceYear() {
		return collectionDocFinanceYear;
	}

	public void setCollectionDocFinanceYear(BigDecimal collectionDocFinanceYear) {
		this.collectionDocFinanceYear = collectionDocFinanceYear;
	}

	public BigDecimal getCollectionDocumentNo() {
		return collectionDocumentNo;
	}

	public void setCollectionDocumentNo(BigDecimal collectionDocumentNo) {
		this.collectionDocumentNo = collectionDocumentNo;
	}

	public String getBlackListIndicator() {
		return blackListIndicator;
	}

	public void setBlackListIndicator(String blackListIndicator) {
		this.blackListIndicator = blackListIndicator;
	}

	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}

	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}

	public BigDecimal getDeliveryModeId() {
		return deliveryModeId;
	}

	public void setDeliveryModeId(BigDecimal deliveryModeId) {
		this.deliveryModeId = deliveryModeId;
	}

	public Date getAccountMmyyyy() {
		return accountMmyyyy;
	}

	public void setAccountMmyyyy(Date accountMmyyyy) {
		this.accountMmyyyy = accountMmyyyy;
	}

	public String getWesternUnionMtcno() {
		return westernUnionMtcno;
	}

	public void setWesternUnionMtcno(String westernUnionMtcno) {
		this.westernUnionMtcno = westernUnionMtcno;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	public BigDecimal getUsdAmount() {
		return usdAmount;
	}

	public void setUsdAmount(BigDecimal usdAmount) {
		this.usdAmount = usdAmount;
	}

	public Boolean getSelectedrecord() {
		return selectedrecord;
	}

	public void setSelectedrecord(Boolean selectedrecord) {
		this.selectedrecord = selectedrecord;
	}

}
