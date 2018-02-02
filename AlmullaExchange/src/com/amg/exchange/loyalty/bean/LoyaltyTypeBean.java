package com.amg.exchange.loyalty.bean;

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

import com.amg.exchange.bco.bean.BranchComplianceDataTable;
import com.amg.exchange.common.dto.ComplaintActionDTO;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.complaint.DTO.ComplaintMatrixDTO;
import com.amg.exchange.complaint.bean.ComplaintTypeDataTable;
import com.amg.exchange.complaint.customer.support.model.ComplaintFollowup;
import com.amg.exchange.complaint.customer.support.model.ComplaintLog;
import com.amg.exchange.complaint.customer.support.service.IComplaintRegisteredToRemittancesService;
import com.amg.exchange.complaint.model.CommunicationMethod;
import com.amg.exchange.complaint.model.CommunicationMethodDesc;
import com.amg.exchange.complaint.model.ComplaintAction;
import com.amg.exchange.complaint.model.ComplaintActionDesc;
import com.amg.exchange.complaint.model.ComplaintAssigned;
import com.amg.exchange.complaint.model.ComplaintAssignedDesc;
import com.amg.exchange.complaint.model.ComplaintMatrix;
import com.amg.exchange.complaint.model.ComplaintRemarksDesc;
import com.amg.exchange.complaint.model.ComplaintRemarksMaster;
import com.amg.exchange.complaint.model.ComplaintTypeDesc;
import com.amg.exchange.complaint.model.ComplaintTypeMaster;
import com.amg.exchange.complaint.service.IComplaintActionService;
import com.amg.exchange.complaint.service.IComplaintAssignedService;
import com.amg.exchange.complaint.service.IComplaintRemarksService;
import com.amg.exchange.complaint.service.IComplaintService;
import com.amg.exchange.complaint.service.IComplaintTypeService;
import com.amg.exchange.loyalty.model.LoyaltyType;
import com.amg.exchange.loyalty.model.LoyaltyTypeDesc;
import com.amg.exchange.loyalty.service.ILoyaltyTypeService;
import com.amg.exchange.registration.bean.ArticleLevelDataTable;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.service.ICountryMasterservice;
import com.amg.exchange.remittance.dao.IServiceGroupMasterDao;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleDetailsDesc;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IBankMasterService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("unused")
@Component("loyaltyType")
@Scope("session")
public class LoyaltyTypeBean<T> implements Serializable {

	private static final Logger LOG = Logger.getLogger(LoyaltyTypeBean.class);

	/*
	 * Autowire Declaration start here
	 */
	@Autowired
	ILoyaltyTypeService<T> loyaltyTypeService;

	/*
	 * Autowire declaration end here
	 */

	private static final long serialVersionUID = 1L;

	/*
	 * Properties declaration start here
	 */
	private BigDecimal loyalityTypeId;
	private String loyalityTypeCode;
	private String loyalityType;
	private String corporateCode;
	private String corporatePoints;
	private String employeePoints;
	private BigDecimal levelNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private String remarks = "";
	private BigDecimal applicationCountryId;

	private BigDecimal loyalityTypeDescId;
	private BigDecimal loyaltyTypeArabicDescId;
	private String shortDescEnglish;
	private String fullDescEnglish;
	private String shortDescLocal;
	private String fullDescLocal;

	private Boolean booLoyaltyTypeDetail;
	private Boolean booLoyaltyTypeDataTable;

	private String dynamicLabelForActivateDeactivate;

	private List<LoyaltyTypeDataTable> loyaltyTypeDataTableList = new CopyOnWriteArrayList<LoyaltyTypeDataTable>();
	private List<LoyaltyTypeDataTable> newLoyaltyTypeDataTableList = new CopyOnWriteArrayList<LoyaltyTypeDataTable>();

	LoyaltyTypeDataTable loyaltyTypeDataTable = null;

	private List<LoyaltyType> loyaltyTypeList = new ArrayList<LoyaltyType>();
	private List<LoyaltyTypeDesc> loyaltyTypeDescList = new ArrayList<LoyaltyTypeDesc>();

	SessionStateManage sessionStateManage = new SessionStateManage();

	private Boolean booEdit;
	private Boolean isdisable;
	private Boolean btnClear;
	private Boolean disableSubmitButton;
	private Boolean booSubmitPanel;
	private String errorMessage;

