package com.amg.exchange.bean;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.amg.exchange.bco.service.IBranchComplianceOfficerService;
import com.amg.exchange.common.bean.RuleEngine;
import com.amg.exchange.common.model.Amlstatus;
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
import com.amg.exchange.finscan.webservice.FNSServicesLookupSoapClient;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.mail.ApllicationMailer1;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerChangeLog;
import com.amg.exchange.registration.model.CustomerContactDetailView;
import com.amg.exchange.registration.model.CustomerDependant;
import com.amg.exchange.registration.model.CustomerEmployeeInfoView;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerIdproofView;
import com.amg.exchange.registration.model.CustomerInfoView;
import com.amg.exchange.registration.model.CustomerSponsor;
import com.amg.exchange.registration.model.DmsMas;
import com.amg.exchange.registration.model.DocumentImg;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.IdentityTypeMaster;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.registration.model.SmartCardInfo;
import com.amg.exchange.registration.service.IBranchPageService;
import com.amg.exchange.registration.service.ICompanyMasterservice;
import com.amg.exchange.registration.service.ICorporateRegService;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.registration.service.IEmployeeService;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.model.RelationsDescription;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.CollectionUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.ReadScanProperties;
import com.amg.exchange.util.SessionStateManage;

@Component(value = "branchRegistration")
@Scope("session")
public class CustomerRegistrationBranchBean<T> implements Serializable { //extends AbstractReportBean


	@Autowired
	IBranchComplianceOfficerService bcoService;

	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;

	@Autowired
	IBranchPageService<T> branchpageService;

	@Autowired
	ICorporateRegService<T> corpRegService;

	@Autowired
	RuleEngine<T> ruleEngine;

	@Autowired
	NomineeRegistration nomineeRegistration;

	@Autowired
	IEmployeeService iEmployeeService;

	@Autowired
	ApllicationMailer1 mailService1;

	@Autowired(required = true)
	JavaMailSender mailSender1;

	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;

	Logger log = Logger.getLogger(CustomerRegistrationBranchBean.class);

	private static final long serialVersionUID = 1L;

	private SessionStateManage session = new SessionStateManage();

	public BigDecimal pkCustomerId = null;
	public String expDateCheck = null;
	private String idType = null;
	private String selectType = "Manual";
	private String manual = null;
	private String smartCard = null;

	// this is used for Remitter Information
	private String idNumber = null;
	private String title = null;
	private String token = null;
	private String firstName = null;
	private String lastName = null;
	private String middleName = null;
	private String shortName = null;
	private String titlel = null;
	private String firstNamel = null;
	private String lastNamel = null;
	private String middleNamel = null;
	private String shortNamel = null;
	private BigDecimal nationality = null;
	private String gender = null;
	private String mobile = null;
	private String email = null;
	private String alternativeEmail = null;


	// Added for adding two new fields begin
	private String introducedBy = null;
	private String medicalInsuranceInd = null;
	// Added for adding two new fields end

	private String AMLStatus = null;
	private BigDecimal numberofhits = null;

	// This is used for Contact Details Panel
	public BigDecimal pkCustomerContactDetails = null;
	private BigDecimal contactTypeId = null;
	private String localArea = null;
	private BigDecimal countryId = null;
	private String street = null;
	private String block = null;
	private String telephone = null;
	private String area = null;
	private BigDecimal cityId = null;
	private String flat = null;
	private String countryOfBirth = null;
	private String birthPlace = null;
	private String fatherName = null;

	public BigDecimal pkCustomerEmployeeId = null;
	private BigDecimal occupation = null;
	private String employer = null;
	private BigDecimal employmentType = null;
	private String empcategory = null;
	private String originId = null;
	private BigDecimal stateId = null;
	private String stateEmployment = null;
	private String postalCode = null;
	private String officeTel = null;
	private BigDecimal districtId = null;
	private String statusMessage = null;
	private BigDecimal introduceBy = null;
	private String insurance = null;
	private String rel = null;
	private String dataTableContactTypeValue = null;
	private String idnumberProof = null;
	private String department = null;

	private BigDecimal empCountryId = null;
	private BigDecimal empStateId = null;
	private BigDecimal empDistrictId = null;
	private String empInfoBlock = null;
	private String empInfoStreet = null;
	private String emparea = null;
	private BigDecimal empCityId = null;
	private BigDecimal empInfoEmploymentTypeId = null;

	public BigDecimal pkCustomerIdProof = null;
	private BigDecimal idTypeproof = null;
	private BigDecimal idFor = null;
	private Date expDate = null;
	private Date dateExp = null;
	private Date dob = null;
	private String minDob;
	private Date creationDateCustomer = null;
	private String createdByCustomer = null;
	private Date creationDateEmployee = null;
	private String createdByEmployee = null;
	private Date creationDateContact = null;
	private String createdByContact = null;
	private Date creationDateProof = null;
	private String createdByProof = null;
	private String verifyToken = null;
	private String emosCustomer=null;


	private Boolean booNomineeFirst = true;
	private Boolean booNomineeAfter = true;
	
	private String errorMsg= null;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	private Map<BigDecimal, String> idTypeMap = new HashMap<BigDecimal, String>();
	// private List<IdentityType> identityTypeList = new
	// ArrayList<IdentityType>();
	// private List<ContactType> contactTypeList = new ArrayList<ContactType>();
	private List<AddContactDetailBean> contactList = new ArrayList<AddContactDetailBean>();
	private List<AddContactDetailBean> contactListDelete = new ArrayList<AddContactDetailBean>();
	// List<CreateProofTable> createProofList = new
	// ArrayList<CreateProofTable>();
	CopyOnWriteArrayList<CreateProofTable> createProofList = new CopyOnWriteArrayList<CreateProofTable>();
	private List<CreateProofTable> createProofListDelete = new ArrayList<CreateProofTable>();
	private List<CustomerIdProof> customerIdProofList = new ArrayList<CustomerIdProof>();
	private List<CustomerEmploymentInfo> customerEmploymentInfoList = new ArrayList<CustomerEmploymentInfo>();
	private List<Customer> customerList = new ArrayList<Customer>();

	// added by kani begin
	private List<CustomerIdProof> customerReferencaList = new ArrayList<CustomerIdProof>();
	private List<Customer> customerIntroducedList = new ArrayList<Customer>();

	// Added by kani end

	private List<ContactDetail> contactDetailList = new ArrayList<ContactDetail>();
	private List<CountryMasterDesc> countryList;
	private List<StateMasterDesc> lstState;
	private List<CityMasterDesc> lstCity;
	private List<DistrictMasterDesc> lstDistrict;
	// private SessionStateManage sessionStateManage = null;

	/* Responsible to populate State,District, City in emp Details */
	private List<StateMasterDesc> lstEmpStateList = new ArrayList<StateMasterDesc>();
	private List<DistrictMasterDesc> lstEmpDistrictList = new ArrayList<DistrictMasterDesc>();
	private List<CityMasterDesc> lstEmpCityList = new ArrayList<CityMasterDesc>();

	private Map<BigDecimal, String> mapContactTypeList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapCountryList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapStateList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapDistirictList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapCityList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> idForMap = new HashMap<BigDecimal, String>();
	// Map<String, BussComponentConfDetail> mapComponentBehavior = new
	// HashMap<String, BussComponentConfDetail>();

	private Boolean booManual = false;
	private Boolean booSmartcard = false;
	private Boolean booIdDetail = true;
	private Boolean booCustomerDetails = false; // this is rendered for if
	// select smart card
	private Boolean booRemitterInfo = false; // this is rendered for if select
	// manual
	private Boolean booContactDetails = false; // this is rendered for Contact
	// Detail panel
	private Boolean booEmploymentDetails = false; // this is rendered for
	// Employment Detail panel
	private Boolean booProof = false; // this is rendered for Proof Detail panel
	private boolean contactDataTable = false; // this is rendered for Contact
	// Detail Datatable
	private boolean idListcheck = false; // this is for atleast add one proof
	// details message
	private boolean booidproofDatatable = false; // // this is rendered for
	// proof Detail Datatable
	/** will check Employment country, state, city is there */
	private Boolean booEmployed = false;

	// this is for Nominee
	private boolean boonomeneeCheck = false;
	private BigDecimal nomineeId = null;
	private boolean nomineeApprove;

	private Boolean booProfListDuplicate = false;
	private Boolean booIdTypeCheck = false;

	private Boolean booUnEmp = false;
	private Boolean enablePTbl;
	private boolean approved;
	private Boolean boolAdditional = false;
	private Boolean contactlistcheck = false;// this is for atleast add one
	// contact details message
	private Boolean prooflistcheck = false;
	private boolean boosmartCardAppear = false; // this is used for hide smart
	// card display

	private Boolean renderIdProofVissibility = false;
	private Part part;
	private UIComponent tableForm;
	private String DATE_FORMAT = "dd/MM/yyyy";

	private UploadedFile file;
	private StreamedContent downloadFile;

	private BigDecimal image_id;

	private Boolean booCivilId = false;
	private Boolean booOtherId = false;

	private Boolean booBrowsedFile = false;
	private Boolean booEmploymentInformation = false;
	private Boolean booEmploymentPanel = true;
	private boolean uploadFileVisibility = false;
	private Boolean imageUploadCheck = false;
	private Map<String, StreamedContent> imageMap = new HashMap<String, StreamedContent>();

	private String idTypeBranchPage = "";
	// private String idTypeproof = null;

	private String hidden1 = "0";
	private int saveUptoPanel = 1;

	private BigDecimal pkimage;

	private Boolean booContactDetailsSize = false;

	/*	private static final String CUSTOMERTYPE = "Individual";
	private static final String CIVILID = "CIVIL ID";
	private static final String GROUPID = "Almulla Group";
	private static final String EMPLOYMENTTYPE = "Un Employed";
	private static final String METHODTYPE = "Manual";
	private static final String NATIONAL_ID = "NATIONAL ID";*/
	private static final DateFormat dateformat = null;
	// private static final String IDENTITYFOR = "ID Proof";

	/* To track which id type has been selected */
	private String selectedIdType = null;

	private String showDob = null;

	private Boolean booContactDetailsDuplicate = false; // This is responsible
	// to Check dupliacte
	// Contact Detail
	private Boolean signatureCaptureRender = false;
	private Boolean signatureSpecimenRender = false;
	private Boolean renderFinal = false;
	private Boolean renderVerifyReport = false;
	private Boolean backFinalRender = false;
	private Boolean submitRender = false;
	private Boolean exitRender = false;
	private Boolean nextSignaturePanel = false;

	// Timezone for application country based creation log
	private Date currentTime = new Date();



	public Date getCurrentTime() {

		Date objList = getGeneralService().getSysDateTimestamp(sessionStateManage.getCountryId());


		if(objList != null){
			currentTime = objList;}
		else{

			//currentTime.getTime();
			currentTime =null;
		}

		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public String getManual() {
		return manual;
	}

	public void setManual(String manual) {
		this.manual = manual;
	}

	public String getSmartCard() {
		return smartCard;
	}

	public void setSmartCard(String smartCard) {
		this.smartCard = smartCard;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getTitlel() {
		return titlel;
	}

	public void setTitlel(String titlel) {
		this.titlel = titlel;
	}

	public String getFirstNamel() {
		return firstNamel;
	}

	public void setFirstNamel(String firstNamel) {
		this.firstNamel = firstNamel;
	}

	public String getLastNamel() {
		return lastNamel;
	}

	public void setLastNamel(String lastNamel) {
		this.lastNamel = lastNamel;
	}

	public String getMiddleNamel() {
		return middleNamel;
	}

	public void setMiddleNamel(String middleNamel) {
		this.middleNamel = middleNamel;
	}

	public String getShortNamel() {
		return shortNamel;
	}

	public void setShortNamel(String shortNamel) {
		this.shortNamel = shortNamel;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlternativeEmail() {
		return alternativeEmail;
	}

	public void setAlternativeEmail(String alternativeEmail) {
		this.alternativeEmail = alternativeEmail;
	}

	// Addeded by Kani begin

	public String getIntroducedBy() {

		return introducedBy;
	}

	public void setIntroducedBy(String introducedBy) {
		getintrudecedby();
		this.introducedBy = introducedBy;
	}

	public String getMedicalInsuranceInd() {
		return medicalInsuranceInd;
	}

	public void setMedicalInsuranceInd(String medicalInsuranceInd) {
		this.medicalInsuranceInd = medicalInsuranceInd;
	}

	// Added by kani end

	public String getAMLStatus() {
		return AMLStatus;
	}

	public void setAMLStatus(String aMLStatus) {
		AMLStatus = aMLStatus;
	}

	public BigDecimal getNumberofhits() {
		return numberofhits;
	}

	public void setNumberofhits(BigDecimal numberofhits) {
		this.numberofhits = numberofhits;
	}

	public BigDecimal getContactTypeId() {
		return contactTypeId;
	}

	public void setContactTypeId(BigDecimal contactTypeId) {
		this.contactTypeId = contactTypeId;
	}

	public String getLocalArea() {
		return localArea;
	}

	public void setLocalArea(String localArea) {
		this.localArea = localArea;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFlat() {
		return flat;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}

	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public BigDecimal getOccupation() {
		return occupation;
	}

	public void setOccupation(BigDecimal occupation) {
		this.occupation = occupation;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public BigDecimal getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(BigDecimal employmentType) {

		this.employmentType = employmentType;
	}

	public String getEmpcategory() {
		return empcategory;
	}

	public void setEmpcategory(String empcategory) {
		this.empcategory = empcategory;
	}

	public String getOriginId() {
		return originId;
	}

	public void setOriginId(String originId) {
		this.originId = originId;
	}

	public String getStateEmployment() {
		return stateEmployment;
	}

	public void setStateEmployment(String stateEmployment) {
		this.stateEmployment = stateEmployment;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public BigDecimal getIdFor() {
		return idFor;
	}

	public void setIdFor(BigDecimal idFor) {
		this.idFor = idFor;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public BigDecimal getIntroduceBy() {
		return introduceBy;
	}

	public void setIntroduceBy(BigDecimal introduceBy) {
		this.introduceBy = introduceBy;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getDataTableContactTypeValue() {
		return dataTableContactTypeValue;
	}

	public void setDataTableContactTypeValue(String dataTableContactTypeValue) {
		this.dataTableContactTypeValue = dataTableContactTypeValue;
	}

	public String getIdnumberProof() {
		return idnumberProof;
	}

	public void setIdnumberProof(String idnumberProof) {
		this.idnumberProof = idnumberProof;
	}

	public String getEmparea() {
		return emparea;
	}

	public void setEmparea(String emparea) {
		this.emparea = emparea;
	}

	public BigDecimal getIdTypeproof() {
		return idTypeproof;
	}

	public String getExpDateCheck() {
		return expDateCheck;
	}

	public void setExpDateCheck(String expDateCheck) {
		this.expDateCheck = expDateCheck;
	}

	public BigDecimal getNationality() {
		return nationality;
	}

	public void setNationality(BigDecimal nationality) {
		this.nationality = nationality;
	}

	public BigDecimal getEmpCountryId() {
		return empCountryId;
	}

	public void setEmpCountryId(BigDecimal empCountryId) {
		this.empCountryId = empCountryId;
	}

	public BigDecimal getEmpStateId() {
		return empStateId;
	}

	public void setEmpStateId(BigDecimal empStateId) {
		this.empStateId = empStateId;
	}

	public BigDecimal getEmpDistrictId() {
		return empDistrictId;
	}

	public void setEmpDistrictId(BigDecimal empDistrictId) {
		this.empDistrictId = empDistrictId;
	}

	public BigDecimal getEmpCityId() {
		return empCityId;
	}

	public void setEmpCityId(BigDecimal empCityId) {
		this.empCityId = empCityId;
	}

	public void setIdTypeproof(BigDecimal idTypeproof) {
		this.idTypeproof = idTypeproof;
	}

	public String getEmpInfoBlock() {
		return empInfoBlock;
	}

	public void setEmpInfoBlock(String empInfoBlock) {
		this.empInfoBlock = empInfoBlock;
	}

	public String getEmpInfoStreet() {
		return empInfoStreet;
	}

	public void setEmpInfoStreet(String empInfoStreet) {
		this.empInfoStreet = empInfoStreet;
	}

	public BigDecimal getEmpInfoEmploymentTypeId() {
		return empInfoEmploymentTypeId;
	}

	public void setEmpInfoEmploymentTypeId(BigDecimal empInfoEmploymentTypeId) {
		this.empInfoEmploymentTypeId = empInfoEmploymentTypeId;
	}

	public Map<BigDecimal, String> getIdTypeMap() {
		return idTypeMap;
	}

	public void setIdTypeMap(Map<BigDecimal, String> idTypeMap) {
		this.idTypeMap = idTypeMap;
	}

	public Boolean getContactlistcheck() {
		return contactlistcheck;
	}

	public void setContactlistcheck(Boolean contactlistcheck) {
		this.contactlistcheck = contactlistcheck;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getCityId() {
		return cityId;
	}

	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}

	public BigDecimal getStateId() {
		return stateId;
	}

	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}

	public BigDecimal getDistrictId() {
		return districtId;
	}

	public void setDistrictId(BigDecimal districtId) {
		this.districtId = districtId;
	}

	public List<CountryMasterDesc> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}

	public List<StateMasterDesc> getLstState() {
		return lstState;
	}

	public void setLstState(List<StateMasterDesc> lstState) {
		this.lstState = lstState;
	}

	public List<CityMasterDesc> getLstCity() {
		return lstCity;
	}

	public void setLstCity(List<CityMasterDesc> lstCity) {
		this.lstCity = lstCity;
	}

	public List<DistrictMasterDesc> getLstDistrict() {
		return lstDistrict;
	}

	public void setLstDistrict(List<DistrictMasterDesc> lstDistrict) {
		this.lstDistrict = lstDistrict;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getPkCustomerId() {
		return pkCustomerId;
	}

	public void setPkCustomerId(BigDecimal pkCustomerId) {
		this.pkCustomerId = pkCustomerId;
	}

	public BigDecimal getPkCustomerContactDetails() {
		return pkCustomerContactDetails;
	}

	public void setPkCustomerContactDetails(BigDecimal pkCustomerContactDetails) {
		this.pkCustomerContactDetails = pkCustomerContactDetails;
	}

	public BigDecimal getPkCustomerEmployeeId() {
		return pkCustomerEmployeeId;
	}

	public void setPkCustomerEmployeeId(BigDecimal pkCustomerEmployeeId) {
		this.pkCustomerEmployeeId = pkCustomerEmployeeId;
	}

	public BigDecimal getPkCustomerIdProof() {
		return pkCustomerIdProof;
	}

	public void setPkCustomerIdProof(BigDecimal pkCustomerIdProof) {
		this.pkCustomerIdProof = pkCustomerIdProof;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public Boolean getBooManual() {
		return booManual;
	}

	public void setBooManual(Boolean booManual) {
		this.booManual = booManual;
	}

	public Boolean getBooSmartcard() {
		return booSmartcard;
	}

	public void setBooSmartcard(Boolean booSmartcard) {
		this.booSmartcard = booSmartcard;
	}

	public Boolean getBooIdDetail() {
		return booIdDetail;
	}

	public void setBooIdDetail(Boolean booIdDetail) {
		this.booIdDetail = booIdDetail;
	}

	public Boolean getBooCustomerDetails() {
		return booCustomerDetails;
	}

	public void setBooCustomerDetails(Boolean booCustomerDetails) {
		this.booCustomerDetails = booCustomerDetails;
	}

	public Boolean getBooRemitterInfo() {
		return booRemitterInfo;
	}

	public void setBooRemitterInfo(Boolean booRemitterInfo) {
		this.booRemitterInfo = booRemitterInfo;
	}

	public Boolean getBooContactDetails() {
		return booContactDetails;
	}

	public void setBooContactDetails(Boolean booContactDetails) {
		this.booContactDetails = booContactDetails;
	}

	public Boolean getBooEmploymentDetails() {
		return booEmploymentDetails;
	}

	public void setBooEmploymentDetails(Boolean booEmploymentDetails) {
		this.booEmploymentDetails = booEmploymentDetails;
	}

	public Boolean getBooProof() {
		return booProof;
	}

	public void setBooProof(Boolean booProof) {
		this.booProof = booProof;
	}

	public String getDATE_FORMAT() {
		return DATE_FORMAT;
	}

	public void setDATE_FORMAT(String dATE_FORMAT) {
		DATE_FORMAT = dATE_FORMAT;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public StreamedContent getDownloadFile() {
		return downloadFile;
	}

	public void setDownloadFile(StreamedContent downloadFile) {
		this.downloadFile = downloadFile;
	}

	public BigDecimal getImage_id() {
		return image_id;
	}

	public void setImage_id(BigDecimal image_id) {
		this.image_id = image_id;
	}

	public Boolean getBooBrowsedFile() {
		return booBrowsedFile;
	}

	public void setBooBrowsedFile(Boolean booBrowsedFile) {
		this.booBrowsedFile = booBrowsedFile;
	}

	public Map<String, StreamedContent> getImageMap() {
		return imageMap;
	}

	public void setImageMap(Map<String, StreamedContent> imageMap) {
		this.imageMap = imageMap;
	}

	public String getIdTypeBranchPage() {
		return idTypeBranchPage;
	}

	public void setIdTypeBranchPage(String idTypeBranchPage) {
		this.idTypeBranchPage = idTypeBranchPage;
	}

	public String getHidden1() {
		return hidden1;
	}

	public void setHidden1(String hidden1) {
		this.hidden1 = hidden1;
	}

	public Boolean getBooContactDetailsSize() {
		return booContactDetailsSize;
	}

	public void setBooContactDetailsSize(Boolean booContactDetailsSize) {
		this.booContactDetailsSize = booContactDetailsSize;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public Date getDateExp() {
		return dateExp;
	}

	public void setDateExp(Date dateExp) {
		this.dateExp = dateExp;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public boolean isBoosmartCardAppear() {
		return boosmartCardAppear;
	}

	public void setBoosmartCardAppear(boolean boosmartCardAppear) {
		this.boosmartCardAppear = boosmartCardAppear;
	}

	public List<AddContactDetailBean> getContactList() {
		setContactDataTable(contactList.size() > 0 ? true : false);
		return contactList;
	}

	/*
	 * public void setContactList(List<AddContactDetailBean> contactList) {
	 * this.contactList = contactList; }
	 */

	public boolean isUploadFileVisibility() {
		return uploadFileVisibility;
	}

	public void setUploadFileVisibility(boolean uploadFileVisibility) {
		this.uploadFileVisibility = uploadFileVisibility;
	}

	public boolean isContactDataTable() {
		return contactDataTable;
	}

	public void setContactDataTable(boolean contactDataTable) {
		this.contactDataTable = contactDataTable;
	}

	public List<AddContactDetailBean> getContactListDelete() {
		return contactListDelete;
	}

	public void setContactListDelete(
			List<AddContactDetailBean> contactListDelete) {
		this.contactListDelete = contactListDelete;
	}

	public CopyOnWriteArrayList<CreateProofTable> getCreateProofList() {
		return createProofList;
	}

	public void setCreateProofList(
			CopyOnWriteArrayList<CreateProofTable> createProofList) {
		this.createProofList = createProofList;
	}

	public List<CreateProofTable> getCreateProofListDelete() {
		return createProofListDelete;
	}

	public void setCreateProofListDelete(
			List<CreateProofTable> createProofListDelete) {
		this.createProofListDelete = createProofListDelete;
	}

	public Boolean getBooProfListDuplicate() {
		return booProfListDuplicate;
	}

	public void setBooProfListDuplicate(Boolean booProfListDuplicate) {
		this.booProfListDuplicate = booProfListDuplicate;
	}

	public Boolean getBooIdTypeCheck() {
		return booIdTypeCheck;
	}

	public void setBooIdTypeCheck(Boolean booIdTypeCheck) {
		this.booIdTypeCheck = booIdTypeCheck;
	}

	public Boolean getBooUnEmp() {
		return booUnEmp;
	}

	public void setBooUnEmp(Boolean booUnEmp) {
		this.booUnEmp = booUnEmp;
	}

	public Boolean getEnablePTbl() {
		return enablePTbl;
	}

	public void setEnablePTbl(Boolean enablePTbl) {
		this.enablePTbl = enablePTbl;
	}

	public boolean getApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public Boolean getBoolAdditional() {
		return boolAdditional;
	}

	public void setBoolAdditional(Boolean boolAdditional) {
		this.boolAdditional = boolAdditional;
	}

	public Boolean getProoflistcheck() {
		return prooflistcheck;
	}

	public void setProoflistcheck(Boolean prooflistcheck) {
		this.prooflistcheck = prooflistcheck;
	}

	// private String
	// userName=sessionStateManage.isExists("userName")?sessionStateManage.getSessionValue("userName"):"root";

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getMinDob() {
		return minDob;
	}

	public void setMinDob(String minDob) {
		this.minDob = minDob;
	}

	public Boolean getBooEmploymentInformation() {
		return booEmploymentInformation;
	}

	public void setBooEmploymentInformation(Boolean booEmploymentInformation) {
		this.booEmploymentInformation = booEmploymentInformation;
	}

	public Boolean getBooEmploymentPanel() {
		return booEmploymentPanel;
	}

	public void setBooEmploymentPanel(Boolean booEmploymentPanel) {
		this.booEmploymentPanel = booEmploymentPanel;
	}

	public int getSaveUptoPanel() {
		return saveUptoPanel;
	}

	public void setSaveUptoPanel(int saveUptoPanel) {
		this.saveUptoPanel = saveUptoPanel;
	}

	public List<StateMasterDesc> getLstEmpStateList() {
		popEmpState();
		return lstEmpStateList;
	}

	public void setLstEmpStateList(List<StateMasterDesc> lstEmpStateList) {
		this.lstEmpStateList = lstEmpStateList;
	}

	public List<DistrictMasterDesc> getLstEmpDistrictList() {
		return lstEmpDistrictList;
	}

	public void setLstEmpDistrictList(
			List<DistrictMasterDesc> lstEmpDistrictList) {
		this.lstEmpDistrictList = lstEmpDistrictList;
	}

	public List<CityMasterDesc> getLstEmpCityList() {
		return lstEmpCityList;
	}

	public void setLstEmpCityList(List<CityMasterDesc> lstEmpCityList) {
		this.lstEmpCityList = lstEmpCityList;
	}

	public String getSelectedIdType() {
		return selectedIdType;
	}

	public void setSelectedIdType(String selectedIdType) {
		this.selectedIdType = selectedIdType;
	}

	public Map<BigDecimal, String> getMapContactTypeList() {
		return mapContactTypeList;
	}

	public void setMapContactTypeList(Map<BigDecimal, String> mapContactTypeList) {
		this.mapContactTypeList = mapContactTypeList;
	}

	public boolean isBoonomeneeCheck() {
		return boonomeneeCheck;
	}

	public void setBoonomeneeCheck(boolean boonomeneeCheck) {
		this.boonomeneeCheck = boonomeneeCheck;
	}

	public BigDecimal getNomineeId() {
		return nomineeId;
	}

	public void setNomineeId(BigDecimal nomineeId) {
		this.nomineeId = nomineeId;
	}

	public boolean getIdListcheck() {
		return idListcheck;
	}

	public void setIdListcheck(boolean idListcheck) {
		this.idListcheck = idListcheck;
	}

	public boolean getBooidproofDatatable() {
		return booidproofDatatable;
	}

	public void setBooidproofDatatable(boolean booidproofDatatable) {
		this.booidproofDatatable = booidproofDatatable;
	}

	public Boolean getBooEmployed() {
		return booEmployed;
	}

	public void setBooEmployed(Boolean booEmployed) {
		this.booEmployed = booEmployed;
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

	/** Responsible to hold the identity Type from Rule Engine */
	Map<BigDecimal, String> mapIdentityType = new HashMap<BigDecimal, String>();

	public BigDecimal getPkimage() {
		return pkimage;
	}

	public void setPkimage(BigDecimal pkimage) {
		this.pkimage = pkimage;
	}

	public Boolean getImageUploadCheck() {
		return imageUploadCheck;
	}

	public void setImageUploadCheck(Boolean imageUploadCheck) {
		this.imageUploadCheck = imageUploadCheck;
	}



	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public ICustomerRegistrationBranchService<T> getIcustomerRegistrationService() {
		return icustomerRegistrationService;
	}

	public void setIcustomerRegistrationService(
			ICustomerRegistrationBranchService<T> icustomerRegistrationService) {
		this.icustomerRegistrationService = icustomerRegistrationService;
	}

	public IBranchPageService<T> getBranchpageService() {
		return branchpageService;
	}

	public void setBranchpageService(IBranchPageService<T> branchpageService) {
		this.branchpageService = branchpageService;
	}

	/*
	 * public String viewBehaviorBean(String componentName, String type) {
	 * 
	 * return new CollectionUtil().fetchBehavior(mapComponentBehavior,
	 * componentName, type); }
	 */

	/**
	 * @return the booContactDetailsDuplicate
	 */
	public Boolean getBooContactDetailsDuplicate() {
		return booContactDetailsDuplicate;
	}

	/**
	 * @param booContactDetailsDuplicate
	 *            the booContactDetailsDuplicate to set
	 */
	public void setBooContactDetailsDuplicate(Boolean booContactDetailsDuplicate) {
		this.booContactDetailsDuplicate = booContactDetailsDuplicate;
	}

	public RuleEngine<T> getRuleEngine() {
		return ruleEngine;
	}

	public void setRuleEngine(RuleEngine<T> ruleEngine) {
		this.ruleEngine = ruleEngine;
	}

	/*private String userName = FacesContext.getCurrentInstance()
			.getExternalContext().getSessionMap().get("userName").toString();*/

	public ICorporateRegService<T> getCorpRegService() {
		return corpRegService;
	}

	public void setCorpRegService(ICorporateRegService<T> corpRegService) {
		this.corpRegService = corpRegService;
	}

	public Date getCreationDateCustomer() {
		return creationDateCustomer;
	}

	public void setCreationDateCustomer(Date creationDateCustomer) {
		this.creationDateCustomer = creationDateCustomer;
	}

	public String getCreatedByCustomer() {
		return createdByCustomer;
	}

	public void setCreatedByCustomer(String createdByCustomer) {
		this.createdByCustomer = createdByCustomer;
	}

	public Date getCreationDateEmployee() {
		return creationDateEmployee;
	}

	public void setCreationDateEmployee(Date creationDateEmployee) {
		this.creationDateEmployee = creationDateEmployee;
	}

	public String getCreatedByEmployee() {
		return createdByEmployee;
	}

	public void setCreatedByEmployee(String createdByEmployee) {
		this.createdByEmployee = createdByEmployee;
	}

	public Date getCreationDateContact() {
		return creationDateContact;
	}

	public void setCreationDateContact(Date creationDateContact) {
		this.creationDateContact = creationDateContact;
	}

	public String getCreatedByContact() {
		return createdByContact;
	}

	public void setCreatedByContact(String createdByContact) {
		this.createdByContact = createdByContact;
	}

	public Date getCreationDateProof() {
		return creationDateProof;
	}

	public void setCreationDateProof(Date creationDateProof) {
		this.creationDateProof = creationDateProof;
	}

	public String getCreatedByProof() {
		return createdByProof;
	}

	public void setCreatedByProof(String createdByProof) {
		this.createdByProof = createdByProof;
	}

	public Boolean getRenderIdProofVissibility() {
		return renderIdProofVissibility;
	}

	public void setRenderIdProofVissibility(Boolean renderIdProofVissibility) {
		this.renderIdProofVissibility = renderIdProofVissibility;
	}

	private CountryMaster countryMaster = null;
	private CompanyMaster companyMaster = null;
	private Customer customer = null;
	private StateMaster stateMaster = null;
	private DistrictMaster districtMaster = null;
	private CityMaster cityMaster = null;
	// private ContactType contactType = null;
	private LanguageType languageType = null;

	public Blob storeImage(InputStream inputStream) throws IOException,
	SerialException, SQLException {

		/*
		 * InputStream stream = null;
		 * 
		 * try { stream = file.getInputstream(); } catch (Exception e) {
		 * log.info("problem for store image"+e); }
		 */
		return new javax.sql.rowset.serial.SerialBlob(readFully(inputStream));
	}

	public void veryfiAllClick() {
		if (getApproved()) {
			for (CreateProofTable element : createProofList) {
				element.setChecked(true);
			}
		} else {
			for (CreateProofTable element : createProofList) {
				element.setChecked(false);
			}
		}
	}

	public byte[] readFully(InputStream input) throws IOException {
		byte[] buffer = new byte[8192];
		int bytesRead;
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
		return output.toByteArray();
	}

	private BigDecimal individualIdType = new BigDecimal(1);

	SessionStateManage sessionStateManage = new SessionStateManage();

	public void getFetchContactTypeList() {
		mapContactTypeList = ruleEngine.getComponentData("Contact Type");
		idForMap = ruleEngine.getComponentData("ID For");
		idTypeMap = ruleEngine.getComponentData("Identity Type");
	}

	/**
	 * method is responsible to populate List of Nationality from DB
	 * 
	 * @return
	 */

	Map<BigDecimal, String> mapNationalityList = new HashMap<BigDecimal, String>();
	public List<CountryMasterDesc> getNationalityNameList() {
		// SessionStateManage sessionStateManage = new SessionStateManage();

		List<CountryMasterDesc>	nationalityList = new ArrayList<CountryMasterDesc>();
		nationalityList.addAll(getGeneralService().getNationalityList(sessionStateManage.getLanguageId()));
		for (CountryMasterDesc countryMasterDesc : nationalityList) {
			mapNationalityList.put(countryMasterDesc.getCountryMasterId(),
					countryMasterDesc.getCountryName());
		}
		return nationalityList;

	}

	/**
	 * method is responsible to populate List of countries from DB
	 * 
	 * @return
	 */
	public List<CountryMasterDesc> getCountryNameList() {
		// popState(null);
		// sessionStateManage = new SessionStateManage();
		countryList = new ArrayList<CountryMasterDesc>();
		countryList.addAll(getGeneralService().getCountryList(sessionStateManage.getLanguageId()));

		for (CountryMasterDesc countryMasterDesc : countryList) {
			mapCountryList.put(countryMasterDesc.getFsCountryMaster().getCountryId(),
					countryMasterDesc.getCountryName());
		}
		return countryList;
	}

	// this method call to application country by nazish
	/*public List<CountryMasterDesc> getCountryNameListResident() {
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<CountryMasterDesc> lstCountry = getGeneralService().getCountryList(sessionStateManage.getLanguageId());

		*//**we will utilize the map in contact details at adding time, we will show country name and  will id in database *//*
		for (CountryMasterDesc countryMasterDesc : lstCountry) {
			mapCountryList.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
		}
		return lstCountry;
	}*/

	/**
	 * 
	 * method to get state from db and add all the state code and name will be
	 * mapped to hashMap
	 */
	public void popState() {
		//ruleEngine.init();
		telephoneCodeFromDB();
		getComponentBehavior();
		getComponentBehaviorMobileContact();
		lstState = new ArrayList<StateMasterDesc>();
		lstState.addAll(getGeneralService().getStateList(
				sessionStateManage.getLanguageId(),getCountryId()));
		//new BigDecimal(sessionStateManage.isExists("countryId")?sessionStateManage.getSessionValue("countryId"):getCountryId().toString())));
		for (StateMasterDesc stateMasterDesc : lstState) {
			mapStateList.put(stateMasterDesc.getFsStateMaster().getStateId(),
					stateMasterDesc.getStateName());
		}
		setStateId(null);
		setDistrictId(null);
		setCityId(null);
		setLstDistrict(null);
		setLstCity(null);
	}

	public List<StateMasterDesc> getStateListFromDB() {

		return lstState;
	}

	/*
	 * 
	 * method to get District from db and add all the state code and name will
	 * be mapped to hashMap
	 */
	public void popDistrict() {

		lstDistrict = new ArrayList<DistrictMasterDesc>();
		lstDistrict.addAll(getGeneralService().getDistrictList(
				sessionStateManage.getLanguageId(),
				getCountryId(), getStateId()));
		for (DistrictMasterDesc districtMasterDesc : lstDistrict) {
			mapDistirictList.put(districtMasterDesc.getFsDistrictMaster()
					.getDistrictId(), districtMasterDesc.getDistrict());
		}

		setDistrictId(null);
		setCityId(null);
		setLstCity(null);
	}

	public List<DistrictMasterDesc> getDistrictListFromDB() {
		return lstDistrict;

	}

	/**
	 * 
	 * method to get city from db and add all the state code and name will be
	 * mapped to hashMap
	 */
	public void popCity() {

		lstCity = new ArrayList<CityMasterDesc>();
		lstCity.addAll(getGeneralService().getCityList(
				sessionStateManage.getLanguageId(),
				getCountryId(), getStateId(),
				getDistrictId()));
		for (CityMasterDesc cityMasterDesc : lstCity) {
			mapCityList.put(cityMasterDesc.getFsCityMaster().getCityId(),
					cityMasterDesc.getCityName());
		}
	}

	/**
	 * 
	 * method to get state from db and add all the state code and name will be
	 * mapped to hashMap
	 */
	public void popEmpState() {

		// SessionStateManage sessionStateManage = new SessionStateManage();
		List<StateMasterDesc> stateMaster = getGeneralService().getStateList(
				sessionStateManage.getLanguageId(),
				sessionStateManage.getCountryId());
		setLstEmpStateList(stateMaster);


	}

	/*
	 * 
	 * method to get District from db and add all the state code and name will
	 * be mapped to hashMap
	 */
	public void popEmpDistrict(AjaxBehaviorEvent e) {
		// SessionStateManage sessionStateManage = new SessionStateManage();
		List<DistrictMasterDesc> lstDistrict = getGeneralService()
				.getDistrictList(sessionStateManage.getLanguageId(),
						sessionStateManage.getCountryId(), getEmpStateId());
		setLstEmpDistrictList(lstDistrict);

		setEmpDistrictId(null);
		setEmpCityId(null);
		setLstEmpCityList(null);
	}

	/**
	 * 
	 * method to get city from db and add all the state code and name will be
	 * mapped to hashMap
	 */
	public void popEmpCity(AjaxBehaviorEvent e) {

		// SessionStateManage sessionStateManage = new SessionStateManage();
		List<CityMasterDesc> lstCity = getGeneralService().getCityList(
				sessionStateManage.getLanguageId(),
				sessionStateManage.getCountryId(), getEmpStateId(),
				getEmpDistrictId());
		setLstEmpCityList(lstCity);
		setEmpCityId(null);


	}

	/*
	 * public List<CityMasterDesc> getEmpCityListFromDB() { return lstCity;
	 * 
	 * }
	 */

	public List<CityMasterDesc> getCityListFromDB() {
		return lstCity;

	}

	/**
	 * This is responsible to render Panel, depending upon Employment Type
	 * selection
	 * 
	 * @param event
	 */
	public void getEmploymentStatus(AjaxBehaviorEvent event) {
		/* 53 means unemployed and 0 means Select */

		if (getEmploymentType().intValue() == getGeneralService().getComponentId(Constants.EMPLOYMENTTYPE,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()
				|| getEmploymentType().intValue() == 0) {
			if(getPkCustomerEmployeeId()!=null && customerEmploymentInfoList.size() != 0){
				RequestContext.getCurrentInstance().execute("unemployeechange.show();");
			}else {
				setBooEmploymentPanel(false);
			}
		} else {
			setBooEmploymentPanel(true);
		}
		if(getAdditinal2()!=null){
		setEmployer(getAdditinal2());
		}

	}

	public void employeeStatusIfYes(){
		icustomerRegistrationService.deleteEmployeeInfo(getPkCustomerEmployeeId());
		setBooEmploymentPanel(false);
		clearCustomerEmployeeOnChange();
	}

	public void clearCustomerEmployeeOnChange(){
		setOccupation(null);
		setEmployer(null);
		setEmpInfoBlock(null);
		setEmparea(null);
		setEmpCountryId(null);
		setEmpStateId(null);
		setEmpCityId(null);
		setEmpDistrictId(null);
		setEmpInfoStreet(null);
		setOfficeTel(null);
		setPostalCode(null);
		setDepartment(null);
		customerEmploymentInfoList.clear();
		lstEmpDistrictList.clear();
		lstEmpCityList.clear();
	}

	public void employeeStatusIfNo(){
		if (customerEmploymentInfoList.size() != 0) {
			setEmploymentType(customerEmploymentInfoList.get(0).getFsBizComponentDataByEmploymentTypeId().getComponentDataId());
			setBooEmploymentPanel(true);
		}
	}

	public void getIDTypeValue(AjaxBehaviorEvent event) {
		setSelectedIdType(getIdType());
		setBooIdTypeCheck(false);
	}



	public void onblurId(AjaxBehaviorEvent event) {

		if (getIdType() != null) {
			if (sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {

				mapIdentityType = ruleEngine.getComponentData("Identity Type");
				BigDecimal identityTpeId = null;
				for (Map.Entry<BigDecimal, String> entry : mapIdentityType
						.entrySet()) {
					if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
						identityTpeId = entry.getKey();
						break;
					}
				}
			
				if (getSelectedIdType() != null
						&& getSelectedIdType().equalsIgnoreCase(
								identityTpeId.toPlainString())) {

					boolean status = false;
					try {
						String returnString = getGeneralService()
								.getCivilIdStatus(new BigDecimal(getIdNumber()));
						if (returnString.equalsIgnoreCase("y")) {
							status = true;
						} else {
							status = false;
						}
					} catch (Exception e) {
						status = false;
					}

					setBooIdTypeCheck(!status);
				} else {
					setBooIdTypeCheck(false);
				}
			}
		}

	}

	public void getIDTypeProofValue(AjaxBehaviorEvent event) throws ParseException {
		setSelectedIdType(getIdTypeproof().toPlainString());
		setBooIdTypeCheck(false);

		if(getIdTypeproof().intValue() == new BigDecimal(getIdType()).intValue()){
			setIdnumberProof(getIdNumber());
			setReadOnlyIdNumber(true);
		}else{
			setIdnumberProof(null);	
			setReadOnlyIdNumber(false);
		}

		if(getSelectType() != null &&  !getSelectType()
				.equalsIgnoreCase(
						getGeneralService()
						.getComponentId(
								Constants.METHODTYPE,
								new BigDecimal(
										sessionStateManage
										.isExists("languageId") ? sessionStateManage
												.getSessionValue("languageId")
												: "" + 1))
												.getFsBizComponentData().getComponentDataId()
												.toPlainString())){
			setDateExp(new SimpleDateFormat("dd/MM/yyyy").parse(getExpirydate()));

		}

		String validity = generalService.getValidity(sessionStateManage.getCountryId(),
				new BigDecimal(getSelectedIdType()));

		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, new Integer(validity));
		Date today90 = cal.getTime();
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
		String finalDate = form.format(today90);
		setExpDateCheck(finalDate);
		String finalsDate = form.format(new Date());
		setMinDob(finalsDate);

	}

	public void onblurIdProof(AjaxBehaviorEvent event) {

		mapIdentityType = ruleEngine.getComponentData("Identity Type");
		BigDecimal identityTpeId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
				identityTpeId = entry.getKey();
				break;
			}
		}

		if (getIdTypeproof() != null
				&& getIdTypeproof().intValue()==identityTpeId.intValue()) {

			boolean status = false;
			try {
				String returnString = getGeneralService()
						.getCivilIdStatus(new BigDecimal(getIdnumberProof()));
				if (returnString.equalsIgnoreCase("y")) {
					status = true;
				} else {
					status = false;
				}
			} catch (Exception e) {
				status = false;
			}

			setBooIdTypeCheck(!status);
		} else {
			setBooIdTypeCheck(false);
		}
	}


	public void idInfo() {

		setBooRemitterInfo(false);
		setBooIdDetail(true);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooEmploymentDetails(false);
		setBooProof(false);
		setBooVerifyAllRen(false);

		setRenderUpdate(false);
		setRenderRegistration(false);
		setRenderVerifyReport(false);
		setRenderFinal(false);
		setExitRender(false);
		setNextSignaturePanel(false);
		// start by subramanian 10/02/2015
		setBooIdTypeCheck(false);
		// End by subramanian 10/02/2015
		setRenderIntroducePanel(false);
		setBooManualGo(true);
		setBoofirstPanel(false);

	}

	/**
	 * Next or Back button is pressed from Remitter Panel, next panel is contact
	 * details This method called from Go button
	 * 
	 * @throws ParseException
	 */

	private String customerExpDate;

	public String getCustomerExpDate() {
		return customerExpDate;
	}

	public void setCustomerExpDate(String customerExpDate) {
		this.customerExpDate = customerExpDate;
	}

	public void nextRemitterInfo() throws ParseException {

		setDisablePipsDropdown(false);
		nomineeRegistration.setCivilID(null);
		nomineeRegistration.setFirstName(null);
		nomineeRegistration.setFirstNameLocal(null);
		nomineeRegistration.setNationality(null);
		nomineeRegistration.setEffectiveDate(null);
		nomineeRegistration.setMinDate(null);

		//setEnableCheckId(true);
		setPkCustomerId(null);
		setBooEmailCheck(false);
		setBooMobilecheck(false);
		setMinagevalidation(false);
		contactList.clear();
		createProofList.clear();
		contactListDelete.clear();
		createProofListDelete.clear();
		setNextUpdateRender(false);
		setBoofirstPanel(true);
		setRenderIntroducePanel(true);
		setBahrainCardPanel(false);

		mapIdentityType = ruleEngine.getComponentData("Identity Type");

		BigDecimal identityTpeIds = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
				identityTpeIds = entry.getKey();
				break;
			}
		}

		BigDecimal idtypeCivilIdnew = getGeneralService().getComponentId(Constants.CIVIL_ID_NEW,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
		if(getIdType()!=null && idtypeCivilIdnew!=null && getIdType().equalsIgnoreCase(identityTpeIds.toString()) || getIdType().equalsIgnoreCase(idtypeCivilIdnew.toString())){
			customerIdProofList = getIcustomerRegistrationService().getCustomerIdProofCheck(new BigDecimal(identityTpeIds.toString()),getIdNumber(),session.getCountryId());

			if (customerIdProofList.size() == 0) {
				customerIdProofList = getIcustomerRegistrationService().getCustomerIdProofCheck(idtypeCivilIdnew,getIdNumber(),session.getCountryId());
			}
		}else{

			customerIdProofList = getIcustomerRegistrationService()
					.getCustomerIdProofCheck(new BigDecimal(getIdType()),
							getIdNumber(),session.getCountryId());
		}

		if (customerIdProofList.size() > 0) {

			//if(customerIdProofList.get(0).getFsCustomer().getIsActive()!=null && customerIdProofList.get(0).getFsCustomer().getIsActive().equalsIgnoreCase(Constants.Yes)){
			//if(customerIdProofList.get(0).getIdentityExpiryDate()!=null && customerIdProofList.get(0).getIdentityExpiryDate().compareTo(new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date())))>=0){
			//if (customerIdProofList.get(0).getFsCustomer().getActivatedInd().equalsIgnoreCase("0")) {
			if (customerIdProofList.get(0).getFsCustomer().getActivatedInd() != null && customerIdProofList.get(0).getFsCustomer().getActivatedInd().equalsIgnoreCase(Constants.CUST_INACTIVE_INDICATOR)) {
				RequestContext.getCurrentInstance().execute("dlgAlready.show();");
			} else if (customerIdProofList.get(0).getFsCustomer().getSmartCardIndicator().equalsIgnoreCase(Constants.Yes)) {
				RequestContext.getCurrentInstance().execute("dlgSmartcard.show();");
				/*
				 * }else if(customerIdProofList.get(0).getFsCustomer().
				 * getVerificationTokenId()!=null){
				 * RequestContext.getCurrentInstance
				 * ().execute("dlgverified.show();");
				 */
			} else {
				setBooRemitterInfo(true);
				setBooIdDetail(false);
				setBooCustomerDetails(false);
				setBooContactDetails(false);
				setBooEmploymentDetails(false);
				setBooProof(false);
				setBooOtherId(false);
				setBooCivilId(true);
				fetchCustomerInfo();

				setRenderSavebutton(false);
				setRenderSignature(false);
				setBooVerifyAllRen(false);
				setRenderverication(false);
				setSignaturePanelRender(false);

				setRenderUpdate(false);
				setRenderRegistration(false);
				setRenderVerifyReport(false);
				setRenderFinal(false);
				setExitRender(false);
				setNextSignaturePanel(false);
				setNextUpdateRender(false);
				setBooManualGo(false);
				setRenderIntroducePanel(true);
			}
			/*}else{
					if (customerIdProofList.get(0).getIdentityExpiryDate() != null){
						setCustomerExpDate(new SimpleDateFormat("dd/MM/yyyy").format(customerIdProofList.get(0).getIdentityExpiryDate()));
						}
					RequestContext.getCurrentInstance().execute("expiredCustomer.show();");
				}*/
			/*}else{
				RequestContext.getCurrentInstance().execute("notactive.show();");
			}*/

		} else {
			setRenderVerifyReport(false);
			setSignaturePanelRender(false);
			setRenderUpdate(false);
			setRenderRegistration(false);
			setBooCivilId(false);
			setBooOtherId(false);
			// getIDTypeValue(AjaxBehaviorEvent event);

			clearRemitterInfo();
			clearContactDetail();
			clearEmploymentInfo();
			clearProofInfo();
			contactList.clear();
			createProofList.clear();
			setNextUpdateRender(false);


			// FS_INSERT_CUSMAS - Trigger is responsible to generate CUSTOMER REFERENCE NUMBER 

			//getDealYear();
			//getDocument();
			//getDocumentSerialID(processIn);

			mapIdentityType = ruleEngine.getComponentData("Identity Type");
			BigDecimal identityTpeId = null;
			for (Map.Entry<BigDecimal, String> entry : mapIdentityType
					.entrySet()) {
				if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
					identityTpeId = entry.getKey();
					break;
				}
			}

			if (sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)
					&& getIdType().equalsIgnoreCase(identityTpeId.toString())) {
				System.out.println("==IIIIIIIIIIIIIIIIIIIIIIIIIIIi="
						+ identityTpeId.toPlainString());
				// SessionStateManage sessionStateManage = new
				// SessionStateManage();
				if (getIdType().equalsIgnoreCase(identityTpeId.toPlainString())) {

					/** Date of birth manipulation from CIVIL ID */
					String id = getIdNumber();
					String dob = null;
					if (id.charAt(0) == '2') {
						dob = id.substring(5, 7) + "/" + id.substring(3, 5)
								+ "/19" + id.substring(1, 3);
					} else {
						dob = id.substring(5, 7) + "/" + id.substring(3, 5)
								+ "/20" + id.substring(1, 3);
					}
					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd/MM/yyyy");
					Date date = formatter.parse(dob);
					setDob(date);
					setBooCivilId(true);
					setBooOtherId(false);
					setChangeDobPass(true);
					setShowDob(formatter.format(date));

					boolean status = false;
					try {
						String returnString = getGeneralService()
								.getCivilIdStatus(new BigDecimal(getIdNumber()));
						if (returnString.equalsIgnoreCase("y")) {
							status = true;
							fetchCustomerInfo();
							if (getSelectType() != null
									&& getSelectType()
									.equalsIgnoreCase(
											getGeneralService()
											.getComponentId(
													Constants.METHODTYPE,
													sessionStateManage
													.getLanguageId())
													.getFsBizComponentData()
													.getComponentDataId()
													.toPlainString())) {

								setBooRemitterInfo(true);
								setBooIdDetail(false);
								setBooCustomerDetails(false);
								setBooContactDetails(false);
								setBooEmploymentDetails(false);
								setBooProof(false);
								setRenderSavebutton(false);
								setRenderSignature(false);
								setBooVerifyAllRen(false);
								setRenderverication(false);
								setSignaturePanelRender(false);
								setRenderUpdate(false);
								setRenderRegistration(false);
								setRenderVerifyReport(false);
								setRenderFinal(false);
								setExitRender(false);
								setNextSignaturePanel(false);
								setNextUpdateRender(false);
								setRenderIntroducePanel(true);
								setBooManualGo(false);

							} else {
								setBooRemitterInfo(false);
								setBooIdDetail(false);
								setBooCustomerDetails(true);
								setBooContactDetails(false);
								setBooEmploymentDetails(false);
								setBooProof(false);
								setRenderSavebutton(false);
								setRenderSignature(false);
								setBooVerifyAllRen(false);
								setRenderverication(false);
								setSignaturePanelRender(false);
								setRenderUpdate(false);
								setRenderRegistration(false);
								setRenderVerifyReport(false);
								setRenderFinal(false);
								setExitRender(false);
								setNextSignaturePanel(false);
								setNextUpdateRender(false);
								setRenderIntroducePanel(true);
								setBooManualGo(false);

							}

						} else {
							status = false;

						}
					} catch (Exception e) {
						status = false;
					}

					setBooIdTypeCheck(!status);

				}
			} else {
				setBooCivilId(false);
				setBooOtherId(true);
				setBooIdTypeCheck(false);
				fetchCustomerInfo();
				if (getSelectType() != null
						&& getSelectType()
						.equalsIgnoreCase(
								getGeneralService()
								.getComponentId(
										Constants.METHODTYPE,
										new BigDecimal(
												sessionStateManage
												.isExists("languageId") ? sessionStateManage
														.getSessionValue("languageId")
														: "" + 1))
														.getFsBizComponentData()
														.getComponentDataId()
														.toPlainString())) {

					setBooRemitterInfo(true);
					setBooIdDetail(false);
					setBooCustomerDetails(false);
					setBooContactDetails(false);
					setBooEmploymentDetails(false);
					setBooProof(false);
					setRenderSavebutton(false);
					setRenderSignature(false);
					setBooVerifyAllRen(false);
					setRenderverication(false);
					setSignaturePanelRender(false);
					setRenderUpdate(false);
					setRenderRegistration(false);
					setRenderVerifyReport(false);
					setRenderFinal(false);
					setExitRender(false);
					setNextUpdateRender(false);
					setRenderIntroducePanel(true);
					setBooManualGo(false);

				} else {
					setBooRemitterInfo(false);
					setBooIdDetail(false);
					setBooCustomerDetails(true);
					setBooContactDetails(false);
					setBooEmploymentDetails(false);
					setBooProof(false);
					setRenderSavebutton(false);
					setRenderSignature(false);
					setBooVerifyAllRen(false);
					setRenderverication(false);
					setSignaturePanelRender(false);
					setRenderUpdate(false);
					setRenderRegistration(false);
					setRenderVerifyReport(false);
					setRenderFinal(false);
					setExitRender(false);
					setNextUpdateRender(false);
					setRenderIntroducePanel(true);
					setBooManualGo(false);

				}

			}

		}

	}

	/**
	 * Next or Back button is pressed from Contact Detail Panel, next panel is
	 * Employment details and Back panel is Remitter Info
	 * @throws Exception 
	 */

	public Boolean nextContactDetails(){
		
		getContactTypeList();

		if (getBooMobilecheck() == false && getBooEmailCheck() == false) {
			setBooRemitterInfo(false);
			setBooIdDetail(false);
			setBooCustomerDetails(false);
			setBooContactDetails(true);
			setBooEmploymentDetails(false);
			setBooProof(false);
			setContactlistcheck(false);
			setRenderSavebutton(false);
			setRenderSignature(false);
			setBooVerifyAllRen(false);
			setRenderverication(false);
			setSignaturePanelRender(false);
			setRenderUpdate(false);
			setRenderRegistration(false);
			setRenderVerifyReport(false);
			setRenderFinal(false);
			setExitRender(false);
			setNextSignaturePanel(false);
			//amlServiceSearch();
			clearContactDetail();
			setNextUpdateRender(false);
			setRenderIntroducePanel(false);
			setBooManualGo(false);
			setBoofirstPanel(false);
			setBahrainCardPanel(false);
			if(contactList.size()>0){
				setBooContactDetailsButtonPanel(true);
			}else{
				setBooContactDetailsButtonPanel(false);
			}
			
			return true;

		}else{
			return false;
		}
	}

	/**
	 * Next or Back button is pressed from Employment Detail Panel, next panel
	 * is proof details and Back panel is Contact Details Panel
	 */

	public Boolean nextEmploymentDetails() {

		setCountryId(sessionStateManage.getCountryId());
		setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), getCountryId()));
		boolean mandatoryR = false;
		boolean mandatoryP = false;
		if(sessionStateManage.getCountryId().intValue()!= getNationality().intValue()){
		for (AddContactDetailBean contactDetails : contactList) {
			if (contactDetails.getContactTypeId().intValue() == getGeneralService().getComponentId(Constants.PERMANENT, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()) {
				mandatoryP = true;
				setContactlistcheck(false);

			}else if (contactDetails.getContactTypeId().intValue() == getGeneralService().getComponentId(Constants.RESIDENCE, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()) {
				mandatoryR = true;
				setContactlistcheck(false);

			}
		}
		}else{
			 mandatoryR = true;
			 mandatoryP = true;
		}
		if (mandatoryP && mandatoryR) {

			setBooRemitterInfo(false);
			setBooIdDetail(false);
			setBooCustomerDetails(false);
			setBooContactDetails(false);
			setBooProof(false);
			setBooEmploymentDetails(true);
			setBooRenderFinalSave(false);
			setBooContactDetailsDuplicate(false);
			setRenderSavebutton(false);
			setRenderSignature(false);
			setBooVerifyAllRen(false);
			setRenderverication(false);
			setSignaturePanelRender(false);
			setRenderUpdate(false);
			setRenderRegistration(false);
			setRenderVerifyReport(false);
			setRenderFinal(false);
			setExitRender(false);
			setNextSignaturePanel(false);
			setNextUpdateRender(false);
			setRenderIntroducePanel(false);
			setBooManualGo(false);
			setBoofirstPanel(false);
			setBahrainCardPanel(false);
			return true;
		} else {
			setContactlistcheck(true);
			return false;
		}

	}

	/**
	 * Back button is pressed from Employment Detail Panel, Back panel is
	 * Employment Details Panel
	 * @throws ParseException 
	 */


	public Boolean nextProofDetails() {
		boolean returnCheck = false;
		try {
			boolean verify = true;
			for (CreateProofTable createProofTable : createProofList) {

				if (createProofTable.getDate_expiary() != null) {
					SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
					if(dateformat.parse(createProofTable.getDate_expiary()).compareTo(dateformat.parse(dateformat.format(new Date())))<=0)
					{
						verify = false;
					}else{
						verify = true;
						break;
					}

				}
			}

			if(verify == true){

				int i = 0;
				int j=1;

				for (CreateProofTable createProofTable : createProofList) {
					if (getIdType().equalsIgnoreCase(createProofTable.getId_type())) {
						i = 1;
						break;
					} else {
						i = 0;
					}
				}

				if (i == 1) {
					j=1;
					if (getBooChangeDob()) {

						for (CreateProofTable data : createProofList) {

							if (data.getId_for().equalsIgnoreCase(getGeneralService().getComponentId(Constants.CHANGE_DOB,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString())) {
								j=1;
								break;
							} else {
								j=0;
							}
						}
					}

					if(j==1){	

						String verificationToken = null;
						String updatedBy = null;
						setBooRemitterInfo(false);
						setBooIdDetail(false);
						setBooCustomerDetails(false);
						setBooContactDetails(false);
						setBooProof(false);
						setBooEmploymentDetails(false);
						setRenderSavebutton(false);
						setBooVerifyAllRen(false);
						setRenderSignature(false);
						setRenderverication(false);
						setSignaturePanelRender(true);
						setRenderUpdate(false);
						setRenderRegistration(false);
						setRenderVerifyReport(false);
						setRenderFinal(false);
						setRenderIntroducePanel(false);
						setBooManualGo(false);
						setBoofirstPanel(false);
						setBahrainCardPanel(false);
						setExitRender(false);
						setNextSignaturePanel(false);		
						setBooRenderFinalSave(true);
						setNextUpdateRender(false);
						BigDecimal dailyLimit = null;
						BigDecimal monthyLimit = null;
						Boolean signaturecheck = false;

						if (getPkCustomerId() != null) {
							List<Customer> custlist = getIcustomerRegistrationService().getVerificationToken(getPkCustomerId());
							if(custlist.size()!=0){
								Customer customerlst = custlist.get(0);
								verificationToken = customerlst.getVerificationTokenId();
								updatedBy = customerlst.getUpdatedBy();
								dailyLimit= customerlst.getDailyLimit();
								monthyLimit= customerlst.getDailyLimit();
								if(customerlst.getSignatureSpecimen() == null){
									signaturecheck = false;
								}else{
									signaturecheck = true;
								}
							}
							
							if(signaturecheck){
								if (dailyLimit != null && monthyLimit != null) {
									setSignatureCaptureRender(false);
									setSignatureSpecimenRender(true);
									setSubmitRender(true);
									setExitRender(true);
									returnCheck = true;
								} else {
									setSignatureCaptureRender(true);
									setSignatureSpecimenRender(false);
									setSubmitRender(false);
									setExitRender(false);
									returnCheck = true;
								}
							}else{
								setSignatureCaptureRender(true);
								setSignatureSpecimenRender(false);
								setSubmitRender(true);
								setExitRender(false);
								returnCheck = true;
							}

						} else {
							setSignatureCaptureRender(true);
							setSignatureSpecimenRender(false);
							setSubmitRender(true);
							setExitRender(false);
							returnCheck = true;
						}

					}else{
						returnCheck = false;
						RequestContext.getCurrentInstance().execute("PF('dobchangemsg').show();");
						
					}
				}else{
					returnCheck = false;
					RequestContext.getCurrentInstance().execute("PF('idtypecheck').show();");
				}
			}else{
				returnCheck = false;
				setEnableScan(false);
				setRenderModifyScan(false);
				RequestContext.getCurrentInstance().execute("idExpired.show();");
			}

		} catch (ParseException e) {
			returnCheck = false;
			e.printStackTrace();
		}
		return returnCheck;
	}

	public Boolean nextSignature() {
		
		try {
			setBooRemitterInfo(false);
			setBooIdDetail(false);
			setBooCustomerDetails(false);
			setBooContactDetails(false);
			setBooProof(true);
			setBooEmploymentDetails(false);
			setRenderSavebutton(false);
			setRenderSignature(false);
			setBooVerifyAllRen(false);
			setRenderSignature(true);
			setRenderverication(false);
			setSignaturePanelRender(false);
			setRenderUpdate(false);
			setRenderRegistration(false);
			setRenderVerifyReport(false);
			setRenderFinal(false);
			setExitRender(false);
			setSubmitRender(false);
			setNextUpdateRender(false);
			setNextSignaturePanel(false);
			setBooRenderFinalSave(false);
			setRenderIntroducePanel(false);
			setBooManualGo(false);
			setBoofirstPanel(false);
			setBahrainCardPanel(false);
			if(createProofList.size()!=0){
				setRenderSignature(true);
			}else{
				setRenderSignature(false);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void verifyConfirmation() {
		setBooRemitterInfo(false);
		setBooIdDetail(false);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooProof(true);
		setBooEmploymentDetails(false);
		setRenderSavebutton(false);
		setRenderSignature(false);
		setBooVerifyAllRen(false);
		setRenderSignature(true);
		setRenderverication(false);

		setSignaturePanelRender(true);
		setRenderVerifyReport(false);
		setRenderFinal(false);
		setExitRender(false);
		setNextUpdateRender(false);
		setRenderIntroducePanel(false);
		setBooManualGo(false);
		setBahrainCardPanel(false);

		/*
		 * System.out.println("customer id="+getPkCustomerId());
		 * if(getPkCustomerId()==null){ setSignatureCaptureRender(true);
		 * setSignatureSpecimenRender(false); }else{
		 * setSignatureCaptureRender(false); setSignatureSpecimenRender(true); }
		 */

	}

	public void backRemitterInfo() {

		if (getSelectType()
				.equalsIgnoreCase(
						getGeneralService()
						.getComponentId(
								Constants.METHODTYPE,
								new BigDecimal(
										sessionStateManage
										.isExists("languageId") ? sessionStateManage
												.getSessionValue("languageId")
												: "" + 1))
												.getFsBizComponentData().getComponentDataId()
												.toPlainString())
												&& getSelectType() != null) {
			setBoofirstPanel(true);
			setBooRemitterInfo(true);
			setBooIdDetail(false);
			setBooCustomerDetails(false);
			setBooContactDetails(false);
			setBooEmploymentDetails(false);
			setBooProof(false);
			setBooContactDetailsDuplicate(false);
			setRenderSavebutton(false);
			setRenderSignature(false);
			setBooVerifyAllRen(false);
			setRenderverication(false);
			setSignaturePanelRender(false);
			setRenderVerifyReport(false);
			setRenderFinal(true);
			setNextSignaturePanel(false);
			setNextUpdateRender(false);
			setRenderIntroducePanel(true);
			setBooManualGo(false);
			// setContactlistcheck(false);

		} else {
			setBoofirstPanel(false);
			setBooRemitterInfo(false);
			setBooIdDetail(false);

			setBooContactDetails(false);
			setBooEmploymentDetails(false);
			setBooProof(false);
			setBooContactDetailsDuplicate(false);
			setRenderSavebutton(false);
			setRenderSignature(false);
			setBooVerifyAllRen(false);
			setRenderverication(false);
			setSignaturePanelRender(false);
			setRenderVerifyReport(false);
			setRenderFinal(false);
			setNextSignaturePanel(false);
			setNextUpdateRender(false);
			setRenderIntroducePanel(true);
			setBooManualGo(false);
			if(sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)){
				setBooCustomerDetails(true);
			}else if(sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.BAHRAIN_ALPHA_TWO_CODE)){
				setBahrainCardPanel(true);
			}

		}

	}

	public void backContactDetails() {
		System.out.println("..." + getLevel() + "OCuupation : "
				+ getOccupation());
		setBooRemitterInfo(false);
		setBooIdDetail(false);
		setBooCustomerDetails(false);
		setBooContactDetails(true);
		setBooEmploymentDetails(false);
		setBooProof(false);
		setRenderSavebutton(false);
		setRenderSignature(false);
		setBooVerifyAllRen(false);
		setRenderverication(false);
		setRenderVerifyReport(false);
		setRenderFinal(false);
		setNextSignaturePanel(false);
		setNextUpdateRender(false);
		setRenderIntroducePanel(false);
		setBooManualGo(false);
		setBoofirstPanel(false);
		setBahrainCardPanel(false);
		setContactDetails();

	}

	public void backEmploymentDetails() {

		/*
		 * if (createProofList.size() == 0) { setIdListcheck(true);
		 * 
		 * } else {
		 */
		setBooRemitterInfo(false);
		setBooIdDetail(false);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooProof(false);
		setBooEmploymentDetails(true);
		setRenderSavebutton(false);
		setRenderSignature(false);
		setBooVerifyAllRen(false);
		setRenderverication(false);
		setSignaturePanelRender(false);
		setRenderVerifyReport(false);
		setRenderFinal(false);
		setNextSignaturePanel(false);
		setNextUpdateRender(false);
		setRenderIntroducePanel(false);
		setBooManualGo(false);
		setBoofirstPanel(false);
		setBahrainCardPanel(false);
		// }
	}

	public void backProofDetails() throws ParseException {

		/*
		 * if (createProofList.size() == 0) { setIdListcheck(true);
		 * 
		 * } else {
		 */

		setBooRemitterInfo(false);
		setBooIdDetail(false);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooProof(false);


		//Viswa@@@
		setBooEmploymentDetails(true);
		//


		setRenderSavebutton(false);
		setRenderSignature(false);
		setBooVerifyAllRen(false);
		setRenderverication(false);		

		//Viswa@@@
		setSignaturePanelRender(false);
		//

		setRenderVerifyReport(false);
		setRenderFinal(false);
		setNextSignaturePanel(false);
		setBooRenderFinalSave(false);
		setNextUpdateRender(false);
		setRenderIntroducePanel(false);
		setBooManualGo(false);
		setBoofirstPanel(false);
		setBahrainCardPanel(false);
		setEmployeementInfo();
		setDepenedentDetails();

	}
	
	public Boolean smartcardcheck = false;

	public Boolean getSmartcardcheck() {
		return smartcardcheck;
	}

	public void setSmartcardcheck(Boolean smartcardcheck) {
		this.smartcardcheck = smartcardcheck;
	}

	/**
	 * this panel is used for card Details
	 * 
	 * @throws ParseException
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws DOMException 
	 */

	public void appearCarddetail() throws ParseException, DOMException, ParserConfigurationException, SAXException, IOException {



		if (getSelectType()
				.equalsIgnoreCase(
						getGeneralService()
						.getComponentId(
								Constants.METHODTYPE,
								new BigDecimal(
										sessionStateManage
										.isExists("languageId") ? sessionStateManage
												.getSessionValue("languageId")
												: "" + 1))
												.getFsBizComponentData().getComponentDataId()
												.toPlainString())
												&& getSelectType() != null) {

			setBooRemitterInfo(false);
			setBooCustomerDetails(false);
			setBooManual(true);
			setBoosmartCardAppear(false);
			setRenderverication(false);
			setSignaturePanelRender(false);
			setRenderVerifyReport(false);
			setRenderFinal(false);
			setNextSignaturePanel(false);
			setNextUpdateRender(false);
			setRenderIntroducePanel(false);
			setBooManualGo(true);
			setBoofirstPanel(false);
			setBahrainCardPanel(false);
			setIdNumber(null);
			setEmail(null);
			setMobile(mobile);
			setSmartcardcheck(false);
			setMobileNoFetch(null);
			setMsgMobileNoFetch(false);
			setRenManualArticle(true);
			setRenCardArticle(false);
			setBooCheckMobile(false);
			setBooMobileHide(false);
			setBooIdType(true);
			setPkCustomerId(null);

		} else {
			setSmartCardId(null);
			setSmartcardcheck(true);
			setRenManualArticle(false);
			setRenCardArticle(true);
			setPkCustomerId(null);
			fetchSmartCardData();

			/*setBooRemitterInfo(false);
			setBooManual(false);
			setBoosmartCardAppear(false);

			setBooIdDetail(false);

			setBooContactDetails(false);
			setBooEmploymentDetails(false);
			setBooProof(false);
			setRenderSignature(false);
			setRenderverication(false);
			setSignaturePanelRender(false);
			setRenderVerifyReport(false);
			setRenderFinal(false);
			setNextSignaturePanel(false);
			setNextUpdateRender(false);
			setRenderIntroducePanel(false);
			setBooManualGo(false);

			if(sessionStateManage.getCountryId().intValue() == Constants.KUWAIT_COUNTRY_ID){
				setBooCustomerDetails(true);
				setBahrainCardPanel(false);
			}else if(sessionStateManage.getCountryId().intValue() == Constants.BHRAIN_COUNTRY_ID){
			setBahrainCardPanel(true);
			setBooCustomerDetails(false);
			}
			 */
		}

	}

	/**
	 * This will control upto which panel need to save
	 * 
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 * @throws SerialException
	 */
	/*
	 * public void panelSaveController() throws SerialException, IOException,
	 * SQLException, ParseException { if (getSaveUptoPanel() == 1) {
	 * saveCustomerInfo(); } else if (getSaveUptoPanel() == 2) {
	 * saveContactDetails(); } else if (getSaveUptoPanel() == 3) {
	 * saveEmployementDetails(); } else {
	 * 
	 * saveProofDetails(); } }
	 */


	/*	@SuppressWarnings("unused")
	public String saveCustomerInfo() throws IOException, ParseException {

		customer = new Customer();
		SessionStateManage sessionStateManage = new SessionStateManage();


	 * if (sessionStateManage.isExists("countryId")) { country =
	 * sessionStateManage.getSessionValue("countryId"); }

		// try{
		 Country ID save 

		countryMaster = new CountryMaster();
		countryMaster.setCountryId(sessionStateManage.getCountryId());

		 Nationality Id save 
		CountryMaster nationality = new CountryMaster();
		nationality.setCountryId(getNationality());

		 save company 
		companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(sessionStateManage.getCompanyId());

	 *//** Customer Type *//*

		BizComponentData customerType = new BizComponentData();
		customerType.setComponentDataId(getGeneralService()
				.getComponentId(CUSTOMERTYPE,
						sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId());

	  *//** save Group ID *//*
		BizComponentData companyGroup = new BizComponentData();
		companyGroup.setComponentDataId(getGeneralService()
				.getComponentId(GROUPID, sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId());

		languageType = new LanguageType();
		languageType.setLanguageId(sessionStateManage.getLanguageId());
		customer.setFsLanguageType(languageType);
		customer.setFsCountryMasterByCountryId(countryMaster);
		customer.setShortName(getShortName());
		customer.setShortNameLocal(getShortNamel());
		//customer.setAmlStatus(getAMLStatus());
		customer.setTitle(getTitle());
		customer.setFirstName(getFirstName());
		customer.setMiddleName(getMiddleName());
		customer.setLastName(getLastName());
		customer.setTitleLocal(getTitlel());
		customer.setFirstNameLocal(getFirstNamel());
		customer.setMiddleNameLocal(getMiddleNamel());
		customer.setLastNameLocal(getLastNamel());
		customer.setGender(getGender());
		customer.setDailyLimit(getDailyLimit());

		customer.setWeeklyLimit(getWeeklyLimit());
		customer.setMontlyLimit(getMonthlyLimit());
		customer.setQuaterlyLimit(getQuarterlyLimit());
		customer.setHalfYearly(getHalfyearly());
		customer.setAnnualLimit(getAnnualLimit());
		customer.setSmartCardIndicator("N");
		customer.setIsActive("Y");
		customer.setBranchCode(new BigDecimal(sessionStateManage.getBranchId()));
		customer.setPepsIndicator(getPepsindicator());

		mapIdentityType = ruleEngine.getComponentData("Identity Type");
		BigDecimal identityTpeId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(CIVILID)) {
				identityTpeId = entry.getKey();
				break;
			}
		}

		if (getBooChangeDob()) {

			customer.setDateOfBirth(getDob());

		} else if (getIdType().equalsIgnoreCase(Constants.CIVILID)) {

			customer.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy")
					.parse(getShowDob()));
		} else {
			customer.setDateOfBirth(getDob());
		}

		customer.setAlterEmailId(getAlternativeEmail());

		if (getCustRefId() != null) {
			customer.setIntroducedBy(getCustRefId().toString());
			customer.setIntroducedDate(new Date());

		}
		customer.setMedicalInsuranceInd(getMedicalInsuranceInd());

		customer.setMobile(getMobile());
		customer.setEmail(getEmail());

		// customer.setCreatedBy(userName);
		customer.setActivatedInd("1");

		// TITLE, TITLE(L), Gender ned to be save

		// save to article
		ArticleDetails articleDetail = new ArticleDetails();
		articleDetail.setArticleDetailId(getLevel());
		customer.setFsArticleDetails(articleDetail);

		// save to Income
		IncomeRangeMaster incomeRange = new IncomeRangeMaster();
		incomeRange.setIncomeRangeId(getIncomeRange());
		customer.setFsIncomeRangeMaster(incomeRange);

		customer.setFsCountryMasterByCountryId(countryMaster);
		customer.setFsCompanyMaster(companyMaster);
		customer.setFsBizComponentDataByCustomerTypeId(customerType);
		customer.setFsBizComponentDataByGroupId(companyGroup);
		customer.setFsCountryMasterByNationality(nationality);

		if (getApproved()) {
			// customer.setActivatedInd("1");
			customer.setActivatedDate(new Date());
			customer.setVerificationBy(userName);
			customer.setVerificationDate(new Date());
		} else {
			// customer.setActivatedInd("0");
			// customer.setInactivatedDate(new Date());
		}

		for (CreateProofTable createProofTable : createProofList) {

			if (createProofTable.getChecked() != null) {
				if (createProofTable.getChecked()) {
					//CR for created date based on application Country
					//customer.setActivatedDate(new Date());
					customer.setActivatedDate(getCurrentTime());
					customer.setVerificationBy(userName);
					//CR for created date based on application Country
					//customer.setVerificationDate(new Date());
					customer.setVerificationDate(getCurrentTime());
				} else {
					// customer.setInactivatedDate(new Date());
				}
			}
		}

		boolean isSignature = false;

		 Managing save or update 
		if (getPkCustomerId() != null) {
			customer.setCustomerId(getPkCustomerId());
			customer.setUpdatedBy(userName);
			//CR for created date based on application Country
			//customer.setLastUpdated(new Date());
			customer.setLastUpdated(getCurrentTime());
			customer.setCreatedBy(getCreatedByCustomer());
			customer.setCreationDate(getCreationDateCustomer());
			customer.setCustomerReference(getUpdateCustomerRefNo());
			customer.setSignatureSpecimen(getSignatureSpecimen());
			// checkLimit();

		} else {
			customer.setCreatedBy(userName);
			//CR for created date based on application Country
			//customer.setCreationDate(new Date());
			//java.sql.Date tempDate= (java.sql.Date) new Date(getCurrentTime().getTime());
			//image.setUploadDate(convertFromSQLDateToJAVADate(tempDate));
			customer.setCreationDate(getCurrentTime());
			//customer.setCustomerReference(getCustomerRefNo());


			if (getDigitalSignature() != null) {
				customer.setSignatureSpecimen(getDigitalSignature());
			} else {
				isSignature = true;
				RequestContext.getCurrentInstance().execute(
						"signatureMandatory.show();");

			}

		}


	   * if (getApproved()) { // customer.setActivatedInd("1");
	   * customer.setActivatedDate(new Date());
	   * customer.setVerificationBy(userName);
	   * customer.setVerificationDate(new Date()); } else { //
	   * customer.setActivatedInd("0"); //customer.setInactivatedDate(new
	   * Date()); }
	   * 
	   * for (CreateProofTable createProofTable : createProofList) {
	   * 
	   * if (createProofTable.getChecked() != null) { if
	   * (createProofTable.getChecked()) { customer.setActivatedDate(new
	   * Date()); customer.setVerificationBy(userName);
	   * customer.setVerificationDate(new Date()); } else {
	   * //customer.setInactivatedDate(new Date()); } } }

		if (!isSignature) {


	   * AML Check done here

			 @@@ AML	
			String amlReturnStatus = null;
			String amlStatus = null;
			String amlhits = null;

			//amlReturnStatus = getAMLCheckStatus();
			amlReturnStatus ="PASS-0";

			if (amlReturnStatus == null) {
				RequestContext.getCurrentInstance().execute(
						"amlerror.show();");
			} else {

				String[] parts = amlReturnStatus.split("-");
				amlStatus = parts[0];
				//amlStatus ="PENDING";
				amlhits  = parts[1];
				if (amlStatus
						.equalsIgnoreCase(Constants.FINSCAN_STATUS_ERROR)) {

					RequestContext.getCurrentInstance().execute(
							"amlerror.show();");

				} else {

					customer.setNumberOfHits(new BigDecimal(amlhits));
					if(amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PENDING)){
						customer.setAmlStatus(Constants.AML_STATUS_BCO);
					}
					if(amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PASS)){
						customer.setAmlStatus(Constants.AML_STATUS_PASS);
					}

					getIcustomerRegistrationService().saveCustomer(customer);
					setPkCustomerId(customer.getCustomerId());
					customerIdToNominee = customer.getCustomerId();


	   * AML Status Save to EX_AML_CHECK table

				 @@@@ AML	
					if(amlStatus.equals(Constants.FINSCAN_STATUS_PENDING)){
						//saveAmlStatus(customer);
					}	


					saveContactDetails();
					saveEmployementDetails();

					if (createProofList.size() == 0) {

						setIdListcheck(true);
						return "";
					} else {
						setIdListcheck(false);
						saveProofDetails();


						if (getNominee()) {
							getNomineeRegistration().setNominatorId(
									customerIdToNominee, "first");
							return "nominee";
						} else {
							try {
								ExternalContext context = FacesContext
										.getCurrentInstance()
										.getExternalContext();
								// context.invalidateSession();

	   * setBooIdDetail(true);
	   * setBooRemitterInfo(false);
	   * setBooCustomerDetails(false);
	   * setBooContactDetails(false);
	   * setBooEmploymentDetails(false);
	   * setBooProof(false);


								String verificationToken = null;
								String updatedBy = null;
								List<Customer> custlist = new ArrayList<Customer>();
								custlist = getIcustomerRegistrationService()
										.getVerificationToken(getPkCustomerId());
								if (custlist.size() > 0) {
									verificationToken = custlist.get(0)
											.getVerificationTokenId();
									updatedBy = custlist.get(0).getUpdatedBy();
								}

								if (updatedBy != null) {

									String toMailid = custlist.get(0)
											.getEmail();
									mailService1.sendMail(toMailid,
											"Customer Info Log Difference",
											verificationToken);

									setSignaturePanelRender(false);
									setRenderUpdate(true);
									setRenderRegistration(false);
									setBooProof(false);
									setRenderSignature(false);
									setRenderVerifyReport(false);
									setBooRenderFinalSave(false);
									setExitRender(false);
									setSuccessPanel(false);
									setUpdatePanel(false);
									setBooVerifyAllRen(false);
									setNextSignaturePanel(false);
									setNextUpdateRender(false);

								} else {
									// first time registration submited
									setExitRender(false);
									setBooRenderFinalSave(false);

									setSignaturePanelRender(false);
									setSignatureCaptureRender(false);
									setSignatureSpecimenRender(false);

									setBooVerifyAllRen(false);

									setRenderUpdate(false);
									setRenderRegistration(false);
									setBooProof(false);
									setRenderSignature(false);
									setRenderVerifyReport(false);

									setBooRemitterInfo(false);
									setBooIdDetail(false);
									setBooCustomerDetails(false);
									setBooContactDetails(false);
									setBooEmploymentDetails(false);
									setBooProof(false);
									setRenderSavebutton(false);
									setRenderSignature(false);
									setBooVerifyAllRen(false);
									setRenderverication(false);
									setSuccessPanel(true);
									setUpdatePanel(false);

									setRenderUpdate(false);
									setRenderRegistration(false);
									setRenderVerifyReport(false);
									setRenderFinal(false);
									setExitRender(false);
									setBooVerifyAllRen(false);
									setNextSignaturePanel(false);
									setNextUpdateRender(false);


								}

							} catch (Exception e) {
								log.info("Problem to redirect:" + e);
							}

						}
					}
					getAMLCheckStatus_afterSave(getPkCustomerId());
			 @@@@@@@@@ AML			
				}
			}
		}
		return null;

	}*/

	/**
	 * Save in Contact Details
	 */
	/*	public String saveContactDetails() throws IOException, ParseException {

		SessionStateManage sessionStateManage = new SessionStateManage();
		countryMaster = new CountryMaster();
		stateMaster = new StateMaster();
		districtMaster = new DistrictMaster();
		cityMaster = new CityMaster();
		// contactType = new ContactType();

		customer = new Customer();
		ContactDetail contactDetail = null;

		 Calculating Language ID 

		languageType = new LanguageType();
		languageType.setLanguageId(sessionStateManage.getLanguageId());

		for (AddContactDetailBean details : contactList) {

			contactDetail = new ContactDetail();

			BizComponentData contactType = new BizComponentData();
			contactType.setComponentDataId(details.getContactTypeId());
			contactDetail.setFsBizComponentDataByContactTypeId(contactType);

			customer.setCustomerId(getPkCustomerId());
			contactDetail.setFsCustomer(customer);
			contactDetail.setFsCountryMaster(countryMaster);
			contactDetail.setFsLanguageType(languageType);
			contactDetail.setFsStateMaster(stateMaster);
			contactDetail.setFsDistrictMaster(districtMaster);


			countryMaster.setCountryId(details.getCountryId());
			stateMaster.setStateId(details.getStateId());
			districtMaster.setDistrictId(details.getDistrictId());

			if(details.getCityId()!=null)
			{
				cityMaster.setCityId(details.getCityId());
				contactDetail.setFsCityMaster(cityMaster);
			}


			contactDetail.setArea(details.getArea());
			contactDetail.setBlock(details.getBlock());
			contactDetail.setFlat(details.getFlat());
			contactDetail.setStreet(details.getStreet());
			contactDetail.setTelephone(details.getTel());

			contactDetail.setCreatedBy(userName);

			//CR for created date based on application Country			
			//contactDetail.setCreationDate(new Date());
			contactDetail.setCreationDate(getCurrentTime());
			contactDetail.setActiveStatus("1");
			contactDetail.setBuildingNo(details.getBuildingNo());

			if (details.getContactDetailsId()!= null) {
				contactDetail.setContactDetailId(details.getContactDetailsId());
				contactDetail.setCreatedBy(details.getCreatedByContact());
				contactDetail.setCreationDate(details.getCreatedDateContact());
				contactDetail.setUpdatedBy(userName);
				//CR for created date based on application Country
				//contactDetail.setLastUpdated(new Date());
				contactDetail.setLastUpdated(getCurrentTime());
			} else {
				contactDetail.setCreatedBy(userName);
				//CR for created date based on application Country	
				//contactDetail.setCreationDate(new Date());

				contactDetail.setCreationDate(getCurrentTime());

			}

			getIcustomerRegistrationService().saveContactDetails(
					contactDetail);

		}

			if(contactListDelete.size()>0){
			for (AddContactDetailBean details : contactListDelete) {

				contactDetail = new ContactDetail();
				customer.setCustomerId(getPkCustomerId());

				BizComponentData contactType = new BizComponentData();
				contactType.setComponentDataId(details.getContactTypeId());
				contactDetail.setFsBizComponentDataByContactTypeId(contactType);
				contactDetail.setFsCustomer(customer);
				contactDetail.setFsCountryMaster(countryMaster);
				contactDetail.setFsLanguageType(languageType);
				contactDetail.setFsStateMaster(stateMaster);
				contactDetail.setFsDistrictMaster(districtMaster);


				countryMaster.setCountryId(details.getCountryId());
				stateMaster.setStateId(details.getStateId());
				districtMaster.setDistrictId(details.getDistrictId());

				if(details.getCityId()!=null)
				{
					cityMaster.setCityId(details.getCityId());
					contactDetail.setFsCityMaster(cityMaster);
				}


				contactDetail.setArea(details.getArea());
				contactDetail.setBlock(details.getBlock());
				contactDetail.setFlat(details.getFlat());
				contactDetail.setStreet(details.getStreet());
				contactDetail.setTelephone(details.getTel());

				contactDetail.setCreatedBy(userName);
				//CR for created date based on application Country	
				//contactDetail.setCreationDate(new Date());
				contactDetail.setCreationDate(getCurrentTime());
				contactDetail.setActiveStatus("0");
				contactDetail.setBuildingNo(details.getBuildingNo());

					contactDetail.setContactDetailId(details.getContactDetailsId());
					contactDetail.setCreatedBy(details.getCreatedByContact());
					contactDetail.setCreationDate(details.getCreatedDateContact());
					contactDetail.setUpdatedBy(userName);
					//CR for created date based on application Country	
					//contactDetail.setLastUpdated(new Date());

					contactDetail.setLastUpdated(getCurrentTime());
				getIcustomerRegistrationService().saveContactDetails(
						contactDetail);
			}

			}




		// }

		return "";

	}*/

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
	 * Save in CustomerEmployment Table
	 */
	/*public void saveEmployementDetails() throws IOException, ParseException {

		System.out.println("====CALLED EMPLOyment==============");

		CustomerEmploymentInfo custEmp = new CustomerEmploymentInfo();

		customer = new Customer();
		customer.setCustomerId(getPkCustomerId());
		custEmp.setFsCustomer(customer);
		languageType = new LanguageType();
		languageType.setLanguageId(sessionStateManage.getLanguageId());
		custEmp.setFsLanguageType(languageType);

		 save company 
		companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(sessionStateManage.getCompanyId());

		BizComponentData employemntType = new BizComponentData();
		employemntType.setComponentDataId(getEmploymentType());
		custEmp.setFsBizComponentDataByEmploymentTypeId(employemntType);

		try {
			// if (getEmploymentType().intValue() != 53) {

			BizComponentData occupation = new BizComponentData();
			occupation.setComponentDataId(getOccupation());
			custEmp.setFsBizComponentDataByOccupationId(occupation);

			countryMaster = new CountryMaster();
			stateMaster = new StateMaster();
			districtMaster = new DistrictMaster();
			cityMaster = new CityMaster();

			countryMaster.setCountryId(sessionStateManage.getCountryId());
			stateMaster.setStateId(getEmpStateId());
			districtMaster.setDistrictId(getEmpDistrictId());

			custEmp.setFsCompanyMaster(companyMaster);
			// custEmp.setFsCustomer(customer);
			// custEmp.setFsLanguageType(languageType);
			custEmp.setFsCountryMaster(countryMaster);
			custEmp.setFsStateMaster(stateMaster);
			custEmp.setFsDistrictMaster(districtMaster);

			if(getEmpCityId()!=null)
			{
				cityMaster.setCityId(getEmpCityId());
				custEmp.setFsCityMaster(cityMaster);
			}

			// custEmp.setOccupation(getOccupation());
			custEmp.setEmployerName(getEmployer());
			// custEmp.setFsEmploymentType(employmentType);
			custEmp.setDepartment(getDepartment());
			custEmp.setArea(getEmparea());
			custEmp.setBlock(getEmpInfoBlock());
			custEmp.setStreet(getEmpInfoStreet());
			custEmp.setOfficeTelephone(getOfficeTel());
			custEmp.setPostal(getPostalCode());

			// }

			custEmp.setCustEmpInfoId(getPkCustomerEmployeeId());
			if (getPkCustomerEmployeeId() != null) {
				custEmp.setCreatedBy(getCreatedByEmployee());
				custEmp.setCreationDate(getCreationDateEmployee());
				custEmp.setUpdatedBy(userName);
				//CR for created date based on application Country	
				//custEmp.setLastUpdated(new Date());
				custEmp.setLastUpdated(getCurrentTime());
			} else {
				custEmp.setCreatedBy(userName);
				//CR for created date based on application Country	
				//custEmp.setCreationDate(new Date());
				custEmp.setCreationDate(getCurrentTime());
			}

	 *//** Responsible to save *//*
			if (getEmploymentType().intValue() == getGeneralService()
					.getComponentId(EMPLOYMENTTYPE,
							sessionStateManage.getLanguageId())
					.getFsBizComponentData().getComponentDataId().intValue()) {
				if (getBooEmployed()) {
					getIcustomerRegistrationService()
							.saveCustomerEmploymentInfo(custEmp);
				}
			} else {
				getIcustomerRegistrationService().saveCustomerEmploymentInfo(
						custEmp);
			}

		} catch (NullPointerException npexp) {
			log.info("Null Pointer for customer Info" + npexp);
		} catch (Exception ioexp) {
			log.info("IO Exception for customer Info" + ioexp);
		}

	}
	  */
	/**
	 * Save Customer ID proof
	 * @throws ParseException 
	 */
	/*public void saveProofDetails() throws IOException, ParseException {

		SessionStateManage sessionStateManage = new SessionStateManage();

		customer = new Customer();
		customer.setCustomerId(getPkCustomerId());

		BizComponentData identityType = new BizComponentData();

		BizComponentData customerType = new BizComponentData();
		customerType.setComponentDataId(getGeneralService()
				.getComponentId(CUSTOMERTYPE,
						sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId());

		for (CreateProofTable createProofTable : createProofList) {
			// idType = new IdentityType();
			CustomerIdProof custProof = new CustomerIdProof();
			custProof.setFsCustomer(customer);
			custProof.setFsLanguageType(languageType);
			custProof.setFsBizComponentDataByCustomerTypeId(customerType);

			identityType.setComponentDataId(new BigDecimal(createProofTable
					.getId_type()));
			custProof.setFsBizComponentDataByIdentityTypeId(identityType);

			DocumentImg imgDoc = new DocumentImg();
			imgDoc.setImgId(createProofTable.getImageId());
			custProof.setFsDocumentImg(imgDoc);

			BizComponentData idFor = new BizComponentData();

			idFor.setComponentDataId(new BigDecimal(createProofTable
					.getId_for()));
			custProof.setFsBizComponentDataByIdentityFor(idFor);

			BizComponentData idType = new BizComponentData();
			idType.setComponentDataId(new BigDecimal(createProofTable
					.getId_type()));

			custProof.setIdentityInt(createProofTable.getId_number());

			custProof.setIdentityStatus("1");
			custProof.setApprovedDate(getCurrentTime());

			if (createProofTable.getChecked() != null) {
				if (createProofTable.getChecked()) {
					custProof.setApprovedBy(userName);
					//CR for created date based on application Country	
					//custProof.setApprovedDate(new Date());
					custProof.setApprovedDate(getCurrentTime());
				} else {
					custProof.setApprovedBy("0");
					//CR for created date based on application Country
					//custProof.setApprovedDate(new Date());
					custProof.setApprovedDate(getCurrentTime());
				}
			}

			Date expDate = new SimpleDateFormat("dd/MM/yyyy")
					.parse(createProofTable.getDate_expiary());

			custProof.setIdentityExpiryDate(expDate);
			// custProof.setCreatedBy(userName);
			// custProof.setCreationDate(new Date());

			custProof.setCustProofId(createProofTable.getCustomerIdProofId());
			if (createProofTable.getCustomerIdProofId() != null) {
				custProof.setUpdatedBy(userName);//CR for created date based on application Country	
				//custProof.setLastUpdatedDate(new Date());
				custProof.setLastUpdatedDate(getCurrentTime());
				custProof.setCreatedBy(createProofTable.getCreatedByIdProof());
				custProof.setCreationDate(createProofTable
						.getCreatedDateIdProof());
				//custProof.setImgUploadDate(new SimpleDateFormat("dd/MM/yyyy").parse(createProofTable.getImgDate()));

			} else {
				custProof.setCreatedBy(userName);
				//CR for created date based on application Country
				//custProof.setCreationDate(new Date());
				custProof.setCreationDate(getCurrentTime());
				//custProof.setImgUploadDate(new Date());
				//custProof.setImgUploadDate(getCurrentTime());

			}
			getIcustomerRegistrationService().saveCustomerIdProof(custProof);
		}

		for (CreateProofTable createProofTable : createProofListDelete) {
			// idType = new IdentityType();
			CustomerIdProof custProof = new CustomerIdProof();
			custProof.setFsCustomer(customer);
			custProof.setFsLanguageType(languageType);
			custProof.setFsBizComponentDataByCustomerTypeId(customerType);

			custProof.setFsBizComponentDataByIdentityTypeId(identityType);

			DocumentImg imgDoc = new DocumentImg();
			imgDoc.setImgId(createProofTable.getImageId());
			custProof.setFsDocumentImg(imgDoc);

			BizComponentData idFor = new BizComponentData();
			idFor.setComponentDataId(new BigDecimal(createProofTable
					.getId_for()));
			custProof.setFsBizComponentDataByIdentityFor(idFor);

			// custProof.setIdentityFor(createProofTable.getId_for());
			custProof.setIdentityInt(createProofTable.getId_number());
			custProof.setIdentityStatus("0");

			if (createProofTable.getChecked() != null) {
				if (createProofTable.getChecked()) {
					custProof.setApprovedBy(userName);
					//CR for created date based on application Country
					//custProof.setApprovedDate(new Date());
					custProof.setApprovedDate(getCurrentTime());
				} else {
					custProof.setApprovedBy("0");
					//CR for created date based on application Country
					//custProof.setApprovedDate(new Date());
					custProof.setApprovedDate(getCurrentTime());
				}
			}

			Date expDate = new SimpleDateFormat("dd/MM/yyyy")
					.parse(createProofTable.getDate_expiary());

			custProof.setIdentityExpiryDate(expDate);
			// custProof.setCreatedBy(userName);
			// custProof.setCreationDate(new Date());

			custProof.setCustProofId(createProofTable.getCustomerIdProofId());
			if (createProofTable.getCustomerIdProofId().intValue() != 0) {
				custProof.setUpdatedBy(userName);
				//CR for created date based on application Country
				//custProof.setLastUpdatedDate(new Date());
				custProof.setLastUpdatedDate(getCurrentTime());
				custProof.setCreatedBy(createProofTable.getCreatedByIdProof());
				custProof.setCreationDate(createProofTable
						.getCreatedDateIdProof());

			} else {
				custProof.setCreatedBy(userName);
				//CR for created date based on application Country
				//custProof.setCreationDate(new Date());
				custProof.setCreationDate(getCurrentTime());
			}
			getIcustomerRegistrationService().saveCustomerIdProof(custProof);
		}

	}*/

	// getBranchpageService().saveOrUpdateCustomerIdProof(custProof);
	// }

	/*
	 * method to fetch the record from db
	 */
	public String fetchCustomerInfo() throws ParseException {

		System.out.println("==============CALLED FETCH ===  ");

		// if(getIdType().equalsIgnoreCase("19")){
		String validity = generalService.getValidity(sessionStateManage.getCountryId(), new BigDecimal(getIdType()));
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, new Integer(validity));
		Date today90 = cal.getTime();
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
		String finalDate = form.format(today90);
		setExpDateCheck(finalDate);
		String finalsDate = form.format(new Date());
		setMinDob(finalsDate);

		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(new Date());
		//	cal1.add(Calendar.YEAR, -Constants.AGE_LIMIT);
		cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
		Date today18 = cal1.getTime();
		SimpleDateFormat form1 = new SimpleDateFormat("dd/MM/yyyy");
		String minDateFinal = form1.format(today18);
		setEffectiveMinDate(minDateFinal);
		// }else{
		/*
		 * Calendar cal = new GregorianCalendar(); cal.setTime(new Date());
		 * cal.add(Calendar.DAY_OF_MONTH, +0); Date today90 = cal.getTime();
		 * SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy"); String
		 * finalDate = form.format(today90); setExpDateCheck(finalDate); String
		 * finalsDate = form.format(new Date()); setMinDob(finalsDate);
		 * 
		 * }
		 */

		SessionStateManage sessionStateManage = new SessionStateManage();

		// try {
		getFetchContactTypeList();
		/*customerIdProofList = getIcustomerRegistrationService()
				.getCustomerIdProofCheck(new BigDecimal(getIdType()),
						getIdNumber(),sessionStateManage.getCountryId());
		System.out.println("==============customerIdProofList size FETCH ===  "
				+ customerIdProofList);*/


		BigDecimal identityTpeIds = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
				identityTpeIds = entry.getKey();
				break;
			}
		}

		BigDecimal idtypeCivilIdnew = getGeneralService().getComponentId(Constants.CIVIL_ID_NEW,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
		
		if(getIdType()!=null && idtypeCivilIdnew!=null && getIdType().equalsIgnoreCase(identityTpeIds.toString()) || getIdType().equalsIgnoreCase(idtypeCivilIdnew.toString())){
			customerIdProofList = getIcustomerRegistrationService().getCustomerIdProofCheck(identityTpeIds,getIdNumber(),session.getCountryId());

			if (customerIdProofList.size() == 0) {
				customerIdProofList = getIcustomerRegistrationService().getCustomerIdProofCheck(idtypeCivilIdnew,getIdNumber(),session.getCountryId());
			}
		}else{
			customerIdProofList = getIcustomerRegistrationService().getCustomerIdProofCheck(new BigDecimal(getIdType()),getIdNumber(),session.getCountryId());
		}

		if (customerIdProofList.size() > 0) {
			setBooNomineeFirst(false);
			setBooNomineeAfter(true);
			// getFetchIdType();
			setPkCustomerId(customerIdProofList.get(0).getFsCustomer().getCustomerId());
			System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOO======"+ customerIdProofList.get(0).getFsCustomer().getCustomerId());
			setChangeDobPass(true);
			setCustomerIdProof();
			setCustomerDetails();
			fillCountryList();
			fillStateList();
			fillDistrictList();
			fillCityList();
			setContactDetails();
			setDepenedentDetails();
			setEmployeementInfo();
			setSignaturePanelRender(true);
			setBooChangeDob(false);
			setNextUpdateRender(false);

		} else {

			setBooNomineeFirst(true);
			setBooNomineeAfter(false);
			setIdnumberProof(getIdNumber());
			setIdTypeproof(new BigDecimal(getIdType()));
			setReadOnlyIdNumber(true);
			setBooRemitterInfo(true);
			setBooIdDetail(false);
			setBooCustomerDetails(false);
			setBooContactDetails(false);
			setBooEmploymentDetails(false);
			setBooProof(false);
			setRenderVerifyReport(false);
			setNextUpdateRender(false);

		}
		/*
		 * } catch (Exception e) { log.info("This is for IDProof"+e); }
		 */
		checkPepLink();

		return "";


	}
	
	public List<CreateProofTable> dummiCustomerIdProofList = new ArrayList<CreateProofTable>();

	public List<CreateProofTable> getDummiCustomerIdProofList() {
		return dummiCustomerIdProofList;
	}

	public void setDummiCustomerIdProofList(
			List<CreateProofTable> dummiCustomerIdProofList) {
		this.dummiCustomerIdProofList = dummiCustomerIdProofList;
	}

	public void setCustomerIdProof() {

		// setPkCustomerId(customerIdProofList.get(0).getFsCustomer().getCustomerId());
		// nomineeRegistration.setNominatorId(getPkCustomerId(), "fromBranch");
		// customerIdProofList.clear();

		CreateProofTable createProofTable = null;
		customerIdProofList.clear();
		customerIdProofList = getIcustomerRegistrationService().getCustomerIdProofList(getPkCustomerId());
		getFetchContactTypeList();
		createProofList.clear();
		dummiCustomerIdProofList.clear();
		for (CustomerIdProof customerIdProof : customerIdProofList) {

			createProofTable = new CreateProofTable();
			
			createProofTable.setCustomerIdProofId(customerIdProof.getCustProofId());

			if(customerIdProof.getFsBizComponentDataByIdentityFor() != null){
				createProofTable.setIdFor(idForMap.get(customerIdProof.getFsBizComponentDataByIdentityFor().getComponentDataId()));
				createProofTable.setId_for(customerIdProof.getFsBizComponentDataByIdentityFor().getComponentDataId().toPlainString());
			}
			
			createProofTable.setId_number(customerIdProof.getIdentityInt());
			createProofTable.setIdType(idTypeMap.get(customerIdProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId()));
			if (customerIdProof.getIdentityExpiryDate() != null ){
				createProofTable.setDate_expiary(new SimpleDateFormat(DATE_FORMAT).format(customerIdProof.getIdentityExpiryDate()));
			}
			createProofTable.setId_type(customerIdProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId().toPlainString());
			createProofTable.setCustomerIdProofId(customerIdProof.getCustProofId());
			createProofTable.setCreatedByIdProof(customerIdProof.getCreatedBy());
			createProofTable.setCreatedDateIdProof(customerIdProof.getCreationDate());
			setBooVerifyAllRen(true);
			setBooidproofDatatable(true);
			setEnableScan(true);
			if(customerIdProof.getIdentityExpiryDate() != null){
				createProofList.add(createProofTable);
			}else{
				dummiCustomerIdProofList.add(createProofTable);
			}
			
		}
		
		if(createProofList.size() == 0){
			for (CustomerIdProof customerIdProof : customerIdProofList) {
				setCustomerIDProofId(customerIdProof.getCustProofId());
			}
		}

	}
	
	public Date activeDate;
	public String verificationToken;

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public void setCustomerDetails() {

		getArticleData();

		customerList = getIcustomerRegistrationService().getCustomerInfo(getPkCustomerId());

		if (customerList.size() != 0) {
			setNationality(customerList.get(0).getFsCountryMasterByNationality().getCountryId());

			// setLevel(customerList.get(0).getFsArticleDetails().getArticleDetailId());
			setFirstName(customerList.get(0).getFirstName());
			setFirstNamel(customerList.get(0).getFirstNameLocal());
			setMiddleName(customerList.get(0).getMiddleName());
			setMiddleNamel(customerList.get(0).getMiddleNameLocal());
			setShortName(customerList.get(0).getShortName());
			setShortNamel(customerList.get(0).getShortNameLocal());
			setLastName(customerList.get(0).getLastName());
			setLastNamel(customerList.get(0).getLastNameLocal());
			setEmail(customerList.get(0).getEmail());
			setTitle(customerList.get(0).getTitle());
			setMobile(customerList.get(0).getMobile());
			setGender(customerList.get(0).getGender());
			setEmosCustomer(customerList.get(0).getEmosCustomer());
			//setSignatureSpecimen(customerList.get(0).getSignatureSpecimen());
			setDigitalSignature(customerList.get(0).getSignatureSpecimen());
			setActiveInd(customerList.get(0).getActivatedInd());
			setActiveDate(customerList.get(0).getActivatedDate());
			//setVerificationToken(customerList.get(0).getVerificationTokenId() == null ? "" : customerList.get(0).getVerificationTokenId());
			if(customerList.get(0).getCustomerReference()!=null){
				setUpdateCustomerRefNo(customerList.get(0).getCustomerReference());
			}
			setCraetedDateforCustomer(new SimpleDateFormat("dd/MM/yyyy").format(customerList.get(0).getCreationDate()));
			if (sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {
				mapIdentityType = ruleEngine.getComponentData("Identity Type");
				BigDecimal identityTpeId = null;
				for (Map.Entry<BigDecimal, String> entry : mapIdentityType
						.entrySet()) {
					if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
						identityTpeId = entry.getKey();
						break;
					}
				}
				// System.out.println("==="+getSelectedIdType());
				// SessionStateManage sessionStateManage = new
				// SessionStateManage();
				if (getIdType() != null) {
					if (getIdType().equalsIgnoreCase(identityTpeId.toString())) {
						if(customerList.get(0).getDateOfBirth()!=null){
							setShowDob(new SimpleDateFormat("dd/MM/yyyy")
							.format(customerList.get(0).getDateOfBirth()));
						}else{
							setBooCivilId(false);
							setBooOtherId(true);
							setChangeDobPass(false);
						}
					} else {
						setDob(customerList.get(0).getDateOfBirth());
						setBooCivilId(false);
						setBooOtherId(true);
						setChangeDobPass(false);
					}
				}
			}

			setDob(customerList.get(0).getDateOfBirth());
			/*setDob(customerList.get(0).getDateOfBirth());*/
			setAlternativeEmail(customerList.get(0).getAlterEmailId());

			// Added by kani begin
			if(customerList.get(0).getIntroducedBy()!=null){
				setIntroduceBy(new BigDecimal(customerList.get(0).getIntroducedBy()));
			}

			fetchIntroducer();

			if(customerList.get(0).getMedicalInsuranceInd()!=null){
				if (customerList.get(0).getMedicalInsuranceInd().equalsIgnoreCase("Y")) {
					setMedicalInsuranceInd("Y");// Added by Nazish on 11-Feb 2015
				} else {
					setMedicalInsuranceInd("N");
				}
			}

			if(customerList.get(0).getPepsIndicator()!=null){
				if (customerList.get(0).getPepsIndicator().equalsIgnoreCase("Y")) {
					setPepsindicator("Y");// Added by Nazish on 11-Feb 2015
				} else {
					setPepsindicator("N");
				}
			}


			// Added by kani end
			setTitlel(customerList.get(0).getTitleLocal());
			// setCountryId(customerList.get(0).getFsCountryMasterByCountryId().getCountryId());

			setCreatedByCustomer(customerList.get(0).getCreatedBy());
			setCreationDateCustomer(customerList.get(0).getCreationDate());

			if (customerList.get(0).getFsArticleDetails()!= null){

				setArticle(customerList.get(0).getFsArticleDetails().getFsArticleMaster().getArticleId());
				generateLevel();
				setLevel(customerList.get(0).getFsArticleDetails().getArticleDetailId());
				generateIncomeRange();
				setIncomeRange(customerList.get(0).getFsIncomeRangeMaster().getIncomeRangeId());

				/*setDailyLimit(customerList.get(0).getDailyLimit());
				setWeeklyLimit(customerList.get(0).getWeeklyLimit());
				setMonthlyLimit(customerList.get(0).getMontlyLimit());
				setQuarterlyLimit(customerList.get(0).getQuaterlyLimit());
				setHalfyearly(customerList.get(0).getHalfYearly());
				setAnnualLimit(customerList.get(0).getAnnualLimit());*/
			}

			setAMLStatus(customerList.get(0).getAmlStatus());
			setNumberofhits(customerList.get(0).getNumberOfHits());

			//if (customerList.get(0).getActivatedInd().equalsIgnoreCase("0")) {
			if (customerList.get(0).getActivatedInd() != null && customerList.get(0).getActivatedInd().equalsIgnoreCase(Constants.CUST_INACTIVE_INDICATOR)) {

				setApproved(false);
			} else {
				setApproved(true);
			}
		}
	}

	// Get country list and store into local map object
	private void fillCountryList() {

		if (mapCountryList.size() == 0) {
			mapCountryList.clear();

			for (CountryMasterDesc countryMasterDesc : getGeneralService()
					.getCountryList(sessionStateManage.getLanguageId())) {
				mapCountryList.put(countryMasterDesc.getFsCountryMaster()
						.getCountryId(), countryMasterDesc.getCountryName());
			}

		}
	}

	// Get state list and store into local map object
	private void fillStateList() {

		if (mapStateList.size() == 0) {
			mapStateList.clear();
			for (StateMasterDesc stateMasterDesc : getGeneralService()
					.getStateList(sessionStateManage.getLanguageId())) {
				mapStateList.put(stateMasterDesc.getFsStateMaster()
						.getStateId(), stateMasterDesc.getStateName());
			}
		}
	}

	// Get district list and store into local map object
	private void fillDistrictList() {

		if (mapDistirictList.size() == 0) {
			mapDistirictList.clear();
			for (DistrictMasterDesc districtMasterDesc : getGeneralService()
					.getDistrictList(sessionStateManage.getLanguageId())) {
				mapDistirictList.put(districtMasterDesc.getFsDistrictMaster()
						.getDistrictId(), districtMasterDesc.getDistrict());
			}
		}
	}

	// Get city list and store into local map object
	private void fillCityList() {

		if (mapCityList.size() == 0) {
			mapCityList.clear();
			for (CityMasterDesc cityMasterDesc : getGeneralService()
					.getCityList(sessionStateManage.getLanguageId())) {
				mapCityList.put(cityMasterDesc.getFsCityMaster().getCityId(),
						cityMasterDesc.getCityName());
			}
		}
	}

	public void setContactDetails() {

		contactList.clear();
		contactDetailList.clear();
		contactDetailList.addAll(getIcustomerRegistrationService()
				.getCustomerContactDetails(getPkCustomerId()));

		if (contactDetailList.size() > 0) {
			// contactList.clear();
			for (ContactDetail cdetail : contactDetailList) {
				/*	BigDecimal cityId = null;
				if(cdetail.getFsCityMaster()!=null){
					cityId = cdetail.getFsCityMaster().getCityId();
				}
				 */				AddContactDetailBean addContactDetailBean = new AddContactDetailBean();
				 if(cdetail.getFsBizComponentDataByContactTypeId()!=null){
					 getFetchContactTypeList();
					 addContactDetailBean.setContType(mapContactTypeList.get(cdetail.getFsBizComponentDataByContactTypeId().getComponentDataId()));
					 addContactDetailBean.setContactTypeId(cdetail.getFsBizComponentDataByContactTypeId().getComponentDataId());
				 }
				 if(cdetail.getFsCountryMaster()!=null){
					 fillCountryList();
					 addContactDetailBean.setCountry(mapCountryList.get(cdetail.getFsCountryMaster().getCountryId()));
					 addContactDetailBean.setCountryId(cdetail.getFsCountryMaster().getCountryId());
				 }
				 if(cdetail.getFsStateMaster()!=null){
					 fillStateList();
					 addContactDetailBean.setState( mapStateList.get(cdetail.getFsStateMaster().getStateId()));
					 addContactDetailBean.setStateId(cdetail.getFsStateMaster().getStateId());
				 }
				 if(cdetail.getFsDistrictMaster()!=null){
					 fillDistrictList();
					 addContactDetailBean.setDist(mapDistirictList.get(cdetail.getFsDistrictMaster().getDistrictId()));
					 addContactDetailBean.setDistrictId(cdetail.getFsDistrictMaster().getDistrictId());
				 }
				 if(cdetail.getFsCityMaster()!=null){
					 fillCityList();
					 addContactDetailBean.setCity(mapCityList.get(cdetail.getFsCityMaster().getCityId()));
					 addContactDetailBean.setCityId(cdetail.getFsCityMaster().getCityId());
				 }
				 //addContactDetailBean.setArea(cdetail.getArea());
				 addContactDetailBean.setFlat(cdetail.getFlat());
				 addContactDetailBean.setBuildingNo(cdetail.getBuildingNo());
				 addContactDetailBean.setBlock(cdetail.getBlock());
				 addContactDetailBean.setCreatedByContact(cdetail.getCreatedBy());
				 addContactDetailBean.setCreatedDateContact(cdetail.getCreationDate());
				 addContactDetailBean.setContactDetailsId(cdetail.getContactDetailId());
				 addContactDetailBean.setTel(cdetail.getTelephone());
				 addContactDetailBean.setStreet(cdetail.getStreet());
				 //added by nazish cr 14-JULY-2015
				 addContactDetailBean.setMobileContact(cdetail.getMobile());
				 addContactDetailBean.setTelephoneCode(cdetail.getTelephoneCode());
				 contactList.add(addContactDetailBean);


				 /*AddContactDetailBean addContactDetailBean = new AddContactDetailBean(
						mapContactTypeList.get(cdetail
								.getFsBizComponentDataByContactTypeId()
								.getComponentDataId()), cdetail.getArea(),
						mapCountryList.get(cdetail.getFsCountryMaster()
								.getCountryId()), cdetail.getStreet(),
						cdetail.getBlock(), cdetail.getTelephone(),
						cdetail.getFlat(), mapStateList.get(cdetail
								.getFsStateMaster().getStateId()),
						mapDistirictList.get(cdetail.getFsDistrictMaster()
								.getDistrictId()), mapCityList.get(cityId), false, false,
						cdetail.getFsBizComponentDataByContactTypeId()
								.getComponentDataId(), cdetail
								.getFsCountryMaster().getCountryId(), cdetail
								.getFsStateMaster().getStateId(), cdetail
								.getFsDistrictMaster().getDistrictId(), cityId,
						cdetail.getContactDetailId(), cdetail.getBuildingNo());
				addContactDetailBean
						.setCreatedByContact(cdetail.getCreatedBy());
				addContactDetailBean.setCreatedDateContact(cdetail
						.getCreationDate());
				addContactDetailBean.setContactDetailsId(cdetail.getContactDetailId());
				contactList.add(addContactDetailBean);
				  */				setContactDataTable(true);
				  setBooContactDetailsButtonPanel(true);
			}

		}

	}

	public void populateState() {
		sessionStateManage = new SessionStateManage();
		List<StateMasterDesc> statemasterDesc = getGeneralService()
				.getStateList(sessionStateManage.getLanguageId(),
						getEmpCountryId());
		setLstEmpStateList(statemasterDesc);
	}

	public void populateDistrict() {
		sessionStateManage = new SessionStateManage();
		List<DistrictMasterDesc> districtmasterDesc = getGeneralService()
				.getDistrictList(sessionStateManage.getLanguageId(),
						getEmpCountryId(), getEmpStateId());
		setLstEmpDistrictList(districtmasterDesc);
	}

	public void populateCity() {
		sessionStateManage = new SessionStateManage();
		List<CityMasterDesc> citymasterDesc = getGeneralService().getCityList(
				sessionStateManage.getLanguageId(), getEmpCountryId(),
				getEmpStateId(), getEmpDistrictId());
		setLstEmpCityList(citymasterDesc);
	}

	public void setEmployeementInfo() {

		setOccupation(null);
		setEmployer("");
		setEmpInfoBlock("");
		setEmparea("");
		setEmpCountryId(null);
		setEmpStateId(null);
		setEmpCityId(null);
		setEmpDistrictId(null);
		setEmpInfoStreet("");
		setOfficeTel("");
		setPostalCode("");
		setDepartment("");
		customerEmploymentInfoList.clear();
		customerEmploymentInfoList.addAll(getIcustomerRegistrationService()
				.getCustomerEmploymentInfo(getPkCustomerId()));
		// sessionStateManage = new SessionStateManage();

		if (customerEmploymentInfoList.size() > 0) {
			setEmploymentType(customerEmploymentInfoList.get(0)
					.getFsBizComponentDataByEmploymentTypeId()
					.getComponentDataId());
			// setEmpInfoEmploymentTypeId(customerEmploymentInfoList.get(0).getCustEmpInfoId());
			setOccupation(customerEmploymentInfoList.get(0)
					.getFsBizComponentDataByOccupationId().getComponentDataId());
			if(getAdditinal2()!=null){
				setEmployer(getAdditinal2());
			}else{
			setEmployer(customerEmploymentInfoList.get(0).getEmployerName());
			}
			setEmpCountryId(customerEmploymentInfoList.get(0)
					.getFsCountryMaster().getCountryId());
			populateState();
			setEmpStateId(customerEmploymentInfoList.get(0).getFsStateMaster()
					.getStateId());
			populateDistrict();
			setEmpDistrictId(customerEmploymentInfoList.get(0)
					.getFsDistrictMaster().getDistrictId());
			populateCity();
			if(customerEmploymentInfoList.get(0).getFsCityMaster()!=null){
				setEmpCityId(customerEmploymentInfoList.get(0).getFsCityMaster()
						.getCityId());
			}
			setEmpInfoStreet(customerEmploymentInfoList.get(0).getStreet());
			setPostalCode(customerEmploymentInfoList.get(0).getPostal());
			setEmpInfoBlock(customerEmploymentInfoList.get(0).getBlock());
			setEmparea(customerEmploymentInfoList.get(0).getArea());
			setOfficeTel(customerEmploymentInfoList.get(0).getOfficeTelephone());
			setDepartment(customerEmploymentInfoList.get(0).getDepartment());
			setCreatedByEmployee(customerEmploymentInfoList.get(0)
					.getCreatedBy());
			setCreationDateEmployee(customerEmploymentInfoList.get(0)
					.getCreationDate());
			setPkCustomerEmployeeId(customerEmploymentInfoList.get(0)
					.getCustEmpInfoId());

			/**
			 * This boolean is responsible to render the panel depending upon
			 * Employment Type
			 */
			if (getEmploymentType().intValue() == getGeneralService()
					.getComponentId(Constants.EMPLOYMENTTYPE,
							sessionStateManage.getLanguageId())
							.getFsBizComponentData().getComponentDataId().intValue()) {
				setBooEmploymentPanel(false);
			} else {
				setBooEmploymentPanel(true);
			}
			setBooEmployed(true);
		} else {
			setEmploymentType(new BigDecimal(getGeneralService()
					.getComponentId(Constants.EMPLOYMENTTYPE,
							sessionStateManage.getLanguageId())
							.getFsBizComponentData().getComponentDataId().intValue()));
			/**
			 * This boolean is responsible to render the panel depending upon
			 * Employment Type
			 */
			setBooEmploymentPanel(false);
			setBooEmployed(false);

		}
	}

	public void clear() {

		clearFirst();
		clearRemitterInfo();
		clearContactDetail();
		clearEmploymentInfo();
		clearProofInfo();
		clearSmartCardInfo();

	}

	public void clearFirst() {

		setIdType("");
		setIdNumber("");
		setSelectType("");
		setLevel(null);
		setArticle(null);
		setBooIdTypeCheck(false);

	}

	/*
	 * method is responsible foe Clear Remitter Information
	 */
	public void clearRemitterInfo() {

		setTitle("");
		setFirstName("");
		setLastName("");
		setMiddleName("");
		setShortName("");
		setTitlel("");
		setFirstNamel("");
		setLastNamel("");
		setMiddleNamel("");
		setShortNamel("");
		setNationality(null);
		setGender("");
		setMobile("");
		setEmail("");
		setAlternativeEmail("");
		setDob(null);
		setAMLStatus("");
		setNumberofhits(null);
		setDigitalSignature(null);
		// start by subramanian 10/02/2015
		setIntroducedBy(null);
		setMedicalInsuranceInd(null);
		setPepsindicator(null);
		setMobileNoFetch(null);
		// End by subramanian 10/02/2015

	}

	/*
	 * method is responsible foe Clear Contact Information
	 */
	public void clearContactDetail() {

		setContactTypeId(null);
		setLocalArea("");
		setCountryId(null);
		setStreet("");
		setStateId(null);
		setTelephone("");
		setDistrictId(null);
		setFlat("");
		setCityId(null);
		setBlock("");
		setBuildingNo(null);
		setBooContactDetailsDuplicate(false);
		setMobileContact(null);
		setTelephoneCode(null);
		setPlusSign(null);
		setMsgMobileOrTel(false);
		// contactList.clear();

	}

	/*
	 * method is responsible foe Clear Employment Information
	 */
	public void clearEmploymentInfo() {

		setArticle(null);
		setLevel(null);
		setEmploymentType(null);
		setOccupation(null);
		setEmployer("");
		setEmpInfoBlock("");
		setEmparea("");
		setEmpCountryId(null);
		setEmpStateId(null);
		setEmpCityId(null);
		setEmpDistrictId(null);
		setEmpInfoStreet("");
		setOfficeTel("");
		setPostalCode("");
		setDepartment("");
		setIncomeRange(null);
		setDailyLimit(null);
		setWeeklyLimit(null);
		setMonthlyLimit(null);
		setHalfyearly(null);
		setAnnualLimit(null);
		setQuarterlyLimit(null);
	}

	/*
	 * method is responsible foe Clear Customer Proof Information
	 */
	public void clearProofInfo() {

		setIdTypeproof(null);

		setIdnumberProof("");
		//setDateExp(null);
		setIdFor(null);
		// createProofList.clear();
		// setBooidproofDatatable(false);
		setRenderIdProofVissibility(false);

	}

	/*
	 * method is responsible to add contact in a dataTable
	 */
	public void addContactDetailsDataTable() {

		setBooContactDetailsDuplicate(false);
		for (AddContactDetailBean contactDetails : contactList) {
			if (getContactTypeId().intValue() == contactDetails
					.getContactTypeId().intValue()
					&& getCountryId().intValue() == contactDetails.getCountryId().intValue()
					&& getStateId().intValue() == contactDetails.getStateId().intValue()
					&& getDistrictId().intValue() == contactDetails.getDistrictId().intValue()
					//&& getCityId().intValue() == contactDetails.getCityId().intValue()
					&& getStreet().equalsIgnoreCase(contactDetails.getStreet())
					&& getBlock().equalsIgnoreCase(contactDetails.getBlock())
					&& getFlat().equalsIgnoreCase(contactDetails.getFlat())) {
				setBooContactDetailsDuplicate(true);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('duplicateDetails').show();");
				break;
			}
		}

		if (!getBooContactDetailsDuplicate()) {
			if(getMobileContact().length()!= 0 || getTelephone().length()!=0){
			
			AddContactDetailBean addContact = new AddContactDetailBean();
			addContact.setContactTypeId(this.contactTypeId);
			addContact.setContType(mapContactTypeList.get(this.contactTypeId));
			addContact.setCountryId(this.countryId);
			addContact.setCountry(mapCountryList.get(this.countryId));
			// addContact.setCountry(this.countryName);;
			addContact.setStateId(this.stateId);
			addContact.setState(mapStateList.get(this.stateId));
			addContact.setDistrictId(this.districtId);
			addContact.setDist(mapDistirictList.get(this.districtId));
			addContact.setCityId(this.cityId);
			addContact.setCity(mapCityList.get(this.cityId));
			//addContact.setArea(this.localArea);
			addContact.setStreet(this.street);
			addContact.setFlat(this.flat);
			
			addContact.setBlock(this.block);
			addContact.setContactDetailsId(new BigDecimal(0));
			addContact.setBuildingNo(this.buildingNo);
			addContact.setContactDetailsId(getPkCustomerContactDetails());
			if(getBooCheckMobileContact()){
			addContact.setMobileContact(this.getMobileContact());
			addContact.setTelephoneCode(this.getTelephoneCode());
			}
			
			if(getBooCheckTelContact()){
				addContact.setTel(this.telephone);
				addContact.setTelephoneCode(this.getTelephoneCode());
			}
			contactList.add(addContact);
			if (getContactTypeId().intValue() == getGeneralService().getComponentId(Constants.RESIDENCE,sessionStateManage.getLanguageId())
					.getFsBizComponentData().getComponentDataId().intValue()) {//PK
				setRendercontactTypeId(false);

			} else {
				setRendercontactTypeId(false);
			}
			/*if (getContactTypeId().intValue() == Constants.RESIDENT) {
				setRendercontactTypeId(false);

			} else {
				setRendercontactTypeId(false);
			}*/
			setContactTypeId(null);
			//setCountryId(null);
			setStateId(null);
			setDistrictId(null);
			setCityId(null);
			setLocalArea("");
			setStreet("");
			setBlock("");
			setTelephone(null);
			setFlat("");
			setBuildingNo(null);
			setTelephoneCode(null);
			setMobileContact(null);
			setPlusSign(null);
			setContactDataTable(true);
			setBooContactDetailsButtonPanel(true);
			setContactlistcheck(false);
			setRenderHideCountryId(false);
			setPkCustomerContactDetails(null);
			setMsgMobileOrTel(false);
			setRenContactMobile(false);
			setRenContactTel(false);
			setBooCheckMobileContact(false);
			setBooCheckTelContact(false);

			lstState.clear();
			lstDistrict.clear();
			lstCity.clear();
			}else{
				setMsgMobileOrTel(true);
			}
		}
	}

	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException,
	SerialException, SQLException {
		setFile(event.getFile());
		setImageUploadCheck(true);
		setInputStream(event.getFile().getInputstream());

		DocumentImg image = new DocumentImg();
		image.setImage(storeImage(inputStream));
		image.setUploadDate(new Date());
		image.setImageName(event.getFile().getFileName());
		image.setImgStatus("1");
		image.setUploadDate(new Date());
		getBranchpageService().saveImage(image);
		setPkimage(image.getImgId());
	}

	public void addRows() {

		boolean dublicate = true;
		setRenderIdProofVissibility(false);

		try {

			for (CreateProofTable addCustomerIdProofBean : createProofList) {

				if (getIdFor().compareTo(
						new BigDecimal(addCustomerIdProofBean.getId_for())) == 0) {
					if (getIdnumberProof().equalsIgnoreCase(
							addCustomerIdProofBean.getId_number())) {
						if (getIdTypeproof().equals(
								new BigDecimal(addCustomerIdProofBean
										.getId_type()))) {

							if (getDateExp().after(
									new SimpleDateFormat("dd/MM/yy")
									.parse(addCustomerIdProofBean
											.getDate_expiary()))) {
								if(new Date().before(new SimpleDateFormat("dd/MM/yy").parse(addCustomerIdProofBean.getDate_expiary()))){
									dublicate = false;
									break;
								}else{
									dublicate = true;
								}

							} else {
								dublicate = false;
								break;
							}
						}else {
							dublicate = true;
						}

					} else {
						dublicate = true;
					}
				} else {
					dublicate = true;
				}
			}

			if (dublicate) {
				if (!getImageUploadCheck()) {
					setUploadFileVisibility(true);
				} else if (getBooIdTypeCheck().equals(true)) {
					setBooIdTypeCheck(true);
				} else {

					String fileName = getFile().getFileName();
					if (fileName.contains(".doc") || fileName.contains(".docx")
							|| fileName.contains(".jpg")
							|| fileName.contains(".jpeg")
							|| fileName.contains(".png")
							|| fileName.contains(".pdf")) {

						SimpleDateFormat dateformat = new SimpleDateFormat(
								"dd/MM/yyyy");

						String df = dateformat.format(getDateExp());

						CreateProofTable createProofTable = new CreateProofTable(this.idFor.toPlainString(),this.idTypeproof.toPlainString(),this.idnumberProof, df, "insert", 0,idForMap.get(this.idFor),idTypeMap.get(this.idTypeproof));
						createProofTable.setCustomerIdProofId(null);

						createProofTable.setImgDate(dateformat.format(new Date()));
						createProofList.add(createProofTable);

						if (createProofList.size() > 1) {

							setBooVerifyAllRen(true);

						} else {

							setBooVerifyAllRen(false);

						}
						setBooidproofDatatable(true);

						// new
						setBooRenderFinalSave(false);
						//

						setIdListcheck(false);

						// setExpDate(null);
						setDateExp(null);
						setIdTypeproof(null);
						setIdnumberProof("");
						setIdFor(null);
						setImageUploadCheck(false);
						setUploadFileVisibility(false);
						setBooIdTypeCheck(false);
						setRenderSignature(true);
						setNextUpdateRender(false);
					} else {
						setUploadFileVisibility(true);
					}
				}
			} else {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('duplicateproof').show();");
				setRenderIdProofVissibility(false);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeIdProof(CreateProofTable proofTable) {
		createProofList.remove(proofTable);
		createProofListDelete.add(proofTable);
		setRenderIdProofVissibility(false);

		if (createProofList.size() == 0) {
			setBooRenderFinalSave(false);
			setBooidproofDatatable(false);
			setBooVerifyAllRen(false);
			setApproved(false);
		}
	}

	public void removeContactDetail(AddContactDetailBean addContact) {

		if (addContact.getContactDetailsId() != null) {
			//	contactListDelete.add(addContact);
			icustomerRegistrationService.deleteCustContactDetails(addContact.getContactDetailsId(), session.getUserName());
			contactList.remove(addContact);
		}else{
			contactList.remove(addContact);
		}
		if(contactList.size()>0){
			setContactDataTable(true);
			setBooContactDetailsButtonPanel(true);
		}else{
			setContactDataTable(false);
			setBooContactDetailsButtonPanel(false);
		}

	}

	public StreamedContent downloadFile(BigDecimal imageId) {

		StreamedContent image = null;
		try {

			image = getImage(imageId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	public StreamedContent getImage(BigDecimal imageId) {
		try {
			if (imageId.toPlainString().length() > 0 && imageId != null) {
				Blob blob = getBranchpageService().getImage(imageId).get(0)
						.getImage();
				String imageNameWithExtention = getBranchpageService()
						.getImage(imageId).get(0).getImageName();
				String imageExtention = imageNameWithExtention
						.substring(imageNameWithExtention.lastIndexOf("."));
				String imagename = imageNameWithExtention.substring(0,
						imageNameWithExtention.lastIndexOf(".") - 1);

				int blobLength = (int) blob.length();
				byte[] blobAsBytes = blob.getBytes(1, blobLength);
				InputStream stream = new ByteArrayInputStream(blobAsBytes);
				downloadFile = new DefaultStreamedContent(stream, "image/jpg",
						imagename + imageExtention);
			}
		} catch (Exception e) {
			log.info("This for Get Image" + e);
		}
		return downloadFile;
	}

	/*
	 * public void getNominee(){
	 * 
	 * if(getNomineeApprove()){
	 * 
	 * setBoonomeneeCheck(true); }else{
	 * 
	 * 
	 * setBoonomeneeCheck(false);
	 * 
	 * } }
	 */
	public boolean getNomineeApprove() {
		return nomineeApprove;
	}

	public void setNomineeApprove(boolean nomineeApprove) {
		this.nomineeApprove = nomineeApprove;
	}

	private BigDecimal article = null;
	private BigDecimal level = null;
	private List<ArticleDetails> lstLevel = new ArrayList<ArticleDetails>();
	private BigDecimal customerIdToNominee = null;
	private Boolean nominee = false;



	public NomineeRegistration getNomineeRegistration() {
		return nomineeRegistration;
	}

	public void setNomineeRegistration(NomineeRegistration nomineeRegistration) {
		this.nomineeRegistration = nomineeRegistration;
	}

	public BigDecimal getArticle() {
		return article;
	}

	public void setArticle(BigDecimal article) {
		this.article = article;
	}

	public BigDecimal getLevel() {
		return level;
	}

	public void setLevel(BigDecimal level) {
		this.level = level;
	}

	public List<ArticleDetails> getLevelData() {
		return lstLevel;
	}

	public BigDecimal getCustomerIdToNominee() {
		return customerIdToNominee;
	}

	public void setCustomerIdToNominee(BigDecimal customerIdToNominee) {
		this.customerIdToNominee = customerIdToNominee;
	}

	/**
	 * This method will call on on change of Article
	 */
	/*public void generateLevel() {
		lstLevel = getBranchpageService().getLevelData(getArticle());
	}

	 */
	public void generateLevel() {
		enableDependent();
		lstLevel = getBranchpageService().getLevelData(getArticle(),sessionStateManage.getLanguageId());
	}

	public List<ArticleMasterDesc> getArticleData() {

		List<ArticleMasterDesc> lstArticles = getBranchpageService().getArtilces(sessionStateManage.getCountryId(),sessionStateManage.getLanguageId());


		/*	List<ArticleMaster> lstArticles = getBranchpageService().getArtilces(
				session.getCountryId());*/
		return lstArticles;
	}

	/*public List<ArticleMaster> getArticleData() {
		List<ArticleMaster> lstArticles = getBranchpageService().getArtilces(
				session.getCountryId());
		return lstArticles;
	}*/

	public Boolean getNominee() {
		return nominee;
	}

	public void setNominee(Boolean nominee) {

		this.nominee = nominee;
	}

	public void ShowFromSearchPage(String civil_id, String id_type) {
		// setBooIdTypeCheck(false);
		setIdType(id_type);
		setIdNumber(civil_id);
		setSelectType(getGeneralService()
				.getComponentId(Constants.METHODTYPE, sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId().toPlainString());
		setBooManual(true);
		setBooIdDetail(true);
		setBooRemitterInfo(false);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooEmploymentDetails(false);
		setBooProof(false);
		setSignaturePanelRender(false);
		setRenderFinal(false);
		setRenderVerifyReport(false);
		setNextUpdateRender(false);
	}

	public void resetValues()  {

		setBooIdDetail(true);
		setBooRemitterInfo(false);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooEmploymentDetails(false);
		setBooProof(false);
		clear();
		setContactlistcheck(false);
		setProoflistcheck(false);
		setBooManual(false);
		setBooSmartcard(false);
		setBooidproofDatatable(false);
		setRenderIdProofVissibility(false);
		contactList.clear();
		createProofList.clear();
		setApproved(false);
		setNominee(false);
		setBooContactDetailsDuplicate(false);
		setRenderverifiedTokenNumber(false);
		setRenderSignature(false);
		setBooVerifyAllRen(false);
		setRenderSavebutton(false);
		setRenderverication(false);
		setSignaturePanelRender(false);
		setRenderFinal(false);
		setRenderVerifyReport(false);
		setRenderUpdate(false);
		setVerifyToken(null);
		setDigitalSignature(null);
		setSuccessPanel(false);
		setUpdatePanel(false);
		setBooRenderFinalSave(false);
		setMinagevalidation(false);
		setNextUpdateRender(false);
		setRenderIntroducePanel(false);
		setBooManualGo(false);
		setBoofirstPanel(false);
		setBahrainCardPanel(false);
		//setEnableCheckId(true);


		try {
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect("../registration/customerregistrationbranch.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
		}
	}

	public String getShowDob() {
		return showDob;
	}

	public void setShowDob(String showDob) {
		this.showDob = showDob;
	}

	public void changeGender() {
		changeLocalTitle();

		Map<BigDecimal, String> mapGender = ruleEngine
				.getComponentData("Gender");
		Map<BigDecimal, String> mapTitle = ruleEngine.getComponentData("Title");

		System.out.println(mapGender);
		System.out.println(mapTitle);

		if (mapTitle.get(new BigDecimal(title)) != null) {if (mapTitle.get(new BigDecimal(title)).trim()
				.equalsIgnoreCase("Mr.")) {
			for (BigDecimal key : mapGender.keySet()) {
				String value = mapGender.get(key);
				if (mapGender.get(key).equalsIgnoreCase("male")) {
					gender = key.toPlainString();
					gender = value;
				}
			}
		} else if (mapTitle.get(new BigDecimal(title)).trim()
				.equalsIgnoreCase("Ms.")) {
			for (BigDecimal key : mapGender.keySet()) {
				String value = mapGender.get(key);
				if (mapGender.get(key).equalsIgnoreCase("female")) {
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
							.equalsIgnoreCase(getGeneralService().getComponentId(Constants.LOCAL_TITLE_FOR_MR, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString())) {
						String value = eleEntry.getValue();
						// titlel =eleEntry.getKey().toPlainString();
						titlel = value;

					}
				}
			} else if (mapTitle.get(new BigDecimal(title)).trim()
					.equalsIgnoreCase("Ms.")) {
				for (Entry<BigDecimal, String> eleEntry : mapLocalTitle
						.entrySet()) {
					if (eleEntry.getKey().toString()
							.equalsIgnoreCase(getGeneralService().getComponentId(Constants.LOCAL_TITLE_FOR_MRS, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString())) {
						String value = eleEntry.getValue();
						// titlel =eleEntry.getKey().toPlainString();
						titlel = value;

					}
				}
			}
		}
	}

	public String amlCheck(String name) {
		List<Amlstatus> data = getBranchpageService().amlCheck(name);

		if (data.size() > 0) {
			if (data.get(0).getRemStatus().equalsIgnoreCase("pass")) {
				return "PASS";
			} else {
				return "AML STATUS Failed";
			}
		} else {
			return "NOT FOUND";
		}

	}

	public Boolean getBooNomineeFirst() {
		return booNomineeFirst;
	}

	public void setBooNomineeFirst(Boolean booNomineeFirst) {
		this.booNomineeFirst = booNomineeFirst;
	}

	public Boolean getBooNomineeAfter() {
		return booNomineeAfter;
	}

	public void setBooNomineeAfter(Boolean booNomineeAfter) {
		this.booNomineeAfter = booNomineeAfter;
	}

	// -----------------------New CR Date 29-10-2014

	private String successMessage = null;
	private Boolean booChangeDob = false;
	private Boolean renderDobChangeMessage = false;

	public String getSuccessMessage() {
		return getGeneralService().getMessage(
				sessionStateManage.getCountryId(),
				sessionStateManage.getLanguageId());
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	private String countryName = null;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Boolean getBooChangeDob() {
		return booChangeDob;
	}

	public void setBooChangeDob(Boolean booChangeDob) {
		this.booChangeDob = booChangeDob;
	}

	public Boolean getRenderDobChangeMessage() {
		return renderDobChangeMessage;
	}

	public void setRenderDobChangeMessage(Boolean renderDobChangeMessage) {
		this.renderDobChangeMessage = renderDobChangeMessage;
	}

	public Boolean mainSave() {
		
		boolean returnCheck = false;

		try {

			String s = "";
			System.out.println(s);
			int i = 0;
			int j=1;
			// boolean dobDocPresent = false;
			if (getSelectType().equalsIgnoreCase(getGeneralService().getComponentId(Constants.METHODTYPE,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toPlainString())) {

				if (createProofList.size() != 0) {

					for (CreateProofTable createProofTable : createProofList) {
						if (getIdType().equalsIgnoreCase(createProofTable.getId_type())) {
							i = 1;
							break;
						} else {
							i = 0;
						}
					}

					if (i == 1) {
						if (getBooChangeDob()) {
							for (CreateProofTable data : createProofList) {
								j=1;
								if (data.getId_for().equalsIgnoreCase(getGeneralService().getComponentId(Constants.CHANGE_DOB,sessionStateManage.getLanguageId())
										.getFsBizComponentData().getComponentDataId().toString())) {

									Boolean checkSignature = checkingSignature();

									if(checkSignature){
										returnCheck = true;
										//saveCustomerInformation();
										/*setBooRenderFinalSave(false);
										setBooVerifyAllRen(false);
										setSignaturePanelRender(false);*/
									}else{
										returnCheck = false;
										RequestContext.getCurrentInstance().execute("signatureMandatory.show();");
									}

									break;

								} else {
									j=0;
								}
							}

							if(j==0){
								returnCheck = false;
								setSuccessPanel(false);
								setBooRenderFinalSave(true);
								RequestContext.getCurrentInstance().execute("PF('dobchangemsg').show();");
							}
						} else {
							Boolean checkSig = checkingSignature();

							if(checkSig){
								returnCheck = true;
								//saveCustomerInformation();
								/*setBooRenderFinalSave(false);
								setBooVerifyAllRen(false);
								setSignaturePanelRender(false);*/
								//setSuccessPanel(true);
							}else{
								returnCheck = false;
								RequestContext.getCurrentInstance().execute("signatureMandatory.show();");
							}
						}
					} else {
						returnCheck = false;
						setSuccessPanel(false);
						setBooRenderFinalSave(true);
						RequestContext.getCurrentInstance().execute("PF('idtypecheck').show();");
					}

				}

			} else {
				//saveSmartCardInfoToCutomer();
				returnCheck = true;
				setBooRenderFinalSave(false);
				// setSuccessPanel(true);
			}

			pepReportList.clear();
			pepreportGeneration();

			if(getPepsindicator()==null){
				setBooPepDescriptionReport(false);
				setBooPepDescriptionUpdateReport(false);
			}else if(getPepsindicator().equalsIgnoreCase("N")){
				setBooPepDescriptionReport(false);
				setBooPepDescriptionUpdateReport(false);
			}else if(getPepsindicator().equalsIgnoreCase("Y") && getDisablePipsDropdown()!=true){
				setBooPepDescriptionReport(true);
				setBooPepDescriptionUpdateReport(true);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return returnCheck;
	}

	private Boolean disablePipsDropdown=false;


	private List<Customer> custlist = new ArrayList<Customer>();
	private String pepsReportStatus = null;


	public void checkPepLink(){
		List<CustomerIdProof>  customerId=generalService.getCustomerIdBasedOnCivilId(getIdNumber());
		if(customerId.size()!=0){
			custlist = getIcustomerRegistrationService().getVerificationToken(customerId.get(0).getFsCustomer().getCustomerId());
			if (custlist.size() != 0) {
				pepsReportStatus = custlist.get(0).getPepsIndicator();
				if(pepsReportStatus!=null){
					if(pepsReportStatus.equalsIgnoreCase("y")){
						setDisablePipsDropdown(true);
						setBooPepDescriptionReport(false);
						setBooPepDescriptionUpdateReport(false);
					}else{
						setDisablePipsDropdown(false);
					}
				}
			}else{
				setDisablePipsDropdown(false);
			}
		}
	}

	public Boolean getDisablePipsDropdown() {
		return disablePipsDropdown;
	}

	public void setDisablePipsDropdown(Boolean disablePipsDropdown) {
		this.disablePipsDropdown = disablePipsDropdown;
	}

	/*private int indicatorvalue=session.getCompanyId().intValue();*/
	private BigDecimal incomeRange = null;
	private List<IncomeRangeMaster> lstIncomeRange = new ArrayList<IncomeRangeMaster>();

	public BigDecimal getIncomeRange() {
		return incomeRange;
	}

	public void setIncomeRange(BigDecimal incomeRange) {
		this.incomeRange = incomeRange;
	}

	public List<IncomeRangeMaster> getLstIncomeRange() {
		return lstIncomeRange;
	}

	public void setLstIncomeRange(List<IncomeRangeMaster> lstIncomeRange) {
		this.lstIncomeRange = lstIncomeRange;
	}

	public void generateIncomeRange() {

		lstIncomeRange = getGeneralService().getIncomeRange(
				sessionStateManage.getCountryId(), getLevel());
	}

	public void enableDob() {

		if (getBooChangeDob()) {
			setBooCivilId(false);
			setBooOtherId(true);
			setDob(null);
			// setBooReadonly(false);
		} else {
			setBooCivilId(true);
			setBooOtherId(false);

		}

	}

	public Boolean getBooReadonly() {
		return booReadonly;
	}

	public void setBooReadonly(Boolean booReadonly) {
		this.booReadonly = booReadonly;
	}

	public Boolean getChangeDobPass() {
		return changeDobPass;
	}

	public void setChangeDobPass(Boolean changeDobPass) {
		this.changeDobPass = changeDobPass;
	}

	private Boolean booReadonly = true;

	private Boolean changeDobPass = false;

	private Boolean booMobilecheck = false;

	private Boolean booEmailCheck = false;

	public Boolean getBooMobilecheck() {
		return booMobilecheck;
	}

	public void setBooMobilecheck(Boolean booMobilecheck) {
		this.booMobilecheck = booMobilecheck;
	}

	public Boolean getBooEmailCheck() {
		return booEmailCheck;
	}

	public void setBooEmailCheck(Boolean booEmailCheck) {
		this.booEmailCheck = booEmailCheck;
	}

	public void checkMobile() {

		setBooMobilecheck(false);
		setErrorMsg(null);
		List<Customer> matchMobile = new ArrayList<Customer>();

		matchMobile.addAll(getGeneralService().getMobileCheck(

				sessionStateManage.getCountryId(), getMobile()));

		if (getPkCustomerId() != null) {

			customerList = getIcustomerRegistrationService().getCustomerInfo(
					getPkCustomerId());

			if (getPkCustomerId().intValue() == customerList.get(0)
					.getCustomerId().intValue()
					&& getMobile().equalsIgnoreCase(
							customerList.get(0).getMobile())) {

				setBooMobilecheck(false);

			} else if (matchMobile!= null && matchMobile.size() > 0) {
				log.info("Mobile No :"+matchMobile.get(0).getCustomerId());
				if(matchMobile.get(0).getCustomerId()!=null){
					setErrorMsg(matchMobile.get(0).getCustomerId().toString());
				}
				setBooMobilecheck(true);
			}

		} else if (matchMobile!= null && matchMobile.size() > 0) {

			setBooMobilecheck(true);

		}

	}

	public void checkEmail() {

		setBooEmailCheck(false);
		List<Customer> matchEmail = new ArrayList<Customer>();

		matchEmail.addAll(getGeneralService().getEmailCheck(getEmail()));

		if (getPkCustomerId() != null) {

			customerList = getIcustomerRegistrationService().getCustomerInfo(
					getPkCustomerId());

			if (getPkCustomerId().intValue() == customerList.get(0)
					.getCustomerId().intValue()
					&& getEmail().equalsIgnoreCase(
							customerList.get(0).getEmail())) {

				setBooEmailCheck(false);

			} else if (matchEmail.size() > 0) {

				setBooEmailCheck(true);

			}

		} else if (matchEmail.size() > 0) {

			setBooEmailCheck(true);

		}

	}

	public void checkEmailCard() {
		setEmail(getScardemail());
		checkEmail();
	}

	public void checkMobileCard() {
		setMobile(getTelephone1());
		checkMobile();
	}

	private StreamedContent myImage;

	/*
	 * public StreamedContent getMyImage() {
	 * 
	 * return myImage;
	 * 
	 * }
	 * 
	 * public void setMyImage(StreamedContent myImage) {
	 * 
	 * this.myImage = myImage;
	 * 
	 * }
	 * 
	 * public CustomerRegistrationBranchBean(){ try { Blob blob =
	 * getBranchpageService().getImage(new BigDecimal(100)).get(0).getImage();
	 * String imageNameWithExtention = getBranchpageService().getImage(new
	 * BigDecimal(100)).get(0).getImageName(); String imageExtention =
	 * imageNameWithExtention
	 * .substring(imageNameWithExtention.lastIndexOf(".")); String imagename =
	 * imageNameWithExtention
	 * .substring(0,imageNameWithExtention.lastIndexOf(".") - 1);
	 * 
	 * int blobLength = (int) blob.length(); byte[] blobAsBytes =
	 * blob.getBytes(1, blobLength); InputStream stream = new
	 * ByteArrayInputStream(blobAsBytes);
	 * 
	 * //InputStream inputStream = myImage = new DefaultStreamedContent(stream,
	 * "image/png"); }
	 */

	// CR Employee Salary Limit Fields

	private BigDecimal dailyLimit;
	private BigDecimal weeklyLimit;
	private BigDecimal monthlyLimit;
	private BigDecimal quarterlyLimit;
	private BigDecimal halfyearly;
	private BigDecimal annualLimit;

	public BigDecimal getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(BigDecimal dailyLimit) {
		this.dailyLimit = dailyLimit;
	}

	public BigDecimal getWeeklyLimit() {
		return weeklyLimit;
	}

	public void setWeeklyLimit(BigDecimal weeklyLimit) {
		this.weeklyLimit = weeklyLimit;
	}

	public BigDecimal getMonthlyLimit() {
		return monthlyLimit;
	}

	public void setMonthlyLimit(BigDecimal monthlyLimit) {
		this.monthlyLimit = monthlyLimit;
	}

	public BigDecimal getQuarterlyLimit() {
		return quarterlyLimit;
	}

	public void setQuarterlyLimit(BigDecimal quarterlyLimit) {
		this.quarterlyLimit = quarterlyLimit;
	}

	public BigDecimal getHalfyearly() {
		return halfyearly;
	}

	public void setHalfyearly(BigDecimal halfyearly) {
		this.halfyearly = halfyearly;
	}

	public BigDecimal getAnnualLimit() {
		return annualLimit;
	}

	public void setAnnualLimit(BigDecimal annualLimit) {
		this.annualLimit = annualLimit;
	}

	public void comparingDailyWeekly(FacesContext context,
			UIComponent component, Object value) {
		BigDecimal weeklyValue = (BigDecimal) value;
		System.out.println("!!!!!!!" + weeklyValue);
		System.out.println(" getDailyLimit : " + getDailyLimit()
				+ " weeklyValue : " + weeklyValue);
		if (getDailyLimit() == null) {
			FacesMessage msg = new FacesMessage(
					"Please Enter Daily and Then Weekly",
					"Please Enter Daily and Then Weekly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getDailyLimit().compareTo(weeklyValue) > 0
				|| getDailyLimit().compareTo(weeklyValue) == 0) {
			setWeeklyLimit(null);
			FacesMessage msg = new FacesMessage(
					"Please Enter Greater Than Daily",
					"Please Enter Greater Than Daily");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

	public void comparingWeeklyMonthly(FacesContext context,
			UIComponent component, Object value) {
		BigDecimal monthlyValue = (BigDecimal) value;
		if (getWeeklyLimit() == null || monthlyValue == null) {
			FacesMessage msg = new FacesMessage(
					"Please Enter Weekly and Then Monthly",
					"Please Enter Weekly and Then Monthly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getWeeklyLimit().compareTo(monthlyValue) > 0
				|| getWeeklyLimit().compareTo(monthlyValue) == 0) {
			setMonthlyLimit(null);
			FacesMessage msg = new FacesMessage(
					"Please Enter Greater Than Weekly",
					"Please Enter Greater Than Weekly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	public void comparingMonthlyQuarterly(FacesContext context,
			UIComponent component, Object value) {
		BigDecimal quarterlyValue = (BigDecimal) value;
		if (getMonthlyLimit() == null || quarterlyValue == null) {
			FacesMessage msg = new FacesMessage(
					"Please Enter Monthly and Then Quarterly",
					"Please Enter Monthly and Then Quarterly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getMonthlyLimit().compareTo(quarterlyValue) > 0
				|| getMonthlyLimit().compareTo(quarterlyValue) == 0) {
			setQuarterlyLimit(null);
			FacesMessage msg = new FacesMessage(
					"Please Enter Greater Than Monthly",
					"Please Enter Greater Than Monthly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	public void comparingQuarterlyHalfYearly(FacesContext context,
			UIComponent component, Object value) {
		BigDecimal halfYearlyValue = (BigDecimal) value;
		if (getQuarterlyLimit() == null || halfYearlyValue == null) {
			FacesMessage msg = new FacesMessage(
					"Please Enter Quarterly and Then Half Yearly",
					"Please Enter Quarterly and Then Half Yearly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getQuarterlyLimit().compareTo(halfYearlyValue) > 0
				|| getQuarterlyLimit().compareTo(halfYearlyValue) == 0) {
			setHalfyearly(null);
			FacesMessage msg = new FacesMessage(
					"Please Enter Greater Than Quarterly",
					"Please Enter Greater Than Quarterly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	public void comparingHalfYearlyAnnually(FacesContext context,
			UIComponent component, Object value) {
		BigDecimal annualyValue = (BigDecimal) value;
		System.out.println("!!!!!!!" + annualyValue);
		System.out.println(" getHalfyearly : " + getHalfyearly()
				+ " annualyValue : " + annualyValue);
		if (getHalfyearly() == null || annualyValue == null) {
			FacesMessage msg = new FacesMessage(
					"Please Enter Half Yearly and Then Annual",
					"Please Enter Half Yearly and Then Annual");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getHalfyearly().compareTo(annualyValue) > 0
				|| getHalfyearly().compareTo(annualyValue) == 0) {
			setAnnualLimit(null);
			FacesMessage msg = new FacesMessage(
					"Please Enter Greater Than HalfYearly",
					"Please Enter Greater Than HalfYearly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	private String stateName;
	private String districtName;
	private String cityName;
	private String nationalityName;
	private String titleName;
	private String articleName;
	private String levelName;
	private String incomeRangeName;
	private String emplTypeName;
	private String professionName;

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getNationalityName() {
		return nationalityName;
	}

	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getIncomeRangeName() {
		return incomeRangeName;
	}

	public void setIncomeRangeName(String incomeRangeName) {
		this.incomeRangeName = incomeRangeName;
	}

	public String getEmplTypeName() {
		return emplTypeName;
	}

	public void setEmplTypeName(String emplTypeName) {
		this.emplTypeName = emplTypeName;
	}

	public String getProfessionName() {
		return professionName;
	}

	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

	private Boolean rendercontactTypeId = false;

	public Boolean getRendercontactTypeId() {
		return rendercontactTypeId;
	}

	public void setRendercontactTypeId(Boolean rendercontactTypeId) {
		this.rendercontactTypeId = rendercontactTypeId;
	}


	public void clickOnOK() throws ParseException, IOException {

		setBooVerifyAllRen(false);
		setBooRemitterInfo(false);
		setBooIdDetail(false);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooEmploymentDetails(false);
		setBooProof(false);
		setContactlistcheck(false);
		setRenderSavebutton(false);
		setRenderSignature(false);

		clearRemitterInfo();
		clearContactDetail();
		clearEmploymentInfo();
		setBooCivilId(false);
		setBooOtherId(false);
		setChangeDobPass(false);
		clearProofInfo();
		contactList.clear();
		createProofList.clear();
		setIdNumberverification(getIdNumber());
		setSignaturePanelRender(false);
		setRenderFinal(false);
		setRenderVerifyReport(false);
		setBooChangeDob(false);
		setBooVerifyAllRen(false);
		setRenderverication(true);
		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("customerregistrationbranch.xhtml");
		/*getFetchContactTypeList();
		customerIdProofList = getIcustomerRegistrationService()
				.getCustomerIdProofCheck(new BigDecimal(getIdType()),
						getIdNumber(),session.getCountryId());

		if (customerIdProofList.size() > 0) {
			setPkCustomerId(customerIdProofList.get(0).getFsCustomer()
					.getCustomerId());
			fillCountryList();
			fillStateList();
			fillDistrictList();
			fillCityList();
			setContactDetails();
			setEmployeementInfo();

			setCustomerIdProof();
			setCustomerDetailsFetch();

			setBooVerifyAllRen(false);
		}

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("customerregistrationbranch.xhtml");
		 */
	}

	public void clickRegistration() throws IOException, ParseException {
		setBooIdTypeCheck(false);

		setChangeDobPass(true);

		setBooRemitterInfo(true);
		setBooIdDetail(false);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooEmploymentDetails(false);
		setBooProof(false);
		setBooVerifyAllRen(false);
		getFetchContactTypeList();

		setSignaturePanelRender(false);
		setRenderFinal(false);
		setRenderVerifyReport(false);
		setBooChangeDob(false);
		getFetchContactTypeList();
		setNextUpdateRender(false);

		/*customerIdProofList = getIcustomerRegistrationService()
				.getCustomerIdProofCheck(new BigDecimal(getIdType()),
						getIdNumber(),session.getCountryId());
		 */

		BigDecimal identityTpeIds = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
				identityTpeIds = entry.getKey();
				break;
			}
		}

		BigDecimal idtypeCivilIdnew = getGeneralService().getComponentId(Constants.CIVIL_ID_NEW,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
		if(getIdType()!=null && idtypeCivilIdnew!=null && getIdType().equalsIgnoreCase(identityTpeIds.toString()) || getIdType().equalsIgnoreCase(idtypeCivilIdnew.toString())){
			customerIdProofList = getIcustomerRegistrationService().getCustomerIdProofCheck(identityTpeIds,getIdNumber(),session.getCountryId());

			if (customerIdProofList.size() == 0) {
				customerIdProofList = getIcustomerRegistrationService().getCustomerIdProofCheck(idtypeCivilIdnew,getIdNumber(),session.getCountryId());
			}
		}else{

			customerIdProofList = getIcustomerRegistrationService()
					.getCustomerIdProofCheck(new BigDecimal(getIdType()),
							getIdNumber(),session.getCountryId());
		}

		if (customerIdProofList.size() > 0) {
			setPkCustomerId(customerIdProofList.get(0).getFsCustomer()
					.getCustomerId());
			fillCountryList();
			fillStateList();
			fillDistrictList();
			fillCityList();
			setContactDetails();
			setEmployeementInfo();

			setCustomerIdProof();
			setCustomerDetailsFetch();
			setBooVerifyAllRen(false);
			setRenderverication(false);
			setBooCivilId(true);
			setBooOtherId(false);
			setChangeDobPass(true);

		}

		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("customerregistrationbranch.xhtml");
	}

	public void clickOnokManual() throws IOException, ParseException {

		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("customerregistrationbranch.xhtml");
	}

	private Boolean booVerifyAllRen = false;

	public Boolean getBooVerifyAllRen() {
		return booVerifyAllRen;
	}

	public void setBooVerifyAllRen(Boolean booVerifyAllRen) {
		this.booVerifyAllRen = booVerifyAllRen;
	}

	public void setCustomerDetailsFetch() {

		customerList = getIcustomerRegistrationService().getCustomerInfoFetch(
				getPkCustomerId());

		setNationality(customerList.get(0).getFsCountryMasterByNationality()
				.getCountryId());

		// setLevel(customerList.get(0).getFsArticleDetails().getArticleDetailId());
		setFirstName(customerList.get(0).getFirstName());
		setFirstNamel(customerList.get(0).getFirstNameLocal());
		setMiddleName(customerList.get(0).getMiddleName());
		setMiddleNamel(customerList.get(0).getMiddleNameLocal());
		setShortName(customerList.get(0).getShortName());
		setShortNamel(customerList.get(0).getShortNameLocal());
		setLastName(customerList.get(0).getLastName());
		setLastNamel(customerList.get(0).getLastNameLocal());
		setEmail(customerList.get(0).getEmail());
		setTitle(customerList.get(0).getTitle());
		setMobile(customerList.get(0).getMobile());
		setGender(customerList.get(0).getGender());
		setEmosCustomer(customerList.get(0).getEmosCustomer());
		setActiveInd(customerList.get(0).getActivatedInd());
		if(customerList.get(0).getCustomerReference()!=null){
			setUpdateCustomerRefNo(customerList.get(0).getCustomerReference());
		}
		if (sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {
			mapIdentityType = ruleEngine.getComponentData("Identity Type");
			BigDecimal identityTpeId = null;
			for (Map.Entry<BigDecimal, String> entry : mapIdentityType
					.entrySet()) {
				if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
					identityTpeId = entry.getKey();
					break;
				}
			}

			// System.out.println("==="+getSelectedIdType());
			// SessionStateManage sessionStateManage = new SessionStateManage();
			if (getIdType().equalsIgnoreCase(identityTpeId.toPlainString())) {
				if(customerList.get(0).getDateOfBirth()!=null){
					setShowDob(new SimpleDateFormat("dd/MM/yyyy")
					.format(customerList.get(0).getDateOfBirth()));
				}else{
					setBooCivilId(false);
					setBooOtherId(true);
					setChangeDobPass(false);
				}

			} else {

				setDob(customerList.get(0).getDateOfBirth());
			}
		}

		setDob(customerList.get(0).getDateOfBirth());

		setAlternativeEmail(customerList.get(0).getAlterEmailId());

		setTitlel(customerList.get(0).getTitleLocal());
		// setCountryId(customerList.get(0).getFsCountryMasterByCountryId().getCountryId());

		setCreatedByCustomer(customerList.get(0).getCreatedBy());
		setCreationDateCustomer(customerList.get(0).getCreationDate());

		setAMLStatus(customerList.get(0).getAmlStatus());
		setNumberofhits(customerList.get(0).getNumberOfHits());

		//setAMLStatus("pass");
		//setNumberofhits("0");

		//if (customerList.get(0).getActivatedInd().equalsIgnoreCase("0")) {
		if (customerList.get(0).getActivatedInd().equalsIgnoreCase(Constants.CUST_INACTIVE_INDICATOR)) {
			setApproved(false);
		} else {
			setApproved(true);
		}
	}

	private StreamedContent mypdfdata;

	public StreamedContent getMypdfdata() {
		return mypdfdata;
	}

	public void setMypdfdata(StreamedContent mypdfdata) {
		this.mypdfdata = mypdfdata;
	}

	public String getIdFile() {
		return  java.util.UUID.randomUUID().toString();
	}

	public StreamedContent getMyImage() {
		return myImage;
	}

	public void setMyImage(StreamedContent myImage) {
		this.myImage = myImage;
	}

	private List<CreateProofTable> checkedverify = new ArrayList<CreateProofTable>();

	public List<CreateProofTable> getCheckedverify() {
		return checkedverify;
	}

	public void setCheckedverify(List<CreateProofTable> checkedverify) {
		this.checkedverify = checkedverify;
	}

	public void verifyValIfNO() {
		List<CreateProofTable> lstcreateProof =getCheckedverify();
		if(lstcreateProof.size()!=0){
			CreateProofTable createProof = lstcreateProof.get(0);
			createProof.setChecked(false);
		}

	}

	public void showImage(CreateProofTable createProof) {
		checkedverify.clear();
		BigDecimal imageId = createProof.getImageId();
		setMypdfdata(null);
		setMyImage(null);
		InputStream stream = null;
		Blob blob = null;
		String imagename=null,imageExtention=null,str;
		System.out.println("!!!!!!!!!!!!! image ID : " + imageId);
		try {
			blob = getBranchpageService().getImage(imageId).get(0).getImage();
			String imageNameWithExtention = getBranchpageService().getImage(imageId).get(0).getImageName();


			try {
				String[] parts = imageNameWithExtention.split("\\.");
				imagename = parts[0];
				imageExtention = parts[1];
				System.out.println("Components:"+imagename+"$$$$$$$$$"+imageExtention);
			} catch (Exception nme) 
			{
				System.out.println("No Validation Component");
			}


			/*String imageExtention = imageNameWithExtention.substring(imageNameWithExtention.lastIndexOf("."));
			String imagename = imageNameWithExtention.substring(0,
					imageNameWithExtention.lastIndexOf("."));*/

			System.out.println("imageNameWithExtention!!!!!!!!!!!!"
					+ imageNameWithExtention);
			System.out.println("imageExtention!!!!!!!!!!!!" + imageExtention);
			System.out.println("imagename!!!!!!!!!!!!" + imagename);
			int blobLength = (int) blob.length();
			byte[] blobAsBytes = blob.getBytes(1, blobLength);
			stream = new ByteArrayInputStream(blobAsBytes);
			//myImage = new DefaultStreamedContent(stream, "image/"+imageExtention+"",imageNameWithExtention);
			checkedverify.add(createProof);
			setCheckedverify(checkedverify);
			if (createProof.getChecked()) {
				RequestContext context = RequestContext.getCurrentInstance();
				if(imageExtention.equalsIgnoreCase("pdf")){
					mypdfdata = new DefaultStreamedContent(stream, "application/"+imageExtention+"",imageNameWithExtention);
					setMypdfdata(mypdfdata);
					context.execute("PF('showpdf').show();");
				}else{
					myImage = new DefaultStreamedContent(stream, "image/"+imageExtention+"",imageNameWithExtention);
					setMyImage(myImage);
					context.execute("PF('showimage').show();");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private String email1;



	public ApllicationMailer1 getMailService1() {
		return mailService1;
	}

	public void setMailService1(ApllicationMailer1 mailService1) {
		this.mailService1 = mailService1;
	}

	public JavaMailSender getMailSender1() {
		return mailSender1;
	}

	public void setMailSender1(JavaMailSender mailSender1) {
		this.mailSender1 = mailSender1;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	private Boolean renderverifiedTokenNumber = false;

	public Boolean getRenderverifiedTokenNumber() {
		return renderverifiedTokenNumber;
	}

	public void setRenderverifiedTokenNumber(Boolean renderverifiedTokenNumber) {
		this.renderverifiedTokenNumber = renderverifiedTokenNumber;
	}

	public void checkLimit() {

		String roleId1 = sessionStateManage.getRoleId();
		// String sender=sessionStateManage.getUserName();
		// String branchId=sessionStateManage.getBranchId();String
		// employeeid=sessionStateManage.getTelephone();
		// String location1=sessionStateManage.getLocation();
		// String telephoneno=sessionStateManage.getTelephone();
		int roleId = Integer.parseInt(roleId1);
		// String email1=sessionStateManage.getEmail1();
		System.out.println("==================+email1");
		List<Employee> empList = iEmployeeService.getEmployees();

		System.out.println("Employee List =========== > " + empList.size());
		for (Employee emp : empList) {

			if (emp.getFsRoleMaster().getRoleId().intValue() == roleId) {
				int countryBranchId = emp.getFsCountryBranch()
						.getCountryBranchId().intValue();

				// RequestContext context = RequestContext.getCurrentInstance();
				// context.execute("succ.show();");
				// MAIL GENERATION CODE

				List<Employee> newlist = iEmployeeService.getEmployees();
				ListIterator<Employee> le = newlist.listIterator();
				while (le.hasNext()) {
					Employee e1 = le.next();

					if (e1.getFsCountryBranch().getCountryBranchId().intValue() == countryBranchId
							&& e1.getFsRoleMaster().getRoleId().intValue() < roleId) {

						if ((e1.getFsCountryBranch().getCountryBranchId()
								.intValue() == countryBranchId)
								&& (e1.getFsRoleMaster().getRoleId().intValue() == roleId - 1)) {

							String emailId = e1.getEmail();

							String employeename = e1.getEmployeeName();

							Random r = new Random();
							int tokennumber = r.nextInt(99999) + 10000;
							String tokennum = "" + tokennumber;
							String customerid = "" + getProfessionName();

							// mailService.sendTokenMail1(emailId, getEmail1(),
							// "test", customerid, tokennum);
							// String customerId=getPkCustomerId().toString();
							String customerId = getIdNumber();
							// System.out.println(telephoneno);

							// mailService1.sendTokenMail2(emailId,email1,"welcome",
							// customerId ,
							// tokennum,sender,branchId,location1,telephoneno,employeename,email1);

						}

					}

				}
			}

		}
	}

	// SMart Card Integration By NAZISH

	// this is used for Smart card Information
	private String titleEn;
	private String fullNameEn;
	private String firstNameEn;
	private String lastNameEn;
	private String middleNameEn;
	private BigDecimal smartCardId;
	private String genderLatin;
	private String nationalityLatin;

	private String fullNameAr;
	private String firstNameAr;
	private String lastNameAr;
	private String middleNameAr;
	private String genderAr;
	private String nationalityAr;
	private String fatherFullNameAr;
	private String bloodType;
	private String civilId;
	private String telephone1;
	private String telephone2;
	private String scardemail;

	private String fatherNameArabic;

	private String arabicGFName;
	private String ArabicSurname;
	private String guardianCivilId;

	private String scarddistrict;
	private String scardstreet;
	private String building_no;
	private String unit_type;
	private String unit_no;
	private String scardfloor;
	private String sCardblock;

	private String birthdate;

	private String issuedate;
	private String expirydate;
	private String documentno;
	private String serialno;
	private String moireference;
	private String moireferenceType;
	private String additinal1;
	private String additinal2;
	private String addressRef;


	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public String getFullNameEn() {
		return fullNameEn;
	}

	public void setFullNameEn(String fullNameEn) {
		this.fullNameEn = fullNameEn;
	}

	public String getFirstNameEn() {
		return firstNameEn;
	}

	public void setFirstNameEn(String firstNameEn) {
		this.firstNameEn = firstNameEn;
	}

	public String getLastNameEn() {
		return lastNameEn;
	}

	public void setLastNameEn(String lastNameEn) {
		this.lastNameEn = lastNameEn;
	}

	public BigDecimal getSmartCardId() {
		return smartCardId;
	}

	public void setSmartCardId(BigDecimal smartCardId) {
		this.smartCardId = smartCardId;
	}

	public String getGenderLatin() {
		return genderLatin;
	}

	public void setGenderLatin(String genderLatin) {
		this.genderLatin = genderLatin;
	}

	public String getNationalityLatin() {
		return nationalityLatin;
	}

	public void setNationalityLatin(String nationalityLatin) {
		this.nationalityLatin = nationalityLatin;
	}

	public String getFullNameAr() {
		return fullNameAr;
	}

	public void setFullNameAr(String fullNameAr) {
		this.fullNameAr = fullNameAr;
	}

	public String getFatherFullNameAr() {
		return fatherFullNameAr;
	}

	public void setFatherFullNameAr(String fatherFullNameAr) {
		this.fatherFullNameAr = fatherFullNameAr;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getCivilId() {
		return civilId;
	}

	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}

	public String getTelephone1() {
		return telephone1;
	}

	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}

	public String getTelephone2() {
		return telephone2;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}

	public String getFatherNameArabic() {
		return fatherNameArabic;
	}

	public void setFatherNameArabic(String fatherNameArabic) {
		this.fatherNameArabic = fatherNameArabic;
	}

	public String getArabicGFName() {
		return arabicGFName;
	}

	public void setArabicGFName(String arabicGFName) {
		this.arabicGFName = arabicGFName;
	}

	public String getArabicSurname() {
		return ArabicSurname;
	}

	public void setArabicSurname(String arabicSurname) {
		ArabicSurname = arabicSurname;
	}

	public String getBuilding_no() {
		return building_no;
	}

	public void setBuilding_no(String building_no) {
		this.building_no = building_no;
	}

	public String getUnit_type() {
		return unit_type;
	}

	public void setUnit_type(String unit_type) {
		this.unit_type = unit_type;
	}

	public String getUnit_no() {
		return unit_no;
	}

	public void setUnit_no(String unit_no) {
		this.unit_no = unit_no;
	}

	public String getScardemail() {
		return scardemail;
	}

	public void setScardemail(String scardemail) {
		this.scardemail = scardemail;
	}

	public String getScarddistrict() {
		return scarddistrict;
	}

	public void setScarddistrict(String scarddistrict) {
		this.scarddistrict = scarddistrict;
	}

	public String getScardstreet() {
		return scardstreet;
	}

	public void setScardstreet(String scardstreet) {
		this.scardstreet = scardstreet;
	}

	public String getScardfloor() {
		return scardfloor;
	}

	public void setScardfloor(String scardfloor) {
		this.scardfloor = scardfloor;
	}

	public String getsCardblock() {
		return sCardblock;
	}

	public void setsCardblock(String sCardblock) {
		this.sCardblock = sCardblock;
	}

	public String getGuardianCivilId() {
		return guardianCivilId;
	}

	public void setGuardianCivilId(String guardianCivilId) {
		this.guardianCivilId = guardianCivilId;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getIssuedate() {
		return issuedate;
	}

	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}

	public String getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(String expirydate) {
		this.expirydate = expirydate;
	}

	public String getDocumentno() {
		return documentno;
	}

	public void setDocumentno(String documentno) {
		this.documentno = documentno;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getMoireference() {
		return moireference;
	}

	public void setMoireference(String moireference) {
		this.moireference = moireference;
	}

	public String getMiddleNameEn() {
		return middleNameEn;
	}

	public void setMiddleNameEn(String middleNameEn) {
		this.middleNameEn = middleNameEn;
	}

	public String getFirstNameAr() {
		return firstNameAr;
	}

	public void setFirstNameAr(String firstNameAr) {
		this.firstNameAr = firstNameAr;
	}

	public String getLastNameAr() {
		return lastNameAr;
	}

	public void setLastNameAr(String lastNameAr) {
		this.lastNameAr = lastNameAr;
	}

	public String getMiddleNameAr() {
		return middleNameAr;
	}

	public void setMiddleNameAr(String middleNameAr) {
		this.middleNameAr = middleNameAr;
	}

	public String getNationalityAr() {
		return nationalityAr;
	}

	public void setNationalityAr(String nationalityAr) {
		this.nationalityAr = nationalityAr;
	}

	public String getGenderAr() {
		return genderAr;
	}

	public void setGenderAr(String genderAr) {
		this.genderAr = genderAr;
	}

	public String getMoireferenceType() {
		return moireferenceType;
	}

	public void setMoireferenceType(String moireferenceType) {
		this.moireferenceType = moireferenceType;
	}

	public String getAdditinal1() {
		return additinal1;
	}

	public void setAdditinal1(String additinal1) {
		this.additinal1 = additinal1;
	}

	public String getAdditinal2() {
		return additinal2;
	}

	public void setAdditinal2(String additinal2) {
		this.additinal2 = additinal2;
	}

	public String getAddressRef() {
		return addressRef;
	}

	public void setAddressRef(String addressRef) {
		this.addressRef = addressRef;
	}

	/*	public void displayCivilIdDetails() throws FileNotFoundException,
			ParseException {

		clear();
		fetchSmartCardData();

	}*/

	/*	@SuppressWarnings("unchecked")
	public void saveSmartCardInfo() throws ParseException {

		DmsMas smartCard = new DmsMas();
		//smartCard.setAppnCountry(sessionStateManage.getCountryId().toString());
		List<CountryMaster> alphacode = icustomerRegistrationService.getCountryAlpha2Code(session.getCountryId());
		if(alphacode.size()!=0){
			String countryAlphaCode = alphacode.get(0).getCountryAlpha2Code()==null ? "" : alphacode.get(0).getCountryAlpha2Code();
			smartCard.setAppnCountry(countryAlphaCode);
		}
		//smartCard.setAppnCountry(Constants.KUWAIT_ALPHA_TWO_CODE);
		smartCard.setDocType(Constants.CIVILID);
		smartCard.setIdno(getCivilId());
		smartCard.setSerialNo(getSerialno());
		smartCard.setDocumentNo(getDocumentno());
		smartCard.setEFuname(getFullNameEn());
		smartCard.setLFstName(getFirstNameAr());
		smartCard.setLSurname(getArabicSurname());
		smartCard.setLFatherName(getFatherNameArabic());
		smartCard.setLFuname(getFullNameAr());
		smartCard.setLGfName(getArabicGFName());
		smartCard.setNtcdId(getNationalityLatin());
		//smartCard.setNationalityAr(getNationalityAr());
		smartCard.setGender(getGenderLatin());
		smartCard.setIssuedat(new SimpleDateFormat("dd/MM/yyyy")
				.parse(getIssuedate()));
		smartCard.setExpireDt(new SimpleDateFormat("dd/MM/yyyy")
				.parse(getExpirydate()));
		smartCard.setBirthdat(new SimpleDateFormat("dd/MM/yyyy")
				.parse(getBirthdate()));
		smartCard.setTel1(getTelephone1());
		smartCard.setTel2(getTelephone2());
		smartCard.setEmailId(getScardemail());
		smartCard.setBloodTyp(getBloodType());
		smartCard.setBldgno(getBuilding_no());
		//smartCard.setUnitType(getUnit_type());
		//smartCard.setUnitNo(getUnit_no());
		smartCard.setFloorno(getScardfloor());
		smartCard.setStreet(getScardstreet());
		smartCard.setBlockNo(getsCardblock());
		smartCard.setDistrict(getScarddistrict());
		smartCard.setGuardIdno(getGuardianCivilId());
		smartCard.setAddrUniqKey(getAddressRef());
		smartCard.setProgno("JAVA");//Hard Coded Value

		if (getSmartCardId() != null) {
			smartCard.setDmsIdMasId(getSmartCardId());
			// smartCard.setu
			 //smartCard.(getCreatedByCustomer());
			smartCard.setUpddat(getCreationDateCustomer());

		} else {
			smartCard.setCreator(userName);
			//CR for creation date based on application country
			//smartCard.setCreateDate(new Date());
			smartCard.setCrtdat(getCurrentTime());
			// getIcustomerRegistrationService().saveCustomer(customer);
			// setPkCustomerId(customer.getCustomerId());
		}
		generalService.saveOrUpdate((T)smartCard);
		setSmartCardId(smartCard.getDmsIdMasId());

	}*/

	public void saveSmartCardInfoToCutomer() throws ParseException, IOException {

		try{
			customer = new Customer();
			SessionStateManage sessionStateManage = new SessionStateManage();

			countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionStateManage.getCountryId());

			/* Nationality Id save */
			CountryMaster nationality = new CountryMaster();
			nationality.setCountryId(getNationality());

			/* save company */
			companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(sessionStateManage.getCompanyId());

			/** Customer Type */
			BizComponentData customerType = new BizComponentData();
			customerType.setComponentDataId(getGeneralService().getComponentId(Constants.CUSTOMERTYPE_INDU,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());

			/** save Group ID */
			BizComponentData companyGroup = new BizComponentData();
			companyGroup.setComponentDataId(getGeneralService().getComponentId(Constants.GROUPID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());

			languageType = new LanguageType();
			languageType.setLanguageId(sessionStateManage.getLanguageId());
			customer.setFsLanguageType(languageType);
			customer.setFsCountryMasterByCountryId(countryMaster);
			customer.setFsCountryMasterByNationality(nationality);

			/*	if (getGenderLatin().equalsIgnoreCase("F")) {
			customer.setTitle(Constants.TITLE_FOR_MRS);
			customer.setTitleLocal(Constants.LOCAL_TITLE_FOR_MRS);
			if(sessionStateManage.getLanguageId().intValue()==Constants.LANGUAGE_ID){
			customer.setGender(Constants.GENDER_FEMALE);
			}else{
				customer.setGender(Constants.GENDER_FEMALE_ARABIC);
			}
		} else {
			customer.setTitle(Constants.TITLE_FOR_MR);
			customer.setTitleLocal(Constants.LOCAL_TITLE_FOR_MR);
			if(sessionStateManage.getLanguageId().intValue()==Constants.LANGUAGE_ID){
			customer.setGender(Constants.GENDER_MALE);
			}else{
				customer.setGender(Constants.GENDER_MALE_ARABIC);
			}
		}
			 */
			customer.setTitle(getTitle());
			customer.setGender(getGender());
			customer.setTitleLocal(getTitlel());

			String fullName = getFullNameEn();
			String names[] = fullName.split(" ");

			if(names.length==4){
				String firstName = names[0];
				String middleName = names[1]+" "+names[2];
				String lastName = names[3];
				customer.setFirstName(firstName);
				customer.setMiddleName(middleName);
				customer.setLastName(lastName);
			}else if(names.length==5){
				String firstName = names[0];
				String middleName = names[1]+" "+names[2]+" "+names[3];
				String lastName = names[4];
				customer.setFirstName(firstName);
				customer.setMiddleName(middleName);
				customer.setLastName(lastName);

			}else if(names.length==3){
				String firstName = names[0];
				String middleName = names[1];
				String lastName = names[2];
				customer.setFirstName(firstName);
				customer.setMiddleName(middleName);
				customer.setLastName(lastName);

			}else if(names.length==2){
				String firstName = names[0];
				//String middleName = names[1];
				String lastName = names[1];
				customer.setFirstName(firstName);
				//customer.setMiddleName(middleName);
				customer.setLastName(lastName);

			}else if(names.length==1){
				String firstName = names[0];

				customer.setFirstName(firstName);


			}else{

				String firstName = names[0];
				String middleName = names[1];
				String lastName = names[2];
				customer.setFirstName(firstName);
				customer.setMiddleName(middleName);
				customer.setLastName(lastName);

			}

			/*if(getFirstName()!=null){
			customer.setFirstName(getFirstName());
		}
		if(getMiddleName()!=null){
			customer.setMiddleName(getMiddleName());
		}
		if(getLastName()!=null){
			customer.setLastName(getLastName());
		}*/
			if(getShortName()!=null  && getShortName().equalsIgnoreCase("")){
				customer.setShortName(getShortName());
			}

			customer.setFirstNameLocal(getFirstNameAr());
			customer.setLastNameLocal(getLastNameAr());

			/*if(getFirstNamel()!=null){
			customer.setFirstNameLocal(getFirstNamel());
		}
		if(getMiddleNamel()!=null){
			customer.setMiddleNameLocal(getMiddleNamel());
		}
		if(getLastNamel()!=null){
			customer.setLastNameLocal(getLastNamel());
		}*/
			if(getShortNamel()!=null){
				customer.setShortNameLocal(getShortNamel());
			}

			customer.setDailyLimit(getDailyLimit());

			customer.setWeeklyLimit(getWeeklyLimit());
			customer.setMontlyLimit(getMonthlyLimit());
			customer.setQuaterlyLimit(getQuarterlyLimit());
			customer.setHalfYearly(getHalfyearly());
			customer.setAnnualLimit(getAnnualLimit());
			customer.setSmartCardIndicator(Constants.Yes);
			customer.setIsActive(Constants.Yes);
			customer.setBranchCode( new BigDecimal(sessionStateManage.getBranchId()));

			// Added by kani begin smart cardhh
			if (getCustRefId() != null) {

				customer.setIntroducedBy(getCustRefId().toString());
				customer.setIntroducedDate(new Date());

			}
			customer.setMedicalInsuranceInd(getMedicalInsuranceInd());
			customer.setPepsIndicator(getPepsindicator());


			mapIdentityType = ruleEngine.getComponentData("Identity Type");
			BigDecimal identityTpeId = null;
			for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
				if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
					identityTpeId = entry.getKey();
					break;
				}
			}

			customer.setMobile(getTelephone1());
			customer.setEmail(getScardemail());
			customer.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy")
			.parse(getBirthdate()));

			// customer.setCreatedBy(userName);
			//customer.setActivatedInd("1");
			customer.setActivatedInd(Constants.CUST_ACTIVE_INDICATOR);
			ArticleDetails articleDetail = new ArticleDetails();
			articleDetail.setArticleDetailId(getLevel());
			customer.setFsArticleDetails(articleDetail);

			// save to Income
			IncomeRangeMaster incomeRange = new IncomeRangeMaster();
			incomeRange.setIncomeRangeId(getIncomeRange());
			customer.setFsIncomeRangeMaster(incomeRange);

			customer.setFsCountryMasterByCountryId(countryMaster);
			customer.setFsCompanyMaster(companyMaster);
			customer.setFsBizComponentDataByCustomerTypeId(customerType);
			customer.setFsBizComponentDataByGroupId(companyGroup);
			customer.setActivatedInd(Constants.CUST_ACTIVE_INDICATOR);
			customer.setEmosCustomer(getEmosCustomer());

			/* Managing save or update */
			if (getPkCustomerId() != null) {
				customer.setCustomerId(getPkCustomerId());
				//customer.setUpdatedBy(sessionStateManage.getUserName());
				//CR for creation date based on application country
				//customer.setLastUpdated(new Date());
				customer.setLastUpdated(getCurrentTime());
				customer.setCreatedBy(getCreatedByCustomer());
				customer.setCreationDate(getCreationDateCustomer());
				customer.setCustomerReference(getUpdateCustomerRefNo());
				customer.setSignatureSpecimen(getDigitalSignature());
				//customer.setActivatedInd("1");

				// checkLimit();
				if(getActiveInd()!=null && getActiveInd().equalsIgnoreCase(Constants.CUST_ACTIVE_INDICATOR)){
					customer.setUpdatedBy(session.getUserName());

				}

			} else {
				customer.setCreatedBy(sessionStateManage.getUserName());

				customer.setCreationDate(getCurrentTime());
				//customer.setCustomerReference(getCustomerRefNo()); CR -Trigger will responsible to generate Customer Reference number.
				if(getDigitalSignature()!=null){
					customer.setSignatureSpecimen(getDigitalSignature());
				}else{
					RequestContext.getCurrentInstance().execute("signatureMandatory.show();");
				}

			}


			customer.setActivatedDate(getCurrentTime());
			customer.setVerificationBy(sessionStateManage.getUserName());

			customer.setVerificationDate(getCurrentTime());


			//@@@ AML	
			String amlReturnStatus = null;
			String amlStatus = null;
			String amlhits = null;

			amlReturnStatus = getAMLCheckStatus(customer);

			//amlReturnStatus ="PASS-0";

			if (amlReturnStatus == null) {
				customer.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);

				customer.setNumberOfHits(new BigDecimal(0));
			}else{
				String[] parts = amlReturnStatus.split("-");
				amlStatus = parts[0];
				amlhits = parts[1];

				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_ERROR)){
					customer.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);
					customer.setNumberOfHits(new BigDecimal(amlhits));
				}
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PENDING)){
					customer.setAmlStatus(Constants.AML_STATUS_BCO);
					customer.setNumberOfHits(new BigDecimal(amlhits));
					//saveAmlStatus(customer);
				}
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PASS)){
					customer.setAmlStatus(Constants.AML_STATUS_PASS);
					customer.setNumberOfHits(new BigDecimal(0));
				}



			}


			HashMap<String,Object> mapAllDetailForSave = new HashMap<String,Object>();

			mapAllDetailForSave.put("customerinfo", customer);

			List<ContactDetail> lstCustContactDetails =  saveCustomerContactDetails();

			mapAllDetailForSave.put("customerContactDetails", lstCustContactDetails);

			if(getBooEmploymentPanel()){
				CustomerEmploymentInfo custEmployeeInfo = saveCustomerEmployeeInformation();
				mapAllDetailForSave.put("customerEmployeeInfo", custEmployeeInfo);
				mapAllDetailForSave.put("customerUnEmployee", new Boolean(true));
			}else{
				mapAllDetailForSave.put("customerUnEmployee", new Boolean(false));
			}

			DmsMas customerBySmartCard = saveSmartCardInformationForDmsMas();

			mapAllDetailForSave.put("customerBySmartCard", customerBySmartCard);

			if (createProofList.size() == 0) {
				//setIdListcheck(true);
				mapAllDetailForSave.put("custIdProofexists", new Boolean(false));
				try {
					getIcustomerRegistrationService().saveSmartCardCustomerRegistration(mapAllDetailForSave);
					setPkCustomerId(customer.getCustomerId());
				} catch (Exception e) {
					log.error("Exception Occured While Saving", e);
					RequestContext.getCurrentInstance().execute("saveerror.show();");
				}
			} else {
				//setIdListcheck(false);

				List<CustomerIdProof> lstCustIdProof = saveCustomerProofInformation();

				mapAllDetailForSave.put("custIdProofexists",new Boolean(true));
				mapAllDetailForSave.put("customerIdProof", lstCustIdProof);

				try {
					getIcustomerRegistrationService().saveSmartCardCustomerRegistration(mapAllDetailForSave);
					setPkCustomerId(customer.getCustomerId());
				} catch (Exception e) {
					log.error("Exception Occured While Saving", e);

					setExitRender(false);
					setBooRenderFinalSave(false);
					setSignaturePanelRender(false);
					setSignatureCaptureRender(true);
					setSignatureSpecimenRender(false);
					setBooVerifyAllRen(false);
					setRenderUpdate(false);
					setRenderRegistration(false);
					setBooProof(false);
					setRenderSignature(false);
					setRenderVerifyReport(false);
					setBooRemitterInfo(false);
					setBooIdDetail(false);
					setBooCustomerDetails(false);
					setBooContactDetails(false);
					setBooEmploymentDetails(false);
					setBooProof(false);
					setRenderSavebutton(false);
					setRenderSignature(false);
					setBooVerifyAllRen(false);
					setRenderverication(false);
					setSuccessPanel(false);
					setUpdatePanel(false);
					setRenderUpdate(false);
					setRenderRegistration(false);
					setRenderVerifyReport(false);
					setRenderFinal(false);
					setExitRender(false);
					setBooVerifyAllRen(false);
					setNextSignaturePanel(false);
					setNextUpdateRender(false);
					RequestContext.getCurrentInstance().execute("saveerror.show();");
				}
			}


			// code block for partial save	

			/*getIcustomerRegistrationService().saveCustomer(customer);
		setPkCustomerId(customer.getCustomerId());
		saveContactDetails();
		saveEmployementDetails();
		saveProofDetails();
		saveSmartCardInfo();*/

			String verificationToken = null;
			String emosCustomer = null;
			String updatedBy = null;
			BigDecimal customerReference = null;

			//List<Customer> custlist = new ArrayList<Customer>();
			List<Customer> custlist = getIcustomerRegistrationService().getVerificationToken(getPkCustomerId());
			if (custlist.size() > 0) {
				verificationToken = custlist.get(0).getVerificationTokenId();
				updatedBy = custlist.get(0).getUpdatedBy();
				emosCustomer = custlist.get(0).getEmosCustomer();
				if(custlist.get(0).getCustomerReference()!=null){
					customerReference = custlist.get(0).getCustomerReference();
					setUpdateCustomerRefNo(customerReference);
				}
			}

			/* if (emosCustomer !=null && !emosCustomer.equalsIgnoreCase("1")) {
			  String toMailid = custlist.get(0).getEmail();
			  mailService1.sendMail(toMailid, "Customer Info Log Difference", verificationToken);
			  setSignaturePanelRender(false);
			  setRenderUpdate(true);
			  setRenderRegistration(false);
			  setBooProof(false);
			  setRenderSignature(false);
			  setRenderVerifyReport(false);
			  setBooRenderFinalSave(false);
			  setExitRender(false);
			  setSuccessPanel(false);
			  setUpdatePanel(false);
			  setBooVerifyAllRen(false);
			  setNextSignaturePanel(false);
			  setNextUpdateRender(false);
			  getAMLCheckStatus_afterSave(getPkCustomerId());
			  customer.setCustomerId(getPkCustomerId());
			  getIcustomerRegistrationService().saveCustomerEmos(getPkCustomerId(), Constants.Yes);
		  }
		  else if (updatedBy != null && emosCustomer == null)  {
			  String toMailid = custlist.get(0).getEmail();
			  mailService1.sendMail(toMailid, "Customer Info Log Difference", verificationToken);
			  setSignaturePanelRender(false);
			  setRenderUpdate(true);
			  setRenderRegistration(false);
			  setBooProof(false);
			  setRenderSignature(false);
			  setRenderVerifyReport(false);
			  setBooRenderFinalSave(false);
			  setExitRender(false);
			  setSuccessPanel(false);
			  setUpdatePanel(false);
			  setBooVerifyAllRen(false);
			  setNextSignaturePanel(false);
			  setNextUpdateRender(false);
			  getAMLCheckStatus_afterSave(getPkCustomerId());
			  customer.setCustomerId(getPkCustomerId());
			  getIcustomerRegistrationService().saveCustomerEmos(getPkCustomerId(), Constants.Yes);

		  } else {*/

			setExitRender(false);
			setBooRenderFinalSave(false);
			setSignaturePanelRender(false);
			setSignatureCaptureRender(false);
			setSignatureSpecimenRender(false);
			setBooVerifyAllRen(false);
			setRenderUpdate(false);
			setRenderRegistration(false);
			setBooProof(false);
			setRenderSignature(false);
			setRenderVerifyReport(false);
			setBooRemitterInfo(false);
			setBooIdDetail(false);
			setBooCustomerDetails(false);
			setBooContactDetails(false);
			setBooEmploymentDetails(false);
			setBooProof(false);
			setRenderSavebutton(false);
			setRenderSignature(false);
			setBooVerifyAllRen(false);
			setRenderverication(false);
			setSuccessPanel(true);
			setUpdatePanel(false);
			setRenderUpdate(false);
			setRenderRegistration(false);
			setRenderVerifyReport(false);
			setRenderFinal(false);
			setExitRender(false);
			setBooVerifyAllRen(false);
			setNextSignaturePanel(false);
			setNextUpdateRender(false);
			getAMLCheckStatus_afterSave(getPkCustomerId());
			if(emosCustomer!=null && emosCustomer.equalsIgnoreCase("1")){
				getIcustomerRegistrationService().saveCustomerEmos(getPkCustomerId(), Constants.Yes);

			}

			List<String> outPutList = icustomerRegistrationService.callProcedureUpdate(getPkCustomerId());

			if(outPutList.size()>0){

				if(outPutList.get(0).equalsIgnoreCase(Constants.Yes)){

					RequestContext.getCurrentInstance().execute("migrationexception.show();");

				}
			}

			//}
		} catch (AMGException e) {

			log.error("Exception Occured While Migration Data", e);
			RequestContext.getCurrentInstance().execute("migrationexception.show();");
		}
	}
	/*
		if(( emosCustomer !=null && emosCustomer.equalsIgnoreCase("1")) && (verificationToken !=null)){

			updateVerificationToken(getPkCustomerId());
		}

		  getAMLCheckStatus_afterSave(getPkCustomerId());
		  // @@@@@@@@@ AML			
		  }*/
	//}


	//}

	public Boolean nextContactDetailsFromSmartCardInfo() {
		getContactTypeList();
		Boolean smartCardNext = false;
		
		try {
			if (getBooMobilecheck() == false && getBooEmailCheck() == false) {
				setBooRemitterInfo(false);
				setBooIdDetail(false);
				setBooCustomerDetails(false);
				setBooContactDetails(true);
				setBooEmploymentDetails(false);
				setBooProof(false);
				setContactlistcheck(false);
				setRenderSavebutton(false);
				setRenderSignature(false);
				setBooVerifyAllRen(false);
				setRenderverication(false);
				setSignaturePanelRender(false);
				setRenderUpdate(false);
				setRenderRegistration(false);
				setRenderVerifyReport(false);
				setRenderFinal(false);
				setExitRender(false);
				setNextSignaturePanel(false);
				setNextUpdateRender(false);
				setRenderIntroducePanel(false);
				setBooManualGo(false);
				if(contactList.size()>0){
					setBooContactDetailsButtonPanel(true);
				}else{
					setBooContactDetailsButtonPanel(false);
				}
				smartCardNext = true;
			}	
		} catch (Exception e) {
			System.out.println(e.getMessage());
			smartCardNext = false;
		}
		return smartCardNext;
	}

	// for implement Digital Signature

	private String digitalSignature;
	//	private String signatureSpecimen;
	private Boolean signaturePanelRender = false;

	public String getDigitalSignature() {
		return digitalSignature;
	}

	public void setDigitalSignature(String digitalSignature) {
		this.digitalSignature = digitalSignature;
	}

	/*public String getSignatureSpecimen() {
		return signatureSpecimen;
	}

	public void setSignatureSpecimen(String signatureSpecimen) {
		this.signatureSpecimen = signatureSpecimen;
	}*/

	public Boolean getSignaturePanelRender() {
		return signaturePanelRender;
	}

	public void setSignaturePanelRender(Boolean signaturePanelRender) {
		this.signaturePanelRender = signaturePanelRender;
	}

	public String smartCardDisplay(String host, String prdPort,String requestType, String env) throws ParseException {
		StringBuffer sb = new StringBuffer();
		StringBuffer urlBuffer = new StringBuffer();
		String appender = "?";
		String ampersand = "&";
		String equals = "=";
		String colon = ":";
		String rootContext = "/KwtSmartCard/SmartCartServ"; // KwtSmartCard/smartcard
		if (env.equalsIgnoreCase("test")) {
			urlBuffer.append("http://").append(host).append(colon)
			.append(prdPort).append(rootContext).append(appender);
		} else if (env.equalsIgnoreCase("live")) {
			urlBuffer.append("https://").append(host);
			if (prdPort != null && prdPort.length() > 0) {
				urlBuffer.append(colon).append(prdPort);
			}
			urlBuffer.append(rootContext).append(appender);
		}
		urlBuffer.append("type").append(equals).append("M").append(ampersand);

		try {
			URL knetRequest = new URL(urlBuffer.toString());
			HttpURLConnection testyc = null;
			HttpsURLConnection prdyc = null;
			BufferedReader in = null;
			if (env.equalsIgnoreCase("test")) {
				testyc = (HttpURLConnection) knetRequest.openConnection();
				in = new BufferedReader(new InputStreamReader(
						testyc.getInputStream()));
			} else if (env.equalsIgnoreCase("live")) {
				prdyc = (HttpsURLConnection) knetRequest.openConnection();
				in = new BufferedReader(new InputStreamReader(
						prdyc.getInputStream()));
			}
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine + "##");
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] str = sb.toString().split("#");

		if (str.length > 1) {


			for (int i = 0; i < str.length; i++) {
				String string = str[i];
				// System.out.println("str :"+string);

				if (i == 0) {
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setFullNameAr(part2);

				}

				if (i == 1) {
					System.out.println("Arabic First Name");

					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setFirstNameAr(part2);

				}

				if (i == 2) {
					System.out.println("Arabic Father Name ");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setFatherNameArabic(part2);

				}

				if (i == 3) {
					System.out.println("GF name is ");
					// String string = tag[i];
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setArabicGFName(part2);

				}

				if (i == 4) {
					System.out.println("Arabic surname");
					// String string = tag[i];
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setArabicSurname(part2);

				}

				if (i == 5) {
					System.out.println("Nationality in Arabic");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setNationalityAr(part2);
				}

				if (i == 6) {
					System.out.println("Gender in Arabic");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setGenderAr(part2);

				}

				if (i == 7) {
					System.out.println("name is ");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					System.out.println(part2);
					setFullNameEn(part2);

				}

				if (i == 8) {
					System.out.println("Civil Id ");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setCivilId(part2);

				}

				if (i == 9) {
					System.out.println("Gender ");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					System.out.println(part2);
					setGenderLatin(part2);

					if (part2.equalsIgnoreCase("F")) {
						setTitle(getGeneralService().getComponentId(Constants.TITLE_FOR_MRS_NAME, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString());
						setTitleEn(Constants.TITLE_FOR_MRS_NAME);
						setTitlel(Constants.LOCAL_TITLE_FOR_MRS);
						if(sessionStateManage.getLanguageId().toString().equalsIgnoreCase(Constants.ENGLISH_LANGUAGE_ID)){
							setGender(Constants.GENDER_FEMALE);
						}else{
							setGender(Constants.GENDER_FEMALE_ARABIC);
						}
					} else {
						setTitle(getGeneralService().getComponentId(Constants.TITLE_FOR_MR_NAME, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString());
						setTitleEn(Constants.TITLE_FOR_MR_NAME);
						setTitlel(Constants.LOCAL_TITLE_FOR_MR);
						if(sessionStateManage.getLanguageId().toString().equalsIgnoreCase(Constants.ENGLISH_LANGUAGE_ID)){
							setGender(Constants.GENDER_MALE);
						}else{
							setGender(Constants.GENDER_MALE_ARABIC);
						}
					}

				}

				if (i == 10) {
					System.out.println("Nationality is ");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					System.out.println(part2);
					setNationalityLatin(part2);

				}

				if (i == 11) {
					System.out.println("Birthday ");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					Date birthDate = new SimpleDateFormat("yyyy/MM/dd")
					.parse(part2);
					setBirthdate(new SimpleDateFormat("dd/MM/yyyy")
					.format(birthDate));

				}

				if (i == 12) {
					System.out.println("Issuing Date");

					String[] parts = string.split("=");
					String part2 = parts[1];
					Date issueDate = new SimpleDateFormat("yyyy/MM/dd")
					.parse(part2);
					setIssuedate(new SimpleDateFormat("dd/MM/yyyy")
					.format(issueDate));

				}
				if (i == 13) {
					System.out.println("Expiry Date");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					Date expDate = new SimpleDateFormat("yyyy/MM/dd")
					.parse(part2);
					setExpirydate(new SimpleDateFormat("dd/MM/yyyy")
					.format(expDate));

				}

				if (i == 14) {
					System.out.println("Document No");

					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setDocumentno(part2);

				}

				if (i == 15) {
					System.out.println("Serial No");

					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setSerialno(part2);

				}

				if (i == 16) {
					System.out.println("MOI Reference  No");

					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setMoireference(part2);

				}

				if (i == 17) {
					System.out.println("MOI Reference  Indicator");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					System.out.println(part2);

				}

				if (i == 18) {
					System.out.println("District in Arabic");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setScarddistrict(part2);

				}

				if (i == 19) {
					System.out.println("Block is ");

					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setsCardblock(part2);

				}

				if (i == 20) {
					System.out.println("street in Arabic");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setScardstreet(part2);

				}

				if (i == 21) {
					System.out.println("Building No ");

					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setBuilding_no(part2);

				}

				if (i == 22) {
					System.out.println("Unit type in Arabic");
					// String string = tag[i];
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setUnit_type(part2);

				}

				if (i == 23) {
					System.out.println("Unit Number is ");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setUnit_no(part2);

				}

				if (i == 24) {
					System.out.println("Unit Number is ");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setScardfloor(part2);

				}

				if (i == 25) {
					System.out.println("Blood type is ");

					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setBloodType(part2);

				}

				if (i == 26) {
					System.out.println("guardian civil id ");

					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];

					setGuardianCivilId(part2);

				}

				if (i == 27) {
					System.out.println("Telephone1 is ");

					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setTelephone1(part2);

				}

				if (i == 28) {
					System.out.println("telephone 2 ");

					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setTelephone2(part2);

				}

				if (i == 29) {
					System.out.println("email ");

					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setScardemail(part2);

				}

				if (i == 30) {
					System.out.println("Arabic Additional field is ");
					// String string = tag[i];
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					
					  String[] articleDetails = part2.split("/");
				      String[] aricleid = articleDetails[1].split(" ");
				      setAdditinal1(aricleid[2]);
					System.out.println("ARTICLE SMART CARD"+aricleid[2]);
					getArticleDataFromCard();

				}

				if (i == 31) {
					System.out.println("Arabic Additional field2 is ");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setEmployer(part2);
					setAdditinal2(part2);
					System.out.println("Employer        == "+part2);
				}

				if (i == 32) {
					System.out.println("address ref ");

					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setAddressRef(part2);

				}

			}
		} else {

			setBooCustomerDetails(false);
			setBooIdDetail(true);
			setBooManual(false);
			setBoofirstPanel(false);
			setBooRemitterInfo(false);
			setBahrainCardPanel(false);

			RequestContext.getCurrentInstance().execute("dldInserCard.show();");

		}

		return sb.toString();
	}

	public void SmartCardClient() {
	}

	private List<CountryMaster> countryAlphaList = new ArrayList<CountryMaster>();
	private List<DmsMas> smartCardInfoList = new ArrayList<DmsMas>();
	//private Constants constants = new Constants();
	private Boolean renderSavebutton;
	private Boolean renderSignature;
	private Boolean renderUpdate = false;
	private Boolean renderRegistration = false;

	public Boolean getRenderUpdate() {
		return renderUpdate;
	}

	public void setRenderUpdate(Boolean renderUpdate) {
		this.renderUpdate = renderUpdate;
	}

	public Boolean getRenderRegistration() {
		return renderRegistration;
	}

	public void setRenderRegistration(Boolean renderRegistration) {
		this.renderRegistration = renderRegistration;
	}

	public Boolean getRenderSavebutton() {
		return renderSavebutton;
	}

	public void setRenderSavebutton(Boolean renderSavebutton) {
		this.renderSavebutton = renderSavebutton;
	}

	public Boolean getRenderSignature() {
		return renderSignature;
	}

	public void setRenderSignature(Boolean renderSignature) {
		this.renderSignature = renderSignature;
	}

	public void getNationalityAlphacode() {

		countryAlphaList = icustomerRegistrationService
				.getNationalityAlphaCode(getNationalityLatin());

		for (CountryMaster countryList : countryAlphaList) {

			setNationality(countryList.getCountryId());
			System.out.println("KKKKKKKKKKKK" + countryList.getCountryId());

		}

	}

	public void nextDigitalSignature() {

		setBooRemitterInfo(false);
		setBooIdDetail(false);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooProof(false);
		setBooEmploymentDetails(false);
		setBooContactDetailsDuplicate(false);
		setRenderSavebutton(false);
		setRenderSignature(true);
		setBooVerifyAllRen(false);
		setRenderSavebutton(true);
		setSignaturePanelRender(true);
		setRenderFinal(false);
		setRenderVerifyReport(false);
		setNextUpdateRender(false);

	}

	public void backFromDigitalSignature() {

		setBooRemitterInfo(false);
		setBooIdDetail(false);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooProof(true);
		setBooEmploymentDetails(false);
		setBooContactDetailsDuplicate(false);
		setRenderSavebutton(false);
		setRenderSignature(false);
		setBooVerifyAllRen(false);
		setSignaturePanelRender(false);
		setRenderFinal(false);
		setRenderVerifyReport(false);
		setNextUpdateRender(false);

	}

	public void clearSmartCardInfo() {
		setSmartCardId(null);
		setCivilId(null);
		setSerialno(null);
		setDocumentno(null);
		setFullNameEn(null);
		setFullNameAr(null);
		setFirstNameEn(null);
		setFirstNameAr(null);
		setArabicSurname(null);
		setFatherNameArabic(null);
		setArabicGFName(null);
		setNationalityLatin(null);
		setNationalityAr(null);
		setGenderLatin(null);
		setGenderAr(null);
		setIssuedate(null);
		setExpirydate(null);
		setBirthdate(null);
		setTelephone1(null);
		setTelephone2(null);
		setScardemail(null);
		setBloodType(null);
		setBuilding_no(null);
		setUnit_type(null);
		setUnit_no(null);
		setScardfloor(null);
		setStreet(null);
		setScarddistrict(null);
		setsCardblock(null);
		setGuardianCivilId(null);
		setAdditinal1(null);
		setAdditinal2(null);
		setAddressRef(null);
		setPkCustomerId(null);

	}

	public void setCustomerDetailsForSmardCardCustomer() {

		getArticleData();
		customerList = getIcustomerRegistrationService().getCustomerInfo(
				getPkCustomerId());

		setNationality(customerList.get(0).getFsCountryMasterByNationality()
				.getCountryId());

		setCreatedByCustomer(customerList.get(0).getCreatedBy());
		setCreationDateCustomer(customerList.get(0).getCreationDate());
		setCraetedDateforCustomer(new SimpleDateFormat("dd/MM/yyyy").format(customerList.get(0).getCreationDate()));

		if (customerList.get(0).getFsArticleDetails()!= null) {

			setArticle(customerList.get(0).getFsArticleDetails()
					.getFsArticleMaster().getArticleId());
			generateLevel();
			setLevel(customerList.get(0).getFsArticleDetails()
					.getArticleDetailId());
			generateIncomeRange();
			setIncomeRange(customerList.get(0).getFsIncomeRangeMaster()
					.getIncomeRangeId());

			/*setDailyLimit(customerList.get(0).getDailyLimit());
			setWeeklyLimit(customerList.get(0).getWeeklyLimit());
			setMonthlyLimit(customerList.get(0).getMontlyLimit());
			setQuarterlyLimit(customerList.get(0).getQuaterlyLimit());
			setHalfyearly(customerList.get(0).getHalfYearly());
			setAnnualLimit(customerList.get(0).getAnnualLimit());*/
		}
		setFirstName(customerList.get(0).getFirstName());
		setMiddleName(customerList.get(0).getMiddleName());
		setLastName(customerList.get(0).getLastName());
		setShortName(customerList.get(0).getLastName());

		setFirstNamel(customerList.get(0).getFirstNameLocal());
		setMiddleNamel(customerList.get(0).getMiddleNameLocal());
		setLastNamel(customerList.get(0).getLastNameLocal());
		setShortNamel(customerList.get(0).getShortNameLocal());
		setDigitalSignature(customerList.get(0).getSignatureSpecimen());
		setTelephone1(customerList.get(0).getMobile());
		setScardemail(customerList.get(0).getEmail());
		setActiveInd(customerList.get(0).getActivatedInd());
		setEmosCustomer(customerList.get(0).getEmosCustomer());
		if(customerList.get(0).getCustomerReference()!=null){
			setUpdateCustomerRefNo(customerList.get(0).getCustomerReference());
		}

		if(customerList.get(0).getPepsIndicator()!=null){
			if (customerList.get(0).getPepsIndicator().equalsIgnoreCase(Constants.Yes)) {
				setPepsindicator(Constants.Yes);// Added by Nazish on 11-Feb 2015
			} else {
				setPepsindicator(Constants.No);
			}
		}
		if(customerList.get(0).getIntroducedBy()!=null){
			setIntroduceBy(new BigDecimal(customerList.get(0).getIntroducedBy()));
		}
		fetchIntroducer();
		if(customerList.get(0).getMedicalInsuranceInd()!=null){
			if (customerList.get(0).getMedicalInsuranceInd().equalsIgnoreCase(Constants.Yes)) {
				setMedicalInsuranceInd(Constants.Yes);// Added by Nazish on 11-Feb 2015
			} else {
				setMedicalInsuranceInd(Constants.No);
			}
		}

		checkPepLink();

	}


	public void fetchSmartCardData() throws ParseException, DOMException, ParserConfigurationException, SAXException, IOException {

		clear();

		if(sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)){

			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}

			//smartCardDisplay(ipAddress, "8085", "M", "test");
			smartCardDisplay("10.200.4.69", "8085", "M", "test");

			SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

			Calendar cal1 = Calendar.getInstance();
			cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
			Date today18 = cal1.getTime();
			if (dateformat.parse(getBirthdate()).before(today18)) {

				setBooCivilId(true);
				setBooOtherId(false);
				setIdNumber(getCivilId());

				mapIdentityType = ruleEngine.getComponentData("Identity Type");

				BigDecimal identityTpeId = null;
				for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
					if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
						identityTpeId = entry.getKey();
						break;
					}
				}

				setIdType(identityTpeId.toString());
				List<String> outprocedurevalues = new ArrayList<String>();

				try {
					outprocedurevalues = icustomerRegistrationService.getCustomerRefOrSave(identityTpeId, getCivilId(), Constants.Individual);

					if(outprocedurevalues.size()!=0){
						setAllow_Ind(outprocedurevalues.get(0));
						setCustRefNo((outprocedurevalues.get(1)) == null ? "" : (outprocedurevalues.get(1)));

						if(getAllow_Ind().equalsIgnoreCase(Constants.Yes)){
							try {

								setBooRemitterInfo(false);
								setBooManual(false);
								setBoosmartCardAppear(true);

								setBooIdDetail(false);

								setBooContactDetails(false);
								setBooEmploymentDetails(false);
								setBooProof(false);
								setRenderSignature(false);
								setRenderverication(false);
								setSignaturePanelRender(false);
								setRenderVerifyReport(false);
								setRenderFinal(false);
								setNextSignaturePanel(false);
								setNextUpdateRender(false);
								setRenderIntroducePanel(false);
								setBooManualGo(false);
								setBooCustomerDetails(true);
								setBahrainCardPanel(false);

								

								smartCardInfoList = icustomerRegistrationService
										.getSmartCardData(getCivilId());

								if (smartCardInfoList.size() > 0) {

									setCivilId(smartCardInfoList.get(0).getIdno());
									setSerialno(smartCardInfoList.get(0).getSerialNo());
									setDocumentno(smartCardInfoList.get(0).getDocumentNo());
									setFullNameEn(smartCardInfoList.get(0).getEFuname());
									setFullNameAr(smartCardInfoList.get(0).getLFuname());
									setFirstNameEn(smartCardInfoList.get(0).getEFirstName());
									setFirstNameAr(smartCardInfoList.get(0).getLFstName());
									setArabicSurname(smartCardInfoList.get(0).getLSurname());
									setFatherNameArabic(smartCardInfoList.get(0).getLFatherName());
									setArabicGFName(smartCardInfoList.get(0).getLGfName());
									setNationalityLatin(smartCardInfoList.get(0).getNtcdId());
									//setNationalityAr(smartCardInfoList.get(0).getNationalityAr());
									setGenderLatin(smartCardInfoList.get(0).getGender());

									if (smartCardInfoList.get(0).getGender().equalsIgnoreCase("F")) {
										setTitle(getGeneralService().getComponentId(Constants.TITLE_FOR_MRS_NAME, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString());
										setTitleEn(Constants.TITLE_FOR_MRS_NAME);
										setTitlel(Constants.LOCAL_TITLE_FOR_MRS);
										if(sessionStateManage.getLanguageId().toString().equalsIgnoreCase(Constants.ENGLISH_LANGUAGE_ID)){
											setGender(Constants.GENDER_FEMALE);
										}else{
											setGender(Constants.GENDER_FEMALE_ARABIC);
										}
									} else {
										setTitle(getGeneralService().getComponentId(Constants.TITLE_FOR_MR_NAME, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString());
										setTitleEn(Constants.TITLE_FOR_MR_NAME);
										setTitlel(Constants.LOCAL_TITLE_FOR_MR);
										if(sessionStateManage.getLanguageId().toString().equalsIgnoreCase(Constants.ENGLISH_LANGUAGE_ID)){
											setGender(Constants.GENDER_MALE);
										}else{
											setGender(Constants.GENDER_MALE_ARABIC);
										}
									}

									setBirthdate(new SimpleDateFormat("dd/MM/yyyy").format(smartCardInfoList.get(0).getBirthdat()));
									//setTelephone1(smartCardInfoList.get(0).getTel1());
									setTelephone2(smartCardInfoList.get(0).getTel2());
									//setScardemail(smartCardInfoList.get(0).getEmailId());
									setBloodType(smartCardInfoList.get(0).getBloodTyp());
									setBuilding_no(smartCardInfoList.get(0).getBldgno());


									setScardfloor(smartCardInfoList.get(0).getFloorno());
									setScardstreet(smartCardInfoList.get(0).getStreet());
									setScarddistrict(smartCardInfoList.get(0).getDistrict());
									setsCardblock(smartCardInfoList.get(0).getBlockNo());
									setGuardianCivilId(smartCardInfoList.get(0).getGuardIdno());

									setAddressRef(smartCardInfoList.get(0).getAddrUniqKey());

									setCreatedByCustomer(smartCardInfoList.get(0).getCreator());
									setCreationDateCustomer(smartCardInfoList.get(0).getCrtdat());
									setSmartCardId(smartCardInfoList.get(0).getDmsIdMasId());
								}


								BigDecimal idtypeCivilIdnew = getGeneralService().getComponentId(Constants.CIVIL_ID_NEW,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
								if(getIdType()!=null && idtypeCivilIdnew!=null && getIdType().equalsIgnoreCase(identityTpeId.toString()) || getIdType().equalsIgnoreCase(idtypeCivilIdnew.toString())){
									customerIdProofList = getIcustomerRegistrationService().getCustomerIdProofCheck(identityTpeId,getIdNumber(),session.getCountryId());

									if (customerIdProofList.size() == 0) {
										customerIdProofList = getIcustomerRegistrationService().getCustomerIdProofCheck(idtypeCivilIdnew,getIdNumber(),session.getCountryId());
									}

								}
								/*	
									customerIdProofList = getIcustomerRegistrationService()
											.getCustomerIdProofCheck(identityTpeId,getCivilId(),sessionStateManage.getCountryId());*/

								if (customerIdProofList.size() > 0) {

									setPkCustomerId(customerIdProofList.get(0).getFsCustomer().getCustomerId());				

									setBooCustomerDetails(true);

									setCustomerIdProof();
									setCustomerDetailsForSmardCardCustomer();

									setContactDetails();
									setDepenedentDetails();
									setEmployeementInfo();
									setSignaturePanelRender(false);

								} else {

									//getDealYear();
									//getDocument();
									//getDocumentSerialID(processIn);
									getFetchContactTypeList();

									getNationalityAlphacode();
									setChangeDobPass(true);

									mapIdentityType = ruleEngine.getComponentData("Identity Type");           
									BigDecimal idTypeproof = null;
									for (Map.Entry<BigDecimal, String> entry : mapIdentityType
											.entrySet()) {
										if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
											idTypeproof = entry.getKey();
											break;
										}
									}
									setIdTypeproof(idTypeproof);
									setIdType(idTypeproof.toString());
									setDateExp(new SimpleDateFormat("dd/MM/yyyy")
									.parse(getExpirydate()));
									System.out.println("====civil id" + idTypeproof);
									setIdnumberProof(getCivilId());
									setReadOnlyIdNumber(true);
								}


							} catch (ParseException e) {
								log.info("parser excetion"+e);
							}

							catch(NullPointerException ne){
								log.info("Null pointer exception occured"+ne);
								setBooCustomerDetails(false);
								setBooIdDetail(true);
								setBooManual(false);
								setBoofirstPanel(false);
								setBooRemitterInfo(false);
								setBahrainCardPanel(false);
								RequestContext.getCurrentInstance().execute("exceptionfetching.show();");

							}
						}else{

							setBooRemitterInfo(false);
							setBooManual(false);
							setBoosmartCardAppear(true);

							setBooIdDetail(true);

							setBooContactDetails(false);
							setBooEmploymentDetails(false);
							setBooProof(false);
							setRenderSignature(false);
							setRenderverication(false);
							setSignaturePanelRender(false);
							setRenderVerifyReport(false);
							setRenderFinal(false);
							setNextSignaturePanel(false);
							setNextUpdateRender(false);
							setRenderIntroducePanel(false);
							setBooManualGo(false);
							setBooCustomerDetails(false);
							setBahrainCardPanel(false);
							RequestContext.getCurrentInstance().execute("printCustomerReference.show();");
						}

					}

				} catch (AMGException e) {
					CollectionUtil collUtil = new CollectionUtil();
					//setExceptionMessage(collUtil.formatErrorMessage(e.getMessage()));

					//System.out.println("*******Error message ********"+getExceptionMessage());
					//Added by kani end

					RequestContext.getCurrentInstance().execute("sqlexception.show();");
				}
			} else{
				RequestContext.getCurrentInstance().execute("dobcheck.show();");  
			}

		}else if(sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.BAHRAIN_ALPHA_TWO_CODE)){
			//fetchBahrainSmartCardDetails();
		}
	}


	public void clickOnSmartCard() throws IOException {

		setBooRemitterInfo(false);
		setBooIdDetail(true);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooProof(false);
		setBooEmploymentDetails(false);
		setBooContactDetailsDuplicate(false);
		setRenderSavebutton(false);
		setRenderSignature(false);
		setBooVerifyAllRen(false);
		setRenderSavebutton(false);
		setSignaturePanelRender(false);
		setRenderFinal(false);
		setRenderVerifyReport(false);
		setNextUpdateRender(false);
		setBooManual(false);
		setBoosmartCardAppear(false);
		setBahrainCardPanel(false);
		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("customerregistrationbranch.xhtml");

	}

	private final String CUST_REG_RPT_FILE = Constants.CUSTOMER_REG_RPT_FILE;
	private final String CUST_LOG_RPT_FILE = Constants.CUSTOMER_LOG_DIFF_RPT_FILE;
	/*
	@Override
	protected String getCompileFileName() {
		List<Customer> custlist = new ArrayList<Customer>();
		String verificationToken = null;
		String pepsReportStatus =null;
		custlist = getIcustomerRegistrationService().getVerificationToken(
				getPkCustomerId());
		if (custlist.size() != 0) {
			verificationToken = custlist.get(0).getVerificationTokenId();
		}

		System.out.println("verificationToken======================="
				+ verificationToken);

		if (verificationToken == null) {
			return CUST_REG_RPT_FILE;

		} else {
			return CUST_LOG_RPT_FILE;
		}
	}

	@Override
	protected Map<String, Object> getReportParameters() {
		Map<String, Object> reportParameters = new HashMap<String, Object>();
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext();

		String realPath = ctx.getRealPath("//");
		String logoPath = realPath + Constants.RPT_IMAGE_PATH;
		String reportPath = realPath + Constants.RPT_PATH;
		String SUBREPORT_DIR = realPath + Constants.RPT_PATH;

		System.out.println("customerif" + getPkCustomerId());
		System.out.println("logoPath" + logoPath);
		System.out.println("reportPath" + reportPath);
		System.out.println("SUBREPORT_DIR" + SUBREPORT_DIR);

		reportParameters.put("customerId", getPkCustomerId());
		reportParameters.put("logoPath", logoPath);
		reportParameters.put("reportPath", reportPath);
		reportParameters.put("SUBREPORT_DIR", SUBREPORT_DIR);

		return reportParameters;
	}

	public void reportexecute() {
		try {

			super.prepareReport();

		} catch (Exception e) {
			// make your own exception handling
			e.printStackTrace();
		}
	}
	 */
	public String getVerifyToken() {
		return verifyToken;
	}

	public void setVerifyToken(String verifyToken) {
		this.verifyToken = verifyToken;
	}

	public void tokenVerify() {
		String verificationToken = null;
		String token = getVerifyToken();
		List<Customer> custlist = new ArrayList<Customer>();
		custlist = getIcustomerRegistrationService().getVerificationToken(getPkCustomerId());
		if (custlist.size() != 0) {
			verificationToken = custlist.get(0).getVerificationTokenId();
		}

		if (verificationToken.equals(token)) {
			// RequestContext.getCurrentInstance().execute("validToken.show();");
			setSignaturePanelRender(true);
			setSignatureCaptureRender(true);
			setSignatureSpecimenRender(true);
			setRenderSignature(false);
			setRenderUpdate(false);
			setRenderRegistration(false);
			setRenderFinal(true);
			setRenderVerifyReport(true);
			setExitRender(true);
			setBackFinalRender(false);
			setSubmitRender(false);
			//Viswa@@@
			//setNextSignaturePanel(false);

			setSuccessPanel(false);
			setUpdatePanel(false);
			setNextUpdateRender(true);
		} else {
			RequestContext.getCurrentInstance().execute("invalidToken.show();");
		}

	}

	public void successRegistration() throws IOException {
		// RequestContext.getCurrentInstance().execute("success.show();");
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		context.redirect("../registration/customerregistrationbranch.xhtml");
	}

	private Boolean renderverication = false;
	private String idNumberverification;
	private String tokenNumber;

	public Boolean getRenderverication() {
		return renderverication;
	}

	public void setRenderverication(Boolean renderverication) {
		this.renderverication = renderverication;
	}

	public String getIdNumberverification() {
		return idNumberverification;
	}

	public void setIdNumberverification(String idNumberverification) {
		this.idNumberverification = idNumberverification;
	}

	public String getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public void fetchCustomerInfoVerification() {

		System.out.println("VALIDITY IS lANGUAGE ###########"
				+ sessionStateManage.getLanguageId());
		System.out.println("VALIDITY IS COUNTRY ###########"
				+ sessionStateManage.getCountryId());
		System.out.println("VALIDITY IS IDTYPE ###########" + getIdType());

		String validity = generalService.getValidity(sessionStateManage.getCountryId(), new BigDecimal(getIdType()));

		System.out.println("VALIDITY IS ###########" + validity);

		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, new Integer(validity));
		Date today90 = cal.getTime();
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
		String finalDate = form.format(today90);
		setExpDateCheck(finalDate);
		String finalsDate = form.format(new Date());
		setMinDob(finalsDate);
		// setBooDobInput(true);
		// setBooDobCalender(false);

		try {
			customerIdProofList = getBranchpageService().getCustomerIdProof(
					getIdNumberverification());
			if (customerIdProofList.size() > 0) {
				// Means if image id will be null, Approve all will be disable,
				// so disable true, 100 means no image
				/*if (customerIdProofList.size() == 1
						&& customerIdProofList.get(0).getFsDocumentImg()
								.getImgId().intValue() == 100) {
					// setBooUploadedOrNot(true);
				} else {
					// setBooUploadedOrNot(false);
				}*/
				getFetchContactTypeList();
				customerList = getBranchpageService().getCustomerInfoWithToken(
						customerIdProofList.get(0).getFsCustomer()
						.getCustomerId(), getTokenNumber());
				if (customerList.size() > 0) {
					// if(customerList.get(0).getActivatedInd().equalsIgnoreCase("1"))
					// {
					// RequestContext.getCurrentInstance().execute("dlgApproved.show();");
					// } else {
					setPkCustomerId(customerIdProofList.get(0).getFsCustomer()
							.getCustomerId());
					setBooRemitterInfo(true);
					setBooIdDetail(false);
					setBooCustomerDetails(false);
					setBooContactDetails(false);
					setBooEmploymentDetails(false);
					setBooProof(false);
					setContactlistcheck(false);
					setRenderSavebutton(false);
					setRenderSignature(false);
					setBooVerifyAllRen(false);
					setRenderverication(false);
					setCustomerDetails();
					setCustomerIdProof();
					fillCountryList();
					fillStateList();
					fillDistrictList();
					fillCityList();
					setContactDetails();
					setEmployeementInfo();
					setSignaturePanelRender(false);
					setRenderFinal(false);
					setNextUpdateRender(false);
					setBooCivilId(true);
					setBooOtherId(false);
					setChangeDobPass(true);

					// }
				} else {
					// setRenderIdNotReg(true);
					RequestContext context = RequestContext
							.getCurrentInstance();
					context.execute("notMatched.show();");
				}
			} else {
				// setRenderIdNotReg(true);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("notMatched.show();");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Boolean getSignatureCaptureRender() {
		return signatureCaptureRender;
	}

	public void setSignatureCaptureRender(Boolean signatureCaptureRender) {
		this.signatureCaptureRender = signatureCaptureRender;
	}

	public Boolean getSignatureSpecimenRender() {
		return signatureSpecimenRender;
	}

	public void setSignatureSpecimenRender(Boolean signatureSpecimenRender) {
		this.signatureSpecimenRender = signatureSpecimenRender;
	}

	public Boolean getRenderFinal() {
		return renderFinal;
	}

	public void setRenderFinal(Boolean renderFinal) {
		this.renderFinal = renderFinal;
	}

	public Boolean getRenderVerifyReport() {
		return renderVerifyReport;
	}

	public void setRenderVerifyReport(Boolean renderVerifyReport) {
		this.renderVerifyReport = renderVerifyReport;
	}

	public Boolean getSubmitRender() {
		return submitRender;
	}

	public void setSubmitRender(Boolean submitRender) {
		this.submitRender = submitRender;
	}

	public Boolean getExitRender() {
		return exitRender;
	}

	public void setExitRender(Boolean exitRender) {
		this.exitRender = exitRender;
	}

	public Boolean getBackFinalRender() {
		return backFinalRender;
	}

	public void setBackFinalRender(Boolean backFinalRender) {
		this.backFinalRender = backFinalRender;
	}

	private List<Document> lstDocument = new ArrayList<Document>();
	List<UserFinancialYear> DealYearList = new ArrayList<UserFinancialYear>();
	private String processIn = "U";
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

	public String getDealYear() {
		return dealYear;
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

	public String getEmosCustomer() {
		return emosCustomer;
	}

	public void setEmosCustomer(String emosCustomer) {
		this.emosCustomer = emosCustomer;
	}

	/*public String getDocument() {

		lstDocument = generalService.getDocument(
				constants.DOCUMENT_CODE_FOR_CUSTOMER,
				sessionStateManage.getLanguageId());
		for (Document lstdoc : lstDocument) {
			setDocumentSerialityNo(lstdoc.getDocumentID());

		}
		return document;
	}*/

	/*public String getDocumentSerialID(String processIn) {

		String documentSerialID = generalService.getNextDocumentReferenceNumber(sessionStateManage.getCountryId().intValue(), sessionStateManage.getCompanyId().intValue(), getDocumentSerialityNo().intValue(), new BigDecimal(dealYear).intValue(),processIn);
		if(documentSerialID!=null){
			setCustomerRefNo(new BigDecimal(documentSerialID));
		}
		return documentSerialID;
	}*/

	/*public String getDealYear() throws ParseException {
		DealYearList = generalService.getDealYear(getCurrentTime());
		if (DealYearList.size() != 0) {

			dealYear = DealYearList.get(0).getFinancialYear().toString();

			setDealYear(dealYear);
		}
		return dealYear;
	}*/

	public void exit() throws IOException {
		//resetValues();
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	// added by kani begin

	public void getintrudecedby() {
		if (getIntroducedBy() != null && !getIntroducedBy().equalsIgnoreCase("")) {
			customerReferencaList = getIcustomerRegistrationService().getCustomerId(session.getCountryId(),getIntroducedBy());

			if (customerReferencaList.size() != 0) {

				customerIntroducedList = getIcustomerRegistrationService()
						.getCustomerInfo(
								customerReferencaList.get(0).getFsCustomer()
								.getCustomerId());

				setCustRefId(customerIntroducedList.get(0)
						.getCustomerReference());

			} else {
				setIntroducedBy(null);
				RequestContext.getCurrentInstance().execute(
						"civilexists.show();");
			}
		}
	}

	private BigDecimal custRefId;

	public BigDecimal getCustRefId() {
		return custRefId;
	}

	public void setCustRefId(BigDecimal custRefId) {
		this.custRefId = custRefId;
	}

	public Boolean getNextSignaturePanel() {
		return nextSignaturePanel;
	}

	public void setNextSignaturePanel(Boolean nextSignaturePanel) {
		this.nextSignaturePanel = nextSignaturePanel;
	}

	// added by kani End

	private Boolean successPanel = false;

	public Boolean getSuccessPanel() {
		return successPanel;
	}

	public void setSuccessPanel(Boolean successPanel) {
		this.successPanel = successPanel;
	}

	public void gotoHome() throws IOException {
		updateVerificationToken(getPkCustomerId());
		exit();
		setSuccessPanel(false);
	}

	/*public void generateReport() {
		if(getPkCustomerId()!=null){
			reportexecute();
			updateVerificationToken(getPkCustomerId());
		}
	}
	 */
	private Boolean booContactDetailsButtonPanel = false;

	public Boolean getBooContactDetailsButtonPanel() {
		return booContactDetailsButtonPanel;
	}

	public void setBooContactDetailsButtonPanel(
			Boolean booContactDetailsButtonPanel) {
		this.booContactDetailsButtonPanel = booContactDetailsButtonPanel;
	}

	private Boolean booRenderFinalSave = false;

	public Boolean getBooRenderFinalSave() {
		return booRenderFinalSave;
	}

	public void setBooRenderFinalSave(Boolean booRenderFinalSave) {
		this.booRenderFinalSave = booRenderFinalSave;
	}

	private Boolean updatePanel = false;

	public Boolean getUpdatePanel() {
		return updatePanel;
	}

	public void setUpdatePanel(Boolean updatePanel) {
		this.updatePanel = updatePanel;
	}

	// Verification token set to null
	public void updateVerificationToken(BigDecimal customerId) {

		icustomerRegistrationService.updateVerificationToken(customerId);




		/*
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			conn = DBConnection.getdataconnection();

			String query = "update FS_CUSTOMER set verification_token =? where customer_id = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setBigDecimal(1, null);
			pstmt.setBigDecimal(2, customerId);
			pstmt.executeUpdate();

		} catch (Exception e) {

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		 */}

	/*
	 * public void checkingIdType(){ if(getIdType().equalsIgnoreCase(getI))
	 * if(getIdType().equals(anObject)){
	 * 
	 * } }
	 */

	private String effectiveMinDate;

	public String getEffectiveMinDate() {
		return effectiveMinDate;
	}

	public void setEffectiveMinDate(String effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}


	public void editContactDetails(AddContactDetailBean bean){
		setContactTypeId(null);
		setLocalArea("");
		setCountryId(null);
		setStreet("");
		setStateId(null);
		setTelephone("");
		setDistrictId(null);
		setFlat("");
		setCityId(null);
		setBlock("");
		setBuildingNo(null);
		if(bean.getTel()!=null){
		setTelephone(bean.getTel());
		setTelephoneCode(bean.getTelephoneCode());
		setRenContactTel(true);
		setBooCheckTelContact(true);
		
		}
		setLocalArea(bean.getArea());
		setStreet(bean.getStreet());
		setPkCustomerContactDetails(null);


		setBlock(bean.getBlock());
		//added by nazish cr 14-JULY-2015
		if(bean.getMobileContact()!=null){
		setMobileContact(bean.getMobileContact());
		setTelephoneCode(bean.getTelephoneCode());
		setBooCheckMobileContact(true);
		setRenContactMobile(true);
		}
		setMsgMobileOrTel(false);

		setFlat(bean.getFlat());
		setContactTypeId(bean.getContactTypeId());
		setBuildingNo(bean.getBuildingNo());
		setPkCustomerContactDetails(bean.getContactDetailsId());
		fillCountryList();
		if(bean.getContactTypeId().intValue() == getGeneralService().getComponentId(Constants.PERMANENT,sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId().intValue()){//PK
			setCountryId(bean.getCountryId());
			setRenderHideCountryId(true);
		}else{
			setRenderHideCountryId(false);
			setCountryId(bean.getCountryId());
		}
		/*if(bean.getContactTypeId().intValue() == Constants.PERMANENT){
			setCountryId(bean.getCountryId());
			setRenderHideCountryId(true);
		}else{
			setRenderHideCountryId(false);
			setCountryId(bean.getCountryId());
		}*/
		popState();
		setStateId(bean.getStateId());
		popDistrict();
		setDistrictId(bean.getDistrictId());
		popCity();
		setCityId(bean.getCityId());
		contactList.remove(bean);

		/*
		if (bean.getContactDetailsId() != null) {
			//contactListDelete.add(bean);
			contactList.remove(bean);
		}else{
			contactList.remove(bean);
		}*/
		if(contactList.size()>0){
			setContactDataTable(true);
			setBooContactDetailsButtonPanel(true);
		}else{
			setContactDataTable(false);
			setBooContactDetailsButtonPanel(false);
		}

	}

	// added by Naizsh on 16-Feb-2015

	private String buildingNo = null;
	private String pepsindicator = null;
	private String introducedDate = null;

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

	public String getPepsindicator() {
		return pepsindicator;
	}

	public void setPepsindicator(String pepsindicator) {
		this.pepsindicator = pepsindicator;
	}

	public String getIntroducedDate() {
		return introducedDate;
	}

	public void setIntroducedDate(String introducedDate) {
		this.introducedDate = introducedDate;
	}

	/*public void selectIdentityType() {
		setCountryId(null);
		if (getContactTypeId().intValue() == Constants.PERMANENT) {
			setRenderHideCountryId(true);

		} else {

			setRenderHideCountryId(false);
			setCountryId(sessionStateManage.getCountryId());
			popState();
			getCountryNameListResident();
		}
	}*/


	public void selectIdentityType() {
		setCountryId(null);
		setMobileContact(null);
		setPlusSign(null);
		setTelephoneCode(null);
		//if (getContactTypeId().intValue() == Constants.PERMANENT) {
		if(getContactTypeId()!=null){
		if(getContactTypeId().intValue() == getGeneralService().getComponentId(Constants.PERMANENT,sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId().intValue()){
			setRenderHideCountryId(true);
			setCityRequired(false);
			setBooRendercityOptional(true);
			setBooRendercityRequired(false);
			setCityId(null);
			setCityName(null);
			setMobileContact(null);
			setCountryId(getNationality());
			popState();
			setRenContactMobile(false);
			setRenContactTel(false);
			setBooCheckMobileContact(false);
			setRenContactTel(false);
		} else {
			setBooRendercityOptional(false);
			setBooRendercityRequired(true);
			setCityRequired(true);
			setRenderHideCountryId(false);
			setRenContactMobile(true);
			setRenContactTel(false);
			setBooCheckMobileContact(true);
			setRenContactTel(false);
			setMobileContact(getMobile());
			setCountryId(sessionStateManage.getCountryId());
			popState();
			//getCountryNameListResident();
		}
		}else{
			setLstState(null);
			setStateId(null);
			setLstDistrict(null);
			setDistrictId(null);
			setLstCity(null);
			setCityId(null);
			setRenContactMobile(false);
			setRenContactTel(false);
			setBooCheckMobileContact(false);
			setRenContactTel(false);
		}
	}

	private Boolean booRendercityRequired = false;
	private Boolean booRendercityOptional = true;
	private Boolean cityRequired = false;

	public Boolean getCityRequired() {
		return cityRequired;
	}

	public void setCityRequired(Boolean cityRequired) {
		this.cityRequired = cityRequired;
	}

	public Boolean getBooRendercityRequired() {
		return booRendercityRequired;
	}

	public void setBooRendercityRequired(Boolean booRendercityRequired) {
		this.booRendercityRequired = booRendercityRequired;
	}

	public Boolean getBooRendercityOptional() {
		return booRendercityOptional;
	}

	public void setBooRendercityOptional(Boolean booRendercityOptional) {
		this.booRendercityOptional = booRendercityOptional;
	}


	/***
	 * 
	 * THIS METHOD WOILL RETURN AML STATUS 
	 * 
	 * PASS -NO OF HITS
	 * PENDING -NO OF HITS
	 * FAIL -NO OF HITS
	 * @throws Exception 
	 *  
	 * 
	 * 
	 * 
	 * **/


	/*//PEP Description Report Generation ---------------> RAHAMATHALI SHAIK

List<PepReport> pepReportList=new ArrayList<PepReport>();




public void pepreportGeneration(){
	pepReportList.clear();
	PepReport pepReport=new PepReport();
	pepReport.setCivilId(getIdNumber());
	pepReport.setCountry(specialCustomerDealRequestService.getBankCountryNameForUpdate(session.getCountryId()));
	pepReport.setCountryBranch(session.getLocation());
	ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	String realPath = ctx.getRealPath("//");
	String logoPath = realPath + Constants.LOGO_PATH;
	//String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath(Constants.LOGO_PATH); 
	pepReport.setLogoPath(logoPath);
	pepReport.setCustomerName(getFirstName());
	if(generalService.getValidExpiryDate(getIdNumber())!=null)
	{
		pepReport.setValidUpto(new SimpleDateFormat("dd/MM/yyyy").format(generalService.getValidExpiryDate(getIdNumber())));
	}

	pepReportList.add(pepReport);
}
	 */

	/*
JasperPrint jasperPrint;  
public void init() throws JRException{  
    JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(pepReportList);  
String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/PEPDeclarationReport.jasper");        
jasperPrint=JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);  
}
public void generateNewUpdatedReports(ActionEvent actionEvent) throws JRException, IOException{
    init();  
    HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
   httpServletResponse.addHeader("Content-disposition", "attachment; filename=PEPDeclarationReport.pdf");  
    ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
    JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
    FacesContext.getCurrentInstance().responseComplete();  

}*/

	private Boolean booPepDescriptionReport=false;
	private Boolean booPepDescriptionUpdateReport=false;
	private String expDateMax;
	private Boolean minagevalidation=false;
	private Date minDate = new Date();
	private Date imageUpdateDate;
	private String craetedDateforCustomer;



	public Boolean getBooPepDescriptionUpdateReport() {
		return booPepDescriptionUpdateReport;
	}

	public void setBooPepDescriptionUpdateReport(
			Boolean booPepDescriptionUpdateReport) {
		this.booPepDescriptionUpdateReport = booPepDescriptionUpdateReport;
	}

	public Boolean getBooPepDescriptionReport() {
		return booPepDescriptionReport;
	}

	public void setBooPepDescriptionReport(Boolean booPepDescriptionReport) {
		this.booPepDescriptionReport = booPepDescriptionReport;
	}

	public String getExpDateMax() {
		return expDateMax;
	}

	public void setExpDateMax(String expDateMax) {
		this.expDateMax = expDateMax;
	}

	public Boolean getMinagevalidation() {
		return minagevalidation;
	}

	public void setMinagevalidation(Boolean minagevalidation) {
		this.minagevalidation = minagevalidation;
	}



	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}



	public Date getImageUpdateDate() {
		return imageUpdateDate;
	}

	public void setImageUpdateDate(Date imageUpdateDate) {
		this.imageUpdateDate = imageUpdateDate;
	}


	public String getCraetedDateforCustomer() {
		return craetedDateforCustomer;
	}

	public void setCraetedDateforCustomer(String craetedDateforCustomer) {
		this.craetedDateforCustomer = craetedDateforCustomer;
	}

	public void ageValidation(SelectEvent e) {

		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
		Date today18 = cal1.getTime();
		SimpleDateFormat form1 = new SimpleDateFormat("dd/MM/yyyy");
		String minDateFinal = form1.format(today18);
		setEffectiveMinDate(minDateFinal);
		if (getDob().before(today18)) {
			setMinagevalidation(false);

		}else{
			setMinagevalidation(true);
			setDob(null);
		}
	}

	public void clickOnsignaute() throws IOException{

		setBooRemitterInfo(false);
		setBooIdDetail(false);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooProof(false);
		setBooEmploymentDetails(false);
		setRenderSavebutton(false);
		setBooVerifyAllRen(false);
		setRenderSignature(false);
		setRenderverication(false);
		setSignaturePanelRender(true);
		setRenderUpdate(false);
		setRenderRegistration(false);
		setRenderVerifyReport(false);
		setRenderFinal(false);
		setSuccessPanel(false);
		setUpdatePanel(false);
		setExitRender(false);
		setNextSignaturePanel(false);		
		setBooRenderFinalSave(true);
		setNextUpdateRender(false);
		FacesContext.getCurrentInstance().getExternalContext().redirect("customerregistrationbranch.xhtml");

	}


	public void fromSearchPage(String civil_id, BigDecimal referenceNo) {
		// setBooIdTypeCheck(false);
		setIntroduceBy(new BigDecimal(civil_id));
		setCustRefId(referenceNo);

		setBooRemitterInfo(true);
		setBooIdDetail(false);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooEmploymentDetails(false);
		setBooProof(false);
		setContactlistcheck(false);
		setRenderSavebutton(false);
		setRenderSignature(false);
		setBooVerifyAllRen(false);
		setRenderverication(false);
		setSignaturePanelRender(false);
		setRenderUpdate(false);
		setRenderRegistration(false);
		setRenderVerifyReport(false);
		setRenderFinal(false);
		setExitRender(false);
		setNextSignaturePanel(false);
		setNextUpdateRender(false);
	}


	//rahamathali code for search customer START
	private Boolean isFromCustomerRegistration=false;
	private Boolean boocustomerRegistration=false;




	public Boolean getBoocustomerRegistration() {
		return boocustomerRegistration;
	}

	public void setBoocustomerRegistration(Boolean boocustomerRegistration) {
		this.boocustomerRegistration = boocustomerRegistration;
	}

	public Boolean getIsFromCustomerRegistration() {
		return isFromCustomerRegistration;
	}

	public void setIsFromCustomerRegistration(Boolean isFromCustomerRegistration) {
		this.isFromCustomerRegistration = isFromCustomerRegistration;
	}

	public void searchClicked() {
		try {
			setIsFromCustomerRegistration(true);
			setBoocustomerRegistration(true);
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect("../search/searrchcustomer.xhtml");

		} catch (Exception e) {
			log.info("Problem to Redirect the page : " + e);
		}
	}

	//rahamathali code for search customer END

	public Boolean getNextUpdateRender() {
		return nextUpdateRender;
	}

	public void setNextUpdateRender(Boolean nextUpdateRender) {
		this.nextUpdateRender = nextUpdateRender;
	}

	private Boolean nextUpdateRender =false;

	public void nextUpdateResult(){
		setBooRemitterInfo(false);
		setBooIdDetail(false);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooProof(false);
		setBooEmploymentDetails(false);
		setRenderSavebutton(false);
		setBooVerifyAllRen(false);
		setRenderSignature(false);
		setRenderverication(false);
		setSignaturePanelRender(false);
		setRenderUpdate(false);
		setRenderRegistration(false);
		setRenderVerifyReport(false);
		setRenderFinal(false);
		setSuccessPanel(false);
		setUpdatePanel(false);
		setExitRender(false);
		setNextSignaturePanel(false);		
		setBooRenderFinalSave(false);
		setNextUpdateRender(false);
		setUpdatePanel(true);
		setRenderIntroducePanel(false);
		setBooManualGo(false);
	}

	public void exitFromUpdate() throws IOException {
		updateVerificationToken(getPkCustomerId());
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void backFromSignature(){

		setBooRemitterInfo(false);
		setBooIdDetail(false);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooEmploymentDetails(false);
		setBooProof(true);
		setContactlistcheck(false);
		setRenderSavebutton(false);

		setBooVerifyAllRen(false);
		setRenderverication(false);
		setSignaturePanelRender(false);
		setRenderUpdate(false);
		setRenderRegistration(false);
		setRenderVerifyReport(false);
		setRenderFinal(false);
		setExitRender(false);
		setNextSignaturePanel(false);
		//amlServiceSearch();
		clearContactDetail();
		setNextUpdateRender(false);

		setBooContactDetailsButtonPanel(false);
		setRenderIntroducePanel(false);
		setBooManualGo(false);
		setBooRenderFinalSave(false);
		setRenderSignature(true);
		setRenderModifyScan(false);
	}

	public void idInfoCard() {

		setBooRemitterInfo(false);
		setBooIdDetail(true);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooEmploymentDetails(false);
		setBooProof(false);
		setBooVerifyAllRen(false);

		setRenderUpdate(false);
		setRenderRegistration(false);
		setRenderVerifyReport(false);
		setRenderFinal(false);
		setExitRender(false);
		setNextSignaturePanel(false);
		// start by subramanian 10/02/2015
		setBooIdTypeCheck(false);
		setBooManual(false);
		// End by subramanian 10/02/2015
		setRenderIntroducePanel(false);
		setBooManualGo(false);
		setBoofirstPanel(false);
		setBahrainCardPanel(false);

	}

	private Boolean renderIntroducePanel= false;
	private Boolean booManualGo = false;
	private Boolean boofirstPanel = false;
	private Boolean bahrainCardPanel = false;

	public Boolean getRenderIntroducePanel() {
		return renderIntroducePanel;
	}

	public void setRenderIntroducePanel(Boolean renderIntroducePanel) {
		this.renderIntroducePanel = renderIntroducePanel;
	}

	public Boolean getBooManualGo() {
		return booManualGo;
	}

	public void setBooManualGo(Boolean booManualGo) {
		this.booManualGo = booManualGo;
	}


	public Boolean getBoofirstPanel() {
		return boofirstPanel;
	}

	public void setBoofirstPanel(Boolean boofirstPanel) {
		this.boofirstPanel = boofirstPanel;
	}



	public Boolean getBahrainCardPanel() {
		return bahrainCardPanel;
	}

	public void setBahrainCardPanel(Boolean bahrainCardPanel) {
		this.bahrainCardPanel = bahrainCardPanel;
	}

	public void fetchIntroducer(){

		List<CustomerIdProof> introducerList = icustomerRegistrationService.getCivilID(getIntroduceBy());
		if(introducerList.size()>0){
			setIntroducedBy(introducerList.get(0).getIdentityInt());
		}
	}


	//Bahrain Smart Card

	public void bahrainSmartCrad() throws ParserConfigurationException, SAXException, IOException, DOMException, ParseException{

		File fXmlFile = new File("C:/BahrainSmartCard/BAHRAIN_SMARTCARD.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		org.w3c.dom.Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("SmartcardData");
		//NodeList nList = doc.getElementsByTagName("AddressArabic");


		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node fstNode = nList.item(temp);
			if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement0 = (Element)fstNode;
				NodeList nList0 = eElement0.getElementsByTagName("IdNumber");

				NodeList nList1 = eElement0.getElementsByTagName("AddressEnglish");
				NodeList nList2 = eElement0.getElementsByTagName("ArabicFullName");


				NodeList nList3 = eElement0.getElementsByTagName("BirthDate");
				NodeList nList4 = eElement0.getElementsByTagName("CardCountry");
				NodeList nList5 = eElement0.getElementsByTagName("CardexpiryDate");

				NodeList nList6 = eElement0.getElementsByTagName("CardIssueDate");
				NodeList nList7 = doc.getElementsByTagName("NationalityCode");
				NodeList nList8 = doc.getElementsByTagName("PassportExpiryDate");


				NodeList nList9 = doc.getElementsByTagName("CardSerialNumber");
				NodeList nList10 = doc.getElementsByTagName("PassportNumber");
				NodeList nList11 = doc.getElementsByTagName("EnglishFullName");

				NodeList nList12 = doc.getElementsByTagName("FingerprintCode");
				NodeList nList13 = doc.getElementsByTagName("Gender");
				NodeList nList14 = doc.getElementsByTagName("IdNumber");
				NodeList nList15 = doc.getElementsByTagName("MiscellaneousTextData");
				NodeList nList16 = doc.getElementsByTagName("MiscellaneousTextData");

				Element eElement = (Element)nList0.item(0);



				Element eElement1 = (Element)nList1.item(0);
				Element eElement2 = (Element)nList2.item(0);

				Element eElement3 = (Element) nList3.item(0);
				Element eElement4 = (Element) nList4.item(0);
				Element eElement5 = (Element) nList5.item(0);

				Element eElement6 = (Element) nList6.item(0);
				Element eElement7 = (Element) nList7.item(0);
				Element eElement8 = (Element) nList8.item(0);

				Element eElement9 = (Element) nList9.item(0);
				Element eElement10 = (Element) nList10.item(0);
				Element eElement11 = (Element) nList11.item(0);

				Element eElement12 = (Element) nList12.item(0);
				Element eElement13 = (Element) nList13.item(0);
				Element eElement14 = (Element) nList14.item(0);
				Element eElement15 = (Element) nList15.item(0);

				setFullNamebah(eElement11.getFirstChild().getNodeValue());
				setFullNameArbah(eElement2.getFirstChild().getNodeValue());
				setGenderbh(eElement13.getFirstChild().getNodeValue());

				setNationalId(eElement.getFirstChild().getNodeValue());

				setBirthdatebh(eElement11.getFirstChild().getNodeValue());


				setIssuedatebah(eElement6.getFirstChild().getNodeValue());


				setExpirydatebah(eElement5.getFirstChild().getNodeValue());

				String[] addressDetails = eElement1.getFirstChild().getNodeValue().split(" ");
				String[] building = addressDetails[3].split("#:");
				String[] road = addressDetails[8].split(":");
				String[] block = addressDetails[12].split("#:");

				setBuilding_nobh(building[1]);
				setsCardblockbh(block[1]);
				setScardstreetbh(road[1]);

				setFlatbh(addressDetails[1]);
				setSerialnobahrain(eElement9.getFirstChild().getNodeValue());

				String[] nameDeatils = eElement11.getFirstChild().getNodeValue().split(" ");
				setFirstNamebah(nameDeatils[0]);
				setMiddnamebah(nameDeatils[1]+" "+nameDeatils[2]);
				setLastnamearbah(nameDeatils[3]);

				String[] nameDeatilsAr = eElement2.getFirstChild().getNodeValue().split(" ");
				setFirstnamelBah(nameDeatilsAr[0]);
				setMiddnameLbah(nameDeatilsAr[1]+" "+nameDeatilsAr[2]);
				setLastnamearbah(nameDeatilsAr[3]);

			}
		}
	}

	private String nationalId;
	private String serialnobahrain;
	private String fullNamebah;
	private String fullNameArbah;
	private String firstNamebah;
	private String middnamebah;
	private String lastnamebah;
	private String firstnamelBah;
	private String middnameLbah;
	private String lastnamearbah;
	private String issuedatebah;
	private String expirydatebah;
	private String birthdatebh;
	private String mobilebh;
	private String scardemailbh;
	private String bloodTypebh;
	private String building_nobh;
	private String flatbh;
	private String roadbh;
	private String scardstreetbh;
	private String sCardblockbh;
	private String countryofcard;
	private String genderbh;
	private String nationalitybah;
	private BigDecimal nationalityCodebah;
	private BigDecimal nationalityIdbah;


	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getSerialnobahrain() {
		return serialnobahrain;
	}

	public void setSerialnobahrain(String serialnobahrain) {
		this.serialnobahrain = serialnobahrain;
	}

	public String getFullNamebah() {
		return fullNamebah;
	}

	public void setFullNamebah(String fullNamebah) {
		this.fullNamebah = fullNamebah;
	}


	public String getFullNameArbah() {
		return fullNameArbah;
	}

	public void setFullNameArbah(String fullNameArbah) {
		this.fullNameArbah = fullNameArbah;
	}

	public String getFirstNamebah() {
		return firstNamebah;
	}

	public void setFirstNamebah(String firstNamebah) {
		this.firstNamebah = firstNamebah;
	}

	public String getMiddnamebah() {
		return middnamebah;
	}

	public void setMiddnamebah(String middnamebah) {
		this.middnamebah = middnamebah;
	}

	public String getFirstnamelBah() {
		return firstnamelBah;
	}

	public void setFirstnamelBah(String firstnamelBah) {
		this.firstnamelBah = firstnamelBah;
	}

	public String getMiddnameLbah() {
		return middnameLbah;
	}

	public void setMiddnameLbah(String middnameLbah) {
		this.middnameLbah = middnameLbah;
	}

	public String getIssuedatebah() {
		return issuedatebah;
	}

	public void setIssuedatebah(String issuedatebah) {
		this.issuedatebah = issuedatebah;
	}

	public String getExpirydatebah() {
		return expirydatebah;
	}

	public void setExpirydatebah(String expirydatebah) {
		this.expirydatebah = expirydatebah;
	}

	public String getBirthdatebh() {
		return birthdatebh;
	}

	public void setBirthdatebh(String birthdatebh) {
		this.birthdatebh = birthdatebh;
	}

	public String getMobilebh() {
		return mobilebh;
	}

	public void setMobilebh(String mobilebh) {
		this.mobilebh = mobilebh;
	}

	public String getScardemailbh() {
		return scardemailbh;
	}

	public void setScardemailbh(String scardemailbh) {
		this.scardemailbh = scardemailbh;
	}

	public String getBloodTypebh() {
		return bloodTypebh;
	}

	public void setBloodTypebh(String bloodTypebh) {
		this.bloodTypebh = bloodTypebh;
	}

	public String getBuilding_nobh() {
		return building_nobh;
	}

	public void setBuilding_nobh(String building_nobh) {
		this.building_nobh = building_nobh;
	}

	public String getFlatbh() {
		return flatbh;
	}

	public void setFlatbh(String flatbh) {
		this.flatbh = flatbh;
	}

	public String getRoadbh() {
		return roadbh;
	}

	public void setRoadbh(String roadbh) {
		this.roadbh = roadbh;
	}

	public String getScardstreetbh() {
		return scardstreetbh;
	}

	public void setScardstreetbh(String scardstreetbh) {
		this.scardstreetbh = scardstreetbh;
	}

	public String getsCardblockbh() {
		return sCardblockbh;
	}

	public void setsCardblockbh(String sCardblockbh) {
		this.sCardblockbh = sCardblockbh;
	}

	public String getCountryofcard() {
		return countryofcard;
	}

	public void setCountryofcard(String countryofcard) {
		this.countryofcard = countryofcard;
	}



	public String getGenderbh() {
		return genderbh;
	}

	public void setGenderbh(String genderbh) {
		this.genderbh = genderbh;
	}



	public String getLastnamebah() {
		return lastnamebah;
	}

	public void setLastnamebah(String lastnamebah) {
		this.lastnamebah = lastnamebah;
	}

	public String getLastnamearbah() {
		return lastnamearbah;
	}

	public void setLastnamearbah(String lastnamearbah) {
		this.lastnamearbah = lastnamearbah;
	}


	public String getNationalitybah() {
		return nationalitybah;
	}

	public void setNationalitybah(String nationalitybah) {
		this.nationalitybah = nationalitybah;
	}



	public BigDecimal getNationalityCodebah() {
		return nationalityCodebah;
	}

	public void setNationalityCodebah(BigDecimal nationalityCodebah) {
		this.nationalityCodebah = nationalityCodebah;
	}

	public BigDecimal getNationalityIdbah() {
		return nationalityIdbah;
	}

	public void setNationalityIdbah(BigDecimal nationalityIdbah) {
		this.nationalityIdbah = nationalityIdbah;
	}

	public void saveSmartCardInfoBahrain() throws ParseException {

		SmartCardInfo smartCard = new SmartCardInfo();

		smartCard.setCivilId(getNationalId());
		smartCard.setSerialNo(getSerialnobahrain());

		smartCard.setFullName(getFullNamebah());
		smartCard.setFirstNameAr(getFirstNamebah());
		smartCard.setSurnameAr(getLastnamearbah());

		smartCard.setFullNameAr(getFullNameArbah());
		smartCard.setNationalityId(getNationalitybah());
		smartCard.setGender(getGenderbh());
		smartCard.setIssueDate(new SimpleDateFormat("dd/MM/yyyy")
		.parse(getIssuedatebah()));
		smartCard.setExpireDate(new SimpleDateFormat("dd/MM/yyyy")
		.parse(getExpirydatebah()));
		smartCard.setBirthDate(new SimpleDateFormat("dd/MM/yyyy")
		.parse(getBirthdatebh()));
		smartCard.setTelephone1(getMobilebh());
		smartCard.setEmailId(getScardemailbh());
		smartCard.setBloodType(getBloodTypebh());
		smartCard.setBldgNo(getBuilding_nobh());
		smartCard.setUnitNo(getFlatbh());
		//smartCard.setFloorNo(getsca);
		smartCard.setStreet(getRoadbh());
		smartCard.setBlockNo(getsCardblockbh());
		//smartCard.setDistrict(getScarddistrict());
		smartCard.setGuardIdNo(getGuardianCivilId());
		smartCard.setAddlFld1(getAdditinal1());
		smartCard.setAddlFld2(getAdditinal2());
		smartCard.setAddrUniqKey(getAddressRef());
		customer = new Customer();
		customer.setCustomerId(getPkCustomerId());
		smartCard.setFsCustomer(customer);

		if (getSmartCardId() != null) {
			smartCard.setSmartCardId(getSmartCardId());
			// smartCard.setu
			// smartCard.setCreator(getCreatedByCustomer());
			smartCard.setCreateDate(getCreationDateCustomer());

		} else {
			smartCard.setCreator(sessionStateManage.getUserName());
			smartCard.setCreateDate(new Date());
			// getIcustomerRegistrationService().saveCustomer(customer);
			// setPkCustomerId(customer.getCustomerId());
		}
		getIcustomerRegistrationService().saveSmartCardInfo(smartCard);
		setSmartCardId(smartCard.getSmartCardId());

	}

	/*public void saveSmartCardBahrainInfoToCutomer() throws ParseException, IOException {

		customer = new Customer();
		SessionStateManage sessionStateManage = new SessionStateManage();

		countryMaster = new CountryMaster();
		countryMaster.setCountryId(sessionStateManage.getCountryId());

		 Nationality Id save 
		CountryMaster nationality = new CountryMaster();
		nationality.setCountryId(getNationality());

		 save company 
		companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(sessionStateManage.getCompanyId());

	 *//** Customer Type *//*

		BizComponentData customerType = new BizComponentData();
		customerType.setComponentDataId(getGeneralService()
				.getComponentId(CUSTOMERTYPE,
						sessionStateManage.getLanguageId())
						.getFsBizComponentData().getComponentDataId());

	  *//** save Group ID *//*
		BizComponentData companyGroup = new BizComponentData();
		companyGroup.setComponentDataId(getGeneralService()
				.getComponentId(GROUPID, sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId());

		languageType = new LanguageType();
		languageType.setLanguageId(sessionStateManage.getLanguageId());
		customer.setFsLanguageType(languageType);
		customer.setFsCountryMasterByCountryId(countryMaster);
		customer.setFsCountryMasterByNationality(nationality);

		if (getGenderLatin().equalsIgnoreCase("F")) {
			customer.setTitle(Constants.TITLE_FOR_MRS);
			customer.setTitleLocal(Constants.LOCAL_TITLE_FOR_MRS);
			if(sessionStateManage.getLanguageId().intValue()==Constants.LANGUAGE_ID){
				customer.setGender(Constants.GENDER_FEMALE);
			}else{
				customer.setGender(Constants.GENDER_FEMALE_ARABIC);
			}
		} else {
			customer.setTitle(Constants.TITLE_FOR_MR);
			customer.setTitleLocal(Constants.LOCAL_TITLE_FOR_MR);
			if(sessionStateManage.getLanguageId().intValue()==Constants.LANGUAGE_ID){
				customer.setGender(Constants.GENDER_MALE);
			}else{
				customer.setGender(Constants.GENDER_MALE_ARABIC);
			}
		}


		customer.setFirstName(getFirstnamelBah());
		customer.setMiddleName(getMiddnamebah());
		customer.setLastName(getLastnamearbah());

		customer.setFirstNameLocal(getFirstnamelBah());
		customer.setLastNameLocal(getLastnamearbah());
		customer.setMiddleNameLocal(getMiddnameLbah());
		customer.setDailyLimit(getDailyLimit());

		customer.setWeeklyLimit(getWeeklyLimit());
		customer.setMontlyLimit(getMonthlyLimit());
		customer.setQuaterlyLimit(getQuarterlyLimit());
		customer.setHalfYearly(getHalfyearly());
		customer.setAnnualLimit(getAnnualLimit());
		customer.setSmartCardIndicator("Y");
		customer.setIsActive("Y");
		customer.setBranchCode( new BigDecimal(sessionStateManage.getBranchId()));

		// Added by kani begin smart cardhh
		if (getCustRefId() != null) {

			customer.setIntroducedBy(getCustRefId().toString());
			customer.setIntroducedDate(new Date());

		}
		customer.setMedicalInsuranceInd(getMedicalInsuranceInd());
		customer.setPepsIndicator(getPepsindicator());


		mapIdentityType = ruleEngine.getComponentData("Identity Type");
		BigDecimal identityTpeId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(CIVILID)) {
				identityTpeId = entry.getKey();
				break;
			}
		}

		customer.setMobile(getMobilebh());
		customer.setEmail(getScardemailbh());
		customer.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy")
		.parse(getBirthdatebh()));

		// customer.setCreatedBy(userName);
		customer.setActivatedInd("1");
		ArticleDetails articleDetail = new ArticleDetails();
		articleDetail.setArticleDetailId(getLevel());
		customer.setFsArticleDetails(articleDetail);

		// save to Income
		IncomeRangeMaster incomeRange = new IncomeRangeMaster();
		incomeRange.setIncomeRangeId(getIncomeRange());
		customer.setFsIncomeRangeMaster(incomeRange);

		customer.setFsCountryMasterByCountryId(countryMaster);
		customer.setFsCompanyMaster(companyMaster);
		customer.setFsBizComponentDataByCustomerTypeId(customerType);
		customer.setFsBizComponentDataByGroupId(companyGroup);

		 Managing save or update 
		if (getPkCustomerId() != null) {
			customer.setCustomerId(getPkCustomerId());
			customer.setUpdatedBy(userName);
			//CR for creation date based on application country 
			//customer.setLastUpdated(new Date());
			customer.setLastUpdated(getCurrentTime());
			customer.setCreatedBy(getCreatedByCustomer());
			customer.setCreationDate(getCreationDateCustomer());
			customer.setCustomerReference(getUpdateCustomerRefNo());
			customer.setSignatureSpecimen(getSignatureSpecimen());

			// checkLimit();

		} else {
			customer.setCreatedBy(userName);
			//CR for creation date based on application country 
			//customer.setCreationDate(new Date());
			customer.setCreationDate(getCurrentTime());
			customer.setCustomerReference(getCustomerRefNo());
			if(getDigitalSignature()!=null){
				customer.setSignatureSpecimen(getDigitalSignature());
			}else{
				RequestContext.getCurrentInstance().execute("signatureMandatory.show();");
			}
			// getIcustomerRegistrationService().saveCustomer(customer);
			// setPkCustomerId(customer.getCustomerId());
		}

		if (getApproved()) {
			// customer.setActivatedInd("1");
			//CR for creation date based on application country 
			//customer.setActivatedDate(new Date());
			customer.setActivatedDate(getCurrentTime());
			customer.setVerificationBy(userName);
			//CR for creation date based on application country 
			//customer.setVerificationDate(new Date());
			customer.setVerificationDate(getCurrentTime());
		} else {
			// customer.setActivatedInd("0");
			//	customer.setInactivatedDate(new Date());
		}






		//CR for creation date based on application country 
		//customer.setActivatedDate(new Date());
		customer.setActivatedDate(getCurrentTime());
		customer.setVerificationBy(userName);
		//CR for creation date based on application country 
		//customer.setVerificationDate(new Date());
		customer.setVerificationDate(getCurrentTime());



		getIcustomerRegistrationService().saveCustomer(customer);
		setPkCustomerId(customer.getCustomerId());
		saveContactDetails();
		saveEmployementDetails();
		saveProofDetails();
		saveSmartCardInfoBahrain();

		//Viswa@@@
		setNextSignaturePanel(false);
		//


		FacesContext.getCurrentInstance().getExternalContext().redirect("signature.xhtml");
	}*/

	/*public void fetchSmartCardDataForBahrain() throws ParseException, DOMException, ParserConfigurationException, SAXException, IOException {

		clear();
		getDealYear();
		getDocument();
		getDocumentSerialID(processIn);

		getFetchContactTypeList();

		setBooRemitterInfo(false);
		setBooCustomerDetails(false);
		setBooIdDetail(false);
		setBooContactDetails(false);
		setBooProof(false);
		setBooEmploymentDetails(false);
		setBooContactDetailsDuplicate(false);
		setRenderSavebutton(false);
		setRenderSignature(false);
		setBooVerifyAllRen(false);
		setSignaturePanelRender(false);
		setRenderFinal(false);
		setRenderVerifyReport(false);
		setNextUpdateRender(false);
		setBoofirstPanel(true);

		setRenderIntroducePanel(true);
		setBahrainCardPanel(true);
		bahrainSmartCrad();
		getNationalityCode();

		smartCardInfoList = icustomerRegistrationService
				.getSmartCardData(getNationalId());

		if (smartCardInfoList.size() > 0) {

			setNationalId(smartCardInfoList.get(0).getCivilId());
			setSerialnobahrain(smartCardInfoList.get(0).getSerialNo());

			setFullNameEn(smartCardInfoList.get(0).getFullName());
			setFullNameAr(smartCardInfoList.get(0).getFullNameAr());

			setNationalitybah(smartCardInfoList.get(0).getNationalityId());
			setGenderbh(smartCardInfoList.get(0).getGender());
			setIssuedatebah(new SimpleDateFormat("dd/MM/yyyy")
					.format(smartCardInfoList.get(0).getIssueDate()));
			setExpirydatebah(new SimpleDateFormat("dd/MM/yyyy")
					.format(smartCardInfoList.get(0).getExpireDate()));
			setBirthdatebh(new SimpleDateFormat("dd/MM/yyyy")
					.format(smartCardInfoList.get(0).getBirthDate()));
			setMobilebh(smartCardInfoList.get(0).getTelephone1());

			setScardemailbh(smartCardInfoList.get(0).getEmailId());
			setBloodTypebh(smartCardInfoList.get(0).getBloodType());
			setBuilding_nobh(smartCardInfoList.get(0).getBldgNo());
			setUnit_type(smartCardInfoList.get(0).getUnitType());
			setUnit_no(smartCardInfoList.get(0).getUnitNo());
			setScardfloor(smartCardInfoList.get(0).getFloorNo());
			setScardstreetbh(smartCardInfoList.get(0).getStreet());

			setsCardblockbh(smartCardInfoList.get(0).getBlockNo());

			setPkCustomerId(smartCardInfoList.get(0).getFsCustomer()
					.getCustomerId());
			setCreatedByCustomer(smartCardInfoList.get(0).getCreator());
			setCreationDateCustomer(smartCardInfoList.get(0).getCreateDate());

			setCustomerDetailsForSmardCardCustomer();

			setCustomerIdProof();

			setCustomerDetails();

			fillCountryList();
			fillStateList();
			fillDistrictList();
			fillCityList();
			setContactDetails();
			setEmployeementInfo();
			setSignaturePanelRender(true);

		} else {

			mapIdentityType = ruleEngine.getComponentData("Identity Type");
			BigDecimal idTypeproof = null;
			for (Map.Entry<BigDecimal, String> entry : mapIdentityType
					.entrySet()) {
				if (entry.getValue().equalsIgnoreCase(NATIONAL_ID)) {
					idTypeproof = entry.getKey();
					break;
				}
			}
			setIdTypeproof(idTypeproof);
			setDateExp(new SimpleDateFormat("dd/MM/yyyy")
					.parse(getExpirydatebah()));
			System.out.println("====civil id" + idTypeproof);
		}

	}*/

	public void nextContactDetailsFromSmartCardInfoBahrain(){

		setBooRemitterInfo(false);
		setBooIdDetail(false);
		setBooCustomerDetails(false);
		setBooContactDetails(true);
		setBooEmploymentDetails(false);
		setBooProof(false);
		setContactlistcheck(false);
		setRenderSavebutton(false);
		setRenderSignature(false);
		setBooVerifyAllRen(false);
		setRenderverication(false);
		setSignaturePanelRender(false);
		setRenderUpdate(false);
		setRenderRegistration(false);
		setRenderVerifyReport(false);
		setRenderFinal(false);
		setExitRender(false);
		setNextSignaturePanel(false);
		setNextUpdateRender(false);
		setRenderIntroducePanel(false);
		setBooManualGo(false);
		setBahrainCardPanel(false);

	}

	public void clearSmartCardInfoBahrain() {

		setFullNamebah(null);
		setFullNameArbah(null);
		setGenderbh(null);

		setNationalId(null);

		setBirthdatebh(null);

		setIssuedatebah(null);

		setExpirydatebah(null);

		setBuilding_nobh(null);
		setsCardblockbh(null);
		setScardstreetbh(null);

		setFlatbh(null);
		setSerialnobahrain(null);

		setFirstNamebah(null);
		setMiddnamebah(null);
		setLastnamearbah(null);
		setFirstnamelBah(null);
		setMiddnameLbah(null);
		setLastnamearbah(null);
	}


	/*public void getNationalityCode() {

		List<CountryMasterDesc> countryCodeList = icustomerRegistrationService.getNationalityCode(sessionStateManage.getLanguageId(), getNationalityCodebah());

		for (CountryMasterDesc countryList : countryCodeList) {

			setNationality(countryList.getFsCountryMaster().getCountryId());
			setNationalitybah(countryList.getNationality());
			System.out.println("KKKKKKKKKKKK" + countryList.getNationality());

		}

	}*/

	//Bahrain Smart Card

	//Save data to AML Check - By Viswa

	public String getAMLCheckStatus(Customer customer) {

		String amlReturnStatus = null;
		String status = "  ";

		try {
			FNSServicesLookupSoapClient finscanService = new FNSServicesLookupSoapClient();

			String fullName = customer.getFirstName() + " "+ customer.getMiddleName() + " " + customer.getLastName();

			String fullNameLocal = customer.getFirstNameLocal() + " " + customer.getMiddleNameLocal() + " " + customer.getLastNameLocal();

			amlReturnStatus = finscanService.amlServiceSearch("Individual",
					getGender(), "Active", "Yes", "Yes", "Yes", " ", " ", " ", " ",
					" ", " ", " ", "Full Source", "Yes", "Yes", "WC",
					"World-Check", "  ", "  ", "Country", customer
					.getFsCountryMasterByNationality().getCountryId()
					.toString(), "Date of Birth", customer
					.getDateOfBirth().toString(), "National ID Number",
					getIdNumber(), "Passport ID", "  ", fullName, fullNameLocal,
					"100061-E", "Test", status);

			System.out.println("AML Status is:" + amlReturnStatus);
		} catch (Exception e) {

			log.info("Aml Exception:" + e);
		}
		return amlReturnStatus;
	}


	public void getAMLCheckStatus_afterSave(BigDecimal customerId) {

		List<Customer> customerInfoList = new ArrayList<Customer>();
		String amlReturnStatus = null;
		String status = " ";

		String civilIdNumber = null;

		String customerRef = null;

		try {
			FNSServicesLookupSoapClient finscanService = new FNSServicesLookupSoapClient();

			customerInfoList = icustomerRegistrationService
					.getCustomerInfo(customerId);

			if (customerInfoList.size() > 0) {

				List<CustomerIdProof> idproofList = icustomerRegistrationService
						.getCivilID(customerInfoList.get(0).getCustomerReference());
				if (idproofList.size() > 0) {
					civilIdNumber = idproofList.get(0).getIdentityInt();
				}

				customerRef = customerInfoList.get(0).getCustomerReference()
						.toString()
						+ "-E";

				String fullName = customerInfoList.get(0).getFirstName() + " "
						+ customerInfoList.get(0).getMiddleName() + " "
						+ customerInfoList.get(0).getLastName();
				String fullNameLocal = customerInfoList.get(0)
						.getFirstNameLocal()
						+ " "
						+ customerInfoList.get(0).getMiddleNameLocal()
						+ " "
						+ customerInfoList.get(0).getLastNameLocal();

				amlReturnStatus = finscanService.amlServiceSearch("Individual",
						customerInfoList.get(0).getGender(), "Active", "Yes",
						"Yes", "Yes", " ", " ", " ", " ", " ", " ", " ",
						"Full Source", "Yes", "Yes", "WC", "World-Check", "  ",
						"  ", "Country", customerInfoList.get(0)
						.getFsCountryMasterByNationality()
						.getCountryId().toString(), "Date of Birth",
						customerInfoList.get(0).getDateOfBirth().toString(),
						"National ID Number", civilIdNumber, "Passport ID",
						"  ", fullName, fullNameLocal, customerRef, "AMIEC",
						status);

			}

			System.out.println("AML Status is:" + amlReturnStatus);
		} catch (Exception e) {

			log.info("Aml Exception:" + e);
		}
	}
	/*
	public void saveAmlStatus(Customer customer) throws ParseException {
		BranchComplianceOfficer branchComplianceOfficer = new BranchComplianceOfficer();

		CountryBranch countryBranch = new CountryBranch();
		countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage
				.getBranchId()));

		countryMaster = new CountryMaster();
		countryMaster.setCountryId(sessionStateManage.getCountryId());

		customer.setCustomerId(customer.getCustomerId());

		branchComplianceOfficer.setCountryBranchId(countryBranch);
		branchComplianceOfficer.setAccountYearMonth(DateUtil
				.getCurrentAccountYearMonth());
		branchComplianceOfficer.setCustomerId(customer);
		branchComplianceOfficer.setApplicationCountryId(countryMaster);
		branchComplianceOfficer.setCustomerReferenceCode(customer
				.getCustomerReference().toString());

		branchComplianceOfficer.setAmlCode(customer.getAmlStatus());
		branchComplianceOfficer.setAmlNumberOfHits(customer.getNumberOfHits());
		branchComplianceOfficer.setIsActive("Y");
		branchComplianceOfficer.setCreatedBy(sessionStateManage.getUserName());
		branchComplianceOfficer.setCreatedDate(new Date());

		bcoService.saveBranchCompliance(branchComplianceOfficer);

	}
	 */

	//Added By Nazish For Scanner

	//private Boolean enableCheckId = false;
	private Boolean enableScan = true;
	private String dobS;
	private String expDateS;
	private String idNumberScan;




	/*	public Boolean getEnableCheckId() {
		return enableCheckId;
	}

	public void setEnableCheckId(Boolean enableCheckId) {
		this.enableCheckId = enableCheckId;
	}*/

	public Boolean getEnableScan() {
		return enableScan;
	}

	public void setEnableScan(Boolean enableScan) {
		this.enableScan = enableScan;
	}


	public String getDobS() {
		return dobS;
	}

	public void setDobS(String dobS) {
		this.dobS = dobS;
	}

	public String getExpDateS() {
		return expDateS;
	}

	public void setExpDateS(String expDateS) {
		this.expDateS = expDateS;
	}

	public String getIdNumberScan() {
		return idNumberScan;
	}

	public void setIdNumberScan(String idNumberScan) {
		this.idNumberScan = idNumberScan;
	}

	public void checkId() throws IOException, ParseException  {

		boolean duplicate=duplicateCheck();
		if(duplicate)
		{
			//setEnableScan(false);
			StringBuffer urlBuffer = new StringBuffer();

			String equals = "=";
			String ampersand = "&";
			String idTypes = "IdType";

			String strIdType=idTypeMap.get(getIdTypeproof());

			String idType =null;

			if (getSelectType().equalsIgnoreCase(getGeneralService().getComponentId(Constants.METHODTYPE,	sessionStateManage.getLanguageId())
					.getFsBizComponentData().getComponentDataId()
					.toPlainString())) {

				if(strIdType.equalsIgnoreCase("CIVIL ID"))
				{
					idType = "IdType=OldID";
				}else if(strIdType.equalsIgnoreCase("LICENSE NO"))
				{
					idType = "IdType=NewDrivingLicense";
				}else if(strIdType.equalsIgnoreCase("CIVIL ID NEW"))
				{
					idType = "IdType=NewID";
				}else if(strIdType.equalsIgnoreCase("BEDOUIN"))
				{
					idType = "IdType=Bedoun";
				}else
				{
					idType = "IdType=Other";
				}

			}else
			{

				if(strIdType.equalsIgnoreCase("CIVIL ID"))
				{
					idType = "IdType=NewID";
				}else if(strIdType.equalsIgnoreCase("LICENSE NO"))
				{
					idType = "IdType=NewDrivingLicense";
				}else if(strIdType.equalsIgnoreCase("CIVIL ID NEW"))
				{
					idType = "IdType=NewID";
				}else if(strIdType.equalsIgnoreCase("BEDOUIN"))
				{
					idType = "IdType=Bedoun";
				}else
				{
					idType = "IdType=Other";
				}
			}

			String rootContext = "http://192.168.100.195/IDAutomation/frmcheckID.ashx?ClientRefNO"; 
			urlBuffer.append(rootContext).append(equals).append(getIdnumberProof()).append(ampersand).append(idType);

			try {

				URL knetRequest = new URL(urlBuffer.toString());
				System.out.println("===URL Checkid===== "+urlBuffer.toString());
				HttpURLConnection httConn = null;
				BufferedReader in = null;

				httConn = (HttpURLConnection) knetRequest.openConnection();
				in = new BufferedReader(new InputStreamReader(
						httConn.getInputStream()));

				String str = in.readLine();

				System.out.println("IIIIIIIIIII== " + str);

				if (str.startsWith("DMS_Error")) {
					in.close();
					setEnableScan(false);
					scan();
					//RequestContext.getCurrentInstance().execute("scanRecord.show();");

				} else {

					String s22 = str;



					HashMap<String, String> map=new HashMap<String, String>();
					StringTokenizer stToken= new StringTokenizer(s22,",");
					int i=1;
					while(stToken.hasMoreTokens() && i<=3){
						String tNmae= stToken.nextToken();
						String arr[]=tNmae.split("=");
						if(arr.length==1)
						{
							map.put(arr[0], "");
						}else
						{
							map.put(arr[0], arr[1]);
						}
						i++;

					}
					boolean valueCheck=false;
					for (String keyValue : map.keySet()) {

						String value=map.get(keyValue);
						if(value.equalsIgnoreCase(""))
						{
							valueCheck=true;
							break;
						}
					}

					if(valueCheck)
					{
						in.close();
						setEnableScan(false);
						scan();
						//RequestContext.getCurrentInstance().execute("scanRecord.show();");
					}else
					{
						String[] str11 = s22.split("=");
						String str22 = str11.toString();

						String[] str12 = str11[1].split(",");

						String[] str13 = str11[2].split(",");

						String[] str14 = str11[3].split(",");

						java.util.Date expiryDcheck = new SimpleDateFormat("yyyyMMdd")
						.parse(str13[0].toString());

						java.util.Date birthDate = new SimpleDateFormat("yyyyMMdd")
						.parse(str14[0].toString());

						String expiryDate1 = new SimpleDateFormat("dd/MM/yyyy")
						.format(expiryDcheck);

						setIdNumberScan(str12[0]);
						setDateExp(new SimpleDateFormat("dd/MM/yyyy")
						.parse(expiryDate1));

						SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

						getFetchContactTypeList();

						String df = dateformat.format(getDateExp());


						if (expiryDcheck.compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {

							duplicate=duplicateCheck();
							if(duplicate)
							{
								CreateProofTable createProofTable = new CreateProofTable(
										getIdFor().toPlainString(), getIdTypeproof()
										.toPlainString(), getIdNumberScan(), df,
										"insert", 0, idForMap.get(this.idFor),
										idTypeMap.get(this.idTypeproof));
								createProofTable.setCustomerIdProofId(null);

								createProofList.add(createProofTable);
								setBooidproofDatatable(true);
								setRenderModifyScan(false);
								/*setEnableCheckId(true);*/
								setRenderSignature(true);
								clearProofInfo();
								setDateExp(null);
							}else
							{
								RequestContext context = RequestContext.getCurrentInstance();
								context.execute("PF('duplicateproof').show();");
							}
						} else {
							setEnableScan(true);
							setRenderSignature(false);
							RequestContext context = RequestContext.getCurrentInstance();
							context.execute("PF('idExpired').show();");


						}
					}


				}
				in.close();
			} catch (MalformedURLException e) {
				log.info("Connection Problem: "+e);
			} catch (IOException ie) {
				log.info("Connection Problem: "+ie);
			}
		}else
		{
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('duplicateproof').show();");
		}

	}

	private boolean duplicateCheck()
	{
		boolean dublicate=false;
		try{
			if(createProofList.size()>0)
			{
				for (CreateProofTable addCustomerIdProofBean : createProofList) {

					if (getIdFor().compareTo(
							new BigDecimal(addCustomerIdProofBean.getId_for())) == 0) {
						if (getIdnumberProof().equalsIgnoreCase(
								addCustomerIdProofBean.getId_number())) {
							if (getIdTypeproof().equals(
									new BigDecimal(addCustomerIdProofBean
											.getId_type()))) {

								if (getDateExp().after(
										new SimpleDateFormat("dd/MM/yy")
										.parse(addCustomerIdProofBean
												.getDate_expiary()))) {
									if(new Date().before(new SimpleDateFormat("dd/MM/yy").parse(addCustomerIdProofBean.getDate_expiary()))){
										dublicate = false;
										break;
									}else{
										dublicate = true;
									}

								} else {
									dublicate = false;
									break;
								}
							}else {
								dublicate = true;
							}

						} else {
							dublicate = true;
						}
					} else {
						dublicate = true;
					}
				}
			}else
			{
				dublicate = true;
			}

		}catch(Exception ex)
		{
			log.info("Customer Id proof duplicate check error: "+ex);
		}
		return dublicate;

	}

	// This method for local testing purpose
	public void checkId_localTest() throws IOException, ParseException {

		//setEnableScan(false);
		//StringBuffer urlBuffer = new StringBuffer();

		//String equals = "=";
		boolean duplicate=duplicateCheck();
		if(duplicate)
		{
			try {

				String str = "IdNo=280050804874,ExprireDate=20170430,BirthDate=19970501";
				//String str="DMS_Error";
				//http://192.168.100.195/IDAutomation/frmmain.aspx?ClientRefNO=304121301179&IdNo=304121301179&BirthDate=20150407&ExpireDate=20150407&IdType=NewID&Creator=EXCHDBA&CreationDate=20150407&CreatedApp=EMOS

				System.out.println("IIIIIIIIIII== " + str);

				if (str.startsWith("DMS_Error")) {

					setEnableScan(false);
					RequestContext.getCurrentInstance().execute(
							"scanRecord.show();");

				} else {

					String s22 = str;

					HashMap<String, String> map=new HashMap<String, String>();
					StringTokenizer stToken= new StringTokenizer(s22,",");
					while(stToken.hasMoreTokens()){
						String tNmae= stToken.nextToken();
						String arr[]=tNmae.split("=");
						if(arr.length==1)
						{
							map.put(arr[0], "");
						}else
						{
							map.put(arr[0], arr[1]);
						}


					}
					boolean valueCheck=false;
					for (String keyValue : map.keySet()) {

						String value=map.get(keyValue);
						if(value.equalsIgnoreCase(""))
						{
							valueCheck=true;
							break;
						}
					}
					/*java.util.Date expiryDcheck =null;
					  if(map.get("ExpireDate")!=null && map.get("ExpireDate").equalsIgnoreCase("")){
						expiryDcheck = new SimpleDateFormat("yyyyddmm").parse(map.get("ExpireDate"));
						String expiryDate1 = new SimpleDateFormat("dd/MM/yyyy").format(expiryDcheck);
						setDateExp(new SimpleDateFormat("dd/MM/yyyy")
						.parse(expiryDate1));
					  }

					  if(map.get("BirthDate")!=null && map.get("BirthDate").equalsIgnoreCase("")){
				java.util.Date birthDate = new SimpleDateFormat("yyyyddmm").parse(map.get("BirthDate"));
					  }

					  if(map.get("IdNo")!=null && map.get("IdNo").equalsIgnoreCase("")){
						  setIdNumberScan(map.get("IdNo"));
					  }
					 */
					if(valueCheck)
					{
						setEnableScan(false);
						RequestContext.getCurrentInstance().execute("scanRecord.show();");
					}else
					{
						String[] str11 = s22.split("=");
						String str22 = str11.toString();

						String[] str12 = str11[1].split(",");

						String[] str13 = str11[2].split(",");

						String[] str14 = str11[3].split(",");

						/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							sdf.format(date)*/

						java.util.Date expiryDcheck = new SimpleDateFormat("yyyyMMdd")
						.parse(str13[0].toString());

						java.util.Date birthDate = new SimpleDateFormat("yyyyMMdd")
						.parse(str14[0].toString());

						String expiryDate1 = new SimpleDateFormat("dd/MM/yyyy")
						.format(expiryDcheck);

						setIdNumberScan(str12[0]);
						setDateExp(new SimpleDateFormat("dd/MM/yyyy")
						.parse(expiryDate1));

						SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

						getFetchContactTypeList();

						String df = dateformat.format(getDateExp());

						if (expiryDcheck.compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {

							duplicate=duplicateCheck();
							if(duplicate)
							{
								CreateProofTable createProofTable = new CreateProofTable(
										getIdFor().toPlainString(), getIdTypeproof()
										.toPlainString(), getIdNumberScan(), df,
										"insert", 0, idForMap.get(this.idFor),
										idTypeMap.get(this.idTypeproof));
								createProofTable.setCustomerIdProofId(null);

								createProofList.add(createProofTable);
								setBooidproofDatatable(true);
								/*setEnableCheckId(true);*/
								setRenderSignature(true);
								clearProofInfo();
							}else
							{
								RequestContext context = RequestContext.getCurrentInstance();
								context.execute("PF('duplicateproof').show();");
							}
						} else {

							setEnableScan(true);
							setRenderSignature(false);

						}
					}

				}

			} catch (NullPointerException npe) {
				log.info("Connection Problem: " + npe);
			}
		}else
		{
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('duplicateproof').show();");
		}


	}

	public void scan() throws IOException, ParseException  {

		if(getDob()!=null){

			String dobstr = new SimpleDateFormat("dd/MM/yyyy").format(getDob());
			String[] dateStr = dobstr.split("/");
			setDobS(dateStr[2]+dateStr[1]+dateStr[0]);

		}

		if(getBirthdate()!=null){

			String[] dateStr = getBirthdate().split("/");

			setDobS(dateStr[2]+dateStr[1]+dateStr[0]);
		}

		if(getShowDob()!=null){

			String[] dateStr = getShowDob().split("/");

			setDobS(dateStr[2]+dateStr[1]+dateStr[0]);

		}

		if(getDateExp()!=null){

			String dateExp = new SimpleDateFormat("dd/MM/yyyy").format(getDateExp());
			String[] dateStr = dateExp.split("/");
			setExpDateS(dateStr[2]+dateStr[1]+dateStr[0]);


		}else{
			RequestContext.getCurrentInstance().execute("expDatemand.show();");
		}

		setEnableScan(true);
		RequestContext.getCurrentInstance().execute("scanwait.show();");


	}

	/*public void redirectToScanningBrowser() {  

			StringBuffer urlBuffer = new StringBuffer();

			String ampersand = "&";
			String equals = "=";

			String idNo = "IdNo";
			String birthDate = "BirthDate";
			String expiryDate = "ExpireDate";
			String rootContext = "http://192.168.100.195/IDAutomation/frmmain.aspx?ClientRefNO";

			urlBuffer.append(rootContext).append(equals).append(getIdnumberProof()).
			append(ampersand).append(idNo).append(equals).append(getIdnumberProof()).append(ampersand).
			append(birthDate).append(equals).append(getDobS()).append(ampersand).append(expiryDate).append(equals).append(getExpDateS());
			try {
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

				context.redirect(urlBuffer.toString());

			} catch (Exception e) {
				log.info("Problem to redirect: " + e);
			}


	    } */


	public void redirectToScanningBrowser() {  

		setEnableScan(true);
		StringBuffer urlBuffer = new StringBuffer();

		String ampersand = "&";

		String equals = "=";

		String sysDate = new SimpleDateFormat("dd/MM/yyyy").format(getCurrentTime());

		String[] dateStr = sysDate.split("/");

		String curDate=(dateStr[2]+dateStr[1]+dateStr[0]);


		// New URL to be constructed 

		//http://192.168.100.195/IDAutomation/frmmain.aspx?ClientRefNO=304121301179&IdNo=304121301179&BirthDate=20150407&ExpireDate=20150407

		//&IdType=NewID&Creator=EXCHDBA&CreationDate=20150407&CreatedApp=EMOS

		String strIdType=idTypeMap.get(getIdTypeproof());

		String idType =null;

		if (getSelectType().equalsIgnoreCase(getGeneralService().getComponentId(Constants.METHODTYPE,	sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId()
				.toPlainString())) {

			if(strIdType.equalsIgnoreCase("CIVIL ID"))
			{
				idType = "IdType=OldID";
			}else if(strIdType.equalsIgnoreCase("LICENSE NO"))
			{
				idType = "IdType=NewDrivingLicense";
			}else if(strIdType.equalsIgnoreCase("CIVIL ID NEW"))
			{
				idType = "IdType=NewID";
			}else if(strIdType.equalsIgnoreCase("BEDOUIN"))
			{
				idType = "IdType=Bedoun";
			}else
			{
				idType = "IdType=Other";
			}

		}else
		{

			if(strIdType.equalsIgnoreCase("CIVIL ID"))
			{
				idType = "IdType=NewID";
			}else if(strIdType.equalsIgnoreCase("LICENSE NO"))
			{
				idType = "IdType=NewDrivingLicense";
			}else if(strIdType.equalsIgnoreCase("CIVIL ID NEW"))
			{
				idType = "IdType=NewID";
			}else if(strIdType.equalsIgnoreCase("BEDOUIN"))
			{
				idType = "IdType=Bedoun";
			}else
			{
				idType = "IdType=Other";
			}
		}



		String idNo = "IdNo";

		String birthDate = "BirthDate";

		String expiryDate = "ExpireDate";

		//String idType = "IdType=NewID";

		String creator = "Creator";

		String creationDate = "CreationDate";

		String createdApp = "CreatedApp=EMOS";

		String rootContext = "http://192.168.100.195/IDAutomation/frmmain.aspx?ClientRefNO";

		urlBuffer.append(rootContext).append(equals).append(getIdnumberProof()).

		append(ampersand).append(idNo).append(equals).append(getIdnumberProof()).append(ampersand).

		append(birthDate).append(equals).append(getDobS()).append(ampersand).append(expiryDate).append(equals).append(getExpDateS())

		.append(ampersand).append(idType)  // For ID Type

		.append(ampersand).append(creator).append(equals).append(sessionStateManage.getUserName())  // For Creator

		.append(ampersand).append(creationDate).append(equals).append(curDate)  // For Creation Date

		.append(ampersand).append(createdApp);   // For Created By

		System.out.println("Add Document of  OCR URL :  " + urlBuffer.toString());
		log.info("Add Document of OCR URL :  " + urlBuffer.toString());

		try {

			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();



			context.redirect(urlBuffer.toString());



		} catch (Exception e) {

			log.info("Problem to redirect: " + e);

		}

	} 
	public void onDateSelect(SelectEvent event) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		//setBnkValueDate(format.format(event.getObject()));
		try {
			setExpDate(format.parse(format.format(event.getObject())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	// Partial Save or RollBack the Details 


	public Boolean checkingSignature() throws ParseException, IOException{
		if (getDigitalSignature() != null) {
			return true;
		} else {
			return false;
		}

	}

	public String saveCustomerInformation() throws ParseException, IOException{

		try {

			Customer customerinfo = new Customer();

			customerinfo.setCustomerId(getPkCustomerId());

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionStateManage.getCountryId());
			customerinfo.setFsCountryMasterByCountryId(countryMaster);

			CountryMaster nationality = new CountryMaster();
			nationality.setCountryId(getNationality());
			customerinfo.setFsCountryMasterByNationality(nationality);

			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(sessionStateManage.getCompanyId());
			customerinfo.setFsCompanyMaster(companyMaster);

			BizComponentData customerType = new BizComponentData();
			customerType.setComponentDataId(getGeneralService().getComponentId(Constants.CUSTOMERTYPE_INDU, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
			customerinfo.setFsBizComponentDataByCustomerTypeId(customerType);

			BizComponentData companyGroup = new BizComponentData();
			companyGroup.setComponentDataId(getGeneralService().getComponentId(Constants.GROUPID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());	
			customerinfo.setFsBizComponentDataByGroupId(companyGroup);

			LanguageType languageType = new LanguageType();
			languageType.setLanguageId(sessionStateManage.getLanguageId());
			customerinfo.setFsLanguageType(languageType);

			IncomeRangeMaster incomeRange = new IncomeRangeMaster();
			incomeRange.setIncomeRangeId(getIncomeRange());
			customerinfo.setFsIncomeRangeMaster(incomeRange);

			ArticleDetails articleDetail = new ArticleDetails();
			articleDetail.setArticleDetailId(getLevel());
			customerinfo.setFsArticleDetails(articleDetail);

			customerinfo.setShortName(getShortName());
			customerinfo.setShortNameLocal(getShortNamel());
			// customerinfo.setAmlStatus(getAMLStatus());
			customerinfo.setTitle(getTitle());
			customerinfo.setFirstName(getFirstName());
			customerinfo.setMiddleName(getMiddleName());
			customerinfo.setLastName(getLastName());
			customerinfo.setTitleLocal(getTitlel());
			customerinfo.setFirstNameLocal(getFirstNamel());
			customerinfo.setMiddleNameLocal(getMiddleNamel());
			customerinfo.setLastNameLocal(getLastNamel());
			customerinfo.setGender(getGender());
			customerinfo.setDailyLimit(getDailyLimit());
			customerinfo.setWeeklyLimit(getWeeklyLimit());
			customerinfo.setMontlyLimit(getMonthlyLimit());
			customerinfo.setQuaterlyLimit(getQuarterlyLimit());
			customerinfo.setHalfYearly(getHalfyearly());
			customerinfo.setAnnualLimit(getAnnualLimit());
			customerinfo.setSmartCardIndicator(Constants.No);
			customerinfo.setIsActive(Constants.Yes);
			customerinfo.setBranchCode(new BigDecimal(sessionStateManage.getBranchId()));
			customerinfo.setPepsIndicator(getPepsindicator());
			customerinfo.setMedicalInsuranceInd(getMedicalInsuranceInd());
			customerinfo.setMobile(getMobile());
			customerinfo.setEmail(getEmail());
			customerinfo.setEmosCustomer(getEmosCustomer());

			customerinfo.setActivatedInd(Constants.CUST_ACTIVE_INDICATOR);
			customerinfo.setAlterEmailId(getAlternativeEmail());


			mapIdentityType = ruleEngine.getComponentData("Identity Type");

			BigDecimal identityTpeId = null;
			for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
				if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
					identityTpeId = entry.getKey();
					break;
				}
			}

			if (getBooChangeDob()) {
				customerinfo.setDateOfBirth(getDob());
			} else if (getIdType().equalsIgnoreCase(identityTpeId.toString())) {
				if(getShowDob()!=null){
					customerinfo.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(getShowDob()));
				} else{
					customerinfo.setDateOfBirth(getDob());
				}
			} else {
				customerinfo.setDateOfBirth(getDob());
			}

			if (getCustRefId() != null) {
				customerinfo.setIntroducedBy(getCustRefId().toString());
				customerinfo.setIntroducedDate(new Date());
			}


			if (getPkCustomerId() != null) {

				customerinfo.setLastUpdated(getCurrentTime());
				customerinfo.setCreatedBy(getCreatedByCustomer());
				customerinfo.setCreationDate(getCreationDateCustomer());
				customerinfo.setCustomerReference(getUpdateCustomerRefNo());
				customerinfo.setSignatureSpecimen(getDigitalSignature());
				if(getActiveInd()!=null && getActiveInd().equalsIgnoreCase(Constants.CUST_ACTIVE_INDICATOR)){
					customerinfo.setUpdatedBy(session.getUserName());
				}

			} else {
				
				BigDecimal custRef = getIcustomerRegistrationService().callProcedureCustReferenceNumber(getCompanyCodeByCompanyId() , Constants.DOCUMENT_CODE_FOR_CUSTOMER , getDealYearbyDate() , session.getBranchId());
				
				if(custRef != null){
					customerinfo.setCustomerReference(custRef);
				}

				customerinfo.setCreatedBy(session.getUserName());
				customerinfo.setCreationDate(getCurrentTime());
				customerinfo.setSignatureSpecimen(getDigitalSignature());

			}
/*
		//@@@ AML	
			String amlReturnStatus = null;
			String amlStatus = null;
			String amlhits = null;

			amlReturnStatus = getAMLCheckStatus(customerinfo);

			//amlReturnStatus ="PASS-0";

			if (amlReturnStatus == null) {
				customerinfo.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);

				customerinfo.setNumberOfHits(new BigDecimal(0));
			}else{
				String[] parts = amlReturnStatus.split("-");
				amlStatus = parts[0];
				amlhits = parts[1];
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_ERROR)){
					customerinfo.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);
					customerinfo.setNumberOfHits(new BigDecimal(amlhits));
				}
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PENDING)){
					customerinfo.setAmlStatus(Constants.AML_STATUS_BCO);
					customerinfo.setNumberOfHits(new BigDecimal(amlhits));
					//saveAmlStatus(customer);
				}
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PASS)){
					customerinfo.setAmlStatus(Constants.AML_STATUS_PASS);
					customerinfo.setNumberOfHits(new BigDecimal(0));
				}



			}
*/
			HashMap<String,Object> mapAllDetailForSave = new HashMap<String,Object>();

			mapAllDetailForSave.put("customerinfo", customerinfo);
			//getIcustomerRegistrationService().saveCustomer(customerinfo);

			/*setPkCustomerId(customerinfo.getCustomerId());
				  customerIdToNominee = customerinfo.getCustomerId();*/

			List<ContactDetail> lstCustContactDetails =  saveCustomerContactDetails();

			mapAllDetailForSave.put("customerContactDetails", lstCustContactDetails);

			if(getBooEmploymentPanel()){
				CustomerEmploymentInfo custEmployeeInfo = saveCustomerEmployeeInformation();

				mapAllDetailForSave.put("customerEmployeeInfo", custEmployeeInfo);
				mapAllDetailForSave.put("customerUnEmployee", new Boolean(true));
			}else{
				mapAllDetailForSave.put("customerUnEmployee", new Boolean(false));
			}



			if (createProofList.size() == 0) {

				setIdListcheck(true);

				mapAllDetailForSave.put("custIdProofexists", new Boolean(false));

				getIcustomerRegistrationService().saveCustomerRegistration(mapAllDetailForSave);

				setPkCustomerId(customerinfo.getCustomerId());
				customerIdToNominee = customerinfo.getCustomerId();

				return "";
			} else {
				setIdListcheck(false);

				List<CustomerIdProof> lstCustIdProof = saveCustomerProofInformation();

				mapAllDetailForSave.put("custIdProofexists",new Boolean(true));
				mapAllDetailForSave.put("customerIdProof", lstCustIdProof);

				getIcustomerRegistrationService().saveCustomerRegistration(mapAllDetailForSave);

				setPkCustomerId(customerinfo.getCustomerId());
				customerIdToNominee = customerinfo.getCustomerId();




				/*if (getNominee()) {
						  getNomineeRegistration().setNominatorId(customerIdToNominee, "first");
						  return "nominee";
					  } else {*/
				/* try {
							  //ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				 */
				String verificationToken = null;
				String updatedBy = null;
				BigDecimal customerReference = null;

				List<Customer> custlist = getIcustomerRegistrationService()
						.getVerificationToken(getPkCustomerId());
				if (custlist.size() > 0) {
					verificationToken = custlist.get(0)
							.getVerificationTokenId();
					updatedBy = custlist.get(0).getUpdatedBy();
					emosCustomer = custlist.get(0).getEmosCustomer();
					if(custlist.get(0).getCustomerReference()!=null){
						customerReference = custlist.get(0).getCustomerReference();
						setUpdateCustomerRefNo(customerReference);
					}
				}

				/* if (emosCustomer !=null && !emosCustomer.equalsIgnoreCase("1")) {

								  String toMailid = custlist.get(0).getEmail();
								  mailService1.sendMail(toMailid, "Customer Info Log Difference", verificationToken);
								  setSignaturePanelRender(false);
								  setRenderUpdate(true);
								  setRenderRegistration(false);
								  setBooProof(false);
								  setRenderSignature(false);
								  setRenderVerifyReport(false);
								  setBooRenderFinalSave(false);
								  setExitRender(false);
								  setSuccessPanel(false);
								  setUpdatePanel(false);
								  setBooVerifyAllRen(false);
								  setNextSignaturePanel(false);
								  setNextUpdateRender(false);

								  //customerinfo.setEmosCustomer(Constants.Yes);
								  getAMLCheckStatus_afterSave(getPkCustomerId());
								  //customerinfo.setCustomerId(getPkCustomerId());
								  getIcustomerRegistrationService().saveCustomerEmos(getPkCustomerId(), Constants.Yes);
							  }

							  else if (updatedBy != null && emosCustomer == null)  {

									  String toMailid = custlist.get(0).getEmail();
									  mailService1.sendMail(toMailid, "Customer Info Log Difference", verificationToken);
									  setSignaturePanelRender(false);
									  setRenderUpdate(true);
									  setRenderRegistration(false);
									  setBooProof(false);
									  setRenderSignature(false);
									  setRenderVerifyReport(false);
									  setBooRenderFinalSave(false);
									  setExitRender(false);
									  setSuccessPanel(false);
									  setUpdatePanel(false);
									  setBooVerifyAllRen(false);
									  setNextSignaturePanel(false);
									  setNextUpdateRender(false);

									  //customerinfo.setEmosCustomer(Constants.Yes);
									  getAMLCheckStatus_afterSave(getPkCustomerId());
									  //customerinfo.setCustomerId(getPkCustomerId());
									  getIcustomerRegistrationService().saveCustomerEmos(getPkCustomerId(), Constants.Yes);


							  } else {*/

				setExitRender(false);
				setBooRenderFinalSave(false);
				setSignaturePanelRender(false);
				setSignatureCaptureRender(false);
				setSignatureSpecimenRender(false);
				setBooVerifyAllRen(false);
				setRenderUpdate(false);
				setRenderRegistration(false);
				setBooProof(false);
				setRenderSignature(false);
				setRenderVerifyReport(false);
				setBooRemitterInfo(false);
				setBooIdDetail(false);
				setBooCustomerDetails(false);
				setBooContactDetails(false);
				setBooEmploymentDetails(false);
				setBooProof(false);
				setRenderSavebutton(false);
				setRenderSignature(false);
				setBooVerifyAllRen(false);
				setRenderverication(false);
				setSuccessPanel(true);
				setUpdatePanel(false);
				setRenderUpdate(false);
				setRenderRegistration(false);
				setRenderVerifyReport(false);
				setRenderFinal(false);
				setExitRender(false);
				setBooVerifyAllRen(false);
				setNextSignaturePanel(false);
				setNextUpdateRender(false);

				//customerinfo.setEmosCustomer(null);
				getAMLCheckStatus_afterSave(getPkCustomerId());
				if(emosCustomer!=null && emosCustomer.equalsIgnoreCase("1")){
					getIcustomerRegistrationService().saveCustomerEmos(getPkCustomerId(), Constants.Yes);

				}
				List<String> outPutList = icustomerRegistrationService.callProcedureUpdate(getPkCustomerId());

				if(outPutList.size()>0){

					if(outPutList.get(0).equalsIgnoreCase(Constants.Yes)){

						RequestContext.getCurrentInstance().execute("migrationexception.show();");

					}
				}

				//}


			}
			// }


			/*  }catch(AMGException ae){
				  log.error("Exception Occured While Migration Data", ae);
				  RequestContext.getCurrentInstance().execute("migrationexception.show();");*/
		} catch (Exception e) {
			log.error("Exception Occured While saving Data", e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}


		return null;	
	}



	public List<ContactDetail> saveCustomerContactDetails(){

		List<ContactDetail> lstContactDetails = new ArrayList<ContactDetail>();

		for (AddContactDetailBean details : contactList) {

			ContactDetail contactDetail = new ContactDetail();

			contactDetail.setContactDetailId(details.getContactDetailsId());

			Customer customerinfo = new Customer();
			customerinfo.setCustomerId(getPkCustomerId());
			contactDetail.setFsCustomer(customerinfo);

			BizComponentData contactType = new BizComponentData();
			contactType.setComponentDataId(details.getContactTypeId());
			contactDetail.setFsBizComponentDataByContactTypeId(contactType);

			if(details.getCountryId()!=null){
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(details.getCountryId());
				contactDetail.setFsCountryMaster(countryMaster);
			}

			LanguageType languageType = new LanguageType();
			languageType.setLanguageId(session.getLanguageId());
			contactDetail.setFsLanguageType(languageType);
			if(details.getStateId()!=null){
				StateMaster stateMaster = new StateMaster();
				stateMaster.setStateId(details.getStateId());
				contactDetail.setFsStateMaster(stateMaster);
			}

			if(details.getDistrictId()!=null){

				DistrictMaster districtMaster = new DistrictMaster();
				districtMaster.setDistrictId(details.getDistrictId());
				contactDetail.setFsDistrictMaster(districtMaster);
			}

			if(details.getCityId()!=null){
				CityMaster cityMaster = new CityMaster();
				cityMaster.setCityId(details.getCityId());
				contactDetail.setFsCityMaster(cityMaster);
			}
			
			if(getCustomerSignatureDetailsave()){
				contactDetail.setActiveStatus(Constants.CUST_ACTIVE_INDICATOR);  
			}else{
				if(getDigitalSignature() != null){
					contactDetail.setActiveStatus(Constants.CUST_ACTIVE_INDICATOR);
				}else{
					contactDetail.setActiveStatus(Constants.CUST_ACTIVE_INDICATOR);
				}
			}


			//contactDetail.setArea(details.getArea());
			contactDetail.setBlock(details.getBlock());
			contactDetail.setFlat(details.getFlat());
			contactDetail.setStreet(details.getStreet());
			contactDetail.setTelephone(details.getTel());
			contactDetail.setBuildingNo(details.getBuildingNo());
			//added by nazish cr 14-JULY-2015
			contactDetail.setMobile(details.getMobileContact());
			contactDetail.setTelephoneCode(details.getTelephoneCode());

			if (details.getContactDetailsId() != null) {
				contactDetail.setCreatedBy(details.getCreatedByContact());
				contactDetail.setCreationDate(details.getCreatedDateContact());
				contactDetail.setUpdatedBy(session.getUserName());
				contactDetail.setLastUpdated(getCurrentTime());
			} else {
				contactDetail.setCreatedBy(session.getUserName());
				contactDetail.setCreationDate(getCurrentTime());
			}

			lstContactDetails.add(contactDetail);

		}

		return lstContactDetails;
	}


	public CustomerEmploymentInfo saveCustomerEmployeeInformation() {

		CustomerEmploymentInfo custEmp = new CustomerEmploymentInfo();

		custEmp.setCustEmpInfoId(getPkCustomerEmployeeId());

		Customer customer = new Customer();
		customer.setCustomerId(getPkCustomerId());
		custEmp.setFsCustomer(customer);

		LanguageType languageType = new LanguageType();
		languageType.setLanguageId(sessionStateManage.getLanguageId());
		custEmp.setFsLanguageType(languageType);

		CompanyMaster companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(sessionStateManage.getCompanyId());
		custEmp.setFsCompanyMaster(companyMaster);

		BizComponentData employemntType = new BizComponentData();
		employemntType.setComponentDataId(getEmploymentType());
		custEmp.setFsBizComponentDataByEmploymentTypeId(employemntType);

		BizComponentData occupation = new BizComponentData();
		occupation.setComponentDataId(getOccupation());
		custEmp.setFsBizComponentDataByOccupationId(occupation);

		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(sessionStateManage.getCountryId());
		custEmp.setFsCountryMaster(countryMaster);

		StateMaster stateMaster = new StateMaster();
		stateMaster.setStateId(getEmpStateId());
		custEmp.setFsStateMaster(stateMaster);

		DistrictMaster districtMaster = new DistrictMaster();
		districtMaster.setDistrictId(getEmpDistrictId());
		custEmp.setFsDistrictMaster(districtMaster);

		if(getEmpCityId()!=null){
			CityMaster cityMaster = new CityMaster();
			cityMaster.setCityId(getEmpCityId());
			custEmp.setFsCityMaster(cityMaster);
		}

		custEmp.setEmployerName(getEmployer());
		custEmp.setDepartment(getDepartment());
		custEmp.setArea(getEmparea());
		custEmp.setBlock(getEmpInfoBlock());
		custEmp.setStreet(getEmpInfoStreet());
		custEmp.setOfficeTelephone(getOfficeTel());
		custEmp.setPostal(getPostalCode());


		if (getPkCustomerEmployeeId() != null) {
			custEmp.setCreatedBy(getCreatedByEmployee());
			custEmp.setCreationDate(getCreationDateEmployee());
			custEmp.setUpdatedBy(session.getUserName());
			custEmp.setLastUpdated(getCurrentTime());
		} else {
			custEmp.setCreatedBy(session.getUserName());
			custEmp.setCreationDate(getCurrentTime());
		}

		return custEmp;
	}
	
	public BigDecimal customerIDProofId;

	public BigDecimal getCustomerIDProofId() {
		return customerIDProofId;
	}

	public void setCustomerIDProofId(BigDecimal customerIDProofId) {
		this.customerIDProofId = customerIDProofId;
	}

	public List<CustomerIdProof> saveCustomerProofInformation() {
		
		List<CustomerIdProof> lstCustIdProof = new ArrayList<CustomerIdProof>();
		
		try {
			
			
			if(createProofList.size() != 0){
				for (CreateProofTable createProofTable : createProofList) {

					CustomerIdProof custProof = new CustomerIdProof();
					
					if(dummiCustomerIdProofList.size() != 0){
						if(dummiCustomerIdProofList.get(0).getId_type().equalsIgnoreCase(createProofTable.getId_type())){
							if(dummiCustomerIdProofList.get(0).getId_number().equalsIgnoreCase(createProofTable.getId_number())){
								custProof.setCustProofId(dummiCustomerIdProofList.get(0).getCustomerIdProofId());
							}else{
								custProof.setCustProofId(createProofTable.getCustomerIdProofId());
							}
						}else{
							custProof.setCustProofId(createProofTable.getCustomerIdProofId());
						}
					}else{
						custProof.setCustProofId(createProofTable.getCustomerIdProofId());
					}
					
					Customer customer = new Customer();
					customer.setCustomerId(getPkCustomerId());
					custProof.setFsCustomer(customer);

					LanguageType languageType = new LanguageType();
					languageType.setLanguageId(session.getLanguageId());
					custProof.setFsLanguageType(languageType);

					BizComponentData customerType = new BizComponentData();
					customerType.setComponentDataId(getGeneralService().getComponentId(Constants.CUSTOMERTYPE_INDU, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
					custProof.setFsBizComponentDataByCustomerTypeId(customerType);

					BizComponentData identityType = new BizComponentData();
					identityType.setComponentDataId(new BigDecimal(createProofTable.getId_type()));
					custProof.setFsBizComponentDataByIdentityTypeId(identityType);

					BizComponentData idFor = new BizComponentData();
					idFor.setComponentDataId(new BigDecimal(createProofTable.getId_for()));
					custProof.setFsBizComponentDataByIdentityFor(idFor);

					BizComponentData idType = new BizComponentData();
					idType.setComponentDataId(new BigDecimal(createProofTable.getId_type()));
					custProof.setFsBizComponentDataByIdentityTypeId(idType);

					custProof.setIdentityInt(createProofTable.getId_number());
					
					if(getCustomerSignatureDetailsave()){
						custProof.setIdentityStatus(Constants.CUST_ACTIVE_INDICATOR);
					}else{
						if(getDigitalSignature() != null){
							custProof.setIdentityStatus(Constants.CUST_ACTIVE_INDICATOR);
						}else{
							custProof.setIdentityStatus(Constants.CUST_ACTIVE_INDICATOR);
						}
					}
					
					if(createProofTable.getDate_expiary()!=null){
						Date expDate = new SimpleDateFormat("dd/MM/yyyy").parse(createProofTable.getDate_expiary());
						custProof.setIdentityExpiryDate(expDate);
					}


					if (createProofTable.getCustomerIdProofId() != null) {
						custProof.setUpdatedBy(session.getUserName());
						custProof.setLastUpdatedDate(getCurrentTime());
						custProof.setCreatedBy(createProofTable.getCreatedByIdProof());
						custProof.setCreationDate(createProofTable.getCreatedDateIdProof());
					} else {
						custProof.setCreatedBy(session.getUserName());
						custProof.setCreationDate(getCurrentTime());
					}

					lstCustIdProof.add(custProof);
				}
			}else{


				CustomerIdProof custProof = new CustomerIdProof();

				custProof.setCustProofId(getCustomerIDProofId());

				Customer customer = new Customer();
				customer.setCustomerId(getPkCustomerId());
				custProof.setFsCustomer(customer);

				LanguageType languageType = new LanguageType();
				languageType.setLanguageId(session.getLanguageId());
				custProof.setFsLanguageType(languageType);

				BizComponentData customerType = new BizComponentData();
				customerType.setComponentDataId(getGeneralService().getComponentId(Constants.CUSTOMERTYPE_INDU, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
				custProof.setFsBizComponentDataByCustomerTypeId(customerType);

				/*BizComponentData idFor = new BizComponentData();
				idFor.setComponentDataId(new BigDecimal(createProofTable.getId_for()));
				custProof.setFsBizComponentDataByIdentityFor(idFor);*/

				BizComponentData idType = new BizComponentData();
				idType.setComponentDataId(new BigDecimal(getIdType()));
				custProof.setFsBizComponentDataByIdentityTypeId(idType);

				custProof.setIdentityInt(getIdNumber());
				custProof.setIdentityStatus(Constants.CUST_ACTIVE_INDICATOR);
				custProof.setCreatedBy(session.getUserName());
				custProof.setCreationDate(getCurrentTime());
				

				lstCustIdProof.add(custProof);
			
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return lstCustIdProof;
	}

	public DmsMas saveSmartCardInformationForDmsMas() {
		
		DmsMas smartCard = new DmsMas();

		try {
			
			/* mapIdentityType = ruleEngine.getComponentData("Identity Type");

				  BigDecimal identityTpeId = null;
				  for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
					  if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
						  identityTpeId = entry.getKey();
						  break;
					  }
				  }*/
			//smartCard.setAppnCountry(sessionStateManage.getCountryId().toString());
			List<CountryMaster> alphacode = icustomerRegistrationService.getCountryAlpha2Code(session.getCountryId());
			if(alphacode.size()!=0){
				String countryAlphaCode = alphacode.get(0).getCountryAlpha2Code()==null ? "" : alphacode.get(0).getCountryAlpha2Code();
				smartCard.setAppnCountry(countryAlphaCode);
			}

			//smartCard.setAppnCountry(Constants.KUWAIT_ALPHA_TWO_CODE);
			smartCard.setDocType(Constants.CIVIL_ID_VALUE);
			smartCard.setIdno(getCivilId());
			smartCard.setSerialNo(getSerialno());
			smartCard.setDocumentNo(getDocumentno());
			smartCard.setEFuname(getFullNameEn());
			smartCard.setLFstName(getFirstNameAr());
			smartCard.setLSurname(getArabicSurname());
			smartCard.setLFatherName(getFatherNameArabic());
			smartCard.setLFuname(getFullNameAr());
			smartCard.setLGfName(getArabicGFName());
			smartCard.setNtcdId(getNationalityLatin());
			//smartCard.setNationalityAr(getNationalityAr());
			smartCard.setGender(getGenderLatin());
			smartCard.setIssuedat(new SimpleDateFormat("dd/MM/yyyy").parse(getIssuedate()));
			smartCard.setExpireDt(new SimpleDateFormat("dd/MM/yyyy").parse(getExpirydate()));
			smartCard.setBirthdat(new SimpleDateFormat("dd/MM/yyyy").parse(getBirthdate()));
			smartCard.setTel1(getTelephone1());
			smartCard.setTel2(getTelephone2());
			smartCard.setEmailId(getScardemail());
			smartCard.setBloodTyp(getBloodType());
			smartCard.setBldgno(getBuilding_no());
			//smartCard.setUnitType(getUnit_type());
			//smartCard.setUnitNo(getUnit_no());
			smartCard.setFloorno(getScardfloor());
			smartCard.setStreet(getScardstreet());
			smartCard.setBlockNo(getsCardblock());
			smartCard.setDistrict(getScarddistrict());
			smartCard.setGuardIdno(getGuardianCivilId());
			smartCard.setAddrUniqKey(getAddressRef());
			smartCard.setProgno("JAVA");//Hard Coded Value

			if (getSmartCardId() != null) {
				smartCard.setDmsIdMasId(getSmartCardId());
				//smartCard.(getCreatedByCustomer());
				smartCard.setUpddat(getCreationDateCustomer());

			} else {
				smartCard.setCreator(sessionStateManage.getUserName());
				//CR for creation date based on application country
				//smartCard.setCreateDate(new Date());
				smartCard.setCrtdat(getCurrentTime());
				// getIcustomerRegistrationService().saveCustomer(customer);
				// setPkCustomerId(customer.getCustomerId());
			}
			//generalService.saveOrUpdate((T)smartCard);
			setSmartCardId(smartCard.getDmsIdMasId());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return smartCard;
	}

	private String exceptionMessage;
	private String allow_Ind;
	private String custRefNo;

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}


	public String getAllow_Ind() {
		return allow_Ind;
	}

	public void setAllow_Ind(String allow_Ind) {
		this.allow_Ind = allow_Ind;
	}

	public String getCustRefNo() {
		return custRefNo;
	}

	public void setCustRefNo(String custRefNo) {
		this.custRefNo = custRefNo;
	}

	public void checkingIdWithDBForProcessing() throws ParseException{

		if(getMobileNoFetch()!=null){
			fetcDataThroughMobileNo();
		}else{
		List<String> outprocedurevalues = new ArrayList<String>();

		try {
			outprocedurevalues = icustomerRegistrationService.getCustomerRefOrSave(new BigDecimal(getIdType()), getIdNumber(), Constants.Individual);

			if(outprocedurevalues.size()!=0){
				setAllow_Ind(outprocedurevalues.get(0));
				setCustRefNo((outprocedurevalues.get(1)) == null ? "" : (outprocedurevalues.get(1)));

				if(getAllow_Ind().equalsIgnoreCase(Constants.Yes)){
					try {
						
						nextRemitterInfo();
					
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					RequestContext.getCurrentInstance().execute("printCustomerReference.show();");
				}

			}

		} catch (AMGException e) {
			log.error("Procedure CAll Exception: "+e);

			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}

		}

	}


	public void checkingIdWithDBForProcessingSmartCard(){

		mapIdentityType = ruleEngine.getComponentData("Identity Type");

		BigDecimal identityTpeId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
				identityTpeId = entry.getKey();
				break;
			}
		}

		List<String> outprocedurevalues = new ArrayList<String>();

		try {
			outprocedurevalues = icustomerRegistrationService.getCustomerRefOrSave(identityTpeId, getCivilId(), Constants.Individual);

			if(outprocedurevalues.size()!=0){
				setAllow_Ind(outprocedurevalues.get(0));
				setCustRefNo((outprocedurevalues.get(1)) == null ? "" : (outprocedurevalues.get(1)));

				if(getAllow_Ind().equalsIgnoreCase(Constants.Yes)){
					try {
						nextRemitterInfo();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					RequestContext.getCurrentInstance().execute("printCustomerReference.show();");
				}

			}

		} catch (AMGException e) {
			// TODO Auto-generated catch block
			CollectionUtil collUtil = new CollectionUtil();
			setExceptionMessage(collUtil.formatErrorMessage(e.getMessage()));

			System.out.println("*******Error message ********"+getExceptionMessage());
			//Added by kani end

			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}



	}

	/* private String countryLphaCode=null;


		  public String getCountryLphaCode() {
			return countryLphaCode;
		}

		public void setCountryLphaCode(String countryLphaCode) {
			this.countryLphaCode = countryLphaCode;
		}

		public void getCountryAlphCode(){
			  List<CountryMaster> coutryAlphaList = generalService.getCountryAlphaList(sessionStateManage.getCountryId());

			  if(coutryAlphaList.size()>0){
				  setCountryLphaCode(coutryAlphaList.get(0).getCountryAlpha2Code());
			  }

		  }*/

	//added by nazish if id already expired and need to update.

	public void idExpiredGoNext() throws ParseException{
		clearRemitterInfo();
		clearContactDetail();
		clearEmploymentInfo();
		clearProofInfo();
		setLevel(null);
		setArticle(null);
		setBooRemitterInfo(true);
		setBooIdDetail(false);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooEmploymentDetails(false);
		setBooProof(false);
		fetchCustomerInfo();
		/*setBooOtherId(false);
				setBooCivilId(true);*/
		setRenderSavebutton(false);
		setRenderSignature(false);
		setBooVerifyAllRen(false);
		setRenderverication(false);
		setSignaturePanelRender(false);

		setRenderUpdate(false);
		setRenderRegistration(false);
		setRenderVerifyReport(false);
		setRenderFinal(false);
		setExitRender(false);
		setNextSignaturePanel(false);
		setNextUpdateRender(false);
		setBooManualGo(false);
		setRenderIntroducePanel(true);

	}




	//Added by nazish for Non OCR Document

	/*  public void checkIdNonOcr() throws IOException, ParseException  {

			  Properties prop = new Properties();
				InputStream input = null;

				System.out.println(new GettingDBConfiguration().getPath());
				input = new FileInputStream(new GettingDBConfiguration().getPath());

				// load a properties file
				prop.load(input);

				// get the property value and print it out
				String url = prop.getProperty("url");  
				String username = prop.getProperty("username");
				String password = prop.getProperty("password");

				boolean duplicate=duplicateCheck();
				if(duplicate)
				{
					//setEnableScan(false);
					StringBuffer urlBuffer = new StringBuffer();

					String ampersand = "&";

					String equals = "=";

					String rootContext = "http://192.168.100.195/arcmateintegration/aspforms/frmIMain.aspx?action=CheckDocumentExistsScript&Repository=AlMulla%20Group&ArcMateField_DocTypeId=e9f46cda-1b69-4bb3-942c-c02fcd9e3721&username=admin&Password=admin&Civil%20ID"; 
					urlBuffer.append(rootContext).append(equals).append(getIdnumberProof());
					try {

						URL knetRequest = new URL(urlBuffer.toString());
						HttpURLConnection httConn = null;
						BufferedReader in = null;

						httConn = (HttpURLConnection) knetRequest.openConnection();
						in = new BufferedReader(new InputStreamReader(
								httConn.getInputStream()));

						String str = in.readLine();

						System.out.println("IIIIIIIIIII== " + str);

						in.close();
					} catch (MalformedURLException e) {
						log.info("Connection Problem: "+e);
					} catch (IOException ie) {
						log.info("Connection Problem: "+ie);
					}
				}else
				{
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('duplicateproof').show();");
				}

			}*/



	// ###################################  ALL REPORT GENERATION #########################################


	JasperPrint jasperPrint; 
	private List<PepReport> pepReportList=new CopyOnWriteArrayList<PepReport>();
	private List<CustomerLogReport> customerLogReportList=new CopyOnWriteArrayList<CustomerLogReport>();
	private List<CustomerMainReport> customerRegistrationReportList=new CopyOnWriteArrayList<CustomerMainReport>();
	List<CustomerContactDetailView> customerContactDetailList=new CopyOnWriteArrayList<CustomerContactDetailView>();
	List<CustomerEmployeeInfoView> customerEmployeeInfoList=new CopyOnWriteArrayList<CustomerEmployeeInfoView>();
	List<CustomerIdproofView> customerIdProofviewList=new CopyOnWriteArrayList<CustomerIdproofView>();

	//PEP Description Report Generation ---------------> RAHAMATHALI SHAIK
	public void pepreportGeneration(){
		pepReportList.clear();
		PepReport pepReport=new PepReport();
		pepReport.setCivilId(getIdNumber());
		pepReport.setCountry(specialCustomerDealRequestService.getBankCountryNameForUpdate(session.getCountryId(),session.getLanguageId()));
		pepReport.setCountryBranch(session.getLocation());
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		
		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		//String logoPath = realPath + Constants.LOGO_PATH;
		//String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath(Constants.LOGO_PATH); 
		pepReport.setLogoPath(logoPath);
		pepReport.setCustomerName(getFirstName());
		if(generalService.getValidExpiryDate(getIdNumber())!=null)
		{
			pepReport.setValidUpto(new SimpleDateFormat("dd/MM/yyyy").format(generalService.getValidExpiryDate(getIdNumber())));
		}

		pepReportList.add(pepReport);
	}

	public void init() throws JRException{  
		JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(pepReportList);  
		String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/PEPDeclarationReport.jasper");        
		jasperPrint=JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);  
	}
	public void generatePepDeclarationReport(ActionEvent actionEvent) throws JRException, IOException{
		init();  
		HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=PEPDeclarationReport.pdf");  
		ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
		JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
		FacesContext.getCurrentInstance().responseComplete();  

	}

	// Report Generation for Customer Log Difference---------------> RAHAMATHALI SHAIK
	public void init1() throws JRException{  
		JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(customerLogReportList);  
		String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/customerlogdifferencenewreport.jasper");        
		jasperPrint=JasperFillManager.fillReport(reportPath,new HashMap(),beanCollectionDataSource);  
	}
	public void generateLogdifferenceReport(ActionEvent actionEvent) throws JRException, IOException{
		fetchUpdateLogInfo(getPkCustomerId(),getVerifyToken());
		init1();  
		HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=CustomerLogDifferenceReport.pdf");  
		ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
		JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
		FacesContext.getCurrentInstance().responseComplete();  

		if(getPkCustomerId()!=null){
			updateVerificationToken(getPkCustomerId());
		}

	}

	public void fetchUpdateLogInfo(BigDecimal customerId,String verificationToken){
		customerLogReportList.clear();
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		

		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(getCountryName() != null && getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		
		
		List<CustomerChangeLog> customerChangeLogList=icustomerRegistrationService.findCustomerChangeLog(customerId,verificationToken);
		for(CustomerChangeLog customerChangeLog:customerChangeLogList){
			CustomerLogReport customerLogReport=new CustomerLogReport();
			customerLogReport.setFromColumnName(customerChangeLog.getFromColumnName());
			customerLogReport.setFromTableName(customerChangeLog.getFromTableName());
			customerLogReport.setNewValue(customerChangeLog.getNewValue());
			customerLogReport.setOldValue(customerChangeLog.getOldValue());
			customerLogReport.setLogoPath(logoPath);
			customerLogReportList.add(customerLogReport);
		}
	}
	// RePort Generation For Customer Registration---------------> RAHAMATHALI SHAIK

	public void init2() throws JRException{  

		JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(customerRegistrationReportList);  
		String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/CustomerRegistrationNewReport.jasper");        
		jasperPrint=JasperFillManager.fillReport(reportPath,new HashMap(),beanCollectionDataSource);  
	}
	public void generateCustomerRegistrationReport(ActionEvent actionEvent) throws JRException, IOException{
		fetchCustomerRegistrationInfo(getPkCustomerId());

		init2();  
		HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=CustomerRegistrationReport.pdf");  
		ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
		JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
		FacesContext.getCurrentInstance().responseComplete();  

	}



	public void fetchCustomerRegistrationInfo(BigDecimal customerId){
		customerRegistrationReportList.clear();
		customerContactDetailList.clear();
		customerEmployeeInfoList.clear();
		customerIdProofviewList.clear();
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		
		
		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		
		
		
		//String logoPath = realPath + Constants.LOGO_PATH;
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		List<CustomerInfoView> customerRegistrationList=icustomerRegistrationService.findCustomerRegistration(customerId);

		List<CustomerContactDetailView> customerContactDetail=icustomerRegistrationService.findCustomerContactDetails(customerId);
		List<CustomerEmployeeInfoView> customerEmployeeInfo= icustomerRegistrationService.findCustomerEmployeeInfo(customerId);
		List<CustomerIdproofView> customerIdProof= icustomerRegistrationService.findCustomerIdProof(customerId);

		for(CustomerContactDetailView customerContact:customerContactDetail){
			CustomerContactDetailView customerContactDetailView=new CustomerContactDetailView();
			customerContactDetailView.setComponentDescription(customerContact.getComponentDescription());
			customerContactDetailView.setStateName(customerContact.getStateName());
			customerContactDetailView.setDistcritName(customerContact.getDistcritName());
			customerContactDetailView.setCityName(customerContact.getCityName());
			customerContactDetailView.setContactTelephone(customerContact.getContactTelephone());
			customerContactDetailView.setContactFlat(customerContact.getContactFlat());
			customerContactDetailView.setContactBlockNo(customerContact.getContactBlockNo());
			customerContactDetailView.setContactStreet(customerContact.getContactStreet());
			customerContactDetailView.setContactArea(customerContact.getContactArea());
			customerContactDetailView.setContactBuildingNo(customerContact.getContactBuildingNo());
			customerContactDetailList.add(customerContactDetailView);

		}
		for(CustomerEmployeeInfoView customerEmployee:customerEmployeeInfo){
			CustomerEmployeeInfoView customerEmployeeInfoView=new CustomerEmployeeInfoView();
			customerEmployeeInfoView.setEmployeeTypeName(customerEmployee.getEmployeeTypeName());
			customerEmployeeInfoView.setEmployeeProofDesc(customerEmployee.getEmployeeProofDesc());
			customerEmployeeInfoView.setEmployeeBlock(customerEmployee.getEmployeeBlock());
			customerEmployeeInfoView.setEmployeeArea(customerEmployee.getEmployeeArea());
			customerEmployeeInfoView.setDepartment(customerEmployee.getDepartment());
			customerEmployeeInfoView.setEmployeePostal(customerEmployee.getEmployeePostal());
			customerEmployeeInfoView.setEmployeeName(customerEmployee.getEmployeeName());
			customerEmployeeInfoView.setEmployeeState(customerEmployee.getEmployeeState());
			customerEmployeeInfoView.setEmployeeTelePhone(customerEmployee.getEmployeeTelePhone());
			customerEmployeeInfoView.setStateName(customerEmployee.getStateName());
			customerEmployeeInfoView.setDistcritName(customerEmployee.getDistcritName());
			customerEmployeeInfoView.setCityName(customerEmployee.getCityName());
			customerEmployeeInfoList.add(customerEmployeeInfoView);

		}
		for(CustomerIdproofView customerIdproof:customerIdProof){
			CustomerIdproofView customerIdproofView=new CustomerIdproofView();
			customerIdproofView.setIdProofFor(customerIdproof.getIdProofFor());
			customerIdproofView.setIdProofTypeDesc(customerIdproof.getIdProofTypeDesc());
			customerIdproofView.setIdProofInt(customerIdproof.getIdProofInt());
			customerIdproofView.setIdProofExpiredDate(customerIdproof.getIdProofExpiredDate());
			customerIdProofviewList.add(customerIdproofView);
		}

		for(CustomerInfoView customer:customerRegistrationList){

			CustomerMainReport customerMainReport=new CustomerMainReport();
			customerMainReport.setTitle(customer.getTitle());
			customerMainReport.setTitleLocal(customer.getTitleLocal());
			customerMainReport.setFirstName(customer.getFirstName());
			customerMainReport.setFirstNameLocal(customer.getFirstNameLocal());
			customerMainReport.setMiddleName(customer.getMiddleName());
			customerMainReport.setMiddleNameLocal(customer.getMiddleNameLocal());
			customerMainReport.setLastName(customer.getLastName());
			customerMainReport.setLastNameLocal(customer.getLastNameLocal());
			customerMainReport.setShortName(customer.getShortName());
			customerMainReport.setShortNameLocal(customer.getShortNameLocal());
			String nationality =   getGeneralService().getNationalityName(session.getLanguageId(), customer.getNationality());
			if(nationality!=null){
				customerMainReport.setNationality(nationality);
			}

			customerMainReport.setAmlStatus(customer.getAmlStatus());
			customerMainReport.setEmail(customer.getEmail());
			customerMainReport.setAlternativeEmail(customer.getAlterEmailId());
			customerMainReport.setGender(customer.getGender());
			customerMainReport.setDateOfBirth(customer.getDateOfBirth());
			customerMainReport.setMobileNo(customer.getMobile());
			customerMainReport.setCustomerReference(customer.getCustomerReference());
			customerMainReport.setIntroducedBy(customer.getIntroducedBy());
			//  customerMainReport.setIntroducedDate(customer.getIntroducedDate());
			customerMainReport.setPepIndicator(customer.getPepIndicator());
			customerMainReport.setMedicalInsurence(customer.getMedicalInsurenceIndicator());
			customerMainReport.setArtical(customer.getArticalName());
			customerMainReport.setLevel(customer.getArticalLevel());
			customerMainReport.setIncomeRange(customer.getIncomeRangeName());
			customerMainReport.setDailyLimit(customer.getDailyTranscationLimit());
			customerMainReport.setWeeklyLimit(customer.getWeeklyTransactionLimit());
			customerMainReport.setMonthlyLimit(customer.getMonthlyTransactionLimit());
			/*customerMainReport.setQuaterlyLimit(customer.getQuaterlyTransactionLimit());
			customerMainReport.setHalfyearlyLimit(customer.getHalfYearlyTransactionLimit());*/
			customerMainReport.setAnnualLimit(customer.getAnnualTransactionLimit());
			customerMainReport.setLogoPath(logoPath);
			customerMainReport.setSignature(customer.getSignature());
			customerMainReport.setComponentDescription(customer.getComponentDescription());
			customerMainReport.setCountryName(customer.getCountryName());
			customerMainReport.setSubReport(subReportPath);
			customerMainReport.setContactDetailList(customerContactDetailList);
			customerMainReport.setEmployeeInfoList(customerEmployeeInfoList);
			customerMainReport.setIdProofList(customerIdProofviewList);
			customerRegistrationReportList.add(customerMainReport);
		}



	}

	//Added by nazish for Non OCR Document

	public Boolean renderModifyScan= false;
	public Boolean readOnlyIdNumber= true;

	public Boolean getRenderModifyScan() {
		return renderModifyScan;
	}

	public void setRenderModifyScan(Boolean renderModifyScan) {
		this.renderModifyScan = renderModifyScan;
	}

	public Boolean getReadOnlyIdNumber() {
		return readOnlyIdNumber;
	}

	public void setReadOnlyIdNumber(Boolean readOnlyIdNumber) {
		this.readOnlyIdNumber = readOnlyIdNumber;
	}

	public void mainCheckId() throws IOException, ParseException{

		List<IdentityTypeMaster> ocrStatusList = icustomerRegistrationService.getOcrList(getIdTypeproof());
		if(ocrStatusList.size()>0){
			if(ocrStatusList.get(0).getOcrStstus().equalsIgnoreCase(Constants.Yes)){
				checkId();
			}else{
				checkIdNonOcr();
			}
		}
	}

	public void mainScanId() throws IOException, ParseException {

		List<IdentityTypeMaster> ocrStatusList = icustomerRegistrationService.getOcrList(getIdTypeproof());
		if(ocrStatusList.size()>0){
			if(ocrStatusList.get(0).getOcrStstus().equalsIgnoreCase(Constants.Yes)){
				scan();
			}else{

				setEnableScan(false);
				RequestContext.getCurrentInstance().execute("adddocument.show();");

			}
		}
	}


	@SuppressWarnings("static-access")
	public void checkIdNonOcr() throws IOException, ParseException  {

		ReadScanProperties readScan = new ReadScanProperties();

		boolean duplicate=duplicateCheck();
		if(duplicate)
		{
			//setEnableScan(false);
			StringBuffer urlBuffer = new StringBuffer();

			String equals = "=";
			String ampersand = "&";

			String rootContext = readScan.getPropertiesValue().get("urlchecknonocr"); 
			String usernamevalue = readScan.getPropertiesValue().get("usernamevalue");
			String passwordvalue = readScan.getPropertiesValue().get("passwordvalue");
			String username = readScan.getPropertiesValue().get("username");
			String password = readScan.getPropertiesValue().get("password");
			String arcmateField_DocID = readScan.getPropertiesValue().get("ArcmateField_DocID");
			String passportArccode = readScan.getPropertiesValue().get("Passport_ArcMateField_FileCategoryID");
			String civilid20 = readScan.getPropertiesValue().get("civilid20");
			String arcMateField_FileCategoryID = readScan.getPropertiesValue().get("ArcMateField_FileCategoryID"); 
			String urlfileroot = readScan.getPropertiesValue().get("urlfileid");

			String idNumberNocr = null;
			Boolean nonocrid = false;
			mapIdentityType = ruleEngine.getComponentData("Identity Type");

			BigDecimal identityTpeId = null;
			for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
				if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
					identityTpeId = entry.getKey();
					break;
				}
			}

			for (CreateProofTable createProofTable : createProofList) {

				if (createProofTable.getId_type() != null ) {

					if(new BigDecimal(createProofTable.getId_type()).intValue()== identityTpeId.intValue())
					{
						idNumberNocr = createProofTable.getId_number();
						nonocrid = true;
						break;
					}else{
						nonocrid = false;
					}
				}else{
					nonocrid = false;
				}
			}

			if(nonocrid){
				urlBuffer.append(rootContext).append(ampersand).append(username).append(equals).append(usernamevalue)
				.append(ampersand).append(password).append(equals).append(passwordvalue).append(ampersand).append(civilid20).append(equals).append(idNumberNocr);
			}else{

				//CheckDocumentExistsScript Action

				urlBuffer.append(rootContext).append(ampersand).append(username).append(equals).append(usernamevalue)
				.append(ampersand).append(password).append(equals).append(passwordvalue).append(ampersand).append(civilid20).append(equals).append(getIdnumberProof());

			}

			try {

				URL knetRequest = new URL(urlBuffer.toString());
				HttpURLConnection httConn = null;
				BufferedReader in = null;

				httConn = (HttpURLConnection) knetRequest.openConnection();
				in = new BufferedReader(new InputStreamReader(
						httConn.getInputStream()));

				String str = in.readLine();

				if(str.equalsIgnoreCase("0")){
					in.close();
					RequestContext.getCurrentInstance().execute("adddocument.show();");
					//RequestContext context = RequestContext.getCurrentInstance();
					//context.execute("PF('adddocument').show();");
					//setEnableScan(false);
					//RequestContext.getCurrentInstance().execute("scanRecord.show();");
					//AddDocument

				}else if(str.startsWith("Doc ID")){
					//CheckFileExistsScript

					String[] s1 = str.split("=");

					in.close();
					StringBuffer urlBuffer1 = new StringBuffer();
					urlBuffer1.append(urlfileroot).append(ampersand).append(username).append(equals).append(usernamevalue)
					.append(ampersand).append(password).append(equals).append(passwordvalue).append(ampersand).append(arcMateField_FileCategoryID).append(equals).append(passportArccode).append(ampersand).append(arcmateField_DocID)
					.append(equals).append(s1[1]);

					try {

						URL request = new URL(urlBuffer1.toString());
						HttpURLConnection httConn1 = null;
						BufferedReader in1 = null;

						httConn1 = (HttpURLConnection) request.openConnection();
						in1 = new BufferedReader(new InputStreamReader(
								httConn1.getInputStream()));

						String str1 = in1.readLine();

						if(str1.startsWith("File ID")){
							setRenderModifyScan(true);

							SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

							getFetchContactTypeList();

							String df = dateformat.format(getDateExp());

							if (getExpDate().compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {

								duplicate=duplicateCheck();
								if(duplicate)
								{
									CreateProofTable createProofTable = new CreateProofTable(
											getIdFor().toPlainString(), getIdTypeproof()
											.toPlainString(), getIdnumberProof(), df,
											"insert", 0, idForMap.get(this.idFor),
											idTypeMap.get(this.idTypeproof));
									createProofTable.setCustomerIdProofId(null);

									createProofList.add(createProofTable);
									setBooidproofDatatable(true);
									setRenderModifyScan(false);
									/*setEnableCheckId(true);*/
									setEnableScan(true);
									setRenderSignature(true);
									clearProofInfo();
								}else
								{
									RequestContext context = RequestContext.getCurrentInstance();
									context.execute("PF('duplicateproof').show();");
								}
							} else {
								setRenderModifyScan(true);
								setEnableScan(true);
								setRenderSignature(true);
								RequestContext context = RequestContext.getCurrentInstance();
								context.execute("PF('idExpirednocr').show();");


							}
						}else{
							setRenderModifyScan(true);
							setEnableScan(true);
							RequestContext.getCurrentInstance().execute("filenotfounds.show();");

						}

					} catch (MalformedURLException e) {
						log.info("Connection Problem: "+e);
					} catch (IOException ie) {
						log.info("Connection Problem: "+ie);
					}





				}

				in.close();
			} catch (MalformedURLException e) {
				log.info("Connection Problem: "+e);
			} catch (IOException ie) {
				log.info("Connection Problem: "+ie);
			}
		}else
		{
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('duplicateproof').show();");
		}

	}

	@SuppressWarnings("static-access")
	public void addDocumentBrowser(){

		ReadScanProperties readScan = new ReadScanProperties();

		StringBuffer urlBuffer = new StringBuffer();

		String equals = "=";
		String ampersand = "&";
		String rootContextadd = readScan.getPropertiesValue().get("urladddoc"); 
		String passportArccode = readScan.getPropertiesValue().get("Passport_ArcMateField_FileCategoryID");
		String gccnationalidArccode = readScan.getPropertiesValue().get("GCCNationalID_ArcMateField_FileCategoryID");
		String civilid20 = readScan.getPropertiesValue().get("civilid20");
		String passport = readScan.getPropertiesValue().get("passport");
		String gccnationalid = readScan.getPropertiesValue().get("gccnationalid");
		String fileCatgoryId = readScan.getPropertiesValue().get("FileCategoryID");
		String idNumberNocr = null;
		Boolean nonocrid = false;
		mapIdentityType = ruleEngine.getComponentData("Identity Type");

		BigDecimal identityTpeId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
				identityTpeId = entry.getKey();
				break;
			}
		}

		for (CreateProofTable createProofTable : createProofList) {

			if (createProofTable.getId_type() != null ) {

				if(new BigDecimal(createProofTable.getId_type()).intValue()== identityTpeId.intValue())
				{
					idNumberNocr = createProofTable.getId_number();
					nonocrid = true;
					break;
				}else{
					nonocrid = false;
				}
			}else{
				nonocrid = false;
			}
		}

		if(nonocrid){
			if(getIdTypeproof().intValue() == getGeneralService().getComponentId(passport,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()){
				urlBuffer.append(rootContextadd).append(ampersand).append(civilid20).append(equals).append(idNumberNocr)
				.append(ampersand).append(fileCatgoryId).append(equals).append(passportArccode);
			}else if(getIdTypeproof().intValue() == getGeneralService().getComponentId(gccnationalid,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()){

				urlBuffer.append(rootContextadd).append(ampersand).append(civilid20).append(equals).append(idNumberNocr)
				.append(ampersand).append(fileCatgoryId).append(equals).append(gccnationalidArccode);

			}
		}else{

			//CheckDocumentExistsScript Action

			if(getIdTypeproof().intValue() == getGeneralService().getComponentId(passport,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()){
				urlBuffer.append(rootContextadd).append(ampersand).append(civilid20).append(equals).append(getIdnumberProof())
				.append(ampersand).append(fileCatgoryId).append(equals).append(passportArccode);
			}else if(getIdTypeproof().intValue() == getGeneralService().getComponentId(gccnationalid,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()){

				urlBuffer.append(rootContextadd).append(ampersand).append(civilid20).append(equals).append(getIdnumberProof())
				.append(ampersand).append(fileCatgoryId).append(equals).append(gccnationalidArccode);

			}

		}

		System.out.println("Add Document of Non OCR URL :  " + urlBuffer.toString());
		log.info("Add Document of Non OCR URL :  " + urlBuffer.toString());

		try {

			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();



			context.redirect(urlBuffer.toString());



		} catch (Exception e) {

			log.info("Problem to redirect: " + e);

		}

	}

	public void modifyScan(){
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('modifyscan').show();");
	}

	@SuppressWarnings("static-access")
	public void modifyNonOcr(){

		setEnableScan(true);
		setRenderModifyScan(false);

		ReadScanProperties readScan = new ReadScanProperties();

		StringBuffer urlBuffer = new StringBuffer();

		String equals = "=";
		String ampersand = "&";

		String rootContext = readScan.getPropertiesValue().get("urlmodifydoc");

		String passportArccode = readScan.getPropertiesValue().get("Passport_ArcMateField_FileCategoryID");
		String gccnationalidArccode = readScan.getPropertiesValue().get("GCCNationalID_ArcMateField_FileCategoryID");
		String civilid20 = readScan.getPropertiesValue().get("civilid20");
		String passport = readScan.getPropertiesValue().get("passport");
		String gccnationalid = readScan.getPropertiesValue().get("gccnationalid");
		String fileCatgoryId = readScan.getPropertiesValue().get("FileCategoryID");

		String idNumberNocr = null;
		Boolean nonocrid = false;
		Boolean ocrid = false;
		mapIdentityType = ruleEngine.getComponentData("Identity Type");

		BigDecimal identityTpeId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
				identityTpeId = entry.getKey();
				break;
			}
		}

		for (CreateProofTable createProofTable : createProofList) {

			if (createProofTable.getId_type() != null ) {

				if(new BigDecimal(createProofTable.getId_type()).intValue()== identityTpeId.intValue())
				{
					//idNumberNocr = createProofTable.getId_number();
					ocrid = true;
					break;
				}else{
					nonocrid = true;
				}
			}
		}
		idNumberNocr = getIdnumberProof();
		if(!nonocrid && !ocrid){

			if(getIdTypeproof().intValue()== getGeneralService().getComponentId(passport,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()){
				urlBuffer.append(rootContext).append(ampersand).append(civilid20).append(equals).append(idNumberNocr)
				.append(ampersand).append(fileCatgoryId).append(equals).append(passportArccode);
			}else if(getIdTypeproof().intValue()==getGeneralService().getComponentId(gccnationalid,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()){

				urlBuffer.append(rootContext).append(ampersand).append(civilid20).append(equals).append(idNumberNocr)
				.append(ampersand).append(fileCatgoryId).append(equals).append(gccnationalidArccode);

			}

		}else{

			//CheckDocumentExistsScript Action

			for (CreateProofTable createProof : createProofList) {
				if(new BigDecimal(createProof.getId_type()).intValue()== getGeneralService().getComponentId(passport,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()){
					urlBuffer.append(rootContext).append(ampersand).append(civilid20).append(equals).append(createProof.getId_number())
					.append(ampersand).append(fileCatgoryId).append(equals).append(passportArccode);
					break;
				}else if(new BigDecimal(createProof.getId_type()).intValue()==getGeneralService().getComponentId(gccnationalid,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()){

					urlBuffer.append(rootContext).append(ampersand).append(civilid20).append(equals).append(createProof.getId_number())
					.append(ampersand).append(fileCatgoryId).append(equals).append(gccnationalidArccode);
					break;										
				}
			}
		}

		System.out.println("Modify Document of Non OCR URL :  " + urlBuffer.toString());
		log.info("Modify Document of Non OCR URL :  " + urlBuffer.toString());

		try {

			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();



			context.redirect(urlBuffer.toString());



		} catch (Exception e) {

			log.info("Problem to redirect: " + e);

		}

	}

	// added for mobile format validation

	public void validateMobile(FacesContext context, UIComponent component, Object value) throws ValidatorException {


		String returnString = generalService.validateMobileTelephone(sessionStateManage.getCountryAlphaTwoCode(), value.toString(), Constants.MOBILE_CONTACT);
		if (returnString.equalsIgnoreCase(Constants.Yes)) {

		} else {

			FacesMessage msg = new FacesMessage("Mobile", returnString);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

		}


	}


	public void validateTelephone(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if(getContactTypeId()!=null){
			if(getContactTypeId().intValue() == getGeneralService().getComponentId(Constants.RESIDENCE,sessionStateManage.getLanguageId())
					.getFsBizComponentData().getComponentDataId().intValue()){

				String returnString = generalService.validateMobileTelephone(sessionStateManage.getCountryAlphaTwoCode(), value.toString(), Constants.RESIDENCE_CONTACT);
				if (returnString.equalsIgnoreCase(Constants.Yes)) {

				} else {

					FacesMessage msg = new FacesMessage("Residence", returnString);
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(msg);

				}

			}else{
				validateTelephonePermanent(context, component, value);
			}
		}

	}


	public void validateOfficeTelephone(FacesContext context, UIComponent component, Object value) throws ValidatorException {


		String returnString = generalService.validateMobileTelephone(sessionStateManage.getCountryAlphaTwoCode(), value.toString(), Constants.RESIDENCE_CONTACT);
		if (returnString.equalsIgnoreCase(Constants.Yes)) {

		} else {

			FacesMessage msg = new FacesMessage("Residence", returnString);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

		}

	}

	public String activeInd= null;



	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}


	@SuppressWarnings("static-access")
	public void viewScan(CreateProofTable createProofTable){

		ReadScanProperties readScan = new ReadScanProperties();

		StringBuffer urlBuffer = new StringBuffer();

		String equals = "=";
		String ampersand = "&";
		String rootContextview = readScan.getPropertiesValue().get("urlviewbrowser"); 
		String passportArccode = readScan.getPropertiesValue().get("Passport_ArcMateField_FileCategoryID");
		String gccnationalidArccode = readScan.getPropertiesValue().get("GCCNationalID_ArcMateField_FileCategoryID");
		String oldCiviidArccode = readScan.getPropertiesValue().get("Old_CivilId_ArcMateField_FileCategoryID");
		String smartCardArccode = readScan.getPropertiesValue().get("SmartCard_ArcMateField_FileCategoryID");

		String newIdChipArccode = readScan.getPropertiesValue().get("New_ID_Chip_ArcMateField_FileCategoryID");
		String drivingLicenecArccode = readScan.getPropertiesValue().get("Driving_Licence_ArcMateField_FileCategoryID");
		String bedounArccode = readScan.getPropertiesValue().get("Bedoun_ArcMateField_FileCategoryID");

		String civilid20 = readScan.getPropertiesValue().get("civilid20");

		String fileCatgoryId = readScan.getPropertiesValue().get("FileCategoryID");
		String passport = readScan.getPropertiesValue().get("passport");
		String gccnationalid = readScan.getPropertiesValue().get("gccnationalid");
		String bedoun = readScan.getPropertiesValue().get("bedoun");
		String smartCard = readScan.getPropertiesValue().get("smartCard");
		String civilid = readScan.getPropertiesValue().get("civilid");
		String drivingLicence = readScan.getPropertiesValue().get("drivinglicense");
		String newIdwitoutChip = readScan.getPropertiesValue().get("newidwitoutchip");

		String usernamevalue = readScan.getPropertiesValue().get("usernamevalue");
		String passwordvalue = readScan.getPropertiesValue().get("passwordvalue");
		String username = readScan.getPropertiesValue().get("username");
		String password = readScan.getPropertiesValue().get("password");

		/***
		 * 
		 * CR 16062015 Due to add new CIVIL ID NEW for OCR by Nazish
		 * 
		 */
		String newcivilid = readScan.getPropertiesValue().get("newcivilid");
		String new_CivilId_ArcMateField_FileCategoryID = readScan.getPropertiesValue().get("new_CivilId_ArcMateField_FileCategoryID");

		/***
		 * 
		 * CR 16062015 Due to add new CIVIL ID NEW for OCR by Nazish
		 * 
		 */
		String strIdType= createProofTable.getIdType();

		String arcmateCode =null;

		/*	if (getSelectType().equalsIgnoreCase(getGeneralService().getComponentId(Constants.METHODTYPE,	sessionStateManage.getLanguageId())
										.getFsBizComponentData().getComponentDataId()
										.toPlainString())) {*/

		if(strIdType.equalsIgnoreCase(civilid))
		{
			arcmateCode = oldCiviidArccode;
		}else if(strIdType.equalsIgnoreCase(drivingLicence))
		{
			arcmateCode = drivingLicenecArccode;
		}else if(strIdType.equalsIgnoreCase(bedoun))
		{
			arcmateCode = bedounArccode;
		}else if(strIdType.equalsIgnoreCase(passport))
		{
			arcmateCode = passportArccode;
		}else if(strIdType.equalsIgnoreCase(gccnationalid)){
			arcmateCode = gccnationalidArccode;
		}							
		else if(strIdType.equalsIgnoreCase(smartCard))
		{
			arcmateCode = smartCardArccode;
		} 
		else if(strIdType.equalsIgnoreCase(newcivilid)){
			arcmateCode = new_CivilId_ArcMateField_FileCategoryID;
		}

		urlBuffer.append(rootContextview).append(ampersand).append(username).append(equals).append(usernamevalue)
		.append(ampersand).append(password).append(equals).append(passwordvalue).append(ampersand).append(fileCatgoryId).append(equals).append(arcmateCode)
		.append(ampersand).append(civilid20).append(equals).append(createProofTable.getId_number());

		System.out.println("SCANNED VIEW URL :  " + urlBuffer.toString());
		log.info("SCANNED VIEW URL :  " + urlBuffer.toString());

		try {

			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

			context.redirect(urlBuffer.toString());

		} catch (Exception e) {

			log.info("Problem to redirect: " + e);

		}


	}


	Map<String, BizComponentConfDetail> returnObject = null;
	Map<String, Map<String, Object>> jsonOutput = new HashMap<String,Map<String, Object>>();
	Map<String, String> mapValidator = new HashMap<String,String>();
	Map<String, Object> map = new TreeMap<String, Object>();

	Map<String, Object> getComponentBehavior(){

		try{

			List<String> componentNames = Arrays.asList("Telephone Number");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(),sessionStateManage.getApplicationId(),sessionStateManage.getCompanyId(),getCountryId(),sessionStateManage.getPageId());
			for(Entry<String, BizComponentConfDetail> entry:returnObject.entrySet()){

				mapValidator = new HashMap<String,String>();
				mapValidator.put("MIN_VALUE", entry.getValue().getMinValue().toPlainString());
				mapValidator.put("MAX_VALUE", entry.getValue().getMaxValue().toPlainString());

			}
		}catch(Exception e){

			map.put("error", "1");
		}
		map.put("data", jsonOutput);
		map.put("total", jsonOutput.size());
		return map;
	}

	public void validateTelephonePermanent(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if(value.toString().length()!=0){
		if (value.toString().length()  == new BigDecimal(mapValidator.get("MAX_VALUE")).intValue()) {

		} else {

			FacesMessage msg = new FacesMessage("Permanet", "Telephone Number must be "+mapValidator.get("MAX_VALUE")+" Character");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

		}
		}

	}
	
	public Boolean customersave;
	public Boolean smartcardcustomersave;
	public Boolean smartcardDMSsave;
	public Boolean customerIdProofsave;
	public Boolean customerContactDetailsave;
	public Boolean customerEmployeeDetailsave;
	public Boolean customerSignatureDetailsave;
	
	public Boolean getCustomerSignatureDetailsave() {
		return customerSignatureDetailsave;
	}

	public void setCustomerSignatureDetailsave(Boolean customerSignatureDetailsave) {
		this.customerSignatureDetailsave = customerSignatureDetailsave;
	}

	public Boolean getCustomersave() {
		return customersave;
	}

	public void setCustomersave(Boolean customersave) {
		this.customersave = customersave;
	}

	public Boolean getCustomerIdProofsave() {
		return customerIdProofsave;
	}

	public void setCustomerIdProofsave(Boolean customerIdProofsave) {
		this.customerIdProofsave = customerIdProofsave;
	}

	public Boolean getCustomerContactDetailsave() {
		return customerContactDetailsave;
	}

	public void setCustomerContactDetailsave(Boolean customerContactDetailsave) {
		this.customerContactDetailsave = customerContactDetailsave;
	}

	public Boolean getCustomerEmployeeDetailsave() {
		return customerEmployeeDetailsave;
	}

	public void setCustomerEmployeeDetailsave(Boolean customerEmployeeDetailsave) {
		this.customerEmployeeDetailsave = customerEmployeeDetailsave;
	}
	
	public Boolean getSmartcardcustomersave() {
		return smartcardcustomersave;
	}

	public void setSmartcardcustomersave(Boolean smartcardcustomersave) {
		this.smartcardcustomersave = smartcardcustomersave;
	}
	
	public Boolean getSmartcardDMSsave() {
		return smartcardDMSsave;
	}

	public void setSmartcardDMSsave(Boolean smartcardDMSsave) {
		this.smartcardDMSsave = smartcardDMSsave;
	}

	public void savesmartcardCustomerPage(){
		try {
			if(nextContactDetailsFromSmartCardInfo()){

				setSmartcardcustomersave(true);
				setCustomerIdProofsave(true);
				setSmartcardDMSsave(true);
				setCustomerContactDetailsave(false);
				setCustomerEmployeeDetailsave(false);
				setCustomerSignatureDetailsave(false);
				saveSmartCardCustomerInformation();

			}else{
				setSmartcardcustomersave(false);
				setCustomerIdProofsave(false);
				setSmartcardDMSsave(false);
				setCustomerContactDetailsave(false);
				setCustomerEmployeeDetailsave(false);
				setCustomerSignatureDetailsave(false);
				//no save done
				System.out.println("ERROR : No Save Happen");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void savingCustomerPageToPage() {
		try {
			if(nextContactDetails()){

				setCustomersave(true);
				setCustomerIdProofsave(true);
				setCustomerContactDetailsave(false);
				setCustomerEmployeeDetailsave(false);
				setCustomerSignatureDetailsave(false);
				saveManualCustomerInformation();

			}else{
				setCustomersave(false);
				setCustomerIdProofsave(false);
				setCustomerContactDetailsave(false);
				setCustomerEmployeeDetailsave(false);
				setCustomerSignatureDetailsave(false);
				//no save done
				System.out.println("ERROR : No Save Happen");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void saveContactDetailsPage(){
		
		try{
			if(nextEmploymentDetails()){
				setCustomersave(false);
				setSmartcardcustomersave(false);
				setSmartcardDMSsave(false);
				setCustomerIdProofsave(false);
				setCustomerContactDetailsave(true);
				setCustomerEmployeeDetailsave(false);
				setCustomerSignatureDetailsave(false);
				if(getSmartcardcheck()){
					saveSmartCardCustomerInformation();
				}else{
					saveManualCustomerInformation();
				}
				
			}else{
				setCustomersave(false);
				setCustomerIdProofsave(false);
				setCustomerContactDetailsave(false);
				setCustomerEmployeeDetailsave(false);
				setCustomerSignatureDetailsave(false);
				setSmartcardcustomersave(false);
				setSmartcardDMSsave(false);
				//no save done
				System.out.println("ERROR : No Save Happen");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	public void saveEmployeeDetailsPage(){
		try{

			if(nextSignature()){
				setCustomerIdProofsave(false);
				setCustomerContactDetailsave(false);
				setCustomerEmployeeDetailsave(true);
				setCustomerSignatureDetailsave(false);
				if(getSmartcardcheck()){
					setSmartcardcustomersave(true);
					saveSmartCardCustomerInformation();
				}else{
					setCustomersave(true);
					saveManualCustomerInformation();
				}
				setCustomerIdProof();
			}else{
				setCustomersave(false);
				setCustomerIdProofsave(false);
				setCustomerContactDetailsave(false);
				setCustomerEmployeeDetailsave(false);
				setCustomerSignatureDetailsave(false);
				setSmartcardcustomersave(false);
				setSmartcardDMSsave(false);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void saveCustomerIdProofPage(){
		try {
			if(nextProofDetails()){
				setCustomersave(false);
				setSmartcardcustomersave(false);
				setSmartcardDMSsave(false);
				setCustomerIdProofsave(true);
				setCustomerContactDetailsave(false);
				setCustomerEmployeeDetailsave(false);
				setCustomerSignatureDetailsave(false);
				if(getSmartcardcheck()){
					saveSmartCardCustomerInformation();
				}else{
					saveManualCustomerInformation();
				}
			}else{
				setCustomersave(false);
				setCustomerIdProofsave(false);
				setCustomerContactDetailsave(false);
				setCustomerEmployeeDetailsave(false);
				setCustomerSignatureDetailsave(false);
				setSmartcardcustomersave(false);
				setSmartcardDMSsave(false);
				//no save done
				System.out.println("ERROR : No Save Happen");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveCustomerSignaturePage(){
		try {
			if(mainSave()){
				
				setCustomerIdProofsave(false);
				setCustomerContactDetailsave(false);
				setCustomerEmployeeDetailsave(false);
				setSmartcardDMSsave(false);
				setCustomerSignatureDetailsave(true);
				if(getSmartcardcheck()){
					setSmartcardcustomersave(true);
					saveSmartCardCustomerInformation();
				}else{
					setCustomersave(true);
					saveManualCustomerInformation();
				}
				setBooRenderFinalSave(false);
				setBooVerifyAllRen(false);
				setSignaturePanelRender(false);
			}else{
				setCustomersave(false);
				setCustomerIdProofsave(false);
				setCustomerContactDetailsave(false);
				setCustomerEmployeeDetailsave(false);
				setCustomerSignatureDetailsave(false);
				setSmartcardcustomersave(false);
				setSmartcardDMSsave(false);
				//no save done
				System.out.println("ERROR : No Save Happen");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public String saveManualCustomerInformation(){

		try {

			Customer customerinfo = new Customer();

			customerinfo.setCustomerId(getPkCustomerId());

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionStateManage.getCountryId());
			customerinfo.setFsCountryMasterByCountryId(countryMaster);

			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(sessionStateManage.getCompanyId());
			customerinfo.setFsCompanyMaster(companyMaster);

			BizComponentData customerType = new BizComponentData();
			customerType.setComponentDataId(getGeneralService().getComponentId(Constants.CUSTOMERTYPE_INDU, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
			customerinfo.setFsBizComponentDataByCustomerTypeId(customerType);

			BizComponentData companyGroup = new BizComponentData();
			companyGroup.setComponentDataId(getGeneralService().getComponentId(Constants.GROUPID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());	
			customerinfo.setFsBizComponentDataByGroupId(companyGroup);

			LanguageType languageType = new LanguageType();
			languageType.setLanguageId(sessionStateManage.getLanguageId());
			customerinfo.setFsLanguageType(languageType);

			// step by step save
			customerinfo.setTitle(getTitle());
			customerinfo.setFirstName(getFirstName());
			customerinfo.setMiddleName(getMiddleName());
			customerinfo.setLastName(getLastName());
			customerinfo.setShortName(getShortName());
			customerinfo.setTitleLocal(getTitlel());
			customerinfo.setFirstNameLocal(getFirstNamel());
			customerinfo.setMiddleNameLocal(getMiddleNamel());
			customerinfo.setLastNameLocal(getLastNamel());
			customerinfo.setShortNameLocal(getShortNamel());

			CountryMaster nationality = new CountryMaster();
			nationality.setCountryId(getNationality());
			customerinfo.setFsCountryMasterByNationality(nationality);

			customerinfo.setGender(getGender());
			customerinfo.setMobile(getMobile());
			customerinfo.setEmail(getEmail());
			customerinfo.setAlterEmailId(getAlternativeEmail());
			customerinfo.setBranchCode(new BigDecimal(sessionStateManage.getBranchId()));

			mapIdentityType = ruleEngine.getComponentData("Identity Type");

			BigDecimal identityTpeId = null;
			for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
				if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
					identityTpeId = entry.getKey();
					break;
				}
			}

			if (getBooChangeDob()) {
				customerinfo.setDateOfBirth(getDob());
			} else if(getIdType()!=null && getIdType().equalsIgnoreCase(identityTpeId.toString())) {
				if(getShowDob()!=null){
					customerinfo.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(getShowDob()));
				} else{
					customerinfo.setDateOfBirth(getDob());
				}
			} else {
				customerinfo.setDateOfBirth(getDob());
			}

			// These Records will fetch while Income Range Available

			if(getIncomeRange() != null){

				IncomeRangeMaster incomeRange = new IncomeRangeMaster();
				incomeRange.setIncomeRangeId(getIncomeRange());
				customerinfo.setFsIncomeRangeMaster(incomeRange);

				ArticleDetails articleDetail = new ArticleDetails();
				articleDetail.setArticleDetailId(getLevel());
				customerinfo.setFsArticleDetails(articleDetail);

				/*customerinfo.setDailyLimit(getDailyLimit());
				customerinfo.setWeeklyLimit(getWeeklyLimit());
				customerinfo.setMontlyLimit(getMonthlyLimit());
				customerinfo.setQuaterlyLimit(getQuarterlyLimit());
				customerinfo.setHalfYearly(getHalfyearly());
				customerinfo.setAnnualLimit(getAnnualLimit());*/

			}
			
			//verification token check
			/*if (getVerificationToken() != null && !getVerificationToken().equalsIgnoreCase("")) {
				customerinfo.setVerificationTokenId(getVerificationToken());
			}*/

			if(getCustomerSignatureDetailsave()){
				customerinfo.setIsActive(Constants.CUST_ACTIVE_INDICATOR); 
				customerinfo.setActivatedInd(Constants.CUST_ACTIVE_INDICATOR); 
				//customerinfo.setActivatedDate(new Date());
				customerinfo.setActivatedDate(getCurrentTime());
			}else{
				if(getDigitalSignature() != null){
					customerinfo.setIsActive(Constants.CUST_ACTIVE_INDICATOR);  
					customerinfo.setActivatedInd(Constants.CUST_ACTIVE_INDICATOR);
					customerinfo.setActivatedDate(getActiveDate());
				}else{
					customerinfo.setIsActive(Constants.CUST_ACTIVE_INDICATOR);  
					//customerinfo.setActivatedInd(Constants.CUST_INACTIVE_INDICATOR);  
				}
			}


			customerinfo.setSmartCardIndicator(Constants.CUST_INACTIVE_INDICATOR);
			customerinfo.setPepsIndicator(getPepsindicator());
			customerinfo.setMedicalInsuranceInd(getMedicalInsuranceInd());
			customerinfo.setEmosCustomer(getEmosCustomer());

			if (getCustRefId() != null) {
				customerinfo.setIntroducedBy(getCustRefId().toString());
				customerinfo.setIntroducedDate(new Date());
			}


			if (getPkCustomerId() != null) {

				customerinfo.setLastUpdated(getCurrentTime());
				customerinfo.setCreatedBy(getCreatedByCustomer());
				customerinfo.setCreationDate(getCreationDateCustomer());
				 List<Customer> referenceList = icustomerRegistrationService.getVerificationToken(getPkCustomerId());
				    if(referenceList.size()>0){
				    	setUpdateCustomerRefNo(referenceList.get(0).getCustomerReference());
				    } 
				customerinfo.setCustomerReference(getUpdateCustomerRefNo());
				if(getActiveInd()!=null && getActiveInd().equalsIgnoreCase(Constants.CUST_ACTIVE_INDICATOR)){
					customerinfo.setUpdatedBy(session.getUserName());
				}

			} else {
				BigDecimal custRef = getIcustomerRegistrationService().callProcedureCustReferenceNumber(getCompanyCodeByCompanyId() , Constants.DOCUMENT_CODE_FOR_CUSTOMER , getDealYearbyDate() , session.getBranchId());

				if(custRef != null){
					customerinfo.setCustomerReference(custRef);
				}

				customerinfo.setCreatedBy(session.getUserName());
				customerinfo.setCreationDate(getCurrentTime());
			}

			if(getDigitalSignature() != null ){
				customerinfo.setSignatureSpecimen(getDigitalSignature());
			}

			//@@@ AML Check
			String amlReturnStatus = null;
			String amlStatus = null;
			String amlhits = null;

			amlReturnStatus = getAMLCheckStatus(customerinfo);

			//amlReturnStatus ="PASS-0";

			if (amlReturnStatus == null) {
				customerinfo.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);
				customerinfo.setNumberOfHits(new BigDecimal(0));
			}else{
				String[] parts = amlReturnStatus.split("-");
				amlStatus = parts[0];
				amlhits = parts[1];
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_ERROR)){
					customerinfo.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);
					customerinfo.setNumberOfHits(new BigDecimal(amlhits));
				}
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PENDING)){
					customerinfo.setAmlStatus(Constants.AML_STATUS_BCO);
					customerinfo.setNumberOfHits(new BigDecimal(amlhits));
				}
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PASS)){
					customerinfo.setAmlStatus(Constants.AML_STATUS_PASS);
					customerinfo.setNumberOfHits(new BigDecimal(0));
				}

			}

			HashMap<String,Object> mapAllDetailForSave = new HashMap<String,Object>();

			if(getCustomersave()){
				mapAllDetailForSave.put("customerinfo", customerinfo);
				setPkCustomerId(customerinfo.getCustomerId());
				setCreatedByCustomer(customerinfo.getCreatedBy());
			    setCreationDateCustomer(customerinfo.getCreationDate());
			    
			    //setUpdateCustomerRefNo(customerinfo.getCustomerReference());
			   /* List<Customer> referenceList = icustomerRegistrationService.getVerificationToken(getPkCustomerId());
			    if(referenceList.size()>0){
			    	setUpdateCustomerRefNo(referenceList.get(0).getCustomerReference());
			    } */
			}

			if(getCustomerContactDetailsave()){
				List<ContactDetail> lstCustContactDetails =  saveCustomerContactDetails();
				mapAllDetailForSave.put("customerContactDetails", lstCustContactDetails);
			}

			if(getCustomerEmployeeDetailsave()){
				if(getBooEmploymentPanel()){
					CustomerEmploymentInfo custEmployeeInfo = saveCustomerEmployeeInformation();
					mapAllDetailForSave.put("customerEmployeeInfo", custEmployeeInfo);
					mapAllDetailForSave.put("customerUnEmployee", new Boolean(true));
				}else{
					mapAllDetailForSave.put("customerUnEmployee", new Boolean(false));
				}
				
				//Added for save Dependent
				
				if(getRenderShowDependent()){
					CustomerSponsor customerDepandant = saveCustomerDependant();
					mapAllDetailForSave.put("customerDepandantDetails", customerDepandant);
				}else if(getCustomerDependentId()!=null){
					icustomerRegistrationService.deleteDependant(getCustomerDependentId());
				}

			}
			
			
			if(getCustomerIdProofsave()){
				if (createProofList.size() == 0) {

					setIdListcheck(true);

					List<CustomerIdProof> lstCustIdProof = saveCustomerProofInformation();

					mapAllDetailForSave.put("custIdProofexists", new Boolean(true));
					mapAllDetailForSave.put("customerIdProof", lstCustIdProof);

					getIcustomerRegistrationService().saveCustomerRegistration(mapAllDetailForSave);

					setPkCustomerId(customerinfo.getCustomerId());
					//setCreatedByCustomer(customerinfo.getCreatedBy());
				    //setCreationDateCustomer(customerinfo.getCreationDate());
				    // setUpdateCustomerRefNo(customerinfo.getCustomerReference());
					customerIdToNominee = customerinfo.getCustomerId();

					return "";
				} else {

					setIdListcheck(false);

					List<CustomerIdProof> lstCustIdProof = saveCustomerProofInformation();

					mapAllDetailForSave.put("custIdProofexists",new Boolean(true));
					mapAllDetailForSave.put("customerIdProof", lstCustIdProof);

					getIcustomerRegistrationService().saveCustomerRegistration(mapAllDetailForSave);

					setPkCustomerId(customerinfo.getCustomerId());
					//setCreatedByCustomer(customerinfo.getCreatedBy());
				    //setCreationDateCustomer(customerinfo.getCreationDate());
				    //setUpdateCustomerRefNo(customerinfo.getCustomerReference());
					customerIdToNominee = customerinfo.getCustomerId();

					return "";
				}
			}

			if(getCustomerSignatureDetailsave()){

				setIdListcheck(false);

				getIcustomerRegistrationService().saveCustomerRegistration(mapAllDetailForSave);

				setPkCustomerId(customerinfo.getCustomerId());
				//setCreatedByCustomer(customerinfo.getCreatedBy());
			    //setCreationDateCustomer(customerinfo.getCreationDate());
			    //setUpdateCustomerRefNo(customerinfo.getCustomerReference());
				customerIdToNominee = customerinfo.getCustomerId();

				String verificationToken = null;
				String updatedBy = null;
				BigDecimal customerReference = null;

				List<Customer> custlist = getIcustomerRegistrationService().getVerificationToken(getPkCustomerId());
				if (custlist.size() > 0) {
					verificationToken = custlist.get(0).getVerificationTokenId();
					updatedBy = custlist.get(0).getUpdatedBy();
					emosCustomer = custlist.get(0).getEmosCustomer();
					if(custlist.get(0).getCustomerReference()!=null){
						customerReference = custlist.get(0).getCustomerReference();
						setUpdateCustomerRefNo(customerReference);
					}
				}

/*				if (emosCustomer !=null && !emosCustomer.equalsIgnoreCase("1")) {

					String toMailid = custlist.get(0).getEmail();
					mailService1.sendMail(toMailid, "Customer Info Log Difference", verificationToken);
					setSignaturePanelRender(false);
					setRenderUpdate(true);
					setRenderRegistration(false);
					setBooProof(false);
					setRenderSignature(false);
					setRenderVerifyReport(false);
					setBooRenderFinalSave(false);
					setExitRender(false);
					setSuccessPanel(false);
					setUpdatePanel(false);
					setBooVerifyAllRen(false);
					setNextSignaturePanel(false);
					setNextUpdateRender(false);

					//customerinfo.setEmosCustomer(Constants.Yes);
					getAMLCheckStatus_afterSave(getPkCustomerId());
					//customerinfo.setCustomerId(getPkCustomerId());
					getIcustomerRegistrationService().saveCustomerEmos(getPkCustomerId(), Constants.Yes);
				}

				else if (updatedBy != null && emosCustomer == null)  {

					String toMailid = custlist.get(0).getEmail();
					mailService1.sendMail(toMailid, "Customer Info Log Difference", verificationToken);
					setSignaturePanelRender(false);
					setRenderUpdate(true);
					setRenderRegistration(false);
					setBooProof(false);
					setRenderSignature(false);
					setRenderVerifyReport(false);
					setBooRenderFinalSave(false);
					setExitRender(false);
					setSuccessPanel(false);
					setUpdatePanel(false);
					setBooVerifyAllRen(false);
					setNextSignaturePanel(false);
					setNextUpdateRender(false);

					//customerinfo.setEmosCustomer(Constants.Yes);
					getAMLCheckStatus_afterSave(getPkCustomerId());
					//customerinfo.setCustomerId(getPkCustomerId());
					getIcustomerRegistrationService().saveCustomerEmos(getPkCustomerId(), Constants.Yes);


				} else {*/

					setExitRender(false);
					setBooRenderFinalSave(false);
					setSignaturePanelRender(false);
					setSignatureCaptureRender(false);
					setSignatureSpecimenRender(false);
					setBooVerifyAllRen(false);
					setRenderUpdate(false);
					setRenderRegistration(false);
					setBooProof(false);
					setRenderSignature(false);
					setRenderVerifyReport(false);
					setBooRemitterInfo(false);
					setBooIdDetail(false);
					setBooCustomerDetails(false);
					setBooContactDetails(false);
					setBooEmploymentDetails(false);
					setRenderSavebutton(false);
					setRenderverication(false);
					setSuccessPanel(true);
					setUpdatePanel(false);
					setRenderFinal(false);
					setNextSignaturePanel(false);
					setNextUpdateRender(false);

					getAMLCheckStatus_afterSave(getPkCustomerId());
					if(emosCustomer!=null && emosCustomer.equalsIgnoreCase("1")){
						getIcustomerRegistrationService().saveCustomerEmos(getPkCustomerId(), Constants.Yes);
					}
			//	}

				List<String> outPutList = icustomerRegistrationService.callProcedureUpdate(getPkCustomerId());

				if(outPutList.size()>0){
					if(outPutList != null && outPutList.get(0).equalsIgnoreCase(Constants.Yes)){
						RequestContext.getCurrentInstance().execute("migrationexception.show();");
					}
				}
			}else{
				setIdListcheck(false);

				getIcustomerRegistrationService().saveCustomerRegistration(mapAllDetailForSave);

				setPkCustomerId(customerinfo.getCustomerId());
				//setCreatedByCustomer(customerinfo.getCreatedBy());
			    //setCreationDateCustomer(customerinfo.getCreationDate());
			    //setUpdateCustomerRefNo(customerinfo.getCustomerReference());
				customerIdToNominee = customerinfo.getCustomerId();

				return "";
			}

		} catch (AMGException e) {
			log.error("Exception Occured While migration Data", e);
			RequestContext.getCurrentInstance().execute("migrationexception.show();");
		} catch (Exception e) {
			log.error("Exception Occured While saving Data", e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}

		return "";	

	}
	
	public void saveSmartCardCustomerInformation(){

		try{
			
			Customer customer = new Customer();
		
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionStateManage.getCountryId());
			customer.setFsCountryMasterByCountryId(countryMaster);

			/* Nationality Id save */
			CountryMaster nationality = new CountryMaster();
			nationality.setCountryId(getNationality());
			customer.setFsCountryMasterByNationality(nationality);

			/* save company */
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(sessionStateManage.getCompanyId());
			customer.setFsCompanyMaster(companyMaster);

			/** Customer Type */
			BizComponentData customerType = new BizComponentData();
			customerType.setComponentDataId(getGeneralService().getComponentId(Constants.CUSTOMERTYPE_INDU,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
			customer.setFsBizComponentDataByCustomerTypeId(customerType);

			/** save Group ID */
			BizComponentData companyGroup = new BizComponentData();
			companyGroup.setComponentDataId(getGeneralService().getComponentId(Constants.GROUPID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
			customer.setFsBizComponentDataByGroupId(companyGroup);

			LanguageType languageType = new LanguageType();
			languageType.setLanguageId(sessionStateManage.getLanguageId());
			customer.setFsLanguageType(languageType);
		
			customer.setTitle(getTitle());
			customer.setGender(getGender());
			customer.setTitleLocal(getTitlel());

			String fullName = getFullNameEn();
			String names[] = fullName.split(" ");

			if(names.length==4){
				String firstName = names[0];
				String middleName = names[1]+" "+names[2];
				String lastName = names[3];
				customer.setFirstName(firstName);
				customer.setMiddleName(middleName);
				customer.setLastName(lastName);
			}else if(names.length==5){
				String firstName = names[0];
				String middleName = names[1]+" "+names[2]+" "+names[3];
				String lastName = names[4];
				customer.setFirstName(firstName);
				customer.setMiddleName(middleName);
				customer.setLastName(lastName);

			}else if(names.length==3){
				String firstName = names[0];
				String middleName = names[1];
				String lastName = names[2];
				customer.setFirstName(firstName);
				customer.setMiddleName(middleName);
				customer.setLastName(lastName);

			}else if(names.length==2){
				String firstName = names[0];
				//String middleName = names[1];
				String lastName = names[1];
				customer.setFirstName(firstName);
				//customer.setMiddleName(middleName);
				customer.setLastName(lastName);

			}else if(names.length==1){
				String firstName = names[0];
				customer.setFirstName(firstName);
			}else{

				String firstName = names[0];
				String middleName = names[1];
				String lastName = names[2];
				customer.setFirstName(firstName);
				customer.setMiddleName(middleName);
				customer.setLastName(lastName);

			}

			
			if(getShortName()!=null  && getShortName().equalsIgnoreCase("")){
				customer.setShortName(getShortName());
			}

			customer.setFirstNameLocal(getFirstNameAr());
			customer.setLastNameLocal(getLastNameAr());

			
			if(getShortNamel()!=null){
				customer.setShortNameLocal(getShortNamel());
			}
			
			if(getIncomeRange() != null){
				
				// save to Income
				IncomeRangeMaster incomeRange = new IncomeRangeMaster();
				incomeRange.setIncomeRangeId(getIncomeRange());
				customer.setFsIncomeRangeMaster(incomeRange);
				
				ArticleDetails articleDetail = new ArticleDetails();
				articleDetail.setArticleDetailId(getLevel());
				customer.setFsArticleDetails(articleDetail);
				
				/*customer.setDailyLimit(getDailyLimit());
				customer.setWeeklyLimit(getWeeklyLimit());
				customer.setMontlyLimit(getMonthlyLimit());
				customer.setQuaterlyLimit(getQuarterlyLimit());
				customer.setHalfYearly(getHalfyearly());
				customer.setAnnualLimit(getAnnualLimit());*/
			}

			//verification token check
			/*if (getVerificationToken() != null && !getVerificationToken().equalsIgnoreCase("")) {
				customer.setVerificationTokenId(getVerificationToken());
			}*/
			
			customer.setSmartCardIndicator(Constants.CUST_ACTIVE_INDICATOR);
			//customer.setIsActive(Constants.Yes);
			
			if(getCustomerSignatureDetailsave()){
				customer.setIsActive(Constants.CUST_ACTIVE_INDICATOR); 
				customer.setActivatedInd(Constants.CUST_ACTIVE_INDICATOR); 
				//customer.setActivatedDate(new Date());
				customer.setActivatedDate(getCurrentTime());
			}else{
				if(getDigitalSignature() != null){
					customer.setIsActive(Constants.CUST_ACTIVE_INDICATOR);  
					customer.setActivatedInd(Constants.CUST_ACTIVE_INDICATOR);
					customer.setActivatedDate(getActiveDate());
				}else{
					customer.setIsActive(Constants.CUST_ACTIVE_INDICATOR);  
					//customerinfo.setActivatedInd(Constants.CUST_INACTIVE_INDICATOR);  
				}
			}
			
			customer.setBranchCode( new BigDecimal(sessionStateManage.getBranchId()));

			// Added by kani begin smart cardhh
			if (getCustRefId() != null) {
				customer.setIntroducedBy(getCustRefId().toString());
				customer.setIntroducedDate(new Date());
			}
			customer.setMedicalInsuranceInd(getMedicalInsuranceInd());
			customer.setPepsIndicator(getPepsindicator());

			mapIdentityType = ruleEngine.getComponentData("Identity Type");
			BigDecimal identityTpeId = null;
			for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
				if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
					identityTpeId = entry.getKey();
					break;
				}
			}

			customer.setMobile(getTelephone1());
			customer.setEmail(getScardemail());
			try {
				customer.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(getBirthdate()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//customer.setActivatedInd(Constants.CUST_ACTIVE_INDICATOR);
			customer.setEmosCustomer(getEmosCustomer());

			/* Managing save or update */
			if (getPkCustomerId() != null) {
				
				customer.setCustomerId(getPkCustomerId());
				customer.setLastUpdated(getCurrentTime());
				customer.setCreatedBy(getCreatedByCustomer());
				customer.setCreationDate(getCreationDateCustomer());
				List<Customer> referenceList = icustomerRegistrationService.getVerificationToken(getPkCustomerId());
			    if(referenceList.size()>0){
			    	setUpdateCustomerRefNo(referenceList.get(0).getCustomerReference());
			    } 
				customer.setCustomerReference(getUpdateCustomerRefNo());
				if(getDigitalSignature()!=null){
					customer.setSignatureSpecimen(getDigitalSignature());
				}
				
				//customer.setActivatedInd("1");

				// checkLimit();
				if(getActiveInd()!=null && getActiveInd().equalsIgnoreCase(Constants.CUST_ACTIVE_INDICATOR)){
					customer.setUpdatedBy(session.getUserName());
				}

			} else {
				customer.setCreatedBy(sessionStateManage.getUserName());
				customer.setCreationDate(getCurrentTime());
				//customer.setCustomerReference(getCustomerRefNo()); CR -Trigger will responsible to generate Customer Reference number.
				if(getDigitalSignature()!=null){
					customer.setSignatureSpecimen(getDigitalSignature());
				}/*else{
					RequestContext.getCurrentInstance().execute("signatureMandatory.show();");
				}*/
				BigDecimal custRef = getIcustomerRegistrationService().callProcedureCustReferenceNumber(getCompanyCodeByCompanyId() , Constants.DOCUMENT_CODE_FOR_CUSTOMER , getDealYearbyDate() , session.getBranchId());

				if(custRef != null){
					customer.setCustomerReference(custRef);
				}

			}


			
			customer.setVerificationBy(sessionStateManage.getUserName());
			customer.setVerificationDate(getCurrentTime());


			//@@@ AML	
			String amlReturnStatus = null;
			String amlStatus = null;
			String amlhits = null;

			amlReturnStatus = getAMLCheckStatus(customer);

			if (amlReturnStatus == null) {
				customer.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);
				customer.setNumberOfHits(new BigDecimal(0));
			}else{
				String[] parts = amlReturnStatus.split("-");
				amlStatus = parts[0];
				amlhits = parts[1];

				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_ERROR)){
					customer.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);
					customer.setNumberOfHits(new BigDecimal(amlhits));
				}
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PENDING)){
					customer.setAmlStatus(Constants.AML_STATUS_BCO);
					customer.setNumberOfHits(new BigDecimal(amlhits));
				}
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PASS)){
					customer.setAmlStatus(Constants.AML_STATUS_PASS);
					customer.setNumberOfHits(new BigDecimal(0));
				}
			}


			HashMap<String,Object> mapAllDetailForSave = new HashMap<String,Object>();
			
			if(getSmartcardcustomersave()){
				mapAllDetailForSave.put("customerinfo", customer);
				setPkCustomerId(customer.getCustomerId());
				setCreatedByCustomer(customer.getCreatedBy());
			    setCreationDateCustomer(customer.getCreationDate());
			    //setUpdateCustomerRefNo(customer.getCustomerReference());
			    /*List<Customer> referenceList = icustomerRegistrationService.getVerificationToken(getPkCustomerId());
			    if(referenceList.size()>0){
			    	setUpdateCustomerRefNo(referenceList.get(0).getCustomerReference());
			    } */
			}
			
			if(getCustomerContactDetailsave()){
				List<ContactDetail> lstCustContactDetails =  saveCustomerContactDetails();
				mapAllDetailForSave.put("customerContactDetails", lstCustContactDetails);
			}
			
			if(getCustomerEmployeeDetailsave()){
				if(getBooEmploymentPanel()){
					CustomerEmploymentInfo custEmployeeInfo = saveCustomerEmployeeInformation();
					mapAllDetailForSave.put("customerEmployeeInfo", custEmployeeInfo);
					mapAllDetailForSave.put("customerUnEmployee", new Boolean(true));
				}else{
					mapAllDetailForSave.put("customerUnEmployee", new Boolean(false));
				}
				
				//Added for save Dependent
				
				if(getRenderShowDependent()){
					CustomerSponsor customerDepandant = saveCustomerDependant();
					mapAllDetailForSave.put("customerDepandantDetails", customerDepandant);
				}else if(getCustomerDependentId()!=null){
					icustomerRegistrationService.deleteDependant(getCustomerDependentId());
				}

			}
			

			if(getSmartcardDMSsave()){
				DmsMas customerBySmartCard = saveSmartCardInformationForDmsMas();
				mapAllDetailForSave.put("customerBySmartCard", customerBySmartCard);
			}
			
			if(getCustomerIdProofsave()){
				if (createProofList.size() == 0) {

					List<CustomerIdProof> lstCustIdProof = saveCustomerProofInformation();

					mapAllDetailForSave.put("custIdProofexists", new Boolean(true));
					mapAllDetailForSave.put("customerIdProof", lstCustIdProof);
					try {
						getIcustomerRegistrationService().saveSmartCardCustomerRegistration(mapAllDetailForSave);
						setPkCustomerId(customer.getCustomerId());
						//setCreatedByCustomer(customer.getCreatedBy());
					    //setCreationDateCustomer(customer.getCreationDate());
					   // setUpdateCustomerRefNo(customer.getCustomerReference());
						
					} catch (Exception e) {
						log.error("Exception Occured While Saving", e);
						RequestContext.getCurrentInstance().execute("saveerror.show();");
					}
				} else {

					List<CustomerIdProof> lstCustIdProof = saveCustomerProofInformation();

					mapAllDetailForSave.put("custIdProofexists",new Boolean(true));
					mapAllDetailForSave.put("customerIdProof", lstCustIdProof);

					try {
						getIcustomerRegistrationService().saveSmartCardCustomerRegistration(mapAllDetailForSave);
						setPkCustomerId(customer.getCustomerId());
						//setCreatedByCustomer(customer.getCreatedBy());
					   // setCreationDateCustomer(customer.getCreationDate());
					   // setUpdateCustomerRefNo(customer.getCustomerReference());
					} catch (Exception e) {
						log.error("Exception Occured While Saving", e);

						setExitRender(false);
						setBooRenderFinalSave(false);
						setSignaturePanelRender(false);
						setSignatureCaptureRender(true);
						setSignatureSpecimenRender(false);
						setBooVerifyAllRen(false);
						setRenderUpdate(false);
						setRenderRegistration(false);
						setBooProof(false);
						setRenderSignature(false);
						setRenderVerifyReport(false);
						setBooRemitterInfo(false);
						setBooIdDetail(false);
						setBooCustomerDetails(false);
						setBooContactDetails(false);
						setBooEmploymentDetails(false);
						setBooProof(false);
						setRenderSavebutton(false);
						setRenderSignature(false);
						setBooVerifyAllRen(false);
						setRenderverication(false);
						setSuccessPanel(false);
						setUpdatePanel(false);
						setRenderUpdate(false);
						setRenderRegistration(false);
						setRenderVerifyReport(false);
						setRenderFinal(false);
						setExitRender(false);
						setBooVerifyAllRen(false);
						setNextSignaturePanel(false);
						setNextUpdateRender(false);
						RequestContext.getCurrentInstance().execute("saveerror.show();");
					}
				}
			}
			
			if(getCustomerSignatureDetailsave()){

				try {
					getIcustomerRegistrationService().saveCustomerRegistration(mapAllDetailForSave);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				setPkCustomerId(customer.getCustomerId());
				//setCreatedByCustomer(customer.getCreatedBy());
			   // setCreationDateCustomer(customer.getCreationDate());
			    //setUpdateCustomerRefNo(customer.getCustomerReference());
				customerIdToNominee = customer.getCustomerId();

				String verificationToken = null;
				String emosCustomer = null;
				String updatedBy = null;
				BigDecimal customerReference = null;

				//List<Customer> custlist = new ArrayList<Customer>();
				List<Customer> custlist = getIcustomerRegistrationService().getVerificationToken(getPkCustomerId());
				if (custlist.size() > 0) {
					verificationToken = custlist.get(0).getVerificationTokenId();
					updatedBy = custlist.get(0).getUpdatedBy();
					emosCustomer = custlist.get(0).getEmosCustomer();
					if(custlist.get(0).getCustomerReference()!=null){
						customerReference = custlist.get(0).getCustomerReference();
						setUpdateCustomerRefNo(customerReference);
					}
				}

				/* if (emosCustomer !=null && !emosCustomer.equalsIgnoreCase("1")) {
				  String toMailid = custlist.get(0).getEmail();
				  mailService1.sendMail(toMailid, "Customer Info Log Difference", verificationToken);
				  setSignaturePanelRender(false);
				  setRenderUpdate(true);
				  setRenderRegistration(false);
				  setBooProof(false);
				  setRenderSignature(false);
				  setRenderVerifyReport(false);
				  setBooRenderFinalSave(false);
				  setExitRender(false);
				  setSuccessPanel(false);
				  setUpdatePanel(false);
				  setBooVerifyAllRen(false);
				  setNextSignaturePanel(false);
				  setNextUpdateRender(false);
				  getAMLCheckStatus_afterSave(getPkCustomerId());
				  customer.setCustomerId(getPkCustomerId());
				  getIcustomerRegistrationService().saveCustomerEmos(getPkCustomerId(), Constants.Yes);
			  }
			  else if (updatedBy != null && emosCustomer == null)  {
				  String toMailid = custlist.get(0).getEmail();
				  mailService1.sendMail(toMailid, "Customer Info Log Difference", verificationToken);
				  setSignaturePanelRender(false);
				  setRenderUpdate(true);
				  setRenderRegistration(false);
				  setBooProof(false);
				  setRenderSignature(false);
				  setRenderVerifyReport(false);
				  setBooRenderFinalSave(false);
				  setExitRender(false);
				  setSuccessPanel(false);
				  setUpdatePanel(false);
				  setBooVerifyAllRen(false);
				  setNextSignaturePanel(false);
				  setNextUpdateRender(false);
				  getAMLCheckStatus_afterSave(getPkCustomerId());
				  customer.setCustomerId(getPkCustomerId());
				  getIcustomerRegistrationService().saveCustomerEmos(getPkCustomerId(), Constants.Yes);

			  } else {*/

				setExitRender(false);
				setBooRenderFinalSave(false);
				setSignaturePanelRender(false);
				setSignatureCaptureRender(false);
				setSignatureSpecimenRender(false);
				setBooVerifyAllRen(false);
				setRenderUpdate(false);
				setRenderRegistration(false);
				setBooProof(false);
				setRenderSignature(false);
				setRenderVerifyReport(false);
				setBooRemitterInfo(false);
				setBooIdDetail(false);
				setBooCustomerDetails(false);
				setBooContactDetails(false);
				setBooEmploymentDetails(false);
				setBooProof(false);
				setRenderSavebutton(false);
				setRenderSignature(false);
				setBooVerifyAllRen(false);
				setRenderverication(false);
				setSuccessPanel(true);
				setUpdatePanel(false);
				setRenderUpdate(false);
				setRenderRegistration(false);
				setRenderVerifyReport(false);
				setRenderFinal(false);
				setExitRender(false);
				setBooVerifyAllRen(false);
				setNextSignaturePanel(false);
				setNextUpdateRender(false);
				getAMLCheckStatus_afterSave(getPkCustomerId());
				if(emosCustomer!=null && emosCustomer.equalsIgnoreCase("1")){
					getIcustomerRegistrationService().saveCustomerEmos(getPkCustomerId(), Constants.Yes);

				}

				List<String> outPutList = icustomerRegistrationService.callProcedureUpdate(getPkCustomerId());

				if(outPutList.size()>0){
					if(outPutList.get(0).equalsIgnoreCase(Constants.Yes)){
						RequestContext.getCurrentInstance().execute("migrationexception.show();");
					}
				}
			}else{

				try {
					getIcustomerRegistrationService().saveCustomerRegistration(mapAllDetailForSave);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				setPkCustomerId(customer.getCustomerId());
				//setCreatedByCustomer(customer.getCreatedBy());
			    //setCreationDateCustomer(customer.getCreationDate());
			    //setUpdateCustomerRefNo(customer.getCustomerReference());
				customerIdToNominee = customer.getCustomerId();

			}


			

		} catch (AMGException e) {

			log.error("Exception Occured While Migration Data", e);
			RequestContext.getCurrentInstance().execute("migrationexception.show();");
		}

	}
	
	/*
	
	CR started by Nazish on 14-JULY-2015
	*/
	private String mobileContact=null;
	private String telephoneCode=null;
	private String plusSign=null;
	private Boolean msgMobileOrTel=false;
	private String mobileNoFetch=null;
	private Boolean msgMobileNoFetch=false;
	private Date idExpiryDateCheck = null;
	private Boolean booIdType=true;
	private Boolean booCheckMobile = false;
	private Boolean booMobileHide = false;
	private Boolean renManualArticle = true;
	private Boolean renCardArticle = false;
	private String articleDesCard=null;
	private Boolean renDependent = false;
	private Boolean renderShowDependent = false;
	private String dependantIdNumber=null;
	private String dependentName=null;
	private BigDecimal dependentRelationship=null;
	private String dependentIdExpireDate =null;
	private String dependentIdNoMsg =null;
	private String dependentIdExpireDateMsg =null;
	private Boolean renderRegisterDependent = false;
	private BigDecimal dependentCustomerId=null;
	private String createdByDependent =null;
	private Date createdDateDependent =null;
	private BigDecimal dependentCustomerReferenceNo=null;
	private BigDecimal customerDependentId=null;


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
	
	

	public String getMobileNoFetch() {
		return mobileNoFetch;
	}

	public void setMobileNoFetch(String mobileNoFetch) {
		this.mobileNoFetch = mobileNoFetch;
	}
	
	

	public Boolean getMsgMobileNoFetch() {
		return msgMobileNoFetch;
	}

	public void setMsgMobileNoFetch(Boolean msgMobileNoFetch) {
		this.msgMobileNoFetch = msgMobileNoFetch;
	}

	
	
	public Date getIdExpiryDateCheck() {
		return idExpiryDateCheck;
	}

	public void setIdExpiryDateCheck(Date idExpiryDateCheck) {
		this.idExpiryDateCheck = idExpiryDateCheck;
	}
	
	

	public Boolean getBooIdType() {
		return booIdType;
	}

	public void setBooIdType(Boolean booIdType) {
		this.booIdType = booIdType;
	}

	public Boolean getBooCheckMobile() {
		return booCheckMobile;
	}

	public void setBooCheckMobile(Boolean booCheckMobile) {
		this.booCheckMobile = booCheckMobile;
	}

	public Boolean getBooMobileHide() {
		return booMobileHide;
	}

	public void setBooMobileHide(Boolean booMobileHide) {
		this.booMobileHide = booMobileHide;
	}
	
	

	public Boolean getRenManualArticle() {
		return renManualArticle;
	}

	public void setRenManualArticle(Boolean renManualArticle) {
		this.renManualArticle = renManualArticle;
	}

	public Boolean getRenCardArticle() {
		return renCardArticle;
	}

	public void setRenCardArticle(Boolean renCardArticle) {
		this.renCardArticle = renCardArticle;
	}

	public String getArticleDesCard() {
		return articleDesCard;
	}

	public void setArticleDesCard(String articleDesCard) {
		this.articleDesCard = articleDesCard;
	}

	
	
	
	public Boolean getRenDependent() {
		return renDependent;
	}

	public void setRenDependent(Boolean renDependent) {
		this.renDependent = renDependent;
	}

	public Boolean getRenderShowDependent() {
		return renderShowDependent;
	}

	public void setRenderShowDependent(Boolean renderShowDependent) {
		this.renderShowDependent = renderShowDependent;
	}

	public String getDependantIdNumber() {
		return dependantIdNumber;
	}

	public void setDependantIdNumber(String dependantIdNumber) {
		this.dependantIdNumber = dependantIdNumber;
	}

	public String getDependentName() {
		return dependentName;
	}

	public void setDependentName(String dependentName) {
		this.dependentName = dependentName;
	}

	public BigDecimal getDependentRelationship() {
		return dependentRelationship;
	}

	public void setDependentRelationship(BigDecimal dependentRelationship) {
		this.dependentRelationship = dependentRelationship;
	}

	public String getDependentIdExpireDate() {
		return dependentIdExpireDate;
	}

	public void setDependentIdExpireDate(String dependentIdExpireDate) {
		this.dependentIdExpireDate = dependentIdExpireDate;
	}

	public String getDependentIdNoMsg() {
		return dependentIdNoMsg;
	}

	public void setDependentIdNoMsg(String dependentIdNoMsg) {
		this.dependentIdNoMsg = dependentIdNoMsg;
	}

	public String getDependentIdExpireDateMsg() {
		return dependentIdExpireDateMsg;
	}

	public void setDependentIdExpireDateMsg(String dependentIdExpireDateMsg) {
		this.dependentIdExpireDateMsg = dependentIdExpireDateMsg;
	}

	public Boolean getRenderRegisterDependent() {
		return renderRegisterDependent;
	}

	public void setRenderRegisterDependent(Boolean renderRegisterDependent) {
		this.renderRegisterDependent = renderRegisterDependent;
	}

	public BigDecimal getDependentCustomerId() {
		return dependentCustomerId;
	}

	public void setDependentCustomerId(BigDecimal dependentCustomerId) {
		this.dependentCustomerId = dependentCustomerId;
	}

	public String getCreatedByDependent() {
		return createdByDependent;
	}

	public void setCreatedByDependent(String createdByDependent) {
		this.createdByDependent = createdByDependent;
	}

	public Date getCreatedDateDependent() {
		return createdDateDependent;
	}

	public void setCreatedDateDependent(Date createdDateDependent) {
		this.createdDateDependent = createdDateDependent;
	}

	public BigDecimal getDependentCustomerReferenceNo() {
		return dependentCustomerReferenceNo;
	}

	public void setDependentCustomerReferenceNo(
			BigDecimal dependentCustomerReferenceNo) {
		this.dependentCustomerReferenceNo = dependentCustomerReferenceNo;
	}

	public BigDecimal getCustomerDependentId() {
		return customerDependentId;
	}

	public void setCustomerDependentId(BigDecimal customerDependentId) {
		this.customerDependentId = customerDependentId;
	}

	public void telephoneCodeFromDB(){
		setPlusSign("+");
	  setTelephoneCode(generalService.getTelephoneCountryBasedOnNationality(getCountryId()));
	}
	
	Map<String, BizComponentConfDetail> returnObjectmob = null;
	Map<String, String> mapValidatorMobile = new HashMap<String,String>();
	Map<String, Object> mapMobile = new TreeMap<String, Object>();
	Map<String, Map<String, Object>> jsonOutputMob = new HashMap<String,Map<String, Object>>();
	

	Map<String, Object> getComponentBehaviorMobileContact(){

		try{

			List<String> componentNames = Arrays.asList("Mobile Contact");
			returnObjectmob = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(),sessionStateManage.getApplicationId(),sessionStateManage.getCompanyId(),getCountryId(),sessionStateManage.getPageId());
			for(Entry<String, BizComponentConfDetail> entry:returnObjectmob.entrySet()){

				mapValidatorMobile = new HashMap<String,String>();
				mapValidatorMobile.put("MIN_VALUE", entry.getValue().getMinValue().toPlainString());
				mapValidatorMobile.put("MAX_VALUE", entry.getValue().getMaxValue().toPlainString());

			}
		}catch(Exception e){

			mapMobile.put("error", "1");
		}
		map.put("data", jsonOutputMob);
		map.put("total", jsonOutputMob.size());
		return map;


	}
	
	public void validateContactMobile(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if(getContactTypeId()!=null){
			if(getContactTypeId().intValue() == getGeneralService().getComponentId(Constants.RESIDENCE,sessionStateManage.getLanguageId())
					.getFsBizComponentData().getComponentDataId().intValue()){

				String returnString = generalService.validateMobileTelephone(sessionStateManage.getCountryAlphaTwoCode(), value.toString(), Constants.MOBILE_CONTACT);
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
	
	/*
	 * 
	 * Fetch Contact  Type
	 * 
	 * */
	public Map<BigDecimal, String> getContactTypeList() {
		Map<BigDecimal, String> mapContactList = ruleEngine.getComponentData("Contact Type");
		if(sessionStateManage.getCountryId().intValue() == getNationality().intValue()){
			mapContactList.remove(getGeneralService().getComponentId(Constants.PERMANENT,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
		}
		return mapContactList;
	}
	
	public void fetcDataThroughMobileNo() throws ParseException{
		
		List<Customer> customerList = icustomerRegistrationService.getCustomerData(sessionStateManage.getCountryId(), getMobileNoFetch());
		if(customerList.size()>0){
			if (customerList.get(0).getSmartCardIndicator().equalsIgnoreCase(Constants.Yes)) {
				RequestContext.getCurrentInstance().execute("dlgSmartcard.show();");
			}else{
			getFetchContactTypeList();
			setBooNomineeFirst(false);
			setBooNomineeAfter(true);
			setPkCustomerId(customerList.get(0).getCustomerId());
			setChangeDobPass(true);
			setCustomerIdProof();
			setCustomerDetails();
			fillCountryList();
			fillStateList();
			fillDistrictList();
			fillCityList();
			setContactDetails();
			setDepenedentDetails();
			setEmployeementInfo();
			setSignaturePanelRender(true);
			setBooChangeDob(false);
			setNextUpdateRender(false);
			setMsgMobileNoFetch(false);
			setBooRemitterInfo(true);
			setBooIdDetail(false);
			setBooCustomerDetails(false);
			setBooContactDetails(false);
			setBooEmploymentDetails(false);
			setBooProof(false);
			setRenderSavebutton(false);
			setRenderSignature(false);
			setBooVerifyAllRen(false);
			setRenderverication(false);
			setSignaturePanelRender(false);
			setRenderUpdate(false);
			setRenderRegistration(false);
			setRenderVerifyReport(false);
			setRenderFinal(false);
			setExitRender(false);
			setNextUpdateRender(false);
			setRenderIntroducePanel(true);
			setBooManualGo(false);
			setBooOtherId(false);
			setBooCivilId(true);
			}
		}else{
			setMsgMobileNoFetch(true);
		}
	}
	
   
   
   public void enableMobileNo(){
	   setMsgMobileNoFetch(false);
	   
	   if(getBooCheckMobile()){
		   setBooMobileHide(true);
		   setBooIdType(false);
		   setIdType(null);
		   setIdNumber(null);
		   setBooIdTypeCheck(false);
	   }else{
		   setBooMobileHide(false);
		   setBooIdType(true);
		   setMobileNoFetch(null);
		   setMsgMobileNoFetch(false);
	   }
	   
   }
   
   public void getArticleDataFromCard() {

	   try{
		   List<ArticleMasterDesc> listArticle = icustomerRegistrationService.getArtilcesThroughCode(getAdditinal1(),sessionStateManage.getLanguageId());
		   if(listArticle.size()>0){
			   setRenManualArticle(false);
			   setRenCardArticle(true);
			   setArticle(listArticle.get(0).getArticleMaster().getArticleId());
			  
					   setArticleDesCard(listArticle.get(0).getArticleeDescription());
				   
			 
			   generateLevel();
		   }else{
			   setRenManualArticle(true);
			   setRenCardArticle(false);
		   }
	   }catch(Exception e){
		   log.info("Article not Available in Card"+e);
		   setRenManualArticle(true);
		   setRenCardArticle(false);
	   }
   }
   
   public void enableDependent(){

	   List<ArticleMaster> articleList = icustomerRegistrationService.getArticleMasterList(getArticle());
	   if(articleList.size()>0){
	   if(articleList.get(0).getCustomerType().equalsIgnoreCase("D")){
		   setRenderShowDependent(true);
		   setDependantIdNumber(null);
		   setDependentIdExpireDate(null);
		   setDependentRelationship(null);
		   setDependentName(null);
	   }else{
		   
		   setRenderShowDependent(false);
	   }
	   }else{
		  
		   setRenderShowDependent(false);
	   }

   }
   
   public void clearDependent(){
	setDependantIdNumber(null);
	 setDependentIdExpireDate(null);
	 setDependentRelationship(null);
	 setDependentName(null);
	 setRenDependent(false);
	 setRenderShowDependent(false);
	 setRenderRegisterDependent(false);
	 setCustomerDependentId(null);
	
   }
   
   private List<RelationsDescription> relationDescList  = new ArrayList<RelationsDescription>();
   
   
   
   public List<RelationsDescription> getRelationDescList(){
	   
	   relationDescList = icustomerRegistrationService.getRelationsDescriptionList(sessionStateManage.getLanguageId());
	   return relationDescList;
   }
   
   public void checkDependentCustomer() throws ParseException{
	   setDependentIdNoMsg(null);
		setDependentIdExpireDateMsg(null);
		BigDecimal customerType = getGeneralService().getComponentId(Constants.CUSTOMERTYPE_INDU,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
		List<CustomerIdProof>  registerIdProofList= icustomerRegistrationService.getRegisterId(getDependantIdNumber(), sessionStateManage.getCountryId(), customerType);
		if(registerIdProofList.size()>0){
			setRenderRegisterDependent(false);
			for(CustomerIdProof regIdList:registerIdProofList){
				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

				if(dateformat.parse(dateformat.format(regIdList.getIdentityExpiryDate())).compareTo(dateformat.parse(dateformat.format(new Date())))<=0){
					setDependentIdNoMsg(regIdList.getIdentityInt());
					setDependentIdExpireDateMsg(dateformat.format(regIdList.getIdentityExpiryDate()));
					setDependentName(null);
					setDependentIdExpireDate(null);
					setDependentRelationship(null);
					setDependentCustomerReferenceNo(regIdList.getFsCustomer().getCustomerReference());

					RequestContext.getCurrentInstance().execute("dependentIdExpired.show();");
			}else{
				
				setDependentIdNoMsg(null);
				setDependentIdExpireDateMsg(null);
				setDependentName(regIdList.getFsCustomer().getFirstName());
				setDependentIdExpireDate(dateformat.format(regIdList.getIdentityExpiryDate()));
				setDependentCustomerId(regIdList.getFsCustomer().getCustomerId());
				setDependentCustomerReferenceNo(regIdList.getFsCustomer().getCustomerReference());
				break;
				/*List<CustomerDependant> listCustomerDep = icustomerRegistrationService.checkDependantList(getPkCustomerId(), getDependentCustomerReferenceNo());
				   if(listCustomerDep.size()>0){
					   setCustomerDependentId(listCustomerDep.get(0).getCustomerDependantId());
					   setRenDependent(true);
					   RequestContext.getCurrentInstance().execute("dependentchange.show();");
				   }*/
				
			}
			
			}
			
		}else{
			setRenderRegisterDependent(true);
		}
   }
   
 public void checkDuplicateDependant(){
	 List<CustomerDependant> dependentList = icustomerRegistrationService.getDependantList(getPkCustomerId(),getDependentCustomerId());
	   if(dependentList.size()>0){
		   setDependentIdNoMsg(null);
			setDependentIdExpireDateMsg(null);
			setDependentName(null);
			setDependentIdExpireDate(null);
			setDependentRelationship(null);
			setDependentCustomerReferenceNo(null);
		   RequestContext.getCurrentInstance().execute("dependentDuplicate.show();");
	   }else{
		   
	   }
   }
   public CustomerSponsor saveCustomerDependant(){
	  
	   CustomerSponsor custDependant = new CustomerSponsor();
	   
	   Customer customer = new Customer();
		customer.setCustomerId(getPkCustomerId());
		custDependant.setFsCustomer(customer);
		
	  Relations relations = new Relations();
	  relations.setRelationsId(getDependentRelationship());
	  custDependant.setExRelations(relations);
	  
	  custDependant.setCustomerRefrenceNo(getUpdateCustomerRefNo());
	  custDependant.setSponsorName(getDependentName());
	  
	  custDependant.setIsActive(Constants.Yes);
	  if(getCustomerDependentId()!=null){
		  custDependant.setCreatedBy(getCreatedByDependent());
		  custDependant.setCreatedDate(getCreatedDateDependent());
		  custDependant.setCustomerSponsorId(getCustomerDependentId());
		  custDependant.setUpdatedBy(sessionStateManage.getUserName());
		  custDependant.setUpdatedDate(getCurrentTime());
	  }else{
		  custDependant.setCreatedBy(sessionStateManage.getUserName());
		  custDependant.setCreatedDate(getCurrentTime());
	  }
	   
	return custDependant;
	   
   }
   
   public void setDepenedentDetails() throws ParseException{
	   
	   clearDependent();
	   List<CustomerSponsor> listCustomerDependent = icustomerRegistrationService.getCustomerSponsorList(getPkCustomerId());
	   if(listCustomerDependent.size()>0){
		   setRenderShowDependent(true);
		   setCustomerDependentId(listCustomerDependent.get(0).getCustomerSponsorId());
		   setDependentName(listCustomerDependent.get(0).getSponsorName());
		   setDependentCustomerReferenceNo(listCustomerDependent.get(0).getCustomerRefrenceNo());
		   setCreatedByDependent(listCustomerDependent.get(0).getCreatedBy());
		   setCreatedDateDependent(listCustomerDependent.get(0).getCreatedDate());
		   getRelationDescList();
		   setDependentRelationship(listCustomerDependent.get(0).getExRelations().getRelationsId());
		
		 
	   }
	   
	   
   }
   
   public void getCustomerRefrenceNo(){
	   List<Customer> custlist = getIcustomerRegistrationService().getVerificationToken(getPkCustomerId());
		if (custlist.size() > 0) {
			
			if(custlist.get(0).getCustomerReference()!=null){
				setUpdateCustomerRefNo(custlist.get(0).getCustomerReference());
			}
		}
   }
   
   
   public void okAddress(){
	   setContactTypeId(null);
   }
   
	private BigDecimal dealYearId;
	private String userDealYear;
	private BigDecimal userDealYearId;
	private BigDecimal companyCode;
	
	@Autowired
	ICompanyMasterservice icompanyMaster;
	
   
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

	public String getDealYearbyDate() {
		
		try{
			DealYearList = generalService.getDealYear(new Date());
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
		
		List<CompanyMasterDesc> lstcompanycode = icompanyMaster.viewById(session.getCompanyId());
		
		if(lstcompanycode.size() != 0){
			
			for (CompanyMasterDesc companyMasterDesc : lstcompanycode) {
				setCompanyCode(companyMasterDesc.getFsCompanyMaster().getCompanyCode());
			}
			
		}
		
		return companyCode;
	}
   //New CR
	
	private Boolean renMobilContactLocal = false;
	private Boolean renMobileContact = true;
	private Boolean renContactMobile = false;
	private Boolean renContactTel = false;
	private Boolean booCheckMobileContact = false;
	private Boolean booCheckTelContact = false;



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

	public Boolean getRenContactMobile() {
		return renContactMobile;
	}

	public void setRenContactMobile(Boolean renContactMobile) {
		this.renContactMobile = renContactMobile;
	}

	public Boolean getRenContactTel() {
		return renContactTel;
	}

	public void setRenContactTel(Boolean renContactTel) {
		this.renContactTel = renContactTel;
	}

	public Boolean getBooCheckMobileContact() {
		return booCheckMobileContact;
	}

	public void setBooCheckMobileContact(Boolean booCheckMobileContact) {
		this.booCheckMobileContact = booCheckMobileContact;
	}

	public Boolean getBooCheckTelContact() {
		return booCheckTelContact;
	}

	public void setBooCheckTelContact(Boolean booCheckTelContact) {
		this.booCheckTelContact = booCheckTelContact;
	}
	
  public void enableMobileNoContact(){
	if(getBooCheckMobileContact()){
		setRenContactMobile(true);
	}else{
		setRenContactMobile(false);
	}
	
  }
	
	 public void enableTelNoContact(){
			if(getBooCheckTelContact()){
				setRenContactTel(true);
			}else{
				setRenContactTel(false);
			}
	
}
		 
	 
	
}
		
		