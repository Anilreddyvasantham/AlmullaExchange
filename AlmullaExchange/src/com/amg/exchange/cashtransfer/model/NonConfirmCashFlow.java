package com.amg.exchange.cashtransfer.model;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_NON_CONFORM_CASH_FLOW")
public class NonConfirmCashFlow implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private BigDecimal countryBranchId;
	private BigDecimal countryBranchCode;
	private BigDecimal toCountryBranchCode;
	private String fromCashier;
	private String toCashier;
	private BigDecimal documentId;
	private BigDecimal documentNo;
	private BigDecimal documentFinancialYear;
	private Date documentDate;
	private String toBranchName;
	private String fromBranchName;
	private BigDecimal cashHeaderId;
	private BigDecimal idNo;

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

	@Column(name = "COUNTRY_BRANCH_CODE")
	public BigDecimal getCountryBranchCode() {
		return countryBranchCode;
	}

	public void setCountryBranchCode(BigDecimal countryBranchCode) {
		this.countryBranchCode = countryBranchCode;
	}

	@Column(name = "TO_COUNTRY_BRANCH_CODE")
	public BigDecimal getToCountryBranchCode() {
		return toCountryBranchCode;
	}

	public void setToCountryBranchCode(BigDecimal toCountryBranchCode) {
		this.toCountryBranchCode = toCountryBranchCode;
	}

	@Column(name = "FROM_CASHIER")
	public String getFromCashier() {
		return fromCashier;
	}

	public void setFromCashier(String fromCashier) {
		this.fromCashier = fromCashier;
	}

	@Column(name = "TO_CASHIER")
	public String getToCashier() {
		return toCashier;
	}

	public void setToCashier(String toCashier) {
		this.toCashier = toCashier;
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

	@Column(name = "DOCUMENT_FINANCE_YR")
	public BigDecimal getDocumentFinancialYear() {
		return documentFinancialYear;
	}

	public void setDocumentFinancialYear(BigDecimal documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}

	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name = "TO_BRANCH_NAME")
	public String getToBranchName() {
		return toBranchName;
	}

	public void setToBranchName(String toBranchName) {
		this.toBranchName = toBranchName;
	}

	@Column(name = "FROM_BRANCH_NAME")
	public String getFromBranchName() {
		return fromBranchName;
	}

	public void setFromBranchName(String fromBranchName) {
		this.fromBranchName = fromBranchName;
	}

	@Override
	public String toString() {
		return "NonConfirmCashFlow [countryBranchId=" + countryBranchId + ", countryBranchCode=" + countryBranchCode + ", toCountryBranchCode=" + toCountryBranchCode + ", fromCashier=" + fromCashier + ", toCashier=" + toCashier + ", documentId=" + documentId + ", documentNo=" + documentNo
				+ ", documentFinancialYear=" + documentFinancialYear + ", documentDate=" + documentDate + ", toBranchName=" + toBranchName + ", fromBranchName=" + fromBranchName + ", cashHeaderId=" + cashHeaderId + ", idNo=" + idNo + "]";
	}
}
