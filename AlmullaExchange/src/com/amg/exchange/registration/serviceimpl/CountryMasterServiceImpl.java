package com.amg.exchange.registration.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.dao.ICountryMastereDao;
import com.amg.exchange.registration.service.ICountryMasterservice;

@Service("countryMasterservice")
public class CountryMasterServiceImpl implements ICountryMasterservice {
	

	
	@Autowired
	ICountryMastereDao iCountryMastereDao;

	@Override
	@Transactional
	public void save(CountryMaster countryMaster) {
		iCountryMastereDao.save(countryMaster);
		
	}

	@Override
	@Transactional
	public void saveDescription(CountryMasterDesc countryeMasterDesc) {
		iCountryMastereDao.saveDescription(countryeMasterDesc);
	}


	@Override
	@Transactional
	public List<CountryMasterDesc> getAllComponent(String query, BigDecimal languageId) {
		return iCountryMastereDao.getAllComponent(query,languageId);
	}

	@Override
	@Transactional
	public CountryMaster viewByCode(String countryCode) {
		return iCountryMastereDao.viewByCode(countryCode);
	}

	@Override
	@Transactional
	public List<CountryMasterDesc> viewDescriptionsById(BigDecimal countryId) {
		return iCountryMastereDao.viewDescriptionsById(countryId);
	}


	@Override
	@Transactional
	public List<CountryMaster> getCountryMasterList() {
		// TODO Auto-generated method stub
		return iCountryMastereDao.getCountryMasterList();
	}

	@Override
	@Transactional
	public List<CountryMasterDesc> getCountryList() {
		
		return iCountryMastereDao.getCountryList();
	}

	@Override
	@Transactional
	public boolean checkDesciption(String Descption) {
		
		return iCountryMastereDao.checkDesciption(Descption);
	}

	@Override
	@Transactional
	public boolean checkNationality(String localNationality) {
		return iCountryMastereDao.checkNationality(localNationality);
	}

	@Override
	@Transactional
	public boolean isoCheck(String isoCode) {
		// TODO Auto-generated method stub
		return iCountryMastereDao.isoCheck(isoCode);
	}

	@Override
	@Transactional
	public boolean alpha2Check(String countryAlpha2Code) {
		// TODO Auto-generated method stub
		return iCountryMastereDao.alpha2Check(countryAlpha2Code);
	}

	@Override
	@Transactional
	public boolean alpha3Check(String countryAlpha3Code) {
		return iCountryMastereDao.alpha3Check(countryAlpha3Code);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(HashMap<String, Object> saveObject) throws Exception {
		iCountryMastereDao.save(saveObject);
		
	}

	@Override
	@Transactional
	public List<CountryMasterDesc> viewById(BigDecimal countryId) {
		return iCountryMastereDao.viewById(countryId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(CountryMaster master, List<CountryMasterDesc> countryDescList) {
		iCountryMastereDao.delete(master,countryDescList);
		
	}

	@Override
	@Transactional
	public List<CountryMasterDesc> getunApproveCountryList() {
		return iCountryMastereDao.getunApproveCountryList();
	}

	@Override
	@Transactional
	public void approveRecord(BigDecimal countryId, String userName) {
		
		iCountryMastereDao.approveRecord(countryId,userName);
	}

	@Override
	@Transactional
	public List<CountryMaster> getCountryMasterListforApproval() {
		// TODO Auto-generated method stub
		return iCountryMastereDao.getCountryMasterListforApproval();
	}

	@Override
	@Transactional
	public boolean TeleCodeCheck(String telCode) {
		return iCountryMastereDao.TeleCodeCheck(telCode);
	}

	@Override
	@Transactional
	public List<CountryMasterDesc> getbankApplicabilityCountryList(BigDecimal languageId, List<BigDecimal> appCountryList) {
		// TODO Auto-generated method stub
		return iCountryMastereDao.getbankApplicabilityCountryList(languageId,appCountryList);
	}}
