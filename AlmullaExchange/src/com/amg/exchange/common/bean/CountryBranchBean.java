package com.amg.exchange.common.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.amg.exchange.common.service.ICountryBranchService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("countryBranchBean")
@Scope("session")
public class CountryBranchBean<T> implements Serializable {

	/**
	   * 
	   */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(CountryBranchBean.class);
	private BigDecimal countryBranchId;
	private BigDecimal applicationCountryId;
	
	private String branchName;
	private BigDecimal accountCode;
	private String corporateStatus;
	private String corporateStatusName;
	private BigDecimal branchCode;
	
	private String headOfficeIndicator;
	private String headOfficeIndicatorName;
	private BigDecimal telephoneNumber;
	private String emailId;
	private BigDecimal areaCode;
	private BigDecimal ecNumeber;
	private String ipAddress;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks="";

	private Boolean booEdit;
	private Boolean isdisable;
	private Boolean btnClear;
	private Boolean disableSubmit;

	private Boolean booCountryBranchDetail;
	private Boolean booCountryBranchDataTable;
	private String wuAccCode;

	  private Boolean ifEditClicked=false;

	private String dynamicLabelForActivateDeactivate;
	SessionStateManage sessionStateManage = new SessionStateManage();

	private List<CountryBranchDataTable> lstOfCOuntryBranchNewDataTables = new CopyOnWriteArrayList<CountryBranchDataTable>();
	private List<CountryBranchDataTable> lstOfCOuntryBranchDataTables = new CopyOnWriteArrayList<CountryBranchDataTable>();

	private CountryBranchDataTable countryBranchDataTable = null;

	private List<CountryBranch> lstOfCountryBranchs = new ArrayList<CountryBranch>();
	
	@Autowired
	ICountryBranchService<T> countryBranchService;
	
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	private Boolean booVisible;

	public Boolean getBooVisible() {
		return booVisible;
	}

	public void setBooVisible(Boolean booVisible) {
		this.booVisible = booVisible;
	}
	
	
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public BigDecimal getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(BigDecimal accountCode) {
		this.accountCode = accountCode;
	}

	public String getCorporateStatus() {
		return corporateStatus;
	}

	public void setCorporateStatus(String corporateStatus) {
		this.corporateStatus = corporateStatus;
	}

	

