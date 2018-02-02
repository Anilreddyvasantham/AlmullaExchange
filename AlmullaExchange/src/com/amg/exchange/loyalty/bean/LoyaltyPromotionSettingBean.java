package com.amg.exchange.loyalty.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.loyalty.model.LoyaltyParameterSetting;
import com.amg.exchange.loyalty.model.LoyaltyPromotionSettings;
import com.amg.exchange.loyalty.service.ILoyaltyPromotionSettingService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("loyaltyPromotionSetting")
@Scope("session")
public class LoyaltyPromotionSettingBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(LoyaltyPromotionSettingBean.class);

	private BigDecimal loyaltyPromotionId;
	private BigDecimal appCountryId;

	private BigDecimal loyaltyParameterId;
	private Date startDate;
	private Date endDate;
	private String templateCode;
	private String description;
	private String templateDescription;
	private BigDecimal branchId;
	private String branchName;

	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;

	private String releaseFlag;

	private String remarks = "";

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

	private LoyaltyPromotionSettingDataTable loyaltyPromotionSettingObj = null;

	private List<LoyaltyPromotionSettingDataTable> lstLoyaltyPromotionSettingNewDataTables = new CopyOnWriteArrayList<LoyaltyPromotionSettingDataTable>();
	private List<LoyaltyPromotionSettingDataTable> lstLoyaltyPromotionSettingDataTables = new CopyOnWriteArrayList<LoyaltyPromotionSettingDataTable>();

	private List<LoyaltyPromotionSettings> lstofLoyaltyPromotionSetting = new ArrayList<LoyaltyPromotionSettings>();

	private List<CountryBranch> lstOfCountryBranch = new ArrayList<CountryBranch>();

	private List<LoyaltyParameterSetting> lstOfLoyaltyParameter = new ArrayList<LoyaltyParameterSetting>();

	Map<BigDecimal, String> maplstOfLoyaltyParameter = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> maplstOfCountryBranch = new HashMap<BigDecimal, String>();
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	ILoyaltyPromotionSettingService<T> loyaltyPromotionSettingService;

	public List<CountryBranch> getLstOfCountryBranch() {
		return lstOfCountryBranch;
	}

	public void setLstOfCountryBranch(List<CountryBranch> lstOfCountryBranch) {
		this.lstOfCountryBranch = lstOfCountryBranch;
	}

	public String getReleaseFlag() {
		return releaseFlag;
	}

	public void setReleaseFlag(String releaseFlag) {
		this.releaseFlag = releaseFlag;
	}

	public List<LoyaltyParameterSetting> getLstOfLoyaltyParameter() {
		return lstOfLoyaltyParameter;
	}

	public void setLstOfLoyaltyParameter(List<LoyaltyParameterSetting> lstOfLoyaltyParameter) {
		this.lstOfLoyaltyParameter = lstOfLoyaltyParameter;
	}

	public LoyaltyPromotionSettingDataTable getLoyaltyPromotionSettingObj() {
		return loyaltyPromotionSettingObj;
	}

	public void setLoyaltyPromotionSettingObj(LoyaltyPromotionSettingDataTable loyaltyPromotionSettingObj) {
		this.loyaltyPromotionSettingObj = loyaltyPromotionSettingObj;
	}

	public List<LoyaltyPromotionSettingDataTable> getLstLoyaltyPromotionSettingNewDataTables() {
		return lstLoyaltyPromotionSettingNewDataTables;
	}

	public void setLstLoyaltyPromotionSettingNewDataTables(List<LoyaltyPromotionSettingDataTable> lstLoyaltyPromotionSettingNewDataTables) {
		this.lstLoyaltyPromotionSettingNewDataTables = lstLoyaltyPromotionSettingNewDataTables;
	}

	public List<LoyaltyPromotionSettingDataTable> getLstLoyaltyPromotionSettingDataTables() {
		return lstLoyaltyPromotionSettingDataTables;
	}

	public void setLstLoyaltyPromotionSettingDataTables(List<LoyaltyPromotionSettingDataTable> lstLoyaltyPromotionSettingDataTables) {
		this.lstLoyaltyPromotionSettingDataTables = lstLoyaltyPromotionSettingDataTables;
	}

	public List<LoyaltyPromotionSettings> getLstofLoyaltyPromotionSetting() {
		return lstofLoyaltyPromotionSetting;
	}

	public void setLstofLoyaltyPromotionSetting(List<LoyaltyPromotionSettings> lstofLoyaltyPromotionSetting) {
		this.lstofLoyaltyPromotionSetting = lstofLoyaltyPromotionSetting;
	}

	public ILoyaltyPromotionSettingService<T> getLoyaltyPromotionSettingService() {
		return loyaltyPromotionSettingService;
	}

	public void setLoyaltyPromotionSettingService(ILoyaltyPromotionSettingService<T> loyaltyPromotionSettingService) {
		this.loyaltyPromotionSettingService = loyaltyPromotionSettingService;
	}

	public BigDecimal getLoyaltyPromotionId() {
		return loyaltyPromotionId;
	}

	public void setLoyaltyPromotionId(BigDecimal loyaltyPromotionId) {
		this.loyaltyPromotionId = loyaltyPromotionId;
	}

	public BigDecimal getLoyaltyParameterId() {
		return loyaltyParameterId;
	}

	public void setLoyaltyParameterId(BigDecimal loyaltyParameterId) {
		this.loyaltyParameterId = loyaltyParameterId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public BigDecimal getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
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

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getBooSubmitPanel() {
		return booSubmitPanel;
	}

	public void setBooSubmitPanel(Boolean booSubmitPanel) {
		this.booSubmitPanel = booSubmitPanel;
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

	public Boolean getBooRead() {
		return booRead;
	}

	public void setBooRead(Boolean booRead) {
		this.booRead = booRead;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Map<BigDecimal, String> getMaplstOfLoyaltyParameter() {
		return maplstOfLoyaltyParameter;
	}

	public void setMaplstOfLoyaltyParameter(Map<BigDecimal, String> maplstOfLoyaltyParameter) {
		this.maplstOfLoyaltyParameter = maplstOfLoyaltyParameter;
	}

	public Map<BigDecimal, String> getMaplstOfCountryBranch() {
		return maplstOfCountryBranch;
	}

	public void setMaplstOfCountryBranch(Map<BigDecimal, String> maplstOfCountryBranch) {
		this.maplstOfCountryBranch = maplstOfCountryBranch;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void loyaltyPromotionSettingCreationNavigation() {

		lstLoyaltyPromotionSettingDataTables.clear();
		lstLoyaltyPromotionSettingNewDataTables.clear();
		setBooRenderDataTable(false);
		setBooSaveOrExit(false);
		setBooAdd(true);
		setBooApproval(false);
		setBooRead(false);
		setBooClearPanel(false);
		setBooSubmitPanel(false);
		// fetch loyaltyTemplate Code
		fetchLoyaltyPromotionTemplateCode();
		// fetch all branches from loading
		fetchAllLoyaltyAllBranches();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "loyaltypromotionsettings.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/loyaltypromotionsettings.xhtml");
			setLoyaltyParameterId(null);
			clearAll();

		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void fetchLoyaltyPromotionTemplateCode() {
		try{
			maplstOfLoyaltyParameter.clear();
			lstOfLoyaltyParameter = getLoyaltyPromotionSettingService().getLoyaltyParameterList(sessionStateManage.getCountryId());
			if(lstOfLoyaltyParameter != null){
				for (LoyaltyParameterSetting loyaltyPromotionSettings : lstOfLoyaltyParameter) {
					maplstOfLoyaltyParameter.put(loyaltyPromotionSettings.getLoyaltyParameterId(), loyaltyPromotionSettings.getTemplateCriteria());
				}
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	public void fetchAllLoyaltyAllBranches() {
		try{
			maplstOfCountryBranch.clear();
			lstOfCountryBranch = getLoyaltyPromotionSettingService().getCountryBranchList(sessionStateManage.getCountryId());
			for (CountryBranch countryBranch : lstOfCountryBranch) {
				maplstOfCountryBranch.put(countryBranch.getCountryBranchId(), countryBranch.getBranchName());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	public void duplicateChekingLoyaltyCatagoryCode() {
		if (lstLoyaltyPromotionSettingDataTables.size() > 0) {
			for (LoyaltyPromotionSettingDataTable dataTable : lstLoyaltyPromotionSettingDataTables) {
				if (dataTable.getTemplateCode().equalsIgnoreCase(getTemplateCode())) {
					clearAll();
					RequestContext.getCurrentInstance().execute("datatable.show();");
					return;
				}
			}
		}

		if (getTemplateCode() != null) {
			addRecordsToDataTable();
		}
	}

	public void addRecordsToDataTable() {
		try{
			setBooEditButton(false);
			setBooClearPanel(false);
			setBooSubmitPanel(false);
			LoyaltyPromotionSettingDataTable loyaltyCatagoryDataTable = new LoyaltyPromotionSettingDataTable();

			loyaltyCatagoryDataTable.setAppCountryId(sessionStateManage.getCountryId());
			loyaltyCatagoryDataTable.setLoyaltyPromotionId(getLoyaltyPromotionId());

			loyaltyCatagoryDataTable.setTemplateCode(getTemplateCode());
			loyaltyCatagoryDataTable.setCreatedBy(getCreatedBy());
			loyaltyCatagoryDataTable.setCreatedDate(getCreatedDate());
			loyaltyCatagoryDataTable.setLoyaltyParameterId(getLoyaltyParameterId());
			loyaltyCatagoryDataTable.setTemplateDescription(getTemplateDescription());
			loyaltyCatagoryDataTable.setDescription(maplstOfLoyaltyParameter.get(getLoyaltyParameterId()));
			loyaltyCatagoryDataTable.setBranchName(maplstOfCountryBranch.get(getBranchId()));
			loyaltyCatagoryDataTable.setStartDate(getStartDate());
			loyaltyCatagoryDataTable.setEndDate(getEndDate());
			loyaltyCatagoryDataTable.setBranchId(getBranchId());
			if (getReleaseFlag() != null) {
				if (getReleaseFlag().equalsIgnoreCase(Constants.Yes)) {
					loyaltyCatagoryDataTable.setReleaseFlag(Constants.YES);
				} else {
					loyaltyCatagoryDataTable.setReleaseFlag(Constants.NO);
				}
			}

			if (getLoyaltyPromotionId() != null) {
				if (loyaltyCatagoryDataTable.getTemplateCode().equals(loyaltyPromotionSettingObj.getTemplateCode()) && loyaltyCatagoryDataTable.getLoyaltyParameterId().equals(loyaltyPromotionSettingObj.getLoyaltyParameterId())
						&& loyaltyCatagoryDataTable.getBranchId().equals(loyaltyPromotionSettingObj.getBranchId()) && loyaltyCatagoryDataTable.getTemplateDescription().equals(loyaltyPromotionSettingObj.getTemplateDescription())
						&& loyaltyCatagoryDataTable.getStartDate().equals(loyaltyPromotionSettingObj.getStartDate()) && loyaltyCatagoryDataTable.getEndDate().equals(loyaltyPromotionSettingObj.getEndDate())
						&& loyaltyCatagoryDataTable.getReleaseFlag().equals(loyaltyPromotionSettingObj.getReleaseFlag())) {
					loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					loyaltyCatagoryDataTable.setIsActive(getIsActive());
					loyaltyCatagoryDataTable.setModifiedBy(getModifiedBy());
					loyaltyCatagoryDataTable.setModifiedDate(getModifiedDate());
					loyaltyCatagoryDataTable.setApprovedBy(getApprovedBy());
					loyaltyCatagoryDataTable.setApprovedDate(getApprovedDate());
					loyaltyCatagoryDataTable.setRemarks(getRemarks());
					loyaltyCatagoryDataTable.setLoyaltyPromotionId(getLoyaltyPromotionId());
				} else {
					loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					loyaltyCatagoryDataTable.setIsActive(Constants.U);
					loyaltyCatagoryDataTable.setModifiedBy(sessionStateManage.getUserName());
					loyaltyCatagoryDataTable.setModifiedDate(new Date());
					loyaltyCatagoryDataTable.setApprovedBy(null);
					loyaltyCatagoryDataTable.setApprovedDate(null);
					loyaltyCatagoryDataTable.setRemarks(null);
					loyaltyCatagoryDataTable.setLoyaltyPromotionId(getLoyaltyPromotionId());
					loyaltyCatagoryDataTable.setIfEditClicked(true);
				}
			} else {
				loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				loyaltyCatagoryDataTable.setIsActive(Constants.U);
				loyaltyCatagoryDataTable.setCreatedBy(sessionStateManage.getUserName());
				loyaltyCatagoryDataTable.setCreatedDate(new Date());
				loyaltyCatagoryDataTable.setIfEditClicked(true);
			}
			lstLoyaltyPromotionSettingDataTables.add(loyaltyCatagoryDataTable);

			if (getLoyaltyPromotionId() == null) {
				lstLoyaltyPromotionSettingNewDataTables.add(loyaltyCatagoryDataTable);
			}

			clearAll();
			setBooRenderDataTable(true);
			setBooSaveOrExit(true);
			setBooApproval(false);
			setBooRead(false);
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::addRecordsToDataTable");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void saveAllLoyaltyCatagoryMethod() {
		try {
			for (LoyaltyPromotionSettingDataTable loyaltyCatagoryDataTable : lstLoyaltyPromotionSettingDataTables) {
				if (loyaltyCatagoryDataTable.getIfEditClicked().equals(true)) {
					LoyaltyPromotionSettings loyaltyCatagoryMaster = new LoyaltyPromotionSettings();
					loyaltyCatagoryMaster.setLoyaltyPromotionId(loyaltyCatagoryDataTable.getLoyaltyPromotionId());

					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(loyaltyCatagoryDataTable.getAppCountryId());
					loyaltyCatagoryMaster.setApplicationCountryId(countryMaster);

					loyaltyCatagoryMaster.setTemplateCode(loyaltyCatagoryDataTable.getTemplateCode());
					loyaltyCatagoryMaster.setTemplateDescription(loyaltyCatagoryDataTable.getTemplateDescription());
					// Loyalty Promotion
					LoyaltyParameterSetting loyaltyParameterSetting = new LoyaltyParameterSetting();
					loyaltyParameterSetting.setLoyaltyParameterId(loyaltyCatagoryDataTable.getLoyaltyParameterId());
					loyaltyCatagoryMaster.setLoyaltyParameterId(loyaltyParameterSetting);

					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(loyaltyCatagoryDataTable.getBranchId());
					loyaltyCatagoryMaster.setBranchId(countryBranch);

					loyaltyCatagoryMaster.setStartDate(loyaltyCatagoryDataTable.getStartDate());
					loyaltyCatagoryMaster.setEndDate(loyaltyCatagoryDataTable.getEndDate());
					if (loyaltyCatagoryDataTable.getReleaseFlag().equalsIgnoreCase(Constants.YES)) {
						loyaltyCatagoryMaster.setReleaseFlag(Constants.Yes);
					} else {
						loyaltyCatagoryMaster.setReleaseFlag(Constants.No);
					}
					loyaltyCatagoryMaster.setCreatedBy(loyaltyCatagoryDataTable.getCreatedBy());
					loyaltyCatagoryMaster.setCreatedDate(loyaltyCatagoryDataTable.getCreatedDate());
					loyaltyCatagoryMaster.setModifiedBy(loyaltyCatagoryDataTable.getModifiedBy());
					loyaltyCatagoryMaster.setModifiedDate(loyaltyCatagoryDataTable.getModifiedDate());
					loyaltyCatagoryMaster.setApprovedBy(loyaltyCatagoryDataTable.getApprovedBy());
					loyaltyCatagoryMaster.setApprovedDate(loyaltyCatagoryDataTable.getApprovedDate());
					loyaltyCatagoryMaster.setIsActive(loyaltyCatagoryDataTable.getIsActive());
					loyaltyCatagoryMaster.setRemarks(loyaltyCatagoryDataTable.getRemarks());
					getLoyaltyPromotionSettingService().saveAndUpdateLoyaltyPromotionSettings(loyaltyCatagoryMaster);
				}
			}
			lstLoyaltyPromotionSettingDataTables.clear();
			lstLoyaltyPromotionSettingNewDataTables.clear();
			RequestContext.getCurrentInstance().execute("complete.show();");
			clearAll();
			setLoyaltyParameterId(null);
			setBooRenderDataTable(false);
			setBooSaveOrExit(false);
			setBooAdd(true);
			setBooApproval(false);
			setBooRead(false);

		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::saveAllLoyaltyCatagoryMethod");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	// click on ok button for saved
	public void clickOnOKSave() {
		lstLoyaltyPromotionSettingDataTables.clear();
		lstLoyaltyPromotionSettingNewDataTables.clear();
		clearAll();
		setBooRenderDataTable(false);
		setBooSaveOrExit(false);
		setBooAdd(true);
		setBooRead(false);
		setBooApproval(false);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/loyaltypromotionsettings.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void viewLoyaltyCatagoryMethod() {

		try{
			lstLoyaltyPromotionSettingDataTables.clear();
			// clearAll();
			if(getLoyaltyParameterId() != null){
				lstofLoyaltyPromotionSetting = getLoyaltyPromotionSettingService().displayAllLoyaltyPromotionSettingToView(getLoyaltyParameterId());

				if (lstofLoyaltyPromotionSetting.size() > 0) {
					setBooRenderDataTable(true);
					setBooSaveOrExit(true);
					setBooApproval(false);
					setBooRead(false);
				} else if (lstLoyaltyPromotionSettingNewDataTables.size() !=0) {
					setBooRenderDataTable(true);
					setBooSaveOrExit(true);
					setBooApproval(false);
					setBooRead(false);
				}else {
					setBooRenderDataTable(false);
					setBooSaveOrExit(false);
					setBooApproval(false);
					setBooRead(false);
					RequestContext.getCurrentInstance().execute("noRecords.show();");
					return;
				}

				for (LoyaltyPromotionSettings loyaltyCatagoryMaster : lstofLoyaltyPromotionSetting) {
					LoyaltyPromotionSettingDataTable loyaltyCatagoryDataTable = new LoyaltyPromotionSettingDataTable();

					loyaltyCatagoryDataTable.setLoyaltyPromotionId(loyaltyCatagoryMaster.getLoyaltyPromotionId());
					loyaltyCatagoryDataTable.setTemplateCode(loyaltyCatagoryMaster.getTemplateCode());
					loyaltyCatagoryDataTable.setTemplateDescription(loyaltyCatagoryMaster.getTemplateDescription());


					loyaltyCatagoryDataTable.setBranchId(loyaltyCatagoryMaster.getBranchId().getCountryBranchId());
					loyaltyCatagoryDataTable.setLoyaltyParameterId(loyaltyCatagoryMaster.getLoyaltyParameterId().getLoyaltyParameterId());
					loyaltyCatagoryDataTable.setStartDate(loyaltyCatagoryMaster.getStartDate());
					loyaltyCatagoryDataTable.setEndDate(loyaltyCatagoryMaster.getEndDate());
					loyaltyCatagoryDataTable.setDescription(maplstOfLoyaltyParameter.get(loyaltyCatagoryMaster.getLoyaltyParameterId().getLoyaltyParameterId()));
					loyaltyCatagoryDataTable.setBranchName(maplstOfCountryBranch.get(loyaltyCatagoryMaster.getBranchId().getCountryBranchId()));
					if(loyaltyCatagoryMaster.getReleaseFlag() != null){
					if (loyaltyCatagoryMaster.getReleaseFlag().equalsIgnoreCase(Constants.Yes)) {
						loyaltyCatagoryDataTable.setReleaseFlag(Constants.YES);
					} else {
						loyaltyCatagoryDataTable.setReleaseFlag(Constants.NO);
					}
					}
					/*
					 * loyaltyCatagoryDataTable.setReleaseFlag(
					 * loyaltyCatagoryMaster.getReleaseFlag());
					 */

					loyaltyCatagoryDataTable.setAppCountryId(loyaltyCatagoryMaster.getApplicationCountryId().getCountryId());

					loyaltyCatagoryDataTable.setModifiedBy(loyaltyCatagoryMaster.getModifiedBy());
					loyaltyCatagoryDataTable.setModifiedDate(loyaltyCatagoryMaster.getModifiedDate());
					loyaltyCatagoryDataTable.setCreatedBy(loyaltyCatagoryMaster.getCreatedBy());
					loyaltyCatagoryDataTable.setCreatedDate(loyaltyCatagoryMaster.getCreatedDate());
					loyaltyCatagoryDataTable.setApprovedBy(loyaltyCatagoryMaster.getApprovedBy());
					loyaltyCatagoryDataTable.setApprovedDate(loyaltyCatagoryMaster.getApprovedDate());
					loyaltyCatagoryDataTable.setRemarks(loyaltyCatagoryMaster.getRemarks());
					loyaltyCatagoryDataTable.setIsActive(loyaltyCatagoryMaster.getIsActive());
					loyaltyCatagoryDataTable.setRenderEditButton(true);
					loyaltyCatagoryDataTable.setBooEditButton(false);
					if(loyaltyCatagoryDataTable.getIsActive() != null){
					if (loyaltyCatagoryDataTable.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					} else if (loyaltyCatagoryDataTable.getIsActive().equalsIgnoreCase(Constants.D)) {
						loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					} else if (loyaltyCatagoryDataTable.getIsActive().equalsIgnoreCase(Constants.U) && loyaltyCatagoryDataTable.getModifiedBy() == null && loyaltyCatagoryDataTable.getModifiedDate() == null && loyaltyCatagoryDataTable.getApprovedBy() == null
							&& loyaltyCatagoryDataTable.getApprovedDate() == null && loyaltyCatagoryDataTable.getRemarks() == null) {
						loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					} else {

						loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}
					}
					lstLoyaltyPromotionSettingDataTables.add(loyaltyCatagoryDataTable);
				}

				lstLoyaltyPromotionSettingDataTables.addAll(lstLoyaltyPromotionSettingNewDataTables);
				clearAll();

				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/loyaltypromotionsettings.xhtml");

				} catch (Exception e) {
					e.printStackTrace();
				}     
			}else{
				RequestContext.getCurrentInstance().execute("selectOne.show();");
				setBooRenderDataTable(false);
				setBooSaveOrExit(false);
				return;
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::viewLoyaltyCatagoryMethod");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	public void editLoyaltyCatagoryMethod(LoyaltyPromotionSettingDataTable loyaltyCatagoryDataTable) {
		try{
			setLoyaltyPromotionSettingObj(loyaltyCatagoryDataTable);
			setLoyaltyPromotionId(loyaltyCatagoryDataTable.getLoyaltyPromotionId());
			setAppCountryId(loyaltyCatagoryDataTable.getAppCountryId());
			setTemplateCode(loyaltyCatagoryDataTable.getTemplateCode());
			setTemplateDescription(loyaltyCatagoryDataTable.getTemplateDescription());
			// getLoyaltyParameterList();

			setLoyaltyParameterId(loyaltyCatagoryDataTable.getLoyaltyParameterId());
			// getcountryBranchList();
			setBranchId(loyaltyCatagoryDataTable.getBranchId());

			setStartDate(loyaltyCatagoryDataTable.getStartDate());
			setEndDate(loyaltyCatagoryDataTable.getEndDate());
			if(loyaltyCatagoryDataTable.getReleaseFlag() != null){
			if (loyaltyCatagoryDataTable.getReleaseFlag().equalsIgnoreCase(Constants.YES)) {
				setReleaseFlag(Constants.Yes);
			} else {
				setReleaseFlag(Constants.No);
			}
			}
			setCreatedBy(loyaltyCatagoryDataTable.getCreatedBy());
			setCreatedDate(loyaltyCatagoryDataTable.getCreatedDate());
			setModifiedBy(loyaltyCatagoryDataTable.getModifiedBy());
			setModifiedDate(loyaltyCatagoryDataTable.getModifiedDate());
			setApprovedBy(loyaltyCatagoryDataTable.getApprovedBy());
			setApprovedDate(loyaltyCatagoryDataTable.getApprovedDate());
			setIsActive(loyaltyCatagoryDataTable.getIsActive());
			setRemarks(loyaltyCatagoryDataTable.getRemarks());

			setDynamicLabelForActivateDeactivate(loyaltyCatagoryDataTable.getDynamicLabelForActivateDeactivate());

			lstLoyaltyPromotionSettingDataTables.remove(loyaltyCatagoryDataTable);
			lstLoyaltyPromotionSettingNewDataTables.remove(loyaltyCatagoryDataTable);
			if (lstLoyaltyPromotionSettingDataTables.size() == 0) {
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

	public void clearAll() {

		setAppCountryId(null);
		setTemplateCode(null);
		setTemplateDescription(null);
		setStartDate(null);
		setEndDate(null);
		setBranchId(null);
		//setLoyaltyParameterId(null);
		setLoyaltyPromotionId(null);
		setReleaseFlag(null);
		setRemarks(null);

	}

	public void checkStatusType(LoyaltyPromotionSettingDataTable dataTable) {
		if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
			RequestContext.getCurrentInstance().execute("pending.show();");
			return;
		} else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			lstLoyaltyPromotionSettingDataTables.remove(dataTable);
			lstLoyaltyPromotionSettingNewDataTables.remove(dataTable);
		} else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			dataTable.setRemarksCheck(true);
			setApprovedBy(dataTable.getApprovedBy());
			setApprovedDate(dataTable.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
			return;
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
		if (lstLoyaltyPromotionSettingDataTables.size() == 0) {
			setBooRenderDataTable(false);
			setBooSaveOrExit(false);
			setBooAdd(true);
			setBooApproval(false);
			setBooRead(false);

		}
	}

	public void confirmPermanentDelete() throws Exception{
		if (lstLoyaltyPromotionSettingDataTables.size() > 0) {
			for (LoyaltyPromotionSettingDataTable loyaltyCatagoryDataTable : lstLoyaltyPromotionSettingDataTables) {
				if (loyaltyCatagoryDataTable.getPermentDeleteCheck() != null) {
					if (loyaltyCatagoryDataTable.getPermentDeleteCheck().equals(true)) {
						delete(loyaltyCatagoryDataTable);
						lstLoyaltyPromotionSettingDataTables.remove(loyaltyCatagoryDataTable);
					}
				}
			}
		}

	}

	public void conformToDeActivteCompliantType(LoyaltyPromotionSettingDataTable loyaltyCatagoryDataTable) {
		try{
			loyaltyPromotionSettingService.activateLoyaltyPromotionSettings(loyaltyCatagoryDataTable.getLoyaltyPromotionId(), sessionStateManage.getUserName());
			viewLoyaltyCatagoryMethod();
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void activateRecord() throws Exception{

		if (lstLoyaltyPromotionSettingDataTables.size() > 0) {
			for (LoyaltyPromotionSettingDataTable loyaltyCatagoryDataTable : lstLoyaltyPromotionSettingDataTables) {
				if (loyaltyCatagoryDataTable.getActiveRecordCheck() != null) {
					if (loyaltyCatagoryDataTable.getActiveRecordCheck().equals(true)) {
						conformToDeActivteCompliantType(loyaltyCatagoryDataTable);
					}
				}
			}
		}

	}

	public void remarkSelectedRecord() throws Exception{

		if (getRemarks() != null && !getRemarks().equals("")) {
			for (LoyaltyPromotionSettingDataTable loyaltyPromotionSettingDataTable : lstLoyaltyPromotionSettingDataTables) {
				if (loyaltyPromotionSettingDataTable.getRemarksCheck() != null) {
					if (loyaltyPromotionSettingDataTable.getRemarksCheck().equals(true)) {
						loyaltyPromotionSettingDataTable.setRemarks(getRemarks());
						updateLoyaltyPromotionSetting(loyaltyPromotionSettingDataTable);
						clearAll();
						viewLoyaltyCatagoryMethod();
						setRemarks(null);
					}
				}
			}

		} else {
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		}
	}

	public void delete(LoyaltyPromotionSettingDataTable loyaltyCatagoryDataTable) {
		try{
			loyaltyPromotionSettingService.deleteLoyaltyPromotionSettings(loyaltyCatagoryDataTable.getLoyaltyPromotionId());
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void updateLoyaltyPromotionSetting(LoyaltyPromotionSettingDataTable dataTable) {
		try{
			loyaltyPromotionSettingService.upDateActiveRecordtoDeActive(dataTable.getLoyaltyPromotionId(), dataTable.getRemarks(), sessionStateManage.getUserName());
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
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/loyaltypromotionsettings.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void exit() {
		log.info("Enter in exit method ");

		try {
			lstLoyaltyPromotionSettingDataTables.clear();
			lstLoyaltyPromotionSettingNewDataTables.clear();
			clearAll();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

		log.info("Exit from exit method ");
	}

	public void disableSubmit() {
		setBooSubmitPanel(true);
	}

	// Negative Value checking
	public void negativeValueChecking(FacesContext context, UIComponent component, Object value) {
		BigDecimal negativeValue = (BigDecimal) value;

		if (negativeValue.intValue() <= 0) {
			FacesMessage msg = new FacesMessage("Please Enter Only Positive Values, Zero Not Allowed", "Please Enter Only Positive Values, Zero Not Allowed");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	public void checkToDateValidator() {

		if (getStartDate() != null) {
			if (getStartDate().after(getEndDate())) {
				setEndDate(null);
				RequestContext.getCurrentInstance().execute("datevalid.show();");
			}

		}

	}

	// auto complete
	public List<String> autoComplete(String query) {
		try{
			if (query.length() > 0) {
				return loyaltyPromotionSettingService.toFetchAllLoyaltyTemplateCode(query);
			} else {
				return null;
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			return null;
		}
	}

	// populate
	public void populate() {
		try{
			List<LoyaltyPromotionSettings> lstLoyaltyPromotionSettings = loyaltyPromotionSettingService.toCompareTheTemplateCode(getTemplateCode());
			if (lstLoyaltyPromotionSettings.size() > 0) {
				clearAll();
				RequestContext.getCurrentInstance().execute("datatable.show();");
				return;
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
		}
	}

	// Approval Started
	public void pageNavigationForLoyaltyPromotionSettingApproval() {
		setBooAdd(false);
		setBooApproval(false);
		setBooSaveOrExit(false);
		setBooRenderDataTable(true);
		setBooSubmitPanel(true);
		setBooClearPanel(true);
		setBooRead(false);
		// fetch loyaltyTemplate Code
		fetchLoyaltyPromotionTemplateCode();
		// fetch all branches from loading
		fetchAllLoyaltyAllBranches();
		fetchRecordforLoyaltyPromotionForApprovalDataTable();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "LoyaltyPromotionSettingApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyPromotionSettingApproval.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}
	public void fetchRecordforLoyaltyPromotionForApprovalDataTable(){
		try{
			lstLoyaltyPromotionSettingDataTables.clear();
			clearAll();
			List<LoyaltyPromotionSettings> lstofLoyaltyPromotionSetting = loyaltyPromotionSettingService.displayAllLoyaltyPromotionSettingToApprovalDtataTable();
			if (lstofLoyaltyPromotionSetting.size() > 0) {
				for (LoyaltyPromotionSettings loyaltyCatagoryMaster : lstofLoyaltyPromotionSetting) {
					LoyaltyPromotionSettingDataTable loyaltyCatagoryDataTable = new LoyaltyPromotionSettingDataTable();

					loyaltyCatagoryDataTable.setLoyaltyPromotionId(loyaltyCatagoryMaster.getLoyaltyPromotionId());
					loyaltyCatagoryDataTable.setTemplateCode(loyaltyCatagoryMaster.getTemplateCode());
					loyaltyCatagoryDataTable.setTemplateDescription(loyaltyCatagoryMaster.getTemplateDescription());
					loyaltyCatagoryDataTable.setBranchId(loyaltyCatagoryMaster.getBranchId().getCountryBranchId());
					loyaltyCatagoryDataTable.setLoyaltyParameterId(loyaltyCatagoryMaster.getLoyaltyParameterId().getLoyaltyParameterId());
					loyaltyCatagoryDataTable.setStartDate(loyaltyCatagoryMaster.getStartDate());
					loyaltyCatagoryDataTable.setEndDate(loyaltyCatagoryMaster.getEndDate());
					loyaltyCatagoryDataTable.setDescription(maplstOfLoyaltyParameter.get(loyaltyCatagoryMaster.getLoyaltyParameterId().getLoyaltyParameterId()));
					loyaltyCatagoryDataTable.setBranchName(maplstOfCountryBranch.get(loyaltyCatagoryMaster.getBranchId().getCountryBranchId()));
					if(loyaltyCatagoryMaster.getReleaseFlag() != null){
					if (loyaltyCatagoryMaster.getReleaseFlag().equalsIgnoreCase(Constants.Yes)) {
						loyaltyCatagoryDataTable.setReleaseFlag(Constants.YES);
					} else {
						loyaltyCatagoryDataTable.setReleaseFlag(Constants.NO);
					}
					}
					/*
					 * loyaltyCatagoryDataTable.setReleaseFlag(
					 * loyaltyCatagoryMaster.getReleaseFlag());
					 */

					loyaltyCatagoryDataTable.setAppCountryId(loyaltyCatagoryMaster.getApplicationCountryId().getCountryId());

					loyaltyCatagoryDataTable.setModifiedBy(loyaltyCatagoryMaster.getModifiedBy());
					loyaltyCatagoryDataTable.setModifiedDate(loyaltyCatagoryMaster.getModifiedDate());
					loyaltyCatagoryDataTable.setCreatedBy(loyaltyCatagoryMaster.getCreatedBy());
					loyaltyCatagoryDataTable.setCreatedDate(loyaltyCatagoryMaster.getCreatedDate());
					loyaltyCatagoryDataTable.setApprovedBy(loyaltyCatagoryMaster.getApprovedBy());
					loyaltyCatagoryDataTable.setApprovedDate(loyaltyCatagoryMaster.getApprovedDate());
					loyaltyCatagoryDataTable.setRemarks(loyaltyCatagoryMaster.getRemarks());
					loyaltyCatagoryDataTable.setIsActive(loyaltyCatagoryMaster.getIsActive());
					loyaltyCatagoryDataTable.setRenderEditButton(true);
					loyaltyCatagoryDataTable.setBooEditButton(false);
					if(loyaltyCatagoryDataTable.getIsActive() != null){
					if (loyaltyCatagoryDataTable.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					} else if (loyaltyCatagoryDataTable.getIsActive().equalsIgnoreCase(Constants.D)) {
						loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					} else if (loyaltyCatagoryDataTable.getIsActive().equalsIgnoreCase(Constants.U) && loyaltyCatagoryDataTable.getModifiedBy() == null && loyaltyCatagoryDataTable.getModifiedDate() == null && loyaltyCatagoryDataTable.getApprovedBy() == null
							&& loyaltyCatagoryDataTable.getApprovedDate() == null && loyaltyCatagoryDataTable.getRemarks() == null) {
						loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					} else {

						loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}
					}
					lstLoyaltyPromotionSettingDataTables.add(loyaltyCatagoryDataTable);
				}   
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}
	public void approvalCheckForLoyaltyPromotionSettingMasterUser(LoyaltyPromotionSettingDataTable dataTable){
		try{
			if (!(dataTable.getModifiedBy() == null ? dataTable.getCreatedBy() : dataTable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())) {
				setLoyaltyPromotionId(dataTable.getLoyaltyPromotionId());
				setAppCountryId(dataTable.getAppCountryId());
				setTemplateCode(dataTable.getTemplateCode());
				setTemplateDescription(dataTable.getTemplateDescription());
				setLoyaltyParameterId(dataTable.getLoyaltyParameterId());
				setBranchId(dataTable.getBranchId());
				setStartDate(dataTable.getStartDate());
				setEndDate(dataTable.getEndDate());
				if(dataTable.getReleaseFlag() != null){
				if (dataTable.getReleaseFlag().equalsIgnoreCase(Constants.YES)) {
					setReleaseFlag(Constants.Yes);
				} else {
					setReleaseFlag(Constants.No);
				}
				}
				setCreatedBy(dataTable.getCreatedBy());
				setCreatedDate(dataTable.getCreatedDate());
				setModifiedBy(dataTable.getModifiedBy());
				setModifiedDate(dataTable.getModifiedDate());
				setIsActive(dataTable.getIsActive());
				setDynamicLabelForActivateDeactivate(dataTable.getDynamicLabelForActivateDeactivate());
				setBooRenderDataTable(false);
				setBooSaveOrExit(false);
				setBooAdd(false);
				setBooApproval(true);
				setBooRead(true);
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/loyaltypromotionsettings.xhtml");
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
	public void loyaltyPromotionSettingApproveRecord() {
		try{
			String approvalMsg = loyaltyPromotionSettingService.checkLoyaltyPromotionApproveMultiUser(getLoyaltyPromotionId(), sessionStateManage.getUserName());
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
		clearAll();
		setBooAdd(false);
		setBooRenderDataTable(false);
		setBooSaveOrExit(false);
		setBooApproval(false);
		setBooRead(false);
		try {
			fetchRecordforLoyaltyPromotionForApprovalDataTable();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyPromotionSettingApproval.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void clickOnOkButton() {
		lstLoyaltyPromotionSettingDataTables.clear();
		fetchRecordforLoyaltyPromotionForApprovalDataTable();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyPromotionSettingApproval.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void loyaltyPromotionApprovedByOhterPerson() {
		fetchRecordforLoyaltyPromotionForApprovalDataTable();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyPromotionSettingApproval.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void loyaltyPromotionSettingCancel() {
		fetchRecordforLoyaltyPromotionForApprovalDataTable();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyPromotionSettingApproval.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}



	/* Approval Ended */
}
