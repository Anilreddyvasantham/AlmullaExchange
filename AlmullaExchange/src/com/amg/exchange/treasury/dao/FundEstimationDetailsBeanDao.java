package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.treasury.model.FundEstimationDays;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.util.AMGException;

public interface FundEstimationDetailsBeanDao {
	
	public List<FundEstimationDetails> getData(BigDecimal countryId);
	public String getCountryName(BigDecimal countryId);
	public int getDecimalValue(BigDecimal currencyId);
	
	public BigDecimal getUsdPk(String currencyCode);
	
	//Call procedure for fund estimation calculation
	public HashMap<String,String> callFundEstimation(HashMap<String,String> inputValues) throws AMGException;
	
	public List<FundEstimationDays> getFundEstimationDyas(HashMap<String, String> inputValues) throws AMGException;
}
