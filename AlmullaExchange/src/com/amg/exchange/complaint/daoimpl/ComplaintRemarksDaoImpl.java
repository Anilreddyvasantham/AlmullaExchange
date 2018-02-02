package com.amg.exchange.complaint.daoimpl;

import java.io.Serializable;
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
import com.amg.exchange.complaint.dao.IComplaintRemarksDao;
import com.amg.exchange.complaint.model.ComplaintRemarksDesc;
import com.amg.exchange.complaint.model.ComplaintRemarksMaster;
import com.amg.exchange.util.Constants;
@SuppressWarnings("serial")
@Repository
public class ComplaintRemarksDaoImpl<T> extends CommonDaoImpl<T> implements IComplaintRemarksDao<T>,Serializable {

	public ComplaintRemarksDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void saveComplaintRemarksMethod(ComplaintRemarksMaster complaintRemarksMaster) {
		
		getSession().saveOrUpdate(complaintRemarksMaster);
	}
	
	//@Override
	public void saveComplaintRemarksDescMethod(ComplaintRemarksDesc complaintRemarksDesc){
		getSession().saveOrUpdate(complaintRemarksDesc);
	}

	@Override
	public void deleteComplaintRemarksMethod(BigDecimal complaintRemarksId, BigDecimal englishDescId, BigDecimal arabicDescId) {
		
		ComplaintRemarksMaster complaintRemarksMaster=(ComplaintRemarksMaster) getSession().get(ComplaintRemarksMaster.class, complaintRemarksId);
		ComplaintRemarksDesc englishComplaintRemarksDesc=(ComplaintRemarksDesc) getSession().get(ComplaintRemarksDesc.class, englishDescId);
		ComplaintRemarksDesc arabicComplaintRemarksDesc=(ComplaintRemarksDesc) getSession().get(ComplaintRemarksDesc.class, arabicDescId);
		getSession().delete(complaintRemarksMaster);
		getSession().delete(englishComplaintRemarksDesc);
		getSession().delete(arabicComplaintRemarksDesc);
	
	}
	
	@Override
	public void activateComplaintRemarksMethod(BigDecimal complaintRemarksId, String userName) {
		ComplaintRemarksMaster complaintRemarksMaster = (ComplaintRemarksMaster) getSession().get(ComplaintRemarksMaster.class, complaintRemarksId);
		complaintRemarksMaster.setIsActive(Constants.U);
		complaintRemarksMaster.setModifiedBy(userName);
		complaintRemarksMaster.setModifiedDate(new Date());
		complaintRemarksMaster.setApprovedBy(null);
		complaintRemarksMaster.setApprovedDate(null);
		complaintRemarksMaster.setRemarks(null);
		getSession().update(complaintRemarksMaster);

	}
	
	@Override
	public List<ComplaintRemarksDesc> getAllComplaintRemarksDesc( BigDecimal ComplaintRemarksId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintRemarksDesc.class, "complaintRemarksDesc");
		
		criteria.setFetchMode("complaintRemarksDesc.complaintRemarks", FetchMode.JOIN);
		criteria.createAlias("complaintRemarksDesc.complaintRemarks", "complaintRemarks", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintRemarks.complaintRemarksId", ComplaintRemarksId));
	
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		
		List<ComplaintRemarksDesc> objList = (List<ComplaintRemarksDesc>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}
	
	@Override
	public List<ComplaintRemarksMaster> getAllComplaintRemarks(BigDecimal appCountryId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintRemarksMaster.class, "complaintRemarksMaster");
		
		criteria.setFetchMode("complaintRemarksMaster.appCountryId", FetchMode.JOIN);
		criteria.createAlias("complaintRemarksMaster.appCountryId", "appCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("appCountryId.countryId", appCountryId));
		

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		
		List<ComplaintRemarksMaster> objList = (List<ComplaintRemarksMaster>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}
	
	@Override
	public List<ComplaintRemarksMaster> getComplaintRemarksForApprove(BigDecimal appCountryId,String inActive) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintRemarksMaster.class, "complaintRemarksMaster");
		
		criteria.setFetchMode("complaintRemarksMaster.appCountryId", FetchMode.JOIN);
		criteria.createAlias("complaintRemarksMaster.appCountryId", "appCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("appCountryId.countryId", appCountryId));
		

		criteria.add(Restrictions.eq("complaintRemarksMaster.isActive", inActive));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		
		List<ComplaintRemarksMaster> objList = (List<ComplaintRemarksMaster>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}
	
	@Override
	public Boolean isComplaintCodeExist(String complaintCode) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintRemarksMaster.class, "complaintRemarksMaster");

		criteria.add(Restrictions.eq("complaintRemarksMaster.complaintRemarksCode", complaintCode));
		
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ComplaintRemarksMaster> objList = (List<ComplaintRemarksMaster>) findAll(criteria);
		
		if (objList.isEmpty())
			return false;

		return true;

	}
	
