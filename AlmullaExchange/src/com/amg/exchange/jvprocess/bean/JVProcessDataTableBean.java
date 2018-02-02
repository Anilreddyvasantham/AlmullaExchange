package com.amg.exchange.jvprocess.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.amg.exchange.remittance.bean.KIOSKDenominationDataTable;

public class JVProcessDataTableBean implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String subDescription;
	private BigDecimal bankId;
	private BigDecimal lineNo;
	private String bankCode;
	private BigDecimal currencyId;
	private String currencyCode;
	private BigDecimal accountNoId;
	private String accountNumber;
	private String fundGlNo;
	private String particularsDesc;
	private String glAccountNo;
	private String subCode;
	private BigDecimal foreignAmt;
	private BigDecimal exchangeRate;
	private BigDecimal kwdAmount;
	private boolean currencyNormal;
	private boolean currencyWithDenomination;
	private ArrayList<KIOSKDenominationDataTable> lstDenominationDataTable;
	private String bankName;
	private String currencyName;
	
	private String jvGlAcNoOne;
	private String jvGlAcNoTwo;
	private String jvGlAcNoThree;
	private String jvGlAcNoFour;
	private String jvGlAcNoFive;
	private String jvGlAcNoSix;
	private String jvGlAcNoSeven;
	private BigDecimal dayBookDetailsID;
	
	
	public boolean isCurrencyNormal() {
		return currencyNormal;
	}
	public void setCurrencyNormal(boolean currencyNormal) {
		this.currencyNormal = currencyNormal;
	}
	public boolean isCurrencyWithDenomination() {
		return currencyWithDenomination;
	}
	public void setCurrencyWithDenomination(boolean currencyWithDenomination) {
		this.currencyWithDenomination = currencyWithDenomination;
	}
	
	public String getSubDescription() {
		return subDescription;
	}
	public void setSubDescription(String subDescription) {
		this.subDescription = subDescription;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public BigDecimal getAccountNoId() {
		return accountNoId;
	}
	public void setAccountNoId(BigDecimal accountNoId) {
		this.accountNoId = accountNoId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getFundGlNo() {
		return fundGlNo;
	}
	public void setFundGlNo(String fundGlNo) {
		this.fundGlNo = fundGlNo;
	}
	public String getParticularsDesc() {
		return particularsDesc;
	}
	public void setParticularsDesc(String particularsDesc) {
		this.particularsDesc = particularsDesc;
	}
	public String getGlAccountNo() {
		return glAccountNo;
	}
	public void setGlAccountNo(String glAccountNo) {
		this.glAccountNo = glAccountNo;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public BigDecimal getForeignAmt() {
		return foreignAmt;
	}
	public void setForeignAmt(BigDecimal foreignAmt) {
		this.foreignAmt = foreignAmt;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public BigDecimal getKwdAmount() {
		return kwdAmount;
	}
	public void setKwdAmount(BigDecimal kwdAmount) {
		this.kwdAmount = kwdAmount;
	}
	public ArrayList<KIOSKDenominationDataTable> getLstDenominationDataTable() {
		return lstDenominationDataTable;
	}
	public void setLstDenominationDataTable(
			ArrayList<KIOSKDenominationDataTable> lstDenominationDataTable) {
		this.lstDenominationDataTable = lstDenominationDataTable;
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
	public BigDecimal getLineNo() {
		return lineNo;
	}
	public void setLineNo(BigDecimal lineNo) {
		this.lineNo = lineNo;
	}
	public String getJvGlAcNoOne() {
		return jvGlAcNoOne;
	}
	public void setJvGlAcNoOne(String jvGlAcNoOne) {
		this.jvGlAcNoOne = jvGlAcNoOne;
	}
	public String getJvGlAcNoTwo() {
		return jvGlAcNoTwo;
	}
	public void setJvGlAcNoTwo(String jvGlAcNoTwo) {
		this.jvGlAcNoTwo = jvGlAcNoTwo;
	}
	public String getJvGlAcNoThree() {
		return jvGlAcNoThree;
	}
	public void setJvGlAcNoThree(String jvGlAcNoThree) {
		this.jvGlAcNoThree = jvGlAcNoThree;
	}
	public String getJvGlAcNoFour() {
		return jvGlAcNoFour;
	}
	public void setJvGlAcNoFour(String jvGlAcNoFour) {
		this.jvGlAcNoFour = jvGlAcNoFour;
	}
	public String getJvGlAcNoFive() {
		return jvGlAcNoFive;
	}
	public void setJvGlAcNoFive(String jvGlAcNoFive) {
		this.jvGlAcNoFive = jvGlAcNoFive;
	}
	public String getJvGlAcNoSix() {
		return jvGlAcNoSix;
	}
	public void setJvGlAcNoSix(String jvGlAcNoSix) {
		this.jvGlAcNoSix = jvGlAcNoSix;
	}
	public String getJvGlAcNoSeven() {
		return jvGlAcNoSeven;
	}
	public void setJvGlAcNoSeven(String jvGlAcNoSeven) {
		this.jvGlAcNoSeven = jvGlAcNoSeven;
	}
	public BigDecimal getDayBookDetailsID() {
		return dayBookDetailsID;
	}
	public void setDayBookDetailsID(BigDecimal dayBookDetailsID) {
		this.dayBookDetailsID = dayBookDetailsID;
	}
	
	
	
	
	

}
