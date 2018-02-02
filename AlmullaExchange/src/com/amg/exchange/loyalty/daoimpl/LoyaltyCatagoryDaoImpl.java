package com.amg.exchange.loyalty.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
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
import com.amg.exchange.loyalty.dao.ILoyaltyCatagoryDao;
import com.amg.exchange.loyalty.model.LoyaltyCatagoryMaster;
import com.amg.exchange.loyalty.model.LoyaltyCatergoryMasterDesc;
import com.amg.exchange.loyalty.model.LoyaltyTypeDesc;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;
@SuppressWarnings("unchecked")
@Repository
public class LoyaltyCatagoryDaoImpl<T> extends CommonDaoImpl<T> implements Serializable, ILoyaltyCatagoryDao<T> {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void saveAndUpdateLoyaltyCatagoryMaster(LoyaltyCatagoryMaster loyaltyCatagoryMaster) {
		getSession().saveOrUpdate(loyaltyCatagoryMaster);
		
	}

	@Override
	public void saveAndUpdateLoyaltyCatergoryMasterDesc(LoyaltyCatergoryMasterDesc loyaltyCatergoryMasterDesc) {
		getSession().saveOrUpdate(loyaltyCatergoryMasterDesc);
		
	}

	@Override
	public Boolean isCatagoryCodeExist(String categoryCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyCatagoryMaster.class, "loyaltyCatagoryMaster");
		criteria.add(Restrictions.eq("loyaltyCatagoryMaster.categoryCode", categoryCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<LoyaltyCatagoryMaster> objList = (List<LoyaltyCatagoryMaster>) findAll(criteria);
		return CommonUtil.checkSizeOfRecords(objList);
		
	}

	@Override
	public List<LoyaltyCatergoryMasterDesc> displayAllLoyaltyCatergoryMasterDesc(BigDecimal loyaltyCatagoryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyCatergoryMasterDesc.class, "loyaltyCatergoryMasterDesc");
		criteria.setFetchMode("loyaltyCatergoryMasterDesc.loyaltyCategoryId", FetchMode.JOIN);
		criteria.createAlias("loyaltyCatergoryMasterDesc.loyaltyCategoryId", "loyaltyCategoryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("loyaltyCategoryId.loyaltyCatagoryId", loyaltyCatagoryId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		List<LoyaltyCatergoryMasterDesc> objList = (List<LoyaltyCatergoryMasterDesc>) findAll(criteria);
		List<?> list = CommonUtil.nullCheck(objList);
		return (List<LoyaltyCatergoryMasterDesc>) list;
	}

	@Override
	public List<LoyaltyCatagoryMaster> displayAllLoyaltyCatagoryToView(BigDecimal applicationCountryId,String categoryType) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyCatagoryMaster.class, "loyaltyCatagoryMaster");
		
		criteria.setFetchMode("loyaltyCatagoryMaster.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("loyaltyCatagoryMaster.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("applicationCountryId.countryId", applicationCountryId));
		
