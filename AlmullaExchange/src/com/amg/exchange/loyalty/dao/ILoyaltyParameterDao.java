package com.amg.exchange.loyalty.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.loyalty.model.LoyaltyCatergoryMasterDesc;
import com.amg.exchange.loyalty.model.LoyaltyParameterSetting;
import com.amg.exchange.loyalty.model.LoyaltyTypeDesc;

public interface ILoyaltyParameterDao {

	  public List<LoyaltyTypeDesc> toFetchAllLoyaltyTypeDesc(BigDecimal languageId);

	  public List<LoyaltyCatergoryMasterDesc> toFetchAllLoyalityCatergoryDesc(BigDecimal languageId);

	  public void saveLoyaltyParameterSettingAllValues(LoyaltyParameterSetting loyaltyParameterSetting);

	  public List<LoyaltyParameterSetting> toFetchAllViewDetails();

	  public String toFetchCategoryNameBesedOnCategoryId(BigDecimal loyaltyCatagoryId, BigDecimal languageId);

	  public String toFtechTypeNameBasedOnTypeId(BigDecimal loyalityTypeId, BigDecimal languageId);

	  public String toFetchCountryNameBasedonCountryId(BigDecimal countryId, BigDecimal languageId);

	  public String toFetchBankNameBasedonBankId(BigDecimal bankId);

	  public String toFetchCurrencyNameBasedonCurrencyId(BigDecimal currencyId);

	  public String toFetchStateNameBasedonStateId(BigDecimal stateId, BigDecimal languageId);

	  public void deleteRecordPermentelyFromLoyaltyParameterSetting(BigDecimal ltyParameterPk);

	  public void upDateActiveRecordtoDeActive(BigDecimal ltyParameterPk, String remarks, String userName);

	  public void DeActiveRecordToPendingForApprovalOfLoyaltyParameterSetting(BigDecimal ltyParameterPk, String userName);

	  public List<LoyaltyParameterSetting> toFetchAllApprovalDetailsFormLoyaltyParameterSetting();

	  public String checkLoyaltyParameterApproveMultiUser(BigDecimal ltyParameterPk, String userName);
	  
	  public List<LoyaltyParameterSetting> toFetchAllViewDetails(BigDecimal loyaltyTypeId,BigDecimal loyaltyCatagoryId,BigDecimal countryId);

	  public List<String> toFetchAllLoyaltyTemplateCode(String query);

	  public List<LoyaltyParameterSetting> toCompareTheTemplateCode(String templateCode);

}
