package com.amg.exchange.remittance.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.BankFieldMapping;

public interface IBankFieldMappingDao<T> {

	  public List<AdditionalBankRuleMap> toFetchAdditionalData();

	  public void saveAllBankFieldMapping(BankFieldMapping bankFieldMapping);

	  public void deleteRecordPermentelyFromDb(BigDecimal bankFieldMappingId);

	  public void upDateActiveRecordtoDeActive(BigDecimal bankFieldMappingId, String remarks, String userName);

	  public void DeActiveRecordToPendingForApprovalBankFieldMapping(BigDecimal bankFieldMappingId, String userName);

	  public List<BankFieldMapping> toFetchAllViewDetailsOfBankFieldMapping(BigDecimal countryId);

	  public String toFtechCoumnIdBasedOnColumnName(String fieldName,String tableName);

	  public List<BankFieldMapping> toFetchAllApprovalDetailsOfBankFieldMapping(BigDecimal countryId);

	  public String checkBankFieldMappingMultiUser(BigDecimal bankFieldMappingId, String userName);

	  public List<BankFieldMapping> toCheckDuplicateData(String tableName, String fieldName, String fieldvalue);

}
