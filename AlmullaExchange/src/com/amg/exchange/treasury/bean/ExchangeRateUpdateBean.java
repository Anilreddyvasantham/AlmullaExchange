package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cashtransfer.service.ICashTransferLToLService;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.ICurrencyService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.ExchangeRate;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.IRatesUpdateService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;

@Component("exchangeRateUpdateBean")
@Scope("session")
public class ExchangeRateUpdateBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(ExchangeRateUpdateBean.class);
	
	private SessionStateManage session = new SessionStateManage();

	private String localErrorMsg = null;
	private String errorMessage;


	private List<CountryMasterDesc> countryList = new ArrayList<CountryMasterDesc>();
	private List<CurrencyMaster> currencyList = new ArrayList<CurrencyMaster>();
	private List<BankMaster> bankList = new ArrayList<BankMaster>();
	private List<CountryBranch> countryBranchList = new ArrayList<CountryBranch>();
	private List<ServiceMasterDesc> serviceMasters = new ArrayList<ServiceMasterDesc>();
	private List<RatesUpdateBeanDataTable> ratesUpdateDataTableList = new ArrayList<RatesUpdateBeanDataTable>();
	private List<RatesUpdateBeanDataTable> deleteExchangeRate = new ArrayList<RatesUpdateBeanDataTable>();
	//private List<RatesUpdateBeanDataTable> echangeRateList = new ArrayList<RatesUpdateBeanDataTable>();

	private Map<BigDecimal, String> countryMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> currencyMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> bankMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> countryBranchMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> serviceMap = new HashMap<BigDecimal, String>();
	

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;

	@Autowired
	IRatesUpdateService<T> ratesUpdateService;
	
	@Autowired
	ICashTransferLToLService cashTransferLToLService;
	
	@Autowired
	ICurrencyService currencyService;
	
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;


	public List<RatesUpdateBeanDataTable> getDeleteExchangeRate() {
		return deleteExchangeRate;
	}
	public void setDeleteExchangeRate(List<RatesUpdateBeanDataTable> deleteExchangeRate) {
		this.deleteExchangeRate = deleteExchangeRate;
	}

	public String getLocalErrorMsg() {
		return localErrorMsg;
	}
	public void setLocalErrorMsg(String localErrorMsg) {
		this.localErrorMsg = localErrorMsg;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void fetchCountry(){
		List<CountryMasterDesc> lstcountry=null;
		try{
			lstcountry = generalService.getCountryList(session.getLanguageId());
			for (CountryMasterDesc countryMasterDesc : lstcountry) {
				countryMap.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
			}
			
			if(lstcountry != null && lstcountry.size() != 0){
				setCountryList(lstcountry);
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());      
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
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
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
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
		}
	}
	
	public void fetchBank(RatesUpdateBeanDataTable ratedetails){
		List<BankMaster> correspondingBankList=null;
		try{
			if(ratedetails.getCountryId() != null){
				correspondingBankList=generalService.getCorrespondingBanks(ratedetails.getCountryId());
				bankMap.clear();
				for(BankMaster bankApp:correspondingBankList){
					bankMap.put(bankApp.getBankId(), bankApp.getBankFullName());
				}
				
				if(correspondingBankList != null && correspondingBankList.size() != 0){
					ratedetails.setBankList(correspondingBankList);
				}
			}else{
				ratedetails.setBankList(null);
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::fetchBank"+ne.getMessage());
			RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
			return; 
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage("Method Name::fetchBank"+exception.getMessage());
			RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
			return;       
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
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
		}
	}

	public void allListOfData(){
		fetchCurrency();
		fetchCountry();
		fetchCountryBranch();
		fetchServiceMaster();
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

	public List<RatesUpdateBeanDataTable> getRatesUpdateDataTableList() {
		return ratesUpdateDataTableList;
	}
	public void setRatesUpdateDataTableList(List<RatesUpdateBeanDataTable> ratesUpdateDataTableList) {
		this.ratesUpdateDataTableList = ratesUpdateDataTableList;
	}
	
	public List<ServiceMasterDesc> getServiceMasters() {
		return serviceMasters;
	}
	public void setServiceMasters(List<ServiceMasterDesc> serviceMasters) {
		this.serviceMasters = serviceMasters;
	}
	
	
	public void ratesUpdatePageNavigation() {
		ratesUpdateDataTableList.clear();
		setErrorMessage(null);
		allListOfData();
		view();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "ExchangeRateUpdate.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../remittance/ExchangeRateUpdate.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeRecord(RatesUpdateBeanDataTable rateUpdateObj) throws Exception{

		if(deleteExchangeRate != null && !deleteExchangeRate.isEmpty()){
			deleteExchangeRate.clear();
		}

		deleteExchangeRate.add(rateUpdateObj);

		if(deleteExchangeRate.size() == 1){
			// call dialogue box to confirm
			setErrorMessage("Do you want to delete Exchange Rate?");
			RequestContext.getCurrentInstance().execute("deleteExchangeRate.show();");
		}

	}

	public void deactiveExchangeRate(){
		setErrorMessage(null);
		if(deleteExchangeRate.size() == 1){
			RatesUpdateBeanDataTable rateUpdateObj = deleteExchangeRate.get(0);
			if(rateUpdateObj.getRatesUpdatePk()==null){	
				ratesUpdateDataTableList.remove(rateUpdateObj);
			}else{
				//deleteRecord(rateUpdateObj);
				deleteRecordExchangeRec(rateUpdateObj.getRatesUpdatePk());
				ratesUpdateDataTableList.remove(rateUpdateObj);
			}
		}
	}
	
	// delete permenantely in temp table
	public void deleteRecordExchangeRec(BigDecimal rateUpdateObj){
		ratesUpdateService.deleteExchangeRecord(rateUpdateObj);
	}

	public void saveDataTableRecods() {

		if (ratesUpdateDataTableList.isEmpty()) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		} else {
			try{
				
				boolean checkRatedataTable = true;
				boolean checkCurrencydataTable = true;

				for (RatesUpdateBeanDataTable checknullrecords : ratesUpdateDataTableList) {
					
					if(checknullrecords.getCurrencyId() == null){
						localErrorMsg="Please enter currency in Data table";
						setLocalErrorMsg(localErrorMsg);
						checkCurrencydataTable = false;
						break;
					}

					if(checknullrecords.getSellingRateMax()==null || checknullrecords.getSellingRateMin()==null || checknullrecords.getBuyingRateMax()==null || checknullrecords.getBuyingRateMin()==null){
						localErrorMsg="Please Enter Sell Rate  and  Buy Rate in Data table";
						setLocalErrorMsg(localErrorMsg);
						checkRatedataTable = false;
						break;
					}

				}
				
				// duplicate records check
				boolean checkDup = checkingDTandDB();
				if(checkDup){
					setErrorMessage("Duplicate record available, Please delete the duplicate record");
					RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
					return;
				}
				
				// Same currency, country check
				HashMap<String, Object> checkDup1 = checkCurrencyCountry();
				if(checkDup1.get("Status") != null){
					BigDecimal currencyId = null;
					String currencyName = null;
					BigDecimal countryId = null;
					String countryName = null;
					BigDecimal bankId = null;
					String bankName = null;
					BigDecimal serviceId = null;
					String serviceName = null;
					BigDecimal branchId = null;
					String branchName = null;
					Boolean status = (Boolean) checkDup1.get("Status");
					if(checkDup1.get("CurrencyId") != null){
						currencyId = (BigDecimal) checkDup1.get("CurrencyId");
					}
					if(checkDup1.get("CountryId") != null){
						countryId = (BigDecimal) checkDup1.get("CountryId");
					}
					if(checkDup1.get("BankId") != null){
						bankId = (BigDecimal) checkDup1.get("BankId");
					}
					if(checkDup1.get("ServiceId") != null){
						serviceId = (BigDecimal) checkDup1.get("ServiceId");
					}
					if(checkDup1.get("BranchId") != null){
						branchId = (BigDecimal) checkDup1.get("BranchId");
					}
					
					if(status){
						
						if(currencyMap != null && currencyMap.size() != 0 && currencyId != null){
							currencyName = currencyMap.get(currencyId);
						}
						
						if(countryMap != null && countryMap.size() != 0 && countryId != null){
							countryName = countryMap.get(countryId);
						}
						
						if(bankMap != null && bankMap.size() != 0 && bankId != null){
							bankName = bankMap.get(bankId);
						}
						
						if(serviceMap != null && serviceMap.size() != 0 && serviceId != null){
							serviceName = serviceMap.get(serviceId);
						}
						
						if(countryBranchMap != null && countryBranchMap.size() != 0 && branchId != null){
							branchName = countryBranchMap.get(branchId);
						}
						
						String combination = "";
						combination = combination == "" ? combination.concat(currencyName == null ? "" : currencyName) : combination.concat(" / ").concat(currencyName == null ? "" : currencyName);
						combination = countryName == null ? combination.concat(countryName == null ? "" : countryName) : combination.concat(" / ").concat(countryName == null ? "" : countryName);
						combination = bankName == null ? combination.concat(bankName == null ? "" : bankName) : combination.concat(" / ").concat(bankName == null ? "" : bankName);
						combination = serviceName == null ? combination.concat(serviceName == null ? "" : serviceName) : combination.concat(" / ").concat(serviceName == null ? "" : serviceName);
						combination = branchName == null ? combination.concat(branchName == null ? "" : branchName) : combination.concat(" / ").concat(branchName == null ? "" : branchName);
												
						setErrorMessage("Exchange Rate is already specified for : " +combination);
						RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
						return;
					}
				}

				if(checkCurrencydataTable){
					if(checkRatedataTable){
						for (RatesUpdateBeanDataTable ratesUpdateObj : ratesUpdateDataTableList) {

							ExchangeRate exchageRateObj = new ExchangeRate();

							exchageRateObj.setExchangeRateMasterId(ratesUpdateObj.getRatesUpdatePk());

							CountryMaster appcountryMaster = new CountryMaster();
							appcountryMaster.setCountryId(session.getCountryId());
							exchageRateObj.setApplicationCountryId(appcountryMaster);


							if(ratesUpdateObj.getCountryId()!=null){
								CountryMaster countryMaster = new CountryMaster();
								countryMaster.setCountryId(ratesUpdateObj.getCountryId());
								exchageRateObj.setCountryId(countryMaster);
							}

							if(ratesUpdateObj.getCurrencyId()!=null){
								CurrencyMaster currencyMaster = new CurrencyMaster();
								currencyMaster.setCurrencyId(ratesUpdateObj.getCurrencyId());
								exchageRateObj.setCurrencyId(currencyMaster);
							}

							if(ratesUpdateObj.getBranchId()!=null){
								CountryBranch countryBranch = new CountryBranch();
								countryBranch.setCountryBranchId(ratesUpdateObj.getBranchId());
								exchageRateObj.setCountryBranchId(countryBranch);
							}

							if(ratesUpdateObj.getBankId()!=null){
								BankMaster bankMaster = new BankMaster();
								bankMaster.setBankId(ratesUpdateObj.getBankId());
								exchageRateObj.setBankMaster(bankMaster);
							}

							if(ratesUpdateObj.getServiceId()!=null){
								ServiceMaster serviceMasters = new ServiceMaster();
								serviceMasters.setServiceId(ratesUpdateObj.getServiceId());
								exchageRateObj.setServiceId(serviceMasters);
							}

							exchageRateObj.setSellrateMax(ratesUpdateObj.getSellingRateMax());
							exchageRateObj.setSellrateMin(ratesUpdateObj.getSellingRateMin());
							exchageRateObj.setBuyrateMax(ratesUpdateObj.getBuyingRateMax());
							exchageRateObj.setBuyrateMin(ratesUpdateObj.getBuyingRateMin());
							exchageRateObj.setCorporateRate(ratesUpdateObj.getCorporateRate());

							if(ratesUpdateObj.getRatesUpdatePk()!=null){
								exchageRateObj.setModifiedBy(session.getUserName());
								exchageRateObj.setModifiedDate(new Date());
								exchageRateObj.setCreatedBy(ratesUpdateObj.getCreatedBy());
								exchageRateObj.setCreatedDate(ratesUpdateObj.getCreatedDate());
							}else{
								exchageRateObj.setCreatedBy(session.getUserName());
								exchageRateObj.setCreatedDate(new Date());
							}

							exchageRateObj.setRecStatus(Constants.U);

							if(ratesUpdateObj.getSellingRateMax()!=null && ratesUpdateObj.getSellingRateMin()!=null && ratesUpdateObj.getBuyingRateMax()!=null && ratesUpdateObj.getBuyingRateMin()!=null){
								ratesUpdateService.saveRecord(exchageRateObj);
							}

						}
						RequestContext.getCurrentInstance().execute("complete.show();");
					}else{
						RequestContext.getCurrentInstance().execute("dataTableNull1.show();");
					}
				}else{
					RequestContext.getCurrentInstance().execute("dataTableNull1.show();");
				}
				

			}catch(NullPointerException ne){
				log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::saveDataTableRecods"+ne.getMessage());
				RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
				return; 	  
			}catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage("Method Name::saveDataTableRecods"+exception.getMessage());
				RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
				return;   	  
			}
		}
	}

	public void clickOnOKSave() throws Exception{
		ratesUpdateDataTableList.clear();
		setErrorMessage(null);
		view();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../remittance/ExchangeRateUpdate.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void exit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/branchhome.xhtml");
		}
	}

	

	public void view(){
		try{
			List<RatesUpdateBeanDataTable> echangeRateList = new ArrayList<RatesUpdateBeanDataTable>();
			
			List<ExchangeRate> ratesList = ratesUpdateService.getAllExchageRates();
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++"+ratesList.size());
			if(ratesList.size()>0){
				for(ExchangeRate exRate:ratesList){
					
					RatesUpdateBeanDataTable ratesDT=new RatesUpdateBeanDataTable();

					ratesDT.setRatesUpdatePk(exRate.getExchangeRateMasterId());
					// END BY NAG 21/02/2015

					if(exRate.getCurrencyId()!=null){
						ratesDT.setCurrencyId(exRate.getCurrencyId().getCurrencyId());
						ratesDT.setCurrencyName(currencyMap.get(exRate.getCurrencyId().getCurrencyId()));
					}

					if(exRate.getCountryId()!=null){
						ratesDT.setCountryId(exRate.getCountryId().getCountryId());
						ratesDT.setCountryName(countryMap.get(exRate.getCountryId().getCountryId()));
						
						// fetch banks
						fetchBank(ratesDT);
					}

					if(exRate.getBankMaster()!=null){
						ratesDT.setBankId(exRate.getBankMaster().getBankId());
						if(bankMap != null && bankMap.size() != 0){
							ratesDT.setBankName(bankMap.get(exRate.getBankMaster().getBankId()));
						}else{
							ratesDT.setBankName(generalService.getBankName(exRate.getBankMaster().getBankId()));
						}
					}

					if(exRate.getCountryBranchId()!=null){
						ratesDT.setBranchId(exRate.getCountryBranchId().getCountryBranchId());
						if(countryBranchMap != null && countryBranchMap.size() != 0){
							ratesDT.setBranchName(countryBranchMap.get(exRate.getCountryBranchId().getCountryBranchId()));
						}else{
							ratesDT.setBranchName(cashTransferLToLService.getBranchName(exRate.getCountryBranchId().getCountryBranchId()).get(0).getBranchName());
						}
						
					}

					if(exRate.getServiceId()!=null){
						ratesDT.setServiceId(exRate.getServiceId().getServiceId());
						if(serviceMap != null && serviceMap.size() != 0){
							ratesDT.setServiceName(serviceMap.get(exRate.getServiceId().getServiceId()));
						}else{
							ratesDT.setServiceName(generalService.getServiceDesc(session.getLanguageId(), exRate.getServiceId().getServiceId()).get(0).getLocalServiceDescription());
						}
						
					}

					ratesDT.setSellingRateMin(exRate.getSellrateMin());
					ratesDT.setSellingRateMax(exRate.getSellrateMax());
					ratesDT.setBuyingRateMin(exRate.getBuyrateMin());
					ratesDT.setBuyingRateMax(exRate.getBuyrateMax());
					ratesDT.setCorporateRate(exRate.getCorporateRate());
					ratesDT.setCreatedBy(exRate.getCreatedBy());
					ratesDT.setCreatedDate(exRate.getCreatedDate());
					
					ratesDT.setCurrencyList(getCurrencyList());
					ratesDT.setCountryList(getCountryList());
					ratesDT.setServiceMasters(getServiceMasters());
					ratesDT.setCountryBranchList(getCountryBranchList());

					echangeRateList.add(ratesDT);
				}
			}
			
			if(echangeRateList.size()>0){
				ratesUpdateDataTableList.addAll(echangeRateList); 
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::view"+ne.getMessage());
			RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage("Method Name::view"+exception.getMessage());
			RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
			return;       
		}

	}

	public void deleteRecord(RatesUpdateBeanDataTable ratesUpdateDt){
		
		try{
			
			ExchangeRate exchageRateObj = new ExchangeRate();

			exchageRateObj.setExchangeRateMasterId(ratesUpdateDt.getRatesUpdatePk());

			if(ratesUpdateDt.getAppcountryId() != null){
				CountryMaster appcountryMaster = new CountryMaster();
				appcountryMaster.setCountryId(ratesUpdateDt.getAppcountryId());
				exchageRateObj.setApplicationCountryId(appcountryMaster);
			}

			if(ratesUpdateDt.getCountryId() != null){
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(ratesUpdateDt.getCountryId());
				exchageRateObj.setCountryId(countryMaster);
			}

			if(ratesUpdateDt.getCurrencyId() != null){
				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(ratesUpdateDt.getCurrencyId());
				exchageRateObj.setCurrencyId(currencyMaster);
			}

			if(ratesUpdateDt.getBranchId() != null){
				CountryBranch countryBranch = new CountryBranch();
				countryBranch.setCountryBranchId(ratesUpdateDt.getBranchId());
				exchageRateObj.setCountryBranchId(countryBranch);
			}

			if(ratesUpdateDt.getBankId() != null){
				BankMaster bankMaster = new BankMaster();
				bankMaster.setBankId(ratesUpdateDt.getBankId());
				exchageRateObj.setBankMaster(bankMaster);
			}
			
			if(ratesUpdateDt.getServiceId() != null){
				ServiceMaster serviceMasters = new ServiceMaster();
				serviceMasters.setServiceId(ratesUpdateDt.getServiceId());
				exchageRateObj.setServiceId(serviceMasters);
			}
			
			if(ratesUpdateDt.getRemittanceId() != null){
				RemittanceModeMaster remittanceModeMaster = new RemittanceModeMaster();
				remittanceModeMaster.setRemittanceModeId(ratesUpdateDt.getRemittanceId());
				exchageRateObj.setRemitanceMode(remittanceModeMaster);
			}
			
			if(ratesUpdateDt.getDeliveryId() != null){
				DeliveryMode deliveryMode = new DeliveryMode();
				deliveryMode.setDeliveryModeId(ratesUpdateDt.getDeliveryId());
				exchageRateObj.setDeliveryMode(deliveryMode);
			}

			exchageRateObj.setSellrateMax(ratesUpdateDt.getSellingRateMax());
			exchageRateObj.setSellrateMin(ratesUpdateDt.getSellingRateMin());
			exchageRateObj.setBuyrateMax(ratesUpdateDt.getBuyingRateMax());
			exchageRateObj.setBuyrateMin(ratesUpdateDt.getBuyingRateMin());
			exchageRateObj.setCorporateRate(ratesUpdateDt.getCorporateRate());
			
			exchageRateObj.setCreatedBy(ratesUpdateDt.getCreatedBy());
			exchageRateObj.setCreatedDate(ratesUpdateDt.getCreatedDate());
			
			exchageRateObj.setModifiedBy(session.getUserName());
			exchageRateObj.setModifiedDate(new Date());
			
			exchageRateObj.setRecStatus(Constants.D);

			ratesUpdateService.saveRecord(exchageRateObj);
			
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::deleteRecord"+ne.getMessage());
			RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage("Method Name::deleteRecord"+exception.getMessage());
			RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
			return;   
		}

	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if(newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void checkSellRateMinMax(RatesUpdateBeanDataTable rateUpdateMinMax){
		if(rateUpdateMinMax.getCurrencyId() != null){
			if(rateUpdateMinMax.getSellingRateMin() != null && rateUpdateMinMax.getSellingRateMin().compareTo(BigDecimal.ZERO) != 0 && rateUpdateMinMax.getSellingRateMax() != null && rateUpdateMinMax.getSellingRateMax().compareTo(BigDecimal.ZERO) != 0){
				
				boolean checkStatus = checkSellRateMinMaxWithExchange(rateUpdateMinMax);
				
				if(!checkStatus){
					if(rateUpdateMinMax.getSellingRateMin().compareTo(rateUpdateMinMax.getSellingRateMax())>0){
						rateUpdateMinMax.setSellingRateMax(null);
						setErrorMessage(WarningHandler.showWarningMessage("lbl.sellratemaximumshouldbegreaterthansellrateminimum", session.getLanguageId()));
						RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
					}
				}
			}
		}else{
			rateUpdateMinMax.setSellingRateMin(null);
			rateUpdateMinMax.setSellingRateMax(null);
			setErrorMessage("Please select currency");
			RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
		}
	}

	public void checkBuyRateMinMax(RatesUpdateBeanDataTable rateUpdateMinMax){
		if(rateUpdateMinMax.getBuyingRateMin() != null && rateUpdateMinMax.getBuyingRateMin().compareTo(BigDecimal.ZERO) != 0 && rateUpdateMinMax.getBuyingRateMax() != null && rateUpdateMinMax.getBuyingRateMax().compareTo(BigDecimal.ZERO) != 0 ){
			if(rateUpdateMinMax.getBuyingRateMin().compareTo(rateUpdateMinMax.getBuyingRateMax())>0){
				rateUpdateMinMax.setBuyingRateMax(null);
				setErrorMessage(WarningHandler.showWarningMessage("lbl.buyratemaximumshouldbegreaterthanbuyrateminimum", session.getLanguageId()));
				RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
			}
		}
	}
	
	public boolean checkSellRateMinMaxWithExchange(RatesUpdateBeanDataTable rateUpdateMinMax){

		BigDecimal sellMinRate = BigDecimal.ZERO;
		BigDecimal sellMaxRate = BigDecimal.ZERO;
		boolean checkStatus = false;

		List<CurrencyOtherInformation> currencyOther = currencyService.searchByCurrencyId(rateUpdateMinMax.getCurrencyId());
		if(currencyOther != null && currencyOther.size() != 0){
			CurrencyOtherInformation currencyOtherinfo = currencyOther.get(0);
			sellMinRate = currencyOtherinfo.getFundMinRate();
			sellMaxRate = currencyOtherinfo.getFundMaxRate();

			if(sellMinRate != null && sellMinRate.compareTo(BigDecimal.ZERO) != 0 && sellMaxRate != null && sellMaxRate.compareTo(BigDecimal.ZERO) != 0){
				if(rateUpdateMinMax.getSellingRateMin().compareTo(sellMinRate) < 0){
					checkStatus = true;
					rateUpdateMinMax.setSellingRateMin(null);

					setErrorMessage("Sell rate must be between Currency Master fund minimum : " + sellMinRate + " and fund maximum : " + sellMaxRate);
					RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
				}else if(rateUpdateMinMax.getSellingRateMax().compareTo(sellMaxRate) > 0){
					checkStatus = true;
					rateUpdateMinMax.setSellingRateMax(null);

					setErrorMessage("Sell rate must be between Currency Master fund minimum : " + sellMinRate + " and fund maximum : " + sellMaxRate);
					RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
				}else{
					checkStatus = false;
				}
			}
		}

		return checkStatus;

	}
	
	public void addEmptyRow()
	{
		// duplicate records check
		boolean checkDup = checkingDTandDB();
		if(checkDup){
			setErrorMessage("Duplicate record available, Please delete the duplicate record");
			RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
			return;
		}
		
		// Same currency, country check
		HashMap<String, Object> checkDup1 = checkCurrencyCountry();
		if(checkDup1.get("Status") != null){
			BigDecimal currencyId = null;
			String currencyName = null;
			BigDecimal countryId = null;
			String countryName = null;
			BigDecimal bankId = null;
			String bankName = null;
			BigDecimal serviceId = null;
			String serviceName = null;
			BigDecimal branchId = null;
			String branchName = null;
			Boolean status = (Boolean) checkDup1.get("Status");
			if(checkDup1.get("CurrencyId") != null){
				currencyId = (BigDecimal) checkDup1.get("CurrencyId");
			}
			if(checkDup1.get("CountryId") != null){
				countryId = (BigDecimal) checkDup1.get("CountryId");
			}
			if(checkDup1.get("BankId") != null){
				bankId = (BigDecimal) checkDup1.get("BankId");
			}
			if(checkDup1.get("ServiceId") != null){
				serviceId = (BigDecimal) checkDup1.get("ServiceId");
			}
			if(checkDup1.get("BranchId") != null){
				branchId = (BigDecimal) checkDup1.get("BranchId");
			}
			
			if(status){
				
				if(currencyMap != null && currencyMap.size() != 0 && currencyId != null){
					currencyName = currencyMap.get(currencyId);
				}
				
				if(countryMap != null && countryMap.size() != 0 && countryId != null){
					countryName = countryMap.get(countryId);
				}
				
				if(bankMap != null && bankMap.size() != 0 && bankId != null){
					bankName = bankMap.get(bankId);
				}
				
				if(serviceMap != null && serviceMap.size() != 0 && serviceId != null){
					serviceName = serviceMap.get(serviceId);
				}
				
				if(countryBranchMap != null && countryBranchMap.size() != 0 && branchId != null){
					branchName = countryBranchMap.get(branchId);
				}
				
				String combination = "";
				combination = combination == "" ? combination.concat(currencyName == null ? "" : currencyName) : combination.concat(" / ").concat(currencyName == null ? "" : currencyName);
				combination = countryName == null ? combination.concat(countryName == null ? "" : countryName) : combination.concat(" / ").concat(countryName == null ? "" : countryName);
				combination = bankName == null ? combination.concat(bankName == null ? "" : bankName) : combination.concat(" / ").concat(bankName == null ? "" : bankName);
				combination = serviceName == null ? combination.concat(serviceName == null ? "" : serviceName) : combination.concat(" / ").concat(serviceName == null ? "" : serviceName);
				combination = branchName == null ? combination.concat(branchName == null ? "" : branchName) : combination.concat(" / ").concat(branchName == null ? "" : branchName);
										
				setErrorMessage("Rate is already specified for : " +combination);
				RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
				return;
			}
		}
		
		// need to add on row
		addDataTable();
	}
	
	public void addDataTable(){

		RatesUpdateBeanDataTable rateUpdateDtObj = new RatesUpdateBeanDataTable();
		// ADDED BY NAG 21/02/2015
		rateUpdateDtObj.setRatesUpdatePk(null);
		// END BY NAG 21/02/2015
		rateUpdateDtObj.setCountryId(null);
		rateUpdateDtObj.setCountryName(null);
		rateUpdateDtObj.setCurrencyId(null);
		rateUpdateDtObj.setCurrencyName(null);
		rateUpdateDtObj.setBankId(null);
		rateUpdateDtObj.setBankName(null);
		rateUpdateDtObj.setServiceId(null);
		rateUpdateDtObj.setServiceName(null);
		rateUpdateDtObj.setBranchId(null);
		rateUpdateDtObj.setBranchName(null);
		rateUpdateDtObj.setSellingRateMin(null);
		rateUpdateDtObj.setSellingRateMax(null);
		rateUpdateDtObj.setBuyingRateMin(null);
		rateUpdateDtObj.setBuyingRateMax(null);
		rateUpdateDtObj.setCorporateRate(null);
		
		rateUpdateDtObj.setCurrencyList(getCurrencyList());
		rateUpdateDtObj.setCountryList(getCountryList());
		rateUpdateDtObj.setServiceMasters(getServiceMasters());
		rateUpdateDtObj.setCountryBranchList(getCountryBranchList());
		
		ratesUpdateDataTableList.add(rateUpdateDtObj);
	
	}
	
	public boolean checkingDTandDB(){

		boolean checkDup = false; 
		
		List<RatesUpdateBeanDataTable> ratesUpdateDtRm = new ArrayList<RatesUpdateBeanDataTable>();
		
		for(RatesUpdateBeanDataTable ratesupdate : ratesUpdateDataTableList){

			ratesUpdateDtRm.clear();
			ratesUpdateDtRm.addAll(ratesUpdateDataTableList);
			ratesUpdateDtRm.remove(ratesupdate);
			
			for(RatesUpdateBeanDataTable ratesupdate1 : ratesUpdateDtRm){
				if((ratesupdate1.getCurrencyId() == null && ratesupdate.getCurrencyId() == null) || (ratesupdate1.getCurrencyId() != null && ratesupdate.getCurrencyId() != null && ratesupdate1.getCurrencyId().compareTo(ratesupdate.getCurrencyId()) == 0 )){
					if((ratesupdate1.getCountryId() == null && ratesupdate.getCountryId() == null) || (ratesupdate1.getCountryId() != null && ratesupdate.getCountryId() != null && ratesupdate1.getCountryId().compareTo(ratesupdate.getCountryId()) == 0 )){
						if((ratesupdate1.getBankId() == null && ratesupdate.getBankId() == null) || (ratesupdate1.getBankId() != null && ratesupdate.getBankId() != null && ratesupdate1.getBankId().compareTo(ratesupdate.getBankId()) == 0 )){
							if((ratesupdate1.getServiceId() == null && ratesupdate.getServiceId() == null) || (ratesupdate1.getServiceId() != null && ratesupdate.getServiceId() != null && ratesupdate1.getServiceId().compareTo(ratesupdate.getServiceId()) == 0 )){
								if((ratesupdate1.getBranchId() == null && ratesupdate.getBranchId() == null) || (ratesupdate1.getBranchId() != null && ratesupdate.getBranchId() != null && ratesupdate1.getBranchId().compareTo(ratesupdate.getBranchId()) == 0 )){
									checkDup = true;
									break;
								}else{
									checkDup = false; 
								}
							}else{
								checkDup = false; 
							}
						}else{
							checkDup = false; 
						}
					}else{
						checkDup = false; 
					}
				}else{
					checkDup = false; 
				}
			}
			
			if(checkDup){
				break;
			}
		}

		return checkDup;
	}
	
	// checking currency and country check
	public HashMap<String, Object> checkCurrencyCountry(){
		
		boolean checkDup = false;
		List<RatesUpdateBeanDataTable> ratesUpdateDtRm = new ArrayList<RatesUpdateBeanDataTable>();
		BigDecimal currencyId = null;
		BigDecimal countryId = null;
		BigDecimal bankId = null;
		BigDecimal serviceId = null;
		BigDecimal branchId = null;
		HashMap<String, Object> lstCheck = new HashMap<String, Object>();
	
		for(RatesUpdateBeanDataTable ratesupdate : ratesUpdateDataTableList){

			ratesUpdateDtRm.clear();
			ratesUpdateDtRm.addAll(ratesUpdateDataTableList);
			ratesUpdateDtRm.remove(ratesupdate);
			
			for(RatesUpdateBeanDataTable ratesupdate1 : ratesUpdateDtRm){
				if((ratesupdate1.getCurrencyId() == null && ratesupdate.getCurrencyId() == null) || (ratesupdate1.getCurrencyId() != null && ratesupdate.getCurrencyId() != null && ratesupdate1.getCurrencyId().compareTo(ratesupdate.getCurrencyId()) == 0 )){
					if(ratesupdate.getCountryId() == null ? (ratesupdate1.getCountryId() == null ? true : false) : (ratesupdate1.getCountryId() != null ? true : false)){
						if(ratesupdate.getBankId() == null ? (ratesupdate1.getBankId() == null ? true : false) :  (ratesupdate1.getBankId() != null ? true : false)){
							if(ratesupdate.getServiceId() == null ? (ratesupdate1.getServiceId() == null ? true : false) :  (ratesupdate1.getServiceId() != null ? true : false)){
								if(ratesupdate.getBranchId() == null ? (ratesupdate1.getBranchId() == null ? true : false) :  (ratesupdate1.getBranchId() != null ? true : false)){
									checkDup = false;
								}else{
									// break
									checkDup = true;
									currencyId = ratesupdate.getCurrencyId();
									countryId = ratesupdate.getCountryId();
									bankId = ratesupdate.getBankId();
									serviceId = ratesupdate.getServiceId();
									branchId = ratesupdate.getBranchId();
									break;
								}
							}else{
								// break
								checkDup = true;
								currencyId = ratesupdate.getCurrencyId();
								countryId = ratesupdate.getCountryId();
								bankId = ratesupdate.getBankId();
								serviceId = ratesupdate.getServiceId();
								branchId = ratesupdate.getBranchId();
								break;
							}
						}else{
							// break
							checkDup = true;
							currencyId = ratesupdate.getCurrencyId();
							countryId = ratesupdate.getCountryId();
							bankId = ratesupdate.getBankId();
							serviceId = ratesupdate.getServiceId();
							branchId = ratesupdate.getBranchId();
							break;
						}
					}else{
						// break
						checkDup = true;
						currencyId = ratesupdate.getCurrencyId();
						countryId = ratesupdate.getCountryId();
						bankId = ratesupdate.getBankId();
						serviceId = ratesupdate.getServiceId();
						branchId = ratesupdate.getBranchId();
						break;
					}
				}
			}
			
			if(checkDup){
				break;
			}
		}
		
		lstCheck.put("CurrencyId", currencyId);
		lstCheck.put("CountryId", countryId);
		lstCheck.put("BankId", bankId);
		lstCheck.put("ServiceId", serviceId);
		lstCheck.put("BranchId", branchId);
		lstCheck.put("Status", checkDup);
		
		return lstCheck; 
		
	}
	
	// clear error message
	public void clearErrMsg(){
		setErrorMessage(null);
	}


}
