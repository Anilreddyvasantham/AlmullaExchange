package com.amg.exchange.common.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.InsuranceMaster;
import com.amg.exchange.common.model.InsuranceMasterDescription;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IInsuranceSetUpService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/*******************************************************************************************************************
 * File : InsuranceSetUpBean.java
 * 
 * Project : AlMulla Exchange Online Remittance System
 * 
 * Package : com.amg.exchange.treasury.bean
 * 
 * Created : Date : 08-07-2015 3:46:42 : Nagarjuna Revision:
 * 
 * Last Change: Date : 2015-07-09 By : Nagarjuna Revision:
 * 
 * Description: TODO
 ********************************************************************************************************************/
@Component("insuranceSetUpBean")
@Scope("session")
public class InsuranceSetUpBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(InsuranceSetUpBean.class);
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	private String insuranceMsgOne;
	private String insuranceMsgTwo;
	private String insuranceArabicMsgOne;
	private String insuranceArabicMsgTwo;
	private Date effectiveFromDate;
	private Date effectiveToDate;
	private BigDecimal insuranceDays;
	private BigDecimal loyaltyPoints;
	private BigDecimal insuranceAmount;
	private BigDecimal insuranceAdditionalAmount;
	private BigDecimal insuranceSetUpPk;
	private String dynamicActivateDeactivate;
	private String isActive;
	private Boolean booCheckUpdate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private Boolean booRenderSubmit;
	private Boolean booRenderDataTable;
	private Boolean booHideClear;
	private BigDecimal insuranceSetUpDescPk;
	private BigDecimal insuranceSetUpArabicPk;
	
	private String errorMessage;
	private Boolean booVisible;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

	public Boolean getBooVisible() {
		return booVisible;
	}

	public void setBooVisible(Boolean booVisible) {
		this.booVisible = booVisible;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getDynamicActivateDeactivate() {
		return dynamicActivateDeactivate;
	}

	public void setDynamicActivateDeactivate(String dynamicActivateDeactivate) {
		this.dynamicActivateDeactivate = dynamicActivateDeactivate;
	}

	public BigDecimal getInsuranceSetUpDescPk() {
		return insuranceSetUpDescPk;
	}

	public void setInsuranceSetUpDescPk(BigDecimal insuranceSetUpDescPk) {
		this.insuranceSetUpDescPk = insuranceSetUpDescPk;
	}

	public BigDecimal getInsuranceSetUpArabicPk() {
		return insuranceSetUpArabicPk;
	}

	public void setInsuranceSetUpArabicPk(BigDecimal insuranceSetUpArabicPk) {
		this.insuranceSetUpArabicPk = insuranceSetUpArabicPk;
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

	public List<InsuranceSetUpDataTableBean> getViewList() {
		return viewList;
	}

	public void setViewList(List<InsuranceSetUpDataTableBean> viewList) {
		this.viewList = viewList;
	}

	public Boolean getBooRenderSubmit() {
		return booRenderSubmit;
	}

	public void setBooRenderSubmit(Boolean booRenderSubmit) {
		this.booRenderSubmit = booRenderSubmit;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public Boolean getBooHideClear() {
		return booHideClear;
	}

	public void setBooHideClear(Boolean booHideClear) {
		this.booHideClear = booHideClear;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	SessionStateManage sessionStateManage = new SessionStateManage();
	private List<InsuranceSetUpDataTableBean> insuranceDTList = new CopyOnWriteArrayList<InsuranceSetUpDataTableBean>();
	private List<InsuranceMaster> insuranceList = new ArrayList<InsuranceMaster>();
	@Autowired
	IInsuranceSetUpService insuranceService;

	public List<InsuranceSetUpDataTableBean> getInsuranceDTList() {
		return insuranceDTList;
	}

	public void setInsuranceDTList(List<InsuranceSetUpDataTableBean> insuranceDTList) {
		this.insuranceDTList = insuranceDTList;
	}

	public List<InsuranceMaster> getInsuranceList() {
		return insuranceList;
	}

	public void setInsuranceList(List<InsuranceMaster> insuranceList) {
		this.insuranceList = insuranceList;
	}

	public BigDecimal getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}

	public BigDecimal getToAmount() {
		return toAmount;
	}

	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
	}

	public String getInsuranceMsgOne() {
		return insuranceMsgOne;
	}

	public void setInsuranceMsgOne(String insuranceMsgOne) {
		this.insuranceMsgOne = insuranceMsgOne;
	}

	public String getInsuranceMsgTwo() {
		return insuranceMsgTwo;
	}

	public void setInsuranceMsgTwo(String insuranceMsgTwo) {
		this.insuranceMsgTwo = insuranceMsgTwo;
	}

	public String getInsuranceArabicMsgOne() {
		return insuranceArabicMsgOne;
	}

	public void setInsuranceArabicMsgOne(String insuranceArabicMsgOne) {
		this.insuranceArabicMsgOne = insuranceArabicMsgOne;
	}

	public String getInsuranceArabicMsgTwo() {
		return insuranceArabicMsgTwo;
	}

	public void setInsuranceArabicMsgTwo(String insuranceArabicMsgTwo) {
		this.insuranceArabicMsgTwo = insuranceArabicMsgTwo;
	}

	public BigDecimal getInsuranceDays() {
		return insuranceDays;
	}

	public void setInsuranceDays(BigDecimal insuranceDays) {
		this.insuranceDays = insuranceDays;
	}

	public BigDecimal getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(BigDecimal loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public BigDecimal getInsuranceAdditionalAmount() {
		return insuranceAdditionalAmount;
	}

	public void setInsuranceAdditionalAmount(BigDecimal insuranceAdditionalAmount) {
		this.insuranceAdditionalAmount = insuranceAdditionalAmount;
	}

	public BigDecimal getInsuranceSetUpPk() {
		return insuranceSetUpPk;
	}

	public void setInsuranceSetUpPk(BigDecimal insuranceSetUpPk) {
		this.insuranceSetUpPk = insuranceSetUpPk;
	}

	public Date getEffectiveToDate() {
		return effectiveToDate;
	}

	public void setEffectiveToDate(Date effectiveToDate) {
		this.effectiveToDate = effectiveToDate;
	}

	public Date getEffectiveFromDate() {
		return effectiveFromDate;
	}

	public void setEffectiveFromDate(Date effectiveFromDate) {
		this.effectiveFromDate = effectiveFromDate;
	}

	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}

	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigationToInsuranceSetUp() {
		setBooRenderDataTable(false);
		setBooRenderSubmit(false);
		setBooHideClear(false);
		clearAll();
		insuranceDTList.clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "medicalinsurancesetup.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/medicalinsurancesetup.xhtml");
		} catch (Exception e) {
		}
	}

	public void exit() {
		clearAll();
		insuranceDTList.clear();
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} catch (IOException e) {
				log.error("===========Navigation problemin exit() method==========");
			}
		} else {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			} catch (IOException e) {
				log.error("===========Navigation problem exit() ==========");
			}
		}
	}

	public void clearAll() {
		setEffectiveFromDate(null);
		setEffectiveToDate(null);
		setFromAmount(null);
		setToAmount(null);
		setInsuranceMsgOne(null);
		setInsuranceMsgTwo(null);
		setInsuranceArabicMsgOne(null);
		setInsuranceArabicMsgTwo(null);
		setInsuranceDays(null);
		setLoyaltyPoints(null);
		setInsuranceAdditionalAmount(null);
		setInsuranceAmount(null);
		setInsuranceSetUpPk(null);
		setInsuranceSetUpDescPk(null);
		setInsuranceSetUpArabicPk(null);
		setRemarks(null);
		setApprovedBy(null);
		setApprovedDate(null);
	}

	// customized validation for toAmount>=from amount
	public void comparingAmount(FacesContext context, UIComponent component, Object value) {
		BigDecimal toAmount = (BigDecimal) value;
		if (getFromAmount() == null || toAmount == null) {
			FacesMessage msg = new FacesMessage("Please Enter From Amount and Then ToAmount", "Please Enter From Amount and Then ToAmount");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getFromAmount().compareTo(toAmount) > 0) {
			// || getFromAmount().compareTo(toAmount) == 0
			setToAmount(null);
			FacesMessage msg = new FacesMessage("Please Enter Greater Than From Amount", "Please Enter Greater Than From Amount");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	public void addRecordsToDataTable() {
		try {
			
			setBooVisible(false);
			if (viewList.size() == 0) {
				InsuranceSetUpDataTableBean insuranceDTObj = new InsuranceSetUpDataTableBean();
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				insuranceDTObj.setEffectiveFromDate(format.format(getEffectiveFromDate()));
				insuranceDTObj.setEffectiveToDate(format.format(getEffectiveToDate()));
				insuranceDTObj.setInsuranceDays(getInsuranceDays());
				insuranceDTObj.setLoyaltyPoints(getLoyaltyPoints());
				insuranceDTObj.setFromAmount(getFromAmount());
				insuranceDTObj.setToAmount(getToAmount());
				insuranceDTObj.setInsuranceAmount(getInsuranceAmount());
				insuranceDTObj.setInsuranceAdditionalAmount(getInsuranceAdditionalAmount());
				insuranceDTObj.setInsuranceMsgOne(getInsuranceMsgOne());
				insuranceDTObj.setInsuranceMsgTwo(getInsuranceMsgTwo());
				insuranceDTObj.setInsuranceArabicMsgOne(getInsuranceArabicMsgOne());
				insuranceDTObj.setInsuranceArabicMsgTwo(getInsuranceArabicMsgTwo());
				insuranceDTObj.setInsuranceSetUpPk(getInsuranceSetUpPk());
				insuranceDTObj.setInsuranceMasterDescPk(getInsuranceSetUpDescPk());
				insuranceDTObj.setInsuranceMasterDescArabicPk(getInsuranceSetUpArabicPk());
				if (getInsuranceSetUpPk() != null) {
					insuranceDTObj.setDynamicActivateDeactivate(getDynamicActivateDeactivate());
					insuranceDTObj.setIsActive(getIsActive());
					insuranceDTObj.setRemarks(getRemarks());
					insuranceDTObj.setCreatedBy(getCreatedBy());
					insuranceDTObj.setCreatedDate(getCreatedDate());
					insuranceDTObj.setApprovedBy(getApprovedBy());
					insuranceDTObj.setApprovedDate(getApprovedDate());
					insuranceDTObj.setBooCheckUpdate(getBooCheckUpdate());
					insuranceDTObj.setModifiedBy( sessionStateManage.getUserName());
					insuranceDTObj.setModifiedDate( new Date());
				} else {
					insuranceDTObj.setDynamicActivateDeactivate(Constants.REMOVE);
					insuranceDTObj.setIsActive(Constants.U);
					insuranceDTObj.setBooCheckSave(true);
					insuranceDTObj.setCreatedBy(sessionStateManage.getUserName());
					insuranceDTObj.setCreatedDate(new Date());
					insuranceDTObj.setBooCheckUpdate(true);
				}
				insuranceDTList.add(insuranceDTObj);
			}
			if (insuranceDTList.size() > 0) {
				if (viewList.size() > 0) {
					for (InsuranceSetUpDataTableBean insuranceDt : insuranceDTList) {
						for (InsuranceSetUpDataTableBean insuranceViewDt : viewList) {
							if (insuranceDt.getEffectiveFromDate().equals(insuranceViewDt.getEffectiveFromDate()) && insuranceDt.getEffectiveToDate().equals(insuranceViewDt.getEffectiveToDate())) {
								viewList.remove(insuranceViewDt);
							}
						}
					}
				}
				insuranceDTList.addAll(viewList);
			} else {
				insuranceDTList.addAll(viewList);
			}
			clearAll();
			setBooRenderDataTable(true);
			setBooRenderSubmit(true);
		} catch (Exception e) {

			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		
		}
	}

	public void saveDataTableRecords() {
		try {
			setBooVisible(false);
			if (insuranceDTList.size() > 0) {
				for (InsuranceSetUpDataTableBean insuranceDTObj : insuranceDTList) {
					InsuranceMaster insuranceSetUpObj = new InsuranceMaster();
					log.info("companyId=" + sessionStateManage.getCompanyId());
					log.info("insurancedays=" + insuranceDTObj.getInsuranceDays());
					log.info("Loyalty=" + insuranceDTObj.getLoyaltyPoints());
					log.info("countryId=" + sessionStateManage.getCountryId());
					log.info("Arabicmsgone=" + insuranceDTObj.getInsuranceArabicMsgOne());
					log.info("ArabicmsgTwo=" + insuranceDTObj.getInsuranceArabicMsgTwo());
					log.info("InsuranceMsgOne=" + insuranceDTObj.getInsuranceMsgOne());
					log.info("InsuranceMsgTwo=" + insuranceDTObj.getInsuranceMsgTwo());
					log.info("insuranceAmount=" + insuranceDTObj.getInsuranceAmount());
					log.info("InsuarnceAdditionalAmount=" + insuranceDTObj.getInsuranceAdditionalAmount());
					log.info("toAmount=" + insuranceDTObj.getToAmount());
					log.info("FromAmount=" + insuranceDTObj.getFromAmount());
					CompanyMaster companymaster = new CompanyMaster();
					companymaster.setCompanyId(sessionStateManage.getCompanyId());
					insuranceSetUpObj.setFsCompanyMaster(companymaster);
					CountryMaster country = new CountryMaster();
					country.setCountryId(sessionStateManage.getCountryId());
					insuranceSetUpObj.setApplicationCountryId(country);
					try {
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						Date fromDate = formatter.parse(insuranceDTObj.getEffectiveFromDate());
						insuranceSetUpObj.setFromDate(fromDate);
						Date toDate = formatter.parse(insuranceDTObj.getEffectiveToDate());
						insuranceSetUpObj.setToDate(toDate);
					} catch (Exception e) {
						log.error("=========Exception Date Coversion========");
					}
					if (insuranceDTObj.getInsuranceSetUpPk() != null) {
						insuranceSetUpObj.setInsuranceSetUpId(insuranceDTObj.getInsuranceSetUpPk());
						insuranceSetUpObj.setModifiedBy( sessionStateManage.getUserName());
						insuranceSetUpObj.setModifiedDate( new Date());
					}
					insuranceSetUpObj.setInsuranceDays(insuranceDTObj.getInsuranceDays());
					insuranceSetUpObj.setInsuranceLoyaltyPoints(insuranceDTObj.getLoyaltyPoints());
					insuranceSetUpObj.setInsuranceAdditionalAmount(insuranceDTObj.getInsuranceAdditionalAmount());
					insuranceSetUpObj.setInsuranceAmount(insuranceDTObj.getInsuranceAmount());
					insuranceSetUpObj.setToAmount(insuranceDTObj.getToAmount());
					insuranceSetUpObj.setFromAmount(insuranceDTObj.getFromAmount());
					if(insuranceDTObj.getInsuranceSetUpPk() ==null){
					insuranceSetUpObj.setCreatedBy(sessionStateManage.getUserName());
					insuranceSetUpObj.setCreatedDate(new Date());
					}else{
						insuranceSetUpObj.setCreatedBy(insuranceDTObj.getCreatedBy() );
						insuranceSetUpObj.setCreatedDate(insuranceDTObj.getCreatedDate());
					}
					insuranceSetUpObj.setIsActive(Constants.U);
					if (insuranceDTObj.getBooCheckUpdate()) {
						insuranceService.saveOrUpadate(insuranceSetUpObj);
					}
					InsuranceMasterDescription insuranceMasterDescEngObj = new InsuranceMasterDescription();
					insuranceMasterDescEngObj.setInsuranceMasterId(insuranceSetUpObj);
					LanguageType languageTypeEnglish = new LanguageType();
					languageTypeEnglish.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
					insuranceMasterDescEngObj.setLanguageType(languageTypeEnglish);
					insuranceMasterDescEngObj.setInsuranceMasterDescId(insuranceDTObj.getInsuranceMasterDescPk());
					insuranceMasterDescEngObj.setInsurancePrimaryMessage(insuranceDTObj.getInsuranceMsgOne());
					insuranceMasterDescEngObj.setInsuranceSecondaryMessage(insuranceDTObj.getInsuranceMsgTwo());
					if (insuranceDTObj.getBooCheckUpdate()) {
						insuranceService.saveOrUpdate(insuranceMasterDescEngObj);
					}
					InsuranceMasterDescription insuranceMasterDescArabicObj = new InsuranceMasterDescription();
					insuranceMasterDescArabicObj.setInsuranceMasterId(insuranceSetUpObj);
					LanguageType languageTypeArabic = new LanguageType();
					languageTypeArabic.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
					insuranceMasterDescArabicObj.setLanguageType(languageTypeArabic);
					insuranceMasterDescArabicObj.setInsurancePrimaryMessage(insuranceDTObj.getInsuranceArabicMsgOne());
					insuranceMasterDescArabicObj.setInsuranceSecondaryMessage(insuranceDTObj.getInsuranceArabicMsgTwo());
					insuranceMasterDescArabicObj.setInsuranceMasterDescId(insuranceDTObj.getInsuranceMasterDescArabicPk());
					if (insuranceDTObj.getBooCheckUpdate()) {
						insuranceService.saveOrUpdate(insuranceMasterDescArabicObj);
					}
				}
				RequestContext.getCurrentInstance().execute("completed.show();");
			} else {
				RequestContext.getCurrentInstance().execute("notrecordfound.show();");
			}
		} catch (Exception e) {log.info("*****************************************************" + getErrorMessage());
		setBooVisible(true);
		RequestContext.getCurrentInstance().execute("error.show();");
		setErrorMessage("Exception ocurred" + e);
		return;}
	}

	public void clickOnOKSave() {
		pageNavigationToInsuranceSetUp();
	}

	public void checkToDateValidator() {
		if (getEffectiveFromDate() != null) {
			if (getEffectiveFromDate().after(getEffectiveToDate())) {
				setEffectiveToDate(null);
				RequestContext.getCurrentInstance().execute("datevalid.show();");
			}
		}
	}

	public void editRecord(InsuranceSetUpDataTableBean insuranceDTObj) {
		try {
			setBooVisible(false);
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				setEffectiveFromDate(formatter.parse(insuranceDTObj.getEffectiveFromDate()));
				setEffectiveToDate(formatter.parse(insuranceDTObj.getEffectiveToDate()));
			} catch (ParseException e) {
				log.error("=============Date coversion problem============");
			}
			setInsuranceSetUpPk(insuranceDTObj.getInsuranceSetUpPk());
			setInsuranceSetUpDescPk(insuranceDTObj.getInsuranceMasterDescPk());
			setInsuranceSetUpArabicPk(insuranceDTObj.getInsuranceMasterDescArabicPk());
			setInsuranceDays(insuranceDTObj.getInsuranceDays());
			setLoyaltyPoints(insuranceDTObj.getLoyaltyPoints());
			setInsuranceAdditionalAmount(insuranceDTObj.getInsuranceAdditionalAmount());
			setInsuranceAmount(insuranceDTObj.getInsuranceAmount());
			setToAmount(insuranceDTObj.getToAmount());
			setFromAmount(insuranceDTObj.getFromAmount());
			setInsuranceMsgOne(insuranceDTObj.getInsuranceMsgOne());
			setInsuranceMsgTwo(insuranceDTObj.getInsuranceMsgTwo());
			setInsuranceArabicMsgOne(insuranceDTObj.getInsuranceArabicMsgOne());
			setInsuranceArabicMsgTwo(insuranceDTObj.getInsuranceArabicMsgTwo());
			setCreatedBy(insuranceDTObj.getCreatedBy());
			setCreatedDate(insuranceDTObj.getCreatedDate());
			setIsActive( Constants.U);
			setDynamicActivateDeactivate(Constants.PENDING_FOR_APPROVE );
			setBooCheckUpdate(true);
			setBooHideClear(true);
			insuranceDTList.remove(insuranceDTObj);
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	List<InsuranceSetUpDataTableBean> viewList = new CopyOnWriteArrayList<InsuranceSetUpDataTableBean>();

	// view
	public void viewAll() {
		try {
			setBooVisible(false);
			viewList.clear();
			List<InsuranceMasterDescription> allList = insuranceService.getAllRecordsFromDB();
			if (allList.size() > 0) {
				for (InsuranceMasterDescription insuranceDesc : allList) {
					InsuranceSetUpDataTableBean insuranceDTObj = new InsuranceSetUpDataTableBean();
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					insuranceDTObj.setInsuranceSetUpPk(insuranceDesc.getInsuranceMasterId().getInsuranceSetUpId());
					insuranceDTObj.setEffectiveFromDate(format.format(insuranceDesc.getInsuranceMasterId().getFromDate()));
					insuranceDTObj.setEffectiveToDate(format.format(insuranceDesc.getInsuranceMasterId().getToDate()));
					insuranceDTObj.setInsuranceAdditionalAmount(insuranceDesc.getInsuranceMasterId().getInsuranceAdditionalAmount());
					insuranceDTObj.setInsuranceAmount(insuranceDesc.getInsuranceMasterId().getInsuranceAmount());
					insuranceDTObj.setFromAmount(insuranceDesc.getInsuranceMasterId().getFromAmount());
					insuranceDTObj.setToAmount(insuranceDesc.getInsuranceMasterId().getToAmount());
					insuranceDTObj.setApplicationCountryId(insuranceDesc.getInsuranceMasterId().getApplicationCountryId().getCountryId());
					insuranceDTObj.setCompanyId(insuranceDesc.getInsuranceMasterId().getFsCompanyMaster().getCompanyId());
					insuranceDTObj.setLoyaltyPoints(insuranceDesc.getInsuranceMasterId().getInsuranceLoyaltyPoints());
					insuranceDTObj.setInsuranceDays(insuranceDesc.getInsuranceMasterId().getInsuranceDays());
					insuranceDTObj.setCreatedBy(insuranceDesc.getInsuranceMasterId().getCreatedBy());
					insuranceDTObj.setCreatedDate(insuranceDesc.getInsuranceMasterId().getCreatedDate());
					insuranceDTObj.setModifiedBy(insuranceDesc.getInsuranceMasterId().getModifiedBy());
					insuranceDTObj.setModifiedDate(insuranceDesc.getInsuranceMasterId().getModifiedDate());
					insuranceDTObj.setApprovedBy(insuranceDesc.getInsuranceMasterId().getApprovedBy());
					insuranceDTObj.setApprovedDate(insuranceDesc.getInsuranceMasterId().getApprovedDate());
					insuranceDTObj.setIsActive(insuranceDesc.getInsuranceMasterId().getIsActive());
					if (insuranceDesc.getInsuranceMasterId().getIsActive().equalsIgnoreCase(Constants.Yes)) {
						insuranceDTObj.setDynamicActivateDeactivate(Constants.DEACTIVATE);
						insuranceDTObj.setBooCheckDelete(false);
						insuranceDTObj.setBooCheckUpdate(false);
						setBooHideClear(false);
						insuranceDTObj.setBooActivate(false);
					} else if (insuranceDesc.getInsuranceMasterId().getIsActive().equalsIgnoreCase(Constants.D)) {
						insuranceDTObj.setDynamicActivateDeactivate(Constants.ACTIVATE);
						setBooHideClear(false);
						insuranceDTObj.setBooCheckDelete(false);
						insuranceDTObj.setBooCheckUpdate(false);
						insuranceDTObj.setBooActivate(false);
					} else if (insuranceDesc.getInsuranceMasterId().getIsActive().equalsIgnoreCase(Constants.U)) {
						if (insuranceDesc.getInsuranceMasterId().getModifiedBy() == null && insuranceDesc.getInsuranceMasterId().getModifiedDate() == null && insuranceDesc.getInsuranceMasterId().getApprovedBy() == null && insuranceDesc.getInsuranceMasterId().getApprovedDate() == null
								&& insuranceDesc.getInsuranceMasterId().getApprovedBy() == null && insuranceDesc.getInsuranceMasterId().getRemarks() == null) {
							insuranceDTObj.setDynamicActivateDeactivate(Constants.DELETE);
							setBooHideClear(false);
							insuranceDTObj.setBooCheckDelete(false);
							insuranceDTObj.setBooCheckUpdate(false);
							insuranceDTObj.setBooActivate(false);
						} else {
							insuranceDTObj.setDynamicActivateDeactivate(Constants.PENDING_FOR_APPROVE);
							insuranceDTObj.setBooCheckDelete(false);
							insuranceDTObj.setBooCheckUpdate(false);
							insuranceDTObj.setBooActivate(false);
						}
					}
					if (insuranceDesc.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID.toString())) {
						insuranceDTObj.setInsuranceMsgOne(insuranceDesc.getInsurancePrimaryMessage());
						insuranceDTObj.setInsuranceMsgTwo(insuranceDesc.getInsuranceSecondaryMessage());
						insuranceDTObj.setInsuranceMasterDescPk(insuranceDesc.getInsuranceMasterDescId());
						if (insuranceDTObj.getInsuranceMasterDescPk() != null && insuranceDTObj.getInsuranceMasterDescArabicPk() != null) {
							viewList.add(insuranceDTObj);
						}
					}
					List<InsuranceMasterDescription> appList2 = insuranceService.getAllRecordsFromDB();
					for (InsuranceMasterDescription InsuranceDescObj2 : appList2) {
						if (insuranceDTObj.getInsuranceSetUpPk().toString().equals(InsuranceDescObj2.getInsuranceMasterId().getInsuranceSetUpId().toString()) && InsuranceDescObj2.getLanguageType().getLanguageId().toString().equals(Constants.ARABIC_LANGUAGE_ID.toString())) {
							insuranceDTObj.setInsuranceArabicMsgOne(InsuranceDescObj2.getInsurancePrimaryMessage());
							insuranceDTObj.setInsuranceArabicMsgTwo(InsuranceDescObj2.getInsuranceSecondaryMessage());
							insuranceDTObj.setInsuranceMasterDescArabicPk(InsuranceDescObj2.getInsuranceMasterDescId());
							if (insuranceDTObj.getInsuranceMasterDescPk() != null && insuranceDTObj.getInsuranceMasterDescArabicPk() != null) {
								viewList.add(insuranceDTObj);
							}
						}
					}
				}
			}
			if (viewList.size() > 0) {
				addRecordsToDataTable();
				viewList.clear();
			} else {
				RequestContext.getCurrentInstance().execute("exist.show();");
			}
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void checkStatusType(InsuranceSetUpDataTableBean insuranceSetUpObj) throws IOException {
		try {
			setBooVisible(false);
			if (insuranceSetUpObj.getDynamicActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				insuranceSetUpObj.setRemarkCheck( true);
				setApprovedBy(insuranceSetUpObj.getApprovedBy());
				setApprovedDate(insuranceSetUpObj.getApprovedDate());
				RequestContext.getCurrentInstance().execute("remarks.show();");
			} else if (insuranceSetUpObj.getDynamicActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				insuranceSetUpObj.setBooActivate(true);
				RequestContext.getCurrentInstance().execute("activateRecord.show();");
			} else if (insuranceSetUpObj.getDynamicActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && insuranceSetUpObj.getRemarks() == null && insuranceSetUpObj.getApprovedBy() == null && insuranceSetUpObj.getApprovedDate() == null && insuranceSetUpObj.getModifiedBy() == null
					&& insuranceSetUpObj.getModifiedDate() == null) {
				insuranceSetUpObj.setBooCheckDelete(true);
				RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			} else if (insuranceSetUpObj.getDynamicActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
				removeRecord(insuranceSetUpObj);
			} else if (insuranceSetUpObj.getDynamicActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
				RequestContext.getCurrentInstance().execute("pending.show();");
			}
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void removeRecordFromDB(InsuranceSetUpDataTableBean insuranceDataTable) {
		try {
			setBooVisible(false);
			insuranceService.deleteRecordFromChild(insuranceDataTable.getInsuranceMasterDescPk());
			insuranceService.deleteRecordFromChild(insuranceDataTable.getInsuranceMasterDescArabicPk());
			log.info("===========Child records deleted=====");
			insuranceService.deleteRecordFromParent(insuranceDataTable.getInsuranceSetUpPk());
			log.info("===========Parent records deleted=====");
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void removeRecord(InsuranceSetUpDataTableBean insuranceDataTable) {
		try {
			setBooVisible(false);
			if (insuranceDataTable.getInsuranceSetUpPk() == null) {
				if (insuranceDataTable.getBooCheckSave().equals(true)) {
					insuranceDTList.remove(insuranceDataTable);
					if (insuranceDTList.size() <= 0) {
						setBooRenderDataTable(false);
						setBooRenderSubmit(false);
					}
				}
			} else {
				deActivateRecord(insuranceDataTable);
				insuranceDTList.clear();
				viewAll();
				insuranceDTList.addAll(viewList);
			}
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void deActivateRecord(InsuranceSetUpDataTableBean insuranceDataTableObj) {
		try {
			setBooVisible(false);
			InsuranceMaster insuranceMasterObj = new InsuranceMaster();
			insuranceMasterObj.setInsuranceSetUpId(insuranceDataTableObj.getInsuranceSetUpPk());
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date fromDate = formatter.parse(insuranceDataTableObj.getEffectiveFromDate());
				insuranceMasterObj.setFromDate(fromDate);
				Date toDate = formatter.parse(insuranceDataTableObj.getEffectiveToDate());
				insuranceMasterObj.setToDate(toDate);
			} catch (Exception e) {
				log.error("=========Exception Date Coversion========");
			}
			insuranceMasterObj.setFromAmount(insuranceDataTableObj.getFromAmount());
			insuranceMasterObj.setToAmount(insuranceDataTableObj.getToAmount());
			insuranceMasterObj.setInsuranceLoyaltyPoints(insuranceDataTableObj.getLoyaltyPoints());
			insuranceMasterObj.setInsuranceDays(insuranceDataTableObj.getInsuranceDays());
			insuranceMasterObj.setInsuranceAdditionalAmount(insuranceDataTableObj.getInsuranceAdditionalAmount());
			insuranceMasterObj.setInsuranceAmount(insuranceDataTableObj.getInsuranceAmount());
			CountryMaster country = new CountryMaster();
			country.setCountryId(insuranceDataTableObj.getApplicationCountryId());
			insuranceMasterObj.setApplicationCountryId(country);
			CompanyMaster company = new CompanyMaster();
			company.setCompanyId(insuranceDataTableObj.getCompanyId());
			insuranceMasterObj.setFsCompanyMaster(company);
			insuranceMasterObj.setCreatedBy(insuranceDataTableObj.getCreatedBy());
			insuranceMasterObj.setCreatedDate(insuranceDataTableObj.getCreatedDate());
			insuranceMasterObj.setModifiedBy(sessionStateManage.getUserName());
			insuranceMasterObj.setModifiedDate(new Date());
			if (insuranceDataTableObj.getDynamicActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				insuranceMasterObj.setModifiedBy(sessionStateManage.getUserName());
				insuranceMasterObj.setModifiedDate(new Date());
				insuranceMasterObj.setIsActive(Constants.U);
			} else {
				insuranceMasterObj.setModifiedBy(sessionStateManage.getUserName());
				insuranceMasterObj.setModifiedDate(new Date());
				insuranceMasterObj.setIsActive(Constants.D);
				insuranceMasterObj.setRemarks(insuranceDataTableObj.getRemarks());
				insuranceMasterObj.setApprovedDate(null);
				insuranceMasterObj.setApprovedBy(null);
			}
			insuranceService.saveOrUpadate(insuranceMasterObj);
			
			InsuranceMasterDescription insuranceDescObj = new InsuranceMasterDescription();
			LanguageType languageTypeSec = new LanguageType();
			languageTypeSec.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			insuranceDescObj.setLanguageType(languageTypeSec);
			insuranceDescObj.setInsuranceMasterDescId(insuranceDataTableObj.getInsuranceMasterDescPk());
			insuranceDescObj.setInsurancePrimaryMessage(insuranceDataTableObj.getInsuranceMsgOne());
			insuranceDescObj.setInsuranceSecondaryMessage(insuranceDataTableObj.getInsuranceMsgTwo());
			insuranceDescObj.setInsuranceMasterId(insuranceMasterObj);
			insuranceService.saveOrUpdate(insuranceDescObj);
			InsuranceMasterDescription insuranceDescArabicObj = new InsuranceMasterDescription();
			LanguageType languageTypeSecArabic = new LanguageType();
			languageTypeSecArabic.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			insuranceDescArabicObj.setLanguageType(languageTypeSecArabic);
			insuranceDescArabicObj.setInsuranceMasterDescId(insuranceDataTableObj.getInsuranceMasterDescArabicPk());
			insuranceDescArabicObj.setInsurancePrimaryMessage(insuranceDataTableObj.getInsuranceArabicMsgOne());
			insuranceDescArabicObj.setInsuranceSecondaryMessage(insuranceDataTableObj.getInsuranceArabicMsgTwo());
			insuranceDescArabicObj.setInsuranceMasterId(insuranceMasterObj);
			insuranceService.saveOrUpdate(insuranceDescArabicObj);
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void remarkSelectedRecord() throws IOException {
		try {
			setBooVisible(false);
			log.info("::::::::::::::::::remarkSelectedRecord() method called:::::::::::::::");
			for (InsuranceSetUpDataTableBean insuranceDTObj : insuranceDTList) {
				if (insuranceDTObj.getRemarkCheck() != null) {
					if (insuranceDTObj.getRemarkCheck().equals(true)) {
						insuranceDTObj.setRemarks(getRemarks());
						removeRecord(insuranceDTObj);
						setRemarks(null);
					}
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

	public void confirmPermanentDelete() {
		try {
			setBooVisible(false);
			if (insuranceDTList.size() > 0) {
				for (InsuranceSetUpDataTableBean insuranceDatatableObj : insuranceDTList) {
					if (insuranceDatatableObj.getBooCheckDelete()) {
						removeRecordFromDB(insuranceDatatableObj);
						insuranceDTList.clear();
						viewAll();
						insuranceDTList.addAll(viewList);
					}
					if (insuranceDTList.size() <= 0) {
						setBooRenderDataTable(false);
						setBooRenderSubmit(false);
					}
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

	public void activateRecord() {
		try {
			setBooVisible(false);
			if (insuranceDTList.size() > 0) {
				for (InsuranceSetUpDataTableBean insuranceMasterDTObj : insuranceDTList) {
					if (insuranceMasterDTObj.getBooActivate()) {
						deActivateRecord(insuranceMasterDTObj);
						insuranceDTList.clear();
						viewAll();
						insuranceDTList.addAll(viewList);
					}
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

	public void cancelRemarks() {
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/medicalinsurancesetup.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void duplicateCheck() {
		try {
			if (insuranceDTList.size() != 0) {
				for (InsuranceSetUpDataTableBean insuranceDt : insuranceDTList) {
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					if (insuranceDt.getEffectiveFromDate().equalsIgnoreCase(format.format(getEffectiveFromDate())) && insuranceDt.getEffectiveToDate().equalsIgnoreCase(format.format(getEffectiveToDate()))) {
						clearAll();
						RequestContext.getCurrentInstance().execute("dataexist.show();");
					}
				}
			}
			if (getEffectiveFromDate() != null && getEffectiveToDate() != null) {
				addRecordsToDataTable();
				viewList.clear();
			}
		} catch (Exception e) {


			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		
		
		}
	}
}
