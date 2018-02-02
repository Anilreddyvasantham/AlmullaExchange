package com.amg.exchange.loyalty.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.loyalty.model.LoyaltyMasterEncashment;
import com.amg.exchange.loyalty.service.ILoyaltyEncashmentService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("encashment")
@Scope("session")
@SuppressWarnings("unused")
public class EncashmentBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(EncashmentBean.class);
	private BigDecimal encashmentPk;
	private BigDecimal points;
	private BigDecimal equivalentValue;
	private String description = null;
	private Date effectiveDateFrom;
	private Date effectiveDateTo;
	private String activeFlag;
	private Date minDate;
	private BigDecimal applicationCountryId;
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

	private EncashmentDataTable encashmentObj = null;

	private List<EncashmentDataTable> lstEncashmentDataTables = new CopyOnWriteArrayList<EncashmentDataTable>();
	private List<EncashmentDataTable> encashmentDataTablesNewList = new CopyOnWriteArrayList<EncashmentDataTable>();

	SessionStateManage sessionManage = new SessionStateManage();

	@Autowired
	ILoyaltyEncashmentService loyaltyEncashmentService;

	public BigDecimal getPoints() {
		return points;
	}

	public void setPoints(BigDecimal points) {
		this.points = points;
	}

	public BigDecimal getEquivalentValue() {
		return equivalentValue;
	}

	public void setEquivalentValue(BigDecimal equivalentValue) {
		this.equivalentValue = equivalentValue;
	}

	public Date getEffectiveDateFrom() {
		return effectiveDateFrom;
	}

	public void setEffectiveDateFrom(Date effectiveDateFrom) {
		this.effectiveDateFrom = effectiveDateFrom;
	}

	public Date getEffectiveDateTo() {
		return effectiveDateTo;
	}

	public void setEffectiveDateTo(Date effectiveDateTo) {
		this.effectiveDateTo = effectiveDateTo;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Date getMinDate() {
		return new Date();
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public BigDecimal getEncashmentPk() {
		return encashmentPk;
	}

	public void setEncashmentPk(BigDecimal encashmentPk) {
		this.encashmentPk = encashmentPk;
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

	public List<EncashmentDataTable> getLstEncashmentDataTables() {
		return lstEncashmentDataTables;
	}

	public void setLstEncashmentDataTables(List<EncashmentDataTable> lstEncashmentDataTables) {
		this.lstEncashmentDataTables = lstEncashmentDataTables;
	}

	public List<EncashmentDataTable> getEncashmentDataTablesNewList() {
		return encashmentDataTablesNewList;
	}

	public void setEncashmentDataTablesNewList(List<EncashmentDataTable> encashmentDataTablesNewList) {
		this.encashmentDataTablesNewList = encashmentDataTablesNewList;
	}

	public EncashmentDataTable getEncashmentObj() {
		return encashmentObj;
	}

	public void setEncashmentObj(EncashmentDataTable encashmentObj) {
		this.encashmentObj = encashmentObj;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// Duplicate Record Checking For Data Table
	public void duplicateChekingEncashment() {
		if (lstEncashmentDataTables.size() > 0) {
			for (EncashmentDataTable encashmentDataTable : lstEncashmentDataTables) {
				if (encashmentDataTable.getPoints().equals(getPoints())) {
					clearAllFields();
					RequestContext.getCurrentInstance().execute("datatable.show();");
					return;
				}
			}
		}
		if (getPoints() != null) {
			addRecordsToDataTable();
		}
	}

	// add Records To Data Table
	public void addRecordsToDataTable() {
		try{
			setBooEditButton(false);
			setBooClearPanel(false);
			setBooSubmitPanel(false);
			EncashmentDataTable encashmentDtObj = new EncashmentDataTable();
			encashmentDtObj.setPoints(getPoints());
			encashmentDtObj.setEquivalentValue(getEquivalentValue());
			if (getDescription() == null) {
				encashmentDtObj.setDescription(null);
			} else {
				encashmentDtObj.setDescription(getDescription());
			}

			encashmentDtObj.setEffectiveDateFrom(getEffectiveDateFrom());
			encashmentDtObj.setEffectiveDateTo(getEffectiveDateTo());
			encashmentDtObj.setApplicationCountryId(sessionManage.getCountryId());
			if (getActiveFlag() != null) {
				if (getActiveFlag().equalsIgnoreCase(Constants.Yes)) {
					encashmentDtObj.setActiveFlag(Constants.YES);
				} else {
					encashmentDtObj.setActiveFlag(Constants.NO);
				}
			}
			encashmentDtObj.setCreatedBy(getCreatedBy());
			encashmentDtObj.setCreatedDate(getCreatedDate());
			if (getEncashmentPk() != null) {
				if (encashmentDtObj.getPoints().equals(encashmentObj.getPoints()) && encashmentDtObj.getEquivalentValue().equals(encashmentObj.getEquivalentValue()) && encashmentDtObj.getDescription().equals(encashmentObj.getDescription())
						&& encashmentDtObj.getEffectiveDateFrom().equals(encashmentObj.getEffectiveDateFrom()) && encashmentDtObj.getEffectiveDateTo().equals(encashmentObj.getEffectiveDateTo())
						&& encashmentDtObj.getActiveFlag().equalsIgnoreCase(encashmentObj.getActiveFlag())) {
					encashmentDtObj.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					encashmentDtObj.setIsActive(getIsActive());
					encashmentDtObj.setModifiedBy(getModifiedBy());
					encashmentDtObj.setModifiedDate(getModifiedDate());
					encashmentDtObj.setApprovedBy(getApprovedBy());
					encashmentDtObj.setApprovedDate(getApprovedDate());
					encashmentDtObj.setRemarks(getRemarks());
					encashmentDtObj.setEncashmentPk(getEncashmentPk());
				} else {
					encashmentDtObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					encashmentDtObj.setIsActive(Constants.U);
					encashmentDtObj.setModifiedBy(sessionManage.getUserName());
					encashmentDtObj.setModifiedDate(new Date());
					encashmentDtObj.setApprovedBy(null);
					encashmentDtObj.setApprovedDate(null);
					encashmentDtObj.setRemarks(null);
					encashmentDtObj.setEncashmentPk(getEncashmentPk());
					encashmentDtObj.setIfEditClicked(true);
				}
			} else {
				encashmentDtObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				encashmentDtObj.setIsActive(Constants.U);
				encashmentDtObj.setCreatedBy(sessionManage.getUserName());
				encashmentDtObj.setCreatedDate(new Date());
				encashmentDtObj.setIfEditClicked(true);
			}
			lstEncashmentDataTables.add(encashmentDtObj);

			if (getEncashmentPk() == null) {
				encashmentDataTablesNewList.add(encashmentDtObj);
			}
			setBooRenderDataTable(true);
			setBooSaveOrExit(true);
			setBooApproval(false);
			setBooRead(false);
			clearAllFields();
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}  
	}

	// Save
	public void save() {
		try {
			for (EncashmentDataTable dataTable : lstEncashmentDataTables) {
				if (dataTable.getIfEditClicked().equals(true)) {
					LoyaltyMasterEncashment loyaltyMaster = new LoyaltyMasterEncashment();
					loyaltyMaster.setEncashmentId(dataTable.getEncashmentPk());
					if (dataTable.getActiveFlag() != null) {
						if (dataTable.getActiveFlag().equalsIgnoreCase(Constants.YES)) {
							loyaltyMaster.setActiveFlag(Constants.Yes);
						} else {
							loyaltyMaster.setActiveFlag(Constants.No);
						}
					}

					loyaltyMaster.setDescription(dataTable.getDescription());
					loyaltyMaster.setEffectiveDateFrom(dataTable.getEffectiveDateFrom());
					loyaltyMaster.setEffectiveDateTo(dataTable.getEffectiveDateTo());
					loyaltyMaster.setPoints(dataTable.getPoints());
					loyaltyMaster.setEquivalantAmount(dataTable.getEquivalentValue());
					loyaltyMaster.setIsActive(dataTable.getIsActive());
					loyaltyMaster.setCreatedBy(dataTable.getCreatedBy());
					loyaltyMaster.setCreatedDate(dataTable.getCreatedDate());
					loyaltyMaster.setModifiedBy(dataTable.getModifiedBy());
					loyaltyMaster.setModifiedDate(dataTable.getModifiedDate());
					loyaltyMaster.setApprovedBy(dataTable.getApprovedBy());
					loyaltyMaster.setApprovedDate(dataTable.getApprovedDate());
					loyaltyMaster.setRemarks(dataTable.getRemarks());
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(dataTable.getApplicationCountryId());
					loyaltyMaster.setApplicationCountryId(countryMaster);

					loyaltyEncashmentService.saveOrUpdate(loyaltyMaster);
				}
			}
			RequestContext.getCurrentInstance().execute("succsses.show();");
			lstEncashmentDataTables.clear();
			encashmentDataTablesNewList.clear();
			clearAllFields();
			return;
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::saveDataTableRecods");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// Date Validation
	public void checkToDateValidator() {
		if (getEffectiveDateFrom() != null) {
			if (getEffectiveDateFrom().after(getEffectiveDateTo())) {
				setEffectiveDateTo(null);
				RequestContext.getCurrentInstance().execute("datevalid.show();");
				return;
			}

		}
	}

	// From Date Validation
	public void toClearToDate() {
		if (getEffectiveDateFrom() != null) {
			setEffectiveDateTo(null);
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// Page Navigation For EncashMent
	public void pageNavigationToLoyaltyEncashment() {
		setBooRenderDataTable(false);
		setBooSaveOrExit(false);
		setBooAdd(true);
		setBooApproval(false);
		setBooRead(false);
		setBooClearPanel(false);
		setBooSubmitPanel(false);
		lstEncashmentDataTables.clear();
		encashmentDataTablesNewList.clear();
		clearAllFields();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "Encashment.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/Encashment.xhtml");
		} catch (IOException exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	// Exit
	public void exit() throws IOException {
		lstEncashmentDataTables.clear();
		encashmentDataTablesNewList.clear();
		if (sessionManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	// click on Ok button
	public void clear() {

		lstEncashmentDataTables.clear();
		encashmentDataTablesNewList.clear();
		clearAllFields();
		setBooRenderDataTable(false);
		setBooSaveOrExit(false);
		setBooAdd(true);
		setBooRead(false);
		setBooApproval(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/Encashment.xhtml");
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// Clear All Fields
	public void clearAllFields() {
		setPoints(null);
		setEquivalentValue(null);
		setEffectiveDateFrom(null);
		setEffectiveDateTo(null);
		setDescription(null);
		setActiveFlag(null);
		setEncashmentPk(null);
	}

	// Edit Button
	public void edit(EncashmentDataTable encashmentDtObj) {
		try{
			setEncashmentObj(encashmentDtObj);
			setApplicationCountryId(encashmentDtObj.getApplicationCountryId());
			setEncashmentPk(encashmentDtObj.getEncashmentPk());
			setPoints(encashmentDtObj.getPoints());
			setEquivalentValue(encashmentDtObj.getEquivalentValue());
			setDescription(encashmentDtObj.getDescription());
			setEffectiveDateFrom(encashmentDtObj.getEffectiveDateFrom());
			setEffectiveDateTo(encashmentDtObj.getEffectiveDateTo());
			if(encashmentDtObj.getActiveFlag() != null){
			if (encashmentDtObj.getActiveFlag().equalsIgnoreCase(Constants.YES)) {
				setActiveFlag(Constants.Yes);
			} else {
				setActiveFlag(Constants.No);
			}
			}
			setCreatedBy(encashmentDtObj.getCreatedBy());
			setCreatedDate(encashmentDtObj.getCreatedDate());
			setDynamicLabelForActivateDeactivate(encashmentDtObj.getDynamicLabelForActivateDeactivate());
			setIsActive(encashmentDtObj.getIsActive());
			setModifiedBy(encashmentDtObj.getModifiedBy());
			setModifiedDate(encashmentDtObj.getModifiedDate());
			setApprovedBy(encashmentDtObj.getApprovedBy());
			setApprovedDate(encashmentDtObj.getApprovedDate());
			setRemarks(encashmentDtObj.getRemarks());
			lstEncashmentDataTables.remove(encashmentDtObj);
			encashmentDataTablesNewList.remove(encashmentDtObj);
			if (lstEncashmentDataTables.size() == 0) {
				setBooRenderDataTable(false);
				setBooSaveOrExit(false);
				setBooAdd(true);
				setBooApproval(false);
				setBooRead(false);

			} else {
				setBooEditButton(true);
				setBooSubmitPanel(true);
				setBooClearPanel(true);
				setBooApproval(false);
				setBooRead(false);

			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		} 
	}

	// view Started
	public void viewAllEncashmentDetails() {
		try{
			lstEncashmentDataTables.clear();
			List<LoyaltyMasterEncashment> lstEncashments = loyaltyEncashmentService.getViewAllDetails();
			if (lstEncashments.size() > 0) {
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
			for (LoyaltyMasterEncashment loyaltyMasterEncashment : lstEncashments) {
				EncashmentDataTable dataTable = new EncashmentDataTable();
				dataTable.setApplicationCountryId(loyaltyMasterEncashment.getApplicationCountryId().getCountryId());
				dataTable.setPoints(loyaltyMasterEncashment.getPoints());
				dataTable.setEquivalentValue(loyaltyMasterEncashment.getEquivalantAmount());
				dataTable.setDescription(loyaltyMasterEncashment.getDescription());
				dataTable.setEffectiveDateFrom(loyaltyMasterEncashment.getEffectiveDateFrom());
				dataTable.setEffectiveDateTo(loyaltyMasterEncashment.getEffectiveDateTo());
				dataTable.setEncashmentPk(loyaltyMasterEncashment.getEncashmentId());
				if (loyaltyMasterEncashment.getActiveFlag() != null) {
					if (loyaltyMasterEncashment.getActiveFlag().equalsIgnoreCase(Constants.Yes)) {
						dataTable.setActiveFlag(Constants.YES);
					} else {
						dataTable.setActiveFlag(Constants.NO);
					}
				}
				dataTable.setCreatedBy(loyaltyMasterEncashment.getCreatedBy());
				dataTable.setCreatedDate(loyaltyMasterEncashment.getCreatedDate());
				dataTable.setModifiedBy(loyaltyMasterEncashment.getModifiedBy());
				dataTable.setModifiedDate(loyaltyMasterEncashment.getModifiedDate());
				dataTable.setApprovedBy(loyaltyMasterEncashment.getApprovedBy());
				dataTable.setApprovedDate(loyaltyMasterEncashment.getApprovedDate());
				dataTable.setRemarks(loyaltyMasterEncashment.getRemarks());
				dataTable.setIsActive(loyaltyMasterEncashment.getIsActive());
				dataTable.setRenderEditButton(true);
				dataTable.setBooEditButton(false);
				if(dataTable.getIsActive() != null){
				if (dataTable.getIsActive().equalsIgnoreCase(Constants.Yes)) {
					dataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				} else if (dataTable.getIsActive().equalsIgnoreCase(Constants.D)) {
					dataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				} else if (dataTable.getIsActive().equalsIgnoreCase(Constants.U) && dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null && dataTable.getApprovedBy() == null && dataTable.getApprovedDate() == null && dataTable.getRemarks() == null) {
					dataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
				} else {
					dataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				}
				}
				lstEncashmentDataTables.add(dataTable);
			}
			lstEncashmentDataTables.addAll(encashmentDataTablesNewList);
			clearAllFields();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/Encashment.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// ACTIVATE DEACTIVATE HARD DELETE STARTED
	public void checkStatus(EncashmentDataTable encashmentObj) {
		if (encashmentObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
			RequestContext.getCurrentInstance().execute("pending.show();");
			return;
		} else if (encashmentObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			lstEncashmentDataTables.remove(encashmentObj);
			encashmentDataTablesNewList.remove(encashmentObj);
		} else if (encashmentObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			encashmentObj.setRemarksCheck(true);
			setApprovedBy(encashmentObj.getApprovedBy());
			setApprovedDate(encashmentObj.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
		} else if (encashmentObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			encashmentObj.setActiveRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;
		} else if (encashmentObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && encashmentObj.getModifiedBy() == null && encashmentObj.getModifiedDate() == null && encashmentObj.getApprovedBy() == null && encashmentObj.getApprovedDate() == null
				&& encashmentObj.getRemarks() == null) {
			encashmentObj.setPermentDeleteCheck(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			return;
		}
		if (lstEncashmentDataTables.size() == 0) {
			setBooRenderDataTable(false);
			setBooSaveOrExit(false);
			setBooAdd(true);
			setBooApproval(false);
			setBooRead(false);

		}
	}

	// Hard Delete
	public void loyaltyEncashmentConformDelete() throws Exception{
		if (lstEncashmentDataTables.size() > 0) {
			for (EncashmentDataTable dataTable : lstEncashmentDataTables) {
				if (dataTable.getPermentDeleteCheck() != null) {
					if (dataTable.getPermentDeleteCheck().equals(true)) {
						deleteRecordLoyaltyEncashment(dataTable);
						lstEncashmentDataTables.remove(dataTable);
					}
				}
			}
		}
	}

	public void deleteRecordLoyaltyEncashment(EncashmentDataTable encashmentDtObj) {
		try{
			loyaltyEncashmentService.deleteRecordPermentelyFromLoyaltyEncashment(encashmentDtObj.getEncashmentPk());
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// click on DeActive link
	public void clickOkRemarks() throws Exception{
		if (getRemarks() != null && !getRemarks().equals("")) {
			for (EncashmentDataTable encashmentDataTable : lstEncashmentDataTables) {
				if (encashmentDataTable.getRemarksCheck() != null) {
					if (encashmentDataTable.getRemarksCheck().equals(true)) {
						encashmentDataTable.setRemarks(getRemarks());
						updateLoyaltyEncshment(encashmentDataTable);
						clearAllFields();
						viewAllEncashmentDetails();
						setRemarks(null);
					}
				}
			}

		} else {
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		}
	}

	public void updateLoyaltyEncshment(EncashmentDataTable dataTable) {
		try{
			loyaltyEncashmentService.upDateActiveRecordtoDeActive(dataTable.getEncashmentPk(), dataTable.getRemarks(), sessionManage.getUserName());
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// click on Active link
	public void activateRecord() throws Exception{
		if (lstEncashmentDataTables.size() > 0) {
			for (EncashmentDataTable encashmentDataTable : lstEncashmentDataTables) {
				if (encashmentDataTable.getActiveRecordCheck() != null) {
					if (encashmentDataTable.getActiveRecordCheck().equals(true)) {
						conformActiveRecordToPendingForApproval(encashmentDataTable);
					}
				}
			}
		}
	}

	public void conformActiveRecordToPendingForApproval(EncashmentDataTable dataTable) {
		try{
			loyaltyEncashmentService.DeActiveRecordToPendingForApprovalOfLoyaltyEncashment(dataTable.getEncashmentPk(), sessionManage.getUserName());
			viewAllEncashmentDetails();
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void cancelRemarks() {
		setRemarks(null);
		setBooApproval(false);
		setBooRead(false);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/Encashment.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// auto Complete Tag
	public List<String> autoComplete(String query) {
		try{
			if (query.length() > 0) {
				return loyaltyEncashmentService.toFetchAllLoyaltyPoinsList(query);
			} else {
				return null;
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;       
		}
	}

	// Item Select For Points
	public void itemSelectPopulate() {
		try{
			List<LoyaltyMasterEncashment> lstLoyaltyMasterEncashment = loyaltyEncashmentService.toCompareThepointValues(getPoints());
			if (lstLoyaltyMasterEncashment.size() > 0) {
				clearAllFields();
				RequestContext.getCurrentInstance().execute("datatable.show();");
				return;
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// Disable Submit
	public void disableSubmit() {
		setBooSubmitPanel(true);
	}

	// ACTIVATE DEACTIVATE HARD DELETE Ended

	// Approval Started
	public void loyaltyEncashmentApprovalPageNavigation() {
		setBooAdd(false);
		setBooApproval(false);
		setBooSaveOrExit(false);
		setBooRenderDataTable(true);
		setBooSubmitPanel(true);
		setBooClearPanel(true);
		setBooRead(false);
		lstEncashmentDataTables.clear();
		encashmentDataTablesNewList.clear();
		fetchRecordforLoyaltyEncashmentApprovalDataTable();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "EncashmentApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/EncashmentApproval.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// Fetch Approval Record For Data table
	public void fetchRecordforLoyaltyEncashmentApprovalDataTable() {
		try{
			lstEncashmentDataTables.clear();
			List<LoyaltyMasterEncashment> lsEncashments = loyaltyEncashmentService.toFetchAllApprovalDetails();
			if (lsEncashments.size() > 0) {
				for (LoyaltyMasterEncashment loyaltyMasterEncashment : lsEncashments) {
					EncashmentDataTable dataTable = new EncashmentDataTable();
					dataTable.setApplicationCountryId(loyaltyMasterEncashment.getApplicationCountryId().getCountryId());
					dataTable.setPoints(loyaltyMasterEncashment.getPoints());
					dataTable.setEquivalentValue(loyaltyMasterEncashment.getEquivalantAmount());
					dataTable.setDescription(loyaltyMasterEncashment.getDescription());
					dataTable.setEffectiveDateFrom(loyaltyMasterEncashment.getEffectiveDateFrom());
					dataTable.setEffectiveDateTo(loyaltyMasterEncashment.getEffectiveDateTo());
					dataTable.setEncashmentPk(loyaltyMasterEncashment.getEncashmentId());
					if (loyaltyMasterEncashment.getActiveFlag() != null) {
						if (loyaltyMasterEncashment.getActiveFlag().equalsIgnoreCase(Constants.Yes)) {
							dataTable.setActiveFlag(Constants.YES);
						} else {
							dataTable.setActiveFlag(Constants.NO);
						}
					}
					dataTable.setCreatedBy(loyaltyMasterEncashment.getCreatedBy());
					dataTable.setCreatedDate(loyaltyMasterEncashment.getCreatedDate());
					dataTable.setModifiedBy(loyaltyMasterEncashment.getModifiedBy());
					dataTable.setModifiedDate(loyaltyMasterEncashment.getModifiedDate());
					dataTable.setApprovedBy(loyaltyMasterEncashment.getApprovedBy());
					dataTable.setApprovedDate(loyaltyMasterEncashment.getApprovedDate());
					dataTable.setRemarks(loyaltyMasterEncashment.getRemarks());
					dataTable.setIsActive(loyaltyMasterEncashment.getIsActive());
					dataTable.setRenderEditButton(true);
					dataTable.setBooEditButton(false);
					if(dataTable.getIsActive() != null){
					if (dataTable.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						dataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					} else if (dataTable.getIsActive().equalsIgnoreCase(Constants.D)) {
						dataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					} else if (dataTable.getIsActive().equalsIgnoreCase(Constants.U) && dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null && dataTable.getApprovedBy() == null && dataTable.getApprovedDate() == null && dataTable.getRemarks() == null) {
						dataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					} else {
						dataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}
					}
					lstEncashmentDataTables.add(dataTable);
				}
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// multiUser
	public void approvelCheckForLoyaltyEncashmentUser(EncashmentDataTable encashmentDtObj) {
		try{
			if (!(encashmentDtObj.getModifiedBy() == null ? encashmentDtObj.getCreatedBy() : encashmentDtObj.getModifiedBy()).equalsIgnoreCase(sessionManage.getUserName())) {
				setApplicationCountryId(encashmentDtObj.getApplicationCountryId());
				setEncashmentPk(encashmentDtObj.getEncashmentPk());
				setPoints(encashmentDtObj.getPoints());
				setEquivalentValue(encashmentDtObj.getEquivalentValue());
				setDescription(encashmentDtObj.getDescription());
				setEffectiveDateFrom(encashmentDtObj.getEffectiveDateFrom());
				setEffectiveDateTo(encashmentDtObj.getEffectiveDateTo());
				if(encashmentDtObj.getActiveFlag() != null){
				if (encashmentDtObj.getActiveFlag().equalsIgnoreCase(Constants.YES)) {
					setActiveFlag(Constants.Yes);
				} else {
					setActiveFlag(Constants.No);
				}
				}
				setCreatedBy(encashmentDtObj.getCreatedBy());
				setCreatedDate(encashmentDtObj.getCreatedDate());
				setDynamicLabelForActivateDeactivate(encashmentDtObj.getDynamicLabelForActivateDeactivate());
				setIsActive(encashmentDtObj.getIsActive());
				setModifiedBy(encashmentDtObj.getModifiedBy());
				setModifiedDate(encashmentDtObj.getModifiedDate());
				setBooRenderDataTable(false);
				setBooSaveOrExit(false);
				setBooAdd(false);
				setBooApproval(true);
				setBooRead(true);
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/Encashment.xhtml");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				RequestContext.getCurrentInstance().execute("notApproved.show();");
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// approve Record
	public void loyaltyEncashmentApproveRecord() {
		try{
			String approvalMsg = loyaltyEncashmentService.checkTestKeyMasterApproveMultiUser(getEncashmentPk(), sessionManage.getUserName());
			if (approvalMsg.equalsIgnoreCase("Success")) {
				RequestContext.getCurrentInstance().execute("approve.show();");
			} else {
				RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				return;
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// click ok button for Approve
	public void clickOnOKApprove() {
		clearAllFields();
		setBooAdd(false);
		setBooRenderDataTable(false);
		setBooSaveOrExit(false);
		setBooApproval(false);
		setBooRead(false);
		try {
			fetchRecordforLoyaltyEncashmentApprovalDataTable();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/EncashmentApproval.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// Click on Ok Button
	public void clickOnOkButton() {
		lstEncashmentDataTables.clear();
		fetchRecordforLoyaltyEncashmentApprovalDataTable();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/EncashmentApproval.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// Already Approve click on Ok button
	public void loyaltyEncashmentApprovedByOhterPerson() {
		fetchRecordforLoyaltyEncashmentApprovalDataTable();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/EncashmentApproval.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// Cancel Button
	public void loyaltyEncashmentCancel() {
		fetchRecordforLoyaltyEncashmentApprovalDataTable();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/EncashmentApproval.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	/* Approval Ended */

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}



}
