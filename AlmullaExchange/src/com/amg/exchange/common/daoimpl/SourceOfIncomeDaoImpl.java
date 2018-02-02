package com.amg.exchange.common.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.dao.ISourceOfIncomeDao;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.InsuranceMaster;
import com.amg.exchange.common.model.InsuranceMasterDescription;
import com.amg.exchange.complaint.model.ComplaintAction;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.remittance.model.Imps;
import com.amg.exchange.util.Constants;
 
@SuppressWarnings("rawtypes")
@Repository
public class SourceOfIncomeDaoImpl  extends CommonDaoImpl implements ISourceOfIncomeDao {

	 

	@Override
	public List<SourceOfIncome> getAllRecords() {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SourceOfIncome.class,"sourceOfIncome" );
		 
			 
		dCriteria.setFetchMode("sourceOfIncome.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("sourceOfIncome.fsCountryMaster", "fsCountryMaster",JoinType.INNER_JOIN);
		dCriteria.setFetchMode("sourceOfIncome.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("sourceOfIncome.fsCompanyMaster", "fsCompanyMaster",JoinType.INNER_JOIN);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<SourceOfIncome>)findAll(dCriteria);
 
	}

	@Override
	public void deleteRecordPermanently(BigDecimal sourceOfIncomePk,
			BigDecimal srcIncomeDescEnglishPk, BigDecimal srcIncomeArabicPk) {
		SourceOfIncome   sourceOfIncome=(SourceOfIncome) getSession().get(SourceOfIncome.class,  sourceOfIncomePk);
		SourceOfIncomeDescription   sourceOfIncomeEngDesc=(SourceOfIncomeDescription) getSession().get(SourceOfIncomeDescription.class,  srcIncomeDescEnglishPk);
		SourceOfIncomeDescription  sourceOfIncomeArabicDesc=(SourceOfIncomeDescription) getSession().get(SourceOfIncomeDescription.class, srcIncomeArabicPk);
		getSession().delete(sourceOfIncomeEngDesc);	
		getSession().delete(sourceOfIncomeArabicDesc);		
		getSession().delete(sourceOfIncome);		
		
	}

	@Override
	public void activateRecord(BigDecimal sourceOfIncomePk, String userName) {
		SourceOfIncome srcIncomeObj=(SourceOfIncome)getSession().get(SourceOfIncome.class, sourceOfIncomePk);
		srcIncomeObj.setIsActive(Constants.U);
		srcIncomeObj.setModifiedBy(userName);
		srcIncomeObj.setModifiedDate(new Date());
		srcIncomeObj.setApprovedBy(null);
		srcIncomeObj.setApprovedDate(null);
		srcIncomeObj.setRemarks(null);
		getSession().update(srcIncomeObj);
		
	}

	@Override
	public void saveOrUpdate(SourceOfIncome sourceOfIncome,
			SourceOfIncomeDescription sourceOfIncomeEngDesc,
			SourceOfIncomeDescription sourceOfIncomeArabicDesc) {
	try{
		getSession().saveOrUpdate(sourceOfIncome);
		getSession().saveOrUpdate(sourceOfIncomeEngDesc);
		getSession().saveOrUpdate(sourceOfIncomeArabicDesc);
	}catch(Exception e){
 
	}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SourceOfIncomeDescription> getSourceOfIncomeDesc(
			BigDecimal sourceOfIncId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SourceOfIncomeDescription.class,"sourceOfIncomeDesc" );
		dCriteria.setFetchMode("sourceOfIncomeDesc.languageId", FetchMode.JOIN);
		dCriteria.createAlias("sourceOfIncomeDesc.languageId", "languageId",JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("sourceOfIncomeDesc.sourceOfIncomeId", FetchMode.JOIN);
		dCriteria.createAlias("sourceOfIncomeDesc.sourceOfIncomeId", "sourceOfIncomeId",JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("sourceOfIncomeId.sourceId", sourceOfIncId));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<SourceOfIncomeDescription>)findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SourceOfIncome> getSourceOfIncomeRecordsBanedOncode(
			String sourceCode) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SourceOfIncome.class,"sourceOfIncome" );
		 
		dCriteria.setFetchMode("sourceOfIncome.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("sourceOfIncome.fsCountryMaster", "fsCountryMaster",JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("sourceOfIncome.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("sourceOfIncome.fsCompanyMaster", "fsCompanyMaster",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq( "sourceCode", sourceCode));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<SourceOfIncome>)findAll(dCriteria);
	}

