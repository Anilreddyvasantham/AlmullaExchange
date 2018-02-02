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
import com.amg.exchange.complaint.model.ComplaintTypeMaster;
import com.amg.exchange.loyalty.dao.ILoyaltyTypeDao;
import com.amg.exchange.loyalty.model.LoyaltyCatagoryMaster;
import com.amg.exchange.loyalty.model.LoyaltyCatergoryMasterDesc;
import com.amg.exchange.loyalty.model.LoyaltyType;
import com.amg.exchange.loyalty.model.LoyaltyTypeDesc;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;

@SuppressWarnings({"serial" ,"unchecked"})
@Repository
public class LoyaltyTypeDaoImpl<T> extends CommonDaoImpl<T> implements ILoyaltyTypeDao<T>, Serializable {
	
	@Override
	public List<LoyaltyType> displayList(String loyaltyType,BigDecimal applicationCountryId){
		
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(LoyaltyType.class, "loyalityType");
		
		detachedCriteria.setFetchMode("loyalityType.applicationCountryId", FetchMode.JOIN);
		detachedCriteria.createAlias("loyalityType.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("applicationCountryId.countryId", applicationCountryId));
		
		detachedCriteria.add(Restrictions.eq("loyalityType.loyalityType", loyaltyType ).ignoreCase());
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		
		List<LoyaltyType> objList = (List<LoyaltyType>)findAll(detachedCriteria);
		
		if(objList.isEmpty())
			return null;
		
		return objList;
	}
	
	@Override
	public List<LoyaltyType> displayListById(BigDecimal id){
		
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(LoyaltyType.class, "loyalityType");
		
		return (List<LoyaltyType>)findAll(detachedCriteria);
	}
	
	@Override
	public List<LoyaltyType> displayListByCode(String code){
		
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(LoyaltyType.class, "loyalityType");
		detachedCriteria.add(Restrictions.eq("loyalityType.loyalityTypeCode", code));
		
		return (List<LoyaltyType>)findAll(detachedCriteria);
	}
	
	@Override
	public List<LoyaltyTypeDesc> getDescriptionById(BigDecimal id){
		
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(LoyaltyTypeDesc.class, "loyalityTypeDesc");
		detachedCriteria.add(Restrictions.eq("loyalityTypeDesc.loyalityTypeId.loyalityTypeId", id));
		
		return (List<LoyaltyTypeDesc>)findAll(detachedCriteria);
	}
	
	
	@Override
	public void saveMaster(LoyaltyType loyalityType) {
		getSession().saveOrUpdate(loyalityType);
		
	}

	@Override
	public void saveMasterDescriptionEnglish(LoyaltyTypeDesc loyalityTypeDesc) {
		getSession().saveOrUpdate(loyalityTypeDesc);
		
	}
	
	@Override
	public void saveMasterDescriptionLocal(LoyaltyTypeDesc loyalityTypeDesc) {
		getSession().saveOrUpdate(loyalityTypeDesc);
		
	}
	
	@Override
	public List<String> autoCompleteList(String query) {
		DetachedCriteria criteria=DetachedCriteria.forClass(LoyaltyType.class, "loyalityType");
		criteria.add(Restrictions.like("loyalityType.loyalityTypeCode", query, MatchMode.ANYWHERE).ignoreCase());
		criteria.setProjection(Projections.property("loyalityType.loyalityTypeCode"));
		criteria.addOrder(Order.asc("loyalityType.loyalityTypeCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<String> lstLoyaltyTypeCode = (List<String>) findAll(criteria);
		return lstLoyaltyTypeCode;
	}
	
	@Override
	public void activate(BigDecimal id, String userName) {
		LoyaltyType loyaltyType = (LoyaltyType) getSession().get(LoyaltyType.class, id);
		loyaltyType.setIsActive(Constants.Yes);
		loyaltyType.setModifiedBy(userName);
		loyaltyType.setModifiedDate(new Date());
		loyaltyType.setApprovedBy(null);
		loyaltyType.setApprovedDate(null);
		loyaltyType.setRemarks(null);
		getSession().update(loyaltyType);

	}
	@Override
	public void deleteLoyaltyType(BigDecimal loyaltyTypeId, BigDecimal loyaltyTypeDescId, BigDecimal loyaltyTypeArabicDescId){
		LoyaltyType loyaltyType=(LoyaltyType) getSession().get(LoyaltyType.class, loyaltyTypeId);
		LoyaltyTypeDesc englishLoyaltyTypeDesc=(LoyaltyTypeDesc) getSession().get(LoyaltyTypeDesc.class, loyaltyTypeDescId);
		LoyaltyTypeDesc arabicLoyaltyTypeDesc=(LoyaltyTypeDesc) getSession().get(LoyaltyTypeDesc.class, loyaltyTypeArabicDescId);
		getSession().delete(loyaltyType);
		getSession().delete(englishLoyaltyTypeDesc);
		getSession().delete(arabicLoyaltyTypeDesc);
		
	}

	@Override
	public void saveLoyalty(HashMap<String, Object> saveMapInfo)
			throws AMGException {
		LoyaltyType loyalty = (LoyaltyType) saveMapInfo.get("loyaltyType");
		LoyaltyTypeDesc englishDesc = (LoyaltyTypeDesc) saveMapInfo.get("englishDesc");
		LoyaltyTypeDesc localDesc = (LoyaltyTypeDesc) saveMapInfo.get("localDesc");
		if(loyalty!=null){
			getSession().saveOrUpdate(loyalty);
		}
		if(englishDesc!=null){
			getSession().saveOrUpdate(englishDesc);
		}
		if(localDesc!=null){
			getSession().saveOrUpdate(localDesc);
		}
		
	}

}
