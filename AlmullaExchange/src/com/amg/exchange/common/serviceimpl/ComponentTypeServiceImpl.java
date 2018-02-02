package com.amg.exchange.common.serviceimpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.IComponentTypeDao;
import com.amg.exchange.common.model.ComponentType;
import com.amg.exchange.common.model.ComponentTypeDetail;
import com.amg.exchange.common.model.HighValueAMLAuthViewFC;
import com.amg.exchange.common.model.HighValueAMLAuthViewLocal;
import com.amg.exchange.common.service.IComponentTypeService;
import com.amg.exchange.util.AMGException;

@SuppressWarnings("serial")
@Service("componentTypeServiceImpl")
public class ComponentTypeServiceImpl <T> implements IComponentTypeService<T>, Serializable {

	@Autowired
	IComponentTypeDao<T> componentTypeDao;
	
	@Override
	@Transactional
	public List<ComponentType> getComponentTypeList() {
		
		return componentTypeDao.getComponentTypeList();
	}

	@Override
	@Transactional
	public void storeData(List<ComponentTypeDetail> componentTypeList) {
		
		componentTypeDao.storeData(componentTypeList);
	}
	
	@Override
	@Transactional
	public List<HighValueAMLAuthViewLocal> getHighValueAmlAuthRecords(){
		return componentTypeDao.getHighValueAmlAuthRecords();
	}
	
	@Override
	@Transactional
	public List<HighValueAMLAuthViewFC> getHighValueAmlAuthRecordsForFC(){
		return componentTypeDao.getHighValueAmlAuthRecordsForFC();
	} 
	
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Boolean saveHighValueAmlAuth(List<HighValueAMLAuthViewLocal> highvalueAmlList, String userName,String remaks)throws AMGException{
		return componentTypeDao.saveHighValueAmlAuth(highvalueAmlList, userName, remaks);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public Boolean saveHighValueAmlAuthForFC(List<HighValueAMLAuthViewFC> highvalueAmlList, String userName,String remaks)throws AMGException{
		return componentTypeDao.saveHighValueAmlAuthForFC(highvalueAmlList, userName, remaks);
	}
	
		 
}
