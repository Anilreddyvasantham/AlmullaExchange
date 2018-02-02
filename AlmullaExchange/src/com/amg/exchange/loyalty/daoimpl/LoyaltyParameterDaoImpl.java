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
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.loyalty.dao.ILoyaltyParameterDao;
import com.amg.exchange.loyalty.model.LoyaltyCatergoryMasterDesc;
import com.amg.exchange.loyalty.model.LoyaltyParameterSetting;
import com.amg.exchange.loyalty.model.LoyaltyPromotionSettings;
import com.amg.exchange.loyalty.model.LoyaltyTypeDesc;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.Constants;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Repository
public class LoyaltyParameterDaoImpl extends CommonDaoImpl implements ILoyaltyParameterDao {

	  @Override
	  public List<LoyaltyTypeDesc> toFetchAllLoyaltyTypeDesc(BigDecimal languageId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyTypeDesc.class, "loyalityTypeDesc");
		    criteria.setFetchMode("loyalityTypeDesc.languageId", FetchMode.JOIN);
		    criteria.createAlias("loyalityTypeDesc.languageId", "languageId", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("languageId.languageId", languageId));
		    
		    criteria.setFetchMode("loyalityTypeDesc.loyalityTypeId", FetchMode.JOIN);
		    criteria.createAlias("loyalityTypeDesc.loyalityTypeId", "loyalityTypeId", JoinType.INNER_JOIN);
		   
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<LoyaltyTypeDesc> lstLoyalityTypeDescs = (List<LoyaltyTypeDesc>) findAll(criteria);
		    return lstLoyalityTypeDescs;
	  }

	  @Override
	  public List<LoyaltyCatergoryMasterDesc> toFetchAllLoyalityCatergoryDesc(BigDecimal languageId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyCatergoryMasterDesc.class, "loyaltyCatergoryMasterDesc");
		    criteria.setFetchMode("loyaltyCatergoryMasterDesc.languageId", FetchMode.JOIN);
		    criteria.createAlias("loyaltyCatergoryMasterDesc.languageId", "languageId", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("languageId.languageId", languageId));
		    
		    criteria.setFetchMode("loyaltyCatergoryMasterDesc.loyaltyCategoryId", FetchMode.JOIN);
		    criteria.createAlias("loyaltyCatergoryMasterDesc.loyaltyCategoryId", "loyaltyCategoryId", JoinType.INNER_JOIN);
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<LoyaltyCatergoryMasterDesc> lstLoyaltyCatergoryMasterDesc = (List<LoyaltyCatergoryMasterDesc>) findAll(criteria);
		    return lstLoyaltyCatergoryMasterDesc;
	  }

	  @Override
	  public void saveLoyaltyParameterSettingAllValues(LoyaltyParameterSetting loyaltyParameterSetting) {
		    getSession().saveOrUpdate(loyaltyParameterSetting);
	  }

	  @Override
	  public List<LoyaltyParameterSetting> toFetchAllViewDetails() {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyParameterSetting.class, "loyaltyParameterSetting");
		    // Category Master
		    criteria.setFetchMode("loyaltyParameterSetting.loyaltyCatagoryMaster", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.loyaltyCatagoryMaster", "loyaltyCatagoryMaster", JoinType.INNER_JOIN);
		    // TypeMaster
		    criteria.setFetchMode("loyaltyParameterSetting.loyaltyType", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.loyaltyType", "loyaltyType", JoinType.INNER_JOIN);
		    // Country Master
		    criteria.setFetchMode("loyaltyParameterSetting.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    // State Master
		    criteria.setFetchMode("loyaltyParameterSetting.fsStateMaster", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		    // Bank Master
		    criteria.setFetchMode("loyaltyParameterSetting.exBankMaster", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		    // Currency Master
		    criteria.setFetchMode("loyaltyParameterSetting.exCurrencyMaster", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);
		    // Application Country
		    criteria.setFetchMode("loyaltyParameterSetting.applicationCountry", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.applicationCountry", "applicationCountry", JoinType.INNER_JOIN);
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<LoyaltyParameterSetting> lstLoyaltyParameterSetting = (List<LoyaltyParameterSetting>) findAll(criteria);
		    return lstLoyaltyParameterSetting;
	  }

	  @Override
	  public String toFetchCategoryNameBesedOnCategoryId(BigDecimal loyaltyCatagoryId, BigDecimal languageId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyCatergoryMasterDesc.class, "loyaltyCatergoryMasterDesc");
		    criteria.setFetchMode("loyaltyCatergoryMasterDesc.loyaltyCategoryId", FetchMode.JOIN);
		    criteria.createAlias("loyaltyCatergoryMasterDesc.loyaltyCategoryId", "loyaltyCategoryId", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("loyaltyCategoryId.loyaltyCatagoryId", loyaltyCatagoryId));
		    criteria.setFetchMode("loyaltyCatergoryMasterDesc.languageId", FetchMode.JOIN);
		    criteria.createAlias("loyaltyCatergoryMasterDesc.languageId", "languageId", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("languageId.languageId", languageId));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<LoyaltyCatergoryMasterDesc> lstLoyaltyCatergoryMasterDesc = (List<LoyaltyCatergoryMasterDesc>) findAll(criteria);
		    String fullDesc = null;
		    if(lstLoyaltyCatergoryMasterDesc != null && lstLoyaltyCatergoryMasterDesc.size() !=0){
			      fullDesc=lstLoyaltyCatergoryMasterDesc.get(0).getFullDesc();
		    }
		    return fullDesc;
	  }

	  @Override
	  public String toFtechTypeNameBasedOnTypeId(BigDecimal loyalityTypeId, BigDecimal languageId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyTypeDesc.class, "loyalityTypeDesc");
		    criteria.setFetchMode("loyalityTypeDesc.loyalityTypeId", FetchMode.JOIN);
		    criteria.createAlias("loyalityTypeDesc.loyalityTypeId", "loyalityTypeId", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("loyalityTypeId.loyalityTypeId", loyalityTypeId));
		    criteria.setFetchMode("loyalityTypeDesc.languageId", FetchMode.JOIN);
		    criteria.createAlias("loyalityTypeDesc.languageId", "languageId", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("languageId.languageId", languageId));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<LoyaltyTypeDesc> lstLoyalityTypeDescs = (List<LoyaltyTypeDesc>) findAll(criteria);
		    String fullDesc = null;
		    if (lstLoyalityTypeDescs.size() > 0) {
			      fullDesc = lstLoyalityTypeDescs.get(0).getFullDescription();
		    }
		    return fullDesc;
	  }

	  @Override
	  public String toFetchCountryNameBasedonCountryId(BigDecimal countryId, BigDecimal languageId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		    criteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		    criteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		    criteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<CountryMasterDesc> lstCountryMasterDesc = (List<CountryMasterDesc>) findAll(criteria);
		    String countryName = null;
		    if (lstCountryMasterDesc.size() > 0) {
			      countryName = lstCountryMasterDesc.get(0).getCountryName();
		    }
		    return countryName;
	  }

	  @Override
	  public String toFetchBankNameBasedonBankId(BigDecimal bankId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		    criteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<BankMaster> lstBankMaster = (List<BankMaster>) findAll(criteria);
		    String bankName = null;
		    if (lstBankMaster.size() > 0) {
			      bankName = lstBankMaster.get(0).getBankFullName();
		    }
		    return bankName;
	  }

	  @Override
	  public String toFetchCurrencyNameBasedonCurrencyId(BigDecimal currencyId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		    criteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<CurrencyMaster> lstCurrencyMaster = (List<CurrencyMaster>) findAll(criteria);
		    String currencyName = null;
		    if (lstCurrencyMaster.size() > 0) {
			      currencyName = lstCurrencyMaster.get(0).getCurrencyName();
		    }
		    return currencyName;
	  }

	  @Override
	  public String toFetchStateNameBasedonStateId(BigDecimal stateId, BigDecimal languageId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		    criteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsStateMaster.stateId", stateId));
		    criteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<StateMasterDesc> lstStateMasterDesc = (List<StateMasterDesc>) findAll(criteria);
		    String stateName = null;
		    if (lstStateMasterDesc.size() > 0) {
			      stateName = lstStateMasterDesc.get(0).getStateName();
		    }
		    return stateName;
	  }

	  @Override
	  public void deleteRecordPermentelyFromLoyaltyParameterSetting(BigDecimal ltyParameterPk) {
		    LoyaltyParameterSetting loyaltyParameterSetting = (LoyaltyParameterSetting) getSession().get(LoyaltyParameterSetting.class, ltyParameterPk);
		    getSession().delete(loyaltyParameterSetting);
	  }

	  @Override
	  public void upDateActiveRecordtoDeActive(BigDecimal ltyParameterPk, String remarks, String userName) {
		    LoyaltyParameterSetting loyaltyParameterSetting = (LoyaltyParameterSetting) getSession().get(LoyaltyParameterSetting.class, ltyParameterPk);
		    loyaltyParameterSetting.setRemarks(remarks);
		    loyaltyParameterSetting.setModifiedBy(userName);
		    loyaltyParameterSetting.setModifiedDate(new Date());
		    loyaltyParameterSetting.setApprovedBy(null);
		    loyaltyParameterSetting.setApprovedDate(null);
		    loyaltyParameterSetting.setIsActive(Constants.D);
		    getSession().update(loyaltyParameterSetting);
	  }

	  @Override
	  public void DeActiveRecordToPendingForApprovalOfLoyaltyParameterSetting(BigDecimal ltyParameterPk, String userName) {
		    LoyaltyParameterSetting loyaltyParameterSetting = (LoyaltyParameterSetting) getSession().get(LoyaltyParameterSetting.class, ltyParameterPk);
		    loyaltyParameterSetting.setModifiedBy(userName);
		    loyaltyParameterSetting.setModifiedDate(new Date());
		    loyaltyParameterSetting.setApprovedBy(null);
		    loyaltyParameterSetting.setApprovedDate(null);
		    loyaltyParameterSetting.setRemarks(null);
		    loyaltyParameterSetting.setIsActive(Constants.U);
		    getSession().update(loyaltyParameterSetting);
	  }

	  @Override
	  public List<LoyaltyParameterSetting> toFetchAllApprovalDetailsFormLoyaltyParameterSetting() {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyParameterSetting.class, "loyaltyParameterSetting");
		    // Category Master
		    criteria.setFetchMode("loyaltyParameterSetting.loyaltyCatagoryMaster", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.loyaltyCatagoryMaster", "loyaltyCatagoryMaster", JoinType.INNER_JOIN);
		    // TypeMaster
		    criteria.setFetchMode("loyaltyParameterSetting.loyaltyType", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.loyaltyType", "loyaltyType", JoinType.INNER_JOIN);
		    // Country Master
		    criteria.setFetchMode("loyaltyParameterSetting.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    // State Master
		    criteria.setFetchMode("loyaltyParameterSetting.fsStateMaster", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		    // Bank Master
		    criteria.setFetchMode("loyaltyParameterSetting.exBankMaster", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		    // Currency Master
		    criteria.setFetchMode("loyaltyParameterSetting.exCurrencyMaster", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);
		    // Application Country
		    criteria.setFetchMode("loyaltyParameterSetting.applicationCountry", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.applicationCountry", "applicationCountry", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("loyaltyParameterSetting.isActive", Constants.U));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<LoyaltyParameterSetting> lstLoyaltyParameterSetting = (List<LoyaltyParameterSetting>) findAll(criteria);
		    return lstLoyaltyParameterSetting;
	  }

	  @Override
	  public String checkLoyaltyParameterApproveMultiUser(BigDecimal ltyParameterPk, String userName) {
		    String approvalMsg;
		    LoyaltyParameterSetting loyaltyParameterSetting = (LoyaltyParameterSetting) getSession().get(LoyaltyParameterSetting.class, ltyParameterPk);
		    String approvalUser = loyaltyParameterSetting.getApprovedBy();
		    if (approvalUser == null) {
			      loyaltyParameterSetting.setIsActive(Constants.Yes);
			      loyaltyParameterSetting.setApprovedBy(userName);
			      loyaltyParameterSetting.setApprovedDate(new Date());
			      getSession().update(loyaltyParameterSetting);
			      approvalMsg = "Success";
		    } else {
			      approvalMsg = "Fail";
		    }
		    return approvalMsg;
	  }
	  @Override
	  public List<LoyaltyParameterSetting> toFetchAllViewDetails(BigDecimal loyaltyTypeId,BigDecimal loyaltyCatagoryId,BigDecimal countryId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyParameterSetting.class, "loyaltyParameterSetting");
		    // Category Master
		    criteria.setFetchMode("loyaltyParameterSetting.loyaltyCatagoryMaster", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.loyaltyCatagoryMaster", "loyaltyCatagoryMaster", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("loyaltyCatagoryMaster.loyaltyCatagoryId", loyaltyCatagoryId));
		    
		    // TypeMaster
		    criteria.setFetchMode("loyaltyParameterSetting.loyaltyType", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.loyaltyType", "loyaltyType", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("loyaltyType.loyalityTypeId", loyaltyTypeId));
		    // Country Master
		    criteria.setFetchMode("loyaltyParameterSetting.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		    // State Master
		    criteria.setFetchMode("loyaltyParameterSetting.fsStateMaster", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		    // Bank Master
		    criteria.setFetchMode("loyaltyParameterSetting.exBankMaster", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		    // Currency Master
		    criteria.setFetchMode("loyaltyParameterSetting.exCurrencyMaster", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);
		    // Application Country
		    criteria.setFetchMode("loyaltyParameterSetting.applicationCountry", FetchMode.JOIN);
		    criteria.createAlias("loyaltyParameterSetting.applicationCountry", "applicationCountry", JoinType.INNER_JOIN);
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<LoyaltyParameterSetting> lstLoyaltyParameterSetting = (List<LoyaltyParameterSetting>) findAll(criteria);
		    return lstLoyaltyParameterSetting;
	  }

	  @Override
	  public List<String> toFetchAllLoyaltyTemplateCode(String query) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyParameterSetting.class, "loyaltyPromotionSettings");
			criteria.add(Restrictions.like("loyaltyPromotionSettings.templateCode", query, MatchMode.START).ignoreCase());
			criteria.setProjection(Projections.property("loyaltyPromotionSettings.templateCode"));
			criteria.addOrder(Order.asc("loyaltyPromotionSettings.templateCode"));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<String> lstLoyaltyPromotionSettings = (List<String>) findAll(criteria);
			return lstLoyaltyPromotionSettings;
	  }

	  @Override
	  public List<LoyaltyParameterSetting> toCompareTheTemplateCode(String templateCode) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyParameterSetting.class, "loyaltyParameterSetting");
					criteria.add(Restrictions.eq("loyaltyParameterSetting.templateCode", templateCode));
					criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
					List<LoyaltyParameterSetting> lstLoyaltyParameterSetting = (List<LoyaltyParameterSetting>) findAll(criteria);
					return lstLoyaltyParameterSetting;
	  }

}
