package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.treasury.dao.IBankApplicabilityDao;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankIndicatorDescription;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IBankApplicabilityService;
import com.amg.exchange.util.AMGException;
@SuppressWarnings("serial")
@Service("bankApplicabilityServiceImpl")
public class BankApplicabilityServiceImpl<T> implements IBankApplicabilityService<T>, Serializable {
	@Autowired
	IBankApplicabilityDao<T> bankApplicabilityDao;
	
	public IBankApplicabilityDao<T> getBankApplicabilityDao() {
		return bankApplicabilityDao;
	}

	public void setBankApplicabilityDao(
			IBankApplicabilityDao<T> bankApplicabilityDao) {
		this.bankApplicabilityDao = bankApplicabilityDao;
	}

    @Transactional
	@Override
	public void saveBankApplicabilityDetails(BankApplicability bankApplicability) {
		getBankApplicabilityDao().saveBankApplicabilityDetails(bankApplicability);
		
	}
    
    @Transactional
	public List<BankIndicator> getBankIndicatorList() {
		return getBankApplicabilityDao().getBankIndicatorList();
	}
    @Transactional
	@Override
	public List<BankApplicability> getBankApplicability(BigDecimal companyId,
			BigDecimal countryId, BigDecimal bankTypeId, BigDecimal bankId) {
		
		return getBankApplicabilityDao().getBankApplicability(companyId, countryId, bankTypeId, bankId);
	}

	@Override
	@Transactional
	public List<BankMaster> getlist(BigDecimal bankid) {
		// TODO Auto-generated method stub
		return getBankApplicabilityDao().getBanklist(bankid);
	}

	@Override
	@Transactional
	public List<BankApplicability> getBankApplicabilityForView(
			BigDecimal companyId, BigDecimal countryId, BigDecimal bankId) {
		
		return getBankApplicabilityDao().getBankApplicabilityForView(companyId, countryId, bankId);
	}

	@Override
	@Transactional
	public List<CountryMasterDesc> getCountryFromCompany(BigDecimal companyId,BigDecimal languageId) {
		
		return getBankApplicabilityDao().getCountryFromCompany(companyId, languageId);
	}

	@Override
	@Transactional
	public List<BankApplicability> getBankApplicabilityApproval(
			BigDecimal bankApplicabilityId) {
		
		return getBankApplicabilityDao().getBankApplicabilityApproval(bankApplicabilityId);
	}

	@Override
	@Transactional
	public void activeDeactiveRecord(BigDecimal bankApplicabilityID, String status ,String remarks) {
	
		getBankApplicabilityDao().activeDeactiveRecord(bankApplicabilityID, status,remarks);
	}

	@Override
	@Transactional
	public List<BankIndicatorDescription> getBankIndicatorDescList(BigDecimal languageId) {
		 
		return getBankApplicabilityDao().getBankIndicatorDescList(languageId);
	}

	@Override
	@Transactional
	public List<BigDecimal> getCorrespondingCountryList(BigDecimal appCountryId,String cB) {
		return getBankApplicabilityDao().getCorrespondingCountryList(appCountryId,cB);
	}

	@Override
	 @Transactional
	public List<BankApplicability> getBankList( BigDecimal appcountryId,String bankIndicatorCorrBank, BigDecimal countryId) {
		return getBankApplicabilityDao().getBankList(appcountryId,bankIndicatorCorrBank,countryId);
	}

	@Override
	 @Transactional
	public List<BankApplicability> getApplicabilityBankList(BigDecimal countryId, String bankIndicatorCorrBank) {
		 return getBankApplicabilityDao().getApplicabilityBankList(countryId,bankIndicatorCorrBank);
	}

	@Override
	@Transactional
	public List<BankApplicability> getApplicabilityBankingChennalBankList(BigDecimal countryId, String bankIndicatorAgentBank) {
		return getBankApplicabilityDao().getApplicabilityBankingChennalBankList(countryId, bankIndicatorAgentBank);
	}

	@Override
	@Transactional
	public HashMap<String, String> callPopulateBankApplicability(
			HashMap<String, String> inputValues) throws AMGException {
  
		return getBankApplicabilityDao().callPopulateBankApplicability(inputValues);
	}

}
