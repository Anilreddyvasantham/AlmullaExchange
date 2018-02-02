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

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.bankBranchUpload.service.BankBranchUploadService;
import com.amg.exchange.beneficiary.bean.BranchDataTable;
import com.amg.exchange.beneficiary.bean.SearchBranchDeatilsBean;
import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.bean.CustomerPersonalInfoBean;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IBankApprovalService;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("bankBranchDetails")
@Scope("session")
public class BankBranchDetails<T> implements Serializable {
	Logger log = Logger.getLogger(CustomerPersonalInfoBean.class);
	private static final long serialVersionUID = 1L;
	private String branchCode;
	private String branchCodeFullName;
	private String fullName;
	private String shortName;
	private String address1;
	private String address2;
	private String zipCode;
	private String telephoneNumber;
	private String fax;
	private String email;
	private String contactPerson;
	private String contactdesignation;
	private String contactDepartment;
	private String micrCode;
	private String fullNameLocal;
	private String shortNameLocal;
	private String address1Local;
	private String address2Local;
	private String contactPersonLocal;
	private String contactdesignationLocal;
	private String contactDepartmentLocal;
	private String swift;
	private String debitBranchID;
	private String branchIFSC;
	private String routingID;
	private String mobileNo;
	private BigDecimal bankID;

	private Date createddate;
	private String createdby;
	private Boolean booUpdateBranchCode=true;
	private Boolean booSystemBranchCode=false;
	private String dBbranchCode;
	private Boolean editButton;
	private BigDecimal branchId;
	private String branchFullNameForSearch;
	private String ifscCodeForSearch;


	private String branchNameMsg;
	private String errorMsg=null;;

	private BigDecimal countryId;
	private String countryName;
	private BigDecimal stateId;
	private BigDecimal cityId;
	private BigDecimal districtId;
	private Boolean isLocalPanelRender = true;
	private Boolean isBranchInfo = true;
	private Boolean isBranchLocalAddressPanel = true;
	private Boolean isBranchTransactionPanel = true;
	private List<StateMasterDesc> lstStateList = new ArrayList<StateMasterDesc>();
	private List<DistrictMasterDesc> lstDistrictList = new ArrayList<DistrictMasterDesc>();
	private List<CityMasterDesc> lstCityList = new ArrayList<CityMasterDesc>();
	private List<BankMaster> lstBankList = new ArrayList<BankMaster>();
	private List<BankMaster> lstbankcountry = new ArrayList<BankMaster>();
	//private List<BankBranchView> lstbranchCode = new ArrayList<BankBranchView>();
	List<BankBranch> lstBranchDataForDeactive= new ArrayList<BankBranch>();
	List<CountryMasterDesc> countryNameList = new ArrayList<CountryMasterDesc>();
	private List<BankBranch> lstbranch = new ArrayList<BankBranch>();
	private List<BankBranch> branchList = new ArrayList<BankBranch>();
	private Map<BigDecimal, String> lstBankNames = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> lstCountryNames = new HashMap<BigDecimal, String>();
	private List<CountryMasterDesc> allCountryList = null;


	private Boolean booRenderDatatable;

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	@Autowired
	ApplicationContext appContext;
	
	@Autowired
	BankBranchUploadService bankBranchUploadService;


	public Boolean getBooRenderDatatable() {
		return booRenderDatatable;
	}

	public void setBooRenderDatatable(Boolean booRenderDatatable) {
		this.booRenderDatatable = booRenderDatatable;
	}

	public void setBooUpdateBranchCode(Boolean booUpdateBranchCode) {
		this.booUpdateBranchCode = booUpdateBranchCode;
	}

	public Boolean getBooUpdateBranchCode() {
		return booUpdateBranchCode;
	}

	public Boolean getBooSystemBranchCode() {
		return booSystemBranchCode;
	}

	public void setBooSystemBranchCode(Boolean booSystemBranchCode) {
		this.booSystemBranchCode = booSystemBranchCode;
	}

	public String getdBbranchCode() {
		return dBbranchCode;
	}

	public void setdBbranchCode(String dBbranchCode) {
		this.dBbranchCode = dBbranchCode;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public List<BankMaster> getLstbankcountry() {
		return lstbankcountry;
	}

	public void setLstbankcountry(List<BankMaster> lstbankcountry) {
		this.lstbankcountry = lstbankcountry;
	}

	private BankBranch selectedBankbranch;

	private BigDecimal pkBankBranch = null;
	SessionStateManage sessionStateManage = new SessionStateManage();

	/**
	 * @return the selectedBankbranch
	 */
	public BankBranch getSelectedBankbranch() {
		return this.selectedBankbranch;
	}

	/**
	 * @param selectedBankbranch
	 *            the selectedBankbranch to set
	 */
	public void setSelectedBankbranch(BankBranch selectedBankbranch) {
		this.selectedBankbranch = selectedBankbranch;
	}

	// Retrive Bank Branch List
	private List<BankBranch> bankBranchList = new ArrayList<BankBranch>();




	/*public List<BankBranchView> getLstbranchCode() {
		return lstbranchCode;
	}

	public void setLstbranchCode(List<BankBranchView> lstbranchCode) {
		this.lstbranchCode = lstbranchCode;
	}*/



	public List<BankBranch> getLstBranchDataForDeactive() {
		return lstBranchDataForDeactive;
	}

	public void setLstBranchDataForDeactive(
			List<BankBranch> lstBranchDataForDeactive) {
		this.lstBranchDataForDeactive = lstBranchDataForDeactive;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public List<CountryMasterDesc> getCountryNameList() {
		return countryNameList;
	}

	public void setCountryNameList(List<CountryMasterDesc> countryNameList) {
		this.countryNameList = countryNameList;
	}

	public List<BankBranch> getLstbranch() {
		return lstbranch;
	}

	public void setLstbranch(List<BankBranch> lstbranch) {
		this.lstbranch = lstbranch;
	}

	/**
	 * Bank Branch detail construre 
	 */
	public BankBranchDetails() {

		setIsBranchInfo(true);
		setIsBranchLocalAddressPanel(false);
		setIsBranchTransactionPanel(false);
	}
	
	public void clearCache() throws IOException {
		resetBankBranch();
		setIsBranchInfo(true);
		setIsBranchLocalAddressPanel(false);
		setIsBranchTransactionPanel(false);
		setErrorMsg(null);
		setCountryNameList(null);
		getBankNameList();
		populateCountryList();
		loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankbranchdetails.xhtml");
		FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankbranchdetails.xhtml");
	}

	/**
	 * @param branchCode
	 * @param fullName
	 * @param shortName
	 * @param address1
	 * @param address2
	 * @param telephoneNumber
	 * @param fax
	 * @param email
	 * @param contactPerson
	 * @param contactdesignation
	 * @param contactDepartment
	 * @param micrCode
	 * @param fullNameLocal
	 * @param shortNameLocal
	 * @param address1Local
	 * @param address2Local
	 * @param contactPersonLocal
	 * @param contactdesignationLocal
	 * @param contactDepartmentLocal
	 * @param swift
	 * @param debitBranchID
	 * @param branchIFSC
	 * @param routingID
	 */
	public BankBranchDetails(String branchCode, String fullName,
			String shortName, String address1, String address2,
			String telephoneNumber, String fax, String email,
			String contactPerson, String contactdesignation,
			String contactDepartment, String micrCode, String fullNameLocal,
			String shortNameLocal, String address1Local, String address2Local, // NOPMD by exim07 on 9/6/14 10:44 AM
			String contactPersonLocal, String contactdesignationLocal,
			String contactDepartmentLocal, String swift, String debitBranchID,
			String branchIFSC, String routingID) {
		this.branchCode = branchCode;
		this.fullName = fullName;
		this.shortName = shortName;
		this.address1 = address1;
		this.address2 = address2;
		this.zipCode=zipCode;
		this.telephoneNumber = telephoneNumber;
		this.fax = fax;
		this.email = email;
		this.contactPerson = contactPerson;
		this.contactdesignation = contactdesignation;
		this.contactDepartment = contactDepartment;
		this.micrCode = micrCode;
		this.fullNameLocal = fullNameLocal;
		this.shortNameLocal = shortNameLocal;
		this.address1Local = address1Local;
		this.address2Local = address2Local;
		this.contactPersonLocal = contactPersonLocal;
		this.contactdesignationLocal = contactdesignationLocal;
		this.contactDepartmentLocal = contactDepartmentLocal;
		this.swift = swift;
		this.debitBranchID = debitBranchID;
		this.branchIFSC = branchIFSC;
		this.routingID = routingID;
	}

	@Autowired
	IGeneralService<T> generalService;

	/**
	 * @return the generalService
	 */
	public IGeneralService<T> getGeneralService() {
		return this.generalService;
	}

	/**
	 * @param generalService
	 *            the generalService to set
	 */
	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	/**
	 * @return the bankBranchDetailsService
	 */
	@Autowired
	IBankBranchDetailsService<T> bankBranchDetailsService;

	public IBankBranchDetailsService<T> getBankBranchDetailsService() {
		return this.bankBranchDetailsService;
	}

	/**
	 * @param bankBranchDetailsService
	 *            the bankBranchDetailsService to set
	 */
	public void setBankBranchDetailsService(
			IBankBranchDetailsService<T> bankBranchDetailsService) {
		this.bankBranchDetailsService = bankBranchDetailsService;
	}



	public String getBranchCodeFullName() {
		return branchCodeFullName;
	}

	public void setBranchCodeFullName(String branchCodeFullName) {
		this.branchCodeFullName = branchCodeFullName;
	}

	/**
	 * @return the branchNameMsg
	 */
	public String getBranchNameMsg() {
		return this.branchNameMsg;
	}

	/**
	 * @param branchNameMsg the branchNameMsg to set
	 */
	public void setBranchNameMsg(String branchNameMsg) {
		this.branchNameMsg = branchNameMsg;
	}

	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		return this.branchCode;
	}

	/**
	 * @param branchCode
	 *            the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return this.fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return this.shortName;
	}

	/**
	 * @param shortName
	 *            the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return this.address1;
	}

	/**
	 * @param address1
	 *            the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return this.address2;
	}


	/**
	 * @param address2
	 *            the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	/**
	 * @return the telephoneNumber
	 */
	public String getTelephoneNumber() {
		return this.telephoneNumber;
	}

	/**
	 * @param telephoneNumber
	 *            the telephoneNumber to set
	 */
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return this.fax;
	}

	/**
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return this.contactPerson;
	}

	/**
	 * @param contactPerson
	 *            the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the contactdesignation
	 */
	public String getContactdesignation() {
		return this.contactdesignation;
	}

	/**
	 * @param contactdesignation
	 *            the contactdesignation to set
	 */
	public void setContactdesignation(String contactdesignation) {
		this.contactdesignation = contactdesignation;
	}

	/**
	 * @return the contactDepartment
	 */
	public String getContactDepartment() {
		return this.contactDepartment;
	}

	/**
	 * @param contactDepartment
	 *            the contactDepartment to set
	 */
	public void setContactDepartment(String contactDepartment) {
		this.contactDepartment = contactDepartment;
	}

	/**
	 * @return the micrCode
	 */
	public String getMicrCode() {
		return this.micrCode;
	}

