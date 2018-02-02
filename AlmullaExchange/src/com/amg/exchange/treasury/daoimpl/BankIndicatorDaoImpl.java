package com.amg.exchange.treasury.daoimpl;

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

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.model.RoutingAgent;
import com.amg.exchange.remittance.model.RoutingAgentView;
import com.amg.exchange.treasury.dao.BankIndicatorDao;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankIndicatorDescription;
import com.amg.exchange.util.Constants;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Repository
public class BankIndicatorDaoImpl extends CommonDaoImpl implements BankIndicatorDao {

	@Override
	public void saveOrUpdate(BankIndicator bankIndicatorObj) {
		getSession().saveOrUpdate(bankIndicatorObj);
	}


	@Override
	public List<BankIndicatorDescription> getAllRecordsFromDB() {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankIndicatorDescription.class,"bankIndicatorDesc");
		 
		criteria.setFetchMode("bankIndicatorDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("bankIndicatorDesc.languageType","languageType", JoinType.INNER_JOIN);
		criteria.setFetchMode("bankIndicatorDesc.bankIndicator", FetchMode.JOIN);
		criteria.createAlias("bankIndicatorDesc.bankIndicator", "bankIndicator",JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankIndicatorDescription>) findAll(criteria);
	}


	 
	@Override
	public List<BankIndicator> getAllUnApprovedRecordsFrmDB() {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankIndicator.class,"bankIndicator");
		criteria.add(Restrictions.eq("bankIndicator.isActive", Constants.U));
		criteria.add(Restrictions.isNull("bankIndicator.approvedBy"));
		criteria.add(Restrictions.isNull("bankIndicator.approvedDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankIndicator>) findAll(criteria);
	}


	 
	@Override
	public List<String> getBankIndicatorCodeFromDB(String query) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankIndicator.class,"bankIndicator");
		
		criteria.add(Restrictions.like("bankIndicator.bankIndicatorCode", query, MatchMode.ANYWHERE).ignoreCase()); 
		criteria.setProjection(Projections.property("bankIndicator.bankIndicatorCode"));
		criteria.addOrder(Order.asc("bankIndicator.bankIndicatorCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<String>) findAll(criteria);
	}


	@Override
	public String getBankIndicatorInEnglish(BigDecimal bankIndicatorId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankIndicatorDescription.class, "bankIndicatorDesc");

		criteria.setFetchMode("bankIndicatorDesc.bankIndicator", FetchMode.SELECT);
		criteria.createAlias("bankIndicatorDesc.bankIndicator", "bankIndicator",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankIndicator.bankIndicatorId", bankIndicatorId));

		criteria.setFetchMode("bankIndicatorDesc.languageType",FetchMode.SELECT);
		criteria.createAlias("bankIndicatorDesc.languageType","languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", new BigDecimal(Constants.ENGLISH_LANGUAGE_ID)));
		

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	 
		return ((BankIndicatorDescription) criteria.getExecutableCriteria(getSession()).list().get(0)).getBankIndicatorDescription();
	 
	}


	@Override
	public String getBankIndicatorInLocal(BigDecimal bankIndicatorId) {
		DetachedCriteria criteria = DetachedCriteria.forClass( BankIndicatorDescription.class, "bankIndicatorDesc");

		criteria.setFetchMode("bankIndicatorDesc.bankIndicator",FetchMode.SELECT);
		criteria.createAlias("bankIndicatorDesc.bankIndicator", "bankIndicator",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankIndicator.bankIndicatorId", bankIndicatorId));

		criteria.setFetchMode("bankIndicatorDesc.languageType",FetchMode.SELECT);
		criteria.createAlias("bankIndicatorDesc.languageType","languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", new BigDecimal(Constants.ARABIC_LANGUAGE_ID)));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
 
		return ((BankIndicatorDescription) criteria.getExecutableCriteria(getSession()).list().get(0)).getBankIndicatorDescription();
 
	}


	@Override
	public void saveOrUpdate(BankIndicatorDescription bankIndicatorDesc) {
		getSession().saveOrUpdate(bankIndicatorDesc);
	}


	 
	@Override
	public List<BankIndicatorDescription> getAllRecordsForDuplicateDescCheck(String bankInddesc) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankIndicatorDescription.class, "bankIndicatorDescription");

		criteria.setFetchMode("bankIndicatorDescription.languageType",FetchMode.JOIN);
		criteria.createAlias("bankIndicatorDescription.languageType","languageType", JoinType.INNER_JOIN);
		criteria.setFetchMode("bankIndicatorDescription.bankIndicator", FetchMode.JOIN);
		criteria.createAlias("bankIndicatorDescription.bankIndicator", "bankIndicator",JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("languageType.languageId", new BigDecimal(Constants.ENGLISH_LANGUAGE_ID)));
		criteria.add(Restrictions.eq("bankIndicatorDescription.bankIndicatorDescription", bankInddesc));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<BankIndicatorDescription>) findAll(criteria);
	}


	@Override
	public void delete(BankIndicatorDescription bankIndicatorDesc) {
		getSession().delete(bankIndicatorDesc);
	}


	@Override
	public void delete(BankIndicator bankIndicator) {
		getSession().delete(bankIndicator);
	}


	 
	@Override
	public List<BankIndicator> getRecordFromDB(String bankIndicatorCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankIndicator.class,"bankIndicator");
		criteria.add(Restrictions.eq("bankIndicator.bankIndicatorCode",bankIndicatorCode));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<BankIndicator>) findAll(criteria);
	}


	 
	@Override
	public List<BankIndicatorDescription> getDescriptionRecordFromDB(BigDecimal bankIndicatorId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankIndicatorDescription.class, "bankIndicatorDescription");
		criteria.setFetchMode("bankIndicatorDescription.bankIndicator", FetchMode.JOIN);
		criteria.createAlias("bankIndicatorDescription.bankIndicator", "bankIndicator",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankIndicator.bankIndicatorId",bankIndicatorId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<BankIndicatorDescription>) findAll(criteria);
	}


	@Override
	public List<BankIndicatorDescription> getAllRecordsForApproval() {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankIndicatorDescription.class, "bankIndicatorDescription");
		criteria.setFetchMode("bankIndicatorDescription.bankIndicator", FetchMode.JOIN);
		criteria.createAlias("bankIndicatorDescription.bankIndicator", "bankIndicator",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankIndicator.isActive",Constants.U));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<BankIndicatorDescription>) findAll(criteria);
	}


	@Override
	public String approve(BigDecimal bankIndicatorPk,String userName) {
		String approveMsg;
		BankIndicator bankIndicator=(BankIndicator) getSession().get(BankIndicator.class, bankIndicatorPk);
		String approvedUser=bankIndicator.getApprovedBy();
		if(approvedUser==null)
		{
			bankIndicator.setApprovedBy(userName );
			bankIndicator.setApprovedDate(new Date());
			bankIndicator.setIsActive(Constants.Yes);
			getSession().saveOrUpdate(bankIndicator);
			approveMsg="Success";
		}
		else{
			approveMsg="Fail";
		}
		return  approveMsg;
	}


	@Override
	public void saveOrUpdate(RoutingAgent routingAgent) {
		getSession().saveOrUpdate(routingAgent);
	}


	@Override
	public List<RoutingAgentView> getAgentListFromView(BigDecimal bankIndicatorId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RoutingAgentView.class,"routingagentView");
		criteria.add(Restrictions.eq("routingagentView.bankIndicatorId",bankIndicatorId));
		//criteria.add(Restrictions.eq("routingagentView.isActive","Y"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RoutingAgentView>) findAll(criteria);
	}

}
