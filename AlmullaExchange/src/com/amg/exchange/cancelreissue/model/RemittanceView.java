package com.amg.exchange.cancelreissue.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Author Nazish Ehsan Hashmi
 */
@Entity
@Table(name = "V_EX_REMITTANCE")
public class RemittanceView {
	private BigDecimal remittanceTransactionId;
	private BigDecimal applicationCountryId;
	private BigDecimal companyId;
	private BigDecimal documentFinYear;
	private BigDecimal documentFinYearId;
	private BigDecimal documentId;
	private BigDecimal documentCode;
	private BigDecimal documentNo;
	private BigDecimal branchId;
	private Date documentDate;
	private BigDecimal applicationFinYear;
	private BigDecimal applicationDocumentNo;
	private BigDecimal remittanceModeId;
	private BigDecimal deliveryModeId;
	private BigDecimal saleCurrencyId;
	private String saleCurrencyCode;
	private String saleCurrencyDesc;
	private BigDecimal saleAmount;
	private BigDecimal purchaseCurrencyId;
	private String purchaseCurrencyCode;
	private String purchaseCurrencyDesc;
	private BigDecimal purchaseAmount;
	private BigDecimal exchangeRateApplied;
	private BigDecimal localCurrnecyCommisionId;
	private BigDecimal localCommisionAmount;
	private BigDecimal localChargeCurrnecyId;
	private BigDecimal localChargeAmount;
	private BigDecimal localDeliveryCurrnecyId;
	private BigDecimal localDeliveryAmount;
	private BigDecimal localNetCurrnecyId;
	private BigDecimal localNetTrxAmount;
	private BigDecimal bankId;
	private String correspondingBankName;
	private BigDecimal bankBranchId;
	private String branchName;
	private String benificiaryName;
	private String benificiaryBank;
	private String benificiaryBranch;
	private String benificiaryAccountNo;
	private String instruction;
	private String benificiaryInterBank1;
	private String benificiaryInterBank2;
	private String benificiarySwiftBank1;
	private String benificiarySwiftBank2;
	private BigDecimal customerId;
	private BigDecimal customerRefNo;
	private String phone;
	private String sourceOfIncome;
	private String bankCode;
	private BigDecimal bankBranchCode;
	private String beneficiaryAddress;
	private String PayTo;
	private String beneficiaryTelephone;
	private String serviceGroupCode;
	private String serviceDroupDesc;
	private BigDecimal serviceGroupID;
	private Date createdDate;
	private String transactionStatus;
	private String fileCreation;
	private BigDecimal bankCountryId;
	private String transferMode;
	private String bankStaus;
	private String bankStatusUser;
	private Date bankStautsDate;
	private String cancelStatus;
	private BigDecimal stopDocumentNo;
	private String webServiceStatus;
	private Date stopDocumentDate;
	private BigDecimal stopDocumentCode;
	private BigDecimal stopDocumentFinanceYear;
	private Date cancelDocumentDate;
	private BigDecimal cancelDocumentCode;
	private BigDecimal cancelDocumentFinanceYear;
	private BigDecimal cancelDocumentNo;
	
