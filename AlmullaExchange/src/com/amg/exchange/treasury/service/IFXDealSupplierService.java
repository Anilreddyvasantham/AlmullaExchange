package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.CurrencyMaster;

public interface IFXDealSupplierService<T> {

	public List<CompanyMasterDesc> getCompanyList(BigDecimal languageId);
	public List<CurrencyMaster> getCurrencyList();
	
	// To get AutoCompleteList

	public List<Customer> getAllCustomerList(BigDecimal customerReference);

	public List<Customer> getAllComponentOreder(String query);
         
	public String getAllCustomerName(BigDecimal dealWithCustomer);
}
