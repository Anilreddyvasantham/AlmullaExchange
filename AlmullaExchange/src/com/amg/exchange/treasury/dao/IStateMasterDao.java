package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;

public interface IStateMasterDao<T> {

	  public List<String> getStateListCode(String query);

	  public void saveRecord(StateMaster stateMaster, StateMasterDesc stateMasterDes1, StateMasterDesc stateMasterDes2);

	  public List<StateMaster> getStateList(String stateCode,BigDecimal countryId);

	  public List<StateMasterDesc> getstateDescList(BigDecimal stateId);

	  public List<StateMasterDesc> getAllStateList();

	  public void save(StateMaster stateMaster);

	  public void saverRecordDesc(StateMasterDesc stateMasterDes1);

	  public void deleteRecordDesc(StateMasterDesc stateMasterDes1);

	  public void delete(StateMaster stateMaster);

	  public void approvedRecord(BigDecimal stateId, String userName);

	  public List<StateMasterDesc> getAllStateApproveList();

	  public List<CountryMasterDesc> getAllCountryMasterList(BigDecimal languageId, BigDecimal countryId);

	  public void saveRecordForFileupload(StateMaster stateMaster);

	  public void saveRecordForFileUploadDesc(StateMasterDesc stateMasterDes1);

	  public List<StateMaster> getstateMasterList(String stateCode);

	  public List<StateMaster> getAllStateMasterList();

	  public String approvRecord(BigDecimal stateId, String userName);

	  public List<String> getStateListCode(String query, BigDecimal countryId);

	  public List<StateMasterDesc> getAllStateList(BigDecimal countryId);

	  public List<StateMasterDesc> toFetchStateDesc(String englishStateName);

	  public List<StateMaster> toFetchDetailsForView(BigDecimal countryId);

	  public List<StateMaster> toCheckBasedOnCountryAndStateCode(BigDecimal countryId, String stateCode);

	  public List<StateMasterDesc> toFetchStateDesc(String englishStateName, BigDecimal countryId);

	  public List<StateMaster> toFetchForApprovalView(BigDecimal countryId);

	  public List<StateMasterDesc> toFetchAllStateDescBasedOnDesc(String englishStateName, BigDecimal countryId);
	  
	  public List<StateMasterDesc> getAllStateListBasedOnLanguae(BigDecimal languageId,BigDecimal countryId);

	  // public List<StateMasterDesc> getAllStateDescList(BigDecimal
	  // stateId);

}
