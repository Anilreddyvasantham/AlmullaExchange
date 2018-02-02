package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class KnetSuccessPageCustomerDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal customerId;
	private String customerName;
	private BigDecimal receiptYear;
	private BigDecimal helpdeskNo;
	private String receiptDate;
	
	public BigDecimal getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public BigDecimal getReceiptYear() {
		return receiptYear;
	}
	
	public void setReceiptYear(BigDecimal receiptYear) {
		this.receiptYear = receiptYear;
	}
	
	public String getReceiptDate() {
		return receiptDate;
	}
	
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public BigDecimal getHelpdeskNo() {
		return helpdeskNo;
	}

	public void setHelpdeskNo(BigDecimal helpdeskNo) {
		this.helpdeskNo = helpdeskNo;
	}
	
	

}
