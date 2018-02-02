package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.treasury.dao.IStateMasterDao;
import com.amg.exchange.treasury.service.IStateMasterService;

@Service("stateMasterServiceImpl")
public class StateMasterServiceImpl<T> implements IStateMasterService<T> {

	  @Autowired
	  IStateMasterDao<T> stateMasterDao;

	  @Override
	  @Transactional
	  public List<String> getStateListCode(String query) {
		    return stateMasterDao.getStateListCode(query);
	  }

	  @Override
	  @Transactional
	  public void saveRecord(StateMaster stateMaster, StateMasterDesc stateMasterDes1, StateMasterDesc stateMasterDes2) {
		    stateMasterDao.saveRecord(stateMaster, stateMasterDes1, stateMasterDes2);
	  }

	  @Override
	  @Transactional
	  public List<StateMaster> getStateList(String stateCode,BigDecimal countryId) {
		    return stateMasterDao.getStateList(stateCode,countryId);
	  }

	  @Override
	  @Transactional
	  public List<StateMasterDesc> getstateDescList(BigDecimal stateId) {
		    return stateMasterDao.getstateDescList(stateId);
	  }

	  @Override
	  @Transactional
	  public List<StateMasterDesc> getAllStateList() {
		    return stateMasterDao.getAllStateList();
	  }

	  @Override
	  @Transactional
	  public void save(StateMaster stateMaster) {
		    stateMasterDao.save(stateMaster);

	  }

	  @Override
	  @Transactional
	  public void saverRecordDesc(StateMasterDesc stateMasterDes1) {
		    stateMasterDao.saverRecordDesc(stateMasterDes1);
	  }

	  @Override
	  @Transactional
	  public void deleteRecordDesc(StateMasterDesc stateMasterDes1) {
		    stateMasterDao.deleteRecordDesc(stateMasterDes1);
	  }

	  @Override
	  @Transactional
	  public void delete(StateMaster stateMaster) {
		    stateMasterDao.delete(stateMaster);
	  }

	  @Override
	  @Transactional
	  public void approvedRecord(BigDecimal stateId, String userName) {
		    stateMasterDao.approvedRecord(stateId, userName);
	  }

	  @Override
	  @Transactional
	  public List<StateMasterDesc> getAllStateApproveList() {
		    return stateMasterDao.getAllStateApproveList();
	  }

	  @Override
	  @Transactional
	  public List<CountryMasterDesc> getAllCountryMasterList(BigDecimal languageId, BigDecimal countryId) {
		    return stateMasterDao.getAllCountryMasterList(languageId, countryId);
	  }

	  @Override
	  @Transactional
	  public void saveRecordForFileupload(StateMaster stateMaster) {
		    stateMasterDao.saveRecordForFileupload(stateMaster);
	  }

	  @Override
	  @Transactional
	  public void saveRecordForFileUploadDesc(StateMasterDesc stateMasterDes1) {
		    stateMasterDao.saveRecordForFileUploadDesc(stateMasterDes1);
	  }

	  @Override
	  @Transactional
	  public List<StateMaster> getstateMasterList(String stateCode) {
		    return stateMasterDao.getstateMasterList(stateCode);
	  }

	  @Override
	  @Transactional
	  public List<StateMaster> getAllStateMasterList() {
		    return stateMasterDao.getAllStateMasterList();
	  }

	  @Override
	  @Transactional
	  public String approvRecord(BigDecimal stateId, String userName) {
		    return stateMasterDao.approvRecord(stateId, userName);
	  }

	  @Override
	  @Transactional
	  public List<String> getStateListCode(String query, BigDecimal countryId) {
		    return stateMasterDao.getStateListCode(query, countryId);
	  }

	  @Override
	  @Transactional
	  public List<StateMasterDesc> getAllStateList(BigDecimal countryId) {
		    return stateMasterDao.getAllStateList(countryId);
	  }

	  @Override
	  @Transactional
	  public List<StateMasterDesc> toFetchStateDesc(String englishStateName) {
		    return stateMasterDao.toFetchStateDesc(englishStateName);
	  }

	  @Override
	  @Transactional
	  public List<StateMaster> toFetchDetailsForView(BigDecimal countryId) {
		    return stateMasterDao.toFetchDetailsForView(countryId);
	  }

	  @Override
	  @Transactional
	  public List<StateMaster> toCheckBasedOnCountryAndStateCode(BigDecimal countryId, String stateCode) {
		    return stateMasterDao.toCheckBasedOnCountryAndStateCode(countryId,stateCode);
	  }

	  @Override
	  @Transactional
	  public List<StateMasterDesc> toFetchStateDesc(String englishStateName, BigDecimal countryId) {
		    return stateMasterDao.toFetchStateDesc(englishStateName,countryId);
	  }

	  @Override
	  @Transactional
	  public List<StateMaster> toFetchForApprovalView(BigDecimal countryId) {
		    return stateMasterDao.toFetchForApprovalView(countryId);
	  }

	  @Override
	  @Transactional
	  public List<StateMasterDesc> toFetchAllStateDescBasedOnDesc(String englishStateName, BigDecimal countryId) {
		    return stateMasterDao.toFetchAllStateDescBasedOnDesc(englishStateName,countryId);
	  }

	@Override
	@Transactional
	public List<StateMasterDesc> getAllStateListBasedOnLanguae(BigDecimal languageId, BigDecimal countryId) {
		return stateMasterDao.getAllStateListBasedOnLanguae(languageId, countryId);
	}

}
