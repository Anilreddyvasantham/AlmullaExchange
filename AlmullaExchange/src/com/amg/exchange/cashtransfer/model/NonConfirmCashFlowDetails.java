package com.amg.exchange.cashtransfer.model;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_NON_CONF_CASH_FLOW_DET")
public class NonConfirmCashFlowDetails implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal idNo;
	private BigDecimal cashHeaderId;
	private BigDecimal countryBranchId;
	private BigDecimal documentId;
	private BigDecimal documentNo;
	private Date documentDate;
	private BigDecimal notesQuantity;
	private BigDecimal fcAmount;
	private String branchName;
	private String currencyName;
	private BigDecimal totalvalue;
	private BigDecimal documentFinancialYear;

	@Id
	@Column(name = "IDNO")
	public BigDecimal getIdNo() {
		return idNo;
	}

	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}

	@Column(name = "CASH_HEADER_ID")
	public BigDecimal getCashHeaderId() {
		return cashHeaderId;
	}

	public void setCashHeaderId(BigDecimal cashHeaderId) {
		this.cashHeaderId = cashHeaderId;
	}

	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	@Column(name = "DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name = "DOCUMENT_FINANCE_YR")
	public BigDecimal getDocumentFinancialYear() {
		return documentFinancialYear;
	}

	public void setDocumentFinancialYear(BigDecimal documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}

	@Column(name = "NOTES_QTY")
	public BigDecimal getNotesQuantity() {
		return notesQuantity;
	}

	public void setNotesQuantity(BigDecimal notesQuantity) {
		this.notesQuantity = notesQuantity;
	}

	@Column(name = "FC_AMOUNT")
	public BigDecimal getFcAmount() {
		return fcAmount;
	}

	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}

	@Column(name = "BRANCH_NAME")
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Column(name = "CURRENCY_NAME")
	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	@Column(name = "TOTAL_VALUE")
	public BigDecimal getTotalvalue() {
		return totalvalue;
	}

	public void setTotalvalue(BigDecimal totalvalue) {
		this.totalvalue = totalvalue;
	}
}
