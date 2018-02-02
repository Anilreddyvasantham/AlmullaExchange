package com.amg.exchange.loyalty.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.loyalty.model.LoyaltyPrivileges;
import com.amg.exchange.loyalty.dao.ILoyaltyPrivilegesDao;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.loyalty.service.ILoyaltyPrivilegesService;

@Service("loyaltyPrivilegesservice")
public class LoyaltyPrivilegesServiceImpl implements ILoyaltyPrivilegesService  {
	

	
	@Autowired
	ILoyaltyPrivilegesDao loyaltyPrivilegesDao;

	
	
	
	public ILoyaltyPrivilegesDao getLoyaltyPrivilegesDao() {
		return loyaltyPrivilegesDao;
	}

	public void setLoyaltyPrivilegesDao(ILoyaltyPrivilegesDao loyaltyPrivilegesDao) {
		this.loyaltyPrivilegesDao = loyaltyPrivilegesDao;
	}

	@Override
	@Transactional
	public void save(LoyaltyPrivileges loyaltyPrivileges) {
	
		getLoyaltyPrivilegesDao().save(loyaltyPrivileges);
		
	}

	@Override
	@Transactional
	public List<Employee> getEmployeeList(BigDecimal countryId) {
		return getLoyaltyPrivilegesDao().getEmployeeList(countryId);
	}

	@Override
	@Transactional
	public LoyaltyPrivileges populateValues(String userName) {
		return getLoyaltyPrivilegesDao().populateValues(userName);
	}

	@Override
	@Transactional
	public BigDecimal getEmployeeId(String userName) {
		return getLoyaltyPrivilegesDao().getEmployeeId(userName);
	}


	

}