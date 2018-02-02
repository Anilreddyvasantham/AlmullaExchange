package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.model.OnlineRateAlert;
import com.amg.exchange.model.RateAlertFrequency;
import com.amg.exchange.remittance.dao.IRateAlertSetupDao;
import com.amg.exchange.remittance.service.IRateAlertSetupService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.ExchangeRate;


/**
 * @author Mohan
 *
 * @param <T>
 */
@Service("rateAlertSetupServiceImpl")
public class RateAlertSetupServiceImpl<T> extends CommonDaoImpl<T> implements IRateAlertSetupService<T>{
	
	@Autowired
	IRateAlertSetupDao iAlertRateSetupDao;

	public IRateAlertSetupDao getiAlertRateSetupDao() {
		return iAlertRateSetupDao;
	}

	public void setiAlertRateSetupDao(IRateAlertSetupDao iAlertRateSetupDao) {
		this.iAlertRateSetupDao = iAlertRateSetupDao;
	}

	@Override
	@Transactional
	public BigDecimal getExchangeRatefortheCountry(BigDecimal appcountryId, BigDecimal currencyId) {
		return getiAlertRateSetupDao().getExchangeRatefortheCountry( appcountryId,currencyId);
	}

	@Override
	@Transactional
	public void save(OnlineRateAlert onlineRateAlert) {
		 getiAlertRateSetupDao().save(onlineRateAlert);
		
	}

	@Override
	@Transactional
	public List<RateAlertFrequency> getHowOftenList() {
		return getiAlertRateSetupDao().getHowOftenList();
	}

	@Override
	@Transactional
	public List<CurrencyMaster> getCurrencyList() {
		return getiAlertRateSetupDao().getCurrencyList();
	}

	@Override
	@Transactional
	public ExchangeRate getExchangeRate(BigDecimal appcountryId, BigDecimal currencyId) {
		return getiAlertRateSetupDao().getExchangeRate( appcountryId,currencyId);
	}

	@Override
	@Transactional
	public OnlineRateAlert isExists(BigDecimal customerId, BigDecimal currencyId, String frequnecy) {
		
		return getiAlertRateSetupDao().isExists( customerId,  currencyId,  frequnecy);
	}


	
	}

	

