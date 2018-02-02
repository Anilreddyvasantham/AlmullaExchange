package com.amg.exchange.foreigncurrency.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.Stock;

public interface ForeignLocalCurrencyDenominationService<T> {
	
	public List<CurrencyWiseDenomination> getLocalCurrencyDenomination(BigDecimal countryId, String userName, String branchId, BigDecimal companyId, String currencyId);
	
	public void updateStock(BigDecimal pk, int closingQuantity, String userName);
	
	public void updateRefund(BigDecimal pk, String refundedCash, String userName);
	
	public int getDecimalPerCountry(BigDecimal countryId);
	
	public int getDecimalPerCurrency(BigDecimal courencyId);
	public int getDecimalPerCurrencyBasedOnQuoteName(String  quoteName);
	public List<Stock> getLocalCurrencyDenominationFromStock(BigDecimal countryId, String userName, String countryBranchId, BigDecimal companyId, String currecnId);
	
	public List<Stock> getCurrencyDenominationFromStock(BigDecimal countryId, String userName, String countryBranchId, BigDecimal companyId, String currecnId);
	
	public List<CurrencyWiseDenomination> getLocalCurrencyDenominationStockWithoutUser(BigDecimal countryId, String currecnId);
	
}
