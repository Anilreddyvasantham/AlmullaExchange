package com.amg.exchange.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.TokenGeneration;
import com.amg.exchange.common.bean.RuleEngine;
import com.amg.exchange.common.model.BizComponentConfDetail;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.mail.ApplicationMailer;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.DocumentImg;
import com.amg.exchange.registration.service.ICompanyMasterservice;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.registration.service.IRemmiterOnlineRegService;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.CollectionUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("remmiterInfo")
@Scope("session")
public class RemmiterInfoManageBean<T> implements Serializable {

	/** logger class name setting */
	Logger log = Logger.getLogger(RemmiterInfoManageBean.class);

	/** serial UID settings **/
	private static final long serialVersionUID = 1L;

	/**
	 * Declaring static variables for rule engine
	 */

	@Autowired
	GoogleMapGenerator<T> googleMapGeneratorBean;

	/*private static final String CUSTOMERTYPE = "Individual";
	private static final String CIVILID = "Civil ID";
	private static final String GROUPID = "Almulla Group";
	private static final String EMPLOYMENTTYPE = "Un Employed";
	private static final String IDENTITYFOR = "ID Proof";*/

	private BigDecimal pkCustomerIdProof = null;
	private String idNumber = null;
	private String idType = Constants.CIVILID;
	private BigDecimal idTypeId = null;
	private Date dob = null;
	private Boolean renderKuwaitId = true;
	private Boolean renderOtherId = false;
	private Date idExpDate = null;
	private String fsCustomerIdProofCreatedBy = null;
	private Date fsCustomerIdProofCreatedDate = null;

	private BigDecimal pkCustomerId = null;
	private String expDateCheck = null;
	private String showDob = null;

	private BigDecimal pkImage = null;
	// private UploadedFile file = null;
	private StreamedContent downloadFile = null;

	private String title = null;
	private String firstName = null;
	private String middleName = null;
	private String lastName = null;
	private String shortName = null;
	private String localTitle = null;
	private String localFirstName = null;
	private String localMiddleName = null;
	private String localLastName = null;
	private String localShortName = null;
	private BigDecimal nationality = null;
	private String gender = null;
	private String mobileNo = null;
	private String email = null;
	private String alternateEmail = null;
	private Date dateOfBirth = null;
	private String fsCustomerCreatedBy = null;
	private Date fsCustomerCreatedDate = null;

	public BigDecimal pkCustomerEmployeeId = null;
	private BigDecimal employmentType = null;
	private BigDecimal occupation = null;
	private String department = null;
	private String employer = null;
	private String blockNo = null;
	private String streetNo = null;
	private String area = null;
	private String officeTelNo = null;
	private BigDecimal country = null;
	private BigDecimal state = null;
	private BigDecimal district = null;
	private BigDecimal city = null;
	private String postalCode = null;
	private String fsCustomerEmpCreatedBy = null;
	private Date fsCustomerEmpCreatedDate = null;

	public BigDecimal pkCustomerContactDetails = null;
	private BigDecimal contactType = null;
	private String contactLocalArea = null;
	private BigDecimal contactCountry = null;
	private String contactStreetNo = null;
	private BigDecimal contactState = null;
	private String contactTelephoneNo = null;
	private BigDecimal contactDistrict = null;
	private String contactFlat = null;
	private BigDecimal contactCity = null;
	private String contactBlockNo = null;
	private String fsCustomerContactDetilsCreatedBy = null;
	private Date fsCustomerContactDetilsCreatedDate = null;

	/** These booleans are responsible to render panels */
	private Boolean booPersonalInformation = true;
	private Boolean booEmploymentInformation = false;
	private Boolean booContactDetails = false;
	/** Responsible to populate State,District, City in Employment Info */
	private List<StateMasterDesc> lstStateList = new ArrayList<StateMasterDesc>();
	private List<DistrictMasterDesc> lstDistrictList = new ArrayList<DistrictMasterDesc>();
	private List<CityMasterDesc> lstCityList = new ArrayList<CityMasterDesc>();
	// private Map<BigDecimal, String> mapContactTypeList = new
	// HashMap<BigDecimal, String>();

	/** Responsible to populate State,District, City in Contact Details */
	private List<StateMasterDesc> lstContactStateList = new ArrayList<StateMasterDesc>();
	private List<DistrictMasterDesc> lstContactDistrictList = new ArrayList<DistrictMasterDesc>();
	private List<CityMasterDesc> lstContactCityList = new ArrayList<CityMasterDesc>();
	// private Map<String, BizComponentConfDetail> mapComponentBehavior = new
	// HashMap<String,BizComponentConfDetail>();

	/** Responsible to hold Contact Type in that list */
	// private List<ContactType> lstContactType = new ArrayList<ContactType>();

	/**
	 * This is responsible to get track upto which panel user has given input,
	 * default is first panel, that is 1
	 */
	private int saveUptoPanel = 1;

	/** Responsible to make readonly Civil ID fields, for existing customer */
	private Boolean booReadonlyIdNumber = false;

	/**
	 * Responsible to render the Employment Panel depending upon Employment type
	 */
	private Boolean booEmploymentPanel = true;

	/**
	 * Responsible to disable save button when once the customer has gone to
	 * branch
	 */
	private Boolean booDisableSave = false;

	/**
	 * This variable is responsible to track that, data is already there in
	 * database or not
	 */
	private Boolean booViewed = false;

	/**
	 * This is responsible to show validation msg depending upon uploaded file
	 * type
	 */
	private Boolean booImageValidation = false;

	/**
	 * Responsible to show icon for showing image in growl where there is some
	 * image already stored in database
	 */
	private Boolean booHasImage = false;

	/** This is responsible to Check dupliacte Contact Details */
	private Boolean booContactDetailsDuplicate = false;

	/** will check Employment country, state, city is there */
	private Boolean booEmployed = false;

	/* Keeping Track thatImage is uploaded */
	private Boolean booImageUploaded = false;

	/** Responsible to populate data */
	private List<Customer> lstCustomer = new ArrayList<Customer>();
	private List<CustomerEmploymentInfo> lstCustomerEmployment = new ArrayList<CustomerEmploymentInfo>();
	private List<CustomerIdProof> lstCustomerIdProof = new ArrayList<CustomerIdProof>();
	private List<ContactDetail> lstContactDetailsModel = new ArrayList<ContactDetail>();

	/**
	 * Responsible to holds all the contact Details Object, both Adding and
	 * fetching time
	 */
	private List<ContactDetails> lstContactDetails = new ArrayList<ContactDetails>();

	/** Responsible to format Date */
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	/** Responsible to manage session */
	SessionStateManage sessionStateManage = new SessionStateManage();

	/** Responsible to support save controller */
	private CountryMaster countryMaster = null;
	private CompanyMaster companyMaster = null;
	private Customer customer = null;

	/** For managing image save */
	private DocumentImg image = null;

	/**
	 * These maps are required for, showing String in Datatable, but in database
	 * ID will save
	 */
	private Map<BigDecimal, String> mapContactType = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapCountry = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapState = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapDistrict = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapCity = new HashMap<BigDecimal, String>();

	/** Responsible to hold the identity Type from Rule Engine */
	Map<BigDecimal, String> mapIdentityType = new HashMap<BigDecimal, String>();

	/** Holding the component Behavior */
	// private Map<String, BussComponentConfDetail> mapComponentBehavior = new
	// HashMap<String,BussComponentConfDetail>();

	/** setting token */
	private String tokenKey = null;
	private TokenGeneration tokenGeneration = new TokenGeneration();

	/**
	 * Responsible to Check Civil ID is already registered or not
	 */
	private Boolean booRegistered = false;
	private Boolean booIdTypeCheck = false;// added by nazish
	private Boolean booCivilId = false;
	private Boolean booOtherId = true;

	@Autowired
	IRemmiterOnlineRegService<T> remOnlineReg;
	
	@Autowired
	ICustomerRegistrationBranchService<T> customerRegistrationService;

	public IRemmiterOnlineRegService<T> getRemOnlineReg() {
		return remOnlineReg;
	}

	public void setRemOnlineReg(IRemmiterOnlineRegService<T> remOnlineReg) {
		this.remOnlineReg = remOnlineReg;
	}

	@Autowired
	IGeneralService<T> iGeneralService;
	
	// Timezone for application country based creation log
	private Date currentTime;
	
	

