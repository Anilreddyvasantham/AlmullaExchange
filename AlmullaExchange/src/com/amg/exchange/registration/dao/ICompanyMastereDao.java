package com.amg.exchange.registration.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.remittance.model.ViewBeneServiceCurrency;


public interface ICompanyMastereDao {

	void save(CountryMaster countryMaster);

	List<String> getAllComponent(String query);

	void saveDescription(CountryMasterDesc countryeMasterDesc);

	CompanyMaster viewByCode(BigDecimal getcompanyCode);

	List<CompanyMasterDesc> checkDesciption(String localcompanyDescription);

	List<CompanyMasterDesc> viewById(BigDecimal companyId);

	void save(CompanyMaster savecompanyMaster, List<CompanyMasterDesc> companyMasterDesc) throws Exception;

	 List<CompanyMaster> viewMasterRecords();

	void delete(CompanyMaster companyMaster, List<CompanyMasterDesc> desc);

	List<CompanyMaster> viewMasterRecordsforApproval();

	void approveRecord(BigDecimal companyId, String userName);

	String viewById(BigDecimal companyId, BigDecimal languageId);
	
	public void saveCompanyValues(CompanyMaster companyMaster,List<CompanyMasterDesc> list) throws Exception;
	
}
