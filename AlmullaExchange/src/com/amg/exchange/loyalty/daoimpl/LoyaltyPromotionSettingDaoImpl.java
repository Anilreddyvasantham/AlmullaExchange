package com.amg.exchange.loyalty.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.loyalty.dao.ILoyaltyPromotionSettingDao;
import com.amg.exchange.loyalty.model.LoyaltyParameterSetting;
import com.amg.exchange.loyalty.model.LoyaltyPromotionSettings;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;

@Repository
@SuppressWarnings({ "unchecked" })
public class LoyaltyPromotionSettingDaoImpl<T> extends CommonDaoImpl<T> implements ILoyaltyPromotionSettingDao<T> {

	

	  @Override
	  public void saveAndUpdateLoyaltyPromotionSettings(LoyaltyPromotionSettings loyaltyPromotionSettings) {
		    getSession().saveOrUpdate(loyaltyPromotionSettings);

	  }

	  @Override
	  public List<LoyaltyPromotionSettings> displayAllLoyaltyPromotionSettingToView(BigDecimal loyaltyParameterId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyPromotionSettings.class, "loyaltyPromotionSettings");
		    criteria.setFetchMode("loyaltyPromotionSettings.loyaltyParameterId", FetchMode.JOIN);
		    criteria.createAlias("loyaltyPromotionSettings.loyaltyParameterId", "loyaltyParameterId", JoinType.INNER_JOIN);
		    
		    criteria.setFetchMode("loyaltyPromotionSettings.branchId", FetchMode.JOIN);
		    criteria.createAlias("loyaltyPromotionSettings.branchId", "branchId", JoinType.INNER_JOIN);
		    
