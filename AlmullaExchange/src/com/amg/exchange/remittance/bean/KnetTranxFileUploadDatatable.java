package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class KnetTranxFileUploadDatatable implements Serializable{
	
	
	private BigDecimal customerId = null;
	private String customerReference = null;
	private String trnxDate = null;
	private String cardNumber = null;
	private String merchantTranckId = null;
	private String customerName = null;
	private String approvalNumber;
	private BigDecimal amount;
	private String status = null;
	private String errorMessage = null;
	private BigDecimal authorizationCode = null;
	private String resultCode = null;
	private String referenceId = null;
	private String transactionId = null;
	private String orderId = null;
	private String userField1;
	private String userField2;
	private String userField3;
	private String userField4;
	private String userField5;
	private BigDecimal srNo;
	private String alreadyUploaded;
	private String cusRefNotExist;
	private String customerCardNotExist;
	private String trnxStatus;
	private BigDecimal finYear;
	private String toEmailId;  
	private BigDecimal companyId;
	private BigDecimal collDocCode;
	private BigDecimal collDocFyr;
	private BigDecimal collDocNo;
	private String pIndicator;
	private String textColor=null;
	

	
	
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public String getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}
	public String getTrnxDate() {
		return trnxDate;
	}
	public void setTrnxDate(String trnxDate) {
		this.trnxDate = trnxDate;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getMerchantTranckId() {
		return merchantTranckId;
	}
	public void setMerchantTranckId(String merchantTranckId) {
		this.merchantTranckId = merchantTranckId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getApprovalNumber() {
		return approvalNumber;
	}
	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public BigDecimal getAuthorizationCode() {
		return authorizationCode;
	}
	public void setAuthorizationCode(BigDecimal authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUserField1() {
		return userField1;
	}
	public void setUserField1(String userField1) {
		this.userField1 = userField1;
	}
	public String getUserField2() {
		return userField2;
	}
	public void setUserField2(String userField2) {
		this.userField2 = userField2;
	}
	public String getUserField3() {
		return userField3;
	}
	public void setUserField3(String userField3) {
		this.userField3 = userField3;
	}
	public String getUserField4() {
		return userField4;
	}
	public void setUserField4(String userField4) {
		this.userField4 = userField4;
	}
	public String getUserField5() {
		return userField5;
	}
	public void setUserField5(String userField5) {
		this.userField5 = userField5;
	}
	public BigDecimal getSrNo() {
		return srNo;
	}
	public void setSrNo(BigDecimal srNo) {
		this.srNo = srNo;
	}
	public String getAlreadyUploaded() {
		return alreadyUploaded;
	}
	public void setAlreadyUploaded(String alreadyUploaded) {
		this.alreadyUploaded = alreadyUploaded;
	}
	public String getCusRefNotExist() {
		return cusRefNotExist;
	}
	public void setCusRefNotExist(String cusRefNotExist) {
		this.cusRefNotExist = cusRefNotExist;
	}
	public String getCustomerCardNotExist() {
		return customerCardNotExist;
	}
	public void setCustomerCardNotExist(String customerCardNotExist) {
		this.customerCardNotExist = customerCardNotExist;
	}
	public String getTrnxStatus() {
		return trnxStatus;
	}
	public void setTrnxStatus(String trnxStatus) {
		this.trnxStatus = trnxStatus;
	}
	public BigDecimal getFinYear() {
		return finYear;
	}
	public void setFinYear(BigDecimal finYear) {
		this.finYear = finYear;
	}
	public String getToEmailId() {
		return toEmailId;
	}
	public void setToEmailId(String toEmailId) {
		this.toEmailId = toEmailId;
	}
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	public BigDecimal getCollDocCode() {
		return collDocCode;
	}
	public void setCollDocCode(BigDecimal collDocCode) {
		this.collDocCode = collDocCode;
	}
	public BigDecimal getCollDocFyr() {
		return collDocFyr;
	}
	public void setCollDocFyr(BigDecimal collDocFyr) {
		this.collDocFyr = collDocFyr;
	}
	public BigDecimal getCollDocNo() {
		return collDocNo;
	}
	public void setCollDocNo(BigDecimal collDocNo) {
		this.collDocNo = collDocNo;
	}
	public String getpIndicator() {
		return pIndicator;
	}
	public void setpIndicator(String pIndicator) {
		this.pIndicator = pIndicator;
	}
	public String getTextColor() {
		return textColor;
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	
	
	
	
	
	
}
