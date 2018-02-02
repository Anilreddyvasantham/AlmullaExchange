package com.amg.exchange.online.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.mail.ApllicationMailer1;
import com.amg.exchange.mail.ApplicationMailer;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.service.ICustomerApprovalPlaceOrderRateFeedService;
import com.amg.exchange.online.service.IGSMPlaceOrderRateFeedService;
import com.amg.exchange.online.service.IPlaceAnOrderCreationService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.service.DeliveryModeService;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IRemittanceModeService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;
@Component("customerApprovalPlaceOrderRateFeedBean")
@Scope("session")
public class CustomerApprovalPlaceOrderRateFeedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(CustomerApprovalPlaceOrderRateFeedBean.class);
	
	//Page level Variables
	private BigDecimal rateOfferedPk;
	private BigDecimal countryId;
	private String countryName;
	private BigDecimal beneficiaryMasterId;
	private String beneficiaryName;
	private BigDecimal bankId;
	private String bankName;
	private BigDecimal currencyId;
	private String currency;
	private BigDecimal beneficiaryAccountId;
	private String beneficiaryAccount;
	private BigDecimal amount;
	private BigDecimal rateOffered;
	private BigDecimal customerId;
	private String customerEmailId;
	private String customerUniqueNumber;
	private BigDecimal countryBranchId;
	private String branchSupport;
	private BigDecimal branchSupportId;
	private String equivalentCurrency;
	private BigDecimal preferedRate;
	private String customerRefAndName;
	private BigDecimal documentNumber;
	private BigDecimal documentFinanceYear;
	private BigDecimal bankAccountSeqId;
	private BigDecimal remitType;
	private BigDecimal customerRef;
	private BigDecimal beneficiaryBranchId;
	
	
	//Routing Details
	private BigDecimal routingCountryId;
	private BigDecimal routingBankId;
	private BigDecimal remitRemittanceId;
	private BigDecimal remitDeliveryId;
	
	//common Variables
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private String errorMessage;
	private Boolean booRenderBranchSupport=false;
	private Boolean booRenderYesOrNo=false;
	private Boolean booRenderLocation=false;
	private Boolean booRenderCustVisitPanel=false;
	private Boolean booRenderBeneficiaryVisitPanel=false;
	private Boolean booRenderBeneficiarysignPanel=false;
	private Boolean booRenderAccountMultiplePanel=false;
	private Boolean booRenderAccountsignPanel=false;
	
	//services
	@Autowired
	ICustomerApprovalPlaceOrderRateFeedService customerApprovalPlaceOrderRateFeedService;
	
	@Autowired 
	IGeneralService<T> generalService;
	
	@Autowired
	IGSMPlaceOrderRateFeedService gSMPlaceOrderRateFeedService;
	
	@Autowired
	ApplicationMailer applicationMailer;
	
	@Autowired
	ApllicationMailer1 mailService;
	
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	
	@Autowired
	IPlaceAnOrderCreationService  iPlaceOnOrderCreationService;
	
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
	
	
	
	private SessionStateManage session= new SessionStateManage();
	private List<CountryBranch> countryBranch = new ArrayList<CountryBranch>();
	private List<CustomerApprovalPlaceOrderRateFeedDataTable> lsApprovalPlaceOrderRateFeedDT=new ArrayList<CustomerApprovalPlaceOrderRateFeedDataTable>();
	private List<PopulateData> beneficiaryList;
	private List<PopulateData> lstAccountNumber;
	
	
	//Page variables getters & setters
	
	public BigDecimal getRateOfferedPk() {
		return rateOfferedPk;
	}
	public void setRateOfferedPk(BigDecimal rateOfferedPk) {
		this.rateOfferedPk = rateOfferedPk;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public BigDecimal getBeneficiaryMasterId() {
		return beneficiaryMasterId;
	}
	public void setBeneficiaryMasterId(BigDecimal beneficiaryMasterId) {
		this.beneficiaryMasterId = beneficiaryMasterId;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getBeneficiaryAccountId() {
		return beneficiaryAccountId;
	}
	public void setBeneficiaryAccountId(BigDecimal beneficiaryAccountId) {
		this.beneficiaryAccountId = beneficiaryAccountId;
	}
	public String getBeneficiaryAccount() {
		return beneficiaryAccount;
	}
	public void setBeneficiaryAccount(String beneficiaryAccount) {
		this.beneficiaryAccount = beneficiaryAccount;
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
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public String getCustomerEmailId() {
		return customerEmailId;
	}
	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}
	public String getCustomerUniqueNumber() {
		return customerUniqueNumber;
	}
	public void setCustomerUniqueNumber(String customerUniqueNumber) {
		this.customerUniqueNumber = customerUniqueNumber;
	}
	public String getEquivalentCurrency() {
		return equivalentCurrency;
	}
	public void setEquivalentCurrency(String equivalentCurrency) {
		this.equivalentCurrency = equivalentCurrency;
	}
	//common variables getters & setters
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
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Boolean getBooRenderBranchSupport() {
		return booRenderBranchSupport;
	}
	public void setBooRenderBranchSupport(Boolean booRenderBranchSupport) {
		this.booRenderBranchSupport = booRenderBranchSupport;
	}
	public Boolean getBooRenderYesOrNo() {
		return booRenderYesOrNo;
	}
	public void setBooRenderYesOrNo(Boolean booRenderYesOrNo) {
		this.booRenderYesOrNo = booRenderYesOrNo;
	}
	public List<CountryBranch> getCountryBranch() {
		return countryBranch;
	}
	public void setCountryBranch(List<CountryBranch> countryBranch) {
		this.countryBranch = countryBranch;
	}
	public Boolean getBooRenderLocation() {
		return booRenderLocation;
	}
	public void setBooRenderLocation(Boolean booRenderLocation) {
		this.booRenderLocation = booRenderLocation;
	}
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	public String getBranchSupport() {
		return branchSupport;
	}
	public void setBranchSupport(String branchSupport) {
		this.branchSupport = branchSupport;
	}
	public BigDecimal getBranchSupportId() {
		return branchSupportId;
	}
	public void setBranchSupportId(BigDecimal branchSupportId) {
		this.branchSupportId = branchSupportId;
	}
	public BigDecimal getPreferedRate() {
		return preferedRate;
	}
	public void setPreferedRate(BigDecimal preferedRate) {
		this.preferedRate = preferedRate;
	}
	
	public Boolean getBooRenderCustVisitPanel() {
		return booRenderCustVisitPanel;
	}
	public void setBooRenderCustVisitPanel(Boolean booRenderCustVisitPanel) {
		this.booRenderCustVisitPanel = booRenderCustVisitPanel;
	}
	
	public List<CustomerApprovalPlaceOrderRateFeedDataTable> getLsApprovalPlaceOrderRateFeedDT() {
		return lsApprovalPlaceOrderRateFeedDT;
	}
	public void setLsApprovalPlaceOrderRateFeedDT(
			List<CustomerApprovalPlaceOrderRateFeedDataTable> lsApprovalPlaceOrderRateFeedDT) {
		this.lsApprovalPlaceOrderRateFeedDT = lsApprovalPlaceOrderRateFeedDT;
	}
	
	public String getCustomerRefAndName() {
		return customerRefAndName;
	}
	public void setCustomerRefAndName(String customerRefAndName) {
		this.customerRefAndName = customerRefAndName;
	}
	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}
	
	public BigDecimal getRoutingCountryId() {
		return routingCountryId;
	}
	public void setRoutingCountryId(BigDecimal routingCountryId) {
		this.routingCountryId = routingCountryId;
	}
	public BigDecimal getRoutingBankId() {
		return routingBankId;
	}
	public void setRoutingBankId(BigDecimal routingBankId) {
		this.routingBankId = routingBankId;
	}
	public BigDecimal getRemitRemittanceId() {
		return remitRemittanceId;
	}
	public void setRemitRemittanceId(BigDecimal remitRemittanceId) {
		this.remitRemittanceId = remitRemittanceId;
	}
	public BigDecimal getRemitDeliveryId() {
		return remitDeliveryId;
	}
	public void setRemitDeliveryId(BigDecimal remitDeliveryId) {
		this.remitDeliveryId = remitDeliveryId;
	}
	
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}
	public Boolean getBooRenderBeneficiaryVisitPanel() {
		return booRenderBeneficiaryVisitPanel;
	}
	public void setBooRenderBeneficiaryVisitPanel(
			Boolean booRenderBeneficiaryVisitPanel) {
		this.booRenderBeneficiaryVisitPanel = booRenderBeneficiaryVisitPanel;
	}
	public List<PopulateData> getBeneficiaryList() {
		return beneficiaryList;
	}
	public void setBeneficiaryList(List<PopulateData> beneficiaryList) {
		this.beneficiaryList = beneficiaryList;
	}
	public Boolean getBooRenderBeneficiarysignPanel() {
		return booRenderBeneficiarysignPanel;
	}
	public void setBooRenderBeneficiarysignPanel(
			Boolean booRenderBeneficiarysignPanel) {
		this.booRenderBeneficiarysignPanel = booRenderBeneficiarysignPanel;
	}
	
	public BigDecimal getBankAccountSeqId() {
		return bankAccountSeqId;
	}
	public void setBankAccountSeqId(BigDecimal bankAccountSeqId) {
		this.bankAccountSeqId = bankAccountSeqId;
	}
	public List<PopulateData> getLstAccountNumber() {
		return lstAccountNumber;
	}
	public void setLstAccountNumber(List<PopulateData> lstAccountNumber) {
		this.lstAccountNumber = lstAccountNumber;
	}
	public BigDecimal getRemitType() {
		return remitType;
	}
	public void setRemitType(BigDecimal remitType) {
		this.remitType = remitType;
	}
	public BigDecimal getCustomerRef() {
		return customerRef;
	}
	public void setCustomerRef(BigDecimal customerRef) {
		this.customerRef = customerRef;
	}
	public Boolean getBooRenderAccountMultiplePanel() {
		return booRenderAccountMultiplePanel;
	}
	public void setBooRenderAccountMultiplePanel(
			Boolean booRenderAccountMultiplePanel) {
		this.booRenderAccountMultiplePanel = booRenderAccountMultiplePanel;
	}
	public Boolean getBooRenderAccountsignPanel() {
		return booRenderAccountsignPanel;
	}
	public void setBooRenderAccountsignPanel(Boolean booRenderAccountsignPanel) {
		this.booRenderAccountsignPanel = booRenderAccountsignPanel;
	}
	public BigDecimal getBeneficiaryBranchId() {
		return beneficiaryBranchId;
	}
	public void setBeneficiaryBranchId(BigDecimal beneficiaryBranchId) {
		this.beneficiaryBranchId = beneficiaryBranchId;
	}
	//Page Navigation
	public void customerApprovalPageNavigation(){
		lsApprovalPlaceOrderRateFeedDT.clear();
		clearAllFields();
		clearRoutingCountry();
		clearAllProcedureVariables();
		setBooRenderCustVisitPanel(false);
		setBooRenderBranchSupport(false);
		setBooRenderYesOrNo(false);
		setBooRenderLocation(false);
		setBooRenderBeneficiaryVisitPanel(false);
		setBooRenderBeneficiarysignPanel(true);
		setBooRenderAccountMultiplePanel(false);
		setBooRenderAccountsignPanel(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/CustomerApprovalPlaceOrderRateFeed.xhtml");
			toFetchRecordsFormCustomerApproval();
		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("customerApprovalPageNavigation  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
		}
	}
	
	
	public void customerApprovalPageNavigationForTemp(){
		lsApprovalPlaceOrderRateFeedDT.clear();
		clearAllFields();
		clearRoutingCountry();
		clearAllProcedureVariables();
		setBooRenderCustVisitPanel(false);
		setBooRenderBranchSupport(false);
		setBooRenderYesOrNo(false);
		setBooRenderLocation(false);
		setBooRenderBeneficiaryVisitPanel(false);
		setBooRenderBeneficiarysignPanel(true);
		setBooRenderAccountMultiplePanel(false);
		setBooRenderAccountsignPanel(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/CustomerApprovalPlaceOrderRateFeedOnline.xhtml");
			toFetchRecordsFormCustomerApproval();
		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("customerApprovalPageNavigation  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
		}
	}
	
	
	//clear All fields
	public void clearAllFields(){
		setRateOfferedPk(null);
		setCountryId(null);
		setCountryName(null);
		setBeneficiaryMasterId(null);
		setBeneficiaryName(null);
		setBankId(null);
		setBankName(null);
		setCurrencyId(null);
		setCurrency(null);
		setBeneficiaryAccountId(null);
		setBeneficiaryAccount(null);
		setAmount(null);
		setRateOffered(null);
		setErrorMessage(null);
		setCustomerEmailId(null);
		setCustomerUniqueNumber(null);
		setCountryBranchId(null);
		setEquivalentCurrency(null);
		setPreferedRate(null);
		setCustomerRefAndName(null);
		setDocumentNumber(null);
		setDocumentFinanceYear(null);
		setBankAccountSeqId(null);
		setRemitType(null);
		setCustomerRef(null);
	}
	
	//Fetch Records From Place Order
		public void toFetchRecordsFormCustomerApproval(){
			lsApprovalPlaceOrderRateFeedDT.clear();
			List<RatePlaceOrder> lstRatePlaceOrderUnApproved=customerApprovalPlaceOrderRateFeedService.fetchAllRecrdsforUnApprovedFromCustomer(session.getCustomerId(),new BigDecimal(session.getBranchId()));
			if(lstRatePlaceOrderUnApproved.size()>0){
				for (RatePlaceOrder ratePlaceOrder : lstRatePlaceOrderUnApproved) {
					CustomerApprovalPlaceOrderRateFeedDataTable customerApprovalDT= new CustomerApprovalPlaceOrderRateFeedDataTable();
					String custerName=null;
					String brachName=null;
					String beneName=null;
					String benefiBankName=null;
					String remittanceType=null;
					String curQtyName=null;
					String ctryName=null;
					BigDecimal custrRef=null;
					/*Dispaly Data table Variables started*/
					//CustomerId and Name
					customerApprovalDT.setCustomerId(ratePlaceOrder.getCustomerId());
					//custerName=generalService.getCustomerNameBasedOnCustomerId(ratePlaceOrder.getCustomerId());
					custerName=generalService.getCustomerNameCustomerId(ratePlaceOrder.getCustomerId());
					customerApprovalDT.setCustomerName(custerName);
					customerApprovalDT.setCustomerRefNo(ratePlaceOrder.getCustomerreference());
					customerApprovalDT.setCustomerRefAndName(ratePlaceOrder.getCustomerreference()+ "-" +custerName);
					//CountryId and Name
					customerApprovalDT.setCountryId(ratePlaceOrder.getBeneficiaryCountryId());
					ctryName=generalService.getCountryName(session.getLanguageId(), ratePlaceOrder.getBeneficiaryCountryId());
					customerApprovalDT.setCountryName(ctryName);
					//BranchId and Name
					customerApprovalDT.setBranchId(ratePlaceOrder.getCountryBranchId());
					brachName=gSMPlaceOrderRateFeedService.toFetchBranchName(ratePlaceOrder.getCountryBranchId());
					customerApprovalDT.setBranch(brachName);
					//beneficiary Name
					if(ratePlaceOrder.getBeneficiaryMasterId()!=null)
					{
						customerApprovalDT.setBeneficiaryId(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
						beneName=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
						customerApprovalDT.setBeneficiaryName(beneName);
					}
					
					//bank Name
					customerApprovalDT.setBankId(ratePlaceOrder.getBeneficiaryBankId());
					benefiBankName=generalService.getBankName(ratePlaceOrder.getBeneficiaryBankId());
					customerApprovalDT.setBankName(benefiBankName);
					//Remit Type
					customerApprovalDT.setRemittanceId(ratePlaceOrder.getRemitType());
					remittanceType=gSMPlaceOrderRateFeedService.toFetchServiceGroupDesc(session.getLanguageId(),ratePlaceOrder.getRemitType());
					customerApprovalDT.setRemittanceName(remittanceType);
					//currency
					customerApprovalDT.setCurrencyId(ratePlaceOrder.getDestinationCurrenyId());
					customerApprovalDT.setCurrency(generalService.getCurrencyName(ratePlaceOrder.getDestinationCurrenyId()));
					/*Dispaly Data table Variables Endec*/
					//all  variables
					customerApprovalDT.setRateOfferedIdPk(ratePlaceOrder.getRatePlaceOrderId());
					customerApprovalDT.setCreatedBy(ratePlaceOrder.getCreatedBy());
					customerApprovalDT.setCreatedDate(ratePlaceOrder.getCreatedDate());
					customerApprovalDT.setIsActive(ratePlaceOrder.getIsActive());
					customerApprovalDT.setModifiedBy(ratePlaceOrder.getModifiedBy());
					customerApprovalDT.setModifiedDate(ratePlaceOrder.getModifiedDate());
					customerApprovalDT.setApprovedBy(ratePlaceOrder.getApprovedBy());
					customerApprovalDT.setApprovedDate(ratePlaceOrder.getApprovedDate());
					customerApprovalDT.setTransctionConcluded(ratePlaceOrder.getTransactionConcluded());
					customerApprovalDT.setRateOffered(ratePlaceOrder.getRateOffered());
					curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(ratePlaceOrder.getDestinationCurrenyId());
					customerApprovalDT.setEquivalentCurrencyDt("["+curQtyName+"]");
					customerApprovalDT.setAmount(ratePlaceOrder.getTransactionAmount());
					customerApprovalDT.setAmountAndQtyName(ratePlaceOrder.getTransactionAmount()+ "-" +curQtyName);
					customerApprovalDT.setAppDocumentNumber(ratePlaceOrder.getDocumentNumber());
					customerApprovalDT.setAppFinaceYear(ratePlaceOrder.getDocumentFinanceYear());
					lsApprovalPlaceOrderRateFeedDT.add(customerApprovalDT);
					clearAllFields();
					clearRoutingCountry();
					setBooRenderCustVisitPanel(false);
					setBooRenderYesOrNo(false);
					setBooRenderBranchSupport(false);
					setBooRenderLocation(false);
					
				}
			}
		}
		
		public void clearRoutingCountry(){
			setRoutingCountryId(null);
			setRoutingBankId(null);
			setRemitRemittanceId(null);
			setRemitDeliveryId(null);
		}
	
	//to Fetch Records From DB
	public void toFetchAllRecordsForSpecialRate(CustomerApprovalPlaceOrderRateFeedDataTable dataTable){
		try {
			setCustomerRefAndName(null);
			setDocumentNumber(null);
			List<RatePlaceOrder> lstRatePlaceOrders=customerApprovalPlaceOrderRateFeedService.toFetchRecordsBasedOnCustomerId(dataTable.getCustomerId(),dataTable.getAppDocumentNumber(),dataTable.getAppFinaceYear());
			if(lstRatePlaceOrders.size()>0){
				//local Varibles
				String beneficiaryCountryName=null;
				String beneName=null;
				String benefiBankName=null;
				String currName=null;
				String accountNum=null;
				String currencyQtyName=null;
				String custerName=null;
				setRateOfferedPk(lstRatePlaceOrders.get(0).getRatePlaceOrderId());
				setCountryId(lstRatePlaceOrders.get(0).getBeneficiaryCountryId());
				if(lstRatePlaceOrders.get(0).getBeneficiaryCountryId() != null){
					beneficiaryCountryName=generalService.getCountryName(session.getLanguageId(), lstRatePlaceOrders.get(0).getBeneficiaryCountryId());
					setCountryName(beneficiaryCountryName);
				}
				if(lstRatePlaceOrders.get(0).getBeneficiaryMasterId()!=null)
				{
					setBooRenderBeneficiarysignPanel(true);
					setBooRenderBeneficiaryVisitPanel(false);
					setBeneficiaryMasterId(lstRatePlaceOrders.get(0).getBeneficiaryMasterId().getBeneficaryMasterSeqId());
					beneName=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(lstRatePlaceOrders.get(0).getBeneficiaryMasterId().getBeneficaryMasterSeqId());
					setBeneficiaryName(beneName);
				}else{
						setBeneficiaryName(null);
						setBooRenderBeneficiaryVisitPanel(true);
						setBooRenderBeneficiarysignPanel(false);
						List<PopulateData> lstBeneName=iPlaceOnOrderCreationService.getBeneficiarNameList(dataTable.getCustomerId(), dataTable.getCountryId(), dataTable.getRemittanceId(),dataTable.getCustomerRefNo());
						if(lstBeneName!=null && lstBeneName.size()>0)
						{
							setBeneficiaryList(lstBeneName);
						}
				}		
				
				
				setBankId(lstRatePlaceOrders.get(0).getBeneficiaryBankId());
				if(lstRatePlaceOrders.get(0).getBeneficiaryBankId() != null){
					benefiBankName=generalService.getBankName(lstRatePlaceOrders.get(0).getBeneficiaryBankId());
					setBankName(benefiBankName);
				}
				if(lstRatePlaceOrders.get(0).getDestinationCurrenyId() != null){
					
					List<PopulateData> lstofCurrency = new ArrayList<PopulateData>();

					lstofCurrency= iPlaceOnOrderCreationService.getBasedOnCountyCurrency(lstRatePlaceOrders.get(0).getBeneficiaryCountryId());
					if(lstofCurrency!=null && lstofCurrency.size()>0)
					{
						setCurrencyId(lstofCurrency.get(0).getPopulateId());
						currName=generalService.getCurrencyName(lstRatePlaceOrders.get(0).getDestinationCurrenyId());
						setCurrency(currName);
						currencyQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(lstRatePlaceOrders.get(0).getDestinationCurrenyId());
						setEquivalentCurrency("["+currencyQtyName+"]");
					}
					
				}
				if(lstRatePlaceOrders.get(0).getAccountSeqquenceId() != null){
					setBooRenderAccountMultiplePanel(false);
					setBooRenderAccountsignPanel(true);
					setBeneficiaryAccountId(lstRatePlaceOrders.get(0).getAccountSeqquenceId().getBeneficaryAccountSeqId());
					//accountNum=customerApprovalPlaceOrderRateFeedService.toFetchAccountNumber(lstRatePlaceOrders.get(0).getAccountSeqquenceId().getBeneficaryAccountSeqId());
					setBeneficiaryAccount(lstRatePlaceOrders.get(0).getBeneficiaryAccountNo());
				}else{
					setBooRenderAccountMultiplePanel(true);
					setBooRenderAccountsignPanel(false);
					setBeneficiaryAccount(null);
				}
				setAmount(lstRatePlaceOrders.get(0).getTransactionAmount());
				setRateOffered(lstRatePlaceOrders.get(0).getRateOffered());
				setCreatedBy(lstRatePlaceOrders.get(0).getCreatedBy());
				setCreatedDate(lstRatePlaceOrders.get(0).getCreatedDate());
				setApprovedBy(lstRatePlaceOrders.get(0).getApprovedBy());
				setApprovedDate(lstRatePlaceOrders.get(0).getApprovedDate());
				setIsActive(lstRatePlaceOrders.get(0).getIsActive());
				setCustomerEmailId(lstRatePlaceOrders.get(0).getCustomerEmail());
				setCustomerUniqueNumber(lstRatePlaceOrders.get(0).getCustomerUnqiueNumber());
				setCustomerId(lstRatePlaceOrders.get(0).getCustomerId());
				//custerName=generalService.getCustomerNameBasedOnCustomerId(ratePlaceOrder.getCustomerId());
				custerName=generalService.getCustomerNameCustomerId(lstRatePlaceOrders.get(0).getCustomerId());
				setCustomerName(custerName);
				setCustomerRef(lstRatePlaceOrders.get(0).getCustomerreference());
				if(lstRatePlaceOrders.get(0).getCustomerreference() != null){
				setCustomerRefAndName(lstRatePlaceOrders.get(0).getCustomerreference()+ "-" +custerName);
				}
				setRemitType(lstRatePlaceOrders.get(0).getRemitType());
				if(lstRatePlaceOrders.get(0).getRemitType() != null){
				String remittanceType=gSMPlaceOrderRateFeedService.toFetchServiceGroupDesc(session.getLanguageId(),lstRatePlaceOrders.get(0).getRemitType());
				setRemittanceName(remittanceType);
				}
				setDocumentNumber(lstRatePlaceOrders.get(0).getDocumentNumber());
				setDocumentFinanceYear(lstRatePlaceOrders.get(0).getDocumentFinanceYear());
				setRoutingCountryId(lstRatePlaceOrders.get(0).getRoutingCountryId());
				setRoutingBankId(lstRatePlaceOrders.get(0).getRoutingBankId());
				setRemitRemittanceId(lstRatePlaceOrders.get(0).getRemittanceModeId());
				setRemitDeliveryId(lstRatePlaceOrders.get(0).getDeliveryModeId());
				setBooRenderCustVisitPanel(true);
				setBooRenderYesOrNo(true);
			}else{
				RequestContext.getCurrentInstance().execute("noRecords.show();");
				return;
			}
			/*BigDecimal customerIdForIdentity=null;
			List<CustomerIdProof> lstCustomerIdProofs=generalService.getCustomerIdBasedOnCivilId("245304942131");
			if(lstCustomerIdProofs.size()>0){
				customerIdForIdentity=lstCustomerIdProofs.get(0).getFsCustomer().getCustomerId();
			}
			List<RatePlaceOrder> lstRatePlaceOrders=customerApprovalPlaceOrderRateFeedService.toFetchRecordsBasedOnCustomerId(new BigDecimal(2581));session.getCustomerId()
			if(lstRatePlaceOrders.size()>0){
				//local Varibles
				String beneficiaryCountryName=null;
				String beneName=null;
				String benefiBankName=null;
				String currName=null;
				String accountNum=null;
				String currencyQtyName=null;
				
				setCustomerId(lstRatePlaceOrders.get(0).getCustomerId());
				setRateOfferedPk(lstRatePlaceOrders.get(0).getRatePlaceOrderId());
				if(lstRatePlaceOrders.get(0).getBeneficiaryCountryId() != null){
					setCountryId(lstRatePlaceOrders.get(0).getBeneficiaryCountryId());
					beneficiaryCountryName=generalService.getCountryName(session.getLanguageId(), lstRatePlaceOrders.get(0).getBeneficiaryCountryId());
					setCountryName(beneficiaryCountryName);
				}
				if(lstRatePlaceOrders.get(0).getBeneficiaryMasterId().getBeneficaryMasterSeqId() != null){
					setBeneficiaryMasterId(lstRatePlaceOrders.get(0).getBeneficiaryMasterId().getBeneficaryMasterSeqId());
					beneName=gSMPlaceOrderRateFeedService.toFetchBeneficiaryName(lstRatePlaceOrders.get(0).getBeneficiaryMasterId().getBeneficaryMasterSeqId());
					setBeneficiaryName(beneName);
				}
				if(lstRatePlaceOrders.get(0).getBeneficiaryBankId() != null){
					setBankId(lstRatePlaceOrders.get(0).getBeneficiaryBankId());
					benefiBankName=generalService.getBankName(lstRatePlaceOrders.get(0).getBeneficiaryBankId());
					setBankName(benefiBankName);
				}
				if(lstRatePlaceOrders.get(0).getCurrencyId() != null){
					setCurrencyId(lstRatePlaceOrders.get(0).getCurrencyId());
					currName=generalService.getCurrencyName(lstRatePlaceOrders.get(0).getCurrencyId());
					setCurrency(currName);
					currencyQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(lstRatePlaceOrders.get(0).getCurrencyId());
					setEquivalentCurrency("["+currencyQtyName+"]");
				}
				if(lstRatePlaceOrders.get(0).getAccountSeqquenceId().getBeneficaryAccountSeqId() != null){
					setBeneficiaryAccountId(lstRatePlaceOrders.get(0).getAccountSeqquenceId().getBeneficaryAccountSeqId());
					accountNum=customerApprovalPlaceOrderRateFeedService.toFetchAccountNumber(lstRatePlaceOrders.get(0).getAccountSeqquenceId().getBeneficaryAccountSeqId());
					setBeneficiaryAccount(accountNum);
				}
				setAmount(lstRatePlaceOrders.get(0).getTransactionAmount());
				setRateOffered(lstRatePlaceOrders.get(0).getRateOffered());
				setCreatedBy(lstRatePlaceOrders.get(0).getCreatedBy());
				setCreatedDate(lstRatePlaceOrders.get(0).getCreatedDate());
				setApprovedBy(lstRatePlaceOrders.get(0).getApprovedBy());
				setApprovedDate(lstRatePlaceOrders.get(0).getApprovedDate());
				setIsActive(lstRatePlaceOrders.get(0).getIsActive());
				setCustomerEmailId(lstRatePlaceOrders.get(0).getCustomerEmail());
				setCustomerUniqueNumber(lstRatePlaceOrders.get(0).getCustomerUnqiueNumber());
			}*/
			} catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::toFetchAllRecordsForSpecialRate");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
			}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
			}
	}
	
	public void remitTransction(){
		/*try {
		customerApprovalPlaceOrderRateFeedService.toActivateRecord(getRateOfferedPk(),session.getUserName());
		HttpSession sessionManage = session.getSession();
		sessionManage.setAttribute("request", "CUSTOMER_URL"); 
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect(Constants.CUSTOMER_URL);
		} catch (IOException exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		}*/
		setBooRenderBranchSupport(true);
		setBooRenderYesOrNo(false);
		setBooRenderLocation(false);
		BigDecimal beneMasterId=customerApprovalPlaceOrderRateFeedService.getBeneficiarList(getBeneficiaryName(),getCountryId(),getRemitType(),getCustomerRef(),getCustomerId());
		customerApprovalPlaceOrderRateFeedService.toActivateRecord(getRateOfferedPk(),beneMasterId,getBeneficiaryAccountId(),getBeneficiaryAccount(),session.getUserName());
		/*HashMap<String, String> inputValues=new HashMap<String, String>();
		inputValues.put("emailId", getCustomerEmailId());
		inputValues.put("customerUniqueNumber", getCustomerUniqueNumber());
		inputValues.put("User", session.getUserName());
		applicationMailer.sendRateOfferedBranchSupportTrnx(inputValues);*/
		//RequestContext.getCurrentInstance().execute("timeOut.show();");
		//clearAllFields();
	}
	
	public void branchSupportTrnx(){
		//setBooRenderYesOrNo(false);
		//RequestContext.getCurrentInstance().execute("complete.show();");
		//clearAllFields();
		setBooRenderBranchSupport(false);
		setBooRenderYesOrNo(false);
		setBooRenderLocation(true);
		countryBranch.clear();
		List<CountryBranch> lstCountryBranchs=generalService.getBranchDetails(session.getCountryId());
		if(lstCountryBranchs.size()>0){
			countryBranch.addAll(lstCountryBranchs);
		}
	}
	
	public void save() {
		try{
		if(getCountryBranchId() != null){
		String emailIdForBranch=null;
		emailIdForBranch=customerApprovalPlaceOrderRateFeedService.toFetchEmailForBranchMgr(getCountryBranchId());
		System.out.println("emailIdForBranch::::::::::::::::::::::::::::::::::::::"+emailIdForBranch);
		customerApprovalPlaceOrderRateFeedService.toActivateRecordForBranchSupport(getRateOfferedPk(),getCountryBranchId(),session.getUserName());
		HashMap<String, String> inputValues=new HashMap<String, String>();
		inputValues.put("emailId", emailIdForBranch);
		inputValues.put("customerUniqueNumber", getCustomerUniqueNumber());
		inputValues.put("User", session.getUserName());
		RequestContext.getCurrentInstance().execute("complete.show();");
		List<ApplicationSetup> lstApplicationSetup=iPersonalRemittanceService.getEmailFromAppSetup(session.getCompanyId(), session.getCountryId());
		mailService.sendRateOfferedMailBranchSupportTrnx(lstApplicationSetup,inputValues);
		//existing mail 
		//applicationMailer.sendRateOfferedBranchSupportTrnx(inputValues);
		
		clearAllFields();
		}else{
			RequestContext.getCurrentInstance().execute("selectBranch.show();");
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
	
	public void clickOnOKSave(){
		lsApprovalPlaceOrderRateFeedDT.clear();
		setBooRenderYesOrNo(false);
		setBooRenderBranchSupport(false);
		setBooRenderLocation(false);
		setBooRenderCustVisitPanel(false);
		toFetchRecordsFormCustomerApproval();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/CustomerApprovalPlaceOrderRateFeed.xhtml");
		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("clickOnOKSave  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
		}
	}
	
	public void clickOnOKSaveOnline(){
		lsApprovalPlaceOrderRateFeedDT.clear();
		setBooRenderYesOrNo(false);
		setBooRenderBranchSupport(false);
		setBooRenderLocation(false);
		setBooRenderCustVisitPanel(false);
		toFetchRecordsFormCustomerApproval();
		setBooRenderBeneficiarysignPanel(true);
		setBooRenderBeneficiaryVisitPanel(false);
		setBooRenderAccountMultiplePanel(false);
		setBooRenderAccountsignPanel(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/CustomerApprovalPlaceOrderRateFeedOnline.xhtml");
		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("clickOnOKSave  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
		}
	}
	
	//Exit into Home
	  public void exit(){
		  lsApprovalPlaceOrderRateFeedDT.clear();
		  clearAllFields();
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
	
	public  HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
    }
	
	public void cancelRemitTrnx(){
		customerApprovalPlaceOrderRateFeedService.toDeActivateRecord(getRateOfferedPk(),session.getUserName());
		//clearAllFields();
		RequestContext.getCurrentInstance().execute("rejectRate.show();");
		return;
	}
	
	public static BigDecimal round(BigDecimal bd, int places) {
		if (places < 0) throw new IllegalArgumentException();

		bd = bd.setScale(places, RoundingMode.UP);
		return bd;
	}
	
	public void clickOkRejectRate(){
		BigDecimal acceptRate=null;
		acceptRate=round(new BigDecimal(1).divide(getRateOffered(),BigDecimal.ROUND_UP).multiply(new BigDecimal(0.1)), BigDecimal.ROUND_UP).add(new BigDecimal(1).divide(getRateOffered(),BigDecimal.ROUND_UP));
		//acceptRate=(1/getRateOffered().intValue()*(0.1)).add(new BigDecimal(1).divide(getRateOffered()));
		if(acceptRate.compareTo(getPreferedRate())==0){
			/*OnlineRateAlert onlineRateAlert=new OnlineRateAlert();
			onlineRateAlert.setCivilId(new BigDecimal(2453));
			//Custimer Id
			Customer customer=new Customer();
			customer.setCustomerId(getCustomerId());
			onlineRateAlert.setCustomerId(customer);
			//BeneficaryMaster
			BeneficaryMaster beneficaryMaster=new BeneficaryMaster();
			beneficaryMaster.setBeneficaryMasterSeqId(getBeneficiaryMasterSeqId());
			onlineRateAlert.setBeneficaryId(beneficaryMaster);
			//CurrencyMaster
			CurrencyMaster currencyMaster=new CurrencyMaster();
			currencyMaster.setCurrencyId(new BigDecimal(session.getCurrencyId()));
			onlineRateAlert.setBaseCurrencyId(currencyMaster);
			//CurrencyCode
			String currencyCode=null;
			currencyCode=generalService.getCurrencyCode(new BigDecimal(session.getCurrencyId()));
			onlineRateAlert.setBaseCurrencyCode(currencyCode);
			//Foreign CurrencyMaster
			CurrencyMaster foreignCurrencyMaster=new CurrencyMaster();
			foreignCurrencyMaster.setCurrencyId(getCurrencyId());
			onlineRateAlert.setForeignCurrencyId(foreignCurrencyMaster);
			//foreignCurrencyCode
			String foreCurrencyCode=null;
			foreCurrencyCode=generalService.getCurrencyCode(getCurrencyId());
			onlineRateAlert.setForeignCurrencyCode(foreCurrencyCode);
			//ExchangeRate
			ExchangeRate exchangeRate=new ExchangeRate();
			exchangeRate.setExchangeRateMasterId(getPreferedRate());
			onlineRateAlert.setExchangeRateId(exchangeRate);*/
			RequestContext.getCurrentInstance().execute("complete.show();");
			clearAllFields();
		}else if (acceptRate.compareTo(getPreferedRate())==1) {
			/*OnlineRateAlert onlineRateAlert=new OnlineRateAlert();*/
			RequestContext.getCurrentInstance().execute("complete.show();");
			clearAllFields();
		}else{
			RequestContext.getCurrentInstance().execute("preferdRateExced.show();");
			setPreferedRate(null);
			return;
		}
		
		
		
	}
	
	public void cancelRejectRate(){
		setPreferedRate(null);
		return;
	}
	
	//personal Remittance variables
	private String customerName;
	private String customerNameLocal;
	private String customerMobileNumber;
	private String perBeneficiaryName;
	private String beneficiaryTelephoneNo;
	private String beneficiaryCountry;
	private String beneficiaryBank;
	private String beneficiaryBranch;
	private BigDecimal beneServiceId;
	private String beneServiceName;
	/*private String routingCountry;
	private String routingBank;
	private String routingBranch;*/
	private BigDecimal routingRemitModeId;
	private String routingRemitName;
	private BigDecimal routingDelivaryId;
	private String routingDeliveryName;
	//private BigDecimal beneficiaryMasterSeqId;
	private BigDecimal beneficiaryAccountSeqId;

	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerNameLocal() {
		return customerNameLocal;
	}
	public void setCustomerNameLocal(String customerNameLocal) {
		this.customerNameLocal = customerNameLocal;
	}
	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}
	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}
	public String getPerBeneficiaryName() {
		return perBeneficiaryName;
	}
	public void setPerBeneficiaryName(String perBeneficiaryName) {
		this.perBeneficiaryName = perBeneficiaryName;
	}
	public String getBeneficiaryTelephoneNo() {
		return beneficiaryTelephoneNo;
	}
	public void setBeneficiaryTelephoneNo(String beneficiaryTelephoneNo) {
		this.beneficiaryTelephoneNo = beneficiaryTelephoneNo;
	}
	public String getBeneficiaryCountry() {
		return beneficiaryCountry;
	}
	public void setBeneficiaryCountry(String beneficiaryCountry) {
		this.beneficiaryCountry = beneficiaryCountry;
	}
	public String getBeneficiaryBank() {
		return beneficiaryBank;
	}
	public void setBeneficiaryBank(String beneficiaryBank) {
		this.beneficiaryBank = beneficiaryBank;
	}
	public String getBeneficiaryBranch() {
		return beneficiaryBranch;
	}
	public void setBeneficiaryBranch(String beneficiaryBranch) {
		this.beneficiaryBranch = beneficiaryBranch;
	}
	public BigDecimal getBeneServiceId() {
		return beneServiceId;
	}
	public void setBeneServiceId(BigDecimal beneServiceId) {
		this.beneServiceId = beneServiceId;
	}
	public String getBeneServiceName() {
		return beneServiceName;
	}
	public void setBeneServiceName(String beneServiceName) {
		this.beneServiceName = beneServiceName;
	}
	/*public String getRoutingCountry() {
		return routingCountry;
	}
	public void setRoutingCountry(String routingCountry) {
		this.routingCountry = routingCountry;
	}
	public String getRoutingBank() {
		return routingBank;
	}
	public void setRoutingBank(String routingBank) {
		this.routingBank = routingBank;
	}
	public String getRoutingBranch() {
		return routingBranch;
	}
	public void setRoutingBranch(String routingBranch) {
		this.routingBranch = routingBranch;
	}*/
	public BigDecimal getRoutingRemitModeId() {
		return routingRemitModeId;
	}
	public void setRoutingRemitModeId(BigDecimal routingRemitModeId) {
		this.routingRemitModeId = routingRemitModeId;
	}
	public String getRoutingRemitName() {
		return routingRemitName;
	}
	public void setRoutingRemitName(String routingRemitName) {
		this.routingRemitName = routingRemitName;
	}
	public BigDecimal getRoutingDelivaryId() {
		return routingDelivaryId;
	}
	public void setRoutingDelivaryId(BigDecimal routingDelivaryId) {
		this.routingDelivaryId = routingDelivaryId;
	}
	public String getRoutingDeliveryName() {
		return routingDeliveryName;
	}
	public void setRoutingDeliveryName(String routingDeliveryName) {
		this.routingDeliveryName = routingDeliveryName;
	}
	
	/*public BigDecimal getBeneficiaryMasterSeqId() {
		return beneficiaryMasterSeqId;
	}
	public void setBeneficiaryMasterSeqId(BigDecimal beneficiaryMasterSeqId) {
		this.beneficiaryMasterSeqId = beneficiaryMasterSeqId;
	}*/
	public BigDecimal getBeneficiaryAccountSeqId() {
		return beneficiaryAccountSeqId;
	}
	public void setBeneficiaryAccountSeqId(BigDecimal beneficiaryAccountSeqId) {
		this.beneficiaryAccountSeqId = beneficiaryAccountSeqId;
	}
	
	public void clearRoutingAllDetails(){
		setCustomerName(null);
		setCustomerNameLocal(null);
		setCustomerMobileNumber(null);
		setPerBeneficiaryName(null);
		setBeneficiaryTelephoneNo(null);
		setBeneficiaryCountry(null);
		setBeneficiaryBank(null);
		setBeneficiaryBranch(null);
		setBeneServiceId(null);
		setBeneServiceName(null);
		setRoutingCountry(null);
		setRoutingBank(null);
		setRoutingRemitModeId(null);
		setRoutingRemitName(null);
		setRoutingDelivaryId(null);
		setRoutingDeliveryName(null);
		setRoutingBranch(null);
		setBeneficiaryMasterId(null);
		setBeneficiaryAccountSeqId(null);
	}
	
	@Autowired
	CustomerOnlineTransctionBean customerOnlineTransctionBean;
	//Payonline customer
	public void payOnline() throws AMGException{
		if(getBeneficiaryMasterId() == null && getBeneficiaryName() == null || getBeneficiaryAccount() == null || getBeneficiaryAccountId() == null){
			RequestContext.getCurrentInstance().execute("selectBeneAndAccount.show();");
			return;
		}
		BigDecimal beneMasterId=customerApprovalPlaceOrderRateFeedService.getBeneficiarList(getBeneficiaryName(),getCountryId(),getRemitType(),getCustomerRef(),getCustomerId());
		customerApprovalPlaceOrderRateFeedService.toActivateRecord(getRateOfferedPk(),beneMasterId,getBeneficiaryAccountId(),getBeneficiaryAccount(),session.getUserName());
		toFetchRoutingDetailsFromRemitProcedure();
		customerOnlineTransctionBean.setBeneficiaryCountryId(getCountryName());
		customerOnlineTransctionBean.setCustomerId(getCustomerId());
		customerOnlineTransctionBean.setBeneficiaryBankId(getBankName());
		customerOnlineTransctionBean.setBeneficiaryName(getBeneficiaryName());
		customerOnlineTransctionBean.setBeneficiaryMasterSeqId(getBeneficiaryMasterId());
		customerOnlineTransctionBean.setBeneficiaryCurrencyId(getCurrency());
		customerOnlineTransctionBean.setCurrencyId(getCurrencyId());
		customerOnlineTransctionBean.setTranxAmount(getAmount());
		customerOnlineTransctionBean.setSpecialRateOffer(getRateOffered());
		customerOnlineTransctionBean.setRateOfferedPk(getRateOfferedPk());
		customerOnlineTransctionBean.setDocumentNumber(getDocumentNumber());
		customerOnlineTransctionBean.setDocumentFinanceYear(getDocumentFinanceYear());
		customerOnlineTransctionBean.setRoutingCountry(getRoutingCountry());
		customerOnlineTransctionBean.setRoutingBank(getRoutingBankId());
		customerOnlineTransctionBean.setDataserviceid(getDataserviceid());
		customerOnlineTransctionBean.setBeneAccountSeqId(getBeneficiaryAccountId());
		customerOnlineTransctionBean.setBeneAccountNumber(getBeneficiaryAccount());
		customerOnlineTransctionBean.setRemitRemittanceId(getRemitMode());
		customerOnlineTransctionBean.setRemitDeliveryId(getDeliveryMode());
		customerOnlineTransctionBean.setRoutingBranch(getRoutingBranch());
		customerOnlineTransctionBean.getSourceofIncomeDetails();
		customerOnlineTransctionBean.dynamicLevel();
		customerOnlineTransctionBean.matchData();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/CustomerOnlineTransction.xhtml");
		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("clickOnOKSave  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
		}
		
	}	
	
	public void exitOnline() {

		try {
			clearAllFields();
			// setBooRenderExitButtonOnline(false);
			// setBooRenderExitButtonBranch(false);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(session.getMenuPage());
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.error("clickOnOKSave  :" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
		}
	}
	
	//based on bene name we can select Account
	public void populateAccountNumber(){
		try{
			setBeneficiaryAccount(null);
			setBeneficiaryAccountId(null);
			setLstAccountNumber(null);
			setBeneficiaryMasterId(null);
			if(getBeneficiaryName()!=null){

				//setCurrencyQuoteName(null);

				List<PopulateData> accountNumberList= iPlaceOnOrderCreationService.getBeneAccountNumber(getBeneficiaryName(),getCountryId(),getBankId(),getRemitType(),getCustomerRef());
				if(accountNumberList!=null && accountNumberList.size()==0  )
				{
					setBeneficiaryName(null);
					setBeneficiaryMasterId(null);
					//setRoutingDetailsShow(false);
					RequestContext.getCurrentInstance().execute("accountNo.show();");
					return;
				}
				log.info( "============bank accountnumList======="+accountNumberList.size());
				HashMap<BigDecimal,String> beneAccNoMap = new HashMap<BigDecimal,String> ();
				for(PopulateData populateData :accountNumberList)
				{
					beneAccNoMap.put(populateData.getPopulateId(), populateData.getPopulateCode());
				}
				//setBeneAccNoMap(beneAccNoMap);
				if(accountNumberList!=null && accountNumberList.size()==1)
				{
					setBooRenderAccountMultiplePanel(false);
					setBooRenderAccountsignPanel(true);
					setBeneficiaryAccount(accountNumberList.get( 0).getPopulateCode());
					setBeneficiaryAccountId(accountNumberList.get(0).getPopulateId());

					HashMap<String, BigDecimal> rtnMap=iPlaceOnOrderCreationService.getBeneMasterIdCurrencyId(getBeneficiaryName(),getCountryId(),getBankId(),getRemitType(),getCustomerRef(),getBeneficiaryAccount());

					BigDecimal beneficaryMasterSeqId =  rtnMap.get("BeneficaryMasterSeqId");
					setBeneficiaryMasterId(beneficaryMasterSeqId);
					/*	String currencyQuoteName =iPlaceOnOrderCreationService.toFetchCurrencyQtyName(beneficaryMasterSeqId,getAccountSequenceId(),getCustomerRef());
					if(currencyQuoteName!=null)
					{
						setCurrencyQuoteName("["+currencyQuoteName+"]");
					}*/



				}else if(accountNumberList!=null && accountNumberList.size()>1){
					setBooRenderAccountMultiplePanel(true);
					setBooRenderAccountsignPanel(false);
					setLstAccountNumber(accountNumberList);
					/*log.info( "===========bank account number======"+accountNumberList.get( 0).getBankAccountNumber());
						setAccountNumber(accountNumberList.get( 0).getBankAccountNumber() );
						setAccountSequenceId(accountNumberList.get(0).getBeneficiaryAccountSeqId() );
						setCurrencyId(accountNumberList.get(0).getCurrencyId());*/
				}

				
				//setRoutingDetailsShow(true);
				//routingsetupDetails(getAcceptPlaceOrderDataTable());

			}else
			{
				//clearRoutingSetup();
			}

		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	//to call remit procedure details
	public void toFetchRoutingDetailsFromRemitProcedure() throws AMGException{
		BigDecimal BeneBranchId=gSMPlaceOrderRateFeedService.getBeneBranchId(getBankId(),getBeneficiaryMasterId(),getBeneficiaryName());
		setBeneficiaryBranchId(BeneBranchId);
		//toset routing Bank
		//procedure calling started 
		
		HashMap<String, String> inputRoutingBankSetUpdetails = new HashMap<String, String>();

		inputRoutingBankSetUpdetails.put("P_APPLICATION_COUNTRY_ID", session.getCountryId().toPlainString());
		inputRoutingBankSetUpdetails.put("P_BENE_COUNTRY_ID", getCountryId().toPlainString()); // beneficiary bank Country Id
		inputRoutingBankSetUpdetails.put("P_BENE_BANK_ID", getBankId().toPlainString());
		inputRoutingBankSetUpdetails.put("P_BENE_BANK_BRANCH_ID",getBeneficiaryBranchId().toPlainString());

		List<ServiceGroupMasterDesc> lstServiceCode = iServiceGroupMasterService.getServiceGroupDescList(getRemitType());
		if (lstServiceCode != null) {
			ServiceGroupMasterDesc ServiceCode = lstServiceCode.get(0);
			inputRoutingBankSetUpdetails.put("P_SERVICE_GROUP_CODE", ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
			setServiceGroupCode(ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
		}
		inputRoutingBankSetUpdetails.put("P_CURRENCY_ID", getCurrencyId().toPlainString());

		
					
			if( (session.getBranchId()!=null || session.getCustomerType().equals("E"))){ // && sessionStateManage.getRoleId().equals("1")
				inputRoutingBankSetUpdetails.put("P_USER_TYPE","BRANCH");
				setBooSingleService(true);
			}else if(session.getBranchId()!=null  &&  session.getUserType().equalsIgnoreCase("K")){
				inputRoutingBankSetUpdetails.put("P_USER_TYPE","KIOSK");
				setBooMultipleService(false);
			}else{
				setBooMultipleService(false);
			}

			setBankAccountSeqId(getBeneficiaryAccountId());
				
				if (getRemittanceName().equalsIgnoreCase(Constants.CASHNAME)) {
				List<BeneficaryAccount> lstBeneficaryAccount= iPersonalRemittanceService.getCashProductDetails(getBankAccountSeqId());

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
	}
	
	//routing procedure variables
	private String serviceGroupCode;
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
		private Boolean booRenderRemitPanel=false;
		private String procedureError; 
		
		//procedures list variables
		private List<PopulateData> routingbankvalues;
		private List<PopulateData> serviceList;
		private List<PopulateData> routingCountrylst;
		private List<PopulateData> lstofRemittance;
		private List<PopulateData> lstofDelivery;
		private List<PopulateData> routingBankBranchlst;

	public String getServiceGroupCode() {
		return serviceGroupCode;
	}
	public void setServiceGroupCode(String serviceGroupCode) {
		this.serviceGroupCode = serviceGroupCode;
	}
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
	public Boolean getBooSingleRoutingCountry() {
		return booSingleRoutingCountry;
	}
	public void setBooSingleRoutingCountry(Boolean booSingleRoutingCountry) {
		this.booSingleRoutingCountry = booSingleRoutingCountry;
	}
	public String getRoutingCountryName() {
		return routingCountryName;
	}
	public void setRoutingCountryName(String routingCountryName) {
		this.routingCountryName = routingCountryName;
	}
	public Boolean getBooMultipleRoutingBank() {
		return booMultipleRoutingBank;
	}
	public void setBooMultipleRoutingBank(Boolean booMultipleRoutingBank) {
		this.booMultipleRoutingBank = booMultipleRoutingBank;
	}
	public Boolean getBooSingleRoutingBank() {
		return booSingleRoutingBank;
	}
	public void setBooSingleRoutingBank(Boolean booSingleRoutingBank) {
		this.booSingleRoutingBank = booSingleRoutingBank;
	}
	public String getRoutingBankName() {
		return routingBankName;
	}
	public void setRoutingBankName(String routingBankName) {
		this.routingBankName = routingBankName;
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
	public String getRemittanceName() {
		return remittanceName;
	}
	public void setRemittanceName(String remittanceName) {
		this.remittanceName = remittanceName;
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
	public void setRoutingCountry(BigDecimal routingCountry) {
		this.routingCountry = routingCountry;
	}
	public void setRoutingBank(BigDecimal routingBank) {
		this.routingBank = routingBank;
	}
	public void setRoutingBranch(BigDecimal routingBranch) {
		this.routingBranch = routingBranch;
	}
	public BigDecimal getRoutingCountry() {
		return routingCountry;
	}
	public BigDecimal getRoutingBank() {
		return routingBank;
	}
	public BigDecimal getRoutingBranch() {
		return routingBranch;
	}
	public Boolean getBooRenderRemitPanel() {
		return booRenderRemitPanel;
	}
	public void setBooRenderRemitPanel(Boolean booRenderRemitPanel) {
		this.booRenderRemitPanel = booRenderRemitPanel;
	}
	public String getProcedureError() {
		return procedureError;
	}
	public void setProcedureError(String procedureError) {
		this.procedureError = procedureError;
	}
	
		public List<PopulateData> getRoutingbankvalues() {
		return routingbankvalues;
	}
	public void setRoutingbankvalues(List<PopulateData> routingbankvalues) {
		this.routingbankvalues = routingbankvalues;
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
	
}
