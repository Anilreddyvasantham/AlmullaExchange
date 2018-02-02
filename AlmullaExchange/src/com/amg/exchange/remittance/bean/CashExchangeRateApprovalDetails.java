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

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.model.AuthorizationExchangeRateApprovalView;
import com.amg.exchange.remittance.model.CashRate;
import com.amg.exchange.treasury.bean.RatesUpdateBeanDataTable;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.ExchangeRate;
import com.amg.exchange.treasury.model.ViewUnApprovedCashRate;
import com.amg.exchange.treasury.service.IRatesUpdateService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("cashExchangeRateApprovalDetails")
@Scope("session")
public class CashExchangeRateApprovalDetails<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SessionStateManage session = new SessionStateManage();

	private String exceptionMessage;
	private boolean booRenderAuthorization = false;
	private String autenticationAuthorizedBy;
	private String autenticationAuthorizedPassword;

	private List<CashExchangeRateApprovalDataBase> ratesUpdateDataTableList = new ArrayList<CashExchangeRateApprovalDataBase>();
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

	public List<CashExchangeRateApprovalDataBase> getRatesUpdateDataTableList() {
		return ratesUpdateDataTableList;
	}
	public void setRatesUpdateDataTableList(List<CashExchangeRateApprovalDataBase> ratesUpdateDataTableList) {
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

		List<ViewUnApprovedCashRate> ratesList = ratesUpdateService.getAllCashExchageRatesFromViewUnApprovedCashRate();

		if(ratesList != null && ratesList.size() != 0){

			for (ViewUnApprovedCashRate unApprovedExchangeRatesView : ratesList) {

				CashExchangeRateApprovalDataBase lstExchangeApproval = new CashExchangeRateApprovalDataBase();

				lstExchangeApproval.setUnAppcashRateId(unApprovedExchangeRatesView.getUnAppcashRateId());
				lstExchangeApproval.setCashRateId(unApprovedExchangeRatesView.getCashRateId());
				lstExchangeApproval.setAlternativeCurrencyCode(unApprovedExchangeRatesView.getAlternativeCurrencyCode());
				lstExchangeApproval.setAlternativeCurrencyId(unApprovedExchangeRatesView.getAlternativeCurrencyId());
				lstExchangeApproval.setAlternativeCurrencyName(unApprovedExchangeRatesView.getAlternativeCurrencyName());
				lstExchangeApproval.setApplicationCountryId(unApprovedExchangeRatesView.getApplicationCountryId());
				lstExchangeApproval.setAuthCheckInd(unApprovedExchangeRatesView.getAuthCheckInd());
				lstExchangeApproval.setBaseCurrencyCode(unApprovedExchangeRatesView.getBaseCurrencyCode());
				lstExchangeApproval.setBaseCurrencyId(unApprovedExchangeRatesView.getBaseCurrencyId());
				lstExchangeApproval.setBaseCurrencyName(unApprovedExchangeRatesView.getBaseCurrencyName());
				lstExchangeApproval.setBranchName(unApprovedExchangeRatesView.getBranchName());
				lstExchangeApproval.setBuyMaxRate(unApprovedExchangeRatesView.getBuyMaxRate());
				lstExchangeApproval.setBuyMinRate(unApprovedExchangeRatesView.getBuyMinRate());
				lstExchangeApproval.setCountryBranchId(unApprovedExchangeRatesView.getCountryBranchId());
				lstExchangeApproval.setLocCod(unApprovedExchangeRatesView.getLocCod());
				lstExchangeApproval.setPrvBuyMaxRate(unApprovedExchangeRatesView.getPrvBuyMaxRate());
				lstExchangeApproval.setPrvBuyMinRate(unApprovedExchangeRatesView.getPrvBuyMinRate());
				lstExchangeApproval.setPrvSalMaxRate(unApprovedExchangeRatesView.getPrvSalMaxRate());
				lstExchangeApproval.setPrvSalMinRate(unApprovedExchangeRatesView.getPrvSalMinRate());
				lstExchangeApproval.setSalMaxRate(unApprovedExchangeRatesView.getSalMaxRate());
				lstExchangeApproval.setSalMinRate(unApprovedExchangeRatesView.getSalMinRate());
				lstExchangeApproval.setCreatedBy(unApprovedExchangeRatesView.getCreatedBy());
				lstExchangeApproval.setCreatedDate(unApprovedExchangeRatesView.getCreatedDate());
				if(unApprovedExchangeRatesView.getAuthCheckInd() != null && unApprovedExchangeRatesView.getAuthCheckInd().equalsIgnoreCase(Constants.Yes)){
					lstExchangeApproval.setAuthCheckStatus(Constants.YES);
				}else{
					lstExchangeApproval.setAuthCheckStatus(Constants.NO);
				}

				ratesUpdateDataTableList.add(lstExchangeApproval);
			}

		}//else{
			//setRatesUpdateDataTableList(null);
		//}

	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void exchangeApprovalPageNavigation() {
		view();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "CashExchangeRateApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/CashExchangeRateApproval.xhtml");
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
				for (CashExchangeRateApprovalDataBase lstAuthorizeCheck : ratesUpdateDataTableList) {
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

		List<CashRate> lstRecordsToSave = new ArrayList<CashRate>();
		HashMap<BigDecimal, BigDecimal> exchangeRateMaster = new HashMap<BigDecimal, BigDecimal>();

		try{

			for (CashExchangeRateApprovalDataBase lstexchangerate : ratesUpdateDataTableList) {

				CashRate cashRate = new CashRate();

				if(lstexchangerate.getCashRateId() != null){
					cashRate.setCashRateId(lstexchangerate.getCashRateId());
					cashRate.setModifiedBy(session.getUserName());
					cashRate.setModifiedDate(new Date());
				}else{
					cashRate.setCashRateId(null);
				}

				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(lstexchangerate.getApplicationCountryId());
				cashRate.setAppCountryId(countryMaster);

				CurrencyMaster alternativeCurrencyMaster = new CurrencyMaster();
				alternativeCurrencyMaster.setCurrencyId(lstexchangerate.getAlternativeCurrencyId());
				cashRate.setAlternateCurrencyId(alternativeCurrencyMaster);
				cashRate.setAlternateCurrencyCode(lstexchangerate.getAlternativeCurrencyCode());

				CurrencyMaster basecurrencyMaster = new CurrencyMaster();
				basecurrencyMaster.setCurrencyId(lstexchangerate.getBaseCurrencyId());
				cashRate.setBaseCurrencyId(basecurrencyMaster);
				cashRate.setBaseCurrencyCode(lstexchangerate.getBaseCurrencyCode());

				if(lstexchangerate.getCountryBranchId()!=null)
				{
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(lstexchangerate.getCountryBranchId());
					cashRate.setCountryBranchId(countryBranch);

				}
				cashRate.setLocCode(lstexchangerate.getLocCod().toString());

				cashRate.setSaleMaxRate(lstexchangerate.getSalMaxRate());
				cashRate.setSaleMinRate(lstexchangerate.getSalMinRate());
				cashRate.setBuyRateMax(lstexchangerate.getBuyMaxRate());
				cashRate.setBuyRateMin(lstexchangerate.getBuyMinRate());

				if(lstexchangerate.getPrvBuyMaxRate() != null){
					cashRate.setPrvBuyMaxRate(lstexchangerate.getPrvBuyMaxRate());
				}
				if(lstexchangerate.getPrvBuyMaxRate() != null){
					cashRate.setPrvBuyMinRate(lstexchangerate.getPrvBuyMinRate());
				}
				if(lstexchangerate.getPrvSalMaxRate() != null){
					cashRate.setPrvSellMaxRate(lstexchangerate.getPrvSalMaxRate());
				}
				if(lstexchangerate.getPrvSalMinRate() != null){
					cashRate.setPrvSellMinRate(lstexchangerate.getPrvSalMinRate());
				}

				if(lstexchangerate.getAuthCheckInd() != null && lstexchangerate.getAuthCheckInd().equalsIgnoreCase(Constants.Yes)){
					cashRate.setAuthorisedBy(getAutenticationAuthorizedBy());
					cashRate.setAuthorisedDate(new Date());
				}
				
				cashRate.setCreatedBy(lstexchangerate.getCreatedBy());
				cashRate.setCreatedDate(lstexchangerate.getCreatedDate());
				cashRate.setIsActive(Constants.Yes);
				cashRate.setApprovedBy(session.getUserName());
				cashRate.setApprovedDate(new Date());

				//cashRate.setUploadDate(getUploadDate());

				lstRecordsToSave.add(cashRate);
				// need to get created id "EX_UNAPP_CASHRATE"
				exchangeRateMaster.put(lstexchangerate.getUnAppcashRateId(), lstexchangerate.getUnAppcashRateId());

			}

			HashMap<String, Object> saveAprroveRecords = new HashMap<String, Object>();

			saveAprroveRecords.put("lstRecordsToSave", lstRecordsToSave);
			saveAprroveRecords.put("exchangeRateMaster", exchangeRateMaster);

			System.out.println(exchangeRateMaster.toString());

			ratesUpdateService.saveApprovedCashExchangedRate(saveAprroveRecords);

			RequestContext.getCurrentInstance().execute("approve.show();");

		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	public void clickOKbuttonApprove(){
		//Viewing All Records From Exchange Table
		setBooRenderAuthorization(false);
		setAutenticationAuthorizedBy(null);
		setAutenticationAuthorizedPassword(null);
		view();
	}


}
