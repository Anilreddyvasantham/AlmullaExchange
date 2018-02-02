package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.model.AlternateChannels;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.DebitAutendicationView;
import com.amg.exchange.remittance.model.ServiceProviderView;
import com.amg.exchange.remittance.service.IMobileAppVivaService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;


@Component("mobileAppVivaServiceBean")
@Scope("session")
public class MobileAppVivaServiceBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private BigDecimal customerId;
	private BigDecimal customerrefno;
	private String customerName;
	private String customerArbicName;
	private String customerLocalMobile;
	private String errorMessage;
	private String availOnLineService;
	private String availMobileAppService;
	private String availKioskService;
	
	private BigDecimal serviceProviderId;
	private BigDecimal beneCountryId;
	private String beneName;
	private BigDecimal beneBankId;
	private BigDecimal beneBranchId;
	private BigDecimal beneBankAccId;
	private BigDecimal fixedAmount;
	private String currencyQuote;
	private BigDecimal languageId;
	private BigDecimal otpNo;
	private String amlAuthorizedBy;
	private List<DebitAutendicationView> empllist;
	private String amlAuthorizedPassword;
	private String beneficiaryBankName;
	private String accountNumber;
	private BigDecimal accountSequenceId;
	private String beneficiaryBranch;
	private String serviceProviderName;
	private String availServiceProvider;
	
	
	private Boolean booRenderServiceProviderPanel;
	private Boolean booRenderDTServiceProviderPanel;
	private Boolean manualSelection;
	private Boolean booManualAuthorizedPanel;
	private Boolean booRenderMultipleBank;
	private Boolean booRenderSingleBank;
	private Boolean booRenderBnkAccNoList;
	private Boolean booRenderBnkAccNo;
	private Boolean booRenderMultipleBranch;
	private Boolean booRenderSingleBranch;
	private Boolean booRenderBenDeailsService;
	private Boolean otpSelection;
	private Boolean booOTPAuthorizedPanel;
	private Boolean booOtpManualCheckBox;
	
	private List<PopulateData>  allBeneCountryList;
	private List<PopulateData>  beneficiaryList;
	private List<PopulateData> lstBanks ;
	private List<PopulateData>  lstAccountNumber;
	private List<PopulateData> lstBranch;
	List<AlternateChannelDataTable> lstAlerChannelDt;
	
	private List<LanguageType> lstLanguageTypes;
	private List<ServiceProviderView> lstServiceProviderView;

	
	SessionStateManage session= new SessionStateManage();
	@Autowired
	ICustomerRegistrationBranchService<T> iCustomerRegistrationBranchService;
	
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	
	@Autowired
	IMobileAppVivaService<T> iMobileAppVivaService;
	@Autowired
	IGeneralService<T> generalService;
	
	/*@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;*/
	@Autowired
	IBankBranchDetailsService<T> bankBranchDetailsService;
	
	
	HashMap<BigDecimal, String> beneCountryMap= new HashMap<BigDecimal, String>();
	HashMap<BigDecimal, String> beneBankMap= new HashMap<BigDecimal, String>();
	HashMap<BigDecimal, String> beneBankBranchMap= new HashMap<BigDecimal, String>();
			
	HashMap<BigDecimal, String> beneAccountMap= new HashMap<BigDecimal, String>();
	
	
	
	public List<ServiceProviderView> getLstServiceProviderView() {
		return lstServiceProviderView;
	}
	public void setLstServiceProviderView(
			List<ServiceProviderView> lstServiceProviderView) {
		this.lstServiceProviderView = lstServiceProviderView;
	}
	public String getAvailServiceProvider() {
		return availServiceProvider;
	}
	public void setAvailServiceProvider(String availServiceProvider) {
		this.availServiceProvider = availServiceProvider;
	}
	public Boolean getBooOtpManualCheckBox() {
		return booOtpManualCheckBox;
	}
	public void setBooOtpManualCheckBox(Boolean booOtpManualCheckBox) {
		this.booOtpManualCheckBox = booOtpManualCheckBox;
	}
	public Boolean getOtpSelection() {
		return otpSelection;
	}
	public void setOtpSelection(Boolean otpSelection) {
		this.otpSelection = otpSelection;
	}
	public Boolean getBooOTPAuthorizedPanel() {
		return booOTPAuthorizedPanel;
	}
	public void setBooOTPAuthorizedPanel(Boolean booOTPAuthorizedPanel) {
		this.booOTPAuthorizedPanel = booOTPAuthorizedPanel;
	}
	public List<AlternateChannelDataTable> getLstAlerChannelDt() {
		return lstAlerChannelDt;
	}
	public void setLstAlerChannelDt(List<AlternateChannelDataTable> lstAlerChannelDt) {
		this.lstAlerChannelDt = lstAlerChannelDt;
	}
	public Boolean getBooRenderBenDeailsService() {
		return booRenderBenDeailsService;
	}
	public void setBooRenderBenDeailsService(Boolean booRenderBenDeailsService) {
		this.booRenderBenDeailsService = booRenderBenDeailsService;
	}
	public String getServiceProviderName() {
		return serviceProviderName;
	}
	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}
	public List<LanguageType> getLstLanguageTypes() {
		return lstLanguageTypes;
	}
	public void setLstLanguageTypes(List<LanguageType> lstLanguageTypes) {
		this.lstLanguageTypes = lstLanguageTypes;
	}

	public Boolean getBooRenderSingleBranch() {
		return booRenderSingleBranch;
	}

	public void setBooRenderSingleBranch(Boolean booRenderSingleBranch) {
		this.booRenderSingleBranch = booRenderSingleBranch;
	}

	public String getBeneficiaryBranch() {
		return beneficiaryBranch;
	}

	public void setBeneficiaryBranch(String beneficiaryBranch) {
		this.beneficiaryBranch = beneficiaryBranch;
	}

	public Boolean getBooRenderMultipleBranch() {
		return booRenderMultipleBranch;
	}

	public void setBooRenderMultipleBranch(Boolean booRenderMultipleBranch) {
		this.booRenderMultipleBranch = booRenderMultipleBranch;
	}

	
	public List<PopulateData> getLstBranch() {
		return lstBranch;
	}

	public void setLstBranch(List<PopulateData> lstBranch) {
		this.lstBranch = lstBranch;
	}

	public BigDecimal getAccountSequenceId() {
		return accountSequenceId;
	}

	public void setAccountSequenceId(BigDecimal accountSequenceId) {
		this.accountSequenceId = accountSequenceId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Boolean getBooRenderBnkAccNoList() {
		return booRenderBnkAccNoList;
	}

	public void setBooRenderBnkAccNoList(Boolean booRenderBnkAccNoList) {
		this.booRenderBnkAccNoList = booRenderBnkAccNoList;
	}

	public Boolean getBooRenderBnkAccNo() {
		return booRenderBnkAccNo;
	}

	public void setBooRenderBnkAccNo(Boolean booRenderBnkAccNo) {
		this.booRenderBnkAccNo = booRenderBnkAccNo;
	}

	public List<PopulateData> getLstAccountNumber() {
		return lstAccountNumber;
	}

	public void setLstAccountNumber(List<PopulateData> lstAccountNumber) {
		this.lstAccountNumber = lstAccountNumber;
	}

	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}

	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}

	public Boolean getBooRenderMultipleBank() {
		return booRenderMultipleBank;
	}

	public void setBooRenderMultipleBank(Boolean booRenderMultipleBank) {
		this.booRenderMultipleBank = booRenderMultipleBank;
	}

	public Boolean getBooRenderSingleBank() {
		return booRenderSingleBank;
	}

	public void setBooRenderSingleBank(Boolean booRenderSingleBank) {
		this.booRenderSingleBank = booRenderSingleBank;
	}

	public List<PopulateData> getLstBanks() {
		return lstBanks;
	}

	public void setLstBanks(List<PopulateData> lstBanks) {
		this.lstBanks = lstBanks;
	}

	public List<PopulateData> getBeneficiaryList() {
		return beneficiaryList;
	}

	public void setBeneficiaryList(List<PopulateData> beneficiaryList) {
		this.beneficiaryList = beneficiaryList;
	}

	public BigDecimal getCustomerrefno() {
		return customerrefno;
	}

	public void setCustomerrefno(BigDecimal customerrefno) {
		this.customerrefno = customerrefno;
	}

	public List<PopulateData> getAllBeneCountryList() {
		return allBeneCountryList;
	}

	public void setAllBeneCountryList(List<PopulateData> allBeneCountryList) {
		this.allBeneCountryList = allBeneCountryList;
	}

	public BigDecimal getOtpNo() {
		return otpNo;
	}

	public void setOtpNo(BigDecimal otpNo) {
		this.otpNo = otpNo;
	}

	public String getAmlAuthorizedBy() {
		return amlAuthorizedBy;
	}

	public void setAmlAuthorizedBy(String amlAuthorizedBy) {
		this.amlAuthorizedBy = amlAuthorizedBy;
	}

	public List<DebitAutendicationView> getEmpllist() {
		return empllist;
	}

	public void setEmpllist(List<DebitAutendicationView> empllist) {
		this.empllist = empllist;
	}

	public String getAmlAuthorizedPassword() {
		return amlAuthorizedPassword;
	}

	public void setAmlAuthorizedPassword(String amlAuthorizedPassword) {
		this.amlAuthorizedPassword = amlAuthorizedPassword;
	}

	public BigDecimal getServiceProviderId() {
		return serviceProviderId;
	}

	public void setServiceProviderId(BigDecimal serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}

	public BigDecimal getBeneCountryId() {
		return beneCountryId;
	}

	public void setBeneCountryId(BigDecimal beneCountryId) {
		this.beneCountryId = beneCountryId;
	}

	public String getBeneName() {
		return beneName;
	}
	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}

	public BigDecimal getBeneBankId() {
		return beneBankId;
	}
	public void setBeneBankId(BigDecimal beneBankId) {
		this.beneBankId = beneBankId;
	}

	public BigDecimal getBeneBranchId() {
		return beneBranchId;
	}

	public void setBeneBranchId(BigDecimal beneBranchId) {
		this.beneBranchId = beneBranchId;
	}

	public BigDecimal getBeneBankAccId() {
		return beneBankAccId;
	}

	public void setBeneBankAccId(BigDecimal beneBankAccId) {
		this.beneBankAccId = beneBankAccId;
	}

	public BigDecimal getFixedAmount() {
		return fixedAmount;
	}
	public void setFixedAmount(BigDecimal fixedAmount) {
		this.fixedAmount = fixedAmount;
	}
	
	public String getCurrencyQuote() {
		return currencyQuote;
	}
	public void setCurrencyQuote(String currencyQuote) {
		this.currencyQuote = currencyQuote;
	}
	
	public BigDecimal getLanguageId() {
		return languageId;
	}
	public void setLanguageId(BigDecimal languageId) {
		this.languageId = languageId;
	}
	
	public Boolean getBooManualAuthorizedPanel() {
		return booManualAuthorizedPanel;
	}

	public void setBooManualAuthorizedPanel(Boolean booManualAuthorizedPanel) {
		this.booManualAuthorizedPanel = booManualAuthorizedPanel;
	}
	public Boolean getManualSelection() {
		return manualSelection;
	}

	public void setManualSelection(Boolean manualSelection) {
		this.manualSelection = manualSelection;
	}

	public Boolean getBooRenderServiceProviderPanel() {
		return booRenderServiceProviderPanel;
	}

	public void setBooRenderServiceProviderPanel(
			Boolean booRenderServiceProviderPanel) {
		this.booRenderServiceProviderPanel = booRenderServiceProviderPanel;
	}

	public Boolean getBooRenderDTServiceProviderPanel() {
		return booRenderDTServiceProviderPanel;
	}

	public void setBooRenderDTServiceProviderPanel(
			Boolean booRenderDTServiceProviderPanel) {
		this.booRenderDTServiceProviderPanel = booRenderDTServiceProviderPanel;
	}

	public String getAvailOnLineService() {
		return availOnLineService;
	}

	public void setAvailOnLineService(String availOnLineService) {
		this.availOnLineService = availOnLineService;
	}

	public String getAvailMobileAppService() {
		return availMobileAppService;
	}

	public void setAvailMobileAppService(String availMobileAppService) {
		this.availMobileAppService = availMobileAppService;
	}

	public String getAvailKioskService() {
		return availKioskService;
	}

	public void setAvailKioskService(String availKioskService) {
		this.availKioskService = availKioskService;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerArbicName() {
		return customerArbicName;
	}

	public void setCustomerArbicName(String customerArbicName) {
		this.customerArbicName = customerArbicName;
	}

	public String getCustomerLocalMobile() {
		return customerLocalMobile;
	}
	public void setCustomerLocalMobile(String customerLocalMobile) {
		this.customerLocalMobile = customerLocalMobile;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public void pageNavigateMobileAppVivaService()
	{		
		try {
			setCustomerLocalMobile(getLocalMobleNo()==null ? "" : getLocalMobleNo());
			loadAvialServices();
			//loadServiceProvider();
			String currencyQuoteValue = generalService.getCurrencyQuote(new BigDecimal(session.getCurrencyId()));
			setCurrencyQuote(currencyQuoteValue);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/MobileAppVivaService.xhtml");
		} catch (IOException e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
	}
	
	public void resetVlaues()
	{
		setCustomerName(null);
		setCustomerArbicName(null);
		setCustomerLocalMobile(null);
		setBooRenderServiceProviderPanel(false);
		setBooRenderDTServiceProviderPanel(false);
		setBooManualAuthorizedPanel(false);
		setManualSelection(false);
		setBooRenderBenDeailsService(false);
	}
	public String getLocalMobleNo()
	{
		String customerMobileNo = null;
		try {
			customerMobileNo=iCustomerRegistrationBranchService.getMobileNoBasedOnCustomerId(getCustomerId());
		} catch (AMGException e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
		return customerMobileNo;
	}

	public void callMobileAppService()
	{
		try{
			resetServiceProvider();
			setServiceProviderName(null);
			if(getAvailServiceProvider()!=null && getAvailServiceProvider().equalsIgnoreCase(Constants.Yes))
			{
				boolean rtnvalue=loadServiceProvider();
				if(!rtnvalue)
				{
					RequestContext.getCurrentInstance().execute("serviceProveeiNoT.show();");
					return;
				}
				List<AlternateChannels> lstAlternateChannels= new ArrayList<AlternateChannels>();
				
				lstAlternateChannels=iMobileAppVivaService.findExistSericeProvider(getCustomerId());
				if(lstAlternateChannels!=null && lstAlternateChannels.size()>0)
				{
					addBeneDetailsToDataTable(lstAlternateChannels);
					setBooRenderBenDeailsService(false);
					setBooRenderDTServiceProviderPanel(true);
					setBooRenderServiceProviderPanel(false);
					
					
				}else
				{
					
					setBooRenderDTServiceProviderPanel(false);
					setBooRenderServiceProviderPanel(true);
					//setBooRenderBenDeailsService(false);
					populateBeneFiciaryCountry();
				}
				
			}else
			{
				setBooRenderServiceProviderPanel(false);
				setBooManualAuthorizedPanel(false);
				setBooRenderDTServiceProviderPanel(false);
				
			}
		}catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
		
		
		
		
	}
	
	public void callBeneDetails()
	{
		try{
			resetServiceProvider();
			if(getServiceProviderName()!=null)
			{
				setBooRenderBenDeailsService(true);
				setBooRenderDTServiceProviderPanel(false);
				setBooRenderServiceProviderPanel(true);
				setBooOtpManualCheckBox(true);
				populateBeneFiciaryCountry();
				
			}else
			{
				setBooRenderDTServiceProviderPanel(false);
				setBooRenderServiceProviderPanel(true);
				setBooOtpManualCheckBox(true);
				populateBeneFiciaryCountry();
			}
		}catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
		
			
	}
	public void addBeneDetailsToDataTable(List<AlternateChannels> lstAlternateChannels)
	{
		try{
			List<AlternateChannelDataTable> lstAlerChannelDt= new ArrayList<AlternateChannelDataTable>();
			
			for(AlternateChannels alternateChannels :lstAlternateChannels)
			{
				List<BenificiaryListView> lstBenificiaryListView=iMobileAppVivaService.getBeneficirarylist(alternateChannels.getBeneMasterId().getBeneficaryMasterSeqId(), alternateChannels.getBeneAccountId().getBeneficaryAccountSeqId(), alternateChannels.getBeneRelationId().getBeneficaryRelationshipId());
				AlternateChannelDataTable alternateChannelDataTable = new AlternateChannelDataTable();
				alternateChannelDataTable.setAlternateCannelsId(alternateChannels.getAlternateCheanelId());
				alternateChannelDataTable.setBeneCountryName(generalService.getCountryName(session.getLanguageId() ,alternateChannels.getBeneCountryId().getCountryId()));
				if(lstBenificiaryListView!=null && lstBenificiaryListView.size()>0)
				{
					BenificiaryListView benificiaryListView =lstBenificiaryListView.get(0);
					alternateChannelDataTable.setBeneName(benificiaryListView.getBenificaryName());
					alternateChannelDataTable.setBeneAccountNumber(benificiaryListView.getBankAccountNumber());
				}
				
				alternateChannelDataTable.setBeneBank(generalService.getBankName(alternateChannels.getBeneBankId().getBankId()));
				alternateChannelDataTable.setBeneBankBranch(bankBranchDetailsService.toFetchBranchName(alternateChannels.getBeneBranchId().getBankBranchId()));
				
				alternateChannelDataTable.setFixedAmount(alternateChannels.getFixedAmount());
				List<LanguageType> lstLanguageType=iMobileAppVivaService.getLanguageType(alternateChannels.getBeneLanguageId().getLanguageId()) ;
				if(lstLanguageType!=null && lstLanguageType.size()>0)
				{
					LanguageType languageType=lstLanguageType.get(0);
					alternateChannelDataTable.setLanguageName(languageType.getLanguageName());
					
				}
				alternateChannelDataTable.setServiceProviderName(alternateChannels.getServiceProviderId());
				lstAlerChannelDt.add(alternateChannelDataTable);
				
			}
			setLstAlerChannelDt(lstAlerChannelDt);
		}catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
		
	}
	public void selectManualSelect()
	{
		try {
			
			if(getManualSelection())
			{
				boolean mandatoryCheck= checkManadatoryFields();
				if(mandatoryCheck)
				{
					setManualSelection(false);
					return;
				}
				if(getOtpSelection())
				{
					//RequestContext.getCurrentInstance().execute("deselectOpt.show();");
					//setManualSelection(false);
					//return;
					setOtpSelection(false);
					setBooOTPAuthorizedPanel(false);
				}
				setBooManualAuthorizedPanel(true);
				loadManualAuthorizedDetails();
			}else
			{
				setBooManualAuthorizedPanel(false);
			}
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
		
	}
	
	public void selectOtpSelect()
	{
		 try {
				if(getOtpSelection())
				{
					if(getManualSelection())
					{
						//RequestContext.getCurrentInstance().execute("deselectManual.show();");
						//setOtpSelection(false);
						//return;
						setManualSelection(false);
						setBooManualAuthorizedPanel(false);
					}
					List<AlternateChannels> lstAlternateChannels=iMobileAppVivaService.checkDuplicateSericeProviderBankBeneAcc(getCustomerId(), getBeneCountryId(), getBeneBankId(), getAccountNumber());
					if(lstAlternateChannels!=null && lstAlternateChannels.size()>0)
					{
						RequestContext.getCurrentInstance().execute("duplicateCheck.show();");
						return;
					}
					boolean mandatoryCheck= checkManadatoryFields();
					if(mandatoryCheck)
					{
						setOtpSelection(false);
						return;
					}
					callOTPProcedure();
					setBooOTPAuthorizedPanel(true);
					
				}else
				{
					setBooOTPAuthorizedPanel(false);
				}
			} catch (Exception e) {
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("errorPage.show();");
			}
	}
	
	private void resetServiceProvider()
	{
		setServiceProviderId(null);
		setBeneCountryId(null);
		setBeneName(null);
		setBeneBankId(null);
		setBeneBranchId(null);
		setBeneBankAccId(null);
		setFixedAmount(null);
		setLanguageId(null);
		setOtpNo(null);
		setAmlAuthorizedBy(null);
		setEmpllist(null);
		setAmlAuthorizedPassword(null);
		setManualSelection(false);
		setBooRenderSingleBank(false);
		setBooRenderMultipleBank(true);
		setBooRenderBnkAccNoList(true);
		setBooRenderBnkAccNo(false);
		setBooRenderMultipleBranch(true);
		setBooRenderSingleBranch(false);
		setBooRenderBenDeailsService(false);
		setBooOTPAuthorizedPanel(false);
		setOtpSelection(false);
		setBooOTPAuthorizedPanel(false);
		setBooManualAuthorizedPanel(false);
		setBooOtpManualCheckBox(false);
		setBeneficiaryBranch(null);
		setAccountNumber(null);
		setAccountSequenceId(null);
		//setAvailServiceProvider(null);
		//setServiceProviderName(null);
		
	}
	public void addAnotherServiceProvider()
	{
		RequestContext.getCurrentInstance().execute("antoherService.show();");
	}
	public void addServiceprovider()
	{
		List<AlternateChannelDataTable> lstAlternateChannelDataTable=getLstAlerChannelDt();
		if(lstAlternateChannelDataTable!=null && lstAlternateChannelDataTable.size()>0)
		{
			AlternateChannelDataTable alternateChannelDataTable =lstAlternateChannelDataTable.get(0);
			
			iMobileAppVivaService.deactivateExistServiceProvider(alternateChannelDataTable.getAlternateCannelsId(),session.getUserName());
		}
		setBooRenderServiceProviderPanel(true);
		setBooRenderDTServiceProviderPanel(false);
		setLstAlerChannelDt(null);
		loadServiceProvider();
		
	}
	
	private void populateBeneFiciaryCountry()
	{
		try{
			setBeneCountryId(null);
			//backFromBenificaryStatusPanel();
			List<PopulateData> countryList = iMobileAppVivaService.getCountryList(getCustomerId(), session.getLanguageId());
			//List<PopulateData>  beneCountryList = new ArrayList<PopulateData>();
			if(countryList != null && countryList.size() > 1){
				
				//beneCountryList.addAll(countryList);
				setAllBeneCountryList(countryList);
				loadBeneCountry(countryList);

			}else if(countryList != null && countryList.size() == 1) {
				
				setAllBeneCountryList(countryList);
				PopulateData populateData=countryList.get(0);
				setBeneCountryId(populateData.getPopulateId());
				populateBeneficiary();
				loadBeneCountry(countryList);
				//populateCustomerDetailsFromBeneRelation();
			}
			toFetchAllLanguages();
		}catch(Exception e){
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
		
	}
	public void loadBeneCountry(List<PopulateData> countryList)
	{
		if(beneCountryMap!=null)
		{
			beneCountryMap.clear();
			for(PopulateData populateData:countryList)
			{
				beneCountryMap.put(populateData.getPopulateId(), populateData.getPopulateCode()+" - "+populateData.getPopulateName());
			}
			
		}
		
		
	}
	public void populateBeneficiary()
	{
		try{
			setBeneName(null);
			setBeneficiaryList(null);
			setBeneBankId(null);
			setLstBanks(null);
			setBeneficiaryBankName(null);
			setBeneBankAccId(null);
			setAccountNumber(null);
			setLstAccountNumber(null);
			setAccountSequenceId(null);
			setBeneBranchId(null);
			setBeneficiaryBranch(null);
			
			List<PopulateData> lstBeneName=iMobileAppVivaService.getBenNameList(getCustomerId(),getBeneCountryId());

			setBeneficiaryList(lstBeneName);
		}catch(Exception e){
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
		
	}
	
	public void populateBanks()
	{
		try{
			if(getBeneName()!=null)
			{
				
				setBeneBankId(null);
				setLstBanks(null);
				setBeneficiaryBankName(null);
				setBeneBankAccId(null);
				setAccountNumber(null);
				setLstAccountNumber(null);
				setAccountSequenceId(null);
				setBeneBranchId(null);
				setBeneficiaryBranch(null);
				
				List<PopulateData> lstBeneBanks=iMobileAppVivaService.getBeneBankList(getCustomerId(), getBeneCountryId(), getBeneName());

				if(lstBeneBanks!=null && lstBeneBanks.size()==0)
				{
					RequestContext.getCurrentInstance().execute("bankNot.show();");
					return;
				}else if (lstBeneBanks!=null && lstBeneBanks.size()==1) {
					setBeneBankId(lstBeneBanks.get(0).getPopulateId());
					setBeneficiaryBankName(lstBeneBanks.get(0).getPopulateName());
					setBooRenderSingleBank(true);
					setBooRenderMultipleBank(false);
					loadBeneBank(lstBeneBanks);
					populateBeneBranch();
				}else if (lstBeneBanks!=null && lstBeneBanks.size() > 1){
				setLstBanks(lstBeneBanks);
				loadBeneBank(lstBeneBanks);
				setBooRenderSingleBank(false);
				setBooRenderMultipleBank(true);
				}
				
			}
			
		}catch(Exception e){
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}

	}
	public void loadBeneBank(List<PopulateData> lstBeneBanks)
	{
		if(beneBankMap!=null)
		{
			beneBankMap.clear();
			for(PopulateData populateData :lstBeneBanks)
			{
				beneBankMap.put(populateData.getPopulateId(), populateData.getPopulateCode()+" - "+populateData.getPopulateName());
			}
		}
		
	}
	public void populateAccountNumber()
	{
		
		try{
			setBeneBankAccId(null);
			setAccountNumber(null);
			setLstAccountNumber(null);
			List<PopulateData> accountNumberList= iMobileAppVivaService.getBeneAccountNoList(getCustomerId(),getBeneCountryId(), getBeneBankId(),getBeneName(),getBeneBranchId());
			/*if(accountNumberList!=null && accountNumberList.size()==0  )
			{
				RequestContext.getCurrentInstance().execute("accountNo.show();");
				return;
			}*/
			
			if(accountNumberList!=null && accountNumberList.size()==1)
			{
				setBooRenderBnkAccNoList(false);
				setBooRenderBnkAccNo(true);
				setAccountNumber(accountNumberList.get( 0).getPopulateCode());
				setAccountSequenceId(accountNumberList.get(0).getPopulateId());
				loadBeneAccountDeatils(accountNumberList);

			}else if(accountNumberList!=null && accountNumberList.size()>1){
				setBooRenderBnkAccNoList(true);
				setBooRenderBnkAccNo(false);
				setLstAccountNumber(accountNumberList);
				loadBeneAccountDeatils(accountNumberList);
			}
		}catch(Exception e){
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
		
	}
	public void loadBeneAccountDeatils(List<PopulateData> accountNumberList)
	{
		if(beneAccountMap!=null)
		{
			beneAccountMap.clear();
			for(PopulateData populateData :accountNumberList)
			{
				beneAccountMap.put(populateData.getPopulateId(), populateData.getPopulateCode());
			}
		}
		
	}
	public void populateOtherDetails()
	{
		try{
			
		}catch(Exception e){
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
	}
	
	public void populateBeneBranch()
	{
		try{
			setBeneBranchId(null);
			setBeneficiaryBranch(null);
			
			setLstBranch(null);
			
			setAccountNumber(null);
			setLstAccountNumber(null);
			setAccountSequenceId(null);
			
			List<PopulateData> beneBrabchList= iMobileAppVivaService.getBeneAccountNoList( getBeneBankId(),getCustomerId(),getBeneCountryId(),getBeneName());
			if(beneBrabchList!=null && beneBrabchList.size()==0  )
			{
				RequestContext.getCurrentInstance().execute("beneBranchNot.show();");
				return;
			}
			
			if(beneBrabchList!=null && beneBrabchList.size()==1)
			{
				setBooRenderMultipleBranch(false);
				setBooRenderSingleBranch(true);
				setBeneficiaryBranch(beneBrabchList.get( 0).getPopulateCode() +" - "+beneBrabchList.get( 0).getPopulateName());
				setBeneBranchId(beneBrabchList.get(0).getPopulateId());
				populateAccountNumber();
				

			}else if(beneBrabchList!=null && beneBrabchList.size()>1){
				setBooRenderMultipleBranch(true);
				setBooRenderSingleBranch(false);
				setLstBranch(beneBrabchList);
				
			}
		}catch(Exception e){
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
	}
	public void loadBeneBankBranch(List<PopulateData> beneBrabchList)
	{
		if(beneBankBranchMap!=null)
		{
			beneBankBranchMap.clear();
			for(PopulateData populateData:beneBrabchList)
			{
				beneBankBranchMap.put(populateData.getPopulateId(), populateData.getPopulateCode() +" - "+populateData.getPopulateName());
			}
		}
		
	}
	public void toFetchAllLanguages(){
		try{
			List<LanguageType> lstLanguageTypes=generalService.getAllLanguages(); 
			setLstLanguageTypes(lstLanguageTypes);
		}catch(Exception e){
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
	}
	
	public void callOTPProcedure()
	{
		
		try {
			String otpNoGenrate=createRandomOTP();
			iMobileAppVivaService.updateOTPDetails(getCustomerId(), otpNoGenrate, session.getUserName());
			
			BigDecimal languageId = session.getLanguageId()==null ? BigDecimal.ONE : session.getLanguageId();
			String otpSmsMessage=WarningHandler.showWarningMessage("lbl.smsOTPMessage", languageId);
			if(otpSmsMessage==null)
			{
				otpSmsMessage=WarningHandler.showWarningMessage("lbl.smsOTPMessage", BigDecimal.ONE);
			}
			String finalMessage=otpSmsMessage +" "+otpNoGenrate;
			
			HashMap<String, String> inputValues = new HashMap<String, String>();
			
			inputValues.put("P_SENDER_ID", "AlMulla EXC");
			inputValues.put("P_MOBILE", getCustomerLocalMobile());
			inputValues.put("P_MESSAGE", finalMessage);
			inputValues.put("P_APPLNAME", "");
			inputValues.put("P_AL1COD", "");
			inputValues.put("P_ACNTCOD", "");
			inputValues.put("P_CREATOR", "");
			inputValues.put("P_LANGUAGE", getLanguageId().toPlainString());
			inputValues.put("P_COMCOD", "");
			inputValues.put("P_DOCCOD", "");
			inputValues.put("P_DOCFYR", "");
			inputValues.put("P_DOCNO", "");
			
			HashMap<String, String> outputValues = iMobileAppVivaService.callOTPSendProcedure(inputValues);
		}catch (AMGException e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
	}
	
	
	public void mainSave()
	{
		try {
			
			if(getAvailOnLineService()==null)
			{
				RequestContext.getCurrentInstance().execute("onlineCheck.show()");
				return;
			}
			
			if(getAvailKioskService()==null)
			{
				RequestContext.getCurrentInstance().execute("kioskCheck.show()");
				return;
			}
			if(getAvailMobileAppService()==null)
			{
				RequestContext.getCurrentInstance().execute("mobileCheck.show()");
				return;
			}
			
			if(getAvailServiceProvider()==null)
			{
				RequestContext.getCurrentInstance().execute("serviceProviderCheck.show()");
				return;
			}else
			{
				if(getAvailServiceProvider().equalsIgnoreCase(Constants.Yes))
				{
					if(getBooRenderDTServiceProviderPanel()!=null && getBooRenderDTServiceProviderPanel())
					{
						if(getLstAlerChannelDt()!=null && getLstAlerChannelDt().size()>0)
						{
							// no save condition. One Beneficiary only allowed
						}else
						{
							if(getServiceProviderName()!=null)
							{
								boolean mandatoryCheck= checkManadatoryFields();
								if(mandatoryCheck)
								{
									return;
								}
								
								if(!getManualSelection() && !getOtpSelection() )
								{
									RequestContext.getCurrentInstance().execute("VerifiedMethod.show()");
									return;
								}
								
								if(getManualSelection())
								{
									boolean passwordVerify=authorizedDetailsPWVerification();
									if(!passwordVerify)
									{
										RequestContext.getCurrentInstance().execute("inValidLogin.show()");
										return;
									}
								}
								if(getOtpSelection())
								{
									if(getOtpNo()==null)
									{
										RequestContext.getCurrentInstance().execute("otpNoEmpty.show();");
										return;
									}
									
									String rtnMessage=iMobileAppVivaService.verifyOtpNo(getOtpNo(), getCustomerId(), session.getUserName());
									if(rtnMessage!=null)
									{
										if(rtnMessage.equalsIgnoreCase("FAIL"))
										{
											setOtpNo(null);
											RequestContext.getCurrentInstance().execute("failToUpdate.show();");
											return;
										}else if(rtnMessage.equalsIgnoreCase("OTPNOTMATCH"))
										{
											setOtpNo(null);
											RequestContext.getCurrentInstance().execute("otpNotMatch.show();");
											return;
										}
									}
								}
							}else
							{
								RequestContext.getCurrentInstance().execute("serviceProviderNameCheck.show();");
								return;
							}
						}
					}else if(getBooRenderServiceProviderPanel()!=null && getBooRenderServiceProviderPanel())
					{


						if(getServiceProviderName()!=null)
						{
							boolean mandatoryCheck= checkManadatoryFields();
							if(mandatoryCheck)
							{
								return;
							}
							
							if(!getManualSelection() && !getOtpSelection() )
							{
								RequestContext.getCurrentInstance().execute("VerifiedMethod.show()");
								return;
							}
							
							if(getManualSelection())
							{
								boolean passwordVerify=authorizedDetailsPWVerification();
								if(!passwordVerify)
								{
									RequestContext.getCurrentInstance().execute("inValidLogin.show()");
									return;
								}
							}
							if(getOtpSelection())
							{
								if(getOtpNo()==null)
								{
									RequestContext.getCurrentInstance().execute("otpNoEmpty.show();");
									return;
								}
								
								String rtnMessage=iMobileAppVivaService.verifyOtpNo(getOtpNo(), getCustomerId(), session.getUserName());
								if(rtnMessage!=null)
								{
									if(rtnMessage.equalsIgnoreCase("FAIL"))
									{
										setOtpNo(null);
										RequestContext.getCurrentInstance().execute("failToUpdate.show();");
										return;
									}else if(rtnMessage.equalsIgnoreCase("OTPNOTMATCH"))
									{
										setOtpNo(null);
										RequestContext.getCurrentInstance().execute("otpNotMatch.show();");
										return;
									}
								}
							}
						}else
						{
							RequestContext.getCurrentInstance().execute("serviceProviderNameCheck.show();");
							return;
						}
					
					
					}
					
					
				}
				
				
			}
			
			if(getLstAlerChannelDt()!=null && getLstAlerChannelDt().size()>0)
			{
				// no save condition. One Beneficiary only allowed
			}else{
				HashMap<String, AlternateChannels> saveMap= new HashMap<String, AlternateChannels>();

				HashMap<String, String> rtnMap=iMobileAppVivaService.getAvialServices(getCustomerId());
				String avialKiosk=null;
				String avialMobile=null;
				String avialOnline=null;
				String avialServiceProvider=null;
				if(rtnMap!=null)
				{
					avialKiosk=rtnMap.get("KIOSK")==null ? Constants.No:rtnMap.get("KIOSK");
					avialMobile=rtnMap.get("MOBILE")==null ? Constants.No:rtnMap.get("MOBILE");
					avialOnline=rtnMap.get("ONLINE")==null ? Constants.No:rtnMap.get("ONLINE") ;
					avialServiceProvider=rtnMap.get("SERVICEPROVIDER")==null ? Constants.No:rtnMap.get("SERVICEPROVIDER");
				}
				
				if(avialOnline!=null && !getAvailOnLineService().equalsIgnoreCase(avialOnline))
				{
					iMobileAppVivaService.checkExistRecordUpdateToDeativate(getCustomerId(), "ONLINE", session.getUserName());
					if(getAvailOnLineService()!=null && getAvailOnLineService().equalsIgnoreCase(Constants.Yes))
					{
						AlternateChannels alternateChannelsOnline= new AlternateChannels();
						
						Customer customer= new Customer();
						customer.setCustomerId(getCustomerId());
						alternateChannelsOnline.setCustomerId(customer);
						
						alternateChannelsOnline.setChannelId("ONLINE");
						
						alternateChannelsOnline.setIsActive(Constants.Yes);
						
						alternateChannelsOnline.setCreatedBy(session.getUserName());
						alternateChannelsOnline.setCreatedDate(new Date());
						saveMap.put("ONLINE", alternateChannelsOnline);
						
					}
				}
				
				if(avialKiosk!=null && !getAvailKioskService().equalsIgnoreCase(avialKiosk))
				{
					iMobileAppVivaService.checkExistRecordUpdateToDeativate(getCustomerId(), "KIOSK", session.getUserName());
					if(getAvailKioskService()!=null && getAvailKioskService().equalsIgnoreCase(Constants.Yes))
					{
						AlternateChannels alternateChannelsKiosk= new AlternateChannels();
						
						Customer customer= new Customer();
						customer.setCustomerId(getCustomerId());
						alternateChannelsKiosk.setCustomerId(customer);
						
						alternateChannelsKiosk.setChannelId("KIOSK");
						
						alternateChannelsKiosk.setIsActive(Constants.Yes);
						
						alternateChannelsKiosk.setCreatedBy(session.getUserName());
						alternateChannelsKiosk.setCreatedDate(new Date());
						saveMap.put("KIOSK", alternateChannelsKiosk);
						
					}
				}
				
				if(avialMobile!=null && !getAvailMobileAppService().equalsIgnoreCase(avialMobile))
				{
					iMobileAppVivaService.checkExistRecordUpdateToDeativate(getCustomerId(), "MOBILEAPP", session.getUserName());
					if(getAvailMobileAppService()!=null && getAvailMobileAppService().equalsIgnoreCase(Constants.Yes))
					{
						AlternateChannels alternateChannelsMobApp= new AlternateChannels();
						
						Customer customer= new Customer();
						customer.setCustomerId(getCustomerId());
						alternateChannelsMobApp.setCustomerId(customer);
						
						alternateChannelsMobApp.setChannelId("MOBILEAPP");
						
						alternateChannelsMobApp.setIsActive(Constants.Yes);
						
						alternateChannelsMobApp.setCreatedBy(session.getUserName());
						alternateChannelsMobApp.setCreatedDate(new Date());
						saveMap.put("MOBILEAPP", alternateChannelsMobApp);
						
					}
				}

				if(getAvailServiceProvider()!=null && getAvailServiceProvider().equalsIgnoreCase(Constants.Yes) && getServiceProviderName()!=null)
				{
					iMobileAppVivaService.checkExistRecordUpdateToDeativate(getCustomerId(), "MOBILE", session.getUserName());
					
					AlternateChannels alternateChannels= new AlternateChannels();
					
					alternateChannels.setChannelId("MOBILE");
					alternateChannels.setServiceProviderId(getServiceProviderName());
					
					CountryMaster countryMaster= new CountryMaster();
					countryMaster.setCountryId(getBeneCountryId());
					alternateChannels.setBeneCountryId(countryMaster);
					
					Customer customer= new Customer();
					customer.setCustomerId(getCustomerId());
					alternateChannels.setCustomerId(customer);
					
					if(getAccountNumber()==null)
					{
						String accNo=beneAccountMap.get(getAccountSequenceId());
						if(accNo != null && !accNo.equalsIgnoreCase("")){
							setAccountNumber(accNo);
						}
					}
					
					BeneficaryAccount beneficaryAccount=iMobileAppVivaService.getRelionShipDetails(getBeneCountryId(),  getBeneBankId(), getCustomerId(), getBeneBranchId(), getAccountNumber());
					
					List<BeneficaryRelationship> lstBeneficaryRelationship=iPersonalRemittanceService.isBenificaryRelationExist(beneficaryAccount.getBeneficaryMaster().getBeneficaryMasterSeqId(), beneficaryAccount.getBeneficaryAccountSeqId());
					
					BeneficaryMaster beneficaryMaster= new BeneficaryMaster();
					beneficaryMaster.setBeneficaryMasterSeqId(beneficaryAccount.getBeneficaryMaster().getBeneficaryMasterSeqId());
					alternateChannels.setBeneMasterId(beneficaryMaster);
					
					alternateChannels.setBeneAccountId(beneficaryAccount);
					
					if(lstBeneficaryRelationship!=null && lstBeneficaryRelationship.size()>0)
					{
						BeneficaryRelationship beneficaryRelationship=lstBeneficaryRelationship.get(0);
						alternateChannels.setBeneRelationId(beneficaryRelationship);
					}
					
					if(getManualSelection())
					{
						alternateChannels.setOtpVerificationMethod("M");
						alternateChannels.setOtpVerifiedBy(getAmlAuthorizedBy());
						alternateChannels.setOtpVerifiedDate(new Date());
					}
					if(getOtpSelection())
					{
						alternateChannels.setOtpVerificationMethod("O");
						alternateChannels.setOtpVerifiedBy(session.getUserName());
						alternateChannels.setOtpVerifiedDate(new Date());
					}
					BankMaster bankMaster= new BankMaster();
					bankMaster.setBankId(getBeneBankId());
					alternateChannels.setBeneBankId(bankMaster);
					
					BankBranch  bankBranch=new BankBranch();
					bankBranch.setBankBranchId(getBeneBranchId());
					alternateChannels.setBeneBranchId(bankBranch);
					
					LanguageType languageType= new LanguageType();
					languageType.setLanguageId(getLanguageId());
					alternateChannels.setBeneLanguageId(languageType);
					//alternateChannels.setAccountNumber(getAccountNumber());
					
					alternateChannels.setFixedAmount(getFixedAmount());
					//alternateChannels.setBeneName(getBeneName());
					
					alternateChannels.setIsActive(Constants.Yes);
					
					alternateChannels.setCreatedBy(session.getUserName());
					
					alternateChannels.setCreatedDate(new Date());
					
					saveMap.put("MOBILEAPPSERVICE", alternateChannels);
					
				}
				
				iMobileAppVivaService.saveAlternateChanlesDetails(saveMap, session.getUserName(), getCustomerId(), getAvailOnLineService(), getAvailKioskService(), getAvailMobileAppService(),getAvailServiceProvider());
				RequestContext.getCurrentInstance().execute("complete.show();");
			}
			
			
			
		}catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
		
		
		
	}
	
	public boolean authorizedDetailsPWVerification() {
	
		boolean passwordCheck=false;
		String userName = getAmlAuthorizedBy().trim();
		String password = getAmlAuthorizedPassword();
		
		List<DebitAutendicationView> lstEmpLogin = new ArrayList<DebitAutendicationView>();
		

		lstEmpLogin = 	iPersonalRemittanceService.getdebitAutendicationListByUserId(userName,password);

		if (lstEmpLogin.size() == 1) {
			passwordCheck=true;

		} else {
			passwordCheck=false;
			/*RequestContext.getCurrentInstance().execute("inValidLogin.show()");
			return;*/
		}
		return passwordCheck;
	}
	public void loadManualAuthorizedDetails()
	{
		try {
			setEmpllist(null);
			List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService.getdebitAutendicationList();

			setEmpllist(localEmpllist);
		}catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
		
	}
	
	public void checkOtpNo()
	{
		try {
			String rtnMessage=iMobileAppVivaService.verifyOtpNo(getOtpNo(), getCustomerId(), session.getUserName());
			if(rtnMessage!=null)
			{
				if(rtnMessage.equalsIgnoreCase("FAIL"))
				{
					setOtpNo(null);
					RequestContext.getCurrentInstance().execute("failToUpdate.show();");
				}else if(rtnMessage.equalsIgnoreCase("OTPNOTMATCH"))
				{
					setOtpNo(null);
					RequestContext.getCurrentInstance().execute("otpNotMatch.show();");
				}
			}
		} catch (AMGException e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}
	
	private boolean checkManadatoryFields()
	{
		boolean rtnValue=false;
		if(getBeneCountryId()==null)
		{
			RequestContext.getCurrentInstance().execute("selectBeneCntry.show();");
			rtnValue= true;
			return rtnValue;
		}
		if(getBeneName()==null)
		{
			RequestContext.getCurrentInstance().execute("selectBeneName.show();");
			rtnValue= true;
			return rtnValue;
		}
		if(getBeneBankId()==null)
		{
			RequestContext.getCurrentInstance().execute("selectBeneBank.show();");
			rtnValue= true;
			return rtnValue;
		}
		if(getBeneBranchId()==null)
		{
			RequestContext.getCurrentInstance().execute("selectBeneBranch.show();");
			rtnValue= true;
			return rtnValue;
		}
		/*if(getAccountSequenceId()==null)
		{
			RequestContext.getCurrentInstance().execute("selectBeneAccount.show();");
			rtnValue= true;
			return rtnValue;
		}*/
		if(getLanguageId()==null)
		{
			RequestContext.getCurrentInstance().execute("selectLanguage.show();");
			rtnValue= true;
			return rtnValue;
		}
		return rtnValue;
	}
	public void loadAvialServices()
	{
		HashMap<String, String> rtnMap=iMobileAppVivaService.getAvialServices(getCustomerId());
		if(rtnMap!=null)
		{
			String avialKiosk=rtnMap.get("KIOSK");
			String avialMobile=rtnMap.get("MOBILE");
			String avialOnline=rtnMap.get("ONLINE");
			String avialServiceProvider=rtnMap.get("SERVICEPROVIDER");
			
			
			if(avialKiosk!=null)
			{
				setAvailKioskService(avialKiosk);
			}else
			{
				setAvailKioskService(Constants.No);
			}
			
			if(avialMobile!=null)
			{
				setAvailMobileAppService(avialMobile);
				
			}else
			{
				setAvailMobileAppService(Constants.No);
			}
			
			if(avialOnline!=null)
			{
				setAvailOnLineService(avialOnline);
			}else
			{
				setAvailOnLineService(Constants.No);
			}
			
			if(avialServiceProvider!=null)
			{
				setAvailServiceProvider(avialServiceProvider);
				callMobileAppService();
			}else
			{
				setAvailServiceProvider(Constants.No);
			}
		}
	}
	
	public String createRandomOTP() {
	       
        int OtpLength = 4;
        String validChars = 
            "1234567890";
        String otp = "";
        for (int i = 0; i < OtpLength; i++) {
            otp = 
                    otp + String.valueOf(validChars.charAt((int)(Math.random() * 
                                                                      validChars.length())));
        }
        return otp;
      }

	public void exit() throws IOException {
        if(session.getRoleId().equalsIgnoreCase("1")){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	public void backToRemitanceFirstPanel() {
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		try {
			context.redirect("../remittance/PersonalRemittance.xhtml");
			
		} catch (Exception e) {
			
		}
		
	}
	public boolean loadServiceProvider()
	{
		boolean rtnValue=false;
		setLstServiceProviderView(null);
		List<ServiceProviderView> lstServiceProviderView= iMobileAppVivaService.getSrviceProvider();
		if(lstServiceProviderView== null || lstServiceProviderView.size()==0)
		{
			rtnValue=false;
			return rtnValue;
		}
		if(lstServiceProviderView!=null && lstServiceProviderView.size()==1)
		{
			ServiceProviderView serviceProviderView=lstServiceProviderView.get(0);
			rtnValue=true;
			setServiceProviderName(serviceProviderView.getServiceProvider());
			setLstServiceProviderView(lstServiceProviderView);
			//populateBeneFiciaryCountry();
			callBeneDetails();
			
		}else if(lstServiceProviderView!=null && lstServiceProviderView.size()>1)
		{
			rtnValue=true;
			setLstServiceProviderView(lstServiceProviderView);
		}
		return rtnValue;
	}
}
