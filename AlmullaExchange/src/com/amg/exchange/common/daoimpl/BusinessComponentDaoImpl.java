package com.amg.exchange.common.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import com.amg.exchange.common.dao.IBusinessComponentDao;
import com.amg.exchange.common.model.BizComponentConfDetail;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.BizComponentDataRef;
import com.amg.exchange.common.model.BusinessComponent;
import com.amg.exchange.common.model.ComponentType;

@SuppressWarnings("serial")
@Repository
public class BusinessComponentDaoImpl <T> extends CommonDaoImpl<T> implements IBusinessComponentDao<T>, Serializable {

	private static final Logger LOG = Logger.getLogger(BusinessComponentDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<ComponentType> getComponentType() {
		LOG.info("Entering into getComponentType method");
		DetachedCriteria criteria = DetachedCriteria.forClass(ComponentType.class, "componentType");
		criteria.add(Restrictions.eq("componentType.isActive", "Y"));
		criteria.setFetchMode("componentType.componentTypeDetail", FetchMode.JOIN);
		criteria.createAlias("componentType.componentTypeDetail", "componentTypeDetail", JoinType.LEFT_OUTER_JOIN);
		LOG.info("Exit into getComponentType method");
		return (List<ComponentType>)criteria.getExecutableCriteria(getSession()).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BusinessComponent> getAllBusinessComponent() { 
		LOG.info("Entering into getAllBusinessComponent method");
		DetachedCriteria criteria = DetachedCriteria.forClass(BusinessComponent.class, "businessComponent");
		LOG.info("Exit into getAllBusinessComponent method");
		return (List<BusinessComponent>)findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BizComponentConfDetail getBusinessComponent(String componentName) {
		LOG.info("Entering into getBusinessComponent method");
		LOG.info("componentName == "+componentName);
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentConfDetail.class, "bizComponentConfDetail");
		criteria.setFetchMode("bizComponentConfDetail.fsBusinessComponentConf", FetchMode.JOIN);
		criteria.createAlias("bizComponentConfDetail.fsBusinessComponentConf", "fsBusinessComponentConf", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBusinessComponentConf.levelId", new BigDecimal(0)));
		criteria.setFetchMode("fsBusinessComponentConf.fsBusinessComponent", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponentConf.fsBusinessComponent", "fsBusinessComponent", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBusinessComponent.componentName", componentName).ignoreCase());
		criteria.setFetchMode("fsBusinessComponent.fsComponentType", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponent.fsComponentType", "fsComponentType", JoinType.INNER_JOIN);
		LOG.info("Exit into getBusinessComponent method");
		return ((List<BizComponentConfDetail>) findAll(criteria)).get(0);
	}

	@Override 
	@SuppressWarnings("unchecked")
	public List<BizComponentDataDesc> getBusinessComponentDropDownData(BigDecimal componentConfId, BigDecimal languageId) {
		LOG.info("Entering into getBusinessComponentDropDownData method");
		LOG.info("componentConfId == " + componentConfId);
		LOG.info("languageId == " + languageId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentDataDesc.class, "bizComponentDataDesc");
		criteria.setFetchMode("bizComponentDataDesc.fsBizComponentData", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataDesc.fsBizComponentData", "fsBizComponentData", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBizComponentData.active", "Y"));
		criteria.setFetchMode("bizComponentDataDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		DetachedCriteria subCriteria = DetachedCriteria.forClass(BizComponentDataRef.class, "bizComponentDataRef");
		subCriteria.setFetchMode("bizComponentDataRef.fsBusinessComponentConf", FetchMode.JOIN);
		subCriteria.createAlias("bizComponentDataRef.fsBusinessComponentConf", "fsBusinessComponentConf", JoinType.INNER_JOIN);
		subCriteria.add(Restrictions.eq("fsBusinessComponentConf.componentConfId", componentConfId));
		subCriteria.setFetchMode("bizComponentDataRef.fsBizComponentData", FetchMode.JOIN);
		subCriteria.createAlias("bizComponentDataRef.fsBizComponentData", "bizComponentData", JoinType.INNER_JOIN);
		subCriteria.add(Restrictions.eqProperty("bizComponentData.componentDataId", "fsBizComponentData.componentDataId"));
		subCriteria.setProjection(Projections.distinct(Projections.property("bizComponentData.componentDataId")));
		criteria.add(Subqueries.propertyIn("fsBizComponentData.componentDataId", subCriteria));
		LOG.info("Exit into getBusinessComponentDropDownData method");
		return (List<BizComponentDataDesc>) findAll(criteria);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getAllBusinessComponent(String query) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BusinessComponent.class, "bussComponentComboData");
		criteria.add(Restrictions.like("bussComponentComboData.componentName", query, MatchMode.ANYWHERE).ignoreCase());
		criteria.setProjection(Projections.property("bussComponentComboData.componentName"));
		criteria.addOrder(Order.asc("bussComponentComboData.componentName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<String>) findAll(criteria);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<BigDecimal, BizComponentDataRef> getAllComponentDataRef(BigDecimal componentConfId) {
		LOG.info("Entering into getAllComponentDataRef method");
		LOG.info("componentConfId == "+componentConfId);
		
		Map<BigDecimal, BizComponentDataRef> mapReturn = new HashMap<BigDecimal, BizComponentDataRef>();
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentDataRef.class, "bizComponentDataRef");
		
		criteria.setFetchMode("bizComponentDataRef.fsBusinessComponentConf", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataRef.fsBusinessComponentConf", "fsBusinessComponentConf", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBusinessComponentConf.componentConfId", componentConfId));
		
		criteria.setFetchMode("bizComponentDataRef.fsBizComponentData", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataRef.fsBizComponentData", "fsBizComponentData", JoinType.INNER_JOIN);
		
		for(BizComponentDataRef bean: (List<BizComponentDataRef>)findAll(criteria)){
			mapReturn.put(bean.getFsBizComponentData().getComponentDataId(), bean);
		}
		LOG.info("Exit into getAllComponentDataRef method");
		return mapReturn;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<BizComponentData> getBusinessComponentData(BigDecimal componentConfId) {
		LOG.info("Entering into getBusinessComponentData method");
		LOG.info("componentConfId == " + componentConfId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentData.class, "fsBizComponentData");
		criteria.add(Restrictions.eq("fsBizComponentData.active", "Y"));
		criteria.setFetchMode("fsBizComponentData.fsBizComponentDataDescs", FetchMode.JOIN);
		criteria.createAlias("fsBizComponentData.fsBizComponentDataDescs", "fsBizComponentDataDescs", JoinType.LEFT_OUTER_JOIN);
		criteria.setFetchMode("fsBizComponentDataDescs.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("fsBizComponentDataDescs.fsLanguageType", "fsLanguageType", JoinType.LEFT_OUTER_JOIN);
		DetachedCriteria subCriteria = DetachedCriteria.forClass(BizComponentDataRef.class, "bizComponentDataRef");
		subCriteria.setFetchMode("bizComponentDataRef.fsBusinessComponentConf", FetchMode.JOIN);
		subCriteria.createAlias("bizComponentDataRef.fsBusinessComponentConf", "fsBusinessComponentConf", JoinType.INNER_JOIN);
		subCriteria.add(Restrictions.eq("fsBusinessComponentConf.componentConfId", componentConfId));
		subCriteria.setFetchMode("bizComponentDataRef.fsBizComponentData", FetchMode.JOIN);
		subCriteria.createAlias("bizComponentDataRef.fsBizComponentData", "bizComponentData", JoinType.INNER_JOIN);
		subCriteria.add(Restrictions.eqProperty("bizComponentData.componentDataId", "fsBizComponentData.componentDataId"));
		subCriteria.setProjection(Projections.distinct(Projections.property("bizComponentData.componentDataId")));
		criteria.add(Subqueries.propertyIn("fsBizComponentData.componentDataId", subCriteria));
		criteria.addOrder(Order.asc("fsBizComponentDataDescs.dataDesc"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Entering into getBusinessComponentData method");
		return (List<BizComponentData>) findAll(criteria);
	}

	@Override
	public void saveOrUpdate(T entity) {
		LOG.info("Entering into saveOrUpdate method");
		super.saveOrUpdate(entity);
		LOG.info("Exit into saveOrUpdate method");
	}

}
