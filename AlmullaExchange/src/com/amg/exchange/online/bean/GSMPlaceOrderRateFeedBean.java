package com.amg.exchange.online.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.TokenGeneration;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.mail.ApllicationMailer1;
import com.amg.exchange.mail.ApplicationMailer;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.service.ICustomerApprovalPlaceOrderRateFeedService;
import com.amg.exchange.online.service.IGSMPlaceOrderRateFeedService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.remittance.service.ISpecialRateRequestService;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.service.DeliveryModeService;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IRemittanceModeService;
import com.amg.exchange.treasury.service.ISupplierService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("gSMPlaceOrderRateFeedBean")
@Scope("session")
public class GSMPlaceOrderRateFeedBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(GSMPlaceOrderRateFeedBean.class);
	//Page level Variables
	private BigDecimal countryBranchid;
	private String countryBranchName;
	private BigDecimal customerRef;
	private BigDecimal rateOfferedIdPk;
	private Date dateOfRequest;
	private String branch;
	private String currency;
	private BigDecimal amount;
	private BigDecimal rateOffered;
	private String transctionConcluded;
	private String emailId;
	private BigDecimal currencyId;
	private BigDecimal rateOfferMinRate;
	private BigDecimal rateOfferMaxRate;
	private BigDecimal countryId;
	private BigDecimal remittanceId;
	private BigDecimal beneficiaryId;
	private BigDecimal bankId;
	private String countryName;
	private String beneficiaryName;
	private String bankName;
	private String equivalentCurrency;
	private BigDecimal accountSeqId;
	private String customerUniqueNumber;
	private String customerRefandName;
	private BigDecimal beneficiaryBranchId;
	private String serviceGroupCode;
	private String custIndicator;
	private BigDecimal documentNumber;
	private BigDecimal documentFinanceYear;
	//common Variables
	private String errorMessage;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private Boolean booGroupSalesManager;
	private Boolean isFromGroupSalesManagerCustomer=false;
	private Boolean booRenderDataTable=false;
	private Boolean booRenderSaveOrExit=false;
	private Boolean booRenderClear=false;
	private String procedureError;
	private Boolean booRenderRemitPanel=false;
	
	
	//procedure variables
	//service Variables
	private Boolean booMultipleService=false;
	private BigDecimal dataserviceid;
	private Boolean booSingleService=false;
	private String databenificaryservice;
	//Routing County Variables
	private Boolean booMultipleRoutingCountry=false;
	private BigDecimal routingCountry;
	private Boolean booSingleRoutingCountry=false;
	private String routingCountryName;
	//Routing Bank Variables
	private Boolean booMultipleRoutingBank=false;
	private BigDecimal routingBank;
	private Boolean booSingleRoutingBank=false;
	private String routingBankName;
	//Routing Remittance Variables
	private Boolean booMultipleRemit=false;
	private BigDecimal remitMode;
	private Boolean booSingleRemit=false;
	private String remittanceName;
	//Routing Delivery Variables
	private Boolean booRenderDeliveryModeDDPanel=false;
	private BigDecimal deliveryMode;
	private Boolean booRenderDeliveryModeInputPanel=false;
	private String deliveryModeInput;
	//Routing RoutingBranch Variables
	private Boolean booMultipleRoutingBranch=false;
	private BigDecimal routingBranch;
	private Boolean booSingleRoutingBranch=false;
	private String routingBranchName;
	
	SessionStateManage session= new SessionStateManage();
	//Search Customer Variables
	private BigDecimal customerId;
	private String customerName;
	
	
	
	
	//DataTable List
	private List<GSMPlaceOrderRateFeedDataTable> lstGroupSalesMgrSpecialRateRequest=new ArrayList<GSMPlaceOrderRateFeedDataTable>();
	private List<GSMPlaceOrderRateFeedDataTable> groupSalesMgrSpecialRateReqForPlaceOrder=new ArrayList<GSMPlaceOrderRateFeedDataTable>();
	private List<CountryBranch> countryBranch = new ArrayList<CountryBranch>();
	//procedures list variables
	private List<PopulateData> routingbankvalues = new ArrayList<PopulateData>();
	private List<PopulateData> serviceList = new ArrayList<PopulateData>();
	private List<PopulateData> routingCountrylst = new ArrayList<PopulateData>();
	private List<PopulateData> lstofRemittance = new ArrayList<PopulateData>();
	private List<PopulateData> lstofDelivery = new ArrayList<PopulateData>();
	private List<PopulateData> routingBankBranchlst = new ArrayList<PopulateData>();
	
	
	
	private TokenGeneration tokenGeneration = new TokenGeneration();
	//service Declaritions
	@Autowired
	IGSMPlaceOrderRateFeedService gSMPlaceOrderRateFeedService;
	
	@Autowired
	ICustomerApprovalPlaceOrderRateFeedService customerApprovalPlaceOrderRateFeedService;
	
	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	ApllicationMailer1 apllicationMailer1;
	
	@Autowired
	ApplicationMailer applicationMailer;
	
	@Autowired
	ISupplierService<T> iSupplierService;
	
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	
	@Autowired
	IStopPaymentService<T> stopPaymentService;
	
	@Autowired
	ISpecialRateRequestService specialRateService;
	
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	
	@Autowired
	ServiceCodeMasterService serviceMasterService;
	
	@Autowired 
	IServiceGroupMasterService iServiceGroupMasterService;
	
	@Autowired
	IBankBranchDetailsService<T> bankBranchDetailsService;
	
	@Autowired
	IRemittanceModeService iRemitModeMaster;
	
	@Autowired
	DeliveryModeService iDeliveryModeMaster;
	
	
	
	public BigDecimal getRateOfferedIdPk() {
		return rateOfferedIdPk;
	}
	public void setRateOfferedIdPk(BigDecimal rateOfferedIdPk) {
		this.rateOfferedIdPk = rateOfferedIdPk;
	}
	public Date getDateOfRequest() {
		return dateOfRequest;
	}
	public void setDateOfRequest(Date dateOfRequest) {
		this.dateOfRequest = dateOfRequest;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getRateOffered() {
		return rateOffered;
	}
	public void setRateOffered(BigDecimal rateOffered) {
		this.rateOffered = rateOffered;
	}
	public String getTransctionConcluded() {
		return transctionConcluded;
	}
	public void setTransctionConcluded(String transctionConcluded) {
		this.transctionConcluded = transctionConcluded;
	}
	public String getCustomerUniqueNumber() {
		return customerUniqueNumber;
	}
	public void setCustomerUniqueNumber(String customerUniqueNumber) {
		this.customerUniqueNumber = customerUniqueNumber;
	}
	public TokenGeneration getTokenGeneration() {
		return tokenGeneration;
	}
	public void setTokenGeneration(TokenGeneration tokenGeneration) {
		this.tokenGeneration = tokenGeneration;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public List<GSMPlaceOrderRateFeedDataTable> getLstGroupSalesMgrSpecialRateRequest() {
		return lstGroupSalesMgrSpecialRateRequest;
	}
	public void setLstGroupSalesMgrSpecialRateRequest(
			List<GSMPlaceOrderRateFeedDataTable> lstGroupSalesMgrSpecialRateRequest) {
		this.lstGroupSalesMgrSpecialRateRequest = lstGroupSalesMgrSpecialRateRequest;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public BigDecimal getCountryBranchid() {
		return countryBranchid;
	}
	public void setCountryBranchid(BigDecimal countryBranchid) {
		this.countryBranchid = countryBranchid;
	}
	public String getCountryBranchName() {
		return countryBranchName;
	}
	public void setCountryBranchName(String countryBranchName) {
		this.countryBranchName = countryBranchName;
	}
	public List<CountryBranch> getCountryBranch() {
		return countryBranch;
	}
	public void setCountryBranch(List<CountryBranch> countryBranch) {
		this.countryBranch = countryBranch;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public Boolean getBooGroupSalesManager() {
		return booGroupSalesManager;
	}
	public void setBooGroupSalesManager(Boolean booGroupSalesManager) {
		this.booGroupSalesManager = booGroupSalesManager;
	}
	public Boolean getIsFromGroupSalesManagerCustomer() {
		return isFromGroupSalesManagerCustomer;
	}
	public void setIsFromGroupSalesManagerCustomer(
			Boolean isFromGroupSalesManagerCustomer) {
		this.isFromGroupSalesManagerCustomer = isFromGroupSalesManagerCustomer;
	}
	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}
	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}
	public Boolean getBooRenderSaveOrExit() {
		return booRenderSaveOrExit;
	}
	public void setBooRenderSaveOrExit(Boolean booRenderSaveOrExit) {
		this.booRenderSaveOrExit = booRenderSaveOrExit;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public BigDecimal getRateOfferMinRate() {
		return rateOfferMinRate;
	}
	public void setRateOfferMinRate(BigDecimal rateOfferMinRate) {
		this.rateOfferMinRate = rateOfferMinRate;
	}
	public BigDecimal getRateOfferMaxRate() {
		return rateOfferMaxRate;
	}
	public void setRateOfferMaxRate(BigDecimal rateOfferMaxRate) {
		this.rateOfferMaxRate = rateOfferMaxRate;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public BigDecimal getRemittanceId() {
		return remittanceId;
	}
	public void setRemittanceId(BigDecimal remittanceId) {
		this.remittanceId = remittanceId;
	}
	public BigDecimal getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(BigDecimal beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getRemittanceName() {
		return remittanceName;
	}
	public void setRemittanceName(String remittanceName) {
		this.remittanceName = remittanceName;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getEquivalentCurrency() {
		return equivalentCurrency;
	}
	public void setEquivalentCurrency(String equivalentCurrency) {
		this.equivalentCurrency = equivalentCurrency;
	}
	public BigDecimal getAccountSeqId() {
		return accountSeqId;
	}
	public void setAccountSeqId(BigDecimal accountSeqId) {
		this.accountSeqId = accountSeqId;
	}
	public BigDecimal getBeneficiaryBranchId() {
		return beneficiaryBranchId;
	}
	public void setBeneficiaryBranchId(BigDecimal beneficiaryBranchId) {
		this.beneficiaryBranchId = beneficiaryBranchId;
	}
	
	public String getRoutingBankName() {
		return routingBankName;
	}
	public void setRoutingBankName(String routingBankName) {
		this.routingBankName = routingBankName;
	}
	//Search Customer Variables Getters & Setters
	public BigDecimal getCustomerRef() {
		return customerRef;
	}
	public void setCustomerRef(BigDecimal customerRef) {
		this.customerRef = customerRef;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public Boolean getBooRenderClear() {
		return booRenderClear;
	}
	public void setBooRenderClear(Boolean booRenderClear) {
		this.booRenderClear = booRenderClear;
	}
	public List<GSMPlaceOrderRateFeedDataTable> getGroupSalesMgrSpecialRateReqForPlaceOrder() {
		return groupSalesMgrSpecialRateReqForPlaceOrder;
	}
	public void setGroupSalesMgrSpecialRateReqForPlaceOrder(
			List<GSMPlaceOrderRateFeedDataTable> groupSalesMgrSpecialRateReqForPlaceOrder) {
		this.groupSalesMgrSpecialRateReqForPlaceOrder = groupSalesMgrSpecialRateReqForPlaceOrder;
	}
	public String getCustomerRefandName() {
		return customerRefandName;
	}
	public void setCustomerRefandName(String customerRefandName) {
		this.customerRefandName = customerRefandName;
	}
		
	public String getRoutingCountryName() {
		return routingCountryName;
	}
	public void setRoutingCountryName(String routingCountryName) {
		this.routingCountryName = routingCountryName;
	}
	public List<PopulateData> getRoutingbankvalues() {
		return routingbankvalues;
	}
	public void setRoutingbankvalues(List<PopulateData> routingbankvalues) {
		this.routingbankvalues = routingbankvalues;
	}
	public String getServiceGroupCode() {
		return serviceGroupCode;
	}
	public void setServiceGroupCode(String serviceGroupCode) {
		this.serviceGroupCode = serviceGroupCode;
	}
	public String getProcedureError() {
		return procedureError;
	}
	public void setProcedureError(String procedureError) {
		this.procedureError = procedureError;
	}
	//procedure getters & setters
	public Boolean getBooMultipleService() {
		return booMultipleService;
	}
	public void setBooMultipleService(Boolean booMultipleService) {
		this.booMultipleService = booMultipleService;
	}
	public BigDecimal getDataserviceid() {
		return dataserviceid;
	}
	public void setDataserviceid(BigDecimal dataserviceid) {
		this.dataserviceid = dataserviceid;
	}
	public Boolean getBooSingleService() {
		return booSingleService;
	}
	public void setBooSingleService(Boolean booSingleService) {
		this.booSingleService = booSingleService;
	}
	public String getDatabenificaryservice() {
		return databenificaryservice;
	}
	public void setDatabenificaryservice(String databenificaryservice) {
		this.databenificaryservice = databenificaryservice;
	}
	public Boolean getBooMultipleRoutingCountry() {
		return booMultipleRoutingCountry;
	}
	public void setBooMultipleRoutingCountry(Boolean booMultipleRoutingCountry) {
		this.booMultipleRoutingCountry = booMultipleRoutingCountry;
	}
	public BigDecimal getRoutingCountry() {
		return routingCountry;
	}
	public void setRoutingCountry(BigDecimal routingCountry) {
		this.routingCountry = routingCountry;
	}
	public Boolean getBooSingleRoutingCountry() {
		return booSingleRoutingCountry;
	}
	public void setBooSingleRoutingCountry(Boolean booSingleRoutingCountry) {
		this.booSingleRoutingCountry = booSingleRoutingCountry;
	}
	public Boolean getBooMultipleRoutingBank() {
		return booMultipleRoutingBank;
	}
	public void setBooMultipleRoutingBank(Boolean booMultipleRoutingBank) {
		this.booMultipleRoutingBank = booMultipleRoutingBank;
	}
	public BigDecimal getRoutingBank() {
		return routingBank;
	}
	public void setRoutingBank(BigDecimal routingBank) {
		this.routingBank = routingBank;
	}
	public Boolean getBooSingleRoutingBank() {
		return booSingleRoutingBank;
	}
	public void setBooSingleRoutingBank(Boolean booSingleRoutingBank) {
		this.booSingleRoutingBank = booSingleRoutingBank;
	}
	public Boolean getBooMultipleRemit() {
		return booMultipleRemit;
	}
	public void setBooMultipleRemit(Boolean booMultipleRemit) {
		this.booMultipleRemit = booMultipleRemit;
	}
	public BigDecimal getRemitMode() {
		return remitMode;
	}
	public void setRemitMode(BigDecimal remitMode) {
		this.remitMode = remitMode;
	}
	public Boolean getBooSingleRemit() {
		return booSingleRemit;
	}
	public void setBooSingleRemit(Boolean booSingleRemit) {
		this.booSingleRemit = booSingleRemit;
	}
	public Boolean getBooRenderDeliveryModeDDPanel() {
		return booRenderDeliveryModeDDPanel;
	}
	public void setBooRenderDeliveryModeDDPanel(Boolean booRenderDeliveryModeDDPanel) {
		this.booRenderDeliveryModeDDPanel = booRenderDeliveryModeDDPanel;
	}
	public BigDecimal getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(BigDecimal deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public Boolean getBooRenderDeliveryModeInputPanel() {
		return booRenderDeliveryModeInputPanel;
	}
	public void setBooRenderDeliveryModeInputPanel(
			Boolean booRenderDeliveryModeInputPanel) {
		this.booRenderDeliveryModeInputPanel = booRenderDeliveryModeInputPanel;
	}
	public String getDeliveryModeInput() {
		return deliveryModeInput;
	}
	public void setDeliveryModeInput(String deliveryModeInput) {
		this.deliveryModeInput = deliveryModeInput;
	}
	public Boolean getBooMultipleRoutingBranch() {
		return booMultipleRoutingBranch;
	}
	public void setBooMultipleRoutingBranch(Boolean booMultipleRoutingBranch) {
		this.booMultipleRoutingBranch = booMultipleRoutingBranch;
	}
	public BigDecimal getRoutingBranch() {
		return routingBranch;
	}
	public void setRoutingBranch(BigDecimal routingBranch) {
		this.routingBranch = routingBranch;
	}
	public Boolean getBooSingleRoutingBranch() {
		return booSingleRoutingBranch;
	}
	public void setBooSingleRoutingBranch(Boolean booSingleRoutingBranch) {
		this.booSingleRoutingBranch = booSingleRoutingBranch;
	}
	public String getRoutingBranchName() {
		return routingBranchName;
	}
	public void setRoutingBranchName(String routingBranchName) {
		this.routingBranchName = routingBranchName;
	}
	public List<PopulateData> getServiceList() {
		return serviceList;
	}
	public void setServiceList(List<PopulateData> serviceList) {
		this.serviceList = serviceList;
	}
	public List<PopulateData> getRoutingCountrylst() {
		return routingCountrylst;
	}
	public void setRoutingCountrylst(List<PopulateData> routingCountrylst) {
		this.routingCountrylst = routingCountrylst;
	}
	public List<PopulateData> getLstofRemittance() {
		return lstofRemittance;
	}
	public void setLstofRemittance(List<PopulateData> lstofRemittance) {
		this.lstofRemittance = lstofRemittance;
	}
	public List<PopulateData> getLstofDelivery() {
		return lstofDelivery;
	}
	public void setLstofDelivery(List<PopulateData> lstofDelivery) {
		this.lstofDelivery = lstofDelivery;
	}
	public List<PopulateData> getRoutingBankBranchlst() {
		return routingBankBranchlst;
	}
	public void setRoutingBankBranchlst(List<PopulateData> routingBankBranchlst) {
		this.routingBankBranchlst = routingBankBranchlst;
	}
	public String getCustIndicator() {
		return custIndicator;
	}
	public void setCustIndicator(String custIndicator) {
		this.custIndicator = custIndicator;
	}
	
	public Boolean getBooRenderRemitPanel() {
		return booRenderRemitPanel;
	}
	public void setBooRenderRemitPanel(Boolean booRenderRemitPanel) {
		this.booRenderRemitPanel = booRenderRemitPanel;
	}
	
	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}
	//Page Navigation
	public void groupSalesManagerApprovalPageNavigation(){
		countryBranch.clear();
		lstGroupSalesMgrSpecialRateRequest.clear();
		groupSalesMgrSpecialRateReqForPlaceOrder.clear();
		clearAllFields();
		clearForRenderFields();
		clearAllProcedureVariables();
		renderProcedureVariables();
		setBooRenderDataTable(false);
		setBooRenderSaveOrExit(false);
		setBooRenderClear(false);
		setBooRenderRemitPanel(false);
		toFetchAllBranches();
		toFetchRecordsFormPlaceOrder();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/gSMPlaceOrderRateFeed.xhtml");
		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("groupSalesManagerApprovalPageNavigation  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
		}
	}
	
	
	//Fetch All Branches
	public void toFetchAllBranches(){
		countryBranch.clear();
		List<CountryBranch> lstCountryBranchs=generalService.getBranchDetails(session.getCountryId());
		if(lstCountryBranchs.size()>0){
			countryBranch.addAll(lstCountryBranchs);
		}
		
	}
	//clearAllFields
	public void clearAllFields(){
		setCountryBranchid(null);
		setCountryBranchName(null);
		setCustomerRef(null);
		setCustomerId(null);
	}
	
	public void clearForRenderFields(){
		setRateOfferedIdPk(null);
		setBranch(null);
		setCurrency(null);
		setCurrencyId(null);
		setRateOffered(null);
		setAmount(null);
		setRateOfferMaxRate(null);
		setRateOfferMinRate(null);
		setCountryId(null);
		setCountryName(null);
		setRemittanceId(null);
		setBeneficiaryId(null);
		setBeneficiaryName(null);
		setBankId(null);
		setBankName(null);
		setEquivalentCurrency(null);
		setAccountSeqId(null);
		setCreatedDate(null);
		setCustomerUniqueNumber(null);
		setCustomerId(null);
		setCustomerName(null);
		setCustomerRefandName(null);
		setServiceGroupCode(null);
		setCustIndicator(null);	
		setDocumentNumber(null);
		setDocumentFinanceYear(null);
	}
	
	//clear procedure variables
	public void clearAllProcedureVariables(){
		setDataserviceid(null);
		setDatabenificaryservice(null);
		setRoutingCountry(null);
		setRoutingCountryName(null);
		setRoutingBank(null);
		setRoutingBankName(null);
		setRemitMode(null);
		setRemittanceName(null);
		setDeliveryMode(null);
		setDeliveryModeInput(null);
		setRoutingBranch(null);
		setRoutingBranchName(null);
	}
	//renders Procedure Variables
	public void renderProcedureVariables(){
		setBooMultipleService(false);
		setBooSingleService(false);
		setBooMultipleRoutingCountry(false);
		setBooSingleRoutingCountry(false);
		setBooMultipleRoutingBank(false);
		setBooSingleRoutingBank(false);
		setBooMultipleRemit(false);
		setBooSingleRemit(false);
		setBooRenderDeliveryModeDDPanel(false);
		setBooRenderDeliveryModeInputPanel(false);
		setBooMultipleRoutingBranch(false);
		setBooSingleRoutingBranch(false);
	}
	
	//Fetch Records From Place Order
	public void toFetchRecordsFormPlaceOrder(){
		lstGroupSalesMgrSpecialRateRequest.clear();
		List<RatePlaceOrder> lstRatePlaceOrderUnApproved=gSMPlaceOrderRateFeedService.fetchAllRecrdsforUnApproved(getCountryBranchid());
		if(lstRatePlaceOrderUnApproved.size()>0){
			for (RatePlaceOrder ratePlaceOrder : lstRatePlaceOrderUnApproved) {
				GSMPlaceOrderRateFeedDataTable gsmPlaceOrderRateFeedDT= new GSMPlaceOrderRateFeedDataTable();
				String custerName=null;
				String brachName=null;
				String beneName=null;
				String benefiBankName=null;
				String remittanceType=null;
				String curQtyName=null;
				String ctryName=null;
				/*Dispaly Data table Variables started*/
				//CustomerId and Name
				gsmPlaceOrderRateFeedDT.setCustomerId(ratePlaceOrder.getCustomerId());
				//custerName=generalService.getCustomerNameBasedOnCustomerId(ratePlaceOrder.getCustomerId());
				custerName=generalService.getCustomerNameCustomerId(ratePlaceOrder.getCustomerId());
				gsmPlaceOrderRateFeedDT.setCustomerName(custerName);
				//CountryId and Name
				gsmPlaceOrderRateFeedDT.setCountryId(ratePlaceOrder.getBeneficiaryCountryId());
				ctryName=generalService.getCountryName(session.getLanguageId(), ratePlaceOrder.getBeneficiaryCountryId());
				gsmPlaceOrderRateFeedDT.setCountryName(ctryName);
				//BranchId and Name
				gsmPlaceOrderRateFeedDT.setBranchId(ratePlaceOrder.getCountryBranchId());
				brachName=gSMPlaceOrderRateFeedService.toFetchBranchName(ratePlaceOrder.getCountryBranchId());
				gsmPlaceOrderRateFeedDT.setBranch(brachName);
				//beneficiary Name
				gsmPlaceOrderRateFeedDT.setBeneficiaryId(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
				beneName=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
				gsmPlaceOrderRateFeedDT.setBeneficiaryName(beneName);
				//bank Name
				gsmPlaceOrderRateFeedDT.setBankId(ratePlaceOrder.getBeneficiaryBankId());
				benefiBankName=generalService.getBankName(ratePlaceOrder.getBeneficiaryBankId());
				gsmPlaceOrderRateFeedDT.setBankName(benefiBankName);
				//Remit Type
				gsmPlaceOrderRateFeedDT.setRemittanceId(ratePlaceOrder.getRemitType());
				remittanceType=gSMPlaceOrderRateFeedService.toFetchServiceGroupDesc(session.getLanguageId(),ratePlaceOrder.getRemitType());
				gsmPlaceOrderRateFeedDT.setRemittanceName(remittanceType);
				//currency
				gsmPlaceOrderRateFeedDT.setCurrencyId(ratePlaceOrder.getDestinationCurrenyId());
				gsmPlaceOrderRateFeedDT.setCurrency(generalService.getCurrencyName(ratePlaceOrder.getDestinationCurrenyId()));
				/*Dispaly Data table Variables Endec*/
				//all  variables
				gsmPlaceOrderRateFeedDT.setRateOfferedIdPk(ratePlaceOrder.getRatePlaceOrderId());
				gsmPlaceOrderRateFeedDT.setCreatedBy(ratePlaceOrder.getCreatedBy());
				gsmPlaceOrderRateFeedDT.setCreatedDate(ratePlaceOrder.getCreatedDate());
				gsmPlaceOrderRateFeedDT.setIsActive(ratePlaceOrder.getIsActive());
				gsmPlaceOrderRateFeedDT.setModifiedBy(ratePlaceOrder.getModifiedBy());
				gsmPlaceOrderRateFeedDT.setModifiedDate(ratePlaceOrder.getModifiedDate());
				gsmPlaceOrderRateFeedDT.setApprovedBy(ratePlaceOrder.getApprovedBy());
				gsmPlaceOrderRateFeedDT.setApprovedDate(ratePlaceOrder.getApprovedDate());
				gsmPlaceOrderRateFeedDT.setTransctionConcluded(ratePlaceOrder.getTransactionConcluded());
				gsmPlaceOrderRateFeedDT.setRateOffered(ratePlaceOrder.getRateOffered());
				curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(ratePlaceOrder.getDestinationCurrenyId());
				gsmPlaceOrderRateFeedDT.setEquivalentCurrencyDt("["+curQtyName+"]");
				gsmPlaceOrderRateFeedDT.setAmount(ratePlaceOrder.getTransactionAmount());
				gsmPlaceOrderRateFeedDT.setAmountAndQtyName(ratePlaceOrder.getTransactionAmount()+ "-" +curQtyName);
				gsmPlaceOrderRateFeedDT.setDocumentNumber(ratePlaceOrder.getDocumentNumber());
				gsmPlaceOrderRateFeedDT.setDocumentFinanceYear(ratePlaceOrder.getDocumentFinanceYear());
				lstGroupSalesMgrSpecialRateRequest.add(gsmPlaceOrderRateFeedDT);
				clearAllFields();
				clearForRenderFields();
				clearAllProcedureVariables();
				clearAllProcedureVariables();
				setBooRenderDataTable(false);
				setBooRenderSaveOrExit(false);
				setBooRenderClear(false);
				setBooRenderRemitPanel(false);
			}
		}
	}
	//Fetch records from Db
	public void toFetchAllRecords(GSMPlaceOrderRateFeedDataTable gsmDataTable){
		groupSalesMgrSpecialRateReqForPlaceOrder.clear();
		clearAllFields();
		clearForRenderFields();
		clearAllProcedureVariables();
		clearAllProcedureVariables();
		setBooRenderDataTable(false);
		setBooRenderSaveOrExit(false);
		setBooRenderClear(false);
		setBooRenderRemitPanel(false);
		setAccountSeqId(null);
		//idOnBlur();
		try{
			List<RatePlaceOrder> lstRatePlaceOrders=gSMPlaceOrderRateFeedService.fetchAllRecrds(gsmDataTable.getCustomerId(),getCountryBranchid());
			//List<RatePlaceOrder> lstRatePlaceOrderUnApproved=gSMPlaceOrderRateFeedService.fetchAllRecrdsUnapprovedRecrdsBasedonCusterId(gsmDataTable.getCustomerId(),getCountryBranchid());
			List<RatePlaceOrder> lstRatePlaceOrderUnApproved=customerApprovalPlaceOrderRateFeedService.toFetchRecordsUnApprovesgsm(gsmDataTable.getCustomerId(),gsmDataTable.getDocumentNumber(),gsmDataTable.getDocumentFinanceYear());
			BigDecimal custrRef=null;
			String custmerName=null;
			custrRef=gSMPlaceOrderRateFeedService.toFetchCustomerRef(gsmDataTable.getCustomerId());
			//custmerName=generalService.getCustomerNameBasedOnCustomerId(gsmDataTable.getCustomerId());
			custmerName=generalService.getCustomerNameCustomerId(gsmDataTable.getCustomerId());
			if(lstRatePlaceOrders.size()>0 && lstRatePlaceOrderUnApproved.size() >0){
				for (RatePlaceOrder ratePlaceOrder : lstRatePlaceOrders) {
					String countryName=null;
					String remitType=null;
					String BeneName=null;
					String curQtyName=null;
					String beneBankName=null;
					String couBranchName=null;
					if(custrRef != null){
					setCustomerRefandName(custrRef.toString()+ "-" +custmerName);
					}
					GSMPlaceOrderRateFeedDataTable dataTable=new GSMPlaceOrderRateFeedDataTable();
						dataTable.setRateOfferedIdPk(ratePlaceOrder.getRatePlaceOrderId());
						dataTable.setDateOfRequest(ratePlaceOrder.getCreatedDate());
						if(ratePlaceOrder.getCountryBranchId() != null){
						/*List<CountryBranch> list=generalService.getBranchDetailsForToLocation(session.getCountryId(), ratePlaceOrder.getCountryBranchId());
						if(list.size() != 0){
						dataTable.setBranch(list.get(0).getBranchName());
						}*/
							couBranchName=stopPaymentService.toFetchCountryBranchName(session.getCountryId(),ratePlaceOrder.getCountryBranchId());
							dataTable.setBranch(couBranchName);
						}
						if(ratePlaceOrder.getDestinationCurrenyId() != null){
						dataTable.setCurrency(generalService.getCurrencyName(ratePlaceOrder.getDestinationCurrenyId()));
						}
						if(ratePlaceOrder.getBeneficiaryCountryId() != null){
							countryName=generalService.getCountryName(session.getLanguageId(), ratePlaceOrder.getBeneficiaryCountryId());
							dataTable.setCountryName(countryName);
						}
						if(ratePlaceOrder.getRemitType() != null){
							remitType=gSMPlaceOrderRateFeedService.toFetchServiceGroupDesc(session.getLanguageId(),ratePlaceOrder.getRemitType());
							dataTable.setRemittanceName(remitType);
						}
						if(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId() != null){
							BeneName=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
							dataTable.setBeneficiaryName(BeneName);
							curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(ratePlaceOrder.getDestinationCurrenyId());
							dataTable.setEquivalentCurrencyDt("["+curQtyName+"]");
						}
						if(ratePlaceOrder.getBeneficiaryBankId() != null){
							beneBankName=generalService.getBankName(ratePlaceOrder.getBeneficiaryBankId());
							dataTable.setBankName(beneBankName);
						}						
						dataTable.setAmount(ratePlaceOrder.getTransactionAmount());
						dataTable.setRateOffered(ratePlaceOrder.getRateOffered());
						//dataTable.setTransctionConcluded(getTransctionConcluded());
						dataTable.setCreatedBy(ratePlaceOrder.getCreatedBy());
						dataTable.setCreatedDate(ratePlaceOrder.getCreatedDate());
						dataTable.setEmailId(ratePlaceOrder.getCustomerEmail());
						dataTable.setCountryId(ratePlaceOrder.getBeneficiaryCountryId());
						dataTable.setRemittanceId(ratePlaceOrder.getRemitType());
						dataTable.setBeneficiaryId(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
						dataTable.setAccountSeqId(ratePlaceOrder.getAccountSeqquenceId().getBeneficaryAccountSeqId());
						dataTable.setBankId(ratePlaceOrder.getBeneficiaryBankId());
						groupSalesMgrSpecialRateReqForPlaceOrder.add(dataTable);
					}
				setBooRenderDataTable(true);
				setBooRenderSaveOrExit(true);
				setBooRenderClear(true);
				String couBranchName=null;
				String currName=null;
				String beneficiaryCountryName=null;
				String remittanceType=null;
				String Beneficiary=null;
				String benefiBankName=null;
				String currencyQtyName=null;
				BigDecimal BeneBranchId=null;
				//text field population
				if(lstRatePlaceOrderUnApproved.get(0).getCountryBranchId() != null){
				couBranchName=stopPaymentService.toFetchCountryBranchName(session.getCountryId(),lstRatePlaceOrderUnApproved.get(0).getCountryBranchId());
				setBranch(couBranchName);
				}
				if(lstRatePlaceOrderUnApproved.get(0).getDestinationCurrenyId() != null){
				setCurrencyId(lstRatePlaceOrderUnApproved.get(0).getDestinationCurrenyId());
				currName=generalService.getCurrencyName(lstRatePlaceOrderUnApproved.get(0).getDestinationCurrenyId());
				setCurrency(currName);
				}
				if(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryCountryId() != null){
					setCountryId(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryCountryId());
					beneficiaryCountryName=generalService.getCountryName(session.getLanguageId(), lstRatePlaceOrderUnApproved.get(0).getBeneficiaryCountryId());
					setCountryName(beneficiaryCountryName);
				}
				if(lstRatePlaceOrderUnApproved.get(0).getRemitType() != null){
					setRemittanceId(lstRatePlaceOrderUnApproved.get(0).getRemitType());
					remittanceType=gSMPlaceOrderRateFeedService.toFetchServiceGroupDesc(session.getLanguageId(),lstRatePlaceOrderUnApproved.get(0).getRemitType());
					setRemittanceName(remittanceType);
				}
				if(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryBankId() != null){
					setBankId(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryBankId());
					benefiBankName=generalService.getBankName(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryBankId());
					setBankName(benefiBankName);
				}
				if(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryMasterId().getBeneficaryMasterSeqId() != null){
					Beneficiary=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryMasterId().getBeneficaryMasterSeqId());
					setBeneficiaryName(Beneficiary);
					currencyQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(lstRatePlaceOrderUnApproved.get(0).getDestinationCurrenyId());
					setEquivalentCurrency("["+currencyQtyName+"]");
				}
				setAmount(lstRatePlaceOrderUnApproved.get(0).getTransactionAmount());
				setRateOfferedIdPk(lstRatePlaceOrderUnApproved.get(0).getRatePlaceOrderId());
				setEmailId(lstRatePlaceOrderUnApproved.get(0).getCustomerEmail());
				setCreatedDate(lstRatePlaceOrderUnApproved.get(0).getCreatedDate());
				setDocumentNumber(lstRatePlaceOrderUnApproved.get(0).getDocumentNumber());
				
				BeneBranchId=gSMPlaceOrderRateFeedService.getBeneBranchId(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryBankId(),lstRatePlaceOrderUnApproved.get(0).getBeneficiaryMasterId().getBeneficaryMasterSeqId(),getBeneficiaryName());
				setBeneficiaryBranchId(BeneBranchId);
				//toset routing Bank
				//procedure calling started 
				
				HashMap<String, String> inputRoutingBankSetUpdetails = new HashMap<String, String>();

				inputRoutingBankSetUpdetails.put("P_APPLICATION_COUNTRY_ID", session.getCountryId().toPlainString());
				inputRoutingBankSetUpdetails.put("P_BENE_COUNTRY_ID", getCountryId().toPlainString()); // beneficiary bank Country Id
				inputRoutingBankSetUpdetails.put("P_BENE_BANK_ID", getBankId().toPlainString());
				inputRoutingBankSetUpdetails.put("P_BENE_BANK_BRANCH_ID",getBeneficiaryBranchId().toPlainString());

				List<ServiceGroupMasterDesc> lstServiceCode = iServiceGroupMasterService.getServiceGroupDescList(getRemittanceId());
				if (lstServiceCode != null) {
					ServiceGroupMasterDesc ServiceCode = lstServiceCode.get(0);
					inputRoutingBankSetUpdetails.put("P_SERVICE_GROUP_CODE", ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
					setServiceGroupCode(ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
				}
				inputRoutingBankSetUpdetails.put("P_CURRENCY_ID", getCurrencyId().toPlainString());

				
					/*HashMap<String ,String> bannedBankProcedureOut = iPersonalRemittanceService.getBannedBankCheckProcedure(session.getCountryId(),getBankId(),getBeneficiaryMasterId());

					if(bannedBankProcedureOut.get("P_ERROR_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ERROR_MESSAGE").equals("")){
						System.out.println("P_error_message :"+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
						setProcedureError("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
						RequestContext.getCurrentInstance().execute("procedureErr.show();");
						return;
					}else if(bannedBankProcedureOut.get("P_ALERT_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ALERT_MESSAGE").equals("")){
						System.out.println("P_ALERT_MESSAGE :"+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
						setProcedureError("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
						RequestContext.getCurrentInstance().execute("procedureErr.show();");
					}*/

					
					if( (session.getBranchId()!=null || session.getCustomerType().equals("E"))){ // && sessionStateManage.getRoleId().equals("1")
						inputRoutingBankSetUpdetails.put("P_USER_TYPE","BRANCH");
						setBooSingleService(true);
					}else if(session.getBranchId()!=null  &&  session.getUserType().equalsIgnoreCase("K")){
						inputRoutingBankSetUpdetails.put("P_USER_TYPE","KIOSK");
						setBooMultipleService(false);
					}else{
						setBooMultipleService(false);
					}

					setAccountSeqId(lstRatePlaceOrderUnApproved.get(0).getAccountSeqquenceId().getBeneficaryAccountSeqId());
						
						if (getRemittanceName().equalsIgnoreCase(Constants.CASHNAME)) {
						List<BeneficaryAccount> lstBeneficaryAccount= iPersonalRemittanceService.getCashProductDetails(getAccountSeqId());

						if(lstBeneficaryAccount.size()>0)
						{

							BeneficaryAccount beneficaryAccount =lstBeneficaryAccount.get(0);
							// setting service Master Id
							List<ServiceMaster> lstServiceMaster = serviceMasterService.toFetchServiceRecordsServiceMaster(beneficaryAccount.getServicegropupId().getServiceGroupId());
							if(lstServiceMaster.size() != 0){
								ServiceMaster serviceMasterId = lstServiceMaster.get(0);
								setDataserviceid(serviceMasterId.getServiceId());
								setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"), getDataserviceid()).get(0).getLocalServiceDescription());
								setBooSingleService(true);
								setBooMultipleService(false);
								System.out.println("Service Desc :"+getDatabenificaryservice()+"\t setDatabenificaryservice :"+getDatabenificaryservice());
							}

							setRoutingCountry(beneficaryAccount.getBank().getFsCountryMaster().getCountryId()); 
							String lstRcountry = generalService.getCountryName(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),getRoutingCountry());
							if (lstRcountry != null) {
								setRoutingCountryName(lstRcountry);
								setBooMultipleRoutingCountry(false);
								setBooSingleRoutingCountry(true);
							}

							if(beneficaryAccount.getServiceProvider() != null){
								setRoutingBank(beneficaryAccount.getServiceProvider().getBankId()); 
								String lstRBank = generalService.getBankName(getRoutingBank());
								if (lstRBank != null) {
									setRoutingBankName(lstRBank);
									setBooMultipleRoutingBank(false);
									setBooSingleRoutingBank(true);
								}
							}

							if(beneficaryAccount.getBankBranch() != null){
								setRoutingBranch(beneficaryAccount.getServiceProviderBranchId()); // routing Bank Branch Id
								List<BankBranch> lstRbranch = bankBranchDetailsService.getBankBranchByBranchID(getRoutingBranch());
								if (lstRbranch != null && lstRbranch.size() != 0) {
									BankBranch routingBranchName = lstRbranch.get(0);
									setRoutingBranchName(routingBranchName.getBranchFullName());
									setBooMultipleRoutingBranch(false);
									setBooSingleRoutingBranch(true);
								}
							}


							// to fetch remittance
							//remittancelistByBankIdForCash(beneficaryAccount.getServicegropupId().getServiceGroupId());
							remittancelistByBankIdForCash();
							setBooRenderRemitPanel(true);
							// to fetch remittance
							//remittancelistByBankId();
						}
						

					} else {

						HashMap<String, String> outputRoutingBankSetUpdetails = iPersonalRemittanceService.getRoutingBankSetupDetails(inputRoutingBankSetUpdetails);

						System.out.println("P_ERROR_MESSAGE"+outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));

						if (outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE") != null) {
							setProcedureError("EX_GET_ROUTING_SET_UP" + " : " +outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));
							RequestContext.getCurrentInstance().execute("procedureErr.show();");
							setBooRenderRemitPanel(false);
							return;
						} else {
							
							setBooRenderRemitPanel(true);
							if (!outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID").equalsIgnoreCase("0")) {
								setDataserviceid(new BigDecimal(outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID")));
								setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"), getDataserviceid()).get(0).getLocalServiceDescription());
								setBooSingleService(true);
								setBooMultipleService(false);
								System.out.println("Service Desc :"+getDatabenificaryservice()+"\t setDatabenificaryservice :"+getDatabenificaryservice());

								if (!outputRoutingBankSetUpdetails.get("P_ROUTING_COUNTRY_ID").equalsIgnoreCase("0")) {

									setRoutingCountry(new BigDecimal(outputRoutingBankSetUpdetails.get("P_ROUTING_COUNTRY_ID"))); 
									String lstRcountry = generalService.getCountryName(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),getRoutingCountry());
									if (lstRcountry != null) {
										setRoutingCountryName(lstRcountry);
										setBooMultipleRoutingCountry(false);
										setBooSingleRoutingCountry(true);
									}

									if (!outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_ID").equalsIgnoreCase("0")) {

										setRoutingBank(new BigDecimal(outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_ID"))); 
										String lstRBank = generalService.getBankName(getRoutingBank());
										if (lstRBank != null) {
											setRoutingBankName(lstRBank);
											setBooMultipleRoutingBank(false);
											setBooSingleRoutingBank(true);
										}

										if (!outputRoutingBankSetUpdetails.get("P_REMITTANCE_MODE_ID").equalsIgnoreCase("0")) {

											// spl pool based on routing country , routing bank
											//specialRequestFcAmountCalculation();
											setRemitMode(new BigDecimal(outputRoutingBankSetUpdetails.get("P_REMITTANCE_MODE_ID")));
											String remitName = iRemitModeMaster.getRemittanceDesc(getRemitMode(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"));
											if (remitName != null) {
												setRemittanceName(remitName);
												setBooMultipleRemit(false);
												setBooSingleRemit(true);
											}

											if (!outputRoutingBankSetUpdetails.get("P_DELIVERY_MODE_ID").equalsIgnoreCase("0")) {
												setDeliveryMode(new BigDecimal(outputRoutingBankSetUpdetails.get("P_DELIVERY_MODE_ID")));
												String deliveryName = iDeliveryModeMaster.getDeliveryDesc(
														getDeliveryMode(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"));
												if (deliveryName != null) {
													setDeliveryModeInput(deliveryName);
													setBooRenderDeliveryModeDDPanel(false);
													setBooRenderDeliveryModeInputPanel(true);
												}

												if (!outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_BRANCH_ID").equalsIgnoreCase("0")) {
													// set All values in form , No Need of view

													setRoutingBranch(new BigDecimal(outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_BRANCH_ID"))); // routing Bank Branch Id
													List<BankBranch> lstRbranch = bankBranchDetailsService.getBankBranchByBranchID(getRoutingBranch());
													if (lstRbranch != null && lstRbranch.size() != 0) {
														BankBranch routingBranchName = lstRbranch.get(0);
														setRoutingBranchName(routingBranchName.getBranchFullName());
														setBooMultipleRoutingBranch(false);
														setBooSingleRoutingBranch(true);
													}
												} else {
													// to fetch Bank Branch
													bankBranchByBankView();
												}

											} else {
												// to fetch Delivery
												deliverylistByRemitId();
											}

										} else {
											// to fetch remittance
											remittancelistByBankId();
										}

									} else {

										// setting routing bank Id from view
										bankDetailsByCountry();

									}

								} else {
									// fetch routing country from view
									List<PopulateData> lstRoutingCountry = iPersonalRemittanceService.getRoutingCountryList(session.getCountryId(),getBankId(),getBeneficiaryBranchId(),getCountryId(),
											getCurrencyId(),getDataserviceid(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"));

									if (lstRoutingCountry.size() == 0) {
										setBooSingleRoutingCountry(true);
										setBooMultipleRoutingCountry(false);
										setRoutingCountryName(null);
										setRoutingCountry(null);
										RequestContext.getCurrentInstance().execute("routingCountryNoData.show();");
										return;
									} else if (lstRoutingCountry.size() == 1) {
										setBooSingleRoutingCountry(true);
										setBooMultipleRoutingCountry(false);
										setRoutingCountry(lstRoutingCountry.get(0).getPopulateId());
										setRoutingCountryName(lstRoutingCountry.get(0).getPopulateName());
										// setting routing bank Id from view
										bankDetailsByCountry();
									} else {
										setRoutingCountryName(null);
										setBooSingleRoutingCountry(false);
										setBooMultipleRoutingCountry(true);
										setRoutingCountrylst(lstRoutingCountry);
									}

								}

							}else{ 
								setDataserviceid(new BigDecimal(outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID")));
								getServiceListDetails();
							}
						}
					}
						//procedure calling Ended 
			
				
				}else if (lstRatePlaceOrders.size()>0) {
					if(custrRef != null){
						setCustomerRefandName(custrRef.toString()+ "-" +custmerName);
						}
					for (RatePlaceOrder ratePlaceOrder : lstRatePlaceOrders) {
						String countryName=null;
						String remitType=null;
						String BeneName=null;
						String curQtyName=null;
						String beneBankName=null;
						String couBranchName=null;
						GSMPlaceOrderRateFeedDataTable dataTable=new GSMPlaceOrderRateFeedDataTable();
							dataTable.setRateOfferedIdPk(ratePlaceOrder.getRatePlaceOrderId());
							dataTable.setDateOfRequest(ratePlaceOrder.getCreatedDate());
							if(ratePlaceOrder.getCountryBranchId() != null){
								/*List<CountryBranch> list=generalService.getBranchDetailsForToLocation(session.getCountryId(), ratePlaceOrder.getCountryBranchId());
								if(list.size() != 0){
								dataTable.setBranch(list.get(0).getBranchName());
								}*/
									couBranchName=stopPaymentService.toFetchCountryBranchName(session.getCountryId(),ratePlaceOrder.getCountryBranchId());
									dataTable.setBranch(couBranchName);
								}
							
							if(ratePlaceOrder.getDestinationCurrenyId() != null){
							dataTable.setCurrency(generalService.getCurrencyName(ratePlaceOrder.getDestinationCurrenyId()));
							}
							if(ratePlaceOrder.getBeneficiaryCountryId() != null){
								countryName=generalService.getCountryName(session.getLanguageId(), ratePlaceOrder.getBeneficiaryCountryId());
								dataTable.setCountryName(countryName);
							}
							if(ratePlaceOrder.getRemitType() != null){
								remitType=gSMPlaceOrderRateFeedService.toFetchServiceGroupDesc(session.getLanguageId(),ratePlaceOrder.getRemitType());
								dataTable.setRemittanceName(remitType);
							}
							if(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId() != null){
								BeneName=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
								dataTable.setBeneficiaryName(BeneName);
								curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(ratePlaceOrder.getDestinationCurrenyId());
								dataTable.setEquivalentCurrencyDt("["+curQtyName+"]");
							}
							if(ratePlaceOrder.getBeneficiaryBankId() != null){
								beneBankName=generalService.getBankName(ratePlaceOrder.getBeneficiaryBankId());
								dataTable.setBankName(beneBankName);
							}						
							dataTable.setAmount(ratePlaceOrder.getTransactionAmount());
							dataTable.setRateOffered(ratePlaceOrder.getRateOffered());
							//dataTable.setTransctionConcluded(getTransctionConcluded());
							dataTable.setCreatedBy(ratePlaceOrder.getCreatedBy());
							dataTable.setCreatedDate(ratePlaceOrder.getCreatedDate());
							dataTable.setEmailId(ratePlaceOrder.getCustomerEmail());
							dataTable.setCountryId(ratePlaceOrder.getBeneficiaryCountryId());
							dataTable.setRemittanceId(ratePlaceOrder.getRemitType());
							dataTable.setBeneficiaryId(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
							dataTable.setAccountSeqId(ratePlaceOrder.getAccountSeqquenceId().getBeneficaryAccountSeqId());
							dataTable.setBankId(ratePlaceOrder.getBeneficiaryBankId());
							groupSalesMgrSpecialRateReqForPlaceOrder.add(dataTable);
						}
					setBooRenderDataTable(true);
					setBooRenderSaveOrExit(true);
					setBooRenderClear(true);
					if(lstRatePlaceOrderUnApproved.size() >0){
					String couBranchName=null;
					String currName=null;
					String beneficiaryCountryName=null;
					String remittanceType=null;
					String Beneficiary=null;
					String benefiBankName=null;
					String currencyQtyName=null;
					BigDecimal BeneBranchId=null;
					//text field population
					if(lstRatePlaceOrderUnApproved.get(0).getCountryBranchId() != null){
					couBranchName=stopPaymentService.toFetchCountryBranchName(session.getCountryId(),lstRatePlaceOrderUnApproved.get(0).getCountryBranchId());
					setBranch(couBranchName);
					}
					if(lstRatePlaceOrderUnApproved.get(0).getDestinationCurrenyId() != null){
					setCurrencyId(lstRatePlaceOrderUnApproved.get(0).getDestinationCurrenyId());
					currName=generalService.getCurrencyName(lstRatePlaceOrderUnApproved.get(0).getDestinationCurrenyId());
					setCurrency(currName);
					}
					if(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryCountryId() != null){
						setCountryId(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryCountryId());
						beneficiaryCountryName=generalService.getCountryName(session.getLanguageId(), lstRatePlaceOrderUnApproved.get(0).getBeneficiaryCountryId());
						setCountryName(beneficiaryCountryName);
					}
					if(lstRatePlaceOrderUnApproved.get(0).getRemitType() != null){
						setRemittanceId(lstRatePlaceOrderUnApproved.get(0).getRemitType());
						remittanceType=gSMPlaceOrderRateFeedService.toFetchServiceGroupDesc(session.getLanguageId(),lstRatePlaceOrderUnApproved.get(0).getRemitType());
						setRemittanceName(remittanceType);
					}
					if(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryBankId() != null){
						setBankId(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryBankId());
						benefiBankName=generalService.getBankName(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryBankId());
						setBankName(benefiBankName);
					}
					if(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryMasterId().getBeneficaryMasterSeqId() != null){
						Beneficiary=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryMasterId().getBeneficaryMasterSeqId());
						setBeneficiaryName(Beneficiary);
						currencyQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(lstRatePlaceOrderUnApproved.get(0).getDestinationCurrenyId());
						setEquivalentCurrency("["+currencyQtyName+"]");
					}
					setAmount(lstRatePlaceOrderUnApproved.get(0).getTransactionAmount());
					setRateOfferedIdPk(lstRatePlaceOrderUnApproved.get(0).getRatePlaceOrderId());
					setEmailId(lstRatePlaceOrderUnApproved.get(0).getCustomerEmail());
					setCreatedDate(lstRatePlaceOrderUnApproved.get(0).getCreatedDate());
					setDocumentNumber(lstRatePlaceOrderUnApproved.get(0).getDocumentNumber());
					BeneBranchId=gSMPlaceOrderRateFeedService.toFetchBankBranchId(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryBankId(),lstRatePlaceOrderUnApproved.get(0).getBeneficiaryMasterId().getBeneficaryMasterSeqId(),lstRatePlaceOrderUnApproved.get(0).getAccountSeqquenceId().getBeneficaryAccountSeqId());
					setBeneficiaryBranchId(BeneBranchId);
					//toset routing Bank
					
					
					//procedure calling started 
					
					HashMap<String, String> inputRoutingBankSetUpdetails = new HashMap<String, String>();

					inputRoutingBankSetUpdetails.put("P_APPLICATION_COUNTRY_ID", session.getCountryId().toPlainString());
					inputRoutingBankSetUpdetails.put("P_BENE_COUNTRY_ID", getCountryId().toPlainString()); // beneficiary bank Country Id
					inputRoutingBankSetUpdetails.put("P_BENE_BANK_ID", getBankId().toPlainString());
					inputRoutingBankSetUpdetails.put("P_BENE_BANK_BRANCH_ID",getBeneficiaryBranchId().toPlainString());

					List<ServiceGroupMasterDesc> lstServiceCode = iServiceGroupMasterService.getServiceGroupDescList(getRemittanceId());
					if (lstServiceCode != null) {
						ServiceGroupMasterDesc ServiceCode = lstServiceCode.get(0);
						inputRoutingBankSetUpdetails.put("P_SERVICE_GROUP_CODE", ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
						setServiceGroupCode(ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
					}
					inputRoutingBankSetUpdetails.put("P_CURRENCY_ID", getCurrencyId().toPlainString());

					
						/*HashMap<String ,String> bannedBankProcedureOut = iPersonalRemittanceService.getBannedBankCheckProcedure(session.getCountryId(),getBankId(),getBeneficiaryMasterId());

						if(bannedBankProcedureOut.get("P_ERROR_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ERROR_MESSAGE").equals("")){
							System.out.println("P_error_message :"+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
							setProcedureError("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
							RequestContext.getCurrentInstance().execute("procedureErr.show();");
							return;
						}else if(bannedBankProcedureOut.get("P_ALERT_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ALERT_MESSAGE").equals("")){
							System.out.println("P_ALERT_MESSAGE :"+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
							setProcedureError("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
							RequestContext.getCurrentInstance().execute("procedureErr.show();");
						}*/

						
						if( (session.getBranchId()!=null || session.getCustomerType().equals("E"))){ // && sessionStateManage.getRoleId().equals("1")
							inputRoutingBankSetUpdetails.put("P_USER_TYPE","BRANCH");
							setBooSingleService(true);
						}else if(session.getBranchId()!=null  &&  session.getUserType().equalsIgnoreCase("K")){
							inputRoutingBankSetUpdetails.put("P_USER_TYPE","KIOSK");
							setBooMultipleService(false);
						}else{
							setBooMultipleService(false);
						}

						setAccountSeqId(lstRatePlaceOrderUnApproved.get(0).getAccountSeqquenceId().getBeneficaryAccountSeqId());
							
							if (getRemittanceName().equalsIgnoreCase(Constants.CASHNAME)) {
							List<BeneficaryAccount> lstBeneficaryAccount= iPersonalRemittanceService.getCashProductDetails(getAccountSeqId());

							if(lstBeneficaryAccount.size()>0)
							{

								BeneficaryAccount beneficaryAccount =lstBeneficaryAccount.get(0);
								// setting service Master Id
								List<ServiceMaster> lstServiceMaster = serviceMasterService.toFetchServiceRecordsServiceMaster(beneficaryAccount.getServicegropupId().getServiceGroupId());
								if(lstServiceMaster.size() != 0){
									ServiceMaster serviceMasterId = lstServiceMaster.get(0);
									setDataserviceid(serviceMasterId.getServiceId());
									setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"), getDataserviceid()).get(0).getLocalServiceDescription());
									setBooSingleService(true);
									setBooMultipleService(false);
									System.out.println("Service Desc :"+getDatabenificaryservice()+"\t setDatabenificaryservice :"+getDatabenificaryservice());
								}

								setRoutingCountry(beneficaryAccount.getBank().getFsCountryMaster().getCountryId()); 
								String lstRcountry = generalService.getCountryName(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),getRoutingCountry());
								if (lstRcountry != null) {
									setRoutingCountryName(lstRcountry);
									setBooMultipleRoutingCountry(false);
									setBooSingleRoutingCountry(true);
								}

								if(beneficaryAccount.getServiceProvider() != null){
									setRoutingBank(beneficaryAccount.getServiceProvider().getBankId()); 
									String lstRBank = generalService.getBankName(getRoutingBank());
									if (lstRBank != null) {
										setRoutingBankName(lstRBank);
										setBooMultipleRoutingBank(false);
										setBooSingleRoutingBank(true);
									}
								}

								if(beneficaryAccount.getBankBranch() != null){
									setRoutingBranch(beneficaryAccount.getServiceProviderBranchId()); // routing Bank Branch Id
									List<BankBranch> lstRbranch = bankBranchDetailsService.getBankBranchByBranchID(getRoutingBranch());
									if (lstRbranch != null && lstRbranch.size() != 0) {
										BankBranch routingBranchName = lstRbranch.get(0);
										setRoutingBranchName(routingBranchName.getBranchFullName());
										setBooMultipleRoutingBranch(false);
										setBooSingleRoutingBranch(true);
									}
								}


								// to fetch remittance
								//remittancelistByBankIdForCash(beneficaryAccount.getServicegropupId().getServiceGroupId());
								remittancelistByBankIdForCash();
								setBooRenderRemitPanel(true);
								// to fetch remittance
								//remittancelistByBankId();
							}
							

						} else {

							HashMap<String, String> outputRoutingBankSetUpdetails = iPersonalRemittanceService.getRoutingBankSetupDetails(inputRoutingBankSetUpdetails);

							System.out.println("P_ERROR_MESSAGE"+outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));

							if (outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE") != null) {
								setProcedureError("EX_GET_ROUTING_SET_UP" + " : " +outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));
								RequestContext.getCurrentInstance().execute("procedureErr.show();");
								setBooRenderRemitPanel(false);
								return;
							} else {
								setBooRenderRemitPanel(true);

								if (!outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID").equalsIgnoreCase("0")) {
									setDataserviceid(new BigDecimal(outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID")));
									setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"), getDataserviceid()).get(0).getLocalServiceDescription());
									setBooSingleService(true);
									setBooMultipleService(false);
									System.out.println("Service Desc :"+getDatabenificaryservice()+"\t setDatabenificaryservice :"+getDatabenificaryservice());

									if (!outputRoutingBankSetUpdetails.get("P_ROUTING_COUNTRY_ID").equalsIgnoreCase("0")) {

										setRoutingCountry(new BigDecimal(outputRoutingBankSetUpdetails.get("P_ROUTING_COUNTRY_ID"))); 
										String lstRcountry = generalService.getCountryName(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),getRoutingCountry());
										if (lstRcountry != null) {
											setRoutingCountryName(lstRcountry);
											setBooMultipleRoutingCountry(false);
											setBooSingleRoutingCountry(true);
										}

										if (!outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_ID").equalsIgnoreCase("0")) {

											setRoutingBank(new BigDecimal(outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_ID"))); 
											String lstRBank = generalService.getBankName(getRoutingBank());
											if (lstRBank != null) {
												setRoutingBankName(lstRBank);
												setBooMultipleRoutingBank(false);
												setBooSingleRoutingBank(true);
											}

											if (!outputRoutingBankSetUpdetails.get("P_REMITTANCE_MODE_ID").equalsIgnoreCase("0")) {

												// spl pool based on routing country , routing bank
												//specialRequestFcAmountCalculation();
												setRemitMode(new BigDecimal(outputRoutingBankSetUpdetails.get("P_REMITTANCE_MODE_ID")));
												String remitName = iRemitModeMaster.getRemittanceDesc(getRemitMode(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"));
												if (remitName != null) {
													setRemittanceName(remitName);
													setBooMultipleRemit(false);
													setBooSingleRemit(true);
												}

												if (!outputRoutingBankSetUpdetails.get("P_DELIVERY_MODE_ID").equalsIgnoreCase("0")) {
													setDeliveryMode(new BigDecimal(outputRoutingBankSetUpdetails.get("P_DELIVERY_MODE_ID")));
													String deliveryName = iDeliveryModeMaster.getDeliveryDesc(
															getDeliveryMode(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"));
													if (deliveryName != null) {
														setDeliveryModeInput(deliveryName);
														setBooRenderDeliveryModeDDPanel(false);
														setBooRenderDeliveryModeInputPanel(true);
													}

													if (!outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_BRANCH_ID").equalsIgnoreCase("0")) {
														// set All values in form , No Need of view

														setRoutingBranch(new BigDecimal(outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_BRANCH_ID"))); // routing Bank Branch Id
														List<BankBranch> lstRbranch = bankBranchDetailsService.getBankBranchByBranchID(getRoutingBranch());
														if (lstRbranch != null && lstRbranch.size() != 0) {
															BankBranch routingBranchName = lstRbranch.get(0);
															setRoutingBranchName(routingBranchName.getBranchFullName());
															setBooMultipleRoutingBranch(false);
															setBooSingleRoutingBranch(true);
														}
													} else {
														// to fetch Bank Branch
														bankBranchByBankView();
													}

												} else {
													// to fetch Delivery
													deliverylistByRemitId();
												}

											} else {
												// to fetch remittance
												remittancelistByBankId();
											}

										} else {

											// setting routing bank Id from view
											bankDetailsByCountry();

										}

									} else {
										// fetch routing country from view
										List<PopulateData> lstRoutingCountry = iPersonalRemittanceService.getRoutingCountryList(session.getCountryId(),getBankId(),getBeneficiaryBranchId(),getCountryId(),
												getCurrencyId(),getDataserviceid(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"));

										if (lstRoutingCountry.size() == 0) {
											setBooSingleRoutingCountry(true);
											setBooMultipleRoutingCountry(false);
											setRoutingCountryName(null);
											setRoutingCountry(null);
											RequestContext.getCurrentInstance().execute("routingCountryNoData.show();");
											return;
										} else if (lstRoutingCountry.size() == 1) {
											setBooSingleRoutingCountry(true);
											setBooMultipleRoutingCountry(false);
											setRoutingCountry(lstRoutingCountry.get(0).getPopulateId());
											setRoutingCountryName(lstRoutingCountry.get(0).getPopulateName());
											// setting routing bank Id from view
											bankDetailsByCountry();
										} else {
											setRoutingCountryName(null);
											setBooSingleRoutingCountry(false);
											setBooMultipleRoutingCountry(true);
											setRoutingCountrylst(lstRoutingCountry);
										}

									}

								}else{ 
									setDataserviceid(new BigDecimal(outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID")));
									getServiceListDetails();
								}
							}
						}
							//procedure calling Ended 
						
					}
					}else if (lstRatePlaceOrderUnApproved.size() >0) {
						if(custrRef != null){
							setCustomerRefandName(custrRef.toString()+ "-" +custmerName);
							}
						setBooRenderDataTable(true);
						setBooRenderSaveOrExit(true);
						setBooRenderClear(true);
						String couBranchName=null;
						String currName=null;
						String beneficiaryCountryName=null;
						String remittanceType=null;
						String Beneficiary=null;
						String benefiBankName=null;
						String currencyQtyName=null;
						BigDecimal BeneBranchId=null;
						//text field population
						if(lstRatePlaceOrderUnApproved.get(0).getCountryBranchId() != null){
						couBranchName=stopPaymentService.toFetchCountryBranchName(session.getCountryId(),lstRatePlaceOrderUnApproved.get(0).getCountryBranchId());
						setBranch(couBranchName);
						}
						if(lstRatePlaceOrderUnApproved.get(0).getDestinationCurrenyId() != null){
						setCurrencyId(lstRatePlaceOrderUnApproved.get(0).getDestinationCurrenyId());
						currName=generalService.getCurrencyName(lstRatePlaceOrderUnApproved.get(0).getDestinationCurrenyId());
						setCurrency(currName);
						}
						if(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryCountryId() != null){
							setCountryId(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryCountryId());
							beneficiaryCountryName=generalService.getCountryName(session.getLanguageId(), lstRatePlaceOrderUnApproved.get(0).getBeneficiaryCountryId());
							setCountryName(beneficiaryCountryName);
						}
						if(lstRatePlaceOrderUnApproved.get(0).getRemitType() != null){
							setRemittanceId(lstRatePlaceOrderUnApproved.get(0).getRemitType());
							remittanceType=gSMPlaceOrderRateFeedService.toFetchServiceGroupDesc(session.getLanguageId(),lstRatePlaceOrderUnApproved.get(0).getRemitType());
							setRemittanceName(remittanceType);
						}
						if(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryBankId() != null){
							setBankId(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryBankId());
							benefiBankName=generalService.getBankName(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryBankId());
							setBankName(benefiBankName);
						}
						if(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryMasterId().getBeneficaryMasterSeqId() != null){
							Beneficiary=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryMasterId().getBeneficaryMasterSeqId());
							setBeneficiaryName(Beneficiary);
							currencyQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(lstRatePlaceOrderUnApproved.get(0).getDestinationCurrenyId());
							setEquivalentCurrency("["+currencyQtyName+"]");
						}
						setAmount(lstRatePlaceOrderUnApproved.get(0).getTransactionAmount());
						setRateOfferedIdPk(lstRatePlaceOrderUnApproved.get(0).getRatePlaceOrderId());
						setEmailId(lstRatePlaceOrderUnApproved.get(0).getCustomerEmail());
						setCreatedDate(lstRatePlaceOrderUnApproved.get(0).getCreatedDate());
						setDocumentNumber(lstRatePlaceOrderUnApproved.get(0).getDocumentNumber());
						
						BeneBranchId=gSMPlaceOrderRateFeedService.toFetchBankBranchId(lstRatePlaceOrderUnApproved.get(0).getBeneficiaryBankId(),lstRatePlaceOrderUnApproved.get(0).getBeneficiaryMasterId().getBeneficaryMasterSeqId(),lstRatePlaceOrderUnApproved.get(0).getAccountSeqquenceId().getBeneficaryAccountSeqId());
						setBeneficiaryBranchId(BeneBranchId);
						
						//procedure calling started 
						
						HashMap<String, String> inputRoutingBankSetUpdetails = new HashMap<String, String>();

						inputRoutingBankSetUpdetails.put("P_APPLICATION_COUNTRY_ID", session.getCountryId().toPlainString());
						inputRoutingBankSetUpdetails.put("P_BENE_COUNTRY_ID", getCountryId().toPlainString()); // beneficiary bank Country Id
						inputRoutingBankSetUpdetails.put("P_BENE_BANK_ID", getBankId().toPlainString());
						inputRoutingBankSetUpdetails.put("P_BENE_BANK_BRANCH_ID",getBeneficiaryBranchId().toPlainString());

						List<ServiceGroupMasterDesc> lstServiceCode = iServiceGroupMasterService.getServiceGroupDescList(getRemittanceId());
						if (lstServiceCode != null) {
							ServiceGroupMasterDesc ServiceCode = lstServiceCode.get(0);
							inputRoutingBankSetUpdetails.put("P_SERVICE_GROUP_CODE", ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
							setServiceGroupCode(ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
						}
						inputRoutingBankSetUpdetails.put("P_CURRENCY_ID", getCurrencyId().toPlainString());

						
							/*HashMap<String ,String> bannedBankProcedureOut = iPersonalRemittanceService.getBannedBankCheckProcedure(session.getCountryId(),getBankId(),getBeneficiaryMasterId());

							if(bannedBankProcedureOut.get("P_ERROR_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ERROR_MESSAGE").equals("")){
								System.out.println("P_error_message :"+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
								setProcedureError("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
								RequestContext.getCurrentInstance().execute("procedureErr.show();");
								return;
							}else if(bannedBankProcedureOut.get("P_ALERT_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ALERT_MESSAGE").equals("")){
								System.out.println("P_ALERT_MESSAGE :"+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
								setProcedureError("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
								RequestContext.getCurrentInstance().execute("procedureErr.show();");
							}*/

							
							if( (session.getBranchId()!=null || session.getCustomerType().equals("E"))){ // && sessionStateManage.getRoleId().equals("1")
								inputRoutingBankSetUpdetails.put("P_USER_TYPE","BRANCH");
								setBooSingleService(true);
							}else if(session.getBranchId()!=null  &&  session.getUserType().equalsIgnoreCase("K")){
								inputRoutingBankSetUpdetails.put("P_USER_TYPE","KIOSK");
								setBooMultipleService(false);
							}else{
								setBooMultipleService(false);
							}

						
							setAccountSeqId(lstRatePlaceOrderUnApproved.get(0).getAccountSeqquenceId().getBeneficaryAccountSeqId());
								if (getRemittanceName().equalsIgnoreCase(Constants.CASHNAME)) {
								List<BeneficaryAccount> lstBeneficaryAccount= iPersonalRemittanceService.getCashProductDetails(getAccountSeqId());

								if(lstBeneficaryAccount.size()>0)
								{

									BeneficaryAccount beneficaryAccount =lstBeneficaryAccount.get(0);
									// setting service Master Id
									List<ServiceMaster> lstServiceMaster = serviceMasterService.toFetchServiceRecordsServiceMaster(beneficaryAccount.getServicegropupId().getServiceGroupId());
									if(lstServiceMaster.size() != 0){
										ServiceMaster serviceMasterId = lstServiceMaster.get(0);
										setDataserviceid(serviceMasterId.getServiceId());
										setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"), getDataserviceid()).get(0).getLocalServiceDescription());
										setBooSingleService(true);
										setBooMultipleService(false);
										System.out.println("Service Desc :"+getDatabenificaryservice()+"\t setDatabenificaryservice :"+getDatabenificaryservice());
									}

									setRoutingCountry(beneficaryAccount.getBank().getFsCountryMaster().getCountryId()); 
									String lstRcountry = generalService.getCountryName(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),getRoutingCountry());
									if (lstRcountry != null) {
										setRoutingCountryName(lstRcountry);
										setBooMultipleRoutingCountry(false);
										setBooSingleRoutingCountry(true);
									}

									if(beneficaryAccount.getServiceProvider() != null){
										setRoutingBank(beneficaryAccount.getServiceProvider().getBankId()); 
										String lstRBank = generalService.getBankName(getRoutingBank());
										if (lstRBank != null) {
											setRoutingBankName(lstRBank);
											setBooMultipleRoutingBank(false);
											setBooSingleRoutingBank(true);
										}
									}

									if(beneficaryAccount.getBankBranch() != null){
										setRoutingBranch(beneficaryAccount.getServiceProviderBranchId()); // routing Bank Branch Id
										List<BankBranch> lstRbranch = bankBranchDetailsService.getBankBranchByBranchID(getRoutingBranch());
										if (lstRbranch != null && lstRbranch.size() != 0) {
											BankBranch routingBranchName = lstRbranch.get(0);
											setRoutingBranchName(routingBranchName.getBranchFullName());
											setBooMultipleRoutingBranch(false);
											setBooSingleRoutingBranch(true);
										}
									}


									// to fetch remittance
									//remittancelistByBankIdForCash(beneficaryAccount.getServicegropupId().getServiceGroupId());
									remittancelistByBankIdForCash();
									setBooRenderRemitPanel(true);
									// to fetch remittance
									//remittancelistByBankId();
								}
								

							} else {

								HashMap<String, String> outputRoutingBankSetUpdetails = iPersonalRemittanceService.getRoutingBankSetupDetails(inputRoutingBankSetUpdetails);

								System.out.println("P_ERROR_MESSAGE"+outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));

								if (outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE") != null) {
									setProcedureError("EX_GET_ROUTING_SET_UP" + " : " +outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));
									RequestContext.getCurrentInstance().execute("procedureErr.show();");
									setBooRenderRemitPanel(false);
									return;
								} else {
									
									setBooRenderRemitPanel(true);


									if (!outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID").equalsIgnoreCase("0")) {
										setDataserviceid(new BigDecimal(outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID")));
										setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"), getDataserviceid()).get(0).getLocalServiceDescription());
										setBooSingleService(true);
										setBooMultipleService(false);
										System.out.println("Service Desc :"+getDatabenificaryservice()+"\t setDatabenificaryservice :"+getDatabenificaryservice());

										if (!outputRoutingBankSetUpdetails.get("P_ROUTING_COUNTRY_ID").equalsIgnoreCase("0")) {

											setRoutingCountry(new BigDecimal(outputRoutingBankSetUpdetails.get("P_ROUTING_COUNTRY_ID"))); 
											String lstRcountry = generalService.getCountryName(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"),getRoutingCountry());
											if (lstRcountry != null) {
												setRoutingCountryName(lstRcountry);
												setBooMultipleRoutingCountry(false);
												setBooSingleRoutingCountry(true);
											}

											if (!outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_ID").equalsIgnoreCase("0")) {

												setRoutingBank(new BigDecimal(outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_ID"))); 
												String lstRBank = generalService.getBankName(getRoutingBank());
												if (lstRBank != null) {
													setRoutingBankName(lstRBank);
													setBooMultipleRoutingBank(false);
													setBooSingleRoutingBank(true);
												}

												if (!outputRoutingBankSetUpdetails.get("P_REMITTANCE_MODE_ID").equalsIgnoreCase("0")) {

													// spl pool based on routing country , routing bank
													//specialRequestFcAmountCalculation();
													setRemitMode(new BigDecimal(outputRoutingBankSetUpdetails.get("P_REMITTANCE_MODE_ID")));
													String remitName = iRemitModeMaster.getRemittanceDesc(getRemitMode(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"));
													if (remitName != null) {
														setRemittanceName(remitName);
														setBooMultipleRemit(false);
														setBooSingleRemit(true);
													}

													if (!outputRoutingBankSetUpdetails.get("P_DELIVERY_MODE_ID").equalsIgnoreCase("0")) {
														setDeliveryMode(new BigDecimal(outputRoutingBankSetUpdetails.get("P_DELIVERY_MODE_ID")));
														String deliveryName = iDeliveryModeMaster.getDeliveryDesc(
																getDeliveryMode(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"));
														if (deliveryName != null) {
															setDeliveryModeInput(deliveryName);
															setBooRenderDeliveryModeDDPanel(false);
															setBooRenderDeliveryModeInputPanel(true);
														}

														if (!outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_BRANCH_ID").equalsIgnoreCase("0")) {
															// set All values in form , No Need of view

															setRoutingBranch(new BigDecimal(outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_BRANCH_ID"))); // routing Bank Branch Id
															List<BankBranch> lstRbranch = bankBranchDetailsService.getBankBranchByBranchID(getRoutingBranch());
															if (lstRbranch != null && lstRbranch.size() != 0) {
																BankBranch routingBranchName = lstRbranch.get(0);
																setRoutingBranchName(routingBranchName.getBranchFullName());
																setBooMultipleRoutingBranch(false);
																setBooSingleRoutingBranch(true);
															}
														} else {
															// to fetch Bank Branch
															bankBranchByBankView();
														}

													} else {
														// to fetch Delivery
														deliverylistByRemitId();
													}

												} else {
													// to fetch remittance
													remittancelistByBankId();
												}

											} else {

												// setting routing bank Id from view
												bankDetailsByCountry();

											}

										} else {
											// fetch routing country from view
											List<PopulateData> lstRoutingCountry = iPersonalRemittanceService.getRoutingCountryList(session.getCountryId(),getBankId(),getBeneficiaryBranchId(),getCountryId(),
													getCurrencyId(),getDataserviceid(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"));

											if (lstRoutingCountry.size() == 0) {
												setBooSingleRoutingCountry(true);
												setBooMultipleRoutingCountry(false);
												setRoutingCountryName(null);
												setRoutingCountry(null);
												RequestContext.getCurrentInstance().execute("routingCountryNoData.show();");
												return;
											} else if (lstRoutingCountry.size() == 1) {
												setBooSingleRoutingCountry(true);
												setBooMultipleRoutingCountry(false);
												setRoutingCountry(lstRoutingCountry.get(0).getPopulateId());
												setRoutingCountryName(lstRoutingCountry.get(0).getPopulateName());
												// setting routing bank Id from view
												bankDetailsByCountry();
											} else {
												setRoutingCountryName(null);
												setBooSingleRoutingCountry(false);
												setBooMultipleRoutingCountry(true);
												setRoutingCountrylst(lstRoutingCountry);
											}

										}
										
										//procedure calling Ended 

									}else{ 
										setDataserviceid(new BigDecimal(outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID")));
										getServiceListDetails();
									}
								}
							}
								//procedure calling Ended 
						
				
					}/*else{	
						if(custrRef != null){
							setCustomerRefandName(custrRef.toString()+ "-" +custmerName);
							}
					setBooRenderClear(false);
					setBooRenderDataTable(true);
					setBooRenderSaveOrExit(true);
					}*/
			
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::toFetchAllRecords");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
			}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
			}
	}
	
	//procedure internal calling methods start
	public void remittancelistByBankIdForCash(){

		// spl pool based on routing country , routing bank
		List<PopulateData> lstRemitView;
		try {
			lstRemitView = iPersonalRemittanceService.getRemittanceListByCountryBankForCashProduct(session.getCountryId(),getBankId(),getBeneficiaryBranchId(),
							getCountryId(),getCurrencyId(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),getRoutingBranch(),
							new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));
			if (lstRemitView.size() == 0) {
				setBooSingleRemit(true);
				setBooMultipleRemit(false);
				setRemittanceName(null);
				setRemitMode(null);
				RequestContext.getCurrentInstance().execute("remittanceNoData.show();");
				return;
			} else if (lstRemitView.size() == 1) {
				setBooSingleRemit(true);
				setBooMultipleRemit(false);
				setRemittanceName(lstRemitView.get(0).getPopulateName());
				setRemitMode(lstRemitView.get(0).getPopulateId());
				deliverylistByRemitIdForCash();
			} else {
				setRemittanceName(null);
				setRemitMode(null);
				setBooSingleRemit(false);
				setBooMultipleRemit(true);
				setLstofRemittance(lstRemitView);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deliverylistByRemitIdForCash(){
		
		List<PopulateData> lstDeliveryView;
		try {
			lstDeliveryView = iPersonalRemittanceService
					.getDeliveryListByCountryBankRemitForCashProduct(session.getCountryId(),getBankId(),getBeneficiaryBranchId(),
							getCountryId(),getCurrencyId(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),getRoutingBranch(),getRemitMode(),
							new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));

			if (lstDeliveryView.size() == 0) {
				setBooRenderDeliveryModeInputPanel(true);
				setBooRenderDeliveryModeDDPanel(false);
				setDeliveryModeInput(null);
				setDeliveryMode(null);
				RequestContext.getCurrentInstance().execute("DeliveryNoData.show();");
				return;
			} else if (lstDeliveryView.size() == 1) {
				setBooRenderDeliveryModeInputPanel(true);
				setBooRenderDeliveryModeDDPanel(false);
				setDeliveryModeInput(lstDeliveryView.get(0).getPopulateName());
				setDeliveryMode(lstDeliveryView.get(0).getPopulateId());
				//bankBranchByBankView();
			} else {
				setDeliveryModeInput(null);
				setDeliveryMode(null);
				setBooRenderDeliveryModeInputPanel(false);
				setBooRenderDeliveryModeDDPanel(true);
				setLstofDelivery(lstDeliveryView);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void bankBranchByBankView(){
		
		if (getRoutingBank() != null) {
			List<PopulateData> lstRoutingBankBranch = iPersonalRemittanceService.getRoutingBranchList(session.getCountryId(),
							getBankId(), getBeneficiaryBranchId(),getCountryId(),getCurrencyId(), getDataserviceid(),
							getRoutingCountry(), getRoutingBank(),getRemitMode(), getDeliveryMode());

			if (lstRoutingBankBranch.size() == 0) {
				setBooSingleRoutingBranch(true);
				setBooMultipleRoutingBranch(false);
				setRoutingBranchName(null);
				setRoutingBranch(null);
			} else if (lstRoutingBankBranch.size() == 1) {
				setBooSingleRoutingBranch(true);
				setBooMultipleRoutingBranch(false);
				setRoutingBranch(lstRoutingBankBranch.get(0).getPopulateId());
				setRoutingBranchName(lstRoutingBankBranch.get(0).getPopulateName());
			} else {
				setRoutingBranchName(null);
				setRoutingBranch(null);
				setBooSingleRoutingBranch(false);
				setBooMultipleRoutingBranch(true);
				setRoutingBankBranchlst(lstRoutingBankBranch);
			}

		}
	}
	
	public void deliverylistByRemitId(){
		
		List<PopulateData> lstDeliveryView = iPersonalRemittanceService.getDeliveryListByCountryBankRemit(session.getCountryId(),getBankId(),getBeneficiaryBranchId(),
						//getDatabenificarycountry(),
						getCountryId(),
						getCurrencyId(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),getRemitMode(),
						new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));

		if (lstDeliveryView.size() == 0) {
			setBooRenderDeliveryModeInputPanel(true);
			setBooRenderDeliveryModeDDPanel(false);
			setDeliveryModeInput(null);
			setDeliveryMode(null);
			RequestContext.getCurrentInstance().execute("DeliveryNoData.show();");
			return;
		} else if (lstDeliveryView.size() == 1) {
			setBooRenderDeliveryModeInputPanel(true);
			setBooRenderDeliveryModeDDPanel(false);
			setDeliveryModeInput(lstDeliveryView.get(0).getPopulateName());
			setDeliveryMode(lstDeliveryView.get(0).getPopulateId());
			bankBranchByBankView();
		} else {
			setDeliveryModeInput(null);
			setDeliveryMode(null);
			setBooRenderDeliveryModeInputPanel(false);
			setBooRenderDeliveryModeDDPanel(true);
			setLstofDelivery(lstDeliveryView);
		}
	}
	
	public void remittancelistByBankId(){

		// spl pool based on routing country , routing bank
		
		List<PopulateData> lstRemitView = iPersonalRemittanceService
				.getRemittanceListByCountryBank(session.getCountryId(),getBankId(),getBeneficiaryBranchId(),
						//getDatabenificarycountry(),
						getCountryId(),
						getCurrencyId(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),
						new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));



		if (lstRemitView.size() == 0) {
			setBooSingleRemit(true);
			setBooMultipleRemit(false);
			setRemittanceName(null);
			setRemitMode(null);
			RequestContext.getCurrentInstance().execute("remittanceNoData.show();");
			return;
		} else if (lstRemitView.size() == 1) {
			setBooSingleRemit(true);
			setBooMultipleRemit(false);
			setRemittanceName(lstRemitView.get(0).getPopulateName());
			setRemitMode(lstRemitView.get(0).getPopulateId());
			deliverylistByRemitId();
		} else {
			setRemittanceName(null);
			setRemitMode(null);
			setBooSingleRemit(false);
			setBooMultipleRemit(true);
			setLstofRemittance(lstRemitView);
			//setRemitMode(lstRemitView.get(0).getPopulateId());
			//deliverylistByRemitId();
		}
	}
	
	public void bankDetailsByCountry(){
		


		System.out.println("bankDetailsByCountry getDataserviceid bankDetailsByCountry :"+getDataserviceid());
		if (getRoutingCountry() != null) {
			System.out.println("Service name : " + getDatabenificaryservice());
			Boolean ttCheckSameNotAllow = false;

			if (getDatabenificaryservice().equalsIgnoreCase(Constants.TTNAME)) {
				ttCheckSameNotAllow = true;
			} else {
				ttCheckSameNotAllow = false;
			}

			List<PopulateData> lstRoutingBank = iPersonalRemittanceService.getRoutingBankList(session.getCountryId(),
					getBankId(), getBeneficiaryBranchId(),
					//getDatabenificarycountry(),
					getCountryId(),
					getCurrencyId(), getDataserviceid(),
					getRoutingCountry(), ttCheckSameNotAllow);
			if (lstRoutingBank.size() == 0) {
				setBooSingleRoutingBank(true);
				setBooMultipleRoutingBank(false);
				setRoutingBankName(null);
				setRoutingBank(null);
				RequestContext.getCurrentInstance().execute("routingBankNoData.show();");
				return;
			} else if (lstRoutingBank.size() == 1) {
				setBooSingleRoutingBank(true);
				setBooMultipleRoutingBank(false);
				setRoutingBank(lstRoutingBank.get(0).getPopulateId());
				setRoutingBankName(lstRoutingBank.get(0).getPopulateName());
				remittancelistByBankId();
			} else {
				setRoutingBankName(null);
				setRoutingBank(null);
				setBooSingleRoutingBank(false);
				setBooMultipleRoutingBank(true);
				setRoutingbankvalues(lstRoutingBank);
			}

		}
		
	}
	
	
	
	public void getServiceListDetails(){
		List<PopulateData> serviceList = iPersonalRemittanceService.getServiceList(session.getCountryId(),getBankId(), getBeneficiaryBranchId(),getCountryId(),getCurrencyId(),getServiceGroupCode());
		//this.setServiceList(serviceList);

		if(serviceList!=null  && serviceList.size()>0){
			//setBooMultipleService(true);
			//setBooSingleService(false);
			//setRemittanceId(serviceList.get(0).getPopulateId());
			setDataserviceid(serviceList.get(0).getPopulateId());
			countryNameByServiceId();
			//deliverylistByRemitId();
		}else{
			RequestContext.getCurrentInstance().execute("serviceNoData.show();");
			return;
		}
	}
	//based on service selection render fields
	public void countryNameByServiceId(){

		if(dataserviceid !=null){
			setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"), getDataserviceid()).get(0).getLocalServiceDescription());

			// fetch routing country from view
			List<PopulateData> lstRoutingCountry = iPersonalRemittanceService.getRoutingCountryList(session.getCountryId(),getBankId(),getBeneficiaryBranchId(),
					//getDatabenificarycountry(),
					getCountryId(),
					getCurrencyId(),getDataserviceid(),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId"): "1"));

			if (lstRoutingCountry.size() == 0) {
				setBooSingleRoutingCountry(true);
				setBooMultipleRoutingCountry(false);
				setRoutingCountryName(null);
				setRoutingCountry(null);
				RequestContext.getCurrentInstance().execute("routingCountryNoData.show();");
				return;
			} else if (lstRoutingCountry.size() == 1) {
				setBooSingleRoutingCountry(true);
				setBooMultipleRoutingCountry(false);
				setRoutingCountry(lstRoutingCountry.get(0).getPopulateId());
				setRoutingCountryName(lstRoutingCountry.get(0).getPopulateName());
				// setting routing bank Id from view
				bankDetailsByCountry();
			} else {
				setRoutingCountryName(null);
				setRoutingCountry(null);
				setBooSingleRoutingCountry(false);
				setBooMultipleRoutingCountry(true);
				setRoutingCountrylst(lstRoutingCountry);
			}
		}

	}
	//based on country selection render fields
	public void countryNamebyCountryId(){
		for (PopulateData bankname : getRoutingCountrylst()) {
			if (bankname.getPopulateId().compareTo(getRoutingCountry()) == 0) {
				setRoutingCountryName(bankname.getPopulateName());
			}
		}

		bankDetailsByCountry();
	}
	
	//based on bank selection render fields
	public void BankNamebyBankId(){
		for (PopulateData bankname : getRoutingbankvalues()) {
			if (bankname.getPopulateId().compareTo(getRoutingBank()) == 0) {
				setRoutingBankName(bankname.getPopulateName());
			}
		}
		remittancelistByBankId();
	}
	//based on remit selection render Fields
	public void remittanceNamebyremitId(){
		for (PopulateData remitname : getLstofRemittance()) {
			if (remitname.getPopulateId().compareTo(getRemitMode()) == 0) {
				setRemittanceName(remitname.getPopulateName());
			}
		}
		if (getDatabenificaryservice().equalsIgnoreCase(Constants.CASHNAME))
		{
			deliverylistByRemitIdForCash();
		}else
		{
			deliverylistByRemitId();
		}
	}
	
	//based on Delivery selection render Fields
	public void deliveryNamebydeliveryId(){
		for (PopulateData deliveryname : getLstofDelivery()) {
			if (deliveryname.getPopulateId().compareTo(getDeliveryMode()) == 0) {
				setDeliveryModeInput(deliveryname.getPopulateName());
			}
		}
		/*if (!getDatabenificaryservicegroup().equalsIgnoreCase(Constants.CASHNAME))
		{
			bankBranchByBankView();
		}*/

		bankBranchByBankView();
	}
	//based on Routing selection render fields
	public void routingbranchNamebyroutingBankBranchId(){
		for (PopulateData branchname : routingBankBranchlst) {
			if (branchname.getPopulateId().compareTo(getRoutingBranch()) == 0) {
				setRoutingBranchName(branchname.getPopulateName());
			}
		}
	}
	
	public void save(){
		try{
			/*for (GroupSalesManagerApprovalSpecialRateReqDataTable groupSalesMgrObj : lstGroupSalesMgrSpecialRateRequest) {
						//gSMPlaceOrderRateFeedService.approveAllRecords(groupSalesMgrObj.getRateOfferedIdPk(),groupSalesMgrObj.getRateOffered(),groupSalesMgrObj.getTransctionConcluded(),session.getUserName(),new Date());
						if (groupSalesMgrObj.getEmailId() != null) {
							applicationMailer.sendRegistrationMail(groupSalesMgrObj.getEmailId(), "Pin Generation ",session.getUserName());
						}
				}*/
			
			if(getRateOffered()==null)
			{
				RequestContext.getCurrentInstance().execute("rateOffred.show();");
				return;
			}
			if(getCustIndicator()==null)
			{
				RequestContext.getCurrentInstance().execute("custIndicator.show();");
				return;
			}
			
			boolean minMaxrate=checkMinMaxRate();
			if(!minMaxrate)
			{
				return;
			}
			createUniqueApplicationNumber();
			if(getRateOffered() != null && getCustIndicator() != null){
				HashMap<String, String> inputValues=new HashMap<String, String>();
				inputValues.put("EmailId", getEmailId());
				inputValues.put("RateOffered", "Rate Offered");
				inputValues.put("User", session.getUserName());
				inputValues.put("Amount", getAmount()+" - "+getEquivalentCurrency());
				inputValues.put("Created Date", getCreatedDate().toString());
				inputValues.put("RateOfferedMgr", getRateOffered().toString());
				inputValues.put("customerUrl",Constants.CUSTOMER_URL);
				inputValues.put("documentNumber", getDocumentNumber().toString());
				String approveMsg = gSMPlaceOrderRateFeedService.approveAllRecords(getRateOfferedIdPk(),getRateOffered(),getCustIndicator(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),getRemitMode(),getDeliveryMode(),getRoutingBranch(),session.getUserName(),getCustomerUniqueNumber());
				if (approveMsg.equalsIgnoreCase("Success")) {
					RequestContext.getCurrentInstance().execute("approve.show();");
					if (getEmailId() != null && getRateOffered() != null) {
						List<ApplicationSetup> lstApplicationSetup=iPersonalRemittanceService.getEmailFromAppSetup(session.getCompanyId(), session.getCountryId());
						apllicationMailer1.sendMailToCustomerForPlaceOrderApprval(lstApplicationSetup, inputValues);
					}
					return;
				} else {
					RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
					return;
				}
			}else{
				RequestContext.getCurrentInstance().execute("selectIndi.show();");
				return;
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("save::toFetchAllRecords");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}
	public boolean checkMinMaxRate(){
		
		boolean minMaxrate=false;
		try{
			setRateOfferMaxRate(null);
			setRateOfferMaxRate(null);
			BigDecimal raterMinRate=null;
			BigDecimal rateMaxRate=null;

			//List<ExchangeRateApprovalDetModel> lstExchangeRateApprovalDetModel=gSMPlaceOrderRateFeedService.getMinMaxRate(getCountryId(),getBankId(),getCurrencyId());

			//if(lstExchangeRateApprovalDetModel!=null && lstExchangeRateApprovalDetModel.size()>0)
			//{
			//ExchangeRateApprovalDetModel exchangeRateApprovalDetModel=lstExchangeRateApprovalDetModel.get(0);
			//rateMaxRate= exchangeRateApprovalDetModel.getSellRateMax();
			//raterMinRate=exchangeRateApprovalDetModel.getSellRateMin();
			List<CurrencyOtherInformation> lstCurrencyOtherInformation=specialRateService.getMinMaxRate(getCurrencyId());
			if(lstCurrencyOtherInformation!=null && lstCurrencyOtherInformation.size()>0)
			{
				CurrencyOtherInformation currencyOtherInformation=lstCurrencyOtherInformation.get(0);
				rateMaxRate= currencyOtherInformation.getFundMaxRate();
				raterMinRate=currencyOtherInformation.getFundMinRate();
				setRateOfferMinRate(raterMinRate);
				setRateOfferMaxRate(rateMaxRate);
				if(getRateOffered().compareTo(raterMinRate)>=0)
				{
					if(!(getRateOffered().compareTo(rateMaxRate)<=0))
					{
						minMaxrate=false;
						setRateOffered(null);
						RequestContext.getCurrentInstance().execute("minmaxcheck.show();");
						return minMaxrate;
					}
					minMaxrate=true;
				}else
				{
					minMaxrate=false;
					setRateOffered(null);
					RequestContext.getCurrentInstance().execute("minmaxcheck.show();");
					return minMaxrate;
				}
			}else{
				minMaxrate=false;
				setRateOffered(null);
				RequestContext.getCurrentInstance().execute("noSellRate.show();");
				return minMaxrate;
			}
		} catch(NullPointerException  e){
			minMaxrate=false;
			setErrorMessage("Method Name: updateRecords  "+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e){
			minMaxrate=false;
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		return minMaxrate;

	}
	
	//Generate Unique number
	public void createUniqueApplicationNumber() {
		String strToken = null;
		try {
			strToken = getTokenGeneration().getRandomIdentifier(8);
		} catch (Exception e) {
			log.info("Problem in Token Generation" + e);
		}

		boolean tokenConfirm = true;
		while (tokenConfirm) {
			try {
				if (gSMPlaceOrderRateFeedService.CheckUnqiueNumber(strToken).size() > 0) {
					tokenConfirm = true;
					strToken = getTokenGeneration().getRandomIdentifier(8);
				} else {
					tokenConfirm = false;
				}
			} catch (Exception e) {
				tokenConfirm = false;
			}
		}
		setCustomerUniqueNumber(strToken);

	}
	
	public void clickOnOKSave(){
		lstGroupSalesMgrSpecialRateRequest.clear();
		groupSalesMgrSpecialRateReqForPlaceOrder.clear();
		clearAllFields();
		clearForRenderFields();
		setBooRenderDataTable(false);
		setBooRenderSaveOrExit(false);
		setBooRenderClear(false);
		setBooRenderRemitPanel(false);
		toFetchRecordsFormPlaceOrder();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/gSMPlaceOrderRateFeed.xhtml");
		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("clickOnOKSave  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
		}
	}
	/*//##################################Unused Code Started#################################### */	
	//customer reference Validation 
	//AjaxBehaviorEvent event
	public void idOnBlur() {
		  try{
			  List<Customer> listDetails = icustomerRegistrationService.getIntroducerCustId(getCustomerRef());
				if(listDetails!=null && listDetails.size()>0){
					setCustomerId(listDetails.get(0).getCustomerId());
				}else{
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("noRecords.show();");
					setCustomerId(null);
					setCustomerRef(null);
					setBooRenderClear(false);
					setBooRenderDataTable(false);
					setBooRenderSaveOrExit(false);
					clearAllFields();
					clearForRenderFields();
				}
			    }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
	}
	
	//go for customer search Page
	public void searchClicked(){
		try {
			setBooGroupSalesManager(true);
			setIsFromGroupSalesManagerCustomer(true);
			setBooRenderClear(false);
			setBooRenderDataTable(false);
			setBooRenderSaveOrExit(false);
			clearAllFields();
			clearForRenderFields();
			
			HttpSession sessionManage = session.getSession();
			sessionManage.setAttribute("request", "groupSalesManagerApprovalSpecialRateRequest"); 
			
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../search/searchcustomer.xhtml");
			

		} catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
	}
	
	public  HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
    }
	/*//##################################Unused Code Ended#################################### */	
	//location Selection to clear All Fields
	public void toClearSelectLocation(){
		setCustomerRef(null);
		setCustomerId(null);
		clearForRenderFields();
		setBooRenderClear(false);
		setBooRenderDataTable(false);
		setBooRenderSaveOrExit(false);
		
	}
	  //Exit into Home
	  public void exit(){
		    lstGroupSalesMgrSpecialRateRequest.clear();
		    try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} catch (NullPointerException ne) {
				setErrorMessage("Method Name::exit");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}  
	  }

}
