package com.amg.exchange.registration.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.remittance.model.ParameterGrant;
import com.amg.exchange.remittance.model.ParameterMaster;

public interface IParameterSecurityMaintainceService {

	public  List<ParameterMaster> getParameterRuleId(BigDecimal parameterid);

	public List<Employee> getAllBranchList(BigDecimal branchid);

	public void save(ParameterGrant parameterGrant);

	public List<ParameterGrant> getAllDetailsFromDBAll(BigDecimal countryBranchid, BigDecimal userId, String userName);
	
	//added parameter Master
	public List<ParameterGrant> getAllDetailsFromDBAll(BigDecimal countryBranchid,BigDecimal userId,String userName,String parameterMaster);

	public List<String> getParameterCode(String query);

	public List<ParameterMaster> tofetchAllRecordsFromParameterMaster();

	public String toFetchRecordIdFromParameterMaster(BigDecimal parameterId);

	public List<Employee> toFetchEmployeeRecords(String userName,BigDecimal branchid);



}