	public ILoyaltyTypeService<T> getLoyaltyTypeService() {
		return loyaltyTypeService;
	}

	public void setLoyaltyTypeService(ILoyaltyTypeService<T> loyaltyTypeService) {
		this.loyaltyTypeService = loyaltyTypeService;
	}

	public BigDecimal getLoyalityTypeId() {
		return loyalityTypeId;
	}

	public void setLoyalityTypeId(BigDecimal loyalityTypeId) {
		this.loyalityTypeId = loyalityTypeId;
	}

	public String getLoyalityTypeCode() {
		return loyalityTypeCode;
	}

	public void setLoyalityTypeCode(String loyalityTypeCode) {
		this.loyalityTypeCode = loyalityTypeCode;
	}

	public String getLoyalityType() {
		return loyalityType;
	}

	public void setLoyalityType(String loyalityType) {
		this.loyalityType = loyalityType;
	}

	public String getCorporateCode() {
		return corporateCode;
	}

	public void setCorporateCode(String corporateCode) {
		this.corporateCode = corporateCode;
	}

	public String getCorporatePoints() {
		return corporatePoints;
	}

	public void setCorporatePoints(String corporatePoints) {
		this.corporatePoints = corporatePoints;
	}

	public String getEmployeePoints() {
		return employeePoints;
	}

	public void setEmployeePoints(String employeePoints) {
		this.employeePoints = employeePoints;
	}

	public BigDecimal getLevelNo() {
		return levelNo;
	}

