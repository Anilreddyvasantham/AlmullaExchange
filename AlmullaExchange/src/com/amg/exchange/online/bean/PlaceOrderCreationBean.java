package com.amg.exchange.online.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.beneficiary.model.BanksView;
import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.mail.ApllicationMailer1;
import com.amg.exchange.mail.ApplicationMailer;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.service.IGSMPlaceOrderRateFeedService;
import com.amg.exchange.online.service.IPlaceAnOrderCreationService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.ViewArea;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.bean.PersonalRemittanceBean;
import com.amg.exchange.remittance.bean.PersonalRemmitanceBeneficaryDataTable;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.bean.PopulateDataWithCode;
import com.amg.exchange.remittance.model.AuthicationLimitCheckView;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.ViewRoutingAgents;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IRoutingSetUpDetailsService;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;

@Component("placeOrderCreationBean")
@Scope("session")
public class PlaceOrderCreationBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger  log = Logger.getLogger(PlaceOrderCreationBean.class);
	private SessionStateManage session=new SessionStateManage();

	// auto wired
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	ICustomerRegistrationBranchService<T> iCustomerRegistrationBranchService;
	@Autowired
	IPlaceAnOrderCreationService  iPlaceOnOrderCreationService;
	@Autowired
	IBeneficaryCreation beneficaryCreation;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IGSMPlaceOrderRateFeedService gSMPlaceOrderRateFeedService;
	@Autowired
	ApplicationMailer applicationMailer;
	@Autowired
	ApllicationMailer1 apllicationMailer1;
	@Autowired
	private ApplicationContext appContext;
	@Autowired
	IRoutingSetUpDetailsService<T> iroutingSetUpDetailsService;

	private BigDecimal beneficiaryCountryId;
	private BigDecimal customerId;
	private BigDecimal customerRef;
	private String customerName;
	private BigDecimal serviceGroupId;
	private String serviceGroupCode;
	private String serviceGroupName;
	private BigDecimal beneficiaryId;
	private String beneficiaryName;
	private BigDecimal beneficiaryAgentBankId;
	private String beneficiaryAgentBankName;
	private BigDecimal beneficiaryAgentBankBranchId;
	private String beneficiaryAgentBankBranchName;
	private BigDecimal beneficiaryBankId;
	private String beneficiaryBankName;
	private BigDecimal beneficiaryAccountSeqId;
	private String beneficiaryAccountNumber;
	private BigDecimal requestCurrency;
	private BigDecimal destinationCurrency;
	private String emailId;
	private BigDecimal fcOrLocalAmount;
	private BigDecimal beneficiaryCurrencyId;
	private String beneficiaryCurrencyName;
	private String currencyQuoteName;
	private Date valueDate;
	private Date effectiveMinDate = new Date();
	private String areaName;
	//private String remarks;

	private Boolean booRenderSingleBank = true;
	private Boolean booRenderMultipleBank = false;
	private boolean bankingChannelProducts = true;
	private Boolean booRenderSingleAccount = true;
	private Boolean booRenderMultipleAccount = false;
	private Boolean booRenderSingleAgentBank = true;
	private Boolean booRenderMultipleAgentBank = false;
	private Boolean booRenderSingleAgentBankBranch = true;
	private Boolean booRenderMultipleAgentBankBranch = false;

	private BigDecimal financialYearId;
	private BigDecimal finaceYear;
	private BigDecimal documentId;
	private BigDecimal countryBranchId;
	private String errorMsg;
	private Boolean placeOrderCheck;
	private Boolean enableDesCurrency;

	private List<CountryMasterDesc> lstCountry;
	private List<PopulateData> allServiceList;
	private List<PopulateData> beneficiaryList;
	private List<PopulateDataWithCode> lstBanks;
	private List<PopulateDataWithCode> lstAgentBanks;
	private List<PopulateDataWithCode> lstAgentBankBranch;
	private List<PopulateData> lstAccountNumber;
	private List<ViewArea> lstViewArea;
	private List<PopulateData> lstRequestCurrency;
	private List<PopulateData> lstDestinationCurrency;

	private PersonalRemmitanceBeneficaryDataTable personalRemitDatatable = new PersonalRemmitanceBeneficaryDataTable();

	private Boolean booRenderPlaceOrderFromRemit = false;


	// page Nagivation for Place order creation
	public void pageNavigation(){
		try {
			log.info( "===============navigation==================");
			setPersonalRemitDatatable(null);
			setBooRenderPlaceOrderFromRemit(false);
			clearAll();
			fetchCustomerDetails();
			setCountryBranchId(new BigDecimal(session.getBranchId()));
			List<CountryMasterDesc> lstCountryMasterDesc = generalService.getCountryList(session.getLanguageId());
			setLstCountry(lstCountryMasterDesc);
			loadArea();			
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/PlaceOnOrderCreationBean.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Place Order Branch Login
	public void branchPageNavigation(){
		log.info( "===============navigation==================");
		try {
			setPersonalRemitDatatable(null);
			setBooRenderPlaceOrderFromRemit(false);
			clearAll();
			fetchDocYear();
			fetchCustomerDetails();
			setCountryBranchId(new BigDecimal(session.getBranchId()));
			List<CountryMasterDesc> lstCountryMasterDesc = generalService.getCountryList(session.getLanguageId());
			setLstCountry(lstCountryMasterDesc);
			loadArea();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/PlaceOnOrderCreationBeanBranch.xhtml");
		} catch (Exception e) {
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	// place Order Personal remittance
	public void branchPersonalRemitPageNavigation(PersonalRemmitanceBeneficaryDataTable datatabledetails){
		log.info( "===============navigation==================");
		try {
			setPersonalRemitDatatable(datatabledetails);
			clearAll();
			fetchDocYear();
			fetchCustomerDetails();
			setCountryBranchId(new BigDecimal(session.getBranchId()));
			List<CountryMasterDesc> lstCountryMasterDesc = generalService.getCountryList(session.getLanguageId());
			setLstCountry(lstCountryMasterDesc);
			loadArea();
			setAllPersonalRemitDt();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/PlaceOnOrderCreationBeanBranch.xhtml");
		} catch (Exception e) {
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	// set all beneficiary details
	public void setAllPersonalRemitDt(){
		// set All values
		setBeneficiaryCountryId(getPersonalRemitDatatable().getCountryId());  // bank Country Id

		setBeneficiaryId(getPersonalRemitDatatable().getBeneficaryMasterSeqId());

		populateServiceGroup();
		setServiceGroupId(getPersonalRemitDatatable().getServiceGroupId());
		populateBeneficiary();
		setBeneficiaryName(getPersonalRemitDatatable().getBenificaryName());
		//populateBanks();
		if(getPersonalRemitDatatable().getServiceGroupName().equalsIgnoreCase(Constants.CASHNAME)){
			// cash product
			setBankingChannelProducts(false);

			setBeneficiaryBankId(getPersonalRemitDatatable().getServiceProvider());
			String bankName = generalService.getBankName(getPersonalRemitDatatable().getServiceProvider());
			if(bankName != null){
				setBeneficiaryBankName(bankName);
			}
			setBooRenderSingleBank(true);
			setBooRenderMultipleBank(false);

			setBeneficiaryAgentBankId(getPersonalRemitDatatable().getBankId());
			setBeneficiaryAgentBankName(getPersonalRemitDatatable().getBankName());
			setBooRenderSingleAgentBank(true);
			setBooRenderMultipleAgentBank(false);

			setBeneficiaryAgentBankBranchId(getPersonalRemitDatatable().getBranchId());
			setBeneficiaryAgentBankBranchName(getPersonalRemitDatatable().getBankBranchName());
			setBooRenderSingleAgentBankBranch(true);
			setBooRenderMultipleAgentBankBranch(false);

		}else{
			// banking Channel
			setBankingChannelProducts(true);

			setBeneficiaryBankId(getPersonalRemitDatatable().getBankId());
			setBeneficiaryBankName(getPersonalRemitDatatable().getBankName());
			setBooRenderSingleBank(true);
			setBooRenderMultipleBank(false);

			setBooRenderSingleAccount(true);
			setBooRenderMultipleAccount(false);

			setBeneficiaryAccountNumber(getPersonalRemitDatatable().getBankAccountNumber());
			setBeneficiaryAccountSeqId(getPersonalRemitDatatable().getBeneficiaryAccountSeqId());
		}

		populateCurrency();

	}

	// populate customer details
	public void fetchCustomerDetails(){
		List<Customer> custList = iCustomerRegistrationBranchService.getCustomerInfo(getCustomerId());
		if(custList != null && custList.size() != 0){
			Customer customer=custList.get(0);
			setEmailId(customer.getEmail());
			setCustomerRef(customer.getCustomerReference());
			setCustomerName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " " + nullCheck(customer.getLastName()));
		}

	}

	// service group -- banking Channel and cash
	public void populateServiceGroup() {  
		try{
			clear();

			List<PopulateData> serviceList=iPlaceOnOrderCreationService.getServiceGroupList(session.getLanguageId());

			if(serviceList!=null && serviceList.size()==0)
			{
				setErrorMsg("Service Group not Available");
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}else{
				setAllServiceList(serviceList);
			}
		}catch(Exception e){
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	public void populateBeneficiary(){

		try{
			setBeneficiaryList(null);
			setBeneficiaryName(null);
			setBeneficiaryBankId(null);
			setBeneficiaryBankName(null);
			setFcOrLocalAmount(null);
			setCurrencyQuoteName(null);
			setAreaName(null);
			setRequestCurrency(null);
			setDestinationCurrency(null);
			setLstDestinationCurrency(null);
			setLstRequestCurrency(null);
			setBeneficiaryAccountNumber(null);
			setBeneficiaryAccountSeqId(null);
			setLstAccountNumber(null);
			setBooRenderSingleBank(true);
			setBooRenderMultipleBank(false);
			setBooRenderSingleAccount(true);
			setBooRenderMultipleAccount(false);
			setBankingChannelProducts(true);

			List<PopulateData> lstBeneName = iPlaceOnOrderCreationService.getBeneficiarNameList(getCustomerId(), getBeneficiaryCountryId(), getServiceGroupId(),getCustomerRef());
			if(lstBeneName != null && lstBeneName.size() != 0){
				setBeneficiaryList(lstBeneName);
			}

			for (PopulateData populateData : allServiceList) {
				if(populateData.getPopulateId().compareTo(getServiceGroupId())==0){
					setServiceGroupCode(populateData.getPopulateCode());
					setServiceGroupName(populateData.getPopulateName());
					break;
				}
			}

			if(getServiceGroupCode() != null && getServiceGroupCode().equalsIgnoreCase(Constants.BANKCHANNEL)){
				setBankingChannelProducts(true);
			}else if(getServiceGroupCode() != null && getServiceGroupCode().equalsIgnoreCase(Constants.CASHFORONLINE)){
				setBankingChannelProducts(false);
			}

			// needed when beneficiary is optional don't delete
			// populateBanks();

		}catch(Exception e){
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

	}

	// populate banks
	public void populateBanks()
	{
		try{

			setBeneficiaryBankId( null);
			setLstBanks(null);
			setFcOrLocalAmount(null);
			setCurrencyQuoteName(null);
			setAreaName(null);
			setRequestCurrency(null);
			setDestinationCurrency(null);
			setBooRenderSingleBank(true);
			setBooRenderMultipleBank(false);

			setBeneficiaryAccountNumber(null);
			setBeneficiaryAccountSeqId(null);
			setLstAccountNumber(null);
			setBooRenderSingleAccount(true);
			setBooRenderMultipleAccount(false);

			setBeneficiaryAgentBankId(null);
			setBeneficiaryAgentBankName(null);
			setLstAgentBanks(null);
			setBooRenderSingleAgentBank(true);
			setBooRenderMultipleAgentBank(false);

			setBeneficiaryAgentBankBranchId(null);
			setBeneficiaryAgentBankBranchName(null);
			setLstAgentBankBranch(null);
			setBooRenderSingleAgentBankBranch(true);
			setBooRenderMultipleAgentBankBranch(false);

			if(getBeneficiaryName() != null)
			{
				List<PopulateData> lstBeneBanks = iPlaceOnOrderCreationService.getBeneficiarBankList(getBeneficiaryName(),getServiceGroupId(),getBeneficiaryCountryId(),getCustomerRef());

				if(lstBeneBanks!=null && lstBeneBanks.size()==0)
				{
					setErrorMsg("Bank not Available");
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}else if (lstBeneBanks!=null && lstBeneBanks.size()==1) {
					setBeneficiaryBankId(lstBeneBanks.get(0).getPopulateId());
					setBeneficiaryBankName(lstBeneBanks.get(0).getPopulateName());
					setBooRenderSingleBank(true);
					setBooRenderMultipleBank(false);

					if(isBankingChannelProducts()){
						populateAccountNumber();
					}else{
						populateAgentsforCash();
					}

				}else if (lstBeneBanks!=null && lstBeneBanks.size() > 1){

					List<PopulateDataWithCode> lstSpBank = new ArrayList<PopulateDataWithCode>();
					List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

					for (PopulateData viewRoutingAgents : lstBeneBanks) {

						if(!duplicateCheck.contains(viewRoutingAgents.getPopulateId())){
							duplicateCheck.add(viewRoutingAgents.getPopulateId());

							PopulateDataWithCode lstBank = new PopulateDataWithCode();

							lstBank.setId(viewRoutingAgents.getPopulateId());
							lstBank.setCode(viewRoutingAgents.getPopulateCode());
							lstBank.setName(viewRoutingAgents.getPopulateName());

							lstSpBank.add(lstBank);
						}

					}

					setLstBanks(lstSpBank);
					setBooRenderSingleBank(false);
					setBooRenderMultipleBank(true);
				}

			}else{
				if(getServiceGroupCode() != null && getServiceGroupCode().equalsIgnoreCase(Constants.BANKCHANNEL)){
					fetchAllBeneServiceBanks();
				}else if(getServiceGroupCode() != null && getServiceGroupCode().equalsIgnoreCase(Constants.CASHFORONLINE)){
					fetchAllBeneServiceProvider();
				}
			}

		}catch(Exception e){
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

	}

	// calling view V_EX_ROUTING_AGENTS for banks based on appcountry,benecountry,servicegroup
	public void fetchAllBeneServiceProvider(){
		setLstBanks(null);

		List<ViewRoutingAgents> lstSerProvBanks = iroutingSetUpDetailsService.fetchAllRoutingAgents(session.getCountryId(), getBeneficiaryCountryId(), getServiceGroupId(), null, null);

		if(lstSerProvBanks.size() != 0){

			List<PopulateDataWithCode> lstSpBank = new ArrayList<PopulateDataWithCode>();
			List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

			for (ViewRoutingAgents viewRoutingAgents : lstSerProvBanks) {

				if(!duplicateCheck.contains(viewRoutingAgents.getRoutingBankId())){
					duplicateCheck.add(viewRoutingAgents.getRoutingBankId());

					PopulateDataWithCode lstBank = new PopulateDataWithCode();

					lstBank.setId(viewRoutingAgents.getRoutingBankId());
					lstBank.setCode(viewRoutingAgents.getRoutingBankCode());
					lstBank.setName(viewRoutingAgents.getRoutingBankName());

					lstSpBank.add(lstBank);
				}

			}

			if(lstSpBank != null && lstSpBank.size() == 1){
				setBooRenderSingleBank(true);
				setBooRenderMultipleBank(false);
				setBeneficiaryBankName(lstSpBank.get(0).getName());
				setBeneficiaryBankId(lstSpBank.get(0).getId());
				if(isBankingChannelProducts()){
					populateAccountNumber();
				}else{
					populateCurrency();
				}
			}else if(lstSpBank != null && lstSpBank.size() > 1){
				setBooRenderSingleBank(false);
				setBooRenderMultipleBank(true);
				setLstBanks(lstSpBank);
			}else{
				setBooRenderSingleBank(true);
				setBooRenderMultipleBank(false);
			}
		}
	}

	// calling view VW_EX_BANKS for banks based on appcountry, benecountry,servicegroup
	public void fetchAllBeneServiceBanks(){
		setLstBanks(null);

		List<BanksView> lstBanksView = beneficaryCreation.getBankListFromView(session.getCountryId(), getBeneficiaryCountryId(), getServiceGroupId());
		if(lstBanksView.size() != 0){

			List<PopulateDataWithCode> lstSpBank = new ArrayList<PopulateDataWithCode>();
			List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

			for (BanksView viewBanks : lstBanksView) {

				if(!duplicateCheck.contains(viewBanks.getBankId())){
					duplicateCheck.add(viewBanks.getBankId());

					PopulateDataWithCode lstBank = new PopulateDataWithCode();

					lstBank.setId(viewBanks.getBankId());
					lstBank.setCode(viewBanks.getBankCode());
					lstBank.setName(viewBanks.getBankFullName());

					lstSpBank.add(lstBank);
				}
			}

			if(lstSpBank != null && lstSpBank.size() == 1){
				setBooRenderSingleBank(true);
				setBooRenderMultipleBank(false);
				setBeneficiaryBankName(lstSpBank.get(0).getName());
				setBeneficiaryBankId(lstSpBank.get(0).getId());
				populateAgentsforCash();
			}else if(lstSpBank != null && lstSpBank.size() > 1){
				setBooRenderSingleBank(false);
				setBooRenderMultipleBank(true);
				setLstBanks(lstSpBank);
			}else{
				setBooRenderSingleBank(true);
				setBooRenderMultipleBank(false);
			}
		}
	}

	// populate agents for the service providers
	public void populateAgentsforCash(){
		try{

			setFcOrLocalAmount(null);
			setCurrencyQuoteName(null);
			setBeneficiaryAccountNumber(null);
			setBeneficiaryAccountSeqId(null);
			setLstAccountNumber(null);
			setAreaName(null);
			setRequestCurrency(null);
			setDestinationCurrency(null);
			setBeneficiaryAgentBankId(null);
			setBeneficiaryAgentBankName(null);
			setLstAgentBanks(null);
			setBooRenderSingleAgentBank(true);
			setBooRenderMultipleAgentBank(false);
			setBeneficiaryAgentBankBranchId(null);
			setBeneficiaryAgentBankBranchName(null);
			setLstAgentBankBranch(null);
			setBooRenderSingleAgentBankBranch(true);
			setBooRenderMultipleAgentBankBranch(false);

			if(getBeneficiaryName()!=null){

				List<PopulateData> agentBanksList = iPlaceOnOrderCreationService.getBeneficiaryAgents(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerId());

				if(agentBanksList!=null && agentBanksList.size()==0) {
					setErrorMsg("Agent Bank not Available");
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}else if (agentBanksList!=null && agentBanksList.size()==1) {
					setBeneficiaryAgentBankId(agentBanksList.get(0).getPopulateId());
					setBeneficiaryAgentBankName(agentBanksList.get(0).getPopulateName());
					setBooRenderSingleAgentBank(true);
					setBooRenderMultipleAgentBank(false);
					populateAgentsBranchforCash();
				}else if (agentBanksList!=null && agentBanksList.size() > 1){

					List<PopulateDataWithCode> lstSpBank = new ArrayList<PopulateDataWithCode>();
					List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

					for (PopulateData viewRoutingAgents : agentBanksList) {

						if(!duplicateCheck.contains(viewRoutingAgents.getPopulateId())){
							duplicateCheck.add(viewRoutingAgents.getPopulateId());

							PopulateDataWithCode lstBank = new PopulateDataWithCode();

							lstBank.setId(viewRoutingAgents.getPopulateId());
							lstBank.setCode(viewRoutingAgents.getPopulateCode());
							lstBank.setName(viewRoutingAgents.getPopulateName());

							lstSpBank.add(lstBank);
						}

					}

					setLstAgentBanks(lstSpBank);
					setBooRenderSingleAgentBank(false);
					setBooRenderMultipleAgentBank(true);
				}

			}

		}catch(Exception e){
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

	}

	// populate agents branch for the service providers
	public void populateAgentsBranchforCash(){
		try{

			setFcOrLocalAmount(null);
			setCurrencyQuoteName(null);
			setBeneficiaryAccountNumber(null);
			setBeneficiaryAccountSeqId(null);
			setLstAccountNumber(null);
			setAreaName(null);
			setRequestCurrency(null);
			setDestinationCurrency(null);
			setBeneficiaryAgentBankBranchId(null);
			setBeneficiaryAgentBankBranchName(null);
			setLstAgentBankBranch(null);
			setBooRenderSingleAgentBankBranch(true);
			setBooRenderMultipleAgentBankBranch(false);

			if(getBeneficiaryName()!=null){

				List<PopulateData> agentBanksList = iPlaceOnOrderCreationService.getBeneficiaryAgentsBranch(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerId(),getBeneficiaryAgentBankId());

				if(agentBanksList!=null && agentBanksList.size()==0) {
					setErrorMsg("Agent Bank Branch not Available");
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}else if (agentBanksList!=null && agentBanksList.size()==1) {
					setBeneficiaryAgentBankBranchId(agentBanksList.get(0).getPopulateId());
					setBeneficiaryAgentBankBranchName(agentBanksList.get(0).getPopulateName());
					setBooRenderSingleAgentBankBranch(true);
					setBooRenderMultipleAgentBankBranch(false);
					populateCurrency();
				}else if (agentBanksList!=null && agentBanksList.size() > 1){

					List<PopulateDataWithCode> lstSpBank = new ArrayList<PopulateDataWithCode>();
					List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

					for (PopulateData viewRoutingAgents : agentBanksList) {

						if(!duplicateCheck.contains(viewRoutingAgents.getPopulateId())){
							duplicateCheck.add(viewRoutingAgents.getPopulateId());

							PopulateDataWithCode lstBank = new PopulateDataWithCode();

							lstBank.setId(viewRoutingAgents.getPopulateId());
							lstBank.setCode(viewRoutingAgents.getPopulateCode());
							lstBank.setName(viewRoutingAgents.getPopulateName());

							lstSpBank.add(lstBank);
						}

					}

					setLstAgentBankBranch(lstSpBank);
					setBooRenderSingleAgentBankBranch(false);
					setBooRenderMultipleAgentBankBranch(true);
				}

			}

		}catch(Exception e){
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

	}


	// populate account 
	public void populateAccountNumber() {
		try{

			setFcOrLocalAmount(null);
			setCurrencyQuoteName(null);
			setBeneficiaryAccountNumber(null);
			setBeneficiaryAccountSeqId(null);
			setLstAccountNumber(null);
			setAreaName(null);
			setRequestCurrency(null);
			setDestinationCurrency(null);
			setBooRenderSingleAccount(true);
			setBooRenderMultipleAccount(false);

			if(isBankingChannelProducts()){
				if(getBeneficiaryName()!=null){

					List<PopulateData> accountNumberList= iPlaceOnOrderCreationService.getBeneAccountNumber(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerRef());

					if(accountNumberList!=null && accountNumberList.size()==0)
					{
						setErrorMsg("AccountNumber Not Available");
						RequestContext.getCurrentInstance().execute("error.show();");
						return;
					}else if(accountNumberList!=null && accountNumberList.size()==1)
					{
						setBooRenderSingleAccount(true);
						setBooRenderMultipleAccount(false);
						setBeneficiaryAccountNumber(accountNumberList.get(0).getPopulateCode());
						setBeneficiaryAccountSeqId(accountNumberList.get(0).getPopulateId());
						populateCurrency();
					}else if(accountNumberList!=null && accountNumberList.size()>1){
						setBooRenderSingleAccount(false);
						setBooRenderMultipleAccount(true);
						setLstAccountNumber(accountNumberList);
					}
				}else{
					// if no Bene name
				}
			}else{
				populateAgentsforCash();
			}
		}catch(Exception e){
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	// populate currency 
	public void populateCurrency(){


		// bank Account number 
		if(getLstAccountNumber() != null){
			for (PopulateData popdata : getLstAccountNumber()) {
				if(getBeneficiaryAccountSeqId() != null && popdata.getPopulateId().compareTo(getBeneficiaryAccountSeqId())==0){
					setBeneficiaryAccountNumber(popdata.getPopulateCode());
					break;
				}
			}
		}

		List<PopulateData> rtnMap = iPlaceOnOrderCreationService.fetchBeneMasterDetailsfromView(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerRef(),getBeneficiaryAccountNumber());

		if(rtnMap != null && rtnMap.size() != 0){

			List<PopulateData> lstPopCust = new ArrayList<PopulateData>();

			for (PopulateData populateData : rtnMap) {
				PopulateData popreqCurr = new PopulateData();

				if(populateData.getPopulateCode() != null){
					popreqCurr.setPopulateId(new BigDecimal(populateData.getPopulateCode()));
					popreqCurr.setPopulateCode(populateData.getPopulateName());
					popreqCurr.setPopulateName(generalService.getCurrencyName(new BigDecimal(populateData.getPopulateCode())));
				}

				lstPopCust.add(popreqCurr);
			}

			if(lstPopCust != null && lstPopCust.size() != 0){
				if(lstPopCust.size() == 1){
					setBeneficiaryCurrencyId(lstPopCust.get(0).getPopulateId());
					setCurrencyQuoteName(lstPopCust.get(0).getPopulateCode());
					setBeneficiaryCurrencyName(lstPopCust.get(0).getPopulateName());

					List<PopulateData> lstCurrency = new ArrayList<PopulateData>();
					PopulateData populatedata = new PopulateData(getBeneficiaryCurrencyId(), getBeneficiaryCurrencyName());
					PopulateData populatedata1 = null;
					String localCurrencyName = generalService.getCurrencyName(new BigDecimal(session.getCurrencyId()));
					if(localCurrencyName != null){
						populatedata1 = new PopulateData(new BigDecimal(session.getCurrencyId()),localCurrencyName);
					}
					
					lstCurrency.add(populatedata);
					if(populatedata1 != null){
						lstCurrency.add(populatedata1);
					}
					setLstRequestCurrency(lstCurrency);
					
					if (lstCurrency.size() != 0) {
						for (PopulateData lstofcurrency : lstCurrency) {
							if (lstofcurrency.getPopulateId().compareTo(new BigDecimal(session.getCurrencyId())) != 0) {
								setDestinationCurrency(lstofcurrency.getPopulateId());
							}
						}
					}
					setRequestCurrency(new BigDecimal(session.getCurrencyId()));
					selectCurrency();
				}else{
					List<PopulateData> lstCurrency = new ArrayList<PopulateData>();
					List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

					for (PopulateData populateData : lstPopCust) {
						if(!duplicateCheck.contains(populateData.getPopulateId())){
							duplicateCheck.add(populateData.getPopulateId());
							PopulateData populatedata = new PopulateData(populateData.getPopulateId(), populateData.getPopulateName());
							lstCurrency.add(populatedata);
						}
					}
					PopulateData populatedata1 = null;
					String localCurrencyName = generalService.getCurrencyName(new BigDecimal(session.getCurrencyId()));
					if(localCurrencyName != null){
						populatedata1 = new PopulateData(new BigDecimal(session.getCurrencyId()),localCurrencyName);
					}
					if(populatedata1 != null){
						lstCurrency.add(populatedata1);
					}
					setLstRequestCurrency(lstCurrency);
					if (lstCurrency.size() != 0) {
						for (PopulateData lstofcurrency : lstCurrency) {
							if (lstofcurrency.getPopulateId().compareTo(new BigDecimal(session.getCurrencyId())) != 0) {
								setDestinationCurrency(lstofcurrency.getPopulateId());
							}
						}
					}
					setRequestCurrency(new BigDecimal(session.getCurrencyId()));
					selectCurrency();
				}
			}
		}
	}

	// destination currency
	public void loadDestinationCurrencylist()
	{
		setEnableDesCurrency(true);
		List<PopulateData> lstofCurrency = new ArrayList<PopulateData>();
		if(getLstRequestCurrency() != null && getLstRequestCurrency().size() != 0){

			for (PopulateData populateData : getLstRequestCurrency()) {
				if(populateData.getPopulateId().compareTo(new BigDecimal(session.getCurrencyId())) != 0){
					lstofCurrency.add(populateData);
					setDestinationCurrency(populateData.getPopulateId());
				}
			}
		}
		setLstDestinationCurrency(lstofCurrency);
	}

	// destination condition check
	public void selectCurrency()
	{
		loadDestinationCurrencylist();
		BigDecimal localCurrencyId=new BigDecimal(session.getCurrencyId());
		if(getRequestCurrency() != null && getRequestCurrency().compareTo(localCurrencyId)!=0)
		{
			setDestinationCurrency(getRequestCurrency());
			setEnableDesCurrency(true);
		}else
		{
			setEnableDesCurrency(false);
		}

	}

	// null check
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

	// load all areas from V_EX_AREA
	private void loadArea()
	{
		setLstViewArea(null);
		List<ViewArea> lstViewArea = gSMPlaceOrderRateFeedService.getAreaPlace();
		if(lstViewArea.size()>0){
			setLstViewArea(lstViewArea);
		}
	}

	public void backToFirst()
	{
		try{
			if(getPersonalRemitDatatable() != null){
				// back to personal remittance
				setPersonalRemitDatatable(null);
				setBooRenderPlaceOrderFromRemit(false);
				clearAll();

				PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
				objectPersonalRemittance.personalRemittanceBackFromBene(null);
				objectPersonalRemittance.setBeneficiaryCountryId(objectPersonalRemittance.getNationality());
				objectPersonalRemittance.populateCustomerDetailsFromBeneRelation();
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");

			}else{
				BranchPlaceOrder cranchPlaceOrder = (BranchPlaceOrder) appContext.getBean("branchPlaceOrder");
				cranchPlaceOrder.branchPlaceOrderPageNavigation();
			}
		}catch(Exception e){
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	public void exitBranch(){

		try {
			clearAll();
			//setBooRenderExitButtonOnline(false);
			//setBooRenderExitButtonBranch(false);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}catch(Exception e){
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}  
	}

	// clear All
	public void clearAll(){
		
		if(getBooRenderPlaceOrderFromRemit()){
			setAreaName(null);
			setValueDate(new Date());
			setFcOrLocalAmount(null);
			//setRemarks(null);
		}else{
			// bene country
			setBeneficiaryCountryId(null);
			// bene service group
			setServiceGroupId(null);
			setServiceGroupCode(null);
			setServiceGroupName(null);
			setAllServiceList(null);
			// bene dt
			setBeneficiaryId(null);
			setBeneficiaryName(null);
			setBeneficiaryList(null);

			//setFinaceYear(null);
			setDocumentId(null);
			setCurrencyQuoteName(null);
			setAreaName(null);
			setValueDate(new Date());
			setFcOrLocalAmount(null);
			// bene currency
			setLstRequestCurrency(null);
			setLstDestinationCurrency(null);
			setRequestCurrency(null);
			setDestinationCurrency(null);
			// bene bank
			setBeneficiaryBankId(null);
			setBeneficiaryBankName(null);
			setLstBanks(null);
			setBooRenderSingleBank(true);
			setBooRenderMultipleBank(false);

			setPlaceOrderCheck(false);
			// bene account
			setBankingChannelProducts(true);
			setBeneficiaryAccountNumber(null);
			setBeneficiaryAccountSeqId(null);
			setLstAccountNumber(null);
			setBooRenderSingleAccount(true);
			setBooRenderMultipleAccount(null);
			// bene agent
			setBeneficiaryAgentBankBranchId(null);
			setBeneficiaryAgentBankBranchName(null);
			setLstAgentBankBranch(null);
			setBooRenderSingleAgentBankBranch(true);
			setBooRenderMultipleAgentBankBranch(false);
			// bene agent branch
			setBeneficiaryAgentBankId(null);
			setBeneficiaryAgentBankName(null);
			setLstAgentBanks(null);
			setBooRenderSingleAgentBank(true);
			setBooRenderMultipleAgentBank(false);
			setBooRenderPlaceOrderFromRemit(false);
			
			//setRemarks(null);
			//setPersonalRemitDatatable(null);
		}
	}

	// clear below country
	public void clear(){

		// bene service group
		setServiceGroupId(null);
		setServiceGroupCode(null);
		setServiceGroupName(null);
		setAllServiceList(null);
		// bene dt
		setBeneficiaryId(null);
		setBeneficiaryName(null);
		setBeneficiaryList(null);

		//setFinaceYear(null);
		setDocumentId(null);
		setCurrencyQuoteName(null);
		setAreaName(null);
		setValueDate(new Date());
		setFcOrLocalAmount(null);
		// bene currency
		setLstRequestCurrency(null);
		setLstDestinationCurrency(null);
		setRequestCurrency(null);
		setDestinationCurrency(null);
		// bene bank
		setBeneficiaryBankId(null);
		setBeneficiaryBankName(null);
		setLstBanks(null);
		setBooRenderSingleBank(true);
		setBooRenderMultipleBank(false);

		setPlaceOrderCheck(false);
		// bene account
		setBankingChannelProducts(true);
		setBeneficiaryAccountNumber(null);
		setBeneficiaryAccountSeqId(null);
		setLstAccountNumber(null);
		setBooRenderSingleAccount(true);
		setBooRenderMultipleAccount(null);
		// bene agent
		setBeneficiaryAgentBankBranchId(null);
		setBeneficiaryAgentBankBranchName(null);
		setLstAgentBankBranch(null);
		setBooRenderSingleAgentBankBranch(true);
		setBooRenderMultipleAgentBankBranch(false);
		// bene agent branch
		setBeneficiaryAgentBankId(null);
		setBeneficiaryAgentBankName(null);
		setLstAgentBanks(null);
		setBooRenderSingleAgentBank(true);
		setBooRenderMultipleAgentBank(false);

	}

	// fetch doc year
	public void fetchDocYear(){
		try{
			List<UserFinancialYear> financialYearList =generalService .getDealYear(new Date());
			log.info("financialYearList :"+financialYearList.size());
			if(financialYearList!=null && financialYearList.size()>0){
				if(financialYearList.get(0).getFinancialYear()!=null){
					finaceYear = financialYearList.get(0).getFinancialYear();
				}
				financialYearId=financialYearList.get(0).getFinancialYearID();
				setFinancialYearId(financialYearId);
				setFinaceYear(finaceYear);
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMsg(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public String getDocumentDescription() {
		String  documentDescription=null;

		try{
			List<Document> documentDesc=generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PLACEORDER),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));
			for(Document des:documentDesc)
			{
				setDocumentId(des.getDocumentID());

			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMsg(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;       
		}
		return documentDescription;
	}

	// save record 
	public void saveRecord(){
		try{

			if(isBankingChannelProducts()) {
				if(getBeneficiaryAccountNumber()==null)
				{
					setErrorMsg(WarningHandler.showWarningMessage("lbl.accountnomandatory", new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"))); 
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}
			}
			
			// checking beneficiary and amount exists or not per day
			if(getFcOrLocalAmount() != null && getFcOrLocalAmount().compareTo(BigDecimal.ZERO) != 0){
				List<CurrencyOtherInformation> lstCurrencyData = iPlaceOnOrderCreationService.fetchCurrencyMasterOthInfo(getRequestCurrency());
				if(lstCurrencyData != null && lstCurrencyData.size() != 0){
					if(lstCurrencyData.size() == 1){
						CurrencyOtherInformation currencyOth = lstCurrencyData.get(0);
						
						if(currencyOth.getPlaceOrderLimit() != null){
							if(currencyOth.getPlaceOrderLimit().compareTo(getFcOrLocalAmount()) <= 0){
								// allow
							}else{
								setErrorMsg("Please enter more than Place Order Limit amount : "+currencyOth.getPlaceOrderLimit()); 
								RequestContext.getCurrentInstance().execute("error.show();");
								return;
							}
						}else{
							setErrorMsg("Place order limit is not updated please contact Back Office"); 
							RequestContext.getCurrentInstance().execute("error.show();");
							return;
						}
					}else{
						setErrorMsg("Multiple currency fetching from EX_CURRENCY_OTHINFO"); 
						RequestContext.getCurrentInstance().execute("error.show();");
						return;
					}
				}
			}else{
				setErrorMsg("Please Enter Transaction Amount"); 
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
			
			BigDecimal beneficaryMasterSeqId = null;
			BigDecimal beneficaryAccountSeqId = null;

			List<BenificiaryListView> ListBene = iPlaceOnOrderCreationService.getBeneficiaryDetails(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerId(),getBeneficiaryAgentBankId(),getBeneficiaryAgentBankBranchId(),getBeneficiaryAccountNumber(),getDestinationCurrency());

			if(ListBene != null && ListBene.size() == 1){
				BenificiaryListView lstBeneDt = ListBene.get(0);

				beneficaryMasterSeqId = lstBeneDt.getBeneficaryMasterSeqId();
				beneficaryAccountSeqId = lstBeneDt.getBeneficiaryAccountSeqId();

				if(getBeneficiaryAccountSeqId() != null){
					if(beneficaryAccountSeqId != null && beneficaryAccountSeqId.compareTo(getBeneficiaryAccountSeqId())!=0){
						setBeneficiaryAccountSeqId(beneficaryAccountSeqId);
					}
				}else{
					setBeneficiaryAccountSeqId(beneficaryAccountSeqId);
				}

			}else{
				setErrorMsg("Mulitple Records"); 
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}

			if(isBankingChannelProducts()){
				
				// check the beneficiary limit for day
				BigDecimal authLimit = BigDecimal.ZERO;
				BigDecimal beneLimit = BigDecimal.ZERO;
				List<AuthicationLimitCheckView> lstAuthLimit = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.LimitBeneficiaryPerDay_PlaceOrder);
				if(lstAuthLimit != null && lstAuthLimit.size() != 0){
					authLimit = lstAuthLimit.get(0).getAuthLimit();
					
					List<RatePlaceOrder> lstPlaceOrders=iPlaceOnOrderCreationService.authLimitCheck(getCustomerId(),getCustomerRef(),getBeneficiaryBankId(),new Date(),getBeneficiaryCountryId(),getBeneficiaryAccountNumber(),getServiceGroupId(),getFcOrLocalAmount(),beneficaryMasterSeqId,getBeneficiaryAccountSeqId());
					
					if(lstPlaceOrders != null && lstPlaceOrders.size() != 0){
						beneLimit = new BigDecimal(lstPlaceOrders.size());
						if(authLimit != null && beneLimit.compareTo(authLimit) >= 0){
							setErrorMsg("PLACE ORDER SAME BENE DIFFERENT AMOUNT LIMIT CHECK EXCEED"); 
							RequestContext.getCurrentInstance().execute("error.show();");
							clearAll();
							return;
						}
					}
				}
				
				List<RatePlaceOrder> lstPlaceOrders=iPlaceOnOrderCreationService.duplicatecheckRecord(getCustomerId(),getCustomerRef(),getBeneficiaryBankId(),new Date(),getBeneficiaryCountryId(),getBeneficiaryAccountNumber(),getServiceGroupId(),getFcOrLocalAmount(),beneficaryMasterSeqId,getBeneficiaryAccountSeqId());
				if(lstPlaceOrders.size()>0){
					setErrorMsg(WarningHandler.showWarningMessage("lbl.placeOrederExist", new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"))); 
					RequestContext.getCurrentInstance().execute("error.show();");
					clearAll();
					return;
				}
			}else{
				
				// check the beneficiary limit for day
				BigDecimal authLimit = BigDecimal.ZERO;
				BigDecimal beneLimit = BigDecimal.ZERO;
				List<AuthicationLimitCheckView> lstAuthLimit = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.LimitBeneficiaryPerDay_PlaceOrder);
				if(lstAuthLimit != null && lstAuthLimit.size() != 0){
					authLimit = lstAuthLimit.get(0).getAuthLimit();
					
					List<RatePlaceOrder> lstPlaceOrders=iPlaceOnOrderCreationService.authLimitCheck(getCustomerId(),getCustomerRef(),getBeneficiaryAgentBankId(),new Date(),getBeneficiaryCountryId(),getBeneficiaryAccountNumber(),getServiceGroupId(),getFcOrLocalAmount(),beneficaryMasterSeqId,getBeneficiaryAccountSeqId());
					
					if(lstPlaceOrders != null && lstPlaceOrders.size() != 0){
						beneLimit = new BigDecimal(lstPlaceOrders.size());
						if(authLimit != null && beneLimit.compareTo(authLimit) >= 0){
							setErrorMsg("PLACE ORDER SAME BENE DIFFERENT AMOUNT LIMIT CHECK EXCEED"); 
							RequestContext.getCurrentInstance().execute("error.show();");
							clearAll();
							return;
						}
					}
				}
				
				List<RatePlaceOrder> lstPlaceOrders=iPlaceOnOrderCreationService.duplicatecheckRecord(getCustomerId(),getCustomerRef(),getBeneficiaryAgentBankId(),new Date(),getBeneficiaryCountryId(),getBeneficiaryAccountNumber(),getServiceGroupId(),getFcOrLocalAmount(),beneficaryMasterSeqId,getBeneficiaryAccountSeqId());
				if(lstPlaceOrders.size()>0){
					setErrorMsg(WarningHandler.showWarningMessage("lbl.placeOrederExist", new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"))); 
					RequestContext.getCurrentInstance().execute("error.show();");
					clearAll();
					return;
				}
			}

			//getDocumentDescription();
			getFinaceYear();
			BigDecimal docRefNumber=getDocumentSerialIdNumber(Constants.U);
			if(docRefNumber.compareTo(BigDecimal.ZERO)==0)
			{
				setErrorMsg(WarningHandler.showWarningMessage("lbl.docZero", new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"))); 
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}

			RatePlaceOrder ratePlaceOrderObj=new RatePlaceOrder();

			if(isBankingChannelProducts()){
				ratePlaceOrderObj.setBeneficiaryBankId(getBeneficiaryBankId());
			}else{
				ratePlaceOrderObj.setBeneficiaryBankId(getBeneficiaryAgentBankId());
			}

			ratePlaceOrderObj.setCustomerId(getCustomerId());
			ratePlaceOrderObj.setCreatedBy(session.getUserName());
			ratePlaceOrderObj.setCreatedDate(new Date());
			ratePlaceOrderObj.setBeneficiaryCountryId(getBeneficiaryCountryId());
			ratePlaceOrderObj.setBeneficiaryAccountNo(getBeneficiaryAccountNumber());
			ratePlaceOrderObj.setRemitType(getServiceGroupId());
			ratePlaceOrderObj.setCustomerEmail(getEmailId());
			ratePlaceOrderObj.setTransactionAmount(getFcOrLocalAmount());
			ratePlaceOrderObj.setIsActive(Constants.U);

			if(getBeneficiaryAccountSeqId()!=null){
				BeneficaryAccount beneAccountObj=new BeneficaryAccount();
				beneAccountObj.setBeneficaryAccountSeqId(getBeneficiaryAccountSeqId());
				ratePlaceOrderObj.setAccountSeqquenceId(beneAccountObj );
			}
			if(beneficaryMasterSeqId!=null)
			{
				BeneficaryMaster beneMasterObj=new BeneficaryMaster();
				beneMasterObj.setBeneficaryMasterSeqId(beneficaryMasterSeqId);
				ratePlaceOrderObj.setBeneficiaryMasterId(beneMasterObj);
			}
			if(getRequestCurrency()!=null)
			{
				ratePlaceOrderObj.setRequestCurrencyId(getRequestCurrency());
			}

			ratePlaceOrderObj.setCountryBranchId(getCountryBranchId());

			ratePlaceOrderObj.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PLACEORDER));
			ratePlaceOrderObj.setDocumentId(getDocumentId());
			ratePlaceOrderObj.setDocumentNumber(docRefNumber);
			ratePlaceOrderObj.setDocumentFinanceYear(getFinaceYear());
			ratePlaceOrderObj.setCompanyId(session.getCompanyId());
			ratePlaceOrderObj.setApplicationCountryId(session.getCountryId());
			ratePlaceOrderObj.setCustomerreference(getCustomerRef());
			ratePlaceOrderObj.setValueDate(getValueDate());
			ratePlaceOrderObj.setAreaName(getAreaName());
			//ratePlaceOrderObj.setRemarks(getRemarks());

			if(getDestinationCurrency()!=null)
			{
				ratePlaceOrderObj.setDestinationCurrenyId(getDestinationCurrency());
			}


			iPlaceOnOrderCreationService.saveRecord(ratePlaceOrderObj);
			//setSaveVisiable(true);
			RequestContext.getCurrentInstance().execute("complete.show();");
			if(getEmailId() != null){
				String beneBankName=null;
				String brachName= null;
				//String custmerName=null;
				beneBankName=generalService.getBankName(getBeneficiaryBankId());
				brachName=gSMPlaceOrderRateFeedService.toFetchBranchName(getCountryBranchId());
				//custmerName=generalService.getCustomerNameCustomerId(getCustomerId());

				HashMap<String, String> inputValues=new HashMap<String, String>();
				SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				inputValues.put("EmailId", getEmailId());
				inputValues.put("RateOffered", "Rate Offered");
				inputValues.put("User", session.getUserName());
				inputValues.put("Name", getCustomerName() + " / " + getCustomerRef() );
				inputValues.put("beneName", getBeneficiaryName()==null ? "" : getBeneficiaryName());
				inputValues.put("Bank", beneBankName);
				inputValues.put("Branch Name", brachName);

				String curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(getRequestCurrency());
				if(curQtyName != null){
					inputValues.put("Amount", getFcOrLocalAmount()+" - "+curQtyName);
				}

				inputValues.put("Craeted By", session.getUserName());
				inputValues.put("Created Date", format.format(new Date()).toString());
				//RequestContext.getCurrentInstance().execute("complete.show();");
				List<ApplicationSetup> lstApplicationSetup=iPersonalRemittanceService.getEmailFromAppSetup(session.getCompanyId(), session.getCountryId());

				apllicationMailer1.sendMailToGSMForPlaceOrder(lstApplicationSetup, inputValues);
			}


		}catch(Exception e){
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

	}

	public BigDecimal getDocumentSerialIdNumber(String processIn) {

		String documentSerialId="0";
		BigDecimal docId = BigDecimal.ZERO;
		try {
			
			List<Document> lstdoc = generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PLACEORDER),new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"));
			
			if(lstdoc != null && lstdoc.size() != 0){
				
				docId = lstdoc.get(0).getDocumentID();
				
				if(docId != null && docId.compareTo(BigDecimal.ZERO) != 0){
					
					setDocumentId(docId);
					
					HashMap<String, String> outPutValues= generalService.getNextDocumentRefNumber(session.getCountryId().intValue() , session.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_PLACEORDER) , getFinaceYear().intValue(),processIn,session.getCountryBranchCode());

					//String proceErrorFlag=outPutValues.get("PROCE_ERORRFLAG");
					String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
					if(proceErrorMsg!=null)
					{
						//setBooDocVisble(Boolean.TRUE);
						setErrorMsg(proceErrorMsg);
						RequestContext.getCurrentInstance().execute("error.show();");
						return BigDecimal.ZERO;
					}else if(outPutValues.get("DOCNO") !=null && new BigDecimal(outPutValues.get("DOCNO")).compareTo(BigDecimal.ZERO)==0){
						//setBooDocVisble(Boolean.TRUE);
						setErrorMsg(WarningHandler.showWarningMessage("lbl.docZero", new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1")));
						RequestContext.getCurrentInstance().execute("error.show();");
						return BigDecimal.ZERO;
					}else
					{
						//setBooDocVisble(Boolean.FALSE);
						documentSerialId=outPutValues.get("DOCNO");
					}
				}
			}
		} catch (NumberFormatException | AMGException e) {
			//setBooDocVisble(Boolean.TRUE);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return BigDecimal.ZERO;
		}

		return new BigDecimal(documentSerialId);
	}

	public void saveOkClick1(){

		try {
			if(getPersonalRemitDatatable() != null){
				// back to personal remittance
				clearAll();

				PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
				objectPersonalRemittance.personalRemittanceBackFromBene(null);
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");

			}else{
				clearAll();
			}
		} catch (Exception e) {
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

	}

	// getters and setters
	public BigDecimal getBeneficiaryCountryId() {
		return beneficiaryCountryId;
	}
	public void setBeneficiaryCountryId(BigDecimal beneficiaryCountryId) {
		this.beneficiaryCountryId = beneficiaryCountryId;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

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

	public BigDecimal getServiceGroupId() {
		return serviceGroupId;
	}
	public void setServiceGroupId(BigDecimal serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}

	public String getServiceGroupCode() {
		return serviceGroupCode;
	}
	public void setServiceGroupCode(String serviceGroupCode) {
		this.serviceGroupCode = serviceGroupCode;
	}

	public String getServiceGroupName() {
		return serviceGroupName;
	}
	public void setServiceGroupName(String serviceGroupName) {
		this.serviceGroupName = serviceGroupName;
	}

	public BigDecimal getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(BigDecimal beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}
	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}

	public BigDecimal getBeneficiaryAgentBankId() {
		return beneficiaryAgentBankId;
	}
	public void setBeneficiaryAgentBankId(BigDecimal beneficiaryAgentBankId) {
		this.beneficiaryAgentBankId = beneficiaryAgentBankId;
	}

	public BigDecimal getBeneficiaryAgentBankBranchId() {
		return beneficiaryAgentBankBranchId;
	}
	public void setBeneficiaryAgentBankBranchId(BigDecimal beneficiaryAgentBankBranchId) {
		this.beneficiaryAgentBankBranchId = beneficiaryAgentBankBranchId;
	}

	public String getBeneficiaryAgentBankBranchName() {
		return beneficiaryAgentBankBranchName;
	}
	public void setBeneficiaryAgentBankBranchName(String beneficiaryAgentBankBranchName) {
		this.beneficiaryAgentBankBranchName = beneficiaryAgentBankBranchName;
	}

	public String getBeneficiaryAgentBankName() {
		return beneficiaryAgentBankName;
	}
	public void setBeneficiaryAgentBankName(String beneficiaryAgentBankName) {
		this.beneficiaryAgentBankName = beneficiaryAgentBankName;
	}

	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}
	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}

	public BigDecimal getBeneficiaryAccountSeqId() {
		return beneficiaryAccountSeqId;
	}
	public void setBeneficiaryAccountSeqId(BigDecimal beneficiaryAccountSeqId) {
		this.beneficiaryAccountSeqId = beneficiaryAccountSeqId;
	}

	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}
	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	public BigDecimal getRequestCurrency() {
		return requestCurrency;
	}
	public void setRequestCurrency(BigDecimal requestCurrency) {
		this.requestCurrency = requestCurrency;
	}

	public BigDecimal getDestinationCurrency() {
		return destinationCurrency;
	}
	public void setDestinationCurrency(BigDecimal destinationCurrency) {
		this.destinationCurrency = destinationCurrency;
	}

	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public BigDecimal getFcOrLocalAmount() {
		return fcOrLocalAmount;
	}
	public void setFcOrLocalAmount(BigDecimal fcOrLocalAmount) {
		this.fcOrLocalAmount = fcOrLocalAmount;
	}

	public BigDecimal getBeneficiaryCurrencyId() {
		return beneficiaryCurrencyId;
	}
	public void setBeneficiaryCurrencyId(BigDecimal beneficiaryCurrencyId) {
		this.beneficiaryCurrencyId = beneficiaryCurrencyId;
	}

	public String getBeneficiaryCurrencyName() {
		return beneficiaryCurrencyName;
	}
	public void setBeneficiaryCurrencyName(String beneficiaryCurrencyName) {
		this.beneficiaryCurrencyName = beneficiaryCurrencyName;
	}

	public String getCurrencyQuoteName() {
		return currencyQuoteName;
	}
	public void setCurrencyQuoteName(String currencyQuoteName) {
		this.currencyQuoteName = currencyQuoteName;
	}

	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}
	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}

	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Boolean getBooRenderSingleBank() {
		return booRenderSingleBank;
	}
	public void setBooRenderSingleBank(Boolean booRenderSingleBank) {
		this.booRenderSingleBank = booRenderSingleBank;
	}

	public Boolean getBooRenderMultipleBank() {
		return booRenderMultipleBank;
	}
	public void setBooRenderMultipleBank(Boolean booRenderMultipleBank) {
		this.booRenderMultipleBank = booRenderMultipleBank;
	}

	public boolean isBankingChannelProducts() {
		return bankingChannelProducts;
	}
	public void setBankingChannelProducts(boolean bankingChannelProducts) {
		this.bankingChannelProducts = bankingChannelProducts;
	}

	public Boolean getBooRenderSingleAccount() {
		return booRenderSingleAccount;
	}
	public void setBooRenderSingleAccount(Boolean booRenderSingleAccount) {
		this.booRenderSingleAccount = booRenderSingleAccount;
	}

	public Boolean getBooRenderMultipleAccount() {
		return booRenderMultipleAccount;
	}
	public void setBooRenderMultipleAccount(Boolean booRenderMultipleAccount) {
		this.booRenderMultipleAccount = booRenderMultipleAccount;
	}

	public Boolean getBooRenderSingleAgentBank() {
		return booRenderSingleAgentBank;
	}
	public void setBooRenderSingleAgentBank(Boolean booRenderSingleAgentBank) {
		this.booRenderSingleAgentBank = booRenderSingleAgentBank;
	}

	public Boolean getBooRenderMultipleAgentBank() {
		return booRenderMultipleAgentBank;
	}
	public void setBooRenderMultipleAgentBank(Boolean booRenderMultipleAgentBank) {
		this.booRenderMultipleAgentBank = booRenderMultipleAgentBank;
	}

	public Boolean getBooRenderSingleAgentBankBranch() {
		return booRenderSingleAgentBankBranch;
	}
	public void setBooRenderSingleAgentBankBranch(Boolean booRenderSingleAgentBankBranch) {
		this.booRenderSingleAgentBankBranch = booRenderSingleAgentBankBranch;
	}

	public Boolean getBooRenderMultipleAgentBankBranch() {
		return booRenderMultipleAgentBankBranch;
	}
	public void setBooRenderMultipleAgentBankBranch(Boolean booRenderMultipleAgentBankBranch) {
		this.booRenderMultipleAgentBankBranch = booRenderMultipleAgentBankBranch;
	}

	public BigDecimal getFinancialYearId() {
		return financialYearId;
	}
	public void setFinancialYearId(BigDecimal financialYearId) {
		this.financialYearId = financialYearId;
	}

	public BigDecimal getFinaceYear() {
		return finaceYear;
	}
	public void setFinaceYear(BigDecimal finaceYear) {
		this.finaceYear = finaceYear;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Boolean getPlaceOrderCheck() {
		return placeOrderCheck;
	}
	public void setPlaceOrderCheck(Boolean placeOrderCheck) {
		this.placeOrderCheck = placeOrderCheck;
	}

	public List<CountryMasterDesc> getLstCountry() {
		return lstCountry;
	}
	public void setLstCountry(List<CountryMasterDesc> lstCountry) {
		this.lstCountry = lstCountry;
	}

	public List<PopulateData> getAllServiceList() {
		return allServiceList;
	}
	public void setAllServiceList(List<PopulateData> allServiceList) {
		this.allServiceList = allServiceList;
	}

	public List<PopulateData> getBeneficiaryList() {
		return beneficiaryList;
	}
	public void setBeneficiaryList(List<PopulateData> beneficiaryList) {
		this.beneficiaryList = beneficiaryList;
	}

	public List<PopulateDataWithCode> getLstBanks() {
		return lstBanks;
	}
	public void setLstBanks(List<PopulateDataWithCode> lstBanks) {
		this.lstBanks = lstBanks;
	}

	public List<PopulateDataWithCode> getLstAgentBanks() {
		return lstAgentBanks;
	}
	public void setLstAgentBanks(List<PopulateDataWithCode> lstAgentBanks) {
		this.lstAgentBanks = lstAgentBanks;
	}

	public List<PopulateDataWithCode> getLstAgentBankBranch() {
		return lstAgentBankBranch;
	}
	public void setLstAgentBankBranch(List<PopulateDataWithCode> lstAgentBankBranch) {
		this.lstAgentBankBranch = lstAgentBankBranch;
	}

	public List<PopulateData> getLstAccountNumber() {
		return lstAccountNumber;
	}
	public void setLstAccountNumber(List<PopulateData> lstAccountNumber) {
		this.lstAccountNumber = lstAccountNumber;
	}

	public List<ViewArea> getLstViewArea() {
		return lstViewArea;
	}
	public void setLstViewArea(List<ViewArea> lstViewArea) {
		this.lstViewArea = lstViewArea;
	}

	public List<PopulateData> getLstRequestCurrency() {
		return lstRequestCurrency;
	}
	public void setLstRequestCurrency(List<PopulateData> lstRequestCurrency) {
		this.lstRequestCurrency = lstRequestCurrency;
	}

	public List<PopulateData> getLstDestinationCurrency() {
		return lstDestinationCurrency;
	}
	public void setLstDestinationCurrency(List<PopulateData> lstDestinationCurrency) {
		this.lstDestinationCurrency = lstDestinationCurrency;
	}

	public Boolean getEnableDesCurrency() {
		return enableDesCurrency;
	}
	public void setEnableDesCurrency(Boolean enableDesCurrency) {
		this.enableDesCurrency = enableDesCurrency;
	}

	public PersonalRemmitanceBeneficaryDataTable getPersonalRemitDatatable() {
		return personalRemitDatatable;
	}
	public void setPersonalRemitDatatable(PersonalRemmitanceBeneficaryDataTable personalRemitDatatable) {
		this.personalRemitDatatable = personalRemitDatatable;
	}

	/*public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}*/

	public Boolean getBooRenderPlaceOrderFromRemit() {
		return booRenderPlaceOrderFromRemit;
	}
	public void setBooRenderPlaceOrderFromRemit(Boolean booRenderPlaceOrderFromRemit) {
		this.booRenderPlaceOrderFromRemit = booRenderPlaceOrderFromRemit;
	}
	
}
