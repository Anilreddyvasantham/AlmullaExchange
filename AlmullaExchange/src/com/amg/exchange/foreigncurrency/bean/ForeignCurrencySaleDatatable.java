package com.amg.exchange.foreigncurrency.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ForeignCurrencySaleDatatable {
	
	
	private String sourceOfIncome;
	private  int notesQty;
	private BigDecimal denaminationAmount;
	private BigDecimal exchangeRate;
	private BigDecimal adjustmentAmount;
	private String progNo;
	private String purpose;
	private String transactionType;
	private String remarks;
	private Date documentDate;
	private BigDecimal documentLineNo;
	private Date accountMMYY;
	public String getSourceOfIncome() {
		return sourceOfIncome;
	}
	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}
	public  int getNotesQty() {
		return notesQty;
	}
	public void setNotesQty(int notesQty) {
		this.notesQty = notesQty;
	}
	public BigDecimal getDenaminationAmount() {
		return denaminationAmount;
	}
	public void setDenaminationAmount(BigDecimal denaminationAmount) {
		this.denaminationAmount = denaminationAmount;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public BigDecimal getAdjustmentAmount() {
		return adjustmentAmount;
	}
	public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}
	public String getProgNo() {
		return progNo;
	}
	public void setProgNo(String progNo) {
		this.progNo = progNo;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	public BigDecimal getDocumentLineNo() {
		return documentLineNo;
	}
	public void setDocumentLineNo(BigDecimal documentLineNo) {
		this.documentLineNo = documentLineNo;
	}
	public Date getAccountMMYY() {
		return accountMMYY;
	}
	public void setAccountMMYY(Date accountMMYY) {
		this.accountMMYY = accountMMYY;
	}
	
	
	

}
