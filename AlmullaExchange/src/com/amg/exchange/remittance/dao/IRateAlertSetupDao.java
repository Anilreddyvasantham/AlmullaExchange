package com.amg.exchange.remittance.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.model.OnlineRateAlert;
import com.amg.exchange.model.RateAlertFrequency;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.ExchangeRate;

public interface IRateAlertSetupDao {

	public BigDecimal getExchangeRatefortheCountry(BigDecimal appcountryId, BigDecimal currencyId) ;

	public void save(OnlineRateAlert onlineRateAlert);

	public List<RateAlertFrequency> getHowOftenList();
	
	
	public List<CurrencyMaster> getCurrencyList();

	public ExchangeRate getExchangeRate(BigDecimal appcountryId, BigDecimal currencyId);

	public OnlineRateAlert isExists(BigDecimal customerId, BigDecimal currencyId, String  frequncy);
}