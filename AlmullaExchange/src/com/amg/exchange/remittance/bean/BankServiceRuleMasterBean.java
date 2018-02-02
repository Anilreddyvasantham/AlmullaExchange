package com.amg.exchange.remittance.bean;

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
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.bean.RuleEngine;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.BankCharges;
import com.amg.exchange.remittance.model.BankChargesMasterLog;
import com.amg.exchange.remittance.model.BankServiceRule;
import com.amg.exchange.remittance.model.ProductGroup;
import com.amg.exchange.remittance.model.TransferMode;
import com.amg.exchange.remittance.service.IBankServiceRuleMasterService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IServiceApplicabilityRuleService;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.service.IBankAccountDetailsService;
import com.amg.exchange.treasury.service.IBankMasterService;
import com.amg.exchange.treasury.service.IPipsMasterService;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component(value = "bankServiceRule")
@Scope("session")
public class BankServiceRuleMasterBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(BankServiceRuleMasterBean.class);
	private BigDecimal bankServiceRuleIdPk;
	private BigDecimal bankServiceRuleId = null;// pk
	private BigDecimal countryId;
	private BigDecimal currencyId;
	private BigDecimal remittanceModeId;
	private BigDecimal deliveryId;
	private BigDecimal bankId;
	private String ruleName;
	private BigDecimal deliveryDays;
	private BigDecimal transferMode;
	private BigDecimal productGroup;
	private Boolean productGroupOptional = false;
	private BigDecimal chargeCurrency;
	private String chargeAccount;
	private BigDecimal commisionCurrencyId;
	private String commisionAccount;
	private BigDecimal costCurrencyId;
	private String chargeDebitAccount;
	private String chargeCreditAccount;
	private String manualFeedback;
	private String pinNoFormat;
	private String pinNoPadding;
	private String specialCharacterforPin;
	private String remittanceSwiftTrf;
	private String createdBy;
	private Date cretaedDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String bankCode;
	private String isActive;
	// for Bank Charges
	private BigDecimal bankChargesId = null;// PK
	private String chargeType;
	private BigDecimal chargeFor;
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	private BigDecimal chargeAmount;
	private String createdByBankCharges;
	private Date createdDateBankCharges;
	private String modifiedByBankCharges;
	private Date modifiedDateBankCharges;
	private Boolean renderDatatable = false;
	private BigDecimal currencyCode;
	private BigDecimal costCurrencyCode;
	private BigDecimal costAmount;
	private String customertype;	

	public String getCustomertype() {
		return customertype;
	}
	public void setCustomertype(String customertype) {
		this.customertype = customertype;
	}

	public BigDecimal getBankServiceRuleIdPk() {
		return bankServiceRuleIdPk;
	}
	public void setBankServiceRuleIdPk(BigDecimal bankServiceRuleIdPk) {
		this.bankServiceRuleIdPk = bankServiceRuleIdPk;
	}

	public BigDecimal getBankServiceRuleId() {
		return bankServiceRuleId;
	}
	public void setBankServiceRuleId(BigDecimal bankServiceRuleId) {
		this.bankServiceRuleId = bankServiceRuleId;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}

	public BigDecimal getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(BigDecimal deliveryId) {
		this.deliveryId = deliveryId;
	}

	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public BigDecimal getDeliveryDays() {
		return deliveryDays;
	}
	public void setDeliveryDays(BigDecimal deliveryDays) {
		this.deliveryDays = deliveryDays;
	}

	public BigDecimal getTransferMode() {
		return transferMode;
	}
	public void setTransferMode(BigDecimal transferMode) {
		this.transferMode = transferMode;
	}

	public BigDecimal getProductGroup() {
		return productGroup;
	}
	public void setProductGroup(BigDecimal productGroup) {
		this.productGroup = productGroup;
	}

	public Boolean getProductGroupOptional() {
		return productGroupOptional;
	}
	public void setProductGroupOptional(Boolean productGroupOptional) {
		this.productGroupOptional = productGroupOptional;
	}
	
	public BigDecimal getChargeCurrency() {
		return chargeCurrency;
	}
	public void setChargeCurrency(BigDecimal chargeCurrency) {
		this.chargeCurrency = chargeCurrency;
	}

	public String getChargeAccount() {
		return chargeAccount;
	}
	public void setChargeAccount(String chargeAccount) {
		this.chargeAccount = chargeAccount;
	}

	public BigDecimal getCommisionCurrencyId() {
		return commisionCurrencyId;
	}
	public void setCommisionCurrencyId(BigDecimal commisionCurrencyId) {
		this.commisionCurrencyId = commisionCurrencyId;
	}

	public String getCommisionAccount() {
		return commisionAccount;
	}
	public void setCommisionAccount(String commisionAccount) {
		this.commisionAccount = commisionAccount;
	}

	public BigDecimal getCostCurrencyId() {
		return costCurrencyId;
	}
	public void setCostCurrencyId(BigDecimal costCurrencyId) {
		this.costCurrencyId = costCurrencyId;
	}

	public String getChargeDebitAccount() {
		return chargeDebitAccount;
	}
	public void setChargeDebitAccount(String chargeDebitAccount) {
		this.chargeDebitAccount = chargeDebitAccount;
	}

	public String getChargeCreditAccount() {
		return chargeCreditAccount;
	}
	public void setChargeCreditAccount(String chargeCreditAccount) {
		this.chargeCreditAccount = chargeCreditAccount;
	}

	public String getManualFeedback() {
		return manualFeedback;
	}
	public void setManualFeedback(String manualFeedback) {
		this.manualFeedback = manualFeedback;
	}

	public String getPinNoFormat() {
		return pinNoFormat;
	}
	public void setPinNoFormat(String pinNoFormat) {
		this.pinNoFormat = pinNoFormat;
	}

	public String getPinNoPadding() {
		return pinNoPadding;
	}
	public void setPinNoPadding(String pinNoPadding) {
		this.pinNoPadding = pinNoPadding;
	}

	public String getSpecialCharacterforPin() {
		return specialCharacterforPin;
	}
	public void setSpecialCharacterforPin(String specialCharacterforPin) {
		this.specialCharacterforPin = specialCharacterforPin;
	}

	public String getRemittanceSwiftTrf() {
		return remittanceSwiftTrf;
	}
	public void setRemittanceSwiftTrf(String remittanceSwiftTrf) {
		this.remittanceSwiftTrf = remittanceSwiftTrf;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCretaedDate() {
		return cretaedDate;
	}
	public void setCretaedDate(Date cretaedDate) {
		this.cretaedDate = cretaedDate;
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

	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public BigDecimal getBankChargesId() {
		return bankChargesId;
	}
	public void setBankChargesId(BigDecimal bankChargesId) {
		this.bankChargesId = bankChargesId;
	}

	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public BigDecimal getChargeFor() {
		return chargeFor;
	}
	public void setChargeFor(BigDecimal chargeFor) {
		this.chargeFor = chargeFor;
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

	public BigDecimal getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(BigDecimal chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getCreatedByBankCharges() {
		return createdByBankCharges;
	}
	public void setCreatedByBankCharges(String createdByBankCharges) {
		this.createdByBankCharges = createdByBankCharges;
	}

	public Date getCreatedDateBankCharges() {
		return createdDateBankCharges;
	}
	public void setCreatedDateBankCharges(Date createdDateBankCharges) {
		this.createdDateBankCharges = createdDateBankCharges;
	}

	public String getModifiedByBankCharges() {
		return modifiedByBankCharges;
	}
	public void setModifiedByBankCharges(String modifiedByBankCharges) {
		this.modifiedByBankCharges = modifiedByBankCharges;
	}

	public Date getModifiedDateBankCharges() {
		return modifiedDateBankCharges;
	}
	public void setModifiedDateBankCharges(Date modifiedDateBankCharges) {
		this.modifiedDateBankCharges = modifiedDateBankCharges;
	}

	public Boolean getRenderDatatable() {
		return renderDatatable;
	}
	public void setRenderDatatable(Boolean renderDatatable) {
		this.renderDatatable = renderDatatable;
	}

	public List<CountryMasterDesc> getCountryList() {
		return countryList;
	}
	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}
	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public IServiceApplicabilityRuleService getIserviceApplicabilityService() {
		return iserviceApplicabilityService;
	}
	public void setIserviceApplicabilityService(IServiceApplicabilityRuleService iserviceApplicabilityService) {
		this.iserviceApplicabilityService = iserviceApplicabilityService;
	}

	public Map<BigDecimal, String> getMapCountryList() {
		return mapCountryList;
	}
	public void setMapCountryList(Map<BigDecimal, String> mapCountryList) {
		this.mapCountryList = mapCountryList;
	}

	public Map<BigDecimal, String> getMapCurrencyList() {
		return mapCurrencyList;
	}
	public void setMapCurrencyList(Map<BigDecimal, String> mapCurrencyList) {
		this.mapCurrencyList = mapCurrencyList;
	}

	public Map<BigDecimal, String> getMapRemittanceList() {
		return mapRemittanceList;
	}
	public void setMapRemittanceList(Map<BigDecimal, String> mapRemittanceList) {
		this.mapRemittanceList = mapRemittanceList;
	}

	public Map<BigDecimal, String> getMapDeliveryList() {
		return mapDeliveryList;
	}
	public void setMapDeliveryList(Map<BigDecimal, String> mapDeliveryList) {
		this.mapDeliveryList = mapDeliveryList;
	}

	public SessionStateManage getSessionStateManage() {
		return sessionStateManage;
	}
	public void setSessionStateManage(SessionStateManage sessionStateManage) {
		this.sessionStateManage = sessionStateManage;
	}

	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(BigDecimal currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getCostCurrencyCode() {
		return costCurrencyCode;
	}
	public void setCostCurrencyCode(BigDecimal costCurrencyCode) {
		this.costCurrencyCode = costCurrencyCode;
	}

	public BigDecimal getCostAmount() {
		return costAmount;
	}
	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	private List<CountryMasterDesc> countryList;
	private List<BankMaster> bankMasterList;

	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IServiceApplicabilityRuleService iserviceApplicabilityService;
	@Autowired
	IBankServiceRuleMasterService iBankServiceRuleMasterService;
	@Autowired
	IBankAccountDetailsService<T> iBankAccountDetService;
	@Autowired
	IBankMasterService<T> ibankMasterService;


	Map<BigDecimal, String> mapCountryList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapCurrencyList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapCurrencyCode = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapRemittanceList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapDeliveryList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapBank = new HashMap<BigDecimal, String>();
	// List<BankServiceRuleMasterDataTable> bankServiceDataTableList = new
	// ArrayList<BankServiceRuleMasterDataTable>();
	CopyOnWriteArrayList<BankServiceRuleMasterDataTable> bankServiceDataTableList = new CopyOnWriteArrayList<BankServiceRuleMasterDataTable>();
	CopyOnWriteArrayList<BankServiceRuleMasterDataTable> newBankServiceDataTableList = new CopyOnWriteArrayList<BankServiceRuleMasterDataTable>();
	BankServiceRuleMasterDataTable deletedRec = new BankServiceRuleMasterDataTable();
	SessionStateManage sessionStateManage = new SessionStateManage();

	/*
	 * private static final String CUSTOMERTYPE_INDIVIDUAL = "Individual";
	 * private static final String CUSTOMERTYPE_CORPORATE = "Corporate";
	 */
	public CopyOnWriteArrayList<BankServiceRuleMasterDataTable> getBankServiceDataTableList() {
		return bankServiceDataTableList;
	}
	public void setBankServiceDataTableList(CopyOnWriteArrayList<BankServiceRuleMasterDataTable> bankServiceDataTableList) {
		this.bankServiceDataTableList = bankServiceDataTableList;
	}

	public BankServiceRuleMasterDataTable getDeletedRec() {
		return deletedRec;
	}
	public void setDeletedRec(BankServiceRuleMasterDataTable deletedRec) {
		this.deletedRec = deletedRec;
	}
	/**
	 * method is responsible to populate List of countries from DB
	 * 
	 * @return
	 */
	public List<CountryMasterDesc> getCountryNameList() {
		try {
			countryList = new ArrayList<CountryMasterDesc>();
			countryList.addAll(generalService.getCountryList(sessionStateManage.getLanguageId()));
			for (CountryMasterDesc countryMasterDesc : countryList) {
				mapCountryList.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
			}
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
		}
		return countryList;
	}

	@Autowired
	IPipsMasterService pipsMasterService;
	@Autowired
	IPersonalRemittanceService personalRemittanceService;

	// CR get currency list from database
	public List<BeneCountryService> getCurrencyList() {
		/*
		 * List<CurrencyMaster> currencyList= new
		 * ArrayList<CurrencyMaster>();
		 * currencyList.addAll(generalService.getCurrencyList());
		 */
		List<BeneCountryService> lstBeneCountryService = null;
		try {
			lstBeneCountryService = pipsMasterService.getCurrencyMaster(getCountryId());
			for (BeneCountryService beneCountryService : lstBeneCountryService) {
				mapCurrencyList.put(beneCountryService.getCurrencyId().getCurrencyId(), beneCountryService.getCurrencyId().getCurrencyName());
				mapCurrencyCode.put(beneCountryService.getCurrencyId().getCurrencyId(), beneCountryService.getCurrencyId().getCurrencyCode());
			}
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
		}
		return lstBeneCountryService;
	}

	/*
	 * public List<RemittanceModeDescription> getRemittanceList(){
	 * 
	 * List<RemittanceModeDescription> remittanceList= new
	 * ArrayList<RemittanceModeDescription>();
	 * remittanceList.addAll(generalService
	 * .getRemittanceList(sessionStateManage.getLanguageId()));
	 * for(RemittanceModeDescription remittanceMode:remittanceList){
	 * 
	 * mapRemittanceList.put(remittanceMode.getRemittanceModeMaster().
	 * getRemittanceModeId(), remittanceMode.getRemittanceDescription());
	 * 
	 * } return remittanceList;
	 * 
	 * }
	 */
	/*
	 * public List<DeliveryModeDesc> getDeliveryList(){
	 * List<DeliveryModeDesc> deliveryList= new
	 * ArrayList<DeliveryModeDesc>(); deliveryList.addAll(generalService
	 * .getDeliveryModeList(sessionStateManage.getLanguageId()));
	 * for(DeliveryModeDesc deliveryMode:deliveryList){
	 * mapDeliveryList.put(deliveryMode
	 * .getDeliveryMode().getDeliveryModeId(),deliveryMode
	 * .getEnglishDeliveryName()); }
	 * 
	 * return deliveryList;
	 * 
	 * }
	 */
	public void popBank(AjaxBehaviorEvent e) {
		try {
			setBankId(null);
			setCurrencyId(null);
			setRemittanceModeId(null);
			setDeliveryId(null);
			clearDeliverAll();
			bankMasterList = new ArrayList<BankMaster>();
			List<BankApplicability> bankApplicablityList = getGeneralService().getCoresBankList(getCountryId());
			for (BankApplicability bankApplicability : bankApplicablityList) {
				bankMasterList.add(bankApplicability.getBankMaster());
			}
			
			List<BankMaster> bankMasterdetails = ibankMasterService.fetchBankServiceProvider();

			if(bankMasterdetails != null && bankMasterdetails.size() != 0){
				for (BankMaster bankMaster : bankMasterdetails) {
					if(bankMaster.getBankId() != null){
						bankMasterList.add(bankMaster);
					}
				}
			}
			// bankMasterList.addAll(getGeneralService().getAllBankList(sessionStateManage.getLanguageId(),
			// getCountryId()));
			if (bankMasterList != null) {
				for (BankMaster bankmaster : bankMasterList) {
					mapBank.put(bankmaster.getBankId(), bankmaster.getBankFullName());
				}
			}
			getRemittanceFromCountry();
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void popCurrency() {
		setBankId(null);
		setRemittanceModeId(null);
		setDeliveryId(null);
		clearDeliverAll();
		fetchProductGroupBasedonBankCurrency();
	}

	public List<BankMaster> getBankMasterList() {
		return bankMasterList;
	}

	public List<BizComponentDataDesc> getComponentName() {
		// List<BizComponentDataDesc> componentList= new
		// ArrayList<BizComponentDataDesc>();
		BigDecimal componentIdI = getGeneralService().getComponentId(Constants.CUSTOMERTYPE_INDIVIDUAL, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
		// BigDecimal componentIdC =
		// getGeneralService().getComponentId(CUSTOMERTYPE_CORPORATE,
		// sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
		// componentList.addAll(generalService.getComponentNameIndividual(sessionStateManage.getLanguageId(),
		// componentIdI,componentIdC));
		return generalService.getComponentNameIndividual(sessionStateManage.getLanguageId(), componentIdI);
	}

	public List<BizComponentDataDesc> getComponentNameCorporate() {
		// List<BizComponentDataDesc> componentList= new
		// ArrayList<BizComponentDataDesc>();
		BigDecimal componentIdC = getGeneralService().getComponentId(Constants.CUSTOMERTYPE_CORPORATE, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
		return generalService.getComponentNameIndividual(sessionStateManage.getLanguageId(), componentIdC);
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void clearCache() {
		clearAll();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankservicerulemaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/bankservicerulemaster.xhtml");
			clearAll();
			setCostAmount(BigDecimal.ZERO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getCurrencyList1();
	}

	private void addDataTableList() {
		getCutomerTypeMap();
		BankServiceRuleMasterDataTable bankServiceData = new BankServiceRuleMasterDataTable();

		bankServiceData.setBankChargesId(getBankChargesId());
 
		if (getChargeFor().intValue() == Integer.parseInt(Constants.BOTHFOR_BANKSERVICE_COMPONENT_ID)) {
			bankServiceData.setChargeFordis(Constants.BOTH);
			bankServiceData.setChargeFor(getChargeFor());
			/*
			 * }else if(getChargeFor().intValue()==
			 * Integer.parseInt
			 * (Constants.CORPORATE_COMPONENT_ID)){
			 * bankServiceData
			 * .setChargeFordis(Constants.CUSTOMERTYPE_CORPORATE
			 * );
			 * bankServiceData.setChargeFor(getChargeFor());
			 */} else {
				 bankServiceData.setChargeFordis(customerTypeMap.get(getChargeFor()));
				 bankServiceData.setChargeFor(getChargeFor());
			 }
		if (getChargeType().equalsIgnoreCase(Constants.C)) {
			bankServiceData.setChargeTypedis(Constants.COMISSION);
			bankServiceData.setChargeType(getChargeType());
		} else {
			bankServiceData.setChargeTypedis(Constants.OVERSEASE_CHARGE);
			bankServiceData.setChargeType(getChargeType());
		}
		bankServiceData.setFromAmount(getFromAmount());
		bankServiceData.setToAmount(getToAmount());
		bankServiceData.setChargeAmount(getChargeAmount());
		bankServiceData.setCurrencyCode(getBankChrgeCurrencyCode());
		bankServiceData.setCurrencyCodeName(mapCurrencyList.get(getBankChrgeCurrencyCode()));
		bankServiceData.setCostCurrencyCode(getBankChrgeCostCurrencyCode());
		bankServiceData.setCostCurrencyCodeName(mapCurrencyList.get(getBankChrgeCostCurrencyCode()));
		bankServiceData.setCostAmount(getCostAmount());
		bankServiceData.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
		bankServiceData.setRenderEditButton(true);
		bankServiceData.setIsActive(Constants.U);
		bankServiceData.setCheckSave(true);
		bankServiceData.setCreatedBy(sessionStateManage.getUserName());
		bankServiceData.setCreatedDate(new Date());
		bankServiceDataTableList.add(bankServiceData);
		newBankServiceDataTableList.add(bankServiceData);
		setRenderDatatable(true);
		clearBankCharges();
	}

	public void validationCheck() {
		boolean validateWithDb = false;
		String dbStatus = null;
		if (getBankServiceRulePkId() != null) {
			Map<String, String> mapValidation = validateWithAllRecords(getBankServiceRulePkId());
			String validateResult = mapValidation.get(Constants.VALIDATE);
			dbStatus = mapValidation.get(Constants.ACTIVE);
			if (validateResult.equalsIgnoreCase(Constants.YES)) {
				validateWithDb = true;
			} else {
				validateWithDb = false;
			}
		}
		if (!validateWithDb) {
			if (dbStatus != null) {
				if (dbStatus.equalsIgnoreCase(Constants.U)) {
					RequestContext.getCurrentInstance().execute("bnkchargesUstatus.show();");
					return;
				} else if (dbStatus.equalsIgnoreCase(Constants.D)) {
					RequestContext.getCurrentInstance().execute("bankchargesDstatus.show();");
					return;
				}
			}
		}
		if (bankServiceDataTableList.size() != 0) {
			// List<BankServiceRuleMasterDataTable>
			// localBankServiceDataTableList = new
			// ArrayList<BankServiceRuleMasterDataTable>();
			boolean rangeCheck = false;
			for (BankServiceRuleMasterDataTable bankServiceData : bankServiceDataTableList) {
				if (getChargeFor().compareTo(new BigDecimal(Constants.BOTHFOR_BANKSERVICE_COMPONENT_ID)) == 0) {
					if (bankServiceData.getChargeType().equals(getChargeType())) {
						boolean isRangeCheck = isInRange(getFromAmount(), getToAmount(), bankServiceData.getFromAmount(), bankServiceData.getToAmount());
						if (isRangeCheck) {
							rangeCheck = true;
						} else {
							rangeCheck = false;
							break;
						}
					} else {
						rangeCheck = true;
					}
				} else {
					if (bankServiceData.getChargeFor().compareTo(new BigDecimal(Constants.BOTHFOR_BANKSERVICE_COMPONENT_ID)) == 0) {
						if (bankServiceData.getChargeType().equals(getChargeType())) {
							boolean isRangeCheck = isInRange(getFromAmount(), getToAmount(), bankServiceData.getFromAmount(), bankServiceData.getToAmount());
							if (isRangeCheck) {
								rangeCheck = true;
							} else {
								rangeCheck = false;
								break;
							}
						}
					} else {
						if (bankServiceData.getChargeFor().compareTo(getChargeFor()) == 0) {
							if (bankServiceData.getChargeType().equals(getChargeType())) {
								boolean isRangeCheck = isInRange(getFromAmount(), getToAmount(), bankServiceData.getFromAmount(), bankServiceData.getToAmount());
								if (isRangeCheck) {
									rangeCheck = true;
								} else {
									rangeCheck = false;
									break;
								}
							} else {
								rangeCheck = true;
							}
						} else {
							rangeCheck = true;
						}
					}
				}
			}
			if (rangeCheck) {
				addDataTableList();
			} else {
				RequestContext.getCurrentInstance().execute("bankcharges.show();");
			}
		} else {
			addDataTableList();
		}
	}

	public void clearBankService() {
		setBankServiceRulePkId(null);
		setBankServiceRuleId(null);
		setBankId(null);
		setCountryId(null);
		setCurrencyId(null);
		setRemittanceModeId(null);
		setDeliveryId(null);
		setDeliveryDays(null);
		setRuleName(null);
		setTransferMode(null);
		setChargeCurrency(null);
		setChargeAccount(null);
		setCommisionCurrencyId(null);
		setCommisionAccount(null);
		setChargeCreditAccount(null);
		setChargeDebitAccount(null);
		setManualFeedback(null);
		setPinNoFormat(null);
		setPinNoPadding(null);
		setSpecialCharacterforPin(null);
		setRemittanceSwiftTrf(null);
		setCreatedBy(null);
		setCretaedDate(null);
		setTestKeyInFile(null);
		setBankFilePrefix(null);
		setProductGroup(null);
		mapTransferModeCode.clear();
		mapProductGroupCode.clear();
	}

	public void clearBankCharges() {
		setBankChargesId(null);
		setChargeFor(null);
		setChargeType(null);
		setChargeAmount(null);
		setFromAmount(null);
		setToAmount(null);
		setCurrencyCode(null);
		setCostCurrencyCode(null);
		setBankChrgeCurrencyCode(null);
		setBankChrgeCostCurrencyCode(null);
		setCostAmount(BigDecimal.ZERO);
	}

	public void clearAll() {
		clearBankService();
		clearBankCharges();
		bankServiceDataTableList.clear();
		newBankServiceDataTableList.clear();
		setRenderDatatable(false);
	}

	public void clearBankChargesService() {
		clearBankService();
		clearBankCharges();
		setRenderDatatable(false);
	}

	public void save() {
		try {
			// saving process 
			HashMap<String, Object> saveAllMap=  new HashMap<String, Object>();

			List<BankCharges> lstBankCharges = new ArrayList<BankCharges>();
			lstBankCharges.clear();

			BankServiceRule bankService = new BankServiceRule();

			bankService.setBankServiceRuleId(getBankServiceRulePkId());

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(getCountryId());
			bankService.setCountryId(countryMaster);

			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(getCurrencyId());
			bankService.setCurrencyId(currencyMaster);

			RemittanceModeMaster remittanceMode = new RemittanceModeMaster();
			remittanceMode.setRemittanceModeId(getRemittanceModeId());
			bankService.setRemittanceModeId(remittanceMode);

			DeliveryMode deliveryMode = new DeliveryMode();
			deliveryMode.setDeliveryModeId(getDeliveryId());
			bankService.setDeliveryModeId(deliveryMode);

			TransferMode transferMode = new TransferMode();
			transferMode.setTransferModeId(getTransferMode());
			bankService.setTransferMode(transferMode);

			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(getBankId());
			bankService.setBankId(bankMaster);

			bankService.setApplicationCountryId(sessionStateManage.getCountryId());

			// bankService.setChargeCurrency(currencyMasterCharge);
			// bankService.setComissionCurrency(currencyMastercomission);
			// bankService.setCostCurrency(currencyMasterCost);

			bankService.setBankCode(getBankCode());
			bankService.setFullname(getRuleName());
			bankService.setDeliveryDays(getDeliveryDays());
			bankService.setComissionAccNo(getCommisionAccount());
			bankService.setChargeAccNo(getChargeAccount());
			bankService.setCostDebitAccNo(getChargeDebitAccount());
			bankService.setCostCreditAccNo(getChargeCreditAccount());
			bankService.setManualFeedBack(getManualFeedback());
			bankService.setPinNo(getPinNoFormat());
			bankService.setPinPad(getPinNoPadding());
			bankService.setPinSpecialChar(getSpecialCharacterforPin());
			bankService.setRemitSwift(getRemittanceSwiftTrf());
			bankService.setIsActive(Constants.U);
			bankService.setProductGroup(getProductGroup());

			if(getTestKeyInFile()!=null){
				if (getTestKeyInFile().compareTo(Constants.ONE) == 0) {
					bankService.setTestKeyInFile(Constants.Yes);
				} else if (getTestKeyInFile().compareTo(new BigDecimal(Constants.TWO)) == 0) {
					bankService.setTestKeyInFile(Constants.No);
				}
			}
			if(getBankFilePrefix()!=null){
				/*if (getBankFilePrefix().compareTo(Constants.ONE) == 0) {
					 bankService.setBankFilePrefix(Constants.Yes);
				 } else if (getBankFilePrefix().compareTo(new BigDecimal(Constants.TWO)) == 0) {
					 bankService.setBankFilePrefix(Constants.No);
				 }*/
				bankService.setBankFilePrefix(getBankFilePrefix());

			}
			if (getBankServiceRulePkId() != null && getBankServiceRulePkId().intValue() != 0) {
				bankService.setModifiedBy(sessionStateManage.getUserName());
				bankService.setModifiedDate(new Date());
				bankService.setCreatedBy(getCreatedBy());
				bankService.setCreatedDate(getCretaedDate());
			} else {
				bankService.setCreatedBy(sessionStateManage.getUserName());
				bankService.setCreatedDate(new Date());
			}

			//iBankServiceRuleMasterService.saveBankRule(bankService);

			for (BankServiceRuleMasterDataTable bankServiceData : bankServiceDataTableList) {
				
				if(!bankServiceData.getIsActive().equalsIgnoreCase(Constants.Yes) && !bankServiceData.getIsActive().equalsIgnoreCase(Constants.D)){
					BankCharges bankCharges = new BankCharges();
					bankCharges.setBankChargeId(bankServiceData.getBankChargesId());
					bankCharges.setBankServiceRuleId(bankService);
					BizComponentData bizcomponent = new BizComponentData();
					bizcomponent.setComponentDataId(bankServiceData.getChargeFor());
					bankCharges.setChargeFor(bizcomponent);
					bankCharges.setChargeType(bankServiceData.getChargeType());
					bankCharges.setChargeAmount(bankServiceData.getChargeAmount());
					bankCharges.setFromAmount(bankServiceData.getFromAmount());
					bankCharges.setToAmount(bankServiceData.getToAmount());
					if(bankService.getIsActive().equalsIgnoreCase(Constants.U)){
						bankCharges.setIsActive(Constants.U);
						bankCharges.setApprovedBy(null);
						bankCharges.setApprovedDate(null);
					}else{
						bankCharges.setIsActive(bankServiceData.getIsActive());
						bankCharges.setApprovedBy(bankServiceData.getApprovedBy());
						bankCharges.setApprovedDate(bankServiceData.getApprovedDate());
					}
					bankCharges.setCurrencyCode(bankServiceData.getCurrencyCode());
					bankCharges.setCostCurrencyCode(bankServiceData.getCostCurrencyCode());
					bankCharges.setCostAmount(bankServiceData.getCostAmount());
					if (bankServiceData.getBankChargesId() != null) {
						bankCharges.setCreatedBy(bankServiceData.getCreatedBy());
						bankCharges.setCreatedDate(bankServiceData.getCreatedDate());
						bankCharges.setModifiedBy(sessionStateManage.getUserName());
						bankCharges.setModifiedDate(new Date());
					}else{
						bankCharges.setCreatedBy(sessionStateManage.getUserName());
						bankCharges.setCreatedDate(new Date());
					}

					lstBankCharges.add(bankCharges);
				}
			
			}

			saveAllMap.put("BankService", bankService);
			saveAllMap.put("BankCharges", lstBankCharges);

			iBankServiceRuleMasterService.saveAllDetails(saveAllMap);

			RequestContext.getCurrentInstance().execute("complete.show();");

		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::save");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	//
	public void cancel() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/bankservicerulemasterapproval.xhtml");
			setRenderDatatable(false);
			setBooBankChargesPanel(false);
			setRenderExitApproveal(true);
			clearAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exit() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/bankservicerulemaster.xhtml");
			clearAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exitpage() throws IOException {
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void getBankcode() {
		setRemittanceModeId(null);
		setDeliveryId(null);
		clearDeliverAll();
		for (BankMaster bankmaster : bankMasterList) {
			if (bankmaster.getBankId().toPlainString().equalsIgnoreCase(getBankId().toPlainString())) {
				setBankCode(bankmaster.getBankCode());
			}
		}
		fetchProductGroupBasedonBankCurrency();
	}

	public void checkCurrencyCodeExist() {
		List<CurrencyMaster> lstCurrency = iBankServiceRuleMasterService.checkCurrencyCodeExist(getCurrencyCode().toPlainString(), sessionStateManage.getCountryId());
		if (lstCurrency.size() == 0) {
			setCurrencyCode(null);
			RequestContext.getCurrentInstance().execute("currencyNotExist.show();");
		}
	}

	public void checkCostCurrencyCodeExist() {
		List<CurrencyMaster> lstCurrency = iBankServiceRuleMasterService.checkCurrencyCodeExist(getCostCurrencyCode().toPlainString(), sessionStateManage.getCountryId());
		if (lstCurrency.size() == 0) {
			setCostCurrencyCode(null);
			RequestContext.getCurrentInstance().execute("currencyNotExist.show();");
		}
	}

	private boolean isInRange(BigDecimal frmoAmt, BigDecimal toAmt, BigDecimal tabFromAmt, BigDecimal tabToAmt) {
		boolean returnValue = false;
		try {
			if (frmoAmt.compareTo(toAmt) < 0) {
				if (frmoAmt.compareTo(tabFromAmt) < 0) {
					if (toAmt.compareTo(tabFromAmt) < 0) {
						returnValue = true;
					} else {
						returnValue = false;
					}
				} else if (frmoAmt.compareTo(tabToAmt) > 0) {
					if (toAmt.compareTo(tabToAmt) > 0) {
						returnValue = true;
					} else {
						returnValue = false;
					}
				}
			} else {
				returnValue = false;
			}
		} catch (NumberFormatException e) {
			returnValue = false;
		}
		return returnValue;
	}

	List<CurrencyMaster> lstBankChrgesCurrencyCode = new ArrayList<CurrencyMaster>();
	List<CurrencyMaster> lstBankChrgesCostCurrencyCode = new ArrayList<CurrencyMaster>();
	List<BeneCountryService> lstBeneCountryServiceForRemittance = new ArrayList<BeneCountryService>();
	List<BeneCountryService> lstBeneCountryServiceForDeliver = new ArrayList<BeneCountryService>();
	List<RemittanceModeDescription> remittanceList = new ArrayList<RemittanceModeDescription>();
	List<DeliveryModeDesc> deliveryList = new ArrayList<DeliveryModeDesc>();
	List<TransferMode> transferModeList = new ArrayList<TransferMode>();
	private Map<BigDecimal,String> mapTransferModeCode = new HashMap<BigDecimal,String>();
	List<ProductGroup> productGroupList = new ArrayList<ProductGroup>();
	private Map<BigDecimal,String> mapProductGroupCode = new HashMap<BigDecimal,String>();
	private BigDecimal bankChrgeCurrencyCode;
	private BigDecimal bankChrgeCostCurrencyCode;
	private BigDecimal testKeyInFile;
	private String bankFilePrefix;
	private BigDecimal bankServiceRulePkId;
	private BigDecimal bankChargesPkId;

	public List<CurrencyMaster> getLstBankChrgesCurrencyCode() {
		return lstBankChrgesCurrencyCode;
	}
	public void setLstBankChrgesCurrencyCode(List<CurrencyMaster> lstBankChrgesCurrencyCode) {
		this.lstBankChrgesCurrencyCode = lstBankChrgesCurrencyCode;
	}

	public List<CurrencyMaster> getLstBankChrgesCostCurrencyCode() {
		return lstBankChrgesCostCurrencyCode;
	}
	public void setLstBankChrgesCostCurrencyCode(List<CurrencyMaster> lstBankChrgesCostCurrencyCode) {
		this.lstBankChrgesCostCurrencyCode = lstBankChrgesCostCurrencyCode;
	}

	public BigDecimal getBankChrgeCurrencyCode() {
		return bankChrgeCurrencyCode;
	}
	public void setBankChrgeCurrencyCode(BigDecimal bankChrgeCurrencyCode) {
		this.bankChrgeCurrencyCode = bankChrgeCurrencyCode;
	}

	public BigDecimal getBankChrgeCostCurrencyCode() {
		return bankChrgeCostCurrencyCode;
	}
	public void setBankChrgeCostCurrencyCode(BigDecimal bankChrgeCostCurrencyCode) {
		this.bankChrgeCostCurrencyCode = bankChrgeCostCurrencyCode;
	}

	public List<BeneCountryService> getLstBeneCountryServiceForRemittance() {
		return lstBeneCountryServiceForRemittance;
	}
	public void setLstBeneCountryServiceForRemittance(List<BeneCountryService> lstBeneCountryServiceForRemittance) {
		this.lstBeneCountryServiceForRemittance = lstBeneCountryServiceForRemittance;
	}

	public List<BeneCountryService> getLstBeneCountryServiceForDeliver() {
		return lstBeneCountryServiceForDeliver;
	}
	public void setLstBeneCountryServiceForDeliver(List<BeneCountryService> lstBeneCountryServiceForDeliver) {
		this.lstBeneCountryServiceForDeliver = lstBeneCountryServiceForDeliver;
	}

	public List<RemittanceModeDescription> getRemittanceList() {
		return remittanceList;
	}
	public void setRemittanceList(List<RemittanceModeDescription> remittanceList) {
		this.remittanceList = remittanceList;
	}

	public List<DeliveryModeDesc> getDeliveryList() {
		return deliveryList;
	}
	public void setDeliveryList(List<DeliveryModeDesc> deliveryList) {
		this.deliveryList = deliveryList;
	}

	public List<TransferMode> getTransferModeList() {
		return transferModeList;
	}
	public void setTransferModeList(List<TransferMode> transferModeList) {
		this.transferModeList = transferModeList;
	}

	public Map<BigDecimal, String> getMapTransferModeCode() {
		return mapTransferModeCode;
	}
	public void setMapTransferModeCode(Map<BigDecimal, String> mapTransferModeCode) {
		this.mapTransferModeCode = mapTransferModeCode;
	}
	
	public List<ProductGroup> getProductGroupList() {
		return productGroupList;
	}
	public void setProductGroupList(List<ProductGroup> productGroupList) {
		this.productGroupList = productGroupList;
	}
	
	public Map<BigDecimal, String> getMapProductGroupCode() {
		return mapProductGroupCode;
	}
	public void setMapProductGroupCode(Map<BigDecimal, String> mapProductGroupCode) {
		this.mapProductGroupCode = mapProductGroupCode;
	}
	
	public BigDecimal getTestKeyInFile() {
		return testKeyInFile;
	}
	public void setTestKeyInFile(BigDecimal testKeyInFile) {
		this.testKeyInFile = testKeyInFile;
	}

	public String getBankFilePrefix() {
		return bankFilePrefix;
	}
	public void setBankFilePrefix(String bankFilePrefix) {
		this.bankFilePrefix = bankFilePrefix;
	}

	public BigDecimal getBankServiceRulePkId() {
		return bankServiceRulePkId;
	}
	public void setBankServiceRulePkId(BigDecimal bankServiceRulePkId) {
		this.bankServiceRulePkId = bankServiceRulePkId;
	}

	public BigDecimal getBankChargesPkId() {
		return bankChargesPkId;
	}
	public void setBankChargesPkId(BigDecimal bankChargesPkId) {
		this.bankChargesPkId = bankChargesPkId;
	}

	public void getCurrencyList1() {
		List<CurrencyMaster> lstDbCurrencyMaster = generalService.getCurrencyList();
		for (CurrencyMaster currencyMaster : lstDbCurrencyMaster) {
			mapCurrencyList.put(currencyMaster.getCurrencyId(), currencyMaster.getCurrencyName());
			mapCurrencyCode.put(currencyMaster.getCurrencyId(), currencyMaster.getQuoteName());
		}
		setLstBankChrgesCurrencyCode(lstDbCurrencyMaster);
		setLstBankChrgesCostCurrencyCode(lstDbCurrencyMaster);
		List<TransferMode> lstTransferMode = iBankServiceRuleMasterService.getTransferMode();
		if(lstTransferMode.size() != 0){
			for (TransferMode transferMode : lstTransferMode) {
				mapTransferModeCode.put(transferMode.getTransferModeId(), transferMode.getTransferMode());
			}
		}
		setTransferModeList(lstTransferMode);
		
	}

	public void getRemittanceFromCountry() throws Exception {
		remittanceList = iBankServiceRuleMasterService.getRemittance(sessionStateManage.getCountryId(), getCountryId(), sessionStateManage.getLanguageId());
		for (RemittanceModeDescription remittanceMode : remittanceList) {
			mapRemittanceList.put(remittanceMode.getRemittanceModeMaster().getRemittanceModeId(), remittanceMode.getRemittanceDescription());
		}
		setRemittanceList(remittanceList);
	}

	public void getDeliveryFromCountryRemittance() {
		deliveryList = iBankServiceRuleMasterService.getDeliverFromRemittance(sessionStateManage.getCountryId(), getCountryId(), getRemittanceModeId(), sessionStateManage.getLanguageId());
		for (DeliveryModeDesc deliveryMode : deliveryList) {
			mapDeliveryList.put(deliveryMode.getDeliveryMode().getDeliveryModeId(), deliveryMode.getEnglishDeliveryName());
		}
		setDeliveryList(deliveryList);
	}

	public void popDeliverLst() {
		clearDeliverAll();
		setDeliveryId(null);
		setDeliveryList(null);
		getDeliveryFromCountryRemittance();
	}

	public void clearDeliverChanges() {
		/*
		 * setBankServiceRuleId(null); setBankId(null);
		 * setCountryId(null); setCurrencyId(null);
		 * setRemittanceModeId(null); setDeliveryId(null);
		 */
		setDeliveryDays(null);
		setRuleName(null);
		setTransferMode(null);
		setChargeCurrency(null);
		setChargeAccount(null);
		setCommisionCurrencyId(null);
		setCommisionAccount(null);
		setChargeCreditAccount(null);
		setChargeDebitAccount(null);
		setManualFeedback(null);
		setPinNoFormat(null);
		setPinNoPadding(null);
		setSpecialCharacterforPin(null);
		setRemittanceSwiftTrf(null);
		setCreatedBy(null);
		setCretaedDate(null);
		setTestKeyInFile(null);
		setBankFilePrefix(null);
	}

	public void clearDeliverAll() {
		try {
			clearBankCharges();
			clearDeliverChanges();
			bankServiceDataTableList.clear();
			setRenderDatatable(false);
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void fetchRecord() {
		try {
			String isActive = null;
			List<BankServiceRule> lstBankServiceRule = iBankServiceRuleMasterService.getBankServiceRule(getCountryId(), getCurrencyId(), getBankId(), getRemittanceModeId(), getDeliveryId(),isActive);
			if (lstBankServiceRule != null && lstBankServiceRule.size() > 0) {
				// List<BankCharges> lstBankCharges =
				// null;
				BigDecimal bankServiceRuleId = null;
				for (BankServiceRule bankServiceRule : lstBankServiceRule) {
					bankServiceRuleId = bankServiceRule.getBankServiceRuleId();
					setBankServiceRulePkId(bankServiceRuleId);
					setDeliveryDays(bankServiceRule.getDeliveryDays());
					setRuleName(bankServiceRule.getFullname());
					setTransferMode(bankServiceRule.getTransferMode().getTransferModeId());
					setChargeAccount(bankServiceRule.getChargeAccNo());
					setCommisionAccount(bankServiceRule.getComissionAccNo());
					setChargeCreditAccount(bankServiceRule.getCostCreditAccNo());
					setChargeDebitAccount(bankServiceRule.getCostDebitAccNo());
					setManualFeedback(bankServiceRule.getManualFeedBack());
					setPinNoFormat(bankServiceRule.getPinNo());
					setPinNoPadding(bankServiceRule.getPinPad());
					setSpecialCharacterforPin(bankServiceRule.getPinSpecialChar());
					setRemittanceSwiftTrf(bankServiceRule.getRemitSwift());
					setIsActive(bankServiceRule.getIsActive());
					setProductGroup(bankServiceRule.getProductGroup());
					if (bankServiceRule.getTestKeyInFile() != null && bankServiceRule.getTestKeyInFile().equalsIgnoreCase(Constants.Yes)) {
						setTestKeyInFile(Constants.ONE);
					} else if (bankServiceRule.getTestKeyInFile() != null && bankServiceRule.getTestKeyInFile().equalsIgnoreCase(Constants.No)) {
						setTestKeyInFile(new BigDecimal(Constants.TWO));
					}
					setBankFilePrefix(bankServiceRule.getBankFilePrefix());
					/*if (bankServiceRule.getBankFilePrefix() != null && bankServiceRule.getBankFilePrefix().equalsIgnoreCase(Constants.Yes)) {
						 setBankFilePrefix(Constants.ONE);
					 } else if (bankServiceRule.getBankFilePrefix() != null && bankServiceRule.getBankFilePrefix().equalsIgnoreCase(Constants.No)) {
						 setBankFilePrefix(new BigDecimal(Constants.TWO));
					 }*/
					setCreatedBy(bankServiceRule.getCreatedBy());
					setCretaedDate(bankServiceRule.getCreatedDate());
				}
				if (bankServiceRuleId != null) {
					// bankServiceDataTableList.clear();
					addBankChargesRecordsToDtTable(bankServiceRuleId);
				}
			} else {
				clearDeliverAll();
				setRenderDatatable(false);
			}
			
			fetchProductGroupBasedonBankCurrency();
			
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::fetchRecord");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	private void addBankChargesRecordsToDtTable(BigDecimal bankServiceRulePkId) {
		try {
			bankServiceDataTableList.clear();
			getCutomerTypeMap();
			List<BankCharges> lstBankCharges = iBankServiceRuleMasterService.getBankCharges(bankServiceRulePkId);
			if (lstBankCharges != null && lstBankCharges.size() > 0) {
				for (BankCharges bankCharges : lstBankCharges) {
					BigDecimal chargesFor = bankCharges.getChargeFor().getComponentDataId();// getFsBizComponentDataDescs().get(0).getFsBizComponentData().getComponentDataId();
					setBankChargesPkId(bankCharges.getBankChargeId());
					BankServiceRuleMasterDataTable bankServiceData = new BankServiceRuleMasterDataTable();
					bankServiceData.setBankChargesId(bankCharges.getBankChargeId());

					if (chargesFor.intValue() == Integer.parseInt(Constants.BOTHFOR_BANKSERVICE_COMPONENT_ID)) {
						bankServiceData.setChargeFordis(Constants.BOTH);
						bankServiceData.setChargeFor(new BigDecimal(Constants.BOTHFOR_BANKSERVICE_COMPONENT_ID));
					} else {
						bankServiceData.setChargeFordis(customerTypeMap.get(bankCharges.getChargeFor().getComponentDataId()));
						bankServiceData.setChargeFor(bankCharges.getChargeFor().getComponentDataId());
					}
					if (bankCharges.getBankChargeId() != null) {
						setDynamicLabelForActivateDeactivate(Constants.DELETE);
						setRenderEditButton(true);
						setCheckSave(false);
						if (bankCharges.getIsActive().equalsIgnoreCase(Constants.Yes)) {
							setDynamicLabelForActivateDeactivate(Constants.DELETE);
							setRenderEditButton(true);
							setCheckSave(false);
						} else if (bankCharges.getIsActive().equalsIgnoreCase(Constants.D)) {
							setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
							setRenderEditButton(false);
							setCheckSave(false);
						} else if (bankCharges.getIsActive().equalsIgnoreCase(Constants.U)) {
							setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
							setRenderEditButton(true);
							setCheckSave(false);
						}
						bankServiceData.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
						bankServiceData.setRenderEditButton(getRenderEditButton());
						bankServiceData.setIsActive(bankCharges.getIsActive());
						bankServiceData.setCheckSave(getCheckSave());
						bankServiceData.setCreatedBy(bankCharges.getCreatedBy());
						bankServiceData.setCreatedDate(bankCharges.getCreatedDate());
					}
					if (bankCharges.getChargeType().equalsIgnoreCase(Constants.C)) {
						bankServiceData.setChargeTypedis(Constants.COMISSION);
						bankServiceData.setChargeType(bankCharges.getChargeType());
					} else {
						bankServiceData.setChargeTypedis(Constants.OVERSEASE_CHARGE);
						bankServiceData.setChargeType(bankCharges.getChargeType());
					}
					bankServiceData.setFromAmount(bankCharges.getFromAmount());
					bankServiceData.setToAmount(bankCharges.getToAmount());
					bankServiceData.setChargeAmount(bankCharges.getChargeAmount());
					bankServiceData.setCurrencyCode(bankCharges.getCurrencyCode());
					bankServiceData.setCurrencyCodeName(mapCurrencyList.get(bankCharges.getCurrencyCode()));
					bankServiceData.setCostCurrencyCode(bankCharges.getCostCurrencyCode());
					bankServiceData.setCostCurrencyCodeName(mapCurrencyList.get(bankCharges.getCostCurrencyCode()));
					bankServiceData.setCostAmount(bankCharges.getCostAmount());
					bankServiceData.setApprovedBy(bankCharges.getApprovedBy());
					bankServiceData.setApprovedDate(bankCharges.getApprovedDate());
					// bankServiceData.setIsActive(bankCharges.getIsActive());
					// bankServiceData.setCreatedBy(bankCharges.getCreatedBy());
					// bankServiceData.setCreatedDate(bankCharges.getCreatedDate());
					bankServiceDataTableList.add(bankServiceData);
					setRenderDatatable(true);
					clearBankCharges();
				}
			}
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void removeRecord(BankServiceRuleMasterDataTable bankServiceData) {
		try {
			if (bankServiceData.getBankChargesId() != null) {
				BankCharges bankCharges = new BankCharges();
				bankCharges.setBankChargeId(bankServiceData.getBankChargesId());
				BankServiceRule bankService = new BankServiceRule();
				bankService.setBankServiceRuleId(getBankServiceRulePkId());
				bankCharges.setBankServiceRuleId(bankService);
				BizComponentData bizcomponent = new BizComponentData();
				bizcomponent.setComponentDataId(bankServiceData.getChargeFor());
				bankCharges.setChargeFor(bizcomponent);
				bankCharges.setChargeType(bankServiceData.getChargeType());
				bankCharges.setChargeAmount(bankServiceData.getChargeAmount());
				bankCharges.setFromAmount(bankServiceData.getFromAmount());
				bankCharges.setToAmount(bankServiceData.getToAmount());
				// bankCharges.setIsActive("N");//Hard
				// Code
				bankCharges.setCurrencyCode(bankServiceData.getCurrencyCode());
				bankCharges.setCostCurrencyCode(bankServiceData.getCostCurrencyCode());
				bankCharges.setCostAmount(bankServiceData.getCostAmount());
				if (bankServiceData.getBankChargesId() != null && bankServiceData.getBankChargesId().intValue() != 0) {
					bankCharges.setModifiedBy(sessionStateManage.getUserName());
					bankCharges.setModifiedDate(new Date());
					bankCharges.setCreatedBy(bankServiceData.getCreatedBy());
					bankCharges.setCreatedDate(bankServiceData.getCreatedDate());
				} else {
					bankCharges.setCreatedBy(sessionStateManage.getUserName());
					bankCharges.setCreatedDate(new Date());
				}
				if (bankServiceData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
					bankCharges.setIsActive(Constants.U);
				} else if (bankServiceData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
					bankCharges.setIsActive(Constants.D);
					bankCharges.setRemarks(bankServiceData.getRemarks());
				}
				iBankServiceRuleMasterService.saveBankCharges(bankCharges);
				bankServiceDataTableList.clear();
				addBankChargesRecordsToDtTable(getBankServiceRulePkId());
				if (newBankServiceDataTableList.size() != 0) {
					bankServiceDataTableList.addAll(newBankServiceDataTableList);
				}
				if (bankServiceDataTableList.size() > 0) {
					setRenderDatatable(true);
				} else {
					setRenderDatatable(false);
				}
			} else {
				bankServiceDataTableList.remove(bankServiceData);
				newBankServiceDataTableList.remove(bankServiceData);
				if (bankServiceDataTableList.size() > 0) {
					setRenderDatatable(true);
				} else {
					setRenderDatatable(false);
				}
			}
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::removeRecord");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void editRecord(BankServiceRuleMasterDataTable bankServiceData) {
		try {
			getCutomerTypeMap();
			setBankChargesId(bankServiceData.getBankChargesId());
			setChargeFor(bankServiceData.getChargeFor());
			setChargeType(bankServiceData.getChargeType());
			setChargeAmount(bankServiceData.getChargeAmount());
			setFromAmount(bankServiceData.getFromAmount());
			setToAmount(bankServiceData.getToAmount());
			setCurrencyCode(bankServiceData.getCurrencyCode());
			setCostCurrencyCode(bankServiceData.getCostCurrencyCode());
			setBankChrgeCurrencyCode(bankServiceData.getCurrencyCode());
			setBankChrgeCostCurrencyCode(bankServiceData.getCostCurrencyCode());
			setCostAmount(bankServiceData.getCostAmount());
			bankServiceDataTableList.remove(bankServiceData);
			if (bankServiceDataTableList.size() > 0) {
				setRenderDatatable(true);
			} else {
				setRenderDatatable(false);
			}
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	private boolean booChildPanel;
	private boolean booDataTablePanel;
	private boolean booBankChargesPanel = false;
	private boolean booRenderedButtonPanel = false;
	private boolean renderDatatableApproveal;

	public boolean isBooChildPanel() {
		return booChildPanel;
	}

	public void setBooChildPanel(boolean booChildPanel) {
		this.booChildPanel = booChildPanel;
	}

	public boolean isBooDataTablePanel() {
		return booDataTablePanel;
	}

	public void setBooDataTablePanel(boolean booDataTablePanel) {
		this.booDataTablePanel = booDataTablePanel;
	}

	public boolean isBooBankChargesPanel() {
		return booBankChargesPanel;
	}

	public void setBooBankChargesPanel(boolean booBankChargesPanel) {
		this.booBankChargesPanel = booBankChargesPanel;
	}

	public boolean isBooRenderedButtonPanel() {
		return booRenderedButtonPanel;
	}

	public void setBooRenderedButtonPanel(boolean booRenderedButtonPanel) {
		this.booRenderedButtonPanel = booRenderedButtonPanel;
	}

	public boolean isRenderDatatableApproveal() {
		return renderDatatableApproveal;
	}

	public void setRenderDatatableApproveal(boolean renderDatatableApproveal) {
		this.renderDatatableApproveal = renderDatatableApproveal;
	}

	public void bankServiceRuleApproval() {
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankservicerulemasterapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/bankservicerulemasterapproval.xhtml");
			clearAll();
			setCostAmount(BigDecimal.ZERO);
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		setBooChildPanel(false);
		// setRenderDatatable(false);
		setBooBankChargesPanel(false);
		setBooRenderedButtonPanel(false);
		setRenderDatatableApproveal(false);
		setRenderExitApproveal(true);
	}

	List<BankServiceRuleMasterDataTable> bankServiceDataTableListForApprove = new ArrayList<BankServiceRuleMasterDataTable>();

	public List<BankServiceRuleMasterDataTable> getBankServiceDataTableListForApprove() {
		return bankServiceDataTableListForApprove;
	}

	public void setBankServiceDataTableListForApprove(List<BankServiceRuleMasterDataTable> bankServiceDataTableListForApprove) {
		this.bankServiceDataTableListForApprove = bankServiceDataTableListForApprove;
	}

	public void fetchApprovalRecord() {
		try {
			bankServiceDataTableListForApprove.clear();
			getCutomerTypeMap();
			String isActive = null;
			List<BankServiceRule> lstBankServiceRule = iBankServiceRuleMasterService.getBankServiceRule(getCountryId(), getCurrencyId(), getBankId(), getRemittanceModeId(), getDeliveryId(),isActive);
			List<TransferMode> lstTransferMode = iBankServiceRuleMasterService.getTransferMode();
			if(lstTransferMode.size() != 0){
				for (TransferMode transferMode : lstTransferMode) {
					mapTransferModeCode.put(transferMode.getTransferModeId(), transferMode.getTransferMode());
				}
			}
			setTransferModeList(lstTransferMode);
			if (lstBankServiceRule != null && lstBankServiceRule.size() > 0) {
				List<BankCharges> lstBankCharges = null;
				BigDecimal bankServiceRuleId = null;
				for (BankServiceRule bankServiceRule : lstBankServiceRule) {
					bankServiceRuleId = bankServiceRule.getBankServiceRuleId();
					lstBankCharges = iBankServiceRuleMasterService.getBankChargesApprove(bankServiceRuleId);
					if ((lstBankCharges == null || lstBankCharges.size() == 0) && bankServiceRule.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						RequestContext.getCurrentInstance().execute("NoDataFound.show();");
						setRenderExitApproveal(true);
						return;
					}
					setBankServiceRulePkId(bankServiceRuleId);
					setDeliveryDays(bankServiceRule.getDeliveryDays());
					setRuleName(bankServiceRule.getFullname());
					setTransferMode(bankServiceRule.getTransferMode().getTransferModeId());
					setChargeAccount(bankServiceRule.getChargeAccNo());
					setCommisionAccount(bankServiceRule.getComissionAccNo());
					setChargeCreditAccount(bankServiceRule.getCostCreditAccNo());
					setChargeDebitAccount(bankServiceRule.getCostDebitAccNo());
					setManualFeedback(bankServiceRule.getManualFeedBack());
					setPinNoFormat(bankServiceRule.getPinNo());
					setPinNoPadding(bankServiceRule.getPinPad());
					setSpecialCharacterforPin(bankServiceRule.getPinSpecialChar());
					setRemittanceSwiftTrf(bankServiceRule.getRemitSwift());
					setIsActive(bankServiceRule.getIsActive());
					setProductGroup(bankServiceRule.getProductGroup());
					setCreatedBy(bankServiceRule.getCreatedBy());
					setCretaedDate(bankServiceRule.getCreatedDate());
					setModifiedBy(bankServiceRule.getModifiedBy());
					setModifiedDate(bankServiceRule.getModifiedDate());
					if (bankServiceRule.getTestKeyInFile() != null && bankServiceRule.getTestKeyInFile().equalsIgnoreCase(Constants.Yes)) {
						setTestKeyInFile(Constants.ONE);
					} else if (bankServiceRule.getTestKeyInFile() != null && bankServiceRule.getTestKeyInFile().equalsIgnoreCase(Constants.No)) {
						setTestKeyInFile(new BigDecimal(Constants.TWO));
					}
					/* if (bankServiceRule.getBankFilePrefix() != null && bankServiceRule.getBankFilePrefix().equalsIgnoreCase(Constants.Yes)) {
						 setBankFilePrefix(Constants.ONE);
					 } else if (bankServiceRule.getBankFilePrefix() != null && bankServiceRule.getBankFilePrefix().equalsIgnoreCase(Constants.No)) {
						 setBankFilePrefix(new BigDecimal(Constants.TWO));
					 }*/
					setBankFilePrefix(bankServiceRule.getBankFilePrefix());
				}
				if (bankServiceRuleId != null) {
					if (lstBankCharges != null && lstBankCharges.size() > 0) {
						for (BankCharges bankCharges : lstBankCharges) {
							if (bankCharges.getApprovedBy() == null) {
								BigDecimal chargesFor = bankCharges.getChargeFor().getComponentDataId();// getFsBizComponentDataDescs().get(0).getFsBizComponentData().getComponentDataId();
								setBankChargesPkId(bankCharges.getBankChargeId());
								BankServiceRuleMasterDataTable bankServiceData = new BankServiceRuleMasterDataTable();
								bankServiceData.setBankChargesId(bankCharges.getBankChargeId());

								if (chargesFor.intValue() == Integer.parseInt(Constants.BOTHFOR_BANKSERVICE_COMPONENT_ID)) {
									bankServiceData.setChargeFordis(Constants.BOTH);
									bankServiceData.setChargeFor(new BigDecimal(Constants.BOTHFOR_BANKSERVICE_COMPONENT_ID));
								} else {
									bankServiceData.setChargeFordis(customerTypeMap.get(bankCharges.getChargeFor().getComponentDataId()));
									bankServiceData.setChargeFor(bankCharges.getChargeFor().getComponentDataId());
								}
								if (bankCharges.getChargeType().equalsIgnoreCase(Constants.C)) {
									bankServiceData.setChargeTypedis(Constants.COMISSION);
									bankServiceData.setChargeType(bankCharges.getChargeType());
								} else {
									bankServiceData.setChargeTypedis(Constants.OVERSEASE_CHARGE);
									bankServiceData.setChargeType(bankCharges.getChargeType());
								}
								bankServiceData.setFromAmount(bankCharges.getFromAmount());
								bankServiceData.setToAmount(bankCharges.getToAmount());
								bankServiceData.setChargeAmount(bankCharges.getChargeAmount());
								bankServiceData.setCurrencyCode(bankCharges.getCurrencyCode());
								bankServiceData.setCurrencyCodeName(mapCurrencyList.get(bankCharges.getCurrencyCode()));
								bankServiceData.setCostCurrencyCode(bankCharges.getCostCurrencyCode());
								bankServiceData.setCostCurrencyCodeName(mapCurrencyList.get(bankCharges.getCostCurrencyCode()));
								bankServiceData.setCostAmount(bankCharges.getCostAmount());
								bankServiceData.setIsActive(bankCharges.getIsActive());
								bankServiceData.setCreatedByBankCharges(bankCharges.getCreatedBy());
								bankServiceData.setCreatedDateBankCharges(bankCharges.getCreatedDate());
								bankServiceData.setModifiedByBankCharges(bankCharges.getModifiedBy());
								bankServiceData.setModifiedDateBankCharges(bankCharges.getModifiedDate());
								bankServiceDataTableListForApprove.add(bankServiceData);
								setRenderDatatableApproveal(true);
								clearBankCharges();
							}
						}
					}
				}
				setRenderExitApproveal(false);
				fetchProductGroupBasedonBankCurrency();
			} else {
				setRenderDatatableApproveal(false);
				clearApprove();
				setRenderExitApproveal(true);
				RequestContext.getCurrentInstance().execute("NoDataFound.show();");
			}
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::fetchApprovalRecord");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void editRecordForApproval(BankServiceRuleMasterDataTable bankServiceData) throws Exception {
		getCurrencyList1();
		setChargeFor(bankServiceData.getChargeFor());
		setChargeType(bankServiceData.getChargeType());
		setChargeAmount(bankServiceData.getChargeAmount());
		setFromAmount(bankServiceData.getFromAmount());
		setToAmount(bankServiceData.getToAmount());
		// getChargeType();
		setCurrencyCode(bankServiceData.getCurrencyCode());
		// getCurrencyCode();
		setCostCurrencyCode(bankServiceData.getCostCurrencyCode());
		setBankChrgeCurrencyCode(bankServiceData.getCurrencyCode());
		setBankChrgeCostCurrencyCode(bankServiceData.getCostCurrencyCode());
		setCostAmount(bankServiceData.getCostAmount());
		setBooBankChargesPanel(true);
		setBankChargesId(bankServiceData.getBankChargesId());
		// setBankChargesPkId(bankServiceData.getBankChargesId());
		setIsActive(bankServiceData.getIsActive());
		setCreatedByBankCharges(bankServiceData.getCreatedByBankCharges());
		setCreatedDateBankCharges(bankServiceData.getCreatedDateBankCharges());
		// setBooRenderedButtonPanel(true);
		setCreatedBy(bankServiceData.getCreatedBy());
		setCretaedDate(bankServiceData.getCreatedDate());
		bankServiceDataTableList.remove(bankServiceData);
	}

	public void approve() {
		try {
			BankServiceRule bankService = new BankServiceRule();
			bankService.setBankServiceRuleId(getBankServiceRulePkId());
			CountryMaster countryMaster = new CountryMaster();
			CurrencyMaster currencyMaster = new CurrencyMaster();
			/*
			 * CurrencyMaster currencyMastercomission = new
			 * CurrencyMaster(); CurrencyMaster
			 * currencyMasterCost = new CurrencyMaster();
			 * CurrencyMaster currencyMasterCharge = new
			 * CurrencyMaster();
			 */
			RemittanceModeMaster remittanceMode = new RemittanceModeMaster();
			DeliveryMode deliveryMode = new DeliveryMode();
			TransferMode transferMode = new TransferMode();
			BankMaster bankMaster = new BankMaster();
			countryMaster.setCountryId(getCountryId());
			currencyMaster.setCurrencyId(getCurrencyId());
			/*
			 * currencyMastercomission.setCurrencyId(
			 * getCommisionCurrencyId());
			 * currencyMasterCost.setCurrencyId
			 * (getChargeCurrency());
			 * currencyMasterCharge.setCurrencyId
			 * (getCostCurrencyId());
			 */
			remittanceMode.setRemittanceModeId(getRemittanceModeId());
			deliveryMode.setDeliveryModeId(getDeliveryId());
			bankMaster.setBankId(getBankId());
			transferMode.setTransferModeId(getTransferMode());
			bankService.setTransferMode(transferMode);
			bankService.setCountryId(countryMaster);
			bankService.setCurrencyId(currencyMaster);
			// bankService.setChargeCurrency(currencyMasterCharge);
			// bankService.setComissionCurrency(currencyMastercomission);
			// bankService.setCostCurrency(currencyMasterCost);
			bankService.setRemittanceModeId(remittanceMode);
			bankService.setDeliveryModeId(deliveryMode);
			bankService.setBankId(bankMaster);
			bankService.setBankCode(getBankCode());
			bankService.setFullname(getRuleName());
			bankService.setDeliveryDays(getDeliveryDays());
			bankService.setComissionAccNo(getCommisionAccount());
			bankService.setChargeAccNo(getChargeAccount());
			bankService.setCostDebitAccNo(getChargeDebitAccount());
			bankService.setCostCreditAccNo(getChargeCreditAccount());
			bankService.setManualFeedBack(getManualFeedback());
			bankService.setPinNo(getPinNoFormat());
			bankService.setPinPad(getPinNoPadding());
			bankService.setPinSpecialChar(getSpecialCharacterforPin());
			bankService.setRemitSwift(getRemittanceSwiftTrf());
			bankService.setIsActive(getIsActive());
			bankService.setProductGroup(getProductGroup());
			if (getTestKeyInFile() != null && getTestKeyInFile().compareTo(Constants.ONE) == 0) {
				bankService.setTestKeyInFile(Constants.Yes);
			} else if (getTestKeyInFile() != null && getTestKeyInFile().compareTo(new BigDecimal(Constants.TWO)) == 0) {
				bankService.setTestKeyInFile(Constants.No);
			}
			/*if (getBankFilePrefix() != null && getBankFilePrefix().compareTo(Constants.ONE) == 0) {
				 bankService.setBankFilePrefix(Constants.Yes);
			 } else if (getBankFilePrefix() != null && getBankFilePrefix().compareTo(new BigDecimal(Constants.TWO)) == 0) {
				 bankService.setBankFilePrefix(Constants.No);
			 }*/
			bankService.setBankFilePrefix(getBankFilePrefix());
			bankService.setCreatedBy(getCreatedBy());
			bankService.setCreatedDate(getCretaedDate());
			bankService.setApprovedBy(sessionStateManage.getUserName());
			bankService.setApprovedDate(new Date());
			iBankServiceRuleMasterService.saveBankRule(bankService);
			BankCharges bankCharges = new BankCharges();
			bankCharges.setBankChargeId(getBankChargesId());
			bankCharges.setBankServiceRuleId(bankService);
			BizComponentData bizcomponent = new BizComponentData();
			bizcomponent.setComponentDataId(getChargeFor());
			bankCharges.setChargeFor(bizcomponent);
			bankCharges.setChargeType(getChargeType());
			bankCharges.setChargeAmount(getChargeAmount());
			bankCharges.setFromAmount(getFromAmount());
			bankCharges.setToAmount(getToAmount());
			bankCharges.setIsActive(Constants.Yes);
			bankCharges.setCurrencyCode(getCurrencyCode());
			bankCharges.setCostCurrencyCode(getCostCurrencyCode());
			bankCharges.setCostAmount(getCostAmount());
			bankCharges.setModifiedBy(sessionStateManage.getUserName());
			bankCharges.setModifiedDate(new Date());
			bankCharges.setCreatedBy(getCreatedBy());
			bankCharges.setCreatedDate(new Date());
			bankCharges.setApprovedBy(sessionStateManage.getUserName());
			bankCharges.setApprovedDate(new Date());
			if (CommonUtil.validateApprovedBy(getCreatedBy(), sessionStateManage.getUserName()) != true) {
				iBankServiceRuleMasterService.saveBankCharges(bankCharges);
				RequestContext.getCurrentInstance().execute("approve.show();");
				clearBankCharges();
				// clearBankService();
			} else {
				RequestContext.getCurrentInstance().execute("notValidUser.show();");
			}
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::approve");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	// rahamath code changes CR-05/03/2015
	private Boolean disableSubmitButton = false;
	private Boolean renderEditButton = false;
	private Boolean checkSave;
	private String dynamicLabelForActivateDeactivate;

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getCheckSave() {
		return checkSave;
	}

	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
	}

	public Boolean getRenderEditButton() {
		return renderEditButton;
	}

	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}

	public Boolean getDisableSubmitButton() {
		return disableSubmitButton;
	}

	public void setDisableSubmitButton(Boolean disableSubmitButton) {
		this.disableSubmitButton = disableSubmitButton;
	}

	public void hideSubmitButton() {/*
	 * 
	 * if(getLocalServiceDescription()!=null
	 * || getServiceDescription()!=null){
	 * setDisableSubmitButton(true);
	 * }else{
	 * setDisableSubmitButton(false); }
	 */
	}

	// rahamath code changes CR-05/03/2015
	// Dailog Box code
	private String remarks;
	private Date activateDate;
	private String activateBy;
	private boolean renderExitApproveal;

	public Date getActivateDate() {
		return activateDate;
	}

	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
	}

	public String getActivateBy() {
		return activateBy;
	}

	public void setActivateBy(String activateBy) {
		this.activateBy = activateBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isRenderExitApproveal() {
		return renderExitApproveal;
	}

	public void setRenderExitApproveal(boolean renderExitApproveal) {
		this.renderExitApproveal = renderExitApproveal;
	}
	
	public void deleteReocrd(){
		try{
			if(bankServiceDataTableList.size()>0){
					for (BankServiceRuleMasterDataTable  bankService : bankServiceDataTableList) {
						if(bankService.getRemarkCheck()!=null){
						if(bankService.getRemarkCheck().equals(true)){ 
							iBankServiceRuleMasterService.deleteRecord(bankService.getBankChargesId());
							saveRecordsToBankChargesMasLog(bankService);
							bankServiceDataTableList.remove(bankService);
						}
					}
				 }
					
					if(bankServiceDataTableList!=null && bankServiceDataTableList.size()>0){
						setRenderDatatable(true);
					}else{
						setRenderDatatable(false);
					}
			}
			  }catch(Exception exception){
				    log.info("confirmPermanentDelete():::::::::::::::::::::::"+exception.getMessage());
				    setErrorMessage(exception.getMessage());
				    RequestContext.getCurrentInstance().execute("error.show();");
				    return;
			  }
	}

	public void checkStatusType(BankServiceRuleMasterDataTable bankServiceRuleMasterDataTable) throws IOException {
		setDeletedRec(null);
		if (bankServiceRuleMasterDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)) {
			bankServiceRuleMasterDataTable.setRemarkCheck(true);
			/*setActivateDate(bankServiceRuleMasterDataTable.getApprovedDate());
			setActivateBy(bankServiceRuleMasterDataTable.getApprovedBy());
			setDeletedRec(bankServiceRuleMasterDataTable);
			RequestContext.getCurrentInstance().execute("remarks.show();");*/
			RequestContext.getCurrentInstance().execute("deleteRecord.show();");			
			
		} else {
			setDeletedRec(null);
			removeRecord(bankServiceRuleMasterDataTable);
		}
	}

	public void remarkSelectedRecord() throws IOException {
		//for (BankServiceRuleMasterDataTable bankServiceRuleMasterDataTable : bankServiceDataTableList) {
		if(getDeletedRec() != null){
			BankServiceRuleMasterDataTable bankServiceRuleMasterDataTable = getDeletedRec();
			if (bankServiceRuleMasterDataTable.getRemarkCheck() != null) {
				if (bankServiceRuleMasterDataTable.getRemarkCheck().equals(true)) {
					bankServiceRuleMasterDataTable.setRemarks(getRemarks());
					removeRecord(bankServiceRuleMasterDataTable);
					setRemarks(null);
				}
			}
		}else{
			
		}
	}

	public Map<String, String> validateWithAllRecords(BigDecimal bankServiceRuleId) {
		boolean rangeCheck = false;
		Map<String, String> mapReturn = new HashMap<String, String>();
		List<BankCharges> lstBankCharges = iBankServiceRuleMasterService.getBankChargesForAllRecords(bankServiceRuleId);
		if (lstBankCharges.size() != 0) {
			for (BankCharges bankCharges : lstBankCharges) {
				if (getChargeFor().compareTo(new BigDecimal(Constants.BOTHFOR_BANKSERVICE_COMPONENT_ID)) == 0) {
					if (bankCharges.getChargeType().equals(getChargeType())) {
						boolean isRangeCheck = isInRange(getFromAmount(), getToAmount(), bankCharges.getFromAmount(), bankCharges.getToAmount());
						if (isRangeCheck) {
							rangeCheck = true;
						} else {
							rangeCheck = false;
							mapReturn.put(Constants.ACTIVE, bankCharges.getIsActive());
							break;
						}
					} else {
						rangeCheck = true;
					}
				} else {
					if (bankCharges.getChargeFor().getComponentDataId().compareTo(new BigDecimal(Constants.BOTHFOR_BANKSERVICE_COMPONENT_ID)) == 0) {
						if (bankCharges.getChargeType().equals(getChargeType())) {
							boolean isRangeCheck = isInRange(getFromAmount(), getToAmount(), bankCharges.getFromAmount(), bankCharges.getToAmount());
							if (isRangeCheck) {
								rangeCheck = true;
							} else {
								rangeCheck = false;
								mapReturn.put(Constants.ACTIVE, bankCharges.getIsActive());
								break;
							}
						}
					} else {
						if (bankCharges.getChargeFor().getComponentDataId().compareTo(getChargeFor()) == 0) {
							if (bankCharges.getChargeType().equals(getChargeType())) {
								boolean isRangeCheck = isInRange(getFromAmount(), getToAmount(), bankCharges.getFromAmount(), bankCharges.getToAmount());
								if (isRangeCheck) {
									rangeCheck = true;
								} else {
									rangeCheck = false;
									mapReturn.put(Constants.ACTIVE, bankCharges.getIsActive());
									break;
								}
							} else {
								rangeCheck = true;
							}
						} else {
							rangeCheck = true;
						}
					}
				}
			}
			if (rangeCheck) {
				mapReturn.put(Constants.VALIDATE, Constants.YES);
			} else {
				mapReturn.put(Constants.VALIDATE, Constants.NO);
			}
		} else {
			mapReturn.put(Constants.VALIDATE, Constants.YES);
		}
		return mapReturn;
	}

	public void approveAll() {
		try {
			if(getBankServiceRulePkId() != null){
				if (!(getModifiedBy() == null ? getCreatedBy() : getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())) {
					List<BigDecimal> lstBnkCrgsApprove = new ArrayList<BigDecimal>();
					for (BankServiceRuleMasterDataTable bankServiceRuleMasterDataTable : bankServiceDataTableListForApprove) {
						lstBnkCrgsApprove.add(bankServiceRuleMasterDataTable.getBankChargesId());
					}
					if (lstBnkCrgsApprove.size() > 0 || getBankServiceRulePkId() != null ) {
						iBankServiceRuleMasterService.saveApproveList(lstBnkCrgsApprove, sessionStateManage.getUserName(),getBankServiceRulePkId());
						RequestContext.getCurrentInstance().execute("approveAll.show();");
					}else{
						setErrorMessage("Data Missing");
						RequestContext.getCurrentInstance().execute("error.show();");
					}
				} else {
					RequestContext.getCurrentInstance().execute("notValidUser.show();");
				}
			}else{
				RequestContext.getCurrentInstance().execute("NoDataFound.show();");
			}
			
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::approveAll");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void clearApprove() {
		clearBankServiceApprove();
		clearBankChargesApprove();
		bankServiceDataTableList.clear();
		newBankServiceDataTableList.clear();
		setRenderDatatable(false);
	}

	public void clearBankServiceApprove() {
		setBankServiceRulePkId(null);
		setDeliveryDays(null);
		setRuleName(null);
		setTransferMode(null);
		setChargeCurrency(null);
		setChargeAccount(null);
		setCommisionCurrencyId(null);
		setCommisionAccount(null);
		setChargeCreditAccount(null);
		setChargeDebitAccount(null);
		setManualFeedback(null);
		setPinNoFormat(null);
		setPinNoPadding(null);
		setSpecialCharacterforPin(null);
		setRemittanceSwiftTrf(null);
		setCreatedBy(null);
		setCretaedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setTestKeyInFile(null);
		setBankFilePrefix(null);
		setProductGroup(null);
	}

	public void clearBankChargesApprove() {
		setChargeFor(null);
		setChargeType(null);
		setChargeAmount(null);
		setFromAmount(null);
		setToAmount(null);
		setCurrencyCode(null);
		setCostCurrencyCode(null);
		setBankChrgeCurrencyCode(null);
		setBankChrgeCostCurrencyCode(null);
		setCostAmount(BigDecimal.ZERO);
	}

	/*
	 * 
	 * Fetch ID Type
	 */
	@Autowired
	RuleEngine<T> ruleEngine;
	private Map<BigDecimal, String> customerTypeMap = new HashMap<BigDecimal, String>();

	public Map<BigDecimal, String> getCutomerTypeMap() {
		customerTypeMap.putAll(ruleEngine.getComponentData("Customer Type"));

		return customerTypeMap;
	}

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	// Commission Account checking with V_GLNO view
	public void checkingCommissionAccWithVGLNO(){
		try{
			if(getCommisionAccount() != null && !getCommisionAccount().equalsIgnoreCase("") && getCurrencyId() != null){
				//List<ViewGeneralValidationGlNo> lstCommAcc = iBankAccountDetService.getAccountBalanceGLNoWithCurrency(mapCurrencyCode.get(getCurrencyId()), getCommisionAccount());
				
				
				
				/* Modified by Rabil on 20/02/2017 
				 *  As per Tripti Ma'am it should be local currency as refer to Old EMOs .
				 *  HashMap<String, String> glNoDesc = iBankAccountDetService.getAccountBalanceGLNoWithCurrencyWithProc(getCurrencyId(), getCommisionAccount());
				 */
				
				HashMap<String, String> glNoDesc = iBankAccountDetService.getAccountBalanceGLNoWithCurrencyWithProc(new BigDecimal(sessionStateManage.getCurrencyId()), getCommisionAccount());
				
				
				if(glNoDesc != null){
					// allow
					String gldesc = glNoDesc.get("GL_DESC");
					String errMsg = glNoDesc.get("ERR_MSG");
					if(errMsg != null){
						// error msg
						setCommisionAccount(null);
						setErrorMessage(errMsg);
						RequestContext.getCurrentInstance().execute("error.show();");
					}else{
						// allow
					}
				}else{
					// error msg
					setCommisionAccount(null);
					setErrorMessage("Invalid Commission Account");
					RequestContext.getCurrentInstance().execute("error.show();");
				}
			}
		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	// Charge Debit Account checking with V_GLNO view
	public void checkingChargeDebitAccWithVGLNO(){
		try{
			if(getChargeDebitAccount() != null && !getChargeDebitAccount().equalsIgnoreCase("") && getCurrencyId() != null){
				//List<ViewGeneralValidationGlNo> lstChargeDebitAcc = iBankAccountDetService.getAccountBalanceGLNoWithCurrency(mapCurrencyCode.get(getCurrencyId()), getChargeDebitAccount());
				//HashMap<String, String> glNoDesc = iBankAccountDetService.getAccountBalanceGLNoWithCurrencyWithProc(getCurrencyId(), getChargeDebitAccount());
				
				
				
				/* Modified by Rabil on 20/02/2017 
				 *  As per Tripti Ma'am it should be local currency as refer to Old EMOs .
				 *  	HashMap<String, String> glNoDesc = iBankAccountDetService.getAccountBalanceGLNoWithCurrencyWithProc(getCurrencyId(), getChargeDebitAccount());
				 */
				
				HashMap<String, String> glNoDesc = iBankAccountDetService.getAccountBalanceGLNoWithCurrencyWithProc(new BigDecimal(sessionStateManage.getCurrencyId()), getChargeDebitAccount());
				
				if(glNoDesc != null){
					// allow
					String gldesc = glNoDesc.get("GL_DESC");
					String errMsg = glNoDesc.get("ERR_MSG");
					if(errMsg != null){
						// error msg
						setChargeDebitAccount(null);
						setErrorMessage(errMsg);
						RequestContext.getCurrentInstance().execute("error.show();");
					}else{
						// allow
					}
				}else{
					// error msg
					setChargeDebitAccount(null);
					setErrorMessage("Invalid Charge Debit Account");
					RequestContext.getCurrentInstance().execute("error.show();");
				}
			}
		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		
	}

	// Charge Account checking with V_GLNO view
	public void checkingChargeAccWithVGLNO(){
		try{
			System.out.println("Local Currency :"+sessionStateManage.getCurrencyId());
			if(getChargeAccount() != null && !getChargeAccount().equalsIgnoreCase("") && getCurrencyId() != null){
				//List<ViewGeneralValidationGlNo> lstglNo = iBankAccountDetService.getAccountBalanceGLNoWithCurrency(mapCurrencyCode.get(getCurrencyId()), getChargeAccount());
				
				/* Modified by Rabil on 20/02/2017 
				 *  As per Tripti Ma'am it should be local currency as refer to Old EMOs .
				 *  HashMap<String, String> glNoDesc = iBankAccountDetService.getAccountBalanceGLNoWithCurrencyWithProc(getCurrencyId(), getChargeAccount());
				 */
				HashMap<String, String> glNoDesc = iBankAccountDetService.getAccountBalanceGLNoWithCurrencyWithProc(new BigDecimal(sessionStateManage.getCurrencyId()), getChargeAccount());
				
				
				if(glNoDesc != null){
					// allow
					String gldesc = glNoDesc.get("GL_DESC");
					String errMsg = glNoDesc.get("ERR_MSG");
					if(errMsg != null){
						// error msg
						setChargeAccount(null);
						setErrorMessage(errMsg);
						RequestContext.getCurrentInstance().execute("error.show();");
					}else{
						// allow
					}
				}else{
					// error msg
					setChargeAccount(null);
					setErrorMessage("Invalid Charge Account");
					RequestContext.getCurrentInstance().execute("error.show();");
				}
			}
		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	// Charge Credit Account checking with V_GLNO view
	public void checkingChargeCreditAccWithVGLNO(){
		try{
			if(getChargeCreditAccount() != null && !getChargeCreditAccount().equalsIgnoreCase("") && getBankChrgeCostCurrencyCode() != null){
				//List<ViewGeneralValidationGlNo> lstfundglNo = iBankAccountDetService.getAccountBalanceGLNoWithCurrency(mapCurrencyCode.get(getCurrencyId()), getChargeCreditAccount());
				
				/* Modified by Rabil on 20/02/2017 
				 *  As per Tripti Ma'am it should be local currency as refer to Old EMOs .
				 *  HashMap<String, String> glNoDesc = iBankAccountDetService.getAccountBalanceGLNoWithCurrencyWithProc(getCurrencyId(), getChargeCreditAccount());
				 *  
				 *  bankChrgeCostCurrencyCode
				 */
				
				
				
				//HashMap<String, String> glNoDesc = iBankAccountDetService.getAccountBalanceGLNoWithCurrencyWithProc(getCurrencyId(), getChargeCreditAccount());
				//22/02/2017
				HashMap<String, String> glNoDesc = iBankAccountDetService.getAccountBalanceGLNoWithCurrencyWithProc(getBankChrgeCostCurrencyCode(), getChargeCreditAccount());
				
				if(glNoDesc != null){
					// allow
					String gldesc = glNoDesc.get("GL_DESC");
					String errMsg = glNoDesc.get("ERR_MSG");
					if(errMsg != null){
						// error msg
						setChargeCreditAccount(null);
						setErrorMessage(errMsg);
						RequestContext.getCurrentInstance().execute("error.show();");
					}else{
						// allow
					}
				}else{
					// error msg
					setChargeCreditAccount(null);
					setErrorMessage("Invalid Charge Credit Account");
					RequestContext.getCurrentInstance().execute("error.show();");
				}
			}
		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}
	
	// fetch product group based on Bank and currency
	public void fetchProductGroupBasedonBankCurrency(){
		if(productGroupList != null || !productGroupList.isEmpty()){
			productGroupList.clear();
		}
		if(getBankId() != null && getCurrencyId() != null && getTransferMode() != null){
			List<ProductGroup> lstProductGrp = personalRemittanceService.fetchProductGroup(getBankId(), getCurrencyId());
			if(lstProductGrp.size() != 0){
				for (ProductGroup productGroup : lstProductGrp) {
					if(productGroup.getPrintMode().equalsIgnoreCase(mapTransferModeCode.get(getTransferMode()))){
						productGroupList.add(productGroup);
						mapProductGroupCode.put(productGroup.getProductGroupSerial(), productGroup.getPrintMode());
					}
				}
				
				if(productGroupList.size() != 0){
					checkTransferModeWithProductGroup();
				}
			}
		}
	}
	
	// check transfer mode with product group print mode - validation check
	public void checkTransferModeWithProductGroup(){
		
		if(getBankId() != null){
			// Not WU Bank enter
			if(getBankCode() != null && !getBankCode().equalsIgnoreCase(Constants.WU_BANK_CODE)){
				// Not W Transfer mode enter
				if(getTransferMode() != null && !mapTransferModeCode.get(getTransferMode()).equalsIgnoreCase(Constants.W)){
					setProductGroupOptional(true);
					if(getTransferMode() != null && getProductGroup() != null && !mapTransferModeCode.get(getTransferMode()).equalsIgnoreCase(mapProductGroupCode.get(getProductGroup()))){
						setProductGroup(null);
						setErrorMessage("Print Mode does not Match");
						RequestContext.getCurrentInstance().execute("error.show();");
					}
				}else{
					// Transfer Mode W no Condition
					setProductGroupOptional(false);
					productGroupList.clear();
					mapProductGroupCode.clear();
				}
			}else{
				// bank Code should not be western union
				setProductGroupOptional(false);
				productGroupList.clear();
				mapProductGroupCode.clear();
			}
		}else{
			// bank id mandatory, product group
			setProductGroupOptional(false);
		}
	}
	
	
	 //This method called when clicked hard delete button in data table
	 /* public void deleteRecordFromDB(BankServiceRuleMasterDataTable bankServiceData){
		 if(bankServiceData.getBankChargesId()!=null){
		  iBankServiceRuleMasterService.deleteRecordsFrmDB(bankServiceData.getBankChargesId());
		  saveRecordsToBankChargesMasLog(bankServiceData);
		  bankServiceDataTableList.remove(bankServiceData);
			
		 }
	  }*/
	  //This Method called  After successfully Hard Delete store data into MASLOG table
	  public void saveRecordsToBankChargesMasLog(BankServiceRuleMasterDataTable bankServiceData){
		  BankChargesMasterLog bankChargesLog=new BankChargesMasterLog();
		  
		  bankChargesLog.setFileName(bankServiceData.getChargeType() );
		  bankChargesLog.setChangeType(Constants.D);
		  bankChargesLog.setOldInf((bankServiceData.getFromAmount()!=null?bankServiceData.getFromAmount():"")+"-"+(bankServiceData.getToAmount()!=null?bankServiceData.getToAmount():"")+"-"+( bankServiceData.getChargeAmount()!=null?bankServiceData.getChargeAmount():""));
		  bankChargesLog.setNewInf(null);
		  bankChargesLog.setOracleUser(sessionStateManage.getUserName() );
		  bankChargesLog.setTabName("EX_BANK_CHARGES");
		  bankChargesLog.setUniqIndex("EX_BANK_CHARGES");
		  bankChargesLog.setUniqIndexVaue(getCountryId()+"-"+getCurrencyId()+"-"+getBankId()+"-"+getRemittanceModeId()+"-"+getDeliveryId());
		  bankChargesLog.setChangeDate( new Date());
		  
		  iBankServiceRuleMasterService.saveBankChargesLog(bankChargesLog);
		  
		  
	  }
	

}
