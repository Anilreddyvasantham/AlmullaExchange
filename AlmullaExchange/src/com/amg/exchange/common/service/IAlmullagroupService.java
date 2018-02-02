package com.amg.exchange.common.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.MarketingData;

public interface IAlmullagroupService<T> {
	
	public List<MarketingData> getCountryList(BigDecimal langId,BigDecimal countryId);

}
