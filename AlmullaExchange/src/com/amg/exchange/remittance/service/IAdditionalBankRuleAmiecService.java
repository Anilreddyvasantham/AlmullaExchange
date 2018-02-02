package com.amg.exchange.remittance.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;

public interface IAdditionalBankRuleAmiecService {

	public void save(AdditionalBankRuleAmiec additionalBankRuleAmiec)throws Exception;
	
	public BigDecimal getMasterPk(String flexfield);
	
	public List<AdditionalBankRuleAmiec> getamiecCodeList();
	
	//public List<AdditionalBankRuleAmiec> getamiecDescriptionList(String amiecDescription);
	
	public List<String> getAllComponent(String query,BigDecimal countryId,String flexfield);
	
	public List<String> getComponentadditionalData(String query,BigDecimal countryId,String flexField,BigDecimal bankId);
	
	public List<AdditionalBankRuleAmiec> populateAmiecDescription(BigDecimal countryId,String flexField,String amiecCode);	
	
	public List<AdditionalBankRuleAddData> populateAdditionalDescription(BigDecimal countryId,BigDecimal bankId,String flexField,String additionalData);	
	
	public List<AdditionalBankRuleMap> getLstofFlexFields(BigDecimal countryId);
	
	public List<AdditionalBankRuleMap> getAllRecordbyCountryFlex(BigDecimal countryId,String flexfield);
	
	public List<AdditionalBankRuleAmiec> getAllDetailsbyCountryFlex(BigDecimal countryId,String flexfield);
	
	//public List<AdditionalBankRuleAmiec> deleteIsActiveN(AdditionalBankRuleAmiec additionalBankRuleAmiec);
	
	public List<AdditionalBankRuleAddData> populateBankRuleData(BigDecimal countryId,String flexfield,BigDecimal bankId,String additionalData);
	
	// fetch id based on country and flexfield
	public BigDecimal getAdditionalBankRuleMapMasterPk(BigDecimal countryId,String flexfield);
	
}
