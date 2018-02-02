package com.amg.exchange.enquiry.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ConsiladateCountryWiseFundReqDataTable {

	  private Date projectionDate;
	  private BigDecimal exchangeCountry;
	  private BigDecimal bankCountry;
	  private BigDecimal bankId;
	  private BigDecimal currencyId;
	  private BigDecimal fundReqEFT;
	  private BigDecimal fundReqTT;
	  private BigDecimal fundReqCash;
	  private BigDecimal dealsDoneEFT;
	  private BigDecimal dealsDoneTT;
	  private BigDecimal dealsDoneCash;
	  private BigDecimal diffEFT;
	  private BigDecimal diffTT;
	  private BigDecimal diffCash;
	  private String exchangeCountryName;
	  private String bankCountryName;
	  private String bankName;
	  private String currencyName;
	  private Boolean booBank=false;
	  private Boolean booTT=false;
	  private String ttBankName;
	  private BigDecimal fcAmount;
	  private BigDecimal totalFcAmount;
	  private BigDecimal trasureDealHeaderId;
	  
	  private String fundReqEFTDisplay;
	  private String fundReqTTDisplay;
	  private String fundReqCashDisplay;
	  private String dealsDoneEFTDisplay;
	  private String dealsDoneTTDisplay;
	  private String dealsDoneCashDisplay;
	  private String diffEFTDisplay;
	  private String diffTTDisplay;
	  private String diffCashDisplay;
	  private String fcAmountDisplay;
	  
	  public Date getProjectionDate() {
	  	  return projectionDate;
	  }
	  public void setProjectionDate(Date projectionDate) {
	  	  this.projectionDate = projectionDate;
	  }
	  public BigDecimal getExchangeCountry() {
	  	  return exchangeCountry;
	  }
	  public void setExchangeCountry(BigDecimal exchangeCountry) {
	  	  this.exchangeCountry = exchangeCountry;
	  }
	  public BigDecimal getBankCountry() {
	  	  return bankCountry;
	  }
	  public void setBankCountry(BigDecimal bankCountry) {
	  	  this.bankCountry = bankCountry;
	  }
	  public BigDecimal getBankId() {
	  	  return bankId;
	  }
	  public void setBankId(BigDecimal bankId) {
	  	  this.bankId = bankId;
	  }
	  public BigDecimal getCurrencyId() {
	  	  return currencyId;
	  }
	  public void setCurrencyId(BigDecimal currencyId) {
	  	  this.currencyId = currencyId;
	  }
	  public BigDecimal getFundReqEFT() {
	  	  return fundReqEFT;
	  }
	  public void setFundReqEFT(BigDecimal fundReqEFT) {
	  	  this.fundReqEFT = fundReqEFT;
	  }
	  public BigDecimal getFundReqTT() {
	  	  return fundReqTT;
	  }
	  public void setFundReqTT(BigDecimal fundReqTT) {
	  	  this.fundReqTT = fundReqTT;
	  }
	  public BigDecimal getFundReqCash() {
	  	  return fundReqCash;
	  }
	  public void setFundReqCash(BigDecimal fundReqCash) {
	  	  this.fundReqCash = fundReqCash;
	  }
	  public BigDecimal getDealsDoneEFT() {
	  	  return dealsDoneEFT;
	  }
	  public void setDealsDoneEFT(BigDecimal dealsDoneEFT) {
	  	  this.dealsDoneEFT = dealsDoneEFT;
	  }
	  public BigDecimal getDealsDoneTT() {
	  	  return dealsDoneTT;
	  }
	  public void setDealsDoneTT(BigDecimal dealsDoneTT) {
	  	  this.dealsDoneTT = dealsDoneTT;
	  }
	  public BigDecimal getDealsDoneCash() {
	  	  return dealsDoneCash;
	  }
	  public void setDealsDoneCash(BigDecimal dealsDoneCash) {
	  	  this.dealsDoneCash = dealsDoneCash;
	  }
	  public BigDecimal getDiffEFT() {
	  	  return diffEFT;
	  }
	  public void setDiffEFT(BigDecimal diffEFT) {
	  	  this.diffEFT = diffEFT;
	  }
	  public BigDecimal getDiffTT() {
	  	  return diffTT;
	  }
	  public void setDiffTT(BigDecimal diffTT) {
	  	  this.diffTT = diffTT;
	  }
	  public BigDecimal getDiffCash() {
	  	  return diffCash;
	  }
	  public void setDiffCash(BigDecimal diffCash) {
	  	  this.diffCash = diffCash;
	  }
	  public String getExchangeCountryName() {
	  	  return exchangeCountryName;
	  }
	  public void setExchangeCountryName(String exchangeCountryName) {
	  	  this.exchangeCountryName = exchangeCountryName;
	  }
	  public String getBankCountryName() {
	  	  return bankCountryName;
	  }
	  public void setBankCountryName(String bankCountryName) {
	  	  this.bankCountryName = bankCountryName;
	  }
	  public String getBankName() {
	  	  return bankName;
	  }
	  public void setBankName(String bankName) {
	  	  this.bankName = bankName;
	  }
	  public String getCurrencyName() {
	  	  return currencyName;
	  }
	  public void setCurrencyName(String currencyName) {
	  	  this.currencyName = currencyName;
	  }
	  public Boolean getBooBank() {
	  	  return booBank;
	  }
	  public void setBooBank(Boolean booBank) {
	  	  this.booBank = booBank;
	  }
	  public Boolean getBooTT() {
	  	  return booTT;
	  }
	  public void setBooTT(Boolean booTT) {
	  	  this.booTT = booTT;
	  }
	  public String getTtBankName() {
	  	  return ttBankName;
	  }
	  public void setTtBankName(String ttBankName) {
	  	  this.ttBankName = ttBankName;
	  }
	  public BigDecimal getFcAmount() {
	  	  return fcAmount;
	  }
	  public void setFcAmount(BigDecimal fcAmount) {
	  	  this.fcAmount = fcAmount;
	  }
	  public BigDecimal getTotalFcAmount() {
	  	  return totalFcAmount;
	  }
	  public void setTotalFcAmount(BigDecimal totalFcAmount) {
	  	  this.totalFcAmount = totalFcAmount;
	  }
	  public BigDecimal getTrasureDealHeaderId() {
	  	  return trasureDealHeaderId;
	  }
	  public void setTrasureDealHeaderId(BigDecimal trasureDealHeaderId) {
	  	  this.trasureDealHeaderId = trasureDealHeaderId;
	  }
	  public String getFundReqEFTDisplay() {
	  	  return fundReqEFTDisplay;
	  }
	  public void setFundReqEFTDisplay(String fundReqEFTDisplay) {
	  	  this.fundReqEFTDisplay = fundReqEFTDisplay;
	  }
	  public String getFundReqTTDisplay() {
	  	  return fundReqTTDisplay;
	  }
	  public void setFundReqTTDisplay(String fundReqTTDisplay) {
	  	  this.fundReqTTDisplay = fundReqTTDisplay;
	  }
	  public String getFundReqCashDisplay() {
	  	  return fundReqCashDisplay;
	  }
	  public void setFundReqCashDisplay(String fundReqCashDisplay) {
	  	  this.fundReqCashDisplay = fundReqCashDisplay;
	  }
	  public String getDealsDoneEFTDisplay() {
	  	  return dealsDoneEFTDisplay;
	  }
	  public void setDealsDoneEFTDisplay(String dealsDoneEFTDisplay) {
	  	  this.dealsDoneEFTDisplay = dealsDoneEFTDisplay;
	  }
	  public String getDealsDoneTTDisplay() {
	  	  return dealsDoneTTDisplay;
	  }
	  public void setDealsDoneTTDisplay(String dealsDoneTTDisplay) {
	  	  this.dealsDoneTTDisplay = dealsDoneTTDisplay;
	  }
	  public String getDealsDoneCashDisplay() {
	  	  return dealsDoneCashDisplay;
	  }
	  public void setDealsDoneCashDisplay(String dealsDoneCashDisplay) {
	  	  this.dealsDoneCashDisplay = dealsDoneCashDisplay;
	  }
	  public String getDiffEFTDisplay() {
	  	  return diffEFTDisplay;
	  }
	  public void setDiffEFTDisplay(String diffEFTDisplay) {
	  	  this.diffEFTDisplay = diffEFTDisplay;
	  }
	  public String getDiffTTDisplay() {
	  	  return diffTTDisplay;
	  }
	  public void setDiffTTDisplay(String diffTTDisplay) {
	  	  this.diffTTDisplay = diffTTDisplay;
	  }
	  public String getDiffCashDisplay() {
	  	  return diffCashDisplay;
	  }
	  public void setDiffCashDisplay(String diffCashDisplay) {
	  	  this.diffCashDisplay = diffCashDisplay;
	  }
	  public String getFcAmountDisplay() {
	  	  return fcAmountDisplay;
	  }
	  public void setFcAmountDisplay(String fcAmountDisplay) {
	  	  this.fcAmountDisplay = fcAmountDisplay;
	  }
	  
	  
	  
}
