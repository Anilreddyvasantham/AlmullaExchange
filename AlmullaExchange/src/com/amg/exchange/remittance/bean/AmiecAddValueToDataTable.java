package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;

public class AmiecAddValueToDataTable implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private BigDecimal bankId;
	private String bankName;
	
	private BigDecimal countryId;
	private String countryName;
	private String flexiField;
	
	private String amiecCode;
	private String amiecDesc;
	
	private String additionalDescription;
	private String bankCode;
	private Boolean select = false;
	
	private List<AdditionalBankAmiecTemp> addtionalbankAmiecTempList;
	
	public List<AdditionalBankRuleAddData> listBankCombo;
	
	
	public List<AdditionalBankAmiecTemp> getAddtionalbankAmiecTempList() {
		return addtionalbankAmiecTempList;
	}

	public void setAddtionalbankAmiecTempList(
			List<AdditionalBankAmiecTemp> addtionalbankAmiecTempList) {
		this.addtionalbankAmiecTempList = addtionalbankAmiecTempList;
	}

	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getFlexiField() {
		return flexiField;
	}
	public void setFlexiField(String flexiField) {
		this.flexiField = flexiField;
	}
	public String getAmiecCode() {
		return amiecCode;
	}
	public void setAmiecCode(String amiecCode) {
		this.amiecCode = amiecCode;
	}
	public String getAmiecDesc() {
		return amiecDesc;
	}
	public void setAmiecDesc(String amiecDesc) {
		this.amiecDesc = amiecDesc;
	}
	public String getAdditionalDescription() {
		return additionalDescription;
	}
	public void setAdditionalDescription(String additionalDescription) {
		this.additionalDescription = additionalDescription;
	}
	public List<AdditionalBankRuleAddData> getListBankCombo() {
		return listBankCombo;
	}
	public void setListBankCombo(List<AdditionalBankRuleAddData> listBankCombo) {
		this.listBankCombo = listBankCombo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Boolean getSelect() {
		return select;
	}

	public void setSelect(Boolean select) {
		this.select = select;
	}
	
}
