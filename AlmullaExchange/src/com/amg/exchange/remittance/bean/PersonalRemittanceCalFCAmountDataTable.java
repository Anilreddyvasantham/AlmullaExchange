package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PersonalRemittanceCalFCAmountDataTable implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal bankId;
	private String bankName;
	private BigDecimal serviceRequestId;
	private String customerName;
	private BigDecimal rate;
	private BigDecimal amount;
	private Boolean select = false;
	private Date valideUpto;
	private BigDecimal specialCustomerPrimaryKey;
	private BigDecimal utilizedAmount;
	private BigDecimal currencyId;
	private BigDecimal splcompanyID;
	private BigDecimal spldocumentID;
	private BigDecimal splfinyearID;
	private BigDecimal spldocnumID;
	private BigDecimal percentageAddedAmount;
	private BigDecimal percentageParameterAmount;
	private String percentageParameterErrorMsg;
	private boolean booRenderInputFCAmount;
	
	
	public BigDecimal getSplcompanyID() {
		return splcompanyID;
	}
	public void setSplcompanyID(BigDecimal splcompanyID) {
		this.splcompanyID = splcompanyID;
	}
	
	public BigDecimal getSpldocumentID() {
		return spldocumentID;
	}
	public void setSpldocumentID(BigDecimal spldocumentID) {
		this.spldocumentID = spldocumentID;
	}
	
	public BigDecimal getSplfinyearID() {
		return splfinyearID;
	}
	public void setSplfinyearID(BigDecimal splfinyearID) {
		this.splfinyearID = splfinyearID;
	}
	
	public BigDecimal getSpldocnumID() {
		return spldocnumID;
	}
	public void setSpldocnumID(BigDecimal spldocnumID) {
		this.spldocnumID = spldocnumID;
	}
	
	public BigDecimal getUtilizedAmount() {
		return utilizedAmount;
	}
	public void setUtilizedAmount(BigDecimal utilizedAmount) {
		this.utilizedAmount = utilizedAmount;
	}
	
	public BigDecimal getSpecialCustomerPrimaryKey() {
		return specialCustomerPrimaryKey;
	}
	public void setSpecialCustomerPrimaryKey(BigDecimal specialCustomerPrimaryKey) {
		this.specialCustomerPrimaryKey = specialCustomerPrimaryKey;
	}
	
	public Date getValideUpto() {
		return valideUpto;
	}
	public void setValideUpto(Date valideUpto) {
		this.valideUpto = valideUpto;
	}
	
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public BigDecimal getServiceRequestId() {
		return serviceRequestId;
	}
	public void setServiceRequestId(BigDecimal serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}

	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public Boolean getSelect() {
		return select;
	}
	public void setSelect(Boolean select) {
		this.select = select;
	}
	
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	
	public BigDecimal getPercentageAddedAmount() {
		return percentageAddedAmount;
	}
	public void setPercentageAddedAmount(BigDecimal percentageAddedAmount) {
		this.percentageAddedAmount = percentageAddedAmount;
	}
	
	public BigDecimal getPercentageParameterAmount() {
		return percentageParameterAmount;
	}
	public void setPercentageParameterAmount(BigDecimal percentageParameterAmount) {
		this.percentageParameterAmount = percentageParameterAmount;
	}
	
	public String getPercentageParameterErrorMsg() {
		return percentageParameterErrorMsg;
	}
	public void setPercentageParameterErrorMsg(String percentageParameterErrorMsg) {
		this.percentageParameterErrorMsg = percentageParameterErrorMsg;
	}
	
	public boolean isBooRenderInputFCAmount() {
		return booRenderInputFCAmount;
	}
	public void setBooRenderInputFCAmount(boolean booRenderInputFCAmount) {
		this.booRenderInputFCAmount = booRenderInputFCAmount;
	}

}