	@Override
	public void approveRecord(BigDecimal complaintRemarksId, String userName,String isActive) {
		ComplaintRemarksMaster complaintRemarksMaster = (ComplaintRemarksMaster) getSession().get(ComplaintRemarksMaster.class, complaintRemarksId);
		complaintRemarksMaster.setIsActive(isActive);		
		complaintRemarksMaster.setApprovedBy(userName);
		complaintRemarksMaster.setApprovedDate(new Date());
		
		getSession().update(complaintRemarksMaster);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> autoCompleteList(String query) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintRemarksMaster.class, "complaintRemarksMaster");
		criteria.add(Restrictions.like("complaintRemarksMaster.complaintRemarksCode", query, MatchMode.ANYWHERE).ignoreCase());
		criteria.setProjection(Projections.property("complaintRemarksMaster.complaintRemarksCode"));
		criteria.addOrder(Order.asc("complaintRemarksMaster.complaintRemarksCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<String> lstComplaintAssignedCode = (List<String>) findAll(criteria);
		return lstComplaintAssignedCode;
	}
	
	@Override
	public List<ComplaintRemarksMaster> getComplaintRemarksBasedOnComplaintCode(String complaintCode) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintRemarksMaster.class, "complaintRemarksMaster");
		
		criteria.add(Restrictions.eq("complaintRemarksMaster.complaintRemarksCode", complaintCode));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		
		List<ComplaintRemarksMaster> objList = (List<ComplaintRemarksMaster>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public List<ComplaintRemarksDesc> getAllComplaintRemarksForComplaintCreation(BigDecimal countryId, BigDecimal languageId) {
		  // TODO Auto-generated method stub
		  DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintRemarksDesc.class,"complaintRemarksDesc");

		  criteria.setFetchMode("complaintRemarksDesc.complaintRemarks", FetchMode.JOIN);
		  criteria.createAlias("complaintRemarksDesc.complaintRemarks", "complaintRemarks",JoinType.INNER_JOIN);
		  criteria.add(Restrictions.eq("complaintRemarks.isActive",Constants.Yes));

		  criteria.setFetchMode("complaintRemarks.appCountryId", FetchMode.JOIN);
		  criteria.createAlias("complaintRemarks.appCountryId", "applicationCountryId",JoinType.INNER_JOIN);
		  criteria.add(Restrictions.eq("applicationCountryId.countryId",countryId));

		  criteria.setFetchMode("complaintRemarksDesc.languageId", FetchMode.JOIN);
		  criteria.createAlias("complaintRemarksDesc.languageId", "languageId",JoinType.INNER_JOIN);
		  criteria.add(Restrictions.eq("languageId.languageId",languageId));
		  
		  criteria.addOrder(Order.asc("complaintRemarks.complaintRemarksCode"));

		  criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		  List<ComplaintRemarksDesc> lstCompRemDesc =  (List<ComplaintRemarksDesc>) findAll(criteria);		  

		  return lstCompRemDesc;
	  }

}
