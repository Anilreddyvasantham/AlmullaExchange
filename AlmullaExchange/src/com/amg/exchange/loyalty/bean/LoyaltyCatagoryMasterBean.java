package com.amg.exchange.loyalty.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.loyalty.model.LoyaltyCatagoryMaster;
import com.amg.exchange.loyalty.model.LoyaltyCatergoryMasterDesc;
import com.amg.exchange.loyalty.model.LoyaltyTypeDesc;
import com.amg.exchange.loyalty.service.ILoyaltyCatagoryService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("loyaltyCatagoryMaster")
@Scope("session")
public class LoyaltyCatagoryMasterBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(LoyaltyCatagoryMasterBean.class);

	private BigDecimal loyaltyCatagoryId;
	private BigDecimal localLanguageId;
	private BigDecimal englishLanguageId;
	private BigDecimal engLoyaltyCatagoryDescId;
	private BigDecimal localLoyaltyCatagoryDescId;
	private BigDecimal appCountryId;
	private BigDecimal loyaltyCatagoryDescId;

	private String fullEngLoyaltyCatagoryDesc;
	private String fullLocalLoyaltyCatagoryDesc;
	private String shortEngLoyaltyCatagoryDesc;
	private String shortLocalLoyaltyCatagoryDesc;

	private String categoryCode;
	private String fieldName;
	private String validOption;
	//private String validOptionName;
	private String categoryType;

	private BigDecimal categoryTypeId;

	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks = "";

	private String dynamicLabelForActivateDeactivate;

	private Boolean booEdit;
	private Boolean isdisable;
	private Boolean btnClear;
	private Boolean disableSubmitButton;
	private Boolean booSubmitPanel;

	private Boolean booLoyaltyCatagoryDetail;
	private Boolean booLoyaltyCatagoryDataTable;

	private LoyaltyCatagoryMasterDataTable loyaltyCatagoryDataTable = null;
	private String errorMessage;

	private List<LoyaltyCatagoryMasterDataTable> lstLoyaltyCatagoryNewDataTables = new CopyOnWriteArrayList<LoyaltyCatagoryMasterDataTable>();
	private List<LoyaltyCatagoryMasterDataTable> lstLoyaltyCatagoryDataTables = new CopyOnWriteArrayList<LoyaltyCatagoryMasterDataTable>();

	private List<LoyaltyCatagoryMaster> lstofLoyaltyCatagory = new ArrayList<LoyaltyCatagoryMaster>();
	private List<LoyaltyCatergoryMasterDesc> lstofLoyaltyCatagoryDesc = new ArrayList<LoyaltyCatergoryMasterDesc>();

	private List<LoyaltyTypeDesc> lstOfLoyaltyTypeDescs = new ArrayList<LoyaltyTypeDesc>();

	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	ILoyaltyCatagoryService<T> loyaltyCatagoryService;

	public BigDecimal getLoyaltyCatagoryId() {
		return loyaltyCatagoryId;
	}

	public void setLoyaltyCatagoryId(BigDecimal loyaltyCatagoryId) {
		this.loyaltyCatagoryId = loyaltyCatagoryId;
	}

	public BigDecimal getEngLoyaltyCatagoryDescId() {
		return engLoyaltyCatagoryDescId;
	}

	public void setEngLoyaltyCatagoryDescId(BigDecimal engLoyaltyCatagoryDescId) {
		this.engLoyaltyCatagoryDescId = engLoyaltyCatagoryDescId;
	}

	public BigDecimal getLocalLoyaltyCatagoryDescId() {
		return localLoyaltyCatagoryDescId;
	}

	public void setLocalLoyaltyCatagoryDescId(BigDecimal localLoyaltyCatagoryDescId) {
		this.localLoyaltyCatagoryDescId = localLoyaltyCatagoryDescId;
	}

	public String getFullEngLoyaltyCatagoryDesc() {
		return fullEngLoyaltyCatagoryDesc;
	}

	public void setFullEngLoyaltyCatagoryDesc(String fullEngLoyaltyCatagoryDesc) {
		this.fullEngLoyaltyCatagoryDesc = fullEngLoyaltyCatagoryDesc;
	}

	public String getFullLocalLoyaltyCatagoryDesc() {
		return fullLocalLoyaltyCatagoryDesc;
	}

	public void setFullLocalLoyaltyCatagoryDesc(String fullLocalLoyaltyCatagoryDesc) {
		this.fullLocalLoyaltyCatagoryDesc = fullLocalLoyaltyCatagoryDesc;
	}

	public String getShortEngLoyaltyCatagoryDesc() {
		return shortEngLoyaltyCatagoryDesc;
	}

	public void setShortEngLoyaltyCatagoryDesc(String shortEngLoyaltyCatagoryDesc) {
		this.shortEngLoyaltyCatagoryDesc = shortEngLoyaltyCatagoryDesc;
	}

	public String getShortLocalLoyaltyCatagoryDesc() {
		return shortLocalLoyaltyCatagoryDesc;
	}

	public void setShortLocalLoyaltyCatagoryDesc(String shortLocalLoyaltyCatagoryDesc) {
		this.shortLocalLoyaltyCatagoryDesc = shortLocalLoyaltyCatagoryDesc;
	}

	public BigDecimal getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public BigDecimal getLoyaltyCatagoryDescId() {
		return loyaltyCatagoryDescId;
	}

	public void setLoyaltyCatagoryDescId(BigDecimal loyaltyCatagoryDescId) {
		this.loyaltyCatagoryDescId = loyaltyCatagoryDescId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}


	public BigDecimal getCategoryTypeId() {
		return categoryTypeId;
	}

	public void setCategoryTypeId(BigDecimal categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
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

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getValidOption() {
		return validOption;
	}

	public void setValidOption(String validOption) {
		this.validOption = validOption;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}


	public List<LoyaltyTypeDesc> getLstOfLoyaltyTypeDescs() {
		return lstOfLoyaltyTypeDescs;
	}

	public void setLstOfLoyaltyTypeDescs(List<LoyaltyTypeDesc> lstOfLoyaltyTypeDescs) {
		this.lstOfLoyaltyTypeDescs = lstOfLoyaltyTypeDescs;
	}

	public Boolean getBooEdit() {
		return booEdit;
	}

	public void setBooEdit(Boolean booEdit) {
		this.booEdit = booEdit;
	}

	public Boolean getIsdisable() {
		return isdisable;
	}

	public void setIsdisable(Boolean isdisable) {
		this.isdisable = isdisable;
	}

	public Boolean getBtnClear() {
		return btnClear;
	}

	public void setBtnClear(Boolean btnClear) {
		this.btnClear = btnClear;
	}

	public Boolean getDisableSubmitButton() {
		return disableSubmitButton;
	}

	public void setDisableSubmitButton(Boolean disableSubmitButton) {
		this.disableSubmitButton = disableSubmitButton;
	}

	public Boolean getBooLoyaltyCatagoryDetail() {
		return booLoyaltyCatagoryDetail;
	}

	public void setBooLoyaltyCatagoryDetail(Boolean booLoyaltyCatagoryDetail) {
		this.booLoyaltyCatagoryDetail = booLoyaltyCatagoryDetail;
	}

	public Boolean getBooLoyaltyCatagoryDataTable() {
		return booLoyaltyCatagoryDataTable;
	}

	public void setBooLoyaltyCatagoryDataTable(Boolean booLoyaltyCatagoryDataTable) {
		this.booLoyaltyCatagoryDataTable = booLoyaltyCatagoryDataTable;
	}

	public BigDecimal getLocalLanguageId() {
		return localLanguageId;
	}

	public void setLocalLanguageId(BigDecimal localLanguageId) {
		this.localLanguageId = localLanguageId;
	}

	public BigDecimal getEnglishLanguageId() {
		return englishLanguageId;
	}

	public void setEnglishLanguageId(BigDecimal englishLanguageId) {
		this.englishLanguageId = englishLanguageId;
	}

	public List<LoyaltyCatagoryMasterDataTable> getLstLoyaltyCatagoryDataTables() {
		return lstLoyaltyCatagoryDataTables;
	}

	public void setLstLoyaltyCatagoryDataTables(List<LoyaltyCatagoryMasterDataTable> lstLoyaltyCatagoryDataTables) {
		this.lstLoyaltyCatagoryDataTables = lstLoyaltyCatagoryDataTables;
	}

	public ILoyaltyCatagoryService<T> getLoyaltyCatagoryService() {
		return loyaltyCatagoryService;
	}

	public void setLoyaltyCatagoryService(ILoyaltyCatagoryService<T> loyaltyCatagoryService) {
		this.loyaltyCatagoryService = loyaltyCatagoryService;
	}

	public Boolean getBooSubmitPanel() {
		return booSubmitPanel;
	}

	public void setBooSubmitPanel(Boolean booSubmitPanel) {
		this.booSubmitPanel = booSubmitPanel;
	}

	public List<LoyaltyCatagoryMasterDataTable> getLstLoyaltyCatagoryNewDataTables() {
		return lstLoyaltyCatagoryNewDataTables;
	}

	public void setLstLoyaltyCatagoryNewDataTables(List<LoyaltyCatagoryMasterDataTable> lstLoyaltyCatagoryNewDataTables) {
		this.lstLoyaltyCatagoryNewDataTables = lstLoyaltyCatagoryNewDataTables;
	}

	public LoyaltyCatagoryMasterDataTable getLoyaltyCatagoryDataTable() {
		return loyaltyCatagoryDataTable;
	}

	public void setLoyaltyCatagoryDataTable(LoyaltyCatagoryMasterDataTable loyaltyCatagoryDataTable) {
		this.loyaltyCatagoryDataTable = loyaltyCatagoryDataTable;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<LoyaltyTypeDesc> getLoyaltyTypeList() {

		lstOfLoyaltyTypeDescs = getLoyaltyCatagoryService().getLoyaltyTypeDescList(sessionStateManage.getLanguageId());

		return lstOfLoyaltyTypeDescs;
	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void loyaltyCatagoryCreationNavigation() {

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "loyaltycatagorymaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/loyaltycatagorymaster.xhtml");
			setBooLoyaltyCatagoryDataTable(false);
			setBooLoyaltyCatagoryDetail(true);
			lstLoyaltyCatagoryDataTables.clear();
			lstLoyaltyCatagoryNewDataTables.clear();
			clearAll();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/*	public void loyaltyCatagoryApprovalNavigation() {

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complainttypeapproval.xhtml");
			setBooLoyaltyCatagoryDataTable(true);
			setBooLoyaltyCatagoryDetail(false);
			approveViewLoyaltyCatagoryMethod();

		} catch (Exception e) {
			// TODO: handle exception loyaltyCatagoryApprovalNavigation
		}
	}*/

	public void duplicateChekingLoyaltyCatagoryCode() {
		if (lstLoyaltyCatagoryDataTables.size() > 0) {
			for (LoyaltyCatagoryMasterDataTable dataTable : lstLoyaltyCatagoryDataTables) {
				if (dataTable.getCategoryCode().equalsIgnoreCase(getCategoryCode())) {
					clearAll();
					RequestContext.getCurrentInstance().execute("datatable.show();");
					return;
				}
			}
		}

		if (getCategoryCode() != null) {
			addRecordsToDataTable();
		}
	}

	public void addRecordsToDataTable() {

		try{
			setBooSubmitPanel(false);
			LoyaltyCatagoryMasterDataTable loyaltyCatagoryDataTable = new LoyaltyCatagoryMasterDataTable();
			loyaltyCatagoryDataTable.setAppCountryId(sessionStateManage.getCountryId());

			loyaltyCatagoryDataTable.setCategoryCode(getCategoryCode());
			loyaltyCatagoryDataTable.setFieldName(getFieldName());
			loyaltyCatagoryDataTable.setCategoryType(getCategoryType());



			if(getValidOption() !=null){
				if(getValidOption().equals(Constants.Yes)){
					loyaltyCatagoryDataTable.setValidOption(Constants.Yes);
					loyaltyCatagoryDataTable.setValidOptionName(Constants.YES);
				}else if(getValidOption().equals(Constants.No)){
					loyaltyCatagoryDataTable.setValidOption(Constants.No);
					loyaltyCatagoryDataTable.setValidOptionName(Constants.NO);
				}
			}


			loyaltyCatagoryDataTable.setEngLoyaltyCatagoryDescId(getEngLoyaltyCatagoryDescId());
			loyaltyCatagoryDataTable.setLocalLoyaltyCatagoryDescId(getLocalLoyaltyCatagoryDescId());

			loyaltyCatagoryDataTable.setLocalLanguageId(getLocalLanguageId());
			loyaltyCatagoryDataTable.setEnglishLanguageId(getEnglishLanguageId());
			loyaltyCatagoryDataTable.setFullEngLoyaltyCatagoryDesc(getFullEngLoyaltyCatagoryDesc());
			loyaltyCatagoryDataTable.setFullLocalLoyaltyCatagoryDesc(getFullLocalLoyaltyCatagoryDesc());
			loyaltyCatagoryDataTable.setShortEngLoyaltyCatagoryDesc(getShortEngLoyaltyCatagoryDesc());
			loyaltyCatagoryDataTable.setShortLocalLoyaltyCatagoryDesc(getShortLocalLoyaltyCatagoryDesc());

			loyaltyCatagoryDataTable.setCreatedBy(getCreatedBy());
			loyaltyCatagoryDataTable.setCreatedDate(getCreatedDate());
			loyaltyCatagoryDataTable.setLoyaltyCatagoryId(getLoyaltyCatagoryId());

			if (getLoyaltyCatagoryId() != null) {
				if (lstLoyaltyCatagoryNewDataTables.size() == 0 && (lstLoyaltyCatagoryDataTables.size() != 0 || getLoyaltyCatagoryDataTable() != null)) {
					loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					setBooEdit(true);
					loyaltyCatagoryDataTable.setIsActive(Constants.U);
					loyaltyCatagoryDataTable.setModifiedBy(sessionStateManage.getUserName());
					loyaltyCatagoryDataTable.setModifiedDate(new Date());


					if (getLoyaltyCatagoryDataTable() != null) {
						if ((loyaltyCatagoryDataTable.getCategoryCode().equalsIgnoreCase(loyaltyCatagoryDataTable.getCategoryCode()) && loyaltyCatagoryDataTable.getFullEngLoyaltyCatagoryDesc().equalsIgnoreCase(getFullEngLoyaltyCatagoryDesc())
								&& loyaltyCatagoryDataTable.getShortEngLoyaltyCatagoryDesc().equalsIgnoreCase(getShortEngLoyaltyCatagoryDesc()) && loyaltyCatagoryDataTable.getFullLocalLoyaltyCatagoryDesc().equalsIgnoreCase(getFullLocalLoyaltyCatagoryDesc()) && loyaltyCatagoryDataTable
								.getShortLocalLoyaltyCatagoryDesc().equalsIgnoreCase(getShortLocalLoyaltyCatagoryDesc()))) {

							loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
							loyaltyCatagoryDataTable.setIsActive(getIsActive());
							loyaltyCatagoryDataTable.setModifiedBy(getModifiedBy());
							loyaltyCatagoryDataTable.setModifiedDate(getModifiedDate());
							loyaltyCatagoryDataTable.setApprovedBy(getApprovedBy());
							loyaltyCatagoryDataTable.setApprovedDate(getApprovedDate());
							loyaltyCatagoryDataTable.setRemarks(getRemarks());

						} else {
							loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
							setBooEdit(true);
							loyaltyCatagoryDataTable.setIsActive(Constants.U);
							loyaltyCatagoryDataTable.setModifiedBy(sessionStateManage.getUserName());
							loyaltyCatagoryDataTable.setModifiedDate(new Date());
							loyaltyCatagoryDataTable.setApprovedBy(null);
							loyaltyCatagoryDataTable.setApprovedDate(null);
							loyaltyCatagoryDataTable.setRemarks(null);

						}
					}
				} else {
					loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					loyaltyCatagoryDataTable.setIsActive(getIsActive());
					loyaltyCatagoryDataTable.setModifiedBy(getModifiedBy());
					loyaltyCatagoryDataTable.setModifiedDate(getModifiedDate());
					loyaltyCatagoryDataTable.setApprovedBy(getApprovedBy());
					loyaltyCatagoryDataTable.setApprovedDate(getApprovedDate());
					loyaltyCatagoryDataTable.setRemarks(getRemarks());
				}
			} else {
				loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				loyaltyCatagoryDataTable.setIsActive(Constants.U);
				loyaltyCatagoryDataTable.setCreatedBy(sessionStateManage.getUserName());
				loyaltyCatagoryDataTable.setCreatedDate(new Date());

			}
			lstLoyaltyCatagoryDataTables.add(loyaltyCatagoryDataTable);

			if (getLoyaltyCatagoryId() == null) {
				lstLoyaltyCatagoryNewDataTables.add(loyaltyCatagoryDataTable);
			}
			setBooLoyaltyCatagoryDataTable(true);
			setBooLoyaltyCatagoryDetail(true);

			clearAll();
		}catch(NullPointerException ne){
			log.info("Method Name::addRecordsToDataTable"+ne.getMessage());
			setErrorMessage("Method Name::addRecordsToDataTable"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			log.info("Method Name::addRecordsToDataTable"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	public void saveAllLoyaltyCatagoryMethod() {

		try{
			if(lstLoyaltyCatagoryDataTables.size()>0){
				for (LoyaltyCatagoryMasterDataTable loyaltyCatagoryDataTable : lstLoyaltyCatagoryDataTables) {

					LoyaltyCatagoryMaster loyaltyCatagoryMaster = new LoyaltyCatagoryMaster();
					loyaltyCatagoryMaster.setLoyaltyCatagoryId(loyaltyCatagoryDataTable.getLoyaltyCatagoryId());


					loyaltyCatagoryMaster.setFieldName(loyaltyCatagoryDataTable.getFieldName());
					//loyaltyCatagoryDataTable.setValidOption(loyaltyCatagoryDataTable.getValidOption());

					/*lstOfLoyaltyTypeDescs = getLoyaltyCatagoryService().getLoyaltyTypeMasterDesc(getCategoryTypeId(), sessionStateManage.getLanguageId());

				if(lstOfLoyaltyTypeDescs !=null){
				for(LoyaltyTypeDesc loyaltyTypeDesc : lstOfLoyaltyTypeDescs){
					loyaltyCatagoryMaster.setCategoryType(loyaltyTypeDesc.getShortDescription());

				}
			}*/


					loyaltyCatagoryMaster.setCategoryType(loyaltyCatagoryDataTable.getCategoryType());


					if(loyaltyCatagoryDataTable.getValidOption() != null){
						if (loyaltyCatagoryDataTable.getValidOption().equals(Constants.Yes)) {
							loyaltyCatagoryMaster.setValidOption(Constants.Yes);
							//loyaltyCatagoryDataTable.setValidOptionName(Constants.YES);
						} else if (loyaltyCatagoryDataTable.getValidOption().equals(Constants.No)) {
							loyaltyCatagoryMaster.setValidOption(Constants.No);
							//loyaltyCatagoryDataTable.setValidOptionName(Constants.NO);
						}
					}



					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(loyaltyCatagoryDataTable.getAppCountryId());
					loyaltyCatagoryMaster.setApplicationCountryId(countryMaster);

					loyaltyCatagoryMaster.setCategoryCode(loyaltyCatagoryDataTable.getCategoryCode());

					loyaltyCatagoryMaster.setCreatedBy(loyaltyCatagoryDataTable.getCreatedBy());
					loyaltyCatagoryMaster.setCreatedDate(loyaltyCatagoryDataTable.getCreatedDate());
					loyaltyCatagoryMaster.setModifiedBy(loyaltyCatagoryDataTable.getModifiedBy());
					loyaltyCatagoryMaster.setModifiedDate(loyaltyCatagoryDataTable.getModifiedDate());
					loyaltyCatagoryMaster.setApprovedBy(loyaltyCatagoryDataTable.getApprovedBy());
					loyaltyCatagoryMaster.setApprovedDate(loyaltyCatagoryDataTable.getApprovedDate());
					loyaltyCatagoryMaster.setIsactive(Constants.Yes);
					//loyaltyCatagoryMaster.setRemarks(loyaltyCatagoryDataTable.getRemarks());


					LoyaltyCatergoryMasterDesc loyaltyCatagoryEnglishDesc = new LoyaltyCatergoryMasterDesc();

					LanguageType englishLanguageType = new LanguageType();
					englishLanguageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
					loyaltyCatagoryEnglishDesc.setLanguageId(englishLanguageType);
					loyaltyCatagoryEnglishDesc.setFullDesc(loyaltyCatagoryDataTable.getFullEngLoyaltyCatagoryDesc());
					loyaltyCatagoryEnglishDesc.setShortDesc(loyaltyCatagoryDataTable.getShortEngLoyaltyCatagoryDesc());
					loyaltyCatagoryEnglishDesc.setLoyaltyCategoryId(loyaltyCatagoryMaster);
					loyaltyCatagoryEnglishDesc.setLoyaltyCategoryMasterdescId(loyaltyCatagoryDataTable.getEngLoyaltyCatagoryDescId());


					LoyaltyCatergoryMasterDesc loyaltyCatagoryArbicDesc = new LoyaltyCatergoryMasterDesc();

					LanguageType arabicLanguageType = new LanguageType();
					arabicLanguageType.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
					loyaltyCatagoryArbicDesc.setLanguageId(arabicLanguageType);

					loyaltyCatagoryArbicDesc.setFullDesc(loyaltyCatagoryDataTable.getFullLocalLoyaltyCatagoryDesc());
					loyaltyCatagoryArbicDesc.setShortDesc(loyaltyCatagoryDataTable.getShortLocalLoyaltyCatagoryDesc());
					loyaltyCatagoryArbicDesc.setLoyaltyCategoryMasterdescId(loyaltyCatagoryDataTable.getLocalLoyaltyCatagoryDescId());
					loyaltyCatagoryArbicDesc.setLoyaltyCategoryId(loyaltyCatagoryMaster);

					HashMap<String,Object> mapAllForSave = new HashMap<String,Object>();
					mapAllForSave.put("loyaltyType", loyaltyCatagoryMaster);
					mapAllForSave.put("englishDesc", loyaltyCatagoryEnglishDesc);
					mapAllForSave.put("localDesc", loyaltyCatagoryArbicDesc);
					getLoyaltyCatagoryService().saveLoyaltyCataegory(mapAllForSave);

					/*try {
				insertLoyaltyCatagoryAndDesc(loyaltyCatagoryMaster, loyaltyCatagoryMaster, loyaltyCatagoryMaster);


			} catch (Exception e) {
			}*/
				}
			}
			RequestContext.getCurrentInstance().execute("complete.show();");
			lstLoyaltyCatagoryDataTables.clear();
			setBooLoyaltyCatagoryDataTable(false);
		}catch(NullPointerException ne){
			log.info("Method Name::saveAllLoyaltyCatagoryMethod"+ne.getMessage());
			setErrorMessage("Method Name::saveAllLoyaltyCatagoryMethod"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			log.info("Method Name::saveAllLoyaltyCatagoryMethod"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	@SuppressWarnings("unused")
	private void insertLoyaltyCatagoryAndDesc(LoyaltyCatagoryMaster loyaltyCatagoryMaster, LoyaltyCatergoryMasterDesc loyaltyCatagoryEnglishDesc, LoyaltyCatergoryMasterDesc loyaltyCatagoryArbicDesc) {
		getLoyaltyCatagoryService().saveAndUpdateLoyaltyCatagoryMaster(loyaltyCatagoryMaster);
		getLoyaltyCatagoryService().saveAndUpdateLoyaltyCatergoryMasterDesc(loyaltyCatagoryEnglishDesc);
		getLoyaltyCatagoryService().saveAndUpdateLoyaltyCatergoryMasterDesc(loyaltyCatagoryArbicDesc);
	}

	public void viewLoyaltyCatagoryMethod() {

		try{
			System.out.println(""+getCategoryType());

			if(getCategoryType() == null){
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("viewSearch.show();");
				return;
			}else{
				Boolean childRecordCheck = false;
				lstLoyaltyCatagoryDataTables.clear();
				lstLoyaltyCatagoryNewDataTables.clear();
				//clearAll();
				lstofLoyaltyCatagory = getLoyaltyCatagoryService().displayAllLoyaltyCatagoryToView(sessionStateManage.getCountryId(),getCategoryType());

				if (lstofLoyaltyCatagory != null) {
					setBooLoyaltyCatagoryDataTable(true);
					setBooLoyaltyCatagoryDetail(true);
					for (LoyaltyCatagoryMaster loyaltyCatagoryMaster : lstofLoyaltyCatagory) {
						LoyaltyCatagoryMasterDataTable loyaltyCatagoryDataTable = new LoyaltyCatagoryMasterDataTable();

						loyaltyCatagoryDataTable.setLoyaltyCatagoryId(loyaltyCatagoryMaster.getLoyaltyCatagoryId());
						loyaltyCatagoryDataTable.setCategoryCode(loyaltyCatagoryMaster.getCategoryCode());
						loyaltyCatagoryDataTable.setFieldName(loyaltyCatagoryMaster.getFieldName());
						//loyaltyCatagoryDataTable.setValidOption(loyaltyCatagoryMaster.getValidOption());
						loyaltyCatagoryDataTable.setCategoryType(loyaltyCatagoryMaster.getCategoryType());

						/*lstOfLoyaltyTypeDescs = getLoyaltyCatagoryService().getLoyaltyTypeId(loyaltyCatagoryMaster.getCategoryType(), sessionStateManage.getLanguageId());

					if(lstOfLoyaltyTypeDescs !=null){
						for(LoyaltyTypeDesc loyaltyTypeDesc : lstOfLoyaltyTypeDescs){
							loyaltyCatagoryDataTable.setCategoryTypeId(loyaltyTypeDesc.getLoyalityTypeId().getLoyalityTypeId());

						}
					}*/

						if(loyaltyCatagoryMaster.getValidOption() !=null){
							if (loyaltyCatagoryMaster.getValidOption().equals(Constants.Yes)) {
								loyaltyCatagoryDataTable.setValidOption(Constants.Yes);
								loyaltyCatagoryDataTable.setValidOptionName(Constants.YES);
							} else if (loyaltyCatagoryMaster.getValidOption().equals(Constants.No)) {
								loyaltyCatagoryDataTable.setValidOption(Constants.No);
								loyaltyCatagoryDataTable.setValidOptionName(Constants.NO);
							}

						}

						loyaltyCatagoryDataTable.setAppCountryId(loyaltyCatagoryMaster.getApplicationCountryId().getCountryId());
						loyaltyCatagoryDataTable.setModifiedBy(loyaltyCatagoryMaster.getModifiedBy());
						loyaltyCatagoryDataTable.setModifiedDate(loyaltyCatagoryMaster.getModifiedDate());
						loyaltyCatagoryDataTable.setCreatedBy(loyaltyCatagoryMaster.getCreatedBy());
						loyaltyCatagoryDataTable.setCreatedDate(loyaltyCatagoryMaster.getCreatedDate());
						loyaltyCatagoryDataTable.setApprovedBy(loyaltyCatagoryMaster.getApprovedBy());
						loyaltyCatagoryDataTable.setApprovedDate(loyaltyCatagoryMaster.getApprovedDate());
						loyaltyCatagoryDataTable.setRemarks(loyaltyCatagoryMaster.getRemarks());
						loyaltyCatagoryDataTable.setIsActive(loyaltyCatagoryMaster.getIsactive());

						if (loyaltyCatagoryDataTable.getIsActive().equalsIgnoreCase(Constants.Yes)) {
							loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
						} else if (loyaltyCatagoryDataTable.getIsActive().equalsIgnoreCase(Constants.D)) {
							loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
						} else if (loyaltyCatagoryDataTable.getIsActive().equalsIgnoreCase(Constants.U) && loyaltyCatagoryDataTable.getModifiedBy() == null && loyaltyCatagoryDataTable.getModifiedDate() == null && loyaltyCatagoryDataTable.getApprovedBy() == null
								&& loyaltyCatagoryDataTable.getApprovedDate() == null && loyaltyCatagoryDataTable.getRemarks() == null) {
							loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
						} else {
							setBooEdit(true);
							loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						}

						lstofLoyaltyCatagoryDesc = getLoyaltyCatagoryService().displayAllLoyaltyCatergoryMasterDesc(loyaltyCatagoryMaster.getLoyaltyCatagoryId());
						if (lstofLoyaltyCatagoryDesc != null) {
							for (LoyaltyCatergoryMasterDesc loyaltyCatagoryDesc : lstofLoyaltyCatagoryDesc) {
								childRecordCheck = true;
								if (loyaltyCatagoryDesc.getLanguageId().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))) {
									loyaltyCatagoryDataTable.setEnglishLanguageId(loyaltyCatagoryDesc.getLanguageId().getLanguageId());
									loyaltyCatagoryDataTable.setFullEngLoyaltyCatagoryDesc(loyaltyCatagoryDesc.getFullDesc());
									loyaltyCatagoryDataTable.setShortEngLoyaltyCatagoryDesc(loyaltyCatagoryDesc.getShortDesc());
									loyaltyCatagoryDataTable.setEngLoyaltyCatagoryDescId(loyaltyCatagoryDesc.getLoyaltyCategoryMasterdescId());

								} else if (loyaltyCatagoryDesc.getLanguageId().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
									loyaltyCatagoryDataTable.setLocalLanguageId(loyaltyCatagoryDesc.getLanguageId().getLanguageId());
									loyaltyCatagoryDataTable.setFullLocalLoyaltyCatagoryDesc(loyaltyCatagoryDesc.getFullDesc());
									loyaltyCatagoryDataTable.setShortLocalLoyaltyCatagoryDesc(loyaltyCatagoryDesc.getShortDesc());
									loyaltyCatagoryDataTable.setLocalLoyaltyCatagoryDescId(loyaltyCatagoryDesc.getLoyaltyCategoryMasterdescId());

								}

							}
						} else {
							childRecordCheck = false;
						}

						if (childRecordCheck == true) {
							lstLoyaltyCatagoryDataTables.add(loyaltyCatagoryDataTable);
						}
					}

					lstLoyaltyCatagoryDataTables.addAll(lstLoyaltyCatagoryNewDataTables);
					clearAll();

				} else {
					RequestContext.getCurrentInstance().execute("noRecords.show();");
				}
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/loyaltycatagorymaster.xhtml");

				} catch (Exception e) {
					e.printStackTrace();
				}


			}
		}catch(NullPointerException ne){
			log.info("Method Name::viewLoyaltyCatagoryMethod"+ne.getMessage());
			setErrorMessage("Method Name::viewLoyaltyCatagoryMethod"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			log.info("Method Name::viewLoyaltyCatagoryMethod"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	public void editLoyaltyCatagoryMethod(LoyaltyCatagoryMasterDataTable loyaltyCatagoryDataTable) {

		try{
			setLoyaltyCatagoryId(loyaltyCatagoryDataTable.getLoyaltyCatagoryId());
			setAppCountryId(loyaltyCatagoryDataTable.getAppCountryId());
			setCategoryCode(loyaltyCatagoryDataTable.getCategoryCode());
			//setCategoryTypeId(loyaltyCatagoryDataTable.getCategoryTypeId());

			if(loyaltyCatagoryDataTable.getValidOption() !=null){
				if(loyaltyCatagoryDataTable.getValidOption().equals(Constants.Yes)){
					setValidOption(loyaltyCatagoryDataTable.getValidOption());

				}else if(loyaltyCatagoryDataTable.getValidOption().equals(Constants.No)){
					setValidOption(loyaltyCatagoryDataTable.getValidOption());
				}
			}


			setFieldName(loyaltyCatagoryDataTable.getFieldName());
			setCategoryType(loyaltyCatagoryDataTable.getCategoryType());

			setCreatedBy(loyaltyCatagoryDataTable.getCreatedBy());
			setCreatedDate(loyaltyCatagoryDataTable.getCreatedDate());
			setModifiedBy(loyaltyCatagoryDataTable.getModifiedBy());
			setModifiedDate(loyaltyCatagoryDataTable.getModifiedDate());
			setApprovedBy(loyaltyCatagoryDataTable.getApprovedBy());
			setApprovedDate(loyaltyCatagoryDataTable.getApprovedDate());
			setIsActive(loyaltyCatagoryDataTable.getIsActive());
			setRemarks(loyaltyCatagoryDataTable.getRemarks());

			setEngLoyaltyCatagoryDescId(loyaltyCatagoryDataTable.getEngLoyaltyCatagoryDescId());
			setEnglishLanguageId(loyaltyCatagoryDataTable.getEnglishLanguageId());
			setFullEngLoyaltyCatagoryDesc(loyaltyCatagoryDataTable.getFullEngLoyaltyCatagoryDesc());
			setShortEngLoyaltyCatagoryDesc(loyaltyCatagoryDataTable.getShortEngLoyaltyCatagoryDesc());

			setDynamicLabelForActivateDeactivate(loyaltyCatagoryDataTable.getDynamicLabelForActivateDeactivate());

			setLocalLoyaltyCatagoryDescId(loyaltyCatagoryDataTable.getLocalLoyaltyCatagoryDescId());
			setLocalLanguageId(loyaltyCatagoryDataTable.getLocalLanguageId());
			setFullLocalLoyaltyCatagoryDesc(loyaltyCatagoryDataTable.getFullLocalLoyaltyCatagoryDesc());
			setShortLocalLoyaltyCatagoryDesc(loyaltyCatagoryDataTable.getShortLocalLoyaltyCatagoryDesc());

			lstLoyaltyCatagoryDataTables.remove(loyaltyCatagoryDataTable);
			setBooEdit(true);
			setBtnClear(true);
			setIsdisable(true);

		}catch(NullPointerException ne){
			log.info("Method Name::editLoyaltyCatagoryMethod"+ne.getMessage());
			setErrorMessage("Method Name::save"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			log.info("Method Name::editLoyaltyCatagoryMethod"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	/*public void approveViewLoyaltyCatagoryMethod() {
		lstLoyaltyCatagoryDataTables.clear();

		lstofLoyaltyCatagory = getLoyaltyCatagoryService().displayLoyaltyCatagoryForApprove(sessionStateManage.getCountryId(), Constants.U);

		if (lstofLoyaltyCatagory != null) {

			for (LoyaltyCatagoryMaster loyaltyCatagoryMaster : lstofLoyaltyCatagory) {

				LoyaltyCatagoryMasterDataTable loyaltyCatagoryDataTable = new LoyaltyCatagoryMasterDataTable();

				loyaltyCatagoryDataTable.setLoyaltyCatagoryId(loyaltyCatagoryMaster.getLoyaltyCatagoryId());
				loyaltyCatagoryDataTable.setCategoryCode(loyaltyCatagoryMaster.getCategoryCode());

				loyaltyCatagoryDataTable.setAppCountryId(loyaltyCatagoryMaster.getApplicationCountryId().getCountryId());

				loyaltyCatagoryDataTable.setModifiedBy(loyaltyCatagoryMaster.getModifiedBy());
				loyaltyCatagoryDataTable.setModifiedDate(loyaltyCatagoryMaster.getModifiedDate());
				loyaltyCatagoryDataTable.setCreatedBy(loyaltyCatagoryMaster.getCreatedBy());
				loyaltyCatagoryDataTable.setCreatedDate(loyaltyCatagoryMaster.getCreatedDate());
				loyaltyCatagoryDataTable.setApprovedBy(loyaltyCatagoryMaster.getApprovedBy());
				loyaltyCatagoryDataTable.setApprovedDate(loyaltyCatagoryMaster.getApprovedDate());
				loyaltyCatagoryDataTable.setIsActive(loyaltyCatagoryMaster.getIsactive());

				loyaltyCatagoryDataTable.setRemarks(loyaltyCatagoryMaster.getRemarks());

				lstofLoyaltyCatagoryDesc = getLoyaltyCatagoryService().displayAllLoyaltyCatergoryMasterDesc(loyaltyCatagoryMaster.getLoyaltyCatagoryId());
				if (lstofLoyaltyCatagoryDesc != null) {
					for (LoyaltyCatergoryMasterDesc loyaltyCatagoryDesc : lstofLoyaltyCatagoryDesc) {

						if (loyaltyCatagoryDesc.getLanguageId().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))) {
							loyaltyCatagoryDataTable.setEnglishLanguageId(loyaltyCatagoryDesc.getLanguageId().getLanguageId());
							loyaltyCatagoryDataTable.setFullEngLoyaltyCatagoryDesc(loyaltyCatagoryDesc.getFullDesc());
							loyaltyCatagoryDataTable.setShortEngLoyaltyCatagoryDesc(loyaltyCatagoryDesc.getShortDesc());
							loyaltyCatagoryDataTable.setEngLoyaltyCatagoryDescId(loyaltyCatagoryDesc.getLoyaltyCategoryMasterdescId());

						} else if (loyaltyCatagoryDesc.getLanguageId().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
							loyaltyCatagoryDataTable.setLocalLanguageId(loyaltyCatagoryDesc.getLanguageId().getLanguageId());
							loyaltyCatagoryDataTable.setFullLocalLoyaltyCatagoryDesc(loyaltyCatagoryDesc.getFullDesc());
							loyaltyCatagoryDataTable.setShortLocalLoyaltyCatagoryDesc(loyaltyCatagoryDesc.getShortDesc());
							loyaltyCatagoryDataTable.setLocalLoyaltyCatagoryDescId(loyaltyCatagoryDesc.getLoyaltyCategoryMasterdescId());

						}

					}
				}

				lstLoyaltyCatagoryDataTables.add(loyaltyCatagoryDataTable);
				setBooLoyaltyCatagoryDataTable(true);
				setBooLoyaltyCatagoryDetail(false);

				clearAll();

			}
		}

	}*/

	/*public void editLoyaltyCatagoryForApproveMethod(LoyaltyCatagoryMasterDataTable loyaltyCatagoryDataTable) {

		if ((loyaltyCatagoryDataTable.getModifiedBy() == null ? loyaltyCatagoryDataTable.getCreatedBy() : loyaltyCatagoryDataTable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())) {
			RequestContext.getCurrentInstance().execute("notValidUser.show();");
		} else {

			setLoyaltyCatagoryId(loyaltyCatagoryDataTable.getLoyaltyCatagoryId());
			setAppCountryId(loyaltyCatagoryDataTable.getAppCountryId());
			setCategoryCode(loyaltyCatagoryDataTable.getCategoryCode());

			setCreatedBy(loyaltyCatagoryDataTable.getCreatedBy());
			setCreatedDate(loyaltyCatagoryDataTable.getCreatedDate());
			setModifiedBy(loyaltyCatagoryDataTable.getModifiedBy());
			setModifiedDate(loyaltyCatagoryDataTable.getModifiedDate());
			setApprovedBy(loyaltyCatagoryDataTable.getApprovedBy());
			setApprovedDate(loyaltyCatagoryDataTable.getApprovedDate());
			setIsActive(loyaltyCatagoryDataTable.getIsActive());
			setRemarks(loyaltyCatagoryDataTable.getRemarks());

			setEngLoyaltyCatagoryDescId(loyaltyCatagoryDataTable.getEngLoyaltyCatagoryDescId());
			setEnglishLanguageId(loyaltyCatagoryDataTable.getEnglishLanguageId());
			setFullEngLoyaltyCatagoryDesc(loyaltyCatagoryDataTable.getFullEngLoyaltyCatagoryDesc());
			setShortEngLoyaltyCatagoryDesc(loyaltyCatagoryDataTable.getShortEngLoyaltyCatagoryDesc());

			setLocalLoyaltyCatagoryDescId(loyaltyCatagoryDataTable.getLocalLoyaltyCatagoryDescId());
			setLocalLanguageId(loyaltyCatagoryDataTable.getLocalLanguageId());
			setFullLocalLoyaltyCatagoryDesc(loyaltyCatagoryDataTable.getFullLocalLoyaltyCatagoryDesc());
			setShortLocalLoyaltyCatagoryDesc(loyaltyCatagoryDataTable.getShortLocalLoyaltyCatagoryDesc());

			lstLoyaltyCatagoryDataTables.remove(loyaltyCatagoryDataTable);
			setBooLoyaltyCatagoryDataTable(false);
			setBooLoyaltyCatagoryDetail(true);
		}

	}*/

	/*public void approveLoyaltyCatagoryMethod() {

		getLoyaltyCatagoryService().approveRecord(getLoyaltyCatagoryId(), sessionStateManage.getUserName(), Constants.Yes);
		RequestContext.getCurrentInstance().execute("complete.show();");

	}*/

	public void clearAll() {

		setAppCountryId(null);
		setCategoryCode(null);
		setFieldName(null);
		setValidOption(null);
		setCategoryType(null);
		setCategoryTypeId(null);

		setLoyaltyCatagoryDescId(null);
		setLoyaltyCatagoryId(null);
		setFullEngLoyaltyCatagoryDesc(null);
		setFullLocalLoyaltyCatagoryDesc(null);
		setShortEngLoyaltyCatagoryDesc(null);
		setShortLocalLoyaltyCatagoryDesc(null);
		setLoyaltyCatagoryDescId(null);
		setLocalLoyaltyCatagoryDescId(null);
		setEngLoyaltyCatagoryDescId(null);
		setRemarks(null);

		setBooEdit(false);
		setBtnClear(false);
		setIsdisable(false);
		setDisableSubmitButton(false);

	}

	public void checkStatusType(LoyaltyCatagoryMasterDataTable loyaltyCatagoryDataTable) throws IOException {

		try{
			if(loyaltyCatagoryDataTable.getDynamicLabelForActivateDeactivate()!=null){
			if (loyaltyCatagoryDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
				// setBooEdit(t);
				RequestContext.getCurrentInstance().execute("pending.show();");
				return;
			} else if (loyaltyCatagoryDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {

				loyaltyCatagoryDataTable.setRemarksCheck(true);
				setRemarks(null);
				setApprovedBy(loyaltyCatagoryDataTable.getApprovedBy());
				setApprovedDate(loyaltyCatagoryDataTable.getApprovedDate());
				RequestContext.getCurrentInstance().execute("remarks.show();");
				return;
			} else if (loyaltyCatagoryDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)) {
				loyaltyCatagoryDataTable.setBooCheckDelete(true);
				RequestContext.getCurrentInstance().execute("permanentDelete.show();");
				return;
			} else if (loyaltyCatagoryDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				loyaltyCatagoryDataTable.setActivateRecordCheck(true);
				RequestContext.getCurrentInstance().execute("activateRecord.show();");
				return;
			} else if (loyaltyCatagoryDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
				lstLoyaltyCatagoryDataTables.remove(loyaltyCatagoryDataTable);
				lstLoyaltyCatagoryNewDataTables.clear();

			}
			}
		}catch(NullPointerException ne){
			log.info("Method Name::checkStatusType"+ne.getMessage());
			setErrorMessage("Method Name::checkStatusType"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			log.info("Method Name::checkStatusType"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void confirmPermanentDelete() {
		if (lstLoyaltyCatagoryDataTables.size() > 0) {
			for (LoyaltyCatagoryMasterDataTable loyaltyCatagoryDataTable : lstLoyaltyCatagoryDataTables) {
				if (loyaltyCatagoryDataTable.getBooCheckDelete() != null) {
					if (loyaltyCatagoryDataTable.getBooCheckDelete().equals(true)) {
						try{
							delete(loyaltyCatagoryDataTable);
							lstLoyaltyCatagoryDataTables.remove(loyaltyCatagoryDataTable);
						}catch(NullPointerException ne){
							log.info("Method Name::confirmPermanentDelete"+ne.getMessage());
							setErrorMessage("Method Name::confirmPermanentDelete"); 
							RequestContext.getCurrentInstance().execute("nullPointerId.show();");
							return; 
						} catch (Exception exception) {
							log.info("Method Name::confirmPermanentDelete"+exception.getMessage());
							setErrorMessage(exception.getMessage());
							RequestContext.getCurrentInstance().execute("exception.show();");
							return;
						}

					}
				}
			}
			RequestContext.getCurrentInstance().execute("update.show();");
		}

	}

	public void conformToDeActivteCompliantType(LoyaltyCatagoryMasterDataTable loyaltyCatagoryDataTable) {
		getLoyaltyCatagoryService().activateLoyaltyCatagoryMaster(loyaltyCatagoryDataTable.getLoyaltyCatagoryId(), sessionStateManage.getUserName());
		//RequestContext.getCurrentInstance().execute("update.show();");
	}

	public void activateRecord() {

		if (lstLoyaltyCatagoryDataTables.size() > 0) {
			for (LoyaltyCatagoryMasterDataTable loyaltyCatagoryDataTable : lstLoyaltyCatagoryDataTables) {
				if (loyaltyCatagoryDataTable.getActivateRecordCheck() != null) {
					if (loyaltyCatagoryDataTable.getActivateRecordCheck().equals(true)) {
						try{
							conformToDeActivteCompliantType(loyaltyCatagoryDataTable);
						}catch(NullPointerException ne){
							log.info("Method Name::activateRecord"+ne.getMessage());
							setErrorMessage("Method Name::activateRecord"); 
							RequestContext.getCurrentInstance().execute("nullPointerId.show();");
							return; 
						} catch (Exception exception) {
							log.info("Method Name::activateRecord"+exception.getMessage());
							setErrorMessage(exception.getMessage());
							RequestContext.getCurrentInstance().execute("exception.show();");
							return;
						}

					}
				}
			}
			RequestContext.getCurrentInstance().execute("update.show();");
		}

	}

	public void remarkSelectedRecord() throws IOException {

		for (LoyaltyCatagoryMasterDataTable loyaltyCatagoryDataTable : lstLoyaltyCatagoryDataTables) {
			if (loyaltyCatagoryDataTable.getRemarksCheck().equals(true)) {
				if (!getRemarks().equals("")) {
					loyaltyCatagoryDataTable.setRemarks(getRemarks());
					loyaltyCatagoryDataTable.setApprovedBy(null);
					loyaltyCatagoryDataTable.setApprovedDate(null);
					loyaltyCatagoryDataTable.setRemarksCheck(true);
					update(loyaltyCatagoryDataTable);
					lstLoyaltyCatagoryDataTables.clear();

				} else {
					RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
					return;

				}
			}

		}

	}

	public void delete(LoyaltyCatagoryMasterDataTable loyaltyCatagoryDataTable) {

		getLoyaltyCatagoryService().deleteLoyaltyCatagoryMaster(loyaltyCatagoryDataTable.getLoyaltyCatagoryId(), loyaltyCatagoryDataTable.getEngLoyaltyCatagoryDescId(), loyaltyCatagoryDataTable.getLocalLoyaltyCatagoryDescId());
		//RequestContext.getCurrentInstance().execute("update.show();");
	}

	public void update(LoyaltyCatagoryMasterDataTable loyaltyCatagoryDataTable) throws IOException {

		try {

			LoyaltyCatagoryMaster loyaltyCatagoryMaster = new LoyaltyCatagoryMaster();

			loyaltyCatagoryMaster.setLoyaltyCatagoryId(loyaltyCatagoryDataTable.getLoyaltyCatagoryId());
			loyaltyCatagoryMaster.setCategoryCode(loyaltyCatagoryDataTable.getCategoryCode());

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(loyaltyCatagoryDataTable.getAppCountryId());
			loyaltyCatagoryMaster.setApplicationCountryId(countryMaster);

			loyaltyCatagoryMaster.setCreatedBy(loyaltyCatagoryDataTable.getCreatedBy());
			loyaltyCatagoryMaster.setCreatedDate(loyaltyCatagoryDataTable.getCreatedDate());
			// loyaltyCatagoryDataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(loyaltyCatagoryMaster.getIsActive()));
			loyaltyCatagoryMaster.setRemarks(loyaltyCatagoryMaster.getRemarks());

			loyaltyCatagoryMaster.setModifiedBy(sessionStateManage.getUserName());
			loyaltyCatagoryMaster.setModifiedDate(new Date());
			loyaltyCatagoryMaster.setApprovedBy(null);
			loyaltyCatagoryMaster.setApprovedDate(null);
			loyaltyCatagoryMaster.setRemarks(loyaltyCatagoryDataTable.getRemarks());
			loyaltyCatagoryMaster.setIsactive(Constants.D);
			loyaltyCatagoryMaster.setCreatedBy(loyaltyCatagoryDataTable.getCreatedBy());
			loyaltyCatagoryMaster.setCreatedDate(loyaltyCatagoryDataTable.getCreatedDate());

			getLoyaltyCatagoryService().saveAndUpdateLoyaltyCatagoryMaster(loyaltyCatagoryMaster);

			RequestContext.getCurrentInstance().execute("update.show();");

		}catch(NullPointerException ne){
			log.info("Method Name::update"+ne.getMessage());
			setErrorMessage("Method Name::update"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception exception) {
			log.info("Method Name::update"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void exit() {
		log.info("Enter in exit method ");

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			lstLoyaltyCatagoryDataTables.clear();
			lstLoyaltyCatagoryNewDataTables.clear();
			setBooLoyaltyCatagoryDataTable(false);
			setBooLoyaltyCatagoryDetail(false);
			clearAll();

		} catch (Exception e) {
			log.info("Problem to redirect:" + e);
		}

		log.info("Exit from exit method ");
	}

	public List<String> autoComplete(String query) {
		if (query.length() > 0) {
			return getLoyaltyCatagoryService().autoCompleteList(query);
		} else {
			return null;
		}
	}

	public void autoCompletePopulateValue() {

		lstofLoyaltyCatagory = getLoyaltyCatagoryService().displayLoyaltyCatagoryBasedOnCatagoryCode(getCategoryCode());

		if (lstofLoyaltyCatagory.size() > 0) {
			setCategoryCode(null);
			RequestContext.getCurrentInstance().execute("codeExist.show();");
		} else {

			setFullEngLoyaltyCatagoryDesc(null);
			setFullLocalLoyaltyCatagoryDesc(null);
			setShortEngLoyaltyCatagoryDesc(null);
			setShortLocalLoyaltyCatagoryDesc(null);
			setLoyaltyCatagoryDescId(null);
			setEngLoyaltyCatagoryDescId(null);
			setLocalLoyaltyCatagoryDescId(null);
			setRemarks(null);
		}

	}

	public void disableSubmit() {
		setIsdisable(true);
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
	
	public void loyaltyCategoryOk(){
		lstLoyaltyCatagoryDataTables.clear();
		lstLoyaltyCatagoryNewDataTables.clear();
		setBooLoyaltyCatagoryDataTable(false);
		clearAll();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/loyaltycatagorymaster.xhtml");
		}catch(Exception e){
			
		}
	}

}
