package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.BankContactDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Zone;
import com.amg.exchange.treasury.model.ZoneMasterDesc;
import com.amg.exchange.treasury.service.IBankMasterContactDetailsService;
import com.amg.exchange.treasury.service.IBankMasterService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("bankApproval")
@Scope("session")
public class BankApprovalBean<T> implements Serializable {

	/**
	 * @author
	 */
	private static final long serialVersionUID = -1333138778448003609L;

	Logger log = Logger.getLogger(BankMasterInfoBean.class);

	/**
	 * Constructor
	 */
	public BankApprovalBean() {
		setValidationValue();
	}

	private BigDecimal bankIdInternal = null;
	private String bankCode = null;
	private String fullName = null;
	private String shortName = null;
	private String languageBank = null;
	private String address1 = null;
	private String address2 = null;
	private BigDecimal currencyId = null;
	private BigDecimal countryId = null;
	private String currencyCode = null;
	private BigDecimal stateId = null;
	private BigDecimal districtId = null;
	private BigDecimal cityId = null;
	private String zip = null;
	private String telephone = null;
	private String fax = null;
	private String email = null;
	private String contactPerson = null;
	private String designation = null;
	private String department = null;
	private String localFullName = null;
	private String localShortname = null;
	private String localAddress1 = null;
	private String localAddress2 = null;
	private String localContactPerson = null;
	private String localDesignation = null;
	private String localDepartment = null;
	private String IFSCLength = null;
	private String reutersBankNname = null;
	private String fileSpecificOrAll = null;
	private String fileBranchWiseYOrN = null;
	private String MICRReuters = null;
	private String remmiterModeYOrN = null;
	private String allowNoBank = null;

	private Boolean booBankGenInfo = true;
	private Boolean booBankAddress = false;
	private Boolean booLocalPanelRender = false;
	private Boolean booFileAcceptance = false;
	private List<StateMasterDesc> lstStateList = new ArrayList<StateMasterDesc>();
	private List<DistrictMasterDesc> lstDistrictList = new ArrayList<DistrictMasterDesc>();
	private List<CityMasterDesc> lstCityList = new ArrayList<CityMasterDesc>();
	private List<ZoneMasterDesc> zoneMasterDesList=new ArrayList<ZoneMasterDesc>();
	private List<CurrencyMaster> lstCountryCurrencyList = new ArrayList<CurrencyMaster>();

	public String getAllowNoBank() {
		return allowNoBank;
	}

