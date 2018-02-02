package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.bankBranchUpload.service.BankBranchUploadService;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IBankLengthService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankContactDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Zone;
import com.amg.exchange.treasury.model.ZoneMasterDesc;
import com.amg.exchange.treasury.service.IBankApprovalService;
import com.amg.exchange.treasury.service.IBankMasterContactDetailsService;
import com.amg.exchange.treasury.service.IBankMasterService;
import com.amg.exchange.treasury.service.IZoneMasterService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("bankMaster")
@Scope("session")
public class BankMasterInfoBean<T> implements Serializable {

	/**
	 * @author 
	 */
	private static final long serialVersionUID = -1333138778448003609L;

	Logger log=Logger.getLogger(BankMasterInfoBean.class);
	private SessionStateManage session = new SessionStateManage();
	Constants constant = new Constants();

	/**
	 * Constructor
	 */
	public BankMasterInfoBean() {
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
	private String allowNoBank=null;
	private String warningMessage;
	private String bankBranchCheck;

	private Boolean booBankGenInfo = true;
	private Boolean booBankAddress = false;
	private Boolean booLocalPanelRender = false;
	private Boolean booFileAcceptance = false;
	private Boolean renderBankLengthPanel=false;

	private List<StateMasterDesc> lstStateList = new ArrayList<StateMasterDesc>();
	private List<DistrictMasterDesc> lstDistrictList = new ArrayList<DistrictMasterDesc>();
	private List<CityMasterDesc> lstCityList = new ArrayList<CityMasterDesc>();
	private List<BankMaster> lstDeActivedRecord = new ArrayList<BankMaster>();

	private List<CurrencyMaster> lstCountryCurrencyList = new ArrayList<CurrencyMaster>();
	private HashMap<BigDecimal, String> mapCountryName = new HashMap<BigDecimal, String>();

	private String createdBy;
	private Date createdDate;
	private String cretedByForContc;
	private Date createdDateForContc;
	
	private List<CountryMasterDesc> allCountryList = null;
	private List<BankMaster> bankBranchList = null;
	private BigDecimal bankBranch;
	
	@Autowired
	BankBranchUploadService bankBranchUploadService;

	@Autowired
	IZoneMasterService zoneMasterService;



	public String getWarningMessage() {
		return warningMessage;
	}
	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}
	public Boolean getRenderBankLengthPanel() {
		return renderBankLengthPanel;
	}
	public void setRenderBankLengthPanel(Boolean renderBankLengthPanel) {
		this.renderBankLengthPanel = renderBankLengthPanel;
	}
	public List<BankMaster> getLstDeActivedRecord() {
		return lstDeActivedRecord;
	}
	public void setLstDeActivedRecord(List<BankMaster> lstDeActivedRecord) {
		this.lstDeActivedRecord = lstDeActivedRecord;
	}
	public String getCretedByForContc() {
		return cretedByForContc;
	}
	public void setCretedByForContc(String cretedByForContc) {
		this.cretedByForContc = cretedByForContc;
	}
	public Date getCreatedDateForContc() {
		return createdDateForContc;
	}
	public void setCreatedDateForContc(Date createdDateForContc) {
		this.createdDateForContc = createdDateForContc;
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
	
	public String getBankBranchCheck() {
		return bankBranchCheck;
	}
	public void setBankBranchCheck(String bankBranchCheck) {
		this.bankBranchCheck = bankBranchCheck;
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
	public void setBankMasterInfoService(IBankMasterService<T> bankMasterInfoService) {
		this.bankMasterInfoService = bankMasterInfoService;
	}

	public List<CurrencyMaster> getLstCountryCurrencyList() {
		return lstCountryCurrencyList;
	}
	public void setLstCountryCurrencyList(
			List<CurrencyMaster> lstCountryCurrencyList) {
		this.lstCountryCurrencyList = lstCountryCurrencyList;
	}
	
	public HashMap<BigDecimal, String> getMapCountryName() {
		return mapCountryName;
	}
	public void setMapCountryName(HashMap<BigDecimal, String> mapCountryName) {
		this.mapCountryName = mapCountryName;
	}	

	public List<CountryMasterDesc> getAllCountryList() {
		return allCountryList;
	}
	public void setAllCountryList(List<CountryMasterDesc> allCountryList) {
		this.allCountryList = allCountryList;
	}	

	public List<BankMaster> getBankBranchList() {
		return bankBranchList;
	}
	public void setBankBranchList(List<BankMaster> bankBranchList) {
		this.bankBranchList = bankBranchList;
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

	/**
	 * Responsible to populate Country
	 * @return
	 */
	SessionStateManage sessionStateManage = new SessionStateManage();
	public List<CountryMasterDesc> getCountryNameList() {
		List<CountryMasterDesc> lstCountry =null;

		try{

			//SessionStateManage sessionStateManage = new SessionStateManage();
			lstCountry = getiGeneralService().getCountryList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):""+1));

		}catch (NullPointerException e) {
			setWarningMessage("Method Name: getCountryNameList");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}
		return lstCountry;
	}

	/**
	 * Responsible to prepare stateList by country change
	 * @param event
	 */
	public void generateStateList(AjaxBehaviorEvent event) {
		try{

			//SessionStateManage sessionStateManage = new SessionStateManage();
			List<StateMasterDesc> stateMaster =getiGeneralService().getStateList(
					new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):""+1), getCountryId()); 
			if(stateMaster.size()>0){
				setLstStateList(stateMaster);
			}
			if(getCountryId()!= null && getCountryId().intValue() != sessionStateManage.getCountryId().intValue()) {
				setBooLocalPanelRender(false);
			} else {
				setBooLocalPanelRender(true);
			}
			List<CurrencyMaster> lstCurrency=getCurrencyList();

			setLstCountryCurrencyList(lstCurrency);
			/*reset State, District, City, as country is changing*/ 
			setCurrencyCode(null);
			setStateId(null);
			setDistrictId(null);
			setCityId(null);

