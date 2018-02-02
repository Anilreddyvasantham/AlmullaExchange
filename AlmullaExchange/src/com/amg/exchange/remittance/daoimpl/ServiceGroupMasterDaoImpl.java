package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.dao.IServiceGroupMasterDao;
import com.amg.exchange.treasury.model.ServiceGroupMaster;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class ServiceGroupMasterDaoImpl extends CommonDaoImpl implements IServiceGroupMasterDao {
	  private static final Logger log = Logger.getLogger(ServiceGroupMasterDaoImpl.class);

	  public ServiceGroupMasterDaoImpl() {
	  }

	  @Override
	  public void save(ServiceGroupMaster serviceGroupMaster) {

		    getSession().saveOrUpdate(serviceGroupMaster);
	  }

	  @Override
	  public void save(ServiceGroupMasterDesc serviceGroupMasterDesc) {

		    getSession().saveOrUpdate(serviceGroupMasterDesc);

	  }

	  @Override
	  @SuppressWarnings("unchecked")
	  public Boolean isExistServiceGroupCode(String serviceGroupCode) {

		    DetachedCriteria criteria = DetachedCriteria.forClass(ServiceGroupMaster.class, "serviceGroupMaster");

		    criteria.add(Restrictions.eq("serviceGroupMaster.serviceGroupCode", serviceGroupCode));

		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    criteria.getExecutableCriteria(getSession()).setCacheable(true);

		    List<ServiceGroupMaster> data = (List<ServiceGroupMaster>) findAll(criteria);

		    if (data.isEmpty())
			      return false;

		    return true;
	  }

	  @Override
	  @SuppressWarnings("unchecked")
	  public List<ServiceGroupMasterDesc> getServiceGroupDescList(BigDecimal serviceGroupId) {

		    DetachedCriteria criteria = DetachedCriteria.forClass(ServiceGroupMasterDesc.class, "serviceGroupMasterDesc");

		    criteria.setFetchMode("serviceGroupMasterDesc.serviceGroupMasterId", FetchMode.JOIN);
		    criteria.createAlias("serviceGroupMasterDesc.serviceGroupMasterId", "serviceGroupMasterId", JoinType.INNER_JOIN);

		    criteria.add(Restrictions.eq("serviceGroupMasterId.serviceGroupId", serviceGroupId));

		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    // criteria.getExecutableCriteria(getSession()).setCacheable(true);

		    List<ServiceGroupMasterDesc> data = (List<ServiceGroupMasterDesc>) findAll(criteria);

		    /*
		     * if (data.isEmpty()) return null;
		     */

		    return data;
	  }

	  @Override
	  @SuppressWarnings("unchecked")
	  public List<ServiceGroupMaster> getServiceGroupMasterList() {

		    DetachedCriteria criteria = DetachedCriteria.forClass(ServiceGroupMaster.class, "serviceGroupMaster");

		    // criteria.add(Restrictions.eq("serviceGroupMaster.serviceGroupCode",
		    // serviceGroupCode));

		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    criteria.getExecutableCriteria(getSession()).setCacheable(true);

		    List<ServiceGroupMaster> data = (List<ServiceGroupMaster>) findAll(criteria);

		    if (data.isEmpty())
			      return null;

		    return data;
	  }

	  @Override
	  public void delete(BigDecimal serviceGroupId, BigDecimal serviceGroupEngDescId, BigDecimal serviceGroupLocalDescId) {

		    ServiceGroupMaster serviceGroupMaster = (ServiceGroupMaster) getSession().get(ServiceGroupMaster.class, serviceGroupId);
		    ServiceGroupMasterDesc serviceGroupEngDesc = (ServiceGroupMasterDesc) getSession().get(ServiceGroupMasterDesc.class, serviceGroupEngDescId);
		    ServiceGroupMasterDesc serviceGroupArbDesc = (ServiceGroupMasterDesc) getSession().get(ServiceGroupMasterDesc.class, serviceGroupLocalDescId);
		    getSession().delete(serviceGroupEngDesc);
		    getSession().delete(serviceGroupArbDesc);
		    getSession().delete(serviceGroupMaster);
		    // getSession().flush();
	  }

	  @Override
	  public void activateRecord(BigDecimal serviceGroupId, String userName) {
		    ServiceGroupMaster serviceGroupMaster = (ServiceGroupMaster) getSession().get(ServiceGroupMaster.class, serviceGroupId);
		    serviceGroupMaster.setIsActive(Constants.U);
		    serviceGroupMaster.setModifiedBy(userName);
		    serviceGroupMaster.setModifiedDate(new Date());
		    serviceGroupMaster.setApprovedBy(null);
		    serviceGroupMaster.setApprovedDate(null);
		    serviceGroupMaster.setRemarks(null);
		    getSession().update(serviceGroupMaster);

	  }

	  @Override
	  @SuppressWarnings("unchecked")
	  public List<String> toFetchServiceGroupCodeList(String query) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(ServiceGroupMaster.class, "serviceGroupMaster");
		    criteria.add(Restrictions.like("serviceGroupMaster.serviceGroupCode", query, MatchMode.START).ignoreCase());
		    criteria.setProjection(Projections.property("serviceGroupMaster.serviceGroupCode"));
		    criteria.addOrder(Order.asc("serviceGroupMaster.serviceGroupCode"));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<String> lstServiceGroupMaster = (List<String>) findAll(criteria);
		    return lstServiceGroupMaster;
	  }

	  @Override
	  @SuppressWarnings("unchecked")
	  public List<ServiceGroupMaster> toServiceGroupCodeAllValues(String serviceGroupCode) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(ServiceGroupMaster.class, "serviceGroupMaster");
		    criteria.add(Restrictions.eq("serviceGroupMaster.serviceGroupCode", serviceGroupCode));
		    List<ServiceGroupMaster> lstServiceGroupMaster = (List<ServiceGroupMaster>) findAll(criteria);
		    return lstServiceGroupMaster;
	  }

	  @Override
	  public void upDateActiveRecordToDeAtivate(BigDecimal serviceGroupId, String userName, String remarks) {
		    ServiceGroupMaster serviceGroupMaster = (ServiceGroupMaster) getSession().get(ServiceGroupMaster.class, serviceGroupId);
		    serviceGroupMaster.setIsActive(Constants.D);
		    serviceGroupMaster.setModifiedBy(userName);
		    serviceGroupMaster.setModifiedDate(new Date());
		    serviceGroupMaster.setApprovedBy(null);
		    serviceGroupMaster.setApprovedDate(null);
		    serviceGroupMaster.setRemarks(remarks);
		    getSession().update(serviceGroupMaster);
	  }

	  @Override
	  public void saveOrUpdate(ServiceGroupMaster serviceGroupMaster, ServiceGroupMasterDesc serviceGroupMasterDesc, ServiceGroupMasterDesc serviceGroupMasterDesc1) {
		  
			      getSession().saveOrUpdate(serviceGroupMaster);
			      getSession().saveOrUpdate(serviceGroupMasterDesc);
			      getSession().saveOrUpdate(serviceGroupMasterDesc1);
		     
	  }
}
