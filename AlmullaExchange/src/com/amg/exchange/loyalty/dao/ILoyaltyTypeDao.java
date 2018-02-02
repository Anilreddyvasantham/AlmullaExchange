package com.amg.exchange.loyalty.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.loyalty.model.LoyaltyType;
import com.amg.exchange.loyalty.model.LoyaltyTypeDesc;
import com.amg.exchange.util.AMGException;

public interface ILoyaltyTypeDao<T> {
	
	public List<LoyaltyType> displayList(String loyaltyType,BigDecimal applicationCountryId);
	
	public List<LoyaltyType> displayListById(BigDecimal id);
	
	public List<LoyaltyType> displayListByCode(String code);
	
	public List<LoyaltyTypeDesc> getDescriptionById(BigDecimal id);		
	
	public void saveMaster(LoyaltyType loyalityType);
	
	public void saveMasterDescriptionEnglish(LoyaltyTypeDesc loyalityTypeDesc);
	
	public void saveMasterDescriptionLocal(LoyaltyTypeDesc loyalityTypeDesc);
	
	public List<String> autoCompleteList(String query);
	
	public void activate(BigDecimal id, String userName);
	
	public void deleteLoyaltyType(BigDecimal loyaltyTypeId, BigDecimal loyaltyTypeDescId, BigDecimal loyaltyTypeArabicDescId);
	
	public void saveLoyalty(HashMap<String , Object> saveMapInfo) throws AMGException;

}
