package com.amg.exchange.cancelreissue.bean;

import java.math.BigDecimal;
import java.util.Date;

public class RepushBankTrnxDataTable {

	BigDecimal docNumber;

	BigDecimal companyCode = null;

	BigDecimal docfyr = null;

	BigDecimal cutomerRefernce;

	String bankCode;

	String cutomerName;

	String beneName;

	String delvInd;

	String trnStatus;

	Date docDate;

	BigDecimal amountExch;

	BigDecimal amountTrans;

	BigDecimal netAmount;

	String remarks;
	Boolean selectedStatus;
	public BigDecimal getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(BigDecimal docNumber) {
		this.docNumber = docNumber;
	}
	public BigDecimal getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(BigDecimal companyCode) {
		this.companyCode = companyCode;
	}
	public BigDecimal getDocfyr() {
		return docfyr;
	}
	public void setDocfyr(BigDecimal docfyr) {
		this.docfyr = docfyr;
	}
	public BigDecimal getCutomerRefernce() {
		return cutomerRefernce;
	}
	public void setCutomerRefernce(BigDecimal cutomerRefernce) {
		this.cutomerRefernce = cutomerRefernce;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getCutomerName() {
		return cutomerName;
	}
	public void setCutomerName(String cutomerName) {
		this.cutomerName = cutomerName;
	}
	public String getBeneName() {
		return beneName;
	}
	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}
	public String getDelvInd() {
		return delvInd;
	}
	public void setDelvInd(String delvInd) {
		this.delvInd = delvInd;
	}
	public String getTrnStatus() {
		return trnStatus;
	}
	public void setTrnStatus(String trnStatus) {
		this.trnStatus = trnStatus;
	}
	public Date getDocDate() {
		return docDate;
	}
	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}
	public BigDecimal getAmountExch() {
		return amountExch;
	}
	public void setAmountExch(BigDecimal amountExch) {
		this.amountExch = amountExch;
	}
	public BigDecimal getAmountTrans() {
		return amountTrans;
	}
	public void setAmountTrans(BigDecimal amountTrans) {
		this.amountTrans = amountTrans;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Boolean getSelectedStatus() {
		return selectedStatus;
	}
	public void setSelectedStatus(Boolean selectedStatus) {
		this.selectedStatus = selectedStatus;
	}
}
