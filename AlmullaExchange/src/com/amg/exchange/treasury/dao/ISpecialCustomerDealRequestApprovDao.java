package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;

public interface ISpecialCustomerDealRequestApprovDao<T> {
	
	public List<CustomerSpecialDealRequest> CustomerSpecialDealRequest(BigDecimal countryId);
	public void updateApprove(List<BigDecimal> lstApproved, String userName);
	public String getCountryName(BigDecimal countryId);
    public List<CustomerSpecialDealRequest> getCustName(Customer countryId);
    
    
    public String countryName(BigDecimal countryId,BigDecimal languageId);
	
}