	public void setLevelNo(BigDecimal levelNo) {
		this.levelNo = levelNo;
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

	public BigDecimal getLoyalityTypeDescId() {
		return loyalityTypeDescId;
	}

	public void setLoyalityTypeDescId(BigDecimal loyalityTypeDescId) {
		this.loyalityTypeDescId = loyalityTypeDescId;
	}

	public String getShortDescEnglish() {
		return shortDescEnglish;
	}

	public void setShortDescEnglish(String shortDescEnglish) {
		this.shortDescEnglish = shortDescEnglish;
	}

	public String getFullDescEnglish() {
		return fullDescEnglish;
	}

	public void setFullDescEnglish(String fullDescEnglish) {
		this.fullDescEnglish = fullDescEnglish;
	}

	public String getShortDescLocal() {
		return shortDescLocal;
	}

	public void setShortDescLocal(String shortDescLocal) {
		this.shortDescLocal = shortDescLocal;
	}

	public String getFullDescLocal() {
		return fullDescLocal;
	}

	public void setFullDescLocal(String fullDescLocal) {
		this.fullDescLocal = fullDescLocal;
	}

	public SessionStateManage getSessionStateManage() {
		return sessionStateManage;
	}

	public void setSessionStateManage(SessionStateManage sessionStateManage) {
		this.sessionStateManage = sessionStateManage;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public Boolean getBooLoyaltyTypeDetail() {
		return booLoyaltyTypeDetail;
	}

	public void setBooLoyaltyTypeDetail(Boolean booLoyaltyTypeDetail) {
		this.booLoyaltyTypeDetail = booLoyaltyTypeDetail;
	}

	public Boolean getBooLoyaltyTypeDataTable() {
		return booLoyaltyTypeDataTable;
	}

	public void setBooLoyaltyTypeDataTable(Boolean booLoyaltyTypeDataTable) {
		this.booLoyaltyTypeDataTable = booLoyaltyTypeDataTable;
	}

	public List<LoyaltyTypeDataTable> getLoyaltyTypeDataTableList() {
		return loyaltyTypeDataTableList;
	}

	public void setLoyaltyTypeDataTableList(List<LoyaltyTypeDataTable> loyaltyTypeDataTableList) {
		this.loyaltyTypeDataTableList = loyaltyTypeDataTableList;
	}

	public List<LoyaltyTypeDataTable> getNewLoyaltyTypeDataTableList() {
		return newLoyaltyTypeDataTableList;
	}

	public void setNewLoyaltyTypeDataTableList(List<LoyaltyTypeDataTable> newLoyaltyTypeDataTableList) {
		this.newLoyaltyTypeDataTableList = newLoyaltyTypeDataTableList;
	}

	public List<LoyaltyType> getLoyaltyTypeList() {
		return loyaltyTypeList;
	}

	public void setLoyaltyTypeList(List<LoyaltyType> loyaltyTypeList) {
		this.loyaltyTypeList = loyaltyTypeList;
	}

	public List<LoyaltyTypeDesc> getLoyaltyTypeDescList() {
		return loyaltyTypeDescList;
	}

	public void setLoyaltyTypeDescList(List<LoyaltyTypeDesc> loyaltyTypeDescList) {
		this.loyaltyTypeDescList = loyaltyTypeDescList;
	}

	public BigDecimal getLoyaltyTypeArabicDescId() {
		return loyaltyTypeArabicDescId;
	}

	public void setLoyaltyTypeArabicDescId(BigDecimal loyaltyTypeArabicDescId) {
		this.loyaltyTypeArabicDescId = loyaltyTypeArabicDescId;
	}

	public LoyaltyTypeDataTable getLoyaltyTypeDataTable() {
		return loyaltyTypeDataTable;
	}

	public void setLoyaltyTypeDataTable(LoyaltyTypeDataTable loyaltyTypeDataTable) {
		this.loyaltyTypeDataTable = loyaltyTypeDataTable;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
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

	public Boolean getBooSubmitPanel() {
		return booSubmitPanel;
	}

	public void setBooSubmitPanel(Boolean booSubmitPanel) {
		this.booSubmitPanel = booSubmitPanel;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean checkDuplicate() {
		boolean isExist = false;
		if (loyaltyTypeDataTableList.size() > 0) {
			for (LoyaltyTypeDataTable dataTable : loyaltyTypeDataTableList) {
				if (dataTable.getLoyalityTypeCode().equalsIgnoreCase(getLoyalityTypeCode())) {

					isExist = true;
				} else {
					isExist = false;
				}
			}
		}
		return isExist;
	}

	public void addToDataTable() {
    
		try{
		LoyaltyTypeDataTable loyaltyTypeDataTable = new LoyaltyTypeDataTable();

		loyaltyTypeDataTable.setLoyalityTypeId(getLoyalityTypeId());
		loyaltyTypeDataTable.setLoyalityTypeDescId(getLoyalityTypeDescId());
		loyaltyTypeDataTable.setLoyaltyTypeArabicDescId(getLoyaltyTypeArabicDescId());

		loyaltyTypeDataTable.setApplicationCountryId(sessionStateManage.getCountryId());

		loyaltyTypeDataTable.setLoyalityTypeCode(getLoyalityTypeCode());
		loyaltyTypeDataTable.setCorporateCode(getCorporateCode());
		loyaltyTypeDataTable.setCorporatePoints(getCorporatePoints());
		loyaltyTypeDataTable.setEmployeePoints(getEmployeePoints());
		loyaltyTypeDataTable.setLevelNo(getLevelNo());
		loyaltyTypeDataTable.setLoyalityType(getLoyalityType());

		loyaltyTypeDataTable.setShortDescEnglish(getShortDescEnglish());
		loyaltyTypeDataTable.setFullDescEnglish(getFullDescEnglish());
		loyaltyTypeDataTable.setShortDescLocal(getShortDescLocal());
		loyaltyTypeDataTable.setFullDescLocal(getFullDescLocal());

		if (getLoyalityTypeId() != null) {
			
			if(loyaltyTypeDataTableList.size()!= 0 || getLoyaltyTypeDataTable() != null){
				loyaltyTypeDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
		    	  setBooEdit(true);		    	  
		    	  loyaltyTypeDataTable.setIsActive(Constants.U);
		    	  loyaltyTypeDataTable.setModifiedBy(sessionStateManage.getUserName());
		    	  loyaltyTypeDataTable.setModifiedDate(new Date());
			

			if (getLoyaltyTypeDataTable() != null) {
				if ((loyaltyTypeDataTable.getLoyalityTypeCode().equalsIgnoreCase(loyaltyTypeDataTable.getLoyalityTypeCode()) && loyaltyTypeDataTable.getFullDescEnglish().equalsIgnoreCase(getFullDescEnglish()) && loyaltyTypeDataTable.getShortDescEnglish().equalsIgnoreCase(getShortDescEnglish())
						&& loyaltyTypeDataTable.getFullDescLocal().equalsIgnoreCase(getFullDescLocal()) && loyaltyTypeDataTable.getShortDescLocal().equalsIgnoreCase(getShortDescLocal()))) {

					loyaltyTypeDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					loyaltyTypeDataTable.setIsActive(getIsActive());
					loyaltyTypeDataTable.setModifiedBy(getModifiedBy());
					loyaltyTypeDataTable.setModifiedDate(getModifiedDate());
					loyaltyTypeDataTable.setApprovedBy(getApprovedBy());
					loyaltyTypeDataTable.setApprovedDate(getApprovedDate());
					loyaltyTypeDataTable.setRemarks(getRemarks());

				} else {
					loyaltyTypeDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					// setBooEdit(true);
					loyaltyTypeDataTable.setIsActive(Constants.U);
					loyaltyTypeDataTable.setModifiedBy(sessionStateManage.getUserName());
					loyaltyTypeDataTable.setModifiedDate(new Date());
					loyaltyTypeDataTable.setApprovedBy(null);
					loyaltyTypeDataTable.setApprovedDate(null);
					loyaltyTypeDataTable.setRemarks(null);

				}
			}
		}else{
			

			loyaltyTypeDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
			loyaltyTypeDataTable.setIsActive(getIsActive());
			loyaltyTypeDataTable.setModifiedBy(getModifiedBy());
			loyaltyTypeDataTable.setModifiedDate(getModifiedDate());
			loyaltyTypeDataTable.setApprovedBy(getApprovedBy());
			loyaltyTypeDataTable.setApprovedDate(getApprovedDate());
			loyaltyTypeDataTable.setRemarks(getRemarks());        
	    
		}
		} else {
			loyaltyTypeDataTable.setDynamicLabelForActivateDeactivate("REMOVE");
			loyaltyTypeDataTable.setCreatedBy(sessionStateManage.getUserName());
			loyaltyTypeDataTable.setCreatedDate(new Date());
			loyaltyTypeDataTable.setIsActive(Constants.Yes);
		}

		if (checkDuplicate() == true) {
			RequestContext.getCurrentInstance().execute("datatable.show();");
			clear();
		} else {
			loyaltyTypeDataTableList.add(loyaltyTypeDataTable);
			setBooLoyaltyTypeDataTable(true);
			setBooLoyaltyTypeDetail(true);
			clear();
		}
		}catch(NullPointerException ne){
    		LOG.info("Method Name::addToDataTable"+ne.getMessage());
    		setErrorMessage("Method Name::addToDataTable"); 
    		RequestContext.getCurrentInstance().execute("nullPointerId.show();");
    		return; 
    	} catch (Exception exception) {
    		LOG.info("Method Name::addToDataTable"+exception.getMessage());
    		setErrorMessage(exception.getMessage());
    		RequestContext.getCurrentInstance().execute("exception.show();");
    		return;
    	}
	}

	public void save() {
		
       try{
		for (LoyaltyTypeDataTable loyaltyTypeDataTable : loyaltyTypeDataTableList) {

			LoyaltyType loyaltyType = new LoyaltyType();
			loyaltyType.setLoyalityTypeId(loyaltyTypeDataTable.getLoyalityTypeId());
			loyaltyType.setLoyalityTypeCode(loyaltyTypeDataTable.getLoyalityTypeCode());
			loyaltyType.setCorporateCode(loyaltyTypeDataTable.getCorporateCode());
			loyaltyType.setCorporatePoints(loyaltyTypeDataTable.getCorporatePoints());
			loyaltyType.setEmployeePoints(loyaltyTypeDataTable.getEmployeePoints());
			loyaltyType.setLevelNo(loyaltyTypeDataTable.getLevelNo());
			loyaltyType.setLoyalityType(loyaltyTypeDataTable.getLoyalityType());

			loyaltyType.setCreatedBy(loyaltyTypeDataTable.getCreatedBy());
			loyaltyType.setCreatedDate(loyaltyTypeDataTable.getCreatedDate());
			loyaltyType.setIsActive(loyaltyTypeDataTable.getIsActive());

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(loyaltyTypeDataTable.getApplicationCountryId());
			loyaltyType.setApplicationCountryId(countryMaster);

			LoyaltyTypeDesc englishDesc = new LoyaltyTypeDesc();
			LanguageType englishLanguageType = new LanguageType();
			englishLanguageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			englishDesc.setLanguageId(englishLanguageType);

			englishDesc.setLoyalityTypeId(loyaltyType);
			englishDesc.setLoyalityTypeDescId(loyaltyTypeDataTable.getLoyalityTypeDescId());
			englishDesc.setShortDescription(loyaltyTypeDataTable.getShortDescEnglish());
			englishDesc.setFullDescription(loyaltyTypeDataTable.getFullDescEnglish());

			LoyaltyTypeDesc localDesc = new LoyaltyTypeDesc();
			LanguageType localLanguageType = new LanguageType();
			localLanguageType.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			localDesc.setLanguageId(localLanguageType);

			localDesc.setLoyalityTypeId(loyaltyType);
			localDesc.setLoyalityTypeDescId(loyaltyTypeDataTable.getLoyaltyTypeArabicDescId());
			localDesc.setShortDescription(loyaltyTypeDataTable.getShortDescLocal());
			localDesc.setFullDescription(loyaltyTypeDataTable.getFullDescLocal());
			
			HashMap<String,Object> mapAllForSave = new HashMap<String,Object>();
			mapAllForSave.put("loyaltyType", loyaltyType);
			mapAllForSave.put("englishDesc", englishDesc);
			mapAllForSave.put("localDesc", localDesc);
			getLoyaltyTypeService().saveLoyalty(mapAllForSave);
			RequestContext.getCurrentInstance().execute("complete.show();");
			//saveAll(loyaltyType, englishDesc, localDesc);

		}
       }catch(NullPointerException ne){
    	   LOG.info("Method Name::save"+ne.getMessage());
    	   setErrorMessage("Method Name::save"); 
    	   RequestContext.getCurrentInstance().execute("nullPointerId.show();");
    	   return; 
       } catch (Exception exception) {
    	   LOG.info("Method Name::save"+exception.getMessage());
    	   setErrorMessage(exception.getMessage());
    	   RequestContext.getCurrentInstance().execute("exception.show();");
    	   return;
       }

	}

	private void saveAll(LoyaltyType loyaltyType, LoyaltyTypeDesc loyaltyTypeDescEnglish, LoyaltyTypeDesc loyaltyTypeDescLocal) {
		getLoyaltyTypeService().saveMaster(loyaltyType);
		getLoyaltyTypeService().saveMasterDescriptionEnglish(loyaltyTypeDescEnglish);
		getLoyaltyTypeService().saveMasterDescriptionLocal(loyaltyTypeDescLocal);
		RequestContext.getCurrentInstance().execute("complete.show();");
	}

	public void view() {

		loyaltyTypeDataTableList.clear();
		// clear();

		if (getLoyalityType() == null) {
			setBooLoyaltyTypeDataTable(false);
			setBooLoyaltyTypeDetail(true);
			clear();
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("viewSearch.show();");
			return;
		} else {
			try{
			loyaltyTypeList = getLoyaltyTypeService().displayList(getLoyalityType(), sessionStateManage.getCountryId());

			if (loyaltyTypeList != null) {

				for (LoyaltyType loyaltyType : loyaltyTypeList) {
					LoyaltyTypeDataTable loyaltyTypeDataTable = new LoyaltyTypeDataTable();

					loyaltyTypeDataTable.setLoyalityTypeId(loyaltyType.getLoyalityTypeId());
					loyaltyTypeDataTable.setApplicationCountryId(loyaltyType.getApplicationCountryId().getCountryId());
					loyaltyTypeDataTable.setLoyalityTypeCode(loyaltyType.getLoyalityTypeCode());
					loyaltyTypeDataTable.setLoyalityType(loyaltyType.getLoyalityType());
					loyaltyTypeDataTable.setCorporateCode(loyaltyType.getCorporateCode());
					loyaltyTypeDataTable.setCorporatePoints(loyaltyType.getCorporatePoints());
					loyaltyTypeDataTable.setEmployeePoints(loyaltyType.getEmployeePoints());
					loyaltyTypeDataTable.setLevelNo(loyaltyType.getLevelNo());
				//	loyaltyTypeDataTable.setIsActive(loyaltyType.getIsActive());
					loyaltyTypeDataTable.setCreatedBy(loyaltyType.getCreatedBy());
					loyaltyTypeDataTable.setCreatedDate(loyaltyType.getCreatedDate());
					loyaltyTypeDataTable.setModifiedBy(loyaltyType.getModifiedBy());
					loyaltyTypeDataTable.setModifiedDate(loyaltyType.getModifiedDate());
					//loyaltyTypeDataTable.setApprovedBy(loyaltyType.getApprovedBy());
					//loyaltyTypeDataTable.setApprovedDate(loyaltyType.getApprovedDate());
				//	loyaltyTypeDataTable.setRemarks(loyaltyType.getRemarks());
					
					if(loyaltyType.getIsActive() != null){
					if (loyaltyType.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						loyaltyTypeDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					} else if (loyaltyType.getIsActive().equalsIgnoreCase(Constants.D)) {
						loyaltyTypeDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					} else if (loyaltyType.getIsActive().equalsIgnoreCase(Constants.U) && loyaltyTypeDataTable.getModifiedBy() == null && loyaltyTypeDataTable.getModifiedDate() == null && loyaltyTypeDataTable.getApprovedBy() == null && loyaltyTypeDataTable.getApprovedDate() == null
							&& loyaltyTypeDataTable.getRemarks() == null) {
						loyaltyTypeDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					} else {
						setBooEdit(true);
						loyaltyTypeDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}
					}
					
					
					/*if (loyaltyType.getIsActive().equals(Constants.Yes)) {
						
						loyaltyTypeDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
						loyaltyTypeDataTable.setIsActive(loyaltyType.getIsActive());
					}else if(loyaltyType.getIsActive().equals(Constants.D))
					  {
					  loyaltyTypeDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					  loyaltyTypeDataTable.setIsActive(loyaltyType.getIsActive());
					  }*/
					 
					loyaltyTypeDescList = getLoyaltyTypeService().getDescriptionById(loyaltyType.getLoyalityTypeId());

					if (loyaltyTypeDescList != null) {
						for (LoyaltyTypeDesc loyaltyTypeDesc : loyaltyTypeDescList) {

							if (loyaltyTypeDesc.getLanguageId().getLanguageId() != null && loyaltyTypeDesc.getLanguageId().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))) {
								loyaltyTypeDataTable.setLoyalityTypeDescId(loyaltyTypeDesc.getLoyalityTypeDescId());
								loyaltyTypeDataTable.setShortDescEnglish(loyaltyTypeDesc.getShortDescription());
								loyaltyTypeDataTable.setFullDescEnglish(loyaltyTypeDesc.getFullDescription());

							} else if (loyaltyTypeDesc.getLanguageId().getLanguageId() != null && loyaltyTypeDesc.getLanguageId().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
								loyaltyTypeDataTable.setLoyaltyTypeArabicDescId(loyaltyTypeDesc.getLoyalityTypeDescId());
								loyaltyTypeDataTable.setShortDescLocal(loyaltyTypeDesc.getShortDescription());
								loyaltyTypeDataTable.setFullDescLocal(loyaltyTypeDesc.getFullDescription());
							}

						}
					}
					loyaltyTypeDataTableList.add(loyaltyTypeDataTable);
				}

				loyaltyTypeDataTableList.addAll(newLoyaltyTypeDataTableList);
				setBooLoyaltyTypeDataTable(true);
				setBooLoyaltyTypeDetail(true);
				clear();

			} else {
				setLoyalityType(null);
				RequestContext.getCurrentInstance().execute("noRecords.show();");
			}
			 }catch(NullPointerException ne){
		    	   LOG.info("Method Name::view"+ne.getMessage());
		    	   setErrorMessage("Method Name::view"); 
		    	   RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    	   return; 
		       } catch (Exception exception) {
		    	   LOG.info("Method Name::view"+exception.getMessage());
		    	   setErrorMessage(exception.getMessage());
		    	   RequestContext.getCurrentInstance().execute("exception.show();");
		    	   return;
		       }
		}
			

	}

	public void edit(LoyaltyTypeDataTable dataTable) {

		try{
		setLoyalityTypeCode(dataTable.getLoyalityTypeCode());
		setLoyalityType(dataTable.getLoyalityType());
		setCorporateCode(dataTable.getCorporateCode());
		setCorporatePoints(dataTable.getCorporatePoints());
		setEmployeePoints(dataTable.getEmployeePoints());
		setLevelNo(dataTable.getLevelNo());
		setShortDescEnglish(dataTable.getShortDescEnglish());
		setShortDescLocal(dataTable.getShortDescLocal());
		setFullDescEnglish(dataTable.getFullDescEnglish());
		setFullDescLocal(dataTable.getFullDescLocal());
		setLoyalityTypeId(dataTable.getLoyalityTypeId());
		setLoyalityTypeDescId(dataTable.getLoyalityTypeDescId());
		setLoyaltyTypeArabicDescId(dataTable.getLoyaltyTypeArabicDescId());
		setCreatedBy(dataTable.getCreatedBy());
		setCreatedDate(dataTable.getCreatedDate());
		setApplicationCountryId(dataTable.getApplicationCountryId());
		setIsActive(dataTable.getIsActive());

		loyaltyTypeDataTableList.remove(dataTable);
		 }catch(NullPointerException ne){
	    	   LOG.info("Method Name::edit"+ne.getMessage());
	    	   setErrorMessage("Method Name::edit"); 
	    	   RequestContext.getCurrentInstance().execute("nullPointerId.show();");
	    	   return; 
	       } catch (Exception exception) {
	    	   LOG.info("Method Name::edit"+exception.getMessage());
	    	   setErrorMessage(exception.getMessage());
	    	   RequestContext.getCurrentInstance().execute("exception.show();");
	    	   return;
	       }
	}

	public void checkStatusType(LoyaltyTypeDataTable dataTable) throws IOException {

		 if(dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){ 
			 setBooEdit(true);
		 RequestContext.getCurrentInstance().execute("pending.show();");
		 return; 
		 }else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {

			dataTable.setRemarksCheck(true);
			setRemarks(null);
			setApprovedBy(dataTable.getCreatedBy());
			setApprovedDate(dataTable.getCreatedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
			return;
		} /*
		 * else if
		 * (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase
		 * (Constants.DELETE)) { dataTable.setBooCheckDelete(true);
		 * RequestContext
		 * .getCurrentInstance().execute("permanentDelete.show();"); return; }
		 */else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			dataTable.setActivateRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;
		} else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			loyaltyTypeDataTableList.remove(dataTable);
			loyaltyTypeDataTableList.clear();

		}
	}

	public void activateRecord() {

		if (loyaltyTypeDataTableList.size() > 0) {
			for (LoyaltyTypeDataTable dataTable : loyaltyTypeDataTableList) {
				if (dataTable.getActivateRecordCheck() != null) {
					if (dataTable.getActivateRecordCheck().equals(true)) {
						try{
						conformToDeActivte(dataTable);
						 }catch(NullPointerException ne){
					    	   LOG.info("Method Name::activateRecord"+ne.getMessage());
					    	   setErrorMessage("Method Name::activateRecord"); 
					    	   RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					    	   return; 
					       } catch (Exception exception) {
					    	   LOG.info("Method Name::activateRecord"+exception.getMessage());
					    	   setErrorMessage(exception.getMessage());
					    	   RequestContext.getCurrentInstance().execute("exception.show();");
					    	   return;
					       }
					}
				}
			}
		}

	}

	public void conformToDeActivte(LoyaltyTypeDataTable datatable) {
		getLoyaltyTypeService().activate(datatable.getLoyalityTypeId(), sessionStateManage.getUserName());
		RequestContext.getCurrentInstance().execute("update.show();");
	}

	public void remarkSelectedRecord() throws IOException {

		for (LoyaltyTypeDataTable dataTable : loyaltyTypeDataTableList) {
			if (dataTable.getRemarksCheck().equals(true)) {
				if (!getRemarks().equals("")) {
					dataTable.setRemarks(getRemarks());
					dataTable.setApprovedBy(null);
					dataTable.setApprovedDate(null);
					dataTable.setRemarksCheck(true);
					update(dataTable);
					loyaltyTypeDataTableList.clear();

				} else {
					RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
					return;

				}
			}

		}

	}

	public void confirmPermanentDelete() {
		if (loyaltyTypeDataTableList.size() > 0) {
			for (LoyaltyTypeDataTable dataTable : loyaltyTypeDataTableList) {
				if (dataTable.getBooCheckDelete() != null) {
					if (dataTable.getBooCheckDelete().equals(true)) {

						delete(dataTable);

					}
				}
			}
		}

	}

	public void delete(LoyaltyTypeDataTable dataTable) {
 
		try {
		getLoyaltyTypeService().deleteLoyaltyType(dataTable.getLoyalityTypeId(), dataTable.getLoyalityTypeDescId(), dataTable.getLoyaltyTypeArabicDescId());
	
			clear();
			setBooLoyaltyTypeDataTable(false);
			setBooLoyaltyTypeDetail(true);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/loyaltytype.xhtml");
		/*} catch (Exception e) {
			e.printStackTrace();
		}*/
		}catch(NullPointerException ne){
	    	   LOG.info("Method Name::delete"+ne.getMessage());
	    	   setErrorMessage("Method Name::delete"); 
	    	   RequestContext.getCurrentInstance().execute("nullPointerId.show();");
	    	   return; 
	       } catch (Exception exception) {
	    	   LOG.info("Method Name::delete"+exception.getMessage());
	    	   setErrorMessage(exception.getMessage());
	    	   RequestContext.getCurrentInstance().execute("exception.show();");
	    	   return;
	       }
		// RequestContext.getCurrentInstance().execute("update.show();");
	}

	public void update(LoyaltyTypeDataTable dataTable) throws IOException {

		try {

			LoyaltyType loyaltyType = new LoyaltyType();

			loyaltyType.setLoyalityTypeId(dataTable.getLoyalityTypeId());
			loyaltyType.setLoyalityTypeCode(dataTable.getLoyalityTypeCode());
			loyaltyType.setLoyalityType(dataTable.getLoyalityType());
			loyaltyType.setCorporateCode(dataTable.getCorporateCode());
			loyaltyType.setCorporatePoints(dataTable.getCorporatePoints());
			loyaltyType.setEmployeePoints(dataTable.getEmployeePoints());
			loyaltyType.setLevelNo(dataTable.getLevelNo());
			
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(dataTable.getApplicationCountryId());
			loyaltyType.setApplicationCountryId(countryMaster);


			loyaltyType.setModifiedBy(sessionStateManage.getUserName());
			loyaltyType.setModifiedDate(new Date());
			loyaltyType.setApprovedBy(null);
			loyaltyType.setApprovedDate(null);
			loyaltyType.setRemarks(dataTable.getRemarks());
			loyaltyType.setIsActive(Constants.D);
			loyaltyType.setCreatedBy(dataTable.getCreatedBy());
			loyaltyType.setCreatedDate(dataTable.getCreatedDate());

			getLoyaltyTypeService().saveMaster(loyaltyType);

			RequestContext.getCurrentInstance().execute("update.show();");

		/*} catch (Exception e) {
			e.printStackTrace();
		}*/
		}catch(NullPointerException ne){
	    	   LOG.info("Method Name::update"+ne.getMessage());
	    	   setErrorMessage("Method Name::update"); 
	    	   RequestContext.getCurrentInstance().execute("nullPointerId.show();");
	    	   return; 
	       } catch (Exception exception) {
	    	   LOG.info("Method Name::update"+exception.getMessage());
	    	   setErrorMessage(exception.getMessage());
	    	   RequestContext.getCurrentInstance().execute("exception.show();");
	    	   return;
	       }
	}

	public List<String> autoComplete(String query) {
		if (query.length() > 0) {
			return getLoyaltyTypeService().autoCompleteList(query);
		} else {
			return null;
		}
	}

	public void autoCompletePopulate() {

		loyaltyTypeList = getLoyaltyTypeService().displayListByCode(getLoyalityTypeCode());

		if (loyaltyTypeList.size() > 0) {
			setLoyalityTypeCode(null);
			RequestContext.getCurrentInstance().execute("codeExist.show();");
		}/*
		 * else{
		 * 
		 * clear(); }
		 */

	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void loyaltyTypeNavigation() {

		try {
			clear();
			loyaltyTypeDataTableList.clear();
			setBooLoyaltyTypeDataTable(false);
			setBooLoyaltyTypeDetail(true);
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "loyaltytype.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/loyaltytype.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exit() throws IOException {
		clear();
		loyaltyTypeDataTableList.clear();
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void clear() {
		setCorporateCode(null);
		setCorporatePoints(null);
		setEmployeePoints(null);
		setLevelNo(null);
		setLoyalityType(null);
		setLoyalityTypeCode(null);
		setShortDescEnglish(null);
		setFullDescEnglish(null);
		setShortDescLocal(null);
		setFullDescLocal(null);
		setLoyalityTypeDescId(null);
		setLoyalityTypeId(null);
		setLoyaltyTypeArabicDescId(null);
		setApplicationCountryId(null);
		setRemarks(null);
		

	}

}
