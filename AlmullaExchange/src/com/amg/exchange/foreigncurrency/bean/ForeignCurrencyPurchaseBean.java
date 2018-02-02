package com.amg.exchange.foreigncurrency.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.amg.exchange.bean.LoginBean;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CutomerDetailsView;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyPurchaseReport;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.mail.ApllicationMailer1;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerIdproofView;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.registration.service.IEmployeeService;
import com.amg.exchange.remittance.model.CashRate;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("foreignCurrencyPurchaseBean")
/* @SessionScoped */
@Scope("session")
public class ForeignCurrencyPurchaseBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(ForeignCurrencyPurchaseBean.class);

	private List<CurrencyWiseDenomination> lstDenomination;
	private PurposeOfTransaction purposeofTransaction;
	private SourceOfIncome sourceOfIncome;
	private String saleAmount = null;
	private String tempSaleAmount = null;
	private String sourceOfIncomes;
	private String purposeOfTransactions;
	private int purposeId;
	private int sourceId;
	private boolean forNextPanels = false;
	private Boolean fcPurchageLabel = false;
	private Boolean localcashpaidtoCustomerLabel = false;
	private boolean booAccept=false;
	private String signatureMandatoryMsg;
	private boolean booRenderSignatureMsg = false;

	/** Responsible to manage session */
	SessionStateManage sessionStateManage = new SessionStateManage();

	/* Responsible to Date Management */
	private int financeMonth;
	private int finaceYear;
	private BigDecimal financeYearId;
	private String id = null;
	private String name = null;
	private String idRef = null;
	private String mobile = null;
	private BigDecimal customerId;
	private BigDecimal neededPurchaseAmount;
	private String digitalSignature=null;
	private String signatureSpecimen = null; // property :Signature Specimen

	// private int documentId = constants.DOCUMENT_ID_FOR_FC; // Now Hot code
	private int documentId = Integer.parseInt(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE);
	private BigDecimal documentIdPk=null;
	private String processIn = Constants.Yes;
	// private String processIn = "U";
	private int documentSerialId;

	/* Responsible to manage Column heading in Search Page datatable */
	private Boolean booReg = false;
	private Boolean booFc = true;

	/* Responsible to populate Location */
	private String location = null;
	private String msgs = null;
	private Boolean BooProcedureDialog=false;
	private String errMsg;

	// payment mode
	private boolean booSecondPanelDenoDatatable;
	private boolean booSecondPanelButtons;
	private BigDecimal colpaymentmodeId;
	private String colpaymentmodeName;
	private String colpaymentmodeCode;
	private String colBankCode;
	// cheque details
	private boolean booRenderColCheque;
	private boolean booRenderColBanks;
	private boolean booRenderColApprovalNo;
	private String colChequeRef;
	private Date colChequeDate;
	private Date effectiveMinDate = new Date();
	private String colApprovalNo;
	private String saveOrNext = Constants.Save;
	private List<PaymentModeDesc> lstFetchAllPayMode = new ArrayList<PaymentModeDesc>();
	private List<ViewBankDetails> bankMasterList = new CopyOnWriteArrayList<ViewBankDetails>();
	private Map<String , BigDecimal> mapFetchAllPayMode = new HashMap<String, BigDecimal>();

	List<Employee> list = new ArrayList<>();

	private BigDecimal currencyId;
	private BigDecimal noNotes = BigDecimal.ZERO;
	private BigDecimal denomination;
	private BigDecimal purchaseAmount;
	private BigDecimal fundMaxRate;
	private BigDecimal totalPurchaseAmount;
	private BigDecimal exchangeRate;
	private String saleCurrencyCode;
	private BigDecimal avgExchageRate;
	private String remarks;
	private String signature;
	private String foreignCurrencyName;

	private String digitalSign;// new property for digital sign

	private List<DenominationBean> denominationBeanList = new ArrayList<DenominationBean>();
	private List<PurposeOfTransaction> lstPurposeOfTransaction = new ArrayList<PurposeOfTransaction>();
	private List<SourceOfIncomeDescription> lstSourceOfIncome = new ArrayList<SourceOfIncomeDescription>();
	private List<UserFinancialYear> financialYearList = new ArrayList<UserFinancialYear>();
	private List<CurrencyMaster> lstCurrencyList = new ArrayList<CurrencyMaster>();

	/**
	 * Responsible to manage panel
	 */
	private boolean booPurchasePanel = true;
	private boolean booPanelDatatable = false;
	private boolean booRefundPanel = false;
	private boolean signaturePanel = false;
	private boolean focus = true;

	private Collect collect;
	private CollectDetail collectDetail;
	private ForeignCurrencyAdjust foreignCurrencyAdjust;
	private CompanyMaster companyMaster;
	private CountryMaster countryMaster;
	private Customer customer;
	private CustomerIdProof customerIdProof;
	private CurrencyMaster currencyMaster;
	private BankMaster bankMaster;
	private CurrencyWiseDenomination denominationMaster;
	private ReceiptPayment receiptPayment;

	private String totalRefundCashRecipt = null;
	private String totalRefundLocalCashEntered = null;

	private BigDecimal denomIdCheckFor = null;
	// by subramanian Id proof based on customer
	private StreamedContent myImage;

	private String docSerialIdNumberForSave;

	private String errmsg;
	private int documentSerilaNoCheck;

	// End
	private BigDecimal pkCollect = null;
	private Boolean isFromFCPurchage = false;

	@Autowired
	IGeneralService<T> iGeneralService;
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;
	@Autowired
	IPaymentService ipaymentService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	LoginBean loginBean;
	

	public Map<String, BigDecimal> getMapFetchAllPayMode() {
		return mapFetchAllPayMode;
	}
	public void setMapFetchAllPayMode(Map<String, BigDecimal> mapFetchAllPayMode) {
		this.mapFetchAllPayMode = mapFetchAllPayMode;
	}
	
	public boolean isBooSecondPanelDenoDatatable() {
		return booSecondPanelDenoDatatable;
	}
	public void setBooSecondPanelDenoDatatable(boolean booSecondPanelDenoDatatable) {
		this.booSecondPanelDenoDatatable = booSecondPanelDenoDatatable;
	}
	
	public boolean isBooSecondPanelButtons() {
		return booSecondPanelButtons;
	}
	public void setBooSecondPanelButtons(boolean booSecondPanelButtons) {
		this.booSecondPanelButtons = booSecondPanelButtons;
	}
	
	public List<CurrencyMaster> getLstCurrencyList() {
		return lstCurrencyList;
	}
	public void setLstCurrencyList(List<CurrencyMaster> lstCurrencyList) {
		this.lstCurrencyList = lstCurrencyList;
	}

	public boolean isBooRenderColCheque() {
		return booRenderColCheque;
	}
	public void setBooRenderColCheque(boolean booRenderColCheque) {
		this.booRenderColCheque = booRenderColCheque;
	}

	public boolean isBooRenderColBanks() {
		return booRenderColBanks;
	}
	public void setBooRenderColBanks(boolean booRenderColBanks) {
		this.booRenderColBanks = booRenderColBanks;
	}

	public boolean isBooRenderColApprovalNo() {
		return booRenderColApprovalNo;
	}
	public void setBooRenderColApprovalNo(boolean booRenderColApprovalNo) {
		this.booRenderColApprovalNo = booRenderColApprovalNo;
	}

	public String getColChequeRef() {
		return colChequeRef;
	}
	public void setColChequeRef(String colChequeRef) {
		this.colChequeRef = colChequeRef;
	}

	public Date getColChequeDate() {
		return colChequeDate;
	}
	public void setColChequeDate(Date colChequeDate) {
		this.colChequeDate = colChequeDate;
	}

	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}
	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}

	public String getColApprovalNo() {
		return colApprovalNo;
	}
	public void setColApprovalNo(String colApprovalNo) {
		this.colApprovalNo = colApprovalNo;
	}
	
	public String getSaveOrNext() {
		return saveOrNext;
	}
	public void setSaveOrNext(String saveOrNext) {
		this.saveOrNext = saveOrNext;
	}
	
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Boolean getBooProcedureDialog() {
		return BooProcedureDialog;
	}
	public void setBooProcedureDialog(Boolean booProcedureDialog) {
		BooProcedureDialog = booProcedureDialog;
	}

	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public StreamedContent getMyImage() {
		return myImage;
	}
	public void setMyImage(StreamedContent myImage) {
		this.myImage = myImage;
	}

	public IForeignCurrencyPurchaseService<T> getForeignCurrencyPurchaseService() {
		return foreignCurrencyPurchaseService;
	}
	public void setForeignCurrencyPurchaseService(IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService) {
		this.foreignCurrencyPurchaseService = foreignCurrencyPurchaseService;
	}

	public List<CurrencyWiseDenomination> getLstDenomination() {
		return lstDenomination;
	}
	public void setLstDenomination(List<CurrencyWiseDenomination> lstDenomination) {
		this.lstDenomination = lstDenomination;
	}

	public Boolean getFcPurchageLabel() {
		return fcPurchageLabel;
	}
	public void setFcPurchageLabel(Boolean fcPurchageLabel) {
		this.fcPurchageLabel = fcPurchageLabel;
	}

	public Boolean getLocalcashpaidtoCustomerLabel() {
		return localcashpaidtoCustomerLabel;
	}
	public void setLocalcashpaidtoCustomerLabel(Boolean localcashpaidtoCustomerLabel) {
		this.localcashpaidtoCustomerLabel = localcashpaidtoCustomerLabel;
	}

	public Boolean getIsFromFCPurchage() {
		return isFromFCPurchage;
	}
	public void setIsFromFCPurchage(Boolean isFromFCPurchage) {
		this.isFromFCPurchage = isFromFCPurchage;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getNoNotes() {
		return noNotes;
	}
	public void setNoNotes(BigDecimal noNotes) {
		this.noNotes = noNotes;
	}

	public BigDecimal getDenomination() {
		return denomination;
	}
	public void setDenomination(BigDecimal denomination) {
		this.denomination = denomination;
	}

	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}
	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public BigDecimal getFundMaxRate() {
		return fundMaxRate;
	}
	public void setFundMaxRate(BigDecimal fundMaxRate) {
		this.fundMaxRate = fundMaxRate;
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

	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}

	public boolean isFocus() {
		return focus;
	}
	public void setFocus(boolean focus) {
		this.focus = focus;
	}
	
	public String getForeignCurrencyName() {
		if (getCurrencyId() != null) {
			//result = getForeignCurrencyPurchaseService().getCurrencyById(getCurrencyId());
			String purchaseCurrencyQuote = iGeneralService.getCurrencyQuote(getCurrencyId());
			if(purchaseCurrencyQuote != null){
				foreignCurrencyName = getSaleCurrencyCode()+ " / " + purchaseCurrencyQuote;
			}
		} else {
			foreignCurrencyName = getSaleCurrencyCode()+" / IND";
		}
		return foreignCurrencyName;
	}

	public void setForeignCurrencyName(String foreignCurrencyName) {
		this.foreignCurrencyName = foreignCurrencyName;
	}

	public BigDecimal getTotalPurchaseAmount() {
		return totalPurchaseAmount;
	}
	public void setTotalPurchaseAmount(BigDecimal totalPurchaseAmount) {
		this.totalPurchaseAmount = totalPurchaseAmount;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public List<DenominationBean> getDenominationBeanList() {
		return denominationBeanList;
	}
	public void setDenominationBeanList(List<DenominationBean> denominationBeanList) {
		this.denominationBeanList = denominationBeanList;
	}

	public boolean isBooPurchasePanel() {
		return booPurchasePanel;
	}
	public void setBooPurchasePanel(boolean booPurchasePanel) {
		this.booPurchasePanel = booPurchasePanel;
	}

	public boolean isBooPanelDatatable() {
		return booPanelDatatable;
	}
	public void setBooPanelDatatable(boolean booPanelDatatable) {
		this.booPanelDatatable = booPanelDatatable;
	}

	public boolean isBooRefundPanel() {
		return booRefundPanel;
	}
	public void setBooRefundPanel(boolean booRefundPanel) {
		this.booRefundPanel = booRefundPanel;
	}

	public Collect getCollect() {
		return collect;
	}
	public void setCollect(Collect collect) {
		this.collect = collect;
	}

	public CollectDetail getCollectDetail() {
		return collectDetail;
	}
	public void setCollectDetail(CollectDetail collectDetail) {
		this.collectDetail = collectDetail;
	}

	public ForeignCurrencyAdjust getForeignCurrencyAdjust() {
		return foreignCurrencyAdjust;
	}
	public void setForeignCurrencyAdjust(ForeignCurrencyAdjust foreignCurrencyAdjust) {
		this.foreignCurrencyAdjust = foreignCurrencyAdjust;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public int getPurposeId() {
		return purposeId;
	}
	public void setPurposeId(int purposeId) {
		this.purposeId = purposeId;
	}

	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public String getMsgs() {
		return msgs;
	}
	public void setMsgs(String msgs) {
		this.msgs = msgs;
	}

	public CustomerIdProof getCustomerIdProof() {
		return customerIdProof;
	}
	public void setCustomerIdProof(CustomerIdProof customerIdProof) {
		this.customerIdProof = customerIdProof;
	}

	public CurrencyMaster getCurrencyMaster() {
		return currencyMaster;
	}
	public void setCurrencyMaster(CurrencyMaster currencyMaster) {
		this.currencyMaster = currencyMaster;
	}

	public BankMaster getBankMaster() {
		return bankMaster;
	}
	public void setBankMaster(BankMaster bankMaster) {
		this.bankMaster = bankMaster;
	}

	public CurrencyWiseDenomination getDenominationMaster() {
		return denominationMaster;
	}
	public void setDenominationMaster(CurrencyWiseDenomination denominationMaster) {
		this.denominationMaster = denominationMaster;
	}

	public List<PurposeOfTransaction> getLstPurposeOfTransaction() {
		return lstPurposeOfTransaction;
	}
	public void setLstPurposeOfTransaction(List<PurposeOfTransaction> lstPurposeOfTransaction) {
		this.lstPurposeOfTransaction = lstPurposeOfTransaction;
	}

	public List<SourceOfIncomeDescription> getLstSourceOfIncome() {
		return lstSourceOfIncome;
	}
	public void setLstSourceOfIncome(List<SourceOfIncomeDescription> lstSourceOfIncome) {
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

	public BigDecimal getDenomIdCheckFor() {
		return denomIdCheckFor;
	}
	public void setDenomIdCheckFor(BigDecimal denomIdCheckFor) {
		this.denomIdCheckFor = denomIdCheckFor;
	}

	public BigDecimal getNeededPurchaseAmount() {
		return neededPurchaseAmount;
	}
	public void setNeededPurchaseAmount(BigDecimal neededPurchaseAmount) {
		this.neededPurchaseAmount = neededPurchaseAmount;
	}

	public List<PaymentModeDesc> getLstFetchAllPayMode() {
		return lstFetchAllPayMode;
	}
	public void setLstFetchAllPayMode(List<PaymentModeDesc> lstFetchAllPayMode) {
		this.lstFetchAllPayMode = lstFetchAllPayMode;
	}	

	public BigDecimal getColpaymentmodeId() {
		return colpaymentmodeId;
	}
	public void setColpaymentmodeId(BigDecimal colpaymentmodeId) {
		this.colpaymentmodeId = colpaymentmodeId;
	}

	public String getColpaymentmodeName() {
		return colpaymentmodeName;
	}
	public void setColpaymentmodeName(String colpaymentmodeName) {
		this.colpaymentmodeName = colpaymentmodeName;
	}

	public String getColpaymentmodeCode() {
		return colpaymentmodeCode;
	}
	public void setColpaymentmodeCode(String colpaymentmodeCode) {
		this.colpaymentmodeCode = colpaymentmodeCode;
	}

	public String getColBankCode() {
		return colBankCode;
	}
	public void setColBankCode(String colBankCode) {
		this.colBankCode = colBankCode;
	}

	public List<ViewBankDetails> getBankMasterList() {
		return bankMasterList;
	}
	public void setBankMasterList(List<ViewBankDetails> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

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
	public void fetchCurrencyList() {

		if(lstCurrencyList != null || !lstCurrencyList.isEmpty()){
			lstCurrencyList.clear();
		}

		try {
			List<CurrencyMaster> lstcurrency = getForeignCurrencyPurchaseService().getAllCurrency(sessionStateManage.getCountryId());
			if(lstcurrency != null && lstcurrency.size() != 0){
				List<CurrencyMaster> lstcurrencyPurchase = new ArrayList<CurrencyMaster>();
				for (CurrencyMaster currencyMaster : lstcurrency) {
					if(currencyMaster.getAllowFCPurchase() != null && currencyMaster.getAllowFCPurchase().equalsIgnoreCase(Constants.Yes)){
						lstcurrencyPurchase.add(currencyMaster);
					}
				}
				
				lstCurrencyList.addAll(lstcurrencyPurchase);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void oncellEditForRefund(ForeignLocalCurrencyDataTable bean) {
		String result = null;
		if(!bean.getQty().equals("") && bean.getQty()!=null) {
			result = String.valueOf(Double.parseDouble((bean.getItem()).toString()) * Double.parseDouble(bean.getQty()));
			BigDecimal total=((bean.getItem()).multiply(new BigDecimal(bean.getQty())));
			result=total.toString();
			bean.setPrice(new GetRound().round(Double.parseDouble(result), foreignLocalCurrencyDenominationService.getDecimalPerCountry(sessionStateManage.getCountryId())));
		}else{
			bean.setPrice("0");
		}

		// int serial, BigDecimal item, String qty, String price, int stock,
		// BigDecimal pkDenom, BigDecimal denominationId, BigDecimal currencyId,
		// String denominationDesc
		/* Responsible to keep sum of total amount of cash entered */
		double totalSum = 0;

		/* Responsible to calculate sum of entered cash amount */
		for (ForeignLocalCurrencyDataTable element : lstRefundData) {
			if (element.getPrice() != null && !element.getPrice().equalsIgnoreCase("") && element.getPrice().length() != 0) {
				totalSum = totalSum + Double.parseDouble(element.getPrice());
			}
		}

		/* setting the summation value in bean object */
		setTotalRefundLocalCashEntered(new GetRound().round(totalSum, foreignLocalCurrencyDenominationService.getDecimalPerCountry(sessionStateManage.getCountryId())));
	}

	/**
	 * 
	 * @return source of income
	 */
	public void fetchSourceOfIncomeList() {

		if(lstSourceOfIncome != null || !lstSourceOfIncome.isEmpty()){
			lstSourceOfIncome.clear();
		}

		try {
			List<SourceOfIncomeDescription> lstSourceOfIncomesDesc = getForeignCurrencyPurchaseService().getSourceofIncome(sessionStateManage.getLanguageId());
			if(lstSourceOfIncomesDesc != null && lstSourceOfIncomesDesc.size() != 0){
				lstSourceOfIncome.addAll(lstSourceOfIncomesDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return purpose of transaction
	 */
	public void fetchPurposeOfTransactionsList() {
		if(lstPurposeOfTransaction != null || !lstPurposeOfTransaction.isEmpty()){
			lstPurposeOfTransaction.clear();
		}

		try {
			List<PurposeOfTransaction> lstPurposeOfTransactions = getForeignCurrencyPurchaseService().getAllPurposeOfTransaction();
			if(lstPurposeOfTransactions != null && lstPurposeOfTransactions.size() != 0){
				lstPurposeOfTransaction.addAll(lstPurposeOfTransactions);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getCurrencyWiseDenominationList(AjaxBehaviorEvent event) {
		try {
			lstDenomination = getForeignCurrencyPurchaseService().getDenominationByCurrencyID(getCurrencyId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Currency calculation through no of notes
	 */
	public BigDecimal currencyCalculationByNotes() {

		purchaseAmount = denomination.multiply(noNotes);
		purchaseAmount = new BigDecimal(Constants.PURCHAGE_AMOUNT_FOR_FC);
		return purchaseAmount;
	}

	/***************************************************************** Date Calculation *****************************************************/
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

	public String getIdRef() {
		return idRef;
	}
	public void setIdRef(String idRef) {
		this.idRef = idRef;
	}

	public Boolean getBooReg() {
		return booReg;
	}
	public void setBooReg(Boolean booReg) {
		this.booReg = booReg;
	}

	public Boolean getBooFc() {
		return booFc;
	}
	public void setBooFc(Boolean booFc) {
		this.booFc = booFc;
	}

	public int getFinanceMonth() {
		return financeMonth;
	}
	public void setFinanceMonth(int financeMonth) {
		this.financeMonth = financeMonth;
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
			financeYearId = new BigDecimal(financialYearList.get(0).getFinancialYearID().toString());
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

	public ReceiptPayment getReceiptPayment() {
		return receiptPayment;
	}

	public void setReceiptPayment(ReceiptPayment receiptPayment) {
		this.receiptPayment = receiptPayment;
	}

	public List<UserFinancialYear> getFinancialYearList() {
		return financialYearList;
	}

	public void setFinancialYearList(List<UserFinancialYear> financialYearList) {
		this.financialYearList = financialYearList;
	}

	private int varToKeepSerial ;

	public int getVarToKeepSerial() {
		return varToKeepSerial;
	}

	public void setVarToKeepSerial(int varToKeepSerial) {
		this.varToKeepSerial = varToKeepSerial;
	}

	public int getDocumentSerialId() {
		int docNo=Integer.parseInt(getDocumentSerialID(Constants.Yes));
		setVarToKeepSerial(docNo);
		return docNo;
	}

	public void setDocumentSerialId(int documentSerialId) {
		this.documentSerialId = documentSerialId;
	}

	/**
	 * Responsible to Redirect page to search page, on Focus event of textField
	 * from page
	 * 
	 * @param event
	 */
	public void idOnFocus(AjaxBehaviorEvent event) {
		try {
			setBooReg(false);
			setBooFc(true);
			/* specialCustomerDealRequestBean.setBooSpCustomer(false); */
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../search/searchcustomer.xhtml");
		} catch (Exception e) {
			log.info("Problem to Redirect the page : " + e);
		}
	}

	/**
	 * Responsible to take care when search button is clicked
	 */
	public void searchClicked() {
		try {
			setBooReg(false);
			setBooFc(true);
			/*
			 * specialCustomerDealRequestBean.setBooSpCustomer(false);
			 * specialCustomerDealRequestBean.setIsFromSpecialCustomer(false);
			 * supplierBean.setBooSupplierBean(false);
			 * supplierBean.setIsFromSupplierCustref(true);
			 * searchCustomerManageBean.setBooSearchCustomer(false);
			 * searchCustomerManageBean.setBooPass(false);
			 * searchCustomerManageBean.setFinalData(null);
			 */

			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("request", "foreignCurrencyPurchase");
			setIsFromFCPurchage(true);
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../search/searchcustomer.xhtml");
		} catch (Exception e) {
			log.info("Problem to Redirect the page : " + e);
		}
	}

	/**
	 * Responsible to populate value on lost focus
	 * 
	 * @param event
	 */

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}
	
	public void idOnBlur(AjaxBehaviorEvent event) throws SQLException {
		String userType=null;
		setBooVisble(false);
		setTempErrorMessage(null);
		List<CustomerIdProof> data = null;
		if((sessionStateManage.getBranchId()!=null || sessionStateManage.getCustomerType().equals("E")) && sessionStateManage.getRoleId().equals("1")){
			userType ="BRANCH"; 
		}else if(sessionStateManage.getBranchId()!=null  &&  sessionStateManage.getUserType().equalsIgnoreCase("K")){
			userType ="KIOSK";
		}else
		{
			//userType ="ONLINE";
		}
		
		if(getId() != null && !getId().equalsIgnoreCase("")){
			//data = getForeignCurrencyPurchaseService().dataCust(getId().trim());
			data = getForeignCurrencyPurchaseService().fetchCustomerByIdentityInt(getId().trim());
		}

		if (data != null && data.size() > 0) {
			setCustomerId(data.get(0).getFsCustomer().getCustomerId());
			
			log.info("getCustomerNo() :"+getCustomerId()+"\t sessionStateManage.getCountryId():"+sessionStateManage.getCountryId()+"\t sessionStateManage.getUserName() :"+sessionStateManage.getUserName());
			HashMap<String,String> customerValiMessage = iPersonalRemittanceService.getValidateCustomerProcedure(sessionStateManage.getCountryId(), getCustomerId(),sessionStateManage.getUserName(),userType);
			log.info("customerValiMessage :"+customerValiMessage);
			log.info("INDICATOR===="+customerValiMessage.get( "INDICATOR"));

			if(customerValiMessage.get( "ERROR_MESSAGE")!=null){
				setName("");
				setMobile("");
				setIdRef("");
				setId("");
				setErrmsg(customerValiMessage.get("ERROR_MESSAGE"));
				RequestContext.getCurrentInstance().execute("err.show();");
				return;
			}else if(customerValiMessage!=null&&customerValiMessage.get( "INDICATOR")!=null ){
				setName("");
				setMobile("");
				setIdRef("");
				setId("");
				setErrmsg(customerValiMessage.get( "ERROR_MESSAGE"));
				if(customerValiMessage.get("INDICATOR").equalsIgnoreCase( Constants.Yes)){
					RequestContext.getCurrentInstance().execute("customerregproceed.show();");
				}
			}else{
				setBooVisble(false);
				setTempErrorMessage(null);
				String customerName = nullCheck(data.get(0).getFsCustomer().getFirstName()) + " " + nullCheck(data.get(0).getFsCustomer().getMiddleName()) + " " + nullCheck(data.get(0).getFsCustomer().getLastName());
				setName(customerName);
				setCustomerId(data.get(0).getFsCustomer().getCustomerId());
				setMobile(data.get(0).getFsCustomer().getMobile());
				if(data.get(0).getFsCustomer().getSignatureSpecimenClob() != null){
					setSignatureSpecimen(data.get(0).getFsCustomer().getSignatureSpecimenClob().getSubString(1,(int) data.get(0).getFsCustomer().getSignatureSpecimenClob().length()));
				}

				if(data.get(0).getFsCustomer().getCustomerReference() !=null){
					setIdRef(String.valueOf(data.get(0).getFsCustomer().getCustomerReference()));
				}
				
				// due to currency denomination issue blocked
				/*BigDecimal currencyId = iPersonalRemittanceService.getCurrencyId(data.get(0).getFsCustomer().getFsCountryMasterByNationality().getCountryId());
				setCurrencyId(currencyId);
				getDenominationTable(event);*/
				
					
			}
			
		} else {
			setName("");
			setMobile("");
			setIdRef("");
			setId("");
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("idNotF.show();");
		}
	}
	
	/*public void denominationTableVisible(){
		if(getCurrencyId().equals(0)){
			getDenominationTable(null);
		}
	}*/

	public void getDenominationTable(AjaxBehaviorEvent event) {
		try {
			setFocus(false);
			denominationBeanList.clear();
			setNeededPurchaseAmount(BigDecimal.ZERO);
			//BigDecimal saleCurrencyId = new BigDecimal(sessionStateManage.getCurrencyId());
			BigDecimal maxExchangeRate = BigDecimal.ZERO;
			DenominationBean denominationBean = null;
			List<CashRate> roleWiseExchangeRates = getForeignCurrencyPurchaseService().getRoleWiseExchangeRateByRoleId(new BigDecimal(sessionStateManage.getBranchId()),currencyId);

			for (CashRate cash : roleWiseExchangeRates) {

				maxExchangeRate = cash.getBuyRateMax();
			}

			List<CurrencyWiseDenomination> currencyWiseDenomination = getForeignCurrencyPurchaseService().getDenominationByCurrencyID(currencyId);
			for (CurrencyWiseDenomination currencyWiseDenominationobj : currencyWiseDenomination) {
				denominationBean = new DenominationBean(currencyWiseDenominationobj.getDenominationAmount(), "", maxExchangeRate, Integer.parseInt(currencyWiseDenominationobj.getDenominationId().toString()), currencyWiseDenominationobj.getDenominationDesc(),
						currencyWiseDenominationobj.getDenominationAmount(), currencyWiseDenominationobj.getExCurrencyMaster().getCurrencyName());

				denominationBeanList.add(denominationBean);
			}

			// Reseting TotalPurchaseAmount and SalesAmount acc to purchase
			// currency @ Chiranjeevi
			setTotalPurchaseAmount(BigDecimal.ZERO);
			setTempSaleAmount("0.0");
			setSaleAmount("0.0");

			getForeignCurrencyName();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param event
	 *            table value change event for validation and immediate data
	 *            change
	 */
	public void onCellEdit(DenominationBean denominationBean) {
		if(getNeededPurchaseAmount() != null && getNeededPurchaseAmount() != null){
			if(getBooVisble()!=null && getBooVisble())
			{
				denominationBean.setNoOfNotes("");
				setErrmsg(getTempErrorMessage());
				RequestContext.getCurrentInstance().execute("err.show();");
				return;
			}

			System.out.println("getBooVisble :"+getBooVisble()+"\t No of Notes :"+denominationBean.getNoOfNotes());
			String purchaseAmount="";
			// Decimal is according to currency id in purchaseAmount
			if(denominationBean.getNoOfNotes()!=null && !denominationBean.getNoOfNotes().equals("")){
				purchaseAmount = new GetRound().round(Double.parseDouble(denominationBean.getDenominationName().toString()) *  Double.parseDouble(denominationBean.getNoOfNotes()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));
			}else{
				purchaseAmount="0";
			}
			// Decimal is according to Country id in saleAmount
			String saleAmount = new GetRound().round(Double.parseDouble(purchaseAmount) * Double.parseDouble(denominationBean.getExchangeRate().toString()), foreignLocalCurrencyDenominationService.getDecimalPerCountry(sessionStateManage.getCountryId()));

			denominationBean.setPurchaseAmount(new BigDecimal(purchaseAmount));
			denominationBean.setSalesAmount(new BigDecimal(saleAmount));
			
			averageRate();
			fetchTotalPurchaseAmount();
			fetchTotalTempSaleAmount();
			
			if(getTotalPurchaseAmount() != null && getNeededPurchaseAmount() != null){
				if(getTotalPurchaseAmount().compareTo(getNeededPurchaseAmount()) >= 0){
					System.out.println("greater and equal");
					roundedTotalSaleAmountFunction();
				}else{
					System.out.println("less and not equal");
					setSaleAmount(getTempSaleAmount());
					roundedTotalSaleAmountFunction();
				}
			}
			
		}else{
			denominationBean.setNoOfNotes("");
			setErrmsg("Please Enter Purchase Amount");
			RequestContext.getCurrentInstance().execute("err.show();");
		}
	}
	
	public void fetchTotalTempSaleAmount(){
		Double value = 0.0;
		for (DenominationBean denominationBean : denominationBeanList) {
			if (denominationBean.getNoOfNotes() != null && !denominationBean.getNoOfNotes().equals("") && !denominationBean.getNoOfNotes().equals("0")) {
				value += Double.parseDouble(denominationBean.getDenominationName().toString()) * Double.parseDouble(denominationBean.getNoOfNotes()) * Double.parseDouble(denominationBean.getExchangeRate().toString());
			}
		}
		
		if(value != null && value != 0){
			setTempSaleAmount(new GetRound().round(value, foreignLocalCurrencyDenominationService.getDecimalPerCountry(sessionStateManage.getCountryId())));
		}
	}
	
	public void fetchTotalPurchaseAmount(){
		setTotalPurchaseAmount(null);
		BigDecimal totalPurAmt = BigDecimal.ZERO;
		for (DenominationBean denominationBean : denominationBeanList) {
			if(denominationBean.getNoOfNotes()!=null && !denominationBean.getNoOfNotes().equals("") && !denominationBean.getNoOfNotes().equals("0")){
				//totalPurAmt += Double.parseDouble(denominationBean.getDenominationName().toString()) *  Double.parseDouble(denominationBean.getNoOfNotes());
				totalPurAmt = totalPurAmt.add(denominationBean.getDenominationName().multiply(new BigDecimal(denominationBean.getNoOfNotes())));
			}
		}
		
		if(totalPurAmt != null && totalPurAmt.compareTo(BigDecimal.ZERO) != 0){
			setTotalPurchaseAmount(totalPurAmt);
		}
	}



	Boolean booVisble ;
	String tempErrorMessage;




	public String getTempErrorMessage() {
		return tempErrorMessage;
	}

	public void setTempErrorMessage(String tempErrorMessage) {
		this.tempErrorMessage = tempErrorMessage;
	}

	public Boolean getBooVisble() {
		return booVisble;
	}

	public void setBooVisble(Boolean booVisble) {
		this.booVisble = booVisble;
	}

	public void onCellEditExchangeRate(DenominationBean denominationBean) {

		setBooVisble(false);
		setTempErrorMessage(null);
		setErrMsg(null);
		// Decimal is according to currency id in purchaseAmount
		//String purchaseAmount = new GetRound().round(Double.parseDouble(denominationBean.getDenominationName().toString()) * denominationBean.getNoOfNotes(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));


		String purchaseAmount="";
		// Decimal is according to currency id in purchaseAmount
		if(denominationBean.getNoOfNotes()!=null && !denominationBean.getNoOfNotes().equals("")){
			purchaseAmount = new GetRound().round(Double.parseDouble(denominationBean.getDenominationName().toString()) *  Double.parseDouble(denominationBean.getNoOfNotes()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getCurrencyId()));
		}else{
			purchaseAmount="";
		}



		// Decimal is according to Country id in saleAmount
		String saleAmount = new GetRound().round(Double.parseDouble(purchaseAmount) * Double.parseDouble(denominationBean.getExchangeRate().toString()), foreignLocalCurrencyDenominationService.getDecimalPerCountry(sessionStateManage.getCountryId()));

		denominationBean.setPurchaseAmount(new BigDecimal(purchaseAmount));
		denominationBean.setSalesAmount(new BigDecimal(saleAmount));
		//denominationBean.setExchangeRate(denominationBean.getExchangeRate());

		//if(denominationBean.getNoOfNotes()!=0 )
		if(denominationBean.getNoOfNotes()!=null && !denominationBean.getNoOfNotes().equals("") && !denominationBean.getNoOfNotes().equals("0"))
		{
			List<String> result = foreignCurrencyPurchaseService.getFsAvg(denominationBean.getExchangeRate(), getCurrencyId());

			if(result==null)
			{
				RequestContext.getCurrentInstance().execute("err.show();");
				setErrmsg("Exchange Rate not available for this currecny ");
				return;
			}

			if(result.get(0).equalsIgnoreCase("Y")){
				setBooVisble(true);
				/* FacesMessage msg = new FacesMessage("Rate Should be Between"+ result.get(1) +" and "+result.get(2), ""
	        	 		+ "Rate Should be Between"+ result.get(1) +" and "+result.get(2));
				 msg.setSeverity(FacesMessage.SEVERITY_ERROR);*/
				// throw new ValidatorException(msg);
				denominationBean.setExchangeRate(denominationBean.getExchangeRate());
				setErrmsg("Rate Should be Between"+ result.get(1) +" and "+result.get(2));
				setTempErrorMessage("Rate Should be Between"+ result.get(1) +" and "+result.get(2));
				RequestContext.getCurrentInstance().execute("err.show();");
				return;

				// denominationBean.setExchangeRate(denominationBean.getExchangeRate());
				//denominationBean.setSalesAmount(null);*/
			} 
		}


	}

	public void ClickForLocalCurrrency() throws IOException {
		if (getLstData() != null) {
			getLstData().clear();
		}
		// FacesContext.getCurrentInstance().getExternalContext().redirect("foreignLocalCurrencyDenomination.xhtml?totalSale="
		// + getTotalSale());
		FacesContext.getCurrentInstance().getExternalContext().redirect("foreignLocalCurrencyDenomination.xhtml");
	}

	/******************************************* Local Currency Manage ***************************************************/

	/* Responsible to show total amount of entered cash */
	private String totalValue = null;

	/* Responsible to control all data in Data table */
	private ArrayList<ForeignLocalCurrencyDataTable> lstData = new ArrayList<ForeignLocalCurrencyDataTable>();
	private ArrayList<ForeignLocalCurrencyDataTable> lstRefundData = new ArrayList<ForeignLocalCurrencyDataTable>();

	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	public String getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

	public ArrayList<ForeignLocalCurrencyDataTable> getLstData() {
		return lstData;
	}
	public void setLstData(ArrayList<ForeignLocalCurrencyDataTable> lstData) {
		this.lstData = lstData;
	}

	public ArrayList<ForeignLocalCurrencyDataTable> getLstRefundData() {
		return lstRefundData;
	}
	public void setLstRefundData(ArrayList<ForeignLocalCurrencyDataTable> lstRefundData) {
		this.lstRefundData = lstRefundData;
	}

	/**
	 * Responsible to hold datatable data, and send it back when called from
	 * page
	 * 
	 * @return
	 */

	public void fetchLstData() {

		/* Checking that it's first time or not, first time list size will be 0 */
		double sAmount = 0;
		sAmount = Double.parseDouble(getSaleAmount());
		double saleAmt = 0.0;
		setTotalValue("0");
		if (lstData.size() == 0) {
			/* Responsible to show serial number in data table */
			int i = 0;
			/*
			 * Responsible to hold each row in different bean object of data table
			 */
			ForeignLocalCurrencyDataTable item = null;

			List<Stock> dataFromDb = foreignLocalCurrencyDenominationService.getLocalCurrencyDenominationFromStock(sessionStateManage.getCountryId(), sessionStateManage.getUserName(), sessionStateManage.getBranchId(), sessionStateManage.getCompanyId(), sessionStateManage.getCurrencyId());
			saleAmt = sAmount;
			/* putting the value in list to show in data table */

			boolean deFalg = false;
			for (Stock element : dataFromDb) {
				int stock = element.getOpenQuantity() + element.getPurchaseQuantity() + element.getReceivedQuantity() - (element.getSaleQuantity() + element.getTransactionQuantity());

				String qty = null;
				double count = 0, totalNotes = 0;
				// double sAmount = 0;
				double denamination = 0;
				String amount = null;
				double result = 0.0;

				int cal = 0;

				if (stock > 0) {
					denamination = Double.parseDouble(element.getDenominationId().getDenominationAmount().toString());

					if (denamination < sAmount) {
						count = sAmount / denamination;
					}
					totalNotes = totalNotes + count;
					sAmount = sAmount % denamination;

					/*if (count != 0) {

						qty = new Integer((int) Math.ceil(count)).toString();
						cal = new Integer((int) Math.ceil(count));
						result = cal * denamination;
						log.info("result ----------- > " + result);
						if (result == new Integer((int) Math.ceil(saleAmt))) {
							amount = new Integer((int) Math.ceil(result)).toString();

						} else {
							if ((result - 1) == (new Integer((int) Math.ceil(saleAmt)))) {
								amount = new Integer((int) Math.ceil(result)).toString();
							} else {
								qty = "";

								amount = "0";
							}

						}

					} else {
						qty = "";
						amount = "0";
					}*/

					item = new ForeignLocalCurrencyDataTable(++i, element.getDenominationId().getDenominationAmount(), qty, amount, stock, element.getStockId(), element.getDenominationId().getDenominationId(), element.getDenominationId().getExCurrencyMaster().getCurrencyId(), element
							.getDenominationId().getDenominationDesc(),element.getDenominationId().getDenominationAmount());

					lstData.add(item);

				}// if(deFalg) break;
			}
		}
	}

	private String validNoOFQuantity = null;

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
				FacesContext.getCurrentInstance().getExternalContext().redirect("../foreigncurrency/foreigncurrencypurchase.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



	/* Custom method */
	public void oncellEditForeign(ForeignLocalCurrencyDataTable bean) {

		try {

			if (bean.getQty()!=null && !bean.getQty().equals("") && Integer.parseInt(bean.getQty()) > bean.getStock()) {
				bean.setQty("");
				bean.setPrice("");
				setValidNoOFQuantity(String.valueOf(bean.getStock()));
				RequestContext.getCurrentInstance().execute("invalidNoOFNotes.show();");
				return;
			} else {
				setDenomIdCheckFor(bean.getPkDenom());

				String result = null;

				if(!bean.getQty().equals("") && bean.getQty()!=null) {
					result = String.valueOf(Double.parseDouble((bean.getItem()).toString()) * Double.parseDouble(bean.getQty()));
					BigDecimal total=((bean.getItem()).multiply(new BigDecimal(bean.getQty())));
					result=total.toString();
					bean.setPrice(new GetRound().round(Double.parseDouble(result), foreignLocalCurrencyDenominationService.getDecimalPerCountry(sessionStateManage.getCountryId())));
				}else{
					bean.setPrice("0");
				}

				//we are changed int to bigdecimal bcoz we need to calculate Decimal values also 
				/* Responsible to keep sum of total amount of cash entered */
				BigDecimal totalSum= new BigDecimal(0);
				/* Responsible to calculate sum of entered cash amount */
				for (ForeignLocalCurrencyDataTable element : lstData) {
					if (element.getPrice() != null && !element.getPrice().equalsIgnoreCase("") && element.getPrice().length() != 0) {
						//totalSum = totalSum + Integer.parseInt(element.getPrice());
						BigDecimal valueOfPrice=new BigDecimal(element.getPrice());
						totalSum=totalSum.add(valueOfPrice);
					}
				}

				/* setting the summation value in bean object */
				setTotalValue(String.valueOf(totalSum));
				
				// render Save or Next button
				if (Double.parseDouble(getTotalValue()) <= Double.parseDouble(String.valueOf(getSaleAmount()))) {
					setSaveOrNext(Constants.Save);
				}else{
					setSaveOrNext(Constants.Next);
				}
				
			}
		}catch (Exception e) {
			bean.setQty("");
			bean.setPrice("");
			setValidNoOFQuantity(String.valueOf(bean.getStock()));
			RequestContext.getCurrentInstance().execute("invalidNoOFNotes.show();");
			return;
		}
	}

	/**
	 * Responsible to take care when accept button is pressed from Purchase
	 * panel
	 */
	public void acceptFromPurchase() {
		System.out.println("getErrmsg() :"+getErrmsg()+"\t getTempErrorMessage():"+getTempErrorMessage());

		try{
			setFocus(true);
			if(getErrmsg()!=null && getTempErrorMessage()!=null){
				RequestContext.getCurrentInstance().execute("err.show();");
				return;
			}
			setDigitalSignature(null);
			setSaveOrNext(Constants.Save);
			if(lstData != null || !lstData.isEmpty()){
				lstData.clear();
			}

			if (getTotalPurchaseAmount() != null && getNeededPurchaseAmount() != null && getNeededPurchaseAmount().compareTo(getTotalPurchaseAmount()) == 0 ) {
				
				// checking conditions of PaymentModes
				if(getColpaymentmodeCode() != null && getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)){
					// cash product
					// cash stock payable
					fetchLstData();
					setBooSecondPanelButtons(true);
					setBooSecondPanelDenoDatatable(true);
				}else if(getColpaymentmodeCode() != null && getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)){
					// Bank Cheque
					setBooSecondPanelButtons(true);
					setBooSecondPanelDenoDatatable(false);
				}else if(getColpaymentmodeCode() != null && getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)){
					// Bank Transfer
					setBooSecondPanelButtons(true);
					setBooSecondPanelDenoDatatable(false);
				}else{
					// not possible
					setBooSecondPanelButtons(true);
					setBooSecondPanelDenoDatatable(true);
				}
				
				setBooPurchasePanel(false);
				setBooPanelDatatable(true);
				setBooRefundPanel(false);
				setSignaturePanel(true);
				setSignatureSpecimen(getSignatureSpecimen());
				setForNextPanels(true);
				setDigitalSignature(null);
				// render signature 
				renderDigitalSignatureBasedOnBranch();
			} else {
				RequestContext.getCurrentInstance().execute("dlgSaleCannotZero.show();");
				return;
			}
		}catch(Exception e){
			setErrmsg(null);
			setTempErrorMessage(null);
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("err.show();");
			return;
		}

	}

	/**
	 * Back from Sale panel
	 */
	public void backFromSalePanel() {

		setFocus(true);
		setFcPurchageLabel(true);
		setLocalcashpaidtoCustomerLabel(false);
		setBooPurchasePanel(true);
		setBooPanelDatatable(false);
		setBooRefundPanel(false);
		setForNextPanels(false);
		setSignaturePanel(false);

		lstData.clear();
	}

	/**
	 * Save pressed from sale panel
	 * getInsertEmosFcPurchase
	 */
	public void saveFromSalePanel() {
		setFocus(true);

		System.out.println("signature :" + getSignature() + "\t Get Sig :" + getSignatureSpecimen() + "\t digitalSignature :" + digitalSignature);

		System.out.println("getTotalValue() :" + getTotalValue() + "\t getSaleAmount() :" + getSaleAmount());
		
		// checking conditions of PaymentModes
		if(getColpaymentmodeCode() != null && getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)){
			// cash product
			if (Double.parseDouble(getTotalValue()) == Double.parseDouble(String.valueOf(getSaleAmount()))) {
				boolean checkLimit = roleWiseLimit();
				if (checkLimit) {
					RequestContext.getCurrentInstance().execute("limitValidation.show();");
				} else {

					if (isSignaturePanel() && (digitalSignature == null || digitalSignature.equals(""))) {
						setErrmsg("Signature is Mandatory,Please sign and continue");
						RequestContext.getCurrentInstance().execute("err.show();");
						return;
					} else {

						try {
							try {
								saveAll();
							} catch (Exception e) {
								e.printStackTrace();
								setErrMsg(null);
								setErrmsg("SAVE ALL :" + e.getMessage());
								RequestContext.getCurrentInstance().execute("err.show();");
								return;
							}

							clear();
							setFcPurchageLabel(false);
							setLocalcashpaidtoCustomerLabel(false);
							setBooRenderFirstPanel(false);
							setBooPurchasePanel(false);

							setSuccessPanel(true);

							String outMsg = getInsertEmosFcPurchase();
							if (outMsg != null) {
								setErrmsg(null);
								setErrmsg("EX_INSERT_EMOS_FC_PURCHASE " + outMsg);
								RequestContext.getCurrentInstance().execute("err.show();");
								return;
							}

						} catch (AMGException e) {
							setErrmsg(null);
							setErrmsg("EX_INSERT_EMOS_FC_PURCHASE " + e.getMessage());
							RequestContext.getCurrentInstance().execute("err.show();");
							return;
						}

					}
				}

			} else if (Double.parseDouble(getTotalValue()) - Double.parseDouble(getSaleAmount()) < 0.0) {
				if (isSignaturePanel() && (digitalSignature == null || digitalSignature.equals(""))) {
					setErrmsg("Signature is Mandatory,Please sign and continue");
					RequestContext.getCurrentInstance().execute("err.show();");
					return;
				} else{
					RequestContext.getCurrentInstance().execute("showValid.show();");
					return;
				}
			} else {
				if (isSignaturePanel() && (digitalSignature == null || digitalSignature.equals(""))) {
					setErrmsg("Signature is Mandatory,Please sign and continue");
					RequestContext.getCurrentInstance().execute("err.show();");
					return;
				} else{
					
					if(lstRefundData != null || !lstRefundData.isEmpty()){
						lstRefundData.clear();
					}
					setTotalRefundLocalCashEntered("0");
					
					// collect cash product stock
					fetchLstRefundData();
					
					setTotalRefundCashRecipt(new GetRound().round(Double.parseDouble(getTotalValue()) - Double.parseDouble(getSaleAmount()), foreignLocalCurrencyDenominationService.getDecimalPerCountry(sessionStateManage.getCountryId())));
					setFcPurchageLabel(false);
					setLocalcashpaidtoCustomerLabel(true);
					setBooPurchasePanel(false);
					setBooPanelDatatable(false);
					setBooRefundPanel(true);
					/*if (digitalSignature != null) {
						setSignaturePanel(false);
					} else {
						setSignaturePanel(true);
					}*/
					setSignatureSpecimen(getSignatureSpecimen());
					setForNextPanels(true);
				}
			}
		}else{
			// Bank Cheque and Bank Transfer
			boolean checkLimit = roleWiseLimit();
			if (checkLimit) {
				RequestContext.getCurrentInstance().execute("limitValidation.show();");
			} else {

				if (isSignaturePanel() && (digitalSignature == null || digitalSignature.equals(""))) {
					setErrmsg("Signature is Mandatory,Please sign and continue");
					RequestContext.getCurrentInstance().execute("err.show();");
					return;
				} else {
					try {
						try {
							saveAll();
						} catch (Exception e) {
							e.printStackTrace();
							setErrMsg(null);
							setErrmsg("SAVE ALL :" + e.getMessage());
							RequestContext.getCurrentInstance().execute("err.show();");
							return;
						}

						clear();
						setFcPurchageLabel(false);
						setLocalcashpaidtoCustomerLabel(false);
						setBooRenderFirstPanel(false);
						setBooPurchasePanel(false);

						setSuccessPanel(true);

						String outMsg = getInsertEmosFcPurchase();
						if (outMsg != null) {
							setErrmsg(null);
							setErrmsg("EX_INSERT_EMOS_FC_PURCHASE " + outMsg);
							RequestContext.getCurrentInstance().execute("err.show();");
							return;
						}

					} catch (AMGException e) {
						setErrmsg(null);
						setErrmsg("EX_INSERT_EMOS_FC_PURCHASE " + e.getMessage());
						RequestContext.getCurrentInstance().execute("err.show();");
						return;
					}
				}

			}
		}
	}

	/**
	 * Added by Rabil	
	 */
	public void saveAll() throws Exception{

		try {

			//Document document = new Document();
			BigDecimal docId = iGeneralService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")).get(0).getDocumentID();
			if(docId != null){
				setDocumentIdPk(docId);
			}

			String saveDocumentSerialID = getDocumentSerialID(Constants.U);
			setDocSerialIdNumberForSave(saveDocumentSerialID);		

			HashMap<String, Object> returnResult 			 = saveCollectionList();
			Collect  	collect								 =(Collect)returnResult.get("collect");
			List<CollectDetail> collectionDetailList 		 =  saveCollectionDetailList(collect);
			List<ForeignCurrencyAdjust> foreignCurrencyAdjustList = saveForeignCurrecnyList(collect);
			
			List<Payment> paymentRefundData = savePaymentRecord(collect);
			
			//List<ReceiptPayment> listReceiptPmt				 =savePaymentReceiptList();
			ReceiptPayment receiptPayment = savePaymentReceiptList(collect);

			BigDecimal collectionId = new BigDecimal(0);
			try {		
				collectionId = getForeignCurrencyPurchaseService().saveCollectCollecDetailCurrAdjustAndFinalReceipt(collect, collectionDetailList, foreignCurrencyAdjustList, receiptPayment, paymentRefundData);
				setPkCollect(collectionId);
				log.info(" collection id is "+ collectionId);
			} catch (Exception e2) {
				setErrmsg("Exception occured while saving collection and details " + e2.getMessage());
				log.info("Exception occured while saving collection and details "+e2);
				RequestContext.getCurrentInstance().execute("err.show();");
				return;
			}
		}catch(Exception e){
			e.printStackTrace();
		}


	}


	public String getInsertEmosFcPurchase() throws AMGException{
		String outMsg =  getForeignCurrencyPurchaseService().getInsertEmosFcPurchase(sessionStateManage.getCountryId(),new BigDecimal(sessionStateManage.getSessionValue("companyId")),new BigDecimal(documentId), new BigDecimal(getFinaceYear()),new BigDecimal(getDocSerialIdNumberForSave()));
		return outMsg;
	} 



	/*
	public boolean limitYes() {
		saveForeignCurrencyPurchase();
		// responsible to update closing stock
		updateStock();

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("complete.show();");
		return true;
	}
	 */
	public boolean limitNo() {
		// clearCache();
		return false;
	}

	public void saveFromRefundPanel() {

		setFocus(true);
		if (getTotalRefundLocalCashEntered() != null && getTotalRefundCashRecipt() != null) {

			if (Double.parseDouble(getTotalRefundLocalCashEntered()) < Double.parseDouble(getTotalRefundCashRecipt())) {
				RequestContext.getCurrentInstance().execute("dlgRefundCashMessage.show();");
				return;
			}else if (Double.parseDouble(getTotalRefundLocalCashEntered()) > Double.parseDouble(getTotalRefundCashRecipt())) {
				RequestContext.getCurrentInstance().execute("dlgTotalRefundMessage.show();");
				return;
			} else {
				if (isSignaturePanel() && (digitalSignature == null || digitalSignature.equals(""))) {
					setErrmsg("Signature is Mandatory,Please sign and continue");
					RequestContext.getCurrentInstance().execute("err.show();");
					return;
				} else {

					try {
						log.info("::::::::::::::::::::::::::::::::::::: Called for save :::::::::::::::::::::::::::::::");

						try {
							saveAll();
						} catch (Exception e) {
							e.printStackTrace();
							setErrMsg(null);
							setErrmsg("SAVE ALL :" + e.getMessage());
							RequestContext.getCurrentInstance().execute("err.show();");
							return;
						}
						
						clear();
						setFcPurchageLabel(false);
						setLocalcashpaidtoCustomerLabel(false);
						setBooRenderFirstPanel(false);
						setBooPurchasePanel(false);
						setSuccessPanel(true);

						log.info(":::::::::::::::::::::::::::::::: Saved Successfull :::::::::::::::::::::::::::::::::::::::::::::::::::::");

						String outMsg = getInsertEmosFcPurchase();
						if (outMsg != null) {
							setErrMsg(null);
							setErrmsg("EX_INSERT_EMOS_FC_PURCHASE " + outMsg);
							RequestContext.getCurrentInstance().execute("err.show();");
							return;
						}

					} catch (AMGException e) {
						setErrMsg(null);
						setErrmsg("EX_INSERT_EMOS_FC_PURCHASE " + e.getMessage());
						RequestContext.getCurrentInstance().execute("err.show();");
						return;
					}

				}

			}
		}

	}

	public void updateRefund() {
		foreignLocalCurrencyDenominationService.updateRefund(getPkCollect(), getTotalRefundLocalCashEntered(), sessionStateManage.getUserName());
	}

	public void updateStock() {
		log.info("Called::::::::::::::::::::::::::::::::::::::::: Update Stock"+lstData.size());
		for (ForeignLocalCurrencyDataTable element : lstData) {
			if(element.getQty()!=null && !element.getQty().equals("") && !element.getQty().equals("0")){
				foreignLocalCurrencyDenominationService.updateStock(element.getPkDenom(), Integer.parseInt(element.getQty()), sessionStateManage.getUserName());
			}
		}
	}

	/**
	 * Back from refund
	 */
	public void backFromRefund() {
		setFocus(true);
		setFcPurchageLabel(true);
		setLocalcashpaidtoCustomerLabel(false);
		setBooPurchasePanel(false);
		setBooPanelDatatable(true);
		setBooRefundPanel(false);
		setSignaturePanel(false);
		//setSignaturePanel(true);
		/*// clear stock
		setTotalValue("0");
		if(lstData != null || !lstData.isEmpty()){
			lstData.clear();
		}*/
	}

	/**
	 * Responsible to calculate sale amount in purchase panel
	 * 
	 * @return
	 */
	public String getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(String saleAmount) {
		this.saleAmount = saleAmount;
	}
	
	public String getTempSaleAmount() {
		return tempSaleAmount;
	}
	public void setTempSaleAmount(String tempSaleAmount) {
		this.tempSaleAmount = tempSaleAmount;
	}
	
	public String getSaleCurrencyCode() {
		return saleCurrencyCode;
	}
	public void setSaleCurrencyCode(String saleCurrencyCode) {
		this.saleCurrencyCode = saleCurrencyCode;
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
	 * A
	 * @return : Added by Rabil
	 */

	public HashMap<String, Object> saveCollectionList() {
		Collect collect = new Collect();
		HashMap<String, Object> returnResult = new HashMap<String, Object>();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		String date = getCurrentDateWithFormat();
		Date acc_Month = null;
		try{

			acc_Month = DATE_FORMAT.parse(date);			
			// Company save
			companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
			collect.setFsCompanyMaster(companyMaster);
			log.info("saleAmount  ..." + getSaleAmount());
			// customer Save
			//collect.setDocumentNo(new BigDecimal(documentSerialId));
			collect.setDocumentNo(new BigDecimal(getDocSerialIdNumberForSave()));
			collect.setDocumentId(getDocumentIdPk());
			
			/*if(getCustomerId() != null){
				customer = new Customer();
				customer.setCustomerId(getCustomerId());
				collect.setFsCustomer(customer);
			}*/
			
			customer = new Customer();
			customer.setCustomerId(getCustomerId());
			collect.setFsCustomer(customer);
			
			collect.setDocumentFinanceYear(new BigDecimal(finaceYear));

			CountryBranch countryBranch = new CountryBranch();
			countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
			collect.setExBankBranch(countryBranch);
			collect.setApplicationCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));

			collect.setPaidAmount(getTotalPurchaseAmount()); // fc Amount
			collect.setRefoundAmount(BigDecimal.ZERO); // no refund is possible for FC Purchase
			collect.setNetAmount(getTotalPurchaseAmount()); // fc Amount
			
			//collect.setPaidAmount(new BigDecimal(getTotalValue()==null ? getSaleAmount() : getTotalValue())); // Local currency sale amount 
			/*if (getTotalRefundLocalCashEntered() != null && Double.parseDouble(getTotalRefundLocalCashEntered()) > 0) {
				collect.setRefoundAmount(new BigDecimal(getTotalRefundLocalCashEntered()));
				collect.setNetAmount(new BigDecimal(getSaleAmount()));
			} else {
				collect.setRefoundAmount(new BigDecimal(0.0));
				//collect.setNetAmount(new BigDecimal(Double.parseDouble(String.valueOf(getTotalPurchaseAmount())) - 0.0));
				collect.setNetAmount(new BigDecimal(getSaleAmount()));
			}*/


			collect.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE));
			collect.setIsActive(Constants.Yes);

			collect.setAccountMMYYYY(acc_Month);

			collect.setGeneralLegerDate(new Date());
			collect.setCollectDate(new Date());
			// Currency Insert
			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(getCurrencyId());
			collect.setExCurrencyMaster(currencyMaster);

			collect.setCreatedBy(sessionStateManage.getSessionValue("userName"));
			collect.setCreatedDate(new Date());
			
			List<CompanyMasterDesc> companyCode = iGeneralService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
			if(companyCode != null && !companyCode.isEmpty() && companyCode.get(0).getFsCompanyMaster() != null){
				BigDecimal companyCodeValue = companyCode.get(0).getFsCompanyMaster().getCompanyCode();
				collect.setCompanyCode(companyCodeValue);
			}
			
			collect.setLocCode(sessionStateManage.getCountryBranchCode());

			returnResult.put("collect", collect);

		}catch (Exception e) {
			e.printStackTrace();
		}

		return returnResult;
	}


	/**
	 * Collection Information Save
	 */

	public List<CollectDetail> saveCollectionDetailList(Collect collect){
		List<CollectDetail> collectDetailList = new ArrayList<CollectDetail>();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		String date = getCurrentDateWithFormat();
		int i = 0;
		Date acc_Month = null;

		try{

			if(collect!=null){
				acc_Month = DATE_FORMAT.parse(date);
				CollectDetail collectDetail = new CollectDetail();

				// customer Save
				/*if(getCustomerId() != null){
					customer = new Customer();
					customer.setCustomerId(getCustomerId());
					collectDetail.setFsCustomer(customer);
				}*/
				
				Customer customer = new Customer();
				customer.setCustomerId(getCustomerId());
				collectDetail.setFsCustomer(customer);

				// Company save
				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
				collectDetail.setFsCompanyMaster(companyMaster);

				// Country Save
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
				collectDetail.setFsCountryMaster(countryMaster);

				collectDetail.setDocumentFinanceYear(collect.getDocumentFinanceYear());
				
				collectDetail.setCollectionMode(Constants.C); // only foreign currency is paid in cash C
				collectDetail.setPaymentModeId(mapFetchAllPayMode.get(Constants.C));
				
				//collectDetail.setPaymentModeId(paymentModeId);
				collectDetail.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE));
				collectDetail.setDocumentId(collect.getDocumentId());
				//collectDetail.setDocumentNo(new BigDecimal(documentSerialId));
				collectDetail.setDocumentNo(collect.getDocumentNo());

				CountryBranch countryBranch = new CountryBranch();
				countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
				collectDetail.setExBankBranch(countryBranch);

				collectDetail.setDocumentLineNo(new BigDecimal(++i));

				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(getCurrencyId());
				collectDetail.setExCurrencyMaster(currencyMaster);

				collectDetail.setAcyymm(acc_Month);

				// collectDetail.setExBankBranch();
				collectDetail.setDocumentDate(new Date());
				collectDetail.setCollAmt(collect.getPaidAmount());
				collectDetail.setIsActive(Constants.Yes);
				collectDetail.setCreatedDate(new Date());
				collectDetail.setCreatedBy(sessionStateManage.getSessionValue("userName"));
				
				collectDetail.setCompanyCode(collect.getCompanyCode());
				
				collectDetail.setLocCode(sessionStateManage.getCountryBranchCode());
				
				collectDetailList.add(collectDetail);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return collectDetailList;
	}

	public List<ForeignCurrencyAdjust>  saveForeignCurrecnyList(Collect collect){

		List<ForeignCurrencyAdjust>  lstCurrencyAdjust = new ArrayList<>();

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		Date acc_Month = null;

		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());

			int i = 0;
			for (DenominationBean denominationBean : denominationBeanList) {
				ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();
				//if (denominationBean.getNoOfNotes() != 0) {
				if (denominationBean.getNoOfNotes() !=null && !denominationBean.getNoOfNotes().equals("0") && !denominationBean.getNoOfNotes().equals("")) {
					// Company save
					CompanyMaster companyMaster = new CompanyMaster();
					companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
					foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);

					// Country Save
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
					foreignCurrencyAdjust.setFsCountryMaster(countryMaster);

					// customer Save
					/*if(getCustomerId() != null){
						customer = new Customer();
						customer.setCustomerId(getCustomerId());
						foreignCurrencyAdjust.setFsCustomer(customer);
					}*/
					
					Customer customer = new Customer();
					customer.setCustomerId(getCustomerId());
					foreignCurrencyAdjust.setFsCustomer(customer);

					foreignCurrencyAdjust.setDocumentDate(new Date());

					// currency Id
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(getCurrencyId());
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);

					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(denominationBean.getNoOfNotes()));
					foreignCurrencyAdjust.setAdjustmentAmount(denominationBean.getPurchaseAmount());
					
					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster.setDenominationId(new BigDecimal(denominationBean.getDenominationID()));
					foreignCurrencyAdjust.setFsDenominationId(denominationMaster);

					//foreignCurrencyAdjust.setExchangeRate(denominationBean.getExchangeRate());
					foreignCurrencyAdjust.setExchangeRate(GetRound.roundBigDecimal(getAvgExchageRate(), 9));
					foreignCurrencyAdjust.setDenaminationAmount(denominationBean.getPurchaseAmount());
					foreignCurrencyAdjust.setDocumentFinanceYear(new BigDecimal(finaceYear));
					foreignCurrencyAdjust.setOracleUser(sessionStateManage.getUserName());
					/****************************************************************************************/
					// Tanumoy

					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE));
					foreignCurrencyAdjust.setDocumentNo(new BigDecimal(getDocSerialIdNumberForSave()));
					foreignCurrencyAdjust.setDocumentId(getDocumentIdPk());
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++i));
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);

					// BankBranch bankbranch = new BankBranch();
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);

					CurrencyWiseDenomination currencyWiseDenomination = new CurrencyWiseDenomination();
					currencyWiseDenomination.setDenominationId(new BigDecimal(denominationBean.getDenominationID()));
					foreignCurrencyAdjust.setFsDenominationId(currencyWiseDenomination);

					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(denominationBean.getNoOfNotes()));
					//foreignCurrencyAdjust.setExchangeRate(denominationBean.getExchangeRate());
					foreignCurrencyAdjust.setDenaminationAmount(denominationBean.getDenominationAmount());
					foreignCurrencyAdjust.setProgNumber(Constants.FC_PURCHAGE);
					
					//foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
					// now document status is set P not Y
					foreignCurrencyAdjust.setDocumentStatus(Constants.P);
					
					foreignCurrencyAdjust.setTransactionType(Constants.P);
					
					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getSessionValue("userName"));
					// no approval screen - so added directly while creating - Mandatory
					foreignCurrencyAdjust.setApprovalBy(sessionStateManage.getUserName());
					foreignCurrencyAdjust.setApprovalDate(new Date());

					foreignCurrencyAdjust.setCollect(collect);
					
					foreignCurrencyAdjust.setCompanyCode(collect.getCompanyCode());

					//Set in the List.
					lstCurrencyAdjust.add(foreignCurrencyAdjust);
				}
			}



			// For 2nd Panel
			i = 0;
			for (ForeignLocalCurrencyDataTable stockBean : lstData) {

				ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();

				if (stockBean.getQty() != null && !stockBean.getQty().equals("") && !stockBean.getQty().equals("0")) {

					// Company save
					CompanyMaster companyMaster = new CompanyMaster();
					companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
					foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);

					// Country Save
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
					foreignCurrencyAdjust.setFsCountryMaster(countryMaster);

					// customer Save
					/*if(getCustomerId() != null){
						customer = new Customer();
						customer.setCustomerId(getCustomerId());
						foreignCurrencyAdjust.setFsCustomer(customer);
					}*/
					
					Customer customer = new Customer();
					customer.setCustomerId(getCustomerId());
					foreignCurrencyAdjust.setFsCustomer(customer);
					
					foreignCurrencyAdjust.setDocumentDate(new Date());

					// currency Id
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(stockBean.getCurrencyId());
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);

					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(stockBean.getQty()));

					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster.setDenominationId(stockBean.getDenominationId());
					foreignCurrencyAdjust.setFsDenominationId(denominationMaster);

					// It's for KWD
					foreignCurrencyAdjust.setExchangeRate(BigDecimal.ZERO);
					foreignCurrencyAdjust.setDenaminationAmount(stockBean.getItem());
					foreignCurrencyAdjust.setDocumentFinanceYear(new BigDecimal(finaceYear));
					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(stockBean.getPrice()));
					foreignCurrencyAdjust.setOracleUser(sessionStateManage.getUserName());
					/****************************************************************************************/

					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE));
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++i));
					//foreignCurrencyAdjust.setDocumentNo(new BigDecimal(documentSerialId));
					foreignCurrencyAdjust.setDocumentNo(new BigDecimal(getDocSerialIdNumberForSave()));
					foreignCurrencyAdjust.setDocumentId(getDocumentIdPk());
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);

					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);

					CurrencyWiseDenomination currencyWiseDenomination = new CurrencyWiseDenomination();
					currencyWiseDenomination.setDenominationId(stockBean.getDenominationId());
					foreignCurrencyAdjust.setFsDenominationId(currencyWiseDenomination);

					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(stockBean.getQty()));
					// foreignCurrencyAdjust.setExchangeRate(stockBean.getExchangeRate());
					foreignCurrencyAdjust.setDenaminationAmount(stockBean.getItem());
					foreignCurrencyAdjust.setProgNumber(Constants.FC_PURCHAGE);
					
					//foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
					// now document status is set P not Y
					foreignCurrencyAdjust.setDocumentStatus(Constants.P);
					
					foreignCurrencyAdjust.setTransactionType(Constants.F); // Refund Amount F to Customer Local Currency
					/**
					 * CR : from O to S
					 */
					// foreignCurrencyAdjust.setTransactionType("S");

					/********************************************************************************************/


					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getSessionValue("userName"));
					// no approval screen - so added directly while creating - Mandatory
					foreignCurrencyAdjust.setApprovalBy(sessionStateManage.getUserName());
					foreignCurrencyAdjust.setApprovalDate(new Date());
					
					foreignCurrencyAdjust.setCompanyCode(collect.getCompanyCode());
					
					foreignCurrencyAdjust.setCollect(collect);

					//Set in the List.
					lstCurrencyAdjust.add(foreignCurrencyAdjust);

				} 
			}

			// 3rd Panel
			i = 0;
			for (ForeignLocalCurrencyDataTable stockBean : lstRefundData) {
				ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();

				if (stockBean.getQty() != null && !stockBean.getQty().equals("") && !stockBean.getQty().equals("0")) {

					// Company save
					companyMaster = new CompanyMaster();
					companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
					foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);

					// Country Save
					countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
					foreignCurrencyAdjust.setFsCountryMaster(countryMaster);

					// customer Save
					if(getCustomerId() != null){
						customer = new Customer();
						customer.setCustomerId(getCustomerId());
						foreignCurrencyAdjust.setFsCustomer(customer);
					}
					

					foreignCurrencyAdjust.setDocumentDate(new Date());

					// currency Id
					currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(stockBean.getCurrencyId());
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);

					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(stockBean.getQty()));

					denominationMaster = new CurrencyWiseDenomination();
					denominationMaster.setDenominationId(stockBean.getDenominationId());
					foreignCurrencyAdjust.setFsDenominationId(denominationMaster);

					// It's for KWD
					foreignCurrencyAdjust.setExchangeRate(BigDecimal.ZERO);
					foreignCurrencyAdjust.setDenaminationAmount(stockBean.getItem());
					foreignCurrencyAdjust.setDocumentFinanceYear(new BigDecimal(finaceYear));
					foreignCurrencyAdjust.setOracleUser(sessionStateManage.getUserName());


					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE));
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++i));
					foreignCurrencyAdjust.setDocumentId(getDocumentIdPk());
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
					foreignCurrencyAdjust.setDocumentNo(new BigDecimal(getDocSerialIdNumberForSave()));

					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);

					CurrencyWiseDenomination currencyWiseDenomination = new CurrencyWiseDenomination();
					currencyWiseDenomination.setDenominationId(stockBean.getDenominationId());
					foreignCurrencyAdjust.setFsDenominationId(currencyWiseDenomination);

					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(stockBean.getQty()));
					// foreignCurrencyAdjust.setExchangeRate(stockBean.getExchangeRate());
					foreignCurrencyAdjust.setDenaminationAmount(stockBean.getItem());
					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(stockBean.getPrice()));
					foreignCurrencyAdjust.setProgNumber(Constants.FC_PURCHAGE);
					
					// foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
					// now document status is set P not Y
					foreignCurrencyAdjust.setDocumentStatus(Constants.P);
					
					foreignCurrencyAdjust.setTransactionType(Constants.C); // collected Amount C - collected from customer

					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getSessionValue("userName"));
					// no approval screen - so added directly while creating - Mandatory
					foreignCurrencyAdjust.setApprovalBy(sessionStateManage.getUserName());
					foreignCurrencyAdjust.setApprovalDate(new Date());
					
					foreignCurrencyAdjust.setCompanyCode(collect.getCompanyCode());

					//foreignCurrencyAdjust.setCollect(collect);

					//Set in the List.
					lstCurrencyAdjust.add(foreignCurrencyAdjust);
				} 
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lstCurrencyAdjust;
	}
	
	public List<Payment> savePaymentRecord(Collect collect){
		
		List<Payment> lstPayment = new ArrayList<Payment>();
		
		// if refund available need to store in Ex_Payment
		if(lstData != null && !lstData.isEmpty()){
			
			try{
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
				String date = getCurrentDateWithFormat();
				int i = 0;
				Date acc_Month = null;
				acc_Month = DATE_FORMAT.parse(date);

				Payment payment = new Payment();

				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(sessionStateManage.getCountryId());
				payment.setCountryId(countryMaster);

				payment.setCompanyId(sessionStateManage.getCompanyId());

				/*if(getCustomerId() != null){
					payment.setCustomerId(getCustomerId());
				}*/
				
				payment.setCustomerId(getCustomerId());

				payment.setDocCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE));

				if(finaceYear != 0){
					payment.setDocYear(new BigDecimal(finaceYear));
				}

				payment.setAcyymm(acc_Month);

				payment.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));

				//payment.setReceiptType(Constants.RECEIPT_TYPE); // 70 - fc Purchase
				payment.setPaymentDate(new Date());
				payment.setPaymentmode(getColpaymentmodeCode());// not only collection mode Cash - C , Bank Transfer - T , Bank Cheque - B
				payment.setCurrencyId(lstData.get(0).getCurrencyId()); // stock currency id always application currency id
				payment.setPaidAmount(new BigDecimal(getSaleAmount()) );

				payment.setDocNumber(new BigDecimal(getDocSerialIdNumberForSave()));

				payment.setIsActive(Constants.Yes);

				payment.setCreatedBy(sessionStateManage.getUserName());
				payment.setCreatedDate(new Date());
				
				payment.setLocCod(sessionStateManage.getCountryBranchCode());
				payment.setCompanyCode(collect.getCompanyCode());
				payment.setDocumentId(collect.getDocumentId());
				
				lstPayment.add(payment);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return lstPayment;
	}


	public ReceiptPayment savePaymentReceiptList(Collect collect){
		List<ReceiptPayment> receiptPaymentList = new ArrayList<ReceiptPayment>();
		ReceiptPayment receiptPayment=null;

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		Date acc_Month = null;

		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
			receiptPayment = new ReceiptPayment();
			
			System.out.println("acc_Month :"+acc_Month);

			log.info("paymentReceipt start ..");

			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
			receiptPayment.setFsCompanyMaster(companyMaster);

			// Country Save
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
			receiptPayment.setFsCountryMaster(countryMaster);

			// customer Save
			/*if(getCustomerId() != null){
				customer = new Customer();
				customer.setCustomerId(getCustomerId());
				receiptPayment.setFsCustomer(customer);
			}*/
			
			Customer customer = new Customer();
			customer.setCustomerId(getCustomerId());
			receiptPayment.setFsCustomer(customer);
			
			
			if(getIdRef() != null){
				receiptPayment.setCustomerReference(new BigDecimal(getIdRef()) );
			}

			receiptPayment.setDocumentFinanceYear(new BigDecimal(finaceYear));
			receiptPayment.setCustomerName(getName());
			
			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(getCurrencyId());
			receiptPayment.setLocalFsCountryMaster(currencyMaster);
			
			receiptPayment.setForignTrnxAmount(totalPurchaseAmount);
			
			CurrencyMaster currencyMaster1 = new CurrencyMaster();
			currencyMaster1.setCurrencyId(new BigDecimal(getSourceId()));
			receiptPayment.setForeignFsCountryMaster(currencyMaster1);// Local
			// currency
			// KWD
			receiptPayment.setLocalTrnxAmount(new BigDecimal(getSaleAmount()));
			receiptPayment.setTransactionActualRate(GetRound.roundBigDecimal(getAvgExchageRate(), 9));
			receiptPayment.setLocalNetAmount(new BigDecimal(getSaleAmount()));
			
			// receiptPayment.setDocumentStatus(Constants.Yes);
			// now document status is set P not Y
			receiptPayment.setDocumentStatus(Constants.P);
			
			receiptPayment.setGeneralLegerDate(new Date());
			receiptPayment.setIsActive(Constants.Yes);
			receiptPayment.setAccountMMYYYY(acc_Month);
			receiptPayment.setTransactionType(Constants.P);

			receiptPayment.setDocumentId(collect.getDocumentId());
			receiptPayment.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE));
			receiptPayment.setDocumentNo(new BigDecimal(getDocSerialIdNumberForSave()));
			//Ban/*kBranch bankBranch = new BankBranch();*/
			CountryBranch countryBranch= new CountryBranch();
			countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
			/*bankBranch.setBankBranchId(new BigDecimal(sessionStateManage.getBranchId()));
			receiptPayment.setFsbankBranch(bankBranch);*/

			receiptPayment.setCountryBranch(countryBranch);
			receiptPayment.setDocumentDate(new Date());

			CurrencyMaster currencyMaster2 = new CurrencyMaster();
			currencyMaster2.setCurrencyId(getCurrencyId());
			receiptPayment.setForeignFsCountryMaster(currencyMaster2);

			CurrencyMaster localCurrencyMaster = new CurrencyMaster();
			localCurrencyMaster.setCurrencyId(getForeignCurrencyPurchaseService().getLocalCurrencyId(sessionStateManage.getCountryId()));
			receiptPayment.setLocalFsCountryMaster(localCurrencyMaster);

			receiptPayment.setLocalTrnxAmount(new BigDecimal(getSaleAmount()));

			// purposeoftransaction
			PurposeOfTransaction purposeofTransaction = new PurposeOfTransaction();
			purposeofTransaction.setPurposeId(new BigDecimal(getPurposeOfTransactions()));
			receiptPayment.setPurposeOfTransaction(purposeofTransaction);

			SourceOfIncome sourceOfIncome = new SourceOfIncome();
			sourceOfIncome.setSourceId(new BigDecimal(getSourceOfIncomes()));
			receiptPayment.setSourceOfIncome(sourceOfIncome);

			receiptPayment.setRemarks(getRemarks());
			//receiptPayment.setSignature(getSignature());
			receiptPayment.setSignatureSpecimenClob(stringToClob(digitalSignature));
			receiptPayment.setCreatedDate(new Date());
			receiptPayment.setCreatedBy(sessionStateManage.getSessionValue("userName"));

			//Added by Rabil 21/02/2016

			receiptPayment.setColDocCode(collect.getDocumentCode());
			receiptPayment.setColDocFyr(collect.getDocumentFinanceYear());
			receiptPayment.setColDocNo(collect.getDocumentNo());
			receiptPayment.setSourceofIncomeId(new BigDecimal(getSourceOfIncomes()));
			receiptPayment.setDocumentFinanceYearId(getFinanceYearId());

			//receiptPayment.setApplicationDocumentNo(applicationDocumentNo);
			//receiptPayment.setApplicationFinanceYear(applicationFinanceYear);
			
			receiptPayment.setCollectionMode(getColpaymentmodeCode()); // not only collection mode Cash - C , Bank Transfer - T , Bank Cheque - B

			if(getColpaymentmodeCode() != null && getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)){
				// Bank Cheque 
				receiptPayment.setChequeBankCode(getColBankCode());
				receiptPayment.setChequeRef(getColChequeRef());
				receiptPayment.setChequeDate(getColChequeDate());
				receiptPayment.setApprovalNo(getColApprovalNo());
			}else if(getColpaymentmodeCode() != null && getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)){
				// Bank Transfer
				receiptPayment.setChequeBankCode(getColBankCode());
				receiptPayment.setApprovalNo(getColApprovalNo());
			}else{
				receiptPayment.setChequeBankCode(null);
				receiptPayment.setChequeRef(null);
				receiptPayment.setChequeDate(null);
				receiptPayment.setApprovalNo(null);
			}
			
			receiptPayment.setCompanyCode(collect.getCompanyCode());
			
			receiptPayment.setLocCode(sessionStateManage.getCountryBranchCode());
			
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String hostname = loginBean.getClientIpAddress(request);
			receiptPayment.setTransactionIPAddress(hostname);

			//receiptPaymentList.add(receiptPayment);


		}catch(Exception e){
			e.printStackTrace();
		}

		return receiptPayment;
	}

	/**
	 * Check for Stock Availability
	 * 
	 * @param value
	 * @param denomId
	 */
	public String checkStockAvailability(Object value, BigDecimal denomId) {
		String check = Constants.No;
		for (ForeignLocalCurrencyDataTable element : lstData) {
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
	private Date Date;

	/**
	 * save paymentReceipt
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void savepaymentReceipt() {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		Date acc_Month = null;

		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		log.info("paymentReceipt start ..");
		try {
			boolean isExist = true;
			if (receiptPayment == null) {
				receiptPayment = new ReceiptPayment();
				isExist = false;
			}
			companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
			receiptPayment.setFsCompanyMaster(companyMaster);

			// Country Save
			countryMaster = new CountryMaster();
			countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
			receiptPayment.setFsCountryMaster(countryMaster);

			// customer Save
			if(getCustomerId() != null){
				customer = new Customer();
				customer.setCustomerId(getCustomerId());
				receiptPayment.setFsCustomer(customer);
			}
			

			receiptPayment.setDocumentFinanceYear(new BigDecimal(finaceYear));
			receiptPayment.setCustomerName(getName());
			currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(getCurrencyId());
			receiptPayment.setLocalFsCountryMaster(currencyMaster);
			receiptPayment.setForignTrnxAmount(totalPurchaseAmount);
			currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(new BigDecimal(getSourceId()));
			receiptPayment.setForeignFsCountryMaster(currencyMaster);// Local
			// currency
			// KWD
			receiptPayment.setLocalTrnxAmount(new BigDecimal(getSaleAmount()));
			receiptPayment.setTransactionActualRate(GetRound.roundBigDecimal(getAvgExchageRate(), 9));
			receiptPayment.setLocalNetAmount(new BigDecimal(getSaleAmount()));
			
			//receiptPayment.setDocumentStatus(Constants.Yes);
			// now document status is set P not Y
			receiptPayment.setDocumentStatus(Constants.P);
			
			receiptPayment.setGeneralLegerDate(new Date());
			receiptPayment.setIsActive(Constants.Yes);
			receiptPayment.setAccountMMYYYY(acc_Month);
			receiptPayment.setTransactionType(Constants.P);

			receiptPayment.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE));
			receiptPayment.setDocumentNo(new BigDecimal(getDocSerialIdNumberForSave()));
			//Ban/*kBranch bankBranch = new BankBranch();*/
			CountryBranch countryBranch= new CountryBranch();
			countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
			/*bankBranch.setBankBranchId(new BigDecimal(sessionStateManage.getBranchId()));
			receiptPayment.setFsbankBranch(bankBranch);*/

			receiptPayment.setCountryBranch(countryBranch);
			receiptPayment.setDocumentDate(new Date());

			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(getCurrencyId());
			receiptPayment.setForeignFsCountryMaster(currencyMaster);

			CurrencyMaster localCurrencyMaster = new CurrencyMaster();
			localCurrencyMaster.setCurrencyId(getForeignCurrencyPurchaseService().getLocalCurrencyId(sessionStateManage.getCountryId()));
			receiptPayment.setLocalFsCountryMaster(localCurrencyMaster);

			receiptPayment.setLocalTrnxAmount(new BigDecimal(getSaleAmount()));

			// purposeoftransaction
			purposeofTransaction = new PurposeOfTransaction();
			purposeofTransaction.setPurposeId(new BigDecimal(getPurposeOfTransactions()));
			receiptPayment.setPurposeOfTransaction(purposeofTransaction);

			sourceOfIncome = new SourceOfIncome();
			sourceOfIncome.setSourceId(new BigDecimal(getSourceOfIncomes()));
			receiptPayment.setSourceOfIncome(sourceOfIncome);

			receiptPayment.setRemarks(getRemarks());
			//receiptPayment.setSignature(getSignature());
			receiptPayment.setSignatureSpecimenClob(stringToClob(digitalSignature));
			receiptPayment.setCreatedDate(new Date());
			receiptPayment.setCreatedBy(sessionStateManage.getSessionValue("userName"));

			//Added by Rabil 21/02/2016

			receiptPayment.setColDocCode(collect.getDocumentCode());
			receiptPayment.setColDocFyr(collect.getDocumentFinanceYear());
			receiptPayment.setColDocNo(collect.getDocumentNo());
			receiptPayment.setSourceofIncomeId(new BigDecimal(getSourceOfIncomes()));
			receiptPayment.setDocumentFinanceYearId(getFinanceYearId());

			//receiptPayment.setApplicationDocumentNo(applicationDocumentNo);
			//receiptPayment.setApplicationFinanceYear(applicationFinanceYear);

			receiptPayment.setCollectionMode(getColpaymentmodeCode()); // not only collection mode Cash - C , Bank Transfer - T , Bank Cheque - B

			if(getColpaymentmodeCode() != null && getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)){
				// Bank Cheque 
				receiptPayment.setChequeBankCode(getColBankCode());
				receiptPayment.setChequeRef(getColChequeRef());
				receiptPayment.setChequeDate(getColChequeDate());
				receiptPayment.setApprovalNo(getColApprovalNo());
			}else if(getColpaymentmodeCode() != null && getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)){
				// Bank Transfer
				receiptPayment.setChequeBankCode(getColBankCode());
				receiptPayment.setApprovalNo(getColApprovalNo());
			}else{
				receiptPayment.setChequeBankCode(null);
				receiptPayment.setChequeRef(null);
				receiptPayment.setChequeDate(null);
				receiptPayment.setApprovalNo(null);
			}
			
			receiptPayment.setLocCode(sessionStateManage.getCountryBranchCode());

			getForeignCurrencyPurchaseService().save((T) receiptPayment);

		} catch (Exception e) {
			e.printStackTrace();
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("err.show();");
			return;
		}
		log.info("paymentReceipt end ..");
	}

	public void fetchLstRefundData() {
		/* Checking that it's first time or not, first time list size will be 0 */
		setTotalRefundLocalCashEntered("0");
		if (lstRefundData.size() == 0) {
			/* Responsible to show serial number in data table */
			int i = 0;
			/*
			 * Responsible to hold each row in different bean object of data table
			 */
			ForeignLocalCurrencyDataTable item = null;

			List<CurrencyWiseDenomination> dataFromDb = foreignLocalCurrencyDenominationService.getLocalCurrencyDenominationStockWithoutUser(sessionStateManage.getCountryId(), sessionStateManage.getCurrencyId());
			/* putting the value in list to show in data table */
			if(dataFromDb !=null){
				for (CurrencyWiseDenomination element : dataFromDb) {
					item = new ForeignLocalCurrencyDataTable(++i, element.getDenominationAmount(), "", "",0, new BigDecimal(0), element.getDenominationId(), element.getExCurrencyMaster().getCurrencyId(),
							element.getDenominationDesc(),element.getDenominationAmount());
					lstRefundData.add(item);
				}
			}
		}
	}

	public BigDecimal getPkCollect() {
		return pkCollect;
	}

	public void setPkCollect(BigDecimal pkCollect) {
		this.pkCollect = pkCollect;
	}

	public String getTotalRefundCashRecipt() {
		return totalRefundCashRecipt;
	}

	public void setTotalRefundCashRecipt(String totalRefundCashRecipt) {
		this.totalRefundCashRecipt = totalRefundCashRecipt;
	}

	public String getTotalRefundLocalCashEntered() {
		return totalRefundLocalCashEntered;
	}

	public void setTotalRefundLocalCashEntered(String totalRefundLocalCashEntered) {
		this.totalRefundLocalCashEntered = totalRefundLocalCashEntered;
	}

	public void clear() {
		setSuccessPanel(false);
		setFcPurchageLabel(true);
		setLocalcashpaidtoCustomerLabel(false);
		setBooPurchasePanel(true);
		setBooPanelDatatable(false);
		setBooRefundPanel(false);
		setSignaturePanel(false);
		setFinaceYear(0);
		setFinanceMonth(0);
		setId(null);
		setName(null);
		setIdRef(null);
		setMobile(null);
		setCurrencyId(null);
		setSaleCurrencyCode(null);
		if(denominationBeanList != null || !denominationBeanList.isEmpty()){
			denominationBeanList.clear();
		}
		setTotalPurchaseAmount(null);
		setSaleAmount(null);
		setTempSaleAmount(null);
		// setAvgExchageRate(0.0);
		setRemarks(null);
		setSignature(null);
		setSourceOfIncomes(null);
		setPurposeOfTransactions(null);
		setForNextPanels(false);
		setVarToKeepSerial(0);
		setNeededPurchaseAmount(null);
		setMyImage(null);
		setDigitalSignature(null);
		setDigitalSign(null);
		if(lstData != null || !lstData.isEmpty()){
			lstData.clear();
		}
		if(lstRefundData != null || !lstRefundData.isEmpty()){
			lstRefundData.clear();
		}
		
		// second panel
		setTotalValue(null);
		// third panel
		setTotalRefundCashRecipt(null);
		setTotalRefundLocalCashEntered(null);

		// payment clearing
		setColBankCode(null);
		setColChequeRef(null);
		setColChequeDate(null);
		setColApprovalNo(null);
		setColpaymentmodeId(null);
		setColpaymentmodeName(null);
		setColpaymentmodeCode(null);
		setBooRenderColBanks(true);
		setBooRenderColCheque(true);
		setBooRenderColApprovalNo(true);
		setSaveOrNext(Constants.Save);

		if(bankMasterList != null || !bankMasterList.isEmpty()){
			bankMasterList.clear();
		}

	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	
	
	// checking cashier option in Fs_Employee is Y or N
	public boolean checkEmployeeAllowedOrNot(){
		boolean empStatus = Boolean.FALSE;
		List<Employee> checkEmployee = iGeneralService.getEmployeeDetail(sessionStateManage.getEmployeeId());
		if(checkEmployee != null && checkEmployee.size() != 0){
			Employee empstatus = checkEmployee.get(0);
			if(empstatus.getCashierOpt() != null){
				if(empstatus.getCashierOpt().equalsIgnoreCase(Constants.Y)){
					empStatus = Boolean.TRUE;
				}else{
					empStatus = Boolean.FALSE;
				}
			}else{
				empStatus = Boolean.FALSE;
			}
		}

		return empStatus;
	}
	
	public void clearCache() {
		
		boolean cashierOptionStatus = checkEmployeeAllowedOrNot();
		if(cashierOptionStatus){
			setSuccessPanel(false);
			setBooRenderFirstPanel(true);
			clear();
			fetchLocalSaleCurrency();
			toFetchPaymentDetails();
			fetchPurposeOfTransactionsList();
			fetchSourceOfIncomeList();
			fetchCurrencyList();
			try {
				loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "foreigncurrencypurchase.xhtml");
				FacesContext.getCurrentInstance().getExternalContext().redirect("../foreigncurrency/foreigncurrencypurchase.xhtml");
			} catch (Exception e) {
				log.info("Problem to redirect");
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "You are not authorized to collect amount. Please request IT for permission.", ""));
		}
		
	}
	
	// fetch local sale currency 
	public void fetchLocalSaleCurrency(){
		setSaleCurrencyCode(null);
		String saleCurrencyQuote = iGeneralService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));
		if(saleCurrencyQuote != null){
			setSaleCurrencyCode(saleCurrencyQuote);
		}
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void clickDenaminationOK() {

		setFcPurchageLabel(true);
		setLocalcashpaidtoCustomerLabel(false);
		setBooPurchasePanel(true);
		setBooPanelDatatable(false);
		setBooRefundPanel(false);
		setForNextPanels(false);
	}

	public void idNotFound() {
		setId(null);
		setName(null);
		setMobile(null);
		setIdRef(null);
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
		setFcPurchageLabel(true);
		setLocalcashpaidtoCustomerLabel(false);
		setBooPurchasePanel(true);
		setBooPanelDatatable(false);
		setBooRefundPanel(false);
		setBooPurchasePanel(true);
		setBooPanelDatatable(false);
		setBooRefundPanel(false);
		setFinaceYear(0);
		setFinanceMonth(0);
		setId(null);
		setIdRef(null);
		setName(null);
		setMobile(null);
		setCurrencyId(null);
		setSaleCurrencyCode(null);
		denominationBeanList.clear();
		setTotalPurchaseAmount(null);
		setSaleAmount(null);
		setTempSaleAmount(null);
		setForNextPanels(false);
		// setAvgExchageRate(0.0);
		setRemarks(null);
		setSignature(null);
		setSourceOfIncomes(null);
		setPurposeOfTransactions(null);
		setVarToKeepSerial(0);
		setSignaturePanel(false);

		lstData.clear();
		lstRefundData.clear();

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

		try{
			HashMap<String, String> outPutValues =  iGeneralService.getNextDocumentRefNumber(Integer.parseInt(sessionStateManage.getSessionValue("countryId")), Integer.parseInt(sessionStateManage.getSessionValue("companyId")), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE),  getFinaceYear(),
					processIn,sessionStateManage.getCountryBranchCode());

			String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
			if(proceErrorMsg!=null)
			{
				setBooProcedureDialog(true);
				setErrMsg(proceErrorMsg);
				setDocumentSerilaNoCheck(Integer.parseInt(outPutValues.get("DOCNO") ));
				//RequestContext.getCurrentInstance().execute("proceErr.show();");
				return "0";

			}else{
				setBooProcedureDialog(false);
				String documentSerialID=outPutValues.get("DOCNO");
				return documentSerialID;

			}


		}catch(NumberFormatException | AMGException e){
			setBooProcedureDialog(true);
			setErrMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
			return "0";
		}


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

	public boolean isForNextPanels() {
		return forNextPanels;
	}

	public void setForNextPanels(boolean forNextPanels) {
		this.forNextPanels = forNextPanels;
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
	private String password;
	private String location1;
	private BigDecimal limitationAmount;

	public BigDecimal getLimitationAmount() {
		return limitationAmount;
	}

	public void setLimitationAmount(BigDecimal limitationAmount) {
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

	public void checkLimit() {
		roleWiseLimit();
	}


	private boolean roleWiseLimit() {
		boolean limitStatus = false;
		String roleId1 = sessionStateManage.getRoleId();

		String sender = sessionStateManage.getUserName();
		String branchId = sessionStateManage.getBranchId();
		String employeeid = sessionStateManage.getEmployeeId().toString();
		location1 = sessionStateManage.getLocation();
		String telephoneno = sessionStateManage.getTelephoneNumber();
		String email = "a.subramanian@almullagroup.com";
		int roleId = Integer.parseInt(roleId1);
		BigDecimal purchaseAmount1 = getNeededPurchaseAmount();


		log.info("Role No "+ roleId);
		RoleMaster roleMaster = iEmployeeService.getRoleName(new BigDecimal(sessionStateManage.getRoleId()));

		BigDecimal dailyLimitId = null;
		BigDecimal limitAmount = null;

		if(roleMaster!=null)
		{
			dailyLimitId = iEmployeeService.getLimitType(Constants.DAILYLIMIT);
			BigDecimal countryId = foreignCurrencyPurchaseService.getcountrybasedonCurrency(getCurrencyId());
			limitAmount = iEmployeeService.getEmployeeLimitFromCashLimit(roleMaster.getRoleName(), countryId, dailyLimitId);
		}

		if (limitAmount != null) {

			int res = purchaseAmount1.compareTo(limitAmount);
			if (/*employeeLimit.getFsRolemaster().getRoleId().intValue() == roleId && */res <= 0) {

				int countryBranchId = Integer.parseInt(branchId);
				limitationAmount = limitAmount;//employeeLimit.getLimitationAmount();
				List<Employee> newlist = iEmployeeService.getEmployees();

				ListIterator<Employee> le = newlist.listIterator();

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

							mailService1.sendTokenMail1(emailId, email, "welcome", customerid, tokennum, sender, branchId, location1, telephoneno, employeename, email);
							limitStatus = true;
						}
				}

			}else{
              // limit exists				
			}

		}
		return limitStatus;
	}



	public void reportexecute() { 
		try {
			redirectToFCPge();
		} catch (Exception e) { // make your own exception handling
			e.printStackTrace(); 
		}
	} 
	
	public void generateNewUpdatedReports(){ 
		reportexecute();
	}


	public void clickOnOKSave() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("foreigncurrencypurchase.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect");
		}
	}

	public String redirectToFCPge() {
		return "fcPage";
	}

	public String getDigitalSign() {
		return digitalSign;
	}

	public void setDigitalSign(String digitalSign) {
		this.digitalSign = digitalSign;
	}

	public boolean isSignaturePanel() {
		return signaturePanel;
	}

	public void setSignaturePanel(boolean signaturePanel) {
		this.signaturePanel = signaturePanel;
	}

	public String getSignatureSpecimen() {
		return signatureSpecimen;
	}

	public void setSignatureSpecimen(String signatureSpecimen) {
		this.signatureSpecimen = signatureSpecimen;
	}

	public String getDocSerialIdNumberForSave() {
		return docSerialIdNumberForSave;
	}

	public void setDocSerialIdNumberForSave(String docSerialIdNumberForSave) {
		this.docSerialIdNumberForSave = docSerialIdNumberForSave;
	}

	private Boolean successPanel = false;
	private Boolean booRenderFirstPanel = false;

	public Boolean getBooRenderFirstPanel() {
		return booRenderFirstPanel;
	}

	public void setBooRenderFirstPanel(Boolean booRenderFirstPanel) {
		this.booRenderFirstPanel = booRenderFirstPanel;
	}

	public Boolean getSuccessPanel() {
		return successPanel;
	}

	public void setSuccessPanel(Boolean successPanel) {
		this.successPanel = successPanel;
	}

	public void completed() {
		clearCache();
	}

	// ############## Report Genaration By RAHAMATHALI SHAIK ##############

	private JasperPrint jasperPrint;
	private List<ForeignCurrencyPurchageReport> foreignCurrencyPurchageList = new CopyOnWriteArrayList<ForeignCurrencyPurchageReport>();

	public void init() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(foreignCurrencyPurchageList);
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/fc_salereport.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}

	public void genarateForeignCurrencyReports(ActionEvent actionEvent) throws JRException, IOException {
		ServletOutputStream servletOutputStream=null;
		try{
			fetchForeignCurrencyPurchageDetails();
			init();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=ForeignCurrencyPurchageReport.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		}catch(Exception e){
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("err.show();");
			return;
		}finally{
			if(servletOutputStream!=null){
				servletOutputStream.close();
			}
		}
	
	}

	public void fetchForeignCurrencyPurchageDetails() {
		foreignCurrencyPurchageList.clear();

		List<ForeignCurrencyPurchaseReport> objList = new ArrayList<ForeignCurrencyPurchaseReport>();

		if(getDocSerialIdNumberForSave() != null){
			objList = foreignCurrencyPurchaseService.getFcPurchaseReportList(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(),new BigDecimal(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE) , new BigDecimal(getFinaceYear()), new BigDecimal(getDocSerialIdNumberForSave()));
		}
		
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String logoPath = realPath + Constants.LOGO_PATH;
		
		
		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		
		

		for (ForeignCurrencyPurchaseReport foreignCurrencyPurchaseReport : objList) {

			ForeignCurrencyPurchageReport fCPurchageReport = new ForeignCurrencyPurchageReport();

			fCPurchageReport.setCustomerId(foreignCurrencyPurchaseReport.getCustomerId());
			//fCPurchageReport.setCustomerName(foreignCurrencyPurchaseReport.getFirstName());
			String customerName = iGeneralService.getCustomerNameCustomerId(foreignCurrencyPurchaseReport.getCustomerId());
			if(customerName != null){
				fCPurchageReport.setCustomerName(customerName);
			}
			fCPurchageReport.setIdExpiryDate(foreignCurrencyPurchaseReport.getIdentityExpiryDate());
			if(foreignCurrencyPurchaseReport.getIdentityInt()!=null){
				fCPurchageReport.setCivilIdNo(foreignCurrencyPurchaseReport.getIdentityInt());
			}else{
				// civil id not available then it is crno
				List<CustomerIdproofView> lstcustProof = iGeneralService.getCustomerIdProofDetailsFromView(foreignCurrencyPurchaseReport.getCustomerId());
				if(lstcustProof != null && lstcustProof.size() != 0){
					CustomerIdproofView custDet = lstcustProof.get(0);
					
					if(custDet != null){
						if(custDet.getIdProofInt() != null){
							fCPurchageReport.setCivilIdNo(custDet.getIdProofInt());
						}
						
						if(custDet.getIdProofExpiredDate() != null){
							fCPurchageReport.setIdExpiryDate(custDet.getIdProofExpiredDate());
						}
						
					}
					
				}
			}
			
			if(foreignCurrencyPurchaseReport.getMobileNo() != null && !foreignCurrencyPurchaseReport.getMobileNo().trim().equalsIgnoreCase("")){
				fCPurchageReport.setContactNo(new BigDecimal(foreignCurrencyPurchaseReport.getMobileNo()));
			}
			fCPurchageReport.setDate(foreignCurrencyPurchaseReport.getCollectionDate());
			fCPurchageReport.setForeignCurrency(foreignCurrencyPurchaseReport.getQuoteName());
			fCPurchageReport.setSourceOfIncome(foreignCurrencyPurchaseReport.getSourceDesc());
			fCPurchageReport.setPurpose(foreignCurrencyPurchaseReport.getPurposeDesc());
			fCPurchageReport.setNetAmount(GetRound.roundBigDecimal(foreignCurrencyPurchaseReport.getNetAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(foreignCurrencyPurchaseReport.getCurrencyId())));//GetRound.roundBigDecimal(foreignCurrencyPurchaseReport.getNetAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())))
			fCPurchageReport.setRefundAmount(GetRound.roundBigDecimal(foreignCurrencyPurchaseReport.getRefundAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(foreignCurrencyPurchaseReport.getCurrencyId())));
			fCPurchageReport.setCommision(GetRound.roundBigDecimal(new BigDecimal(0) , foreignLocalCurrencyDenominationService.getDecimalPerCurrency(foreignCurrencyPurchaseReport.getCurrencyId())) );
			if(foreignCurrencyPurchaseReport.getTelephoneNo() != null){
				fCPurchageReport.setMobileNo(new BigDecimal(foreignCurrencyPurchaseReport.getTelephoneNo()));
			}
			fCPurchageReport.setEmployeeName(foreignCurrencyPurchaseReport.getEmployeeName());
			fCPurchageReport.setLocation(foreignCurrencyPurchaseReport.getLocation());
			fCPurchageReport.setCustomerRefNo(foreignCurrencyPurchaseReport.getCustomerReference());
			fCPurchageReport.setAmountPaid(GetRound.roundBigDecimal(foreignCurrencyPurchaseReport.getPaidAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(foreignCurrencyPurchaseReport.getCurrencyId())));
			fCPurchageReport.setPaymentMode(foreignCurrencyPurchaseReport.getPaymentMode());
			fCPurchageReport.setSaleAmount(GetRound.roundBigDecimal(foreignCurrencyPurchaseReport.getSaleAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			fCPurchageReport.setPurchageRate(GetRound.roundBigDecimal(foreignCurrencyPurchaseReport.getForeignTranxAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(foreignCurrencyPurchaseReport.getCurrencyId())));
			// fCPurchageReport.setExchangeRate(foreignCurrencyPurchaseReport.getExchangeRate());
			fCPurchageReport.setExchangeRate(foreignCurrencyPurchaseReport.getTranxActualRate());
			fCPurchageReport.setReceiptNo(foreignCurrencyPurchaseReport.getDocumentNo());
			fCPurchageReport.setDocFynYr(foreignCurrencyPurchaseReport.getDocFynYear());
			fCPurchageReport.setLocCurrency(iGeneralService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId() )));
			fCPurchageReport.setBothCurrency(foreignCurrencyPurchaseReport.getQuoteName()+"/"+iGeneralService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId() )));
			
			
			try {
				if(foreignCurrencyPurchaseReport.getEmployeeSignature()!=null){
				 fCPurchageReport.setCashierSignature(foreignCurrencyPurchaseReport.getEmployeeSignature().getSubString(1,(int) foreignCurrencyPurchaseReport.getEmployeeSignature().length())) ;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if(foreignCurrencyPurchaseReport.getSignatureSpecimenClob()!=null){
				 fCPurchageReport.setSignature(foreignCurrencyPurchaseReport.getSignatureSpecimenClob().getSubString(1,(int)foreignCurrencyPurchaseReport.getSignatureSpecimenClob().length()) );
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//GetRound.roundBigDecimal((amountPayable),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
			
			
			/*HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(foreignCurrencyPurchaseReport.getCustomerReference(), foreignCurrencyPurchaseReport.getDocumentDate());

			String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
			String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
			String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
			String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
			String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
			String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");


			StringBuffer loyaltyPoint = new StringBuffer();

			if(!prLtyStr1.trim().equals("")){
				loyaltyPoint.append(prLtyStr1);
			}
			if(!prLtyStr2.trim().equals("")){
				loyaltyPoint.append("\n");
				loyaltyPoint.append(prLtyStr2);
			}
			fCPurchageReport.setLoyaltyPointsExp(loyaltyPoint.toString() );
			//obj.setLoyalityPointExpiring(loyaltyPoint.toString());

			StringBuffer insurence = new StringBuffer();

			if(!prInsStr1.trim().equals("")){
				insurence.append(prInsStr1);
			}
			if(prInsStrAr1.trim().equals("")){
				insurence.append("\n");
				insurence.append(prInsStrAr1);
			}

			if(!prInsStr2.trim().equals("")){
				insurence.append("\n");
				insurence.append(prInsStr2);
			}
			if(!prInsStrAr2.trim().equals("")){
				insurence.append("\n");
				insurence.append(prInsStrAr2);
			}
			fCPurchageReport.setInsurence(insurence.toString());*/
			
			HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(foreignCurrencyPurchaseReport.getCustomerReference(), foreignCurrencyPurchaseReport.getDocumentDate());

			String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
			String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
			String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
			String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
			String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
			String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");

			StringBuffer loyaltyPoint = new StringBuffer();
			if(!prLtyStr1.trim().equals("")){
				loyaltyPoint.append(prLtyStr1);
			}
			if(!prLtyStr2.trim().equals("")){
				loyaltyPoint.append("\n");
				loyaltyPoint.append(prLtyStr2);
			}
			
			List<CutomerDetailsView> customerListD = iPersonalRemittanceService.getCustomerDetails(foreignCurrencyPurchaseReport.getCustomerId());

			if (customerListD != null && customerListD.size() > 0) {
				CutomerDetailsView cust = customerListD.get(0);
				if(cust.getIdType()!=null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
					fCPurchageReport.setLoyaltyPointsExp("");
				}else{
					fCPurchageReport.setLoyaltyPointsExp(loyaltyPoint.toString());
				}
			}
			
			
			
			//fCPurchageReport.setLoyaltyPointsExp(loyaltyPoint.toString());

			StringBuffer insurence1 = new StringBuffer();
			if(!prInsStr1.trim().equals("")){
				insurence1.append(prInsStr1);
			}
			if(!prInsStrAr1.trim().equals("")){
				insurence1.append("\n");
				insurence1.append(prInsStrAr1);
			}
			fCPurchageReport.setInsurence1(insurence1.toString());

			StringBuffer insurence2 = new StringBuffer();
			if(!prInsStr2.trim().equals("")){
				insurence2.append(prInsStr2);
			}
			if(!prInsStrAr2.trim().equals("")){
				insurence2.append("\n");
				insurence2.append(prInsStrAr2);
			}
			fCPurchageReport.setInsurence2(insurence2.toString());
			
			//sessionStateManage
			//fCPurchageReport.setInsurence("");
			// fCPurchageReport.setCommision(new BigDecimal(""));
			// fCPurchageReport.setNetPaidAmount(null);
			fCPurchageReport.setLogoPath(logoPath);

			foreignCurrencyPurchageList.add(fCPurchageReport);

		}

	}

	public void populateSearchValue() {
		HttpSession session = sessionStateManage.getSession();
		@SuppressWarnings("unchecked")
		ForeignCurrencyPurchaseBean<T> foreignCurrencyPurchaseBean = (ForeignCurrencyPurchaseBean<T>) session.getAttribute("searchObject");
		// BigDecimal IdNumber = (BigDecimal) session.getAttribute("IdNumber");

		if (foreignCurrencyPurchaseBean != null) {
			log.info("getCustomerId()" + foreignCurrencyPurchaseBean.getCustomerId());
			log.info("getMobile()" + foreignCurrencyPurchaseBean.getMobile());
			log.info("getId()" + foreignCurrencyPurchaseBean.getId());
			log.info("getName()" + foreignCurrencyPurchaseBean.getName());
			log.info("getCustomerId()" + foreignCurrencyPurchaseBean.getCustomerId());
			log.info("getSignatureSpecimen()" + foreignCurrencyPurchaseBean.getSignatureSpecimen());
			setCustomerId(foreignCurrencyPurchaseBean.getCustomerId());
			setMobile(foreignCurrencyPurchaseBean.getMobile());
			setId(foreignCurrencyPurchaseBean.getId());
			setIdRef(foreignCurrencyPurchaseBean.getIdRef());
			setName(foreignCurrencyPurchaseBean.getName());
			setSignatureSpecimen(foreignCurrencyPurchaseBean.getSignatureSpecimen());
			session.removeAttribute("searchObject");

		}

	}


	/**
	 * * @author : Rabil
	 */
	public java.sql.Clob stringToClob(String source) throws Exception {
		try {
			return new javax.sql.rowset.serial.SerialClob(source.toCharArray());
		} catch (Exception e) {
			return null;
		}
	}

	public int getDocumentSerilaNoCheck() {
		return documentSerilaNoCheck;
	}
	public void setDocumentSerilaNoCheck(int documentSerilaNoCheck) {
		this.documentSerilaNoCheck = documentSerilaNoCheck;
	}

	public String getSignatureMandatoryMsg() {
		return signatureMandatoryMsg;
	}
	public void setSignatureMandatoryMsg(String signatureMandatoryMsg) {
		this.signatureMandatoryMsg = signatureMandatoryMsg;
	}

	public boolean isBooRenderSignatureMsg() {
		return booRenderSignatureMsg;
	}
	public void setBooRenderSignatureMsg(boolean booRenderSignatureMsg) {
		this.booRenderSignatureMsg = booRenderSignatureMsg;
	}

	public BigDecimal getDocumentIdPk() {
		return documentIdPk;
	}
	public void setDocumentIdPk(BigDecimal documentIdPk) {
		this.documentIdPk = documentIdPk;
	}

	public String getDigitalSignature() {
		return digitalSignature;
	}
	public void setDigitalSignature(String digitalSignature) {
		this.digitalSignature = digitalSignature;
	}

	public BigDecimal getFinanceYearId() {
		return financeYearId;
	}
	public void setFinanceYearId(BigDecimal financeYearId) {
		this.financeYearId = financeYearId;
	}

	// change by payment mode - cash and debit card
	public void changeofPaymentMode() {
		try{
			List<PaymentModeDesc> lstofPayment = ipaymentService.getPaymentDescLangList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
			//Boolean checkCash = false;
			String paymentModedesc = null;
			String paymentModeCode = null;
			setBooRenderColBanks(true);
			setBooRenderColCheque(true);
			setBooRenderColApprovalNo(true);
			//setBooColApprovalNo(false);
			if (lstofPayment.size() != 0) {
				for (PaymentModeDesc paymentModeDesc : lstofPayment) {
					if ((getColpaymentmodeId() == null ? BigDecimal.ZERO : getColpaymentmodeId()).compareTo(paymentModeDesc.getPaymentMode().getPaymentModeId()) == 0) {
						paymentModedesc = paymentModeDesc.getLocalPaymentName();
						paymentModeCode = paymentModeDesc.getPaymentMode().getPaymentCode();
						setColpaymentmodeName(paymentModedesc);
						setColpaymentmodeCode(paymentModeCode);
						break;
					} else {
						//paymentModedesc = null;
						setColpaymentmodeName(null);
						setColpaymentmodeCode(null);
					}
				}

				if(getColpaymentmodeCode() != null){
					// Cash , Bank Cheque and Bank Transfer
					List<PaymentMode> paymentModedetails = ipaymentService.getPaymentCheck(getColpaymentmodeCode());

					if (paymentModedetails.size() != 0) {
						// payment mode bank variables
						setColBankCode(null);
						setColChequeRef(null);
						setColChequeDate(null);
						setColApprovalNo(null);

						if(getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)){


							/**Added by Rabil on 15 02 2017
							 * FC PURCHASE SCREEN FC AMOUNT EQUIVALENT KD MORE THAN 3000 SHOULD NOT ALLOW CHECK NEED TO ACTIVATE by Kanmani
							 */
							errMsg = iPersonalRemittanceService.getExCheckCashLimitProcedure(sessionStateManage.getCountryId(),getCustomerId(), getColpaymentmodeId(),new BigDecimal(getSaleAmount()));
							System.out.println("errorMessage :" + errMsg);


							if (errMsg != null && !errMsg.equals("")) {
								setErrmsg(errMsg);
								setTempErrorMessage(errMsg);
								RequestContext.getCurrentInstance().execute("err.show();");

							} 




							setBooRenderColBanks(true);
							setBooRenderColCheque(true);
							setBooRenderColApprovalNo(true);
						}else if(getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)){
							setBooRenderColBanks(false);
							setBooRenderColCheque(false);
							setBooRenderColApprovalNo(true);
							getLocalBankListforIndicator();
						}else if(getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)){
							setBooRenderColBanks(false);
							setBooRenderColCheque(true);
							setBooRenderColApprovalNo(false);
							getLocalBankListforIndicator();
						}else{
							setColpaymentmodeId(null);
							setBooRenderColBanks(true);
							setBooRenderColCheque(true);
							setBooRenderColApprovalNo(true);
							System.out.println("Payment Mode Newly added");
							RequestContext.getCurrentInstance().execute("checkPaymentModeService.show();");
							return;
						}

					}
				}else{
					setBooRenderColBanks(true);
					setBooRenderColCheque(true);
					setBooRenderColApprovalNo(true);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//to fetch payment details
	public void toFetchPaymentDetails(){

		if(lstFetchAllPayMode != null || !lstFetchAllPayMode.isEmpty()){
			lstFetchAllPayMode.clear();
			mapFetchAllPayMode.clear();
		}

		List<PaymentModeDesc> list=ipaymentService.fetchPaymodeDesc(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"),Constants.Yes);
		if(list.size() !=0){
			// only cash , bank transfer and bank cheque
			for (PaymentModeDesc paymentModeDesc : list) {
				if(paymentModeDesc.getPaymentMode().getPaymentCode().equalsIgnoreCase(Constants.CashCode) || paymentModeDesc.getPaymentMode().getPaymentCode().equalsIgnoreCase(Constants.ChequeCode)
						|| paymentModeDesc.getPaymentMode().getPaymentCode().equalsIgnoreCase(Constants.BankTransferCode)){
					lstFetchAllPayMode.add(paymentModeDesc);
					mapFetchAllPayMode.put(paymentModeDesc.getPaymentMode().getPaymentCode(), paymentModeDesc.getPaymentMode().getPaymentModeId());
				}
			}
		}
	}

	// to get the local bank list or customer bank list
	public void getLocalBankListforIndicator() {

		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<ViewBankDetails> lstofBank = new ArrayList<ViewBankDetails>();

		if(bankMasterList != null || !bankMasterList.isEmpty()){
			bankMasterList.clear();
		}

		List<ViewBankDetails> localbankList = iGeneralService.getLocalBankListFromView(sessionStateManage.getCountryId());

		if (localbankList.size() != 0) {
			for (ViewBankDetails lstBank : localbankList) {
				if (!duplicateCheck.contains(lstBank.getChequeBankId())) {
					duplicateCheck.add(lstBank.getChequeBankId());
					lstofBank.add(lstBank);
				}
			}
		}

		if(lstofBank != null && !lstofBank.isEmpty()){
			bankMasterList.addAll(lstofBank);
		}

	}

	public static void main(String[] args){
		String str = getCurrentDateWithFormat();
		System.out.println("Str :"+str);
	}
	
	// rounded total rounded sale amount
	public void roundedTotalSaleAmountFunction(){
		// calling the function F_ROUND_LOCAL_AMT_LOWER
		try{
			BigDecimal totalSaleAmountRound = foreignCurrencyPurchaseService.getRoundedSaleAmountByFunc(new BigDecimal(getTempSaleAmount()));
			if(totalSaleAmountRound != null && totalSaleAmountRound.compareTo(BigDecimal.ZERO) != 0){
				BigDecimal TotalsaleAmountwithRound = new GetRound().roundBigDecimal(totalSaleAmountRound, foreignLocalCurrencyDenominationService.getDecimalPerCountry(sessionStateManage.getCountryId()));
				setSaleAmount(TotalsaleAmountwithRound.toPlainString());
				int decimalvalue = Integer.parseInt(Constants.FX_DEAL_WITH_SUPPLIER_DEAL_DECIMAL_PLACES);
				setAvgExchageRate(new BigDecimal(getSaleAmount()).divide(getTotalPurchaseAmount(),decimalvalue,BigDecimal.ROUND_HALF_UP));
			}else{
				setSaleAmount(getTempSaleAmount());
			}
		}catch(Exception e){
			setErrmsg("Function F_ROUND_LOCAL_AMT_LOWER :" + e.getMessage());
			RequestContext.getCurrentInstance().execute("err.show();");
		}
	}
	
	// fetch avg rate
	public void averageRate(){
		BigDecimal avgExchageRateDT = BigDecimal.ZERO;
		int i = 0;
		try {
			int noteSize = 0;
			for (DenominationBean denominationBean : denominationBeanList) {
				//if (denominationBean.getNoOfNotes() != 0) {
				if (denominationBean.getNoOfNotes() !=null && !denominationBean.getNoOfNotes().equals("") && !denominationBean.getNoOfNotes().equals("0")) {
					avgExchageRateDT = avgExchageRateDT.add(denominationBean.getExchangeRate());
					i = 1;
					noteSize++;
				}
			}
			if (denominationBeanList.size() != 0) {
				//avgExchageRateDT = avgExchageRateDT / (denominationBeanList.size() - noteSize);
				avgExchageRateDT = avgExchageRateDT.divide(new BigDecimal(noteSize));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (i == 0) {
			setAvgExchageRate(null);
		}else{
			setAvgExchageRate(GetRound.roundBigDecimal(avgExchageRateDT, 9));
		}
	}
	
	public void searchOperationtoFcP() throws SQLException {
		setBooVisble(false);
		String userType=null;
		if((sessionStateManage.getBranchId()!=null || sessionStateManage.getCustomerType().equals("E")) && sessionStateManage.getRoleId().equals("1")){
			userType ="BRANCH"; 
		}else if(sessionStateManage.getBranchId()!=null  &&  sessionStateManage.getUserType().equalsIgnoreCase("K")){
			userType ="KIOSK";
		}else
		{
			//userType ="ONLINE";
		}

		log.info("getCustomerNo() :"+getCustomerId()+"\t sessionStateManage.getCountryId():"+sessionStateManage.getCountryId()+"\t sessionStateManage.getUserName() :"+sessionStateManage.getUserName());
		HashMap<String,String> customerValiMessage = iPersonalRemittanceService.getValidateCustomerProcedure(sessionStateManage.getCountryId(), getCustomerId(),sessionStateManage.getUserName(),userType);
		log.info("customerValiMessage :"+customerValiMessage);
		log.info("INDICATOR===="+customerValiMessage.get("INDICATOR"));

		if(customerValiMessage.get("ERROR_MESSAGE")!=null){
			setName("");
			setMobile("");
			setIdRef("");
			setBooVisble(true);
			setTempErrorMessage(customerValiMessage.get("ERROR_MESSAGE"));
			setErrmsg(customerValiMessage.get("ERROR_MESSAGE"));
			RequestContext.getCurrentInstance().execute("err.show();");
			return;
		}else if(customerValiMessage!=null && customerValiMessage.get("INDICATOR")!=null ){
			setName("");
			setMobile("");
			setIdRef("");
			setBooVisble(true);
			setTempErrorMessage(customerValiMessage.get("ERROR_MESSAGE"));
			setErrmsg(customerValiMessage.get("ERROR_MESSAGE"));
			if(customerValiMessage.get("INDICATOR").equalsIgnoreCase(Constants.Yes)){
				RequestContext.getCurrentInstance().execute("customerregproceed.show();");
			}
		}else{
			// allow
			setBooVisble(false);
			setTempErrorMessage(null);
		}

	}
	
	// checking whether Signature required for Cooperate Users
	public void renderDigitalSignatureBasedOnBranch(){
		Boolean corsSigIndCheck =  iPersonalRemittanceService.checkCorporateBranchForSignature(new BigDecimal(sessionStateManage.getBranchId()));
		if(corsSigIndCheck || sessionStateManage.getUserType().equalsIgnoreCase(Constants.E)){
			setSignaturePanel(false);
		}else{
			setSignaturePanel(true);
		}
	}
	
}