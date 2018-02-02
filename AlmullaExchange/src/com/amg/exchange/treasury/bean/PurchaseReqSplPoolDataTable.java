package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class PurchaseReqSplPoolDataTable {
	
	private BigDecimal customerSpecialDealReqId;
	private String customerFirstName;
	private BigDecimal customerReference;
	private BigDecimal documentNumber;
	private Date projectionDate;
	private BigDecimal foreignCurrencyAmount;
	private BigDecimal localAmount;
	private BigDecimal fCAmountforCommon;
	private Boolean selectRecord=false;
	private BigDecimal customerId;
	private BigDecimal splPoolPurchaseDetId;
	private BigDecimal splReqCompanyId;
	private BigDecimal splReqDocCode;
	private BigDecimal splReqFinanceYear;
	private BigDecimal splReqDocNo;
	private String faAccountNo;
	
	public PurchaseReqSplPoolDataTable(BigDecimal customerSpecialDealReqId,String customerFirstName,
			BigDecimal documentNumber, Date projectionDate,
			BigDecimal foreignCurrencyAmount, BigDecimal localAmount,BigDecimal fCAmountforCommon,Boolean selectRecord,BigDecimal customerId
			,BigDecimal splPoolPurchaseDetId,BigDecimal splReqCompanyId,BigDecimal splReqDocCode,BigDecimal splReqDocNo,BigDecimal splReqFinanceYear) {
		super();
		this.customerSpecialDealReqId = customerSpecialDealReqId;
		this.customerFirstName = customerFirstName;
		this.documentNumber = documentNumber;
		this.projectionDate = projectionDate;
		this.foreignCurrencyAmount = foreignCurrencyAmount;
		this.localAmount = localAmount;
		this.fCAmountforCommon = fCAmountforCommon;
		this.selectRecord= selectRecord;
		this.customerId = customerId;
		this.splPoolPurchaseDetId = splPoolPurchaseDetId;
		this.splReqCompanyId = splReqCompanyId;
		this.splReqDocCode = splReqDocCode;
		this.splReqDocNo = splReqDocNo;
		this.splReqFinanceYear = splReqFinanceYear;
		
	}
	
	public PurchaseReqSplPoolDataTable() {
		super();
	}
	
	

	public String getFaAccountNo() {
		return faAccountNo;
	}

	public void setFaAccountNo(String faAccountNo) {
		this.faAccountNo = faAccountNo;
	}

	public BigDecimal getCustomerSpecialDealReqId() {
		return customerSpecialDealReqId;
	}

	public void setCustomerSpecialDealReqId(BigDecimal customerSpecialDealReqId) {
		this.customerSpecialDealReqId = customerSpecialDealReqId;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Date getProjectionDate() {
		return projectionDate;
	}

	public void setProjectionDate(Date projectionDate) {
		this.projectionDate = projectionDate;
	}

	public BigDecimal getForeignCurrencyAmount() {
		return foreignCurrencyAmount;
	}

	public void setForeignCurrencyAmount(BigDecimal foreignCurrencyAmount) {
		this.foreignCurrencyAmount = foreignCurrencyAmount;
	}

	public BigDecimal getLocalAmount() {
		return localAmount;
	}

	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}
	
	public BigDecimal getfCAmountforCommon() {
		return fCAmountforCommon;
	}

	public void setfCAmountforCommon(BigDecimal fCAmountforCommon) {
		this.fCAmountforCommon = fCAmountforCommon;
	}
	
	public Boolean getSelectRecord() {
		return selectRecord;
	}

	public void setSelectRecord(Boolean selectRecord) {
		this.selectRecord = selectRecord;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal bigDecimal) {
		this.customerId = bigDecimal;
	}

	public BigDecimal getSplPoolPurchaseDetId() {
		return splPoolPurchaseDetId;
	}

	public void setSplPoolPurchaseDetId(BigDecimal splPoolPurchaseDetId) {
		this.splPoolPurchaseDetId = splPoolPurchaseDetId;
	}

	public BigDecimal getSplReqCompanyId() {
		return splReqCompanyId;
	}

	public void setSplReqCompanyId(BigDecimal splReqCompanyId) {
		this.splReqCompanyId = splReqCompanyId;
	}

	public BigDecimal getSplReqDocCode() {
		return splReqDocCode;
	}

	public void setSplReqDocCode(BigDecimal splReqDocCode) {
		this.splReqDocCode = splReqDocCode;
	}

	public BigDecimal getSplReqDocNo() {
		return splReqDocNo;
	}

	public void setSplReqDocNo(BigDecimal splReqDocNo) {
		this.splReqDocNo = splReqDocNo;
	}

	public BigDecimal getSplReqFinanceYear() {
		return splReqFinanceYear;
	}

	public void setSplReqFinanceYear(BigDecimal splReqFinanceYear) {
		this.splReqFinanceYear = splReqFinanceYear;
	}

	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	
	
	

}
