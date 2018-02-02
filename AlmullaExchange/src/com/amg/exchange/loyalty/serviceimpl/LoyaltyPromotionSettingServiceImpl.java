package com.amg.exchange.loyalty.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.loyalty.dao.ILoyaltyParameterDao;
import com.amg.exchange.loyalty.dao.ILoyaltyPromotionSettingDao;
import com.amg.exchange.loyalty.model.LoyaltyParameterSetting;
import com.amg.exchange.loyalty.model.LoyaltyPromotionSettings;
import com.amg.exchange.loyalty.service.ILoyaltyPromotionSettingService;
import com.amg.exchange.registration.model.CountryBranch;

@Service("loyaltyPromotionSettingService")
@Transactional
public class LoyaltyPromotionSettingServiceImpl<T> implements ILoyaltyPromotionSettingService<T> {

	  @Autowired
	  ILoyaltyPromotionSettingDao<T> loyaltyPromotionSettingDao;

	  public ILoyaltyPromotionSettingDao<T> getLoyaltyPromotionSettingDao() {
		    return loyaltyPromotionSettingDao;
	  }

	  public void setLoyaltyPromotionSettingDao(ILoyaltyPromotionSettingDao<T> loyaltyPromotionSettingDao) {
		    this.loyaltyPromotionSettingDao = loyaltyPromotionSettingDao;
	  }

	  @Override
	  public void saveAndUpdateLoyaltyPromotionSettings(LoyaltyPromotionSettings loyaltyPromotionSettings) {
		    getLoyaltyPromotionSettingDao().saveAndUpdateLoyaltyPromotionSettings(loyaltyPromotionSettings);

	  }

	  @Override
	  public List<CountryBranch> getCountryBranchList(BigDecimal appCountryId) {

		    return getLoyaltyPromotionSettingDao().getCountryBranchList(appCountryId);
	  }

	  @Override
	  public List<LoyaltyParameterSetting> getLoyaltyParameterList(BigDecimal appCountryId) {
		    return getLoyaltyPromotionSettingDao().getLoyaltyParameterList(appCountryId);
	  }

	  @Override
	  public List<LoyaltyPromotionSettings> displayAllLoyaltyPromotionSettingToView(BigDecimal loyaltyParameterId) {
		    return getLoyaltyPromotionSettingDao().displayAllLoyaltyPromotionSettingToView(loyaltyParameterId);
	  }

	  @Override
	  public void activateLoyaltyPromotionSettings(BigDecimal loyaltyCatagoryId, String userName) {
		    loyaltyPromotionSettingDao.activateLoyaltyPromotionSettings(loyaltyCatagoryId,userName);
	  }

	  @Override
	  public void deleteLoyaltyPromotionSettings(BigDecimal loyaltyCatagoryId) {
		    loyaltyPromotionSettingDao.deleteLoyaltyPromotionSettings(loyaltyCatagoryId);
	  }

	  @Override
	  public List<LoyaltyPromotionSettings> displayLoyaltyCatagoryBasedOnCatagoryCode(String categoryCode) {
		    // TODO Auto-generated method stub
		    return null;
	  }

	  @Override
	  public List<String> toFetchAllLoyaltyTemplateCode(String query) {
		    return loyaltyPromotionSettingDao.toFetchAllLoyaltyTemplateCode(query);
	  }

	  @Override
	  public List<LoyaltyPromotionSettings> toCompareTheTemplateCode(String templateCode) {
		    return loyaltyPromotionSettingDao.toCompareTheTemplateCode(templateCode);
	  }

	  @Override
	  public void upDateActiveRecordtoDeActive(BigDecimal loyaltyPromotionId, String remarks, String userName) {
		    loyaltyPromotionSettingDao.upDateActiveRecordtoDeActive(loyaltyPromotionId,remarks,userName);  
	  }

	  @Override
	  public List<LoyaltyPromotionSettings> displayAllLoyaltyPromotionSettingToApprovalDtataTable() {
		    return loyaltyPromotionSettingDao.displayAllLoyaltyPromotionSettingToApprovalDtataTable();
	  }

	  @Override
	  public String checkLoyaltyPromotionApproveMultiUser(BigDecimal loyaltyPromotionId, String userName) {
		    return loyaltyPromotionSettingDao.checkLoyaltyPromotionApproveMultiUser(loyaltyPromotionId,userName);
	  }

	  

	  
}
