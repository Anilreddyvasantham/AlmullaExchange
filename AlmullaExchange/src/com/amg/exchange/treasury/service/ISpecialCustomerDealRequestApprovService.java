package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;

public interface ISpecialCustomerDealRequestApprovService<T> {

	public List<CustomerSpecialDealRequest> CustomerSpecialDealRequest(
			BigDecimal countryId);

	public void updateApprove(List<BigDecimal> lstApproved, String userName);

	public String getCountryName(BigDecimal countryId);
	
	public List<CustomerSpecialDealRequest> getCustName(Customer customer);
	
	public String countryName(BigDecimal countryId,BigDecimal languageId);
	
}
