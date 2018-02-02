package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_CUST_DBCARD_INFO")
public class CustomerDBCardDetailsView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BigDecimal customerBankId;
	public String bankCode;
	public BigDecimal bankId;
	public BigDecimal customerId;
	public BigDecimal customerReference;
	public String debitHideCard;
	public String debitFullCard;
	public String bankDescription;
	
	@Id
	@Column(name="CUSTOMER_BANK_ID")
	public BigDecimal getCustomerBankId() {
		return customerBankId;
	}
	
	public void setCustomerBankId(BigDecimal customerBankId) {
		this.customerBankId = customerBankId;
	}
	
	@Column(name="BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name="BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	@Column(name="CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	@Column(name="CUSTOMER_REFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	
	@Column(name="DEBIT_CARD")
	public String getDebitHideCard() {
		return debitHideCard;
	}
	
	public void setDebitHideCard(String debitHideCard) {
		this.debitHideCard = debitHideCard;
	}
	
	@Column(name="FULL_DEBIT_CARD")
	public String getDebitFullCard() {
		return debitFullCard;
	}
	
	public void setDebitFullCard(String debitFullCard) {
		this.debitFullCard = debitFullCard;
	}

	@Column(name="BANK_DESCRIPTION")
	public String getBankDescription() {
		return bankDescription;
	}

	public void setBankDescription(String bankDescription) {
		this.bankDescription = bankDescription;
	}
	
	

}
