package com.amg.exchange.remittance.dao;


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

public interface ICorporateRemittanceDao {

	
	public CustomerIdProof getCustomerList(String identityType);
	
	
	
	public List<BeneficaryStatus> getBeneficaryStatusList();
	
	public List<CustomerIdProof> getCustomerDetails(String customerId);
	public List<Relations> getRelationsList();
	
	public List<CurrencyMaster> getCurrencyList(BigDecimal countryId);
	public List<BankMaster> getbanklist(BigDecimal currencyId);
	
	public List<BankBranch> getBankbranchlist(BigDecimal bankId);
	
	public List<CountryMasterDesc> getCountryList();
	public List<CustomerIdProof> checkForCorporateCustomer(BigDecimal customerId);
	
	public List<Customer> getCorporateCustomerDetails(String crno);
	
	public List<BeneficaryMaster> getCorporateBenificiaryList(BigDecimal customerId);



	public List<CustomerIdProof> getRegisterId(String crno, BigDecimal countryId);
		
}