	public Date getCurrentTime() {
		
		Date objList = getiGeneralService().getSysDateTimestamp(sessionStateManage.getCountryId());
		
		if(objList != null){
		currentTime = objList;}
		else{
			currentTime =null;
		}
		
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public IGeneralService<T> getiGeneralService() {
		return iGeneralService;
	}

	public void setiGeneralService(IGeneralService<T> iGeneralService) {
		this.iGeneralService = iGeneralService;
	}

	@Autowired
	ApplicationMailer mailService;

	public ApplicationMailer getMailService() {
		return mailService;
	}

	public void setMailService(ApplicationMailer mailService) {
		this.mailService = mailService;
	}

	@Autowired
	public TokenGeneration getTokenGeneration() {
		return tokenGeneration;
	}

	public void setTokenGeneration(TokenGeneration tokenGeneration) {
		this.tokenGeneration = tokenGeneration;
	}

	@Autowired
	RuleEngine<T> ruleEngine;

	public BigDecimal getPkCustomerIdProof() {
		return pkCustomerIdProof;
	}

	public void setPkCustomerIdProof(BigDecimal pkCustomerIdProof) {
		this.pkCustomerIdProof = pkCustomerIdProof;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdType() {

		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public BigDecimal getIdTypeId() {

		return idTypeId;
	}

	public void setIdTypeId(BigDecimal idTypeId) {
		this.idTypeId = idTypeId;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Boolean getRenderKuwaitId() {
		return renderKuwaitId;
	}

	public void setRenderKuwaitId(Boolean renderKuwaitId) {
		this.renderKuwaitId = renderKuwaitId;
	}

	public Boolean getRenderOtherId() {
		return renderOtherId;
	}

	public void setRenderOtherId(Boolean renderOtherId) {
		this.renderOtherId = renderOtherId;
	}

	public Date getIdExpDate() {
		return idExpDate;
	}

	public void setIdExpDate(Date idExpDate) {
		this.idExpDate = idExpDate;
	}

	public String getFsCustomerIdProofCreatedBy() {
		return fsCustomerIdProofCreatedBy;
	}

	public void setFsCustomerIdProofCreatedBy(String fsCustomerIdProofCreatedBy) {
		this.fsCustomerIdProofCreatedBy = fsCustomerIdProofCreatedBy;
	}

	public Date getFsCustomerIdProofCreatedDate() {
		return fsCustomerIdProofCreatedDate;
	}

	public void setFsCustomerIdProofCreatedDate(Date fsCustomerIdProofCreatedDate) {
		this.fsCustomerIdProofCreatedDate = fsCustomerIdProofCreatedDate;
	}

	public String getExpDateCheck() {
		return expDateCheck;
	}

	public void setExpDateCheck(String expDateCheck) {
		this.expDateCheck = expDateCheck;
	}

	public BigDecimal getPkCustomerId() {
		return pkCustomerId;
	}

	public void setPkCustomerId(BigDecimal pkCustomerId) {
		this.pkCustomerId = pkCustomerId;
	}

	/* public UploadedFile getFile() {
	 * return file;
	 * }
	 * public void setFile(UploadedFile file) {
	 * this.file = file;
	 * } */

	public BigDecimal getPkImage() {
		return pkImage;
	}

	public void setPkImage(BigDecimal pkImage) {
		this.pkImage = pkImage;
	}

	public StreamedContent getDownloadFile() {
		return downloadFile;
	}

	public void setDownloadFile(StreamedContent downloadFile) {
		this.downloadFile = downloadFile;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLocalTitle() {
		return localTitle;
	}

	public void setLocalTitle(String localTitle) {
		this.localTitle = localTitle;
	}

	public String getLocalFirstName() {
		return localFirstName;
	}

	public void setLocalFirstName(String localFirstName) {
		this.localFirstName = localFirstName;
	}

	public String getLocalMiddleName() {
		return localMiddleName;
	}

	public void setLocalMiddleName(String localMiddleName) {
		this.localMiddleName = localMiddleName;
	}

	public String getLocalLastName() {
		return localLastName;
	}

	public void setLocalLastName(String localLastName) {
		this.localLastName = localLastName;
	}

	public String getLocalShortName() {
		return localShortName;
	}

	public void setLocalShortName(String localShortName) {
		this.localShortName = localShortName;
	}

	public BigDecimal getNationality() {
		return nationality;
	}

	public void setNationality(BigDecimal nationality) {
		this.nationality = nationality;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlternateEmail() {
		return alternateEmail;
	}

	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFsCustomerCreatedBy() {
		return fsCustomerCreatedBy;
	}

	public void setFsCustomerCreatedBy(String fsCustomerCreatedBy) {
		this.fsCustomerCreatedBy = fsCustomerCreatedBy;
	}

	public Date getFsCustomerCreatedDate() {
		return fsCustomerCreatedDate;
	}

	public void setFsCustomerCreatedDate(Date fsCustomerCreatedDate) {
		this.fsCustomerCreatedDate = fsCustomerCreatedDate;
	}

	public BigDecimal getPkCustomerEmployeeId() {
		return pkCustomerEmployeeId;
	}

	public void setPkCustomerEmployeeId(BigDecimal pkCustomerEmployeeId) {
		this.pkCustomerEmployeeId = pkCustomerEmployeeId;
	}

	public BigDecimal getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(BigDecimal employmentType) {
		this.employmentType = employmentType;
	}

	public BigDecimal getOccupation() {
		return occupation;
	}

	public void setOccupation(BigDecimal occupation) {
		this.occupation = occupation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getBlockNo() {
		return blockNo;
	}

	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}

	public String getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getOfficeTelNo() {
		return officeTelNo;
	}

	public void setOfficeTelNo(String officeTelNo) {
		this.officeTelNo = officeTelNo;
	}

	public BigDecimal getCountry() {
		return country;
	}

	public void setCountry(BigDecimal country) {
		this.country = country;
	}

	public BigDecimal getState() {
		return state;
	}

	public void setState(BigDecimal state) {
		this.state = state;
	}

	public BigDecimal getDistrict() {
		return district;
	}

	public void setDistrict(BigDecimal district) {
		this.district = district;
	}

	public BigDecimal getCity() {
		return city;
	}

	public void setCity(BigDecimal city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getFsCustomerEmpCreatedBy() {
		return fsCustomerEmpCreatedBy;
	}

	public void setFsCustomerEmpCreatedBy(String fsCustomerEmpCreatedBy) {
		this.fsCustomerEmpCreatedBy = fsCustomerEmpCreatedBy;
	}

	public Date getFsCustomerEmpCreatedDate() {
		return fsCustomerEmpCreatedDate;
	}

	public void setFsCustomerEmpCreatedDate(Date fsCustomerEmpCreatedDate) {
		this.fsCustomerEmpCreatedDate = fsCustomerEmpCreatedDate;
	}

	public BigDecimal getPkCustomerContactDetails() {
		return pkCustomerContactDetails;
	}

	public void setPkCustomerContactDetails(BigDecimal pkCustomerContactDetails) {
		this.pkCustomerContactDetails = pkCustomerContactDetails;
	}

	public BigDecimal getContactType() {
		return contactType;
	}

	public void setContactType(BigDecimal contactType) {
		this.contactType = contactType;
	}

	public String getContactLocalArea() {
		return contactLocalArea;
	}

	public void setContactLocalArea(String contactLocalArea) {
		this.contactLocalArea = contactLocalArea;
	}

	public BigDecimal getContactCountry() {
		return contactCountry;
	}

	public void setContactCountry(BigDecimal contactCountry) {
		this.contactCountry = contactCountry;
	}

	public String getContactStreetNo() {
		return contactStreetNo;
	}

	public void setContactStreetNo(String contactStreetNo) {
		this.contactStreetNo = contactStreetNo;
	}

	public BigDecimal getContactState() {
		return contactState;
	}

	public void setContactState(BigDecimal contactState) {
		this.contactState = contactState;
	}

	public String getContactTelephoneNo() {
		return contactTelephoneNo;
	}

	public void setContactTelephoneNo(String contactTelephoneNo) {
		this.contactTelephoneNo = contactTelephoneNo;
	}

	public BigDecimal getContactDistrict() {
		return contactDistrict;
	}

	public void setContactDistrict(BigDecimal contactDistrict) {
		this.contactDistrict = contactDistrict;
	}

	public String getContactFlat() {
		return contactFlat;
	}

	public void setContactFlat(String contactFlat) {
		this.contactFlat = contactFlat;
	}

	public BigDecimal getContactCity() {
		return contactCity;
	}

	public void setContactCity(BigDecimal contactCity) {
		this.contactCity = contactCity;
	}

	public String getContactBlockNo() {
		return contactBlockNo;
	}

	public void setContactBlockNo(String contactBlockNo) {
		this.contactBlockNo = contactBlockNo;
	}

	public String getFsCustomerContactDetilsCreatedBy() {
		return fsCustomerContactDetilsCreatedBy;
	}

	public void setFsCustomerContactDetilsCreatedBy(String fsCustomerContactDetilsCreatedBy) {
		this.fsCustomerContactDetilsCreatedBy = fsCustomerContactDetilsCreatedBy;
	}

	public Date getFsCustomerContactDetilsCreatedDate() {
		return fsCustomerContactDetilsCreatedDate;
	}

	public void setFsCustomerContactDetilsCreatedDate(Date fsCustomerContactDetilsCreatedDate) {
		this.fsCustomerContactDetilsCreatedDate = fsCustomerContactDetilsCreatedDate;
	}

	public String getShowDob() {
		return showDob;
	}

	public void setShowDob(String showDob) {
		this.showDob = showDob;
	}

	public Boolean getBooPersonalInformation() {
		return booPersonalInformation;
	}

	public void setBooPersonalInformation(Boolean booPersonalInformation) {
		this.booPersonalInformation = booPersonalInformation;
	}

	public Boolean getBooEmploymentInformation() {
		return booEmploymentInformation;
	}

	public void setBooEmploymentInformation(Boolean booEmploymentInformation) {
		this.booEmploymentInformation = booEmploymentInformation;
	}

	public Boolean getBooContactDetails() {
		return booContactDetails;
	}

	public void setBooContactDetails(Boolean booContactDetails) {
		this.booContactDetails = booContactDetails;
	}

	public int getSaveUptoPanel() {
		return saveUptoPanel;
	}

	public void setSaveUptoPanel(int saveUptoPanel) {
		this.saveUptoPanel = saveUptoPanel;
	}

	public Boolean getBooReadonlyIdNumber() {
		return booReadonlyIdNumber;
	}

	public void setBooReadonlyIdNumber(Boolean booReadonlyIdNumber) {
		this.booReadonlyIdNumber = booReadonlyIdNumber;
	}

	public Boolean getBooEmploymentPanel() {
		return booEmploymentPanel;
	}

	public void setBooEmploymentPanel(Boolean booEmploymentPanel) {
		this.booEmploymentPanel = booEmploymentPanel;
	}

	public Boolean getBooDisableSave() {
		return booDisableSave;
	}

	public void setBooDisableSave(Boolean booDisableSave) {
		this.booDisableSave = booDisableSave;
	}

	public Boolean getBooViewed() {
		return booViewed;
	}

	public void setBooViewed(Boolean booViewed) {
		this.booViewed = booViewed;
	}

	public Boolean getBooImageValidation() {
		return booImageValidation;
	}

	public void setBooImageValidation(Boolean booImageValidation) {
		this.booImageValidation = booImageValidation;
	}

	public Boolean getBooHasImage() {
		return booHasImage;
	}

	public void setBooHasImage(Boolean booHasImage) {
		this.booHasImage = booHasImage;
	}

	public Boolean getBooContactDetailsDuplicate() {
		return booContactDetailsDuplicate;
	}

	public void setBooContactDetailsDuplicate(Boolean booContactDetailsDuplicate) {
		this.booContactDetailsDuplicate = booContactDetailsDuplicate;
	}

	public Boolean getBooEmployed() {
		return booEmployed;
	}

	public void setBooEmployed(Boolean booEmployed) {
		this.booEmployed = booEmployed;
	}

	public Boolean getBooImageUploaded() {
		return booImageUploaded;
	}

	public void setBooImageUploaded(Boolean booImageUploaded) {
		this.booImageUploaded = booImageUploaded;
	}

	public List<StateMasterDesc> getLstStateList() {
		generateStateList();
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

	public List<StateMasterDesc> getLstContactStateList() {

		return lstContactStateList;
	}

	public void setLstContactStateList(List<StateMasterDesc> lstContactStateList) {
		this.lstContactStateList = lstContactStateList;
	}

	public List<DistrictMasterDesc> getLstContactDistrictList() {
		return lstContactDistrictList;
	}

	public void setLstContactDistrictList(List<DistrictMasterDesc> lstContactDistrictList) {
		this.lstContactDistrictList = lstContactDistrictList;
	}

	public List<CityMasterDesc> getLstContactCityList() {
		return lstContactCityList;
	}

	public void setLstContactCityList(List<CityMasterDesc> lstContactCityList) {
		this.lstContactCityList = lstContactCityList;
	}

	/* public List<ContactType> getLstContactType() {
	 * return lstContactType;
	 * }
	 * public void setLstContactType(List<ContactType> lstContactType) {
	 * this.lstContactType = lstContactType;
	 * } */

	public List<Customer> getLstCustomer() {
		return lstCustomer;
	}

	public void setLstCustomer(List<Customer> lstCustomer) {
		this.lstCustomer = lstCustomer;
	}

	public List<CustomerEmploymentInfo> getLstCustomerEmployment() {
		return lstCustomerEmployment;
	}

	public void setLstCustomerEmployment(List<CustomerEmploymentInfo> lstCustomerEmployment) {
		this.lstCustomerEmployment = lstCustomerEmployment;
	}

	public List<CustomerIdProof> getLstCustomerIdProof() {
		return lstCustomerIdProof;
	}

	public void setLstCustomerIdProof(List<CustomerIdProof> lstCustomerIdProof) {
		this.lstCustomerIdProof = lstCustomerIdProof;
	}

	public List<ContactDetail> getLstContactDetailsModel() {
		return lstContactDetailsModel;
	}

	public void setLstContactDetailsModel(List<ContactDetail> lstContactDetailsModel) {
		this.lstContactDetailsModel = lstContactDetailsModel;
	}

	public Map<BigDecimal, String> getMapContactType() {
		return mapContactType;
	}

	public void setMapContactType(Map<BigDecimal, String> mapContactType) {
		this.mapContactType = mapContactType;
	}

	public Map<BigDecimal, String> getMapCountry() {
		return mapCountry;
	}

	public void setMapCountry(Map<BigDecimal, String> mapCountry) {
		this.mapCountry = mapCountry;
	}

	public Map<BigDecimal, String> getMapState() {
		return mapState;
	}

	public void setMapState(Map<BigDecimal, String> mapState) {
		this.mapState = mapState;
	}

	public Map<BigDecimal, String> getMapDistrict() {
		return mapDistrict;
	}

	public void setMapDistrict(Map<BigDecimal, String> mapDistrict) {
		this.mapDistrict = mapDistrict;
	}

	public Map<BigDecimal, String> getMapCity() {
		return mapCity;
	}

	public void setMapCity(Map<BigDecimal, String> mapCity) {
		this.mapCity = mapCity;
	}

	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	public List<ContactDetails> getLstContactDetails() {
		return lstContactDetails;
	}

	public Boolean getBooRegistered() {
		return booRegistered;
	}

	public void setBooRegistered(Boolean booRegistered) {
		this.booRegistered = booRegistered;
	}

	/**
	 * Responsible to populate nationality
	 * 
	 * @return
	 */
	public List<CountryMasterDesc> getNationalityList() {
		log.info("Entering into getNationalityList method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<CountryMasterDesc> lstCountry = getiGeneralService().getNationalityList(
				new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1));
		log.info("Exit into getNationalityList method");
		return lstCountry;
	}

	/**
	 * Responsible to populate Country
	 * 
	 * @return
	 */
	public List<CountryMasterDesc> getCountryNameList() {
		log.info("Entering into getCountryNameList method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<CountryMasterDesc> lstCountry = getiGeneralService().getCountryList(sessionStateManage.getLanguageId());

		/**
		 * we will utilize the map in contact details at adding time, we will
		 * show country name and will id in database
		 */
		for (CountryMasterDesc countryMasterDesc : lstCountry) {
			mapCountry.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
		}
		log.info("Exit into getCountryNameList method");
		return lstCountry;
	}

	public List<CountryMasterDesc> getCountryNameListResident() {
		log.info("Entering into getCountryNameListResident method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<CountryMasterDesc> lstCountry = getiGeneralService().getCountryList(sessionStateManage.getLanguageId());

		/**
		 * we will utilize the map in contact details at adding time, we will
		 * show country name and will id in database
		 */
		for (CountryMasterDesc countryMasterDesc : lstCountry) {
			mapCountry.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
		}
		log.info("Exit into getCountryNameListResident method");
		return lstCountry;
	}

	/**
	 * clear Personal Information
	 */
	public void clearPersonalInformation() {
		// setIdNumber("");
		setIdExpDate(null);
		// setShowDob("");
		// setFileUpload
		setTitle("");
		setFirstName("");
		setMiddleName("");
		setLastName("");
		setShortName("");
		setLocalTitle("");
		setLocalFirstName("");
		setLocalMiddleName("");
		setLocalLastName("");
		setLocalShortName("");
		setNationality(null);
		setGender("");
		setMobileNo("");
		// setEmail("");
		setAlternateEmail("");
	}

	/******************************************** FOR EMPLOYEMNT DETAILS *****************************************************/
	/**
	 * Responsible to prepare stateList by country change, Country List is
	 * common for Both Employment And Contact Details
	 * 
	 * @param event
	 */
	public void generateStateList() {
		log.info("Entering into generateStateList method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<StateMasterDesc> stateMaster = getiGeneralService().getStateList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1),
				sessionStateManage.getCountryId());
		setLstStateList(stateMaster);

		/*** reset State, District, City, as country is changing */
		//setState(null);
		//setDistrict(null);
		//setCity(null);

		//setLstDistrictList(null);
		//setLstCityList(null);
		log.info("Exit into generateStateList method");
	}

	/**
	 * Responsible to populate District depending upon state selection
	 * 
	 * @param event
	 */
	public void generateDistrictList(AjaxBehaviorEvent event) {
		log.info("Entering into generateDistrictList method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<DistrictMasterDesc> lstDistrict = getiGeneralService().getDistrictList(
				new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1), sessionStateManage.getCountryId(), getState());
		setLstDistrictList(lstDistrict);

		/** reset District, City, as state is changing */
		//setDistrict(null);
		//setCity(null);
		log.info("Exit into generateDistrictList method");
	}

	/**
	 * Responsible to populate city depending upon district selection
	 * 
	 * @param event
	 */
	public void generateCityList(AjaxBehaviorEvent event) {
		log.info("Entering into generateCityList method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<CityMasterDesc> lstCity = getiGeneralService().getCityList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1),
				sessionStateManage.getCountryId(), getState(), getDistrict());
		setLstCityList(lstCity);
		log.info("Exit into generateCityList method");
	}

	/**
	 * This method will call when city change happen
	 * It's a do-nothing method, except this after click back button, selected
	 * cityId will not bind with bean
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
		log.info("Entering into populateState method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<StateMasterDesc> stateMaster = getiGeneralService().getStateList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1),
				sessionStateManage.getCountryId());
		setLstStateList(stateMaster);
		log.info("Exit into populateState method");
	}

	/**
	 * This method is responsible to populate district depending upon state
	 * election
	 */
	
	private void populateDistrict() {
		log.info("Entering into populateDistrict method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<DistrictMasterDesc> lstDistrict = getiGeneralService().getDistrictList(
				new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1), sessionStateManage.getCountryId(), getState());
		setLstDistrictList(lstDistrict);
		log.info("Exit into populateDistrict method");
	}

	/**
	 * This method is responsible to populate City depending upon district
	 * selection
	 */
	private void populateCity() {
		log.info("Entering into populateCity method");
		// log.debug("populate City called : "+getDistrict());
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<CityMasterDesc> lstCity = getiGeneralService().getCityList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1),
				sessionStateManage.getCountryId(), getState(), getDistrict());

		/* for (CityMasterDesc cityMasterDesc : lstCity) {
		 * log.debug(cityMasterDesc.getCityName());
		 * } */

		setLstCityList(lstCity);
		log.info("Exit into populateCity method");
	}

	/**
	 * Responsible to populate Employment Type
	 * 
	 * @return
	 */
	/* public List<EmploymentType> getEmploymentTypeList() {
	 * SessionStateManage sessionStateManage = new SessionStateManage();
	 * return getiGeneralService().getEmploymentTypes(new
	 * BigDecimal(sessionStateManage
	 * .isExists("languageId")?sessionStateManage.getSessionValue
	 * ("languageId"):""+1));
	 * } */

	/**
	 * This is responsible to render Panel, depending upon Employment Type
	 * selection
	 * 
	 * @param event
	 */
	public void getEmploymentStatus(AjaxBehaviorEvent event) {
		log.info("Entering into getEmploymentStatus method");
		/*** 3 means unemployed and 0 means Select */
		// log.debug("Enter : "+getEmploymentType());
		if (getEmploymentType().intValue() == getiGeneralService()
				.getComponentId(Constants.EMPLOYMENTTYPE, sessionStateManage.getLanguageId()).getFsBizComponentData()
				.getComponentDataId().intValue()
				|| getEmploymentType().intValue() == 0) {
			setBooEmploymentPanel(false);

		} else {
			setBooEmploymentPanel(true);
		}
		log.info("Exit  into getEmploymentStatus method");
	}

	/** Responsible to clear only Employment Details Fields */
	public void clearEmploymentDetails() {
		setEmploymentType(null);
		setOccupation(null);
		setEmployer("");
		setBlockNo("");
		setStreetNo("");
		setArea("");
		setOfficeTelNo("");
		setCountry(null);
		setState(null);
		setDistrict(null);
		setCity(null);
		setPostalCode("");
		setDepartment("");
	}

	/******************************************** FOR CONTACT DETAILS *****************************************************/

	/**
	 * Responsible to get all the Contact Type, and populate in Map
	 * 
	 * @return
	 */
	/* public List<ContactType> getFetchContactTypeList() {
	 * SessionStateManage sessionStateManage = new SessionStateManage();
	 * List<ContactType> lstContactType = getiGeneralService().getContactTypes(
	 * new
	 * BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage
	 * .getSessionValue("languageId"):""+1)); *//**
	 * we will use this map when we
	 * will do ADD for Contact details ADD
	 */
	/*for (ContactType contactType : lstContactType) {
	 * mapContactType.put(contactType.getContactTypeId(),
	 * contactType.getContactType());
	 * }
	 * setLstContactType(lstContactType);
	 * return getLstContactType();
	 * } */

	public void getFetchContactTypeList() {
		mapContactType = ruleEngine.getComponentData("Contact Type");
	}

	/**
	 * Responsible to prepare stateList by country change
	 * 
	 * @param event
	 */
	public void generateContactStateList() {
		log.info("Entering into generateContactStateList method");
		telephoneCodeFromDB();
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<StateMasterDesc> stateMaster = getiGeneralService().getStateList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1),
				getContactCountry());
		setLstContactStateList(stateMaster);

		/** We will use this map at Adding time in Contact Details */
		for (StateMasterDesc stateMasterDesc : stateMaster) {
			mapState.put(stateMasterDesc.getFsStateMaster().getStateId(), stateMasterDesc.getStateName());
		}
		ruleEngine.init();

		/** reset State, District, City, as country is changing */
		setContactState(null);
		setContactDistrict(null);
		setContactCity(null);

		setLstContactDistrictList(null);
		setLstContactCityList(null);
		log.info("exit into generateContactStateList method");
	}

	/**
	 * Responsible to populate District depending upon state selection
	 * 
	 * @param event
	 */
	public void generateContactDistrictList() {
		log.info("Entering into generateContactDistrictList method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<DistrictMasterDesc> lstDistrict = getiGeneralService().getDistrictList(
				new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1), getContactCountry(), getContactState());
		setLstContactDistrictList(lstDistrict);

		/** We will use in Add time in Contact Details */
		for (DistrictMasterDesc districtMasterDesc : lstDistrict) {
			mapDistrict.put(districtMasterDesc.getFsDistrictMaster().getDistrictId(), districtMasterDesc.getDistrict());
		}

		/** reset District, City, as state is changing */
		setContactDistrict(null);
		setContactCity(null);
		log.info("Exit into generateContactDistrictList method");
	}

	/**
	 * Responsible to populate city depending upon district selection
	 * 
	 * @param event
	 */
	public void generateContactCityList() {
		log.info("Entering into generateContactCityList method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<CityMasterDesc> lstCity = getiGeneralService().getCityList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1),
				getContactCountry(), getContactState(), getContactDistrict());
		setLstContactCityList(lstCity);

		/** We will use Adding time */
		for (CityMasterDesc cityMasterDesc : lstCity) {
			mapCity.put(cityMasterDesc.getFsCityMaster().getCityId(), cityMasterDesc.getCityName());
		}
		log.info("Entering into generateContactCityList method");
	}

	/**
	 * This method will call when city change happen
	 * It's a do-nothing method, except this after click back button, selected
	 * cityId will not bind with bean
	 * 
	 * @param event
	 */
	public void getContactCity(AjaxBehaviorEvent event) {
	}

	/**
	 * This method is responsible to populate State depending upon country
	 * selection
	 */
	private void populateContactState() {
		log.info("Entering into populateContactState method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<StateMasterDesc> stateMaster = getiGeneralService().getStateList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1),
				getContactCountry());

		for (StateMasterDesc stateMasterDesc : stateMaster) {
			mapState.put(stateMasterDesc.getFsStateMaster().getStateId(), stateMasterDesc.getStateName());
		}

		setLstContactStateList(stateMaster);
		log.info("Exit into populateContactState method");
	}

	/**
	 * This method is responsible to populate district depending upon state
	 * election
	 */
	private void populateContactDistrict() {
		log.info("Entering into populateContactDistrict method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<DistrictMasterDesc> lstDistrict = getiGeneralService().getDistrictList(
				new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1), getContactCountry(), getContactState());

		for (DistrictMasterDesc districtMasterDesc : lstDistrict) {
			mapDistrict.put(districtMasterDesc.getFsDistrictMaster().getDistrictId(), districtMasterDesc.getDistrict());
		}

		setLstContactDistrictList(lstDistrict);
		log.info("Exit into populateContactDistrict method");
	}

	/**
	 * This method is responsible to populate City depending upon district
	 * selection
	 */
	private void populateContactCity() {
		log.info("Entering into populateContactCity method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<CityMasterDesc> lstCity = getiGeneralService().getCityList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1),
				getContactCountry(), getContactState(), getContactDistrict());

		for (CityMasterDesc cityMasterDesc : lstCity) {
			mapCity.put(cityMasterDesc.getFsCityMaster().getCityId(), cityMasterDesc.getCityName());
		}

		setLstContactCityList(lstCity);
		log.info("Exit into populateContactCity method");
	}

	/**
	 * Responsible to Add Contact Details, when ADD button is pressed
	 */
	public void addContactDetails() {
		log.info("Entering into addContactDetails method");

		// setBoorenderSavePanel(true);
		setBoorenderDataTable(true);
		getFetchContactTypeList();
		/**
		 * This boolean is responsible to show and off the duplicate record
		 * message
		 */
		setBooContactDetailsDuplicate(false);

		if (getContactType() != null && getContactCountry() != null && getContactState() != null && getContactDistrict() != null &&(getContactTelephoneNo()!=null || getMobileContact()!=null)) {
			for (ContactDetails contactDetails : lstContactDetails) {

				/** Checking of duplicate records */
				if (getContactType().intValue() == contactDetails.getContactType().intValue() && getContactCountry().intValue() == contactDetails.getCountry().intValue()
						&& getContactState().intValue() == contactDetails.getState().intValue() && getContactDistrict().intValue() == contactDetails.getDistrict().intValue()
						&& getContactCity().intValue() == contactDetails.getCity().intValue() && getContactLocalArea().equalsIgnoreCase(contactDetails.getLocalArea())
						&& getContactStreetNo().equalsIgnoreCase(contactDetails.getStreet()) && getContactFlat().equalsIgnoreCase(contactDetails.getFlat())
						&& getContactTelephoneNo().equalsIgnoreCase(contactDetails.getTelephone()) && getContactBlockNo().equalsIgnoreCase(contactDetails.getBlock())) {

					/** Showing the Duplicate record message */
					setBooContactDetailsDuplicate(true);
					break;
				}
			}

			/** If not duplicate then we will add the record in datatable */
			if (!getBooContactDetailsDuplicate()) {
				ContactDetails contactDetail = new ContactDetails();

				contactDetail.setContactType(getContactType());
				contactDetail.setStrContactType(mapContactType.get(getContactType()));
				contactDetail.setCountry(getContactCountry());
				contactDetail.setStrCountry(iGeneralService.getCountryName(getContactCountry()));
				contactDetail.setState(getContactState());
				contactDetail.setStrState(mapState.get(getContactState()));
				contactDetail.setDistrict(getContactDistrict());
				contactDetail.setStrDistrict(iGeneralService.getDistrictName(sessionStateManage.getLanguageId(), getContactCountry(), getContactState(), getContactDistrict()));
				contactDetail.setCity(getContactCity());
				contactDetail.setStrCity(mapCity.get(getContactCity()));
				contactDetail.setFlat(getContactFlat());
				contactDetail.setBuildingNo(getBuildingNo());
				contactDetail.setLocalArea(getContactLocalArea());
				contactDetail.setStreet(getContactStreetNo());
				contactDetail.setBlock(getContactBlockNo());
				contactDetail.setTelephone(getContactTelephoneNo());
				contactDetail.setMobileContact(getMobileContact());
				contactDetail.setTelephoneCode(getTelephoneCode());
				contactDetail.setPkContactDetails(new BigDecimal(0));

				lstContactDetails.add(contactDetail);
				clearContactDetails();
				setRenderHideCountryId(false);
				setMsgMobileOrTel(false);
				
			}
		} else {
			RequestContext.getCurrentInstance().execute("isertDetails.show();");

		}

		/** Responsible to clear the Contact details fields */
		if (getBooContactDetailsDuplicate()) {
			clearContactDetails();
			setBooContactDetailsDuplicate(true);
		} else {
			clearContactDetails();
		}
		log.info("Exit into addContactDetails method");
	}

	/** Responsible to clear only Contact Details Fields */
	public void clearContactDetails() {
		setContactType(null);
		setContactLocalArea("");
		setContactCountry(null);
		setContactStreetNo("");
		setContactState(null);
		setContactTelephoneNo("");
		setContactDistrict(null);
		setContactFlat("");
		setContactCity(null);
		setContactBlockNo("");
		setBuildingNo(null);
		setMobileContact(null);

		lstContactStateList.clear();
		lstContactDistrictList.clear();
		lstContactCityList.clear();
		setRenderHideCountryId(false);
		setCountryName(null);

		setBooContactDetailsDuplicate(false);
	}

	/**
	 * *************************************************************************
	 * ***************************************
	 */
	/***
	 * This is responsible to save image, by next button
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	public void managingImageSaveByNextPanel() throws SQLException, IOException {
		log.info("Entering into managingImageSaveByNextPanel method");
		/** Rendering the Panel */
		setBooPersonalInformation(false);
		setBooEmploymentInformation(true);
		setBooContactDetails(false);

		/** once reach to 3rd panel, the that's final */
		if (getSaveUptoPanel() != 3) {
			setSaveUptoPanel(2);
		}

		/** Image saving */
		/* image = new DocumentImg();
		 * image.setImage(storeImage());
		 * image.setUploadDate(new Date());
		 * image.setImageName(getFile().getFileName());
		 * image.setImgStatus("1");
		 * image.setUploadDate(new Date()); *//** Manipulating save/update */
		/*if(getPkImage()!=null) {
		 * image.setImgId(getPkImage());
		 * }
		 * 
		 * if(image.getImage().length()> 0 && !getBooDisableSave()){
		 * getRemOnlineReg().saveImage(image);
		 * setPkImage(image.getImgId());
		 * } */

		log.info("Exit into managingImageSaveByNextPanel method");
	}

