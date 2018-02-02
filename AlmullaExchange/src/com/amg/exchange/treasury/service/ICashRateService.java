package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.model.CashRate;
import com.amg.exchange.remittance.model.UnApprovalCashRate;
import com.amg.exchange.treasury.model.CurrencyMaster;

public interface ICashRateService {
	
	public List<CashRate> getCashRateRecordsFromDB(BigDecimal countryBranchId);
	
	public void save(UnApprovalCashRate unApprovalCashRate);
	
	public List<UnApprovalCashRate> getCashRateForApprove(String isActive);
	
	public String getQuoteName(BigDecimal currencyId);
	
	public void cashRateSave(CashRate cashRate);
	
	public void delete(BigDecimal unApprovalCashRateId);
	
	public List<CurrencyMaster> getCurrencyList(BigDecimal localCurrencyId);
	
	public BigDecimal getBranchDetails(BigDecimal countryBranchId);
	
	public List<UnApprovalCashRate> getAllUnAppCashRate();
	
	public List<CashRate> isExistAllUnAppCashRate(BigDecimal alternateCurrencyId,BigDecimal baseCurrencyid,BigDecimal appliationCountryId, BigDecimal branchId);
	
	public List<CountryBranch> getCountryBranchList(BigDecimal countryBranchId);
	
	public String getCurrencyCode(BigDecimal currencyId);
	public List<UnApprovalCashRate> getUnApprovalCashRateWithoutBranch(BigDecimal alternateCurrencyId,BigDecimal baseCurrencyid,BigDecimal appliationCountryId, BigDecimal branchId);

}
