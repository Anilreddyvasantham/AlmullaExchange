/**
 * 
 */
package com.amg.exchange.common.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.treasury.model.ServiceMaster;

/**
 * @author Subramaniam
 *
 */
public interface IDistrictMasterDao<T> {
	public void saveRecord(DistrictMaster districtMaster);

	public String getStateName(BigDecimal languageId,BigDecimal stateId) ;
	public void save(DistrictMasterDesc districtMasterDesc);
	public List<StateMaster> getCountryName(BigDecimal languageId, BigDecimal stateId);
	public void delete(BigDecimal districtId,BigDecimal districtMasterEngDescPk,BigDecimal districtMasterArbDescPk);
	public List<DistrictMasterDesc> getDistrictList();
	public void approveRecord(BigDecimal districtMasterId, String userName);

	public void deleteRecordDesc(DistrictMasterDesc districtMasterDesc);
	public void deleteRecord(DistrictMaster districtMaster);
	public List<DistrictMasterDesc> getDistrictList(BigDecimal districtId);

	public void modifyRecord(BigDecimal districtMasterId, String userName,String remarks);

	public List<DistrictMasterDesc> getDistrictListBasedOnDistrictCode(String districtCode,String stateCode,String districtName);

	public List<StateMasterDesc> getStateName(String stateCode);
	public Boolean isDistrictListBasedOnDistrictCode(String districtCode);

	public List<StateMasterDesc> getStateList(String stateId) ;

	public List<StateMaster> getCountryFromState(BigDecimal stateId);

	public List<DistrictMaster> viewMasterRecords(BigDecimal countryId);

	public List<DistrictMasterDesc> viewById(BigDecimal districtId);

	public void activateRecord(BigDecimal districtMaster, String userName);

	public List<StateMaster> getStateMasterId(BigDecimal countryId,BigDecimal stateId);

	public List<DistrictMasterDesc> checkLocalDescription(String localDistrictName,BigDecimal districtId);

	public List<DistrictMasterDesc> checkEnglishDescription(String englishDistrictName,BigDecimal districtId);

	public List<DistrictMaster> getDistrictBasedOnDistrictCode(String districtCode,BigDecimal stateId);

	public List<DistrictMaster> viewMasterRecords();

	public List<DistrictMaster> approveViewMasterRecords(BigDecimal stateId,String actvice);

	public List<StateMaster> getStateListBasedOnCountryId(BigDecimal countryId, String stateCode);

	public List<StateMasterDesc> getStateNameBasedOnId(BigDecimal stateId);

	public String approveRecord(List<BigDecimal> districtId,String userName);

	public String getDistrictName(BigDecimal languageId,BigDecimal districtId);

	public String getCityName(BigDecimal languageId,BigDecimal cityId);

}
