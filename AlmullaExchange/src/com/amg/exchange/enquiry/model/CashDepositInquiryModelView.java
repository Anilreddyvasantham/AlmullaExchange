package com.amg.exchange.enquiry.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_EX_CASH_DEPOSIT_INQUIRY")
public class CashDepositInquiryModelView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal idNo;
	private BigDecimal adjustmentAmount;
	private BigDecimal cashAmount;
	private BigDecimal countryBranchCode;
	private BigDecimal countryBranchId;
	private BigDecimal currencyId;
	private String currencyName;
	private BigDecimal denominationAmount;
	private BigDecimal denominationId;
	private Date documentDate;
	private BigDecimal documentFinancialYear;
	private BigDecimal documentId;
	private BigDecimal documentCode;
	private String documentDesc;
	private BigDecimal documentLNo;
	private BigDecimal documentNo;
	private String fromBranchName;
	private String fromCashier;
	private String fromCashierName;
	private BigDecimal notesQty;
	private BigDecimal totalValues;
	private String toBranchName;
	private String toCashier;
	private String toCashierName;
	private BigDecimal toCountryBranchCode;
	
	
	public CashDepositInquiryModelView() {
		super();
	}
	
	
	@Id
	@Column(name="IDNO")
	public BigDecimal getIdNo() {
		return idNo;
	}
	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}
	
	@Column(name="ADJUSTMENT_AMOUNT")
	public BigDecimal getAdjustmentAmount() {
		return adjustmentAmount;
	}
	public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}
	
	@Column(name="CASH_AMOUNT")
	public BigDecimal getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}
	
	@Column(name="COUNTRY_BRANCH_CODE")
	public BigDecimal getCountryBranchCode() {
		return countryBranchCode;
	}
	public void setCountryBranchCode(BigDecimal countryBranchCode) {
		this.countryBranchCode = countryBranchCode;
	}
	
	@Column(name="COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
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
	
	@Column(name="DENOMINATION_AMOUNT")
	public BigDecimal getDenominationAmount() {
		return denominationAmount;
	}
	public void setDenominationAmount(BigDecimal denominationAmount) {
		this.denominationAmount = denominationAmount;
	}
	
	@Column(name="DENOMINATION_ID")
	public BigDecimal getDenominationId() {
		return denominationId;
	}
	public void setDenominationId(BigDecimal denominationId) {
		this.denominationId = denominationId;
	}

	@Column(name="DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	@Column(name="DOCUMENT_FINANCE_YR")
	public BigDecimal getDocumentFinancialYear() {
		return documentFinancialYear;
	}
	public void setDocumentFinancialYear(BigDecimal documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}
	
	@Column(name="DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}
	
	@Column(name="DOCUMENT_LNO")
	public BigDecimal getDocumentLNo() {
		return documentLNo;
	}
	public void setDocumentLNo(BigDecimal documentLNo) {
		this.documentLNo = documentLNo;
	}
	
	@Column(name="DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	@Column(name="FROM_BRANCH_NAME")
	public String getFromBranchName() {
		return fromBranchName;
	}
	public void setFromBranchName(String fromBranchName) {
		this.fromBranchName = fromBranchName;
	}
	
	@Column(name="FROM_CASHIER")
	public String getFromCashier() {
		return fromCashier;
	}
	public void setFromCashier(String fromCashier) {
		this.fromCashier = fromCashier;
	}
	
	@Column(name="NOTES_QTY")
	public BigDecimal getNotesQty() {
		return notesQty;
	}
	public void setNotesQty(BigDecimal notesQty) {
		this.notesQty = notesQty;
	}
	
	@Column(name="TOTAL_VALUE")
	public BigDecimal getTotalValues() {
		return totalValues;
	}
	public void setTotalValues(BigDecimal totalValues) {
		this.totalValues = totalValues;
	}
	
	@Column(name="TO_BRANCH_NAME")
	public String getToBranchName() {
		return toBranchName;
	}
	public void setToBranchName(String toBranchName) {
		this.toBranchName = toBranchName;
	}
	
	@Column(name="TO_CASHIER")
	public String getToCashier() {
		return toCashier;
	}
	public void setToCashier(String toCashier) {
		this.toCashier = toCashier;
	}
	
	@Column(name="TO_COUNTRY_BRANCH_CODE")
	public BigDecimal getToCountryBranchCode() {
		return toCountryBranchCode;
	}
	public void setToCountryBranchCode(BigDecimal toCountryBranchCode) {
		this.toCountryBranchCode = toCountryBranchCode;
	}

	@Column(name="DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name="DOCUMENT_DESC")
	public String getDocumentDesc() {
		return documentDesc;
	}
	public void setDocumentDesc(String documentDesc) {
		this.documentDesc = documentDesc;
	}

	@Column(name="FROM_CASHIER_NAME")
	public String getFromCashierName() {
		return fromCashierName;
	}
	public void setFromCashierName(String fromCashierName) {
		this.fromCashierName = fromCashierName;
	}

	@Column(name="TO_CASHIER_NAME")
	public String getToCashierName() {
		return toCashierName;
	}
	public void setToCashierName(String toCashierName) {
		this.toCashierName = toCashierName;
	}
	
	
	

}
