package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.treasury.model.ServiceGroupMaster;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("serviceGroupMasterBean")
@Scope("session")
public class ServiceGroupMasterBean<T> implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;

	  private BigDecimal serviceGroupId;
	  private BigDecimal appCountryId;
	  private String serviceGroupCode;
	  private String isActive;
	  private Date createdDate;
	  private String createdBy;
	  private Date modifiedDate;
	  private String modifiedBy;
	  private Date approvedDate;
	  private String approvedBy;
	  private String remarks;
	  private String serviceGroupLocalDesc;
	  private String serviceGroupEnglishDesc;
	  private BigDecimal serviceGroupEngDescId;
	  private BigDecimal serviceGroupLocalDescId;
	  private BigDecimal languageTypeId;

	  private Boolean booCheckUpdate = false;
	  private Boolean booCheckDelete = false;

	  private Boolean activateRecordCheck = false;

	  private Boolean booRenderInputPanel = false;
	  private Boolean booRenderDatatablePanel = false;
	  private String errorMessage;

	  @Autowired
	  IServiceGroupMasterService serviceGroupMasterService;

	  @Autowired
	  IGeneralService<T> generalService;

	  SessionStateManage sessionStateManage = new SessionStateManage();

	  private List<ServiceGroupBeanDataTable> lstGroupBeanDataTables = new CopyOnWriteArrayList<ServiceGroupBeanDataTable>();
	  private List<ServiceGroupBeanDataTable> newlstGroupBeanDataTables = new CopyOnWriteArrayList<ServiceGroupBeanDataTable>();

	  public BigDecimal getServiceGroupId() {
		    return serviceGroupId;
	  }

	  public void setServiceGroupId(BigDecimal serviceGroupId) {
		    this.serviceGroupId = serviceGroupId;
	  }

	  public BigDecimal getAppCountryId() {
		    return appCountryId;
	  }

	  public void setAppCountryId(BigDecimal appCountryId) {
		    this.appCountryId = appCountryId;
	  }

	  public String getServiceGroupCode() {
		    return serviceGroupCode;
	  }

	  public void setServiceGroupCode(String serviceGroupCode) {
		    this.serviceGroupCode = serviceGroupCode;
	  }

	  public String getIsActive() {
		    return isActive;
	  }

	  public void setIsActive(String isActive) {
		    this.isActive = isActive;
	  }

	  public Date getCreatedDate() {
		    return createdDate;
	  }

	  public void setCreatedDate(Date createdDate) {
		    this.createdDate = createdDate;
	  }

	  public String getCreatedBy() {
		    return createdBy;
	  }

	  public void setCreatedBy(String createdBy) {
		    this.createdBy = createdBy;
	  }

	  public Date getModifiedDate() {
		    return modifiedDate;
	  }

	  public void setModifiedDate(Date modifiedDate) {
		    this.modifiedDate = modifiedDate;
	  }

	  public String getModifiedBy() {
		    return modifiedBy;
	  }

	  public void setModifiedBy(String modifiedBy) {
		    this.modifiedBy = modifiedBy;
	  }

	  public Date getApprovedDate() {
		    return approvedDate;
	  }

	  public void setApprovedDate(Date approvedDate) {
		    this.approvedDate = approvedDate;
	  }

	  public String getApprovedBy() {
		    return approvedBy;
	  }

	  public void setApprovedBy(String approvedBy) {
		    this.approvedBy = approvedBy;
	  }

	  public String getRemarks() {
		    return remarks;
	  }

	  public void setRemarks(String remarks) {
		    this.remarks = remarks;
	  }

	  public String getServiceGroupLocalDesc() {
		    return serviceGroupLocalDesc;
	  }

	  public void setServiceGroupLocalDesc(String serviceGroupLocalDesc) {
		    this.serviceGroupLocalDesc = serviceGroupLocalDesc;
	  }

	  public String getServiceGroupEnglishDesc() {
		    return serviceGroupEnglishDesc;
	  }

	  public void setServiceGroupEnglishDesc(String serviceGroupEnglishDesc) {
		    this.serviceGroupEnglishDesc = serviceGroupEnglishDesc;
	  }

	  public BigDecimal getServiceGroupEngDescId() {
		    return serviceGroupEngDescId;
	  }

	  public void setServiceGroupEngDescId(BigDecimal serviceGroupEngDescId) {
		    this.serviceGroupEngDescId = serviceGroupEngDescId;
	  }

	  public BigDecimal getServiceGroupLocalDescId() {
		    return serviceGroupLocalDescId;
	  }

	  public void setServiceGroupLocalDescId(BigDecimal serviceGroupLocalDescId) {
		    this.serviceGroupLocalDescId = serviceGroupLocalDescId;
	  }

	  public BigDecimal getLanguageTypeId() {
		    return languageTypeId;
	  }

	  public void setLanguageTypeId(BigDecimal languageTypeId) {
		    this.languageTypeId = languageTypeId;
	  }

	  public Boolean getBooCheckUpdate() {
		    return booCheckUpdate;
	  }

	  public void setBooCheckUpdate(Boolean booCheckUpdate) {
		    this.booCheckUpdate = booCheckUpdate;
	  }

	  public Boolean getBooCheckDelete() {
		    return booCheckDelete;
	  }

	  public void setBooCheckDelete(Boolean booCheckDelete) {
		    this.booCheckDelete = booCheckDelete;
	  }

	  public Boolean getActivateRecordCheck() {
		    return activateRecordCheck;
	  }

	  public void setActivateRecordCheck(Boolean activateRecordCheck) {
		    this.activateRecordCheck = activateRecordCheck;
	  }

	  public List<ServiceGroupBeanDataTable> getLstGroupBeanDataTables() {
		    return lstGroupBeanDataTables;
	  }

	  public void setLstGroupBeanDataTables(List<ServiceGroupBeanDataTable> lstGroupBeanDataTables) {
		    this.lstGroupBeanDataTables = lstGroupBeanDataTables;
	  }

	  public Boolean getBooRenderInputPanel() {
		    return booRenderInputPanel;
	  }

	  public void setBooRenderInputPanel(Boolean booRenderInputPanel) {
		    this.booRenderInputPanel = booRenderInputPanel;
	  }

	  public Boolean getBooRenderDatatablePanel() {
		    return booRenderDatatablePanel;
	  }

	  public void setBooRenderDatatablePanel(Boolean booRenderDatatablePanel) {
		    this.booRenderDatatablePanel = booRenderDatatablePanel;
	  }

	  public IServiceGroupMasterService getServiceGroupMasterService() {
		    return serviceGroupMasterService;
	  }

	  public void setServiceGroupMasterService(IServiceGroupMasterService serviceGroupMasterService) {
		    this.serviceGroupMasterService = serviceGroupMasterService;
	  }

	  public IGeneralService<T> getGeneralService() {
		    return generalService;
	  }

	  public void setGeneralService(IGeneralService<T> generalService) {
		    this.generalService = generalService;
	  }
	  @Autowired
	  LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	  public void serviceGroupPageNavigation() {
		    setBooRenderInputPanel(true);
		    setBooRenderDatatablePanel(false);
		    setBooSaveOrExit(false);
		    setBooApproval(false);
		    setBooRead(false);
		    setBooClearPanel(false);
		    setBooSubmitPanel(false);
		    clearAllFields();
		    lstGroupBeanDataTables.clear();
		    newlstGroupBeanDataTables.clear();
		    clearAllFields();

		    try {
		    	loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "ServiceGroupMaster.xhtml");
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/ServiceGroupMaster.xhtml");
		    } catch (Exception e) {
	 			      e.printStackTrace();
		    }
	  }

	  // Auto Complete method
	  public List<String> autoComplete(String query) {
		    if (query.length() > 0) {
			      return serviceGroupMasterService.toFetchServiceGroupCodeList(query);
		    } else {
			      return null;
		    }
	  }

	  // ajax calling
	  public void itemSelectPopulate() {
		  try{
		    List<ServiceGroupMaster> lstServiceGroupMaster = serviceGroupMasterService.toServiceGroupCodeAllValues(getServiceGroupCode());
		    if (lstServiceGroupMaster.size() > 0) {
			      clearAllFields();
			      RequestContext.getCurrentInstance().execute("datatable.show();");
			      return;

		    }
		  }catch(NullPointerException  e){
				setErrorMessage("Method Name:itemSelectPopulate"+e.getMessage()); 
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
			catch(Exception e){
				setErrorMessage(e.getMessage()); 
				RequestContext.getCurrentInstance().execute("csp.show();");
			} 
	  }

	  // Duplicate Checking Method
	  public void duplicateChekingForDataTable() {
		    if (lstGroupBeanDataTables.size() != 0) {
			      for (ServiceGroupBeanDataTable serGroupDt : lstGroupBeanDataTables) {
					if (serGroupDt.getServiceGroupCode().equalsIgnoreCase(getServiceGroupCode())) {
						  clearAllFields();
						  RequestContext.getCurrentInstance().execute("datatable.show();");
						  setBooEditButton(false);
						  setBooClearPanel(false);
						  setBooSubmitPanel(false);
						  return;
					}
			      }
		    }
		    if (getServiceGroupCode() != null) {
			      addToDataTable();
		    }
	  }

	  public void addToDataTable() {
		  try{
		    setBooEditButton(false);
		    setBooClearPanel(false);
		    setBooSubmitPanel(false);
		    ServiceGroupBeanDataTable serviceGroupBeanDataTable = new ServiceGroupBeanDataTable();
		    serviceGroupBeanDataTable.setServiceGroupId(getServiceGroupId());
		    serviceGroupBeanDataTable.setServiceGroupCode(getServiceGroupCode());
		    serviceGroupBeanDataTable.setAppCountryId(sessionStateManage.getCountryId());
		    serviceGroupBeanDataTable.setServiceGroupEngDescId(getServiceGroupEngDescId());
		    serviceGroupBeanDataTable.setServiceGroupEnglishDesc(getServiceGroupEnglishDesc());
		    serviceGroupBeanDataTable.setServiceGroupLocalDescId(getServiceGroupLocalDescId());
		    serviceGroupBeanDataTable.setServiceGroupLocalDesc(getServiceGroupLocalDesc());
		    serviceGroupBeanDataTable.setEnglishLanguageId(getEnglishLanguageId());
		    serviceGroupBeanDataTable.setArabicLanguageId(getArabicLanguageId());
		    serviceGroupBeanDataTable.setCreatedBy(getCreatedBy());
		    serviceGroupBeanDataTable.setCreatedDate(getCreatedDate());
		    if (getServiceGroupId() != null) {
			      if (serviceGroupBeanDataTable.getServiceGroupCode().equalsIgnoreCase(serviceGroupBeanDtObj.getServiceGroupCode()) && serviceGroupBeanDataTable.getServiceGroupEnglishDesc().equalsIgnoreCase(serviceGroupBeanDtObj.getServiceGroupEnglishDesc())
						  && serviceGroupBeanDataTable.getServiceGroupLocalDesc().equalsIgnoreCase(serviceGroupBeanDtObj.getServiceGroupLocalDesc())) {
					serviceGroupBeanDataTable.setCreatedBy(getCreatedBy());
					serviceGroupBeanDataTable.setCreatedDate(getCreatedDate());
					serviceGroupBeanDataTable.setModifiedBy(getModifiedBy());
					serviceGroupBeanDataTable.setModifiedDate(getModifiedDate());
					serviceGroupBeanDataTable.setIsActive(getIsActive());
					serviceGroupBeanDataTable.setApprovedBy(getApprovedBy());
					serviceGroupBeanDataTable.setApprovedDate(getApprovedDate());
					serviceGroupBeanDataTable.setRemarks(getRemarks());
					serviceGroupBeanDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelActivateOrDeActivate());
			      } else {
					serviceGroupBeanDataTable.setCreatedBy(getCreatedBy());
					serviceGroupBeanDataTable.setCreatedDate(getCreatedDate());
					serviceGroupBeanDataTable.setModifiedBy(sessionStateManage.getUserName());
					serviceGroupBeanDataTable.setModifiedDate(new Date());
					serviceGroupBeanDataTable.setIsActive(Constants.U);
					serviceGroupBeanDataTable.setApprovedBy(null);
					serviceGroupBeanDataTable.setApprovedDate(null);
					serviceGroupBeanDataTable.setRemarks(null);
					serviceGroupBeanDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					serviceGroupBeanDataTable.setIfEditClicked(true);
			      }
		    } else {
			      serviceGroupBeanDataTable.setCreatedBy(sessionStateManage.getUserName());
			      serviceGroupBeanDataTable.setCreatedDate(new Date());
			      serviceGroupBeanDataTable.setModifiedBy(null);
			      serviceGroupBeanDataTable.setModifiedDate(null);
			      serviceGroupBeanDataTable.setApprovedBy(null);
			      serviceGroupBeanDataTable.setApprovedDate(null);
			      serviceGroupBeanDataTable.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
			      serviceGroupBeanDataTable.setIsActive(Constants.U);
			      serviceGroupBeanDataTable.setIfEditClicked(true);
		    }
		    lstGroupBeanDataTables.add(serviceGroupBeanDataTable);
		    if (getServiceGroupId() == null) {
			      newlstGroupBeanDataTables.add(serviceGroupBeanDataTable);
		    }
		    setBooRenderInputPanel(true);
		    setBooRenderDatatablePanel(true);
		    setBooSaveOrExit(true);
		    setBooApproval(false);
		    setBooRead(false);
		    clearAllFields();
		  }catch(NullPointerException  e){
				setErrorMessage("Method Name:addToDataTable"+e.getMessage()); 
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
			catch(Exception e){
				setErrorMessage(e.getMessage()); 
				RequestContext.getCurrentInstance().execute("csp.show();");
			} 
	  }

	  public void save() {
 
		    if (lstGroupBeanDataTables.isEmpty()) {
			      RequestContext context = RequestContext.getCurrentInstance();
			      context.execute("dataNotFund.show();");
		    } else {

			      try {

					for (ServiceGroupBeanDataTable serviceGroupBeanDataTable : lstGroupBeanDataTables) {
						  if (serviceGroupBeanDataTable.getIfEditClicked().equals(true)) {
							    ServiceGroupMaster serviceGroupMaster = new ServiceGroupMaster();
							    serviceGroupMaster.setServiceGroupId(serviceGroupBeanDataTable.getServiceGroupId());
							    CountryMaster countryMaster = new CountryMaster();
							    countryMaster.setCountryId(serviceGroupBeanDataTable.getAppCountryId());
							    serviceGroupMaster.setAppCountryMaster(countryMaster);
							    serviceGroupMaster.setServiceGroupCode(serviceGroupBeanDataTable.getServiceGroupCode());
							    serviceGroupMaster.setServiceGroupId(serviceGroupBeanDataTable.getServiceGroupId());
							    serviceGroupMaster.setCreatedBy(serviceGroupBeanDataTable.getCreatedBy());
							    serviceGroupMaster.setCreatedDate(serviceGroupBeanDataTable.getCreatedDate());
							    serviceGroupMaster.setModifiedBy(serviceGroupBeanDataTable.getModifiedBy());
							    serviceGroupMaster.setModifiedDate(serviceGroupBeanDataTable.getModifiedDate());
							    serviceGroupMaster.setApprovedBy(serviceGroupBeanDataTable.getApprovedBy());
							    serviceGroupMaster.setApprovedDate(serviceGroupBeanDataTable.getApprovedDate());
							    serviceGroupMaster.setIsActive(serviceGroupBeanDataTable.getIsActive());
							    serviceGroupMaster.setRemarks(serviceGroupBeanDataTable.getRemarks());

							    // getServiceGroupMasterService().save(serviceGroupMaster);

							    ServiceGroupMasterDesc serviceGroupMasterDesc = new ServiceGroupMasterDesc();
							    LanguageType languageType = new LanguageType();
							    languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
							    serviceGroupMasterDesc.setLanguageType(languageType);
							    serviceGroupMasterDesc.setServiceGroupMasterDescId(serviceGroupBeanDataTable.getServiceGroupEngDescId());
							    serviceGroupMasterDesc.setServiceGroupDesc(serviceGroupBeanDataTable.getServiceGroupEnglishDesc());
							    serviceGroupMasterDesc.setServiceGroupMasterId(serviceGroupMaster);
							    // getServiceGroupMasterService().save(serviceGroupMasterDesc);

							    ServiceGroupMasterDesc serviceGroupMasterDesc1 = new ServiceGroupMasterDesc();
							    LanguageType languageType1 = new LanguageType();
							    languageType1.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
							    serviceGroupMasterDesc1.setLanguageType(languageType1);
							    serviceGroupMasterDesc1.setServiceGroupMasterDescId(serviceGroupBeanDataTable.getServiceGroupLocalDescId());
							    serviceGroupMasterDesc1.setServiceGroupDesc(serviceGroupBeanDataTable.getServiceGroupLocalDesc());
							    serviceGroupMasterDesc1.setServiceGroupMasterId(serviceGroupMaster);
							    // getServiceGroupMasterService().save(serviceGroupMasterDesc1);
							    serviceGroupMasterService.saveOrUpdate(serviceGroupMaster, serviceGroupMasterDesc, serviceGroupMasterDesc1);
						  }
					}
					lstGroupBeanDataTables.clear();
					newlstGroupBeanDataTables.clear();
					RequestContext.getCurrentInstance().execute("complete.show();");
					 setBooRenderDatatablePanel(false);
					setBooSaveOrExit(false);
					setBooApproval(false);
					setBooRead(false);

			      } catch(NullPointerException  e){
						setErrorMessage("Method Name:save"+e.getMessage()); 
						RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
					}
					catch(Exception e){
						setErrorMessage(e.getMessage()); 
						RequestContext.getCurrentInstance().execute("csp.show();");
					} 

		    }

	  }

	  public void edit(ServiceGroupBeanDataTable serviceGroupBeanDataTable) {
		    setServiceGroupBeanDtObj(serviceGroupBeanDataTable);
		    setServiceGroupCode(serviceGroupBeanDataTable.getServiceGroupCode());
		    setAppCountryId(serviceGroupBeanDataTable.getAppCountryId());
		    setServiceGroupEngDescId(serviceGroupBeanDataTable.getServiceGroupEngDescId());
		    setServiceGroupEnglishDesc(serviceGroupBeanDataTable.getServiceGroupEnglishDesc());
		    setServiceGroupLocalDescId(serviceGroupBeanDataTable.getServiceGroupLocalDescId());
		    setServiceGroupLocalDesc(serviceGroupBeanDataTable.getServiceGroupLocalDesc());
		    setServiceGroupId(serviceGroupBeanDataTable.getServiceGroupId());
		    setEnglishLanguageId(serviceGroupBeanDataTable.getEnglishLanguageId());
		    setArabicLanguageId(serviceGroupBeanDataTable.getArabicLanguageId());
		    setRemarks(serviceGroupBeanDataTable.getRemarks());
		    setCreatedBy(serviceGroupBeanDataTable.getCreatedBy());
		    setCreatedDate(serviceGroupBeanDataTable.getCreatedDate());
		    setApprovedBy(serviceGroupBeanDataTable.getApprovedBy());
		    setApprovedDate(serviceGroupBeanDataTable.getApprovedDate());
		    setModifiedBy(serviceGroupBeanDataTable.getModifiedBy());
		    setModifiedDate(serviceGroupBeanDataTable.getModifiedDate());
		    setIsActive(serviceGroupBeanDataTable.getIsActive());
		    setDynamicLabelActivateOrDeActivate(serviceGroupBeanDataTable.getDynamicLabelForActivateDeactivate());
		    lstGroupBeanDataTables.remove(serviceGroupBeanDataTable);
		    newlstGroupBeanDataTables.remove(serviceGroupBeanDataTable);
		    if (lstGroupBeanDataTables.size() == 0) {
			      setBooRenderDatatablePanel(false);
			      setBooSaveOrExit(false);
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

	  public void clearAllFields() {

		    setServiceGroupCode(null);
		    setServiceGroupEnglishDesc(null);
		    setServiceGroupLocalDesc(null);
		    setServiceGroupId(null);
		    setServiceGroupEngDescId(null);
		    setServiceGroupEnglishDesc(null);
		    setServiceGroupLocalDescId(null);
		    setServiceGroupLocalDesc(null);
		    setServiceGroupId(null);
		    setAppCountryId(null);
		    setEnglishLanguageId(null);
		    setArabicLanguageId(null);
		    // setIsActive(null);

	  }

	  public void view() {
		  try{
		    lstGroupBeanDataTables.clear();
		    Boolean childRecordCheck = false;
		    clearAllFields();
		    List<ServiceGroupMaster> objList = getServiceGroupMasterService().getServiceGroupMasterList();

		    if (objList.size() != 0) {
			      setBooRenderDatatablePanel(true);
			      setBooSaveOrExit(true);
			      setBooApproval(false);
			      setBooRead(false);
		    } else {
			      setBooRenderDatatablePanel(false);
			      setBooSaveOrExit(false);
			      setBooApproval(false);
			      setBooRead(false);
			      RequestContext.getCurrentInstance().execute("noRecords.show();");
			      return;
		    }

		    for (ServiceGroupMaster serviceGroupMaster : objList) {

			      ServiceGroupBeanDataTable serviceGroupBean = new ServiceGroupBeanDataTable();

			      serviceGroupBean.setServiceGroupId(serviceGroupMaster.getServiceGroupId());
			      serviceGroupBean.setServiceGroupCode(serviceGroupMaster.getServiceGroupCode());
			      serviceGroupBean.setCreatedBy(serviceGroupMaster.getCreatedBy());
			      serviceGroupBean.setCreatedDate(serviceGroupMaster.getCreatedDate());
			      serviceGroupBean.setModifiedBy(serviceGroupMaster.getModifiedBy());
			      serviceGroupBean.setModifiedDate(serviceGroupMaster.getModifiedDate());
			      serviceGroupBean.setApprovedBy(serviceGroupMaster.getApprovedBy());
			      serviceGroupBean.setApprovedDate(serviceGroupMaster.getApprovedDate());
			      serviceGroupBean.setIsActive(serviceGroupMaster.getIsActive());
			      // serviceGroupBean.setDynamicLabelForActivateDeactivate(setreverActiveStatus(serviceGroupMaster.getIsActive()));
			      serviceGroupBean.setAppCountryId(serviceGroupMaster.getAppCountryMaster().getCountryId());
			      if (serviceGroupBean.getIsActive().equalsIgnoreCase(Constants.Yes)) {
					serviceGroupBean.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
			      } else if (serviceGroupBean.getIsActive().equalsIgnoreCase(Constants.D)) {
					serviceGroupBean.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
			      } else if (serviceGroupBean.getIsActive().equalsIgnoreCase(Constants.U) && serviceGroupBean.getModifiedBy() == null && serviceGroupBean.getModifiedDate() == null && serviceGroupBean.getApprovedBy() == null && serviceGroupBean.getApprovedDate() == null
						  && serviceGroupBean.getRemarks() == null) {
					serviceGroupBean.setDynamicLabelForActivateDeactivate(Constants.DELETE);
			      } else {
					serviceGroupBean.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
			      }
			      List<ServiceGroupMasterDesc> objDescList = getServiceGroupMasterService().getServiceGroupDescList(serviceGroupMaster.getServiceGroupId());
			      if (objDescList.size() != 0) {
					childRecordCheck = true;
					for (ServiceGroupMasterDesc serviceGroupMasterDesc : objDescList) {

						  if (serviceGroupMasterDesc.getLanguageType().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))) {
							    // serviceGroupBean.setLanguageTypeId(serviceGroupMasterDesc.getLanguageType().getLanguageId());
							    serviceGroupBean.setServiceGroupEnglishDesc(serviceGroupMasterDesc.getServiceGroupDesc());
							    serviceGroupBean.setServiceGroupEngDescId(serviceGroupMasterDesc.getServiceGroupMasterDescId());
							    serviceGroupBean.setEnglishLanguageId(serviceGroupMasterDesc.getLanguageType().getLanguageId());

						  } else if (serviceGroupMasterDesc.getLanguageType().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
							    // serviceGroupBean.setLanguageTypeId(serviceGroupMasterDesc.getLanguageType().getLanguageId());
							    serviceGroupBean.setServiceGroupLocalDesc(serviceGroupMasterDesc.getServiceGroupDesc());
							    serviceGroupBean.setServiceGroupLocalDescId(serviceGroupMasterDesc.getServiceGroupMasterDescId());
							    serviceGroupBean.setArabicLanguageId(serviceGroupMasterDesc.getLanguageType().getLanguageId());

						  }
					}
			      } else {
					childRecordCheck = false;
			      }

			      if (childRecordCheck == true) {
					lstGroupBeanDataTables.add(serviceGroupBean);
			      }

		    }
		    lstGroupBeanDataTables.addAll(newlstGroupBeanDataTables);
		    clearAllFields();
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/ServiceGroupMaster.xhtml");
		    } catch (Exception exception) {
			      exception.printStackTrace();
			      // log.info("exception.getMessage()::::::::::::::::::::::::::::::::::::::::::::::::::::::::"
			      // + exception.getMessage());
		    }
		  } catch(NullPointerException  e){
				setErrorMessage("Method Name:removeRecord"+e.getMessage()); 
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
			catch(Exception e){
				setErrorMessage(e.getMessage()); 
				RequestContext.getCurrentInstance().execute("csp.show();");
			} 
	  }

	  public void confirmPermanentDelete() {
		    if (lstGroupBeanDataTables.size() > 0) {
			      for (ServiceGroupBeanDataTable serviceGroupBeanDataTable : lstGroupBeanDataTables) {
					if (serviceGroupBeanDataTable.getBooCheckDelete() != null) {
						  if (serviceGroupBeanDataTable.getBooCheckDelete().equals(true)) {
							    delete(serviceGroupBeanDataTable);
							    lstGroupBeanDataTables.remove(serviceGroupBeanDataTable);

							    view();
						  }
					}
			      }
		    }

	  }

	  public void delete(ServiceGroupBeanDataTable serviceGroupBeanDataTable) {
		    getServiceGroupMasterService().delete(serviceGroupBeanDataTable.getServiceGroupId(), serviceGroupBeanDataTable.getServiceGroupEngDescId(), serviceGroupBeanDataTable.getServiceGroupLocalDescId());
		    RequestContext.getCurrentInstance().execute("delete.show();");
	  }

	  public void checkStatusType(ServiceGroupBeanDataTable serviceGroupBeanDataTable) throws IOException {
		  try{ 
		  if (serviceGroupBeanDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			      serviceGroupBeanDataTable.setRemarksCheck(true);
			      setApprovedBy(serviceGroupBeanDataTable.getApprovedBy());
			      setApprovedDate(serviceGroupBeanDataTable.getApprovedDate());
			      RequestContext.getCurrentInstance().execute("remarks.show();");
			      return;
		    } else if (serviceGroupBeanDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)) {
			      serviceGroupBeanDataTable.setBooCheckDelete(true);
			      RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			      return;
		    } else if (serviceGroupBeanDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			      serviceGroupBeanDataTable.setActivateRecordCheck(true);
			      RequestContext.getCurrentInstance().execute("activateRecord.show();");
			      return;
		    } else if (serviceGroupBeanDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			      lstGroupBeanDataTables.remove(serviceGroupBeanDataTable);
			      newlstGroupBeanDataTables.remove(serviceGroupBeanDataTable);
		    } else if (serviceGroupBeanDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && serviceGroupBeanDataTable.getModifiedBy() == null && serviceGroupBeanDataTable.getModifiedDate() == null && serviceGroupBeanDataTable.getApprovedBy() == null
					&& serviceGroupBeanDataTable.getApprovedDate() == null && serviceGroupBeanDataTable.getRemarks() == null) {
			      serviceGroupBeanDataTable.setPermentDeleteCheck(true);
			      RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			      return;
		    } else if (serviceGroupBeanDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
			      RequestContext.getCurrentInstance().execute("pending.show();");
			      return;
		    }

		    if (lstGroupBeanDataTables.size() == 0) {
			      setBooRenderDatatablePanel(false);
			      setBooSaveOrExit(false);
			      setBooApproval(false);
			      setBooRead(false);
		    }
		  } catch(Exception e){
		    	setErrorMessage("Method Name:checkStatusType"+e.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		    } 
	  }

	  public void remarkSelectedRecord() throws IOException {
		    if (getRemarks() != null && !getRemarks().equals("")) {
			      for (ServiceGroupBeanDataTable serviceGroupBeanDataTable : lstGroupBeanDataTables) {
					if (serviceGroupBeanDataTable.getRemarksCheck().equals(true)) {
						  serviceGroupBeanDataTable.setRemarks(getRemarks());
						  serviceGroupBeanDataTable.setApprovedBy(null);
						  serviceGroupBeanDataTable.setApprovedDate(null);
						  serviceGroupBeanDataTable.setRemarksCheck(true);
						  clearAllFields();
						  update(serviceGroupBeanDataTable);
						  setRemarks(null);
						  view();
					}

			      }
		    } else {
			      RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
			      return;
		    }

	  }

	  public void update(ServiceGroupBeanDataTable dataTable) {
		    serviceGroupMasterService.upDateActiveRecordToDeAtivate(dataTable.getServiceGroupId(), sessionStateManage.getUserName(), getRemarks());
	  }

	  public void activateRecord() {

		    if (lstGroupBeanDataTables.size() > 0) {
			      for (ServiceGroupBeanDataTable serviceGroupBeanDataTable : lstGroupBeanDataTables) {
					if (serviceGroupBeanDataTable.getActivateRecordCheck() != null) {
						  if (serviceGroupBeanDataTable.getActivateRecordCheck().equals(true)) {
							    getServiceGroupMasterService().activateRecord(serviceGroupBeanDataTable.getServiceGroupId(), sessionStateManage.getUserName());
							    view();
							    clearAllFields();
							    return;
						  }
					}
			      }
		    }

	  }

	  public void serviceGroupApprovePageNavigation() {

		    try {
		    	  loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "servicegroupapprovel.xhtml");
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/servicegroupapprovel.xhtml");
			      approveView();
			      setBooRenderDatatablePanel(true);

		    }  catch(Exception e){
		    	setErrorMessage(e.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("csp.show();");
		    } 
	  }

	  public void approveView() {
		  try{
		    lstGroupBeanDataTables.clear();
		    List<ServiceGroupMaster> objList = getServiceGroupMasterService().getServiceGroupMasterList();

		    if (objList != null) {

			      for (ServiceGroupMaster serviceGroupMaster : objList) {

					if (serviceGroupMaster.getIsActive().equals(Constants.U)) {
						  ServiceGroupBeanDataTable serviceGroupBean = new ServiceGroupBeanDataTable();

						  serviceGroupBean.setServiceGroupId(serviceGroupMaster.getServiceGroupId());
						  serviceGroupBean.setServiceGroupCode(serviceGroupMaster.getServiceGroupCode());
						  serviceGroupBean.setCreatedBy(serviceGroupMaster.getCreatedBy());
						  serviceGroupBean.setCreatedDate(serviceGroupMaster.getCreatedDate());
						  serviceGroupBean.setModifiedBy(serviceGroupMaster.getModifiedBy());
						  serviceGroupBean.setModifiedDate(serviceGroupMaster.getModifiedDate());
						  serviceGroupBean.setApprovedBy(serviceGroupMaster.getApprovedBy());
						  serviceGroupBean.setApprovedDate(serviceGroupMaster.getApprovedDate());
						  serviceGroupBean.setIsActive(serviceGroupMaster.getIsActive());
						  serviceGroupBean.setAppCountryId(serviceGroupMaster.getAppCountryMaster().getCountryId());

						  List<ServiceGroupMasterDesc> objDescList = getServiceGroupMasterService().getServiceGroupDescList(serviceGroupMaster.getServiceGroupId());

						  if (objDescList != null) {
							    for (ServiceGroupMasterDesc serviceGroupMasterDesc : objDescList) {

								      if (serviceGroupMasterDesc.getLanguageType().getLanguageId() != null && serviceGroupMasterDesc.getLanguageType().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))) {
										// serviceGroupBean.setLanguageTypeId(serviceGroupMasterDesc.getLanguageType().getLanguageId());
										serviceGroupBean.setServiceGroupEnglishDesc(serviceGroupMasterDesc.getServiceGroupDesc());
										serviceGroupBean.setServiceGroupEngDescId(serviceGroupMasterDesc.getServiceGroupMasterDescId());
										serviceGroupBean.setEnglishLanguageId(serviceGroupMasterDesc.getLanguageType().getLanguageId());

								      } else if (serviceGroupMasterDesc.getLanguageType().getLanguageId() != null && serviceGroupMasterDesc.getLanguageType().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
										// serviceGroupBean.setLanguageTypeId(serviceGroupMasterDesc.getLanguageType().getLanguageId());
										serviceGroupBean.setServiceGroupLocalDesc(serviceGroupMasterDesc.getServiceGroupDesc());
										serviceGroupBean.setServiceGroupLocalDescId(serviceGroupMasterDesc.getServiceGroupMasterDescId());
										serviceGroupBean.setArabicLanguageId(serviceGroupMasterDesc.getLanguageType().getLanguageId());
								      }
							    }
						  }

						  lstGroupBeanDataTables.add(serviceGroupBean);
						  setBooRenderInputPanel(false);
						  setBooRenderDatatablePanel(true);
					}

			      }

		    }
		  }catch(NullPointerException  e){
				setErrorMessage("Method Name:approveView"+e.getMessage()); 
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
			catch(Exception e){
				setErrorMessage(e.getMessage()); 
				RequestContext.getCurrentInstance().execute("csp.show();");
			} 
	  }

	  public void approve(ServiceGroupBeanDataTable serviceGroupBeanDataTable) {

		    if (!(serviceGroupBeanDataTable.getModifiedBy() == null ? serviceGroupBeanDataTable.getCreatedBy() : serviceGroupBeanDataTable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())) {
			      try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/servicegroupapprovel.xhtml");
					setBooRenderInputPanel(true);
					setBooRenderDatatablePanel(false);
					setServiceGroupId(serviceGroupBeanDataTable.getServiceGroupId());
					setServiceGroupCode(serviceGroupBeanDataTable.getServiceGroupCode());
					setServiceGroupEngDescId(serviceGroupBeanDataTable.getServiceGroupEngDescId());
					setServiceGroupEnglishDesc(serviceGroupBeanDataTable.getServiceGroupEnglishDesc());
					setServiceGroupLocalDescId(serviceGroupBeanDataTable.getServiceGroupLocalDescId());
					setServiceGroupLocalDesc(serviceGroupBeanDataTable.getServiceGroupLocalDesc());
					setServiceGroupId(serviceGroupBeanDataTable.getServiceGroupId());
					setAppCountryId(serviceGroupBeanDataTable.getAppCountryId());
					setCreatedBy(serviceGroupBeanDataTable.getCreatedBy());
					setCreatedDate(serviceGroupBeanDataTable.getCreatedDate());
					setApprovedBy(serviceGroupBeanDataTable.getApprovedBy());
					setApprovedDate(serviceGroupBeanDataTable.getApprovedDate());
					setModifiedBy(serviceGroupBeanDataTable.getModifiedBy());
					setModifiedDate(serviceGroupBeanDataTable.getModifiedDate());
					setIsActive(serviceGroupBeanDataTable.getIsActive());

			      } catch(NullPointerException  e){
						setErrorMessage("Method Name:approve"+e.getMessage()); 
						RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
					}
					catch(Exception e){
						setErrorMessage(e.getMessage()); 
						RequestContext.getCurrentInstance().execute("csp.show();");
					} 

		    } else {
			      RequestContext.getCurrentInstance().execute("notValidUser.show();");

		    }

	  }

	  public void approveSave() {
		  try{
		    ServiceGroupMaster serviceGroupMaster = new ServiceGroupMaster();

		    serviceGroupMaster.setServiceGroupId(getServiceGroupId());
		    serviceGroupMaster.setServiceGroupCode(getServiceGroupCode());

		    CountryMaster countryMaster = new CountryMaster();
		    countryMaster.setCountryId(getAppCountryId());
		    serviceGroupMaster.setAppCountryMaster(countryMaster);

		    serviceGroupMaster.setCreatedBy(getCreatedBy());
		    serviceGroupMaster.setCreatedDate(getCreatedDate());
		    serviceGroupMaster.setModifiedBy(getModifiedBy());
		    serviceGroupMaster.setModifiedDate(getModifiedDate());
		    serviceGroupMaster.setApprovedBy(sessionStateManage.getUserName());
		    serviceGroupMaster.setApprovedDate(new Date());
		    serviceGroupMaster.setRemarks(null);
		    serviceGroupMaster.setIsActive(Constants.Yes);

		    getServiceGroupMasterService().save(serviceGroupMaster);

		    RequestContext context = RequestContext.getCurrentInstance();
		    context.execute("complete.show();");
} catch(NullPointerException  e){
	setErrorMessage("Method Name:approve"+e.getMessage()); 
	RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
}
catch(Exception e){
	setErrorMessage(e.getMessage()); 
	RequestContext.getCurrentInstance().execute("csp.show();");
} 

	  }

	  public void exit() {

		    try {
			      List<RoleMaster> rolList = getGeneralService().getRoleList(new BigDecimal(sessionStateManage.getRoleId()));

			      if (rolList.get(0).getRoleName().equalsIgnoreCase(Constants.USER_ROLE_MANAGER)) {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");

					clearAllFields();
			      } else {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");

					clearAllFields();

			      }

		    } catch(NullPointerException  e){
		    	setErrorMessage("Method Name:exit"+e.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		    }
		    catch(Exception e){
		    	setErrorMessage(e.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("csp.show();");
		    } 

	  }

	  public void clickOnOK() {
		    lstGroupBeanDataTables.clear();
		    newlstGroupBeanDataTables.clear();
		    setBooRenderDatatablePanel(false);
		    setBooSaveOrExit(false);
		    setBooApproval(false);
		    setBooRead(false);
		    clearAllFields();
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/ServiceGroupMaster.xhtml");

		    }  catch(Exception e){
		    	setErrorMessage(e.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("csp.show();");
		    } 

	  }

	  public void approveOK() {
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/servicegroupapprovel.xhtml");
			      setBooRenderInputPanel(false);
			      setBooRenderDatatablePanel(true);
			      approveView();
			      clearAllFields();

		    }  catch(Exception e){
		    	setErrorMessage(e.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("csp.show();");
		    } 

	  }

	  public List<ServiceGroupBeanDataTable> getNewlstGroupBeanDataTables() {
		    return newlstGroupBeanDataTables;
	  }

	  public void setNewlstGroupBeanDataTables(List<ServiceGroupBeanDataTable> newlstGroupBeanDataTables) {
		    this.newlstGroupBeanDataTables = newlstGroupBeanDataTables;
	  }

	  private BigDecimal englishLanguageId;
	  private BigDecimal arabicLanguageId;
	  private ServiceGroupBeanDataTable serviceGroupBeanDtObj = null;
	  private String dynamicLabelActivateOrDeActivate;
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

	  public BigDecimal getEnglishLanguageId() {
		    return englishLanguageId;
	  }

	  public void setEnglishLanguageId(BigDecimal englishLanguageId) {
		    this.englishLanguageId = englishLanguageId;
	  }

	  public BigDecimal getArabicLanguageId() {
		    return arabicLanguageId;
	  }

	  public void setArabicLanguageId(BigDecimal arabicLanguageId) {
		    this.arabicLanguageId = arabicLanguageId;
	  }

	  public ServiceGroupBeanDataTable getServiceGroupBeanDtObj() {
		    return serviceGroupBeanDtObj;
	  }

	  public void setServiceGroupBeanDtObj(ServiceGroupBeanDataTable serviceGroupBeanDtObj) {
		    this.serviceGroupBeanDtObj = serviceGroupBeanDtObj;
	  }

	  public String getDynamicLabelActivateOrDeActivate() {
		    return dynamicLabelActivateOrDeActivate;
	  }

	  public void setDynamicLabelActivateOrDeActivate(String dynamicLabelActivateOrDeActivate) {
		    this.dynamicLabelActivateOrDeActivate = dynamicLabelActivateOrDeActivate;
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

	  public void cancelRemarks() {
		    setRemarks(null);
		    setBooApproval(false);
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/ServiceGroupMaster.xhtml");
		    } catch(Exception e){
		    	setErrorMessage(e.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("csp.show();");
		    } 
	  }

	  public void disableSubmit() {
		    setBooSubmitPanel(true);
	  }

	  public void cancelApproval() {
		    approveView();
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/servicegroupapprovel.xhtml");
		    }  catch(Exception e){
		    	setErrorMessage(e.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("csp.show();");
		    } 
	  }

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