	/*REMITTANCE_TRANSACTION_ID    NOT NULL NUMBER         
	APPLICATION_COUNTRY_ID                NUMBER         
	COMPANY_ID                            NUMBER         
	DOCUMENT_FINANCE_YEAR_ID              NUMBER(4)      
	DOCUMENT_FINANCE_YEAR                 NUMBER         
	DOCUMENT_ID                           NUMBER(2)      
	DOCUMENT_CODE                         NUMBER         
	DOCUMENT_NO                           NUMBER(14)     
	BRANCH_ID                             NUMBER         
	DOCUMENT_DATE                         DATE           
	APPLICATION_FINANCE_YEAR              NUMBER(4)      
	APPLICATION_DOCUMENT_NO               NUMBER(14)     
	REMITTANCE_MODE_ID                    NUMBER         
	DELIVERY_MODE_ID                      NUMBER         
	SALE_CURRENCY_ID                      NUMBER         
	SALE_CURRENCY_CODE                    VARCHAR2(4000) 
	SALE_CURRENCY_DESC                    VARCHAR2(4000) 
	SALE_AMOUNT                           NUMBER(22,3)   
	PURCHASE_CURRENCY                     NUMBER         
	PURCHASE_CURRENCY_CODE                VARCHAR2(4000) 
	PURCHASE_CURRENCY_DESC                VARCHAR2(4000) 
	PURCHASE_AMOUNT                       NUMBER(18,3)   
	EXCHANGE_RATE_APPLIED                 NUMBER         
	LOCAL_COMMISION_CURRENCY_ID           NUMBER         
	LOCAL_COMMISION_AMOUNT                NUMBER(18,3)   
	LOCAL_CHARGE_CURRENCY_ID              NUMBER         
	LOCAL_CHARGE_AMOUNT                   NUMBER(18,3)   
	LOCAL_DELIVERY_CURRENCY_ID            NUMBER         
	LOCAL_DELIVERY_AMOUNT                 NUMBER(18,3)   
	LOCAL_NET_CURRENCY_ID                 NUMBER         
	LOCAL_NET_TRANX_AMOUNT                NUMBER(18,3)   
	BANK_ID                               NUMBER         
	BANK_CODE                             VARCHAR2(4000) 
	CORRESPONDING_BANK_NAME               VARCHAR2(100)  
	BANK_BRANCH_ID                        NUMBER(22)     
	BANK_BRANCH_CODE                      NUMBER         
	BRANCH_NAME                           VARCHAR2(100)  
	BENEFICIARY_NAME                      VARCHAR2(200)  
	BENEFICIARY_BANK                      VARCHAR2(200)  
	BENEFICIARY_BRANCH                    VARCHAR2(200)  
	BENEFICIARY_ACCOUNT_NO                VARCHAR2(200)  
	PAYTO                                 VARCHAR2(4000) 
	INSTRUCTION                           CHAR(1)        
	BENEFICIARY_INTER_BANK1               VARCHAR2(300)  
	BENEFICIARY_INTER_BANK2               VARCHAR2(300)  
	BENEFICIARY_SWIFT_BANK1               VARCHAR2(11)   
	BENEFICIARY_SWIFT_BANK2               VARCHAR2(11)   
	BENE_ADDRESS                          VARCHAR2(4000) 
	BENE_TELEPHONE                        VARCHAR2(4000) 
	CUSTOMER_ID                           NUMBER         
	CUSTOMER_REFERENCE                    NUMBER         
	PHONE                                 VARCHAR2(12)   
	SOURCE_OF_INCOME                      NUMBER         
	SERVICE_GROUP_ID                      NUMBER         
	SERVICE_GROUP_CODE                    VARCHAR2(10)   
	SERVICE_GROUP_DESC                    VARCHAR2(40)   
	CREATED_DATE                          DATE           
	TRANSACTION_STATUS                    VARCHAR2(1)    
	FILE_CREATION                         VARCHAR2(1)    
	BANK_COUNTRY_ID                       NUMBER         
	TRANSFER_MODE                         VARCHAR2(20)   
	BANK_STATUS                           VARCHAR2(1)    
	BANK_STATUS_USER                      VARCHAR2(15)   
	BANK_STATUS_DATE                      DATE           
	CANCELLATION_STATUS                   VARCHAR2(1)    
	WEB_SERVICE_STATUS                    VARCHAR2(20)  
	STOP_DOCUMENT_NO                      NUMBER    
	
	STOP_DOCUMENT_DATE                    DATE           
	STOP_DOCUMENT_CODE                    NUMBER         
	STOP_DOCUMENT_FINANCE_YEAR            NUMBER         
	CANCEL_DOCUMENT_DATE                  DATE           
	CANCEL_DOCUMENT_CODE                  NUMBER         
	CANCEL_DOCUMENT_FINANCE_YEAR          NUMBER         
	CANCEL_DOCUMENT_NO                    NUMBER*/
	

	@Id
	@Column(name = "REMITTANCE_TRANSACTION_ID")
	public BigDecimal getRemittanceTransactionId() {
		return remittanceTransactionId;
	}

	public void setRemittanceTransactionId(BigDecimal remittanceTransactionId) {
		this.remittanceTransactionId = remittanceTransactionId;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
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

	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "BRANCH_ID")
	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
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

	@Column(name = "APPLICATION_DOCUMENT_NO")
	public BigDecimal getApplicationDocumentNo() {
		return applicationDocumentNo;
	}

	public void setApplicationDocumentNo(BigDecimal applicationDocumentNo) {
		this.applicationDocumentNo = applicationDocumentNo;
	}

