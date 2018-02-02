package com.amg.exchange.enquiry.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class ReceiptEnquiryDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal documentFinancialYear;
	private BigDecimal documentNo;
	private BigDecimal customerRef;
	private String customerName;
	private String tranctionType;
	private String coresponingBank;
	private String beneficiaryBAnk;
	private BigDecimal fcAmount;
	private BigDecimal localAmount;
	private Date tranctionDate;
	private String status;
	private BigDecimal remitDocNo;
	private BigDecimal receiptNo;
	private String beneficiaryName;
	private String beneficiaryAccountNumber;
	private String paymentBy;
	private String checkBank;
	private String chequeNo;
	private String chequeDate;
	private String approval;
	private BigDecimal voucherYear;
	private BigDecimal voucherNo;
	private BigDecimal amount;
	private BigDecimal specialDealYear;
	private BigDecimal specialDealNo;
	
	public BigDecimal getSpecialDealYear() {
		return specialDealYear;
	}
	public void setSpecialDealYear(BigDecimal specialDealYear) {
		this.specialDealYear = specialDealYear;
	}
	
	public BigDecimal getSpecialDealNo() {
		return specialDealNo;
	}
	public void setSpecialDealNo(BigDecimal specialDealNo) {
		this.specialDealNo = specialDealNo;
	}

	public String getPaymentBy() {
		return paymentBy;
	}
	public void setPaymentBy(String paymentBy) {
		this.paymentBy = paymentBy;
	}
	
	public String getCheckBank() {
		return checkBank;
	}
	public void setCheckBank(String checkBank) {
		this.checkBank = checkBank;
	}
	
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	
	public String getChequeDate() {
		return chequeDate;
	}
	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}
	
	public String getApproval() {
		return approval;
	}
	public void setApproval(String approval) {
		this.approval = approval;
	}
	
	public BigDecimal getVoucherYear() {
		return voucherYear;
	}
	public void setVoucherYear(BigDecimal voucherYear) {
		this.voucherYear = voucherYear;
	}
	
	public BigDecimal getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(BigDecimal voucherNo) {
		this.voucherNo = voucherNo;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getDocumentFinancialYear() {
		return documentFinancialYear;
	}
	public void setDocumentFinancialYear(BigDecimal documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}
	
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	public BigDecimal getCustomerRef() {
		return customerRef;
	}
	public void setCustomerRef(BigDecimal customerRef) {
		this.customerRef = customerRef;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getTranctionType() {
		return tranctionType;
	}
	public void setTranctionType(String tranctionType) {
		this.tranctionType = tranctionType;
	}
	
	public String getCoresponingBank() {
		return coresponingBank;
	}
	public void setCoresponingBank(String coresponingBank) {
		this.coresponingBank = coresponingBank;
	}
	
	public String getBeneficiaryBAnk() {
		return beneficiaryBAnk;
	}
	public void setBeneficiaryBAnk(String beneficiaryBAnk) {
		this.beneficiaryBAnk = beneficiaryBAnk;
	}
	
	public BigDecimal getFcAmount() {
		return fcAmount;
	}
	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}
	
	public BigDecimal getLocalAmount() {
		return localAmount;
	}
	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}
	
	public Date getTranctionDate() {
		return tranctionDate;
	}
	public void setTranctionDate(Date tranctionDate) {
		this.tranctionDate = tranctionDate;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public BigDecimal getRemitDocNo() {
		return remitDocNo;
	}
	public void setRemitDocNo(BigDecimal remitDocNo) {
		this.remitDocNo = remitDocNo;
	}
	
	public BigDecimal getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(BigDecimal receiptNo) {
		this.receiptNo = receiptNo;
	}
	
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	
	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}
	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}
	
	

}
