package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.dao.IServiceGroupMasterDao;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.treasury.model.ServiceGroupMaster;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;

@Service("serviceGroupMasterService")
public class ServiceGroupMasterServiceImpl implements IServiceGroupMasterService {

	  @Autowired
	  IServiceGroupMasterDao serviceGroupMasterDao;

	  public ServiceGroupMasterServiceImpl() {
		    // TODO Auto-generated constructor stub
	  }

	  public IServiceGroupMasterDao getServiceGroupMasterDao() {
		    return serviceGroupMasterDao;
	  }

	  public void setServiceGroupMasterDao(IServiceGroupMasterDao serviceGroupMasterDao) {
		    this.serviceGroupMasterDao = serviceGroupMasterDao;
	  }

	  @Override
	  @Transactional
	  public void save(ServiceGroupMaster serviceGroupMaster) {
		    getServiceGroupMasterDao().save(serviceGroupMaster);

	  }

	  @Override
	  @Transactional
	  public void save(ServiceGroupMasterDesc serviceGroupMasterDesc) {
		    getServiceGroupMasterDao().save(serviceGroupMasterDesc);

	  }

	  @Override
	  @Transactional
	  public Boolean isExistServiceGroupCode(String serviceGroupCode) {
		    return getServiceGroupMasterDao().isExistServiceGroupCode(serviceGroupCode);
	  }

	  @Override
	  @Transactional
	  public List<ServiceGroupMaster> getServiceGroupMasterList() {
		    return getServiceGroupMasterDao().getServiceGroupMasterList();
	  }

	  @Override
	  @Transactional
	  public List<ServiceGroupMasterDesc> getServiceGroupDescList(BigDecimal serviceGroupId) {
		    return getServiceGroupMasterDao().getServiceGroupDescList(serviceGroupId);
	  }

	  @Override
	  @Transactional
	  public void delete(BigDecimal serviceGroupId, BigDecimal serviceGroupEngDescId, BigDecimal serviceGroupLocalDescId) {
		    getServiceGroupMasterDao().delete(serviceGroupId, serviceGroupEngDescId, serviceGroupLocalDescId);
	  }

	  @Override
	  @Transactional
	  public void activateRecord(BigDecimal serviceGroupId, String userName) {
		    getServiceGroupMasterDao().activateRecord(serviceGroupId, userName);

	  }

	  @Override
	  @Transactional
	  public List<String> toFetchServiceGroupCodeList(String query) {
		    return getServiceGroupMasterDao().toFetchServiceGroupCodeList(query);
	  }

	  @Override
	  @Transactional
	  public List<ServiceGroupMaster> toServiceGroupCodeAllValues(String serviceGroupCode) {
		    return getServiceGroupMasterDao().toServiceGroupCodeAllValues(serviceGroupCode);
	  }

	  @Override
	  @Transactional
	  public void upDateActiveRecordToDeAtivate(BigDecimal serviceGroupId, String userName, String remarks) {
		    getServiceGroupMasterDao().upDateActiveRecordToDeAtivate(serviceGroupId, userName, remarks);
	  }

	  @Override
	  @Transactional
	  public void saveOrUpdate(ServiceGroupMaster serviceGroupMaster, ServiceGroupMasterDesc serviceGroupMasterDesc, ServiceGroupMasterDesc serviceGroupMasterDesc1) {
		    getServiceGroupMasterDao().saveOrUpdate(serviceGroupMaster, serviceGroupMasterDesc, serviceGroupMasterDesc1);
	  }

}