	public void setAllowNoBank(String allowNoBank) {
		this.allowNoBank = allowNoBank;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLanguageBank() {
		return languageBank;
	}

	public void setLanguageBank(String languageBank) {
		this.languageBank = languageBank;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getDistrictId() {
		return districtId;
	}

	public void setDistrictId(BigDecimal districtId) {
		this.districtId = districtId;
	}

	public BigDecimal getStateId() {
		return stateId;
	}

	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}

	public BigDecimal getCityId() {
		return cityId;
	}

	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
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

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLocalFullName() {
		return localFullName;
	}

	public void setLocalFullName(String localFullName) {
		this.localFullName = localFullName;
	}

	public String getLocalShortname() {
		return localShortname;
	}

	public void setLocalShortname(String localShortname) {
		this.localShortname = localShortname;
	}

	public String getLocalAddress1() {
		return localAddress1;
	}

	public void setLocalAddress1(String localAddress1) {
		this.localAddress1 = localAddress1;
	}

	public String getLocalAddress2() {
		return localAddress2;
	}

	public void setLocalAddress2(String localAddress2) {
		this.localAddress2 = localAddress2;
	}

	public String getLocalContactPerson() {
		return localContactPerson;
	}

	public void setLocalContactPerson(String localContactPerson) {
		this.localContactPerson = localContactPerson;
	}

	public String getLocalDesignation() {
		return localDesignation;
	}

	public void setLocalDesignation(String localDesignation) {
		this.localDesignation = localDesignation;
	}

	public String getLocalDepartment() {
		return localDepartment;
	}

	public void setLocalDepartment(String localDepartment) {
		this.localDepartment = localDepartment;
	}

	public String getIFSCLength() {
		return IFSCLength;
	}

	public void setIFSCLength(String iFSCLength) {
		IFSCLength = iFSCLength;
	}

	public String getReutersBankNname() {
		return reutersBankNname;
	}

	public void setReutersBankNname(String reutersBankNname) {
		this.reutersBankNname = reutersBankNname;
	}

	public String getFileSpecificOrAll() {
		return fileSpecificOrAll;
	}

	public void setFileSpecificOrAll(String fileSpecificOrAll) {
		this.fileSpecificOrAll = fileSpecificOrAll;
	}

	public String getFileBranchWiseYOrN() {
		return fileBranchWiseYOrN;
	}

	public void setFileBranchWiseYOrN(String fileBranchWiseYOrN) {
		this.fileBranchWiseYOrN = fileBranchWiseYOrN;
	}

	public String getMICRReuters() {
		return MICRReuters;
	}

	public void setMICRReuters(String mICRReuters) {
		MICRReuters = mICRReuters;
	}

	public String getRemmiterModeYOrN() {
		return remmiterModeYOrN;
	}

	public void setRemmiterModeYOrN(String remmiterModeYOrN) {
		this.remmiterModeYOrN = remmiterModeYOrN;
	}

	public List<StateMasterDesc> getLstStateList() {
		return lstStateList;
	}

	public void setLstStateList(List<StateMasterDesc> lstStateList) {
		this.lstStateList = lstStateList;
	}

	public List<DistrictMasterDesc> getLstDistrictList() {
		return lstDistrictList;
	}

	public void setLstDistrictList(List<DistrictMasterDesc> lstDistrictList) {
		this.lstDistrictList = lstDistrictList;
	}

	public List<CityMasterDesc> getLstCityList() {
		return lstCityList;
	}

	public void setLstCityList(List<CityMasterDesc> lstCityList) {
		this.lstCityList = lstCityList;
	}

	public Boolean getBooBankGenInfo() {
		return booBankGenInfo;
	}

	public void setBooBankGenInfo(Boolean booBankGenInfo) {
		this.booBankGenInfo = booBankGenInfo;
	}

	public Boolean getBooBankAddress() {
		return booBankAddress;
	}

	public void setBooBankAddress(Boolean booBankAddress) {
		this.booBankAddress = booBankAddress;
	}

	public boolean getBooLocalPanelRender() {
		return booLocalPanelRender;
	}

	public void setBooLocalPanelRender(boolean booLocalPanelRender) {
		this.booLocalPanelRender = booLocalPanelRender;
	}

	public Boolean getBooFileAcceptance() {
		return booFileAcceptance;
	}

	public void setBooFileAcceptance(Boolean booFileAcceptance) {
		this.booFileAcceptance = booFileAcceptance;
	}

	public BigDecimal getBankIdInternal() {
		return bankIdInternal;
	}

	public void setBankIdInternal(BigDecimal bankIdInternal) {
		this.bankIdInternal = bankIdInternal;
	}

	@Autowired
	IGeneralService<T> iGeneralService;

	public IGeneralService<T> getiGeneralService() {
		return iGeneralService;
	}

	public void setiGeneralService(IGeneralService<T> iGeneralService) {
		this.iGeneralService = iGeneralService;
	}

	@Autowired
	IBankMasterService<T> bankMasterInfoService;

	public IBankMasterService<T> getBankMasterInfoService() {
		return bankMasterInfoService;
	}

	public void setBankMasterInfoService(
			IBankMasterService<T> bankMasterInfoService) {
		this.bankMasterInfoService = bankMasterInfoService;
	}

	public List<CurrencyMaster> getLstCountryCurrencyList() {
		return lstCountryCurrencyList;
	}

	public void setLstCountryCurrencyList(
			List<CurrencyMaster> lstCountryCurrencyList) {
		this.lstCountryCurrencyList = lstCountryCurrencyList;
	}

	
	public List<ZoneMasterDesc> getZoneMasterDesList() {
		return zoneMasterDesList;
	}

	public void setZoneMasterDesList(List<ZoneMasterDesc> zoneMasterDesList) {
		this.zoneMasterDesList = zoneMasterDesList;
	}

	/**
	 * Responsible to populate Country
	 * 
	 * @return
	 */
	SessionStateManage sessionStateManage = new SessionStateManage();

	public List<CountryMasterDesc> getCountryNameList() {
		// SessionStateManage sessionStateManage = new SessionStateManage();
		List<CountryMasterDesc> lstCountry = getiGeneralService()
				.getCountryList(
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "" + 1));
		return lstCountry;
	}

	/**
	 * Responsible to prepare stateList by country change
	 * 
	 * @param event
	 */
	public void generateStateList(AjaxBehaviorEvent event) {
		// SessionStateManage sessionStateManage = new SessionStateManage();
		List<StateMasterDesc> stateMaster = getiGeneralService()
				.getStateList(
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "" + 1),
						getCountryId());
		setLstStateList(stateMaster);

		if (getCountryId() != null && getCountryId().intValue() != sessionStateManage.getCountryId().intValue()) {
			setBooLocalPanelRender(false);
		} else {
			setBooLocalPanelRender(true);
		}
		List<CurrencyMaster> lstCurrency = getCurrencyList();

		setLstCountryCurrencyList(lstCurrency);
		/* reset State, District, City, as country is changing */
		setCurrencyCode(null);
		setStateId(null);
		setDistrictId(null);
		setCityId(null);

