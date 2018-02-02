package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.remittance.dao.ICorporateRemittanceDao;
import com.amg.exchange.remittance.dao.IPersonalRemittanceDao;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryStatus;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.service.ICorporateRemittanceService;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;

@Service("corporateRemittanceService")
public class CorporateRemittanceServiceImpl implements 	ICorporateRemittanceService { 	
		
	@Autowired
	IPersonalRemittanceDao personalRemittanceDao;
	
	@Autowired
	ICorporateRemittanceDao corporateRemittanceDao;
	
	@Transactional
	@Override
	public CustomerIdProof getCustomerList(String identitytype) {
		// TODO Auto-generated method stub
		return personalRemittanceDao.getCustomerList(identitytype);
	}
	
	@Override
	@Transactional
	public List<BeneficaryStatus> getBeneficaryStatusList(
			) {

		return personalRemittanceDao.getBeneficaryStatusList();
	}

	@Override
	@Transactional
	public List<CustomerIdProof> getCustomerDetails(String customerId) {

		return corporateRemittanceDao.getCustomerDetails(customerId);
	}

	@Override
	@Transactional
	public List<Relations> getRelationsList() {
		return personalRemittanceDao.getRelationsList();
	}

	@Override
	@Transactional
	public List<BankMaster> getbanklist(BigDecimal currencyId) {
		// TODO Auto-generated method stub
		return personalRemittanceDao.getbanklist(currencyId);
	}

	@Override
	@Transactional
	public List<BankBranch> getBankbranchlist(BigDecimal bankId) {
		// TODO Auto-generated method stub
		return personalRemittanceDao.getBankbranchlist(bankId);
	}

	@Override
	@Transactional
	public List<CountryMasterDesc> getCountryList() {
		// TODO Auto-generated method stub
		return corporateRemittanceDao.getCountryList();
	}

	@Override
	@Transactional
	public List<CurrencyMaster> getCurrencyList(BigDecimal countryId) {
		// TODO Auto-generated method stub
		return personalRemittanceDao.getCurrencyList(countryId);
	}

	@Override
	@Transactional
	public List<CustomerIdProof> checkForCorporateCustomer(BigDecimal customerId) {
		return corporateRemittanceDao.checkForCorporateCustomer(customerId);
	}

	@Override
	@Transactional
	public List<Customer> getCorporateCustomerDetails(String crno) {

		return corporateRemittanceDao.getCorporateCustomerDetails(crno);
	}

	@Override
	@Transactional
	public List<BeneficaryMaster> getCorporateBenificiaryList(BigDecimal customerId) {
		return corporateRemittanceDao.getCorporateBenificiaryList(customerId);
	}

	@Override
	@Transactional
	public List<CustomerIdProof> getRegisterId(String crno, BigDecimal countryId) {
		return corporateRemittanceDao.getRegisterId(crno,countryId);
	}


}


