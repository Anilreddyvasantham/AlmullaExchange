package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.bean.BankCountryPopulationBean;
import com.amg.exchange.treasury.bean.UsdGlobalRequirementDetails;
import com.amg.exchange.treasury.dao.IUsdGlobalRequirementDetailsDao;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.treasury.service.IUsdGlobalRequirementDetailsService;

@Service("UsdGlobalRequirementDetailsService")
public class IUsdGlobalRequirementDetailsServiceImpl  implements IUsdGlobalRequirementDetailsService{
	
	@Autowired
	IUsdGlobalRequirementDetailsDao iUsdGlobalRequirementDetailsDao;
	
	@Override
	@Transactional
	public List<BankAccountDetails> getCurrencyOfBank(BigDecimal bankId) {
		return iUsdGlobalRequirementDetailsDao.getCurrencyOfBank(bankId);
	}

	@Override
	@Transactional
	public List<BankMaster> getBankAccordingToBankCountry(BigDecimal applicationCountryId, BigDecimal countryId) {
		return iUsdGlobalRequirementDetailsDao.getBankAccordingToBankCountry(applicationCountryId, countryId);
	}

	@Override
	@Transactional
	public List<FundEstimationDetails> search(BigDecimal bankId, BigDecimal currencyId, BigDecimal bankCountry, BigDecimal detailId) {
		return iUsdGlobalRequirementDetailsDao.search(bankId, currencyId, bankCountry,detailId);
	}

	@Override
	@Transactional
	public String getCountryName(BigDecimal countryId) {
		return iUsdGlobalRequirementDetailsDao.getCountryName(countryId);
	}

	@Override
	@Transactional
	public List<BankCountryPopulationBean> getBankContry() {
		return iUsdGlobalRequirementDetailsDao.getBankContry();
	}

	@Override
	@Transactional
	public String getBankName(BigDecimal countryId) {
		return iUsdGlobalRequirementDetailsDao.getBankName(countryId);
	}

}
