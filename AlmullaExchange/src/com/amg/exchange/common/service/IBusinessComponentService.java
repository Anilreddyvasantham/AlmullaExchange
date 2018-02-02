package com.amg.exchange.common.service;

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

		 File		: IBusinessComponentService.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.service
 
		 Created	:	
 						Date	: 30-May-2014 1:07:05 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 30-May-2014 1:07:05 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
public interface IBusinessComponentService<T>  extends IMutualService<T>{
	
	public List<ComponentType> getComponentType();
	public void saveOrUpdate(T entity);
	public List<BusinessComponent> getAllBusinessComponent();
	public BizComponentConfDetail getBusinessComponent(String componentName);
	public List<BizComponentDataDesc> getBusinessComponentDropDownData(BigDecimal componentConfId, BigDecimal languageId);
	public List<String> getAllBusinessComponent(String query);
	public Map<BigDecimal, BizComponentDataRef> getAllComponentDataRef(BigDecimal componentConfId);
	public List<BizComponentData> getBusinessComponentData(BigDecimal componentConfId);
}
