package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.ExchangeRateApprovalDetModel;
import com.amg.exchange.remittance.model.ViewExchangeRateAppDetails;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.ExchangeRate;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

/*
 * Author Mohan
 */

@Component("exchangeRateEnquiryBean")
@Scope("session")
public class ExchangeRateEnquiryBean<T>  extends RatesUpdateBean<T> implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(ExchangeRateEnquiryBean.class);
	private static final long serialVersionUID = 1L;
	
	private String countryBranchName = null;

	private  Boolean booEnableDatatable = false;
	private String errorMsg;
	private SessionStateManage session = new SessionStateManage();
	private List<RatesUpdateBeanDataTable> ratesUpdateDataTable = new ArrayList<RatesUpdateBeanDataTable>();
	private ArrayList<ExchangeRate> ratesListFromDB = new ArrayList<ExchangeRate>();
	private Map<BigDecimal, String> currencyMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> countryMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> countryCodeMap = new TreeMap<BigDecimal, String>();
	private Map<BigDecimal, String> bankMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> countryBranchMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> serviceMap = new HashMap<BigDecimal, String>();
	//private Map<BigDecimal, String> deliveryMap = new HashMap<BigDecimal, String>();
	//private Map<BigDecimal, String> remittanceMap = new HashMap<BigDecimal, String>();

	private List<CountryMasterDesc> countryList = new ArrayList<CountryMasterDesc>();
	private List<CurrencyMaster> currencyList = new ArrayList<CurrencyMaster>();
	private List<BankMaster> bankList = new ArrayList<BankMaster>();
	private List<CountryBranch> countryBranchList = new ArrayList<CountryBranch>();
	private List<ServiceMasterDesc> serviceMasters = new ArrayList<ServiceMasterDesc>();
	//private List<DeliveryMode> deliveryModeList = new ArrayList<DeliveryMode>();
	//private List<DeliveryModeDesc> listDelivryDesc = new ArrayList<>();
	//private List<RemittanceModeDescription> remittanceModeList = new ArrayList<RemittanceModeDescription>();
	List<ViewExchangeRateAppDetails> exchangeRateForBranchList = new ArrayList<ViewExchangeRateAppDetails>();
	/*List<ViewExchangeRateAppDetails> exchangeRateCurrencyWiseCountryList = new ArrayList<ViewExchangeRateAppDetails>();*/
	private Map<BigDecimal, String> currencywisecountryMap = new HashMap<BigDecimal, String>();
	private List<PopulateData> lstCountryBasedOnCurrency = new ArrayList<PopulateData>();


	@Autowired
	IPersonalRemittanceService personalRemittanceService;

	public List<RatesUpdateBeanDataTable> getRatesUpdateDataTable() {
		return ratesUpdateDataTable;
	}
	public void setRatesUpdateDataTable(List<RatesUpdateBeanDataTable> ratesUpdateDataTable) {
		this.ratesUpdateDataTable = ratesUpdateDataTable;
	}

	public Boolean getBooEnableDatatable() {
		return booEnableDatatable;
	}
	public void setBooEnableDatatable(Boolean booEnableDatatable) {
		this.booEnableDatatable = booEnableDatatable;
	}
	
	public List<CountryMasterDesc> getCountryList() {
		return countryList;
	}
	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}

	public List<CurrencyMaster> getCurrencyList() {
		return currencyList;
	}
	public void setCurrencyList(List<CurrencyMaster> currencyList) {
		this.currencyList = currencyList;
	}

	public List<BankMaster> getBankList() {
		return bankList;
	}
	public void setBankList(List<BankMaster> bankList) {
		this.bankList = bankList;
	}

	public List<CountryBranch> getCountryBranchList() {
		return countryBranchList;
	}
	public void setCountryBranchList(List<CountryBranch> countryBranchList) {
		this.countryBranchList = countryBranchList;
	}
	
	public List<ServiceMasterDesc> getServiceMasters() {
		return serviceMasters;
	}
	public void setServiceMasters(List<ServiceMasterDesc> serviceMasters) {
		this.serviceMasters = serviceMasters;
	}
	
	public String getCountryBranchName() {
		return countryBranchName;
	}
	public void setCountryBranchName(String countryBranchName) {
		this.countryBranchName = countryBranchName;
	}
	
	public void fetchBank(){
		List<BankMaster> correspondingBankList=null;
		try{
			correspondingBankList = generalService.getAllBankListFromBankMaster();
			for (BankMaster bankMaster : correspondingBankList) {
				bankMap.put(bankMaster.getBankId(), bankMaster.getBankFullName());
			}
			if(correspondingBankList != null && correspondingBankList.size() != 0){
				setBankList(correspondingBankList);
			}
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::fetchBank"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}
	
	public void fetchCurrency(){
		List<CurrencyMaster> lstcurrency = null;
		try{
			currencyMap.clear();
			lstcurrency = generalService.getCurrencyList();
			for (CurrencyMaster currencyMaster : lstcurrency) {
				currencyMap.put(currencyMaster.getCurrencyId(),
						currencyMaster.getCurrencyName());
			}
			
			if(lstcurrency != null && lstcurrency.size() != 0){
				setCurrencyList(lstcurrency);
			}
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
		}
	}
	
	public void fetchCountry(){
		List<CountryMasterDesc> lstcountry=null;
		try{
			lstcountry = generalService.getCountryList(session.getLanguageId());
			for (CountryMasterDesc countryMasterDesc : lstcountry) {
				countryMap.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
				countryCodeMap.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getFsCountryMaster().getCountryCode());
			}
			
			if(lstcountry != null && lstcountry.size() != 0){
				setCountryList(lstcountry);
			}
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());      
		}
	}
	
	public void fetchCountryBranch(){
		List<CountryBranch> lstcountryBranch=null;
		try{
			countryBranchMap.clear();
			lstcountryBranch = ratesUpdateService.getCountryBranchList(session.getCountryId());
			for (CountryBranch countryBranch : lstcountryBranch) {
				countryBranchMap.put(countryBranch.getCountryBranchId(),countryBranch.getBranchName());
			}
			
			if(lstcountryBranch != null && lstcountryBranch.size() != 0){
				setCountryBranchList(lstcountryBranch);
			}
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
		}
	}
	
	public void fetchServiceMaster(){
		List<ServiceMasterDesc> lstservicemaster = null;
		try{
			serviceMap.clear();
			lstservicemaster = ratesUpdateService.getServiceMastersList(session.getLanguageId());
			for (ServiceMasterDesc serviceMasterDesc : lstservicemaster) {
				serviceMap.put(serviceMasterDesc.getServiceMaster().getServiceId(),	serviceMasterDesc.getLocalServiceDescription());
			}
			
			if(lstservicemaster != null && lstservicemaster.size() != 0){
				setServiceMasters(lstservicemaster);
			}
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
		}
	}

	public void exchangeRateDlgDisplay(){
		//set country branch Name
		
		List<CountryBranch> countryBranch = generalService.getCountryBranchLocCode(new BigDecimal(session.getBranchId()));
		if(countryBranch != null && countryBranch.size() != 0){
			CountryBranch branchName = countryBranch.get(0);
			setCountryBranchName(branchName.getBranchName());
		}
		
		clear();
		fetchCurrency();
		fetchCountry();
	}

	public void countryBasedOnCurrency(){
		System.out.println("getCurrencyId() :"+getCurrencyId());
		if(lstCountryBasedOnCurrency != null || !lstCountryBasedOnCurrency.isEmpty()){
			lstCountryBasedOnCurrency.clear();
		}
		if(ratesUpdateDataTable != null || !ratesUpdateDataTable.isEmpty()){
			ratesUpdateDataTable.clear();
		}
		
		List<ViewExchangeRateAppDetails> exchangeRateCurrencyWiseCountryList  = ratesUpdateService.getAllExchageRatesCountryBasedOnCurrency(getCurrencyId());
		
		//Map<BigDecimal, String> mapCountryList = new TreeMap<BigDecimal, String>();
		/*if(mapCountryList.isEmpty()){
			List<CountryMasterDesc> countryCodeslst = personalRemittanceService.getCountryList(session.getLanguageId());

			for (CountryMasterDesc countryCodes : countryCodeslst) {
				mapCountryList.put(countryCodes.getFsCountryMaster().getCountryId(), countryCodes.getFsCountryMaster().getCountryCode());
			}
		}*/

		for (Object object : exchangeRateCurrencyWiseCountryList) {
			Object[] li = (Object[])object;
			if(li.length>0)
			{
				if(li[0]!=null && li[1]!=null)
				{
					PopulateData lstRBankData = new PopulateData();
					lstRBankData.setPopulateId(new BigDecimal( li[0].toString()));
					lstRBankData.setPopulateCode(countryCodeMap.get(new BigDecimal(li[0].toString())));
					lstRBankData.setPopulateName(li[1].toString());
					lstCountryBasedOnCurrency.add(lstRBankData);
				}
			}
		}

	}



	public void viewInqForLocationWise(){
		callListofDetailsForInquiry();
		System.out.println("Session countr branch ID :"+session.getBranchId());
		ratesUpdateDataTable.clear();
		List<ViewExchangeRateAppDetails> exchangeRateForBranchList =  ratesUpdateService.getAllExchageRatesBasedOnCountryandCurrencyAndBrancWise(getCurrencyId(), getCountryId(), new BigDecimal(session.getBranchId()));
		if(null!=exchangeRateForBranchList &&exchangeRateForBranchList.size()>0){
			for(ViewExchangeRateAppDetails exRate : exchangeRateForBranchList){
				RatesUpdateBeanDataTable ratesDT=new RatesUpdateBeanDataTable();

				//ratesDT.setRatesUpdatePk(exRate.getExchangeRateMasterId());
				// END BY NAG 21/02/2015

				if(exRate.getCurrencyId()!=null){
					ratesDT.setCurrencyId(exRate.getCurrencyId());
					ratesDT.setCurrencyName(currencyMap.get(exRate.getCurrencyId()));
				}

				if(exRate.getCountryId()!=null){
					ratesDT.setCountryId(exRate.getCountryId());
					ratesDT.setCountryName(countryMap.get(exRate.getCountryId()));
				}

				if(exRate.getBankId()!=null){
					ratesDT.setBankId(exRate.getBankId());
					ratesDT.setBankName(bankMap.get(exRate.getBankId()));
				}

				if(exRate.getCountryBranchId()!=null){
					ratesDT.setBranchId(exRate.getCountryBranchId());
					ratesDT.setBranchName(countryBranchMap.get(exRate.getCountryBranchId()));
				}

				if(exRate.getServiceId()!=null){
					ratesDT.setServiceId(exRate.getServiceId());
					ratesDT.setServiceName(serviceMap.get(exRate.getServiceId()));
				}
				
				ratesDT.setAmount(exRate.getAmount());		

				if(exRate.getCountryBranchId()!=null){
					ratesDT.setBranchId(exRate.getCountryBranchId());
					ratesDT.setBranchName(countryBranchMap.get(exRate.getCountryBranchId()));
				}

				ratesDT.setSellingRateMin(exRate.getSellRateMin());
				ratesDT.setSellingRateMax(exRate.getSellRateMax());
				ratesDT.setBuyingRateMin(exRate.getBuyRateMin());
				ratesDT.setBuyingRateMax(exRate.getBuyRateMax());
				ratesDT.setCorporateRate(exRate.getCorporateRate());

				ratesUpdateDataTable.add(ratesDT);
			}
		}

	}

	public void view()
	{	
		try{
			setBooEnableDatatable(true);
			ratesUpdateDataTable.clear();
			
			if(getCurrencyId() == null && getCountryId() == null){
				setErrorMsg("Please select Currency or Country");
				RequestContext.getCurrentInstance().execute("csp.show();");
				return;
			}

			LOGGER.debug(getCurrencyId());
			LOGGER.debug(getCountryId());
			LOGGER.debug(getBranchId());

			List<ExchangeRateApprovalDetModel> ratesList =  ratesUpdateService.getAllExchageRatesBasedOnCountryandCurrencyandBranch(getCurrencyId(),getCountryId(),getBranchId());

			LOGGER.debug("Size------------------>"+ratesList.size());

			if(null!=ratesList &&ratesList.size()>0){
				for(ExchangeRateApprovalDetModel exRate : ratesList){
					RatesUpdateBeanDataTable ratesDT=new RatesUpdateBeanDataTable();

					ratesDT.setRatesUpdatePk(exRate.getExchangeRateMasterId());
					// END BY NAG 21/02/2015

					if(exRate.getCurrencyId()!=null){
						ratesDT.setCurrencyId(exRate.getCurrencyId());
						ratesDT.setCurrencyName(currencyMap.get(exRate.getCurrencyId()));
					}

					if(exRate.getCountryId()!=null){
						ratesDT.setCountryId(exRate.getCountryId());
						ratesDT.setCountryName(countryMap.get(exRate.getCountryId()));
					}

					if(exRate.getBankId()!=null){
						ratesDT.setBankId(exRate.getBankId());
						ratesDT.setBankName(bankMap.get(exRate.getBankId()));
					}

					if(exRate.getCountryBranchId()!=null){
						ratesDT.setBranchId(exRate.getCountryBranchId());
						ratesDT.setBranchName(countryBranchMap.get(exRate.getCountryBranchId()));
					}

					if(exRate.getServiceId()!=null){
						ratesDT.setServiceId(exRate.getServiceId());
						ratesDT.setServiceName(serviceMap.get(exRate.getServiceId()));
					}

					ratesDT.setSellingRateMin(exRate.getSellRateMin());
					ratesDT.setSellingRateMax(exRate.getSellRateMax());
					ratesDT.setBuyingRateMin(exRate.getBuyRateMin());
					ratesDT.setBuyingRateMax(exRate.getBuyRateMax());
					ratesDT.setCorporateRate(exRate.getCorporateRate());

					ratesDT.setPrvBuyMaxRate(exRate.getPrvBuyRateMax());
					ratesDT.setPrvBuyMinRate(exRate.getPrvBuyRateMin());
					ratesDT.setPrvSellMaxRate(exRate.getPrvSellRateMax());
					ratesDT.setPrvSellMinRate(exRate.getPrvSellRateMin());

					ratesDT.setCreatedBy(exRate.getCreatedBy());
					ratesDT.setCreatedDate(exRate.getCreatedDate());
					ratesDT.setIsActive(setRecordStatus(exRate.getIsActive()));

					ratesUpdateDataTable.add(ratesDT);
				}
			}
		}catch(NullPointerException  e){ 

			setErrorMsg("view :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		LOGGER.debug("ratesUpdateDataTable's Size------------------>"+ratesUpdateDataTable.size());


	}

	public void exchangeRateEnquiryPageNavigation() {
		setCurrencyId(null);
		setCountryId(null);
		setBranchId(null);
		callListofDetails();
		ratesUpdateDataTable.clear();
		setBooEnableDatatable(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "ExchangeRateEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/ExchangeRateEnquiry.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void callListofDetails(){
		try{
			fetchCurrency();
			fetchCountry();
			fetchBank();
			fetchServiceMaster();
			fetchCountryBranch();
		}catch(NullPointerException  e){ 
			setErrorMsg("callListofDetails :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	
	public void callListofDetailsForInquiry(){
		try{
			fetchBank();
			fetchServiceMaster();
			fetchCountryBranch();
		}catch(NullPointerException  e){ 
			setErrorMsg("callListofDetails :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void clear()
	{
		setCurrencyId(null);
		setCountryId(null);
		setBranchId(null);
		setBooEnableDatatable(false);
		
		if(lstCountryBasedOnCurrency != null || !lstCountryBasedOnCurrency.isEmpty()){
			lstCountryBasedOnCurrency.clear();
		}
		if(ratesUpdateDataTable != null || !ratesUpdateDataTable.isEmpty()){
			ratesUpdateDataTable.clear();
		}
	}

	public void clickOnExit() throws IOException{
		clear();
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else if (session.getRoleId().equalsIgnoreCase("2")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}else{
			FacesContext.getCurrentInstance() .getExternalContext().redirect("../common/saleshome.xhtml");
		}
	}


	private String setRecordStatus(String status) {

		String strStatus = null;

		if (status.equalsIgnoreCase(Constants.U)) {
			strStatus="Un_ Approve";
		}
		if (status.equalsIgnoreCase(Constants.D)) {

			strStatus = "Deleted";
		}
		if (status.equalsIgnoreCase(Constants.Yes)) {
			strStatus = "Activated";
		}
		return strStatus;

	}

	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public List<ViewExchangeRateAppDetails> getExchangeRateForBranchList() {
		return exchangeRateForBranchList;
	}
	public void setExchangeRateForBranchList(List<ViewExchangeRateAppDetails> exchangeRateForBranchList) {
		this.exchangeRateForBranchList = exchangeRateForBranchList;
	}

	public List<PopulateData> getLstCountryBasedOnCurrency() {
		return lstCountryBasedOnCurrency;
	}
	public void setLstCountryBasedOnCurrency(List<PopulateData> lstCountryBasedOnCurrency) {
		this.lstCountryBasedOnCurrency = lstCountryBasedOnCurrency;
	}

	public Map<BigDecimal, String> getCurrencywisecountryMap() {
		return currencywisecountryMap;
	}
	public void setCurrencywisecountryMap(Map<BigDecimal, String> currencywisecountryMap) {
		this.currencywisecountryMap = currencywisecountryMap;
	}
	
	
}
