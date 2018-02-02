package com.amg.exchange.common.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.MarketingData;

public interface IAlamullagroupDao<T> {
	
	public List<MarketingData> getCountryDetails(BigDecimal langId,BigDecimal countryId);

}
