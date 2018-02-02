package com.amg.exchange.remittance.bean;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("parameterDetailsBean")
@Scope("session")
public class ParameterDetailsBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
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
	private String detailRecordId;
	private boolean ispopulate;
	private String flexFieldName;
	private String flexiFieldValue;
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
	private String parameterMasterDescription;
	private Boolean booradonlyforMasterDesciption;
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
	private ParameterDetailsDataTable selectedDocument;
	private String parameterCode;
	
	private Boolean booVisible;
	
	

	public Boolean getBooVisible() {
		return booVisible;
	}

	public void setBooVisible(Boolean booVisible) {
		this.booVisible = booVisible;
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

	public String getParameterMasterDescription() {
		return parameterMasterDescription;
	}

	public void setParameterMasterDescription(String parameterMasterDescription) {
		this.parameterMasterDescription = parameterMasterDescription;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Boolean getBooDefintionReadOnly() {
		return booDefintionReadOnly;
	}

	public void setBooDefintionReadOnly(Boolean booDefintionReadOnly) {
		this.booDefintionReadOnly = booDefintionReadOnly;
	}

	public Date getDateFlexiValue() {
		return dateFlexiValue;
	}

	public void setDateFlexiValue(Date dateFlexiValue) {
		this.dateFlexiValue = dateFlexiValue;
	}

	List<ParameterDefinition> lstDefinitionrMaster = new ArrayList<ParameterDefinition>();
	List<ParameterMaster> lstParameterMaster = new ArrayList<ParameterMaster>();
	List<FlexField> flexFieldList = new ArrayList<FlexField>();
	private Boolean booRenderDataTable = false;
	List<ParameterDetailsDataTable> finalList = new ArrayList<ParameterDetailsDataTable>();
	List<ParameterDetailsDataTable> lstFinalListForDataTable = new ArrayList<ParameterDetailsDataTable>();
	List<ParameterCode> lstParameterCode = new ArrayList<ParameterCode>();
	SessionStateManage session = new SessionStateManage();
	@Autowired
	IParameterService parameterService;

	// get and set methods
	public Boolean getRemarksCheck() {
		return remarksCheck;
	}

	public List<ParameterCode> getLstParameterCode() {
		return lstParameterCode;
	}

	public void setLstParameterCode(List<ParameterCode> lstParameterCode) {
		this.lstParameterCode = lstParameterCode;
	}

	public Boolean getBasedOnDate() {
		return basedOnDate;
	}

	public void setBasedOnDate(Boolean basedOnDate) {
		this.basedOnDate = basedOnDate;
	}

	public Boolean getBasedOnFlexiList() {
		return basedOnFlexiList;
	}

	public void setBasedOnFlexiList(Boolean basedOnFlexiList) {
		this.basedOnFlexiList = basedOnFlexiList;
	}

	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}

	public Boolean getPermanetDeleteCheck() {
		return permanetDeleteCheck;
	}

	public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
		this.permanetDeleteCheck = permanetDeleteCheck;
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

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
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
	//	List<ParameterMaster> templstParameterMaster =// parameterService.fetchAllParameterMasterForView();
				//lstParameterMaster=		 parameterService.fetchAllParameterMasterForView();
		return lstParameterMaster;
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

	/*
	 * public String getDataField() { LOGGER.info(
	 * "******************************************************************888");
	 * return dataField; }
	 * 
	 * public void setDataField(String dataField) { this.dataField = dataField;
	 * }
	 */
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
		/* lstDefinitionrMaster = parameterService.getAllRecords(); */
		/*
		 * List<String> StringList = new ArrayList<String>();
		 * 
		 * for (ParameterDefinition element : lstDefinitionrMaster) {
		 * 
		 * lstDefinitionrMaster.add(element);
		 * 
		 * }
		 * 
		 * List<ParameterDefinition> tempDefinitionrMaster = new
		 * ArrayList<ParameterDefinition>();
		 * 
		 * for (ParameterDefinition element : lstDefinitionrMaster) {
		 * 
		 * if(!StringList.contains(element.getRecordId())){
		 * StringList.add(element.getRecordId());
		 * tempDefinitionrMaster.add(element); }
		 * 
		 * } lstDefinitionrMaster.clear();
		 * lstDefinitionrMaster.addAll(tempDefinitionrMaster);
		 * StringList.clear();
		 */
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

	/*
	 * public String getCharField() { return charField; }
	 * 
	 * public void setCharField(String charField) { this.charField = charField;
	 * }
	 */
	public void save() {
	}
	
	public void getParameterMasterList()
 {
		try {
			List<ParameterGrant> grantList = parameterService.getGrantListfortheUser(session.getUserName());
			ArrayList<String> grantRecordList = new ArrayList<String>();
			if (grantList != null && !grantList.isEmpty()) {
				for (ParameterGrant grantObj : grantList) {
					grantRecordList.add(grantObj.getRecordId());
				}
				if (!grantRecordList.isEmpty()) {
					lstParameterMaster = parameterService.getUserRightsRecord(grantRecordList);
				}
			}
		} catch (Exception e) {
			LOGGER.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {
		setParameterCode(null);
		clearAll();
		clearRenderDyanamicFields();
		getParameterMasterList();
		ispopulate = false;
		lstFinalListForDataTable.clear();
		flexFieldList.clear();
		setBooRenderDataTable(false);
		setRenderEditButton(false);
		setBasedOnFlexiList(true);
		setBasedOnDate(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "parameterdetails.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/parameterdetails.xhtml");
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void getParameterMaster() {
		try {
			LOGGER.info("getParameterDefinitionId()" + getParameterDefinitionId());
			ParameterDefinition parameterDefinition = parameterService.viewByID(getParameterDefinitionId());
			if (parameterDefinition != null) {
				lstParameterMaster = parameterService.viewDefinitionListforDetails(parameterDefinition.getParameterMasterId().getParameterMasterId());
			}
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void populateParameterMaster() {
		LOGGER.info("Enter into populateParameterMaster method : ParameterMasterandDefinitionBean ");
		LOGGER.info("RecordId  " + getRecordId());
		setRenderEditButton(true);
		ispopulate = true;
	}

	// TO POPULATE THE RELATION CODE BASED ON GIVEN ENTRY
	public List<String> autoCompleteData(String query) {
		try {
			if (query.length() > 0) {
				List<ParameterDetails> ParameterList = parameterService.getAllDetailsComponent(query, getParameterMasterId());
				List<String> list = new ArrayList<String>();
				for (ParameterDetails parameter : ParameterList) {
					list.add(parameter.getParamCodeDef());
				}
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return null;
		}
	}

	public void getFlexiList() {
		try {
			FlexField temp = null;
			clearexceptDandM();
			flexFieldList.clear();
			List<ParameterDefinition> defanitionList = parameterService.getnewFlexiList(getParameterMasterId(), getParameterDefinitionId());
			for (ParameterDefinition parameterDefinition : defanitionList) {
				temp = new FlexField();
				temp.setColumnName(parameterDefinition.getFieldName());
				flexFieldList.add(temp);
			}
			if (flexFieldList.isEmpty()) {
				clearexceptDandM();
			}
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void getFlexiListforEdit() {
		FlexField temp = null;
		/* clearexceptDandM(); */
		flexFieldList.clear();
		List<ParameterDefinition> defanitionList = parameterService.getnewFlexiList(getParameterMasterId(), getParameterDefinitionId());
		for (ParameterDefinition parameterDefinition : defanitionList) {
			temp = new FlexField();
			temp.setColumnName(parameterDefinition.getFieldName());
			flexFieldList.add(temp);
		}
		/*
		 * if(flexFieldList.isEmpty()) { clearexceptDandM(); }
		 */
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
	
	
	public class NumbericField
 {
		private BigDecimal listValue;

		public BigDecimal getListValue() {
			return listValue;
		}

		public void setListValue(BigDecimal listValue) {
			this.listValue = listValue;
		}
	}
	
	

	public void addToDataTable() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			List<String> flexiFieldList = parameterService.getFlexiFieldfromDB(getParameterMasterId());
			
			
			if (booEdit != null && booEdit) {
				LOGGER.info("*****************************editable record************************" );
			} else {
				boolean isExistInDatabase = parameterService.isExistInDatabase(getParameterMasterId(), getParameterCode());
				if (isExistInDatabase) {
					setErrorMessage("Parameter Code already exist in the Database");
					LOGGER.info("*****************************************************" + getErrorMessage());
					RequestContext.getCurrentInstance().execute("csp.show();");
					return;
				}
				boolean isExistInDataTable = false;
				ParameterDetailsDataTable datatable = null;
				if (!lstFinalListForDataTable.isEmpty()) {
					for (int i = 0; i < lstFinalListForDataTable.size(); i++) {
						datatable = lstFinalListForDataTable.get(i);
						if (datatable.getParameterCode().equals(getParameterCode())) {
							isExistInDataTable = true;
							break;
						}
					}
				}
				if (isExistInDataTable) {
					setErrorMessage("Parameter Code already exist in the datatable");
					LOGGER.info("*****************************************************" + getErrorMessage());
					RequestContext.getCurrentInstance().execute("csp.show();");
					return;
				}
			}
			
			if(flexiFieldList != null){
				for (int i = 0; i < flexiFieldList.size(); i++) {
					String compareValue = flexiFieldList.get(i);
					LOGGER.info(compareValue);
					String charValue = null;
					BigDecimal numberValue = null;
					Date dateValue = null;
					ParameterDefinition value_Indicator = parameterService.isValueIndicatorEnabled(compareValue.trim(), getParameterMasterId());
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
						Boolean result = null;
						/* String errorString = ""; */
						Object checkValue = new Object();
						if (charValue != null) {
							checkValue = charValue;
							result = parameterService.checkDatabaseValue(checkValue, value_Indicator.getTableName(), value_Indicator.getTableField(), LocalConstants.C);
							/* errorString = charValue; */
						} else if (numberValue != null) {
							checkValue = numberValue;
							result = parameterService.checkDatabaseValue(checkValue, value_Indicator.getTableName(), value_Indicator.getTableField(),  LocalConstants.N);
							/* errorString = numberValue.toPlainString(); */
						} else if (dateValue != null) {
							checkValue = dateValue;
							result = parameterService.checkDatabaseValue(checkValue, value_Indicator.getTableName(), value_Indicator.getTableField(), LocalConstants.D);
							/* errorString = dateValue.toString(); */
						}
						if (result!=null && !result) {
							setErrorMessage("Invalid entry");
							LOGGER.info("*****************************************************" + getErrorMessage());
							RequestContext.getCurrentInstance().execute("csp.show();");
							return;
						}
					}
				}
			}
			
			
			
			ParameterDetailsDataTable dataTable = new ParameterDetailsDataTable();
			dataTable.setParameterDetailsId(getParameterDetailsId());
			dataTable.setArabicFullDesc(getArabicFullDesc());
			dataTable.setArabicShortDesc(getArabicShortDesc());
			dataTable.setParameterDefinitionId(getParameterDefinitionId());
			dataTable.setParamcodedefintion(getParameterCode());
			dataTable.setParameterCode(getParameterCode());
			dataTable.setParameterFullDesc(getParameterFullDesc());
			dataTable.setParameterShortDesc(getParameterShortDesc());
			dataTable.setParameterMasterId(getParameterMasterId());
			dataTable.setFlexFieldName(getFlexFieldName());
			dataTable.setModifiedBy(getModifiedBy());
			dataTable.setModifiedDate(getModifiedDate());
			dataTable.setFlexiFieldValue(getFlexiFieldValue());
			dataTable.setRenderEditButton(true);
			dataTable.setCharfield1(getCharfield1());
			dataTable.setCharfield2(getCharfield2());
			dataTable.setCharfield3(getCharfield3());
			dataTable.setCharfield4(getCharfield4());
			dataTable.setCharfield5(getCharfield5());
			dataTable.setCharfield6(getCharfield6());
			dataTable.setCharfield7(getCharfield7());
			dataTable.setCharfield8(getCharfield8());
			dataTable.setCharfield9(getCharfield9());
			dataTable.setDatefield1(getDatefield1());
			dataTable.setDatefield2(getDatefield2());
			dataTable.setDatefield3(getDatefield3());
			dataTable.setDatefield4(getDatefield4());
			dataTable.setDatefield5(getDatefield5());
			dataTable.setDatefield6(getDatefield6());
			dataTable.setDatefield7(getDatefield7());
			dataTable.setDatefield8(getDatefield8());
			dataTable.setDatefield9(getDatefield9());
			dataTable.setNumericfield1(getNumericfield1());
			dataTable.setNumericfield2(getNumericfield2());
			dataTable.setNumericfield3(getNumericfield3());
			dataTable.setNumericfield4(getNumericfield4());
			dataTable.setNumericfield5(getNumericfield5());
			dataTable.setNumericfield6(getNumericfield6());
			dataTable.setNumericfield7(getNumericfield7());
			dataTable.setNumericfield8(getNumericfield8());
			dataTable.setNumericfield9(getNumericfield9());
			dataTable.setRecordId(getRecordId());
			if (ispopulate) {
				dataTable.setCreatedDate(getCreatedDate());
				dataTable.setCreatedBy(getCreatedBy());
				dataTable.setIsActive(getIsActive());
				dataTable.setModifiedBy(getModifiedBy());
				dataTable.setModifiedDate(getModifiedDate());
				setRenderEditButton(true);
			} else {
				dataTable.setCreatedDate(new Date());
				dataTable.setCreatedBy(session.getUserName());
			}
			if (getParameterDetailsId() == null) {
				dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(dataTable.getIsActive()));
				dataTable.setIsActive(Constants.U);
			} else {
				dataTable.setIsActive(getIsActive());
				dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(dataTable.getIsActive()));
			}
			// dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(dataTable.getIsActive()));
			if (dataTable.getModifiedBy() != null && dataTable.getIsActive() != null && dataTable.getIsActive().equals("U")) {
				dataTable.setDynamicLabelForActivateDeactivate("");
			}
			/* setBooRenderDataTable(true); */
			setHideEdit(false);
			LOGGER.info("#######################################################");
			LOGGER.info(dataTable);
			LOGGER.info("#######################################################");
			
			if(booEdit!=null && booEdit)
			{
				setBooEdit(false);
			}
			lstFinalListForDataTable.add(dataTable);
/*	clearexceptMandD();*/
			/*setParameterDefinitionId(null);
			setParameterMasterId(null);*/
			setRenderEditButton(false);
			setHideEdit(false);
//	clearAllBBB();
//	saveToDataBase();
			setBooRenderDataTable(true);
			
			clearAllPopulateValuesforAddDataTable();
//	clearRenderDyanamicFields();
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	/*
	 * private void setCharValueOnFlexiFieldValue(String flexiFieldValue2,String
	 * charValue) {
	 * 
	 * char number = flexiFieldValue2.charAt(flexiFieldValue2.length()-1);
	 * LOGGER.info(number);
	 * 
	 * if(flexiFieldValue2.contains("CHAR") ) { if(number=='1') {
	 * setCharfield1(charValue); } else if(number=='2'){
	 * setCharfield2(charValue); } else if(number=='3'){
	 * setCharfield3(charValue); } else if(number=='4'){
	 * setCharfield4(charValue); } }
	 * 
	 * }
	 */
	/*
	 * private void clearAllexceptList() {
	 * 
	 * setCharfield1(null); setCharfield2(null); setCharfield3(null);
	 * setCharfield5(null); setCharfield6(null); setCharfield7(null);
	 * setCharfield8(null); setCharfield9(null);
	 * 
	 * setDatefield1(null); setDatefield2(null); setDatefield3(null);
	 * setDatefield4(null); setDatefield5(null); setDatefield6(null);
	 * setDatefield7(null); setDatefield8(null); setDatefield9(null);
	 * 
	 * setNumericfield1(null); setNumericfield2(null); setNumericfield3(null);
	 * setNumericfield4(null); setNumericfield5(null); setNumericfield6(null);
	 * setNumericfield7(null); setNumericfield8(null); setNumericfield9(null);
	 * clearexceptMandD(); setParameterMasterId(null);
	 * setParameterDefinitionId(null); setFlexFieldName(null);
	 * setFlexiFieldValue(null); setIsActive(null); setCreatedBy(null);
	 * setCreatedDate(null); setModifiedBy(null); setModifiedDate(null);
	 * setDateFlexiValue(null); clearexceptDandM(); setBasedOnDate(null);
	 * setBasedOnFlexiList(true); //clearList();
	 * 
	 * }
	 */
	
	private void clearAllPopulateValuesforAddDataTable() {
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
		setParameterCode(null);
		
	}
	
	
	
	
	private void clearAllPopulateValues() {
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
		setParameterDefinitionId(null);
		setIsActive(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setBasedOnDate(null);
		setBasedOnFlexiList(true);
		setParameterFullDesc(null);
		setParameterShortDesc(null);
		setArabicFullDesc(null);
		setArabicShortDesc(null);
		setFlexiFieldValue(null);
		setDateFlexiValue(null);
		/* setDataField(null); */
		setParameterDetailsId(null);
	}

	public void saveToDataBase() {
		for (ParameterDetailsDataTable datatable : lstFinalListForDataTable) {
			ParameterDetails details = new ParameterDetails();
			details.setArFullDesc(datatable.getArabicFullDesc());
			details.setArShortDesc(datatable.getArabicShortDesc());
			details.setCharField1(datatable.getCharfield1());
			details.setCharField2(datatable.getCharfield2());
			details.setCharField3(datatable.getCharfield3());
			details.setCharField4(datatable.getCharfield4());
			details.setCharField5(datatable.getCharfield5());
			details.setCharField6(datatable.getCharfield6());
			details.setCharField7(datatable.getCharfield7());
			details.setCharField8(datatable.getCharfield8());
			details.setCharField9(datatable.getCharfield9());
			details.setDateField1(datatable.getDatefield1());
			details.setDateField2(datatable.getDatefield2());
			details.setDateField3(datatable.getDatefield3());
			details.setDateField4(datatable.getDatefield4());
			details.setDateField5(datatable.getDatefield5());
			details.setDateField6(datatable.getDatefield6());
			details.setDateField7(datatable.getDatefield7());
			details.setDateField8(datatable.getDatefield8());
			details.setDateField9(datatable.getDatefield9());
			details.setNumericField1(datatable.getNumericfield1());
			details.setNumericField2(datatable.getNumericfield2());
			details.setNumericField3(datatable.getNumericfield3());
			details.setNumericField4(datatable.getNumericfield4());
			details.setNumericField5(datatable.getNumericfield5());
			details.setNumericField6(datatable.getNumericfield6());
			details.setNumericField7(datatable.getNumericfield7());
			details.setNumericField8(datatable.getNumericfield8());
			details.setNumericField9(datatable.getNumericfield9());
			details.setFullDesc(datatable.getParameterFullDesc());
			details.setShortDesc(datatable.getParameterShortDesc());
			details.setRecordId(datatable.getRecordId());
			details.setParamCodeDef(datatable.getParamcodedefintion());
			ParameterDefinition parameterDefinition = new ParameterDefinition();
			parameterDefinition.setParameterDefinitionId(datatable.getParameterDefinitionId());
			/* details.setParameterDefinitionId(null); */
			ParameterMaster parameterMaster = new ParameterMaster();
			parameterMaster.setParameterMasterId(datatable.getParameterMasterId());
			details.setParameterMasterId(parameterMaster);
			ParameterDefinition pd = new ParameterDefinition();
			pd.setParameterDefinitionId(datatable.getParameterMasterId());
			/* details.setParameterDefinitionId(pd); */
			details.setRecordId(datatable.getRecordId());
			details.setParameterDetailsId(datatable.getParameterDetailsId());
			details.setIsActive(datatable.getIsActive());
			details.setCreatedBy(datatable.getCreatedBy());
			details.setCreatedDate(datatable.getCreatedDate());
			details.setModifiedBy(datatable.getModifiedBy());
			details.setModifiedDate(datatable.getModifiedDate());
			details.setApprovedBy(datatable.getApprovedBy());
			details.setApprovedDate(datatable.getApprovedDate());
			details.setRemarks(datatable.getRemarks());
			LOGGER.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			LOGGER.info(datatable);
			LOGGER.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			parameterService.saveDetails(details);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("success.show();");
			details = null;
		}
	}

	public void fecthRecords() {
		List<Object> list = parameterService.getRecords(getParameterDefinitionId(), getParameterMasterId(), getFlexFieldName());
		if (getFlexFieldName().contains("CHAR") || getFlexFieldName().contains("NUMERIC")) {
			setBasedOnFlexiList(true);
			setBasedOnDate(false);
		} else {
			setBasedOnFlexiList(false);
			setBasedOnDate(true);
		}
		/*
		 * for (String string : list) { LOGGER.info("***********************" +
		 * string); }
		 */
		if (list != null && !list.isEmpty()) {
			for (Object string : list) {
				LOGGER.info("***********************" + string);
				LOGGER.info("***********************" + string == null);
			}
			String fieldValue = String.valueOf(list.get(0));
			if (!getFlexFieldName().contains("CHAR") || !getFlexFieldName().contains("NUMERIC")) {
				try {
					Calendar cal = Calendar.getInstance();
					String year = fieldValue.substring(0, 4);
					LOGGER.info(year);
					String mon = fieldValue.substring(5, 7);
					LOGGER.info(mon);
					String day = fieldValue.substring(8, 10);
					LOGGER.info(day);
					cal.set(Integer.parseInt(year), Integer.parseInt(mon) - 1, Integer.parseInt(day));
					/*
					 * d = new
					 * SimpleDateFormat("dd/MMM/yyyy").parse(cal.getTime());
					 */
					SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yy");
					LOGGER.info("@@@@@@@@@@@@ " + format1.format(cal.getTime()));
					setDateFlexiValue(cal.getTime());
					LOGGER.info(format1.format(cal.getTime()));
					fieldValue = format1.format(cal.getTime());
					LOGGER.info("############################ " + fieldValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (fieldValue == null) {
				LOGGER.info("Data not available");
				clearexceptMandD();
			} else {
				BigDecimal detailsId = parameterService.getDetails(getParameterDefinitionId(), getParameterMasterId(), getFlexFieldName(), fieldValue);
				if (detailsId != null) {
					ParameterDetails parameterDetails = parameterService.viewByDetailsID(detailsId);
					if (parameterDetails != null) {
						LOGGER.info(parameterDetails.getParamCodeDef());
						setParamcodedefintion(parameterDetails.getParamCodeDef());
						setParameterFullDesc(parameterDetails.getFullDesc());
						setParameterShortDesc(parameterDetails.getShortDesc());
						setArabicFullDesc(parameterDetails.getArFullDesc());
						setArabicShortDesc(parameterDetails.getArShortDesc());
						setFlexiFieldValue(fieldValue);
						/* setDataField(fieldValue); */
						setParameterDetailsId(parameterDetails.getParameterDetailsId());
						setCreatedBy(parameterDetails.getCreatedBy());
						setCreatedDate(parameterDetails.getCreatedDate());
						setIsActive(parameterDetails.getIsActive());
						setModifiedBy(parameterDetails.getModifiedBy());
						setModifiedDate(parameterDetails.getModifiedDate());
						setRemarks(parameterDetails.getRemarks());
						// setValues();
						ispopulate = true;
					}
				}
			}
		} else {
			clearexceptDandM();
			LOGGER.info("Data not available");
		}
	}

	public void clearAll() {
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
		
		setCharfieldLabel1(null);
		setCharfieldLabel2(null);
		setCharfieldLabel3(null);
		setCharfieldLabel4(null);
		setCharfieldLabel5(null);
		setCharfieldLabel6(null);
		setCharfieldLabel7(null);
		setCharfieldLabel8(null);
		setCharfieldLabel9(null);
		setDatefieldLabel1(null);
		setDatefieldLabel2(null);
		setDatefieldLabel3(null);
		setDatefieldLabel4(null);
		setDatefieldLabel5(null);
		setDatefieldLabel6(null);
		setDatefieldLabel7(null);
		setDatefieldLabel8(null);
		setDatefieldLabel9(null);
		setNumericfieldLabel1(null);
		setNumericfieldLabel2(null);
		setNumericfieldLabel3(null);
		setNumericfieldLabel4(null);
		setNumericfieldLabel5(null);
		setNumericfieldLabel6(null);
		setNumericfieldLabel7(null);
		setNumericfieldLabel8(null);
		setNumericfieldLabel9(null);
		
		
		clearexceptMandD();
		setParameterMasterId(null);
		setParameterDefinitionId(null);
		setFlexFieldName(null);
		setFlexiFieldValue(null);
		setIsActive(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		clearexceptDandM();
		clearList();
		setDateFlexiValue(null);
	}

	public void clearAllB() {
		clearRenderDyanamicFields();
		setParameterMasterId(null);
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
		
		
		setCharfieldLabel1(null);
		setCharfieldLabel2(null);
		setCharfieldLabel3(null);
		setCharfieldLabel4(null);
		setCharfieldLabel5(null);
		setCharfieldLabel6(null);
		setCharfieldLabel7(null);
		setCharfieldLabel8(null);
		setCharfieldLabel9(null);
		setDatefieldLabel1(null);
		setDatefieldLabel2(null);
		setDatefieldLabel3(null);
		setDatefieldLabel4(null);
		setDatefieldLabel5(null);
		setDatefieldLabel6(null);
		setDatefieldLabel7(null);
		setDatefieldLabel8(null);
		setDatefieldLabel9(null);
		setNumericfieldLabel1(null);
		setNumericfieldLabel2(null);
		setNumericfieldLabel3(null);
		setNumericfieldLabel4(null);
		setNumericfieldLabel5(null);
		setNumericfieldLabel6(null);
		setNumericfieldLabel7(null);
		setNumericfieldLabel8(null);
		setNumericfieldLabel9(null);
		
		
	
		
		
		
		clearexceptMandD();
		setParameterMasterId(null);
		setParameterDefinitionId(null);
		setFlexFieldName(null);
		setFlexiFieldValue(null);
		setIsActive(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setParameterCode(null);
		clearexceptDandM();
		/* clearList(); */
		lstDefinitionrMaster.clear();
		flexFieldList.clear();
		finalList.clear();
		setDateFlexiValue(null);
		pageNavigation();
	}

	public void clearAllBBB() {
		clearRenderDyanamicFields();
		setParameterMasterId(null);
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
		
		setCharfieldLabel1(null);
		setCharfieldLabel2(null);
		setCharfieldLabel3(null);
		setCharfieldLabel4(null);
		setCharfieldLabel5(null);
		setCharfieldLabel6(null);
		setCharfieldLabel7(null);
		setCharfieldLabel8(null);
		setCharfieldLabel9(null);
		setDatefieldLabel1(null);
		setDatefieldLabel2(null);
		setDatefieldLabel3(null);
		setDatefieldLabel4(null);
		setDatefieldLabel5(null);
		setDatefieldLabel6(null);
		setDatefieldLabel7(null);
		setDatefieldLabel8(null);
		setDatefieldLabel9(null);
		setNumericfieldLabel1(null);
		setNumericfieldLabel2(null);
		setNumericfieldLabel3(null);
		setNumericfieldLabel4(null);
		setNumericfieldLabel5(null);
		setNumericfieldLabel6(null);
		setNumericfieldLabel7(null);
		setNumericfieldLabel8(null);
		setNumericfieldLabel9(null);
		
		
		clearexceptMandD();
		setParameterMasterId(null);
		setParameterDefinitionId(null);
		setFlexFieldName(null);
		setFlexiFieldValue(null);
		setIsActive(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setParameterCode(null);
		clearexceptDandM();
		/* clearList(); */
		lstDefinitionrMaster.clear();
		flexFieldList.clear();
		finalList.clear();
		setDateFlexiValue(null);
	}

	public void clearList() {
		lstDefinitionrMaster.clear();
		lstFinalListForDataTable.clear();
		flexFieldList.clear();
		finalList.clear();
	}

	public void clearexceptMandD() {
		setParamcodedefintion(null);
		setParameterFullDesc(null);
		setParameterShortDesc(null);
		setArabicFullDesc(null);
		setArabicShortDesc(null);
		setFlexiFieldValue(null);
		/* setDataField(null); */
		setParameterDetailsId(null);
		setFlexFieldName(null);
		setDateFlexiValue(null);
		flexFieldList.clear();
	}

	public void clearexceptDandM() {
		setParamcodedefintion(null);
		setParameterFullDesc(null);
		setParameterShortDesc(null);
		setArabicFullDesc(null);
		setArabicShortDesc(null);
		setFlexiFieldValue(null);
		setDateFlexiValue(null);
		/* setDataField(null); */
		setParameterDetailsId(null);
		// setFlexFieldName(null);
		// flexFieldList.clear();
	}

	/*
	 * public void prepareValues() {
	 * 
	 * LOGGER.info("**********************");
	 * 
	 * LOGGER.info("Data field" + getDataField());
	 * 
	 * LOGGER.info("getFlexFieldName" + getFlexFieldName());
	 * LOGGER.info("**********************"); if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("CHAR_FIELD1")) {
	 * setCharfield1(getDataField()); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("CHAR_FIELD2")) {
	 * setCharfield2(getDataField()); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("CHAR_FIELD3")) {
	 * setCharfield3(getDataField()); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("CHAR_FIELD4")) {
	 * setCharfield4(getDataField()); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("CHAR_FIELD5")) {
	 * setCharfield5(getDataField()); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("CHAR_FIELD6")) {
	 * setCharfield6(getDataField()); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("CHAR_FIELD7")) {
	 * setCharfield7(getDataField()); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("CHAR_FIELD8")) {
	 * setCharfield8(getDataField()); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("CHAR_FIELD9")) {
	 * setCharfield9(getDataField()); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("NUMERIC_FIELD1") ) { setNumericfield1(new
	 * BigDecimal(getDataField())); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("NUMERIC_FIELD2") ) { setNumericfield2(new
	 * BigDecimal(getDataField())); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("NUMERIC_FIELD3") ) { setNumericfield3(new
	 * BigDecimal(getDataField())); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("NUMERIC_FIELD4") ) { setNumericfield4(new
	 * BigDecimal(getDataField())); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("NUMERIC_FIELD5") ) { setNumericfield5(new
	 * BigDecimal(getDataField())); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("NUMERIC_FIELD6") ) { setNumericfield6(new
	 * BigDecimal(getDataField())); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("NUMERIC_FIELD7") ) { setNumericfield7(new
	 * BigDecimal(getDataField())); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("NUMERIC_FIELD8") ) { setNumericfield8(new
	 * BigDecimal(getDataField())); } else if(getFlexFieldName()!= null &&
	 * getFlexFieldName().equals("NUMERIC_FIELD9") ) { setNumericfield9(new
	 * BigDecimal(getDataField())); } }
	 */
	Boolean CHAR_FIELD1;
	Boolean CHAR_FIELD2;
	Boolean CHAR_FIELD3;
	Boolean CHAR_FIELD4;
	Boolean CHAR_FIELD5;
	Boolean CHAR_FIELD6;
	Boolean CHAR_FIELD7;
	Boolean CHAR_FIELD8;
	Boolean CHAR_FIELD9;
	
	Boolean CHAR_FIELDLOV1;
	Boolean CHAR_FIELDLOV2;
	Boolean CHAR_FIELDLOV3;
	Boolean CHAR_FIELDLOV4;
	Boolean CHAR_FIELDLOV5;
	Boolean CHAR_FIELDLOV6;
	Boolean CHAR_FIELDLOV7;
	Boolean CHAR_FIELDLOV8;
	Boolean CHAR_FIELDLOV9;
	
	
	Boolean NUMERIC_FIELD1;
	Boolean NUMERIC_FIELD2;
	Boolean NUMERIC_FIELD3;
	Boolean NUMERIC_FIELD4;
	Boolean NUMERIC_FIELD5;
	Boolean NUMERIC_FIELD6;
	Boolean NUMERIC_FIELD7;
	Boolean NUMERIC_FIELD8;
	Boolean NUMERIC_FIELD9;
	
	
	Boolean NUMERIC_FIELDLOV1;
	Boolean NUMERIC_FIELDLOV2;
	Boolean NUMERIC_FIELDLOV3;
	Boolean NUMERIC_FIELDLOV4;
	Boolean NUMERIC_FIELDLOV5;
	Boolean NUMERIC_FIELDLOV6;
	Boolean NUMERIC_FIELDLOV7;
	Boolean NUMERIC_FIELDLOV8;
	Boolean NUMERIC_FIELDLOV9;
	
	Boolean DATE_FIELD1;
	Boolean DATE_FIELD2;
	Boolean DATE_FIELD3;
	Boolean DATE_FIELD4;
	Boolean DATE_FIELD5;
	Boolean DATE_FIELD6;
	Boolean DATE_FIELD7;
	Boolean DATE_FIELD8;
	Boolean DATE_FIELD9;
	

	public Boolean getCHAR_FIELDLOV1() {
		return CHAR_FIELDLOV1;
	}

	public void setCHAR_FIELDLOV1(Boolean cHAR_FIELDLOV1) {
		CHAR_FIELDLOV1 = cHAR_FIELDLOV1;
	}

	public Boolean getCHAR_FIELDLOV2() {
		return CHAR_FIELDLOV2;
	}

	public void setCHAR_FIELDLOV2(Boolean cHAR_FIELDLOV2) {
		CHAR_FIELDLOV2 = cHAR_FIELDLOV2;
	}

	public Boolean getCHAR_FIELDLOV3() {
		return CHAR_FIELDLOV3;
	}

	public void setCHAR_FIELDLOV3(Boolean cHAR_FIELDLOV3) {
		CHAR_FIELDLOV3 = cHAR_FIELDLOV3;
	}

	public Boolean getCHAR_FIELDLOV4() {
		return CHAR_FIELDLOV4;
	}

	public void setCHAR_FIELDLOV4(Boolean cHAR_FIELDLOV4) {
		CHAR_FIELDLOV4 = cHAR_FIELDLOV4;
	}

	public Boolean getCHAR_FIELDLOV5() {
		return CHAR_FIELDLOV5;
	}

	public void setCHAR_FIELDLOV5(Boolean cHAR_FIELDLOV5) {
		CHAR_FIELDLOV5 = cHAR_FIELDLOV5;
	}

	public Boolean getCHAR_FIELDLOV6() {
		return CHAR_FIELDLOV6;
	}

	public void setCHAR_FIELDLOV6(Boolean cHAR_FIELDLOV6) {
		CHAR_FIELDLOV6 = cHAR_FIELDLOV6;
	}

	public Boolean getCHAR_FIELDLOV7() {
		return CHAR_FIELDLOV7;
	}

	public void setCHAR_FIELDLOV7(Boolean cHAR_FIELDLOV7) {
		CHAR_FIELDLOV7 = cHAR_FIELDLOV7;
	}

	public Boolean getCHAR_FIELDLOV8() {
		return CHAR_FIELDLOV8;
	}

	public void setCHAR_FIELDLOV8(Boolean cHAR_FIELDLOV8) {
		CHAR_FIELDLOV8 = cHAR_FIELDLOV8;
	}

	public Boolean getCHAR_FIELDLOV9() {
		return CHAR_FIELDLOV9;
	}

	public void setCHAR_FIELDLOV9(Boolean cHAR_FIELDLOV9) {
		CHAR_FIELDLOV9 = cHAR_FIELDLOV9;
	}

	public Boolean getNUMERIC_FIELDLOV1() {
		return NUMERIC_FIELDLOV1;
	}

	public void setNUMERIC_FIELDLOV1(Boolean nUMERIC_FIELDLOV1) {
		NUMERIC_FIELDLOV1 = nUMERIC_FIELDLOV1;
	}

	public Boolean getNUMERIC_FIELDLOV2() {
		return NUMERIC_FIELDLOV2;
	}

	public void setNUMERIC_FIELDLOV2(Boolean nUMERIC_FIELDLOV2) {
		NUMERIC_FIELDLOV2 = nUMERIC_FIELDLOV2;
	}

	public Boolean getNUMERIC_FIELDLOV3() {
		return NUMERIC_FIELDLOV3;
	}

	public void setNUMERIC_FIELDLOV3(Boolean nUMERIC_FIELDLOV3) {
		NUMERIC_FIELDLOV3 = nUMERIC_FIELDLOV3;
	}

	public Boolean getNUMERIC_FIELDLOV4() {
		return NUMERIC_FIELDLOV4;
	}

	public void setNUMERIC_FIELDLOV4(Boolean nUMERIC_FIELDLOV4) {
		NUMERIC_FIELDLOV4 = nUMERIC_FIELDLOV4;
	}

	public Boolean getNUMERIC_FIELDLOV5() {
		return NUMERIC_FIELDLOV5;
	}

	public void setNUMERIC_FIELDLOV5(Boolean nUMERIC_FIELDLOV5) {
		NUMERIC_FIELDLOV5 = nUMERIC_FIELDLOV5;
	}

	public Boolean getNUMERIC_FIELDLOV6() {
		return NUMERIC_FIELDLOV6;
	}

	public void setNUMERIC_FIELDLOV6(Boolean nUMERIC_FIELDLOV6) {
		NUMERIC_FIELDLOV6 = nUMERIC_FIELDLOV6;
	}

	public Boolean getNUMERIC_FIELDLOV7() {
		return NUMERIC_FIELDLOV7;
	}

	public void setNUMERIC_FIELDLOV7(Boolean nUMERIC_FIELDLOV7) {
		NUMERIC_FIELDLOV7 = nUMERIC_FIELDLOV7;
	}

	public Boolean getNUMERIC_FIELDLOV8() {
		return NUMERIC_FIELDLOV8;
	}

	public void setNUMERIC_FIELDLOV8(Boolean nUMERIC_FIELDLOV8) {
		NUMERIC_FIELDLOV8 = nUMERIC_FIELDLOV8;
	}

	public Boolean getNUMERIC_FIELDLOV9() {
		return NUMERIC_FIELDLOV9;
	}

	public void setNUMERIC_FIELDLOV9(Boolean nUMERIC_FIELDLOV9) {
		NUMERIC_FIELDLOV9 = nUMERIC_FIELDLOV9;
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

	public void prepareValues() {
		LOGGER.info("**********************");
		LOGGER.info("Data field" + getFlexiFieldValue());
		LOGGER.info("getFlexFieldName" + getFlexFieldName());
		LOGGER.info("**********************");
		if (getFlexFieldName() != null && getFlexFieldName().equals("CHAR_FIELD1")) {
			setCharfield1(getFlexiFieldValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("CHAR_FIELD2")) {
			setCharfield2(getFlexiFieldValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("CHAR_FIELD3")) {
			setCharfield3(getFlexiFieldValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("CHAR_FIELD4")) {
			setCharfield4(getFlexiFieldValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("CHAR_FIELD5")) {
			setCharfield5(getFlexiFieldValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("CHAR_FIELD6")) {
			setCharfield6(getFlexiFieldValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("CHAR_FIELD7")) {
			setCharfield7(getFlexiFieldValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("CHAR_FIELD8")) {
			setCharfield8(getFlexiFieldValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("CHAR_FIELD9")) {
			setCharfield9(getFlexiFieldValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("NUMERIC_FIELD1")) {
			setNumericfield1(new BigDecimal(getFlexiFieldValue()));
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("NUMERIC_FIELD2")) {
			setNumericfield2(new BigDecimal(getFlexiFieldValue()));
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("NUMERIC_FIELD3")) {
			setNumericfield3(new BigDecimal(getFlexiFieldValue()));
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("NUMERIC_FIELD4")) {
			setNumericfield4(new BigDecimal(getFlexiFieldValue()));
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("NUMERIC_FIELD5")) {
			setNumericfield5(new BigDecimal(getFlexiFieldValue()));
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("NUMERIC_FIELD6")) {
			setNumericfield6(new BigDecimal(getFlexiFieldValue()));
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("NUMERIC_FIELD7")) {
			setNumericfield7(new BigDecimal(getFlexiFieldValue()));
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("NUMERIC_FIELD8")) {
			setNumericfield8(new BigDecimal(getFlexiFieldValue()));
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("NUMERIC_FIELD9")) {
			setNumericfield9(new BigDecimal(getFlexiFieldValue()));
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("DATE_FIELD1")) {
			//
			setDatefield1(getDateFlexiValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("DATE_FIELD1")) {
			setDatefield2(getDateFlexiValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("DATE_FIELD2")) {
			setDatefield2(getDatefield2());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("DATE_FIELD3")) {
			setDatefield3(getDateFlexiValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("DATE_FIELD4")) {
			setDatefield4(getDateFlexiValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("DATE_FIELD5")) {
			setDatefield5(getDateFlexiValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("DATE_FIELD6")) {
			setDatefield6(getDateFlexiValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("DATE_FIELD7")) {
			setDatefield7(getDateFlexiValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("DATE_FIELD8")) {
			setDatefield8(getDateFlexiValue());
		} else if (getFlexFieldName() != null && getFlexFieldName().equals("DATE_FIELD9")) {
			setDatefield9(getDateFlexiValue());
		}
	}

	public Date convertDate(String dateString) {
		Calendar cal = Calendar.getInstance();
		try {
			String year = dateString.substring(0, 4);
			LOGGER.info(year);
			String mon = dateString.substring(5, 7);
			LOGGER.info(mon);
			String day = dateString.substring(8, 10);
			LOGGER.info(day);
			cal.set(Integer.parseInt(year), Integer.parseInt(mon), Integer.parseInt(day));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cal.getTime();
	}

	public Date convertDateforEdit(String dateString) {
		Calendar cal = Calendar.getInstance();
		try {
			String year = dateString.substring(0, 4);
			LOGGER.info(year);
			String mon = dateString.substring(5, 7);
			LOGGER.info(mon);
			String day = dateString.substring(8, 10);
			LOGGER.info(day);
			cal.set(Integer.parseInt(year), Integer.parseInt(mon) - 1, Integer.parseInt(day));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cal.getTime();
	}

	public void view() {
	try {
		/*	clearexceptMandD();*/
			LOGGER.info("********view***********");
			if (getParameterMasterId() == null /*
												 * || getParameterMasterId()==null
												 * || getFlexFieldName()==null
												 */) {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("please.show();");
				return;
			}
			lstFinalListForDataTable.clear();
			/* clearAll(); */
			LOGGER.info("getParameterDefinitionId() " + getParameterDefinitionId());
			LOGGER.info("getParameterMasterId()  " + getParameterMasterId());
			List<ParameterDetails> list = parameterService.getRecordsbasedOnMasterId(getParameterMasterId());
			// List<ParameterDefinition> definitionlist =
			// parameterService.getFlexiList(getParameterMasterId());
			/*
			 * for (ParameterDefinition parameterDefinition : definitionlist) {
			 * 
			 * }
			 */
			if (list != null && !list.isEmpty()) {
				
				
				List<ParameterDefinition> flexiFieldList = parameterService.getFlexiFieldfromDBWithList(getParameterMasterId());
				boolean isDataBaseEnabled = false;
				String tableName = new String();
				String columnName = new String();
				String databaseValueIndicator = new String();
				for (int i = 0; i < flexiFieldList.size(); i++) {
					String compareValue = flexiFieldList.get(i).getFieldName();
					String labelValue = flexiFieldList.get(i).getFullDesc();
					databaseValueIndicator = flexiFieldList.get(i).getValueIndicator();
					isDataBaseEnabled = false;
					tableName = null;
					columnName = null;
					if (databaseValueIndicator.equals(Constants.D)) {
						isDataBaseEnabled = true;
						tableName = flexiFieldList.get(i).getTableName();
						columnName = flexiFieldList.get(i).getTableField();
					}
					LOGGER.info(compareValue);
					if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD1)) {
						if (isDataBaseEnabled) {
							setCHAR_FIELDLOV1(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<String> tempList = parameterService.getStringValueList(tableName, columnName);
								CHAR_FIELD1LOVList = convertStringList(tempList);
							}
						} else {
							setCHAR_FIELD1(true);
						}
						setCharfieldLabel1(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD2)) {
						if (isDataBaseEnabled) {
							setCHAR_FIELDLOV2(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<String> tempList = parameterService.getStringValueList(tableName, columnName);
								CHAR_FIELD2LOVList = convertStringList(tempList);
							}
						} else {
							setCHAR_FIELD2(true);
						}
						setCharfieldLabel2(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD3)) {
						if (isDataBaseEnabled) {
							setCHAR_FIELDLOV3(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<String> tempList = parameterService.getStringValueList(tableName, columnName);
								CHAR_FIELD3LOVList = convertStringList(tempList);
							}
						} else {
							setCHAR_FIELD3(true);
						}
						setCharfieldLabel3(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD4)) {
						if (isDataBaseEnabled) {
							setCHAR_FIELDLOV4(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<String> tempList = parameterService.getStringValueList(tableName, columnName);
								CHAR_FIELD4LOVList = convertStringList(tempList);
							}
						} else {
							setCHAR_FIELD4(true);
						}
						setCharfieldLabel4(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD5)) {
						if (isDataBaseEnabled) {
							setCHAR_FIELDLOV5(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<String> tempList = parameterService.getStringValueList(tableName, columnName);
								CHAR_FIELD5LOVList = convertStringList(tempList);
							}
						} else {
							setCHAR_FIELD5(true);
						}
						setCharfieldLabel5(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD6)) {
						if (isDataBaseEnabled) {
							setCHAR_FIELDLOV6(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<String> tempList = parameterService.getStringValueList(tableName, columnName);
								CHAR_FIELD6LOVList = convertStringList(tempList);
							}
						} else {
							setCHAR_FIELD6(true);
						}
						setCharfieldLabel6(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD7)) {
						if (isDataBaseEnabled) {
							setCHAR_FIELDLOV7(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<String> tempList = parameterService.getStringValueList(tableName, columnName);
								CHAR_FIELD7LOVList = convertStringList(tempList);
							}
						} else {
							setCHAR_FIELD7(true);
						}
						setCharfieldLabel7(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD8)) {
						if (isDataBaseEnabled) {
							setCHAR_FIELDLOV8(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<String> tempList = parameterService.getStringValueList(tableName, columnName);
								CHAR_FIELD8LOVList = convertStringList(tempList);
							}
						} else {
							setCHAR_FIELD8(true);
						}
						setCharfieldLabel8(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD9)) {
						if (isDataBaseEnabled) {
							setCHAR_FIELDLOV9(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<String> tempList = parameterService.getStringValueList(tableName, columnName);
								CHAR_FIELD9LOVList = convertStringList(tempList);
							}
						} else {
							setCHAR_FIELD9(true);
						}
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
					} 
					
					
					
					else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD1)) {
						if (isDataBaseEnabled) {
							setNUMERIC_FIELDLOV1(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
								NUMERIC_FIELD1LOVList = convertNUmberList(tempList);
							}
						} else {
							setNUMERIC_FIELD1(true);
						}
						setNumericfieldLabel1(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD2)) {
						
						if (isDataBaseEnabled) {
							setNUMERIC_FIELDLOV2(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
								NUMERIC_FIELD2LOVList = convertNUmberList(tempList);
							}
						} else {
							setNUMERIC_FIELD2(true);
						}
						setNumericfieldLabel2(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD3)) {
						
						if (isDataBaseEnabled) {
							setNUMERIC_FIELDLOV3(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
								NUMERIC_FIELD3LOVList = convertNUmberList(tempList);
							}
						} else {
							setNUMERIC_FIELD3(true);
						}
						
						
						
						
						
						
						setNumericfieldLabel3(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD4)) {
						
						if (isDataBaseEnabled) {
							setNUMERIC_FIELDLOV4(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
								NUMERIC_FIELD4LOVList = convertNUmberList(tempList);
							}
						} else {
							setNUMERIC_FIELD4(true);
						}
						
						
						
						
						setNumericfieldLabel4(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD5)) {
						
						
						if (isDataBaseEnabled) {
							setNUMERIC_FIELDLOV5(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
								NUMERIC_FIELD5LOVList = convertNUmberList(tempList);
							}
						} else {
							setNUMERIC_FIELD5(true);
						}
						
						
						
					
						setNumericfieldLabel5(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD6)) {
						
						
						if (isDataBaseEnabled) {
							setNUMERIC_FIELDLOV6(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
								NUMERIC_FIELD6LOVList = convertNUmberList(tempList);
							}
						} else {
							setNUMERIC_FIELD6(true);
						}
						
						
					
						setNumericfieldLabel6(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD7)) {
						
						if (isDataBaseEnabled) {
							setNUMERIC_FIELDLOV7(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
								NUMERIC_FIELD7LOVList = convertNUmberList(tempList);
							}
						} else {
							setNUMERIC_FIELD7(true);
						}
						
						
						
						
						
						setNumericfieldLabel7(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD8)) {
						
						
						if (isDataBaseEnabled) {
							setNUMERIC_FIELDLOV8(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
								NUMERIC_FIELD8LOVList = convertNUmberList(tempList);
							}
						} else {
							setNUMERIC_FIELD8(true);
						}
						
						
						
						setNumericfieldLabel8(labelValue);
					} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD9)) {
						
						
						if (isDataBaseEnabled) {
							setNUMERIC_FIELDLOV9(true);
							if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
								List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
								NUMERIC_FIELD9LOVList = convertNUmberList(tempList);
							}
						} else {
							setNUMERIC_FIELD9(true);
						}
						
						
					
						setNumericfieldLabel9(labelValue);
					}
				}
				
				
				
				
				setRenderEditButton(true);
				setHideEdit(true);
				// List<ParameterDetails> detailsList =
				// parameterService.getRecords(getParameterDefinitionId(),
				// getParameterMasterId());
				setBooRenderDataTable(true);
				ParameterDetailsDataTable datatable = null;
				for (ParameterDetails parameterDetails : list) {
					datatable = new ParameterDetailsDataTable();
					LOGGER.info(parameterDetails.getParameterDetailsId());
					LOGGER.info(parameterDetails.getParamCodeDef());
					LOGGER.info(parameterDetails.getParameterDetailsId());
					datatable.setRecordId(parameterDetails.getRecordId());
					datatable.setParameterMasterId(parameterDetails.getParameterMasterId().getParameterMasterId());
					datatable.setParameterDefinitionId(parameterDetails.getParameterMasterId().getParameterMasterId());
					datatable.setParamcodedefintion(parameterDetails.getParamCodeDef());
					datatable.setParameterFullDesc(parameterDetails.getFullDesc());
					datatable.setParameterShortDesc(parameterDetails.getShortDesc());
					datatable.setParameterCode(parameterDetails.getParamCodeDef());
					datatable.setCHAR_FIELDLOV1(true);
					datatable.setArabicFullDesc(parameterDetails.getArFullDesc());
					datatable.setArabicShortDesc(parameterDetails.getArShortDesc());
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
					datatable.setIsActive(parameterDetails.getIsActive());
					datatable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(parameterDetails.getIsActive()));
					if (parameterDetails.getIsActive().equals(Constants.U) && parameterDetails.getModifiedBy() != null && parameterDetails.getModifiedDate() != null) {
						datatable.setDynamicLabelForActivateDeactivate("");
					}
					datatable.setRenderEditButton(true);
					datatable.setApprovedBy(parameterDetails.getApprovedBy());
					datatable.setApprovedDate(parameterDetails.getApprovedDate());
					lstFinalListForDataTable.add(datatable);
					parameterDetails = null;
					datatable = null;
				}
			} else {
				LOGGER.info("Record not found");
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("notfound.show();");
				return;
			}
	} catch (Exception e) {
		setBooVisible(true);
		RequestContext.getCurrentInstance().execute("csp.show();");
		setErrorMessage("Exception ocurred" + e);
		return;	}
	}

	private String setreverActiveStatus(String status) {
		String strStatus = null;
		if (status == null) {
			strStatus = "Remove";
		} else if (status.equalsIgnoreCase("U")) {
			strStatus = "Delete";
		} else if (status.equalsIgnoreCase("D")) {
			strStatus = "Activate";
		} else if (status.equalsIgnoreCase("Y")) {
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

	public void removeRecord(ParameterDetailsDataTable dataTable) {
		try {
			if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
				lstFinalListForDataTable.remove(dataTable);
				if (lstFinalListForDataTable.isEmpty()) {
					setBooRenderDataTable(false);
				}
			} else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null && dataTable.getApprovedBy() == null && dataTable.getApprovedDate() == null && dataTable.getRemarks() == null) {
				dataTable.setPermanetDeleteCheck(true);
				RequestContext.getCurrentInstance().execute("permanentDelete.show();");
				return;
			} else if ((dataTable.getDynamicLabelForActivateDeactivate() != null && (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)))) {
				dataTable.setRemarksCheck(true);
				setIsActive(dataTable.getIsActive());
				dataTable.setRemarks(getRemarks());
				setApprovedBy(dataTable.getApprovedBy());
				setApprovedDate(dataTable.getApprovedDate());
				RequestContext.getCurrentInstance().execute("remarks.show();");
				return;
			} else if ((dataTable.getDynamicLabelForActivateDeactivate() != null && (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)))) {
				changeStatus(dataTable);
				lstFinalListForDataTable.clear();
				view();
			}
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	/*
	 * if ((dataTable.getDynamicLabelForActivateDeactivate() != null &&
	 * (dataTable
	 * .getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants
	 * .DELETE)) ||
	 * dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase
	 * (Constants.REMOVE))) { if (dataTable.getParameterDetailsId() != null) {
	 * 
	 * } }
	 */
	Boolean booEdit;
	
	
	public Boolean getBooEdit() {
		return booEdit;
	}

	public void setBooEdit(Boolean booEdit) {
		this.booEdit = booEdit;
	}

	public void editRecord(ParameterDetailsDataTable dataTable) {
		setRenderEditButton(true);
		setHideEdit(true);
		setBooEdit(true);
		setParameterDetailsId(dataTable.getParameterDetailsId());
		setArabicFullDesc(dataTable.getArabicFullDesc());
		setArabicShortDesc(dataTable.getArabicShortDesc());
		setParameterDefinitionId(dataTable.getParameterDefinitionId());
		setParamcodedefintion(dataTable.getParamcodedefintion());
		setParameterFullDesc(dataTable.getParameterFullDesc());
		setParameterShortDesc(dataTable.getParameterShortDesc());
		setParameterMasterId(dataTable.getParameterMasterId());
		setParameterCode(dataTable.getParameterCode());
		setModifiedBy(dataTable.getModifiedBy());
		setModifiedDate(dataTable.getModifiedDate());
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
		setRenderEditButton(true);
		setCreatedDate(dataTable.getCreatedDate());
		setCreatedBy(dataTable.getCreatedBy());
		setIsActive(Constants.U);
		setModifiedBy(session.getUserName());
		setModifiedDate(new Date());
		lstFinalListForDataTable.remove(dataTable);
		if (lstFinalListForDataTable.isEmpty()) {
			setBooRenderDataTable(false);
		}
	}

	public void confirmPermanentDelete() {
		try {
			if (lstFinalListForDataTable.size() > 0) {
				ParameterDetailsDataTable dataTable = null;
				List<ParameterDetailsDataTable> tempist = new CopyOnWriteArrayList<ParameterDetailsDataTable>();
				tempist.clear();
				for (ParameterDetailsDataTable dTable : lstFinalListForDataTable) {
					tempist.add(dTable);
				}
				Iterator<ParameterDetailsDataTable> iter = tempist.iterator();
				while (iter.hasNext()) {
					dataTable = iter.next();
					if (dataTable.getPermanetDeleteCheck() != null && dataTable.getPermanetDeleteCheck().equals(true)) {
						tempist.remove(dataTable);
						delete(dataTable);
					}
				}
				lstFinalListForDataTable.clear();
				for (ParameterDetailsDataTable dTable2 : tempist) {
					lstFinalListForDataTable.add(dTable2);
				}
				if (lstFinalListForDataTable.isEmpty()) {
					setBooRenderDataTable(false);
				}
			}
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void delete(ParameterDetailsDataTable dataTable) {
		try {
			try {
				ParameterDetails details = new ParameterDetails();
				details.setParameterDetailsId(dataTable.getParameterDetailsId());
				ParameterMaster master = new ParameterMaster();
				master.setParameterMasterId(dataTable.getParameterMasterId());
				parameterService.delete(details);
				RequestContext.getCurrentInstance().execute("deleted.show();");
			} catch (Exception e) {
				LOGGER.info("Exception occured" + e);
			}
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void cancelRemarks() {
		LOGGER.info("Entering into cancelRemarks method");
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/parameterdetails.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into cancelRemarks method");
	}

	public void updateStatusoftheRecord() throws IOException {
		try {
			if (getRemarks() != null && !getRemarks().equals("")) {
				for (ParameterDetailsDataTable dataTable : lstFinalListForDataTable) {
					if (dataTable.getRemarksCheck() != null) {
						if (dataTable.getRemarksCheck().equals(true)) {
							dataTable.setRemarks(getRemarks());
							changeStatus(dataTable);
							setRemarks(null);
						}
					}
				}
				try {
					view();
					FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/parameterdetails.xhtml");
				} catch (Exception e) {
				}
			} else {
				RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
			}
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	private void changeStatus(ParameterDetailsDataTable dataTable) {
		try {
			String status = "";
			if (dataTable.getDynamicLabelForActivateDeactivate().equals(Constants.DEACTIVATE)) {
				status = Constants.D;
			} else if (dataTable.getDynamicLabelForActivateDeactivate().equals(Constants.ACTIVATE)) {
				status = Constants.U;
			}
			String returnStatus = parameterService.updateStaus(dataTable.getParameterDetailsId(), session.getUserName(), status, dataTable.getRemarks());
			LOGGER.info(returnStatus);
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void getMasterList() {
		try {
			
			clearexceptMandD();
			clearList();
			LOGGER.info("Entering into getDefinitionList method");
			ParameterMaster pm = parameterService.viewParameterMasterById(getParameterMasterId());
			setRecordId(pm.getRecordId());
			setParameterMasterDescription(pm.getFullDesc());
			//setParameterFullDesc(pm.getFullDesc());
			//setParameterShortDesc(pm.getShortDesc());
			//setArabicFullDesc(pm.getLocalFullDesc());
			//setArabicShortDesc(pm.getLocalShortDesc());
			setBooradonlyforMasterDesciption(false);
			LOGGER.info("Exit into getDefinitionList method");
			clearRenderDyanamicFields();

			setParameterCode(null);
			setBooRenderDataTable(false);
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public static boolean isNumeric(String str) {
		try {
			@SuppressWarnings("unused")
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public void populateDetails() {
		try {
			LOGGER.info("Entering into populateDetails method ");
			ParameterDetails details = parameterService.populateDetails(getParameterMasterId(), getParamcodedefintion());
			if (details != null) {
				setParameterDetailsId(details.getParameterDetailsId());
				setArabicFullDesc(details.getArFullDesc());
				setArabicShortDesc(details.getArShortDesc());
				/*
				 * setParameterDefinitionId(details.getParameterDefinitionId().
				 * getParameterDefinitionId());
				 */
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
				clearAllPopulateValues();
			}
			LOGGER.info("Exit into populateDetails method ");
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public class ParameterCode {
		private String pcode;

		public String getPcode() {
			return pcode;
		}

		public void setPcode(String pcode) {
			this.pcode = pcode;
		}
	}

	public void getEnableFlexifiled() {
		try {
			LOGGER.info("Master id " + getParameterMasterId());
			LOGGER.info("parameter code " + getParameterCode());
			ParameterDetails details = parameterService.populateDetails(getParameterMasterId(), getParameterCode());
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
				setParameterDetailsId(null);
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
			boolean isDataBaseEnabled = false;
			String tableName = new String();
			String columnName = new String();
			String databaseValueIndicator = new String();
			for (int i = 0; i < flexiFieldList.size(); i++) {
				String compareValue = flexiFieldList.get(i).getFieldName();
				String labelValue = flexiFieldList.get(i).getFullDesc();
				databaseValueIndicator = flexiFieldList.get(i).getValueIndicator();
				isDataBaseEnabled = false;
				tableName = null;
				columnName = null;
				if (databaseValueIndicator.equals(Constants.D)) {
					isDataBaseEnabled = true;
					tableName = flexiFieldList.get(i).getTableName();
					columnName = flexiFieldList.get(i).getTableField();
				}
				LOGGER.info(compareValue);
				if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD1)) {
					if (isDataBaseEnabled) {
						setCHAR_FIELDLOV1(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<String> tempList = parameterService.getStringValueList(tableName, columnName);
							CHAR_FIELD1LOVList = convertStringList(tempList);
						}
					} else {
						setCHAR_FIELD1(true);
					}
					setCharfieldLabel1(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD2)) {
					if (isDataBaseEnabled) {
						setCHAR_FIELDLOV2(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<String> tempList = parameterService.getStringValueList(tableName, columnName);
							CHAR_FIELD2LOVList = convertStringList(tempList);
						}
					} else {
						setCHAR_FIELD2(true);
					}
					setCharfieldLabel2(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD3)) {
					if (isDataBaseEnabled) {
						setCHAR_FIELDLOV3(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<String> tempList = parameterService.getStringValueList(tableName, columnName);
							CHAR_FIELD3LOVList = convertStringList(tempList);
						}
					} else {
						setCHAR_FIELD3(true);
					}
					setCharfieldLabel3(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD4)) {
					if (isDataBaseEnabled) {
						setCHAR_FIELDLOV4(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<String> tempList = parameterService.getStringValueList(tableName, columnName);
							CHAR_FIELD4LOVList = convertStringList(tempList);
						}
					} else {
						setCHAR_FIELD4(true);
					}
					setCharfieldLabel4(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD5)) {
					if (isDataBaseEnabled) {
						setCHAR_FIELDLOV5(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<String> tempList = parameterService.getStringValueList(tableName, columnName);
							CHAR_FIELD5LOVList = convertStringList(tempList);
						}
					} else {
						setCHAR_FIELD5(true);
					}
					setCharfieldLabel5(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD6)) {
					if (isDataBaseEnabled) {
						setCHAR_FIELDLOV6(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<String> tempList = parameterService.getStringValueList(tableName, columnName);
							CHAR_FIELD6LOVList = convertStringList(tempList);
						}
					} else {
						setCHAR_FIELD6(true);
					}
					setCharfieldLabel6(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD7)) {
					if (isDataBaseEnabled) {
						setCHAR_FIELDLOV7(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<String> tempList = parameterService.getStringValueList(tableName, columnName);
							CHAR_FIELD7LOVList = convertStringList(tempList);
						}
					} else {
						setCHAR_FIELD7(true);
					}
					setCharfieldLabel7(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD8)) {
					if (isDataBaseEnabled) {
						setCHAR_FIELDLOV8(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<String> tempList = parameterService.getStringValueList(tableName, columnName);
							CHAR_FIELD8LOVList = convertStringList(tempList);
						}
					} else {
						setCHAR_FIELD8(true);
					}
					setCharfieldLabel8(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.CHAR_FIELD9)) {
					if (isDataBaseEnabled) {
						setCHAR_FIELDLOV9(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<String> tempList = parameterService.getStringValueList(tableName, columnName);
							CHAR_FIELD9LOVList = convertStringList(tempList);
						}
					} else {
						setCHAR_FIELD9(true);
					}
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
				} 
				
				
				
				else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD1)) {
					if (isDataBaseEnabled) {
						setNUMERIC_FIELDLOV1(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
							NUMERIC_FIELD1LOVList = convertNUmberList(tempList);
						}
					} else {
						setNUMERIC_FIELD1(true);
					}
					setNumericfieldLabel1(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD2)) {
					
					if (isDataBaseEnabled) {
						setNUMERIC_FIELDLOV2(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
							NUMERIC_FIELD2LOVList = convertNUmberList(tempList);
						}
					} else {
						setNUMERIC_FIELD2(true);
					}
					setNumericfieldLabel2(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD3)) {
					
					if (isDataBaseEnabled) {
						setNUMERIC_FIELDLOV3(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
							NUMERIC_FIELD3LOVList = convertNUmberList(tempList);
						}
					} else {
						setNUMERIC_FIELD3(true);
					}
					
					
					
					
					
					
					setNumericfieldLabel3(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD4)) {
					
					if (isDataBaseEnabled) {
						setNUMERIC_FIELDLOV4(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
							NUMERIC_FIELD4LOVList = convertNUmberList(tempList);
						}
					} else {
						setNUMERIC_FIELD4(true);
					}
					
					
					
					
					setNumericfieldLabel4(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD5)) {
					
					
					if (isDataBaseEnabled) {
						setNUMERIC_FIELDLOV5(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
							NUMERIC_FIELD5LOVList = convertNUmberList(tempList);
						}
					} else {
						setNUMERIC_FIELD5(true);
					}
					
					
					
				
					setNumericfieldLabel5(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD6)) {
					
					
					if (isDataBaseEnabled) {
						setNUMERIC_FIELDLOV6(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
							NUMERIC_FIELD6LOVList = convertNUmberList(tempList);
						}
					} else {
						setNUMERIC_FIELD6(true);
					}
					
					
				
					setNumericfieldLabel6(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD7)) {
					
					if (isDataBaseEnabled) {
						setNUMERIC_FIELDLOV7(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
							NUMERIC_FIELD7LOVList = convertNUmberList(tempList);
						}
					} else {
						setNUMERIC_FIELD7(true);
					}
					
					
					
					
					
					setNumericfieldLabel7(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD8)) {
					
					
					if (isDataBaseEnabled) {
						setNUMERIC_FIELDLOV8(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
							NUMERIC_FIELD8LOVList = convertNUmberList(tempList);
						}
					} else {
						setNUMERIC_FIELD8(true);
					}
					
					
					
					setNumericfieldLabel8(labelValue);
				} else if (compareValue != null && compareValue.equals(LocalConstants.NUMERIC_FIELD9)) {
					
					
					if (isDataBaseEnabled) {
						setNUMERIC_FIELDLOV9(true);
						if (tableName != null && !tableName.equals("") && columnName != null && !columnName.equals("")) {
							List<BigDecimal> tempList = parameterService.getValueList(tableName, columnName);
							NUMERIC_FIELD9LOVList = convertNUmberList(tempList);
						}
					} else {
						setNUMERIC_FIELD9(true);
					}
					
					
				
					setNumericfieldLabel9(labelValue);
				}
			}
			
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}
	

	private List<FlexField> convertStringList(List<String> tempList) {
		List<FlexField> finalList = new ArrayList<FlexField>();
		
		for (String string : tempList) {
			FlexField str = new FlexField();
			str.setColumnName(string);
			finalList.add(str);
		}
		return finalList;
	}

	private List<NumbericField> convertNUmberList(List<BigDecimal> tempList) {
		List<NumbericField> finalList = new ArrayList<NumbericField>();
		for (BigDecimal big : tempList) {
			NumbericField num = new NumbericField();
			num.setListValue(big);
			finalList.add(num);
		}
		return finalList;
	}

	public void clearRenderDyanamicFields() {
		setCHAR_FIELD1(null);
		setCHAR_FIELD2(null);
		setCHAR_FIELD3(null);
		setCHAR_FIELD4(null);
		setCHAR_FIELD5(null);
		setCHAR_FIELD6(null);
		setCHAR_FIELD7(null);
		setCHAR_FIELD8(null);
		setCHAR_FIELD9(null);
		setDATE_FIELD1(null);
		setDATE_FIELD2(null);
		setDATE_FIELD3(null);
		setDATE_FIELD4(null);
		setDATE_FIELD5(null);
		setDATE_FIELD6(null);
		setDATE_FIELD7(null);
		setDATE_FIELD8(null);
		setDATE_FIELD9(null);
		setNUMERIC_FIELD1(null);
		setNUMERIC_FIELD2(null);
		setNUMERIC_FIELD3(null);
		setNUMERIC_FIELD4(null);
		setNUMERIC_FIELD5(null);
		setNUMERIC_FIELD6(null);
		setNUMERIC_FIELD7(null);
		setNUMERIC_FIELD8(null);
		setNUMERIC_FIELD9(null);
		setNUMERIC_FIELDLOV1(null);
		setNUMERIC_FIELDLOV2(null);
		setNUMERIC_FIELDLOV3(null);
		setNUMERIC_FIELDLOV4(null);
		setNUMERIC_FIELDLOV5(null);
		setNUMERIC_FIELDLOV6(null);
		setNUMERIC_FIELDLOV7(null);
		setNUMERIC_FIELDLOV8(null);
		setNUMERIC_FIELDLOV9(null);
		setCHAR_FIELDLOV1(null);
		setCHAR_FIELDLOV2(null);
		setCHAR_FIELDLOV3(null);
		setCHAR_FIELDLOV4(null);
		setCHAR_FIELDLOV5(null);
		setCHAR_FIELDLOV6(null);
		setCHAR_FIELDLOV7(null);
		setCHAR_FIELDLOV8(null);
		setCHAR_FIELDLOV9(null);
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
		
		
		
		
		static final String CHAR_FIELD1LOV="CHAR_FIELD1LOV";
		static final String CHAR_FIELD2LOV="CHAR_FIELD2LOV";
		static final String CHAR_FIELD3LOV="CHAR_FIELD3LOV";
		static final String CHAR_FIELD4LOV="CHAR_FIELD4LOV";
		static final String CHAR_FIELD5LOV="CHAR_FIELD5LOV";
		static final String CHAR_FIELD6LOV="CHAR_FIELD6LOV";
		static final String CHAR_FIELD7LOV="CHAR_FIELD7LOV";
		static final String CHAR_FIELD8LOV="CHAR_FIELD8LOV";
		static final String CHAR_FIELD9LOV="CHAR_FIELD9LOV";
		
		
		static final String NUMERIC_FIELD1LOV="NUMERIC_FIELD1LOV";
		static final String NUMERIC_FIELD2LOV="NUMERIC_FIELD2LOV";
		static final String NUMERIC_FIELD3LOV="NUMERIC_FIELD3LOV";
		static final String NUMERIC_FIELD4LOV="NUMERIC_FIELD4LOV";
		static final String NUMERIC_FIELD5LOV="NUMERIC_FIELD5LOV";
		static final String NUMERIC_FIELD6LOV="NUMERIC_FIELD6LOV";
		static final String NUMERIC_FIELD7LOV="NUMERIC_FIELD7LOV";
		static final String NUMERIC_FIELD8LOV="NUMERIC_FIELD8LOV";
		static final String NUMERIC_FIELD9LOV="NUMERIC_FIELD9LOV";
		
		
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

	
	List<FlexField> CHAR_FIELD1LOVList = new ArrayList<FlexField>();
	List<FlexField> CHAR_FIELD2LOVList = new ArrayList<FlexField>();
	List<FlexField> CHAR_FIELD3LOVList = new ArrayList<FlexField>();
	List<FlexField> CHAR_FIELD4LOVList = new ArrayList<FlexField>();
	List<FlexField> CHAR_FIELD5LOVList = new ArrayList<FlexField>();
	List<FlexField> CHAR_FIELD6LOVList = new ArrayList<FlexField>();
	List<FlexField> CHAR_FIELD7LOVList = new ArrayList<FlexField>();
	List<FlexField> CHAR_FIELD8LOVList = new ArrayList<FlexField>();
	List<FlexField> CHAR_FIELD9LOVList = new ArrayList<FlexField>();
	
	
	List<NumbericField> NUMERIC_FIELD1LOVList = new ArrayList<NumbericField>();
	List<NumbericField> NUMERIC_FIELD2LOVList = new ArrayList<NumbericField>();
	List<NumbericField> NUMERIC_FIELD3LOVList = new ArrayList<NumbericField>();
	List<NumbericField> NUMERIC_FIELD4LOVList = new ArrayList<NumbericField>();
	List<NumbericField> NUMERIC_FIELD5LOVList = new ArrayList<NumbericField>();
	List<NumbericField> NUMERIC_FIELD6LOVList = new ArrayList<NumbericField>();
	List<NumbericField> NUMERIC_FIELD7LOVList = new ArrayList<NumbericField>();
	List<NumbericField> NUMERIC_FIELD8LOVList = new ArrayList<NumbericField>();
	List<NumbericField> NUMERIC_FIELD9LOVList = new ArrayList<NumbericField>();
	
	
	public List<FlexField> getCHAR_FIELD1LOVList() {
		return CHAR_FIELD1LOVList;
	}

	public void setCHAR_FIELD1LOVList(List<FlexField> cHAR_FIELD1LOVList) {
		CHAR_FIELD1LOVList = cHAR_FIELD1LOVList;
	}

	public List<FlexField> getCHAR_FIELD2LOVList() {
		return CHAR_FIELD2LOVList;
	}

	public void setCHAR_FIELD2LOVList(List<FlexField> cHAR_FIELD2LOVList) {
		CHAR_FIELD2LOVList = cHAR_FIELD2LOVList;
	}

	public List<FlexField> getCHAR_FIELD3LOVList() {
		return CHAR_FIELD3LOVList;
	}

	public void setCHAR_FIELD3LOVList(List<FlexField> cHAR_FIELD3LOVList) {
		CHAR_FIELD3LOVList = cHAR_FIELD3LOVList;
	}

	public List<FlexField> getCHAR_FIELD4LOVList() {
		return CHAR_FIELD4LOVList;
	}

	public void setCHAR_FIELD4LOVList(List<FlexField> cHAR_FIELD4LOVList) {
		CHAR_FIELD4LOVList = cHAR_FIELD4LOVList;
	}

	public List<FlexField> getCHAR_FIELD5LOVList() {
		return CHAR_FIELD5LOVList;
	}

	public void setCHAR_FIELD5LOVList(List<FlexField> cHAR_FIELD5LOVList) {
		CHAR_FIELD5LOVList = cHAR_FIELD5LOVList;
	}

	public List<FlexField> getCHAR_FIELD6LOVList() {
		return CHAR_FIELD6LOVList;
	}

	public void setCHAR_FIELD6LOVList(List<FlexField> cHAR_FIELD6LOVList) {
		CHAR_FIELD6LOVList = cHAR_FIELD6LOVList;
	}

	public List<FlexField> getCHAR_FIELD7LOVList() {
		return CHAR_FIELD7LOVList;
	}

	public void setCHAR_FIELD7LOVList(List<FlexField> cHAR_FIELD7LOVList) {
		CHAR_FIELD7LOVList = cHAR_FIELD7LOVList;
	}

	public List<FlexField> getCHAR_FIELD8LOVList() {
		return CHAR_FIELD8LOVList;
	}

	public void setCHAR_FIELD8LOVList(List<FlexField> cHAR_FIELD8LOVList) {
		CHAR_FIELD8LOVList = cHAR_FIELD8LOVList;
	}

	public List<FlexField> getCHAR_FIELD9LOVList() {
		return CHAR_FIELD9LOVList;
	}

	public void setCHAR_FIELD9LOVList(List<FlexField> cHAR_FIELD9LOVList) {
		CHAR_FIELD9LOVList = cHAR_FIELD9LOVList;
	}

	public List<NumbericField> getNUMERIC_FIELD1LOVList() {
		return NUMERIC_FIELD1LOVList;
	}

	public void setNUMERIC_FIELD1LOVList(List<NumbericField> nUMERIC_FIELD1LOVList) {
		NUMERIC_FIELD1LOVList = nUMERIC_FIELD1LOVList;
	}

	public List<NumbericField> getNUMERIC_FIELD2LOVList() {
		return NUMERIC_FIELD2LOVList;
	}

	public void setNUMERIC_FIELD2LOVList(List<NumbericField> nUMERIC_FIELD2LOVList) {
		NUMERIC_FIELD2LOVList = nUMERIC_FIELD2LOVList;
	}

	public List<NumbericField> getNUMERIC_FIELD3LOVList() {
		return NUMERIC_FIELD3LOVList;
	}

	public void setNUMERIC_FIELD3LOVList(List<NumbericField> nUMERIC_FIELD3LOVList) {
		NUMERIC_FIELD3LOVList = nUMERIC_FIELD3LOVList;
	}

	public List<NumbericField> getNUMERIC_FIELD4LOVList() {
		return NUMERIC_FIELD4LOVList;
	}

	public void setNUMERIC_FIELD4LOVList(List<NumbericField> nUMERIC_FIELD4LOVList) {
		NUMERIC_FIELD4LOVList = nUMERIC_FIELD4LOVList;
	}

	public List<NumbericField> getNUMERIC_FIELD5LOVList() {
		return NUMERIC_FIELD5LOVList;
	}

	public void setNUMERIC_FIELD5LOVList(List<NumbericField> nUMERIC_FIELD5LOVList) {
		NUMERIC_FIELD5LOVList = nUMERIC_FIELD5LOVList;
	}

	public List<NumbericField> getNUMERIC_FIELD6LOVList() {
		return NUMERIC_FIELD6LOVList;
	}

	public void setNUMERIC_FIELD6LOVList(List<NumbericField> nUMERIC_FIELD6LOVList) {
		NUMERIC_FIELD6LOVList = nUMERIC_FIELD6LOVList;
	}

	public List<NumbericField> getNUMERIC_FIELD7LOVList() {
		return NUMERIC_FIELD7LOVList;
	}

	public void setNUMERIC_FIELD7LOVList(List<NumbericField> nUMERIC_FIELD7LOVList) {
		NUMERIC_FIELD7LOVList = nUMERIC_FIELD7LOVList;
	}

	public List<NumbericField> getNUMERIC_FIELD8LOVList() {
		return NUMERIC_FIELD8LOVList;
	}

	public void setNUMERIC_FIELD8LOVList(List<NumbericField> nUMERIC_FIELD8LOVList) {
		NUMERIC_FIELD8LOVList = nUMERIC_FIELD8LOVList;
	}

	public List<NumbericField> getNUMERIC_FIELD9LOVList() {
		return NUMERIC_FIELD9LOVList;
	}

	public void setNUMERIC_FIELD9LOVList(List<NumbericField> nUMERIC_FIELD9LOVList) {
		NUMERIC_FIELD9LOVList = nUMERIC_FIELD9LOVList;
	}

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
}
