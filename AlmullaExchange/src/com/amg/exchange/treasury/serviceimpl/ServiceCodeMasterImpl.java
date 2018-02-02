package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.IServiceCodeMasterDao;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;

@Service("serviceCodeMasterService")
public class ServiceCodeMasterImpl implements ServiceCodeMasterService {

	@Autowired
	IServiceCodeMasterDao serviceCodeMasterDao;

	@Override
	@Transactional
	public void saveRecord(ServiceMaster serviceMaster) {

		serviceCodeMasterDao.saveRecord(serviceMaster);
	}

	@Override
	@Transactional
	public List<ServiceMaster> getServiceMastersList() {
		return serviceCodeMasterDao.getServiceMasterList();

	}

	@Override
	@Transactional
	public List<ServiceMaster> getServiceCheck(String serviceCode) {

		return serviceCodeMasterDao.getRemittanceCheck(serviceCode);
	}

	@Override
	@Transactional
	public void save(ServiceMasterDesc serviceMasterDesc) {
		serviceCodeMasterDao.save(serviceMasterDesc);

	}

	@Override
	@Transactional
	public  List<ServiceMasterDesc> LocalServiceDescriptionFromDB(BigDecimal languageId , BigDecimal serviceId){
		return serviceCodeMasterDao.LocalServiceDescriptionFromDB(languageId,serviceId);

	}

	@Override
	@Transactional
	public void updateRecordServiceMaster(ServiceMaster serviceMaster) {
		serviceCodeMasterDao.updateRecordServiceMaster(serviceMaster);
	}

	@Override
	@Transactional
	public void updateRecordServiceMasterDesc(ServiceMasterDesc serviceMasterDesc) {
		serviceCodeMasterDao.updateRecordServiceMasterDesc(serviceMasterDesc);

	}


	@Transactional
	public List<String> getServiceCodeList(String query,BigDecimal serviceGroupId){

		return serviceCodeMasterDao.getServiceCodeList(query,serviceGroupId);

	}
	@Transactional
	public void saveRecordToDB(ServiceMaster serviceMaster){
		serviceCodeMasterDao.saveRecordToDB(serviceMaster);
	}
	@Transactional
	public void saveToDB(ServiceMasterDesc serviceMasterDesc){
		serviceCodeMasterDao.saveToDB(serviceMasterDesc);
	}

	@Override
	@Transactional
	public List<ServiceMasterDesc> getAllServiceList(BigDecimal serviceGroupId){
		return serviceCodeMasterDao.getAllServiceList(serviceGroupId);
	}

	@Transactional
	public List<ServiceMasterDesc> getAllServicesForApprove(){
		return serviceCodeMasterDao.getAllServicesForApprove();
	}

	@Transactional
	public String approveReord(BigDecimal servicePk,String userName){
		return serviceCodeMasterDao.approveReord(servicePk,userName);
	}

	@Override
	@Transactional
	public List<ServiceMasterDesc> LocalServiceDescription(BigDecimal serviceId) {
		return serviceCodeMasterDao.LocalServiceDescription(serviceId);
	}

	@Override
	@Transactional
	public void deleteRecordPermanently(BigDecimal servicePk,BigDecimal serviceEngPk,BigDecimal serviceArbPk){
		serviceCodeMasterDao.deleteRecordPermanently(servicePk,serviceEngPk,serviceArbPk);
	}

	@Override
	@Transactional
	public List<ServiceMasterDesc> getServiceCodeCheck(String serviceCode) {

		return serviceCodeMasterDao.getServiceCodeCheck(serviceCode);
	}

	@Override
	@Transactional
	public void activateRecord(BigDecimal servicePk,String userName){
		serviceCodeMasterDao.activateRecord(servicePk,userName);
	}

	@Override
	@Transactional
	public List<ServiceGroupMasterDesc> LocalServiceGroupDescription(BigDecimal languageId, BigDecimal serviceGroupId) {
		return serviceCodeMasterDao.LocalServiceGroupDescription(languageId,serviceGroupId);
	}
	@Override
	@Transactional
	public List<ServiceGroupMasterDesc> getServiceGroupDescription(BigDecimal languageId){
		return serviceCodeMasterDao.getServiceGroupDescription(languageId);
	}

	@Override
	@Transactional
	public BigDecimal getServiceIdbyServiceName(String serviceName) {
		// TODO Auto-generated method stub
		return serviceCodeMasterDao.getServiceIdbyServiceName(serviceName);
	}

	  @Override
	  @Transactional
	  public String toFetchServiceGroupName(BigDecimal serviceGroupId, BigDecimal languageId) {
		    return serviceCodeMasterDao.toFetchServiceGroupName(serviceGroupId,languageId);
	  }

	  @Override
	  @Transactional
	  public List<ServiceMaster> toFetchServiceRecordsServiceMaster(BigDecimal serviceGroup) {
		    return serviceCodeMasterDao.toFetchServiceRecordsServiceMaster(serviceGroup);
	  }

}
