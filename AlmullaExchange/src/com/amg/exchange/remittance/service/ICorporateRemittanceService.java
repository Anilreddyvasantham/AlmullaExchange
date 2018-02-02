package com.amg.exchange.remittance.service;
import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryStatus;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;

public interface ICorporateRemittanceService {
	
	public CustomerIdProof getCustomerList(String identitytype);
	public List<BeneficaryStatus> getBeneficaryStatusList();

	public List<CustomerIdProof> getCustomerDetails(String customerId);
	public List<Relations> getRelationsList();
	
	//Ramakrishna code
	
	public List<CurrencyMaster> getCurrencyList(BigDecimal countryId);
	public List<BankMaster> getbanklist(BigDecimal currencyId);
	
	public List<BankBranch> getBankbranchlist(BigDecimal bankId);
	
	public List<CountryMasterDesc> getCountryList();
	public List<CustomerIdProof> checkForCorporateCustomer(BigDecimal customerId);
	//added by nazish for corporate details
	public List<Customer> getCorporateCustomerDetails(String crno);
	public List<BeneficaryMaster> getCorporateBenificiaryList(BigDecimal customerId);
	public List<CustomerIdProof> getRegisterId(String string, BigDecimal countryId);
}
