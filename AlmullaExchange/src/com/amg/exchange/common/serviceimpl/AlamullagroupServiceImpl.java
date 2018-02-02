
package com.amg.exchange.common.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.IAlamullagroupDao;
import com.amg.exchange.common.model.MarketingData;
import com.amg.exchange.common.service.IAlmullagroupService;


@Service
public class AlamullagroupServiceImpl<T> implements IAlmullagroupService<T>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	IAlamullagroupDao<T> iAlamullagroupDao;

	@Transactional
	@Override
	public List<MarketingData> getCountryList(BigDecimal langId,BigDecimal countryId) {
		// TODO Auto-generated method stub
		return iAlamullagroupDao.getCountryDetails(langId,countryId);
	}
	
	 

}
