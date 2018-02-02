package com.amg.exchange.stock.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dto.CurrencyMasterDTO;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.stock.dao.IStockAdjustmentDao;
import com.amg.exchange.stock.service.IStockAdjustmentService;

@Service("stockAdjustmentServiceImpl")
public class StockAdjustmentServiceImpl<T> implements Serializable ,IStockAdjustmentService<T> 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	IStockAdjustmentDao<T> iStockAdjustmentDao;
	
	@Override
	@Transactional
	public List<Employee> getAllStaffList(BigDecimal branchId) {
		
		return iStockAdjustmentDao.getAllStaffList(branchId);
	}

	@Override
	@Transactional
	public List<CurrencyMasterDTO> getCurrencyFromStock(
			BigDecimal countryBranchId, String userName) {
		
		return iStockAdjustmentDao.getCurrencyFromStock(countryBranchId, userName);
	}

	@Override
	@Transactional
	public BigDecimal getBalance(BigDecimal countryBranchId, String userName,
			BigDecimal currencyId) {
		
		return iStockAdjustmentDao.getBalance(countryBranchId, userName, currencyId);
	}

	@Override
	@Transactional
	public List<CurrencyWiseDenomination> currencyWiseDenominations(BigDecimal currencyId){
		
		return iStockAdjustmentDao.currencyWiseDenominations(currencyId);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveForeignCurrencyAdjustRecords(List<ForeignCurrencyAdjust> foreignCurrencyAdjList) throws Exception{
		
		 iStockAdjustmentDao.saveForeignCurrencyAdjustRecords(foreignCurrencyAdjList);
	}
}
