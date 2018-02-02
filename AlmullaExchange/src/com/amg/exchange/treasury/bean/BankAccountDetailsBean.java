package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.bankBranchUpload.service.BankBranchUploadService;
import com.amg.exchange.common.model.BankAccountType;
import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.jvprocess.model.ViewGeneralValidationGlNo;
import com.amg.exchange.treasury.model.BankAccount;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.IBankAccountDetailsService;
import com.amg.exchange.treasury.service.IBankAccountService;
import com.amg.exchange.treasury.service.IBankApprovalService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestApprovService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("bankaccountdetails")
@Scope("session")
public class BankAccountDetailsBean<T> implements Serializable {

	/**
	 * 
	 */  
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(BankAccountDetailsBean.class);

	private BigDecimal bankAccountDetId = null;// PK
	private BigDecimal bankId = null;
	private String accountNo = null;
	private String accountType = null;
	private String accountDesc = null;
	private BigDecimal currency = null;
	private BigDecimal minBalance = null;
	private BigDecimal odLimit = null;
	private String glNo = null;
	private BigDecimal countryId = null;
	private String telephone = null;
	private String fax = null;
	private String email = null;
	private String contactPerson = null;
	private String department = null;
	private String designation = null;
	private String mobile = null;
	private String contPersonLocal = null;
	private String departmentLocal = null;
	private String designationLocal = null;
	private String fundGlNo ;
	private String ctrlGlNo;
	private BigDecimal internalMinBalance = null;
	private Boolean accountdetail = true;
	private Boolean contactdetail = false;
	private Boolean contactdetaillocal = false;

	private String countryShow = null;
	private String bankShow = null;
	private Map<BigDecimal,String> mapCountry  = new HashMap<BigDecimal,String>();
	private Map<BigDecimal,String> mapBank  = new HashMap<BigDecimal,String>();
	private List<BankMaster> currencyOfBank = new ArrayList<BankMaster>();
	private List<BankAccountTypeDesc> accountTypeList=new ArrayList<BankAccountTypeDesc>();

	private List<BankMaster> bankMasterList;
	private List<BankAccountDetails> bankAccountDetailsList =new ArrayList<BankAccountDetails>();

	private int accNumberSize;
	private Boolean boobankAcc=true;
	private Boolean booSelectbankAcc=false;
	private BigDecimal accountNumber = null;
	private BigDecimal accountId = null;

	private int bankcurrencySize;
	private Boolean boobankCurrency=true;
	private Boolean booSelectbankCurrency=false;
	private String bankCurrencyName = null;
	private BigDecimal bankCurrencyId = null;

	private Date createDate;
	private String creatyedBy;

	private BigDecimal applicationCountryId;
	private  String errorMesssage;
	private List<CountryMasterDesc> allCountryList = null;
	private List<BankMaster> bankBranchList = null;
	private BigDecimal bankBranch;


	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IBankAccountDetailsService<T> iBankAccountDetService;

	@Autowired
	IFundEstimationService<T> fundEstimationService;
	
	@Autowired
	BankBranchUploadService bankBranchUploadService;



	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public BigDecimal getBankAccountDetId() {
		return bankAccountDetId;
	}

	public void setBankAccountDetId(BigDecimal bankAccountDetId) {
		this.bankAccountDetId = bankAccountDetId;
	}

	public List<BankMaster> getCurrencyOfBank() {
		return currencyOfBank;
	}

