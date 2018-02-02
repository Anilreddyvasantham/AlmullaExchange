package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.bean.RelationsBean;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankIndicatorDescription;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IBankApplicabilityService;
import com.amg.exchange.treasury.service.IBankApprovalService;
import com.amg.exchange.treasury.service.IBankIndicatorService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.iCypherSecurity;
import com.amg.exchange.util.impl.CypherSecurityImpl;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

@Component(value = "bankApplicabilityManagedBean")
@Scope("session")
public class BankApplicabilityManagedBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3943860350339577651L;
	private static final Logger log = Logger.getLogger(BankApplicabilityManagedBean.class);
	// TODO
	private BigDecimal countryId;
	private String timeZone;
	private BigDecimal companyId;
	private String companyName;
	private BigDecimal bankId;
	private BigDecimal bankBranchId;
	private String exchangeId;
	private BigDecimal bankTypeId;
	private String userName;
	private String password;
	private String agentId;
	private String agentPin;
	private BigDecimal bankApplicabilityId;
	private List<BankMaster> bankMasterList;

	private CountryMaster countryMaster = null;
	private CompanyMaster companyMaster = null;
	private BankMaster bankMaster = null;
	private BankIndicator bankIndicator = null;
	private boolean renderWebCredentials = false;
	private boolean renderFirst = true;
	private String errorMsg;
	
	List<BankIndicator> bankindicatorlist = new ArrayList<BankIndicator>();
	
	List<BankApplicability> lstBankApplicabilityForDeactive= new ArrayList<BankApplicability>();

	Map<BigDecimal, String> mapBankType = new LinkedHashMap<BigDecimal, String>();

	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	private BigDecimal bankTypeIdForChange;
	// TODO
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IBankApplicabilityService<T> bankApplicabilityService;

	@Autowired
	ISpecialCustomerDealRequestService<T> iSpecialCustomerDealRequestService;
	
	public boolean isRenderWebCredentials() {
		return renderWebCredentials; 
	}

	public void setRenderWebCredentials(boolean renderWebCredentials) {
		this.renderWebCredentials = renderWebCredentials;
	}

	public boolean isRenderFirst() {
		return renderFirst;
	}

	public void setRenderFirst(boolean renderFirst) {
		this.renderFirst = renderFirst;
	}

	public BigDecimal getBankApplicabilityId() {
		return bankApplicabilityId;
	}

	public void setBankApplicabilityId(BigDecimal bankApplicabilityId) {
		this.bankApplicabilityId = bankApplicabilityId;
	}

	public IBankApplicabilityService<T> getBankApplicabilityService() {
		return bankApplicabilityService;
	}

	public void setBankApplicabilityService(IBankApplicabilityService<T> bankApplicabilityService) {
		this.bankApplicabilityService = bankApplicabilityService;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public BigDecimal getBankTypeId() {
		return bankTypeId;
	}

	public void setBankTypeId(BigDecimal bankTypeId) {
		this.bankTypeId = bankTypeId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentPin() {
		return agentPin;
	}

	public void setAgentPin(String agentType) {
		this.agentPin = agentType;
	}

	public BigDecimal getBankTypeIdForChange() {
		return bankTypeIdForChange;
	}

	public void setBankTypeIdForChange(BigDecimal bankTypeIdForChange) {
		this.bankTypeIdForChange = bankTypeIdForChange;
	}

	public List<BankIndicatorDescription> getBankindicatorlist() {
		
		List<BankIndicatorDescription> bankIndDescList = new ArrayList<BankIndicatorDescription>();
	//	List<BankIndicator> bankIndList = new ArrayList<BankIndicator>();
		//bankIndList.addAll(bankApplicabilityService.getBankIndicatorList());
	try{
		bankIndDescList.addAll(bankApplicabilityService.getBankIndicatorDescList(sessionStateManage.getLanguageId()));
	}catch(NullPointerException  e){ 
		 
			setErrorMsg("getBankindicatorlist :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
 
	/*for (BankIndicator indicator : bankIndList) {
		mapCountryList.put(indicator.getBankIndicatorId(),
				indicator.getBankIndicatorName());
	}
	return bankIndList;*/
	for (BankIndicatorDescription indicator : bankIndDescList) {
		mapCountryList.put(indicator.getBankIndicator().getBankIndicatorId(),
				indicator.getBankIndicatorDescription());
	}
	return bankIndDescList;
	
	}

	public void setBankindicatorlist(List<BankIndicator> bankindicatorlist) {
		this.bankindicatorlist = bankindicatorlist;
	}

	public List<BankApplicability> getLstBankApplicabilityForDeactive() {
		return lstBankApplicabilityForDeactive;
	}

	public void setLstBankApplicabilityForDeactive(
			List<BankApplicability> lstBankApplicabilityForDeactive) {
		this.lstBankApplicabilityForDeactive = lstBankApplicabilityForDeactive;
	}

	SessionStateManage sessionStateManage = new SessionStateManage();

	// TODO
	/**
	 * method is responsible to populate List of countries from DB
	 * 
	 * @return
	 */
	public List<CountryMasterDesc> getCountryListFromDB() {
		List<CountryMasterDesc> countryList = new ArrayList<CountryMasterDesc>();
		try{
		countryList.addAll(getGeneralService().getCountryListExceptAppCountry(sessionStateManage.getLanguageId(), sessionStateManage.getCountryId()));
		}catch(NullPointerException  e){ 
				setErrorMsg("getBankindicatorlist :");
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
			catch (Exception e) {
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
		for (CountryMasterDesc countryMasterDesc : countryList) {
			mapCountryList.put(countryMasterDesc.getCountryMasterId(),
					countryMasterDesc.getCountryName());
		}
		return countryList;
	}
	

	/**
	 * method is responsible to populate List of companies from DB
	 * 
	 * @return
	 */
	public List<CompanyMasterDesc> getCompanyListFromDB() {
		List<CompanyMasterDesc> companyList=new ArrayList<CompanyMasterDesc>();
try{	
companyList =iSpecialCustomerDealRequestService.getAllCompanyList(sessionStateManage.getCompanyId(),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
}catch(NullPointerException  e){ 
	 
		setErrorMsg("Method Name:getBankindicatorlist ");
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}
	catch (Exception e) {
		setErrorMsg(e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");
	}	
		for (CompanyMasterDesc countryMasterDesc :companyList) {
			mapCompanyList.put(countryMasterDesc.getCompanyMasterId(),
					countryMasterDesc.getCompanyName());
			
			setCompanyId(countryMasterDesc.getFsCompanyMaster().getCompanyId());
			setCompanyName(countryMasterDesc.getCompanyName());
		}
		
		/*List<CompanyMasterDesc> companyList = new ArrayList<CompanyMasterDesc>();
		companyList.addAll(getGeneralService().getAllCompanyList(sessionStateManage.getLanguageId()));

		for (CompanyMasterDesc countryMasterDesc :companyList) {
			mapCompanyList.put(countryMasterDesc.getCompanyMasterId(),
					countryMasterDesc.getCompanyName());
		}*/
		
		return companyList;
	}

	/**
	 * method is responsible to populate List of companies from DB
	 * 
	 * @return
	 */
	public void popBank(AjaxBehaviorEvent e) {
		bankMasterList = new ArrayList<BankMaster>();
		bankMasterList.addAll(getGeneralService().getAllBankList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"), getCountryId()));
	}

	public List<BankMaster> getBankListFromDB() {
		
	
	List<BankMaster> bankList = new ArrayList<BankMaster>();

	try{
	bankList.addAll(getGeneralService().getAllBankListFromBankMaster());
	}catch(NullPointerException  e){ 
			setErrorMsg("Method Name:getBankListFromDB");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}	
	for (BankMaster bankdeslist :bankList) {
		mapBankList.put(bankdeslist.getBankId(),
				bankdeslist.getBankFullName());
	}
	return bankList;
	}

	/**
	 * method is responsible to populate List of companies from DB
	 * 
	 * @return
	 */
	/*
	 * public List<BussComponentComboData> getBankTypeListFromDB() {
	 * 
	 * return getGeneralService().getAllBankTypeList(new
	 * BigDecimal(sessionStateManage
	 * .isExists("languageId")?sessionStateManage.getSessionValue
	 * ("languageId"):"1"));
	 * 
	 * }
	 */
	/**
	 * method is responsible to save the Bank Applicability Details in db
	 * 
	 * @return
	 */
	// @SuppressWarnings("rawtypes")
	public void saveBankApplicabilityDetails() {
		log.info("::::::::::::::::::::::::::::::::::::::::::Save:::::::::::::::::::::::::");
		try {
			boolean passWordCheck=false;
			if(getUserName()!=null)
			{
				if(!getUserName().equalsIgnoreCase(""))
				{
					if(getPassword()==null || getPassword().equalsIgnoreCase(""))
					{
						RequestContext.getCurrentInstance().execute("passWord.show();");
						passWordCheck=true;
					}
				}
				
				
			}
			if(getAgentId()!=null)
			{
				if(!getAgentId().equalsIgnoreCase(""))
				{
					if(getAgentPin()==null || getAgentPin().equalsIgnoreCase(""))
					{
						RequestContext.getCurrentInstance().execute("agentpin.show();");
						passWordCheck=true;
					}
				}
				
			}
			
			if(!passWordCheck)
			{
				log.info("the country id is" + getCountryId());
				log.info("the company id is" + getCompanyId());
				log.info("the bank id is" + getBankId());
				BankApplicability bankApplicability = new BankApplicability();
				companyMaster = new CompanyMaster();
				countryMaster = new CountryMaster();
				bankMaster = new BankMaster();
				bankIndicator = new BankIndicator();
				
				String secretKey = getUserName();
				String secretKey1 = getAgentId();
				iCypherSecurity cypherSecurity = new CypherSecurityImpl();
				countryMaster.setCountryId(getCountryId());
				companyMaster.setCompanyId(getCompanyId());
				bankMaster.setBankId(getBankId());
				bankIndicator.setBankIndicatorId(getBankTypeId());
				
				bankApplicability.setApplicabilityId(getApplicabilityId()); 
				bankApplicability.setFsCountryMaster(countryMaster);
				bankApplicability.setFsCompanyMaster(companyMaster);
				bankApplicability.setBankMaster(bankMaster);
				bankApplicability.setTimezone(new BigDecimal(getTimeZone()));
				bankApplicability.setExchangeId(getExchangeId());
				bankApplicability.setBankInd(bankIndicator);
				if(getUserName()!=null)
				{
					bankApplicability.setWebsrvUsername(getUserName());
					bankApplicability.setWebsrvPassword(cypherSecurity.encodePassword(getPassword(), secretKey));
				}
				
				if(getAgentId()!=null)
				{
					bankApplicability.setWebsrvAgntId(getAgentId());
					bankApplicability.setWebsrvAgntPin(cypherSecurity.encodePassword(getAgentPin(), secretKey1));
				}
				
				
				if(getApplicabilityId()!=null)
				{
					bankApplicability.setModifier(sessionStateManage.getUserName());
					bankApplicability.setUpdateDate(new Date());
					bankApplicability.setCreateDate(getFcreatDate());
					bankApplicability.setCreator(getFcreateBy());
				}else
				{
					bankApplicability.setCreateDate(new Date());
					bankApplicability.setCreator(sessionStateManage.getUserName());
				}
				
				bankApplicability.setIsActive("U");
				bankApplicability.setApprovedBy(null);
				bankApplicability.setApprovedDate(null);
				bankApplicability.setApprovedDate(null);
				bankApplicability.setRemarks(null);
				getBankApplicabilityService().saveBankApplicabilityDetails(bankApplicability);
				
				RequestContext.getCurrentInstance().execute("complete.show();");

			}
			
		} catch (NullPointerException  e) {
			setErrorMsg("Method Name: saveBankApplicabilityDetails");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		//return "success123";
	}

	/**
	 * 
	 * method is responsible to clear the Bank Applicability Details from the
	 * screen
	 */
	public void clearBankApplicabilityDetails() {
		log.info( "inside method cleare");
		setBankApplicabilityId(null);
		setApplicabilityId(null);
		//setCountryId(null);
		//setCountryName(null);
		setTimeZone(null);
		//setCompanyId(null);
		setBankId(null);
		setExchangeId(null);
		setBankTypeId(null);
		setUserName(null);
		setPassword(null);
		setAgentId(null);
		setAgentPin(null);
		setRenderFirst(true);
		setRenderWebCredentials(false);
	}

	/**
	 * method is responsible to cancel the Bank Master
	 * 
	 * @return
	 */
	public void cancelBankApplicabilityDetails() {
	 log.info("::::::::::::::::: inside cancel   :::::::::::::::::");
		clearBankApplicabilityDetails();
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	/**
	 * method is responsible to go next panel
	 * 
	 * @return
	 */
	public void nextBankApplicabilityDetails() {
		log.info("the inside next=============================================================");
		setRenderWebCredentials(true);
		setRenderFirst(false);
	}

	/**
	 * method is responsible to go back next page
	 * 
	 * @return
	 */
	public void backBankApplicabilityDetails() {
		 log.info("the inside back===============================================================");
		setRenderWebCredentials(false);
		setRenderFirst(true);

	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void clearCache()
	{
		//setCountryId(null);
		//setCountryName(null);
		setTimeZone(null);
		//setCompanyId(null);
		setBankId(null);
		setExchangeId(null);
		setBankTypeId(null);
		setUserName(null);
		setPassword(null);
		setAgentId(null);
		setAgentPin(null);
		setBooCheck(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankapplicability.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankapplicability.xhtml");
		} catch (IOException e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		getCompanyListFromDB();
		popUpCountry();
	}
	
	public void completed()
	{
		clearBankApplicabilityDetails();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankapplicability.xhtml");
		}  
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		
	}
	
	//ADDED BY NAZISH FOR CLEAR
	
	public void clear(){
		
		//setCountryId(null);
		//setCountryName(null);
		setTimeZone(null);
		//setCompanyId(null);
		setBankId(null);
		setExchangeId(null);
		setBankTypeId(null);
		setUserName(null);
		setPassword(null);
		setAgentId(null);
		setAgentPin(null);
		
	}
	
	public void fethRecord(){
try{

		if(getCompanyId()!=null && getCountryId()!=null && getBankTypeId()!=null && getBankId()!=null){
			
			List<BankApplicability> listApplicability = bankApplicabilityService.getBankApplicability(getCompanyId(), getCountryId(), getBankTypeId(), getBankId());

			if(listApplicability.size()>0){

				setExchangeId(listApplicability.get(0).getExchangeId());
				if(listApplicability.get(0).getTimezone() != null){
					setTimeZone(listApplicability.get(0).getTimezone().toPlainString());
				}
				
			}
			
			List<BankApplicability> lstBankApplicability=bankApplicabilityService.getBankApplicabilityForView(getCompanyId(), getCountryId(), getBankId());
			setBankTypeIdForChange(null);
			if(lstBankApplicability.size()>0)
			{
				//setBankTypeId(lstBankApplicability.get(0).getBankInd().getBankIndicatorId());
				setBankTypeIdForChange(lstBankApplicability.get(0).getBankInd().getBankIndicatorId());
				if(getBankTypeId().compareTo(lstBankApplicability.get(0).getBankInd().getBankIndicatorId())!=0)
				{
					RequestContext.getCurrentInstance().execute("bankAppIdExist.show();");
				}
				
			}
		}
		
}catch (NullPointerException  e) {
	setErrorMsg("Method Name: fethRecord");
	RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
}
catch (Exception e) {
	setErrorMsg(e.getMessage());
	RequestContext.getCurrentInstance().execute("csp.show();");
}
		
	}
	public void bankIndOK()
	{
		//a
		//bankApplicabilityService
		
	}
	public void bankIndCancel()
	{
		setBankTypeId(getBankTypeIdForChange());
	}
	public void fetchRecActive()
	{
		/*listApply=bankApplicabilityService.getlist(getBankId());
		if(listApply.size()>0)
		{
			setBooCheck(true);
		} else
		{
			setBooCheck(false);
			
		}*/
		try{
		List<BankApplicability> lstBankApplicability=bankApplicabilityService.getBankApplicabilityForView(getCompanyId(), getCountryId(), getBankId());
		clearBankView();
		if(lstBankApplicability.size()>0)
		{
			
			if(lstBankApplicability.get(0).getIsActive().equalsIgnoreCase(Constants.D))
			{
				setLstBankApplicabilityForDeactive(lstBankApplicability);
				RequestContext.getCurrentInstance().execute("deactive.show();");
			}else{
				populateBankApplicabilityDetails(lstBankApplicability);
			}
		}
		
		}catch (NullPointerException  e) {
			setErrorMsg("Method Name: fetchRecActive");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		
	}
	
	private void populateBankApplicabilityDetails(List<BankApplicability> lstBankApplicability)
	{
	 try{
		for(BankApplicability bankApplicability :lstBankApplicability)
		{
			//setCountryId(null);
			if(bankApplicability.getTimezone() != null){
				setTimeZone(bankApplicability.getTimezone().toPlainString());
			}
			//setCompanyId(null);
			//setBankId(null);
			setExchangeId(bankApplicability.getExchangeId());
			setBankTypeId(bankApplicability.getBankInd().getBankIndicatorId());
			setUserName(bankApplicability.getWebsrvUsername());
			setPassword(bankApplicability.getWebsrvPassword());
			setAgentId(bankApplicability.getWebsrvAgntId());
			setAgentPin(bankApplicability.getWebsrvAgntPin());
			setApplicabilityId(bankApplicability.getApplicabilityId());
			setFcreateBy(bankApplicability.getCreator());
			setFcreatDate(bankApplicability.getCreateDate());
		}
	 } catch (NullPointerException  e) {
			setErrorMsg("Method Name: populateBankApplicabilityDetails");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	
	public void deactiveYes()
	{
		List<BankApplicability> lstBankApplicability=getLstBankApplicabilityForDeactive();
		populateBankApplicabilityDetails(lstBankApplicability);
	}
	public void deactiveNo()
	{
		setBankId(null);
		//clearToInsertNewBranchCode();
	}
	
	List<BankMaster> listApply=new ArrayList<BankMaster>();
	
	private boolean booCheck=false;
	private BigDecimal applicabilityId;
	private String fcreateBy;
	private Date fcreatDate;
	
	public boolean getBooCheck() {
		return booCheck;
	}

	public void setBooCheck(boolean booCheck) {
		this.booCheck = booCheck;
	}
	

	public BigDecimal getApplicabilityId() {
		return applicabilityId;
	}

	public void setApplicabilityId(BigDecimal applicabilityId) {
		this.applicabilityId = applicabilityId;
	}

	public String getFcreateBy() {
		return fcreateBy;
	}

	public void setFcreateBy(String fcreateBy) {
		this.fcreateBy = fcreateBy;
	}

	public Date getFcreatDate() {
		return fcreatDate;
	}

	public void setFcreatDate(Date fcreatDate) {
		this.fcreatDate = fcreatDate;
	}

	public void companyCountryValidate()
	{
		
	}
	
	private void clearBankView()
	{
		setBankApplicabilityId(null);
		setApplicabilityId(null);
		setTimeZone(null);
		setExchangeId(null);
		setBankTypeId(null);
		setUserName(null);
		setPassword(null);
		setAgentId(null);
		setAgentPin(null);
	}
	
	String countryName;
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public void popUpCountry()
	{
		try{
		//setCountryName(null);
		//setCountryId(null);
		List<CountryMasterDesc> lstCountryMasterDesc=bankApplicabilityService.getCountryFromCompany(getCompanyId(), new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		
		if(lstCountryMasterDesc.size()>0)
		{
			for(CountryMasterDesc countryMasterDesc :lstCountryMasterDesc)
			{
				
				setCountryName(countryMasterDesc.getCountryName());
				setCountryId(countryMasterDesc.getFsCountryMaster().getCountryId());
			}
		}
		} catch (NullPointerException  e) {
			setErrorMsg("Method Name: populateBankApplicabilityDetails");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
			catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		
	}
	
	
	
/* new code by Viswa */
	
	@Autowired
	IBankApprovalService bankApprovalService;
	
	private String createdBy = null;
	private Date createdDate = null;
	private String remarks=null;
	private boolean approval = false;
	
	private String isActive = null;
	private String dynamicLabelForActivateDeactivate=null;

	
	private List<BankApplicabilityDataTable> approvalList = new ArrayList<BankApplicabilityDataTable>();
	private List<BankApplicabilityDataTable> activationList = new ArrayList<BankApplicabilityDataTable>();
	
	public void approvalNavigation() {
		approvalList.clear();
		setApproval(true);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankapplicabilityapprovallist.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../treasury/bankapplicabilityapprovallist.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void activationNavigation() {
		setApproval(true);
		activationList.clear();
		loadDataTableList();
		setRemarks(null);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankapplicabilityactiveinactivelist.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../treasury/bankapplicabilityactiveinactivelist.xhtml");
		} catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
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

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public List<BankApplicabilityDataTable> getApprovalList() {
		
		approvalList.clear();
		BankApplicabilityDataTable bankApplicabilityDataTable = null;
		
		List<BankApplicability> approvalist = bankApprovalService.getBankApplicabilityListForApproval();
		
		for (BankApplicability bankApplicability : approvalist) {
			bankApplicabilityDataTable = new BankApplicabilityDataTable();
			
			bankApplicabilityDataTable.setBankApplicabilityId(bankApplicability.getApplicabilityId());
			bankApplicabilityDataTable.setCompanyId(bankApplicability.getFsCompanyMaster().getCompanyId());
		    getCompanyListFromDB();
			bankApplicabilityDataTable.setCompanyName(mapCompanyList.get(bankApplicability.getFsCompanyMaster().getCompanyId()));
			/*bankApplicabilityDataTable.setBankTypeId(bankApplicability.getBankInd().getBankIndicatorId());
			bankApplicabilityDataTable.setBankTypeName(mapbankIndicator.get(bankApplicability.getBankInd().getBankIndicatorId()));*/
			bankApplicabilityDataTable.setBankId(bankApplicability.getBankMaster().getBankId());
			bankApplicabilityDataTable.setBankName(bankApplicability.getBankMaster().getBankFullName());
			bankApplicabilityDataTable.setCountryId(bankApplicability.getFsCountryMaster().getCountryId());
			bankApplicabilityDataTable.setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), bankApplicability.getFsCountryMaster().getCountryId()));
			bankApplicabilityDataTable.setIsActive(bankApplicability.getIsActive());
			bankApplicabilityDataTable.setCreatedBy(bankApplicability.getCreator());
			bankApplicabilityDataTable.setCreatedDate(bankApplicability.getCreateDate());
			bankApplicabilityDataTable.setModifiedBy( bankApplicability.getModifier());
			bankApplicabilityDataTable.setModifiedDate(bankApplicability.getUpdateDate() );
			bankApplicabilityDataTable.setApprovedBy(sessionStateManage.getUserName());
			bankApplicabilityDataTable.setApprovedDate(new Date());
			//added @koti
			bankApplicabilityDataTable.setBankTypeId(bankApplicability.getBankInd().getBankIndicatorId());
			bankApplicabilityDataTable.setBankCode(bankApplicability.getBankMaster().getBankCode());
			
			
			bankApplicabilityDataTable.setBankTypeName(ibankIndService.getBankIndicatorInEnglish(bankApplicability .getBankInd().getBankIndicatorId()));//nag
			approvalList.add(bankApplicabilityDataTable);
		}
		return approvalList;
	}
	@Autowired
	IBankIndicatorService ibankIndService;
	public void gotoApproval(BankApplicabilityDataTable datatable) throws IOException{
	try{
		
		//if(CommonUtil.validateApprovedBy(sessionStateManage.getUserName(), datatable.getCreatedBy())== true){
			if((datatable.getModifiedBy()==null ? datatable.getCreatedBy() : datatable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())){
			RequestContext.getCurrentInstance().execute("unapprove.show();");
		}else{
	/*	if(getCompanyId()!=null && getCountryId()!=null && getBankTypeId()!=null && getBankId()!=null){*/
		List<BankApplicability> listApproval = bankApplicabilityService.getBankApplicabilityApproval(datatable.getBankApplicabilityId());
		
			if(listApproval.size()>0){
				
				setExchangeId(listApproval.get(0).getExchangeId());
				if(listApproval.get(0).getTimezone() != null){
				setTimeZone(listApproval.get(0).getTimezone().toPlainString());
				}else{
					throw new Exception("time zone comming null values :: timeZone");
				}
				setCreatedByApproval(listApproval.get(0).getCreator());
				setCraetedApprovalDate(listApproval.get(0).getCreateDate());
			    setBankApplicabilityId(listApproval.get(0).getApplicabilityId());
				setCompanyId(listApproval.get(0).getFsCompanyMaster().getCompanyId());
				getCompanyListFromDB();
				setCompanyNameApproval(mapCompanyList.get(listApproval.get(0).getFsCompanyMaster().getCompanyId()));
				setCountryId(listApproval.get(0).getFsCountryMaster().getCountryId());
				setCountryNameApproval(generalService.getCountryName(sessionStateManage.getLanguageId(), listApproval.get(0).getFsCountryMaster().getCountryId()));
				setBankId(listApproval.get(0).getBankMaster().getBankId());
				setBankNameApproval(listApproval.get(0).getBankMaster().getBankFullName());
				setBankTypeId(listApproval.get(0).getBankInd().getBankIndicatorId());
				getBankindicatorlist();
				setBankTypeApproval(ibankIndService.getDescriptionRecordFromDB( listApproval.get(0).getBankInd().getBankIndicatorId()).get( 0).getBankIndicatorDescription());
				//setBankApplicabilityId(listApproval.get(0).getApplicabilityId());
				//setIsActive(listApproval.get(0).getIsActive());
				setRemarks(listApproval.get(0).getRemarks());
				setExchangeId(listApproval.get(0).getExchangeId());
				setUserName(listApproval.get(0).getWebsrvUsername());
				setPassword(listApproval.get(0).getWebsrvPassword());
				setAgentId(listApproval.get(0).getWebsrvAgntId());
				setAgentPin(listApproval.get(0).getWebsrvAgntPin());
				
			}
			

			FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../treasury/bankapplicabilityapproval.xhtml");
		}
	}catch (NullPointerException  e) {
		setErrorMsg("Method Name: gotoApproval");
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}
		catch (Exception e) {
		setErrorMsg(e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");
	}
	}
	//}
	
	public void bankApplicableApprove() {
		
	try{
			
			log.info("the country id is" + getCountryId());
			log.info("the company id is" + getCompanyId());
			log.info("the bank id is" + getBankId());
		/*blocked 17/03/2015 @koti before only checking validation	
		if(CommonUtil.validateApprovedBy(sessionStateManage.getUserName(), getCreatedByApproval())!= true){*/
			
			BankApplicability bankApplicability = new BankApplicability();
			companyMaster = new CompanyMaster();
			countryMaster = new CountryMaster();
			bankMaster = new BankMaster();
			bankIndicator = new BankIndicator();
			
			String secretKey = getUserName();
			String secretKey1 = getAgentId();
			iCypherSecurity cypherSecurity = new CypherSecurityImpl();
			countryMaster.setCountryId(getCountryId());
			companyMaster.setCompanyId(getCompanyId());
			bankMaster.setBankId(getBankId());
			bankIndicator.setBankIndicatorId(getBankTypeId());
			
			bankApplicability.setFsCountryMaster(countryMaster);
			bankApplicability.setFsCompanyMaster(companyMaster);
			bankApplicability.setBankMaster(bankMaster);
			bankApplicability.setTimezone(new BigDecimal(getTimeZone()));
			bankApplicability.setExchangeId(getExchangeId());
			bankApplicability.setBankInd(bankIndicator);
			bankApplicability.setWebsrvUsername(getUserName());
			bankApplicability.setWebsrvPassword(cypherSecurity.encodePassword(getPassword(), secretKey));
			bankApplicability.setWebsrvAgntId(getAgentId());
			bankApplicability.setWebsrvAgntPin(cypherSecurity.encodePassword(getAgentId(), secretKey1));
			bankApplicability.setCreateDate(getCraetedApprovalDate());
			bankApplicability.setCreator(getCreatedByApproval());
			bankApplicability.setApplicabilityId(getBankApplicabilityId());
			bankApplicability.setIsActive(Constants.Yes);
			
			bankApplicability.setApprovedBy(sessionStateManage.getUserName());
			bankApplicability.setApprovedDate(new Date());
			
			getBankApplicabilityService().saveBankApplicabilityDetails(bankApplicability);
			
			//END OF PROCEDURE CALL
 			HashMap<String, String>  approveRecord = new HashMap<String, String>();
			approveRecord.put("BANK_ID", getBankId().toPlainString());
			String bankCode=generalService.getBankCode(getBankId());
			approveRecord.put("BANK_CODE",bankCode);
			
			HashMap<String, String>   ouputValues =getBankApplicabilityService().callPopulateBankApplicability(approveRecord);
			
			if(ouputValues.get("P_ERROR_MESSAGE")!=null){
				setErrorMsg(ouputValues.get("P_ERROR_MESSAGE"));
				RequestContext.getCurrentInstance().execute("csp.show();");
				return ; 
			}
			else{		
			RequestContext.getCurrentInstance().execute("approve.show();");
			}
	}catch (NullPointerException  e) {
		setErrorMsg("Method Name: gotoApproval");
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}
		catch (Exception e) {
		setErrorMsg(e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");
	}
			}/*
			blocked 17/03/2015 @koti before only checking validation 
			else{
				RequestContext.getCurrentInstance().execute("unapprove.show();");
			}
		
	}*/
	
	/*public void bankApplicableApprove(){
		
		List<BankApplicability> listInfo = bankApplicabilityService.getBankApplicability(getCompanyId(), getCountryId(), getBankTypeId(), getBankId());
		
		if(listInfo.size()!=0){
			setCreatedBy(listInfo.get(0).getCreator());
		}
		
		if(CommonUtil.validateApprovedBy(sessionStateManage.getUserName(), getCreatedBy())==true){
			approve();
			RequestContext.getCurrentInstance().execute("approve.show();");
		}else{
			RequestContext.getCurrentInstance().execute("unapprove.show();");
		}
	}*/

	
	/*public void gotoApproval(BankApplicabilityDataTable datatable) {		
		//approvalList.clear();
		
		try {
			
			FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../treasury/bankapplicabilityapproval.xhtml");
			fethRecordForApproval(datatable);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}*/

	public void setApprovalList(List<BankApplicabilityDataTable> approvalList) {
		this.approvalList = approvalList;
	}

	public List<BankApplicabilityDataTable> getActivationList() {/*
		activationList.clear();
		BankApplicabilityDataTable bankApplicabilityDataTable = null;
		
		List<BankApplicability> bankApplicabilityActivelist = bankApprovalService.getBankApplicabilityListForActiveInactive();
		
		for (BankApplicability bankApplicability : bankApplicabilityActivelist) {
			bankApplicabilityDataTable = new BankApplicabilityDataTable();
			
			bankApplicabilityDataTable.setBankApplicabilityId(bankApplicability.getApplicabilityId());
			bankApplicabilityDataTable.setCompanyId(bankApplicability.getFsCompanyMaster().getCompanyId());
		    //getCompanyListFromDB();
			bankApplicabilityDataTable.setCompanyName(mapCompanyList.get(bankApplicability.getFsCompanyMaster().getCompanyId()));
			bankApplicabilityDataTable.setBankTypeId(bankApplicability.getBankInd().getBankIndicatorId());
		
			//bankApplicabilityDataTable.setBankTypeName(mapbankIndicator.get(bankApplicability.getBankInd().getBankIndicatorId()));
			//bankApplicabilityDataTable.setBankTypeName(bankApplicability.getBankInd().toString());
			bankApplicabilityDataTable.setBankId(bankApplicability.getBankMaster().getBankId());
			bankApplicabilityDataTable.setBankName(bankApplicability.getBankMaster().getBankFullName());
			bankApplicabilityDataTable.setCountryId(bankApplicability.getFsCountryMaster().getCountryId());
			//bankApplicabilityDataTable.setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), bankApplicability.getFsCountryMaster().getCountryId()));
			bankApplicabilityDataTable.setIsActive(bankApplicability.getIsActive());
			bankApplicabilityDataTable.setCreatedBy(bankApplicability.getCreator());
			bankApplicabilityDataTable.setCreatedDate(bankApplicability.getCreateDate());
			bankApplicabilityDataTable.setBankCode(bankApplicability.getBankMaster().getBankCode());
			bankApplicabilityDataTable.setBankBranchId(bankApplicability.getBankMaster().getBankId());
			
			
			bankApplicabilityDataTable.setBankName(bankApplicability.getBankMaster().getBankFullName());
			
			bankApplicabilityDataTable.setBankTypeName(bankApplicability.getBankInd().getBankIndicatorName());
			
			activationList.add(bankApplicabilityDataTable);
		}*/
		return activationList;
	}

	public void loadDataTableList()
	{
try{
		activationList.clear();
		BankApplicabilityDataTable bankApplicabilityDataTable = null;
		
		List<BankApplicability> bankApplicabilityActivelist = bankApprovalService.getBankApplicabilityListForActiveInactive();
		
		for (BankApplicability bankApplicability : bankApplicabilityActivelist) {
			bankApplicabilityDataTable = new BankApplicabilityDataTable();
			
			bankApplicabilityDataTable.setBankApplicabilityId(bankApplicability.getApplicabilityId());
			bankApplicabilityDataTable.setCompanyId(bankApplicability.getFsCompanyMaster().getCompanyId());
		    //getCompanyListFromDB();
			bankApplicabilityDataTable.setCompanyName(mapCompanyList.get(bankApplicability.getFsCompanyMaster().getCompanyId()));
			bankApplicabilityDataTable.setBankTypeId(bankApplicability.getBankInd().getBankIndicatorId());
		
			//bankApplicabilityDataTable.setBankTypeName(mapbankIndicator.get(bankApplicability.getBankInd().getBankIndicatorId()));
			//bankApplicabilityDataTable.setBankTypeName(bankApplicability.getBankInd().toString());
			bankApplicabilityDataTable.setBankId(bankApplicability.getBankMaster().getBankId());
			bankApplicabilityDataTable.setBankName(bankApplicability.getBankMaster().getBankFullName());
			bankApplicabilityDataTable.setCountryId(bankApplicability.getFsCountryMaster().getCountryId());
			//bankApplicabilityDataTable.setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), bankApplicability.getFsCountryMaster().getCountryId()));
			bankApplicabilityDataTable.setIsActive(bankApplicability.getIsActive());
			bankApplicabilityDataTable.setCreatedBy(bankApplicability.getCreator());
			bankApplicabilityDataTable.setCreatedDate(bankApplicability.getCreateDate());
			bankApplicabilityDataTable.setBankCode(bankApplicability.getBankMaster().getBankCode());
			bankApplicabilityDataTable.setBankBranchId(bankApplicability.getBankMaster().getBankId());
			
			
			bankApplicabilityDataTable.setBankName(bankApplicability.getBankMaster().getBankFullName());
			
			bankApplicabilityDataTable.setBankTypeName(ibankIndService.getBankIndicatorInEnglish(bankApplicability.getBankInd().getBankIndicatorId()));
			
			activationList.add(bankApplicabilityDataTable);
		}
		setActivationList(activationList);
}catch (NullPointerException  e) {
	setErrorMsg("Method Name: loadDataTableList");
	RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
}
	catch (Exception e) {
	setErrorMsg(e.getMessage());
	RequestContext.getCurrentInstance().execute("csp.show();");
}
	}
	public void setActivationList(List<BankApplicabilityDataTable> activationList) {
		this.activationList = activationList;
	}
	
	public void clickOnOKApprove() throws IOException{
		approvalList.clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankapplicabilityapprovallist.xhtml");
		} catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	private BigDecimal bankApplicablityID;
	
	
	public BigDecimal getBankApplicablityID() {
		return bankApplicablityID;
	}

	public void setBankApplicablityID(BigDecimal bankApplicablityID) {
		this.bankApplicablityID = bankApplicablityID;
	}

	public void getActiveInactive(BankApplicabilityDataTable datatable) {
		
		setBankId(datatable.getBankId());
		setBankName(datatable.getBankName());
		setBankCode(datatable.getBankCode());
		setBankTypeName(datatable.getBankTypeName());
		setCountryId(datatable.getCountryId());
		setCompanyId(datatable.getCompanyId());
		setBankTypeId(datatable.getBankTypeId());
		setCreatedBy(datatable.getCreatedBy());
		setCreatedDate(datatable.getCreatedDate());
		setBankApplicablityID(datatable.getBankApplicabilityId());
		if(datatable.getIsActive().equalsIgnoreCase(Constants.Yes)){
			RequestContext.getCurrentInstance().execute("remarks.show();");
		}
		if(datatable.getIsActive().equalsIgnoreCase("D")){
			RequestContext.getCurrentInstance().execute("unupprove.show();");
		}
		
	}
	
	public void updateRemarks() throws IOException {
		
		try {/*
			
			List<BankApplicability> listInfo = bankApplicabilityService.getBankApplicability(getCompanyId(), getCountryId(), getBankTypeId(), getBankId());
			
			System.out.println("the country id is" + getCountryId());
			System.out.println("the company id is" + getCompanyId());
			System.out.println("the bank id is" + getBankId());
			BankApplicability bankApplicability = new BankApplicability();
			companyMaster = new CompanyMaster();
			countryMaster = new CountryMaster();
			bankMaster = new BankMaster();
			bankIndicator = new BankIndicator();
			
			String secretKey = listInfo.get(0).getWebsrvUsername();
			String secretKey1 = listInfo.get(0).getWebsrvAgntPin();
			iCypherSecurity cypherSecurity = new CypherSecurityImpl();
			countryMaster.setCountryId(listInfo.get(0).getFsCountryMaster().getCountryId());
			companyMaster.setCompanyId(listInfo.get(0).getFsCompanyMaster().getCompanyId());
			bankMaster.setBankId(listInfo.get(0).getBankMaster().getBankId());
			bankIndicator.setBankIndicatorId(listInfo.get(0).getBankInd().getBankIndicatorId());
			
			bankApplicability.setFsCountryMaster(countryMaster);
			bankApplicability.setFsCompanyMaster(companyMaster);
			bankApplicability.setBankMaster(bankMaster);
			bankApplicability.setTimezone(listInfo.get(0).getTimezone());
			bankApplicability.setExchangeId(listInfo.get(0).getExchangeId());
			bankApplicability.setBankInd(bankIndicator);
			bankApplicability.setWebsrvUsername(listInfo.get(0).getWebsrvUsername());
			bankApplicability.setWebsrvPassword(cypherSecurity.encodePassword(listInfo.get(0).getWebsrvPassword(), secretKey));
			
			bankApplicability.setWebsrvPassword(listInfo.get(0).getWebsrvPassword());
			
			bankApplicability.setWebsrvAgntId(listInfo.get(0).getWebsrvAgntId());
			bankApplicability.setWebsrvAgntPin(cypherSecurity.encodePassword(listInfo.get(0).getWebsrvAgntPin(), secretKey1));
			bankApplicability.setWebsrvAgntPin(listInfo.get(0).getWebsrvAgntPin());
			
			bankApplicability.setCreateDate(listInfo.get(0).getCreateDate());
			bankApplicability.setCreator(listInfo.get(0).getCreator());
			bankApplicability.setApplicabilityId(listInfo.get(0).getApplicabilityId());
			
			
			if(listInfo.get(0).getIsActive().equals("Y")){
				bankApplicability.setRemarks(getRemarks());
				bankApplicability.setApprovedBy(null);
				bankApplicability.setApprovedDate(null);
				bankApplicability.setIsActive("D");
			}
			if(listInfo.get(0).getIsActive().equals("D")){
				bankApplicability.setApprovedBy(null);
				bankApplicability.setApprovedDate(null);
				bankApplicability.setIsActive("U");
				bankApplicability.setRemarks(null);
			}
			
			getBankApplicabilityService().saveBankApplicabilityDetails(bankApplicability);
			
	
		*/
			bankApplicabilityService.activeDeactiveRecord(getBankApplicablityID() , Constants.Yes,getRemarks());
			
		}	catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankapplicabilityactiveinactivelist.xhtml");
		loadDataTableList();
		setRemarks(null);
	}
	
	public void clickOnOKActivate() throws IOException{
		setRemarks(null);
		//activationList.clear();
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankapplicabilityactiveinactivelist.xhtml");
		} catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void clickOnOKDeActivate() throws IOException{
		
		try {
			//updateRemarks();
			bankApplicabilityService.activeDeactiveRecord(getBankApplicablityID() , "D","");
			activationList.clear();
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankapplicabilityactiveinactivelist.xhtml");
			loadDataTableList();
		} catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	
	public void exit() throws IOException {
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}
	
	Map<BigDecimal, String> mapCountryList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapCompanyList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapbankIndicator = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapBankList = new HashMap<BigDecimal, String>();
	
	private String companyNameApproval;
	private String countryNameApproval;
	private String bankNameApproval;
	private String bankTypeApproval;
	private String createdByApproval;
	private Date craetedApprovalDate;

	public String getCompanyNameApproval() {
		return companyNameApproval;
	}

	public void setCompanyNameApproval(String companyNameApproval) {
		this.companyNameApproval = companyNameApproval;
	}

	public String getCountryNameApproval() {
		return countryNameApproval;
	}

	public void setCountryNameApproval(String countryNameApproval) {
		this.countryNameApproval = countryNameApproval;
	}

	public String getBankNameApproval() {
		return bankNameApproval;
	}

	public void setBankNameApproval(String bankNameApproval) {
		this.bankNameApproval = bankNameApproval;
	}

	public String getBankTypeApproval() {
		return bankTypeApproval;
	}

	public void setBankTypeApproval(String bankTypeApproval) {
		this.bankTypeApproval = bankTypeApproval;
	}

	public String getCreatedByApproval() {
		return createdByApproval;
	}

	public void setCreatedByApproval(String createdByApproval) {
		this.createdByApproval = createdByApproval;
	}

	public Date getCraetedApprovalDate() {
		return craetedApprovalDate;
	}

	public void setCraetedApprovalDate(Date craetedApprovalDate) {
		this.craetedApprovalDate = craetedApprovalDate;
	}
	
	//added koti
	
	private String bankCode;
	private String bankName;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	private String bankTypeName;

	public String getBankTypeName() {
		return bankTypeName;
	}

	public void setBankTypeName(String bankTypeName) {
		this.bankTypeName = bankTypeName;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
	
}
