package com.amg.exchange.complaint.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.dto.ComplaintActionDTO;
import com.amg.exchange.complaint.dao.IComplaintActionDao;
import com.amg.exchange.complaint.model.ComplaintAction;
import com.amg.exchange.complaint.model.ComplaintActionDesc;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class ComplaintActionDaoImpl extends CommonDaoImpl implements IComplaintActionDao {
	private static final Logger log=Logger.getLogger(ComplaintActionDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintActionDTO> getAllRecords() {
		DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintAction.class,"complaintAction");
	
		criteria.setFetchMode("complaintAction.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("complaintAction.applicationCountryId", "applicationCountryId",JoinType.INNER_JOIN);
		ProjectionList columns = Projections.projectionList();
		 columns.add(Projections.property("complaintActionCode"),"complaintActionCode");
		 columns.add(Projections.property("complaintActionId"),"complaintActionId");
		 columns.add(Projections.property("applicationCountryId.countryId"),"applicationCountryId");
		 columns.add(Projections.property("createdBy"),"createdBy");
		 columns.add(Projections.property("actionGroup"),"actionGroup");
		 columns.add(Projections.property("createdDate"),"createdDate");
		 columns.add(Projections.property("modifiedBy"),"modifiedBy");
		 columns.add(Projections.property("modifiedDate"),"modifiedDate");
		 columns.add(Projections.property("approvedBy"),"approvedBy");
		 columns.add(Projections.property("approvedDate"),"approvedDate");
		 columns.add(Projections.property("remarks"),"remarks");
		 columns.add(Projections.property("isActive"),"isActive");
		 
		 criteria.setProjection(columns);
		 criteria.setResultTransformer( new AliasToBeanResultTransformer(ComplaintActionDTO.class) );
		 List<ComplaintActionDTO> lstcomplaintactionDTO=  (List<ComplaintActionDTO>) findAll(criteria);
			return lstcomplaintactionDTO;  
	}

	@Override
	public void saveRecords(ComplaintAction complaintAction,
			ComplaintActionDesc complaintDescEng,
			ComplaintActionDesc complaintDescArabic) {
		try{
			getSession().saveOrUpdate(complaintAction);
			getSession().saveOrUpdate(complaintDescEng);
			getSession().saveOrUpdate(complaintDescArabic);
			}catch(Exception exception){
				log.error("Error Occured While Saving  ComplaintAction and ComplaintActionDesc Records  :" +exception.getMessage());
			}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAutoComplete(String query) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintAction.class, "complaintAction");
		criteria.add(Restrictions.like("complaintAction.complaintActionCode", query, MatchMode.ANYWHERE).ignoreCase());
		criteria.setProjection(Projections.property("complaintAction.complaintActionCode"));
		criteria.addOrder(Order.asc("complaintAction.complaintActionCode"));
 		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<String>)findAll(criteria);
	}

	@Override
	public void deleteRecordPermanently(BigDecimal complaintActionPk,BigDecimal complaintDescEnglishPk, BigDecimal complaintArabicPk) {
		
		ComplaintAction  complaintAction=(ComplaintAction) getSession().get(ComplaintAction.class, complaintActionPk);
		ComplaintActionDesc complaintActionEngDesc=(ComplaintActionDesc) getSession().get(ComplaintActionDesc.class, complaintDescEnglishPk);
		ComplaintActionDesc complaintActionArabicDesc=(ComplaintActionDesc) getSession().get(ComplaintActionDesc.class, complaintArabicPk);
		getSession().delete(complaintActionEngDesc);	
		getSession().delete(complaintActionArabicDesc);		
		getSession().delete(complaintAction);		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintActionDTO> getAllComplaintActionChildRecords(BigDecimal complaintActionId) {
	DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintActionDesc.class,"complaintActionDesc");
	
    criteria.setFetchMode("complaintActionDesc.complaintAction", FetchMode.JOIN);
	criteria.createAlias("complaintActionDesc.complaintAction", "complaintAction",JoinType.INNER_JOIN);
	criteria.setFetchMode("complaintActionDesc.languageId", FetchMode.JOIN);
	criteria.createAlias("complaintActionDesc.languageId", "languageId",JoinType.INNER_JOIN);
	criteria.add(Restrictions.eq("complaintActionDesc.complaintAction.complaintActionId",complaintActionId));
	
	ProjectionList columns = Projections.projectionList();
	columns.add(Projections.property("languageId.languageId"), "languageId");
	columns.add(Projections.property("fullDescription"),"fullDescription");
	columns.add(Projections.property("shortDescription"), "shortDescription");
	columns.add(Projections.property("complaintActionDescId"), "complaintActionDescId");
	criteria.setProjection(columns);
	criteria.setResultTransformer( new AliasToBeanResultTransformer(ComplaintActionDTO.class) );
	List<ComplaintActionDTO> lstcomaplaintActionDTO=  (List<ComplaintActionDTO>) findAll(criteria);
	return lstcomaplaintActionDTO;
	 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintActionDTO> getComplaintActionRecord(String complaintActionCode) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintAction.class,"complaintAction");
		
		criteria.setFetchMode("complaintAction.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("complaintAction.applicationCountryId", "applicationCountryId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintAction.complaintActionCode",complaintActionCode));
		ProjectionList columns = Projections.projectionList();
		 columns.add(Projections.property("complaintActionCode"),"complaintActionCode");
		 columns.add(Projections.property("complaintActionId"),"complaintActionId");
		 columns.add(Projections.property("applicationCountryId.countryId"),"applicationCountryId");
		 columns.add(Projections.property("actionGroup"),"actionGroup");
		 columns.add(Projections.property("createdBy"),"createdBy");
		 columns.add(Projections.property("createdDate"),"createdDate");
		 columns.add(Projections.property("modifiedBy"),"modifiedBy");
		 columns.add(Projections.property("modifiedDate"),"modifiedDate");
		 columns.add(Projections.property("approvedBy"),"approvedBy");
		 columns.add(Projections.property("approvedDate"),"approvedDate");
		 columns.add(Projections.property("remarks"),"remarks");
		 columns.add(Projections.property("isActive"),"isActive");
		 
		 criteria.setProjection(columns);
		 criteria.setResultTransformer( new AliasToBeanResultTransformer(ComplaintActionDTO.class) );
		 List<ComplaintActionDTO> lstcomplaintactionDTO=  (List<ComplaintActionDTO>) findAll(criteria);
			return lstcomplaintactionDTO;  
	}

	@Override
	public void activateRecord(BigDecimal complaintActionPk, String userName) {
		ComplaintAction cmplaintAction=(ComplaintAction) getSession().get(ComplaintAction.class, complaintActionPk);
		cmplaintAction.setIsActive(Constants.U);
		cmplaintAction.setModifiedBy(userName);
		cmplaintAction.setModifiedDate(new Date());
		cmplaintAction.setApprovedBy(null);
		cmplaintAction.setApprovedDate(null);
		cmplaintAction.setRemarks(null);
		getSession().update(cmplaintAction);
		
		
	}

	 

	@Override
	public String approveRecord(BigDecimal complaintActionPk, String userName) {
		 
		String approveMsg;
		ComplaintAction complaintAction=(ComplaintAction) getSession().get(ComplaintAction.class, complaintActionPk);
		
		String approvedUser=complaintAction.getApprovedBy();
		if(approvedUser==null)
		{
			complaintAction.setIsActive(Constants.Yes);
			complaintAction.setApprovedBy(userName);
			complaintAction.setApprovedDate(new Date());
			complaintAction.setRemarks("");
			getSession().update(complaintAction);
			approveMsg="Success";
			
		}else
		{
			approveMsg="Fail";
		}
		
		return approveMsg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintActionDTO> getAllUnApprovedRecordsFromDesc(
			BigDecimal complaintActionId) {
	DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintActionDesc.class,"complaintActionDesc");
    criteria.setFetchMode("complaintActionDesc.complaintAction", FetchMode.JOIN);
	criteria.createAlias("complaintActionDesc.complaintAction", "complaintAction",JoinType.INNER_JOIN);
	criteria.setFetchMode("complaintActionDesc.languageId", FetchMode.JOIN);
	criteria.createAlias("complaintActionDesc.languageId", "languageId",JoinType.INNER_JOIN);
	criteria.add(Restrictions.eq("complaintActionDesc.complaintAction.complaintActionId",complaintActionId));
	
	ProjectionList columns = Projections.projectionList();
	columns.add(Projections.property("languageId.languageId"), "languageId");
	columns.add(Projections.property("fullDescription"),"fullDescription");
	columns.add(Projections.property("shortDescription"), "shortDescription");
	columns.add(Projections.property("complaintActionDescId"), "complaintActionDescId");
	criteria.setProjection(columns);
	criteria.setResultTransformer( new AliasToBeanResultTransformer(ComplaintActionDTO.class) );
	List<ComplaintActionDTO> lstcomplaintActionDTO=  (List<ComplaintActionDTO>) findAll(criteria);
	return lstcomplaintActionDTO;
		 
	}

	@Override
	public List<ComplaintActionDTO> getAllUnApprovedRecords() {
		 DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintAction.class,"complaintAction");
		 
		 criteria.add(Restrictions.eq("complaintAction.isActive",Constants.U));
		 ProjectionList columns=Projections.projectionList();
		 
		 columns.add(Projections.property("complaintActionCode"),"complaintActionCode");
		 columns.add(Projections.property("complaintActionId"),"complaintActionId");
		 columns.add(Projections.property("applicationCountryId.countryId"),"applicationCountryId");
		 columns.add(Projections.property("createdBy"),"createdBy");
		 columns.add(Projections.property("actionGroup"),"actionGroup");
		 columns.add(Projections.property("createdDate"),"createdDate");
		 columns.add(Projections.property("modifiedBy"),"modifiedBy");
		 columns.add(Projections.property("modifiedDate"),"modifiedDate");
		 columns.add(Projections.property("approvedBy"),"approvedBy");
		 columns.add(Projections.property("approvedDate"),"approvedDate");
		 columns.add(Projections.property("remarks"),"remarks");
		 columns.add(Projections.property("isActive"),"isActive");
		 
		 criteria.setProjection(columns);
		 criteria.setResultTransformer( new AliasToBeanResultTransformer(ComplaintActionDTO.class) );
		 List<ComplaintActionDTO>  complaintActionDTO=  (List<ComplaintActionDTO>) findAll(criteria);
		return complaintActionDTO; 
		 
	}

	@Override
	public List<ComplaintActionDTO> getParentRecordForPopulate(
			String complaintActionCode) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintAction.class,"complaintAction");
		
		criteria.setFetchMode("complaintAction.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("complaintAction.applicationCountryId", "applicationCountryId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintAction.complaintActionCode",complaintActionCode));
		ProjectionList columns = Projections.projectionList();
		 columns.add(Projections.property("complaintActionCode"),"complaintActionCode");
		 columns.add(Projections.property("complaintActionId"),"complaintActionId");
		 columns.add(Projections.property("applicationCountryId.countryId"),"applicationCountryId");
		 columns.add(Projections.property("createdBy"),"createdBy");
		 columns.add(Projections.property("actionGroup"),"actionGroup");
		 columns.add(Projections.property("createdDate"),"createdDate");
		 columns.add(Projections.property("modifiedBy"),"modifiedBy");
		 columns.add(Projections.property("modifiedDate"),"modifiedDate");
		 columns.add(Projections.property("approvedBy"),"approvedBy");
		 columns.add(Projections.property("approvedDate"),"approvedDate");
		 columns.add(Projections.property("remarks"),"remarks");
		 columns.add(Projections.property("isActive"),"isActive");
		 
		 criteria.setProjection(columns);
		 criteria.setResultTransformer( new AliasToBeanResultTransformer(ComplaintActionDTO.class) );
		 List<ComplaintActionDTO> lstcomplaintactionDTO=  (List<ComplaintActionDTO>) findAll(criteria);
			return lstcomplaintactionDTO;  
	}
	
	
	
	

}
