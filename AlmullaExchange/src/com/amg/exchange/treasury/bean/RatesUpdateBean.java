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
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.ExchangeRate;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.IRatesUpdateService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;

/*
 * Author Rahamathali Shaik
 */

@Component("ratesUpdate")
@Scope("session")
public class RatesUpdateBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(RatesUpdateBean.class);

	private BigDecimal countryId = null;
	private BigDecimal currencyId = null;
	private BigDecimal bankId = null;
	private BigDecimal serviceId = null;
	private BigDecimal branchId = null;
	private BigDecimal sellingRateMin = null;
	private BigDecimal sellingRateMax = null;
	private BigDecimal buyingRateMin = null;
	private BigDecimal buyingRateMax = null;
	private BigDecimal corporateRate = null;
	private Boolean booRenderSaveExit = false;
	private Boolean booRenderDataTable = false;
	//private BigDecimal remittanceId = null;
	//private BigDecimal deliveryId = null;
	// Nag added 21/02/2015
	private BigDecimal ratesUpdatePk = null;
	private String localErrorMsg = null;

	private List<CountryMasterDesc> countryList = new ArrayList<CountryMasterDesc>();
	private List<CurrencyMaster> currencyList = new ArrayList<CurrencyMaster>();
	private List<BankMaster> bankList = new ArrayList<BankMaster>();
	private List<CountryBranch> countryBranchList = new ArrayList<CountryBranch>();
	private List<ServiceMasterDesc> serviceMasters = new ArrayList<ServiceMasterDesc>();
	private List<DeliveryMode> deliveryModeList = new ArrayList<DeliveryMode>();
	private List<DeliveryModeDesc> listDelivryDesc = new ArrayList<>();
	private List<RemittanceModeDescription> remittanceModeList = new ArrayList<RemittanceModeDescription>();
	private List<RatesUpdateBeanDataTable> ratesUpdateDataTableList = new ArrayList<RatesUpdateBeanDataTable>();
	private SessionStateManage session = new SessionStateManage();

	private Map<BigDecimal, String> countryMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> currencyMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> bankMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> countryBranchMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> serviceMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> deliveryMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> remittanceMap = new HashMap<BigDecimal, String>();
	private List<RatesUpdateBeanDataTable> deleteExchangeRate = new ArrayList<RatesUpdateBeanDataTable>();

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

	public List<RemittanceModeDescription> getRemittanceModeList() {
		List<RemittanceModeDescription> remittanceList=null;
		try{
			remittanceList = new ArrayList<RemittanceModeDescription>();
			remittanceList.addAll(ratesUpdateService.getRemittanceMastersList(session.getLanguageId()));
			for (RemittanceModeDescription remittanceMode : remittanceList) {
				remittanceMap.put(remittanceMode.getRemittanceModeMaster()
						.getRemittanceModeId(), remittanceMode
						.getRemittanceDescription());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
		}
		return remittanceList;
	}

	public void setRemittanceModeList(
			List<RemittanceModeDescription> remittanceModeList) {
		this.remittanceModeList = remittanceModeList;
	}

	public List<DeliveryMode> getDeliveryModeList() {
		List<DeliveryMode> deliveryList=null;
		try{
			deliveryList = new ArrayList<DeliveryMode>();
			deliveryList.addAll(ratesUpdateService.getDeliveryModeMastersList());
			for (DeliveryMode deliveryMode : deliveryList) {
				deliveryMap.put(deliveryMode.getDeliveryModeId(),
						deliveryMode.getDeliveryMode());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());    
		}
		return deliveryList;

	}


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

	public void setDeliveryModeList(List<DeliveryMode> deliveryModeList) {
		this.deliveryModeList = deliveryModeList;
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

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getServiceId() {
		return serviceId;
	}

	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public BigDecimal getSellingRateMin() {
		return sellingRateMin;
	}

	public void setSellingRateMin(BigDecimal sellingRateMin) {
		this.sellingRateMin = sellingRateMin;
	}

	public BigDecimal getSellingRateMax() {
		return sellingRateMax;
	}

	public void setSellingRateMax(BigDecimal sellingRateMax) {
		this.sellingRateMax = sellingRateMax;
	}

	public BigDecimal getBuyingRateMin() {
		return buyingRateMin;
	}

	public void setBuyingRateMin(BigDecimal buyingRateMin) {
		this.buyingRateMin = buyingRateMin;
	}

	public BigDecimal getBuyingRateMax() {
		return buyingRateMax;
	}

	public void setBuyingRateMax(BigDecimal buyingRateMax) {
		this.buyingRateMax = buyingRateMax;
	}

	public BigDecimal getCorporateRate() {
		return corporateRate;
	}

	public void setCorporateRate(BigDecimal corporateRate) {
		this.corporateRate = corporateRate;
	}

	/*
	 * public List<ServiceIndicator> getServiceList() { return
	 * ratesUpdateService.getServiceIndicatorList(); }
	 * 
	 * public void setServiceList(List<ServiceIndicator> serviceList) {
	 * this.serviceList = serviceList; }
	 */

	public void allListOfData(){
		getCountryList();
		getBankList();
		getCountryBranchList();
		getCurrencyList();
		getServiceMasters();
		getRemittanceModeList();
		getListDelivryDesc();
	}

	public List<CountryMasterDesc> getCountryList() {
		List<CountryMasterDesc> countryList=null;
		try{
			countryList = generalService
					.getCountryList(session.getLanguageId());

			for (CountryMasterDesc countryMasterDesc : countryList) {
				countryMap.put(countryMasterDesc.getFsCountryMaster()
						.getCountryId(), countryMasterDesc.getCountryName());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());      
		}
		return countryList;
	}

	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}

	public List<CurrencyMaster> getCurrencyList() {
		List<CurrencyMaster> currencyList=null;
		try{
			currencyList = generalService.getCurrencyList();
			for (CurrencyMaster currencyMaster : currencyList) {
				currencyMap.put(currencyMaster.getCurrencyId(),
						currencyMaster.getCurrencyName());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
		}
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
		List<CountryBranch> countryBranchList=null;
		try{
			countryBranchMap.clear();
			countryBranchList = ratesUpdateService.getCountryBranchList(session.getCountryId());
			for (CountryBranch countryBranch : countryBranchList) {
				countryBranchMap.put(countryBranch.getCountryBranchId(),countryBranch.getBranchName());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
		}
		return countryBranchList;
	}

	public void setCountryBranchList(List<CountryBranch> countryBranchList) {
		this.countryBranchList = countryBranchList;
	}

	public List<RatesUpdateBeanDataTable> getRatesUpdateDataTableList() {
		return ratesUpdateDataTableList;
	}

	public void setRatesUpdateDataTableList(
			List<RatesUpdateBeanDataTable> ratesUpdateDataTableList) {
		this.ratesUpdateDataTableList = ratesUpdateDataTableList;
	}

	public void clearAllFields() {
		setRatesUpdatePk(null);
		//setCountryId(null);
		//setCurrencyId(null);
		setBankId(null);
		setServiceId(null);
		setBranchId(null);
		//setRemittanceId(null);
		//setDeliveryId(null);
		setSellingRateMin(null);
		setSellingRateMax(null);
		setBuyingRateMin(null);
		setBuyingRateMax(null);
		setCorporateRate(null);
		//bankMap.clear();
		//countryBranchMap.clear();
		//serviceMap.clear();
	}
	
	public void clearAll(){
		setRatesUpdatePk(null);
		setCountryId(null);
		setCurrencyId(null);
		setBankId(null);
		setServiceId(null);
		setBranchId(null);
		//setRemittanceId(null);
		//setDeliveryId(null);
		setSellingRateMin(null);
		setSellingRateMax(null);
		setBuyingRateMin(null);
		setBuyingRateMax(null);
		setCorporateRate(null);
	}
	
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void ratesUpdatePageNavigation() {
		clearAllFields();
		setCountryId(null);
		setCurrencyId(null);
		setBooRenderDataTable(false);
		setBooRenderSaveExit(false);
		ratesUpdateDataTableList.clear();
		allListOfData();
		view();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "RatesUpdate.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../remittance/RatesUpdate.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addRecordsToDataTable() {
		try{
			if(echangeRateList.size()==0){
				RatesUpdateBeanDataTable rateUpdateDtObj = new RatesUpdateBeanDataTable();
				// ADDED BY NAG 21/02/2015
				rateUpdateDtObj.setRatesUpdatePk(getRatesUpdatePk());
				// END BY NAG 21/02/2015
				rateUpdateDtObj.setCountryId(getCountryId());
				rateUpdateDtObj.setCountryName(countryMap.get(getCountryId()));
				rateUpdateDtObj.setCurrencyId(getCurrencyId());
				rateUpdateDtObj.setCurrencyName(currencyMap.get(getCurrencyId()));
				rateUpdateDtObj.setBankId(getBankId());
				rateUpdateDtObj.setBankName(bankMap.get(getBankId()));
				rateUpdateDtObj.setServiceId(getServiceId());
				rateUpdateDtObj.setServiceName(serviceMap.get(getServiceId()));
				rateUpdateDtObj.setBranchId(getBranchId());
				rateUpdateDtObj.setBranchName(countryBranchMap.get(getBranchId()));
				/*rateUpdateDtObj.setRemittanceId(getRemittanceId());
		rateUpdateDtObj.setRemittanceName(remittanceMap.get(getRemittanceId()));
		rateUpdateDtObj.setDeliveryId(getDeliveryId());
		rateUpdateDtObj.setDeliveryName(deliveryMap.get(getDeliveryId()));*/
				rateUpdateDtObj.setSellingRateMin(getSellingRateMin());
				rateUpdateDtObj.setSellingRateMax(getSellingRateMax());
				rateUpdateDtObj.setBuyingRateMin(getBuyingRateMin());
				rateUpdateDtObj.setBuyingRateMax(getBuyingRateMax());
				rateUpdateDtObj.setCorporateRate(getCorporateRate());
				ratesUpdateDataTableList.add(rateUpdateDtObj);
			}
			if(ratesUpdateDataTableList.size()>0){

				if(echangeRateList.size()>0){
					for(RatesUpdateBeanDataTable exchangeDt:ratesUpdateDataTableList){
						for(RatesUpdateBeanDataTable exchngRateDt: echangeRateList){
							if( exchangeDt.getCountryId().toString().equals(exchngRateDt.getCountryId().toString())&&exchangeDt.getCurrencyId().toString().equals(exchngRateDt.getCurrencyId().toString())&&exchangeDt.getBranchId().toString().equals(exchngRateDt.getBranchId().toString())&&exchangeDt.getServiceId().toString().equals(exchngRateDt.getServiceId().toString())&&exchangeDt.getRemittanceId().toString().equals(exchngRateDt.getRemittanceId().toString())&&exchangeDt.getDeliveryId().toString().equals(exchngRateDt.getDeliveryId().toString())){
								echangeRateList.remove(exchngRateDt);
							}

						}

					}

				} 
				ratesUpdateDataTableList.addAll(echangeRateList); 
			}
			else{ 
				ratesUpdateDataTableList.addAll(echangeRateList); 
			}
			clearAllFields();
			setBooRenderDataTable(true);
			setBooRenderSaveExit(true);
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void editRecord(RatesUpdateBeanDataTable rateUpdateObj) {
		try{
			setRatesUpdatePk(rateUpdateObj.getRatesUpdatePk() );
			setCountryId(rateUpdateObj.getCountryId());
			setCurrencyId(rateUpdateObj.getCurrencyId());
			setBankId(rateUpdateObj.getBankId());
			setServiceId(rateUpdateObj.getServiceId());
			setBranchId(rateUpdateObj.getBranchId());
			/*setRemittanceId(rateUpdateObj.getRemittanceId());
		setDeliveryId(rateUpdateObj.getDeliveryId());*/
			setSellingRateMin(rateUpdateObj.getSellingRateMin());
			setSellingRateMax(rateUpdateObj.getSellingRateMax());
			setBuyingRateMin(rateUpdateObj.getBuyingRateMin());
			setBuyingRateMax(rateUpdateObj.getBuyingRateMax());
			setCorporateRate(rateUpdateObj.getCorporateRate());
			ratesUpdateDataTableList.remove(rateUpdateObj);
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
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
				boolean checkdataTable = true;


				for (RatesUpdateBeanDataTable checknullrecords : ratesUpdateDataTableList) {

					if(checknullrecords.getSellingRateMax()==null || checknullrecords.getSellingRateMin()==null || checknullrecords.getBuyingRateMax()==null || checknullrecords.getBuyingRateMin()==null){
						/*localErrorMsg = "Currency Name : " + (checknullrecords.getCurrencyName()==null?"----":checknullrecords.getCurrencyName())+"/"
							 +"Country Name : "+ (checknullrecords.getCountryName()==null?"----":checknullrecords.getCountryName())+"/"
							 +"Bank Name : "+ (checknullrecords.getBankName()==null?"----":checknullrecords.getBankName())+"/"
							 +"Branch Name : "+ (checknullrecords.getBranchName()==null?"----":checknullrecords.getBranchName())+"/"
							 +"Service Name : "+ (checknullrecords.getServiceName()==null?"----":checknullrecords.getServiceName());*/
						/* +"Remittance Name : "+ (checknullrecords.getRemittanceName()==null?"----":checknullrecords.getRemittanceName())+"/"
							 +"Delivery Name : "+ (checknullrecords.getDeliveryName()==null?"----":checknullrecords.getDeliveryName());*/
						localErrorMsg="Please Enter Sell Rate  and  Buy Rate in Data table";
						setLocalErrorMsg(localErrorMsg);
						System.out.println("Break not to save");
						checkdataTable = false;
						break;
					}

				}

				if(checkdataTable){
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

						/*	if(ratesUpdateObj.getRemittanceId()!=null){
						RemittanceModeMaster remittanceModeMaster = new RemittanceModeMaster();
						remittanceModeMaster.setRemittanceModeId(ratesUpdateObj.getRemittanceId());
						exchageRateObj.setRemitanceMode(remittanceModeMaster);
					}

					if(ratesUpdateObj.getDeliveryId()!=null){
						DeliveryMode deliveryMode = new DeliveryMode();
						deliveryMode.setDeliveryModeId(ratesUpdateObj.getDeliveryId());
						exchageRateObj.setDeliveryMode(deliveryMode);
					}*/

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




			}catch(NullPointerException ne){
				log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::saveDataTableRecods"); 
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return; 	  
			}catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage()); 
				RequestContext.getCurrentInstance().execute("error.show();");
				return;   	  
			}
		}
	}

	public void clickOnOKSave() throws Exception{

		clearAll();
		ratesUpdateDataTableList.clear();
		setBooRenderDataTable(false);
		setBooRenderSaveExit(false);
		view();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../remittance/RatesUpdate.xhtml");
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

	public List<ServiceMasterDesc> getServiceMasters() {
		List<ServiceMasterDesc> servicemasterList=null;
		try{
			serviceMap.clear();
			servicemasterList = ratesUpdateService.getServiceMastersList(session.getLanguageId());
			for (ServiceMasterDesc serviceMasterDesc : servicemasterList) {
				serviceMap.put(serviceMasterDesc.getServiceMaster().getServiceId(),	serviceMasterDesc.getLocalServiceDescription());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
		}
		return servicemasterList;
	}

	public void setServiceMasters(List<ServiceMasterDesc> serviceMasters) {
		this.serviceMasters = serviceMasters;
	}

	public List<DeliveryModeDesc> getListDelivryDesc() {
		List<DeliveryModeDesc> listDelivryDesc=null;
		try{
			listDelivryDesc = ratesUpdateService
					.lstDeliveryMode(session.getLanguageId());
			for (DeliveryModeDesc deliveryModeDesc : listDelivryDesc) {
				deliveryMap.put(deliveryModeDesc.getDeliveryMode()
						.getDeliveryModeId(), deliveryModeDesc
						.getEnglishDeliveryName());
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
		}
		return listDelivryDesc;
	}

	public void setListDelivryDesc(List<DeliveryModeDesc> listDelivryDesc) {
		this.listDelivryDesc = listDelivryDesc;
	}

	// NAG CODE START 21/02/2015

	//List<ExchangeRate> exchangeRateList = new ArrayList<>();

	/*	public void checkExchangeRatesCombination() {
		exchangeRateList.clear();
		setRatesUpdatePk(null);
		setSellingRateMax(null);
		setSellingRateMin(null);
		setBuyingRateMax(null);
		setBuyingRateMin(null);
		setCorporateRate(null);

		exchangeRateList = ratesUpdateService.exchangeRateList(getCountryId(),
				getCurrencyId(), getBankId(), getServiceId(), getBranchId(),
				getRemittanceId(), getDeliveryId());

		if (exchangeRateList.size() > 0) {
			//RequestContext.getCurrentInstance().execute("duplicate1.show();");
			setRatesUpdatePk(exchangeRateList.get(0).getExchangeRateMasterId());
			setSellingRateMin(exchangeRateList.get(0).getSellrateMin());
			setSellingRateMax(exchangeRateList.get(0).getSellrateMax());
			setBuyingRateMin(exchangeRateList.get(0).getBuyrateMin());
			setBuyingRateMax(exchangeRateList.get(0).getBuyrateMax());
			setCorporateRate(exchangeRateList.get(0).getCorporateRate());

		}

		if (ratesUpdateDataTableList.size() > 0) {

			for (RatesUpdateBeanDataTable ratesDT : ratesUpdateDataTableList) {

				//if(){

				if (ratesDT.getServiceId().equals(getServiceId())
						&& (ratesDT.getBankId().equals(getBankId())
								&& ratesDT.getCountryId()
										.equals(getCountryId()) && (ratesDT
								.getBranchId().equals(getBranchId()) && (ratesDT
								.getServiceId().equals(getServiceId()) && (ratesDT
								.getCurrencyId().equals(getCurrencyId()) && (ratesDT
								.getDeliveryId().equals(getDeliveryId()) && (ratesDT
								.getRemittanceId().equals(getRemittanceId())))))))) {

					setBooRenderDataTable(true);
					setBankId(null);
					setBranchId(null);
					setCountryId(null);
					setCurrencyId(null);
					setServiceId(null);
					setRemittanceId(null);
					setDeliveryId(null);
					setSellingRateMax(null);
					setSellingRateMin(null);
					setBuyingRateMax(null);
					setBuyingRateMin(null);
					setCorporateRate(null);
					//RequestContext.getCurrentInstance().execute(
							//"duplicate.show();");

				}

			}

		}

	}*/


	public void getAllDetailsToList() {

		if(ratesUpdateDataTableList.size()!=0){
			for (RatesUpdateBeanDataTable  ratesDT : ratesUpdateDataTableList) {
				if (ratesDT.getServiceId().equals(getServiceId())
						&& (ratesDT.getBankId().equals(getBankId())
								&& ratesDT.getCountryId().equals(getCountryId()) && (ratesDT
										.getBranchId().equals(getBranchId()) && (ratesDT
												.getServiceId().equals(getServiceId()) && (ratesDT
														.getCurrencyId().equals(getCurrencyId())))))) {
					setBankId(null);;
					setCountryId(null);
					setBranchId(null);
					setCurrencyId(null);
					setServiceId(null);
					/* setRemittanceId(null);
				setDeliveryId(null);*/
					setSellingRateMax(null);
					setSellingRateMin(null);
					setBuyingRateMax(null);
					setBuyingRateMin(null);
					setCorporateRate(null);

					RequestContext.getCurrentInstance().execute("duplicate.show();");

				} 

			}
		}
		//CR blocked due to currency id only madatory
		/*if (getBankId()!=null&&getBranchId()!=null&&getCurrencyId()!=null&getCountryId()!=null&&getServiceId()!=null&&getRemittanceId()!=null&&getDeliveryId()!=null){
			addRecordsToDataTable();
		}*/
		if (getCurrencyId()!=null){
			addRecordsToDataTable();
		}
	}


	public void exitDialog() {
		if(ratesUpdateDataTableList.size()>0){
			setBooRenderDataTable(true);
			setBooRenderSaveExit(true);
			setCountryId( null);
			setBankId( null);
			setBranchId( null);
			setCurrencyId( null);
			setServiceId(null);
			/*setDeliveryId( null);
		    setRemittanceId( null);*/
			setBuyingRateMax( null);
			setBuyingRateMin( null);
			setSellingRateMax( null);
			setSellingRateMin( null);
			setCorporateRate( null);

		}else{
			setBooRenderDataTable(false);
			setBooRenderSaveExit(false);
			setCountryId( null);
			setBankId( null);
			setBranchId( null);
			setCurrencyId( null);
			setServiceId(null);
			/* setDeliveryId( null);
			    setRemittanceId( null);*/
			setBuyingRateMax( null);
			setBuyingRateMin( null);
			setSellingRateMax( null);
			setSellingRateMin( null);
			setCorporateRate( null);

		}
		view();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../remittance/RatesUpdate.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void updateRecord() throws Exception{

		if(ratesUpdateDataTableList.size()>0){
			setBooRenderDataTable(true);
			setBooRenderSaveExit(true);

		}else{
			setBooRenderDataTable(false);
			setBooRenderSaveExit(false);
		}
		view();

		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../remittance/RatesUpdate.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public BigDecimal getRatesUpdatePk() {
		return ratesUpdatePk;
	}

	public void setRatesUpdatePk(BigDecimal ratesUpdatePk) {
		this.ratesUpdatePk = ratesUpdatePk;
	}
	List<RatesUpdateBeanDataTable> echangeRateList=new CopyOnWriteArrayList<RatesUpdateBeanDataTable>();

	public void view(){
		try{
			List<ExchangeRate> ratesList=ratesUpdateService.getAllExchageRates();
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


					if(exRate.getRemitanceMode()!=null){
						ratesDT.setRemittanceId(exRate.getRemitanceMode().getRemittanceModeId());
						if(remittanceMap != null && remittanceMap.size() != 0){
							ratesDT.setRemittanceName(remittanceMap.get(exRate.getRemitanceMode().getRemittanceModeId()));
						}
					}


					if(exRate.getDeliveryMode()!=null){
						ratesDT.setDeliveryId(exRate.getDeliveryMode().getDeliveryModeId());
						if(deliveryMap != null && deliveryMap.size() != 0){
							ratesDT.setDeliveryName(deliveryMap.get(exRate.getDeliveryMode().getDeliveryModeId()));
						}
					}

					ratesDT.setSellingRateMin(exRate.getSellrateMin());
					ratesDT.setSellingRateMax(exRate.getSellrateMax());
					ratesDT.setBuyingRateMin(exRate.getBuyrateMin());
					ratesDT.setBuyingRateMax(exRate.getBuyrateMax());
					ratesDT.setCorporateRate(exRate.getCorporateRate());

					ratesDT.setCreatedBy(exRate.getCreatedBy());
					ratesDT.setCreatedDate(exRate.getCreatedDate());

					echangeRateList.add(ratesDT);
				}

			}
			if(echangeRateList.size()>0){
				addRecordsToDataTable();
				echangeRateList.clear();
				setRatesUpdatePk(null);
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::view"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
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
			setErrorMessage("Method Name::deleteRecord"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
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

	int i=0;
	public void checkingDTandDB(){
		
		if(getCurrencyId() != null){
			for(RatesUpdateBeanDataTable ratesupdate : ratesUpdateDataTableList){
				if(getCurrencyId()!=null && ratesupdate.getCurrencyId()!=null){
					if((getCurrencyId() == null && ratesupdate.getCurrencyId() == null) || (getCurrencyId() != null && ratesupdate.getCurrencyId() != null && getCurrencyId().compareTo(ratesupdate.getCurrencyId()) == 0 )){
						if((getCountryId() == null && ratesupdate.getCountryId() == null) || (getCountryId() != null && ratesupdate.getCountryId() != null && getCountryId().compareTo(ratesupdate.getCountryId()) == 0 )){
							if((getBankId() == null && ratesupdate.getBankId() == null) || (getBankId() != null && ratesupdate.getBankId() != null && getBankId().compareTo(ratesupdate.getBankId()) == 0 )){
								if((getBranchId() == null && ratesupdate.getBranchId() == null) || (getBranchId() != null && ratesupdate.getBranchId() != null && getBranchId().compareTo(ratesupdate.getBranchId()) == 0 )){
									if((getServiceId() == null && ratesupdate.getServiceId() == null) || (getServiceId() != null && ratesupdate.getServiceId() != null && getServiceId().compareTo(ratesupdate.getServiceId()) == 0 )){
										i=1;
										break;
										/*if(Objects.equals(getRemittanceId(), ratesupdate.getRemittanceId())){
											if(Objects.equals(getDeliveryId(), ratesupdate.getDeliveryId())){
												i=1;
												break;
											}else{
												i=0;
											}
										}else{
											i=0;
										}*/
									}else{
										i=0;
									}
								}else{
									i=0;
								}
							}else{
								i=0;
							}
						}else{
							i=0;
						}
					}else{
						i=0;
					}
				}
			}
			if(i==1){
				System.out.println("Record Exists");
				RequestContext.getCurrentInstance().execute("duplicate.show();");
			}else{
				addRecordsToDataTable();
			}
		}else{
			setErrorMessage("please select currency"); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	public void checkSellRateMinMax(RatesUpdateBeanDataTable rateUpdateMinMax){
		System.out.println("Sell Rate ");
		if(rateUpdateMinMax.getSellingRateMin() != null && rateUpdateMinMax.getSellingRateMin().compareTo(BigDecimal.ZERO) != 0 && rateUpdateMinMax.getSellingRateMax() != null && rateUpdateMinMax.getSellingRateMax().compareTo(BigDecimal.ZERO) != 0){
			
			boolean checkStatus = checkSellRateMinMaxWithExchange(rateUpdateMinMax);
			
			if(!checkStatus){
				if(rateUpdateMinMax.getSellingRateMin().compareTo(rateUpdateMinMax.getSellingRateMax())>0){
					rateUpdateMinMax.setSellingRateMax(null);
					
					setErrorMessage(WarningHandler.showWarningMessage("lbl.sellratemaximumshouldbegreaterthansellrateminimum", session.getLanguageId()));
					RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
					//RequestContext.getCurrentInstance().execute("sellrateerror.show();");
				}
			}
		}
	}

	public void checkBuyRateMinMax(RatesUpdateBeanDataTable rateUpdateMinMax){
		System.out.println("Sell Rate ");
		if(rateUpdateMinMax.getBuyingRateMin() != null && rateUpdateMinMax.getBuyingRateMin().compareTo(BigDecimal.ZERO) != 0 && rateUpdateMinMax.getBuyingRateMax() != null && rateUpdateMinMax.getBuyingRateMax().compareTo(BigDecimal.ZERO) != 0 ){
			if(rateUpdateMinMax.getBuyingRateMin().compareTo(rateUpdateMinMax.getBuyingRateMax())>0){
				//rateUpdateMinMax.setBuyingRateMin(null);
				rateUpdateMinMax.setBuyingRateMax(null);
				RequestContext.getCurrentInstance().execute("buyrateerror.show();");
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
					
					setErrorMessage("Sell rate minimum must be greater than Currency Master fund minimum");
					RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
				}else if(rateUpdateMinMax.getSellingRateMax().compareTo(sellMaxRate) > 0){
					checkStatus = true;
					rateUpdateMinMax.setSellingRateMax(null);
					
					setErrorMessage("Sell rate maximum must be less than Currency Master fund maximum");
					RequestContext.getCurrentInstance().execute("checkExchangeRate.show();");
				}else{
					checkStatus = false;
				}
			}
		}
		
		return checkStatus;
		
	}
	
	public void populateBankList(){
		List<BankMaster> correspondingBankList=null;
		try{
			correspondingBankList=generalService.getCorrespondingBanks(getCountryId());
			bankMap.clear();
			for(BankMaster bankApp:correspondingBankList){
				bankMap.put(bankApp.getBankId(), bankApp.getBankFullName());
			}
			setBankList(correspondingBankList);
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::populateBankList"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch(Exception exception){
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