	/***
	 * This is responsible to save Image from the first panel
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
/* public void managingImageSaveFromFirstPanelSave() throws SQLException,
 * IOException {
 * log.info("Entering into managingImageSaveFromFirstPanelSave method");
 * image = new DocumentImg();
 * image.setImage(storeImage());
 * image.setUploadDate(new Date());
 * image.setImageName(getFile().getFileName());
 * image.setImgStatus("1");
 * image.setUploadDate(new Date()); *//** Here is deciding save or update */
	/*if(getPkImage()!=null) {
	 * image.setImgId(getPkImage());
	 * } *//**
	 * save will happen only if there is any image and this person not
	 * went branch
	 */
	/*if(image.getImage().length()>0 && !getBooDisableSave()){
	 * getRemOnlineReg().saveImage(image);
	 * }
	 * log.info("Exit into managingImageSaveFromFirstPanelSave method");
	 * } */
	/***
	 * when Next button will press from Personal Information, we need to save
	 * image also at that time
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws SerialException
	 */
	public void nextFromPersonalInfo() throws SerialException, IOException, SQLException {
		getContactTypeList();
		log.info("Entering into nextFromPersonalInfo method");
		// log.debug("Image Upload Status : "+getBooImageUploaded()+" Viewed : "+getBooViewed());

		/* if(!getBooImageUploaded() && !getBooViewed()) {
		 * setBooImageValidation(true);
		 * } else {
		 * setBooImageValidation(false);
		 * managingImageSaveByNextPanel();
		 * } */

		if (getBooMobilecheck() == false) {
			managingImageSaveByNextPanel();
			setCountry(sessionStateManage.getCountryId());
			setCountryName(iGeneralService.getCountryName(sessionStateManage.getLanguageId(), getCountry()));

			

		}
		log.info("Exit into nextFromPersonalInfo method");
	}

