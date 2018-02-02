package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;

public interface IServiceCodeMasterDao {

	public List<ServiceMaster> getServiceMasterList();

	public void saveRecord(ServiceMaster serviceMaster);

	public void saveRecordToDB(ServiceMaster serviceMaster);

	public void saveToDB(ServiceMasterDesc serviceMasterDesc);

	public List<ServiceMaster> getRemittanceCheck(String serviceCode);

	public void save(ServiceMasterDesc serviceMasterDesc);

	public  List<ServiceMasterDesc> LocalServiceDescriptionFromDB(BigDecimal languageId ,BigDecimal serviceId);

	public void updateRecordServiceMaster(ServiceMaster serviceMaster);

	public void updateRecordServiceMasterDesc(ServiceMasterDesc serviceMasterDesc);

	public List<String> getServiceCodeList(String query, BigDecimal serviceGroupId);

	public List<ServiceMasterDesc> getAllServiceList(BigDecimal serviceGroupId);

	public List<ServiceMasterDesc> getAllServicesForApprove();

	public  String approveReord(BigDecimal servicePk,String userName);

	public List<ServiceMasterDesc> LocalServiceDescription(BigDecimal serviceId);

	public void deleteRecordPermanently(BigDecimal servicePk,BigDecimal serviceEngPk,BigDecimal serviceArbPk);

	public List<ServiceMasterDesc> getServiceCodeCheck(String serviceCode) ;

	public void  activateRecord(BigDecimal servicePk,String userName);

	public List<ServiceGroupMasterDesc> LocalServiceGroupDescription(BigDecimal languageId, BigDecimal serviceGroupId);

	public List<ServiceGroupMasterDesc> getServiceGroupDescription(BigDecimal languageId);
	
	public BigDecimal getServiceIdbyServiceName(String serviceName);

	  public String toFetchServiceGroupName(BigDecimal serviceGroupId, BigDecimal languageId);

	  public List<ServiceMaster> toFetchServiceRecordsServiceMaster(BigDecimal serviceGroup);
}
