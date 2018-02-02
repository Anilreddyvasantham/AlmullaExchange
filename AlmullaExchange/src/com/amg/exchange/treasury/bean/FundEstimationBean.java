package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;

import org.joda.time.LocalDate;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.FundEstimation;
import com.amg.exchange.treasury.model.FundEstimationDays;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.treasury.service.FundEstimationDetailsBeanService;
import com.amg.exchange.treasury.service.IBankTransferService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.IFxDealwithSupplierService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.treasury.service.IUsdGlobalRequirementDetailsService;
import com.amg.exchange.treasury.viewModel.TreasuryEstimationDaysView;
import com.amg.exchange.treasury.viewModel.TreasuryFundestimationView;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.DateUtil;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("fundEstimationBean")
@Scope("session")
public class FundEstimationBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(FundEstimationBean.class);

	// private String projectionDate = null;
	// Added By Rabil on 10/08/2015

	private Date projectionDate = null;

	private Date currentDate = new Date();

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
	private List<TreasuryFundestimationView> fundEstimationfromViewList = new ArrayList<TreasuryFundestimationView>();
	private List<TreasuryEstimationDaysView> estimationDaysFromViewList = new ArrayList<TreasuryEstimationDaysView>();

	private String exceptionMessage=null;	
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
	private String errorMessage;
	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getTomAmount() {
		return tomAmount;
	}

	public void setTomAmount(BigDecimal tomAmount) {
		this.tomAmount = tomAmount;
	}

	public BigDecimal getSpotAmount() {
		return spotAmount;
	}

	public void setSpotAmount(BigDecimal spotAmount) {
		this.spotAmount = spotAmount;
	}

	private BigDecimal cashAmount;
	private BigDecimal tomAmount;
	private BigDecimal spotAmount;

	private SessionStateManage sessionManage = new SessionStateManage();

	private BigDecimal updateDaysId;

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
						if (addofsaleandenter.intValue() >= 0) {
							usdSalePrjValue();
						} else {
							setSalesProjection(null);
							setiKONRate(null);
							setUsdSalesProjection(null);
							RequestContext.getCurrentInstance().execute("notAllowed.show();");
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
		setExceptionMessage(null);
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
		setProjectionDate(new Date());
		// setCountryCurrencyList(null);

		List<BankCountryPopulationBean> lstBankCountryPopulationBean = fundEstimationService.getBankContryFromView(sessionManage.getCountryId());

		setBankCountryList(lstBankCountryPopulationBean);
		setFundEstimationList(null);

		setSpotAmount( null);
		setCashAmount( null);
		setTomAmount( null);

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "fundestimation.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/fundestimation.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Responsible to populate Bank Country from Bank Master
	 * 
	 * @return
	 */

	/*
	 * public List<BankCountryPopulationBean> getBankCountryList() { return
	 * fundEstimationService.getBankContry(sessionManage.getCountryId()); }
	 */

	/*
	 * public List<BankApplicability> getBankAccordingToBankCountry() { return
	 * generalService
	 * .getCoresBankLocalBankInterBankList(sessionManage.getCountryId(),
	 * getBankCountry()); }
	 */

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
	 * Called on blur event
	 */
	public void updateSalesProjection() {
		setBooSystemGen(false);
		setBooUpdateSalesProj(true);
		setSalesProjection(null);
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

	public void noofdayschecking(FacesContext context, UIComponent component, Object value) {
		BigDecimal noofdaysval = (BigDecimal) value;

		if (noofdaysval.intValue() <= 0 || noofdaysval.intValue() > 9) {
			setNoOfDaysEstimation(null);
			FacesMessage msg = new FacesMessage("Please Enter Number with in [ 1-9 ]", "Please Enter Number with in [ 1-9 ]");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	/**
	 * Responsible for Save
	 * 
	 * @throws ParseException
	 *             modified by rabil
	 */
	public void save() throws ParseException {
		// if(fundEstimationList.isEmpty()){
		try{
			/*	for (FundEstimationPopulationBean fundEstimationlistVal : fundEstimationList) {
				if((fundEstimationlistVal.getSpotAmount()!=null&&fundEstimationlistVal.getSpotAmount()!=BigDecimal.ZERO)|(fundEstimationlistVal.getCashAmount()!=null&&fundEstimationlistVal.getCashAmount()!=BigDecimal.ZERO)|(fundEstimationlistVal.getTomAmount()!=null&&getTomAmount()!=null)){
				System.out.println("========");
				}else{
					RequestContext.getCurrentInstance().execute("pleaseentertom.show();");
					return;
				}
				}*/

			for (FundEstimationPopulationBean fundEstimationlistVal : fundEstimationList) {
				//if((fundEstimationlistVal.getSpotAmount()!=null&&fundEstimationlistVal.getSpotAmount()!=BigDecimal.ZERO)|(getCashAmount()!=null&&getCashAmount()!=BigDecimal.ZERO)|(getTomAmount()!=null&&getTomAmount()!=null)){
				System.out.println("fundEstimationlistVal Total usd value :"+fundEstimationlistVal.getUsdTotalProjectionAmount()+"\t Display :"+fundEstimationlistVal.getUnfundedTrnxForDisplay());
				if(fundEstimationlistVal.getAdditionalSalesProjAmnt() != null){
					if ( fundEstimationlistVal.getTotalProjectionAmount().compareTo(BigDecimal.ZERO) != 0) {

						/*
						 * System.out.println(" GetApplicationCountryId :" +
						 * fundEstimationlistVal.getApplicationCountryId() +
						 * "\n getCompanyId " + getCompanyId() + "\n getBankCountryId "
						 * + fundEstimationlistVal.getBankCountryId() +
						 * "\n getCurrencyId " + fundEstimationlistVal.getCurrencyId() +
						 * "\n getBankId " + fundEstimationlistVal.getBankId() +
						 * "\n getProjectionDate " + getProjectionDate() +
						 * "\n getNoOfDaysEstimation " + getNoOfDaysEstimation() +
						 * "\n getiKONRate " + getiKONRate() +
						 * "\n getPreviousday current balance " +
						 * fundEstimationlistVal.getForeignCurrencyBalance() +
						 * "\n getPreviousday current balance " +
						 * fundEstimationlistVal.getUnfundedTrnx() +
						 * "\n Foreign currency value :" +
						 * fundEstimationlistVal.getForeignCurrencyBalance() +
						 * "\n getSystemGeneratedSalesProjection:" +
						 * getSystemGeneratedSalesProjection() //
						 * getSystemGeneratedSalesProjection() + "\n getServiceId:" +
						 * fundEstimationlistVal.getServiceId() +
						 * "\t fundEstimationlistVal  Fund ID :" +
						 * fundEstimationlistVal.getFundEstimationId());
						 */
						FundEstimation fundEstimation = new FundEstimation();

						fundEstimation.setFundEstimtaionId(fundEstimationlistVal.getFundEstimationId());
						fundEstimation.setApplicationCountryId(fundEstimationlistVal.getApplicationCountryId());
						fundEstimation.setCompanyId(getCompanyId());
						fundEstimation.setCurrencyId(getCurrencyId());
						fundEstimation.setBankCountryId(getBankCountry());

						fundEstimation.setBankId(fundEstimationlistVal.getBankId() == null ? new BigDecimal(0) : fundEstimationlistVal.getBankId());
						fundEstimation.setProjectionDate(getProjectionDate());
						fundEstimation.setEstimateNumberOfDays(getNoOfDaysEstimation());
						fundEstimation.setIkonRate(getiKONRate());
						fundEstimation.setPreviousDaysCurrentBalance(fundEstimationlistVal.getForeignCurrencyBalance() == null ? new BigDecimal(0) : fundEstimationlistVal.getForeignCurrencyBalance());
						fundEstimation.setCashAmount(fundEstimationlistVal.getCashAmount() == null ? BigDecimal.ZERO : fundEstimationlistVal.getCashAmount());
						fundEstimation.setSpotAmount(fundEstimationlistVal.getSpotAmount()  == null ? BigDecimal.ZERO : fundEstimationlistVal.getSpotAmount());
						fundEstimation.setTomAmount(fundEstimationlistVal.getTomAmount()  == null ? BigDecimal.ZERO : fundEstimationlistVal.getTomAmount());
						
						BigDecimal totalAdditionalSaleProjection =  fundEstimationlistVal.getCashAmount().add(fundEstimationlistVal.getSpotAmount()).add(fundEstimationlistVal.getTomAmount());

						// added for change Sales Projection CR
						if (fundEstimationlistVal.getCurrencyId().equals(fundEstimationDetailsBeanService.getUsdPk(Constants.FUND_ESTIMATION_USD_CURRENCY_CODE))) {
							if (fundEstimationlistVal.getForeignCurrencyBalance() != null) {
								fundEstimation.setUsdCurrentBalanace(fundEstimationlistVal.getForeignCurrencyBalance().multiply(getiKONRate()).setScale(fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId()), RoundingMode.FLOOR));
							}
							fundEstimation.setValueDatedTransaction(fundEstimationlistVal.getUnfundedTrnx());
							if(fundEstimationlistVal.getUnfundedTrnx()!=null)
							{
								fundEstimation.setuSdValueDatedTransaction(round(fundEstimationlistVal.getUnfundedTrnx().multiply(getiKONRate()), 1));
							}
						} else {
							if (fundEstimationlistVal.getForeignCurrencyBalance() != null) {
								fundEstimation.setUsdCurrentBalanace(fundEstimationlistVal.getForeignCurrencyBalance().multiply(getiKONRate()).setScale(fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId()), RoundingMode.FLOOR));
							}

							fundEstimation.setValueDatedTransaction(fundEstimationlistVal.getUnfundedTrnx());
							if(fundEstimationlistVal.getUnfundedTrnx()!=null)
							{
								fundEstimation.setuSdValueDatedTransaction(round(fundEstimationlistVal.getUnfundedTrnx().multiply(getiKONRate()), fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId())));
							}

						}

						fundEstimation.setServiceId(fundEstimationlistVal.getServiceId());
						if(fundEstimationlistVal.getForeignCurrencyBalance()!=null){
							fundEstimation.setUsdCurrentBalanace(fundEstimationlistVal.getForeignCurrencyBalance().multiply(getiKONRate()).setScale(fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId()), RoundingMode.FLOOR));
						}
						// fundEstimation.setSystemGeneratedSalesProjectionAmount(fundEstimationlistVal.getSalesProjAmnt().add(fundEstimationlistVal.getAdditionalSalesProjAmnt()));
						// // getSystemGeneratedSalesProjection()

						System.out.println("value :" + fundEstimationlistVal.getAdditionalSalesProjAmnt().signum());

						fundEstimation.setSystemGeneratedSalesProjectionAmount(fundEstimationlistVal.getAdditionalSalesProjAmnt().signum() == 1 ? fundEstimationlistVal.getSalesProjAmnt().add(totalAdditionalSaleProjection)
								: fundEstimationlistVal.getSalesProjAmnt().subtract(totalAdditionalSaleProjection)); // getSystemGeneratedSalesProjection()

						fundEstimation.setCreatedBy(sessionManage.getUserName());

						fundEstimation.setCreatedDate(new Date());
						fundEstimation.setUsdValueTotalSalesProjection(fundEstimationlistVal.getUsdTotalProjectionAmount());
						if(fundEstimation.getFundEstimtaionId() != null){
							if (fundEstimation.getFundEstimtaionId().compareTo(BigDecimal.ZERO) != 0) {
								fundEstimationService.saveAndUpadate(fundEstimation);
							} else {
								fundEstimationService.save(fundEstimation);
							}
						}else{
							throw new Exception("Addtion sale projection comming null value:::(fundEstimtaionId)");
						}


						/** FOR SAVING FundEstimationDetails **/

						FundEstimationDetails fundEstimationDetails = new FundEstimationDetails();

						fundEstimationDetails.setFundEstimtaionId(fundEstimation);
						fundEstimationDetails.setApplicationCountryId(fundEstimationlistVal.getApplicationCountryId());
						fundEstimationDetails.setCompanyId(getCompanyId());
						fundEstimationDetails.setCurrencyId(getCurrencyId());
						fundEstimationDetails.setBankCountryId(getBankCountry());

						fundEstimationDetails.setBankId(fundEstimationlistVal.getBankId() == null ? new BigDecimal(0) : fundEstimationlistVal.getBankId());

						fundEstimationDetails.setProjectionDate(getProjectionDate()); //
						fundEstimationDetails.setIkonRate(getiKONRate());

						//fundEstimationDetails.setExFundEstimationDetails(new BigDecimal(0));
						// added for change Sales Projection CR

						if (getCurrencyId().equals(fundEstimationDetailsBeanService.getUsdPk(Constants.FUND_ESTIMATION_USD_CURRENCY_CODE))) {
							if (getSalesProjection() != null && getSalesProjection().toString().length() > 0) {
								fundEstimationDetails.setSalesForeignCurrencyProjection(fundEstimationlistVal.getAdditionalSalesProjAmnt());
								if(fundEstimationlistVal.getAdditionalSalesProjAmnt()!=null)
								{
									fundEstimationDetails.setUsdValue(fundEstimationlistVal.getAdditionalSalesProjAmnt().multiply(getiKONRate()).setScale(fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId()), RoundingMode.FLOOR));
								}

							} else {
								fundEstimationDetails.setSalesForeignCurrencyProjection(fundEstimationlistVal.getAdditionalSalesProjAmnt());
								if(getSystemGeneratedSalesProjection()!=null)
								{
									fundEstimationDetails.setUsdValue(getSystemGeneratedSalesProjection().multiply(getiKONRate()).setScale(fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId()), RoundingMode.FLOOR));
								}

							}
						} else if (fundEstimationlistVal.getAdditionalSalesProjAmnt() != null && fundEstimationlistVal.getAdditionalSalesProjAmnt().toString().length() > 0) {
							fundEstimationDetails.setSalesForeignCurrencyProjection(fundEstimationlistVal.getAdditionalSalesProjAmnt());
							fundEstimationDetails.setUsdValue(fundEstimationlistVal.getAdditionalSalesProjAmnt().multiply(getiKONRate()).setScale(fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId()), RoundingMode.FLOOR));
						} else {
							fundEstimationDetails.setSalesForeignCurrencyProjection(getSystemGeneratedSalesProjection());
							if(getSystemGeneratedSalesProjection()!=null)
							{
								fundEstimationDetails.setUsdValue(getSystemGeneratedSalesProjection().multiply(getiKONRate()).setScale(fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId()), RoundingMode.FLOOR));
							}

						}
						fundEstimationDetails.setCreatedBy(sessionManage.getUserName());
						fundEstimationDetails.setCreatedDate(new Date());

						fundEstimationService.saveFundEstimtaionDetails(fundEstimationDetails);

						System.out.println("RRRRR getNoOfDaysEstimation :" + getNoOfDaysEstimation().intValue() + " =====\n");

						//try {

						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
						List<FundEstimationDaysDtable> lstFundEstimationDaysDtable=fundEstimationlistVal.getAddingDaysForCalculation();
						int szie=0;
						if(lstFundEstimationDaysDtable!=null)
						{
							szie=lstFundEstimationDaysDtable.size();
						}

						for (int i = 0; i < szie; i++) {

							FundEstimationDaysDtable fundestmDaysdtable =lstFundEstimationDaysDtable.get(i);

							//HashMap<String, String> outPutValues =fundEstimationDetailsBeanService.callFundEstimation(inputValues);
							if(!fundestmDaysdtable.isAlreadyInsert())
							{
								FundEstimationDays fundEstimationDay = new FundEstimationDays();
								fundEstimationDay.setFundEstimtaionId(fundEstimation);
								fundEstimationDay.setFundEstimationDetailsId(fundEstimationDetails);

								fundEstimationDay.setApplicationCountryId(fundEstimationlistVal.getApplicationCountryId());
								fundEstimationDay.setCompanyId(getCompanyId());
								fundEstimationDay.setCurrencyId(getCurrencyId());
								fundEstimationDay.setBankCountryId(getBankCountry());

								fundEstimationDay.setProjectionDate(getProjectionDate());
								fundEstimationDay.setEstimateDate(getNextDate(0));
								fundEstimationDay.setPreviousMonthDate1(format.parse(fundestmDaysdtable.getPreviousMonthDate1()));
								fundEstimationDay.setPreviousMonthValue1(fundestmDaysdtable.getPreviousMonthValue1());
								fundEstimationDay.setPreviousMonthDate2(format.parse(fundestmDaysdtable.getPreviousMonthDate2()));
								fundEstimationDay.setPreviousMonthValue2(fundestmDaysdtable.getPreviousMonthValue2());
								fundEstimationDay.setPreviousMonthDate3(format.parse(fundestmDaysdtable.getPreviousMonthDate3()));
								fundEstimationDay.setPreviousMonthValue3(fundestmDaysdtable.getPreviousMonthValue3());
								fundEstimationDay.setBankId(fundEstimationlistVal.getBankId() == null ? new BigDecimal(0) : fundEstimationlistVal.getBankId());
								fundEstimationDay.setServiceId(fundEstimationlistVal.getServiceId() == null ? new BigDecimal(0) : fundEstimationlistVal.getServiceId());
								occuranceOfWeek(i);

								fundEstimationDay.setPreviousMonthWeekDate1(format.parse(fundestmDaysdtable.getPreviousMonthWeekDate1()));
								fundEstimationDay.setPreviousMonthWeekValue1(fundestmDaysdtable.getPreviousMonthWeekValue1());
								fundEstimationDay.setPreviousMonthWeekDate2(format.parse(fundestmDaysdtable.getPreviousMonthWeekDate2()));
								fundEstimationDay.setPreviousMonthWeekValue2(fundestmDaysdtable.getPreviousMonthWeekValue2());
								fundEstimationDay.setPreviousMonthWeekDate3(format.parse(fundestmDaysdtable.getPreviousMonthWeekDate3()));
								fundEstimationDay.setPreviousMonthWeekValue3(fundestmDaysdtable.getPreviousMonthWeekValue3());
								fundEstimationDay.setCreatedBy(sessionManage.getUserName());
								fundEstimationDay.setCreatedDate(new Date());
								fundEstimationService.saveFundEstimationDays(fundEstimationDay);
							}


						}

						/*} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

					}	
				}else{
					throw new Exception("Addtion sale projection comming null value:::(foreignCurrencyBalance)");
				}
				/*}else{
				RequestContext.getCurrentInstance().execute("pleaseentertom.show();");
				return;
			}//end of if and else
				 */			// end of IF-else
			} // End of FOR

			// } //End of IF
			RequestContext.getCurrentInstance().execute("complete.show();");

		}catch(NullPointerException ne){
			log.info("Save::"+ne.getMessage());
			setExceptionMessage("Method Name::Save"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch (Exception e){
			log.info("Save :"+e.getMessage());
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return;
		}	

	}

	/*
	 * public void save() throws ParseException {
	 * 
	 * System.out.println("Save Fund Estimation Rabil :");
	 * 
	 * FundEstimation fundEstimation = new FundEstimation();
	 * 
	 * CountryMaster applicationCountryMaster = new CountryMaster();
	 * applicationCountryMaster.setCountryId(getCountryId());
	 * fundEstimation.setExFundEstimationForApplicationCountry
	 * (applicationCountryMaster);
	 * 
	 * CompanyMaster companyMaster = new CompanyMaster();
	 * companyMaster.setCompanyId(getCompanyId());
	 * fundEstimation.setFsCompanyMaster(companyMaster);
	 * 
	 * CountryMaster bankCountryMaster = new CountryMaster();
	 * bankCountryMaster.setCountryId(getBankCountry());
	 * fundEstimation.setExFundEstimationForBankCountry(bankCountryMaster);
	 * 
	 * CurrencyMaster currencyMaster = new CurrencyMaster();
	 * currencyMaster.setCurrencyId(getCurrencyId());
	 * 
	 * fundEstimation.setExCurrenyMaster(currencyMaster);
	 * 
	 * BankMaster bankMaster = new BankMaster();
	 * bankMaster.setBankId(getBankId());
	 * fundEstimation.setExBankMaster(bankMaster);
	 * 
	 * fundEstimation.setProjectionDate(new Date());
	 * fundEstimation.setEstimateNumberOfDays(getNoOfDaysEstimation());
	 * fundEstimation.setIkonRate(getiKONRate());
	 * fundEstimation.setPreviousDaysCurrentBalance(getCurrentBankBalance()); //
	 * added for change Sales Projection CR if
	 * (getCurrencyId().equals(fundEstimationDetailsBeanService
	 * .getUsdPk(Constants.FUND_ESTIMATION_USD_CURRENCY_CODE))) { if
	 * (getCurrentBankBalance() != null) {
	 * fundEstimation.setUsdCurrentBalanace(getCurrentBankBalance
	 * ().multiply(getiKONRate
	 * ()).setScale(fundEstimationDetailsBeanService.getDecimalValue
	 * (getCurrencyId()), RoundingMode.FLOOR)); }
	 * 
	 * fundEstimation.setValueDatedTransaction(getValueDatedTrnsaction());
	 * fundEstimation
	 * .setuSdValueDatedTransaction(round(getValueDatedTrnsaction()
	 * .multiply(getiKONRate()), 1)); } else {
	 * fundEstimation.setUsdCurrentBalanace
	 * (getCurrentBankBalance().multiply(getiKONRate
	 * ()).setScale(fundEstimationDetailsBeanService
	 * .getDecimalValue(getCurrencyId()), RoundingMode.FLOOR));
	 * 
	 * fundEstimation.setValueDatedTransaction(getValueDatedTrnsaction());
	 * fundEstimation
	 * .setuSdValueDatedTransaction(round(getValueDatedTrnsaction()
	 * .multiply(getiKONRate()),
	 * fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId()))); }
	 * 
	 * fundEstimation.setSystemGeneratedSalesProjectionAmount(
	 * getSystemGeneratedSalesProjection());
	 * fundEstimation.setCreatedBy(sessionManage.getUserName());
	 * fundEstimation.setCreatedDate(new Date());
	 * 
	 * fundEstimationService.save(fundEstimation);
	 * 
	 * FundEstimationDetails fundEstimationDetails = new
	 * FundEstimationDetails();
	 * 
	 * fundEstimationDetails.setFundEstimtaionId(fundEstimation);
	 * fundEstimationDetails
	 * .setExFundEstimtaionDetailsForApplicationCountry(applicationCountryMaster
	 * ); fundEstimationDetails.setFsCompanyMaster(companyMaster);
	 * fundEstimationDetails
	 * .setExFundEstimtaionDeatilsForBankCountry(bankCountryMaster);
	 * fundEstimationDetails.setExCurrenyMaster(currencyMaster);
	 * fundEstimationDetails.setExBankMaster(bankMaster);
	 * fundEstimationDetails.setProjectionDate(new Date());
	 * fundEstimationDetails.setIkonRate(getiKONRate());
	 * 
	 * BankAccountDetails bankAccountDetails = new BankAccountDetails();
	 * 
	 * BigDecimal bankaccountDeatailsId =
	 * bankTransferService.getBankAccountDeatilsPk(getAccountNumber()); if
	 * (bankaccountDeatailsId != null) {
	 * bankAccountDetails.setBankAcctDetId(bankaccountDeatailsId); }
	 * fundEstimationDetails.setExFundEstimationDetails(bankAccountDetails);
	 * 
	 * // added for change Sales Projection CR
	 * 
	 * if
	 * (getCurrencyId().equals(fundEstimationDetailsBeanService.getUsdPk(Constants
	 * .FUND_ESTIMATION_USD_CURRENCY_CODE))) { if (getSalesProjection() != null
	 * && getSalesProjection().toString().length() > 0) {
	 * fundEstimationDetails.setSalesForeignCurrencyProjection
	 * (getSalesProjection());
	 * fundEstimationDetails.setUsdValue(getSalesProjection
	 * ().multiply(getiKONRate
	 * ()).setScale(fundEstimationDetailsBeanService.getDecimalValue
	 * (getCurrencyId()), RoundingMode.FLOOR)); } else {
	 * fundEstimationDetails.setSalesForeignCurrencyProjection
	 * (getSystemGeneratedSalesProjection());
	 * fundEstimationDetails.setUsdValue(getSystemGeneratedSalesProjection
	 * ().multiply
	 * (getiKONRate()).setScale(fundEstimationDetailsBeanService.getDecimalValue
	 * (getCurrencyId()), RoundingMode.FLOOR)); } } else if
	 * (getSalesProjection() != null && getSalesProjection().toString().length()
	 * > 0) {
	 * fundEstimationDetails.setSalesForeignCurrencyProjection(getSalesProjection
	 * ());
	 * fundEstimationDetails.setUsdValue(getSalesProjection().multiply(getiKONRate
	 * (
	 * )).setScale(fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId
	 * ()), RoundingMode.FLOOR)); } else {
	 * fundEstimationDetails.setSalesForeignCurrencyProjection
	 * (getSystemGeneratedSalesProjection());
	 * fundEstimationDetails.setUsdValue(getSystemGeneratedSalesProjection
	 * ().multiply
	 * (getiKONRate()).setScale(fundEstimationDetailsBeanService.getDecimalValue
	 * (getCurrencyId()), RoundingMode.FLOOR)); }
	 * fundEstimationDetails.setCreatedBy(sessionManage.getUserName());
	 * fundEstimationDetails.setCreatedDate(new Date());
	 * 
	 * fundEstimationService.saveFundEstimtaionDetails(fundEstimationDetails);
	 * 
	 * System.out.println("Rabil getNoOfDaysEstimation :"+getNoOfDaysEstimation()
	 * .intValue()+" =====\n");
	 * 
	 * for (int i = 0; i < getNoOfDaysEstimation().intValue(); i++) {
	 * 
	 * FundEstimationDays fundEstimationDay = new FundEstimationDays();
	 * 
	 * fundEstimationDay.setFundEstimtaionId(fundEstimation);
	 * fundEstimationDay.setFundEstimationDetailsId(fundEstimationDetails);
	 * fundEstimationDay
	 * .setExFundEstimationDaysApplicationCountry(applicationCountryMaster);
	 * fundEstimationDay.setFsCompanyMaster(companyMaster);
	 * fundEstimationDay.setExFundEstimationDaysBankCountry(bankCountryMaster);
	 * fundEstimationDay.setExCurrenyMaster(currencyMaster);
	 * fundEstimationDay.setExBankMaster(bankMaster);
	 * fundEstimationDay.setProjectionDate(new Date());
	 * fundEstimationDay.setEstimateDate(getNextDate(0));
	 * 
	 * fundEstimationDay.setPreviousMonthDate1(new
	 * SimpleDateFormat("dd/MM/yy").parse
	 * ((Calendar.getInstance().get(Calendar.DATE) + i) + "/" +
	 * (Calendar.getInstance().get(Calendar.MONTH) - 0) + "/" +
	 * Calendar.getInstance().get(Calendar.YEAR)));
	 * fundEstimationDay.setPreviousMonthValue1(Constants.PREVIOUS_MOTH_VALUE1);
	 * fundEstimationDay.setPreviousMonthDate2(new
	 * SimpleDateFormat("dd/MM/yy").parse
	 * ((Calendar.getInstance().get(Calendar.DATE) + i) + "/" +
	 * (Calendar.getInstance().get(Calendar.MONTH) - 1) + "/" +
	 * Calendar.getInstance().get(Calendar.YEAR)));
	 * fundEstimationDay.setPreviousMonthValue2(Constants.PREVIOUS_MOTH_VALUE2);
	 * fundEstimationDay.setPreviousMonthDate3(new
	 * SimpleDateFormat("dd/MM/yy").parse
	 * ((Calendar.getInstance().get(Calendar.DATE) + i) + "/" +
	 * (Calendar.getInstance().get(Calendar.MONTH) - 2) + "/" +
	 * Calendar.getInstance().get(Calendar.YEAR)));
	 * fundEstimationDay.setPreviousMonthValue3(Constants.PREVIOUS_MOTH_VALUE3);
	 * 
	 * occuranceOfWeek(i);
	 * 
	 * fundEstimationDay.setPreviousMonthWeekDate1(new
	 * SimpleDateFormat("yyyy-MM-dd"
	 * ).parse(getNthWeekDay(getNoofOccurance().intValue(),
	 * getDayofweek().intValue())));
	 * fundEstimationDay.setPreviousMonthWeekValue1
	 * (Constants.PREVIOUS_MOTH_WEEK_VALUE1);
	 * fundEstimationDay.setPreviousMonthWeekDate2(new
	 * SimpleDateFormat("yyyy-MM-dd"
	 * ).parse(getNthWeekDay(getNoofOccurance().intValue(),
	 * getDayofweek().intValue())));
	 * fundEstimationDay.setPreviousMonthWeekValue2
	 * (Constants.PREVIOUS_MOTH_WEEK_VALUE2);
	 * fundEstimationDay.setPreviousMonthWeekDate3(new
	 * SimpleDateFormat("yyyy-MM-dd"
	 * ).parse(getNthWeekDay(getNoofOccurance().intValue(),
	 * getDayofweek().intValue())));
	 * fundEstimationDay.setPreviousMonthWeekValue3
	 * (Constants.PREVIOUS_MOTH_WEEK_VALUE3);
	 * 
	 * fundEstimationDay.setCreatedBy(sessionManage.getUserName());
	 * fundEstimationDay.setCreatedDate(new Date());
	 * 
	 * fundEstimationService.saveFundEstimationDays(fundEstimationDay); }
	 * RequestContext.getCurrentInstance().execute("complete.show();"); }
	 */

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
			setProjectionDate(new Date());
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void commonSave() throws ParseException {
		if (isBooCheck()) {
			// saveReprojected();
		} else {
			save();
		}
	}

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

	/** Rabil */

	// public void showDetailsInDialog() {
	// int i = 1;
	// lstDetails.clear();
	//
	// for (FundEstimationDetails element : fundDetailsList) {
	// lstDetails.add(new FundAccumulationDetailsDatatable(i++,
	// element.getSalesForeignCurrencyProjection(), round(element.getUsdValue(),
	// fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId())),
	// element.getProjectionDate().toString(), element.getIkonRate(),
	// iUsdGlobalRequirementDetailsService.getBankName(element.getExFundEstimtaionDeatilsForBankCountry().getCountryId()),
	// element.getExBankMaster().getBankFullName(),
	// element.getExCurrenyMaster().getCurrencyName(),
	// element.getExFundEstimationDetails().getBankAcctNo()));
	// }
	// }

	/*
	 * public void showDetailsInNoOfDays() { int i = 1;
	 * daysDetailsDialog.clear(); for (FundEstimationDaysDtable element :
	 * addingDaysForCalculation) { daysDetailsDialog.add(new
	 * FundEstimationDaysDtable(new
	 * SimpleDateFormat("dd/MM/yy").format(element.getPreviousMonthDate1()),
	 * element.getPreviousMonthValue1(), new
	 * SimpleDateFormat("dd/MM/yy").format(element.getPreviousMonthDate2()),
	 * element.getPreviousMonthValue2(), new
	 * SimpleDateFormat("dd/MM/yy").format(element.getPreviousMonthDate3()),
	 * element.getPreviousMonthValue3(), new
	 * SimpleDateFormat("dd/MM/yy").format(element.getPreviousMonthWeekDate1()),
	 * element .getPreviousMonthWeekValue1(), new
	 * SimpleDateFormat("dd/MM/yy").format(element.getPreviousMonthWeekDate2()),
	 * element.getPreviousMonthWeekValue3(), new
	 * SimpleDateFormat("dd/MM/yy").format(element
	 * .getPreviousMonthWeekDate3()), element.getPreviousMonthWeekValue3(), new
	 * SimpleDateFormat("dd/MM/yy").format(element.getProjectiondate()))); } }
	 */
	List<FundEstimationDetails> fundDetailsList;
	List<FundEstimationDays> fundEstDays;

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

	public List<FundEstimationDays> getFundEstDays() {
		return fundEstDays;
	}

	public void setFundEstDays(List<FundEstimationDays> fundEstDays) {
		this.fundEstDays = fundEstDays;
	}

	public void getDaysDataTable(FundEstimationPopulationBean fundEstimationBean) throws ParseException {
		addingDaysForCalculation.clear();

		System.out.println("getDaysDataTable :" + getNoOfDaysEstimation());


		HashMap<String, String> inputValues = new HashMap<String, String>();



		try {


			/*for (int i = 0; i < getNoOfDaysEstimation().intValue(); i++) {

				inputValues.put("P_APPLICATION_COUNTRY_ID",  sessionManage.getCountryId().toPlainString());
				inputValues.put("P_BANK_COUNTRY_ID", getBankCountry().toPlainString());
				inputValues.put("P_CURRENCY_ID", getCurrencyId().toPlainString());
				inputValues.put("P_BANK_ID", fundEstimationBean.getBankId().toPlainString());

				Date projectionDate=DateUtil.addDays(getProjectionDate(), i);

				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

				inputValues.put("P_PROJECTION_DATE",format.format(projectionDate));

				HashMap<String, String> outPutValues =fundEstimationDetailsBeanService.callFundEstimation(inputValues);

				FundEstimationDaysDtable fundestmDaysdtable = new FundEstimationDaysDtable();

				fundestmDaysdtable.setProjectiondate(format.format(projectionDate));

				fundestmDaysdtable.setPreviousMonthDate1(outPutValues.get("P_PREVIOUS_MONTH_DT1"));
				fundestmDaysdtable.setPreviousMonthValue1(outPutValues.get("P_PREVIOUS_MONTH_1"));		

				fundestmDaysdtable.setPreviousMonthDate2(outPutValues.get("P_PREVIOUS_MONTH_DT2"));
				fundestmDaysdtable.setPreviousMonthValue2(outPutValues.get("P_PREVIOUS_MONTH_2"));

				fundestmDaysdtable.setPreviousMonthDate3(outPutValues.get("P_PREVIOUS_MONTH_DT3"));
				fundestmDaysdtable.setPreviousMonthValue3(outPutValues.get("P_PREVIOUS_MONTH_3"));

				fundestmDaysdtable.setPreviousMonthWeekDate1(outPutValues.get("P_PREVIOUS_WEEK_DT1"));
				fundestmDaysdtable.setPreviousMonthWeekValue1(outPutValues.get("P_PREVIOUS_WEEK_1"));

				fundestmDaysdtable.setPreviousMonthWeekDate2(outPutValues.get("P_PREVIOUS_WEEK_DT2"));
				fundestmDaysdtable.setPreviousMonthWeekValue2(outPutValues.get("P_PREVIOUS_WEEK_2"));

				fundestmDaysdtable.setPreviousMonthWeekDate3(outPutValues.get("P_PREVIOUS_WEEK_DT3"));
				fundestmDaysdtable.setPreviousMonthWeekValue3(outPutValues.get("P_PREVIOUS_WEEK_3"));

				addingDaysForCalculation.add(fundestmDaysdtable);

				fundestmDaysdtable.setProjectiondate(Calendar.getInstance().getTime().toString());

				fundestmDaysdtable.setPreviousMonthDate1((Calendar.getInstance().get(Calendar.DATE) + i) + "/" + (Calendar.getInstance().get(Calendar.MONTH) - 0) + "/" + Calendar.getInstance().get(Calendar.YEAR));

				fundestmDaysdtable.setPreviousMonthValue1(Constants.PREVIOUS_MOTH_VALUE1);

				fundestmDaysdtable.setPreviousMonthDate2((Calendar.getInstance().get(Calendar.DATE) + i) + "/" + (Calendar.getInstance().get(Calendar.MONTH) - 1) + "/" + Calendar.getInstance().get(Calendar.YEAR));

				fundestmDaysdtable.setPreviousMonthValue2(Constants.PREVIOUS_MOTH_VALUE2);

				fundestmDaysdtable.setPreviousMonthDate3((Calendar.getInstance().get(Calendar.DATE) + i) + "/" + (Calendar.getInstance().get(Calendar.MONTH) - 2) + "/" + Calendar.getInstance().get(Calendar.YEAR));

				fundestmDaysdtable.setPreviousMonthValue3(Constants.PREVIOUS_MOTH_VALUE3);

				occuranceOfWeek(i);

				fundestmDaysdtable.setPreviousMonthWeekDate1(getNthWeekDay(getNoofOccurance().intValue(), getDayofweek().intValue()));

				fundestmDaysdtable.setPreviousMonthWeekValue1(Constants.PREVIOUS_MOTH_WEEK_VALUE1);

				fundestmDaysdtable.setPreviousMonthWeekDate2(getNthWeekDay(getNoofOccurance().intValue(), getDayofweek().intValue()));

				fundestmDaysdtable.setPreviousMonthWeekValue2(Constants.PREVIOUS_MOTH_WEEK_VALUE2);

				fundestmDaysdtable.setPreviousMonthWeekDate3(getNthWeekDay(getNoofOccurance().intValue(), getDayofweek().intValue()));

				fundestmDaysdtable.setPreviousMonthWeekValue3(Constants.PREVIOUS_MOTH_WEEK_VALUE3);


			}*/
			addingDaysForCalculation.clear();
			addingDaysForCalculation.addAll(fundEstimationBean.getAddingDaysForCalculation());
			// showDetailsInNoOfDays();
			RequestContext.getCurrentInstance().execute("PF('onloadDays').show();");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	/**
	 * Added by Rabil
	 */
	public void ikonRateFromView() throws Exception {

		log.info("ikonRateFromView before format  :"+getProjectionDate()); 
		String projectionDate = DateUtil.todaysDateWithDDMMYY(getProjectionDate(), "2");
		log.info("after ikonRateFromView :"+projectionDate);
		estimationDaysFromViewList = fundEstimationService.getEstimationDaysFromView(sessionManage.getCountryId(), getBankCountry(), getCurrencyId(), projectionDate);

		if (estimationDaysFromViewList.isEmpty()) {
			iKONRate = fxDealwithSupplierService.getIkonRateBasedOnCurrency(currencyId);
			setIkonrate(true);
			setNoOfDaysEstimationBol(false);
			setNoOfDaysEstimation(null);

		} else {
			System.out.println("if block :" + currencyId);
			iKONRate = estimationDaysFromViewList.get(0).getiKonRate();
			noOfDaysEstimation = estimationDaysFromViewList.get(0).getEstimationDays();
			setIkonrate(true);
			setNoOfDaysEstimationBol(true);
		}
	}

	/**
	 * Author :Rabil Date :12/08/2015
	 */
	public void searchFundEstimation() {

		try
		{
			fundEstimationList = new ArrayList<FundEstimationPopulationBean>();
			fundEstimationfromViewList = fundEstimationService.getFundEstimationFromView(sessionManage.getCountryId(), getBankCountry(), getCurrencyId());
			NumberFormat usa = NumberFormat.getInstance(Locale.getDefault());
			HashMap<String, String> hashMapValFrProc =null;
			System.out.println("searchFundEstimation getIkon rate :"+getiKONRate());


			for (TreasuryFundestimationView treasuryFundestimationView : fundEstimationfromViewList) {

				FundEstimationPopulationBean fundEstimationPopulationBean = new FundEstimationPopulationBean();

				try{


					hashMapValFrProc = fundEstimationService.getExProjectionFutureProcedure(sessionManage.getCountryId(),getBankCountry(),treasuryFundestimationView.getBankId(),getCurrencyId(),treasuryFundestimationView.getServiceId(),getProjectionDate());


					fundEstimationPopulationBean.setApplicationCountryId(treasuryFundestimationView.getApplicationCountryId());

					fundEstimationPopulationBean.setSrNo(treasuryFundestimationView.getSrNo());
					fundEstimationPopulationBean.setApplicationCountryId(treasuryFundestimationView.getApplicationCountryId());

					fundEstimationPopulationBean.setAdditionalSalesProjAmnt(new BigDecimal(0).setScale(fundEstimationDetailsBeanService.getDecimalValue(treasuryFundestimationView.getCurrencyId()), RoundingMode.FLOOR));
					//fundEstimationPopulationBean.setAdditionalSalesProjAmnt(new BigDecimal(0).setScale(fundEstimationDetailsBeanService.getDecimalValue(treasuryFundestimationView.getCurrencyId()), RoundingMode.FLOOR));
					//fundEstimationPopulationBean.setCashOrTomOrSpotAmount(new BigDecimal(0).setScale(fundEstimationDetailsBeanService.getDecimalValue(treasuryFundestimationView.getCurrencyId()), RoundingMode.FLOOR) );


					fundEstimationPopulationBean.setBankCountryId(treasuryFundestimationView.getBankCountryId());
					fundEstimationPopulationBean.setBankId(treasuryFundestimationView.getBankId());
					fundEstimationPopulationBean.setBankCountryId(treasuryFundestimationView.getBankCountryId());
					fundEstimationPopulationBean.setBankShortName(treasuryFundestimationView.getBankShortName());
					fundEstimationPopulationBean.setCurrencyId(treasuryFundestimationView.getCurrencyId());

					//commented by Rabil on 25/01/2016
					fundEstimationPopulationBean.setForeignCurrencyBalance(treasuryFundestimationView.getForeignCurrencyBalance());

					if(treasuryFundestimationView.getForeignCurrencyBalance()!=null)

					{ 
						System.out.println(treasuryFundestimationView.getForeignCurrencyBalance());
						fundEstimationPopulationBean.setForeignCurrencyBalanceForDisplay(usa.format(treasuryFundestimationView.getForeignCurrencyBalance()));
					}

					if(hashMapValFrProc.get("P_Sales_projection_amt")!=null){
						fundEstimationPopulationBean.setSalesProjAmnt(new BigDecimal(hashMapValFrProc.get("P_Sales_projection_amt")).setScale(fundEstimationDetailsBeanService.getDecimalValue(treasuryFundestimationView.getCurrencyId()), RoundingMode.FLOOR));
						fundEstimationPopulationBean.setSalesProjAmntForDisplay(usa.format(new BigDecimal(hashMapValFrProc.get("P_Sales_projection_amt"))));



						//fundEstimationPopulationBean.setSalesProjAmnt(treasuryFundestimationView.getSalesProjAmnt().setScale(fundEstimationDetailsBeanService.getDecimalValue(treasuryFundestimationView.getCurrencyId()), RoundingMode.FLOOR));
						//fundEstimationPopulationBean.setSalesProjAmntForDisplay(usa.format(treasuryFundestimationView.getSalesProjAmnt()));

					}
					
					if(hashMapValFrProc.get("P_FUND_ESTIMATION_ID")!=null){
						fundEstimationPopulationBean.setFundEstimationId(new BigDecimal(hashMapValFrProc.get("P_FUND_ESTIMATION_ID")));
						List<FundEstimation> fundList=fundEstimationService.getFundEstimationDataForTomCash(treasuryFundestimationView.getApplicationCountryId(), treasuryFundestimationView.getBankCountryId(), treasuryFundestimationView.getCurrencyId(), treasuryFundestimationView.getBankId(), getProjectionDate(), new BigDecimal(hashMapValFrProc.get("P_FUND_ESTIMATION_ID")));
						if(fundList.size()>0){
							fundEstimationPopulationBean.setCashAmount(fundList.get(0).getCashAmount() != null ? fundList.get(0).getCashAmount():BigDecimal.ZERO);
							fundEstimationPopulationBean.setTomAmount( fundList.get(0).getTomAmount() != null ? fundList.get(0).getTomAmount():BigDecimal.ZERO);
							fundEstimationPopulationBean.setSpotAmount(fundList.get(0).getSpotAmount() != null ? fundList.get(0).getSpotAmount():BigDecimal.ZERO);
						} 
					}
					
					//fundEstimationPopulationBean.setTotalProjectionAmount(new BigDecimal(0).setScale(fundEstimationDetailsBeanService.getDecimalValue(treasuryFundestimationView.getCurrencyId()), RoundingMode.FLOOR));
					//fundEstimationPopulationBean.setTotalProjectionAmountForDisplay(usa.format(new BigDecimal(hashMapValFrProc.get("P_Sales_projection_amt"))));
					
					BigDecimal totalAmt = fundEstimationPopulationBean.getCashAmount().add(fundEstimationPopulationBean.getTomAmount()).add(fundEstimationPopulationBean.getSpotAmount()).add(fundEstimationPopulationBean.getSalesProjAmnt());
					BigDecimal usaTotalAmt=totalAmt.divide(getiKONRate(),RoundingMode.FLOOR);

					fundEstimationPopulationBean.setTotalProjectionAmount(totalAmt);
					fundEstimationPopulationBean.setUsdTotalProjectionAmount(usaTotalAmt);
					fundEstimationPopulationBean.setUsdTotalProjectionAmountForDisplay(usa.format(usaTotalAmt));
					fundEstimationPopulationBean.setTotalProjectionAmountForDisplay(usa.format(totalAmt));
					
					fundEstimationPopulationBean.setServiceId(treasuryFundestimationView.getServiceId());
					fundEstimationPopulationBean.setServiceMasterDesc(treasuryFundestimationView.getServiceMasterDesc());

					//fundEstimationPopulationBean.setTotalProjectionAmount(new BigDecimal(0).setScale(fundEstimationDetailsBeanService.getDecimalValue(treasuryFundestimationView.getCurrencyId()), RoundingMode.FLOOR));
					//fundEstimationPopulationBean.setTotalProjectionAmountForDisplay(usa.format(treasuryFundestimationView.getSalesProjAmnt()));

					/*if(hashMapValFrProc.get("P_USD_VAL_SALES_PROJECTION")!=null){
						fundEstimationPopulationBean.setUsdTotalProjectionAmount(new BigDecimal(hashMapValFrProc.get("P_USD_VAL_SALES_PROJECTION")).setScale(fundEstimationDetailsBeanService.getDecimalValue(new BigDecimal(Constants.FUND_ESTIMATION_USD_CURRENCY_CODE)), RoundingMode.FLOOR));
						fundEstimationPopulationBean.setUsdTotalProjectionAmountForDisplay(usa.format(new BigDecimal(hashMapValFrProc.get("P_USD_VAL_SALES_PROJECTION"))));
					}*/

					//fundEstimationPopulationBean.setUsdTotalProjectionAmount(treasuryFundestimationView.getUsdTotalsalesProjAmnt().setScale(fundEstimationDetailsBeanService.getDecimalValue(new BigDecimal(Constants.FUND_ESTIMATION_USD_CURRENCY_CODE)), RoundingMode.FLOOR));
					//fundEstimationPopulationBean.setUsdTotalProjectionAmountForDisplay(usa.format(treasuryFundestimationView.getUsdTotalsalesProjAmnt()));
					/*if(treasuryFundestimationView.getSalesProjAmnt() !=null && treasuryFundestimationView.getSalesProjAmnt().compareTo(BigDecimal.ZERO)!=0)
					{	

						BigDecimal usdEquivalantTotal =treasuryFundestimationView.getSalesProjAmnt().divide(getiKONRate(),RoundingMode.FLOOR); 
						System.out.println("usdEquivalantTotal :"+usdEquivalantTotal);
						fundEstimationPopulationBean.setUsdTotalProjectionAmount(usdEquivalantTotal.setScale(fundEstimationDetailsBeanService.getDecimalValue(new BigDecimal(Constants.FUND_ESTIMATION_USD_CURRENCY_CODE)), RoundingMode.FLOOR));
						System.out.println("usdEquivalantTotal after round:"+fundEstimationPopulationBean.getUsdTotalProjectionAmount());
						fundEstimationPopulationBean.setUsdTotalProjectionAmountForDisplay(usa.format(fundEstimationPopulationBean.getUsdTotalProjectionAmount()));
					}else{
						fundEstimationPopulationBean.setUsdTotalProjectionAmount(BigDecimal.ZERO);
						fundEstimationPopulationBean.setUsdTotalProjectionAmountForDisplay("0");
					}*/

					System.out.println("usd total amount :"+fundEstimationPopulationBean.getUsdTotalProjectionAmount()+"\t For Display :"+fundEstimationPopulationBean.getUsdTotalProjectionAmountForDisplay());

					if(treasuryFundestimationView.getUnfundedTrnx()!=null)
					{
						fundEstimationPopulationBean.setUnfundedTrnx(treasuryFundestimationView.getUnfundedTrnx().setScale(fundEstimationDetailsBeanService.getDecimalValue(treasuryFundestimationView.getCurrencyId()), RoundingMode.FLOOR));
						fundEstimationPopulationBean.setUnfundedTrnxForDisplay(usa.format(treasuryFundestimationView.getUnfundedTrnx())); 
					}


					//fundEstimationPopulationBean.setFundEstimationId(treasuryFundestimationView.getFundEstimationId());

					//if(treasuryFundestimationView.getBankId()!=null)
					//{
					List<FundEstimationDaysDtable> localAddingDaysForCalculation =addViwRecords(treasuryFundestimationView.getBankId(),treasuryFundestimationView.getServiceId());

					fundEstimationPopulationBean.setAddingDaysForCalculation(localAddingDaysForCalculation);
					//}




					fundEstimationList.add(fundEstimationPopulationBean);

				}catch(NullPointerException ne){
					log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setExceptionMessage("Method Name::searchFundEstimation"); 
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return; 
				}catch (AMGException e){
					log.info("EX_P_GET_PROJECTIONS :"+e.getMessage());
					setExceptionMessage("EX_P_GET_PROJECTIONS  "+e.getMessage());
					RequestContext.getCurrentInstance().execute("sqlexception.show();");
					return;
				}	


			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setExceptionMessage("Method Name::searchFundEstimation"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			setExceptionMessage("EX_P_GET_PROJECTIONS  "+e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return;
		}
	}

	private List<FundEstimationDaysDtable>  addViwRecords(BigDecimal bankId,BigDecimal serviceId)
	{
		List<FundEstimationDaysDtable> localAddingDaysForCalculation = new ArrayList<FundEstimationDaysDtable>();
		try {

			HashMap<String, String> inputValues = new HashMap<String, String>();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

			inputValues.put("P_APPLICATION_COUNTRY_ID",  sessionManage.getCountryId().toPlainString());
			inputValues.put("P_BANK_COUNTRY_ID", getBankCountry().toPlainString());
			inputValues.put("P_CURRENCY_ID", getCurrencyId().toPlainString());
			inputValues.put("P_BANK_ID", bankId==null ? "0" : bankId.toPlainString());
			inputValues.put("P_SERVICE_ID", serviceId==null ? "0" : serviceId.toPlainString());
			inputValues.put("P_COMPANY_ID", sessionManage.getCompanyId().toString());
			inputValues.put("P_PROJECTION_DATE",format.format(getProjectionDate()));

			List<FundEstimationDays> lstFundEstimationDays= fundEstimationDetailsBeanService.getFundEstimationDyas(inputValues);

			if(lstFundEstimationDays!=null && lstFundEstimationDays.size()>0)
			{
				for(FundEstimationDays fundEstimationDays :lstFundEstimationDays)
				{

					FundEstimationDaysDtable fundestmDaysdtable = new FundEstimationDaysDtable();

					fundestmDaysdtable.setProjectiondate(format.format(projectionDate));

					fundestmDaysdtable.setPreviousMonthDate1(format.format(fundEstimationDays.getPreviousMonthDate1()));
					fundestmDaysdtable.setPreviousMonthValue1(fundEstimationDays.getPreviousMonthValue1());		

					fundestmDaysdtable.setPreviousMonthDate2(format.format(fundEstimationDays.getPreviousMonthDate2()));
					fundestmDaysdtable.setPreviousMonthValue2(fundEstimationDays.getPreviousMonthValue2());

					fundestmDaysdtable.setPreviousMonthDate3(format.format(fundEstimationDays.getPreviousMonthDate3()));
					fundestmDaysdtable.setPreviousMonthValue3(fundEstimationDays.getPreviousMonthValue3());

					fundestmDaysdtable.setPreviousMonthWeekDate1(format.format(fundEstimationDays.getPreviousMonthWeekDate1()));
					fundestmDaysdtable.setPreviousMonthWeekValue1(fundEstimationDays.getPreviousMonthWeekValue1());

					fundestmDaysdtable.setPreviousMonthWeekDate2(format.format(fundEstimationDays.getPreviousMonthWeekDate2()));
					fundestmDaysdtable.setPreviousMonthWeekValue2(fundEstimationDays.getPreviousMonthWeekValue2());

					fundestmDaysdtable.setPreviousMonthWeekDate3(format.format(fundEstimationDays.getPreviousMonthWeekDate3()));
					fundestmDaysdtable.setPreviousMonthWeekValue3(fundEstimationDays.getPreviousMonthWeekValue3());
					fundestmDaysdtable.setAlreadyInsert(true);

					localAddingDaysForCalculation.add(fundestmDaysdtable);
				}
			}else
			{
				for (int i = 0; i < getNoOfDaysEstimation().intValue(); i++) {


					Date projectionDate=DateUtil.addDays(getProjectionDate(), i);

					inputValues.put("P_PROJECTION_DATE",format.format(projectionDate));

					HashMap<String, String> outPutValues =fundEstimationDetailsBeanService.callFundEstimation(inputValues);

					FundEstimationDaysDtable fundestmDaysdtable = new FundEstimationDaysDtable();

					fundestmDaysdtable.setProjectiondate(format.format(projectionDate));

					fundestmDaysdtable.setPreviousMonthDate1(outPutValues.get("P_PREVIOUS_MONTH_DT1"));
					fundestmDaysdtable.setPreviousMonthValue1(outPutValues.get("P_PREVIOUS_MONTH_1"));		

					fundestmDaysdtable.setPreviousMonthDate2(outPutValues.get("P_PREVIOUS_MONTH_DT2"));
					fundestmDaysdtable.setPreviousMonthValue2(outPutValues.get("P_PREVIOUS_MONTH_2"));

					fundestmDaysdtable.setPreviousMonthDate3(outPutValues.get("P_PREVIOUS_MONTH_DT3"));
					fundestmDaysdtable.setPreviousMonthValue3(outPutValues.get("P_PREVIOUS_MONTH_3"));

					fundestmDaysdtable.setPreviousMonthWeekDate1(outPutValues.get("P_PREVIOUS_WEEK_DT1"));
					fundestmDaysdtable.setPreviousMonthWeekValue1(outPutValues.get("P_PREVIOUS_WEEK_1"));

					fundestmDaysdtable.setPreviousMonthWeekDate2(outPutValues.get("P_PREVIOUS_WEEK_DT2"));
					fundestmDaysdtable.setPreviousMonthWeekValue2(outPutValues.get("P_PREVIOUS_WEEK_2"));

					fundestmDaysdtable.setPreviousMonthWeekDate3(outPutValues.get("P_PREVIOUS_WEEK_DT3"));
					fundestmDaysdtable.setPreviousMonthWeekValue3(outPutValues.get("P_PREVIOUS_WEEK_3"));
					fundestmDaysdtable.setAlreadyInsert(false);

					localAddingDaysForCalculation.add(fundestmDaysdtable);

				}
			}



		} catch (AMGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setExceptionMessage("EX_P_GET_PROJECTIONS  "+e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
			return null;
		}
		return localAddingDaysForCalculation;
	}
	/**
	 * Author :Rabil Date :13/08/2015
	 */

	public void onCellEditSave(CellEditEvent event) {

		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
	}

	/*	*//**
	 * 
	 * @author :Rabil Date : Save Projected value
	 */
	/*
	 * 
	 * public void saveReprojectedFundEstimation() throws ParseException {
	 * 
	 * System.out.println("fundEstimationList onCellEditSave:" +
	 * fundEstimationList.size()); for (FundEstimationPopulationBean listVal :
	 * fundEstimationList) { System.out.println("fundEstimationList :" +
	 * listVal.getAdditionalSalesProjAmnt() + "\t getTotalProjectionAmount()" +
	 * listVal.getTotalProjectionAmount() + "\n Foreign currency value :" +
	 * listVal.getForeignCurrencyBalance());
	 * 
	 * }
	 * 
	 * System.out.println("FUND ESTIMATION SAVE  SAVE METHOD :");
	 * 
	 * FundEstimation fundEstimation = new FundEstimation();
	 * FundEstimationDetails fundEstimationDetails = new
	 * FundEstimationDetails();
	 * 
	 * fundEstimation.setFundEstimtaionId(fundDetailsList.get(0).getFundEstimtaionId
	 * ().getFundEstimtaionId()); System.out.println("fundEstimation Id :" +
	 * fundEstimation.getFundEstimtaionId() + "\t ");
	 * 
	 * // RequestContext.getCurrentInstance().execute("complete.show();");
	 * 
	 * }
	 */

	public void fetchDetails() throws ParseException {
		BigDecimal accountBanlance = BigDecimal.ZERO;
		String balance = fundEstimationService.getCurrentBalance(getAccountNumber());
		if (balance != null) {
			accountBanlance = new BigDecimal(balance);
		}
		setCurrentBankBalance(round((accountBanlance == null ? BigDecimal.ZERO : accountBanlance), fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId())));

		BigDecimal bankAccountDeatailsPk = bankTransferService.getBankAccountDeatilsPk(getAccountNumber());

		/*
		 * fundDetailsList = fundEstimationService.getFundEstimationData(new
		 * SimpleDateFormat("dd/MM/yyyy").parse(getProjectionDate()),
		 * getBankCountry(), getBankId(), getCurrencyId(),
		 * sessionManage.getCountryId(), bankAccountDeatailsPk); fundEstDays =
		 * fundEstimationService.getFundEstimationNoOfDays(new
		 * SimpleDateFormat("dd/MM/yyyy").parse(getProjectionDate()),
		 * getBankCountry(), getBankId(), getCurrencyId(),
		 * sessionManage.getCountryId(), bankAccountDeatailsPk);
		 */

		System.out.println("Test RABIL :" + getProjectionDate() + "\t getBankCountry():" + getBankCountry() + "\t getBankId() :" + getBankId() + "\t getCurrencyId() :" + getCurrencyId());

		fundDetailsList = fundEstimationService.getFundEstimationData(getProjectionDate(), getBankCountry(), getBankId(), getCurrencyId(), sessionManage.getCountryId(), bankAccountDeatailsPk);
		// fundEstDays =
		// fundEstimationService.getFundEstimationNoOfDays(getProjectionDate(),
		// getBankCountry(), getBankId(), getCurrencyId(),
		// sessionManage.getCountryId(), bankAccountDeatailsPk);

		BigDecimal accumulatedSumBalance = BigDecimal.ZERO;
		BigDecimal acculumatedUSDBalance = BigDecimal.ZERO;

		if (fundDetailsList.size() > 0) {

			setEditButton(false);
			setBooCheck(true);
			setControl1stAnd2ndTime(true);
			setBooRenderLinkNoofDays(true);
			setBooRenderNormalNoOfdays(false);

			for (FundEstimationDetails element : fundDetailsList) {
				accumulatedSumBalance = accumulatedSumBalance.add(element.getSalesForeignCurrencyProjection());
				acculumatedUSDBalance = acculumatedUSDBalance.add(element.getUsdValue());
			}

			if (fundEstDays.size() > 0) {
				setFundEstDays(fundEstDays);
				int sizevalue = fundEstDays.size();
				setNoOfDaysEstimation(new BigDecimal(sizevalue));
			}

			// fetching sale projection from Function
			setNoofDaysPopup(1);
			// blurOnEstimationDate();

			setAccumulatedSalesProjectionAmount(accumulatedSumBalance);
			setAccumulatedSalesUSDEquivalent(String.valueOf(acculumatedUSDBalance));
		} else {
			setEditButton(true);
			setBooCheck(false);
			setControl1stAnd2ndTime(false);
			setBooRenderLinkNoofDays(false);
			setBooRenderNormalNoOfdays(true);
			currencyChnage();
			setNoOfDaysEstimation(null);
			setValueDatedTrnsaction(null);
			setSalesProjection(null);
			setSystemGeneratedSalesProjection(null);
			setiKONRate(null);
			setUsdSalesProjection(null);
			if (getCurrencyId().equals(fundEstimationDetailsBeanService.getUsdPk(Constants.FUND_ESTIMATION_USD_CURRENCY_CODE))) {
				setiKONRate(new BigDecimal(1.0));
				setIkonrate(true);
			} else {
				setIkonrate(false);
			}
		}
	}

	/*
	 * public void saveReprojected() throws ParseException {
	 * 
	 * System.out.println("FUND ESTIMATION SAVE  SAVE METHOD :" );
	 * 
	 * FundEstimation fundEstimation = new FundEstimation();
	 * 
	 * fundEstimation.setFundEstimtaionId(fundDetailsList.get(0).getFundEstimtaionId
	 * ().getFundEstimtaionId());
	 * 
	 * 
	 * 
	 * CountryMaster applicationCountryMaster = new CountryMaster();
	 * applicationCountryMaster.setCountryId(getCountryId());
	 * fundEstimation.setExFundEstimationForApplicationCountry
	 * (applicationCountryMaster);
	 * 
	 * CompanyMaster companyMaster = new CompanyMaster();
	 * companyMaster.setCompanyId(getCompanyId());
	 * fundEstimation.setFsCompanyMaster(companyMaster);
	 * 
	 * CountryMaster bankCountryMaster = new CountryMaster();
	 * bankCountryMaster.setCountryId(getBankCountry());
	 * fundEstimation.setExFundEstimationForBankCountry(bankCountryMaster);
	 * 
	 * CurrencyMaster currencyMaster = new CurrencyMaster();
	 * currencyMaster.setCurrencyId(getCurrencyId());
	 * 
	 * fundEstimation.setExCurrenyMaster(currencyMaster);
	 * 
	 * BankMaster bankMaster = new BankMaster();
	 * bankMaster.setBankId(getBankId());
	 * fundEstimation.setExBankMaster(bankMaster);
	 * 
	 * fundEstimation.setProjectionDate(new Date());
	 * fundEstimation.setEstimateNumberOfDays(getNoOfDaysEstimation());
	 * fundEstimation.setIkonRate(getiKONRate());
	 * fundEstimation.setPreviousDaysCurrentBalance(getCurrentBankBalance()); //
	 * added for change Sales Projection CR if
	 * (getCurrencyId().equals(fundEstimationDetailsBeanService
	 * .getUsdPk(Constants.FUND_ESTIMATION_USD_CURRENCY_CODE))) {
	 * fundEstimation.
	 * setUsdCurrentBalanace(getCurrentBankBalance().multiply(getiKONRate
	 * ()).setScale
	 * (fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId()),
	 * RoundingMode.FLOOR));
	 * fundEstimation.setValueDatedTransaction(getValueDatedTrnsaction());
	 * fundEstimation
	 * .setuSdValueDatedTransaction(round(getValueDatedTrnsaction()
	 * .multiply(getiKONRate()), 1)); } else {
	 * //fundEstimation.setUsdCurrentBalanace
	 * (getCurrentBankBalance().multiply(getiKONRate
	 * ()).setScale(fundEstimationDetailsBeanService
	 * .getDecimalValue(getCurrencyId()), RoundingMode.FLOOR));
	 * fundEstimation.setUsdCurrentBalanace
	 * (getCurrentBankBalance().multiply(getiKONRate
	 * ()).setScale(fundEstimationDetailsBeanService
	 * .getDecimalValue(getCurrencyId()), RoundingMode.FLOOR));
	 * fundEstimation.setValueDatedTransaction(getValueDatedTrnsaction());
	 * fundEstimation
	 * .setuSdValueDatedTransaction(round(getValueDatedTrnsaction()
	 * .multiply(getiKONRate()),
	 * fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId())));
	 * 
	 * } fundEstimation.setSystemGeneratedSalesProjectionAmount(
	 * getSystemGeneratedSalesProjection());
	 * fundEstimation.setCreatedBy(sessionManage.getUserName());
	 * fundEstimation.setCreatedDate(new Date());
	 * 
	 * fundEstimationService.saveAndUpadate(fundEstimation);
	 * 
	 * FundEstimationDetails fundEstimationDetails = new
	 * FundEstimationDetails();
	 * 
	 * fundEstimationDetails.setFundEstimtaionId(fundEstimation);
	 * fundEstimationDetails
	 * .setExFundEstimtaionDetailsForApplicationCountry(applicationCountryMaster
	 * ); fundEstimationDetails.setFsCompanyMaster(companyMaster);
	 * fundEstimationDetails
	 * .setExFundEstimtaionDeatilsForBankCountry(bankCountryMaster);
	 * fundEstimationDetails.setExCurrenyMaster(currencyMaster);
	 * fundEstimationDetails.setExBankMaster(bankMaster);
	 * fundEstimationDetails.setProjectionDate(new Date());
	 * fundEstimationDetails.setIkonRate(getiKONRate());
	 * 
	 * BankAccountDetails bankAccountDetails = new BankAccountDetails();
	 * BigDecimal bankaccountDeatailsId =
	 * bankTransferService.getBankAccountDeatilsPk(getAccountNumber()); if
	 * (bankaccountDeatailsId != null) {
	 * bankAccountDetails.setBankAcctDetId(bankaccountDeatailsId); }
	 * fundEstimationDetails.setExFundEstimationDetails(bankAccountDetails);
	 * 
	 * // added for change Sales Projection CR
	 * 
	 * if
	 * (getCurrencyId().equals(fundEstimationDetailsBeanService.getUsdPk(Constants
	 * .FUND_ESTIMATION_USD_CURRENCY_CODE))) { if (getSalesProjection() != null
	 * && getSalesProjection().toString().length() > 0) {
	 * fundEstimationDetails.setSalesForeignCurrencyProjection
	 * (getSalesProjection());
	 * fundEstimationDetails.setUsdValue(getSalesProjection
	 * ().multiply(getiKONRate
	 * ()).setScale(fundEstimationDetailsBeanService.getDecimalValue
	 * (getCurrencyId()), RoundingMode.FLOOR)); } else {
	 * fundEstimationDetails.setSalesForeignCurrencyProjection
	 * (getSystemGeneratedSalesProjection());
	 * fundEstimationDetails.setUsdValue(getSystemGeneratedSalesProjection
	 * ().multiply
	 * (getiKONRate()).setScale(fundEstimationDetailsBeanService.getDecimalValue
	 * (getCurrencyId()), RoundingMode.FLOOR)); } } else if
	 * (getSalesProjection() != null && getSalesProjection().toString().length()
	 * > 0) {
	 * fundEstimationDetails.setSalesForeignCurrencyProjection(getSalesProjection
	 * ());
	 * fundEstimationDetails.setUsdValue(getSalesProjection().multiply(getiKONRate
	 * (
	 * )).setScale(fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId
	 * ()), RoundingMode.FLOOR)); } else {
	 * fundEstimationDetails.setSalesForeignCurrencyProjection
	 * (getSystemGeneratedSalesProjection());
	 * fundEstimationDetails.setUsdValue(getSystemGeneratedSalesProjection
	 * ().multiply
	 * (getiKONRate()).setScale(fundEstimationDetailsBeanService.getDecimalValue
	 * (getCurrencyId()), RoundingMode.FLOOR)); }
	 * 
	 * fundEstimationDetails.setCreatedBy(sessionManage.getUserName());
	 * fundEstimationDetails.setCreatedDate(new Date());
	 * 
	 * fundEstimationService.saveFundEstimtaionDetails(fundEstimationDetails);
	 * 
	 * FundEstimationDays fundEstimationDay = new FundEstimationDays(); int
	 * count = 0;
	 * 
	 * 
	 * for (FundEstimationDays element : fundEstDays) {
	 * fundEstimationDay.setFundEstimtaionDaysId
	 * (element.getFundEstimtaionDaysId());
	 * 
	 * fundEstimationDay.setFundEstimtaionId(fundEstimation);
	 * fundEstimationDay.setFundEstimationDetailsId(fundEstimationDetails);
	 * fundEstimationDay
	 * .setExFundEstimationDaysApplicationCountry(applicationCountryMaster);
	 * fundEstimationDay.setFsCompanyMaster(companyMaster);
	 * fundEstimationDay.setExFundEstimationDaysBankCountry(bankCountryMaster);
	 * fundEstimationDay.setExCurrenyMaster(currencyMaster);
	 * fundEstimationDay.setExBankMaster(bankMaster);
	 * fundEstimationDay.setProjectionDate(new Date());
	 * fundEstimationDay.setEstimateDate(getNextDate(0));
	 * 
	 * fundEstimationDay.setPreviousMonthDate1(new
	 * SimpleDateFormat("dd/MM/yy").parse
	 * ((Calendar.getInstance().get(Calendar.DATE) + count) + "/" +
	 * (Calendar.getInstance().get(Calendar.MONTH) - 0) + "/" +
	 * Calendar.getInstance().get(Calendar.YEAR)));
	 * fundEstimationDay.setPreviousMonthValue1(Constants.PREVIOUS_MOTH_VALUE1);
	 * fundEstimationDay.setPreviousMonthDate2(new
	 * SimpleDateFormat("dd/MM/yy").parse
	 * ((Calendar.getInstance().get(Calendar.DATE) + count) + "/" +
	 * (Calendar.getInstance().get(Calendar.MONTH) - 1) + "/" +
	 * Calendar.getInstance().get(Calendar.YEAR)));
	 * fundEstimationDay.setPreviousMonthValue2(Constants.PREVIOUS_MOTH_VALUE2);
	 * fundEstimationDay.setPreviousMonthDate3(new
	 * SimpleDateFormat("dd/MM/yy").parse
	 * ((Calendar.getInstance().get(Calendar.DATE) + count) + "/" +
	 * (Calendar.getInstance().get(Calendar.MONTH) - 2) + "/" +
	 * Calendar.getInstance().get(Calendar.YEAR)));
	 * fundEstimationDay.setPreviousMonthValue3(Constants.PREVIOUS_MOTH_VALUE3);
	 * 
	 * occuranceOfWeek(count);
	 * 
	 * fundEstimationDay.setPreviousMonthWeekDate1(new
	 * SimpleDateFormat("yyyy-MM-dd"
	 * ).parse(getNthWeekDay(getNoofOccurance().intValue(),
	 * getDayofweek().intValue())));
	 * fundEstimationDay.setPreviousMonthWeekValue1
	 * (Constants.PREVIOUS_MOTH_WEEK_VALUE1);
	 * fundEstimationDay.setPreviousMonthWeekDate2(new
	 * SimpleDateFormat("yyyy-MM-dd"
	 * ).parse(getNthWeekDay(getNoofOccurance().intValue(),
	 * getDayofweek().intValue())));
	 * fundEstimationDay.setPreviousMonthWeekValue2
	 * (Constants.PREVIOUS_MOTH_WEEK_VALUE2);
	 * fundEstimationDay.setPreviousMonthWeekDate3(new
	 * SimpleDateFormat("yyyy-MM-dd"
	 * ).parse(getNthWeekDay(getNoofOccurance().intValue(),
	 * getDayofweek().intValue())));
	 * fundEstimationDay.setPreviousMonthWeekValue3
	 * (Constants.PREVIOUS_MOTH_WEEK_VALUE3);
	 * 
	 * fundEstimationDay.setCreatedBy(sessionManage.getUserName());
	 * fundEstimationDay.setCreatedDate(new Date());
	 * fundEstimationService.saveAndUpdateFundEstimationDays(fundEstimationDay);
	 * count++; }
	 * 
	 * for (int i = count; i < getNoOfDaysEstimation().intValue(); i++) {
	 * fundEstimationDay.setFundEstimtaionDaysId(getUpdateDaysId());
	 * 
	 * fundEstimationDay.setFundEstimtaionId(fundEstimation);
	 * fundEstimationDay.setFundEstimationDetailsId(fundEstimationDetails);
	 * fundEstimationDay
	 * .setExFundEstimationDaysApplicationCountry(applicationCountryMaster);
	 * fundEstimationDay.setFsCompanyMaster(companyMaster);
	 * fundEstimationDay.setExFundEstimationDaysBankCountry(bankCountryMaster);
	 * fundEstimationDay.setExCurrenyMaster(currencyMaster);
	 * fundEstimationDay.setExBankMaster(bankMaster);
	 * fundEstimationDay.setProjectionDate(new Date());
	 * fundEstimationDay.setEstimateDate(getNextDate(0));
	 * 
	 * fundEstimationDay.setPreviousMonthDate1(new
	 * SimpleDateFormat("dd/MM/yy").parse
	 * ((Calendar.getInstance().get(Calendar.DATE) + i) + "/" +
	 * (Calendar.getInstance().get(Calendar.MONTH) - 0) + "/" +
	 * Calendar.getInstance().get(Calendar.YEAR)));
	 * fundEstimationDay.setPreviousMonthValue1(Constants.PREVIOUS_MOTH_VALUE1);
	 * fundEstimationDay.setPreviousMonthDate2(new
	 * SimpleDateFormat("dd/MM/yy").parse
	 * ((Calendar.getInstance().get(Calendar.DATE) + i) + "/" +
	 * (Calendar.getInstance().get(Calendar.MONTH) - 1) + "/" +
	 * Calendar.getInstance().get(Calendar.YEAR)));
	 * fundEstimationDay.setPreviousMonthValue2(Constants.PREVIOUS_MOTH_VALUE2);
	 * fundEstimationDay.setPreviousMonthDate3(new
	 * SimpleDateFormat("dd/MM/yy").parse
	 * ((Calendar.getInstance().get(Calendar.DATE) + i) + "/" +
	 * (Calendar.getInstance().get(Calendar.MONTH) - 2) + "/" +
	 * Calendar.getInstance().get(Calendar.YEAR)));
	 * fundEstimationDay.setPreviousMonthValue3(Constants.PREVIOUS_MOTH_VALUE3);
	 * 
	 * occuranceOfWeek(i);
	 * 
	 * fundEstimationDay.setPreviousMonthWeekDate1(new
	 * SimpleDateFormat("yyyy-MM-dd"
	 * ).parse(getNthWeekDay(getNoofOccurance().intValue(),
	 * getDayofweek().intValue())));
	 * fundEstimationDay.setPreviousMonthWeekValue1
	 * (Constants.PREVIOUS_MOTH_WEEK_VALUE1);
	 * fundEstimationDay.setPreviousMonthWeekDate2(new
	 * SimpleDateFormat("yyyy-MM-dd"
	 * ).parse(getNthWeekDay(getNoofOccurance().intValue(),
	 * getDayofweek().intValue())));
	 * fundEstimationDay.setPreviousMonthWeekValue2
	 * (Constants.PREVIOUS_MOTH_WEEK_VALUE2);
	 * fundEstimationDay.setPreviousMonthWeekDate3(new
	 * SimpleDateFormat("yyyy-MM-dd"
	 * ).parse(getNthWeekDay(getNoofOccurance().intValue(),
	 * getDayofweek().intValue())));
	 * fundEstimationDay.setPreviousMonthWeekValue3
	 * (Constants.PREVIOUS_MOTH_WEEK_VALUE3);
	 * 
	 * fundEstimationDay.setCreatedBy(sessionManage.getUserName());
	 * fundEstimationDay.setCreatedDate(new Date());
	 * 
	 * fundEstimationService.saveAndUpdateFundEstimationDays(fundEstimationDay);
	 * 
	 * }
	 * 
	 * RequestContext.getCurrentInstance().execute("complete.show();"); }
	 */

	/*
	 * 
	 * public void saveReprojected() throws ParseException {
	 * 
	 * System.out.println("FUND ESTIMATION SAVE  SAVE METHOD :");
	 * 
	 * FundEstimation fundEstimation = new FundEstimation();
	 * 
	 * fundEstimation.setFundEstimtaionId(fundDetailsList.get(0).getFundEstimtaionId
	 * ().getFundEstimtaionId());
	 * 
	 * 
	 * 
	 * CountryMaster applicationCountryMaster = new CountryMaster();
	 * applicationCountryMaster.setCountryId(getCountryId());
	 * fundEstimation.setExFundEstimationForApplicationCountry
	 * (applicationCountryMaster);
	 * 
	 * CompanyMaster companyMaster = new CompanyMaster();
	 * companyMaster.setCompanyId(getCompanyId());
	 * fundEstimation.setFsCompanyMaster(companyMaster);
	 * 
	 * CountryMaster bankCountryMaster = new CountryMaster();
	 * bankCountryMaster.setCountryId(getBankCountry());
	 * fundEstimation.setExFundEstimationForBankCountry(bankCountryMaster);
	 * 
	 * CurrencyMaster currencyMaster = new CurrencyMaster();
	 * currencyMaster.setCurrencyId(getCurrencyId());
	 * 
	 * fundEstimation.setExCurrenyMaster(currencyMaster);
	 * 
	 * BankMaster bankMaster = new BankMaster();
	 * bankMaster.setBankId(getBankId());
	 * fundEstimation.setExBankMaster(bankMaster);
	 * 
	 * fundEstimation.setProjectionDate(new Date());
	 * fundEstimation.setEstimateNumberOfDays(getNoOfDaysEstimation());
	 * fundEstimation.setIkonRate(getiKONRate());
	 * fundEstimation.setPreviousDaysCurrentBalance(getCurrentBankBalance()); //
	 * added for change Sales Projection CR if
	 * (getCurrencyId().equals(fundEstimationDetailsBeanService
	 * .getUsdPk(Constants.FUND_ESTIMATION_USD_CURRENCY_CODE))) {
	 * 
	 * fundEstimation.setUsdCurrentBalanace(getCurrentBankBalance().multiply(
	 * getiKONRate
	 * ()).setScale(fundEstimationDetailsBeanService.getDecimalValue(
	 * getCurrencyId ()), RoundingMode.FLOOR));
	 * 
	 * fundEstimation.setValueDatedTransaction(getValueDatedTrnsaction());
	 * fundEstimation
	 * .setuSdValueDatedTransaction(round(getValueDatedTrnsaction()
	 * .multiply(getiKONRate()), 1)); } else {
	 * fundEstimation.setUsdCurrentBalanace
	 * (getCurrentBankBalance().multiply(getiKONRate
	 * ()).setScale(fundEstimationDetailsBeanService
	 * .getDecimalValue(getCurrencyId()), RoundingMode.FLOOR));
	 * 
	 * fundEstimation.setValueDatedTransaction(getValueDatedTrnsaction());
	 * fundEstimation
	 * .setuSdValueDatedTransaction(round(getValueDatedTrnsaction()
	 * .multiply(getiKONRate()),
	 * fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId())));
	 * 
	 * } fundEstimation.setSystemGeneratedSalesProjectionAmount(
	 * getSystemGeneratedSalesProjection());
	 * fundEstimation.setCreatedBy(sessionManage.getUserName());
	 * fundEstimation.setCreatedDate(new Date());
	 * 
	 * fundEstimationService.saveAndUpadate(fundEstimation);
	 * 
	 * FundEstimationDetails fundEstimationDetails = new
	 * FundEstimationDetails();
	 * 
	 * fundEstimationDetails.setFundEstimtaionId(fundEstimation);
	 * fundEstimationDetails
	 * .setExFundEstimtaionDetailsForApplicationCountry(applicationCountryMaster
	 * ); fundEstimationDetails.setFsCompanyMaster(companyMaster);
	 * fundEstimationDetails
	 * .setExFundEstimtaionDeatilsForBankCountry(bankCountryMaster);
	 * fundEstimationDetails.setExCurrenyMaster(currencyMaster);
	 * fundEstimationDetails.setExBankMaster(bankMaster);
	 * fundEstimationDetails.setProjectionDate(new Date());
	 * fundEstimationDetails.setIkonRate(getiKONRate());
	 * 
	 * BankAccountDetails bankAccountDetails = new BankAccountDetails();
	 * BigDecimal bankaccountDeatailsId =
	 * bankTransferService.getBankAccountDeatilsPk(getAccountNumber()); if
	 * (bankaccountDeatailsId != null) {
	 * bankAccountDetails.setBankAcctDetId(bankaccountDeatailsId); }
	 * fundEstimationDetails.setExFundEstimationDetails(bankAccountDetails);
	 * 
	 * // added for change Sales Projection CR
	 * 
	 * if
	 * (getCurrencyId().equals(fundEstimationDetailsBeanService.getUsdPk(Constants
	 * .FUND_ESTIMATION_USD_CURRENCY_CODE))) { if (getSalesProjection() != null
	 * && getSalesProjection().toString().length() > 0) {
	 * fundEstimationDetails.setSalesForeignCurrencyProjection
	 * (getSalesProjection());
	 * fundEstimationDetails.setUsdValue(getSalesProjection
	 * ().multiply(getiKONRate
	 * ()).setScale(fundEstimationDetailsBeanService.getDecimalValue
	 * (getCurrencyId()), RoundingMode.FLOOR)); } else {
	 * fundEstimationDetails.setSalesForeignCurrencyProjection
	 * (getSystemGeneratedSalesProjection());
	 * fundEstimationDetails.setUsdValue(getSystemGeneratedSalesProjection
	 * ().multiply
	 * (getiKONRate()).setScale(fundEstimationDetailsBeanService.getDecimalValue
	 * (getCurrencyId()), RoundingMode.FLOOR)); } } else if
	 * (getSalesProjection() != null && getSalesProjection().toString().length()
	 * > 0) {
	 * fundEstimationDetails.setSalesForeignCurrencyProjection(getSalesProjection
	 * ());
	 * fundEstimationDetails.setUsdValue(getSalesProjection().multiply(getiKONRate
	 * (
	 * )).setScale(fundEstimationDetailsBeanService.getDecimalValue(getCurrencyId
	 * ()), RoundingMode.FLOOR)); } else {
	 * fundEstimationDetails.setSalesForeignCurrencyProjection
	 * (getSystemGeneratedSalesProjection());
	 * fundEstimationDetails.setUsdValue(getSystemGeneratedSalesProjection
	 * ().multiply
	 * (getiKONRate()).setScale(fundEstimationDetailsBeanService.getDecimalValue
	 * (getCurrencyId()), RoundingMode.FLOOR)); }
	 * 
	 * fundEstimationDetails.setCreatedBy(sessionManage.getUserName());
	 * fundEstimationDetails.setCreatedDate(new Date());
	 * 
	 * fundEstimationService.saveFundEstimtaionDetails(fundEstimationDetails);
	 * 
	 * FundEstimationDays fundEstimationDay = new FundEstimationDays(); int
	 * count = 0; for (FundEstimationDays element : fundEstDays) {
	 * 
	 * fundEstimationDay.setFundEstimtaionDaysId(element.getFundEstimtaionDaysId(
	 * ));
	 * 
	 * fundEstimationDay.setFundEstimtaionId(fundEstimation);
	 * fundEstimationDay.setFundEstimationDetailsId(fundEstimationDetails);
	 * fundEstimationDay
	 * .setExFundEstimationDaysApplicationCountry(applicationCountryMaster);
	 * fundEstimationDay.setFsCompanyMaster(companyMaster);
	 * fundEstimationDay.setExFundEstimationDaysBankCountry(bankCountryMaster);
	 * fundEstimationDay.setExCurrenyMaster(currencyMaster);
	 * fundEstimationDay.setExBankMaster(bankMaster);
	 * fundEstimationDay.setProjectionDate(new Date());
	 * fundEstimationDay.setEstimateDate(getNextDate(0));
	 * 
	 * fundEstimationDay.setPreviousMonthDate1(new
	 * SimpleDateFormat("dd/MM/yy").parse
	 * ((Calendar.getInstance().get(Calendar.DATE) + count) + "/" +
	 * (Calendar.getInstance().get(Calendar.MONTH) - 0) + "/" +
	 * Calendar.getInstance().get(Calendar.YEAR)));
	 * fundEstimationDay.setPreviousMonthValue1(Constants.PREVIOUS_MOTH_VALUE1);
	 * fundEstimationDay.setPreviousMonthDate2(new
	 * SimpleDateFormat("dd/MM/yy").parse
	 * ((Calendar.getInstance().get(Calendar.DATE) + count) + "/" +
	 * (Calendar.getInstance().get(Calendar.MONTH) - 1) + "/" +
	 * Calendar.getInstance().get(Calendar.YEAR)));
	 * fundEstimationDay.setPreviousMonthValue2(Constants.PREVIOUS_MOTH_VALUE2);
	 * fundEstimationDay.setPreviousMonthDate3(new
	 * SimpleDateFormat("dd/MM/yy").parse
	 * ((Calendar.getInstance().get(Calendar.DATE) + count) + "/" +
	 * (Calendar.getInstance().get(Calendar.MONTH) - 2) + "/" +
	 * Calendar.getInstance().get(Calendar.YEAR)));
	 * fundEstimationDay.setPreviousMonthValue3(Constants.PREVIOUS_MOTH_VALUE3);
	 * 
	 * occuranceOfWeek(count);
	 * 
	 * fundEstimationDay.setPreviousMonthWeekDate1(new
	 * SimpleDateFormat("yyyy-MM-dd"
	 * ).parse(getNthWeekDay(getNoofOccurance().intValue(),
	 * getDayofweek().intValue())));
	 * fundEstimationDay.setPreviousMonthWeekValue1
	 * (Constants.PREVIOUS_MOTH_WEEK_VALUE1);
	 * fundEstimationDay.setPreviousMonthWeekDate2(new
	 * SimpleDateFormat("yyyy-MM-dd"
	 * ).parse(getNthWeekDay(getNoofOccurance().intValue(),
	 * getDayofweek().intValue())));
	 * fundEstimationDay.setPreviousMonthWeekValue2
	 * (Constants.PREVIOUS_MOTH_WEEK_VALUE2);
	 * fundEstimationDay.setPreviousMonthWeekDate3(new
	 * SimpleDateFormat("yyyy-MM-dd"
	 * ).parse(getNthWeekDay(getNoofOccurance().intValue(),
	 * getDayofweek().intValue())));
	 * fundEstimationDay.setPreviousMonthWeekValue3
	 * (Constants.PREVIOUS_MOTH_WEEK_VALUE3);
	 * 
	 * fundEstimationDay.setCreatedBy(sessionManage.getUserName());
	 * fundEstimationDay.setCreatedDate(new Date());
	 * fundEstimationService.saveAndUpdateFundEstimationDays(fundEstimationDay);
	 * count++; }
	 * 
	 * for (int i = count; i < getNoOfDaysEstimation().intValue(); i++) {
	 * fundEstimationDay.setFundEstimtaionDaysId(getUpdateDaysId());
	 * 
	 * fundEstimationDay.setFundEstimtaionId(fundEstimation);
	 * fundEstimationDay.setFundEstimationDetailsId(fundEstimationDetails);
	 * fundEstimationDay
	 * .setExFundEstimationDaysApplicationCountry(applicationCountryMaster);
	 * fundEstimationDay.setFsCompanyMaster(companyMaster);
	 * fundEstimationDay.setExFundEstimationDaysBankCountry(bankCountryMaster);
	 * fundEstimationDay.setExCurrenyMaster(currencyMaster);
	 * fundEstimationDay.setExBankMaster(bankMaster);
	 * fundEstimationDay.setProjectionDate(new Date());
	 * fundEstimationDay.setEstimateDate(getNextDate(0));
	 * 
	 * fundEstimationDay.setPreviousMonthDate1(new
	 * SimpleDateFormat("dd/MM/yy").parse
	 * ((Calendar.getInstance().get(Calendar.DATE) + i) + "/" +
	 * (Calendar.getInstance().get(Calendar.MONTH) - 0) + "/" +
	 * Calendar.getInstance().get(Calendar.YEAR)));
	 * fundEstimationDay.setPreviousMonthValue1(Constants.PREVIOUS_MOTH_VALUE1);
	 * fundEstimationDay.setPreviousMonthDate2(new
	 * SimpleDateFormat("dd/MM/yy").parse
	 * ((Calendar.getInstance().get(Calendar.DATE) + i) + "/" +
	 * (Calendar.getInstance().get(Calendar.MONTH) - 1) + "/" +
	 * Calendar.getInstance().get(Calendar.YEAR)));
	 * fundEstimationDay.setPreviousMonthValue2(Constants.PREVIOUS_MOTH_VALUE2);
	 * fundEstimationDay.setPreviousMonthDate3(new
	 * SimpleDateFormat("dd/MM/yy").parse
	 * ((Calendar.getInstance().get(Calendar.DATE) + i) + "/" +
	 * (Calendar.getInstance().get(Calendar.MONTH) - 2) + "/" +
	 * Calendar.getInstance().get(Calendar.YEAR)));
	 * fundEstimationDay.setPreviousMonthValue3(Constants.PREVIOUS_MOTH_VALUE3);
	 * 
	 * occuranceOfWeek(i);
	 * 
	 * fundEstimationDay.setPreviousMonthWeekDate1(new
	 * SimpleDateFormat("yyyy-MM-dd"
	 * ).parse(getNthWeekDay(getNoofOccurance().intValue(),
	 * getDayofweek().intValue())));
	 * fundEstimationDay.setPreviousMonthWeekValue1
	 * (Constants.PREVIOUS_MOTH_WEEK_VALUE1);
	 * fundEstimationDay.setPreviousMonthWeekDate2(new
	 * SimpleDateFormat("yyyy-MM-dd"
	 * ).parse(getNthWeekDay(getNoofOccurance().intValue(),
	 * getDayofweek().intValue())));
	 * fundEstimationDay.setPreviousMonthWeekValue2
	 * (Constants.PREVIOUS_MOTH_WEEK_VALUE2);
	 * fundEstimationDay.setPreviousMonthWeekDate3(new
	 * SimpleDateFormat("yyyy-MM-dd"
	 * ).parse(getNthWeekDay(getNoofOccurance().intValue(),
	 * getDayofweek().intValue())));
	 * fundEstimationDay.setPreviousMonthWeekValue3
	 * (Constants.PREVIOUS_MOTH_WEEK_VALUE3);
	 * 
	 * fundEstimationDay.setCreatedBy(sessionManage.getUserName());
	 * fundEstimationDay.setCreatedDate(new Date());
	 * 
	 * fundEstimationService.saveAndUpdateFundEstimationDays(fundEstimationDay);
	 * 
	 * }
	 * 
	 * RequestContext.getCurrentInstance().execute("complete.show();"); }
	 */
	public boolean isBooCheck() {
		return BooCheck;
	}

	public void setBooCheck(boolean booCheck) {
		BooCheck = booCheck;
	}


	public void dateChange(SelectEvent event){
		Date date = (Date) event.getObject();
		System.out.print(date.toString());
		setProjectionDate(date);
		setCurrencyId(null);
		setiKONRate(null);
		setBankId(null);
		setNoOfDaysEstimation(null);
		setIkonrate(true);
		setFundEstimationList(null);
		setBankCountry(null);
		setNoOfDaysEstimationBol(false);

	}


	public void showHideSales() {

		// System.out.println("Rabil 12 While select the nbank country: \t " +
		// getBankId() + "\t show Hides :" + getBankCountry());

		System.out.println("Get Projection Date :"+getProjectionDate());
		// bankAccordingToBankCountry.clear();
		setProjectionDate(getProjectionDate());
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

		List<CountryCurrencyPopulationBean> lstCountryCurrency = fundEstimationService.getBankCurrencyFromView(sessionManage.getCountryId(), getBankCountry());

		if (lstCountryCurrency.size() > 0) {
			/*	for (CountryCurrencyPopulationBean ccp : lstCountryCurrency) {
				// System.out.println("Currency Name : " + ccp.getCurrencyName()
				// + " \t Country ID--->" + ccp.getBankCountryId() +
				// "\t getCurrencyId:" + ccp.getCurrencyId());
			}*/

			this.setCountryCurrencyList(lstCountryCurrency);
		}

	}

	// public void blurOnEstimationDate() throws ParseException {

	/**
	 * 
	 * @throws ParseException
	 *             : Modified by Rabil
	 * @throws AMGException 
	 */
	public void blurOnEstimationDate(FundEstimationPopulationBean fundEstimationBean) throws ParseException {

		setEditButton(true);
		setIkonrate(false);
		setBooSystemGen(true);
		setBooUpdateSalesProj(false);

		BigDecimal applicationCountryId = sessionManage.getCountryId();// generalService.getCountryIdBasedOnCountryAlpha2Code(Constants.KUWAIT_ALPHA_TWO_CODE);

		System.out.println("blurOnEstimationDate  :" + getNoOfDaysEstimation() + "\t getCompanyId(): " + getCompanyId() + "\t applicationCountryId :" + applicationCountryId + "\t getBankCountry() :" + getBankCountry() + "\t getBankId() :"
				+ getBankId() + "\t fundEstimationBean.getBankId :" + fundEstimationBean.getBankId());

		// BigDecimal value =
		// generalService.callValueDatedTransaction(getCompanyId(),
		// applicationCountryId, getBankCountry(), getCurrencyId(), getBankId(),
		// getNoOfDaysEstimation());

		BigDecimal value = generalService.callValueDatedTransaction(getCompanyId(), applicationCountryId, getBankCountry(), getCurrencyId(), fundEstimationBean.getBankId(), getNoOfDaysEstimation());
		if (value != null) {
			setValueDatedTrnsaction(value);
		} else {
			setValueDatedTrnsaction(BigDecimal.ZERO);
		}
		setSystemGeneratedSalesProjection(generalService.callSaleProjection(getCompanyId(), sessionManage.getCountryId(), getBankCountry(), getCurrencyId(), getBankId(), getNoOfDaysEstimation()));

		if (getCurrencyId().equals(fundEstimationDetailsBeanService.getUsdPk(Constants.FUND_ESTIMATION_USD_CURRENCY_CODE))) {
			setiKONRate(new BigDecimal(Constants.FUND_ESTIMATION_IKON_RATE));
			setIkonrate(true);
			if (getSalesProjection() != null && getiKONRate() != null) {
				setUsdSalesProjection(round(getSalesProjection().multiply(getiKONRate()), 2));
			} else if (getSystemGeneratedSalesProjection() != null && getiKONRate() != null) {
				setUsdSalesProjection(round(getSystemGeneratedSalesProjection().multiply(getiKONRate()), 2));
			} else {
			}
		} else {
			setIkonrate(false);
		}
		// To set and open datatable in form
		if (getNoofDaysPopup() != 1) {
			getDaysDataTable(fundEstimationBean);
		}
		setNoofDaysPopup(0);
	}

	public void occuranceOfWeek(int i) {
		Calendar localCalendar = Calendar.getInstance(Locale.FRANCE);
		localCalendar.set(localCalendar.get(Calendar.YEAR), localCalendar.get(Calendar.MONTH), (localCalendar.get(Calendar.DAY_OF_MONTH) + i));

		Date currentTime = localCalendar.getTime();
		int currentMonth = localCalendar.get(Calendar.MONTH);
		int currentYear = localCalendar.get(Calendar.YEAR);
		int currentDayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
		int currentDayOfMonth = localCalendar.get(Calendar.DAY_OF_MONTH);
		int CurrentDayOfYear = localCalendar.get(Calendar.DAY_OF_YEAR);

		//
		// Month value in Java is 0-based so 11 means December.
		//
		Calendar start = Calendar.getInstance(Locale.FRANCE);
		start.set(currentYear, currentMonth, 1);

		int numberOfDays = 0;
		int whichDay = currentDayOfWeek;
		int weekday = 0;

		while (start.before(localCalendar) || start.equals(localCalendar)) {
			if (start.get(Calendar.DAY_OF_WEEK) == whichDay) {
				numberOfDays++;
				start.add(Calendar.DAY_OF_MONTH, 7);
			} else {
				start.add(Calendar.DAY_OF_MONTH, 1);
			}
		}

		setNoofOccurance(new BigDecimal(numberOfDays));
		if (whichDay == 1) {
			weekday = 7;
		} else if (whichDay == 2) {
			weekday = 1;
		} else if (whichDay == 3) {
			weekday = 2;
		} else if (whichDay == 4) {
			weekday = 3;
		} else if (whichDay == 5) {
			weekday = 4;
		} else if (whichDay == 6) {
			weekday = 5;
		} else if (whichDay == 7) {
			weekday = 6;
		} else {
		}

		setDayofweek(new BigDecimal((weekday)));

	}

	public static String getNthWeekDay(int n, int dayOfWeek) {

		LocalDate today = new LocalDate();

		if (monthcal == 0) {
			LocalDate d1 = today.minusMonths(1);
			da = d1.getMonthOfYear();
			da1 = d1.getYear();
			monthcal++;
		} else if (monthcal == 1) {
			LocalDate d1 = today.minusMonths(2);
			da = d1.getMonthOfYear();
			da1 = d1.getYear();
			monthcal++;
		} else {
			LocalDate d1 = today.minusMonths(3);
			da = d1.getMonthOfYear();
			da1 = d1.getYear();
			monthcal = 0;
		}

		LocalDate start = new LocalDate(da1, da, 1);
		LocalDate date = start.withDayOfWeek(dayOfWeek);
		if (n <= 4) {
			return (date.isBefore(start)) ? date.plusWeeks(n).toString() : date.plusWeeks(n - 1).toString();
		} else if (n <= 5) {
			return (date.isBefore(start)) ? date.plusWeeks(n - 1).toString() : date.plusWeeks(n - 2).toString();
		} else {
			return (date.isBefore(start)) ? date.plusWeeks(n - 2).toString() : date.plusWeeks(n - 3).toString();
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
			populateAccountNumber();
			if (getCurrencyId().equals(fundEstimationDetailsBeanService.getUsdPk(Constants.FUND_ESTIMATION_USD_CURRENCY_CODE))) {
				setiKONRate(new BigDecimal(Constants.FUND_ESTIMATION_IKON_RATE));
				setIkonrate(true);
			} else {
				setIkonrate(false);
			}

		} else {
			setBoobankCurrency(false);
			setBooSelectbankCurrency(true);
			setBooSaveButton(false);
			setCurrencyOfBank(pbankcurrency);
		}

	}

	/**
	 * Bank Account Number will check how many currency available and retrieve
	 * according to BankId and CurrencyId
	 */

	public void populateAccountNumber() throws ParseException {
		setiKONRate(null);
		setBooUpdateSalesProj(false);
		setBooSystemGen(true);
		List<BankAccountDetails> ptabledata = fundEstimationService.getAccountNumber(getBankId(), getCurrencyId());
		if (getCurrencyId().equals(fundEstimationDetailsBeanService.getUsdPk(Constants.FUND_ESTIMATION_USD_CURRENCY_CODE))) {
			setIkonrate(true);
			setiKONRate(new BigDecimal(Constants.FUND_ESTIMATION_IKON_RATE));
		}
		accNumberSize = ptabledata.size();
		if (accNumberSize == 0) {
			setBoobankAcc(true);
			setBooSelectbankAcc(false);
			setBooSaveButton(true);

			RequestContext.getCurrentInstance().execute("dataNotFund.show();");
		} else if (accNumberSize == 1) {

			for (BankAccountDetails element : ptabledata) {
				setAccountId(new BigDecimal(element.getFundGlno()));
				setAccountNumberName(element.getBankAcctNo());
			}
			setAccountNumber(getAccountId().toPlainString());
			setBoobankAcc(true);
			setBooSelectbankAcc(false);
			setBooSaveButton(false);
			fetchDetails();

		} else {
			setBoobankAcc(false);
			setBooSelectbankAcc(true);
			setBooSaveButton(false);
			setLstAccountNumber(ptabledata);
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

	public List<TreasuryFundestimationView> getFundEstimationfromViewList() {
		return fundEstimationfromViewList;
	}

	public void setFundEstimationfromViewList(List<TreasuryFundestimationView> fundEstimationfromViewList) {
		this.fundEstimationfromViewList = fundEstimationfromViewList;
	}

	public List<TreasuryEstimationDaysView> getEstimationDaysFromViewList() {
		return estimationDaysFromViewList;
	}

	public void setEstimationDaysFromViewList(List<TreasuryEstimationDaysView> estimationDaysFromViewList) {
		this.estimationDaysFromViewList = estimationDaysFromViewList;
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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void onCellEdit(FundEstimationPopulationBean fundEstimationPopulationBean)
	{
		NumberFormat usa = NumberFormat.getInstance(Locale.getDefault());
		BigDecimal additionalSalesProjAmnt=  fundEstimationPopulationBean.getAdditionalSalesProjAmnt();

		if(additionalSalesProjAmnt==null)
		{
			additionalSalesProjAmnt=BigDecimal.ZERO;
		}

		if(additionalSalesProjAmnt.signum()==-1)
		{
			fundEstimationPopulationBean.setAdditionalSalesProjAmnt(BigDecimal.ZERO);
			setErrorMessage("Negative values not allowed");
			RequestContext.getCurrentInstance().execute("errorDailog.show();");
			return;
		}


		BigDecimal salesProjAmnt =fundEstimationPopulationBean.getSalesProjAmnt();


		if(salesProjAmnt==null)
		{
			salesProjAmnt=BigDecimal.ZERO;
		}

		if(salesProjAmnt!=null && additionalSalesProjAmnt!=null)
		{
			BigDecimal totalAmt=additionalSalesProjAmnt.add(salesProjAmnt);
			BigDecimal usaTotalAmt=totalAmt.divide(getiKONRate(),RoundingMode.FLOOR);

			fundEstimationPopulationBean.setTotalProjectionAmount(totalAmt);
			fundEstimationPopulationBean.setUsdTotalProjectionAmount(usaTotalAmt);
			fundEstimationPopulationBean.setUsdTotalProjectionAmountForDisplay(usa.format(usaTotalAmt));
			fundEstimationPopulationBean.setTotalProjectionAmountForDisplay(usa.format(totalAmt));
		}

	}

	public String getExceptionMessage()
	{
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage)
	{
		this.exceptionMessage = exceptionMessage;
	}

	public Date getCurrentDate()
	{
		return currentDate;
	}

	public void setCurrentDate(Date currentDate)
	{
		this.currentDate = currentDate;
	}

	public void onCellEditCashOrTomAmt(FundEstimationPopulationBean fundEstimationPopulationBean)
	{
		cashAmount = fundEstimationPopulationBean.getCashAmount();

		if(cashAmount==null)
		{
			setCashAmount(BigDecimal.ZERO);
			fundEstimationPopulationBean.setCashAmount(getCashAmount());
		}else{
			setCashAmount(cashAmount);
		}

		if(cashAmount.signum()==-1)
		{
			fundEstimationPopulationBean.setCashAmount(BigDecimal.ZERO);
			setErrorMessage("Negative values not allowed");
			RequestContext.getCurrentInstance().execute("errorDailog.show();");
			return;
		}else{
			BigDecimal tomAmount = fundEstimationPopulationBean.getTomAmount() == null ? BigDecimal.ZERO : fundEstimationPopulationBean.getTomAmount();
			BigDecimal spotAmount = fundEstimationPopulationBean.getSpotAmount() == null ? BigDecimal.ZERO : fundEstimationPopulationBean.getSpotAmount();
			BigDecimal salesProjAmnt = fundEstimationPopulationBean.getSalesProjAmnt() == null ? BigDecimal.ZERO : fundEstimationPopulationBean.getSalesProjAmnt();
			
			accumulateTotalProjection(getCashAmount(), tomAmount, spotAmount, salesProjAmnt, fundEstimationPopulationBean);
		}
	}
	
	public void onCellEditTomAmt(FundEstimationPopulationBean fundEstimationPopulationBean)
	{
		tomAmount = fundEstimationPopulationBean.getTomAmount();

		if(tomAmount==null)
		{
			setTomAmount(BigDecimal.ZERO);
			fundEstimationPopulationBean.setTomAmount(getTomAmount());
		}else{
			setTomAmount(tomAmount);
		}
		
		if(tomAmount.signum()==-1)
		{
			fundEstimationPopulationBean.setTomAmount(BigDecimal.ZERO);
			setErrorMessage("Negative values not allowed");
			RequestContext.getCurrentInstance().execute("errorDailog.show();");
			return;
		}else{
			BigDecimal cashAmount = fundEstimationPopulationBean.getCashAmount() == null ? BigDecimal.ZERO : fundEstimationPopulationBean.getCashAmount();
			BigDecimal spotAmount = fundEstimationPopulationBean.getSpotAmount() == null ? BigDecimal.ZERO : fundEstimationPopulationBean.getSpotAmount();
			BigDecimal salesProjAmnt = fundEstimationPopulationBean.getSalesProjAmnt() == null ? BigDecimal.ZERO : fundEstimationPopulationBean.getSalesProjAmnt();
			
			accumulateTotalProjection(cashAmount, getTomAmount() , spotAmount, salesProjAmnt, fundEstimationPopulationBean);
		}
	}
	
	public void onCellEditSpotAmt(FundEstimationPopulationBean fundEstimationPopulationBean)
	{
		spotAmount = fundEstimationPopulationBean.getSpotAmount();
		 
		if(spotAmount==null)
		{
			setSpotAmount(BigDecimal.ZERO);
			fundEstimationPopulationBean.setSpotAmount(getSpotAmount());
		}else{
			setSpotAmount(spotAmount);
		}

		if(spotAmount.signum()==-1)
		{
			fundEstimationPopulationBean.setSpotAmount(BigDecimal.ZERO);
			setErrorMessage("Negative values not allowed");
			RequestContext.getCurrentInstance().execute("errorDailog.show();");
			return;
		}else{
			BigDecimal cashAmount = fundEstimationPopulationBean.getCashAmount() == null ? BigDecimal.ZERO : fundEstimationPopulationBean.getCashAmount();
			BigDecimal tomAmount = fundEstimationPopulationBean.getTomAmount() == null ? BigDecimal.ZERO : fundEstimationPopulationBean.getTomAmount();
			BigDecimal salesProjAmnt = fundEstimationPopulationBean.getSalesProjAmnt() == null ? BigDecimal.ZERO : fundEstimationPopulationBean.getSalesProjAmnt();
			
			accumulateTotalProjection(cashAmount, tomAmount, getSpotAmount(), salesProjAmnt, fundEstimationPopulationBean);
		}
	}
	
	// accumulation of cash , TOM , SPOT and Overnight Sales projection
	public void accumulateTotalProjection(BigDecimal cashAmount , BigDecimal tomAmount , BigDecimal spotAmount , BigDecimal salesProjAmnt , FundEstimationPopulationBean fundEstimationPopulationBean){
		
		NumberFormat usa = NumberFormat.getInstance(Locale.getDefault());

		BigDecimal totalAmt = salesProjAmnt.add(cashAmount).add(tomAmount).add(spotAmount);
		BigDecimal usaTotalAmt=totalAmt.divide(getiKONRate(),RoundingMode.FLOOR);

		fundEstimationPopulationBean.setTotalProjectionAmount(totalAmt);
		fundEstimationPopulationBean.setUsdTotalProjectionAmount(usaTotalAmt);
		fundEstimationPopulationBean.setUsdTotalProjectionAmountForDisplay(usa.format(usaTotalAmt));
		fundEstimationPopulationBean.setTotalProjectionAmountForDisplay(usa.format(totalAmt));
	
	}

}
