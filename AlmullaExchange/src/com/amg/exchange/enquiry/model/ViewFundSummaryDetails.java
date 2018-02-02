package com.amg.exchange.enquiry.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="VW_EX_FUND_SUMMARY_DETAILS")
public class ViewFundSummaryDetails implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  
	  private BigDecimal idNo;
	  private BigDecimal applicationCountryId;
	  private BigDecimal bankCountryId;
	  private BigDecimal currencyId;
	  private String currencyName;
	  private BigDecimal bankId;
	  private String bankShortName;
	  private BigDecimal eftFundAmount;
	  private BigDecimal eftDealAmount;
	  private BigDecimal ttFundAmount;
	  private BigDecimal ttDealAmount;
	  private BigDecimal cashFundAmount;
	  private BigDecimal cashDealAmount;
	  private String bankCountryName;
	  private Date projectionDate;
	  
	  
	  @Id
	  @Column(name="IDNO")
	  public BigDecimal getIdNo() {
	  	  return idNo;
	  }
	  public void setIdNo(BigDecimal idNo) {
	  	  this.idNo = idNo;
	  }
	  @Column(name="APPLICATION_COUNTRY_ID")
	  public BigDecimal getApplicationCountryId() {
	  	  return applicationCountryId;
	  }
	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
	  	  this.applicationCountryId = applicationCountryId;
	  }
	  @Column(name="BANK_COUNTRY_ID")
	  public BigDecimal getBankCountryId() {
	  	  return bankCountryId;
	  }
	  public void setBankCountryId(BigDecimal bankCountryId) {
	  	  this.bankCountryId = bankCountryId;
	  }
	  @Column(name="CURRENCY_ID")
	  public BigDecimal getCurrencyId() {
	  	  return currencyId;
	  }
	  public void setCurrencyId(BigDecimal currencyId) {
	  	  this.currencyId = currencyId;
	  }
	  @Column(name="CURRENCY_NAME")
	  public String getCurrencyName() {
	  	  return currencyName;
	  }
	  public void setCurrencyName(String currencyName) {
	  	  this.currencyName = currencyName;
	  }
	  @Column(name="BANK_ID")
	  public BigDecimal getBankId() {
	  	  return bankId;
	  }
	  public void setBankId(BigDecimal bankId) {
	  	  this.bankId = bankId;
	  }
	  @Column(name="BANK_SHORT_NAME")
	  public String getBankShortName() {
	  	  return bankShortName;
	  }
	  public void setBankShortName(String bankShortName) {
	  	  this.bankShortName = bankShortName;
	  }
	  @Column(name="EFT_FUND_AMOUNT")
	  public BigDecimal getEftFundAmount() {
	  	  return eftFundAmount;
	  }
	  public void setEftFundAmount(BigDecimal eftFundAmount) {
	  	  this.eftFundAmount = eftFundAmount;
	  }
	  @Column(name="EFT_DEAL_AMOUNT")
	  public BigDecimal getEftDealAmount() {
	  	  return eftDealAmount;
	  }
	  public void setEftDealAmount(BigDecimal eftDealAmount) {
	  	  this.eftDealAmount = eftDealAmount;
	  }
	  @Column(name="TT_FUND_AMOUNT")
	  public BigDecimal getTtFundAmount() {
	  	  return ttFundAmount;
	  }
	  public void setTtFundAmount(BigDecimal ttFundAmount) {
	  	  this.ttFundAmount = ttFundAmount;
	  }
	  @Column(name="TT_DEAL_AMOUNT")
	  public BigDecimal getTtDealAmount() {
	  	  return ttDealAmount;
	  }
	  public void setTtDealAmount(BigDecimal ttDealAmount) {
	  	  this.ttDealAmount = ttDealAmount;
	  }
	  @Column(name="CASH_FUND_AMOUNT")
	  public BigDecimal getCashFundAmount() {
	  	  return cashFundAmount;
	  }
	  public void setCashFundAmount(BigDecimal cashFundAmount) {
	  	  this.cashFundAmount = cashFundAmount;
	  }
	  @Column(name="CASH_DEAL_AMOUNT")
	  public BigDecimal getCashDealAmount() {
	  	  return cashDealAmount;
	  }
	  public void setCashDealAmount(BigDecimal cashDealAmount) {
	  	  this.cashDealAmount = cashDealAmount;
	  }
	  @Column(name="BANK_COUNTRY_NAME")
	  public String getBankCountryName() {
	  	  return bankCountryName;
	  }
	  public void setBankCountryName(String bankCountryName) {
	  	  this.bankCountryName = bankCountryName;
	  }
	  @Column(name="PROJECTION_DATE")
	  public Date getProjectionDate() {
	  	  return projectionDate;
	  }
	  public void setProjectionDate(Date projectionDate) {
	  	  this.projectionDate = projectionDate;
	  }
	  
	  
}
