package com.amg.exchange.online.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RatePlaceOrderDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal rateOfferedPk;
	private String chequeReference;
	private Date chequeDate;
	private BigDecimal chequeBankCode;
	private BigDecimal customerId;
	private BigDecimal customerReference;
	private BigDecimal tranctionAmount;
	private String emailId;
	
	public BigDecimal getRateOfferedPk() {
		return rateOfferedPk;
	}
	public void setRateOfferedPk(BigDecimal rateOfferedPk) {
		this.rateOfferedPk = rateOfferedPk;
	}
	public String getChequeReference() {
		return chequeReference;
	}
	public void setChequeReference(String chequeReference) {
		this.chequeReference = chequeReference;
	}
	public Date getChequeDate() {
		return chequeDate;
	}
	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}
	public BigDecimal getChequeBankCode() {
		return chequeBankCode;
	}
	public void setChequeBankCode(BigDecimal chequeBankCode) {
		this.chequeBankCode = chequeBankCode;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	public BigDecimal getTranctionAmount() {
		return tranctionAmount;
	}
	public void setTranctionAmount(BigDecimal tranctionAmount) {
		this.tranctionAmount = tranctionAmount;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	

}
