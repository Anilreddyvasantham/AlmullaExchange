package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.bean.LocalBankBalanceDatatable;

public interface ILocalBankBalanceService {
	public List<LocalBankBalanceDatatable> getLocalBankBalance(BigDecimal applicationCountryId, int decimalValue);
}
