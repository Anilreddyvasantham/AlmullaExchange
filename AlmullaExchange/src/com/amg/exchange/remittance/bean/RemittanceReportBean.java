package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.CollectionDetailView;

public class RemittanceReportBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// for remittance Application
	private String applicationNo;
	private String date;
	private String proposedCurrencyAndAmount;
	private String proposedExchangeRate;
	private String proposedKWDAmount;
	private String proposedCommission;
	private String proposedOtherCharges;
	private String totalKWDAmount;
	private String futherInstructions;
	private String benefPaymentMode;
	private String purposeOfRemittance;
	private String address;
	private String senderName;
	private String contactNumber;
	private String civilId;
	private String idExpiryDate;
	private String incomeSource;
	private String signature;
	private String cashierSignature;
	private String userName;

	// for receipt report
	private BigDecimal customerId;
	private String firstName;
	private String middleName;
	private BigDecimal mobileNo;
	private String insurence1;
	private String insurence2;
	private String location;
	private BigDecimal phoneNumber;
	private String receiptNo;
	private String transactionNo;
	private String beneficiaryName;
	private String benefeciaryBankName;
	private String benefeciaryBranchName;
	private String benefeciaryAccountNumber;
	private String perposeOfRemittance;
	private String paymentChannel;
	private String currencyAndAmount;
	private String exchangeRate;
	private String localTransactionAmount;
	private String commision;
	private String otherCharges;
	private String totalAmount;
	private String sourceOfIncome;
	private String intermediataryBank;
	private BigDecimal amountPaid;
	private BigDecimal amountRefund;
	private BigDecimal netPaidAmount;
	private String paymentMode;
	private String saleAmount;
	private String currencyQuoteName;
	private String beneSwiftBank1;
	private String beneSwiftBank2;
	private String beneSwiftAddr1;
	private String beneSwiftAddr2;
	private String instruction;
	private String loyalityPointExpiring;
	private BigDecimal noOfTransaction;
	private  String pinNo;
	private  String mtcn;
	
	private String senderPointsEarned;
	private String senderNewPointsEarned;
	private String promotionDiscount;
	private String otherPurposeOfTransaction;
	
	private String purchageAmount;

	private String subReport;

	private List<RemittanceReportBean> collectionDetailList;

	private List<CollectionDetailView> collectionViewList;

	//private List<RemittanceCollectionBean> collectionAppList;

	
	
	//for collect mode
	private String collectionMode;
	private String approvalNo;
	private String transactionId;
	private String knetreceiptDateTime;
	private Boolean knetBooleanCheck=false;
	private Boolean drawLine=false;

	private String netAmount;
	private String paidAmount;
	private String refundedAmount;
	private BigDecimal collectAmount;
	private List<PurposeOfRemittanceReportBean> purposeOfRemitTrList;
	
	private String lessLoyaltyEncasement;
	private String amountPayable;
	
	private String senderCountry;
	private String senderState;
	private String senderCity;
	private String senderFirstName;
	private String senderLastName;
	private String senderMobileno;
	private String senderAddress;
	private String senderMessage;
	
	private String questionLabel;
	private String answerLabel;
	private String messageLabel;
	
	private String question;
	private String answer;
	private String message;
	
	//For Company information ADDED BY VISWA --START
	private String engCompanyInfo;
	private String arabicCompanyInfo;
	private boolean sendEmail;
	private String logoPath;
	
	//Added by Rabil
	private String lessAmtbCouponEncasement;
	private String waterImage;

	public String getEngCompanyInfo() {
		return engCompanyInfo;
	}
	public void setEngCompanyInfo(String engCompanyInfo) {
		this.engCompanyInfo = engCompanyInfo;
	}

	public String getArabicCompanyInfo() {
		return arabicCompanyInfo;
	}
	public void setArabicCompanyInfo(String arabicCompanyInfo) {
		this.arabicCompanyInfo = arabicCompanyInfo;
	}	
	
	//For Company information ADDED BY VISWA --END
	
	public Boolean getDrawLine() {
		return drawLine;
	}
	public void setDrawLine(Boolean drawLine) {
		this.drawLine = drawLine;
	}

	public Boolean getKnetBooleanCheck() {
		return knetBooleanCheck;
	}
	public void setKnetBooleanCheck(Boolean knetBooleanCheck) {
		this.knetBooleanCheck = knetBooleanCheck;
	}

	public String getApprovalNo() {
		return approvalNo;
	}
	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getKnetreceiptDateTime() {
		return knetreceiptDateTime;
	}
	public void setKnetreceiptDateTime(String knetreceiptDateTime) {
		this.knetreceiptDateTime = knetreceiptDateTime;
	}

	public String getPurchageAmount() {
		return purchageAmount;
	}
	public void setPurchageAmount(String purchageAmount) {
		this.purchageAmount = purchageAmount;
	}

	public String getCashierSignature() {
		return cashierSignature;
	}
	public void setCashierSignature(String cashierSignature) {
		this.cashierSignature = cashierSignature;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getCollectAmount() {
		return collectAmount;
	}
	public void setCollectAmount(BigDecimal collectAmount) {
		this.collectAmount = collectAmount;
	}

	public String getCollectionMode() {
		return collectionMode;
	}
	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}
	
	public String getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}
	
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getRefundedAmount() {
		return refundedAmount;
	}
	public void setRefundedAmount(String refundedAmount) {
		this.refundedAmount = refundedAmount;
	}

	public String getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(String saleAmount) {
		this.saleAmount = saleAmount;
	}
	
	public String getLoyalityPointExpiring() {
		return loyalityPointExpiring;
	}
	public void setLoyalityPointExpiring(String loyalityPointExpiring) {
		this.loyalityPointExpiring = loyalityPointExpiring;
	}

	public BigDecimal getNoOfTransaction() {
		return noOfTransaction;
	}
	public void setNoOfTransaction(BigDecimal noOfTransaction) {
		this.noOfTransaction = noOfTransaction;
	}

	public String getPinNo() {
		return pinNo;
	}
	public void setPinNo(String pinNo) {
		this.pinNo = pinNo;
	}

	public String getCurrencyQuoteName() {
		return currencyQuoteName;
	}
	public void setCurrencyQuoteName(String currencyQuoteName) {
		this.currencyQuoteName = currencyQuoteName;
	}

	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public String getProposedCurrencyAndAmount() {
		return proposedCurrencyAndAmount;
	}
	public void setProposedCurrencyAndAmount(String proposedCurrencyAndAmount) {
		this.proposedCurrencyAndAmount = proposedCurrencyAndAmount;
	}

	public String getProposedExchangeRate() {
		return proposedExchangeRate;
	}
	public void setProposedExchangeRate(String proposedExchangeRate) {
		this.proposedExchangeRate = proposedExchangeRate;
	}

	public String getProposedKWDAmount() {
		return proposedKWDAmount;
	}
	public void setProposedKWDAmount(String proposedKWDAmount) {
		this.proposedKWDAmount = proposedKWDAmount;
	}

	public String getProposedCommission() {
		return proposedCommission;
	}
	public void setProposedCommission(String proposedCommission) {
		this.proposedCommission = proposedCommission;
	}

	public String getProposedOtherCharges() {
		return proposedOtherCharges;
	}
	public void setProposedOtherCharges(String proposedOtherCharges) {
		this.proposedOtherCharges = proposedOtherCharges;
	}

	public String getTotalKWDAmount() {
		return totalKWDAmount;
	}
	public void setTotalKWDAmount(String totalKWDAmount) {
		this.totalKWDAmount = totalKWDAmount;
	}

	public String getFutherInstructions() {
		return futherInstructions;
	}
	public void setFutherInstructions(String futherInstructions) {
		this.futherInstructions = futherInstructions;
	}
	
	public String getBenefPaymentMode() {
		return benefPaymentMode;
	}
	public void setBenefPaymentMode(String benefPaymentMode) {
		this.benefPaymentMode = benefPaymentMode;
	}

	public String getPurposeOfRemittance() {
		return purposeOfRemittance;
	}
	public void setPurposeOfRemittance(String purposeOfRemittance) {
		this.purposeOfRemittance = purposeOfRemittance;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}

	public String getIdExpiryDate() {
		return idExpiryDate;
	}
	public void setIdExpiryDate(String idExpiryDate) {
		this.idExpiryDate = idExpiryDate;
	}

	public String getIncomeSource() {
		return incomeSource;
	}
	public void setIncomeSource(String incomeSource) {
		this.incomeSource = incomeSource;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public BigDecimal getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(BigDecimal mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getInsurence1() {
		return insurence1;
	}
	public void setInsurence1(String insurence1) {
		this.insurence1 = insurence1;
	}

	public String getInsurence2() {
		return insurence2;
	}
	public void setInsurence2(String insurence2) {
		this.insurence2 = insurence2;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(BigDecimal phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getBenefeciaryBankName() {
		return benefeciaryBankName;
	}
	public void setBenefeciaryBankName(String benefeciaryBankName) {
		this.benefeciaryBankName = benefeciaryBankName;
	}

	public String getBenefeciaryBranchName() {
		return benefeciaryBranchName;
	}
	public void setBenefeciaryBranchName(String benefeciaryBranchName) {
		this.benefeciaryBranchName = benefeciaryBranchName;
	}

	public String getBenefeciaryAccountNumber() {
		return benefeciaryAccountNumber;
	}
	public void setBenefeciaryAccountNumber(String benefeciaryAccountNumber) {
		this.benefeciaryAccountNumber = benefeciaryAccountNumber;
	}

	public String getPerposeOfRemittance() {
		return perposeOfRemittance;
	}
	public void setPerposeOfRemittance(String perposeOfRemittance) {
		this.perposeOfRemittance = perposeOfRemittance;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}
	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public String getCurrencyAndAmount() {
		return currencyAndAmount;
	}
	public void setCurrencyAndAmount(String currencyAndAmount) {
		this.currencyAndAmount = currencyAndAmount;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getLocalTransactionAmount() {
		return localTransactionAmount;
	}
	public void setLocalTransactionAmount(String localTransactionAmount) {
		this.localTransactionAmount = localTransactionAmount;
	}

	public String getCommision() {
		return commision;
	}
	public void setCommision(String commision) {
		this.commision = commision;
	}

	public String getOtherCharges() {
		return otherCharges;
	}
	public void setOtherCharges(String otherCharges) {
		this.otherCharges = otherCharges;
	}

	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getSourceOfIncome() {
		return sourceOfIncome;
	}
	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	public String getIntermediataryBank() {
		return intermediataryBank;
	}
	public void setIntermediataryBank(String intermediataryBank) {
		this.intermediataryBank = intermediataryBank;
	}

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	public BigDecimal getAmountRefund() {
		return amountRefund;
	}
	public void setAmountRefund(BigDecimal amountRefund) {
		this.amountRefund = amountRefund;
	}

	public BigDecimal getNetPaidAmount() {
		return netPaidAmount;
	}
	public void setNetPaidAmount(BigDecimal netPaidAmount) {
		this.netPaidAmount = netPaidAmount;
	}

	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getSubReport() {
		return subReport;
	}
	public void setSubReport(String subReport) {
		this.subReport = subReport;
	}

	public List<RemittanceReportBean> getCollectionDetailList() {
		return collectionDetailList;
	}
	public void setCollectionDetailList(List<RemittanceReportBean> collectionDetailList) {
		this.collectionDetailList = collectionDetailList;
	}

/*	public List<RemittanceCollectionBean> getCollectionAppList() {
		return collectionAppList;
	}

	public void setCollectionAppList(List<RemittanceCollectionBean> collectionAppList) {
		this.collectionAppList = collectionAppList;
	}*/

	public List<CollectionDetailView> getCollectionViewList() {
		return collectionViewList;
	}
	public void setCollectionViewList(List<CollectionDetailView> collectionViewList) {
		this.collectionViewList = collectionViewList;
	}
	
	public String getBeneSwiftBank1() {
		return beneSwiftBank1;
	}
	public void setBeneSwiftBank1(String beneSwiftBank1) {
		this.beneSwiftBank1 = beneSwiftBank1;
	}

	public String getBeneSwiftBank2() {
		return beneSwiftBank2;
	}
	public void setBeneSwiftBank2(String beneSwiftBank2) {
		this.beneSwiftBank2 = beneSwiftBank2;
	}

	public String getBeneSwiftAddr1() {
		return beneSwiftAddr1;
	}
	public void setBeneSwiftAddr1(String beneSwiftAddr1) {
		this.beneSwiftAddr1 = beneSwiftAddr1;
	}

	public String getBeneSwiftAddr2() {
		return beneSwiftAddr2;
	}
	public void setBeneSwiftAddr2(String beneSwiftAddr2) {
		this.beneSwiftAddr2 = beneSwiftAddr2;
	}

	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public List<PurposeOfRemittanceReportBean> getPurposeOfRemitTrList() {
		return purposeOfRemitTrList;
	}
	public void setPurposeOfRemitTrList(List<PurposeOfRemittanceReportBean> purposeOfRemitTrList) {
		this.purposeOfRemitTrList = purposeOfRemitTrList;
	}

	public String getAmountPayable() {
		return amountPayable;
	}
	public void setAmountPayable(String amountPayable) {
		this.amountPayable = amountPayable;
	}

	public String getLessLoyaltyEncasement() {
		return lessLoyaltyEncasement;
	}
	public void setLessLoyaltyEncasement(String lessLoyaltyEncasement) {
		this.lessLoyaltyEncasement = lessLoyaltyEncasement;
	}

	public String getMtcn() {
		return mtcn;
	}
	public void setMtcn(String mtcn) {
		this.mtcn = mtcn;
	}

	public String getSenderPointsEarned() {
		return senderPointsEarned;
	}
	public void setSenderPointsEarned(String senderPointsEarned) {
		this.senderPointsEarned = senderPointsEarned;
	}

	public String getSenderNewPointsEarned() {
		return senderNewPointsEarned;
	}
	public void setSenderNewPointsEarned(String senderNewPointsEarned) {
		this.senderNewPointsEarned = senderNewPointsEarned;
	}

	public String getSenderCountry() {
		return senderCountry;
	}
	public void setSenderCountry(String senderCountry) {
		this.senderCountry = senderCountry;
	}

	public String getSenderState() {
		return senderState;
	}
	public void setSenderState(String senderState) {
		this.senderState = senderState;
	}

	public String getSenderCity() {
		return senderCity;
	}
	public void setSenderCity(String senderCity) {
		this.senderCity = senderCity;
	}

	public String getSenderFirstName() {
		return senderFirstName;
	}
	public void setSenderFirstName(String senderFirstName) {
		this.senderFirstName = senderFirstName;
	}

	public String getSenderLastName() {
		return senderLastName;
	}
	public void setSenderLastName(String senderLastName) {
		this.senderLastName = senderLastName;
	}

	public String getSenderMobileno() {
		return senderMobileno;
	}
	public void setSenderMobileno(String senderMobileno) {
		this.senderMobileno = senderMobileno;
	}

	public String getSenderAddress() {
		return senderAddress;
	}
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getSenderMessage() {
		return senderMessage;
	}
	public void setSenderMessage(String senderMessage) {
		this.senderMessage = senderMessage;
	}

	public String getQuestionLabel() {
		return questionLabel;
	}
	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}

	public String getAnswerLabel() {
		return answerLabel;
	}
	public void setAnswerLabel(String answerLabel) {
		this.answerLabel = answerLabel;
	}

	public String getMessageLabel() {
		return messageLabel;
	}
	public void setMessageLabel(String messageLabel) {
		this.messageLabel = messageLabel;
	}

	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getPromotionDiscount() {
		return promotionDiscount;
	}
	public void setPromotionDiscount(String promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	public String getOtherPurposeOfTransaction() {
		return otherPurposeOfTransaction;
	}
	public void setOtherPurposeOfTransaction(String otherPurposeOfTransaction) {
		this.otherPurposeOfTransaction = otherPurposeOfTransaction;
	}
	
	public String getLessAmtbCouponEncasement() {
		return lessAmtbCouponEncasement;
	}
	public void setLessAmtbCouponEncasement(String lessAmtbCouponEncasement) {
		this.lessAmtbCouponEncasement = lessAmtbCouponEncasement;
	}
	
	public boolean isSendEmail() {
		return sendEmail;
	}
	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}
	
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
	public String getWaterImage() {
		return waterImage;
	}
	public void setWaterImage(String waterImage) {
		this.waterImage = waterImage;
	}
	
	
}
