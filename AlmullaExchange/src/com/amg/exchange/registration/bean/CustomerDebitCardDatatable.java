package com.amg.exchange.registration.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CustomerDebitCardDatatable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String colBankCode;
	private String colCardNo;
	private String colNameofCard;
	private String colAuthorizedby;
	private String colMaskCardNo;
	private BigDecimal bankId;
	private BigDecimal customerBankPkId;
	private String shortDesc;
	private String fullDesc;
	private String debitCardPrefex;
	private String debitCardNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private Date authorizedDate;
	private String bankPrefix;
	private String bankSuffix;
	
	
	public String getColBankCode() {
		return colBankCode;
	}
	public void setColBankCode(String colBankCode) {
		this.colBankCode = colBankCode;
	}
	
	public String getColCardNo() {
		return colCardNo;
	}
	public void setColCardNo(String colCardNo) {
		this.colCardNo = colCardNo;
	}
	
	public String getColNameofCard() {
		return colNameofCard;
	}
	public void setColNameofCard(String colNameofCard) {
		this.colNameofCard = colNameofCard;
	}
	
	public String getColAuthorizedby() {
		return colAuthorizedby;
	}
	public void setColAuthorizedby(String colAuthorizedby) {
		this.colAuthorizedby = colAuthorizedby;
	}
	
	public String getColMaskCardNo() {
		return colMaskCardNo;
	}
	public void setColMaskCardNo(String colMaskCardNo) {
		this.colMaskCardNo = colMaskCardNo;
	}
	
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	public BigDecimal getCustomerBankPkId() {
		return customerBankPkId;
	}
	public void setCustomerBankPkId(BigDecimal customerBankPkId) {
		this.customerBankPkId = customerBankPkId;
	}
	
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	
	public String getFullDesc() {
		return fullDesc;
	}
	public void setFullDesc(String fullDesc) {
		this.fullDesc = fullDesc;
	}
	
	public String getDebitCardPrefex() {
		return debitCardPrefex;
	}
	public void setDebitCardPrefex(String debitCardPrefex) {
		this.debitCardPrefex = debitCardPrefex;
	}
	
	public String getDebitCardNo() {
		return debitCardNo;
	}
	public void setDebitCardNo(String debitCardNo) {
		this.debitCardNo = debitCardNo;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public Date getAuthorizedDate() {
		return authorizedDate;
	}
	public void setAuthorizedDate(Date authorizedDate) {
		this.authorizedDate = authorizedDate;
	}
	
	public String getBankPrefix() {
		return bankPrefix;
	}
	public void setBankPrefix(String bankPrefix) {
		this.bankPrefix = bankPrefix;
	}
	
	public String getBankSuffix() {
		return bankSuffix;
	}
	public void setBankSuffix(String bankSuffix) {
		this.bankSuffix = bankSuffix;
	}
	
	
}
