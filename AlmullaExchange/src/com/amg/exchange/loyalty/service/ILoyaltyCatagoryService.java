package com.amg.exchange.loyalty.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.loyalty.model.LoyaltyCatagoryMaster;
import com.amg.exchange.loyalty.model.LoyaltyCatergoryMasterDesc;
import com.amg.exchange.loyalty.model.LoyaltyTypeDesc;
import com.amg.exchange.util.AMGException;

public interface ILoyaltyCatagoryService<T> {
	

	public void saveAndUpdateLoyaltyCatagoryMaster(LoyaltyCatagoryMaster loyaltyCatagoryMaster);
	public void saveAndUpdateLoyaltyCatergoryMasterDesc(LoyaltyCatergoryMasterDesc loyaltyCatergoryMasterDesc);
	public Boolean isCatagoryCodeExist(String categoryCode);
	public List<LoyaltyCatergoryMasterDesc> displayAllLoyaltyCatergoryMasterDesc(BigDecimal loyaltyCatagoryId);
	public List<LoyaltyCatagoryMaster> displayAllLoyaltyCatagoryToView(BigDecimal applicationCountryId,String categoryType);
	public void activateLoyaltyCatagoryMaster(BigDecimal loyaltyCatagoryId, String userName);
	public void deleteLoyaltyCatagoryMaster(BigDecimal loyaltyCatagoryId, BigDecimal englishDescId, BigDecimal arabicDescId);
	public List<String> autoCompleteList(String query);
	
	public List<LoyaltyCatagoryMaster> displayLoyaltyCatagoryForApprove(BigDecimal appCountryId,String inActive);
	public void approveRecord(BigDecimal loyaltyCatagoryId, String userName,String isActive);
	public List<LoyaltyCatagoryMaster> displayLoyaltyCatagoryBasedOnCatagoryCode(String categoryCode);
	public List<LoyaltyCatergoryMasterDesc> displayLoyaltyCatagoryForLoyaltyCreation(BigDecimal appCountryId , BigDecimal languageId);
	
	public List<LoyaltyTypeDesc> getLoyaltyTypeDescList( BigDecimal languageId);
	
	public List<LoyaltyTypeDesc> getLoyaltyTypeMasterDesc(BigDecimal loyaltyTypeId,BigDecimal languageId);
	
	public List<LoyaltyTypeDesc> getLoyaltyTypeId(String categoryType, BigDecimal languageId);
	
	//partial save applied by nazish 
	public void saveLoyaltyCataegory(HashMap<String , Object> saveMapInfo) throws AMGException;
	

}
