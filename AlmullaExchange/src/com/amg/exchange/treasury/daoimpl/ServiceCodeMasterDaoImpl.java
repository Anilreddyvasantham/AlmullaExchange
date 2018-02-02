package com.amg.exchange.treasury.daoimpl;

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
import com.amg.exchange.treasury.dao.IServiceCodeMasterDao;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.util.Constants;

@SuppressWarnings("unchecked")
@Repository
public class ServiceCodeMasterDaoImpl<T> extends CommonDaoImpl<T> implements IServiceCodeMasterDao {

	Logger LOGGER = Logger.getLogger(ServiceCodeMasterDaoImpl.class);

	@Override
	public void saveRecord(ServiceMaster serviceMaster) {
		getSession().saveOrUpdate(serviceMaster);
	}

	@Override
	public List<ServiceMaster> getServiceMasterList() {
		DetachedCriteria criteria=DetachedCriteria.forClass(ServiceMaster.class);
		return (List<ServiceMaster>)findAll(criteria);
	}

	@Override
	public List<ServiceMaster> getRemittanceCheck(String serviceCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceMaster.class, "serviceMaster");

		criteria.add(Restrictions.eq("serviceMaster.serviceCode", serviceCode).ignoreCase());

		return (List<ServiceMaster>)findAll(criteria);
	}

	@Override
	public void save(ServiceMasterDesc serviceMasterDesc) {
		getSession().saveOrUpdate(serviceMasterDesc);
	}

	/*@Override
    public List<ServiceMaster> getServiceCodeCheck(String serviceCode) {
	DetachedCriteria criteria = DetachedCriteria.forClass(ServiceMaster.class, "serviceMaster");
	criteria.add(Restrictions.eq("serviceMaster.serviceCode", serviceCode).ignoreCase());

	return (List<ServiceMaster>)findAll(criteria);
    }*/

	/*@Override
    public void save(ServiceMasterDesc serviceMasterDesc) {
	getSession().save((T)(serviceMasterDesc));

    }*/

	@Override
	public List<ServiceMasterDesc> LocalServiceDescriptionFromDB(BigDecimal languageId ,BigDecimal serviceId){
		DetachedCriteria criteria=DetachedCriteria.forClass(ServiceMasterDesc.class,"serviceMasterDesc");
		criteria.setFetchMode("serviceMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.languageType", "languageType",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));

		criteria.setFetchMode("serviceMasterDesc.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.serviceMaster", "serviceMaster",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceMaster.serviceId", serviceId));

		return (List<ServiceMasterDesc>) findAll(criteria);
	}

	@Override
	public void updateRecordServiceMaster(ServiceMaster serviceMaster){
		getSession().update(serviceMaster );
	}

	@Override
	public void updateRecordServiceMasterDesc(ServiceMasterDesc serviceMasterDesc){
		getSession().update(serviceMasterDesc );
	}

	@Override
	public List<String> getServiceCodeList(String query,BigDecimal serviceGroupId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceMaster.class, "serviceMaster");
		criteria.add(Restrictions.like("serviceMaster.serviceCode", query, MatchMode.ANYWHERE).ignoreCase());
		criteria.setFetchMode("serviceMaster.serviceGroupId", FetchMode.JOIN);
		criteria.createAlias("serviceMaster.serviceGroupId", "serviceGroupId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceGroupId.serviceGroupId",serviceGroupId));
		criteria.setProjection(Projections.property("serviceMaster.serviceCode"));
		criteria.addOrder(Order.asc("serviceMaster.serviceCode"));
		//criteria.add(Restrictions.eq("serviceMaster.isActive",  "N"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<String>)findAll(criteria);
	}

	public void saveRecordToDB(ServiceMaster serviceMaster){
		getSession().save(serviceMaster);
	}

	public void saveToDB(ServiceMasterDesc serviceMasterDesc){
		getSession().save(serviceMasterDesc);//(serviceMasterDesc);
	}

	public List<ServiceMasterDesc> getAllServiceList(BigDecimal serviceGroupId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceMasterDesc.class, "serviceMasterDesc");
		criteria.setFetchMode("serviceMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.languageType", "languageType",JoinType.INNER_JOIN);
		criteria.setFetchMode("serviceMasterDesc.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.serviceMaster", "serviceMaster",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceMaster.serviceId",serviceGroupId));	
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		/*if(serviceGroupId!=null){
			criteria.setFetchMode("serviceMaster.serviceGroupId", FetchMode.JOIN);
			criteria.createAlias("serviceMaster.serviceGroupId", "serviceGroupId",JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("serviceGroupId.serviceGroupId",serviceGroupId));	
		}*/

		/*criteria.add(Restrictions.eq("serviceMaster.isActive",  "Y"));
	criteria.add(Restrictions.eq("serviceMaster.isActive",  "D"));*/
		//criteria.add(Restrictions.ne("serviceMaster.isActive","U"));
		return (List<ServiceMasterDesc>)findAll(criteria);
	}
	public List<ServiceMasterDesc> getAllServicesForApprove(){
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceMasterDesc.class, "serviceMasterDesc");
		criteria.setFetchMode("serviceMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.languageType", "languageType",JoinType.INNER_JOIN);
		criteria.setFetchMode("serviceMasterDesc.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.serviceMaster", "serviceMaster",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("serviceMaster.isActive",Constants.U));
		//criteria.add(Restrictions.isNull("serviceMaster.approvedBy"));
		//criteria.add(Restrictions.isNull("serviceMaster.approvedDate"));

		return (List<ServiceMasterDesc>)findAll(criteria);
	}

	@Override
	public String approveReord(BigDecimal servicePk, String userName) {

		String approveMsg;
		ServiceMaster service=(ServiceMaster) getSession().get(ServiceMaster.class, servicePk);
		String approvedUser=service.getApprovedBy();
		if(approvedUser==null)
		{
			service.setIsActive(Constants.Yes);
			service.setApprovedBy(userName);
			service.setApprovedDate(new Date());
			service.setRemarks("");
			getSession().update(service);
			approveMsg="Success";
		}
		else{
			approveMsg="Fail";
		}
		return  approveMsg;

		/*ServiceMaster service=(ServiceMaster) getSession().get(ServiceMaster.class, servicePk);
          service.setIsActive(Constants.Yes);
	      service.setApprovedBy(userName);
	      service.setApprovedDate(new Date());
	      service.setRemarks("");
	      getSession().update(service);*/

	}

	@Override
	public List<ServiceMasterDesc> LocalServiceDescription(BigDecimal serviceId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ServiceMasterDesc.class,"serviceMasterDesc");

		criteria.setFetchMode("serviceMasterDesc.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.serviceMaster", "serviceMaster",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceMaster.serviceId", serviceId));

		return (List<ServiceMasterDesc>) findAll(criteria);
	}

	@Override
	public void deleteRecordPermanently(BigDecimal servicePk,BigDecimal serviceEngPk,BigDecimal serviceArbPk){
		ServiceMaster service=(ServiceMaster) getSession().get(ServiceMaster.class, servicePk);
		ServiceMasterDesc serviceEngDesc=(ServiceMasterDesc) getSession().get(ServiceMasterDesc.class, serviceEngPk);
		ServiceMasterDesc serviceArbDesc=(ServiceMasterDesc) getSession().get(ServiceMasterDesc.class, serviceArbPk);
		getSession().delete(serviceEngDesc );
		getSession().delete(serviceArbDesc );
		getSession().delete(service);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceMasterDesc> getServiceCodeCheck(String serviceCode) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ServiceMasterDesc.class,"serviceMasterDesc");
		criteria.setFetchMode("serviceMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.languageType", "languageType",JoinType.INNER_JOIN);

		criteria.setFetchMode("serviceMasterDesc.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.serviceMaster", "serviceMaster",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceMaster.serviceCode", serviceCode));

		return (List<ServiceMasterDesc>) findAll(criteria);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void activateRecord(BigDecimal servicePk,String userName){
		ServiceMaster service=(ServiceMaster) getSession().get(ServiceMaster.class, servicePk);
		service.setIsActive(Constants.U);
		service.setModifiedBy(userName);
		service.setModifiedDate(new Date());
		service.setApprovedBy(null);
		service.setApprovedDate(null);
		service.setRemarks(null);
		getSession().update(service);

	}

	@Override
	public List<ServiceGroupMasterDesc> LocalServiceGroupDescription(BigDecimal languageId, BigDecimal serviceGroupId) {

		LOGGER.info("Entering into LocalServiceGroupDescription method");
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceGroupMasterDesc.class, "serviceGroupMasterDesc");
		criteria.setFetchMode("serviceGroupMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceGroupMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));

		criteria.setFetchMode("serviceGroupMasterDesc.serviceGroupMasterId", FetchMode.JOIN);
		criteria.createAlias("serviceGroupMasterDesc.serviceGroupMasterId", "serviceGroupMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceGroupMaster.serviceGroupId", serviceGroupId));
		LOGGER.info("Exit into LocalServiceGroupDescription method");
		return (List<ServiceGroupMasterDesc>) findAll(criteria);
		
	}

	@Override
	public List<ServiceGroupMasterDesc> getServiceGroupDescription(BigDecimal languageId) {

		LOGGER.info("Entering into LocalServiceGroupDescription method");
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceGroupMasterDesc.class, "serviceGroupMasterDesc");
		criteria.setFetchMode("serviceGroupMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceGroupMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));

		criteria.setFetchMode("serviceGroupMasterDesc.serviceGroupMasterId", FetchMode.JOIN);
		criteria.createAlias("serviceGroupMasterDesc.serviceGroupMasterId", "serviceGroupMaster", JoinType.INNER_JOIN);
		//criteria.add(Restrictions.eq("serviceGroupMaster.serviceGroupId", serviceGroupId));
		LOGGER.info("Exit into LocalServiceGroupDescription method");
		return (List<ServiceGroupMasterDesc>) findAll(criteria);
		
	}

	@Override
	public BigDecimal getServiceIdbyServiceName(String serviceName) {
		BigDecimal serviceId = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceMasterDesc.class, "serviceMasterDesc");
		
		criteria.setFetchMode("serviceMasterDesc.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.serviceMaster", "serviceMaster",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("serviceMasterDesc.localServiceDescription", serviceName));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ServiceMasterDesc> lstServiceMaster = (List<ServiceMasterDesc>) findAll(criteria);
		if(lstServiceMaster != null && lstServiceMaster.size() != 0){
			serviceId = lstServiceMaster.get(0).getServiceMaster().getServiceId();
		}
		
		return serviceId;
	}

	  @Override
	  public String toFetchServiceGroupName(BigDecimal serviceGroupId, BigDecimal languageId) {
			DetachedCriteria criteria = DetachedCriteria.forClass(ServiceGroupMasterDesc.class, "serviceGroupMasterDesc");
			criteria.setFetchMode("serviceGroupMasterDesc.serviceGroupMasterId", FetchMode.JOIN);
			criteria.createAlias("serviceGroupMasterDesc.serviceGroupMasterId", "serviceGroupMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("serviceGroupMaster.serviceGroupId", serviceGroupId));
			criteria.setFetchMode("serviceGroupMasterDesc.languageType", FetchMode.JOIN);
			criteria.createAlias("serviceGroupMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("languageType.languageId", languageId));
			List<ServiceGroupMasterDesc> lstGroupMasterDescs=(List<ServiceGroupMasterDesc>) findAll(criteria);
			String serviceGroupName=null;
			if(lstGroupMasterDescs != null && lstGroupMasterDescs.size() !=0){
				  serviceGroupName=lstGroupMasterDescs.get(0).getServiceGroupDesc();
			}
			return serviceGroupName;
	  }

	  @Override
	  public List<ServiceMaster> toFetchServiceRecordsServiceMaster(BigDecimal serviceGroup) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(ServiceMaster.class, "serviceMaster");
			criteria.setFetchMode("serviceMaster.serviceGroupId", FetchMode.JOIN);
			criteria.createAlias("serviceMaster.serviceGroupId", "serviceGroupId",JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("serviceGroupId.serviceGroupId",serviceGroup));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<ServiceMaster> lstServiceMasters= (List<ServiceMaster>) findAll(criteria);
			return lstServiceMasters;
	  }
	
	
}

	

