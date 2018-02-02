package com.amg.exchange.common.dao;

import java.util.List;

import com.amg.exchange.common.model.ComponentType;
import com.amg.exchange.common.model.ComponentTypeDetail;
import com.amg.exchange.common.model.HighValueAMLAuthViewFC;
import com.amg.exchange.common.model.HighValueAMLAuthViewLocal;
import com.amg.exchange.util.AMGException;



/*******************************************************************************************************************

		 File		: IComponentTypeDao.java
 
		 Project	: AlmullaExchangeService

		 Package	: com.amg.exchange.common.dao
 
		 Created	:	
 						Date	: 07-Jul-2014 2:52:36 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 07-Jul-2014 2:52:36 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
public interface IComponentTypeDao<T> {
	
	public List<ComponentType> getComponentTypeList();
	
	public void storeData(List<ComponentTypeDetail> componentTypeList);
	
	public List<HighValueAMLAuthViewLocal> getHighValueAmlAuthRecords();
	
	public List<HighValueAMLAuthViewFC> getHighValueAmlAuthRecordsForFC();
	
	public Boolean saveHighValueAmlAuth(List<HighValueAMLAuthViewLocal> highvalueAmlList, String userName,String remaks)throws AMGException;
	public Boolean saveHighValueAmlAuthForFC(List<HighValueAMLAuthViewFC> highvalueAmlList, String userName,String remaks)throws AMGException;
}
