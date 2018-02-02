package com.amg.exchange.remittance.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.dao.IBankFieldMappingDao;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.BankFieldMapping;
import com.amg.exchange.remittance.service.IBankFieldMappingService;
@Service("bankFieldMappingService")

public class BankFieldMappingServiceImpl<T> implements IBankFieldMappingService<T>,Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  @Autowired
	  IBankFieldMappingDao<T> bankFieldMappingDao;

	  @Override
	  @Transactional
	  public List<AdditionalBankRuleMap> toFetchAdditionalData() {
		    return bankFieldMappingDao.toFetchAdditionalData();
	  }

	  @Override
	  @Transactional
	  public void saveAllBankFieldMapping(BankFieldMapping bankFieldMapping) {
		    bankFieldMappingDao.saveAllBankFieldMapping(bankFieldMapping);    
	  }

	  @Override
	  @Transactional
	  public void deleteRecordPermentelyFromDb(BigDecimal bankFieldMappingId) {
		    bankFieldMappingDao.deleteRecordPermentelyFromDb(bankFieldMappingId);
	  }

	  @Override
	  @Transactional
	  public void upDateActiveRecordtoDeActive(BigDecimal bankFieldMappingId, String remarks, String userName) {
		    bankFieldMappingDao.upDateActiveRecordtoDeActive(bankFieldMappingId,remarks,userName);
	  }

	  @Override
	  @Transactional
	  public void DeActiveRecordToPendingForApprovalBankFieldMapping(BigDecimal bankFieldMappingId, String userName) {
		    bankFieldMappingDao.DeActiveRecordToPendingForApprovalBankFieldMapping(bankFieldMappingId,userName);   
	  }

	  @Override
	  @Transactional
	  public List<BankFieldMapping> toFetchAllViewDetailsOfBankFieldMapping(BigDecimal countryId) {
		    return bankFieldMappingDao.toFetchAllViewDetailsOfBankFieldMapping(countryId);
	  }

	  @Override
	  @Transactional
	  public String toFtechCoumnIdBasedOnColumnName(String fieldName,String tableName) {
		    return bankFieldMappingDao.toFtechCoumnIdBasedOnColumnName(fieldName,tableName);
	  }

	  @Override
	  @Transactional
	  public List<BankFieldMapping> toFetchAllApprovalDetailsOfBankFieldMapping(BigDecimal countryId) {
		    return bankFieldMappingDao.toFetchAllApprovalDetailsOfBankFieldMapping(countryId);
	  }

	  @Override
	  @Transactional
	  public String checkBankFieldMappingMultiUser(BigDecimal bankFieldMappingId, String userName) {
		    return bankFieldMappingDao.checkBankFieldMappingMultiUser(bankFieldMappingId,userName);
	  }

	  @Override
	  @Transactional
	  public List<BankFieldMapping> toCheckDuplicateData(String tableName, String fieldvalue, String fieldName) {
		    return bankFieldMappingDao.toCheckDuplicateData(tableName,fieldName,fieldvalue);
	  }
}
