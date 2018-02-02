package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.deal.supplier.model.TreasuryCustomerSupplier;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.treasury.service.ISupplierService;
import com.amg.exchange.util.CollectionUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("supplierBean")
/*@SessionScoped*/
@Scope("session")
public class SupplierBean<T> implements Serializable {
	
	/**
	 *  Author Rahamathali Shaik
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(SupplierBean.class);

	private String customerId = null;
	private String localName = null;
	private String mobileNumber = null;
	private BigDecimal nationality = null;
	private String emailId = null;
	private String sundryDeptolRef = null;
	private BigDecimal bankcode = null;
	private String bankDescription = null;
	private String nationallityName = null;
	private String bankAccountNumber = null;
	private String currencyName=null;
	private BigDecimal currencyId = null;
	private String customerName=null;
	private BigDecimal customerTypeId=null;
	private String signatureSpecimen = null;
	private BigDecimal customerPK;
	private BigDecimal treasuryCustomerSupplierPK;
	
	private Boolean booSupplierBean=false;
	private Boolean isFromSupplierBean=false;
	private Boolean disableSubmit=false;
	private Boolean booRenderDataTable;
	private Boolean booSavePanel;
	private Boolean isFromSupplierCustref=false;
	
	public List<SupplierDataTableBean> lstSupplierDetails=new CopyOnWriteArrayList<SupplierDataTableBean>();
	private SessionStateManage sessionStateManage = new SessionStateManage();
	private List<BankApplicability> bankListFrmBankApp=new ArrayList<BankApplicability>();
	private List<CurrencyMaster> currencyList=new ArrayList<CurrencyMaster>();
	
	private Map<BigDecimal, String> bankListMap=new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> currencyListMap=new HashMap<BigDecimal, String>();
	String exceptionMessage=null;
	
	@Autowired
	IGeneralService<T> iGeneralService;
	
	@Autowired
	ISupplierService<T> iSupplierService;
	
	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
	
	/*@Autowired
	ForeignCurrencyPurchaseBean<T> foreignCurrencyPurchaseBean;*/
	
	/*@PostConstruct
	public void createBeans()
	{
		specialCustomerDealRequestBean = new SpecialCustomerDealRequestBean<T>();
		searchCustomerManageBean = new SearchCustomerManageBean<T>();
		foreignCurrencyPurchaseBean = new ForeignCurrencyPurchaseBean<T>();
	}*/
	
	
	/*@Autowired
	SpecialCustomerDealRequestBean<T> specialCustomerDealRequestBean;*/
	
