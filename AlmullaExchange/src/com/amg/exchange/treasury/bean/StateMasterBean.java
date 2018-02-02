package com.amg.exchange.treasury.bean;

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

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.service.IStateMasterService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("stateMasterBean")
@Scope("session")
@SuppressWarnings("unused")
public class StateMasterBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(StateMasterBean.class);
	private int selectType;
	private Boolean booRenderFirstPanel;
	private Boolean booRenderStatePanel;
	private Boolean booRenderFileUploadPanel;
	private String stateCode;
	private BigDecimal countryId;
	private String countryName;
	private BigDecimal stateId;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String englishStateName;
	private String arabicStateName;
	private String dynamicLabelForActivateDeactivate;
	private Boolean renderEditButton = false;
	private BigDecimal stateMasterPK;
	private BigDecimal englishStateDescPK;
	private BigDecimal arabicStateDescPK;
	private Boolean booSaveExit;
	private Boolean booAdd;
	private Boolean booRenderDataTable;
	private Boolean submt = false;
	private Boolean submitPanel;
	private boolean booEditButton;
	private Boolean clearPanel = false;
	private Boolean boohideSecod;
	private Boolean booReadonly;
	private Boolean IfFileUpload = false;
	
	private Boolean booReadonlyField=false;
	
	public Boolean getBooReadonlyField() {
		return booReadonlyField;
	}

	public void setBooReadonlyField(Boolean booReadonlyField) {
		this.booReadonlyField = booReadonlyField;
	}

	private StateMasterDataTable stateMasterDTObj = null;
	private List<StateMasterDataTable> stateMasterDTList = new CopyOnWriteArrayList<StateMasterDataTable>();
	private List<StateMasterDataTable> stateMasterNewDTList = new CopyOnWriteArrayList<StateMasterDataTable>();
	private List<CountryMaster> countryMasterList = new ArrayList<CountryMaster>();
	private List<CountryMasterDesc> countryMasterDescLT = new ArrayList<CountryMasterDesc>();
	private List<StateMaster> stateMasters = new ArrayList<StateMaster>();
	private List<StateMasterDesc> stateDesc = new ArrayList<StateMasterDesc>();
	private Map<BigDecimal, String> countryMasterMap = new HashMap<BigDecimal, String>();
	private SessionStateManage session = new SessionStateManage();
	StateMaster stateMaster = new StateMaster();
	String errmsg;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IStateMasterService<T> stateMasterService;
	@Autowired
	StateMasterBeanFileupload stateMasterBeanFileupload;

	public int getSelectType() {
		return selectType;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public void setSelectType(int selectType) {
		this.selectType = selectType;
	}

	public Boolean getBooRenderFirstPanel() {
		return booRenderFirstPanel;
	}

	public void setBooRenderFirstPanel(Boolean booRenderFirstPanel) {
		this.booRenderFirstPanel = booRenderFirstPanel;
	}

	public Boolean getBooRenderStatePanel() {
		return booRenderStatePanel;
	}

	public void setBooRenderStatePanel(Boolean booRenderStatePanel) {
		this.booRenderStatePanel = booRenderStatePanel;
	}

	public Boolean getBooRenderFileUploadPanel() {
		return booRenderFileUploadPanel;
	}

	public void setBooRenderFileUploadPanel(Boolean booRenderFileUploadPanel) {
		this.booRenderFileUploadPanel = booRenderFileUploadPanel;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
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

	public String getEnglishStateName() {
		return englishStateName;
	}

	public void setEnglishStateName(String englishStateName) {
		this.englishStateName = englishStateName;
	}

	public String getArabicStateName() {
		return arabicStateName;
	}

	public void setArabicStateName(String arabicStateName) {
		this.arabicStateName = arabicStateName;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getRenderEditButton() {
		return renderEditButton;
	}

	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}

	public BigDecimal getStateMasterPK() {
		return stateMasterPK;
	}

	public void setStateMasterPK(BigDecimal stateMasterPK) {
		this.stateMasterPK = stateMasterPK;
	}

	public BigDecimal getEnglishStateDescPK() {
		return englishStateDescPK;
	}

	public void setEnglishStateDescPK(BigDecimal englishStateDescPK) {
		this.englishStateDescPK = englishStateDescPK;
	}

	public BigDecimal getArabicStateDescPK() {
		return arabicStateDescPK;
	}

	public void setArabicStateDescPK(BigDecimal arabicStateDescPK) {
		this.arabicStateDescPK = arabicStateDescPK;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<StateMasterDataTable> getStateMasterDTList() {
		return stateMasterDTList;
	}

	public void setStateMasterDTList(List<StateMasterDataTable> stateMasterDTList) {
		this.stateMasterDTList = stateMasterDTList;
	}

	public List<StateMasterDataTable> getStateMasterNewDTList() {
		return stateMasterNewDTList;
	}

	public void setStateMasterNewDTList(List<StateMasterDataTable> stateMasterNewDTList) {
		this.stateMasterNewDTList = stateMasterNewDTList;
	}

	public List<CountryMaster> getCountryMasterList() {
		return countryMasterList;
	}

	public void setCountryMasterList(List<CountryMaster> countryMasterList) {
		this.countryMasterList = countryMasterList;
	}

	public Boolean getBooSaveExit() {
		return booSaveExit;
	}

	public void setBooSaveExit(Boolean booSaveExit) {
		this.booSaveExit = booSaveExit;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public BigDecimal getStateId() {
		return stateId;
	}

	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}

	public StateMasterDataTable getStateMasterDTObj() {
		return stateMasterDTObj;
	}

	public void setStateMasterDTObj(StateMasterDataTable stateMasterDTObj) {
		this.stateMasterDTObj = stateMasterDTObj;
	}

	public Boolean getIfFileUpload() {
		return IfFileUpload;
	}

	public void setIfFileUpload(Boolean ifFileUpload) {
		IfFileUpload = ifFileUpload;
	}

	public List<CountryMasterDesc> getCountryMasterDescLT() {
		LOGGER.info("Entering into getCountryMasterDescLT method");
		countryMasterDescLT = generalService.getCountryList(session.getLanguageId());
		for (CountryMasterDesc countryMasterDes : countryMasterDescLT) {
			countryMasterMap.put(countryMasterDes.getFsCountryMaster().getCountryId(), countryMasterDes.getCountryName());
		}
		LOGGER.info("Exit into getCountryMasterDescLT method");
		return countryMasterDescLT;
	}

	public void setCountryMasterDescLT(List<CountryMasterDesc> countryMasterDescLT) {
		this.countryMasterDescLT = countryMasterDescLT;
	}

	public Map<BigDecimal, String> getCountryMasterMap() {
		return countryMasterMap;
	}

	public void setCountryMasterMap(Map<BigDecimal, String> countryMasterMap) {
		this.countryMasterMap = countryMasterMap;
	}

	public Boolean getBooAdd() {
		return booAdd;
	}

	public void setBooAdd(Boolean booAdd) {
		this.booAdd = booAdd;
	}

	public List<StateMaster> getStateMasters() {
		return stateMasters;
	}

	public void setStateMasters(List<StateMaster> stateMasters) {
		this.stateMasters = stateMasters;
	}

	public List<StateMasterDesc> getStateDesc() {
		return stateDesc;
	}

	public void setStateDesc(List<StateMasterDesc> stateDesc) {
		this.stateDesc = stateDesc;
	}

	public Boolean getSubmt() {
		return submt;
	}

	public void setSubmt(Boolean submt) {
		this.submt = submt;
	}

	public Boolean getSubmitPanel() {
		return submitPanel;
	}

	public void setSubmitPanel(Boolean submitPanel) {
		this.submitPanel = submitPanel;
	}

	public boolean isBooEditButton() {
		return booEditButton;
	}

	public void setBooEditButton(boolean booEditButton) {
		this.booEditButton = booEditButton;
	}

	public Boolean getClearPanel() {
		return clearPanel;
	}

	public void setClearPanel(Boolean clearPanel) {
		this.clearPanel = clearPanel;
	}

	public Boolean getBoohideSecod() {
		return boohideSecod;
	}

	public void setBoohideSecod(Boolean boohideSecod) {
		this.boohideSecod = boohideSecod;
	}

	public Boolean getBooReadonly() {
		return booReadonly;
	}

	public void setBooReadonly(Boolean booReadonly) {
		this.booReadonly = booReadonly;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// Page Navigation
	public void pageNavigation() {
		LOGGER.info("Entering into pageNavigation method");
		stateMasterDTList.clear();
		stateMasterNewDTList.clear();
		setBooRenderFirstPanel(true);
		setBooRenderStatePanel(false);
		setBooRenderFileUploadPanel(false);
		setBooSaveExit(false);
		setBooAdd(false);
		setClearPanel(false);
		setBoohideSecod(false);
		setBooReadonly(false);
		setBooEditButton( false);
		setBooRenderDataTable(false);
		setBooApprovalDataTable(false);
		setSelectType(0);
		clearAllFields();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "StateMaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/StateMaster.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into pageNavigation method");
	}

	public void exit() {
		LOGGER.info("Entering into exit method");
		try {
			stateMasterDTList.clear();
			stateMasterNewDTList.clear();
			if (session.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into exit method");
	}

	// select type
	public void selectType() {
		LOGGER.info("Entering into selectType method");
		try {
			int typeSelect = getSelectType();
			if (getSelectType() == 1) {
				setBooRenderFirstPanel(false);
				setBooRenderStatePanel(true);
				setBooSaveExit(false);
				setBooAdd(true);
				setBooRenderDataTable(false);
				setBoohideSecod(false);
				setBooRenderFileUploadPanel(false);
			} else if (getSelectType() == 2) {
				setBooRenderFirstPanel(false);
				setBooRenderStatePanel(false);
				setBooRenderFileUploadPanel(true);
				setBoohideSecod(false);
				stateMasterBeanFileupload.setIfFileUpload(false);
				stateMasterBeanFileupload.setBooRenderDataTable(false);
				stateMasterBeanFileupload.setBooSaveExit(false);
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/StateMasterFileupload.xhtml");
			}
		} catch (Exception e) {
		}
		LOGGER.info("Exit into selectType method");
	}

	// Auto Complete method
	public List<String> autoCompleteStateCode(String query) {
		LOGGER.info("Entering into autoCompleteStateCode method");
		if (getCountryId() == null) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please select country ");
			return null;
		}
		if (query.length() > 0) {
			return stateMasterService.getStateListCode(query, getCountryId());
		} else {
			LOGGER.info("Exit into autoCompleteStateCode method");
			return null;
		}
	}

	public void getAlldetailstoList() {
		try {
			LOGGER.info("Entering into getAlldetailstoList method");
			if (stateMasterDTList.size() > 0) {
				for (StateMasterDataTable stateMasterObj : stateMasterDTList) {
					if (stateMasterObj.getCountryId().equals(getCountryId()) && stateMasterObj.getStateCode().equalsIgnoreCase(getStateCode())) {
						clearAllFields();
						RequestContext.getCurrentInstance().execute("datatable.show();");
						return;
					}
				}
			}
			if (getStateCode() != null) {
				addRecordsToDataTable();
			}
			LOGGER.info("Exit into getAlldetailstoList method");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Exception ocuured " + e);
			return;
		}
	}

	public void addRecordsToDataTable() {
		LOGGER.info("Entering into addRecordsToDataTable method");
		setSubmitPanel(false);
		setBooEditButton(false);
		setClearPanel(false);
		boolean alreadyDT = false;
		if (stateMasterDTList.size() != 0) {
			for (StateMasterDataTable dataTable : stateMasterDTList) {
				if (dataTable.getCountryId() != null) {
					if (dataTable.getStateCode() != null && dataTable.getStateCode().equalsIgnoreCase(getStateCode()) && dataTable.getCountryId().equals(getCountryId())) {
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrmsg("The State code already exist");
						alreadyDT = true;
						break;
					} else if (dataTable.getEnglishStateName() != null && dataTable.getEnglishStateName().equalsIgnoreCase(getEnglishStateName()) && dataTable.getCountryId().equals(getCountryId())) {
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrmsg("The State name already exist");
						alreadyDT = true;
						break;
					} else if (dataTable.getArabicStateName() != null && dataTable.getArabicStateName().equalsIgnoreCase(getArabicStateName()) && dataTable.getCountryId().equals(getCountryId())) {
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrmsg("The State name already exist");
						alreadyDT = true;
						break;
					}
				}
			}
		}
		boolean alreadyDB = false;
		if (!alreadyDT) {
			if (getStateId() == null) {
				List<StateMaster> alreadyExist = stateMasterService.toCheckBasedOnCountryAndStateCode(getCountryId(), getStateCode());
				if (alreadyExist != null && alreadyExist.size() != 0) {
					alreadyDB = true;
					RequestContext.getCurrentInstance().execute("csp.show();");
					setErrmsg("The State Code already exist");
					setEnglishStateName(null);
					setArabicStateName(null);
				} else {
					List<StateMasterDesc> listStateEng = stateMasterService.toFetchStateDesc(getEnglishStateName(), getCountryId());
					List<StateMasterDesc> listrArb = stateMasterService.toFetchStateDesc(getEnglishStateName(), getCountryId());
					if (listStateEng != null && listStateEng.size() != 0) {
						alreadyDB = true;
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrmsg("The State name already exist");
						setEnglishStateName(null);
						setArabicStateName(null);
					} else if (listrArb != null && listrArb.size() != 0) {
						alreadyDB = true;
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrmsg("The State name already exist");
						setEnglishStateName(null);
						setArabicStateName(null);
					}
				}
			}
		} else {
			alreadyDB = false;
		}
		if (!alreadyDT && !alreadyDB) {
			/* if (stateMasterNewDTList.size() == 0) { */
			StateMasterDataTable dataTable = new StateMasterDataTable();
			dataTable.setCountryId(getCountryId());
			dataTable.setCountryName(countryMasterMap.get(getCountryId()));
			dataTable.setStateCode(getStateCode());
			dataTable.setStateId(getStateId());
			dataTable.setEnglishStateName(getEnglishStateName());
			dataTable.setEnglishStateDescPK(getEnglishStateDescPK());
			dataTable.setArabicStateName(getArabicStateName());
			dataTable.setArabicStateDescPK(getArabicStateDescPK());
			dataTable.setCreatedBy(getCreatedBy());
			dataTable.setCreatedDate(getCreatedDate());
			dataTable.setRenderEditButton(true);
			if (getStateId() != null) {
				if (stateMasterDTList.size() != 0) {
					if (stateMasterDTObj != null) {
						if (dataTable.getStateCode().equals(stateMasterDTObj.getStateCode()) && dataTable.getEnglishStateName().equals(stateMasterDTObj.getEnglishStateName()) && dataTable.getArabicStateName().equals(stateMasterDTObj.getArabicStateName())) {
							dataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
							dataTable.setModifiedBy(getModifiedBy());
							dataTable.setModifiedDate(getModifiedDate());
							dataTable.setIsActive(getIsActive());
							dataTable.setApprovedBy(getApprovedBy());
							dataTable.setApprovedDate(getApprovedDate());
							dataTable.setRemarks(getRemarks());
						} else {
							dataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
							dataTable.setModifiedBy(session.getUserName());
							dataTable.setModifiedDate(new Date());
							dataTable.setIsActive(Constants.U);
							dataTable.setApprovedBy(null);
							dataTable.setApprovedDate(null);
							dataTable.setRemarks(null);
						}
					} else {
						dataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
						dataTable.setModifiedBy(getModifiedBy());
						dataTable.setModifiedDate(getModifiedDate());
						dataTable.setIsActive(getIsActive());
						dataTable.setApprovedBy(getApprovedBy());
						dataTable.setApprovedDate(getApprovedDate());
						dataTable.setRemarks(getRemarks());
					}
				} else {
					dataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					dataTable.setModifiedBy(getModifiedBy());
					dataTable.setModifiedDate(getModifiedDate());
					dataTable.setIsActive(getIsActive());
					dataTable.setApprovedBy(getApprovedBy());
					dataTable.setApprovedDate(getApprovedDate());
					dataTable.setRemarks(getRemarks());
				}
			} else {
				dataTable.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				dataTable.setCreatedBy(session.getUserName());
				dataTable.setCreatedDate(new Date());
				dataTable.setIsActive(Constants.U);
			}
			stateMasterDTList.add(dataTable);
			if (getStateId() == null) {
				stateMasterNewDTList.add(dataTable);
			}
		}
		clear();
		setBooRenderDataTable(true);
		setBooApprovalDataTable(false);
		setBooSaveExit(true);
		LOGGER.info("Exit into addRecordsToDataTable method");
	}

	public void saveDataTableRecods() {
		LOGGER.info("Entering into saveDataTableRecods method");
		if (stateMasterDTList.isEmpty()) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		} else {
			try {
				for (StateMasterDataTable stateMasterDT : stateMasterDTList) {
					stateMaster.setStateCode(stateMasterDT.getStateCode());
					stateMaster.setStateId(stateMasterDT.getStateId());
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(stateMasterDT.getCountryId());
					stateMaster.setFsCountryMaster(countryMaster);
					stateMaster.setCreatedBy(stateMasterDT.getCreatedBy());
					stateMaster.setApprovedBy(stateMasterDT.getApprovedBy());
					stateMaster.setApprovedDate(stateMasterDT.getApprovedDate());
					stateMaster.setCreatedDate(stateMasterDT.getCreatedDate());
					stateMaster.setIsActive(stateMasterDT.getIsActive());
					stateMaster.setModifiedBy(stateMasterDT.getModifiedBy());
					stateMaster.setModifiedDate(stateMasterDT.getModifiedDate());
					stateMaster.setRemarks(stateMasterDT.getRemarks());
					stateMasterService.save(stateMaster);
					// Eng
					StateMasterDesc stateMasterDes1 = new StateMasterDesc();
					stateMasterDes1.setStateDescId(stateMasterDT.getEnglishStateDescPK());
					stateMasterDes1.setFsStateMaster(stateMaster);
					LanguageType languageType = new LanguageType();
					languageType.setLanguageId(new BigDecimal(1));
					stateMasterDes1.setFsLanguageType(languageType);
					stateMasterDes1.setStateName(stateMasterDT.getEnglishStateName());
					stateMasterService.saverRecordDesc(stateMasterDes1);
					// Ara
					StateMasterDesc stateMasterDes2 = new StateMasterDesc();
					stateMasterDes2.setStateDescId(stateMasterDT.getArabicStateDescPK());
					stateMasterDes2.setFsStateMaster(stateMaster);
					LanguageType languageType2 = new LanguageType();
					languageType2.setLanguageId(new BigDecimal(2));
					stateMasterDes2.setFsLanguageType(languageType2);
					stateMasterDes2.setStateName(stateMasterDT.getArabicStateName());
					stateMasterService.saverRecordDesc(stateMasterDes2);
					// stateMasterService.saveRecord(stateMaster,stateMasterDes1,stateMasterDes2);
				}
				RequestContext.getCurrentInstance().execute("complete.show();");
				clearAllFields();
				stateMasterDTList.clear();
				stateMasterNewDTList.clear();
				setBooApprovalDataTable(false);
			} catch (Exception e) {
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Exception ocuured " + e);
				return;
			}
		}
		LOGGER.info("Exit into saveDataTableRecods method");
	}

	public void clickOnOKSave() {
		LOGGER.info("Entering into clickOnOKSave method");
		setBooReadonly( false);
		setBooReadonlyField(false);
		setBooRenderFirstPanel(false);
		setBooRenderStatePanel(true);
		setBooRenderFileUploadPanel(false);
		setBooSaveExit(false);
		setBooAdd(true);
		setBooRenderDataTable(false);
		clearAllFields();
		setBooApprovalDataTable(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/StateMaster.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into clickOnOKSave method");
	}

	// clear Functionality
	public void clearAllFields() {
		LOGGER.info("Entering into clearAllFields method");
		setCountryId(null);
		setCountryName(null);
		setStateCode(null);
		setStateId(null);
		setStateMasterPK(null);
		setEnglishStateDescPK(null);
		setEnglishStateName(null);
		setArabicStateDescPK(null);
		setArabicStateName(null);
		// setBooRenderDataTable(false);
		LOGGER.info("Exit into clearAllFields method");
	}

	public void clearAllFieldsRedirect() {
		LOGGER.info("Entering into clearAllFields method");
		setCountryId(null);
		setCountryName(null);
		setStateCode(null);
		setStateId(null);
		setStateMasterPK(null);
		setEnglishStateDescPK(null);
		setEnglishStateName(null);
		setArabicStateDescPK(null);
		setArabicStateName(null);
		setBooEditButton(false);
	    setBooReadonly( false);
	    setBooReadonlyField( false);
		// setBooRenderDataTable(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/StateMaster.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into clearAllFields method");
	}

	public void clear() {
		setStateCode(null);
		setStateId(null);
		setStateMasterPK(null);
		setEnglishStateDescPK(null);
		setEnglishStateName(null);
		setArabicStateDescPK(null);
		setArabicStateName(null);
	}

	// submit button hiding
	public void submitButtonHide() {
		LOGGER.info("Entering into submitButtonHide method");
		if (getEnglishStateName() != null || getArabicStateName() != null) {
			setSubmt(true);
		} else {
			setSubmt(false);
		}
		LOGGER.info("Exit into submitButtonHide method");
	}

	// Edit Record
	public void editRecord(StateMasterDataTable stateMasterDTObj) {
		try {
			setBooReadonly(true);
			setBooReadonlyField(false);
			LOGGER.info("Entering into editRecord method");
			setStateMasterDTObj(stateMasterDTObj);
			setSubmitPanel(true);
			setSubmt(false);
			submitButtonHide();
			setClearPanel(true);
			setCountryId(stateMasterDTObj.getCountryId());
			setCountryName(stateMasterDTObj.getCountryName());
			setStateCode(stateMasterDTObj.getStateCode());
			setStateId(stateMasterDTObj.getStateId());
			setStateMasterPK(stateMasterDTObj.getStateMasterPK());
			setEnglishStateDescPK(stateMasterDTObj.getEnglishStateDescPK());
			setEnglishStateName(stateMasterDTObj.getEnglishStateName());
			setArabicStateDescPK(stateMasterDTObj.getArabicStateDescPK());
			setArabicStateName(stateMasterDTObj.getArabicStateName());
			setRenderEditButton(true);
			setDynamicLabelForActivateDeactivate(stateMasterDTObj.getDynamicLabelForActivateDeactivate());
			setIsActive(stateMasterDTObj.getIsActive());
			setCreatedBy(stateMasterDTObj.getCreatedBy());
			setCreatedDate(stateMasterDTObj.getCreatedDate());
			setApprovedBy(stateMasterDTObj.getApprovedBy());
			setApprovedDate(stateMasterDTObj.getApprovedDate());
			setBooEditButton(true);
			setModifiedBy(stateMasterDTObj.getModifiedBy());
			setModifiedDate(stateMasterDTObj.getModifiedDate());
			setRemarks(stateMasterDTObj.getRemarks());
			stateMasterDTList.remove(stateMasterDTObj);
			stateMasterNewDTList.remove(stateMasterDTObj);
			setRenderEditButton(true);
			setBooSaveExit(true);
			setBooAdd(true);
			setBooRenderStatePanel(true);
			setBooRenderFirstPanel(false);
			if (stateMasterDTList.size() <= 0) {
				setBooRenderDataTable(false);
				setBooSaveExit(false);
			}
			LOGGER.info("Exit into editRecord method");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Exception ocuured " + e);
			return;
		}
	}

	// duplicate checking
	public void checkStateCode() {
		try {
			LOGGER.info("Entering into checkStateCode method");
			stateMasters = stateMasterService.getStateList(getStateCode(), getCountryId());
			if (stateMasters.size() > 0) {
				setStateCode(null);
				RequestContext.getCurrentInstance().execute("datatable.show();");
				return;
			}
			LOGGER.info("Exit into checkStateCode method");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Exception ocuured " + e);
			return;
		}
	}

	public void view() {
		LOGGER.info("Entering into view method");
		stateMasterNewDTList.clear();
		if (getCountryId() == null) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please select country ");
			return;
		}
		List<StateMaster> stateMasterList = stateMasterService.getAllStateMasterList();
		// List<StateMasterDesc> stateDescList =
		// stateMasterService.getAllStateList(getCountryId());
		if (stateMasterList.size() > 0) {
			for (StateMaster stateMaster : stateMasterList) {/*
																 * StateMasterDataTable
																 * stateMasterDataTable
																 * = new
																 * StateMasterDataTable
																 * ();
																 * stateMasterDataTable
																 * .setCountryId
																 * (
																 * stateMasterDesc
																 * .
																 * getFsStateMaster
																 * ().
																 * getFsCountryMaster
																 * ().
																 * getCountryId(
																 * ));
																 * stateMasterDataTable
																 * .
																 * setCountryName
																 * (
																 * countryMasterMap
																 * .get(
																 * stateMasterDesc
																 * .
																 * getFsStateMaster
																 * ().
																 * getFsCountryMaster
																 * ().
																 * getCountryId(
																 * )));
																 * stateMasterDataTable
																 * .setStateCode
																 * (
																 * stateMasterDesc
																 * .
																 * getFsStateMaster
																 * ().
																 * getStateCode(
																 * ));
																 * stateMasterDataTable
																 * .setStateId(
																 * stateMasterDesc
																 * .
																 * getFsStateMaster
																 * ().getStateId
																 * ());
																 * stateMasterDataTable
																 * .setIsActive(
																 * stateMasterDesc
																 * .
																 * getFsStateMaster
																 * ().
																 * getIsActive()
																 * );
																 * stateMasterDataTable
																 * .setCreatedBy
																 * (
																 * stateMasterDesc
																 * .
																 * getFsStateMaster
																 * ().
																 * getCreatedBy(
																 * ));
																 * stateMasterDataTable
																 * .
																 * setCreatedDate
																 * (
																 * stateMasterDesc
																 * .
																 * getFsStateMaster
																 * ().
																 * getCreatedDate
																 * ());
																 * stateMasterDataTable
																 * .
																 * setModifiedBy
																 * (
																 * stateMasterDesc
																 * .
																 * getFsStateMaster
																 * ().
																 * getModifiedBy
																 * ());
																 * stateMasterDataTable
																 * .
																 * setModifiedDate
																 * (
																 * stateMasterDesc
																 * .
																 * getFsStateMaster
																 * ().
																 * getModifiedDate
																 * ());
																 * stateMasterDataTable
																 * .
																 * setApprovedBy
																 * (
																 * stateMasterDesc
																 * .
																 * getFsStateMaster
																 * ().
																 * getApprovedBy
																 * ());
																 * stateMasterDataTable
																 * .
																 * setApprovedDate
																 * (
																 * stateMasterDesc
																 * .
																 * getFsStateMaster
																 * ().
																 * getApprovedDate
																 * ());
																 * stateMasterDataTable
																 * .setRemarks(
																 * stateMasterDesc
																 * .
																 * getFsStateMaster
																 * ().getRemarks
																 * ());
																 * stateMasterDataTable
																 * .
																 * setRenderEditButton
																 * (true);
																 * stateMasterDataTable
																 * .
																 * setBooEditButton
																 * (false); if
																 * (stateMasterDesc
																 * .
																 * getFsStateMaster
																 * ().
																 * getIsActive()
																 * .
																 * equalsIgnoreCase
																 * (Constants.
																 * Yes)) {
																 * stateMasterDataTable
																 * .
																 * setDynamicLabelForActivateDeactivate
																 * (Constants.
																 * DEACTIVATE);
																 * 
																 * } else if
																 * (stateMasterDesc
																 * .
																 * getFsStateMaster
																 * ().
																 * getIsActive()
																 * .
																 * equalsIgnoreCase
																 * (Constants.D)
																 * ) {
																 * stateMasterDataTable
																 * .
																 * setDynamicLabelForActivateDeactivate
																 * (Constants.
																 * ACTIVATE);
																 * 
																 * } else if
																 * (stateMasterDesc
																 * .
																 * getFsStateMaster
																 * ().
																 * getIsActive()
																 * .
																 * equalsIgnoreCase
																 * (Constants.U)
																 * ) { if(
																 * stateMasterDataTable
																 * .
																 * getModifiedBy
																 * () ==null &&
																 * stateMasterDataTable
																 * .
																 * getModifiedDate
																 * () ==null){
																 * stateMasterDataTable
																 * .
																 * setDynamicLabelForActivateDeactivate
																 * (Constants.
																 * DELETE);
																 * }else{
																 * stateMasterDataTable
																 * .
																 * setDynamicLabelForActivateDeactivate
																 * (""); } }
																 * 
																 * if
																 * (stateMasterDesc
																 * .
																 * getFsLanguageType
																 * ().
																 * getLanguageId
																 * ().intValue()
																 * == 1) {
																 * stateMasterDataTable
																 * .
																 * setEnglishStateDescPK
																 * (
																 * stateMasterDesc
																 * .
																 * getStateDescId
																 * ());
																 * stateMasterDataTable
																 * .
																 * setEnglishStateName
																 * (
																 * stateMasterDesc
																 * .getStateName
																 * ()); if
																 * (stateMasterDataTable
																 * .
																 * getEnglishStateName
																 * () != null &&
																 * stateMasterDataTable
																 * .
																 * getArabicStateName
																 * () != null) {
																 * stateMasterNewDTList
																 * .add(
																 * stateMasterDataTable
																 * ); } } List<
																 * StateMasterDesc>
																 * stateDescList2
																 * =
																 * stateMasterService
																 * .
																 * getAllStateList
																 * (getCountryId
																 * ()); for
																 * (StateMasterDesc
																 * stateMasterDesc2
																 * :
																 * stateDescList)
																 * { if
																 * (stateMasterDataTable
																 * .getStateCode
																 * ().equals(
																 * stateMasterDesc2
																 * .
																 * getFsStateMaster
																 * ().
																 * getStateCode(
																 * )) &&
																 * stateMasterDesc2
																 * .
																 * getFsLanguageType
																 * ().
																 * getLanguageId
																 * ().intValue()
																 * == 2) {
																 * stateMasterDataTable
																 * .
																 * setArabicStateDescPK
																 * (
																 * stateMasterDesc2
																 * .
																 * getStateDescId
																 * ());
																 * stateMasterDataTable
																 * .
																 * setArabicStateName
																 * (
																 * stateMasterDesc2
																 * .getStateName
																 * ()); if
																 * (stateMasterDataTable
																 * .
																 * getEnglishStateName
																 * () != null &&
																 * stateMasterDataTable
																 * .
																 * getArabicStateName
																 * () != null) {
																 * stateMasterNewDTList
																 * .add(
																 * stateMasterDataTable
																 * ); } } }
																 * 
																 */
				StateMasterDataTable stateMasterDataTable = new StateMasterDataTable();
				stateMasterDataTable.setCountryId(stateMaster.getFsCountryMaster().getCountryId());
				stateMasterDataTable.setCountryName(countryMasterMap.get(stateMaster.getFsCountryMaster().getCountryId()));
				stateMasterDataTable.setStateCode(stateMaster.getStateCode());
				stateMasterDataTable.setStateId(stateMaster.getStateId());
				stateMasterDataTable.setIsActive(stateMaster.getIsActive());
				stateMasterDataTable.setCreatedBy(stateMaster.getCreatedBy());
				stateMasterDataTable.setCreatedDate(stateMaster.getCreatedDate());
				stateMasterDataTable.setModifiedBy(stateMaster.getModifiedBy());
				stateMasterDataTable.setModifiedDate(stateMaster.getModifiedDate());
				stateMasterDataTable.setApprovedBy(stateMaster.getApprovedBy());
				stateMasterDataTable.setApprovedDate(stateMaster.getApprovedDate());
				stateMasterDataTable.setRemarks(stateMaster.getRemarks());
				stateMasterDataTable.setRenderEditButton(true);
				stateMasterDataTable.setBooEditButton(false);
				if (stateMaster.getIsActive().equalsIgnoreCase(Constants.Yes)) {
					stateMasterDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				} else if (stateMaster.getIsActive().equalsIgnoreCase(Constants.D)) {
					stateMasterDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				} else if (stateMaster.getIsActive().equalsIgnoreCase(Constants.U)) {
					if (stateMasterDataTable.getModifiedBy() == null && stateMasterDataTable.getModifiedDate() == null) {
						stateMasterDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					} else {
						stateMasterDataTable.setDynamicLabelForActivateDeactivate("");
					}
				}
				List<StateMasterDesc> stateDesList = stateMasterService.getstateDescList(stateMaster.getStateId());
				for (StateMasterDesc desc : stateDesList) {
					if (desc.getFsLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID)) {
						stateMasterDataTable.setEnglishStateDescPK(desc.getStateDescId());
						stateMasterDataTable.setEnglishStateName(desc.getStateName());
					}
					if (desc.getFsLanguageType().getLanguageId().toString().equals(Constants.ARABIC_LANGUAGE_ID)) {
						stateMasterDataTable.setArabicStateDescPK(desc.getStateDescId());
						stateMasterDataTable.setArabicStateName(desc.getStateName());
					}
				}
				stateMasterNewDTList.add(stateMasterDataTable);
			}
		} else {
			RequestContext.getCurrentInstance().execute("norec.show();");
		}
		if (stateMasterNewDTList.size() != 0) {
			addRecordsToDataTable();
			stateMasterNewDTList.clear();
		} else {
			stateMasterDTList.clear();
			clearAllFields();
			RequestContext.getCurrentInstance().execute("norec.show();");
			return;
		}
		LOGGER.info("Entering into Exit method");
	}

	public void checkStatus(StateMasterDataTable stateMasterDTObj) {
		try {
			LOGGER.info("Entering into checkStatus method");
			if (stateMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				stateMasterDTObj.setRemarksChecks(true);
				setApprovedBy(stateMasterDTObj.getApprovedBy());
				setApprovedDate(stateMasterDTObj.getApprovedDate());
				RequestContext.getCurrentInstance().execute("remarks.show();");
			} else if (stateMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && stateMasterDTObj.getModifiedBy() == null && stateMasterDTObj.getModifiedDate() == null && stateMasterDTObj.getRemarks() == null && stateMasterDTObj.getApprovedBy() == null
					&& stateMasterDTObj.getApprovedDate() == null) {
				stateMasterDTObj.setPermanetDeleteCheck(true);
				RequestContext.getCurrentInstance().execute("permanentDelete.show();");
				return;
			} else if (stateMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
				RequestContext.getCurrentInstance().execute("pending.show();");
				return;
			} else {
				removeRecord(stateMasterDTObj);
			}
			LOGGER.info("Exit into checkStatus method");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Exception occurred "+e);
			return;
		}
	}

	public void confirmPermanentDelete() {
		try {
			LOGGER.info("Entering into confirmPermanentDelete method");
			if (stateMasterDTList.size() > 0) {
				for (StateMasterDataTable stateMasterDataTable : stateMasterDTList) {
					if (stateMasterDataTable.getActivateRecordCheck() != null) {
						if (stateMasterDataTable.getPermanetDeleteCheck().equals(true)) {
							stateMasterHdelete(stateMasterDataTable);
							stateMasterDTList.remove(stateMasterDataTable);
						}
					}
				}
			}
			LOGGER.info("Exit into confirmPermanentDelete method");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Exception occurred "+e);
			return;
		}
	}

	public void clickOnOKRemarks() {
		try {
			if (getRemarks() != null && !getRemarks().equals("")) {
				LOGGER.info("Entering into clickOnOKRemarks method");
				for (StateMasterDataTable stateMasterDTObj : stateMasterDTList) {
					if (stateMasterDTObj.getRemarksChecks() != null) {
						if (stateMasterDTObj.getRemarksChecks().equals(true)) {
							stateMasterDTObj.setRemarks(getRemarks());
							removeRecord(stateMasterDTObj);
							setRemarks(null);
						}
					}
				}
			} else {
				RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
				return;
			}
			LOGGER.info("Exit into clickOnOKRemarks method");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Exception occurred "+e);
			return;
		}
	}

	public void removeRecord(StateMasterDataTable stateMasterDTObj) {
		LOGGER.info("Entering into removeRecord method");
		if (stateMasterDTObj.getStateId() == null) {
			stateMasterDTList.remove(stateMasterDTObj);
			stateMasterNewDTList.remove(stateMasterDTObj);
			if (stateMasterDTList.size() <= 0) {
				setBooRenderDataTable(false);
				setBooSaveExit(false);
				setBooApprovalDataTable(false);
			}
		} else {
			delete(stateMasterDTObj);
			// stateMasterDTList.clear();
			// view();
			viewAllDetails();
			// stateMasterDTList.addAll(stateMasterNewDTList);
		}
		LOGGER.info("Exit into removeRecord method");
	}

	public void stateMasterHdelete(StateMasterDataTable stateMasterDTObj) {
		try {
			LOGGER.info("Entering into stateMasterHdelete method");
			// Eng
			StateMasterDesc stateMasterDes1 = new StateMasterDesc();
			stateMasterDes1.setStateDescId(stateMasterDTObj.getEnglishStateDescPK());
			LanguageType languageType = new LanguageType();
			languageType.setLanguageId(new BigDecimal(1));
			stateMasterDes1.setFsLanguageType(languageType);
			stateMasterDes1.setStateName(stateMasterDTObj.getEnglishStateName());
			stateMasterService.deleteRecordDesc(stateMasterDes1);
			// Ara
			StateMasterDesc stateMasterDes2 = new StateMasterDesc();
			stateMasterDes2.setStateDescId(stateMasterDTObj.getArabicStateDescPK());
			LanguageType languageType2 = new LanguageType();
			languageType2.setLanguageId(new BigDecimal(2));
			stateMasterDes2.setFsLanguageType(languageType2);
			stateMasterDes2.setStateName(stateMasterDTObj.getArabicStateName());
			stateMasterService.deleteRecordDesc(stateMasterDes2);
			stateMaster.setStateCode(stateMasterDTObj.getStateCode());
			stateMaster.setStateId(stateMasterDTObj.getStateId());
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(stateMasterDTObj.getCountryId());
			stateMaster.setCreatedBy(stateMasterDTObj.getCreatedBy());
			stateMaster.setCreatedDate(stateMasterDTObj.getCreatedDate());
			stateMaster.setIsActive(stateMasterDTObj.getIsActive());
			stateMasterService.delete(stateMaster);
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Exception occurred "+e);
			return;
		}
		LOGGER.info("Exit into stateMasterHdelete method");
	}

	public void delete(StateMasterDataTable stateMasterDTObj) {
		try {
			LOGGER.info("Entering into delete method");
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(stateMasterDTObj.getCountryId());
			stateMaster.setFsCountryMaster(countryMaster);
			stateMaster.setStateCode(stateMasterDTObj.getStateCode());
			stateMaster.setStateId(stateMasterDTObj.getStateId());
			stateMaster.setIsActive(stateMasterDTObj.getIsActive());
			stateMaster.setCreatedBy(stateMasterDTObj.getCreatedBy());
			stateMaster.setCreatedDate(stateMasterDTObj.getCreatedDate());
			if (stateMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				stateMaster.setIsActive("D");
				stateMaster.setModifiedBy(session.getUserName());
				stateMaster.setModifiedDate(new Date());
				stateMaster.setRemarks(stateMasterDTObj.getRemarks());
			} else if (stateMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				stateMaster.setIsActive(Constants.U);
				stateMaster.setModifiedBy(session.getUserName());
				stateMaster.setModifiedDate(new Date());
				stateMaster.setRemarks(null);
			}
			stateMasterService.save(stateMaster);
			StateMasterDesc stateMasterDes1 = new StateMasterDesc();
			stateMasterDes1.setStateDescId(stateMasterDTObj.getEnglishStateDescPK());
			stateMasterDes1.setFsStateMaster(stateMaster);
			LanguageType languageType = new LanguageType();
			languageType.setLanguageId(new BigDecimal(1));
			stateMasterDes1.setFsLanguageType(languageType);
			stateMasterDes1.setStateName(stateMasterDTObj.getEnglishStateName());
			stateMasterService.saverRecordDesc(stateMasterDes1);
			// Ara
			StateMasterDesc stateMasterDes2 = new StateMasterDesc();
			stateMasterDes2.setStateDescId(stateMasterDTObj.getArabicStateDescPK());
			stateMasterDes2.setFsStateMaster(stateMaster);
			LanguageType languageType2 = new LanguageType();
			languageType2.setLanguageId(new BigDecimal(2));
			stateMasterDes2.setFsLanguageType(languageType2);
			stateMasterDes2.setStateName(stateMasterDTObj.getArabicStateName());
			stateMasterService.saverRecordDesc(stateMasterDes2);
			LOGGER.info("Exit into delete method");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Exception occurred "+e);
			return;
		}
	}

	// approvel started
	public void pageNavigtionApprovel() {
		LOGGER.info("Entering into pageNavigtionApprovel method");
		stateMasterDTList.clear();
		clearAllFields();
		setBooRenderFirstPanel(false);
		setBooRenderStatePanel(false);
		setBooRenderFileUploadPanel(false);
		setBooSaveExit(false);
		setBooAdd(false);
		setClearPanel(false);
		setBoohideSecod(false);
		setBooReadonly(false);
		setBooReadonlyField( false);
		setBooRenderDataTable(false);
		setSelectType(0);
		clearAllFields();
		setBooApprovalDataTable(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "StateMasterApprovel.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/StateMasterApprovel.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into pageNavigtionApprovel method");
	}

	public void fetchRecordsToDb() {
		try {
			LOGGER.info("Entering into fetchRecordsToDb method");
			stateMasterDTList.clear();
			Boolean chiledRecordCheck = false;
			List<StateMaster> lstStateMasters = stateMasterService.toFetchForApprovalView(getCountryId());
			if (lstStateMasters.size() > 0) {
				for (StateMaster stateMaster : lstStateMasters) {
					StateMasterDataTable dataTable = new StateMasterDataTable();
					dataTable.setCountryId(stateMaster.getFsCountryMaster().getCountryId());
					dataTable.setStateCode(stateMaster.getStateCode());
					dataTable.setStateId(stateMaster.getStateId());
					dataTable.setCountryName(stateMasterService.getAllCountryMasterList(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"), stateMaster.getFsCountryMaster().getCountryId()).get(0).getCountryName());
					// dataTable.setCountryName(countryMasterMap.get(stateMaster.getFsCountryMaster().getCountryId()));
					dataTable.setCreatedBy(stateMaster.getCreatedBy());
					dataTable.setCreatedDate(stateMaster.getCreatedDate());
					dataTable.setModifiedBy(stateMaster.getModifiedBy());
					dataTable.setModifiedDate(stateMaster.getModifiedDate());
					dataTable.setApprovedBy(stateMaster.getApprovedBy());
					dataTable.setApprovedDate(stateMaster.getApprovedDate());
					dataTable.setRemarks(stateMaster.getRemarks());
					dataTable.setIsActive(stateMaster.getIsActive());
					dataTable.setRenderEditButton(true);
					dataTable.setBooEditButton(false);
					if (stateMaster.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						dataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					} else if (stateMaster.getIsActive().equalsIgnoreCase(Constants.D)) {
						dataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					} else if (stateMaster.getIsActive().equalsIgnoreCase(Constants.U)) {
						if (dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null) {
							dataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
						} else {
							dataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						}
					}
					List<StateMasterDesc> lsStateMasterDescs = stateMasterService.getstateDescList(stateMaster.getStateId());
					if (lsStateMasterDescs.size() > 0) {
						for (StateMasterDesc stateMasterDesc : lsStateMasterDescs) {
							if (stateMasterDesc.getFsLanguageType().getLanguageId().intValue() == 1) {
								dataTable.setEnglishStateDescPK(stateMasterDesc.getStateDescId());
								dataTable.setEnglishStateName(stateMasterDesc.getStateName());
							} else {
								chiledRecordCheck = true;
								dataTable.setArabicStateDescPK(stateMasterDesc.getStateDescId());
								dataTable.setArabicStateName(stateMasterDesc.getStateName());
							}
						}
					}
					if (chiledRecordCheck == true) {
						stateMasterDTList.add(dataTable);
						setBooApprovalDataTable(true);
						setBooRenderFirstPanel(false);
						setBooRenderStatePanel(false);
						setBooRenderFileUploadPanel(false);
						setBooSaveExit(false);
						setBooAdd(false);
						setClearPanel(false);
						setBoohideSecod(false);
						setBooReadonly(false);
						setBooReadonlyField(false);
						setBooRenderDataTable(false);
						setSelectType(0);
						clear();
					}
				}
			} else {
				RequestContext.getCurrentInstance().execute("noRecord.show();");
				setBooApprovalDataTable(false);
				return;
			}
			LOGGER.info("Exit into fetchRecordsToDb method");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Exception occurred "+e);
			return;
		}
	}

	// user checking
	public void approvelCheck(StateMasterDataTable stateMasterDTObj) {
		LOGGER.info("Entering into approvelCheck method");
		/*
		 * if (!stateMasterDTObj.getCreatedBy().equalsIgnoreCase(session.
		 * getUserName())) {
		 */
		if (!(stateMasterDTObj.getModifiedBy() == null ? stateMasterDTObj.getCreatedBy() : stateMasterDTObj.getModifiedBy()).equalsIgnoreCase(session.getUserName())) {
			setCountryId(stateMasterDTObj.getCountryId());
			setStateCode(stateMasterDTObj.getStateCode());
			setStateId(stateMasterDTObj.getStateId());
			setEnglishStateName(stateMasterDTObj.getEnglishStateName());
			setEnglishStateDescPK(stateMasterDTObj.getEnglishStateDescPK());
			setArabicStateName(stateMasterDTObj.getArabicStateName());
			setArabicStateDescPK(stateMasterDTObj.getArabicStateDescPK());
			setBooRenderFirstPanel(false);
			setBooRenderFileUploadPanel(false);
			setBooAdd(false);
			setBooSaveExit(false);
			setBooRenderDataTable(false);
			setBooEditButton(false);
			setBooRenderStatePanel(true);
			setBoohideSecod(true);
			setBooReadonly(true);
			setBooReadonlyField( true);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/StateMaster.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			RequestContext.getCurrentInstance().execute("notApproved.show();");
		}
		LOGGER.info("Exit into approvelCheck method");
	}

	public void approval() {
		LOGGER.info("Entering into approval method");
		/*
		 * stateMasterService.approvedRecord(getStateId(),
		 * session.getUserName());
		 * RequestContext.getCurrentInstance().execute("approve.show();");
		 */
		String approveMsg = stateMasterService.approvRecord(getStateId(), session.getUserName());
		if (approveMsg.equalsIgnoreCase("Success")) {
			RequestContext.getCurrentInstance().execute("approve.show();");
		} else {
			RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
		}
		LOGGER.info("Exit into approval method");
	}

	public void clickOnOKApprove() {
		LOGGER.info("Entering into clickOnOKApprove method");
		try {
			clear();
			setBooRenderDataTable(false);
			setBooAdd(false);
			setBooSaveExit(false);
			try {
				fetchRecordsToDb();
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/StateMasterApprovel.xhtml");
			} catch (Exception e1) {
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Exception occurred "+e);
			return;
		}
		LOGGER.info("Exit into clickOnOKApprove method");
	}

	public void cancel() {
		LOGGER.info("Entering into cancel method");
		try {
			clear();
			setBooRenderDataTable(false);
			setBooAdd(false);
			setBooSaveExit(false);
			try {
				fetchRecordsToDb();
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/StateMasterApprovel.xhtml");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
		}
		LOGGER.info("Exit into cancel method");
	}

	public void cancelRemarks() {
		LOGGER.info("Entering into cancelRemarks method");
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/StateMaster.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into cancelRemarks method");
	}

	public void clickOnOkButton() {
		LOGGER.info("Entering into clickOnOkButton method");
		try {
			clearAllFields();
			setBooRenderDataTable(false);
			setBooAdd(false);
			setBooSaveExit(false);
			try {
				fetchRecordsToDb();
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/StateMasterApprovel.xhtml");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
		}
		LOGGER.info("Exit into clickOnOkButton method");
	}

	public void viewAllDetails() {
		try {
			stateMasterDTList.clear();
			Boolean chiledRecordCheck = false;
			if (getCountryId() != null) {
				List<StateMaster> lstStateMasters = stateMasterService.toFetchDetailsForView(getCountryId());
				if (lstStateMasters.size() > 0) {
					setBooRenderDataTable(true);
					setBooSaveExit(true);
					setBoohideSecod(false);
					setBooReadonly(false);
					setBooReadonlyField( false);
					setBooEditButton(false);
					for (StateMaster stateMaster : lstStateMasters) {
						StateMasterDataTable dataTable = new StateMasterDataTable();
						dataTable.setCountryId(stateMaster.getFsCountryMaster().getCountryId());
						dataTable.setStateCode(stateMaster.getStateCode());
						dataTable.setStateId(stateMaster.getStateId());
						dataTable.setCountryName(countryMasterMap.get(stateMaster.getFsCountryMaster().getCountryId()));
						dataTable.setCreatedBy(stateMaster.getCreatedBy());
						dataTable.setCreatedDate(stateMaster.getCreatedDate());
						dataTable.setModifiedBy(stateMaster.getModifiedBy());
						dataTable.setModifiedDate(stateMaster.getModifiedDate());
						dataTable.setApprovedBy(stateMaster.getApprovedBy());
						dataTable.setApprovedDate(stateMaster.getApprovedDate());
						dataTable.setRemarks(stateMaster.getRemarks());
						dataTable.setIsActive(stateMaster.getIsActive());
						dataTable.setRenderEditButton(true);
						dataTable.setBooEditButton(false);
						if (stateMaster.getIsActive().equalsIgnoreCase(Constants.Yes)) {
							dataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
						} else if (stateMaster.getIsActive().equalsIgnoreCase(Constants.D)) {
							dataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
						} else if (stateMaster.getIsActive().equalsIgnoreCase(Constants.U)) {
							if (dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null) {
								dataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
							} else {
								dataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
							}
						}
						List<StateMasterDesc> lsStateMasterDescs = stateMasterService.getstateDescList(stateMaster.getStateId());
						if (lsStateMasterDescs.size() > 0) {
							for (StateMasterDesc stateMasterDesc : lsStateMasterDescs) {
								if (stateMasterDesc.getFsLanguageType().getLanguageId().intValue() == 1) {
									dataTable.setEnglishStateDescPK(stateMasterDesc.getStateDescId());
									dataTable.setEnglishStateName(stateMasterDesc.getStateName());
								} else {
									chiledRecordCheck = true;
									dataTable.setArabicStateDescPK(stateMasterDesc.getStateDescId());
									dataTable.setArabicStateName(stateMasterDesc.getStateName());
								}
							}
						}
						/* if (chiledRecordCheck == true) { */
						stateMasterDTList.add(dataTable);
						clear();
						/* } */
						/*
						 * stateMasterDTList.addAll(stateMasterNewDTList); clear();
						 */
					}
					// stateMasterDTList.addAll(stateMasterNewDTList);
				} else {
					setBooRenderDataTable(false);
					setBooSaveExit(false);
					setBoohideSecod(false);
					setBooReadonly(false);
					setBooReadonlyField( false);
					setBooEditButton( false);
					RequestContext.getCurrentInstance().execute("norec.show();");
					return;
				}
				stateMasterDTList.addAll(stateMasterNewDTList);
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/StateMaster.xhtml");
				} catch (Exception exception) {
					LOGGER.info("exception.getMessage()::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + exception.getMessage());
				}
			} else {
				RequestContext.getCurrentInstance().execute("country.show();");
				return;
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Exception occurred "+e);
			return;
		}
	}

	private Boolean booApprovalDataTable = false;

	public Boolean getBooApprovalDataTable() {
		return booApprovalDataTable;
	}

	public void setBooApprovalDataTable(Boolean booApprovalDataTable) {
		this.booApprovalDataTable = booApprovalDataTable;
	}
}
