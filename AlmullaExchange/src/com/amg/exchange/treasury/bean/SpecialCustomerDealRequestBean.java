package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.mail.ApllicationMailer1;
import com.amg.exchange.mail.ApplicationMailer;
import com.amg.exchange.miscellaneous.service.IMiscellaneousReceiptPaymentService;
import com.amg.exchange.online.service.IGSMPlaceOrderRateFeedService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.service.IBankIndicatorService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.treasury.service.ISupplierService;
import com.amg.exchange.util.CollectionUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;





@Component("specialCustomerDealRequestBean")
/* @SessionScoped */
@Scope("session")
public class SpecialCustomerDealRequestBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(SpecialCustomerDealRequestBean.class);

	private SessionStateManage sessionStateManage = new SessionStateManage();

	private String id;
	private String name;
	private String bankName = null;
	private String currencyName = null;
	private String countryName = null;
	private String processIn = Constants.Yes;
	private String fcAmount = null;
	private String documentDescription = null;
	private BigDecimal documentNo = null;
	private BigDecimal documentSerialIdNumber = null;
	private Boolean aproveAll = false;
	private BigDecimal msgVal1 = null;
	private BigDecimal msgVal2 = null;
	private int documentSerialId;

	private int customerId = 0;
	private int customerTypeId = 0;
	private int finaceYear = 0;
	private int documentId = Integer.parseInt(Constants.DOCUMENT_CODE_FOR_SPL_CUSTOMER_DEAL_REQUEST);

	private BigDecimal companyId = null;
	private BigDecimal document = null;
	private BigDecimal financialYearId = null;
	private BigDecimal bankCountry = null;
	private BigDecimal bankId = null;
	private BigDecimal bankAccountNumber = null;
	private BigDecimal currencyId = null;

	private Boolean booleanApprove = false;
	private Boolean booRenderMandatory = false;
	private Boolean booRenderDataTable = false;
	private Boolean booSpCustomer = false;
	private Boolean isFromSpecialCustomer = false;
	private Boolean renderSaveButton = false;
	private Boolean approveAllPanel = false;
	private BigDecimal documentSerialIdNumberForDataTable = null;
	private boolean booAddButton = false;
	private boolean booUpdateButton = false;
	private Boolean readOnlyCustometId = false;
	private Boolean disableSearchButton = false;
	private Boolean booCivilIdInput = false;
	private Boolean booCivilIdOnBlur = false;
	private BigDecimal avgRate;
	private Boolean visableSet=false;

	private String nameForUpdate = null;
	private BigDecimal accountNumberForUpdate = null;
	private String bankCurrencyNameForUpdate = null;

	private boolean booSystemGen = false;
	private boolean booUpdateSalesProj = false;
	private String manualDocumentSerialId = null;

	private Date validUpTo = null;
	private Date requestDate = null;
	private String custType = null;

	private Boolean booRenderFirstPanel = false;
	private Boolean booRenderSecondPanel = false;
	private Boolean displayDynamicColor = false;

	private String bankCountryForUpdate = null;
	private String bankNameForUpdate = null;
	private String finaceYearForUpdate = null;
	private String documentDescriptionForUpdate = null;
	private String companyNameForUpdate = null;
	private BigDecimal fcAmountForUpdate = null;
	private Date validUpToForUpdate = null;
	private String companyListFromDB = null;
	private String errMsg;
	private BigDecimal fundMaxRate;
	private BigDecimal fundMinRate;
	private Date valueDate;
	private Date effetiveMinDate= new Date();



	private List<UserFinancialYear> financialYearList = new ArrayList<UserFinancialYear>();
	private List<BankCountryPopulationBean> bankCountryList = new ArrayList<BankCountryPopulationBean>();
	private List<BankMaster> bankAccordingToBankCountry = new ArrayList<BankMaster>();
	private List<BankAccountDetails> currencyOfBank = new ArrayList<BankAccountDetails>();
	private List<CustomerIdProof> customerData = new ArrayList<CustomerIdProof>();
	private List<SpecialCustomerDealRequestBeanDataTable> specialCustomerDealRequestBeanDataTable = new ArrayList<SpecialCustomerDealRequestBeanDataTable>();
	private List<BankAccountDetails> lstAccountNumber = new ArrayList<BankAccountDetails>();
	private Map<BigDecimal, String> mapAccountDetails = new HashMap<BigDecimal, String>();
	private List<CustomerSpecialDealRequest> customerSpecialDealRequest = new ArrayList<CustomerSpecialDealRequest>();
	private List<Document> documentDesc = new ArrayList<Document>();

	private Map<BigDecimal, String> bankCountryMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> bankMap = new HashMap<BigDecimal, String>();
	private BigDecimal customerSpecialDealReqId;

	private int accNumberSize;
	private Boolean boobankAcc = true;
	private Boolean booSelectbankAcc = false;
	private String accountNumber = null;
	private BigDecimal accountId = null;

	private int bankcurrencySize;
	private Boolean boobankCurrency = true;
	private Boolean booSelectbankCurrency = false;
	private String bankCurrencyName = null;
	private BigDecimal bankCurrencyId = null;
	private Boolean booCancelButton = false;
	private String civilIdForUpdate = null;

	private Date requestDateForUpdate = null;
	private Boolean boorequestedDate = false;
	private Boolean boorequestedDateForUpadate = false;
	private Boolean renderValueDateOption=false;

	/*
	 * @Autowired ForeignCurrencyPurchaseBean<T> foreignCurrencyPurchaseBean;
	 */

	@SuppressWarnings("rawtypes")
	@Autowired
	IMiscellaneousReceiptPaymentService miscellaneousReceiptPaymentService;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IFundEstimationService<T> fundEstimationService;

	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;

	@Autowired
	SupplierBean<T> supplierBean;

	@Autowired
	ISupplierService<T> iSupplierService;
	
	@Autowired
	IGSMPlaceOrderRateFeedService gSMPlaceOrderRateFeedService;
	
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	
	@Autowired
	ApplicationMailer applicationMailer;
	
	@Autowired
	ApllicationMailer1 apllicationMailer1;

	@Autowired
	IBankIndicatorService bankIndicatorService;
	
	@Autowired
	ICustomerRegistrationBranchService customerRegistrationBranchService;

	// modification
	private Boolean booRenderDocument = false;
	private Boolean booRenderAcno = false;
	private Boolean booRenderDocumentForUpdate = false;
	private Boolean boorenderAcnoForUpdate = false;



	public void setDocumentSerialId(int documentSerialId) {
		this.documentSerialId = documentSerialId;
	}


	public Boolean getVisableSet() {
		return visableSet;
	}

	public void setVisableSet(Boolean visableSet) {
		this.visableSet = visableSet;
	}

	public BigDecimal getAvgRate() {
		return avgRate;
	}

	public void setAvgRate(BigDecimal avgRate) {
		this.avgRate = avgRate;
	}

	public Boolean getRenderValueDateOption() {
		return renderValueDateOption;
	}

	public void setRenderValueDateOption(Boolean renderValueDateOption) {
		this.renderValueDateOption = renderValueDateOption;
	}

	public Boolean getDisplayDynamicColor() {
		return displayDynamicColor;
	}

	public void setDisplayDynamicColor(Boolean displayDynamicColor) {
		this.displayDynamicColor = displayDynamicColor;
	}

	public List<BankAccountDetails> getCurrencyOfBank() {
		return currencyOfBank;
	}

	public void setCurrencyOfBank(List<BankAccountDetails> currencyOfBank) {
		this.currencyOfBank = currencyOfBank;
	}

	public Boolean getBooRenderDocument() {
		return booRenderDocument;
	}

	public void setBooRenderDocument(Boolean booRenderDocument) {
		this.booRenderDocument = booRenderDocument;
	}

	public Boolean getBooRenderAcno() {
		return booRenderAcno;
	}

	public void setBooRenderAcno(Boolean booRenderAcno) {
		this.booRenderAcno = booRenderAcno;
	}

	public Boolean getBooRenderDocumentForUpdate() {
		return booRenderDocumentForUpdate;
	}

	public void setBooRenderDocumentForUpdate(Boolean booRenderDocumentForUpdate) {
		this.booRenderDocumentForUpdate = booRenderDocumentForUpdate;
	}

	public Boolean getBoorenderAcnoForUpdate() {
		return boorenderAcnoForUpdate;
	}

	public void setBoorenderAcnoForUpdate(Boolean boorenderAcnoForUpdate) {
		this.boorenderAcnoForUpdate = boorenderAcnoForUpdate;
	}

	public BigDecimal getAccountNumberForUpdate() {
		return accountNumberForUpdate;
	}


	public BigDecimal getFundMaxRate() {
		return fundMaxRate;
	}

	public BigDecimal getFundMinRate() {
		return fundMinRate;
	}

	public void setFundMaxRate(BigDecimal fundMaxRate) {
		this.fundMaxRate = fundMaxRate;
	}

	public void setFundMinRate(BigDecimal fundMinRate) {
		this.fundMinRate = fundMinRate;
	}

	public void setAccountNumberForUpdate(BigDecimal accountNumberForUpdate) {
		this.accountNumberForUpdate = accountNumberForUpdate;
	}

	public String getBankCurrencyNameForUpdate() {
		return bankCurrencyNameForUpdate;
	}

	public void setBankCountryList(List<BankCountryPopulationBean> bankCountryList) {
		this.bankCountryList = bankCountryList;
	}

	public List<BankCountryPopulationBean> getBankCountryList() {
		return bankCountryList;
	}

	public void setBankCurrencyNameForUpdate(String bankCurrencyNameForUpdate) {
		this.bankCurrencyNameForUpdate = bankCurrencyNameForUpdate;
	}

	public String getNameForUpdate() {
		return nameForUpdate;
	}

	public void setNameForUpdate(String nameForUpdate) {
		this.nameForUpdate = nameForUpdate;
	}

	public Date getValidUpToForUpdate() {
		return validUpToForUpdate;
	}

	public void setValidUpToForUpdate(Date validUpToForUpdate) {
		this.validUpToForUpdate = validUpToForUpdate;
	}

	public Boolean getBooRenderFirstPanel() {
		return booRenderFirstPanel;
	}

	public void setBooRenderFirstPanel(Boolean booRenderFirstPanel) {
		this.booRenderFirstPanel = booRenderFirstPanel;
	}

	public Boolean getBooRenderSecondPanel() {
		return booRenderSecondPanel;
	}

	public void setBooRenderSecondPanel(Boolean booRenderSecondPanel) {
		this.booRenderSecondPanel = booRenderSecondPanel;
	}

	public BigDecimal getFcAmountForUpdate() {
		return fcAmountForUpdate;
	}

	public void setFcAmountForUpdate(BigDecimal fcAmountForUpdate) {
		this.fcAmountForUpdate = fcAmountForUpdate;
	}

	public String getDocumentDescriptionForUpdate() {
		return documentDescriptionForUpdate;
	}

	public void setDocumentDescriptionForUpdate(String documentDescriptionForUpdate) {
		this.documentDescriptionForUpdate = documentDescriptionForUpdate;
	}

	public String getCompanyNameForUpdate() {
		return companyNameForUpdate;
	}

	public void setCompanyNameForUpdate(String companyNameForUpdate) {
		this.companyNameForUpdate = companyNameForUpdate;
	}

	public String getFinaceYearForUpdate() {
		return finaceYearForUpdate;
	}

	public void setFinaceYearForUpdate(String finaceYearForUpdate) {
		this.finaceYearForUpdate = finaceYearForUpdate;
	}

	public Date getRequestDateForUpdate() {
		return requestDateForUpdate;
	}

	public void setRequestDateForUpdate(Date requestDateForUpdate) {
		this.requestDateForUpdate = requestDateForUpdate;
	}

	public Boolean getBoorequestedDate() {
		return boorequestedDate;
	}

	public void setBoorequestedDate(Boolean boorequestedDate) {
		this.boorequestedDate = boorequestedDate;
	}

	public Boolean getBoorequestedDateForUpadate() {
		return boorequestedDateForUpadate;
	}

	public void setBoorequestedDateForUpadate(Boolean boorequestedDateForUpadate) {
		this.boorequestedDateForUpadate = boorequestedDateForUpadate;
	}

	public Boolean getBooCivilIdOnBlur() {
		return booCivilIdOnBlur;
	}

	public void setBooCivilIdOnBlur(Boolean booCivilIdOnBlur) {
		this.booCivilIdOnBlur = booCivilIdOnBlur;
	}

	public String getCivilIdForUpdate() {
		return civilIdForUpdate;
	}

	public void setCivilIdForUpdate(String civilIdForUpdate) {
		this.civilIdForUpdate = civilIdForUpdate;
	}

	public Boolean getBooCivilIdInput() {
		return booCivilIdInput;
	}

	public void setBooCivilIdInput(Boolean booCivilIdInput) {
		this.booCivilIdInput = booCivilIdInput;
	}

	public Boolean getBooCancelButton() {
		return booCancelButton;
	}

	public void setBooCancelButton(Boolean booCancelButton) {
		this.booCancelButton = booCancelButton;
	}

	public String getBankCountryForUpdate() {
		return bankCountryForUpdate;
	}

	public void setBankCountryForUpdate(String bankCountryForUpdate) {
		this.bankCountryForUpdate = bankCountryForUpdate;
	}

	public String getBankNameForUpdate() {
		return bankNameForUpdate;
	}

	public void setBankNameForUpdate(String bankNameForUpdate) {
		this.bankNameForUpdate = bankNameForUpdate;
	}

	public Boolean getDisableSearchButton() {
		return disableSearchButton;
	}

	public void setDisableSearchButton(Boolean disableSearchButton) {
		this.disableSearchButton = disableSearchButton;
	}

	public Boolean getReadOnlyCustometId() {
		return readOnlyCustometId;
	}

	public void setReadOnlyCustometId(Boolean readOnlyCustometId) {
		this.readOnlyCustometId = readOnlyCustometId;
	}

	public boolean isBooAddButton() {
		return booAddButton;
	}

	public void setBooAddButton(boolean booAddButton) {
		this.booAddButton = booAddButton;
	}

	public boolean isBooUpdateButton() {
		return booUpdateButton;
	}

	public void setBooUpdateButton(boolean booUpdateButton) {
		this.booUpdateButton = booUpdateButton;
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

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getAccountId() {
		return accountId;
	}

	public void setAccountId(BigDecimal accountId) {
		this.accountId = accountId;
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

	public String getManualDocumentSerialId() {
		return manualDocumentSerialId;
	}

	public void setManualDocumentSerialId(String manualDocumentSerialId) {
		this.manualDocumentSerialId = manualDocumentSerialId;
	}

	public List<CustomerIdProof> getCustomerData() {
		return customerData;
	}

	public void setCustomerData(List<CustomerIdProof> customerData) {
		this.customerData = customerData;
	}

	public void setBankAccordingToBankCountry(List<BankMaster> bankAccordingToBankCountry) {
		this.bankAccordingToBankCountry = bankAccordingToBankCountry;
	}

	public List<UserFinancialYear> getFinancialYearList() {
		return financialYearList;
	}

	public void setFinancialYearList(List<UserFinancialYear> financialYearList) {
		this.financialYearList = financialYearList;
	}

	public List<BankAccountDetails> getLstAccountNumber() {
		return lstAccountNumber;
	}

	public void setLstAccountNumber(List<BankAccountDetails> lstAccountNumber) {
		this.lstAccountNumber = lstAccountNumber;
	}

	public List<SpecialCustomerDealRequestBeanDataTable> getSpecialCustomerDealRequestBeanDataTable() {
		return specialCustomerDealRequestBeanDataTable;
	}

	public void setSpecialCustomerDealRequestBeanDataTable(List<SpecialCustomerDealRequestBeanDataTable> specialCustomerDealRequestBeanDataTable) {
		this.specialCustomerDealRequestBeanDataTable = specialCustomerDealRequestBeanDataTable;
	}

	public BigDecimal getDocumentSerialIdNumberForDataTable() {
		return documentSerialIdNumberForDataTable;
	}

	public void setDocumentSerialIdNumberForDataTable(BigDecimal documentSerialIdNumberForDataTable) {
		this.documentSerialIdNumberForDataTable = documentSerialIdNumberForDataTable;
	}

	public BigDecimal getMsgVal2() {
		return msgVal2;
	}

	public void setMsgVal2(BigDecimal msgVal2) {
		this.msgVal2 = msgVal2;
	}

	public BigDecimal getMsgVal1() {
		return msgVal1;
	}

	public void setMsgVal1(BigDecimal msgVal1) {
		this.msgVal1 = msgVal1;
	}

	public Boolean getApproveAllPanel() {
		return approveAllPanel;
	}

	public void setApproveAllPanel(Boolean approveAllPanel) {
		this.approveAllPanel = approveAllPanel;
	}

	public Boolean getAproveAll() {
		return aproveAll;
	}

	public void setAproveAll(Boolean aproveAll) {
		this.aproveAll = aproveAll;
	}

	public Date getRequestDate() {
		return new Date();
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public String getDocumentDescription() {
		return documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}

	public Boolean getRenderSaveButton() {
		return renderSaveButton;
	}

	public void setRenderSaveButton(Boolean renderSaveButton) {
		this.renderSaveButton = renderSaveButton;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public String getBankName() {
		return bankName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Boolean getBooRenderMandatory() {
		return booRenderMandatory;
	}

	public void setBooRenderMandatory(Boolean booRenderMandatory) {
		this.booRenderMandatory = booRenderMandatory;
	}

	public Boolean getIsFromSpecialCustomer() {
		return isFromSpecialCustomer;
	}

	public void setIsFromSpecialCustomer(Boolean isFromSpecialCustomer) {
		this.isFromSpecialCustomer = isFromSpecialCustomer;
	}

	public BigDecimal getDocument() {
		return document;
	}

	public void setDocument(BigDecimal document) {
		this.document = document;
	}

	public String getFcAmount() {

		return fcAmount;

	}

	public void setFcAmount(String fcAmount) {
		this.fcAmount = fcAmount;
	}

	public BigDecimal getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(BigDecimal bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public Date getValidUpTo() {
		return validUpTo;
	}

	public void setValidUpTo(Date validUpTo) {
		this.validUpTo = validUpTo;
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

	public BigDecimal getBankCountry() {
		return bankCountry;
	}

	public void setBankCountry(BigDecimal bankCountry) {
		this.bankCountry = bankCountry;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

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

	public Boolean getBooSpCustomer() {
		return booSpCustomer;
	}

	public void setBooSpCustomer(Boolean booSpCustomer) {
		this.booSpCustomer = booSpCustomer;
	}

	public int getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(int customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public BigDecimal getCustomerSpecialDealReqId() {
		return customerSpecialDealReqId;
	}

	public void setCustomerSpecialDealReqId(BigDecimal customerSpecialDealReqId) {
		this.customerSpecialDealReqId = customerSpecialDealReqId;
	}

	public BigDecimal getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(BigDecimal financialYearId) {
		this.financialYearId = financialYearId;
	}

	public Boolean getBooleanApprove() {
		return booleanApprove;
	}

	public void setBooleanApprove(Boolean booleanApprove) {
		this.booleanApprove = booleanApprove;
	}


	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getDocumentSerialID(String processIn){

		/*String documentSerialId=null;
		Boolean visableSet = false;

		log.info("process in :" + processIn);//
		 try {
			HashMap<String, String> outPutValues= generalService.getNextDocumentReferenceNumber(Integer.parseInt(sessionStateManage.getSessionValue("countryId")), Integer.parseInt(sessionStateManage.getSessionValue("companyId")), documentId, getFinaceYear(), processIn,sessionStateManage.getCountryBranchCode());

			String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
			if(proceErrorMsg!=null)
			{
				setErrMsg(proceErrorMsg);
				visableSet = true;
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}else {
				visableSet = false;
				documentSerialId=outPutValues.get("DOCNO");
			}
		} catch (NumberFormatException | AMGException e) {
			visableSet = true;
			setErrMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
		}
		 setVisableSet(visableSet);
		return documentSerialId;*/
		String documentSerialID=null;
		try{
			documentSerialID = generalService.getNextDocumentReferenceNumber(Integer.parseInt(sessionStateManage.getSessionValue("countryId")), Integer.parseInt(sessionStateManage.getSessionValue("companyId")), documentId, getFinaceYear(), processIn,sessionStateManage.getCountryBranchCode());
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::getDocumentSerialID");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return null; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;       
		}
		return documentSerialID;

	}

	public int getDocumentSerialId() throws Exception {
		int documentNo = 0;
		String documentSerial = getDocumentSerialID(processIn);
		if(documentSerial!=null && documentSerial!=""){
			documentNo = Integer.parseInt(documentSerial);
		}
		return documentNo;
	}


	public void checkRelationcodeisNumber(FacesContext context, UIComponent component, Object value) {
		try {
			BigDecimal val = new BigDecimal(value.toString());
			@SuppressWarnings("unused")
			boolean isNUmber = CollectionUtil.isIntegerValue(val);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(" Sale Rate should be number", " Sale Rate should be number");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}




	public int getFinaceYear() {
		return finaceYear;
	}

	public BigDecimal getDocumentSerialIdNumber() throws Exception {

		BigDecimal value = new BigDecimal(getDocumentSerialId());
		setDocumentSerialIdNumberForDataTable(new BigDecimal(10));
		return value;
	}

	public void setDocumentSerialIdNumber(BigDecimal documentSerialIdNumber) {
		this.documentSerialIdNumber = documentSerialIdNumber;
	}

	public void setFinaceYear(int finaceYear) {
		this.finaceYear = finaceYear;
	}

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}


	public void idOnBlur(AjaxBehaviorEvent event) {
		try{
			List<Customer>	customerData = iSupplierService.getCustomerDetailsBasedOnReference(new BigDecimal(getId()));
			//	customerData = specialCustomerDealRequestService.dataCust(customerInfo.get(0).get);
			if (customerData != null && customerData.size() > 0) {

				Customer customerDT = customerData.get(0);

				setName(nullCheck(customerDT.getFirstName()) + " " + nullCheck(customerDT.getMiddleName()) + " " + nullCheck(customerDT.getLastName()));
				setCustomerId(Integer.parseInt(customerDT.getCustomerId().toString()));
				setCustomerTypeId(Integer.parseInt(customerDT.getFsBizComponentDataByCustomerTypeId().getComponentDataId().toString()));
				// TODO
				if (getCustomerTypeId() == generalService.getComponentId(Constants.CUSTOMERTYPE_CORP, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()) {
					custType = "C";
				} else if (getCustomerTypeId() == generalService.getComponentId(Constants.CUSTOMERTYPE_INDU, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()) {
					custType = "I";
				}
				validDate(specialCustomerDealRequestService.getValidUpTo(custType));
			} else {
				setName("");
				setValidUpTo(null);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("idNotFound.show();");

			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void getDocDetails(AjaxBehaviorEvent event) {
		try{
			setBooRenderFirstPanel(false);
			setBooRenderSecondPanel(true);
			setNullValues();

			if(getFinaceYearForUpdate() != null && getManualDocumentSerialId() != null){

				//List<UserFinancialYear> lstUserFinancial = miscellaneousReceiptPaymentService.getFinanacilYearId(new BigDecimal(getFinaceYearForUpdate()));
				
				BigDecimal documentPk = specialCustomerDealRequestService.getDocumentPk(new BigDecimal(Constants.DOCUMENT_CODE_FOR_SPL_CUSTOMER_DEAL_REQUEST));
				
				if(documentPk != null && documentPk.compareTo(BigDecimal.ZERO) != 0){

					//UserFinancialYear userFinancialYearId = lstUserFinancial.get(0);

					//BigDecimal financialYearId = userFinancialYearId.getFinancialYearID();

					List<CustomerSpecialDealRequest> lstCustomerSpecialDealRequest = specialCustomerDealRequestService.getCustSpDocNo(new BigDecimal(getFinaceYearForUpdate()),new BigDecimal(getManualDocumentSerialId()),sessionStateManage.getCompanyId(),documentPk);

					if (lstCustomerSpecialDealRequest != null && lstCustomerSpecialDealRequest.size() > 0) {
						for (CustomerSpecialDealRequest custSpDealReq : lstCustomerSpecialDealRequest) {
							setFinaceYearForUpdate(specialCustomerDealRequestService.getUserFinancialYearForUpadate(custSpDealReq.getDocumentFinancialYear().getFinancialYearID()));
							setCompanyNameForUpdate(specialCustomerDealRequestService.getCompanyNameForUpdate(custSpDealReq.getCustomerSpeacialDealReqCompanyMaster().getCompanyId()));
							setDocumentDescriptionForUpdate(specialCustomerDealRequestService.getDocumentNameForUpdate(custSpDealReq.getCustomerSpeacialDealReqDocument().getDocumentID()));
							setManualDocumentSerialId(getManualDocumentSerialId());
							setRequestDateForUpdate((custSpDealReq.getProjectionDate()));
							//setCivilIdForUpdate(specialCustomerDealRequestService.getCivilIdForUpadate(custSpDealReq.getCustomerSpeacialDealReqCustomer().getCustomerId()));
							setCivilIdForUpdate(custSpDealReq.getCustomerSpeacialDealReqCustomer().getCustomerReference().toPlainString());
							setNameForUpdate(nullCheck(custSpDealReq.getCustomerSpeacialDealReqCustomer().getFirstName()) + " " + nullCheck(custSpDealReq.getCustomerSpeacialDealReqCustomer().getMiddleName()) + " " + nullCheck(custSpDealReq.getCustomerSpeacialDealReqCustomer().getLastName()));
							setValidUpToForUpdate((custSpDealReq.getValidUpto()));
							setBankCountryForUpdate(specialCustomerDealRequestService.getBankCountryNameForUpdate(custSpDealReq.getCustomerSpeacialDealReqCountryMaster().getCountryId(),sessionStateManage.getLanguageId()));
							setBankNameForUpdate(specialCustomerDealRequestService.getBankNameForUpdate((custSpDealReq.getCustomerSpeacialDealReqBankMaster().getBankId())));
							setBankCurrencyNameForUpdate(specialCustomerDealRequestService.getCurrencyForUpdate((custSpDealReq.getCustomerSpeacialDealReqCurrencyMaster().getCurrencyId())));
							setFcAmountForUpdate(custSpDealReq.getForeignCurrencyAmount());
							if(custSpDealReq.getTentiveSaleRate()!=null){
								setTentativeSaleRateForUpdate(custSpDealReq.getTentiveSaleRate().toString());
							}
							if( !(getBankCountryForUpdate().equalsIgnoreCase( Constants.KUWAIT))){
								if(custSpDealReq.getValueDateOption()!=null){
									if((custSpDealReq.getValueDateOption().equalsIgnoreCase( Constants.CASH))||(custSpDealReq.getValueDateOption().equalsIgnoreCase( Constants.SPOT)) || (custSpDealReq.getValueDateOption().equalsIgnoreCase( Constants.TOM)) )
										setRenderValueDateOption( true);
									setValueDateOptionForUpdate(custSpDealReq.getValueDateOption());
								}else{
									setRenderValueDateOption(false);
								}
							}
						}
						setBooAddButton(false);
						setBooUpdateButton(true);
						lstCustomerSpecialDealRequest.clear();
					} else {
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("DocNoNotFound.show();");
					}
				}
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::getDocDetails");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void searchClicked() {

		log.info("Entering into searchClicked method ");
		try {
			/*
			 * foreignCurrencyPurchaseBean.setBooFc(false);
			 * foreignCurrencyPurchaseBean.setBooReg(false);
			 */
			setBooSpCustomer(true);
			setIsFromSpecialCustomer(true);
			/*
			 * supplierBean.setBooSupplierBean(false);
			 * supplierBean.setIsFromSupplierCustref(true);
			 * searchCustomerManageBean.setBooSearchCustomer(false);
			 * searchCustomerManageBean.setBooPass(false);
			 * searchCustomerManageBean.setFinalData(null);
			 */

			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("request", "specialCustomerDealRequest");

			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../search/searchcustomer.xhtml");

		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

		log.info("Exit into searchClicked method ");
	}

	public void validDate(int id) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, id);
		setValidUpTo(cal.getTime());
	}

	public void setCompanyListFromDB(String companyListFromDB) {
		this.companyListFromDB = companyListFromDB;
	}

	public String getCompanyListFromDB() {
		return companyListFromDB;
	}

	public void bankCurrency() {
		try{
			bankChange();
			List<BankAccountDetails> pbankcurrency = fundEstimationService.getCurrencyOfBank(getBankId());
			bankcurrencySize = pbankcurrency.size();
			if (bankcurrencySize == 0) {
				List<BankAccountDetails> bankAccList = specialCustomerDealRequestService.getCurrencyBasedOnCountry(getBankCountry());
				int bankAcountListSize = bankAccList.size();
				if (bankAcountListSize > 0) {
					if (bankAcountListSize == 1) {
						for (BankAccountDetails element : bankAccList) {
							setBankCurrencyId(element.getFsCurrencyMaster().getCurrencyId());
							setBankCurrencyName(element.getFsCurrencyMaster().getCurrencyName());
						}
						setCurrencyId(getBankCurrencyId());
						setCurrencyName(getBankCurrencyName());
						setBoobankCurrency(true);
						setBooSelectbankCurrency(false);

					} else {
						setCurrencyOfBank(bankAccList);
						setBoobankCurrency(false);
						setBooSelectbankCurrency(true);
					}
				}else{
					RequestContext.getCurrentInstance().execute("noCurrency.show();");
					return;
				}

			} else if (bankcurrencySize == 1) {

				for (BankAccountDetails element : pbankcurrency) {
					setBankCurrencyId(element.getFsCurrencyMaster().getCurrencyId());
					setBankCurrencyName(element.getFsCurrencyMaster().getCurrencyName());
				}
				setCurrencyId(getBankCurrencyId());
				setCurrencyName(getBankCurrencyName());
				setBoobankCurrency(true);
				setBooSelectbankCurrency(false);
				populateAccountNumber();

			} else {
				setCurrencyOfBank(pbankcurrency);
				setBoobankCurrency(false);
				setBooSelectbankCurrency(true);
			}

			getBankAvgRate();
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::bankCurrency");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void populateAccountNumber() throws Exception {
		List<BankAccountDetails> ptabledata = fundEstimationService.getAccountNumber(getBankId(), getCurrencyId());

		for (BankAccountDetails bankAcDetails : currencyOfBank) {
			if(bankAcDetails.getFsCurrencyMaster().getCurrencyId()!=null){
				if (bankAcDetails.getFsCurrencyMaster().getCurrencyId().equals(getCurrencyId()))
					setCurrencyName(bankAcDetails.getFsCurrencyMaster().getCurrencyName());
			}
		}
		accNumberSize = ptabledata.size();
		if (accNumberSize <= 1) {

			for (BankAccountDetails element : ptabledata) {
				setAccountId(element.getBankAcctDetId());
				setAccountNumber(element.getBankAcctNo());
			}
			setBankAccountNumber(getAccountId());
			setBoobankAcc(true);
			setBooSelectbankAcc(false);

		} else {
			setBoobankAcc(false);
			setBooSelectbankAcc(true);
		}
		setLstAccountNumber(ptabledata);

		getBankAvgRate();
	}

	public void preProcessingMethods() {
		try{
			// for document description
			documentDesc = specialCustomerDealRequestService.getDocumentDescription(new BigDecimal(Constants.DOCUMENT_CODE_FOR_SPL_CUSTOMER_DEAL_REQUEST), new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
			for (Document des : documentDesc) {
				setDocumentNo(des.getDocumentCode());
				setDocumentDescription(des.getDocumentDesc());
			}

			// for financial year
			try {
				financialYearList = specialCustomerDealRequestService.getUserFinancialYear(new Date());
				log.info("financialYearList :" + financialYearList.size());
				if (financialYearList != null)
					finaceYear = Integer.parseInt(financialYearList.get(0).getFinancialYear().toString());
				financialYearId = financialYearList.get(0).getFinancialYearID();
				setFinancialYearId(financialYearId);
				setFinaceYear(finaceYear);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// request date
			setRequestDate(new Date());

			// for company name
			List<CompanyMasterDesc> data = specialCustomerDealRequestService.getAllCompanyList(sessionStateManage.getCompanyId(), new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
			setCompanyId(data.get(0).getFsCompanyMaster().getCompanyId());
			setCompanyListFromDB(data.get(0).getCompanyName());

			// bankCountrylist
			List<BankCountryPopulationBean> bankCountryList = fundEstimationService.getBankContryFromView(sessionStateManage.getCountryId());
			for (BankCountryPopulationBean abnkCBean : bankCountryList) {
				bankCountryMap.put(abnkCBean.getBankCountryId(), abnkCBean.getBankCountryName());
			}
			setBankCountryList(bankCountryList);
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::preProcessingMethods");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	public void clearFields(){
		setBooRenderFirstPanel(true);
		setBooRenderSecondPanel(false);
		setBankAccountNumber(null);
		setCurrencyId(null);
		setBankCurrencyName(null);
		setBoobankCurrency(true);
		setBooSelectbankCurrency(false);
		setAccountNumber(null);
		setBoobankAcc(true);
		setBooSelectbankAcc(false);
		setId(null);
		setName(null);
		setBankId(null);
		setBankCountry(null);
		setCurrencyId(null);
		setValidUpTo(null);
		setBankAccountNumber(null);
		setFcAmount(null);
		setCurrencyId(null);
		setBankCurrencyName(null);
		setBankAccountNumber(null);
		setAccountNumber(null);
		bankAccordingToBankCountry.clear();
		currencyOfBank.clear();
		bankMap.clear();
		bankCountryMap.clear();
		bankBasedOnCountry.clear();
		specialCustomerDealRequestBeanDataTable.clear();
		setBooRenderDataTable(false);
		setRenderSaveButton(false);
		setAvgRate(null);
	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void specialCustomerPageNavigation() {
		setRenderValueDateOption( false);
		clearFields();
		setTentativeSaleRate(null);
		preProcessingMethods();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "specialCustomer.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/specialCustomer.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void bankChange() {

		setCurrencyId(null);
		setBankCurrencyName(null);
		setBoobankCurrency(true);
		setBooSelectbankCurrency(false);

		setAccountNumber(null);
		setBoobankAcc(true);
		setBooSelectbankAcc(false);

	}

	public void saveRecords() {
		try{
			if (specialCustomerDealRequestBeanDataTable.isEmpty()) {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("dataNotFund.show();");
			} else {

				for (SpecialCustomerDealRequestBeanDataTable splCustDealReq : specialCustomerDealRequestBeanDataTable) {
					
					CustomerSpecialDealRequest customerSpecialDealRequest = new CustomerSpecialDealRequest();
					
					if(splCustDealReq.getBankName()!=null){
						BankMaster customerSpeacialDealReqBankMaster = new BankMaster();
						customerSpeacialDealReqBankMaster.setBankId(new BigDecimal(splCustDealReq.getBankName()));
						customerSpecialDealRequest.setCustomerSpeacialDealReqBankMaster(customerSpeacialDealReqBankMaster);
					}
					
					if(splCustDealReq.getCurrencyName()!=null){
						CurrencyMaster customerSpeacialDealReqCurrencyMaster = new CurrencyMaster();
						customerSpeacialDealReqCurrencyMaster.setCurrencyId(new BigDecimal(splCustDealReq.getCurrencyName()));
						customerSpecialDealRequest.setCustomerSpeacialDealReqCurrencyMaster(customerSpeacialDealReqCurrencyMaster);
					}
					
					CountryMaster bankCountryMaster = new CountryMaster();
					bankCountryMaster.setCountryId(splCustDealReq.getCountry());
					customerSpecialDealRequest.setCustomerSpeacialDealReqCountryMaster(bankCountryMaster);

					CountryMaster applicationCountryCountryMaster = new CountryMaster();
					applicationCountryCountryMaster.setCountryId(sessionStateManage.getCountryId());
					customerSpecialDealRequest.setApplicationCountryCountryMaster(applicationCountryCountryMaster);

					CompanyMaster customerSpeacialDealReqCompanyMaster = new CompanyMaster();
					customerSpeacialDealReqCompanyMaster.setCompanyId(splCustDealReq.getCompanyId());
					customerSpecialDealRequest.setCustomerSpeacialDealReqCompanyMaster(customerSpeacialDealReqCompanyMaster);

					Customer customerSpeacialDealReqCustomer = new Customer();
					customerSpeacialDealReqCustomer.setCustomerId(splCustDealReq.getCustomerId());
					customerSpecialDealRequest.setCustomerSpeacialDealReqCustomer(customerSpeacialDealReqCustomer);

					UserFinancialYear documentFinancialYear = new UserFinancialYear();
					documentFinancialYear.setFinancialYearID(splCustDealReq.getSpecialCustomerDealRequestyearId());
					customerSpecialDealRequest.setDocumentFinancialYear(documentFinancialYear);
					
					customerSpecialDealRequest.setDocumentFinanYear(splCustDealReq.getSpecialCustomerDealRequestyear());

					Document document = new Document();
					BigDecimal documentPk = specialCustomerDealRequestService.getDocumentPk(splCustDealReq.getDocument());
					document.setDocumentID(documentPk);
					customerSpecialDealRequest.setCustomerSpeacialDealReqDocument(document);

					customerSpecialDealRequest.setValueDateOption(splCustDealReq.getValueDateOption());
					
					if(splCustDealReq.getTentativeSaleRate()!=null){
						customerSpecialDealRequest.setTentiveSaleRate(new BigDecimal(splCustDealReq.getTentativeSaleRate()));
					}
					
					customerSpecialDealRequest.setHighValueTrnx(Constants.SPL_CUSTOMER_HIGH_VALUE_TRNX);
					customerSpecialDealRequest.setForeignCurrencyAmount(splCustDealReq.getForeignCurrencyAmount());
					customerSpecialDealRequest.setDocumentNumber(splCustDealReq.getDocumentNo());
					customerSpecialDealRequest.setValidUpto(splCustDealReq.getValidUpTo());
					customerSpecialDealRequest.setCreatedBy(sessionStateManage.getUserName());
					customerSpecialDealRequest.setCreatedDate(new Date());
					customerSpecialDealRequest.setProjectionDate(new Date());
					customerSpecialDealRequest.setUtilizedAmount(BigDecimal.ZERO);

					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					Date valDate = formatter.parse(splCustDealReq.getValueDate() );
					customerSpecialDealRequest.setValueDate(valDate);

					if (splCustDealReq.getAprove() == true) {
						customerSpecialDealRequest.setApprovedBy(sessionStateManage.getUserName());
						customerSpecialDealRequest.setApprovedDate(new Date());
					} else {
						customerSpecialDealRequest.setApprovedBy("");
						customerSpecialDealRequest.setApprovedDate(null);
					}
					
					specialCustomerDealRequestService.saveData(customerSpecialDealRequest);
					
					if(splCustDealReq.getCustomerId() != null){
						String beneBankName=null;
						String brachName= null;
						BigDecimal customerRef = null;
						List<Customer> custmer = null;
						if(splCustDealReq.getBankName() != null){
							beneBankName = generalService.getBankName(new BigDecimal(splCustDealReq.getBankName()));
						}
						brachName=gSMPlaceOrderRateFeedService.toFetchBranchName(new BigDecimal(sessionStateManage.getBranchId()));
						if(splCustDealReq.getCustomerId() != null){
							custmer = customerRegistrationBranchService.getCustomerInfo(splCustDealReq.getCustomerId());
							if(custmer != null && custmer.size() != 0){
								customerRef = custmer.get(0).getCustomerReference();
							}
						}
						

						HashMap<String, String> inputValues=new HashMap<String, String>();
						SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						inputValues.put("DocumentNo_Year", splCustDealReq.getDocumentNo() + " / " +splCustDealReq.getSpecialCustomerDealRequestyear());
						inputValues.put("EmailId", "exch-treasury@almullagroup.com");
						inputValues.put("RateOffered", "Special Deal Information");
						inputValues.put("User", sessionStateManage.getUserName());
						inputValues.put("Name", splCustDealReq.getCustomerName() + " / " + customerRef);
						inputValues.put("Bank", beneBankName);
						inputValues.put("Branch Name", brachName);
						inputValues.put("Amount", splCustDealReq.getForeignCurrencyAmount()+" - "+splCustDealReq.getCurrencyNameFromDT());
						inputValues.put("Craeted By", sessionStateManage.getUserName());
						inputValues.put("Created Date", format.format(new Date()).toString());
						List<ApplicationSetup> lstApplicationSetup=iPersonalRemittanceService.getEmailFromAppSetup(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId());

						apllicationMailer1.sendMailToSPLCUSTREQ(lstApplicationSetup, inputValues);
					}
					
				}

				setBooRenderDataTable(false);
				setApproveAllPanel(false);
				setRenderSaveButton(false);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("complete.show();");
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::saveDataTableRecods");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void clickOnOKAdd() {
		setAllFieldsToNull();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("specialCustomer.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect");
		}
	}

	public void exit() throws IOException {
		clearFields();
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void getCustomerDetails(BigDecimal validDate) {

		if (getCustomerTypeId() == Integer.parseInt(Constants.CORPORATE_COMPONENT_ID)) {
			validDate(3);
		} else {
			validDate(1);
		}
	}

	public void addRecordsToDataTable() throws Exception {
		//if(getDocumentSerialIdNumber()!=null && getDocumentSerialIdNumber().intValue()>0){
		setBooRenderDataTable(true);
		setApproveAllPanel(true);
		setRenderSaveButton(true);
		SpecialCustomerDealRequestBeanDataTable splCustDealReqDataTable = new SpecialCustomerDealRequestBeanDataTable();

		splCustDealReqDataTable.setSpecialCustomerDealRequestyear(new BigDecimal(getFinaceYear()));
		splCustDealReqDataTable.setSpecialCustomerDealRequestNumber(getDocumentSerialIdNumber());
		splCustDealReqDataTable.setRequestDate(new SimpleDateFormat("dd/MM/yyyy").format(getValidUpTo()));
		splCustDealReqDataTable.setCountry(getBankCountry());
		if(getBankId()!=null){
			splCustDealReqDataTable.setBankName(getBankId().toPlainString());
		}
		if(getCurrencyId()!=null){
			splCustDealReqDataTable.setCurrencyName(getCurrencyId().toPlainString());
		}
		if(getFcAmount()!=null){
			splCustDealReqDataTable.setForeignCurrencyAmount(new BigDecimal(getFcAmount()));
		}
		splCustDealReqDataTable.setCustomerName(getName());


		//splCustDealReqDataTable.setValueDateOption(getValueDateOption());
		//splCustDealReqDataTable.setRenderValueDateOption(true);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		splCustDealReqDataTable.setValueDate( format.format( getValueDate()));
		splCustDealReqDataTable.setTentativeSaleRate(getTentativeSaleRate());


		splCustDealReqDataTable.setCompanyId(getCompanyId());
		splCustDealReqDataTable.setDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_SPL_CUSTOMER_DEAL_REQUEST));
		splCustDealReqDataTable.setId(getId());
		splCustDealReqDataTable.setName(getName());
		splCustDealReqDataTable.setValidUpTo(getValidUpTo());
		splCustDealReqDataTable.setSpecialCustomerDealRequestyearId(getFinancialYearId());
		splCustDealReqDataTable.setCustomerId(new BigDecimal(getCustomerId()));
		splCustDealReqDataTable.setBankNameFromDT(bankMap.get(getBankId()));
		splCustDealReqDataTable.setCurrencyNameFromDT(getCurrencyName());
		splCustDealReqDataTable.setCountryNameFromDT(bankCountryMap.get(getBankCountry()));
		if(getFcAmount()!=null){
			splCustDealReqDataTable.setMsgValue(new BigDecimal(getFcAmount()));
		}
		BigDecimal saveDocumentSerialID = new BigDecimal(getDocumentSerialID(Constants.U));
		splCustDealReqDataTable.setDocumentNo(saveDocumentSerialID);
		specialCustomerDealRequestBeanDataTable.add(splCustDealReqDataTable);
		setAllFieldsToNull();
		bankBasedOnCountry.clear();
		/*}else{
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("zeroDocNo.show();");
	}*/
	}

	public void setAllFieldsToNull() {
		setId(null);
		setName(null);
		setBankId(null);
		setBankCountry(null);
		setCurrencyId(null);
		setValidUpTo(null);
		setBankAccountNumber(null);
		setFcAmount(null);
		setCurrencyId(null);
		setBankCurrencyName(null);
		setBankAccountNumber(null);
		setAccountNumber(null);
		setBoobankAcc(true);
		setBoobankCurrency(true);
		setBooSelectbankAcc(false);
		setBooSelectbankCurrency(false);
		setValueDateOption(null);
		setTentativeSaleRate(null);
		setAvgRate(null);
		setValueDate(null);
		setEffetiveMinDate(new Date());
		currencyOfBank.clear();
	}

	public void editRow(SpecialCustomerDealRequestBeanDataTable splCustDealReq) {
		setMsgVal1(splCustDealReq.getMsgValue());
		splCustDealReq.setForeignCurrencyAmount(splCustDealReq.getForeignCurrencyAmount());
		setMsgVal2(splCustDealReq.getForeignCurrencyAmount());
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("onAdd.show();");

	}

	public void updateFCAmount(SpecialCustomerDealRequestBeanDataTable splCustDealReq) {
		setDisplayDynamicColor(true);
		splCustDealReq.setRenderInputForFC(false);
	}

	public void checkAll() {
		for (SpecialCustomerDealRequestBeanDataTable splCustDealReq : specialCustomerDealRequestBeanDataTable) {
			if (getAproveAll()) {
				splCustDealReq.setAprove(true);
			} else {
				splCustDealReq.setAprove(false);
			}
		}

	}

	public void idNotFound() {
		setId(null);
		setName(null);
		setValidUpTo(null);
	}

	public void updateSalesProjection() {
		setFinaceYearForUpdate(null);
		setBooRenderFirstPanel(false);
		setBooRenderSecondPanel(true);
		setNullValues();
		setManualDocumentSerialId(null);
		setBooAddButton(false);
		setBooUpdateButton(true);
		setBooCancelButton(true);
		setBooSystemGen(false);
		setBooUpdateSalesProj(true);
		bankAccordingToBankCountry.clear();
		currencyOfBank.clear();
	}

	public void updateFCAmount() {
		try{

			//BigDecimal financialYearId = null;

			if(getFinaceYearForUpdate() != null && getManualDocumentSerialId() != null){
				//List<UserFinancialYear> lstUserFinancial = miscellaneousReceiptPaymentService.getFinanacilYearId(new BigDecimal(getFinaceYearForUpdate()));
				/*if(lstUserFinancial != null && lstUserFinancial.size() != 0){
					UserFinancialYear userFinancialYearId = lstUserFinancial.get(0);
					financialYearId = userFinancialYearId.getFinancialYearID();
				}*/

				BigDecimal documentPk = specialCustomerDealRequestService.getDocumentPk(new BigDecimal(Constants.DOCUMENT_CODE_FOR_SPL_CUSTOMER_DEAL_REQUEST));

				if(documentPk != null && documentPk.compareTo(BigDecimal.ZERO) != 0){
					List<CustomerSpecialDealRequest> lstCustomerSpecialDealRequest = specialCustomerDealRequestService.getCustSpDocNo(new BigDecimal(getFinaceYearForUpdate()),new BigDecimal(getManualDocumentSerialId()),sessionStateManage.getCompanyId(),documentPk);
					CustomerSpecialDealRequest custSpdeal = lstCustomerSpecialDealRequest.get(0);
					specialCustomerDealRequestService.updateFCAmount(custSpdeal.getCustomerSpecialDealReqId(), getFcAmountForUpdate(), sessionStateManage.getUserName());
				}else{
					log.info("exception.getMessage():::::::::::::::::::::::::::::::");
					setErrorMessage("Document Id Not Available"); 
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}
			}else if(getFinaceYear() != 0 && getManualDocumentSerialId() != null){
				/*List<UserFinancialYear> lstUserFinancial = miscellaneousReceiptPaymentService.getFinanacilYearId(new BigDecimal(getFinaceYear()));
				if(lstUserFinancial != null && lstUserFinancial.size() != 0){
					UserFinancialYear userFinancialYearId = lstUserFinancial.get(0);
					financialYearId = userFinancialYearId.getFinancialYearID();
				}*/
				
				BigDecimal documentPk = specialCustomerDealRequestService.getDocumentPk(new BigDecimal(Constants.DOCUMENT_CODE_FOR_SPL_CUSTOMER_DEAL_REQUEST));
				
				if(documentPk != null && documentPk.compareTo(BigDecimal.ZERO) != 0){
					List<CustomerSpecialDealRequest> lstCustomerSpecialDealRequest = specialCustomerDealRequestService.getCustSpDocNo(new BigDecimal(getFinaceYear()),new BigDecimal(getManualDocumentSerialId()),sessionStateManage.getCompanyId(),documentPk);
					CustomerSpecialDealRequest custSpdeal = lstCustomerSpecialDealRequest.get(0);
					specialCustomerDealRequestService.updateFCAmount(custSpdeal.getCustomerSpecialDealReqId(), getFcAmountForUpdate(), sessionStateManage.getUserName());
				}else{
					log.info("exception.getMessage():::::::::::::::::::::::::::::::");
					setErrorMessage("Document Id Not Available"); 
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}
			}else{
				log.info("exception.getMessage():::::::::::::::::::::::::::::::");
				setErrorMessage("Not Update"); 
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void updateSpData() {
		updateFCAmount();
		setNullValues();
		setManualDocumentSerialId(null);
		setBooAddButton(false);
		setBooUpdateButton(true);

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("onUpdate.show();");

	}

	public void setNullValues() {
		setBankCountryForUpdate(null);
		setBankNameForUpdate(null);
		//setFinaceYearForUpdate(null);
		setRequestDateForUpdate(null);
		setDocumentDescriptionForUpdate(null);
		setCivilIdForUpdate(null);
		setValidUpToForUpdate(null);
		setCompanyNameForUpdate(null);
		setFcAmountForUpdate(null);
		setNameForUpdate(null);
		setAccountNumberForUpdate(null);
		setBankCurrencyNameForUpdate(null);
		setValueDateOptionForUpdate(null);
		setTentativeSaleRateForUpdate(null);
	}

	public void cancelUpdate() {
		setNullValues();
		setManualDocumentSerialId(null);
		setBooRenderFirstPanel(true);
		setBooRenderSecondPanel(false);
		setBooCancelButton(false);
		setBooAddButton(true);
		setBooUpdateButton(false);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("specialCustomer.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect");
		}
	}

	private Set<BankApplicability> bankBasedOnCountry = new HashSet<BankApplicability>();

	public Set<BankApplicability> getBankBasedOnCountry() {
		return bankBasedOnCountry;
	}

	public void setBankBasedOnCountry(Set<BankApplicability> bankBasedOnCountry) {
		this.bankBasedOnCountry = bankBasedOnCountry;
	}


	public void countryChange(){
		setCurrencyId(null);
		setBankCurrencyName(null);
		setBankCurrencyId(null);
		setBankCurrencyName(null);
	}


	public void  getBankAvgRate() throws Exception{
		setAvgRate(null);
		if(getBankId()!=null && getCurrencyId()!=null){
			List<BankAccountDetails> ptabledata = fundEstimationService.getAccountNumber(getBankId(), getCurrencyId());
			if(ptabledata.size()>0){
				BankAccountDetails bankActDet = ptabledata.get(0);
				String fundGlNo =bankActDet.getFundGlno();

				if(fundGlNo!=null){
					List<AccountBalance>    accBalList= generalService.getExchangeRateFromAccBal(getBankId(), getCurrencyId(),fundGlNo);
					if(accBalList.size()>0){
						AccountBalance banlance = accBalList.get(0);
						setAvgRate(banlance.getAverageRate());
					}
				}
			}
		}
	}


	public void validateTentativeSaleRate(){
		try{
			setFundMaxRate(null);
			setFundMinRate(null);
			if(getCurrencyId()!=null){
				List<CurrencyOtherInformation> currencyList = 	specialCustomerDealRequestService.getCurrencyDetails(getCurrencyId());
				CurrencyOtherInformation currency = currencyList.get(0);
				BigDecimal tentitiveRate =BigDecimal.ZERO;
				if(getTentativeSaleRate()!=null){
					tentitiveRate = new BigDecimal(getTentativeSaleRate());
				}
				if(tentitiveRate!=null && currency.getFundMaxRate()!=null && currency.getFundMinRate()!=null){
					setFundMaxRate(currency.getFundMaxRate());
					setFundMinRate(currency.getFundMinRate());
					if(currency.getFundMaxRate().doubleValue()>=tentitiveRate.doubleValue()){

					}else{

						RequestContext.getCurrentInstance().execute("tentiveRate.show();");
						setTentativeSaleRate(null);
						return;
					}
					if(currency.getFundMinRate().doubleValue()<=tentitiveRate.doubleValue()){

					}else{
						RequestContext.getCurrentInstance().execute("tentiveRate.show();");
						setTentativeSaleRate(null);
						return;
					}
				}
			}else{
				RequestContext.getCurrentInstance().execute("currencyMandatory.show();");
				setTentativeSaleRate(null);
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::validateTentativeSaleRate");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;    
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void bankBasedOnBankCountry() throws Exception {
		setValueDateOption(null);
		String bankName = 	bankCountryMap.get(getBankCountry());

		if(bankName.equalsIgnoreCase(Constants.KUWAIT)){
			setValueDateOption(Constants.CASH);
			setRenderValueDateOption(false);
		}else if(bankName.equalsIgnoreCase(Constants.OMAN)){
			setRenderValueDateOption(true);
			setValueDateOption( getValueDateOption());
		}else if(bankName.equalsIgnoreCase(Constants.BAHRAIN)) {
			setRenderValueDateOption(true);
			setValueDateOption( getValueDateOption());
		}else{
			setRenderValueDateOption(false);
			setValueDateOption(null);
		}



		countryChange();
		BigDecimal correBankInd = null;
		BigDecimal fundingBankInd = null;
		BigDecimal benificaryBankInd = null;

		List<BankIndicator> correIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_CORR_BANK);

		if (correIndlist.size() != 0) {
			correBankInd = correIndlist.get(0).getBankIndicatorId();
		}

		List<BankIndicator> fundingIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_FUND_BANK);

		if (fundingIndlist.size() != 0) {
			fundingBankInd = fundingIndlist.get(0).getBankIndicatorId();
		}

		List<BankIndicator> beniIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_BENI_BANK);

		if (beniIndlist.size() != 0) {
			benificaryBankInd = beniIndlist.get(0).getBankIndicatorId();
		}

		List<BankMaster> bankList = specialCustomerDealRequestService.getBankListBasedOnCountry(getBankCountry());

		List<BankApplicability> bankAppList = specialCustomerDealRequestService.populateBankAccordingToBankCountry(bankList);
		Set<BankApplicability> bankAp = new HashSet<BankApplicability>();
		for (BankApplicability bankApp : bankAppList) {
			if(bankApp.getBankInd().getBankIndicatorId()!=null){
				if ((bankApp.getBankInd().getBankIndicatorId()).equals(correBankInd) || (bankApp.getBankInd().getBankIndicatorId()).equals(fundingBankInd) || (bankApp.getBankInd().getBankIndicatorId()).equals(benificaryBankInd)) {
					bankAp.add(bankApp);
					bankMap.put(bankApp.getBankMaster().getBankId(), bankApp.getBankMaster().getBankFullName());
				}
			}
		}
		if(bankAp.size()>0){
			setBankBasedOnCountry(bankAp);
		}else{
			bankBasedOnCountry.clear();
			setBankId(null);
			RequestContext.getCurrentInstance().execute("noBankFound.show();");
			return;
		}

		getBankAvgRate();
	}

	public void populateSearchValue() {
		try{
			HttpSession session = sessionStateManage.getSession();
			@SuppressWarnings("unchecked")
			SpecialCustomerDealRequestBean<T> specialCustomerDealRequestBean = (SpecialCustomerDealRequestBean<T>) session.getAttribute("searchspecialCustomer");
			if (specialCustomerDealRequestBean != null) {
				setName(specialCustomerDealRequestBean.getName());
				setId(specialCustomerDealRequestBean.getId());
				setCustomerId(specialCustomerDealRequestBean.getCustomerId());
				setCustomerTypeId(specialCustomerDealRequestBean.getCustomerTypeId());
				setValidUpTo(specialCustomerDealRequestBean.getValidUpTo());
				log.info("Name " + getName());
				log.info("id " + getId());
				log.info("customer Id " + getCustomerId());
				log.info("Customer Type " + getCustomerTypeId());
				log.info("Valid up to " + getValidUpTo());
				log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@searchspecialCustomer's populateSearchValue@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				session.removeAttribute("searchspecialCustomer");

			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	private String valueDateOption;
	private String tentativeSaleRate;
	private String valueDateOptionForUpdate;
	private String tentativeSaleRateForUpdate;



	public String getValueDateOption() {
		return valueDateOption;
	}

	public String getTentativeSaleRate() {
		return tentativeSaleRate;
	}

	public String getValueDateOptionForUpdate() {
		return valueDateOptionForUpdate;
	}

	public String getTentativeSaleRateForUpdate() {
		return tentativeSaleRateForUpdate;
	}

	public void setValueDateOption(String valueDateOption) {
		this.valueDateOption = valueDateOption;
	}

	public void setTentativeSaleRate(String tentativeSaleRate) {
		this.tentativeSaleRate = tentativeSaleRate;
	}

	public void setValueDateOptionForUpdate(String valueDateOptionForUpdate) {
		this.valueDateOptionForUpdate = valueDateOptionForUpdate;
	}

	public void setTentativeSaleRateForUpdate(String tentativeSaleRateForUpdate) {
		this.tentativeSaleRateForUpdate = tentativeSaleRateForUpdate;
	}


	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	///////////////////////////////////////////////////itext///////////////////////////////


	public Date getValueDate() {
		return valueDate;
	}


	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}


	public Date getEffetiveMinDate() {
		return effetiveMinDate;
	}


	public void setEffetiveMinDate(Date effetiveMinDate) {
		this.effetiveMinDate = effetiveMinDate;
	}

	public void onDateSelectForValueDate(SelectEvent event) {

		//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date valDate=(Date) event.getObject();
		System.out.println("=========================="+valDate);
		setValueDate(valDate );
		setEffetiveMinDate(valDate);


	}





}
