package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.AvailableDatabaseObjects;
import com.amg.exchange.common.model.AvailableTableColumns;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.remittance.model.ParameterDefinition;
import com.amg.exchange.remittance.model.ParameterMaster;
import com.amg.exchange.remittance.service.IParameterService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("parameterMasterandDefinitionBean")
@Scope("session")
public class ParameterMasterandDefinitionBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	Logger LOGGER = Logger.getLogger(ParameterMasterandDefinitionBean.class);
	SessionStateManage sessionmanage = new SessionStateManage();
	// variables declaration
	private BigDecimal parameterMasterDefinitionId;
	private BigDecimal codeLen;
	private String codeType;
	private String parameterFullDesc;
	private String recordId;
	private String parameterShortDesc;
	private String localCreatedBy;
	private Date localCreatedDate;
	private String localModifiedBy;
	private Date localModifiedDate;
	private String localIsActive;
	private String localDynamicStatus;
	private Boolean localCheckSave = false;
	private Boolean booRenderDataTable = false;
	private String errorMessage;
	private BigDecimal parameterMasterId;
	private String parameterId;
	/* private String recordId; */
	private String fieldName;
	private BigDecimal fieldLength;
	private String lparameterFullDesc;
	private String lparameterShortDesc;
	private String definitionFullDesc;
	private String definitionShortDesc;
	private String ldefinitionFullDesc;
	private String ldefinitionShortDesc;
	private Boolean booEnableValueFields;
	private Boolean booEnableTableFields;
	private boolean booEnableDefinitionTableFields;
	private String dynamicLabelForActivateDeactivate;
	private String flexFieldName;
	private boolean hideEdit;
	/*
	 * private String parameterFullDesc; private String parameterShortDesc;
	 */
	private String nullIndicator;
	private String validation;
	private BigDecimal valueFrom;
	private BigDecimal valueTo;
	private String tableName;
	private String tableField;
	private BigDecimal displayOrder;
	private Boolean booRecordIdOnly;
	private boolean ispopulate;
	private BigDecimal applicationCountryId;
	private String createdBy;
	private String modifiedBy;
	private Date modifiedDate;
	private Date createdDate;
	private String dcreatedBy;
	private String dmodifiedBy;
	private Date dmodifiedDate;
	private Date dcreatedDate;
	private String isActive;
	private String isDActive;
	private Boolean booRecordIdOnlyMasterData;
	// List Declaration
	List<ParameterMasterandDefinitionBeanDataTable> freshList = new ArrayList<ParameterMasterandDefinitionBeanDataTable>();
	List<ParameterMasterandDefinitionBeanDataTable> lstParamMastDB = new ArrayList<ParameterMasterandDefinitionBeanDataTable>();
	List<ParameterMasterandDefinitionBeanDataTable> lstFinalListForDataTable = new ArrayList<ParameterMasterandDefinitionBeanDataTable>();
	List<ParameterMaster> lstFetchedParamMastDB = new ArrayList<ParameterMaster>();
	List<ParameterMaster> lstCheckRecordIdExists = new ArrayList<ParameterMaster>();
	List<ParameterMaster> lstSaveAllToDB = new ArrayList<ParameterMaster>();
	List<ParameterDefinition> lstFetchedParamDefDB = new ArrayList<ParameterDefinition>();
	List<FlexField> flexFieldList = new ArrayList<FlexField>();


	List<AvailableDatabaseObjects> lstDatabaseObjects = new ArrayList<AvailableDatabaseObjects>();

	List<AvailableTableColumns> lstTablecolumns = new ArrayList<AvailableTableColumns>();

	// Mapping Services
	@Autowired
	IParameterService parameterService;




	public List<AvailableTableColumns> getLstTablecolumns() {
		return lstTablecolumns;
	}

	public void setLstTablecolumns(List<AvailableTableColumns> lstTablecolumns) {
		this.lstTablecolumns = lstTablecolumns;
	}

	public List<AvailableDatabaseObjects> getLstDatabaseObjects() {
		return lstDatabaseObjects;
	}

	public void setLstDatabaseObjects(List<AvailableDatabaseObjects> lstDatabaseObjects) {
		this.lstDatabaseObjects = lstDatabaseObjects;
	}

	public Boolean getBooRecordIdOnlyMasterData() {
		return booRecordIdOnlyMasterData;
	}

	public void setBooRecordIdOnlyMasterData(Boolean booRecordIdOnlyMasterData) {
		this.booRecordIdOnlyMasterData = booRecordIdOnlyMasterData;
	}

	// get and set methods
	public boolean isIspopulate() {
		return ispopulate;
	}

	public boolean isHideEdit() {
		return hideEdit;
	}

	public void setHideEdit(boolean hideEdit) {
		this.hideEdit = hideEdit;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsDActive() {
		return isDActive;
	}

	public void setIsDActive(String isDActive) {
		this.isDActive = isDActive;
	}

	public String getDcreatedBy() {
		return dcreatedBy;
	}

	public void setDcreatedBy(String dcreatedBy) {
		this.dcreatedBy = dcreatedBy;
	}

	public String getDmodifiedBy() {
		return dmodifiedBy;
	}

	public void setDmodifiedBy(String dmodifiedBy) {
		this.dmodifiedBy = dmodifiedBy;
	}

	public Date getDmodifiedDate() {
		return dmodifiedDate;
	}

	public void setDmodifiedDate(Date dmodifiedDate) {
		this.dmodifiedDate = dmodifiedDate;
	}

	@PostConstruct
	public void setError() {
		setErrorMessage("");
	}

	public boolean getBooEnableDefinitionTableFields() {
		return booEnableDefinitionTableFields;
	}

	public void setBooEnableDefinitionTableFields(boolean booEnableDefinitionTableFields) {
		this.booEnableDefinitionTableFields = booEnableDefinitionTableFields;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getDcreatedDate() {
		return dcreatedDate;
	}

	public void setDcreatedDate(Date dcreatedDate) {
		this.dcreatedDate = dcreatedDate;
	}

	public Boolean getBooEnableTableFields() {
		return booEnableTableFields;
	}

	public void setBooEnableTableFields(Boolean booEnableTableFields) {
		this.booEnableTableFields = booEnableTableFields;
	}

	public Boolean getBooEnableValueFields() {
		return booEnableValueFields;
	}

	public void setBooEnableValueFields(Boolean booEnableValueFields) {
		this.booEnableValueFields = booEnableValueFields;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public BigDecimal getParameterMasterDefinitionId() {
		return parameterMasterDefinitionId;
	}

	public void setParameterMasterDefinitionId(BigDecimal parameterMasterDefinitionId) {
		this.parameterMasterDefinitionId = parameterMasterDefinitionId;
	}

	public String getFlexFieldName() {
		return flexFieldName;
	}

	public void setFlexFieldName(String flexFieldName) {
		this.flexFieldName = flexFieldName;
	}

	public List<FlexField> getFlexFieldList() {
		return flexFieldList;
	}

	public void setFlexFieldList(List<FlexField> flexFieldList) {
		this.flexFieldList = flexFieldList;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public String getDefinitionFullDesc() {
		return definitionFullDesc;
	}

	public void setDefinitionFullDesc(String definitionFullDesc) {
		this.definitionFullDesc = definitionFullDesc;
	}

	public String getDefinitionShortDesc() {
		return definitionShortDesc;
	}

	public void setDefinitionShortDesc(String definitionShortDesc) {
		this.definitionShortDesc = definitionShortDesc;
	}

	public String getLdefinitionFullDesc() {
		return ldefinitionFullDesc;
	}

	public void setLdefinitionFullDesc(String ldefinitionFullDesc) {
		this.ldefinitionFullDesc = ldefinitionFullDesc;
	}

	public String getLdefinitionShortDesc() {
		return ldefinitionShortDesc;
	}

	public void setLdefinitionShortDesc(String ldefinitionShortDesc) {
		this.ldefinitionShortDesc = ldefinitionShortDesc;
	}

	public String getLparameterFullDesc() {
		return lparameterFullDesc;
	}

	public void setLparameterFullDesc(String lparameterFullDesc) {
		this.lparameterFullDesc = lparameterFullDesc;
	}

	public String getLparameterShortDesc() {
		return lparameterShortDesc;
	}

	public void setLparameterShortDesc(String lparameterShortDesc) {
		this.lparameterShortDesc = lparameterShortDesc;
	}

	public void setIspopulate(boolean ispopulate) {
		this.ispopulate = ispopulate;
	}

	public BigDecimal getCodeLen() {
		return codeLen;
	}

	public Boolean getBooRecordIdOnly() {
		return booRecordIdOnly;
	}

	public void setBooRecordIdOnly(Boolean booRecordIdOnly) {
		this.booRecordIdOnly = booRecordIdOnly;
	}

	public SessionStateManage getSessionmanage() {
		return sessionmanage;
	}

	public void setSessionmanage(SessionStateManage sessionmanage) {
		this.sessionmanage = sessionmanage;
	}

	public BigDecimal getParameterMasterId() {
		return parameterMasterId;
	}

	public void setParameterMasterId(BigDecimal parameterMasterId) {
		this.parameterMasterId = parameterMasterId;
	}

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public BigDecimal getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(BigDecimal fieldLength) {
		this.fieldLength = fieldLength;
	}

	public String getNullIndicator() {
		return nullIndicator;
	}

	public void setNullIndicator(String nullIndicator) {
		this.nullIndicator = nullIndicator;
	}

	public BigDecimal getValueFrom() {
		return valueFrom;
	}

	public void setValueFrom(BigDecimal valueFrom) {
		this.valueFrom = valueFrom;
	}

	public BigDecimal getValueTo() {
		return valueTo;
	}

	public void setValueTo(BigDecimal valueTo) {
		this.valueTo = valueTo;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableField() {
		return tableField;
	}

	public void setTableField(String tableField) {
		this.tableField = tableField;
	}

	public BigDecimal getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(BigDecimal displayOrder) {
		this.displayOrder = displayOrder;
	}

	public IParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(IParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setCodeLen(BigDecimal codeLen) {
		this.codeLen = codeLen;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getParameterFullDesc() {
		return parameterFullDesc;
	}

	public void setParameterFullDesc(String parameterFullDesc) {
		this.parameterFullDesc = parameterFullDesc;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getParameterShortDesc() {
		return parameterShortDesc;
	}

	public void setParameterShortDesc(String parameterShortDesc) {
		this.parameterShortDesc = parameterShortDesc;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public List<ParameterMasterandDefinitionBeanDataTable> getFreshList() {
		return freshList;
	}

	public void setFreshList(List<ParameterMasterandDefinitionBeanDataTable> freshList) {
		this.freshList = freshList;
	}

	public String getLocalCreatedBy() {
		return localCreatedBy;
	}

	public void setLocalCreatedBy(String localCreatedBy) {
		this.localCreatedBy = localCreatedBy;
	}

	public Date getLocalCreatedDate() {
		return localCreatedDate;
	}

	public void setLocalCreatedDate(Date localCreatedDate) {
		this.localCreatedDate = localCreatedDate;
	}

	public String getLocalIsActive() {
		return localIsActive;
	}

	public void setLocalIsActive(String localIsActive) {
		this.localIsActive = localIsActive;
	}

	public String getLocalDynamicStatus() {
		return localDynamicStatus;
	}

	public void setLocalDynamicStatus(String localDynamicStatus) {
		this.localDynamicStatus = localDynamicStatus;
	}

	public List<ParameterMasterandDefinitionBeanDataTable> getLstParamMastDB() {
		return lstParamMastDB;
	}

	public void setLstParamMastDB(List<ParameterMasterandDefinitionBeanDataTable> lstParamMastDB) {
		this.lstParamMastDB = lstParamMastDB;
	}

	public List<ParameterMaster> getLstFetchedParamMastDB() {
		return lstFetchedParamMastDB;
	}

	public void setLstFetchedParamMastDB(List<ParameterMaster> lstFetchedParamMastDB) {
		this.lstFetchedParamMastDB = lstFetchedParamMastDB;
	}

	public List<ParameterMaster> getLstCheckRecordIdExists() {
		return lstCheckRecordIdExists;
	}

	public void setLstCheckRecordIdExists(List<ParameterMaster> lstCheckRecordIdExists) {
		this.lstCheckRecordIdExists = lstCheckRecordIdExists;
	}

	public List<ParameterMasterandDefinitionBeanDataTable> getLstFinalListForDataTable() {
		return lstFinalListForDataTable;
	}

	public void setLstFinalListForDataTable(List<ParameterMasterandDefinitionBeanDataTable> lstFinalListForDataTable) {
		this.lstFinalListForDataTable = lstFinalListForDataTable;
	}

	public Boolean getLocalCheckSave() {
		return localCheckSave;
	}

	public void setLocalCheckSave(Boolean localCheckSave) {
		this.localCheckSave = localCheckSave;
	}

	public String getLocalModifiedBy() {
		return localModifiedBy;
	}

	public void setLocalModifiedBy(String localModifiedBy) {
		this.localModifiedBy = localModifiedBy;
	}

	public Date getLocalModifiedDate() {
		return localModifiedDate;
	}

	public void setLocalModifiedDate(Date localModifiedDate) {
		this.localModifiedDate = localModifiedDate;
	}

	public List<ParameterMaster> getLstSaveAllToDB() {
		return lstSaveAllToDB;
	}

	public void setLstSaveAllToDB(List<ParameterMaster> lstSaveAllToDB) {
		this.lstSaveAllToDB = lstSaveAllToDB;
	}

	// Methods Start
	// checking duplicates in record id in db and data table
	public void addtoDataTable() {
		
		
		try {
			LOGGER.info("Record id " + getRecordId());
			boolean check = false;
			if (!freshList.isEmpty()) {
				for (ParameterMasterandDefinitionBeanDataTable datatable : lstFinalListForDataTable) {
					if (datatable.getFlexFieldName() != null && datatable.getFlexFieldName().equals(getFlexFieldName())) {
						setErrorMessage("Record Id Already Exist");
						RequestContext.getCurrentInstance().execute("csp.show();");
						return;
					} else if (datatable.getParameterId() != null && datatable.getParameterId().equals(getParameterId())) {
						setErrorMessage("Parameter Code Already Exist");
						RequestContext.getCurrentInstance().execute("csp.show();");
						return;
					} else {
						check = true;
					}
				}
			} else {
				check = true;
			}
			if (check) {
				if (getValidation() != null && getValidation().equals("R")) {
					int result = getValueFrom().compareTo(getValueTo());
					LOGGER.info("value from and value to comparision");
					LOGGER.info("Result " + result);
					if (result == 0) {
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrorMessage("Value From and Value To both are equal");
						return;
					} else if (result == 1) {
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrorMessage("Value From is greater than Value To ");
						return;
					}
					LOGGER.info("value from and value to comparision ends here");
				}
				ParameterMasterandDefinitionBeanDataTable datatable = new ParameterMasterandDefinitionBeanDataTable();
				datatable.setRecordId(getRecordId());
				datatable.setParameterMasterId(getParameterMasterId());
				datatable.setParameterMasterDefinitionId(getParameterMasterDefinitionId());
				datatable.setLparameterFullDesc(getLparameterFullDesc());
				datatable.setLparameterShortDesc(getLparameterShortDesc());
				datatable.setParameterFullDesc(getParameterFullDesc());
				datatable.setParameterShortDesc(getParameterShortDesc());
				datatable.setCreatedBy(getCreatedBy());
				datatable.setCreatedDate(getCreatedDate());
				datatable.setDcreatedBy(getDcreatedBy());
				datatable.setDcreatedDate(getDcreatedDate());
				datatable.setModifiedBy(getModifiedBy());
				datatable.setModifiedDate(getModifiedDate());
				datatable.setDmodifiedBy(getDmodifiedBy());
				datatable.setDmodifiedDate(getDmodifiedDate());
				LOGGER.info("Application country " + sessionmanage.getCountryId());
				datatable.setApplicationCountryId(sessionmanage.getCountryId());
				if (getParameterMasterId() != null) {
					datatable.setModifiedBy(sessionmanage.getUserName());
					datatable.setModifiedDate(new Date());
					datatable.setDmodifiedBy(sessionmanage.getUserName());
					datatable.setDmodifiedDate(new Date());
					if (getParameterMasterDefinitionId() == null) {
						datatable.setDcreatedDate(new Date());
						datatable.setDcreatedBy(sessionmanage.getUserName());
					}
				} else {
					datatable.setCreatedDate(new Date());
					datatable.setCreatedBy(sessionmanage.getUserName());
					datatable.setDcreatedDate(new Date());
					datatable.setDcreatedBy(sessionmanage.getUserName());
					datatable.setDynamicLabelForActivateDeactivate("Remove");
				}
				datatable.setCodeLen(getCodeLen());
				datatable.setCodeType(getCodeType());
				datatable.setFlexFieldName(getFlexFieldName());
				datatable.setDisplayOrder(getDisplayOrder());
				datatable.setDefinitionFullDesc(getDefinitionFullDesc());
				datatable.setDefinitionShortDesc(getDefinitionShortDesc());
				datatable.setFieldLength(getFieldLength());
				datatable.setNullIndicator(getNullIndicator());
				datatable.setValidation(getValidation());
				datatable.setValueFrom(getValueFrom());
				datatable.setValueTo(getValueTo());
				datatable.setTableName(getTableName());
				datatable.setTableField(getTableField());
				datatable.setParameterId(getParameterId());
				datatable.setLdefinitionFullDesc(getLdefinitionFullDesc());
				datatable.setLdefinitionShortDesc(getLdefinitionShortDesc());
				// datatable.setDynamicLabelForActivateDeactivate("Delete");
				LOGGER.info("*************************************");
				LOGGER.info(datatable);
				LOGGER.info("*************************************");
				freshList.add(datatable);
				datatable = null;
				/* clearAll(); */
				clearDefinitionforAdd();
				setBooRenderDataTable(true);
				setHideEdit(false);
				lstFinalListForDataTable.addAll(freshList);
				freshList.clear();
				if (lstFinalListForDataTable.size() > 0) {
					setBooRecordIdOnly(true);
					setBooRecordIdOnlyMasterData(true);
				}
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocuured "+ e);
			return;
		}
	}

	/*
	 * // Adding to DataTable public void addToDataTable() { try {
	 * ParameterMasterDataTable parameterMaster = new
	 * ParameterMasterDataTable();
	 * parameterMaster.setParameterMasterId(getParameterMasterPK());
	 * parameterMaster.setRecordId(getRecordId());
	 * parameterMaster.setFullDesc(getParameterFullDesc());
	 * parameterMaster.setShortDesc(getParameterShortDesc());
	 * parameterMaster.setCodeLen(getCodeLen());
	 * parameterMaster.setCodeType(getCodeType()); if (getParameterMasterPK() !=
	 * null) { parameterMaster.setCreatedBy(getLocalCreatedBy());
	 * parameterMaster.setCreatedDate(getLocalCreatedDate());
	 * parameterMaster.setIsActive(getLocalIsActive());
	 * parameterMaster.setDynamicLabelForActivateDeactivate
	 * (getLocalDynamicStatus());
	 * parameterMaster.setCheckSave(getLocalCheckSave());
	 * parameterMaster.setModifiedBy(sessionmanage.getUserName());
	 * parameterMaster.setModifiedDate(new Date()); } else {
	 * parameterMaster.setCreatedBy(sessionmanage.getUserName());
	 * parameterMaster.setCreatedDate(new Date());
	 * parameterMaster.setIsActive(Constants.U);
	 * parameterMaster.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
	 * parameterMaster.setCheckSave(true); }
	 * lstParametersMaster.add(parameterMaster); // to show in Form
	 * lstFinalListForDataTable.add(parameterMaster); } catch (Exception e) { }
	 * }
	 */
	// main clear method
	private void clearDefinitionforAdd() {
		setLdefinitionFullDesc(null);
		setLdefinitionShortDesc(null);
		setDefinitionFullDesc(null);
		setDefinitionShortDesc(null);
		setDisplayOrder(null);
		setFieldLength(null);
		setNullIndicator(null);
		setValidation(null);
		setParameterId(null);
		setValueFrom(null);
		setValueTo(null);
		setTableField(null);
		setTableName(null);
		setDefinitionShortDesc(null);
		setDefinitionFullDesc(null);
		setParameterMasterDefinitionId(null);
		setFlexFieldName(null);
	}

	public void clearMaster() {
		setRecordId(null);
		setParameterFullDesc(null);
		setParameterShortDesc(null);
		setCodeLen(null);
		setCodeType(null);
		setLparameterFullDesc(null);
		setLparameterShortDesc(null);
		setBooRecordIdOnly(null);
	}

	public void cleariwthMaster() {
		clearAll();
		clearMaster();
		renderingRecordsId();
	}
	
	public void renderingRecordsId(){
		setRenderRecDropDown(true);
		setRenderRecEditable(false);
		setRenderedit(true);
	}

	public void clearAll() {
		setBooRenderDataTable(false);
		setLdefinitionFullDesc(null);
		setLdefinitionShortDesc(null);
		setDefinitionFullDesc(null);
		setDefinitionShortDesc(null);
		setFlexFieldName(null);
		setDisplayOrder(null);
		setFieldLength(null);
		setNullIndicator(null);
		setValidation(null);
		setParameterId(null);
		setValueFrom(null);
		setValueTo(null);
		setTableField(null);
		setTableName(null);
		setDefinitionShortDesc(null);
		setDefinitionFullDesc(null);
		setParameterMasterDefinitionId(null);
		/* setBoodropRecordIdOnly(null); */
	}

	public void clearAllExceptRecordID() {
		setParameterFullDesc(null);
		setParameterShortDesc(null);
		setCodeLen(null);
		setCodeType(null);
		setLdefinitionFullDesc(null);
		setLdefinitionShortDesc(null);
		setDefinitionFullDesc(null);
		setDefinitionShortDesc(null);
		setFlexFieldName(null);
		setDisplayOrder(null);
		setFieldLength(null);
		setNullIndicator(null);
		setValidation(null);
		setParameterId(null);
		setValueFrom(null);
		setValueTo(null);
		setTableField(null);
		setTableName(null);
		setDefinitionShortDesc(null);
		setDefinitionFullDesc(null);
		setLparameterFullDesc(null);
		setLparameterShortDesc(null);
		setParameterMasterId(null);
		setParameterMasterDefinitionId(null);
		/* setBoodropRecordIdOnly(null); */
	}

	public void clearDefinition() {
		setLdefinitionFullDesc(null);
		setLdefinitionShortDesc(null);
		setDefinitionFullDesc(null);
		setDefinitionShortDesc(null);
		setDisplayOrder(null);
		setFieldLength(null);
		setNullIndicator(null);
		setValidation(null);
		setParameterId(null);
		setValueFrom(null);
		setValueTo(null);
		setTableField(null);
		setTableName(null);
		setDefinitionShortDesc(null);
		setDefinitionFullDesc(null);
		setParameterMasterDefinitionId(null);
	}

	// clear after adding to DataTable
	public void clearAfterAdd() {
		setRecordId(null);
		setParameterFullDesc(null);
		setParameterShortDesc(null);
		setCodeLen(null);
		setCodeType(null);
		/* setParameterMasterPK(null); */
	}

	// on click of edit button in Data Table
	public void editRecord(ParameterMasterandDefinitionBeanDataTable datatable) {
		try {
			
			if (datatable != null) {
				setHideEdit(true);
				setBooRecordIdOnly(true);
				setParameterMasterId(datatable.getParameterMasterId());
				setRecordId(datatable.getRecordId());
				setParameterFullDesc(datatable.getParameterFullDesc());
				setParameterShortDesc(datatable.getParameterShortDesc());
				setCodeLen(datatable.getCodeLen());
				setCodeType(datatable.getCodeType());
				setLparameterFullDesc(datatable.getLparameterFullDesc());
				setLparameterShortDesc(datatable.getLparameterShortDesc());
				setCreatedBy(datatable.getCreatedBy());
				setCreatedDate(datatable.getCreatedDate());
				setModifiedBy(datatable.getModifiedBy());
				setModifiedDate(datatable.getModifiedDate());
				setApplicationCountryId(datatable.getApplicationCountryId());
				if (datatable.getFlexFieldName() != null) {
					setBooEnableDefinitionTableFields(false);
					setParameterMasterDefinitionId(datatable.getParameterMasterDefinitionId());
					setFlexFieldName(datatable.getFlexFieldName());
					if (datatable.getParameterMasterDefinitionId() != null) {
						setParameterMasterDefinitionId(datatable.getParameterMasterDefinitionId());
					}
					setFieldLength(datatable.getFieldLength());
					setDefinitionFullDesc(datatable.getDefinitionFullDesc());
					setDefinitionShortDesc(datatable.getDefinitionShortDesc());
					setNullIndicator(datatable.getNullIndicator());
					setValidation(datatable.getValidation());
					if (datatable.getValueFrom() != null) {
						setValueFrom(datatable.getValueFrom());
					} else {
						setValueFrom(null);
					}
					if (datatable.getValueTo() != null) {
						setValueTo(datatable.getValueTo());
						setBooEnableValueFields(false);
						/* setBooEnableTableFields(true); */
					} else {
						setValueTo(null);
						setBooEnableValueFields(true);
						/* setBooEnableTableFields(false); */
						setTableName(datatable.getTableName());
						setTableField(datatable.getTableField());
					}
					/*
					 * setTableName(datatable.getTableName());
					 * setTableField(datatable.getTableField());
					 */
					if (datatable.getTableName() != null) {
						setBooEnableTableFields(false);
					} else {
						setBooEnableTableFields(true);
					}
					setParameterId(datatable.getParameterId());
					setDisplayOrder(datatable.getDisplayOrder());
					setIsActive(Constants.Yes);
					setDcreatedBy(datatable.getDcreatedBy());
					setDcreatedDate(datatable.getDcreatedDate());
					setModifiedBy(datatable.getDmodifiedBy());
					setModifiedDate(datatable.getDmodifiedDate());
					setLdefinitionFullDesc(datatable.getLdefinitionFullDesc());
					setLdefinitionShortDesc(datatable.getLdefinitionShortDesc());
					/* setBoodropRecordIdOnly(true); */
				}
				lstFinalListForDataTable.remove(datatable);
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocuured "+ e);
			return;
		}
	}

	// on click of view, viewing all records from DataBase
	public void view() {
		try {
			lstFetchedParamDefDB.clear();
			lstFinalListForDataTable.clear();
			lstParamMastDB.clear();
			LOGGER.info("lstFetchedParamDefDB" + lstFetchedParamDefDB.size());
			LOGGER.info("lstFinalListForDataTable" + lstFinalListForDataTable.size());
			// lstFetchedParamMastDB = parameterService.fetchAllParameterMasterForView();
			// blocked due to fetch based on record id
			//lstFetchedParamDefDB = parameterService.fetchAllParameterDefinitionforView();
			lstFetchedParamDefDB = parameterService.fetchAllParameterDefinitionforView(getRecordId());
			/* ParameterDefinition parameterDefinition = null; */
			setHideEdit(false);
			if (lstFetchedParamDefDB.size() != 0) {
				for (ParameterDefinition lstPMDB : lstFetchedParamDefDB) {
					ParameterMasterandDefinitionBeanDataTable datatable = new ParameterMasterandDefinitionBeanDataTable();
					datatable.setParameterMasterId(lstPMDB.getParameterMasterId().getParameterMasterId());
					datatable.setRecordId(lstPMDB.getRecordId());
					datatable.setParameterFullDesc(lstPMDB.getFullDesc());
					datatable.setParameterShortDesc(lstPMDB.getShortDesc());
					datatable.setCodeLen(lstPMDB.getParameterMasterId().getCodeLen());
					datatable.setCodeType(lstPMDB.getParameterMasterId().getCodeType());
					datatable.setCreatedBy(lstPMDB.getCreatedBy());
					datatable.setCreatedDate(lstPMDB.getCreatedDate());
					datatable.setModifiedBy(lstPMDB.getModifiedBy());
					datatable.setModifiedDate(lstPMDB.getModifiedDate());
					datatable.setIsActive(lstPMDB.getIsActive());
					datatable.setLparameterFullDesc(lstPMDB.getLocalFullDesc());
					datatable.setLparameterShortDesc(lstPMDB.getLocalShortDesc());
					datatable.setApplicationCountryId(lstPMDB.getParameterMasterId().getApplicationCountry().getCountryId());
					LOGGER.info("Application country" + lstPMDB.getParameterMasterId().getApplicationCountry().getCountryId());

					datatable.setParameterMasterDefinitionId(lstPMDB.getParameterDefinitionId());
					datatable.setFieldName(lstPMDB.getFieldName());
					datatable.setFieldLength(lstPMDB.getFieldLength());
					datatable.setDefinitionFullDesc(lstPMDB.getFullDesc());
					datatable.setDefinitionShortDesc(lstPMDB.getShortDesc());
					datatable.setNullIndicator(lstPMDB.getNullIndication());
					datatable.setValidation(lstPMDB.getValueIndicator());
					if (lstPMDB.getValueFrom() != null) {
						datatable.setValueFrom(new BigDecimal(lstPMDB.getValueFrom()));
					} else {
						datatable.setValueFrom(null);
					}
					if (lstPMDB.getValueFrom() != null) {
						datatable.setValueTo(new BigDecimal(lstPMDB.getValueTo()));
					} else {
						datatable.setValueTo(null);
					}
					datatable.setFlexFieldName(lstPMDB.getFieldName());
					datatable.setTableName(lstPMDB.getTableName());
					datatable.setTableField(lstPMDB.getTableField());
					datatable.setParameterId(lstPMDB.getParameterId());
					datatable.setDisplayOrder(lstPMDB.getDisplayOrder());
					datatable.setIsActive(lstPMDB.getIsActive());
					datatable.setDcreatedBy(lstPMDB.getCreatedBy());
					datatable.setDcreatedDate(lstPMDB.getCreatedDate());
					datatable.setDmodifiedBy(lstPMDB.getModifiedBy());
					datatable.setDmodifiedDate(lstPMDB.getModifiedDate());
					datatable.setLdefinitionFullDesc(lstPMDB.getLocalFullDesc());
					datatable.setLdefinitionShortDesc(lstPMDB.getLocalShortDesc());
					
					LOGGER.info("*************************");
					LOGGER.info(datatable);
					LOGGER.info("*************************");
					lstParamMastDB.add(datatable);
				}
			}
			if (lstFetchedParamDefDB.size() != 0) {
				lstFinalListForDataTable.addAll(lstParamMastDB);
				/* lstFinalListForDataTable.addAll(lstParametersMaster); */
				setBooRenderDataTable(true);
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocuured "+ e);
			return;
		}
	}

	// save to DataBase
	public void saveToDataBase() {
		boolean check = false;
		ParameterMaster saveParameterMaster = null;
		ParameterDefinition parameterDefinition = null;
		CountryMaster applicationCountry = null;
		saveParameterMaster = new ParameterMaster();
		for (ParameterMasterandDefinitionBeanDataTable datatable : lstFinalListForDataTable) {
			LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			LOGGER.info(datatable);
			LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			/*
			 * saveParameterMaster =
			 * parameterService.viewById(datatable.getRecordId());
			 * parameterDefinition =
			 * parameterService.viewDefinitionById(datatable.getRecordId());
			 */
			LOGGER.info("Record id " + datatable.getRecordId());
			if (datatable.getParameterMasterId() != null) {
				saveParameterMaster.setParameterMasterId(datatable.getParameterMasterId());
			}
			saveParameterMaster.setRecordId(datatable.getRecordId());
			saveParameterMaster.setFullDesc(datatable.getParameterFullDesc());
			saveParameterMaster.setShortDesc(datatable.getParameterShortDesc());
			saveParameterMaster.setCodeLen(datatable.getCodeLen());
			saveParameterMaster.setCodeType(datatable.getCodeType());
			saveParameterMaster.setLocalFullDesc(datatable.getLparameterFullDesc());
			saveParameterMaster.setLocalShortDesc(datatable.getLparameterShortDesc());
			saveParameterMaster.setIsActive(Constants.Yes);
			saveParameterMaster.setCreatedBy(datatable.getCreatedBy());
			saveParameterMaster.setCreatedDate(datatable.getCreatedDate());
			saveParameterMaster.setModifiedBy(datatable.getModifiedBy());
			saveParameterMaster.setModifiedDate(datatable.getModifiedDate());
			LOGGER.info("Application country" + datatable.getApplicationCountryId());
			LOGGER.info("Application country" + datatable.getApplicationCountryId());
			applicationCountry = new CountryMaster();
			applicationCountry.setCountryId(datatable.getApplicationCountryId());
			saveParameterMaster.setApplicationCountry(applicationCountry);
			if (datatable.getFlexFieldName() != null) {
				parameterDefinition = new ParameterDefinition();
				if (datatable.getParameterMasterDefinitionId() != null) {
					parameterDefinition.setParameterDefinitionId(datatable.getParameterMasterDefinitionId());
				}
				parameterDefinition.setParameterMasterId(saveParameterMaster);
				parameterDefinition.setRecordId(datatable.getRecordId());
				parameterDefinition.setFieldName(datatable.getFlexFieldName());
				parameterDefinition.setFieldLength(datatable.getFieldLength());
				parameterDefinition.setFullDesc(datatable.getDefinitionFullDesc());
				parameterDefinition.setShortDesc(datatable.getDefinitionShortDesc());
				parameterDefinition.setNullIndication(datatable.getNullIndicator());
				parameterDefinition.setValueIndicator(datatable.getValidation());
				if (datatable.getValueFrom() != null) {
					parameterDefinition.setValueFrom(String.valueOf(datatable.getValueFrom()));
				} else {
					parameterDefinition.setValueFrom(null);
				}
				if (datatable.getValueTo() != null) {
					parameterDefinition.setValueTo(String.valueOf(datatable.getValueTo()));
				} else {
					parameterDefinition.setValueTo(null);
				}
				parameterDefinition.setTableName(datatable.getTableName());
				parameterDefinition.setTableField(datatable.getTableField());
				parameterDefinition.setParameterId(datatable.getParameterId());
				parameterDefinition.setDisplayOrder(datatable.getDisplayOrder());
				parameterDefinition.setIsActive(Constants.Yes);
				parameterDefinition.setCreatedBy(datatable.getDcreatedBy());
				parameterDefinition.setCreatedDate(datatable.getDcreatedDate());
				parameterDefinition.setModifiedBy(datatable.getDmodifiedBy());
				parameterDefinition.setModifiedDate(datatable.getDmodifiedDate());
				parameterDefinition.setLocalFullDesc(datatable.getLdefinitionFullDesc());
				parameterDefinition.setLocalShortDesc(datatable.getLdefinitionShortDesc());
			}
			try {
				if (datatable.getFlexFieldName() != null) {
					parameterService.save(saveParameterMaster, parameterDefinition);
					saveParameterMaster.setParameterMasterId(saveParameterMaster.getParameterMasterId());
				} else {
					parameterService.saveParameterMaster(saveParameterMaster);
				}
				check = true;
				applicationCountry = null;
			} catch (Exception e) {
				setErrorMessage("Exception occured" + e.getMessage());
				LOGGER.info("*****************************************************" + getErrorMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
				LOGGER.info(e.getMessage());
				LOGGER.info(e.getMessage());
				LOGGER.info(e.getMessage());
				clearAll();
			}
			parameterDefinition = null;
		}
		if (check) {
			setErrorMessage("Record saved successfully");
			LOGGER.info("*****************************************************" + getErrorMessage());
			RequestContext.getCurrentInstance().execute("success.show();");
			clearAll();
			lstFinalListForDataTable.clear();
			setBooRenderDataTable(false);
			setBooEnableDefinitionTableFields(true);
			setBooEnableTableFields(true);
			setBooEnableValueFields(true);
			clearList();
		}
	}

	public void clearList() {
		freshList.clear();
		lstParamMastDB.clear();
		lstFetchedParamMastDB.clear();
		lstFinalListForDataTable.clear();
		lstCheckRecordIdExists.clear();
		lstSaveAllToDB.clear();
	}
	
	public void clearLists()
	{
		lstFinalListForDataTable.clear();
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {
		cleariwthMaster();
		addflexiColumninList();
		clearLists();
		setBooEnableValueFields(true);
		setBooEnableTableFields(true);
		setBooEnableDefinitionTableFields(true);
		fetchAllRecordsIdDB();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionmanage.getCountryId(), sessionmanage.getUserType(), sessionmanage.getUserName(), "parametermasteranddefinition.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/parametermasteranddefinition.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void validationCheck() {
		if (getValidation() != null && getValidation().equals("R")) {
			setBooEnableValueFields(false);
			setBooEnableTableFields(true);
			setTableField(null);
			setTableName(null);
		} else if (getValidation() != null && getValidation().equals("D")) {
			try{
				lstDatabaseObjects = parameterService.getDataBaseObjects();
			}
			catch(Exception e)
			{
				setErrorMessage("AvailableDatabaseObjects " + e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
				setValidation(null);
				return;
			}
			setBooEnableValueFields(true);
			setBooEnableTableFields(false);
			setValueFrom(null);
			setValueTo(null);
			setParameterId(null);

			LOGGER.info("lstDatabaseObjects" + lstDatabaseObjects.size());
		} else if (getValidation() != null && getValidation().equals("N")) {
			setBooEnableValueFields(true);
			setBooEnableTableFields(true);
			setValueFrom(null);
			setValueTo(null);
			setTableField(null);
			setTableName(null);
			setParameterId(null);
		}
	}

	private void addflexiColumninList() {
		FlexField temp = null;
		for (int i = 1; i <= 9; i++) {
			temp = new FlexField();
			temp.setColumnName("CHAR_FIELD" + i);
			flexFieldList.add(temp);
			temp = null;
		}
		for (int i = 1; i <= 9; i++) {
			temp = new FlexField();
			temp.setColumnName("DATE_FIELD" + i);
			flexFieldList.add(temp);
			temp = null;
		}
		for (int i = 1; i <= 9; i++) {
			temp = new FlexField();
			temp.setColumnName("NUMERIC_FIELD" + i);
			flexFieldList.add(temp);
			temp = null;
		}
	}

	// TO POPULATE THE RELATION CODE BASED ON GIVEN ENTRY
	public List<String> autoCompleteData(String query) {
		if (query.length() > 0) {
			List<ParameterMaster> ParameterList = parameterService.getAllComponent(query, sessionmanage.getLanguageId());
			List<String> list = new ArrayList<String>();
			for (ParameterMaster parameter : ParameterList) {
				list.add(parameter.getRecordId());
			}
			return list;
		} else {
			return null;
		}
	}

	public void populateParameterMaster() {
		LOGGER.info("Enter into populateParameterMaster method : ParameterMasterandDefinitionBean ");
		LOGGER.info("RecordId  " + getRecordId());
		ispopulate = true;
		ParameterMaster parameter = null;
		ParameterDefinition parameterDefinition = null;
		if (getRecordId() != null && !getRecordId().equals("") && !getRecordId().equals("0")) {
			parameter = parameterService.viewById(getRecordId());
			LOGGER.info("parameter is null" + parameter == null);
			if (parameter != null) {
				setParameterFullDesc(parameter.getFullDesc());
				setParameterShortDesc(parameter.getShortDesc());
				setCodeLen(parameter.getCodeLen());
				setCodeType(parameter.getCodeType());
				setLparameterFullDesc(parameter.getLocalFullDesc());
				setLparameterShortDesc(parameter.getLocalShortDesc());
				setParameterMasterId(parameter.getParameterMasterId());
				setCreatedDate(parameter.getCreatedDate());
				setModifiedDate(parameter.getModifiedDate());
				setModifiedBy(parameter.getModifiedBy());
				setCreatedBy(parameter.getCreatedBy());
				LOGGER.info("parameterDefinition is null " + parameterDefinition == null);
				/*
				 * parameterDefinition =
				 * parameterService.viewDefinitionById(getRecordId()); if
				 * (parameterDefinition != null) {
				 * LOGGER.info("****parameterDefinition.getFullDesc()****" +
				 * parameterDefinition.getFullDesc());
				 * setParameterMasterDefinitionId
				 * (parameterDefinition.getParameterDefinitionId());
				 * setParameterMasterId(parameter.getParameterMasterId());
				 * setDisplayOrder(parameterDefinition.getDisplayOrder());
				 * setDefinitionFullDesc(parameterDefinition.getFullDesc());
				 * setDefinitionShortDesc(parameterDefinition.getShortDesc());
				 * setFieldLength(parameterDefinition.getFieldLength());
				 * setFieldName(parameterDefinition.getFieldName());
				 * setNullIndicator(parameterDefinition.getNullIndication());
				 * setParameterId(parameterDefinition.getParameterId());
				 * setTableName(parameterDefinition.getTableName());
				 * setTableField(parameterDefinition.getTableField());
				 * setLdefinitionFullDesc
				 * (parameterDefinition.getLocalFullDesc());
				 * setLdefinitionShortDesc
				 * (parameterDefinition.getLocalShortDesc());
				 * setDefinitionFullDesc(parameterDefinition.getFullDesc());
				 * setDefinitionShortDesc(parameterDefinition.getShortDesc());
				 * setValidation(parameterDefinition.getValueIndicator());
				 * setFlexFieldName(parameterDefinition.getFieldName());
				 * setDcreatedBy(parameterDefinition.getCreatedBy());
				 * setDcreatedDate(parameterDefinition.getCreatedDate());
				 * setDmodifiedBy(parameterDefinition.getModifiedBy());
				 * setDmodifiedDate(parameterDefinition.getModifiedDate());
				 * setBooEnableDefinitionTableFields(false); if
				 * (parameterDefinition.getValueFrom() != null) {
				 * setValueFrom(new
				 * BigDecimal(parameterDefinition.getValueFrom()));
				 * setBooEnableValueFields(false); } if
				 * (parameterDefinition.getValueTo() != null) { setValueTo(new
				 * BigDecimal(parameterDefinition.getValueTo())); }
				 * 
				 * setBoodropRecordIdOnly(true); }
				 */
			} else {
				clearAllExceptRecordID();
				setBooEnableDefinitionTableFields(true);
				setBooEnableValueFields(true);
				setBooEnableTableFields(true);
			}
		}else {
			clearAllExceptRecordID();
			setBooEnableDefinitionTableFields(true);
			setBooEnableValueFields(true);
			setBooEnableTableFields(true);
		}
		LOGGER.info("Exit into populateParameterMaster method : ParameterMasterandDefinitionBean ");
	}

	public class FlexField {
		private String columnName;

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
	}

	public void enableDefintionFields() {
		LOGGER.info(getFlexFieldName());
		LOGGER.info(getFlexFieldName());
		LOGGER.info(getFlexFieldName() == null);
		if (getFlexFieldName() != null) {
			if (!lstFinalListForDataTable.isEmpty()) {
				for (ParameterMasterandDefinitionBeanDataTable datatable : lstFinalListForDataTable) {
					if (datatable.getFlexFieldName() != null && datatable.getFlexFieldName().equals(getFlexFieldName())) {
						setFlexFieldName(null);
						setErrorMessage("FlexiField Name Already Exist");
						RequestContext.getCurrentInstance().execute("csp.show();");
						return;
					}
				}
			}
			setBooEnableDefinitionTableFields(false);
			ParameterDefinition parameterDefinition = null;
			parameterDefinition = parameterService.fetchFlexiName(getParameterMasterId(), getFlexFieldName());
			if (parameterDefinition != null) {
				LOGGER.info("****parameterDefinition.getFullDesc()****" + parameterDefinition.getFullDesc());
				setParameterMasterDefinitionId(parameterDefinition.getParameterDefinitionId());
				setParameterMasterId(getParameterMasterId());
				setDisplayOrder(parameterDefinition.getDisplayOrder());
				setDefinitionFullDesc(parameterDefinition.getFullDesc());
				setDefinitionShortDesc(parameterDefinition.getShortDesc());
				setFieldLength(parameterDefinition.getFieldLength());
				setFieldName(parameterDefinition.getFieldName());
				setNullIndicator(parameterDefinition.getNullIndication());
				setParameterId(parameterDefinition.getParameterId());
				setTableName(parameterDefinition.getTableName());
				if(parameterDefinition.getTableName()!=null)
				{
					lstDatabaseObjects = parameterService.getDataBaseObjects();
					enableTableField();
				}
				setTableField(parameterDefinition.getTableField());
				setLdefinitionFullDesc(parameterDefinition.getLocalFullDesc());
				setLdefinitionShortDesc(parameterDefinition.getLocalShortDesc());
				setDefinitionFullDesc(parameterDefinition.getFullDesc());
				setDefinitionShortDesc(parameterDefinition.getShortDesc());
				setValidation(parameterDefinition.getValueIndicator());
				setFlexFieldName(parameterDefinition.getFieldName());
				setDcreatedBy(parameterDefinition.getCreatedBy());
				setDcreatedDate(parameterDefinition.getCreatedDate());
				setDmodifiedBy(parameterDefinition.getModifiedBy());
				setDmodifiedDate(parameterDefinition.getModifiedDate());
				setBooEnableDefinitionTableFields(false);
				if (parameterDefinition.getValueFrom() != null) {
					setValueFrom(new BigDecimal(parameterDefinition.getValueFrom()));
					setBooEnableValueFields(false);
				}
				if (parameterDefinition.getValueTo() != null) {
					setValueTo(new BigDecimal(parameterDefinition.getValueTo()));
				}
				if (parameterDefinition.getTableName() != null) {
					setBooEnableTableFields(false);
				}
				// setBoodropRecordIdOnly(true);
			} else {
				LOGGER.info("###########################################################");
				clearDefinition();
				/* setBoodropRecordIdOnly(false); */
				setBooEnableDefinitionTableFields(false);
				LOGGER.info("###########################################################");
			}
		}
		if (getFlexFieldName() == null) {
			setBooEnableDefinitionTableFields(true);
		}
		/*
		 * LOGGER.info(
		 * "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" +
		 * getBooEnableDefinitionTableFields()); LOGGER.info(
		 * "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" +
		 * getBooEnableDefinitionTableFields()); LOGGER.info(
		 * "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" +
		 * getBooEnableDefinitionTableFields());
		 * 
		 * if(getBooEnableDefinitionTableFields()) {
		 * 
		 * }
		 */
		/*
		 * else { setBooEnableDefinitionTableFields(true); }
		 */
	}

	public void clickOnExit() throws IOException {
		clearMaster();
		clearAll();
		clearList();
		if (sessionmanage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void removefromDataTable(ParameterMasterandDefinitionBeanDataTable datatable) {
		lstFinalListForDataTable.remove(datatable);
	}

	public void isParameterCodeExist() {
		System.out.println(lstFinalListForDataTable.size());
		if (!lstFinalListForDataTable.isEmpty()) {
			for (ParameterMasterandDefinitionBeanDataTable datatable : lstFinalListForDataTable) {
				if (datatable.getParameterId() != null && datatable.getParameterId().equals(getParameterId())) {
					setParameterId(null);
					setErrorMessage("Parameter Code Already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
					return;
				}
			}
		}
	}

	public void enableTableField()
	{
		LOGGER.info("Entering into enableTableField method");
		LOGGER.info("Table Name " + getTableName());
		try {
			lstTablecolumns = parameterService.getTablecolumns(getTableName());
		} catch (Exception e) {
			setErrorMessage("AvailableTableColumns " + e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			setTableName(null);
			return;
		}
		LOGGER.info("Entering into enableTableField method");
	}
	
	// variables
	private boolean renderRecEditable;
	private boolean renderRecDropDown;
	private boolean renderedit;
	List<ParameterMaster> parameterRecordIdList = new ArrayList<ParameterMaster>();
	
	public boolean isRenderRecEditable() {
		return renderRecEditable;
	}
	public void setRenderRecEditable(boolean renderRecEditable) {
		this.renderRecEditable = renderRecEditable;
	}

	public boolean isRenderRecDropDown() {
		return renderRecDropDown;
	}
	public void setRenderRecDropDown(boolean renderRecDropDown) {
		this.renderRecDropDown = renderRecDropDown;
	}

	public boolean isRenderedit() {
		return renderedit;
	}
	public void setRenderedit(boolean renderedit) {
		this.renderedit = renderedit;
	}

	public List<ParameterMaster> getParameterRecordIdList() {
		return parameterRecordIdList;
	}
	public void setParameterRecordIdList(List<ParameterMaster> parameterRecordIdList) {
		this.parameterRecordIdList = parameterRecordIdList;
	}

	// record id making into drop down and enterable and edit option
	public void fetchAllRecordsIdDB(){
		
		if(parameterRecordIdList != null && parameterRecordIdList.size() != 0){
			parameterRecordIdList.clear();
		}
		
		List<ParameterMaster> recordIdList = parameterService.fetchAllParameterMasterForView();
		if(recordIdList.size() != 0){
			parameterRecordIdList.addAll(recordIdList);
		}else{
			parameterRecordIdList.clear();
		}
	}
	
	//edit of record id
	public void editableRecordId(){
		clearAll();
		clearMaster();
		if(isRenderRecDropDown()){
			setRenderRecDropDown(false);
			setRenderRecEditable(true);
		}else{
			fetchAllRecordsIdDB();
			setRenderRecDropDown(true);
			setRenderRecEditable(false);
		}
	}
	
}
