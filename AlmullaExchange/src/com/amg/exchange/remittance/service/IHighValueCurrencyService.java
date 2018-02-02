package com.amg.exchange.remittance.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.HighValueCurrencySetup;
import com.amg.exchange.treasury.model.CurrencyMaster;

public interface IHighValueCurrencyService<T> {

	
	public void save(HighValueCurrencySetup highValueCurrencySetup);
	
	public List<HighValueCurrencySetup> getAllCurrencyList(BigDecimal currencyId);
	
	public List<HighValueCurrencySetup> getEnquiryList();
	
	public String approveReord(BigDecimal highValueId, String userName);
	
	public void delete(BigDecimal highValueId);
	
	public List<CurrencyMaster> getCurrencyList(BigDecimal currencyId);
}
