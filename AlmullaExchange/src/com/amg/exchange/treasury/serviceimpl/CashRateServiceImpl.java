package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.model.CashRate;
import com.amg.exchange.remittance.model.UnApprovalCashRate;
import com.amg.exchange.treasury.dao.ICashRateDao;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.ICashRateService;
@Service
public class CashRateServiceImpl implements ICashRateService {
	@Autowired
	ICashRateDao icashRateDao;

	@Override
	@Transactional
	public List<CashRate> getCashRateRecordsFromDB(
			BigDecimal countryBranchId) {
		 
		return  icashRateDao.getCashRateRecordsFromDB(countryBranchId);
	}
	
	@Override
	@Transactional
	public void save(UnApprovalCashRate unApprovalCashRate){
		 icashRateDao.save(unApprovalCashRate);
		 
	}
	@Override
	@Transactional
	public List<UnApprovalCashRate> getCashRateForApprove(String isActive){
		return  icashRateDao.getCashRateForApprove(isActive);
	}
	@Override
	@Transactional
	public String getQuoteName(BigDecimal currencyId){
		return  icashRateDao.getQuoteName(currencyId);
	}
	@Override
	@Transactional
	public void cashRateSave(CashRate cashRate){
		icashRateDao.cashRateSave(cashRate);
	}
	@Override
	@Transactional
	public void delete(BigDecimal unApprovalCashRateId){
		icashRateDao.delete(unApprovalCashRateId);
	}
	@Override
	@Transactional
	public List<CurrencyMaster> getCurrencyList(BigDecimal localCurrencyId){
		return  icashRateDao. getCurrencyList(localCurrencyId);
	}
	@Override
	@Transactional
	public BigDecimal getBranchDetails(BigDecimal countryBranchId){
		return  icashRateDao.getBranchDetails(countryBranchId);
	}
	@Override
	@Transactional
	public List<UnApprovalCashRate> getAllUnAppCashRate(){
		return  icashRateDao.getAllUnAppCashRate();
	}
	@Override
	@Transactional
	public List<CashRate> isExistAllUnAppCashRate(BigDecimal alternateCurrencyId,BigDecimal baseCurrencyid,BigDecimal appliationCountryId, BigDecimal branchId){
		return  icashRateDao.isExistAllUnAppCashRate(alternateCurrencyId, baseCurrencyid, appliationCountryId, branchId);
	}
	@Override
	@Transactional
	public List<CountryBranch> getCountryBranchList(BigDecimal countryBranchId){
		return  icashRateDao.getCountryBranchList(countryBranchId);
	}
	@Override
	@Transactional
	public String getCurrencyCode(BigDecimal currencyId){
		return  icashRateDao.getCurrencyCode(currencyId);
	}

	@Override
	@Transactional
	public List<UnApprovalCashRate> getUnApprovalCashRateWithoutBranch(
			BigDecimal alternateCurrencyId, BigDecimal baseCurrencyid,
			BigDecimal appliationCountryId, BigDecimal branchId) {
 
		return icashRateDao.getUnApprovalCashRateWithoutBranch(alternateCurrencyId,baseCurrencyid,appliationCountryId,branchId);
	}
}
