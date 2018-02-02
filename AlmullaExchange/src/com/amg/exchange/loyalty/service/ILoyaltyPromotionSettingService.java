package com.amg.exchange.loyalty.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.loyalty.model.LoyaltyParameterSetting;
import com.amg.exchange.loyalty.model.LoyaltyPromotionSettings;
import com.amg.exchange.registration.model.CountryBranch;

public interface ILoyaltyPromotionSettingService<T> {

	  public List<CountryBranch> getCountryBranchList(BigDecimal appCountryId);

	  public List<LoyaltyParameterSetting> getLoyaltyParameterList(BigDecimal appCountryId);

	  public void saveAndUpdateLoyaltyPromotionSettings(LoyaltyPromotionSettings loyaltyPromotionSettings);

	  public List<LoyaltyPromotionSettings> displayAllLoyaltyPromotionSettingToView(BigDecimal loyaltyParameterId);

	  public void activateLoyaltyPromotionSettings(BigDecimal loyaltyCatagoryId, String userName);

	  public void deleteLoyaltyPromotionSettings(BigDecimal loyaltyCatagoryId);

	  public List<LoyaltyPromotionSettings> displayLoyaltyCatagoryBasedOnCatagoryCode(String categoryCode);

	  

	 
	  public List<String> toFetchAllLoyaltyTemplateCode(String query);

	  public List<LoyaltyPromotionSettings> toCompareTheTemplateCode(String templateCode);

	  public void upDateActiveRecordtoDeActive(BigDecimal loyaltyPromotionId, String remarks, String userName);

	  public List<LoyaltyPromotionSettings> displayAllLoyaltyPromotionSettingToApprovalDtataTable();

	  public String checkLoyaltyPromotionApproveMultiUser(BigDecimal loyaltyPromotionId, String userName);

}
