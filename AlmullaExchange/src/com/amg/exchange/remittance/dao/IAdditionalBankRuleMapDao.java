package com.amg.exchange.remittance.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.dto.BankMasterDTO;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.remittance.model.AdditionalBankDetailsView;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AdditionalBankRuleFlexFieldView;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.AmiecAndBankMapping;

public interface IAdditionalBankRuleMapDao {

	public void save(AdditionalBankRuleMap additionalBankRuleMap) throws Exception;

	public List<AdditionalBankRuleMap> getAllFlexField();

	public List<AdditionalBankRuleMap> getAdditionalFlexField(BigDecimal countryId);

	//for getting all AdditionalBankRuleMap records for view
	public List<AdditionalBankRuleMap> getAdditionBankRuleMapRecordsForView();

	//for getting all AdditionalBankRuleAmiec records for view
	public List<AdditionalBankRuleAmiec> getAdditionBankAlmullaCodeRecordsForView(BigDecimal countryId,String flexfield);

	//for getting all AdditionalBankRuleAmiec records for view
	public List<AdditionalBankRuleAddData> getAdditionBankRuleRecordsForView(BigDecimal countryId,String flexifieild,BigDecimal bankId);

	//Added by kani for Enquiry Screen Begin 
	public List<AdditionalBankRuleMap> getAdditionBankRuleMapRecordsForEnquiry();
	public List<AdditionalBankRuleAmiec> getAdditionBankAlmullaCodeRecordsForEnquiry();

	public List<AdditionalBankRuleAddData> getAdditionBankRuleRecordsForEnquiry();
	//Added by kani for Enquiry Screen End 


	public void activateBankRuleMapRecord(BigDecimal bankRuleMapPk,String userName);
	public void deleteBankRuleMapRecord(BigDecimal bankRuleMapPk);
	public void remarkBankRuleMapRecord(BigDecimal bankRuleMapPk,String remarkedText,String userName);

	public void activateBankRuleAlmullaCode(BigDecimal bankRuleMapPk,String userName);
	public void deleteBankRuleAlmullaCode(BigDecimal bankRuleMapPk);
	public void remarkBankRuleAlmullaCode(BigDecimal bankRuleMapPk,String remarkedText,String userName);

	public void activateBankRule(BigDecimal bankRuleMapPk,String userName);
	public void deleteBankRule(BigDecimal bankRuleMapPk);
	public void remarkBankRule(BigDecimal bankRuleMapPk,String remarkedText,String userName);

	public List<AdditionalBankRuleMap> duplicateCheckInDBBankMap(BigDecimal countryId,String bFlexFieldID,String flexFieldID,String flexFieldName,BigDecimal orderNo);
	
	public List<AdditionalBankRuleMap> duplicateCheckInDBBankMap(BigDecimal countryId,String bFlexFieldID,String flexFieldName,BigDecimal orderNo);
	
	public List<AdditionalBankRuleAmiec> duplicateCheckInDBBankAlmullaCode(BigDecimal countryId,String flexFieldID,String amiecCode,String amiecDescription);
	public List<AdditionalBankRuleAddData> duplicateCheckInDBBankRule(BigDecimal countryId,String flexFieldID,BigDecimal bankId,String additionalData,String additionalDataDesc);
	public List<CountryMasterDesc> getCountryList(BigDecimal languageId);
	public List<BankMasterDTO> getBanlList(BigDecimal countryId,String flexField);
	public List<AdditionalBankDetailsView> getAmiecDetails(BigDecimal countryId,String flexField);
	public List<AdditionalBankRuleAddData> getBankDetails(BigDecimal bankId);
	public String getBankCode(String bankDesc);
	public List<AdditionalBankRuleAddData> getAllBankDetails();
	public List<AmiecAndBankMapping> fetchDataFromAmiecTable(BigDecimal countryId, String flexField, String amiecCode,BigDecimal bankId);

	public List<AmiecAndBankMapping>   checkRecordAvailableInAmiecAndBankMappingTable(
			BigDecimal countryId, String flexiField, String amiecCode, String amiecDesc,BigDecimal bankId);

	public void updateRecord(List<AmiecAndBankMapping> amiecAndBankMappingUpdateList,String userName);
	public void saveRecord(List<AmiecAndBankMapping> amiecAndBankMappingList);

	public List<AdditionalBankRuleMap> toFetchAdditionBankRuleMapRecordsForView(BigDecimal countryId);

	public List<AdditionalBankRuleMap> tofetchAll(BigDecimal countryId);
	
	// to fetch all flex fields from view V_EX_INDIC 21/12/2015
	public List<AdditionalBankRuleFlexFieldView> tofetchAllFlexFieldsFromView();
	
	// based on country , bank and flexi_field from AdditionalBankRuleAddData
	public List<AdditionalBankRuleAddData> getBankDetailsAndDescription(BigDecimal countryId, BigDecimal bankId, String flexiField);
	
	public List<AmiecAndBankMapping> fetchAllDataFromAmiecTableByCountryFlexField(BigDecimal countryId, String flexField);

	public List<AdditionalBankRuleAddData> fetchAllDataFromAdditionalBankRule(BigDecimal countryId, String flexiField);
	
}