	/***
	 * Back button is pressed from Employee panel, back to Personal Information
	 * panel
	 */
	public void backFromEmp() {
		setBooPersonalInformation(true);
		setBooEmploymentInformation(false);
		setBooContactDetails(false);
	}

	/***
	 * Next button is pressed from Employee Panel, next panel is contact details
	 */
	public void nextFromEmp() {
		setBooPersonalInformation(false);
		setBooEmploymentInformation(false);
		setBooContactDetails(true);
		setRenderHideCountryId(false);
		setSaveUptoPanel(3);
		setCountryName(null);
		setContactCountry(null);

		// setBoorenderSavePanel(false);
		setBoorenderDataTable(false);
		if (lstContactDetails.size() > 0) {
			// setBoorenderSavePanel(true);
			setBoorenderDataTable(true);
		}
	}

	/***
	 * Back button pressed from Contact Details
	 */
	public void backFromContactDetails() {
		setBooPersonalInformation(false);
		setBooEmploymentInformation(true);
		setBooContactDetails(false);
		// setBoorenderSavePanel(false);
		setBoorenderDataTable(false);
	}

	/***
	 * Responsible to fetch data after login
	 * 
	 * @param userName
	 * @param customerId
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public String view(String userName, String customerId, String emailId) throws SQLException, IOException, ParseException {

		log.info("Entering into view method");
		/**
		 * Clearing cache for panel display
		 */
		getCountryBranch();
		getCountryAlphCode();
		if (getCountryLphaCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {
			setRenderKuwaitId(true);
			setRenderOtherId(false);
		} else {
			setRenderKuwaitId(false);
			setRenderOtherId(true);
		}

		setDownloadFile(null);
		setBooHasImage(false);
		setBooImageUploaded(false);
		setBooPersonalInformation(true);
		setBooEmploymentInformation(false);
		setBooContactDetails(false);
		setBooRegistered(false);
		setIdNumber("");
		setPkImage(null);

		setEmail(emailId);
		setIdNumber(userName);

		/** ID proof minimum date manipulation */
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, +0);
		Date today90 = cal.getTime();
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
		String minExpDate = form.format(today90);
		setExpDateCheck(minExpDate);

