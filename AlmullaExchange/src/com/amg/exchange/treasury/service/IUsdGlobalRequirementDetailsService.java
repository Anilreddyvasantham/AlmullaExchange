package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.bean.BankCountryPopulationBean;
import com.amg.exchange.treasury.bean.UsdGlobalRequirementDetails;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.FundEstimationDetails;

public interface IUsdGlobalRequirementDetailsService {
	
	public List<BankAccountDetails> getCurrencyOfBank(BigDecimal bankId);
	public List<BankMaster> getBankAccordingToBankCountry(BigDecimal applicationCountryId, BigDecimal countryId);
	public List<FundEstimationDetails> search(BigDecimal bankId, BigDecimal currencyId, BigDecimal bankCountry, BigDecimal detailedId);
	public String getCountryName(BigDecimal countryId);
	public List<BankCountryPopulationBean> getBankContry();
	public String getBankName(BigDecimal countryId);
	
}
