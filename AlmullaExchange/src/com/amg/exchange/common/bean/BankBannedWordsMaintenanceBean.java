package com.amg.exchange.common.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.BankBannedWordsMaintenance;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IBankBannedWordsMaintenanceService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("bankBannedWordsMaintenanceBean")
@Scope("session")
public class BankBannedWordsMaintenanceBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(BankBannedWordsMaintenanceBean.class);
	private SessionStateManage session = new SessionStateManage();
	private BigDecimal bankBannedPk;
	private String bankCode;
	private BigDecimal bankId;
	private String bankBannedName;
	private BigDecimal countryId;
	private String selectionType;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private Boolean renderAcceptButtonPanel = false;
	private Boolean renderAddPanel = false;
	private Boolean renderDropDowns = false;
	private Boolean renderDatatable = false;
	private Boolean renderAddMoreBannedWord = false;
	private Boolean disableClearButton;
	private Boolean disableAcceptButton;
	private String errorMessage;
	private List<CountryMasterDesc> allCountryList = new ArrayList<CountryMasterDesc>();
	private List<BankMaster> bankList = new ArrayList<BankMaster>();
	private List<BankBannedWordsMaintananceDataTableBean> bankBannedDataTableList = new ArrayList<BankBannedWordsMaintananceDataTableBean>();
	private Map<BigDecimal, String> bankBannedCountryMap = new HashMap<BigDecimal, String>();
	// Auto Wiring Section started
	@Autowired
	IGeneralService<T> igeneralService;
	@Autowired
	IBankBannedWordsMaintenanceService iBankBannedWordsService;
	// Auto Wiring Section ended

	public List<BankBannedWordsMaintananceDataTableBean> getBankBannedDataTableList() {
		return bankBannedDataTableList;
	}

	public void setBankBannedDataTableList(List<BankBannedWordsMaintananceDataTableBean> bankBannedDataTableList) {
		this.bankBannedDataTableList = bankBannedDataTableList;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getBankBannedPk() {
		return bankBannedPk;
	}

	public void setBankBannedPk(BigDecimal bankBannedPk) {
		this.bankBannedPk = bankBannedPk;
	}

	public String getBankBannedName() {
		return bankBannedName;
	}

	public void setBankBannedName(String bankBannedName) {
		this.bankBannedName = bankBannedName;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setAllCountryList(List<CountryMasterDesc> allCountryList) {
		this.allCountryList = allCountryList;
	}

	public Map<BigDecimal, String> getBankBannedCountryMap() {
		return bankBannedCountryMap;
	}

	public void setBankBannedCountryMap(Map<BigDecimal, String> bankBannedCountryMap) {
		this.bankBannedCountryMap = bankBannedCountryMap;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public List<BankMaster> getBankList() {
		return bankList;
	}

	public void setBankList(List<BankMaster> bankList) {
		this.bankList = bankList;
	}

	public Boolean getRenderAcceptButtonPanel() {
		return renderAcceptButtonPanel;
	}

	public void setRenderAcceptButtonPanel(Boolean renderAcceptButtonPanel) {
		this.renderAcceptButtonPanel = renderAcceptButtonPanel;
	}

	public Boolean getRenderAddPanel() {
		return renderAddPanel;
	}

	public void setRenderAddPanel(Boolean renderAddPanel) {
		this.renderAddPanel = renderAddPanel;
	}

	public Boolean getRenderDropDowns() {
		return renderDropDowns;
	}

	public void setRenderDropDowns(Boolean renderDropDowns) {
		this.renderDropDowns = renderDropDowns;
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

	public String getSelectionType() {
		return selectionType;
	}

	public void setSelectionType(String selectionType) {
		this.selectionType = selectionType;
	}

	public Boolean getRenderDatatable() {
		return renderDatatable;
	}

	public void setRenderDatatable(Boolean renderDatatable) {
		this.renderDatatable = renderDatatable;
	}

	public Boolean getRenderAddMoreBannedWord() {
		return renderAddMoreBannedWord;
	}

	public void setRenderAddMoreBannedWord(Boolean renderAddMoreBannedWord) {
		this.renderAddMoreBannedWord = renderAddMoreBannedWord;
	}

	public Boolean getDisableClearButton() {
		return disableClearButton;
	}

	public void setDisableClearButton(Boolean disableClearButton) {
		this.disableClearButton = disableClearButton;
	}

	public Boolean getDisableAcceptButton() {
		return disableAcceptButton;
	}

	public void setDisableAcceptButton(Boolean disableAcceptButton) {
		this.disableAcceptButton = disableAcceptButton;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigationToBankBannedWordsMaintenance() {
		setRenderAddPanel(false);
		setRenderDropDowns(false);
		setRenderDatatable(false);
		setRenderAcceptButtonPanel(false);
		setRenderAddMoreBannedWord(false);
		setSelectionType(null);
		clearAll();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "bankbannedwordsmaintanance.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/bankbannedwordsmaintanance.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to   BankBannedWordsMaintenance");
		}
	}

	public void exitButton() {
		try {
			if (session.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		} catch (Exception e) {
			log.error(":::::::::Problem Ocuured in Exit Button::::::");
		}
	}

	public void clearAll() {
		log.info("clear button called ==================");
		setBankBannedName(null);
		setBankBannedPk(null);
		setBankCode(null);
		setBankId(null);
		setCountryId(null);
		if (getSelectionType() != null) {
			if (getSelectionType().equalsIgnoreCase(Constants.A)) {
				setRenderDatatable(false);
				setRenderAcceptButtonPanel(false);
				setSelectionType(null);
				setRenderAddMoreBannedWord(false);
			} else {
				setBankId(null);
				setCountryId(null);
			}
		}
		bankBannedDataTableList.clear();
	}

	public void populateCountryBanks() {
		log.info("banks list==============" + getCountryId());
		setBankList(igeneralService.getBankList(getCountryId()));
	}

	public void addEmptyRow() {
		try {
			boolean checkEmpty = false;
			for (BankBannedWordsMaintananceDataTableBean element : bankBannedDataTableList) {
				if (element.getBankBannedName().equals("")) {
					checkEmpty = true;
				}
			}
			if (checkEmpty) {
				RequestContext.getCurrentInstance().execute("dlgNegNotAllowed.show();");
			} else {
				BankBannedWordsMaintananceDataTableBean bankBannedDTDetails = new BankBannedWordsMaintananceDataTableBean();
				bankBannedDTDetails.setBankBannedName("");
				bankBannedDTDetails.setSelectionMode(getSelectionType());
				bankBannedDTDetails.setBankId(getBankId());
				bankBannedDTDetails.setDynamicLabelActivateDeactivate(Constants.DELETE);
				if (getBankId() != null) {
					bankBannedDTDetails.setBankCode(igeneralService.getBankCode(getBankId()));
				}
				bankBannedDTDetails.setCountryId(getCountryId());
				if (getCountryId() != null) {
					bankBannedDTDetails.setCountryCode(bankBannedCountryMap.get(getCountryId()));
				}
				bankBannedDataTableList.add(bankBannedDTDetails);
				setRenderAcceptButtonPanel(true);
			}
		} catch (Exception e) {
			
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			return;
		}
	}

	public void populateSpecificRecords() {
		try {
			bankBannedDataTableList.clear();
			List<BankBannedWordsMaintenance> specificBannedList = iBankBannedWordsService.getSpecificReords(getBankId(), getCountryId(), getSelectionType());
			if (specificBannedList.size() > 0) {
				setRenderAcceptButtonPanel(true);
				setRenderDatatable(true);
				setDisableClearButton(false);
				for (BankBannedWordsMaintenance bankBannedObj : specificBannedList) {
					BankBannedWordsMaintananceDataTableBean bankBannedDTObj = new BankBannedWordsMaintananceDataTableBean();
					bankBannedDTObj.setBankBannedPk(bankBannedObj.getBannedId());
					bankBannedDTObj.setCreatedBy(bankBannedObj.getCreatedBy());
					bankBannedDTObj.setCreatedDate(bankBannedObj.getCreatedDate());
					bankBannedDTObj.setModifiedBy(bankBannedObj.getModifiedBy());
					bankBannedDTObj.setModifiedDate(bankBannedObj.getModifiedDate());
					bankBannedDTObj.setBankBannedName(bankBannedObj.getBannedName());
					bankBannedDTObj.setCountryCode(bankBannedObj.getCountryCode());
					bankBannedDTObj.setCountryId(bankBannedObj.getCountryId().getCountryId());
					bankBannedDTObj.setBankCode(bankBannedObj.getBankCode());
					bankBannedDTObj.setBankId(bankBannedObj.getBankId().getBankId());
					bankBannedDTObj.setSelectionMode(bankBannedObj.getSelectionMode());
					bankBannedDTObj.setIsActive(bankBannedObj.getIsActive());
					if (bankBannedObj.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						bankBannedDTObj.setDynamicLabelActivateDeactivate(Constants.DEACTIVATE);
					} else if (bankBannedObj.getIsActive().equalsIgnoreCase(Constants.U)) {
						bankBannedDTObj.setDynamicLabelActivateDeactivate(Constants.ACTIVATE);
					}
					bankBannedDataTableList.add(bankBannedDTObj);
				}
			} else {
				setRenderAcceptButtonPanel(false);
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			return;
		}
	}

	public void showBannnedWordsInDatatable() {
		log.info("==================showBannnedWordsInDatatable called==================== ");
		try {
			bankBannedDataTableList.clear();
			if (getSelectionType() != null) {
				if (getSelectionType().equalsIgnoreCase(Constants.A)) {
					log.info("==============all radio button selected ==========");
					setCountryId(null);
					setBankId(null);
					setRenderDropDowns(false);
					setRenderAddPanel(false);
					setRenderDatatable(true);
					setRenderAddMoreBannedWord(true);
					setRenderAcceptButtonPanel(true);
					setDisableClearButton(false);
					List<BankBannedWordsMaintenance> allBannedList = iBankBannedWordsService.getAllBannedWordsList(getSelectionType());
					if (allBannedList.size() > 0) {
						setRenderAcceptButtonPanel(true);
						for (BankBannedWordsMaintenance bankBannedWordsObj : allBannedList) {
							BankBannedWordsMaintananceDataTableBean bankBannedDTallObj = new BankBannedWordsMaintananceDataTableBean();
							bankBannedDTallObj.setBankBannedName(bankBannedWordsObj.getBannedName());
							bankBannedDTallObj.setBankBannedPk(bankBannedWordsObj.getBannedId());
							bankBannedDTallObj.setCreatedBy(bankBannedWordsObj.getCreatedBy());
							bankBannedDTallObj.setCreatedDate(bankBannedWordsObj.getCreatedDate());
							bankBannedDTallObj.setModifiedBy(bankBannedWordsObj.getModifiedBy());
							bankBannedDTallObj.setModifiedDate(bankBannedWordsObj.getModifiedDate());
							bankBannedDTallObj.setBankCode(bankBannedWordsObj.getBankCode());
							bankBannedDTallObj.setSelectionMode(bankBannedWordsObj.getSelectionMode());
							bankBannedDTallObj.setIsActive(bankBannedWordsObj.getIsActive());
							if (bankBannedWordsObj.getIsActive().equalsIgnoreCase(Constants.Yes)) {
								bankBannedDTallObj.setDynamicLabelActivateDeactivate(Constants.DEACTIVATE);
							} else if (bankBannedWordsObj.getIsActive().equalsIgnoreCase(Constants.U)) {
								bankBannedDTallObj.setDynamicLabelActivateDeactivate(Constants.ACTIVATE);
							}
							if (bankBannedWordsObj.getBankId() != null && bankBannedWordsObj.getCountryId() != null) {
								bankBannedDTallObj.setBankId(bankBannedWordsObj.getBankId().getBankId());
								bankBannedDTallObj.setCountryId(bankBannedWordsObj.getCountryId().getCountryId());
							}
							bankBannedDTallObj.setCountryCode(bankBannedWordsObj.getCountryCode());
							bankBannedDataTableList.add(bankBannedDTallObj);
						}
					} else {
						setRenderAcceptButtonPanel(false);
					}
				} else if (getSelectionType().equalsIgnoreCase(Constants.S)) {
					log.info("==============Specific radio button selected ==========");
					// setBankId(null);
					// setCountryId( null);
					bankBannedDataTableList.clear();
					setRenderDropDowns(true);
					setRenderAddPanel(true);
					setRenderDatatable(true);
					setRenderAcceptButtonPanel(true);
					setRenderAddMoreBannedWord(true);
					setDisableClearButton(false);
					if (null != getCountryId() && null != getBankId()) {
						List<BankBannedWordsMaintenance> specificBannedList = iBankBannedWordsService.getSpecificReords(getBankId(), getCountryId(), getSelectionType());
						for (BankBannedWordsMaintenance bankBannedObj : specificBannedList) {
							BankBannedWordsMaintananceDataTableBean bankBannedDTObj = new BankBannedWordsMaintananceDataTableBean();
							bankBannedDTObj.setBankBannedPk(bankBannedObj.getBannedId());
							bankBannedDTObj.setCreatedBy(bankBannedObj.getCreatedBy());
							bankBannedDTObj.setCreatedDate(bankBannedObj.getCreatedDate());
							bankBannedDTObj.setModifiedBy(bankBannedObj.getModifiedBy());
							bankBannedDTObj.setModifiedDate(bankBannedObj.getModifiedDate());
							bankBannedDTObj.setBankBannedName(bankBannedObj.getBannedName());
							bankBannedDTObj.setCountryCode(bankBannedObj.getCountryCode());
							bankBannedDTObj.setCountryId(bankBannedObj.getCountryId().getCountryId());
							bankBannedDTObj.setBankCode(bankBannedObj.getBankCode());
							bankBannedDTObj.setBankId(bankBannedObj.getBankId().getBankId());
							bankBannedDTObj.setSelectionMode(bankBannedObj.getSelectionMode());
							bankBannedDTObj.setIsActive(bankBannedObj.getIsActive());
							if (bankBannedObj.getIsActive().equalsIgnoreCase(Constants.Yes)) {
								bankBannedDTObj.setDynamicLabelActivateDeactivate(Constants.DEACTIVATE);
							} else if (bankBannedObj.getIsActive().equalsIgnoreCase(Constants.U)) {
								bankBannedDTObj.setDynamicLabelActivateDeactivate(Constants.ACTIVATE);
							}
							bankBannedDataTableList.add(bankBannedDTObj);
						}
					}
				} else {
				}
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			return;
		}
	}

	// when we click accept button this save method called
	public void save() {
		log.info("save() method called====================================");
		try {
			if (bankBannedDataTableList.size() > 0) {
				int emptyCount = 0;
				for (BankBannedWordsMaintananceDataTableBean bankBannedDTObject : bankBannedDataTableList) {
					if (bankBannedDTObject.getBankBannedName().isEmpty()) {
						emptyCount = emptyCount + 1;
					}
				}
				if (emptyCount <= 0) {
					for (BankBannedWordsMaintananceDataTableBean bankBannedDTObj : bankBannedDataTableList) {
						BankBannedWordsMaintenance bankBannedObj = new BankBannedWordsMaintenance();
						bankBannedObj.setBannedId(bankBannedDTObj.getBankBannedPk());
						bankBannedObj.setBannedName(bankBannedDTObj.getBankBannedName());
						if (null != bankBannedDTObj.getBankBannedPk()) {
							bankBannedObj.setCreatedBy(bankBannedDTObj.getCreatedBy());
							bankBannedObj.setCreatedDate(bankBannedDTObj.getCreatedDate());
						} else {
							bankBannedObj.setCreatedBy(session.getUserName());
							bankBannedObj.setCreatedDate(new Date());
						}
						bankBannedObj.setSelectionMode(bankBannedDTObj.getSelectionMode());
						if (null != bankBannedDTObj.getCountryId()) {
							CountryMaster countryMasterObj = new CountryMaster();
							countryMasterObj.setCountryId(bankBannedDTObj.getCountryId());
							bankBannedObj.setCountryId(countryMasterObj);
							bankBannedObj.setCountryCode(bankBannedDTObj.getCountryCode());
						}
						if (null != bankBannedDTObj.getBankId()) {
							BankMaster bankMasterObj = new BankMaster();
							bankMasterObj.setBankId(bankBannedDTObj.getBankId());
							bankBannedObj.setBankId(bankMasterObj);
							bankBannedObj.setBankCode(bankBannedDTObj.getBankCode());
						}
						if (bankBannedDTObj.getBankBannedPk() != null) {
							bankBannedObj.setIsActive(bankBannedDTObj.getIsActive());
							bankBannedObj.setModifiedBy(session.getUserName());
							bankBannedObj.setModifiedDate(new Date());
						} else {
							bankBannedObj.setIsActive(Constants.Yes);
						}
						iBankBannedWordsService.saveOrUpdate(bankBannedObj);
					}
					RequestContext.getCurrentInstance().execute("complete.show();");
					bankBannedDataTableList.clear();
					setBankId(null);
					setCountryId(null);
				} else {
					RequestContext.getCurrentInstance().execute("pleaseenterword.show();");
				}
			} else {
				RequestContext.getCurrentInstance().execute("norecordsfound.show();");
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			return;
		}
	}

	public List<CountryMasterDesc> getAllCountryList() {
		try {
			allCountryList = igeneralService.getCountryList(session.getLanguageId());
			log.info("Total country list is::::::::::::::::::::::::::::::::::::::::" + allCountryList.size());
			for (CountryMasterDesc countryMasterDesc : allCountryList) {
				bankBannedCountryMap.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getFsCountryMaster().getCountryCode());
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			return null;
		}
		return allCountryList;
	}

	public void removeRecordBankBannedRecords(BankBannedWordsMaintananceDataTableBean bankBannedWordDataTableObj) {
		if (null != bankBannedWordDataTableObj.getBankBannedPk()) {
			if (bankBannedWordDataTableObj.getIsActive().equalsIgnoreCase(Constants.Yes)) {
				iBankBannedWordsService.deleteRecord(bankBannedWordDataTableObj.getBankBannedPk(), session.getUserName());
				showBannnedWordsInDatatable();
			} else if (bankBannedWordDataTableObj.getIsActive().equalsIgnoreCase(Constants.U)) {
				iBankBannedWordsService.activateWord(bankBannedWordDataTableObj.getBankBannedPk(), session.getUserName());
				showBannnedWordsInDatatable();
			}
		} else {
			bankBannedDataTableList.remove(bankBannedWordDataTableObj);
			if (bankBannedDataTableList.size() == 0) {
				setRenderAcceptButtonPanel(false);
			}
		}
	}

	public void editRecord(BankBannedWordsMaintananceDataTableBean bankBannedDataObj) {
		if (!bankBannedDataObj.getBankBannedName().isEmpty()) {
			if (!bankBannedDataObj.getBankBannedName().trim().equalsIgnoreCase("")) {
				int count = 0;
				for (BankBannedWordsMaintananceDataTableBean bannedDTObj : bankBannedDataTableList) {
					if (bannedDTObj.getBankBannedName().equalsIgnoreCase(bankBannedDataObj.getBankBannedName())) {
						count = count + 1;
						if (count >= 2) {
							bankBannedDataObj.setBankBannedName("");
							RequestContext.getCurrentInstance().execute("duplicate.show();");
						}
					}
				}
			} else {
				RequestContext.getCurrentInstance().execute("pleaseenterword.show();");
			}
		} else {
			RequestContext.getCurrentInstance().execute("pleaseenterword.show();");
		}
	}
}
