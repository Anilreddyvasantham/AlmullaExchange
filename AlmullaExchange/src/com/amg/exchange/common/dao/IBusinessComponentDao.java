package com.amg.exchange.common.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.amg.exchange.common.model.BizComponentConfDetail;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.BizComponentDataRef;
import com.amg.exchange.common.model.BusinessComponent;
import com.amg.exchange.common.model.ComponentType;

/*******************************************************************************************************************

		 File		: IBusinessComponentDao.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.dao
 
		 Created	:	
 						Date	: 30-May-2014 9:21:57 am
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 30-May-2014 9:21:57 am
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
public interface IBusinessComponentDao<T> {
	
	public List<ComponentType> getComponentType();
	public void saveOrUpdate(T entity);
	public List<BusinessComponent> getAllBusinessComponent();
	public BizComponentConfDetail getBusinessComponent(String componentName);
	public List<BizComponentDataDesc> getBusinessComponentDropDownData(BigDecimal componentConfId, BigDecimal languageId);
	public List<String> getAllBusinessComponent(String query);
	public Map<BigDecimal, BizComponentDataRef> getAllComponentDataRef(BigDecimal componentConfId);
	public List<BizComponentData> getBusinessComponentData(BigDecimal componentConfId);
}
