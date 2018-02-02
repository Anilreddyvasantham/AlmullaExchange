package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.bean.LocalBankBalanceDatatable;

public interface ILocalBankBalanceDao {
	public List<LocalBankBalanceDatatable> getLocalBankBalance(BigDecimal applicationCountryId, int decimalValue);
}
