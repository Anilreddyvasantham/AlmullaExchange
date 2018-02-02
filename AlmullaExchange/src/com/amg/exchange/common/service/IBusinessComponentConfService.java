package com.amg.exchange.common.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.amg.exchange.common.model.BizComponentConfDetail;
import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.BizComponentDataRef;



/*******************************************************************************************************************

		 File		: IBusinessComponentConfService.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.service
 
		 Created	:	
 						Date	: 30-May-2014 9:22:34 am
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 30-May-2014 9:22:34 am
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
public interface IBusinessComponentConfService<T>  extends IMutualService<T>{
	
	public BizComponentConfDetail getBusinessComponent(BigDecimal componentId);
	public BizComponentConfDetail getBusinessComponent(BigDecimal componentId, BigDecimal applicationId, BigDecimal companyId, BigDecimal countryId, BigDecimal pageId);
	public List<BizComponentDataDesc> getBusinessComponentDropDownData(BigDecimal componentConfId, BigDecimal languageId);
	public List<BizComponentDataRef> getBusinessComponentDataRef(BigDecimal componentConfId, BigDecimal languageId);
	public void saveOrUpdate(T entity);
	public void delete(T entity);
	public List<Object[]> getBusinessComponentTree(BigDecimal componentId);
	public Map<String, BigDecimal> getBusinessComponentData(BigDecimal componentId, BigDecimal languageId);
}
