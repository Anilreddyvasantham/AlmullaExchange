package com.amg.exchange.loyalty.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.loyalty.model.LoyaltyCatagoryMaster;
import com.amg.exchange.loyalty.model.LoyaltyCatergoryMasterDesc;
import com.amg.exchange.loyalty.model.LoyaltyParameterSetting;
import com.amg.exchange.loyalty.model.LoyaltyType;
import com.amg.exchange.loyalty.model.LoyaltyTypeDesc;
import com.amg.exchange.loyalty.service.ILoyaltyParameterService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("loyaltyParameterSettingBean")
@Scope("session")
public class LoyaltyParameterSettingBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log=Logger.getLogger(LoyaltyParameterSettingBean.class);
	// page level Variables
	private BigDecimal ltyParameterPk;
	private BigDecimal categoryId;
	private String categoryName;
	private BigDecimal loyaltyTypeId;
	private String loyaltyName;
	private BigDecimal countryId;
	private String countryName;
	private BigDecimal bankId;
	private String bankname;
	private BigDecimal currencyId;
	private String currencyName;
	private BigDecimal operatorId;
	private String operatorName;
	private BigDecimal amount;
	private BigDecimal remittanceModeId;
	private String remittanceName;
	private BigDecimal deliveryModeId;
	private String deliveryName;
	private BigDecimal noOfDays;
	private BigDecimal dayOfTheWeekId;
	private String dayOfTheWeekName;
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	private Date fromTime;
	private Date toTime;
	private BigDecimal loyaltyPoints;
	private String relatopnShipToIntroDuction;
	private BigDecimal stateId;
	private String stateName;
	private BigDecimal applicationCountryId;
	private String templateCode;
	private String templateCriteria;
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

	// List
	private List<LoyaltyParameterSettingDataTable> loyaltyParameterSettingDT = new CopyOnWriteArrayList<LoyaltyParameterSettingDataTable>();
	private List<LoyaltyParameterSettingDataTable> loyaltyParameterSettingNewDT = new CopyOnWriteArrayList<LoyaltyParameterSettingDataTable>();
	private List<CountryMasterDesc> countryMasterDesclist = new ArrayList<CountryMasterDesc>();
	private List<BankMaster> bankMasterlist = new ArrayList<BankMaster>();
	private List<CurrencyMaster> currencyMasterlist = new ArrayList<CurrencyMaster>();
	private List<RemittanceModeDescription> remittanceModeDescriptionlist = new ArrayList<RemittanceModeDescription>();
	private List<DeliveryModeDesc> deliveryModeDesclist = new ArrayList<DeliveryModeDesc>();
	private List<StateMasterDesc> stateMasterDescList = new ArrayList<StateMasterDesc>();
	private List<BankAccountDetails> bankAccountDetailsList = new ArrayList<BankAccountDetails>();
	private List<LoyaltyTypeDesc> loyalityTypeDescsList = new ArrayList<LoyaltyTypeDesc>();
	private List<LoyaltyCatergoryMasterDesc> loyaltyCatergoryMasterDescList = new ArrayList<LoyaltyCatergoryMasterDesc>();
	// Map
	Map<BigDecimal, String> mapCountryMasterDesclist = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapBankMasterlist = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapCurrencyMasterlist = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapRemittanceModeDescriptionlist = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapDeliveryModeDesclist = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapStateMasterDescList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapLoyalityTypeDescsList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapLoyaltyCatergoryMasterDescList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapbankAccountDetailsList = new HashMap<BigDecimal, String>();

	// Service
	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	ILoyaltyParameterService loyaltyParameterService;

	@Autowired
	IFundEstimationService<T> fundEstimationService;

	SessionStateManage sessionStateManage = new SessionStateManage();

	private LoyaltyParameterSettingDataTable loyaltyParameterSettingObj = null;



	public BigDecimal getLtyParameterPk() {
		return ltyParameterPk;
	}

	public void setLtyParameterPk(BigDecimal ltyParameterPk) {
		this.ltyParameterPk = ltyParameterPk;
	}

	public BigDecimal getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public BigDecimal getLoyaltyTypeId() {
		return loyaltyTypeId;
	}

	public void setLoyaltyTypeId(BigDecimal loyaltyTypeId) {
		this.loyaltyTypeId = loyaltyTypeId;
	}

	public String getLoyaltyName() {
		return loyaltyName;
	}

	public void setLoyaltyName(String loyaltyName) {
		this.loyaltyName = loyaltyName;
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

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public BigDecimal getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(BigDecimal operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}

	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}

	public String getRemittanceName() {
		return remittanceName;
	}

	public void setRemittanceName(String remittanceName) {
		this.remittanceName = remittanceName;
	}

	public BigDecimal getDeliveryModeId() {
		return deliveryModeId;
	}

	public void setDeliveryModeId(BigDecimal deliveryModeId) {
		this.deliveryModeId = deliveryModeId;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public BigDecimal getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(BigDecimal noOfDays) {
		this.noOfDays = noOfDays;
	}

	public BigDecimal getDayOfTheWeekId() {
		return dayOfTheWeekId;
	}

	public void setDayOfTheWeekId(BigDecimal dayOfTheWeekId) {
		this.dayOfTheWeekId = dayOfTheWeekId;
	}

	public String getDayOfTheWeekName() {
		return dayOfTheWeekName;
	}

	public void setDayOfTheWeekName(String dayOfTheWeekName) {
		this.dayOfTheWeekName = dayOfTheWeekName;
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

	public Date getFromTime() {
		return fromTime;
	}

	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToTime() {
		return toTime;
	}

	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}

	public BigDecimal getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(BigDecimal loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public String getRelatopnShipToIntroDuction() {
		return relatopnShipToIntroDuction;
	}

	public void setRelatopnShipToIntroDuction(String relatopnShipToIntroDuction) {
		this.relatopnShipToIntroDuction = relatopnShipToIntroDuction;
	}

	public BigDecimal getStateId() {
		return stateId;
	}

	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
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

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateCriteria() {
		return templateCriteria;
	}

	public void setTemplateCriteria(String templateCriteria) {
		this.templateCriteria = templateCriteria;
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

	public List<LoyaltyParameterSettingDataTable> getLoyaltyParameterSettingDT() {
		return loyaltyParameterSettingDT;
	}

	public void setLoyaltyParameterSettingDT(List<LoyaltyParameterSettingDataTable> loyaltyParameterSettingDT) {
		this.loyaltyParameterSettingDT = loyaltyParameterSettingDT;
	}

	public List<LoyaltyParameterSettingDataTable> getLoyaltyParameterSettingNewDT() {
		return loyaltyParameterSettingNewDT;
	}

	public void setLoyaltyParameterSettingNewDT(List<LoyaltyParameterSettingDataTable> loyaltyParameterSettingNewDT) {
		this.loyaltyParameterSettingNewDT = loyaltyParameterSettingNewDT;
	}

	public List<CountryMasterDesc> getCountryMasterDesclist() {
		return countryMasterDesclist;
	}

	public void setCountryMasterDesclist(List<CountryMasterDesc> countryMasterDesclist) {
		this.countryMasterDesclist = countryMasterDesclist;
	}

	public List<BankMaster> getBankMasterlist() {
		return bankMasterlist;
	}

	public void setBankMasterlist(List<BankMaster> bankMasterlist) {
		this.bankMasterlist = bankMasterlist;
	}

	public List<CurrencyMaster> getCurrencyMasterlist() {
		return currencyMasterlist;
	}

	public void setCurrencyMasterlist(List<CurrencyMaster> currencyMasterlist) {
		this.currencyMasterlist = currencyMasterlist;
	}

	public List<RemittanceModeDescription> getRemittanceModeDescriptionlist() {
		return remittanceModeDescriptionlist;
	}

	public void setRemittanceModeDescriptionlist(List<RemittanceModeDescription> remittanceModeDescriptionlist) {
		this.remittanceModeDescriptionlist = remittanceModeDescriptionlist;
	}

	public List<DeliveryModeDesc> getDeliveryModeDesclist() {
		return deliveryModeDesclist;
	}

	public void setDeliveryModeDesclist(List<DeliveryModeDesc> deliveryModeDesclist) {
		this.deliveryModeDesclist = deliveryModeDesclist;
	}

	public List<StateMasterDesc> getStateMasterDescList() {
		return stateMasterDescList;
	}

	public void setStateMasterDescList(List<StateMasterDesc> stateMasterDescList) {
		this.stateMasterDescList = stateMasterDescList;
	}

	public List<BankAccountDetails> getBankAccountDetailsList() {
		return bankAccountDetailsList;
	}

	public void setBankAccountDetailsList(List<BankAccountDetails> bankAccountDetailsList) {
		this.bankAccountDetailsList = bankAccountDetailsList;
	}

	public Map<BigDecimal, String> getMapCountryMasterDesclist() {
		return mapCountryMasterDesclist;
	}

	public void setMapCountryMasterDesclist(Map<BigDecimal, String> mapCountryMasterDesclist) {
		this.mapCountryMasterDesclist = mapCountryMasterDesclist;
	}

	public Map<BigDecimal, String> getMapBankMasterlist() {
		return mapBankMasterlist;
	}

	public void setMapBankMasterlist(Map<BigDecimal, String> mapBankMasterlist) {
		this.mapBankMasterlist = mapBankMasterlist;
	}

	public Map<BigDecimal, String> getMapCurrencyMasterlist() {
		return mapCurrencyMasterlist;
	}

	public void setMapCurrencyMasterlist(Map<BigDecimal, String> mapCurrencyMasterlist) {
		this.mapCurrencyMasterlist = mapCurrencyMasterlist;
	}

	public Map<BigDecimal, String> getMapRemittanceModeDescriptionlist() {
		return mapRemittanceModeDescriptionlist;
	}

	public void setMapRemittanceModeDescriptionlist(Map<BigDecimal, String> mapRemittanceModeDescriptionlist) {
		this.mapRemittanceModeDescriptionlist = mapRemittanceModeDescriptionlist;
	}

	public Map<BigDecimal, String> getMapDeliveryModeDesclist() {
		return mapDeliveryModeDesclist;
	}

	public void setMapDeliveryModeDesclist(Map<BigDecimal, String> mapDeliveryModeDesclist) {
		this.mapDeliveryModeDesclist = mapDeliveryModeDesclist;
	}

	public Map<BigDecimal, String> getMapStateMasterDescList() {
		return mapStateMasterDescList;
	}

	public void setMapStateMasterDescList(Map<BigDecimal, String> mapStateMasterDescList) {
		this.mapStateMasterDescList = mapStateMasterDescList;
	}

	public List<LoyaltyTypeDesc> getLoyalityTypeDescsList() {
		return loyalityTypeDescsList;
	}

	public void setLoyalityTypeDescsList(List<LoyaltyTypeDesc> loyalityTypeDescsList) {
		this.loyalityTypeDescsList = loyalityTypeDescsList;
	}

	public List<LoyaltyCatergoryMasterDesc> getLoyaltyCatergoryMasterDescList() {
		return loyaltyCatergoryMasterDescList;
	}

	public void setLoyaltyCatergoryMasterDescList(List<LoyaltyCatergoryMasterDesc> loyaltyCatergoryMasterDescList) {
		this.loyaltyCatergoryMasterDescList = loyaltyCatergoryMasterDescList;
	}

	public Map<BigDecimal, String> getMapLoyalityTypeDescsList() {
		return mapLoyalityTypeDescsList;
	}

	public void setMapLoyalityTypeDescsList(Map<BigDecimal, String> mapLoyalityTypeDescsList) {
		this.mapLoyalityTypeDescsList = mapLoyalityTypeDescsList;
	}

	public Map<BigDecimal, String> getMapLoyaltyCatergoryMasterDescList() {
		return mapLoyaltyCatergoryMasterDescList;
	}

	public void setMapLoyaltyCatergoryMasterDescList(Map<BigDecimal, String> mapLoyaltyCatergoryMasterDescList) {
		this.mapLoyaltyCatergoryMasterDescList = mapLoyaltyCatergoryMasterDescList;
	}

	public LoyaltyParameterSettingDataTable getLoyaltyParameterSettingObj() {
		return loyaltyParameterSettingObj;
	}

	public void setLoyaltyParameterSettingObj(LoyaltyParameterSettingDataTable loyaltyParameterSettingObj) {
		this.loyaltyParameterSettingObj = loyaltyParameterSettingObj;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public Map<BigDecimal, String> getMapbankAccountDetailsList() {
		return mapbankAccountDetailsList;
	}

	public void setMapbankAccountDetailsList(Map<BigDecimal, String> mapbankAccountDetailsList) {
		this.mapbankAccountDetailsList = mapbankAccountDetailsList;
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
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// loyalty Parameter Page Navigation
	public void loyaltyParameterSettingPageNavigation() {
		loyaltyParameterSettingDT.clear();
		loyaltyParameterSettingNewDT.clear();
		setBooRenderDataTable(false);
		setBooSaveOrExit(false);
		setBooAdd(true);
		setBooApproval(false);
		setBooRead(false);
		setBooClearPanel(false);
		setBooSubmitPanel(false);
		clearAllFields();
		setCategoryId(null);
		setCategoryName(null);
		setLoyaltyTypeId(null);
		setLoyaltyName(null);
		setCountryId(null);
		setCountryName(null);

		// form loading to fetch All Countries,Remittance&Delivery
		fetchAllCountries();
		fetchAllRemitDesc();
		fetchAllDeliveryDesc();
		fetchAllLoyaltyType();
		fetchAllLoyaltyCatergory();

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "LoyaltyParameterSetting.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyParameterSetting.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}


	}

	// form loading calling methods
	public void fetchAllCountries() {
		try{
			mapCountryMasterDesclist.clear();
			countryMasterDesclist = generalService.getCountryList(sessionStateManage.getLanguageId());
			for (CountryMasterDesc countryMasterDesc : countryMasterDesclist) {
				mapCountryMasterDesclist.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	public void fetchAllRemitDesc() {
		try{
			mapRemittanceModeDescriptionlist.clear();
			remittanceModeDescriptionlist = generalService.getRemittanceList(sessionStateManage.getLanguageId());
			for (RemittanceModeDescription remittanceModeDescription : remittanceModeDescriptionlist) {
				mapRemittanceModeDescriptionlist.put(remittanceModeDescription.getRemittanceModeMaster().getRemittanceModeId(), remittanceModeDescription.getRemittanceDescription());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		} 
	}

	public void fetchAllDeliveryDesc() {
		try{
			mapDeliveryModeDesclist.clear();
			deliveryModeDesclist = generalService.getDeliveryModeList(sessionStateManage.getLanguageId());
			for (DeliveryModeDesc deliveryModeDesc : deliveryModeDesclist) {
				mapDeliveryModeDesclist.put(deliveryModeDesc.getDeliveryMode().getDeliveryModeId(), deliveryModeDesc.getEnglishDeliveryName());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	public void fetchAllLoyaltyType() {
		try{
			mapLoyalityTypeDescsList.clear();
			loyalityTypeDescsList = loyaltyParameterService.toFetchAllLoyaltyTypeDesc(sessionStateManage.getLanguageId());
			for (LoyaltyTypeDesc loyalityTypeDesc : loyalityTypeDescsList) {
				mapLoyalityTypeDescsList.put(loyalityTypeDesc.getLoyalityTypeId().getLoyalityTypeId(), loyalityTypeDesc.getFullDescription());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	public void fetchAllLoyaltyCatergory() {
		try{
			mapLoyaltyCatergoryMasterDescList.clear();
			loyaltyCatergoryMasterDescList = loyaltyParameterService.toFetchAllLoyalityCatergoryDesc(sessionStateManage.getLanguageId());
			for (LoyaltyCatergoryMasterDesc loyaltyCatergoryMasterDesc : loyaltyCatergoryMasterDescList) {
				mapLoyaltyCatergoryMasterDescList.put(loyaltyCatergoryMasterDesc.getLoyaltyCategoryId().getLoyaltyCatagoryId(), loyaltyCatergoryMasterDesc.getFullDesc());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	// ajaxMethodCalling
	public void toFetchBankAndStateBasedOnCountry() throws Exception{
		// based on country to Fetch All bank
		toFetchBankBasedOnCountry();
		// based on country to Fetch All bank
		toFetchStateBasedOnCountry();

	}

	// based on country to Fetch All bank
	public void toFetchBankBasedOnCountry() {
		try{
			mapBankMasterlist.clear();
			bankMasterlist = generalService.getAllBankList(sessionStateManage.getLanguageId(), getCountryId());
			for (BankMaster bankMaster : bankMasterlist) {
				mapBankMasterlist.put(bankMaster.getBankId(), bankMaster.getBankFullName());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	// based on country to Fetch state
	public void toFetchStateBasedOnCountry() {
		try{
			mapStateMasterDescList.clear();
			stateMasterDescList = generalService.getStateList(sessionStateManage.getLanguageId(), getCountryId());
			for (StateMasterDesc stateMasterDesc : stateMasterDescList) {
				mapStateMasterDescList.put(stateMasterDesc.getFsStateMaster().getStateId(), stateMasterDesc.getStateName());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	// based on Bank to Fetch Currency
	public void toFetchCurrencyBasedOnBank() {
		try{
			mapbankAccountDetailsList.clear();
			bankAccountDetailsList = fundEstimationService.getCurrencyOfBank(getBankId());
			for (BankAccountDetails bankAccountDetails : bankAccountDetailsList) {
				mapbankAccountDetailsList.put(bankAccountDetails.getFsCurrencyMaster().getCurrencyId(), bankAccountDetails.getFsCurrencyMaster().getCurrencyName());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	// ToAmount Validation
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

	public void toFromTimeValidation() {
		if (getFromTime() != null) {
			setToTime(null);
		}
	}

	public void toTimeValidation() {
		try{
			if (getFromTime() != null) {
				if (getFromTime().after(getToTime())) {
					setToTime(null);
					RequestContext.getCurrentInstance().execute("toTime.show();");
					return;
				}
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}

	}

	// Duplicate Checking for DataTable
	public void duplicateChekingLoyaltyParameterSetting() {
		if (loyaltyParameterSettingDT.size() > 0) {
			for (LoyaltyParameterSettingDataTable loyaltyParameterSettingDtObj : loyaltyParameterSettingDT) {
				if (loyaltyParameterSettingDtObj.getCategoryId().equals(getCategoryId()) && loyaltyParameterSettingDtObj.getLoyaltyTypeId().equals(getLoyaltyTypeId()) && loyaltyParameterSettingDtObj.getCountryId().equals(getCountryId())
						&& loyaltyParameterSettingDtObj.getBankId().equals(getBankId()) && loyaltyParameterSettingDtObj.getCurrencyId().equals(getCurrencyId())) {
					clearAllFields();
					RequestContext.getCurrentInstance().execute("datatable.show();");
					return;
				}
			}
		}
		addRecorsToDataTable();
	}

	// clearAllFields
	public void clearAllFields() {
		setLtyParameterPk(null);
		/*setCategoryId(null);
		    setCategoryName(null);
		    setLoyaltyTypeId(null);
		    setLoyaltyName(null);
		    setCountryId(null);
		    setCountryName(null);
		 */ 
		setTemplateCode(null);
		setTemplateCriteria(null);
		setBankId(null);
		setBankname(null);
		setCurrencyId(null);
		setCurrencyName(null);
		setRemittanceModeId(null);
		setRemittanceName(null);
		setOperatorId(null);
		setOperatorName(null);
		setAmount(null);
		setDeliveryModeId(null);
		setDeliveryName(null);
		setDayOfTheWeekId(null);
		setDayOfTheWeekName(null);
		setFromAmount(null);
		setToAmount(null);
		setFromTime(null);
		setToTime(null);
		setLoyaltyPoints(null);
		setRelatopnShipToIntroDuction(null);
		setNoOfDays(null);
		setStateId(null);
		setStateName(null);
		setApplicationCountryId(null);
		setIfEditClicked(false);
	}

	// Add Records To Data Table
	public void addRecorsToDataTable() {
		try{
			setBooEditButton(false);
			setBooClearPanel(false);
			setBooSubmitPanel(false);
			LoyaltyParameterSettingDataTable loyaltyParameterSettingDTOBJ = new LoyaltyParameterSettingDataTable();
			loyaltyParameterSettingDTOBJ.setCategoryId(getCategoryId());
			loyaltyParameterSettingDTOBJ.setCategoryName(mapLoyaltyCatergoryMasterDescList.get(getCategoryId()));
			loyaltyParameterSettingDTOBJ.setLoyaltyTypeId(getLoyaltyTypeId());
			loyaltyParameterSettingDTOBJ.setLoyaltyName(mapLoyalityTypeDescsList.get(getLoyaltyTypeId()));
			loyaltyParameterSettingDTOBJ.setCountryId(getCountryId());
			loyaltyParameterSettingDTOBJ.setCountryName(mapCountryMasterDesclist.get(getCountryId()));
			loyaltyParameterSettingDTOBJ.setBankId(getBankId());
			loyaltyParameterSettingDTOBJ.setBankname(mapBankMasterlist.get(getBankId()));
			loyaltyParameterSettingDTOBJ.setCurrencyId(getCurrencyId());
			loyaltyParameterSettingDTOBJ.setCurrencyName(mapbankAccountDetailsList.get(getCurrencyId()));
			loyaltyParameterSettingDTOBJ.setRemittanceModeId(getRemittanceModeId());
			loyaltyParameterSettingDTOBJ.setRemittanceName(mapRemittanceModeDescriptionlist.get(getRemittanceModeId()));
			loyaltyParameterSettingDTOBJ.setApplicationCountryId(sessionStateManage.getCountryId());
			if (getOperatorId() != null) {
				loyaltyParameterSettingDTOBJ.setOperatorId(getOperatorId());
				if (getOperatorId().intValue() == 1) {
					loyaltyParameterSettingDTOBJ.setOperatorName("Lessthan");
				} else if (getOperatorId().intValue() == 2) {
					loyaltyParameterSettingDTOBJ.setOperatorName("Lessthan Or Equal To");
				} else if (getOperatorId().intValue() == 3) {
					loyaltyParameterSettingDTOBJ.setOperatorName("Greaterthan");
				} else if (getOperatorId().intValue() == 4) {
					loyaltyParameterSettingDTOBJ.setOperatorName("Greaterthan Or Equal To");
				}else if (getOperatorId().intValue() == 5) {
					loyaltyParameterSettingDTOBJ.setOperatorName("Every");
				}
			}
			loyaltyParameterSettingDTOBJ.setAmount(getAmount());
			loyaltyParameterSettingDTOBJ.setFromAmount(getFromAmount());
			loyaltyParameterSettingDTOBJ.setToAmount(getToAmount());
			loyaltyParameterSettingDTOBJ.setDeliveryModeId(getDeliveryModeId());
			loyaltyParameterSettingDTOBJ.setDeliveryName(mapDeliveryModeDesclist.get(getDeliveryModeId()));
			if (getDayOfTheWeekId() != null) {
				loyaltyParameterSettingDTOBJ.setDayOfTheWeekId(getDayOfTheWeekId());
				if (getDayOfTheWeekId().intValue() == 1) {
					loyaltyParameterSettingDTOBJ.setDayOfTheWeekName("Monday");
				} else if (getDayOfTheWeekId().intValue() == 2) {
					loyaltyParameterSettingDTOBJ.setDayOfTheWeekName("Tuesday");
				} else if (getDayOfTheWeekId().intValue() == 3) {
					loyaltyParameterSettingDTOBJ.setDayOfTheWeekName("Wednesday");
				} else if (getDayOfTheWeekId().intValue() == 4) {
					loyaltyParameterSettingDTOBJ.setDayOfTheWeekName("Thuresday");
				} else if (getDayOfTheWeekId().intValue() == 5) {
					loyaltyParameterSettingDTOBJ.setDayOfTheWeekName("Friday");
				} else if (getDayOfTheWeekId().intValue() == 6) {
					loyaltyParameterSettingDTOBJ.setDayOfTheWeekName("Saturday");
				} else if (getDayOfTheWeekId().intValue() == 7) {
					loyaltyParameterSettingDTOBJ.setDayOfTheWeekName("Sunday");
				}else if (getDayOfTheWeekId().intValue() == 8) {
					loyaltyParameterSettingDTOBJ.setDayOfTheWeekName("All Days");
				}
			}
			loyaltyParameterSettingDTOBJ.setFromAmount(getFromAmount());
			loyaltyParameterSettingDTOBJ.setToAmount(getToAmount());
			loyaltyParameterSettingDTOBJ.setFromTime(getFromTime());
			loyaltyParameterSettingDTOBJ.setToTime(getToTime());
			loyaltyParameterSettingDTOBJ.setLoyaltyPoints(getLoyaltyPoints());
			loyaltyParameterSettingDTOBJ.setRelatopnShipToIntroDuction(getRelatopnShipToIntroDuction());
			loyaltyParameterSettingDTOBJ.setNoOfDays(getNoOfDays());
			loyaltyParameterSettingDTOBJ.setStateId(getStateId());
			loyaltyParameterSettingDTOBJ.setTemplateCode(getTemplateCode());
			loyaltyParameterSettingDTOBJ.setTemplateCriteria(getTemplateCriteria());
			loyaltyParameterSettingDTOBJ.setStateName(mapStateMasterDescList.get(getStateId()));
			loyaltyParameterSettingDTOBJ.setCreatedBy(getCreatedBy());
			loyaltyParameterSettingDTOBJ.setCreatedDate(getCreatedDate());
			if (getLtyParameterPk() != null) {
				if (loyaltyParameterSettingDTOBJ.getCategoryId().equals(loyaltyParameterSettingObj.getCategoryId()) && loyaltyParameterSettingDTOBJ.getLoyaltyTypeId().equals(loyaltyParameterSettingObj.getLoyaltyTypeId())
						&& loyaltyParameterSettingDTOBJ.getCountryId().equals(loyaltyParameterSettingObj.getCountryId()) && loyaltyParameterSettingDTOBJ.getBankId().equals(loyaltyParameterSettingObj.getBankId())
						&& loyaltyParameterSettingDTOBJ.getCurrencyId().equals(loyaltyParameterSettingObj.getCurrencyId()) && loyaltyParameterSettingDTOBJ.getRemittanceModeId().equals(loyaltyParameterSettingObj.getRemittanceModeId())
						&& loyaltyParameterSettingDTOBJ.getAmount().equals(loyaltyParameterSettingObj.getAmount()) && loyaltyParameterSettingDTOBJ.getDeliveryModeId().equals(loyaltyParameterSettingObj.getDeliveryModeId()) 
						&& loyaltyParameterSettingDTOBJ.getFromAmount().equals(loyaltyParameterSettingObj.getFromAmount()) && loyaltyParameterSettingDTOBJ.getToAmount().equals(loyaltyParameterSettingObj.getToAmount())
						&& loyaltyParameterSettingDTOBJ.getLoyaltyPoints().equals(loyaltyParameterSettingObj.getLoyaltyPoints()) && loyaltyParameterSettingDTOBJ.getRelatopnShipToIntroDuction().equalsIgnoreCase(loyaltyParameterSettingObj.getRelatopnShipToIntroDuction())
						&& loyaltyParameterSettingDTOBJ.getNoOfDays().equals(loyaltyParameterSettingObj.getNoOfDays()) && loyaltyParameterSettingDTOBJ.getStateId().equals(loyaltyParameterSettingObj.getStateId())
						&& loyaltyParameterSettingDTOBJ.getFromTime().equals(loyaltyParameterSettingObj.getFromTime()) && loyaltyParameterSettingDTOBJ.getToTime().equals(loyaltyParameterSettingObj.getToTime())
						&& loyaltyParameterSettingDTOBJ.getOperatorId().equals(loyaltyParameterSettingObj.getOperatorId()) && loyaltyParameterSettingDTOBJ.getDayOfTheWeekId().equals(loyaltyParameterSettingObj.getDayOfTheWeekId())) {
					loyaltyParameterSettingDTOBJ.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					loyaltyParameterSettingDTOBJ.setIsActive(getIsActive());
					loyaltyParameterSettingDTOBJ.setModifiedBy(getModifiedBy());
					loyaltyParameterSettingDTOBJ.setModifiedDate(getModifiedDate());
					loyaltyParameterSettingDTOBJ.setApprovedBy(getApprovedBy());
					loyaltyParameterSettingDTOBJ.setApprovedDate(getApprovedDate());
					loyaltyParameterSettingDTOBJ.setRemarks(getRemarks());
					loyaltyParameterSettingDTOBJ.setLtyParameterPk(getLtyParameterPk());
				} else {
					loyaltyParameterSettingDTOBJ.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					loyaltyParameterSettingDTOBJ.setIsActive(Constants.U);
					loyaltyParameterSettingDTOBJ.setModifiedBy(sessionStateManage.getUserName());
					loyaltyParameterSettingDTOBJ.setModifiedDate(new Date());
					loyaltyParameterSettingDTOBJ.setApprovedBy(null);
					loyaltyParameterSettingDTOBJ.setApprovedDate(null);
					loyaltyParameterSettingDTOBJ.setRemarks(null);
					loyaltyParameterSettingDTOBJ.setLtyParameterPk(getLtyParameterPk());
					loyaltyParameterSettingDTOBJ.setIfEditClicked(true);
				}
			} else {
				loyaltyParameterSettingDTOBJ.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				loyaltyParameterSettingDTOBJ.setIsActive(Constants.U);
				loyaltyParameterSettingDTOBJ.setCreatedBy(sessionStateManage.getUserName());
				loyaltyParameterSettingDTOBJ.setCreatedDate(new Date());
				loyaltyParameterSettingDTOBJ.setIfEditClicked(true);
			}
			loyaltyParameterSettingDT.add(loyaltyParameterSettingDTOBJ);

			if (getLtyParameterPk() == null) {
				loyaltyParameterSettingNewDT.add(loyaltyParameterSettingDTOBJ);
			}
			setBooRenderDataTable(true);
			setBooSaveOrExit(true);
			setBooApproval(false);
			setBooRead(false);
			clearAllFields();
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::addRecorsToDataTable");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	// edit
	public void edit(LoyaltyParameterSettingDataTable dataTable) {
		try{
			setLoyaltyParameterSettingObj(dataTable);
			setIfEditClicked(true);
			setLtyParameterPk(dataTable.getLtyParameterPk());
			setTemplateCode(dataTable.getTemplateCode());
			setTemplateCriteria(dataTable.getTemplateCriteria());
			setCategoryId(dataTable.getCategoryId());
			setLoyaltyTypeId(dataTable.getLoyaltyTypeId());
			setCountryId(dataTable.getCountryId());
			toFetchBankBasedOnCountry();
			setBankId(dataTable.getBankId());
			toFetchCurrencyBasedOnBank();
			setCurrencyId(dataTable.getCurrencyId());
			setRemittanceModeId(dataTable.getRemittanceModeId());
			setApplicationCountryId(dataTable.getApplicationCountryId());
			if (dataTable.getOperatorName() != null) {
				if (dataTable.getOperatorName().equalsIgnoreCase("Lessthan")) {
					//setOperatorId(new BigDecimal(1));
					setOperatorId(dataTable.getOperatorId());
				} else if (dataTable.getOperatorName().equalsIgnoreCase("Lessthan Or Equal To")) {
					//setOperatorId(new BigDecimal(2));
					setOperatorId(dataTable.getOperatorId());
				} else if (dataTable.getOperatorName().equalsIgnoreCase("Greaterthan")) {
					//setOperatorId(new BigDecimal(3));
					setOperatorId(dataTable.getOperatorId());
				} else if (dataTable.getOperatorName().equalsIgnoreCase("Greaterthan Or Equal To")) {
					//setOperatorId(new BigDecimal(4));
					setOperatorId(dataTable.getOperatorId());
				}else if (dataTable.getOperatorName().equalsIgnoreCase("Every")) {
					//setOperatorId(new BigDecimal(5));
					setOperatorId(dataTable.getOperatorId());
				}
			}
			setAmount(dataTable.getAmount());
			setFromAmount(dataTable.getFromAmount());
			setToAmount(dataTable.getToAmount());
			setDeliveryModeId(dataTable.getDeliveryModeId());
			if (dataTable.getDayOfTheWeekName() != null) {
				if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("Monday")) {
					//setDayOfTheWeekId(new BigDecimal(1));
					setDayOfTheWeekId(dataTable.getDayOfTheWeekId());
				} else if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("Tuesday")) {
					//setDayOfTheWeekId(new BigDecimal(2));
					setDayOfTheWeekId(dataTable.getDayOfTheWeekId());
				} else if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("Wednesday")) {
					//setDayOfTheWeekId(new BigDecimal(3));
					setDayOfTheWeekId(dataTable.getDayOfTheWeekId());
				} else if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("Thuresday")) {
					//setDayOfTheWeekId(new BigDecimal(4));
					setDayOfTheWeekId(dataTable.getDayOfTheWeekId());
				} else if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("Friday")) {
					//setDayOfTheWeekId(new BigDecimal(5));
					setDayOfTheWeekId(dataTable.getDayOfTheWeekId());
				} else if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("Saturday")) {
					//setDayOfTheWeekId(new BigDecimal(6));
					setDayOfTheWeekId(dataTable.getDayOfTheWeekId());
				} else if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("Sunday")) {
					//setDayOfTheWeekId(new BigDecimal(7));
					setDayOfTheWeekId(dataTable.getDayOfTheWeekId());
				}else if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("All Days")) {
					//setDayOfTheWeekId(new BigDecimal(8));
					setDayOfTheWeekId(dataTable.getDayOfTheWeekId());
				}
			}
			setFromAmount(dataTable.getFromAmount());
			setToAmount(dataTable.getToAmount());
			setFromTime(dataTable.getFromTime());
			setToTime(dataTable.getToTime());
			setLoyaltyPoints(dataTable.getLoyaltyPoints());
			setRelatopnShipToIntroDuction(dataTable.getRelatopnShipToIntroDuction());
			setNoOfDays(dataTable.getNoOfDays());
			toFetchStateBasedOnCountry();
			setStateId(dataTable.getStateId());
			setCreatedBy(dataTable.getCreatedBy());
			setCreatedDate(dataTable.getCreatedDate());
			if(dataTable.getDynamicLabelForActivateDeactivate() != null){
			setDynamicLabelForActivateDeactivate(dataTable.getDynamicLabelForActivateDeactivate());
			}
			setIsActive(dataTable.getIsActive());
			setModifiedBy(dataTable.getModifiedBy());
			setModifiedDate(dataTable.getModifiedDate());
			setApprovedBy(dataTable.getApprovedBy());
			setApprovedDate(dataTable.getApprovedDate());
			setRemarks(dataTable.getRemarks());
			loyaltyParameterSettingDT.remove(dataTable);
			loyaltyParameterSettingNewDT.remove(dataTable);
			if (loyaltyParameterSettingDT.size() == 0) {
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

	// Save Data Table records
	public void saveAllLoyaltyParameterSetting() {
		try {
			for (LoyaltyParameterSettingDataTable loyaltyParameterSettingDtObj : loyaltyParameterSettingDT) {
				if (loyaltyParameterSettingDtObj.getIfEditClicked().equals(true)) {
					LoyaltyParameterSetting loyaltyParameterSetting = new LoyaltyParameterSetting();
					loyaltyParameterSetting.setLoyaltyParameterId(loyaltyParameterSettingDtObj.getLtyParameterPk());
					// Category
					LoyaltyCatagoryMaster loyaltyCatagoryMaster = new LoyaltyCatagoryMaster();
					loyaltyCatagoryMaster.setLoyaltyCatagoryId(loyaltyParameterSettingDtObj.getCategoryId());
					loyaltyParameterSetting.setLoyaltyCatagoryMaster(loyaltyCatagoryMaster);
					// Lotalaty Type
					LoyaltyType loyaltyType = new LoyaltyType();
					loyaltyType.setLoyalityTypeId(loyaltyParameterSettingDtObj.getLoyaltyTypeId());
					loyaltyParameterSetting.setLoyaltyType(loyaltyType);
					// Country Master
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(loyaltyParameterSettingDtObj.getCountryId());
					loyaltyParameterSetting.setFsCountryMaster(countryMaster);
					// Bank Master
					BankMaster bankMaster = new BankMaster();
					bankMaster.setBankId(loyaltyParameterSettingDtObj.getBankId());
					loyaltyParameterSetting.setExBankMaster(bankMaster);
					// CurrencyMaster
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(loyaltyParameterSettingDtObj.getCurrencyId());
					loyaltyParameterSetting.setExCurrencyMaster(currencyMaster);
					// RemittanceModeMaster
					/* RemittanceModeMaster remittanceModeMaster = new RemittanceModeMaster();
						  remittanceModeMaster.setRemittanceModeId(loyaltyParameterSettingDtObj.getRemittanceModeId());*/
					loyaltyParameterSetting.setRemittanceModeMaster(loyaltyParameterSettingDtObj.getRemittanceModeId());
					// Operator
					if (loyaltyParameterSettingDtObj.getOperatorName() != null) {
						if (loyaltyParameterSettingDtObj.getOperatorName().equalsIgnoreCase("Lessthan")) {
							loyaltyParameterSetting.setCummulativeOperator("Lessthan");
						} else if (loyaltyParameterSettingDtObj.getOperatorName().equalsIgnoreCase("Lessthan Or Equal To")) {
							loyaltyParameterSetting.setCummulativeOperator("Lessthan Or Equal To");
						} else if (loyaltyParameterSettingDtObj.getOperatorName().equalsIgnoreCase("Greaterthan")) {
							loyaltyParameterSetting.setCummulativeOperator("Greaterthan");
						} else if (loyaltyParameterSettingDtObj.getOperatorName().equalsIgnoreCase("Greaterthan Or Equal To")) {
							loyaltyParameterSetting.setCummulativeOperator("Greaterthan Or Equal To");
						}else if (loyaltyParameterSettingDtObj.getOperatorName().equalsIgnoreCase("Every")) {
							loyaltyParameterSetting.setCummulativeOperator("Every");
						}
					}
					// Amount Figure
					loyaltyParameterSetting.setCummulativeFigure(loyaltyParameterSettingDtObj.getAmount());
					// Delivery Mode
					/* DeliveryMode deliveryMode = new DeliveryMode();
						  deliveryMode.setDeliveryModeId(loyaltyParameterSettingDtObj.getDeliveryModeId());*/
					loyaltyParameterSetting.setDeliveryMode(loyaltyParameterSettingDtObj.getDeliveryModeId());
					// Day Of The Week
					if (loyaltyParameterSettingDtObj.getDayOfTheWeekName() != null) {
						if (loyaltyParameterSettingDtObj.getDayOfTheWeekName().equalsIgnoreCase("Monday")) {
							loyaltyParameterSetting.setDateOfTheWeek("Monday");
						} else if (loyaltyParameterSettingDtObj.getDayOfTheWeekName().equalsIgnoreCase("Tuesday")) {
							loyaltyParameterSetting.setDateOfTheWeek("Tuesday");
						} else if (loyaltyParameterSettingDtObj.getDayOfTheWeekName().equalsIgnoreCase("Wednesday")) {
							loyaltyParameterSetting.setDateOfTheWeek("Wednesday");
						} else if (loyaltyParameterSettingDtObj.getDayOfTheWeekName().equalsIgnoreCase("Thuresday")) {
							loyaltyParameterSetting.setDateOfTheWeek("Thuresday");
						} else if (loyaltyParameterSettingDtObj.getDayOfTheWeekName().equalsIgnoreCase("Friday")) {
							loyaltyParameterSetting.setDateOfTheWeek("Friday");
						} else if (loyaltyParameterSettingDtObj.getDayOfTheWeekName().equalsIgnoreCase("Saturday")) {
							loyaltyParameterSetting.setDateOfTheWeek("Saturday");
						} else if (loyaltyParameterSettingDtObj.getDayOfTheWeekName().equalsIgnoreCase("Sunday")) {
							loyaltyParameterSetting.setDateOfTheWeek("Sunday");
						}else if (loyaltyParameterSettingDtObj.getDayOfTheWeekName().equalsIgnoreCase("All Days")) {
							loyaltyParameterSetting.setDateOfTheWeek("All Days");
						}
					}
					loyaltyParameterSetting.setFromAmount(loyaltyParameterSettingDtObj.getFromAmount());
					loyaltyParameterSetting.setToAmount(loyaltyParameterSettingDtObj.getToAmount());
					loyaltyParameterSetting.setTemplateCode(loyaltyParameterSettingDtObj.getTemplateCode());
					loyaltyParameterSetting.setTemplateCriteria(loyaltyParameterSettingDtObj.getTemplateCriteria());
					GregorianCalendar processtime = new GregorianCalendar();
					processtime.setTime(loyaltyParameterSettingDtObj.getFromTime());
					XMLGregorianCalendar xmlFin = DatatypeFactory.newInstance().newXMLGregorianCalendar(processtime);

					String hours = xmlFin.getHour() + "";
					String minutes = xmlFin.getMinute() + "";
					String starttimeHours = "";
					String starttimeMinutes = "";

					if (hours.length() == 1) {
						starttimeHours = "0" + "" + hours;
					} else {
						starttimeHours = hours;
					}
					if (minutes.length() == 1) {
						starttimeMinutes = "0" + minutes;
					} else {
						starttimeMinutes = minutes;
					}
					if (starttimeHours.length() == 2 && starttimeHours.length() == 2) {
						loyaltyParameterSetting.setFromTime(starttimeHours + ":" + starttimeMinutes);
					}
					GregorianCalendar processtime2 = new GregorianCalendar();
					processtime2.setTime(loyaltyParameterSettingDtObj.getToTime());
					XMLGregorianCalendar xmlFin2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(processtime2);

					String hours2 = xmlFin2.getHour() + "";
					String minutes2 = xmlFin2.getMinute() + "";
					String starttimeHours2 = "";
					String starttimeMinutes2 = "";

					if (hours2.length() == 1) {
						starttimeHours2 = "0" + "" + hours2;
					} else {
						starttimeHours2 = hours2;
					}
					if (minutes2.length() == 1) {
						starttimeMinutes2 = "0" + minutes2;
					} else {
						starttimeMinutes2 = minutes2;
					}
					if (starttimeHours2.length() == 2 && starttimeHours2.length() == 2) {
						loyaltyParameterSetting.setToTime(starttimeHours2 + ":" + starttimeMinutes2);
					}
					loyaltyParameterSetting.setPoints(loyaltyParameterSettingDtObj.getLoyaltyPoints());
					loyaltyParameterSetting.setNoOfDays(loyaltyParameterSettingDtObj.getNoOfDays());
					loyaltyParameterSetting.setReferenceIntrodution(loyaltyParameterSettingDtObj.getRelatopnShipToIntroDuction());
					// StateMaster
					StateMaster stateMaster = new StateMaster();
					stateMaster.setStateId(loyaltyParameterSettingDtObj.getStateId());
					loyaltyParameterSetting.setFsStateMaster(stateMaster);
					// Application country
					CountryMaster applicationCuntry = new CountryMaster();
					applicationCuntry.setCountryId(loyaltyParameterSettingDtObj.getApplicationCountryId());
					loyaltyParameterSetting.setApplicationCountry(applicationCuntry);
					// Common Variables for Saving
					loyaltyParameterSetting.setCreatedBy(loyaltyParameterSettingDtObj.getCreatedBy());
					loyaltyParameterSetting.setCreatedDate(loyaltyParameterSettingDtObj.getCreatedDate());
					loyaltyParameterSetting.setModifiedBy(loyaltyParameterSettingDtObj.getModifiedBy());
					loyaltyParameterSetting.setModifiedDate(loyaltyParameterSettingDtObj.getModifiedDate());
					loyaltyParameterSetting.setApprovedBy(loyaltyParameterSettingDtObj.getApprovedBy());
					loyaltyParameterSetting.setApprovedDate(loyaltyParameterSettingDtObj.getApprovedDate());
					loyaltyParameterSetting.setIsActive(loyaltyParameterSettingDtObj.getIsActive());
					loyaltyParameterSetting.setRemarks(loyaltyParameterSettingDtObj.getRemarks());
					loyaltyParameterService.saveLoyaltyParameterSettingAllValues(loyaltyParameterSetting);
				}
			}
			loyaltyParameterSettingDT.clear();
			loyaltyParameterSettingNewDT.clear();
			RequestContext.getCurrentInstance().execute("complete.show();");
			clearAllFields();
			setCategoryId(null);
			setCategoryName(null);
			setLoyaltyTypeId(null);
			setLoyaltyName(null);
			setCountryId(null);
			setCountryName(null);

			setBooRenderDataTable(false);
			setBooSaveOrExit(false);
			setBooAdd(true);
			setBooApproval(false);
			setBooRead(false);
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}

	}

	// click on ok button for saved
	public void clickOnOKSave() {
		loyaltyParameterSettingDT.clear();
		loyaltyParameterSettingNewDT.clear();
		clearAllFields();
		setCategoryId(null);
		setCategoryName(null);
		setLoyaltyTypeId(null);
		setLoyaltyName(null);
		setCountryId(null);
		setCountryName(null);

		setBooRenderDataTable(false);
		setBooSaveOrExit(false);
		setBooAdd(true);
		setBooRead(false);
		setBooApproval(false);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyParameterSetting.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	//exit
	public void exit(){
		loyaltyParameterSettingDT.clear();
		loyaltyParameterSettingNewDT.clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}  
	}
	// not saved button ok button
	public void loyalityParameterSettingNotSaved() {
		loyaltyParameterSettingDT.clear();
		loyaltyParameterSettingNewDT.clear();
		clearAllFields();
		setCategoryId(null);
		setCategoryName(null);
		setLoyaltyTypeId(null);
		setLoyaltyName(null);
		setCountryId(null);
		setCountryName(null);

		setBooRenderDataTable(false);
		setBooSaveOrExit(false);
		setBooAdd(true);
		setBooRead(false);
		setBooApproval(false);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyParameterSetting.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	public void viewAllLoyaltyParameterSetting() {
		loyaltyParameterSettingDT.clear();
		try{
			if(getLoyaltyTypeId() == null || getCategoryId() == null || getCountryId() == null){
				clearAllFields();
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("viewSearch.show();");
				return;
			}else{
				List<LoyaltyParameterSetting> lstParameterSettings = loyaltyParameterService.toFetchAllViewDetails(getLoyaltyTypeId(), getCategoryId(), getCountryId());

				if (lstParameterSettings.size() > 0) {
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
				for (LoyaltyParameterSetting loyaltyParameterSetting : lstParameterSettings) {
					LoyaltyParameterSettingDataTable loyaltyParameterSettingDtobj = new LoyaltyParameterSettingDataTable();
					loyaltyParameterSettingDtobj.setLtyParameterPk(loyaltyParameterSetting.getLoyaltyParameterId());
					loyaltyParameterSettingDtobj.setTemplateCode(loyaltyParameterSetting.getTemplateCode());
					loyaltyParameterSettingDtobj.setTemplateCriteria(loyaltyParameterSetting.getTemplateCriteria());
					loyaltyParameterSettingDtobj.setCategoryId(loyaltyParameterSetting.getLoyaltyCatagoryMaster().getLoyaltyCatagoryId());
					loyaltyParameterSettingDtobj.setCategoryName(loyaltyParameterService.toFetchCategoryNameBesedOnCategoryId(loyaltyParameterSetting.getLoyaltyCatagoryMaster().getLoyaltyCatagoryId(), sessionStateManage.getLanguageId()));
					loyaltyParameterSettingDtobj.setLoyaltyTypeId(loyaltyParameterSetting.getLoyaltyType().getLoyalityTypeId());
					loyaltyParameterSettingDtobj.setLoyaltyName(loyaltyParameterService.toFtechTypeNameBasedOnTypeId(loyaltyParameterSetting.getLoyaltyType().getLoyalityTypeId(), sessionStateManage.getLanguageId()));
					loyaltyParameterSettingDtobj.setCountryId(loyaltyParameterSetting.getFsCountryMaster().getCountryId());
					loyaltyParameterSettingDtobj.setCountryName(loyaltyParameterService.toFetchCountryNameBasedonCountryId(loyaltyParameterSetting.getFsCountryMaster().getCountryId(), sessionStateManage.getLanguageId()));
					loyaltyParameterSettingDtobj.setBankId(loyaltyParameterSetting.getExBankMaster().getBankId());
					loyaltyParameterSettingDtobj.setBankname(loyaltyParameterService.toFetchBankNameBasedonBankId(loyaltyParameterSetting.getExBankMaster().getBankId()));
					loyaltyParameterSettingDtobj.setCurrencyId(loyaltyParameterSetting.getExCurrencyMaster().getCurrencyId());
					loyaltyParameterSettingDtobj.setCurrencyName(loyaltyParameterService.toFetchCurrencyNameBasedonCurrencyId(loyaltyParameterSetting.getExCurrencyMaster().getCurrencyId()));
					// loyaltyParameterSettingDtobj.setRemittanceModeId(loyaltyParameterSetting.getRemittanceModeMaster().getRemittanceModeId());
					//loyaltyParameterSettingDtobj.setRemittanceName(mapRemittanceModeDescriptionlist.get(loyaltyParameterSetting.getRemittanceModeMaster().getRemittanceModeId()));
					loyaltyParameterSettingDtobj.setRemittanceModeId(loyaltyParameterSetting.getRemittanceModeMaster());
					loyaltyParameterSettingDtobj.setRemittanceName(mapRemittanceModeDescriptionlist.get(loyaltyParameterSetting.getRemittanceModeMaster()));
					/*loyaltyParameterSettingDtobj.setDeliveryModeId(loyaltyParameterSetting.getDeliveryMode().getDeliveryModeId());
			      loyaltyParameterSettingDtobj.setDeliveryName(mapDeliveryModeDesclist.get(loyaltyParameterSetting.getDeliveryMode().getDeliveryModeId()));*/
					loyaltyParameterSettingDtobj.setDeliveryModeId(loyaltyParameterSetting.getDeliveryMode());
					loyaltyParameterSettingDtobj.setDeliveryName(mapDeliveryModeDesclist.get(loyaltyParameterSetting.getDeliveryMode()));
					loyaltyParameterSettingDtobj.setStateId(loyaltyParameterSetting.getFsStateMaster().getStateId());
					loyaltyParameterSettingDtobj.setStateName(loyaltyParameterService.toFetchStateNameBasedonStateId(loyaltyParameterSetting.getFsStateMaster().getStateId(), sessionStateManage.getLanguageId()));
					// Operator
					if (loyaltyParameterSetting.getCummulativeOperator() != null) {
						if (loyaltyParameterSetting.getCummulativeOperator().equalsIgnoreCase("Lessthan")) {
							loyaltyParameterSettingDtobj.setOperatorName("Lessthan");
							loyaltyParameterSettingDtobj.setOperatorId(new BigDecimal(1));
						} else if (loyaltyParameterSetting.getCummulativeOperator().equalsIgnoreCase("Lessthan Or Equal To")) {
							loyaltyParameterSettingDtobj.setOperatorName("Lessthan Or Equal To");
							loyaltyParameterSettingDtobj.setOperatorId(new BigDecimal(2));
						} else if (loyaltyParameterSetting.getCummulativeOperator().equalsIgnoreCase("Greaterthan")) {
							loyaltyParameterSettingDtobj.setOperatorName("Greaterthan");
							loyaltyParameterSettingDtobj.setOperatorId(new BigDecimal(3));
						} else if (loyaltyParameterSetting.getCummulativeOperator().equalsIgnoreCase("Greaterthan Or Equal To")) {
							loyaltyParameterSettingDtobj.setOperatorName("Greaterthan Or Equal To");
							loyaltyParameterSettingDtobj.setOperatorId(new BigDecimal(4));
						}else if (loyaltyParameterSetting.getCummulativeOperator().equalsIgnoreCase("Every")) {
							loyaltyParameterSettingDtobj.setOperatorName("Every");
							loyaltyParameterSettingDtobj.setOperatorId(new BigDecimal(5));
						}
					}

					// Day Of The Week
					if (loyaltyParameterSetting.getDateOfTheWeek() != null) {
						if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("Monday")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("Monday");
							loyaltyParameterSettingDtobj.setDayOfTheWeekId(new BigDecimal(1));
						} else if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("Tuesday")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("Tuesday");
							loyaltyParameterSettingDtobj.setDayOfTheWeekId(new BigDecimal(2));
						} else if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("Wednesday")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("Wednesday");
							loyaltyParameterSettingDtobj.setDayOfTheWeekId(new BigDecimal(3));
						} else if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("Thuresday")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("Thuresday");
							loyaltyParameterSettingDtobj.setDayOfTheWeekId(new BigDecimal(4));
						} else if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("Friday")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("Friday");
							loyaltyParameterSettingDtobj.setDayOfTheWeekId(new BigDecimal(5));
						} else if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("Saturday")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("Saturday");
							loyaltyParameterSettingDtobj.setDayOfTheWeekId(new BigDecimal(6));
						} else if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("Sunday")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("Sunday");
							loyaltyParameterSettingDtobj.setDayOfTheWeekId(new BigDecimal(7));
						}else if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("All Days")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("All Days");
							loyaltyParameterSettingDtobj.setDayOfTheWeekId(new BigDecimal(8));
						}
					}
					loyaltyParameterSettingDtobj.setAmount(loyaltyParameterSetting.getCummulativeFigure());
					loyaltyParameterSettingDtobj.setFromAmount(loyaltyParameterSetting.getFromAmount());
					loyaltyParameterSettingDtobj.setToAmount(loyaltyParameterSetting.getToAmount());
					// From time
					String ptFromTime = loyaltyParameterSetting.getFromTime();
					DateFormat formatter = new SimpleDateFormat("HH:mm");
					try {
						Date fromDate = (Date) formatter.parse(ptFromTime);
						loyaltyParameterSettingDtobj.setFromTime(fromDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					// To time
					String ptToTime = loyaltyParameterSetting.getToTime();
					DateFormat simformatter = new SimpleDateFormat("HH:mm");
					try {
						Date toDate = (Date) simformatter.parse(ptToTime);
						loyaltyParameterSettingDtobj.setToTime(toDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}

					loyaltyParameterSettingDtobj.setLoyaltyPoints(loyaltyParameterSetting.getPoints());
					loyaltyParameterSettingDtobj.setRelatopnShipToIntroDuction(loyaltyParameterSetting.getReferenceIntrodution());
					loyaltyParameterSettingDtobj.setNoOfDays(loyaltyParameterSetting.getNoOfDays());
					loyaltyParameterSettingDtobj.setApplicationCountryId(loyaltyParameterSetting.getApplicationCountry().getCountryId());
					// common Variables
					loyaltyParameterSettingDtobj.setCreatedBy(loyaltyParameterSetting.getCreatedBy());
					loyaltyParameterSettingDtobj.setCreatedDate(loyaltyParameterSetting.getCreatedDate());
					loyaltyParameterSettingDtobj.setModifiedBy(loyaltyParameterSetting.getModifiedBy());
					loyaltyParameterSettingDtobj.setModifiedDate(loyaltyParameterSetting.getModifiedDate());
					loyaltyParameterSettingDtobj.setApprovedBy(loyaltyParameterSetting.getApprovedBy());
					loyaltyParameterSettingDtobj.setApprovedDate(loyaltyParameterSetting.getApprovedDate());
					loyaltyParameterSettingDtobj.setRemarks(loyaltyParameterSetting.getRemarks());
					loyaltyParameterSettingDtobj.setIsActive(loyaltyParameterSetting.getIsActive());
					loyaltyParameterSettingDtobj.setRenderEditButton(true);
					loyaltyParameterSettingDtobj.setBooEditButton(false);
					if(loyaltyParameterSettingDtobj.getIsActive() != null){
					if (loyaltyParameterSettingDtobj.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						loyaltyParameterSettingDtobj.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					} else if (loyaltyParameterSettingDtobj.getIsActive().equalsIgnoreCase(Constants.D)) {
						loyaltyParameterSettingDtobj.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					} else if (loyaltyParameterSettingDtobj.getIsActive().equalsIgnoreCase(Constants.U) && loyaltyParameterSettingDtobj.getModifiedBy() == null && loyaltyParameterSettingDtobj.getModifiedDate() == null && loyaltyParameterSettingDtobj.getApprovedBy() == null
							&& loyaltyParameterSettingDtobj.getApprovedDate() == null && loyaltyParameterSettingDtobj.getRemarks() == null) {
						loyaltyParameterSettingDtobj.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					} else {
						loyaltyParameterSettingDtobj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}
					}
					loyaltyParameterSettingDT.add(loyaltyParameterSettingDtobj);
				}
				loyaltyParameterSettingDT.addAll(loyaltyParameterSettingNewDT);
				clearAllFields();
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyParameterSetting.xhtml");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	// Activate Deactivate Detete from started
	public void checkStatus(LoyaltyParameterSettingDataTable dataTable) {
		if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
			RequestContext.getCurrentInstance().execute("pending.show();");
			return;
		} else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			loyaltyParameterSettingDT.remove(dataTable);
			loyaltyParameterSettingNewDT.remove(dataTable);
		} else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			dataTable.setRemarksCheck(true);
			setApprovedBy(dataTable.getApprovedBy());
			setApprovedDate(dataTable.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
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
		if (loyaltyParameterSettingDT.size() == 0) {
			setBooRenderDataTable(false);
			setBooSaveOrExit(false);
			setBooAdd(true); 
			setBooApproval(false);
			setBooRead(false);

		}
	}

	// Hard Delete
	public void loyaltyParameterSettingConformDelete()throws Exception {
		if (loyaltyParameterSettingDT.size() > 0) {
			for (LoyaltyParameterSettingDataTable loyaltyParameterSettingDataTable : loyaltyParameterSettingDT) {
				if (loyaltyParameterSettingDataTable.getPermentDeleteCheck() != null) {
					if (loyaltyParameterSettingDataTable.getPermentDeleteCheck().equals(true)) {
						deleteRecordLoyaltyParameterSetting(loyaltyParameterSettingDataTable);
						loyaltyParameterSettingDT.remove(loyaltyParameterSettingDataTable);
					}
				}
			}
		}
	}

	public void deleteRecordLoyaltyParameterSetting(LoyaltyParameterSettingDataTable dataTable) {
		try{
			loyaltyParameterService.deleteRecordPermentelyFromLoyaltyParameterSetting(dataTable.getLtyParameterPk());
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
			for (LoyaltyParameterSettingDataTable loyaltyParameterSettingDataTable : loyaltyParameterSettingDT) {
				if (loyaltyParameterSettingDataTable.getRemarksCheck() != null) {
					if (loyaltyParameterSettingDataTable.getRemarksCheck().equals(true)) {
						loyaltyParameterSettingDataTable.setRemarks(getRemarks());
						updateLoyaltyParameterSetting(loyaltyParameterSettingDataTable);

						try {
							clearAllFields();
							setBooRenderDataTable(false);
							setBooSaveOrExit(false);
							FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyParameterSetting.xhtml");
						} catch (Exception e) {
							e.printStackTrace();
						}
						// viewAllLoyaltyParameterSetting();
						setRemarks(null);
					}
				}
			}

		} else {
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		}
	}

	public void updateLoyaltyParameterSetting(LoyaltyParameterSettingDataTable dataTable) {
		try{
			loyaltyParameterService.upDateActiveRecordtoDeActive(dataTable.getLtyParameterPk(), dataTable.getRemarks(), sessionStateManage.getUserName());
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	// click on Active link
	public void activateRecord() throws Exception{
		if (loyaltyParameterSettingDT.size() > 0) {
			for (LoyaltyParameterSettingDataTable loyaltyParameterSettingDataTable : loyaltyParameterSettingDT) {
				if (loyaltyParameterSettingDataTable.getActiveRecordCheck() != null) {
					if (loyaltyParameterSettingDataTable.getActiveRecordCheck().equals(true)) {
						conformActiveRecordToPendingForApproval(loyaltyParameterSettingDataTable);
					}
				}
			}
		}
	}

	public void conformActiveRecordToPendingForApproval(LoyaltyParameterSettingDataTable dataTable) {
		try{
			loyaltyParameterService.DeActiveRecordToPendingForApprovalOfLoyaltyParameterSetting(dataTable.getLtyParameterPk(), sessionStateManage.getUserName());
			setBooRenderDataTable(false);
			setBooSaveOrExit(false);
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}

		// viewAllLoyaltyParameterSetting();
	}

	public void cancelRemarks() {
		setRemarks(null);
		setBooApproval(false); 
		setBooRead(false);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyParameterSetting.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	// Activate Deactivate Detete started Ended

	// Approvel Started
	public void loyaltyParameterApprovalPageNavigation() {
		setBooAdd(false);
		setBooApproval(false);
		setBooSaveOrExit(false);
		setBooRenderDataTable(true);
		setBooSubmitPanel(true);
		setBooClearPanel(true);
		setBooRead(false);
		fetchAllCountries();
		fetchAllRemitDesc();
		fetchAllDeliveryDesc();
		fetchAllLoyaltyType();
		fetchAllLoyaltyCatergory();
		loyaltyParameterSettingDT.clear();
		loyaltyParameterSettingNewDT.clear();
		fetchRecordforLoyaltyParameterForApprovalDataTable();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "LoyaltyParameterSettingApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyParameterSettingApproval.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	// fetch from Db All Records
	public void fetchRecordforLoyaltyParameterForApprovalDataTable() {
		try{
			loyaltyParameterSettingDT.clear();
			List<LoyaltyParameterSetting> lstParameterSettings = loyaltyParameterService.toFetchAllApprovalDetailsFormLoyaltyParameterSetting();
			if (lstParameterSettings.size() > 0) {
				for (LoyaltyParameterSetting loyaltyParameterSetting : lstParameterSettings) {
					LoyaltyParameterSettingDataTable loyaltyParameterSettingDtobj = new LoyaltyParameterSettingDataTable();
					loyaltyParameterSettingDtobj.setLtyParameterPk(loyaltyParameterSetting.getLoyaltyParameterId());
					loyaltyParameterSettingDtobj.setCategoryId(loyaltyParameterSetting.getLoyaltyCatagoryMaster().getLoyaltyCatagoryId());
					loyaltyParameterSettingDtobj.setCategoryName(loyaltyParameterService.toFetchCategoryNameBesedOnCategoryId(loyaltyParameterSetting.getLoyaltyCatagoryMaster().getLoyaltyCatagoryId(), sessionStateManage.getLanguageId()));
					loyaltyParameterSettingDtobj.setLoyaltyTypeId(loyaltyParameterSetting.getLoyaltyType().getLoyalityTypeId());
					loyaltyParameterSettingDtobj.setLoyaltyName(loyaltyParameterService.toFtechTypeNameBasedOnTypeId(loyaltyParameterSetting.getLoyaltyType().getLoyalityTypeId(), sessionStateManage.getLanguageId()));
					loyaltyParameterSettingDtobj.setCountryId(loyaltyParameterSetting.getFsCountryMaster().getCountryId());
					loyaltyParameterSettingDtobj.setCountryName(loyaltyParameterService.toFetchCountryNameBasedonCountryId(loyaltyParameterSetting.getFsCountryMaster().getCountryId(), sessionStateManage.getLanguageId()));
					loyaltyParameterSettingDtobj.setBankId(loyaltyParameterSetting.getExBankMaster().getBankId());
					loyaltyParameterSettingDtobj.setBankname(loyaltyParameterService.toFetchBankNameBasedonBankId(loyaltyParameterSetting.getExBankMaster().getBankId()));
					loyaltyParameterSettingDtobj.setCurrencyId(loyaltyParameterSetting.getExCurrencyMaster().getCurrencyId());
					loyaltyParameterSettingDtobj.setCurrencyName(loyaltyParameterService.toFetchCurrencyNameBasedonCurrencyId(loyaltyParameterSetting.getExCurrencyMaster().getCurrencyId()));
					/*loyaltyParameterSettingDtobj.setRemittanceModeId(loyaltyParameterSetting.getRemittanceModeMaster().getRemittanceModeId());
					loyaltyParameterSettingDtobj.setRemittanceName(mapRemittanceModeDescriptionlist.get(loyaltyParameterSetting.getRemittanceModeMaster().getRemittanceModeId()));
					loyaltyParameterSettingDtobj.setDeliveryModeId(loyaltyParameterSetting.getDeliveryMode().getDeliveryModeId());
					loyaltyParameterSettingDtobj.setDeliveryName(mapDeliveryModeDesclist.get(loyaltyParameterSetting.getDeliveryMode().getDeliveryModeId()));*/
					loyaltyParameterSettingDtobj.setRemittanceModeId(loyaltyParameterSetting.getRemittanceModeMaster());
					loyaltyParameterSettingDtobj.setRemittanceName(mapRemittanceModeDescriptionlist.get(loyaltyParameterSetting.getRemittanceModeMaster()));
					loyaltyParameterSettingDtobj.setDeliveryModeId(loyaltyParameterSetting.getDeliveryMode());
					loyaltyParameterSettingDtobj.setDeliveryName(mapDeliveryModeDesclist.get(loyaltyParameterSetting.getDeliveryMode()));
					loyaltyParameterSettingDtobj.setStateId(loyaltyParameterSetting.getFsStateMaster().getStateId());
					loyaltyParameterSettingDtobj.setStateName(loyaltyParameterService.toFetchStateNameBasedonStateId(loyaltyParameterSetting.getFsStateMaster().getStateId(), sessionStateManage.getLanguageId()));
					loyaltyParameterSettingDtobj.setTemplateCode(loyaltyParameterSetting.getTemplateCode());
					loyaltyParameterSettingDtobj.setTemplateCriteria(loyaltyParameterSetting.getTemplateCriteria());
					// Operator
					if (loyaltyParameterSetting.getCummulativeOperator() != null) {
						if (loyaltyParameterSetting.getCummulativeOperator().equalsIgnoreCase("Lessthan")) {
							loyaltyParameterSettingDtobj.setOperatorName("Lessthan");
						} else if (loyaltyParameterSetting.getCummulativeOperator().equalsIgnoreCase("Lessthan Or Equal To")) {
							loyaltyParameterSettingDtobj.setOperatorName("Lessthan Or Equal To");
						} else if (loyaltyParameterSetting.getCummulativeOperator().equalsIgnoreCase("Greaterthan")) {
							loyaltyParameterSettingDtobj.setOperatorName("Greaterthan");
						} else if (loyaltyParameterSetting.getCummulativeOperator().equalsIgnoreCase("Greaterthan Or Equal To")) {
							loyaltyParameterSettingDtobj.setOperatorName("Greaterthan Or Equal To");
						}else if (loyaltyParameterSetting.getCummulativeOperator().equalsIgnoreCase("Every")) {
							loyaltyParameterSettingDtobj.setOperatorName("Every");
						}
					}

					// Day Of The Week
					if (loyaltyParameterSetting.getDateOfTheWeek() != null) {
						if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("Monday")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("Monday");
						} else if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("Tuesday")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("Tuesday");
						} else if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("Wednesday")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("Wednesday");
						} else if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("Thuresday")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("Thuresday");
						} else if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("Friday")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("Friday");
						} else if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("Saturday")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("Saturday");
						} else if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("Sunday")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("Sunday");
						}else if (loyaltyParameterSetting.getDateOfTheWeek().equalsIgnoreCase("All Days")) {
							loyaltyParameterSettingDtobj.setDayOfTheWeekName("All Days");
						}
					}
					loyaltyParameterSettingDtobj.setAmount(loyaltyParameterSetting.getCummulativeFigure());
					loyaltyParameterSettingDtobj.setFromAmount(loyaltyParameterSetting.getFromAmount());
					loyaltyParameterSettingDtobj.setToAmount(loyaltyParameterSetting.getToAmount());
					// From time
					String ptFromTime = loyaltyParameterSetting.getFromTime();
					DateFormat formatter = new SimpleDateFormat("HH:mm");
					try {
						Date fromDate = (Date) formatter.parse(ptFromTime);
						loyaltyParameterSettingDtobj.setFromTime(fromDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					// To time
					String ptToTime = loyaltyParameterSetting.getToTime();
					DateFormat simformatter = new SimpleDateFormat("HH:mm");
					try {
						Date toDate = (Date) simformatter.parse(ptToTime);
						loyaltyParameterSettingDtobj.setToTime(toDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}

					loyaltyParameterSettingDtobj.setLoyaltyPoints(loyaltyParameterSetting.getPoints());
					loyaltyParameterSettingDtobj.setRelatopnShipToIntroDuction(loyaltyParameterSetting.getReferenceIntrodution());
					loyaltyParameterSettingDtobj.setNoOfDays(loyaltyParameterSetting.getNoOfDays());
					loyaltyParameterSettingDtobj.setApplicationCountryId(loyaltyParameterSetting.getApplicationCountry().getCountryId());
					// common Variables
					loyaltyParameterSettingDtobj.setCreatedBy(loyaltyParameterSetting.getCreatedBy());
					loyaltyParameterSettingDtobj.setCreatedDate(loyaltyParameterSetting.getCreatedDate());
					loyaltyParameterSettingDtobj.setModifiedBy(loyaltyParameterSetting.getModifiedBy());
					loyaltyParameterSettingDtobj.setModifiedDate(loyaltyParameterSetting.getModifiedDate());
					loyaltyParameterSettingDtobj.setApprovedBy(loyaltyParameterSetting.getApprovedBy());
					loyaltyParameterSettingDtobj.setApprovedDate(loyaltyParameterSetting.getApprovedDate());
					loyaltyParameterSettingDtobj.setRemarks(loyaltyParameterSetting.getRemarks());
					loyaltyParameterSettingDtobj.setIsActive(loyaltyParameterSetting.getIsActive());
					loyaltyParameterSettingDtobj.setRenderEditButton(true);
					loyaltyParameterSettingDtobj.setBooEditButton(false);
					if (loyaltyParameterSettingDtobj.getIsActive() != null){
					if (loyaltyParameterSettingDtobj.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						loyaltyParameterSettingDtobj.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					} else if (loyaltyParameterSettingDtobj.getIsActive().equalsIgnoreCase(Constants.D)) {
						loyaltyParameterSettingDtobj.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					} else if (loyaltyParameterSettingDtobj.getIsActive().equalsIgnoreCase(Constants.U) && loyaltyParameterSettingDtobj.getModifiedBy() == null && loyaltyParameterSettingDtobj.getModifiedDate() == null && loyaltyParameterSettingDtobj.getApprovedBy() == null
							&& loyaltyParameterSettingDtobj.getApprovedDate() == null && loyaltyParameterSettingDtobj.getRemarks() == null) {
						loyaltyParameterSettingDtobj.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					} else {
						loyaltyParameterSettingDtobj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}
					}
					loyaltyParameterSettingDT.add(loyaltyParameterSettingDtobj);
				}
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	public void approvelCheckForLoyaltyParameterSettingMasterUser(LoyaltyParameterSettingDataTable dataTable) {
		try{
			if (!(dataTable.getModifiedBy() == null ? dataTable.getCreatedBy() : dataTable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())) {
				setLtyParameterPk(dataTable.getLtyParameterPk());
				setCategoryId(dataTable.getCategoryId());
				setLoyaltyTypeId(dataTable.getLoyaltyTypeId());
				setTemplateCode(dataTable.getTemplateCode());
				setTemplateCriteria(dataTable.getTemplateCriteria());
				setCountryId(dataTable.getCountryId());
				toFetchBankBasedOnCountry();
				setBankId(dataTable.getBankId());
				toFetchCurrencyBasedOnBank();
				setCurrencyId(dataTable.getCurrencyId());
				setRemittanceModeId(dataTable.getRemittanceModeId());
				setApplicationCountryId(dataTable.getApplicationCountryId());
				if (dataTable.getOperatorName() != null) {
					if (dataTable.getOperatorName().equalsIgnoreCase("Lessthan")) {
						setOperatorId(new BigDecimal(1));
					} else if (dataTable.getOperatorName().equalsIgnoreCase("Lessthan Or Equal To")) {
						setOperatorId(new BigDecimal(2));
					} else if (dataTable.getOperatorName().equalsIgnoreCase("Greaterthan")) {
						setOperatorId(new BigDecimal(3));
					} else if (dataTable.getOperatorName().equalsIgnoreCase("Greaterthan Or Equal To")) {
						setOperatorId(new BigDecimal(4));
					}else if (dataTable.getOperatorName().equalsIgnoreCase("Every")) {
						setOperatorId(new BigDecimal(5));
					}
				}
				setAmount(dataTable.getAmount());
				setFromAmount(dataTable.getFromAmount());
				setToAmount(dataTable.getToAmount());
				setDeliveryModeId(dataTable.getDeliveryModeId());
				if (dataTable.getDayOfTheWeekName() != null) {
					if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("Monday")) {
						setDayOfTheWeekId(new BigDecimal(1));
					} else if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("Tuesday")) {
						setDayOfTheWeekId(new BigDecimal(2));
					} else if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("Wednesday")) {
						setDayOfTheWeekId(new BigDecimal(3));
					} else if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("Thuresday")) {
						setDayOfTheWeekId(new BigDecimal(4));
					} else if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("Friday")) {
						setDayOfTheWeekId(new BigDecimal(5));
					} else if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("Saturday")) {
						setDayOfTheWeekId(new BigDecimal(6));
					} else if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("Sunday")) {
						setDayOfTheWeekId(new BigDecimal(7));
					}else if (dataTable.getDayOfTheWeekName().equalsIgnoreCase("All Days")) {
						setDayOfTheWeekId(new BigDecimal(8));
					}
				}
				setFromAmount(dataTable.getFromAmount());
				setToAmount(dataTable.getToAmount());
				setFromTime(dataTable.getFromTime());
				setToTime(dataTable.getToTime());
				setLoyaltyPoints(dataTable.getLoyaltyPoints());
				setRelatopnShipToIntroDuction(dataTable.getRelatopnShipToIntroDuction());
				setNoOfDays(dataTable.getNoOfDays());
				toFetchStateBasedOnCountry();
				setStateId(dataTable.getStateId());
				setCreatedBy(dataTable.getCreatedBy());
				setCreatedDate(dataTable.getCreatedDate());
				setDynamicLabelForActivateDeactivate(dataTable.getDynamicLabelForActivateDeactivate());
				setIsActive(dataTable.getIsActive());
				setModifiedBy(dataTable.getModifiedBy());
				setModifiedDate(dataTable.getModifiedDate());
				setBooRenderDataTable(false);
				setBooSaveOrExit(false);
				setBooAdd(false);
				setBooApproval(true);
				setBooRead(true);
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyParameterSetting.xhtml");
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
	public void loyaltyParameterSettingApproveRecord() {
		try{
			String approvalMsg = loyaltyParameterService.checkLoyaltyParameterApproveMultiUser(getLtyParameterPk(), sessionStateManage.getUserName());
			if (approvalMsg.equalsIgnoreCase("Success")) {
				RequestContext.getCurrentInstance().execute("approve.show();");
			} else {
				RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				return;
			}
		}catch(NullPointerException ne){
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

	// click ok button for Approve
	public void clickOnOKApprove() {
		clearAllFields();
		setBooAdd(false);
		setBooRenderDataTable(false);
		setBooSaveOrExit(false);
		setBooApproval(false);
		setBooRead(false);
		try {
			fetchRecordforLoyaltyParameterForApprovalDataTable();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyParameterSettingApproval.xhtml");
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void clickOnOkButton() {
		loyaltyParameterSettingDT.clear();
		fetchRecordforLoyaltyParameterForApprovalDataTable();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyParameterSettingApproval.xhtml");
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void loyaltyParameterApprovedByOhterPerson() {
		fetchRecordforLoyaltyParameterForApprovalDataTable();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyParameterSettingApproval.xhtml");
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void loyaltyParameterSettingCancel() {
		fetchRecordforLoyaltyParameterForApprovalDataTable();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../loyalty/LoyaltyParameterSettingApproval.xhtml");
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	/* Approval Ended */
	//disable submit button
	public void disableSubmit() {
		setBooSubmitPanel(true);
	}
	/* auto Compleate start*/
	public List<String> autoComplete(String query) {
		try{
			if (query.length() > 0) {
				return loyaltyParameterService.toFetchAllLoyaltyTemplateCode(query);
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

	public void populate(){
		try{
			List<LoyaltyParameterSetting> lstLoyaltyPromotionSettings = loyaltyParameterService.toCompareTheTemplateCode(getTemplateCode());
			if (lstLoyaltyPromotionSettings.size() > 0) {
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

	private String errorMessage;



	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


}