	public void setCurrencyOfBank(List<BankMaster> currencyOfBank) {
		this.currencyOfBank = currencyOfBank;
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

	public BigDecimal getAccountId() {
		return accountId;
	}

	public void setAccountId(BigDecimal accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountDesc() {
		return accountDesc;
	}

	public void setAccountDesc(String accountDesc) {
		this.accountDesc = accountDesc;
	}

	public BigDecimal getCurrency() {
		return currency;
	}

	public void setCurrency(BigDecimal currency) {
		this.currency = currency;
	}

	public String getGlNo() {
		return glNo;
	}

	public void setGlNo(String glNo) {
		this.glNo = glNo;
	}

	/*
	 * public String getAddress1() { return address1; } public void
	 * setAddress1(String address1) { this.address1 = address1; } public String
	 * getAddress2() { return address2; } public void setAddress2(String
	 * address2) { this.address2 = address2; }
	 */
	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContPersonLocal() {
		return contPersonLocal;
	}

	public void setContPersonLocal(String contPersonLocal) {
		this.contPersonLocal = contPersonLocal;
	}

	public String getDepartmentLocal() {
		return departmentLocal;
	}

	public void setDepartmentLocal(String departmentLocal) {
		this.departmentLocal = departmentLocal;
	}

	public String getDesignationLocal() {
		return designationLocal;
	}

	public void setDesignationLocal(String designationLocal) {
		this.designationLocal = designationLocal;
	}

	public String getFundGlNo() {
		return fundGlNo;
	}

	public void setFundGlNo(String fundGlNo) {
		this.fundGlNo = fundGlNo;
	}

	public BigDecimal getMinBalance() {
		return minBalance;
	}

	public void setMinBalance(BigDecimal minBalance) {
		this.minBalance = minBalance;
	}

	public BigDecimal getOdLimit() {
		return odLimit;
	}

	public void setOdLimit(BigDecimal odLimit) {
		this.odLimit = odLimit;
	}

	public BigDecimal getInternalMinBalance() {
		return internalMinBalance;
	}

	public void setInternalMinBalance(BigDecimal internalMinBalance) {
		this.internalMinBalance = internalMinBalance;
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public IBankAccountDetailsService<T> getiBankAccountDetService() {
		return iBankAccountDetService;
	}

	public void setiBankAccountDetService(IBankAccountDetailsService<T> iBankAccountDetService) {
		this.iBankAccountDetService = iBankAccountDetService;
	}

	public List<BankMaster> getBankMasterList() {
		return bankMasterList;
	}

	public void setBankMasterList(List<BankMaster> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatyedBy() {
		return creatyedBy;
	}

	public void setCreatyedBy(String creatyedBy) {
		this.creatyedBy = creatyedBy;
	}
	
	

	public String getCtrlGlNo() {
		return ctrlGlNo;
	}

	public void setCtrlGlNo(String ctrlGlNo) {
		this.ctrlGlNo = ctrlGlNo;
	}

	public List<CountryMasterDesc> getAllCountryList() {
		return allCountryList;
	}

	public void setAllCountryList(List<CountryMasterDesc> allCountryList) {
		this.allCountryList = allCountryList;
	}

	public BigDecimal getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(BigDecimal bankBranch) {
		this.bankBranch = bankBranch;
	}
	
	public BankBranchUploadService getBankBranchUploadService() {
		return bankBranchUploadService;
	}

	public void setBankBranchUploadService(
			BankBranchUploadService bankBranchUploadService) {
		this.bankBranchUploadService = bankBranchUploadService;
	}
	
	public List<BankMaster> getBankBranchList() {
		return bankBranchList;
	}

	public void setBankBranchList(List<BankMaster> bankBranchList) {
		this.bankBranchList = bankBranchList;
	}





	SessionStateManage sessionStateManage = new SessionStateManage();

	/*
	 * private String userName=FacesContext.getCurrentInstance()
	 * .getExternalContext().getSessionMap().get("userName") .toString();
	 */
	/*
	 * public List<CountryMasterDesc> getCountryNameList() {
	 * 
	 * 
	 * SessionStateManage sessionStateManage = new SessionStateManage(); return
	 * getGeneralService().getCountryList(new
	 * BigDecimal(sessionStateManage.isExists
	 * ("languageId")?sessionStateManage.getSessionValue("languageId"):"1")); }
	 */



	/**
	 * Responsible to populate Country
	 * 
	 * @return
	 */
	/*	public List<CountryMasterDesc> getCountryNameList() {
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<CountryMasterDesc> lstCountry = getGeneralService().getCountryList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1));
		for(CountryMasterDesc countryMasterDesc:lstCountry) {
			mapCountry.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
		}
		return lstCountry;
	}*/

	public List<BankCountryPopulationBean> getCountryNameList() {
		/*List<CountryMasterDesc> lstCountry = getGeneralService()
				.getCountryList(
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "" + 1));*/
		/*
		 *//**
		 * Responsible to populate Bank Country from Bank Master
		 * 
		 * @return
		 *//*
		public List<BankCountryPopulationBean> getBankCountryList() {*/
		List<BankCountryPopulationBean> countryList=null;
		//return generalService.getBankContry(sessionStateManage.getCountryId());
		try{
			countryList=generalService.getAllBankContry();
			for(BankCountryPopulationBean countryObj:countryList){
				countryMap.put(countryObj.getBankCountryId(), countryObj.getBankCountryName());
			}
		}catch(NullPointerException  e){
			setErrorMesssage("Method Name: getCountryNameList "+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		return countryList;
		/*}
		return lstCountry;*/
	}



	public List<BankAccountTypeDesc> getAccountTypeList() {
		return iBankAccountDetService.getAccountTypeList(sessionStateManage.getLanguageId());
	}

	public void setAccountTypeList(List<BankAccountTypeDesc> accountTypeList) {
		this.accountTypeList = accountTypeList;
	}

	public List<BankAccountDetails> getBankAccountDetailsList() {
		return bankAccountDetailsList;
	}

	public void setBankAccountDetailsList(List<BankAccountDetails> bankAccountDetailsList) {
		this.bankAccountDetailsList = bankAccountDetailsList;
	}

	public Boolean getAccountdetail() {
		return accountdetail;
	}

	public void setAccountdetail(Boolean accountdetail) {
		this.accountdetail = accountdetail;
	}

	public Boolean getContactdetail() {
		return contactdetail;
	}

	public void setContactdetail(Boolean contactdetail) {
		this.contactdetail = contactdetail;
	}

	public Boolean getContactdetaillocal() {
		return contactdetaillocal;
	}

	public void setContactdetaillocal(Boolean contactdetaillocal) {
		this.contactdetaillocal = contactdetaillocal;
	}


	public String getCountryShow() {
		return countryShow;
	}

	public void setCountryShow(String countryShow) {
		this.countryShow = countryShow;
	}

	public String getBankShow() {
		return bankShow;
	}

	public void setBankShow(String bankShow) {
		this.bankShow = bankShow;
	}

	public void nextAccountDetails() {
		//setCountryShow(getCountryNameList().get(0).getBankCountryName());
		/*
		 * 17-02-2015 Start
		 */
		setCountryShow(CustomerSpecialDealRequest.countryName(sessionStateManage.getCountryId(), sessionStateManage.getLanguageId()));
		/*
		 * End modified by Ramakrishna
		 */

		setBankShow(mapBank.get(getBankId()));
		setAccountdetail(false);
		setContactdetail(true);
		if (getCountryId() != null && getCountryId().intValue() != sessionStateManage.getCountryId().intValue()) {
			setContactdetaillocal(false);
		} else {
			setContactdetaillocal(true);
		}
	}

	public void backAccountDetails() {

		setAccountdetail(true);
		setContactdetail(false);

	}

	/**
	 * method is responsible to populate List of Bank from DB
	 * 
	 * @return
	 */

	public void popBank() {
		try{
			clearDetails();

			bankMasterList = new ArrayList<BankMaster>();

			SessionStateManage sessionStateManage = new SessionStateManage();

			bankMasterList.addAll(getGeneralService().getAllBankList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"), getCountryId()));
			for(BankMaster bankmaster:bankMasterList){
				mapBank.put(bankmaster.getBankId(), bankmaster.getBankFullName());

			}
		}catch(NullPointerException  e){
			setErrorMesssage("Method Name: popBank "+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	/*	private List<BankMaster> currencyList = new ArrayList<BankMaster>();

	public List<BankMaster> getCurrencyList() {
		return currencyList;
	}*/

	/*public void popCurrency(AjaxBehaviorEvent e){
		currencyList = new ArrayList<BankMaster>();
		currencyList.addAll(getiBankAccountDetService().getCurrencyOfBank(getCountryId(), getBankId()));

	}*/

	/**
	 * Responsible to fetch Account No.
	 * 
	 * @return
	 */
	public void fetchData()
	{
		try{
			List<BankAccountDetails> lstBankAccDtls =iBankAccountDetService.getBankAccountDetails(getCountryId(),getBankId(), getAccountNo());

			for(BankAccountDetails bankAccountDetails :lstBankAccDtls)
			{
				setCountryId(bankAccountDetails.getFsCountryMaster().getCountryId());

				if(bankAccountDetails.getApplicationCountryId()!=null) {
					setApplicationCountryId(bankAccountDetails.getApplicationCountryId());
				}
				//setAccountType(bankAccountDetails.getAcctType());
				setMinBalance(bankAccountDetails.getMinBal());
				setOdLimit(bankAccountDetails.getOdlmt());
				setGlNo(bankAccountDetails.getGlno());
				setFundGlNo(bankAccountDetails.getFundGlno());
				setInternalMinBalance(bankAccountDetails.getIntMinbal());
				setContactPerson(bankAccountDetails.getContactPerson());
				setDepartment(bankAccountDetails.getContactDept());
				setDesignation(bankAccountDetails.getContactDesg());
				setContPersonLocal(bankAccountDetails.getContactPersonAr());
				setDepartmentLocal(bankAccountDetails.getContactDeptAr());
				setDesignationLocal(bankAccountDetails.getContactDesgAr());
				setMobile(bankAccountDetails.getMobile());
				setTelephone(bankAccountDetails.getTelephoneNo());
				setFax(bankAccountDetails.getFaxno());
				setEmail(bankAccountDetails.getEmail());
				setCurrency(bankAccountDetails.getFsCurrencyMaster().getCurrencyId());
				setAccountType(bankAccountDetails.getBankAccountType().getBankAccountTypeId().toString());
				setBankAccountDetId(bankAccountDetails.getBankAcctDetId());
				setBankAccountpkId(bankAccountDetails.getBankAcctDetId());
				setCreateDate(bankAccountDetails.getCreateDate());
				setCreatyedBy(bankAccountDetails.getCreator());
				setBankAccountId(bankAccountDetails.getBankAccountId().getBankAccountId());
				setCtrlGlNo(bankAccountDetails.getCtrlGlNo());
			}
		}catch(NullPointerException  e){
			setErrorMesssage("Method Name: fetchData  "+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}
	public void popBankAccountNo() throws ParseException{
		/*System.out.println("bankAccountDetailsList  Strart..");
		//bankAccountDetailsList = new ArrayList<BankAccount>(); 
		bankAccountDetailsList = iBankAccountDetService.getBankAccountNoFromBankAccount(getCountryId(), getBankId());*/
		setAccountNo(null);
		setCurrency(null);
		clearAccount();
		//to populate currency with acc number
		//List<BankAccount> ptabledata = iBankAccountDetService.getBankAccountNoFromBankAccount(getCountryId(), getBankId());
		try{	
			List<BankAccountDetails> ptabledata = iBankAccountDetService.getDataForView(getCountryId(), getBankId());

			accNumberSize = ptabledata.size();
			if(accNumberSize!=0){
				bankAccountDetailsList.clear();
				setBankAccountDetailsList(null);
				/*for (BankAccount element : ptabledata) {			
				setAccountId(element.getBankAccountId());
				setAccountNo(element.getDebitAcct());				
			}*/
				setBankAccountDetailsList(ptabledata);
				/*setBoobankAcc(true);
			setBooSelectbankAcc(false);*/
				setEditButton(true);;
				setBooSystemAccountNo(true);
				setBooUpdateAccountNo(false);


			}else{
				/*setBoobankAcc(false);
			setBooSelectbankAcc(true);*/

				setEditButton(false);;
				setBooSystemAccountNo(false);
				setBooUpdateAccountNo(true);
			}

			fetchData();
		}catch(NullPointerException  e){
			setErrorMesssage("popBankAccountNo"+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void getAccountNumber()
	{
		clearAccount();
		fetchData();
	}


	/*public void bankCurrency() throws ParseException{

	    clearDetails();
	    clearAccount();
		setCurrency(null);
		setAccountNo(null);
		popBankAccountNo();


        List<BankMaster> pbankcurrency = getiBankAccountDetService().getCurrencyOfBank(getCountryId(), getBankId());

		bankcurrencySize = pbankcurrency.size();
		if(bankcurrencySize<=1){

			for (BankMaster element : pbankcurrency) {	
				setBankCurrencyId(element.getCurrencyMaster().getCurrencyId());
				setBankCurrencyName(element.getCurrencyMaster().getCurrencyName());//currencyname setted 
			}
			setCurrency(getBankCurrencyId().toPlainString());
			setBoobankCurrency(true);
			setBooSelectbankCurrency(false);			
		}else{
			setBoobankCurrency(false);
			setBooSelectbankCurrency(true);
		}
		popBankAccountNo();
		setCurrencyOfBank(pbankcurrency);
	}*/

	private BigDecimal bankAccountpkId;

	public BigDecimal getBankAccountpkId() {
		return bankAccountpkId;
	}

	public void setBankAccountpkId(BigDecimal bankAccountpkId) {
		this.bankAccountpkId = bankAccountpkId;
	}

	/**
	 * Responsible to save Bank Account details
	 * 
	 * @return
	 */
	public  HashMap<String,  Object> save(BankAccount bankAccNum) {

		/*BigDecimal accountId=null;

		if(getBankAccountDetId()!=null)
		{
			List<AccountBalance> lstAccountBalance=iBankAccountDetService.getAccountBalance(getAccountNo(),getBankAccountDetId());
			if(lstAccountBalance.size()>0)
			{
				AccountBalance accountBalance=lstAccountBalance.get(0);
				accountId=accountBalance.getAccountId();
			}
		}*/

		HashMap<String,  Object> bankHashMap=new HashMap<String, Object>();

		BankAccountDetails accountdetails = new BankAccountDetails();

		accountdetails.setApplicationCountryId(sessionStateManage.getCountryId());
		accountdetails.setBankAcctDetId(getBankAccountpkId());


		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(getCountryId());
		accountdetails.setFsCountryMaster(countryMaster);

		CompanyMaster companymaster = new CompanyMaster();
		companymaster.setCompanyId(sessionStateManage.getCompanyId());
		accountdetails.setFsCompanyMaster(companymaster);

		BankMaster bankmaster = new BankMaster();
		bankmaster.setBankId(getBankId());
		accountdetails.setExBankMaster(bankmaster);

		accountdetails.setBankAcctNo(getAccountNo());
		//accountdetails.setAcctType(getAccountType());

		BankAccountType bankAccountType=new BankAccountType();
		bankAccountType.setBankAccountTypeId(new BigDecimal(getAccountType()));
		accountdetails.setBankAccountType(bankAccountType);

		CurrencyMaster currencyMaster = new CurrencyMaster();
		currencyMaster.setCurrencyId(getCurrency());

		BankAccount bankAcc = new BankAccount();
		bankAcc.setBankAccountId(bankAccNum.getBankAccountId());
		accountdetails.setBankAccountId(bankAcc);

		/*for (BankAccount element : bankAccountDetailsList) {
			if(element.getDebitAcct().equalsIgnoreCase(getAccountNo())) {
				bankAcc.setBankAccountId(element.getBankAccountId());
				accountdetails.setBankAccountId(bankAcc);
			}
		}*/

		//bankAcc.setBankAccountId(iBankAccountDetService.getBankAccontId(getCountryId(), getBankId(), getb));

		accountdetails.setFsCurrencyMaster(currencyMaster);
		accountdetails.setMinBal(getMinBalance());
		accountdetails.setOdlmt(getOdLimit());
		accountdetails.setGlno(getGlNo());
		accountdetails.setTelephoneNo(getTelephone());
		accountdetails.setFaxno(getFax());
		accountdetails.setEmail(getEmail());
		accountdetails.setContactPerson(getContactPerson());
		accountdetails.setContactDept(getDepartment());
		accountdetails.setContactDesg(getDesignation());
		accountdetails.setMobile(getMobile());
		accountdetails.setContactPersonAr(getContPersonLocal());
		accountdetails.setContactDeptAr(getDepartmentLocal());
		accountdetails.setContactDesgAr(getDesignationLocal());
		accountdetails.setFundGlno(getFundGlNo());
		accountdetails.setIntMinbal(getInternalMinBalance());
		accountdetails.setCtrlGlNo(getCtrlGlNo());
		
		if (getBankAccountDetId() != null && getBankAccountDetId().intValue() != 0) {
			accountdetails.setRecordStatus(Constants.U);
			if(getCreatyedBy() != null){
				accountdetails.setCreator(getCreatyedBy());
				accountdetails.setCreateDate(getCreateDate());
			}else{
				accountdetails.setCreator(sessionStateManage.getUserName());
				accountdetails.setCreateDate(new Date());
			}
			accountdetails.setModifier(sessionStateManage.getUserName());
			accountdetails.setUpdateDate(new Date());
		} else {
			accountdetails.setRecordStatus(Constants.U);
			accountdetails.setCreateDate(new Date());
			accountdetails.setCreator(sessionStateManage.getUserName());
		}

		bankHashMap.put( "BankAccountDeatials1", accountdetails );
		//getiBankAccountDetService().saveBankAccountDetails(accountdetails);

		// blocked due to not insert or update in AccountBalance Table
		/*AccountBalance accountBal = new AccountBalance();

		if(accountId!=null)
		{
			accountBal.setAccountId(accountId);
		}

		if(getAccountNo() != null){
			accountBal.setAccountNo(getAccountNo());
		}

		accountBal.setExCurrencyMaster(currencyMaster);

		if(accountdetails != null){
			accountBal.setExBankAccountDetails(accountdetails);
		}

		if(getCountryId() != null){
			countryMaster.setCountryId(getCountryId());
			accountBal.setFsCountryMaster(countryMaster);
		}

		companymaster.setCompanyId(sessionStateManage.getCompanyId());
		accountBal.setCompanyMaster(companymaster);

		if(getBankId() != null){
			bankmaster.setBankId(getBankId());
			accountBal.setBankMaster(bankmaster);
		}

		if(getGlNo()!= null){
			accountBal.setGeneralLeaderNo(getGlNo());
		}

		if(getFundGlNo() != null){
			accountBal.setGlSlNumber(getFundGlNo());
		}

		accountBal.setCreatedBy(sessionStateManage.getUserName());
		accountBal.setCreatedDate(new Date());		

		bankHashMap.put("accountBal", accountBal);*/

		return bankHashMap;
		//getiBankAccountDetService().saveBankAccountDetailsId(accountBal);


		//RequestContext.getCurrentInstance().execute("complete.show();");
		//	countryMap.clear();
		//mapBank.clear();
		//return "success";

	}

	/**
	 * for fetching the records if Account No Already Exists
	 * 
	 */

	public void fetchBankAccountDetails() {
		try{
			List<BankAccountDetails> bankaccountdetailslist = new ArrayList<BankAccountDetails>();
			if (getAccountNo() != null && getAccountNo().length() > 0) {
				System.out.println("=================" + getAccountNo());
				bankaccountdetailslist = getiBankAccountDetService().getBankAccountDetails(getBankId(),getAccountNo());
				if (bankaccountdetailslist != null && bankaccountdetailslist.size() > 0) {

					if(bankaccountdetailslist.get(0).getApplicationCountryId()!=null) 
					{
						setApplicationCountryId(bankaccountdetailslist.get(0).getApplicationCountryId());
					}

					setBankAccountDetId(bankaccountdetailslist.get(0).getBankAcctDetId());
					setCountryId(bankaccountdetailslist.get(0).getFsCountryMaster().getCountryId());
					setBankId(bankaccountdetailslist.get(0).getExBankMaster().getBankId());
					setAccountNo(bankaccountdetailslist.get(0).getBankAcctNo());
					setAccountType(bankaccountdetailslist.get(0).getBankAccountType().getBankAccountTypeId().toString());
					setCurrency(bankaccountdetailslist.get(0).getFsCurrencyMaster().getCurrencyId());
					setMinBalance(bankaccountdetailslist.get(0).getMinBal());
					setOdLimit(bankaccountdetailslist.get(0).getOdlmt());
					setGlNo(bankaccountdetailslist.get(0).getGlno());
					setTelephone(bankaccountdetailslist.get(0).getTelephoneNo());
					setFax(bankaccountdetailslist.get(0).getFaxno());
					setEmail(bankaccountdetailslist.get(0).getEmail());
					setContactPerson(bankaccountdetailslist.get(0).getContactPerson());
					setDepartment(bankaccountdetailslist.get(0).getContactDept());
					setDesignation(bankaccountdetailslist.get(0).getContactDesg());
					setContPersonLocal(bankaccountdetailslist.get(0).getContactPersonAr());
					setDepartmentLocal(bankaccountdetailslist.get(0).getContactDeptAr());
					setDesignationLocal(bankaccountdetailslist.get(0).getContactDesgAr());
					setFundGlNo(bankaccountdetailslist.get(0).getFundGlno());
					setInternalMinBalance(bankaccountdetailslist.get(0).getIntMinbal());
				} else {
					String accountno = getAccountNo();
					reset();
					setAccountNo(accountno);
				}
			}
		}catch(NullPointerException  e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	/**
	 * for Clear the field
	 * 
	 */
	public void reset() {
		setBankAccountId(null);
		setBankAccountDetId(null);
		setBankId(null);
		setBankAccountpkId(null);
		setAccountType("");
		setAccountNo("");
		setAccountDesc("");
		setCurrency(null);
		setMinBalance(null);
		setOdLimit(null);
		setGlNo("");
		// setAddress1("");
		// setAddress2("");
		setCountryId(null);
		setTelephone("");
		setFax("");
		setMobile("");
		setEmail("");
		setContactPerson("");
		setDepartment("");
		setDesignation("");
		setContPersonLocal("");
		setDepartmentLocal("");
		setDesignationLocal("");
		setFundGlNo("");
		setInternalMinBalance(null);
		setAccountdetail(true);
		setContactdetail(false);
		setContactdetaillocal(false);
		setBoobankAcc(true);
		setBooSelectbankAcc(false);
		setBankCurrencyName(null);
		setBoobankCurrency(true);
		setBooSelectbankCurrency(false);
		currencyOfBank.clear();
		setEditButton(false);;
		setBooSystemAccountNo(false);
		setBooUpdateAccountNo(true);
		setCtrlGlNo(null);
	}


	public void clearAccount() {

		setAccountType(null);
		setBankAccountpkId(null);
		setMinBalance(null);
		setOdLimit(null);
		setGlNo("");
		setTelephone(null);
		setFax(null);
		setMobile(null);
		setEmail(null);
		setContactPerson(null);
		setDepartment(null);
		setDesignation(null);
		setContPersonLocal(null);
		setDepartmentLocal(null);
		setDesignationLocal(null);
		setFundGlNo("");
		setInternalMinBalance(null);
		setCtrlGlNo(null);
		//setAccountdetail(true);
		//setContactdetail(false);
		//setContactdetaillocal(false);
		//setBoobankAcc(true);
		//setBooSelectbankAcc(false);
		//setBankCurrencyName(null);
		//setBoobankCurrency(true);
		//setBooSelectbankCurrency(false);
		currencyOfBank.clear();
	}


	public void clearDetails() {
		setBoobankAcc(true);
		setBooSelectbankAcc(false);
		setCurrency(null);
		setBankAccountpkId(null);
		setBankCurrencyName(null);
		setBoobankCurrency(true);
		setBooSelectbankCurrency(false);
		currencyOfBank.clear();
	}

	public void cancel() {
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../login/login.xhtml");
		} catch(NullPointerException  e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	/**
	 * For Exit Button
	 */
	public void exit() {
		reset();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch(NullPointerException  e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	
	public void clearCache() throws IOException {

		reset();

		setAccountdetail(true);
		setContactdetail(false);
		setRenderDataTable(false);
		bankAccountDetailsList.clear();
		loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankaccountdetails.xhtml");
		FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankaccountdetails.xhtml");
		getCurrencyList();
	}


	@Autowired
	ISpecialCustomerDealRequestApprovService<T> CustomerSpecialDealRequest;

	List<CurrencyMaster> lstCurrencyMaster = new ArrayList<CurrencyMaster>();


	public List<CurrencyMaster> getLstCurrencyMaster() {
		return lstCurrencyMaster;
	}

	public void setLstCurrencyMaster(List<CurrencyMaster> lstCurrencyMaster) {
		this.lstCurrencyMaster = lstCurrencyMaster;
	}

	public void getCurrencyList()
	{
		List<CurrencyMaster> lstDbCurrencyMaster=generalService.getCurrencyList();
		for(CurrencyMaster curencyObj:lstDbCurrencyMaster){
			courrencyMap.put(curencyObj.getCurrencyId(), curencyObj.getCurrencyName());
			mapCurrencyCode.put(curencyObj.getCurrencyId(), curencyObj.getQuoteName());
		}
		setLstCurrencyMaster(lstDbCurrencyMaster);
	}
	
	public void clickOnOk(){
		reset();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankaccountdetails.xhtml");
		} catch(NullPointerException  e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	//Attaching two pages Account and Acoount Details

	@Autowired
	IBankAccountService<T> ibankAccountService;

	private BigDecimal bankAccountId = null; // PK

	public BigDecimal getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(BigDecimal bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	/*public void dulicateCheckAccountNo(){

		List<BankAccount> matchAccountNo= new ArrayList<BankAccount>();
		matchAccountNo.addAll(ibankAccountService.getDuplicateAccountNo(getBankId(), getAccountNo()));
		System.out.println("==============Size============="+matchAccountNo.size());
		if(matchAccountNo.size()>0){
			RequestContext.getCurrentInstance().execute("recordExists.show();");
		}else{
			save();
			setBooDuplicateAcc(false);
		}
	}*/

	public void saveBankAccount() {

		HashMap< String, Object> saveHashMap=new HashMap<String, Object>();

		List<BankAccountDetails> checkList = new ArrayList<BankAccountDetails>();
		try{	
			if(getGlNo() != null || getFundGlNo() != null || getCtrlGlNo() != null){
				boolean checkGLNO=checktoValidateGlNo();
				if(!checkGLNO){
					return;
				}
				boolean fundGLNO=checktoValidateFundGlNo() ;
				if(!fundGLNO){
					return;
				}
				boolean ctrlGLNO= checktoValidatectrlGlNo(); 
				if (!ctrlGLNO) {
					return;
				}
			}
			checkList = iBankAccountDetService.getBankAccountDetails(getBankId(),getAccountNo());

			if(checkList.size()!=0 && getBankAccountDetId()==null){
				System.out.println("list size ===============");
				RequestContext.getCurrentInstance().execute("duplicatecode.show();");
			}else{
				BankAccount bankaccount = new BankAccount();

				bankaccount.setBankAccountId(getBankAccountId());

				CountryMaster countryMaster =  new CountryMaster();
				countryMaster.setCountryId(sessionStateManage.getCountryId()); //application not the bank country
				bankaccount.setFsCountryMaster(countryMaster);

				CompanyMaster companymaster = new CompanyMaster();
				companymaster.setCompanyId(sessionStateManage.getCompanyId());
				bankaccount.setFsCompanyMaster(companymaster);

				BankMaster bankmaster = new BankMaster();
				bankmaster.setBankId(getBankId());
				bankaccount.setExBankMaster(bankmaster);

				bankaccount.setCurrencyId(getCurrency());
				bankaccount.setDebitAcct(getAccountNo());

				if (getBankAccountId() != null && getBankAccountId().intValue() != 0) {

					bankaccount.setModifier(sessionStateManage.getUserName());
					bankaccount.setUpdateDate(new Date());
					bankaccount.setRecordStatus(Constants.U);
					if(getCreatyedBy() != null){
						bankaccount.setCreator(getCreatyedBy());
						bankaccount.setCreateDate(getCreateDate());
					}else{
						bankaccount.setCreator(sessionStateManage.getUserName());
						bankaccount.setCreateDate(new Date());
					}
				} else {
					bankaccount.setCreator(sessionStateManage.getUserName());
					bankaccount.setCreateDate(new Date());
					bankaccount.setRecordStatus(Constants.U);
				}
				//ibankAccountService.saveBankAccount(bankaccount);
				// Saving Details Table
				HashMap<String,  Object>  hashMapObj = save(bankaccount); 

				BankAccountDetails bankAccountDeatis=(BankAccountDetails)hashMapObj.get("BankAccountDeatials1");
				//AccountBalance accountBal=(AccountBalance)hashMapObj.get("accountBal" );
				saveHashMap.put("BankAccountSave", bankaccount);
				saveHashMap.put("BankAccountDetails", bankAccountDeatis);
				//saveHashMap.put("AccountBalance", accountBal);
				iBankAccountDetService.saveBankAccountDetails(saveHashMap);
				RequestContext.getCurrentInstance().execute("complete.show();");
				countryMap.clear();
				mapBank.clear();
				return;
			}
		}catch(NullPointerException  e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}


	/* New Code by viswa   */


	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	@Autowired
	IBankApprovalService bankApprovalService;

	List<BankAccountDataTable> approvalList = new ArrayList<BankAccountDataTable>();
	List<BankAccountDataTable> activationList = new ArrayList<BankAccountDataTable>();

	private boolean approval=false;
	private String createdBy = null;
	private Date createdDate = null;
	private String remarks=null;	
	private String isActive = null;


	public void approvalNavigation() {
		approvalList.clear();
		setApproval(true);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankaccountapprovallist.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../treasury/bankaccountapprovallist.xhtml");
		} catch(NullPointerException  e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void activeNavigation() {
		activationList.clear();
		setRemarks( null);
		try {
			setCountryId(null);
			setBankBranch(null);
			setBankBranchList(null);
			List<CountryMasterDesc> countryList = bankBranchUploadService.getAllCountryList(sessionStateManage.getLanguageId());
			if(countryList!=null && countryList.size() > 0) {
				setAllCountryList(countryList);
			}
			
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankaccountactiveinactivelist.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../treasury/bankaccountactiveinactivelist.xhtml");
		} catch(NullPointerException  e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	
	public void bankLists(){
		List<BankMaster> bankList = bankBranchUploadService.getBankList(getCountryId());		
		if(bankList!=null && bankList.size() > 0){
			setBankBranchList(bankList);
		} else {
			setBankBranch(null);
			setBankBranchList(null);
		}
	}
	
	
	public void datatableList() {
		activationList.clear();
		if(getCountryId() ==null){
			RequestContext.getCurrentInstance().execute("countryMandatory.show();");
		}else{
			BankAccountDataTable bankAccountDataTable = null;

			//service change
			List<BankAccountDetails> bankAccountList =  bankApprovalService.getBankAccountForActiveInactive(getCountryId(),getBankBranch());

			if(bankAccountList.size()>0){
				for (BankAccountDetails bankAccountdetails : bankAccountList) {
					try{
						bankAccountDataTable = new BankAccountDataTable();
						bankAccountDataTable.setBankAccountDetId( bankAccountdetails.getBankAcctDetId());
						bankAccountDataTable.setAccountNo(bankAccountdetails.getBankAcctNo());
						bankAccountDataTable.setBankId(bankAccountdetails.getExBankMaster().getBankId());
						bankAccountDataTable.setBankCurrencyId(bankAccountdetails.getFsCurrencyMaster().getCurrencyId());
						bankAccountDataTable.setContactPerson(bankAccountdetails.getContactPerson());
						bankAccountDataTable.setBankCode(bankAccountdetails.getExBankMaster().getBankCode());
						bankAccountDataTable.setTelephone(bankAccountdetails.getTelephoneNo() );
						bankAccountDataTable.setFax( bankAccountdetails.getFaxno());
						bankAccountDataTable.setEmail( bankAccountdetails.getEmail());

						if(bankAccountdetails.getApplicationCountryId()!=null)
						{
							bankAccountDataTable.setApplicationCountryId(bankAccountdetails.getApplicationCountryId());
						}

						//	bankAccountDataTable.setCountryName(bankAccountdetails.getFsCountryMaster().getBusinessCountry().);
						bankAccountDataTable.setBankName(bankAccountdetails.getExBankMaster().getBankFullName());
						bankAccountDataTable.setCurrencyName(bankAccountdetails.getFsCurrencyMaster().getCurrencyName());
						bankAccountDataTable.setIsActive(bankAccountdetails.getRecordStatus());
						bankAccountDataTable.setCreatedBy(bankAccountdetails.getCreator());
						bankAccountDataTable.setCreatedDate(bankAccountdetails.getCreateDate());
						bankAccountDataTable.setDepartment(bankAccountdetails.getContactDept());
						bankAccountDataTable.setBankAccountDetId(bankAccountdetails.getBankAcctDetId());
						bankAccountDataTable.setRemarks(bankAccountdetails.getRemarks());

						activationList.add(bankAccountDataTable);
					}catch(NullPointerException  e){
						setErrorMesssage(e.getMessage());
						RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
					}
					catch(Exception e){
						setErrorMesssage(e.getMessage());
						RequestContext.getCurrentInstance().execute("csp.show();");
					}
				} 
			} else {
				RequestContext.getCurrentInstance().execute("noRecords.show();");
			}
		}		
	}

	public void gotoApproval(BankAccountDataTable datatable) {
		if((datatable.getModifier() ==null?datatable.getCreatedBy():datatable.getModifier()).equalsIgnoreCase(sessionStateManage.getUserName())){
			RequestContext.getCurrentInstance().execute("unapprove.show();");
		}else{
			try {

				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../treasury/bankaccountapproval.xhtml");
				fetchBankAccountDetailsForApprove(datatable);

			} catch(NullPointerException  e){
				setErrorMesssage(e.getMessage());
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
			catch(Exception e){
				setErrorMesssage(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
		}
	}	

	private BigDecimal fetchcompany;
	private BigDecimal fetchAccHeaderId;
	private String fecthModifyBy;
	private Date fetchModifyDate;

	public BigDecimal getFetchcompany() {
		return fetchcompany;
	}

	public void setFetchcompany(BigDecimal fetchcompany) {
		this.fetchcompany = fetchcompany;
	}

	public BigDecimal getFetchAccHeaderId() {
		return fetchAccHeaderId;
	}

	public void setFetchAccHeaderId(BigDecimal fetchAccHeaderId) {
		this.fetchAccHeaderId = fetchAccHeaderId;
	}

	public String getFecthModifyBy() {
		return fecthModifyBy;
	}

	public void setFecthModifyBy(String fecthModifyBy) {
		this.fecthModifyBy = fecthModifyBy;
	}

	public Date getFetchModifyDate() {
		return fetchModifyDate;
	}

	public void setFetchModifyDate(Date fetchModifyDate) {
		this.fetchModifyDate = fetchModifyDate;
	}

	public void fetchBankAccountDetailsForApprove(BankAccountDataTable datatable) {
		try{
			List<BankAccountDetails> bankaccountdetailslist = new ArrayList<BankAccountDetails>();
			if (datatable.getAccountNo() != null ) {
				bankaccountdetailslist = getiBankAccountDetService().getBankAccountDetails(datatable.getBankId(),datatable.getAccountNo());
				if (bankaccountdetailslist != null && bankaccountdetailslist.size() != 0) {

					setBankAccountDetId(bankaccountdetailslist.get(0).getBankAcctDetId());
					setCountryId(bankaccountdetailslist.get(0).getFsCountryMaster().getCountryId());

					fetchPopBank();

					setFetchcompany(bankaccountdetailslist.get(0).getFsCompanyMaster().getCompanyId());
					setBankId(bankaccountdetailslist.get(0).getExBankMaster().getBankId());

					getCurrencyList();
					setCurrency(bankaccountdetailslist.get(0).getFsCurrencyMaster().getCurrencyId());
					setFetchAccHeaderId(bankaccountdetailslist.get(0).getBankAccountId().getBankAccountId());

					setAccountNo(bankaccountdetailslist.get(0).getBankAcctNo());
					setAccountType(bankaccountdetailslist.get(0).getBankAccountType().getBankAccountTypeId().toString());
					setCurrency(bankaccountdetailslist.get(0).getFsCurrencyMaster().getCurrencyId());
					setMinBalance(bankaccountdetailslist.get(0).getMinBal());
					setOdLimit(bankaccountdetailslist.get(0).getOdlmt());
					setGlNo(bankaccountdetailslist.get(0).getGlno());
					setMobile(bankaccountdetailslist.get(0).getMobile());
					setTelephone(bankaccountdetailslist.get(0).getTelephoneNo());
					setFax(bankaccountdetailslist.get(0).getFaxno());
					setEmail(bankaccountdetailslist.get(0).getEmail());
					if(bankaccountdetailslist.get(0).getApplicationCountryId()!=null) {
						setApplicationCountryId(bankaccountdetailslist.get(0).getApplicationCountryId());
					}
					setContactPerson(bankaccountdetailslist.get(0).getContactPerson());
					setDepartment(bankaccountdetailslist.get(0).getContactDept());
					setDesignation(bankaccountdetailslist.get(0).getContactDesg());
					setContPersonLocal(bankaccountdetailslist.get(0).getContactPersonAr());
					setDepartmentLocal(bankaccountdetailslist.get(0).getContactDeptAr());
					setDesignationLocal(bankaccountdetailslist.get(0).getContactDesgAr());
					setFundGlNo(bankaccountdetailslist.get(0).getFundGlno());
					setInternalMinBalance(bankaccountdetailslist.get(0).getIntMinbal());
					setCreatedBy(bankaccountdetailslist.get(0).getCreator());
					setCreateDate(bankaccountdetailslist.get(0).getCreateDate());
					setFecthModifyBy(bankaccountdetailslist.get(0).getModifier());
					setFetchModifyDate(bankaccountdetailslist.get(0).getUpdateDate());
					setCtrlGlNo(bankaccountdetailslist.get(0).getCtrlGlNo());
				} 
			}

			/*List<BankAccount> fetchbankaccount = new ArrayList<BankAccount>();
		if (datatable.getAccountNo() != null) {
			bankaccountdetails = getiBankAccountDetService().getBankAccountDetails(datatable.getAccountNo());
		}*/
		}catch(NullPointerException  e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}


	public List<BankAccountDataTable> getApprovalList() {

		approvalList.clear();
		BankAccountDataTable bankAccountDataTable = null;

		List<BankAccountDetails> bankAccountList = bankApprovalService.getBankAccountDetailListForApproval();

		for (BankAccountDetails bankAccountdetails : bankAccountList) {
			bankAccountDataTable = new BankAccountDataTable();
			bankAccountDataTable.setAccountNo(bankAccountdetails.getBankAcctNo());
			bankAccountDataTable.setBankId(bankAccountdetails.getExBankMaster().getBankId());
			bankAccountDataTable.setBankCurrencyId(bankAccountdetails.getFsCurrencyMaster().getCurrencyId());
			bankAccountDataTable.setContactPerson(bankAccountdetails.getContactPerson());

			if(bankAccountdetails.getApplicationCountryId()!=null){
				bankAccountDataTable.setApplicationCountryId(bankAccountdetails.getApplicationCountryId());
			}

			/*bankAccountDataTable.setCountryName(bankAccountdetails.getFsCountryMaster().getBusinessCountry());
			bankAccountDataTable.setBankName(bankAccountdetails.getExBankMaster().getBankFullName());
			bankAccountDataTable.setCurrencyName(bankAccountdetails.getFsCurrencyMaster().getCurrencyName());*/

			bankAccountDataTable.setIsActive(bankAccountdetails.getRecordStatus());
			bankAccountDataTable.setCreatedBy(bankAccountdetails.getCreator());
			bankAccountDataTable.setCreatedDate(bankAccountdetails.getCreateDate());
			bankAccountDataTable.setModifier(bankAccountdetails.getModifier());
			bankAccountDataTable.setModifiedDate(bankAccountdetails.getUpdateDate());
			
			bankAccountDataTable.setDepartment(bankAccountdetails.getContactDept());
			bankAccountDataTable.setBankAccountDetId(bankAccountdetails.getBankAcctDetId());

			bankAccountDataTable.setEmail(bankAccountdetails.getEmail());
			bankAccountDataTable.setTelephone(bankAccountdetails.getTelephoneNo());
			bankAccountDataTable.setFax(bankAccountdetails.getFaxno());
			bankAccountDataTable.setApprovedBy(sessionStateManage.getUserName());
			bankAccountDataTable.setApprovedDate(new Date());
			bankAccountDataTable.setBankCode(bankAccountdetails.getExBankMaster().getBankCode());
			bankAccountDataTable.setBankName(bankAccountdetails.getExBankMaster().getBankFullName());

			approvalList.add(bankAccountDataTable);
		}

		return approvalList;
	}

	public void clickOnOKApprove() throws IOException{
		approvalList.clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankaccountapprovallist.xhtml");
		} catch(NullPointerException  e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}



	public void setApprovalList(List<BankAccountDataTable> approvalList) {
		this.approvalList = approvalList;
	}

	public List<BankAccountDataTable> getActivationList() {		
		return activationList;
	}

	public void getActiveInactive(BankAccountDataTable datatable) {

		setBankId(datatable.getBankId());
		setCountryId(datatable.getCountryId());
		setCreatedBy(datatable.getCreatedBy());
		setCreatedDate(datatable.getCreatedDate());

		setAccountNo(datatable.getAccountNo());//
		if(datatable.getIsActive().equalsIgnoreCase(Constants.Yes)){
			RequestContext.getCurrentInstance().execute("remarks.show();");

		}
		if(datatable.getIsActive().equalsIgnoreCase(Constants.D)){
			RequestContext.getCurrentInstance().execute("unupprove.show();");
		}

	}

	public void updateRemarks() {
		try{
			List<BankAccountDetails> bankaccountdetailslist = new ArrayList<BankAccountDetails>();
			bankaccountdetailslist = getiBankAccountDetService().getBankAccountDetails(getBankId(),getAccountNo());

			if(bankaccountdetailslist.size()!=0){
				//BigDecimal accountId=null;

				/*if(getBankAccountDetId()!=null)	{
					List<AccountBalance> lstAccountBalance=iBankAccountDetService.getAccountBalance(bankaccountdetailslist.get(0).getBankAcctNo(),bankaccountdetailslist.get(0).getBankAcctDetId());
					if(lstAccountBalance.size()>0){
						AccountBalance accountBalance=lstAccountBalance.get(0);
						accountId=accountBalance.getAccountId();
					}
				}*/			

				BankAccountDetails accountdetails = new BankAccountDetails();	

				if(bankaccountdetailslist.get(0).getApplicationCountryId()!=null) {
					accountdetails.setApplicationCountryId(bankaccountdetailslist.get(0).getApplicationCountryId());
				}

				accountdetails.setBankAcctDetId(bankaccountdetailslist.get(0).getBankAcctDetId());			

				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId((bankaccountdetailslist.get(0).getFsCountryMaster().getCountryId()));
				accountdetails.setFsCountryMaster(countryMaster);

				CompanyMaster companymaster = new CompanyMaster();
				companymaster.setCompanyId((bankaccountdetailslist.get(0).getFsCompanyMaster().getCompanyId()));
				accountdetails.setFsCompanyMaster(companymaster);

				BankMaster bankmaster = new BankMaster();
				bankmaster.setBankId((bankaccountdetailslist.get(0).getExBankMaster().getBankId()));
				accountdetails.setExBankMaster(bankmaster);

				accountdetails.setBankAcctNo((bankaccountdetailslist.get(0).getBankAcctNo()));
				//accountdetails.setAcctType(getAccountType());

				BankAccountType bankAccountType=new BankAccountType();
				bankAccountType.setBankAccountTypeId((bankaccountdetailslist.get(0).getBankAccountType().getBankAccountTypeId()));
				accountdetails.setBankAccountType(bankAccountType);

				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId((bankaccountdetailslist.get(0).getFsCurrencyMaster().getCurrencyId()));

				BankAccount bankAcc = new BankAccount();
				bankAcc.setBankAccountId(bankaccountdetailslist.get(0).getBankAccountId().getBankAccountId());
				accountdetails.setBankAccountId(bankAcc);

				accountdetails.setFsCurrencyMaster(currencyMaster);
				accountdetails.setMinBal(bankaccountdetailslist.get(0).getMinBal());
				accountdetails.setOdlmt(bankaccountdetailslist.get(0).getOdlmt());
				accountdetails.setGlno(bankaccountdetailslist.get(0).getGlno());
				accountdetails.setTelephoneNo(bankaccountdetailslist.get(0).getTelephoneNo());
				accountdetails.setFaxno(bankaccountdetailslist.get(0).getFaxno());
				accountdetails.setEmail(bankaccountdetailslist.get(0).getEmail());
				accountdetails.setContactPerson(bankaccountdetailslist.get(0).getContactPerson());
				accountdetails.setContactDept(bankaccountdetailslist.get(0).getContactDept());
				accountdetails.setContactDesg(bankaccountdetailslist.get(0).getContactDesg());
				accountdetails.setMobile(bankaccountdetailslist.get(0).getMobile());
				accountdetails.setContactPersonAr(bankaccountdetailslist.get(0).getContactPersonAr());
				accountdetails.setContactDeptAr(bankaccountdetailslist.get(0).getContactDeptAr());
				accountdetails.setContactDesgAr(bankaccountdetailslist.get(0).getContactDesgAr());
				accountdetails.setFundGlno(bankaccountdetailslist.get(0).getFundGlno());
				accountdetails.setIntMinbal(bankaccountdetailslist.get(0).getIntMinbal());

				if(bankaccountdetailslist.get(0).getRecordStatus().equals(Constants.Yes)){
					accountdetails.setRemarks(getRemarks());
					accountdetails.setApprovedBy(null);
					accountdetails.setApprovedDate(null);
					accountdetails.setCreator(bankaccountdetailslist.get(0).getCreator());
					accountdetails.setCreateDate(bankaccountdetailslist.get(0).getCreateDate());
					accountdetails.setRecordStatus(Constants.D);
				}
				if(bankaccountdetailslist.get(0).getRecordStatus().equals(Constants.D)){
					accountdetails.setApprovedBy(null);
					accountdetails.setApprovedDate(null);
					accountdetails.setCreator(sessionStateManage.getUserName());
					accountdetails.setCreateDate(new Date());
					accountdetails.setRecordStatus("U");
					accountdetails.setRemarks(null);
				}

				getiBankAccountDetService().saveBankAccountDetails(accountdetails);

			}
		}catch(NullPointerException  e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void clickOnOKActivate() throws IOException{
		activationList.clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankaccountactiveinactivelist.xhtml");
		} catch (Exception e) {
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void clickOnOKDeActivate() throws IOException{

		try {
			updateRemarks();
			setBankBranch(null);
			setBankBranchList(null);
			activationList.clear();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankaccountactiveinactivelist.xhtml");
		}catch(NullPointerException  e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void setActivationList(List<BankAccountDataTable> activationList) {
		this.activationList = activationList;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}	
	private boolean editButton;
	private boolean booSystemAccountNo;
	private BigDecimal dBAccountId;
	private boolean booUpdateAccountNo;



	public boolean isEditButton() {
		return editButton;
	}

	public void setEditButton(boolean editButton) {
		this.editButton = editButton;
	}

	public boolean isBooSystemAccountNo() {
		return booSystemAccountNo;
	}

	public void setBooSystemAccountNo(boolean booSystemAccountNo) {
		this.booSystemAccountNo = booSystemAccountNo;
	}

	public BigDecimal getdBAccountId() {
		return dBAccountId;
	}

	public void setdBAccountId(BigDecimal dBAccountId) {
		this.dBAccountId = dBAccountId;
	}

	public boolean isBooUpdateAccountNo() {
		return booUpdateAccountNo;
	}

	public void setBooUpdateAccountNo(boolean booUpdateAccountNo) {
		this.booUpdateAccountNo = booUpdateAccountNo;
	}

	public void insertNewAccountNo() {
		setBooSystemAccountNo(false);
		setBooUpdateAccountNo(true);
		setEditButton(false);
		setAccountNo(null);
		setCurrency(null);
		clearAccount();
	}

	public void approveBankAccount(){
		approveAccountDetails();
		/*if(sessionStateManage.getUserName().equalsIgnoreCase(getCreatedBy())){
			RequestContext.getCurrentInstance().execute("unapprove.show();");
		}else{
			approveAccountDetails();
		}*/

	}

	/*public void approveBankAccountTable(){

		BankAccount bankaccount = new BankAccount();

		bankaccount.setBankAccountId(getBankAccountId());

		CountryMaster countryMaster =  new CountryMaster();
		countryMaster.setCountryId(getCountryId());
		bankaccount.setFsCountryMaster(countryMaster);

		CompanyMaster companymaster = new CompanyMaster();
		companymaster.setCompanyId(sessionStateManage.getCompanyId());
		bankaccount.setFsCompanyMaster(companymaster);

		BankMaster bankmaster = new BankMaster();
		bankmaster.setBankId(getBankId());
		bankaccount.setExBankMaster(bankmaster);

		bankaccount.setCurrencyId(getCurrency());
		bankaccount.setDebitAcct(getAccountNo());

		if (getBankAccountId() != null && getBankAccountId().intValue() != 0) {

			bankaccount.setModifier(sessionStateManage.getUserName());
			bankaccount.setUpdateDate(new Date());
			bankaccount.setRecordStatus(Constants.IsActive_U);
			bankaccount.setCreator(getCreatyedBy());
			bankaccount.setCreateDate(getCreateDate());

		} else {

			bankaccount.setCreator(sessionStateManage.getUserName());
			bankaccount.setCreateDate(new Date());
			bankaccount.setRecordStatus(Constants.IsActive_U);

		}

		ibankAccountService.saveBankAccount(bankaccount);
	}*/

	public void approveAccountDetails(){
		try{
			BankAccountDetails accountdetails = new BankAccountDetails();

			if(getApplicationCountryId()!=null) {
				accountdetails.setApplicationCountryId(getApplicationCountryId());
			}

			accountdetails.setBankAcctDetId(getBankAccountDetId());


			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(getCountryId());
			accountdetails.setFsCountryMaster(countryMaster);

			CompanyMaster companymaster = new CompanyMaster();
			companymaster.setCompanyId(getFetchcompany());
			accountdetails.setFsCompanyMaster(companymaster);

			BankMaster bankmaster = new BankMaster();
			bankmaster.setBankId(getBankId());
			accountdetails.setExBankMaster(bankmaster);

			accountdetails.setBankAcctNo(getAccountNo());

			BankAccountType bankAccountType=new BankAccountType();
			bankAccountType.setBankAccountTypeId(new BigDecimal(getAccountType()));
			accountdetails.setBankAccountType(bankAccountType);

			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(getCurrency());
			accountdetails.setFsCurrencyMaster(currencyMaster);

			BankAccount bankAcc = new BankAccount();
			bankAcc.setBankAccountId(getFetchAccHeaderId());
			accountdetails.setBankAccountId(bankAcc);


			accountdetails.setMinBal(getMinBalance());
			accountdetails.setOdlmt(getOdLimit());
			accountdetails.setGlno(getGlNo());
			accountdetails.setTelephoneNo(getTelephone());
			accountdetails.setFaxno(getFax());
			accountdetails.setEmail(getEmail());
			accountdetails.setContactPerson(getContactPerson());
			accountdetails.setContactDept(getDepartment());
			accountdetails.setContactDesg(getDesignation());
			accountdetails.setMobile(getMobile());
			accountdetails.setContactPersonAr(getContPersonLocal());
			accountdetails.setContactDeptAr(getDepartmentLocal());
			accountdetails.setContactDesgAr(getDesignationLocal());
			accountdetails.setFundGlno(getFundGlNo());
			accountdetails.setIntMinbal(getInternalMinBalance());
			accountdetails.setCtrlGlNo(getCtrlGlNo());

			accountdetails.setRecordStatus(Constants.Yes);

			accountdetails.setCreateDate(getCreateDate());
			accountdetails.setCreator(getCreatedBy());
			accountdetails.setModifier(getFecthModifyBy());
			accountdetails.setUpdateDate(getFetchModifyDate());
			accountdetails.setApprovedBy(sessionStateManage.getUserName());
			accountdetails.setApprovedDate(new Date());

			//ibankAccountService.approveBankAccount(getFetchAccHeaderId(), sessionStateManage.getUserName());

			getiBankAccountDetService().saveBankAccountDetails(accountdetails);

			//END OF PROCEDURE CALL
 			HashMap<String, String>  approveRecord = new HashMap<String, String>();
			approveRecord.put("BANK_ID", getBankId().toPlainString());
			String bankCode= getGeneralService().getBankCode(getBankId() );
			approveRecord.put("BANK_CODE",bankCode);
			approveRecord.put("BANK_ACCOUNT_ID", getFetchAccHeaderId().toPlainString());
			approveRecord.put("BANK_ACCT_DET_ID" , getBankAccountDetId().toPlainString());
			
			//procedure call
			HashMap<String, String> ouputValues = getiBankAccountDetService().callPopulateBankAccountDeatils(approveRecord);
			
			if(ouputValues.get("P_ERROR_MESSAGE")!=null){
				setErrorMesssage(ouputValues.get("P_ERROR_MESSAGE"));
				ibankAccountService.procErrorToUnApprove(getBankAccountDetId(),getFetchAccHeaderId());
				RequestContext.getCurrentInstance().execute("csp.show();");
				return ; 
			}
			else{
				RequestContext.getCurrentInstance().execute("approve.show();");
			}
		}catch(NullPointerException  e){
			setErrorMesssage(e.getMessage());
			ibankAccountService.procErrorToUnApprove(getBankAccountDetId(),getFetchAccHeaderId());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			ibankAccountService.procErrorToUnApprove(getBankAccountDetId(),getFetchAccHeaderId());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void fetchPopBank(){
		List<BankMaster> bankMasterlst = getGeneralService().getAllBankList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"), getCountryId());
		if(bankMasterlst.size()!=0){
			setBankMasterList(bankMasterlst);
		}

	}


	//for bank account view Screen

	private Boolean renderDataTable=false;
	private Map<BigDecimal, String> countryMap=new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> courrencyMap=new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapCurrencyCode = new HashMap<BigDecimal, String>();
	private List<BankAccountDataTable> bankAccountDetailsDataTableList=new ArrayList<BankAccountDataTable>();



	public Map<BigDecimal, String> getMapCurrencyCode() {
		return mapCurrencyCode;
	}
	public void setMapCurrencyCode(Map<BigDecimal, String> mapCurrencyCode) {
		this.mapCurrencyCode = mapCurrencyCode;
	}

	public List<BankAccountDataTable> getBankAccountDetailsDataTableList() {
		return bankAccountDetailsDataTableList;
	}
	public void setBankAccountDetailsDataTableList(List<BankAccountDataTable> bankAccountDetailsDataTableList) {
		this.bankAccountDetailsDataTableList = bankAccountDetailsDataTableList;
	}

	@Autowired
	ISpecialCustomerDealRequestService specialCustomerDealRequestService;

	public Boolean getRenderDataTable() {
		return renderDataTable;
	}

	public void setRenderDataTable(Boolean renderDataTable) {
		this.renderDataTable = renderDataTable;
	}

	public void viewRelatedData(){
		try{
			List<BankAccountDetails>  bankAccountDelList= iBankAccountDetService.getDataForView(getCountryId(), getBankId());
			if(bankAccountDelList.size()>0){
				setRenderDataTable(true);
				bankAccountDetailsDataTableList.clear();
				for(BankAccountDetails bankAccountDetail:bankAccountDelList){
					BankAccountDataTable bankAcDataTable=new BankAccountDataTable();
					bankAcDataTable.setCountryName(countryMap.get(bankAccountDetail.getFsCountryMaster().getCountryId()));
					bankAcDataTable.setBankName(mapBank.get(bankAccountDetail.getExBankMaster().getBankId()));
					bankAcDataTable.setAccountNo(bankAccountDetail.getBankAcctNo());
					bankAcDataTable.setCurrencyName(specialCustomerDealRequestService.getCurrencyForUpdate(bankAccountDetail.getFsCurrencyMaster().getCurrencyId()));
					bankAcDataTable.setAccountType(iBankAccountDetService.getAccountTypeName(bankAccountDetail.getBankAccountType().getBankAccountTypeId()));
					if(bankAccountDetail.getApplicationCountryId()!=null) {
						bankAcDataTable.setApplicationCountryId(bankAccountDetail.getApplicationCountryId());
					}
					if(bankAccountDetail.getRecordStatus() != null && bankAccountDetail.getRecordStatus().equalsIgnoreCase(Constants.Yes)){
						bankAcDataTable.setStatus("Actived");
					}else if(bankAccountDetail.getRecordStatus() != null && bankAccountDetail.getRecordStatus().equalsIgnoreCase("D")){
						bankAcDataTable.setStatus("DeActivated");
					}else if(bankAccountDetail.getRecordStatus() != null && bankAccountDetail.getRecordStatus().equalsIgnoreCase("U")){
						bankAcDataTable.setStatus("UnApprove");
					}

					bankAccountDetailsDataTableList.add(bankAcDataTable);

				}
			}else{
				RequestContext.getCurrentInstance().execute("noRecords.show();");
				bankAccountDetailsDataTableList.clear();
				setRenderDataTable(false);
			}
			setCountryId(null);
			setBankId(null);
		}catch(NullPointerException  e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToBankAccountView(){
		bankAccountDetailsDataTableList.clear();
		setCountryId(null);
		setBankId(null);
		setRenderDataTable(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankAccountDetailsView.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankAccountDetailsView.xhtml");
		} catch (IOException e) {
			setErrorMesssage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}



	//bank branch activate in active filtering

	public void filterBankBranchData(){

	}

	public  String getErrorMesssage() {
		return errorMesssage;
	}

	public void setErrorMesssage(String errorMesssage) {
		this.errorMesssage = errorMesssage;
	}

	// gl no checking with V_GLNO view
	public void checkingGlNoWithVGLNO(){
		if(getGlNo() != null && !getGlNo().equalsIgnoreCase("")){
			//List<CompanyMasterDesc> lstCompDesc  = generalService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
			//if(lstCompDesc != null && !lstCompDesc.isEmpty()){
				//CompanyMasterDesc compdesc = lstCompDesc.get(0);
				//List<ViewGeneralValidationGlNo> lstglNo = iBankAccountDetService.getAccountBalanceGLNo(compdesc.getFsCompanyMaster().getCompanyCode(), getGlNo());
				List<ViewGeneralValidationGlNo> lstglNo = iBankAccountDetService.getAccountBalanceGLNoWithCurrency(mapCurrencyCode.get(getCurrency()), getGlNo());
				if(lstglNo != null && !lstglNo.isEmpty()){
					// allow
				}else{
					// error msg
					setGlNo(null);
					setErrorMesssage("Invalid GL NO");
					RequestContext.getCurrentInstance().execute("csp.show();");
				}
			//}
		}
	}

	// gl no checking with V_GLNO view
	public void checkingFundGlNoWithVGLNO(){
		if(getFundGlNo() != null && !getFundGlNo().equalsIgnoreCase("")){
			//List<CompanyMasterDesc> lstCompDesc  = generalService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
			//if(lstCompDesc != null && !lstCompDesc.isEmpty()){
				//CompanyMasterDesc compdesc = lstCompDesc.get(0);
				//List<ViewGeneralValidationGlNo> lstfundglNo = iBankAccountDetService.getAccountBalanceGLNo(compdesc.getFsCompanyMaster().getCompanyCode(), getFundGlNo());
				List<ViewGeneralValidationGlNo> lstfundglNo = iBankAccountDetService.getAccountBalanceGLNoWithCurrency(mapCurrencyCode.get(getCurrency()), getFundGlNo());
				if(lstfundglNo != null && !lstfundglNo.isEmpty()){
					// allow
				}else{
					// error msg
					setFundGlNo(null);
					setErrorMesssage("Invalid Fund GL NO");
					RequestContext.getCurrentInstance().execute("csp.show();");
				}
			//}
		}
	}

	//to validate glno through procedure EX_CHK_GLNO
	public boolean checktoValidateGlNo() {
		boolean checkGLNO=false;
		try {
			setErrorMesssage(null);
			HashMap<String,String> outPutValues=iBankAccountDetService.getAccountBalanceGLNoWithCurrencyWithProc(getCurrency(),getGlNo());
			//String errMsg=outPutValues.get("ERR_MSG");
			if(outPutValues.get("ERR_MSG") !=null )
			{
				checkGLNO=false;
				setErrorMesssage(outPutValues.get("ERR_MSG"));
				RequestContext.getCurrentInstance().execute("csp.show();");
				return checkGLNO;
			}
			checkGLNO=true;
			/*else
			{
				setGlNo(null);
				if(errMsg==null){
					setErrorMesssage("Procedure Giving Error Message Null");
					RequestContext.getCurrentInstance().execute("csp.show();");
				}else{
					setErrorMesssage(errMsg);
					RequestContext.getCurrentInstance().execute("csp.show();");
				}
			}*/
		} catch (Exception exception) {
			setErrorMesssage("Invalid GL Number");
			checkGLNO=false;
			RequestContext.getCurrentInstance().execute("csp.show();");
			return checkGLNO;
		}
		return checkGLNO;
		
	}
	
	//to validate fund glno through procedure EX_CHK_GLNO
	
	public boolean checktoValidateFundGlNo() {
		boolean fundGLNO=false;
		try {
			setErrorMesssage(null);
			HashMap<String,String> outPutValues=iBankAccountDetService.getAccountBalanceGLNoWithCurrencyWithProc(getCurrency(),getFundGlNo());
			//String errMsg=outPutValues.get("ERR_MSG");
			if(outPutValues.get("ERR_MSG") != null)
			{
				fundGLNO=false;
				setErrorMesssage(outPutValues.get("ERR_MSG"));
				RequestContext.getCurrentInstance().execute("csp.show();");
				return fundGLNO;
			}
			fundGLNO=true;
			/*else
			{
				setFundGlNo(null);
				if(errMsg==null){
					setErrorMesssage("Procedure Giving Error Message Null");
					RequestContext.getCurrentInstance().execute("csp.show();");
				}else{
					setErrorMesssage(errMsg);
					RequestContext.getCurrentInstance().execute("csp.show();");
				}
			}*/
		} catch (Exception exception) {
			fundGLNO=false;
			setErrorMesssage("Invalid Fund GL Number");
			RequestContext.getCurrentInstance().execute("csp.show();");
			return fundGLNO;
		}
		return fundGLNO;
		
	}
	
	//to validate ctrl Glno through procedure EX_CHK_GLNO
	
	public boolean checktoValidatectrlGlNo() {
		boolean ctrlGLNO=false;
		try {
			setErrorMesssage(null);
			HashMap<String,String> outPutValues=iBankAccountDetService.getAccountBalanceGLNoWithCurrencyWithProc(getCurrency(),getCtrlGlNo());
			//String errMsg=outPutValues.get("ERR_MSG");
			if(outPutValues.get("ERR_MSG") != null)
			{
				ctrlGLNO=false;
				setErrorMesssage(outPutValues.get("ERR_MSG"));
				RequestContext.getCurrentInstance().execute("csp.show();");
				return ctrlGLNO;
			}
			ctrlGLNO=true;
			/*else
			{
				setCtrlGlNo(null);
				if(errMsg==null){
					setErrorMesssage("Procedure Giving Error Message Null");
					RequestContext.getCurrentInstance().execute("csp.show();");
				}else{
					setErrorMesssage(errMsg);
					RequestContext.getCurrentInstance().execute("csp.show();");
				}
			}*/
		} catch (Exception exception) {
			ctrlGLNO=false;
			setErrorMesssage("Invalid Control GL Number");
			RequestContext.getCurrentInstance().execute("csp.show();");
			return ctrlGLNO;
		}
		return ctrlGLNO;
		
	}
	
}
