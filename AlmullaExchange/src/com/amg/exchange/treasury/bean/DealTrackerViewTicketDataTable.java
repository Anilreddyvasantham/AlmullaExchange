package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.amg.exchange.treasury.model.BankAccountDetails;

public class DealTrackerViewTicketDataTable implements java.io.Serializable{

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
	private String reuterReference;
	private String pdBankCode;
	private String sdBankCode;
	private String pdBankName;
	private String sdBankName;
	private Date pdValueDate;
	private Date sdValueDate;
	private String pdCurrencyCode;
	private String sdCurrencyCode;
	private String pdCurrencyName;
	private String sdCurrencyName;
	private BigDecimal pdFCAmount;
	private BigDecimal saleAmount;
	private BigDecimal pdExchangerate;
	private BigDecimal highValueYear;
	private BigDecimal highValueNO;
	private String highValueComb;
	private BigDecimal eftval;
	private BigDecimal ttVal;
	private BigDecimal cashVal;
	private Boolean disableCol;
	private Boolean selectCheck;
	private Boolean booEnableselectCheck;
	
	private BigDecimal accountDetId;
	private List<BankAccountDetails> lstBankAccountDetails;
	private BigDecimal pdAccountDetId;
	private String pdFundGlNo;
	private String sdFundGlNo;
	
	private BigDecimal pdStandardId;
	
	private BigDecimal sdStandardId;
	
	private String pdFCAmountForDisplay;
	
	private String pdAMXBankCode;
	private String sdAMXBankCode;
	private BigDecimal highValueAmount;
	private String highValueAmountForDisplay;
	private BigDecimal customerReference;
	private BigDecimal specialDealCustRequestId;
	private BigDecimal multiFactor;
	private BigDecimal sdCurrencyId;
	private BigDecimal pdCurrencyId;
	private String pdValueDateForDisplay;
	
