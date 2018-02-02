package com.amg.exchange.common.bean;

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

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.common.service.IPurposeOfTransctionService;
import com.amg.exchange.complaint.DTO.ComplaintAssignedDTO;
import com.amg.exchange.complaint.bean.ComplaintAssignedBean;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("purposeOfTransctionBean")
@Scope("session")
public class PurposeOfTransctionBean<T> implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;

	  private static final Logger log = Logger.getLogger(ComplaintAssignedBean.class);
	  // page level variables
	  private BigDecimal purposeOfTransctionPk;
	  private BigDecimal companyId;
	  private String CompanyName;
	  private BigDecimal applicationCountryId;
	  private String purposeOfCode;
	  private String englishFullDesc;
	  private String englishShortDesc;
	  private String arabicFullDesc;
	  private String arablicShortDesc;
	  // common Variables
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String isActive;
	  private String remarks;
	  private String dynamicLabelForActivateDeactivate;
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

	  private List<PurposeOfTransctionDataTable> purposeOfTransctionDtList = new CopyOnWriteArrayList<PurposeOfTransctionDataTable>();
	  private List<PurposeOfTransctionDataTable> purposeOfTransctionNewDtList = new CopyOnWriteArrayList<PurposeOfTransctionDataTable>();
	  private List<CompanyMasterDesc> lstCompanyMasterDesc = new ArrayList<CompanyMasterDesc>();
	  private PurposeOfTransctionDataTable purposeOfTransctionObj = null;
	  Map<BigDecimal, String> mapCompanyMasterDescList = new HashMap<BigDecimal, String>();

	  @Autowired
	  IPurposeOfTransctionService purposeOfTransctionService;

	  @Autowired
	  IGeneralService<T> generalService;

	  SessionStateManage sessionStateManage = new SessionStateManage();

	  public BigDecimal getPurposeOfTransctionPk() {
		    return purposeOfTransctionPk;
	  }

	  public void setPurposeOfTransctionPk(BigDecimal purposeOfTransctionPk) {
		    this.purposeOfTransctionPk = purposeOfTransctionPk;
	  }

	  public BigDecimal getCompanyId() {
		    return companyId;
	  }

	  public void setCompanyId(BigDecimal companyId) {
		    this.companyId = companyId;
	  }

	  public String getCompanyName() {
		    return CompanyName;
	  }

	  public void setCompanyName(String companyName) {
		    CompanyName = companyName;
	  }

	  public BigDecimal getApplicationCountryId() {
		    return applicationCountryId;
	  }

	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
		    this.applicationCountryId = applicationCountryId;
	  }

	  public String getPurposeOfCode() {
		    return purposeOfCode;
	  }

	  public void setPurposeOfCode(String purposeOfCode) {
		    this.purposeOfCode = purposeOfCode;
	  }

	  public String getEnglishFullDesc() {
		    return englishFullDesc;
	  }

	  public void setEnglishFullDesc(String englishFullDesc) {
		    this.englishFullDesc = englishFullDesc;
	  }

	  public String getEnglishShortDesc() {
		    return englishShortDesc;
	  }

	  public void setEnglishShortDesc(String englishShortDesc) {
		    this.englishShortDesc = englishShortDesc;
	  }

	  public String getArabicFullDesc() {
		    return arabicFullDesc;
	  }

	  public void setArabicFullDesc(String arabicFullDesc) {
		    this.arabicFullDesc = arabicFullDesc;
	  }

	  public String getArablicShortDesc() {
		    return arablicShortDesc;
	  }

	  public void setArablicShortDesc(String arablicShortDesc) {
		    this.arablicShortDesc = arablicShortDesc;
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

	  public String getDynamicLabelForActivateDeactivate() {
		    return dynamicLabelForActivateDeactivate;
	  }

	  public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		    this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
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

	  public List<PurposeOfTransctionDataTable> getPurposeOfTransctionDtList() {
		    return purposeOfTransctionDtList;
	  }

	  public void setPurposeOfTransctionDtList(List<PurposeOfTransctionDataTable> purposeOfTransctionDtList) {
		    this.purposeOfTransctionDtList = purposeOfTransctionDtList;
	  }

	  public List<PurposeOfTransctionDataTable> getPurposeOfTransctionNewDtList() {
		    return purposeOfTransctionNewDtList;
	  }

	  public void setPurposeOfTransctionNewDtList(List<PurposeOfTransctionDataTable> purposeOfTransctionNewDtList) {
		    this.purposeOfTransctionNewDtList = purposeOfTransctionNewDtList;
	  }

	  public List<CompanyMasterDesc> getLstCompanyMasterDesc() {
		    return lstCompanyMasterDesc;
	  }

	  public void setLstCompanyMasterDesc(List<CompanyMasterDesc> lstCompanyMasterDesc) {
		    this.lstCompanyMasterDesc = lstCompanyMasterDesc;
	  }

	  public Map<BigDecimal, String> getMapCompanyMasterDescList() {
		    return mapCompanyMasterDescList;
	  }

	  public void setMapCompanyMasterDescList(Map<BigDecimal, String> mapCompanyMasterDescList) {
		    this.mapCompanyMasterDescList = mapCompanyMasterDescList;
	  }

	  public PurposeOfTransctionDataTable getPurposeOfTransctionObj() {
		    return purposeOfTransctionObj;
	  }

	  public void setPurposeOfTransctionObj(PurposeOfTransctionDataTable purposeOfTransctionObj) {
		    this.purposeOfTransctionObj = purposeOfTransctionObj;
	  }

	  // Page Navigation For PurposeOfTransction
	  public void pageNavigationForPurposeOfTransaction() {
		    purposeOfTransctionDtList.clear();
		    purposeOfTransctionNewDtList.clear();
		    setBooRenderDataTable(false);
		    setBooSaveOrExit(false);
		    setBooAdd(true);
		    setBooApproval(false);
		    setBooRead(false);
		    setBooClearPanel(false);
		    setBooSubmitPanel(false);
		    clearAllFields();
		    // form loading to fetch All Company
		    fetchAllCompany();
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../common/PurposeOfTransaction.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }

	  }

	  // clear All Fields
	  public void clearAllFields() {
		    setPurposeOfTransctionPk(null);
		    setCompanyId(null);
		    setCompanyName(null);
		    setApplicationCountryId(null);
		    setPurposeOfCode(null);
		    setEnglishFullDesc(null);
		    setEnglishShortDesc(null);
		    setArabicFullDesc(null);
		    setArablicShortDesc(null);
	  }

	  // to Fetch Company list
	  public void fetchAllCompany() {
		    lstCompanyMasterDesc = generalService.getAllCompanyList(sessionStateManage.getLanguageId());
		    for (CompanyMasterDesc companyMasterDesc : lstCompanyMasterDesc) {
			      mapCompanyMasterDescList.put(companyMasterDesc.getFsCompanyMaster().getCompanyId(), companyMasterDesc.getCompanyName());
		    }
	  }

	  // Auto Complete method

	  public List<String> autoComplete(String query) {
		    if (query.length() > 0) {
			      return purposeOfTransctionService.toFetchPurposeOfCodeList(query);
		    } else {
			      return null;
		    }
	  }

	  // ajax calling
	  public void itemSelectPopulate() {
		    List<PurposeOfTransaction> lstPurposeOfTransaction = purposeOfTransctionService.toPurposeOfCodeAllValues(getPurposeOfCode());
		    if (lstPurposeOfTransaction.size() > 0) {
			      clearAllFields();
			      RequestContext.getCurrentInstance().execute("datatable.show();");
			      return;

		    }
	  }

	  // Duplicate Checking For Data Table
	  public void duplicateChekingPurposeOfTransction() {
		    if (purposeOfTransctionDtList.size() > 0) {
			      for (PurposeOfTransctionDataTable purposeOfTransctionDataTable : purposeOfTransctionDtList) {
					if (purposeOfTransctionDataTable.getPurposeOfCode().equalsIgnoreCase(getPurposeOfCode())) {
						  clearAllFields();
						  RequestContext.getCurrentInstance().execute("datatable.show();");
						  return;
					}
			      }
		    }
		    if (getPurposeOfCode() != null) {
			      addRecordsToDataTable();
		    }
	  }

	  // AddRecord To DataTable
	  public void addRecordsToDataTable() {
		    PurposeOfTransctionDataTable purposeOfTransctionDtObj = new PurposeOfTransctionDataTable();
		    purposeOfTransctionDtObj.setPurposeOfTransctionPk(getPurposeOfTransctionPk());
		    purposeOfTransctionDtObj.setPurposeOfCode(getPurposeOfCode());
		    purposeOfTransctionDtObj.setEnglishFullDesc(getEnglishFullDesc());
		    purposeOfTransctionDtObj.setEnglishShortDesc(getEnglishShortDesc());
		    purposeOfTransctionDtObj.setArabicFullDesc(getArabicFullDesc());
		    purposeOfTransctionDtObj.setArablicShortDesc(getArablicShortDesc());
		    purposeOfTransctionDtObj.setCompanyId(getCompanyId());
		    purposeOfTransctionDtObj.setCompanyName(mapCompanyMasterDescList.get(getCompanyId()));
		    purposeOfTransctionDtObj.setCreatedBy(getCreatedBy());
		    purposeOfTransctionDtObj.setCreatedDate(getCreatedDate());
		    if (getPurposeOfTransctionPk() != null) {
			      if (purposeOfTransctionDtObj.getCompanyId().equals(purposeOfTransctionObj.getCompanyId()) && purposeOfTransctionDtObj.getPurposeOfCode().equals(purposeOfTransctionObj.getPurposeOfCode())
						  && purposeOfTransctionDtObj.getEnglishFullDesc().equals(purposeOfTransctionObj.getEnglishFullDesc()) && purposeOfTransctionDtObj.getEnglishShortDesc().equals(purposeOfTransctionObj.getEnglishShortDesc())
						  && purposeOfTransctionDtObj.getArabicFullDesc().equals(purposeOfTransctionObj.getArabicFullDesc()) && purposeOfTransctionDtObj.getArablicShortDesc().equals(purposeOfTransctionObj.getArablicShortDesc())) {
					purposeOfTransctionDtObj.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					purposeOfTransctionDtObj.setIsActive(getIsActive());
					purposeOfTransctionDtObj.setModifiedBy(getModifiedBy());
					purposeOfTransctionDtObj.setModifiedDate(getModifiedDate());
					purposeOfTransctionDtObj.setApprovedBy(getApprovedBy());
					purposeOfTransctionDtObj.setApprovedDate(getApprovedDate());
					purposeOfTransctionDtObj.setRemarks(getRemarks());
			      } else {
					purposeOfTransctionDtObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					purposeOfTransctionDtObj.setIsActive(Constants.U);
					purposeOfTransctionDtObj.setModifiedBy(sessionStateManage.getUserName());
					purposeOfTransctionDtObj.setModifiedDate(new Date());
					purposeOfTransctionDtObj.setApprovedBy(null);
					purposeOfTransctionDtObj.setApprovedDate(null);
					purposeOfTransctionDtObj.setRemarks(null);
					purposeOfTransctionDtObj.setIfEditClicked(true);
			      }
		    } else {
			      purposeOfTransctionDtObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
			      purposeOfTransctionDtObj.setIsActive(Constants.U);
			      purposeOfTransctionDtObj.setCreatedBy(sessionStateManage.getUserName());
			      purposeOfTransctionDtObj.setCreatedDate(new Date());
			      purposeOfTransctionDtObj.setIfEditClicked(true);
		    }
		    purposeOfTransctionDtList.add(purposeOfTransctionDtObj);

		    if (getPurposeOfTransctionPk() == null) {
			      purposeOfTransctionNewDtList.add(purposeOfTransctionDtObj);
		    }
		    setBooRenderDataTable(true);
		    setBooSaveOrExit(true);
		    setBooApproval(false);
		    setBooRead(false);
		    clearAllFields();
	  }

	  //Save Data Table Records
	  public void saveAllPurposeOfTransction(){
		    for (PurposeOfTransctionDataTable purposeOfTransctionDTObj : purposeOfTransctionDtList) {
			      if(purposeOfTransctionDTObj.getIfEditClicked().equals(true)){
					PurposeOfTransaction purposeOfTransaction=new PurposeOfTransaction();
			      }
			      
		    }
	  }
}
