package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.AuthorizationExchangeRateApprovalView;
import com.amg.exchange.remittance.model.ExchangeRateApprovalDetModel;
import com.amg.exchange.remittance.model.UnApprovedExchangeRatesView;
import com.amg.exchange.treasury.bean.RatesUpdateBeanDataTable;
import com.amg.exchange.treasury.model.ExchangeRate;
import com.amg.exchange.treasury.service.IRatesUpdateService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("exchangeApprovalDetails")
@Scope("session")
public class ExchangeApprovalDetails<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SessionStateManage session = new SessionStateManage();

	private String exceptionMessage;
	private boolean booRenderAuthorization = false;
	private String autenticationAuthorizedBy;
	private String autenticationAuthorizedPassword;

	private List<ExchangeRateApprovalDataTable> ratesUpdateDataTableList = new ArrayList<ExchangeRateApprovalDataTable>();
	private CopyOnWriteArrayList<ExchangeRate> ratesListFromDB = new CopyOnWriteArrayList<ExchangeRate>();
	private CopyOnWriteArrayList<RatesUpdateBeanDataTable> exhangeRateList=new CopyOnWriteArrayList<RatesUpdateBeanDataTable>();
	private List<AuthorizationExchangeRateApprovalView> emplAutenticationlist = new ArrayList<AuthorizationExchangeRateApprovalView>();

	public List<AuthorizationExchangeRateApprovalView> getEmplAutenticationlist() {
		return emplAutenticationlist;
	}
	public void setEmplAutenticationlist(List<AuthorizationExchangeRateApprovalView> emplAutenticationlist) {
		this.emplAutenticationlist = emplAutenticationlist;
	}
	
	public String getAutenticationAuthorizedBy() {
		return autenticationAuthorizedBy;
	}
	public void setAutenticationAuthorizedBy(String autenticationAuthorizedBy) {
		this.autenticationAuthorizedBy = autenticationAuthorizedBy;
	}
	
	public String getAutenticationAuthorizedPassword() {
		return autenticationAuthorizedPassword;
	}
	public void setAutenticationAuthorizedPassword(String autenticationAuthorizedPassword) {
		this.autenticationAuthorizedPassword = autenticationAuthorizedPassword;
	}
	
	public boolean isBooRenderAuthorization() {
		return booRenderAuthorization;
	}
	public void setBooRenderAuthorization(boolean booRenderAuthorization) {
		this.booRenderAuthorization = booRenderAuthorization;
	}
	
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public CopyOnWriteArrayList<ExchangeRate> getRatesListFromDB() {
		return ratesListFromDB;
	}
	public void setRatesListFromDB(CopyOnWriteArrayList<ExchangeRate> ratesListFromDB) {
		this.ratesListFromDB = ratesListFromDB;
	}

	public List<ExchangeRateApprovalDataTable> getRatesUpdateDataTableList() {
		return ratesUpdateDataTableList;
	}
	public void setRatesUpdateDataTableList(List<ExchangeRateApprovalDataTable> ratesUpdateDataTableList) {
		this.ratesUpdateDataTableList = ratesUpdateDataTableList;
	}

	public CopyOnWriteArrayList<RatesUpdateBeanDataTable> getExhangeRateList() {
		return exhangeRateList;
	}
	public void setExhangeRateList(CopyOnWriteArrayList<RatesUpdateBeanDataTable> exhangeRateList) {
		this.exhangeRateList = exhangeRateList;
	}

	@Autowired
	IRatesUpdateService<T> ratesUpdateService;

	@Autowired
	IGeneralService<T> generalService;

	public void view(){

		if(ratesUpdateDataTableList != null && ratesUpdateDataTableList.size() != 0){
			ratesUpdateDataTableList.clear();
		}

		List<UnApprovedExchangeRatesView> ratesList = ratesUpdateService.getAllExchageRatesFromUnApprovedExchangeRateView();
		if(ratesList.size() != 0){

			if(ratesList != null && ratesList.size() != 0){

				for (UnApprovedExchangeRatesView unApprovedExchangeRatesView : ratesList) {

					ExchangeRateApprovalDataTable lstExchangeApproval = new ExchangeRateApprovalDataTable();

					lstExchangeApproval.setApplicationCountryId(unApprovedExchangeRatesView.getApplicationCountryId());
					lstExchangeApproval.setAuthCheckId(unApprovedExchangeRatesView.getAuthCheckId());
					lstExchangeApproval.setBankCode(unApprovedExchangeRatesView.getBankCode());
					lstExchangeApproval.setBankId(unApprovedExchangeRatesView.getBankId());
					lstExchangeApproval.setBranchName(unApprovedExchangeRatesView.getBranchName());
					lstExchangeApproval.setBuyRateMAX(unApprovedExchangeRatesView.getBuyRateMAX());
					lstExchangeApproval.setBuyRateMIN(unApprovedExchangeRatesView.getBuyRateMIN());
					lstExchangeApproval.setCorporateRate(unApprovedExchangeRatesView.getCorporateRate());
					lstExchangeApproval.setCountryBranchId(unApprovedExchangeRatesView.getCountryBranchId());
					lstExchangeApproval.setCountryCode(unApprovedExchangeRatesView.getCountryCode());
					lstExchangeApproval.setCountryId(unApprovedExchangeRatesView.getCountryId());
					lstExchangeApproval.setCountryName(unApprovedExchangeRatesView.getCountryName());
					lstExchangeApproval.setCurrencyCode(unApprovedExchangeRatesView.getCurrencyCode());
					lstExchangeApproval.setCurrencyId(unApprovedExchangeRatesView.getCurrencyId());
					lstExchangeApproval.setCurrencyName(unApprovedExchangeRatesView.getCurrencyName());
					lstExchangeApproval.setExchangeRateMasterAprId(unApprovedExchangeRatesView.getExchangeRateMasterAprId());
					lstExchangeApproval.setExchangeRateMasterId(unApprovedExchangeRatesView.getExchangeRateMasterId());
					lstExchangeApproval.setLocCOD(unApprovedExchangeRatesView.getLocCOD());
					lstExchangeApproval.setPrvBuyRateMAX(unApprovedExchangeRatesView.getPrvBuyRateMAX());
					lstExchangeApproval.setPrvBuyRateMIN(unApprovedExchangeRatesView.getPrvBuyRateMIN());
					lstExchangeApproval.setPrvSellRateMAX(unApprovedExchangeRatesView.getPrvSellRateMAX());
					lstExchangeApproval.setPrvSellRateMIN(unApprovedExchangeRatesView.getPrvSellRateMIN());
					lstExchangeApproval.setSellRateMAX(unApprovedExchangeRatesView.getSellRateMAX());
					lstExchangeApproval.setSellRateMIN(unApprovedExchangeRatesView.getSellRateMIN());
					lstExchangeApproval.setServiceCode(unApprovedExchangeRatesView.getServiceCode());
					lstExchangeApproval.setServiceDescription(unApprovedExchangeRatesView.getServiceDescription());
					lstExchangeApproval.setServiceMasterId(unApprovedExchangeRatesView.getServiceMasterId());
					lstExchangeApproval.setCreatedBy(unApprovedExchangeRatesView.getCreatedBy());
					lstExchangeApproval.setCreatedDate(unApprovedExchangeRatesView.getCreatedDate());
					
					if(unApprovedExchangeRatesView.getAuthCheckId() != null && unApprovedExchangeRatesView.getAuthCheckId().equalsIgnoreCase(Constants.Yes)){
						lstExchangeApproval.setAuthCheckStatus(Constants.YES);
					}else{
						lstExchangeApproval.setAuthCheckStatus(Constants.NO);
					}

					ratesUpdateDataTableList.add(lstExchangeApproval);
				}
			}

		}//else{
			//setRatesUpdateDataTableList(null);
		//}

	}

	public void callListofDetails(){		
		setAutenticationAuthorizedBy(null);
		setAutenticationAuthorizedPassword(null);
		setBooRenderAuthorization(false);
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void exchangeApprovalPageNavigation() {
		callListofDetails();
		view();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "exchangeapprovallist.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/exchangeapprovallist.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkingAuthorizationIndToApprove(){

		if(isBooRenderAuthorization()){
			
			boolean authorPasswordCheck = false;
			
			if(getAutenticationAuthorizedBy() != null && getAutenticationAuthorizedPassword() != null){
				for (AuthorizationExchangeRateApprovalView lstAuthCheck : emplAutenticationlist) {
					if(lstAuthCheck.getOraUser().equalsIgnoreCase(getAutenticationAuthorizedBy())){
						if(lstAuthCheck.getUsrPwd().equalsIgnoreCase(getAutenticationAuthorizedPassword())){
							authorPasswordCheck = true;
							break;
						}
					}
				}
			}else{
				// enter user name and password
				authorPasswordCheck = false;
			}
			
			if(authorPasswordCheck){
				approveAllUnApprovedExchangeRate();
			}else{
				RequestContext.getCurrentInstance().execute("inValidLogin.show()");
				return;
			}

		}else{
			
			boolean authorCheck = false;

			if(ratesUpdateDataTableList != null && ratesUpdateDataTableList.size() != 0){
				for (ExchangeRateApprovalDataTable lstAuthorizeCheck : ratesUpdateDataTableList) {
					if(lstAuthorizeCheck.getAuthCheckStatus() != null && lstAuthorizeCheck.getAuthCheckStatus().equalsIgnoreCase(Constants.YES)){
						authorCheck = true;
						break;
					}else{
						authorCheck = false;
					}
				}
			}

			if(authorCheck){
				setBooRenderAuthorization(true);
				emplAutenticationlist = ratesUpdateService.getAllExchageRatesAuthorizedList();
			}else{
				setBooRenderAuthorization(false);
				approveAllUnApprovedExchangeRate();
			}
		}

	}

	public void approveAllUnApprovedExchangeRate(){
		
		List<ExchangeRateApprovalDetModel> lstRecordsToSave = new ArrayList<ExchangeRateApprovalDetModel>();
		HashMap<BigDecimal, BigDecimal> exchangeRateMaster = new HashMap<BigDecimal, BigDecimal>();

		try{

			for (ExchangeRateApprovalDataTable lstexchangerate : ratesUpdateDataTableList) {

				ExchangeRateApprovalDetModel exchangeRateApp = new ExchangeRateApprovalDetModel();
				
				
				if(lstexchangerate.getExchangeRateMasterAprId() != null){
					exchangeRateApp.setExchangeRateMasterAprDetId(lstexchangerate.getExchangeRateMasterAprId());
					exchangeRateApp.setModifiedBy(session.getUserName());
					exchangeRateApp.setModifiedDate(new Date());
				}else{
					exchangeRateApp.setExchangeRateMasterAprDetId(null);
				}
				exchangeRateApp.setExchangeRateMasterId(lstexchangerate.getExchangeRateMasterId());
				exchangeRateApp.setApplicationCountryId(lstexchangerate.getApplicationCountryId());
				exchangeRateApp.setApprovedBy(session.getUserName());
				exchangeRateApp.setApprovedDate(new Date());
				
				if(lstexchangerate.getAuthCheckId() != null && lstexchangerate.getAuthCheckId().equalsIgnoreCase(Constants.Yes)){
					exchangeRateApp.setAuthorisedBy(getAutenticationAuthorizedBy());
					exchangeRateApp.setAuthorisedDate(new Date());
				}
				
				exchangeRateApp.setBankId(lstexchangerate.getBankId());
				exchangeRateApp.setBuyRateMax(lstexchangerate.getBuyRateMAX());
				exchangeRateApp.setBuyRateMin(lstexchangerate.getBuyRateMIN());
				if(lstexchangerate.getCorporateRate() != null){
					exchangeRateApp.setCorporateRate(lstexchangerate.getCorporateRate());
				}
				exchangeRateApp.setCountryBranchId(lstexchangerate.getCountryBranchId());
				exchangeRateApp.setCountryId(lstexchangerate.getCountryId());
				exchangeRateApp.setCreatedBy(lstexchangerate.getCreatedBy());
				exchangeRateApp.setCreatedDate(lstexchangerate.getCreatedDate());
				exchangeRateApp.setCurrencyId(lstexchangerate.getCurrencyId());
				//exchangeRateApp.setDeliveryModeId(lstexchangerate.get);
				exchangeRateApp.setIsActive(Constants.Yes);
				
				if(lstexchangerate.getPrvBuyRateMAX() != null){
					exchangeRateApp.setPrvBuyRateMax(lstexchangerate.getPrvBuyRateMAX());
				}
				if(lstexchangerate.getPrvBuyRateMIN() != null){
					exchangeRateApp.setPrvBuyRateMin(lstexchangerate.getPrvBuyRateMIN());
				}
				if(lstexchangerate.getPrvSellRateMAX() != null){
					exchangeRateApp.setPrvSellRateMax(lstexchangerate.getPrvSellRateMAX());
				}
				if(lstexchangerate.getPrvSellRateMIN() != null){
					exchangeRateApp.setPrvSellRateMin(lstexchangerate.getPrvSellRateMIN());
				}
				//exchangeRateApp.setRemarks(lstexchangerate.get);
				//exchangeRateApp.setRemitanceModeId();
				exchangeRateApp.setSellRateMax(lstexchangerate.getSellRateMAX());
				exchangeRateApp.setSellRateMin(lstexchangerate.getSellRateMIN());
				exchangeRateApp.setServiceId(lstexchangerate.getServiceMasterId());
				
				lstRecordsToSave.add(exchangeRateApp);
				exchangeRateMaster.put(lstexchangerate.getExchangeRateMasterId(), lstexchangerate.getExchangeRateMasterId());
				
			}
			
			HashMap<String, Object> saveAprroveRecords = new HashMap<String, Object>();
			
			saveAprroveRecords.put("lstRecordsToSave", lstRecordsToSave);
			saveAprroveRecords.put("exchangeRateMaster", exchangeRateMaster);
			
			System.out.println(exchangeRateMaster.toString());
			
			ratesUpdateService.saveApprovedExchangedRate(saveAprroveRecords);

			//ratesUpdateService.saveExchangeRateApproval(exchangeRateApp);
			//ratesUpdateService.deleteExchangeRecord(lstexchangerate);

			RequestContext.getCurrentInstance().execute("approve.show();");

		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	public void clickOKbuttonApprove(){
		//DB Records Fetching Again
		callListofDetails();
		//Viewing All Records From Exchange Table
		view();
	}


}
