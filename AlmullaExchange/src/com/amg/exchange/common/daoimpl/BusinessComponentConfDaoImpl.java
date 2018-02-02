package com.amg.exchange.common.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import com.amg.exchange.common.dao.IBusinessComponentConfDao;
import com.amg.exchange.common.model.BizComponentConfDetail;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.BizComponentDataRef;
import com.amg.exchange.common.model.BusinessComponentConf;

@SuppressWarnings("serial")
@Repository
public class BusinessComponentConfDaoImpl <T> extends CommonDaoImpl<T> implements IBusinessComponentConfDao<T>, Serializable {

	
	private static final Logger LOG = Logger.getLogger(BusinessComponentConfDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public BizComponentConfDetail getBusinessComponent(BigDecimal componentId) {
		LOG.info("Entering into getBusinessComponent method");
		LOG.info("componentId == " + componentId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentConfDetail.class, "bizComponentConfDetail");
		criteria.setFetchMode("bizComponentConfDetail.fsBusinessComponentConf", FetchMode.JOIN);
		criteria.createAlias("bizComponentConfDetail.fsBusinessComponentConf", "fsBusinessComponentConf", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBusinessComponentConf.levelId", new BigDecimal(0)));
		criteria.setFetchMode("fsBusinessComponentConf.fsBusinessComponent", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponentConf.fsBusinessComponent", "fsBusinessComponent", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBusinessComponent.componentId", componentId));
		criteria.setFetchMode("fsBusinessComponent.fsComponentType", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponent.fsComponentType", "fsComponentType", JoinType.INNER_JOIN);
		LOG.info("Exit into getBusinessComponent method");
		return ((List<BizComponentConfDetail>) findAll(criteria)).get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BizComponentConfDetail getBusinessComponent(BigDecimal componentId, BigDecimal applicationId, BigDecimal companyId, BigDecimal countryId, BigDecimal pageId) {
		LOG.info("Entering into getBusinessComponent method");
		LOG.info("componentId == "+componentId);
		LOG.info("applicationId == "+applicationId);
		LOG.info("companyId == "+companyId);
		LOG.info("countryId == "+countryId);
		LOG.info("pageId == "+pageId);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentConfDetail.class, "bussComponentConfDetail");
		criteria.setFetchMode("bussComponentConfDetail.fsBusinessComponentConf", FetchMode.JOIN);
		criteria.createAlias("bussComponentConfDetail.fsBusinessComponentConf", "fsBusinessComponentConf", JoinType.INNER_JOIN);
		criteria.setFetchMode("fsBusinessComponentConf.fsBusinessComponent", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponentConf.fsBusinessComponent", "fsBusinessComponent", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBusinessComponent.componentId", componentId));
		criteria.setFetchMode("fsBusinessComponent.fsComponentType", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponent.fsComponentType", "fsComponentType", JoinType.INNER_JOIN);
		if (applicationId != null && applicationId.intValue() != 0) {
			criteria.setFetchMode("fsBusinessComponentConf.fsRuleApplicationMaster", FetchMode.JOIN);
			criteria.createAlias("fsBusinessComponentConf.fsRuleApplicationMaster", "fsRuleApplicationMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("fsRuleApplicationMaster.applicationId", applicationId));
		}
		if (companyId != null && companyId.intValue() != 0) {
			criteria.setFetchMode("fsBusinessComponentConf.fsCompanyMaster", FetchMode.JOIN);
			criteria.createAlias("fsBusinessComponentConf.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		}
		if (countryId != null && countryId.intValue() != 0) {
			criteria.setFetchMode("fsBusinessComponentConf.fsCountryMaster", FetchMode.JOIN);
			criteria.createAlias("fsBusinessComponentConf.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		}
		if (pageId != null && pageId.intValue() != 0) {
			criteria.setFetchMode("fsBusinessComponentConf.fsRulePageMaster", FetchMode.JOIN);
			criteria.createAlias("fsBusinessComponentConf.fsRulePageMaster", "fsRulePageMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("fsRulePageMaster.pageId", pageId));
		}
		LOG.info("Exit into getBusinessComponent method");
		return ((List<BizComponentConfDetail>) findAll(criteria)).get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizComponentDataDesc> getBusinessComponentDropDownData(BigDecimal componentConfId, BigDecimal languageId) {
		LOG.info("Entering  into getBusinessComponentDropDownData method");
		LOG.info("componentConfId == "+componentConfId);
		LOG.info("languageId == "+languageId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentDataDesc.class, "bizComponentDataDesc");
		criteria.setFetchMode("bizComponentDataDesc.fsBizComponentData", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataDesc.fsBizComponentData", "fsBizComponentData", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBizComponentData.active", "Y"));
		criteria.setFetchMode("fsBizComponentData.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("fsBizComponentData.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		DetachedCriteria subCriteria = DetachedCriteria.forClass(BizComponentDataRef.class, "bizComponentDataRef");
		subCriteria.setFetchMode("bizComponentDataRef.fsBusinessComponentConf", FetchMode.JOIN);
		subCriteria.createAlias("bizComponentDataRef.fsBusinessComponentConf", "fsBusinessComponentConf", JoinType.INNER_JOIN);
		subCriteria.add(Restrictions.eq("fsBusinessComponentConf.componentConfId", componentConfId));
		subCriteria.setFetchMode("bizComponentDataRef.fsBizComponentData", FetchMode.JOIN);
		subCriteria.createAlias("bizComponentDataRef.fsBizComponentData", "bizComponentData", JoinType.INNER_JOIN);
		subCriteria.add(Restrictions.eqProperty("bizComponentData.componentDataId", "fsBizComponentData.componentDataId"));
		subCriteria.setProjection(Projections.distinct(Projections.property("bizComponentData.componentDataId")));
		criteria.add(Subqueries.propertyEq("fsBizComponentData.componentDataId", subCriteria));
		LOG.info("Exit into getBusinessComponentDropDownData method");
		return (List<BizComponentDataDesc>) findAll(criteria);
	}

	@Override
	public void saveOrUpdate(T entity) {
		LOG.info("Entering into saveOrUpdate method");
		getSession().saveOrUpdate(entity);
		LOG.info("Exit into saveOrUpdate method");
	} 

	@Override
	@SuppressWarnings({ "unchecked" })
	public List<Object[]> getBusinessComponentTree(BigDecimal componentId) {
		LOG.info("Entering into getBusinessComponentTree method");
		LOG.info("componentId == "+componentId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BusinessComponentConf.class, "businessComponentConf");
		criteria.add(Restrictions.ne("businessComponentConf.levelId", new BigDecimal(0)));
		criteria.setFetchMode("businessComponentConf.fsBusinessComponent", FetchMode.JOIN);
		criteria.createAlias("businessComponentConf.fsBusinessComponent", "fsBusinessComponent", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBusinessComponent.componentId", componentId));
		criteria.setFetchMode("businessComponentConf.fsRuleApplicationMaster", FetchMode.JOIN);
		criteria.createAlias("businessComponentConf.fsRuleApplicationMaster", "fsRuleApplicationMaster", JoinType.LEFT_OUTER_JOIN);
		criteria.setFetchMode("businessComponentConf.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("businessComponentConf.fsCompanyMaster", "fsCompanyMaster", JoinType.LEFT_OUTER_JOIN);
		criteria.setFetchMode("businessComponentConf.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("businessComponentConf.fsCountryMaster", "fsCountryMaster", JoinType.LEFT_OUTER_JOIN);
		criteria.setFetchMode("businessComponentConf.fsRulePageMaster", FetchMode.JOIN);
		criteria.createAlias("businessComponentConf.fsRulePageMaster", "fsRulePageMaster", JoinType.LEFT_OUTER_JOIN);
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("fsRuleApplicationMaster.applicationId"));
		projectionList.add(Projections.property("fsRuleApplicationMaster.applicationName"));
		projectionList.add(Projections.property("fsCompanyMaster.companyId"));
		projectionList.add(Projections.property("fsCompanyMaster.companyCode"));
		projectionList.add(Projections.property("fsCountryMaster.countryId"));
		projectionList.add(Projections.property("fsRulePageMaster.pageId"));
		projectionList.add(Projections.property("fsRulePageMaster.pageName"));
		projectionList.add(Projections.property("businessComponentConf.componentConfId"));
		criteria.setProjection(projectionList);
		criteria.addOrder(Order.desc("businessComponentConf.levelId"));
		LOG.info("Exit into getBusinessComponentTree method");
		return (List<Object[]>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizComponentDataRef> getBusinessComponentDataRef(BigDecimal componentConfId, BigDecimal languageId) {
		LOG.info("Entering into getBusinessComponentDataRef method");
		LOG.info("componentConfId == "+componentConfId);
		LOG.info("languageId == "+languageId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentDataRef.class, "bizComponentDataRef");
		criteria.setFetchMode("bizComponentDataRef.fsBusinessComponentConf", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataRef.fsBusinessComponentConf", "fsBusinessComponentConf", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBusinessComponentConf.componentConfId", componentConfId));
		criteria.setFetchMode("bizComponentDataRef.fsBizComponentData", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataRef.fsBizComponentData", "fsBizComponentData", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("fsBizComponentData.active", "Y"));
		criteria.setFetchMode("fsBizComponentData.fsBizComponentDataDescs", FetchMode.JOIN);
		criteria.createAlias("fsBizComponentData.fsBizComponentDataDescs", "fsBizComponentDataDescs", JoinType.LEFT_OUTER_JOIN);
		criteria.setFetchMode("fsBizComponentDataDescs.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("fsBizComponentDataDescs.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		criteria.addOrder(Order.asc("fsBizComponentDataDescs.dataDesc"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBusinessComponentDataRef method");
		return (List<BizComponentDataRef>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigDecimal> getBusinessComponentData(BigDecimal componentId, BigDecimal languageId) {
		LOG.info("Entering into getBusinessComponentDataRef method");
		LOG.info("componentId == "+componentId);
		LOG.info("languageId == "+languageId);
		Map<String, BigDecimal> mapReturn = new HashMap<String, BigDecimal>();
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentData.class, "bizComponentData");
		criteria.setFetchMode("bizComponentData.fsBusinessComponent", FetchMode.JOIN);
		criteria.createAlias("bizComponentData.fsBusinessComponent", "fsBusinessComponent", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBusinessComponent.componentId", componentId));
		criteria.setFetchMode("bizComponentData.fsBizComponentDataDescs", FetchMode.JOIN);
		criteria.createAlias("bizComponentData.fsBizComponentDataDescs", "fsBizComponentDataDescs", JoinType.LEFT_OUTER_JOIN);
		criteria.setFetchMode("fsBizComponentDataDescs.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("fsBizComponentDataDescs.fsLanguageType", "fsLanguageType", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("fsBizComponentDataDescs.dataDesc"));
		projectionList.add(Projections.groupProperty("bizComponentData.componentDataId"));
		criteria.setProjection(projectionList);
		for (Object[] row : (List<Object[]>) findAll(criteria)) {
			mapReturn.put((String) row[0], (BigDecimal) row[1]);
		}
		LOG.info("Exit into getBusinessComponentDataRef method");
		return mapReturn;
	} 
}
