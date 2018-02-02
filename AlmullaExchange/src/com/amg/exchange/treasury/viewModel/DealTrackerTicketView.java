package com.amg.exchange.treasury.viewModel;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="V_EX_TICKETS")
public class DealTrackerTicketView implements java.io.Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DealTrackerTicketView(){}

	@Id
	@Column(name="SLNO")
	private BigDecimal slNo;
	
	@Column(name="DEALID")
	private String dealId;
	
	
	@Column(name="TIMEOFDEAL")
	private Date timeofDeal;
	
	@Column(name="DEALERNAME")
	private String dealerName;
	
	@Column(name="DEALWITH")
	private String dealWith;
	
	@Column(name="CONCLUDED_BY")
	private String concludedBy;
	
	@Column(name="COMMENTTEXT")
	private String commentText;
	
	@Column(name="REUTER_REFERENCE")
	private String reuterReference;
	
	@Column(name="PD_CURRENCY")
	private String pdCurrency;
	
	@Column(name="SD_CURRENCY")
	private String sdCurrency;
	
	@Column(name="PD_BANK")
	private String pdBank;
		
	@Column(name="SD_BANK")
	private String sdBank;
	
	@Column(name="PD_VALUE_DATE")
	private Date pdValueDate;
	
	@Column(name="SD_VALUE_DATE")
	private Date sdValueDate;
	
	@Column(name="PD_FC_AMT")
	private BigDecimal pdFcAmt;
	
	@Column(name="SALE_AMOUNT")
	private BigDecimal saleAmount;
	
	@Column(name="EXCHANGE_RATE_PD")
	private BigDecimal pdExchangerate;
	
	@Column(name="HVD_YEAR")
	private BigDecimal highValueYear;
	
	@Column(name="HVD_NO")
	private BigDecimal highValueNO;
	
	@Column(name="MULTI_FACTOR")
	private BigDecimal multiFactor;
	
	
	public DealTrackerTicketView(BigDecimal slNo, String dealId,
			Date timeofDeal, String dealerName, String dealWith,
			String concludedBy, String commentText, String reuterReference,
			String pdCurrency, String sdCurrency, String pdBank, String sdBank,
			Date pdValueDate, Date sdValueDate, BigDecimal pdFcAmt,
			BigDecimal saleAmount, BigDecimal pdExchangerate,
			BigDecimal highValueYear, BigDecimal highValueNO,BigDecimal multiFactor) {
		super();
		this.slNo = slNo;
		this.dealId = dealId;
		this.timeofDeal = timeofDeal;
		this.dealerName = dealerName;
		this.dealWith = dealWith;
		this.concludedBy = concludedBy;
		this.commentText = commentText;
		this.reuterReference = reuterReference;
		this.pdCurrency = pdCurrency;
		this.sdCurrency = sdCurrency;
		this.pdBank = pdBank;
		this.sdBank = sdBank;
		this.pdValueDate = pdValueDate;
		this.sdValueDate = sdValueDate;
		this.pdFcAmt = pdFcAmt;
		this.saleAmount = saleAmount;
		this.pdExchangerate = pdExchangerate;
		this.highValueYear = highValueYear;
		this.highValueNO = highValueNO;
		this.multiFactor=multiFactor;
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

	public String getReuterReference() {
		return reuterReference;
	}

	public void setReuterReference(String reuterReference) {
		this.reuterReference = reuterReference;
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

	public BigDecimal getHighValueYear() {
		return highValueYear;
	}

	public void setHighValueYear(BigDecimal highValueYear) {
		this.highValueYear = highValueYear;
	}

	public BigDecimal getHighValueNO() {
		return highValueNO;
	}

	public void setHighValueNO(BigDecimal highValueNO) {
		this.highValueNO = highValueNO;
	}

	public BigDecimal getMultiFactor() {
		return multiFactor;
	}

	public void setMultiFactor(BigDecimal multiFactor) {
		this.multiFactor = multiFactor;
	}
	
}
