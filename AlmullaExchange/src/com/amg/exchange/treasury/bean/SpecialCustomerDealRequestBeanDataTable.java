package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class SpecialCustomerDealRequestBeanDataTable {
	
	private BigDecimal specialCustomerDealRequestyear=null;
	private BigDecimal specialCustomerDealRequestyearId=null;
	private BigDecimal specialCustomerDealRequestNumber=null;
	private String requestDate=null;
	private BigDecimal country=null;
	private String bankName;
	private String currencyName;
	private BigDecimal foreignCurrencyAmount=null;
	private Boolean aprove=false;
	private BigDecimal bankAccountNumber=null;
	private BigDecimal companyId=null;
	private BigDecimal document=null;
	private String id;
	private String name;
	private Date validUpTo=null;
	private BigDecimal customerId=null;
	private Boolean editFiled=false;
	private Boolean booforeignCurrencyEdit=false;
	private BigDecimal fCAmount=null;
	private Boolean renderInputForFC=true;
	private BigDecimal msgValue=null;
	private BigDecimal financeAccountNumber=null;
	private BigDecimal documentNo;
	private String valueDateOption;
	private String tentativeSaleRate;
	private String customerName;
	private  Boolean renderValueDateOption=false;
	private   String valueDate;
	private String bankNameFromDT="";
	private String currencyNameFromDT="";
	private String countryNameFromDT="";
 
	
	public String getValueDateOption() {
		return valueDateOption;
	}
	public void setValueDateOption(String valueDateOption) {
		this.valueDateOption = valueDateOption;
	}

	public String getTentativeSaleRate() {
		return tentativeSaleRate;
	}
	public void setTentativeSaleRate(String tentativeSaleRate) {
		this.tentativeSaleRate = tentativeSaleRate;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getFinanceAccountNumber() {
		return financeAccountNumber;
	}
	public void setFinanceAccountNumber(BigDecimal financeAccountNumber) {
		this.financeAccountNumber = financeAccountNumber;
	}

	public BigDecimal getMsgValue() {
		return msgValue;
	}
	public void setMsgValue(BigDecimal msgValue) {
		this.msgValue = msgValue;
	}

	public void setRenderInputForFC(Boolean renderInputForFC) {
		this.renderInputForFC = renderInputForFC;
	}
	public Boolean getRenderInputForFC() {
		return renderInputForFC;
	}

	public Boolean getBooforeignCurrencyEdit() {
		return booforeignCurrencyEdit;
	}
	public void setBooforeignCurrencyEdit(Boolean booforeignCurrencyEdit) {
		this.booforeignCurrencyEdit = booforeignCurrencyEdit;
	}

	public Boolean getEditFiled() {
		return editFiled;
	}
	public void setEditFiled(Boolean editFiled) {
		this.editFiled = editFiled;
	}

	public String getBankNameFromDT() {
		return bankNameFromDT;
	}
	public void setBankNameFromDT(String bankNameFromDT) {
		this.bankNameFromDT = bankNameFromDT;
	}

	public String getCurrencyNameFromDT() {
		return currencyNameFromDT;
	}
	public void setCurrencyNameFromDT(String currencyNameFromDT) {
		this.currencyNameFromDT = currencyNameFromDT;
	}

	public String getCountryNameFromDT() {
		return countryNameFromDT;
	}
	public void setCountryNameFromDT(String countryNameFromDT) {
		this.countryNameFromDT = countryNameFromDT;
	}

	public BigDecimal getfCAmount() {
		return fCAmount;
	}
	public void setfCAmount(BigDecimal fCAmount) {
		this.fCAmount = fCAmount;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getSpecialCustomerDealRequestyearId() {
		return specialCustomerDealRequestyearId;
	}
	public void setSpecialCustomerDealRequestyearId(BigDecimal specialCustomerDealRequestyearId) {
		this.specialCustomerDealRequestyearId = specialCustomerDealRequestyearId;
	}

	public BigDecimal getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(BigDecimal bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getDocument() {
		return document;
	}
	public void setDocument(BigDecimal document) {
		this.document = document;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Date getValidUpTo() {
		return validUpTo;
	}
	public void setValidUpTo(Date validUpTo) {
		this.validUpTo = validUpTo;
	}

	public BigDecimal getSpecialCustomerDealRequestyear() {
		return specialCustomerDealRequestyear;
	}
	public void setSpecialCustomerDealRequestyear(BigDecimal specialCustomerDealRequestyear) {
		this.specialCustomerDealRequestyear = specialCustomerDealRequestyear;
	}

	public BigDecimal getSpecialCustomerDealRequestNumber() {
		return specialCustomerDealRequestNumber;
	}
	public void setSpecialCustomerDealRequestNumber(BigDecimal specialCustomerDealRequestNumber) {
		this.specialCustomerDealRequestNumber = specialCustomerDealRequestNumber;
	}

	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public BigDecimal getCountry() {
		return country;
	}
	public void setCountry(BigDecimal country) {
		this.country = country;
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

	public BigDecimal getForeignCurrencyAmount() {
		return foreignCurrencyAmount;
	}
	public void setForeignCurrencyAmount(BigDecimal foreignCurrencyAmount) {
		this.foreignCurrencyAmount = foreignCurrencyAmount;
	}

	public Boolean getAprove() {
		return aprove;
	}
	public void setAprove(Boolean aprove) {
		this.aprove = aprove;
	}

	public Boolean getRenderValueDateOption() {
		return renderValueDateOption;
	}
	public void setRenderValueDateOption(Boolean renderValueDateOption) {
		this.renderValueDateOption = renderValueDateOption;
	}

	public   String getValueDate() {
		return valueDate;
	}
	public void setValueDate( String valueDate) {
		this.valueDate = valueDate;
	}

}
