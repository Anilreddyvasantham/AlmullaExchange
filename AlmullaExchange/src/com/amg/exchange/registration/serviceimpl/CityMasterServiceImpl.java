package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.registration.dao.ICityMasterDao;
import com.amg.exchange.registration.service.ICityMasterService;

@SuppressWarnings("serial")
@Service("cityMasterServiceImpl")
public class CityMasterServiceImpl<T> implements ICityMasterService<T>, Serializable {

	  @Autowired
	  ICityMasterDao<T> cityMasterDao;

	  @Override
	  @Transactional
	  public List<CityMasterDesc> view() {
		    return cityMasterDao.view();
	  }

	  @Override
	  @Transactional
	  public List<CityMasterDesc> getIdentityStatus(BigDecimal countryId, BigDecimal businessComponentId) {
		    return cityMasterDao.getIdentityStatus(countryId, businessComponentId);
	  }

	  @Override
	  @Transactional
	  public List<CityMasterDesc> getUnApprovedList() {
		    return cityMasterDao.getUnApprovedList();
	  }

	  @Override
	  @Transactional
	  public void approve(BigDecimal cityMasterPk, String userName, Date currentDate) {
		    cityMasterDao.approve(cityMasterPk, userName, currentDate);
	  }

	  @Override
	  @Transactional
	  public void delete(BigDecimal cityMasterPk, BigDecimal cityDescEnPk, BigDecimal cityDescLocalPk) {
		    cityMasterDao.delete(cityMasterPk, cityDescEnPk, cityDescLocalPk);

	  }

	  @Override
	  @Transactional
	  public void softDelete(BigDecimal cityMasterPk, String userName, Date currentDate, String remarks) {
		    cityMasterDao.softDelete(cityMasterPk, userName, currentDate, remarks);

	  }

	  @Override
	  @Transactional
	  public void activate(BigDecimal cityMasterPk, String userName, Date currentDate) {
		    cityMasterDao.activate(cityMasterPk, userName, currentDate);

	  }

	  @Override
	  @Transactional
	  public List<CityMaster> viewAllRecord(String countryCode, String stateCode, String districtCode) {
		    return cityMasterDao.viewAllRecord(countryCode, stateCode, districtCode);
	  }

	  @Override
	  @Transactional
	  public List<CityMasterDesc> viewDescRecord(BigDecimal cityIdPk) {
		    return cityMasterDao.viewDescRecord(cityIdPk);
	  }

	  @Override
	  @Transactional
	  public List<CityMaster> viewUnApproveRecord() {
		    return cityMasterDao.viewUnApproveRecord();
	  }

	  @Override
	  @Transactional
	  public List<CityMaster> viewMatchingRecord() {
		    return cityMasterDao.viewMatchingRecord();
	  }

	  @Override
	  @Transactional
	  public List<CityMaster> viewRecordDetails(String countryCode, String stateCode, BigDecimal districtId) {
		    return cityMasterDao.viewRecordDetails(countryCode, stateCode, districtId);
	  }

	  @Override
	  @Transactional
	  public List<CountryMaster> viewCountryId(String countryCode) {
		    return cityMasterDao.viewCountryId(countryCode);
	  }

	  @Override
	  @Transactional
	  public List<StateMaster> viewStateIdId(BigDecimal countryId, String stateCode) {
		    return cityMasterDao.viewStateIdId(countryId, stateCode);
	  }

	  @Override
	  @Transactional
	  public List<StateMasterDesc> getStateList(BigDecimal languageId) {
		    return cityMasterDao.getStateList(languageId);
	  }

	  @Override
	  @Transactional
	  public List<CountryMasterDesc> getCountryList(BigDecimal languageId) {
		    return cityMasterDao.getCountryList(languageId);
	  }

	  @Override
	  @Transactional
	  public String getCountryName(BigDecimal languageId, String countryCode) {
		    // TODO Auto-generated method stub
		    return cityMasterDao.getCountryName(languageId, countryCode);
	  }

	  @Override
	  @Transactional
	  public String getStateName(BigDecimal languageId, String stateCode) {
		    return cityMasterDao.getStateName(languageId, stateCode);
	  }

	  @Override
	  @Transactional
	  public String getDistrictName(BigDecimal languageId, String districtCode) {
		    return cityMasterDao.getDistrictName(languageId, districtCode);
	  }

	  @Override
	  @Transactional
	  public List<String> getCityListCode(String countryCode, String stateCode, String districtCode, String query) {
		    return cityMasterDao.getCityListCode(countryCode, stateCode, districtCode, query);
	  }

	  @Override
	  @Transactional
	  public List<CityMasterDesc> fetchDBBasedonCityCode(String countryCode, String stateCode, String districtCode, String cityCode) {
		    return cityMasterDao.fetchDBBasedonCityCode(countryCode, stateCode, districtCode, cityCode);
	  }

	  @Override
	  @Transactional
	  public void deleteRecordPermentelyFromDb(BigDecimal cityIdPk, BigDecimal cityDescIdEnglishPk, BigDecimal cityDescIdLocalPk) {
		    cityMasterDao.deleteRecordPermentelyFromDb(cityIdPk,cityDescIdEnglishPk,cityDescIdLocalPk);
	  }

	  @Override
	  @Transactional
	  public List<CityMaster> toFetchRecordsForApproval(String countryCode, String stateCode, String districtCode) {
		    return cityMasterDao.toFetchRecordsForApproval(countryCode,stateCode,districtCode);
	  }

	  @Override
	  @Transactional
	  public List<CityMaster> toCheckBasedOnCountryBasedOnStateBasedOnDistBasedOnCityCode(String countryCode, String stateCode, BigDecimal districtId, String cityCode) {
		    return cityMasterDao.toCheckBasedOnCountryBasedOnStateBasedOnDistBasedOnCityCode(countryCode,stateCode,districtId,cityCode);
	  }
	  @Transactional
	  @Override
	  public List<CityMasterDesc> toFetchCityDesc(String cityName, String countryCode, String stateCode, String districtCode) {
		    return cityMasterDao.toFetchCityDesc(cityName,countryCode,stateCode,districtCode);
	  }
	  @Transactional
	  @Override
	  public String toFetchDestrictCodeBasedOnDistrictId(BigDecimal districtId,String stateCode) {
		    return cityMasterDao.toFetchDestrictCodeBasedOnDistrictId(districtId,stateCode);
	  }

	  @Override
	  @Transactional
	  public List<CityMaster> viewRecordDetailsForCheck(String cityCode, String countryCode, String stateCode, BigDecimal districtId) {
		    return cityMasterDao.viewRecordDetailsForCheck(cityCode,countryCode,stateCode,districtId);
	  }

	  @Override
	  @Transactional(rollbackFor=Exception.class)
	  public void saveAllRecorsToDB(CityMaster cityMaster, CityMasterDesc desc, CityMasterDesc desc1) {
		    cityMasterDao.saveAllRecorsToDB(cityMaster,desc,desc1);
	  }

}
