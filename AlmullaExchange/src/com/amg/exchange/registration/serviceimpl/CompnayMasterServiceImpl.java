package com.amg.exchange.registration.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.dao.ICompanyMastereDao;
import com.amg.exchange.registration.service.ICompanyMasterservice;
import com.amg.exchange.remittance.model.ViewBeneServiceCurrency;

@Service("companyMasterservice")
public class CompnayMasterServiceImpl implements ICompanyMasterservice  {
	

	
	@Autowired
	ICompanyMastereDao companyMasterservice;

	@Override
	@Transactional
	public void save(CountryMaster countryMaster) {
		companyMasterservice.save(countryMaster);
		
	}

	@Override
	public void saveDescription(CountryMasterDesc countryeMasterDesc) {
		companyMasterservice.saveDescription(countryeMasterDesc);
	}


	@Override
	@Transactional
	public List<String> getAllComponent(String query) {
		return companyMasterservice.getAllComponent(query);
	}

	@Override
	@Transactional
	public CompanyMaster viewByCode(BigDecimal getcompanyCode) {
		return companyMasterservice.viewByCode(getcompanyCode);
	}

	@Override
	@Transactional
	public List<CompanyMasterDesc> checkDesciption(String localcompanyDescription) {
		// TODO Auto-generated method stub
		return companyMasterservice.checkDesciption(localcompanyDescription);
	}

	@Override
	@Transactional
	public List<CompanyMasterDesc> viewById(BigDecimal companyId) {
		return companyMasterservice.viewById(companyId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(CompanyMaster savecompanyMaster, List<CompanyMasterDesc> companyMasterDesc) throws Exception {
		companyMasterservice.save(savecompanyMaster,companyMasterDesc);
		
	}

	@Override
	@Transactional
	public List<CompanyMaster> viewMasterRecords() {
		return companyMasterservice.viewMasterRecords();
	}

	@Override
	@Transactional
	public void delete(CompanyMaster companyMaster, List<CompanyMasterDesc> desc) {
		companyMasterservice.delete(companyMaster,desc);
		
	}

	@Override
	@Transactional
	public List<CompanyMaster> viewMasterRecordsforApproval() {
		return companyMasterservice.viewMasterRecordsforApproval();
	}

	@Override
	@Transactional
	public void approveRecord(BigDecimal companyId, String userName) {
		companyMasterservice.approveRecord(companyId,userName);
	}

	@Override
	@Transactional
	public String viewById(BigDecimal companyId, BigDecimal languageId) {
		return companyMasterservice.viewById(companyId,languageId);
	}
	
	@Override
	@Transactional
	public void saveCompanyValues(CompanyMaster companyMaster,List<CompanyMasterDesc> comMasterDesc) throws Exception {
		 companyMasterservice.saveCompanyValues(companyMaster,comMasterDesc);
	}

}