package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class HighValueDealRequestEnquiryDataTable {
	
	private BigDecimal requestNumber;
	private String beneficieryBankName;
	private BigDecimal fcAmount;
	private BigDecimal sellRate;
	private String customerName;
	private Date valueDate;
	
	
	
	
	
	
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	public BigDecimal getRequestNumber() {
		return requestNumber;
	}
	public String getBeneficieryBankName() {
		return beneficieryBankName;
	}
	public BigDecimal getFcAmount() {
		return fcAmount;
	}
	public BigDecimal getSellRate() {
		return sellRate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setRequestNumber(BigDecimal requestNumber) {
		this.requestNumber = requestNumber;
	}
	public void setBeneficieryBankName(String beneficieryBankName) {
		this.beneficieryBankName = beneficieryBankName;
	}
	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}
	public void setSellRate(BigDecimal sellRate) {
		this.sellRate = sellRate;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
	

}
