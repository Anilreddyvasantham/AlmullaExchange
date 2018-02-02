package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.FundEstimationAlert;
import com.amg.exchange.treasury.model.FundEstimationDays;
import com.amg.exchange.treasury.service.FundEstimationDetailsBeanService;
import com.amg.exchange.treasury.service.IBankTransferService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.IFxDealwithSupplierService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.treasury.service.IUsdGlobalRequirementDetailsService;
import com.amg.exchange.treasury.viewModel.TreasuryEstimationDaysView;
import com.amg.exchange.treasury.viewModel.TreasuryFundEstimationDealAlertView;
import com.amg.exchange.treasury.viewModel.TreasuryFundestimationAlertView;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.DateUtil;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("fundEstimationDealAlertBean")
@Scope("session")
public class FundEstimationDealAlertBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(FundEstimationDealAlertBean.class);
	ResourceBundle props = ResourceBundle.getBundle("com.amg.exchange.messages.amgamx_en");

	// private String projectionDate = null;
	// Added By Rabil on 10/08/2015

	private Date projectionDate = new Date();

	private BigDecimal companyId = null;
	private String countryName = null;
	private BigDecimal countryId = null;
	private BigDecimal bankCountry = null;
	private BigDecimal bankId = null;
	private BigDecimal currencyId = null;
	private BigDecimal currentBankBalance = null;;
	private BigDecimal accumulatedSalesProjectionAmount;
	private String accumulatedSalesUSDEquivalent = null;
	private BigDecimal valueDatedTrnsaction = null;
	private BigDecimal noOfDaysEstimation = null;
	private BigDecimal salesProjection = null;
	private BigDecimal iKONRate = null;
	private BigDecimal usdSalesProjection;
	// private List<BankMaster> bankCountryList = new ArrayList<BankMaster>();
	private List<BankApplicability> bankAccordingToBankCountry = new ArrayList<BankApplicability>();
	private List<BankAccountDetails> currencyOfBank = new ArrayList<BankAccountDetails>();
	private List<BankAccountDetails> lstAccountNumber = new ArrayList<BankAccountDetails>();
	private List<CompanyMasterDesc> lstCompanyId = new ArrayList<CompanyMasterDesc>();

	/** Added by Rabil start **/

	private List<BankCountryPopulationBean> bankCountryList = new ArrayList<BankCountryPopulationBean>();
	private List<CountryCurrencyPopulationBean> countryCurrencyList = new ArrayList<CountryCurrencyPopulationBean>();
	private List<FundEstimationPopulationBean> fundEstimationList = new ArrayList<FundEstimationPopulationBean>();
	private List<FundEstimationPopulationAlertBean> fundEstimationBeanForDealAlertList = new ArrayList<FundEstimationPopulationAlertBean>();

	private List<FundEstimationPopulationAlertBean> fundEstimationBeanForDealAlertSaveList = new ArrayList<FundEstimationPopulationAlertBean>();

	/** Added by Rabil end **/

	List<FundAccumulationDetailsDatatable> lstDetails = new ArrayList<FundAccumulationDetailsDatatable>();

	private boolean Control1stAnd2ndTime = false;
	private BigDecimal systemGeneratedSalesProjection;
	private BigDecimal fcAmountfromFXDeal;

	private boolean booSystemGen = true;
	private boolean booUpdateSalesProj = false;
	private boolean BooCheck = false;
	private boolean noOfDaysEstimationBol = false;
	// private boolean readOnlySalesProjection = false;
	private boolean editButton = false;
	// private boolean check1stOr2Nd = false;

	private boolean booRenderLinkNoofDays = false;
	private boolean booRenderNormalNoOfdays = true;
	private BigDecimal noofOccurance;
	private BigDecimal dayofweek;
	private int noofDaysPopup = 0;

	private int accNumberSize = 0;
	private Boolean boobankAcc = true;
	private Boolean booSelectbankAcc = false;
	private String accountNumber = null;
	private String accountNumberName = null;
	private BigDecimal accountId = null;

	private int bankcurrencySize = 0;
	private Boolean boobankCurrency = true;
	private Boolean booSelectbankCurrency = false;
	private String bankCurrencyName = null;
	private BigDecimal bankCurrencyId = null;
	private boolean booSaveButton = false;

	private static int monthcal = 0;
	private static int da = 0;
	private static int da1 = 0;

	private SessionStateManage sessionManage = new SessionStateManage();

	private String msg = new String();

	private BigDecimal updateDaysId;
	
	private String exceptionMessage; //added by nazish

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IFundEstimationService<T> fundEstimationService;

	@Autowired
	FundEstimationDetailsBeanService fundEstimationDetailsBeanService;

	@Autowired
	IUsdGlobalRequirementDetailsService iUsdGlobalRequirementDetailsService;

	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;

	@Autowired
	IBankTransferService<T> bankTransferService;

	@Autowired
	IFxDealwithSupplierService<T> fxDealwithSupplierService;

	public List<CompanyMasterDesc> getLstCompanyId() {

		List<CompanyMasterDesc> lstCompanyId = specialCustomerDealRequestService.getAllCompanyList(sessionManage.getCompanyId(), new BigDecimal(sessionManage.isExists("languageId") ? sessionManage.getSessionValue("languageId") : "1"));
		setCompanyId(lstCompanyId.get(0).getFsCompanyMaster().getCompanyId());
		return lstCompanyId;
	}

	/**
	 * This method will receive a Date and return formatted String
	 * 
	 * @param date
	 * @return
	 */
	private String returnFormattedDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return simpleDateFormat.format(date);
	}

	public void usdSalePrjPostiveValue() {
		if (getSalesProjection() != null) {
			if (getSalesProjection().intValue() > 0) {
				if (getiKONRate() != null) {
					setUsdSalesProjection(round(getSalesProjection().multiply(getiKONRate()), 2));
				}
			} else {
				setSalesProjection(null);
				setiKONRate(null);
				setUsdSalesProjection(null);
				RequestContext.getCurrentInstance().execute("negnotallow.show();");
			}

		} else if (getSystemGeneratedSalesProjection() != null && getiKONRate() != null) {
			setUsdSalesProjection(round(getSystemGeneratedSalesProjection().multiply(getiKONRate()), 2));
		} else {
		}
	}

	public void usdSalePrjValue() {
		if (getSalesProjection() != null && getiKONRate() != null) {
			setUsdSalesProjection(round(getSalesProjection().multiply(getiKONRate()), 2));
		} else if (getSystemGeneratedSalesProjection() != null && getiKONRate() != null) {
			setUsdSalesProjection(round(getSystemGeneratedSalesProjection().multiply(getiKONRate()), 2));
		} else {

		}
	}

	/**
	 * This method will call from page, in IKON rate lost focus
	 */
	public void updateUSDSalesProjection() {

		// FX DEAL Value from Treasury Details
		BigDecimal fcAmountfromFXDealBank = fundEstimationService.getFCAmountfromFxDealDetails(getBankId(), getCurrencyId(), getAccountNumber());

		if (fcAmountfromFXDealBank != null) {
			setFcAmountfromFXDeal(fcAmountfromFXDealBank);
			if (getAccumulatedSalesProjectionAmount() != null) {
				if (getAccumulatedSalesProjectionAmount().compareTo(getFcAmountfromFXDeal()) > 0) {
					if (getSystemGeneratedSalesProjection() != null && getSalesProjection() == null) {
						usdSalePrjValue();
					} else {
						BigDecimal subofSaleandFxdeal = getAccumulatedSalesProjectionAmount().subtract(getFcAmountfromFXDeal());
						BigDecimal addofsaleandenter = subofSaleandFxdeal.add(getSalesProjection());
						if(addofsaleandenter!=null){
						if (addofsaleandenter.intValue() >= 0) {
							usdSalePrjValue();
						} else {
							setSalesProjection(null);
							setiKONRate(null);
							setUsdSalesProjection(null);
							RequestContext.getCurrentInstance().execute("notAllowed.show();");
						}
						}
					}

				} else {
					if (getSalesProjection().intValue() < 0) {
						RequestContext.getCurrentInstance().execute("fcmore.show();");
					} else {
						usdSalePrjValue();
					}
				}

			} else {
				usdSalePrjPostiveValue();
			}

		} else {
			usdSalePrjPostiveValue();
		}
	}

	/**
	 * Responsible to set Country ID and CountryName
	 * 
	 * @return
	 */
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void populateValues() {
		System.out.println("populateValues :");
		setBankCountry(null);
		setBankId(null);
		setCurrencyId(null);
		setBankCurrencyName(null);
		setBoobankCurrency(true);
		setBooSelectbankCurrency(false);
		setBooRenderLinkNoofDays(false);
		setBooRenderNormalNoOfdays(true);
		setAccumulatedSalesProjectionAmount(null);
		setAccountNumber(null);
		setBoobankAcc(true);
		setBooSelectbankAcc(false);
		setAccountNumberName(null);
		setCurrentBankBalance(null);
		setValueDatedTrnsaction(null);
		setNoOfDaysEstimation(null);
		setSystemGeneratedSalesProjection(null);
		setSalesProjection(null);
		setiKONRate(null);
		setUsdSalesProjection(null);
		setBooSystemGen(true);
		setBooUpdateSalesProj(false);
		setEditButton(false);
		setBooCheck(false);
		setControl1stAnd2ndTime(false);
		setCountryId(sessionManage.getCountryId());
		setCountryName(fundEstimationService.getCountryNameDesc(sessionManage.getCountryId(), sessionManage.getLanguageId()));
		setCompanyId(sessionManage.getCompanyId());
		setIkonrate(false);

		List<BankCountryPopulationBean> lstBankCountryPopulationBean = fundEstimationService.getBankContryFromView(sessionManage.getCountryId());
		setBankCountryList(lstBankCountryPopulationBean);
		this.setFundEstimationBeanForDealAlertList(null);

		// this.setFundEstimationBeanForDealAlertList(null);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "fundestimationDealAlert.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/fundestimationDealAlert.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void bankChange() {
		setEditButton(false);
		setBooCheck(false);
		setControl1stAnd2ndTime(false);
		setBooRenderLinkNoofDays(false);
		setBooRenderNormalNoOfdays(true);
		setCurrencyId(null);
		setBankCurrencyName(null);
		setBoobankCurrency(true);
		setBooSelectbankCurrency(false);
		setNoOfDaysEstimation(null);

		setAccountNumber(null);
		setBoobankAcc(true);
		setBooSelectbankAcc(false);
		setAccountNumberName(null);
		setCurrentBankBalance(null);
		setValueDatedTrnsaction(null);
		setSalesProjection(null);
		setSystemGeneratedSalesProjection(null);

		setBooUpdateSalesProj(false);
		setBooSystemGen(true);

		setiKONRate(null);
		setUsdSalesProjection(null);
	}

	/**
	 * Calling on currency Change
	 */
	public void currencyChnage() {
	}

	public static BigDecimal round(BigDecimal value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = value;
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd;
	}

	public Date getNextDate(int noOfDay) {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_YEAR, noOfDay);
		Date tomorrow = cal.getTime();
		return tomorrow;
	}

	/**
	 * For Exit Button
	 */
	public void exit() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public void commonSave() throws ParseException { if (isBooCheck()) { //
	 * saveReprojected(); } else { save(); } }
	 */

	public List<FundAccumulationDetailsDatatable> getLstDetails() {
		return lstDetails;
	}

	public void setLstDetails(List<FundAccumulationDetailsDatatable> lstDetails) {
		this.lstDetails = lstDetails;
	}

	List<FundEstimationDays> daysDetails = new ArrayList<FundEstimationDays>();

	public List<FundEstimationDays> getDaysDetails() {
		return daysDetails;
	}

	public void setDaysDetails(List<FundEstimationDays> daysDetails) {
		this.daysDetails = daysDetails;
	}

	List<FundEstimationDaysDtable> daysDetailsDialog = new ArrayList<FundEstimationDaysDtable>();
	List<FundEstimationDaysDtable> addingDaysForCalculation = new ArrayList<FundEstimationDaysDtable>();

	public List<FundEstimationDaysDtable> getAddingDaysForCalculation() {
		return addingDaysForCalculation;
	}

	public void setAddingDaysForCalculation(List<FundEstimationDaysDtable> addingDaysForCalculation) {
		this.addingDaysForCalculation = addingDaysForCalculation;
	}

	public List<FundEstimationDaysDtable> getDaysDetailsDialog() {
		return daysDetailsDialog;
	}

	public void setDaysDetailsDialog(List<FundEstimationDaysDtable> daysDetailsDialog) {
		this.daysDetailsDialog = daysDetailsDialog;
	}

	/**
	 * Author :Rabil Date :12/08/2015
	 */
	public void searchFundEstimationForDealAlert() {
		try{
		fundEstimationBeanForDealAlertList = new ArrayList<FundEstimationPopulationAlertBean>();
		List<TreasuryFundestimationAlertView> fundEstimationDealAlertList = new ArrayList<TreasuryFundestimationAlertView>();

		String projectionDate = DateUtil.todaysDateWithDDMMYY(getProjectionDate(), "");
		fundEstimationDealAlertList = fundEstimationService.getEstimationDealAlertFromView(sessionManage.getCountryId(), getBankCountry(), getCurrencyId(), projectionDate);
		if (fundEstimationDealAlertList != null && fundEstimationDealAlertList.size() > 0) {
			for (TreasuryFundestimationAlertView treasuryFundestimationAlertView : fundEstimationDealAlertList) {
				FundEstimationPopulationAlertBean fundEstimationPopulationAlertBean = new FundEstimationPopulationAlertBean();
				fundEstimationPopulationAlertBean.setApplicationCountryId(treasuryFundestimationAlertView.getApplicationCountryId());
				fundEstimationPopulationAlertBean.setSrNo(treasuryFundestimationAlertView.getSrNo());
				fundEstimationPopulationAlertBean.setApplicationCountryId(treasuryFundestimationAlertView.getApplicationCountryId());
				fundEstimationPopulationAlertBean.setBankCountryId(treasuryFundestimationAlertView.getBankCountryId());
				fundEstimationPopulationAlertBean.setBankId(treasuryFundestimationAlertView.getBankId());
				fundEstimationPopulationAlertBean.setBankCountryId(treasuryFundestimationAlertView.getBankCountryId());
				fundEstimationPopulationAlertBean.setBankShortName(treasuryFundestimationAlertView.getBankShortName());
				fundEstimationPopulationAlertBean.setCurrencyId(treasuryFundestimationAlertView.getCurrencyId());
				if(treasuryFundestimationAlertView.getSalesProjAmnt()!=null){
				fundEstimationPopulationAlertBean.setSalesProjAmnt(treasuryFundestimationAlertView.getSalesProjAmnt().setScale(fundEstimationDetailsBeanService.getDecimalValue(treasuryFundestimationAlertView.getCurrencyId()), RoundingMode.FLOOR));
				}else{
					throw new Exception("Sale Projection Amount Comming null :: salesProjAmnt ");
				}
				fundEstimationPopulationAlertBean.setServiceId(treasuryFundestimationAlertView.getServiceId());
				fundEstimationPopulationAlertBean.setFundEstimationId(treasuryFundestimationAlertView.getFundEstimationId());
				fundEstimationPopulationAlertBean.setProjectionDate(treasuryFundestimationAlertView.getProjectionDate());

				fundEstimationBeanForDealAlertList.add(fundEstimationPopulationAlertBean);
			}
		} else {
			fundEstimationBeanForDealAlertList = new ArrayList<FundEstimationPopulationAlertBean>();
		}
		}catch(NullPointerException ne){
			  log.info("searchFundEstimationForDealAlert::"+ne.getMessage());
			  setExceptionMessage("Method Name::searchFundEstimationForDealAlert"); 
			  RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			  return; 
		}catch (Exception e){
			log.info("searchFundEstimationForDealAlert :"+e.getMessage());
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return;
		}	
	}

	/**
	 * Author :Rabil Date :13/08/2015
	 */

	public void onCellEditSave(CellEditEvent event) {

		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

	}

	public boolean isBooCheck() {
		return BooCheck;
	}

	public void setBooCheck(boolean booCheck) {
		BooCheck = booCheck;
	}

	public void showHideSales() {

		// System.out.println("Rabil 12 While select the nbank country: \t " +
		// getBankId() + "\t show Hides :" + getBankCountry());

		// bankAccordingToBankCountry.clear();
		countryCurrencyList.clear();
		currencyOfBank.clear();
		lstAccountNumber.clear();
		setBankId(null);

		setCurrencyId(null);
		setBankCurrencyName(null);
		setBoobankCurrency(true);
		setBooSelectbankCurrency(false);

		setBooRenderLinkNoofDays(false);
		setBooRenderNormalNoOfdays(true);

		setBooUpdateSalesProj(false);
		setBooSystemGen(true);

		setCurrentBankBalance(null);
		setValueDatedTrnsaction(null);

		setSystemGeneratedSalesProjection(null);
		setSalesProjection(null);
		setiKONRate(null);
		setNoOfDaysEstimation(null);

		setAccountNumber(null);
		setAccountNumberName(null);
		setBoobankAcc(true);
		setBooSelectbankAcc(false);

		setUsdSalesProjection(null);

		setControl1stAnd2ndTime(false);
		setValueDatedTrnsaction(null);
		setCurrentBankBalance(null);
		setIkonrate(false);
      try{
		List<CountryCurrencyPopulationBean> lstCountryCurrency = fundEstimationService.getBankCurrencyFromView(sessionManage.getCountryId(), getBankCountry());

		if (lstCountryCurrency.size() > 0) {
			for (CountryCurrencyPopulationBean ccp : lstCountryCurrency) {
			}

			this.setCountryCurrencyList(lstCountryCurrency);
		}
      }catch(NullPointerException ne){
		  log.info("showHideSales::"+ne.getMessage());
		  setExceptionMessage("Method Name::showHideSales"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
	}catch (Exception e){
		log.info("showHideSales :"+e.getMessage());
		setExceptionMessage(e.getMessage());
		RequestContext.getCurrentInstance().execute("sqlexception.show();");
		return;
	}	
	}

	// rabil

	public void setDealAlert(FundEstimationPopulationAlertBean fundEstimationDealAlertBean) {
		
		try{
		BigDecimal totoalSalesPojecAmountActual = new BigDecimal(0);
		BigDecimal totoalSalesAmount = new BigDecimal(0);
		BigDecimal salesProjAmountActual = new BigDecimal(0);
		BigDecimal salesProjAmountActualPrev = new BigDecimal(0);
		BigDecimal totoalSalesPojecAmountBalan = new BigDecimal(0);

		int i = 0;
		int listSize = 0;

		List<TreasuryFundEstimationDealAlertView> fundEstimationAlertList = new ArrayList<TreasuryFundEstimationDealAlertView>();

		fundEstimationAlertList = fundEstimationService.getEstimationDealAlert(sessionManage.getCountryId(), fundEstimationDealAlertBean.getBankCountryId(), fundEstimationDealAlertBean.getBankId(), fundEstimationDealAlertBean.getCurrencyId(),
				fundEstimationDealAlertBean.getProjectionDate(), fundEstimationDealAlertBean.getFundEstimationId());

		if (!fundEstimationAlertList.isEmpty()) {
			listSize = fundEstimationAlertList.size();
		}

		if (fundEstimationAlertList != null && fundEstimationAlertList.size() > 0) {
			for (TreasuryFundEstimationDealAlertView fundalert : fundEstimationAlertList) {
				i++;
				System.out.println("setDealAlert i :" + i + "\t getFundEstimtaionAlertId Alert Id :" + fundalert.getFundEstimtaionAlertId() + "\t fundalert.getTotalSalesProjectionAmount() :" + fundalert.getTotalSalesProjectionAmount()
						+ "\t fundalert.getTotalSalesProjectionAmount() :" + fundalert.getSalesProjectionAmount());

				FundEstimationPopulationAlertBean fundEstimationPopulationAlertBean = new FundEstimationPopulationAlertBean();

				fundEstimationPopulationAlertBean.setFundEstimtaionAlertId(fundalert.getFundEstimtaionAlertId());
				fundEstimationPopulationAlertBean.setFundEstimationId(fundalert.getFundEstimtaionId());
				fundEstimationPopulationAlertBean.setApplicationCountryId(fundalert.getApplicationCountryId());
				fundEstimationPopulationAlertBean.setBankCountryId(fundalert.getBankCountryId());
				fundEstimationPopulationAlertBean.setBankId(fundalert.getBankId());
				fundEstimationPopulationAlertBean.setBankShortName(fundalert.getBankShortName() == null ? "" : fundalert.getBankShortName());
				fundEstimationPopulationAlertBean.setCurrencyId(fundalert.getCurrencyId());
				fundEstimationPopulationAlertBean.setCompanyId(fundalert.getCompanyId());
				fundEstimationPopulationAlertBean.setServiceId(fundalert.getServiceId());
				if(fundalert.getExchangeRate() == null){
					//getting Exchange Rate start
						String projectionDate = DateUtil.todaysDateWithDDMMYY(getProjectionDate(), "");
						List<TreasuryEstimationDaysView> estimationDaysFromViewList = fundEstimationService.getEstimationDaysFromView(fundalert.getApplicationCountryId(), fundalert.getBankCountryId(),fundalert.getCurrencyId(), projectionDate);
						if(estimationDaysFromViewList.size() !=0){
							  fundEstimationPopulationAlertBean.setExchangeRate(estimationDaysFromViewList.get(0).getiKonRate());  
						}else{
							  fundEstimationPopulationAlertBean.setExchangeRate(fundalert.getExchangeRate() == null ? new BigDecimal(0) : fundalert.getExchangeRate());  
						}
						//getting exchage rate end  
				}else{
					  fundEstimationPopulationAlertBean.setExchangeRate(fundalert.getExchangeRate());	  
				}
				
				//fundEstimationPopulationAlertBean.setExchangeRate(fundalert.getExchangeRate() == null ? new BigDecimal(0) : fundalert.getExchangeRate());
				fundEstimationPopulationAlertBean.setSalesProjAmnt(fundalert.getSalesProjectionAmount() == null ? new BigDecimal(0) : fundalert.getSalesProjectionAmount());

				if (i == 1) {
					salesProjAmountActualPrev = fundalert.getSalesProjectionAmount() == null ? new BigDecimal(0) : fundalert.getSalesProjectionAmount();
					fundEstimationPopulationAlertBean.setTotalSalesProjectionAmount(fundalert.getTotalSalesProjectionAmount() == null ? new BigDecimal(0) : fundalert.getTotalSalesProjectionAmount());
					totoalSalesPojecAmountBalan = fundalert.getTotalSalesProjectionAmount();
				} else {

					totoalSalesPojecAmountBalan = totoalSalesPojecAmountBalan.subtract(salesProjAmountActualPrev);

					fundEstimationPopulationAlertBean.setTotalSalesProjectionAmount(totoalSalesPojecAmountBalan);// fundalert.getTotalSalesProjectionAmount().subtract(salesProjAmountActualPrev));
					// totoalSalesPojecAmountBalan =
					// fundalert.getTotalSalesProjectionAmount().subtract(salesProjAmountActualPrev);
					salesProjAmountActualPrev = fundalert.getSalesProjectionAmount() == null ? new BigDecimal(0) : fundalert.getSalesProjectionAmount();
					// fundEstimationPopulationAlertBean.setTotalSalesProjectionAmount(totoalSalesPojecAmountBalan.subtract(salesProjAmountActualPrev));

				}

				// System.out.println("setDealAlert i :" + i +
				// "\t salesProjAmountActualPrev :" + salesProjAmountActualPrev
				// + "\t totoalSalesPojecAmountBalan :" +
				// totoalSalesPojecAmountBalan + "\t balance : " +
				// totoalSalesPojecAmountBalan);

				fundEstimationPopulationAlertBean.setEmailDate(fundalert.getEmailDate());
				if (fundalert.getFundEstimtaionAlertId().compareTo(BigDecimal.ZERO) == 0 || fundalert.getSalesProjectionAmount().compareTo(BigDecimal.ZERO) == 0 || fundalert.getExchangeRate().compareTo(BigDecimal.ZERO) == 0) {
					fundEstimationPopulationAlertBean.setActionType("Add");
				} else if (fundalert.getFundEstimtaionAlertId().compareTo(BigDecimal.ZERO) != 0 && i != listSize) {
					fundEstimationPopulationAlertBean.setActionType("DEL");
				} else {
					fundEstimationPopulationAlertBean.setActionType("Add");
				}
				fundEstimationBeanForDealAlertSaveList.add(fundEstimationPopulationAlertBean);
			}
		}

		/*
		 * for (FundEstimationPopulationAlertBean
		 * fundEstimationPopulationAlertBean :
		 * fundEstimationBeanForDealAlertSaveList) {
		 * System.out.println("fundEstimationPopulationAlertBean setDealAlert :"
		 * + fundEstimationPopulationAlertBean.getFundEstimtaionAlertId());
		 * 
		 * }
		 */
		RequestContext.getCurrentInstance().execute("PF('alertDetails').show();");
		}catch(NullPointerException ne){
			  log.info("setDealAlert::"+ne.getMessage());
			  setExceptionMessage("Method Name::setDealAlert"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		}catch (Exception e){
			log.info("setDealAlert :"+e.getMessage());
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return;
		}	

	}

	public String addRow(String actionType, FundEstimationPopulationAlertBean alertAddBean) {
		try{

		if (alertAddBean.getSalesProjAmnt().compareTo(BigDecimal.ZERO) == 0 || alertAddBean.getSalesProjAmnt() == null) {
			RequestContext context = RequestContext.getCurrentInstance();
			msg = props.getString("valmsg.salesProj");
			this.setMsg(msg);
			context.execute("PF('valDialog').show();");
			return null;
		} else if (alertAddBean.getExchangeRate().compareTo(BigDecimal.ZERO) == 0 || alertAddBean.getExchangeRate() == null) {
			RequestContext context = RequestContext.getCurrentInstance();
			msg = props.getString("valmsg.exchangeRate");
			this.setMsg(msg);
			context.execute("PF('valDialog').show();");
			return null;

		} else {
			this.setMsg(null);
		}

		if (actionType.equalsIgnoreCase("add") && msg == null) {
			alertAddBean.setActionType("DEL");
			FundEstimationPopulationAlertBean fundEstimationPopulationAlertBean = new FundEstimationPopulationAlertBean();
			fundEstimationPopulationAlertBean.setFundEstimtaionAlertId(new BigDecimal(0));
			fundEstimationPopulationAlertBean.setFundEstimationId(alertAddBean.getFundEstimationId());
			fundEstimationPopulationAlertBean.setApplicationCountryId(alertAddBean.getApplicationCountryId());
			fundEstimationPopulationAlertBean.setBankCountryId(alertAddBean.getBankCountryId());
			fundEstimationPopulationAlertBean.setBankId(alertAddBean.getBankId());
			fundEstimationPopulationAlertBean.setBankShortName(alertAddBean.getBankShortName() == null ? "" : alertAddBean.getBankShortName());
			fundEstimationPopulationAlertBean.setCurrencyId(alertAddBean.getCurrencyId());
			fundEstimationPopulationAlertBean.setCompanyId(alertAddBean.getCompanyId());
			fundEstimationPopulationAlertBean.setServiceId(alertAddBean.getServiceId());
			//getting Exchange Rate start
			String projectionDate = DateUtil.todaysDateWithDDMMYY(getProjectionDate(), "");
			List<TreasuryEstimationDaysView> estimationDaysFromViewList = fundEstimationService.getEstimationDaysFromView(alertAddBean.getApplicationCountryId(), alertAddBean.getBankCountryId(),alertAddBean.getCurrencyId(), projectionDate);
			if(estimationDaysFromViewList.size() !=0){
				  fundEstimationPopulationAlertBean.setExchangeRate(estimationDaysFromViewList.get(0).getiKonRate());  
			}else{
				  fundEstimationPopulationAlertBean.setExchangeRate(alertAddBean.getExchangeRate() == null ? new BigDecimal(0) : alertAddBean.getExchangeRate());  
			}
			//getting exchage rate end
			//fundEstimationPopulationAlertBean.setExchangeRate(new BigDecimal(0));

			fundEstimationPopulationAlertBean.setTotalSalesProjectionAmount(alertAddBean.getTotalSalesProjectionAmount() == null ? new BigDecimal(0) : alertAddBean.getTotalSalesProjectionAmount().subtract(alertAddBean.getSalesProjAmnt()));
			fundEstimationPopulationAlertBean.setSalesProjAmnt(new BigDecimal(0));
			fundEstimationPopulationAlertBean.setEmailDate(alertAddBean.getEmailDate());
			fundEstimationPopulationAlertBean.setActionType("add");
			fundEstimationBeanForDealAlertSaveList.add(fundEstimationPopulationAlertBean);
		} else if (actionType.equalsIgnoreCase("del") && alertAddBean.getFundEstimtaionAlertId().compareTo(BigDecimal.ZERO) != 0) {

			FundEstimationAlert fundestimationAlertForSave = new FundEstimationAlert();

			fundestimationAlertForSave.setFundEstimtaionAlertId(alertAddBean.getFundEstimtaionAlertId());
			fundestimationAlertForSave.setApplicationCountryId(alertAddBean.getApplicationCountryId());
			fundestimationAlertForSave.setBankCountryId(alertAddBean.getBankCountryId());
			fundestimationAlertForSave.setBankId(alertAddBean.getBankId());
			fundestimationAlertForSave.setCompanyId(alertAddBean.getCompanyId());
			fundestimationAlertForSave.setCurrencyId(alertAddBean.getCurrencyId());
			fundestimationAlertForSave.setServiceId(alertAddBean.getServiceId());
			fundestimationAlertForSave.setFundEstimtaionId(alertAddBean.getFundEstimationId());
			fundestimationAlertForSave.setSalesProjectionAmount(alertAddBean.getSalesProjAmnt());
			fundestimationAlertForSave.setExchangeRate(alertAddBean.getExchangeRate());
			fundestimationAlertForSave.setModifiedDate(new Date());
			fundestimationAlertForSave.setFundEstimtaionAlertId(alertAddBean.getFundEstimtaionAlertId());
			fundestimationAlertForSave.setIsActive("N");
			fundestimationAlertForSave.setModifiedBy(sessionManage.getUserName());

			System.out.println("fundEstimationBeanForDealAlertSaveList Size before delete :" + fundEstimationBeanForDealAlertSaveList.size());

			fundEstimationService.saveAndUpadateFundEstimationAlert(fundestimationAlertForSave);
			fundEstimationBeanForDealAlertSaveList.remove(alertAddBean);
		} else if (actionType.equalsIgnoreCase("del")) {
			fundEstimationBeanForDealAlertSaveList.remove(alertAddBean);
		}
		return null;
		}catch(NullPointerException ne){
			  log.info("add::"+ne.getMessage());
			  setExceptionMessage("Method Name::add"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return ""; 
		}catch (Exception e){
			log.info("add :"+e.getMessage());
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return "";
		}	

	}

	public void saveAlertDetails() {
		try {

			for (FundEstimationPopulationAlertBean funeAlertSave : fundEstimationBeanForDealAlertSaveList) {
				FundEstimationAlert fundestimationAlertForSave = new FundEstimationAlert();
				fundestimationAlertForSave.setFundEstimtaionAlertId(funeAlertSave.getFundEstimtaionAlertId());
				fundestimationAlertForSave.setApplicationCountryId(funeAlertSave.getApplicationCountryId());
				fundestimationAlertForSave.setBankCountryId(funeAlertSave.getBankCountryId());
				fundestimationAlertForSave.setBankId(funeAlertSave.getBankId());
				fundestimationAlertForSave.setCompanyId(funeAlertSave.getCompanyId());
				fundestimationAlertForSave.setCurrencyId(funeAlertSave.getCurrencyId());
				fundestimationAlertForSave.setServiceId(funeAlertSave.getServiceId());
				fundestimationAlertForSave.setFundEstimtaionId(funeAlertSave.getFundEstimationId());
				fundestimationAlertForSave.setSalesProjectionAmount(funeAlertSave.getSalesProjAmnt());
				fundestimationAlertForSave.setExchangeRate(funeAlertSave.getExchangeRate());
				fundestimationAlertForSave.setCreatedDate(new Date());
				fundestimationAlertForSave.setIsActive("Y");
				fundestimationAlertForSave.setCreatedBy(sessionManage.getUserName());
				fundestimationAlertForSave.setProjectionDate(new Date());

				if (funeAlertSave.getFundEstimtaionAlertId().compareTo(BigDecimal.ZERO) == 0 && funeAlertSave.getSalesProjAmnt().compareTo(BigDecimal.ZERO) != 0 && funeAlertSave.getExchangeRate().compareTo(BigDecimal.ZERO) != 0) {
					fundEstimationService.saveFundEstimationAlert(fundestimationAlertForSave);
				} else if (funeAlertSave.getFundEstimtaionAlertId().compareTo(BigDecimal.ZERO) != 0) {
					fundEstimationService.saveAndUpadateFundEstimationAlert(fundestimationAlertForSave);
				}
			}
			RequestContext.getCurrentInstance().execute("complete.show();");
		}catch(NullPointerException ne){
			  log.info("save::"+ne.getMessage());
			  setExceptionMessage("Method Name::save"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		}catch (Exception e){
			log.info("save :"+e.getMessage());
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return;
		} finally {
			this.setFundEstimationBeanForDealAlertSaveList(null);
		}

	}

	public void closeClear() {
		try {
			// fundEstimationBeanForDealAlertList = new
			// ArrayList<FundEstimationPopulationAlertBean>();
			fundEstimationBeanForDealAlertSaveList = new ArrayList<FundEstimationPopulationAlertBean>();
			RequestContext.getCurrentInstance().execute("complete.hide();");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<BankAccountDetails> getLstAccountNumber() {
		return lstAccountNumber;
	}

	public void setLstAccountNumber(List<BankAccountDetails> lstAccountNumber) {
		this.lstAccountNumber = lstAccountNumber;
	}

	/**
	 * BankCurrency will check how many currency available and retrieve
	 * according to BankId
	 */

	public void bankCurrency() throws ParseException {

		currencyOfBank.clear();
		lstAccountNumber.clear();
		bankChange();
		setIkonrate(false);
		List<BankAccountDetails> pbankcurrency = fundEstimationService.getCurrencyOfBank(getBankId());

		bankcurrencySize = pbankcurrency.size();
		if (bankcurrencySize == 0) {
			setBoobankCurrency(true);
			setBooSelectbankCurrency(false);
			setBooSaveButton(true);
			RequestContext.getCurrentInstance().execute("dataNotFund.show();");
		} else if (bankcurrencySize == 1) {

			for (BankAccountDetails element : pbankcurrency) {
				setBankCurrencyId(element.getFsCurrencyMaster().getCurrencyId());
				setBankCurrencyName(element.getFsCurrencyMaster().getCurrencyName());
			}
			setCurrencyId(getBankCurrencyId());
			setBoobankCurrency(true);
			setBooSelectbankCurrency(false);
			setBooSaveButton(false);
			// populateAccountNumber();
			if(getCurrencyId()!=null){
			if (getCurrencyId().equals(fundEstimationDetailsBeanService.getUsdPk(Constants.FUND_ESTIMATION_USD_CURRENCY_CODE))) {
				setiKONRate(new BigDecimal(Constants.FUND_ESTIMATION_IKON_RATE));
				setIkonrate(true);
			} else {
				setIkonrate(false);
			}
			}

		} else {
			setBoobankCurrency(false);
			setBooSelectbankCurrency(true);
			setBooSaveButton(false);
			setCurrencyOfBank(pbankcurrency);
		}

	}

	private Boolean ikonrate = false;

	public Boolean getIkonrate() {
		return ikonrate;
	}

	public void setIkonrate(Boolean ikonrate) {
		this.ikonrate = ikonrate;
	}

	/**
	 * @return the projectionDate
	 */
	public Date getProjectionDate() {
		return projectionDate;
	}

	/**
	 * @param projectionDate
	 *            the projectionDate to set
	 */
	public void setProjectionDate(Date projectionDate) {
		this.projectionDate = projectionDate;
	}

	/**
	 * /**
	 * 
	 * @return the countryCurrencyList
	 */
	public List<CountryCurrencyPopulationBean> getCountryCurrencyList() {
		return countryCurrencyList;
	}

	/**
	 * @param countryCurrencyList
	 *            the countryCurrencyList to set
	 */
	public void setCountryCurrencyList(List<CountryCurrencyPopulationBean> countryCurrencyList) {
		this.countryCurrencyList = countryCurrencyList;
	}

	/**
	 * @return the fundEstimationList
	 */
	public List<FundEstimationPopulationBean> getFundEstimationList() {
		return fundEstimationList;
	}

	/**
	 * @param fundEstimationList
	 *            the fundEstimationList to set
	 */
	public void setFundEstimationList(List<FundEstimationPopulationBean> fundEstimationList) {
		this.fundEstimationList = fundEstimationList;
	}

	public boolean isNoOfDaysEstimationBol() {
		return noOfDaysEstimationBol;
	}

	public void setNoOfDaysEstimationBol(boolean noOfDaysEstimationBol) {
		this.noOfDaysEstimationBol = noOfDaysEstimationBol;
	}

	public int getNoofDaysPopup() {
		return noofDaysPopup;
	}

	public void setNoofDaysPopup(int noofDaysPopup) {
		this.noofDaysPopup = noofDaysPopup;
	}

	public BigDecimal getDayofweek() {
		return dayofweek;
	}

	public void setDayofweek(BigDecimal dayofweek) {
		this.dayofweek = dayofweek;
	}

	public BigDecimal getNoofOccurance() {
		return noofOccurance;
	}

	public void setNoofOccurance(BigDecimal noofOccurance) {
		this.noofOccurance = noofOccurance;
	}

	public BigDecimal getFcAmountfromFXDeal() {
		return fcAmountfromFXDeal;
	}

	public void setFcAmountfromFXDeal(BigDecimal fcAmountfromFXDeal) {
		this.fcAmountfromFXDeal = fcAmountfromFXDeal;
	}

	public boolean isBooRenderLinkNoofDays() {
		return booRenderLinkNoofDays;
	}

	public void setBooRenderLinkNoofDays(boolean booRenderLinkNoofDays) {
		this.booRenderLinkNoofDays = booRenderLinkNoofDays;
	}

	public boolean isBooRenderNormalNoOfdays() {
		return booRenderNormalNoOfdays;
	}

	public void setBooRenderNormalNoOfdays(boolean booRenderNormalNoOfdays) {
		this.booRenderNormalNoOfdays = booRenderNormalNoOfdays;
	}

	public BigDecimal getUpdateDaysId() {
		return updateDaysId;
	}

	public void setUpdateDaysId(BigDecimal updateDaysId) {
		this.updateDaysId = updateDaysId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public String getAccountNumberName() {
		return accountNumberName;
	}

	public void setAccountNumberName(String accountNumberName) {
		this.accountNumberName = accountNumberName;
	}

	public String getBankCurrencyName() {
		return bankCurrencyName;
	}

	public void setBankCurrencyName(String bankCurrencyName) {
		this.bankCurrencyName = bankCurrencyName;
	}

	public BigDecimal getBankCurrencyId() {
		return bankCurrencyId;
	}

	public void setBankCurrencyId(BigDecimal bankCurrencyId) {
		this.bankCurrencyId = bankCurrencyId;
	}

	public List<BankAccountDetails> getCurrencyOfBank() {
		return currencyOfBank;
	}

	public void setCurrencyOfBank(List<BankAccountDetails> currencyOfBank) {
		this.currencyOfBank = currencyOfBank;
	}

	public Boolean getBoobankCurrency() {
		return boobankCurrency;
	}

	public void setBoobankCurrency(Boolean boobankCurrency) {
		this.boobankCurrency = boobankCurrency;
	}

	public Boolean getBooSelectbankCurrency() {
		return booSelectbankCurrency;
	}

	public void setBooSelectbankCurrency(Boolean booSelectbankCurrency) {
		this.booSelectbankCurrency = booSelectbankCurrency;
	}

	public BigDecimal getAccountId() {
		return accountId;
	}

	public void setAccountId(BigDecimal accountId) {
		this.accountId = accountId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public boolean isBooSaveButton() {
		return booSaveButton;
	}

	public void setBooSaveButton(boolean booSaveButton) {
		this.booSaveButton = booSaveButton;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getBankCountry() {
		return bankCountry;
	}

	public void setBankCountry(BigDecimal bankCountry) {
		this.bankCountry = bankCountry;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getCurrencyId() {

		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getCurrentBankBalance() {
		return currentBankBalance;
	}

	public void setCurrentBankBalance(BigDecimal currentBankBalance) {
		this.currentBankBalance = currentBankBalance;
	}

	public void setLstCompanyId(List<CompanyMasterDesc> lstCompanyId) {
		this.lstCompanyId = lstCompanyId;
	}

	public BigDecimal getAccumulatedSalesProjectionAmount() {
		return accumulatedSalesProjectionAmount;
	}

	public void setAccumulatedSalesProjectionAmount(BigDecimal accumulatedSalesProjectionAmount) {
		this.accumulatedSalesProjectionAmount = accumulatedSalesProjectionAmount;
	}

	public String getAccumulatedSalesUSDEquivalent() {
		return accumulatedSalesUSDEquivalent;
	}

	public void setAccumulatedSalesUSDEquivalent(String accumulatedSalesUSDEquivalent) {
		this.accumulatedSalesUSDEquivalent = accumulatedSalesUSDEquivalent;
	}

	public BigDecimal getValueDatedTrnsaction() {
		return valueDatedTrnsaction;
	}

	public void setValueDatedTrnsaction(BigDecimal valueDatedTrnsaction) {
		this.valueDatedTrnsaction = valueDatedTrnsaction;
	}

	public BigDecimal getNoOfDaysEstimation() {
		return noOfDaysEstimation;
	}

	public void setNoOfDaysEstimation(BigDecimal noOfDaysEstimation) {
		this.noOfDaysEstimation = noOfDaysEstimation;
	}

	public int getAccNumberSize() {
		return accNumberSize;
	}

	public void setAccNumberSize(int accNumberSize) {
		this.accNumberSize = accNumberSize;
	}

	public Boolean getBoobankAcc() {
		return boobankAcc;
	}

	public void setBoobankAcc(Boolean boobankAcc) {
		this.boobankAcc = boobankAcc;
	}

	public Boolean getBooSelectbankAcc() {
		return booSelectbankAcc;
	}

	public void setBooSelectbankAcc(Boolean booSelectbankAcc) {
		this.booSelectbankAcc = booSelectbankAcc;
	}

	public BigDecimal getSalesProjection() {
		return salesProjection;
	}

	public void setSalesProjection(BigDecimal salesProjection) {
		this.salesProjection = salesProjection;
	}

	public BigDecimal getUsdSalesProjection() {
		return usdSalesProjection;
	}

	public void setUsdSalesProjection(BigDecimal usdSalesProjection) {
		this.usdSalesProjection = usdSalesProjection;
	}

	public BigDecimal getiKONRate() {
		return iKONRate;
	}

	public void setiKONRate(BigDecimal iKONRate) {
		this.iKONRate = iKONRate;
	}

	public boolean isControl1stAnd2ndTime() {
		return Control1stAnd2ndTime;
	}

	public void setControl1stAnd2ndTime(boolean control1stAnd2ndTime) {
		Control1stAnd2ndTime = control1stAnd2ndTime;
	}

	public BigDecimal getSystemGeneratedSalesProjection() {
		return systemGeneratedSalesProjection;
	}

	public void setSystemGeneratedSalesProjection(BigDecimal systemGeneratedSalesProjection) {
		this.systemGeneratedSalesProjection = systemGeneratedSalesProjection;
	}

	public boolean isBooSystemGen() {
		return booSystemGen;
	}

	public void setBooSystemGen(boolean booSystemGen) {
		this.booSystemGen = booSystemGen;
	}

	public boolean isBooUpdateSalesProj() {
		return booUpdateSalesProj;
	}

	public void setBooUpdateSalesProj(boolean booUpdateSalesProj) {
		this.booUpdateSalesProj = booUpdateSalesProj;
	}

	public boolean isEditButton() {
		return editButton;
	}

	public void setEditButton(boolean editButton) {
		this.editButton = editButton;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public List<BankCountryPopulationBean> getBankCountryList() {
		return bankCountryList;
	}

	public void setBankCountryList(List<BankCountryPopulationBean> bankCountryList) {
		this.bankCountryList = bankCountryList;
	}

	public List<BankApplicability> getBankAccordingToBankCountry() {
		return bankAccordingToBankCountry;
	}

	public void setBankAccordingToBankCountry(List<BankApplicability> bankAccordingToBankCountry) {
		this.bankAccordingToBankCountry = bankAccordingToBankCountry;
	}

	public List<FundEstimationPopulationAlertBean> getFundEstimationBeanForDealAlertList() {
		return fundEstimationBeanForDealAlertList;
	}

	public void setFundEstimationBeanForDealAlertList(List<FundEstimationPopulationAlertBean> fundEstimationBeanForDealAlertList) {
		this.fundEstimationBeanForDealAlertList = fundEstimationBeanForDealAlertList;
	}

	public List<FundEstimationPopulationAlertBean> getFundEstimationBeanForDealAlertSaveList() {
		return fundEstimationBeanForDealAlertSaveList;
	}

	public void setFundEstimationBeanForDealAlertSaveList(List<FundEstimationPopulationAlertBean> fundEstimationBeanForDealAlertSaveList) {
		this.fundEstimationBeanForDealAlertSaveList = fundEstimationBeanForDealAlertSaveList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

}