	public BigDecimal getAccountDetId() {
		return accountDetId;
	}
	public void setAccountDetId(BigDecimal accountDetId) {
		this.accountDetId = accountDetId;
	}
	public List<BankAccountDetails> getLstBankAccountDetails() {
		return lstBankAccountDetails;
	}
	public void setLstBankAccountDetails(
			List<BankAccountDetails> lstBankAccountDetails) {
		this.lstBankAccountDetails = lstBankAccountDetails;
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
	
	public String getPdBankCode() {
		return pdBankCode;
	}
	public void setPdBankCode(String pdBankCode) {
		this.pdBankCode = pdBankCode;
	}
	
	public String getSdBankCode() {
		return sdBankCode;
	}
	public void setSdBankCode(String sdBankCode) {
		this.sdBankCode = sdBankCode;
	}
	
	public String getPdBankName() {
		return pdBankName;
	}
	public void setPdBankName(String pdBankName) {
		this.pdBankName = pdBankName;
	}
	
	public String getSdBankName() {
		return sdBankName;
	}
	public void setSdBankName(String sdBankName) {
		this.sdBankName = sdBankName;
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
	
	public String getPdCurrencyCode() {
		return pdCurrencyCode;
	}
	public void setPdCurrencyCode(String pdCurrencyCode) {
		this.pdCurrencyCode = pdCurrencyCode;
	}
	
	public String getSdCurrencyCode() {
		return sdCurrencyCode;
	}
	public void setSdCurrencyCode(String sdCurrencyCode) {
		this.sdCurrencyCode = sdCurrencyCode;
	}
	
	public String getPdCurrencyName() {
		return pdCurrencyName;
	}
	public void setPdCurrencyName(String pdCurrencyName) {
		this.pdCurrencyName = pdCurrencyName;
	}
	
	public String getSdCurrencyName() {
		return sdCurrencyName;
	}
	public void setSdCurrencyName(String sdCurrencyName) {
		this.sdCurrencyName = sdCurrencyName;
	}
	
	public BigDecimal getPdFCAmount() {
		return pdFCAmount;
	}
	public void setPdFCAmount(BigDecimal pdFCAmount) {
		this.pdFCAmount = pdFCAmount;
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
	
	public BigDecimal getEftval() {
		return eftval;
	}
	public void setEftval(BigDecimal eftval) {
		this.eftval = eftval;
	}
	
	public BigDecimal getTtVal() {
		return ttVal;
	}
	public void setTtVal(BigDecimal ttVal) {
		this.ttVal = ttVal;
	}
	
	public BigDecimal getCashVal() {
		return cashVal;
	}
	public void setCashVal(BigDecimal cashVal) {
		this.cashVal = cashVal;
	}
	
	public Boolean getDisableCol() {
		return disableCol;
	}
	public void setDisableCol(Boolean disableCol) {
		this.disableCol = disableCol;
	}
	
	public Boolean getSelectCheck() {
		return selectCheck;
	}
	public void setSelectCheck(Boolean selectCheck) {
		this.selectCheck = selectCheck;
	}
	
	public String getHighValueComb() {
		return highValueComb;
	}
	public void setHighValueComb(String highValueComb) {
		this.highValueComb = highValueComb;
	}
	
	public Boolean getBooEnableselectCheck() {
		return booEnableselectCheck;
	}
	public void setBooEnableselectCheck(Boolean booEnableselectCheck) {
		this.booEnableselectCheck = booEnableselectCheck;
	}
	public BigDecimal getPdAccountDetId() {
		return pdAccountDetId;
	}
	public void setPdAccountDetId(BigDecimal pdAccountDetId) {
		this.pdAccountDetId = pdAccountDetId;
	}
	public String getPdFundGlNo() {
		return pdFundGlNo;
	}
	public void setPdFundGlNo(String pdFundGlNo) {
		this.pdFundGlNo = pdFundGlNo;
	}
	public String getSdFundGlNo() {
		return sdFundGlNo;
	}
	public void setSdFundGlNo(String sdFundGlNo) {
		this.sdFundGlNo = sdFundGlNo;
	}
	public BigDecimal getPdStandardId() {
		return pdStandardId;
	}
	public void setPdStandardId(BigDecimal pdStandardId) {
		this.pdStandardId = pdStandardId;
	}
	public BigDecimal getSdStandardId() {
		return sdStandardId;
	}
	public void setSdStandardId(BigDecimal sdStandardId) {
		this.sdStandardId = sdStandardId;
	}
	public String getPdFCAmountForDisplay() {
		return pdFCAmountForDisplay;
	}
	public void setPdFCAmountForDisplay(String pdFCAmountForDisplay) {
		this.pdFCAmountForDisplay = pdFCAmountForDisplay;
	}
	public String getPdAMXBankCode() {
		return pdAMXBankCode;
	}
	public void setPdAMXBankCode(String pdAMXBankCode) {
		this.pdAMXBankCode = pdAMXBankCode;
	}
	public String getSdAMXBankCode() {
		return sdAMXBankCode;
	}
	public void setSdAMXBankCode(String sdAMXBankCode) {
		this.sdAMXBankCode = sdAMXBankCode;
	}
	public BigDecimal getHighValueAmount() {
		return highValueAmount;
	}
	public void setHighValueAmount(BigDecimal highValueAmount) {
		this.highValueAmount = highValueAmount;
	}
	public String getHighValueAmountForDisplay() {
		return highValueAmountForDisplay;
	}
	public void setHighValueAmountForDisplay(String highValueAmountForDisplay) {
		this.highValueAmountForDisplay = highValueAmountForDisplay;
	}
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	public BigDecimal getSpecialDealCustRequestId() {
		return specialDealCustRequestId;
	}
	public void setSpecialDealCustRequestId(BigDecimal specialDealCustRequestId) {
		this.specialDealCustRequestId = specialDealCustRequestId;
	}
	public BigDecimal getMultiFactor() {
		return multiFactor;
	}
	public void setMultiFactor(BigDecimal multiFactor) {
		this.multiFactor = multiFactor;
	}
	public BigDecimal getSdCurrencyId() {
		return sdCurrencyId;
	}
	public void setSdCurrencyId(BigDecimal sdCurrencyId) {
		this.sdCurrencyId = sdCurrencyId;
	}
	public BigDecimal getPdCurrencyId() {
		return pdCurrencyId;
	}
	public void setPdCurrencyId(BigDecimal pdCurrencyId) {
		this.pdCurrencyId = pdCurrencyId;
	}
	public String getPdValueDateForDisplay() {
		return pdValueDateForDisplay;
	}
	public void setPdValueDateForDisplay(String pdValueDateForDisplay) {
		this.pdValueDateForDisplay = pdValueDateForDisplay;
	}
	
	

	
}
