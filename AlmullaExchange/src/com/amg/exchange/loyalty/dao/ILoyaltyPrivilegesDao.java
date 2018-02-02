package com.amg.exchange.loyalty.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.loyalty.model.LoyaltyPrivileges;
import com.amg.exchange.registration.model.Employee;


public interface ILoyaltyPrivilegesDao {

	void save(LoyaltyPrivileges LoyaltyPrivileges);

	List<Employee> getEmployeeList(BigDecimal countryId);

	LoyaltyPrivileges populateValues(String userName);

	BigDecimal getEmployeeId(String userName);
}
