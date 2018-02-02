package com.amg.exchange.cancelreissue.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "VW_REPUSH_BANK_TRNX_LIST")
public class ViewRepushBankTrnxList implements Serializable{
	
	
	@Id
	@Column(name="DOCNO")
	BigDecimal docNumber;
	
	@Column(name="COMCOD")
	BigDecimal companyCode =null;
	
	@Column(name="DOCFYR")
	BigDecimal docfyr =null;
	
	@Column(name="CUSREF")
	BigDecimal cutomerRefernce ;
	
	@Column(name="CORSBNK")
	String bankCode;
	
	@Column(name="CUSNAM")
	String cutomerName;
	
	@Column(name="BNFNAME")
	String beneName;
	
	@Column(name="DELVIND")
	String delvInd;
	
	@Column(name="TRNFSTS")
	String trnStatus;
	
	@Column(name="DOCDAT")
	Date docDate;
	
	@Column(name="AMTEXCH")
	BigDecimal amountExch;
	
	@Column(name="AMTTRNS")
	BigDecimal amountTrans;
	
	@Column(name="NETAMT")
	BigDecimal netAmount;
	
	@Column(name="REMARKS")
	String remarks;

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
	
	
	
	


}
