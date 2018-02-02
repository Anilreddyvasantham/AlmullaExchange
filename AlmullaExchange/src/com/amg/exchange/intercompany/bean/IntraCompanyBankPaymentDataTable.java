package com.amg.exchange.intercompany.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IntraCompanyBankPaymentDataTable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal intraTransactionId;
	private BigDecimal applicationCountryId;
	private BigDecimal beneficiaryId;
	private BigDecimal companyId;
	private BigDecimal customerId;
	private BigDecimal deliveryModeId;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentId;
	private BigDecimal documentNo;
	private BigDecimal paidCountryBranchId;
	private String pinNumber;
	private String recvCityName;
	private String recvCountryName;
	private String recvCountryTelCode;
	private String recvDistrictName;
	private String recvFirstName;
	private String recvFirstNameLocal;
	private String recvSecondName;
	private String recvSecondNameLocal;
	private String recvThirdName;
	private String recvThirdNameLocal;
	private String recvFourthName;
	private String recvFourthNameLocal;
	private String recvFifthName;
	private String recvFifthNameLocal;
	private BigDecimal recvMobileNumber;
	private String recvStateName;
	private String recvTelephoneNumber;
	private BigDecimal remittanceModeId;
	private String foreignCurrencyName;
	private BigDecimal sendcompanyCode;
	private BigDecimal foreignTrnxAmount;
	private String sendCountryName;
	private Date sendDocumentDate;
	private BigDecimal sendDocumentFinanceYear;
	private BigDecimal sendDocumentId;
	private BigDecimal sendDocumentNo;
	private String sendFirstName;
	private String sendFirstNameLocal;
	private String sendSecondName;
	private String sendSecondNameLocal;
	private String sendThirdName;
	private String sendThirdNameLocal;
	private String beneBankCode;
	private String beneBankName;
	private String beneBankBranchName;
	private String beneBankBranchCode;
	private String beneBankAccountNumber;
	private String localBankCode;
	private String transactionStatus;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String beneficiaryName;
	private boolean selectedRecords;
	
	
	public BigDecimal getIntraTransactionId() {
		return intraTransactionId;
	}
	public void setIntraTransactionId(BigDecimal intraTransactionId) {
		this.intraTransactionId = intraTransactionId;
	}
	
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	public BigDecimal getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(BigDecimal beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}
	
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	public BigDecimal getDeliveryModeId() {
		return deliveryModeId;
	}
	public void setDeliveryModeId(BigDecimal deliveryModeId) {
		this.deliveryModeId = deliveryModeId;
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
	
	public BigDecimal getPaidCountryBranchId() {
		return paidCountryBranchId;
	}
	public void setPaidCountryBranchId(BigDecimal paidCountryBranchId) {
		this.paidCountryBranchId = paidCountryBranchId;
	}
	
	public String getPinNumber() {
		return pinNumber;
	}
	public void setPinNumber(String pinNumber) {
		this.pinNumber = pinNumber;
	}
	
	public String getRecvCityName() {
		return recvCityName;
	}
	public void setRecvCityName(String recvCityName) {
		this.recvCityName = recvCityName;
	}
	
	public String getRecvCountryTelCode() {
		return recvCountryTelCode;
	}
	public void setRecvCountryTelCode(String recvCountryTelCode) {
		this.recvCountryTelCode = recvCountryTelCode;
	}
	
	public String getRecvDistrictName() {
		return recvDistrictName;
	}
	public void setRecvDistrictName(String recvDistrictName) {
		this.recvDistrictName = recvDistrictName;
	}
	
	public String getRecvFirstName() {
		return recvFirstName;
	}
	public void setRecvFirstName(String recvFirstName) {
		this.recvFirstName = recvFirstName;
	}
	
	public String getRecvFirstNameLocal() {
		return recvFirstNameLocal;
	}
	public void setRecvFirstNameLocal(String recvFirstNameLocal) {
		this.recvFirstNameLocal = recvFirstNameLocal;
	}
	
	public String getRecvSecondName() {
		return recvSecondName;
	}
	public void setRecvSecondName(String recvSecondName) {
		this.recvSecondName = recvSecondName;
	}
	
	public String getRecvSecondNameLocal() {
		return recvSecondNameLocal;
	}
	public void setRecvSecondNameLocal(String recvSecondNameLocal) {
		this.recvSecondNameLocal = recvSecondNameLocal;
	}
	
	public String getRecvThirdName() {
		return recvThirdName;
	}
	public void setRecvThirdName(String recvThirdName) {
		this.recvThirdName = recvThirdName;
	}
	
	public String getRecvThirdNameLocal() {
		return recvThirdNameLocal;
	}
	public void setRecvThirdNameLocal(String recvThirdNameLocal) {
		this.recvThirdNameLocal = recvThirdNameLocal;
	}
	
	public String getRecvFourthName() {
		return recvFourthName;
	}
	public void setRecvFourthName(String recvFourthName) {
		this.recvFourthName = recvFourthName;
	}
	
	public String getRecvFourthNameLocal() {
		return recvFourthNameLocal;
	}
	public void setRecvFourthNameLocal(String recvFourthNameLocal) {
		this.recvFourthNameLocal = recvFourthNameLocal;
	}
	
	public String getRecvFifthName() {
		return recvFifthName;
	}
	public void setRecvFifthName(String recvFifthName) {
		this.recvFifthName = recvFifthName;
	}
	
	public String getRecvFifthNameLocal() {
		return recvFifthNameLocal;
	}
	public void setRecvFifthNameLocal(String recvFifthNameLocal) {
		this.recvFifthNameLocal = recvFifthNameLocal;
	}
	
	public BigDecimal getRecvMobileNumber() {
		return recvMobileNumber;
	}
	public void setRecvMobileNumber(BigDecimal recvMobileNumber) {
		this.recvMobileNumber = recvMobileNumber;
	}
	
	public String getRecvStateName() {
		return recvStateName;
	}
	public void setRecvStateName(String recvStateName) {
		this.recvStateName = recvStateName;
	}
	
	public String getRecvTelephoneNumber() {
		return recvTelephoneNumber;
	}
	public void setRecvTelephoneNumber(String recvTelephoneNumber) {
		this.recvTelephoneNumber = recvTelephoneNumber;
	}
	
	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}
	
	public Date getSendDocumentDate() {
		return sendDocumentDate;
	}
	public void setSendDocumentDate(Date sendDocumentDate) {
		this.sendDocumentDate = sendDocumentDate;
	}
	
	public BigDecimal getSendDocumentFinanceYear() {
		return sendDocumentFinanceYear;
	}
	public void setSendDocumentFinanceYear(BigDecimal sendDocumentFinanceYear) {
		this.sendDocumentFinanceYear = sendDocumentFinanceYear;
	}
	
	public BigDecimal getSendDocumentId() {
		return sendDocumentId;
	}
	public void setSendDocumentId(BigDecimal sendDocumentId) {
		this.sendDocumentId = sendDocumentId;
	}
	
	public BigDecimal getSendDocumentNo() {
		return sendDocumentNo;
	}
	public void setSendDocumentNo(BigDecimal sendDocumentNo) {
		this.sendDocumentNo = sendDocumentNo;
	}
	
	public String getSendFirstName() {
		return sendFirstName;
	}
	public void setSendFirstName(String sendFirstName) {
		this.sendFirstName = sendFirstName;
	}
	
	public String getSendFirstNameLocal() {
		return sendFirstNameLocal;
	}
	public void setSendFirstNameLocal(String sendFirstNameLocal) {
		this.sendFirstNameLocal = sendFirstNameLocal;
	}
	
	public String getSendSecondName() {
		return sendSecondName;
	}
	public void setSendSecondName(String sendSecondName) {
		this.sendSecondName = sendSecondName;
	}
	
	public String getSendSecondNameLocal() {
		return sendSecondNameLocal;
	}
	public void setSendSecondNameLocal(String sendSecondNameLocal) {
		this.sendSecondNameLocal = sendSecondNameLocal;
	}
	
	public String getSendThirdName() {
		return sendThirdName;
	}
	public void setSendThirdName(String sendThirdName) {
		this.sendThirdName = sendThirdName;
	}
	
	public String getSendThirdNameLocal() {
		return sendThirdNameLocal;
	}
	public void setSendThirdNameLocal(String sendThirdNameLocal) {
		this.sendThirdNameLocal = sendThirdNameLocal;
	}
	
	public String getBeneBankCode() {
		return beneBankCode;
	}
	public void setBeneBankCode(String beneBankCode) {
		this.beneBankCode = beneBankCode;
	}
	
	public String getBeneBankName() {
		return beneBankName;
	}
	public void setBeneBankName(String beneBankName) {
		this.beneBankName = beneBankName;
	}
	
	public String getBeneBankBranchName() {
		return beneBankBranchName;
	}
	public void setBeneBankBranchName(String beneBankBranchName) {
		this.beneBankBranchName = beneBankBranchName;
	}
	
	public String getBeneBankBranchCode() {
		return beneBankBranchCode;
	}
	public void setBeneBankBranchCode(String beneBankBranchCode) {
		this.beneBankBranchCode = beneBankBranchCode;
	}
	
	public String getBeneBankAccountNumber() {
		return beneBankAccountNumber;
	}
	public void setBeneBankAccountNumber(String beneBankAccountNumber) {
		this.beneBankAccountNumber = beneBankAccountNumber;
	}
	
	public String getLocalBankCode() {
		return localBankCode;
	}
	public void setLocalBankCode(String localBankCode) {
		this.localBankCode = localBankCode;
	}
	
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	
	public String getRecvCountryName() {
		return recvCountryName;
	}
	public void setRecvCountryName(String recvCountryName) {
		this.recvCountryName = recvCountryName;
	}
	
	public String getSendCountryName() {
		return sendCountryName;
	}
	public void setSendCountryName(String sendCountryName) {
		this.sendCountryName = sendCountryName;
	}
	
	public String getForeignCurrencyName() {
		return foreignCurrencyName;
	}
	public void setForeignCurrencyName(String foreignCurrencyName) {
		this.foreignCurrencyName = foreignCurrencyName;
	}
	
	public BigDecimal getSendcompanyCode() {
		return sendcompanyCode;
	}
	public void setSendcompanyCode(BigDecimal sendcompanyCode) {
		this.sendcompanyCode = sendcompanyCode;
	}
	
	public BigDecimal getForeignTrnxAmount() {
		return foreignTrnxAmount;
	}
	public void setForeignTrnxAmount(BigDecimal foreignTrnxAmount) {
		this.foreignTrnxAmount = foreignTrnxAmount;
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
	
	public boolean isSelectedRecords() {
		return selectedRecords;
	}
	public void setSelectedRecords(boolean selectedRecords) {
		this.selectedRecords = selectedRecords;
	}
		
}
