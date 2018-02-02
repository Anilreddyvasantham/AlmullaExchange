package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.dao.IHighValueCurrencyDao;
import com.amg.exchange.remittance.model.HighValueCurrencySetup;
import com.amg.exchange.remittance.service.IHighValueCurrencyService;
import com.amg.exchange.treasury.model.CurrencyMaster;

@SuppressWarnings("serial")
@Service("highValueCurrencyServiceImpl")
public class HighValueCurrencyServiceImpl<T> implements IHighValueCurrencyService<T> {
	
	@Autowired
	IHighValueCurrencyDao<T> highValueCurrencyDao;
	
	

	public IHighValueCurrencyDao<T> getHighValueCurrencyDao() {
		return highValueCurrencyDao;
	}

	public void setHighValueCurrencyDao(IHighValueCurrencyDao<T> highValueCurrencyDao) {
		this.highValueCurrencyDao = highValueCurrencyDao;
	}

	public HighValueCurrencyServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public void save(HighValueCurrencySetup highValueCurrencySetup) {
		getHighValueCurrencyDao().save(highValueCurrencySetup);

	}
	@Transactional
	@Override
	public List<HighValueCurrencySetup> getAllCurrencyList(BigDecimal currencyId){
		return getHighValueCurrencyDao().getAllCurrencyList(currencyId);
	}
	@Transactional
	@Override
	public List<HighValueCurrencySetup> getEnquiryList(){
		return getHighValueCurrencyDao().getEnquiryList();
	}
	@Transactional
	@Override
	public String approveReord(BigDecimal highValueId, String userName){
		return getHighValueCurrencyDao().approveReord(highValueId, userName);
	}
	@Transactional
	@Override
	public void delete(BigDecimal highValueId){
		getHighValueCurrencyDao().delete(highValueId);
		
	}
	@Transactional
	@Override
	public List<CurrencyMaster> getCurrencyList(BigDecimal currencyId) {
		// TODO Auto-generated method stub
		return getHighValueCurrencyDao().getCurrencyList(currencyId);
	}
}