		/**
		 * This condition will return true if the customer is already done first
		 * time online registration
		 */
		if (!customerId.equalsIgnoreCase("empty")) {
			setBooViewed(true);
			/** Responsible to clear the bean */
			clear();
			lstContactDetails.clear();
			setBooDisableSave(false);

			/** If already registered then, it will show first panel */
			setBooPersonalInformation(true);
			setBooEmploymentInformation(false);
			setBooContactDetails(false);

			/** We are getting customerID from from our login table */
			setPkCustomerId(new BigDecimal(customerId));

			/** populating data from customer table */
			lstCustomer.addAll(getRemOnlineReg().getFsCustomer(getPkCustomerId()));
			setBooReadonlyIdNumber(lstCustomer.size() > 0 ? true : false);

			if (getCountryLphaCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {
				setShowDob(lstCustomer.get(0).getDateOfBirth() == null ? "" : formatter.format(lstCustomer.get(0).getDateOfBirth()));
				setBooCivilId(true);
				setBooOtherId(false);
			} else {
				setBooCivilId(false);
				setBooOtherId(true);
				setDob(lstCustomer.get(0).getDateOfBirth());
			}
			/** setDateOfBirth(lstCustomer.get(0).getDateOfBirth()); */
			setTitle(lstCustomer.get(0).getTitle());
			setFirstName(lstCustomer.get(0).getFirstName());
			setMiddleName(lstCustomer.get(0).getMiddleName());
			setLastName(lstCustomer.get(0).getLastName());
			setShortName(lstCustomer.get(0).getShortName());
			setLocalTitle(lstCustomer.get(0).getTitleLocal());
			setLocalFirstName(lstCustomer.get(0).getFirstNameLocal());
			setLocalMiddleName(lstCustomer.get(0).getMiddleNameLocal());
			setLocalLastName(lstCustomer.get(0).getLastNameLocal());
			setLocalShortName(lstCustomer.get(0).getShortNameLocal());
			setNationality(lstCustomer.get(0).getFsCountryMasterByNationality().getCountryId());
			setGender(lstCustomer.get(0).getGender());
			setMobileNo(lstCustomer.get(0).getMobile());
			setEmail(lstCustomer.get(0).getEmail());
			setAlternateEmail(lstCustomer.get(0).getAlterEmailId());
			setTokenKey(lstCustomer.get(0).getTokenKey());

			/**
			 * Here the icon for growling will be shown, because here image is
			 * available in Database
			 */
			setFsCustomerCreatedBy(lstCustomer.get(0).getCreatedBy());
			setFsCustomerCreatedDate(lstCustomer.get(0).getCreationDate());

			// fetch Customer Reference Added by Nazish
			setUpdateCustomerRefNo(lstCustomer.get(0).getCustomerReference());

			/** Once customer went to branch, we need to off update by online */
			//setBooDisableSave(lstCustomer.get(0).getActivatedInd().equalsIgnoreCase("0") ? false : true);
			setBooDisableSave(lstCustomer.get(0).getActivatedInd().equalsIgnoreCase(Constants.CUST_INACTIVE_INDICATOR) ? false : true);

			/** populating data from customer proof table */

			/* mapIdentityType = ruleEngine.getComponentData("Identity Type");
			 * BigDecimal identityTpeId = null;
			 * for (Map.Entry<BigDecimal, String> entry :
			 * mapIdentityType.entrySet()) {
			 * if(entry.getValue().equalsIgnoreCase(CIVILID)){
			 * identityTpeId = entry.getKey();
			 * break;
			 * }
			 * } */
			lstCustomerIdProof = getRemOnlineReg().getProofDetails(getPkCustomerId());
			if (getCountryLphaCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {
				setIdType(Constants.CIVILID);
			} else {
				setIdTypeId(lstCustomerIdProof.get(0).getFsBizComponentDataByIdentityTypeId().getComponentDataId());
			}

			setPkCustomerIdProof(lstCustomerIdProof.get(0).getCustProofId());
			setIdNumber(lstCustomerIdProof.get(0).getIdentityInt());
			setIdExpDate(lstCustomerIdProof.get(0).getIdentityExpiryDate());
			setFsCustomerIdProofCreatedBy(lstCustomerIdProof.get(0).getCreatedBy());
			setFsCustomerIdProofCreatedDate(lstCustomerIdProof.get(0).getCreationDate());

			String dob = null;
			if (getIdNumber().charAt(0) == '2') {
				dob = getIdNumber().substring(5, 7) + "/" + getIdNumber().substring(3, 5) + "/19" + getIdNumber().substring(1, 3);
			} else {
				dob = getIdNumber().substring(5, 7) + "/" + getIdNumber().substring(3, 5) + "/20" + getIdNumber().substring(1, 3);
			}
			Date date = formatter.parse(dob);
			setDateOfBirth(date);
			setShowDob(formatter.format(date));

			/** Responsible to populate image */
			/*setPkImage(lstCustomerIdProof.get(0).getFsDocumentImg().getImgId());
			showImage(lstCustomerIdProof.get(0).getFsDocumentImg().getImage(), lstCustomerIdProof.get(0).getFsDocumentImg().getImageName(), lstCustomerIdProof.get(0).getFsDocumentImg().getImgId());*/

			/** populating Customer Employment Information */
			lstCustomerEmployment.addAll(getRemOnlineReg().getFsCustEmpInfo(getPkCustomerId()));
			/** If there is any data exist, setting the value */
			if (lstCustomerEmployment != null && lstCustomerEmployment.size() > 0) {
				setPkCustomerEmployeeId(lstCustomerEmployment.get(0).getCustEmpInfoId());
				setEmploymentType(lstCustomerEmployment.get(0).getFsBizComponentDataByEmploymentTypeId().getComponentDataId());
				if(lstCustomerEmployment.get(0).getFsBizComponentDataByOccupationId() != null){
					setOccupation(lstCustomerEmployment.get(0).getFsBizComponentDataByOccupationId().getComponentDataId());
				}
				setDepartment(lstCustomerEmployment.get(0).getDepartment());
				setEmployer(lstCustomerEmployment.get(0).getEmployerName());
				setBlockNo(lstCustomerEmployment.get(0).getBlock());
				setStreetNo(lstCustomerEmployment.get(0).getStreet());
				setArea(lstCustomerEmployment.get(0).getArea());
				setOfficeTelNo(lstCustomerEmployment.get(0).getOfficeTelephone());
				if(lstCustomerEmployment.get(0).getFsCountryMaster()!=null){
				setCountry(lstCustomerEmployment.get(0).getFsCountryMaster().getCountryId());
				}
				populateState();
				if(lstCustomerEmployment.get(0).getFsStateMaster()!=null){
				setState(lstCustomerEmployment.get(0).getFsStateMaster().getStateId());
				}
				populateDistrict();
				if(lstCustomerEmployment.get(0).getFsDistrictMaster()!=null){
				setDistrict(lstCustomerEmployment.get(0).getFsDistrictMaster().getDistrictId());
				}
				populateCity();
				if(lstCustomerEmployment.get(0).getFsCityMaster()!=null){
				setCity(lstCustomerEmployment.get(0).getFsCityMaster().getCityId());
				}
				setPostalCode(lstCustomerEmployment.get(0).getPostal());
				setFsCustomerEmpCreatedBy(lstCustomerEmployment.get(0).getCreatedBy());
				setFsCustomerEmpCreatedDate(lstCustomerEmployment.get(0).getCreationDate());

				/**
				 * This boolean is responsible to render the panel depending
				 * upon Employment Type
				 */
				if (getEmploymentType().intValue() == getiGeneralService()
						.getComponentId(Constants.EMPLOYMENTTYPE, sessionStateManage.getLanguageId()).getFsBizComponentData()
						.getComponentDataId().intValue()) {
					setBooEmploymentPanel(false);
				} else {
					setBooEmploymentPanel(true);
				}
				setBooEmployed(true);
			} else {
				setEmploymentType(new BigDecimal(getiGeneralService()
						.getComponentId(Constants.EMPLOYMENTTYPE, sessionStateManage.getLanguageId()).getFsBizComponentData()
						.getComponentDataId().intValue()));
				/**
				 * This boolean is responsible to render the panel depending
				 * upon Employment Type
				 */
				setBooEmploymentPanel(false);
				setBooEmployed(false);
			}

			/** Fetching Contact Details Data */
			lstContactDetailsModel.clear();
			lstContactDetailsModel.addAll(getRemOnlineReg().getFsContactDetails(getPkCustomerId()));
			if (lstContactDetailsModel != null && lstContactDetailsModel.size() > 0) {
				lstContactDetails.clear();
				//ContactDetails contactetails = null;
				for (ContactDetail cdetail : lstContactDetailsModel) {
					
					ContactDetails addContactDetailBean = new ContactDetails();
		              if(cdetail.getFsBizComponentDataByContactTypeId()!=null){
		                getFetchContactTypeList();
						addContactDetailBean.setStrContactType(mapContactType.get(cdetail.getFsBizComponentDataByContactTypeId().getComponentDataId()));
						addContactDetailBean.setContactType(cdetail.getFsBizComponentDataByContactTypeId().getComponentDataId());
		           }
						if(cdetail.getFsCountryMaster()!=null){
							getCountryNameList();
						addContactDetailBean.setStrCountry(mapCountry.get(cdetail.getFsCountryMaster().getCountryId()));
						addContactDetailBean.setCountry(cdetail.getFsCountryMaster().getCountryId());
						}
						if(cdetail.getFsStateMaster()!=null){
           
						addContactDetailBean.setStrState(iGeneralService.getStateName(sessionStateManage.getLanguageId(), cdetail.getFsCountryMaster().getCountryId(), cdetail.getFsStateMaster().getStateId()));
						addContactDetailBean.setState(cdetail.getFsStateMaster().getStateId());
						}
						if(cdetail.getFsDistrictMaster()!=null){
						populateContactDistrict();
						addContactDetailBean.setStrDistrict(iGeneralService.getDistrictName(sessionStateManage.getLanguageId(), cdetail.getFsCountryMaster().getCountryId(), cdetail.getFsStateMaster().getStateId(),cdetail.getFsDistrictMaster().getDistrictId()));
						addContactDetailBean.setDistrict(cdetail.getFsDistrictMaster().getDistrictId());
						}
						if(cdetail.getFsCityMaster()!=null){
							populateContactCity();
						addContactDetailBean.setStrCity(iGeneralService.getCityName(sessionStateManage.getLanguageId(), cdetail.getFsCountryMaster().getCountryId(), cdetail.getFsStateMaster().getStateId(),cdetail.getFsDistrictMaster().getDistrictId(),cdetail.getFsCityMaster().getCityId()));
						addContactDetailBean.setCity(cdetail.getFsCityMaster().getCityId());
						}
						addContactDetailBean.setLocalArea(cdetail.getArea());
						addContactDetailBean.setFlat(cdetail.getFlat());
						addContactDetailBean.setBuildingNo(cdetail.getBuildingNo());
						addContactDetailBean.setBlock(cdetail.getBlock());
						addContactDetailBean.setCreatedBy(cdetail.getCreatedBy());
		        		addContactDetailBean.setCreationDate(cdetail.getCreationDate());
		        		addContactDetailBean.setPkContactDetails(cdetail.getContactDetailId());
						addContactDetailBean.setTelephone(cdetail.getTelephone());
						addContactDetailBean.setStreet(cdetail.getStreet());
						addContactDetailBean.setMobileContact(cdetail.getMobile());
						addContactDetailBean.setTelephoneCode(cdetail.getTelephoneCode());
						lstContactDetails.add(addContactDetailBean);

				}
				clearContactDetails();
			}
		} else {
			clear();
			lstContactDetails.clear();
			setBooDisableSave(false);
			setBooViewed(false);
			setBooHasImage(false);
						if (getCountryLphaCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {
							//** Date of birth manipulation from CIVIL ID *//*
							String id = userName;
							String dob = null;
							if (id.charAt(0) == '2') {
								dob = id.substring(5, 7) + "/" + id.substring(3, 5) + "/19" + id.substring(1, 3);
							} else {
								dob = id.substring(5, 7) + "/" + id.substring(3, 5) + "/20" + id.substring(1, 3);
							}

							Date date = formatter.parse(dob);
							setDateOfBirth(date);
							setShowDob(formatter.format(date));
							setBooCivilId(true);
							setBooOtherId(false);


			}

		}

		log.info("Exit into view method");
		return "remmiterInfo";

	}

	/***
	 * Responsible to fetch Image and populate in page
	 * 
	 * @param image
	 * @param fullImageName
	 * @throws SQLException
	 * @throws IOException
	 */
	public void showImage(Blob image, String fullImageName, BigDecimal imageId) throws SQLException, IOException {
		log.info("Entering into showImage method");
		/** Just a confirmation, that there is a Image exist */
		if (image != null) {
			String imageExtention = fullImageName.substring(fullImageName.lastIndexOf("."));
			String imageName = fullImageName.substring(0, fullImageName.lastIndexOf(".") - 1);
			byte[] blobAsBytes = image.getBytes(1, (int) image.length());
			InputStream stream = new ByteArrayInputStream(blobAsBytes);
			if (imageId.intValue() == 100) {
				setBooHasImage(false);
			} else {
				setBooHasImage(true);
			}
			downloadFile = new DefaultStreamedContent(stream, "image/jpg", imageName + imageExtention);
			log.info("Exit into showImage method");
		}
	}

	/***
	 * Responsible to populate Date of birth from Civil Id and get the minimum
	 * expire date of ID
	 */
	public void popDob() {}

	/***
	 * Responsible to clear all the fields
	 */
	public void clear() {
		clearPersonalInformation();
		clearEmploymentDetails();
		clearContactDetails();

	}

	/***
	 * Responsible to return a blob format of image
	 * 
	 * @return
	 * @throws IOException
	 * @throws SerialException
	 * @throws SQLException
	 */
	public Blob storeImage(InputStream inputStream) throws IOException, SerialException, SQLException {
		log.info("Entering into storeImage method");
		/* InputStream stream = null;
		 * try {
		 * stream = file.getInputstream();
		 * }catch(Exception e){
		 * e.printStackTrace();
		 * } */
		return new javax.sql.rowset.serial.SerialBlob(readFully(inputStream));
	}

	/***
	 * Responsible to prepare a byte array from InputStream
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public byte[] readFully(InputStream input) throws IOException {
		byte[] buffer = new byte[8192];
		int bytesRead;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
		return output.toByteArray();
	}

	/***
	 * This will control upto which panel need to save
	 * 
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 * @throws SerialException
	 */
	public void panelSaveController() throws SerialException, IOException, SQLException, ParseException {

		/* Checking of Image Validation */
		/* if(!getBooImageUploaded() && !getBooViewed()) {
		 * setBooImageValidation(true);
		 * } else {
		 * setBooImageValidation(false);
		 * if(getSaveUptoPanel() == 1) {
		 * savePersonalInfoPanel("direct");
		 * } else if(getSaveUptoPanel() == 2) {
		 * savePersonalInfoPanelAndEmploymentPanel("direct");
		 * } else {
		 * saveAllPanel("direct");
		 * }
		 * } */
		if (getBooMobilecheck() == false) {
			if (getSaveUptoPanel() == 1) {
				savePersonalInfoPanel("direct");
			} else if (getSaveUptoPanel() == 2) {
				savePersonalInfoPanelAndEmploymentPanel("direct");
			} else {
				saveAllPanel("direct");
			}

		}
	}

	/***
	 * Responsible to send mail, with Customer ID with the Token Number
	 */
	public void createTokenId() {
		String strToken = null;
		try {
			strToken = getTokenGeneration().getRandomIdentifier(8);
		} catch (Exception e) {
			log.info("Problem in Token Generation" + e);
		}

		boolean tokenConfirm = true;
		while (tokenConfirm) {
			try {
				if (getRemOnlineReg().CheckTokenAvailable(strToken).size() > 0) {
					tokenConfirm = true;
					strToken = getTokenGeneration().getRandomIdentifier(8);
				} else {
					tokenConfirm = false;
				}
			} catch (Exception e) {
				tokenConfirm = false;
			}
		}
		setTokenKey(strToken);

	}

	/***
	 * Responsible to save data
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws SerialException
	 * @throws ParseException
	 */
	public void savePersonalInfoPanel(String fromWhereCalling) throws SerialException, IOException, SQLException, ParseException {
		log.info("Entering into savePersonalInfoPanel method");
		/** Country ID save */
		countryMaster = new CountryMaster();
		countryMaster.setCountryId(sessionStateManage.getCountryId());

		/** Nationality Id save */
		CountryMaster nationality = new CountryMaster();
		nationality.setCountryId(getNationality());

		/** save company */
		companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(sessionStateManage.getCompanyId());

		/** Calculating Language ID */
		int languageID = 1;
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("languageCode")) {
			languageID = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("languageCode").toString().equalsIgnoreCase("ar") ? 2 : 1;
		}
		LanguageType langType = new LanguageType();
		langType.setLanguageId(new BigDecimal(languageID));

		/** Customer Type */
		BizComponentData customerType = new BizComponentData();
		customerType.setComponentDataId(getiGeneralService().getComponentId(Constants.CUSTOMERTYPE_INDU, new BigDecimal(languageID)).getFsBizComponentData().getComponentDataId());

		/** Group ID */
		BizComponentData companyGroup = new BizComponentData();
		companyGroup.setComponentDataId(getiGeneralService().getComponentId(Constants.GROUPID, new BigDecimal(languageID)).getFsBizComponentData().getComponentDataId());

		/** Customer base table */
		customer = new Customer();
		customer.setFsCountryMasterByCountryId(countryMaster);
		customer.setFsCompanyMaster(companyMaster);
		customer.setFsBizComponentDataByCustomerTypeId(customerType);
		customer.setFsLanguageType(langType);
		customer.setFsBizComponentDataByGroupId(companyGroup);
		customer.setFsCountryMasterByNationality(nationality);
		customer.setTitle(getTitle());
		customer.setFirstName(getFirstName());
		customer.setMiddleName(getMiddleName());
		customer.setLastName(getLastName());
		customer.setShortName(getShortName());
		customer.setTitleLocal(getLocalTitle());
		customer.setFirstNameLocal(getLocalFirstName());
		customer.setMiddleNameLocal(getLocalMiddleName());
		customer.setLastNameLocal(getLocalLastName());
		customer.setShortNameLocal(getLocalShortName());
		customer.setGender(getGender());
		customer.setMobile(getMobileNo());
		customer.setEmail(getEmail());
		customer.setAlterEmailId(getAlternateEmail());
		
		customer.setBranchCode(getCountryBranchId());
		customer.setIsActive(Constants.No);

		if (getCountryLphaCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {
			customer.setDateOfBirth(getDateOfBirth());
		} else {
			customer.setDateOfBirth(getDob());
		}
		customer.setTokenKey(getTokenKey());
		//customer.setActivatedInd("0");
		customer.setActivatedInd(Constants.CUST_INACTIVE_INDICATOR);

		/**
		 * Managing save or update, if condition will return true from second
		 * time(update time)
		 */
		if (getPkCustomerId() != null) {
			customer.setCustomerId(getPkCustomerId());
			customer.setUpdatedBy(sessionStateManage.isExists("userName") ? sessionStateManage.getSessionValue("userName") : "root");
			//customer.setLastUpdated(new Date());
			customer.setLastUpdated(getCurrentTime());
			customer.setCreatedBy(getFsCustomerCreatedBy());
			customer.setCreationDate(getFsCustomerCreatedDate());
			customer.setCustomerReference(getUpdateCustomerRefNo());
			getRemOnlineReg().saveCustomer(customer);
		} else {
			customer.setCreatedBy(sessionStateManage.isExists("userName") ? sessionStateManage.getSessionValue("userName") : "root");
			//customer.setCreationDate(new Date());
			customer.setCreationDate(getCurrentTime());
			
			BigDecimal custRef = customerRegistrationService.callProcedureCustReferenceNumber(getCompanyCodeByCompanyId() , Constants.DOCUMENT_CODE_FOR_CUSTOMER , getDealYearbyDate() , sessionStateManage.getBranchId());
			
			if(custRef != null){
				customer.setCustomerReference(custRef);
			}

			/** Responsible to create token randomly */
			createTokenId();
			customer.setTokenKey(getTokenKey());
			/** save the data in customer table, with token ID */
			getRemOnlineReg().saveCustomer(customer);

			/**
			 * After save first time we have to save the customer ID primary key
			 * field, by which we will send mail to that customer
			 */
			setPkCustomerId(customer.getCustomerId());

			/** First Time will go the mail, with the generated Token */
			getMailService().sendTokenMail(getEmail(), "Successfully Registered", getPkCustomerId().toPlainString(), getTokenKey());
		}

		
		CustomerIdProof idProof = new CustomerIdProof();
		BizComponentData identityType = new BizComponentData();
		if (getCountryLphaCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {

			mapIdentityType = ruleEngine.getComponentData("Identity Type");
			for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
				if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
					identityType.setComponentDataId(entry.getKey());
					break;
				}
			}

			idProof.setFsBizComponentDataByIdentityTypeId(identityType);
		} else {
			identityType.setComponentDataId(getIdTypeId());
			idProof.setFsBizComponentDataByIdentityTypeId(identityType);

		}
		BizComponentData identityFor = new BizComponentData();
		identityFor.setComponentDataId(getiGeneralService().getComponentId(Constants.IDENTITYFOR, new BigDecimal(languageID)).getFsBizComponentData().getComponentDataId());

		idProof.setFsCustomer(customer);
		idProof.setFsBizComponentDataByCustomerTypeId(customerType);
		idProof.setFsLanguageType(langType);

		idProof.setFsBizComponentDataByIdentityFor(identityFor);
		idProof.setIdentityInt(getIdNumber());
		//idProof.setIdentityStatus("1");
		idProof.setIdentityStatus(Constants.CUST_INACTIVE_INDICATOR);
		idProof.setIdentityExpiryDate(getIdExpDate());

		if (!getBooImageUploaded()) {
			image = new DocumentImg();
			if (getPkImage() != null) {
				image.setImgId(getPkImage());
			} else {
				image.setImgId(new BigDecimal(100));
				image.setImageName("empty.jpg");
			}
		}
		log.debug("Image ID Before Save :     " + image.getImgId());
		//idProof.setFsDocumentImg(image);

		/** Managing save or update */
		if (getPkCustomerIdProof() != null) {
			idProof.setCustProofId(getPkCustomerIdProof());
			idProof.setUpdatedBy(sessionStateManage.isExists("userName") ? sessionStateManage.getSessionValue("userName") : "root");
			//idProof.setLastUpdatedDate(new Date());
			idProof.setLastUpdatedDate(getCurrentTime());
			idProof.setCreatedBy(getFsCustomerIdProofCreatedBy());
			idProof.setCreationDate(getFsCustomerIdProofCreatedDate());
		} else {
			idProof.setCreatedBy(sessionStateManage.isExists("userName") ? sessionStateManage.getSessionValue("userName") : "root");
			//idProof.setCreationDate(new Date());
			idProof.setCreationDate(getCurrentTime());
		}

		try {
			log.debug("Entered");
			getRemOnlineReg().saveCustomerEmploymentProofInfo(idProof);
		} catch (Exception e) {
			log.error("Exception occured in RemmiterInfoManageBean ::savePersonalInfoPanel method ", e);
		}

		/** It will update Login Table first time by Customer ID */
		if (!getBooViewed()) {
			getRemOnlineReg().updateLoginCustomerId(sessionStateManage.getSessionValue("userName"), customer.getCustomerId(), sessionStateManage.getCompanyId());
		}

		/** It will redirect only when it will direct call, from that panel */
		if (fromWhereCalling.equalsIgnoreCase("direct")) {
			try {
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

				context.redirect("../common/successonline.xhtml");
				// context.invalidateSession();
			} catch (Exception e) {
				log.error("Problem to Redirect the page : ", e);
			}
		}
		log.info("Exit  into savePersonalInfoPanel method");
	}

	/***
	 * Responsible to save Personal Information panel and Employment Information
	 * panel
	 * 
	 * @param fromWhereCalling
	 * @throws SerialException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ParseException
	 *             Concept: If any customer choose Un Employed, then we will not
	 *             insert data in customer Employment Information table
	 */
	public void savePersonalInfoPanelAndEmploymentPanel(String fromWhereCalling) throws SerialException, IOException, SQLException, ParseException {
		savePersonalInfoPanel("fromEmployment");

		log.debug("////////////////////////////////////////////////////");
		CustomerEmploymentInfo employmentInfo = new CustomerEmploymentInfo();

		/** set Country */
		countryMaster = new CountryMaster();
		countryMaster.setCountryId(getCountry());
		employmentInfo.setFsCountryMaster(countryMaster);

		/** set Company */
		companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(sessionStateManage.getCompanyId());
		employmentInfo.setFsCompanyMaster(companyMaster);
		employmentInfo.setIsActive(Constants.Yes);

		/** Calculating Language ID */
		int languageID = 1;
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("languageCode")) {
			languageID = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("languageCode").toString().equalsIgnoreCase("ar") ? 2 : 1;
		}
		LanguageType langType = new LanguageType();
		langType.setLanguageId(new BigDecimal(languageID));
		employmentInfo.setFsLanguageType(langType);

		/** save customer */
		employmentInfo.setFsCustomer(customer);

		// employmentInfo.setOccupation(getOccupation());
		BizComponentData occupation = new BizComponentData();
		occupation.setComponentDataId(getOccupation());
		employmentInfo.setFsBizComponentDataByOccupationId(occupation);

		/** Employment Type */
		// EmploymentType employemntType = new EmploymentType();
		// employemntType.setEmploymentTypeId(getEmploymentType());
		// employmentInfo.setFsEmploymentType(employemntType);

		BizComponentData employemntType = new BizComponentData();
		employemntType.setComponentDataId(getEmploymentType());
		employmentInfo.setFsBizComponentDataByEmploymentTypeId(employemntType);

		employmentInfo.setEmployerName(getEmployer());
		employmentInfo.setDepartment(getDepartment());

		/** saving state */
		StateMaster state = new StateMaster();
		state.setStateId(getState());
		employmentInfo.setFsStateMaster(state);

		/** saving District */
		DistrictMaster district = new DistrictMaster();
		district.setDistrictId(getDistrict());
		employmentInfo.setFsDistrictMaster(district);

		/** saving city */
		CityMaster cityMaster = new CityMaster();
		cityMaster.setCityId(getCity());
		employmentInfo.setFsCityMaster(cityMaster);

		employmentInfo.setArea(getArea());
		employmentInfo.setBlock(getBlockNo());
		employmentInfo.setStreet(getStreetNo());
		employmentInfo.setPostal(getPostalCode());
		employmentInfo.setOfficeTelephone(getOfficeTelNo());

		/** This condition will return true from 2nd time */
		if (getPkCustomerEmployeeId() != null) {
			employmentInfo.setCustEmpInfoId(getPkCustomerEmployeeId());
			employmentInfo.setUpdatedBy(sessionStateManage.isExists("userName") ? sessionStateManage.getSessionValue("userName") : "root");
			//employmentInfo.setLastUpdated(new Date());
			employmentInfo.setLastUpdated(getCurrentTime());
			employmentInfo.setCreatedBy(getFsCustomerEmpCreatedBy());
			employmentInfo.setCreationDate(getFsCustomerEmpCreatedDate());
		} else {
			employmentInfo.setCreatedBy(sessionStateManage.isExists("userName") ? sessionStateManage.getSessionValue("userName") : "root");
			//employmentInfo.setCreationDate(new Date());
			employmentInfo.setCreationDate(getCurrentTime());
		}

		/** Responsible to save */
		if (getEmploymentType().intValue() == getiGeneralService()
				.getComponentId(Constants.EMPLOYMENTTYPE, sessionStateManage.getLanguageId()).getFsBizComponentData()
				.getComponentDataId().intValue()) {
			if (getBooEmployed()) {
				getRemOnlineReg().saveCustomerEmploymentInfo(employmentInfo);
			}
		} else {
			getRemOnlineReg().saveCustomerEmploymentInfo(employmentInfo);
		}

		/** It will redirect only when it will direct call, from that panel */
		if (fromWhereCalling.equalsIgnoreCase("direct")) {
			try {
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

				context.redirect("../common/successonline.xhtml");
				// context.invalidateSession();
			} catch (Exception e) {
				log.error("Problem to redirect: ", e);
			}
		}

	}

