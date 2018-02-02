package com.amg.exchange.foreigncurrency.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.amg.exchange.aop.FcSaleReport;
import com.amg.exchange.bean.LoginBean;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.RoleWiseCurrencyLimit;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.common.service.ISourceOfIncomeService;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjustApp;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.foreigncurrency.model.ReceiptPaymentApp;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.mail.ApllicationMailer1;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.IEmployeeService;
import com.amg.exchange.remittance.bean.PersonalRemittanceBean;
import com.amg.exchange.remittance.model.CashRate;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.SessionStateManage;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component("foreignCurrencySaleBean")
@Scope("session")
public class ForeignCurrencySaleBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(ForeignCurrencySaleBean.class);
	private int finaceYear;
	private String documentSerialId;
	private String location = null;
	private String id = null;
	private String name = null;
	private String mobile = null;
	private int customerId;
	private BigDecimal countryCurrencyId;
	private String countryCurrencyName;
	private BigDecimal currencyId;
	private BigDecimal saleAmountA;
	private BigDecimal totalPurchaseAmount;
	private BigDecimal totsaleAmount;
	private String foreignCurrencyName;
	private BigDecimal avgExchageRate;
	private String sourceOfIncomes;
	private String purposeOfTransactions;
	private ArrayList<ForeignSaleLocalCurrencyDataTable> lstData = new ArrayList<ForeignSaleLocalCurrencyDataTable>();
	private List<SourceOfIncomeDescription> lstSourceOfIncomes;
	private List<PurposeOfTransaction> lstPurposeOfTransactions;
	private PurposeOfTransaction purposeofTransaction;
	private SourceOfIncome sourceOfIncome;
	private List<CurrencyMaster> lstcurrency;
	private List<CurrencyWiseDenomination> lstDenomination;
	private BigDecimal denomIdCheckFor;
	private BigDecimal localSaleAmount;
	private BigDecimal temLocalSaleAmount;
	/** Responsible to manage session */
	SessionStateManage sessionStateManage = new SessionStateManage();
	/* Responsible to Date Management */
	private int financeMonth;
	private String totalValue = null;
	private String signatureSpecimen;
	// private int documentId = constants.DOCUMENT_ID_FOR_FC; // Now Hot code
	private int documentId = Integer.parseInt(Constants.DOCUMENT_CODE_FOR_FCSALE);
	private String processIn = Constants.Yes;
	@Autowired
	IGeneralService<T> igeneralService;
	private String msgs = null;
	List<Employee> list = new ArrayList<>();
	private String remarks;
	private String digitalSign;// new property for digital sign
	private List<DenominationBean> denominationBeanList = new ArrayList<DenominationBean>();
	private List<PurposeOfTransaction> lstPurposeOfTransaction = new ArrayList<>();
	private List<SourceOfIncome> lstSourceOfIncome = new ArrayList<>();
	private List<UserFinancialYear> financialYearList = new ArrayList<>();
	private StreamedContent myImage;
	private String docSerialIdNumberForSave;
	private Boolean firstTime = null;
	private String remitRequest;
	

	private String errmsg;
	
	private BigDecimal selectCard;
	
	private Boolean renderCustomerSignatureCheck=false;	
	public Boolean getRenderCustomerSignatureCheck() {
		return renderCustomerSignatureCheck;
	}

	public void setRenderCustomerSignatureCheck(Boolean renderCustomerSignatureCheck) {
		this.renderCustomerSignatureCheck = renderCustomerSignatureCheck;
	}
	
	


	@Autowired
	ApplicationContext appContext;
	@Autowired
	LoginBean loginBean;


	
	public BigDecimal getSelectCard() {
		return selectCard;
	}

	public void setSelectCard(BigDecimal selectCard) {
		this.selectCard = selectCard;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getSignatureSpecimen() {
		return signatureSpecimen;
	}

	public void setSignatureSpecimen(String signatureSpecimen) {
		this.signatureSpecimen = signatureSpecimen;
	}

	public String getRemitRequest() {
		return remitRequest;
	}

	public void setRemitRequest(String remitRequest) {
		this.remitRequest = remitRequest;
	}

	public Boolean getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(Boolean firstTime) {
		this.firstTime = firstTime;
	}

	public StreamedContent getMyImage() {
		return null;
	}

	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;

	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;

	public IForeignCurrencyPurchaseService<T> getForeignCurrencyPurchaseService() {
		return foreignCurrencyPurchaseService;
	}

	public void setForeignCurrencyPurchaseService(IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService) {
		this.foreignCurrencyPurchaseService = foreignCurrencyPurchaseService;
	}

	public ForeignCurrencySaleBean() {
	}

	public List<CurrencyMaster> getLstcurrency() {
		return lstcurrency;
	}

	public void setLstcurrency(List<CurrencyMaster> lstcurrency) {
		this.lstcurrency = lstcurrency;
	}

	public List<CurrencyWiseDenomination> getLstDenomination() {
		return lstDenomination;
	}

	public void setLstDenomination(List<CurrencyWiseDenomination> lstDenomination) {
		this.lstDenomination = lstDenomination;
	}

	public List<SourceOfIncomeDescription> getLstSourceOfIncomes() {
		return lstSourceOfIncomes;
	}

	public void setLstSourceOfIncomes(List<SourceOfIncomeDescription> lstSourceOfIncomes) {
		this.lstSourceOfIncomes = lstSourceOfIncomes;
	}

	public List<PurposeOfTransaction> getLstPurposeOfTransactions() {
		return lstPurposeOfTransactions;
	}

	public void setLstPurposeOfTransactions(List<PurposeOfTransaction> lstPurposeOfTransactions) {
		this.lstPurposeOfTransactions = lstPurposeOfTransactions;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public String getProcessIn() {
		return processIn;
	}

	public void setProcessIn(String processIn) {
		this.processIn = processIn;
	}

	public BigDecimal getTotsaleAmount() {
		return totsaleAmount;
	}

	public void setTotsaleAmount(BigDecimal totsaleAmount) {
		this.totsaleAmount = totsaleAmount;
	}

	public BigDecimal getSaleAmountA() {
		return saleAmountA;
	}

	public void setSaleAmountA(BigDecimal saleAmountA) {
		this.saleAmountA = saleAmountA;
	}

	public BigDecimal getTotalPurchaseAmount() {
		return totalPurchaseAmount;
	}

	public void setTotalPurchaseAmount(BigDecimal totalPurchaseAmount) {
		this.totalPurchaseAmount = totalPurchaseAmount;
	}

	public ArrayList<ForeignSaleLocalCurrencyDataTable> getLstData() {
		return lstData;
	}

	public void setLstData(ArrayList<ForeignSaleLocalCurrencyDataTable> lstData) {
		this.lstData = lstData;
	}

	public BigDecimal getDenomIdCheckFor() {
		return denomIdCheckFor;
	}

	public void setDenomIdCheckFor(BigDecimal denomIdCheckFor) {
		this.denomIdCheckFor = denomIdCheckFor;
	}

	public BigDecimal getLocalSaleAmount() {
		return localSaleAmount;
	}

	public void setLocalSaleAmount(BigDecimal localSaleAmount) {
		this.localSaleAmount = localSaleAmount;
	}

	public BigDecimal getTemLocalSaleAmount() {
		return temLocalSaleAmount;
	}

	public void setTemLocalSaleAmount(BigDecimal temLocalSaleAmount) {
		this.temLocalSaleAmount = temLocalSaleAmount;
	}

	public String getForeignCurrencyName() {
		List<CurrencyMaster> result = null;
		if (getCurrencyId() != null) {
			result = getForeignCurrencyPurchaseService().getCurrencyById(getCurrencyId());
			foreignCurrencyName = result.get(0).getCurrencyName() + "/" + getCountryCurrencyName();
		} else {
			foreignCurrencyName = "/" + getCountryCurrencyName();
		}
		return foreignCurrencyName;
	}

	public void setForeignCurrencyName(String foreignCurrencyName) {
		this.foreignCurrencyName = foreignCurrencyName;
	}

	/*
	 * public String getTotalPurchaseAmount() { totalPurchaseAmount = 0.0; for
	 * (DenominationBean denominationBean : denominationBeanList) {
	 * totalPurchaseAmount += Double.parseDouble(denominationBean
	 * .getDenominationName().toString()) denominationBean.getNoOfNotes();
	 * 
	 * } if (getCountryCurrencyId() != null) { return new
	 * GetRound().round(totalPurchaseAmount,
	 * foreignLocalCurrencyDenominationService
	 * .getDecimalPerCurrency(getCountryCurrencyId())); } else { return null; }
	 * }
	 */
	/*
	 * public void setTotalPurchaseAmount(double totalPurchaseAmount) {
	 * this.totalPurchaseAmount = totalPurchaseAmount; }
	 */
	public List<DenominationBean> getDenominationBeanList() {
		return denominationBeanList;
	}

	public void setDenominationBeanList(List<DenominationBean> denominationBeanList) {
		this.denominationBeanList = denominationBeanList;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getMsgs() {
		return msgs;
	}

	public void setMsgs(String msgs) {
		this.msgs = msgs;
	}

	public List<PurposeOfTransaction> getLstPurposeOfTransaction() {
		return lstPurposeOfTransaction;
	}

	public void setLstPurposeOfTransaction(List<PurposeOfTransaction> lstPurposeOfTransaction) {
		this.lstPurposeOfTransaction = lstPurposeOfTransaction;
	}

	public List<SourceOfIncome> getLstSourceOfIncome() {
		return lstSourceOfIncome;
	}

	public void setLstSourceOfIncome(List<SourceOfIncome> lstSourceOfIncome) {
		this.lstSourceOfIncome = lstSourceOfIncome;
	}

	public String getPurposeOfTransactions() {
		return purposeOfTransactions;
	}

	public void setPurposeOfTransactions(String purposeOfTransactions) {
		this.purposeOfTransactions = purposeOfTransactions;
	}

	public String getSourceOfIncomes() {
		return sourceOfIncomes;
	}

	public void setSourceOfIncomes(String sourceOfIncomes) {
		this.sourceOfIncomes = sourceOfIncomes;
	}

	public BigDecimal getCountryCurrencyId() {
		return countryCurrencyId;
	}

	public void setCountryCurrencyId(BigDecimal countryCurrencyId) {
		this.countryCurrencyId = countryCurrencyId;
	}

	public String getCountryCurrencyName() {
		List<CurrencyMaster> lstcontryCurr = igeneralService.getCountryCurrencyList(sessionStateManage.getCountryId());
		String currName = null;
		for (CurrencyMaster currencyMaster : lstcontryCurr) {
			setCountryCurrencyId(currencyMaster.getCurrencyId());
			setCountryCurrencyName(currencyMaster.getCurrencyName());
			currName = currencyMaster.getCurrencyName();
		}
		return currName;
	}

	public void setCountryCurrencyName(String countryCurrencyName) {
		this.countryCurrencyName = countryCurrencyName;
	}

	@SuppressWarnings("deprecation")
	public static String getCurrentDateWithFormat() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		for (int i = 0; i < 12; i++) {
			if (i < 9) {
				data.put(i, "0" + String.valueOf(i + 1));
			} else {
				data.put(i, String.valueOf(i + 1));
			}
		}
		//String year = String.valueOf(new Date().getYear()).substring(1, 3);

		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		String year =String.valueOf(calendar.get(Calendar.YEAR));

		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH)) + "/" + year;
	}

	/**
	 * 
	 * @return currency List
	 */
	public List<CurrencyMaster> getCurrencyList() {
		
		List<CurrencyMaster> lstcurrencySale = new ArrayList<CurrencyMaster>();
		try {
			lstcurrency = getForeignCurrencyPurchaseService().getAllCurrency(sessionStateManage.getCountryId());
			
			
			for (CurrencyMaster currencyMaster : lstcurrency) {
				if(currencyMaster.getAllowFCSale() != null && currencyMaster.getAllowFCSale().equalsIgnoreCase(Constants.Yes)){
					lstcurrencySale.add(currencyMaster);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		getCountryCurrency();
		return lstcurrencySale;
	}

	/**
	 * 
	 * @return source of income
	 */
	public List<SourceOfIncomeDescription> getSourceOfIncomeList() {
		try {
			lstSourceOfIncomes = getForeignCurrencyPurchaseService().getSourceofIncome(sessionStateManage.getLanguageId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstSourceOfIncomes;
	}

	/*
	 * public void getSourceofIncomeDetails() { lstSourceofIncome.clear();
	 * List<SourceOfIncomeDescription> lstSource =
	 * getForeignCurrencyPurchaseService
	 * ().getSourceofIncome(sessionStateManage.getLanguageId()); if
	 * (lstSource.size() != 0) { lstSourceofIncome.addAll(lstSource); } }
	 */
	/**
	 * @return purpose of transaction
	 */
	public List<PurposeOfTransaction> getPurposeOfTransactionsList() {
		try {
			lstPurposeOfTransactions = getForeignCurrencyPurchaseService().getAllPurposeOfTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstPurposeOfTransactions;
	}

	/*
	 * public List<CurrencyWiseDenomination> getCurrencyWiseDenominationList(
	 * AjaxBehaviorEvent event) {
	 */
	public void getCurrencyWiseDenominationList(AjaxBehaviorEvent event) {
		try {
			lstDenomination = getForeignCurrencyPurchaseService().getDenominationByCurrencyID(getCurrencyId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***************************************************************** Date Calculation *****************************************************/
	@SuppressWarnings("unused")
	private int getYearFromDate() {
		Date date = new Date();
		int result = -1;
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			result = cal.get(Calendar.YEAR);
		}
		return result;
	}

	@SuppressWarnings("unused")
	private int currentMonthQuater() {
		int month = getCurrentMonth();
		if (month >= 4 && month < 7)
			return 1;
		else if (month >= 7 && month <= 10)
			return 2;
		else if (month >= 10 && month <= 2)
			return 3;
		else
			return 4;
	}

	private int getCurrentMonth() {
		int cmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		return cmonth;
	}

	/******************************************************* Date Calculation End *********************************************************/
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFinanceMonth() {
		return financeMonth;
	}

	public void setFinanceMonth(int financeMonth) {
		this.financeMonth = financeMonth;
	}

	
	public Boolean checkSignatureMandatory(){
		Boolean chckSig=false;
		if(getRenderCustomerSignatureCheck()){
			if (getDigitalSign() != null && !getDigitalSign().equalsIgnoreCase("")) {
				chckSig=true;
			}else{
				chckSig=false;
			}
		}else{
			chckSig=true;
		}
		return chckSig;
	}
	
	public void checkSignRequired(){
		Boolean signCheck =  iPersonalRemittanceService.checkCorporateBranchForSignature(new BigDecimal(sessionStateManage.getBranchId()));
		if(signCheck || sessionStateManage.getUserType().equalsIgnoreCase(Constants.E)){
			setRenderCustomerSignatureCheck(false);
		}else{
			setRenderCustomerSignatureCheck(true);
		}
	}
	
	
	
	/**
	 * Get finacial Year
	 * 
	 * @return finaceYear
	 */
	public int getFinaceYear() {
		try {
			// financialYearList =
			// getForeignCurrencyPurchaseService().getUserFinancialYear(new
			// Date("11/11/2003")); for different date test
			financialYearList = getForeignCurrencyPurchaseService().getUserFinancialYear(new Date());
			log.info("financialYearList :" + financialYearList.size());
			if (financialYearList != null)
				finaceYear = Integer.parseInt(financialYearList.get(0).getFinancialYear().toString());
			setFinaceYear(finaceYear);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finaceYear;
	}

	public void setFinaceYear(int finaceYear) {
		this.finaceYear = finaceYear;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return sessionStateManage.getLocation();
	}

	public BigDecimal getAvgExchageRate() {
		return avgExchageRate;
	}

	public void setAvgExchageRate(BigDecimal avgExchageRate) {
		this.avgExchageRate = avgExchageRate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<UserFinancialYear> getFinancialYearList() {
		return financialYearList;
	}

	public void setFinancialYearList(List<UserFinancialYear> financialYearList) {
		this.financialYearList = financialYearList;
	}

	private String varToKeepSerial = null;

	public String getVarToKeepSerial() {
		return varToKeepSerial;
	}

	public void setVarToKeepSerial(String varToKeepSerial) {
		this.varToKeepSerial = varToKeepSerial;
	}

	public String getDocumentSerialId() {
		if (getVarToKeepSerial() != null) {
			return getVarToKeepSerial();
		} else {
			setVarToKeepSerial(getDocumentSerialID(Constants.Yes));
			return getVarToKeepSerial();
		}
	}

	public void setDocumentSerialId(String documentSerialId) {
		this.documentSerialId = documentSerialId;
	}

	/*
	 * public void purcahseCurrencyListDisplay() {
	 * 
	 * 
	 * try { denominationBeanList.clear(); BigDecimal roleId = new
	 * BigDecimal(sessionStateManage.getRoleId()); BigDecimal maxExchangeRate =
	 * new BigDecimal("0"); DenominationBean denominationBean = null; //
	 * System.out.println("currencyId   :"+currencyId);
	 * List<RoleWiseExchangeRate> roleWiseExchangeRates =
	 * getForeignCurrencyPurchaseService()
	 * .getRoleWiseExchangeRateByRoleId(roleId);
	 * 
	 * for (RoleWiseExchangeRate roleWiseExchangeRate : roleWiseExchangeRates) {
	 * 
	 * maxExchangeRate = roleWiseExchangeRate.getRoleMaxRate(); }
	 * 
	 * // System.out.println("currencyId   :"+currencyId);
	 * List<CurrencyWiseDenomination> currencyWiseDenomination =
	 * getForeignCurrencyPurchaseService()
	 * .getDenominationByCountryIDCurrencyID(
	 * sessionStateManage.getCountryId(),new
	 * BigDecimal(sessionStateManage.getCurrencyId())); for
	 * (CurrencyWiseDenomination currencyWiseDenominationobj :
	 * currencyWiseDenomination) { denominationBean = new DenominationBean(
	 * currencyWiseDenominationobj.getDenominationAmount(), 0, maxExchangeRate,
	 * Integer.parseInt(currencyWiseDenominationobj
	 * .getDenominationId().toString()),
	 * currencyWiseDenominationobj.getDenominationDesc(),
	 * currencyWiseDenominationobj.getDenominationAmount(),
	 * currencyWiseDenominationobj.getExCurrencyMaster() .getCurrencyCode());
	 * 
	 * denominationBeanList.add(denominationBean); }
	 * 
	 * 
	 * // Reseting TotalPurchaseAmount and SalesAmount acc to purchase //
	 * currency @ Chiranjeevi //setTotalPurchaseAmount(0.0);
	 * 
	 * 
	 * //getForeignCurrencyName(); } catch (Exception e) { e.printStackTrace();
	 * }
	 * 
	 * 
	 * }
	 */
	public void getDenominationTable(/* AjaxBehaviorEvent event */) {/*
	 * //22/01/2015
	 * 
	 * 
	 * /*
	 * Checking
	 * that it's
	 * first
	 * time or
	 * not,
	 * first
	 * time list
	 * size will
	 * be 0
	 */
		double sAmount = 0;
		sAmount = Double.parseDouble(getSaleAmount());
		@SuppressWarnings("unused")
		BigDecimal roleId = new BigDecimal(sessionStateManage.getRoleId());
		BigDecimal maxExchangeRate = BigDecimal.ZERO;
		// System.out.println("currencyId   :"+currencyId);
		if (getCurrencyId() != null) {
			List<CashRate> lstCashRate = getForeignCurrencyPurchaseService().getRoleWiseExchangeRateByRoleId(new BigDecimal(sessionStateManage.getBranchId()), getCurrencyId());
			if (lstCashRate != null) {
				for (CashRate cashRate : lstCashRate) {
					maxExchangeRate = cashRate.getSaleMaxRate();
				}
			}
		}
		lstData.clear();
		if (lstData.size() == 0) {
			/* Responsible to show serial number in datatable */
			int i = 0;
			ForeignSaleLocalCurrencyDataTable item = null;
			List<Stock> dataFromDb = foreignLocalCurrencyDenominationService.getCurrencyDenominationFromStock(sessionStateManage.getCountryId(), sessionStateManage.getUserName(), sessionStateManage.getBranchId(), sessionStateManage.getCompanyId(), getCurrencyId().toPlainString());
			if (dataFromDb.size() != 0) {
				/* putting the value in list to show in datatable */
				for (Stock element : dataFromDb) {
					int stock = element.getOpenQuantity() + element.getPurchaseQuantity() + element.getReceivedQuantity() - (element.getSaleQuantity() + element.getTransactionQuantity());
					String qty = null;
					double count = 0, totalNotes = 0;
					// double sAmount = 0;
					double denamination = 0;
					String amount = null;
					Double result = 0.0;
					int cal = 0;
					if (stock > 0) {
						denamination = Double.parseDouble(element.getDenominationId().getDenominationAmount().toString());
						if (denamination <= sAmount) {
							count = sAmount / denamination;
						}
						totalNotes = totalNotes + count;
						sAmount = sAmount % denamination;
						if (count != 0) {
							qty = new Integer((int) Math.floor(count)).toString();
							cal = new Integer((int) Math.floor(count));
							result = cal * denamination;
							amount = new Integer((int) Math.ceil(result)).toString();
						} else {
							qty = "";
							amount = "0";
						}
						/*
						 * item = new ForeignLocalCurrencyDataTable(++i,
						 * element.getDenominationAmount(), qty, amount, stock,
						 * element.getStockId(), element.getDenominationId(),
						 * element .getExCurrencyMaster().getCurrencyId(),
						 * element.getDenominationDesc());
						 */
						item = new ForeignSaleLocalCurrencyDataTable(++i, element.getDenominationId().getDenominationAmount(), qty, amount, stock, element.getStockId(), element.getDenominationId().getDenominationId(), element.getDenominationId().getExCurrencyMaster().getCurrencyId(), element
								.getDenominationId().getDenominationDesc(), maxExchangeRate, new BigDecimal(0));
						lstData.add(item);
					}
				}
			} else {
				RequestContext.getCurrentInstance().execute("denominationerror.show();");
			}
		}
		/* Responsible to keep sum of total amount of cash entered */
		int totalSum = 0;
		/* Responsible to calculate sum of entered cash amount */
		for (ForeignSaleLocalCurrencyDataTable element : lstData) {
			if (element.getPrice().length() != 0) {
				totalSum = totalSum + Integer.parseInt(element.getPrice());
			}
		}
		/* setting the summation value in bean object */
		setTotalValue(String.valueOf(totalSum));
		/* Responsible to get the parameterised value, and populate here */
		// setTotalSale(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("totalSale"));
		// setlstData;
	}

	/**
	 * @param event
	 *            table value change event for validation and immediate data
	 *            change
	 */

	ForeignLocalCurrencyDataTable dataTableClear = new ForeignLocalCurrencyDataTable();

	public ForeignLocalCurrencyDataTable getDataTableClear() {
		return dataTableClear;
	}

	public void setDataTableClear(ForeignLocalCurrencyDataTable dataTableClear) {
		this.dataTableClear = dataTableClear;
	}


	public void clearDataTableClearDenomination() {
		if (getDataTableClear() != null) {
			ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable = getDataTableClear();
			System.out.println("foreignLocalCurrencyDataTable" + foreignLocalCurrencyDataTable);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../foreigncurrency/foreigncurrencysale.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void onCellEdit(ForeignSaleLocalCurrencyDataTable denominationBean) {

		try {
			if (denominationBean.getQty() != null && !denominationBean.getQty().equals("") && Integer.parseInt(denominationBean.getQty()) > denominationBean.getStock()) {
				denominationBean.setQty("");
				/* denominationBean.setPrice(""); */
				setValidNoOFQuantity(String.valueOf(denominationBean.getStock()));
				RequestContext.getCurrentInstance().execute("invalidNoOFNotes.show();");
				return;
			}
		}
		catch (Exception e) {
			denominationBean.setQty("");
			/* denominationBean.setPrice(""); */
			setValidNoOFQuantity(String.valueOf(denominationBean.getStock()));
			RequestContext.getCurrentInstance().execute("invalidNoOFNotes.show();");
			return;
		}

		if (denominationBean.getQty() != null) {
			if (!denominationBean.getQty().trim().equals("")) {
				BigDecimal saleAmount = GetRound.roundBigDecimal(denominationBean.getItem().multiply(new BigDecimal(denominationBean.getQty())), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));// getCurrencyId()
				BigDecimal purchaseAmount = GetRound.roundBigDecimal(saleAmount.multiply(denominationBean.getExchangeRate()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));
				denominationBean.setPurchaseAmount(purchaseAmount);
				denominationBean.setSaleAmount(saleAmount);
			} else {
				denominationBean.setPurchaseAmount(BigDecimal.ZERO);
				denominationBean.setQty("");
				denominationBean.setSaleAmount(BigDecimal.ZERO);
			}
		} else {
			denominationBean.setPurchaseAmount(BigDecimal.ZERO);
			denominationBean.setQty("");
			denominationBean.setSaleAmount(BigDecimal.ZERO);
		}
		if (lstData.size() > 0) {
			BigDecimal totalSaleAmt = BigDecimal.ZERO;
			BigDecimal totalPurchaseAmt = BigDecimal.ZERO;
			for (ForeignSaleLocalCurrencyDataTable forSalCalc : lstData) {
				if (forSalCalc.getSaleAmount() != null) {
					totalSaleAmt = totalSaleAmt.add(forSalCalc.getSaleAmount());
				}
				if (forSalCalc.getPurchaseAmount() != null) {
					totalPurchaseAmt = totalPurchaseAmt.add(forSalCalc.getPurchaseAmount());
				}
			}
			setTotalPurchaseAmount(totalPurchaseAmt);
			setTotsaleAmount(totalSaleAmt);
		}
		calculateAvgRate();
	}

	public void calculateAvgRate() {
		if (lstData.size() > 0) {
			BigDecimal totalAvgRate = BigDecimal.ZERO;
			BigDecimal count = BigDecimal.ZERO;
			for (ForeignSaleLocalCurrencyDataTable forSalCalc : lstData) {
				if (forSalCalc.getExchangeRate() != null) {
					totalAvgRate = totalAvgRate.add(forSalCalc.getExchangeRate());
					count = count.add(BigDecimal.ONE);
				}
			}
			setAvgExchageRate(totalAvgRate.divide(count));
		}
	}

	// Decimal is according to Country id in saleAmount
	/*
	 * String saleAmount = new GetRound()
	 * .round(Double.parseDouble(purchaseAmount)
	 * Double.parseDouble(denominationBean.getExchangeRate() .toString()),
	 * foreignLocalCurrencyDenominationService
	 * .getDecimalPerCountry(sessionStateManage .getCountryId()));
	 */
	/*
	 * public void ClickForLocalCurrrency() throws IOException { if
	 * (getLstData() != null) { getLstData().clear(); } //
	 * FacesContext.getCurrentInstance().getExternalContext().redirect(
	 * "foreignLocalCurrencyDenomination.xhtml?totalSale=" // + getTotalSale());
	 * FacesContext.getCurrentInstance().getExternalContext()
	 * .redirect("foreignLocalCurrencyDenomination.xhtml"); }
	 */
	/******************************************* Local Currency Manage ***************************************************/
	/* Responsible to show total amount of entered cash */
	/* Responsible to control all data in Datatable */
	// private ArrayList<ForeignLocalCurrencyDataTable> lstRefundData = new
	// ArrayList<ForeignLocalCurrencyDataTable>();
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}


	private String validNoOFQuantity = null;

	public void acceptFromPurchase() {
	}

	public void backFromSalePanel() {
	}


	/**
	 * Responsible to calculate sale amount in purchase panel
	 * 
	 * @return
	 */
	public String getSaleAmount() {
		BigDecimal totalSaleAmount = BigDecimal.ZERO;
		BigDecimal value = BigDecimal.ZERO;
		for (DenominationBean denominationBean : denominationBeanList) {
			value = value.add(denominationBean.getDenominationName().multiply(new BigDecimal(denominationBean.getNoOfNotes())).divide(denominationBean.getExchangeRate(), denominationBean.getExchangeRate().intValue(), BigDecimal.ROUND_UP));
		}
		if (getCurrencyId() != null) {
			totalSaleAmount = GetRound.roundBigDecimal(value, foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));
		}
		return totalSaleAmount.toString();
	}

	public PurposeOfTransaction getPurposeofTransaction() {
		return purposeofTransaction;
	}

	public void setPurposeofTransaction(PurposeOfTransaction purposeofTransaction) {
		this.purposeofTransaction = purposeofTransaction;
	}

	public SourceOfIncome getSourceOfIncome() {
		return sourceOfIncome;
	}

	public void setSourceOfIncome(SourceOfIncome sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	/**
	 * Save ForeignCurrencyPurchasebean
	 */
	/*public void saveForeignCurrencyPurchase() {
	}*/

	/**
	 * Check for Stock Availability
	 * 
	 * @param value
	 * @param denomId
	 */
	public String checkStockAvailability(Object value, BigDecimal denomId) {
		String check = Constants.No;
		for (ForeignSaleLocalCurrencyDataTable element : lstData) {
			if (element.getPkDenom() == denomId) {
				if (value != null && value.toString().length() > 0) {
					if (element.getStock() < Integer.parseInt(value.toString())) {
						check = Constants.Yes;
					}
				}
			}
			if (check.equalsIgnoreCase(Constants.Yes)) {
				break;
			}
		}
		return check;
	}

	private String validNoNotes = null;
	@SuppressWarnings("unused")
	private Date Date;

	public void cleraFields() {
		// setSignaturePanel(false);
		setFinaceYear(0);
		setFinanceMonth(0);
		setId(null);
		setName(null);
		setMobile(null);
		setCurrencyId(null);
		setLocalSaleAmount(null);
		denominationBeanList.clear();
		setTotsaleAmount(null);
		setAvgExchageRate(null);
		setRemarks(null);
		setTotalPurchaseAmount(null);
		setSourceOfIncomes(null);
		setPurposeOfTransactions(null);
		// setForNextPanels(false);
		setVarToKeepSerial(null);
		// setNeededPurchaseAmount(0.0);
		// setMyImage(null);
		setDigitalSign(null);
		setSignatureSpecimen(null);
		lstData.clear();
		// lstRefundData.clear();
	}

	public void clearCache() {
		// setSignaturePanel(false);
		setFinaceYear(0);
		setFinanceMonth(0);
		setId(null);
		setName(null);
		setMobile(null);
		setCurrencyId(null);
		setLocalSaleAmount(null);
		denominationBeanList.clear();
		setTotsaleAmount(null);
		setAvgExchageRate(null);
		setRemarks(null);
		setSourceOfIncomes(null);
		setPurposeOfTransactions(null);
		// setForNextPanels(false);
		setVarToKeepSerial(null);
		// setNeededPurchaseAmount(0.0);
		// setMyImage(null);
		setDigitalSign(null);
		setSignatureSpecimen(null);
		lstData.clear();
		// lstRefundData.clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../foreigncurrency/foreigncurrencysale.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect");
		}
	}

	private void getCountryCurrency() {
		List<CurrencyMaster> lstcontryCurr = igeneralService.getCountryCurrencyList(sessionStateManage.getCountryId());
		for (CurrencyMaster currencyMaster : lstcontryCurr) {
			setCountryCurrencyId(currencyMaster.getCurrencyId());
			setCountryCurrencyName(currencyMaster.getCurrencyCode());
		}
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void idNotFound() {
		setId(null);
		setName(null);
		setMobile(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("foreigncurrencypurchase.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect");
		}
	}

	/**
	 * Responsible to take care when OK button will pressed from Dialog after
	 * saving
	 */
	public void clickOnOK() {
		setFinaceYear(0);
		setFinanceMonth(0);
		setId(null);
		setName(null);
		setMobile(null);
		setCurrencyId(null);
		denominationBeanList.clear();
		// setTotalPurchaseAmount(0.0);
		// /setForNextPanels(false);
		// setAvgExchageRate(0.0);
		setRemarks(null);
		setSourceOfIncomes(null);
		setPurposeOfTransactions(null);
		setVarToKeepSerial(null);
		// setSignaturePanel(false);
		lstData.clear();
		// lstRefundData.clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("foreigncurrencypurchase.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect");
		}
	}

	/**
	 * Document Seriality
	 */
	public String getDocumentSerialID(String processIn) {
		log.info("process in :" + processIn);
		documentSerialId = igeneralService.getNextDocumentReferenceNumber(Integer.parseInt(sessionStateManage.getSessionValue("countryId")), Integer.parseInt(sessionStateManage.getSessionValue("companyId")), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_FCSALE), finaceYear, processIn,
				sessionStateManage.getCountryBranchCode());
		return documentSerialId;
	}

	public String getValidNoNotes() {
		return validNoNotes;
	}

	public void setValidNoNotes(String validNoNotes) {
		this.validNoNotes = validNoNotes;
	}

	public String getValidNoOFQuantity() {
		return validNoOFQuantity;
	}

	public void setValidNoOFQuantity(String validNoOFQuantity) {
		this.validNoOFQuantity = validNoOFQuantity;
	}

	public Double getDenamiantionCount(String denamination, String saleAmount) {
		double denaminationValue = Double.parseDouble(denamination);
		double sAmount = Double.parseDouble(saleAmount);
		double reminder = 0;
		double qution = 0;
		if (denaminationValue <= sAmount) {
			reminder = sAmount % denaminationValue;
			qution = sAmount / denaminationValue;
			if (reminder != 0.0 && denaminationValue <= reminder) {
				qution = reminder / denaminationValue;
			}
			return qution;
		} else {
			qution = sAmount % denaminationValue;
			return qution;
		}
	}

	// CODE FOR LIMITATION EXCEEDING MAIL GENERATION
	@Autowired
	ApllicationMailer1 mailService1;
	@Autowired
	IEmployeeService iEmployeeService;
	private String email;
	private String emailId;
	@SuppressWarnings("unused")
	private String password;
	private String location1;
	private double limitationAmount;

	public double getLimitationAmount() {
		return limitationAmount;
	}

	public void setLimitationAmount(double limitationAmount) {
		this.limitationAmount = limitationAmount;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	String email1 = null;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ApllicationMailer1 getMailService1() {
		return mailService1;
	}

	public void setMailService1(ApllicationMailer1 mailService1) {
		this.mailService1 = mailService1;
	}

	@Autowired(required = true)
	JavaMailSender mailSender1;

	public JavaMailSender getMailSender1() {
		return mailSender1;
	}

	public void setMailSender1(JavaMailSender mailSender1) {
		this.mailSender1 = mailSender1;
	}

	public String getDigitalSign() {
		return digitalSign;
	}

	public void setDigitalSign(String digitalSign) {
		this.digitalSign = digitalSign;
	}

	public String getDocSerialIdNumberForSave() {
		return docSerialIdNumberForSave;
	}

	public void setDocSerialIdNumberForSave(String docSerialIdNumberForSave) {
		this.docSerialIdNumberForSave = docSerialIdNumberForSave;
	}
	
	// to check null
		private String nullCheck(String custname) {
			return custname == null ? "" : custname;
		}

	public void callFromRemittance(BigDecimal customerNO, String idNumber,BigDecimal cardType)// 22/01/2015
	{
		setCustomerIdNumber(customerNO);
		CustomerIdProof customerDetObj=null;
		try {
			cleraFields();
			//List<CustomerIdProof> data = getForeignCurrencyPurchaseService().dataCust(idNumber);
			List<CustomerIdProof> data = iPersonalRemittanceService.getCustomerDetailsFromCustomerId(idNumber.toUpperCase(), cardType,customerNO);
			
			if(data.size() != 0){
				for (CustomerIdProof lstCustomer : data) {
					if(lstCustomer.getIdentityStatus() != null && lstCustomer.getIdentityStatus().equalsIgnoreCase(Constants.Yes)){
						  customerDetObj = lstCustomer;
					}
				}
			}
			
			if(customerDetObj == null){
				// comparing with civil id
				BigDecimal identityTpeIds = igeneralService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
				if(identityTpeIds != null){
					List<CustomerIdProof> data1 = iPersonalRemittanceService.getCustomerDetailsFromCustomerId(idNumber.toUpperCase(), identityTpeIds,customerNO);
					if(data1.size() != 0){
						for (CustomerIdProof lstCustomer : data1) {
							if(lstCustomer.getIdentityStatus() != null && lstCustomer.getIdentityStatus().equalsIgnoreCase(Constants.Yes)){
								  customerDetObj = lstCustomer;
							}
						}
					}
				}
			}
			
			if(customerDetObj == null){
				// comparing with civil id new
				BigDecimal idtypeCivilIdnew = igeneralService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
				if(idtypeCivilIdnew != null){
					List<CustomerIdProof> data2 = iPersonalRemittanceService.getCustomerDetailsFromCustomerId(idNumber.toUpperCase(), idtypeCivilIdnew,customerNO);
					if(data2.size() != 0){
						for (CustomerIdProof lstCustomer : data2) {
							if(lstCustomer.getIdentityStatus() != null && lstCustomer.getIdentityStatus().equalsIgnoreCase(Constants.Yes)){
								  customerDetObj = lstCustomer;
							}
						}
					}
				}
			}
			
			@SuppressWarnings("unused")
			InputStream stream = null;
			@SuppressWarnings("unused")
			Blob blob = null;
			if (customerDetObj != null) {
				setName(nullCheck(customerDetObj.getFsCustomer().getFirstName()) + " " + nullCheck(customerDetObj.getFsCustomer().getMiddleName()) + " " + nullCheck(customerDetObj.getFsCustomer().getLastName()));
				setCustomerId(Integer.parseInt(customerDetObj.getFsCustomer().getCustomerId().toString()));
				setMobile(customerDetObj.getFsCustomer().getMobile());
				setId(idNumber);
				setDigitalSign(null);
				setSignatureSpecimen(customerDetObj.getFsCustomer().getSignatureSpecimenClob().getSubString(1, (int) customerDetObj.getFsCustomer().getSignatureSpecimenClob().length()));
			} else {
				setName("");
				setMobile("");
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("idNotF.show();");
			}
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			BigDecimal currencyId = iPersonalRemittanceService.getCurrencyId(objectPersonalRemittance.getBeneficiaryCountryId());
			setCurrencyId(currencyId);
			getDenominationTable();
		} catch (Exception e) {
			log.info("Problem to redirect:" + e);
		}
	}

	public void backToRemitance() {
		log.info("Entering into backToRemitance method");
		setFirstTime(null);
		removeAttributeFromSession();
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("backToFC", "yes");
			if (getRemitRequest() != null && getRemitRequest().equals("C")) {
				context.redirect("../remittance/CorporateRemittance.xhtml");
				setFirstTime(null);
				removeAttributeFromSession();
			}
			if (getRemitRequest() != null && getRemitRequest().equals("P")) {
				setFirstTime(null);
				removeAttributeFromSession();
				//context.redirect("../remittance/PersonalRemittance.xhtml");
				PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
				objectPersonalRemittance.setCustomerNo(getCustomerIdNumber());
				objectPersonalRemittance.checkShoppingCartRecords();
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");

			}
			// personalRemittanceBean.nextrenderingLastPanel();
		} catch (Exception e) {
			log.info("Problem to redirect:" + e);
		}
		log.info("Exit into backToRemitance method");
	}

	public void backToRemitanceFirstPanel() {
		log.info("Entering into backToRemitanceFirstPanel method");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = sessionStateManage.getSession();
		try {
			if (getRemitRequest() != null && getRemitRequest().equals("C")) {
				context.redirect("../remittance/CorporateRemittance.xhtml");
				session.setAttribute("cbackToRemitanceFirstPanel", "yes");
			}
			if (getRemitRequest() != null && getRemitRequest().equals("P")) {
				//session.setAttribute("backToRemitanceFirstPanel", "yes");
				//context.redirect("../remittance/PersonalRemittance.xhtml");
				PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
				objectPersonalRemittance.personalRemittanceBackFromBene(null);
				objectPersonalRemittance.setBeneficiaryCountryId(objectPersonalRemittance.getNationality());
				objectPersonalRemittance.populateCustomerDetailsFromBeneRelation();
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
			}
			// personalRemittanceBean.fromAccountExistDialogToBeneficaryTelephone();
		} catch (Exception e) {
			log.info("Problem to redirect:" + e);
		}
		setFirstTime(null);
		removeAttributeFromSession();
		log.info("Exit into backToRemitanceFirstPanel method");
	}

	@SuppressWarnings("unused")
	public void checkLimit() {
		String roleId1 = sessionStateManage.getRoleId();
		String sender = sessionStateManage.getUserName();
		String branchId = sessionStateManage.getBranchId();
		String employeeid = sessionStateManage.getEmployeeId().toString();
		location1 = sessionStateManage.getLocation();
		String telephoneno = sessionStateManage.getTelephoneNumber();
		String email = sessionStateManage.getEmail();
		int roleId = Integer.parseInt(roleId1);
		BigDecimal purchaseAmount1 = getLocalSaleAmount();
		// List<Employee> empList = iEmployeeService.getEmployees();
		// Role wise Currency Limit
		RoleWiseCurrencyLimit employeeLimit = iEmployeeService.getEmployeeLimit(new BigDecimal(sessionStateManage.getRoleId()));
		// System.out.println("Employee List =========== > " + empList.size());
		if (employeeLimit != null) {
			
			int res = purchaseAmount1.compareTo(employeeLimit.getLimitationAmount());
			if (employeeLimit.getFsRolemaster().getRoleId().intValue() == roleId && res == 1) {
				int countryBranchId = Integer.parseInt(branchId);
				limitationAmount = employeeLimit.getLimitationAmount().doubleValue();
				// MAIL GENERATION CODE
				List<Employee> newlist = iEmployeeService.getEmployees();
				ListIterator<Employee> le = newlist.listIterator();
				// ListIterator<RoleWiseCurrencyLimit>
				// le=newemployeeLimit.listIterator();
				while (le.hasNext()) {
					Employee e1 = le.next();
					if (e1.getFsCountryBranch().getCountryBranchId().intValue() == countryBranchId && e1.getFsRoleMaster().getRoleId().intValue() < roleId)
						if ((e1.getFsCountryBranch().getCountryBranchId().intValue() == countryBranchId) && (e1.getFsRoleMaster().getRoleId().intValue() == roleId - 1)) {
							// emailId ="r.naramala@almullagroup.com";
							emailId = e1.getEmail();
							String employeename = e1.getEmployeeName();
							Random r = new Random();
							int tokennumber = r.nextInt(99999) + 10000;
							String tokennum = "" + tokennumber;
							String customerid = "" + getCustomerId();
							// mailService.sendTokenMail1(emailId, getEmail1(),
							// "test", customerid, tokennum);
							mailService1.sendTokenMail1(emailId, email, "welcome", customerid, tokennum, sender, branchId, location1, telephoneno, employeename, email);
						}
				}
				setTemLocalSaleAmount(getLocalSaleAmount());
				RequestContext.getCurrentInstance().execute("limit.show();");
				setLocalSaleAmount(null);
			}
		
	}
}
	private boolean saveCheck = false;
	/**
	 * Foreign Currency Adjust Save
	 */
	@SuppressWarnings("unchecked")
	public void saveForeignCurrencyAdjust() {
		//if (getDigitalSign() != null && !getDigitalSign().equalsIgnoreCase("")) {
		
		if (checkSignatureMandatory()) { 
			
			if(lstData != null && lstData.size() != 0){
				if (getTotsaleAmount() != null && getLocalSaleAmount() != null && getLocalSaleAmount().compareTo(getTotsaleAmount()) == 0) {
					SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

					ForeignCurrencyAdjustApp foreignCurrencyAdjustApp = new ForeignCurrencyAdjustApp();
					Date acc_Month = null;
					try {
						acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					int i = 0;
					// For 2nd Panel
					String saveDocumentSerialID = getDocumentSerialID(Constants.U);
					try{

						for (ForeignSaleLocalCurrencyDataTable stockBean : lstData) {
							if (stockBean.getQty() != null && !stockBean.getQty().equals("") && Integer.parseInt(stockBean.getQty()) > 0) {
								// Company save
								CompanyMaster companyMaster = new CompanyMaster();
								companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
								foreignCurrencyAdjustApp.setFsCompanyMaster(companyMaster);
								// Country Save
								CountryMaster countryMaster = new CountryMaster();
								countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
								foreignCurrencyAdjustApp.setFsCountryMaster(countryMaster);
								// customer Save
								Customer customer = new Customer();
								customer.setCustomerId(new BigDecimal(getCustomerId()));
								foreignCurrencyAdjustApp.setFsCustomer(customer);
								foreignCurrencyAdjustApp.setDocumentDate(new Date());
								// currency Id
								CurrencyMaster currencyMaster = new CurrencyMaster();
								currencyMaster.setCurrencyId(stockBean.getCurrencyId());
								foreignCurrencyAdjustApp.setFsCurrencyMaster(currencyMaster);
								foreignCurrencyAdjustApp.setNotesQuantity(Integer.parseInt(stockBean.getQty()));
								CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
								denominationMaster.setDenominationId(stockBean.getDenominationId());
								foreignCurrencyAdjustApp.setFsDenominationId(denominationMaster);
								// It's for KWD
								// foreignCurrencyAdjustApp.setExchangeRate(new BigDecimal(0));
								foreignCurrencyAdjustApp.setDenaminationAmount(stockBean.getItem());
								foreignCurrencyAdjustApp.setDocumentFinanceYear(finaceYear);
								//foreignCurrencyAdjustApp.setAdjustmentAmount(Integer.parseInt(stockBean.getPrice()));
								foreignCurrencyAdjustApp.setAdjustmentAmount(stockBean.getSaleAmount().intValue());
								foreignCurrencyAdjustApp.setOracleUser(sessionStateManage.getUserName());
								/****************************************************************************************/
								try {
									foreignCurrencyAdjustApp.setDocumentCode(Integer.parseInt(Constants.DOCUMENT_CODE_FOR_FCSALE_REMITTANCE_APPLICATION));
									foreignCurrencyAdjustApp.setDocumentLineNumber(++i);
									foreignCurrencyAdjustApp.setDocumentNo(Integer.parseInt(saveDocumentSerialID));
									foreignCurrencyAdjustApp.setAccountmmyyyy(acc_Month);
									CountryBranch countrybranch = new CountryBranch();
									countrybranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
									foreignCurrencyAdjustApp.setFsCountryBranchMaster(countrybranch);
									CurrencyWiseDenomination currencyWiseDenomination = new CurrencyWiseDenomination();
									currencyWiseDenomination.setDenominationId(stockBean.getDenominationId());
									foreignCurrencyAdjustApp.setFsDenominationId(currencyWiseDenomination);
									foreignCurrencyAdjustApp.setNotesQuantity(Integer.parseInt(stockBean.getQty()));
									foreignCurrencyAdjustApp.setExchangeRate(stockBean.getExchangeRate());
									foreignCurrencyAdjustApp.setDenaminationAmount(stockBean.getItem());
									foreignCurrencyAdjustApp.setProgNumber(Constants.FC_SALE);
									foreignCurrencyAdjustApp.setDocumentStatus(Constants.No);
									foreignCurrencyAdjustApp.setTransactionType(Constants.S);
									/**
									 * CR : from O to S
									 */
									// foreignCurrencyAdjust.setTransactionType("S");
								} catch (Exception e) {
									e.printStackTrace();
								}
								/********************************************************************************************/
								/*
								 * foreignCurrencyAdjustApp.setApprovalBy(sessionStateManage
								 * .getSessionValue("userName"));
								 * foreignCurrencyAdjustApp.setApprovalDate(new Date());
								 */
								foreignCurrencyAdjustApp.setCreatedDate(new Date());
								foreignCurrencyAdjustApp.setCreatedBy(sessionStateManage.getSessionValue("userName"));
								foreignCurrencyAdjustApp.setRemarks(getRemarks());
								foreignCurrencyAdjustApp.setPurposeOfTransaction(getPurposeOfTransactions());
								foreignCurrencyAdjustApp.setSourceOfIncome(getSourceOfIncomes());
								// NO NEED TO PUT SIGNATURE
								/*foreignCurrencyAdjustApp.setCustomerSignature(getDigitalSign());*/
								getForeignCurrencyPurchaseService().save((T) foreignCurrencyAdjustApp);
								setMsgs("FC Sale Saved Successfully");
								saveCheck = true;
							} else {
								// /saveCheck=false;
								log.info("Number of notes is 0");
							}
						}
						if (saveCheck) {
							savepaymentReceiptApp(saveDocumentSerialID);
						}

						RequestContext.getCurrentInstance().execute("complete.show();");

					}catch(Exception error){
						setErrmsg(error.getMessage());
						RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
					}

				} else {
					RequestContext.getCurrentInstance().execute("showValid.show();");
				}
			}else{
				RequestContext.getCurrentInstance().execute("denominationerror.show();");
			}
		} else {
			RequestContext.getCurrentInstance().execute("signatureError.show();");
		}
	}


	// back to remittance
	public void completed() {
		// RequestContext.getCurrentInstance().execute("printDialog.show();");
		backToRemitance();
	}
	
	// after successfully created fc sale page if yes
	public void fcSaleNavigationPage(){
		log.info("Entering into callToFCSale method");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("customerNo", getCustomerIdNumber());
			session.setAttribute("IdNumber", getId());
			session.setAttribute("remit", getRemitRequest());
			context.redirect("../foreigncurrency/foreigncurrencysale.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect:" + e);
		}
	}

	@SuppressWarnings("unchecked")
	public void savepaymentReceiptApp(String saveDocumentSerialID) {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		Date acc_Month = null;
		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		log.info("paymentReceipt start ..");
		try {
			ReceiptPaymentApp receiptPayment = new ReceiptPaymentApp();

			receiptPayment.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
			receiptPayment.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
			receiptPayment.setCustomerId(new BigDecimal(getCustomerId()));
			receiptPayment.setDocumentFinanceYear(new BigDecimal(finaceYear));
			receiptPayment.setCustomerName(getName());
			receiptPayment.setLocalCurrencyId(getCountryCurrencyId());
			receiptPayment.setForignTrnxAmount(getTotsaleAmount());
			receiptPayment.setForeignCurrencyId(getCurrencyId());
			receiptPayment.setLocalTrnxAmount(getTotalPurchaseAmount());
			//receiptPayment.setTransactionActualRate(GetRound.roundBigDecimal(getAvgExchageRate(), 4));
			receiptPayment.setTransactionActualRate(getAvgExchageRate());
			receiptPayment.setLocalNetAmount(getTotalPurchaseAmount());
			receiptPayment.setDocumentStatus(Constants.Yes);
			receiptPayment.setGeneralLegerDate(new Date());
			receiptPayment.setIsActive(Constants.Yes);
			receiptPayment.setAccountMMYYYY(acc_Month);
			receiptPayment.setTransactionType(Constants.S);
			receiptPayment.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FCSALE_REMITTANCE_APPLICATION));
			receiptPayment.setDocumentNo(new BigDecimal(saveDocumentSerialID));
			receiptPayment.setBranchId(new BigDecimal(sessionStateManage.getBranchId()));
			receiptPayment.setDocumentDate(new Date());
			
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String hostname = loginBean.getClientIpAddress(request);
			receiptPayment.setTransactionIPAddress(hostname);
			
			if (getPurposeOfTransactions() != null) {
				receiptPayment.setPurposeofTransactionId(new BigDecimal(getPurposeOfTransactions()));
			}
			
			if (getSourceOfIncomes() != null) {
				receiptPayment.setSourceofIncomeId(new BigDecimal(getSourceOfIncomes()));
			}

			if(getSourceOfIncomes() != null){
				receiptPayment.setSourceOfIncomeid(new BigDecimal(getSourceOfIncomes()));
			}

			if (getDigitalSign() != null) {
				try {
					receiptPayment.setSignatureSpacimenClob(stringToClob(getDigitalSign()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			receiptPayment.setRemarks(getRemarks());
			receiptPayment.setCreatedDate(new Date());
			receiptPayment.setCreatedBy(sessionStateManage.getSessionValue("userName"));
			getForeignCurrencyPurchaseService().save((T) receiptPayment);
			saveCheck = true;
		} catch (Exception error) {
			error.printStackTrace();
			saveCheck = false;
			setErrmsg(error.getMessage());
			RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
		}
		log.info("paymentReceipt end ..");
	}


	public java.sql.Clob stringToClob(String source) throws Exception {
		try {
			return new javax.sql.rowset.serial.SerialClob(source.toCharArray());
		} catch (Exception e) {
			return null;
		}
	}

	private Boolean successPanel = false;
	private Boolean booRenderFcSalePanel = false;

	public Boolean getBooRenderFcSalePanel() {
		return booRenderFcSalePanel;
	}

	public void setBooRenderFcSalePanel(Boolean booRenderFcSalePanel) {
		this.booRenderFcSalePanel = booRenderFcSalePanel;
	}

	public Boolean getSuccessPanel() {
		return successPanel;
	}

	public void setSuccessPanel(Boolean successPanel) {
		this.successPanel = successPanel;
	}

	// Report Generation By Rahamathali Shaik............
	private BigDecimal customerIdNumber;

	public BigDecimal getCustomerIdNumber() {
		return customerIdNumber;
	}

	public void setCustomerIdNumber(BigDecimal customerIdNumber) {
		this.customerIdNumber = customerIdNumber;
	}

	private List<FcSaleReport> fcSaleReportDTOList = null;
	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
	@Autowired
	ISourceOfIncomeService sourceOfIncomeService;


	public void generateReports() {
		
		fcSaleReportDTOList = new ArrayList<FcSaleReport>();

		String purchageCurrency = igeneralService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));

		List<ForeignCurrencyAdjustApp> foreignCurrencyAdjustAppList = foreignCurrencyPurchaseService.getAllValuesForReportGenaration(getCustomerIdNumber(), getDocumentSerialId(), new BigDecimal(getFinaceYear()));
		ForeignCurrencyAdjustApp foreignCurrencyAdjustApp = foreignCurrencyAdjustAppList.get(0);

		FcSaleReport fcSaleReport = new FcSaleReport();
		BigDecimal saleCurrencyId = foreignCurrencyAdjustApp.getFsCurrencyMaster().getCurrencyId();
		String saleCurrencyCode = igeneralService.getCurrencyQuote(saleCurrencyId);

		if(foreignCurrencyAdjustApp.getDocumentFinanceYear()!=0 && foreignCurrencyAdjustApp.getDocumentNo()!=0){
			fcSaleReport.setReceiptNo(foreignCurrencyAdjustApp.getDocumentFinanceYear()+" / "+foreignCurrencyAdjustApp.getDocumentNo());
		}else if(foreignCurrencyAdjustApp.getDocumentNo()!=0){
			fcSaleReport.setReceiptNo(foreignCurrencyAdjustApp.getDocumentNo()+"");
		}

		fcSaleReport.setLocation(sessionStateManage.getLocation());

		fcSaleReport.setCustomerName(foreignCurrencyAdjustApp.getFsCustomer().getFirstName()==null?"":foreignCurrencyAdjustApp.getFsCustomer().getFirstName()+ " "+foreignCurrencyAdjustApp.getFsCustomer().getMiddleName()==null?" ":foreignCurrencyAdjustApp.getFsCustomer().getMiddleName());
		fcSaleReport.setTelephoneNo(foreignCurrencyAdjustApp.getFsCustomer().getMobile());
		List<CurrencyMaster> lstcontryCurr = igeneralService.getCountryCurrencyList(foreignCurrencyAdjustApp.getFsCountryMaster().getCountryId());
		fcSaleReport.setPurchageCurrency(lstcontryCurr.get(0).getCurrencyName());
		String saleCurrency = specialCustomerDealRequestService.getCurrencyForUpdate(foreignCurrencyAdjustApp.getFsCurrencyMaster().getCurrencyId());
		if(saleCurrency != null){
			fcSaleReport.setSaleCurrency(saleCurrency);
		}

		String customerSignature = new String();
		List<ReceiptPaymentApp> receiptPaymentAppList = foreignCurrencyPurchaseService.getReceiptPaymentForReportGeneration(new BigDecimal(getDocumentSerialId()));
		for (ReceiptPaymentApp receiptPaymentApp : receiptPaymentAppList) {


			if(receiptPaymentApp.getForignTrnxAmount()!=null && saleCurrencyId!=null && saleCurrencyCode!=null){
				BigDecimal saleAmount=GetRound.roundBigDecimal((receiptPaymentApp.getForignTrnxAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(saleCurrencyId));
				fcSaleReport.setSaleAmount(saleCurrencyCode+"     ******"+saleAmount.toString());
			}

			if(receiptPaymentApp.getForignTrnxAmount()!=null && saleCurrencyId!=null && saleCurrencyCode!=null){
				BigDecimal totalSaleAmount=GetRound.roundBigDecimal((receiptPaymentApp.getForignTrnxAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(saleCurrencyId));
				fcSaleReport.setTotalSaleAmount(saleCurrencyCode+"     ******"+totalSaleAmount.toString());
			}

			if(receiptPaymentApp.getLocalNetAmount()!=null && purchageCurrency!=null){
				BigDecimal totalPurchageAmount=GetRound.roundBigDecimal((receiptPaymentApp.getLocalNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
				fcSaleReport.setTotalPurchageAmount(purchageCurrency+"     ******"+totalPurchageAmount.toString());
			}

			if(receiptPaymentApp.getTransactionActualRate()!=null && purchageCurrency!=null && saleCurrencyCode!=null){
				fcSaleReport.setExchageRate(purchageCurrency+" / "+saleCurrencyCode+"     "+receiptPaymentApp.getTransactionActualRate().toString());
			}

			if(receiptPaymentApp.getSignatureSpacimenClob()!=null)
			{
				try {
					customerSignature = receiptPaymentApp.getSignatureSpacimenClob().getSubString(1,(int) receiptPaymentApp.getSignatureSpacimenClob().length());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		BigDecimal customerRef = foreignCurrencyAdjustApp.getFsCustomer().getCustomerReference();
		fcSaleReport.setCustomerId(customerRef);
		HttpSession session = sessionStateManage.getSession();
		String IdNumber = (String) session.getAttribute("IdNumber");
		fcSaleReport.setCivilId(IdNumber);
		fcSaleReport.setIdExpdate(new SimpleDateFormat("dd/MM/yyyy").format((igeneralService.getValidExpiryDateforFCSale(IdNumber))));
		fcSaleReport.setTodayDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		fcSaleReport.setCommision(null);
		fcSaleReport.setPaymentMode(null);

		if(saleCurrencyCode!=null){
			BigDecimal amountRefund=GetRound.roundBigDecimal((BigDecimal.ZERO),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(saleCurrencyId));
			fcSaleReport.setAmountRefund(saleCurrencyCode+"     ******"+amountRefund.toString());
		}

		fcSaleReport.setRemrks(foreignCurrencyAdjustApp.getRemarks());
		String source = null;
		String purpose = null;
		if (foreignCurrencyAdjustApp.getPurposeOfTransaction() != null) {
			String pur = sourceOfIncomeService.getPurposeOfTransaction(new BigDecimal(foreignCurrencyAdjustApp.getPurposeOfTransaction()));
			if (pur != null) {
				purpose = pur;
			}
		}
		if (foreignCurrencyAdjustApp.getSourceOfIncome() != null) {
			String sur = sourceOfIncomeService.getSourceOfIncome(new BigDecimal(foreignCurrencyAdjustApp.getSourceOfIncome()));
			if (sur != null) {
				source = sur;
			}
		}

		HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(customerRef, foreignCurrencyAdjustApp.getDocumentDate());

		String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
		String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
		String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
		String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
		String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
		String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");

		if(!prLtyStr1.trim().equals("") && !prLtyStr2.trim().equals("")){
			fcSaleReport.setLoyaltyPointExpiring(prLtyStr1+"  \n"+prLtyStr2);
		}else if(!prLtyStr1.trim().equals("")){
			fcSaleReport.setLoyaltyPointExpiring(prLtyStr1);
		}else if(!prLtyStr2.trim().equals("")){
			fcSaleReport.setLoyaltyPointExpiring(prLtyStr2);
		}

		String insurence1 ="";

		if(!prInsStr1.trim().equals("") && !prInsStrAr1.trim().equals("")){
			insurence1 = prInsStr1+"  \n"+prInsStrAr1;
		}else if(!prInsStr1.trim().equals("")){
			insurence1 = prInsStr1;
		}else if(!prInsStrAr1.trim().equals("")){
			insurence1 = prInsStrAr1;
		}

		String insurence2 ="";

		if(!prInsStr2.trim().equals("") && !prInsStrAr2.trim().equals("")){
			insurence2 = prInsStr2+"  \n"+prInsStrAr2;
		}else if(!prInsStr2.trim().equals("")){
			insurence2 = prInsStr2;
		}else if(!prInsStrAr2.trim().equals("")){
			insurence2 = prInsStrAr2;
		}

		if(!insurence1.trim().equals("") && !insurence2.trim().equals("")){
			fcSaleReport.setInsurence(insurence1+" \n"+insurence2);
		}else if(!insurence1.trim().equals("")){
			fcSaleReport.setInsurence(insurence1);
		}else if(!insurence2.trim().equals("")){
			fcSaleReport.setInsurence(insurence2);
		}

		fcSaleReport.setUserName(sessionStateManage.getUserName());
		fcSaleReport.setPurposeOfTransaction(purpose);
		fcSaleReport.setSourceOfIncome(source);
		
		ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = context.getRealPath("//");
		//String logoPath = realPath + Constants.LOGO_PATH;
		
		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		
		fcSaleReport.setLogoPath(logoPath);

		String reportPath = realPath + Constants.REPORT_WATERMARK_LOGO;
		fcSaleReport.setWaterMarkLogoPath(reportPath);
		fcSaleReport.setWaterMarkCheck(false);

		fcSaleReport.setSignature(customerSignature);

		List<Employee> employeeList = iPersonalRemittanceService.getEmployeeDetails(sessionStateManage.getUserName());

		if(employeeList!=null && employeeList.size()>0){
			try {
				Employee employee = employeeList.get(0);
				if (employee.getSignatureSpecimenClob() != null) {
					fcSaleReport.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		fcSaleReportDTOList.add(fcSaleReport);
		session = null;
	}

	JasperPrint jasperPrint;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void init() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fcSaleReportDTOList);

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		//String realPath = ctx.getRealPath("//");
		//String reportPath = realPath + "\\reports\\design\\fcsalefinalReport.jasper";
		String realPath = ctx.getRealPath("//");
		String reportPath = realPath +"//reports//design//fcsalefinalReport.jasper";
		//String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/fcsalefinalReport.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}

	public void generateNewUpdatedReports(ActionEvent actionEvent) throws JRException, IOException {
		ServletOutputStream servletOutputStream=null;
		try{
			generateReports();
			init();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=ForeignCurrencySaleReport.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		}catch(Exception e){
			log.error("Exception occured generateNewUpdatedReports");
		}finally{
			if(servletOutputStream!=null){
				servletOutputStream.close();
			}
		}
	}

	public void populateValue() {
		if (firstTime == null || FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("remit") != null) {
			/* setFirstTime(true); */
			HttpSession session = sessionStateManage.getSession();
			BigDecimal customerNo = (BigDecimal) session.getAttribute("customerNo");
			String IdNumber = (String) session.getAttribute("IdNumber");
			String remitRequest = (String) session.getAttribute("remit");
			BigDecimal cardType = (BigDecimal) session.getAttribute("cardType");
			setRemitRequest(remitRequest);
			setBooRenderFcSalePanel(true);
			setBooRenderFcSalePanel(true);
			setSuccessPanel(false);
			getDocumentSerialID(Constants.Yes); 
			callFromRemittance(customerNo, IdNumber,cardType);
			setFirstTime(true);
			removeAttributeFromSession();
			session.setAttribute("IdNumber", IdNumber);
			checkSignRequired();
		}
	}

	public void removeAttributeFromSession() {
		HttpSession session = sessionStateManage.getSession();
		session.removeAttribute("customerNo");
		session.removeAttribute("IdNumber");
		session.removeAttribute("remit");
	}
}