	@Column(name = "REMITTANCE_MODE_ID")
	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}

	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}

	@Column(name = "DELIVERY_MODE_ID")
	public BigDecimal getDeliveryModeId() {
		return deliveryModeId;
	}

	public void setDeliveryModeId(BigDecimal deliveryModeId) {
		this.deliveryModeId = deliveryModeId;
	}

	@Column(name = "SALE_CURRENCY_ID")
	public BigDecimal getSaleCurrencyId() {
		return saleCurrencyId;
	}

	public void setSaleCurrencyId(BigDecimal saleCurrencyId) {
		this.saleCurrencyId = saleCurrencyId;
	}

	@Column(name = "SALE_CURRENCY_CODE")
	public String getSaleCurrencyCode() {
		return saleCurrencyCode;
	}

	public void setSaleCurrencyCode(String saleCurrencyCode) {
		this.saleCurrencyCode = saleCurrencyCode;
	}

	@Column(name = "SALE_CURRENCY_DESC")
	public String getSaleCurrencyDesc() {
		return saleCurrencyDesc;
	}

	public void setSaleCurrencyDesc(String saleCurrencyDesc) {
		this.saleCurrencyDesc = saleCurrencyDesc;
	}

	@Column(name = "SALE_AMOUNT")
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	@Column(name = "PURCHASE_CURRENCY")
	public BigDecimal getPurchaseCurrencyId() {
		return purchaseCurrencyId;
	}

	public void setPurchaseCurrencyId(BigDecimal purchaseCurrencyId) {
		this.purchaseCurrencyId = purchaseCurrencyId;
	}

	@Column(name = "PURCHASE_CURRENCY_CODE")
	public String getPurchaseCurrencyCode() {
		return purchaseCurrencyCode;
	}

	public void setPurchaseCurrencyCode(String purchaseCurrencyCode) {
		this.purchaseCurrencyCode = purchaseCurrencyCode;
	}

	@Column(name = "PURCHASE_CURRENCY_DESC")
	public String getPurchaseCurrencyDesc() {
		return purchaseCurrencyDesc;
	}

	public void setPurchaseCurrencyDesc(String purchaseCurrencyDesc) {
		this.purchaseCurrencyDesc = purchaseCurrencyDesc;
	}

	@Column(name = "PURCHASE_AMOUNT")
	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	@Column(name = "EXCHANGE_RATE_APPLIED")
	public BigDecimal getExchangeRateApplied() {
		return exchangeRateApplied;
	}

	public void setExchangeRateApplied(BigDecimal exchangeRateApplied) {
		this.exchangeRateApplied = exchangeRateApplied;
	}

	@Column(name = "LOCAL_COMMISION_CURRENCY_ID")
	public BigDecimal getLocalCurrnecyCommisionId() {
		return localCurrnecyCommisionId;
	}

	public void setLocalCurrnecyCommisionId(BigDecimal localCurrnecyCommisionId) {
		this.localCurrnecyCommisionId = localCurrnecyCommisionId;
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

	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	@Column(name = "CORRESPONDING_BANK_NAME")
	public String getCorrespondingBankName() {
		return correspondingBankName;
	}

	public void setCorrespondingBankName(String correspondingBankName) {
		this.correspondingBankName = correspondingBankName;
	}

	@Column(name = "BANK_BRANCH_ID")
	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	@Column(name = "BRANCH_NAME")
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Column(name = "BENEFICIARY_NAME")
	public String getBenificiaryName() {
		return benificiaryName;
	}

	public void setBenificiaryName(String benificiaryName) {
		this.benificiaryName = benificiaryName;
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

	@Column(name = "BENEFICIARY_ACCOUNT_NO")
	public String getBenificiaryAccountNo() {
		return benificiaryAccountNo;
	}

	public void setBenificiaryAccountNo(String benificiaryAccountNo) {
		this.benificiaryAccountNo = benificiaryAccountNo;
	}

	@Column(name = "INSTRUCTION")
	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
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

	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "SOURCE_OF_INCOME")
	public String getSourceOfIncome() {
		return sourceOfIncome;
	}

	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
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

	@Column(name = "BENE_ADDRESS")
	public String getBeneficiaryAddress() {
		return beneficiaryAddress;
	}

	public void setBeneficiaryAddress(String beneficiaryAddress) {
		this.beneficiaryAddress = beneficiaryAddress;
	}

	@Column(name = "PAYTO")
	public String getPayTo() {
		return PayTo;
	}

	public void setPayTo(String payTo) {
		PayTo = payTo;
	}

	@Column(name = "BENE_TELEPHONE")
	public String getBeneficiaryTelephone() {
		return beneficiaryTelephone;
	}

	public void setBeneficiaryTelephone(String beneficiaryTelephone) {
		this.beneficiaryTelephone = beneficiaryTelephone;
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

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "TRANSACTION_STATUS")
	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	@Column(name = "FILE_CREATION")
	public String getFileCreation() {
		return fileCreation;
	}

	public void setFileCreation(String fileCreation) {
		this.fileCreation = fileCreation;
	}

	@Column(name = "BANK_COUNTRY_ID")
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}

	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}

	@Column(name = "TRANSFER_MODE")
	public String getTransferMode() {
		return transferMode;
	}

	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
	}

	@Column(name = "BANK_STATUS")
	public String getBankStaus() {
		return bankStaus;
	}

	public void setBankStaus(String bankStaus) {
		this.bankStaus = bankStaus;
	}

	@Column(name = "BANK_STATUS_USER")
	public String getBankStatusUser() {
		return bankStatusUser;
	}

	public void setBankStatusUser(String bankStatusUser) {
		this.bankStatusUser = bankStatusUser;
	}

	@Column(name = "BANK_STATUS_DATE")
	public Date getBankStautsDate() {
		return bankStautsDate;
	}

	public void setBankStautsDate(Date bankStautsDate) {
		this.bankStautsDate = bankStautsDate;
	}

	@Column(name = "CANCELLATION_STATUS")
	public String getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(String cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	@Column(name = "STOP_DOCUMENT_NO")
	public BigDecimal getStopDocumentNo() {
		return stopDocumentNo;
	}

	public void setStopDocumentNo(BigDecimal stopDocumentNo) {
		this.stopDocumentNo = stopDocumentNo;
	}
	
	@Column(name = "WEB_SERVICE_STATUS")
	public String getWebServiceStatus() {
		return webServiceStatus;
	}

	public void setWebServiceStatus(String webServiceStatus) {
		this.webServiceStatus = webServiceStatus;
	}

	@Column(name = "STOP_DOCUMENT_DATE")
	public Date getStopDocumentDate() {
		return stopDocumentDate;
	}

	public void setStopDocumentDate(Date stopDocumentDate) {
		this.stopDocumentDate = stopDocumentDate;
	}

	@Column(name = "STOP_DOCUMENT_CODE")
	public BigDecimal getStopDocumentCode() {
		return stopDocumentCode;
	}

	public void setStopDocumentCode(BigDecimal stopDocumentCode) {
		this.stopDocumentCode = stopDocumentCode;
	}

	@Column(name = "STOP_DOCUMENT_FINANCE_YEAR")
	public BigDecimal getStopDocumentFinanceYear() {
		return stopDocumentFinanceYear;
	}

	public void setStopDocumentFinanceYear(BigDecimal stopDocumentFinanceYear) {
		this.stopDocumentFinanceYear = stopDocumentFinanceYear;
	}

	@Column(name = "CANCEL_DOCUMENT_DATE")
	public Date getCancelDocumentDate() {
		return cancelDocumentDate;
	}

	public void setCancelDocumentDate(Date cancelDocumentDate) {
		this.cancelDocumentDate = cancelDocumentDate;
	}

	@Column(name = "CANCEL_DOCUMENT_CODE")
	public BigDecimal getCancelDocumentCode() {
		return cancelDocumentCode;
	}

	public void setCancelDocumentCode(BigDecimal cancelDocumentCode) {
		this.cancelDocumentCode = cancelDocumentCode;
	}

	@Column(name = "CANCEL_DOCUMENT_FINANCE_YEAR")
	public BigDecimal getCancelDocumentFinanceYear() {
		return cancelDocumentFinanceYear;
	}

	public void setCancelDocumentFinanceYear(BigDecimal cancelDocumentFinanceYear) {
		this.cancelDocumentFinanceYear = cancelDocumentFinanceYear;
	}

	@Column(name = "CANCEL_DOCUMENT_NO")
	public BigDecimal getCancelDocumentNo() {
		return cancelDocumentNo;
	}

	public void setCancelDocumentNo(BigDecimal cancelDocumentNo) {
		this.cancelDocumentNo = cancelDocumentNo;
	}
	
}