		criteria.add(Restrictions.eq("loyaltyCatagoryMaster.categoryType", categoryType));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		List<LoyaltyCatagoryMaster> objList = (List<LoyaltyCatagoryMaster>) findAll(criteria);
		List<?> list = CommonUtil.nullCheck(objList);
	    return (List <LoyaltyCatagoryMaster>) list;
	}

	@Override
	public void activateLoyaltyCatagoryMaster(BigDecimal loyaltyCatagoryId, String userName) {
		LoyaltyCatagoryMaster loyaltyCatagoryMaster = (LoyaltyCatagoryMaster) getSession().get(LoyaltyCatagoryMaster.class, loyaltyCatagoryId);
		loyaltyCatagoryMaster.setIsactive(Constants.U);
		loyaltyCatagoryMaster.setModifiedBy(userName);
		loyaltyCatagoryMaster.setModifiedDate(new Date());
		loyaltyCatagoryMaster.setApprovedBy(null);
		loyaltyCatagoryMaster.setApprovedDate(null);
		loyaltyCatagoryMaster.setRemarks(null);
		getSession().update(loyaltyCatagoryMaster);
		
	}

	@Override
	public void deleteLoyaltyCatagoryMaster(BigDecimal loyaltyCatagoryId, BigDecimal englishDescId, BigDecimal arabicDescId) {
		LoyaltyCatagoryMaster loyaltyCatagoryMaster=(LoyaltyCatagoryMaster) getSession().get(LoyaltyCatagoryMaster.class, loyaltyCatagoryId);
		LoyaltyCatergoryMasterDesc englishLoyaltyCatergoryMasterDesc=(LoyaltyCatergoryMasterDesc) getSession().get(LoyaltyCatergoryMasterDesc.class, englishDescId);
		LoyaltyCatergoryMasterDesc arabicLoyaltyCatergoryMasterDesc=(LoyaltyCatergoryMasterDesc) getSession().get(LoyaltyCatergoryMasterDesc.class, arabicDescId);
		getSession().delete(loyaltyCatagoryMaster);
		getSession().delete(englishLoyaltyCatergoryMasterDesc);
		getSession().delete(arabicLoyaltyCatergoryMasterDesc);
		
	}

	@Override
	public List<String> autoCompleteList(String query) {

		DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyCatagoryMaster.class, "loyaltyCatagoryMaster");
		criteria.add(Restrictions.like("loyaltyCatagoryMaster.categoryCode", query, MatchMode.ANYWHERE).ignoreCase());
		criteria.setProjection(Projections.property("loyaltyCatagoryMaster.categoryCode"));
		criteria.addOrder(Order.asc("loyaltyCatagoryMaster.categoryCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<String> lstComplaintAssignedCode = (List<String>) findAll(criteria);
		return lstComplaintAssignedCode;
	}

	@Override
	public List<LoyaltyCatagoryMaster> displayLoyaltyCatagoryForApprove(BigDecimal appCountryId, String inActive) {
		DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyCatagoryMaster.class, "loyaltyCatagoryMaster");
		criteria.setFetchMode("loyaltyCatagoryMaster.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("loyaltyCatagoryMaster.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("applicationCountryId.countryId", appCountryId));
		criteria.add(Restrictions.eq("loyaltyCatagoryMaster.isactive", inActive));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		List<LoyaltyCatagoryMaster> objList = (List<LoyaltyCatagoryMaster>) findAll(criteria);
		List<?> list = CommonUtil.nullCheck(objList);
		return (List<LoyaltyCatagoryMaster>) list;
	}

	@Override
	public void approveRecord(BigDecimal loyaltyCatagoryId, String userName, String isActive) {
		LoyaltyCatagoryMaster loyaltyCatagoryMaster = (LoyaltyCatagoryMaster) getSession().get(LoyaltyCatagoryMaster.class, loyaltyCatagoryId);
		loyaltyCatagoryMaster.setIsactive(isActive);		
		loyaltyCatagoryMaster.setApprovedBy(userName);
		loyaltyCatagoryMaster.setApprovedDate(new Date());
		getSession().update(loyaltyCatagoryMaster);
		
	}

	@Override
	public List<LoyaltyCatagoryMaster> displayLoyaltyCatagoryBasedOnCatagoryCode(String categoryCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyCatagoryMaster.class, "loyaltyCatagoryMaster");
		criteria.add(Restrictions.eq("loyaltyCatagoryMaster.categoryCode", categoryCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		List<LoyaltyCatagoryMaster> objList = (List<LoyaltyCatagoryMaster>) findAll(criteria);
		List<?> list = CommonUtil.nullCheck(objList);
		return (List <LoyaltyCatagoryMaster>) list;
	}

	@Override
	public List<LoyaltyCatergoryMasterDesc> displayLoyaltyCatagoryForLoyaltyCreation(BigDecimal appCountryId, BigDecimal languageId) {
		// TODO Auto-generated method stub 
		return null;
	}
	
	@Override
	public List<LoyaltyTypeDesc> getLoyaltyTypeDescList( BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyTypeDesc.class, "loyaltyTypeDesc");
		
		criteria.setFetchMode("loyaltyTypeDesc.loyalityTypeId", FetchMode.JOIN);
		criteria.createAlias("loyaltyTypeDesc.loyalityTypeId", "loyalityTypeId", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("loyaltyTypeDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("loyaltyTypeDesc.languageId", "languageId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		List<LoyaltyTypeDesc> objList = (List<LoyaltyTypeDesc>) findAll(criteria);
		List<?> list = CommonUtil.nullCheck(objList);
	    return (List <LoyaltyTypeDesc>) list;
	}
	
	@Override
	public List<LoyaltyTypeDesc> getLoyaltyTypeId(String categoryType, BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyTypeDesc.class, "loyaltyTypeDesc");
		
		criteria.setFetchMode("loyaltyTypeDesc.loyalityTypeId", FetchMode.JOIN);
		criteria.createAlias("loyaltyTypeDesc.loyalityTypeId", "loyalityTypeId", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("loyaltyTypeDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("loyaltyTypeDesc.languageId", "languageId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));
		
		criteria.add(Restrictions.eq("loyaltyTypeDesc.shortDescription", categoryType));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		List<LoyaltyTypeDesc> objList = (List<LoyaltyTypeDesc>) findAll(criteria);
		List<?> list = CommonUtil.nullCheck(objList);
	    return (List <LoyaltyTypeDesc>) list;
	}
	
	@Override
	public List<LoyaltyTypeDesc> getLoyaltyTypeMasterDesc(BigDecimal loyaltyTypeId,BigDecimal languageId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyTypeDesc.class, "loyaltyTypeDesc");
		
		criteria.setFetchMode("loyaltyTypeDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("loyaltyTypeDesc.languageId", "languageId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));
		
		criteria.setFetchMode("loyaltyTypeDesc.loyalityTypeId", FetchMode.JOIN);
		criteria.createAlias("loyaltyTypeDesc.loyalityTypeId", "loyalityTypeId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("loyalityTypeId.loyalityTypeId", loyaltyTypeId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		List<LoyaltyTypeDesc> objList = (List<LoyaltyTypeDesc>) findAll(criteria);
		List<?> list = CommonUtil.nullCheck(objList);
		return (List<LoyaltyTypeDesc>) list;
	}

	@Override
	public void saveLoyaltyCataegory(HashMap<String, Object> saveMapInfo)
			throws AMGException {
		LoyaltyCatagoryMaster loyaltyCategory= (LoyaltyCatagoryMaster) saveMapInfo.get("loyaltyType");
		LoyaltyCatergoryMasterDesc englishCategory = (LoyaltyCatergoryMasterDesc) saveMapInfo.get("englishDesc");
		LoyaltyCatergoryMasterDesc localCategory = (LoyaltyCatergoryMasterDesc) saveMapInfo.get("localDesc");
		
		if(loyaltyCategory!=null){
			getSession().saveOrUpdate(loyaltyCategory);
		}
		if(englishCategory!=null){
			getSession().saveOrUpdate(englishCategory);
		}
		if(localCategory!=null){
			getSession().saveOrUpdate(localCategory);
		}
		
	}
	
}
