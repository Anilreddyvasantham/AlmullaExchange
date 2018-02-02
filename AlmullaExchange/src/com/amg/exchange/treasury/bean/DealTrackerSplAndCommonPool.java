package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.StandardInstruction;
import com.amg.exchange.treasury.model.StandardInstructionDetails;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;
import com.amg.exchange.treasury.model.ViewCorrespondingBankCountry;
import com.amg.exchange.treasury.service.IBankTransferService;
import com.amg.exchange.treasury.service.IDealTrackerInfoService;
import com.amg.exchange.treasury.service.IFXDetailInformationService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.IPipsMasterService;
import com.amg.exchange.treasury.service.IStandardInstructionsService;
import com.amg.exchange.treasury.viewModel.DealTrackerTicketView;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;

@Component("dealTrackerSplAndCommonPool")
@Scope("session")
public class DealTrackerSplAndCommonPool<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Logger log = Logger.getLogger(DealTrackerSplAndCommonPool.class);
	
	// Create Session
	SessionStateManage sessionStateManage = new SessionStateManage();
	
	// Auto wire required Services
	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	IPipsMasterService iPipsMasterService;
	
	@Autowired
	IDealTrackerInfoService<T> idealTrackerInfoService;
	
	@Autowired
	IFXDetailInformationService<T> fXDetailInformationService;
	
	@Autowired
	FXDetailInfoBean<T> fXDetailInfoBean;
	@Autowired
	IFundEstimationService<T> fundEstimationService;
	/*@Autowired
	IBankMasterService<T> iBankMasterService;*/
	
	@Autowired
	IBankTransferService<T> bankTransferService;
	
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	
	@Autowired
	IStandardInstructionsService<T> iStandardInstructionsService;
	// all list
	//private List<CountryMasterDesc> lstAllCountry = new ArrayList<CountryMasterDesc>();
	private List<BeneCountryService> lstAllCurrencyBasedonCountry = new ArrayList<BeneCountryService>();
	private List<BankMaster> lstAllbankBasedOnCountry = new ArrayList<BankMaster>();
	private List<BankApplicability> lstAllbankBasedOnCntry = new ArrayList<BankApplicability>();
	private List<DealTrackerViewTicketDataTable> lstViewTickets = new ArrayList<DealTrackerViewTicketDataTable>();
	private Map<BigDecimal , String> lstBankCodeId = new HashMap<BigDecimal , String>();
	private Map<BigDecimal , String> lstCurrencyCodeId = new HashMap<BigDecimal , String>();
	private List<DealTrackerViewTicketDataTable> lstDealTrackerDt = new ArrayList<DealTrackerViewTicketDataTable>();
	private List<Document> lstDocument=new ArrayList<Document>();
	private List<UserFinancialYear> dealYearList= new ArrayList<UserFinancialYear>();
	private List<StandardInstructionDetails> lststandardInstrnDetails = new ArrayList<StandardInstructionDetails>();
	List<ViewCorrespondingBankCountry> lstAllCountry=  new ArrayList<ViewCorrespondingBankCountry>();

	private List<CountryCurrencyPopulationBean> lstAllCountryCurrencyPopulationBean = new ArrayList<CountryCurrencyPopulationBean>();
	/*public List<CountryMasterDesc> getLstAllCountry() {
		return lstAllCountry;
	}
	public void setLstAllCountry(List<CountryMasterDesc> lstAllCountry) {
		this.lstAllCountry = lstAllCountry;
	}*/
	
	

	public List<BeneCountryService> getLstAllCurrencyBasedonCountry() {
		return lstAllCurrencyBasedonCountry;
	}
	public List<CountryCurrencyPopulationBean> getLstAllCountryCurrencyPopulationBean() {
		return lstAllCountryCurrencyPopulationBean;
	}
	public void setLstAllCountryCurrencyPopulationBean(
			List<CountryCurrencyPopulationBean> lstAllCountryCurrencyPopulationBean) {
		this.lstAllCountryCurrencyPopulationBean = lstAllCountryCurrencyPopulationBean;
	}
	public List<ViewCorrespondingBankCountry> getLstAllCountry() {
		return lstAllCountry;
	}
	public void setLstAllCountry(List<ViewCorrespondingBankCountry> lstAllCountry) {
		this.lstAllCountry = lstAllCountry;
	}
	public void setLstAllCurrencyBasedonCountry(List<BeneCountryService> lstAllCurrencyBasedonCountry) {
		this.lstAllCurrencyBasedonCountry = lstAllCurrencyBasedonCountry;
	}

	public List<BankMaster> getLstAllbankBasedOnCountry() {
		return lstAllbankBasedOnCountry;
	}
	public void setLstAllbankBasedOnCountry(List<BankMaster> lstAllbankBasedOnCountry) {
		this.lstAllbankBasedOnCountry = lstAllbankBasedOnCountry;
	}

	public List<DealTrackerViewTicketDataTable> getLstViewTickets() {
		return lstViewTickets;
	}
	public void setLstViewTickets(List<DealTrackerViewTicketDataTable> lstViewTickets) {
		this.lstViewTickets = lstViewTickets;
	}
	
	public Map<BigDecimal, String> getLstBankCodeId() {
		return lstBankCodeId;
	}
	public void setLstBankCodeId(Map<BigDecimal, String> lstBankCodeId) {
		this.lstBankCodeId = lstBankCodeId;
	}
	
	public Map<BigDecimal, String> getLstCurrencyCodeId() {
		return lstCurrencyCodeId;
	}
	public void setLstCurrencyCodeId(Map<BigDecimal, String> lstCurrencyCodeId) {
		this.lstCurrencyCodeId = lstCurrencyCodeId;
	}

	public List<DealTrackerViewTicketDataTable> getLstDealTrackerDt() {
		return lstDealTrackerDt;
	}
	public void setLstDealTrackerDt(List<DealTrackerViewTicketDataTable> lstDealTrackerDt) {
		this.lstDealTrackerDt = lstDealTrackerDt;
	}

	public List<StandardInstructionDetails> getLststandardInstrnDetails() {
		return lststandardInstrnDetails;
	}
	public void setLststandardInstrnDetails(List<StandardInstructionDetails> lststandardInstrnDetails) {
		this.lststandardInstrnDetails = lststandardInstrnDetails;
	}
	public List<BankApplicability> getLstAllbankBasedOnCntry() {
		return lstAllbankBasedOnCntry;
	}
	public void setLstAllbankBasedOnCntry(List<BankApplicability> lstAllbankBasedOnCntry) {
		this.lstAllbankBasedOnCntry = lstAllbankBasedOnCntry;
	}




	// form variable
	private Date currentDate;
	private Date effectiveMinDate = new Date();
	private BigDecimal countryId;
	private BigDecimal currencyId;
	private BigDecimal bankId;
	private Boolean booRenderDataTableApp = false;
	private String warningMessage;
	
	// extra variable
	private BigDecimal company;
	private String document;
	private BigDecimal documentNo;
	private String dealYear;
	private String userDealYear;
	private BigDecimal dealYearId;
	private BigDecimal userDealYearId;
	private String purchaseMultipleDivision;
	private BigDecimal saveDocumentSerialID = null;
	private String bankGLAccountNumber;
	private String bankAccountNumber;
	private BigDecimal purchaseBankId;
	private BigDecimal purchaseCurrencyId;
	private BigDecimal purchaseInstrunction;
	private BigDecimal saleBankId;
	private BigDecimal saleCurrencyId;
	private BigDecimal saleInstrunction;
	private BigDecimal saleAvgRate;
	private BigDecimal saleLocalAmount;
	
	private Boolean booDocVisble;
	private String errmsg;

	
	// form get - set
	
	
	public Date getCurrentDate() {
		return currentDate;
	}
	public Boolean getBooDocVisble() {
		return booDocVisble;
	}
	public void setBooDocVisble(Boolean booDocVisble) {
		this.booDocVisble = booDocVisble;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
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
	
	public Boolean getBooRenderDataTableApp() {
		return booRenderDataTableApp;
	}
	public void setBooRenderDataTableApp(Boolean booRenderDataTableApp) {
		this.booRenderDataTableApp = booRenderDataTableApp;
	}
	
	public String getWarningMessage() {
		return warningMessage;
	}
	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}
	
	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}
	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}
	
	
	// Extra variable
	public BigDecimal getPurchaseInstrunction() {
		return purchaseInstrunction;
	}
	public void setPurchaseInstrunction(BigDecimal purchaseInstrunction) {
		this.purchaseInstrunction = purchaseInstrunction;
	}
	
	public BigDecimal getCompany() {
		return company;
	}
	public void setCompany(BigDecimal company) {
		this.company = company;
	}
	
	public List<Document> getLstDocument() {
		return lstDocument;
	}
	public void setLstDocument(List<Document> lstDocument) {
		this.lstDocument = lstDocument;
	}
	
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	public String getDealYear() {
		return dealYear;
	}
	public void setDealYear(String dealYear) {
		this.dealYear = dealYear;
	}
		
	public List<UserFinancialYear> getDealYearList() {
		return dealYearList;
	}
	public void setDealYearList(List<UserFinancialYear> dealYearList) {
		this.dealYearList = dealYearList;
	}
	
	public String getUserDealYear() {
		return userDealYear;
	}
	public void setUserDealYear(String userDealYear) {
		this.userDealYear = userDealYear;
	}

	public BigDecimal getDealYearId() {
		return dealYearId;
	}
	public void setDealYearId(BigDecimal dealYearId) {
		this.dealYearId = dealYearId;
	}
	
	public BigDecimal getUserDealYearId() {
		return userDealYearId;
	}
	public void setUserDealYearId(BigDecimal userDealYearId) {
		this.userDealYearId = userDealYearId;
	}

	public String getPurchaseMultipleDivision() {
		return purchaseMultipleDivision;
	}
	public void setPurchaseMultipleDivision(String purchaseMultipleDivision) {
		this.purchaseMultipleDivision = purchaseMultipleDivision;
	}

	public BigDecimal getSaveDocumentSerialID() {
		return saveDocumentSerialID;
	}
	public void setSaveDocumentSerialID(BigDecimal saveDocumentSerialID) {
		this.saveDocumentSerialID = saveDocumentSerialID;
	}
	
	public String getBankGLAccountNumber() {
		return bankGLAccountNumber;
	}
	public void setBankGLAccountNumber(String bankGLAccountNumber) {
		this.bankGLAccountNumber = bankGLAccountNumber;
	}
	
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	
	public BigDecimal getPurchaseBankId() {
		return purchaseBankId;
	}
	public void setPurchaseBankId(BigDecimal purchaseBankId) {
		this.purchaseBankId = purchaseBankId;
	}
	
	public BigDecimal getPurchaseCurrencyId() {
		return purchaseCurrencyId;
	}
	public void setPurchaseCurrencyId(BigDecimal purchaseCurrencyId) {
		this.purchaseCurrencyId = purchaseCurrencyId;
	}
		
	public BigDecimal getSaleBankId() {
		return saleBankId;
	}
	public void setSaleBankId(BigDecimal saleBankId) {
		this.saleBankId = saleBankId;
	}
	
	public BigDecimal getSaleCurrencyId() {
		return saleCurrencyId;
	}
	public void setSaleCurrencyId(BigDecimal saleCurrencyId) {
		this.saleCurrencyId = saleCurrencyId;
	}
	
	public BigDecimal getSaleInstrunction() {
		return saleInstrunction;
	}
	public void setSaleInstrunction(BigDecimal saleInstrunction) {
		this.saleInstrunction = saleInstrunction;
	}
	
	public BigDecimal getSaleAvgRate() {
		return saleAvgRate;
	}
	public void setSaleAvgRate(BigDecimal saleAvgRate) {
		this.saleAvgRate = saleAvgRate;
	}
	
	public BigDecimal getSaleLocalAmount() {
		return saleLocalAmount;
	}
	public void setSaleLocalAmount(BigDecimal saleLocalAmount) {
		this.saleLocalAmount = saleLocalAmount;
	}	
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// page navigation
	public void dealTrackerSplAndCommonPageNavigation(){
		// clearing
		clearCache();
		// setting current Date
		currentDate();
		// fetching All Country
		fetchAllCountryList();
		dealYearFromDB();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "DealTrackerSplAndCommonPool.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/DealTrackerSplAndCommonPool.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clearCache(){
		
		setCountryId(null);
		setCurrencyId(null);
		setBankId(null);
		lstAllCurrencyBasedonCountry.clear();
		lstAllbankBasedOnCntry.clear();
		lstViewTickets.clear();
		setBooRenderDataTableApp(false);
		setLstAllCountry(null);
	}	
	
	// set Log Date Current Date
	public void currentDate() {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dealDate=format.format(new Date());

		try {
			setCurrentDate(format.parse(dealDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	
	// fetching all country
	public void fetchAllCountryList(){
		/*lstAllCountry.clear();
		List<CountryMasterDesc> lstCountry = generalService.getCountryList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1));
		if (lstCountry.size() != 0) {
			lstAllCountry.addAll(lstCountry);
		}*/
		List<ViewCorrespondingBankCountry> lstViewCorrespondingBankCountry=idealTrackerInfoService.getCorrespondingbankCountryList();
		setLstAllCountry(lstViewCorrespondingBankCountry);
	}
	
	// fetch both Currency and Banks Based on Country Id
	public void fetchCurrencyAndBanksBasedCountryId(){
		fetchCurrencyBasedOnCountry();
		fetchBankBasedOnCountry();
	}
	
	// fetch currency based on country
	public void fetchCurrencyBasedOnCountry(){
		lstAllCurrencyBasedonCountry.clear();
		lstCurrencyCodeId.clear();
		List<BeneCountryService> lstCurrency = iPipsMasterService.getCurrencyMaster(getCountryId());
		if(lstCurrency.size() != 0){
			lstAllCurrencyBasedonCountry.addAll(lstCurrency);
			for (BeneCountryService beneCountryService : lstAllCurrencyBasedonCountry) {
				lstCurrencyCodeId.put(beneCountryService.getCurrencyId().getCurrencyId(), beneCountryService.getCurrencyId().getQuoteName());
			}
		}
		setLstAllCountryCurrencyPopulationBean(null);
		List<CountryCurrencyPopulationBean> lstCountryCurrency = fundEstimationService.getBankCurrencyFromView(sessionStateManage.getCountryId(), getCountryId());
		
		setLstAllCountryCurrencyPopulationBean(lstCountryCurrency);
		
	}
	
	// fetch Bank based on country
	public void fetchBankBasedOnCountry(){
		lstAllbankBasedOnCntry.clear();
		//List<BankMaster> lstbank = generalService.getAllBankList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"), getCountryId());
		List<BankApplicability> lstbank=generalService.getCoresBankList( getCountryId());
		System.out.println("===================list is="+lstbank.size());
		if(lstbank.size() != 0){
			lstAllbankBasedOnCntry.addAll(lstbank);
			for (BankApplicability bankMaster : lstAllbankBasedOnCntry) {
				lstBankCodeId.put(bankMaster.getBankMaster().getBankId(), bankMaster.getBankMaster().getReutersBankName());
			}
		}
	}
	
	// fetch records from View Tickets based on Date , Country , Currency , Bank 
	public void fetchAllViewTickets(){
		
		/*BigDecimal docRefNumber=getDocumentSerialIdNumber(Constants.Yes);
		if(docRefNumber.compareTo(BigDecimal.ZERO)==0)
		{
			return;
		}*/
		
		lstViewTickets.clear();
		String bankCode = null;
		String currencyCode = null;
		WarningHandler handler = new WarningHandler();
		setWarningMessage(null);
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		if(lstBankCodeId.size() != 0){
			bankCode = lstBankCodeId.get(getBankId());
		}
		
		if(lstCurrencyCodeId.size() != 0){
			currencyCode = lstCurrencyCodeId.get(getCurrencyId());
		}
		
		List<DealTrackerTicketView> lstViewRecords;
		try {
			lstViewRecords = idealTrackerInfoService.getDealTrackerTicketView(getCurrentDate(), getCountryId(), currencyCode , bankCode);
			
			
			//lstViewRecords = idealTrackerInfoService.getDealTrackerTicketwithNativeQuery(getCurrentDate(), getCountryId(), currencyCode , bankCode);
			if (lstViewRecords.size() != 0) {
				
				setBooRenderDataTableApp(true);
				NumberFormat usa = NumberFormat.getInstance(Locale.getDefault());
				for (DealTrackerTicketView dealTrackerTicketView : lstViewRecords) {
					
					DealTrackerViewTicketDataTable lstview = new DealTrackerViewTicketDataTable();
					
					lstview.setSlNo(dealTrackerTicketView.getSlNo());
					lstview.setDealId(dealTrackerTicketView.getDealId());
					lstview.setTimeofDeal(dealTrackerTicketView.getTimeofDeal());
					lstview.setDealerName(dealTrackerTicketView.getDealerName());
					lstview.setDealWith(dealTrackerTicketView.getDealWith());
					lstview.setConcludedBy(dealTrackerTicketView.getConcludedBy());
					lstview.setCommentText(dealTrackerTicketView.getCommentText());
					lstview.setReuterReference(dealTrackerTicketView.getReuterReference());
					lstview.setPdBankCode(dealTrackerTicketView.getPdBank());
					lstview.setSdBankCode(dealTrackerTicketView.getSdBank());
					lstview.setMultiFactor(dealTrackerTicketView.getMultiFactor());
					lstview.setPdValueDateForDisplay(dateformat.format(dealTrackerTicketView.getPdValueDate()));
					
					List<BankMaster> lstPDBankMaster= idealTrackerInfoService.getBankMasterInfo(dealTrackerTicketView.getPdBank());
					if(lstPDBankMaster.size()>0)
					{
						lstview.setPdAMXBankCode(lstPDBankMaster.get(0).getBankCode());
					}
					
					//lstview.setPdBankName();
					lstview.setPdValueDate(dealTrackerTicketView.getPdValueDate());
					
					List<BankMaster> lstSDBankMaster= idealTrackerInfoService.getBankMasterInfo(dealTrackerTicketView.getSdBank());
					if(lstSDBankMaster.size()>0)
					{
						lstview.setSdAMXBankCode(lstSDBankMaster.get(0).getBankCode());
					}
					

					//lstview.setSdBankName();
					lstview.setSdValueDate(dealTrackerTicketView.getSdValueDate());
					lstview.setPdCurrencyCode(dealTrackerTicketView.getPdCurrency());
					//lstview.setPdCurrencyName();
					lstview.setSdCurrencyCode(dealTrackerTicketView.getSdCurrency());
					//lstview.setSdCurrencyName();
					BigDecimal pdCurrencyId = fetchCurrencyMasterByCurrencyQuoteCode(dealTrackerTicketView.getPdCurrency());
					if(pdCurrencyId != null && pdCurrencyId.compareTo(BigDecimal.ZERO) != 0){
						BigDecimal pdFcAmount=GetRound.roundBigDecimal(dealTrackerTicketView.getPdFcAmt(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(pdCurrencyId));
						lstview.setPdFCAmount(pdFcAmount);
						lstview.setPdFCAmountForDisplay(usa.format(pdFcAmount));
					}else{
						lstview.setPdFCAmount(dealTrackerTicketView.getPdFcAmt());
						lstview.setPdFCAmountForDisplay(usa.format(dealTrackerTicketView.getPdFcAmt()));
					}
					
					
					List<BankAccountDetails> lstBankAccountDetails = idealTrackerInfoService.getBankAccountDetlsBasedOnCode(dealTrackerTicketView.getSdBank(),dealTrackerTicketView.getSdCurrency());
					
					if(lstBankAccountDetails != null && lstBankAccountDetails.size() != 0){
						List<BankAccountDetails> lstBankACCGLNO = new ArrayList<BankAccountDetails>();
						for (BankAccountDetails bankAccountDetails : lstBankAccountDetails) {
							if(bankAccountDetails.getGlno() != null){
								lstBankACCGLNO.add(bankAccountDetails);
							}
						}
						if(lstBankACCGLNO.size() != 0){
							lstview.setLstBankAccountDetails(lstBankACCGLNO);
						}
					}
					
					BigDecimal sdCurrencyId = fetchCurrencyMasterByCurrencyQuoteCode(dealTrackerTicketView.getSdCurrency());
					if(sdCurrencyId != null && sdCurrencyId.compareTo(BigDecimal.ZERO) != 0){
						lstview.setSaleAmount(GetRound.roundBigDecimal(dealTrackerTicketView.getSaleAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(sdCurrencyId)));
					}else{
						lstview.setSaleAmount(dealTrackerTicketView.getSaleAmount());
					}
					
					lstview.setPdExchangerate(GetRound.roundBigDecimal(dealTrackerTicketView.getPdExchangerate(),6));
					lstview.setHighValueYear(dealTrackerTicketView.getHighValueYear());
					lstview.setHighValueNO(dealTrackerTicketView.getHighValueNO());
					
					if(dealTrackerTicketView.getHighValueYear() != null || dealTrackerTicketView.getHighValueNO() != null){
						HashMap<String,String> listValues= idealTrackerInfoService.getForeignCurrrencyAmountFrmSpclCustomer(dealTrackerTicketView.getHighValueYear(), dealTrackerTicketView.getHighValueNO());
						if(listValues!=null && listValues.size()!=0)
						{
							lstview.setHighValueAmount(new BigDecimal(listValues.get("FCAMOUNT")==null ? "0" : listValues.get("FCAMOUNT")));
							lstview.setHighValueAmountForDisplay(usa.format(new BigDecimal(listValues.get("FCAMOUNT")==null ? "0" : listValues.get("FCAMOUNT"))));
							lstview.setCustomerReference(new BigDecimal(listValues.get("CUSTOMERREFERENCE")==null ? "0" :listValues.get("CUSTOMERREFERENCE")));
							lstview.setSpecialDealCustRequestId(new BigDecimal(listValues.get("CUST_SPECIALDEALREQUESTID")==null ?"0": listValues.get("CUST_SPECIALDEALREQUESTID")));
						}else
						{
							lstview.setHighValueAmount(BigDecimal.ZERO);
							lstview.setHighValueAmountForDisplay("");
							lstview.setCustomerReference(BigDecimal.ZERO);
							lstview.setSpecialDealCustRequestId(BigDecimal.ZERO);
						}
						
					}
					
					//if (lstview.getAccountDetId() != null) {
				if(true){
						if(dealTrackerTicketView.getHighValueYear() != null || dealTrackerTicketView.getHighValueNO() != null){
							lstview.setHighValueComb(dealTrackerTicketView.getHighValueYear() + " / " + dealTrackerTicketView.getHighValueNO());
							lstview.setDisableCol(true);
							lstview.setBooEnableselectCheck(false);
						}else{
							lstview.setDisableCol(false);
							lstview.setBooEnableselectCheck(true);
						}
					}else{
						lstview.setDisableCol(true);
						lstview.setBooEnableselectCheck(true);
					}
					
					lstview.setDisableCol(true);
					lstview.setBooEnableselectCheck(true);
					
					lstViewTickets.add(lstview);
					
				}
				
			}else{
				setBooRenderDataTableApp(false);
				setWarningMessage(handler.showWarningMessage("lbl.noRecordFound", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
			}
		} catch (AMGException e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}
		
		
	}
	
	// checking for the standard instructions details for combination
	public void checkStandardInstrnData(DealTrackerViewTicketDataTable dealTrackerViewRecord){
		
		BigDecimal docRefNumber=getDocumentSerialIdNumber(Constants.Yes);
		if(docRefNumber.compareTo(BigDecimal.ZERO)==0)
		{
			return;
		}
		
		System.out.println("dealTrackerViewRecord.getAccountDetId() :"+dealTrackerViewRecord.getAccountDetId());
		if (dealTrackerViewRecord.getAccountDetId() != null) {
			
			String splitIndicator=idealTrackerInfoService.getSplitIndicatorFromBankMaster(dealTrackerViewRecord.getPdAMXBankCode());
			if(splitIndicator!=null && splitIndicator.equalsIgnoreCase(Constants.Yes))
			{
				dealTrackerViewRecord.setDisableCol(false);
				dealTrackerViewRecord.setBooEnableselectCheck(true);
				
				if(dealTrackerViewRecord.getHighValueYear() != null || dealTrackerViewRecord.getHighValueNO() != null){
					dealTrackerViewRecord.setHighValueComb(dealTrackerViewRecord.getHighValueYear() + " / " + dealTrackerViewRecord.getHighValueNO()+"(" +dealTrackerViewRecord.getHighValueAmountForDisplay()+")");
					//dealTrackerViewRecord.setDisableCol(true);
					//dealTrackerViewRecord.setBooEnableselectCheck(false);
				}else{
					//dealTrackerViewRecord.setDisableCol(false);
					//dealTrackerViewRecord.setBooEnableselectCheck(true);
				}
			}else
			{
				boolean status = getSelectRecordFrom(dealTrackerViewRecord);
				if(status){
					dealTrackerViewRecord.setSelectCheck(true);
				}else{
					
					dealTrackerViewRecord.setSelectCheck(false);
				}
				dealTrackerViewRecord.setDisableCol(true);
				dealTrackerViewRecord.setBooEnableselectCheck(true);
			}
			
		}else{
			dealTrackerViewRecord.setDisableCol(true);
			dealTrackerViewRecord.setBooEnableselectCheck(true);
			//Added by Rabil
			dealTrackerViewRecord.setSelectCheck(false);
		}
		
	}
	
	// on cell edit EFT the Accumulation of Sale Amount must be Equal
	public void cellEditForEFTVal(DealTrackerViewTicketDataTable dealtrackerDt){
		
		BigDecimal totalAmount = BigDecimal.ZERO;		
		
		System.out.println(" EFT : " +dealtrackerDt.getEftval() + " TT : " + dealtrackerDt.getTtVal() + " CASH : " + dealtrackerDt.getCashVal());
		System.out.println(" Purchase Amount : " +dealtrackerDt.getPdFCAmount());
		
		
		if(dealtrackerDt.getHighValueAmount()!= null){
			totalAmount = totalAmount.add(dealtrackerDt.getHighValueAmount());
		}
		
		if(dealtrackerDt.getEftval() != null){
			totalAmount = totalAmount.add(dealtrackerDt.getEftval());
		}
		
		if(dealtrackerDt.getTtVal() != null){
			totalAmount = totalAmount.add(dealtrackerDt.getTtVal());
		}
		
		if(dealtrackerDt.getCashVal() != null){
			totalAmount = totalAmount.add(dealtrackerDt.getCashVal());
		}
		
		if(totalAmount.compareTo(dealtrackerDt.getPdFCAmount())>0){
			dealtrackerDt.setEftval(null);
		}else if(totalAmount.compareTo(dealtrackerDt.getPdFCAmount())==0){
			// method of selected records to Approve
			boolean status = getSelectRecordFrom(dealtrackerDt);
			if(status){
				dealtrackerDt.setSelectCheck(true);
			}else{
				dealtrackerDt.setEftval(null);
				dealtrackerDt.setSelectCheck(false);
			}
		}else{
			dealtrackerDt.setSelectCheck(false);
			lstDealTrackerDt.remove(dealtrackerDt);
		}
		
	}
	
	// on cell edit TT the Accumulation of Sale Amount must be Equal
	public void cellEditForTTVal(DealTrackerViewTicketDataTable dealtrackerDt){

		BigDecimal totalAmount = BigDecimal.ZERO;		

		System.out.println(" EFT : " +dealtrackerDt.getEftval() + " TT : " + dealtrackerDt.getTtVal() + " CASH : " + dealtrackerDt.getCashVal());
		System.out.println(" Purchase Amount : " +dealtrackerDt.getPdFCAmount());

		if(dealtrackerDt.getHighValueAmount()!= null){
			totalAmount = totalAmount.add(dealtrackerDt.getHighValueAmount());
		}
		
		if(dealtrackerDt.getEftval() != null){
			totalAmount = totalAmount.add(dealtrackerDt.getEftval());
		}

		if(dealtrackerDt.getTtVal() != null){
			totalAmount = totalAmount.add(dealtrackerDt.getTtVal());
		}

		if(dealtrackerDt.getCashVal() != null){
			totalAmount = totalAmount.add(dealtrackerDt.getCashVal());
		}

		if(totalAmount.compareTo(dealtrackerDt.getPdFCAmount())>0){
			dealtrackerDt.setTtVal(null);
		}else if(totalAmount.compareTo(dealtrackerDt.getPdFCAmount())==0){
			// method of selected records to Approve
			boolean status = getSelectRecordFrom(dealtrackerDt);
			if(status){
				dealtrackerDt.setSelectCheck(true);
			}else{
				dealtrackerDt.setTtVal(null);
				dealtrackerDt.setSelectCheck(false);
			}
		}else{
			dealtrackerDt.setSelectCheck(false);
			lstDealTrackerDt.remove(dealtrackerDt);
		}

	}
	
	// on cell edit TT the Accumulation of Sale Amount must be Equal
	public void cellEditForCASHVal(DealTrackerViewTicketDataTable dealtrackerDt){

		BigDecimal totalAmount = BigDecimal.ZERO;		

		System.out.println(" EFT : " +dealtrackerDt.getEftval() + " TT : " + dealtrackerDt.getTtVal() + " CASH : " + dealtrackerDt.getCashVal());
		System.out.println(" Purchase Amount : " +dealtrackerDt.getPdFCAmount());

		if(dealtrackerDt.getHighValueAmount()!= null){
			totalAmount = totalAmount.add(dealtrackerDt.getHighValueAmount());
		}
		
		if(dealtrackerDt.getEftval() != null){
			totalAmount = totalAmount.add(dealtrackerDt.getEftval());
		}

		if(dealtrackerDt.getTtVal() != null){
			totalAmount = totalAmount.add(dealtrackerDt.getTtVal());
		}

		if(dealtrackerDt.getCashVal() != null){
			totalAmount = totalAmount.add(dealtrackerDt.getCashVal());
		}

		if(totalAmount.compareTo(dealtrackerDt.getPdFCAmount())>0){
			dealtrackerDt.setCashVal(null);
		}else if(totalAmount.compareTo(dealtrackerDt.getPdFCAmount())==0){
			// method of selected records to Approve
			boolean status = getSelectRecordFrom(dealtrackerDt);
			if(status){
				dealtrackerDt.setSelectCheck(true);
			}else{
				dealtrackerDt.setCashVal(null);
				dealtrackerDt.setSelectCheck(false);
			}
		}else{
			dealtrackerDt.setSelectCheck(false);
			lstDealTrackerDt.remove(dealtrackerDt);
		}

	}
	
	// if highvalueDeal the select box is enable to click 
	public void highValueDealSelectBox(DealTrackerViewTicketDataTable dealtrackerDt){
		boolean status = getSelectRecordFrom(dealtrackerDt);
		if(status){
			dealtrackerDt.setSelectCheck(true);
		}else{
			dealtrackerDt.setSelectCheck(false);
		}
	}
	
	// method of selected records to Approve
	public boolean getSelectRecordFrom(DealTrackerViewTicketDataTable dealtrackerDt){
		boolean checkStatus = false;
		try{
			
			WarningHandler handler = new WarningHandler();
			setWarningMessage(null);

			HashMap<String, Object> rtnCodeMap=idealTrackerInfoService.getIDsFromCodeDealTickets(dealtrackerDt,sessionStateManage.getCountryId(),sessionStateManage.getCompanyId());

			//Validation check
			Boolean dealWithBank=(Boolean)rtnCodeMap.get("DealWithBank");
			if(!dealWithBank.booleanValue())
			{
				setWarningMessage(handler.showWarningMessage("lbl.dealwithbank", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
				return checkStatus;

			}
			
			Boolean dealWithMultipleBnk=(Boolean)rtnCodeMap.get("DealWithMultipleBnk");
			if(dealWithMultipleBnk!=null && !dealWithMultipleBnk)
			{
				setWarningMessage(handler.showWarningMessage("lbl.multidealwithbank", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
				return checkStatus;
			}

			Boolean pdBank=(Boolean)rtnCodeMap.get("PDBank");
			if(!pdBank.booleanValue())
			{
				setWarningMessage(handler.showWarningMessage("lbl.pdBank", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
				return checkStatus;
			}
			
			Boolean pdMultiBank=(Boolean)rtnCodeMap.get("PDMultiBank");
			if(pdMultiBank!=null && !pdMultiBank.booleanValue())
			{
				setWarningMessage(handler.showWarningMessage("lbl.multidealwithbank", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
				return checkStatus;
			}

			Boolean sdBank=(Boolean)rtnCodeMap.get("SDBank");
			if(!sdBank.booleanValue())
			{
				setWarningMessage(handler.showWarningMessage("lbl.sdBank", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
				return checkStatus;
			}

			Boolean sdMultiBank=(Boolean)rtnCodeMap.get("SDMultiBank");
			if(sdMultiBank!=null && !sdMultiBank.booleanValue())
			{
				setWarningMessage(handler.showWarningMessage("lbl.multidealwithbank", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
				return checkStatus;
			}
			
			Boolean pdMultipleAccount=(Boolean)rtnCodeMap.get("PDMultipleAccount");
			if(pdMultipleAccount!=null && !pdMultipleAccount.booleanValue())
			{
				setWarningMessage(handler.showWarningMessage("lbl.pdMultipleAccount", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
				return checkStatus;
			}
			
			
			
			
			Boolean pdCurrency=(Boolean)rtnCodeMap.get("PDCurrency");
			if(!pdCurrency.booleanValue())
			{
				setWarningMessage(handler.showWarningMessage("lbl.pdCurrency", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
				return checkStatus;
			}
			String pdFundGlAccNO=null;
			BigDecimal pdAccontDetId=null;
			
			if(pdCurrency.booleanValue())
			{
				pdFundGlAccNO=(String)rtnCodeMap.get("PDAcountGlNO");
				pdAccontDetId=(BigDecimal)rtnCodeMap.get("PDAcountDetId");
			}
			

			Boolean sdCurrency=(Boolean)rtnCodeMap.get("SDCurrency");
			if(!sdCurrency.booleanValue())
			{
				setWarningMessage(handler.showWarningMessage("lbl.sdCurrency", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
				return checkStatus;
			}

			Boolean pdStndInstrn=(Boolean)rtnCodeMap.get("PDStndInstrn");
			if(!pdStndInstrn.booleanValue())
			{
				setWarningMessage(handler.showWarningMessage("lbl.pdStndInstrn", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
				
				return checkStatus;
			}
			if(pdStndInstrn)
			{
				BigDecimal pdStndInstrnId=(BigDecimal)rtnCodeMap.get("PDStndInstrnId");
				dealtrackerDt.setPdStandardId(pdStndInstrnId);
			}

			Boolean sdStndInstrn=(Boolean)rtnCodeMap.get("SDStndInstrn");
			if(!sdStndInstrn.booleanValue())
			{
				setWarningMessage(handler.showWarningMessage("lbl.sdStndInstrn", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
				return checkStatus;
			}

			if(sdStndInstrn)
			{
				BigDecimal sdStndInstrnId=(BigDecimal)rtnCodeMap.get("SDStndInstrnId");
				dealtrackerDt.setSdStandardId(sdStndInstrnId);
			}
			Boolean fundGlAcountNo=(Boolean)rtnCodeMap.get("FundGlAcountNo");
			if(fundGlAcountNo!=null && !fundGlAcountNo.booleanValue())
			{
				setWarningMessage(handler.showWarningMessage("lbl.fundGlAcountNo", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
				return checkStatus;
			}
			
			String sdFundGlAccNO=null;
			if(fundGlAcountNo!=null && fundGlAcountNo.booleanValue())
			{
				sdFundGlAccNO=(String)rtnCodeMap.get("SDGlAcountNo");
			}
			
			Boolean accBalance=(Boolean)rtnCodeMap.get("Balance");
			if(accBalance!=null && !accBalance.booleanValue())
			{
				setWarningMessage(handler.showWarningMessage("lbl.nobankbalance", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
				return checkStatus;
			}
			
			BigDecimal sdCurrencyId=(BigDecimal)rtnCodeMap.get("SDCurrencyID");
            if(sdCurrencyId!=null)
            {
                 dealtrackerDt.setSdCurrencyId(sdCurrencyId);
                           
            }
            
            BigDecimal pdCurrencyID=(BigDecimal)rtnCodeMap.get("PDCurrencyID");
			if(sdCurrencyId!=null)
			{
				dealtrackerDt.setPdCurrencyId(pdCurrencyID);
						
			}
            if(sdCurrencyId.compareTo(new BigDecimal(sessionStateManage.getCurrencyId()))!=0)
            {
                 Boolean lessBalance=(Boolean)rtnCodeMap.get("LessBalance");
                 if(lessBalance!=null && !lessBalance.booleanValue())
                 {
                      setWarningMessage(handler.showWarningMessage("lbl.thisLocalBankHaveInsufficientBankBalance", sessionStateManage.getLanguageId()));
                      RequestContext.getCurrentInstance().execute("warningDailogId.show();");
                       return checkStatus;
                 }
            }
						
			Boolean avgRate=(Boolean)rtnCodeMap.get("AvgRate");
			if(avgRate!=null && !avgRate.booleanValue())
			{
				setWarningMessage(handler.showWarningMessage("lbl.avgRate", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
				return checkStatus;
			}

			if(dealtrackerDt.getDealId()!=null)
			{
				HashMap<String, Object> retDelaMap = null;
				try {
					retDelaMap = fXDetailInformationService.ValidateDealID(dealtrackerDt.getDealId().trim(),getCurrentDate());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Boolean checkDealId=(Boolean)retDelaMap.get("DealID");

				if(checkDealId.booleanValue())
				{
					RequestContext.getCurrentInstance().execute("dealidexist.show();");
					return checkStatus;
				}
			}
			if(pdFundGlAccNO!=null)
			{
				dealtrackerDt.setPdFundGlNo(pdFundGlAccNO);
			}
			if(sdFundGlAccNO!=null)
			{
				dealtrackerDt.setSdFundGlNo(sdFundGlAccNO);
			}
			if(pdAccontDetId!=null)
			{
				dealtrackerDt.setPdAccountDetId(pdAccontDetId);
			}
			
			//fXDetailInfoBean.callFromDealTracker(dealtrackerDt,rtnCodeMap);
			lstDealTrackerDt.add(dealtrackerDt);
			checkStatus = true;


		}catch(Exception exception){
			setWarningMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
			return checkStatus;
		}
		
		return checkStatus;
	}
	
	// Main Save To Approve All Selected Records
	public void mainSaveApprove(){
		
		
		BigDecimal docRefNumber=getDocumentSerialIdNumber(Constants.Yes);
		if(docRefNumber.compareTo(BigDecimal.ZERO)==0)
		{
			return;
		}
		
		WarningHandler handler = new WarningHandler();
		setWarningMessage(null);

		// final save 
		if(lstDealTrackerDt.size() != 0){
			// User Financial Year 
			dealYearFromDB();
			// get document seriality number
			getDocumentSerialID(Constants.Yes);
			// company based on application
			getLstCompany();
			// Document number 
			getDocumentNumber();
			SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yy");
			boolean dealApprove=false;
			for (DealTrackerViewTicketDataTable lstDealTrackerRec : lstDealTrackerDt) {
				String sysdate=dateformat.format(getCurrentDate());
				
				try {
					Date date = dateformat.parse(sysdate);
					List<TreasuryDealHeader> lstTreasuryDealHeader= idealTrackerInfoService.getTreasuryHrdForDealTracker(date,lstDealTrackerRec.getDealId().trim());
					
					if(lstTreasuryDealHeader.size()==0)
					{
						dealApprove=true;
					}else
					{
						dealApprove=false;
						break;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if(!dealApprove)
			{
				
				RequestContext.getCurrentInstance().execute("alreadyApprove.show();");
				return;
			}else
			{
				// save Method
				saveTreasuryHeader();
			}
			
		}else{
			setWarningMessage(handler.showWarningMessage("lbl.atleastoneRecord", sessionStateManage.getLanguageId()));
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}

	}
	
	// fetching company Id based on Application Country
	public String getLstCompany() {
		List<CompanyMasterDesc> data=generalService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
		setCompany(data.get(0).getFsCompanyMaster().getCompanyId());
		return data.get(0).getCompanyName();
	}
	
	// Fetching Document number 
	public String getDocumentNumber() {
		lstDocument=generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		for(Document lstdoc:lstDocument)
		{
			setDocumentNo(lstdoc.getDocumentID());
			setDocument(lstdoc.getDocumentDesc());
		}
		return document;
	}
	
	// User Financial Year  Based on current Date
	public String dealYearFromDB() {

		try{
			dealYearList = generalService.getDealYear(new Date());
			if(dealYearList!=null){
				if(getUserDealYear()==null){
					dealYear = dealYearList.get(0).getFinancialYear().toString();
					dealYearId=dealYearList.get(0).getFinancialYearID();
					setDealYearId(dealYearId);
					setDealYear(dealYear);
				}
			}
		}catch(Exception e){
			e.printStackTrace();	
		}
		
		return dealYear;
	}
	
	// Current Date with the Format 
	public static String getCurrentDateWithFormat() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		for (int i = 0; i < 12; i++) {
			if(i<9){
				data.put(i, "0"+String.valueOf(i+1));
			} else {
				data.put(i, String.valueOf(i+1)); 
			}
		}
		String year = String.valueOf(new Date().getYear()).substring(1, 3);
		return "01/"+data.get(Calendar.getInstance().get(Calendar.MONTH))+"/"+year;
	}	
	
	// get document seriality number
	public String getDocumentSerialID(String processIn){
		String documentSerialID = generalService.getNextDocumentReferenceNumber(sessionStateManage.getCountryId().intValue() , sessionStateManage.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK) , new BigDecimal(dealYear).intValue(),processIn,sessionStateManage.getCountryBranchCode());
		return documentSerialID;
	}
	
	public BigDecimal getDocumentSerialIdNumber(String processIn) {
		/*String documentSerialIdNumber =igeneralService.getNextDocumentReferenceNumber(sessionStateManage.getCountryId().intValue() ,sessionStateManage.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK), finaceYear,processIn,sessionStateManage.getCountryBranchCode());//sessionStateManage.getCountryBranchCode()
		setDocumentSerialIdNumber(new BigDecimal(documentSerialIdNumber));*/
		String documentSerialId="0";
		 try {
				HashMap<String, String> outPutValues= generalService.getNextDocumentRefNumber(sessionStateManage.getCountryId().intValue() , sessionStateManage.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK) , new BigDecimal(dealYear).intValue(),processIn,sessionStateManage.getCountryBranchCode());
				
				//String proceErrorFlag=outPutValues.get("PROCE_ERORRFLAG");
				String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
				if(proceErrorMsg!=null)
				{
					setBooDocVisble(Boolean.TRUE);
					setErrmsg(proceErrorMsg);
					RequestContext.getCurrentInstance().execute("proceErr.show();");
					return BigDecimal.ZERO;
				}else if(outPutValues.get("DOCNO") !=null && new BigDecimal(outPutValues.get("DOCNO")).compareTo(BigDecimal.ZERO)==0){
					setBooDocVisble(Boolean.TRUE);
					RequestContext.getCurrentInstance().execute("docZero.show();");
					return BigDecimal.ZERO;
					
				}else
				{
					setBooDocVisble(Boolean.FALSE);
					documentSerialId=outPutValues.get("DOCNO");
				}
			} catch (NumberFormatException | AMGException e) {
				
				setBooDocVisble(Boolean.TRUE);
				setErrmsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
				return BigDecimal.ZERO;
			}
		
		
		return new BigDecimal(documentSerialId);
	}
	// fetch bank Id based on Bank Code
	public BigDecimal fetchBankMasterByBankCode(String bankCode){
		BigDecimal bankId = null;
		List<BankMaster> lstBankMaster = generalService.getAllBankCodeFromBankMaster(bankCode);
		if(lstBankMaster.size() != 0){
			BankMaster bankMaster = lstBankMaster.get(0);
			bankId = bankMaster.getBankId();
		}
		
		return bankId;
	}
	
	// fetch currency Id  based on Currency Quote Name Code
	public BigDecimal fetchCurrencyMasterByCurrencyQuoteCode(String currencyCode){
		BigDecimal currencyId = null;
		List<CurrencyMaster> lstCurrencyMaster = idealTrackerInfoService.getCurrencyNameandCurrencyIdBasedonQuoteName(currencyCode);
		if(lstCurrencyMaster.size() != 0){
			CurrencyMaster currencyMaster = lstCurrencyMaster.get(0);
			currencyId = currencyMaster.getCurrencyId();
		}
		
		return currencyId;
	}
	
	// fetching Account number Based on BankId and CurrencyId
	public void populateAccountNumber(BigDecimal bankId , BigDecimal currencyId) {
		setBankAccountNumber(null);
		setBankGLAccountNumber(null);
		//getAllBankCodeFromBankMaster - to fetch bank id and bank name from bank master
		List<BankAccountDetails> ptabledata = generalService.getAccountNumber(bankId, currencyId); // bank id and currency id

		if(ptabledata.size()==0)
		{
			setBankAccountNumber(null);
			setBankGLAccountNumber(null);
			RequestContext.getCurrentInstance().execute("dataNotFund.show();");
		}else if(ptabledata.size()>0){
			for(BankAccountDetails bankAcount :ptabledata)
			{
				setBankAccountNumber(bankAcount.getBankAcctNo());
				setBankGLAccountNumber(bankAcount.getFundGlno());
			}
		}else{
			setBankAccountNumber(null);
			setBankGLAccountNumber(null);
		}
	}
	
	// fetching Standard Instruction Based on BankId and CurrencyId
	public BigDecimal fetchStandardInstrnNumber(BigDecimal bankId , BigDecimal currencyId ,BigDecimal bankAccountDelId ,String instructionType){
		BigDecimal strndInstrnNum = null;
		List<StandardInstruction> standardInstruction = iStandardInstructionsService.getValues(sessionStateManage.getCompanyId(), bankId, currencyId, bankAccountDelId, instructionType);
		if(standardInstruction.size() == 0){
			strndInstrnNum = null;
			RequestContext.getCurrentInstance().execute("nointrndetails.show();");
		}else if(standardInstruction.size() == 1){
			for (StandardInstruction element : standardInstruction){
				//strndInstrnNum = element.getStandardInsructionNumber();
				strndInstrnNum = element.getStandardInstructionId();
			}
		}else{
			strndInstrnNum = null;
		}
		return strndInstrnNum;
	}
	
	// fetching Standard Instruction Based on BankId , CurrencyId , Is_Active and starndardInstrn Number  
	public void fetchStandardInstrnDetails(BigDecimal bankId , BigDecimal currencyId , BigDecimal strndInstrnNum, BigDecimal accountDetailId, String instructionType) {
		
		lststandardInstrnDetails.clear();

		List<StandardInstructionDetails> cinstrndetailsfromDB = fXDetailInformationService.getInstructionsFromDetails(bankId,currencyId,Constants.Yes,strndInstrnNum,accountDetailId,instructionType);

		if(cinstrndetailsfromDB.size() != 0){
			lststandardInstrnDetails.addAll(cinstrndetailsfromDB);
		}

	}
	
	public BigDecimal getSaleLocalAmountBySaleDetails(DealTrackerViewTicketDataTable lstDealTrackerRec){
		
		
		BigDecimal saleLocalAmt = null;
		
		// fetching bank id based on bank code
		BigDecimal bankId =idealTrackerInfoService.getBankMasterInfo(lstDealTrackerRec.getSdBankCode()).get(0).getBankId();
		//BigDecimal bankId = fetchBankMasterByBankCode(lstDealTrackerRec.getSdBankCode());
		if(bankId != null){
			setSaleBankId(bankId);
		}
		
		// fetching Currency id based on Currency code
		BigDecimal currencyId = fetchCurrencyMasterByCurrencyQuoteCode(lstDealTrackerRec.getSdCurrencyCode());
		if(currencyId != null){
			setSaleCurrencyId(currencyId);
		}
		
		System.out.println(" rrrrr getSaleBankId :"+getSaleBankId()+"\t getSaleCurrencyId :"+getSaleCurrencyId());
		
		populateAccountNumber(getSaleBankId() ,lstDealTrackerRec.getSdCurrencyId());
		
		String bankGlNumber=iStandardInstructionsService.getfundGlno(lstDealTrackerRec.getAccountDetId());
		System.out.println("bankGlNumber :"+bankGlNumber);
		setBankGLAccountNumber(bankGlNumber);
		System.out.println("getSaleBankId :"+getSaleBankId()+"\t getSaleCurrencyId :"+getSaleCurrencyId()+"\t getBankGLAccountNumber :"+getBankGLAccountNumber());
		if(getSaleBankId() != null && lstDealTrackerRec.getSdCurrencyId() != null && getBankGLAccountNumber() != null){
			List<AccountBalance> stabledata = fXDetailInformationService.getSaleAvgRate(getSaleBankId(), lstDealTrackerRec.getSdCurrencyId() , getBankGLAccountNumber());
			
			if(stabledata.size() != 0){
				for (AccountBalance accountBalance : stabledata) {
					
					System.out.println("RR accountBalance :"+accountBalance);
					if(accountBalance.getAverageRate()!=null)
					{
						setSaleAvgRate(accountBalance.getAverageRate());
					}
				}
			}
			
			saleLocalAmt = GetRound.roundBigDecimal((lstDealTrackerRec.getSaleAmount().multiply(getSaleAvgRate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
		}
		
		
		
		return saleLocalAmt;

	}
	
	// Save Ex_Treasury_Deal_Header Table
	public void saveTreasuryHeader(){
		boolean saveSucc=false;
		try{
			boolean dealExist=false;
			String dealId=null;
			
			boolean inSufficientBalance=false;
			for (DealTrackerViewTicketDataTable lstDealTrackerRec : lstDealTrackerDt) 
			{
				HashMap<String, Object> outPutValues=idealTrackerInfoService.ValidateDealIDWhileUpdate(lstDealTrackerRec.getDealId(), lstDealTrackerRec.getTimeofDeal());
				Boolean dealIdExist=(Boolean) outPutValues.get("DealID");
				if(dealIdExist!=null && dealIdExist.booleanValue())
				{
					dealExist=true;
					break;
				}
			}
			if(dealExist)
			{
				RequestContext.getCurrentInstance().execute("dealidexist.show();");
				return ;
				
			}else
			{
				
				
				for (DealTrackerViewTicketDataTable lstDealTrackerRec : lstDealTrackerDt)
				{
					String bankGlNumber=iStandardInstructionsService.getfundGlno(lstDealTrackerRec.getAccountDetId());
					
					List<AccountBalance> lstAccBalan = generalService.getBankBalance(bankGlNumber);
					
					for(AccountBalance accountBalance :lstAccBalan)
					{
						BigDecimal accountBal= accountBalance.getForeignBalance();
						if(accountBal== null)
						{
							accountBal=BigDecimal.ZERO;
						}
						if(lstDealTrackerRec.getSdCurrencyId().compareTo(new BigDecimal(sessionStateManage.getCurrencyId()))!=0)
						{
							if(lstDealTrackerRec.getSaleAmount().compareTo(accountBal) > 0){
								inSufficientBalance=true;
								break;
							}
						}
						
					}
				}

				if(inSufficientBalance)
				{
					RequestContext.getCurrentInstance().execute("greatersales.show();");
					return ;
				}
				
				for (DealTrackerViewTicketDataTable lstDealTrackerRec : lstDealTrackerDt) {

					
					
					int lineNumber = 1 ;

					inSufficientBalance=false;
					if(lstDealTrackerRec.getSdCurrencyId().compareTo(new BigDecimal(sessionStateManage.getCurrencyId()))!=0)
					{
						String bankGlNumber=iStandardInstructionsService.getfundGlno(lstDealTrackerRec.getAccountDetId());
						List<AccountBalance> lstAccBalan = generalService.getBankBalance(bankGlNumber);
						
						for(AccountBalance accountBalance :lstAccBalan)
						{
							BigDecimal accountBal= accountBalance.getForeignBalance();
							if(accountBal== null)
							{
								accountBal=BigDecimal.ZERO;
							}
							if(lstDealTrackerRec.getSaleAmount().compareTo(accountBal) > 0){
								inSufficientBalance=true;
								break;
							}
						}
						if(inSufficientBalance)
						{
							dealId=lstDealTrackerRec.getDealId();
							break;
						}
					}
					
					TreasuryDealHeader treasuryDealHeader = new TreasuryDealHeader();

					//treasuryDealHeader.setTreasuryDealHeaderId(getTeasuryDealHeaderId());

					// Save Application Country
					CountryMaster applicationCountry = new CountryMaster();
					applicationCountry.setCountryId(sessionStateManage.getCountryId());
					treasuryDealHeader.setFsCountryMaster(applicationCountry);

					// save Company Master
					CompanyMaster companyMaster = new CompanyMaster();
					companyMaster.setCompanyId(getCompany());
					treasuryDealHeader.setFsCompanyMaster(companyMaster);

					// save Document
					Document document = new Document();
					document.setDocumentID(getDocumentNo());
					treasuryDealHeader.setExDocument(document);

					// save Document Finanace year
					treasuryDealHeader.setUserFinanceYear(new BigDecimal(getDealYear()));

					// save Bank
					BankMaster bankMaster = new BankMaster();
					bankMaster.setBankId(idealTrackerInfoService.getBankMasterInfo(lstDealTrackerRec.getDealWith()).get(0).getBankId());
					treasuryDealHeader.setExBankMaster(bankMaster);

					// save Language Type
					LanguageType langType = new LanguageType();
					langType.setLanguageId(sessionStateManage.getLanguageId());
					treasuryDealHeader.setFsLanguageType(langType);

					treasuryDealHeader.setDocumentDate(lstDealTrackerRec.getTimeofDeal());
					treasuryDealHeader.setContactName(lstDealTrackerRec.getDealerName());
					treasuryDealHeader.setConcludedBy(lstDealTrackerRec.getConcludedBy());
					treasuryDealHeader.setReutersReference(lstDealTrackerRec.getDealId()); // Instead of reuters Ref saving Deal Id in Reuters - treasury Deal Header to get Unique
					treasuryDealHeader.setRemarks(lstDealTrackerRec.getCommentText());
					treasuryDealHeader.setAccyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
					
					if(lstDealTrackerRec.getEftval() != null ){
						treasuryDealHeader.setEftAmount(lstDealTrackerRec.getEftval());
					}
					
					if(lstDealTrackerRec.getTtVal() != null ){
						treasuryDealHeader.setTtAmount(lstDealTrackerRec.getTtVal());
					}
					
					if(lstDealTrackerRec.getCashVal() != null ){
						treasuryDealHeader.setCashAmount(lstDealTrackerRec.getCashVal());
					}

					treasuryDealHeader.setDealWithType(Constants.Fx_BankDealType);// By Default Bank "B" Hard coded - Nashish

					// Save Purchase Details
					treasuryDealHeader.setValueDate(lstDealTrackerRec.getPdValueDate());

					if(lstDealTrackerRec.getMultiFactor().compareTo(BigDecimal.ONE)==0)
					{
						setPurchaseMultipleDivision("1");
						BigDecimal totallocalAmount = GetRound.roundBigDecimal((lstDealTrackerRec.getPdFCAmount().multiply(lstDealTrackerRec.getPdExchangerate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
						treasuryDealHeader.setSaleAmount(totallocalAmount);//Total Sale Amount
					}else
					{
						setPurchaseMultipleDivision("2");
						int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()));
						BigDecimal totallocalAmount = GetRound.roundBigDecimal((lstDealTrackerRec.getPdFCAmount().divide(lstDealTrackerRec.getPdExchangerate(),decimalvalue,BigDecimal.ROUND_HALF_UP)),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
						treasuryDealHeader.setSaleAmount(totallocalAmount);//Total Sale Amount
					}
					treasuryDealHeader.setMultiplicationDivision(getPurchaseMultipleDivision());
					treasuryDealHeader.setPurchaseExchangeRate(lstDealTrackerRec.getPdExchangerate());

					// save purchase Requirement getPurchaseExchangeRate 
					treasuryDealHeader.setTotalPurchaseFCAmt(lstDealTrackerRec.getPdFCAmount()); // totalFcPurchaseAmount
					//getSaleLocalAmount().divide(getTotalFcPurchaseAmount())
					BigDecimal saleLocalAmt = getSaleLocalAmountBySaleDetails(lstDealTrackerRec);
					
					BigDecimal localExchangeRate = BigDecimal.ZERO ;
					if(saleLocalAmt != null){
						localExchangeRate = saleLocalAmt.divide(lstDealTrackerRec.getPdFCAmount(),Integer.parseInt(Constants.FX_DEAL_WITH_SUPPLIER_DEAL_DECIMAL_PLACES),BigDecimal.ROUND_HALF_UP);
						treasuryDealHeader.setPurchaseLocalRate(localExchangeRate); // localExchangeRate
					}
					
					
					BigDecimal totallocalAmount = GetRound.roundBigDecimal(lstDealTrackerRec.getPdFCAmount().multiply(localExchangeRate),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
					treasuryDealHeader.setTotalPurchaseLocalAmt(totallocalAmount); //totallocalAmount

					treasuryDealHeader.setCreatedBy(sessionStateManage.getUserName());
					treasuryDealHeader.setCreatedDate(new Date());
					//treasuryDealHeader.setIsActive(Constants.Yes);
					treasuryDealHeader.setIsActive(Constants.U);
					treasuryDealHeader.setReutersIndicator(Constants.Yes);
					
					treasuryDealHeader.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					// setting new document number
					saveDocumentSerialID= new BigDecimal(getDocumentSerialID(Constants.U));
					if(saveDocumentSerialID.compareTo(BigDecimal.ZERO)==0)
					{
						RequestContext.getCurrentInstance().execute("docZero.show();");
						return;
					}
					treasuryDealHeader.setTreasuryDocumentNumber(saveDocumentSerialID);
					
					HashMap<String,Object> mapAllDetailForSave = new HashMap<String,Object>();

					mapAllDetailForSave.put("TreasuryHeader", treasuryDealHeader);

					TreasuryDealDetail savePurchaseTDetails = saveTreasuryDetailsForPurchase(lstDealTrackerRec , treasuryDealHeader , lineNumber);
					 
					mapAllDetailForSave.put("PurchaseTreasuryDetails", savePurchaseTDetails);

					List<TreasuryStandardInstruction> lstPurchaseTSI = saveStandardInstrnForPurchase(treasuryDealHeader,savePurchaseTDetails,lstDealTrackerRec);
					mapAllDetailForSave.put("ListPurchaseTreasurySI", lstPurchaseTSI);

					
					if(lstDealTrackerRec.getHighValueNO()!=null && lstDealTrackerRec.getHighValueNO()!=null && lstDealTrackerRec.getHighValueAmount().compareTo(BigDecimal.ZERO)!=0)
					{
						lineNumber = lineNumber + 1 ;
						TreasuryDealDetail treasuryDealDetailsForSP =  saveSpecialCustomerDetails(treasuryDealHeader, lstDealTrackerRec, lineNumber);
						mapAllDetailForSave.put("SPTreasuryDealDetails", treasuryDealDetailsForSP);
						List<TreasuryStandardInstruction> lstTreasuryStandardInstruction=saveStandardInstrnForSPPurchase(treasuryDealHeader, savePurchaseTDetails);
						mapAllDetailForSave.put("SPTreasuryStdIns", lstTreasuryStandardInstruction);
					}
					lineNumber = lineNumber + 1 ; 
					TreasuryDealDetail saveSaleTDetails = saveTreasuryDetailsForSale(lstDealTrackerRec , treasuryDealHeader , lineNumber);
					mapAllDetailForSave.put("SaleTreasuryDetails", saveSaleTDetails);

					List<TreasuryStandardInstruction> lstSaleTSI = saveStandardInstrnForSale(treasuryDealHeader,saveSaleTDetails,lstDealTrackerRec);
					mapAllDetailForSave.put("ListSaleTreasurySI", lstSaleTSI);

					
					
					idealTrackerInfoService.saveAllFXDealBank(mapAllDetailForSave);

					// COMMENTED AS PER NEW LOGIC FOR BULK APPROVAL  -- RM & JF
					//fXDetailInformationService.saveUnApprovedGLEntry(treasuryDealHeader);
			}
			

			}
			
			if(!inSufficientBalance)
			{			
				RequestContext.getCurrentInstance().execute("complete.show();");
				lstDealTrackerDt.clear();
			}else
			{
				/*WarningHandler handler = new WarningHandler();
				setWarningMessage(null);
				setWarningMessage(handler.showWarningMessage("lbl.selectedBankHaveInsufficientBankBalance", sessionStateManage.getLanguageId()));*/
				RequestContext.getCurrentInstance().execute("InsufficientBalance.show();");
								
			}

		}catch(AMGException e){
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
			
		} catch (Exception e) {
			
			if(e.getMessage()!=null && e.getMessage().equalsIgnoreCase("Approved"))
			{
				RequestContext.getCurrentInstance().execute("alreadyApprove.show();");
				return;
			}else if (e.getMessage()!=null && e.getMessage().equalsIgnoreCase("Duplicate")){
				
				RequestContext.getCurrentInstance().execute("duplicateApprove.show();");
				return;
			}else if (e.getMessage()!=null && e.getMessage().equalsIgnoreCase("OtherException")){
				
				RequestContext.getCurrentInstance().execute("otherExceptionApprove.show();");
				return;
			}else {
				
				setWarningMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("warningDailogId.show();");
			}
			
			
		}
	}
	
	// Save Ex_Treasury_Deal_Details Table for Purchase Details
	public TreasuryDealDetail saveTreasuryDetailsForPurchase(DealTrackerViewTicketDataTable lstDealTrackerRec , TreasuryDealHeader treasuryDealHeader , int lineNumber){
		
		TreasuryDealDetail treasuryDealDetail = new TreasuryDealDetail();
		
		try{

			// Save PK
			//treasuryDealDetail.setTreasuryDealDetailId(getPurchaseDealDetId());

			// Master Pk
			//treasuryDealHeader.setTreasuryDealHeaderId(treasuryDealHeader.getTreasuryDealHeaderId());
			treasuryDealDetail.setTreasuryDealHeader(treasuryDealHeader);
			
			// fetching bank id based on bank code
			BigDecimal bankId =idealTrackerInfoService.getBankMasterInfo(lstDealTrackerRec.getPdBankCode()).get(0).getBankId();
			//BigDecimal bankId = fetchBankMasterByBankCode(lstDealTrackerRec.getPdBankCode());
			if(bankId != null){
				setPurchaseBankId(bankId);
			}
			
			// save Bank
			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(getPurchaseBankId());//getPurchaseBank()
			treasuryDealDetail.setTreasuryDealBankMaster(bankMaster);
			
			// fetching Currency id based on Currency code
			BigDecimal currencyId = fetchCurrencyMasterByCurrencyQuoteCode(lstDealTrackerRec.getPdCurrencyCode());
			if(currencyId != null){
				setPurchaseCurrencyId(currencyId);
			}
			
			// save Currency
			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(lstDealTrackerRec.getPdCurrencyId());
			treasuryDealDetail.setTreasuryDealDetailCurrencyMaster(currencyMaster);
			
			// fetching Account Number and GL Account Number based on Bank Id and Currency Id
			System.out.println("getPurchaseBankId :"+getPurchaseBankId()+"\t getPurchaseCurrencyId :"+getPurchaseCurrencyId());
			populateAccountNumber(getPurchaseBankId() , lstDealTrackerRec.getPdCurrencyId());
			
			if(lstDealTrackerRec.getPdFundGlNo() != null ){
				//purchase Account Number
				BankAccountDetails bankAccountDetails = new BankAccountDetails();
				BigDecimal bankaccountDeatailsId= bankTransferService.getBankAccountDeatilsPk(lstDealTrackerRec.getPdFundGlNo());
				if(bankaccountDeatailsId!=null){
				bankAccountDetails.setBankAcctDetId(bankaccountDeatailsId);
				}
				treasuryDealDetail.setTreasuryDealDetailBankAccountDetails(bankAccountDetails);
				
				AccountBalance accBal = new AccountBalance();
				//BigDecimal accountBalanceId = bankTransferService.getAccountBalancePk(getBankGLAccountNumber().toPlainString());
				BigDecimal accountBalanceId = bankTransferService.getAccountBalancePk(lstDealTrackerRec.getPdFundGlNo());
				if(accountBalanceId!=null){
				accBal.setAccountId(accountBalanceId);
				}
				treasuryDealDetail.setAccountBalance(accBal);
				
				treasuryDealDetail.setFaAccountNo(lstDealTrackerRec.getPdFundGlNo());
			}

			// application country id
			CountryMaster applicationCountry = new CountryMaster();
			applicationCountry.setCountryId(sessionStateManage.getCountryId());
			treasuryDealDetail.setTreasuryDealCountryMaster(applicationCountry);
			
			// company id
			CompanyMaster companyMasters = new CompanyMaster();
			companyMasters.setCompanyId(sessionStateManage.getCompanyId());
			treasuryDealDetail.setTreasuryDealCompanyMaster(companyMasters);

			treasuryDealDetail.setValueDate(lstDealTrackerRec.getPdValueDate());
			treasuryDealDetail.setMultiplicationDivision(getPurchaseMultipleDivision());
			treasuryDealDetail.setTreasuryDealUserFinanceYear(new BigDecimal(getDealYear()));
			treasuryDealDetail.setDocumentNumber(treasuryDealHeader.getTreasuryDocumentNumber());
			//treasuryDealDetail.setIsActive(Constants.Yes);
			treasuryDealDetail.setIsActive(Constants.U);
			treasuryDealDetail.setLineNumber(new BigDecimal(lineNumber));// Hard coded
			treasuryDealDetail.setLocalExchangeRate(treasuryDealHeader.getPurchaseLocalRate());//getLocalExchangeRate()
			treasuryDealDetail.setExchange(treasuryDealHeader.getPurchaseExchangeRate());//getPurchaseExchangeRate()
			treasuryDealDetail.setTreasuryDealDocument(treasuryDealHeader.getExDocument());
			
			if(treasuryDealHeader.getTotalPurchaseFCAmt() != null){
				
				BigDecimal purchaseCommonPoolAmt= BigDecimal.ZERO;
				if(lstDealTrackerRec.getHighValueYear()!=null && lstDealTrackerRec.getHighValueYear()!=null)
				{
					BigDecimal spPoolAmt=lstDealTrackerRec.getHighValueAmount();
					if(spPoolAmt==null)
					{
						spPoolAmt=BigDecimal.ZERO;
					}
					BigDecimal localPurchaseCommonPoolAmt= treasuryDealHeader.getTotalPurchaseFCAmt();
					
					if(localPurchaseCommonPoolAmt==null)
					{
						localPurchaseCommonPoolAmt=BigDecimal.ZERO;
					}
					purchaseCommonPoolAmt=localPurchaseCommonPoolAmt.subtract(spPoolAmt);
					
				}else{
					purchaseCommonPoolAmt=treasuryDealHeader.getTotalPurchaseFCAmt();
				}
				
				BigDecimal fcAmountWithDecimal= GetRound.roundBigDecimal(purchaseCommonPoolAmt,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(lstDealTrackerRec.getPdCurrencyId()));
				
				// save purchase Requirement for Common Pool
				treasuryDealDetail.setFcAmount(fcAmountWithDecimal);//getFcAmount()getSaleCurrencyId()
				//getFcAmount().multiply(getLocalExchangeRate());
				BigDecimal localAmountWithDecimal= GetRound.roundBigDecimal((purchaseCommonPoolAmt.multiply(treasuryDealHeader.getPurchaseLocalRate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
				treasuryDealDetail.setLocalAmount(localAmountWithDecimal);
				
				BigDecimal lcalDecimalSaleAmt=GetRound.roundBigDecimal(treasuryDealHeader.getSaleAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(lstDealTrackerRec.getPdCurrencyId()));
				treasuryDealDetail.setSaleAmount(lcalDecimalSaleAmt);//getFcSaleAmount()
			}

			// to fetch standard instruction
			//BigDecimal standardInstrnNum = fetchStandardInstrnNumber(getPurchaseBankId(), getPurchaseCurrencyId());
			if (lstDealTrackerRec.getPdStandardId() != null) {
				setPurchaseInstrunction(lstDealTrackerRec.getPdStandardId());
				treasuryDealDetail.setStandardInstruction(lstDealTrackerRec.getPdStandardId());
			}
			treasuryDealDetail.setLineType(Constants.PD);
			treasuryDealDetail.setCreatedBy(sessionStateManage.getUserName());
			treasuryDealDetail.setCreatedDate(new Date());

		}catch(Exception e){
			log.error(e.getMessage());
		}

		return treasuryDealDetail;
	}
	
	public List<TreasuryStandardInstruction> saveStandardInstrnForPurchase(TreasuryDealHeader treasuryDealHeader,TreasuryDealDetail treasuryDealDetail,DealTrackerViewTicketDataTable lstDealTrackerRec){
		
		List<TreasuryStandardInstruction> lstTreasurySI = new ArrayList<TreasuryStandardInstruction>();
		
		//CR Adding Instruction Number and Description into TreasuryStandardInstruction for Purchase
		
		fetchStandardInstrnDetails(treasuryDealDetail.getTreasuryDealBankMaster().getBankId() , treasuryDealDetail.getTreasuryDealDetailCurrencyMaster().getCurrencyId(), lstDealTrackerRec.getPdStandardId(),lstDealTrackerRec.getPdAccountDetId(),"PD");
		
		int lineNumber = 1;

		for (StandardInstructionDetails stndInstrnDetails : lststandardInstrnDetails) {

			TreasuryStandardInstruction stndInstrnforDataTable = new TreasuryStandardInstruction();
			
			stndInstrnforDataTable.setTreasuryDealDetail(treasuryDealDetail);

			// Save Application Country
			CountryMaster applicationCountry = new CountryMaster();
			applicationCountry.setCountryId(sessionStateManage.getCountryId());
			stndInstrnforDataTable.setTreasuryCountryMaster(applicationCountry);

			// save Company Master
			CompanyMaster companyMasters = new CompanyMaster();
			companyMasters.setCompanyId(sessionStateManage.getCompanyId());
			stndInstrnforDataTable.setTreasurycomCompanyMaster(companyMasters);

			stndInstrnforDataTable.setStandardInstructionNumber(treasuryDealDetail.getStandardInstruction());

			// save Document
			stndInstrnforDataTable.setTreasurydocDocument(treasuryDealHeader.getExDocument());

			// save Document Finanace year
			stndInstrnforDataTable.setTreasDocumentFinancialYear(new BigDecimal(getDealYear()));
			stndInstrnforDataTable.setDucumentNumber(treasuryDealHeader.getTreasuryDocumentNumber());
			stndInstrnforDataTable.setLineType(Constants.PD); // Hard Coded

			// save Standard instruction
			stndInstrnforDataTable.setMessageLineNumber(new BigDecimal(lineNumber++));
			stndInstrnforDataTable.setMessageDescription(stndInstrnDetails.getLineDescription());
			//stndInstrnforDataTable.setIsActive(Constants.Yes); // Hard Coded
			stndInstrnforDataTable.setIsActive(Constants.U); // Hard Coded

			// save Language Type
			stndInstrnforDataTable.setTreasuryLanguageType(treasuryDealHeader.getFsLanguageType());
			stndInstrnforDataTable.setCreatedBy(sessionStateManage.getUserName());
			stndInstrnforDataTable.setCreatedDate(new Date());

			lstTreasurySI.add(stndInstrnforDataTable);

		}
		
		return lstTreasurySI;
	}
	
	
	public TreasuryDealDetail saveTreasuryDetailsForSale(DealTrackerViewTicketDataTable lstDealTrackerRec , TreasuryDealHeader treasuryDealHeader , int lineNumber){
		
		TreasuryDealDetail treasuryDealDetsale = new TreasuryDealDetail();
		
		try{

			//treasuryDealDetsale.setTreasuryDealDetailId(getSaleDealDetId());
			// header id
			treasuryDealDetsale.setTreasuryDealHeader(treasuryDealHeader);
			
			// fetching bank id based on bank code
			BigDecimal bankId =idealTrackerInfoService.getBankMasterInfo(lstDealTrackerRec.getSdBankCode()).get(0).getBankId();
			//BigDecimal bankId = fetchBankMasterByBankCode(lstDealTrackerRec.getSdBankCode());a
			if(bankId != null){
				setSaleBankId(bankId);
			}

			// save Bank
			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(getSaleBankId());//getSaleBank()
			treasuryDealDetsale.setTreasuryDealBankMaster(bankMaster);
			
			// fetching Currency id based on Currency code
			BigDecimal currencyId = fetchCurrencyMasterByCurrencyQuoteCode(lstDealTrackerRec.getSdCurrencyCode());
			if(currencyId != null){
				setSaleCurrencyId(currencyId);
			}

			// save Currency
			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(lstDealTrackerRec.getSdCurrencyId());//getSaleCurrency()
			treasuryDealDetsale.setTreasuryDealDetailCurrencyMaster(currencyMaster);

			// Save Application Country
			CountryMaster applicationCountry = new CountryMaster();
			applicationCountry.setCountryId(sessionStateManage.getCountryId());
			treasuryDealDetsale.setTreasuryDealCountryMaster(applicationCountry);

			System.out.println("getSaleBankId :"+getSaleBankId()+"\t getSaleCurrencyId :"+getSaleCurrencyId());
			populateAccountNumber(getSaleBankId() , lstDealTrackerRec.getSdCurrencyId());

			if(lstDealTrackerRec.getSdFundGlNo() != null ){
				// Save Account Number
				String bankGlNumber=iStandardInstructionsService.getfundGlno(lstDealTrackerRec.getAccountDetId());
				System.out.println("bankGlNumber :"+bankGlNumber);
				//setBankGLAccountNumber(bankGlNumber);
				
				
				BankAccountDetails bankAccountDetails = new BankAccountDetails();
				/*BigDecimal bankaccountDeatailsId = bankTransferService.getBankAccountDeatilsPk(lstDealTrackerRec.getSdFundGlNo());
				if(bankaccountDeatailsId!=null){
					bankAccountDetails.setBankAcctDetId(bankaccountDeatailsId);
				}*/
				bankAccountDetails.setBankAcctDetId(lstDealTrackerRec.getAccountDetId());
				treasuryDealDetsale.setTreasuryDealDetailBankAccountDetails(bankAccountDetails);

				AccountBalance accBal = new AccountBalance();
				BigDecimal accountBalanceId = bankTransferService.getAccountBalancePk(bankGlNumber);
				if(accountBalanceId!=null){
					accBal.setAccountId(accountBalanceId);
				}
				treasuryDealDetsale.setAccountBalance(accBal);

				treasuryDealDetsale.setFaAccountNo(bankGlNumber);

			}

			treasuryDealDetsale.setMultiplicationDivision(getPurchaseMultipleDivision());
			treasuryDealDetsale.setDocumentNumber(treasuryDealHeader.getTreasuryDocumentNumber());
			treasuryDealDetsale.setTreasuryDealUserFinanceYear(new BigDecimal(getDealYear()));

			// to fetch standard instruction
			//BigDecimal standardInstrnNum = fetchStandardInstrnNumber(getSaleBankId(), getSaleCurrencyId());
			if (lstDealTrackerRec.getSdStandardId() != null) {
				setSaleInstrunction(lstDealTrackerRec.getSdStandardId());
				treasuryDealDetsale.setStandardInstruction(getSaleInstrunction());
			}
			
			treasuryDealDetsale.setLineType(Constants.SD);

			// company id
			CompanyMaster compMaster = new CompanyMaster();
			compMaster.setCompanyId(sessionStateManage.getCompanyId());
			treasuryDealDetsale.setTreasuryDealCompanyMaster(compMaster);

			Document doc = new Document();
			doc.setDocumentID(getDocumentNo());
			treasuryDealDetsale.setTreasuryDealDocument(doc);

			System.out.println("saveTreasuryDetailsForSale   getSaleBankId() :"+getSaleBankId()+"\t getSaleCurrencyId :"+getSaleCurrencyId()+"\t getBankGLAccountNumber :"+getBankGLAccountNumber());
			List<AccountBalance> stabledata = fXDetailInformationService.getSaleAvgRate(getSaleBankId(),lstDealTrackerRec.getSdCurrencyId() , treasuryDealDetsale.getFaAccountNo());
			if(stabledata.size() != 0){
				for (AccountBalance accountBalance : stabledata) {
					if(accountBalance.getAverageRate()!=null)
					{
						setSaleAvgRate(accountBalance.getAverageRate());
					}
				}
			}

			treasuryDealDetsale.setLineNumber(new BigDecimal(lineNumber));
			treasuryDealDetsale.setLocalExchangeRate(getSaleAvgRate());
			treasuryDealDetsale.setValueDate(lstDealTrackerRec.getSdValueDate());
			setSaleLocalAmount(GetRound.roundBigDecimal((lstDealTrackerRec.getSaleAmount().multiply(getSaleAvgRate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			treasuryDealDetsale.setLocalAmount(getSaleLocalAmount());//saleLocalAmount
			
			BigDecimal saleFcAmtWithDecimal= GetRound.roundBigDecimal(lstDealTrackerRec.getSaleAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(lstDealTrackerRec.getSdCurrencyId()));
			
			treasuryDealDetsale.setFcAmount(saleFcAmtWithDecimal);//saleAmount
			treasuryDealDetsale.setAvgRate(getSaleAvgRate());//saleAvgRate
			//treasuryDealDetsale.setIsActive(Constants.Yes);
			treasuryDealDetsale.setIsActive(Constants.U);
			treasuryDealDetsale.setSaleAmount(GetRound.roundBigDecimal(lstDealTrackerRec.getSaleAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(lstDealTrackerRec.getSdCurrencyId())));////saleAmount*** Ram
			treasuryDealDetsale.setCreatedBy(sessionStateManage.getUserName());
			treasuryDealDetsale.setCreatedDate(new Date());

		}catch(Exception e){
			log.error(e.getMessage());
		}
		return treasuryDealDetsale;
	}
	
	public List<TreasuryStandardInstruction> saveStandardInstrnForSale(TreasuryDealHeader treasuryDealHeader,TreasuryDealDetail treasuryDealDetsale,DealTrackerViewTicketDataTable lstDealTrackerRec){
		
		List<TreasuryStandardInstruction> lstStndInstrnSale = new ArrayList<TreasuryStandardInstruction>();
		
		//CR Adding Instruction Number and Description into TreasuryStandardInstruction for Purchase
		
		fetchStandardInstrnDetails(treasuryDealDetsale.getTreasuryDealBankMaster().getBankId() , treasuryDealDetsale.getTreasuryDealDetailCurrencyMaster().getCurrencyId(), lstDealTrackerRec.getSdStandardId(),lstDealTrackerRec.getAccountDetId(),"SD");
		
		int lineNumberSale = 1;

		for (StandardInstructionDetails stndInstrnDetails : lststandardInstrnDetails) {

			TreasuryStandardInstruction stndInstrnforDataTableforSale = new TreasuryStandardInstruction();

			stndInstrnforDataTableforSale.setTreasuryDealDetail(treasuryDealDetsale);

			// Save Application Country
			CountryMaster applicationCountry = new CountryMaster();
			applicationCountry.setCountryId(sessionStateManage.getCountryId());
			stndInstrnforDataTableforSale.setTreasuryCountryMaster(applicationCountry);

			// save Company Master
			CompanyMaster companyMasters = new CompanyMaster();
			companyMasters.setCompanyId(sessionStateManage.getCompanyId());
			stndInstrnforDataTableforSale.setTreasurycomCompanyMaster(companyMasters);
			
			stndInstrnforDataTableforSale.setStandardInstructionNumber(treasuryDealDetsale.getStandardInstruction());

			// save Document
			stndInstrnforDataTableforSale.setTreasurydocDocument(treasuryDealHeader.getExDocument());

			// save Document Finance year
			stndInstrnforDataTableforSale.setTreasDocumentFinancialYear(new BigDecimal(getDealYear()));
			stndInstrnforDataTableforSale.setDucumentNumber(treasuryDealHeader.getTreasuryDocumentNumber());
			stndInstrnforDataTableforSale.setLineType(Constants.SD); // Hard Coded

			// save Standard instruction
			stndInstrnforDataTableforSale.setMessageLineNumber(new BigDecimal(lineNumberSale++));
			stndInstrnforDataTableforSale.setMessageDescription(stndInstrnDetails.getLineDescription());
			//stndInstrnforDataTableforSale.setIsActive(Constants.Yes); // Hard Coded
			stndInstrnforDataTableforSale.setIsActive(Constants.U); // Hard Coded

			// save Language Type
			stndInstrnforDataTableforSale.setTreasuryLanguageType(treasuryDealHeader.getFsLanguageType());
			stndInstrnforDataTableforSale.setCreatedBy(sessionStateManage.getUserName());
			stndInstrnforDataTableforSale.setCreatedDate(new Date());

			lstStndInstrnSale.add(stndInstrnforDataTableforSale);

		}

		return lstStndInstrnSale;
	}
	
	public TreasuryDealDetail saveSpecialCustomerDetails(TreasuryDealHeader treasuryDealHeader,DealTrackerViewTicketDataTable dealTrackerViewTicketDataTable , int lineNumber){
		
		
		TreasuryDealDetail treasuryDealDetails = new TreasuryDealDetail();
		
		treasuryDealDetails.setTreasuryDealHeader(treasuryDealHeader);

		CustomerSpecialDealRequest custSplDealReq = new CustomerSpecialDealRequest();
		custSplDealReq.setCustomerSpecialDealReqId(dealTrackerViewTicketDataTable.getSpecialDealCustRequestId());
		treasuryDealDetails.setCustomerSpecialDealRequest(custSplDealReq);

		// custSplDealReq.setCustomerSpeacialDealReqCustomer(purchaseSplPool.getCustomerId().toPlainString());
		// save customer Id

		//customer id is replaced with First name @ chiru
		treasuryDealDetails.setCustomerReference(dealTrackerViewTicketDataTable.getCustomerReference());
		// save Doc
		treasuryDealDetails.setSpecialRequestDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_SPL_CUSTOMER_DEAL_REQUEST));
		treasuryDealDetails.setSpecialRequestFinanceYear(dealTrackerViewTicketDataTable.getHighValueYear());
		treasuryDealDetails.setDocumentNumber(treasuryDealHeader.getTreasuryDocumentNumber());
		treasuryDealDetails.setTreasuryDealUserFinanceYear(new BigDecimal(getDealYear()));
		treasuryDealDetails.setStandardInstruction(getPurchaseInstrunction());

		// fetching bank id based on bank code
		BigDecimal bankId =idealTrackerInfoService.getBankMasterInfo(dealTrackerViewTicketDataTable.getPdBankCode()).get(0).getBankId();
		//BigDecimal bankId = fetchBankMasterByBankCode(lstDealTrackerRec.getPdBankCode());
		if(bankId != null){
			setPurchaseBankId(bankId);
		}
					
		BankMaster bankMaster = new BankMaster();
		bankMaster.setBankId(getPurchaseBankId());
		treasuryDealDetails.setTreasuryDealBankMaster(bankMaster);

		
		// fetching Currency id based on Currency code
		BigDecimal currencyId = fetchCurrencyMasterByCurrencyQuoteCode(dealTrackerViewTicketDataTable.getPdCurrencyCode());
		if(currencyId != null){
			setPurchaseCurrencyId(currencyId);
		}
					
		CurrencyMaster currencyMaster = new CurrencyMaster();
		currencyMaster.setCurrencyId(dealTrackerViewTicketDataTable.getPdCurrencyId());
		treasuryDealDetails.setTreasuryDealDetailCurrencyMaster(currencyMaster);
		
		BankAccountDetails bankAccountDetails = new BankAccountDetails();
		BigDecimal bankaccountDeatailsId= bankTransferService.getBankAccountDeatilsPk(dealTrackerViewTicketDataTable.getPdFundGlNo());
		if(bankaccountDeatailsId!=null){
		bankAccountDetails.setBankAcctDetId(bankaccountDeatailsId);
		}
		treasuryDealDetails.setTreasuryDealDetailBankAccountDetails(bankAccountDetails);

		//BankAccountDetails bankAccountDetails = new BankAccountDetails();
		//bankAccountDetails.setBankAcctDetId(getPurchaseAccountNumber());
		//treasuryDealDetails.setTreasuryDealDetailBankAccountDetails(bankAccountDetails);

		treasuryDealDetails.setValueDate(dealTrackerViewTicketDataTable.getPdValueDate());

		treasuryDealDetails.setMultiplicationDivision(getPurchaseMultipleDivision());

		// save company
		//treasuryDealDetails.setSpecialRequestCompanyId(purchaseSplPool.getSplReqCompanyId());

		treasuryDealDetails.setSpecialRequestCompanyId(sessionStateManage.getCompanyId());
		
		// save SP Customer Company ID
		CompanyMaster companyMast1 = new CompanyMaster();
		companyMast1.setCompanyId(sessionStateManage.getCompanyId());
		treasuryDealDetails.setTreasuryDealCompanyMaster(companyMast1);


		BigDecimal fcAmountWithDecimal= GetRound.roundBigDecimal(dealTrackerViewTicketDataTable.getHighValueAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(dealTrackerViewTicketDataTable.getPdCurrencyId()));
		
		treasuryDealDetails.setFcAmount(fcAmountWithDecimal);
		BigDecimal localAmountWithDecimal= GetRound.roundBigDecimal((dealTrackerViewTicketDataTable.getHighValueAmount().multiply(dealTrackerViewTicketDataTable.getPdExchangerate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
		
		treasuryDealDetails.setLocalAmount(localAmountWithDecimal);
		//treasuryDealDetails.setLocalAmount(purchaseSplPool.getForeignCurrencyAmount().multiply(getLocalExchangeRate()));
		treasuryDealDetails.setLineType(Constants.PD);
		//treasuryDealDetails.setFaAccountNo(purchaseSplPool.getFaAccountNo());
		treasuryDealDetails.setFaAccountNo(getBankGLAccountNumber());

		CountryMaster applicationCountry1 = new CountryMaster();
		applicationCountry1.setCountryId(sessionStateManage.getCountryId());
		treasuryDealDetails.setTreasuryDealCountryMaster(applicationCountry1);

		treasuryDealDetails.setIsActive(Constants.U);
		treasuryDealDetails.setLineNumber(new BigDecimal(lineNumber));

		Document doc1 = new Document();
		doc1.setDocumentID(getDocumentNo());
		treasuryDealDetails.setTreasuryDealDocument(doc1);

		treasuryDealDetails.setSpecialRequestDocNumber(dealTrackerViewTicketDataTable.getHighValueNO());

		treasuryDealDetails.setLocalExchangeRate(treasuryDealHeader.getPurchaseLocalRate());//localExchangeRate
		treasuryDealDetails.setExchange(treasuryDealHeader.getPurchaseExchangeRate());
		//treasuryDealDetails.setTreasuryDealDetailId(purchaseSplPool.getSplPoolPurchaseDetId());
		
		//purchaseReqSplPoolDataTable.setLocalAmount(round((customerSpecialDREQ.getForeignCurrencyAmount().multiply(getPurchaseExchangeRate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionManage.getCurrencyId()))));
		
		if(dealTrackerViewTicketDataTable.getMultiFactor().compareTo(BigDecimal.ONE)==0)
		{
			setPurchaseMultipleDivision("1");
			BigDecimal totallocalAmount = GetRound.roundBigDecimal((dealTrackerViewTicketDataTable.getHighValueAmount().multiply(dealTrackerViewTicketDataTable.getPdExchangerate())),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(dealTrackerViewTicketDataTable.getPdCurrencyId()));
			treasuryDealDetails.setSaleAmount(totallocalAmount);//Total Sale Amount
		}else
		{
			setPurchaseMultipleDivision("2");
			BigDecimal totallocalAmountLocal= dealTrackerViewTicketDataTable.getHighValueAmount().divide(dealTrackerViewTicketDataTable.getPdExchangerate(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(dealTrackerViewTicketDataTable.getPdCurrencyId()),BigDecimal.ROUND_HALF_UP);
			BigDecimal totallocalAmount = GetRound.roundBigDecimal((totallocalAmountLocal),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(dealTrackerViewTicketDataTable.getPdCurrencyId()));
			treasuryDealDetails.setSaleAmount(totallocalAmount);//Total Sale Amount
		}
		
		
		treasuryDealDetails.setCreatedBy(sessionStateManage.getUserName());
		treasuryDealDetails.setCreatedDate(new Date());
		
		
		return treasuryDealDetails;
		
	}
	
	public List<StandardInstructionDetails>  fetchStandardInstrnDetails1(BigDecimal bankId , BigDecimal currencyId , BigDecimal strndInstrnNum, BigDecimal accountDetailId, String instructionType) {
		
		
		List<StandardInstructionDetails> cinstrndetailsfromDB = fXDetailInformationService.getInstructionsFromDetails(bankId,currencyId,Constants.Yes,strndInstrnNum,accountDetailId,instructionType);

		return cinstrndetailsfromDB;
	}

	public List<TreasuryStandardInstruction> saveStandardInstrnForSPPurchase(TreasuryDealHeader treasuryDealHeader,TreasuryDealDetail treasuryDealDetail){
		
		List<TreasuryStandardInstruction> lstTreasurySI = new ArrayList<TreasuryStandardInstruction>();
		List<StandardInstructionDetails> cinstrndetailsfromDB=fetchStandardInstrnDetails1(treasuryDealDetail.getTreasuryDealBankMaster().getBankId(),treasuryDealDetail.getTreasuryDealDetailCurrencyMaster().getCurrencyId(),getPurchaseInstrunction(),treasuryDealDetail.getTreasuryDealDetailBankAccountDetails().getBankAcctDetId(),Constants.PD);
		
		//CR Adding Instruction Number and Description into TreasuryStandardInstruction for Purchase
		
		int lineNumber = 1;

		for (StandardInstructionDetails stndInstrnDetails : cinstrndetailsfromDB) {
			//if (purchaseCheckbox) {
				
				TreasuryStandardInstruction stndInstrnforDataTable = new TreasuryStandardInstruction();
				

				// Save Application Country
				CountryMaster applicationCountry = new CountryMaster();
				applicationCountry.setCountryId(sessionStateManage.getCountryId());
				stndInstrnforDataTable.setTreasuryCountryMaster(applicationCountry);

				// save Company Master
				CompanyMaster companyMasters = new CompanyMaster();
				companyMasters.setCompanyId(sessionStateManage.getCompanyId());
				stndInstrnforDataTable.setTreasurycomCompanyMaster(companyMasters);
				
				stndInstrnforDataTable.setStandardInstructionNumber(getPurchaseInstrunction());
				
				// save Document
				stndInstrnforDataTable.setTreasurydocDocument(treasuryDealHeader.getExDocument());
				
				// save Document Finanace year
				stndInstrnforDataTable.setTreasDocumentFinancialYear(new BigDecimal(getDealYear()));
				
				stndInstrnforDataTable.setDucumentNumber(treasuryDealHeader.getTreasuryDocumentNumber());
				
				stndInstrnforDataTable.setLineType(Constants.PD); // Hard Coded
				
				// save Standard instruction

				stndInstrnforDataTable.setMessageLineNumber(new BigDecimal(lineNumber++));
				stndInstrnforDataTable.setMessageDescription(stndInstrnDetails.getLineDescription());
				
				stndInstrnforDataTable.setIsActive(Constants.U); // Hard Coded
				
				// save Language Type
				stndInstrnforDataTable.setTreasuryLanguageType(treasuryDealHeader.getFsLanguageType());
				stndInstrnforDataTable.setCreatedBy(sessionStateManage.getUserName());
				stndInstrnforDataTable.setCreatedDate(new Date());
				stndInstrnforDataTable.setTreasuryDealDetail(treasuryDealDetail);
				
				lstTreasurySI.add(stndInstrnforDataTable);

			//}

		}
		
		return lstTreasurySI;
	}

	// Exit
	public void exit() {
        if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}else{
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}


}
