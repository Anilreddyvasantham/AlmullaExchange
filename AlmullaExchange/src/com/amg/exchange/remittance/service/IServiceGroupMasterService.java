package com.amg.exchange.remittance.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.ServiceGroupMaster;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;

public interface IServiceGroupMasterService {

	  public void save(ServiceGroupMaster serviceGroupMaster);

	  public void save(ServiceGroupMasterDesc serviceGroupMasterDesc);

	  public Boolean isExistServiceGroupCode(String serviceGroupCode);

	  public List<ServiceGroupMaster> getServiceGroupMasterList();

	  public List<ServiceGroupMasterDesc> getServiceGroupDescList(BigDecimal serviceGroupId);

	  public void delete(BigDecimal serviceGroupId, BigDecimal serviceGroupEngDescId, BigDecimal serviceGroupLocalDescId);

	  public void activateRecord(BigDecimal serviceGroupId, String userName);

	  public List<String> toFetchServiceGroupCodeList(String query);

	  public List<ServiceGroupMaster> toServiceGroupCodeAllValues(String serviceGroupCode);

	  public void upDateActiveRecordToDeAtivate(BigDecimal serviceGroupId, String userName, String remarks);

	  public void saveOrUpdate(ServiceGroupMaster serviceGroupMaster, ServiceGroupMasterDesc serviceGroupMasterDesc, ServiceGroupMasterDesc serviceGroupMasterDesc1);

}
