package com.amg.exchange.remittance.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_EX_CBNK")
public class ViewBankDetails {

	private BigDecimal rowId;
	private BigDecimal applicationCountryId;
	private BigDecimal chequeBankId;
	private String chequeBankCode;
	private String bankFullName;
	private String bankShortName;
	private String bankFullNameAr;
	private String bankShortNameAr;
	private String checkGlNumber;
	private String knetGlNo;
	private BigDecimal debitCardLength;


	@Id
	@Column(name="SRNO")
	public BigDecimal getRowId() {
		return rowId;
	}
	public void setRowId(BigDecimal rowId) {
		this.rowId = rowId;
	}
	
	@Column(name="APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name="CHQ_GLNO")
	public String getCheckGlNumber() {
		return checkGlNumber;
	}
	public void setCheckGlNumber(String checkGlNumber) {
		this.checkGlNumber = checkGlNumber;
	}
	@Column(name="KNET_GLNO")
	public String getKnetGlNo() {
		return knetGlNo;
	}

	public void setKnetGlNo(String knetGlNo) {
		this.knetGlNo = knetGlNo;
	}
	@Column(name="SHORT_DESC")
	public String getBankShortName() {
		return bankShortName;
	}
	public void setBankShortName(String bankShortName) {
		this.bankShortName = bankShortName;
	}
	@Column(name="AR_FULL_DESC")
	public String getBankFullNameAr() {
		return bankFullNameAr;
	}

	public void setBankFullNameAr(String bankFullNameAr) {
		this.bankFullNameAr = bankFullNameAr;
	}
	@Column(name="AR_SHORT_DESC")
	public String getBankShortNameAr() {
		return bankShortNameAr;
	}
	public void setBankShortNameAr(String bankShortNameAr) {
		this.bankShortNameAr = bankShortNameAr;
	}

	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}
	@Column(name="FULL_DESC")
	public String getBankFullName() {
		return bankFullName;
	}

	@Column(name="CHEQUE_BANK_ID")
	public BigDecimal getChequeBankId() {
		return chequeBankId;
	}
	public void setChequeBankId(BigDecimal chequeBankId) {
		this.chequeBankId = chequeBankId;
	}

	@Column(name="CHEQUE_BANK_CODE")
	public String getChequeBankCode() {
		return chequeBankCode;
	}
	public void setChequeBankCode(String chequeBankCode) {
		this.chequeBankCode = chequeBankCode;
	}

	@Column(name="DBCARD_LEN")
	public BigDecimal getDebitCardLength() {
		return debitCardLength;
	}
	public void setDebitCardLength(BigDecimal debitCardLength) {
		this.debitCardLength = debitCardLength;
	}

}
