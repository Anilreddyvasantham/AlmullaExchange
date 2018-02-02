package com.amg.exchange.remittance.bean;
import java.io.Serializable;
import java.math.BigDecimal;


public class KnetSuccessPageKnetTransactionDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal transactionYear;
	private BigDecimal transactionDocNo;
	private String beneficiaryName;
	private String accountNo;
	private BigDecimal pinNo;
	private String bankName;
	private String branchName;
	private String paymentChannel;
	private BigDecimal fcamount;
	private BigDecimal commission;
	private BigDecimal rate;
	private BigDecimal localamount;
	private BigDecimal netamount;
	
	public BigDecimal getTransactionYear() {
		return transactionYear;
	}
	public void setTransactionYear(BigDecimal transactionYear) {
		this.transactionYear = transactionYear;
	}
	
	public BigDecimal getTransactionDocNo() {
		return transactionDocNo;
	}
	public void setTransactionDocNo(BigDecimal transactionDocNo) {
		this.transactionDocNo = transactionDocNo;
	}
	
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public BigDecimal getPinNo() {
		return pinNo;
	}
	public void setPinNo(BigDecimal pinNo) {
		this.pinNo = pinNo;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getPaymentChannel() {
		return paymentChannel;
	}
	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	
	public BigDecimal getFcamount() {
		return fcamount;
	}
	public void setFcamount(BigDecimal fcamount) {
		this.fcamount = fcamount;
	}
	
	public BigDecimal getCommission() {
		return commission;
	}
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
	
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	public BigDecimal getLocalamount() {
		return localamount;
	}
	public void setLocalamount(BigDecimal localamount) {
		this.localamount = localamount;
	}
	
	public BigDecimal getNetamount() {
		return netamount;
	}
	public void setNetamount(BigDecimal netamount) {
		this.netamount = netamount;
	}
	
	
	
	
}
