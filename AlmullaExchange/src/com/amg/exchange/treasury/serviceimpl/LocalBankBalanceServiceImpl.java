package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.bean.LocalBankBalanceDatatable;
import com.amg.exchange.treasury.dao.ILocalBankBalanceDao;
import com.amg.exchange.treasury.service.ILocalBankBalanceService;

@Service("localBankBalanceService") 
public class LocalBankBalanceServiceImpl implements ILocalBankBalanceService{
	
	@Autowired
	ILocalBankBalanceDao iLocalBankBalanceDao;

	@Transactional
	public List<LocalBankBalanceDatatable> getLocalBankBalance(BigDecimal applicationCountryId, int decimalValue) {
		return iLocalBankBalanceDao.getLocalBankBalance(applicationCountryId, decimalValue);
	}

}
