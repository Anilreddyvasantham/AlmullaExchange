package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.AvailableDatabaseObjects;
import com.amg.exchange.common.model.AvailableTableColumns;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.BankFieldMapping;
import com.amg.exchange.remittance.service.IBankFieldMappingService;
import com.amg.exchange.remittance.service.IParameterService;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("bankFieldMappingBean")
@Scope("session")
public class BankFieldMappingBean<T> implements Serializable {

	  /**
	 * 
	 */
	  private static final long serialVersionUID = 1L;
	  private static final Logger log = Logger.getLogger(BankFieldMappingBean.class);
	  private BigDecimal bankFieldMappingId = null;
	  private String tableName;
	  private String fieldName;
	  private String fieldvalue;
	  private BigDecimal bankId = null;
	  private String bankName;
	  private String bankValue;
	  private String bankValueDesc;
	  private BigDecimal additionalBankRuleId = null;
	  private String additionalBankRuleName;
	  private BigDecimal applicationCountryId;
	  private String dynamicLabelForActivateDeactivate;
	  // common Variables
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String isActive;
	  private String remarks=null;
	  String errmsg;
	  // Boolean Variables
	  private Boolean booRenderDataTable = false;
	  private Boolean booSaveOrExit = false;
	  private Boolean renderEditButton = false;
	  private Boolean booEditButton = false;
	  private Boolean ifEditClicked = false;
	  private Boolean booClearPanel = false;
	  private Boolean booAdd = false;
	  private Boolean booApproval = false;
	  private Boolean booSubmitPanel = false;
	  private Boolean booRead = false;

	  SessionStateManage sessionStateManage = new SessionStateManage();

	  @Autowired
	  IParameterService parameterService;
	  @Autowired
	  IGeneralService<T> generalService;
	  
	  @Autowired 
	  IBankFieldMappingService<T> bankFieldMappingService;

	 
	  List<AvailableDatabaseObjects> lstDatabaseObjects = new ArrayList<AvailableDatabaseObjects>();
	  List<AvailableTableColumns> lstTablecolumns = new ArrayList<AvailableTableColumns>();
	  private List<AdditionalBankRuleMap> lstAdditionalBankRuleMap=new ArrayList<AdditionalBankRuleMap>();
	  List<BankMaster> bankList = new ArrayList<BankMaster>();
	  private List<BankFieldMappingDataTable> lstBankFieldMappingDataTable=new CopyOnWriteArrayList<BankFieldMappingDataTable>();
	  private List<BankFieldMappingDataTable> newlstBankFieldMappingDataTable=new CopyOnWriteArrayList<BankFieldMappingDataTable>();
	  private BankFieldMappingDataTable bankFieldMappingDTOBJ=null;
	  Map<BigDecimal, String> mapBankNamelist = new HashMap<BigDecimal, String>();
	  Map<BigDecimal, String> mapAdditionalBankRule = new HashMap<BigDecimal, String>();
	  
	  public BigDecimal getBankFieldMappingId() {
		    return bankFieldMappingId;
	  }

	  public void setBankFieldMappingId(BigDecimal bankFieldMappingId) {
		    this.bankFieldMappingId = bankFieldMappingId;
	  }

	  public String getTableName() {
		    return tableName;
	  }

	  public void setTableName(String tableName) {
		    this.tableName = tableName;
	  }

	  public String getFieldName() {
		    return fieldName;
	  }

	  public void setFieldName(String fieldName) {
		    this.fieldName = fieldName;
	  }

	  public String getFieldvalue() {
		    return fieldvalue;
	  }

	  public void setFieldvalue(String fieldvalue) {
		    this.fieldvalue = fieldvalue;
	  }

	  public BigDecimal getBankId() {
		    return bankId;
	  }

	  public void setBankId(BigDecimal bankId) {
		    this.bankId = bankId;
	  }

	  public String getBankValue() {
		    return bankValue;
	  }

	  public void setBankValue(String bankValue) {
		    this.bankValue = bankValue;
	  }

	  public String getBankValueDesc() {
		    return bankValueDesc;
	  }

	  public void setBankValueDesc(String bankValueDesc) {
		    this.bankValueDesc = bankValueDesc;
	  }

	  public BigDecimal getAdditionalBankRuleId() {
		    return additionalBankRuleId;
	  }

	  public void setAdditionalBankRuleId(BigDecimal additionalBankRuleId) {
		    this.additionalBankRuleId = additionalBankRuleId;
	  }

	  public List<AvailableDatabaseObjects> getLstDatabaseObjects() {
		    return lstDatabaseObjects;
	  }

	  public void setLstDatabaseObjects(List<AvailableDatabaseObjects> lstDatabaseObjects) {
		    this.lstDatabaseObjects = lstDatabaseObjects;
	  }

	  public List<AvailableTableColumns> getLstTablecolumns() {
		    return lstTablecolumns;
	  }

