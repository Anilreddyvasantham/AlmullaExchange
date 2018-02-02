package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.CurrencyMaster;

public interface IFXDealSupplierDao<T> {
	
	public List<CompanyMasterDesc> getCompanyList(BigDecimal languageId);
	public List<CurrencyMaster> getCurrencyList();
	
	//to add koti
	public List<Customer> getAllCustomerList(BigDecimal customerReference);

	public List<Customer> getAllComponentOreder(String query);
          public String getAllCustomerName(BigDecimal dealWithCustomer);
	
}
