package com.amg.exchange.remittance.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AmiecAndBankMapping;
import com.amg.exchange.treasury.model.BankMaster;

public interface IAmiecAndBankMappingDao {

	public void save(AmiecAndBankMapping amiecAndBankMapping);
	
	public List<AmiecAndBankMapping> getAllAdditionalBankDataList();
	
	public List<AmiecAndBankMapping> getFlexFieldByCountry(BigDecimal countryId, BigDecimal bankId, String flexField);
	
	public List<AmiecAndBankMapping> getAmiecCodeByCountry(BigDecimal countryId, BigDecimal bankId, String amiecCode);
	
	public List<AmiecAndBankMapping> getBankCodeByCountry(BigDecimal countryId, BigDecimal bankId, String bankCode);
	
	public List<AmiecAndBankMapping>  getBankAmiecAndBankMapping();
	
	public List<AdditionalBankRuleAmiec> getAmielist(BigDecimal countryId,String flexId);
	
	public List<AdditionalBankRuleAddData> getListBankCode(BigDecimal countryId,BigDecimal bankId,String flexId);
	
	public List<AdditionalBankRuleAmiec> getListDesc(String amieCode);
	
	

	//to fetch data from AmiecAndBankMapping tabale for Approval
	public List<AmiecAndBankMapping> getDataForApproval();
	
	//to get flexi field Name based on field Value
		public String getFlexFieldName(String fieldValue);
		
		//approve AmiecAndBankMapping record
		public String approveRecord(BigDecimal amiecPk,String userName);
		
		public List<BankMaster> listofBanks(BigDecimal coutryId);
		
	   public String filedName(String fieldsId,BigDecimal countryId);
		   
	   //Added by kani for Enquiry Screen begin
		public List<AmiecAndBankMapping>  getBankAmiecAndBankMappingEnquiry();
	   
	   // Added by kani for Enquiry Screen end
	   
		public void activateRecord(BigDecimal bankAmiecPk,String userName);
		public void deleteRecord(BigDecimal bankAmiecPk);
		public void remarkRecord(BigDecimal bankAmiecPk,String remarkedText,String userName);

		public List<AmiecAndBankMapping> dupliacteRecordCheckInDB(BigDecimal countryId,BigDecimal bankId,String flexField,String amiecCode,String bankCode);
}
