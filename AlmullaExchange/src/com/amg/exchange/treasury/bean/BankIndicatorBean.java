package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankIndicatorDescription;
import com.amg.exchange.treasury.service.IBankIndicatorService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;

@Component("bankIndicatorBean")
@Scope("session")
public class BankIndicatorBean<T> implements Serializable {
	private static Logger log = Logger.getLogger(BankIndicatorBean.class);
	private static final long serialVersionUID = 3066984316659765679L;
	private String bankIndicatorDescInLocal;
	private String bankIndicatorDescInEnglish;
	private String bankIndicatorCode;
	private BigDecimal bankIndicatorMasterPk;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private boolean booReadOnly;
	private boolean booRenderDatatable;
	private boolean booRenderSave;
	private String dynamicLabelForActivateDeactivate;
	private Boolean booRenderEditButton;
	private Boolean booSubmit;
	private Boolean checkSave;
	private Boolean booRenderApprove;
	private BigDecimal bankIndicatorEnglishPk;
	private BigDecimal bankIndicatorLocalPk;
	private boolean booSubmitHide = false;
	private boolean hideEdit = false;
	private boolean booClear = false;
	private Boolean booCheckUpdate = false;
	private SessionStateManage sessionStateManage = new SessionStateManage();
	List<BankIndicatorDescription> allRecList = new ArrayList<BankIndicatorDescription>();
	List<BankIndicatorDescription> bankIndDescList = new ArrayList<BankIndicatorDescription>();
	List<BankIndicatorDataTableBean> bankIndDTList = new CopyOnWriteArrayList<BankIndicatorDataTableBean>();
	List<BankIndicatorDataTableBean> banIndViewList = new CopyOnWriteArrayList<>();
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IBankIndicatorService ibankIndicatorService;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getBankIndicatorMasterPk() {
		return bankIndicatorMasterPk;
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

	public boolean isBooClear() {
		return booClear;
	}

	public void setBooClear(boolean booClear) {
		this.booClear = booClear;
	}

	public void setBankIndicatorMasterPk(BigDecimal bankIndicatorMasterPk) {
		this.bankIndicatorMasterPk = bankIndicatorMasterPk;
	}

	public String getBankIndicatorDescInLocal() {
		return bankIndicatorDescInLocal;
	}

	public void setBankIndicatorDescInLocal(String bankIndicatorDescInLocal) {
		this.bankIndicatorDescInLocal = bankIndicatorDescInLocal;
	}

	public String getBankIndicatorDescInEnglish() {
		return bankIndicatorDescInEnglish;
	}

	public void setBankIndicatorDescInEnglish(String bankIndicatorDescInEnglish) {
		this.bankIndicatorDescInEnglish = bankIndicatorDescInEnglish;
	}

	public String getBankIndicatorCode() {
		return bankIndicatorCode;
	}

	public void setBankIndicatorCode(String bankIndicatorCode) {
		this.bankIndicatorCode = bankIndicatorCode;
	}

	public SessionStateManage getSessionStateManage() {
		return sessionStateManage;
	}

	public void setSessionStateManage(SessionStateManage sessionStateManage) {
		this.sessionStateManage = sessionStateManage;
	}

	public boolean isBooReadOnly() {
		return booReadOnly;
	}

	public void setBooReadOnly(boolean booReadOnly) {
		this.booReadOnly = booReadOnly;
	}

	public boolean isBooRenderDatatable() {
		return booRenderDatatable;
	}

	public void setBooRenderDatatable(boolean booRenderDatatable) {
		this.booRenderDatatable = booRenderDatatable;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public boolean isBooRenderSave() {
		return booRenderSave;
	}

	public void setBooRenderSave(boolean booRenderSave) {
		this.booRenderSave = booRenderSave;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getBooRenderEditButton() {
		return booRenderEditButton;
	}

	public void setBooRenderEditButton(Boolean booRenderEditButton) {
		this.booRenderEditButton = booRenderEditButton;
	}

	public Boolean getBooSubmit() {
		return booSubmit;
	}

	public void setBooSubmit(Boolean booSubmit) {
		this.booSubmit = booSubmit;
	}

	public BigDecimal getBankIndicatorEnglishPk() {
		return bankIndicatorEnglishPk;
	}

	public void setBankIndicatorEnglishPk(BigDecimal bankIndicatorEnglishPk) {
		this.bankIndicatorEnglishPk = bankIndicatorEnglishPk;
	}

	public BigDecimal getBankIndicatorLocalPk() {
		return bankIndicatorLocalPk;
	}

	public void setBankIndicatorLocalPk(BigDecimal bankIndicatorLocalPk) {
		this.bankIndicatorLocalPk = bankIndicatorLocalPk;
	}

	public Boolean getCheckSave() {
		return checkSave;
	}

	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
	}

	public Boolean getBooRenderApprove() {
		return booRenderApprove;
	}

	public void setBooRenderApprove(Boolean booRenderApprove) {
		this.booRenderApprove = booRenderApprove;
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

	public List<BankIndicatorDataTableBean> getBankIndDTList() {
		return bankIndDTList;
	}

	public void setBankIndDTList(List<BankIndicatorDataTableBean> bankIndDTList) {
		this.bankIndDTList = bankIndDTList;
	}

	public boolean isBooSubmitHide() {
		return booSubmitHide;
	}

	public void setBooSubmitHide(boolean booSubmitHide) {
		this.booSubmitHide = booSubmitHide;
	}

	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}

	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}

	public boolean isHideEdit() {
		return hideEdit;
	}

	public void setHideEdit(boolean hideEdit) {
		this.hideEdit = hideEdit;
	}

	public void hideSubmitt() {
		setBooSubmitHide(true);
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void bankIndicatorPageNavigation() {
		log.info(":::::::::Entered into bankIndicatorPageNavigation() method::::::::");
		clearAll();
		setBooClear(false);
		setBooRenderDatatable(false);
		setBooRenderSave(true);
		setBooRenderApprove(false);
		setBooSubmit(false);
		setBooReadOnly(false);
		banIndViewList.clear();
		bankIndDTList.clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankIndicatorMaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankIndicatorMaster.xhtml");
		} catch (Exception e) {
			log.error("Problem Occured in relationsPageNavigation() method");
		}
	}

	public void clickOnOKSave() {
		bankIndDTList.clear();
		banIndViewList.clear();
		setBooRenderDatatable(false);
		setBooRenderSave(true);
		setBooSubmit(false);
		clearAll();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankIndicatorMaster.xhtml");
		} catch (Exception e) {
			log.error("::::::::::::::::::Problem Occured in clickOnOKSave()::::::::::::::");
		}
	}