	/**
	 * @param micrCode
	 *            the micrCode to set
	 */
	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}

	/**
	 * @return the fullNameLocal
	 */
	public String getFullNameLocal() {
		return this.fullNameLocal;
	}

	/**
	 * @param fullNameLocal
	 *            the fullNameLocal to set
	 */
	public void setFullNameLocal(String fullNameLocal) {
		this.fullNameLocal = fullNameLocal;
	}

	/**
	 * @return the shortNameLocal
	 */
	public String getShortNameLocal() {
		return this.shortNameLocal;
	}

	/**
	 * @param shortNameLocal
	 *            the shortNameLocal to set
	 */
	public void setShortNameLocal(String shortNameLocal) {
		this.shortNameLocal = shortNameLocal;
	}

	/**
	 * @return the address1Local
	 */
	public String getAddress1Local() {
		return this.address1Local;
	}

	/**
	 * @param address1Local
	 *            the address1Local to set
	 */
	public void setAddress1Local(String address1Local) {
		this.address1Local = address1Local;
	}

	/**
	 * @return the address2Local
	 */
	public String getAddress2Local() {
		return this.address2Local;
	}

	/**
	 * @param address2Local
	 *            the address2Local to set
	 */
	public void setAddress2Local(String address2Local) {
		this.address2Local = address2Local;
	}

	/**
	 * @return the contactPersonLocal
	 */
	public String getContactPersonLocal() {
		return this.contactPersonLocal;
	}

	/**
	 * @param contactPersonLocal
	 *            the contactPersonLocal to set
	 */
	public void setContactPersonLocal(String contactPersonLocal) {
		this.contactPersonLocal = contactPersonLocal;
	}

	/**
	 * @return the contactdesignationLocal
	 */
	public String getContactdesignationLocal() {
		return this.contactdesignationLocal;
	}

	/**
	 * @param contactdesignationLocal
	 *            the contactdesignationLocal to set
	 */
	public void setContactdesignationLocal(String contactdesignationLocal) {
		this.contactdesignationLocal = contactdesignationLocal;
	}

	/**
	 * @return the contactDepartmentLocal
	 */
	public String getContactDepartmentLocal() {
		return this.contactDepartmentLocal;
	}

	/**
	 * @param contactDepartmentLocal
	 *            the contactDepartmentLocal to set
	 */
	public void setContactDepartmentLocal(String contactDepartmentLocal) {
		this.contactDepartmentLocal = contactDepartmentLocal;
	}

	/**
	 * @return the swift
	 */
	public String getSwift() {
		return this.swift;
	}

	/**
	 * @param swift
	 *            the swift to set
	 */
	public void setSwift(String swift) {
		this.swift = swift;
	}

	/**
	 * @return the debitBranchID
	 */
	public String getDebitBranchID() {
		return this.debitBranchID;
	}

	/**
	 * @param debitBranchID
	 *            the debitBranchID to set
	 */
	public void setDebitBranchID(String debitBranchID) {
		this.debitBranchID = debitBranchID;
	}

	/**
	 * @return the branchIFSC
	 */
	public String getBranchIFSC() {
		return this.branchIFSC;
	}

	/**
	 * @param branchIFSC
	 *            the branchIFSC to set
	 */
	public void setBranchIFSC(String branchIFSC) {
		this.branchIFSC = branchIFSC;
	}

	/**
	 * @return the routingID
	 */
	public String getRoutingID() {
		return this.routingID;
	}

	/**
	 * @param routingID
	 *            the routingID to set
	 */
	public void setRoutingID(String routingID) {
		this.routingID = routingID;
	}

	// Add Branch Details

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return this.mobileNo;
	}

	/**
	 * @param mobileNo
	 *            the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the countryId
	 */
	public BigDecimal getCountryId() {
		return this.countryId;
	}

	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @param countryId
	 *            the countryId to set
	 */
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the stateId
	 */
	public BigDecimal getStateId() {
		return this.stateId;
	}

	/**
	 * @param stateId
	 *            the stateId to set
	 */
	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return the cityId
	 */
	public BigDecimal getCityId() {
		return this.cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the districtId
	 */
	public BigDecimal getDistrictId() {
		return this.districtId;
	}

	/**
	 * @param districtId
	 *            the districtId to set
	 */
	public void setDistrictId(BigDecimal districtId) {
		this.districtId = districtId;
	}

	/**
	 * @return the bankID
	 */
	public BigDecimal getBankID() {
		return this.bankID;
	}

	/**
	 * @param bankID
	 *            the bankID to set
	 */
	public void setBankID(BigDecimal bankID) {
		this.bankID = bankID;
	}

	/**
	 * Responsible to populate Country
	 * 
	 * @return
	 */
	/*public List<CountryMasterDesc> getCountryNameList() {
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<CountryMasterDesc> lstCountry = getGeneralService()
				.getCountryList(
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "" + 1));
		return lstCountry;
	}*/


	private void populateCountryList() {
		try{
			List<CountryMasterDesc> countryListName = generalService.getCountryList(sessionStateManage.getLanguageId());
			if (countryListName.size() != 0) {
				setCountryNameList(countryListName);
				for (CountryMasterDesc countryMaster : countryNameList) {
					lstCountryNames.put(countryMaster.getFsCountryMaster().getCountryId(), countryMaster.getCountryName());
				}
			}
		} catch (Exception e) {

			log.info("country fetching time Exception :  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}
	
	public void getBankNameList() {
		try{
			setBranchFullNameForSearch(null);
			setIfscCodeForSearch(null );
			branchList.clear();
			List<BankMaster> lstBank = getBankBranchDetailsService().getBankList();
			if(lstBank.size()>0){
				for (BankMaster bankMaster : lstBank) {
					lstBankNames.put(bankMaster.getBankId(), bankMaster.getBankFullName());
				}
				setLstBankList(lstBank);
			}
		} catch (Exception e) {
			log.info("Bank Name List time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
		//return lstBank;
	}

	/**
	 * Responsible to populate BankName
	 * 
	 * @return
	 */
	public void getBankNameListForActiveDeActive() {
		try{
			setBranchFullNameForSearch(null);
			setBankID(null);
			setLstBankList(null);
			setIfscCodeForSearch(null );
			branchList.clear();
			List<BankMaster> lstBank = bankBranchUploadService.getBankList(getCountryId());
			if(lstBank.size()>0){
				for (BankMaster bankMaster : lstBank) {
					lstBankNames.put(bankMaster.getBankId(), bankMaster.getBankFullName());
				}
				setLstBankList(lstBank);
			}
		} catch (Exception e) {
			log.info("Bank Name List time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
		//return lstBank;
	}

	/**
	 * @return the isLocalPanelRender
	 */
	public Boolean getIsLocalPanelRender() {
		return this.isLocalPanelRender;
	}

	/**
	 * @param isLocalPanelRender
	 *            the isLocalPanelRender to set
	 */
	public void setIsLocalPanelRender(Boolean isLocalPanelRender) {
		this.isLocalPanelRender = isLocalPanelRender;
	}

	/**
	 * @return the isBranchInfo
	 */
	public Boolean getIsBranchInfo() {
		return this.isBranchInfo;
	}

	/**
	 * @param isBranchInfo
	 *            the isBranchInfo to set
	 */
	public void setIsBranchInfo(Boolean isBranchInfo) {
		this.isBranchInfo = isBranchInfo;
	}

	/**
	 * @return the isBranchLocalAddressPanel
	 */
	public Boolean getIsBranchLocalAddressPanel() {
		return this.isBranchLocalAddressPanel;
	}

	/**
	 * @param isBranchLocalAddressPanel
	 *            the isBranchLocalAddressPanel to set
	 */
	public void setIsBranchLocalAddressPanel(Boolean isBranchLocalAddressPanel) {
		this.isBranchLocalAddressPanel = isBranchLocalAddressPanel;
	}

	/**
	 * @return the isBranchTransactionPanel
	 */
	public Boolean getIsBranchTransactionPanel() {
		return this.isBranchTransactionPanel;
	}

	/**
	 * @param isBranchTransactionPanel
	 *            the isBranchTransactionPanel to set
	 */
	public void setIsBranchTransactionPanel(Boolean isBranchTransactionPanel) {
		this.isBranchTransactionPanel = isBranchTransactionPanel;
	}

	/**
	 * @return the lstStateList
	 */
	public List<StateMasterDesc> getLstStateList() {
		return this.lstStateList;
	}

	/**
	 * @param lstStateList
	 *            the lstStateList to set
	 */
	public void setLstStateList(List<StateMasterDesc> lstStateList) {
		this.lstStateList = lstStateList;
	}

	/**
	 * @return the lstDistrictList
	 */
	public List<DistrictMasterDesc> getLstDistrictList() {
		return this.lstDistrictList;
	}

	/**
	 * @param lstDistrictList
	 *            the lstDistrictList to set
	 */
	public void setLstDistrictList(List<DistrictMasterDesc> lstDistrictList) {
		this.lstDistrictList = lstDistrictList;
	}

	/**
	 * @return the lstCityList
	 */
	public List<CityMasterDesc> getLstCityList() {
		return this.lstCityList;
	}

	/**
	 * @param lstCityList
	 *            the lstCityList to set
	 */
	public void setLstCityList(List<CityMasterDesc> lstCityList) {
		this.lstCityList = lstCityList;
	}

	/**
	 * @return the lstBankList
	 */
	public List<BankMaster> getLstBankList() {
		return this.lstBankList;
	}

	/**
	 * @param lstBankList
	 *            the lstBankList to set
	 */
	public void setLstBankList(List<BankMaster> lstBankList) {
		this.lstBankList = lstBankList;
	}


	public Map<BigDecimal, String> getLstBankNames() {
		return lstBankNames;
	}
	public void setLstBankNames(Map<BigDecimal, String> lstBankNames) {
		this.lstBankNames = lstBankNames;
	}

	public Map<BigDecimal, String> getLstCountryNames() {
		return lstCountryNames;
	}
	public void setLstCountryNames(Map<BigDecimal, String> lstCountryNames) {
		this.lstCountryNames = lstCountryNames;
	}

	/**
	 * Responsible to prepare stateList by country change
	 * 
	 * @param event
	 */
	public void generateStateList() {
		//SessionStateManage sessionStateManage = new SessionStateManage();
		setLstStateList(null);
		try{
			List<StateMasterDesc> stateMaster = getGeneralService().getStateList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1),getCountryId());
			if(stateMaster.size()!=0){
				setLstStateList(stateMaster);

				if (getCountryId() != null && !getCountryId().equals(sessionStateManage.getCountryId())) {
					setIsLocalPanelRender(false);
				} else {
					setIsLocalPanelRender(true);
				}

			}
		} catch (Exception e) {
			log.info("fetching state list time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}

		setLstDistrictList(null);
		setLstCityList(null);
		setStateId(null);
		setDistrictId(null);
		setCityId(null);
	}

	/**
	 * Responsible to populate District depending upon state selection
	 * 
	 * @param event
	 */
	public void generateDistrictList(AjaxBehaviorEvent event) {
		setLstDistrictList(null);
		try{
			List<DistrictMasterDesc> lstDistrict = getGeneralService()
					.getDistrictList(sessionStateManage.getLanguageId(),
							getCountryId(), getStateId());
			if(lstDistrict.size()>0){
				setLstDistrictList(lstDistrict);
			}
			// reset District, City, as state is changing
			setDistrictId(null);
			setCityId(null);
		} catch (Exception e) {
			log.info("fetching District time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}

	/**
	 * This method is responsible to populate State depending upon country
	 * selection
	 */
	public void populateState() {
		try{
			List<StateMasterDesc> stateMaster = getGeneralService()
					.getStateList(sessionStateManage.getLanguageId(),
							getCountryId());
			if(stateMaster.size()>0){
				setLstStateList(stateMaster);
			}
		} catch (Exception e) {
			log.info("fetching state time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}

	/**
	 * This method is responsible to populate district depending upon state
	 * election
	 */
	public void populateDistrict() {
		try{

			List<DistrictMasterDesc> lstDistrict = getGeneralService()
					.getDistrictList(sessionStateManage.getLanguageId(),
							getCountryId(), getStateId());
			if(lstDistrict.size()>0){
				setLstDistrictList(lstDistrict);
			}
		} catch (Exception e) {
			log.info("fetching District time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}

	/**
	 * This method is responsible to populate City depending upon district
	 * selection
	 */
	public void populateCity() {
		try{
			List<CityMasterDesc> lstCity = getGeneralService()
					.getCityList(sessionStateManage.getLanguageId(),
							getCountryId(), getStateId(), getDistrictId());
			if(lstCity.size()>0){
				setLstCityList(lstCity);
			}
		} catch (Exception e) {
			log.info("fetching city time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}

	/**
	 * This method is responsible to populate City depending upon district
	 * selection
	 */
	public void populateBank() {
		try{
			List<BankMaster> lstBank = getBankBranchDetailsService().getBankList();
			if(lstBank.size()>0){
				setLstBankList(lstBank);
			}
		} catch (Exception e) {
			log.info("populate Bank time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}

	/**
	 * Responsible to populate city depending upon district selection
	 * 
	 * @param event
	 */
	public void generateCityList(AjaxBehaviorEvent event) {
		try{
			List<CityMasterDesc> lstCity = getGeneralService()
					.getCityList(sessionStateManage.getLanguageId(),
							getCountryId(), getStateId(), getDistrictId());
			setLstCityList(lstCity);

			// reset City, as district is changing
			setCityId(null);
		} catch (Exception e) {
			log.info("fetching city time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}


	/**
	 *  
	 * @param event
	 */
	public void getCity(AjaxBehaviorEvent event) {}

	//validate branch code 
	public String validateBranchCode(){
		System.out.println("branch code  :"+getBranchCode());
		List<BankBranch> branchList=getBankBranchDetailsService().checkBranchCode(getBranchCode().toString());
		if(branchList!=null&&branchList.size()>0){
			System.out.println("value there ");
			setBranchNameMsg("Branch Code Already found");
			return "Branch Code Already found";
		}
		else{
			System.out.println("Not there ");
			setBranchNameMsg("");
			return "";
		}

	}

	public void saveBankBranchdetails() throws Exception {

		//try {

		BankBranch bankBranch = new BankBranch();

		bankBranch.setBankBranchId(getPkBankBranch());
		if(getBranchCode()!=null){
			bankBranch.setBranchCode(new BigDecimal(getBranchCode()));
		}
		bankBranch.setBranchFullName(getFullName());
		bankBranch.setBranchShortName(getShortName());
		bankBranch.setAddress1(getAddress1());
		bankBranch.setAddress2(getAddress2());
		bankBranch.setZipCode(getZipCode());
		bankBranch.setTelephoneNo(getTelephoneNumber());
		bankBranch.setFaxNo(getFax());
		bankBranch.setEmail(getEmail());

		bankBranch.setContactPerson(getContactPerson());
		bankBranch.setContactDesg(getContactdesignation());
		bankBranch.setContactDept(getContactDepartment());
		bankBranch.setMicrCode(getMicrCode());
		bankBranch.setBranchFullNameAr(getFullNameLocal());
		bankBranch.setBranchShortNameAr(getShortNameLocal());
		bankBranch.setAddress1Ar(getAddress1Local());
		bankBranch.setAddress2Ar(getAddress2Local());
		bankBranch.setContactPersonAr(getContactPersonLocal());
		bankBranch.setContactDesgAr(getContactdesignationLocal());
		bankBranch.setContactDeptAr(getContactDepartmentLocal());
		bankBranch.setSwiftBic(getSwift());
		bankBranch.setDebitBranchId(getDebitBranchID());
		bankBranch.setBranchIfsc(getBranchIFSC());
		bankBranch.setRoutingId(getRoutingID());
		bankBranch.setMobile(getMobileNo());

		BankMaster bankMaster = new BankMaster();
		bankMaster.setBankId(getBankID());
		bankBranch.setExBankMaster(bankMaster);

		CountryMaster countryMaster = new CountryMaster();
		if(getCountryId()!=null && !getCountryId().equals("")){
			countryMaster.setCountryId(getCountryId());
			bankBranch.setFsCountryMaster(countryMaster);
		}
		if(getStateId()!=null && !getStateId().equals("")){
			StateMaster statemaster = new StateMaster();
			statemaster.setStateId(getStateId());
			bankBranch.setFsStateMaster(statemaster);
		}
		if(getDistrictId()!=null && !getDistrictId().equals("")){
			DistrictMaster districtMaster = new DistrictMaster();
			districtMaster.setDistrictId(getDistrictId());
			bankBranch.setFsDistrictMaster(districtMaster);
		}

		if(getCityId() != null && !getCityId().equals("") ){
			CityMaster cityMaster = new CityMaster();
			cityMaster.setCityId(getCityId());
			bankBranch.setFsCityMaster(cityMaster);
		}


		/*if(getPkBankBranch()!=null)
			{
				bankBranch.setIsactive(Constants.Yes);
			}else
			{
				bankBranch.setIsactive(Constants.U);
			}


			if(getPkBankBranch()!=null){
				bankBranch.setModifiedDate(new Date());
				bankBranch.setModifiedBy(sessionStateManage.getUserName());
				bankBranch.setCreatedBy(getCreatedby());
				bankBranch.setCreatedDate(getCreateddate());
			}else{

				bankBranch.setCreatedBy(sessionStateManage.getUserName());
				bankBranch.setCreatedDate(new Date());

			}*/

		if(isApproval()){
			bankBranch.setIsactive(Constants.Yes);
			bankBranch.setApprovedBy(sessionStateManage.getUserName());
			bankBranch.setApprovedDate(new Date());
			bankBranch.setModifiedDate(getModifiedDate());
			bankBranch.setModifiedBy(getModifiedBy());
			bankBranch.setCreatedBy(getCreatedby());
			bankBranch.setCreatedDate(getCreateddate());
		}else{
			bankBranch.setIsactive(Constants.U);
			if(getPkBankBranch()!=null){
				bankBranch.setModifiedDate(new Date());
				bankBranch.setModifiedBy(sessionStateManage.getUserName());
				bankBranch.setCreatedBy(getCreatedby());
				bankBranch.setCreatedDate(getCreateddate());
			}else{
				bankBranch.setCreatedBy(sessionStateManage.getUserName());
				bankBranch.setCreatedDate(new Date());
			}
		}
		
		//System IP Address.
		InetAddress ipAddress = InetAddress.getLocalHost();
		String ipAddr = ipAddress.getHostAddress().trim();
		bankBranch.setIpAddress(ipAddr);


		getBankBranchDetailsService().saveBankBranchDetail(bankBranch);




		/*} catch (Exception e) {

			log.info("Saving Bank Branch time Error:  "+e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}*/

	}

	/*	public void saveBankBranch() {
		if(checkSwift()==0){

			RequestContext.getCurrentInstance().execute("swiftmatch.show();");

		}else
		{
			saveBankBranchdetails();
			RequestContext.getCurrentInstance().execute("complete.show();");
		}

		//RequestContext.getCurrentInstance().execute("complete.show();");
	}*/

	public void saveBankBranch() {		

		//if(getSwift()==null ||getSwift()=="" ||getSwift().length()<=0){

		//System.out.println("Inside null");
		try{

			saveBankBranchdetails();
			RequestContext.getCurrentInstance().execute("complete.show();");
		}catch(NullPointerException ne){
			ne.printStackTrace();
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMsg("Method Name::saveDataTableRecods"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception e) {
			setErrorMsg(e.getMessage());
			log.info("Saving Bank Branch time Error:  "+e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
			return;
		}
		// CR 11-02-2016 hide by nazish
		/*}else if(checkSwift()==0){

			RequestContext.getCurrentInstance().execute("swiftmatch.show();");

		}else
		{
			try{
			saveBankBranchdetails();
			RequestContext.getCurrentInstance().execute("complete.show();");
			} catch (Exception e) {

				log.info("Saving Bank Branch time Error:  "+e);
				RequestContext.getCurrentInstance().execute("saveerror.show();");
			}
		}
		 */

		//RequestContext.getCurrentInstance().execute("complete.show();");
	}





	/**
	 * cancelBranchDetail
	 * @return
	 */
	public void cancelBranchDetail() {
		try{
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/employeehome.xhtml");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param bankBranchList
	 *            the bankBranchList to set
	 */
	public void setBankBranchList(List<BankBranch> bankBranchList) {

		// System.out.println("bankBranchList     :  "+bankBranchList.size());
		this.bankBranchList = bankBranchList;
	}

	/**
	 * @return the bankBranchList
	 */
	public List<BankBranch> getBankBranchList() {
		// bankBranchList=getBankBranchDetailsService().getBankBranchList();
		/*
		 * System.out.println("bankBranchList     :  "+bankBranchList.size());
		 * System.out.println("vvv vvv  :"+bankBranchList.size());
		 */
		return this.bankBranchList;
	}

	private BigDecimal ifscLengthByBank;

	public BigDecimal getIfscLengthByBank() {
		return ifscLengthByBank;
	}

	public void setIfscLengthByBank(BigDecimal ifscLengthByBank) {
		this.ifscLengthByBank = ifscLengthByBank;
	}

	public void getBank(){
		System.out.println("bank Id : "+getBankID());
		//lstbranchCode.clear();
		setPkBankBranch(null);
		setBranchCodeFullName(null);
		try{
			List<BankMaster> lstcountry = bankBranchDetailsService.getCountrybyBank(getBankID());

			setEditButton(true);
			setBooSystemBranchCode(false);
			setBooUpdateBranchCode(true);
			setdBbranchCode(null);
			setBranchCode(null);
			clearToInsertNewBranchCode();
			setBranchCodeFullName(null);
			setBankName(lstBankNames.get(getBankID()));

			//List<BankBranchView> lstbranch = bankBranchDetailsService.getBranchListfromView(getBankID());
			//	if(lstcountry.get(0).getRecordStatus().equalsIgnoreCase(Constants.Yes)){
			/*if(lstbranch.size()>1){
				setdBbranchCode(null);
				setBranchCode(null);
				//setLstbranchCode(lstbranch);
				setEditButton(true);
				setBooSystemBranchCode(true);
				setBooUpdateBranchCode(false);
				clearToInsertNewBranchCode();
				setBranchCodeFullName(null);
			}else if (lstbranch.size()==1) {
				//setLstbranchCode(lstbranch);
				setdBbranchCode(null);
				//setBranchCode(lstbranch.get(0).getBranchCode().toPlainString());
				setBranchCode(null);
				setEditButton(true);
				setBooSystemBranchCode(true);
				setBooUpdateBranchCode(false);
				clearToInsertNewBranchCode();
				setBranchCode(lstbranch.get(0).getBranchCode().toPlainString());
				setBranchCodeFullName(lstbranch.get(0).getBranchFullName());
			}else{
				setEditButton(false);
				setBooSystemBranchCode(false);
				setBooUpdateBranchCode(true);
				setdBbranchCode(null);
				setBranchCode(null);
				clearToInsertNewBranchCode();
				setBranchCodeFullName(null);
			}*/
			//}

			if(lstcountry.size()!=0){
				setLstbankcountry(lstcountry);
				setCountryId(getLstbankcountry().get(0).getFsCountryMaster().getCountryId());
				generateStateList();
				setIfscLengthByBank(getLstbankcountry().get(0).getIfscLen());
				if(getBranchCode()!=null){
					fetchData();
				}
				setCountryName(lstCountryNames.get(getLstbankcountry().get(0).getFsCountryMaster().getCountryId()));
			}
		} catch (Exception e) {

			log.info("fetching branch time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}

	/*
	 * public List<BankBranch> getBankBranchListInfo(){
	 * System.out.println("bankBranchList     :  "+bankBranchList.size());
	 * bankBranchList=getBankBranchDetailsService().getBankBranchList();
	 * System.out.println("bankBranchList     :  "+bankBranchList.size());
	 * return bankBranchList; }
	 */

	// Reset branch fields
	public String resetBankBranch() {
		setApproval(false);
		setFullName(null);
		setShortName(null);
		setShortName(null);
		setBranchCode(null);
		setdBbranchCode(null);
		setFullNameLocal(null);
		setShortNameLocal(null);
		setAddress1(null);
		setAddress2(null);
		setZipCode(null);
		setAddress1Local(null);
		setAddress2Local(null);
		setTelephoneNumber(null);
		setFax(null);
		setEmail(null);
		setContactDepartment(null);
		setContactDepartmentLocal(null);
		setContactPerson(null);
		setContactPersonLocal(null);
		setContactdesignation(null);
		setContactdesignationLocal(null);
		setSwift(null);
		setDebitBranchID(null);
		setBranchIFSC(null);
		setRoutingID(null);
		setMobileNo(null);
		setMicrCode(null);
		setCountryId(null);
		setCountryName(null);
		setStateId(null);
		setCityId(null);
		setDistrictId(null);
		setBankID(null);
		setBankName(null);
		setErrorMsg(null);
		setEditButton(false);
		setBooSystemBranchCode(false);
		setBooUpdateBranchCode(true);
		/*if(lstbranchCode.size()!=0){
			lstbranchCode.clear();
		}*/
		if(lstbankcountry.size()!=0){
			lstbankcountry.clear();
		}
		/*	if(lstStateList.size()!=0){
			lstStateList.clear();
		}
		if(lstDistrictList.size()!=0){
			lstDistrictList.clear();
		}
		if(lstCityList.size()!=0){
			lstCityList.clear();
		}*/
		if(bankBranchApprovalList.size()!=0){
			bankBranchApprovalList.clear();
		}

		System.out.println("reset END");

		return "";

	}

	public void fetchData() {

		if(getBranchCode() != null && !getBranchCode().equalsIgnoreCase("")){
			if(getdBbranchCode()!=null){
				setBranchCode(getdBbranchCode());
			}
			setLstBranchDataForDeactive(null);
			try{
				List<BankBranch> branchDataFromDb = getBankBranchDetailsService().getData(getBankID(),new BigDecimal(getBranchCode()));
				if(branchDataFromDb.size() > 0) {
					if(branchDataFromDb.get(0).getIsactive().equalsIgnoreCase(Constants.U) || branchDataFromDb.get(0).getIsactive().equalsIgnoreCase(Constants.Yes))
					{
						populateBranchDetails(branchDataFromDb);
					}else{
						setLstBranchDataForDeactive(branchDataFromDb);
						RequestContext.getCurrentInstance().execute("deactive.show();");
					}

				}else{
					clearToInsertNewBranchCode();
					setCountryId(getLstbankcountry().get(0).getFsCountryMaster().getCountryId());
					setCountryName(lstCountryNames.get(getLstbankcountry().get(0).getFsCountryMaster().getCountryId()));
					generateStateList();
				}
			}catch(NullPointerException ne){
				log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMsg("Method Name::fetchData"); 
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return; 
			} catch (Exception e) {

				log.info("Method Name::fetchData"+e);
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("saveerror.show();");
				return;
			}
		}
	}

	private void populateBranchDetails(List<BankBranch> branchDataFromDb)
	{
		try{
			setFullName(branchDataFromDb.get(0).getBranchFullName());
			setShortName(branchDataFromDb.get(0).getBranchShortName());
			setBranchCode(String.valueOf(branchDataFromDb.get(0).getBranchCode()));
			setFullNameLocal(branchDataFromDb.get(0).getBranchFullNameAr());
			setShortNameLocal(branchDataFromDb.get(0).getBranchShortNameAr());
			setAddress1(branchDataFromDb.get(0).getAddress1());
			setAddress2(branchDataFromDb.get(0).getAddress2());
			setZipCode(branchDataFromDb.get(0).getZipCode());
			setAddress1Local(branchDataFromDb.get(0).getAddress1Ar());
			setAddress2Local(branchDataFromDb.get(0).getAddress2Ar());
			setTelephoneNumber(branchDataFromDb.get(0).getTelephoneNo());
			setFax(branchDataFromDb.get(0).getFaxNo());
			setEmail(branchDataFromDb.get(0).getEmail());
			setContactDepartment(branchDataFromDb.get(0).getContactDept());
			setContactDepartmentLocal(branchDataFromDb.get(0).getContactDeptAr());
			setContactPerson(branchDataFromDb.get(0).getContactPerson());
			setContactPersonLocal(branchDataFromDb.get(0).getContactPersonAr());
			setContactdesignation(branchDataFromDb.get(0).getContactDesg());
			setContactdesignationLocal(branchDataFromDb.get(0).getContactDesgAr());
			setSwift(branchDataFromDb.get(0).getSwiftBic());
			setDebitBranchID(branchDataFromDb.get(0).getDebitBranchId());
			setBranchIFSC(branchDataFromDb.get(0).getBranchIfsc());
			setRoutingID(branchDataFromDb.get(0).getRoutingId());
			setMobileNo(branchDataFromDb.get(0).getMobile() );
			setMicrCode(branchDataFromDb.get(0).getMicrCode());
			setCountryId(branchDataFromDb.get(0).getFsCountryMaster().getCountryId());
			setCountryName(lstCountryNames.get(branchDataFromDb.get(0).getFsCountryMaster().getCountryId()));
			populateState();
			if(branchDataFromDb.get(0).getFsStateMaster() != null){
				setStateId(branchDataFromDb.get(0).getFsStateMaster().getStateId());
			}
			populateDistrict();
			if(branchDataFromDb.get(0).getFsDistrictMaster() != null){
				setDistrictId(branchDataFromDb.get(0).getFsDistrictMaster().getDistrictId());
			}
			populateCity();
			if(branchDataFromDb.get(0).getFsCityMaster()!=null){
				setCityId(branchDataFromDb.get(0).getFsCityMaster().getCityId());
			}

			setBankID(branchDataFromDb.get(0).getExBankMaster().getBankId());
			setBankName(lstBankNames.get(branchDataFromDb.get(0).getExBankMaster().getBankId()));
			setPkBankBranch(branchDataFromDb.get(0).getBankBranchId());
			setCreatedby(branchDataFromDb.get(0).getCreatedBy());
			setCreateddate(branchDataFromDb.get(0).getCreatedDate());
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMsg("Method Name::fetchData"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception e) {

			log.info("fetching all details time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
			return; 
		}
	}
	public void deactiveYes()
	{
		List<BankBranch> branchDataFromDb=getLstBranchDataForDeactive();
		populateBranchDetails(branchDataFromDb);
	}
	public void deactiveNo()
	{
		setdBbranchCode(null);
		setBranchCode(null);
		clearToInsertNewBranchCode();
	}
	public void clearToInsertNewBranchCode(){
		System.out.println("NO Values$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4");
		setPkBankBranch(null);
		setFullName(null);
		setShortName(null);
		setShortName(null);
		//	setBranchCode("");
		setFullNameLocal(null);
		setShortNameLocal(null);
		setAddress1(null);
		setAddress2(null);
		setZipCode(null);
		setAddress1Local(null);
		setAddress2Local(null);
		setTelephoneNumber(null);
		setFax(null);
		setEmail(null);
		setContactDepartment(null);
		setContactDepartmentLocal(null);
		setContactPerson(null);
		setContactPersonLocal(null);
		setContactdesignation(null);
		setContactdesignationLocal(null);
		setSwift(null);
		setDebitBranchID(null);
		setBranchIFSC(null);
		setRoutingID(null);
		setMobileNo(null);
		setMicrCode(null);
		setStateId(null);
		setCityId(null);
		setDistrictId(null);
		setCreatedby(null);
		setCreateddate(null);
		//	setBankID(null);
		System.out.println("reset END");

	}

	/**
	 * This method is responsible to supply, behavior of the component,calling
	 * from page
	 * 
	 * @param componentName
	 * @param type
	 * @return
	 */


	public void branchInfoPanelNext() {
		setIsBranchInfo(false);
		setIsBranchLocalAddressPanel(true);
		setIsBranchTransactionPanel(false);

	}

	public void branchLocalAddressPanelBack() {
		setIsBranchInfo(true);
		setIsBranchLocalAddressPanel(false);
		setIsBranchTransactionPanel(false);
	}

	public void branchLocalAddressPanelNext() {
		setIsBranchInfo(false);
		setIsBranchLocalAddressPanel(false);
		setIsBranchTransactionPanel(true);

		/*List<BankMaster> bankmasterinfo = bankBranchDetailsService.getIFSCLength(getBankID());
		if(bankmasterinfo.size()!=0){
			setBranchIFSC(bankmasterinfo.get(0).getIfscLen().toString());
		}*/
	}

	public void barnchTransactionBack() {
		setIsBranchInfo(false);
		setIsBranchLocalAddressPanel(true);
		setIsBranchTransactionPanel(false);

	}


	/*
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

	public BigDecimal getPkBankBranch() {
		return pkBankBranch;
	}

	public void setPkBankBranch(BigDecimal pkBankBranch) {
		this.pkBankBranch = pkBankBranch;
	}  

	public void insertNewBranchCode() {
		BigDecimal maxBranchCode=bankBranchDetailsService.toFetchMaxBranchCodeOfBranchFromBank(getBankID());
		setBooSystemBranchCode(false);
		setBooUpdateBranchCode(true);
		setEditButton(false);
		setdBbranchCode(null);
		setBranchCode(null);
		clearToInsertNewBranchCode();
		if(maxBranchCode != null){
			BigDecimal newBranchCode= null;
			newBranchCode=maxBranchCode.add(new BigDecimal(1));
			//setErrorMsg("Max BranchCode of the Bank is :"+maxBranchCode);
			setErrorMsg("New BranchCode of the Bank is :"+newBranchCode);
			RequestContext.getCurrentInstance().execute("maxBranchCode.show();");
			return;
		}
	}

	public Boolean getEditButton() {
		return editButton;
	}

	public void setEditButton(Boolean editButton) {
		this.editButton = editButton;
	}

	int i=0;

	public void searchingIFSCcodebyBank(){
		try{
			setBranchFullName(null);
			setBankName(null);
			if(getBranchIFSC()!=null && !getBranchIFSC().equals("")){

				BigDecimal branchIFSCLength = new BigDecimal(getBranchIFSC().length());
				boolean ifcDup = false;
				if(getIfscLengthByBank()!=null){
					if(branchIFSCLength.compareTo(getIfscLengthByBank())==0){
						//List<BankBranch> bankBranch = bankBranchDetailsService.checkIfsc(getBranchIFSC());
						List<BankBranch> bankBranch = bankBranchDetailsService.checkIfscByBank(getBankID(),getBranchIFSC());
						/*
				System.out.println("ifsc code "+getBranchIFSC());
				if(getLstbranchCode().size()!=0){
					for(BankBranch lstofBranch : getLstbranchCode()){
						if(lstofBranch.getExBankMaster().getBankId().compareTo(getBankID())==0){
							if(new BigDecimal(getBranchCode()).compareTo(lstofBranch.getBranchCode())==0){
								if(getBranchIFSC().equals(lstofBranch.getBranchIfsc())){
									i=0;
									break;
								}
							}else{
								if(getBranchIFSC().equals(lstofBranch.getBranchIfsc())){
									i=1;
									break;
								}else{
									i=0;
								}
							}
						}
					}*/
						if(bankBranch.size()>0){
							for(BankBranch  bank:bankBranch){
								if(getPkBankBranch()!=null && getPkBankBranch().intValue() == bank.getBankBranchId().intValue()){
									ifcDup = false;
									break;
								}else{
									ifcDup = true;
									String branchName=bankBranchDetailsService.toFetchBranchName(bank.getBankBranchId());
									if(bank.getExBankMaster() != null){
										String bankName=generalService.getBankName(bank.getExBankMaster().getBankId());
										setBankName(bankName);
										setBranchFullName(branchName);
									}
								}
							}

						}

						if(ifcDup){
							setBranchIFSC(null);
							RequestContext.getCurrentInstance().execute("clearifsc.show();");

						}

					}
					else{
						setBranchIFSC(null);
						//setIfscLengthByBank(branchIFSCLength);
						RequestContext.getCurrentInstance().execute("ifsclength.show();");
					}
				} else{
					RequestContext.getCurrentInstance().execute("ifsclengthnotexisted.show();");
				}
			}

		} catch (Exception e) {
			log.info("searching IFSC time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}

	}

	public void clickOnOK(){
		resetBankBranch();

		try{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch(Exception e) {
			System.out.println(e);
		}

	} 




	public void  checkSwift() {
		//int i = 0;
		System.out.println("Entering into checkSwift Bank Branch bean  "+getSwift());
		setErrorMsg(null);
		try{
			boolean swiftCheck = false;
			List<String> swifList = new ArrayList<String>();
			if(getSwift()!=null && getSwift()!=""){
				swifList = getBankBranchDetailsService().checkSwift(getSwift());

				System.out.println("The size of swifList -- " + swifList.size());

				if(swifList!=null && swifList.size()>0){
					for (String swifList1 : swifList) {
						System.out.println(" Swift Values from DB KKKKKKKK   -->  "
								+ swifList1 + "\tSwift value from Form KKKKKKKK  --> "+ getSwift());
						if (swifList1.equals(getSwift())) {
							System.out.println("Insid if equals");
							//i = 1;
							swiftCheck = false;
							break;
						} else {
							System.out.println("Insid else not equals");
							//i = 0;
							swiftCheck = true;

						}
					}
				}else{
					swiftCheck = true;
				}
			}
			if(getSwift()!=null && swiftCheck){
				setErrorMsg(getSwift());
				setSwift(null);
				RequestContext.getCurrentInstance().execute("swiftcheck.show();");

				return; 
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMsg("Method Name::fetchData"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception e) {
			log.info("fetching all details time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
			return; 
		}
		//return i;

	}



	//swiftmatch



	public void updateSwiftYes(){
		System.out.println("Entering into updateSwiftYes method");


		try {


			BankBranch bankBranch = new BankBranch();

			bankBranch.setBankBranchId(getPkBankBranch());

			bankBranch.setBranchCode(new BigDecimal(getBranchCode()));

			bankBranch.setBranchFullName(getFullName());
			bankBranch.setBranchShortName(getShortName());
			bankBranch.setAddress1(getAddress1());
			bankBranch.setAddress2(getAddress2());
			bankBranch.setZipCode(getZipCode());
			bankBranch.setTelephoneNo(getTelephoneNumber());
			bankBranch.setFaxNo(getFax());
			bankBranch.setEmail(getEmail());

			bankBranch.setContactPerson(getContactPerson());
			bankBranch.setContactDesg(getContactdesignation());
			bankBranch.setContactDept(getContactDepartment());
			bankBranch.setMicrCode(getMicrCode());
			bankBranch.setBranchFullNameAr(getFullNameLocal());
			bankBranch.setBranchShortNameAr(getShortNameLocal());
			bankBranch.setAddress1Ar(getAddress1Local());
			bankBranch.setAddress2Ar(getAddress2Local());
			bankBranch.setContactPersonAr(getContactPersonLocal());
			bankBranch.setContactDesgAr(getContactdesignationLocal());
			bankBranch.setContactDeptAr(getContactDepartmentLocal());

			//bankBranch.setSwiftBic(getSwift());

			bankBranch.setSwiftBic(null);

			bankBranch.setDebitBranchId(getDebitBranchID());
			bankBranch.setBranchIfsc(getBranchIFSC());
			bankBranch.setRoutingId(getRoutingID());
			bankBranch.setMobile(getMobileNo());

			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(getBankID());
			bankBranch.setExBankMaster(bankMaster);

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(getCountryId());
			bankBranch.setFsCountryMaster(countryMaster);

			StateMaster statemaster = new StateMaster();
			statemaster.setStateId(getStateId());
			bankBranch.setFsStateMaster(statemaster);

			DistrictMaster districtMaster = new DistrictMaster();
			districtMaster.setDistrictId(getDistrictId());
			bankBranch.setFsDistrictMaster(districtMaster);

			if(getCityId() != null && !getCityId().equals("") ){
				CityMaster cityMaster = new CityMaster();
				cityMaster.setCityId(getCityId());
				bankBranch.setFsCityMaster(cityMaster);
			}

			bankBranch.setIsactive(Constants.U);

			if(getPkBankBranch()!=null){
				bankBranch.setModifiedDate(new Date());
				bankBranch.setModifiedBy(sessionStateManage.getUserName());
				bankBranch.setCreatedBy(getCreatedby());
				bankBranch.setCreatedDate(getCreateddate());
			}else{

				bankBranch.setCreatedBy(sessionStateManage.getUserName());
				bankBranch.setCreatedDate(new Date());

			}

			if(isApproval()==true){
				bankBranch.setApprovedBy(sessionStateManage.getUserName());
				bankBranch.setApprovedDate(new Date());
			}
			getBankBranchDetailsService().saveBankBranchDetail(bankBranch);

			RequestContext.getCurrentInstance().execute("complete.show();");


		} catch (Exception e) {

			log.info("Saving Bank Branch time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}



	}



	public void updateSwiftNo(){
		System.out.println("Entering into updateSwiftNo method");


		try{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch(Exception e) {
			System.out.println(e);
		}	

	}

	// Added by kani end


	/* Bank Branch Deatails approval code VISWA@@@ */

	@Autowired
	IBankApprovalService bankApprovalService;

	public List<BankBranchDataTable> getBankBranchActivationList() {
		return bankBranchActivationList;
	}

	public void setBankBranchActivationList(
			List<BankBranchDataTable> bankBranchActivationList) {
		this.bankBranchActivationList = bankBranchActivationList;
	}

	private boolean approval=false;
	private String createdBy = null;
	private Date createdDate = null;
	private String remarks=null;

	private String isStatus = null;
	private String dynamicLabelForActivateDeactivate=null;


	private List<BankBranchDataTable> bankBranchApprovalList = new ArrayList<BankBranchDataTable>();
	private List<BankBranchDataTable> bankBranchActivationList = new ArrayList<BankBranchDataTable>();
	private List<BankBranchDataTable> bankBranchApprovalSelectedList = new ArrayList<BankBranchDataTable>();

	public void bankBranchApprovalNavigation() {
		//resetBankBranch();
		setIsBranchInfo(true);
		setIsBranchLocalAddressPanel(false);
		setIsBranchTransactionPanel(false);
		bankBranchApprovalList.clear();
		bankBranchApprovalSelectedList.clear();
		setApproval(true);
		toFetchApprovalAllRecords();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankbranchapprovallist.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankbranchapprovallist.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<BankBranchDataTable> getBankBranchApprovalList() {/*

		bankBranchApprovalList.clear();
		BankBranchDataTable bankBranchDataTable = null;
		try{
		List<BankBranch> bankbranchapprovallist = bankApprovalService.getBankBranchListForApproval();

		for (BankBranch bankBranch : bankbranchapprovallist) {
			bankBranchDataTable = new BankBranchDataTable();

			bankBranchDataTable.setBankID(bankBranch.getExBankMaster().getBankId());
			bankBranchDataTable.setBankFullName(bankBranch.getExBankMaster().getBankFullName());
			bankBranchDataTable.setBranchCode(bankBranch.getBranchCode().toString());
			bankBranchDataTable.setFullName(bankBranch.getBranchFullName());
			bankBranchDataTable.setShortName(bankBranch.getBranchShortName());
			bankBranchDataTable.setEmail(bankBranch.getEmail());
			bankBranchDataTable.setCreatedby(bankBranch.getCreatedBy());
			bankBranchDataTable.setCreatedDate(bankBranch.getCreatedDate());
			bankBranchDataTable.setApprovedBy(bankBranch.getApprovedBy());
			bankBranchDataTable.setApprovedDate(bankBranch.getApprovedDate());
			bankBranchDataTable.setBankCode(bankBranch.getExBankMaster().getBankCode());
			bankBranchDataTable.setLocation(bankBranch.getAddress1());
			bankBranchDataTable.setFullNameLocal(bankBranch.getBranchFullNameAr());
			bankBranchDataTable.setBranchIFSC(bankBranch.getBranchIfsc());
			bankBranchDataTable.setBranchPk(bankBranch.getBankBranchId());
			if(bankBranch.getFsCountryMaster()!=null){
			bankBranchDataTable.setCountryId(bankBranch.getFsCountryMaster().getCountryId());
			}
			if(bankBranch.getFsStateMaster()!=null){
			bankBranchDataTable.setStateId(bankBranch.getFsStateMaster().getStateId());
			}
			if(bankBranch.getFsDistrictMaster()!=null){
			bankBranchDataTable.setDistrictId(bankBranch.getFsDistrictMaster().getDistrictId());
			}
			if(bankBranch.getFsCityMaster()!=null){
			bankBranchDataTable.setCityId(bankBranch.getFsCityMaster().getCityId());
			}
			bankBranchDataTable.setAddress1(bankBranch.getAddress1());
			bankBranchDataTable.setAddress2(bankBranch.getAddress2());
			bankBranchDataTable.setAddress1Local(bankBranch.getAddress1Ar());
			bankBranchDataTable.setAddress2Local(bankBranch.getAddress2Ar());
			bankBranchDataTable.setContactDepartment(bankBranch.getContactDept());
			bankBranchDataTable.setContactDepartmentLocal(bankBranch.getContactDeptAr());
			bankBranchDataTable.setContactdesignation(bankBranch.getContactDesg());
			bankBranchDataTable.setContactdesignationLocal(bankBranch.getContactDesgAr());
			bankBranchDataTable.setContactPerson(bankBranch.getContactPerson());
			bankBranchDataTable.setContactPersonLocal(bankBranch.getContactPersonAr());
			bankBranchDataTable.setFax(bankBranch.getFaxNo());
			bankBranchDataTable.setMobileNo(bankBranch.getMobile());
			bankBranchDataTable.setMicrCode(bankBranch.getMicrCode());
			bankBranchDataTable.setDebitBranchID( bankBranch.getDebitBranchId());
			bankBranchDataTable.setRoutingID(bankBranch.getRoutingId());
			bankBranchDataTable.setSwift(bankBranch.getSwiftBic());
			bankBranchDataTable.setTelephoneNumber(bankBranch.getTelephoneNo());
			bankBranchDataTable.setZipCode(bankBranch.getZipCode());
			bankBranchDataTable.setShortName(bankBranch.getBranchShortName());
			bankBranchDataTable.setShortNameLocal(bankBranch.getBranchShortNameAr());
			bankBranchDataTable.setModifiedBy(bankBranch.getModifiedBy());
			bankBranchDataTable.setModifiedDate(bankBranch.getModifiedDate());

			bankBranchApprovalList.add(bankBranchDataTable);
		}
		} catch (Exception e) {
			log.info("fetching list for approval time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}*/

		return bankBranchApprovalList;
	}

	public void setBankBranchApprovalList(
			List<BankBranchDataTable> bankBranchApprovalList) {
		this.bankBranchApprovalList = bankBranchApprovalList;
	}



	public void gotoBankBranchApproval(BankBranchDataTable datatable) {
		//if(CommonUtil.validateApprovedBy(sessionStateManage.getUserName(), datatable.getCreatedby())==true){
		if((datatable.getModifiedBy()==null?datatable.getCreatedby():datatable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())){
			/*	if(datatable.getCreatedBy().equalsIgnoreCase(sessionStateManage.getUserName())){*/
			RequestContext.getCurrentInstance().execute("unapprove.show();");
		}else{
			resetBankBranch();
			setIsBranchInfo(true);
			setIsBranchLocalAddressPanel(false);
			setIsBranchTransactionPanel(false);
			setApproval(true);
			bankBranchApprovalList.clear();
			try {

				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../treasury/bankbranchapproval.xhtml");
				fetchDataForApproval(datatable);
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}

	private String modifiedBy;
	private Date modifiedDate;

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

	public void fetchDataForApproval(BankBranchDataTable data){

		try{
			/*List<BankBranch> branchDataFromDb = getBankBranchDetailsService().getData(data.getBankID(),new BigDecimal(data.getBranchCode()));
		if(branchDataFromDb.size() > 0) {
			try{

			setFullName(branchDataFromDb.get(0).getBranchFullName());
			setShortName(branchDataFromDb.get(0).getBranchShortName());
			setBranchCode(String.valueOf(branchDataFromDb.get(0).getBranchCode()));
			setFullNameLocal(branchDataFromDb.get(0).getBranchFullNameAr());
			setShortNameLocal(branchDataFromDb.get(0).getBranchShortNameAr());
			setAddress1(branchDataFromDb.get(0).getAddress1());
			setAddress2(branchDataFromDb.get(0).getAddress2());
			setZipCode(branchDataFromDb.get(0).getZipCode());
			setAddress1Local(branchDataFromDb.get(0).getAddress1Ar());
			setAddress2Local(branchDataFromDb.get(0).getAddress2Ar());
			setTelephoneNumber(branchDataFromDb.get(0).getTelephoneNo());
			setFax(branchDataFromDb.get(0).getFaxNo());
			setEmail(branchDataFromDb.get(0).getEmail());
			setContactDepartment(branchDataFromDb.get(0).getContactDept());
			setContactDepartmentLocal(branchDataFromDb.get(0).getContactDeptAr());
			setContactPerson(branchDataFromDb.get(0).getContactPerson());
			setContactPersonLocal(branchDataFromDb.get(0).getContactPersonAr());
			setContactdesignation(branchDataFromDb.get(0).getContactDesg());
			setContactdesignationLocal(branchDataFromDb.get(0).getContactDesgAr());
			setSwift(branchDataFromDb.get(0).getSwiftBic());
			setDebitBranchID(branchDataFromDb.get(0).getDebitBranchId());
			setBranchIFSC(branchDataFromDb.get(0).getBranchIfsc());
			setRoutingID(branchDataFromDb.get(0).getRoutingId());
			setMobileNo(branchDataFromDb.get(0).getMobile() );
			setMicrCode(branchDataFromDb.get(0).getMicrCode());
			populateCountryList();
			setCountryId(branchDataFromDb.get(0).getFsCountryMaster().getCountryId());
			populateState();
			setStateId(branchDataFromDb.get(0).getFsStateMaster().getStateId());
			populateDistrict();
			setDistrictId(branchDataFromDb.get(0).getFsDistrictMaster().getDistrictId());
			populateCity();
			if(branchDataFromDb.get(0).getFsCityMaster()!=null){
				setCityId(branchDataFromDb.get(0).getFsCityMaster().getCityId());
			}
			getBankNameList();
			setBankID(branchDataFromDb.get(0).getExBankMaster().getBankId());
			setPkBankBranch(branchDataFromDb.get(0).getBankBranchId());
			setCreatedby(branchDataFromDb.get(0).getCreatedBy());
			setCreateddate(branchDataFromDb.get(0).getCreatedDate());
			setModifiedBy(branchDataFromDb.get(0).getModifiedBy());
			setModifiedDate(branchDataFromDb.get(0).getModifiedDate());

	          } catch (Exception e) {

				log.info("Saving Bank Branch time Error:  "+e);
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("saveerror.show();");
			}

		}else{
			clearToInsertNewBranchCode();
			setCountryId(getLstbankcountry().get(0).getFsCountryMaster().getCountryId());
			generateStateList();
		}*/

			//added by nazish for fine tuning



			setFullName(data.getFullName());
			setShortName(data.getShortName());
			setBranchCode(String.valueOf(data.getBranchCode()));
			setFullNameLocal(data.getFullNameLocal());
			setShortNameLocal(data.getShortNameLocal());
			setAddress1(data.getAddress1());
			setAddress2(data.getAddress2());
			setZipCode(data.getZipCode());
			setAddress1Local(data.getAddress1Local());
			setAddress2Local(data.getAddress2Local());
			setTelephoneNumber(data.getTelephoneNumber());
			setFax(data.getFax());
			setEmail(data.getEmail());
			setContactDepartment(data.getContactDepartment());
			setContactDepartmentLocal(data.getContactDepartmentLocal());
			setContactPerson(data.getContactPerson());
			setContactPersonLocal(data.getContactPersonLocal());
			setContactdesignation(data.getContactdesignation());
			setContactdesignationLocal(data.getContactdesignationLocal());
			setSwift(data.getSwift());
			setDebitBranchID(data.getDebitBranchID());
			setBranchIFSC(data.getBranchIFSC());
			setRoutingID(data.getRoutingID());
			setMobileNo(data.getMobileNo() );
			setMicrCode(data.getMicrCode());
			if(data.getCountryId()!=null){
				populateCountryList();
				setCountryId(data.getCountryId());
				setCountryName(lstCountryNames.get(data.getCountryId()));
			}
			if(data.getStateId()!=null){
				populateState();
				setStateId(data.getStateId());
			}
			if(data.getDistrictId()!=null);
			{
				populateDistrict();
				setDistrictId(data.getDistrictId());
			}
			if(data.getCityId()!=null){
				populateCity();
				setCityId(data.getCityId());
			}
			getBankNameList(); 
			setBankID(data.getBankID());
			setBankName(lstBankNames.get(data.getBankID()));
			setPkBankBranch(data.getBranchPk());
			setCreatedby(data.getCreatedby());
			setCreateddate(data.getCreatedDate());
			setModifiedBy(data.getModifiedBy());
			setModifiedDate(data.getModifiedDate());
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMsg("Method Name::fetchDataForApproval"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 

		} catch (Exception e) {

			log.info("Method Name::fetchDataForApproval:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
			return;
		}
	}

	public void cancelFromApproval() {
		resetBankBranch();
		bankBranchApprovalList.clear();
		try{
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../treasury/bankbranchapprovallist.xhtml");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void approve(){
		try{
			//saveBankBranchdetails();
			bankBranchDetailsService.toApproveRecordsBankBranch(getPkBankBranch(),sessionStateManage.getUserName());

			// START OF PROCEDURE CALL
			HashMap<String, String>  approveRecord = new HashMap<String, String>();

			String bankCode= generalService.getBankCode(getBankID() );
			approveRecord.put("BANK_CODE",bankCode);
			approveRecord.put("BANK_ID",getBankID().toPlainString());
			approveRecord.put("BANK_BRANCH_ID",getPkBankBranch().toPlainString());
			approveRecord.put("BANK_BRANCH_CODE",getBranchCode());
			HashMap<String, String>   ouputValues =getBankBranchDetailsService().callPopulateBankBranch(approveRecord);

			if(ouputValues.get("P_ERROR_MESSAGE")!=null){
				setErrorMsg(ouputValues.get("P_ERROR_MESSAGE"));
				bankBranchDetailsService.unapproveProcedureExcep(getPkBankBranch());
				RequestContext.getCurrentInstance().execute("procedure.show();");
				return ; 
			}else{
				RequestContext.getCurrentInstance().execute("approve.show();");
			}

		} catch (Exception e) {
			log.info("Approval Bank Branch time Error:  "+e);
			setErrorMsg(e.getMessage());
			bankBranchDetailsService.unapproveProcedureExcep(getPkBankBranch());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}

	public void clickOnOKApprove() throws IOException{
		resetBankBranch();
		bankBranchApprovalList.clear();
		toFetchApprovalAllRecords();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankbranchapprovallist.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkIFSCLength(){


	}

	public void cancel() throws IOException {
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


	public void bankBranchActiveNavigation() {
		bankBranchActivationList.clear();
		//	getAlldata();
		populateBank();
		//setApproval(true);
		setBankID(null);
		setLstBankList(null);
		setBankName(null);
		setBranchFullNameForSearch(null);
		setIfscCodeForSearch(null );
		setRenderDataTable(false);
		setSearchBranchName(null);
		setSearchBranchIfsc(null);
		setSearchSwiftIfsc(null);
		setBooRenderDatatable(null);
		setCountryId(null);
		try {
			List<CountryMasterDesc> countryList = bankBranchUploadService.getAllCountryList(sessionStateManage.getLanguageId());
			if(countryList!=null && countryList.size() > 0) {
				setAllCountryList(countryList);
			}
			
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankbranchactiveinactivelist.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../treasury/bankbranchactiveinactivelist.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void pageNavigation()
	{
		try {

			setBooRenderDatatable(null);
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../treasury/bankbranchactiveinactivelist.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getActiveInactive(BankBranchDataTable datatable) {
		setBankID(datatable.getBankID());
		setBankName(lstBankNames.get(datatable.getBankID()));
		setBranchCode(datatable.getBranchCode());
		setCreatedBy(datatable.getCreatedBy());
		setCreatedDate(datatable.getCreatedDate());
		datatable.setIsSelected(true);

		if(datatable.getIsStatus().equalsIgnoreCase(Constants.Yes)){
			RequestContext.getCurrentInstance().execute("remarks.show();");
		}
		if(datatable.getIsStatus().equalsIgnoreCase(Constants.D)){
			RequestContext.getCurrentInstance().execute("unupprove.show();");
		}

	}

	public void updateRemarks(){


		List<BankBranch> branchDataFromDb = getBankBranchDetailsService().getData(getBankID(),new BigDecimal(getBranchCode()));
		BankBranch bankBranch = new BankBranch();
		if(branchDataFromDb.size() > 0) {

			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(getBankID());
			bankBranch.setExBankMaster(bankMaster);	

			bankBranch.setBankBranchId(branchDataFromDb.get(0).getBankBranchId());

			bankBranch.setBranchFullName(branchDataFromDb.get(0).getBranchFullName());
			bankBranch.setBranchShortName(branchDataFromDb.get(0).getBranchShortName());
			bankBranch.setBranchCode(branchDataFromDb.get(0).getBranchCode());
			bankBranch.setBranchFullNameAr(branchDataFromDb.get(0).getBranchFullNameAr());
			bankBranch.setBranchShortNameAr(branchDataFromDb.get(0).getBranchShortNameAr());
			bankBranch.setAddress1(branchDataFromDb.get(0).getAddress1());
			bankBranch.setAddress2(branchDataFromDb.get(0).getAddress2());
			bankBranch.setZipCode(branchDataFromDb.get(0).getZipCode());
			bankBranch.setAddress1Ar(branchDataFromDb.get(0).getAddress1Ar());
			bankBranch.setAddress2Ar(branchDataFromDb.get(0).getAddress2Ar());
			bankBranch.setTelephoneNo(branchDataFromDb.get(0).getTelephoneNo());
			bankBranch.setFaxNo(branchDataFromDb.get(0).getFaxNo());
			bankBranch.setEmail(branchDataFromDb.get(0).getEmail());
			bankBranch.setContactDept(branchDataFromDb.get(0).getContactDept());
			bankBranch.setContactPersonAr(branchDataFromDb.get(0).getContactDeptAr());
			bankBranch.setContactPerson(branchDataFromDb.get(0).getContactPerson());
			bankBranch.setContactPersonAr(branchDataFromDb.get(0).getContactPersonAr());
			bankBranch.setContactDesg(branchDataFromDb.get(0).getContactDesg());
			bankBranch.setContactDesgAr(branchDataFromDb.get(0).getContactDesgAr());
			bankBranch.setSwiftBic(branchDataFromDb.get(0).getSwiftBic());
			bankBranch.setDebitBranchId(branchDataFromDb.get(0).getDebitBranchId());
			bankBranch.setBranchIfsc(branchDataFromDb.get(0).getBranchIfsc());
			bankBranch.setRoutingId(branchDataFromDb.get(0).getRoutingId());
			bankBranch.setMobile(branchDataFromDb.get(0).getMobile() );
			bankBranch.setMicrCode(branchDataFromDb.get(0).getMicrCode());

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(branchDataFromDb.get(0).getFsCountryMaster().getCountryId());
			bankBranch.setFsCountryMaster(countryMaster);

			StateMaster stateMaster = new StateMaster();
			stateMaster.setStateId(branchDataFromDb.get(0).getFsStateMaster().getStateId());
			bankBranch.setFsStateMaster(stateMaster);

			DistrictMaster districtMaster = new DistrictMaster();
			districtMaster.setDistrictId(branchDataFromDb.get(0).getFsDistrictMaster().getDistrictId());
			bankBranch.setFsDistrictMaster(districtMaster);

			if(branchDataFromDb.get(0).getFsCityMaster()!=null){
				CityMaster cityMaster = new CityMaster();
				cityMaster.setCityId(branchDataFromDb.get(0).getFsCityMaster().getCityId());
				bankBranch.setFsCityMaster(cityMaster);
			}

			//setBankID(branchDataFromDb.get(0).getExBankMaster().getBankId());
			setPkBankBranch(branchDataFromDb.get(0).getBankBranchId());

			if(branchDataFromDb.get(0).getIsactive().equals(Constants.Yes)){
				bankBranch.setRemarks(getRemarks());
				bankBranch.setApprovedBy(null);
				bankBranch.setApprovedDate(null);
				bankBranch.setIsactive(Constants.D);
			}
			if(branchDataFromDb.get(0).getIsactive().equals(Constants.D)){
				bankBranch.setApprovedBy(null);
				bankBranch.setApprovedDate(null);
				bankBranch.setIsactive(Constants.U);
				bankBranch.setRemarks(null);
			}

			bankBranchDetailsService.saveBankBranchDetail(bankBranch);
			bankBranchActivationList.remove(bankBranch);
		}
	}

	public void clickOnOKActivate() throws IOException{
		bankBranchActivationList.clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankbranchactiveinactivelist.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnOKDeActivate() throws IOException{
		bankBranchActivationList.clear();

		try {
			updateRemarks();
			/*	getAlldata(getBankID(),getBranchFullNameForSearch());*/
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankbranchactiveinactivelist.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exit() throws IOException {
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
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

	public String getIsStatus() {
		return isStatus;
	}

	public void setIsStatus(String isStatus) {
		this.isStatus = isStatus;
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

	/* Bank Branch Deatails approval code VISWA@@@ */

	//view for branch
	public void getAlldata(BigDecimal bankId, String  fullname){
		bankBranchActivationList.clear();
		BankBranchDataTable bankBranchDataTable = null;

		List<BankBranch> bankbranchActivelist = bankApprovalService.getBankBranchListForActiveInactive(bankId,fullname,getIfscCodeForSearch());
		if(bankbranchActivelist.size()>0){
			setRenderDataTable(true);
			for (BankBranch bankBranch : bankbranchActivelist) {
				bankBranchDataTable = new BankBranchDataTable();

				bankBranchDataTable.setBankID(bankBranch.getExBankMaster().getBankId());
				bankBranchDataTable.setBankCode(bankBranch.getExBankMaster().getBankCode());
				bankBranchDataTable.setBankFullName(bankBranch.getExBankMaster().getBankFullName());
				bankBranchDataTable.setBranchCode(bankBranch.getBranchCode().toString());
				bankBranchDataTable.setFullName(bankBranch.getBranchFullName());
				bankBranchDataTable.setShortName(bankBranch.getBranchShortName());
				bankBranchDataTable.setEmail(bankBranch.getEmail());
				bankBranchDataTable.setCreatedBy(bankBranch.getCreatedBy());
				bankBranchDataTable.setCreatedDate(bankBranch.getCreatedDate());
				bankBranchDataTable.setIsStatus(bankBranch.getIsactive());
				bankBranchDataTable.setBranchPk(bankBranch.getBankBranchId());
				bankBranchDataTable.setBranchIFSC(bankBranch.getBranchIfsc());
				bankBranchDataTable.setSwift(bankBranch.getSwiftBic());
				if(bankBranch.getIsactive().equalsIgnoreCase(Constants.Yes)){
					bankBranchDataTable.setRecordStatus(Constants.ACTIVATE );
					bankBranchDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				}else if(bankBranch.getIsactive().equalsIgnoreCase(Constants.D)){
					bankBranchDataTable.setRecordStatus(Constants.IN_ACTIVE );
					bankBranchDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				} else if (bankBranch.getIsactive().equalsIgnoreCase(Constants.U)) {
					bankBranchDataTable.setRecordStatus(Constants.UNAPPROVED);
					bankBranchDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				}



				bankBranchActivationList.add(bankBranchDataTable);
			}
		}else{
			RequestContext.getCurrentInstance().execute("noRecords.show();");
			setRenderDataTable(false);
		}
	}

	private Boolean renderDataTable=false;



	//bank branch activate in active filtering

	public Boolean getRenderDataTable() {
		return renderDataTable;
	}

	public void setRenderDataTable(Boolean renderDataTable) {
		this.renderDataTable = renderDataTable;
	}

	public void filterBankBranchData(){
		//populateBranch();
		setSearchBranchName(null);
		setSearchBranchIfsc(null);
		setSearchSwiftIfsc(null);
		setBooRenderDatatable(null);
		if(getBankID()!=null){
			try{
				setSearchBranchName(null);
				setSearchBranchIfsc(null);
				setSearchSwiftIfsc(null);
				setRenderDataTable(true);
				//	getAlldata(getBankID(),getBranchFullNameForSearch());
			} catch (Exception e) {
				log.info("fetching active inactive list time Error:  "+e);
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("saveerror.show();");
			}
		}else{
			setRenderDataTable(false);
		}
	}

	public void filterBankBranchDataforbank(){
		setBranchFullNameForSearch(null);
		setIfscCodeForSearch(null);
		branchList.clear();
		populateBranch();
		if(getBankID()!=null){
			try{
				getAlldata(getBankID(),getBranchFullNameForSearch());
			} catch (Exception e) {
				log.info("fetching active inactive list time Error:  "+e);
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("saveerror.show();");
			}
		}else{
			setRenderDataTable(false);
		}
	}

	public void deActivateRecord() throws IOException{

		try {
			for (BankBranchDataTable DataTableObj : bankBranchActivationList) {
				if(DataTableObj.getIsSelected().equals(true)){
					bankApprovalService.upDateRemarks(getRemarks(), DataTableObj.getBranchPk(),sessionStateManage.getUserName());

					HashMap<String, Object> activateDeactiveRec = new HashMap<String, Object>();

					//activateDeactiveRec.put("BankCode", DataTableObj.getBankCode());
					activateDeactiveRec.put("BankId", DataTableObj.getBankID());
					//activateDeactiveRec.put("BankBranchCode", DataTableObj.getBranchCode());
					activateDeactiveRec.put("BankBranchId", DataTableObj.getBranchPk());

					// call procedure to move java to old emos
					String errmsg = bankApprovalService.branchActivateDeActivatemoveJavatoEMOS(activateDeactiveRec);

					if(errmsg != null){
						setErrorMsg(errmsg);
						RequestContext.getCurrentInstance().execute("saveerror.show();");
					}else{
						setRemarks(null);
						setSearchBranchName(null);
						setSearchBranchIfsc(null);
						setSearchSwiftIfsc(null);
						setErrorMsg("Record updated successfully");
						bankBranchActivationList.clear();
						RequestContext.getCurrentInstance().execute("update.show();");
						//	getAlldata(getBankID(),getBranchFullNameForSearch());
						//FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankbranchactiveinactivelist.xhtml");
						break;
					}
				}
			}
		} catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}


	}
	public void clickToActivate() throws IOException{

		try {
			for (BankBranchDataTable DataTableObj : bankBranchActivationList) {
				if(DataTableObj.getIsSelected().equals(true)){
					bankApprovalService.activateRecord(DataTableObj.getBranchPk(),sessionStateManage.getUserName());
					HashMap<String, Object> activateDeactiveRec = new HashMap<String, Object>();

					activateDeactiveRec.put("BankCode", DataTableObj.getBankCode());
					activateDeactiveRec.put("BankId", DataTableObj.getBankID());
					activateDeactiveRec.put("BankBranchCode", DataTableObj.getBranchCode());
					activateDeactiveRec.put("BankBranchId", DataTableObj.getBranchPk());

					// call procedure to move java to old emos
					String errmsg = bankApprovalService.branchActivateDeActivatemoveJavatoEMOS(activateDeactiveRec);

					if(errmsg != null){
						setErrorMsg(errmsg);
						RequestContext.getCurrentInstance().execute("saveerror.show();");
					}else{
						//	getAlldata(getBankID(),getBranchFullNameForSearch());
						setSearchBranchName(null);
						setSearchBranchIfsc(null);
						setSearchSwiftIfsc(null);
						/*setErrorMsg("Record updated successfully");
						bankBranchApprovalList.clear();
						RequestContext.getCurrentInstance().execute("saveerror.show();");*/

						setErrorMsg("Record updated successfully");
						bankBranchActivationList.clear();
						RequestContext.getCurrentInstance().execute("update.show();");
						//FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankbranchactiveinactivelist.xhtml");
						break;
					}
				}
			}
		} catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}

	}

	public void clearRemarks(){
		setRemarks(null);
	}

	private String bankName;


	private String branchFullName;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchFullName() {
		return branchFullName;
	}

	public void setBranchFullName(String branchFullName) {
		this.branchFullName = branchFullName;
	}



	@SuppressWarnings("unchecked")
	public void populateBranch() {
		branchList.clear();
		setBranchList(generalService.getBranchListBasedOnBank(getBankID()));

	}

	public List<BankBranch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BankBranch> branchList) {
		this.branchList = branchList;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public String getBranchFullNameForSearch() {
		return branchFullNameForSearch;
	}

	public void setBranchFullNameForSearch(String branchFullNameForSearch) {
		this.branchFullNameForSearch = branchFullNameForSearch;
	}

	public String getIfscCodeForSearch() {
		return ifscCodeForSearch;
	}

	public void setIfscCodeForSearch(String ifscCodeForSearch) {
		this.ifscCodeForSearch = ifscCodeForSearch;
	}
	public void clearAllFieldsActiveDeactive(){
		setIfscCodeForSearch( null);
		setBranchFullNameForSearch( null);
		setBankID(null);
		setBankName(null);
		setRenderDataTable(false);
	}


	//bank branch approval time getter method called removed
	//added by koti @09/08/2016
	public void toFetchApprovalAllRecords(){


		bankBranchApprovalList.clear();
		BankBranchDataTable bankBranchDataTable = null;
		try{
			List<BankBranch> bankbranchapprovallist = bankApprovalService.getBankBranchListForApproval();

			for (BankBranch bankBranch : bankbranchapprovallist) {

				if(!(bankBranch.getModifiedBy() == null? bankBranch.getCreatedBy() : bankBranch.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())){

					bankBranchDataTable = new BankBranchDataTable();

					bankBranchDataTable.setBankID(bankBranch.getExBankMaster().getBankId());
					bankBranchDataTable.setBankFullName(bankBranch.getExBankMaster().getBankFullName());
					bankBranchDataTable.setBranchCode(bankBranch.getBranchCode().toString());
					bankBranchDataTable.setFullName(bankBranch.getBranchFullName());
					bankBranchDataTable.setShortName(bankBranch.getBranchShortName());
					bankBranchDataTable.setEmail(bankBranch.getEmail());
					bankBranchDataTable.setCreatedby(bankBranch.getCreatedBy());
					bankBranchDataTable.setCreatedDate(bankBranch.getCreatedDate());
					bankBranchDataTable.setApprovedBy(bankBranch.getApprovedBy());
					bankBranchDataTable.setApprovedDate(bankBranch.getApprovedDate());
					bankBranchDataTable.setBankCode(bankBranch.getExBankMaster().getBankCode());
					bankBranchDataTable.setLocation(bankBranch.getAddress1());
					bankBranchDataTable.setFullNameLocal(bankBranch.getBranchFullNameAr());
					bankBranchDataTable.setBranchIFSC(bankBranch.getBranchIfsc());
					bankBranchDataTable.setBranchPk(bankBranch.getBankBranchId());
					if(bankBranch.getFsCountryMaster()!=null){
						bankBranchDataTable.setCountryId(bankBranch.getFsCountryMaster().getCountryId());
					}
					if(bankBranch.getFsStateMaster()!=null){
						bankBranchDataTable.setStateId(bankBranch.getFsStateMaster().getStateId());
					}
					if(bankBranch.getFsDistrictMaster()!=null){
						bankBranchDataTable.setDistrictId(bankBranch.getFsDistrictMaster().getDistrictId());
					}
					if(bankBranch.getFsCityMaster()!=null){
						bankBranchDataTable.setCityId(bankBranch.getFsCityMaster().getCityId());
					}
					bankBranchDataTable.setAddress1(bankBranch.getAddress1());
					bankBranchDataTable.setAddress2(bankBranch.getAddress2());
					bankBranchDataTable.setAddress1Local(bankBranch.getAddress1Ar());
					bankBranchDataTable.setAddress2Local(bankBranch.getAddress2Ar());
					bankBranchDataTable.setContactDepartment(bankBranch.getContactDept());
					bankBranchDataTable.setContactDepartmentLocal(bankBranch.getContactDeptAr());
					bankBranchDataTable.setContactdesignation(bankBranch.getContactDesg());
					bankBranchDataTable.setContactdesignationLocal(bankBranch.getContactDesgAr());
					bankBranchDataTable.setContactPerson(bankBranch.getContactPerson());
					bankBranchDataTable.setContactPersonLocal(bankBranch.getContactPersonAr());
					bankBranchDataTable.setFax(bankBranch.getFaxNo());
					bankBranchDataTable.setMobileNo(bankBranch.getMobile());
					bankBranchDataTable.setMicrCode(bankBranch.getMicrCode());
					bankBranchDataTable.setDebitBranchID( bankBranch.getDebitBranchId());
					bankBranchDataTable.setRoutingID(bankBranch.getRoutingId());
					bankBranchDataTable.setSwift(bankBranch.getSwiftBic());
					bankBranchDataTable.setTelephoneNumber(bankBranch.getTelephoneNo());
					bankBranchDataTable.setZipCode(bankBranch.getZipCode());
					bankBranchDataTable.setShortName(bankBranch.getBranchShortName());
					bankBranchDataTable.setShortNameLocal(bankBranch.getBranchShortNameAr());
					bankBranchDataTable.setModifiedBy(bankBranch.getModifiedBy());
					bankBranchDataTable.setModifiedDate(bankBranch.getModifiedDate());

					bankBranchApprovalList.add(bankBranchDataTable);
				}

			}
		} catch (Exception e) {
			log.info("fetching list for approval time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}


	private String searchBranchName;
	private String searchBranchIfsc;
	private String searchSwiftIfsc;

	public String getSearchBranchName() {
		return searchBranchName;
	}
	public void setSearchBranchName(String searchBranchName) {
		this.searchBranchName = searchBranchName;
	}

	public String getSearchBranchIfsc() {
		return searchBranchIfsc;
	}
	public void setSearchBranchIfsc(String searchBranchIfsc) {
		this.searchBranchIfsc = searchBranchIfsc;
	}

	public String getSearchSwiftIfsc() {
		return searchSwiftIfsc;
	}
	public void setSearchSwiftIfsc(String searchSwiftIfsc) {
		this.searchSwiftIfsc = searchSwiftIfsc;
	}

	public void toSearchBasedOnBankName(){
		if((getSearchBranchName() != null && getSearchBranchName().length()>2) || (getSearchBranchIfsc() !=null && getSearchBranchIfsc().length()>2) || (getSearchSwiftIfsc() != null && getSearchSwiftIfsc().length()>2)){


			/*if(getSearchBranchIfsc() !=null && getSearchBranchIfsc().length()>=2)
			{
				if(getSearchBranchName()==null || (getSearchBranchName() != null && getSearchBranchName().length()==0))
				{
					RequestContext.getCurrentInstance().execute("maximumChar.show();");
					return;
				}
			}

			if(getSearchSwiftIfsc() != null && getSearchSwiftIfsc().length()>=2)
			{
				if((getSearchBranchName()==null|| (getSearchBranchName() != null && getSearchBranchName().length()==0))   || (getSearchBranchIfsc()==null || getSearchBranchIfsc() !=null && getSearchBranchIfsc().length()==0))
				{
					RequestContext.getCurrentInstance().execute("maximumChar.show();");
					return;
				}
			}*/



			bankBranchActivationList.clear();
			BankBranchDataTable bankBranchDataTable = null;

			List<BankBranch> bankbranchActivelist = bankApprovalService.toSearchBranchName(getCountryId(),getBankID(),getSearchBranchName(),getSearchBranchIfsc(),getSearchSwiftIfsc());
			if(bankbranchActivelist.size()>0){
				setRenderDataTable(true);
				setBooRenderDatatable(true);
				for (BankBranch bankBranch : bankbranchActivelist) {
					bankBranchDataTable = new BankBranchDataTable();

					bankBranchDataTable.setBankID(bankBranch.getExBankMaster().getBankId());
					bankBranchDataTable.setBankCode(bankBranch.getExBankMaster().getBankCode());
					bankBranchDataTable.setBankFullName(bankBranch.getExBankMaster().getBankFullName());
					bankBranchDataTable.setBranchCode(bankBranch.getBranchCode().toString());
					bankBranchDataTable.setFullName(bankBranch.getBranchFullName());
					bankBranchDataTable.setShortName(bankBranch.getBranchShortName());
					bankBranchDataTable.setEmail(bankBranch.getEmail());
					bankBranchDataTable.setCreatedBy(bankBranch.getCreatedBy());
					bankBranchDataTable.setCreatedDate(bankBranch.getCreatedDate());
					bankBranchDataTable.setIsStatus(bankBranch.getIsactive());
					bankBranchDataTable.setBranchPk(bankBranch.getBankBranchId());
					bankBranchDataTable.setBranchIFSC(bankBranch.getBranchIfsc());
					bankBranchDataTable.setSwift(bankBranch.getSwiftBic());
					bankBranchDataTable.setRemarks(bankBranch.getRemarks());
					if(bankBranch.getIsactive().equalsIgnoreCase(Constants.Yes)){
						bankBranchDataTable.setRecordStatus(Constants.ACTIVATE );
						bankBranchDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					}else if(bankBranch.getIsactive().equalsIgnoreCase(Constants.D)){
						bankBranchDataTable.setRecordStatus(Constants.IN_ACTIVE );
						bankBranchDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					} else if (bankBranch.getIsactive().equalsIgnoreCase(Constants.U)) {
						bankBranchDataTable.setRecordStatus(Constants.UNAPPROVED);
						bankBranchDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}



					bankBranchActivationList.add(bankBranchDataTable);
				}
			}else{
				RequestContext.getCurrentInstance().execute("noRecords.show();");
				/*setRenderDataTable(false);*/
				bankBranchActivationList.clear();
				return;
			}
		}else{
			RequestContext.getCurrentInstance().execute("maximumChar.show();");
			return;
		}
	}



	public List<BankBranchDataTable> getBankBranchApprovalSelectedList() {
		return bankBranchApprovalSelectedList;
	}
	public void setBankBranchApprovalSelectedList(List<BankBranchDataTable> bankBranchApprovalSelectedList) {
		this.bankBranchApprovalSelectedList = bankBranchApprovalSelectedList;
	}

	// bulk approval
	public void updateBankBranchApproval(){

		try{

			HashMap<String, String> ouputValues = null; 

			if(bankBranchApprovalSelectedList!=null && bankBranchApprovalSelectedList.size()>0){
				//bankApprovalService.bulkApprovalForBankBranch(bankBranchApprovalSelectedList);

				for (BankBranchDataTable lstSelectedBranch : bankBranchApprovalSelectedList) {

					bankBranchDetailsService.toApproveRecordsBankBranch(lstSelectedBranch.getBranchPk(),sessionStateManage.getUserName());
					setPkBankBranch(lstSelectedBranch.getBranchPk());

					// START OF PROCEDURE CALL
					HashMap<String, String>  approveRecord = new HashMap<String, String>();

					approveRecord.put("BANK_CODE",lstSelectedBranch.getBankCode());
					approveRecord.put("BANK_ID",lstSelectedBranch.getBankID().toPlainString());
					approveRecord.put("BANK_BRANCH_ID",lstSelectedBranch.getBranchPk().toPlainString());
					approveRecord.put("BANK_BRANCH_CODE",lstSelectedBranch.getBranchCode());
					ouputValues = getBankBranchDetailsService().callPopulateBankBranch(approveRecord);

					if(ouputValues.get("P_ERROR_MESSAGE") != null){
						break;
					}
				}

				if(ouputValues.get("P_ERROR_MESSAGE") != null){
					setErrorMsg(ouputValues.get("P_ERROR_MESSAGE"));
					bankBranchDetailsService.unapproveProcedureExcep(getPkBankBranch());
					RequestContext.getCurrentInstance().execute("saveerror.show();");
					return; 
				}else{
					bankBranchApprovalSelectedList.clear();
					toFetchApprovalAllRecords();
					RequestContext.getCurrentInstance().execute("approve.show();");
				}

			}else{
				setErrorMsg("Please select atleast One Record");
				RequestContext.getCurrentInstance().execute("saveerror.show();");
			}
		} catch (Exception e) {
			bankBranchApprovalSelectedList.clear();
			toFetchApprovalAllRecords();
			log.info("Approval Bank Branch time Error:  "+e);
			setErrorMsg(e.getMessage());
			bankBranchDetailsService.unapproveProcedureExcep(getPkBankBranch());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}

	}

	// search bank branch moving from bank account to bank branch search
	public void searchClickedCreationbene() {
		try {
			if(getBankID() != null){
				
				SearchBranchDeatilsBean searchBranchDeatils = (SearchBranchDeatilsBean)appContext.getBean("searchBranch");
				searchBranchDeatils.clearSearch();
				
				HttpSession session = sessionStateManage.getSession();
				session.setAttribute("BenificiaryBankID", getBankID());
				session.setAttribute("BenificiaryBankName", getBankName());
				session.setAttribute("EditBankBranchMaster", true);
				session.setAttribute("BenificiaryCountryID",getCountryId());
				session.setAttribute("BenificiaryCountryName", getCountryName());
				session.setAttribute("stateList",null);
				session.setAttribute("stateList", getLstStateList());
				
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("../beneficary/searchbranchdetails.xhtml");
			}else{
				setErrorMsg("Please Select Bank");
				RequestContext.getCurrentInstance().execute("maxBranchCode.show();");
			}
		} catch (Exception e) {
			log.info("Problem to Redirect the page : " + e);
		}
	}
	
	public void fetchBankBranchMaster(BranchDataTable bankBranch){
		if(bankBranch != null && bankBranch.getBankBranchCode() != null){
			setBranchCode(bankBranch.getBankBranchCode().toPlainString());
			fetchData();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankbranchdetails.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public BankBranchUploadService getBankBranchUploadService() {
		return bankBranchUploadService;
	}

	public void setBankBranchUploadService(
			BankBranchUploadService bankBranchUploadService) {
		this.bankBranchUploadService = bankBranchUploadService;
	}

	public List<CountryMasterDesc> getAllCountryList() {
		return allCountryList;
	}

	public void setAllCountryList(List<CountryMasterDesc> allCountryList) {
		this.allCountryList = allCountryList;
	}
	
	

}