	public BigDecimal getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}

	public String getHeadOfficeIndicator() {
		return headOfficeIndicator;
	}

	public void setHeadOfficeIndicator(String headOfficeIndicator) {
		this.headOfficeIndicator = headOfficeIndicator;
	}

	public BigDecimal getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(BigDecimal telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public BigDecimal getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(BigDecimal areaCode) {
		this.areaCode = areaCode;
	}

	public BigDecimal getEcNumeber() {
		return ecNumeber;
	}

	public void setEcNumeber(BigDecimal ecNumeber) {
		this.ecNumeber = ecNumeber;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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

	
	public String getCorporateStatusName() {
		return corporateStatusName;
	}

	public void setCorporateStatusName(String corporateStatusName) {
		this.corporateStatusName = corporateStatusName;
	}

	public String getHeadOfficeIndicatorName() {
		return headOfficeIndicatorName;
	}

	public void setHeadOfficeIndicatorName(String headOfficeIndicatorName) {
		this.headOfficeIndicatorName = headOfficeIndicatorName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public Boolean getBooCountryBranchDetail() {
		return booCountryBranchDetail;
	}

	public void setBooCountryBranchDetail(Boolean booCountryBranchDetail) {
		this.booCountryBranchDetail = booCountryBranchDetail;
	}

	public Boolean getBooCountryBranchDataTable() {
		return booCountryBranchDataTable;
	}

	public void setBooCountryBranchDataTable(Boolean booCountryBranchDataTable) {
		this.booCountryBranchDataTable = booCountryBranchDataTable;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public List<CountryBranchDataTable> getLstOfCOuntryBranchNewDataTables() {
		return lstOfCOuntryBranchNewDataTables;
	}

	public void setLstOfCOuntryBranchNewDataTables(List<CountryBranchDataTable> lstOfCOuntryBranchNewDataTables) {
		this.lstOfCOuntryBranchNewDataTables = lstOfCOuntryBranchNewDataTables;
	}

	public List<CountryBranchDataTable> getLstOfCOuntryBranchDataTables() {
		return lstOfCOuntryBranchDataTables;
	}

	public void setLstOfCOuntryBranchDataTables(List<CountryBranchDataTable> lstOfCOuntryBranchDataTables) {
		this.lstOfCOuntryBranchDataTables = lstOfCOuntryBranchDataTables;
	}

	public CountryBranchDataTable getCountryBranchDataTable() {
		return countryBranchDataTable;
	}

	public void setCountryBranchDataTable(CountryBranchDataTable countryBranchDataTable) {
		this.countryBranchDataTable = countryBranchDataTable;
	}

	public List<CountryBranch> getLstOfCountryBranchs() {
		return lstOfCountryBranchs;
	}

	public void setLstOfCountryBranchs(List<CountryBranch> lstOfCountryBranchs) {
		this.lstOfCountryBranchs = lstOfCountryBranchs;
	}
	


	public ICountryBranchService<T> getCountryBranchService() {
		return countryBranchService;
	}

	public void setCountryBranchService(ICountryBranchService<T> countryBranchService) {
		this.countryBranchService = countryBranchService;
	}

	public Boolean getDisableSubmit() {
		return disableSubmit;
	}

	public void setDisableSubmit(Boolean disableSubmit) {
		this.disableSubmit = disableSubmit;
	}
	
	

	public Boolean getIfEditClicked() {
		return ifEditClicked;
	}

	public void setIfEditClicked(Boolean ifEditClicked) {
		this.ifEditClicked = ifEditClicked;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void countryBranchNavigation() {
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "CountryBranchMaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/CountryBranchMaster.xhtml");
			setBooCountryBranchDetail(true);
			setBooCountryBranchDataTable(false);
			setClearPanel(false);
			setBooEdit(false);
			lstOfCOuntryBranchDataTables.clear();
			clearAll();
			lstOfCOuntryBranchNewDataTables.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void countryBranchNavigationApproval() {
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "CountryBranchMasterApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/CountryBranchMasterApproval.xhtml");
			setBooCountryBranchDetail(false);
			setBooCountryBranchDataTable(true);
			approveViewCountryBranchMethod();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void duplicateChekingComplaintRemarksCode() {
		try {
			setBooVisible(false);
			if (lstOfCOuntryBranchDataTables.size() > 0) {
				for (CountryBranchDataTable countryBranchDataTable : lstOfCOuntryBranchDataTables) {
					if (getBranchCode() != null) {
						if (countryBranchDataTable.getBranchCode().compareTo(getBranchCode()) == 0) {
							RequestContext.getCurrentInstance().execute("datatable.show();");
							clearAll();
							return;
						}
					}
				}
			}
			if (getBranchCode() != null) {
				addRecordsToDataTable();
			}
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}
	
	public void addRecordsToDataTable() {
		  setClearPanel(false);
		 setBooEdit(false);
		 setSubmitPanel(false);
		CountryBranchDataTable countryBranchDataTable = new CountryBranchDataTable();
		
		countryBranchDataTable.setBranchCode(getBranchCode());
		countryBranchDataTable.setBranchName(getBranchName());
		countryBranchDataTable.setAccountCode(getAccountCode());
		countryBranchDataTable.setCorporateStatus(getCorporateStatus());
		countryBranchDataTable.setHeadOfficeIndicator(getHeadOfficeIndicator());
		countryBranchDataTable.setTelephoneNumber(getTelephoneNumber());
		countryBranchDataTable.setEmailId(getEmailId());
		countryBranchDataTable.setAreaCode(getAreaCode());
		countryBranchDataTable.setEcNumeber(getEcNumeber());
		countryBranchDataTable.setIpAddress(getIpAddress());
		countryBranchDataTable.setCountryBranchId(getCountryBranchId());
		countryBranchDataTable.setApplicationCountryId(sessionStateManage.getCountryId());
		countryBranchDataTable.setRenderEditButton(true);
		countryBranchDataTable.setWuAccCode(getWuAccCode());
		if (getCorporateStatus() != null) {
			if (getCorporateStatus().equals(Constants.Yes)) {
				countryBranchDataTable.setCorporateStatus(Constants.Yes);
				countryBranchDataTable.setCorporateStatusName(Constants.YES);
			} else if (getCorporateStatus().equals(Constants.No)) {
				countryBranchDataTable.setCorporateStatus(Constants.No);
				countryBranchDataTable.setCorporateStatusName(Constants.NO);
			}
		}

		if (getHeadOfficeIndicator() != null) {
			if (getHeadOfficeIndicator().equals(Constants.INDICATOR_1)) {
				countryBranchDataTable.setHeadOfficeIndicator(Constants.INDICATOR_1);
				countryBranchDataTable.setHeadOfficeIndicatorName(Constants.YES);
			} else if (getHeadOfficeIndicator().equals(Constants.INDICATOR_0)) {
				countryBranchDataTable.setHeadOfficeIndicator(Constants.INDICATOR_0);
				countryBranchDataTable.setHeadOfficeIndicatorName(Constants.NO);
			}
		}
		
		
		if(getScanIndicatorOrType() != null){
		    if (getScanIndicatorOrType().equalsIgnoreCase("A")) {
			      countryBranchDataTable.setScanIndicatorOrType("ArchmetSystem");
		    } else if (getScanIndicatorOrType().equalsIgnoreCase("D")) {
			      countryBranchDataTable.setScanIndicatorOrType("DbScan");
		    } else {
			      countryBranchDataTable.setScanIndicatorOrType("NoScan");
		    }
		}
		
		if(getDigitalSignTypeInd() !=null){

			if(getDigitalSignTypeInd().equalsIgnoreCase(Constants.Yes)){
				countryBranchDataTable.setDigitalSignTypeInd("YES");
			}else if (getDigitalSignTypeInd().equalsIgnoreCase(Constants.No)) {
				countryBranchDataTable.setDigitalSignTypeInd("NO");
			}
		}

		countryBranchDataTable.setCreatedBy(getCreatedBy());
		countryBranchDataTable.setCreatedDate(getCreatedDate());

		if (getCountryBranchId() != null) {
			if(getCountryBranchDTOBJ() != null){
				  if(countryBranchDataTable.getBranchCode().equals(countryBranchDTOBJ.getBranchCode()) && countryBranchDataTable.getBranchName().equalsIgnoreCase(countryBranchDTOBJ.getBranchName())
						      && countryBranchDataTable.getAccountCode().equals(countryBranchDTOBJ.getAccountCode())  && countryBranchDataTable.getCorporateStatus().equalsIgnoreCase(countryBranchDTOBJ.getCorporateStatus())
						      && countryBranchDataTable.getHeadOfficeIndicator().equalsIgnoreCase(countryBranchDTOBJ.getHeadOfficeIndicator())  && countryBranchDataTable.getTelephoneNumber().equals(countryBranchDTOBJ.getTelephoneNumber())
						      && countryBranchDataTable.getEmailId().equalsIgnoreCase(countryBranchDTOBJ.getEmailId())  && countryBranchDataTable.getAreaCode().equals(countryBranchDTOBJ.getAreaCode())
						      && countryBranchDataTable.getEcNumeber().equals(countryBranchDTOBJ.getEcNumeber())  && countryBranchDataTable.getIpAddress().equalsIgnoreCase(countryBranchDTOBJ.getIpAddress())
						      && countryBranchDataTable.getScanIndicatorOrType().equalsIgnoreCase(countryBranchDTOBJ.getScanIndicatorOrType()) && countryBranchDataTable.getDigitalSignTypeInd().equalsIgnoreCase(countryBranchDTOBJ.getDigitalSignTypeInd())
						      && countryBranchDataTable.getWuAccCode().equalsIgnoreCase(countryBranchDTOBJ.getWuAccCode())){
					    countryBranchDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					    countryBranchDataTable.setIsActive(getIsActive());
					    countryBranchDataTable.setModifiedBy(getModifiedBy());
					    countryBranchDataTable.setModifiedDate(getModifiedDate());
					    countryBranchDataTable.setApprovedBy(getApprovedBy());
					    countryBranchDataTable.setApprovedDate(getApprovedDate());
					    countryBranchDataTable.setRemarks(getRemarks());
				  }else{
					    countryBranchDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					    countryBranchDataTable.setIsActive(Constants.U);
					    countryBranchDataTable.setModifiedBy(sessionStateManage.getUserName());
					    countryBranchDataTable.setModifiedDate(new Date());
					    countryBranchDataTable.setApprovedBy(null);
					    countryBranchDataTable.setApprovedDate(null);
					    countryBranchDataTable.setRemarks(null);
					    countryBranchDataTable.setIfEditClicked(true);    
				  }
			}
			/*if(getIfEditClicked().equals(true)){
				 if((complaintAssignedDTObj.getComplaintAssignedCode().equalsIgnoreCase(complaintDtObj.getComplaintAssignedCode())
										&&complaintAssignedDTObj.getComplaintEnglishFullDescription().equalsIgnoreCase(complaintDtObj.getComplaintEnglishFullDescription())
										&&complaintAssignedDTObj.getComplaintEnglishShortDescription().equalsIgnoreCase(complaintDtObj.getComplaintEnglishShortDescription())
										&&complaintAssignedDTObj.getComplaintArabicFullDescription().equalsIgnoreCase(complaintDtObj.getComplaintArabicFullDescription())
										&&complaintAssignedDTObj.getComplaintArabicShortDescription().equalsIgnoreCase(complaintDtObj.getComplaintArabicShortDescription())
										&&complaintAssignedDTObj.getLogCompalint().equalsIgnoreCase(complaintDtObj.getLogCompalint()))){
							   complaintAssignedDTObj.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
							      complaintAssignedDTObj.setIsActive(getIsActive());
							      complaintAssignedDTObj.setModifiedBy(getModifiedBy());
							      complaintAssignedDTObj.setModifiedDate(getModifiedDate());
							      complaintAssignedDTObj.setApprovedBy(getApprovedBy());
							      complaintAssignedDTObj.setApprovedDate(getApprovedDate());
							      complaintAssignedDTObj.setRemarks(getRemarks());
					    
					  
				}else{
					      complaintAssignedDTObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					      complaintAssignedDTObj.setIsActive(Constants.U);
					      complaintAssignedDTObj.setModifiedBy(sessionStateManage.getUserName());
					      complaintAssignedDTObj.setModifiedDate(new Date());
					      complaintAssignedDTObj.setApprovedBy(null);
					      complaintAssignedDTObj.setApprovedDate(null);
					      complaintAssignedDTObj.setRemarks(null);
					      complaintAssignedDTObj.setIfEditClicked(true);	 
				}
				
			}*/
			/*if (lstOfCOuntryBranchNewDataTables.size() == 0 && (lstOfCOuntryBranchDataTables.size() != 0 || getCountryBranchDataTable() != null)) {
				countryBranchDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);

				countryBranchDataTable.setIsActive(Constants.U);
				countryBranchDataTable.setModifiedBy(sessionStateManage.getUserName());
				countryBranchDataTable.setModifiedDate(new Date());
				countryBranchDataTable.setApprovedBy(null);
				countryBranchDataTable.setApprovedDate(null);
				countryBranchDataTable.setRemarks(null);

				if (getCountryBranchDataTable() != null) {
					if ((countryBranchDataTable.getBranchCode().compareTo(getBranchCode())) == 0 && countryBranchDataTable.getCorporateStatus().equals(getCorporateStatus())) {

						countryBranchDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
						countryBranchDataTable.setIsActive(getIsActive());
						countryBranchDataTable.setModifiedBy(getModifiedBy());
						countryBranchDataTable.setModifiedDate(getModifiedDate());
						countryBranchDataTable.setApprovedBy(getApprovedBy());
						countryBranchDataTable.setApprovedDate(getApprovedDate());
						countryBranchDataTable.setRemarks(getRemarks());

					} else {
						countryBranchDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);

						countryBranchDataTable.setIsActive(Constants.U);
						countryBranchDataTable.setModifiedBy(sessionStateManage.getUserName());
						countryBranchDataTable.setModifiedDate(new Date());
						countryBranchDataTable.setApprovedBy(null);
						countryBranchDataTable.setApprovedDate(null);
						countryBranchDataTable.setRemarks(null);

					}
				}
			} else {
				countryBranchDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
				countryBranchDataTable.setIsActive(getIsActive());
				countryBranchDataTable.setModifiedBy(getModifiedBy());
				countryBranchDataTable.setModifiedDate(getModifiedDate());
				countryBranchDataTable.setApprovedBy(getApprovedBy());
				countryBranchDataTable.setApprovedDate(getApprovedDate());
				countryBranchDataTable.setRemarks(getRemarks());
			}*/
		} else {
			countryBranchDataTable.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
			countryBranchDataTable.setIsActive(Constants.U);
			countryBranchDataTable.setCreatedBy(sessionStateManage.getUserName());
			countryBranchDataTable.setCreatedDate(new Date());
			countryBranchDataTable.setIfEditClicked(true);
		}
		lstOfCOuntryBranchDataTables.add(countryBranchDataTable);

		if (getCountryBranchId() == null) {
			lstOfCOuntryBranchNewDataTables.add(countryBranchDataTable);
		}
		setBooCountryBranchDataTable(true);
		setBooCountryBranchDetail(true);
		clearAll();
	}
	
	public void approveViewCountryBranchMethod() {
		lstOfCOuntryBranchDataTables.clear();
		clearAll();
		lstOfCountryBranchs= countryBranchService.displayCountryBranchForApprove(sessionStateManage.getCountryId());
		if(lstOfCountryBranchs != null){
			setBooCountryBranchDataTable(true);
			setBooCountryBranchDetail(false);
		  for (CountryBranch countryBranch : lstOfCountryBranchs) {				  
			  CountryBranchDataTable countryBranchDataTable = new CountryBranchDataTable();
			  countryBranchDataTable.setBranchCode(countryBranch.getBranchId());
				countryBranchDataTable.setBranchName(countryBranch.getBranchName());
				countryBranchDataTable.setAccountCode(countryBranch.getAccountCode());
				countryBranchDataTable.setCorporateStatus(countryBranch.getCorporateStatus());
				countryBranchDataTable.setHeadOfficeIndicator(countryBranch.getHeadOfficeIndicator());
				countryBranchDataTable.setTelephoneNumber(countryBranch.getTelephoneNumber());
				countryBranchDataTable.setEmailId(countryBranch.getEmailId());
				countryBranchDataTable.setAreaCode(countryBranch.getAreaCode());
				countryBranchDataTable.setEcNumeber(countryBranch.getEcNumeber());
				countryBranchDataTable.setIpAddress(countryBranch.getIpAddress());
				countryBranchDataTable.setCountryBranchId(countryBranch.getCountryBranchId());	
				countryBranchDataTable.setCreatedBy(countryBranch.getCreatedBy());
				countryBranchDataTable.setCreatedDate(countryBranch.getCreatedDate());
				countryBranchDataTable.setWuAccCode(countryBranch.getWuAccountCode());
				if (countryBranch.getCorporateStatus() != null) {
					if (countryBranch.getCorporateStatus().equals(Constants.Yes)) {
						countryBranchDataTable.setCorporateStatus(Constants.Yes);
						countryBranchDataTable.setCorporateStatusName(Constants.YES);
					} else if (countryBranch.getCorporateStatus().equals(Constants.No)) {
						countryBranchDataTable.setCorporateStatus(Constants.No);
						countryBranchDataTable.setCorporateStatusName(Constants.NO);
					}
				}
				
				if (countryBranch.getHeadOfficeIndicator() != null) {
					if (countryBranch.getHeadOfficeIndicator().equals(Constants.INDICATOR_1)) {
						countryBranchDataTable.setHeadOfficeIndicator(Constants.INDICATOR_1);
						countryBranchDataTable.setHeadOfficeIndicatorName(Constants.YES);
					} else if (countryBranch.getHeadOfficeIndicator().equals(Constants.INDICATOR_0)) {
						countryBranchDataTable.setHeadOfficeIndicator(Constants.INDICATOR_0);
						countryBranchDataTable.setHeadOfficeIndicatorName(Constants.NO);
					}
				}
				if (countryBranch.getScanInd() != null) {
					  if (countryBranch.getScanInd().equalsIgnoreCase("A")) {
						    countryBranchDataTable.setScanIndicatorOrType("ArchmetSystem");
					  } else if (countryBranch.getScanInd().equalsIgnoreCase("D")) {
						    countryBranchDataTable.setScanIndicatorOrType("Dbscan");
					  } else {
						    countryBranchDataTable.setScanIndicatorOrType("Noscan");
					  }
				}
				
				if(countryBranch.getDigitalSignInd() !=null){

					if(countryBranch.getDigitalSignInd().equalsIgnoreCase(Constants.Yes)){
						countryBranchDataTable.setDigitalSignTypeInd("Yes");
					}else if (countryBranch.getDigitalSignInd().equalsIgnoreCase(Constants.No)) {
						countryBranchDataTable.setDigitalSignTypeInd("No");
					}
				}
				
			 lstOfCOuntryBranchDataTables.add(countryBranchDataTable);
		  }
		  
		 // lstOfCOuntryBranchDataTables.addAll(lstOfCOuntryBranchNewDataTables);
		  
	  
			
		}else{
			RequestContext.getCurrentInstance().execute("noRecords.show();");
		}
	}
	
	public void editCounForApproveMethod(CountryBranchDataTable countryBranchDataTable){
		setBooVisible(false);
		try {
			if(countryBranchDataTable.getCreatedBy()!= null && (countryBranchDataTable.getModifiedBy()==null?countryBranchDataTable.getCreatedBy():countryBranchDataTable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName()))
			{
				RequestContext.getCurrentInstance().execute("notValidUser.show();");
				return;
			}else{

				setBooCountryBranchDataTable(false);
				setBooCountryBranchDetail(true);

				setBranchCode(countryBranchDataTable.getBranchCode());
				setBranchName(countryBranchDataTable.getBranchName());
				setAccountCode(countryBranchDataTable.getAccountCode());
				setCorporateStatus(countryBranchDataTable.getCorporateStatus());
				setCorporateStatusName(countryBranchDataTable.getCorporateStatusName());
				setAreaCode(countryBranchDataTable.getAreaCode());
				setHeadOfficeIndicator(countryBranchDataTable.getHeadOfficeIndicator());
				setHeadOfficeIndicatorName(countryBranchDataTable.getHeadOfficeIndicatorName());
				setTelephoneNumber(countryBranchDataTable.getTelephoneNumber());
				setEmailId(countryBranchDataTable.getEmailId());
				setEcNumeber(countryBranchDataTable.getEcNumeber());
				setIpAddress(countryBranchDataTable.getIpAddress());
				setCountryBranchId(countryBranchDataTable.getCountryBranchId());
				if(countryBranchDataTable.getScanIndicatorOrType() != null){
					if(countryBranchDataTable.getScanIndicatorOrType().equalsIgnoreCase("ArchmetSystem")){ 
						setScanIndicatorOrType("A");
					}else if (countryBranchDataTable.getScanIndicatorOrType().equalsIgnoreCase("Dbscan")) {
						setScanIndicatorOrType("D");
					}else {
						setScanIndicatorOrType("N");
					}
				}

				if(countryBranchDataTable.getDigitalSignTypeInd() != null){
					if(countryBranchDataTable.getDigitalSignTypeInd().equalsIgnoreCase("YES")){
						setDigitalSignTypeInd(Constants.Yes);
					}else if (countryBranchDataTable.getDigitalSignTypeInd().equalsIgnoreCase("NO")) {
						setDigitalSignTypeInd("N");
					}
				}

				setDynamicLabelForActivateDeactivate(countryBranchDataTable.getDynamicLabelForActivateDeactivate());
				setIsActive(countryBranchDataTable.getIsActive());
				setWuAccCode(countryBranchDataTable.getWuAccCode());
			}
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred " + e);
			return;
		}

	}
	
	public void approveCountryBranchMethod() {
		/*try {
			getCountryBranchService().approveRecord(getCountryBranchId(), sessionStateManage.getUserName(), Constants.Yes);
			RequestContext.getCurrentInstance().execute("complete.show();");
		} catch (Exception e) {
			System.out.println(e);
		}*/
		setBooVisible(false);
		 try {
			String approvalMsg = countryBranchService.checkCountryBranchApproveMultiUser(getCountryBranchId(), sessionStateManage.getUserName());
			    if (approvalMsg.equalsIgnoreCase("Success")) {
				      RequestContext.getCurrentInstance().execute("complete.show();");
			    } else {
				      RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				      return;
			    }
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred " + e);
			return;
		}

	}
	
	public void approveOk(){

		try {
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/CountryBranchMasterApproval.xhtml");
			setBooCountryBranchDataTable(true);
			setBooCountryBranchDetail(false);
			approveViewCountryBranchMethod();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public void countryBranchApprovedByOhterPerson() {
		  approveViewCountryBranchMethod();
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../common/CountryBranchMasterApproval.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }
	  }
	
	 public void countryBranchCancel() {
		   setBooCountryBranchDataTable(true);
			setBooCountryBranchDetail(false);
		   approveViewCountryBranchMethod();
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../common/CountryBranchMasterApproval.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }
	  }
	
	
	public void viewCountryBranchMethod() {	
		 try {
			lstOfCOuntryBranchDataTables.clear();
			clearAll();
			

			lstOfCountryBranchs= countryBranchService.displayAllCountryBranchToView(sessionStateManage.getCountryId());
			  
			  if(lstOfCountryBranchs != null){
					setBooCountryBranchDataTable(true);
					setBooCountryBranchDetail(true);
					setSubmitPanel(false);
					
				  for (CountryBranch countryBranch : lstOfCountryBranchs) {				  
					  CountryBranchDataTable countryBranchDataTable = new CountryBranchDataTable();
					 
					  countryBranchDataTable.setBranchCode(countryBranch.getBranchId());
					  countryBranchDataTable.setApplicationCountryId(countryBranch.getCountryMaster().getCountryId());
						countryBranchDataTable.setBranchName(countryBranch.getBranchName());
						countryBranchDataTable.setAccountCode(countryBranch.getAccountCode());
						countryBranchDataTable.setCorporateStatus(countryBranch.getCorporateStatus());
						countryBranchDataTable.setHeadOfficeIndicator(countryBranch.getHeadOfficeIndicator());
						countryBranchDataTable.setTelephoneNumber(countryBranch.getTelephoneNumber());
						countryBranchDataTable.setEmailId(countryBranch.getEmailId());
						countryBranchDataTable.setAreaCode(countryBranch.getAreaCode());
						countryBranchDataTable.setEcNumeber(countryBranch.getEcNumeber());
						countryBranchDataTable.setIpAddress(countryBranch.getIpAddress());
						countryBranchDataTable.setCountryBranchId(countryBranch.getCountryBranchId());	
						countryBranchDataTable.setCreatedBy(countryBranch.getCreatedBy());
						countryBranchDataTable.setCreatedDate(countryBranch.getCreatedDate());	
						countryBranchDataTable.setModifiedBy(countryBranch.getModifiedBy());
						countryBranchDataTable.setModifiedDate(countryBranch.getModifiedDate());	
						countryBranchDataTable.setApprovedBy(countryBranch.getApprovedBy());
						countryBranchDataTable.setApprovedDate(countryBranch.getApprovedDate());	
						countryBranchDataTable.setWuAccCode(countryBranch.getWuAccountCode());
						
						
						if (countryBranch.getCorporateStatus() != null) {
							if (countryBranch.getCorporateStatus().equals(Constants.Yes)) {
								countryBranchDataTable.setCorporateStatus(Constants.Yes);
								countryBranchDataTable.setCorporateStatusName(Constants.YES);
							} else if (countryBranch.getCorporateStatus().equals(Constants.No)) {
								countryBranchDataTable.setCorporateStatus(Constants.No);
								countryBranchDataTable.setCorporateStatusName(Constants.NO);
							}
						}
						
						if (countryBranch.getHeadOfficeIndicator() != null) {
							if (countryBranch.getHeadOfficeIndicator().equals(Constants.INDICATOR_1)) {
								countryBranchDataTable.setHeadOfficeIndicator(Constants.INDICATOR_1);
								countryBranchDataTable.setHeadOfficeIndicatorName(Constants.YES);
							} else if (countryBranch.getHeadOfficeIndicator().equals(Constants.INDICATOR_0)) {
								countryBranchDataTable.setHeadOfficeIndicator(Constants.INDICATOR_0);
								countryBranchDataTable.setHeadOfficeIndicatorName(Constants.NO);
							}
						}
						
						if (countryBranch.getScanInd() != null) {
							  if (countryBranch.getScanInd().equalsIgnoreCase("A")) {
								    countryBranchDataTable.setScanIndicatorOrType("ArchmetSystem");
							  } else if (countryBranch.getScanInd().equalsIgnoreCase("D")) {
								    countryBranchDataTable.setScanIndicatorOrType("Dbscan");
							  } else {
								    countryBranchDataTable.setScanIndicatorOrType("Noscan");
							  }
						}
						
						if(countryBranch.getDigitalSignInd() != null){
							if(countryBranch.getDigitalSignInd().equalsIgnoreCase(Constants.Yes)){
								countryBranchDataTable.setDigitalSignTypeInd("YES");
							}else if (countryBranch.getDigitalSignInd().equalsIgnoreCase("N")) {
								countryBranchDataTable.setDigitalSignTypeInd("NO");
							}
						}
						
					if(countryBranch.getIsActive() !=null){
						if (countryBranch.getIsActive().equalsIgnoreCase(Constants.Yes)) {
							countryBranchDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
						} else if (countryBranch.getIsActive().equalsIgnoreCase(Constants.D)) {
							countryBranchDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
						} else if (countryBranch.getIsActive().equalsIgnoreCase(Constants.U) && countryBranch.getModifiedBy() == null && countryBranch.getModifiedDate() == null && countryBranch.getApprovedBy() == null && countryBranch.getApprovedDate() == null
								&& countryBranch.getRemarks() == null) {
							countryBranchDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
						} else {
							countryBranchDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						}
					}
					countryBranchDataTable.setDisableEdit(false);
					countryBranchDataTable.setRenderEditButton(true);
					 lstOfCOuntryBranchDataTables.add(countryBranchDataTable);
				  }
				  lstOfCOuntryBranchDataTables.addAll(lstOfCOuntryBranchNewDataTables);
				  
			  }else{
					RequestContext.getCurrentInstance().execute("noRecords.show();");
				}
			  try {
				  FacesContext.getCurrentInstance().getExternalContext().redirect("../common/CountryBranchMaster.xhtml");      
  } catch (Exception e) {
			   e.printStackTrace();
  }
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred " + e);
			return;
		}
			
	}
	
	public void editCountryBranchMethod(CountryBranchDataTable countryBranchDataTable){
		  try {
			setClearPanel(true);
			  setSubmitPanel(true);
			  setCountryBranchDTOBJ(countryBranchDataTable);
			setCountryBranchId(countryBranchDataTable.getCountryBranchId());
			setBranchCode(countryBranchDataTable.getBranchCode());
			setBranchName(countryBranchDataTable.getBranchName());
			setAccountCode(countryBranchDataTable.getAccountCode());
			setCorporateStatus(countryBranchDataTable.getCorporateStatus());
			setAreaCode(countryBranchDataTable.getAreaCode());
			setHeadOfficeIndicator(countryBranchDataTable.getHeadOfficeIndicator());
			setTelephoneNumber(countryBranchDataTable.getTelephoneNumber());
			setEmailId(countryBranchDataTable.getEmailId());
			setEcNumeber(countryBranchDataTable.getEcNumeber());
			setIpAddress(countryBranchDataTable.getIpAddress());
			setCreatedBy(countryBranchDataTable.getCreatedBy());
			setCreatedDate(countryBranchDataTable.getCreatedDate());
			setModifiedBy(countryBranchDataTable.getModifiedBy());
			setModifiedDate(countryBranchDataTable.getModifiedDate());
			setApprovedBy(countryBranchDataTable.getApprovedBy());
			setApprovedDate(countryBranchDataTable.getApprovedDate());
			setIsActive(countryBranchDataTable.getIsActive());
			setDynamicLabelForActivateDeactivate(countryBranchDataTable.getDynamicLabelForActivateDeactivate());
			setRemarks(countryBranchDataTable.getRemarks());
			setWuAccCode(countryBranchDataTable.getWuAccCode());
			setIfEditClicked(true);
			setRenderEditButton(true);
			setBooEdit(true);
			    if (countryBranchDataTable.getScanIndicatorOrType() != null) {
				      if (countryBranchDataTable.getScanIndicatorOrType().equalsIgnoreCase("ArchmetSystem")) {
						setScanIndicatorOrType("A");
				      } else if (countryBranchDataTable.getScanIndicatorOrType().equalsIgnoreCase("Dbscan")) {
						setScanIndicatorOrType("D");
				      } else {
						setScanIndicatorOrType("N");
				      }
			    }
			    
			    if(countryBranchDataTable.getDigitalSignTypeInd() != null){
			    	if(countryBranchDataTable.getDigitalSignTypeInd().equalsIgnoreCase("YES")){
			    		setDigitalSignTypeInd(Constants.Yes);
			    	}else if (countryBranchDataTable.getDigitalSignTypeInd().equalsIgnoreCase("NO")) {
			    		setDigitalSignTypeInd("N");
					}
			    }
			lstOfCOuntryBranchDataTables.remove(countryBranchDataTable);
			lstOfCOuntryBranchNewDataTables.remove(countryBranchDataTable);
			if (lstOfCOuntryBranchDataTables.size() == 0) {
				  setBooCountryBranchDataTable(false);
			    } else {
				      setBooCountryBranchDataTable(true);
			    }
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred " + e);
			return;
		}
		
	}
	
	public void checkStatusType(CountryBranchDataTable countryBranchDataTable) {

		 try {
			 
			if(countryBranchDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
				 //setBooEdit(t);
			      RequestContext.getCurrentInstance().execute("pending.show();");
				return;     
			}else if(countryBranchDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {

				countryBranchDataTable.setRemarksCheck(true);
				setApprovedBy(countryBranchDataTable.getApprovedBy());
				setApprovedDate(countryBranchDataTable.getApprovedDate());
				RequestContext.getCurrentInstance().execute("remarks.show();");
				return;
			} else if (countryBranchDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)) {
				countryBranchDataTable.setBooCheckDelete(true);
				RequestContext.getCurrentInstance().execute("permanentDelete.show();");
				return;
			} else if (countryBranchDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				countryBranchDataTable.setActivateRecordCheck(true);
				RequestContext.getCurrentInstance().execute("activateRecord.show();");
				return;
			} else if (countryBranchDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
				lstOfCOuntryBranchDataTables.remove(countryBranchDataTable);
				lstOfCOuntryBranchNewDataTables.remove(countryBranchDataTable);
			}
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred " + e);
			return;
		}
	}
	
	public void confirmPermanentDelete() {
		if (lstOfCOuntryBranchDataTables.size() > 0) {
			for (CountryBranchDataTable countryBranchDataTable : lstOfCOuntryBranchDataTables) {
				if (countryBranchDataTable.getBooCheckDelete() != null) {
					if (countryBranchDataTable.getBooCheckDelete().equals(true)) {
						delete(countryBranchDataTable);
						lstOfCOuntryBranchDataTables.remove(countryBranchDataTable);
					}
				}
			}
		}

	}
	
	 public void conformToDeActivteCompliantType(CountryBranchDataTable countryBranchDataTable){
		 getCountryBranchService().activateCountryBranch(countryBranchDataTable.getCountryBranchId(), sessionStateManage.getUserName());
		 viewCountryBranchMethod();
		 //RequestContext.getCurrentInstance().execute("update.show();");
	  }

	public void activateRecord() {
		if (lstOfCOuntryBranchDataTables.size() > 0) {
			for (CountryBranchDataTable countryBranchDataTable : lstOfCOuntryBranchDataTables) {
				if (countryBranchDataTable.getActivateRecordCheck() != null) {
					if (countryBranchDataTable.getActivateRecordCheck().equals(true)) {
						conformToDeActivteCompliantType(countryBranchDataTable);
					}
				}
			}
		}

	}

	public void remarkSelectedRecord() throws IOException {
		   if (getRemarks() != null && !getRemarks().equals("")) {
		for (CountryBranchDataTable countryBranchDataTable : lstOfCOuntryBranchDataTables) {
			  if (countryBranchDataTable.getRemarksCheck() != null) {
				    if (countryBranchDataTable.getRemarksCheck().equals(true)) {
							countryBranchDataTable.setRemarks(getRemarks());
							countryBranchDataTable.setApprovedBy(null);
							countryBranchDataTable.setApprovedDate(null);
							countryBranchDataTable.setRemarksCheck(true);
							update(countryBranchDataTable);
							   viewCountryBranchMethod();
							   setRemarks(null);
							//RequestContext.getCurrentInstance().execute("update.show();");
						} 
		}
			
			}

		}else{
			  RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
				return;  
		}
	}
	
	public void cancelRemarks(){
		  setRemarks(null);
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../common/CountryBranchMaster.xhtml");
		    } catch (IOException e) {
			      e.printStackTrace();
		    }  
	}

	public void delete(CountryBranchDataTable countryBranchDataTable) {
		getCountryBranchService().deleteCountryBranch(countryBranchDataTable.getCountryBranchId());
		//RequestContext.getCurrentInstance().execute("update.show();");
	}

	
	public void clearAll(){
		
		setBranchCode(null);
		setBranchName(null);
		setAccountCode(null);
		setCorporateStatus(null);
		setAreaCode(null);
		setHeadOfficeIndicator(null);
		setTelephoneNumber(null);
		setEmailId(null);
		setEcNumeber(null);
		setIpAddress(null);
		setCountryBranchId(null);
		setScanIndicatorOrType(null);
		setDigitalSignTypeInd(null);
		setWuAccCode(null);
	}
	
	public void exit() {
		

		try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
				
				clearAll();
				lstOfCOuntryBranchDataTables.clear();
				lstOfCOuntryBranchNewDataTables.clear();
				setBooCountryBranchDataTable(false);
				setBooCountryBranchDetail(false);
				setBooEdit(true);

		} catch (Exception e) {
			//log.info("Problem to redirect:" + e);
		}

		
	}
	
	public void saveCountryBranch(){
		try{
			setBooVisible(false);
			for(CountryBranchDataTable countryBranchDataTable: lstOfCOuntryBranchDataTables){
				if(countryBranchDataTable.getIfEditClicked()){
					
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(countryBranchDataTable.getCountryBranchId());

					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(countryBranchDataTable.getApplicationCountryId());
					countryBranch.setCountryMaster(countryMaster);

					countryBranch.setBranchId(countryBranchDataTable.getBranchCode());
					countryBranch.setBranchName(countryBranchDataTable.getBranchName());
					countryBranch.setAccountCode(countryBranchDataTable.getAccountCode());
					countryBranch.setAreaCode(countryBranchDataTable.getAreaCode());
					countryBranch.setTelephoneNumber(countryBranchDataTable.getTelephoneNumber());
					countryBranch.setEcNumeber(countryBranchDataTable.getEcNumeber());
					countryBranch.setEmailId(countryBranchDataTable.getEmailId());
					countryBranch.setIpAddress(countryBranchDataTable.getIpAddress());
					countryBranch.setHeadOfficeIndicator(countryBranchDataTable.getHeadOfficeIndicator());
					countryBranch.setCorporateStatus(countryBranchDataTable.getCorporateStatus());
					countryBranch.setWuAccountCode(countryBranchDataTable.getWuAccCode());
					
					if(countryBranchDataTable.getCreatedBy() != null){
						countryBranch.setCreatedBy(countryBranchDataTable.getCreatedBy());
						countryBranch.setCreatedDate(countryBranchDataTable.getCreatedDate());
					}else{
						countryBranch.setCreatedBy(sessionStateManage.getUserName());
						countryBranch.setCreatedDate(new Date());
					}
					
					if(countryBranchDataTable.getCountryBranchId()!=null){
						countryBranch.setModifiedBy(sessionStateManage.getUserName());
						countryBranch.setModifiedDate(new Date());
					}else{
						countryBranch.setModifiedBy(countryBranchDataTable.getModifiedBy());
						countryBranch.setModifiedDate(countryBranchDataTable.getModifiedDate());
					}
					countryBranch.setApprovedBy(countryBranchDataTable.getApprovedBy());
					countryBranch.setApprovedDate(countryBranchDataTable.getApprovedDate());
					countryBranch.setIsActive(countryBranchDataTable.getIsActive());
					countryBranch.setRemarks(countryBranchDataTable.getRemarks());
					if(countryBranchDataTable.getScanIndicatorOrType() != null){
						if (countryBranchDataTable.getScanIndicatorOrType().equalsIgnoreCase("ArchmetSystem")) {
							countryBranch.setScanInd("A");
						} else if (countryBranchDataTable.getScanIndicatorOrType().equalsIgnoreCase("DbScan")) {
							countryBranch.setScanInd("D");
						} else {
							countryBranch.setScanInd("N");
						}
					}

					if(countryBranchDataTable.getDigitalSignTypeInd() != null){
						if(countryBranchDataTable.getDigitalSignTypeInd().equalsIgnoreCase("YES")){
							countryBranch.setDigitalSignInd("Y");
						}else if (countryBranchDataTable.getDigitalSignTypeInd().equalsIgnoreCase("NO")) {
							countryBranch.setDigitalSignInd("N");
						}
					}
					getCountryBranchService().saveAndUpdateCountryBranch(countryBranch);
				}
			}
			RequestContext.getCurrentInstance().execute("complete.show();");
			clearAll();
			lstOfCOuntryBranchDataTables.clear();
			lstOfCOuntryBranchNewDataTables.clear();
			setBooCountryBranchDataTable(false);
			return;
		}catch(Exception exception){
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred " + exception);
			return;
		}
	}
	
	public void update(CountryBranchDataTable countryBranchDataTable) {	
		  countryBranchService.upDateActiveRecordtoDeActive(countryBranchDataTable.getCountryBranchId(), countryBranchDataTable.getRemarks(), sessionStateManage.getUserName());
		/*try {
			
			CountryBranch countryBranch = new CountryBranch();
			countryBranch.setCountryBranchId(countryBranchDataTable.getCountryBranchId());
			
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(countryBranchDataTable.getApplicationCountryId());
			countryBranch.setCountryMaster(countryMaster);
			
			countryBranch.setBranchId(countryBranchDataTable.getBranchCode());
			countryBranch.setBranchName(countryBranchDataTable.getBranchName());
			countryBranch.setAccountCode(countryBranchDataTable.getAccountCode());
			countryBranch.setAreaCode(countryBranchDataTable.getAreaCode());
			countryBranch.setTelephoneNumber(countryBranchDataTable.getTelephoneNumber());
			countryBranch.setEcNumeber(countryBranchDataTable.getEcNumeber());
			countryBranch.setEmailId(countryBranchDataTable.getEmailId());
			countryBranch.setIpAddress(countryBranchDataTable.getIpAddress());
			countryBranch.setHeadOfficeIndicator(countryBranchDataTable.getHeadOfficeIndicator());
			countryBranch.setCorporateStatus(countryBranchDataTable.getCorporateStatus());
			
			countryBranch.setModifiedBy(sessionStateManage.getUserName());
			countryBranch.setModifiedDate(new Date());
			countryBranch.setApprovedBy(countryBranchDataTable.getApprovedBy());
			countryBranch.setApprovedDate(countryBranchDataTable.getApprovedDate());
			countryBranch.setRemarks(countryBranchDataTable.getRemarks());
			countryBranch.setIsActive(Constants.D);
			countryBranch.setCreatedBy(countryBranchDataTable.getCreatedBy());
			countryBranch.setCreatedDate(countryBranchDataTable.getCreatedDate());
			
			countryBranch.setRemarks(countryBranchDataTable.getRemarks());
			
			getCountryBranchService().saveAndUpdateCountryBranch(countryBranch);
			
			
			
		} catch (Exception e) {
			System.out.println(e);
		}*/
	}
	
	 public List<String> autoComplete(BigDecimal query){
		 
		 String q = query.toString();
		    if ( q.length() > 0) {
				return getCountryBranchService().autoCompleteList(q);
			} else {
				return null;
			}
	  }
	
	public void autoCompletePopulateValue() {

		try {
			setBooVisible(false);
			lstOfCountryBranchs = getCountryBranchService().displayCountryBranchBasedOnCountryBranchCode(getBranchCode());
			

			if(lstOfCountryBranchs!=null){
			if (lstOfCountryBranchs.size() >0) {
				setBranchCode(null);
				RequestContext.getCurrentInstance().execute("codeExist.show();");	
			}else{
				
				clearAll();
			}
			}
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}

	}
	
	 public void negativeValueChecking(FacesContext context, UIComponent component,Object value) {
			BigDecimal negativeValue = (BigDecimal) value;

			if (negativeValue.intValue() <= 0) {
				FacesMessage msg = new FacesMessage("Please Enter Only Positive Values, Zero Not Allowed","Please Enter Only Positive Values, Zero Not Allowed");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}else if(negativeValue!=null && negativeValue.toPlainString().length()<8){
				FacesMessage msg = new FacesMessage("Please Enter atleast 8 Character","Please Enter atleast 8 Character");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
		}

	  private String scanIndicatorOrType;


	  public String getScanIndicatorOrType() {
	  	  return scanIndicatorOrType;
	  }

	  public void setScanIndicatorOrType(String scanIndicatorOrType) {
	  	  this.scanIndicatorOrType = scanIndicatorOrType;
	  }

	  private Boolean renderEditButton=false;


	  public Boolean getRenderEditButton() {
	  	  return renderEditButton;
	  }

	  public void setRenderEditButton(Boolean renderEditButton) {
	  	  this.renderEditButton = renderEditButton;
	  }
	  
	  private Boolean clearPanel=false;
	  private Boolean submitPanel=false;
	  

	  public Boolean getClearPanel() {
	  	  return clearPanel;
	  }

	  public void setClearPanel(Boolean clearPanel) {
	  	  this.clearPanel = clearPanel;
	  }

	  public Boolean getSubmitPanel() {
	  	  return submitPanel;
	  }

	  public void setSubmitPanel(Boolean submitPanel) {
	  	  this.submitPanel = submitPanel;
	  }
	  
	 private CountryBranchDataTable countryBranchDTOBJ=null;


public CountryBranchDataTable getCountryBranchDTOBJ() {
	  return countryBranchDTOBJ;
}

public void setCountryBranchDTOBJ(CountryBranchDataTable countryBranchDTOBJ) {
	  this.countryBranchDTOBJ = countryBranchDTOBJ;
}
	 
//auto complete
	  public List<String> autoComplete(String query) {
		    if (query.length() > 0) {
			      return countryBranchService.toFetchAllCountryBranchCode(query);
		    } else {
			      return null;
		    }
	  }

	  // populate
	  public void populate() {
		    List<CountryBranch> lstCountryBranch = countryBranchService.toCompareBranchCode(getBranchCode());
		    if (lstCountryBranch.size() !=0) {
			      clearAll();
			      RequestContext.getCurrentInstance().execute("datatable.show();");
			      return;
		    }
	  }	  

	  private String errMsg;

	  public String getErrMsg() {
		    return errMsg;
	  }

	  public void setErrMsg(String errMsg) {
		    this.errMsg = errMsg;
	  }
	
	//added new Field 20/09/2016
	  private String digitalSignTypeInd;

	public String getDigitalSignTypeInd() {
		return digitalSignTypeInd;
	}

	public void setDigitalSignTypeInd(String digitalSignTypeInd) {
		this.digitalSignTypeInd = digitalSignTypeInd;
	}

	public String getWuAccCode() {
		return wuAccCode;
	}

	public void setWuAccCode(String wuAccCode) {
		this.wuAccCode = wuAccCode;
	}

	
	
	  
	
	
}