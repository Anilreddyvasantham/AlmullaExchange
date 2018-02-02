package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;

public interface ServiceCodeMasterService  {

	public void saveRecord(ServiceMaster serviceMaster);

	public List<ServiceMaster> getServiceMastersList();

	public List<ServiceMaster> getServiceCheck(String serviceCode);

	public List<ServiceMasterDesc> getServiceCodeCheck(String serviceCode);

	public void save(ServiceMasterDesc serviceMasterDesc);

	public  List<ServiceMasterDesc> LocalServiceDescriptionFromDB(BigDecimal languageId ,BigDecimal serviceId);

	public void updateRecordServiceMaster(ServiceMaster serviceMaster);

	public void updateRecordServiceMasterDesc(ServiceMasterDesc serviceMasterDesc);

	public List<String> getServiceCodeList(String query, BigDecimal serviceGroupId);
	
	public void saveRecordToDB(ServiceMaster serviceMaster);
	
	public void saveToDB(ServiceMasterDesc serviceMasterDesc);

	public List<ServiceMasterDesc> getAllServiceList(BigDecimal serviceGroupId);

	public List<ServiceMasterDesc> getAllServicesForApprove();

	public  String approveReord(BigDecimal servicePk,String userName);

	public List<ServiceMasterDesc> LocalServiceDescription(BigDecimal serviceId);

	public void deleteRecordPermanently(BigDecimal servicePk,BigDecimal serviceEngPk,BigDecimal serviceArbPk);

	public void  activateRecord(BigDecimal servicePk,String userName);

	public List<ServiceGroupMasterDesc> LocalServiceGroupDescription(BigDecimal bigDecimal, BigDecimal serviceGroupId);

	public List<ServiceGroupMasterDesc> getServiceGroupDescription(BigDecimal languageId);
	
	public BigDecimal getServiceIdbyServiceName(String serviceName);

	  public String toFetchServiceGroupName(BigDecimal serviceGroupId, BigDecimal languageId);

	  public List<ServiceMaster> toFetchServiceRecordsServiceMaster(BigDecimal serviceGroup);

}
