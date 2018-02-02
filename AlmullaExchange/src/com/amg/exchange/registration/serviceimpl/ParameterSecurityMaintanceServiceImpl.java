package com.amg.exchange.registration.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.IParameterSecurityMaintainceService;
import com.amg.exchange.remittance.dao.IParameterSecurityMaintanceDao;
import com.amg.exchange.remittance.model.ParameterGrant;
import com.amg.exchange.remittance.model.ParameterMaster;
@Service("parameterSecurityMaintainceService")
public class ParameterSecurityMaintanceServiceImpl implements IParameterSecurityMaintainceService {


	@Autowired
	IParameterSecurityMaintanceDao parameterSecurityMaintanceDao;

	@Override
	@Transactional
	public List<ParameterMaster> getParameterRuleId(BigDecimal parameterid) {
		return parameterSecurityMaintanceDao.getParameterRuleId(parameterid);
	}

	@Override
	@Transactional
	public List<Employee> getAllBranchList(BigDecimal branchid) {
		return parameterSecurityMaintanceDao.getAllBranchList(branchid);
	}

	@Override
	@Transactional
	public void save(ParameterGrant parameterGrant) {
		parameterSecurityMaintanceDao.save(parameterGrant);
	}

	@Override
	@Transactional
	public List<ParameterGrant> getAllDetailsFromDBAll(BigDecimal countryBranchid, BigDecimal userId, String userName) {
		return parameterSecurityMaintanceDao.getAllDetailsFromDBAll(countryBranchid,userId,userName);
	}

	@Override
	@Transactional
	public List<String> getParameterCode(String query) {
		return parameterSecurityMaintanceDao.getParameterCode(query);
	}

	@Override
	@Transactional
	public List<ParameterMaster> tofetchAllRecordsFromParameterMaster() {
		return parameterSecurityMaintanceDao.tofetchAllRecordsFromParameterMaster();
	}

	@Override
	@Transactional
	public String toFetchRecordIdFromParameterMaster(BigDecimal parameterId) {
		return parameterSecurityMaintanceDao.toFetchRecordIdFromParameterMaster(parameterId);
	}

	@Override
	@Transactional
	public List<Employee> toFetchEmployeeRecords(String userName,BigDecimal branchid) {
		return parameterSecurityMaintanceDao.toFetchEmployeeRecords(userName,branchid);
	}

	@Override
	@Transactional
	public List<ParameterGrant> getAllDetailsFromDBAll(BigDecimal countryBranchid, BigDecimal userId, String userName,String parameterMaster) {
		 return parameterSecurityMaintanceDao.getAllDetailsFromDBAll(countryBranchid, userId, userName, parameterMaster);
	}



}