	  public void setLstTablecolumns(List<AvailableTableColumns> lstTablecolumns) {
		    this.lstTablecolumns = lstTablecolumns;
	  }

	  public List<BankMaster> getBankList() {
		    return bankList;
	  }

	  public void setBankList(List<BankMaster> bankList) {
		    this.bankList = bankList;
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

	  public String getIsActive() {
		    return isActive;
	  }

	  public void setIsActive(String isActive) {
		    this.isActive = isActive;
	  }

	  public String getRemarks() {
		    return remarks;
	  }

	  public void setRemarks(String remarks) {
		    this.remarks = remarks;
	  }

	  public Boolean getBooRenderDataTable() {
		    return booRenderDataTable;
	  }

	  public void setBooRenderDataTable(Boolean booRenderDataTable) {
		    this.booRenderDataTable = booRenderDataTable;
	  }

	  public Boolean getBooSaveOrExit() {
		    return booSaveOrExit;
	  }

	  public void setBooSaveOrExit(Boolean booSaveOrExit) {
		    this.booSaveOrExit = booSaveOrExit;
	  }

	  public Boolean getRenderEditButton() {
		    return renderEditButton;
	  }

	  public void setRenderEditButton(Boolean renderEditButton) {
		    this.renderEditButton = renderEditButton;
	  }

	  public Boolean getBooEditButton() {
		    return booEditButton;
	  }

	  public void setBooEditButton(Boolean booEditButton) {
		    this.booEditButton = booEditButton;
	  }

	  public Boolean getIfEditClicked() {
		    return ifEditClicked;
	  }

	  public void setIfEditClicked(Boolean ifEditClicked) {
		    this.ifEditClicked = ifEditClicked;
	  }

	  public Boolean getBooClearPanel() {
		    return booClearPanel;
	  }

	  public void setBooClearPanel(Boolean booClearPanel) {
		    this.booClearPanel = booClearPanel;
	  }

	  public Boolean getBooAdd() {
		    return booAdd;
	  }

	  public void setBooAdd(Boolean booAdd) {
		    this.booAdd = booAdd;
	  }

	  public Boolean getBooApproval() {
		    return booApproval;
	  }

	  public void setBooApproval(Boolean booApproval) {
		    this.booApproval = booApproval;
	  }

	  public Boolean getBooSubmitPanel() {
		    return booSubmitPanel;
	  }

	  public void setBooSubmitPanel(Boolean booSubmitPanel) {
		    this.booSubmitPanel = booSubmitPanel;
	  }

	  public Boolean getBooRead() {
		    return booRead;
	  }

	  public void setBooRead(Boolean booRead) {
		    this.booRead = booRead;
	  }
	  
	  public BigDecimal getApplicationCountryId() {
	  	  return applicationCountryId;
	  }

	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
	  	  this.applicationCountryId = applicationCountryId;
	  }
	  
	  public List<BankFieldMappingDataTable> getLstBankFieldMappingDataTable() {
	  	  return lstBankFieldMappingDataTable;
	  }

	  public void setLstBankFieldMappingDataTable(List<BankFieldMappingDataTable> lstBankFieldMappingDataTable) {
	  	  this.lstBankFieldMappingDataTable = lstBankFieldMappingDataTable;
	  }

	  public List<BankFieldMappingDataTable> getNewlstBankFieldMappingDataTable() {
	  	  return newlstBankFieldMappingDataTable;
	  }

	  public void setNewlstBankFieldMappingDataTable(List<BankFieldMappingDataTable> newlstBankFieldMappingDataTable) {
	  	  this.newlstBankFieldMappingDataTable = newlstBankFieldMappingDataTable;
	  }
	  
	  public String getDynamicLabelForActivateDeactivate() {
	  	  return dynamicLabelForActivateDeactivate;
	  }

