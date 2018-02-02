package com.amg.exchange.registration.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;

public interface ICityMasterService<T> {

	  public List<CityMasterDesc> view();

	  public List<CityMasterDesc> getIdentityStatus(BigDecimal countryId, BigDecimal businessComponentId);

	  public List<CityMasterDesc> getUnApprovedList();

	  public void approve(BigDecimal cityMasterPk, String userName, Date currentDate);

	  public void delete(BigDecimal cityMasterPk, BigDecimal cityDescEnPk, BigDecimal cityDescLocalPk);

	  public void softDelete(BigDecimal cityMasterPk, String userName, Date currentDate, String remarks);

	  public void activate(BigDecimal cityMasterPk, String userName, Date currentDate);

	  public List<CityMaster> viewAllRecord(String countryCode, String stateCode, String districtCode);

	  public List<CityMasterDesc> viewDescRecord(BigDecimal cityIdPk);

	  public List<CityMaster> viewUnApproveRecord();

	  public List<CityMaster> viewMatchingRecord();

	  public List<CityMaster> viewRecordDetails(String countryCode, String stateCode, BigDecimal districtId);

	  public List<CountryMaster> viewCountryId(String countryCode);

	  public List<StateMaster> viewStateIdId(BigDecimal countryId, String stateCode);

	  public List<StateMasterDesc> getStateList(BigDecimal languageId);

	  public List<CountryMasterDesc> getCountryList(BigDecimal languageId);

	  public String getCountryName(BigDecimal languageId, String countryCode);

	  public String getStateName(BigDecimal languageId, String stateCode);

	  public String getDistrictName(BigDecimal languageId, String districtCode);

	  public List<String> getCityListCode(String countryCode, String stateCode, String districtCode, String query);

	  public List<CityMasterDesc> fetchDBBasedonCityCode(String countryCode, String stateCode, String districtCode, String cityCode);

	  public void deleteRecordPermentelyFromDb(BigDecimal cityIdPk, BigDecimal cityDescIdEnglishPk, BigDecimal cityDescIdLocalPk);

	  public List<CityMaster> toFetchRecordsForApproval(String countryCode, String stateCode, String districtCode);

	  public List<CityMaster> toCheckBasedOnCountryBasedOnStateBasedOnDistBasedOnCityCode(String countryCode, String stateCode, BigDecimal districtId, String cityCode);

	  public List<CityMasterDesc> toFetchCityDesc(String cityName, String countryCode, String stateCode, String districtCode);

	  public String toFetchDestrictCodeBasedOnDistrictId(BigDecimal districtId,  String stateCode);

	  public List<CityMaster> viewRecordDetailsForCheck(String cityCode, String countryCode, String stateCode, BigDecimal districtId);

	  public void saveAllRecorsToDB(CityMaster cityMaster, CityMasterDesc desc, CityMasterDesc desc1);

}