			setLstDistrictList(null);
			setLstCityList(null);
		}catch (NullPointerException e) {
			setWarningMessage("Method Name: generateStateList");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}


	}

	/**
	 * Responsible to populate District depending upon state selection
	 * @param event
	 */
	public void generateDistrictList(AjaxBehaviorEvent event) {
		//SessionStateManage sessionStateManage = new SessionStateManage();
		try{
			List<DistrictMasterDesc> lstDistrict = getiGeneralService().getDistrictList(
					new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):""+1), getCountryId(), getStateId()
					);
			if(lstDistrict.size()>0){
				setLstDistrictList(lstDistrict);
			}
			/*reset District, City, as state is changing*/ 
			setDistrictId(null);
			setCityId(null);
		}catch (NullPointerException e) {
			setWarningMessage("Method Name: generateDistrictList");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}

	}

	/**
	 * Responsible to populate city depending upon district selection
	 * @param event
	 */
	public void generateCityList(AjaxBehaviorEvent event) {
		try{
			//SessionStateManage sessionStateManage = new SessionStateManage();
			List<CityMasterDesc> lstCity = getiGeneralService().getCityList(
					new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):""+1), 
					getCountryId(),	getStateId(),	getDistrictId()
					); 
			if(lstCity.size()>0){
				setLstCityList(lstCity);
			}
		}catch (NullPointerException e) {
			setWarningMessage("Method Name: generateCityList");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}


	}

	/**
	 * This method will call when city change happen
	 * It's a do-nothing method, except this after click back button,  selected cityId will not bind with bean  
	 * @param event
	 */
	public void getCity(AjaxBehaviorEvent event) {}

	/**
	 * This method is responsible to populate State depending upon country selection
	 */
	private void populateState() {

		//SessionStateManage sessionStateManage = new SessionStateManage();
		List<StateMasterDesc> stateMaster =getiGeneralService().getStateList(
				new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):""+1), getCountryId()); 
		if(stateMaster.size()>0){
			setLstStateList(stateMaster);
		}
	}

	/**
	 * This method is responsible to populate State depending upon country selection
	 */
	private void populateCurrency() {

		//SessionStateManage sessionStateManage = new SessionStateManage();
		List<CurrencyMaster> lstCountryCurrencyList =getiGeneralService().getCountryCurrencyList(getCountryId());
		if(lstCountryCurrencyList.size()>0){		
			setLstCountryCurrencyList(lstCountryCurrencyList);
		}
	}

	/**
	 * This method is responsible to populate district depending upon state election 
	 */
	private void populateDistrict() {
		//SessionStateManage sessionStateManage = new SessionStateManage();
		List<DistrictMasterDesc> lstDistrict = getiGeneralService().getDistrictList(
				new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):""+1), getCountryId(), getStateId());
		if(lstDistrict.size()>0){
			setLstDistrictList(lstDistrict);
		}
	}

	/**
	 * This method is responsible to populate City depending upon district selection 
	 */
	private void populateCity() {
		//SessionStateManage sessionStateManage = new SessionStateManage();
		List<CityMasterDesc> lstCity = getiGeneralService().getCityList(
				new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):""+1), 
				getCountryId(),	getStateId(),	getDistrictId()
				); 
		if(lstCity.size()>0){
			setLstCityList(lstCity);
		}
	}

	/**
	 * method is responsible to cancel the Bank Master
	 * @return
	 */
	public void cancel() {
		try{
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
			context.redirect("../login/login.xhtml");
		} catch(Exception e) {
			log.info("Redirection Problem to Login by Cancel button");
		}
	}

	/**
	 * Responsible to save Bank Master details 
	 * @return
	 */
	public void save() throws Exception{

		//try{
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

		if (getIFSCLength() != null && getIFSCLength().trim().length() != 0) {
			bankMaster.setIfscLen(new BigDecimal(getIFSCLength()));
		} /*else {
			bankMaster.setIfscLen(new BigDecimal("0"));
		}*/


		/*if(getIFSCLength()!=null){
			bankMaster.setIfscLen(new BigDecimal(getIFSCLength()=="" ? "0": getIFSCLength()));
			}*/
		bankMaster.setReutersBankName(getReutersBankNname() );
		bankMaster.setFileAlls(getFileSpecificOrAll());
		bankMaster.setFileBranch(getFileBranchWiseYOrN());
		//bankMaster.setMicrCode(getMICRReuters());
		bankMaster.setFileRemitMode(getRemmiterModeYOrN());

		CountryMaster countryMaster =  new CountryMaster();
		countryMaster.setCountryId(getCountryId());
		bankMaster.setFsCountryMaster(countryMaster);
		bankMaster.setAllowNoBank(getAllowNoBank());
		
		bankMaster.setBankBranchCheck(getBankBranchCheck());
		/*StateMaster statemaster = new StateMaster();
			statemaster.setStateId(getStateId());
			bankMaster.setFsStateMaster(statemaster);
		 */
		/*DistrictMaster districtMaster = new DistrictMaster();
			districtMaster.setDistrictId(getDistrictId());
			bankMaster.setFsDistrictMaster(districtMaster);*/

		/*	CityMaster cityMaster = new CityMaster();
			cityMaster.setCityId(getCityId());
			bankMaster.setFsCityMaster(cityMaster);*/

		/*CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(getCurrencyId());
			bankMaster.setCurrencyMaster(currencyMaster);*/

		//TODO hard code user name   

		bankMaster.setRecordStatus(Constants.U);
		/*It will return true always, except the very first time*/  
		if(getBankIdInternal() != null){
			bankMaster.setBankId(getBankIdInternal());
			bankMaster.setUpdateDate(new Date());
			bankMaster.setModifier(sessionStateManage.getUserName());
			bankMaster.setCreator(getCreatedBy());
			bankMaster.setCreateDate(getCreatedDate());
		}else{
			bankMaster.setCreator(sessionStateManage.getUserName());
			bankMaster.setCreateDate(new Date());
		}
		bankMaster.setApprovedBy(null);
		bankMaster.setApprovedDate(null);
		bankMaster.setRemarks(null);

		//getBankMasterInfoService().saveBankMasterInfo(bankMaster);

		/*setting primary key*/

		//saveForContactDetails(bankMaster);

		//saveBankContact(bankMaster);
		HashMap<String, Object> saveMap = new HashMap<String, Object> ();

		saveMap.put("BankMasterMap", bankMaster);

		List<BankContactDetails> lstBankContactDetails = saveContact(bankMaster);

		saveMap.put("BankContactDetails", lstBankContactDetails);

		List<BankAccountLength> lstBankAccountLength=saveBankLength(bankMaster);

		saveMap.put("BankAccountLength", lstBankAccountLength);

		getBankMasterInfoService().saveAll(saveMap);

		setBankIdInternal(bankMaster.getBankId());
		/*	clear();
			try{
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
				context.redirect("../treasury/bankcontactdetails.xhtml");
			} catch(Exception e) {
				log.info("Problem to redirect:"+e);
			}*/

		/*}catch(Exception e){
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}*/
	}

	/**
	 * Responsible to clear all component of Bank Master
	 * @return
	 */
	public void clear() {
		setAllowNoBank(null);
		setBankBranchCheck(null);
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
		setCreatedBy(null);
		setCreatedDate(null);
		setCreatedDateForContc(null);
		setCretedByForContc(null);
		setBooBankApprovalRender(false);

		setBankIdInternal(null);
		setCurrencyId(null);
		setBankLength(null);
		lstBankMasterContactDetails.clear();
		bankLengthList.clear();
		setBankLengthPanelRendered(false);
		setBoorender(false);
		listContact.clear();
	}

	/**
	 * Fetch data depending upon given bank Code
	 */
	public void fetchBankMasterInfo(AjaxBehaviorEvent event) {

		try{

			//List<BankMaster> lstBankMasterInfo = new ArrayList<BankMaster>();
			List<BankMaster> checkList = new ArrayList<BankMaster>();
			/*Checking that BankCode field empty or not*/ 
			if(getBankCode() != null && getBankCode().length() > 0){

				String[] s = getBankCode().split("-");
				System.out.println("Bank Code : "+s[0]);
				/*System.out.println("Desc : "+s[1]);*/
				if(s[0] != null){
					setBankCode(s[0].trim());
				}

				checkList = getBankMasterInfoService().getAllBankMasterInfo(getBankCode());
				//lstBankMasterInfo = getBankMasterInfoService().getBankMasterInfo(getBankCode());

				if(checkList != null && checkList.size() > 0) {
					if(checkList.get(0).getRecordStatus()!=null && checkList.get(0).getRecordStatus().equalsIgnoreCase(Constants.Yes)){
						fetchedRecordsSetting(checkList);
						/*	clear();
							RequestContext.getCurrentInstance().execute("alreadyApproved.show();");*/
					}else if(checkList.get(0).getRecordStatus()!=null && checkList.get(0).getRecordStatus().equalsIgnoreCase(Constants.U)){
						fetchedRecordsSetting(checkList);
					}else{
						setLstDeActivedRecord(checkList);
						RequestContext.getCurrentInstance().execute("deactive.show();");
					}
				}else {
					String bankCode = getBankCode();
					clear();
					setBankCode(bankCode);
				}

			}

		}catch (NullPointerException e) {
			setWarningMessage("Method Name: fetchBankMasterInfo");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}


	}


	public void fetchedRecordsSetting(List<BankMaster> lstbankdetails) throws Exception{

		List<BankMaster> lstBankMasterInfo = (List<BankMaster>) lstbankdetails;

		/*setting primary key*/ 
		setBankIdInternal(lstBankMasterInfo.get(0).getBankId());
		/*setBankCode(lstBankMasterInfo.get(0).getBankCode());*/
		setFullName(lstBankMasterInfo.get(0).getBankFullName());
		setShortName(lstBankMasterInfo.get(0).getBankShortName());
		setLanguageBank(lstBankMasterInfo.get(0).getLanguageInd());
		setAddress1(lstBankMasterInfo.get(0).getAddress1());
		setAddress2(lstBankMasterInfo.get(0).getAddress2());
		setCountryId(lstBankMasterInfo.get(0).getFsCountryMaster().getCountryId());
		//	setCurrencyId(lstBankMasterInfo.get(0).getCurrencyMaster().getCurrencyId());
		/*Now we need to populate state depending upon country(saved), otherwise state will not set with the state previously saved state*/
		//populateState(); 
		//	setStateId(lstBankMasterInfo.get(0).getFsStateMaster().getStateId());
		/*Now we need to populate District depending upon state(saved), otherwise district will not set with the district previously saved district*/
		//	populateDistrict();
		//setDistrictId(lstBankMasterInfo.get(0).getFsDistrictMaster().getDistrictId());
		/*Now we need to populate City depending upon city(saved), otherwise city will not set with the city previously saved city*/
		//	populateCity();
		//	populateCurrency();
		//	setCityId(lstBankMasterInfo.get(0).getFsCityMaster().getCityId());
		setZip(lstBankMasterInfo.get(0).getZipCode());
		setTelephone(lstBankMasterInfo.get(0).getTeleponeNo());
		setFax(lstBankMasterInfo.get(0).getFaxNo());
		setEmail(lstBankMasterInfo.get(0).getEmail());
		setLocalFullName(lstBankMasterInfo.get(0).getBankFullNameAr());
		setLocalShortname(lstBankMasterInfo.get(0).getBankShortNameAr());
		setLocalAddress1(lstBankMasterInfo.get(0).getAddress1Ar());
		setLocalAddress2(lstBankMasterInfo.get(0).getAddress2Ar());
		if(lstBankMasterInfo.get(0).getIfscLen()!=null){
			setIFSCLength(lstBankMasterInfo.get(0).getIfscLen().toPlainString());
		}
		setReutersBankNname(lstBankMasterInfo.get(0).getReutersBankName());
		setFileSpecificOrAll(lstBankMasterInfo.get(0).getFileAlls());
		setFileBranchWiseYOrN(lstBankMasterInfo.get(0).getFileBranch());
		//setMICRReuters(lstBankMasterInfo.get(0).getMicrCode());
		setRemmiterModeYOrN(lstBankMasterInfo.get(0).getFileRemitMode()); 
		setAllowNoBank(lstBankMasterInfo.get(0).getAllowNoBank());
		setBankBranchCheck(lstBankMasterInfo.get(0).getBankBranchCheck());
		setCreatedBy(lstBankMasterInfo.get(0).getCreator());
		setCreatedDate(lstBankMasterInfo.get(0).getCreateDate());
		//setCreatedDate(createdDate);

		getZoneLists();
		fetchDBContactList();
		fetchBankLength();
		if(lstBankMasterContactDetails.size()>0 && getBankIdInternal()!=null){
			setBooRenderDataTable(true);
			setBooRenderSavePanel(true);
		}
		setBooBankGenInfo(true);
		setBooBankAddress(false);
		setBooFileAcceptance(false);

	}

	public void deactiveYes()
	{
		List<BankMaster> lstdeactive = getLstDeActivedRecord();
		try {
			fetchedRecordsSetting(lstdeactive);
		}catch (NullPointerException e) {
			setWarningMessage("Method Name: deactiveYes");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}
	}

	public void deactiveNo()
	{
		clear();
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
		//System.out.println("Welcome Back");
	}

	/**
	 * This method will call when Log Me Out button will press from confirm dialogue box
	 * @throws IOException
	 */
	public void logoutListener() throws IOException{
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.invalidateSession();
		//System.out.println(ec.getRequestContextPath()) ....This will print the  "/AlmullaExchange"...Context Name..That's why we store External context in 'ec' variable 
		ec.redirect(ec.getRequestContextPath() + "/index.jsp");
	}

	/**
	 * When Next click from first(bankInfoPanel) panel
	 */
	public void bankInfoPanelNext() {
		setBooBankGenInfo(false);
		setBooBankAddress(true);
		setBooFileAcceptance(false);

		/*First time we have to make this local panel invisible, at that time Id will be null*/
		if(getCountryId() == null) {
			setBooLocalPanelRender(false);
		} else if(getCountryId()!= null && getCountryId().intValue() != sessionStateManage.getCountryId().intValue()) {
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
		List<CurrencyMaster> lstCurrency =getiGeneralService().getCountryCurrencyList(getCountryId());

		return lstCurrency;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;

	public void bankMasterPageNavigation(){
		setApproval(false);
		clear();
		clearForContactDetails();
		setRenderBankLengthPanel(true);
		lstBankMasterContactDetails.clear();
		bankLengthList.clear();
		/*setbooBankAddress = false;
		setbooLocalPanelRender = false;
		setbooFileAcceptance = false;*/
		setBooRenderBankMasterPanel(true);
		setBooRenderContactDeatailsPanel(false);
		setBankLengthRendered(true);
		setBankLangthRendered(true);
		listContact.clear();
		clearContactDetails();
		setBankLengthId(null);
		setBooEditButton(false);
		/*if(isApproval()==true){
			setBankLengthPanelRendered(true);
			setNextButtonRendered(true);
		}else{
			setBankLengthPanelRendered(false);
			setNextButtonRendered(false);
		}*/
		loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankmaster.xhtml");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankmaster.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*ended 31/01/2015 added @koti*/


	// Bank contact details Combing ----- RAHAMATHALI SHAIK


	private BigDecimal contactIdInternal= null;
	private BigDecimal contactBankId = null;
	private String bankName=null;
	private BigDecimal contactZone = null;
	private String contactPersonForContactDetails = null;
	private String contactDep = null;
	private String contactDesignation = null;
	private String contactMobile = null;
	private String localContactPersonForContactDetails = null;
	private String localContactDepartment = null;
	private String localContactDesignation = null;
	private BigDecimal zoneId= null;
	private String ZoneName =null;
	private Map<BigDecimal, String> bankInfo = new HashMap<BigDecimal, String>(); 
	private Map<BigDecimal, String> zoneInfo = new HashMap<BigDecimal, String>(); 
	private List<BankMasterContactDetails> lstBankMasterContactDetails = new ArrayList<BankMasterContactDetails>();
	private List<BankMasterContactDetails> lstBankMasterDeletedContactDetails = new ArrayList<BankMasterContactDetails>();
	private List<BankContactDetails> contactDetails = new ArrayList<BankContactDetails>();
	private List<Zone> lstZoneLists = new ArrayList<Zone>() ;
	private List<ZoneMasterDesc> zoneMasterDescs= new ArrayList<ZoneMasterDesc>();

	private Boolean booLocal = true;

	public List<ZoneMasterDesc> getZoneMasterDescs() {
		return zoneMasterDescs;
	}
	public void setZoneMasterDescs(List<ZoneMasterDesc> zoneMasterDescs) {
		this.zoneMasterDescs = zoneMasterDescs;
	}
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
	public List<ZoneMasterDesc> getZoneLists()throws Exception {
		SessionStateManage sessionStateManage = new SessionStateManage();
		zoneMasterDescs = zoneMasterService.getZoneMasterList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1));
		if(zoneMasterDescs.size()>0){
			for(ZoneMasterDesc ZoneMasterDesc : zoneMasterDescs){
				zoneInfo.put(ZoneMasterDesc.getZone().getZoneId(), ZoneMasterDesc.getZoneDescription());
			}
		}
		return zoneMasterDescs;
	}

	public void duplicateCheckingZone(){
		if(listContact.size()>0){
			for (ContactDataTable contactData : listContact) {
				if(contactData.getZoneId() != null && getContactZone() != null){
					if(contactData.getZoneId().equals(getContactZone())){
						RequestContext.getCurrentInstance().execute("alreadyExist.show();");
						setContactZone(null);
						return;
					}
				}
			}
		}
	}

	@Autowired
	IBankMasterContactDetailsService<T> bankMasterContactDetailsService; 
	public IBankMasterContactDetailsService<T> getBankMasterContactDetailsService() {
		return bankMasterContactDetailsService;
	}
	public void setBankMasterContactDetailsService(IBankMasterContactDetailsService<T> bankMasterContactDetailsService) {
		this.bankMasterContactDetailsService = bankMasterContactDetailsService;
	}

	/**
	 * Responsible to populate Bank name what is already saved from bank master page 
	 * @return
	 */
	public List<BankMaster> getBankDetails() {
		List<BankMaster> lstCountry = getBankMasterContactDetailsService().getBankMasterInfo();
		if(lstCountry.size()>0){
			for (BankMaster bankMaster : lstCountry) {
				bankInfo.put(bankMaster.getBankId(), bankMaster.getBankFullName());
			}
		}
		return lstCountry;
	}

	public List<ZoneMasterDesc> getZoneDetails(){
		List<ZoneMasterDesc> lstZone = getBankMasterContactDetailsService().getZoneList(new BigDecimal("1"));

		return lstZone;
	}

	/**
	 * This method is responsible to ass data in datatable
	 */
	/*	public void addBankMasterContactList() {
		System.out.println("getzone Id :"+getZoneId()  +"zoneInfo :"+zoneInfo.get(new BigDecimal("1")));
		BankMasterContactDetails contactDetails = new BankMasterContactDetails(bankInfo.get(getContactBankId()), getContactBankId(), zoneInfo.get(getZoneId()),getZoneId(), 
																														getContactPersonForContactDetails(),  getContactDep(), getContactDesignation(), 
																														getContactMobile(), getLocalContactPersonForContactDetails(), getLocalContactDepartment(), 
																														getLocalContactDesignation(), new BigDecimal(0));

		lstBankMasterContactDetails.add(contactDetails);
		clearForContactDetails();

	}*/

	/**
	 * This method is responsible to fetch data according to bank selection 
	 */


	public void fetchBankContactInfo() throws Exception{



		/*
		lstBankMasterContactDetails.clear();
		contactDetails = getBankMasterContactDetailsService().getbankContactInfo(getBankIdInternal());

			if(contactDetails!=null && contactDetails.size() > 0){
				for (BankContactDetails element : contactDetails) {
					BankMasterContactDetails  contactDetails = new BankMasterContactDetails();
					contactDetails.setInternalContactId(element.getBankContactId());//pk
					contactDetails.setBank(getBankCode());
					contactDetails.setBankId(element.getExBankMaster().getBankId());
					contactDetails.setZoneName(zoneInfo.get(element.getExZone().getZoneId()));
					contactDetails.setZoneId(element.getExZone().getZoneId());
					contactDetails.setContactPerson(element.getContactPerson());
					contactDetails.setContactDep(element.getContactDept());
					contactDetails.setContactDesignation(element.getContactDesg());
					contactDetails.setMobile(element.getMobile());
					contactDetails.setLocalContactPerson(element.getContactPersonAr());
					contactDetails.setLocalContactDesignation(element.getContactDesgAr());
					contactDetails.setLocalContactDepartment(element.getContactDeptAr());
					contactDetails.setCreatedBy(element.getCreator());
					contactDetails.setCreatedDate(element.getCreateDate());
					lstBankMasterContactDetails.add(contactDetails);
				}
			}

		 */




		List<BankContactDetails> contactDetails = bankMasterContactDetailsService.getbankContactInfo(getBankIdInternal());

		/*if(contactDetails.size()!=0){
			setBooRenderDataTable(true);
			lstBankMasterContactDetails.add(contactDetails);
		}else{
			setBooRenderDataTable(false);
		}*/

		setContactBankCode(getBankCode());
		if(contactDetails.size()!=0){
			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(getBankIdInternal());

			//contactDetails.setExBankMaster(bankMaster);

			setContactBankId(contactDetails.get(0).getBankContactId());
			//setContactZone(contactDetails.get(0).getcgetZoneId())
			//setBankId(getContactDetailsPk());
			//setZoneId(contactDetails.get(0).getExZone().getZoneId());
			//setZoneName(contactDetails.get(0).getExZone().getZoneName());
			setContactPersonForContactDetails(contactDetails.get(0).getContactPerson());
			setContactDep(contactDetails.get(0).getContactDept());
			setContactDesignation(contactDetails.get(0).getContactDesg());
			setContactMobile(contactDetails.get(0).getMobile());
			setLocalContactPersonForContactDetails(contactDetails.get(0).getContactPersonAr());
			setLocalContactDepartment(contactDetails.get(0).getContactDeptAr());
			setLocalContactDesignation(contactDetails.get(0).getContactDesgAr());
			setCreatedBy(contactDetails.get(0).getCreator());
			setCreatedDate(contactDetails.get(0).getCreateDate());

			//setContactDetailsPk(getInternalContactId());
		}	



	}


	/**
	 * This method is responsible to manage deleted objects
	 * @param contactDetails
	 */
	public void remove(BankMasterContactDetails bankMastercontactDetails){

		lstBankMasterContactDetails.remove(bankMastercontactDetails);
		if(bankMastercontactDetails.getInternalContactId()!=null){
			lstBankMasterContactDetails.add(bankMastercontactDetails);
			BankContactDetails contactDetails=null;
			for (BankMasterContactDetails element : lstBankMasterContactDetails) {
				contactDetails = new BankContactDetails();
				contactDetails.setBankContactId(element.getInternalContactId());
				Zone zone = new Zone();
				zone.setZoneId(element.getZoneId());
				contactDetails.setExZone(zone);
				contactDetails.setContactPerson(element.getContactPerson());
				contactDetails.setContactDept(element.getContactDep());
				contactDetails.setContactDesg(element.getContactDesignation());
				contactDetails.setContactPersonAr(element.getLocalContactPerson());
				contactDetails.setContactDeptAr(element.getLocalContactDepartment());
				contactDetails.setContactDesgAr(element.getLocalContactDesignation());
				contactDetails.setMobile(element.getMobile());
				contactDetails.setRecordStatus("N");
				BankMaster bankMaster=new BankMaster();
				bankMaster.setBankId(element.getBankId());
				contactDetails.setExBankMaster(bankMaster);
				contactDetails.setCreator(element.getCreatedBy());
				contactDetails.setCreateDate(element.getCreatedDate());
				contactDetails.setUpdateDate(new Date());
				contactDetails.setModifier(sessionStateManage.getUserName());
				getBankMasterContactDetailsService().saveBankMasterContactDetails(contactDetails);
			}
			lstBankMasterContactDetails.remove(bankMastercontactDetails);
		}

		if(lstBankMasterContactDetails.size()<=0){
			setBooRenderDataTable(false);
			setBooRenderSavePanel(false);
		}
	}

	/**
	 * get zone List
	 */
	/*public List<Zone> getZonesList(){

		zoneLists = getBankMasterContactDetailsService().getZoneList(new BigDecimal("1"));
		System.out.println("zoneList  :"+zoneLists.size());
		return zoneLists;
	}*/


	public void saveContactAndBankMaster(){

		try{

			List<BankMaster> checkList = new ArrayList<BankMaster>();
			checkList = getBankMasterInfoService().getAllBankMasterInfo(getBankCode());

			System.out.println("list size ==============="+checkList.size());

			if(checkList.size()!=0 && getBankIdInternal()==null){
				System.out.println("list size ===============");
				RequestContext.getCurrentInstance().execute("duplicatecode.show();");
			}else{	

				save();
				RequestContext.getCurrentInstance().execute("success.show();");

			}

		}catch (NullPointerException e) {
			setWarningMessage("Method Name: saveContactAndBankMaster");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e)
		{
			setWarningMessage(e.toString());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}


	}

	/**
	 * This method is responsible to save the data 
	 */
	public void saveForContactDetails(BankMaster bankMaster) {

		try{
			//Save BankMaster  Information

			System.out.println(".........................................");
			BankContactDetails contactDetails;
			//	BankMaster bankMaster;

			if(lstBankMasterContactDetails.size()!=0){
				for (BankMasterContactDetails element : lstBankMasterContactDetails) {
					contactDetails = new BankContactDetails();
					Zone zone = new Zone();
					zone.setZoneId(element.getZoneId());
					contactDetails.setExZone(zone);
					//	contactDetails.setExZone(zone);
					//	contactDetails.setRegion(element.getZone());
					contactDetails.setContactPerson(element.getContactPerson());
					contactDetails.setContactDept(element.getContactDep());
					contactDetails.setContactDesg(element.getContactDesignation());
					contactDetails.setContactPersonAr(element.getLocalContactPerson());
					contactDetails.setContactDeptAr(element.getLocalContactDepartment());
					contactDetails.setContactDesgAr(element.getLocalContactDesignation());
					contactDetails.setMobile(element.getMobile());
					contactDetails.setRecordStatus(constant.U);

					contactDetails.setExBankMaster(bankMaster);
					//if condition Going to update, else section going to insert
					if(element.getInternalContactId()!=null) {
						contactDetails.setBankContactId(element.getInternalContactId());
						contactDetails.setModifier(sessionStateManage.getUserName());
						contactDetails.setUpdateDate(new Date());
						contactDetails.setCreateDate(element.getCreatedDate());
						contactDetails.setCreator(element.getCreatedBy());
					} else {
						contactDetails.setCreateDate(element.getCreatedDate());
						contactDetails.setCreator(element.getCreatedBy());
					}
					if(isApproval()){
						contactDetails.setRecordStatus(Constants.Yes);
						contactDetails.setApprovedBy(sessionStateManage.getUserName());
						contactDetails.setApprovedDate(new Date());
					}
					getBankMasterContactDetailsService().saveBankMasterContactDetails(contactDetails);
				}
			}

			//Responsible to add active objects from contact details

			clearForContactDetails();

		}catch (NullPointerException e) {
			setWarningMessage("Method Name: saveForContactDetails");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}


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
	 * @throws IOException 
	 */
	public void cancelForContactDetails() throws IOException {
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	/**
	 * This method is calling from Go button of Dialogue Box, Box will appear after clicking Add button(for datatable ADD)    
	 */
	/*	public void goFromPopUp() {
		addBankMasterContactList();
	}*/

	/**
	 * This method will call from cancel button of Dialogue box, Box will appear after clicking Cancel button (for datatable ADD)
	 */
	public void cancelFromPopUp() {
		clearForContactDetails();
	}

	public void contactDetailsPageNavigation(){
		clearForContactDetails();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankcontactdetails.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private BigDecimal contactDetailsPk;


	public BigDecimal getContactDetailsPk() {
		return contactDetailsPk;
	}
	public void setContactDetailsPk(BigDecimal contactDetailsPk) {
		this.contactDetailsPk = contactDetailsPk;
	}
	public void editRecord(BankMasterContactDetails contactDetails){

		setContactBankCode(contactDetails.getBank());
		setContactBankId(contactDetails.getBankId());
		setContactZone(contactDetails.getZoneId());
		setContactPersonForContactDetails(contactDetails.getContactPerson());
		setContactDep(contactDetails.getContactDep());
		setContactDesignation(contactDetails.getContactDesignation());
		setContactMobile(contactDetails.getMobile());
		setLocalContactPersonForContactDetails(contactDetails.getLocalContactPerson());
		setLocalContactDepartment(contactDetails.getLocalContactDepartment());
		setLocalContactDesignation(contactDetails.getLocalContactDesignation());
		setContactDetailsPk(contactDetails.getInternalContactId());
		setCreatedDateForContc(contactDetails.getCreatedDate());
		setCretedByForContc(contactDetails.getCreatedBy());
		lstBankMasterContactDetails.remove(contactDetails);

		if(lstBankMasterContactDetails.size()<=0){
			setBooRenderDataTable(false);
			setBooRenderSavePanel(false);
		}

	}

	public void clickOnOKSave() throws IOException{
		lstBankMasterContactDetails.clear();
		setBooRenderBankMasterPanel(true);
		setRenderBankLengthPanel(true);
		setBooRenderContactDeatailsPanel(false);
		setBankIdInternal(null);
		setContactDetailsPk(null);
		listContact.clear();
		clearContactDetails();
		try{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch(Exception e) {
			System.out.println(e);
		}

	}


	public void addRecordsToDataTable(){

		try{

			BankMasterContactDetails bankMasterContactDetails=new BankMasterContactDetails();
			bankMasterContactDetails.setBank(getContactBankCode());
			bankMasterContactDetails.setZoneName(zoneInfo.get(getContactZone()));
			bankMasterContactDetails.setZoneId(getContactZone());
			bankMasterContactDetails.setContactPerson(getContactPersonForContactDetails());
			bankMasterContactDetails.setContactDep(getContactDep());
			bankMasterContactDetails.setContactDesignation(getContactDesignation());
			bankMasterContactDetails.setMobile(getContactMobile());
			bankMasterContactDetails.setLocalContactPerson(getLocalContactPersonForContactDetails());
			bankMasterContactDetails.setLocalContactDesignation(getLocalContactDesignation());
			bankMasterContactDetails.setLocalContactDepartment(getLocalContactDepartment());



			if(getContactDetailsPk()!=null){
				bankMasterContactDetails.setUpdatedBy(sessionStateManage.getUserName());
				bankMasterContactDetails.setUpdatedDate(new Date());
				bankMasterContactDetails.setInternalContactId(getContactDetailsPk());
				bankMasterContactDetails.setCreatedBy(getCretedByForContc());
				bankMasterContactDetails.setCreatedDate(getCreatedDateForContc());
			}else{
				bankMasterContactDetails.setCreatedBy(sessionStateManage.getUserName());
				bankMasterContactDetails.setCreatedDate(new Date());
			}
			lstBankMasterContactDetails.add(bankMasterContactDetails);

			setBooRenderDataTable(true);
			setBooRenderSavePanel(true);
			clearForContactDetails();
		}catch (NullPointerException e) {
			setWarningMessage("Method Name: addRecordsToDataTable");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}

	}

	private Boolean booRenderContactDeatailsPanel=false;
	private Boolean booRenderBankMasterPanel=true;
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

	public void fetchContactLstFromDB(){
		setRenderBankLengthPanel(false);
		setBooRenderBankMasterPanel(false);
		setBankLengthPanelRendered(false);
		setBooRenderContactDeatailsPanel(true);
		fetchDBContactList();
	}


	//CR Commented due to not Understanding
	public void nextToContactDetailsPanel(){

		if(getBankLength()!=null){
			RequestContext.getCurrentInstance().execute("lengthclear.show();");
			//setBankLength(null);
		}else{

			if(listContact.size()>0)
			{
				setBoorender(true);
			} else {
				setBoorender(false);	
			}
			//		clearForContactDetails();
			setBooRenderBankMasterPanel(false);
			setBooRenderContactDeatailsPanel(true);
			//		SessionStateManage session = new SessionStateManage();

			//contact details
			//		setBankLangthRendered(false);
			//		setBankLengthPanelRendered(false);
			setBankLangthRendered(false);

			setBankLengthPanelRendered(false);
			setBankLangthDataTableRendered(false);
			setNextButtonRendered(false);

			//		clearForContactDetails();
			/*	List<BankContactDetails> contactDetails = bankMasterContactDetailsService.getbankContactInfo(getBankIdInternal());

		setContactBankCode(getBankCode());
		if(contactDetails.size()!=0){
			setContactBankId(contactDetails.get(0).getBankContactId());
			setContactZone(contactDetails.get(0).getExZone().getZoneId());
			setContactPersonForContactDetails(contactDetails.get(0).getContactPerson());
			setContactDep(contactDetails.get(0).getContactDept());
			setContactDesignation(contactDetails.get(0).getContactDesg());
			setContactMobile(contactDetails.get(0).getMobile());
			setLocalContactPersonForContactDetails(contactDetails.get(0).getContactPersonAr());
			setLocalContactDepartment(contactDetails.get(0).getContactDeptAr());
			setLocalContactDesignation(contactDetails.get(0).getContactDesgAr());
			setContactBankId(contactDetails.get(0).getBankContactId());
			setContactZone(null);
			setContactPersonForContactDetails(null);
			setContactDep(null);
			setContactDesignation(null);
			setContactMobile(null);
			setLocalContactPersonForContactDetails(null);
			setLocalContactDepartment(null);
			setLocalContactDesignation(null);
			//setContactDetailsPk(contactDetails.getInternalContactId());
		}	

		getFetchDBContact();*/

			if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {

				if(isApproval()==true){
					setBooBankApprovalRender(true);
					setBooBankCancelRender(true);
					setBooRenderSavePanel(false);
					setBooBankAddRender(false);
					setBooBankCancelRender(false);
					setBooRenderDataTable(false);



				}else{
					setBooBankApprovalRender(false);
					setBooBankCancelRender(false);
					setBooRenderSavePanel(true);
					setBooBankAddRender(true);
					setBooBankCancelRender(true);
				}

			}else{
				setBooBankApprovalRender(false);
				setBooBankCancelRender(false);
				setBooRenderSavePanel(true);
				setBooBankAddRender(true);
				setBooBankCancelRender(true);
			}

			if(getBankIdInternal()==null){
				lstBankMasterContactDetails.clear();
				setContactBankCode(getBankCode());
			}
			//setContactBankCode(getBankCode());
			if(lstBankMasterContactDetails.size()<=0){
				setBooRenderDataTable(false);
				setBooRenderSavePanel(false);
			}
		}

	}

	public void backToBankMasterPanel(){
		if (bankLengthList.size()>0) {
			setBankLengthPanelRendered(true);
		}
		setRenderBankLengthPanel(true);
		setBooRenderBankMasterPanel(true);
		setBooRenderContactDeatailsPanel(false);
		setBankLangthRendered(true);
	}

	private Boolean booRenderSavePanel=false;
	private Boolean booRenderDataTable=false;



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


	public List<String> autoCompleteBankCode(String query){
		List<String> results =null;
		try{
			if(mapCountryName != null && mapCountryName.size() != 0){
				// no need fetch Records
			}else{
				fetchAllCountryByLanguageId();
			}
			
			if (query.length() > 0) {
				List<BankMaster>   bankMaster=iGeneralService.getAllBankCodeFromBankMaster(query);
				results = new ArrayList<String>();  
				for (BankMaster bank:bankMaster) {  
					results.add(bank.getBankCode() + " - " + bank.getBankFullName() + " - " + mapCountryName.get(bank.getFsCountryMaster().getCountryId()));
				}  

			}
		}catch (NullPointerException e) {
			setWarningMessage("Method Name: autoCompleteBankCode");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}
		return results;
	}
	
	// fetch country name by country id and language id
	public void fetchAllCountryByLanguageId(){
		mapCountryName.clear();
		List<CountryMasterDesc> lstCountryDesc = iGeneralService.getCountryList(session.getLanguageId());
		if(lstCountryDesc != null && lstCountryDesc.size() != 0){
			for (CountryMasterDesc countryMasterDesc : lstCountryDesc) {
				mapCountryName.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
			}
		}
	}




	// Bank Contact Details Combing ---RAHAMATHLALI SHAIK

	/*  Below code added by VISWA@@ */

	@Autowired
	IBankApprovalService bankApprovalService;

	@Autowired
	IBankLengthService bankLengthService;

	private Boolean booBankApprovalRender = false;
	private Boolean booBankSaveRender = false;
	private Boolean booBankAddRender = false;
	private Boolean booBankCancelRender = false;
	private Boolean booBankbackRender = false;
	private boolean approval=false;
	private boolean bankLangthRendered=false;
	private boolean bankLangthDataTableRendered=false;
	private String dynamicLabelForActivateDeactivate=null;
	private String remarks=null;

	private boolean nextButtonRendered = false;
	private boolean bankLengthRendered=false;

	private boolean bankLengthPanelRendered = false;
	private BigDecimal bankLength = null;

	public void fetchBankActivationList() throws Exception{
		
		bankActivationList.clear();
		if(getCountryId() ==null){
			RequestContext.getCurrentInstance().execute("countryMandatory.show();");
		} else{
			BankMasterDataTable bankMasterDataTable = null;
			List<BankMaster> bankactivelist = bankApprovalService.getBankListForActiveInactive(getCountryId(),getBankBranch());
			if(bankactivelist.size()>0){
				for (BankMaster bankmaster : bankactivelist) {
					bankMasterDataTable = new BankMasterDataTable();
					System.out.println(bankmaster.getBankFullName());
					bankMasterDataTable.setBankCode(bankmaster.getBankCode());
					bankMasterDataTable.setFullName(bankmaster.getBankFullName());
					bankMasterDataTable.setShortName(bankmaster.getBankShortName());
					bankMasterDataTable.setReutersBankNname(bankmaster.getReutersBankName());
					bankMasterDataTable.setEmail(bankmaster.getEmail());
					bankMasterDataTable.setIsStatus(bankmaster.getRecordStatus());
					bankMasterDataTable.setCreatedBy(bankmaster.getCreator());
					bankMasterDataTable.setCreatedDate(bankmaster.getCreateDate());
					bankMasterDataTable.setRemarks(bankmaster.getRemarks());
					if(bankmaster.getRecordStatus().equalsIgnoreCase(Constants.Yes)){
						bankMasterDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					}
					if(bankmaster.getRecordStatus().equalsIgnoreCase(Constants.D)){
						bankMasterDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					}
					bankActivationList.add(bankMasterDataTable);
				}
			}else {
				RequestContext.getCurrentInstance().execute("noRecords.show();");
			}
		}

		
		//	return bankActivationList;

	}
	public List<BankMasterDataTable> getBankActivationList() {
		return bankActivationList;
	}
	public void setBankActivationList(List<BankMasterDataTable> bankActivationList) {
		this.bankActivationList = bankActivationList;
	}

	private List<BankMasterDataTable> bankApprovalList = new ArrayList<BankMasterDataTable>();
	private List<BankLengthDataTable> bankLengthList = new CopyOnWriteArrayList<BankLengthDataTable>();
	private List<BankMasterDataTable> bankActivationList = new ArrayList<BankMasterDataTable>();

	public void bankApprovalNavigation() {
		clear();
		clearForContactDetails();
		lstBankMasterContactDetails.clear();
		setBooRenderBankMasterPanel(true);
		setBooRenderContactDeatailsPanel(false);
		setLengthLst(null);
		setContactList(null);
		bankApprovalList.clear();
		setApproval(true);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankapprovallist.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../treasury/bankapprovallist.xhtml");
		}catch (NullPointerException e) {
			setWarningMessage("Method Name: bankApprovalNavigation");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		} catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}
	}



	public List<BankMasterDataTable> getBankApprovalList() {

		try{
			bankApprovalList.clear();
			BankMasterDataTable bankMasterDataTable = null;

			List<BankMaster> bankapprovallist = bankApprovalService.getBankListForApproval();
			if(bankapprovallist.size()>0){
				for (BankMaster bankmaster : bankapprovallist) {
					bankMasterDataTable = new BankMasterDataTable();
					System.out.println(bankmaster.getBankFullName());
					bankMasterDataTable.setBankCode(bankmaster.getBankCode());
					bankMasterDataTable.setFullName(bankmaster.getBankFullName());
					bankMasterDataTable.setShortName(bankmaster.getBankShortName());
					bankMasterDataTable.setReutersBankNname(bankmaster.getReutersBankName());
					bankMasterDataTable.setEmail(bankmaster.getEmail());
					bankMasterDataTable.setCreatedBy(bankmaster.getCreator());
					bankMasterDataTable.setCreatedDate(bankmaster.getCreateDate());
					bankMasterDataTable.setFetchCreateBy(sessionStateManage.getUserName());
					bankMasterDataTable.setFetchCreateDate(new Date());
					bankApprovalList.add(bankMasterDataTable);
				}
			}
		}catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}
		return bankApprovalList;
	}

	public void setBankApprovalList(List<BankMasterDataTable> bankApprovalList) {
		this.bankApprovalList = bankApprovalList;
	}

	public void gotoBankMasterApproval(BankMasterDataTable datatable) {
		//added koti@17/03/2015 
		try {
			if(datatable.getCreatedBy().equalsIgnoreCase(sessionStateManage.getUserName())){
				RequestContext.getCurrentInstance().execute("unapprove.show();");
			}else{
				setApproval(true);
				setBooBankApprovalRender(true);
				setRenderBankLengthPanel(true);
				bankApprovalList.clear();
				setBankLengthPanelRendered(true);

				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankmasterapproval.xhtml");
				fetchBankMasterInfo1(datatable);
			}
		}catch (NullPointerException e) {
			setWarningMessage("Method Name: gotoBankMasterApproval");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			return;
		}catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
			return;
		}

	}

	public void getActiveInactive(BankMasterDataTable datatable) {

		setBankCode(datatable.getBankCode());
		setCreatedBy(datatable.getCreatedBy());
		setCreatedDate(datatable.getCreatedDate());
		if(datatable.getIsStatus().equalsIgnoreCase(Constants.Yes)){


			RequestContext.getCurrentInstance().execute("remarks.show();");

		}
		if(datatable.getIsStatus().equalsIgnoreCase(Constants.D)){

			RequestContext.getCurrentInstance().execute("unupprove.show();");
		}

	}


	private String fetchCreateBy;
	private Date fetchCreateDate;

	public String getFetchCreateBy() {
		return fetchCreateBy;
	}
	public void setFetchCreateBy(String fetchCreateBy) {
		this.fetchCreateBy = fetchCreateBy;
	}



	public Date getFetchCreateDate() {
		return fetchCreateDate;
	}
	public void setFetchCreateDate(Date fetchCreateDate) {
		this.fetchCreateDate = fetchCreateDate;
	}
	/**
	 * Fetch data depending upon given bank Code 
	 */
	public void fetchBankMasterInfo1(BankMasterDataTable datatable) throws Exception{

		setBooRenderBankMasterPanel(true);
		setBankLengthPanelRendered(true);
		setBooRenderContactDeatailsPanel(false);
		//setBooLocal(false);

		bankApprovalList.clear();
		List<BankMaster> lstBankMasterInfo = new ArrayList<BankMaster>();
		//	BankMasterDataTable bankdata = new BankMasterDataTable();
		setBankCode(datatable.getBankCode());
		/*Checking that BankCode field empty or not*/ 
		if(getBankCode() != null && getBankCode().length() > 0){
			lstBankMasterInfo = getBankMasterInfoService().getBankMasterInfoForApproval(getBankCode());
			/*checking that data is there in database or not, for the given Bank Code*/   
			if(lstBankMasterInfo != null && lstBankMasterInfo.size() > 0) {
				/*setting primary key*/ 
				setBankCode(datatable.getBankCode());
				setBankIdInternal(lstBankMasterInfo.get(0).getBankId());
				/*setBankCode(lstBankMasterInfo.get(0).getBankCode());*/
				setFullName(lstBankMasterInfo.get(0).getBankFullName());
				setShortName(lstBankMasterInfo.get(0).getBankShortName());
				setLanguageBank(lstBankMasterInfo.get(0).getLanguageInd());
				setAddress1(lstBankMasterInfo.get(0).getAddress1());
				setAddress2(lstBankMasterInfo.get(0).getAddress2());
				setCountryId(lstBankMasterInfo.get(0).getFsCountryMaster().getCountryId());
				//	setCurrencyId(lstBankMasterInfo.get(0).getCurrencyMaster().getCurrencyId());
				/*Now we need to populate state depending upon country(saved), otherwise state will not set with the state previously saved state*/
				//populateState(); 
				//	setStateId(lstBankMasterInfo.get(0).getFsStateMaster().getStateId());
				/*Now we need to populate District depending upon state(saved), otherwise district will not set with the district previously saved district*/
				//	populateDistrict();
				//setDistrictId(lstBankMasterInfo.get(0).getFsDistrictMaster().getDistrictId());
				/*Now we need to populate City depending upon city(saved), otherwise city will not set with the city previously saved city*/
				//	populateCity();
				//	populateCurrency();
				//	setCityId(lstBankMasterInfo.get(0).getFsCityMaster().getCityId());
				setZip(lstBankMasterInfo.get(0).getZipCode());
				setTelephone(lstBankMasterInfo.get(0).getTeleponeNo());
				setFax(lstBankMasterInfo.get(0).getFaxNo());
				setEmail(lstBankMasterInfo.get(0).getEmail());
				setLocalFullName(lstBankMasterInfo.get(0).getBankFullNameAr());
				setLocalShortname(lstBankMasterInfo.get(0).getBankShortNameAr());
				setLocalAddress1(lstBankMasterInfo.get(0).getAddress1Ar());
				setLocalAddress2(lstBankMasterInfo.get(0).getAddress2Ar());
				if(lstBankMasterInfo.get(0).getIfscLen()!=null){
					setIFSCLength(lstBankMasterInfo.get(0).getIfscLen().toPlainString());
				}
				setReutersBankNname(lstBankMasterInfo.get(0).getReutersBankName());
				setFileSpecificOrAll(lstBankMasterInfo.get(0).getFileAlls());
				setFileBranchWiseYOrN(lstBankMasterInfo.get(0).getFileBranch());
				//setMICRReuters(lstBankMasterInfo.get(0).getMicrCode());
				setRemmiterModeYOrN(lstBankMasterInfo.get(0).getFileRemitMode()); 
				setAllowNoBank(lstBankMasterInfo.get(0).getAllowNoBank());
				setBankBranchCheck(lstBankMasterInfo.get(0).getBankBranchCheck());
				setRemarks(lstBankMasterInfo.get(0).getRemarks());
				setCreatedBy(lstBankMasterInfo.get(0).getCreator());
				setCreatedDate(lstBankMasterInfo.get(0).getCreateDate());
				setFetchCreateBy(lstBankMasterInfo.get(0).getCreator());
				setFetchCreateDate(lstBankMasterInfo.get(0).getCreateDate());


			} else {
				String bankCode = getBankCode();
				clear();
				setBankCode(bankCode);
			}
		}

		getZoneLists();
		fetchDBContactList();;
		fetchBankLengthApproval();

		/*if(lstBankMasterContactDetails.size()>0 && getBankIdInternal()!=null){
		setBooRenderDataTable(true);
		setBooRenderSavePanel(true);
	}
	setBooBankGenInfo(true);
	setBooBankAddress(false);
	setBooFileAcceptance(false);*/
	}




	/**
	 * Responsible to save Bank Master details 
	 * @return
	 */
	public boolean approve() throws Exception {



		boolean approveMsg=false;
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
		if(getIFSCLength()!=null){
			bankMaster.setIfscLen(new BigDecimal(getIFSCLength()));
		}
		bankMaster.setReutersBankName(getReutersBankNname() );
		bankMaster.setFileAlls(getFileSpecificOrAll());
		bankMaster.setFileBranch(getFileBranchWiseYOrN());
		//bankMaster.setMicrCode(getMICRReuters());
		bankMaster.setFileRemitMode(getRemmiterModeYOrN());

		CountryMaster countryMaster =  new CountryMaster();
		countryMaster.setCountryId(getCountryId());
		bankMaster.setFsCountryMaster(countryMaster);
		bankMaster.setAllowNoBank(getAllowNoBank());
		bankMaster.setRemarks(getRemarks());
		bankMaster.setBankBranchCheck(getBankBranchCheck());
		/*StateMaster statemaster = new StateMaster();
	statemaster.setStateId(getStateId());
	bankMaster.setFsStateMaster(statemaster);
		 */
		/*DistrictMaster districtMaster = new DistrictMaster();
	districtMaster.setDistrictId(getDistrictId());
	bankMaster.setFsDistrictMaster(districtMaster);*/

		/*	CityMaster cityMaster = new CityMaster();
	cityMaster.setCityId(getCityId());
	bankMaster.setFsCityMaster(cityMaster);*/

		/*CurrencyMaster currencyMaster = new CurrencyMaster();
	currencyMaster.setCurrencyId(getCurrencyId());
	bankMaster.setCurrencyMaster(currencyMaster);*/

		//TODO hard code user name   

		bankMaster.setRecordStatus(Constants.Yes);
		/*It will return true always, except the very first time*/  
		if(getBankIdInternal() != null){
			bankMaster.setBankId(getBankIdInternal());
			/*bankMaster.setUpdateDate(new Date());
		bankMaster.setModifier(sessionStateManage.getUserName());*/
			bankMaster.setCreator(getFetchCreateBy());
			bankMaster.setCreateDate(getFetchCreateDate());
		}else{
			bankMaster.setCreator(sessionStateManage.getUserName());
			bankMaster.setCreateDate(new Date());
		}

		bankMaster.setApprovedBy(sessionStateManage.getUserName());
		bankMaster.setApprovedDate(new Date());
		setCreatedBy(getCreatedBy());
		setCreatedDate(getCreatedDate());

		//	List<BankMaster> lstBankMasterInfo = getBankMasterInfoService().getBankMasterInfo(getBankCode());

		/*if(lstBankMasterInfo!=null){
		setCreatedBy(lstBankMasterInfo.get(0).getCreator());
	}*/

		/*System.out.println(sessionStateManage.getUserName());
	System.out.println(lstBankMasterInfo.get(0).getCreator());

	if(CommonUtil.validateApprovedBy(sessionStateManage.getUserName(), getCreatedBy())==true){
		getBankMasterInfoService().saveBankMasterInfo(bankMaster);
		RequestContext.getCurrentInstance().execute("approve.show();");
	}else{
		RequestContext.getCurrentInstance().execute("unapprove.show();");
	}

		 */

		getBankMasterInfoService().saveBankMasterInfo(bankMaster);

		/*setting primary key*/
		setBankIdInternal(bankMaster.getBankId());
		contactDetailsApprove(bankMaster);
		approveBankAccountLength(bankMaster);
		//END OF PROCEDURE CALL
		//new code for procedure cc move newly created data to emos
		HashMap<String, String>  approveRecord = new HashMap<String, String>();
		approveRecord.put("BANK_ID",  bankMaster.getBankId().toString());

		HashMap<String, String>   ouputValues =getBankMasterInfoService().callPopulateBankMaster(approveRecord);

		if(ouputValues.get("P_ERROR_MESSAGE")!=null){
			setWarningMessage(ouputValues.get("P_ERROR_MESSAGE"));
			getBankMasterInfoService().procErrorToUnApprove(getBankIdInternal(),getContactList(),getLengthLst());
			setLengthLst(null);
			setContactList(null);
			approveMsg=false; 
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
			return approveMsg;
		}
		//END OF PROCEDURE CALL
		clear();
		setLengthLst(null);
		setContactList(null);
		approveMsg=true;
		/*try{
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
		context.redirect("../treasury/bankcontactdetails.xhtml");
	} catch(Exception e) {
		log.info("Problem to redirect:"+e);
	}*/
		return approveMsg; 
	}

	public void cancelFromApproval() throws IOException {
		//resetValues();
		bankApprovalList.clear();
		FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/bankapprovallist.xhtml");
	}

	public void cancelFromActivation() throws IOException {
		//resetValues();
		bankActivationList.clear();
		FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/bankactiveinactivelist.xhtml");
	}


	public void clickOnOKApprove() throws IOException{
		bankApprovalList.clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankapprovallist.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void bankApprove(){

		/*List<BankMaster> lstBankMasterInfo = getBankMasterInfoService().getBankMasterInfo(getBankCode());

	if(lstBankMasterInfo.size()!=0){
		setCreatedBy(lstBankMasterInfo.get(0).getCreator());
	}*/

		try{
			boolean approveMsg=approve();
			//approve();
			if(!approveMsg)
			{
				return;
			}
			RequestContext.getCurrentInstance().execute("approve.show();");
		}catch (NullPointerException e) {
			setWarningMessage("Method Name: bankApprove");
			getBankMasterInfoService().procErrorToUnApprove(getBankIdInternal(),getContactList(),getLengthLst());
			setLengthLst(null);
			setContactList(null);
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			return;
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			getBankMasterInfoService().procErrorToUnApprove(getBankIdInternal(),getContactList(),getLengthLst());
			setLengthLst(null);
			setContactList(null);
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
			return;
		}



		/*if(getFetchCreateBy().equalsIgnoreCase(sessionStateManage.getUserName())){
		RequestContext.getCurrentInstance().execute("unapprove.show();");
	}else{
		approve();
		RequestContext.getCurrentInstance().execute("approve.show();");
	}*/
	}

	public Boolean getBooBankApprovalRender() {
		return booBankApprovalRender;
	}
	public void setBooBankApprovalRender(Boolean booBankApprovalRender) {
		this.booBankApprovalRender = booBankApprovalRender;
	}
	public Boolean getBooBankSaveRender() {
		return booBankSaveRender;
	}
	public void setBooBankSaveRender(Boolean booBankSaveRender) {
		this.booBankSaveRender = booBankSaveRender;
	}
	public Boolean getBooBankAddRender() {
		return booBankAddRender;
	}
	public void setBooBankAddRender(Boolean booBankAddRender) {
		this.booBankAddRender = booBankAddRender;
	}
	public Boolean getBooBankCancelRender() {
		return booBankCancelRender;
	}
	public void setBooBankCancelRender(Boolean booBankCancelRender) {
		this.booBankCancelRender = booBankCancelRender;
	}
	public Boolean getBooBankbackRender() {
		return booBankbackRender;
	}
	public void setBooBankbackRender(Boolean booBankbackRender) {
		this.booBankbackRender = booBankbackRender;
	}
	public boolean isApproval() {
		return approval;
	}
	public void setApproval(boolean approval) {
		this.approval = approval;
	}


	public void saveBankContact(BankMaster bankMaster){

		BankContactDetails contactDetails = new BankContactDetails();

		bankMaster.setBankId(getBankIdInternal());

		contactDetails.setExBankMaster(bankMaster);
		contactDetails.setContactPerson(getContactPersonForContactDetails());
		Zone zone = new Zone();
		zone.setZoneId(getContactZone());
		contactDetails.setExZone(zone);
		contactDetails.setContactDept(getContactDep());
		contactDetails.setContactDesg(getContactDesignation());
		contactDetails.setContactPersonAr(getLocalContactPersonForContactDetails());
		contactDetails.setContactDeptAr(getLocalContactDepartment());
		contactDetails.setContactDesgAr(getLocalContactDesignation());
		contactDetails.setMobile(getContactMobile());
		contactDetails.setRecordStatus(Constants.Yes);

		//contactDetails.setExBankMaster(bankMaster);
		//if condition Going to update, else section going to insert
		if(getContactBankId()!=null) {
			contactDetails.setBankContactId(getContactBankId());
			contactDetails.setModifier(sessionStateManage.getUserName());
			contactDetails.setUpdateDate(new Date());
			contactDetails.setCreateDate(getCreatedDate());
			contactDetails.setCreator(getCreatedBy());
		} else {
			contactDetails.setCreateDate(getCreatedDate());
			contactDetails.setCreator(getCreatedBy());
		}
		if(isApproval()==true){
			contactDetails.setApprovedBy(sessionStateManage.getUserName());
			contactDetails.setApprovedDate(new Date());
		}
		getBankMasterContactDetailsService().saveBankMasterContactDetails(contactDetails);

	}

	public void fetchBankLength() throws Exception{

		bankLengthList.clear();
		setBooEditButton(false);
		List<BankAccountLength> lengthList = new ArrayList<BankAccountLength>();

		/*if(isApproval()){
			lengthList = bankLengthService.findBankId(getBankIdInternal());
		}else{
			lengthList = bankLengthService.findAllLengthByBankId(getBankIdInternal());
		}*/

		lengthList = bankLengthService.findBankId(getBankIdInternal());


		BankLengthDataTable datatable=null;
		for (BankAccountLength length : lengthList) {
			datatable = new BankLengthDataTable();
			datatable.setBankId(length.getBankMaster().getBankId());
			datatable.setBankLengthId(length.getAccountLenId());
			datatable.setBankLength(length.getAcLength());
			datatable.setRenderEditButton(true);
			datatable.setBooEditButton(false);
			/*if(!isApproval()){
				if(length.getRecordStatus().equalsIgnoreCase(Constants.U)){
					datatable.setStatusRecord("Delete");
				}else{
					datatable.setStatusRecord("Active");
				}
			}*/

			if(length.getRecordStatus() != null){
				if (length.getRecordStatus().equalsIgnoreCase(Constants.Yes)) {
					datatable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					datatable.setRecordStatus(Constants.ACTIVE);
					datatable.setStatusRecord(Constants.Yes);
				} else if (length.getRecordStatus().equalsIgnoreCase(Constants.D)) {
					datatable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					datatable.setRecordStatus(Constants.DEACTIVATE);
					datatable.setStatusRecord(Constants.D);
				} /*else if (length.getRecordStatus().equalsIgnoreCase(Constants.U) && loyaltyParameterSettingDtobj.getModifiedBy() == null && loyaltyParameterSettingDtobj.getModifiedDate() == null && loyaltyParameterSettingDtobj.getApprovedBy() == null
						&& loyaltyParameterSettingDtobj.getApprovedDate() == null && loyaltyParameterSettingDtobj.getRemarks() == null) {
					loyaltyParameterSettingDtobj.setDynamicLabelForActivateDeactivate(Constants.DELETE);
				}*/ else if (length.getRecordStatus().equalsIgnoreCase(Constants.U)){
					datatable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					datatable.setRecordStatus(Constants.PENDING_FOR_APPROVE);
					datatable.setStatusRecord(Constants.U);
				}
			}

			bankLengthList.add(datatable);
		}
		if(bankLengthList.size()>0)
		{
			setBankLengthPanelRendered(true);
		}else
		{
			setBankLengthPanelRendered(false);
		}
		setBankLength(null);
		setBankLengthId(null);
	}

	public List<BankAccountLength> saveBankLength(BankMaster bankMaster) throws Exception{

		//List<BankLengthDataTable> baklengthList = new ArrayList<BankLengthDataTable>();
		//BankAccountLength banklength = null;

		List<BankAccountLength> lstBankAccountLength= new ArrayList<BankAccountLength>();

		for (BankLengthDataTable bankLengthObj : bankLengthList) {

			/*lisbanklen=getBankMasterContactDetailsService().getBanklength(bankMaster.getBankId());
		if(lisbanklen.size()>0)
		{
			banklength.setAccountLengthId(lisbanklen.get(0).getAccountLengthId());

		} else{*/

			BankAccountLength banklength = new BankAccountLength();

			banklength.setAccountLenId(bankLengthObj.getBankLengthId());



			bankMaster.setBankId(bankMaster.getBankId());



			banklength.setBankMaster(bankMaster);
			banklength.setAcLength(bankLengthObj.getBankLength());
			/*koti added bank account length status 17062016 activate and deactivate start*/
			if(bankLengthObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)){
				banklength.setRecordStatus(Constants.Yes);
			}else if (bankLengthObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				banklength.setRecordStatus(Constants.D);
			}else if (bankLengthObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
				banklength.setRecordStatus(Constants.U);
			}else{
				banklength.setRecordStatus(Constants.U);
			}
			/*koti added bank account length status 17062016 activate and deactivate End*/
			banklength.setCreator(sessionStateManage.getUserName());
			banklength.setCreateDate(new Date());
			System.out.println(" -----------saved");
			//bankLengthService.saveBankLengthRecord(banklength);
			lstBankAccountLength.add(banklength);

			//System.out.println("saved");
		}
		return lstBankAccountLength;

	}

	private String dbbanklengthstatus;

	public String getDbbanklengthstatus() {
		return dbbanklengthstatus;
	}

	public void setDbbanklengthstatus(String dbbanklengthstatus) {
		this.dbbanklengthstatus = dbbanklengthstatus;
	}

	public void addBankLengthToDataTable(){
		setBankLengthPanelRendered(true);
		setBooEditButton(false);
		BankLengthDataTable bankLengthDataTable=new BankLengthDataTable();
		if(getBankLength() != null){
			bankLengthDataTable.setBankId(getBankIdInternal());
			bankLengthDataTable.setBankLength(getBankLength());
			bankLengthDataTable.setBankLengthId(getBankLengthId());
			/*added koti bank Account length status columns start*/

			bankLengthDataTable.setRenderEditButton(true);
			if(getDbbanklengthstatus() != null){
				if((bankLengthDataTable.getBankLength()).compareTo(bankLengthDTObj.getBankLength())==0){
					if(getDbbanklengthstatus().equalsIgnoreCase(Constants.Yes)){
						bankLengthDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
						bankLengthDataTable.setRecordStatus(Constants.DEACTIVATE);
						bankLengthDataTable.setStatusRecord(getDbbanklengthstatus());
					}else if (getDbbanklengthstatus().equalsIgnoreCase(Constants.D)) {
						bankLengthDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
						bankLengthDataTable.setRecordStatus(Constants.ACTIVATE);
						bankLengthDataTable.setStatusRecord(getDbbanklengthstatus());
					}else if (getDbbanklengthstatus().equalsIgnoreCase(Constants.U)) {
						bankLengthDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						bankLengthDataTable.setRecordStatus(Constants.PENDING_FOR_APPROVE);
						bankLengthDataTable.setStatusRecord(getDbbanklengthstatus());
					}
				}else{
					bankLengthDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					bankLengthDataTable.setRecordStatus(Constants.PENDING_FOR_APPROVE);
					bankLengthDataTable.setStatusRecord(Constants.U);
				}
			}else{
				bankLengthDataTable.setDynamicLabelForActivateDeactivate("Remove");
				bankLengthDataTable.setRecordStatus("Remove");	
				bankLengthDataTable.setStatusRecord(Constants.U);
			}
			//bankLengthDataTable.setStatusRecord(statusRecord);
			/*added koti bank Account length status columns End*/
			/*if(getBankLengthId()!=null){
			if(getDbbanklengthstatus().equalsIgnoreCase("Delete")){
				bankLengthDataTable.setStatusRecord("Delete");
			}else{
				bankLengthDataTable.setStatusRecord("Active");
			}
		}else{
			bankLengthDataTable.setStatusRecord("Remove");
		}*/

			bankLengthList.add(bankLengthDataTable);

			setBankLength(null);
			setBankLengthId(null);
			setDbbanklengthstatus(null);
		}
	}


	/**
	 * add contact length functionality bug fixed 16-03-2015
	 */
	public void checkAndAddBankLength() {
		try{
			setContactBankCode(getBankCode());
			System.out.println("bank length="+getBankLength());

			if(getBankLength()!=null){

				setFlagContactlength(true);

				if (bankLengthList.size() != 0) {
					for (BankLengthDataTable datatable : bankLengthList)
					{
						System.out.println("bank length1="+datatable.getBankLength());
						if(datatable.getBankLength() != null){
							if (datatable.getBankLength().compareTo(getBankLength())==0) 
							{
								//setBankLength(null);
								setFlagContactlength(false);
								RequestContext.getCurrentInstance().execute("duplicate.show();");
								return;
							} else{
								setFlagContactlength(true);
							}
						}else{
							throw new Exception("Bank Length Comming null :: bankLength");
						}
					}
				} 

				if(getFlagContactlength())
				{
					setBankLengthPanelRendered(true);
					addBankLengthToDataTable();

				}

			}else{
				RequestContext.getCurrentInstance().execute("accountLength.show();");
				return;
			}
			//addBankLengthToDataTable();
		}catch (NullPointerException e) {
			setWarningMessage("Method Name: checkAndAddBankLength");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}
	}
	/*
	if(getBankLength()!=null){
	    if (bankLengthList.size() != 0) {
	        for (BankLengthDataTable datatable : bankLengthList) {
	        	System.out.println("bank length1="+datatable.getBankLength());
	            if (datatable.getBankLength().compareTo(getBankLength())==0) {
	               setBankLength(null);
	               RequestContext.getCurrentInstance().execute("duplicate.show();");
	               break;
	            } else {
	                if (datatable.getBankLength()==getBankLength()) {
	                	setBankLength(null);
	                	RequestContext.getCurrentInstance().execute("duplicate.show();");
	                }
	               // addBankLengthToDataTable();	
	            }

	        }

	    }else{
	     	addBankLengthToDataTable();
	    }
	}else{
		RequestContext.getCurrentInstance().execute("checklength.show();");
	}
    setBankLengthPanelRendered(true);
    setBankLangthDataTableRendered(true);
    setNextButtonRendered(true);

}*/

	public void editBankLength(BankLengthDataTable bankLengthDataTable){

		setBankLengthDTObj(bankLengthDataTable);
		setBankIdInternal(bankLengthDataTable.getBankId());
		setBankLength(bankLengthDataTable.getBankLength());
		setBankLengthId(bankLengthDataTable.getBankLengthId());
		setDbbanklengthstatus(bankLengthDataTable.getStatusRecord());
		setBooEditButton(true);
		setRenderEditButton(bankLengthDataTable.getRenderEditButton());
		bankLengthList.remove(bankLengthDataTable);
		if(bankLengthList.size()<=0){
			//setBankLangthDataTableRendered(false);
			setBankLengthPanelRendered(false);
		}

	}

	public void removeBankLength(BankLengthDataTable bankLengthDataTable){

		try{

			Boolean delete;

			if(bankLengthDataTable.getBankLengthId()!=null){
				if(bankLengthDataTable.getStatusRecord().equalsIgnoreCase("Delete")){
					delete = true;
				}else{
					delete = false;
				}
				bankLengthService.deleteBankLengthRecord(bankLengthDataTable.getBankLengthId(), sessionStateManage.getUserName(),delete);
				fetchBankLength();
			}else{
				bankLengthList.remove(bankLengthDataTable);
			}

			if(bankLengthList.size()<=0){
				//setBankLangthDataTableRendered(false);
				setBankLengthPanelRendered(false);
			}

		}catch (NullPointerException e) {
			setWarningMessage("Method Name: removeBankLength");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}catch(Exception e)
		{
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}


	}

	public void bankActiveNavigation() {

		try {
			bankActivationList.clear();
			//setApproval(true);
			//fetchBankActivationList();
			fetchFilterData();
			
			setCountryId(null);
			setBankBranch(null);
			setBankBranchList(null);
			List<CountryMasterDesc> countryList = bankBranchUploadService.getAllCountryList(sessionStateManage.getLanguageId());
			if(countryList!=null && countryList.size() > 0) {
				setAllCountryList(countryList);
			}
			
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankactiveinactivelist.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../treasury/bankactiveinactivelist.xhtml");
		}catch (NullPointerException e) {
			setWarningMessage("Method Name: bankActiveNavigation");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		} catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
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

	public void exit() throws IOException {
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void updateRemarks() throws Exception{	



		BankMaster bankmaster = new BankMaster();
		CountryMaster countryMaster = new CountryMaster();
		List<BankMaster> lstBankMasterInfo = new ArrayList<BankMaster>();
		setBankCode(getBankCode());

		if(getBankCode() != null){
			//lstBankMasterInfo = getBankMasterInfoService().getBankMasterInfo(getBankCode());
			lstBankMasterInfo = getBankMasterInfoService().getAllBankMasterInfo(getBankCode());

			if(lstBankMasterInfo.size() != 0) {
				/*setting primary key*/ 
				bankmaster.setBankId(lstBankMasterInfo.get(0).getBankId());
				setBankCode(getBankCode());
				bankmaster.setBankCode(getBankCode());
				bankmaster.setBankFullName(lstBankMasterInfo.get(0).getBankFullName());
				bankmaster.setBankShortName(lstBankMasterInfo.get(0).getBankShortName());
				bankmaster.setLanguageInd(lstBankMasterInfo.get(0).getLanguageInd());
				bankmaster.setAddress1(lstBankMasterInfo.get(0).getAddress1());
				bankmaster.setAddress2(lstBankMasterInfo.get(0).getAddress2());


				countryMaster.setCountryId(lstBankMasterInfo.get(0).getFsCountryMaster().getCountryId());
				//bankmaster.setCountryId(lstBankMasterInfo.get(0).getFsCountryMaster().getCountryId());

				bankmaster.setFsCountryMaster(countryMaster);

				bankmaster.setZipCode(lstBankMasterInfo.get(0).getZipCode());
				bankmaster.setTeleponeNo(lstBankMasterInfo.get(0).getTeleponeNo());
				bankmaster.setFaxNo(lstBankMasterInfo.get(0).getFaxNo());
				bankmaster.setEmail(lstBankMasterInfo.get(0).getEmail());
				bankmaster.setBankFullNameAr(lstBankMasterInfo.get(0).getBankFullNameAr());
				bankmaster.setBankShortNameAr(lstBankMasterInfo.get(0).getBankShortNameAr());
				bankmaster.setAddress1Ar(lstBankMasterInfo.get(0).getAddress1Ar());
				bankmaster.setAddress2Ar(lstBankMasterInfo.get(0).getAddress2Ar());
				bankmaster.setIfscLen(lstBankMasterInfo.get(0).getIfscLen());
				bankmaster.setReutersBankName(lstBankMasterInfo.get(0).getReutersBankName());
				bankmaster.setFileAlls(lstBankMasterInfo.get(0).getFileAlls());
				bankmaster.setFileBranch(lstBankMasterInfo.get(0).getFileBranch());
				//setMICRReuters(lstBankMasterInfo.get(0).getMicrCode());
				bankmaster.setFileRemitMode(lstBankMasterInfo.get(0).getFileRemitMode()); 
				bankmaster.setAllowNoBank(lstBankMasterInfo.get(0).getAllowNoBank());
				bankmaster.setBankBranchCheck(lstBankMasterInfo.get(0).getBankBranchCheck());
				bankmaster.setCreator(lstBankMasterInfo.get(0).getCreator());
				bankmaster.setCreateDate(lstBankMasterInfo.get(0).getCreateDate());
				bankmaster.setModifier(lstBankMasterInfo.get(0).getModifier());
				if(lstBankMasterInfo.get(0).getRecordStatus().equals(Constants.Yes)){
					bankmaster.setRemarks(getRemarks());
					bankmaster.setUpdateDate(new Date());
					bankmaster.setModifier(session.getUserName());
					bankmaster.setApprovedBy(null);
					bankmaster.setApprovedDate(null);
					bankmaster.setRecordStatus(Constants.D);
				}
				if(lstBankMasterInfo.get(0).getRecordStatus().equals(Constants.D)){
					bankmaster.setUpdateDate(new Date());
					bankmaster.setModifier(session.getUserName());
					bankmaster.setApprovedBy(null);
					bankmaster.setApprovedDate(null);
					bankmaster.setRecordStatus(Constants.U);
					bankmaster.setRemarks(null);
				}

				bankMasterInfoService.saveBankMasterInfo(bankmaster);

				//FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankactiveinactivelist.xhtml");


			} 
		}

		getZoneLists();
		fetchBankContactInfo();
		fetchBankLength();
		setRemarks(null);

	}


	public void clickOnOKActivate() {


		try {
			bankActivationList.clear();
			fetchBankActivationList();
			fetchFilterData();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankactiveinactivelist.xhtml");
		}catch (NullPointerException e) {
			setWarningMessage("Method Name: clickOnOKActivate");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		} catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}
	}

	public void clickOnOKDeActivate() throws IOException{

		try {
			bankActivationList.clear();
			updateRemarks();
			fetchBankActivationList();
			fetchFilterData();			
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankactiveinactivelist.xhtml");
		}catch (NullPointerException e) {
			setWarningMessage("Method Name: clickOnOKDeActivate");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		} catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("warningDailogId.show();");
		}
	}



	public BigDecimal getBankLength() {
		return bankLength;
	}
	public void setBankLength(BigDecimal bankLength) {
		this.bankLength = bankLength;
	}
	public List<BankLengthDataTable> getBankLengthList() {
		return bankLengthList;
	}
	public void setBankLengthList(List<BankLengthDataTable> bankLengthList) {
		this.bankLengthList = bankLengthList;
	}
	public boolean isBankLangthRendered() {
		return bankLangthRendered;
	}
	public void setBankLangthRendered(boolean bankLangthRendered) {
		this.bankLangthRendered = bankLangthRendered;
	}
	public boolean isBankLangthDataTableRendered() {
		return bankLangthDataTableRendered;
	}
	public void setBankLangthDataTableRendered(boolean bankLangthDataTableRendered) {
		this.bankLangthDataTableRendered = bankLangthDataTableRendered;
	}
	public boolean isBankLengthPanelRendered() {
		return bankLengthPanelRendered;
	}
	public void setBankLengthPanelRendered(boolean bankLengthPanelRendered) {
		this.bankLengthPanelRendered = bankLengthPanelRendered;
	}
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public boolean isNextButtonRendered() {
		return nextButtonRendered;
	}
	public void setNextButtonRendered(boolean nextButtonRendered) {
		this.nextButtonRendered = nextButtonRendered;
	}
	public boolean isBankLengthRendered() {
		return bankLengthRendered;
	}
	public void setBankLengthRendered(boolean bankLengthRendered) {
		this.bankLengthRendered = bankLengthRendered;
	}


	public void add()
	{ 
		setFlag(true); 
		setBoorender(true);
		if(listContact.size()>0)
		{
			for(ContactDataTable datatable : listContact)
			{ 
				if(datatable.getZoneId().compareTo(getContactZone())==0)
				{
					setFlag(false);
					//clearContactDetails();
					setContactZone(null);
					RequestContext.getCurrentInstance().execute("checkZone.show();");
					break;
				} else{
					setFlag(true);

				}

			}
		} 

		if(getFlag()){



			ContactDataTable contact=new ContactDataTable();

			contact.setPkcontactId(getContactDetailsPk());

			contact.setBankId(getBankIdInternal());

			contact.setZoneId(getContactZone());
			contact.setZoneName(getBankMasterContactDetailsService().getZoneName(getContactZone()));

			contact.setContactPerson(getContactPersonForContactDetails());

			contact.setContactDep(getContactDep());
			contact.setContactDegign(getContactDesignation());
			contact.setMobile(getContactMobile());

			contact.setContactpersonAr(getLocalContactPersonForContactDetails());
			contact.setContactDepAr(getLocalContactDepartment());
			contact.setContactDegiAr(getLocalContactDesignation());

			if(getContactDetailsPk()!=null){
				if(getDbContactListStatus().equalsIgnoreCase("Delete")){
					contact.setStatus("Delete");
				}else{
					contact.setStatus("Active");
				}
			}else{
				contact.setStatus("Remove");
			}

			listContact.add(contact);

			clearContactDetails();
		}

	}

	private List<ContactDataTable> listContact=new ArrayList<ContactDataTable>();

	public List<ContactDataTable> getListContact() {
		return listContact;
	}
	public void setListContact(List<ContactDataTable> listContact) {
		this.listContact = listContact;
	}

	public List<BankContactDetails> saveContact(BankMaster bankMaster) throws Exception
	{ 

		List<BankContactDetails> lstBankContactDetails = new ArrayList<BankContactDetails>();
		for(ContactDataTable contactData:listContact)
		{
			BankContactDetails contact=new BankContactDetails();

			listDBContact=getBankMasterContactDetailsService().getContactId(contactData.getBankId(), contactData.getZoneId());

			if(listDBContact.size()>0)
			{
				contact.setBankContactId(listDBContact.get(0).getBankContactId());
				contact.setCreator(listDBContact.get(0).getCreator());
				contact.setCreateDate(listDBContact.get(0).getCreateDate());
				contact.setModifier(sessionStateManage.getUserName());
				contact.setUpdateDate(new Date());

			} else {
				contact.setBankContactId(null);
				contact.setCreator(sessionStateManage.getUserName());
				contact.setCreateDate(new Date());
			}

			contact.setContactPerson(contactData.getContactPerson());
			contact.setContactDept(contactData.getContactDep());
			contact.setContactDesg(contactData.getContactDegign());

			/*BankMaster bankid=new BankMaster();
	      bankid.setBankId(contactData.getBankId());*/

			contact.setExBankMaster(bankMaster);


			contact.setMobile(contactData.getMobile());

			Zone zone = new Zone();
			zone.setZoneId(contactData.getZoneId());
			ZoneMasterDesc zoneMasterDesc=new ZoneMasterDesc();
			zoneMasterDesc.setZoneDescription(contactData.getZoneName());

			contact.setExZone(zone);

			contact.setRecordStatus(Constants.U);

			contact.setApprovedBy(null);
			contact.setApprovedDate(null);

			contact.setContactPersonAr(contactData.getContactpersonAr());
			contact.setContactDeptAr(contactData.getContactDepAr());
			contact.setContactDesgAr(contactData.getContactDegiAr());

			//getBankMasterContactDetailsService().saveBankMasterContactDetails(contact);
			lstBankContactDetails.add(contact);


		}

		return lstBankContactDetails;
	}

	public void clearContactDetails()
	{
		setContactZone(null);
		setContactPersonForContactDetails(null);
		setContactDep(null);
		setContactDesignation(null);
		setContactMobile(null);
		setLocalContactPersonForContactDetails(null);
		setLocalContactDepartment(null);
		setLocalContactDesignation(null);
		setContactDetailsPk(null);
	}

	private boolean boorender=false;


	public boolean getBoorender() {
		return boorender;
	}
	public void setBoorender(boolean boorender) {
		this.boorender = boorender;
	}

	private boolean flag=false;

	public boolean getFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}


	private String dbContactListStatus;

	public String getDbContactListStatus() {
		return dbContactListStatus;
	}

	public void setDbContactListStatus(String dbContactListStatus) {
		this.dbContactListStatus = dbContactListStatus;
	}

	public void edit(ContactDataTable contactdata)
	{
		setContactDetailsPk(contactdata.getPkcontactId());
		setContactZone(contactdata.getZoneId());
		setContactPersonForContactDetails(contactdata.getContactPerson());
		setContactDep(contactdata.getContactDep());
		setContactDesignation(contactdata.getContactDegign());
		setContactMobile(contactdata.getMobile());
		setLocalContactPersonForContactDetails(contactdata.getContactpersonAr());
		setLocalContactDepartment(contactdata.getContactDepAr());
		setLocalContactDesignation(contactdata.getContactDegiAr());
		setDbContactListStatus(contactdata.getStatus());

		listContact.remove(contactdata);

		if(listContact.size()<=0){
			setBoorender(false);
		}

	}


	public void delete(ContactDataTable deletecontact)
	{
		Boolean delete;
		if(deletecontact.getPkcontactId()!=null){
			if(deletecontact.getStatus().equalsIgnoreCase("Delete")){
				delete = true;
			}else{
				delete = false;
			}
			getBankMasterContactDetailsService().removeContactDetails(deletecontact.getPkcontactId(), sessionStateManage.getUserName(),delete);
			fetchDBContactList();
		}else{
			listContact.remove(deletecontact);
		}

		if(listContact.size()<=0){
			setBoorender(false);
		}

	}

	private List<BankContactDetails> listDBContact=new ArrayList<BankContactDetails>();

	private List<BankContactDetails> fetchDBContact=new ArrayList<BankContactDetails>();

	public List<BankContactDetails> getFetchDBContact() {

		if(getContactBankId()!=null)
		{
			listContact.clear();
			setBoorender(true);
			fetchDBContact=getBankMasterContactDetailsService().getContactslist(getBankIdInternal());
			for(BankContactDetails dbrecContact:fetchDBContact)
			{
				ContactDataTable DbDataTable=new ContactDataTable();

				DbDataTable.setPkcontactId(dbrecContact.getBankContactId());
				DbDataTable.setBankId(dbrecContact.getExBankMaster().getBankId());

				DbDataTable.setZoneId(dbrecContact.getExZone().getZoneId());
				DbDataTable.setZoneName(zoneInfo.get(dbrecContact.getExZone().getZoneId()));
				DbDataTable.setContactPerson(dbrecContact.getContactPerson());
				DbDataTable.setContactDegign(dbrecContact.getContactDesg());
				DbDataTable.setContactDep(dbrecContact.getContactDept());

				DbDataTable.setContactpersonAr(dbrecContact.getContactPersonAr());
				DbDataTable.setContactDepAr(dbrecContact.getContactDeptAr());
				DbDataTable.setContactDegiAr(dbrecContact.getContactDesgAr());

				DbDataTable.setMobile(dbrecContact.getMobile());


				listContact.add(DbDataTable);



			} 
		} /*else{
		//setBoorender(false);
	}*/


		return fetchDBContact;
	}

	public void setFetchDBContact(List<BankContactDetails> fetchDBContact) {
		this.fetchDBContact = fetchDBContact;
	}

	private BigDecimal bankIdContact;



	public BigDecimal getBankIdContact() {
		return bankIdContact;
	}
	public void setBankIdContact(BigDecimal bankIdContact) {
		this.bankIdContact = bankIdContact;
	}

	private List<BankAccountLength> lisbanklen=new ArrayList<BankAccountLength>();

	private BigDecimal bankLengthId;

	public BigDecimal getBankLengthId() {
		return bankLengthId;
	}
	public void setBankLengthId(BigDecimal bankLengthId) {
		this.bankLengthId = bankLengthId;
	}

	private boolean flagContactlength=false;

	public boolean getFlagContactlength() {
		return flagContactlength;
	}
	public void setFlagContactlength(boolean flagContactlength) {
		this.flagContactlength = flagContactlength;
	}


	public void fetchDBContactList() {

		/*if(isApproval()){
		setBooRenderDataTable(true);
		setBooBankApprovalRender(true);
		fetchDBContact=getBankMasterContactDetailsService().getContactslist(getBankIdInternal());
	}else{
		setBooRenderDataTable(true);
		setBooBankApprovalRender(true);
		fetchDBContact=getBankMasterContactDetailsService().getbankContactInfo(getBankIdInternal());
	}*/

		setBooRenderDataTable(true);
		fetchDBContact=getBankMasterContactDetailsService().getContactslist(getBankIdInternal());

		listContact.clear();
		setContactBankCode(getBankCode());


		if(fetchDBContact.size()!=0){
			for(BankContactDetails dbrecContact:fetchDBContact)
			{
				ContactDataTable DbDataTable=new ContactDataTable();

				DbDataTable.setPkcontactId(dbrecContact.getBankContactId());
				DbDataTable.setBankId(dbrecContact.getExBankMaster().getBankId());
				DbDataTable.setBankName(dbrecContact.getExBankMaster().getBankFullName());

				DbDataTable.setZoneId(dbrecContact.getExZone().getZoneId());

				DbDataTable.setZoneName(zoneInfo.get(dbrecContact.getExZone().getZoneId()));
				DbDataTable.setContactPerson(dbrecContact.getContactPerson());
				DbDataTable.setContactDegign(dbrecContact.getContactDesg());
				DbDataTable.setContactDep(dbrecContact.getContactDept());

				DbDataTable.setContactpersonAr(dbrecContact.getContactPersonAr());
				DbDataTable.setContactDepAr(dbrecContact.getContactDeptAr());
				DbDataTable.setContactDegiAr(dbrecContact.getContactDesgAr());

				DbDataTable.setMobile(dbrecContact.getMobile());
				DbDataTable.setCreatedBy(dbrecContact.getCreator());
				DbDataTable.setCreatedDate(dbrecContact.getCreateDate());
				/*DbDataTable.setModifiedDate(new Date());
			DbDataTable.setModifiedBy(session.getUserName());*/

				if(!isApproval()){
					if(dbrecContact.getRecordStatus().equalsIgnoreCase(Constants.U)){
						DbDataTable.setModifiedBy(session.getUserName());
						DbDataTable.setModifiedDate(new Date());
						DbDataTable.setStatus("Delete");
					}else{
						DbDataTable.setModifiedBy(session.getUserName());
						DbDataTable.setModifiedDate(new Date());
						DbDataTable.setStatus("Active");
					}
				}


				listContact.add(DbDataTable);
			} 
		}


	}

	public void contactDetailsApprove(BankMaster bankMaster){

		if(listContact.size()!=0){
			contactList=new ArrayList<BigDecimal>();
			for (ContactDataTable element : listContact) {
				BankContactDetails contactDetails = new BankContactDetails();
				Zone zone = new Zone();
				zone.setZoneId(element.getZoneId());
				contactDetails.setExZone(zone);
				//	contactDetails.setExZone(zone);
				//	contactDetails.setRegion(element.getZone());
				contactDetails.setBankContactId(element.getPkcontactId());
				contactDetails.setContactPerson(element.getContactPerson());
				contactDetails.setContactDept(element.getContactDep());
				contactDetails.setContactDesg(element.getContactDegign());
				contactDetails.setContactPersonAr(element.getContactpersonAr());
				contactDetails.setContactDeptAr(element.getContactDepAr());
				contactDetails.setContactDesgAr(element.getContactDegiAr());
				contactDetails.setMobile(element.getMobile());
				contactDetails.setRecordStatus(constant.Yes);
				contactDetails.setModifier(element.getModifiedBy());
				contactDetails.setUpdateDate(element.getModifiedDate());
				contactDetails.setCreateDate(element.getCreatedDate());
				contactDetails.setCreator(element.getCreatedBy());

				contactDetails.setExBankMaster(bankMaster);

				contactDetails.setApprovedBy(sessionStateManage.getUserName());
				contactDetails.setApprovedDate(new Date());

				contactList.add(element.getPkcontactId());

				getBankMasterContactDetailsService().saveBankMasterContactDetails(contactDetails);

			}
			setContactList(contactList);
		}

	}

	public void approveBankAccountLength(BankMaster bankMaster){
		/*List<BankAccountLength> lengthList = bankLengthService.findBankId(bankMaster.getBankId());

	if(lengthList.size()!=0){
		for(BankAccountLength obj : lengthList){
			obj.setRecordStatus(Constants.Yes);
			bankLengthService.saveBankLengthRecord(obj);
		} 
	}*/
		List<BankAccountLength> lengthList = bankLengthService.findBankIdApprovalBankLength(getBankIdInternal());
		if(lengthList.size()>0){
			lengthLst=new ArrayList<BigDecimal>();
			for (BankAccountLength bankAccountLengthObj : lengthList) {
				bankAccountLengthObj.setRecordStatus(Constants.Yes);
				lengthLst.add(bankAccountLengthObj.getAccountLenId());
				bankLengthService.saveBankLengthRecord(bankAccountLengthObj);
			}
			setLengthLst(lengthLst);
		}
	}
	public void fetchFilterData(){
		setBankFilterList(getBankActivationList());
	}
	private List<BankMasterDataTable> bankFilterList;

	public List<BankMasterDataTable> getBankFilterList() {
		return bankFilterList;
	}
	public void setBankFilterList(List<BankMasterDataTable> bankFilterList) {
		this.bankFilterList = bankFilterList;
	}

	public void clearRemarks(){
		setRemarks(null);
	}

	/*koti starts Bank Account lenth Activate and DeAtivate start **************16062016*/
	public void checkStatus(BankLengthDataTable dataTable){
		if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
			dataTable.setRecordStatus(Constants.PENDING_FOR_APPROVE);
			RequestContext.getCurrentInstance().execute("pending.show();");
			return;
		} else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			dataTable.setRecordStatus(Constants.REMOVE);
			bankLengthList.remove(dataTable);
			//loyaltyParameterSettingNewDT.remove(dataTable);
		} else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			dataTable.setRemarksCheck(true);
			/*setApprovedBy(dataTable.getApprovedBy());
		setApprovedDate(dataTable.getApprovedDate());*/
			dataTable.setRecordStatus(Constants.ACTIVE);
			RequestContext.getCurrentInstance().execute("remarks.show();");
		} else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			dataTable.setActiveRecordCheck(true);
			dataTable.setRecordStatus(Constants.DEACTIVATE);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;
		} /*else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null && dataTable.getApprovedBy() == null && dataTable.getApprovedDate() == null
			&& dataTable.getRemarks() == null) {
		dataTable.setPermentDeleteCheck(true);
		RequestContext.getCurrentInstance().execute("permanentDelete.show();");
		return;
	}*/
		if (bankLengthList.size() == 0) {
			setBankLengthPanelRendered(false);
		}
	}
	//DeActive record to pending Approval start
	public void activateRecord() throws Exception{
		if (bankLengthList.size() > 0) {
			for (BankLengthDataTable dataTable : bankLengthList) {
				if (dataTable.getActiveRecordCheck() != null) {
					if (dataTable.getActiveRecordCheck().equals(true)) {
						conformActiveRecordToPendingForApproval(dataTable);
						fetchBankLength();
					}
				}
			}
		}
	}

	public void conformActiveRecordToPendingForApproval(BankLengthDataTable dataTable) {
		try{
			bankLengthService.DeActiveRecordToPendingForApprovalOfBankLength(dataTable.getBankLengthId(), sessionStateManage.getUserName());
			bankLengthService.DeActiveRecordToPendingForApprovalOfBankMaster(getBankIdInternal(), sessionStateManage.getUserName());
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setWarningMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}
	//DeActive record to pending Approval End
	//Active Record to DeActivate start
	public void clickOkRemarksBankLenth(){
		if (getRemarks() != null && !getRemarks().equals("")) {
			for (BankLengthDataTable dataTable : bankLengthList) {
				if (dataTable.getRemarksCheck() != null) {
					if (dataTable.getRemarksCheck().equals(true)) {
						dataTable.setRemarks(getRemarks());
						updateBankLength(dataTable);
						try {
							fetchBankLength();
							FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankmaster.xhtml");
						} catch (Exception e) {
							e.printStackTrace();
						}
						setRemarks(null);
					}
				}
			}

		} else {
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		}
	}
	public void updateBankLength(BankLengthDataTable dataTable){
		try{
			bankLengthService.upDateActiveRecordtoDeActive(dataTable.getBankLengthId(), dataTable.getRemarks(), sessionStateManage.getUserName());
			bankLengthService.upDateActiveRecordtoDeActiveBankMaster(getBankIdInternal(),sessionStateManage.getUserName());
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setWarningMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}
	//Active Record to DeActivate End

	//to fetch approval record bank length start
	public void fetchBankLengthApproval() throws Exception{

		bankLengthList.clear();

		List<BankAccountLength> lengthList = new ArrayList<BankAccountLength>();

		/*if(isApproval()){
		lengthList = bankLengthService.findBankId(getBankIdInternal());
	}else{
		lengthList = bankLengthService.findAllLengthByBankId(getBankIdInternal());
	}*/

		lengthList = bankLengthService.findBankIdApprovalBankLength(getBankIdInternal());


		BankLengthDataTable datatable=null;
		for (BankAccountLength length : lengthList) {
			datatable = new BankLengthDataTable();
			datatable.setBankId(length.getBankMaster().getBankId());
			datatable.setBankLengthId(length.getAccountLenId());
			datatable.setBankLength(length.getAcLength());

			/*if(!isApproval()){
			if(length.getRecordStatus().equalsIgnoreCase(Constants.U)){
				datatable.setStatusRecord("Delete");
			}else{
				datatable.setStatusRecord("Active");
			}
		}*/

			if(length.getRecordStatus() != null){
				if (length.getRecordStatus().equalsIgnoreCase(Constants.Yes)) {
					datatable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					datatable.setRecordStatus(Constants.ACTIVE);
				} else if (length.getRecordStatus().equalsIgnoreCase(Constants.D)) {
					datatable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					datatable.setRecordStatus(Constants.DEACTIVATE);
				} /*else if (length.getRecordStatus().equalsIgnoreCase(Constants.U) && loyaltyParameterSettingDtobj.getModifiedBy() == null && loyaltyParameterSettingDtobj.getModifiedDate() == null && loyaltyParameterSettingDtobj.getApprovedBy() == null
					&& loyaltyParameterSettingDtobj.getApprovedDate() == null && loyaltyParameterSettingDtobj.getRemarks() == null) {
				loyaltyParameterSettingDtobj.setDynamicLabelForActivateDeactivate(Constants.DELETE);
			}*/ else if (length.getRecordStatus().equalsIgnoreCase(Constants.U)){
				datatable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				datatable.setRecordStatus(Constants.PENDING_FOR_APPROVE);
			}
			}

			bankLengthList.add(datatable);
		}
		if(bankLengthList.size()>0)
		{
			setBankLengthPanelRendered(true);
		}else
		{
			setBankLengthPanelRendered(false);
		}

	}
	//to fetch approval record bank length End

	//cancel remarks Start
	public void cancelRemarks(){
		setRemarks(null);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankmaster.xhtml");
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setWarningMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	//cancel remarks End

	private Boolean booEditButton=false;
	private Boolean renderEditButton=false;
	private BankLengthDataTable bankLengthDTObj=null;
	public Boolean getBooEditButton() {
		return booEditButton;
	}
	public void setBooEditButton(Boolean booEditButton) {
		this.booEditButton = booEditButton;
	}
	public Boolean getRenderEditButton() {
		return renderEditButton;
	}
	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}
	public BankLengthDataTable getBankLengthDTObj() {
		return bankLengthDTObj;
	}
	public void setBankLengthDTObj(BankLengthDataTable bankLengthDTObj) {
		this.bankLengthDTObj = bankLengthDTObj;
	}


	/*koti starts Bank Account lenth Activate and DeAtivate End **************16062016*/

	public void deleteRecordPermentely(BankLengthDataTable dataTable){
		dataTable.setPermentDeleteCheck(true);
		RequestContext.getCurrentInstance().execute("permanentDelete.show();");
		return;
	}
	public void bankAccountLengthDelete()throws Exception{
		for (BankLengthDataTable dataTable : bankLengthList) {
			if(dataTable.getPermentDeleteCheck()){
				deleteRecord(dataTable);
				bankLengthList.remove(dataTable);
			}
		}
	}
	public void deleteRecord(BankLengthDataTable bankLengthObj){
		try{
			bankLengthService.deleteRecordBankAccountLength(bankLengthObj.getBankLengthId());
			bankLengthService.upDateActiveRecordtoDeActiveBankMaster(getBankIdInternal(),sessionStateManage.getUserName());
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setWarningMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	public void cancelDelete(){
		for (BankLengthDataTable dataTable : bankLengthList) {
			if(dataTable.getPermentDeleteCheck()){
				dataTable.setPermentDeleteCheck(false);
			}
		}

	}

	List<BigDecimal> contactList;
	List<BigDecimal> lengthLst;

	public List<BigDecimal> getContactList() {
		return contactList;
	}
	public void setContactList(List<BigDecimal> contactList) {
		this.contactList = contactList;
	}
	public List<BigDecimal> getLengthLst() {
		return lengthLst;
	}
	public void setLengthLst(List<BigDecimal> lengthLst) {
		this.lengthLst = lengthLst;
	}



}

