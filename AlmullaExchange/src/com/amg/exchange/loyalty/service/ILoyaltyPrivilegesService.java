package com.amg.exchange.loyalty.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.loyalty.model.LoyaltyPrivileges;
import com.amg.exchange.registration.model.Employee;

public interface ILoyaltyPrivilegesService {
	void save(LoyaltyPrivileges loyaltyPrivileges);

	List<Employee> getEmployeeList(BigDecimal countryId);

	LoyaltyPrivileges populateValues(String userName);

	BigDecimal getEmployeeId(String userName);
}
