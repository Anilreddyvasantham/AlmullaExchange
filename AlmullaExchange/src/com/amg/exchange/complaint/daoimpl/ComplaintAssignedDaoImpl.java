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
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.complaint.DTO.ComplaintAssignedDTO;
import com.amg.exchange.complaint.dao.IComplaintAssignedDao;
import com.amg.exchange.complaint.model.ComplaintAssigned;
import com.amg.exchange.complaint.model.ComplaintAssignedDesc;
import com.amg.exchange.util.Constants;

@SuppressWarnings({"rawtypes","unchecked"})
@Repository
public class ComplaintAssignedDaoImpl extends CommonDaoImpl implements IComplaintAssignedDao {
	  private static final Logger log=Logger.getLogger(ComplaintAssignedDaoImpl.class);
	  @Override
	  public List<String> getComplaintAssignedCodeList(String query) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintAssigned.class, "complaintAssigned");
		    
		    /*criteria.add(Restrictions.like("complaintAssigned.complaintAssignedCode", query, MatchMode.ANYWHERE).ignoreCase());
        	criteria.setProjection(Projections.property("complaintAssigned.complaintAssignedCode"));
			criteria.addOrder(Order.asc("complaintAssigned.complaintAssignedCode"));
			*/
		    criteria.add(Restrictions.like("complaintAssigned.assignTo", query, MatchMode.ANYWHERE).ignoreCase());
        	criteria.setProjection(Projections.property("complaintAssigned.assignTo"));
			criteria.addOrder(Order.asc("complaintAssigned.assignTo"));
			
			
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			 List<String> lstComplaintAssignedCode=(List<String>)findAll(criteria);
			return lstComplaintAssignedCode;
	  }

	  @Override
	  public void saveAllComplaintAssigenedRecords(ComplaintAssignedDTO complaintAssignedDTO)throws Exception{
		   ComplaintAssigned complaintAssigned=new ComplaintAssigned();
		   CountryMaster countryMaster=new CountryMaster();
		   countryMaster.setCountryId(complaintAssignedDTO.getApplicationCountryId());
		   complaintAssigned.setApplicationCountryId(countryMaster);
		   complaintAssigned.setComplaintAssignedCode(complaintAssignedDTO.getComplaintAssignedCode());
		   complaintAssigned.setComplaintAssignedId(complaintAssignedDTO.getComplaintAssignedId());
		   complaintAssigned.setAssignTo(complaintAssignedDTO.getComplaintAssignedTo());
		   complaintAssigned.setLogComplaint(complaintAssignedDTO.getLogCompalint());
		   complaintAssigned.setCreatedBy(complaintAssignedDTO.getCreatedBy());
		   complaintAssigned.setCreatedDate(complaintAssignedDTO.getCreatedDate());
		   complaintAssigned.setModifiedBy(complaintAssignedDTO.getModifiedBy());
		   complaintAssigned.setModifiedDate(complaintAssignedDTO.getModifiedDate());
		   complaintAssigned.setApprovedBy(complaintAssignedDTO.getApprovedBy());
		   complaintAssigned.setApprovedDate(complaintAssignedDTO.getApprovedDate());
		   complaintAssigned.setIsActive(complaintAssignedDTO.getIsActive());
		   complaintAssigned.setRemarks(complaintAssignedDTO.getRemarks());
		   
		   ComplaintAssignedDesc complaintAssignedDesc1= new ComplaintAssignedDesc();
		   complaintAssignedDesc1.setComplaintAssignedDescId(complaintAssignedDTO.getComplaintEnglishFullDescriptionId());
		   complaintAssignedDesc1.setComplaintAssigned(complaintAssigned);
		   LanguageType languageId=new LanguageType();
		   languageId.setLanguageId(complaintAssignedDTO.getEnglishLanguageId());
		   complaintAssignedDesc1.setLanguageId(languageId); 
		   complaintAssignedDesc1.setFullDescription(complaintAssignedDTO.getComplaintEnglishFullDescription());
		   complaintAssignedDesc1.setShortDescription(complaintAssignedDTO.getComplaintEnglishShortDescription());
		   
		   ComplaintAssignedDesc complaintAssignedDesc2= new ComplaintAssignedDesc();
		   complaintAssignedDesc2.setComplaintAssignedDescId(complaintAssignedDTO.getComplaintArabicFullDescriptionId());
		   complaintAssignedDesc2.setComplaintAssigned(complaintAssigned);
		   LanguageType languageId2=new LanguageType();
		   languageId2.setLanguageId(complaintAssignedDTO.getArabicLanguageId());
		   complaintAssignedDesc2.setLanguageId(languageId2); 
		   complaintAssignedDesc2.setFullDescription(complaintAssignedDTO.getComplaintArabicFullDescription());
		   complaintAssignedDesc2.setShortDescription(complaintAssignedDTO.getComplaintArabicShortDescription());
		   
		   try {
		    getSession().saveOrUpdate(complaintAssigned);
		    getSession().saveOrUpdate(complaintAssignedDesc1);
		    getSession().saveOrUpdate(complaintAssignedDesc2);
		    
	  } catch (Exception exception) {
		    log.error("Error Occured While Saving ComplaintAssigned and ComplaintAssigned Records  :" +exception.getMessage());
	  }
	  }
	  @Override
	  public List<ComplaintAssignedDTO> getComPlaintAssignedAllValues(String complaintAssignedTo) {
		 DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintAssigned.class,"complaintAssigned");
		 //criteria.add(Restrictions.eq("complaintAssigned.complaintAssignedCode", complaintAssignedCode).ignoreCase());
		 criteria.add(Restrictions.eq("complaintAssigned.assignTo", complaintAssignedTo).ignoreCase());
		 ProjectionList columns=Projections.projectionList();
		 columns.add(Projections.property("complaintAssignedCode"),"complaintAssignedCode");
		 columns.add(Projections.property("complaintAssignedId"),"complaintAssignedId");
		 columns.add(Projections.property("applicationCountryId.countryId"),"applicationCountryId");
		 columns.add(Projections.property("assignTo"),"complaintAssignedTo");
		 columns.add(Projections.property("logComplaint"),"logCompalint");
		 columns.add(Projections.property("createdBy"),"createdBy");
		 columns.add(Projections.property("createdDate"),"createdDate");
		 columns.add(Projections.property("modifiedBy"),"modifiedBy");
		 columns.add(Projections.property("modifiedDate"),"modifiedDate");
		 columns.add(Projections.property("approvedBy"),"approvedBy");
		 columns.add(Projections.property("approvedDate"),"approvedDate");
		 columns.add(Projections.property("remarks"),"remarks");
		 columns.add(Projections.property("isActive"),"isActive");
		 
		 criteria.setProjection(columns);
		 criteria.setResultTransformer( new AliasToBeanResultTransformer(ComplaintAssignedDTO.class) );
	  List<ComplaintAssignedDTO> lstBankAccountTypeDTO=  (List<ComplaintAssignedDTO>) findAll(criteria);
			return lstBankAccountTypeDTO;
	  }
	  @Override
	  public List<ComplaintAssignedDTO> getcomplaintAssignedCodeBasedOnComplaintDescription(BigDecimal complaintAssignedId) {
		    DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintAssignedDesc.class,"complaintAssignedDesc");
		        criteria.setFetchMode("complaintAssignedDesc.complaintAssigned", FetchMode.JOIN);
			criteria.createAlias("complaintAssignedDesc.complaintAssigned", "complaintAssigned",JoinType.INNER_JOIN);
			criteria.setFetchMode("complaintAssignedDesc.languageId", FetchMode.JOIN);
			criteria.createAlias("complaintAssignedDesc.languageId", "languageId",JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("complaintAssigned.complaintAssignedId",complaintAssignedId));
			
			ProjectionList columns = Projections.projectionList();
			columns.add(Projections.property("languageId.languageId"), "languageId");
			columns.add(Projections.property("fullDescription"),"fullDescription");
			columns.add(Projections.property("shortDescription"), "shortDescription");
			columns.add(Projections.property("complaintAssignedDescId"), "complaintAssignedDescId");
			criteria.setProjection(columns);
			 criteria.setResultTransformer( new AliasToBeanResultTransformer(ComplaintAssignedDTO.class) );
		  List<ComplaintAssignedDTO> lstBankAccountTypeDTO=  (List<ComplaintAssignedDTO>) findAll(criteria);
				return lstBankAccountTypeDTO;
	  }

	  @Override
	  public void getComplientAssignedUpdateRecord(ComplaintAssignedDTO complaintAssignedDTO)throws Exception {
		    ComplaintAssigned complaintAssigned=new ComplaintAssigned();
			   CountryMaster countryMaster=new CountryMaster();
			   countryMaster.setCountryId(complaintAssignedDTO.getApplicationCountryId());
			   complaintAssigned.setApplicationCountryId(countryMaster);
			   complaintAssigned.setComplaintAssignedCode(complaintAssignedDTO.getComplaintAssignedCode());
			   complaintAssigned.setComplaintAssignedId(complaintAssignedDTO.getComplaintAssignedId());
			   complaintAssigned.setAssignTo(complaintAssignedDTO.getComplaintAssignedTo());
			   complaintAssigned.setLogComplaint(complaintAssignedDTO.getLogCompalint());
			   complaintAssigned.setCreatedBy(complaintAssignedDTO.getCreatedBy());
			   complaintAssigned.setCreatedDate(complaintAssignedDTO.getCreatedDate());
			   complaintAssigned.setModifiedBy(complaintAssignedDTO.getModifiedBy());
			   complaintAssigned.setModifiedDate(complaintAssignedDTO.getModifiedDate());
			   complaintAssigned.setApprovedBy(complaintAssignedDTO.getApprovedBy());
			   complaintAssigned.setApprovedDate(complaintAssignedDTO.getApprovedDate());
			   complaintAssigned.setIsActive(complaintAssignedDTO.getIsActive());
			   complaintAssigned.setRemarks(complaintAssignedDTO.getRemarks());
			   
			   ComplaintAssignedDesc complaintAssignedDesc1= new ComplaintAssignedDesc();
			   complaintAssignedDesc1.setComplaintAssignedDescId(complaintAssignedDTO.getComplaintEnglishFullDescriptionId());
			   complaintAssignedDesc1.setComplaintAssigned(complaintAssigned);
			   LanguageType languageId=new LanguageType();
			   languageId.setLanguageId(complaintAssignedDTO.getEnglishLanguageId());
			   complaintAssignedDesc1.setLanguageId(languageId); 
			   complaintAssignedDesc1.setFullDescription(complaintAssignedDTO.getComplaintEnglishFullDescription());
			   complaintAssignedDesc1.setShortDescription(complaintAssignedDTO.getComplaintEnglishShortDescription());
			   
			   ComplaintAssignedDesc complaintAssignedDesc2= new ComplaintAssignedDesc();
			   complaintAssignedDesc2.setComplaintAssignedDescId(complaintAssignedDTO.getComplaintArabicFullDescriptionId());
			   complaintAssignedDesc2.setComplaintAssigned(complaintAssigned);
			   LanguageType languageId2=new LanguageType();
			   languageId2.setLanguageId(complaintAssignedDTO.getArabicLanguageId());
			   complaintAssignedDesc2.setLanguageId(languageId2); 
			   complaintAssignedDesc2.setFullDescription(complaintAssignedDTO.getComplaintArabicFullDescription());
			   complaintAssignedDesc2.setShortDescription(complaintAssignedDTO.getComplaintArabicShortDescription());
			   
			   try {
			    getSession().saveOrUpdate(complaintAssigned);
			    getSession().saveOrUpdate(complaintAssignedDesc1);
			    getSession().saveOrUpdate(complaintAssignedDesc2);
			    
		  } catch (Exception exception) {
			    log.error("Error Occured While Saving ComplaintAssigned and ComplaintAssigned Records  :" +exception.getMessage());
		  }    
		    
	  }

	  @Override
	  public List<ComplaintAssignedDTO> getviewRecords() {
		    DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintAssigned.class,"complaintAssigned");
			
			 ProjectionList columns=Projections.projectionList();
			 columns.add(Projections.property("complaintAssignedCode"),"complaintAssignedCode");
			 columns.add(Projections.property("complaintAssignedId"),"complaintAssignedId");
			 columns.add(Projections.property("applicationCountryId.countryId"),"applicationCountryId");
			// columns.add(Projections.property("assignTo"),"complaintAssignedTo");
			 columns.add(Projections.property("logComplaint"),"logCompalint");
			 columns.add(Projections.property("createdBy"),"createdBy");
			 columns.add(Projections.property("createdDate"),"createdDate");
			 columns.add(Projections.property("modifiedBy"),"modifiedBy");
			 columns.add(Projections.property("modifiedDate"),"modifiedDate");
			 columns.add(Projections.property("approvedBy"),"approvedBy");
			 columns.add(Projections.property("approvedDate"),"approvedDate");
			 columns.add(Projections.property("remarks"),"remarks");
			 columns.add(Projections.property("isActive"),"isActive");
			 
			 criteria.setProjection(columns);
			 criteria.setResultTransformer( new AliasToBeanResultTransformer(ComplaintAssignedDTO.class) );
		  List<ComplaintAssignedDTO> lstBankAccountTypeDTO=  (List<ComplaintAssignedDTO>) findAll(criteria);
				return lstBankAccountTypeDTO;
	  }

	  @Override
	  public List<ComplaintAssignedDTO> getAllRecordsRelatedComplaintAssigned(BigDecimal complaintAssignedId) {
		    DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintAssignedDesc.class,"complaintAssignedDesc");
		        criteria.setFetchMode("complaintAssignedDesc.complaintAssigned", FetchMode.JOIN);
			criteria.createAlias("complaintAssignedDesc.complaintAssigned", "complaintAssigned",JoinType.INNER_JOIN);
			criteria.setFetchMode("complaintAssignedDesc.languageId", FetchMode.JOIN);
			criteria.createAlias("complaintAssignedDesc.languageId", "languageId",JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("complaintAssigned.complaintAssignedId",complaintAssignedId));
			
			ProjectionList columns = Projections.projectionList();
			columns.add(Projections.property("languageId.languageId"), "languageId");
			columns.add(Projections.property("fullDescription"),"fullDescription");
			columns.add(Projections.property("shortDescription"), "shortDescription");
			columns.add(Projections.property("complaintAssignedDescId"), "complaintAssignedDescId");
			criteria.setProjection(columns);
			 criteria.setResultTransformer( new AliasToBeanResultTransformer(ComplaintAssignedDTO.class) );
		  List<ComplaintAssignedDTO> lstBankAccountTypeDTO=  (List<ComplaintAssignedDTO>) findAll(criteria);
				return lstBankAccountTypeDTO;
	  }

	  @Override
	  public List<ComplaintAssignedDTO> getfetchRecordForApproval() {
		    DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintAssigned.class,"complaintAssigned");
			 criteria.add(Restrictions.eq("complaintAssigned.isActive",Constants.U));
			 ProjectionList columns=Projections.projectionList();
			 columns.add(Projections.property("complaintAssignedCode"),"complaintAssignedCode");
			 columns.add(Projections.property("complaintAssignedId"),"complaintAssignedId");
			 columns.add(Projections.property("applicationCountryId.countryId"),"applicationCountryId");
			 //columns.add(Projections.property("assignTo"),"complaintAssignedTo");
			 columns.add(Projections.property("logComplaint"),"logCompalint");
			 columns.add(Projections.property("createdBy"),"createdBy");
			 columns.add(Projections.property("createdDate"),"createdDate");
			 columns.add(Projections.property("modifiedBy"),"modifiedBy");
			 columns.add(Projections.property("modifiedDate"),"modifiedDate");
			 columns.add(Projections.property("approvedBy"),"approvedBy");
			 columns.add(Projections.property("approvedDate"),"approvedDate");
			 columns.add(Projections.property("remarks"),"remarks");
			 columns.add(Projections.property("isActive"),"isActive");
			 
			 criteria.setProjection(columns);
			 criteria.setResultTransformer( new AliasToBeanResultTransformer(ComplaintAssignedDTO.class) );
		  List<ComplaintAssignedDTO> lstBankAccountTypeDTO=  (List<ComplaintAssignedDTO>) findAll(criteria);
				return lstBankAccountTypeDTO;
	  }

	  @Override
	  public List<ComplaintAssignedDTO> getAllRecordsRelatedComplaintAssignedApproval(BigDecimal complaintAssignedId) {
		    DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintAssignedDesc.class,"complaintAssignedDesc");
		        criteria.setFetchMode("complaintAssignedDesc.complaintAssigned", FetchMode.JOIN);
			criteria.createAlias("complaintAssignedDesc.complaintAssigned", "complaintAssigned",JoinType.INNER_JOIN);
			criteria.setFetchMode("complaintAssignedDesc.languageId", FetchMode.JOIN);
			criteria.createAlias("complaintAssignedDesc.languageId", "languageId",JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("complaintAssigned.complaintAssignedId",complaintAssignedId));
			
			ProjectionList columns = Projections.projectionList();
			columns.add(Projections.property("languageId.languageId"), "languageId");
			columns.add(Projections.property("fullDescription"),"fullDescription");
			columns.add(Projections.property("shortDescription"), "shortDescription");
			columns.add(Projections.property("complaintAssignedDescId"), "complaintAssignedDescId");
			criteria.setProjection(columns);
			 criteria.setResultTransformer( new AliasToBeanResultTransformer(ComplaintAssignedDTO.class) );
		  List<ComplaintAssignedDTO> lstBankAccountTypeDTO=  (List<ComplaintAssignedDTO>) findAll(criteria);
				return lstBankAccountTypeDTO;
	  }

	  @Override
	  public String getApproveRecord(BigDecimal complaintAssignedId, String userName) {
		    String approvalMsg;
		    ComplaintAssigned complaintAssigned= (ComplaintAssigned) getSession().get(ComplaintAssigned.class, complaintAssignedId);
		    String approvalUser=complaintAssigned.getApprovedBy();
		    if(approvalUser==null){
			      complaintAssigned.setIsActive(Constants.Yes);
			      complaintAssigned.setApprovedBy(userName);
			      complaintAssigned.setApprovedDate(new Date());
			      getSession().update(complaintAssigned);
			      approvalMsg="Success";
		    }else{
			      approvalMsg="Fail";      
		    }
		    return approvalMsg;
	  }

	  @Override
	  public void deActivateRecord(BigDecimal complaintAssignedId, String userName) {
		    ComplaintAssigned complaintAssigned= (ComplaintAssigned) getSession().get(ComplaintAssigned.class, complaintAssignedId);
		    complaintAssigned.setIsActive(Constants.U);
		    complaintAssigned.setModifiedBy(userName);
		    complaintAssigned.setModifiedDate(new Date());
		    complaintAssigned.setApprovedBy(null);
		    complaintAssigned.setApprovedDate(null);
		    complaintAssigned.setRemarks(null);
		    getSession().saveOrUpdate(complaintAssigned);
		    
	  }

	  @Override
	  public void deleteRecordPermentely(BigDecimal complaintAssignedId, BigDecimal complaintEnglishFullDescriptionPK, BigDecimal complaintArabicFullDescriptionPK) {
		    ComplaintAssigned complaintAssigned= (ComplaintAssigned) getSession().get(ComplaintAssigned.class, complaintAssignedId); 
		    ComplaintAssignedDesc complaintAssignedDesc1= (ComplaintAssignedDesc) getSession().get(ComplaintAssignedDesc.class, complaintEnglishFullDescriptionPK);
		    ComplaintAssignedDesc complaintAssignedDesc2= (ComplaintAssignedDesc) getSession().get(ComplaintAssignedDesc.class, complaintArabicFullDescriptionPK);
		    getSession().delete(complaintAssigned);
		    getSession().delete(complaintAssignedDesc1);
		    getSession().delete(complaintAssignedDesc2);
	  }
	  
	  @Override
	  public List<ComplaintAssignedDesc> getAllComplaintAssignedForComplaintCreation(BigDecimal countryId , BigDecimal languageId) {
		  // TODO Auto-generated method stub
		  DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintAssignedDesc.class,"complaintAssignedDesc");

		  criteria.setFetchMode("complaintAssignedDesc.complaintAssigned", FetchMode.JOIN);
		  criteria.createAlias("complaintAssignedDesc.complaintAssigned", "complaintAssigned",JoinType.INNER_JOIN);
		  criteria.add(Restrictions.eq("complaintAssigned.isActive",Constants.Yes));
		  criteria.add(Restrictions.eq("complaintAssigned.logComplaint",Constants.Yes));

		  criteria.setFetchMode("complaintAssigned.applicationCountryId", FetchMode.JOIN);
		  criteria.createAlias("complaintAssigned.applicationCountryId", "applicationCountryId",JoinType.INNER_JOIN);
		  criteria.add(Restrictions.eq("applicationCountryId.countryId",countryId));

		  criteria.setFetchMode("complaintAssignedDesc.languageId", FetchMode.JOIN);
		  criteria.createAlias("complaintAssignedDesc.languageId", "languageId",JoinType.INNER_JOIN);
		  criteria.add(Restrictions.eq("languageId.languageId",languageId));
		  criteria.addOrder(Order.asc("complaintAssigned.complaintAssignedCode"));

		  criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		  List<ComplaintAssignedDesc> lstCompAssDesc =  (List<ComplaintAssignedDesc>) findAll(criteria);		  

		  return lstCompAssDesc;
	  }

	  @Override
	  public List<ComplaintAssignedDTO> getComPlaintAssignedIdDuplicateChecking(String complaintAssignedId) {
		    DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintAssigned.class,"complaintAssigned");
			 criteria.add(Restrictions.eq("complaintAssigned.assignTo", complaintAssignedId).ignoreCase());
			 ProjectionList columns=Projections.projectionList();
			 columns.add(Projections.property("complaintAssignedCode"),"complaintAssignedCode");
			 columns.add(Projections.property("complaintAssignedId"),"complaintAssignedId");
			 columns.add(Projections.property("applicationCountryId.countryId"),"applicationCountryId");
			 //columns.add(Projections.property("assignTo"),"complaintAssignedTo");
			 columns.add(Projections.property("logComplaint"),"logCompalint");
			 columns.add(Projections.property("createdBy"),"createdBy");
			 columns.add(Projections.property("createdDate"),"createdDate");
			 columns.add(Projections.property("modifiedBy"),"modifiedBy");
			 columns.add(Projections.property("modifiedDate"),"modifiedDate");
			 columns.add(Projections.property("approvedBy"),"approvedBy");
			 columns.add(Projections.property("approvedDate"),"approvedDate");
			 columns.add(Projections.property("remarks"),"remarks");
			 columns.add(Projections.property("isActive"),"isActive");
			 
			 criteria.setProjection(columns);
			 criteria.setResultTransformer( new AliasToBeanResultTransformer(ComplaintAssignedDTO.class) );
		  List<ComplaintAssignedDTO> lstBankAccountTypeDTO=  (List<ComplaintAssignedDTO>) findAll(criteria);
				return lstBankAccountTypeDTO;
	  }



}
