package com.amg.exchange.registration.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.IncomeRangeValue;

public interface IIncomeRangeValueService {
	
	public void save(IncomeRangeValue incomeRangeValue);
	
	public List<IncomeRangeValue> getIncomeRangeValueList();
	
	public void delete(BigDecimal incomeRangeValueId) ;
	
	public void activateRecord(BigDecimal incomeRangeValueId, String userName);
	
	public boolean isExistIncomeRange(BigDecimal appCountryId,BigDecimal fromAmount, BigDecimal toAmount);
	
	public List<IncomeRangeValue> getIncomeFRangeValueListforDetail(BigDecimal countryId);

}
