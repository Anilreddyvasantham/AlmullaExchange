package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;

public class AmiecAndMappingDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal bankId;
	private String bankName;
	private String bankCode;
	private String additionalDescription;
	
	public List<AdditionalBankRuleAddData> listBankCombodynamic = new ArrayList<AdditionalBankRuleAddData>();
	
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
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getAdditionalDescription() {
		return additionalDescription;
	}
	public void setAdditionalDescription(String additionalDescription) {
		this.additionalDescription = additionalDescription;
	}
	public List<AdditionalBankRuleAddData> getListBankCombodynamic() {
		return listBankCombodynamic;
	}
	public void setListBankCombodynamic(
			List<AdditionalBankRuleAddData> listBankCombodynamic) {
		this.listBankCombodynamic = listBankCombodynamic;
	}
	
}
