package com.amg.exchange.registration.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.remittance.model.ViewBeneServiceCurrency;



public interface ICompanyMasterservice {
	void save(CountryMaster countryMaster);

	void saveDescription(CountryMasterDesc countryeMasterDesc);

	List<String> getAllComponent(String query);

	CompanyMaster viewByCode(BigDecimal getcompanyCode);

	List<CompanyMasterDesc> checkDesciption(String localcompanyDescription);

	List<CompanyMasterDesc> viewById(BigDecimal companyId);

	void save(CompanyMaster savecompanyMaster, List<CompanyMasterDesc> companyMasterDesc) throws Exception;

	List<CompanyMaster> viewMasterRecords();

	void delete(CompanyMaster companyMaster, List<CompanyMasterDesc> desc);

	List<CompanyMaster> viewMasterRecordsforApproval();

	void approveRecord(BigDecimal companyId, String userName);

	String viewById(BigDecimal companyId, BigDecimal languageId);
	
	public void saveCompanyValues(CompanyMaster companyMaster,List<CompanyMasterDesc> comMasterDesc) throws Exception;


}
