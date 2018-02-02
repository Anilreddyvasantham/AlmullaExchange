package com.amg.exchange.currency.inquiry.serviceImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.currency.inquiry.dao.IBranchInterchangeDao;
import com.amg.exchange.currency.inquiry.service.IBranchInterchangeService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;

@SuppressWarnings("serial")
@Service("branchInterchangeServiceImpl")
public class BranchInterchangeServiceImpl<T> implements IBranchInterchangeService, Serializable {

	@Autowired
	IBranchInterchangeDao branchInterchangeDao;

	public BranchInterchangeServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public IBranchInterchangeDao getBranchInterchangeDao() {
		return branchInterchangeDao;
	}

	public void setBranchInterchangeDao(IBranchInterchangeDao branchInterchangeDao) {
		this.branchInterchangeDao = branchInterchangeDao;
	}
	@Override
	@Transactional
	public void saveOrUpdate(Employee employee){
		getBranchInterchangeDao().saveOrUpdate(employee);
	}

	@Override
	@Transactional
	public List<CountryBranch> getCountryBranchList(BigDecimal appCountryId,BigDecimal countryBranchId){
		
		return getBranchInterchangeDao().getCountryBranchList(appCountryId,countryBranchId);
	}

	@Override
	@Transactional
	public List<Employee> getAllEmployeeList(BigDecimal appCountryId) {
		
		return getBranchInterchangeDao().getAllEmployeeList(appCountryId);
	}
	@Override
	@Transactional
	public List<Employee> getEmployeeList(BigDecimal employeeId){

		return getBranchInterchangeDao().getEmployeeList(employeeId);
	}
	@Override
	@Transactional
	public void updateRecord(BigDecimal employeeId,BigDecimal countryBranchId,String location){
		getBranchInterchangeDao().updateRecord(employeeId, countryBranchId,location);
	}

}
