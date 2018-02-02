package com.amg.exchange.complaint.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.complaint.dao.IComplaintTypeDao;
import com.amg.exchange.complaint.model.ComplaintTypeDesc;
import com.amg.exchange.complaint.model.ComplaintTypeMaster;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;

@SuppressWarnings("unchecked")
@Repository
public class ComplaintTypeDaoImpl<T> extends CommonDaoImpl<T> implements IComplaintTypeDao<T>,Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG=Logger.getLogger(ComplaintTypeDaoImpl.class);

	@Override
	public void saveAndUpdateComplaintType(ComplaintTypeMaster complaintTypeMaster) {
		getSession().saveOrUpdate(complaintTypeMaster);
	}
	
	@Override
	public void saveAndUpdateComplaintTypeDesc(ComplaintTypeDesc complaintTypeDesc){
		getSession().saveOrUpdate(complaintTypeDesc);
	}

	@Override
	public Boolean isComplaintTypeCodeExist(String complaintCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintTypeMaster.class, "complaintTypeMaster");
		criteria.add(Restrictions.eq("complaintTypeMaster.complaintTypeCode", complaintCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ComplaintTypeMaster> objList = (List<ComplaintTypeMaster>) findAll(criteria);
		return CommonUtil.checkSizeOfRecords(objList);
		
	}
	
	@Override
	public List<ComplaintTypeDesc> displayAllComplaintTypeDesc( BigDecimal ComplaintTypeId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintTypeDesc.class, "complaintTypeDesc");
		criteria.setFetchMode("complaintTypeDesc.complaintType", FetchMode.JOIN);
		criteria.createAlias("complaintTypeDesc.complaintType", "complaintType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintType.complaintTypeId", ComplaintTypeId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		List<ComplaintTypeDesc> objList = (List<ComplaintTypeDesc>) findAll(criteria);
		List<?> list = CommonUtil.nullCheck(objList);
		return (List<ComplaintTypeDesc>) list;
	}
	

	@Override
	public List<ComplaintTypeMaster> displayAllComplaintTypeToView(BigDecimal appCountryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintTypeMaster.class, "complaintTypeMaster");
		criteria.setFetchMode("complaintTypeMaster.appCountryId", FetchMode.JOIN);
		criteria.createAlias("complaintTypeMaster.appCountryId", "appCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("appCountryId.countryId", appCountryId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		/*List<ComplaintTypeMaster> objList = (List<ComplaintTypeMaster>) findAll(criteria);
		List<?> list = CommonUtil.nullCheck(objList);
	    return (List <ComplaintTypeMaster>) list;*/

		List<ComplaintTypeMaster> objList = (List<ComplaintTypeMaster>) findAll(criteria);
		if (objList.isEmpty())
			return null;

		return objList;
		
	}
	@Override
	public void activateComplaintType(BigDecimal complaintTypeId, String userName) {
		ComplaintTypeMaster complaintTypeMaster = (ComplaintTypeMaster) getSession().get(ComplaintTypeMaster.class, complaintTypeId);
		complaintTypeMaster.setIsActive(Constants.U);
		complaintTypeMaster.setModifiedBy(userName);
		complaintTypeMaster.setModifiedDate(new Date());
		complaintTypeMaster.setApprovedBy(null);
		complaintTypeMaster.setApprovedDate(null);
		complaintTypeMaster.setRemarks(null);
		getSession().update(complaintTypeMaster);

	}
	
	@Override
	public void deleteComplaintType(BigDecimal complaintTypeId, BigDecimal englishDescId, BigDecimal arabicDescId) {
		ComplaintTypeMaster complaintTypeMaster=(ComplaintTypeMaster) getSession().get(ComplaintTypeMaster.class, complaintTypeId);
		ComplaintTypeDesc englishComplaintTypeDesc=(ComplaintTypeDesc) getSession().get(ComplaintTypeDesc.class, englishDescId);
		ComplaintTypeDesc arabicComplaintTypeDesc=(ComplaintTypeDesc) getSession().get(ComplaintTypeDesc.class, arabicDescId);
		getSession().delete(complaintTypeMaster);
		getSession().delete(englishComplaintTypeDesc);
		getSession().delete(arabicComplaintTypeDesc);
		
		
	}
	
	@Override
	public List<String> autoCompleteList(String query) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintTypeMaster.class, "complaintTypeMaster");
		criteria.add(Restrictions.like("complaintTypeMaster.complaintTypeCode", query, MatchMode.ANYWHERE).ignoreCase());
		criteria.setProjection(Projections.property("complaintTypeMaster.complaintTypeCode"));
		criteria.addOrder(Order.asc("complaintTypeMaster.complaintTypeCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<String> lstComplaintAssignedCode = (List<String>) findAll(criteria);
		return lstComplaintAssignedCode;
	}
	
	
	@Override
	public List<ComplaintTypeMaster> displayComplaintTypeForApprove(BigDecimal appCountryId,String inActive) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintTypeMaster.class, "complaintTypeMaster");
		criteria.setFetchMode("complaintTypeMaster.appCountryId", FetchMode.JOIN);
		criteria.createAlias("complaintTypeMaster.appCountryId", "appCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("appCountryId.countryId", appCountryId));
		criteria.add(Restrictions.eq("complaintTypeMaster.isActive", inActive));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		List<ComplaintTypeMaster> objList = (List<ComplaintTypeMaster>) findAll(criteria);
		List<?> list = CommonUtil.nullCheck(objList);
		return (List<ComplaintTypeMaster>) list;
	}
	
	
	@Override
	public void approveRecord(BigDecimal complaintTypeId, String userName,String isActive) {
		ComplaintTypeMaster complaintTypeMaster = (ComplaintTypeMaster) getSession().get(ComplaintTypeMaster.class, complaintTypeId);
		complaintTypeMaster.setIsActive(isActive);		
		complaintTypeMaster.setApprovedBy(userName);
		complaintTypeMaster.setApprovedDate(new Date());
		getSession().update(complaintTypeMaster);

	}
	
	@Override
	public List<ComplaintTypeMaster> displayComplaintTypeBasedOnComplaintTypeCode(String complaintCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintTypeMaster.class, "complaintTypeMaster");
		criteria.add(Restrictions.eq("complaintTypeMaster.complaintTypeCode", complaintCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		List<ComplaintTypeMaster> objList = (List<ComplaintTypeMaster>) findAll(criteria);
		List<?> list = CommonUtil.nullCheck(objList);
		return (List <ComplaintTypeMaster>) list;
	}

	
	@Override
	public List<ComplaintTypeDesc> displayComplaintTypeForComplaintCreation(BigDecimal countryId, BigDecimal languageId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintTypeDesc.class,"complaintTypeDesc");
		criteria.setFetchMode("complaintTypeDesc.complaintType", FetchMode.JOIN);
		criteria.createAlias("complaintTypeDesc.complaintType", "complaintType",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintType.isActive",Constants.Yes));

		criteria.setFetchMode("complaintType.appCountryId", FetchMode.JOIN);
		criteria.createAlias("complaintType.appCountryId", "appCountryId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("appCountryId.countryId",countryId));

		criteria.setFetchMode("complaintTypeDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("complaintTypeDesc.languageId", "languageId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId",languageId));
		
		criteria.addOrder(Order.asc("complaintType.complaintTypeCode"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ComplaintTypeDesc> lstCompTypeDesc =  (List<ComplaintTypeDesc>) findAll(criteria);		
		List<?> list = CommonUtil.nullCheck(lstCompTypeDesc);
		return (List <ComplaintTypeDesc>) list;

	}
	
}
