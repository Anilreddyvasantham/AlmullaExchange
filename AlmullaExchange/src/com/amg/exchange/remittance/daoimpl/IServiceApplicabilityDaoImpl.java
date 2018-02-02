package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.remittance.dao.IServiceApplicabilityRuleDao;
import com.amg.exchange.remittance.model.ServiceApplicabilityRule;
import com.amg.exchange.remittance.model.ViewBankRuleAppField;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class IServiceApplicabilityDaoImpl extends CommonDaoImpl implements IServiceApplicabilityRuleDao {
	
	Logger LOGGER = Logger.getLogger(IServiceApplicabilityDaoImpl.class);

	public void saveApplicability(ServiceApplicabilityRule serviceApplicabilityRule) {
		getSession().saveOrUpdate(serviceApplicabilityRule);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceApplicabilityRule> searchrecord(BigDecimal appCountryId,BigDecimal country,BigDecimal currency,BigDecimal remittance,BigDecimal delivery){

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ServiceApplicabilityRule.class,"serviceApplicabilityRule");


		dCriteria.setFetchMode("serviceApplicabilityRule.applicationCountryId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("applicationCountryId.countryId", appCountryId));

		dCriteria.setFetchMode("serviceApplicabilityRule.countryId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.countryId", "countryId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("countryId.countryId", country));

		dCriteria.setFetchMode("serviceApplicabilityRule.currencyId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.currencyId", "currencyId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("currencyId.currencyId", currency));

		dCriteria.setFetchMode("serviceApplicabilityRule.remittanceModeId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.remittanceModeId", "remittanceModeId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("remittanceModeId.remittanceModeId", remittance));

		dCriteria.setFetchMode("serviceApplicabilityRule.deliveryModeId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.deliveryModeId", "deliveryModeId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("deliveryModeId.deliveryModeId", delivery));
		dCriteria.add(Restrictions.eq("isActive", Constants.U));
		dCriteria.add(Restrictions.isNull("serviceApplicabilityRule.approvedBy"));
		dCriteria.add(Restrictions.isNull("serviceApplicabilityRule.approvedDate"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);


		return (List<ServiceApplicabilityRule>)findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewBankRuleAppField> getViewData() {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewBankRuleAppField.class,"viewServAppParam");
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ViewBankRuleAppField>)findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceApplicabilityRule> getViewDataServiceAppRule(BigDecimal appCountryId,BigDecimal countryId, BigDecimal currencyId, BigDecimal remitanceId, BigDecimal deliveryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ServiceApplicabilityRule.class,"serviceApplicabilityRule");


		dCriteria.setFetchMode("serviceApplicabilityRule.applicationCountryId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("applicationCountryId.countryId", appCountryId));

		dCriteria.setFetchMode("serviceApplicabilityRule.countryId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.countryId", "countryId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("countryId.countryId", countryId));

		dCriteria.setFetchMode("serviceApplicabilityRule.currencyId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.currencyId", "currencyId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("currencyId.currencyId", currencyId));

		dCriteria.setFetchMode("serviceApplicabilityRule.remittanceModeId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.remittanceModeId", "remittanceModeId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("remittanceModeId.remittanceModeId", remitanceId));

		dCriteria.setFetchMode("serviceApplicabilityRule.deliveryModeId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.deliveryModeId", "deliveryModeId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("deliveryModeId.deliveryModeId", deliveryId));
		//dCriteria.add(Restrictions.eq("isActive", "U"));
		/*	dCriteria.add(Restrictions.isNull("serviceApplicabilityRule.approvedBy"));
			dCriteria.add(Restrictions.isNull("serviceApplicabilityRule.approvedDate"));*/
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<ServiceApplicabilityRule>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceApplicabilityRule> getRecordsForApproval(BigDecimal applicationCountryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceApplicabilityRule.class,"serviceApplicabilityRule");

		criteria.setFetchMode("serviceApplicabilityRule.applicationCountryId",FetchMode.JOIN);
		criteria.createAlias("serviceApplicabilityRule.applicationCountryId", "applicationCountryId", JoinType.LEFT_OUTER_JOIN);

		criteria.add(Restrictions.eq("applicationCountryId.countryId", applicationCountryId));
		criteria.add(Restrictions.eq("isActive", Constants.U));
		criteria.add(Restrictions.isNull("serviceApplicabilityRule.approvedBy"));
		criteria.add(Restrictions.isNull("serviceApplicabilityRule.approvedDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<ServiceApplicabilityRule>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceApplicabilityRule> getRecordFromApproval(
			BigDecimal serviceAppRuleId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceApplicabilityRule.class,"serviceAppRule");
		criteria.add(Restrictions.eq("serviceApplicabilityRuleId", serviceAppRuleId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<ServiceApplicabilityRule>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceApplicabilityRule> getCopyRecord(BigDecimal appCountryId,BigDecimal remitanceId,BigDecimal deliveryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ServiceApplicabilityRule.class,"serviceApplicabilityRule");


		dCriteria.setFetchMode("serviceApplicabilityRule.remittanceModeId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.remittanceModeId", "remittanceModeId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("remittanceModeId.remittanceModeId", remitanceId));

		dCriteria.setFetchMode("serviceApplicabilityRule.deliveryModeId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.deliveryModeId", "deliveryModeId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("deliveryModeId.deliveryModeId", deliveryId));

		dCriteria.setFetchMode("serviceApplicabilityRule.applicationCountryId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("applicationCountryId.countryId", appCountryId));

		dCriteria.add(Restrictions.eq("isActive", Constants.Yes));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);


		return (List<ServiceApplicabilityRule>)findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceApplicabilityRule> getRecord(BigDecimal appCountryId,
			BigDecimal country, BigDecimal currency, BigDecimal remittance,
			BigDecimal delivery) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ServiceApplicabilityRule.class,"serviceApplicabilityRule");


		dCriteria.setFetchMode("serviceApplicabilityRule.applicationCountryId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("applicationCountryId.countryId", appCountryId));

		dCriteria.setFetchMode("serviceApplicabilityRule.countryId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.countryId", "countryId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("countryId.countryId", country));

		dCriteria.setFetchMode("serviceApplicabilityRule.currencyId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.currencyId", "currencyId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("currencyId.currencyId", currency));

		dCriteria.setFetchMode("serviceApplicabilityRule.remittanceModeId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.remittanceModeId", "remittanceModeId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("remittanceModeId.remittanceModeId", remittance));

		dCriteria.setFetchMode("serviceApplicabilityRule.deliveryModeId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.deliveryModeId", "deliveryModeId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("deliveryModeId.deliveryModeId", delivery));
		//dCriteria.add(Restrictions.eq("isActive", Constants.Yes));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);


		return (List<ServiceApplicabilityRule>)findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceApplicabilityRule> getEnqDataServiceAppRule(BigDecimal appCountryId, BigDecimal countryId, BigDecimal currencyId, BigDecimal remitanceId, BigDecimal deliveryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ServiceApplicabilityRule.class,"serviceApplicabilityRule");


		dCriteria.setFetchMode("serviceApplicabilityRule.applicationCountryId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("applicationCountryId.countryId", appCountryId));
		dCriteria.setFetchMode("serviceApplicabilityRule.countryId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.countryId", "countryId", JoinType.INNER_JOIN);

		if(countryId !=null){

			dCriteria.add(Restrictions.eq("countryId.countryId", countryId));

		}



		dCriteria.setFetchMode("serviceApplicabilityRule.currencyId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.currencyId", "currencyId", JoinType.INNER_JOIN);
		if(currencyId != null){
			dCriteria.add(Restrictions.eq("currencyId.currencyId", currencyId));
		}

		dCriteria.setFetchMode("serviceApplicabilityRule.remittanceModeId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.remittanceModeId", "remittanceModeId", JoinType.INNER_JOIN);
		if(remitanceId != null){
			dCriteria.add(Restrictions.eq("remittanceModeId.remittanceModeId", remitanceId));
		}



		dCriteria.setFetchMode("serviceApplicabilityRule.deliveryModeId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.deliveryModeId", "deliveryModeId", JoinType.INNER_JOIN);
		if(deliveryId != null){
			dCriteria.add(Restrictions.eq("deliveryModeId.deliveryModeId", deliveryId));
		}


		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ServiceApplicabilityRule>) findAll(dCriteria);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceApplicabilityRule> getFetchReCordFormDB(BigDecimal appCountryId, BigDecimal countryId,BigDecimal currencyId, BigDecimal remittanceModeId,BigDecimal deliveryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ServiceApplicabilityRule.class,"serviceApplicabilityRule");
		dCriteria.setFetchMode("serviceApplicabilityRule.applicationCountryId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("applicationCountryId.countryId", appCountryId));

		dCriteria.setFetchMode("serviceApplicabilityRule.countryId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.countryId", "countryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("countryId.countryId", countryId));

		dCriteria.setFetchMode("serviceApplicabilityRule.currencyId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.currencyId", "currencyId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("currencyId.currencyId", currencyId));

		dCriteria.setFetchMode("serviceApplicabilityRule.RemittanceModeMaster",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.remittanceModeId", "remittanceModeId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("remittanceModeId.remittanceModeId", remittanceModeId));

		dCriteria.setFetchMode("serviceApplicabilityRule.deliveryModeId",FetchMode.JOIN);
		dCriteria.createAlias("serviceApplicabilityRule.deliveryModeId", "deliveryModeId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("deliveryModeId.deliveryModeId", deliveryId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ServiceApplicabilityRule>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceApplicabilityRule> getAllRecordsToFetchFromDb(BigDecimal appCountryId, BigDecimal countryId,BigDecimal currencyId, BigDecimal remittanceModeId,BigDecimal deliveryId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ServiceApplicabilityRule.class,"serviceApplicabilityRule");
		if(appCountryId != null){
			dCriteria.setFetchMode("serviceApplicabilityRule.applicationCountryId",FetchMode.JOIN);
			dCriteria.createAlias("serviceApplicabilityRule.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("applicationCountryId.countryId", appCountryId));
		}

		if(countryId != null){
			dCriteria.setFetchMode("serviceApplicabilityRule.countryId",FetchMode.JOIN);
			dCriteria.createAlias("serviceApplicabilityRule.countryId", "countryId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("countryId.countryId", countryId));
		}

		if(currencyId != null){
			dCriteria.setFetchMode("serviceApplicabilityRule.currencyId",FetchMode.JOIN);
			dCriteria.createAlias("serviceApplicabilityRule.currencyId", "currencyId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("currencyId.currencyId", currencyId));
		}

		if(remittanceModeId != null){
			dCriteria.setFetchMode("serviceApplicabilityRule.RemittanceModeMaster",FetchMode.JOIN);
			dCriteria.createAlias("serviceApplicabilityRule.remittanceModeId", "remittanceModeId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("remittanceModeId.remittanceModeId", remittanceModeId));
		}

		if(deliveryId != null){
			dCriteria.setFetchMode("serviceApplicabilityRule.deliveryModeId",FetchMode.JOIN);
			dCriteria.createAlias("serviceApplicabilityRule.deliveryModeId", "deliveryModeId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("deliveryModeId.deliveryModeId", deliveryId));
		}

		dCriteria.add(Restrictions.eq("serviceApplicabilityRule.isActive", Constants.U));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<ServiceApplicabilityRule>) findAll(dCriteria);
	}

	@Override
	public void approvedAllRecords(ServiceApplicabilityRule serviceApplicabilityRule) {
		getSession().saveOrUpdate(serviceApplicabilityRule);
	}

	@Override
	public void updateApprovalRecords(List<BigDecimal> lstApproved,String userName) {
		for (BigDecimal bigDecimal : lstApproved) {
			ServiceApplicabilityRule serviceApplicabilityRule=(ServiceApplicabilityRule) getSession().get(ServiceApplicabilityRule.class, bigDecimal);
			serviceApplicabilityRule.setApprovedBy(userName);
			serviceApplicabilityRule.setApprovedDate(new Date());
			serviceApplicabilityRule.setIsActive(Constants.Yes);
			getSession().update(serviceApplicabilityRule);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceApplicabilityRule> getAllStoringTocheckDate(String navigable,String fielType, String validation, String mandatory) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ServiceApplicabilityRule.class,"serviceApplicabilityRule");


		dCriteria.add(Restrictions.eq("serviceApplicabilityRule.navicable", navigable));
		dCriteria.add(Restrictions.eq("serviceApplicabilityRule.fieldType", fielType));
		dCriteria.add(Restrictions.eq("serviceApplicabilityRule.fieldDesc", validation));
		dCriteria.add(Restrictions.eq("serviceApplicabilityRule.mandatory", mandatory));

		return (List<ServiceApplicabilityRule>) findAll(dCriteria);
	}

	@Override
	public List<ServiceApplicabilityRule> getAllStoringTocheckDate() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ServiceApplicabilityRule.class,"serviceApplicabilityRule");

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ServiceApplicabilityRule>) findAll(dCriteria);
	}



	@Override
	public String updateApproveRecords(List<BigDecimal> lstApproved,String userName) {
		String list = null;
		int i=0;
		for (BigDecimal bigDecimal : lstApproved) {
			ServiceApplicabilityRule serviceApplicabilityRule=(ServiceApplicabilityRule) getSession().get(ServiceApplicabilityRule.class, bigDecimal);
			String approvedUser=serviceApplicabilityRule.getApprovedBy();
			if(approvedUser==null)
			{
				serviceApplicabilityRule.setApprovedBy(userName);
				serviceApplicabilityRule.setApprovedDate(new Date());
				serviceApplicabilityRule.setIsActive(Constants.Yes);
				getSession().update(serviceApplicabilityRule);
				i++;
			}
		}
		if(i != 0){
			list="Success";
		}else{
			list="Fail";
		}
		return list;
	}

	@Override
	public String checkServiceApproveMultiUser(BigDecimal serviceApplicabilityRuleId, String userName) {
		String approvalMsg;
		ServiceApplicabilityRule serviceApplicabilityRule = (ServiceApplicabilityRule) getSession().get(ServiceApplicabilityRule.class, serviceApplicabilityRuleId);
		String approvalUser = serviceApplicabilityRule.getApprovedBy();
		if (approvalUser == null) {
			serviceApplicabilityRule.setIsActive(Constants.Yes);
			serviceApplicabilityRule.setApprovedBy(userName);
			serviceApplicabilityRule.setApprovedDate(new Date());
			getSession().update(serviceApplicabilityRule);
			approvalMsg = "Success";
		} else {
			approvalMsg = "Fail";
		}
		return approvalMsg;
	}

	@Override
	public List<Object> isCombinationExist(BigDecimal deliveryId, BigDecimal remittanceModeId, BigDecimal countryId) {
		List<Object> objList = new ArrayList<Object>();
		String query = "SELECT DISTINCT COUNTRY_ID,CURRENCY_ID,REMITTANCE_MODE_ID,DELIVERY_MODE_ID FROM " + "EX_SERVICE_APPLICABILITY_RULE WHERE REMITTANCE_MODE_ID =" + remittanceModeId + " AND DELIVERY_MODE_ID =" + deliveryId + " AND APPLICATION_COUNTRY_ID =" + countryId + "";
		System.out.println(query);
		return objList = getSession().createSQLQuery(query).list();
	}

	@Override
	public List<Object> fetchrecordsforDataTable(BigDecimal countryId, BigDecimal applicationCountryId, BigDecimal currencyId, BigDecimal delivaryId, BigDecimal remittanceModeId) {
		List<Object> objList = new ArrayList<Object>();
		String query = "SELECT FIELD_NAME,FIELD_DESC,NAVICABLE,MANDATORY,FIELD_TYPE,VALIDATIONS,MAX_LENGTH,MIN_LENGTH,LANGUAGE_ID FROM EX_SERVICE_APPLICABILITY_RULE WHERE REMITTANCE_MODE_ID = " + remittanceModeId + " AND DELIVERY_MODE_ID = " + delivaryId + " AND APPLICATION_COUNTRY_ID = "
				+ applicationCountryId + " AND COUNTRY_ID = " + countryId + " AND CURRENCY_ID = " + currencyId;
		System.out.println(query);
		return objList = getSession().createSQLQuery(query).list(); 
	}

	@Override
	public String toFetchLanguageName(BigDecimal languageId) {
		String lanuguageName = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(LanguageType.class, "languageType");
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<LanguageType> lstLanguageTypes = (List<LanguageType>) findAll(criteria);
		if (lstLanguageTypes != null && lstLanguageTypes.size() != 0) {
			lanuguageName = lstLanguageTypes.get(0).getLanguageName();
		}
		return lanuguageName;
	}

	@Override
	public void saveServiceApplicabilityRuleList(
			List<ServiceApplicabilityRule> serviceApplicabilityRule) throws AMGException {
		
		try{
			if(serviceApplicabilityRule != null && serviceApplicabilityRule.size() != 0){
				for (ServiceApplicabilityRule serviceApplicabilityRule2 : serviceApplicabilityRule) {
					getSession().saveOrUpdate(serviceApplicabilityRule2);
				}
			}
		}catch (Exception e) {
			LOGGER.error("Error Occured in  saving ServiceApplicabilityRule");
			throw new AMGException(e.getMessage());
		}

	}

}

