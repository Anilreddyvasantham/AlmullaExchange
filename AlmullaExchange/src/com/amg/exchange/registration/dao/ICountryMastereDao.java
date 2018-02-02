package com.amg.exchange.registration.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;


public interface ICountryMastereDao {

	void save(CountryMaster countryMaster);

	List<CountryMasterDesc> getAllComponent(String query, BigDecimal languageId);

	void saveDescription(CountryMasterDesc countryeMasterDesc);

	CountryMaster viewByCode(String countryCode);

	List<CountryMasterDesc> viewDescriptionsById(BigDecimal countryId);


	List<CountryMaster> getCountryMasterList();

	List<CountryMasterDesc> getCountryList();

	boolean checkDesciption(String descption);

	boolean checkNationality(String localNationality);

	boolean isoCheck(String isoCode);

	boolean alpha2Check(String countryAlpha2Code);
	boolean alpha3Check(String countryAlpha3Code);

	void save(HashMap<String, Object> saveObject) throws Exception;

	List<CountryMasterDesc> viewById(BigDecimal countryId);

	void delete(CountryMaster master, List<CountryMasterDesc> countryDescList);

	List<CountryMasterDesc> getunApproveCountryList();

	void approveRecord(BigDecimal countryId, String userName);

	List<CountryMaster> getCountryMasterListforApproval();

	boolean TeleCodeCheck(String telCode);

	List<CountryMasterDesc> getbankApplicabilityCountryList(BigDecimal languageId, List<BigDecimal> appCountryList);
	
}