		    criteria.add(Restrictions.eq("loyaltyParameterId.loyaltyParameterId", loyaltyParameterId));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    criteria.getExecutableCriteria(getSession()).setCacheable(true);
		    List<LoyaltyPromotionSettings> objList = (List<LoyaltyPromotionSettings>) findAll(criteria);
		    List<?> list = CommonUtil.nullCheck(objList);
		    return (List<LoyaltyPromotionSettings>) list;
	  }

	  @Override
	  public void activateLoyaltyPromotionSettings(BigDecimal loyaltyCatagoryId, String userName) {
		    LoyaltyPromotionSettings loyaltyPromotionSettings = (LoyaltyPromotionSettings) getSession().get(LoyaltyPromotionSettings.class, loyaltyCatagoryId);
		    loyaltyPromotionSettings.setIsActive(Constants.U);
		    loyaltyPromotionSettings.setModifiedBy(userName);
		    loyaltyPromotionSettings.setModifiedDate(new Date());
		    loyaltyPromotionSettings.setApprovedBy(null);
		    loyaltyPromotionSettings.setApprovedDate(null);
		    loyaltyPromotionSettings.setRemarks(null);
		    getSession().update(loyaltyPromotionSettings);

	  }

	  @Override
	  public void deleteLoyaltyPromotionSettings(BigDecimal loyaltyCatagoryId) {
		    LoyaltyPromotionSettings loyaltyPromotionSettings = (LoyaltyPromotionSettings) getSession().get(LoyaltyPromotionSettings.class, loyaltyCatagoryId);
		    getSession().delete(loyaltyPromotionSettings);

	  }

	  @Override
	  public List<LoyaltyPromotionSettings> displayLoyaltyCatagoryBasedOnCatagoryCode(String categoryCode) {
		    // TODO Auto-generated method stub
		    return null;
	  }

	  @Override
	  public List<CountryBranch> getCountryBranchList(BigDecimal appCountryId) {

		    DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");

		    criteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		    criteria.createAlias("countryBranch.countryMaster", "countryMaster", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("countryMaster.countryId", appCountryId));

		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		    List<CountryBranch> objList = (List<CountryBranch>) findAll(criteria);

		    System.out.println("" + objList.size());

		    if (objList.isEmpty())
			      return null;

		    return objList;
	  }

	  @Override
	  public List<LoyaltyParameterSetting> getLoyaltyParameterList(BigDecimal appCountryId) {

		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyParameterSetting.class, "loyaltyParameterSetting");

		    criteria.setFetchMode("loyaltyParameterSetting.applicationCountry", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.applicationCountry", "applicationCountry", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("applicationCountry.countryId", appCountryId));

		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		    List<LoyaltyParameterSetting> objList = (List<LoyaltyParameterSetting>) findAll(criteria);

		    System.out.println("" + objList.size());

		    if (objList.isEmpty())
			      return null;

		    return objList;
	  }

	  @Override
	  public List<String> toFetchAllLoyaltyTemplateCode(String query) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyPromotionSettings.class, "loyaltyPromotionSettings");
			criteria.add(Restrictions.like("loyaltyPromotionSettings.templateCode", query, MatchMode.ANYWHERE).ignoreCase());
			criteria.setProjection(Projections.property("loyaltyPromotionSettings.templateCode"));
			criteria.addOrder(Order.asc("loyaltyPromotionSettings.templateCode"));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<String> lstLoyaltyPromotionSettings = (List<String>) findAll(criteria);
			return lstLoyaltyPromotionSettings;
	  }

	  @Override
	  public List<LoyaltyPromotionSettings> toCompareTheTemplateCode(String templateCode) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyPromotionSettings.class, "loyaltyPromotionSettings");
			criteria.add(Restrictions.eq("loyaltyPromotionSettings.templateCode", templateCode));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<LoyaltyPromotionSettings> lstLoyaltyPromotionSettings = (List<LoyaltyPromotionSettings>) findAll(criteria);
			return lstLoyaltyPromotionSettings;
	  }

	  @Override
	  public void upDateActiveRecordtoDeActive(BigDecimal loyaltyPromotionId, String remarks, String userName) {
		    LoyaltyPromotionSettings loyaltyPromotionSettings = (LoyaltyPromotionSettings) getSession().get(LoyaltyPromotionSettings.class, loyaltyPromotionId);
		    loyaltyPromotionSettings.setRemarks(remarks);
		    loyaltyPromotionSettings.setModifiedBy(userName);
		    loyaltyPromotionSettings.setModifiedDate(new Date());
		    loyaltyPromotionSettings.setApprovedBy(null);
		    loyaltyPromotionSettings.setApprovedDate(null);
		    loyaltyPromotionSettings.setIsActive(Constants.D);
		    getSession().update(loyaltyPromotionSettings);
	  }

	  @Override
	  public List<LoyaltyPromotionSettings> displayAllLoyaltyPromotionSettingToApprovalDtataTable() {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyPromotionSettings.class, "loyaltyPromotionSettings");
		    criteria.setFetchMode("loyaltyPromotionSettings.applicationCountryId", FetchMode.JOIN);
		    criteria.createAlias("loyaltyPromotionSettings.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		    criteria.setFetchMode("loyaltyPromotionSettings.loyaltyParameterId", FetchMode.JOIN);
		    criteria.createAlias("loyaltyPromotionSettings.loyaltyParameterId", "loyaltyParameterId", JoinType.INNER_JOIN);
		    
		    criteria.setFetchMode("loyaltyPromotionSettings.branchId", FetchMode.JOIN);
		    criteria.createAlias("loyaltyPromotionSettings.branchId", "branchId", JoinType.INNER_JOIN);
		    
		    
		    criteria.add(Restrictions.eq("loyaltyPromotionSettings.isActive", Constants.U));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<LoyaltyPromotionSettings> lstLoyaltyPromotionSettings = (List<LoyaltyPromotionSettings>) findAll(criteria);
			return lstLoyaltyPromotionSettings;
	  }

	  @Override
	  public String checkLoyaltyPromotionApproveMultiUser(BigDecimal loyaltyPromotionId, String userName) {
		    String approvalMsg;
		    LoyaltyPromotionSettings loyaltyPromotionSettings = (LoyaltyPromotionSettings) getSession().get(LoyaltyPromotionSettings.class, loyaltyPromotionId);
		    String approvalUser = loyaltyPromotionSettings.getApprovedBy();
		    if (approvalUser == null) {
			      loyaltyPromotionSettings.setIsActive(Constants.Yes);
			      loyaltyPromotionSettings.setApprovedBy(userName);
			      loyaltyPromotionSettings.setApprovedDate(new Date());
			      getSession().update(loyaltyPromotionSettings);
			      approvalMsg = "Success";
		    } else {
			      approvalMsg = "Fail";
		    }
		    return approvalMsg;
	  }

}