	/*@Autowired
	SearchCustomerManageBean<T> searchCustomerManageBean;*/

	
	public List<BankApplicability> getBankListFrmBankApp() {
		return bankListFrmBankApp;
	}
	public void setBankListFrmBankApp(List<BankApplicability> bankListFrmBankApp) {
		this.bankListFrmBankApp = bankListFrmBankApp;
	}
	public Boolean getDisableSubmit() {
		return disableSubmit;
	}
	public void setDisableSubmit(Boolean disableSubmit) {
		this.disableSubmit = disableSubmit;
	}
	public BigDecimal getTreasuryCustomerSupplierPK() {
		return treasuryCustomerSupplierPK;
	}
	public void setTreasuryCustomerSupplierPK(BigDecimal treasuryCustomerSupplierPK) {
		this.treasuryCustomerSupplierPK = treasuryCustomerSupplierPK;
	}
	public BigDecimal getCustomerPK() {
		return customerPK;
	}
	public void setCustomerPK(BigDecimal customerPK) {
		this.customerPK = customerPK;
	}
	public List<CurrencyMaster> getCurrencyList() {
		return currencyList;
	}
	public void setCurrencyList(List<CurrencyMaster> currencyList) {
		this.currencyList = currencyList;
	}
	
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public Boolean getIsFromSupplierBean() {
		return isFromSupplierBean;
	}
	public void setIsFromSupplierBean(Boolean isFromSupplierBean) {
		this.isFromSupplierBean = isFromSupplierBean;
	}
	public Boolean getBooSupplierBean() {
		return booSupplierBean;
	}
	public void setBooSupplierBean(Boolean booSupplierBean) {
		this.booSupplierBean = booSupplierBean;
	}
	public BigDecimal getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(BigDecimal customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public String getNationallityName() {
		return nationallityName;
	}
	public void setNationallityName(String nationallityName) {
		this.nationallityName = nationallityName;
	}
	public String getLocalName() {
		return localName;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public BigDecimal getNationality() {
		return nationality;
	}
	public void setNationality(BigDecimal nationality) {
		this.nationality = nationality;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getSundryDeptolRef() {
		return sundryDeptolRef;
	}
	public void setSundryDeptolRef(String sundryDeptolRef) {
		this.sundryDeptolRef = sundryDeptolRef;
	}
	public BigDecimal getBankcode() {
		return bankcode;
	}
	public void setBankcode(BigDecimal bankcode) {
		this.bankcode = bankcode;
	}
	public String getBankDescription() {
		return bankDescription;
	}
	public void setBankDescription(String bankDescription) {
		this.bankDescription = bankDescription;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getSignatureSpecimen() {
		return signatureSpecimen;
	}
	public void setSignatureSpecimen(String signatureSpecimen) {
		this.signatureSpecimen = signatureSpecimen;
	}
	public Boolean getIsFromSupplierCustref() {
		return isFromSupplierCustref;
	}

	public void setIsFromSupplierCustref(Boolean isFromSupplierCustref) {
		this.isFromSupplierCustref = isFromSupplierCustref;
	}
	public List<SupplierDataTableBean> getLstSupplierDetails() {
		return lstSupplierDetails;
	}

	public void setLstSupplierDetails(List<SupplierDataTableBean> lstSupplierDetails) {
		this.lstSupplierDetails = lstSupplierDetails;
	}

	public Boolean getBooSavePanel() {
		return booSavePanel;
	}

	public void setBooSavePanel(Boolean booSavePanel) {
		this.booSavePanel = booSavePanel;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	
	public void getCustomerDetails() {
			List<Customer>	customerData = iSupplierService.getCustomerDetailsBasedOnReference(new BigDecimal(getCustomerId()));
		if(customerData!=null && customerData.size()>0) {
			setCustomerPK(customerData.get(0).getCustomerId());
			setCustomerTypeId(customerData.get(0).getCustomerId());
			setSundryDeptolRef(customerData.get(0).getSundryDebtorReference());
			String firstName=customerData.get(0).getFirstName()==null ? "": customerData.get(0).getFirstName();
			String middleName=customerData.get(0).getMiddleName()==null ? "" : customerData.get(0).getMiddleName();
			String shortName=customerData.get(0).getShortName()==null ? "" : customerData.get(0).getShortName();
			String lastName=customerData.get(0).getLastName()==null ? "" : customerData.get(0).getLastName();
			String customerName=firstName+" "+middleName+" "+shortName+" "+lastName;
			setLocalName(customerName);
			setMobileNumber(customerData.get(0).getMobile());
			setNationality(customerData.get(0).getFsCountryMasterByNationality().getCountryId());
			setNationallityName(iSupplierService.getNationalityName(customerData.get(0).getFsCountryMasterByNationality().getCountryId()));
			setEmailId(customerData.get(0).getEmail());
			//Digital signatutre
			setSignatureSpecimen(customerData.get(0).getSignatureSpecimen());
			
			if(getCustomerTypeId()!=null){
				
				List<TreasuryCustomerSupplier> treasuryCustomerSupplierList=iSupplierService.getAllTreasuryCustomerSupplier(getCustomerTypeId());
				if(treasuryCustomerSupplierList.size()>0){
				recordsGet(treasuryCustomerSupplierList);
				setBooRenderDataTable(true);
				setBooSavePanel(true);
				}/*else{
					setBooRenderDataTable(false);
					setBooSavePanel(false);
				}*/
			}
			
			
		}else{
			setCustomerId(null);
			setCustomerName("");
			setLocalName(null);
			setMobileNumber(null);
			setNationality(null);
			setEmailId(null);
			setSundryDeptolRef(null);
			setCurrencyId(null);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("idNotFound.show();"); 
			
		}
	
	}

	public void clearAllFields() {
		setBankcode(null);
		setCustomerId(null);
		setBankDescription(null);
		setEmailId(null);
		setMobileNumber(null);
		setLocalName(null);
		setNationality(null);
		setSundryDeptolRef(null);
		setBankAccountNumber(null);
		setBankAccountNumber(null);
		setNationallityName(null);
		setCustomerName(null);
		setCurrencyName(null);
		setCurrencyId(null);
		setCustomerPK(null);
		setTreasuryCustomerSupplierPK(null);
		
	}

	public void saveRecord() {
		try {
			for(SupplierDataTableBean supplierBean:lstSupplierDetails){
			TreasuryCustomerSupplier dealCustomerSupplier = new TreasuryCustomerSupplier();
			dealCustomerSupplier.setDealCustomerSupplierId(supplierBean.getTreasuryCustomerPk());
			if(supplierBean.getBankId()!=null)
			{
				BankMaster bankMaster = new BankMaster();
				bankMaster.setBankId(supplierBean.getBankId());
				dealCustomerSupplier.setBankMaster(bankMaster);
			}
			
			Customer customer = new Customer();
			customer.setCustomerId(supplierBean.getCustomerPk());
			dealCustomerSupplier.setDealSupplierCustomer(customer);
			if(supplierBean.getAccountNumber()!=null && !supplierBean.getAccountNumber().equalsIgnoreCase(""))
			{
				dealCustomerSupplier.setBankAccountNo(new BigDecimal(supplierBean.getAccountNumber()));
			}
			
			if(supplierBean.getCurrencyId()!=null)
			{
				CurrencyMaster currency = new CurrencyMaster();
				currency.setCurrencyId(supplierBean.getCurrencyId());
				dealCustomerSupplier.setDealSupplierCurrency(currency);
			}
			
			dealCustomerSupplier.setCustomerName(supplierBean.getCustomerName());
			iSupplierService.sundryDebtorReference(supplierBean.getSundryDebtorRef(), supplierBean.getCustomerPk());
			dealCustomerSupplier.setCreatedDate(new Date());
			dealCustomerSupplier.setCreatedBy(sessionStateManage.getUserName());
			dealCustomerSupplier.setIsActive(Constants.Yes);
			iSupplierService.save(dealCustomerSupplier);
			}
			RequestContext.getCurrentInstance().execute("complete.show();");
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMessage(e.getMessage());
			System.out.println("Exception "+e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}

	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void suplierPageNavigation() {
		setDisableSubmit(false);
		clearAllFields();
		getBankListForBankApplicability();
		//lstSupplierDetails.clear();
		setBooRenderDataTable(false);
		setBooSavePanel(false);
		getAllCurrencyList();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "supplier.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/supplier.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void searchClicked() {
		try {
	/*		foreignCurrencyPurchaseBean.setBooFc(false);
			foreignCurrencyPurchaseBean.setBooReg(false);*/
			setIsFromSupplierBean(true);
			setBooSupplierBean(true);
			setIsFromSupplierCustref(false);
			/*specialCustomerDealRequestBean.setBooSpCustomer(false);
			searchCustomerManageBean.setBooSearchCustomer(true);
			searchCustomerManageBean.setBooPass(false);
			searchCustomerManageBean.setFinalData(null);*/
			
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("request", "supplier");
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../search/searchcustomer.xhtml");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		}


	public void populateBankDescription() {
		if(getBankcode().intValue()!=0){
			List<BankMaster> lstBankMaster = iSupplierService.getBankCountryList(sessionStateManage.getCountryId());
			for (BankMaster bankMas : lstBankMaster){
				if ((bankMas.getBankId()).equals(getBankcode())) {
					setBankDescription(bankMas.getBankFullName());
				}
			}
		}else{
			setBankDescription(null);
		}
	}
	
	public void addRecordToDataTable(){
		
		/*if(lstSupplierDetails!=null && lstSupplierDetails.size()>0)
		{
			for(SupplierDataTableBean supplierDataTableBean :lstSupplierDetails)
			{
				if(supplierDataTableBean.getCustomerRefId().equalsIgnoreCase(getCustomerId()))
				{
					RequestContext.getCurrentInstance().execute("duplicateCheck.show();");
					return;
				}
			}
		}*/
		
		if(getBankcode()!=null)
		{
			if(getCurrencyId()==null)
			{
				RequestContext.getCurrentInstance().execute("currencyCheck.show();");
				return;
			}
			
			if(getBankAccountNumber()==null || getBankAccountNumber().equalsIgnoreCase(""))
			{
				RequestContext.getCurrentInstance().execute("accNoCheck.show();");
				return;
			}
		}
		setDisableSubmit(false);
		SupplierDataTableBean supplierDataTableBean=new SupplierDataTableBean();
		supplierDataTableBean.setCustomerRefId(getCustomerId());
		supplierDataTableBean.setCustomerName(getLocalName());
		supplierDataTableBean.setMobileNumber(getMobileNumber());
		supplierDataTableBean.setNationality(getNationallityName());
		supplierDataTableBean.setEmail(getEmailId());
		supplierDataTableBean.setSundryDebtorRef(getSundryDeptolRef());
		supplierDataTableBean.setBankId(getBankcode());
		supplierDataTableBean.setBankName(bankListMap.get(getBankcode()));
		supplierDataTableBean.setBankDescription(getBankDescription());
		supplierDataTableBean.setCurrencyId(getCurrencyId());
		supplierDataTableBean.setCurrencyName(currencyListMap.get(getCurrencyId()));
		supplierDataTableBean.setAccountNumber(getBankAccountNumber());
		supplierDataTableBean.setCustomerPk(getCustomerPK());//Adding  customer Pk
		supplierDataTableBean.setTreasuryCustomerPk(getTreasuryCustomerSupplierPK());//adding Treasury Customer Supplier PK
		lstSupplierDetails.add(supplierDataTableBean);
		clearAllFields();
		setBooRenderDataTable(true);
		setBooSavePanel(true);
		
	}
	
	public void remove(SupplierDataTableBean supplierDataTableBean){
		lstSupplierDetails.remove(supplierDataTableBean);
		if(supplierDataTableBean.getTreasuryCustomerPk()!=null){
			iSupplierService.removeTreasuryCustomerRecord(supplierDataTableBean.getTreasuryCustomerPk(),sessionStateManage.getUserName());
		}
		if(lstSupplierDetails.size()<=0){
			setBooRenderDataTable(false);
			setBooSavePanel(false);
		}else{
			setBooRenderDataTable(true);
			setBooSavePanel(true);
		}
	}
	
	public void editRecord(SupplierDataTableBean supplierDataTableBean){
		setDisableSubmit(true);
		setCustomerId(supplierDataTableBean.getCustomerRefId());
		setLocalName(supplierDataTableBean.getCustomerName());
		setMobileNumber(supplierDataTableBean.getMobileNumber());
		setNationallityName(supplierDataTableBean.getNationality());
		setEmailId(supplierDataTableBean.getEmail());
		setSundryDeptolRef(supplierDataTableBean.getSundryDebtorRef());
		setBankcode(supplierDataTableBean.getBankId());
		setBankDescription(supplierDataTableBean.getBankDescription());
		setCurrencyId(supplierDataTableBean.getCurrencyId());
		setBankAccountNumber(supplierDataTableBean.getAccountNumber());
		setCustomerPK(supplierDataTableBean.getCustomerPk());
		setTreasuryCustomerSupplierPK(supplierDataTableBean.getTreasuryCustomerPk());
		lstSupplierDetails.remove(supplierDataTableBean);
		if(lstSupplierDetails.size()<=0){
			setBooRenderDataTable(false);
			setBooSavePanel(false);
		}else{
			setBooRenderDataTable(true);
			setBooSavePanel(true);
		}
	}

	public void accountNumberCheck() {
		logger.debug("Entering into accountNumberCheck method");
		logger.debug("getBankcode()" + getBankcode());
		logger.debug("getCurrencyId()" + getCurrencyId());

		List<TreasuryCustomerSupplier> treasuryCusSupList = iSupplierService.getAccountNumberDetails(getBankcode(), getCurrencyId());
		if (treasuryCusSupList.size() > 0) {
			for (TreasuryCustomerSupplier trsyCutSupplier : treasuryCusSupList) {
				if(trsyCutSupplier.getBankAccountNo()!=null && getBankAccountNumber()!=null)
				{
					if (trsyCutSupplier.getBankAccountNo().toString().equals(getBankAccountNumber())) {
						setBankAccountNumber(null);
						RequestContext.getCurrentInstance().execute("dupAcNo.show();");
						return;
					}
				}
				
			}
		}
		if (lstSupplierDetails.size() > 0) {
			for (SupplierDataTableBean supplierBean : lstSupplierDetails) {
				logger.debug("Currency "+ supplierBean.getCurrencyId());
				logger.debug("Bank id" + supplierBean.getBankId());
				logger.debug("Account Number"+ supplierBean.getAccountNumber());
				
				if(getBankAccountNumber()!=null && getBankcode()!=null && getCurrencyId()!=null && supplierBean.getCurrencyId()!=null && supplierBean.getBankId()!=null)
				{
					if(supplierBean.getCurrencyId().compareTo(getCurrencyId())==0 && supplierBean.getBankId().compareTo(getBankcode())==0)
					{
						setBankAccountNumber(null);
						RequestContext.getCurrentInstance().execute("dupAcNoDT.show();");
						return;
					}
					
				}
				// missing bank account number fix done by mohan
				/*if (supplierBean.getAccountNumber().equals(getBankAccountNumber()) && supplierBean.getCurrencyId().equals(getCurrencyId()) && supplierBean.getBankId().equals(getBankcode())) {
					setBankAccountNumber(null);
					RequestContext.getCurrentInstance().execute("dupAcNoDT.show();");
					return;
				}*/
			}
		}
		logger.debug("Exit into accountNumberCheck method");
	}
	
	public void sundryDebtorRefCheckAndAddDataTable(){

		//for checking sundry debtor reference exist or not in DB
		// blocked due to sundryDebRef can be same to all customers
		/*List<Customer> customerList=iSupplierService.getAllSundryDebtorRef(new BigDecimal(getSundryDeptolRef()));
		if(customerList!=null && customerList.size()>0)
		{
			if(customerList.get(0).getCustomerReference().compareTo(new BigDecimal(getCustomerId()))!=0)
			{
				setSundryDeptolRef(null);
				RequestContext.getCurrentInstance().execute("alreadyExist.show();");
				return;
			}

		}*/
		/*for(Customer customer:customerList){
			if(customer.getSundryDebtorReference()!=null){
				if(customer.getSundryDebtorReference().equals(getSundryDeptolRef()) && !(new BigDecimal(getCustomerId()).equals(customer.getCustomerReference()))){
					setSundryDeptolRef(null);
					RequestContext.getCurrentInstance().execute("alreadyExist.show();");
					return;

				}
			}	

		}*/
		//for checking sundry debtor reference already added to  Data table Or Not
		/*if(lstSupplierDetails.size()>0){
			for(SupplierDataTableBean supplierBean:lstSupplierDetails){
				if(supplierBean.getCustomerRefId().equals(getCustomerId())){
					if(supplierBean.getSundryDebtorRef().equals(getSundryDeptolRef())){
						//allow
					}else{
						setSundryDeptolRef(null);
						RequestContext.getCurrentInstance().execute("notAbleToAdd.show();");
						return;
					}
				}else if(supplierBean.getSundryDebtorRef().equals(getSundryDeptolRef())){
					setSundryDeptolRef(null);
					RequestContext.getCurrentInstance().execute("notAbleToAdd.show();");
					return;
				}else{
					//allow
				}
			}
		}
*/
	}
	
	public void recordsGet(List<TreasuryCustomerSupplier> treasuryCustomerSupplierList){
		
	if(lstSupplierDetails!=null){
		
			lstSupplierDetails.clear();
	}

	 List<SupplierDataTableBean> lstSupplier=new ArrayList<SupplierDataTableBean>();

		try{
		for(TreasuryCustomerSupplier treasuryCustomerSupplier:treasuryCustomerSupplierList){
			SupplierDataTableBean supplierDataTableBean=new SupplierDataTableBean();
			supplierDataTableBean.setTreasuryCustomerPk(treasuryCustomerSupplier.getDealCustomerSupplierId());//treasury customer supplier PK
			supplierDataTableBean.setCustomerRefId(treasuryCustomerSupplier.getDealSupplierCustomer().getCustomerReference().toString());
			supplierDataTableBean.setCustomerName(treasuryCustomerSupplier.getCustomerName());
			supplierDataTableBean.setMobileNumber(treasuryCustomerSupplier.getDealSupplierCustomer().getMobile());
			BigDecimal nationalityId=treasuryCustomerSupplier.getDealSupplierCustomer().getFsCountryMasterByNationality().getCountryId();
			String nationalityName = iSupplierService.getNationalityName(nationalityId);
			if(nationalityName!=null){
			supplierDataTableBean.setNationality(nationalityName);
			}
			supplierDataTableBean.setEmail(treasuryCustomerSupplier.getDealSupplierCustomer().getEmail());
			supplierDataTableBean.setSundryDebtorRef(treasuryCustomerSupplier.getDealSupplierCustomer().getSundryDebtorReference());
			if(treasuryCustomerSupplier.getBankMaster()!=null)
			{
				supplierDataTableBean.setBankId(treasuryCustomerSupplier.getBankMaster().getBankId());
				String bankFullName=iGeneralService.getBankName(treasuryCustomerSupplier.getBankMaster().getBankId());
				supplierDataTableBean.setBankName(bankFullName);
				supplierDataTableBean.setBankDescription(bankFullName);
			}
			if(treasuryCustomerSupplier.getDealSupplierCurrency()!=null)
			{
				supplierDataTableBean.setCurrencyId(treasuryCustomerSupplier.getDealSupplierCurrency().getCurrencyId());
				String currencyName=iGeneralService.getCurrencyName(treasuryCustomerSupplier.getDealSupplierCurrency().getCurrencyId());
				supplierDataTableBean.setCurrencyName(currencyName);
			}
			if(treasuryCustomerSupplier.getBankAccountNo()!=null)
			{
				supplierDataTableBean.setAccountNumber(treasuryCustomerSupplier.getBankAccountNo().toString());
			}
			
			supplierDataTableBean.setCustomerPk(treasuryCustomerSupplier.getDealSupplierCustomer().getCustomerId());
			//lstSupplierDetails.add(supplierDataTableBean);
			lstSupplier.add(supplierDataTableBean);
		}
		if(lstSupplier.size()!=0){
			setLstSupplierDetails(lstSupplier);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void viewAllRecordsFromDB(){
		setDisableSubmit(false);
		//lstSupplierDetails.clear();
		if(getCustomerId()!=null){
		List<TreasuryCustomerSupplier> treasuryCustomerSupplierList=iSupplierService.getAllTreasuryCustomerSupplier(getCustomerTypeId());
		if(treasuryCustomerSupplierList.size()>0){
		recordsGet(treasuryCustomerSupplierList);
		clearAllFields();
		setBooRenderDataTable(true);
		setBooSavePanel(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/supplier.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else{
			setBooRenderDataTable(false);
			setBooSavePanel(false);
			RequestContext.getCurrentInstance().execute("nodataFound.show();");
		}
	}
	else{
		setBooRenderDataTable(false);
		setBooSavePanel(false);
		RequestContext.getCurrentInstance().execute("enter.show();");
	}
	}
	public void getBankListForBankApplicability(){
		List<BankApplicability> bankAppliList=iGeneralService.getLocalBankList(sessionStateManage.getCountryId());
		for(BankApplicability bankApp:bankAppliList){
			bankListMap.put(bankApp.getBankMaster().getBankId(), bankApp.getBankMaster().getBankFullName());
		}
		setBankListFrmBankApp(bankAppliList);
	}

	public void getAllCurrencyList(){
		List<CurrencyMaster> currencyList=iGeneralService.getCurrencyList();
		for(CurrencyMaster currencyL:currencyList){
			currencyListMap.put(currencyL.getCurrencyId(), currencyL.getCurrencyName());
		}
		setCurrencyList(currencyList);
	}
	public void exit() throws IOException {
		//clearValues();
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	

	@SuppressWarnings("unchecked")
	public void populateSearchValue()
 {
		HttpSession session = sessionStateManage.getSession();
		SupplierBean<T> supplierBean = (SupplierBean<T>) session.getAttribute("searchSupplier");
		if (supplierBean != null) {
			
			
			setCustomerName(supplierBean.getCustomerName());
			setCustomerId(supplierBean.getCustomerId());
			setCustomerTypeId(supplierBean.getCustomerTypeId());
			setSundryDeptolRef(supplierBean.getSundryDeptolRef());
			setLocalName(supplierBean.getLocalName());
			setMobileNumber(supplierBean.getMobileNumber());
			setNationality(supplierBean.getNationality());
			setNationallityName(supplierBean.getNationallityName());
			setEmailId(supplierBean.getEmailId());
			setCustomerPK(supplierBean.getCustomerPK());
			session.removeAttribute("searchSupplier");
		}
	}
	
	public void addMoreTransaction(SupplierDataTableBean supplierObj){	
		setCustomerId(supplierObj.getCustomerRefId());
		setLocalName(supplierObj.getCustomerName());
		setMobileNumber(supplierObj.getMobileNumber());
		setNationallityName(supplierObj.getNationality());
		setEmailId(supplierObj.getEmail());
		setSundryDeptolRef(supplierObj.getSundryDebtorRef());
		setCustomerPK(supplierObj.getCustomerPk());
		setTreasuryCustomerSupplierPK(supplierObj.getTreasuryCustomerPk());
		
	}
	
	public void clearValues(){
		setBankcode(null);
		setCustomerId(null);
		setBankDescription(null);
		setEmailId(null);
		setMobileNumber(null);
		setLocalName(null);
		setNationality(null);
		setSundryDeptolRef(null);
		setBankAccountNumber(null);
		setBankAccountNumber(null);
		setNationallityName(null);
		setCustomerName(null);
		setCurrencyName(null);
		setCurrencyId(null);
		setCustomerPK(null);
		setTreasuryCustomerSupplierPK(null);
		lstSupplierDetails.clear();
		setLstSupplierDetails(null);
		setBooRenderDataTable(false);
		setBooSavePanel(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/supplier.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setData(List<SupplierDataTableBean> lstSupplierDetails){
		
		lstSupplierDetails.addAll(lstSupplierDetails);
		setBooRenderDataTable(true);
		setBooSavePanel(true);
		
	}
	public void checkBankAccNoisNumber(FacesContext context, UIComponent component, Object value) {
		try {
		BigDecimal val = new BigDecimal(value.toString());
		@SuppressWarnings("unused")
		boolean isNUmber = CollectionUtil.isIntegerValue(val);
		} catch (Exception e) {
		FacesMessage msg = new FacesMessage("  Bank AccountNo should be number", " Bank AccountNo should be number");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(msg);
		}
		}
	public void checkSundryDebtor(FacesContext context, UIComponent component, Object value) {
		try {
		BigDecimal val = new BigDecimal(value.toString());
		@SuppressWarnings("unused")
		boolean isNUmber = CollectionUtil.isIntegerValue(val);
		} catch (Exception e) {
		FacesMessage msg = new FacesMessage("  Sundry Debtor Reference should be number", " Sundry Debtor Reference should be number");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(msg);
		}
		}
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
	
}
