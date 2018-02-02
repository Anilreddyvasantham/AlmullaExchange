package com.amg.exchange.remittance.daoimpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.AvailableDatabaseObjects;
import com.amg.exchange.common.model.AvailableTableColumns;
import com.amg.exchange.remittance.bean.ParameterMasterandDefinitionBean;
import com.amg.exchange.remittance.dao.IParameterDao;
import com.amg.exchange.remittance.model.ParameterDefinition;
import com.amg.exchange.remittance.model.ParameterDetails;
import com.amg.exchange.remittance.model.ParameterGrant;
import com.amg.exchange.remittance.model.ParameterMaster;
import com.amg.exchange.util.Constants;

@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
@Repository
public class IParameterDaoImpl extends CommonDaoImpl implements IParameterDao, Serializable {
	Logger LOGGER = Logger.getLogger(ParameterMasterandDefinitionBean.class);

	@Override
	public List<ParameterMaster> fetchAllParameterMasterForView() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterMaster.class, "parameterMaster");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("parameterMaster.recordId"));
		return (List<ParameterMaster>) findAll(criteria);
	}

	@Override
	public List<ParameterMaster> checkRecordIdForParamaterMaster(String recordId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterMaster.class, "parameterMaster");
		criteria.add(Restrictions.eq("parameterMaster.recordId", recordId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ParameterMaster>) findAll(criteria);
	}

	@Override
	public void saveParameterMaster(List<ParameterMaster> paramMaster) throws Exception {
		LOGGER.info("Entering into saveParameterMaster method");
		try {
			for (ParameterMaster parameterMaster : paramMaster) {
				getSession().saveOrUpdate(parameterMaster);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		LOGGER.info("Exit into saveParameterMaster method");
	}

	@Override
	public List<ParameterMaster> getAllComponent(String query, BigDecimal languageId) {
		LOGGER.info("Entering intogetAllComponent method");
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterMaster.class, "parameterMaster");
		criteria.add(Restrictions.like("parameterMaster.recordId", query, MatchMode.ANYWHERE).ignoreCase());
		criteria.addOrder(Order.asc("parameterMaster.recordId"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit intogetAllComponent method");
		return (List<ParameterMaster>) findAll(criteria);
	}

	@Override
	public ParameterMaster viewById(String recordId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterMaster.class, "parameterMaster");
		criteria.add(Restrictions.eq("parameterMaster.recordId", recordId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		// return (List<ParameterMaster>) findAll(criteria);
		List<ParameterMaster> list = (List<ParameterMaster>) findAll(criteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public ParameterDefinition viewDefinitionById(String recordId) {
		LOGGER.info("Record id " + recordId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDefinition.class, "parameterDefinition");
		criteria.add(Restrictions.eq("parameterDefinition.recordId", recordId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ParameterDefinition> list = null;
		list = (List<ParameterDefinition>) findAll(criteria);
		LOGGER.info("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL" + list == null);
		LOGGER.info("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL" + list == null);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ParameterDefinition> getAllRecords() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDefinition.class, "parameterDefinition");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("parameterDefinition.recordId"));
		return (List<ParameterDefinition>) findAll(criteria);
	}

	@Override
	public void save(ParameterMaster saveParameterMaster, ParameterDefinition parameterDefinition) throws Exception {
		try {
			saveParameterMaster(saveParameterMaster);
			parameterDefinition.setParameterMasterId(saveParameterMaster);
			saveParameterDefinition(parameterDefinition);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public void saveParameterMaster(ParameterMaster saveParameterMaster) throws Exception {
		LOGGER.info("Entering into save method");
		getSession().saveOrUpdate(saveParameterMaster);
		LOGGER.info("Exit into save method");
	}

	public void saveParameterDefinition(ParameterDefinition parameterDefinition) {
		LOGGER.info("Entering into save method");
		getSession().saveOrUpdate(parameterDefinition);
		LOGGER.info("Exit into save method");
	}

	@Override
	public List<ParameterMaster> viewDefinitionListforDetails(BigDecimal recordId) {
		LOGGER.info("Record id " + recordId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterMaster.class, "parameterMaster");
		criteria.add(Restrictions.eq("parameterMaster.parameterMasterId", recordId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ParameterMaster>) findAll(criteria);
	}

	@Override
	public ParameterDefinition viewByID(BigDecimal parameterDefinitionId) {
		LOGGER.info("Record id " + parameterDefinitionId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDefinition.class, "parameterDefinition");
		criteria.add(Restrictions.eq("parameterDefinition.parameterDefinitionId", parameterDefinitionId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ParameterDefinition> list = null;
		list = (List<ParameterDefinition>) findAll(criteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ParameterDetails> getAllComponent(String query, BigDecimal parameterDefinitionId, BigDecimal parameterMasterId) {
		LOGGER.info("*********************query  " + query + " parameterDefinitionId " + parameterDefinitionId + "  parameterMasterId " + parameterMasterId);
		DetachedCriteria dcriteria = DetachedCriteria.forClass(ParameterDetails.class, "parameterDetails");
		dcriteria.setFetchMode("parameterDetails.parameterMasterId", FetchMode.JOIN);
		dcriteria.createAlias("parameterDetails.parameterMasterId", "parameterMasterId", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("parameterMasterId.parameterMasterId", parameterMasterId));
		dcriteria.setFetchMode("parameterDetails.parameterDefinitionId", FetchMode.JOIN);
		dcriteria.createAlias("parameterDetails.parameterDefinitionId", "parameterDefinitionId", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("parameterDefinitionId.parameterDefinitionId", parameterDefinitionId));
		dcriteria.add(Restrictions.like("parameterDetails.recordId", query, MatchMode.ANYWHERE).ignoreCase());
		dcriteria.addOrder(Order.asc("parameterDetails.recordId"));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ParameterDetails>) findAll(dcriteria);
	}

	@Override
	public ParameterDefinition fetchFlexiName(BigDecimal parameterMasterId, String flexFieldName) {
		DetachedCriteria dcriteria = DetachedCriteria.forClass(ParameterDefinition.class, "parameterDefinition");
		dcriteria.setFetchMode("parameterDefinition.parameterMasterId", FetchMode.JOIN);
		dcriteria.createAlias("parameterDefinition.parameterMasterId", "parameterMasterId", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("parameterMasterId.parameterMasterId", parameterMasterId));
		dcriteria.add(Restrictions.eq("parameterDefinition.fieldName", flexFieldName));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ParameterDefinition> list = null;
		list = (List<ParameterDefinition>) findAll(dcriteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ParameterDefinition> getFlexiList(BigDecimal parameterMasterId) {
		LOGGER.info("  parameterMasterId " + parameterMasterId);
		DetachedCriteria dcriteria = DetachedCriteria.forClass(ParameterDefinition.class, "parameterDefinition");
		dcriteria.setFetchMode("parameterDefinition.parameterMasterId", FetchMode.JOIN);
		dcriteria.createAlias("parameterDefinition.parameterMasterId", "parameterMasterId", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("parameterMasterId.parameterMasterId", parameterMasterId));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ParameterDefinition>) findAll(dcriteria);
	}

	@Override
	public void saveDetails(ParameterDetails details) {
		getSession().saveOrUpdate(details);
	}

	@Override
	public List<ParameterDetails> getRecords(BigDecimal parameterDefinitionId, BigDecimal parameterMasterId) {
		DetachedCriteria dcriteria = DetachedCriteria.forClass(ParameterDetails.class, "parameterDetails");
		dcriteria.setFetchMode("parameterDetails.parameterMasterId", FetchMode.JOIN);
		dcriteria.createAlias("parameterDetails.parameterMasterId", "parameterMasterId", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("parameterMasterId.parameterMasterId", parameterMasterId));
		dcriteria.setFetchMode("parameterDetails.parameterDefinitionId", FetchMode.JOIN);
		dcriteria.createAlias("parameterDetails.parameterDefinitionId", "parameterDefinitionId", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("parameterDefinitionId.parameterDefinitionId", parameterDefinitionId));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ParameterDetails>) findAll(dcriteria);
	}

	@Override
	public List<Object> getRecords(BigDecimal parameterDefinitionId, BigDecimal parameterMasterId, String flexFieldName) {
		String queryString = "SELECT " + flexFieldName + " from EX_PARAMETER_DETAILS WHERE PARAM_MASTER_DEF_ID = " + parameterDefinitionId + " AND PARAM_MASTER_ID=" + parameterMasterId + " AND " + flexFieldName + " IS NOT NULL";
		System.out.println(queryString);
		return getSession().createSQLQuery(queryString).list();
	}

	@Override
	public List<ParameterDetails> fetchAllRecords() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDetails.class, "parameterDetails");
		criteria.setFetchMode("parameterDetails.parameterMasterId", FetchMode.JOIN);
		criteria.createAlias("parameterDetails.parameterMasterId", "parameterMasterId", JoinType.INNER_JOIN);
		criteria.setFetchMode("parameterDetails.parameterDefinitionId", FetchMode.JOIN);
		criteria.createAlias("parameterDetails.parameterDefinitionId", "parameterDefinitionId", JoinType.INNER_JOIN);
		criteria.addOrder(Order.asc("parameterDetails.recordId"));
		criteria.add(Restrictions.eq("parameterDefinitionId.isActive", "Y"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ParameterDetails>) findAll(criteria);
	}

	@Override
	public String approveRecord(BigDecimal parameterDetailsId, String userName) {
		ParameterDetails details = (ParameterDetails) getSession().get(ParameterDetails.class, parameterDetailsId);
		String approver = details.getApprovedBy();
		if (approver == null) {
			details.setIsActive("Y");
			details.setApprovedBy(userName);
			details.setApprovedDate(new Date());
			getSession().update(details);
			return "success";
		}
		return "fail";
	}

	@Override
	public List<ParameterDetails> fetchAllunApprovedRecords() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDetails.class, "parameterDetails");
		criteria.setFetchMode("parameterDetails.parameterMasterId", FetchMode.JOIN);
		criteria.createAlias("parameterDetails.parameterMasterId", "parameterMasterId", JoinType.INNER_JOIN);
		/*
		 * criteria.setFetchMode("parameterDetails.parameterDefinitionId",
		 * FetchMode.JOIN);
		 */
		/*
		 * criteria.createAlias("parameterDetails.parameterDefinitionId",
		 * "parameterDefinitionId", JoinType.INNER_JOIN);
		 */
		criteria.add(Restrictions.eq("parameterDetails.isActive", "U"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		/* criteria.addOrder(Order.asc("parameterDetails.recordId")); */
		return (List<ParameterDetails>) findAll(criteria);
	}

	@Override
	public List<ParameterDefinition> fetchAllParameterDefinitionforView() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDefinition.class, "parameterDefinition");
		criteria.setFetchMode("parameterDefinition.parameterMasterId", FetchMode.JOIN);
		criteria.createAlias("parameterDefinition.parameterMasterId", "parameterMasterId", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("parameterDefinition.recordId"));
		return (List<ParameterDefinition>) findAll(criteria);
		/* return null; */
	}
	
	@Override
	public List<ParameterDefinition> fetchAllParameterDefinitionforView(String recordId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDefinition.class, "parameterDefinition");
		criteria.setFetchMode("parameterDefinition.parameterMasterId", FetchMode.JOIN);
		criteria.createAlias("parameterDefinition.parameterMasterId", "parameterMasterId", JoinType.INNER_JOIN);
		if(recordId != null && !recordId.equals("") && !recordId.equals("0")){
			criteria.add(Restrictions.eq("parameterDefinition.recordId", recordId));
		}
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("parameterDefinition.recordId"));
		return (List<ParameterDefinition>) findAll(criteria);
		/* return null; */
	}

	@Override
	public BigDecimal getDetails(BigDecimal parameterDefinitionId, BigDecimal parameterMasterId, String flexFieldName, String fieldValue) {
		String queryString = "SELECT PARAMETER_DETAILS_ID from EX_PARAMETER_DETAILS WHERE PARAM_MASTER_DEF_ID = " + parameterDefinitionId + " AND PARAM_MASTER_ID=" + parameterMasterId + " AND " + flexFieldName + " IS NOT NULL AND " + flexFieldName + "='" + fieldValue + "'";
		System.out.println(queryString);
		List<Object> objList = getSession().createSQLQuery(queryString).list();
		if (objList != null) {
			BigDecimal result = (BigDecimal) objList.get(0);
			return result;
		}
		return null;
	}

	@Override
	public ParameterDetails viewByDetailsID(BigDecimal detailsId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDetails.class, "parameterDetails");
		criteria.setFetchMode("parameterDetails.parameterMasterId", FetchMode.JOIN);
		criteria.createAlias("parameterDetails.parameterMasterId", "parameterMasterId", JoinType.INNER_JOIN);
		criteria.setFetchMode("parameterDetails.parameterDefinitionId", FetchMode.JOIN);
		criteria.createAlias("parameterDetails.parameterDefinitionId", "parameterDefinitionId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("parameterDetails.parameterDetailsId", detailsId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ParameterDetails> list = (List<ParameterDetails>) findAll(criteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void delete(ParameterDetails details) {
		getSession().delete(details);
	}

	@Override
	public String updateStaus(BigDecimal parameterDetailsId, String userName, String status, String remarks) {
		ParameterDetails detail = (ParameterDetails) getSession().get(ParameterDetails.class, parameterDetailsId);
		String isActive = detail.getIsActive();
		if (isActive.equals(Constants.Yes)) {
			detail.setModifiedBy(userName);
			detail.setModifiedDate(new Date());
			detail.setIsActive(Constants.D);
			detail.setRemarks(remarks);
			detail.setApprovedBy(null);
			detail.setApprovedDate(null);
			getSession().update(detail);
			return "Success";
		}
		if (isActive.equals(Constants.D)) {
			detail.setIsActive(Constants.U);
			detail.setModifiedBy(userName);
			detail.setModifiedDate(new Date());
			detail.setApprovedBy(null);
			detail.setApprovedDate(null);
			detail.setRemarks(null);
			getSession().update(detail);
			return "Success";
		}
		return "fail";
	}

	@Override
	public List<ParameterDefinition> getnewFlexiList(BigDecimal parameterMasterId, BigDecimal parameterDefinitionId) {
		DetachedCriteria dcriteria = DetachedCriteria.forClass(ParameterDefinition.class, "parameterDefinition");
		dcriteria.setFetchMode("parameterDefinition.parameterMasterId", FetchMode.JOIN);
		dcriteria.createAlias("parameterDefinition.parameterMasterId", "parameterMasterId", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("parameterMasterId.parameterMasterId", parameterMasterId));
		dcriteria.add(Restrictions.eq("parameterDefinition.parameterDefinitionId", parameterDefinitionId));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ParameterDefinition>) findAll(dcriteria);
	}

	@Override
	public List<ParameterDetails> getRecordsbasedOnMasterId(BigDecimal parameterMasterId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDetails.class, "parameterDetails");
		criteria.setFetchMode("parameterDetails.parameterMasterId", FetchMode.JOIN);
		criteria.createAlias("parameterDetails.parameterMasterId", "parameterMasterId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("parameterMasterId.parameterMasterId", parameterMasterId));
		/*
		 * criteria.setFetchMode("parameterDetails.parameterDefinitionId",
		 * FetchMode.JOIN);
		 * criteria.createAlias("parameterDetails.parameterDefinitionId",
		 * "parameterDefinitionId", JoinType.INNER_JOIN);
		 */
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ParameterDetails>) findAll(criteria);
	}

	@Override
	public List<ParameterDetails> getAllDetailsComponent(String query, BigDecimal parameterMasterId) {
		LOGGER.info("*********************query  " + query + "  parameterMasterId " + parameterMasterId);
		DetachedCriteria dcriteria = DetachedCriteria.forClass(ParameterDetails.class, "parameterDetails");
		dcriteria.setFetchMode("parameterDetails.parameterMasterId", FetchMode.JOIN);
		dcriteria.createAlias("parameterDetails.parameterMasterId", "parameterMasterId", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("parameterMasterId.parameterMasterId", parameterMasterId));
		/*
		 * dcriteria.setFetchMode("parameterDetails.parameterDefinitionId",
		 * FetchMode.JOIN);
		 * dcriteria.createAlias("parameterDetails.parameterDefinitionId",
		 * "parameterDefinitionId", JoinType.INNER_JOIN);
		 */
		dcriteria.add(Restrictions.like("parameterDetails.paramCodeDef", query, MatchMode.ANYWHERE).ignoreCase());
		dcriteria.addOrder(Order.asc("parameterDetails.recordId"));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ParameterDetails>) findAll(dcriteria);
	}

	@Override
	public ParameterDetails populateDetails(BigDecimal parameterMasterId, String paramcodedefintion) {
		DetachedCriteria dcriteria = DetachedCriteria.forClass(ParameterDetails.class, "parameterDetails");
		dcriteria.setFetchMode("parameterDetails.parameterMasterId", FetchMode.JOIN);
		dcriteria.createAlias("parameterDetails.parameterMasterId", "parameterMasterId", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("parameterMasterId.parameterMasterId", parameterMasterId));
		dcriteria.add(Restrictions.eq("parameterDetails.paramCodeDef", paramcodedefintion));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ParameterDetails> list = (List<ParameterDetails>) findAll(dcriteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public ParameterMaster viewParameterMasterById(BigDecimal parameterMasterId) {
		LOGGER.info("parameterMasterId " + parameterMasterId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterMaster.class, "parameterMaster");
		criteria.add(Restrictions.eq("parameterMaster.parameterMasterId", parameterMasterId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ParameterMaster> list = null;
		list = (List<ParameterMaster>) findAll(criteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<String> getParameterCodeList(BigDecimal parameterMasterId) {
		LOGGER.info("parameterMasterId " + parameterMasterId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDefinition.class, "parameterDefinition");
		criteria.setFetchMode("parameterDefinition.parameterMasterId", FetchMode.JOIN);
		criteria.createAlias("parameterDefinition.parameterMasterId", "paramaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("paramaster.parameterMasterId", parameterMasterId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ParameterDefinition> list = null;
		list = (List<ParameterDefinition>) findAll(criteria);
		List<String> finalList = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				finalList.add(list.get(i).getParameterId());
			}
			return finalList;
		}
		return null;
	}

	@Override
	public String getFlexiFiledName(BigDecimal parameterMasterId, String parameterCode) {
		LOGGER.info("parameterMasterId " + parameterMasterId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDefinition.class, "parameterDefinition");
		criteria.setFetchMode("parameterDefinition.parameterMasterId", FetchMode.JOIN);
		criteria.createAlias("parameterDefinition.parameterMasterId", "paramaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("paramaster.parameterMasterId", parameterMasterId));
		criteria.add(Restrictions.eq("parameterDefinition.parameterId", parameterCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ParameterDefinition> list = null;
		list = (List<ParameterDefinition>) findAll(criteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0).getFieldName();
		}
		return null;
	}

	@Override
	public List<String> getFlexiFieldfromDB(BigDecimal parameterMasterId) {
		LOGGER.info("parameterMasterId " + parameterMasterId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDefinition.class, "parameterDefinition");
		criteria.setFetchMode("parameterDefinition.parameterMasterId", FetchMode.JOIN);
		criteria.createAlias("parameterDefinition.parameterMasterId", "paramaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("paramaster.parameterMasterId", parameterMasterId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ParameterDefinition> list = null;
		list = (List<ParameterDefinition>) findAll(criteria);
		List<String> strList = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				strList.add(list.get(i).getFieldName());
			}
			return strList;
		}
		return null;
	}

	@Override
	public List<AvailableDatabaseObjects> getDataBaseObjects() {
		DetachedCriteria criteria = DetachedCriteria.forClass(AvailableDatabaseObjects.class, "availableDatabaseObjects");
		criteria.addOrder(Order.asc("availableDatabaseObjects.objectName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AvailableDatabaseObjects>) findAll(criteria);
	}

	@Override
	public List<AvailableTableColumns> getTablecolumns(String tableName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AvailableTableColumns.class, "availableTableColumns");
		criteria.add(Restrictions.eq("availableTableColumns.tableName", tableName));
		criteria.addOrder(Order.asc("availableTableColumns.columnName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AvailableTableColumns>) findAll(criteria);
	}

	@Override
	public ParameterDefinition isValueIndicatorEnabled(String fieldName, BigDecimal parameterMasterId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDefinition.class, "parameterDefinition");
		criteria.setFetchMode("parameterDefinition.parameterMasterId", FetchMode.JOIN);
		criteria.createAlias("parameterDefinition.parameterMasterId", "paramaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("paramaster.parameterMasterId", parameterMasterId));
		criteria.add(Restrictions.eq("parameterDefinition.fieldName", fieldName));
		criteria.add(Restrictions.eq("parameterDefinition.valueIndicator", "D"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ParameterDefinition> list = null;
		list = (List<ParameterDefinition>) findAll(criteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Boolean checkDatabaseValue(Object checkValue, String tableName, String tableField, String flag) {
		String queryString = "";
		if (flag.equals("C")) {
			String charValue = checkValue.toString();
			queryString = "select * from " + tableName + " where upper(" + tableField + ") = upper('" + charValue + "')";
		} else if (flag.equals("N")) {
			String charValue = checkValue.toString();
			BigDecimal numberValue = new BigDecimal(charValue);
			queryString = "select * from " + tableName + " where " + tableField + " = " + numberValue + "";
		} else if (flag.equals("D")) {
			Date dateValue = (Date) checkValue;
			Date sqlDate = new java.sql.Date(dateValue.getTime());
			queryString = "select * from " + tableName + " where to_char(" + tableField + ",'yyyy-MM-dd')='" + sqlDate + "'";
		}
		System.out.println(queryString);
		List list = getSession().createSQLQuery(queryString).list();
		if (list != null && !list.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public List<ParameterDefinition> getFlexiFieldfromDBWithList(
			BigDecimal parameterMasterId) {
		LOGGER.info("parameterMasterId " + parameterMasterId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDefinition.class, "parameterDefinition");
		criteria.setFetchMode("parameterDefinition.parameterMasterId", FetchMode.JOIN);
		criteria.createAlias("parameterDefinition.parameterMasterId", "paramaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("paramaster.parameterMasterId", parameterMasterId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ParameterDefinition> list = null;
		list = (List<ParameterDefinition>) findAll(criteria);
		return list;
	}

	@Override
	public ParameterDefinition isValueIndicator(String fieldName, BigDecimal parameterMasterId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDefinition.class, "parameterDefinition");
		criteria.setFetchMode("parameterDefinition.parameterMasterId", FetchMode.JOIN);
		criteria.createAlias("parameterDefinition.parameterMasterId", "paramaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("paramaster.parameterMasterId", parameterMasterId));
		criteria.add(Restrictions.eq("parameterDefinition.fieldName", fieldName));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ParameterDefinition> list = null;
		list = (List<ParameterDefinition>) findAll(criteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<BigDecimal> getValueList(String tableName, String columnName) {
		String queryString = "";
			queryString = "select "+columnName +" from " + tableName + "";
	
		System.out.println(queryString);
		List<Object> list = getSession().createSQLQuery(queryString).list();
		
		List<BigDecimal> numList= new ArrayList<BigDecimal>();
		if (list != null && !list.isEmpty()) {
			
			for (int i = 0; i < list.size(); i++) {
				
			if(list.get(i)!=null) {
			BigDecimal big = new BigDecimal(list.get(i).toString());
			
			numList.add(big);
			}
			}
			
			return numList;
		}
		return null;
	}

	@Override
	public List<String> getStringValueList(String tableName, String columnName) {
		String queryString = "";
		queryString = "select "+columnName +" from " + tableName + "";

	System.out.println(queryString);
	List<Object> list = getSession().createSQLQuery(queryString).list();
	
	List<String> strList= new ArrayList<String>();
	if (list != null && !list.isEmpty()) {
		
		for (int i = 0; i < list.size(); i++) {
			
		if(list.get(i)!=null && !list.get(i).toString().equals(null)) {
		String str = new String(list.get(i).toString());
		strList.add(str);
		}
		
		
		}
		
		return strList;
	}
	return null;
}

	@Override
	public List<ParameterGrant> getGrantListfortheUser(String userName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterGrant.class, "parameterGrant");
		criteria.setFetchMode("parameterGrant.userId", FetchMode.JOIN);
		criteria.createAlias("parameterGrant.userId", "user", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("user.userName", userName));
		criteria.add(Restrictions.eq("parameterGrant.isActive", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ParameterGrant>) findAll(criteria);
		
	}

	@Override
	public List<ParameterMaster> getUserRightsRecord(ArrayList<String> grantRecordList) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterMaster.class, "parameterMaster");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.in("recordId", grantRecordList));
		criteria.addOrder(Order.asc("parameterMaster.recordId"));
		return (List<ParameterMaster>) findAll(criteria);
	}

	@Override
	public List<ParameterDetails> fetchAllunApprovedRecordwithPermission(ArrayList<String> grantRecordList) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDetails.class, "parameterDetails");
		criteria.setFetchMode("parameterDetails.parameterMasterId", FetchMode.JOIN);
		criteria.createAlias("parameterDetails.parameterMasterId", "parameterMasterId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("parameterDetails.isActive", "U"));
		criteria.add(Restrictions.in("recordId", grantRecordList));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ParameterDetails>) findAll(criteria);
	}

	@Override
	public boolean isExistInDatabase(BigDecimal parameterMasterId, String parameterCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ParameterDetails.class, "parameterDetails");
		criteria.setFetchMode("parameterDetails.parameterMasterId", FetchMode.JOIN);
		criteria.createAlias("parameterDetails.parameterMasterId", "parameterMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("parameterMaster.parameterMasterId", parameterMasterId));
		criteria.add(Restrictions.eq("parameterDetails.paramCodeDef", parameterCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ParameterDetails> list = (List<ParameterDetails>) findAll(criteria);
		if (list != null && !list.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public ParameterDetails populateParamaterDetails(BigDecimal parameterMasterId, String paramcodedefintion,BigDecimal parameterDetailsId) {
		DetachedCriteria dcriteria = DetachedCriteria.forClass(ParameterDetails.class, "parameterDetails");
		dcriteria.setFetchMode("parameterDetails.parameterMasterId", FetchMode.JOIN);
		dcriteria.createAlias("parameterDetails.parameterMasterId", "parameterMasterId", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("parameterMasterId.parameterMasterId", parameterMasterId));
		dcriteria.add(Restrictions.eq("parameterDetails.paramCodeDef", paramcodedefintion));
		dcriteria.add(Restrictions.eq("parameterDetails.parameterDetailsId", parameterDetailsId));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ParameterDetails> list = (List<ParameterDetails>) findAll(dcriteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
}