	/***
	 * Responsible to Save All panel
	 * 
	 * @param fromWhereCalling
	 * @throws SerialException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void saveAllPanel(String fromWhereCalling) throws SerialException, IOException, SQLException, ParseException {
		savePersonalInfoPanelAndEmploymentPanel("fromContactDetails");
		ContactDetail contactDetailModel = null;
		/**
		 * We will iterate the loop, and will get the Objects and set the value
		 * in ContactDetails Model class for saving
		 */
		for (ContactDetails contactDetails : lstContactDetails) {

			contactDetailModel = new ContactDetail();
			contactDetailModel.setFsCustomer(customer);

			// ContactType contactType = new ContactType();
			// contactType.setContactTypeId(contactDetails.getContactType());
			// contactDetailModel.setFsContactType(contactType);

			if(contactDetails.getContactType()!=null){
			BizComponentData contactType = new BizComponentData();
			contactType.setComponentDataId(contactDetails.getContactType());
			contactDetailModel.setFsBizComponentDataByContactTypeId(contactType);
			}

			int languageID = 1;
			if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("languageCode")) {
				languageID = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("languageCode").toString().equalsIgnoreCase("ar") ? 2 : 1;
			}
			LanguageType langType = new LanguageType();
			langType.setLanguageId(new BigDecimal(languageID));
			contactDetailModel.setFsLanguageType(langType);

			if(contactDetails.getCountry()!=null){
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(contactDetails.getCountry());
			contactDetailModel.setFsCountryMaster(countryMaster);
			}

			if(contactDetails.getState()!=null){
			StateMaster stateMaster = new StateMaster();
			stateMaster.setStateId(contactDetails.getState());
			contactDetailModel.setFsStateMaster(stateMaster);
			}

			if(contactDetails.getDistrict()!=null){
			DistrictMaster districtMaster = new DistrictMaster();
			districtMaster.setDistrictId(contactDetails.getDistrict());
			contactDetailModel.setFsDistrictMaster(districtMaster);
			}

			if(contactDetails.getCity()!=null){
			CityMaster cityMaster = new CityMaster();
			cityMaster.setCityId(contactDetails.getCity());
			contactDetailModel.setFsCityMaster(cityMaster);
			}

			contactDetailModel.setArea(contactDetails.getLocalArea());
			contactDetailModel.setBlock(contactDetails.getBlock());
			contactDetailModel.setStreet(contactDetails.getStreet());
			contactDetailModel.setFlat(contactDetails.getFlat());
			contactDetailModel.setTelephone(contactDetails.getTelephone());
			//contactDetailModel.setActiveStatus("1");
			contactDetailModel.setActiveStatus(Constants.CUST_ACTIVE_INDICATOR);
			contactDetailModel.setBuildingNo(contactDetails.getBuildingNo());// added
																				// by
																				// naizsh
			contactDetailModel.setMobile(contactDetails.getMobileContact());
			contactDetailModel.setTelephoneCode(contactDetails.getTelephoneCode());

