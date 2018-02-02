package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.ICurrencyService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.viewModel.CurrencyCountryLevelView;
import com.amg.exchange.treasury.viewModel.CurrencyGLLevelView;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("currencyGl_CountryLevelInquiry")
@Scope("session")
public class CurrencyGl_CountryLevelInquiry<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(CurrencyGl_CountryLevelInquiry.class);
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
	private BigDecimal currencyId;
	private String currencyName;
	private String currencyCode;
	private String searchDetails;

	// extra variables
	private boolean booRenderGLLevelDataTable = false;
	private boolean booRenderCountryCurrencyLevelDataTable = false;
	private boolean booRenderClear = false;
	private String exceptionMessage;

	// List of records
	private List<CurrencyMaster> currencyList = new ArrayList<CurrencyMaster>();
	private List<CurrencyGL_CountryDataTable> currencyGLLevelDataTable = new ArrayList<CurrencyGL_CountryDataTable>();
	private List<CurrencyGL_CountryDataTable> currencyCountryLevelDataTable = new ArrayList<CurrencyGL_CountryDataTable>();

	public List<CurrencyMaster> getCurrencyList() {
		return currencyList;
	}
	public void setCurrencyList(List<CurrencyMaster> currencyList) {
		this.currencyList = currencyList;
	}

	public List<CurrencyGL_CountryDataTable> getCurrencyGLLevelDataTable() {
		return currencyGLLevelDataTable;
	}
	public void setCurrencyGLLevelDataTable(List<CurrencyGL_CountryDataTable> currencyGLLevelDataTable) {
		this.currencyGLLevelDataTable = currencyGLLevelDataTable;
	}

	public List<CurrencyGL_CountryDataTable> getCurrencyCountryLevelDataTable() {
		return currencyCountryLevelDataTable;
	}
	public void setCurrencyCountryLevelDataTable(List<CurrencyGL_CountryDataTable> currencyCountryLevelDataTable) {
		this.currencyCountryLevelDataTable = currencyCountryLevelDataTable;
	}


	// variables getters and setters
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

	public String getSearchDetails() {
		return searchDetails;
	}
	public void setSearchDetails(String searchDetails) {
		this.searchDetails = searchDetails;
	}

	// extra variables
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public boolean isBooRenderGLLevelDataTable() {
		return booRenderGLLevelDataTable;
	}

	public void setBooRenderGLLevelDataTable(boolean booRenderGLLevelDataTable) {
		this.booRenderGLLevelDataTable = booRenderGLLevelDataTable;
	}
	public boolean isBooRenderCountryCurrencyLevelDataTable() {
		return booRenderCountryCurrencyLevelDataTable;
	}

	public void setBooRenderCountryCurrencyLevelDataTable(boolean booRenderCountryCurrencyLevelDataTable) {
		this.booRenderCountryCurrencyLevelDataTable = booRenderCountryCurrencyLevelDataTable;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public boolean isBooRenderClear() {
		return booRenderClear;
	}
	public void setBooRenderClear(boolean booRenderClear) {
		this.booRenderClear = booRenderClear;
	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// methods 
	//page navigation
	public void currencyGLCountryLevelPageNavigation() {
		try {
			clearAllFields();
			fetchCurrencyMasterList();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "CurrencyGL_CountryLevel.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/CurrencyGL_CountryLevel.xhtml");
		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("dataexception.show();");
		}
	}

	// method to get the country Name name and country code from dataBase
	public void fetchCurrencyMasterList() {
		try{
			LOG.info("Entering into generalService getCurrencyList method");
			currencyList = generalService.getCurrencyList();
			LOG.info("Exit into generalService getCurrencyList method");
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("dataexception.show();");
		}

	}

	// clearing fields
	public void clearAllFields(){
		setCurrencyCode(null);
		setCurrencyId(null);
		setCurrencyName(null);
		setSearchDetails(null);
		setExceptionMessage(null);
		setBooRenderClear(false);
		setBooRenderGLLevelDataTable(false);
		setBooRenderCountryCurrencyLevelDataTable(false);
		if(currencyGLLevelDataTable != null && !currencyGLLevelDataTable.isEmpty()){
			currencyGLLevelDataTable.clear();
		}
		
		if(currencyCountryLevelDataTable != null && !currencyCountryLevelDataTable.isEmpty()){
			currencyCountryLevelDataTable.clear();
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

	// search operation based on GL / Country Level
	public void searchCurrencyGLCountryDetails(){
		
		try {
			
			if(currencyGLLevelDataTable != null && !currencyGLLevelDataTable.isEmpty()){
				currencyGLLevelDataTable.clear();
			}
			
			if(currencyCountryLevelDataTable != null && !currencyCountryLevelDataTable.isEmpty()){
				currencyCountryLevelDataTable.clear();
			}
			
			if(getSearchDetails().equalsIgnoreCase(Constants.GL_LEVEL)){

				List<CurrencyGLLevelView> lstGLLevel = currencyService.fetchAllCurrencyGLRecords(getCurrencyCode());

				if(lstGLLevel.size() != 0){
					
					setBooRenderClear(true);
					setBooRenderGLLevelDataTable(true);
					setBooRenderCountryCurrencyLevelDataTable(false);
					
					for (CurrencyGLLevelView currencyGLLevelView : lstGLLevel) {
						
						CurrencyGL_CountryDataTable currencyGLRecords = new CurrencyGL_CountryDataTable();

						currencyGLRecords.setBankCode(currencyGLLevelView.getBankCode());
						currencyGLRecords.setBankName(currencyGLLevelView.getBankFullName());
						currencyGLRecords.setCurrencyCode(currencyGLLevelView.getCurrencyCode());
						currencyGLRecords.setCurrencyName(currencyGLLevelView.getCurrencyName());
						currencyGLRecords.setGlNo(currencyGLLevelView.getGlNo());
						currencyGLRecords.setCurrentBalance(currencyGLLevelView.getRateCurBal());
						currencyGLRecords.setForeignBalance(currencyGLLevelView.getRateFCurBal());
						currencyGLRecords.setAverageRate(currencyGLLevelView.getRateAvgRate());
						
						currencyGLLevelDataTable.add(currencyGLRecords);
					}

				}else{
					setBooRenderClear(false);
					setBooRenderGLLevelDataTable(false);
					setBooRenderCountryCurrencyLevelDataTable(false);
					RequestContext.getCurrentInstance().execute("norecords.show();");
				}

			}else if(getSearchDetails().equalsIgnoreCase(Constants.COUNTRY_CURRENCY_LEVEL)){

				List<CurrencyCountryLevelView> lstCountryLevel = currencyService.fetchAllCurrencyCountryLevelRecords(getCurrencyCode());

				if(lstCountryLevel.size() != 0){
					
					setBooRenderClear(true);
					setBooRenderGLLevelDataTable(false);
					setBooRenderCountryCurrencyLevelDataTable(true);
					
					for (CurrencyCountryLevelView currencyCountryLevelView : lstCountryLevel) {
						
						CurrencyGL_CountryDataTable currencyCountryLevelRecords = new CurrencyGL_CountryDataTable();

						currencyCountryLevelRecords.setCountryCode(currencyCountryLevelView.getCountryCode());
						currencyCountryLevelRecords.setCountryName(currencyCountryLevelView.getCountryName());
						currencyCountryLevelRecords.setCurrencyCode(currencyCountryLevelView.getCurrencyCode());
						currencyCountryLevelRecords.setCurrencyName(currencyCountryLevelView.getCurrencyName());
						currencyCountryLevelRecords.setCurrentBalance(currencyCountryLevelView.getCurBal());
						currencyCountryLevelRecords.setForeignBalance(currencyCountryLevelView.getfCurBal());
						currencyCountryLevelRecords.setAverageRate(currencyCountryLevelView.getAvgRate());
						
						currencyCountryLevelDataTable.add(currencyCountryLevelRecords);
					}

				}else{
					setBooRenderClear(false);
					setBooRenderGLLevelDataTable(false);
					setBooRenderCountryCurrencyLevelDataTable(false);
					RequestContext.getCurrentInstance().execute("norecords.show();");
				}

			}else{
				// if null.
				setBooRenderClear(false);
				setBooRenderGLLevelDataTable(false);
				setBooRenderCountryCurrencyLevelDataTable(false);
			}
		} catch (Exception e) {
			setBooRenderClear(false);
			setBooRenderGLLevelDataTable(false);
			setBooRenderCountryCurrencyLevelDataTable(false);
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("dataexception.show();");
		}

	}

}
