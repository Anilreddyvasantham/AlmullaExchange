package com.amg.exchange.common.daoimpl;

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

import com.amg.exchange.common.dao.IBankAccountTypeDao;
import com.amg.exchange.common.dto.BankAccountTypeDTO;
import com.amg.exchange.common.model.BankAccountType;
import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class BankAccountTypeDaoImpl extends CommonDaoImpl implements IBankAccountTypeDao{
	
	private static final Logger log=Logger.getLogger(BankAccountTypeDaoImpl.class);

	
	@Override
	public List<BankAccountType> getAllRecordsForApproval(){
		DetachedCriteria criteria=DetachedCriteria.forClass(BankAccountType.class,"bankAccountType");
		criteria.add(Restrictions.eq("bankAccountType.isActive",Constants.U));
	//	criteria.add(Restrictions.isNull("bankAccountType.approvedBy"));
	//	criteria.add(Restrictions.isNull("bankAccountType.approvedDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankAccountType>) findAll(criteria);
	}
	

	@Override
	public List<BankAccountTypeDesc> getallRecordsRelatedBankAccountType(BigDecimal bankAccountTypeId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(BankAccountTypeDesc.class,"bankAccountTypeDesc");
		
		criteria.setFetchMode("bankAccountTypeDesc.bankAccountTypeId", FetchMode.JOIN);
		criteria.createAlias("bankAccountTypeDesc.bankAccountTypeId", "bankAccountTypeId",JoinType.INNER_JOIN);
		criteria.setFetchMode("bankAccountTypeDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("bankAccountTypeDesc.languageId", "languageId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankAccountTypeId.bankAccountTypeId",bankAccountTypeId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankAccountTypeDesc>) findAll(criteria);
	}


	@Override
	public void saveRecords(BankAccountType bankAccountType, BankAccountTypeDesc bankAccountTypedesc1, BankAccountTypeDesc bankAccountTypedesc2)throws Exception {
		try{
		getSession().saveOrUpdate(bankAccountType);
		getSession().saveOrUpdate(bankAccountTypedesc1);
		getSession().saveOrUpdate(bankAccountTypedesc2);
		}catch(Exception exception){
			log.error("Error Occured While Saving BankAccountType and BankAccountTypeDeac Records  :" +exception.getMessage());
		}
		
	}


	@Override
	public String approveReocrd(BigDecimal bankaccountTypePk,String userName) {
		
		String approveMsg;
		BankAccountType bankAccountType=(BankAccountType) getSession().get(BankAccountType.class, bankaccountTypePk);
		
		String approvedUser=bankAccountType.getApprovedBy();
		if(approvedUser==null)
		{
			bankAccountType.setIsActive(Constants.Yes);
			bankAccountType.setApprovedBy(userName);
			bankAccountType.setApprovedDate(new Date());
			bankAccountType.setRemarks("");
			getSession().update(bankAccountType);
			approveMsg="Sucess";
			
		}else
		{
			approveMsg="Fail";
		}
		
		return approveMsg;
	}


	@Override
	public List<BankAccountType> viewAllRecords() {
		DetachedCriteria criteria=DetachedCriteria.forClass(BankAccountType.class,"bankAccountType");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankAccountType>) findAll(criteria);
	}


	@Override
	public void deleteRecordPermanently(BigDecimal accountTypePk, BigDecimal accountTypedescPk, BigDecimal accountTypeLocalDescPk) {
		BankAccountType bankAccountType=(BankAccountType) getSession().get(BankAccountType.class, accountTypePk);
		BankAccountTypeDesc bankAccountTypeEng=(BankAccountTypeDesc) getSession().get(BankAccountTypeDesc.class, accountTypedescPk);
		BankAccountTypeDesc bankAccountTypeArb=(BankAccountTypeDesc) getSession().get(BankAccountTypeDesc.class, accountTypeLocalDescPk);
		getSession().delete(bankAccountTypeEng);	
		getSession().delete(bankAccountTypeArb);		
		getSession().delete(bankAccountType);		
	}


	@Override
	public List<String> getServiceCodeList(String query){
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountType.class, "bankAccountType");
		criteria.add(Restrictions.like("bankAccountType.bankAccountTypeCode", query, MatchMode.ANYWHERE).ignoreCase());
		 
		criteria.setProjection(Projections.property("bankAccountType.bankAccountTypeCode"));
		criteria.addOrder(Order.asc("bankAccountType.bankAccountTypeCode"));
		//criteria.add(Restrictions.eq("serviceMaster.isActive",  "N"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		 
		return (List<String>)findAll(criteria);
	}
	
	@Override
	public List<BankAccountType> fetchAllRecords(String accountTypeCode){
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountType.class, "bankAccountType");
		
		criteria.add(Restrictions.eq("bankAccountType.bankAccountTypeCode", accountTypeCode).ignoreCase());
		return (List<BankAccountType>)findAll(criteria);

	}

	@Override
	public void activateRecord(BigDecimal bankaccountTypePk,String userName){
		BankAccountType bankAccountType=(BankAccountType) getSession().get(BankAccountType.class, bankaccountTypePk);
		bankAccountType.setIsActive(Constants.U);
		bankAccountType.setModifiedBy(userName);
		bankAccountType.setModifiedDate(new Date());
		bankAccountType.setApprovedBy(null);
		bankAccountType.setApprovedDate(null);
		bankAccountType.setRemarks(null);
		getSession().update(bankAccountType);
		
	}


	@Override
	public void saveAllRecords(BankAccountTypeDTO bankAccountTypeVO)
			throws Exception {
		
	//	for(BankAccountTypeDTO bankAccountTypeVO:bankAccountTypeVOList){
		BankAccountType bankAccountType=new BankAccountType();
		bankAccountType.setBankAccountTypeId(bankAccountTypeVO.getBankAccountTypeId());
		bankAccountType.setBankAccountTypeCode(bankAccountTypeVO.getBankAccountTypeCode());
		bankAccountType.setCreatedBy(bankAccountTypeVO.getCreatedBy());
		bankAccountType.setCreatedDate(bankAccountTypeVO.getCreatedDate());
		bankAccountType.setModifiedBy(bankAccountTypeVO.getModifiedBy());
		bankAccountType.setModifiedDate(bankAccountTypeVO.getModifiedDate());
		bankAccountType.setIsActive(bankAccountTypeVO.getIsActive());
		bankAccountType.setApprovedBy(bankAccountTypeVO.getApprovedBy());
		bankAccountType.setApprovedDate(bankAccountTypeVO.getApprovedDate());
		bankAccountType.setRemarks(bankAccountTypeVO.getRemarks());

		BankAccountTypeDesc bankAccountTypeDesc1=new BankAccountTypeDesc();
		bankAccountTypeDesc1.setBankAccountTypeDescId(bankAccountTypeVO.getBankAccountTypeDescEnglishId());
		bankAccountTypeDesc1.setBankAccountTypeId(bankAccountType);
		LanguageType languageId=new LanguageType();
		languageId.setLanguageId(bankAccountTypeVO.getEnglishLanguageId());
		bankAccountTypeDesc1.setLanguageId(languageId);
		bankAccountTypeDesc1.setBankAccountTypeDesc(bankAccountTypeVO.getBankAccountTypeEnglishDesc());

		BankAccountTypeDesc bankAccountTypeDesc2=new BankAccountTypeDesc();
		bankAccountTypeDesc2.setBankAccountTypeDescId(bankAccountTypeVO.getBankAccountTypeDescArabicId());
		bankAccountTypeDesc2.setBankAccountTypeId(bankAccountType);
		LanguageType languageId1=new LanguageType();
		languageId1.setLanguageId(bankAccountTypeVO.getArabicLanguageId());
		bankAccountTypeDesc2.setLanguageId(languageId1);
		bankAccountTypeDesc2.setBankAccountTypeDesc(bankAccountTypeVO.getBankAccountTypeArabicDesc());

		try{
			getSession().saveOrUpdate(bankAccountType);
			getSession().saveOrUpdate(bankAccountTypeDesc1);
			getSession().saveOrUpdate(bankAccountTypeDesc2);
		}catch(Exception exception){
			log.error("Error Occured While Saving BankAccountType and BankAccountTypeDeac Records  :" +exception.getMessage());
		}

	//	}
	}


	@Override
	public List<BankAccountTypeDTO> viewRecords() {
		DetachedCriteria criteria=DetachedCriteria.forClass(BankAccountType.class,"bankAccountType");
		
		ProjectionList columns = Projections.projectionList();
		
		columns.add(Projections.property("bankAccountTypeCode"), "bankAccountTypeCode");
		columns.add(Projections.property("bankAccountTypeId"),"bankAccountTypeId");
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
		criteria.setResultTransformer( new AliasToBeanResultTransformer(BankAccountTypeDTO.class) );
	
		List<BankAccountTypeDTO> lstBankAccountTypeDTO=  (List<BankAccountTypeDTO>) findAll(criteria);
		
		return lstBankAccountTypeDTO;
	}


	@Override
	public List<BankAccountTypeDTO> getAllRecordsRelatedBankAccountType(
			BigDecimal bankAccountTypeId) {
		
		DetachedCriteria criteria=DetachedCriteria.forClass(BankAccountTypeDesc.class,"bankAccountTypeDesc1");
		
		criteria.setFetchMode("bankAccountTypeDesc1.bankAccountTypeId", FetchMode.JOIN);
		criteria.createAlias("bankAccountTypeDesc1.bankAccountTypeId", "bankAccountTypeId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("bankAccountTypeDesc1.languageId", FetchMode.JOIN);
		criteria.createAlias("bankAccountTypeDesc1.languageId", "languageId",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("bankAccountTypeId.bankAccountTypeId",bankAccountTypeId));
		
		ProjectionList columns = Projections.projectionList();
		
		columns.add(Projections.property("languageId.languageId"), "languageId");
		columns.add(Projections.property("bankAccountTypeDescId"),"bankAccountTypeDescId");
		columns.add(Projections.property("bankAccountTypeDesc"), "bankAccountTypeDesc");
		
		
		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(BankAccountTypeDTO.class) );
		
		List<BankAccountTypeDTO> lstBankAccountTypeDTO=  (List<BankAccountTypeDTO>) findAll(criteria);
		
		
		// TODO Auto-generated method stub
		return lstBankAccountTypeDTO;
	}


	@Override
	public List<BankAccountTypeDTO> fetchAccTypeCodeRecord(String accountTypeCode) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountType.class, "bankAccountType");
		
		criteria.add(Restrictions.eq("bankAccountType.bankAccountTypeCode", accountTypeCode).ignoreCase());
		
		ProjectionList columns = Projections.projectionList();
		
		columns.add(Projections.property("bankAccountTypeCode"), "bankAccountTypeCode");
		columns.add(Projections.property("bankAccountTypeId"),"bankAccountTypeId");
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
		criteria.setResultTransformer( new AliasToBeanResultTransformer(BankAccountTypeDTO.class) );
		
		List<BankAccountTypeDTO> lstBankAccountTypeDTO=  (List<BankAccountTypeDTO>) findAll(criteria);
		
		return lstBankAccountTypeDTO;
	}
}
