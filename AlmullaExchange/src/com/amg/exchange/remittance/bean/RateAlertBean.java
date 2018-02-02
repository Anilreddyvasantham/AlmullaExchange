package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.model.OnlineRateAlert;
import com.amg.exchange.model.RateAlertFrequency;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.remittance.service.IRateAlertSetupService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.ExchangeRate;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Mohan
 * 
 * @param <T>
 */
@Component("rateAlertBean")
@Scope("session")
public class RateAlertBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(RateAlertBean.class);
	// Variables Declaration
	private BigDecimal customerId = null;
	private BigDecimal benificaryId = null;
	private String baseCurrencyCode = null;
	private BigDecimal fCurrencyId = null;
	private String fCurrencyCode = null;
	private BigDecimal exhangeRateId = null;
	private BigDecimal alertRate = null;
	private Boolean mobileCheck = false;
	private Boolean emailCheck;
	private BigDecimal civilId = null;
	private String frequnecy = null;
	private BigDecimal currencyId; // from front end
	private String emailDisplay = null;
	private String mobileDisplay = null;
	private Date effectoveFrom = null;
	private Date effectoveTo = null;
	private BigDecimal howoftenId = null;
	private BigDecimal baseCurrencyId = null;
	// private BigDecimal appcountryId = null; // both for choosing currencies
	private String frequencyDescription = null;
	private BigDecimal onlineRateAlertId = null;
	private BigDecimal triggerRate = null; // user will enter this one
	private BigDecimal exchangeCurrentRateId = null;
	private BigDecimal exchangeCurrentRate = null;
	OnlineRateAlert onlineRateAlert = null; // Model for AlertRate
	private BigDecimal currencyName;
	private Boolean booSubmit;
	private Boolean booRenderDataTable = null;
	private Boolean booRenderSaveExit = false;
	SessionStateManage state = null;
	private boolean hideEdit;

	Map<BigDecimal, String> mapCurrencyList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapQuoteList = new HashMap<BigDecimal, String>();
	private List<RateAlertSetupDataTable> rateAlertSetupDTList = new ArrayList<RateAlertSetupDataTable>();
	Map<BigDecimal, String> mapHowOftenList = new HashMap<BigDecimal, String>();
	List<RateAlertFrequency> howoftenList = new ArrayList<RateAlertFrequency>();
	List<CurrencyMaster> currencyList = new ArrayList<CurrencyMaster>();

	// Services
	@Autowired
	IRateAlertSetupService<T> iRateAlertSetupService;
	@Autowired
	ISpecialCustomerDealRequestService<T> iSpecialCustomerDealRequestService;

	public boolean isHideEdit() {
		return hideEdit;
	}

	public void setHideEdit(boolean hideEdit) {
		this.hideEdit = hideEdit;
	}

	public Boolean getBooSubmit() {
		return booSubmit;
	}

	public void setBooSubmit(Boolean booSubmit) {
		this.booSubmit = booSubmit;
	}

	public BigDecimal getExchangeCurrentRateId() {
		return exchangeCurrentRateId;
	}

	public void setExchangeCurrentRateId(BigDecimal exchangeCurrentRateId) {
		this.exchangeCurrentRateId = exchangeCurrentRateId;
	}

	public List<RateAlertSetupDataTable> getRateAlertSetupDTList() {
		return rateAlertSetupDTList;
	}

	public void setRateAlertSetupDTList(List<RateAlertSetupDataTable> rateAlertSetupDTList) {
		this.rateAlertSetupDTList = rateAlertSetupDTList;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public String getEmailDisplay() {
		return emailDisplay;
	}

	public void setEmailDisplay(String emailDisplay) {
		this.emailDisplay = emailDisplay;
	}

	public String getMobileDisplay() {
		return mobileDisplay;
	}

	public void setMobileDisplay(String mobileDisplay) {
		this.mobileDisplay = mobileDisplay;
	}

	public void setHowoftenList(List<RateAlertFrequency> howoftenList) {
		this.howoftenList = howoftenList;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getBenificaryId() {
		return benificaryId;
	}

	public void setBenificaryId(BigDecimal benificaryId) {
		this.benificaryId = benificaryId;
	}

	public BigDecimal getBaseCurrencyId() {
		return baseCurrencyId;
	}

	public void setBaseCurrencyId(BigDecimal baseCurrencyId) {
		this.baseCurrencyId = baseCurrencyId;
	}

	public String getBaseCurrencyCode() {
		return baseCurrencyCode;
	}

	public void setBaseCurrencyCode(String baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}

	public BigDecimal getfCurrencyId() {
		return fCurrencyId;
	}

	public void setfCurrencyId(BigDecimal fCurrencyId) {
		this.fCurrencyId = fCurrencyId;
	}

	public String getfCurrencyCode() {
		return fCurrencyCode;
	}

	public void setfCurrencyCode(String fCurrencyCode) {
		this.fCurrencyCode = fCurrencyCode;
	}

	public BigDecimal getExhangeRateId() {
		return exhangeRateId;
	}

	public void setExhangeRateId(BigDecimal exhangeRateId) {
		this.exhangeRateId = exhangeRateId;
	}

	public BigDecimal getAlertRate() {
		return alertRate;
	}

	public void setAlertRate(BigDecimal alertRate) {
		this.alertRate = alertRate;
	}

	public BigDecimal getCivilId() {
		return civilId;
	}

	public void setCivilId(BigDecimal civilId) {
		this.civilId = civilId;
	}

	public String getFrequnecy() {
		return frequnecy;
	}

	public void setFrequnecy(String frequnecy) {
		this.frequnecy = frequnecy;
	}

	public Map<BigDecimal, String> getMapCurrencyList() {
		return mapCurrencyList;
	}

	public void setMapCurrencyList(Map<BigDecimal, String> mapCurrencyList) {
		this.mapCurrencyList = mapCurrencyList;
	}

	public Map<BigDecimal, String> getMapQuoteList() {
		return mapQuoteList;
	}

	public void setMapQuoteList(Map<BigDecimal, String> mapQuoteList) {
		this.mapQuoteList = mapQuoteList;
	}

	public BigDecimal getTriggerRate() {
		return triggerRate;
	}

	public void setTriggerRate(BigDecimal triggerRate) {
		this.triggerRate = triggerRate;
	}

	public Boolean getMobileCheck() {
		return mobileCheck;
	}

	public void setMobileCheck(Boolean mobileCheck) {
		this.mobileCheck = mobileCheck;
	}

	public Boolean getEmailCheck() {
		return emailCheck;
	}

	public void setEmailCheck(Boolean emailCheck) {
		this.emailCheck = emailCheck;
	}

	public Boolean getBooRenderSaveExit() {
		return booRenderSaveExit;
	}

	public void setBooRenderSaveExit(Boolean booRenderSaveExit) {
		this.booRenderSaveExit = booRenderSaveExit;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public void addRecordsToDataTable() {

		LOGGER.debug("Entering into  addRecordsToDataTable");
		if (getTriggerRate().doubleValue() == 0) {
			LOGGER.debug("Trigger rate should not  be  zero");
			RequestContext.getCurrentInstance().execute("zero.show();");

		} else if (getTriggerRate().signum() == -1) {
			LOGGER.debug("Trigger rate should not  be  negative");
			RequestContext.getCurrentInstance().execute("negative.show();");
		}

		else if ((!getEmailCheck() && !getMobileCheck())) {
			LOGGER.debug("User should choose any one of option");
			RequestContext.getCurrentInstance().execute("chooseAnyOne.show();");

		} else {

			setBooSubmit(false);
			setHideEdit(false);
			RateAlertSetupDataTable rateAlertObj = new RateAlertSetupDataTable();

			LOGGER.debug("getEmailCheck() " + getEmailCheck());
			LOGGER.debug("getMobileCheck " + getMobileCheck());
			LOGGER.debug("getExchangeCurrentRateId " + getExchangeCurrentRateId());
			LOGGER.debug("getExchangeCurrentRate " + getExchangeCurrentRate());
			LOGGER.debug("getCurrencyId " + getCurrencyId());
			LOGGER.debug("getFrequencyDescription" + getFrequencyDescription());
			LOGGER.debug("getOnlineRateAlertId " + getOnlineRateAlertId());
			LOGGER.debug("getHowoftenId " + getHowoftenId());

			rateAlertObj.setExchnageCurrentRate(getExchangeCurrentRate());
			rateAlertObj.setExchnageCurrentRateId(getExchangeCurrentRateId());
			rateAlertObj.setTriggerRate(getTriggerRate());
			rateAlertObj.setEmailCheck(getEmailCheck());
			rateAlertObj.setMobileCheck(getMobileCheck());
			rateAlertObj.setCurrency(getCurrencyId());
			rateAlertObj.setHowOftenId(getHowoftenId());
			rateAlertObj.setCurrencyName(mapCurrencyList.get(getCurrencyId()));
			rateAlertObj.setHowOften(mapHowOftenList.get(getHowoftenId()));
			rateAlertObj.setEffectoveFrom(new Date());

			// Date calculation

			Date d1 = new Date();
			Calendar cl = Calendar.getInstance();
			cl.setTime(d1);
			String frequency = null;
			if (rateAlertObj.getHowOften().equals(Constants.DAILY)) {
				cl.add(Calendar.DATE, 1);
				frequency = "1";

			} else if (rateAlertObj.getHowOften().equals(Constants.WEEKLY)) {
				cl.add(Calendar.DATE, 7);
				frequency = "7";
			} else if (rateAlertObj.getHowOften().equals(Constants.WEEKLY)) {
				cl.add(Calendar.DATE, 14);
				frequency = "14";
			} else if (rateAlertObj.getHowOften().equals(Constants.MONTHLY)) {
				cl.add(Calendar.DATE, 30);
				frequency = "30";
			} else if (rateAlertObj.getHowOften().equals(Constants.QUARTERLY)) {
				cl.add(Calendar.DATE, 90);
				frequency = "90";
			} else if (rateAlertObj.getHowOften().equals(Constants.HALFYEARLY)) {
				cl.add(Calendar.DATE, 180);
				frequency = "180";
			}

			else if (rateAlertObj.getHowOften().equals(Constants.ANNUALLY)) {
				cl.add(Calendar.DATE, 365);
				frequency = "365";
			}
			rateAlertObj.setEffectoveTo(cl.getTime());

			rateAlertObj.setFrequency(frequency);

			LOGGER.debug(rateAlertObj);

			LOGGER.debug("Size of List  " + rateAlertSetupDTList.size());

			boolean add = false;
			boolean dup = false;

			OnlineRateAlert exce = null;

			try {
				exce = iRateAlertSetupService.isExists(getCustomerId(), getCurrencyId(), frequency);
				LOGGER.debug("Data from OnlineRate table - " + exce.getAlertEmail());
				LOGGER.debug("Data from OnlineRate table - " + exce.getCreatedBy());

				dup = true;
			} catch (Exception e) {

				LOGGER.debug("Exception occured" + e);
			}
			if (rateAlertSetupDTList.size() > 0) {

				for (RateAlertSetupDataTable obj : rateAlertSetupDTList) {

					int currencyCheck = obj.getCurrency().compareTo(rateAlertObj.getCurrency());
					int triggerRate = obj.getTriggerRate().compareTo(rateAlertObj.getTriggerRate());

					if (currencyCheck == 0) {
						if (obj.getHowOften().equals(rateAlertObj.getHowOften())) {
							if (triggerRate == 0) {
								RequestContext.getCurrentInstance().execute("duplicate.show();");
								clearAllFileds();
							} else {
								add = true;
							}
						} else {
							add = true;
						}
					} else {
						add = true;
					}

				}
			} else {
				add = true;
			}

			if (add && !dup) {
				LOGGER.debug("Record added in data table ");
				rateAlertSetupDTList.add(rateAlertObj);
				setBooRenderDataTable(true);
				setBooRenderSaveExit(true);
				clearAllFileds();
			}
			if (dup) {
				LOGGER.debug("Duplicate found in DB");
				RequestContext.getCurrentInstance().execute("duplicate.show();");
				clearAllFileds();
			}

		}

		LOGGER.debug("Exit into addRecordsToDataTable method ");
	}

	public BigDecimal getHowoftenId() {
		return howoftenId;
	}

	public void setHowoftenId(BigDecimal howoftenId) {
		this.howoftenId = howoftenId;
	}

	public Map<BigDecimal, String> getMapHowOftenList() {
		return mapHowOftenList;
	}

	public void setMapHowOftenList(Map<BigDecimal, String> mapHowOftenList) {
		this.mapHowOftenList = mapHowOftenList;
	}

	public BigDecimal getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(BigDecimal currencyName) {
		this.currencyName = currencyName;
	}

	public void clearAllFileds() {

		LOGGER.debug("Entering into clearAllFileds methods");
		setCurrencyId(null);
		setExchangeCurrentRateId(null);
		setEmailCheck(false);
		setMobileCheck(false);
		setTriggerRate(null);
		setHowoftenId(null);
		setExchangeCurrentRate(null);

		LOGGER.debug("Exit into clearAllFileds methods");

	}

	public Date getEffectoveFrom() {
		return effectoveFrom;
	}

	public void setEffectoveFrom(Date effectoveFrom) {
		this.effectoveFrom = effectoveFrom;
	}

	public Date getEffectoveTo() {
		return effectoveTo;
	}

	public void setEffectoveTo(Date effectoveTo) {
		this.effectoveTo = effectoveTo;
	}

	public void checkMobile() {
		if (getMobileCheck()) {
			setMobileCheck(true);
		} else {
			setMobileCheck(false);
		}
	}

	public void checkEmail() {
		if (getEmailCheck()) {
			setEmailCheck(true);
		} else {
			setEmailCheck(false);
		}
	}

	public void saveDataTableRecods() {
		LOGGER.debug("Entering into saveDataTableRecods method");
		if (rateAlertSetupDTList.isEmpty()) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		} else {
			for (RateAlertSetupDataTable rateAlertObj : rateAlertSetupDTList) {

				LOGGER.debug(rateAlertObj);

				onlineRateAlert = new OnlineRateAlert();
				CurrencyMaster currencyId = new CurrencyMaster();
				currencyId.setCurrencyId(rateAlertObj.getCurrency());
				Customer customer = new Customer();
				customer.setCustomerId(getCustomerId());
				onlineRateAlert.setCustomerId(customer);
				onlineRateAlert.setBaseCurrencyId(currencyId);
				onlineRateAlert.setForeignCurrencyId(currencyId);

				onlineRateAlert.setBaseCurrencyCode(mapQuoteList.get(rateAlertObj.getCurrency()));
				onlineRateAlert.setForeignCurrencyCode(mapQuoteList.get(rateAlertObj.getCurrency()));
				onlineRateAlert.setAlertRate(rateAlertObj.getTriggerRate());
				onlineRateAlert.setAlertEmail(rateAlertObj.getEmailCheck().equals(true) ? Constants.Yes : Constants.No);
				onlineRateAlert.setAlertSms(rateAlertObj.getMobileCheck().equals(true) ? Constants.Yes : Constants.No);
				onlineRateAlert.setLastDate(rateAlertObj.getEffectoveFrom());
				onlineRateAlert.setAlertLastDate(rateAlertObj.getEffectoveTo());
				onlineRateAlert.setFrequency(rateAlertObj.getFrequency());
				onlineRateAlert.setIsActive(Constants.Yes);
				onlineRateAlert.setCreatedDate(new Date());
				onlineRateAlert.setCreatedBy(state.getUserName());
				onlineRateAlert.setUpdatedDate(new Date());
				onlineRateAlert.setUpdatedBy(state.getUserName());
				onlineRateAlert.setExchangeRate(rateAlertObj.getExchnageCurrentRate());
				onlineRateAlert.setCivilId(civilId);

				ExchangeRate exchangeCurrentRate = new ExchangeRate();
				exchangeCurrentRate.setExchangeRateMasterId(rateAlertObj.getExchnageCurrentRateId());
				onlineRateAlert.setExchangeRateId(exchangeCurrentRate);
				iRateAlertSetupService.save(onlineRateAlert);
			}
			RequestContext.getCurrentInstance().execute("saveRateAlert.show();");
			clearAllFileds();
			onlineRateAlert = null;
			setBooRenderDataTable(true);
			setBooRenderSaveExit(true);

		}
	}

	public String getFrequencyDescription() {
		return frequencyDescription;
	}

	public void setFrequencyDescription(String frequencyDescription) {
		this.frequencyDescription = frequencyDescription;
	}

	public BigDecimal getOnlineRateAlertId() {
		return onlineRateAlertId;
	}

	public void setOnlineRateAlertId(BigDecimal onlineRateAlertId) {
		this.onlineRateAlertId = onlineRateAlertId;
	}

	public BigDecimal getExchangeCurrentRate() {
		return exchangeCurrentRate;
	}

	public void setExchangeCurrentRate(BigDecimal exchangeCurrentRate) {
		this.exchangeCurrentRate = exchangeCurrentRate;
	}

	public void editRecord(RateAlertSetupDataTable objRateAlertSetupDataTable) {
		LOGGER.debug("Entering into editRecord method");

		setBooSubmit(true);
		setHideEdit(true);
		LOGGER.debug(objRateAlertSetupDataTable);
		setExchangeCurrentRateId(objRateAlertSetupDataTable.getExchnageCurrentRateId());
		setTriggerRate(objRateAlertSetupDataTable.getTriggerRate());
		setCurrencyId(objRateAlertSetupDataTable.getCurrency());
		setHowoftenId(objRateAlertSetupDataTable.getHowOftenId());
		setExchangeCurrentRate(objRateAlertSetupDataTable.getExchnageCurrentRate());
		rateAlertSetupDTList.remove(objRateAlertSetupDataTable);
		setEmailCheck(objRateAlertSetupDataTable.getEmailCheck());
		setMobileCheck(objRateAlertSetupDataTable.getMobileCheck());

		LOGGER.debug("Exit into editRecord method");
	}

	public void removeRecord(RateAlertSetupDataTable objRateAlertSetupDataTable) {
		rateAlertSetupDTList.remove(objRateAlertSetupDataTable);
	}

	public void clickOnOKSave() {
		rateAlertSetupDTList.clear();

		setBooRenderDataTable(false);
		setBooRenderSaveExit(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("..treasury/RateAlertSetup.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// method called from menubar - navigation
	public void rateAlertNavigation() {

		state = new SessionStateManage();
		if (state.getCustomerId() != null && state.getCustomerId().intValue() == 0) {
			RequestContext.getCurrentInstance().execute("customerAccess.show();");
		} else {
			try {
				if (state.getCustomerId() != null && state.getCustomerId().intValue() == 0) {
					RequestContext.getCurrentInstance().execute("customerAccess.show();");
				} else {

					clearAllFileds();
					currencyList.clear();
					rateAlertSetupDTList.clear();
					setBooRenderDataTable(false);
					LOGGER.info("***************" + state.getCustomerId());
					LOGGER.info("***************" + state.getCustomerId());
					LOGGER.info("***************" + state.getCustomerId());
					Customer customer = null;
					customer = getCustomer(state.getCustomerId());
					LOGGER.info("***************" + customer.getCreationDate());
					LOGGER.info("***************" + customer.getActivatedInd());

					if (customer.getActivatedInd().equals("0")) {
						RequestContext.getCurrentInstance().execute("customerAccess.show();");
					}

					else if (customer.getActivatedDate() == null) {
						RequestContext.getCurrentInstance().execute("customerAccess.show();");
					} else if (customer.getFsCompanyMaster().getCompanyId() == null) {
						RequestContext.getCurrentInstance().execute("customerAccess.show();");
					}

					else {

						FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/RateAlertSetup.xhtml");
						LOGGER.info("***************" + customer.getFsCompanyMaster().getCompanyId());
						BigDecimal civilId = new BigDecimal(customer.getFsCustomerIdProofs().get(0).getIdentityInt());
						setCivilId(civilId);
						String customerMobileNo = customer.getMobile();
						String customerEmail = customer.getEmail();
						String emailFirst = customerEmail.substring(0, customerEmail.lastIndexOf('@'));
						String emailSecond = customerEmail.substring(customerEmail.lastIndexOf('@'), customerEmail.length());
						LOGGER.debug("Email first " + emailFirst);

						setEmailDisplay(maskCharacters(emailFirst) + emailSecond);
						setMobileDisplay(maskCharacters(customerMobileNo));

						LOGGER.debug("Customer Email id " + customer.getEmail());
						LOGGER.debug("Customer Mobile No " + customer.getMobile());

						LOGGER.debug("Civil ID " + civilId);
						setCustomerId(customer.getCustomerId());
						for (CurrencyMaster currencyMaster : currencyList) {
							mapCurrencyList.put(currencyMaster.getCurrencyId(), currencyMaster.getCurrencyName());
						}
						currencyList.addAll(iRateAlertSetupService.getCurrencyList());
						for (CurrencyMaster currencyMaster : currencyList) {
							mapCurrencyList.put(currencyMaster.getCurrencyId(), currencyMaster.getCurrencyName());
							mapQuoteList.put(currencyMaster.getCurrencyId(), currencyMaster.getQuoteName());
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// exit to Home Page
	public void exit() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/personaldetails.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<RateAlertFrequency> getHowoftenList() {

		LOGGER.debug("Entering into getHowoftenList method");
		List<RateAlertFrequency> howOftenList = new ArrayList<RateAlertFrequency>();
		howOftenList.addAll(iRateAlertSetupService.getHowOftenList());
		for (RateAlertFrequency obj : howOftenList) {
			mapHowOftenList.put(obj.getOnlineRateAlertId(), obj.getFrequencyDescription());
		}

		return howOftenList;

	}

	public Customer getCustomer(BigDecimal customerId) {
		List<Customer> custList = iSpecialCustomerDealRequestService.getCustomerIdentity(customerId);
		Customer cust = custList.get(0);
		return cust;
	}

	public List<CurrencyMaster> getCurrencyList() {
		return currencyList;
	}

	public void PopUp() {
		LOGGER.debug("Entering to PopUp method");
		exchangeCurrentRateId = null;
		try {
			LOGGER.info("CountryId from stateManagebean----> " + state.getCountryId());
			LOGGER.info("CurrencyId----> " + getCurrencyId());
			ExchangeRate tempObj = new ExchangeRate();
			tempObj = iRateAlertSetupService.getExchangeRate(state.getCountryId(), getCurrencyId());
			exchangeCurrentRateId = tempObj.getExchangeRateMasterId();
			exchangeCurrentRate = tempObj.getSellrateMin();
			LOGGER.info("exchangeCurrentRateId----> " + exchangeCurrentRateId);
			LOGGER.info("exchangeCurrentRate----> " + exchangeCurrentRate);
			setExchangeCurrentRateId(exchangeCurrentRateId);
			setExchangeCurrentRate(exchangeCurrentRate);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in  RateAlertBean.PopUp method" + e.getMessage());
			RequestContext.getCurrentInstance().execute("dataNotFund.show();");
			clearAllFileds();
		}

	}

	// mask character function
	public static String maskCharacters(String mask) {

		StringBuilder masked = new StringBuilder();
		for (int i = 0; i < mask.length(); i++) {
			if (i < (mask.length() / 2)) {
				masked.append('*');
			} else {
				masked.append(mask.charAt(i));
			}

		}
		return masked.toString();
	}

}
