package com.amg.exchange.loyalty.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.loyalty.dao.ILoyaltyParameterDao;
import com.amg.exchange.loyalty.model.LoyaltyCatergoryMasterDesc;
import com.amg.exchange.loyalty.model.LoyaltyParameterSetting;
import com.amg.exchange.loyalty.model.LoyaltyTypeDesc;
import com.amg.exchange.loyalty.service.ILoyaltyParameterService;

@Service("loyaltyParameterService")
@Transactional
public class LoyaltyParameterServiceImpl implements ILoyaltyParameterService {

	  @Autowired
	  ILoyaltyParameterDao loyaltyParameterDao;

	  @Override
	  public List<LoyaltyTypeDesc> toFetchAllLoyaltyTypeDesc(BigDecimal languageId) {
		    return loyaltyParameterDao.toFetchAllLoyaltyTypeDesc(languageId);
	  }

	  @Override
	  public List<LoyaltyCatergoryMasterDesc> toFetchAllLoyalityCatergoryDesc(BigDecimal languageId) {
		    return loyaltyParameterDao.toFetchAllLoyalityCatergoryDesc(languageId);
	  }

	  @Override
	  public void saveLoyaltyParameterSettingAllValues(LoyaltyParameterSetting loyaltyParameterSetting) {
		    loyaltyParameterDao.saveLoyaltyParameterSettingAllValues(loyaltyParameterSetting);
	  }

	  @Override
	  public List<LoyaltyParameterSetting> toFetchAllViewDetails() {
		    return loyaltyParameterDao.toFetchAllViewDetails();
	  }

	  @Override
	  public String toFetchCategoryNameBesedOnCategoryId(BigDecimal loyaltyCatagoryId, BigDecimal languageId) {
		    return loyaltyParameterDao.toFetchCategoryNameBesedOnCategoryId(loyaltyCatagoryId, languageId);
	  }

	  @Override
	  public String toFtechTypeNameBasedOnTypeId(BigDecimal loyalityTypeId, BigDecimal languageId) {
		    return loyaltyParameterDao.toFtechTypeNameBasedOnTypeId(loyalityTypeId, languageId);
	  }

	  @Override
	  public String toFetchCountryNameBasedonCountryId(BigDecimal countryId, BigDecimal languageId) {
		    return loyaltyParameterDao.toFetchCountryNameBasedonCountryId(countryId, languageId);
	  }

	  @Override
	  public String toFetchBankNameBasedonBankId(BigDecimal bankId) {
		    return loyaltyParameterDao.toFetchBankNameBasedonBankId(bankId);
	  }

	  @Override
	  public String toFetchCurrencyNameBasedonCurrencyId(BigDecimal currencyId) {
		    return loyaltyParameterDao.toFetchCurrencyNameBasedonCurrencyId(currencyId);
	  }

	  @Override
	  public String toFetchStateNameBasedonStateId(BigDecimal stateId, BigDecimal languageId) {
		    return loyaltyParameterDao.toFetchStateNameBasedonStateId(stateId, languageId);
	  }

	  @Override
	  public void deleteRecordPermentelyFromLoyaltyParameterSetting(BigDecimal ltyParameterPk) {
		    loyaltyParameterDao.deleteRecordPermentelyFromLoyaltyParameterSetting(ltyParameterPk);
	  }

	  @Override
	  public void upDateActiveRecordtoDeActive(BigDecimal ltyParameterPk, String remarks, String userName) {
		    loyaltyParameterDao.upDateActiveRecordtoDeActive(ltyParameterPk, remarks, userName);
	  }

	  @Override
	  public void DeActiveRecordToPendingForApprovalOfLoyaltyParameterSetting(BigDecimal ltyParameterPk, String userName) {
		    loyaltyParameterDao.DeActiveRecordToPendingForApprovalOfLoyaltyParameterSetting(ltyParameterPk, userName);
	  }

	  @Override
	  public List<LoyaltyParameterSetting> toFetchAllApprovalDetailsFormLoyaltyParameterSetting() {
		    return loyaltyParameterDao.toFetchAllApprovalDetailsFormLoyaltyParameterSetting();
	  }

	  @Override
	  public String checkLoyaltyParameterApproveMultiUser(BigDecimal ltyParameterPk, String userName) {
		    return loyaltyParameterDao.checkLoyaltyParameterApproveMultiUser(ltyParameterPk, userName);
	  }
	  
	  @Override
	public List<LoyaltyParameterSetting> toFetchAllViewDetails(BigDecimal loyaltyTypeId, BigDecimal loyaltyCatagoryId, BigDecimal countryId) {
		  return loyaltyParameterDao.toFetchAllViewDetails(loyaltyTypeId, loyaltyCatagoryId, countryId);
	}

	  @Override
	  public List<String> toFetchAllLoyaltyTemplateCode(String query) {
		    return loyaltyParameterDao.toFetchAllLoyaltyTemplateCode(query);
	  }

	  @Override
	  public List<LoyaltyParameterSetting> toCompareTheTemplateCode(String templateCode) {
		    return loyaltyParameterDao.toCompareTheTemplateCode(templateCode);
	  }
}