	// to populate the bankIndicator codes
	public List<String> autoCompleteData(String query) {
		if (query.length() > 0) {
			List<String> list = null;
			try {
				list = ibankIndicatorService.getBankIndicatorCodeFromDB(query);
			} catch (Exception e) {
				RequestContext.getCurrentInstance().execute("error.show();");
				setErrorMessage(e.toString());
				return null;
			}
			return list;
		} else {
			return null;
		}
	}

	// add records to datatable
	public void addRecordsToDataTable() {
		try {
			setBooSubmitHide(false);
			if (banIndViewList.size() == 0) {
				// List<BankIndicatorDescription> bankIndlist =
				// ibankIndicatorService.getAllRecordsForDuplicateDescCheck(getBankIndicatorDescInEnglish());
				BankIndicatorDataTableBean bankIndDT = new BankIndicatorDataTableBean();
				bankIndDT.setBankIndicatorId(getBankIndicatorMasterPk());
				bankIndDT.setBankIndicatorCode(getBankIndicatorCode());
				bankIndDT.setBankIndicatorDescInEnglish(getBankIndicatorDescInEnglish());
				bankIndDT.setBankIndicatorDescInLocal(getBankIndicatorDescInLocal());
				bankIndDT.setBankIndicatorEnglishDecPk(getBankIndicatorEnglishPk());
				bankIndDT.setBankIndicatorLocalDecPk(getBankIndicatorLocalPk());
				if (getBankIndicatorMasterPk() != null) {
					bankIndDT.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					bankIndDT.setRenderEditButton(getBooRenderEditButton());
					bankIndDT.setIsActive(getIsActive());
					bankIndDT.setRemarks(getRemarks());
					bankIndDT.setCheckSave(getCheckSave());
					bankIndDT.setCreatedBy(getCreatedBy());
					bankIndDT.setCreatedDate(getCreatedDate());
					bankIndDT.setApprovedBy(getApprovedBy());
					bankIndDT.setApprovedDate(getApprovedDate());
					bankIndDT.setBooCheckUpdate(getBooCheckUpdate());
				} else {
					bankIndDT.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
					bankIndDT.setRenderEditButton(true);
					bankIndDT.setIsActive(Constants.U);
					bankIndDT.setCheckSave(true);
					bankIndDT.setCreatedBy(getSessionStateManage().getUserName());
					bankIndDT.setCreatedDate(new Date());
					bankIndDT.setBooCheckUpdate(true);
				}
				bankIndDTList.add(bankIndDT);
			}
			if (bankIndDTList.size() > 0) {
				if (banIndViewList.size() > 0) {
					for (BankIndicatorDataTableBean relationDt : bankIndDTList) {
						for (BankIndicatorDataTableBean relationListDt : banIndViewList) {
							if (relationDt.getBankIndicatorCode().equals(relationListDt.getBankIndicatorCode())) {
								banIndViewList.remove(relationListDt);
							}
						}
					}
				}
				bankIndDTList.addAll(banIndViewList);
			} else {
				bankIndDTList.addAll(banIndViewList);
			}
			clearAll();
			setBooRenderDatatable(true);
			setBooRenderSave(true);
			setBooSubmit(true);
			setHideEdit(false);
			setBooClear(false);
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
		}
	}
	
	
	private Boolean booCheckDelete = false;
	
	
	
	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}

	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}

	public void confirmPermanentDelete() {
		if (bankIndDTList.size() > 0) {
			for (BankIndicatorDataTableBean bankIndicatorDataTableBean : bankIndDTList) {
				if (bankIndicatorDataTableBean.getBooCheckDelete()) {
					try {
						removeRecordFromDB(bankIndicatorDataTableBean);
					} catch (Exception e) {
						RequestContext.getCurrentInstance().execute("error.show();");
						setErrorMessage(e.toString());
						return;
					}
				}
			}
		}
	}
	
	

	public void checkStatusType(BankIndicatorDataTableBean bankIndMasterDTObj) {
		try {
			if (bankIndMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				bankIndMasterDTObj.setRemarkCheck(true);
				setApprovedBy(bankIndMasterDTObj.getApprovedBy());
				setApprovedDate(bankIndMasterDTObj.getApprovedDate());
				RequestContext.getCurrentInstance().execute("remarks.show();");
			} else if (bankIndMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				removeRecord(bankIndMasterDTObj);
			}
			else if (bankIndMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && bankIndMasterDTObj.getRemarks() == null && bankIndMasterDTObj.getApprovedBy() == null && bankIndMasterDTObj.getApprovedDate() == null
					&& bankIndMasterDTObj.getModifiedBy() == null && bankIndMasterDTObj.getModifiedDate() == null && bankIndMasterDTObj.getRemarks() == null) {
			//	bankIndMasterDTObj.setBooCheckDelete(true);
				RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			}
			else if (bankIndMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && bankIndMasterDTObj.getRemarks() == null && bankIndMasterDTObj.getApprovedBy() == null && bankIndMasterDTObj.getApprovedDate() == null && bankIndMasterDTObj.getModifiedBy() == null
					&& bankIndMasterDTObj.getModifiedDate() == null) {
				removeRecordFromDB(bankIndMasterDTObj);
			} else if (bankIndMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
				removeRecord(bankIndMasterDTObj);
			} else if (bankIndMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)) {
				RequestContext.getCurrentInstance().execute("couldnot.show();");
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
		}
	}

	public void removeRecordFromDB(BankIndicatorDataTableBean bankIndDTObj) throws Exception {
		try {
			BankIndicatorDescription bankIndDescription = new BankIndicatorDescription();
			LanguageType languageType = new LanguageType();
			languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			bankIndDescription.setLanguageType(languageType);
			bankIndDescription.setBankIndicatorDescId(bankIndDTObj.getBankIndicatorEnglishDecPk());
			bankIndDescription.setBankIndicatorDescription(bankIndDTObj.getBankIndicatorDescInEnglish());
			// bankIndDescription.setBankIndicator(bankIndicator);
			ibankIndicatorService.delete(bankIndDescription);
			BankIndicatorDescription bankIndDesc = new BankIndicatorDescription();
			LanguageType languageTypeSec = new LanguageType();
			languageTypeSec.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			bankIndDesc.setLanguageType(languageTypeSec);
			bankIndDesc.setBankIndicatorDescId(bankIndDTObj.getBankIndicatorLocalDecPk());
			bankIndDesc.setBankIndicatorDescription(bankIndDTObj.getBankIndicatorDescInLocal());
			// bankIndDesc.setBankIndicator(bankIndicator);
			ibankIndicatorService.delete(bankIndDesc);
			BankIndicator bankIndicator = new BankIndicator();
			bankIndicator.setBankIndicatorId(bankIndDTObj.getBankIndicatorId());
			bankIndicator.setBankIndicatorCode(bankIndDTObj.getBankIndicatorCode());
			bankIndicator.setCreatedBy(bankIndDTObj.getCreatedBy());
			bankIndDTObj.setCreatedDate(bankIndDTObj.getCreatedDate());
			bankIndicator.setRemarks(bankIndDTObj.getRemarks());
			bankIndicator.setModifiedDate(bankIndDTObj.getModifiedDate());
			bankIndicator.setIsActive(bankIndDTObj.getIsActive());
			bankIndicator.setModifiedBy(bankIndDTObj.getModifiedBy());
			bankIndicator.setApprovedBy(bankIndDTObj.getApprovedBy());
			bankIndicator.setApprovedDate(bankIndDTObj.getApprovedDate());
			ibankIndicatorService.delete(bankIndicator);
			bankIndDTList.remove(bankIndDTObj);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public void duplicateCheck() {
		if (bankIndDTList.size() != 0) {
			for (BankIndicatorDataTableBean bankIndDt : bankIndDTList) {
				if (bankIndDt.getBankIndicatorCode().equals(getBankIndicatorCode())) {
					setBankIndicatorCode(null);
					setBankIndicatorDescInLocal(null);
					setBankIndicatorDescInEnglish(null);
					// RequestContext.getCurrentInstance().execute("succ.show();");
					RequestContext.getCurrentInstance().execute("error.show();");
					setErrorMessage(WarningHandler.showWarningMessage("lbl.thisBankIndicatorAlreadyExist", new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
				} else {
					if (bankIndDt.getBankIndicatorDescInEnglish().equals(getBankIndicatorDescInEnglish())) {
						setBankIndicatorCode(null);
						setBankIndicatorDescInLocal(null);
						setBankIndicatorDescInEnglish(null);
						setBooSubmitHide(false);
						RequestContext.getCurrentInstance().execute("error.show();");
						setErrorMessage(WarningHandler.showWarningMessage("lbl.bankindicatoralreadyexist", new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
					}
				}
			}
		}
		if (getBankIndicatorCode() != null) {
			addRecordsToDataTable();
			banIndViewList.clear();
		}
	}

	public void editRecord(BankIndicatorDataTableBean bankIndDatatableObj) {
		log.info("::::::::::::::::::::::::::::Entered into editRecord()::::::::::::::::::");
		setBankIndicatorMasterPk(bankIndDatatableObj.getBankIndicatorId());
		setBankIndicatorEnglishPk(bankIndDatatableObj.getBankIndicatorEnglishDecPk());
		setBankIndicatorLocalPk(bankIndDatatableObj.getBankIndicatorLocalDecPk());
		setBankIndicatorCode(bankIndDatatableObj.getBankIndicatorCode());
		setDynamicLabelForActivateDeactivate(bankIndDatatableObj.getDynamicLabelForActivateDeactivate());
		setBankIndicatorDescInEnglish(bankIndDatatableObj.getBankIndicatorDescInEnglish());
		setBankIndicatorDescInLocal(bankIndDatatableObj.getBankIndicatorDescInLocal());
		setBooRenderEditButton(bankIndDatatableObj.getRenderEditButton());
		setIsActive(Constants.U);
		setCheckSave(bankIndDatatableObj.getCheckSave());
		setBooCheckUpdate(true);
		setBooCheckDelete(true);
		setCreatedBy(bankIndDatatableObj.getCreatedBy());
		setCreatedDate(bankIndDatatableObj.getCreatedDate());
		/*
		 * setApprovedBy(bankIndDatatableObj.getApprovedBy() );
		 * setApprovedDate(bankIndDatatableObj.getApprovedDate() );
		 * setModifiedBy( bankIndDatatableObj.getModifiedBy()); setModifiedDate(
		 * bankIndDatatableObj.getModifiedDate());
		 */
		bankIndDTList.remove(bankIndDatatableObj);
		setHideEdit(true);
		setBooSubmit(true);
		setBooSubmitHide(true);
		setBooClear(true);
		if (bankIndDTList.size() == 0) {
			setBooRenderSave(true);
			setBooRenderDatatable(true);
		}
	}

	public void removeRecord(BankIndicatorDataTableBean bankIndMasterDTObj) {
		if (bankIndMasterDTObj.getBankIndicatorId() == null) {
			if (bankIndMasterDTObj.getCheckSave().equals(true)) {
				bankIndDTList.remove(bankIndMasterDTObj);
				if (bankIndDTList.size() <= 0) {
					setBooRenderSave(true);
					setBooRenderDatatable(false);
					setBooSubmit(false);
				}
			}
		} else {
			deActiveRecord(bankIndMasterDTObj);
			bankIndDTList.clear();
			viewAllRecords();
			bankIndDTList.addAll(banIndViewList);
		}
	}

	public void deActiveRecord(BankIndicatorDataTableBean bankIndDTObj) {
		log.info("::::::::::::::::::::::::::::::Entered into deActiveRecord() method::::::::::::::::::::::::");
		try {
			BankIndicator bankIndicator = new BankIndicator();
			bankIndicator.setBankIndicatorId(bankIndDTObj.getBankIndicatorId());
			bankIndicator.setBankIndicatorCode(bankIndDTObj.getBankIndicatorCode());
			if (bankIndDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				bankIndicator.setIsActive(Constants.U);
			} else if (bankIndDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				bankIndicator.setIsActive(Constants.D);
				bankIndicator.setRemarks(bankIndDTObj.getRemarks());
			}
			if (bankIndDTObj.getBankIndicatorId() != null) {
				bankIndicator.setModifiedDate(new Date());
				bankIndicator.setModifiedBy(getSessionStateManage().getUserName());
			}
			bankIndicator.setCreatedBy(getSessionStateManage().getUserName());
			bankIndicator.setCreatedDate(new Date());
			ibankIndicatorService.saveOrUpdate(bankIndicator);
			BankIndicatorDescription bankIndDescription = new BankIndicatorDescription();
			LanguageType languageType = new LanguageType();
			languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			bankIndDescription.setLanguageType(languageType);
			bankIndDescription.setBankIndicatorDescId(bankIndDTObj.getBankIndicatorEnglishDecPk());
			bankIndDescription.setBankIndicatorDescription(bankIndDTObj.getBankIndicatorDescInEnglish());
			bankIndDescription.setBankIndicator(bankIndicator);
			ibankIndicatorService.saveOrUpdate(bankIndDescription);
			BankIndicatorDescription bankIndDesc = new BankIndicatorDescription();
			LanguageType languageTypeSec = new LanguageType();
			languageTypeSec.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			bankIndDesc.setLanguageType(languageTypeSec);
			bankIndDesc.setBankIndicatorDescId(bankIndDTObj.getBankIndicatorLocalDecPk());
			bankIndDesc.setBankIndicatorDescription(bankIndDTObj.getBankIndicatorDescInLocal());
			bankIndDesc.setBankIndicator(bankIndicator);
			ibankIndicatorService.saveOrUpdate(bankIndDesc);
		} catch (Exception e) {
			log.error("::::::::::::::::::::::Problem occured in deActiveRecord()::::::::::::");
		}
		log.info("::::::::::::::::::::::::::::Exited deActiveRecord() method::::::::::::::::::::");
	}

	public void viewAllRecords() {
		log.info("::::::::::::Entered into viewAllRecords() method:::::::::::::::");
		try {
			setHideEdit(false);
			try {
				allRecList = ibankIndicatorService.getAllRecordsFromDB();
			} catch (Exception e) {
				RequestContext.getCurrentInstance().execute("error.show();");
				setErrorMessage(e.toString());
				return;
			}
			if (allRecList.size() > 0) {
				for (BankIndicatorDescription bankIndDesc : allRecList) {
					BankIndicatorDataTableBean bankIndicatorDataTableBean = new BankIndicatorDataTableBean();
					bankIndicatorDataTableBean.setBankIndicatorId(bankIndDesc.getBankIndicator().getBankIndicatorId());
					bankIndicatorDataTableBean.setBankIndicatorCode(bankIndDesc.getBankIndicator().getBankIndicatorCode());
					bankIndicatorDataTableBean.setIsActive(bankIndDesc.getBankIndicator().getIsActive());
					if (bankIndDesc.getBankIndicator().getIsActive().equalsIgnoreCase(Constants.Yes)) {
						bankIndicatorDataTableBean.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
						bankIndicatorDataTableBean.setRenderEditButton(true);
						bankIndicatorDataTableBean.setCheckSave(false);
					} else if (bankIndDesc.getBankIndicator().getIsActive().equalsIgnoreCase(Constants.D)) {
						bankIndicatorDataTableBean.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
						bankIndicatorDataTableBean.setRemarks(bankIndDesc.getBankIndicator().getRemarks());
						bankIndicatorDataTableBean.setRenderEditButton(false);
						bankIndicatorDataTableBean.setCheckSave(false);
					} else if (bankIndDesc.getBankIndicator().getIsActive().equalsIgnoreCase(Constants.U) && bankIndDesc.getBankIndicator().getModifiedBy() == null && bankIndDesc.getBankIndicator().getModifiedDate() == null && bankIndDesc.getBankIndicator().getApprovedBy() == null
							&& bankIndDesc.getBankIndicator().getApprovedDate() == null && bankIndDesc.getBankIndicator().getRemarks() == null) {
						bankIndicatorDataTableBean.setDynamicLabelForActivateDeactivate(Constants.DELETE);
						// relationsDT.setRemarks(relationsDesc.getRelations().getRemarks());
						bankIndicatorDataTableBean.setRenderEditButton(true);
						bankIndicatorDataTableBean.setCheckSave(false);
						bankIndicatorDataTableBean.setBooCheckDelete(false);
					}
					 else {
						 bankIndicatorDataTableBean.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						 bankIndicatorDataTableBean.setRenderEditButton(true);
						 bankIndicatorDataTableBean.setBooCheckDelete(false);
						 bankIndicatorDataTableBean.setCheckSave(false);
						}
					if (bankIndDesc.getBankIndicator().getBankIndicatorId() != null) {
						bankIndicatorDataTableBean.setModifiedBy(bankIndDesc.getBankIndicator().getModifiedBy());
						bankIndicatorDataTableBean.setModifiedDate(bankIndDesc.getBankIndicator().getModifiedDate());
						bankIndicatorDataTableBean.setBooCheckUpdate(false);
					}
					bankIndicatorDataTableBean.setApprovedBy(bankIndDesc.getBankIndicator().getApprovedBy());
					bankIndicatorDataTableBean.setApprovedDate(bankIndDesc.getBankIndicator().getApprovedDate());
					bankIndicatorDataTableBean.setCreatedBy(bankIndDesc.getBankIndicator().getCreatedBy());
					bankIndicatorDataTableBean.setCreatedDate(bankIndDesc.getBankIndicator().getCreatedDate());
					if (bankIndDesc.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID)) {
						bankIndicatorDataTableBean.setBankIndicatorEnglishDecPk(bankIndDesc.getBankIndicatorDescId());
						bankIndicatorDataTableBean.setBankIndicatorDescInEnglish(bankIndDesc.getBankIndicatorDescription());
						if (bankIndicatorDataTableBean.getBankIndicatorEnglishDecPk() != null && bankIndicatorDataTableBean.getBankIndicatorLocalDecPk() != null) {
							banIndViewList.add(bankIndicatorDataTableBean);
						}
					}
					for (BankIndicatorDescription bankIndDescSec : allRecList) {
						if (bankIndicatorDataTableBean.getBankIndicatorCode().equals(bankIndDescSec.getBankIndicator().getBankIndicatorCode()) && bankIndDescSec.getLanguageType().getLanguageId().toString().equals(Constants.ARABIC_LANGUAGE_ID)) {
							bankIndicatorDataTableBean.setBankIndicatorLocalDecPk(bankIndDescSec.getBankIndicatorDescId());
							bankIndicatorDataTableBean.setBankIndicatorDescInLocal(bankIndDescSec.getBankIndicatorDescription());
							if (bankIndicatorDataTableBean.getBankIndicatorDescInLocal() != null && bankIndicatorDataTableBean.getBankIndicatorDescInEnglish() != null) {
								banIndViewList.add(bankIndicatorDataTableBean);
							}
						}
					}
				}
			}
			if (banIndViewList.size() > 0) {
				addRecordsToDataTable();
				banIndViewList.clear();
			} else {
				RequestContext.getCurrentInstance().execute("exist.show();");
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			return;
		}
		log.info("::::::::::::::::::Exit from viewAllRecords() method::::::::::::::::::");
	}

	public void clearAll() {
		setBankIndicatorMasterPk(null);
		setBankIndicatorDescInEnglish(null);
		setBankIndicatorDescInLocal(null);
		setBankIndicatorLocalPk(null);
		setBankIndicatorEnglishPk(null);
		setBankIndicatorCode(null);
		setDynamicLabelForActivateDeactivate(null);
		setBooSubmitHide(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankIndicatorMaster.xhtml");
		} catch (IOException e) {
			log.error(":::::::::::::::::::::::::::::::::::::::::::Problem Occured in clickOnOKSave():::::::::::::::::::::::::::");
		}
	}

	public void cancel() {
		banIndViewList.clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankIndicatorApproval.xhtml");
		} catch (Exception e) {
			log.error(":::::::::::::::::::::::::::::::::::::::::::Problem Occured in cancel():::::::::::::::::::::::::::");
		}
	}

	public void exit() {
		try {
			List<RoleMaster> rolList = generalService.getRoleList(new BigDecimal(sessionStateManage.getRoleId()));
			if (rolList != null && !rolList.isEmpty() && rolList.get(0).getRoleName().equalsIgnoreCase(Constants.USER_ROLE_MANAGER)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		} catch (Exception e) {
			log.error(":::::::::::::::::::::::::::::::::::::::::::Problem Occured in exit():::::::::::::::::::::::::::");
		}
	}

	public void approvalRecords() {
		try {
			if (ibankIndicatorService.approve(getBankIndicatorMasterPk(), getSessionStateManage().getUserName()).equalsIgnoreCase("Success")) {
				RequestContext.getCurrentInstance().execute("approv.show();");
			} else {
				RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
		}
	}

	public void saveDataTableRecords() {
		log.info(":::::::::::::::::::Entered into saveDataTableRecords() method::::::::::::::");
		if (bankIndDTList.isEmpty()) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(WarningHandler.showWarningMessage("pleaseAddAtleastOneRecordToDb", new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
			return;
		} else {
			try {
				for (BankIndicatorDataTableBean bankIndDatatableDtObj : bankIndDTList) {
					BankIndicator bankIndObj = new BankIndicator();
					bankIndObj.setBankIndicatorId(bankIndDatatableDtObj.getBankIndicatorId());
					bankIndObj.setBankIndicatorCode(bankIndDatatableDtObj.getBankIndicatorCode());
					if (bankIndDatatableDtObj.getBankIndicatorId() != null) {
						bankIndObj.setModifiedBy(getSessionStateManage().getUserName());
						bankIndObj.setModifiedDate(new Date());
						bankIndObj.setCreatedBy(bankIndDatatableDtObj.getCreatedBy());
						bankIndObj.setCreatedDate(bankIndDatatableDtObj.getCreatedDate());
						// bankIndObj.setApprovedBy(null);
						// bankIndObj.setApprovedDate(null);
						// bankIndObj.setIsActive("U");
					} else {
						bankIndObj.setCreatedBy(getSessionStateManage().getUserName());
						bankIndObj.setCreatedDate(new Date());
					}
					bankIndObj.setApprovedBy(bankIndDatatableDtObj.getApprovedBy());
					bankIndObj.setApprovedDate(bankIndDatatableDtObj.getApprovedDate());
					bankIndObj.setIsActive(bankIndDatatableDtObj.getIsActive());
					bankIndObj.setRemarks(bankIndDatatableDtObj.getRemarks());
					if (bankIndDatatableDtObj.getBooCheckUpdate()) {
						ibankIndicatorService.saveOrUpdate(bankIndObj);
					}
					BankIndicatorDescription bankIndDesc = new BankIndicatorDescription();
					LanguageType languageType = new LanguageType();
					languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
					bankIndDesc.setLanguageType(languageType);
					bankIndDesc.setBankIndicatorDescId(bankIndDatatableDtObj.getBankIndicatorEnglishDecPk());
					bankIndDesc.setBankIndicatorDescription(bankIndDatatableDtObj.getBankIndicatorDescInEnglish());
					bankIndDesc.setBankIndicator(bankIndObj);
					if (bankIndDatatableDtObj.getBooCheckUpdate()) {
						ibankIndicatorService.saveOrUpdate(bankIndDesc);
					}
					BankIndicatorDescription bankIndDescSecondObj = new BankIndicatorDescription();
					LanguageType languageType1 = new LanguageType();
					languageType1.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
					bankIndDescSecondObj.setLanguageType(languageType1);
					bankIndDescSecondObj.setBankIndicatorDescId(bankIndDatatableDtObj.getBankIndicatorLocalDecPk());
					bankIndDescSecondObj.setBankIndicatorDescription(bankIndDatatableDtObj.getBankIndicatorDescInLocal());
					bankIndDescSecondObj.setBankIndicator(bankIndObj);
					if (bankIndDatatableDtObj.getBooCheckUpdate()) {
						ibankIndicatorService.saveOrUpdate(bankIndDescSecondObj);
					}
				}
				RequestContext.getCurrentInstance().execute("complete.show();");
			} catch (Exception e) {
				RequestContext.getCurrentInstance().execute("error.show();");
				setErrorMessage(e.toString());
				return;
			}
		}
		bankIndDTList.clear();
	}

	public void remarkSelectedRecord() throws IOException {
		log.info("::::::::::::::::::remarkSelectedRecord() method called:::::::::::::::");
		for (BankIndicatorDataTableBean bankIndMasterDTObj : bankIndDTList) {
			if (bankIndMasterDTObj.getRemarkCheck() != null) {
				if (bankIndMasterDTObj.getRemarkCheck().equals(true)) {
					if (getRemarks() != null && !getRemarks().trim().equals("")) {
						bankIndMasterDTObj.setRemarks(getRemarks());
						removeRecord(bankIndMasterDTObj);
						setRemarks(null);
					} else {
						RequestContext.getCurrentInstance().execute("error.show();");
						setErrorMessage(WarningHandler.showWarningMessage("lbl.art.remarks", new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
						return;
					}
				}
			}
		}
	}

	public void populateValues() {
		try {
			setBankIndicatorMasterPk(null);
			setBankIndicatorLocalPk(null);
			setBankIndicatorEnglishPk(null);
			setBankIndicatorDescInEnglish(null);
			setBankIndicatorDescInLocal(null);
			setDynamicLabelForActivateDeactivate(null);
			if (getBankIndicatorCode() != null) {
				List<BankIndicator> bankIndlist = ibankIndicatorService.getRecordFromDB(getBankIndicatorCode());
				if (bankIndlist.size() != 0) {
					if (bankIndlist.get(0).getIsActive().equalsIgnoreCase(Constants.Yes)) {
						setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
						setBooRenderEditButton(true);
						setCheckSave(false);
						setApprovedBy(bankIndlist.get(0).getApprovedBy());
						setApprovedDate(bankIndlist.get(0).getApprovedDate());
					} else if (bankIndlist.get(0).getIsActive().equalsIgnoreCase(Constants.D)) {
						setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
						setBooRenderEditButton(false);
						setCheckSave(false);
					} else if (bankIndlist.get(0).getIsActive().equalsIgnoreCase(Constants.U)) {
						setDynamicLabelForActivateDeactivate(Constants.DELETE);
						setBooRenderEditButton(false);
						setCheckSave(false);
					}
					setBankIndicatorMasterPk(bankIndlist.get(0).getBankIndicatorId());
					setIsActive(bankIndlist.get(0).getIsActive());
					List<BankIndicatorDescription> bankIndDescLIST = ibankIndicatorService.getDescriptionRecordFromDB(bankIndlist.get(0).getBankIndicatorId());
					for (BankIndicatorDescription bankIndDescription : bankIndDescLIST) {
						if (bankIndDescription.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID)) {
							setBankIndicatorEnglishPk(bankIndDescription.getBankIndicatorDescId());
							setBankIndicatorDescInEnglish(bankIndDescription.getBankIndicatorDescription());
						} else {
							setBankIndicatorLocalPk(bankIndDescription.getBankIndicatorDescId());
							setBankIndicatorDescInLocal(bankIndDescription.getBankIndicatorDescription());
						}
					}
				} else {
					setBankIndicatorMasterPk(null);
					setBankIndicatorLocalPk(null);
					setBankIndicatorEnglishPk(null);
					setBankIndicatorDescInEnglish(null);
					setBankIndicatorDescInLocal(null);
					setDynamicLabelForActivateDeactivate(null);
				}
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			return;
		}
	}

	public void clickOnOk() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankIndicatorApproval.xhtml");
		} catch (Exception e) {
			log.error(":::::::::::::Problem oocured in clickOnOk()::::::::::" + e.getCause());
		}
	}

	public void cancelRemarks() {
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankIndicatorMaster.xhtml");
		} catch (Exception e) {
			log.error(":::::::::::::Problem oocured in cancelRemarks()::::::::::" + e.getCause());
		}
	}
}
