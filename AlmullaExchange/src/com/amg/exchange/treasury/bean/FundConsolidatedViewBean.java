package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.ICurrencyService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.FundConsolidatedView;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("fundConsolidatedViewBean")
@Scope("session")
public class FundConsolidatedViewBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(FundConsolidatedViewBean.class);
	SessionStateManage sessionStateManage = new SessionStateManage();

	// auto wire objects
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	ICurrencyService currencyService;
	@Autowired
	IFundEstimationService<T> fundEstimationService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	// variables for form
	private Date projectionDate;
	private BigDecimal exchangeCountryId;
	private String exchangeCountryName;
	private String exchangeCountryCode;
	private String usdCurrencyCode;
	private String exchangeCurrencyQuoteCode;
	private BigDecimal iKONRate;
	private BigDecimal bankCountryId;
	private String bankCountryName;
	private String bankCountryCode;
	private BigDecimal bankId;
	private String bankName;
	private String bankCode;
	private BigDecimal currencyId;
	private String currencyName;
	private String currencyCode;

	// extra variables
	private boolean booRenderfundConsolidatedDataTable = false;
	private BigDecimal totalFundRequired;
	private BigDecimal totalUSDEquivalent;
	private BigDecimal totalEquivalentAmount;
	private String exceptionMessage;
	private Date effectiveMaxDate;
	private Date currentDate = new Date();

	// List of records
	private List<CountryMasterDesc> lstofbussinesscountry = new ArrayList<CountryMasterDesc>();
	private List<BankCountryPopulationBean> bankCountryList = new ArrayList<BankCountryPopulationBean>();
	private List<BankMaster> bankList = new ArrayList<BankMaster>();
	private List<CountryCurrencyPopulationBean> countryCurrencyList = new ArrayList<CountryCurrencyPopulationBean>();
	private List<FundConsolidatedView> fundConsolidatedList = new ArrayList<FundConsolidatedView>();

	public List<CountryMasterDesc> getLstofbussinesscountry() {
		return lstofbussinesscountry;
	}
	public void setLstofbussinesscountry(List<CountryMasterDesc> lstofbussinesscountry) {
		this.lstofbussinesscountry = lstofbussinesscountry;
	}

	public List<BankCountryPopulationBean> getBankCountryList() {
		return bankCountryList;
	}
	public void setBankCountryList(List<BankCountryPopulationBean> bankCountryList) {
		this.bankCountryList = bankCountryList;
	}

	public List<BankMaster> getBankList() {
		return bankList;
	}
	public void setBankList(List<BankMaster> bankList) {
		this.bankList = bankList;
	}

	public List<CountryCurrencyPopulationBean> getCountryCurrencyList() {
		return countryCurrencyList;
	}
	public void setCountryCurrencyList(List<CountryCurrencyPopulationBean> countryCurrencyList) {
		this.countryCurrencyList = countryCurrencyList;
	}

	public List<FundConsolidatedView> getFundConsolidatedList() {
		return fundConsolidatedList;
	}
	public void setFundConsolidatedList(List<FundConsolidatedView> fundConsolidatedList) {
		this.fundConsolidatedList = fundConsolidatedList;
	}


	// variables getters and setters
	public Date getProjectionDate() {
		return projectionDate;
	}
	public void setProjectionDate(Date projectionDate) {
		this.projectionDate = projectionDate;
	}

	public BigDecimal getExchangeCountryId() {
		return exchangeCountryId;
	}
	public void setExchangeCountryId(BigDecimal exchangeCountryId) {
		this.exchangeCountryId = exchangeCountryId;
	}

	public String getExchangeCountryName() {
		return exchangeCountryName;
	}
	public void setExchangeCountryName(String exchangeCountryName) {
		this.exchangeCountryName = exchangeCountryName;
	}

	public String getExchangeCountryCode() {
		return exchangeCountryCode;
	}
	public void setExchangeCountryCode(String exchangeCountryCode) {
		this.exchangeCountryCode = exchangeCountryCode;
	}

	public String getUsdCurrencyCode() {
		return usdCurrencyCode;
	}
	public void setUsdCurrencyCode(String usdCurrencyCode) {
		this.usdCurrencyCode = usdCurrencyCode;
	}

	public String getExchangeCurrencyQuoteCode() {
		return exchangeCurrencyQuoteCode;
	}
	public void setExchangeCurrencyQuoteCode(String exchangeCurrencyQuoteCode) {
		this.exchangeCurrencyQuoteCode = exchangeCurrencyQuoteCode;
	}

	public BigDecimal getiKONRate() {
		return iKONRate;
	}
	public void setiKONRate(BigDecimal iKONRate) {
		this.iKONRate = iKONRate;
	}

	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}

	public String getBankCountryName() {
		return bankCountryName;
	}
	public void setBankCountryName(String bankCountryName) {
		this.bankCountryName = bankCountryName;
	}

	public String getBankCountryCode() {
		return bankCountryCode;
	}
	public void setBankCountryCode(String bankCountryCode) {
		this.bankCountryCode = bankCountryCode;
	}

	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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

	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	// extra variables
	public boolean isBooRenderfundConsolidatedDataTable() {
		return booRenderfundConsolidatedDataTable;
	}
	public void setBooRenderfundConsolidatedDataTable(boolean booRenderfundConsolidatedDataTable) {
		this.booRenderfundConsolidatedDataTable = booRenderfundConsolidatedDataTable;
	}

	public BigDecimal getTotalFundRequired() {
		return totalFundRequired;
	}
	public void setTotalFundRequired(BigDecimal totalFundRequired) {
		this.totalFundRequired = totalFundRequired;
	}

	public BigDecimal getTotalUSDEquivalent() {
		return totalUSDEquivalent;
	}
	public void setTotalUSDEquivalent(BigDecimal totalUSDEquivalent) {
		this.totalUSDEquivalent = totalUSDEquivalent;
	}

	public BigDecimal getTotalEquivalentAmount() {
		return totalEquivalentAmount;
	}
	public void setTotalEquivalentAmount(BigDecimal totalEquivalentAmount) {
		this.totalEquivalentAmount = totalEquivalentAmount;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public Date getEffectiveMaxDate() {
		return effectiveMaxDate;
	}
	public void setEffectiveMaxDate(Date effectiveMaxDate) {
		this.effectiveMaxDate = effectiveMaxDate;
	}
	
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// methods 
	//page navigation
	public void fundconsolidatedViewPageNavigation() {
		try {
			clearAllFields();
			setProjectionDate(new Date());
			setEffectiveMaxDate(new Date());
			sessionExchangeCountry();
			fetchBussinessCountryList();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "FundConsolidatedPage.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/FundConsolidatedPage.xhtml");
		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("dataexception.show();");
		}
	}

	// method to get the country Name name and country code from dataBase
	public void fetchBussinessCountryList() {
		try{
			LOG.info("Entering into getCountryNameList method");
			lstofbussinesscountry = generalService.getBusinessCountryList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
			LOG.info("Exit into getCountryNameList method");
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("dataexception.show();");
		}

	}

	// setting default session country id
	public void sessionExchangeCountry(){
		setExchangeCountryId(sessionStateManage.getCountryId());
		changeBussinessCountry();
	}

	// clearing fields
	public void clearAllFields(){
		setExchangeCountryId(null);
		setExchangeCountryCode(null);
		setExchangeCountryName(null);
		setExchangeCurrencyQuoteCode(null);
		setiKONRate(null);
		setBankCountryCode(null);
		setBankCountryId(null);
		setBankCountryName(null);
		setBankCode(null);
		setBankId(null);
		setBankName(null);
		setCurrencyCode(null);
		setCurrencyId(null);
		setCurrencyName(null);
		if(lstofbussinesscountry != null && !lstofbussinesscountry.isEmpty()){
			lstofbussinesscountry.clear();
		}
		if(bankCountryList != null && !bankCountryList.isEmpty()){
			bankCountryList.clear();
		}
		if(bankList != null && !bankList.isEmpty()){
			bankList.clear();
		}
		if(countryCurrencyList != null && !countryCurrencyList.isEmpty()){
			countryCurrencyList.clear();
		}
		if(fundConsolidatedList != null && !fundConsolidatedList.isEmpty()){
			fundConsolidatedList.clear();
		}
		setBooRenderfundConsolidatedDataTable(false);
		setExceptionMessage(null);
	}

	// change of business Country currency Changes
	public void changeBussinessCountry(){
		try{
			setExceptionMessage(null);
			if(getExchangeCountryId() != null){
				String quoteName = null;
				List<CurrencyMaster> usdExchangeCurrency = currencyService.getRecordToCheckDuplicate(Constants.FUND_ESTIMATION_USD_CURRENCY_CODE);
				if(usdExchangeCurrency.size() == 1){
					CurrencyMaster currencyCode = usdExchangeCurrency.get(0);
					if(currencyCode != null && currencyCode.getQuoteName() != null){
						quoteName = currencyCode.getQuoteName();
					}else{
						// currency Quote code is null 
						setExchangeCurrencyQuoteCode(null);
					}
				}else{
					// zero or multiple records for that Exchange Country id
					setExchangeCurrencyQuoteCode(null);
				}
				List<CurrencyMaster>  lstCurrencyCode = generalService.getCountryCurrencyList(getExchangeCountryId());

				if(lstCurrencyCode.size() == 1){
					CurrencyMaster currencyCode = lstCurrencyCode.get(0);
					if(currencyCode != null && currencyCode.getQuoteName() != null){
						setExchangeCurrencyQuoteCode(quoteName + "/" +currencyCode.getQuoteName());
					}else{
						// currency Quote code is null 
						setExchangeCurrencyQuoteCode(null);
					}
				}else{
					// zero or multiple records for that Exchange Country id
					setExchangeCurrencyQuoteCode(null);
				}
			}
			// USD IKON RATE
			fetchIKONRATE();
			// bank country based on business country - Exchange Country 
			fetchBankCountryList();
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("dataexception.show();");
		}

	}

	// fetch ikon rate based on record id = "IKON" and Parameter code def = "USD"
	public void fetchIKONRATE(){
		try{
			setExceptionMessage(null);
			BigDecimal ikonRateDB = fundEstimationService.fetchIKONRate();
			if(ikonRateDB != null){
				setiKONRate(ikonRateDB);
			}else{
				setiKONRate(null);
			}
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("dataexception.show();");
		}

	}

	// fetching bank country list
	public void fetchBankCountryList(){
		try{
			setExceptionMessage(null);
			if(bankCountryList != null && !bankCountryList.isEmpty()){
				bankCountryList.clear();
			}
			if(getExchangeCountryId() != null){
				List<BankCountryPopulationBean> lstBankCountryPopulationBean = fundEstimationService.getBankContryFromView(getExchangeCountryId());
				if(lstBankCountryPopulationBean != null && !lstBankCountryPopulationBean.isEmpty()){
					bankCountryList.addAll(lstBankCountryPopulationBean);
				}
			}
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("dataexception.show();");
		}

	}
	
	// fetch bank's and currency's based on Bank Country and Exchange Country
	public void fetchBankCurrencyDetails(){
		// fetching banks
		fetchBankBasedOnBankCountry();
		// fetching Currency's
		fetchCurrencyBasedOnBankCountry();
		
	}

	// fetching banks based on bank country and Exchange Country
	public void fetchBankBasedOnBankCountry(){
		try{
			setExceptionMessage(null);
			if(bankList != null && !bankList.isEmpty()){
				bankList.clear();
			}
			if(getExchangeCountryId() != null){
				List<BankMaster> lstBankMaster = fundEstimationService.getBankAccordingToBankCountry(getExchangeCountryId(), getBankCountryId());
				if(lstBankMaster != null && !lstBankMaster.isEmpty()){
					for (BankMaster bankMaster : lstBankMaster) {
						if(bankMaster.getRecordStatus() != null && bankMaster.getRecordStatus().equalsIgnoreCase(Constants.Yes)){
							bankList.add(bankMaster);
						}
					}
				}
			}
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("dataexception.show();");
		}

	}

	// fetching Currency based on bank country and Exchange Country
	public void fetchCurrencyBasedOnBankCountry(){
		try{
			setExceptionMessage(null);
			if(countryCurrencyList != null && !countryCurrencyList.isEmpty()){
				countryCurrencyList.clear();
			}
			if(getExchangeCountryId() != null){
				List<CountryCurrencyPopulationBean> lstCountryCurrency = fundEstimationService.getBankCurrencyFromView(getExchangeCountryId(), getBankCountryId());
				if(lstCountryCurrency != null && !lstCountryCurrency.isEmpty()){
					countryCurrencyList.addAll(lstCountryCurrency);
				}
			}
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("dataexception.show();");
		}

	}
	
	// searching fund Consolidated view
	public void searchFundConsolidatedView(){
		try{
			setExceptionMessage(null);
			if(fundConsolidatedList != null && !fundConsolidatedList.isEmpty()){
				fundConsolidatedList.clear();
			}
			if(getProjectionDate() != null && getExchangeCountryId() != null){
				List<FundConsolidatedView> lstfundConsolidate = fundEstimationService.fetchFundConsolidatedViewRecords(getProjectionDate(), getExchangeCountryId(), getBankCountryId(), getBankId(), getCurrencyId());
				if(lstfundConsolidate != null && !lstfundConsolidate.isEmpty()){
					setBooRenderfundConsolidatedDataTable(true);
					BigDecimal totalFundRequiredTemp = BigDecimal.ZERO;
					BigDecimal totalUsdEquivalentTemp = BigDecimal.ZERO;
					BigDecimal totalEquivalentTemp = BigDecimal.ZERO;
					String currencyCodeTemp = null;
					int i = 0;
					for (FundConsolidatedView fundConsolidatedView : lstfundConsolidate) {
						if(currencyCodeTemp == null){
							currencyCodeTemp = fundConsolidatedView.getCurrencyCode();
						}else{
							if(!fundConsolidatedView.getCurrencyCode().equalsIgnoreCase(currencyCodeTemp)){
								i++;
							}else{
								i = 0;
							}
						}
						totalFundRequiredTemp = totalFundRequiredTemp.add(fundConsolidatedView.getFundRequiredAmount());
						totalUsdEquivalentTemp = totalUsdEquivalentTemp.add(fundConsolidatedView.getUsdEquivalentAmount());
						if(fundConsolidatedView.getLocalEquivalentAmount() != null){
						totalEquivalentTemp = totalEquivalentTemp.add(fundConsolidatedView.getLocalEquivalentAmount());
						fundConsolidatedView.setLocalEquivalentAmount(GetRound.roundBigDecimal(fundConsolidatedView.getLocalEquivalentAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
						}
						
						fundConsolidatedList.add(fundConsolidatedView);
					}
					if(i == 0){
						setTotalFundRequired(totalFundRequiredTemp);
					}else{
						setTotalFundRequired(null);
					}

					setTotalUSDEquivalent(totalUsdEquivalentTemp);
					setTotalEquivalentAmount(GetRound.roundBigDecimal(totalEquivalentTemp,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));

				}else{
					setBooRenderfundConsolidatedDataTable(false);
					// dialogue box no records found
					RequestContext.getCurrentInstance().execute("exist.show();");
				}
			}
		}catch(NullPointerException e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("dataexception.show();");
		}
	}

	// for Exit Button
	public void exit() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("dataexception.show();");
		}
	}
	
}