	  public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
	  	  this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	  }
	  public String getBankName() {
	  	  return bankName;
	  }

	  public void setBankName(String bankName) {
	  	  this.bankName = bankName;
	  }
	  public Map<BigDecimal, String> getMapBankNamelist() {
	  	  return mapBankNamelist;
	  }

	  public void setMapBankNamelist(Map<BigDecimal, String> mapBankNamelist) {
	  	  this.mapBankNamelist = mapBankNamelist;
	  }
	  
	  public List<AdditionalBankRuleMap> getLstAdditionalBankRuleMap() {
	  	  return lstAdditionalBankRuleMap;
	  }

	  public void setLstAdditionalBankRuleMap(List<AdditionalBankRuleMap> lstAdditionalBankRuleMap) {
	  	  this.lstAdditionalBankRuleMap = lstAdditionalBankRuleMap;
	  }
	  
	  public String getAdditionalBankRuleName() {
	  	  return additionalBankRuleName;
	  }

	  public void setAdditionalBankRuleName(String additionalBankRuleName) {
	  	  this.additionalBankRuleName = additionalBankRuleName;
	  }
	  
	  public Map<BigDecimal, String> getMapAdditionalBankRule() {
	  	  return mapAdditionalBankRule;
	  }

	  public void setMapAdditionalBankRule(Map<BigDecimal, String> mapAdditionalBankRule) {
	  	  this.mapAdditionalBankRule = mapAdditionalBankRule;
	  }
	  
	  public BankFieldMappingDataTable getBankFieldMappingDTOBJ() {
	  	  return bankFieldMappingDTOBJ;
	  }

	  public void setBankFieldMappingDTOBJ(BankFieldMappingDataTable bankFieldMappingDTOBJ) {
	  	  this.bankFieldMappingDTOBJ = bankFieldMappingDTOBJ;
	  }
	  
	  public String getErrmsg() {
	  	  return errmsg;
	  }

	  public void setErrmsg(String errmsg) {
	  	  this.errmsg = errmsg;
	  }

	  public void populateTableNameList() {
		    lstDatabaseObjects  = parameterService.getDataBaseObjects();
	  }

	  public void populateCoulumnName() {
		    try {
			      lstTablecolumns = parameterService.getTablecolumns(getTableName());
		    } catch (Exception e) {
			      e.printStackTrace();
		    }

	  }
	  
	  public void populateFiledValue(){
		String fieldValue=bankFieldMappingService.toFtechCoumnIdBasedOnColumnName(getFieldName(),getTableName());
		setFieldvalue(fieldValue);
	  }

	  public void populateBankList() {
		    mapBankNamelist.clear();
		    bankList = generalService.getAllBankListFromBankMaster();
		    if (bankList.size() > 0) {
			      for (BankMaster bankMaster : bankList) {
					mapBankNamelist.put(bankMaster.getBankId(), bankMaster.getBankFullName());	
			      }
			      setBankList(bankList);
		    }
	  }

	  public void navigateToBankFielPage() {
		    try {
			      setBooRenderDataTable(false);
			      setBooSaveOrExit(false);
			      setBooAdd(true);
			      setBooApproval(false);
			      setBooRead(false);
			      setBooClearPanel(false);
			      setBooSubmitPanel(false);
			      clear();
			      lstBankFieldMappingDataTable.clear();
			      newlstBankFieldMappingDataTable.clear();
			      populateBankList();
			      populateTableNameList();
			      populateAddtionalBankRuleMaps();
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/bankfieldmapping.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }
	  }
	  
	  public void populateAddtionalBankRuleMaps(){
		    mapAdditionalBankRule.clear();
		    lstAdditionalBankRuleMap=bankFieldMappingService.toFetchAdditionalData();
		    if(lstAdditionalBankRuleMap.size() !=0){
		    for (AdditionalBankRuleMap additionalBankRuleMap : lstAdditionalBankRuleMap) {
			      mapAdditionalBankRule.put(additionalBankRuleMap.getAdditionalBankRuleId(), additionalBankRuleMap.getFieldName());
		    }
	  }
	  }

	  public void clear() {
		    setAdditionalBankRuleId(null);
		    setBankFieldMappingId(null);
		    setBankId(null);
		    setBankValue(null);
		    setBankValueDesc(null);
		    setFieldName(null);
		    setAdditionalBankRuleName(null);
		    setFieldvalue(null);
		    setTableName(null);
		   
	  }
	  
	  //Duplicate Checking
	  public void duplicateChekingBankFieldMapping(){
		    if(lstBankFieldMappingDataTable.size() !=0){
			      for (BankFieldMappingDataTable bankFieldMappingDataTable : lstBankFieldMappingDataTable) {
					if(bankFieldMappingDataTable.getTableName().equalsIgnoreCase(getTableName())&&bankFieldMappingDataTable.getFieldName().equalsIgnoreCase(getFieldName())
							    &&bankFieldMappingDataTable.getFieldvalue().equalsIgnoreCase(getFieldvalue())){
						  clear();
						  RequestContext.getCurrentInstance().execute("datatable.show();");
						  setBooEditButton(false);
						  setBooClearPanel(false);
						  setBooSubmitPanel(false);
						  return;
					}
			      }
			      
		    }
		    addRecordsToDataTable();
	  }
	  public void addRecordsToDataTable(){
		    try{
		    setBooEditButton(false);
		    setBooClearPanel(false);
		    setBooSubmitPanel(false);
		    boolean alreadyDT = false;
		    if(!alreadyDT){
			      if(getBankFieldMappingId() ==null){
					List<BankFieldMapping> lstBankFieldMappings=bankFieldMappingService.toCheckDuplicateData(getTableName(),getFieldvalue(),getFieldName());
					if(lstBankFieldMappings != null && lstBankFieldMappings.size() !=0){
						  alreadyDT=true;
						  RequestContext.getCurrentInstance().execute("csp.show();");
						  setErrmsg("The Bank Field Mapping already exist");
						  clear();
						 
					}
			      }
		    }
		    if(!alreadyDT){ 
		    BankFieldMappingDataTable bankFieldMappingDtObj=new BankFieldMappingDataTable();
		    bankFieldMappingDtObj.setBankFieldMappingId(getBankFieldMappingId());
		    bankFieldMappingDtObj.setApplicationCountryId(sessionStateManage.getCountryId());
		    bankFieldMappingDtObj.setAdditionalBankRuleId(getAdditionalBankRuleId());
		    bankFieldMappingDtObj.setAdditionalBankRuleName(mapAdditionalBankRule.get(getAdditionalBankRuleId()));
		    bankFieldMappingDtObj.setTableName(getTableName());
		    bankFieldMappingDtObj.setFieldName(getFieldName());
		    bankFieldMappingDtObj.setFieldvalue(getFieldvalue());
		    bankFieldMappingDtObj.setBankId(getBankId());
		    bankFieldMappingDtObj.setBankName(mapBankNamelist.get(getBankId()));
		    bankFieldMappingDtObj.setBankValue(getBankValue());
		    bankFieldMappingDtObj.setBankValueDesc(getBankValueDesc());
		    bankFieldMappingDtObj.setCreatedBy(getCreatedBy());
		    bankFieldMappingDtObj.setCreatedDate(getCreatedDate());
		    if(getBankFieldMappingId() != null){
			      if(bankFieldMappingDtObj.getTableName().equalsIgnoreCase(bankFieldMappingDTOBJ.getTableName()) && bankFieldMappingDtObj.getFieldName().equalsIgnoreCase(bankFieldMappingDTOBJ.getFieldName())
						  && bankFieldMappingDtObj.getFieldvalue().equalsIgnoreCase(bankFieldMappingDTOBJ.getFieldvalue()) && bankFieldMappingDtObj.getAdditionalBankRuleId().equals(bankFieldMappingDTOBJ.getAdditionalBankRuleId())
						  && bankFieldMappingDtObj.getBankId().equals(bankFieldMappingDTOBJ.getBankId()) && bankFieldMappingDtObj.getBankValue().equalsIgnoreCase(bankFieldMappingDTOBJ.getBankValue())
						  && bankFieldMappingDtObj.getBankValueDesc().equalsIgnoreCase(bankFieldMappingDTOBJ.getBankValueDesc())){
					bankFieldMappingDtObj.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					bankFieldMappingDtObj.setIsActive(getIsActive());
					bankFieldMappingDtObj.setModifiedBy(getModifiedBy());
					bankFieldMappingDtObj.setModifiedDate(getModifiedDate());
					bankFieldMappingDtObj.setApprovedBy(getApprovedBy());
					bankFieldMappingDtObj.setApprovedDate(getApprovedDate());
					bankFieldMappingDtObj.setRemarks(getRemarks());
			      }else{
					bankFieldMappingDtObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					bankFieldMappingDtObj.setIsActive(Constants.U);
					bankFieldMappingDtObj.setModifiedBy(sessionStateManage.getUserName());
					bankFieldMappingDtObj.setModifiedDate(new Date());
					bankFieldMappingDtObj.setApprovedBy(null);
					bankFieldMappingDtObj.setApprovedDate(null);
					bankFieldMappingDtObj.setRemarks(null);
					bankFieldMappingDtObj.setIfEditClicked(true);	
			      }
		    }else {
			      bankFieldMappingDtObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
			      bankFieldMappingDtObj.setIsActive(Constants.U);
			      bankFieldMappingDtObj.setCreatedBy(sessionStateManage.getUserName());
			      bankFieldMappingDtObj.setCreatedDate(new Date());
			      bankFieldMappingDtObj.setIfEditClicked(true);      
		    }
		    lstBankFieldMappingDataTable.add(bankFieldMappingDtObj);

		    if (getBankFieldMappingId() == null) {
			      newlstBankFieldMappingDataTable.add(bankFieldMappingDtObj);
		    }
		    setBooRenderDataTable(true);
		    setBooSaveOrExit(true);
		    setBooApproval(false);
		    setBooRead(false);
		    clear();
		    }
	  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::::"+exception.getMessage());
	  }
	  }
	  
	  //edit button
	  public void edit(BankFieldMappingDataTable dataTable){
		    setBankFieldMappingDTOBJ(dataTable);
		    setBankFieldMappingId(dataTable.getBankFieldMappingId());
		    setTableName(dataTable.getTableName());
		    populateCoulumnName();
		    setFieldName(dataTable.getFieldName());
		    setFieldvalue(dataTable.getFieldvalue());
		    setAdditionalBankRuleId(dataTable.getAdditionalBankRuleId());
		    setBankId(dataTable.getBankId());
		    setBankValue(dataTable.getBankValue());
		    setBankValueDesc(dataTable.getBankValueDesc());
		    setApplicationCountryId(dataTable.getApplicationCountryId());
		    setCreatedBy(dataTable.getCreatedBy());
		    setCreatedDate(dataTable.getCreatedDate());
		    setModifiedBy(dataTable.getModifiedBy());
		    setModifiedDate(dataTable.getModifiedDate());
		    setApprovedBy(dataTable.getApprovedBy());
		    setApprovedDate(dataTable.getApprovedDate());
		    setRemarks(dataTable.getRemarks());
		    setIsActive(dataTable.getIsActive());
		    setDynamicLabelForActivateDeactivate(dataTable.getDynamicLabelForActivateDeactivate());
		    lstBankFieldMappingDataTable.remove(dataTable);
		    newlstBankFieldMappingDataTable.remove(dataTable);
		    if (lstBankFieldMappingDataTable.size() == 0) {
			      setBooRenderDataTable(false);
			      setBooSaveOrExit(false);
			      setBooAdd(true);
			      setBooClearPanel(true);
			      setBooApproval(false);
			      setBooRead(false);

		    } else {
			      setBooEditButton(true);
			      setBooSubmitPanel(true);
			      setBooClearPanel(true);
			      setBooApproval(false);
			      setBooRead(false);
		    }
	  }
	  //Save All Data
	  public void saveAllBankFieldMapping(){
		    try {
			    for (BankFieldMappingDataTable dataTable : lstBankFieldMappingDataTable) {
				      if(dataTable.getIfEditClicked().equals(true)){
					BankFieldMapping bankFieldMapping=new BankFieldMapping();
					bankFieldMapping.setBankFieldMappingId(dataTable.getBankFieldMappingId());
					bankFieldMapping.setTableName(dataTable.getTableName());
					bankFieldMapping.setFieldName(dataTable.getFieldName());
					bankFieldMapping.setFieldValue(dataTable.getFieldvalue());
					bankFieldMapping.setApplicationCountry(dataTable.getApplicationCountryId());
					//save additional data
					AdditionalBankRuleMap additionalBankRuleMap= new AdditionalBankRuleMap();
					additionalBankRuleMap.setAdditionalBankRuleId(dataTable.getAdditionalBankRuleId());
					bankFieldMapping.setExbankAdditinalFields(additionalBankRuleMap);
					//Save Bank Master
					BankMaster bankMaster=new BankMaster();
					bankMaster.setBankId(dataTable.getBankId());
					bankFieldMapping.setBankId(bankMaster);
					bankFieldMapping.setBankValue(dataTable.getBankValue());
					bankFieldMapping.setBankValueDesc(dataTable.getBankValueDesc());
					//common  Variales
					bankFieldMapping.setCreatedBy(dataTable.getCreatedBy());
					bankFieldMapping.setCreatedDate(dataTable.getCreatedDate());
					bankFieldMapping.setModifiedBy(dataTable.getModifiedBy());
					bankFieldMapping.setModifiedDate(dataTable.getModifiedDate());
					bankFieldMapping.setApprovedBy(dataTable.getApprovedBy());
					bankFieldMapping.setApprovedDate(dataTable.getApprovedDate());
					bankFieldMapping.setRemarks(dataTable.getRemarks());
					bankFieldMapping.setIsActive(dataTable.getIsActive());
					
					 bankFieldMappingService.saveAllBankFieldMapping(bankFieldMapping);
				      }
		    }  
			      lstBankFieldMappingDataTable.clear();
			      newlstBankFieldMappingDataTable.clear();
			      RequestContext.getCurrentInstance().execute("complete.show();");
			      clear();
			      setBooRenderDataTable(false);
			      setBooSaveOrExit(false);
			      setBooAdd(true);
			      setBooApproval(false);
			      setBooRead(false);
		    } catch (Exception exception) {
			      log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
		    }
	  }
	  
	  public void clickOnOKSave() {
		    lstBankFieldMappingDataTable.clear();
		    newlstBankFieldMappingDataTable.clear();
		    clear();
		    setBooRenderDataTable(false);
		    setBooSaveOrExit(false);
		    setBooAdd(true);
		    setBooRead(false);
		    setBooApproval(false);

		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/bankfieldmapping.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }
	  }
	  // Disable Submit button ajax calling
	  public void disableSubmit() {
		    setBooSubmitPanel(true);
	  }
	  // exit
	  public void exit() {
		    lstBankFieldMappingDataTable.clear();
		    newlstBankFieldMappingDataTable.clear();
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }
	  }

	  // not saved button ok button
	  public void bankFieldMappingNotSaved() {
		    lstBankFieldMappingDataTable.clear();
		    newlstBankFieldMappingDataTable.clear();
		    clear();
		    setBooRenderDataTable(false);
		    setBooSaveOrExit(false);
		    setBooAdd(true);
		    setBooRead(false);
		    setBooApproval(false);

		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/bankfieldmapping.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }
	  }
	  
	  //Check Status
	  public void checkStatus(BankFieldMappingDataTable dataTable){
		    if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
			      RequestContext.getCurrentInstance().execute("pending.show();");
			      return;
		    } else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			      lstBankFieldMappingDataTable.remove(dataTable);
			      newlstBankFieldMappingDataTable.remove(dataTable);
		    } else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			      dataTable.setRemarksCheck(true);
			      setApprovedBy(dataTable.getApprovedBy());
			      setApprovedDate(dataTable.getApprovedDate());
			      RequestContext.getCurrentInstance().execute("remarks.show();");
		    } else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			      dataTable.setActiveRecordCheck(true);
			      RequestContext.getCurrentInstance().execute("activateRecord.show();");
			      return;
		    } else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null && dataTable.getApprovedBy() == null && dataTable.getApprovedDate() == null
					&& dataTable.getRemarks() == null) {
			      dataTable.setPermentDeleteCheck(true);
			      RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			      return;
		    }
		    if (lstBankFieldMappingDataTable.size() == 0) {
			      setBooRenderDataTable(false);
			      setBooSaveOrExit(false);
			      setBooAdd(true);
			      setBooApproval(false);
			      setBooRead(false);

		    }
	  }
	  // Hard Delete
	  public void bankFieldMappingConformDelete() {
		    if (lstBankFieldMappingDataTable.size() > 0) {
			      for (BankFieldMappingDataTable bankFieldMappingDataTable : lstBankFieldMappingDataTable) {
					if (bankFieldMappingDataTable.getPermentDeleteCheck() != null) {
						  if (bankFieldMappingDataTable.getPermentDeleteCheck().equals(true)) {
							    deleteRecordBankFieldMapping(bankFieldMappingDataTable);
							    lstBankFieldMappingDataTable.remove(bankFieldMappingDataTable);
						  }
					}
			      }
		    }
	  }

	  public void deleteRecordBankFieldMapping(BankFieldMappingDataTable dataTable) {
		    bankFieldMappingService.deleteRecordPermentelyFromDb(dataTable.getBankFieldMappingId());
	  }

	  // click on DeActive link
	  public void clickOkRemarks() {
		    if (getRemarks() != null && !getRemarks().equals("")) {
			      for (BankFieldMappingDataTable bankMappingDataTable : lstBankFieldMappingDataTable) {
					if (bankMappingDataTable.getRemarksCheck() != null) {
						  if (bankMappingDataTable.getRemarksCheck().equals(true)) {
							    bankMappingDataTable.setRemarks(getRemarks());
							    updateBankFieldMapping(bankMappingDataTable);
							    clear();
							    viewAllBankFieldMapping();
							    setRemarks(null);
						  }
					}
			      }

		    } else {
			      RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		    }
	  }

	  public void updateBankFieldMapping(BankFieldMappingDataTable bankFieldMappingDataTable) {
		    bankFieldMappingService.upDateActiveRecordtoDeActive(bankFieldMappingDataTable.getBankFieldMappingId(), bankFieldMappingDataTable.getRemarks(), sessionStateManage.getUserName());
	  }
	// CanCel Remarks
		  public void cancelRemarks() {
			    setRemarks(null);
			    setBooApproval(false);
			    setBooRead(false);
			    try {
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/bankfieldmapping.xhtml");
			    } catch (IOException e) {
				      e.printStackTrace();
			    }
		  }

		  // click on Active link
		  public void activateRecord() {
			    if (lstBankFieldMappingDataTable.size() > 0) {
				      for (BankFieldMappingDataTable bankFieldMappingDataTable : lstBankFieldMappingDataTable) {
						if (bankFieldMappingDataTable.getActiveRecordCheck() != null) {
							  if (bankFieldMappingDataTable.getActiveRecordCheck().equals(true)) {
								    conformActiveRecordToPendingForApproval(bankFieldMappingDataTable);
							  }
						}
				      }
			    }
		  }

		  public void conformActiveRecordToPendingForApproval(BankFieldMappingDataTable dataTable) {
			    bankFieldMappingService.DeActiveRecordToPendingForApprovalBankFieldMapping(dataTable.getBankFieldMappingId(), sessionStateManage.getUserName());
			    viewAllBankFieldMapping();
		  }
		  public void viewAllBankFieldMapping(){
		    lstBankFieldMappingDataTable.clear();
		    clear();
		    try {
			      List<BankFieldMapping> lstBankFieldMappings = bankFieldMappingService.toFetchAllViewDetailsOfBankFieldMapping(sessionStateManage.getCountryId());
			      if (lstBankFieldMappings.size() > 0) {
					setBooRenderDataTable(true);
					setBooSaveOrExit(true);
					setBooApproval(false);
					setBooRead(false);
			      } else {
					setBooRenderDataTable(false);
					setBooSaveOrExit(false);
					setBooApproval(false);
					setBooRead(false);
					RequestContext.getCurrentInstance().execute("noRecords.show();");
					return;
			      }
			      for (BankFieldMapping bankFieldMapping : lstBankFieldMappings) {
					BankFieldMappingDataTable dataTable = new BankFieldMappingDataTable();
					dataTable.setBankFieldMappingId(bankFieldMapping.getBankFieldMappingId());
					dataTable.setApplicationCountryId(bankFieldMapping.getApplicationCountry());
					dataTable.setTableName(bankFieldMapping.getTableName());
					dataTable.setFieldName(bankFieldMapping.getFieldName());
					dataTable.setFieldvalue(bankFieldMapping.getFieldValue());
					dataTable.setAdditionalBankRuleId(bankFieldMapping.getExbankAdditinalFields().getAdditionalBankRuleId());
					dataTable.setAdditionalBankRuleName(mapAdditionalBankRule.get(bankFieldMapping.getExbankAdditinalFields().getAdditionalBankRuleId()));
					dataTable.setBankId(bankFieldMapping.getBankId().getBankId());
					dataTable.setBankName(mapBankNamelist.get(bankFieldMapping.getBankId().getBankId()));
					dataTable.setBankValue(bankFieldMapping.getBankValue());
					dataTable.setBankValueDesc(bankFieldMapping.getBankValueDesc());
					dataTable.setCreatedBy(bankFieldMapping.getCreatedBy());
					dataTable.setCreatedDate(bankFieldMapping.getCreatedDate());
					dataTable.setModifiedBy(bankFieldMapping.getModifiedBy());
					dataTable.setModifiedDate(bankFieldMapping.getModifiedDate());
					dataTable.setApprovedBy(bankFieldMapping.getApprovedBy());
					dataTable.setApprovedDate(bankFieldMapping.getApprovedDate());
					dataTable.setRemarks(bankFieldMapping.getRemarks());
					dataTable.setIsActive(bankFieldMapping.getIsActive());
					if (dataTable.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						  dataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					} else if (dataTable.getIsActive().equalsIgnoreCase(Constants.D)) {
						  dataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					} else if (dataTable.getIsActive().equalsIgnoreCase(Constants.U) && dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null && dataTable.getApprovedBy() == null && dataTable.getApprovedDate() == null && dataTable.getRemarks() == null) {
						  dataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					} else {
						  dataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}
					lstBankFieldMappingDataTable.add(dataTable);
			      }
			      lstBankFieldMappingDataTable.addAll(newlstBankFieldMappingDataTable);
			      clear();
		    } catch (Exception exception) {
			      log.info("exception.getMessage()::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + exception.getMessage());
			      
		    }
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/bankfieldmapping.xhtml");
		    } catch (Exception exception) {
			      log.info("exception.getMessage()::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + exception.getMessage());
		    }
	  }
		  
		  //Approval Started
		  public void approvalPageNavigation(){
			    setBooAdd(false);
			    setBooApproval(false);
			    setBooSaveOrExit(false);
			    setBooRenderDataTable(true);
			    setBooSubmitPanel(true);
			    setBooClearPanel(true);
			    setBooRead(false);
			    	clear();
			      lstBankFieldMappingDataTable.clear();
			      newlstBankFieldMappingDataTable.clear();
			      populateBankList();
			      populateTableNameList();
			      populateAddtionalBankRuleMaps();
			    fetchRecordforBankFieldMappingForApprovalDataTable();
			    try {
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/BankFieldMappingApproval.xhtml");
			    } catch (Exception e) {
				      e.printStackTrace();
			    }
		  }
		  public void fetchRecordforBankFieldMappingForApprovalDataTable(){
			lstBankFieldMappingDataTable.clear();
			    try {
				      List<BankFieldMapping> lstBankFieldMappings = bankFieldMappingService.toFetchAllApprovalDetailsOfBankFieldMapping(sessionStateManage.getCountryId());
				      if (lstBankFieldMappings.size() > 0) {
				      for (BankFieldMapping bankFieldMapping : lstBankFieldMappings) {
						BankFieldMappingDataTable dataTable = new BankFieldMappingDataTable();
						dataTable.setBankFieldMappingId(bankFieldMapping.getBankFieldMappingId());
						dataTable.setApplicationCountryId(bankFieldMapping.getApplicationCountry());
						dataTable.setTableName(bankFieldMapping.getTableName());
						dataTable.setFieldName(bankFieldMapping.getFieldName());
						dataTable.setFieldvalue(bankFieldMapping.getFieldValue());
						dataTable.setAdditionalBankRuleId(bankFieldMapping.getExbankAdditinalFields().getAdditionalBankRuleId());
						dataTable.setAdditionalBankRuleName(mapAdditionalBankRule.get(bankFieldMapping.getExbankAdditinalFields().getAdditionalBankRuleId()));
						dataTable.setBankId(bankFieldMapping.getBankId().getBankId());
						dataTable.setBankName(mapBankNamelist.get(bankFieldMapping.getBankId().getBankId()));
						dataTable.setBankValue(bankFieldMapping.getBankValue());
						dataTable.setBankValueDesc(bankFieldMapping.getBankValueDesc());
						dataTable.setCreatedBy(bankFieldMapping.getCreatedBy());
						dataTable.setCreatedDate(bankFieldMapping.getCreatedDate());
						dataTable.setModifiedBy(bankFieldMapping.getModifiedBy());
						dataTable.setModifiedDate(bankFieldMapping.getModifiedDate());
						dataTable.setApprovedBy(bankFieldMapping.getApprovedBy());
						dataTable.setApprovedDate(bankFieldMapping.getApprovedDate());
						dataTable.setRemarks(bankFieldMapping.getRemarks());
						dataTable.setIsActive(bankFieldMapping.getIsActive());
						if (dataTable.getIsActive().equalsIgnoreCase(Constants.Yes)) {
							  dataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
						} else if (dataTable.getIsActive().equalsIgnoreCase(Constants.D)) {
							  dataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
						} else if (dataTable.getIsActive().equalsIgnoreCase(Constants.U) && dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null && dataTable.getApprovedBy() == null && dataTable.getApprovedDate() == null && dataTable.getRemarks() == null) {
							  dataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
						} else {
							  dataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						}
						lstBankFieldMappingDataTable.add(dataTable);
				      }
				      }
			    } catch (Exception exception) {
				      log.info("exception.getMessage()::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + exception.getMessage());
			    }
		  }
		  public void approvalCheckForBankFieldMappingUser(BankFieldMappingDataTable dataTable){
			    if (!(dataTable.getModifiedBy() == null ? dataTable.getCreatedBy() : dataTable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())) {
				      setBankFieldMappingId(dataTable.getBankFieldMappingId());
				      setTableName(dataTable.getTableName());
				      populateCoulumnName();
				      setFieldName(dataTable.getFieldName());
				      setFieldvalue(dataTable.getFieldvalue());
				      setAdditionalBankRuleId(dataTable.getAdditionalBankRuleId());
				      setBankId(dataTable.getBankId());
				      setBankValue(dataTable.getBankValue());
				      setBankValueDesc(dataTable.getBankValueDesc());
				      setCreatedBy(dataTable.getCreatedBy());
				      setCreatedDate(dataTable.getCreatedDate());
				      setModifiedBy(dataTable.getModifiedBy());
				      setModifiedDate(dataTable.getModifiedDate());
				      setApplicationCountryId(dataTable.getApplicationCountryId());
				      setIsActive(dataTable.getIsActive());
				      setBooRenderDataTable(false);
				      setBooSaveOrExit(false);
				      setBooAdd(false);
				      setBooApproval(true);
				      setBooRead(true);
				      try {
						FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/bankfieldmapping.xhtml");
				      } catch (Exception e) {
						e.printStackTrace();
				      }     
			    }else{
				      RequestContext.getCurrentInstance().execute("notApproved.show();");
				      return;
			    }
		  }
		  
		  public void bankFieldMappingApproveRecord(){
			    String approvalMsg = bankFieldMappingService.checkBankFieldMappingMultiUser(getBankFieldMappingId(), sessionStateManage.getUserName());
			    if (approvalMsg.equalsIgnoreCase("Success")) {
				      RequestContext.getCurrentInstance().execute("approve.show();");
				      return;
			    } else {
				      RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				      return;
			    }    
		  }
		// click ok button for Approve
		  public void clickOnOKApprove() {
			    clear();
			    setBooAdd(false);
			    setBooRenderDataTable(false);
			    setBooSaveOrExit(false);
			    setBooApproval(false);
			    setBooRead(false);
			    try {
				      fetchRecordforBankFieldMappingForApprovalDataTable();
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/BankFieldMappingApproval.xhtml");
			    } catch (Exception e) {
				      e.printStackTrace();
			    }
		  }
		  public void clickOnOkButton() {
			    lstBankFieldMappingDataTable.clear();
			    fetchRecordforBankFieldMappingForApprovalDataTable();
			    try {
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/BankFieldMappingApproval.xhtml");
			    } catch (Exception e) {
				      e.printStackTrace();
			    }
		  }
		  public void bankFieldMappingApprovedByOhterPerson() {
			    fetchRecordforBankFieldMappingForApprovalDataTable();
			    try {
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/BankFieldMappingApproval.xhtml");
			    } catch (Exception e) {
				      e.printStackTrace();
			    }
		  }
		  public void bankFieldMappingCancel(){
			    fetchRecordforBankFieldMappingForApprovalDataTable();
			    try {
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/BankFieldMappingApproval.xhtml");
			    } catch (Exception e) {
				      e.printStackTrace();
			    }
		  }
		  //Approval Ended
}