	@Override
	public List<String> getAutoCompleteSourceOfIncomeCode(String query) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SourceOfIncome.class, "sourceOfIncome");
		criteria.add(Restrictions.like("sourceOfIncome.sourceCode", query, MatchMode.ANYWHERE).ignoreCase());
		criteria.setProjection(Projections.property("sourceOfIncome.sourceCode"));
		criteria.addOrder(Order.asc("sourceOfIncome.sourceCode"));
 		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<String>)findAll(criteria);
 
		 
	}

	@Override
	public List<SourceOfIncomeDescription> getAllUnApprovedRecords() {
		DetachedCriteria criteria = DetachedCriteria.forClass(SourceOfIncomeDescription.class, "sourceOfIncomeDescription");
		criteria.setFetchMode("sourceOfIncomeDescription.sourceOfIncomeId", FetchMode.JOIN);
		criteria.createAlias("sourceOfIncomeDescription.sourceOfIncomeId", "sourceOfIncomeId",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("sourceOfIncomeId.isActive",Constants.U));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<SourceOfIncomeDescription>)findAll(criteria);
		 
	}

	@Override
	public String approveRecord(BigDecimal sourceOfOIncomePk, String userName) {
		String approveMsg;
		SourceOfIncome sourceIncomeMaster=(SourceOfIncome) getSession().get(SourceOfIncome.class, sourceOfOIncomePk);
		String approvedUser= sourceIncomeMaster.getApprovedBy();
		if(approvedUser==null)
		{
			sourceIncomeMaster.setApprovedBy(userName );
			sourceIncomeMaster.setApprovedDate(new Date());
			sourceIncomeMaster.setIsActive(Constants.Yes);
			getSession().saveOrUpdate(sourceIncomeMaster);
			approveMsg="Success";
		}
		else{
			approveMsg="Fail";
		}
		return  approveMsg;
	 
	}

	@Override
	public String getSourceOfIncome(BigDecimal sourceId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SourceOfIncomeDescription.class, "sourceOfIncomeDescription");
		criteria.setFetchMode("sourceOfIncomeDescription.sourceOfIncomeId", FetchMode.JOIN);
		criteria.createAlias("sourceOfIncomeDescription.sourceOfIncomeId", "sourceOfIncomeId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("sourceOfIncomeId.sourceId",sourceId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<SourceOfIncomeDescription> sourceList =(List<SourceOfIncomeDescription>)findAll(criteria); 
		if(sourceList!=null){
		return sourceList.get(0).getSourceOfIncomeFullDesc();
		}else{
			return null;
		}
	}

	@Override
	public String getPurposeOfTransaction(BigDecimal purposeId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PurposeOfTransaction.class, "purposeOfTransaction");
		
		criteria.add(Restrictions.eq("purposeOfTransaction.purposeId",purposeId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<PurposeOfTransaction> purposList =(List<PurposeOfTransaction>)findAll(criteria); 
		if(purposList!=null){
		return purposList.get(0).getPurposeFullDesc();
		}else{
			return null;
		}
	}

	@Override
	public BigDecimal getSourceOfIncomeIdBasedOnName(String sourceDesc,BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SourceOfIncomeDescription.class, "sourceOfIncomeDescription");
		
		criteria.setFetchMode("sourceOfIncomeDescription.languageType", FetchMode.JOIN);
		criteria.createAlias("sourceOfIncomeDescription.languageId", "languageId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));

		criteria.setFetchMode("sourceOfIncomeDescription.sourceOfIncomeId", FetchMode.JOIN);
		criteria.createAlias("sourceOfIncomeDescription.sourceOfIncomeId", "sourceOfIncomeId",JoinType.INNER_JOIN);
	
		criteria.add(Restrictions.eq("sourceOfIncomeDescription.sourceOfIncomeFullDesc",sourceDesc));
		 
		List<SourceOfIncomeDescription> sourceList =(List<SourceOfIncomeDescription>)findAll(criteria); 
		if(sourceList.size()>0){
		return  sourceList.get(0).getSourceOfIncomeId().getSourceId();
		}else{
			return null;
		}
	}

	@Override
	public List<SourceOfIncomeDescription> getSourceOfIncomeRecordsBanedOnCode(
			String sourceCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SourceOfIncomeDescription.class, "sourceOfIncomeDescription");
		criteria.setFetchMode("sourceOfIncomeDescription.sourceOfIncomeId", FetchMode.JOIN);
		criteria.createAlias("sourceOfIncomeDescription.sourceOfIncomeId", "sourceOfIncomeId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("sourceOfIncomeId.sourceCode",sourceCode));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<SourceOfIncomeDescription> sourceList =(List<SourceOfIncomeDescription>)findAll(criteria); 
	if(sourceList.size()>0){
		return sourceList;
	}else{
		return null;
	}
	} 
}

	 