			if (contactDetails.getPkContactDetails().intValue() != 0) {
				contactDetailModel.setContactDetailId(contactDetails.getPkContactDetails());
				contactDetailModel.setUpdatedBy(sessionStateManage.isExists("userName") ? sessionStateManage.getSessionValue("userName") : "root");
				//contactDetailModel.setLastUpdated(new Date());
				contactDetailModel.setLastUpdated(getCurrentTime());
				contactDetailModel.setCreatedBy(contactDetails.getCreatedBy());
				contactDetailModel.setCreationDate(contactDetails.getCreationDate());
			} else {
				contactDetailModel.setCreatedBy(sessionStateManage.isExists("userName") ? sessionStateManage.getSessionValue("userName") : "root");
				//contactDetailModel.setCreationDate(new Date());
				contactDetailModel.setCreationDate(getCurrentTime());
			}
			getRemOnlineReg().saveContactDetails(contactDetailModel);

		}

		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			// context.invalidateSession();
			if (lstContactDetails.size() == 0) {
				context.redirect("../common/successonline.xhtml");
			} else {
				context.redirect("../common/success.xhtml");
				googleMapGeneratorBean.callFromOnlineRegistration(lstContactDetails);
				if(getState()!=null && getDistrict()!=null && getCity()!=null){
				googleMapGeneratorBean.callFromOnlineRegistrationEmployeeInfo(getCountry(), getState(), getDistrict(), getCity());
				}
			}

		} catch (Exception e) {
			log.error("Problem to redirect: ", e);
		}
	}

	/**********************************
	 * Responsible for Advanced File upload
	 * 
	 * @throws SQLException
	 * @throws SerialException
	 *****************************************************************/

	public void handleFileUpload(FileUploadEvent event) throws IOException, SerialException, SQLException {
		InputStream inputStream = event.getFile().getInputstream();

		image = new DocumentImg();
		image.setImage(storeImage(inputStream));
		//image.setUploadDate(new Date());
		image.setUploadDate(getCurrentTime());
		image.setImageName(event.getFile().getFileName());
		image.setImgStatus("1");
		//image.setUploadDate(new Date());
		image.setUploadDate(getCurrentTime());

		/** Manipulating save/update */
		if (getPkImage() == null) {

		} else {
			if (getPkImage().intValue() != 100) {
				image.setImgId(getPkImage());
			}
		}

		if (image.getImage().length() > 0) {
			getRemOnlineReg().saveImage(image);
			setPkImage(image.getImgId());
			setBooImageUploaded(true);
		}
		log.debug(":::::::::::::::::::::::::::::::::::::::::::::::::::::::;Image ID :::::::::::::::::::::::: " + getPkImage());
	}

	public void changeGender() {
		changeLocalTitle();

		Map<BigDecimal, String> mapGender = ruleEngine
				.getComponentData("Gender");
		Map<BigDecimal, String> mapTitle = ruleEngine.getComponentData("Title");

		System.out.println(mapGender);
		System.out.println(mapTitle);

		if (mapTitle.get(new BigDecimal(title)) != null) {if (mapTitle.get(new BigDecimal(title)).trim()
				.equalsIgnoreCase(Constants.TITLE_FOR_MR_NAME)) {
			for (BigDecimal key : mapGender.keySet()) {
				String value = mapGender.get(key);
				if (mapGender.get(key).equalsIgnoreCase(Constants.GENDER_MALE)) {
					gender = key.toPlainString();
					gender = value;
				}
			}
		} else if (mapTitle.get(new BigDecimal(title)).trim()
				.equalsIgnoreCase(Constants.TITLE_FOR_MRS_NAME)) {
			for (BigDecimal key : mapGender.keySet()) {
				String value = mapGender.get(key);
				if (mapGender.get(key).equalsIgnoreCase(Constants.GENDER_FEMALE)) {
					gender = key.toPlainString();
					gender = value;
				}
			}
		} else if (mapTitle.get(new BigDecimal(title)).trim()
				.equalsIgnoreCase("M/s")) {
			
			gender = "";
		}else {
			gender = "";
		}
	} else {
			gender = "";
		}

	}

	public void changeLocalTitle() {

		Map<BigDecimal, String> mapLocalTitle = ruleEngine
				.getComponentData("Local Title");
		Map<BigDecimal, String> mapTitle = ruleEngine.getComponentData("Title");

		System.out.println(mapLocalTitle);
		System.out.println(mapTitle);

		if (mapTitle.get(new BigDecimal(title)) != null) {
			if (mapTitle.get(new BigDecimal(title)).trim()
					.equalsIgnoreCase("Mr.")) {
				for (Entry<BigDecimal, String> eleEntry : mapLocalTitle
						.entrySet()) {
					if (eleEntry.getKey().toString()
							.equalsIgnoreCase(getiGeneralService().getComponentId(Constants.LOCAL_TITLE_FOR_MR, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString())) {
						String value = eleEntry.getValue();
						// titlel =eleEntry.getKey().toPlainString();
						localTitle = value;

					}
				}
			} else if (mapTitle.get(new BigDecimal(title)).trim()
					.equalsIgnoreCase("Ms.")) {
				for (Entry<BigDecimal, String> eleEntry : mapLocalTitle
						.entrySet()) {
					if (eleEntry.getKey().toString()
							.equalsIgnoreCase(getiGeneralService().getComponentId(Constants.LOCAL_TITLE_FOR_MRS, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString())) {//PK
						String value = eleEntry.getValue();
						// titlel =eleEntry.getKey().toPlainString();
						localTitle = value;

					}
				}
			}
		}
	}

	private String countryName = null;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	private Boolean booMobilecheck = false;

	public Boolean getBooMobilecheck() {
		return booMobilecheck;
	}

	public void setBooMobilecheck(Boolean booMobilecheck) {
		this.booMobilecheck = booMobilecheck;
	}

	public Boolean getBooIdTypeCheck() {
		return booIdTypeCheck;
	}

	public void setBooIdTypeCheck(Boolean booIdTypeCheck) {
		this.booIdTypeCheck = booIdTypeCheck;
	}

	public Boolean getBooCivilId() {
		return booCivilId;
	}

	public void setBooCivilId(Boolean booCivilId) {
		this.booCivilId = booCivilId;
	}

	public Boolean getBooOtherId() {
		return booOtherId;
	}

	public void setBooOtherId(Boolean booOtherId) {
		this.booOtherId = booOtherId;
	}

	public void checkMobile() {

		setBooMobilecheck(false);

		List<Customer> matchMobile = new ArrayList<Customer>();

		matchMobile.addAll(getiGeneralService().getMobileCheck(

		sessionStateManage.getCountryId(), getMobileNo()));

		if (getPkCustomerId() != null) {

			lstCustomer = getRemOnlineReg().getFsCustomer(getPkCustomerId());

			if (getPkCustomerId().intValue() == lstCustomer.get(0).getCustomerId().intValue() && getMobileNo().equalsIgnoreCase(lstCustomer.get(0).getMobile())) {

				setBooMobilecheck(false);

			} else if (matchMobile.size() > 0) {

				setBooMobilecheck(true);
			}

		} else if (matchMobile.size() > 0) {

			setBooMobilecheck(true);

		}

	}

	// added by Nazish for customer Reference

	private List<Document> lstDocument = new ArrayList<Document>();
	List<UserFinancialYear> DealYearList = new ArrayList<UserFinancialYear>();
	private String processIn = Constants.U;
	private BigDecimal customerRefNo;
	private BigDecimal updateCustomerRefNo;
	private String document;
	private String dealYear;
	private BigDecimal documentSerialityNo;

	public String getProcessIn() {
		return processIn;
	}

	public void setProcessIn(String processIn) {
		this.processIn = processIn;
	}

	public BigDecimal getCustomerRefNo() {

		return customerRefNo;
	}

	public void setCustomerRefNo(BigDecimal customerRefNo) {
		this.customerRefNo = customerRefNo;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public void setDealYear(String dealYear) {
		this.dealYear = dealYear;
	}

	public BigDecimal getUpdateCustomerRefNo() {
		return updateCustomerRefNo;
	}

	public void setUpdateCustomerRefNo(BigDecimal updateCustomerRefNo) {
		this.updateCustomerRefNo = updateCustomerRefNo;
	}

	public BigDecimal getDocumentSerialityNo() {
		return documentSerialityNo;
	}

	public void setDocumentSerialityNo(BigDecimal documentSerialityNo) {
		this.documentSerialityNo = documentSerialityNo;
	}

	/*public String getDocument() {

		lstDocument = iGeneralService.getDocument(Constants.DOCUMENT_CODE_FOR_CUSTOMER, sessionStateManage.getLanguageId());
		for (Document lstdoc : lstDocument) {
			setDocumentSerialityNo(lstdoc.getDocumentID());

		}
		return document;
	}

	public String getDocumentSerialID(String processIn) {

		String documentSerialID = iGeneralService.getNextDocumentReferenceNumber(sessionStateManage.getCountryId().intValue(), sessionStateManage.getCompanyId().intValue(), getDocumentSerialityNo()
				.intValue(), new BigDecimal(dealYear).intValue(), processIn);
		setCustomerRefNo(new BigDecimal(documentSerialID));
		return documentSerialID;
	}

	public String getDealYear() {
		DealYearList = iGeneralService.getDealYear(new Date());
		if (DealYearList != null) {

			dealYear = DealYearList.get(0).getFinancialYear().toString();

			setDealYear(dealYear);
		}
		return dealYear;
	}*/

	public void removeContactDetail(ContactDetails bean) {
		setBoorenderDataTable(true);
		// setBoorenderSavePanel(true);
		lstContactDetails.remove(bean);
		if (lstContactDetails.size() == 0) {
			setBoorenderDataTable(false);
			// setBoorenderSavePanel(false);
		}

	}

	private Boolean boorenderDataTable = false;

	public Boolean getBoorenderDataTable() {
		return boorenderDataTable;
	}

	public void setBoorenderDataTable(Boolean boorenderDataTable) {
		this.boorenderDataTable = boorenderDataTable;
	}

	public void editContactDetails(ContactDetails bean) {
		setContactType(null);
		setContactLocalArea("");
		setContactCountry(null);
		setContactStreetNo("");
		setContactState(null);
		setContactTelephoneNo("");
		setContactDistrict(null);
		setContactFlat("");
		setContactCity(null);
		setContactBlockNo("");
		setContactTelephoneNo(bean.getTelephone());
		setContactLocalArea(bean.getLocalArea());
		setContactStreetNo(bean.getStreet());

		setContactBlockNo(bean.getBlock());

		setContactFlat(bean.getFlat());
		setContactType(bean.getContactType());
		setBuildingNo(bean.getBuildingNo());
		if (bean.getContactType().intValue() == getiGeneralService().getComponentId(Constants.PERMANENT,sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId().intValue()) {
			setContactCountry(bean.getCountry());
			setRenderHideCountryId(true);
		} else {
			setRenderHideCountryId(false);
			setContactCountry(bean.getCountry());
		}
		setContactState(bean.getState());
		setContactDistrict(bean.getDistrict());
		setContactCity(bean.getCity());

		lstContactDetails.remove(bean);
		if (bean.getContactType() != new BigDecimal(0)) {
			lstContactDetails.add(bean);
		}
		if (lstContactDetails.size() > 0) {
			setBoorenderDataTable(true);
		} else {
			setBoorenderDataTable(false);
		}

	}

	// added by Naizsh on 16-Feb-2015

	private String buildingNo;

	private Boolean renderHideCountryId = false;

	public String getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}

	public Boolean getRenderHideCountryId() {
		return renderHideCountryId;
	}

	public void setRenderHideCountryId(Boolean renderHideCountryId) {
		this.renderHideCountryId = renderHideCountryId;
	}

	public void selectIdentityType() {
		setContactCountry(null);
		setCountryName(null);
		setRenderHideCountryId(false);
		if(getContactType()!=null){
		if (getContactType().intValue() == getiGeneralService().getComponentId(Constants.PERMANENT,sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId().intValue()) {
			setRenderHideCountryId(false);
			setPlusSign(null);
			setTelephoneCode(null);
			setMobileContact(null);
			setRenMobilContactLocal(false);
			setRenMobileContact(true);
			setContactCountry(getNationality());
			setCountryName(iGeneralService.getCountryName(getContactCountry()));
			generateContactStateList();
			
			} else {
         
			setRenderHideCountryId(true);
			setMobileContact(getMobileNo());
			setRenMobilContactLocal(true);
			setRenMobileContact(false);
			setContactCountry(sessionStateManage.getCountryId());
			setCountryName(iGeneralService.getCountryName(getContactCountry()));
			generateContactStateList();
			//getCountryNameListResident();
		}
		}else{
			setContactCountry(null);
			setLstContactStateList(null);
			setContactState(null);
			setLstContactDistrictList(null);
			setContactDistrict(null);
			setLstContactCityList(null);
			setContactCity(null);
		}
	}

	private Boolean minagevalidation = false;

	public Boolean getMinagevalidation() {
		return minagevalidation;
	}

	public void setMinagevalidation(Boolean minagevalidation) {
		this.minagevalidation = minagevalidation;
	}

	public void ageValidation(SelectEvent e) {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
		Date today18 = cal.getTime();

		if (getDob().before(today18)) {
			setMinagevalidation(false);

		} else {
			setMinagevalidation(true);
			setDob(null);
		}
	}

	  private String countryLphaCode=null;
	  
	  
	  public String getCountryLphaCode() {
		return countryLphaCode;
	}

	public void setCountryLphaCode(String countryLphaCode) {
		this.countryLphaCode = countryLphaCode;
	}
	public void getCountryAlphCode(){
		  List<CountryMaster> coutryAlphaList = iGeneralService.getCountryAlphaList(sessionStateManage.getCountryId());
		  
		  if(coutryAlphaList.size()>0){
			  setCountryLphaCode(coutryAlphaList.get(0).getCountryAlpha2Code());
		  }
		  
	  }
	
	// added for mobile format validation
	
	public void validateMobile(FacesContext context, UIComponent component, Object value) throws ValidatorException {
 		getCountryAlphCode();
 
			String returnString = iGeneralService.validateMobileTelephone(getCountryLphaCode(), value.toString(), Constants.MOBILE_CONTACT);
			if (returnString.equalsIgnoreCase(Constants.Yes)) {

			} else {

				FacesMessage msg = new FacesMessage("Mobile", returnString);
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);

			}
		

 		
 }
	
	public void validateTelephone(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		getCountryAlphCode();

		if(getContactType().intValue() == getiGeneralService().getComponentId(Constants.RESIDENCE,sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId().intValue()){

			String returnString = iGeneralService.validateMobileTelephone(getCountryLphaCode(), value.toString(), Constants.RESIDENCE_CONTACT);
			if (returnString.equalsIgnoreCase(Constants.Yes)) {

			} else {

				FacesMessage msg = new FacesMessage("Residence", returnString);
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);

			}

		}


	}
	
	
	public void validateOfficeTelephone(FacesContext context, UIComponent component, Object value) throws ValidatorException {
 		getCountryAlphCode();
 		
			String returnString = iGeneralService.validateMobileTelephone(getCountryLphaCode(),value.toString(), Constants.RESIDENCE_CONTACT);
			if (returnString.equalsIgnoreCase(Constants.Yes)) {

			} else {

				FacesMessage msg = new FacesMessage("Residence", returnString);
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);

			}
	
	  }
	private BigDecimal countryBranchId = null;
	
	
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public void getCountryBranch(){
		
		List<CountryBranch> countryBranch = remOnlineReg.getCountryBranch(new BigDecimal(Constants.BRANCH_CODE_ONLINE));
		if(countryBranch.size() >0){
			setCountryBranchId(countryBranch.get(0).getCountryBranchId());
			
		}
	}
	
	public void getMigrationData(){
		

		if(getPkCustomerId()==null){
		  try {
			  List<String> migrationValue = getRemOnlineReg().getMigrationData(getIdNumber(), new BigDecimal(getMobileNo()), getEmail());

			  if(migrationValue.size()!=0){
				 
				  if(migrationValue.get(0).equalsIgnoreCase(Constants.Yes)){
					fetchData();
				  }else if(migrationValue.get(0).equalsIgnoreCase("S")){
					 clearPersonalInformation();
					  RequestContext.getCurrentInstance().execute("notmatchdata.show();");
				  }
				  
			  }

		  } catch (AMGException e) {
			 
			  RequestContext.getCurrentInstance().execute("sqlexception.show();");
		  }

		}else{
			 checkMobile();
		}

	  
	}
	
	public void fetchData(){


		log.info("Entering into view method");
		/**
		 * Clearing cache for panel display
		 */
		getCountryBranch();
		getCountryAlphCode();
		if (getCountryLphaCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {
			setRenderKuwaitId(true);
			setRenderOtherId(false);
		} else {
			setRenderKuwaitId(false);
			setRenderOtherId(true);
		}

		setDownloadFile(null);
		setBooHasImage(false);
		setBooImageUploaded(false);
		setBooPersonalInformation(true);
		setBooEmploymentInformation(false);
		setBooContactDetails(false);
		setBooRegistered(false);
		setPkImage(null);

		/**
		 * This condition will return true if the customer is already done first
		 * time online registration
		 */
			setBooViewed(false);
			/** Responsible to clear the bean */
			clear();
			lstContactDetails.clear();
			setBooDisableSave(false);

			/** If already registered then, it will show first panel */
			setBooPersonalInformation(true);
			setBooEmploymentInformation(false);
			setBooContactDetails(false);
			
			if (getCountryLphaCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {
				
			BigDecimal idTypes = getiGeneralService().getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
			lstCustomerIdProof = getRemOnlineReg().getIdproofDetails(idTypes,getIdNumber());
			}else{
				lstCustomerIdProof = getRemOnlineReg().getIdproofDetails(getIdTypeId(),getIdNumber());
			}
			setPkCustomerIdProof(lstCustomerIdProof.get(0).getCustProofId());
			setIdNumber(lstCustomerIdProof.get(0).getIdentityInt());
			setIdExpDate(lstCustomerIdProof.get(0).getIdentityExpiryDate());
			setFsCustomerIdProofCreatedBy(lstCustomerIdProof.get(0).getCreatedBy());
			setFsCustomerIdProofCreatedDate(lstCustomerIdProof.get(0).getCreationDate());
			setPkCustomerId(lstCustomerIdProof.get(0).getFsCustomer().getCustomerId());

			

			/** We are getting customerID from from our login table */

			/** populating data from customer table */
			lstCustomer.addAll(getRemOnlineReg().getFsCustomer(getPkCustomerId()));
			setBooReadonlyIdNumber(lstCustomer.size() > 0 ? true : false);

			if (getCountryLphaCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {
				setShowDob(lstCustomer.get(0).getDateOfBirth() == null ? "" : formatter.format(lstCustomer.get(0).getDateOfBirth()));
				setBooCivilId(true);
				setBooOtherId(false);
			} else {
				setBooCivilId(false);
				setBooOtherId(true);
				setDob(lstCustomer.get(0).getDateOfBirth());
			}
			/** setDateOfBirth(lstCustomer.get(0).getDateOfBirth()); */
			setPkCustomerId(lstCustomer.get(0).getCustomerId());
			setTitle(lstCustomer.get(0).getTitle());
			setFirstName(lstCustomer.get(0).getFirstName());
			setMiddleName(lstCustomer.get(0).getMiddleName());
			setLastName(lstCustomer.get(0).getLastName());
			setShortName(lstCustomer.get(0).getShortName());
			setLocalTitle(lstCustomer.get(0).getTitleLocal());
			setLocalFirstName(lstCustomer.get(0).getFirstNameLocal());
			setLocalMiddleName(lstCustomer.get(0).getMiddleNameLocal());
			setLocalLastName(lstCustomer.get(0).getLastNameLocal());
			setLocalShortName(lstCustomer.get(0).getShortNameLocal());
			setNationality(lstCustomer.get(0).getFsCountryMasterByNationality().getCountryId());
			setGender(lstCustomer.get(0).getGender());
			setMobileNo(lstCustomer.get(0).getMobile());
			setEmail(lstCustomer.get(0).getEmail());
			setAlternateEmail(lstCustomer.get(0).getAlterEmailId());
			setTokenKey(lstCustomer.get(0).getTokenKey());

			/**
			 * Here the icon for growling will be shown, because here image is
			 * available in Database
			 */
			setFsCustomerCreatedBy(lstCustomer.get(0).getCreatedBy());
			setFsCustomerCreatedDate(lstCustomer.get(0).getCreationDate());

			// fetch Customer Reference Added by Nazish
			setUpdateCustomerRefNo(lstCustomer.get(0).getCustomerReference());

			/** populating Customer Employment Information */
			lstCustomerEmployment.addAll(getRemOnlineReg().getFsCustEmpInfo(getPkCustomerId()));
			/** If there is any data exist, setting the value */
			if (lstCustomerEmployment != null && lstCustomerEmployment.size() > 0) {
				setPkCustomerEmployeeId(lstCustomerEmployment.get(0).getCustEmpInfoId());
				setEmploymentType(lstCustomerEmployment.get(0).getFsBizComponentDataByEmploymentTypeId().getComponentDataId());
				setOccupation(lstCustomerEmployment.get(0).getFsBizComponentDataByOccupationId().getComponentDataId());
				setDepartment(lstCustomerEmployment.get(0).getDepartment());
				setEmployer(lstCustomerEmployment.get(0).getEmployerName());
				setBlockNo(lstCustomerEmployment.get(0).getBlock());
				setStreetNo(lstCustomerEmployment.get(0).getStreet());
				setArea(lstCustomerEmployment.get(0).getArea());
				setOfficeTelNo(lstCustomerEmployment.get(0).getOfficeTelephone());
				setCountry(lstCustomerEmployment.get(0).getFsCountryMaster().getCountryId());
				populateState();
				if(lstCustomerEmployment.get(0).getFsStateMaster()!=null){
				
				setState(lstCustomerEmployment.get(0).getFsStateMaster().getStateId());
				}
				populateDistrict();
				if(lstCustomerEmployment.get(0).getFsDistrictMaster()!=null){
				
				setDistrict(lstCustomerEmployment.get(0).getFsDistrictMaster().getDistrictId());
				}
				populateCity();
				if(lstCustomerEmployment.get(0).getFsCityMaster()!=null){
				setCity(lstCustomerEmployment.get(0).getFsCityMaster().getCityId());
				}
				setPostalCode(lstCustomerEmployment.get(0).getPostal());
				setFsCustomerEmpCreatedBy(lstCustomerEmployment.get(0).getCreatedBy());
				setFsCustomerEmpCreatedDate(lstCustomerEmployment.get(0).getCreationDate());

				/**
				 * This boolean is responsible to render the panel depending
				 * upon Employment Type
				 */
				if (getEmploymentType().intValue() == getiGeneralService()
						.getComponentId(Constants.EMPLOYMENTTYPE, sessionStateManage.getLanguageId()).getFsBizComponentData()
						.getComponentDataId().intValue()) {
					setBooEmploymentPanel(false);
				} else {
					setBooEmploymentPanel(true);
				}
				setBooEmployed(true);
			} else {
				setEmploymentType(new BigDecimal(getiGeneralService()
						.getComponentId(Constants.EMPLOYMENTTYPE, sessionStateManage.getLanguageId()).getFsBizComponentData()
						.getComponentDataId().intValue()));
				/**
				 * This boolean is responsible to render the panel depending
				 * upon Employment Type
				 */
				setBooEmploymentPanel(false);
				setBooEmployed(false);
			}

			/** Fetching Contact Details Data */
			lstContactDetailsModel.clear();
			lstContactDetailsModel.addAll(getRemOnlineReg().getFsContactDetails(getPkCustomerId()));
			if (lstContactDetailsModel != null && lstContactDetailsModel.size() > 0) {
				lstContactDetails.clear();
				//ContactDetails contactetails = null;
				for (ContactDetail cdetail : lstContactDetailsModel) {
					
					ContactDetails addContactDetailBean = new ContactDetails();
		              if(cdetail.getFsBizComponentDataByContactTypeId()!=null){
		                getFetchContactTypeList();
						addContactDetailBean.setStrContactType(mapContactType.get(cdetail.getFsBizComponentDataByContactTypeId().getComponentDataId()));
						addContactDetailBean.setContactType(cdetail.getFsBizComponentDataByContactTypeId().getComponentDataId());
		           }
						if(cdetail.getFsCountryMaster()!=null){
						addContactDetailBean.setStrCountry(mapCountry.get(cdetail.getFsCountryMaster().getCountryId()));
						addContactDetailBean.setCountry(cdetail.getFsCountryMaster().getCountryId());
						}
						if(cdetail.getFsStateMaster()!=null){

						addContactDetailBean.setStrState( mapState.get(cdetail.getFsStateMaster().getStateId()));
						addContactDetailBean.setState(cdetail.getFsStateMaster().getStateId());
						}
						if(cdetail.getFsDistrictMaster()!=null){
						
						addContactDetailBean.setStrDistrict(mapDistrict.get(cdetail.getFsDistrictMaster().getDistrictId()));
						addContactDetailBean.setDistrict(cdetail.getFsDistrictMaster().getDistrictId());
						}
						if(cdetail.getFsCityMaster()!=null){
						addContactDetailBean.setStrCity(mapCity.get(cdetail.getFsCityMaster().getCityId()));
						addContactDetailBean.setCity(cdetail.getFsCityMaster().getCityId());
						}
						addContactDetailBean.setLocalArea(cdetail.getArea());
						addContactDetailBean.setFlat(cdetail.getFlat());
						addContactDetailBean.setBuildingNo(cdetail.getBuildingNo());
						addContactDetailBean.setBlock(cdetail.getBlock());
						addContactDetailBean.setCreatedBy(cdetail.getCreatedBy());
		        		addContactDetailBean.setCreationDate(cdetail.getCreationDate());
		        		addContactDetailBean.setPkContactDetails(cdetail.getContactDetailId());
						addContactDetailBean.setTelephone(cdetail.getTelephone());
						addContactDetailBean.setStreet(cdetail.getStreet());
						lstContactDetails.add(addContactDetailBean);

				}
				clearContactDetails();
			}
		

			
	}
	
	@Autowired
	ICompanyMasterservice icompanyMaster;
	
	private String mobileContact=null;
	private String telephoneCode=null;
	private String plusSign=null;
	private Boolean msgMobileOrTel = false;
	
	private BigDecimal dealYearId;
	private String userDealYear;
	private BigDecimal userDealYearId;
	private BigDecimal companyCode;
	private Boolean renMobilContactLocal = false;
	private Boolean renMobileContact = true;
	
	public String getMobileContact() {
		return mobileContact;
	}

	public void setMobileContact(String mobileContact) {
		this.mobileContact = mobileContact;
	}

	public String getTelephoneCode() {
		return telephoneCode;
	}

	public void setTelephoneCode(String telephoneCode) {
		this.telephoneCode = telephoneCode;
	}

	
	public String getPlusSign() {
		return plusSign;
	}

	public void setPlusSign(String plusSign) {
		this.plusSign = plusSign;
	}
	
	
	
	public Boolean getMsgMobileOrTel() {
		return msgMobileOrTel;
	}

	public void setMsgMobileOrTel(Boolean msgMobileOrTel) {
		this.msgMobileOrTel = msgMobileOrTel;
	}

	
	public BigDecimal getDealYearId() {
		return dealYearId;
	}

	public void setDealYearId(BigDecimal dealYearId) {
		this.dealYearId = dealYearId;
	}

	public String getUserDealYear() {
		return userDealYear;
	}

	public void setUserDealYear(String userDealYear) {
		this.userDealYear = userDealYear;
	}

	public BigDecimal getUserDealYearId() {
		return userDealYearId;
	}

	public void setUserDealYearId(BigDecimal userDealYearId) {
		this.userDealYearId = userDealYearId;
	}

	public BigDecimal getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(BigDecimal companyCode) {
		this.companyCode = companyCode;
	}
	
	public Boolean getRenMobilContactLocal() {
		return renMobilContactLocal;
	}

	public void setRenMobilContactLocal(Boolean renMobilContactLocal) {
		this.renMobilContactLocal = renMobilContactLocal;
	}

	public Boolean getRenMobileContact() {
		return renMobileContact;
	}

	public void setRenMobileContact(Boolean renMobileContact) {
		this.renMobileContact = renMobileContact;
	}

	public void telephoneCodeFromDB(){
		setPlusSign("+");
	  setTelephoneCode(iGeneralService.getTelephoneCountryBasedOnNationality(getContactCountry()));
	}
	
	Map<String, BizComponentConfDetail> returnObjectmob = null;
	Map<String, String> mapValidatorMobile = new HashMap<String,String>();
	Map<String, Object> mapMobile = new TreeMap<String, Object>();
	Map<String, Map<String, Object>> jsonOutputMob = new HashMap<String,Map<String, Object>>();
	

	Map<String, Object> getComponentBehaviorMobileContact(){

		try{

			List<String> componentNames = Arrays.asList("Mobile Contact");
			returnObjectmob = iGeneralService.getComponentBehavior(componentNames, sessionStateManage.getLevel(),sessionStateManage.getApplicationId(),sessionStateManage.getCompanyId(),getContactCountry(),sessionStateManage.getPageId());
			for(Entry<String, BizComponentConfDetail> entry:returnObjectmob.entrySet()){

				mapValidatorMobile = new HashMap<String,String>();
				mapValidatorMobile.put("MIN_VALUE", entry.getValue().getMinValue().toPlainString());
				mapValidatorMobile.put("MAX_VALUE", entry.getValue().getMaxValue().toPlainString());

			}
		}catch(Exception e){

			mapMobile.put("error", "1");
		}
		mapMobile.put("data", jsonOutputMob);
		mapMobile.put("total", jsonOutputMob.size());
		return mapMobile;


	}
	
	public void validateContactMobile(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if(getContactType()!=null){
			if(getContactType().intValue() == iGeneralService.getComponentId(Constants.RESIDENCE,sessionStateManage.getLanguageId())
					.getFsBizComponentData().getComponentDataId().intValue()){

				String returnString = iGeneralService.validateMobileTelephone(sessionStateManage.getCountryAlphaTwoCode(), value.toString(), Constants.MOBILE_CONTACT);
				if (returnString.equalsIgnoreCase(Constants.Yes)) {

				} else {

					FacesMessage msg = new FacesMessage("Residence", returnString);
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(msg);

				}

			}else{
				validateContactMobilePermanent(context, component, value);
			}
		}

	}

	public void validateContactMobilePermanent(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if(value.toString().length()!=0){
		if (value.toString().length()  == new BigDecimal(mapValidatorMobile.get("MAX_VALUE")).intValue()) {

		} else {

			FacesMessage msg = new FacesMessage("Permanet", "Mobile Number must be "+mapValidatorMobile.get("MAX_VALUE")+" Character");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

		}
		}

	}
	
public String getDealYearbyDate() {
		
		try{
			DealYearList = iGeneralService.getDealYear(new Date());
			if(DealYearList!=null){
				if(getUserDealYear()==null){
					dealYear = DealYearList.get(0).getFinancialYear().toString();
					dealYearId=DealYearList.get(0).getFinancialYearID();
					setDealYearId(dealYearId);
					setDealYear(dealYear);
				}else{
					setDealYear(getUserDealYear());
					setDealYearId(getUserDealYearId());
				}
			}
		}catch(Exception e){
		e.printStackTrace();	
		}
		return dealYear;
	}
	
	public BigDecimal getCompanyCodeByCompanyId(){
		
		List<CompanyMasterDesc> lstcompanycode = icompanyMaster.viewById(sessionStateManage.getCompanyId());
		
		if(lstcompanycode.size() != 0){
			
			for (CompanyMasterDesc companyMasterDesc : lstcompanycode) {
				setCompanyCode(companyMasterDesc.getFsCompanyMaster().getCompanyCode());
			}
			
		}
		
		return companyCode;
	}
	
	 public Map<BigDecimal, String> getContactTypeList() {
		 Map<BigDecimal, String> mapContactTypeList = ruleEngine.getComponentData("Contact Type");
		if(sessionStateManage.getCountryId().intValue()== getNationality().intValue()){
			mapContactTypeList.remove(getiGeneralService().getComponentId(Constants.PERMANENT,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
		}
		return mapContactTypeList;
	}
	
}  


