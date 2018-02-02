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
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.ViewArea;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("placeOnOrderCreationBean")
@Scope("session")
public class PlaceOnOrderCreationBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger  log = Logger.getLogger(PlaceOnOrderCreationBean.class);

	private SessionStateManage session=new SessionStateManage();

	private BigDecimal beneficiaryCountryId;
	private BigDecimal beneficiaryBankId;
	private String product;
	private String accountNumber;
	private BigDecimal customerId;
	private BigDecimal customerRef;
	private BigDecimal beneficiaryId;
	private BigDecimal fcOrLocalAmount;
	private String emailId;
	private BigDecimal serviceGroupId;
	private String errorMsg;
	private BigDecimal accountSequenceId;
	private BigDecimal beneficiaryCurrencyId;
	private String beneficiaryCurrencyName;
	
	//private BigDecimal currencyId;
	private String beneficiaryName;
	private Boolean booRenderBankCash;
	private Boolean booRenderBnkAccNoList;
	private Boolean booRenderBnkAccNo;
	private BigDecimal beneAccSeqId;
	private BigDecimal countryBranchId;
	private String currencyQuoteName;
	private Boolean placeOrderCheck;
	private Boolean booDocVisble;
	private String errmsg;
	private BigDecimal financialYearId;
	private BigDecimal finaceYear;
	private BigDecimal documentId;
	private boolean saveVisiable;
	private Date valueDate;
	private Date effectiveMinDate = new Date();
	private BigDecimal requsetCurrency;
	private List<PopulateData> lstRequestCurrency;
	private List<CountryMasterDesc> lstCountry;
	private List<Employee> lstEmploye;
	private String areaName;
	private String serviceGroupName;
	private String customerName;
	private List<ViewArea> lstViewArea;
	private String beneficiaryBankName;
	private Boolean booRenderSingleBank=false;
	private Boolean booRenderMultipleBank=false;
	/*private Boolean booRenderExitButtonOnline;
	private Boolean booRenderExitButtonBranch;*/
	private BigDecimal destinationCurrency;
	private List<PopulateData> lstDestinationCurrency;
	
	
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
	

	private List<PopulateData>  allBeneCountryList;
	private List<PopulateData>  beneficiaryList;
	private List<PopulateData> allServiceList;
	private List<PopulateData> lstBanks ;
	private List<PopulateData>  lstAccountNumber;
	private HashMap<BigDecimal,String> serviceGroupMap;
	private HashMap<BigDecimal,String> beneAccNoMap;

	
	public boolean isSaveVisiable() {
		return saveVisiable;
	}
	public void setSaveVisiable(boolean saveVisiable) {
		this.saveVisiable = saveVisiable;
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
	
	public BigDecimal getDestinationCurrency() {
		return destinationCurrency;
	}
	public void setDestinationCurrency(BigDecimal destinationCurrency) {
		this.destinationCurrency = destinationCurrency;
	}

	public List<PopulateData> getLstDestinationCurrency() {
		return lstDestinationCurrency;
	}
	public void setLstDestinationCurrency(List<PopulateData> lstDestinationCurrency) {
		this.lstDestinationCurrency = lstDestinationCurrency;
	}

	public List<ViewArea> getLstViewArea() {
		return lstViewArea;
	}
	public void setLstViewArea(List<ViewArea> lstViewArea) {
		this.lstViewArea = lstViewArea;
	}

	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getServiceGroupName() {
		return serviceGroupName;
	}
	public void setServiceGroupName(String serviceGroupName) {
		this.serviceGroupName = serviceGroupName;
	}

	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public List<Employee> getLstEmploye() {
		return lstEmploye;
	}
	public void setLstEmploye(List<Employee> lstEmploye) {
		this.lstEmploye = lstEmploye;
	}

	public List<CountryMasterDesc> getLstCountry() {
		return lstCountry;
	}
	public void setLstCountry(List<CountryMasterDesc> lstCountry) {
		this.lstCountry = lstCountry;
	}

	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}
	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}

	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public BigDecimal getRequsetCurrency() {
		return requsetCurrency;
	}
	public void setRequsetCurrency(BigDecimal requsetCurrency) {
		this.requsetCurrency = requsetCurrency;
	}

	public List<PopulateData> getLstRequestCurrency() {
		return lstRequestCurrency;
	}
	public void setLstRequestCurrency(List<PopulateData> lstRequestCurrency) {
		this.lstRequestCurrency = lstRequestCurrency;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public BigDecimal getFinancialYearId() {
		return financialYearId;
	}
	public void setFinancialYearId(BigDecimal financialYearId) {
		this.financialYearId = financialYearId;
	}
	
	public void setFinaceYear(BigDecimal finaceYear) {
		this.finaceYear = finaceYear;
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

	public Boolean getPlaceOrderCheck() {
		return placeOrderCheck;
	}
	public void setPlaceOrderCheck(Boolean placeOrderCheck) {
		this.placeOrderCheck = placeOrderCheck;
	}
	
	public List<PopulateData> getAllBeneCountryList() {
		return allBeneCountryList;
	}
	public void setAllBeneCountryList(List<PopulateData> allBeneCountryList) {
		this.allBeneCountryList = allBeneCountryList;
	}
	
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
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public BigDecimal getCustomerRef() {
		return customerRef;
	}
	public void setCustomerRef(BigDecimal customerRef) {
		this.customerRef = customerRef;
	}

	public BigDecimal getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(BigDecimal beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public BigDecimal getFcOrLocalAmount() {
		return fcOrLocalAmount;
	}
	public void setFcOrLocalAmount(BigDecimal fcOrLocalAmount) {
		this.fcOrLocalAmount = fcOrLocalAmount;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public List<PopulateData> getBeneficiaryList() {
		return beneficiaryList;
	}
	public void setBeneficiaryList(List<PopulateData> beneficiaryList) {
		this.beneficiaryList = beneficiaryList;
	}

	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}

	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}
	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}

	public List<PopulateData> getAllServiceList() {
		return allServiceList;
	}
	public void setAllServiceList(List<PopulateData> allServiceList) {
		this.allServiceList = allServiceList;
	}

	public BigDecimal getServiceGroupId() {
		return serviceGroupId;
	}
	public void setServiceGroupId(BigDecimal serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}

	public List<PopulateData> getLstBanks() {
		return lstBanks;
	}
	public void setLstBanks(List<PopulateData> lstBanks) {
		this.lstBanks = lstBanks;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public BigDecimal getAccountSequenceId() {
		return accountSequenceId;
	}
	public void setAccountSequenceId(BigDecimal accountSequenceId) {
		this.accountSequenceId = accountSequenceId;
	}

	/*public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}*/

	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	
	public Boolean getBooRenderBankCash() {
		return booRenderBankCash;
	}
	public void setBooRenderBankCash(Boolean booRenderBankCash) {
		this.booRenderBankCash = booRenderBankCash;
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
	
	public BigDecimal getBeneAccSeqId() {
		return beneAccSeqId;
	}
	public void setBeneAccSeqId(BigDecimal beneAccSeqId) {
		this.beneAccSeqId = beneAccSeqId;
	}
	
	public IBeneficaryCreation getBeneficaryCreation() {
		return beneficaryCreation;
	}
	public void setBeneficaryCreation(IBeneficaryCreation beneficaryCreation) {
		this.beneficaryCreation = beneficaryCreation;
	}
	
	public List<PopulateData> getLstAccountNumber() {
		return lstAccountNumber;
	}
	public void setLstAccountNumber(List<PopulateData> lstAccountNumber) {
		this.lstAccountNumber = lstAccountNumber;
	}
	
	public HashMap<BigDecimal, String> getServiceGroupMap() {
		return serviceGroupMap;
	}
	public void setServiceGroupMap(HashMap<BigDecimal, String> serviceGroupMap) {
		this.serviceGroupMap = serviceGroupMap;
	}

	public HashMap<BigDecimal, String> getBeneAccNoMap() {
		return beneAccNoMap;
	}
	public void setBeneAccNoMap(HashMap<BigDecimal, String> beneAccNoMap) {
		this.beneAccNoMap = beneAccNoMap;
	}
	
	/*public Boolean getBooRenderExitButtonOnline() {
		return booRenderExitButtonOnline;
	}
	public void setBooRenderExitButtonOnline(Boolean booRenderExitButtonOnline) {
		this.booRenderExitButtonOnline = booRenderExitButtonOnline;
	}
	
	public Boolean getBooRenderExitButtonBranch() {
		return booRenderExitButtonBranch;
	}
	public void setBooRenderExitButtonBranch(Boolean booRenderExitButtonBranch) {
		this.booRenderExitButtonBranch = booRenderExitButtonBranch;
	}*/


	public String getCurrencyQuoteName() {
		return currencyQuoteName;
	}
	public void setCurrencyQuoteName(String currencyQuoteName) {
		this.currencyQuoteName = currencyQuoteName;
	}

	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	
	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}
	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
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


	public void pageNavigation(){

		try {

			log.info( "===============navigation==================");
			clearAll();

			setCustomerId(session.getCustomerId());
			List<Customer> custList= iCustomerRegistrationBranchService.getCustomerInfo(session.getCustomerId());
			Customer customer=custList.get( 0);
			setEmailId(customer.getEmail());
			setCustomerRef(customer.getCustomerReference() );
			setCustomerName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " " + nullCheck(customer.getLastName()));
			setCountryBranchId(new BigDecimal(session.getBranchId()));
			
			List<CountryMasterDesc>  lstCountryMasterDesc= generalService.getCountryList(session.getLanguageId());
			setLstCountry(lstCountryMasterDesc);
			loadArea();
			/*List<PopulateData> countryList = iPersonalRemittanceService.getBeneficaryList(session.getCustomerId());
			setAllBeneCountryList( countryList);*/

			//setBooRenderExitButtonOnline(true);
			//setBooRenderExitButtonBranch(false);
			
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../onlinespecialrate/PlaceOnOrderCreationBean.xhtml");
			
			/*boolean checkRateOrder= iPlaceOnOrderCreationService.checkPlaceanOrderCreatedForCustomer(getCustomerId());
			if(checkRateOrder)
			{
				setPlaceOrderCheck(true);
				RequestContext.getCurrentInstance().execute("placeorderexistedOnload.show();");
				
				return;
			}else
			{
				setPlaceOrderCheck(false);
			}*/
			
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void populateBeneficiary(){

		try{
			setBeneficiaryList(null);
			//setLstBanks(null);
			setLstAccountNumber(null);
			setBeneficiaryName(null);
			setBeneficiaryBankId(null);
			setBeneficiaryBankName(null);
			setBeneAccSeqId(null);
			setAccountNumber(null);
			setFcOrLocalAmount(null);
			setBooRenderBnkAccNoList(false);
			setBooRenderBnkAccNo(false);
			setBooRenderBankCash(false);
			setCurrencyQuoteName(null);
			setAreaName(null);
			setRequsetCurrency(null);
			setDestinationCurrency(null);
			
			List<PopulateData> lstBeneName = iPlaceOnOrderCreationService.getBeneficiarNameList(getCustomerId(), getBeneficiaryCountryId(), getServiceGroupId(),getCustomerRef());
			if(lstBeneName != null && lstBeneName.size() != 0){
				setBeneficiaryList(lstBeneName);
			}else{
				setBeneficiaryList(null);
			}

			/*if(lstBeneName!=null && lstBeneName.size()==0)
			{
				RequestContext.getCurrentInstance().execute("lstBeneNameNot.show();");
				return;
			}*/
			/*String remitType= getServiceGroupMap().get(getServiceGroupId());
			if(remitType!=null && remitType.equalsIgnoreCase(Constants.CASHFORONLINE))
			{
				setBooRenderBankCash(false);
			}else
			{
				setBooRenderBankCash(true);
				setBooRenderBnkAccNo(true);
			}*/

		}catch(Exception e){
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

	}
	
	public void populateserviceGroup()
	{  
		try{
			setServiceGroupId(null);
			setBeneficiaryName(null);
			setBeneficiaryBankId(null);
			setBeneAccSeqId(null);
			setAccountNumber(null);
			setFcOrLocalAmount(null);
			setLstBanks(null);
			setLstAccountNumber(null);
			setAllServiceList(null);
			setServiceGroupMap(null);
			setBeneficiaryList(null);
			setCurrencyQuoteName(null);
			setAreaName(null);
			setRequsetCurrency(null);
			setDestinationCurrency(null);
			
			//loadRequestCurrencylist();
			List<PopulateData> serviceList=iPlaceOnOrderCreationService.getServiceGroupList(session.getLanguageId());

			if(serviceList!=null && serviceList.size()==0)
			{
				RequestContext.getCurrentInstance().execute("serviceListNot.show();");
				return;
			}
			
			log.info( "================servicelist====="+serviceList.size());
			if(serviceList!=null && serviceList.size()>0){
				setServiceGroupId(serviceList.get(0).getPopulateId());
				setServiceGroupName(serviceList.get(0).getPopulateName());
				
				HashMap<BigDecimal,String> serviceGroupMap = new HashMap<BigDecimal,String> ();
				for(PopulateData populateData :serviceList)
				{
					serviceGroupMap.put(populateData.getPopulateId(), populateData.getPopulateCode());
				}
				setServiceGroupMap(serviceGroupMap);
				setAllServiceList(serviceList);
				
				populateBeneficiary();
			}
			
			List<PopulateData> lstBeneBanks=iPlaceOnOrderCreationService.getBeneCorespondingBankListBasedOnCountru(getBeneficiaryCountryId());

			/*if(lstBeneBanks!=null && lstBeneBanks.size()==0)
			{
				RequestContext.getCurrentInstance().execute("bankNot.show();");
				return;
			}*/
			
			setLstBanks(lstBeneBanks);
			
		}catch(Exception e){
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}
	
	public void populateBanks()
	{
		try{
			setBeneficiaryBankId( null);
			setLstBanks(null);
			setLstAccountNumber(null);
			setBeneAccSeqId(null);
			setAccountNumber(null);
			setFcOrLocalAmount(null);
			setCurrencyQuoteName(null);
			setAreaName(null);
			setRequsetCurrency(null);
			setDestinationCurrency(null);
			setBooRenderBnkAccNoList(false);
			setBooRenderBnkAccNo(false);
			
			if(getBeneficiaryName()!=null)
			{
				String remitType= getServiceGroupMap().get(getServiceGroupId());
				if(remitType!=null && remitType.equalsIgnoreCase(Constants.CASHFORONLINE))
				{
					setBooRenderBankCash(false);
				}else
				{
					setBooRenderBankCash(true);
					setBooRenderBnkAccNo(true);
				}
				List<PopulateData> lstBeneBanks=iPlaceOnOrderCreationService.getBeneficiarBankList(getBeneficiaryName(), getServiceGroupId(),getBeneficiaryCountryId(),getCustomerRef());

				if(lstBeneBanks!=null && lstBeneBanks.size()==0)
				{
					RequestContext.getCurrentInstance().execute("bankNot.show();");
					return;
				}else if (lstBeneBanks!=null && lstBeneBanks.size()==1) {
					setBeneficiaryBankId(lstBeneBanks.get(0).getPopulateId());
					setBeneficiaryBankName(lstBeneBanks.get(0).getPopulateName());
					setBooRenderSingleBank(true);
					setBooRenderMultipleBank(false);
					populateAccountNumber();
				}else if (lstBeneBanks!=null && lstBeneBanks.size() > 1){
				setLstBanks(lstBeneBanks);
				setBooRenderSingleBank(false);
				setBooRenderMultipleBank(true);
				}
				
			}
			
		}catch(Exception e){
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

	}
	
	public void clearAll(){

		//setCustomerRef(null);
		setSaveVisiable(false);
		setAccountSequenceId( null);
		setBeneficiaryId( null);
		setBeneficiaryCountryId( null);
		setBeneficiaryBankId( null);
		setAccountNumber( null);
		setServiceGroupId( null);
		setFcOrLocalAmount( null);
		//setCurrencyId( null);
		setBooRenderBankCash(false);
		setBooRenderBnkAccNoList(false);
		setBooRenderBnkAccNo(false);
		setBeneAccSeqId(null);
		setServiceGroupMap(null);
		setBeneAccNoMap(null);
		setAllServiceList(null);
		setLstBanks(null);
		setLstAccountNumber(null);
		setBeneficiaryName(null);
		setBeneficiaryList(null);
		setCurrencyQuoteName(null);
		setPlaceOrderCheck(false);
		setDocumentId(null);
		setFinaceYear(null);
		setAreaName(null);
		setValueDate(new Date());
		setLstRequestCurrency(null);
		setLstDestinationCurrency(null);
		setServiceGroupName(null);
		setBeneficiaryBankName(null);
		setBooRenderSingleBank(false);
		setBooRenderMultipleBank(true);
		/*setBooRenderExitButtonOnline(false);
		setBooRenderExitButtonBranch(false);*/
	}

	public void populateAccountNumber()
	{
		try{
			if(getBeneficiaryName()!=null && getBooRenderBankCash()){
				setBeneAccNoMap(null);
				setFcOrLocalAmount(null);
				setCurrencyQuoteName(null);
				setAccountNumber(null);
				setAccountSequenceId(null);
				setLstAccountNumber(null);
				setBeneAccSeqId(null);
				setAreaName(null);
				setRequsetCurrency(null);
				setDestinationCurrency(null);
				setBooRenderBnkAccNoList(false);
				setBooRenderBnkAccNo(false);
				
				log.info( "=========== beneid==="+getBeneficiaryName());
				log.info( "=========== customerid==="+session.getCustomerId());
				log.info( "=========== benecountryid==="+getBeneficiaryCountryId());
				log.info( "=========== benebankid==="+getBeneficiaryBankId());

				List<PopulateData> accountNumberList= iPlaceOnOrderCreationService.getBeneAccountNumber(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerRef());
				if(accountNumberList!=null && accountNumberList.size()==0  )
				{
					RequestContext.getCurrentInstance().execute("accountNo.show();");
					return;
				}
				log.info( "============bank accountnumList======="+accountNumberList.size());
				HashMap<BigDecimal,String> beneAccNoMap = new HashMap<BigDecimal,String> ();
				for(PopulateData populateData :accountNumberList)
				{
					beneAccNoMap.put(populateData.getPopulateId(), populateData.getPopulateCode());
				}
				setBeneAccNoMap(beneAccNoMap);
				if(accountNumberList!=null && accountNumberList.size()==1)
				{
					setBooRenderBnkAccNoList(false);
					setBooRenderBnkAccNo(true);
					setAccountNumber(accountNumberList.get( 0).getPopulateCode());
					setAccountSequenceId(accountNumberList.get(0).getPopulateId());
					
					HashMap<String, BigDecimal> rtnMap=iPlaceOnOrderCreationService.getBeneMasterIdCurrencyId(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerRef(),getAccountNumber());

					BigDecimal beneficaryMasterSeqId =  rtnMap.get("BeneficaryMasterSeqId");
					BigDecimal beneficaryCurrencyId = rtnMap.get("CurrencyId");
					setBeneficiaryCurrencyId(beneficaryCurrencyId);
					String currencyName = generalService.getCurrencyName(beneficaryCurrencyId);
					setBeneficiaryCurrencyName(currencyName);
					
					String currencyQuoteName =iPlaceOnOrderCreationService.toFetchCurrencyQtyName(beneficaryMasterSeqId,getAccountSequenceId(),getCustomerRef());
					if(currencyQuoteName!=null)
					{
						setCurrencyQuoteName("["+currencyQuoteName+"]");
					}
					
					loadRequestCurrencylist();
					//loadDestinationCurrencylist();

				}else if(accountNumberList!=null && accountNumberList.size()>1){
					setRequsetCurrency(null);
					setDestinationCurrency(null);
					setLstRequestCurrency(null);
					setLstDestinationCurrency(null);
					setBooRenderBnkAccNoList(true);
					setBooRenderBnkAccNo(false);
					setLstAccountNumber(accountNumberList);
				}else{
					setRequsetCurrency(null);
					setDestinationCurrency(null);
					setLstRequestCurrency(null);
					setLstDestinationCurrency(null);
					setBooRenderBnkAccNoList(true);
					setBooRenderBnkAccNo(false);
					setLstAccountNumber(null);
				}
			}else{
				setFcOrLocalAmount(null);
				setCurrencyQuoteName(null);
				setAccountSequenceId(null);
				setBeneAccSeqId(null);
				setAreaName(null);
				setRequsetCurrency(null);
				setDestinationCurrency(null);
				BigDecimal accountSeqId=null; 
				
				HashMap<String, BigDecimal> rtnMap=iPlaceOnOrderCreationService.getBeneMasterIdCurrencyId(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerRef(),getAccountNumber());

				BigDecimal beneficaryMasterSeqId =  rtnMap.get("BeneficaryMasterSeqId");
				BigDecimal beneficaryCurrencyId = rtnMap.get("CurrencyId");
				setBeneficiaryCurrencyId(beneficaryCurrencyId);
				String currencyName = generalService.getCurrencyName(beneficaryCurrencyId);
				setBeneficiaryCurrencyName(currencyName);
				
				loadRequestCurrencylist();
				loadDestinationCurrencylist();
				//fetch account seq id
				accountSeqId=iPlaceOnOrderCreationService.toFetchAccountSeqId(beneficaryMasterSeqId,getBeneficiaryName(),getBeneficiaryBankId());
				setAccountSequenceId(accountSeqId);
				String currencyQuoteName =iPlaceOnOrderCreationService.toFetchCurrencyQtyName(beneficaryMasterSeqId,getAccountSequenceId(),getCustomerRef());
				if(currencyQuoteName!=null)
				{
					setCurrencyQuoteName("["+currencyQuoteName+"]");
				}
			}

		}catch(Exception e){
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}




	public void saveRecord(){
		try{
			/*boolean checkRateOrder= iPlaceOnOrderCreationService.checkPlaceanOrderCreatedForCustomer(getCustomerId());
			if(checkRateOrder)
			{
				RequestContext.getCurrentInstance().execute("placeorderexisted.show();");
				return;
			}*/
			if(getBeneficiaryName()!=null)
			{

				if(getBooRenderBnkAccNoList() )
				{
					if(getBeneAccSeqId()== null)
					{
						RequestContext.getCurrentInstance().execute("accountnomandatory.show();");
						return;
					}
				}

				if(getBooRenderBnkAccNo())
				{
					if(getAccountNumber()==null)
					{
						RequestContext.getCurrentInstance().execute("accountnomandatory.show();");
						return;
					}
				}

			}
			setSaveVisiable(false);
			/*if(getBooRenderBankCash())
			{
				if(getAccountNumber()==null)
				{
					RequestContext.getCurrentInstance().execute("accountnomandatory.show();");
					return;
				}

			}*/

			HashMap<String, BigDecimal> rtnMap=iPlaceOnOrderCreationService.getBeneMasterIdCurrencyId(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerRef(),getAccountNumber());

			BigDecimal beneficaryMasterSeqId =  rtnMap.get("BeneficaryMasterSeqId");
			BigDecimal beneficaryCurrencyId = rtnMap.get("CurrencyId");
			setBeneficiaryCurrencyId(beneficaryCurrencyId);
			String currencyName = generalService.getCurrencyName(beneficaryCurrencyId);
			setBeneficiaryCurrencyName(currencyName);

			BigDecimal currencyId = rtnMap.get("CurrencyId");

			List<RatePlaceOrder> lstPlaceOrders=iPlaceOnOrderCreationService.duplicatecheckRecord(getCustomerId(),getCustomerRef(),getBeneficiaryBankId(),new Date(),getBeneficiaryCountryId(),getAccountNumber(),getServiceGroupId(),getFcOrLocalAmount(),beneficaryMasterSeqId,getAccountSequenceId());
			if(lstPlaceOrders.size()>0){
				RequestContext.getCurrentInstance().execute("placeORDER.show();");
				clearAll();
				return;
			}
			getDocumentDescription();
			getFinaceYear();
			BigDecimal docRefNumber=getDocumentSerialIdNumber(Constants.U);
			if(docRefNumber.compareTo(BigDecimal.ZERO)==0)
			{
				//RequestContext.getCurrentInstance().execute("docZero.show();");
				return;
			}
			String curQtyName=gSMPlaceOrderRateFeedService.toFetchCurrencyQtyName(getRequsetCurrency());

			RatePlaceOrder ratePlaceOrderObj=new RatePlaceOrder();

			ratePlaceOrderObj.setBeneficiaryBankId(getBeneficiaryBankId());
			ratePlaceOrderObj.setCustomerId(getCustomerId());
			ratePlaceOrderObj.setCreatedBy(session.getUserName());
			ratePlaceOrderObj.setCreatedDate(new Date());
			ratePlaceOrderObj.setBeneficiaryCountryId(getBeneficiaryCountryId());
			ratePlaceOrderObj.setBeneficiaryAccountNo(getAccountNumber());
			ratePlaceOrderObj.setRemitType(getServiceGroupId());
			ratePlaceOrderObj.setCustomerEmail(getEmailId());
			ratePlaceOrderObj.setTransactionAmount(getFcOrLocalAmount());
			ratePlaceOrderObj.setIsActive(Constants.U);

			if(getAccountSequenceId()!=null){
				BeneficaryAccount beneAccountObj=new BeneficaryAccount();
				beneAccountObj.setBeneficaryAccountSeqId(getAccountSequenceId());
				ratePlaceOrderObj.setAccountSeqquenceId(beneAccountObj );
			}
			if(beneficaryMasterSeqId!=null)
			{
				BeneficaryMaster beneMasterObj=new BeneficaryMaster();
				beneMasterObj.setBeneficaryMasterSeqId(beneficaryMasterSeqId);
				ratePlaceOrderObj.setBeneficiaryMasterId(beneMasterObj);
			}
			if(getRequsetCurrency()!=null)
			{
				ratePlaceOrderObj.setRequestCurrencyId(getRequsetCurrency());
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

			if(getDestinationCurrency()!=null)
			{
				ratePlaceOrderObj.setDestinationCurrenyId(getDestinationCurrency());
			}


			iPlaceOnOrderCreationService.saveRecord(ratePlaceOrderObj);
			setSaveVisiable(true);
			RequestContext.getCurrentInstance().execute("complete.show();");
			if(getEmailId() != null){
				String beneBankName=null;
				String brachName= null;
				String custmerName=null;
				beneBankName=generalService.getBankName(getBeneficiaryBankId());
				brachName=gSMPlaceOrderRateFeedService.toFetchBranchName(getCountryBranchId());
				custmerName=generalService.getCustomerNameCustomerId(getCustomerId());

				HashMap<String, String> inputValues=new HashMap<String, String>();
				SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				inputValues.put("EmailId", getEmailId());
				inputValues.put("RateOffered", "Rate Offered");
				inputValues.put("User", session.getUserName());
				inputValues.put("Name", getCustomerName() + " / " + getCustomerRef() );
				inputValues.put("beneName", getBeneficiaryName()==null ? "" : getBeneficiaryName());
				inputValues.put("Bank", beneBankName);
				inputValues.put("Branch Name", brachName);
				inputValues.put("Amount", getFcOrLocalAmount()+" - "+curQtyName);
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


	public void branchPageNavigation(CustomerIdProof customerDetails){

		log.info( "===============navigation==================");
		try {
			clearAll();

			setCustomerId(customerDetails.getFsCustomer().getCustomerId());
			List<Customer> custList= iCustomerRegistrationBranchService.getCustomerInfo(customerDetails.getFsCustomer().getCustomerId());
			Customer customer=custList.get( 0);
			setEmailId(customer.getEmail());
			setCustomerRef(customer.getCustomerReference() );
			setCountryBranchId(new BigDecimal(session.getBranchId()));
			setCustomerName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " " + nullCheck(customer.getLastName()));
			List<CountryMasterDesc>  lstCountryMasterDesc= generalService.getCountryList(session.getLanguageId());
			setLstCountry(lstCountryMasterDesc);
			loadArea();
			/*List<PopulateData> countryList = iPersonalRemittanceService.getBeneficaryList(customerDetails.getFsCustomer().getCustomerId());
			setAllBeneCountryList( countryList);*/
			//setBooRenderExitButtonOnline(false);
			//setBooRenderExitButtonBranch(true);
			/*FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../onlinespecialrate/PlaceOnOrderCreationBean.xhtmll");*/
			/*
			
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/PlaceOnOrderCreationBeanBranch.xhtml");
				boolean checkRateOrder= iPlaceOnOrderCreationService.checkPlaceanOrderCreatedForCustomer(customerDetails.getFsCustomer().getCustomerId());
				if(checkRateOrder)
				{
					setPlaceOrderCheck(true);
					RequestContext.getCurrentInstance().execute("placeorderexistedOnload.show();");
					return;
				}
			} catch (IOException e) {
				
				
				e.printStackTrace();
			}*/
		} catch (Exception e) {
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}


	}

	public void populateOtherDetails()
	{
		//loadDestinationCurrencylist();
		setAreaName(null);
		setRequsetCurrency(null);
		setDestinationCurrency(null);
		
		String accountNumber=getBeneAccNoMap().get(getBeneAccSeqId());
		setAccountSequenceId(getBeneAccSeqId());
		setAccountNumber(accountNumber);
		
		HashMap<String, BigDecimal> rtnMap=iPlaceOnOrderCreationService.getBeneMasterIdCurrencyId(getBeneficiaryName(),getBeneficiaryCountryId(),getBeneficiaryBankId(),getServiceGroupId(),getCustomerRef(),getAccountNumber());

		BigDecimal beneficaryMasterSeqId =  rtnMap.get("BeneficaryMasterSeqId");
		BigDecimal beneficaryCurrencyId = rtnMap.get("CurrencyId");
		setBeneficiaryCurrencyId(beneficaryCurrencyId);
		String currencyName = generalService.getCurrencyName(beneficaryCurrencyId);
		setBeneficiaryCurrencyName(currencyName);
		
		String currencyQuoteName =iPlaceOnOrderCreationService.toFetchCurrencyQtyName(beneficaryMasterSeqId,getAccountSequenceId(),getCustomerRef());
		if(currencyQuoteName!=null)
		{
			setCurrencyQuoteName("["+currencyQuoteName+"]");
		}
		loadRequestCurrencylist();

	}


	public void saveOkClick(){

		try {

			clearAll();

			List<Customer> custList= iCustomerRegistrationBranchService.getCustomerInfo(getCustomerId());
			Customer customer =custList.get( 0);
			setEmailId(customer.getEmail());
			setCustomerRef(customer.getCustomerReference() );
			setCustomerName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " " + nullCheck(customer.getLastName()));
			/*List<PopulateData> countryList = iPersonalRemittanceService.getBeneficaryList(getCustomerId());
			setAllBeneCountryList(countryList);*/
			List<CountryMasterDesc>  lstCountryMasterDesc= generalService.getCountryList(session.getLanguageId());
			setLstCountry(lstCountryMasterDesc);
			loadArea();
			
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../onlinespecialrate/PlaceOnOrderCreationBean.xhtml");
		} catch (IOException e) {
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}


	}
	
	public void saveOkClick1(){

		try {

			clearAll();

			List<Customer> custList= iCustomerRegistrationBranchService.getCustomerInfo(getCustomerId());
			Customer customer=custList.get( 0);
			setEmailId(customer.getEmail());
			setCustomerRef(customer.getCustomerReference() );
			setCustomerName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " " + nullCheck(customer.getLastName()));
			/*loadCurrencylist();
			List<PopulateData> countryList = iPersonalRemittanceService.getBeneficaryList(getCustomerId());
			setAllBeneCountryList(countryList);*/
			
			List<CountryMasterDesc>  lstCountryMasterDesc= generalService.getCountryList(session.getLanguageId());
			setLstCountry(lstCountryMasterDesc);
			loadArea();
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../onlinespecialrate/PlaceOnOrderCreationBeanBranch.xhtml");
		} catch (IOException e) {
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
	
	public void exitOnline(){
		
		try {
			clearAll();
			//setBooRenderExitButtonOnline(false);
			//setBooRenderExitButtonBranch(false);
			FacesContext.getCurrentInstance() .getExternalContext().redirect(session.getMenuPage());
		}catch(Exception e){
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}  
	}
	public void placeOrderCheckClick()
	{
		setPlaceOrderCheck(false);
	}
	
	public BigDecimal getDocumentSerialIdNumber(String processIn) {
		
		String documentSerialId="0";
		 try {
				HashMap<String, String> outPutValues= generalService.getNextDocumentRefNumber(session.getCountryId().intValue() , session.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_PLACEORDER) , getFinaceYear().intValue(),processIn,session.getCountryBranchCode());
				
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
	
	public BigDecimal getFinaceYear() {
		
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
		    setErrmsg(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return BigDecimal.ZERO;       
		    }
		return finaceYear;
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
			setErrmsg(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;       
		}
		return documentDescription;
	}

	private void loadRequestCurrencylist()
	{
		List<PopulateData> lstofCurrency = new ArrayList<PopulateData>();
				
		/*lstofCurrency= iPlaceOnOrderCreationService.getBasedOnCountyCurrency(getBeneficiaryCountryId());
		PopulateData populatedata1 = new PopulateData(new BigDecimal(session.getCurrencyId()),generalService.getCurrencyName(new BigDecimal(session.getCurrencyId())));
		lstofCurrency.add(populatedata1);
		*/
		/*List<SourceCurrencyView> lstSourceCurrencyView =iPlaceOnOrderCreationService.getRequestCurrency(getBeneficiaryBankId());
		if(lstSourceCurrencyView!=null && lstSourceCurrencyView.size()>0)
		{
			for(SourceCurrencyView sourceCurrencyView : lstSourceCurrencyView)
			{
				PopulateData populateData= new PopulateData();
				populateData.setPopulateCode(sourceCurrencyView.getCurrencyCode());
				populateData.setPopulateId(sourceCurrencyView.getCurrencyId());
				populateData.setPopulateName(sourceCurrencyView.getCurrencyName());
				lstofCurrency.add(populateData);
			}
		}*/
		//setRequsetCurrency(lstofCurrency.get(0).getPopulateId());
		
		// fetching from view VW_EX_SOURCE_CURRENCY
		//lstofCurrency=iPlaceOnOrderCreationService.getRequestCurrency(getBeneficiaryBankId());
		//setLstRequestCurrency(lstofCurrency);
		
		// fetching from ex_beneficiary_Account for that beneficiary account number
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
		if (lstofCurrency.size() != 0) {
			for (PopulateData lstofcurrency : lstofCurrency) {
				if (lstofcurrency.getPopulateId().compareTo(new BigDecimal(session.getCurrencyId())) != 0) {
					setDestinationCurrency(lstofcurrency.getPopulateId());
				}
			}
		}
		setRequsetCurrency(new BigDecimal(session.getCurrencyId()));
		selectCurrency();
	}
	
	private void loadArea()
	{
		setLstViewArea(null);
		//List<Employee> employeeList=gSMPlaceOrderRateFeedService.toFetchEmployeeArea();
		List<ViewArea> lstViewArea=gSMPlaceOrderRateFeedService.getAreaPlace();
		
		if(lstViewArea.size()>0){
			setLstViewArea(lstViewArea);
		}
	}
	public void backToFirst()
	{
		BranchPlaceOrder cranchPlaceOrder = (BranchPlaceOrder) appContext.getBean("branchPlaceOrder");
		cranchPlaceOrder.branchPlaceOrderPageNavigation();
	}
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}
	
	private void loadDestinationCurrencylist()
	{
		setEnableDesCurrency(true);
		List<PopulateData> lstofCurrency = new ArrayList<PopulateData>();
				
		/*lstofCurrency= iPlaceOnOrderCreationService.getBasedOnCountyCurrency(getBeneficiaryCountryId());
		PopulateData populatedata1 = new PopulateData(new BigDecimal(session.getCurrencyId()),generalService.getCurrencyName(new BigDecimal(session.getCurrencyId())));
		lstofCurrency.add(populatedata1);
		*/
		/*List<DestinationCurrencyView> lstDestinationCurrencyView =iPlaceOnOrderCreationService.getDestinationCurrency(getBeneficiaryBankId());
		if(lstDestinationCurrencyView!=null && lstDestinationCurrencyView.size()>0)
		{
			for(DestinationCurrencyView destinationCurrencyView : lstDestinationCurrencyView)
			{
				PopulateData populateData= new PopulateData();
				populateData.setPopulateCode(destinationCurrencyView.getCurrencyCode());
				populateData.setPopulateId(destinationCurrencyView.getCurrencyId());
				populateData.setPopulateName(destinationCurrencyView.getCurrencyName());
				lstofCurrency.add(populateData);
			}
		}*/
		//setRequsetCurrency(lstofCurrency.get(0).getPopulateId());
		
		//lstofCurrency=iPlaceOnOrderCreationService.getDestinationCurrency(getBeneficiaryBankId());
		//setLstDestinationCurrency(lstofCurrency);
		
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
	private Boolean enableDesCurrency;
	
	
	
	public Boolean getEnableDesCurrency() {
		return enableDesCurrency;
	}


	public void setEnableDesCurrency(Boolean enableDesCurrency) {
		this.enableDesCurrency = enableDesCurrency;
	}


	public void selectCurrency()
	{
		loadDestinationCurrencylist();
		BigDecimal reqCurrencyId = getRequsetCurrency();
		BigDecimal localCurrencyId=new BigDecimal(session.getCurrencyId());
		if(reqCurrencyId.compareTo(localCurrencyId)!=0)
		{
			setDestinationCurrency(reqCurrencyId);
			setEnableDesCurrency(true);
		}else
		{
			//setDestinationCurrency(null);
			setEnableDesCurrency(false);
		}
		
	}

}
