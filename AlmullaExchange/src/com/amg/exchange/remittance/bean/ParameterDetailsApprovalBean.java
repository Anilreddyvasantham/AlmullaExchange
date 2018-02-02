package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.remittance.model.ParameterDefinition;
import com.amg.exchange.remittance.model.ParameterDetails;
import com.amg.exchange.remittance.model.ParameterGrant;
import com.amg.exchange.remittance.model.ParameterMaster;
import com.amg.exchange.remittance.service.IParameterService;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("parameterDetailspprovlBean")
@Scope("session")
public class ParameterDetailsApprovalBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(ParameterDetailsBean.class);
	// variables declaration
	Logger LOGGER = Logger.getLogger(ParameterDetailsBean.class);
	private BigDecimal parameterDefinitionId;
	private BigDecimal parameterMasterId;
	private String recordId;
	private String paramcodedefintion;
	private String parameterFullDesc;
	private String parameterShortDesc;
	private String arabicFullDesc;
	private String arabicShortDesc;
	private BigDecimal numericField;
	/*private String charField;*/
	private String dataField;
	private String detailRecordId;
	private boolean ispopulate;
	private String flexFieldName;
	private String flexiFieldValue;
	List<ParameterDefinition> lstDefinitionrMaster = new ArrayList<ParameterDefinition>();
	List<ParameterMaster> lstParameterMaster = new ArrayList<ParameterMaster>();
	List<FlexField> flexFieldList = new ArrayList<FlexField>();
	private Boolean booRenderDataTable = false;
	List<ParameterDetailsDataTable> finalList = new ArrayList<ParameterDetailsDataTable>();
	private boolean hideEdit;
	private Boolean renderEditButton;
	
	private BigDecimal parameterDetailsId;
	private BigDecimal numericfield1;
	private BigDecimal numericfield2;
	private BigDecimal numericfield3;
	private BigDecimal numericfield4;
	private BigDecimal numericfield5;
	private BigDecimal numericfield6;
	private BigDecimal numericfield7;
	private BigDecimal numericfield8;
	private BigDecimal numericfield9;
	private String charfield1;
	private String charfield2;
	private String charfield3;
	private String charfield4;
	private String charfield5;
	private String charfield6;
	private String charfield7;
	private String charfield8;
	private String charfield9;
	private Date datefield1;
	private Date datefield2;
	private Date datefield3;
	private Date datefield4;
	private Date datefield5;
	private Date datefield6;
	private Date datefield7;
	private Date datefield8;
	private Date datefield9;
	
	
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String dynamicLabelForActivateDeactivate;
	
	private ParameterDetailsDataTable selectedDocument;
	
	private Boolean booradonlyforMasterDesciption;
	
	
	
	
	SessionStateManage session = new SessionStateManage();
	
	
	
	@Autowired
	IParameterService parameterService;

	// get and set methods
	
	
	private String parameterMasterDescription;
	private Boolean permanetDeleteCheck;
	private Boolean remarksCheck;
	private String errorMessage;
	private Boolean basedOnFlexiList;
	private Boolean basedOnDate;
	private Date dateFlexiValue;
	private Boolean booDefintionReadOnly;
	private Boolean booRenderChar;
	private Boolean booRenderNumber;
	private Boolean booRenderDate;
	private String charValue;
	private BigDecimal numberValue;
	private Date dateValue;
	private String parameterCode;
	
	
	List<ParameterDetailsDataTable> lstFinalListForDataTable = new ArrayList<ParameterDetailsDataTable>();
	
	
	
	
	
	public String getParameterMasterDescription() {
		return parameterMasterDescription;
	}

	public void setParameterMasterDescription(String parameterMasterDescription) {
		this.parameterMasterDescription = parameterMasterDescription;
	}

	public Boolean getPermanetDeleteCheck() {
		return permanetDeleteCheck;
	}

	public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
		this.permanetDeleteCheck = permanetDeleteCheck;
	}

	public Boolean getRemarksCheck() {
		return remarksCheck;
	}

	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Boolean getBasedOnFlexiList() {
		return basedOnFlexiList;
	}

	public void setBasedOnFlexiList(Boolean basedOnFlexiList) {
		this.basedOnFlexiList = basedOnFlexiList;
	}

	public Boolean getBasedOnDate() {
		return basedOnDate;
	}

	public void setBasedOnDate(Boolean basedOnDate) {
		this.basedOnDate = basedOnDate;
	}

	public Date getDateFlexiValue() {
		return dateFlexiValue;
	}

	public void setDateFlexiValue(Date dateFlexiValue) {
		this.dateFlexiValue = dateFlexiValue;
	}

	public Boolean getBooDefintionReadOnly() {
		return booDefintionReadOnly;
	}

	public void setBooDefintionReadOnly(Boolean booDefintionReadOnly) {
		this.booDefintionReadOnly = booDefintionReadOnly;
	}

	public Boolean getBooRenderChar() {
		return booRenderChar;
	}

	public void setBooRenderChar(Boolean booRenderChar) {
		this.booRenderChar = booRenderChar;
	}

	public Boolean getBooRenderNumber() {
		return booRenderNumber;
	}

	public void setBooRenderNumber(Boolean booRenderNumber) {
		this.booRenderNumber = booRenderNumber;
	}

	public Boolean getBooRenderDate() {
		return booRenderDate;
	}

	public void setBooRenderDate(Boolean booRenderDate) {
		this.booRenderDate = booRenderDate;
	}

	public String getCharValue() {
		return charValue;
	}

	public void setCharValue(String charValue) {
		this.charValue = charValue;
	}

	public BigDecimal getNumberValue() {
		return numberValue;
	}

	public void setNumberValue(BigDecimal numberValue) {
		this.numberValue = numberValue;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public Boolean getBooradonlyforMasterDesciption() {
		return booradonlyforMasterDesciption;
	}

	public void setBooradonlyforMasterDesciption(Boolean booradonlyforMasterDesciption) {
		this.booradonlyforMasterDesciption = booradonlyforMasterDesciption;
	}

	public Boolean getRenderEditButton() {
		return renderEditButton;
	}

	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public ParameterDetailsDataTable getSelectedDocument() {
		return selectedDocument;
	}

	public void setSelectedDocument(ParameterDetailsDataTable selectedDocument) {
		this.selectedDocument = selectedDocument;
	}

	public BigDecimal getParameterDetailsId() {
		return parameterDetailsId;
	}

	public void setParameterDetailsId(BigDecimal parameterDetailsId) {
		this.parameterDetailsId = parameterDetailsId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public IParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(IParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public Logger getLOGGER() {
		return LOGGER;
	}

	public void setLOGGER(Logger lOGGER) {
		LOGGER = lOGGER;
	}

	public List<ParameterDetailsDataTable> getFinalList() {
		return finalList;
	}

	public void setFinalList(List<ParameterDetailsDataTable> finalList) {
		this.finalList = finalList;
	}

	public BigDecimal getNumericfield1() {
		return numericfield1;
	}

	public void setNumericfield1(BigDecimal numericfield1) {
		this.numericfield1 = numericfield1;
	}

	public BigDecimal getNumericfield2() {
		return numericfield2;
	}

	public void setNumericfield2(BigDecimal numericfield2) {
		this.numericfield2 = numericfield2;
	}

	public BigDecimal getNumericfield3() {
		return numericfield3;
	}

	public void setNumericfield3(BigDecimal numericfield3) {
		this.numericfield3 = numericfield3;
	}

	public BigDecimal getNumericfield4() {
		return numericfield4;
	}

	public void setNumericfield4(BigDecimal numericfield4) {
		this.numericfield4 = numericfield4;
	}

	public BigDecimal getNumericfield5() {
		return numericfield5;
	}

	public void setNumericfield5(BigDecimal numericfield5) {
		this.numericfield5 = numericfield5;
	}

	public BigDecimal getNumericfield6() {
		return numericfield6;
	}

	public void setNumericfield6(BigDecimal numericfield6) {
		this.numericfield6 = numericfield6;
	}

	public BigDecimal getNumericfield7() {
		return numericfield7;
	}

	public void setNumericfield7(BigDecimal numericfield7) {
		this.numericfield7 = numericfield7;
	}

	public BigDecimal getNumericfield8() {
		return numericfield8;
	}

	public void setNumericfield8(BigDecimal numericfield8) {
		this.numericfield8 = numericfield8;
	}

	public BigDecimal getNumericfield9() {
		return numericfield9;
	}

	public void setNumericfield9(BigDecimal numericfield9) {
		this.numericfield9 = numericfield9;
	}

	public String getCharfield1() {
		return charfield1;
	}

	public void setCharfield1(String charfield1) {
		this.charfield1 = charfield1;
	}

	public String getCharfield2() {
		return charfield2;
	}

	public void setCharfield2(String charfield2) {
		this.charfield2 = charfield2;
	}

	public String getCharfield3() {
		return charfield3;
	}

	public void setCharfield3(String charfield3) {
		this.charfield3 = charfield3;
	}

	public String getCharfield4() {
		return charfield4;
	}

	public void setCharfield4(String charfield4) {
		this.charfield4 = charfield4;
	}

	public String getCharfield5() {
		return charfield5;
	}

	public void setCharfield5(String charfield5) {
		this.charfield5 = charfield5;
	}

	public String getCharfield6() {
		return charfield6;
	}

	public void setCharfield6(String charfield6) {
		this.charfield6 = charfield6;
	}

	public String getCharfield7() {
		return charfield7;
	}

	public void setCharfield7(String charfield7) {
		this.charfield7 = charfield7;
	}

	public String getCharfield8() {
		return charfield8;
	}

	public void setCharfield8(String charfield8) {
		this.charfield8 = charfield8;
	}

	public String getCharfield9() {
		return charfield9;
	}

	public void setCharfield9(String charfield9) {
		this.charfield9 = charfield9;
	}

	public Date getDatefield1() {
		return datefield1;
	}

	public void setDatefield1(Date datefield1) {
		this.datefield1 = datefield1;
	}

	public Date getDatefield2() {
		return datefield2;
	}

	public void setDatefield2(Date datefield2) {
		this.datefield2 = datefield2;
	}

	public Date getDatefield3() {
		return datefield3;
	}

	public void setDatefield3(Date datefield3) {
		this.datefield3 = datefield3;
	}

	public Date getDatefield4() {
		return datefield4;
	}

	public void setDatefield4(Date datefield4) {
		this.datefield4 = datefield4;
	}

	public Date getDatefield5() {
		return datefield5;
	}

	public void setDatefield5(Date datefield5) {
		this.datefield5 = datefield5;
	}

	public Date getDatefield6() {
		return datefield6;
	}

	public void setDatefield6(Date datefield6) {
		this.datefield6 = datefield6;
	}

	public Date getDatefield7() {
		return datefield7;
	}

	public void setDatefield7(Date datefield7) {
		this.datefield7 = datefield7;
	}

	public Date getDatefield8() {
		return datefield8;
	}

	public void setDatefield8(Date datefield8) {
		this.datefield8 = datefield8;
	}

	public Date getDatefield9() {
		return datefield9;
	}

	public void setDatefield9(Date datefield9) {
		this.datefield9 = datefield9;
	}

	public List<ParameterDetailsDataTable> getLstFinalListForDataTable() {
		return lstFinalListForDataTable;
	}

	public void setLstFinalListForDataTable(List<ParameterDetailsDataTable> lstFinalListForDataTable) {
		this.lstFinalListForDataTable = lstFinalListForDataTable;
	}

	public List<ParameterMaster> getLstParameterMaster() {
		return lstParameterMaster =parameterService.fetchAllParameterMasterForView();
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public boolean isHideEdit() {
		return hideEdit;
	}

	public void setHideEdit(boolean hideEdit) {
		this.hideEdit = hideEdit;
	}

	public String getDataField() {
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

	public List<FlexField> getFlexFieldList() {
		return flexFieldList;
	}

	public void setFlexFieldList(List<FlexField> flexFieldList) {
		this.flexFieldList = flexFieldList;
	}

	public String getFlexFieldName() {
		return flexFieldName;
	}

	public void setFlexFieldName(String flexFieldName) {
		this.flexFieldName = flexFieldName;
	}

	public String getFlexiFieldValue() {
		return flexiFieldValue;
	}

	public void setFlexiFieldValue(String flexiFieldValue) {
		this.flexiFieldValue = flexiFieldValue;
	}

	public boolean isIspopulate() {
		return ispopulate;
	}

	public void setIspopulate(boolean ispopulate) {
		this.ispopulate = ispopulate;
	}

	public String getDetailRecordId() {
		return detailRecordId;
	}

	public void setDetailRecordId(String detailRecordId) {
		this.detailRecordId = detailRecordId;
	}

	public void setLstParameterMaster(List<ParameterMaster> lstParameterMaster) {
		this.lstParameterMaster = lstParameterMaster;
	}

	public List<ParameterDefinition> getLstDefinitionrMaster() {
		lstDefinitionrMaster = parameterService.getAllRecords();
		return lstDefinitionrMaster;
	}

	public void setLstDefinitionrMaster(List<ParameterDefinition> lstDefinitionrMaster) {
		this.lstDefinitionrMaster = lstDefinitionrMaster;
	}

	public BigDecimal getParameterDefinitionId() {
		return parameterDefinitionId;
	}

	public void setParameterDefinitionId(BigDecimal parameterDefinitionId) {
		this.parameterDefinitionId = parameterDefinitionId;
	}

	public BigDecimal getParameterMasterId() {
		return parameterMasterId;
	}

	public void setParameterMasterId(BigDecimal parameterMasterId) {
		this.parameterMasterId = parameterMasterId;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getParamcodedefintion() {
		return paramcodedefintion;
	}

	public void setParamcodedefintion(String paramcodedefintion) {
		this.paramcodedefintion = paramcodedefintion;
	}

	public String getParameterFullDesc() {
		return parameterFullDesc;
	}

	public void setParameterFullDesc(String parameterFullDesc) {
		this.parameterFullDesc = parameterFullDesc;
	}

	public String getParameterShortDesc() {
		return parameterShortDesc;
	}

	public void setParameterShortDesc(String parameterShortDesc) {
		this.parameterShortDesc = parameterShortDesc;
	}

	public String getArabicFullDesc() {
		return arabicFullDesc;
	}

	public void setArabicFullDesc(String arabicFullDesc) {
		this.arabicFullDesc = arabicFullDesc;
	}

	public String getArabicShortDesc() {
		return arabicShortDesc;
	}

	public void setArabicShortDesc(String arabicShortDesc) {
		this.arabicShortDesc = arabicShortDesc;
	}

	public BigDecimal getNumericField() {
		return numericField;
	}

	public void setNumericField(BigDecimal numericField) {
		this.numericField = numericField;
	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {
		
		clearAll();
		ispopulate= false;
		lstFinalListForDataTable.clear();
		flexFieldList.clear();
		setBooRenderDataTable(false);
		setRenderEditButton(false);
		view();
		
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "parameterapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/parameterapproval.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getParameterMaster() {
		LOGGER.info("getParameterDefinitionId()" + getParameterDefinitionId());
		ParameterDefinition parameterDefinition = parameterService.viewByID(getParameterDefinitionId());
		if (parameterDefinition != null) {
			lstParameterMaster = parameterService.viewDefinitionListforDetails(parameterDefinition.getParameterMasterId().getParameterMasterId());
		}
	}

	public void populateParameterMaster() {
		LOGGER.info("Enter into populateParameterMaster method : ParameterMasterandDefinitionBean ");
		LOGGER.info("RecordId  " + getRecordId());
		ispopulate = true;
	}

	// TO POPULATE THE RELATION CODE BASED ON GIVEN ENTRY
	public List<String> autoCompleteData(String query) {
		if (query.length() > 0) {
			List<ParameterDetails> ParameterList = parameterService.getAllComponent(query, getParameterDefinitionId(), getParameterMasterId());
			List<String> list = new ArrayList<String>();
			for (ParameterDetails parameter : ParameterList) {
				list.add(parameter.getRecordId());
			}
			return list;
		} else {
			return null;
		}
	}

	public void getFlexiList() {
		try {
			FlexField temp = null;
			List<ParameterDefinition> defanitionList = parameterService.getFlexiList(getParameterMasterId());
			for (ParameterDefinition parameterDefinition : defanitionList) {
				temp = new FlexField();
				temp.setColumnName(parameterDefinition.getFieldName());
				flexFieldList.add(temp);
			}
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
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
	
	
	public void clearAll()
	{

		setCharfield1(null);
		setCharfield2(null);
		setCharfield3(null);
		setCharfield5(null);
		setCharfield6(null);
		setCharfield7(null);
		setCharfield8(null);
		setCharfield9(null);
		
		setDatefield1(null);
		setDatefield2(null);
		setDatefield3(null);
		setDatefield4(null);
		setDatefield5(null);
		setDatefield6(null);
		setDatefield7(null);
		setDatefield8(null);
		setDatefield9(null);
		
		setNumericfield1(null);
		setNumericfield2(null);
		setNumericfield3(null);
		setNumericfield4(null);
		setNumericfield5(null);
		setNumericfield6(null);
		setNumericfield7(null);
		setNumericfield8(null);
		setNumericfield9(null);
		clearexceptMandD();
		setParameterMasterId(null);
		setParameterDefinitionId(null);
		setFlexFieldName(null);
		setFlexiFieldValue(null);
	}
	
	public void clearexceptMandD()
	{
		setParamcodedefintion(null);
		setParameterFullDesc(null);
		setParameterShortDesc(null);
		setArabicFullDesc(null);
		setArabicShortDesc(null);
		setFlexiFieldValue(null);
		setDataField(null);
		setParameterDetailsId(null);
	}
	
	
	
	
	
	public void view()
	{
		try {
			lstFinalListForDataTable.clear();
			
			
			List<ParameterGrant> grantList = parameterService.getGrantListfortheUser(session.getUserName());
			ArrayList<String> grantRecordList = new ArrayList<String>();
			if (grantList != null && !grantList.isEmpty()) {
				for (ParameterGrant grantObj : grantList) {
					grantRecordList.add(grantObj.getRecordId());
				}
			}
			
			List<ParameterDetails> detailsList = new ArrayList<ParameterDetails>();
			if(!grantRecordList.isEmpty()){
				detailsList = parameterService.fetchAllunApprovedRecordwithPermission(grantRecordList);
			}
			
			setBooRenderDataTable(true);
			for (ParameterDetails parameterDetails : detailsList) {
				
				ParameterDetailsDataTable datatable= new ParameterDetailsDataTable();
				log.info(parameterDetails.getParamCodeDef());
				datatable.setRecordId(parameterDetails.getRecordId());
				datatable.setParameterMasterId(parameterDetails.getParameterMasterId().getParameterMasterId());
				/*datatable.setParameterDefinitionId(parameterDetails.getParameterMasterId().getParameterMasterId());*/
				datatable.setParamcodedefintion(parameterDetails.getParamCodeDef());
				datatable.setParameterFullDesc(parameterDetails.getFullDesc());
				datatable.setParameterShortDesc(parameterDetails.getShortDesc());
				datatable.setArabicFullDesc(parameterDetails.getArFullDesc());
				datatable.setArabicShortDesc(parameterDetails.getShortDesc());
				datatable.setParameterCode(parameterDetails.getParamCodeDef());
				datatable.setCharfield1(parameterDetails.getCharField1());
				datatable.setCharfield2(parameterDetails.getCharField2());
				datatable.setCharfield3(parameterDetails.getCharField3());
				datatable.setCharfield4(parameterDetails.getCharField4());
				datatable.setCharfield5(parameterDetails.getCharField5());
				datatable.setCharfield6(parameterDetails.getCharField6());
				datatable.setCharfield7(parameterDetails.getCharField7());
				datatable.setCharfield8(parameterDetails.getCharField8());
				datatable.setCharfield9(parameterDetails.getCharField9());
				datatable.setNumericfield1(parameterDetails.getNumericField1());
				datatable.setNumericfield2(parameterDetails.getNumericField2());
				datatable.setNumericfield3(parameterDetails.getNumericField3());
				datatable.setNumericfield4(parameterDetails.getNumericField4());
				datatable.setNumericfield5(parameterDetails.getNumericField5());
				datatable.setNumericfield6(parameterDetails.getNumericField6());
				datatable.setNumericfield7(parameterDetails.getNumericField7());
				datatable.setNumericfield8(parameterDetails.getNumericField8());
				datatable.setNumericfield9(parameterDetails.getNumericField9());
				datatable.setDatefield1(parameterDetails.getDateField1());
				datatable.setDatefield2(parameterDetails.getDateField2());
				datatable.setDatefield3(parameterDetails.getDateField3());
				datatable.setDatefield4(parameterDetails.getDateField4());
				datatable.setDatefield5(parameterDetails.getDateField5());
				datatable.setDatefield6(parameterDetails.getDateField6());
				datatable.setDatefield7(parameterDetails.getDateField7());
				datatable.setDatefield8(parameterDetails.getDateField8());
				datatable.setDatefield9(parameterDetails.getDateField9());
				datatable.setParameterDetailsId(parameterDetails.getParameterDetailsId());
				datatable.setCreatedBy(parameterDetails.getCreatedBy());
				datatable.setCreatedDate(parameterDetails.getCreatedDate());
				datatable.setModifiedBy(parameterDetails.getModifiedBy());
				datatable.setModifiedDate(parameterDetails.getModifiedDate());
				datatable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(parameterDetails.getIsActive()));
				datatable.setFlexFieldName(getFlexFieldName());
				lstFinalListForDataTable.add(datatable);
			
			}
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}
	
	
	private Boolean booVisible;

	public Boolean getBooVisible() {
		return booVisible;
	}

	public void setBooVisible(Boolean booVisible) {
		this.booVisible = booVisible;
	}

	private String setreverActiveStatus(String status) {

		String strStatus = null;
		
		if (status==null) {
			
			strStatus = "Remove";
		}

		else if (status.equalsIgnoreCase("U")) {
			
			strStatus = "Delete";
		}
		else if (status.equalsIgnoreCase("D")) {

			strStatus = "Activate";
		}
		else if (status.equalsIgnoreCase("Y")) {
			strStatus = "Deactivate";
		}
		return strStatus;

	}
	
	public void clickOnExit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	
	
	
	public void gotoApproval(ParameterDetailsDataTable dataTable) {
		try {
			log.info("Entering into gotoApproval method");
			log.info("Modified by " + dataTable.getModifiedBy());
			log.info("created by " + dataTable.getCreatedBy());
			if (dataTable.getModifiedBy() != null) {
				if (dataTable.getModifiedBy().equals(session.getUserName())) {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("noteligible.show();");
					return;
				}
			} else {
				if (dataTable.getCreatedBy().equals(session.getUserName())) {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("noteligible.show();");
					return;
				}
			}
			setParameterDetailsId(dataTable.getParameterDetailsId());
			setArabicFullDesc(dataTable.getArabicFullDesc());
			setArabicShortDesc(dataTable.getArabicShortDesc());
			setParameterDefinitionId(dataTable.getParameterDefinitionId());
			setParamcodedefintion(dataTable.getParamcodedefintion());
			setParameterFullDesc(dataTable.getParameterFullDesc());
			setParameterShortDesc(dataTable.getParameterShortDesc());
			setParameterMasterId(dataTable.getParameterMasterId());
			setParameterCode(dataTable.getParameterCode());
			setFlexFieldName(dataTable.getFlexFieldName());
			setModifiedBy(dataTable.getModifiedBy());
			setModifiedDate(dataTable.getModifiedDate());
			setRecordId(dataTable.getRecordId());
			setCharfield1(dataTable.getCharfield1());
			setCharfield2(dataTable.getCharfield2());
			setCharfield3(dataTable.getCharfield3());
			setCharfield4(dataTable.getCharfield4());
			setCharfield5(dataTable.getCharfield5());
			setCharfield6(dataTable.getCharfield6());
			setCharfield7(dataTable.getCharfield7());
			setCharfield8(dataTable.getCharfield8());
			setCharfield9(dataTable.getCharfield9());
			setDatefield1(dataTable.getDatefield1());
			setDatefield2(dataTable.getDatefield2());
			setDatefield3(dataTable.getDatefield3());
			setDatefield4(dataTable.getDatefield4());
			setDatefield5(dataTable.getDatefield5());
			setDatefield6(dataTable.getDatefield6());
			setDatefield7(dataTable.getDatefield7());
			setDatefield8(dataTable.getDatefield8());
			setDatefield9(dataTable.getDatefield9());
			setNumericfield1(dataTable.getNumericfield1());
			setNumericfield2(dataTable.getNumericfield2());
			setNumericfield3(dataTable.getNumericfield3());
			setNumericfield4(dataTable.getNumericfield4());
			setNumericfield5(dataTable.getNumericfield5());
			setNumericfield6(dataTable.getNumericfield6());
			setNumericfield7(dataTable.getNumericfield7());
			setNumericfield8(dataTable.getNumericfield8());
			setNumericfield9(dataTable.getNumericfield9());
			// setFlexiFieldValue(dataTable.getFlexiFieldValue());
			getEnableFlexifiled();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/parameterdetailsapproval.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
			log.info("Entering into gotoApproval method");
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}
	
	
	public void approve() {
		try {
			log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			String approveMsg = parameterService.approveRecord(getParameterDetailsId(), session.getUserName());
			if (approveMsg.equals("success")) {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("approve.show();");
			}
			log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}
	
	Boolean CHAR_FIELD1;
	Boolean CHAR_FIELD2;
	Boolean CHAR_FIELD3;
	Boolean CHAR_FIELD4;
	Boolean CHAR_FIELD5;
	Boolean CHAR_FIELD6;
	Boolean CHAR_FIELD7;
	Boolean CHAR_FIELD8;
	Boolean CHAR_FIELD9;
	Boolean NUMERIC_FIELD1;
	Boolean NUMERIC_FIELD2;
	Boolean NUMERIC_FIELD3;
	Boolean NUMERIC_FIELD4;
	Boolean NUMERIC_FIELD5;
	Boolean NUMERIC_FIELD6;
	Boolean NUMERIC_FIELD7;
	Boolean NUMERIC_FIELD8;
	Boolean NUMERIC_FIELD9;
	Boolean DATE_FIELD1;
	Boolean DATE_FIELD2;
	Boolean DATE_FIELD3;
	Boolean DATE_FIELD4;
	Boolean DATE_FIELD5;
	Boolean DATE_FIELD6;
	Boolean DATE_FIELD7;
	Boolean DATE_FIELD8;
	Boolean DATE_FIELD9;
	
	
	public void getEnableFlexifiled() {
		LOGGER.info("Master id " + getParameterMasterId());
		LOGGER.info("parameter code " + getParameterCode());
		//ParameterDetails details = parameterService.populateDetails(getParameterMasterId(), getParameterCode());
		ParameterDetails details = parameterService.populateParamaterDetails(getParameterMasterId(), getParameterCode(),getParameterDetailsId());
		if (details != null) {
			setParameterDetailsId(details.getParameterDetailsId());
			setArabicFullDesc(details.getArFullDesc());
			setArabicShortDesc(details.getArShortDesc());
			setParamcodedefintion(details.getParamCodeDef());
			setParameterFullDesc(details.getFullDesc());
			setParameterShortDesc(details.getShortDesc());
			setParameterMasterId(details.getParameterMasterId().getParameterMasterId());
			setModifiedBy(details.getModifiedBy());
			setModifiedDate(details.getModifiedDate());
			setCharfield1(details.getCharField1());
			setCharfield2(details.getCharField2());
			setCharfield3(details.getCharField3());
			setCharfield4(details.getCharField4());
			setCharfield5(details.getCharField5());
			setCharfield6(details.getCharField6());
			setCharfield7(details.getCharField7());
			setCharfield8(details.getCharField8());
			setCharfield9(details.getCharField9());
			setDatefield1(details.getDateField1());
			setDatefield2(details.getDateField2());
			setDatefield3(details.getDateField3());
			setDatefield4(details.getDateField4());
			setDatefield5(details.getDateField5());
			setDatefield6(details.getDateField6());
			setDatefield7(details.getDateField7());
			setDatefield8(details.getDateField8());
			setDatefield9(details.getDateField9());
			setNumericfield1(details.getNumericField1());
			setNumericfield2(details.getNumericField2());
			setNumericfield3(details.getNumericField3());
			setNumericfield4(details.getNumericField4());
			setNumericfield5(details.getNumericField5());
			setNumericfield6(details.getNumericField6());
			setNumericfield7(details.getNumericField7());
			setNumericfield8(details.getNumericField8());
			setNumericfield9(details.getNumericField9());
			setCreatedDate(details.getCreatedDate());
			setCreatedBy(details.getCreatedBy());
			setIsActive(details.getIsActive());
			setModifiedBy(details.getModifiedBy());
			setModifiedDate(details.getModifiedDate());
			setRemarks(details.getRemarks());
			setRecordId(details.getRecordId());
		} else {
			setCharfield1(null);
			setCharfield2(null);
			setCharfield3(null);
			setCharfield4(null);
			setCharfield5(null);
			setCharfield6(null);
			setCharfield7(null);
			setCharfield8(null);
			setCharfield9(null);
			setDatefield1(null);
			setDatefield2(null);
			setDatefield3(null);
			setDatefield4(null);
			setDatefield5(null);
			setDatefield6(null);
			setDatefield7(null);
			setDatefield8(null);
			setDatefield9(null);
			setNumericfield1(null);
			setNumericfield2(null);
			setNumericfield3(null);
			setNumericfield4(null);
			setNumericfield5(null);
			setNumericfield6(null);
			setNumericfield7(null);
			setNumericfield8(null);
			setNumericfield9(null);
			setIsActive(null);
		}
		// List<String> flexiFieldList =
		// parameterService.getFlexiFieldfromDB(getParameterMasterId());
		List<ParameterDefinition> flexiFieldList = parameterService.getFlexiFieldfromDBWithList(getParameterMasterId());
		for (int i = 0; i < flexiFieldList.size(); i++) {
			String compareValue = flexiFieldList.get(i).getFieldName();
			String labelValue = flexiFieldList.get(i).getFullDesc();
			LOGGER.info(compareValue);
			if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD1)) {
				setCHAR_FIELD1(true);
				setCharfieldLabel1(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD2)) {
				setCHAR_FIELD2(true);
				setCharfieldLabel2(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD3)) {
				setCHAR_FIELD3(true);
				setCharfieldLabel3(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD4)) {
				setCHAR_FIELD4(true);
				setCharfieldLabel4(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD5)) {
				setCHAR_FIELD5(true);
				setCharfieldLabel5(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD6)) {
				setCHAR_FIELD6(true);
				setCharfieldLabel6(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD7)) {
				setCHAR_FIELD7(true);
				setCharfieldLabel7(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD8)) {
				setCHAR_FIELD8(true);
				setCharfieldLabel8(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD9)) {
				setCHAR_FIELD9(true);
				setCharfieldLabel9(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD1)) {
				setDATE_FIELD1(true);
				setDatefieldLabel1(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD2)) {
				setDATE_FIELD2(true);
				setDatefieldLabel2(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD3)) {
				setDATE_FIELD3(true);
				setDatefieldLabel3(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD4)) {
				setDATE_FIELD4(true);
				setDatefieldLabel4(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD5)) {
				setDATE_FIELD5(true);
				setDatefieldLabel5(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD6)) {
				setDATE_FIELD6(true);
				setDatefieldLabel6(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD7)) {
				setDATE_FIELD7(true);
				setDatefieldLabel7(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD8)) {
				setDATE_FIELD8(true);
				setDatefieldLabel8(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD9)) {
				setDATE_FIELD9(true);
				setDatefieldLabel9(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD1)) {
				setNUMERIC_FIELD1(true);
				setNumericfieldLabel1(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD2)) {
				setNUMERIC_FIELD2(true);
				setNumericfieldLabel2(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD3)) {
				setNUMERIC_FIELD3(true);
				setNumericfieldLabel3(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD4)) {
				setNUMERIC_FIELD4(true);
				setNumericfieldLabel4(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD5)) {
				setNUMERIC_FIELD5(true);
				setNumericfieldLabel5(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD6)) {
				setNUMERIC_FIELD6(true);
				setNumericfieldLabel6(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD7)) {
				setNUMERIC_FIELD7(true);
				setNumericfieldLabel7(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD8)) {
				setNUMERIC_FIELD8(true);
				setNumericfieldLabel8(labelValue);
			} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD9)) {
				setNUMERIC_FIELD9(true);
				setNumericfieldLabel9(labelValue);
			}
		}
	}
	
	private String charfieldLabel1;
	private String charfieldLabel2;
	private String charfieldLabel3;
	private String charfieldLabel4;
	private String charfieldLabel5;
	private String charfieldLabel6;
	private String charfieldLabel7;
	private String charfieldLabel8;
	private String charfieldLabel9;
	
	private String datefieldLabel1;
	private String datefieldLabel2;
	private String datefieldLabel3;
	private String datefieldLabel4;
	private String datefieldLabel5;
	private String datefieldLabel6;
	private String datefieldLabel7;
	private String datefieldLabel8;
	private String datefieldLabel9;
	
	private String numericfieldLabel1;
	private String numericfieldLabel2;
	private String numericfieldLabel3;
	private String numericfieldLabel4;
	private String numericfieldLabel5;
	private String numericfieldLabel6;
	private String numericfieldLabel7;
	private String numericfieldLabel8;
	private String numericfieldLabel9;
	
	
	
	
	
	public String getCharfieldLabel1() {
		return charfieldLabel1;
	}

	public void setCharfieldLabel1(String charfieldLabel1) {
		this.charfieldLabel1 = charfieldLabel1;
	}

	public String getCharfieldLabel2() {
		return charfieldLabel2;
	}

	public void setCharfieldLabel2(String charfieldLabel2) {
		this.charfieldLabel2 = charfieldLabel2;
	}

	public String getCharfieldLabel3() {
		return charfieldLabel3;
	}

	public void setCharfieldLabel3(String charfieldLabel3) {
		this.charfieldLabel3 = charfieldLabel3;
	}

	public String getCharfieldLabel4() {
		return charfieldLabel4;
	}

	public void setCharfieldLabel4(String charfieldLabel4) {
		this.charfieldLabel4 = charfieldLabel4;
	}

	public String getCharfieldLabel5() {
		return charfieldLabel5;
	}

	public void setCharfieldLabel5(String charfieldLabel5) {
		this.charfieldLabel5 = charfieldLabel5;
	}

	public String getCharfieldLabel6() {
		return charfieldLabel6;
	}

	public void setCharfieldLabel6(String charfieldLabel6) {
		this.charfieldLabel6 = charfieldLabel6;
	}

	public String getCharfieldLabel7() {
		return charfieldLabel7;
	}

	public void setCharfieldLabel7(String charfieldLabel7) {
		this.charfieldLabel7 = charfieldLabel7;
	}

	public String getCharfieldLabel8() {
		return charfieldLabel8;
	}

	public void setCharfieldLabel8(String charfieldLabel8) {
		this.charfieldLabel8 = charfieldLabel8;
	}

	public String getCharfieldLabel9() {
		return charfieldLabel9;
	}

	public void setCharfieldLabel9(String charfieldLabel9) {
		this.charfieldLabel9 = charfieldLabel9;
	}

	public String getDatefieldLabel1() {
		return datefieldLabel1;
	}

	public void setDatefieldLabel1(String datefieldLabel1) {
		this.datefieldLabel1 = datefieldLabel1;
	}

	public String getDatefieldLabel2() {
		return datefieldLabel2;
	}

	public void setDatefieldLabel2(String datefieldLabel2) {
		this.datefieldLabel2 = datefieldLabel2;
	}

	public String getDatefieldLabel3() {
		return datefieldLabel3;
	}

	public void setDatefieldLabel3(String datefieldLabel3) {
		this.datefieldLabel3 = datefieldLabel3;
	}

	public String getDatefieldLabel4() {
		return datefieldLabel4;
	}

	public void setDatefieldLabel4(String datefieldLabel4) {
		this.datefieldLabel4 = datefieldLabel4;
	}

	public String getDatefieldLabel5() {
		return datefieldLabel5;
	}

	public void setDatefieldLabel5(String datefieldLabel5) {
		this.datefieldLabel5 = datefieldLabel5;
	}

	public String getDatefieldLabel6() {
		return datefieldLabel6;
	}

	public void setDatefieldLabel6(String datefieldLabel6) {
		this.datefieldLabel6 = datefieldLabel6;
	}

	public String getDatefieldLabel7() {
		return datefieldLabel7;
	}

	public void setDatefieldLabel7(String datefieldLabel7) {
		this.datefieldLabel7 = datefieldLabel7;
	}

	public String getDatefieldLabel8() {
		return datefieldLabel8;
	}

	public void setDatefieldLabel8(String datefieldLabel8) {
		this.datefieldLabel8 = datefieldLabel8;
	}

	public String getDatefieldLabel9() {
		return datefieldLabel9;
	}

	public void setDatefieldLabel9(String datefieldLabel9) {
		this.datefieldLabel9 = datefieldLabel9;
	}

	public String getNumericfieldLabel1() {
		return numericfieldLabel1;
	}

	public void setNumericfieldLabel1(String numericfieldLabel1) {
		this.numericfieldLabel1 = numericfieldLabel1;
	}

	public String getNumericfieldLabel2() {
		return numericfieldLabel2;
	}

	public void setNumericfieldLabel2(String numericfieldLabel2) {
		this.numericfieldLabel2 = numericfieldLabel2;
	}

	public String getNumericfieldLabel3() {
		return numericfieldLabel3;
	}

	public void setNumericfieldLabel3(String numericfieldLabel3) {
		this.numericfieldLabel3 = numericfieldLabel3;
	}

	public String getNumericfieldLabel4() {
		return numericfieldLabel4;
	}

	public void setNumericfieldLabel4(String numericfieldLabel4) {
		this.numericfieldLabel4 = numericfieldLabel4;
	}

	public String getNumericfieldLabel5() {
		return numericfieldLabel5;
	}

	public void setNumericfieldLabel5(String numericfieldLabel5) {
		this.numericfieldLabel5 = numericfieldLabel5;
	}

	public String getNumericfieldLabel6() {
		return numericfieldLabel6;
	}

	public void setNumericfieldLabel6(String numericfieldLabel6) {
		this.numericfieldLabel6 = numericfieldLabel6;
	}

	public String getNumericfieldLabel7() {
		return numericfieldLabel7;
	}

	public void setNumericfieldLabel7(String numericfieldLabel7) {
		this.numericfieldLabel7 = numericfieldLabel7;
	}

	public String getNumericfieldLabel8() {
		return numericfieldLabel8;
	}

	public void setNumericfieldLabel8(String numericfieldLabel8) {
		this.numericfieldLabel8 = numericfieldLabel8;
	}

	public String getNumericfieldLabel9() {
		return numericfieldLabel9;
	}

	public void setNumericfieldLabel9(String numericfieldLabel9) {
		this.numericfieldLabel9 = numericfieldLabel9;
	}

	public Boolean getCHAR_FIELD1() {
		return CHAR_FIELD1;
	}

	public void setCHAR_FIELD1(Boolean cHAR_FIELD1) {
		CHAR_FIELD1 = cHAR_FIELD1;
	}

	public Boolean getCHAR_FIELD2() {
		return CHAR_FIELD2;
	}

	public void setCHAR_FIELD2(Boolean cHAR_FIELD2) {
		CHAR_FIELD2 = cHAR_FIELD2;
	}

	public Boolean getCHAR_FIELD3() {
		return CHAR_FIELD3;
	}

	public void setCHAR_FIELD3(Boolean cHAR_FIELD3) {
		CHAR_FIELD3 = cHAR_FIELD3;
	}

	public Boolean getCHAR_FIELD4() {
		return CHAR_FIELD4;
	}

	public void setCHAR_FIELD4(Boolean cHAR_FIELD4) {
		CHAR_FIELD4 = cHAR_FIELD4;
	}

	public Boolean getCHAR_FIELD5() {
		return CHAR_FIELD5;
	}

	public void setCHAR_FIELD5(Boolean cHAR_FIELD5) {
		CHAR_FIELD5 = cHAR_FIELD5;
	}

	public Boolean getCHAR_FIELD6() {
		return CHAR_FIELD6;
	}

	public void setCHAR_FIELD6(Boolean cHAR_FIELD6) {
		CHAR_FIELD6 = cHAR_FIELD6;
	}

	public Boolean getCHAR_FIELD7() {
		return CHAR_FIELD7;
	}

	public void setCHAR_FIELD7(Boolean cHAR_FIELD7) {
		CHAR_FIELD7 = cHAR_FIELD7;
	}

	public Boolean getCHAR_FIELD8() {
		return CHAR_FIELD8;
	}

	public void setCHAR_FIELD8(Boolean cHAR_FIELD8) {
		CHAR_FIELD8 = cHAR_FIELD8;
	}

	public Boolean getCHAR_FIELD9() {
		return CHAR_FIELD9;
	}

	public void setCHAR_FIELD9(Boolean cHAR_FIELD9) {
		CHAR_FIELD9 = cHAR_FIELD9;
	}

	public Boolean getNUMERIC_FIELD1() {
		return NUMERIC_FIELD1;
	}

	public void setNUMERIC_FIELD1(Boolean nUMERIC_FIELD1) {
		NUMERIC_FIELD1 = nUMERIC_FIELD1;
	}

	public Boolean getNUMERIC_FIELD2() {
		return NUMERIC_FIELD2;
	}

	public void setNUMERIC_FIELD2(Boolean nUMERIC_FIELD2) {
		NUMERIC_FIELD2 = nUMERIC_FIELD2;
	}

	public Boolean getNUMERIC_FIELD3() {
		return NUMERIC_FIELD3;
	}

	public void setNUMERIC_FIELD3(Boolean nUMERIC_FIELD3) {
		NUMERIC_FIELD3 = nUMERIC_FIELD3;
	}

	public Boolean getNUMERIC_FIELD4() {
		return NUMERIC_FIELD4;
	}

	public void setNUMERIC_FIELD4(Boolean nUMERIC_FIELD4) {
		NUMERIC_FIELD4 = nUMERIC_FIELD4;
	}

	public Boolean getNUMERIC_FIELD5() {
		return NUMERIC_FIELD5;
	}

	public void setNUMERIC_FIELD5(Boolean nUMERIC_FIELD5) {
		NUMERIC_FIELD5 = nUMERIC_FIELD5;
	}

	public Boolean getNUMERIC_FIELD6() {
		return NUMERIC_FIELD6;
	}

	public void setNUMERIC_FIELD6(Boolean nUMERIC_FIELD6) {
		NUMERIC_FIELD6 = nUMERIC_FIELD6;
	}

	public Boolean getNUMERIC_FIELD7() {
		return NUMERIC_FIELD7;
	}

	public void setNUMERIC_FIELD7(Boolean nUMERIC_FIELD7) {
		NUMERIC_FIELD7 = nUMERIC_FIELD7;
	}

	public Boolean getNUMERIC_FIELD8() {
		return NUMERIC_FIELD8;
	}

	public void setNUMERIC_FIELD8(Boolean nUMERIC_FIELD8) {
		NUMERIC_FIELD8 = nUMERIC_FIELD8;
	}

	public Boolean getNUMERIC_FIELD9() {
		return NUMERIC_FIELD9;
	}

	public void setNUMERIC_FIELD9(Boolean nUMERIC_FIELD9) {
		NUMERIC_FIELD9 = nUMERIC_FIELD9;
	}

	public Boolean getDATE_FIELD1() {
		return DATE_FIELD1;
	}

	public void setDATE_FIELD1(Boolean dATE_FIELD1) {
		DATE_FIELD1 = dATE_FIELD1;
	}

	public Boolean getDATE_FIELD2() {
		return DATE_FIELD2;
	}

	public void setDATE_FIELD2(Boolean dATE_FIELD2) {
		DATE_FIELD2 = dATE_FIELD2;
	}

	public Boolean getDATE_FIELD3() {
		return DATE_FIELD3;
	}

	public void setDATE_FIELD3(Boolean dATE_FIELD3) {
		DATE_FIELD3 = dATE_FIELD3;
	}

	public Boolean getDATE_FIELD4() {
		return DATE_FIELD4;
	}

	public void setDATE_FIELD4(Boolean dATE_FIELD4) {
		DATE_FIELD4 = dATE_FIELD4;
	}

	public Boolean getDATE_FIELD5() {
		return DATE_FIELD5;
	}

	public void setDATE_FIELD5(Boolean dATE_FIELD5) {
		DATE_FIELD5 = dATE_FIELD5;
	}

	public Boolean getDATE_FIELD6() {
		return DATE_FIELD6;
	}

	public void setDATE_FIELD6(Boolean dATE_FIELD6) {
		DATE_FIELD6 = dATE_FIELD6;
	}

	public Boolean getDATE_FIELD7() {
		return DATE_FIELD7;
	}

	public void setDATE_FIELD7(Boolean dATE_FIELD7) {
		DATE_FIELD7 = dATE_FIELD7;
	}

	public Boolean getDATE_FIELD8() {
		return DATE_FIELD8;
	}

	public void setDATE_FIELD8(Boolean dATE_FIELD8) {
		DATE_FIELD8 = dATE_FIELD8;
	}

	public Boolean getDATE_FIELD9() {
		return DATE_FIELD9;
	}

	public void setDATE_FIELD9(Boolean dATE_FIELD9) {
		DATE_FIELD9 = dATE_FIELD9;
	}

	final public class LocalConstants
	{
		
		static final String CHAR_FIELD1="CHAR_FIELD1";
		static final String CHAR_FIELD2="CHAR_FIELD2";
		static final String CHAR_FIELD3="CHAR_FIELD3";
		static final String CHAR_FIELD4="CHAR_FIELD4";
		static final String CHAR_FIELD5="CHAR_FIELD5";
		static final String CHAR_FIELD6="CHAR_FIELD6";
		static final String CHAR_FIELD7="CHAR_FIELD7";
		static final String CHAR_FIELD8="CHAR_FIELD8";
		static final String CHAR_FIELD9="CHAR_FIELD9";
		static final String NUMERIC_FIELD1="NUMERIC_FIELD1";
		static final String NUMERIC_FIELD2="NUMERIC_FIELD2";
		static final String NUMERIC_FIELD3="NUMERIC_FIELD3";
		static final String NUMERIC_FIELD4="NUMERIC_FIELD4";
		static final String NUMERIC_FIELD5="NUMERIC_FIELD5";
		static final String NUMERIC_FIELD6="NUMERIC_FIELD6";
		static final String NUMERIC_FIELD7="NUMERIC_FIELD7";
		static final String NUMERIC_FIELD8="NUMERIC_FIELD8";
		static final String NUMERIC_FIELD9="NUMERIC_FIELD9";
		static final String DATE_FIELD1="DATE_FIELD1";
		static final String DATE_FIELD2="DATE_FIELD2";
		static final String DATE_FIELD3="DATE_FIELD3";
		static final String DATE_FIELD4="DATE_FIELD4";
		static final String DATE_FIELD5="DATE_FIELD5";
		static final String DATE_FIELD6="DATE_FIELD6";
		static final String DATE_FIELD7="DATE_FIELD7";
		static final String DATE_FIELD8="DATE_FIELD8";
		static final String DATE_FIELD9="DATE_FIELD9";
		static final String C= "C";
		public static final String D = "D";
		public static final String N = "N";
	}
	
	
	/*public void addToDataTable() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<String> flexiFieldList = parameterService.getFlexiFieldfromDB(getParameterMasterId());
		for (int i = 0; i < flexiFieldList.size(); i++) {
			String compareValue = flexiFieldList.get(i);
			LOGGER.info(compareValue);
			ParameterDefinition value_Indicator = parameterService.isValueIndicator(compareValue.trim(), getParameterMasterId());
			if (value_Indicator != null) {
				if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD1)) {
					charValue = getCharfield1();
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD2)) {
					charValue = getCharfield2();
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD3)) {
					charValue = getCharfield3();
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD4)) {
					charValue = getCharfield4();
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD5)) {
					charValue = getCharfield5();
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD6)) {
					charValue = getCharfield6();
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD7)) {
					charValue = getCharfield7();
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD8)) {
					charValue = getCharfield8();
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD9)) {
					charValue = getCharfield9();
				} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD1)) {
					dateValue = getDatefield1();
				} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD2)) {
					dateValue = getDatefield2();
				} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD3)) {
					dateValue = getDatefield3();
				} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD4)) {
					dateValue = getDatefield4();
				} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD5)) {
					dateValue = getDatefield5();
				} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD6)) {
					dateValue = getDatefield6();
				} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD7)) {
					dateValue = getDatefield7();
				} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD8)) {
					dateValue = getDatefield8();
				} else if (compareValue != null && compareValue.equals(LocalConstants.DATE_FIELD9)) {
					dateValue = getDatefield9();
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD1)) {
					numberValue = getNumericfield1();
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD2)) {
					numberValue = getNumericfield2();
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD3)) {
					numberValue = getNumericfield3();
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD4)) {
					numberValue = getNumericfield4();
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD5)) {
					numberValue = getNumericfield5();
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD6)) {
					numberValue = getNumericfield6();
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD7)) {
					numberValue = getNumericfield7();
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD8)) {
					numberValue = getNumericfield8();
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD9)) {
					numberValue = getNumericfield9();
				}
			}
		}
	}*/
}
