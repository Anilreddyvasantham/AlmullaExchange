package com.amg.exchange.stock.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.dto.CurrencyMasterDTO;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.registration.model.Employee;

public interface IStockAdjustmentService<T> {

	public List<Employee> getAllStaffList(BigDecimal branchId);
	
	public List<CurrencyMasterDTO> getCurrencyFromStock(BigDecimal countryBranchId,String userName);
	
	public BigDecimal getBalance(BigDecimal countryBranchId,String userName,BigDecimal currencyId);
	
	public List<CurrencyWiseDenomination> currencyWiseDenominations(BigDecimal currencyId);
	
	public void saveForeignCurrencyAdjustRecords(List<ForeignCurrencyAdjust> foreignCurrencyAdjList)throws Exception;
}
