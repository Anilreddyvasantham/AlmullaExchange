package com.amg.exchange.registration.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.bean.CreateSearchTable;
import com.amg.exchange.registration.bean.SearchEntityBean;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;


public interface ISearchCustomerDao<T> {
	
	public List<Customer> getCustomer();
	public List<CustomerIdProof> getCustomerIdProof();
	public List<CustomerIdProof> searchAllCustomer(SearchEntityBean searchEntityBean);
	public List<CustomerIdProof> searchCustomerEnquiryForAllCustomer(SearchEntityBean searchEntityBean);
	public List<Customer> getCustomerInfo(BigDecimal customerId);
	//Customer Activate Deactivate Services
	public List<CustomerIdProof> searchAllCustomers(SearchEntityBean searchEntityBean);
	public void update(CreateSearchTable createSearchTable);
	public Boolean checkAuthorizationOfEmp(BigDecimal employeeId);
	public void ActivateRecord(CreateSearchTable createSearchTable);
	public List<CustomerIdProof> getRecentExpiryDate(SearchEntityBean searchEntityBean);
}
