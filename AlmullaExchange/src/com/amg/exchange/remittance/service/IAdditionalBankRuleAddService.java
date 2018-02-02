package com.amg.exchange.remittance.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.FlexFiledView;

public interface IAdditionalBankRuleAddService {
	public void save(AdditionalBankRuleAddData additionalBankRuleAddData) throws Exception;

	public List<AdditionalBankRuleAddData> getDBCountryFlexBank(BigDecimal countryId, String FlexId, BigDecimal bankId);

	public List<AdditionalBankRuleAddData> getAdditionalBankList();

	public List<AdditionalBankRuleAddData> getBankDescription(String bankCode);

	// get AdditionalBankRuleAddData data for Approval
	public List<AdditionalBankRuleAddData> getDataForApproval();
	public List<AdditionalBankRuleAddData> getDataForApprovalList(BigDecimal countryId,String flexFiled,BigDecimal bankId);

	// approve AmiecAndBankMapping record
	public String approveRecord(BigDecimal addtionalBankRulePk, String userName);
	public String approveRecord(List<BigDecimal> addtionalBankRulePk, String userName);

	// get AdditionalBankRuleAmiec data for Approval
	public List<AdditionalBankRuleAmiec> getDataForApprovalForAmMulla();
	public List<AdditionalBankRuleAmiec> getDataForApprovalForAmMulla(BigDecimal countryId, String FlexField);

	// approve AlmullaBankMapping record
	public String approveRecordForAlmulla(BigDecimal addtionalBankRulePk, String userName);

	// get AdditionalBankRuleMap data for Approval
	public List<AdditionalBankRuleMap> getDataForApprovalForBankRuleMap();

	// approve AdditionalBankRuleMap record
	public String approveRecordForBankRuleMap(BigDecimal addtionalBankRuleMapPk, String userName);

	public List<FlexFiledView> getflexiFieldList();
	
 
	
	
}