		setLstDistrictList(null);
		setLstCityList(null);

	}

	/**
	 * Responsible to populate District depending upon state selection
	 * 
	 * @param event
	 */
	public void generateDistrictList(AjaxBehaviorEvent event) {
		// SessionStateManage sessionStateManage = new SessionStateManage();
		List<DistrictMasterDesc> lstDistrict = getiGeneralService()
				.getDistrictList(
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "" + 1),
						getCountryId(), getStateId());
		setLstDistrictList(lstDistrict);

		/* reset District, City, as state is changing */
		setDistrictId(null);
		setCityId(null);
	}

	/**
	 * Responsible to populate city depending upon district selection
	 * 
	 * @param event
	 */
	public void generateCityList(AjaxBehaviorEvent event) {
		// SessionStateManage sessionStateManage = new SessionStateManage();
		List<CityMasterDesc> lstCity = getiGeneralService()
				.getCityList(
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "" + 1),
						getCountryId(), getStateId(), getDistrictId());
		setLstCityList(lstCity);

	}

	/**
	 * This method will call when city change happen It's a do-nothing method,
	 * except this after click back button, selected cityId will not bind with
	 * bean
	 * 
	 * @param event
	 */
	public void getCity(AjaxBehaviorEvent event) {
	}

	/**
	 * This method is responsible to populate State depending upon country
	 * selection
	 */
	private void populateState() {

		// SessionStateManage sessionStateManage = new SessionStateManage();
		List<StateMasterDesc> stateMaster = getiGeneralService()
				.getStateList(
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "" + 1),
						getCountryId());
		setLstStateList(stateMaster);
	}

	/**
	 * This method is responsible to populate State depending upon country
	 * selection
	 */
	private void populateCurrency() {

		// SessionStateManage sessionStateManage = new SessionStateManage();
		List<CurrencyMaster> lstCountryCurrencyList = getiGeneralService()
				.getCountryCurrencyList(getCountryId());

		setLstCountryCurrencyList(lstCountryCurrencyList);
	}

	/**
	 * This method is responsible to populate district depending upon state
	 * election
	 */
	private void populateDistrict() {
		// SessionStateManage sessionStateManage = new SessionStateManage();
		List<DistrictMasterDesc> lstDistrict = getiGeneralService()
				.getDistrictList(
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "" + 1),
						getCountryId(), getStateId());
		setLstDistrictList(lstDistrict);
	}

	/**
	 * This method is responsible to populate City depending upon district
	 * selection
	 */
	private void populateCity() {
		// SessionStateManage sessionStateManage = new SessionStateManage();
		List<CityMasterDesc> lstCity = getiGeneralService()
				.getCityList(
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "" + 1),
						getCountryId(), getStateId(), getDistrictId());
		setLstCityList(lstCity);
	}

	/**
	 * method is responsible to cancel the Bank Master
	 * 
	 * @return
	 */
	public void cancel() {
		try {
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect("../login/login.xhtml");
		} catch (Exception e) {
			log.info("Redirection Problem to Login by Cancel button");
		}
	}

	/**
	 * Responsible to save Bank Master details
	 * 
	 * @return
	 */
	public void save() {
		BankMaster bankMaster = new BankMaster();
		bankMaster.setBankCode(getBankCode());
		bankMaster.setBankFullName(getFullName());
		bankMaster.setBankShortName(getShortName());
		bankMaster.setLanguageInd(getLanguageBank());
		bankMaster.setAddress1(getAddress1());
		bankMaster.setAddress2(getAddress2());
		bankMaster.setZipCode(getZip());
		bankMaster.setTeleponeNo(getTelephone());
		bankMaster.setFaxNo(getFax());
		bankMaster.setEmail(getEmail());
		bankMaster.setBankFullNameAr(getLocalFullName());
		bankMaster.setBankShortNameAr(getLocalShortname());
		bankMaster.setAddress1Ar(getLocalAddress1());
		bankMaster.setAddress2Ar(getLocalAddress2());
		bankMaster.setIfscLen(new BigDecimal(getIFSCLength()));
		bankMaster.setReutersBankName(getReutersBankNname());
		bankMaster.setFileAlls(getFileSpecificOrAll());
		bankMaster.setFileBranch(getFileBranchWiseYOrN());
		// bankMaster.setMicrCode(getMICRReuters());
		bankMaster.setFileRemitMode(getRemmiterModeYOrN());

		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(getCountryId());
		bankMaster.setFsCountryMaster(countryMaster);
		bankMaster.setAllowNoBank(getAllowNoBank());
		/*
		 * StateMaster statemaster = new StateMaster();
		 * statemaster.setStateId(getStateId());
		 * bankMaster.setFsStateMaster(statemaster);
		 */
		/*
		 * DistrictMaster districtMaster = new DistrictMaster();
		 * districtMaster.setDistrictId(getDistrictId());
		 * bankMaster.setFsDistrictMaster(districtMaster);
		 */

		/*
		 * CityMaster cityMaster = new CityMaster();
		 * cityMaster.setCityId(getCityId());
		 * bankMaster.setFsCityMaster(cityMaster);
		 */

		/*
		 * CurrencyMaster currencyMaster = new CurrencyMaster();
		 * currencyMaster.setCurrencyId(getCurrencyId());
		 * bankMaster.setCurrencyMaster(currencyMaster);
		 */

		// TODO hard code user name
		bankMaster.setCreator(sessionStateManage.getUserName());
		bankMaster.setCreateDate(new Date());
		bankMaster.setRecordStatus(Constants.Yes);
		/* It will return true always, except the very first time */
		if (getBankIdInternal() != null) {
			bankMaster.setBankId(getBankIdInternal());
			bankMaster.setUpdateDate(new Date());
			bankMaster.setModifier(sessionStateManage.getUserName());

		}
		getBankMasterInfoService().saveBankMasterInfo(bankMaster);

		/* setting primary key */
		setBankIdInternal(bankMaster.getBankId());
		saveForContactDetails(bankMaster);
		clear();
		/*
		 * try{ ExternalContext context =
		 * FacesContext.getCurrentInstance().getExternalContext();
		 * context.redirect("../treasury/bankcontactdetails.xhtml"); }
		 * catch(Exception e) { log.info("Problem to redirect:"+e); }
		 */
	}

	/**
	 * Responsible to clear all component of Bank Master
	 * 
	 * @return
	 */
	public void clear() {
		setAllowNoBank(null);
		setBankCode("");
		setFullName("");
		setShortName("");
		setLanguageBank("");
		setAddress1("");
		setAddress2("");
		setCountryId(null);
		setStateId(null);
		setDistrictId(null);
		setCityId(null);
		setZip("");
		setTelephone("");
		setFax("");
		setEmail("");
		setContactPerson("");
		setDesignation("");
		setDepartment("");
		setLocalFullName("");
		setLocalShortname("");
		setLocalAddress1("");
		setLocalAddress2("");
		setLocalContactPerson("");
		setLocalDesignation("");
		setLocalDepartment("");
		setIFSCLength(null);
		setReutersBankNname("");
		setFileSpecificOrAll("");
		setFileBranchWiseYOrN("");
		setMICRReuters("");
		setRemmiterModeYOrN("");

		setBooBankGenInfo(true);
		setBooBankAddress(false);
		setBooFileAcceptance(false);

		setBankIdInternal(null);
		setCurrencyId(null);
	}

	/**
	 * Fetch data depending upon given bank Code
	 */
	public void fetchBankMasterInfo() {
		System.out.println("inside fetchBankMasterInfo KKKKKKKKKKKKKKKKKKKK");
		List<BankMaster> lstBankMasterInfo = new ArrayList<BankMaster>();
		/* Checking that BankCode field empty or not */
		if (getBankCode() != null && getBankCode().length() > 0) {
			lstBankMasterInfo = getBankMasterInfoService().getBankMasterInfo(
					getBankCode());
			/*
			 * checking that data is there in database or not, for the given
			 * Bank Code
			 */
			if (lstBankMasterInfo != null && lstBankMasterInfo.size() > 0) {
				/* setting primary key */
				setBankIdInternal(lstBankMasterInfo.get(0).getBankId());
				/* setBankCode(lstBankMasterInfo.get(0).getBankCode()); */
				setFullName(lstBankMasterInfo.get(0).getBankFullName());
				setShortName(lstBankMasterInfo.get(0).getBankShortName());
				setLanguageBank(lstBankMasterInfo.get(0).getLanguageInd());
				setAddress1(lstBankMasterInfo.get(0).getAddress1());
				setAddress2(lstBankMasterInfo.get(0).getAddress2());
				setCountryId(lstBankMasterInfo.get(0).getFsCountryMaster()
						.getCountryId());
				// setCurrencyId(lstBankMasterInfo.get(0).getCurrencyMaster().getCurrencyId());

				/*
				 * Now we need to populate state depending upon country(saved),
				 * otherwise state will not set with the state previously saved
				 * state
				 */
				populateState();
				// setStateId(lstBankMasterInfo.get(0).getFsStateMaster().getStateId());
				/*
				 * Now we need to populate District depending upon state(saved),
				 * otherwise district will not set with the district previously
				 * saved district
				 */
				populateDistrict();
				// setDistrictId(lstBankMasterInfo.get(0).getFsDistrictMaster().getDistrictId());
				/*
				 * Now we need to populate City depending upon city(saved),
				 * otherwise city will not set with the city previously saved
				 * city
				 */
				populateCity();
				populateCurrency();
				// setCityId(lstBankMasterInfo.get(0).getFsCityMaster().getCityId());
				setZip(lstBankMasterInfo.get(0).getZipCode());
				setTelephone(lstBankMasterInfo.get(0).getTeleponeNo());
				setFax(lstBankMasterInfo.get(0).getFaxNo());
				setEmail(lstBankMasterInfo.get(0).getEmail());
				setLocalFullName(lstBankMasterInfo.get(0).getBankFullNameAr());
				setLocalShortname(lstBankMasterInfo.get(0).getBankShortNameAr());
				setLocalAddress1(lstBankMasterInfo.get(0).getAddress1Ar());
				setLocalAddress2(lstBankMasterInfo.get(0).getAddress2Ar());
				setIFSCLength(lstBankMasterInfo.get(0).getIfscLen()
						.toPlainString());
				setReutersBankNname(lstBankMasterInfo.get(0)
						.getReutersBankName());
				setFileSpecificOrAll(lstBankMasterInfo.get(0).getFileAlls());
				setFileBranchWiseYOrN(lstBankMasterInfo.get(0).getFileBranch());
				// setMICRReuters(lstBankMasterInfo.get(0).getMicrCode());
				setRemmiterModeYOrN(lstBankMasterInfo.get(0).getFileRemitMode());
				// Added by kain begin
				setAllowNoBank(lstBankMasterInfo.get(0).getAllowNoBank());

				System.out.println("select Bank id KKKKKKKKKKKKKKKKKKK-----"
						+ lstBankMasterInfo.get(0).getBankId());
				fetchBankMasterContactDetails(lstBankMasterInfo.get(0)
						.getBankId());
				System.out
						.println("fetchBankMasterContactDetails    KKKKKKKKKKKKKKKKKKK-----"
								+ lstBankMasterInfo.get(0).getBankId());

				// added by kani end

			} else {
				String bankCode = getBankCode();
				clear();
				setBankCode(bankCode);
			}
		}
		setBooBankGenInfo(true);
		setBooBankAddress(false);
		setBooFileAcceptance(false);
	}

	/**
	 * This method is responsible to set the validation value from rule engine
	 */
	public void setValidationValue() {

	}

	/**
	 * This method will call when OK will clicked from confirm dialogue box
	 */
	public void welcomeListener() {
		// System.out.println("Welcome Back");
	}

	/**
	 * This method will call when Log Me Out button will press from confirm
	 * dialogue box
	 * 
	 * @throws IOException
	 */
	public void logoutListener() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		ec.invalidateSession();
		// System.out.println(ec.getRequestContextPath()) ....This will print
		// the "/AlmullaExchange"...Context Name..That's why we store External
		// context in 'ec' variable
		ec.redirect(ec.getRequestContextPath() + "/index.jsp");
	}

	/**
	 * When Next click from first(bankInfoPanel) panel
	 */
	public void bankInfoPanelNext() {
		setBooBankGenInfo(false);
		setBooBankAddress(true);
		setBooFileAcceptance(false);

		/*
		 * First time we have to make this local panel invisible, at that time
		 * Id will be null
		 */
		if (getCountryId() == null) {
			setBooLocalPanelRender(false);
		} else if (getCountryId() != null && getCountryId().intValue() != sessionStateManage.getCountryId().intValue()) {
			setBooLocalPanelRender(false);
		} else {
			setBooLocalPanelRender(true);
		}
	}

	/**
	 * Next button clicked from Address panel
	 */
	public void bankAddressInfoPanelNext() {
		setBooBankGenInfo(false);
		setBooBankAddress(false);
		setBooFileAcceptance(true);
	}

	/**
	 * Back button clicked from address panel
	 */
	public void bankAddressInfoPanelBack() {
		setBooBankGenInfo(true);
		setBooBankAddress(false);
		setBooFileAcceptance(false);
	}

	/**
	 * Back button pressed from file acceptance panel
	 */
	public void fileAcceptancePanelBack() {
		setBooBankGenInfo(false);
		setBooBankAddress(true);
		setBooFileAcceptance(false);
	}

	public List<CurrencyMaster> getCurrencyList() {
		List<CurrencyMaster> lstCurrency = getiGeneralService()
				.getCountryCurrencyList(getCountryId());

		return lstCurrency;
	}

	/* start 31/01/2015 added @koti */
	public void bankMasterPageNavigation() {
		clear();
		/*
		 * setbooBankAddress = false; setbooLocalPanelRender = false;
		 * setbooFileAcceptance = false;
		 */

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/bankmaster.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* ended 31/01/2015 added @koti */

	// Bank contact details Combing ----- RAHAMATHALI SHAIK

	private BigDecimal contactIdInternal = null;
	private BigDecimal contactBankId = null;
	private String bankName = null;
	private BigDecimal contactZone = null;
	private String contactPersonForContactDetails = null;
	private String contactDep = null;
	private String contactDesignation = null;
	private String contactMobile = null;
	private String localContactPersonForContactDetails = null;
	private String localContactDepartment = null;
	private String localContactDesignation = null;
	private BigDecimal zoneId = null;
	private String ZoneName = null;
	private Map<BigDecimal, String> bankInfo = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> zoneInfo = new HashMap<BigDecimal, String>();
	private List<BankMasterContactDetails> lstBankMasterContactDetails = new ArrayList<BankMasterContactDetails>();
	private List<BankMasterContactDetails> lstBankMasterDeletedContactDetails = new ArrayList<BankMasterContactDetails>();
	private List<BankContactDetails> contactDetails = new ArrayList<BankContactDetails>();
	private List<Zone> lstZoneLists = new ArrayList<Zone>();

	private Boolean booLocal = true;

	public BigDecimal getContactIdInternal() {
		return contactIdInternal;
	}

	public void setContactIdInternal(BigDecimal contactIdInternal) {
		this.contactIdInternal = contactIdInternal;
	}

	public BigDecimal getContactBankId() {
		return contactBankId;
	}

	public void setContactBankId(BigDecimal contactBankId) {
		this.contactBankId = contactBankId;
	}

	public BigDecimal getContactZone() {
		return contactZone;
	}

	public void setContactZone(BigDecimal contactZone) {
		this.contactZone = contactZone;
	}

	public String getContactPersonForContactDetails() {
		return contactPersonForContactDetails;
	}

	public void setContactPersonForContactDetails(
			String contactPersonForContactDetails) {
		this.contactPersonForContactDetails = contactPersonForContactDetails;
	}

	public String getContactDep() {
		return contactDep;
	}

	public void setContactDep(String contactDep) {
		this.contactDep = contactDep;
	}

	public String getContactDesignation() {
		return contactDesignation;
	}

	public void setContactDesignation(String contactDesignation) {
		this.contactDesignation = contactDesignation;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getLocalContactPersonForContactDetails() {
		return localContactPersonForContactDetails;
	}

	public void setLocalContactPersonForContactDetails(
			String localContactPersonForContactDetails) {
		this.localContactPersonForContactDetails = localContactPersonForContactDetails;
	}

	public String getLocalContactDepartment() {
		return localContactDepartment;
	}

	public void setLocalContactDepartment(String localContactDepartment) {
		this.localContactDepartment = localContactDepartment;
	}

	public String getLocalContactDesignation() {
		return localContactDesignation;
	}

	public void setLocalContactDesignation(String localContactDesignation) {
		this.localContactDesignation = localContactDesignation;
	}

	public List<BankMasterContactDetails> getLstBankMasterContactDetails() {
		return lstBankMasterContactDetails;
	}

	public List<BankMasterContactDetails> getLstBankMasterDeletedContactDetails() {
		return lstBankMasterDeletedContactDetails;
	}

	public List<BankContactDetails> getContactDetails() {
		return contactDetails;
	}

	public Boolean getBooLocal() {
		return booLocal;
	}

	public void setBooLocal(Boolean booLocal) {
		this.booLocal = booLocal;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BigDecimal getZoneId() {
		return zoneId;
	}

	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneName() {
		return ZoneName;
	}

	public void setZoneName(String zoneName) {
		ZoneName = zoneName;
	}

	public List<Zone> getLstZoneLists() {
		return lstZoneLists;
	}

	public void setLstZoneLists(List<Zone> lstZoneLists) {
		this.lstZoneLists = lstZoneLists;
	}

	/*public List<Zone> getZoneLists() {
		SessionStateManage sessionStateManage = new SessionStateManage();
		lstZoneLists = getBankMasterContactDetailsService()
				.getZoneList(
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "" + 1));
		System.out.println("lstZone  List :" + lstZoneLists.size());
		for (Zone zone : lstZoneLists) {
			zoneInfo.put(zone.getZoneId(), zone.getZoneName());

			System.out.println("id :" + zone.getZoneId() + ":   name : "
					+ zone.getZoneName());
			setZoneId(zone.getZoneId());
			System.out.println("setZoneId    :" + getZoneId());
		}

		return lstZoneLists;
	}
*/
	public List<ZoneMasterDesc> getZoneLists() {
		SessionStateManage sessionStateManage = new SessionStateManage();
		zoneMasterDesList = getBankMasterContactDetailsService().getZoneList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1));
		//System.out.println("lstZone  List :" + lstZoneLists.size());
		for (ZoneMasterDesc ZoneMasterDesc : zoneMasterDesList) {
			zoneInfo.put(ZoneMasterDesc.getZone().getZoneId(), ZoneMasterDesc.getZoneDescription());

		//	System.out.println("id :" + zone.getZoneId() + ":   name : " + zone.getZoneName());
			setZoneId(ZoneMasterDesc.getZone().getZoneId());
			System.out.println("setZoneId    :" + getZoneId());
		}

		return zoneMasterDesList;
	}

	@Autowired
	IBankMasterContactDetailsService<T> bankMasterContactDetailsService;

	public IBankMasterContactDetailsService<T> getBankMasterContactDetailsService() {
		return bankMasterContactDetailsService;
	}

	public void setBankMasterContactDetailsService(
			IBankMasterContactDetailsService<T> bankMasterContactDetailsService) {
		this.bankMasterContactDetailsService = bankMasterContactDetailsService;
	}

	/**
	 * Responsible to populate Bank name what is already saved from bank master
	 * page
	 * 
	 * @return
	 */
	public List<BankMaster> getBankDetails() {
		List<BankMaster> lstCountry = getBankMasterContactDetailsService()
				.getBankMasterInfo();
		for (BankMaster bankMaster : lstCountry) {
			bankInfo.put(bankMaster.getBankId(), bankMaster.getBankFullName());
		}
		return lstCountry;
	}

	/*public List<Zone> getZoneDetails() {
		List<Zone> lstZone = getBankMasterContactDetailsService().getZoneList(
				new BigDecimal("1"));

		return lstZone;
	}*/
	public List<ZoneMasterDesc> getZoneDetails() {
		List<ZoneMasterDesc> lstZone = getBankMasterContactDetailsService().getZoneList(new BigDecimal("1"));

		return lstZone;
	}

	/**
	 * This method is responsible to ass data in datatable
	 */
	/*
	 * public void addBankMasterContactList() {
	 * System.out.println("getzone Id :"+getZoneId()
	 * +"zoneInfo :"+zoneInfo.get(new BigDecimal("1")));
	 * BankMasterContactDetails contactDetails = new
	 * BankMasterContactDetails(bankInfo.get(getContactBankId()),
	 * getContactBankId(), zoneInfo.get(getZoneId()),getZoneId(),
	 * getContactPersonForContactDetails(), getContactDep(),
	 * getContactDesignation(), getContactMobile(),
	 * getLocalContactPersonForContactDetails(), getLocalContactDepartment(),
	 * getLocalContactDesignation(), new BigDecimal(0));
	 * 
	 * lstBankMasterContactDetails.add(contactDetails);
	 * clearForContactDetails();
	 * 
	 * }
	 */

	/**
	 * This method is responsible to fetch data according to bank selection
	 */
	public void fetchBankContactInfo() {

		System.out
				.println("Entering into fetchBankContactInfo  KKKKKKKKKKKKKKKKKKKKKKK");
		lstBankMasterContactDetails.clear();
		// clear();
		if (getContactBankId() != null && getZoneId() != null) {
			setBankName(bankInfo.get(getContactBankId()));
			setZoneName(zoneInfo.get(getZoneId()));
			System.out.println("zoneInfo.get(getZoneId())    :"
					+ zoneInfo.get(getZoneId()));
			// Responsible to local panel on off
			List<BankMaster> countryOfBank = getBankMasterContactDetailsService()
					.getbankCountryInfo(getContactBankId());

			if (countryOfBank.get(0).getFsCountryMaster().getCountryId()
					.intValue() !=sessionStateManage.getCountryId().intValue()) {
				setBooLocal(false);
			} else {
				setBooLocal(true);
			}

			// Responsible to fetch contact details
			contactDetails = getBankMasterContactDetailsService()
					.getbankContactInfo(getContactBankId());
			if (contactDetails != null && contactDetails.size() > 0) {
				for (BankContactDetails element : contactDetails) {
					System.out.println("Name :"
							+ zoneInfo.get(element.getExZone().getZoneId())
							+ " : id :" + element.getExZone().getZoneId());
					BankMasterContactDetails contactDetails = new BankMasterContactDetails();

					lstBankMasterContactDetails.add(contactDetails);
				}
			}
		}
	}

	// Added by kani begin
	public void fetchBankMasterContactDetails(BigDecimal bankId) {

		// Responsible to fetch contact details
		contactDetails = getBankMasterContactDetailsService()
				.getbankContactInfo(bankId);

		System.out.println("size of   contactDetails  KKKKKKKKKKKKKKKKKK  - "
				+ contactDetails.size());

		if (contactDetails != null && contactDetails.size() > 0) {

			// System.out.println("Zone id KKKKKKKKKKKKKKKKKKKKKKKK  ---   "+
			// contactDetails.get(0).getExZone());

			setContactPersonForContactDetails(contactDetails.get(0)
					.getContactPerson());

			setZoneName(getZoneName());

			setContactDep(contactDetails.get(0).getContactDept());
			setContactDesignation(contactDetails.get(0).getContactDesg());
			// setDepartment(contactDetails.get(0).getContactDept());
			setContactMobile(contactDetails.get(0).getMobile());
			setLocalContactPersonForContactDetails(contactDetails.get(0)
					.getContactPersonAr());

			setLocalContactDepartment(contactDetails.get(0).getContactDeptAr());
			setLocalContactDesignation(contactDetails.get(0).getContactDesgAr());

			// setContactZone(contactDetails.get(0).getExZone().getZoneName());

		}

	}

	// Added by kani end
	/**
	 * This method is responsible to manage deleted objects
	 * 
	 * @param contactDetails
	 */
	public void remove(BankMasterContactDetails contactDetails) {
		lstBankMasterContactDetails.remove(contactDetails);
		if (lstBankMasterContactDetails.size() <= 0) {
			setBooRenderDataTable(false);
			setBooRenderSavePanel(false);
		}
	}

	/**
	 * get zone List
	 */
	/*
	 * public List<Zone> getZonesList(){
	 * 
	 * zoneLists = getBankMasterContactDetailsService().getZoneList(new
	 * BigDecimal("1")); System.out.println("zoneList  :"+zoneLists.size());
	 * return zoneLists; }
	 */

	public void saveContactAndBankMaster() {
		save();
		RequestContext.getCurrentInstance().execute("success.show();");
	}

	/**
	 * This method is responsible to save the data
	 */
	public void saveForContactDetails(BankMaster bankMaster) {

		// Save BankMaster Information

		System.out.println(".........................................");
		BankContactDetails contactDetails;
		// BankMaster bankMaster;
		Zone zone;

		// Responsible to add active objects from contact details
		for (BankMasterContactDetails element : lstBankMasterContactDetails) {
			contactDetails = new BankContactDetails();
			zone = new Zone();
			zone.setZoneId(element.getZoneId());
			// contactDetails.setExZone(zone);
			// contactDetails.setRegion(element.getZone());
			contactDetails.setContactPerson(element.getContactPerson());
			contactDetails.setContactDept(element.getContactDep());
			contactDetails.setContactDesg(element.getContactDesignation());
			contactDetails.setContactPersonAr(element.getLocalContactPerson());
			contactDetails
					.setContactDeptAr(element.getLocalContactDepartment());
			contactDetails.setContactDesgAr(element
					.getLocalContactDesignation());
			contactDetails.setMobile(element.getMobile());
			contactDetails.setRecordStatus(Constants.Yes);

			contactDetails.setExBankMaster(bankMaster);
			// if condition Going to update, else section going to insert
			/*
			 * if(element.getInternalContactId().intValue() != 0) {
			 * contactDetails.setBankContactId(element.getInternalContactId());
			 * contactDetails.setModifier("Tanumoy");
			 * contactDetails.setUpdateDate(new Date()); } else {
			 * contactDetails.setCreateDate(new Date());
			 * contactDetails.setCreator("Tanumoy"); }
			 */
			contactDetails.setCreateDate(new Date());
			contactDetails.setCreator("Tanumoy");
			contactDetails.setBankContactId(contactDetails.getBankContactId());
			getBankMasterContactDetailsService().saveBankMasterContactDetails(
					contactDetails);
			/*
			 * After Add we need to store the primary key, unless this if user
			 * press add multiple time, it will insert multiple times in
			 * database, but if we save primary key, it will update
			 */
			// element.setInternalContactId(contactDetails.getBankContactId());
		}

		/*
		 * //Responsible to add inactive objects from contact details for
		 * (BankMasterContactDetails element :
		 * lstBankMasterDeletedContactDetails) { contactDetails = new
		 * BankContactDetails(); // contactDetails.setRegion(element.getZone());
		 * zone = new Zone(); zone.setZoneId(element.getZoneId());
		 * contactDetails.setExZone(zone);
		 * contactDetails.setContactPerson(element.getContactPerson());
		 * contactDetails.setContactDept(element.getContactDep());
		 * contactDetails.setContactDesg(element.getContactDesignation());
		 * contactDetails.setContactPersonAr(element.getLocalContactPerson());
		 * contactDetails.setContactDeptAr(element.getLocalContactDepartment());
		 * contactDetails
		 * .setContactDesgAr(element.getLocalContactDesignation());
		 * contactDetails.setMobile(element.getMobile());
		 * contactDetails.setRecordStatus("0");
		 * 
		 * bankMaster = new BankMaster();
		 * bankMaster.setBankId(element.getBankId());
		 * 
		 * //if condition Going to update, else section going to insert
		 * if(element.getInternalContactId().intValue() != 0) {
		 * contactDetails.setBankContactId(element.getInternalContactId());
		 * contactDetails.setModifier("Tanumoy");
		 * contactDetails.setUpdateDate(new Date()); } else {
		 * contactDetails.setCreateDate(new Date());
		 * contactDetails.setCreator("Tanumoy"); }
		 * 
		 * contactDetails.setExBankMaster(bankMaster);
		 * getBankMasterContactDetailsService
		 * ().saveBankMasterContactDetails(contactDetails); }
		 */

	}

	/**
	 * This method is responsible to clear the page
	 */
	public void clearForContactDetails() {
		setContactBankId(null);
		setBankName("");
		setContactZone(null);
		setContactPersonForContactDetails("");
		setContactDep("");
		setContactDesignation("");
		setContactMobile("");
		setLocalContactPersonForContactDetails("");
		setLocalContactDepartment("");
		setLocalContactDesignation("");
		setContactPerson(null);
	}

	/**
	 * This method is responsible when cancel button is pressed
	 */
	public void cancelForContactDetails() {
		try {
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect("../login/login.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is calling from Go button of Dialogue Box, Box will appear
	 * after clicking Add button(for datatable ADD)
	 */
	/*
	 * public void goFromPopUp() { addBankMasterContactList(); }
	 */

	/**
	 * This method will call from cancel button of Dialogue box, Box will appear
	 * after clicking Cancel button (for datatable ADD)
	 */
	public void cancelFromPopUp() {
		clearForContactDetails();
	}

	public void contactDetailsPageNavigation() {
		clearForContactDetails();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/bankcontactdetails.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void editRecord(BankMasterContactDetails contactDetails) {

		setContactBankId(contactDetails.getBankId());
		setContactZone(contactDetails.getZoneId());
		setContactPersonForContactDetails(contactDetails.getContactPerson());
		setContactDep(contactDetails.getContactDep());
		setContactDesignation(contactDetails.getContactDesignation());
		setContactMobile(contactDetails.getMobile());
		setLocalContactPersonForContactDetails(contactDetails
				.getLocalContactPerson());
		setLocalContactDepartment(contactDetails.getLocalContactDepartment());
		setLocalContactDesignation(contactDetails.getLocalContactDesignation());

		lstBankMasterContactDetails.remove(contactDetails);

		if (lstBankMasterContactDetails.size() <= 0) {
			setBooRenderDataTable(false);
			setBooRenderSavePanel(false);
		}

	}

	public void clickOnOKSave() throws IOException {
		clearForContactDetails();
		clear();
		lstBankMasterContactDetails.clear();
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("bankcontactdetails.xhtml");
	}

	public void addRecordsToDataTable() {
		BankMasterContactDetails bankMasterContactDetails = new BankMasterContactDetails();
		bankMasterContactDetails.setBank(getContactBankCode());
		bankMasterContactDetails.setZoneName(zoneInfo.get(getContactZone()));
		bankMasterContactDetails.setZoneId(getContactZone());
		bankMasterContactDetails
				.setContactPerson(getContactPersonForContactDetails());
		bankMasterContactDetails.setContactDep(getContactDep());
		bankMasterContactDetails.setContactDesignation(getContactDesignation());
		bankMasterContactDetails.setMobile(getContactMobile());
		bankMasterContactDetails
				.setLocalContactPerson(getLocalContactPersonForContactDetails());
		bankMasterContactDetails
				.setLocalContactDesignation(getLocalContactDesignation());
		bankMasterContactDetails
				.setLocalContactDepartment(getLocalContactDepartment());
		lstBankMasterContactDetails.add(bankMasterContactDetails);

		setBooRenderDataTable(true);
		setBooRenderSavePanel(true);
		clearForContactDetails();
	}

	private Boolean booRenderContactDeatailsPanel = false;
	private Boolean booRenderBankMasterPanel = true;
	private String contactBankCode;

	public String getContactBankCode() {
		return contactBankCode;
	}

	public void setContactBankCode(String contactBankCode) {
		this.contactBankCode = contactBankCode;
	}

	public Boolean getBooRenderBankMasterPanel() {
		return booRenderBankMasterPanel;
	}

	public void setBooRenderBankMasterPanel(Boolean booRenderBankMasterPanel) {
		this.booRenderBankMasterPanel = booRenderBankMasterPanel;
	}

	public Boolean getBooRenderContactDeatailsPanel() {
		return booRenderContactDeatailsPanel;
	}

	public void setBooRenderContactDeatailsPanel(
			Boolean booRenderContactDeatailsPanel) {
		this.booRenderContactDeatailsPanel = booRenderContactDeatailsPanel;
	}

	public void nextToContactDetailsPanel() {
		setBooRenderBankMasterPanel(false);
		setBooRenderContactDeatailsPanel(true);
		setContactBankCode(getBankCode());
	}

	public void backToBankMasterPanel() {
		setBooRenderBankMasterPanel(true);
		setBooRenderContactDeatailsPanel(false);
	}

	private Boolean booRenderSavePanel = false;
	private Boolean booRenderDataTable = false;

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public Boolean getBooRenderSavePanel() {
		return booRenderSavePanel;
	}

	public void setBooRenderSavePanel(Boolean booRenderSavePanel) {
		this.booRenderSavePanel = booRenderSavePanel;
	}

	public List<String> autoCompleteBankCode(String query) {
		System.out
				.println("entering in to autoCompleteBankCode KKKKKKKKKKKKKKKKK");
		List<String> results = new ArrayList<String>();
		List<BankMaster> bankMaster = iGeneralService
				.getAllBankCodeFromBankMaster(query);
		if (bankMaster.size() != 0) {
			for (BankMaster bank : bankMaster) {
				results.add(bank.getBankCode());
			}
			return results;
		} else {
			return null;
		}

	}

	// Bank Contact Details Combing ---RAHAMATHLALI SHAIK
	
	
	/*  Below code added by VISWA@@ */
	
	
	public void bakApprovalNavigation() {
			try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/bankapproval1.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

}
