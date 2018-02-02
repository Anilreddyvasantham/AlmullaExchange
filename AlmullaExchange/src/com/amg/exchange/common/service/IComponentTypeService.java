package com.amg.exchange.common.service;

import java.util.List;

import com.amg.exchange.common.model.ComponentType;
import com.amg.exchange.common.model.ComponentTypeDetail;
import com.amg.exchange.common.model.HighValueAMLAuthViewFC;
import com.amg.exchange.common.model.HighValueAMLAuthViewLocal;
import com.amg.exchange.util.AMGException;



public interface IComponentTypeService<T>{
	
	public List<ComponentType> getComponentTypeList();
	
	public void storeData(List<ComponentTypeDetail> componentTypeList);
	
	public List<HighValueAMLAuthViewLocal> getHighValueAmlAuthRecords();
	
	public List<HighValueAMLAuthViewFC> getHighValueAmlAuthRecordsForFC();
	
	public Boolean saveHighValueAmlAuth(List<HighValueAMLAuthViewLocal> highvalueAmlList, String userName,String remaks)throws AMGException;
	public Boolean saveHighValueAmlAuthForFC(List<HighValueAMLAuthViewFC> highvalueAmlList, String userName,String remaks)throws AMGException;
}
