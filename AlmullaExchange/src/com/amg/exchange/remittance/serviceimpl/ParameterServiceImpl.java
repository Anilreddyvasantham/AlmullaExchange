package com.amg.exchange.remittance.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.AvailableDatabaseObjects;
import com.amg.exchange.common.model.AvailableTableColumns;
import com.amg.exchange.remittance.dao.IParameterDao;
import com.amg.exchange.remittance.model.ParameterDefinition;
import com.amg.exchange.remittance.model.ParameterDetails;
import com.amg.exchange.remittance.model.ParameterGrant;
import com.amg.exchange.remittance.model.ParameterMaster;
import com.amg.exchange.remittance.service.IParameterService;

@SuppressWarnings("serial")
@Service("parameterServiceImpl")
public class ParameterServiceImpl implements IParameterService, Serializable{
	
	@Autowired
	IParameterDao paramMasterDao;

	@Override
	@Transactional
	public List<ParameterMaster> fetchAllParameterMasterForView() {
		return paramMasterDao.fetchAllParameterMasterForView();
	}

	@Override
	@Transactional
	public List<ParameterMaster> checkRecordIdForParamaterMaster(String recordId) {
		return paramMasterDao.checkRecordIdForParamaterMaster(recordId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveParameterMaster(List<ParameterMaster> paramMaster) throws Exception {
		paramMasterDao.saveParameterMaster(paramMaster);
	}

	@Override
	@Transactional
	public List<ParameterMaster> getAllComponent(String query, BigDecimal languageId) {
		return paramMasterDao.getAllComponent(query,languageId);
	}

	@Override
	@Transactional
	public ParameterMaster viewById(String recordId) {
		return paramMasterDao.viewById(recordId);
	}

	@Override
	@Transactional
	public ParameterDefinition viewDefinitionById(String recordId) {
		return paramMasterDao.viewDefinitionById(recordId);
	}

	@Override
	@Transactional
	public List<ParameterDefinition> getAllRecords() {
		return paramMasterDao.getAllRecords();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(ParameterMaster saveParameterMaster, ParameterDefinition parameterDefinition)  throws Exception {
		paramMasterDao.save(saveParameterMaster,parameterDefinition);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveParameterMaster(ParameterMaster saveParameterMaster) throws Exception {
		paramMasterDao.saveParameterMaster(saveParameterMaster);
		
	}

	@Override
	@Transactional
	public List<ParameterMaster> viewDefinitionListforDetails(BigDecimal recordId) {
		return paramMasterDao.viewDefinitionListforDetails(recordId);
	}

	@Override
	@Transactional
	public ParameterDefinition viewByID(BigDecimal parameterDefinitionId) {
		return paramMasterDao.viewByID(parameterDefinitionId);
	}

	@Override
	@Transactional
	public List<ParameterDetails> getAllComponent(String query, BigDecimal parameterDefinitionId, BigDecimal parameterMasterId) {
		return paramMasterDao.getAllComponent(query,parameterDefinitionId,parameterMasterId);
	}

	@Override
	@Transactional
	public ParameterDefinition fetchFlexiName(BigDecimal parameterMasterId, String flexFieldName) {
		return paramMasterDao.fetchFlexiName(parameterMasterId,flexFieldName);
	}

	@Override
	@Transactional
	public List<ParameterDefinition> getFlexiList( BigDecimal parameterMasterId) {
		return paramMasterDao.getFlexiList(parameterMasterId);
	}

	@Override
	@Transactional
	public void saveDetails(ParameterDetails details) {
		 paramMasterDao.saveDetails(details);
	}

	@Override
	@Transactional
	public List<ParameterDetails> getRecords(BigDecimal parameterDefinitionId, BigDecimal parameterMasterId) {
		return paramMasterDao.getRecords(parameterDefinitionId,parameterMasterId);
	}

	@Override
	@Transactional
	public List<Object> getRecords(BigDecimal parameterDefinitionId, BigDecimal parameterMasterId, String flexFieldName) {
		return paramMasterDao.getRecords(parameterDefinitionId,parameterMasterId,flexFieldName);
	}

	@Override
	@Transactional
	public List<ParameterDetails> fetchAllRecords() {
		return paramMasterDao.fetchAllRecords();
	}

	@Override
	@Transactional
	public String approveRecord(BigDecimal parameterDetailsId, String userName) {
		return paramMasterDao.approveRecord(parameterDetailsId,userName);
	}

	@Override
	@Transactional
	public List<ParameterDetails> fetchAllunApprovedRecords() {
		return paramMasterDao.fetchAllunApprovedRecords();
	}

	@Override
	@Transactional
	public List<ParameterDefinition> fetchAllParameterDefinitionforView() {
		return paramMasterDao.fetchAllParameterDefinitionforView();
	}
	
	@Override
	@Transactional
	public List<ParameterDefinition> fetchAllParameterDefinitionforView(String recordId) {
		return paramMasterDao.fetchAllParameterDefinitionforView(recordId);
	}

	@Override
	@Transactional
	public BigDecimal getDetails(BigDecimal parameterDefinitionId, BigDecimal parameterMasterId, String flexFieldName, String fieldValue) {
		return paramMasterDao.getDetails(parameterDefinitionId, parameterMasterId, flexFieldName, fieldValue);
	}

	@Override
	@Transactional
	public ParameterDetails viewByDetailsID(BigDecimal detailsId) {
		return paramMasterDao.viewByDetailsID(detailsId);
	}

	@Override
	@Transactional
	public void delete(ParameterDetails parameterDetailsId) {
		paramMasterDao.delete(parameterDetailsId);
		
	}

	@Override
	@Transactional
	public String updateStaus(BigDecimal parameterDetailsId, String userName,String status,String remarks) {
		return paramMasterDao.updateStaus(parameterDetailsId,userName,status,remarks);
		
	}

	@Override
	@Transactional
	public List<ParameterDefinition> getnewFlexiList(BigDecimal parameterMasterId, BigDecimal parameterDefinitionId) {
		return paramMasterDao.getnewFlexiList(parameterMasterId,parameterDefinitionId);
	}

	@Override
	@Transactional
	public List<ParameterDetails> getRecordsbasedOnMasterId(BigDecimal parameterMasterId) {
		return paramMasterDao.getRecordsbasedOnMasterId(parameterMasterId);
	}

	@Override
	@Transactional
	public List<ParameterDetails> getAllDetailsComponent(String query, BigDecimal parameterMasterId) {
		return  paramMasterDao.getAllDetailsComponent(query,parameterMasterId);
	}

	@Override
	@Transactional
	public ParameterDetails populateDetails(BigDecimal parameterMasterId, String paramcodedefintion) {
		return paramMasterDao.populateDetails(parameterMasterId,paramcodedefintion);
	}

	@Override
	@Transactional
	public ParameterMaster viewParameterMasterById(BigDecimal parameterMasterId) {
		return paramMasterDao.viewParameterMasterById(parameterMasterId);
	}

	@Override
	@Transactional
	public List<String> getParameterCodeList(BigDecimal parameterMasterId) {
		return paramMasterDao.getParameterCodeList(parameterMasterId);
	}

	@Override
	@Transactional
	public String getFlexiFiledName(BigDecimal parameterMasterId, String parameterCode) {
		return paramMasterDao.getFlexiFiledName(parameterMasterId,parameterCode);
	}

	@Override
	@Transactional
	public List<String> getFlexiFieldfromDB(BigDecimal parameterMasterId) {
		return paramMasterDao.getFlexiFieldfromDB(parameterMasterId);
	}

	@Override
	@Transactional
	public List<AvailableDatabaseObjects> getDataBaseObjects() {
		return paramMasterDao.getDataBaseObjects();
	}

	@Override
	@Transactional
	public List<AvailableTableColumns> getTablecolumns(String tableName) {
		return paramMasterDao.getTablecolumns(tableName);
	}

	@Override
	@Transactional
	public ParameterDefinition isValueIndicatorEnabled(String fieldName, BigDecimal parameterMasterId) {
		return paramMasterDao.isValueIndicatorEnabled(fieldName,parameterMasterId);
	}

	@Override
	@Transactional
	public Boolean checkDatabaseValue(Object charValue, String tableName, String tableField,String flag) {
		return paramMasterDao.checkDatabaseValue(charValue,tableName,tableField,flag);
	}

	@Override
	@Transactional
	public List<ParameterDefinition> getFlexiFieldfromDBWithList(
			BigDecimal parameterMasterId) {
		return paramMasterDao.getFlexiFieldfromDBWithList(parameterMasterId);
	}

	@Override
	@Transactional
	public ParameterDefinition isValueIndicator(String fieldName, BigDecimal parameterMasterId) {
		return paramMasterDao.isValueIndicator(fieldName,parameterMasterId);
	}

	@Override
	@Transactional
	public List<BigDecimal> getValueList(String tableName, String columnName) {
		return paramMasterDao.getValueList(tableName,columnName);
	}

	@Override
	@Transactional
	public List<String> getStringValueList(String tableName, String columnName) {
		return paramMasterDao.getStringValueList(tableName,columnName);
	}

	@Override
	@Transactional
	public List<ParameterGrant> getGrantListfortheUser(String userName) {
		return paramMasterDao.getGrantListfortheUser(userName);
	}

	@Override
	@Transactional
	public List<ParameterMaster> getUserRightsRecord(ArrayList<String> grantRecordList) {
		return paramMasterDao.getUserRightsRecord(grantRecordList);
	}

	@Override
	@Transactional
	public List<ParameterDetails> fetchAllunApprovedRecordwithPermission(ArrayList<String> grantRecordList) {
		return paramMasterDao.fetchAllunApprovedRecordwithPermission(grantRecordList);
	}

	@Override
	@Transactional
	public boolean isExistInDatabase(BigDecimal parameterMasterId, String parameterCode) {
		return paramMasterDao.isExistInDatabase(parameterMasterId,parameterCode);
	}

	@Override
	@Transactional
	public ParameterDetails populateParamaterDetails(BigDecimal parameterMasterId, String paramcodedefintion,BigDecimal parameterDetailsId) {
		return paramMasterDao.populateParamaterDetails(parameterMasterId, paramcodedefintion, parameterDetailsId);
	}



}
