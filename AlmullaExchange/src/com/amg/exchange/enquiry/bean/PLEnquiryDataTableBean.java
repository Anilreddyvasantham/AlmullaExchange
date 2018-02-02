package com.amg.exchange.enquiry.bean;

import java.math.BigDecimal;

public class PLEnquiryDataTableBean {
	
	
	    private BigDecimal currencyId;
	    private String currencyName;
	    private BigDecimal bankId;
	    private String bankName;
	    private String bankCode;
	    private String glNo;
	    private  String bankPosition;
	    private  String currencyLimit;
	    private BigDecimal totalExposure;
	    private  String amountLocal;
	    private   String hvTrx;
	    
	    private String eftBalance;
	    private String ttBalance;
	    private String cashBalance;
	    private String fundBalance;
	    
	    private  String totalBankPosition;
	    
		public BigDecimal getCurrencyId() {
			return currencyId;
		}
		public void setCurrencyId(BigDecimal currencyId) {
			this.currencyId = currencyId;
		}
		public String getCurrencyName() {
			return currencyName;
		}
		public void setCurrencyName(String currencyName) {
			this.currencyName = currencyName;
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
		public String getGlNo() {
			return glNo;
		}
		public void setGlNo(String glNo) {
			this.glNo = glNo;
		}
		public String getBankPosition() {
			return bankPosition;
		}
		public void setBankPosition(String bankPosition) {
			this.bankPosition = bankPosition;
		}
		public String getCurrencyLimit() {
			return currencyLimit;
		}
		public void setCurrencyLimit(String currencyLimit) {
			this.currencyLimit = currencyLimit;
		}
		public BigDecimal getTotalExposure() {
			return totalExposure;
		}
		public void setTotalExposure(BigDecimal totalExposure) {
			this.totalExposure = totalExposure;
		}
		public String getAmountLocal() {
			return amountLocal;
		}
		public void setAmountLocal(String amountLocal) {
			this.amountLocal = amountLocal;
		}
		public  String getHvTrx() {
			return hvTrx;
		}
		public void setHvTrx(String hvTrx) {
			this.hvTrx = hvTrx;
		}
		public String getBankCode() {
			return bankCode;
		}
		public void setBankCode(String bankCode) {
			this.bankCode = bankCode;
		}
		public String getEftBalance() {
			return eftBalance;
		}
		public void setEftBalance(String eftBalance) {
			this.eftBalance = eftBalance;
		}
		public String getTtBalance() {
			return ttBalance;
		}
		public void setTtBalance(String ttBalance) {
			this.ttBalance = ttBalance;
		}
		public String getCashBalance() {
			return cashBalance;
		}
		public void setCashBalance(String cashBalance) {
			this.cashBalance = cashBalance;
		}
		public String getFundBalance() {
			return fundBalance;
		}
		public void setFundBalance(String fundBalance) {
			this.fundBalance = fundBalance;
		}
		public String getTotalBankPosition() {
			return totalBankPosition;
		}
		public void setTotalBankPosition(String totalBankPosition) {
			this.totalBankPosition = totalBankPosition;
		}
		
}
