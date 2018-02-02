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

import com.amg.exchange.common.daoimpl.BankAccountTypeDaoImpl;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.dto.CommunicationMethodDTO;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.complaint.dao.ICommunicationMethodDao;
import com.amg.exchange.complaint.model.CommunicationMethod;
import com.amg.exchange.complaint.model.CommunicationMethodDesc;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class CommunicationMethodDaoImpl extends CommonDaoImpl implements ICommunicationMethodDao{
	
	private static final Logger log=Logger.getLogger(BankAccountTypeDaoImpl.class);

	
	@Override
	public List<CommunicationMethod> getAllRecordsForApproval(){
		DetachedCriteria criteria=DetachedCriteria.forClass(CommunicationMethod.class,"communicationMethod");
		criteria.add(Restrictions.eq("communicationMethod.isActive",Constants.U));
	//	criteria.add(Restrictions.isNull("bankAccountType.approvedBy"));
	//	criteria.add(Restrictions.isNull("bankAccountType.approvedDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CommunicationMethod>) findAll(criteria);
	}
	

	@Override
	public List<CommunicationMethodDesc> getallRecordsRelatedCommunicationMethod(BigDecimal communicationMethodId){
		DetachedCriteria criteria=DetachedCriteria.forClass(CommunicationMethodDesc.class,"communicationMethodDesc");
		
		criteria.setFetchMode("communicationMethodDesc.communicationMethodId", FetchMode.JOIN);
		criteria.createAlias("communicationMethodDesc.communicationMethodId", "communicationMethodId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("communicationMethodDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("communicationMethodDesc.languageId", "languageId",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("communicationMethodId.comMethodId",communicationMethodId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CommunicationMethodDesc>) findAll(criteria);
	}


	@Override
	public void saveRecords(CommunicationMethod communicationMethod,CommunicationMethodDesc communicationMethodDesc1,CommunicationMethodDesc communicationMethodDesc2)throws Exception{
		try{
		getSession().saveOrUpdate(communicationMethod);
		getSession().saveOrUpdate(communicationMethodDesc1);
		getSession().saveOrUpdate(communicationMethodDesc2);
		}catch(Exception exception){
			log.error("Error Occured While Saving CommunicationMethod and CommunicationMethodDeac Records  :" +exception.getMessage());
		}
		
	}


	@Override
	public String approveReocrd(BigDecimal communicationMethodPk,String userName){
		
		String approveMsg;
		CommunicationMethod communicationMethod=(CommunicationMethod) getSession().get(CommunicationMethod.class, communicationMethodPk);
		
		String approvedUser=communicationMethod.getApprovedBy();
		if(approvedUser==null)
		{
			communicationMethod.setIsActive(Constants.Yes);
			communicationMethod.setApprovedBy(userName);
			communicationMethod.setApprovedDate(new Date());
			communicationMethod.setRemarks("");
			getSession().update(communicationMethod);
			approveMsg="Sucess";
			
		}else
		{
			approveMsg="Fail";
		}
		
		return approveMsg;
	}


	@Override
	public List<CommunicationMethod> viewAllRecords(){
		DetachedCriteria criteria=DetachedCriteria.forClass(CommunicationMethod.class,"communicationMethod");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CommunicationMethod>) findAll(criteria);
	}


	@Override
	public void deleteRecordPermanently(BigDecimal communicationMethodPk,BigDecimal communicationMethodDescPk,BigDecimal communicationMethodLocalDescPk){
		CommunicationMethod communicationMethod=(CommunicationMethod) getSession().get(CommunicationMethod.class, communicationMethodPk);
		CommunicationMethodDesc communicationMethodEng=(CommunicationMethodDesc) getSession().get(CommunicationMethodDesc.class, communicationMethodDescPk);
		CommunicationMethodDesc communicationMethodArb=(CommunicationMethodDesc) getSession().get(CommunicationMethodDesc.class, communicationMethodLocalDescPk);
		getSession().delete(communicationMethodEng);	
		getSession().delete(communicationMethodArb);		
		getSession().delete(communicationMethod);		
	}


	@Override
	public List<String> getCommunicationMethodCodeList(String query){
		DetachedCriteria criteria = DetachedCriteria.forClass(CommunicationMethod.class, "communicationMethod");
		criteria.add(Restrictions.like("communicationMethod.comMethodCode", query, MatchMode.ANYWHERE).ignoreCase());
		 
		criteria.setProjection(Projections.property("communicationMethod.comMethodCode"));
		criteria.addOrder(Order.asc("communicationMethod.comMethodCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		 
		return (List<String>)findAll(criteria);
	}
	
	@Override
	public List<CommunicationMethod> fetchAllRecords(String communicationMethodCode){
		DetachedCriteria criteria = DetachedCriteria.forClass(CommunicationMethod.class, "communicationMethod");
		
		criteria.add(Restrictions.eq("communicationMethod.comMethodCode", communicationMethodCode).ignoreCase());
		return (List<CommunicationMethod>)findAll(criteria);

	}

	@Override
	public void activateRecord(BigDecimal communicationMethodPk,String userName){
		CommunicationMethod communicationMethod=(CommunicationMethod) getSession().get(CommunicationMethod.class, communicationMethodPk);
		communicationMethod.setIsActive(Constants.U);
		communicationMethod.setModifiedBy(userName);
		communicationMethod.setModifiedDate(new Date());
		communicationMethod.setApprovedBy(null);
		communicationMethod.setApprovedDate(null);
		communicationMethod.setRemarks(null);
		getSession().update(communicationMethod);
		
	}


	@Override
	public void saveAllRecords(CommunicationMethodDTO communicationMethodDTO)throws Exception{
		
	//	for(CommunicationMethodDTO communicationMethodDTO:communicationMethodDTOList){
		CommunicationMethod communicationMethod=new CommunicationMethod();
		communicationMethod.setComMethodId(communicationMethodDTO.getCommunicationMethodPk());
		communicationMethod.setComMethodCode(communicationMethodDTO.getCommunicationMethodCode());
		communicationMethod.setEmail(communicationMethodDTO.getEmail());
		
		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(communicationMethodDTO.getCountryId());
		communicationMethod.setApplicationCountryId(countryMaster);
		
		communicationMethod.setCreatedBy(communicationMethodDTO.getCreatedBy());
		communicationMethod.setCreatedDate(communicationMethodDTO.getCreatedDate());
		communicationMethod.setModifiedBy(communicationMethodDTO.getModifiedBy());
		communicationMethod.setModifiedDate(communicationMethodDTO.getModifiedDate());
		communicationMethod.setIsActive(communicationMethodDTO.getIsActive());
		communicationMethod.setApprovedBy(communicationMethodDTO.getApprovedBy());
		communicationMethod.setApprovedDate(communicationMethodDTO.getApprovedDate());
		communicationMethod.setRemarks(communicationMethodDTO.getRemarks());

		CommunicationMethodDesc communicationMethodDesc1=new CommunicationMethodDesc();
		communicationMethodDesc1.setCommMethDescId(communicationMethodDTO.getCommunicationMethodDescPk());
		communicationMethodDesc1.setCommunicationMethodId(communicationMethod);
		
		LanguageType languageId=new LanguageType();
		languageId.setLanguageId(communicationMethodDTO.getEngLanguageId());
		communicationMethodDesc1.setLanguageId(languageId);
		communicationMethodDesc1.setShortDescription(communicationMethodDTO.getCommunicationShortDesc());
		communicationMethodDesc1.setFullDescription(communicationMethodDTO.getCommunicationFullDesc());

		CommunicationMethodDesc communicationMethodDesc2=new CommunicationMethodDesc();
		communicationMethodDesc2.setCommMethDescId(communicationMethodDTO.getCommunicationMethodLocalDescPk());
		communicationMethodDesc2.setCommunicationMethodId(communicationMethod);
		LanguageType languageId1=new LanguageType();
		languageId1.setLanguageId(communicationMethodDTO.getArbLanguageId());
		communicationMethodDesc2.setLanguageId(languageId1);
		communicationMethodDesc2.setShortDescription(communicationMethodDTO.getCommunicationShortDescLocal());
		communicationMethodDesc2.setFullDescription(communicationMethodDTO.getCommunicationFullDescLocal());

		try{
			getSession().saveOrUpdate(communicationMethod);
			getSession().saveOrUpdate(communicationMethodDesc1);
			getSession().saveOrUpdate(communicationMethodDesc2);
		}catch(Exception exception){
			log.error("Error Occured While Saving CommunicationMethod and CommunicationMethodDeac Records  :" +exception.getMessage());
		}

	//	}
	}


	@Override
	public List<CommunicationMethodDTO> viewRecords() {
		DetachedCriteria criteria=DetachedCriteria.forClass(CommunicationMethod.class,"communicationMethod");
		
		criteria.setFetchMode("communicationMethod.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("communicationMethod.applicationCountryId", "applicationCountryId",JoinType.INNER_JOIN);
		
		ProjectionList columns = Projections.projectionList();
		columns.add(Projections.property("comMethodCode"), "communicationMethodCode");
		columns.add(Projections.property("comMethodId"), "communicationMethodId");
		columns.add(Projections.property("email"),"email");
		columns.add(Projections.property("applicationCountryId.countryId"),"countryId");
		columns.add(Projections.property("createdBy"), "createdBy");
		columns.add(Projections.property("createdDate"),"createdDate");
		columns.add(Projections.property("modifiedBy"), "modifiedBy");
		columns.add(Projections.property("modifiedDate"),"modifiedDate");
		columns.add(Projections.property("isActive"), "isActive");
		columns.add(Projections.property("approvedBy"),"approvedBy");
		columns.add(Projections.property("approvedDate"), "approvedDate");
		columns.add(Projections.property("modifiedDate"),"modifiedDate");
		columns.add(Projections.property("remarks"), "remarks");
		
		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(CommunicationMethodDTO.class) );
	
		List<CommunicationMethodDTO> listCommunicationDTO=  (List<CommunicationMethodDTO>) findAll(criteria);
		
		return listCommunicationDTO;
	}


	@Override
	public List<CommunicationMethodDTO> getAllRecordsRelatedCommunicationMethod(BigDecimal communicationMethodId){
		
		DetachedCriteria criteria=DetachedCriteria.forClass(CommunicationMethodDesc.class,"communicationMethodDesc");
		
		criteria.setFetchMode("communicationMethodDesc.communicationMethodId", FetchMode.JOIN);
		criteria.createAlias("communicationMethodDesc.communicationMethodId", "communicationMethodId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("communicationMethodDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("communicationMethodDesc.languageId", "languageId",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("communicationMethodId.comMethodId",communicationMethodId));
		
		ProjectionList columns = Projections.projectionList();
		columns.add(Projections.property("shortDescription"), "communicationShortDesc");
		columns.add(Projections.property("fullDescription"), "communicationFullDesc");
		//columns.add(Projections.property("communicationMethodId.comMethodId"), "communicationMethodDescId");
		columns.add(Projections.property("commMethDescId"), "communicationMethodDescId");
		columns.add(Projections.property("languageId.languageId"), "languageId");
		
		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(CommunicationMethodDTO.class) );
		
		List<CommunicationMethodDTO> lstCommunicationMethodDTO=  (List<CommunicationMethodDTO>) findAll(criteria);
		
		return lstCommunicationMethodDTO;
	}


	@Override
	public List<CommunicationMethodDTO> fetchCommunicationMethodCodeRecord(String communicationMethodCode){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CommunicationMethod.class, "communicationMethod");
		criteria.setFetchMode("communicationMethod.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("communicationMethod.applicationCountryId", "applicationCountryId",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("communicationMethod.comMethodCode", communicationMethodCode).ignoreCase());
		
		ProjectionList columns = Projections.projectionList();
		
		columns.add(Projections.property("comMethodCode"), "communicationMethodCode");
		columns.add(Projections.property("email"),"email");
		columns.add(Projections.property("comMethodId"),"communicationMethodPk");
		columns.add(Projections.property("applicationCountryId.countryId"),"countryId");
		columns.add(Projections.property("createdBy"), "createdBy");
		columns.add(Projections.property("createdDate"),"createdDate");
		columns.add(Projections.property("modifiedBy"), "modifiedBy");
		columns.add(Projections.property("modifiedDate"),"modifiedDate");
		columns.add(Projections.property("isActive"), "isActive");
		columns.add(Projections.property("approvedBy"),"approvedBy");
		columns.add(Projections.property("approvedDate"), "approvedDate");
		columns.add(Projections.property("modifiedDate"),"modifiedDate");
		columns.add(Projections.property("remarks"), "remarks");
		
		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(CommunicationMethodDTO.class) );
		
		List<CommunicationMethodDTO> lstCommunicationMethodDTO=  (List<CommunicationMethodDTO>) findAll(criteria);
		
		return lstCommunicationMethodDTO;
	}


@Override
public List<CommunicationMethodDTO> ApproveRecords() {
	  DetachedCriteria criteria=DetachedCriteria.forClass(CommunicationMethod.class,"communicationMethod");
		
		criteria.setFetchMode("communicationMethod.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("communicationMethod.applicationCountryId", "applicationCountryId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("communicationMethod.isActive", Constants.U));
		ProjectionList columns = Projections.projectionList();
		columns.add(Projections.property("comMethodCode"), "communicationMethodCode");
		columns.add(Projections.property("comMethodId"), "communicationMethodId");
		columns.add(Projections.property("email"),"email");
		columns.add(Projections.property("applicationCountryId.countryId"),"countryId");
		columns.add(Projections.property("createdBy"), "createdBy");
		columns.add(Projections.property("createdDate"),"createdDate");
		columns.add(Projections.property("modifiedBy"), "modifiedBy");
		columns.add(Projections.property("modifiedDate"),"modifiedDate");
		columns.add(Projections.property("isActive"), "isActive");
		columns.add(Projections.property("approvedBy"),"approvedBy");
		columns.add(Projections.property("approvedDate"), "approvedDate");
		columns.add(Projections.property("modifiedDate"),"modifiedDate");
		columns.add(Projections.property("remarks"), "remarks");
		
		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(CommunicationMethodDTO.class) );
	
		List<CommunicationMethodDTO> listCommunicationDTO=  (List<CommunicationMethodDTO>) findAll(criteria);
		
		return listCommunicationDTO;
}


@Override
public List<CommunicationMethodDTO> getAllRecordsApproveRelatedCommunicationMethod(BigDecimal communicationMethodId) {
	  DetachedCriteria criteria=DetachedCriteria.forClass(CommunicationMethodDesc.class,"communicationMethodDesc");
		
		criteria.setFetchMode("communicationMethodDesc.communicationMethodId", FetchMode.JOIN);
		criteria.createAlias("communicationMethodDesc.communicationMethodId", "communicationMethodId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("communicationMethodDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("communicationMethodDesc.languageId", "languageId",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("communicationMethodId.comMethodId",communicationMethodId));
		
		ProjectionList columns = Projections.projectionList();
		columns.add(Projections.property("shortDescription"), "communicationShortDesc");
		columns.add(Projections.property("fullDescription"), "communicationFullDesc");
		//columns.add(Projections.property("communicationMethodId.comMethodId"), "communicationMethodDescId");
		columns.add(Projections.property("commMethDescId"), "communicationMethodDescId");
		columns.add(Projections.property("languageId.languageId"), "languageId");
		
		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(CommunicationMethodDTO.class) );
		
		List<CommunicationMethodDTO> lstCommunicationMethodDTO=  (List<CommunicationMethodDTO>) findAll(criteria);
		
		return lstCommunicationMethodDTO;
}
}
