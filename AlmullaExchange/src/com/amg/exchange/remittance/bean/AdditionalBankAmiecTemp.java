package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;

public class AdditionalBankAmiecTemp implements Serializable{

	private static final long serialVersionUID = 1L;
	private BigDecimal bankId;
	private String bankName;
	private String addtionalFieldDesc;
	private Boolean ifRecordEdited;
	private BigDecimal amiecAndBankMappingPK;
	
	private List<AdditionalBankRuleAddData> addtionalBankRuleList;
	
	
	
	public Boolean getIfRecordEdited() {
		return ifRecordEdited;
	}
	
	public void setIfRecordEdited(Boolean ifRecordEdited) {
		this.ifRecordEdited = ifRecordEdited;
	}

	
	public BigDecimal getAmiecAndBankMappingPK() {
		return amiecAndBankMappingPK;
	}

	public void setAmiecAndBankMappingPK(BigDecimal amiecAndBankMappingPK) {
		this.amiecAndBankMappingPK = amiecAndBankMappingPK;
	}

	public BigDecimal getBankId() {
		return bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public List<AdditionalBankRuleAddData> getAddtionalBankRuleList() {
		return addtionalBankRuleList;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public void setAddtionalBankRuleList(
			List<AdditionalBankRuleAddData> addtionalBankRuleList) {
		this.addtionalBankRuleList = addtionalBankRuleList;
	}
	public String getAddtionalFieldDesc() {
		return addtionalFieldDesc;
	}
	public void setAddtionalFieldDesc(String addtionalFieldDesc) {
		this.addtionalFieldDesc = addtionalFieldDesc;
	}
	
	
	
	
}
