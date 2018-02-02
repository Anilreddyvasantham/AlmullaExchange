package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class DealTrackerInfoDTBean implements java.io.Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal slNo;
	
	private String dealId;
		
	private Date timeofDeal;
		
	private String dealerName;
	
	private String dealWith;
	
	private String concludedBy;
	
	private String commentText;
	
	private String returnReference;
	
	private String pdCurrency;
	
	private String sdCurrency;
	
	private String pdBank;
	
	private String sdBank;
	
	private Date pdValueDate;
	
	private Date sdValueDate;
	
	private BigDecimal pdFcAmt;
	
	private BigDecimal saleAmount;

	private BigDecimal pdExchangerate;
	
	private String pdBankName;
	
	
	
	public String getPdBankName() {
		return pdBankName;
	}

	public void setPdBankName(String pdBankName) {
		this.pdBankName = pdBankName;
	}

	public BigDecimal getSlNo() {
		return slNo;
	}

	public void setSlNo(BigDecimal slNo) {
		this.slNo = slNo;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public Date getTimeofDeal() {
		return timeofDeal;
	}

	public void setTimeofDeal(Date timeofDeal) {
		this.timeofDeal = timeofDeal;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealWith() {
		return dealWith;
	}

	public void setDealWith(String dealWith) {
		this.dealWith = dealWith;
	}

	public String getConcludedBy() {
		return concludedBy;
	}

	public void setConcludedBy(String concludedBy) {
		this.concludedBy = concludedBy;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public String getReturnReference() {
		return returnReference;
	}

	public void setReturnReference(String returnReference) {
		this.returnReference = returnReference;
	}

	public String getPdCurrency() {
		return pdCurrency;
	}

	public void setPdCurrency(String pdCurrency) {
		this.pdCurrency = pdCurrency;
	}

	public String getSdCurrency() {
		return sdCurrency;
	}

	public void setSdCurrency(String sdCurrency) {
		this.sdCurrency = sdCurrency;
	}

	public String getPdBank() {
		return pdBank;
	}

	public void setPdBank(String pdBank) {
		this.pdBank = pdBank;
	}

	public String getSdBank() {
		return sdBank;
	}

	public void setSdBank(String sdBank) {
		this.sdBank = sdBank;
	}

	public Date getPdValueDate() {
		return pdValueDate;
	}

	public void setPdValueDate(Date pdValueDate) {
		this.pdValueDate = pdValueDate;
	}

	public Date getSdValueDate() {
		return sdValueDate;
	}

	public void setSdValueDate(Date sdValueDate) {
		this.sdValueDate = sdValueDate;
	}

	public BigDecimal getPdFcAmt() {
		return pdFcAmt;
	}

	public void setPdFcAmt(BigDecimal pdFcAmt) {
		this.pdFcAmt = pdFcAmt;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public BigDecimal getPdExchangerate() {
		return pdExchangerate;
	}

	public void setPdExchangerate(BigDecimal pdExchangerate) {
		this.pdExchangerate = pdExchangerate;
	}

	
}
