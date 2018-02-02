package com.amg.exchange.foreigncurrency.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.foreigncurrency.dao.ForeignLocalCurrencyDenominationDao;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;

@Service("ForeignLocalCurrencyDenominationService")
public class ForeignLocalCurrencyDenominationServiceImpl<T> implements ForeignLocalCurrencyDenominationService<T>{
	
	@Autowired
	ForeignLocalCurrencyDenominationDao<T> foreignLocalCurrencyDenominationDao;
	
	@Override
	@Transactional
	public List<CurrencyWiseDenomination> getLocalCurrencyDenomination(BigDecimal countryId, String userName, String branchId, BigDecimal companyId, String currencyId) {
		return foreignLocalCurrencyDenominationDao.getLocalCurrencyDenomination(countryId, userName, branchId, companyId, currencyId);
	}

	@Override
	@Transactional
	public void updateStock(BigDecimal pk, int closingQuantity, String userName) {
		foreignLocalCurrencyDenominationDao.updateStock(pk, closingQuantity, userName);
	}

	@Override
	@Transactional
	public void updateRefund(BigDecimal pk, String refundedCash, String userName) {
		foreignLocalCurrencyDenominationDao.updateRefund(pk, refundedCash, userName);
	}

	@Override
	@Transactional
	public int getDecimalPerCountry(BigDecimal countryId) {
		return foreignLocalCurrencyDenominationDao.getDecimalPerCountry(countryId);
	}

	@Override
	@Transactional
	public int getDecimalPerCurrency(BigDecimal courencyId) {
		return foreignLocalCurrencyDenominationDao.getDecimalPerCurrency(courencyId);
	}

	@Override
	@Transactional
	public List<Stock> getLocalCurrencyDenominationFromStock(
			BigDecimal countryId, String userName, String countryBranchId,
			BigDecimal companyId, String currecnId) {
		return foreignLocalCurrencyDenominationDao.getLocalCurrencyDenominationFromStock(countryId, userName, countryBranchId, companyId, currecnId);
	}

	@Override
	@Transactional
	public List<Stock> getCurrencyDenominationFromStock(BigDecimal countryId,
			String userName, String countryBranchId, BigDecimal companyId,
			String currecnId) {
		return foreignLocalCurrencyDenominationDao.getCurrencyDenominationFromStock(countryId, userName, countryBranchId, companyId, currecnId);
	}

	@Override
	@Transactional
	public int getDecimalPerCurrencyBasedOnQuoteName(String quoteName) {
	 
		return foreignLocalCurrencyDenominationDao.getDecimalPerCurrencyBasedOnQuoteName(quoteName);
	}
@Override
@Transactional
public List<CurrencyWiseDenomination> getLocalCurrencyDenominationStockWithoutUser(BigDecimal countryId, String currecnId) {
	return foreignLocalCurrencyDenominationDao.getLocalCurrencyDenominationStockWithoutUser(countryId, currecnId);
}
}
