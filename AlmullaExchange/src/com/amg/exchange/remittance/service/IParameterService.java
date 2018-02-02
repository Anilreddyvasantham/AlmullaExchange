package com.amg.exchange.remittance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.amg.exchange.common.model.AvailableDatabaseObjects;
import com.amg.exchange.common.model.AvailableTableColumns;
import com.amg.exchange.remittance.model.ParameterDefinition;
import com.amg.exchange.remittance.model.ParameterDetails;
import com.amg.exchange.remittance.model.ParameterGrant;
import com.amg.exchange.remittance.model.ParameterMaster;

public interface IParameterService {
	
	public List<ParameterMaster> fetchAllParameterMasterForView();
	
	public List<ParameterMaster> checkRecordIdForParamaterMaster(String recordId);
	
	public void saveParameterMaster(List<ParameterMaster> paramMaster) throws Exception;

	public List<ParameterMaster> getAllComponent(String query, BigDecimal languageId);

	public ParameterMaster viewById(String recordId);

	public ParameterDefinition viewDefinitionById(String recordId);

	public List<ParameterDefinition> getAllRecords();

	public void save(ParameterMaster saveParameterMaster, ParameterDefinition parameterDefinition) throws Exception;

	public void saveParameterMaster(ParameterMaster saveParameterMaster) throws Exception;

	public List<ParameterMaster> viewDefinitionListforDetails(BigDecimal recordId);

	public ParameterDefinition viewByID(BigDecimal parameterDefinitionId);

	public List<ParameterDetails> getAllComponent(String query, BigDecimal parameterDefinitionId, BigDecimal parameterMasterId);

	public ParameterDefinition fetchFlexiName(BigDecimal parameterMasterId, String flexFieldName);

	public List<ParameterDefinition> getFlexiList( BigDecimal parameterMasterId);

	public void saveDetails(ParameterDetails details);

	public List<ParameterDetails> getRecords(BigDecimal parameterDefinitionId, BigDecimal parameterMasterId);

	public List<Object> getRecords(BigDecimal parameterDefinitionId, BigDecimal parameterMasterId, String flexFieldName);

	public List<ParameterDetails> fetchAllRecords();

	public String approveRecord(BigDecimal parameterDetailsId, String userName);

	public List<ParameterDetails> fetchAllunApprovedRecords();

	public List<ParameterDefinition> fetchAllParameterDefinitionforView();
	
	public List<ParameterDefinition> fetchAllParameterDefinitionforView(String recordId);

	public BigDecimal getDetails(BigDecimal parameterDefinitionId, BigDecimal parameterMasterId, String flexFieldName, String fieldValue);

	public ParameterDetails viewByDetailsID(BigDecimal detailsId);

	public void delete(ParameterDetails details);

	public String updateStaus(BigDecimal parameterDetailsId, String userName, String status, String string);

	public List<ParameterDefinition> getnewFlexiList(BigDecimal parameterMasterId, BigDecimal parameterDefinitionId);

	public List<ParameterDetails> getRecordsbasedOnMasterId(BigDecimal parameterMasterId);

	public List<ParameterDetails> getAllDetailsComponent(String query, BigDecimal parameterMasterId);

	public ParameterDetails populateDetails(BigDecimal parameterMasterId, String paramcodedefintion);

	public ParameterMaster viewParameterMasterById(BigDecimal parameterMasterId);

	public List<String> getParameterCodeList(BigDecimal parameterMasterId);

	public String getFlexiFiledName(BigDecimal parameterMasterId, String parameterCode);

	public List<String> getFlexiFieldfromDB(BigDecimal parameterMasterId);
	
	public List<ParameterDefinition> getFlexiFieldfromDBWithList(BigDecimal parameterMasterId);

	public List<AvailableDatabaseObjects> getDataBaseObjects();

	public List<AvailableTableColumns> getTablecolumns(String tableName);

	public ParameterDefinition isValueIndicatorEnabled(String trim, BigDecimal parameterMasterId);

	public Boolean checkDatabaseValue(Object checkValue, String tableName, String tableField, String flag);

	public ParameterDefinition isValueIndicator(String trim, BigDecimal parameterMasterId);

	public List<BigDecimal> getValueList(String tableName, String columnName);

	public List<String> getStringValueList(String tableName, String columnName);

	public List<ParameterGrant> getGrantListfortheUser(String userName);

	public List<ParameterMaster> getUserRightsRecord(ArrayList<String> grantRecordList);

	public List<ParameterDetails> fetchAllunApprovedRecordwithPermission(ArrayList<String> grantRecordList);

	public boolean isExistInDatabase(BigDecimal parameterMasterId, String parameterCode);
	
	public ParameterDetails populateParamaterDetails(BigDecimal parameterMasterId, String paramcodedefintion, BigDecimal parameterDetailsId);


}