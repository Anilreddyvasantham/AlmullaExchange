package com.amg.exchange.currency.inquiry.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;

public interface IBranchInterchangeDao {
	
	public void saveOrUpdate(Employee employee);
	
	public List<CountryBranch> getCountryBranchList(BigDecimal appCountryId,BigDecimal countryBranchId);
	
	public List<Employee> getAllEmployeeList(BigDecimal appCountryId);
	
	public List<Employee> getEmployeeList(BigDecimal employeeId);
	
	public void updateRecord(BigDecimal employeeId,BigDecimal countryBranchId, String location);

}
