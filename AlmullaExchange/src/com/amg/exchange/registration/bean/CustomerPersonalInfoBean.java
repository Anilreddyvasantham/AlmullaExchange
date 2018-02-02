package com.amg.exchange.registration.bean;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.imageio.ImageIO;
import javax.mail.internet.AddressException;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.amg.exchange.bean.AddContactDetailBean;
import com.amg.exchange.bean.CreateProofTable;
import com.amg.exchange.bean.CustomerLogReport;
import com.amg.exchange.bean.CustomerMainReport;
import com.amg.exchange.bean.PepReport;
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
import com.amg.exchange.mail.ApllicationMailer1;
import com.amg.exchange.registration.model.ArcmateScanMaster;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerChangeLog;
import com.amg.exchange.registration.model.CustomerContactDetailView;
import com.amg.exchange.registration.model.CustomerEmployeeInfoView;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerIdproofView;
import com.amg.exchange.registration.model.CustomerImageVerification;
import com.amg.exchange.registration.model.CustomerInfoView;
import com.amg.exchange.registration.model.CustomerMobileLogModel;
import com.amg.exchange.registration.model.CustomerSponsor;
import com.amg.exchange.registration.model.DMSApplMap;
import com.amg.exchange.registration.model.DmsDocBlobUpload;
import com.amg.exchange.registration.model.DmsDocumentBlobTemparory;
import com.amg.exchange.registration.model.DmsMas;
import com.amg.exchange.registration.model.EMOSCustomer;
import com.amg.exchange.registration.model.IdentityTypeMaster;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.registration.model.ScanIdTypeMaster;
import com.amg.exchange.registration.model.ViewActiveCustomerCheck;
import com.amg.exchange.registration.model.ViewOMIDTemp;
import com.amg.exchange.registration.model.ViewRemarks;
import com.amg.exchange.registration.service.IBranchPageService;
import com.amg.exchange.registration.service.ICompanyMasterservice;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.registration.service.IEncrptionDescriptionService;
import com.amg.exchange.registration.service.ILoginService;
import com.amg.exchange.remittance.bean.PersonalRemittanceBean;
import com.amg.exchange.remittance.model.AuthorizedLog;
import com.amg.exchange.remittance.model.CustomerBank;
import com.amg.exchange.remittance.model.DebitAutendicationView;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.model.RelationsDescription;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.remittance.service.ICustomerBankService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BankPrefix;
import com.amg.exchange.treasury.service.IGLTransactionForADocument;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.CustomExceptionHandle;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.ReadScanProperties;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;
import com.amg.exchange.wu.bean.WUTranxFileUploadBean;

@Component(value = "customerPersonalInfo")
@Scope("session")
public class CustomerPersonalInfoBean<T> implements Serializable {
	Logger log = Logger.getLogger(CustomerPersonalInfoBean.class);
	private static final long serialVersionUID = 1L;

	// Manual Personal Info Variabel
	public BigDecimal pkCustomerId = null;
	public String expDateCheck = null;
	private String idType = null;
	private String selectType = "Manual";
	private String manual = null;
	private String smartCard = null;
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
	private String introducedBy = null;
	private String medicalInsuranceInd = null;
	private String AMLStatus = null;
	private BigDecimal numberofhits = null;
	private String pepsindicator = null;
	private String introducedDate = null;
	private BigDecimal custRefId = null;
	private Date creationDateCustomer = null;
	private String createdByCustomer = null;
	private String emosCustomer = null;
	public String activeInd = null;
	private Boolean booReadonly = true;
	public String isActive = null;
	private String nationalityName = null;
	private String remarks;
	private Boolean boolRemarksforStoM = false;
	private String smartCardInd = null;

	// Smart Card Kuwait Personal Info Variable

	// this is used for Smart card Information
	private String titleEn;
	private String fullNameEn;
	private String firstNameEn;
	private String lastNameEn;
	private String middleNameEn;
	private String shortNameEn;
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
	private String firstNameArabic;

	private String middleNameArabic;
	private String lastNameArabic;
	private String arabicSurnameLocal;
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
	private String kioskPin;

	private String craetedDateforCustomer;
	private Boolean minagevalidation = false;

	// check maula or smart card
	public Boolean smartcardcheck = false;

	private Boolean booManual = false;
	private Boolean booSmartcard = false;
	private Boolean booIdDetail = true;
	private Boolean boosmartCardAppear = true;
	private Boolean booChangeDob = false;
	private Boolean booCivilId = false;
	private Boolean booOtherId = false;
	private Boolean renderDobChangeMessage = false;
	private String showDob = null;
	private Date dob = null;
	private String minDob;
	private BigDecimal dealYearId;
	private String userDealYear;
	private BigDecimal userDealYearId;
	private BigDecimal companyCode;
	private String dealYear = null;

	private String exceptionMessage = null;
	private String allow_Ind = null;
	private String custRefNo = null;

	private BigDecimal updateCustomerRefNo = null; // Customer Reference No
	private String digitalSignature;
	public Date activeDate;
	private BigDecimal introduceBy = null;
	private String mobileNoFetch = null;
	private Boolean changeDobPass = false;
	private Boolean booMobilecheck = false;
	private Boolean booEmailCheck = false;
	private Boolean booIdTypeCheck = false;
	private String selectedIdType = null;
	private Boolean booIdType = true;
	private Boolean msgMobileNoFetch = false;
	private Boolean booMobileHide = false;
	private Boolean booCheckMobile = false;
	private Boolean booCheckActiveCutomer = false;

	// this varibale for verification online customer
	private Boolean renderverication = false;
	private String idNumberverification;
	private String tokenNumber;
	private String DATE_FORMAT = "dd/MM/yyyy";
	public BigDecimal customerIDProofId = null;
	private String effectiveMinDate;

	// peps Indicator
	private String pepsReportStatus = null;
	private Boolean disablePipsDropdown = false;
	private Boolean booPepDescriptionReport = false;
	private Boolean booPepDescriptionUpdateReport = false;

	// diable Idexipry filed in customer scaninfo page
	private Boolean renderIdExpiryDateInputField = false;
	private Boolean renderIdExpiryDateCalenderField = false;

	// returnString for defining navigation Rules
	private String returnString = " ";
	private BigDecimal noOfCount = null;
	private int wordCountEn = 0;
	private int wordCountAr = 0;
	public Boolean renderNameCardEn = false;
	public Boolean renderNameCardEnEdit = false;
	public Boolean renderNameCardlocal = false;
	public Boolean renderNameCardlocalEdit = false;

	private Date authorizedDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal customerBankId;
	private boolean booRenderClearDebitCard = false;

	public Boolean booTitleKwt = false;
	public Boolean booTitleOman = false;

	// otp
	public Boolean booRenderOtpAuthPanel = false;
	private BigDecimal otpRetry;
	private Date otpRetryDate;
	private Date otpVerifiedDate;
	private String otpVerifiedBy;
	private Boolean booMobileForOTP = false;
	private String mobileTemp = null;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;


	@Autowired
	RuleEngine<T> ruleEngine;
	@Autowired
	ICompanyMasterservice icompanyMaster;

	@Autowired
	ILoginService<T> loginService;

	// ExternalContext context =
	// FacesContext.getCurrentInstance().getExternalContext();
	SessionStateManage sessionStateManage = new SessionStateManage();
	CustomExceptionHandle exception = new CustomExceptionHandle();
	private Map<BigDecimal, String> idTypeMap = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapIdentityType = new HashMap<BigDecimal, String>();
	List<UserFinancialYear> dealYearList = new ArrayList<UserFinancialYear>();
	private List<CustomerIdProof> customerReferencaList = new ArrayList<CustomerIdProof>();
	private List<Customer> customerIntroducedList = new ArrayList<Customer>();
	private List<CustomerIdProof> customerIdProofList = new ArrayList<CustomerIdProof>();
	private List<Customer> customerList = new ArrayList<Customer>();
	Map<BigDecimal, String> mapNationalityList = new HashMap<BigDecimal, String>();
	public List<CountryMasterDesc> nationalityNameList;
	CopyOnWriteArrayList<CreateProofTable> createProofList = new CopyOnWriteArrayList<CreateProofTable>();
	public List<CreateProofTable> dummiCustomerIdProofList = new ArrayList<CreateProofTable>();
	private Map<BigDecimal, String> idForMap = new HashMap<BigDecimal, String>();
	private List<DmsMas> smartCardInfoList = new ArrayList<DmsMas>();
	private List<Customer> custlist = new ArrayList<Customer>();
	private List<BigDecimal> contactPkList = new ArrayList<BigDecimal>();
	// Added by Rabil 0n 19102015
	// List<ViewActiveCustomerCheck> activeCustomerList = new
	// ArrayList<ViewActiveCustomerCheck>();

	private Map<BigDecimal, String> idTypeValues = new HashMap<BigDecimal, String>();

	public Boolean getRenderIdExpiryDateInputField() {
		return renderIdExpiryDateInputField;
	}

	public Boolean getRenderIdExpiryDateCalenderField() {
		return renderIdExpiryDateCalenderField;
	}

	public void setRenderIdExpiryDateInputField(Boolean renderIdExpiryDateInputField) {
		this.renderIdExpiryDateInputField = renderIdExpiryDateInputField;
	}

	public void setRenderIdExpiryDateCalenderField(Boolean renderIdExpiryDateCalenderField) {
		this.renderIdExpiryDateCalenderField = renderIdExpiryDateCalenderField;
	}

	public Map<BigDecimal, String> getIdTypeValues() {
		return idTypeValues;
	}

	public void setIdTypeValues(Map<BigDecimal, String> idTypeValues) {
		this.idTypeValues = idTypeValues;
	}

	public BigDecimal getPkCustomerId() {
		return pkCustomerId;
	}

	public void setPkCustomerId(BigDecimal pkCustomerId) {
		this.pkCustomerId = pkCustomerId;
	}

	public String getExpDateCheck() {
		return expDateCheck;
	}

	public void setExpDateCheck(String expDateCheck) {
		this.expDateCheck = expDateCheck;
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

	public String getIntroducedBy() {
		return introducedBy;
	}

	public void setIntroducedBy(String introducedBy) {
		this.introducedBy = introducedBy;
	}

	public String getMedicalInsuranceInd() {
		return medicalInsuranceInd;
	}

	public void setMedicalInsuranceInd(String medicalInsuranceInd) {
		this.medicalInsuranceInd = medicalInsuranceInd;
	}

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

	public BigDecimal getCustRefId() {
		return custRefId;
	}

	public void setCustRefId(BigDecimal custRefId) {
		this.custRefId = custRefId;
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

	public String getEmosCustomer() {
		return emosCustomer;
	}

	public void setEmosCustomer(String emosCustomer) {
		this.emosCustomer = emosCustomer;
	}

	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	public Boolean getBooReadonly() {
		return booReadonly;
	}

	public void setBooReadonly(Boolean booReadonly) {
		this.booReadonly = booReadonly;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

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

	public String getMiddleNameEn() {
		return middleNameEn;
	}

	public void setMiddleNameEn(String middleNameEn) {
		this.middleNameEn = middleNameEn;
	}

	public String getShortNameEn() {
		return shortNameEn;
	}

	public void setShortNameEn(String shortNameEn) {
		this.shortNameEn = shortNameEn;
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

	public String getGenderAr() {
		return genderAr;
	}

	public void setGenderAr(String genderAr) {
		this.genderAr = genderAr;
	}

	public String getNationalityAr() {
		return nationalityAr;
	}

	public void setNationalityAr(String nationalityAr) {
		this.nationalityAr = nationalityAr;
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

	public String getScardemail() {
		return scardemail;
	}

	public void setScardemail(String scardemail) {
		this.scardemail = scardemail;
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

	public String getFirstNameArabic() {
		return firstNameArabic;
	}

	public void setFirstNameArabic(String firstNameArabic) {
		this.firstNameArabic = firstNameArabic;
	}

	public String getMiddleNameArabic() {
		return middleNameArabic;
	}

	public void setMiddleNameArabic(String middleNameArabic) {
		this.middleNameArabic = middleNameArabic;
	}

	public String getLastNameArabic() {
		return lastNameArabic;
	}

	public void setLastNameArabic(String lastNameArabic) {
		this.lastNameArabic = lastNameArabic;
	}

	public String getArabicSurnameLocal() {
		return arabicSurnameLocal;
	}

	public void setArabicSurnameLocal(String arabicSurnameLocal) {
		this.arabicSurnameLocal = arabicSurnameLocal;
	}

	public String getGuardianCivilId() {
		return guardianCivilId;
	}

	public void setGuardianCivilId(String guardianCivilId) {
		this.guardianCivilId = guardianCivilId;
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

	public String getKioskPin() {
		return kioskPin;
	}

	public void setKioskPin(String kioskPin) {
		this.kioskPin = kioskPin;
	}

	public String getCraetedDateforCustomer() {
		return craetedDateforCustomer;
	}

	public void setCraetedDateforCustomer(String craetedDateforCustomer) {
		this.craetedDateforCustomer = craetedDateforCustomer;
	}

	public Boolean getSmartcardcheck() {
		return smartcardcheck;
	}

	public void setSmartcardcheck(Boolean smartcardcheck) {
		this.smartcardcheck = smartcardcheck;
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

	public Boolean getRenderverication() {
		return renderverication;
	}

	public void setRenderverication(Boolean renderverication) {
		this.renderverication = renderverication;
	}

	public Boolean getBoosmartCardAppear() {
		return boosmartCardAppear;
	}

	public void setBoosmartCardAppear(Boolean boosmartCardAppear) {
		this.boosmartCardAppear = boosmartCardAppear;
	}

	public Boolean getBooChangeDob() {
		return booChangeDob;
	}

	public void setBooChangeDob(Boolean booChangeDob) {
		this.booChangeDob = booChangeDob;
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

	public Boolean getRenderDobChangeMessage() {
		return renderDobChangeMessage;
	}

	public void setRenderDobChangeMessage(Boolean renderDobChangeMessage) {
		this.renderDobChangeMessage = renderDobChangeMessage;
	}

	public String getShowDob() {
		return showDob;
	}

	public void setShowDob(String showDob) {
		this.showDob = showDob;
	}

	public BigDecimal getUpdateCustomerRefNo() {
		return updateCustomerRefNo;
	}

	public void setUpdateCustomerRefNo(BigDecimal updateCustomerRefNo) {
		this.updateCustomerRefNo = updateCustomerRefNo;
	}

	public String getDigitalSignature() {
		return digitalSignature;
	}

	public void setDigitalSignature(String digitalSignature) {
		this.digitalSignature = digitalSignature;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getMinDob() {
		return minDob;
	}

	public void setMinDob(String minDob) {
		this.minDob = minDob;
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

	public String getDealYear() {
		return dealYear;
	}

	public void setDealYear(String dealYear) {
		this.dealYear = dealYear;
	}

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

	public BigDecimal getIntroduceBy() {
		return introduceBy;
	}

	public void setIntroduceBy(BigDecimal introduceBy) {
		this.introduceBy = introduceBy;
	}

	public String getMobileNoFetch() {
		return mobileNoFetch;
	}

	public void setMobileNoFetch(String mobileNoFetch) {
		this.mobileNoFetch = mobileNoFetch;
	}

	public Boolean getChangeDobPass() {
		return changeDobPass;
	}

	public void setChangeDobPass(Boolean changeDobPass) {
		this.changeDobPass = changeDobPass;
	}

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

	public Boolean getBooIdTypeCheck() {
		return booIdTypeCheck;
	}

	public void setBooIdTypeCheck(Boolean booIdTypeCheck) {
		this.booIdTypeCheck = booIdTypeCheck;
	}

	public String getSelectedIdType() {
		return selectedIdType;
	}

	public void setSelectedIdType(String selectedIdType) {
		this.selectedIdType = selectedIdType;
	}

	public Boolean getBooIdType() {
		return booIdType;
	}

	public void setBooIdType(Boolean booIdType) {
		this.booIdType = booIdType;
	}

	public Boolean getMsgMobileNoFetch() {
		return msgMobileNoFetch;
	}

	public void setMsgMobileNoFetch(Boolean msgMobileNoFetch) {
		this.msgMobileNoFetch = msgMobileNoFetch;
	}

	public Boolean getBooMobileHide() {
		return booMobileHide;
	}

	public void setBooMobileHide(Boolean booMobileHide) {
		this.booMobileHide = booMobileHide;
	}

	public Boolean getBooCheckMobile() {
		return booCheckMobile;
	}

	public void setBooCheckMobile(Boolean booCheckMobile) {
		this.booCheckMobile = booCheckMobile;
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

	public BigDecimal getCustomerIDProofId() {
		return customerIDProofId;
	}

	public void setCustomerIDProofId(BigDecimal customerIDProofId) {
		this.customerIDProofId = customerIDProofId;
	}

	public String getEffectiveMinDate() {
		return effectiveMinDate;
	}

	public void setEffectiveMinDate(String effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}

	public Boolean getDisablePipsDropdown() {
		return disablePipsDropdown;
	}

	public void setDisablePipsDropdown(Boolean disablePipsDropdown) {
		this.disablePipsDropdown = disablePipsDropdown;
	}

	public Boolean getBooPepDescriptionReport() {
		return booPepDescriptionReport;
	}

	public void setBooPepDescriptionReport(Boolean booPepDescriptionReport) {
		this.booPepDescriptionReport = booPepDescriptionReport;
	}

	public Boolean getBooPepDescriptionUpdateReport() {
		return booPepDescriptionUpdateReport;
	}

	public void setBooPepDescriptionUpdateReport(Boolean booPepDescriptionUpdateReport) {
		this.booPepDescriptionUpdateReport = booPepDescriptionUpdateReport;
	}

	public List<CountryMasterDesc> getNationalityNameList() {
		return nationalityNameList;
	}

	public void setNationalityNameList(List<CountryMasterDesc> nationalityNameList) {
		this.nationalityNameList = nationalityNameList;
	}

	public List<CustomerIdProof> getCustomerIdProofList() {
		return customerIdProofList;
	}

	public void setCustomerIdProofList(List<CustomerIdProof> customerIdProofList) {
		this.customerIdProofList = customerIdProofList;
	}

	public List<CreateProofTable> getDummiCustomerIdProofList() {
		return dummiCustomerIdProofList;
	}

	public void setDummiCustomerIdProofList(List<CreateProofTable> dummiCustomerIdProofList) {
		this.dummiCustomerIdProofList = dummiCustomerIdProofList;
	}

	public CopyOnWriteArrayList<CreateProofTable> getCreateProofList() {
		return createProofList;
	}

	public void setCreateProofList(CopyOnWriteArrayList<CreateProofTable> createProofList) {
		this.createProofList = createProofList;
	}

	public Boolean getMinagevalidation() {
		return minagevalidation;
	}

	public void setMinagevalidation(Boolean minagevalidation) {
		this.minagevalidation = minagevalidation;
	}

	public String getNationalityName() {
		return nationalityName;
	}

	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}

	public BigDecimal getNoOfCount() {
		return noOfCount;
	}

	public void setNoOfCount(BigDecimal noOfCount) {
		this.noOfCount = noOfCount;
	}

	public int getWordCountEn() {
		return wordCountEn;
	}

	public void setWordCountEn(int wordCountEn) {
		this.wordCountEn = wordCountEn;
	}

	public int getWordCountAr() {
		return wordCountAr;
	}

	public void setWordCountAr(int wordCountAr) {
		this.wordCountAr = wordCountAr;
	}

	public Boolean getRenderNameCardEn() {
		return renderNameCardEn;
	}

	public void setRenderNameCardEn(Boolean renderNameCardEn) {
		this.renderNameCardEn = renderNameCardEn;
	}

	public Boolean getRenderNameCardEnEdit() {
		return renderNameCardEnEdit;
	}

	public void setRenderNameCardEnEdit(Boolean renderNameCardEnEdit) {
		this.renderNameCardEnEdit = renderNameCardEnEdit;
	}

	public Boolean getRenderNameCardlocal() {
		return renderNameCardlocal;
	}

	public void setRenderNameCardlocal(Boolean renderNameCardlocal) {
		this.renderNameCardlocal = renderNameCardlocal;
	}

	public Boolean getRenderNameCardlocalEdit() {
		return renderNameCardlocalEdit;
	}

	public void setRenderNameCardlocalEdit(Boolean renderNameCardlocalEdit) {
		this.renderNameCardlocalEdit = renderNameCardlocalEdit;
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

	public BigDecimal getCustomerBankId() {
		return customerBankId;
	}
	public void setCustomerBankId(BigDecimal customerBankId) {
		this.customerBankId = customerBankId;
	}

	public boolean isBooRenderClearDebitCard() {
		return booRenderClearDebitCard;
	}
	public void setBooRenderClearDebitCard(boolean booRenderClearDebitCard) {
		this.booRenderClearDebitCard = booRenderClearDebitCard;
	}

	public Date getAuthorizedDate() {
		return authorizedDate;
	}
	public void setAuthorizedDate(Date authorizedDate) {
		this.authorizedDate = authorizedDate;
	}
	
	public String getMobileTemp() {
		return mobileTemp;
	}
	public void setMobileTemp(String mobileTemp) {
		this.mobileTemp = mobileTemp;
	}

	/**
	 * THIS METHOD CALL FOR EITHER SELECT CARD OR MANUAL
	 */

	public void appearCarddetail() throws ParseException, DOMException, ParserConfigurationException, SAXException, IOException {
		setMobileFetch(false);
		setBooMobilecheck(false);
		setBooEmailCheck(false);
		setSmartCardInd(null);
		setRemarks(null);
		setBoolRemarksforStoM(false);
		setCustomerIDProofId(null);
		setBooCheckMobile(false);
		setShowDob(null);
		if (getSelectType() != null
				&& getSelectType().equalsIgnoreCase(
						generalService.getComponentId(Constants.METHODTYPE, sessionStateManage.getLanguageId()).getFsBizComponentData()
						.getComponentDataId().toPlainString())) {
			// setIdNumber(null);
			setEmail(null);
			setMobile(null);
			setMobileTemp(null);
			setSmartcardcheck(false);
			setBooManual(true);
			setBoosmartCardAppear(false);
			setRenderverication(false);
			setPkCustomerId(null);
			setBooMobileHide(false);
			setBooIdType(true);
			setMobileNoFetch(null);
			setBooMobilecheck(false);

		} else {
			setRenderNameCardEn(true);
			setRenderNameCardlocal(true);
			setRenderNameCardEnEdit(false);
			setRenderNameCardlocalEdit(false);
			setSmartCardId(null);
			setSmartcardcheck(true);
			setPkCustomerId(null);
			setUpdateCustomerRefNo(null);
			setBooManual(false);
			setBoosmartCardAppear(false);
			setRenderverication(false);
			setCustomerIDProofId(null);
			fetchSmartCardData();

		}

	}

	/**
	 * method is responsible to populate List of Nationality from DB
	 * 
	 * @return
	 */

	public void populateNationality() {
		// SessionStateManage sessionStateManage = new SessionStateManage();

		List<CountryMasterDesc> nationalityList = new ArrayList<CountryMasterDesc>();
		nationalityList.addAll(generalService.getNationalityList(sessionStateManage.getLanguageId()));
		for (CountryMasterDesc countryMasterDesc : nationalityList) {
			mapNationalityList.put(countryMasterDesc.getCountryMasterId(), countryMasterDesc.getCountryName());
		}
		setNationalityNameList(nationalityList);

	}

	/**
	 * method is responsible to populate List of ID Type
	 * 
	 * @return
	 */

	public void getFetchContactTypeList() {
		mapContactTypeList = ruleEngine.getComponentData("Contact Type");
		idForMap = ruleEngine.getComponentData("ID For");
		idTypeMap = ruleEngine.getComponentData("Identity Type");
	}

	// Timezone for application country based creation log
	private Date currentTime = new Date();

	/**
	 * method is responsible to populate DB Current time
	 */

	public Date getCurrentTime() {

		Date objList = generalService.getSysDateTimestamp(sessionStateManage.getCountryId());

		if (objList != null) {
			currentTime = objList;
		} else {

			currentTime = null;
		}

		return currentTime;
	}

	public String getDealYearbyDate() {

		try {
			dealYearList = generalService.getDealYear(new Date());
			if (dealYearList != null) {
				if (getUserDealYear() == null) {
					dealYear = dealYearList.get(0).getFinancialYear().toString();
					dealYearId = dealYearList.get(0).getFinancialYearID();
					setDealYearId(dealYearId);
					setDealYear(dealYear);
				} else {
					setDealYear(getUserDealYear());
					setDealYearId(getUserDealYearId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dealYear;
	}

	public BigDecimal getCompanyCodeByCompanyId() {

		List<CompanyMasterDesc> lstcompanycode = icompanyMaster.viewById(sessionStateManage.getCompanyId());

		if (lstcompanycode.size() != 0) {

			for (CompanyMasterDesc companyMasterDesc : lstcompanycode) {
				setCompanyCode(companyMasterDesc.getFsCompanyMaster().getCompanyCode());
			}

		}

		return companyCode;
	}

	public String checkingIdWithDBForProcessing() throws ParseException, IOException {

		returnString = " ";
		setErrorMsg(null);

		setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), sessionStateManage.getCountryId()));



		if (getMobileNoFetch() != null) {
			fetcDataThroughMobileNo();
		} else {
			List<String> outprocedurevalues = new ArrayList<String>();

			try {
				outprocedurevalues = icustomerRegistrationService.getCustomerRefOrSave(new BigDecimal(getIdType()), getIdNumber(),
						Constants.Individual);

				if (outprocedurevalues.size() != 0) {
					setAllow_Ind(outprocedurevalues.get(0));
					setCustRefNo((outprocedurevalues.get(1)) == null ? "" : (outprocedurevalues.get(1)));

					if (getAllow_Ind().equalsIgnoreCase(Constants.Yes)) {  // Always return allow indicator as "Y" since the data are migrated to new if they exists in old emos.
						try {

							boolean civilIdCheck = false;
							BigDecimal idtypeCivilId = generalService.getComponentId(Constants.CIVILID,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
							if(idtypeCivilId !=null && idtypeCivilId.compareTo(new BigDecimal(getIdType()))==0){
								civilIdCheck = true;
							}else{
								BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
								if(idtypeCivilIdnew !=null && idtypeCivilIdnew.compareTo(new BigDecimal(getIdType()))==0){
									civilIdCheck = true;
								}else{
									civilIdCheck = false;
								}
							}

							//if(civilIdCheck){
							if(civilIdCheck && 	sessionStateManage.getCountryName() != null && sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){

								String id = getIdNumber();
								String dob = null;
								if (id.charAt(0) == '2') {
									dob = id.substring(5, 7) + "/" + id.substring(3, 5) + "/19" + id.substring(1, 3);
								} else {
									dob = id.substring(5, 7) + "/" + id.substring(3, 5) + "/20" + id.substring(1, 3);
								}
								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								Date date = formatter.parse(dob);
								setBirthdate(formatter.format(date));

								Calendar cal1 = Calendar.getInstance();
								cal1.setTime(new Date());
								cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
								Date today18 = cal1.getTime();

								if (getBirthdate() != null && !formatter.parse(getBirthdate()).before(today18)) {
									RequestContext.getCurrentInstance().execute("dobcheck.show();");
									returnString = "";
								}else{
									nextRemitterInfo();
								}
							}else{
								nextRemitterInfo();
							}

						} catch (ParseException e) {
							e.printStackTrace();
						}
					} 
					/*else {
						RequestContext.getCurrentInstance().execute("printCustomerReference.show();");
					}*/

				}

			} catch (AMGException e) {

				log.info("SQL EXCEPTION procedure: " + e);
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("sqlexception.show();");
			}

		}

		return returnString;
	}

	public void nextRemitterInfo() throws ParseException {
		// activeCustomerList.clear();
		clearPersonalInfo();
		clearProofInfo();
		setDateExp(null);
		setCustomerIDProofId(null);
		setSmartcardcheck(false);
		createProofList.clear();
		dummiCustomerIdProofList.clear();
		setMobileFetch(false);
		setMobileNoFetch(null);
		populateNationality();
		// Calendar cal1 = new GregorianCalendar();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
		Date today18 = cal1.getTime();
		SimpleDateFormat form1 = new SimpleDateFormat("dd/MM/yyyy");
		String minDateFinal = form1.format(today18);
		setEffectiveMinDate(minDateFinal);

		setPkCustomerId(null);
		setMinagevalidation(false);
		setUpdateCustomerRefNo(null);
		setArticle(null);
		setLevel(null);
		setIncomeRange(null);
		getDealYearbyDate();
		mapIdentityType = ruleEngine.getComponentData("Identity Type");

		BigDecimal identityTpeIds = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
				identityTpeIds = entry.getKey();
				setIdentityTpeIdForDashBoard(identityTpeIds);
				break;
			}
		}

		BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId();
		if (getSelectedIdType() != null && getSelectedIdType().equalsIgnoreCase(identityTpeIds.toPlainString())
				|| getSelectedIdType().equalsIgnoreCase(idtypeCivilIdnew.toPlainString())) {

			boolean status = false;
			try {
				String returnString = generalService.getCivilIdStatus(new BigDecimal(getIdNumber()));
				if (returnString.equalsIgnoreCase(Constants.Yes)) {
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

		if (!getBooIdTypeCheck()) {
			/*
			 * BigDecimal idtypeCivilIdnew =
			 * generalService.getComponentId(Constants.CIVIL_ID_NEW,
			 * sessionStateManage.getLanguageId())
			 * .getFsBizComponentData().getComponentDataId();
			 */
			if (getIdType() != null && idtypeCivilIdnew != null && getIdType().equalsIgnoreCase(identityTpeIds.toString())
					|| getIdType().equalsIgnoreCase(idtypeCivilIdnew.toString())) {
				customerIdProofList = icustomerRegistrationService.getCustomerIdProofCheck(new BigDecimal(identityTpeIds.toString()), getIdNumber(),
						sessionStateManage.getCountryId());

				if (customerIdProofList != null && customerIdProofList.size() == 0) {
					customerIdProofList = icustomerRegistrationService.getCustomerIdProofCheck(idtypeCivilIdnew, getIdNumber(),
							sessionStateManage.getCountryId());
				}
			} else {

				customerIdProofList = icustomerRegistrationService.getCustomerIdProofCheck(new BigDecimal(getIdType()), getIdNumber(),
						sessionStateManage.getCountryId());
			}

			if (customerIdProofList != null && customerIdProofList.size() > 0) {

				Set<BigDecimal> set = new HashSet<BigDecimal>();

				for (CustomerIdProof customerIdProof : customerIdProofList) {

					if(customerIdProof.getFsCustomer().getIsActive()!=null && !customerIdProof.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.D)) {
						set.add(customerIdProof.getFsCustomer().getCustomerId());
					}
				}

				if(set.size() > 1)
				{
					RequestContext.getCurrentInstance().execute("duplicate.show();");
					return ;

				}

				CustomerIdProof customerIdProofObj = customerIdProofList.get(0);
				setPkCustomerId(customerIdProofObj.getFsCustomer().getCustomerId());
				setPepsindicator(customerIdProofObj.getFsCustomer().getPepsIndicator());

				if (customerIdProofObj.getFsCustomer().getActivatedInd() != null
						&& customerIdProofObj.getFsCustomer().getActivatedInd().equalsIgnoreCase(Constants.CUST_INACTIVE_INDICATOR)) {
					RequestContext.getCurrentInstance().execute("dlgAlready.show();");
					// returnString ="";
					return;
					/*
					 * } else if (customerIdProofObj.getFsCustomer()
					 * .getSmartCardIndicator()
					 * .equalsIgnoreCase(Constants.Yes)) {
					 * RequestContext.getCurrentInstance().execute(
					 * "dlgSmartcard.show();"); return;
					 */
				} else if (customerIdProofObj.getFsCustomer().getIsActive() != null
						&& customerIdProofObj.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.D)) {

					RequestContext.getCurrentInstance().execute("customeralreadydeactive.show();");
					return;
				} else {
					// Removed since there is no use of this as of on 07-Sep-2016
					/*try {
						Boolean dashBoardCheck = icustomerRegistrationService.checkDashBoardDisplay(getIdNumber(), sessionStateManage.getCountryId());
						if (dashBoardCheck) {
							setNationality(customerIdProofObj.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
							setMobileContact(customerIdProofObj.getFsCustomer().getMobile());
							returnString = "customerDashBoardPage";
							return;
						} else {
							populateCustomerDetails();
						}
					} catch (AMGException e) {
						// exception.handleException(e);
						log.info("Exception occured in Dash Board Page procedure: " + e);
						RequestContext.getCurrentInstance().execute("error.show();");
						return;
					}*/
					populateCustomerDetails();

				}
			} else {  // Brand New Customer Registration through Java Module

				clearPersonalInfo();
				clearProofInfo();
				setIdnumberProof(getIdNumber());
				setIdTypeproof(new BigDecimal(getIdType()));
				if (getSelectedIdType() != null && getSelectedIdType().equalsIgnoreCase(identityTpeIds.toString())) {
					//Generate DOB based on Country smart card
					if(sessionStateManage.getCountryName() != null && sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
						generateDob();
					}else if(sessionStateManage.getCountryName() != null && sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
						booOtherId = true;
					}
					if (getDob()!=null && !getDob().before(today18)) {
						RequestContext.getCurrentInstance().execute("dobcheck.show();");
						returnString = "";
						return;
					}
				} else {
					setBooOtherId(true);
					setBooCivilId(false);
				}
			}

			String isActive = getActiveCustomerCheckFromView(sessionStateManage.getCountryId(), getPkCustomerId());

			System.out.println("manual  activeCustomerList :" + isActive + "\t Remarks :" + getBoolRemarksforStoM() + "\t getREmarks :"
					+ getRemarks());
			if (getBoolRemarksforStoM() && getRemarks() == null) {
				returnString = "";
				return;
			}

			if (isActive != null && isActive.equalsIgnoreCase("Y")) {
				RequestContext.getCurrentInstance().execute("actCustCheck.show();");
				returnString = "";
				return;
			}
			returnString = "customerManualPage";
		} else {
			// returnString = "";
			return;
		}
	}

	public void fetchCustomerPersonalInformation() {
		log.info("fetchCustomerPersonalInformation :" + getPkCustomerId());
		customerList = icustomerRegistrationService.getCustomerInfo(getPkCustomerId());

		if (customerList != null && customerList.size() != 0) {
			if (customerList.get(0).getFsCountryMasterByNationality() != null
					&& customerList.get(0).getFsCountryMasterByNationality().getCountryId() != null) {
				setNationality(customerList.get(0).getFsCountryMasterByNationality().getCountryId());
			}
			if (customerList.get(0).getFirstName() != null) {
				setFirstName(customerList.get(0).getFirstName().trim());
			}
			if (customerList.get(0).getFirstNameLocal() != null) {
				setFirstNamel(customerList.get(0).getFirstNameLocal().trim());
			}
			if (customerList.get(0).getMiddleName() != null) {
				setMiddleName(customerList.get(0).getMiddleName().trim());
			}
			if (customerList.get(0).getMiddleNameLocal() != null) {
				setMiddleNamel(customerList.get(0).getMiddleNameLocal().trim());
			}
			if (customerList.get(0).getShortName() != null) {
				setShortName(customerList.get(0).getShortName().trim());
			}
			if (customerList.get(0).getShortNameLocal() != null) {
				setShortNamel(customerList.get(0).getShortNameLocal().trim());
			}
			if (customerList.get(0).getLastName() != null) {
				setLastName(customerList.get(0).getLastName().trim());
			}
			if (customerList.get(0).getLastNameLocal() != null) {
				setLastNamel(customerList.get(0).getLastNameLocal().trim());
			}
			setEmail(customerList.get(0).getEmail());
			setTitle(customerList.get(0).getTitle());
			
			if(getBooMobileForOTP()){
				setMobile(getMobile());
				setMobileTemp(getMobileTemp());
				setBooMobileForOTP(false);
			}else{
				setMobile(customerList.get(0).getMobile());
				setMobileTemp(customerList.get(0).getMobile());
			}
			
			
			setGender(customerList.get(0).getGender());
			setEmosCustomer(customerList.get(0).getEmosCustomer());
			setSmartCardInd(customerList.get(0).getSmartCardIndicator());
			if (customerList.get(0).getSmartCardIndicator() != null && customerList.get(0).getSmartCardIndicator().equalsIgnoreCase(Constants.Yes)) {
				setBoolRemarksforStoM(true);
			} else {
				setBoolRemarksforStoM(false);
			}
			
			setKioskPin(customerList.get(0).getKioskPin());
			
			//fetch from fs_customer_mobile_log
			List<CustomerMobileLogModel> lstMobileLog = icustomerRegistrationService.fetchOTPDetailByCustomer(getPkCustomerId());
			if(lstMobileLog != null && lstMobileLog.size() != 0){
				CustomerMobileLogModel custMob = lstMobileLog.get(0);
				setOtpNo(custMob.getOtpNo());
				setOtpVerifiedBy(custMob.getOtpVerifiedBy());
				setOtpVerifiedDate(custMob.getOtpVerifiedDate());
				setOtpRetry(custMob.getOtpRetry());
				setOtpRetryDate(custMob.getOtpRetryDate());
			}
			

			/*
			 * if(customerList.get(0).getSignatureSpecimenClob()!=null){
			 * setDigitalSignature
			 * (customerList.get(0).getSignatureSpecimenClob()
			 * .toString());//clobToString
			 * (customerList.get(0).getSignatureSpecimenClob())); }
			 */

			if (customerList.get(0).getSignatureSpecimenClob() != null) {
				try {
					setDigitalSignature(customerList.get(0).getSignatureSpecimenClob()
							.getSubString(1, (int) customerList.get(0).getSignatureSpecimenClob().length()));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// clobToString(customerList.get(0).getSignatureSpecimenClob()));
			}

			// setDigitalSignature(customerList.get(0).getSignatureSpecimen());
			setActiveInd(customerList.get(0).getActivatedInd());
			setActiveDate(customerList.get(0).getActivatedDate());
			setIsActive(customerList.get(0).getIsActive());
			// setVerificationToken(customerList.get(0).getVerificationTokenId()
			// == null ? "" : customerList.get(0).getVerificationTokenId());
			if (customerList.get(0).getCustomerReference() != null) {
				setUpdateCustomerRefNo(customerList.get(0).getCustomerReference());
			}
			setCreationDateCustomer(customerList.get(0).getCreationDate());
			if (customerList.get(0).getDateOfBirth() != null) {
				setShowDob(new SimpleDateFormat("dd/MM/yyyy").format(customerList.get(0).getDateOfBirth()));
			}
			setDob(customerList.get(0).getDateOfBirth());
			setAlternativeEmail(customerList.get(0).getAlterEmailId());
			if (customerList.get(0).getIntroducedBy() != null) {
				String introducerIdNo = icustomerRegistrationService.getCustomerIdentity(new BigDecimal(customerList.get(0).getIntroducedBy()));
				setIntroducedBy(introducerIdNo);
			}

			if (customerList.get(0).getMedicalInsuranceInd() != null) {
				if (customerList.get(0).getMedicalInsuranceInd().equalsIgnoreCase("Y")) {
					setMedicalInsuranceInd("Y");// Added by Nazish on 11-Feb
					// 2015
				} else {
					setMedicalInsuranceInd("N");
				}
			}

			if (customerList.get(0).getPepsIndicator() != null) {
				if (customerList.get(0).getPepsIndicator().equalsIgnoreCase("Y")) {
					setPepsindicator("Y");// Added by Nazish on 11-Feb 2015
				} else {
					setPepsindicator("N");
				}
			}

			setTitlel(customerList.get(0).getTitleLocal());
			setCreatedByCustomer(customerList.get(0).getCreatedBy());
			setCreationDateCustomer(customerList.get(0).getCreationDate());
			// setDigitalSignature(customerList.get(0).getSignatureSpecimen());

			/*
			 * if(customerList.get(0).getSignatureSpecimenClob()!=null){
			 * setDigitalSignature
			 * (clobToString(customerList.get(0).getSignatureSpecimenClob())); }
			 */

			if (customerList.get(0).getSignatureSpecimenClob() != null) {
				try {
					setDigitalSignature(customerList.get(0).getSignatureSpecimenClob()
							.getSubString(1, (int) customerList.get(0).getSignatureSpecimenClob().length()));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// clobToString(customerList.get(0).getSignatureSpecimenClob()));
			}

			if (customerList.get(0).getFsArticleDetails() != null) {

				setArticle(customerList.get(0).getFsArticleDetails().getFsArticleMaster().getArticleId());
				generateLevel();
				setLevel(customerList.get(0).getFsArticleDetails().getArticleDetailId());
				generateIncomeRange();
				setIncomeRange(customerList.get(0).getFsIncomeRangeMaster().getIncomeRangeId());
			}

			setAMLStatus(customerList.get(0).getAmlStatus());
			setNumberofhits(customerList.get(0).getNumberOfHits());

		} else {

		}
	}

	/*
	 * public void searchClicked() { try { ExternalContext context =
	 * FacesContext.getCurrentInstance() .getExternalContext();
	 * context.redirect("../search/searrchcustomer.xhtml");
	 * 
	 * } catch (Exception e) { log.info("Problem to Redirect the page : " + e);
	 * } }
	 */

	public void fetchIntroducer() {

		List<CustomerIdProof> introducerList = icustomerRegistrationService.getCivilID(getIntroduceBy());
		if (introducerList.size() > 0) {
			setIntroducedBy(introducerList.get(0).getIdentityInt());
		}
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
		} else {
			setMinagevalidation(true);
			setDob(null);
		}
	}

	public void checkMobile() {
		setBooMobilecheck(false);
		setBooMobileForOTP(false);
		List<Customer> matchMobile = new ArrayList<Customer>();
		matchMobile.clear();
		setErrorMsg(null);
		matchMobile.addAll(generalService.getMobileCheck(sessionStateManage.getCountryId(), getMobile()));

		if (getPkCustomerId() != null) {

			customerList = icustomerRegistrationService.getCustomerInfo(getPkCustomerId());

			if (getPkCustomerId().intValue() == customerList.get(0).getCustomerId().intValue()
					&& getMobile().equalsIgnoreCase(customerList.get(0).getMobile())) {
				// same customer id and same mobile
				setBooMobilecheck(false);
				setBooMobileForOTP(false);
			} else if (matchMobile != null && matchMobile.size() > 0) {
				// same mobile but different customer id
				log.info("Mobile No :" + matchMobile.get(0).getCustomerId());
				if (matchMobile.get(0).getCustomerId() != null) {
					setErrorMsg(matchMobile.get(0).getCivilId());
				}
				setBooMobileForOTP(false);
				setBooMobilecheck(true);
			}else{
				// different mobile for same customer need to save in FS_CUSTOMER_MOBILE_LOG
				setBooMobileForOTP(true);
			}

		} else if (matchMobile != null && matchMobile.size() > 0) {
			setBooMobileForOTP(false);
			setBooMobilecheck(true);
		}else{
			// different mobile for new customer need to save in FS_CUSTOMER_MOBILE_LOG
			setBooMobileForOTP(true);
		}

	}

	public void checkEmail() {

		setBooEmailCheck(false);
		List<Customer> matchEmail = new ArrayList<Customer>();

		matchEmail.addAll(generalService.getEmailCheck(getEmail()));

		if (getPkCustomerId() != null) {

			customerList = icustomerRegistrationService.getCustomerInfo(getPkCustomerId());

			if (getPkCustomerId().intValue() == customerList.get(0).getCustomerId().intValue()
					&& getEmail().equalsIgnoreCase(customerList.get(0).getEmail())) {

				setBooEmailCheck(false);

			} else if (matchEmail.size() > 0) {

				setBooEmailCheck(true);

			}

		} else if (matchEmail.size() > 0) {

			setBooEmailCheck(true);

		}

	}

	@SuppressWarnings("unchecked")
	public boolean saveManualPersonaInformation() throws Exception {

		// try {
		boolean checkDupCustRef = false;
		String dupCustRef = "";
		Customer customerinfo = new Customer();

		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(sessionStateManage.getCountryId());
		customerinfo.setFsCountryMasterByCountryId(countryMaster);

		CompanyMaster companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(sessionStateManage.getCompanyId());
		customerinfo.setFsCompanyMaster(companyMaster);

		BizComponentData customerType = new BizComponentData();
		customerType.setComponentDataId(generalService.getComponentId(Constants.CUSTOMERTYPE_INDU, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
		customerinfo.setFsBizComponentDataByCustomerTypeId(customerType);
		BizComponentData companyGroup = new BizComponentData();
		companyGroup.setComponentDataId(generalService.getComponentId(Constants.GROUPID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
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

		if (getNationality() != null) {
			CountryMaster nationality = new CountryMaster();
			nationality.setCountryId(getNationality());
			customerinfo.setFsCountryMasterByNationality(nationality);
		}

		customerinfo.setGender(getGender());
		
		//if verified then only save in customer
		if(getPkCustomerId() != null){
			if(getBooMobileForOTP()){
				// if new number need to hold the old number
				customerinfo.setMobile(getMobileTemp());
			}else{
				customerinfo.setMobile(getMobile());
			}
		}else{
			customerinfo.setMobile(getMobile());
		}
		
		checkMailVerfication(getPkCustomerId());
		
		customerinfo.setEmail(getEmail());
		customerinfo.setAlterEmailId(getAlternativeEmail());
		customerinfo.setBranchCode(new BigDecimal(sessionStateManage.getBranchId()));
		customerinfo.setRemarks(getRemarks());
		if(getIdNumber()!=null){
			customerinfo.setCivilId(getIdNumber());
		}

		customerinfo.setKioskPin(kioskPin);

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
		} else if (getIdType() != null && getIdType().equalsIgnoreCase(identityTpeId.toString())) {
			if (getShowDob() != null) {
				customerinfo.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(getShowDob()));
			} else {
				customerinfo.setDateOfBirth(getDob());
			}
		} else {
			customerinfo.setDateOfBirth(getDob());
		}

		if (getSmartCardInd() != null && getSmartCardInd().equalsIgnoreCase(Constants.Yes)) {
			customerinfo.setSmartCardIndicator(Constants.CUST_ACTIVE_INDICATOR);
		} else {
			customerinfo.setSmartCardIndicator(Constants.CUST_INACTIVE_INDICATOR);
		}

		customerinfo.setActivatedInd(Constants.CUST_ACTIVE_INDICATOR);
		customerinfo.setActivatedDate(getCurrentTime());
		customerinfo.setPepsIndicator(getPepsindicator());
		customerinfo.setMedicalInsuranceInd(getMedicalInsuranceInd());
		customerinfo.setEmosCustomer(getEmosCustomer());

		/*customerinfo.setOtpNo(getOtpNo());
		customerinfo.setOtpRetry(getOtpRetry());
		customerinfo.setOtpRetryDate(getOtpRetryDate());
		customerinfo.setOtpVerifiedBy(getOtpVerifiedBy());
		customerinfo.setOtpVerifiedDate(getOtpVerifiedDate());*/


		if (getIntroducedBy() != null) {
			BigDecimal custRefIntroducer = icustomerRegistrationService.getCustomerReference(getIntroducedBy());
			if (custRefIntroducer.compareTo(BigDecimal.ZERO) != 0) {
				customerinfo.setIntroducedBy(custRefIntroducer.toString());
				customerinfo.setIntroducedDate(new Date());
			}

		}

		if (getIncomeRange() != null) {

			IncomeRangeMaster incomeRange = new IncomeRangeMaster();
			incomeRange.setIncomeRangeId(getIncomeRange());
			customerinfo.setFsIncomeRangeMaster(incomeRange);

			ArticleDetails articleDetail = new ArticleDetails();
			articleDetail.setArticleDetailId(getLevel());
			customerinfo.setFsArticleDetails(articleDetail);

		}
		// AML Check
		String amlReturnStatus = null;
		String amlStatus = null;
		String amlhits = null;

		/*
		 * amlReturnStatus = getAMLCheckStatus(customerinfo);
		 * 
		 * // amlReturnStatus ="PASS-0";
		 * 
		 * if (amlReturnStatus == null) {
		 * customerinfo.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);
		 * customerinfo.setNumberOfHits(new BigDecimal(0)); } else { String[]
		 * parts = amlReturnStatus.split("-"); amlStatus = parts[0]; amlhits =
		 * parts[1]; if
		 * (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_ERROR)) {
		 * customerinfo.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);
		 * customerinfo.setNumberOfHits(new BigDecimal(amlhits)); } if
		 * (amlStatus .equalsIgnoreCase(Constants.FINSCAN_STATUS_PENDING)) {
		 * customerinfo.setAmlStatus(Constants.AML_STATUS_BCO);
		 * customerinfo.setNumberOfHits(new BigDecimal(amlhits)); } if
		 * (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PASS)) {
		 * customerinfo.setAmlStatus(Constants.AML_STATUS_PASS);
		 * customerinfo.setNumberOfHits(new BigDecimal(0)); }
		 * 
		 * }
		 */

		if (getPkCustomerId() != null) {
			customerinfo.setCustomerId(getPkCustomerId());
			customerinfo.setCreatedBy(getCreatedByCustomer());
			customerinfo.setCreationDate(getCreationDateCustomer());
			customerinfo.setIsActive(getIsActive());
			if (getDigitalSignature() != null) {
				// customerinfo.setSignatureSpecimen(getDigitalSignature());
				try {
					customerinfo.setSignatureSpecimenClob(stringToClob(getDigitalSignature()));
				} catch (Exception e) {
					log.info("Clob exception: " + e);
				}
			}
			List<Customer> referenceList = icustomerRegistrationService.getVerificationToken(getPkCustomerId());
			if (referenceList.size() > 0) {
				setUpdateCustomerRefNo(referenceList.get(0).getCustomerReference());
			}
			customerinfo.setCustomerReference(getUpdateCustomerRefNo());
			if (getActiveInd() != null && getActiveInd().equalsIgnoreCase(Constants.CUST_ACTIVE_INDICATOR) && getIsActive() != null
					&& getIsActive().equalsIgnoreCase(Constants.CUST_ACTIVE_INDICATOR)) {
				customerinfo.setUpdatedBy(sessionStateManage.getUserName());
				customerinfo.setLastUpdated(getCurrentTime());
			}

			if (getActiveInd() != null && getActiveInd().equalsIgnoreCase(Constants.CUST_INACTIVE_INDICATOR)) {
				// Added by nazish on 30-09-2015
				customerinfo.setVerificationBy(sessionStateManage.getUserName());
				customerinfo.setVerificationDate(getCurrentTime());
			}

		} else {
			BigDecimal custRef = icustomerRegistrationService.callProcedureCustReferenceNumber(getCompanyCodeByCompanyId(),
					Constants.DOCUMENT_CODE_FOR_CUSTOMER, getDealYearbyDate(), sessionStateManage.getBranchId());

			if (custRef != null) {
				customerinfo.setCustomerReference(custRef);
			}

			customerinfo.setCreatedBy(sessionStateManage.getUserName());
			customerinfo.setCreationDate(getCurrentTime());
			customerinfo.setIsActive(Constants.CUST_INACTIVE_INDICATOR);
		}

		List<Customer> listDetails = icustomerRegistrationService.getIntroducerCustId(customerinfo.getCustomerReference());

		if (listDetails != null && listDetails.size() > 1) {
			int i = 0;
			dupCustRef = "";
			for (Customer customer : listDetails) {
				if(customer.getIsActive() != null && customer.getIsActive().equalsIgnoreCase(Constants.Yes)){
					i++;
					dupCustRef = dupCustRef == "" ? dupCustRef.concat(customer.getCustomerReference().toPlainString()) : dupCustRef.concat(",").concat(customer.getCustomerReference().toPlainString());
				}
			}

			if(i > 1){
				setCustomerDuplicateCustRef(dupCustRef);
				checkDupCustRef = true;
				return checkDupCustRef;
			}
		}

		System.out.println(getIdNumber());

		List<Customer> duplicateCustomer = icustomerRegistrationService.checkDuplicateFSCustomer(getIdNumber());

		if (duplicateCustomer != null) {
			if (duplicateCustomer.size() > 1) {
				int i = 0;
				dupCustRef = "";
				for (Customer customer : listDetails) {
					if(customer.getIsActive() != null && customer.getIsActive().equalsIgnoreCase(Constants.Yes)){
						i++;
						dupCustRef = dupCustRef == "" ? dupCustRef.concat(customer.getCustomerReference().toPlainString()) : dupCustRef.concat(",").concat(customer.getCustomerReference().toPlainString());
					}
				}

				if(i > 1){
					setCustomerDuplicateCustRef(dupCustRef);
					checkDupCustRef = true;
					return checkDupCustRef;
				}
			}
			if (customerinfo.getCustomerReference() != null && duplicateCustomer.size()!=0 
					&& duplicateCustomer.get(0).getCustomerReference() != null) {
				dupCustRef = "";
				if (duplicateCustomer.get(0).getCustomerReference()
						.compareTo(customerinfo.getCustomerReference()) != 0) {
					dupCustRef = dupCustRef.concat(customerinfo.getCustomerReference().toPlainString()).concat(",").concat(duplicateCustomer.get(0).getCustomerReference().toPlainString());
					setCustomerDuplicateCustRef(dupCustRef);
					checkDupCustRef = true;
					return checkDupCustRef;
				}
			}
		}


		try {
			String componentCode = icustomerRegistrationService.getComponentCode(new BigDecimal(getIdType()));
			System.out.println(componentCode);
			List<EMOSCustomer> duplicateEMOSCustomer = icustomerRegistrationService	.checkDuplicateEMOSCustomer(getIdNumber(), componentCode);
			System.out.println("duplicateEMOSCustomer" + duplicateEMOSCustomer.size());
			if (duplicateEMOSCustomer != null && duplicateEMOSCustomer.size() > 1) {
				for (EMOSCustomer emosCustomer : duplicateEMOSCustomer) {
					dupCustRef = dupCustRef == "" ? dupCustRef.concat(emosCustomer.getCusref().toPlainString()) : dupCustRef.concat(",").concat(emosCustomer.getCusref().toPlainString());
				}
				setCustomerDuplicateCustRef(dupCustRef);
				checkDupCustRef = true;
				return checkDupCustRef;
			}

			if (duplicateEMOSCustomer != null && duplicateEMOSCustomer.size() == 1) {

				if (customerinfo.getCustomerReference() != null && duplicateEMOSCustomer.get(0).getCusref() != null) {
					if (duplicateEMOSCustomer.get(0).getCusref().compareTo(customerinfo.getCustomerReference()) != 0) {
						dupCustRef = dupCustRef == "" ? dupCustRef.concat(customerinfo.getCustomerReference().toPlainString()) : dupCustRef.concat(",").concat(duplicateCustomer.get(0).getCustomerReference().toPlainString());
						setCustomerDuplicateCustRef(dupCustRef);
						checkDupCustRef = true;
						return checkDupCustRef;
					}
				}
			}
		}

		catch(Exception e)
		{
			System.out.println("Exception occured "+ e);
		}


		//generalService.saveOrUpdate((T) customerinfo); //Till  12 Oct only record is saved in FS_CUSTOMER


		//Added by Rabil on 12 Oct 2016 to save customer and customer Id proof if any one fail it will rollback
		if(createProofList.isEmpty() && createProofList.size()==0){
			
			CustomerIdProof idProof = saveIdproofPartialReturnObject();
			
			if(getBooMobileForOTP()){
				CustomerMobileLogModel custMobileLog = saveCustomerMobileDetails();
				generalService.saveOrUpdateCustomerAndIdProofAndMobile(customerinfo, idProof, custMobileLog);
			}else{
				generalService.saveOrUpdateCustomerAndIdProof(customerinfo, idProof);
			}
		}else{
			if(getBooMobileForOTP()){
				CustomerMobileLogModel custMobileLog = saveCustomerMobileDetails();
				generalService.saveOrUpdateCustomerAndMobile(customerinfo,custMobileLog); 
			}else{
				generalService.saveOrUpdate((T) customerinfo); 
			}
		}

		setPkCustomerId(customerinfo.getCustomerId());
		
		if(getBooMobileForOTP()){
			setMobile(getMobile());
			setMobileTemp(getMobileTemp());
			setBooMobileForOTP(false);
		}else{
			setMobile(customerinfo.getMobile());
			setMobileTemp(customerinfo.getMobile());
		}
		//setMobile(customerinfo.getMobile());
		//setMobileTemp(customerinfo.getMobile());
		
		if (customerinfo.getFsCountryMasterByNationality().getCountryId() != null) {
			setNationality(customerinfo.getFsCountryMasterByNationality().getCountryId());
		}
		setIsActive(customerinfo.getIsActive());

		BigDecimal customerReference = null;

		List<Customer> custlist = icustomerRegistrationService.getVerificationToken(getPkCustomerId());
		if (custlist != null && custlist.size() > 0) {

			emosCustomer = custlist.get(0).getEmosCustomer();
			if (custlist.get(0).getCustomerReference() != null) {
				customerReference = custlist.get(0).getCustomerReference();
				setUpdateCustomerRefNo(customerReference);
			}
		}
		// getAMLCheckStatus_afterSave(getPkCustomerId());
		if (emosCustomer != null && emosCustomer.equalsIgnoreCase("1")) {
			icustomerRegistrationService.saveCustomerEmos(getPkCustomerId(), Constants.Yes);
		}

		/*
		 * } catch (Exception e) {
		 * log.error("Exception Occured While saving Data", e);
		 * RequestContext.getCurrentInstance().execute("saveerror.show();"); }
		 */
		return checkDupCustRef;

	}

	/*
	 * public String getAMLCheckStatus(Customer customer) {
	 * 
	 * String amlReturnStatus = null; String status = "  ";
	 * 
	 * try { FNSServicesLookupSoapClient finscanService = new
	 * FNSServicesLookupSoapClient();
	 * 
	 * String fullName = customer.getFirstName() + " " +
	 * customer.getMiddleName() + " " + customer.getLastName();
	 * 
	 * String fullNameLocal = customer.getFirstNameLocal() + " " +
	 * customer.getMiddleNameLocal() + " " + customer.getLastNameLocal();
	 * 
	 * amlReturnStatus = finscanService.amlServiceSearch("Individual",
	 * getGender(), "Active", "Yes", "Yes", "Yes", " ", " ", " ", " ", " ", " ",
	 * " ", "Full Source", "Yes", "Yes", "WC", "World-Check", "  ", "  ",
	 * "Country", customer .getFsCountryMasterByNationality().getCountryId()
	 * .toString(), "Date of Birth", customer .getDateOfBirth().toString(),
	 * "National ID Number", getIdNumber(), "Passport ID", "  ", fullName,
	 * fullNameLocal, "100061-E", "Test", status);
	 * 
	 * System.out.println("AML Status is:" + amlReturnStatus); } catch
	 * (Exception e) {
	 * 
	 * log.info("Aml Exception:" + e); } return amlReturnStatus; }
	 */
	/*
	 * public void getAMLCheckStatus_afterSave(BigDecimal customerId) {
	 * 
	 * List<Customer> customerInfoList = new ArrayList<Customer>(); String
	 * amlReturnStatus = null; String status = " ";
	 * 
	 * String civilIdNumber = null;
	 * 
	 * String customerRef = null;
	 * 
	 * try { FNSServicesLookupSoapClient finscanService = new
	 * FNSServicesLookupSoapClient();
	 * 
	 * customerInfoList = icustomerRegistrationService
	 * .getCustomerInfo(customerId);
	 * 
	 * if (customerInfoList.size() > 0) {
	 * 
	 * List<CustomerIdProof> idproofList = icustomerRegistrationService
	 * .getCivilID(customerInfoList.get(0) .getCustomerReference()); if
	 * (idproofList.size() > 0) { civilIdNumber =
	 * idproofList.get(0).getIdentityInt(); }
	 * 
	 * customerRef = customerInfoList.get(0).getCustomerReference() .toString()
	 * + "-E";
	 * 
	 * String fullName = customerInfoList.get(0).getFirstName() + " " +
	 * customerInfoList.get(0).getMiddleName() + " " +
	 * customerInfoList.get(0).getLastName(); String fullNameLocal =
	 * customerInfoList.get(0) .getFirstNameLocal() + " " +
	 * customerInfoList.get(0).getMiddleNameLocal() + " " +
	 * customerInfoList.get(0).getLastNameLocal();
	 * 
	 * amlReturnStatus = finscanService.amlServiceSearch("Individual",
	 * customerInfoList.get(0).getGender(), "Active", "Yes", "Yes", "Yes", " ",
	 * " ", " ", " ", " ", " ", " ", "Full Source", "Yes", "Yes", "WC",
	 * "World-Check", "  ", "  ", "Country", customerInfoList.get(0)
	 * .getFsCountryMasterByNationality() .getCountryId().toString(),
	 * "Date of Birth", customerInfoList.get(0).getDateOfBirth().toString(),
	 * "National ID Number", civilIdNumber, "Passport ID", "  ", fullName,
	 * fullNameLocal, customerRef, "AMIEC", status);
	 * 
	 * }
	 * 
	 * System.out.println("AML Status is:" + amlReturnStatus); } catch
	 * (Exception e) {
	 * 
	 * log.info("Aml Exception:" + e); } }
	 */
	public void getintrudecedby() {
		if (getIntroducedBy() != null && !getIntroducedBy().equalsIgnoreCase("")) {
			// customerReferencaList =
			// icustomerRegistrationService.getCustomerId(sessionStateManage.getCountryId(),getIntroducedBy());
			HashMap<String, String> mapIntroducedBy = icustomerRegistrationService.checkIntroducedByActive(sessionStateManage.getCountryId(),
					getIntroducedBy());

			if (mapIntroducedBy.size() != 0) {

				String customerrefNumber = mapIntroducedBy.get("CustomerReference");
				String customerActive = mapIntroducedBy.get("CustomerActive");

				// customerIntroducedList =
				// icustomerRegistrationService.getCustomerInfo(customerReferencaList.get(0).getFsCustomer().getCustomerId());

				if (customerActive.equals("Y")) {
					setCustRefId(new BigDecimal(customerrefNumber));
					log.info(getIntroducedBy());
					log.info(getIdNumber());

					if (getIntroducedBy() != null && getIdNumber() != null && getIntroducedBy().equals(getIdNumber())) {
						setIntroducedBy(null);
						RequestContext.getCurrentInstance().execute("duplicate.show();");
					}

				} else {
					setIntroducedBy(null);
					RequestContext.getCurrentInstance().execute("customernotactive.show();");
				}

			} else {
				setIntroducedBy(null);
				RequestContext.getCurrentInstance().execute("civilexists.show();");
			}
		}
	}

	/*
	 * method is responsible foe Clear Remitter Information
	 */
	public void clearPersonalInfo() {

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
		setMobileTemp("");
		setEmail("");
		setAlternativeEmail("");
		setDob(null);
		setAMLStatus("");
		setNumberofhits(null);
		setDigitalSignature(null);
		setIntroducedBy(null);
		setMedicalInsuranceInd(null);
		setPepsindicator(null);
		// setMobileNoFetch(null);
		setTokenNumber(null);
		// setRemarks(null);
		// setBoolRemarksforStoM(false);
		setBooMobileForOTP(false);
	}

	public void clearFirst() {

		setIdType(null);
		setIdNumber(null);
		// setSelectType(null);
		setBooIdTypeCheck(false);
		setIsActive(null);

	}

	public String idInfo() throws IOException {
		setBooIdDetail(true);
		setBooIdTypeCheck(false);
		clearPersonalInfo();
		clearContactDetail();
		clearEmploymentInfo();
		setRenderverication(false);
		setRemarks(null);
		setBoolRemarksforStoM(false);
		// FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationmain.xhtml");

		return "customerMainPage";
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void resetValues() throws IOException {
		setIsFromWesternUnion(false);
		setIsfromCorporatePage(false);
		setIsFromCustomer(true);
		setBooIdDetail(true);
		setBooManual(false);
		clearFirst();
		clearPersonalInfo();
		clearEmploymentInfo();
		clearSponsor();
		clearProofInfo();
		setBooIdDetail(true);
		setRenderverication(false);
		setSelectType(null);
		setMinagevalidation(false);
		setSmartCardInd(null);
		setRemarks(null);
		setBoolRemarksforStoM(false);
		setSmartcardcheck(false);

		idTypeValues = icustomerRegistrationService.getAllComponentComboDataForCustomer(sessionStateManage.getLanguageId(), Constants.Individual,
				Constants.COMPANYIDTYPE);
		setIdTypeValues(idTypeValues);
		loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "customerregistrationmain.xhtml");
		FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationmain.xhtml");
		// return "customerMainPage";
	}

	public void generateDob() throws ParseException {
		/** Date of birth manipulation from CIVIL ID */
		String id = getIdNumber();
		String dob = null;
		if (id.charAt(0) == '2') {
			dob = id.substring(5, 7) + "/" + id.substring(3, 5) + "/19" + id.substring(1, 3);
		} else {
			dob = id.substring(5, 7) + "/" + id.substring(3, 5) + "/20" + id.substring(1, 3);
		}
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = formatter.parse(dob);
		setDob(date);
		setBooCivilId(true);
		setBooOtherId(false);
		setChangeDobPass(true);
		setShowDob(formatter.format(date));
	}

	public void getIDTypeValue(AjaxBehaviorEvent event) {
		setSelectedIdType(getIdType());
		setBooIdTypeCheck(false);
	}

	public void checkValidCivilId() {

		mapIdentityType = ruleEngine.getComponentData("Identity Type");
		BigDecimal identityTpeId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
				identityTpeId = entry.getKey();
				break;
			}
		}

		if (getSelectedIdType() != null && getSelectedIdType().equalsIgnoreCase(identityTpeId.toPlainString())) {

			boolean status = false;
			try {
				String returnString = generalService.getCivilIdStatus(new BigDecimal(getIdNumber()));
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

	public void changeGender() {
		changeLocalTitle();

		Map<BigDecimal, String> mapGender = ruleEngine.getComponentData("Gender");
		Map<BigDecimal, String> mapTitle = ruleEngine.getComponentData("Title");

		System.out.println(mapGender);
		System.out.println(mapTitle);

		if (mapTitle.get(new BigDecimal(title)) != null) {
			if (mapTitle.get(new BigDecimal(title)).trim().equalsIgnoreCase("Mr.")) {
				for (BigDecimal key : mapGender.keySet()) {
					String value = mapGender.get(key);
					if (mapGender.get(key).equalsIgnoreCase("male")) {
						gender = key.toPlainString();
						gender = value;
					}
				}
			} else if (mapTitle.get(new BigDecimal(title)).trim().equalsIgnoreCase("Ms.")) {
				for (BigDecimal key : mapGender.keySet()) {
					String value = mapGender.get(key);
					if (mapGender.get(key).equalsIgnoreCase("female")) {
						gender = key.toPlainString();
						gender = value;
					}
				}
			} else if (mapTitle.get(new BigDecimal(title)).trim().equalsIgnoreCase("M/s")) {

				gender = "";
			} else {
				gender = "";
			}
		} else {
			gender = "";
		}

	}

	public void changeLocalTitle() {

		Map<BigDecimal, String> mapLocalTitle = ruleEngine.getComponentData("Local Title");
		Map<BigDecimal, String> mapTitle = ruleEngine.getComponentData("Title");

		System.out.println(mapLocalTitle);
		System.out.println(mapTitle);

		if (mapTitle.get(new BigDecimal(title)) != null) {
			if (mapTitle.get(new BigDecimal(title)).trim().equalsIgnoreCase("Mr.")) {
				for (Entry<BigDecimal, String> eleEntry : mapLocalTitle.entrySet()) {
					if (eleEntry
							.getKey()
							.toString()
							.equalsIgnoreCase(
									generalService.getComponentId(Constants.LOCAL_TITLE_FOR_MR, sessionStateManage.getLanguageId())
									.getFsBizComponentData().getComponentDataId().toString())) {
						String value = eleEntry.getValue();

						titlel = value;

					}
				}
			} else if (mapTitle.get(new BigDecimal(title)).trim().equalsIgnoreCase("Ms.")) {
				for (Entry<BigDecimal, String> eleEntry : mapLocalTitle.entrySet()) {
					if (eleEntry
							.getKey()
							.toString()
							.equalsIgnoreCase(
									generalService.getComponentId(Constants.LOCAL_TITLE_FOR_MRS, sessionStateManage.getLanguageId())
									.getFsBizComponentData().getComponentDataId().toString())) {
						String value = eleEntry.getValue();
						titlel = value;

					}
				}
			}
		}
	}

	public void onblurId(AjaxBehaviorEvent event) {

		if (getIdType() != null) {
			if (sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {

				mapIdentityType = ruleEngine.getComponentData("Identity Type");
				BigDecimal identityTpeId = null;
				for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
					if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
						identityTpeId = entry.getKey();
						break;
					}
				}

				BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId())
						.getFsBizComponentData().getComponentDataId();

				if (getSelectedIdType() != null && getSelectedIdType().equalsIgnoreCase(identityTpeId.toPlainString())
						|| getSelectedIdType().equalsIgnoreCase(idtypeCivilIdnew.toPlainString())) {

					boolean status = false;
					try {
						String returnString = generalService.getCivilIdStatus(new BigDecimal(getIdNumber()));
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

	public void enableMobileNo() {
		setMsgMobileNoFetch(false);

		if (getBooCheckMobile()) {
			setBooMobileHide(true);
			setBooIdType(false);
			setIdType(null);
			setIdNumber(null);
			setBooIdTypeCheck(false);
		} else {
			setBooMobileHide(false);
			setBooIdType(true);
			setMobileNoFetch(null);
			setMsgMobileNoFetch(false);
		}

	}

	public String fetcDataThroughMobileNo() {
		clearPersonalInfo();
		clearProofInfo();
		setCustomerIDProofId(null);
		createProofList.clear();
		dummiCustomerIdProofList.clear();
		setMobileFetch(false);

		returnString = " ";
		List<Customer> customerList = icustomerRegistrationService.getCustomerData(sessionStateManage.getCountryId(), getMobileNoFetch());
		if (customerList.size() == 1) {
			// if
			// (customerList.get(0).getSmartCardIndicator().equalsIgnoreCase(Constants.Yes))
			// {
			// RequestContext.getCurrentInstance().execute("dlgSmartcard.show();");
			// } else {
			setMobileFetch(true);
			getFetchContactTypeList();
			setPkCustomerId(customerList.get(0).getCustomerId());
			populateNationality();
			fetchCustomerPersonalInformation();
			setCustomerIdProof();
			setBooOtherId(false);
			setBooCivilId(true);
			setBooManual(true);
			setBooIdDetail(true);
			if (customerList.get(0).getIsActive() != null && customerList.get(0).getIsActive().equalsIgnoreCase(Constants.D)) {

				RequestContext.getCurrentInstance().execute("customeralreadydeactive.show();");
				return "";
			} else {
				// FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customermanualinfo.xhtml");
				String isActive = getActiveCustomerCheckFromView(sessionStateManage.getCountryId(), getPkCustomerId());
				if (isActive != null && isActive.equalsIgnoreCase("Y")) {
					RequestContext.getCurrentInstance().execute("actCustCheck.show();");
					return "";
				} else {
					returnString = "customerManualPage";
				}
			}

			// }
		} else if (customerList.size() > 1) {
			RequestContext.getCurrentInstance().execute("multiplerecord.show();");
			return "";
		} else {
			setMsgMobileNoFetch(true);
		}

		return returnString;
	}

	// added for mobile format validation

	public void validateMobile(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		String returnString = generalService.validateMobileTelephone(sessionStateManage.getCountryAlphaTwoCode(), value.toString(),
				Constants.MOBILE_CONTACT);
		if (returnString.equalsIgnoreCase(Constants.Yes)) {

		} else {

			FacesMessage msg = new FacesMessage("Mobile", returnString);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

		}

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

	// ------------------------X------------------Verification
	// Online---------------------------------X---------------------------

	public String clickOnOK() throws ParseException, IOException {

		clearContactDetail();
		clearEmploymentInfo();
		setBooCivilId(false);
		setBooOtherId(false);
		setChangeDobPass(false);
		clearProofInfo();
		setIdNumberverification(getIdNumber());
		setBooIdDetail(false);
		setRenderverication(true);
		return "customerMainPage";

	}

	public void clickRegistration() throws IOException, ParseException {

		setBooIdTypeCheck(false);

		setChangeDobPass(true);
		setBooOtherId(false);
		setBooCivilId(true);
		fetchCustomerPersonalInformation();
		setCustomerIdProof();
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../registration/customermanualinfo.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
		}
	}

	// This method responsible for verification who registered online

	public void fetchCustomerInfoVerification() {

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
		try {
			customerIdProofList = branchpageService.getCustomerIdProof(getIdNumberverification());
			if (customerIdProofList.size() > 0) {

				getFetchContactTypeList();
				customerList = branchpageService.getCustomerInfoWithToken(customerIdProofList.get(0).getFsCustomer().getCustomerId(),
						getTokenNumber());
				if (customerList.size() > 0) {
					setPkCustomerId(customerIdProofList.get(0).getFsCustomer().getCustomerId());

					setBooCivilId(true);
					setBooOtherId(false);
					setChangeDobPass(true);
					fetchCustomerPersonalInformation();
					setCustomerIdProof();
					try {
						ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
						context.redirect("../registration/customermanualinfo.xhtml");
					} catch (Exception e) {
						log.info("Problem to redirect: " + e);
					}
				} else {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("notMatched.show();");
				}
			} else {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("notMatched.show();");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ------------------------X------------------Verification
	// Online---------------------------------X---------------------------

	@SuppressWarnings("unchecked")
	public void saveCustomerScanDetails() throws AMGException, ParseException {

		if (createProofList.size() != 0) {

			CreateProofTable maxCreateProofTable = Collections.max(createProofList, new CustomerIdProoofComp());

			for (CreateProofTable createProofTable : createProofList) {

				CustomerIdProof custProof = new CustomerIdProof();

				Customer customer = new Customer();
				customer.setCustomerId(getPkCustomerId());
				custProof.setFsCustomer(customer);

				LanguageType languageType = new LanguageType();
				languageType.setLanguageId(sessionStateManage.getLanguageId());
				custProof.setFsLanguageType(languageType);

				BizComponentData customerType = new BizComponentData();
				customerType.setComponentDataId(generalService.getComponentId(Constants.CUSTOMERTYPE_INDU, sessionStateManage.getLanguageId())
						.getFsBizComponentData().getComponentDataId());
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

				custProof.setScanSystem(createProofTable.getScanSystem());

				if (createProofTable.getScanReq() != null && createProofTable.getScanReq().equalsIgnoreCase(Constants.Yes)
						&& createProofTable.getIsActive() != null && createProofTable.getIsActive().equalsIgnoreCase(Constants.Yes)) {
					custProof.setIdentityStatus(Constants.D);
				} else {

					if (createProofTable.getExpiaryDate() != null && createProofTable.getExpiaryDate().compareTo(maxCreateProofTable.getExpiaryDate()) == 0) {
						custProof.setIdentityStatus(Constants.CUST_ACTIVE_INDICATOR);
					} else {
						custProof.setIdentityStatus(Constants.D);
					}

					custProof.setScanReq(createProofTable.getScanReq());
				}

				if (createProofTable.getDate_expiary() != null) {
					Date expDate = new SimpleDateFormat("dd/MM/yyyy").parse(createProofTable.getDate_expiary());
					custProof.setIdentityExpiryDate(expDate);
				}
				//  Id Expiry Date check - 07/09/2016
				else
				{
					log.error("Id Expiry Date could not be null");
					setErrorMsg("Id Expiry Date could not be null");
					RequestContext.getCurrentInstance().execute("saveerror.show();");
				}

				if (createProofTable.getCustomerIdProofId() != null) {
					custProof.setUpdatedBy(sessionStateManage.getUserName());
					custProof.setLastUpdatedDate(getCurrentTime());
					custProof.setCreatedBy(createProofTable.getCreatedByIdProof());
					custProof.setCreationDate(createProofTable.getCreatedDateIdProof());
					custProof.setCustProofId(createProofTable.getCustomerIdProofId());
				} else {
					custProof.setCreatedBy(sessionStateManage.getUserName());
					custProof.setCreationDate(getCurrentTime());
				}

				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

				if (createProofTable.getCheckedScanned()) {

					BigDecimal blobd = icustomerRegistrationService.callTogenerateBlobID(new BigDecimal(getDealYear()));
					HashMap<String, String> mapProcedureData = new HashMap<String, String>();
					mapProcedureData.put("BLOBID", blobd.toString());
					mapProcedureData.put("FINYEAR", getDealYear());
					mapProcedureData.put("IDNO", createProofTable.getId_number());
					mapProcedureData.put("DOCTYPE", createProofTable.getId_type());
					mapProcedureData.put("IDDESC", createProofTable.getIdType());
					icustomerRegistrationService.callScanTableInsert(mapProcedureData,
							new SimpleDateFormat("dd/MM/yyyy").parse(createProofTable.getDate_expiary()));
					saveDMSApplMap(createProofTable, blobd);
					saveDMSDocBlobTemp(createProofTable, blobd);

				}
				generalService.saveOrUpdate((T) custProof);
			}
		}

	}

	// added for Db scan

	public void saveIdproofPartial() {

		try {
			if (createProofList.size() == 0) {
				CustomerIdProof custProof = new CustomerIdProof();
				custProof.setCustProofId(getCustomerIDProofId());

				Customer customer = new Customer();
				customer.setCustomerId(getPkCustomerId());
				custProof.setFsCustomer(customer);

				LanguageType languageType = new LanguageType();
				languageType.setLanguageId(sessionStateManage.getLanguageId());
				custProof.setFsLanguageType(languageType);

				BizComponentData customerType = new BizComponentData();
				customerType.setComponentDataId(generalService.getComponentId(Constants.CUSTOMERTYPE_INDU, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
				custProof.setFsBizComponentDataByCustomerTypeId(customerType);

				BizComponentData idType = new BizComponentData();
				idType.setComponentDataId(new BigDecimal(getIdType()));
				custProof.setFsBizComponentDataByIdentityTypeId(idType);

				custProof.setIdentityInt(getIdNumber());
				custProof.setIdentityStatus(Constants.CUST_ACTIVE_INDICATOR);
				custProof.setCreatedBy(sessionStateManage.getUserName());
				custProof.setCreationDate(getCurrentTime());

				generalService.saveOrUpdate((T) custProof);
			}
		} catch (Exception e) {
			log.error("Error saving in Id Proof: " + e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}

	}


	//Added by Rabil on 12 Oct 2016 saving customer and  customer ID proof at a time otherwise roll back both

	public CustomerIdProof saveIdproofPartialReturnObject(){

		try {
			CustomerIdProof custProof =null;
			if (createProofList.size() == 0) {
				custProof = new CustomerIdProof();
				custProof.setCustProofId(getCustomerIDProofId());

				Customer customer = new Customer();
				customer.setCustomerId(getPkCustomerId());
				custProof.setFsCustomer(customer);

				LanguageType languageType = new LanguageType();
				languageType.setLanguageId(sessionStateManage.getLanguageId());
				custProof.setFsLanguageType(languageType);

				BizComponentData customerType = new BizComponentData();
				customerType.setComponentDataId(generalService.getComponentId(Constants.CUSTOMERTYPE_INDU, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
				custProof.setFsBizComponentDataByCustomerTypeId(customerType);

				BizComponentData idType = new BizComponentData();
				idType.setComponentDataId(new BigDecimal(getIdType()));
				custProof.setFsBizComponentDataByIdentityTypeId(idType);

				custProof.setIdentityInt(getIdNumber());
				custProof.setIdentityStatus(Constants.CUST_ACTIVE_INDICATOR);
				custProof.setCreatedBy(sessionStateManage.getUserName());
				custProof.setCreationDate(getCurrentTime());

				//generalService.saveOrUpdate((T) custProof);

			}
			return custProof;
		} catch (Exception e) {
			log.error("Error saving in Id Proof: " + e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
			return null;
		}

	}
	
	//save customer mobile number in fs_customer_mobile_log
	public CustomerMobileLogModel saveCustomerMobileDetails(){
		
		CustomerMobileLogModel customerMobileLog = new CustomerMobileLogModel();
		
		customerMobileLog.setCreatedBy(sessionStateManage.getUserName());
		customerMobileLog.setCreatedDate(new Date());
		customerMobileLog.setIsActive(Constants.Yes);
		customerMobileLog.setMobile(getMobile());
		//customerMobileLog.setOtpNo();
		//customerMobileLog.setOtpRetry();
		//customerMobileLog.setOtpRetryDate();
		//customerMobileLog.setOtpVerifiedBy();
		//customerMobileLog.setOtpVerifiedDate();
		
		return customerMobileLog;
	}



	public void setCustomerIdProof() {
		setCustomerIDProofId(null);
		CreateProofTable createProofTable = null;
		customerIdProofList.clear();
		customerIdProofList = icustomerRegistrationService.getCustomerIdProofList(getPkCustomerId());

		getFetchContactTypeList();
		createProofList.clear();
		dummiCustomerIdProofList.clear();

		for (CustomerIdProof customerIdProof : customerIdProofList) {

			createProofTable = new CreateProofTable();

			createProofTable.setCustomerIdProofId(customerIdProof.getCustProofId());

			if (customerIdProof.getFsBizComponentDataByIdentityFor() != null) {
				createProofTable.setIdFor(idForMap.get(customerIdProof.getFsBizComponentDataByIdentityFor().getComponentDataId()));
				createProofTable.setId_for(customerIdProof.getFsBizComponentDataByIdentityFor().getComponentDataId().toPlainString());
			}

			createProofTable.setId_number(customerIdProof.getIdentityInt());
			createProofTable.setIdType(idTypeMap.get(customerIdProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId()));
			if (customerIdProof.getIdentityExpiryDate() != null) {
				createProofTable.setDate_expiary(new SimpleDateFormat(DATE_FORMAT).format(customerIdProof.getIdentityExpiryDate()));
			}
			createProofTable.setId_type(customerIdProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId().toPlainString());
			if (getMobileFetch()) {
				setIdType(customerIdProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId().toPlainString());
				setIdNumber(customerIdProof.getIdentityInt());
			}
			// createProofTable.setCustomerIdProofId(customerIdProof.getCustProofId());
			createProofTable.setCreatedByIdProof(customerIdProof.getCreatedBy());
			createProofTable.setCreatedDateIdProof(customerIdProof.getCreationDate());
			createProofTable.setCheckedScanned(false);

			if (customerIdProof.getIdentityExpiryDate() != null) {
				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
				try {
					if (dateformat.parse(dateformat.format(customerIdProof.getIdentityExpiryDate())).compareTo(
							dateformat.parse(dateformat.format(new Date()))) >= 0) {
						List<CountryBranch> listCountryBranch = generalService.getBranchDetailsFromBeneStatus(sessionStateManage.getCountryId(),
								new BigDecimal(sessionStateManage.getBranchId()));
						if (listCountryBranch.size() > 0 && listCountryBranch.get(0).getScanInd() != null
								&& listCountryBranch.get(0).getScanInd().equalsIgnoreCase(Constants.ScanInd_A)) {
							createProofTable.setRenderReScan(true);
						} else {
							createProofTable.setRenderReScan(false);
						}
					} else {
						createProofTable.setRenderReScan(false);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (customerIdProof.getIdentityExpiryDate() != null && customerIdProof.getScanReq() != null
						&& customerIdProof.getScanReq().equalsIgnoreCase(Constants.Scan_Req_Y)) {
					createProofTable.setChecked(false);
					createProofTable.setBooCheckDup(true);
					// setBooCheckDup(false);
				} else {
					createProofTable.setBooCheckDup(false);
				}

				if (customerIdProof.getScanReq() != null) {
					createProofTable.setScanReq(customerIdProof.getScanReq());
				}
				if (customerIdProof.getIdentityStatus() != null) {
					createProofTable.setIsActive(customerIdProof.getIdentityStatus());
				}
				if (customerIdProof.getScanSystem() != null) {
					createProofTable.setScanSystem(customerIdProof.getScanSystem());

					if (customerIdProof.getScanSystem().equalsIgnoreCase(Constants.ScanInd_A)) {
						createProofTable.setBooRenderAImageView(true);
						createProofTable.setBooRenderDImageView(false);

					} else if (customerIdProof.getScanSystem().equalsIgnoreCase(Constants.ScanInd_N)) {
						createProofTable.setBooRenderAImageView(false);
						createProofTable.setBooRenderDImageView(false);
						createProofTable.setRenderReScan(true);

					} else if (customerIdProof.getScanSystem().equalsIgnoreCase(Constants.ScanInd_D)) {
						createProofTable.setBooRenderAImageView(false);
						createProofTable.setBooRenderDImageView(true);
					}

				} else {
					createProofTable.setBooRenderAImageView(true);
					createProofTable.setBooRenderDImageView(false);
				}
				createProofTable.setExistRecord(Constants.Old_Record);
				createProofTable.setExpiaryDate(customerIdProof.getIdentityExpiryDate());
				createProofList.add(createProofTable);

			} else {
				dummiCustomerIdProofList.add(createProofTable);
			}

		}

		if (dummiCustomerIdProofList.size() != 0) {
			for (CreateProofTable customerIdProof : dummiCustomerIdProofList) {
				setCustomerIDProofId(customerIdProof.getCustomerIdProofId());
			}
		} else {
			setCustomerIDProofId(null);
		}

	}

	public void clear() {

		clearFirst();
		clearPersonalInfo();
		clearContactDetail();
		clearEmploymentInfo();
		// clearProofInfo();
		clearSmartCardInfo();

	}

	public void clearSmartCardInfo() {
		setSmartCardId(null);
		setCivilId(null);
		setSerialno(null);
		setDocumentno(null);
		setFullNameEn(null);
		setFullNameAr(null);
		setFirstNameEn("");
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
		setArabicSurnameLocal(null);
		setFirstNameArabic(null);
		setMiddleNameArabic(null);
		setLastNameArabic(null);
		setLastNameArabic(null);
		setMiddleNameEn(null);
		setLastNameEn(null);
		setShortNameEn(null);
		setKioskPin(null);
		setBooTitleKwt(false);
		setBooTitleOman(false);
	}

	// ------------------------------------X-------------------SMART
	// CARD--------------------------X------------------------------------------------------

	public String fetchSmartCardData() throws ParseException, DOMException, ParserConfigurationException, SAXException {

		clear();
		clearProofInfo();
		setDateExp(null);
		setRenderRescan(false);
		setSmartcardcheck(true);
		createProofList.clear();
		dummiCustomerIdProofList.clear();
		setErrorMsg(null);

		returnString = " ";

		if (sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
			smartCardDisplay(ipAddress, "8085", "M", "test");
			if (getCivilId() != null) {

				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

				Calendar cal1 = Calendar.getInstance();
				cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
				Date today18 = cal1.getTime();
				if (dateformat.parse(getBirthdate()).before(today18)) {

					if (getExpirydate() != null && dateformat.parse(getExpirydate()).compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {

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

							if (outprocedurevalues.size() != 0) {
								setAllow_Ind(outprocedurevalues.get(0));
								setCustRefNo((outprocedurevalues.get(1)) == null ? "" : (outprocedurevalues.get(1)));

								if (getAllow_Ind().equalsIgnoreCase(Constants.Yes)) {
									try {

										smartCardInfoList = icustomerRegistrationService.getSmartCardData(getCivilId());

										if (smartCardInfoList.size() > 0) {

											setCivilId(smartCardInfoList.get(0).getIdno());
											// setSerialno(smartCardInfoList.get(0).getSerialNo());
											// setDocumentno(smartCardInfoList.get(0).getDocumentNo());
											// setFullNameEn(smartCardInfoList.get(0).getEFuname());
											// setFullNameAr(smartCardInfoList.get(0).getLFuname());
											// setFirstNameEn(smartCardInfoList.get(0).getEFirstName());
											// setFirstNameAr(smartCardInfoList.get(0).getLFstName());
											// setArabicSurnameLocal(smartCardInfoList.get(0).getLSurname());
											// setFatherNameArabic(smartCardInfoList.get(0).getLFatherName());
											// setArabicGFName(smartCardInfoList.get(0).getLGfName());
											// setNationalityLatin(smartCardInfoList.get(0).getNtcdId());
											// setNationalityAr(smartCardInfoList.get(0).getNationalityAr());
											// setGenderLatin(smartCardInfoList.get(0).getGender());

											if (smartCardInfoList.get(0).getGender().equalsIgnoreCase("F")) {
												setBooTitleKwt(true);
												setBooTitleOman(false);
												setTitle(generalService
														.getComponentId(Constants.TITLE_FOR_MRS_NAME, sessionStateManage.getLanguageId())
														.getFsBizComponentData().getComponentDataId().toString());
												setTitleEn(Constants.TITLE_FOR_MRS_NAME);
												setTitlel(Constants.LOCAL_TITLE_FOR_MRS);
												if (sessionStateManage.getLanguageId().toString().equalsIgnoreCase(Constants.ENGLISH_LANGUAGE_ID)) {
													setGender(Constants.GENDER_FEMALE);
												} else {
													setGender(Constants.GENDER_FEMALE_ARABIC);
												}
											} else if(smartCardInfoList.get(0).getGender().equalsIgnoreCase("M")) {
												setBooTitleKwt(true);
												setBooTitleOman(false);
												setTitle(generalService
														.getComponentId(Constants.TITLE_FOR_MR_NAME, sessionStateManage.getLanguageId())
														.getFsBizComponentData().getComponentDataId().toString());
												setTitleEn(Constants.TITLE_FOR_MR_NAME);
												setTitlel(Constants.LOCAL_TITLE_FOR_MR);
												if (sessionStateManage.getLanguageId().toString().equalsIgnoreCase(Constants.ENGLISH_LANGUAGE_ID)) {
													setGender(Constants.GENDER_MALE);
												} else {
													setGender(Constants.GENDER_MALE_ARABIC);
												}
											}else {
												setTitle(null);
												setGender(null);
												setBooTitleKwt(true);
												setBooTitleOman(false);
											}

											// setBirthdate(new
											// SimpleDateFormat("dd/MM/yyyy").format(smartCardInfoList.get(0).getBirthdat()));
											// setTelephone1(smartCardInfoList.get(0).getTel1());
											setTelephone2(smartCardInfoList.get(0).getTel2());
											// setScardemail(smartCardInfoList.get(0).getEmailId());
											setBloodType(smartCardInfoList.get(0).getBloodTyp());
											setBuilding_no(smartCardInfoList.get(0).getBldgno());


											setScardfloor(smartCardInfoList.get(0).getFloorno());
											// setScardstreet(smartCardInfoList.get(0).getStreet());
											// setScarddistrict(smartCardInfoList.get(0).getDistrict());
											setsCardblock(smartCardInfoList.get(0).getBlockNo());
											setGuardianCivilId(smartCardInfoList.get(0).getGuardIdno());

											setAddressRef(smartCardInfoList.get(0).getAddrUniqKey());

											setCreatedByCustomer(smartCardInfoList.get(0).getCreator());
											setCreationDateCustomer(smartCardInfoList.get(0).getCrtdat());
											setSmartCardId(smartCardInfoList.get(0).getDmsIdMasId());
										}

										BigDecimal idtypeCivilIdnew = generalService
												.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId()).getFsBizComponentData()
												.getComponentDataId();
										if (getIdType() != null && idtypeCivilIdnew != null && getIdType().equalsIgnoreCase(identityTpeId.toString())
												|| getIdType().equalsIgnoreCase(idtypeCivilIdnew.toString())) {
											customerIdProofList = icustomerRegistrationService.getCustomerIdProofCheck(identityTpeId, getIdNumber(),
													sessionStateManage.getCountryId());

											if (customerIdProofList.size() == 0) {
												customerIdProofList = icustomerRegistrationService.getCustomerIdProofCheck(idtypeCivilIdnew,
														getIdNumber(), sessionStateManage.getCountryId());
											}

										}

										if (customerIdProofList.size() > 0) {

											if (customerIdProofList.get(0).getFsCustomer().getIsActive() != null
													&& customerIdProofList.get(0).getFsCustomer().getIsActive().equalsIgnoreCase(Constants.D)) {

												RequestContext.getCurrentInstance().execute("customeralreadydeactive.show();");
												// FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationmain.xhtml");
												return "";
											} else {

												setPkCustomerId(customerIdProofList.get(0).getFsCustomer().getCustomerId());

												getNationalityAlphacode();
												fetchSmartcardDataFromDab();
												setCustomerIdProof();
												setIdTypeproof(identityTpeId);
												setIdType(identityTpeId.toString());
												setDateExp(new SimpleDateFormat("dd/MM/yyyy").parse(getExpirydate()));
												setIdnumberProof(getCivilId());
												setReadOnlyIdNumber(true);
											}

										} else {
											getFetchContactTypeList();
											getNationalityAlphacode();
											setChangeDobPass(true);
											setIdTypeproof(identityTpeId);
											setIdType(identityTpeId.toString());
											setDateExp(new SimpleDateFormat("dd/MM/yyyy").parse(getExpirydate()));
											setIdnumberProof(getCivilId());
											setReadOnlyIdNumber(true);
										}

										BigDecimal nameLenght = icustomerRegistrationService.fetchNamelength(getNationality());
										String nameConcat = getFirstNameEn().trim() + " " + getMiddleNameEn().trim() + " " + getLastNameEn().trim();
										String nameConcatLocal = getFirstNameAr().trim() + " " + getMiddleNameArabic().trim() + " "
												+ getLastNameArabic().trim();
										countWordEnglish(nameConcat);
										countWordLocal(nameConcatLocal);
										if (nameLenght.intValue() != 0 && getWordCountEn() < nameLenght.intValue()) {
											setRenderNameCardEn(false);
											setRenderNameCardEnEdit(true);
										}
										if (nameLenght.intValue() != 0 && getWordCountAr() < nameLenght.intValue()) {
											setRenderNameCardlocal(false);
											setRenderNameCardlocalEdit(true);
										}

										String isActive = null;
										if (customerIdProofList.size() > 0)
											isActive = getActiveCustomerCheckFromView(sessionStateManage.getCountryId(), customerIdProofList.get(0)
													.getFsCustomer().getCustomerId());
										// activeCustomerList =
										// generalService.getActiveCustomerFromView(sessionStateManage.getCountryId(),
										// customerIdProofList.get(0).getFsCustomer().getCustomerId());
										System.out.println("Rabil activeCustomerList :" + isActive);
										if (isActive != null && isActive.equalsIgnoreCase("Y")) {
											RequestContext.getCurrentInstance().execute("actCustCheck.show();");
										} else {
											FacesContext.getCurrentInstance().getExternalContext()
											.redirect("../registration/customersmartcardinfo.xhtml");
										}

									} catch (NullPointerException ne) {
										log.info("Null pointer exception occured" + ne);
										FacesContext.getCurrentInstance().getExternalContext()
										.redirect("../registration/customerregistrationmain.xhtml");
										RequestContext.getCurrentInstance().execute("exceptionfetching.show();");
									}
								} else {
									setBooManual(false);
									setBoosmartCardAppear(true);
									setBooIdDetail(true);
									setRenderverication(false);
									FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationmain.xhtml");
									RequestContext.getCurrentInstance().execute("printCustomerReference.show();");
								}

							}

						} catch (AMGException e) {
							log.info("SQL Exception: " + e);
							setErrorMsg(e.getMessage());
							RequestContext.getCurrentInstance().execute("sqlexception.show();");
						} catch (IOException e) {
							log.info("Exception: " + e);
							e.printStackTrace();
						}
					} else {
						RequestContext.getCurrentInstance().execute("idexpiredcard.show();");
						return "";
					}
				} else {
					RequestContext.getCurrentInstance().execute("dobcheck.show();");
				}
			}
		} else if (sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.BAHRAIN_ALPHA_TWO_CODE)) {
			// fetchBahrainSmartCardDetails();
		} else if (sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.OMAN_ALPHA_TWO_CODE)) {

			if(Constants.OMSMARTCARDENV.equalsIgnoreCase("TEST"))
			{
				try {
					//Delete record from EX_ID_TEMP	table for OMAN SMAR CARD 
					icustomerRegistrationService.deleteRecordBeforeSmarCardReader(sessionStateManage.getUserName());
					//Call client application for OMAN smart card reader
					callOMANSmartCardClientApp();
					//Fetch smart card details from EX_ID_TEMP(ViewOMIDTemp) table
					fetchOmanSmartCardDisplay();
					//Check and validate the smart card details
					processSmartCardOMANDetails();

				} catch (AMGException e) {
					log.info("SQL Exception: " + e);
					setErrorMsg(e.getMessage());
					RequestContext.getCurrentInstance().execute("sqlexception.show();");
				}catch (IOException e) {
					setErrorMsg(e.getMessage());
					RequestContext.getCurrentInstance().execute("sqlexception.show();");
				}catch (Exception e) {
					setErrorMsg(e.getMessage());
					RequestContext.getCurrentInstance().execute("sqlexception.show();");
				}
			}else if(Constants.OMSMARTCARDENV.equalsIgnoreCase("PRODUCTION"))
			{
				try {
					//Delete record from EX_ID_TEMP	table for OMAN SMAR CARD 
					icustomerRegistrationService.deleteRecordBeforeSmarCardReader(sessionStateManage.getUserName());
					//Call client application for OMAN smart card reader
					callOMANSmartCardClientApp();
					//Fetch smart card details from EX_ID_TEMP(ViewOMIDTemp) table
					fetchOmanSmartCardDisplay();
					//Check and validate the smart card details
					processSmartCardOMANDetails();

				} catch (AMGException e) {
					log.info("SQL Exception: " + e);
					setErrorMsg(e.getMessage());
					RequestContext.getCurrentInstance().execute("sqlexception.show();");
				}catch (IOException e) {
					setErrorMsg(e.getMessage());
					RequestContext.getCurrentInstance().execute("sqlexception.show();");
				}catch (Exception e) {
					setErrorMsg(e.getMessage());
					RequestContext.getCurrentInstance().execute("sqlexception.show();");
				}
			}


		}
		return returnString;

		// return returnString;
	}

	/**
	 * 
	 * @param countryId
	 * @param customerId
	 * @return :Active Customer Check
	 */
	public String getActiveCustomerCheckFromView(BigDecimal countryId, BigDecimal customerId) {
		System.out.println("");
		String isActive = null;
		// activeCustomerList.clear();
		List<ViewActiveCustomerCheck> activeCustomerList = null;
		log.info("getActiveCustomerCheckFromView countryId :" + countryId + "\t customerId :" + customerId);
		activeCustomerList = generalService.getActiveCustomerFromView(countryId, customerId);
		if (activeCustomerList != null && !activeCustomerList.isEmpty()) {
			isActive = activeCustomerList.get(0).getIsActive() == null ? "" : activeCustomerList.get(0).getIsActive();
		}
		System.out.println("isActive :" + isActive);
		return isActive;
	}

	public void needModification() {
		System.out.println("needModification :" + getSmartcardcheck() + "\t manual :" + getBooManual() + "\t Mobile :" + getBooCheckMobile());
		try {
			if (getSmartcardcheck()) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customersmartcardinfo.xhtml");
			} else if (getBooManual()) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customermanualinfo.xhtml");
			} else if (getBooCheckMobile()) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customermanualinfo.xhtml");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getNationalityAlphacode() {

		List<CountryMaster> countryAlphaList = icustomerRegistrationService.getNationalityAlphaCode(getNationalityLatin());

		for (CountryMaster countryList : countryAlphaList) {

			setNationality(countryList.getCountryId());
		}

	}

	public String smartCardDisplay(String host, String prdPort, String requestType, String env) throws ParseException {
		StringBuffer sb = new StringBuffer();
		StringBuffer urlBuffer = new StringBuffer();
		String appender = "?";
		String ampersand = "&";
		String equals = "=";
		String colon = ":";
		String rootContext = "/KwtSmartCard/SmartCartServ"; // KwtSmartCard/smartcard
		if (env.equalsIgnoreCase("test")) {
			urlBuffer.append("http://").append(host).append(colon).append(prdPort).append(rootContext).append(appender);
		} else if (env.equalsIgnoreCase("live")) {
			urlBuffer.append("https://").append(host);
			if (prdPort != null && prdPort.length() > 0) {
				urlBuffer.append(colon).append(prdPort);
			}
			urlBuffer.append(rootContext).append(appender);
		}
		urlBuffer.append("type").append(equals).append("M").append(ampersand);
		HttpURLConnection testyc = null;
		HttpsURLConnection prdyc = null;
		BufferedReader in = null;
		try {
			URL knetRequest = new URL(urlBuffer.toString());
			// HttpURLConnection testyc = null;
			// HttpsURLConnection prdyc = null;
			// BufferedReader in = null;
			if (env.equalsIgnoreCase("test")) {
				testyc = (HttpURLConnection) knetRequest.openConnection();
				if(testyc != null && testyc.getInputStream() != null){
					in = new BufferedReader(new InputStreamReader(testyc.getInputStream()));
				}

			} else if (env.equalsIgnoreCase("live")) {
				prdyc = (HttpsURLConnection) knetRequest.openConnection();
				in = new BufferedReader(new InputStreamReader(prdyc.getInputStream()));
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
		} finally {
			try {
				in.close();
				if (testyc != null) {
					testyc.disconnect();
				}
				if (prdyc != null) {
					prdyc.disconnect();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String[] str = sb.toString().split("#");

		if (str.length > 1) {

			for (int i = 0; i < str.length; i++) {
				String string = str[i];

				if (i == 0) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setFullNameAr(part2);
					setArabicSurnameLocal(part2);
					StringTokenizer tokenizer = new StringTokenizer(part2);
					String firstName = "";
					String middleName = "";
					StringBuilder lastName = new StringBuilder();

					int countToken = tokenizer.countTokens();
					for (int ii = 0; ii < countToken; ii++) {
						if (ii == 0) {
							firstName = tokenizer.nextToken();
							setFirstNameAr(firstName);
						} else if (ii == 1) {
							middleName = tokenizer.nextToken();
							setMiddleNameArabic(middleName);
							setFatherNameArabic(middleName);
						} else {
							// lastName.append(tokenizer.nextToken()).append(" ");

							if (lastName != null && lastName.length() > 0) {
								lastName.append(" ").append(tokenizer.nextToken());
							} else {
								lastName.append(tokenizer.nextToken().trim());
							}

						}

					}

					String lstNameCheck = lastName == null ? "" : lastName.toString();

					if (lstNameCheck.contains("\u00A0")) {
						String nameadd = lstNameCheck.replaceAll("\u00A0", "");
						setLastNameArabic(nameadd.trim());
						setArabicGFName(nameadd.trim());
					} else {
						setLastNameArabic(lastName == null ? "" : lastName.toString());
						setArabicGFName(lastName == null ? "" : lastName.toString());
					}

				}

				/*
				 * if (i == 1) { String[] parts = string.split("="); String
				 * part2 = parts[1]; setFirstNameAr(part2);
				 * 
				 * }
				 * 
				 * if (i == 2) { String[] parts = string.split("="); String
				 * part2 = parts[1]; setFatherNameArabic(part2);
				 * setMiddleNameArabic(part2);
				 * 
				 * }
				 * 
				 * if (i == 3) { String[] parts = string.split("="); String
				 * part2 = parts[1]; if (part2 != null) { if
				 * (part2.equalsIgnoreCase("\u00A0")) {
				 * setArabicGFName(part2.replaceAll("\u00A0", ""));
				 * setLastNameArabic(part2.replaceAll("\u00A0", "")); } else {
				 * setArabicGFName(part2); setLastNameArabic(part2); }
				 * 
				 * }
				 * 
				 * }
				 * 
				 * if (i == 4) { String[] parts = string.split("="); String
				 * part2 = parts[1]; if (part2 != null) { if
				 * (part2.equalsIgnoreCase("\u00A0")) {
				 * setArabicSurname(part2.replaceAll("\u00A0", "")); } else {
				 * setArabicSurname(part2);
				 * setLastNameArabic(getLastNameArabic()+" "+part2); }
				 * 
				 * }
				 * 
				 * 
				 * }
				 */

				if (i == 5) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setNationalityAr(part2);
				}

				if (i == 6) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setGenderAr(part2);

				}

				if (i == 7) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setFullNameEn(part2);

					StringTokenizer tokenizer = new StringTokenizer(part2);
					String firstName = "";
					String middleName = "";
					StringBuilder lastName = new StringBuilder();

					int countToken = tokenizer.countTokens();
					for (int ii = 0; ii < countToken; ii++) {
						if (ii == 0) {
							firstName = tokenizer.nextToken();
							setFirstNameEn(firstName);
						} else if (ii == 1) {
							middleName = tokenizer.nextToken();
							setMiddleNameEn(middleName);
						} else {
							// lastName.append(tokenizer.nextToken()).append(" ");
							if (lastName != null && lastName.length() > 0) {
								lastName.append(" ").append(tokenizer.nextToken());
							} else {
								lastName.append(tokenizer.nextToken().trim());
							}
						}

					}
					String lstNameCheck = lastName == null ? "" : lastName.toString();

					if (lstNameCheck.contains("\u00A0")) {
						String nameadd = lstNameCheck.replaceAll("\u00A0", "");
						setLastNameEn(nameadd.trim());
					} else {
						setLastNameEn(lastName == null ? "" : lastName.toString());
					}

				}

				if (i == 8) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setCivilId(part2);

				}

				if (i == 9) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					System.out.println(part2);
					setGenderLatin(part2);

					if (part2.equalsIgnoreCase("F")) {
						setTitle(generalService.getComponentId(Constants.TITLE_FOR_MRS_NAME, sessionStateManage.getLanguageId())
								.getFsBizComponentData().getComponentDataId().toString());
						setTitleEn(Constants.TITLE_FOR_MRS_NAME);
						setTitlel(Constants.LOCAL_TITLE_FOR_MRS);
						if (sessionStateManage.getLanguageId().toString().equalsIgnoreCase(Constants.ENGLISH_LANGUAGE_ID)) {
							setGender(Constants.GENDER_FEMALE);
						} else {
							setGender(Constants.GENDER_FEMALE_ARABIC);
						}
					} else {
						setTitle(generalService.getComponentId(Constants.TITLE_FOR_MR_NAME, sessionStateManage.getLanguageId())
								.getFsBizComponentData().getComponentDataId().toString());
						setTitleEn(Constants.TITLE_FOR_MR_NAME);
						setTitlel(Constants.LOCAL_TITLE_FOR_MR);
						if (sessionStateManage.getLanguageId().toString().equalsIgnoreCase(Constants.ENGLISH_LANGUAGE_ID)) {
							setGender(Constants.GENDER_MALE);
						} else {
							setGender(Constants.GENDER_MALE_ARABIC);
						}
					}

				}

				if (i == 10) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setNationalityLatin(part2);

				}

				if (i == 11) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					if (part2 != null && part2.matches("([0-9]{4})/([0-9]{2})/([0-9]{2})")) {
						Date birthDate = new SimpleDateFormat("yyyy/MM/dd").parse(part2);
						setBirthdate(new SimpleDateFormat("dd/MM/yyyy").format(birthDate));
					} else {

						String id = getCivilId();
						String dob = null;
						if (id.charAt(0) == '2') {
							dob = id.substring(5, 7) + "/" + id.substring(3, 5) + "/19" + id.substring(1, 3);
						} else {
							dob = id.substring(5, 7) + "/" + id.substring(3, 5) + "/20" + id.substring(1, 3);
						}
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						Date date = formatter.parse(dob);
						setBirthdate(formatter.format(date));
					}
				}

				if (i == 12) {

					String[] parts = string.split("=");
					String part2 = parts[1];
					if (part2 != null && part2.matches("([0-9]{4})/([0-9]{2})/([0-9]{2})")) {
						Date issueDate = new SimpleDateFormat("yyyy/MM/dd").parse(part2);
						setIssuedate(new SimpleDateFormat("dd/MM/yyyy").format(issueDate));
					}

				}
				if (i == 13) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					if (part2 != null && part2.matches("([0-9]{4})/([0-9]{2})/([0-9]{2})")) {
						Date expDate = new SimpleDateFormat("yyyy/MM/dd").parse(part2);
						setExpirydate(new SimpleDateFormat("dd/MM/yyyy").format(expDate));
					}

				}

				if (i == 14) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setDocumentno(part2);

				}

				if (i == 15) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setSerialno(part2);

				}

				if (i == 16) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setMoireference(part2);

				}

				if (i == 17) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					System.out.println("Moi Refere: " + part2);

				}

				if (i == 18) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setScarddistrict(part2);
				}

				if (i == 19) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					if (part2 != null) {
						setsCardblock(part2.trim());
					}

				}

				if (i == 20) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setScardstreet(part2);

				}

				if (i == 21) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setBuilding_no(part2);

				}

				if (i == 22) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setUnit_type(part2);

				}

				if (i == 23) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setUnit_no(part2);

				}

				if (i == 24) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setScardfloor(part2);

				}

				if (i == 25) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setBloodType(part2);

				}

				if (i == 26) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setGuardianCivilId(part2);

				}

				if (i == 27) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					// setTelephone1(part2);
				}

				if (i == 28) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setTelephone2(part2);
				}

				if (i == 29) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					if (part2 != null) {
						if (part2.equalsIgnoreCase("\u00A0")) {

							setScardemail(part2.replaceAll("\u00A0", ""));
						} else {
							setScardemail(part2.trim());
						}
					}

				}

				if (i == 30) {

					String[] parts = string.split("=");
					String part2 = parts[1];

					String[] articleDetails = new String[0];
					String[] aricleid = new String[0];

					if (part2 != null) {
						if (part2.equalsIgnoreCase("\u00A0")) {
							setAdditinal1(part2.replaceAll("\u00A0", ""));
						} else {
							articleDetails = part2.split("/");
							aricleid = articleDetails[1].split(" ");
							setAdditinal1(aricleid[2]);
						}
					}

					getArticleDataFromCard();

				}

				if (i == 31) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					if (part2 != null) {
						if (part2.equalsIgnoreCase("\u00A0")) {
							setAdditinal2(part2.replaceAll("\u00A0", ""));
						} else {
							setEmployer(part2);
							setAdditinal2(part2);
						}
					}
				}

				if (i == 32) {
					String[] parts = string.split("=");
					String part2 = parts[1];
					setAddressRef(part2);
				}

				/*
				 * if (i == 33) { String[] parts = string.split("="); String
				 * part2 = parts[1]; if (part2 != null) { if
				 * (part2.equalsIgnoreCase("\u00A0")) {
				 * setFirstNameEn(part2.replaceAll("\u00A0", "")); } else {
				 * setFirstNameEn(part2); }
				 * 
				 * } }
				 * 
				 * if (i == 34) { String[] parts = string.split("="); String
				 * part2 = parts[1]; if (part2 != null) { if
				 * (part2.equalsIgnoreCase("\u00A0")) {
				 * setMiddleNameEn(part2.replaceAll("\u00A0", "")); } else {
				 * setMiddleNameEn(part2); } }
				 * 
				 * }
				 * 
				 * if (i == 35) { String[] parts = string.split("="); String
				 * part2 = parts[1]; if (part2 != null) { if
				 * (part2.equalsIgnoreCase("\u00A0")) {
				 * setLastNameEn(part2.replaceAll("\u00A0", "")); } else {
				 * setLastNameEn(part2); } } }
				 */

				/*
				 * if (i == 36) { String[] parts = string.split("="); String
				 * part2 = parts[1]; if (part2 != null) { if
				 * (part2.equalsIgnoreCase("\u00A0")) {
				 * setShortNameEn(part2.replaceAll("\u00A0", "")); } else {
				 * setLastNameEn(getLastNameEn()+" "+part2);
				 * 
				 * } } }
				 */

			}
		} else {

			setBooIdDetail(true);
			setBooManual(false);
			setSelectType(null);

			RequestContext.getCurrentInstance().execute("dldInserCard.show();");

		}

		return sb.toString();
	}

	public void fetchSmartcardDataFromDab() {

		getArticleData();
		customerList = icustomerRegistrationService.getCustomerInfo(getPkCustomerId());

		// setNationality(customerList.get(0).getFsCountryMasterByNationality().getCountryId());

		setCreatedByCustomer(customerList.get(0).getCreatedBy());
		setCreationDateCustomer(customerList.get(0).getCreationDate());
		setCraetedDateforCustomer(new SimpleDateFormat("dd/MM/yyyy").format(customerList.get(0).getCreationDate()));

		if (customerList.get(0).getFsArticleDetails() != null) {

			setArticle(customerList.get(0).getFsArticleDetails().getFsArticleMaster().getArticleId());
			generateLevel();
			setLevel(customerList.get(0).getFsArticleDetails().getArticleDetailId());
			generateIncomeRange();
			setIncomeRange(customerList.get(0).getFsIncomeRangeMaster().getIncomeRangeId());
		}
		/*
		 * setFirstName(customerList.get(0).getFirstName());
		 * setMiddleName(customerList.get(0).getMiddleName()); if
		 * (customerList.get(0).getLastName() != null &&
		 * customerList.get(0).getEmosCustomer() != null &&
		 * !customerList.get(0).getEmosCustomer().equalsIgnoreCase("1")) {
		 * setLastNameEn(customerList.get(0).getLastName()); }
		 */
		// setShortName(customerList.get(0).getLastName());
		// setFirstNameAr(customerList.get(0).getFirstNameLocal());
		// setMiddleNameArabic(customerList.get(0).getMiddleNameLocal());
		/*
		 * if (customerList.get(0).getLastNameLocal() != null &&
		 * customerList.get(0).getEmosCustomer() != null &&
		 * !customerList.get(0).getEmosCustomer().equalsIgnoreCase("1")) {
		 * setLastNameArabic(customerList.get(0).getLastNameLocal()); }
		 */
		// setDigitalSignature(customerList.get(0).getSignatureSpecimen());
		if (customerList.get(0).getSignatureSpecimenClob() != null) {
			try {
				setDigitalSignature(customerList.get(0).getSignatureSpecimenClob()
						.getSubString(1, (int) customerList.get(0).getSignatureSpecimenClob().length()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// clobToString(customerList.get(0).getSignatureSpecimenClob()));
		}

		// checking telephone1 and telephone2 which is mobile to set in form 1
		/*
		 * if(getTelephone1() != null && getTelephone1().length()>5){ Boolean
		 * checktel = validateMobileNumber(getTelephone1()); if(checktel){
		 * setTelephone1(getTelephone1()); } }
		 * 
		 * if(getTelephone2() != null && getTelephone2().length()>5){ Boolean
		 * checktel = validateMobileNumber(getTelephone2()); if(checktel){
		 * setTelephone1(getTelephone2()); } }
		 */
		setTelephone1(customerList.get(0).getMobile());

		setScardemail(customerList.get(0).getEmail());
		setActiveInd(customerList.get(0).getActivatedInd());
		setEmosCustomer(customerList.get(0).getEmosCustomer());
		setIsActive(customerList.get(0).getIsActive());
		setSmartCardInd(customerList.get(0).getSmartCardIndicator());

		if (customerList.get(0).getCustomerReference() != null) {
			setUpdateCustomerRefNo(customerList.get(0).getCustomerReference());
		}

		if (customerList.get(0).getPepsIndicator() != null) {
			if (customerList.get(0).getPepsIndicator().equalsIgnoreCase(Constants.Yes)) {
				setPepsindicator(Constants.Yes);// Added by Nazish on 11-Feb
				// 2015
			} else {
				setPepsindicator(Constants.No);
			}
		}
		if (customerList.get(0).getIntroducedBy() != null) {
			String introducerIdNo = icustomerRegistrationService.getCustomerIdentity(new BigDecimal(customerList.get(0).getIntroducedBy()));
			setIntroducedBy(introducerIdNo);
		}

		if (customerList.get(0).getMedicalInsuranceInd() != null) {
			if (customerList.get(0).getMedicalInsuranceInd().equalsIgnoreCase(Constants.Yes)) {
				setMedicalInsuranceInd(Constants.Yes);// Added by Nazish on
				// 11-Feb 2015
			} else {
				setMedicalInsuranceInd(Constants.No);
			}
		}

		checkPepLink();

	}

	// validate mobile Number to set in form 1
	public boolean validateMobileNumber(String mobileNumber) {
		String returnString = generalService.validateMobileTelephone(sessionStateManage.getCountryAlphaTwoCode(), mobileNumber,
				Constants.MOBILE_CONTACT);
		if (returnString.equalsIgnoreCase(Constants.Yes)) {
			return true;
		} else {
			return false;
		}

	}

	public void checkPepLink() {
		List<CustomerIdProof> customerId = generalService.getCustomerIdBasedOnCivilId(getIdNumber());
		if (customerId.size() != 0) {
			custlist = icustomerRegistrationService.getVerificationToken(customerId.get(0).getFsCustomer().getCustomerId());
			if (custlist.size() != 0) {
				pepsReportStatus = custlist.get(0).getPepsIndicator();
				if (pepsReportStatus != null) {
					if (pepsReportStatus.equalsIgnoreCase("y")) {
						setDisablePipsDropdown(true);
						setBooPepDescriptionReport(false);
						setBooPepDescriptionUpdateReport(false);
					} else {
						setDisablePipsDropdown(false);
					}
				}
			} else {
				setDisablePipsDropdown(false);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public boolean saveSmartCardInfoToCustomer() throws Exception {

		// try {
		boolean checkDupCustRef = false;
		String dupCustRef = "";
		Customer customer = new Customer();

		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(sessionStateManage.getCountryId());
		customer.setFsCountryMasterByCountryId(countryMaster);

		/* Nationality Id save */
		if (getNationality() != null) {
			CountryMaster nationality = new CountryMaster();
			nationality.setCountryId(getNationality());
			customer.setFsCountryMasterByNationality(nationality);
		}

		/* save company */
		CompanyMaster companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(sessionStateManage.getCompanyId());
		customer.setFsCompanyMaster(companyMaster);

		/** Customer Type */
		BizComponentData customerType = new BizComponentData();
		customerType.setComponentDataId(generalService.getComponentId(Constants.CUSTOMERTYPE_INDU, sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId());
		customer.setFsBizComponentDataByCustomerTypeId(customerType);

		/** save Group ID */
		BizComponentData companyGroup = new BizComponentData();
		companyGroup.setComponentDataId(generalService.getComponentId(Constants.GROUPID, sessionStateManage.getLanguageId()).getFsBizComponentData()
				.getComponentDataId());
		customer.setFsBizComponentDataByGroupId(companyGroup);

		LanguageType languageType = new LanguageType();
		languageType.setLanguageId(sessionStateManage.getLanguageId());
		customer.setFsLanguageType(languageType);

		customer.setTitle(getTitle());
		customer.setGender(getGender());
		customer.setTitleLocal(getTitlel());

		customer.setFirstName(getFirstNameEn());
		customer.setMiddleName(getMiddleNameEn());
		customer.setLastName(getLastNameEn() == null ? "" : getLastNameEn().trim());
		customer.setShortName(getShortNameEn());

		//customer.setOtpNo(getOtpNo());
		//customer.setOtpRetry(getOtpRetry());
		//customer.setOtpRetryDate(getOtpRetryDate());
		//customer.setOtpVerifiedBy(getOtpVerifiedBy());
		//customer.setOtpVerifiedDate(getOtpVerifiedDate());

		if(getIdNumber()!=null){
			customer.setCivilId(getIdNumber());
		}

		/*
		 * if(getFirstNameEn()!=null && getFirstNameEn().equalsIgnoreCase("")){
		 * String fullName = getFullNameEn(); String names[] =
		 * fullName.split(" ");
		 * 
		 * if (names.length == 4) { String firstName = names[0]; String
		 * middleName = names[1] + " " + names[2]; String lastName = names[3];
		 * customer.setFirstName(firstName); customer.setMiddleName(middleName);
		 * customer.setLastName(lastName); } else if (names.length == 5) {
		 * String firstName = names[0]; String middleName = names[1] + " " +
		 * names[2] + " " + names[3]; String lastName = names[4];
		 * customer.setFirstName(firstName); customer.setMiddleName(middleName);
		 * customer.setLastName(lastName);
		 * 
		 * } else if (names.length == 3) { String firstName = names[0]; String
		 * middleName = names[1]; String lastName = names[2];
		 * customer.setFirstName(firstName); customer.setMiddleName(middleName);
		 * customer.setLastName(lastName);
		 * 
		 * } else if (names.length == 2) { String firstName = names[0]; //
		 * String middleName = names[1]; String lastName = names[1];
		 * customer.setFirstName(firstName); //
		 * customer.setMiddleName(middleName); customer.setLastName(lastName);
		 * 
		 * } else if (names.length == 1) { String firstName = names[0];
		 * customer.setFirstName(firstName); } else {
		 * 
		 * String firstName = names[0]; String middleName = names[1]; String
		 * lastName = names[2]; customer.setFirstName(firstName);
		 * customer.setMiddleName(middleName); customer.setLastName(lastName);
		 * 
		 * } }
		 */

		/*
		 * if (getShortName() != null && getShortName().equalsIgnoreCase("")) {
		 * customer.setShortName(getShortName()); }
		 */

		customer.setFirstNameLocal(getFirstNameAr());
		customer.setMiddleNameLocal(getMiddleNameArabic());
		customer.setLastNameLocal(getLastNameArabic() == null ? "" : getLastNameArabic().trim());

		if (getShortNamel() != null) {
			customer.setShortNameLocal(getShortNamel());
		}

		if (getIncomeRange() != null) {

			// save to Income
			IncomeRangeMaster incomeRange = new IncomeRangeMaster();
			incomeRange.setIncomeRangeId(getIncomeRange());
			customer.setFsIncomeRangeMaster(incomeRange);

			ArticleDetails articleDetail = new ArticleDetails();
			articleDetail.setArticleDetailId(getLevel());
			customer.setFsArticleDetails(articleDetail);

		}

		if (getDigitalSignature() != null) {
			// customer.setSignatureSpecimen(getDigitalSignature());
			customer.setSignatureSpecimenClob(stringToClob(getDigitalSignature()));
		}
		customer.setSmartCardIndicator(Constants.CUST_ACTIVE_INDICATOR);

		// customer.setIsActive(Constants.CUST_INACTIVE_INDICATOR);
		customer.setActivatedInd(Constants.CUST_ACTIVE_INDICATOR);
		customer.setActivatedDate(getCurrentTime());

		customer.setBranchCode(new BigDecimal(sessionStateManage.getBranchId()));

		if (getIntroducedBy() != null) {
			BigDecimal custRefIntroducer = icustomerRegistrationService.getCustomerReference(getIntroducedBy());
			if (custRefIntroducer.compareTo(BigDecimal.ZERO) != 0) {
				customer.setIntroducedBy(custRefIntroducer.toString());
				customer.setIntroducedDate(new Date());
			}
		}
		customer.setMedicalInsuranceInd(getMedicalInsuranceInd());
		customer.setPepsIndicator(getPepsindicator());

		customer.setMobile(getTelephone1());
		customer.setEmail(getScardemail());
		if (getBooChangeDob()) {
			customer.setDateOfBirth(getDob());
		} else {
			try {
				customer.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(getBirthdate()));
			} catch (ParseException e1) {
				log.info("Parse exception : " + e1);
			}
		}

		customer.setEmosCustomer(getEmosCustomer());

		// @@@ AML
		String amlReturnStatus = null;
		String amlStatus = null;
		String amlhits = null;

		/*
		 * amlReturnStatus = getAMLCheckStatus(customer);
		 * 
		 * if (amlReturnStatus == null) {
		 * customer.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);
		 * customer.setNumberOfHits(new BigDecimal(0)); } else { String[] parts
		 * = amlReturnStatus.split("-"); amlStatus = parts[0]; amlhits =
		 * parts[1];
		 * 
		 * if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_ERROR)) {
		 * customer.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);
		 * customer.setNumberOfHits(new BigDecimal(amlhits)); } if (amlStatus
		 * .equalsIgnoreCase(Constants.FINSCAN_STATUS_PENDING)) {
		 * customer.setAmlStatus(Constants.AML_STATUS_BCO);
		 * customer.setNumberOfHits(new BigDecimal(amlhits)); } if
		 * (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PASS)) {
		 * customer.setAmlStatus(Constants.AML_STATUS_PASS);
		 * customer.setNumberOfHits(new BigDecimal(0)); } }
		 * 
		 * /* Managing save or update
		 */
		if (getPkCustomerId() != null) {

			customer.setCustomerId(getPkCustomerId());
			customer.setCreatedBy(getCreatedByCustomer());
			customer.setCreationDate(getCreationDateCustomer());
			customer.setIsActive(getIsActive());

			List<Customer> referenceList = icustomerRegistrationService.getVerificationToken(getPkCustomerId());
			if (referenceList.size() > 0) {
				setUpdateCustomerRefNo(referenceList.get(0).getCustomerReference());
				setKioskPin(referenceList.get(0).getKioskPin());
			}
			customer.setCustomerReference(getUpdateCustomerRefNo());
			customer.setKioskPin(getKioskPin());
			if (getDigitalSignature() != null) {
				// customer.setSignatureSpecimen(getDigitalSignature());
				customer.setSignatureSpecimenClob(stringToClob(getDigitalSignature()));

			}

			if (getActiveInd() != null && getActiveInd().equalsIgnoreCase(Constants.CUST_ACTIVE_INDICATOR) && getIsActive() != null
					&& getIsActive().equalsIgnoreCase(Constants.CUST_ACTIVE_INDICATOR)) {
				customer.setUpdatedBy(sessionStateManage.getUserName());
				customer.setLastUpdated(getCurrentTime());
			}

			if (getActiveInd() != null && getActiveInd().equalsIgnoreCase(Constants.CUST_INACTIVE_INDICATOR)) {
				// Added by nazish on 30-09-2015
				customer.setVerificationBy(sessionStateManage.getUserName());
				customer.setVerificationDate(getCurrentTime());
			}

		} else {
			customer.setCreatedBy(sessionStateManage.getUserName());
			customer.setCreationDate(getCurrentTime());
			customer.setIsActive(Constants.CUST_INACTIVE_INDICATOR);
			if (getDigitalSignature() != null) {
				// customer.setSignatureSpecimen(getDigitalSignature());
				customer.setSignatureSpecimenClob(stringToClob(getDigitalSignature()));
			}
			BigDecimal custRef = icustomerRegistrationService.callProcedureCustReferenceNumber(getCompanyCodeByCompanyId(),
					Constants.DOCUMENT_CODE_FOR_CUSTOMER, getDealYearbyDate(), sessionStateManage.getBranchId());

			if (custRef != null) {
				customer.setCustomerReference(custRef);
			}

		}
		checkMailVerfication(getPkCustomerId());
		List<Customer> listDetails = icustomerRegistrationService.getIntroducerCustId(customer.getCustomerReference());
		if (listDetails != null && listDetails.size() > 1) {
			int i = 0;
			for (Customer customer1 : listDetails) {
				if(customer1.getIsActive() != null && customer1.getIsActive().equalsIgnoreCase(Constants.Yes)){
					i++;
					dupCustRef = dupCustRef == "" ? dupCustRef.concat(customer.getCustomerReference().toPlainString()) : dupCustRef.concat(",").concat(customer1.getCustomerReference().toPlainString());
				}
			}

			if(i > 1){
				setCustomerDuplicateCustRef(dupCustRef);
				checkDupCustRef = true;
				return checkDupCustRef;
			}
		}


		System.out.println(getIdNumber());

		List<Customer> duplicateCustomer = icustomerRegistrationService.checkDuplicateFSCustomer(getIdNumber());

		if (duplicateCustomer != null) {

			if (duplicateCustomer.size() > 1) {
				int i = 0;
				for (Customer customer1 : listDetails) {
					if(customer1.getIsActive() != null && customer1.getIsActive().equalsIgnoreCase(Constants.Yes)){
						i++;
						dupCustRef = dupCustRef == "" ? dupCustRef.concat(customer.getCustomerReference().toPlainString()) : dupCustRef.concat(",").concat(customer1.getCustomerReference().toPlainString());
					}
				}

				if(i > 1){
					setCustomerDuplicateCustRef(dupCustRef);
					checkDupCustRef = true;
					return checkDupCustRef;
				}
			}
			if (customer.getCustomerReference() != null && 
					duplicateCustomer.size()!=0 &&  duplicateCustomer.get(0).getCustomerReference() != null) {

				if (duplicateCustomer.get(0).getCustomerReference().compareTo(customer.getCustomerReference()) != 0) {
					dupCustRef = dupCustRef.concat(customer.getCustomerReference().toPlainString()).concat(",").concat(duplicateCustomer.get(0).getCustomerReference().toPlainString());
					setCustomerDuplicateCustRef(dupCustRef);
					checkDupCustRef = true;
					return checkDupCustRef;
				}
			}
		}


		try {

			/*String componentCode = icustomerRegistrationService.getComponentCode(new BigDecimal(getIdType()));
			System.out.println(componentCode);*/
			List<EMOSCustomer> duplicateEMOSCustomer = icustomerRegistrationService	.checkDuplicateEMOSCustomer(getIdNumber(), Constants.IDTYPE_CIVILID);
			System.out.println("duplicateEMOSCustomer" + duplicateEMOSCustomer.size());
			if (duplicateEMOSCustomer != null && duplicateEMOSCustomer.size() > 1) {
				for (EMOSCustomer emosCustomer : duplicateEMOSCustomer) {
					dupCustRef = dupCustRef == "" ? dupCustRef.concat(emosCustomer.getCusref().toPlainString()) : dupCustRef.concat(",").concat(emosCustomer.getCusref().toPlainString());
				}
				setCustomerDuplicateCustRef(dupCustRef);
				checkDupCustRef = true;
				return checkDupCustRef;
			}

			if (duplicateEMOSCustomer != null && duplicateEMOSCustomer.size() == 1) {

				if (customer.getCustomerReference() != null && duplicateEMOSCustomer.get(0).getCusref() != null) {
					if (duplicateEMOSCustomer.get(0).getCusref().compareTo(customer.getCustomerReference()) != 0) {
						dupCustRef = dupCustRef == "" ? dupCustRef.concat(customer.getCustomerReference().toPlainString()) : dupCustRef.concat(",").concat(duplicateCustomer.get(0).getCustomerReference().toPlainString());
						setCustomerDuplicateCustRef(dupCustRef);
						checkDupCustRef = true;
						return checkDupCustRef;
					}
				}
			}

		}

		catch(Exception e)
		{
			System.out.println("Exception occured "+ e);
		}



		//Added by Rabil on 12 Oct 2016 to save customer and customer Id proof if any one fail it will rollback
		if(createProofList.isEmpty() && createProofList.size()==0){
			CustomerIdProof  idProof= saveIdproofPartialReturnObject();
			generalService.saveOrUpdateCustomerAndIdProof(customer, idProof);
		}else{
			generalService.saveOrUpdate((T) customer); 
		}


		//	generalService.saveOrUpdate((T) customer);


		setPkCustomerId(customer.getCustomerId());
		setMobile(customer.getMobile());
		setMobileTemp(customer.getMobile());
		setNationality(customer.getFsCountryMasterByNationality().getCountryId());
		setIsActive(customer.getIsActive());

		BigDecimal customerReference = null;

		List<Customer> custlist = icustomerRegistrationService.getVerificationToken(getPkCustomerId());
		if (custlist.size() > 0) {

			emosCustomer = custlist.get(0).getEmosCustomer();
			if (custlist.get(0).getCustomerReference() != null) {
				customerReference = custlist.get(0).getCustomerReference();
				setUpdateCustomerRefNo(customerReference);
			}
		}

		// getAMLCheckStatus_afterSave(getPkCustomerId());
		if (emosCustomer != null && emosCustomer.equalsIgnoreCase("1")) {
			icustomerRegistrationService.saveCustomerEmos(getPkCustomerId(), Constants.Yes);
		}
		/*
		 * } catch (Exception e) { log.error("Exception Occured While Saving",
		 * e); RequestContext.getCurrentInstance().execute("saveerror.show();");
		 * }
		 */

		return checkDupCustRef;

	}

	public void checkEmailCard() {
		setEmail(getScardemail());
		checkEmail();
	}

	public void checkMobileCard() {
		setMobile(getTelephone1());
		setMobileTemp(getTelephone1());
		checkMobile();
	}

	public void saveSmartCardInformationToDmsMas() {

		DmsMas smartCard = new DmsMas();

		try {
			List<CountryMaster> alphacode = icustomerRegistrationService.getCountryAlpha2Code(sessionStateManage.getCountryId());
			if (alphacode.size() != 0) {
				String countryAlphaCode = alphacode.get(0).getCountryAlpha2Code() == null ? "" : alphacode.get(0).getCountryAlpha2Code();
				smartCard.setAppnCountry(countryAlphaCode);
			}
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
			smartCard.setGender(getGenderLatin());
			smartCard.setIssuedat(new SimpleDateFormat("dd/MM/yyyy").parse(getIssuedate()));
			smartCard.setExpireDt(new SimpleDateFormat("dd/MM/yyyy").parse(getExpirydate()));
			smartCard.setBirthdat(new SimpleDateFormat("dd/MM/yyyy").parse(getBirthdate()));
			smartCard.setTel1(getTelephone1());
			smartCard.setTel2(getTelephone2());
			smartCard.setEmailId(getScardemail());
			smartCard.setBloodTyp(getBloodType());
			smartCard.setBldgno(getBuilding_no());
			smartCard.setFloorno(getScardfloor());
			smartCard.setStreet(getScardstreet());
			smartCard.setBlockNo(getsCardblock());
			smartCard.setDistrict(getScarddistrict());
			smartCard.setGuardIdno(getGuardianCivilId());
			smartCard.setAddrUniqKey(getAddressRef());
			smartCard.setProgno("JAVA");// Hard Coded Value

			if (getSmartCardId() != null) {
				smartCard.setDmsIdMasId(getSmartCardId());
				smartCard.setUpddat(getCreationDateCustomer());

			} else {
				smartCard.setCreator(sessionStateManage.getUserName());
				smartCard.setCrtdat(getCurrentTime());
			}
			icustomerRegistrationService.saveSmartCardDeatils(smartCard);
			setSmartCardId(smartCard.getDmsIdMasId());
		} catch (Exception e) {
			log.error("DMS Saving Error: " + e);
		}
	}

	public String saveSmartCardPage() {
		returnString = " ";
		setErrorMsg(null);
		log.info(getIntroducedBy());
		log.info(getIdNumber());
		setWordCountEn(0);
		setWordCountEn(0);
		/* CBK Black list customer added by Rabil */
		/* CBK Black list customer end by Rabil */

		StringBuffer localName = new StringBuffer();
		StringBuffer engNameConcat = new StringBuffer();

		if (getFirstNameEn() != null) {
			engNameConcat.append(getFirstNameEn().trim());
		}
		if (getMiddleNameEn() != null) {
			engNameConcat.append(" ").append(getMiddleNameEn().trim());
		}
		if (getLastNameEn() != null) {
			engNameConcat.append(" ").append(getLastNameEn().trim());

		}

		if (getFirstNameAr() != null) {
			localName.append(getFirstNameAr().trim());
		}
		if (getMiddleNameArabic() != null) {
			localName.append(" ").append(getMiddleNameArabic().trim());
		}
		if (getLastNameArabic() != null) {
			localName.append(" ").append(getLastNameArabic().trim());
		}

		BizComponentData customerType = new BizComponentData();
		customerType.setComponentDataId(generalService.getComponentId(Constants.CUSTOMERTYPE_INDU, sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId());

		BigDecimal customerTypeId = customerType.getComponentDataId();

		System.out.println("Smart card IdType :" + getIdType() + "\t getCivilId FOR :" + getCivilId() + "\t engNameConcat :" + engNameConcat
				+ "\t engNameConcat :" + engNameConcat + "\t customerTypeId " + customerTypeId);



		try {
			String strBlackListCust = icustomerRegistrationService.getBlackListCustomer(sessionStateManage.getCountryId(), engNameConcat.toString(),
					localName.toString(), customerTypeId, new BigDecimal(getIdType()), getCivilId());

			if (strBlackListCust != null) {
				setErrorMsg(strBlackListCust);
				RequestContext.getCurrentInstance().execute("saveerror.show();");
				return "";
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (getIntroducedBy() != null && getIdNumber() != null && getIntroducedBy().equals(getIdNumber())) {
			setIntroducedBy(null);
			RequestContext.getCurrentInstance().execute("duplicate.show();");

		}

		else {

			if (getIntroducedBy() != null && !getIntroducedBy().equalsIgnoreCase("")) {
				HashMap<String, String> mapIntroducedBy = icustomerRegistrationService.checkIntroducedByActive(sessionStateManage.getCountryId(),
						getIntroducedBy());

				if (mapIntroducedBy.size() != 0) {

					String customerActive = mapIntroducedBy.get("CustomerActive");

					if (!customerActive.equalsIgnoreCase("Y")) {
						setIntroducedBy(null);
						RequestContext.getCurrentInstance().execute("customernotactive.show();");
						return returnString;
					}

				} else {
					setIntroducedBy(null);
					RequestContext.getCurrentInstance().execute("civilexists.show();");
					return returnString;
				}
			}

			if (getBooMobilecheck() == false && getBooEmailCheck() == false) {
				try {

					BigDecimal nameLenght = icustomerRegistrationService.fetchNamelength(getNationality());
					String nameConcat = getFirstNameEn().trim() + " " + getMiddleNameEn().trim() + " " + getLastNameEn().trim();
					String nameConcatLocal = getFirstNameAr().trim() + " " + getMiddleNameArabic().trim() + " " + getLastNameArabic().trim();
					countWordEnglish(nameConcat);
					countWordLocal(nameConcatLocal);
					if (nameLenght.intValue() != 0 && getWordCountEn() < nameLenght.intValue()) {
						setNationalityName(generalService.getNationalityName(sessionStateManage.getLanguageId(), getNationality()));
						setNoOfCount(nameLenght);
						RequestContext.getCurrentInstance().execute("fourtnameerror.show();");
						return returnString;
					} else if (nameLenght.intValue() != 0 && getWordCountAr() < nameLenght.intValue()) {
						setNationalityName(generalService.getNationalityName(sessionStateManage.getLanguageId(), getNationality()));
						setNoOfCount(nameLenght);
						RequestContext.getCurrentInstance().execute("fourtnameerror.show();");
						return returnString;
					} else {
						boolean checkOld = checkingOldDbForCard();
						if (!checkOld) {
							populateContactList();
							try {
								setCustomerDuplicateIdNo(null);
								setCustomerDuplicateTypeId(null);
								setCustomerDuplicateCustRef(null);
								boolean checkDupCustRef = saveSmartCardInfoToCustomer();
								if (checkDupCustRef) {
									if(getCustomerDuplicateCustRef() != null){
										setErrorMsg("Duplicate Details Customer Reference : " + getCustomerDuplicateCustRef());
										RequestContext.getCurrentInstance().execute("dupcustomerref.show();");
										return "";
									}else{
										setErrorMsg("Current Customer Reference : " +getCustRefNo() + " , Duplicate Details Customer Type Id : " + getCustomerDuplicateTypeId() + " and Identity Type : " + getCustomerDuplicateIdNo());
										RequestContext.getCurrentInstance().execute("dupcustomerref.show();");
										return "";
									}
								}
								// saveCustomerScanDetails();
								//Commented by RAbil on 12 oct 2016 bcoz the Save saveManualPersonaInformation it will save both FS_CUSTOMER and FS_CUSTOMER ID PROOF partial
								//saveIdproofPartial();
							} catch (Exception e) {
								log.error("Exception Occured While saving Data", e);
								setErrorMsg(e.getMessage());
								RequestContext.getCurrentInstance().execute("saveerror.show();");
								return "";
							}
							saveSmartCardInformationToDmsMas();
							fetchContactDetails();
							callProcedureForOldUpdate();

							if (contactList.size() > 0) {
								setBooContactDetailsButtonPanel(true);
							} else {
								setBooContactDetailsButtonPanel(false);
							}
							// FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customercontactinfo.xhtml");
							returnString = "customerContactPage";
						} else {
							RequestContext.getCurrentInstance().execute("printCustomerReference.show();");
						}
					}
				} catch (Exception e) {
					log.error("Exception Occured While Saving", e);

					RequestContext.getCurrentInstance().execute("saveerror.show();");

				}
			}
		}
		return returnString;
	}

	public String backFromCard() {
		clear();
		setBooIdDetail(true);
		setRenderverication(false);
		setSelectType(null);
		// FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationmain.xhtml");
		return "customerMainPage";
	}

	public String clickOnSmartCard() {
		setBooIdDetail(true);
		setBooManual(false);
		setBoosmartCardAppear(true);
		// FacesContext.getCurrentInstance().getExternalContext().redirect("customerregistrationmain.xhtml");

		return "customerMainPage";
	}

	public void exit() throws IOException {
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	// ----------------------------------------------------------------X---------------------------------CONTACT
	// DETAILS INFO------------------------------X----------------------
	public BigDecimal pkCustomerContactDetails = null;
	private BigDecimal contactTypeId = null;
	private BigDecimal countryId = null;
	private BigDecimal stateId = null;
	private BigDecimal districtId = null;
	private BigDecimal cityId = null;
	private String countryName = null;
	private String stateName = null;
	private String districtName = null;
	private String cityName = null;

	private String street = null;
	private String block = null;
	private String telephone = null;
	private String area = null;

	private String flat = null;
	private String buildingNo = null;

	private String mobileContact = null;
	private String telephoneCode = null;
	private String plusSign = null;
	private Boolean renMobilContactLocal = false;
	private Boolean renMobileContact = true;
	// private Boolean renContactMobile = false;
	// private Boolean renContactTel = false;
	// private Boolean booCheckMobileContact = false;
	// private Boolean booCheckTelContact = false;
	private Boolean maskEnable = false;
	private Boolean maskNotEnable = false;

	private Boolean booContactDetailsDuplicate = false;
	private Boolean contactDataTable = false;
	private Boolean booContactDetailsButtonPanel = false;
	// private Boolean contactlistcheck = false;
	private Boolean booRendercityRequired = false;
	private Boolean booRendercityOptional = true;
	private Boolean cityRequired = false;
	private Boolean booRenderSmartCard = false;
	private Boolean booDisableCountry = false;

	private BigDecimal nationalityId = null;

	private List<AddContactDetailBean> contactList = new ArrayList<AddContactDetailBean>();
	private Map<BigDecimal, String> mapContactTypeList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapCountryList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapStateList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapDistirictList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapCityList = new HashMap<BigDecimal, String>();
	// private List<ContactDetail> contactDetailList = new
	// ArrayList<ContactDetail>();
	private List<CountryMasterDesc> countryList;
	private List<StateMasterDesc> stateList;
	private List<CityMasterDesc> cityList;
	private List<DistrictMasterDesc> districtList;
	Map<BigDecimal, String> ContactTypeList = new HashMap<BigDecimal, String>();

	public Boolean getBooRenderSmartCard() {
		return booRenderSmartCard;
	}

	public void setBooRenderSmartCard(Boolean booRenderSmartCard) {
		this.booRenderSmartCard = booRenderSmartCard;
	}

	public BigDecimal getPkCustomerContactDetails() {
		return pkCustomerContactDetails;
	}

	public void setPkCustomerContactDetails(BigDecimal pkCustomerContactDetails) {
		this.pkCustomerContactDetails = pkCustomerContactDetails;
	}

	public BigDecimal getContactTypeId() {
		return contactTypeId;
	}

	public void setContactTypeId(BigDecimal contactTypeId) {
		this.contactTypeId = contactTypeId;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
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

	public BigDecimal getCityId() {
		return cityId;
	}

	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getFlat() {
		return flat;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}

	public String getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}

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

	/*
	 * public Boolean getRenContactMobile() { return renContactMobile; } public
	 * void setRenContactMobile(Boolean renContactMobile) {
	 * this.renContactMobile = renContactMobile; } public Boolean
	 * getRenContactTel() { return renContactTel; } public void
	 * setRenContactTel(Boolean renContactTel) { this.renContactTel =
	 * renContactTel; }
	 */
	/*
	 * public Boolean getBooCheckMobileContact() { return booCheckMobileContact;
	 * } public void setBooCheckMobileContact(Boolean booCheckMobileContact) {
	 * this.booCheckMobileContact = booCheckMobileContact; } public Boolean
	 * getBooCheckTelContact() { return booCheckTelContact; } public void
	 * setBooCheckTelContact(Boolean booCheckTelContact) {
	 * this.booCheckTelContact = booCheckTelContact; }
	 */

	public Boolean getBooContactDetailsDuplicate() {
		return booContactDetailsDuplicate;
	}

	public Boolean getMaskEnable() {
		return maskEnable;
	}

	public void setMaskEnable(Boolean maskEnable) {
		this.maskEnable = maskEnable;
	}

	public Boolean getMaskNotEnable() {
		return maskNotEnable;
	}

	public void setMaskNotEnable(Boolean maskNotEnable) {
		this.maskNotEnable = maskNotEnable;
	}

	public void setBooContactDetailsDuplicate(Boolean booContactDetailsDuplicate) {
		this.booContactDetailsDuplicate = booContactDetailsDuplicate;
	}

	public Boolean getContactDataTable() {
		return contactDataTable;
	}

	public void setContactDataTable(Boolean contactDataTable) {
		this.contactDataTable = contactDataTable;
	}

	public Boolean getBooContactDetailsButtonPanel() {
		return booContactDetailsButtonPanel;
	}

	public void setBooContactDetailsButtonPanel(Boolean booContactDetailsButtonPanel) {
		this.booContactDetailsButtonPanel = booContactDetailsButtonPanel;
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

	public Boolean getCityRequired() {
		return cityRequired;
	}

	public void setCityRequired(Boolean cityRequired) {
		this.cityRequired = cityRequired;
	}

	public BigDecimal getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(BigDecimal nationalityId) {
		this.nationalityId = nationalityId;
	}

	public Boolean getBooDisableCountry() {
		return booDisableCountry;
	}

	public void setBooDisableCountry(Boolean booDisableCountry) {
		this.booDisableCountry = booDisableCountry;
	}

	public List<CountryMasterDesc> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}

	public List<StateMasterDesc> getStateList() {
		return stateList;
	}

	public void setStateList(List<StateMasterDesc> stateList) {
		this.stateList = stateList;
	}

	public List<CityMasterDesc> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityMasterDesc> cityList) {
		this.cityList = cityList;
	}

	public List<DistrictMasterDesc> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DistrictMasterDesc> districtList) {
		this.districtList = districtList;
	}

	public List<AddContactDetailBean> getContactList() {
		return contactList;
	}

	public void setContactList(List<AddContactDetailBean> contactList) {
		this.contactList = contactList;
	}

	public Map<BigDecimal, String> getContactTypeList() {
		return ContactTypeList;
	}

	public void setContactTypeList(Map<BigDecimal, String> contactTypeList) {
		ContactTypeList = contactTypeList;
	}

	public void selectIdentityType() {
		for (AddContactDetailBean contactDetails : contactList) {
			if (getContactTypeId() != null && getContactTypeId().intValue() == contactDetails.getContactTypeId().intValue()) {
				setContactTypeId(null);
				RequestContext.getCurrentInstance().execute("alreadyExist.show();");
				return;
			}
		}
		setCountryId(null);
		setMobileContact(null);
		setPlusSign(null);
		setTelephoneCode(null);
		setBlock(null);
		setBuildingNo(null);
		setFlat(null);
		setStreet(null);

		setMaskEnable(false);
		setMaskNotEnable(false);
		setBooRenderSmartCard(false);

		if (getContactTypeId() != null) {
			if (getContactTypeId().intValue() == generalService.getComponentId(Constants.PERMANENT, sessionStateManage.getLanguageId())
					.getFsBizComponentData().getComponentDataId().intValue()) {
				setCityRequired(false);
				setBooRendercityOptional(true);
				setBooRendercityRequired(false);
				setCityId(null);
				setCityName(null);
				setMobileContact(null);
				populateCountryList();
				setCountryId(getNationality());
				System.out.println("Nationality ==" + getNationality());
				setBooRenderSmartCard(false);
				popState();
				setMaskEnable(false);
				setMaskNotEnable(true);
				setBooDisableCountry(false);
				// setRenContactMobile(false);
				// setRenContactTel(false);
				// setBooCheckMobileContact(false);
				// setRenContactTel(false);
				setRenderLocalAddress(false);
				setRenderHomeAddress(true);
			} else {
				setRenderLocalAddress(true);
				setRenderHomeAddress(false);
				setBooRendercityOptional(false);
				setBooRendercityRequired(true);
				setCityRequired(true);
				setBooDisableCountry(true);
				// setRenContactMobile(true);
				// setRenContactTel(false);
				// setBooCheckMobileContact(true);
				// setRenContactTel(false);
				setMaskEnable(true);
				setMaskNotEnable(false);
				setMobileContact(getMobile());
				populateCountryList();
				setCountryId(sessionStateManage.getCountryId());
				popState();
				popDistrict();
				popCity();
				if (getStateList().size() != 0) {

					if (getSmartcardcheck()) {
						String stateDistrictId = icustomerRegistrationService.getStateDistrictIDForSmart(sessionStateManage.getCountryId(),
								getScarddistrict());
						if (stateDistrictId != null && !stateDistrictId.equalsIgnoreCase(Constants.No)) {
							String[] stateDistrictArray = stateDistrictId.split("\\s*,\\s*");
							String stateId = stateDistrictArray[0];
							String districtId = stateDistrictArray[1];
							String cityId = stateDistrictArray[2];
							if (stateId != null) {
								setStateId(new BigDecimal(stateId));
								setBooRenderSmartCard(true);
								popDistrict();
							} else {
								setBooRenderSmartCard(false);
							}

							if (getDistrictList().size() != 0) {
								if (districtId != null) {
									setDistrictId(new BigDecimal(districtId));
									setBooRenderSmartCard(true);
									popCity();
								} else {
									setBooRenderSmartCard(false);
								}
							}

							if (getCityList().size() != 0) {
								if (cityId != null) {
									setCityId(new BigDecimal(cityId));
									setBooRenderSmartCard(true);
								} else {
									setBooRenderSmartCard(false);
								}
							}

						}
					} else {

					}

				} else {
					setStateList(null);
					setStateId(null);
					setDistrictList(null);
					setDistrictId(null);
					setCityList(null);
					setCityId(null);
				}

				if (getSmartcardcheck()) {
					fetchLocalContactFromCard();
				}
			}
		} else {
			setStateList(null);
			setStateId(null);
			setDistrictList(null);
			setDistrictId(null);
			setCityList(null);
			setCityId(null);
			// setRenContactMobile(false);
			// setRenContactTel(false);
			// setBooCheckMobileContact(false);
			// setRenContactTel(false);
		}
	}

	public void fetchLocalContactFromCard() {
		try {
			if (getsCardblock() != null) {
				setBlock(getsCardblock());
			}
			if (getBuilding_no() != null) {
				setBuildingNo(getBuilding_no());
			}
			if (getScardfloor() != null) {
				if (getScardfloor().equalsIgnoreCase("\u00A0")) {
					if (getFlat() != null && getFlat().length() == 0) {
						setFlat(getScardfloor().replaceAll("\u00A0", ""));
					}
				} else {
					setFlat(getScardfloor().trim());
				}
			}
			if (getScardstreet() != null) {
				if (getScardstreet().equalsIgnoreCase("\u00A0")) {
					if (getStreet() != null && getStreet().length() == 0) {
						setStreet(getScardstreet().replaceAll("\u00A0", ""));
					}
				} else {
					setStreet(getScardstreet().trim());
				}
			}

			// checking telephone1 and telephone2 which is mobile to set in form
			// 2
			// Contact List
			if (getTelephone1() != null && getTelephone1().length() > 5) {
				Boolean checktel = validateMobileNumber(getTelephone1());
				if (!checktel) {
					// setBooCheckTelContact(true);
					// setRenContactTel(true);
					setTelephone(getTelephone1());
				} else {
					// setBooCheckTelContact(false);
					// setRenContactTel(false);
					setTelephone(null);
				}
			}

			if (getTelephone2() != null && getTelephone2().length() > 5) {
				Boolean checktel = validateMobileNumber(getTelephone2());
				if (!checktel) {
					// setBooCheckTelContact(false);
					// setRenContactTel(false);
					setTelephone(null);
				} else {
					// setBooCheckTelContact(false);
					// setRenContactTel(false);
					setTelephone(null);
				}
			}

		} catch (Exception e) {
			log.error("Exception Occured: fetchLocalContactFromCard", e);
		}
	}

	/*
	 * method is responsible to add contact in a dataTable
	 */
	public void addContactDetailsDataTable() {

		getFetchContactTypeList();
		setBooContactDetailsDuplicate(false);
		for (AddContactDetailBean contactDetails : contactList) {
			if (getContactTypeId() != null && getContactTypeId().intValue() == contactDetails.getContactTypeId().intValue()) {
				setBooContactDetailsDuplicate(true);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('duplicateDetails').show();");
				return;
			}
		}

		if (!getBooContactDetailsDuplicate()) {
			/*
			 * if ((getMobileContact() != null && getMobileContact().length() !=
			 * 0) || (getTelephone() != null && getTelephone().length() != 0)) {
			 */
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
			// addContact.setArea(this.localArea);
			addContact.setStreet(this.street);
			addContact.setFlat(this.flat);

			addContact.setBlock(this.block);
			addContact.setContactDetailsId(new BigDecimal(0));
			addContact.setBuildingNo(this.buildingNo);
			addContact.setContactDetailsId(getPkCustomerContactDetails());
			if (this.getMobileContact() != null) {
				addContact.setMobileContact(this.getMobileContact());
				addContact.setTelephoneCode(this.getTelephoneCode());
			}

			if (this.getTelephoneCode() != null) {
				addContact.setTel(this.telephone);
				addContact.setTelephoneCode(this.getTelephoneCode());
			}

			if (getCreatedByContact() != null) {
				addContact.setCreatedByContact(getCreatedByContact());
			}
			if (getCreatedDateContact() != null) {
				addContact.setCreatedDateContact(getCreatedDateContact());
			}
			contactList.add(addContact);

			clearContactDetails();
			/*
			 * } else { // setMsgMobileOrTel(true);
			 * RequestContext.getCurrentInstance().execute(
			 * "phnoMandatary.show();"); return; }
			 */
		}

		if (sessionStateManage.getCountryId().intValue() == getNationality().intValue()) {
			setRenderAddMoreContactDetailsLink(false);
		} else {
			if (contactList.size() == 2) {
				setRenderAddMoreContactDetailsLink(false);
			} else {
				setRenderAddMoreContactDetailsLink(true);
			}
		}

		if (contactList.size() == 2) {
			setContactDataTable(true);
			setRenderAddMoreContactDetailsLink(false);
			setRenderCustContactDetailsPanel(false);
		}
		if (contactList.size() > 0) {
			setRenderCustContactDetailsPanel(false);
			setContactDataTable(true);
		}

	}

	public void clearContactDetails() {

		clearContactDetail();
		setTelephoneCode(null);
		setMobileContact(null);
		setPlusSign(null);
		setContactDataTable(true);
		setBooContactDetailsButtonPanel(true);
		setPkCustomerContactDetails(null);
		setCreatedByContact(null);
		setCreatedDateContact(null);
		// setRenContactMobile(false);
		// setRenContactTel(false);
		// setBooCheckMobileContact(false);
		// setBooCheckTelContact(false);

		stateList.clear();
		districtList.clear();
		setRenderLocalAddress(true);
		setRenderHomeAddress(false);
	}

	/*
	 * method is responsible foe Clear Contact Information
	 */
	public void clearContactDetail() {

		setContactTypeId(null);
		setCountryId(null);
		setStreet(null);
		setTelephone(null);
		setDistrictId(null);
		setFlat(null);
		setCityId(null);
		setBlock(null);
		setBuildingNo(null);
		setBooContactDetailsDuplicate(false);
		setMobileContact(null);
		setTelephoneCode(null);
		setPlusSign(null);

		setMaskEnable(false);
		setMaskNotEnable(false);

	}

	/**
	 * Next or Back button is pressed from Contact Detail Panel, next panel is
	 * Employment details and Back panel is Remitter Info
	 * 
	 * @throws IOException
	 * @throws JRException
	 * @throws AMGException
	 * @throws ParseException
	 * @throws Exception
	 */

	public String savePersonalManualPage() throws IOException, AMGException, JRException, ParseException {
		contactPkList.clear();
		setErrorMsg(null);
		setWordCountEn(0);
		setWordCountEn(0);

		if (getIsfromCorporatePage() != null && getIsfromCorporatePage().equals(true)) {
			corporateSessionValues.setPidno(getIdNumber());
			corporateSessionValues.setPartName(getFirstName() + " " + getLastName());
			corporateSessionValues.setPartNameLocal(getFirstNamel() + " " + getLastNamel());
			corporateSessionValues.setPartnerEmail(getEmail());
			corporateSessionValues.setPartnerContactNumber(getMobile());
		}

		returnString = " ";

		StringBuffer localName = new StringBuffer();
		StringBuffer engNameConcat = new StringBuffer();
		/* CBK Black list customer added by Rabil */
		if (getFirstName() != null) {
			engNameConcat.append(getFirstName().trim());
		}
		if (getMiddleName() != null) {
			engNameConcat.append(" ").append(getMiddleName().trim());
		}
		if (getLastName() != null) {
			engNameConcat.append(" ").append(getLastName().trim());
		}

		if (getFirstNamel() != null) {
			localName.append(getFirstNamel().trim());
		}

		if (getMiddleNamel() != null) {
			localName.append(" ").append(getMiddleNamel().trim());
		}

		if (getLastNamel() != null) {
			localName.append(" ").append(getLastNamel().trim());
		}

		BizComponentData customerType = new BizComponentData();
		customerType.setComponentDataId(generalService.getComponentId(Constants.CUSTOMERTYPE_INDU, sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId());

		BigDecimal customerTypeId = customerType.getComponentDataId();

		// System.out.println("Smart card IdType :"+getIdType()+"\t getCivilId FOR :"+getCivilId()
		// +"\t engNameConcat :"+engNameConcat+"\t localName :"+localName+"\t customerTypeId "+customerTypeId);

		try {
			String strBlackListCust = icustomerRegistrationService.getBlackListCustomer(sessionStateManage.getCountryId(), engNameConcat.toString(),
					localName.toString(), customerTypeId, new BigDecimal(getIdType()), getIdNumber());

			if (strBlackListCust != null) {
				setErrorMsg(strBlackListCust);
				RequestContext.getCurrentInstance().execute("saveerror.show();");
				return "";
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/* CBK Black list customer end by Rabil */

		if (getIntroducedBy() != null && getIdNumber() != null && getIntroducedBy().equals(getIdNumber())) {
			setIntroducedBy(null);
			RequestContext.getCurrentInstance().execute("duplicate.show();");
		} else {

			if (getIntroducedBy() != null && !getIntroducedBy().equalsIgnoreCase("")) {
				HashMap<String, String> mapIntroducedBy = icustomerRegistrationService.checkIntroducedByActive(sessionStateManage.getCountryId(),
						getIntroducedBy());

				if (mapIntroducedBy.size() != 0) {

					String customerActive = mapIntroducedBy.get("CustomerActive");

					if (!customerActive.equalsIgnoreCase("Y")) {
						setIntroducedBy(null);
						RequestContext.getCurrentInstance().execute("customernotactive.show();");
						return returnString;
					}

				} else {
					setIntroducedBy(null);
					RequestContext.getCurrentInstance().execute("civilexists.show();");
					return returnString;
				}
			}

			if (getBooMobilecheck() == false && getBooEmailCheck() == false) {
				BigDecimal nameLenght = icustomerRegistrationService.fetchNamelength(getNationality());
				String nameConcat = getFirstName().trim() + " " + getMiddleName().trim() + " " + getLastName().trim();
				String nameConcatLocal = getFirstNamel().trim() + " " + getMiddleNamel().trim() + " " + getLastNamel().trim();
				countWordEnglish(nameConcat);
				countWordLocal(nameConcatLocal);
				if (nameLenght.intValue() != 0 && getWordCountEn() < nameLenght.intValue()) {
					setNationalityName(generalService.getNationalityName(sessionStateManage.getLanguageId(), getNationality()));
					setNoOfCount(nameLenght);
					RequestContext.getCurrentInstance().execute("fourtnameerror.show();");
					return returnString;
				} else if (nameLenght.intValue() != 0 && getWordCountAr() < nameLenght.intValue()) {
					setNationalityName(generalService.getNationalityName(sessionStateManage.getLanguageId(), getNationality()));
					setNoOfCount(nameLenght);
					RequestContext.getCurrentInstance().execute("fourtnameerror.show();");
					return returnString;
				} else {
					if (getMobileFetch()) {
						try {
							setCustomerDuplicateIdNo(null);
							setCustomerDuplicateTypeId(null);
							setCustomerDuplicateCustRef(null);
							boolean checkDupCustRef = saveManualPersonaInformation();
							if (checkDupCustRef) {
								if(getCustomerDuplicateCustRef() != null){
									setErrorMsg("Duplicate Details Customer Reference : " + getCustomerDuplicateCustRef());
									RequestContext.getCurrentInstance().execute("dupcustomerref.show();");
									return "";
								}else{
									setErrorMsg("Current Customer Reference : " +getCustRefNo() + " , Duplicate Details Customer Type Id : " + getCustomerDuplicateTypeId() + " and Identity Type : " + getCustomerDuplicateIdNo());
									RequestContext.getCurrentInstance().execute("dupcustomerref.show();");
									return "";
								}
							}

						} catch (Exception e) {
							log.error("Exception Occured While saving Data", e);
							setErrorMsg(e.getMessage());
							RequestContext.getCurrentInstance().execute("saveerror.show();");
						}
						// saveCustomerScanDetails();
						//saveIdproofPartial(); //  Commented by Rabil on 12 oct  
						fetchCustomerContactDetails();
						returnString = "customerContactPage";
					} else {
						boolean checkOld = checkingOldDb();
						if (!checkOld) {
							try {
								setCustomerDuplicateIdNo(null);
								setCustomerDuplicateTypeId(null);
								setCustomerDuplicateCustRef(null);
								boolean checkDupCustRef = saveManualPersonaInformation(); //FS_CUSTOMER and FS_CUSTOMER ID PROOF  SAVE 
								if (checkDupCustRef) {
									if(getCustomerDuplicateCustRef() != null){
										setErrorMsg("Duplicate Details Customer Reference : " + getCustomerDuplicateCustRef());
										RequestContext.getCurrentInstance().execute("dupcustomerref.show();");
										return "";
									}else{
										setErrorMsg("Current Customer Reference : " +getCustRefNo() + " , Duplicate Details Customer Type Id : " + getCustomerDuplicateTypeId() + " and Identity Type : " + getCustomerDuplicateIdNo());
										RequestContext.getCurrentInstance().execute("dupcustomerref.show();");
										return "";
									}
								}
							} catch (Exception e) {
								log.error("Exception Occured While saving Data", e);
								setErrorMsg(e.getMessage());
								RequestContext.getCurrentInstance().execute("saveerror.show();");
								return "";
							}
							// saveCustomerScanDetails();
							//Commented by RAbil on 12 oct 2016 bcoz the Save saveManualPersonaInformation it will save both FS_CUSTOMER and FS_CUSTOMER ID PROOF partial
							//saveIdproofPartial();

							fetchCustomerContactDetails();
							callProcedureForOldUpdate();
							// FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customercontactinfo.xhtml");
							returnString = "customerContactPage";
						} else {
							RequestContext.getCurrentInstance().execute("printCustomerReference.show();");
						}
					}
				}
			}
		}
		if (sessionStateManage.getCountryId().intValue() == getNationality().intValue()) {
			if (contactList.size() == 1) {
				setRenderAddMoreContactDetailsLink(false);
				setRenderCustContactDetailsPanel(false);
				setContactDataTable(true);
			}

		}
		// return "contact";
		return returnString;
	}

	public void fetchCustomerContactDetails() {

		// v$session update
		loginService.killUserSession(sessionStateManage.getUserName() + "-" + getUpdateCustomerRefNo() + "-C");

		clearContactDetail();
		populateContactList();
		fetchContactDetails();
	}


	public boolean checkDuplicateContactDetails(AddContactDetailBean detailsBean)
	{
		List<ContactDetail> tempcontactDetailList = icustomerRegistrationService.getCustomerContactDetailsForDuplicateCheck(getPkCustomerId());
		if ( tempcontactDetailList != null && tempcontactDetailList.size() > 0) {

			for (int tempOne = 0; tempOne < tempcontactDetailList.size(); tempOne++) {

				if (detailsBean.getContactTypeId().compareTo(tempcontactDetailList.get(tempOne)
						.getFsBizComponentDataByContactTypeId().getComponentDataId()) == 0) {
					if (detailsBean.getContactDetailsId()
							.compareTo(tempcontactDetailList.get(tempOne).getContactDetailId()) != 0) {
						return false;
					}

				}

			}

		}
		return true;
	}





	@SuppressWarnings("unchecked")
	public String saveCustomerContactDetails() {

		try {

			for (AddContactDetailBean details : contactList) {

				boolean check =checkDuplicateContactDetails(details);

				if(!check)
				{
					return "duplicateContactList";
				}

				ContactDetail contactDetail = new ContactDetail();

				Customer customerinfo = new Customer();
				customerinfo.setCustomerId(getPkCustomerId());
				contactDetail.setFsCustomer(customerinfo);

				BizComponentData contactType = new BizComponentData();
				contactType.setComponentDataId(details.getContactTypeId());
				contactDetail.setFsBizComponentDataByContactTypeId(contactType);

				if (details.getCountryId() != null) {
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(details.getCountryId());
					contactDetail.setFsCountryMaster(countryMaster);
				}

				LanguageType languageType = new LanguageType();
				languageType.setLanguageId(sessionStateManage.getLanguageId());
				contactDetail.setFsLanguageType(languageType);
				if (details.getStateId() != null) {
					StateMaster stateMaster = new StateMaster();
					stateMaster.setStateId(details.getStateId());
					contactDetail.setFsStateMaster(stateMaster);
				}

				if (details.getDistrictId() != null) {

					DistrictMaster districtMaster = new DistrictMaster();
					districtMaster.setDistrictId(details.getDistrictId());
					contactDetail.setFsDistrictMaster(districtMaster);
				}

				if (details.getCityId() != null) {
					CityMaster cityMaster = new CityMaster();
					cityMaster.setCityId(details.getCityId());
					contactDetail.setFsCityMaster(cityMaster);
				}
				contactDetail.setActiveStatus(Constants.CUST_ACTIVE_INDICATOR);

				contactDetail.setBlock(details.getBlock());
				contactDetail.setFlat(details.getFlat());
				contactDetail.setStreet(details.getStreet());
				contactDetail.setTelephone(details.getTel());
				contactDetail.setBuildingNo(details.getBuildingNo());
				// added by nazish cr 14-JULY-2015
				if (details.getContactTypeId().intValue() == generalService.getComponentId(Constants.PERMANENT, sessionStateManage.getLanguageId())
						.getFsBizComponentData().getComponentDataId().intValue()) {
					contactDetail.setMobile(details.getMobileContact());
				} else {
					//contactDetail.setMobile(getMobile());
					contactDetail.setMobile(getMobileTemp());
				}
				contactDetail.setTelephoneCode(details.getTelephoneCode());

				if (details.getContactDetailsId() != null) {
					contactDetail.setCreatedBy(details.getCreatedByContact());
					contactDetail.setCreationDate(details.getCreatedDateContact());
					if (details.getCreatedByContact() != null) {
						contactDetail.setUpdatedBy(sessionStateManage.getUserName());
						contactDetail.setLastUpdated(getCurrentTime());
					}
					contactDetail.setContactDetailId(details.getContactDetailsId());
				} else {
					contactDetail.setCreatedBy(sessionStateManage.getUserName());
					contactDetail.setCreationDate(getCurrentTime());
				}
				icustomerRegistrationService.saveOrUpdateContactDetails(contactDetail);

				// generalService.saveOrUpdate((T) contactDetail);

			}
		} catch (Exception e) {
			log.error("Exception Occured While saving Data", e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}

		return "";
	}

	/**
	 * 
	 * method to get Country from db mapped to hashMap
	 */
	private void populateCountryList() {
		List<CountryMasterDesc> countryListName = generalService.getCountryList(sessionStateManage.getLanguageId());
		if (countryListName.size() != 0) {
			// countryList.addAll(countryList);

			for (CountryMasterDesc countryMasterDesc : countryListName) {
				mapCountryList.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
			}
			setCountryList(countryListName);
		}
	}

	/**
	 * 
	 * method to get state from db and add all the state code and name will be
	 * mapped to hashMap
	 */
	public void popState() {

		telephoneCodeFromDB();
		getComponentBehavior();
		getComponentBehaviorMobileContact();
		if (getCountryId() != null) {
			List<StateMasterDesc> lstState = generalService.getStateList(sessionStateManage.getLanguageId(), getCountryId());
			if (sessionStateManage.getCountryId().intValue() == getCountryId().intValue()) {
				if (lstState.size() > 0) {
					setStateList(lstState);
					for (StateMasterDesc stateMasterDesc : lstState) {
						mapStateList.put(stateMasterDesc.getFsStateMaster().getStateId(), stateMasterDesc.getStateName());
						if (stateMasterDesc.getStateName().equalsIgnoreCase(Constants.KUWAIT)) {
							setStateId(stateMasterDesc.getFsStateMaster().getStateId());
							break;
						} else {
							setStateId(null);
						}
					}
					setDistrictId(null);
					setCityId(null);
					setDistrictList(null);
					setCityList(null);
				}
			} else if (lstState.size() > 0) {
				for (StateMasterDesc stateMasterDesc : lstState) {
					mapStateList.put(stateMasterDesc.getFsStateMaster().getStateId(), stateMasterDesc.getStateName());
				}

				setStateList(lstState);
				setStateId(null);
				setDistrictId(null);
				setCityId(null);
				setDistrictList(null);
				setCityList(null);
			}

		} else {
			setStateId(null);
			setDistrictId(null);
			setCityId(null);
			setDistrictList(null);
			setCityList(null);
			setStateList(null);
		}

	}

	/**
	 * 
	 * method to get District from db and add all the district code and name
	 * will be mapped to hashMap
	 */
	public void popDistrict() {

		List<DistrictMasterDesc> lstDistrict = generalService.getDistrictList(sessionStateManage.getLanguageId(), getCountryId(), getStateId());
		if (sessionStateManage.getCountryId().intValue() == getCountryId().intValue()) {
			if (lstDistrict.size() > 0) {
				setDistrictList(lstDistrict);
				for (DistrictMasterDesc districtMasterDesc : lstDistrict) {
					mapDistirictList.put(districtMasterDesc.getFsDistrictMaster().getDistrictId(), districtMasterDesc.getDistrict());
					if (districtMasterDesc.getDistrict().equalsIgnoreCase(Constants.KUWAIT)) {
						setDistrictId(districtMasterDesc.getFsDistrictMaster().getDistrictId());
						break;
					} else {
						setDistrictId(null);
					}
				}

				setCityId(null);
				setCityList(null);
			}

			if(getCountryId() != null && getStateId() != null && getDistrictId() != null){
				popCity();
			}

		} else if (lstDistrict.size() > 0) {
			for (DistrictMasterDesc districtMasterDesc : lstDistrict) {
				mapDistirictList.put(districtMasterDesc.getFsDistrictMaster().getDistrictId(), districtMasterDesc.getDistrict());
			}

			setDistrictList(lstDistrict);
			setDistrictId(null);
			setCityId(null);
			setCityList(null);
		} else {
			setDistrictList(null);
			setDistrictId(null);
			setCityId(null);
			setCityList(null);
		}

	}

	/**
	 * 
	 * method to get city from db and add all the state code and name will be
	 * mapped to hashMap
	 */
	public void popCity() {

		List<CityMasterDesc> lstCity = generalService.getCityList(sessionStateManage.getLanguageId(), getCountryId(), getStateId(), getDistrictId());
		if (lstCity.size() > 0) {
			for (CityMasterDesc cityMasterDesc : lstCity) {
				mapCityList.put(cityMasterDesc.getFsCityMaster().getCityId(), cityMasterDesc.getCityName());
			}
			setCityList(lstCity);
		}
	}

	public void telephoneCodeFromDB() {
		setPlusSign("+");
		System.out.println("CountryId==:    " + getCountryId());
		if (getCountryId() != null) {
			setTelephoneCode(generalService.getTelephoneCountryBasedOnNationality(getCountryId()));
		}
	}

	Map<String, BizComponentConfDetail> returnObject = null;
	Map<String, Map<String, Object>> jsonOutput = new HashMap<String, Map<String, Object>>();
	Map<String, String> mapValidator = new HashMap<String, String>();
	Map<String, Object> map = new TreeMap<String, Object>();

	Map<String, Object> getComponentBehavior() {

		try {

			List<String> componentNames = Arrays.asList("Telephone Number");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), sessionStateManage.getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {

				mapValidator = new HashMap<String, String>();
				mapValidator.put("MIN_VALUE", entry.getValue().getMinValue().toPlainString());
				mapValidator.put("MAX_VALUE", entry.getValue().getMaxValue().toPlainString());
			}
		} catch (Exception e) {

			map.put("error", "1");
		}
		map.put("data", jsonOutput);
		map.put("total", jsonOutput.size());
		return map;

	}

	public void validateTelephonePermanent(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if (value.toString().length() != 0) {
			if (value.toString().length() == new BigDecimal(mapValidator.get("MAX_VALUE")).intValue()) {

			} else {

				FacesMessage msg = new FacesMessage("Permanet", "Telephone Number must be " + mapValidator.get("MAX_VALUE") + " Character");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);

			}
		}

	}

	public void validateTelephone(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if (getContactTypeId() != null) {
			if (getContactTypeId().intValue() == generalService.getComponentId(Constants.RESIDENCE, sessionStateManage.getLanguageId())
					.getFsBizComponentData().getComponentDataId().intValue()) {

				String returnString = generalService.validateMobileTelephone(sessionStateManage.getCountryAlphaTwoCode(), value.toString(),
						Constants.RESIDENCE_CONTACT);
				if (returnString.equalsIgnoreCase(Constants.Yes)) {

				} else {

					FacesMessage msg = new FacesMessage("Residence", returnString);
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(msg);

				}

			} else {
				validateTelephonePermanent(context, component, value);
			}
		}

	}

	public void validateContactMobile(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if (getContactTypeId() != null) {
			if (getContactTypeId().intValue() == generalService.getComponentId(Constants.RESIDENCE, sessionStateManage.getLanguageId())
					.getFsBizComponentData().getComponentDataId().intValue()) {

				String mobileContact = value.toString();
				String removeIffn = mobileContact.replaceAll("-", "");
				String returnString = generalService.validateMobileTelephone(sessionStateManage.getCountryAlphaTwoCode(), removeIffn,
						Constants.MOBILE_CONTACT);
				if (returnString.equalsIgnoreCase(Constants.Yes)) {

				} else {

					FacesMessage msg = new FacesMessage("Residence", returnString);
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(msg);

				}

			} else {
				validateContactMobilePermanent(context, component, value);
			}
		}

	}

	public void validateContactMobilePermanent(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if (value.toString().length() != 0) {
			if (value.toString().length() == new BigDecimal(mapValidatorMobile.get("MAX_VALUE")).intValue()) {

			} else {

				FacesMessage msg = new FacesMessage("Permanet", "Mobile Number must be " + mapValidatorMobile.get("MAX_VALUE") + " Character");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);

			}
		}

	}

	Map<String, BizComponentConfDetail> returnObjectmob = null;
	Map<String, String> mapValidatorMobile = new HashMap<String, String>();
	Map<String, Object> mapMobile = new TreeMap<String, Object>();
	Map<String, Map<String, Object>> jsonOutputMob = new HashMap<String, Map<String, Object>>();

	Map<String, Object> getComponentBehaviorMobileContact() {

		try {

			List<String> componentNames = Arrays.asList("Mobile Contact");
			returnObjectmob = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(),
					sessionStateManage.getApplicationId(), sessionStateManage.getCompanyId(), getCountryId(), sessionStateManage.getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObjectmob.entrySet()) {

				mapValidatorMobile = new HashMap<String, String>();
				mapValidatorMobile.put("MIN_VALUE", entry.getValue().getMinValue().toPlainString());
				mapValidatorMobile.put("MAX_VALUE", entry.getValue().getMaxValue().toPlainString());

			}
		} catch (Exception e) {

			mapMobile.put("error", "1");
		}
		map.put("data", jsonOutputMob);
		map.put("total", jsonOutputMob.size());
		return map;

	}

	public void fetchContactDetails() {

		contactList.clear();
		clearContactDetail();
		List<ContactDetail> contactDetailList = icustomerRegistrationService.getCustomerContactDetails(getPkCustomerId());

		if (contactDetailList.size() > 0) {
			for (ContactDetail cdetail : contactDetailList) {
				AddContactDetailBean addContactDetailBean = new AddContactDetailBean();
				if (cdetail.getFsBizComponentDataByContactTypeId() != null) {
					getFetchContactTypeList();
					addContactDetailBean.setContType(mapContactTypeList.get(cdetail.getFsBizComponentDataByContactTypeId().getComponentDataId()));
					addContactDetailBean.setContactTypeId(cdetail.getFsBizComponentDataByContactTypeId().getComponentDataId());
				}
				if (cdetail.getFsCountryMaster() != null) {
					populateCountryList();
					addContactDetailBean.setCountry(mapCountryList.get(cdetail.getFsCountryMaster().getCountryId()));
					addContactDetailBean.setCountryId(cdetail.getFsCountryMaster().getCountryId());
				}
				if (cdetail.getFsStateMaster() != null) {
					addContactDetailBean.setState(generalService.getStateName(sessionStateManage.getLanguageId(), cdetail.getFsCountryMaster()
							.getCountryId(), cdetail.getFsStateMaster().getStateId()));
					addContactDetailBean.setStateId(cdetail.getFsStateMaster().getStateId());
				}
				if (cdetail.getFsDistrictMaster() != null) {
					addContactDetailBean.setDist(generalService.getDistrictName(sessionStateManage.getLanguageId(), cdetail.getFsCountryMaster()
							.getCountryId(), cdetail.getFsStateMaster().getStateId(), cdetail.getFsDistrictMaster().getDistrictId()));
					addContactDetailBean.setDistrictId(cdetail.getFsDistrictMaster().getDistrictId());
				}
				if (cdetail.getFsCityMaster() != null) {
					addContactDetailBean.setCity(generalService.getCityName(sessionStateManage.getLanguageId(), cdetail.getFsCountryMaster()
							.getCountryId(), cdetail.getFsStateMaster().getStateId(), cdetail.getFsDistrictMaster().getDistrictId(), cdetail
							.getFsCityMaster().getCityId()));
					addContactDetailBean.setCityId(cdetail.getFsCityMaster().getCityId());
				}
				addContactDetailBean.setFlat(cdetail.getFlat());
				addContactDetailBean.setBuildingNo(cdetail.getBuildingNo());
				addContactDetailBean.setBlock(cdetail.getBlock());
				addContactDetailBean.setCreatedByContact(cdetail.getCreatedBy());
				addContactDetailBean.setCreatedDateContact(cdetail.getCreationDate());
				addContactDetailBean.setContactDetailsId(cdetail.getContactDetailId());
				addContactDetailBean.setTel(cdetail.getTelephone());
				addContactDetailBean.setStreet(cdetail.getStreet());
				addContactDetailBean.setMobileContact(cdetail.getMobile());
				addContactDetailBean.setTelephoneCode(cdetail.getTelephoneCode());
				contactList.add(addContactDetailBean);

				// setContactDataTable(true);
				// setBooContactDetailsButtonPanel(true);
			}
		} else {

			setContactDataTable(false);
			setRenderCustContactDetailsPanel(true);
			setRenderLocalAddress(true);
			setRenderHomeAddress(false);
			// setBooContactDetailsButtonPanel(false);
			if (getContactTypeList().size() != 0) {

				for (Map.Entry<BigDecimal, String> entry : getContactTypeList().entrySet()) {
					if (Constants.RESIDENCE.equalsIgnoreCase(entry.getValue())) {
						System.out.println(entry.getKey() + "/" + entry.getValue());
						setContactTypeId(entry.getKey());
						selectIdentityType();
					}
				}

			}

		}

		if (contactList.size() == 2) {
			setContactDataTable(true);
			setRenderAddMoreContactDetailsLink(false);
			setRenderCustContactDetailsPanel(false);
		} else if (contactList.size() == 1) {
			setContactDataTable(true);
			setRenderAddMoreContactDetailsLink(true);
			setRenderCustContactDetailsPanel(false);
		}
		// To handle Multiple records more than 2 -- Added on 7'th September 2018
		if (contactList.size() > 1) {
			setContactDataTable(true);
		}
		if (contactList.size() > 2) {
			setRenderAddMoreContactDetailsLink(false);
		}
	}

	/*
	 * 
	 * Fetch Contact Type
	 */
	public void populateContactList() {
		try {
			Map<BigDecimal, String> mapContactList = ruleEngine.getComponentData("Contact Type");
			if (sessionStateManage.getCountryId().intValue() == getNationality().intValue()) {
				mapContactList.remove(generalService.getComponentId(Constants.PERMANENT, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
			}
			setContactTypeList(mapContactList);
		} catch (Exception e) {
			log.error("Exception Occured While Saving", e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");

		}
	}

	public void editContactDetails(AddContactDetailBean bean) {

		if(contactList!=null && contactList.size() > 2)
		{
			RequestContext.getCurrentInstance().execute("duplicateContactList.show();");
			return ;
		}
		clearContactDetail();
		if (bean.getTel() != null) {
			setTelephone(bean.getTel());
			setTelephoneCode(bean.getTelephoneCode());
			// setRenContactTel(true);
			// setBooCheckTelContact(true);

		}
		setStreet(bean.getStreet());
		setPkCustomerContactDetails(null);
		setBlock(bean.getBlock());
		if (bean.getMobileContact() != null) {
			setMobileContact(bean.getMobileContact());
			setTelephoneCode(bean.getTelephoneCode());
			// setBooCheckMobileContact(true);
			// setRenContactMobile(true);
		}

		setFlat(bean.getFlat());
		setContactTypeId(bean.getContactTypeId());
		setBuildingNo(bean.getBuildingNo());
		setPkCustomerContactDetails(bean.getContactDetailsId());
		populateCountryList();
		setCountryId(bean.getCountryId());

		popState();
		if (bean.getStateId() != null) {
			setStateId(bean.getStateId());
		}
		popDistrict();
		if (bean.getDistrictId() != null) {
			setDistrictId(bean.getDistrictId());
		}
		popCity();
		if (bean.getCityId() != null) {
			setCityId(bean.getCityId());
		}
		setBooRenderSmartCard(false);
		setCreatedByContact(bean.getCreatedByContact());
		setCreatedDateContact(bean.getCreatedDateContact());

		if (bean.getContactTypeId().intValue() == generalService.getComponentId(Constants.PERMANENT, sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId().intValue()) {
			setBooRendercityOptional(true);
			setBooRendercityRequired(false);
			setMaskEnable(false);
			setMaskNotEnable(true);
			setBooDisableCountry(false);
			setRenderLocalAddress(false);
			setRenderHomeAddress(true);
		} else {
			setMobileContact(getMobile());
			setRenderLocalAddress(true);
			setRenderHomeAddress(false);
			setBooRendercityRequired(true);
			setBooRendercityOptional(false);
			setMaskEnable(true);
			setMaskNotEnable(false);
			setBooDisableCountry(true);
			if (getSmartcardcheck()) {
				fetchLocalContactFromCard();
				String stateDistrictId = icustomerRegistrationService.getStateDistrictIDForSmart(sessionStateManage.getCountryId(),
						getScarddistrict());
				if (stateDistrictId != null && !stateDistrictId.equalsIgnoreCase(Constants.No)) {
					String[] stateDistrictArray = stateDistrictId.split("\\s*,\\s*");
					String stateId = stateDistrictArray[0];
					String districtId = stateDistrictArray[1];
					String cityId = stateDistrictArray[2];
					if (stateId != null) {
						setStateId(new BigDecimal(stateId));
						setBooRenderSmartCard(true);
						popDistrict();
					} else {
						setBooRenderSmartCard(false);
					}

					if (getDistrictList() != null && getDistrictList().size() != 0) {
						if (districtId != null) {
							setDistrictId(new BigDecimal(districtId));
							setBooRenderSmartCard(true);
							popCity();
						} else {
							setBooRenderSmartCard(false);
						}
					}

					if (getCityList() != null && getCityList().size() != 0) {
						if (cityId != null) {
							setCityId(new BigDecimal(cityId));
							setBooRenderSmartCard(true);
						} else {
							setBooRenderSmartCard(false);
						}
					}

				}
			}
		}

		contactList.remove(bean);
		/*
		 * if(contactList.size()>0){ setContactDataTable(true);
		 * setBooContactDetailsButtonPanel(true); }else{
		 * setContactDataTable(false); setBooContactDetailsButtonPanel(false); }
		 */

		setRenderCustContactDetailsPanel(true);
		setRenderAddMoreContactDetailsLink(false);
		if (contactList.size() == 1) {
			setContactDataTable(true);
		} else {
			setContactDataTable(false);
		}

	}

	public void removeContactDetail(AddContactDetailBean addContact) {

		if (addContact.getContactDetailsId() != null) {
			// icustomerRegistrationService.deleteCustContactDetails(addContact.getContactDetailsId(),
			// sessionStateManage.getUserName());
			contactPkList.add(addContact.getContactDetailsId());
			contactList.remove(addContact);
		} else {
			contactList.remove(addContact);
		}
		if (contactList.size() > 0) {
			setContactDataTable(true);
			setBooContactDetailsButtonPanel(true);
		} else {
			setContactDataTable(false);
			setBooContactDetailsButtonPanel(false);
		}

		if (sessionStateManage.getCountryId().intValue() != getNationality().intValue()) {
			if (contactList.size() == 1) {
				setRenderAddMoreContactDetailsLink(true);
				setRenderCustContactDetailsPanel(false);
				setContactDataTable(true);
			} else if(contactList.size() > 1) {
				setContactDataTable(true); 
				setRenderCustContactDetailsPanel(false);
			}

			else {
				setContactDataTable(false);
				setRenderCustContactDetailsPanel(true);
			}
		} else if (contactList.size() == 0) {
			setContactDataTable(false);
			setRenderCustContactDetailsPanel(true);
		}

	}

	/*
	 * public void enableMobileNoContact(){ if(getBooCheckMobileContact()){
	 * setRenContactMobile(true); }else{ setRenContactMobile(false); }
	 * 
	 * }
	 * 
	 * public void enableTelNoContact(){ if(getBooCheckTelContact()){
	 * setRenContactTel(true); }else{ setRenContactTel(false); }
	 * 
	 * }
	 */

	public String saveCustomerContactDetailsPage() throws AMGException, JRException, IOException {

		if(contactList!=null && contactList.size() > 2)
		{
			RequestContext.getCurrentInstance().execute("duplicateContactList.show();");
			return "";
		}

		returnString = " ";
		resetBusinessComponent();
		getpageID();

		// to delete contact details record from DB

		// checking data table size
		if (sessionStateManage.getCountryId().intValue() == getNationality().intValue()) {
			if (contactList.size() != 1) {
				RequestContext.getCurrentInstance().execute("atleastOneRecord.show();");
				return "";
			}
		} else {
			if (contactList.size() != 2) {
				RequestContext.getCurrentInstance().execute("LandHMandatary.show();");
				return "";
			}
		}

		if (getSmartcardcheck()) {

			if (getCivilId() != null) {
				String addressCheck = icustomerRegistrationService.checkAdressChangeForSmartCard(getCivilId());

				if (addressCheck != null) {
					if (!addressCheck.equalsIgnoreCase(Constants.Yes)) {
						RequestContext.getCurrentInstance().execute("addressChange.show();");
						return returnString;
					}
				}
			}

		}

		BigDecimal firstReclIdType = null;
		int i = 0 ;
		for (AddContactDetailBean addContactDetailBean : contactList) {
			if (firstReclIdType == null) {
				firstReclIdType = addContactDetailBean.getContactTypeId();
				i++;
			}

			if ((firstReclIdType != null && i > 1)
					&& (firstReclIdType.compareTo(addContactDetailBean.getContactTypeId()) == 0)) {
				RequestContext.getCurrentInstance().execute("LandHMandatary.show();");
				return "";
			}

			i++;
		}


		if (sessionStateManage.getCountryId().compareTo(getNationality()) == 0) {
			if (contactList.size() == 2) {
				RequestContext.getCurrentInstance().execute("natinalityChange.show();");
				return returnString;
			}
		}


		boolean nationalityCheck = false;
		for (AddContactDetailBean addContactDetailBean : contactList) {
			BigDecimal contactCountryID = addContactDetailBean.getCountryId();

			if (contactCountryID.compareTo(getNationality()) == 0) {
				nationalityCheck = true;
				break;
			}

		}

		if (!nationalityCheck) {
			RequestContext.getCurrentInstance().execute("natinalityChange.show();");
			return returnString;
		}

		for (AddContactDetailBean addContactDetailBean : contactList) {
			HashMap<String, String> checkContactMap = icustomerRegistrationService.checkCustomerContactDetailsExist(
					addContactDetailBean.getCountryId(), addContactDetailBean.getStateId(), addContactDetailBean.getDistrictId(),
					addContactDetailBean.getCityId());

			String countryExist = checkContactMap.get("COUNTRYEXIST");
			String stateExist = checkContactMap.get("STATEEXIST");
			String districtExist = checkContactMap.get("DISTRICTEXIST");
			String cityExist = checkContactMap.get("CITYEXIST");

			if (countryExist != null && countryExist.equalsIgnoreCase(Constants.NO)) {
				RequestContext.getCurrentInstance().execute("countryExist.show();");
				return returnString;
			}
			if (stateExist != null && stateExist.equalsIgnoreCase(Constants.NO)) {
				RequestContext.getCurrentInstance().execute("stateExist.show();");
				return returnString;
			}
			if (districtExist != null && districtExist.equalsIgnoreCase(Constants.NO)) {
				RequestContext.getCurrentInstance().execute("districtExist.show();");
				return returnString;
			}
			if (cityExist != null && cityExist.equalsIgnoreCase(Constants.NO)) {
				RequestContext.getCurrentInstance().execute("cityExist.show();");
				return returnString;
			}
		}

		setCountryId(sessionStateManage.getCountryId());
		setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), getCountryId()));
		boolean mandatoryR = false;
		boolean mandatoryP = false;
		resetComponentBehaviour();
		if (sessionStateManage.getCountryId().intValue() != getNationality().intValue()) {
			for (AddContactDetailBean contactDetails : contactList) {
				int permanentComponentId = generalService.getComponentId(Constants.PERMANENT, sessionStateManage.getLanguageId())
						.getFsBizComponentData().getComponentDataId().intValue();

				if (contactDetails.getContactTypeId().intValue() == permanentComponentId) {
					mandatoryP = true;
					if (contactDetails.getBlock() == null) {
						getRuleBehaviourForBlockHome();
					}
					if (contactDetails.getCityId() == null) {
						getRuleBehaviourForCityHome();
					}

					if (contactDetails.getFlat() == null) {
						getRuleBehaviourForFlateHome();
					}
					if (contactDetails.getBuildingNo() == null) {
						getRuleBehaviourForHouseHome();
					}
					if (contactDetails.getMobileContact() == null) {
						getRuleBehaviourForMobileHome();
					}
					if (contactDetails.getStreet() == null) {
						getRuleBehaviourForStreetHome();
					}
					if (contactDetails.getTel() == null) {
						getRuleBehaviourForTelephoneHome();
					}
					if (contactDetails.getCountryId() == null) {
						getRuleBehaviourForCountryHome();
					}
					if (contactDetails.getStateId() == null) {
						getRuleBehaviourForStateHome();
					}
					if (contactDetails.getDistrictId() == null) {
						getRuleBehaviourForDistrictHome();
					}
				} else if (contactDetails.getContactTypeId().intValue() == generalService
						.getComponentId(Constants.RESIDENCE, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId()
						.intValue()) {
					mandatoryR = true;
					if (contactDetails.getBlock() == null) {
						getRuleBehaviourForBlock();
					}
					if (contactDetails.getCityId() == null) {
						getRuleBehaviourForCity();
					}
					if (contactDetails.getDistrictId() == null) {
						getRuleBehaviourForDistrict();
					}
					if (contactDetails.getFlat() == null) {
						getRuleBehaviourForFlate();
					}
					if (contactDetails.getBuildingNo() == null) {
						getRuleBehaviourForHouse();
					}
					if (contactDetails.getMobileContact() == null) {
						getRuleBehaviourForMobile();
					}
					if (contactDetails.getStreet() == null) {
						getRuleBehaviourForStreet();
					}
					if (contactDetails.getTel() == null) {
						getRuleBehaviourForTelephone();
					}
					if (contactDetails.getCountryId() == null) {
						getRuleBehaviourForCountry();
					}
					if (contactDetails.getStateId() == null) {
						getRuleBehaviourForState();
					}
				}
			}
		} else {
			mandatoryR = true;
			mandatoryP = true;
			for (AddContactDetailBean contactDetails : contactList) {
				if (contactDetails.getBlock() == null) {
					getRuleBehaviourForBlock();
				}
				if (contactDetails.getCityId() == null) {
					getRuleBehaviourForCity();
				}
				if (contactDetails.getDistrictId() == null) {
					getRuleBehaviourForDistrict();
				}
				if (contactDetails.getFlat() == null) {
					getRuleBehaviourForFlate();
				}
				if (contactDetails.getBuildingNo() == null) {
					getRuleBehaviourForHouse();
				}
				if (contactDetails.getMobileContact() == null) {
					getRuleBehaviourForMobile();
				}
				if (contactDetails.getStreet() == null) {
					getRuleBehaviourForStreet();
				}
				if (contactDetails.getTel() == null) {
					getRuleBehaviourForTelephone();
				}
				if (contactDetails.getCountryId() == null) {
					getRuleBehaviourForCountry();
				}
				if (contactDetails.getStateId() == null) {
					getRuleBehaviourForState();
				}
			}
		}
		if (mandatoryP && mandatoryR) {
			if (getRequiredCountry().equalsIgnoreCase("N") && getRequiredState().equalsIgnoreCase("N") && getRequiredDistrict().equalsIgnoreCase("N")
					&& getRequiredCity().equalsIgnoreCase("N") && getRequiredBlock().equalsIgnoreCase("N")
					&& getRequiredStreet().equalsIgnoreCase("N") && getRequiredHouse().equalsIgnoreCase("N")
					&& getRequiredFlate().equalsIgnoreCase("N") && getRequiredMobile().equalsIgnoreCase("N")
					&& getRequiredTelephone().equalsIgnoreCase("N") && getRequiredDistrictHome().equalsIgnoreCase("N")
					&& getRequiredCityHome().equalsIgnoreCase("N") && getRequiredBlockHome().equalsIgnoreCase("N")
					&& getRequiredStreetHome().equalsIgnoreCase("N") && getRequiredHouseHome().equalsIgnoreCase("N")
					&& getRequiredFlateHome().equalsIgnoreCase("N") && getRequiredMobileHome().equalsIgnoreCase("N")
					&& getRequiredTelephoneHome().equalsIgnoreCase("N") && getRequiredCountryHome().equalsIgnoreCase("N")
					&& getRequiredStateHome().equalsIgnoreCase("N")) {

				if (contactPkList.size() > 0) {
					for (BigDecimal contactPk : contactPkList) {
						icustomerRegistrationService.deleteCustContactDetails(contactPk, sessionStateManage.getUserName());
					}
					contactPkList.clear();

				}
				String msg = saveCustomerContactDetails();
				if(msg!=null && msg.length() > 0)
				{
					log.error("Exception Occured While saving Data");
					RequestContext.getCurrentInstance().execute("duplicateContact.show();");
					return "";
				}
				populateArticleData();
				popEmpState();
				populateRelationShip();
				clearSponsor();
				fetchSponsorDetails();
				fetchEmploymentInfo();

				// delete contact details update to DB added on 27'th July 2016-
				// Atla


				callProcedureForOldUpdate();
				// fetchArticleData();
				populateArticalDetails();
				if (getSmartcardcheck()) {
					getArticleDataFromCard();
				} else {
					setRenManualArticle(true);
					setRenCardArticle(false);
				}

				returnString = "customerEmployeeInfoPage";
			} else {
				RequestContext.getCurrentInstance().execute("fillcontactdetails.show();");
				return "";
			}
		} else {
			RequestContext.getCurrentInstance().execute("LandHMandatary.show();");
			return "";
		}
		return returnString;
	}

	public String backPersonalInfo() {
		contactPkList.clear();
		returnString = " ";
		setCustomerIdProof();
		if (getSmartcardcheck()) {
			fetchSmartcardDataFromDab();
			returnString = "customerSmartCardPage";
		} else {
			fetchCustomerPersonalInformation();
			returnString = "customerManualPage";
		}
		return returnString;
	}

	public void fetchArticleData() {
		HashMap<String, BigDecimal> mapArtcile = icustomerRegistrationService.getCustomerArticleInfo(getPkCustomerId());
		if (mapArtcile.size() > 0) {
			setArticle(mapArtcile.get("ArticalMasterID"));
			setLevel(mapArtcile.get("ArticalDetailsID"));
			setIncomeRange(mapArtcile.get("IncomerangeID"));
		} else {
			setBooEmploymentPanel(true);
			setEmploymentType(null);
		}

	}

	// --------------------X------------------------------------------X-----------Employee
	// and
	// Sponsor------------------------------------------------X------------------

	// All Screen Fildes
	private String articleDesCard = null;
	private BigDecimal article = null;
	private BigDecimal level = null;
	private BigDecimal incomeRange = null;
	private BigDecimal employmentType = null;
	private BigDecimal occupation = null;
	private String employer = null;
	private BigDecimal empCountryId = null;
	private BigDecimal empStateId = null;
	private BigDecimal empDistrictId = null;
	private BigDecimal empCityId = null;
	private String empInfoBlock = null;
	private String empInfoStreet = null;
	private String emparea = null;
	private String officeTel = null;
	private String postalCode = null;
	private String department = null;
	private Date creationDateEmployee = null;
	private String createdByEmployee = null;

	// Business purpose Fields
	private String sponsorName = null;
	private BigDecimal sponsorRelationship = null;
	private Boolean renSponsor = false;
	private String createdBySponsor = null;
	private Date createdDateSponsor = null;
	public BigDecimal pkCustomerSponsorId = null;
	public BigDecimal pkCustomerEmployeeId = null;
	public BigDecimal sponsorReferenceNo = null;

	// panel rendering fileds
	private Boolean renManualArticle = true;
	private Boolean renCardArticle = false;
	private Boolean renderShowSponsor = false;
	private Boolean booEmploymentPanel = true;
	private Boolean renderHideEmployment = true;

	public String getArticleDesCard() {
		return articleDesCard;
	}

	public void setArticleDesCard(String articleDesCard) {
		this.articleDesCard = articleDesCard;
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

	public BigDecimal getIncomeRange() {
		return incomeRange;
	}

	public void setIncomeRange(BigDecimal incomeRange) {
		this.incomeRange = incomeRange;
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

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
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

	public String getEmparea() {
		return emparea;
	}

	public void setEmparea(String emparea) {
		this.emparea = emparea;
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
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

	// panel render getter and setters
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

	public Boolean getBooEmploymentPanel() {
		return booEmploymentPanel;
	}

	public void setBooEmploymentPanel(Boolean booEmploymentPanel) {
		this.booEmploymentPanel = booEmploymentPanel;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public BigDecimal getSponsorRelationship() {
		return sponsorRelationship;
	}

	public void setSponsorRelationship(BigDecimal sponsorRelationship) {
		this.sponsorRelationship = sponsorRelationship;
	}

	public String getCreatedBySponsor() {
		return createdBySponsor;
	}

	public void setCreatedBySponsor(String createdBySponsor) {
		this.createdBySponsor = createdBySponsor;
	}

	public Date getCreatedDateSponsor() {
		return createdDateSponsor;
	}

	public void setCreatedDateSponsor(Date createdDateSponsor) {
		this.createdDateSponsor = createdDateSponsor;
	}

	public Boolean getRenderShowSponsor() {
		return renderShowSponsor;
	}

	public void setRenderShowSponsor(Boolean renderShowSponsor) {
		this.renderShowSponsor = renderShowSponsor;
	}

	public Boolean getRenderHideEmployment() {
		return renderHideEmployment;
	}

	public void setRenderHideEmployment(Boolean renderHideEmployment) {
		this.renderHideEmployment = renderHideEmployment;
	}

	public Boolean getRenSponsor() {
		return renSponsor;
	}

	public void setRenSponsor(Boolean renSponsor) {
		this.renSponsor = renSponsor;
	}

	public BigDecimal getPkCustomerEmployeeId() {
		return pkCustomerEmployeeId;
	}

	public void setPkCustomerEmployeeId(BigDecimal pkCustomerEmployeeId) {
		this.pkCustomerEmployeeId = pkCustomerEmployeeId;
	}

	public BigDecimal getPkCustomerSponsorId() {
		return pkCustomerSponsorId;
	}

	public void setPkCustomerSponsorId(BigDecimal pkCustomerSponsorId) {
		this.pkCustomerSponsorId = pkCustomerSponsorId;
	}

	public BigDecimal getSponsorReferenceNo() {
		return sponsorReferenceNo;
	}

	public void setSponsorReferenceNo(BigDecimal sponsorReferenceNo) {
		this.sponsorReferenceNo = sponsorReferenceNo;
	}

	private List<ArticleDetails> levelData;
	private List<ArticleMasterDesc> articleData;
	private List<RelationsDescription> relationDescList;
	private List<StateMasterDesc> lstEmpStateList;
	private List<DistrictMasterDesc> lstEmpDistrictList;
	private List<CityMasterDesc> lstEmpCityList;
	private List<IncomeRangeMaster> lstIncomeRange;
	private List<CustomerEmploymentInfo> customerEmploymentInfoList;

	// getters and setters for List
	public List<StateMasterDesc> getLstEmpStateList() {
		return lstEmpStateList;
	}

	public List<ArticleDetails> getLevelData() {
		return levelData;
	}

	public void setLevelData(List<ArticleDetails> levelData) {
		this.levelData = levelData;
	}

	public List<RelationsDescription> getRelationDescList() {
		return relationDescList;
	}

	public void setRelationDescList(List<RelationsDescription> relationDescList) {
		this.relationDescList = relationDescList;
	}

	public List<IncomeRangeMaster> getLstIncomeRange() {
		return lstIncomeRange;
	}

	public void setLstIncomeRange(List<IncomeRangeMaster> lstIncomeRange) {
		this.lstIncomeRange = lstIncomeRange;
	}

	public List<CustomerEmploymentInfo> getCustomerEmploymentInfoList() {
		return customerEmploymentInfoList;
	}

	public void setCustomerEmploymentInfoList(List<CustomerEmploymentInfo> customerEmploymentInfoList) {
		this.customerEmploymentInfoList = customerEmploymentInfoList;
	}

	public List<ArticleMasterDesc> getArticleData() {
		return articleData;
	}

	public void setArticleData(List<ArticleMasterDesc> articleData) {
		this.articleData = articleData;
	}

	public void setLstEmpStateList(List<StateMasterDesc> lstEmpStateList) {
		this.lstEmpStateList = lstEmpStateList;
	}

	public List<DistrictMasterDesc> getLstEmpDistrictList() {
		return lstEmpDistrictList;
	}

	public void setLstEmpDistrictList(List<DistrictMasterDesc> lstEmpDistrictList) {
		this.lstEmpDistrictList = lstEmpDistrictList;
	}

	public List<CityMasterDesc> getLstEmpCityList() {
		return lstEmpCityList;
	}

	public void setLstEmpCityList(List<CityMasterDesc> lstEmpCityList) {
		this.lstEmpCityList = lstEmpCityList;
	}

	// Autowired Services

	@Autowired
	IBranchPageService<T> branchpageService;

	// Business Methods

	public void generateLevel() {
		enableDependent();
		levelData = branchpageService.getLevelData(getArticle(), sessionStateManage.getLanguageId());
	}

	public void enableDependent() {

		List<ArticleMaster> articleList = icustomerRegistrationService.getArticleMasterList(getArticle());
		if (articleList.size() > 0) {
			if (articleList.get(0).getCustomerType().equalsIgnoreCase("D")) {
				setRenderShowSponsor(true);
				setSponsorName(null);
				setSponsorRelationship(null);
				setRenderHideEmployment(false);
			} else {

				setRenderShowSponsor(false);
				setRenderHideEmployment(true);
			}
		} else {

			setRenderShowSponsor(false);
			setRenderHideEmployment(true);
		}

	}

	public void generateIncomeRange() {

		lstIncomeRange = generalService.getIncomeRange(sessionStateManage.getCountryId(), getLevel());
	}

	public void getEmploymentStatus(AjaxBehaviorEvent event) {
		/* 53 means unemployed and 0 means Select */

		if (getEmploymentType().intValue() == generalService.getComponentId(Constants.EMPLOYMENTTYPE, sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId().intValue()
				|| getEmploymentType().intValue() == 0) {
			if (getPkCustomerEmployeeId() != null) {
				RequestContext.getCurrentInstance().execute("unemployeechange.show();");
			} else {
				setBooEmploymentPanel(false);
			}
		} else {
			setBooEmploymentPanel(true);
		}
		if (getAdditinal2() != null) {
			setEmployer(getAdditinal2());
			// To get the state and district.
			// popEmpState();
			// popEmpDistrict();
			// popEmpCity();

		} else {
			setEmployer(null); // Modified 11102015
		}

	}

	public void popEmpDistrict() {

		List<DistrictMasterDesc> lstDistrict = generalService.getDistrictList(sessionStateManage.getLanguageId(), sessionStateManage.getCountryId(),
				getEmpStateId());
		if (lstDistrict.size() > 0) {
			setLstEmpDistrictList(lstDistrict);
			setEmpDistrictId(lstDistrict.get(0).getFsDistrictMaster().getDistrictId());
			popEmpCity();
		} else {
			setEmpDistrictId(null);
			setEmpCityId(null);
			setLstEmpCityList(null);
		}
	}

	public void popEmpCity() {
		List<CityMasterDesc> lstCity = generalService.getCityList(sessionStateManage.getLanguageId(), sessionStateManage.getCountryId(),
				getEmpStateId(), getEmpDistrictId());
		if (lstCity.size() > 0) {
			setLstEmpCityList(lstCity);
		}
		setEmpCityId(null);

	}

	public void validateOfficeTelephone(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		String returnString = generalService.validateMobileTelephone(sessionStateManage.getCountryAlphaTwoCode(), value.toString(),
				Constants.RESIDENCE_CONTACT);
		if (returnString.equalsIgnoreCase(Constants.Yes)) {
		} else {
			FacesMessage msg = new FacesMessage("Residence", returnString);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

		}

	}

	public String saveEmployeeDetailsPage() {
		setCustnormalImage(null);
		setViewImage(null);
		setBooRenderClearDebitCard(false);
		if (getIsfromCorporatePage() != null && getIsfromCorporatePage().equals(true)) {
			corporateSessionValues.setOccupationId(getOccupation());
			createProofList.clear();
			if (createProofList.size() == 0) {
				setBooidproofDatatable(false);
				setRenderSignature(false);
			} else {
				setRenderSignature(true);
				setBooidproofDatatable(true);
			}
		}

		returnString = " ";
		try {
			idExpiryDateInputOrCalendarFieldCheck();
			if (getRenderHideEmployment() && getBooEmploymentPanel()) {
				saveEmployeeInfo();
			} else if (getPkCustomerEmployeeId() != null) {
				icustomerRegistrationService.updateEmployment(getPkCustomerEmployeeId());
			}
			if (getRenderShowSponsor()) {
				saveCustomerSponcerInfo();
			} else if (getPkCustomerSponsorId() != null) {
				icustomerRegistrationService.updateSponsor(getPkCustomerSponsorId());
			}
			updateCustomerArticle();
			callProcedureForOldUpdate();
			/*gotoCustomerDocumentScanPage();
			setIdFor(new BigDecimal(48));
			setCheckImageReject(true);
			if (getSmartcardcheck()) {
				BigDecimal idtypeCivilId = generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId())
						.getFsBizComponentData().getComponentDataId();
				setIdTypeproof(idtypeCivilId);
				setIdnumberProof(getCivilId());
				// setDateExp(getExpDate());
			} else {
				if (getIdType() != null) {
					setIdTypeproof(new BigDecimal(getIdType()));
					setIdnumberProof(getIdNumber());
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
				}
			}
			returnString = "customerSignaturePage";*/
			callCustomerDebitCardDetails();
			returnString = "customerDebitCarnumberPage";
		} catch (Exception e) {
			log.error("Exception Occured While saving Data", e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
		return returnString;
	}

	public void gotoCustomerDocumentScanPage() {
		setCustomerIdProof();
		setBooProof(true);
		setSignaturePanelRender(false);
		idExpiryDateInputOrCalendarFieldCheck();

		if (createProofList.size() == 0) {
			setBooidproofDatatable(false);
			setRenderSignature(false);
		} else {
			setRenderSignature(true);
			setBooidproofDatatable(true);
		}
	}

	public void idExpiryDateInputOrCalendarFieldCheck() {
		if (getSmartcardcheck().equals(true)) {
			setRenderIdExpiryDateCalenderField(false);
			setRenderIdExpiryDateInputField(true);
		} else {
			setRenderIdExpiryDateCalenderField(true);
			setRenderIdExpiryDateInputField(false);
		}
	}

	@SuppressWarnings("unchecked")
	public void saveEmployeeInfo() {
		CustomerEmploymentInfo custEmp = new CustomerEmploymentInfo();

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

		if (getEmpCityId() != null) {
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
		custEmp.setIsActive(Constants.Yes);

		if (getPkCustomerEmployeeId() != null) {
			custEmp.setCustEmpInfoId(getPkCustomerEmployeeId());
			custEmp.setUpdatedBy(sessionStateManage.getUserName());
			custEmp.setLastUpdated(getCurrentTime());
			custEmp.setCreatedBy(getCreatedByEmployee());
			custEmp.setCreationDate(getCreationDateEmployee());
		} else {
			custEmp.setCreatedBy(sessionStateManage.getUserName());
			custEmp.setCreationDate(getCurrentTime());
		}
		generalService.saveOrUpdate((T) custEmp);

	}

	@SuppressWarnings("unchecked")
	public void saveCustomerSponcerInfo() {

		CustomerSponsor sponsorObj = new CustomerSponsor();

		Customer customer = new Customer();
		customer.setCustomerId(getPkCustomerId());
		sponsorObj.setFsCustomer(customer);
		sponsorObj.setSponsorName(getSponsorName());

		Relations relation = new Relations();
		relation.setRelationsId(getSponsorRelationship());
		sponsorObj.setExRelations(relation);
		if (getUpdateCustomerRefNo() == null) {
			BigDecimal customerreference = icustomerRegistrationService.getCustomerReference(getIdNumber());
			sponsorObj.setCustomerRefrenceNo(customerreference);
		} else {
			sponsorObj.setCustomerRefrenceNo(getUpdateCustomerRefNo());
		}

		sponsorObj.setIsActive(Constants.Yes);
		if (getPkCustomerSponsorId() != null) {
			sponsorObj.setCustomerSponsorId(getPkCustomerSponsorId());
			sponsorObj.setCreatedBy(getCreatedBySponsor());
			sponsorObj.setCreatedDate(getCreatedDateSponsor());
			sponsorObj.setUpdatedBy(sessionStateManage.getUserName());
			sponsorObj.setUpdatedDate(getCurrentTime());
		} else {
			sponsorObj.setCreatedBy(sessionStateManage.getUserName());
			sponsorObj.setCreatedDate(getCurrentTime());
		}

		generalService.saveOrUpdate((T) sponsorObj);
	}

	public void updateCustomerArticle() {
		icustomerRegistrationService.updateCustomerArticle(getPkCustomerId(), getLevel(), getIncomeRange());
	}

	public void employeeStatusIfYes() {
		icustomerRegistrationService.deleteEmployeeInfo(getPkCustomerEmployeeId());
		setBooEmploymentPanel(false);
		clearCustomerEmployeeOnChange();
	}

	public void employeeStatusIfNo() {
		if (customerEmploymentInfoList.size() != 0) {
			setEmploymentType(customerEmploymentInfoList.get(0).getFsBizComponentDataByEmploymentTypeId().getComponentDataId());
			setBooEmploymentPanel(true);
		}
	}

	public void clearCustomerEmployeeOnChange() {
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
		setPkCustomerEmployeeId(null);
		lstEmpDistrictList.clear();
		lstEmpCityList.clear();
	}

	public void clearEmploymentInfo() {

		setArticle(null);
		setLevel(null);
		setEmploymentType(null);
		setOccupation(null);
		setEmployer("");
		setEmpInfoBlock("");
		setEmparea("");
		setEmpStateId(null);
		setEmpCityId(null);
		setEmpDistrictId(null);
		setEmpInfoStreet("");
		setOfficeTel("");
		setPostalCode("");
		setDepartment("");
		setIncomeRange(null);
		//Added by Rabil to clear on 03 12 2016
		setAdditinal2(null);

	}

	public void clearSponsor() {
		setSponsorName(null);
		setSponsorRelationship(null);
		setPkCustomerSponsorId(null);

	}

	// pre loaded methods
	public void popEmpState() {
		lstEmpStateList = generalService.getStateList(sessionStateManage.getLanguageId(), sessionStateManage.getCountryId());
		if (lstEmpStateList.size() > 0) {
			setLstEmpStateList(lstEmpStateList);
			setEmpStateId(lstEmpStateList.get(0).getFsStateMaster().getStateId());
		}
		popEmpDistrict();

	}

	public void populateRelationShip() {
		relationDescList = icustomerRegistrationService.getRelationsDescriptionList(sessionStateManage.getLanguageId());
		setRelationDescList(relationDescList);
	}

	public void populateArticleData() {
		articleData = branchpageService.getArtilces(sessionStateManage.getCountryId(), sessionStateManage.getLanguageId());
		setArticleData(articleData);
	}

	public void fetchEmploymentInfo() {

		setEmploymentType(null);
		setOccupation(null);
		setEmployer("");
		setEmpInfoBlock("");
		setEmparea("");
		setEmpStateId(null);
		setEmpCityId(null);
		setEmpDistrictId(null);
		setEmpInfoStreet("");
		setOfficeTel("");
		setPostalCode("");
		setDepartment("");
		List<CustomerEmploymentInfo> customerEmploymentInfoList = icustomerRegistrationService.getCustomerEmploymentInfo(getPkCustomerId());

		if (customerEmploymentInfoList.size() > 0) {
			setEmploymentType(customerEmploymentInfoList.get(0).getFsBizComponentDataByEmploymentTypeId().getComponentDataId());
			// setEmpInfoEmploymentTypeId(customerEmploymentInfoList.get(0).getCustEmpInfoId());
			if(customerEmploymentInfoList.get(0).getFsBizComponentDataByOccupationId()!=null){
				setOccupation(customerEmploymentInfoList.get(0).getFsBizComponentDataByOccupationId().getComponentDataId());
			}
			if (getAdditinal2() != null) {
				setEmployer(getAdditinal2());
			} else {
				setEmployer(customerEmploymentInfoList.get(0).getEmployerName());
			}
			setEmpCountryId(customerEmploymentInfoList.get(0).getFsCountryMaster().getCountryId());
			popEmpState();
			setEmpStateId(customerEmploymentInfoList.get(0).getFsStateMaster().getStateId());
			popEmpDistrict();
			setEmpDistrictId(customerEmploymentInfoList.get(0).getFsDistrictMaster().getDistrictId());
			popEmpCity();
			if (customerEmploymentInfoList.get(0).getFsCityMaster() != null) {
				setEmpCityId(customerEmploymentInfoList.get(0).getFsCityMaster().getCityId());
			}
			setEmpInfoStreet(customerEmploymentInfoList.get(0).getStreet());
			setPostalCode(customerEmploymentInfoList.get(0).getPostal());
			setEmpInfoBlock(customerEmploymentInfoList.get(0).getBlock());
			setEmparea(customerEmploymentInfoList.get(0).getArea());
			setOfficeTel(customerEmploymentInfoList.get(0).getOfficeTelephone());
			setDepartment(customerEmploymentInfoList.get(0).getDepartment());
			setCreatedByEmployee(customerEmploymentInfoList.get(0).getCreatedBy());
			setCreationDateEmployee(customerEmploymentInfoList.get(0).getCreationDate());
			setPkCustomerEmployeeId(customerEmploymentInfoList.get(0).getCustEmpInfoId());

			setCreatedByEmployee(customerEmploymentInfoList.get(0).getCreatedBy());
			setCreationDateEmployee(customerEmploymentInfoList.get(0).getCreationDate());

			/**
			 * This boolean is responsible to render the panel depending upon
			 * Employment Type
			 */
			if (getEmploymentType().intValue() == generalService.getComponentId(Constants.EMPLOYMENTTYPE, sessionStateManage.getLanguageId())
					.getFsBizComponentData().getComponentDataId().intValue()) {
				setBooEmploymentPanel(false);
			} else {
				setBooEmploymentPanel(true);
			}
		} else {
			setEmploymentType(new BigDecimal(generalService.getComponentId(Constants.EMPLOYMENTTYPE, sessionStateManage.getLanguageId())
					.getFsBizComponentData().getComponentDataId().intValue()));

			setBooEmploymentPanel(false);
		}
	}

	/**
	 * This method Fetch Sponsor Details from DB
	 * 
	 */
	public void fetchSponsorDetails() {

		List<CustomerSponsor> listCustomerSponsor = icustomerRegistrationService.getCustomerSponsorList(getPkCustomerId());
		if (listCustomerSponsor.size() > 0) {
			setRenderShowSponsor(true);
			setRenderHideEmployment(false);
			setSponsorReferenceNo(listCustomerSponsor.get(0).getCustomerRefrenceNo());
			setPkCustomerSponsorId(listCustomerSponsor.get(0).getCustomerSponsorId());
			setSponsorName(listCustomerSponsor.get(0).getSponsorName());
			setCreatedBySponsor(listCustomerSponsor.get(0).getCreatedBy());
			setCreatedDateSponsor(listCustomerSponsor.get(0).getCreatedDate());
			getRelationDescList();
			setSponsorRelationship(listCustomerSponsor.get(0).getExRelations().getRelationsId());
		}

	}

	/**
	 * This method Fetch Article from card and matching in Article Master
	 * 
	 */

	public void getArticleDataFromCard() {

		try {
			List<ArticleMasterDesc> listArticle = icustomerRegistrationService.getArtilcesThroughCode(getAdditinal1(),
					sessionStateManage.getLanguageId());
			if (listArticle.size() > 0) {
				setRenManualArticle(false);
				setRenCardArticle(true);
				setArticle(listArticle.get(0).getArticleMaster().getArticleId());

				setArticleDesCard(listArticle.get(0).getArticleeDescription());

				generateLevel();
			} else {
				setRenManualArticle(true);
				setRenCardArticle(false);
			}
		} catch (Exception e) {
			log.info("Article not Available in Card" + e);
			setRenManualArticle(true);
			setRenCardArticle(false);
		}
	}

	/**
	 * This method going to Contact Details Page
	 * 
	 * @throws IOException
	 * 
	 */
	public String backContactDetails() {
		fetchContactDetails();

		if (sessionStateManage.getCountryId().intValue() == getNationality().intValue()) {
			setRenderAddMoreContactDetailsLink(false);
		} else {
			if (contactList.size() >= 2) {
				setRenderAddMoreContactDetailsLink(false);
			} else {
				setRenderAddMoreContactDetailsLink(true);
			}
		}
		// FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customercontactinfo.xhtml");
		return "customerContactPage";
	}

	/**
	 * This method AutoComplete Area
	 * 
	 */
	public List<String> autoCompleteArea(String query) {
		if (query.length() > 0) {
			return icustomerRegistrationService.getArea(query);
		} else {
			return null;
		}
	}

	// --------------------------X---------------------------------CUSTOMER ID
	// PROOF---------------------------X

	public BigDecimal pkCustomerIdProof = null;
	private BigDecimal idTypeproof = null;
	private BigDecimal idFor = null;
	private Date expDate = null;
	private Date dateExp = null;
	private String idnumberProof = null;
	private Boolean booProof = true;
	public Boolean renderModifyScan = false;
	public Boolean readOnlyIdNumber = true;
	private boolean booidproofDatatable = false;

	// Signature Boolean
	private Boolean renderSignature;
	private Boolean signaturePanelRender = false;
	private Boolean signatureCaptureRender = false;
	private Boolean signatureSpecimenRender = false;

	private String dobS;
	private String expDateS;
	private String idNumberScan;

	private Boolean successPanel = false;
	private Boolean renFirstSuccess = false;
	private Boolean renSecondSuccess = false;
	private Date minDate = new Date();

	public BigDecimal getPkCustomerIdProof() {
		return pkCustomerIdProof;
	}

	public void setPkCustomerIdProof(BigDecimal pkCustomerIdProof) {
		this.pkCustomerIdProof = pkCustomerIdProof;
	}

	public BigDecimal getIdTypeproof() {
		return idTypeproof;
	}

	public void setIdTypeproof(BigDecimal idTypeproof) {
		this.idTypeproof = idTypeproof;
	}

	public BigDecimal getIdFor() {
		return idFor;
	}

	public void setIdFor(BigDecimal idFor) {
		this.idFor = idFor;
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

	public String getIdnumberProof() {
		return idnumberProof;
	}

	public void setIdnumberProof(String idnumberProof) {
		this.idnumberProof = idnumberProof;
	}

	public Boolean getBooProof() {
		return booProof;
	}

	public void setBooProof(Boolean booProof) {
		this.booProof = booProof;
	}

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

	public boolean isBooidproofDatatable() {
		return booidproofDatatable;
	}

	public void setBooidproofDatatable(boolean booidproofDatatable) {
		this.booidproofDatatable = booidproofDatatable;
	}

	// Scan

	public Boolean getRenderSignature() {
		return renderSignature;
	}

	public void setRenderSignature(Boolean renderSignature) {
		this.renderSignature = renderSignature;
	}

	public Boolean getSignaturePanelRender() {
		return signaturePanelRender;
	}

	public void setSignaturePanelRender(Boolean signaturePanelRender) {
		this.signaturePanelRender = signaturePanelRender;
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

	public Boolean getSuccessPanel() {
		return successPanel;
	}

	public void setSuccessPanel(Boolean successPanel) {
		this.successPanel = successPanel;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Boolean getRenFirstSuccess() {
		return renFirstSuccess;
	}

	public void setRenFirstSuccess(Boolean renFirstSuccess) {
		this.renFirstSuccess = renFirstSuccess;
	}

	public Boolean getRenSecondSuccess() {
		return renSecondSuccess;
	}

	public void setRenSecondSuccess(Boolean renSecondSuccess) {
		this.renSecondSuccess = renSecondSuccess;
	}

	public void mainCheckId() throws IOException, ParseException {

		List<IdentityTypeMaster> ocrStatusList = icustomerRegistrationService.getOcrList(getIdTypeproof());
		if (ocrStatusList != null && ocrStatusList.size() > 0) {
			if (ocrStatusList.get(0).getOcrStstus().equalsIgnoreCase(Constants.Yes)) {
				checkId();
			} else {
				checkIdNonOcr();
			}
		} else {

			RequestContext.getCurrentInstance().execute("checkidtypetable.show();");
		}
	}

	@SuppressWarnings("static-access")
	public void checkId() throws IOException {

		boolean duplicate = false;
		// if (duplicate) {
		StringBuffer urlBuffer = new StringBuffer();

		String equals = "=";
		String ampersand = "&";
		// String idTypes = "IdType";

		String strIdType = idTypeMap.get(getIdTypeproof());

		try {

			List<CountryBranch> listCountryBranch = generalService.getBranchDetailsFromBeneStatus(sessionStateManage.getCountryId(), new BigDecimal(
					sessionStateManage.getBranchId()));
			if (listCountryBranch.size() > 0 && listCountryBranch.get(0).getScanInd() != null
					&& listCountryBranch.get(0).getScanInd().equalsIgnoreCase(Constants.ScanInd_A)) {

				BigDecimal civilIdNew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId())
						.getFsBizComponentData().getComponentDataId();
				List<ArcmateScanMaster> arcmateList = icustomerRegistrationService.fetchArcmateMasterData(Constants.CHECK, Constants.OCR);
				List<ScanIdTypeMaster> scanIdList = null;
				if (getSmartcardcheck()) {
					scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(civilIdNew);
				} else {
					if (getSmartCardInd() != null && getSmartCardInd().equalsIgnoreCase(Constants.CUST_ACTIVE_INDICATOR)) {
						scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(civilIdNew);
					} else {
						scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(getIdTypeproof());
					}

				}
				if (arcmateList.size() != 0 && scanIdList.size() != 0) {
					ArcmateScanMaster arcmateValue = arcmateList.get(0);
					ScanIdTypeMaster scanIdValue = scanIdList.get(0);
					String rootContext = "http://";
					String idNo = getIdnumberProof();
					urlBuffer.append(rootContext).append(arcmateValue.getIpAddress().trim()).append("/").append(arcmateValue.getContextPath().trim());
					if (arcmateValue.getUrlParamField1() != null) {
						urlBuffer.append(arcmateValue.getUrlParamField1().trim()).append(arcmateValue.getFieldAssigner().trim()).append(idNo);
					}
					if (arcmateValue.getUrlParamField2() != null && scanIdValue.getIdTypeValue() != null) {
						urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField2().trim())
						.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getIdTypeValue());
					}
					if (arcmateValue.getUrlParamField3() != null) {
						// urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField3().trim()).append(arcmateValue.getFieldAssigner().trim()).append(idNo);
					}
					if (arcmateValue.getUrlParamField4() != null) {
						// urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField4().trim()).append(arcmateValue.getFieldAssigner().trim()).append(idNo);
					}
					URL knetRequest = new URL(urlBuffer.toString());
					System.out.println("CheckId For OCR URL :  " + urlBuffer.toString());
					log.info("Arcmate CheckId For OCR URL :  " + urlBuffer.toString());
					HttpURLConnection httConn = null;
					BufferedReader in = null;

					httConn = (HttpURLConnection) knetRequest.openConnection();
					in = new BufferedReader(new InputStreamReader(httConn.getInputStream()));

					String str = in.readLine();

					if (str.startsWith("DMS_Error")) {
						in.close();
						httConn.disconnect();
						scan();

					} else {

						String s22 = str;

						log.info("Arcmate response: " + s22);
						HashMap<String, String> map = new HashMap<String, String>();
						StringTokenizer stToken = new StringTokenizer(s22, ",");
						int i = 1;
						while (stToken.hasMoreTokens() && i <= 2) {
							String tNmae = stToken.nextToken();
							String arr[] = tNmae.split("=");
							if (arr.length == 1) {
								map.put(arr[0], "");
							} else {
								map.put(arr[0], arr[1]);
							}
							i++;

						}
						boolean valueCheck = false;
						for (String keyValue : map.keySet()) {

							String value = map.get(keyValue);
							if (value.equalsIgnoreCase("")) {
								valueCheck = true;
								break;
							}
						}

						if (valueCheck) {
							in.close();
							scan();
						} else {
							String[] str11 = s22.split("=");
							// String str22 = str11.toString();

							String[] str12 = str11[1].split(",");

							String[] str13 = str11[2].split(",");

							String[] str14 = str11[3].split(",");
							String[] str15 = str11[4].split(",");

							System.out.println("name: " + str11[0]);
							System.out.println("File: " + str12[0].toString());
							System.out.println("Status: " + str13[0].toString());
							System.out.println("idno: " + str14[0].toString());
							System.out.println("expiry Date: " + str15[0].toString());

							// String[] str14 = str11[3].split(",");

							java.util.Date expiryDcheck = null;
							String expiryDate1 = null;

							if (str11[0].startsWith("FileId")) {
								if (!str15[0].equals("")) {
									expiryDcheck = new SimpleDateFormat("yyyyMMdd").parse(str15[0].toString());
									// expiryDate1 = new
									// SimpleDateFormat("dd/MM/yyyy").format(getDateExp());
									expiryDate1 = new SimpleDateFormat("dd/MM/yyyy").format(getDateExp());
								} else {
									expiryDate1 = new SimpleDateFormat("dd/MM/yyyy").format(getDateExp());
								}
								if (!str14[0].equals("")) {
									// setIdNumberScan(str14[0]);
									setIdNumberScan(getIdnumberProof());
								} else {
									setIdNumberScan(getIdnumberProof());
								}
								setFileID(str12[0]);
								setFileScanStatus(str13[0]);
							} else {
								if (!str13[0].equals("")) {
									expiryDcheck = new SimpleDateFormat("yyyyMMdd").parse(str13[0].toString());
									expiryDate1 = new SimpleDateFormat("dd/MM/yyyy").format(expiryDcheck);
								} else {
									expiryDate1 = new SimpleDateFormat("dd/MM/yyyy").format(getDateExp());
								}
								if (!str12[0].equals("")) {
									setIdNumberScan(str12[0]);
								} else {
									setIdNumberScan(getIdnumberProof());
								}
							}

							SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
							if (expiryDate1 != null) {
								setIdExpiryDateScan(dateformat.parse(expiryDate1));
							} else {
								setIdExpiryDateScan(getExpDate());
							}
							getFetchContactTypeList();

							String scanexpireDate = expiryDate1;

							boolean expDateChecking = true;

							if (expiryDcheck != null) {
								if (expiryDcheck.compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {
									expDateChecking = true;
								} else {
									expDateChecking = false;
								}

							} else {
								expDateChecking = true;
							}

							// if
							// (expiryDcheck.compareTo(dateformat.parse(dateformat.format(new
							// Date()))) >= 0) {
							if (expDateChecking) {

								duplicate = duplicateCheck();
								if (duplicate) {
									// if(getIdnumberProof()!=null &&
									// getIdnumberProof().equalsIgnoreCase(getIdNumberScan())){

									if (getDateExp() != null && expiryDate1.equalsIgnoreCase(dateformat.format(getDateExp()))) {

										boolean checkScanStatus = true;

										if (getFileScanStatus() != null && getFileScanStatus().trim().equalsIgnoreCase("Failure_V")) {
											if (getCheckImageReject()) {
												List<CustomerImageVerification> listVerification = icustomerRegistrationService.getImageStatus(
														getPkCustomerId(), getIdnumberProof());
												if (listVerification.size() > 0 && listVerification.get(0).getComplianceStatus() != null
														&& listVerification.get(0).getComplianceStatus().equalsIgnoreCase(Constants.REJECT_VALUE)) {
													checkScanStatus = false;
												} else {
													checkScanStatus = true;
												}
											} else {
												checkScanStatus = true;
											}
										} else if (getFileScanStatus() != null && getFileScanStatus().trim().equalsIgnoreCase("Failure_O")) {
											checkScanStatus = false;
										}

										if (checkScanStatus) {

											if (getFileScanStatus() != null && getFileScanStatus().trim().equalsIgnoreCase("Failure_V")) {
												List<CustomerImageVerification> listVerification = icustomerRegistrationService.getImageStatus(
														getPkCustomerId(), getIdnumberProof());
												if (listVerification.size() > 0 && listVerification.get(0).getComplianceStatus() != null
														&& listVerification.get(0).getComplianceStatus().equalsIgnoreCase(Constants.REJECT_VALUE)) {
													setComplianceStatus(Constants.PENDING_VALUE);
												}
												saveCustomerImageVerificationInfo();
											}

											boolean checkrecord = false;
											try {
												if (createProofList.size() > 0) {
													for (CreateProofTable addCustomerIdProofBean : createProofList) {
														checkrecord = false;
														if (getIdFor().compareTo(new BigDecimal(addCustomerIdProofBean.getId_for())) == 0) {
															if (getIdNumberScan().equalsIgnoreCase(addCustomerIdProofBean.getId_number())) {
																if (getIdTypeproof().equals(new BigDecimal(addCustomerIdProofBean.getId_type()))) {
																	if (new Date().before(new SimpleDateFormat("dd/MM/yy")
																	.parse(addCustomerIdProofBean.getDate_expiary()))
																	&& addCustomerIdProofBean.getScanReq() != null
																	&& addCustomerIdProofBean.getScanReq().equalsIgnoreCase(Constants.Yes)) {
																		addCustomerIdProofBean.setScanSystem(Constants.ScanInd_A);
																		addCustomerIdProofBean.setBooRenderAImageView(true);
																		addCustomerIdProofBean.setBooRenderDImageView(false);
																		addCustomerIdProofBean.setScanReq(null);
																		addCustomerIdProofBean.setModifiedByIdProof(sessionStateManage.getUserName());
																		addCustomerIdProofBean.setModifiedDateIdProof(getCurrentTime());
																		addCustomerIdProofBean.setBooCheckDup(false);
																		addCustomerIdProofBean.setRenderReScan(true);
																		addCustomerIdProofBean.setScanImage(null);
																		addCustomerIdProofBean.setCheckedScanned(false);
																		// dummiRecord
																		// =
																		// addCustomerIdProofBean;
																		checkrecord = true;
																		clearProofInfo();
																		if (!getSmartcardcheck()) {
																			setDateExp(null);
																		}
																		break;
																	}
																}
															}
														}
													}
												}

											} catch (Exception ex) {
												log.info("Customer Id proof duplicate check error: " + ex);
											}

											if (!checkrecord) {

												CreateProofTable custProof = new CreateProofTable();

												custProof.setId_for(getIdFor().toString());
												custProof.setId_type(getIdTypeproof().toString());
												custProof.setId_number(getIdNumberScan());
												custProof.setIdFor(idForMap.get(this.idFor));
												custProof.setIdType(idTypeMap.get(this.idTypeproof));

												if (scanexpireDate != null) {
													custProof.setDate_expiary(scanexpireDate);
													custProof.setExpiaryDate(new SimpleDateFormat("dd/MM/yyyy").parse(scanexpireDate));
												}

												if (getCustomerIDProofId() != null) {
													custProof.setCustomerIdProofId(getCustomerIDProofId());
													if (dummiCustomerIdProofList.size() != 0) {
														CreateProofTable lstdata = dummiCustomerIdProofList.get(0);
														custProof.setCreatedByIdProof(lstdata.getCreatedByIdProof());
														custProof.setCreatedDateIdProof(lstdata.getCreatedDateIdProof());
													}

												} else {
													custProof.setCreatedByIdProof(sessionStateManage.getUserName());
													custProof.setCreatedDateIdProof(getCurrentTime());
												}
												custProof.setScanSystem(Constants.ScanInd_A);

												custProof.setBooRenderAImageView(true);
												custProof.setBooRenderDImageView(false);
												custProof.setChecked(false);
												custProof.setRenderReScan(true);
												custProof.setCheckedScanned(false);
												custProof.setExistRecord(Constants.New_Record);
												createProofList.add(custProof);
												// setBooCheckDup(true);
												setBooidproofDatatable(true);
												setRenderModifyScan(false);
												/* setEnableCheckId(true); */
												setRenderSignature(true);
												clearProofInfo();
												if (!getSmartcardcheck()) {
													setDateExp(null);
												}
												// setDateExp(null);
											}

											// setDateExp(null);
										} else {
											scanNewCard();
											RequestContext.getCurrentInstance().execute("rescanagain.show();");
											return;
										}
									} else {

										if (getSmartcardcheck()) {
											try {
												scanNewCard();
											} catch (IOException e) {
												log.info("Sacn new Card Problem:" + e);
											}
											RequestContext.getCurrentInstance().execute("scanwithnewcard.show();");
										} else {
											try {
												scanNewCard();
											} catch (IOException e) {
												log.info("Sacn new Card Problem:" + e);
											}
											RequestContext.getCurrentInstance().execute("scanwithnewcardmanual.show();");
										}
									}
									/*
									 * }else{
									 * RequestContext.getCurrentInstance()
									 * .execute
									 * ("PF('scandatamismatch').show();"); }
									 */
								} else {
									RequestContext context = RequestContext.getCurrentInstance();
									context.execute("PF('duplicateproof').show();");
								}
							} else {
								setRenderSignature(false);
								scan();
								RequestContext context = RequestContext.getCurrentInstance();
								context.execute("PF('idExpired').show();");

							}
						}

					}
					in.close();
				} else {
					RequestContext.getCurrentInstance().execute("arcmateTable.show();");
				}

			} else if (listCountryBranch.size() > 0 && listCountryBranch.get(0).getScanInd() != null
					&& listCountryBranch.get(0).getScanInd().equalsIgnoreCase(Constants.ScanInd_N)) {

				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

				getFetchContactTypeList();

				String df = dateformat.format(getDateExp());

				if (getDateExp().compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {

					duplicate = duplicateCheck();
					if (duplicate) {
						CreateProofTable custProof = new CreateProofTable();

						custProof.setId_for(getIdFor().toString());
						custProof.setId_type(getIdTypeproof().toString());
						custProof.setId_number(getIdnumberProof());
						custProof.setIdFor(idForMap.get(this.idFor));
						custProof.setIdType(idTypeMap.get(this.idTypeproof));
						custProof.setScanReq(Constants.Yes);
						// if (scanexpireDate != null) {
						custProof.setDate_expiary(df);
						custProof.setExpiaryDate(getDateExp());
						// }

						if (getCustomerIDProofId() != null) {
							custProof.setCustomerIdProofId(getCustomerIDProofId());
							if (dummiCustomerIdProofList.size() != 0) {
								CreateProofTable lstdata = dummiCustomerIdProofList.get(0);
								custProof.setCreatedByIdProof(lstdata.getCreatedByIdProof());
								custProof.setCreatedDateIdProof(lstdata.getCreatedDateIdProof());
							}

						} else {
							custProof.setCreatedByIdProof(sessionStateManage.getUserName());
							custProof.setCreatedDateIdProof(getCurrentTime());
						}
						custProof.setScanSystem(Constants.ScanInd_N);

						custProof.setBooRenderAImageView(false);
						custProof.setBooRenderDImageView(false);
						custProof.setChecked(true);
						custProof.setCheckedScanned(false);
						custProof.setExistRecord(Constants.New_Record);
						createProofList.add(custProof);
						setBooidproofDatatable(true);
						setRenderModifyScan(false);
						setRenderSignature(true);
						// setBooCheckDup(true);
						clearProofInfo();
						if (!getSmartcardcheck()) {
							setDateExp(null);
						}
					} else {
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("PF('duplicateproof').show();");
					}
				}
			} else if (listCountryBranch.size() > 0 && listCountryBranch.get(0).getScanInd() != null
					&& listCountryBranch.get(0).getScanInd().equalsIgnoreCase(Constants.ScanInd_D)) {

				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

				getFetchContactTypeList();

				String df = dateformat.format(getDateExp());

				if (getDateExp().compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {

					duplicate = duplicateCheck();

					if (duplicate) {
						try {
							getDealYearbyDate();

							System.out.println("Start Image" + new Date().getTime());
							HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

							String ipAddress = request.getHeader("X-FORWARDED-FOR");
							if (ipAddress == null) {
								ipAddress = request.getRemoteAddr();
							}
							System.out.println(ipAddress);
							URL url = new URL("http://" + ipAddress + ":8085/Scan/Scan");

							// URL url = new
							// URL("http://localhost:8085/Scan/Scan");
							BufferedImage image = ImageIO.read(url);
							System.out.println("End Image" + new Date().getTime());
							/*
							 * HttpURLConnection httConn = null; BufferedReader
							 * in = null;
							 * 
							 * httConn = (HttpURLConnection)
							 * url.openConnection(); in = new BufferedReader(new
							 * InputStreamReader(httConn.getInputStream()));
							 * BufferedImage image = in.r String str =
							 * in.readLine();
							 */

							if (image == null) {

								RequestContext context = RequestContext.getCurrentInstance();
								context.execute("PF('imgNotFound').show();");
								return;
							}

							boolean checkrecord = false;
							// CreateProofTable dummiRecord = new
							// CreateProofTable();
							try {
								if (createProofList.size() > 0) {
									for (CreateProofTable addCustomerIdProofBean : createProofList) {
										checkrecord = false;
										if (getIdFor().compareTo(new BigDecimal(addCustomerIdProofBean.getId_for())) == 0) {
											if (getIdnumberProof().equalsIgnoreCase(addCustomerIdProofBean.getId_number())) {
												if (getIdTypeproof().equals(new BigDecimal(addCustomerIdProofBean.getId_type()))) {
													if (new Date().before(new SimpleDateFormat("dd/MM/yy").parse(addCustomerIdProofBean
															.getDate_expiary()))
															&& addCustomerIdProofBean.getScanReq() != null
															&& addCustomerIdProofBean.getScanReq().equalsIgnoreCase(Constants.Yes)) {
														addCustomerIdProofBean.setScanSystem(Constants.ScanInd_D);
														addCustomerIdProofBean.setBooRenderAImageView(false);
														addCustomerIdProofBean.setBooRenderDImageView(true);
														addCustomerIdProofBean.setScanReq(null);
														addCustomerIdProofBean.setModifiedByIdProof(sessionStateManage.getUserName());
														addCustomerIdProofBean.setModifiedDateIdProof(getCurrentTime());
														addCustomerIdProofBean.setBooCheckDup(false);
														// fetching image from
														// location
														String imgdata = imageToString(image, url.toString());
														// byte[] byteConent =
														// imgdata.getBytes();

														addCustomerIdProofBean.setScanImage(imgdata);
														addCustomerIdProofBean.setCheckedScanned(true);
														// dummiRecord =
														// addCustomerIdProofBean;
														populateImage(addCustomerIdProofBean);
														checkrecord = true;
														clearProofInfo();
														if (!getSmartcardcheck()) {
															setDateExp(null);
														}
														break;
													}
												}
											}
										}
									}
								}

							} catch (Exception ex) {
								log.info("Customer Id proof duplicate check error: " + ex);
							}

							if (!checkrecord) {

								CreateProofTable custProof = new CreateProofTable();

								custProof.setId_for(getIdFor().toString());
								custProof.setId_type(getIdTypeproof().toString());
								custProof.setId_number(getIdnumberProof());
								custProof.setIdFor(idForMap.get(this.idFor));
								custProof.setIdType(idTypeMap.get(this.idTypeproof));
								custProof.setDate_expiary(df);
								custProof.setExpiaryDate(getDateExp());

								if (getCustomerIDProofId() != null) {
									custProof.setCustomerIdProofId(getCustomerIDProofId());
									if (dummiCustomerIdProofList.size() != 0) {
										CreateProofTable lstdata = dummiCustomerIdProofList.get(0);
										custProof.setCreatedByIdProof(lstdata.getCreatedByIdProof());
										custProof.setCreatedDateIdProof(lstdata.getCreatedDateIdProof());
									}

								} else {
									custProof.setCreatedByIdProof(sessionStateManage.getUserName());
									custProof.setCreatedDateIdProof(getCurrentTime());
								}

								custProof.setScanSystem(Constants.ScanInd_D);

								custProof.setBooRenderAImageView(false);
								custProof.setBooRenderDImageView(true);

								// fetching image from location
								String imgdata = imageToString(image, url.toString());
								// byte[] byteConent = imgdata.getBytes();

								custProof.setScanImage(imgdata);
								custProof.setChecked(false);
								custProof.setCheckedScanned(true);
								custProof.setExistRecord(Constants.New_Record);
								createProofList.add(custProof);
								setBooidproofDatatable(true);
								setRenderModifyScan(false);
								setRenderSignature(true);
								// setBooCheckDup(true);
								clearProofInfo();

								// populate image automatically
								populateImage(custProof);
								if (!getSmartcardcheck()) {
									setDateExp(null);
								}
							}
							// }
						} catch (Exception ex) {
							log.info("Customer Id proof duplicate check error: " + ex);
						}
					} else {
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("PF('duplicateproof').show();");
					}
				}

			}
		} catch (MalformedURLException e) {
			log.info("Connection Problem: " + e);
		} catch (IOException ie) {
			log.info("Connection Problem: " + ie);
			RequestContext.getCurrentInstance().execute("machinenotconnected.show();");
		} catch (ParseException e1) {
			log.info("Arcmate Problem: " + e1);
			RequestContext.getCurrentInstance().execute("arcmateproblem.show();");
		}

	}

	public void scan() throws IOException, ParseException {

		if (getDob() != null) {

			String dobstr = new SimpleDateFormat("dd/MM/yyyy").format(getDob());
			String[] dateStr = dobstr.split("/");
			setDobS(dateStr[2] + dateStr[1] + dateStr[0]);

		}

		if (getBirthdate() != null) {

			String[] dateStr = getBirthdate().split("/");

			setDobS(dateStr[2] + dateStr[1] + dateStr[0]);
		}

		if (getShowDob() != null) {

			String[] dateStr = getShowDob().split("/");

			setDobS(dateStr[2] + dateStr[1] + dateStr[0]);

		}

		if (getDateExp() != null) {

			String dateExp = new SimpleDateFormat("dd/MM/yyyy").format(getDateExp());
			String[] dateStr = dateExp.split("/");
			setExpDateS(dateStr[2] + dateStr[1] + dateStr[0]);

		} else {
			// RequestContext.getCurrentInstance().execute("expDatemand.show();");
		}

		RequestContext.getCurrentInstance().execute("scanwait.show();");

	}

	public void scanNewCard() throws IOException, ParseException {

		if (getDob() != null) {

			String dobstr = new SimpleDateFormat("dd/MM/yyyy").format(getDob());
			String[] dateStr = dobstr.split("/");
			setDobS(dateStr[2] + dateStr[1] + dateStr[0]);

		}

		if (getBirthdate() != null) {

			String[] dateStr = getBirthdate().split("/");

			setDobS(dateStr[2] + dateStr[1] + dateStr[0]);
		}

		if (getShowDob() != null) {

			String[] dateStr = getShowDob().split("/");

			setDobS(dateStr[2] + dateStr[1] + dateStr[0]);

		}

		if (getDateExp() != null) {

			String dateExp = new SimpleDateFormat("dd/MM/yyyy").format(getDateExp());
			String[] dateStr = dateExp.split("/");
			setExpDateS(dateStr[2] + dateStr[1] + dateStr[0]);

		} else {
			// RequestContext.getCurrentInstance().execute("expDatemand.show();");
		}

	}

	@SuppressWarnings("static-access")
	public void redirectToScanningBrowser() {
		setCheckImageReject(false);
		StringBuffer urlBuffer = new StringBuffer();

		String sysDate = new SimpleDateFormat("dd/MM/yyyy").format(getCurrentTime());

		String[] dateStr = sysDate.split("/");

		String curDate = (dateStr[2] + dateStr[1] + dateStr[0]);

		BigDecimal civilIdNew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId()).getFsBizComponentData()
				.getComponentDataId();
		List<ArcmateScanMaster> arcmateList = icustomerRegistrationService.fetchArcmateMasterData(Constants.SCAN, Constants.OCR);
		List<ScanIdTypeMaster> scanIdList = null;
		if (getSmartcardcheck()) {
			scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(civilIdNew);
		} else {
			if (getSmartCardInd() != null && getSmartCardInd().equalsIgnoreCase(Constants.CUST_ACTIVE_INDICATOR)) {
				scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(civilIdNew);
			} else {
				scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(getIdTypeproof());
			}

		}

		if (arcmateList.size() != 0 && scanIdList.size() != 0) {
			ArcmateScanMaster arcmateValue = arcmateList.get(0);
			ScanIdTypeMaster scanIdValue = scanIdList.get(0);
			String rootContext = "http://";
			String idNo = getIdnumberProof();
			urlBuffer.append(rootContext).append(arcmateValue.getIpAddress().trim()).append("/").append(arcmateValue.getContextPath().trim());
			if (arcmateValue.getUrlParamField1() != null) {
				urlBuffer.append(arcmateValue.getUrlParamField1().trim()).append(arcmateValue.getFieldAssigner().trim()).append(idNo);
			}
			if (arcmateValue.getUrlParamField2() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField2().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(idNo);
			}
			if (arcmateValue.getUrlParamField3() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField3().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(getDobS());
			}
			if (arcmateValue.getUrlParamField4() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField4().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(getExpDateS());
			}
			if (arcmateValue.getUrlParamField5() != null && scanIdValue.getIdTypeValue() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField5().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getIdTypeValue());
			}
			if (arcmateValue.getUrlParamField6() != null) {
				String engName = null;
				if (getSmartcardcheck()) {
					engName = getFullNameEn();
				} else {
					if (getMiddleName() != null && getMiddleName().length() != 0) {
						engName = getFirstName().trim() + " " + getLastName().trim();
					} else {
						engName = getFirstName().trim() + " " + getMiddleName().trim() + " " + getLastName().trim();
					}
				}

				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField6().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(engName);
			}

			if (arcmateValue.getUrlParamField7() != null) {
				String localName = null;
				if (getSmartcardcheck()) {
					localName = getFullNameAr();
				} else {
					if (getMiddleNamel() != null && getMiddleNamel().length() != 0) {
						localName = getFirstNamel().trim() + " " + getLastNamel().trim();
					} else {
						localName = getFirstNamel().trim() + " " + getMiddleNamel().trim() + " " + getLastNamel().trim();
					}
				}

				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField7().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(localName);
			}

			if (arcmateValue.getUrlParamField8() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(getNationality());
			}

			if (arcmateValue.getUrlParamField9() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField9().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(getNationality());
			}

			if (arcmateValue.getUrlParamField10() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField10().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(getGender());
			}

			if (arcmateValue.getUrlParamField11() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField11().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(sessionStateManage.getUserName());
			}

			if (arcmateValue.getUrlParamField12() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField12().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(curDate);
			}

			if (arcmateValue.getUrlParamField13() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField13().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append("EMOS");
			}

			if (arcmateValue.getUrlParamField14() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField14().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(sessionStateManage.getBranchId());
			}

			if (arcmateValue.getUrlParamField15() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField15().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append("IP");
			}

			System.out.println("Add Document of  OCR URL :  " + urlBuffer.toString());
			log.info("Add Document of OCR URL :  " + urlBuffer.toString());

			try {

				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

				context.redirect(urlBuffer.toString());

			} catch (Exception e) {
				log.info("Problem to redirect: " + e);
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../common/errorscanning.xhtml");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		} else {
			RequestContext.getCurrentInstance().execute("arcmateTable.show();");
		}

	}

	@SuppressWarnings("static-access")
	public void viewScan(CreateProofTable createProofTable) {

		StringBuffer urlBuffer = new StringBuffer();

		String idNumberNocr = null;
		Boolean ocrid = false;
		mapIdentityType = ruleEngine.getComponentData("Identity Type");

		BigDecimal identityTpeId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
				identityTpeId = entry.getKey();
				break;
			}
		}

		BigDecimal drivingLicenceId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.LICENSE_NO)) {
				drivingLicenceId = entry.getKey();
				break;
			}
		}

		BigDecimal bedounId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.BEDOUIN)) {
				bedounId = entry.getKey();
				break;
			}
		}

		BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId();

		for (CreateProofTable createProof : createProofList) {

			if (createProof.getId_type() != null) {

				if (createProof.getId_type().equalsIgnoreCase(identityTpeId.toPlainString())
						|| createProof.getId_type().equalsIgnoreCase(idtypeCivilIdnew.toPlainString()) || bedounId != null
						&& createProof.getId_type().equalsIgnoreCase(bedounId.toPlainString())
						|| (drivingLicenceId != null && createProof.getId_type().equalsIgnoreCase(drivingLicenceId.toPlainString()))) {
					idNumberNocr = createProof.getId_number();
					ocrid = true;
					break;
				} else {
					ocrid = false;
				}
			}
		}

		List<ArcmateScanMaster> arcmateList = icustomerRegistrationService.fetchArcmateMasterData(Constants.VIEW, Constants.BOTH_VIEW);
		List<ScanIdTypeMaster> scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(new BigDecimal(createProofTable.getId_type()));

		if (arcmateList.size() != 0 && scanIdList.size() != 0) {
			ArcmateScanMaster arcmateValue = arcmateList.get(0);
			ScanIdTypeMaster scanIdValue = scanIdList.get(0);
			String rootContext = "http://";
			urlBuffer.append(rootContext).append(arcmateValue.getIpAddress().trim()).append("/").append(arcmateValue.getContextPath().trim());
			if (arcmateValue.getUrlParamField1() != null) {
				urlBuffer.append(arcmateValue.getUrlParamField1().trim());
			}
			if (arcmateValue.getUrlParamField2() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField2().trim());
			}
			if (arcmateValue.getUrlParamField3() != null && scanIdValue.getDocumentId() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField3().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getDocumentId());
			}

			if (arcmateValue.getUrlParamField4() != null && scanIdValue.getFolderId() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField4().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFolderId());
			}

			if (arcmateValue.getUrlParamField5() != null && scanIdValue.getUserName() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField5().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getUserName());
			}
			if (arcmateValue.getUrlParamField6() != null && scanIdValue.getPassword() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField6().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getPassword());
			}

			if (arcmateValue.getUrlParamField7() != null && scanIdValue.getFileCategoryId() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField7().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFileCategoryId());
			}

			if (ocrid) {

				if (arcmateValue.getUrlParamField8() != null) {
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim())
					.append(arcmateValue.getFieldAssigner().trim()).append(idNumberNocr);
				}

			} else {

				if (arcmateValue.getUrlParamField8() != null) {
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim())
					.append(arcmateValue.getFieldAssigner().trim()).append(createProofTable.getId_number());
				}

			}

			System.out.println("SCANNED VIEW URL :  " + urlBuffer.toString());
			log.info("SCANNED VIEW URL :  " + urlBuffer.toString());

			try {

				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

				context.redirect(urlBuffer.toString());

			} catch (Exception e) {

				log.info("Problem to redirect: " + e);
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../common/errorscanning.xhtml");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		} else {
			RequestContext.getCurrentInstance().execute("arcmateTable.show();");
		}

	}

	@SuppressWarnings("static-access")
	public void checkIdNonOcr() throws IOException, ParseException {

		ReadScanProperties readScan = new ReadScanProperties();

		boolean duplicate = duplicateCheck();
		// if (duplicate) {
		StringBuffer urlBuffer = new StringBuffer();

		String idNumberNocr = null;
		Boolean ocrd = false;
		Boolean checkFileId = true;
		mapIdentityType = ruleEngine.getComponentData("Identity Type");

		BigDecimal identityTpeId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
				identityTpeId = entry.getKey();
				break;
			}
		}

		BigDecimal drivingLicenceId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.LICENSE_NO)) {
				drivingLicenceId = entry.getKey();
				break;
			}
		}

		BigDecimal bedounId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.BEDOUIN)) {
				bedounId = entry.getKey();
				break;
			}
		}

		BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId();

		for (CreateProofTable createProofTable : createProofList) {

			if (createProofTable.getId_type() != null) {

				if (createProofTable.getId_type().equalsIgnoreCase(identityTpeId.toPlainString())
						|| createProofTable.getId_type().equalsIgnoreCase(idtypeCivilIdnew.toPlainString()) || bedounId != null
						&& createProofTable.getId_type().equalsIgnoreCase(bedounId.toPlainString())
						|| (drivingLicenceId != null && createProofTable.getId_type().equalsIgnoreCase(drivingLicenceId.toPlainString()))) {
					idNumberNocr = createProofTable.getId_number();
					ocrd = true;
					break;
				} else {
					ocrd = false;
				}
			} else {
				ocrd = false;
			}

			if (createProofTable.getDate_expiary() != null) {
				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
				if (dateformat.parse(createProofTable.getDate_expiary()).compareTo(dateformat.parse(dateformat.format(new Date()))) <= 0) {
					checkFileId = false;
				} else {
					checkFileId = true;
					break;
				}

			} else {
				checkFileId = true;
			}
		}

		List<ArcmateScanMaster> arcmateList = icustomerRegistrationService.fetchArcmateMasterData(Constants.CHECK_DOCUMENT, Constants.NON_OCR);
		List<ScanIdTypeMaster> scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(getIdTypeproof());

		// if(arcmateList.size() != 0 && scanIdList.size() != 0){
		ArcmateScanMaster arcmateValue = arcmateList.get(0);
		ScanIdTypeMaster scanIdValue = scanIdList.get(0);
		String rootContext = "http://";
		// String idNo = getIdnumberProof();
		urlBuffer.append(rootContext).append(arcmateValue.getIpAddress().trim()).append("/").append(arcmateValue.getContextPath().trim());
		if (arcmateValue.getUrlParamField1() != null) {
			urlBuffer.append(arcmateValue.getUrlParamField1().trim());
		}

		if (arcmateValue.getUrlParamField2() != null) {
			urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField2().trim());
		}

		if (arcmateValue.getUrlParamField3() != null && scanIdValue.getDocumentId() != null) {
			urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField3().trim())
			.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getDocumentId());
		}
		if (arcmateValue.getUrlParamField4() != null && scanIdValue.getUserName() != null) {
			urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField4().trim())
			.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getUserName());
		}
		if (arcmateValue.getUrlParamField5() != null && scanIdValue.getPassword() != null) {
			urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField5().trim())
			.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getPassword());
		}
		if (ocrd) {

			if (arcmateValue.getUrlParamField6() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField6().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(idNumberNocr);
			}

		} else {

			// CheckDocumentExistsScript Action
			if (arcmateValue.getUrlParamField6() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField6().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(getIdnumberProof());
			}

		}

		try {

			List<CountryBranch> listCountryBranch = generalService.getBranchDetailsFromBeneStatus(sessionStateManage.getCountryId(), new BigDecimal(
					sessionStateManage.getBranchId()));
			if (listCountryBranch.size() > 0 && listCountryBranch.get(0).getScanInd() != null
					&& listCountryBranch.get(0).getScanInd().equalsIgnoreCase(Constants.ScanInd_A)) {
				URL knetRequest = new URL(urlBuffer.toString());
				System.out.println("CheckId For Non OCR URL :  " + urlBuffer.toString());
				log.info("arcmate  CheckId For Non OCR URL :  " + urlBuffer.toString());
				HttpURLConnection httConn = null;
				BufferedReader in = null;

				httConn = (HttpURLConnection) knetRequest.openConnection();
				in = new BufferedReader(new InputStreamReader(httConn.getInputStream()));

				String str = in.readLine();

				if (str.equalsIgnoreCase("0")) {
					in.close();
					httConn.disconnect();
					RequestContext.getCurrentInstance().execute("adddocument.show();");

				} else if (str.startsWith("Doc ID")) {
					// CheckFileExistsScript

					String[] s1 = str.split("=");

					in.close();
					httConn.disconnect();

					StringBuffer urlBuffer1 = new StringBuffer();
					List<ArcmateScanMaster> arcmateListFile = icustomerRegistrationService.fetchArcmateMasterData(Constants.CHECK_FILE,
							Constants.NON_OCR);
					List<ScanIdTypeMaster> scanIdListFile = icustomerRegistrationService.fetchScanIdTypeMasterData(getIdTypeproof());

					if (arcmateListFile.size() != 0 && scanIdListFile.size() != 0) {
						ArcmateScanMaster arcmate = arcmateListFile.get(0);
						ScanIdTypeMaster scanId = scanIdListFile.get(0);
						urlBuffer1.append(rootContext).append(arcmate.getIpAddress().trim()).append("/").append(arcmate.getContextPath().trim());
						if (arcmate.getUrlParamField1() != null) {
							urlBuffer1.append(arcmate.getUrlParamField1().trim());
						}

						if (arcmate.getUrlParamField2() != null) {
							urlBuffer1.append(arcmate.getFieldSeprator().trim()).append(arcmate.getUrlParamField2().trim());
						}

						if (arcmate.getUrlParamField3() != null && scanId.getDocumentId() != null) {
							urlBuffer1.append(arcmate.getFieldSeprator().trim()).append(arcmate.getUrlParamField3().trim())
							.append(arcmate.getFieldAssigner().trim()).append(scanId.getDocumentId());
						}
						if (arcmate.getUrlParamField4() != null && scanId.getUserName() != null) {
							urlBuffer1.append(arcmate.getFieldSeprator().trim()).append(arcmate.getUrlParamField4().trim())
							.append(arcmate.getFieldAssigner().trim()).append(scanId.getUserName());
						}
						if (arcmate.getUrlParamField5() != null && scanId.getPassword() != null) {
							urlBuffer1.append(arcmate.getFieldSeprator().trim()).append(arcmate.getUrlParamField5().trim())
							.append(arcmate.getFieldAssigner().trim()).append(scanId.getPassword());
						}
						/*
						 * if (getIdTypeproof().intValue() ==
						 * generalService.getComponentId(Constants.PASSPORT,
						 * sessionStateManage.getLanguageId())
						 * .getFsBizComponentData
						 * ().getComponentDataId().intValue()) {
						 */
						if (arcmate.getUrlParamField6() != null && scanId.getFileCategoryId() != null) {
							urlBuffer1.append(arcmate.getFieldSeprator().trim()).append(arcmate.getUrlParamField6().trim())
							.append(arcmate.getFieldAssigner().trim()).append(scanId.getFileCategoryId());
						}
						if (arcmate.getUrlParamField7() != null) {
							urlBuffer1.append(arcmate.getFieldSeprator().trim()).append(arcmate.getUrlParamField7().trim())
							.append(arcmate.getFieldAssigner().trim()).append(s1[1]);
						}
					}

					try {

						System.out.println("File Exist For Non OCR URL :  " + urlBuffer1.toString());
						log.info("Arcmate File Exist For Non OCR URL :  " + urlBuffer1.toString());
						URL request = new URL(urlBuffer1.toString());
						HttpURLConnection httConn1 = null;
						BufferedReader in1 = null;

						httConn1 = (HttpURLConnection) request.openConnection();
						in1 = new BufferedReader(new InputStreamReader(httConn1.getInputStream()));

						String str1 = in1.readLine();

						if (str1.startsWith("File ID")) {
							setRenderModifyScan(true);

							SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

							getFetchContactTypeList();

							String df = dateformat.format(getDateExp());

							if (getExpDate().compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {

								duplicate = duplicateCheck();
								if (duplicate) {

									boolean checkrecord = false;
									try {
										if (createProofList.size() > 0) {
											for (CreateProofTable addCustomerIdProofBean : createProofList) {
												checkrecord = false;
												if (getIdFor().compareTo(new BigDecimal(addCustomerIdProofBean.getId_for())) == 0) {
													if (getIdnumberProof().equalsIgnoreCase(addCustomerIdProofBean.getId_number())) {
														if (getIdTypeproof().equals(new BigDecimal(addCustomerIdProofBean.getId_type()))) {
															if (new Date().before(new SimpleDateFormat("dd/MM/yy").parse(addCustomerIdProofBean
																	.getDate_expiary()))
																	&& addCustomerIdProofBean.getScanReq() != null
																	&& addCustomerIdProofBean.getScanReq().equalsIgnoreCase(Constants.Yes)) {
																addCustomerIdProofBean.setScanSystem(Constants.ScanInd_A);
																addCustomerIdProofBean.setBooRenderAImageView(true);
																addCustomerIdProofBean.setBooRenderDImageView(false);
																addCustomerIdProofBean.setScanReq(null);
																addCustomerIdProofBean.setModifiedByIdProof(sessionStateManage.getUserName());
																addCustomerIdProofBean.setModifiedDateIdProof(getCurrentTime());
																addCustomerIdProofBean.setBooCheckDup(false);
																addCustomerIdProofBean.setRenderReScan(true);
																addCustomerIdProofBean.setScanImage(null);
																addCustomerIdProofBean.setCheckedScanned(false);
																// dummiRecord =
																// addCustomerIdProofBean;
																checkrecord = true;
																clearProofInfo();
																setDateExp(null);
																break;
															}
														}
													}
												}
											}
										}

									} catch (Exception ex) {
										log.info("Customer Id proof duplicate check error: " + ex);
									}

									if (!checkrecord) {
										CreateProofTable custProof = new CreateProofTable();

										custProof.setId_for(getIdFor().toString());
										custProof.setId_type(getIdTypeproof().toString());
										custProof.setId_number(getIdnumberProof());
										custProof.setIdFor(idForMap.get(this.idFor));
										custProof.setIdType(idTypeMap.get(this.idTypeproof));

										custProof.setDate_expiary(df);
										custProof.setExpiaryDate(getDateExp());

										if (getCustomerIDProofId() != null) {
											custProof.setCustomerIdProofId(getCustomerIDProofId());
											if (dummiCustomerIdProofList.size() != 0) {
												CreateProofTable lstdata = dummiCustomerIdProofList.get(0);
												custProof.setCreatedByIdProof(lstdata.getCreatedByIdProof());
												custProof.setCreatedDateIdProof(lstdata.getCreatedDateIdProof());
											}

										} else {
											custProof.setCreatedByIdProof(sessionStateManage.getUserName());
											custProof.setCreatedDateIdProof(getCurrentTime());
										}
										custProof.setScanSystem(Constants.ScanInd_A);
										custProof.setBooRenderAImageView(true);
										custProof.setBooRenderDImageView(false);
										custProof.setChecked(false);
										custProof.setRenderReScan(true);
										custProof.setCheckedScanned(false);
										custProof.setExistRecord(Constants.New_Record);
										createProofList.add(custProof);
										setBooidproofDatatable(true);
										setRenderModifyScan(false);
										setRenderSignature(true);
										// setBooCheckDup(true);
										clearProofInfo();
										setDateExp(null);
									}

								} else {
									RequestContext context = RequestContext.getCurrentInstance();
									context.execute("PF('duplicateproof').show();");
								}
							} else {
								setRenderModifyScan(true);
								setRenderSignature(true);
								RequestContext context = RequestContext.getCurrentInstance();
								context.execute("PF('idExpirednocr').show();");

							}
						} else {
							setRenderModifyScan(true);
							RequestContext.getCurrentInstance().execute("filenotfounds.show();");

						}

					} catch (MalformedURLException e) {
						log.info("Connection Problem: " + e);
					} catch (IOException ie) {
						log.info("Connection Problem: " + ie);
						RequestContext.getCurrentInstance().execute("machinenotconnected.show();");
					}

				}

				in.close();
				httConn.disconnect();
			} else if (listCountryBranch.size() > 0 && listCountryBranch.get(0).getScanInd() != null
					&& listCountryBranch.get(0).getScanInd().equalsIgnoreCase(Constants.ScanInd_N)) {

				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

				getFetchContactTypeList();

				String df = dateformat.format(getDateExp());

				if (getExpDate().compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {

					duplicate = duplicateCheck();
					if (duplicate) {
						CreateProofTable custProof = new CreateProofTable();

						custProof.setId_for(getIdFor().toString());
						custProof.setId_type(getIdTypeproof().toString());
						custProof.setId_number(getIdnumberProof());
						custProof.setIdFor(idForMap.get(this.idFor));
						custProof.setIdType(idTypeMap.get(this.idTypeproof));
						custProof.setScanReq(Constants.Yes);

						custProof.setDate_expiary(df);
						custProof.setExpiaryDate(getDateExp());

						if (getCustomerIDProofId() != null) {
							custProof.setCustomerIdProofId(getCustomerIDProofId());
							if (dummiCustomerIdProofList.size() != 0) {
								CreateProofTable lstdata = dummiCustomerIdProofList.get(0);
								custProof.setCreatedByIdProof(lstdata.getCreatedByIdProof());
								custProof.setCreatedDateIdProof(lstdata.getCreatedDateIdProof());
							}

						} else {
							custProof.setCreatedByIdProof(sessionStateManage.getUserName());
							custProof.setCreatedDateIdProof(getCurrentTime());
						}

						custProof.setScanSystem(Constants.ScanInd_N);
						custProof.setBooRenderAImageView(false);
						custProof.setBooRenderDImageView(false);
						custProof.setChecked(true);
						custProof.setCheckedScanned(false);
						custProof.setExistRecord(Constants.New_Record);
						createProofList.add(custProof);

						setBooidproofDatatable(true);
						setRenderModifyScan(false);
						setRenderSignature(true);
						// setBooCheckDup(true);
						clearProofInfo();
						setDateExp(null);
					} else {
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("PF('duplicateproof').show();");
					}
				}

			} else if (listCountryBranch.size() > 0 && listCountryBranch.get(0).getScanInd() != null
					&& listCountryBranch.get(0).getScanInd().equalsIgnoreCase(Constants.ScanInd_D)) {

				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

				getFetchContactTypeList();

				String df = dateformat.format(getDateExp());

				if (getDateExp().compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {

					duplicate = duplicateCheck();

					if (duplicate) {
						getDealYearbyDate();

						HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

						String ipAddress = request.getHeader("X-FORWARDED-FOR");
						if (ipAddress == null) {
							ipAddress = request.getRemoteAddr();
						}
						System.out.println(ipAddress);
						URL url = new URL("http://" + ipAddress + ":8085/Scan/Scan");

						// URL url = new URL("http://localhost:8085/Scan/Scan");
						BufferedImage image = ImageIO.read(url);

						if (image == null) {

							RequestContext context = RequestContext.getCurrentInstance();
							context.execute("PF('imgNotFound').show();");
							return;
						}

						boolean checkrecord = false;
						// CreateProofTable dummiRecord = new
						// CreateProofTable();
						try {
							if (createProofList.size() > 0) {
								for (CreateProofTable addCustomerIdProofBean : createProofList) {
									checkrecord = false;
									if (getIdFor().compareTo(new BigDecimal(addCustomerIdProofBean.getId_for())) == 0) {
										if (getIdnumberProof().equalsIgnoreCase(addCustomerIdProofBean.getId_number())) {
											if (getIdTypeproof().equals(new BigDecimal(addCustomerIdProofBean.getId_type()))) {
												if (new Date()
												.before(new SimpleDateFormat("dd/MM/yy").parse(addCustomerIdProofBean.getDate_expiary()))
												&& addCustomerIdProofBean.getScanReq() != null
												&& addCustomerIdProofBean.getScanReq().equalsIgnoreCase(Constants.Yes)) {
													addCustomerIdProofBean.setScanSystem(Constants.ScanInd_D);
													addCustomerIdProofBean.setBooRenderAImageView(false);
													addCustomerIdProofBean.setBooRenderDImageView(true);
													addCustomerIdProofBean.setScanReq(null);
													addCustomerIdProofBean.setModifiedByIdProof(sessionStateManage.getUserName());
													addCustomerIdProofBean.setModifiedDateIdProof(getCurrentTime());
													addCustomerIdProofBean.setBooCheckDup(false);
													// fetching image from
													// location
													String imgdata = imageToString(image, url.toString());
													// byte[] byteConent =
													// imgdata.getBytes();
													addCustomerIdProofBean.setCheckedScanned(true);
													addCustomerIdProofBean.setScanImage(imgdata);
													// dummiRecord =
													// addCustomerIdProofBean;
													populateImage(addCustomerIdProofBean);
													checkrecord = true;
													clearProofInfo();
													setDateExp(null);
													break;
												}
											}
										}
									}
								}
							}

						} catch (Exception ex) {
							log.info("Customer Id proof duplicate check error: " + ex);
						}

						if (!checkrecord) {

							CreateProofTable custProof = new CreateProofTable();

							custProof.setId_for(getIdFor().toString());
							custProof.setId_type(getIdTypeproof().toString());
							custProof.setId_number(getIdnumberProof());
							custProof.setIdFor(idForMap.get(this.idFor));
							custProof.setIdType(idTypeMap.get(this.idTypeproof));
							custProof.setDate_expiary(df);
							custProof.setExpiaryDate(getDateExp());

							if (getCustomerIDProofId() != null) {
								custProof.setCustomerIdProofId(getCustomerIDProofId());
								if (dummiCustomerIdProofList.size() != 0) {
									CreateProofTable lstdata = dummiCustomerIdProofList.get(0);
									custProof.setCreatedByIdProof(lstdata.getCreatedByIdProof());
									custProof.setCreatedDateIdProof(lstdata.getCreatedDateIdProof());
								}

							} else {
								custProof.setCreatedByIdProof(sessionStateManage.getUserName());
								custProof.setCreatedDateIdProof(getCurrentTime());
							}

							custProof.setScanSystem(Constants.ScanInd_D);

							custProof.setBooRenderAImageView(false);
							custProof.setBooRenderDImageView(true);

							// fetching image from location
							String imgdata = imageToString(image, url.toString());
							// byte[] byteConent = imgdata.getBytes();

							custProof.setScanImage(imgdata);
							custProof.setChecked(false);
							custProof.setCheckedScanned(true);
							custProof.setExistRecord(Constants.New_Record);
							createProofList.add(custProof);
							setBooidproofDatatable(true);
							setRenderModifyScan(false);
							setRenderSignature(true);
							// setBooCheckDup(true);
							clearProofInfo();
							setDateExp(null);

							// populate image automatically
							populateImage(custProof);
						}
						// }
					} else {
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("PF('duplicateproof').show();");
					}
				}

			}
		} catch (MalformedURLException e) {
			log.info("Connection Problem: " + e);
		} catch (IOException ie) {
			log.info("Connection Problem: " + ie);
		}
		/*
		 * } else { RequestContext context =
		 * RequestContext.getCurrentInstance();
		 * context.execute("PF('duplicateproof').show();"); }
		 */

	}

	@SuppressWarnings("static-access")
	public void addDocumentBrowser() {

		StringBuffer urlBuffer = new StringBuffer();

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

		BigDecimal drivingLicenceId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.GCC_NATIONAL_ID)) {
				drivingLicenceId = entry.getKey();
				break;
			}
		}

		BigDecimal bedounId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.BEDOUIN)) {
				bedounId = entry.getKey();
				break;
			}
		}

		BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId();

		for (CreateProofTable createProofTable : createProofList) {

			if (createProofTable.getId_type() != null) {

				if (createProofTable.getId_type().equalsIgnoreCase(identityTpeId.toPlainString())
						|| createProofTable.getId_type().equalsIgnoreCase(idtypeCivilIdnew.toPlainString()) || bedounId != null
						&& createProofTable.getId_type().equalsIgnoreCase(bedounId.toPlainString())
						|| (drivingLicenceId != null && createProofTable.getId_type().equalsIgnoreCase(drivingLicenceId.toPlainString()))) {
					idNumberNocr = createProofTable.getId_number();
					nonocrid = true;
					break;
				} else {
					nonocrid = false;
				}
			} else {
				nonocrid = false;
			}
		}

		List<ArcmateScanMaster> arcmateList = icustomerRegistrationService.fetchArcmateMasterData(Constants.SCAN, Constants.NON_OCR);
		List<ScanIdTypeMaster> scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(getIdTypeproof());

		if (arcmateList.size() != 0 && scanIdList.size() != 0) {
			ArcmateScanMaster arcmateValue = arcmateList.get(0);
			ScanIdTypeMaster scanIdValue = scanIdList.get(0);
			String rootContext = "http://";
			urlBuffer.append(rootContext).append(arcmateValue.getIpAddress().trim()).append("/").append(arcmateValue.getContextPath().trim());
			if (arcmateValue.getUrlParamField1() != null) {
				urlBuffer.append(arcmateValue.getUrlParamField1().trim());
			}
			if (arcmateValue.getUrlParamField2() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField2().trim());
			}
			if (arcmateValue.getUrlParamField3() != null && scanIdValue.getDocumentId() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField3().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getDocumentId());
			}

			if (arcmateValue.getUrlParamField4() != null && scanIdValue.getFolderId() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField4().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFolderId());
			}

			if (arcmateValue.getUrlParamField5() != null && scanIdValue.getUserName() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField5().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getUserName());
			}
			if (arcmateValue.getUrlParamField6() != null && scanIdValue.getPassword() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField6().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getPassword());
			}

			if (nonocrid) {

				if (arcmateValue.getUrlParamField7() != null) {
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField7().trim())
					.append(arcmateValue.getFieldAssigner().trim()).append(idNumberNocr);
				}

				if (arcmateValue.getUrlParamField8() != null && scanIdValue.getFileCategoryId() != null) {
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim())
					.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFileCategoryId());
				}

			} else {

				if (arcmateValue.getUrlParamField7() != null) {
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField7().trim())
					.append(arcmateValue.getFieldAssigner().trim()).append(getIdnumberProof());
				}

				if (arcmateValue.getUrlParamField8() != null && scanIdValue.getFileCategoryId() != null) {
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim())
					.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFileCategoryId());
				}

			}

			System.out.println("Add Document of Non OCR URL :  " + urlBuffer.toString());
			log.info("Add Document of Non OCR URL :  " + urlBuffer.toString());

			try {

				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

				context.redirect(urlBuffer.toString());

			} catch (Exception e) {

				log.info("Problem to redirect: " + e);
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../common/errorscanning.xhtml");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		} else {

		}

	}

	public void modifyScan() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('modifyscan').show();");
	}

	@SuppressWarnings("static-access")
	public void modifyNonOcr() {

		setRenderModifyScan(false);

		StringBuffer urlBuffer = new StringBuffer();

		String idNumberNocr = null;
		Boolean ocrid = false;
		mapIdentityType = ruleEngine.getComponentData("Identity Type");

		BigDecimal identityTpeId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
				identityTpeId = entry.getKey();
				break;
			}
		}

		BigDecimal drivingLicenceId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.LICENSE_NO)) {
				drivingLicenceId = entry.getKey();
				break;
			}
		}

		BigDecimal bedounId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.BEDOUIN)) {
				bedounId = entry.getKey();
				break;
			}
		}

		BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId();

		for (CreateProofTable createProofTable : createProofList) {

			if (createProofTable.getId_type() != null) {

				if (createProofTable.getId_type().equalsIgnoreCase(identityTpeId.toPlainString())
						|| createProofTable.getId_type().equalsIgnoreCase(idtypeCivilIdnew.toPlainString()) || bedounId != null
						&& createProofTable.getId_type().equalsIgnoreCase(bedounId.toPlainString())
						|| (drivingLicenceId != null && createProofTable.getId_type().equalsIgnoreCase(drivingLicenceId.toPlainString()))) {
					idNumberNocr = createProofTable.getId_number();
					ocrid = true;
					break;
				} else {
					ocrid = false;
				}
			}
		}

		List<ArcmateScanMaster> arcmateList = icustomerRegistrationService.fetchArcmateMasterData(Constants.MODIFY, Constants.NON_OCR);
		List<ScanIdTypeMaster> scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(getIdTypeproof());

		if (arcmateList.size() != 0 && scanIdList.size() != 0) {
			ArcmateScanMaster arcmateValue = arcmateList.get(0);
			ScanIdTypeMaster scanIdValue = scanIdList.get(0);
			String rootContext = "http://";
			urlBuffer.append(rootContext).append(arcmateValue.getIpAddress().trim()).append("/").append(arcmateValue.getContextPath().trim());
			if (arcmateValue.getUrlParamField1() != null) {
				urlBuffer.append(arcmateValue.getUrlParamField1().trim());
			}
			if (arcmateValue.getUrlParamField2() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField2().trim());
			}
			if (arcmateValue.getUrlParamField3() != null && scanIdValue.getDocumentId() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField3().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getDocumentId());
			}

			if (arcmateValue.getUrlParamField4() != null && scanIdValue.getFolderId() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField4().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFolderId());
			}

			if (arcmateValue.getUrlParamField5() != null && scanIdValue.getUserName() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField5().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getUserName());
			}
			if (arcmateValue.getUrlParamField6() != null && scanIdValue.getPassword() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField6().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getPassword());
			}

			if (ocrid) {

				if (arcmateValue.getUrlParamField7() != null) {
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField7().trim())
					.append(arcmateValue.getFieldAssigner().trim()).append(idNumberNocr);
				}

				if (arcmateValue.getUrlParamField8() != null && scanIdValue.getFileCategoryId() != null) {
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim())
					.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFileCategoryId());
				}

			} else {

				if (arcmateValue.getUrlParamField7() != null) {
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField7().trim())
					.append(arcmateValue.getFieldAssigner().trim()).append(getIdnumberProof());
				}

				if (arcmateValue.getUrlParamField8() != null && scanIdValue.getFileCategoryId() != null) {
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim())
					.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFileCategoryId());
				}
			}

			System.out.println("Modify Document of Non OCR URL :  " + urlBuffer.toString());
			log.info("Modify Document of Non OCR URL :  " + urlBuffer.toString());

			try {

				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

				context.redirect(urlBuffer.toString());

			} catch (Exception e) {

				log.info("Problem to redirect: " + e);
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../common/errorscanning.xhtml");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		} else {
			RequestContext.getCurrentInstance().execute("arcmateTable.show();");
		}

	}

	private boolean duplicateCheck() {
		boolean dublicate = false;
		try {
			if (createProofList.size() > 0) {
				for (CreateProofTable addCustomerIdProofBean : createProofList) {

					if (getIdFor().compareTo(new BigDecimal(addCustomerIdProofBean.getId_for())) == 0 && !addCustomerIdProofBean.isBooCheckDup()) {
						if (getIdnumberProof().equalsIgnoreCase(addCustomerIdProofBean.getId_number())) {
							if (getIdTypeproof().equals(new BigDecimal(addCustomerIdProofBean.getId_type()))) {
								SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
								if (getCurrentTime().before(dateformat.parse(addCustomerIdProofBean.getDate_expiary()))) {
									// if
									// (getDateExp().before(dateformat.parse(addCustomerIdProofBean.getDate_expiary())))
									// {

									if (dateformat.parse(dateformat.format(getDateExp())).compareTo(
											dateformat.parse(addCustomerIdProofBean.getDate_expiary())) == 0) {
										dublicate = false;
										break;
									} else if (getDateExp().before(dateformat.parse(addCustomerIdProofBean.getDate_expiary()))) {
										dublicate = false;
										break;

									} else {
										dublicate = true;
									}

									/*
									 * } else { dublicate = true; }
									 */
								} else {
									dublicate = true;
								}
							} else {
								dublicate = true;
							}

						} else {
							dublicate = true;
						}
					} else {
						dublicate = true;
					}
				}
			} else {
				dublicate = true;
			}

		} catch (Exception ex) {
			log.info("Customer Id proof duplicate check error: " + ex);
		}
		return dublicate;

	}

	/*
	 * method is responsible foe Clear Customer Proof Information
	 */
	public void clearProofInfo() {

		setIdTypeproof(null);
		setIdnumberProof("");
		setIdFor(null);

	}

	public void onDateSelect(SelectEvent event) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		setExpDate(format.parse(format.format(event.getObject())));

	}

	public Boolean nextSignature() {

		boolean returnCheck = false;
		try {
			boolean verify = true;
			boolean matchIdExpire = true;
			for (CreateProofTable createProofTable : createProofList) {

				if (createProofTable.getDate_expiary() != null) {
					SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
					if (dateformat.parse(createProofTable.getDate_expiary()).compareTo(dateformat.parse(dateformat.format(new Date()))) < 0) {
						verify = false;
					} else {
						verify = true;
						break;
					}

				}
			}

			for (CreateProofTable createProofTable : createProofList) {

				if (createProofTable.getDate_expiary() != null && getDateExp() != null) {
					SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
					// if(dateformat.parse(createProofTable.getDate_expiary()).compareTo(dateformat.parse(dateformat.format(new
					// Date()))) >= 0)
					if (dateformat.parse(createProofTable.getDate_expiary()).compareTo(dateformat.parse(dateformat.format(getDateExp()))) < 0) {
						matchIdExpire = false;
					} else {
						matchIdExpire = true;
						break;
					}

				} else {
					matchIdExpire = true;
				}
			}

			if (verify == true) {

				if (matchIdExpire) {

					int i = 0;
					int j = 1;
					BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId())
							.getFsBizComponentData().getComponentDataId();
					BigDecimal idtypeCivilId = generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId())
							.getFsBizComponentData().getComponentDataId();
					for (CreateProofTable createProofTable : createProofList) {
						if (getIdType() != null && getIdType().equalsIgnoreCase(createProofTable.getId_type())) {
							i = 1;
							break;
						} else if (createProofTable.getId_type() != null
								&& (createProofTable.getId_type().equalsIgnoreCase(idtypeCivilIdnew.toString()) || createProofTable.getId_type()
										.equalsIgnoreCase(idtypeCivilId.toString()))) {
							i = 1;
							break;

						} else {
							i = 0;
						}
					}

					if (i == 1) {
						j = 1;
						if (getBooChangeDob()) {

							for (CreateProofTable data : createProofList) {

								if (data.getId_for().equalsIgnoreCase(
										generalService.getComponentId(Constants.CHANGE_DOB, sessionStateManage.getLanguageId())
										.getFsBizComponentData().getComponentDataId().toString())) {
									j = 1;
									break;
								} else {
									j = 0;
								}
							}
						}

						if (j == 1) {

							BigDecimal articleId = null;
							Boolean signaturecheck = false;

							if (getPkCustomerId() != null) {
								List<Customer> custlist = icustomerRegistrationService.getCustomerInfo(pkCustomerId);
								if (custlist.size() != 0) {
									Customer customerlst = custlist.get(0);
									articleId = customerlst.getFsArticleDetails().getArticleDetailId();

									// if (customerlst.getSignatureSpecimen() ==
									// null) { //Commented by Rabil

									if (customerlst.getSignatureSpecimenClob() == null) {
										signaturecheck = false;
									} else {
										signaturecheck = true;
									}
								}

								if (signaturecheck) {
									if (articleId != null) {
										setSignatureCaptureRender(false);
										setSignatureSpecimenRender(true);
										returnCheck = true;
									} else {
										setSignatureCaptureRender(true);
										setSignatureSpecimenRender(false);
										returnCheck = true;
									}
								} else {
									setSignatureCaptureRender(true);
									setSignatureSpecimenRender(false);
									returnCheck = true;
								}

							} else {
								setSignatureCaptureRender(true);
								setSignatureSpecimenRender(false);
								returnCheck = true;
							}

						} else {
							returnCheck = false;
							RequestContext.getCurrentInstance().execute("PF('dobchangemsg').show();");

						}
					} else {
						returnCheck = false;
						RequestContext.getCurrentInstance().execute("PF('idtypecheck').show();");
					}
				} else {
					returnCheck = false;
					List<CountryBranch> listCountryBranch = generalService.getBranchDetailsFromBeneStatus(sessionStateManage.getCountryId(),
							new BigDecimal(sessionStateManage.getBranchId()));
					if (listCountryBranch.size() > 0 && listCountryBranch.get(0).getScanInd() != null
							&& listCountryBranch.get(0).getScanInd().equalsIgnoreCase(Constants.ScanInd_A)) {
						if (getSmartcardcheck()) {
							try {
								scanNewCard();
							} catch (IOException e) {
								log.info("Sacn new Card Problem:" + e);
							}
							RequestContext.getCurrentInstance().execute("scanwithnewcard.show();");
						} else if (listCountryBranch.size() > 0 && listCountryBranch.get(0).getScanInd() != null
								&& listCountryBranch.get(0).getScanInd().equalsIgnoreCase(Constants.ScanInd_D)) {
							try {
								scanNewCard();
							} catch (IOException e) {
								log.info("Sacn new Card Problem:" + e);
							}
							RequestContext.getCurrentInstance().execute("scanwithnewcardmanualsave.show();");
						}
					} else {
						RequestContext.getCurrentInstance().execute("needscan.show();");
					}
				}
			} else {
				returnCheck = false;
				setRenderModifyScan(false);
				RequestContext.getCurrentInstance().execute("idExpireddatatable.show();");
			}

		} catch (ParseException e) {
			returnCheck = false;
			e.printStackTrace();
		}
		return returnCheck;

	}

	public void saveCustomerIdProofPage() {
		setErrorMsg(null);
		try {
			if (nextSignature()) {
				boolean verify = true;
				for (CreateProofTable createProofTable : createProofList) {

					if (createProofTable.getScanReq() != null && createProofTable.getScanReq().equalsIgnoreCase(Constants.Scan_Req_Y)
							&& !createProofTable.getChecked()) {
						verify = false;
					} else {
						verify = true;
						break;
					}
				}
				if (verify) {
					try {
						saveCustomerScanDetails();
						String errMsg=icustomerRegistrationService.callProcedureUpdateCustomerFromIdProof(getPkCustomerId());
						if(errMsg!=null && !errMsg.equalsIgnoreCase(""))
						{
							setErrorMsg(errMsg);
							RequestContext.getCurrentInstance().execute("saveerror.show();");
							return;
						}
						callProcedureForOldUpdate();
						gotoCustomerDigitalSignaturePanel();
					} catch (AMGException ae) {
						log.error("Error saving in bloberror: " + ae);
						setErrorMsg(ae.getMessage());
						RequestContext.getCurrentInstance().execute("bloberror.show();");
					} catch (Exception e) {
						log.error("Error saving in Id Proof: " + e);
						setErrorMsg(e.getMessage());
						RequestContext.getCurrentInstance().execute("saveerror.show();");
					}
				} else {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('scanmust').show();");
				}

			} else {
				setBooProof(true);
				setSignaturePanelRender(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gotoCustomerDigitalSignaturePanel() {
		setBooProof(false);
		setSignaturePanelRender(true);
		BigDecimal articleId = null;
		Boolean signaturecheck = false;
		setBooRenderOtpPanel(false);
		setBooRenderOtpAuthPanel(false);

		if (getPkCustomerId() != null) {
			List<Customer> custlist = icustomerRegistrationService.getCustomerInfo(getPkCustomerId());
			if (custlist.size() != 0) {
				Customer customerlst = custlist.get(0);

				articleId = customerlst.getFsArticleDetails().getArticleDetailId();

				if (customerlst.getSignatureSpecimenClob() == null) {
					signaturecheck = false;
				} else {
					signaturecheck = true;

					if (customerList.get(0).getSignatureSpecimenClob() != null) {
						try {
							setDigitalSignature(customerList.get(0).getSignatureSpecimenClob()
									.getSubString(1, (int) customerList.get(0).getSignatureSpecimenClob().length()));
						} catch (SQLException e) {
							e.printStackTrace();
						}// clobToString(customerList.get(0).getSignatureSpecimenClob()));
					}
				}
			}

			// checking whether Signature required for Cooperate Users
			Boolean corsSigIndCheck =  iPersonalRemittanceService.checkCorporateBranchForSignature(new BigDecimal(sessionStateManage.getBranchId()));		

			if(corsSigIndCheck){
				setSignatureCaptureRender(false);
				setSignatureSpecimenRender(true);
				setRenFirstSuccess(false);
				setRenSecondSuccess(true);
			}else{
				if (signaturecheck) {
					if (articleId != null) {
						setSignatureCaptureRender(false);
						setSignatureSpecimenRender(true);
						setRenFirstSuccess(false);
						setRenSecondSuccess(true);
					} else {
						setSignatureCaptureRender(true);
						setSignatureSpecimenRender(false);
						setRenFirstSuccess(true);
						setRenSecondSuccess(false);
					}
				} else {
					setSignatureCaptureRender(true);
					setSignatureSpecimenRender(false);
					setRenFirstSuccess(true);
					setRenSecondSuccess(false);
				}
			}
			// otp based on condition
			populateCustomerOtpPanel(getPkCustomerId());

		} else {
			setSignatureCaptureRender(true);
			setSignatureSpecimenRender(false);
			setRenFirstSuccess(true);
			setRenSecondSuccess(false);
		}
	}

	// This method for local testing purpose
	public void checkId_localTest() throws IOException, ParseException {

		boolean duplicate = duplicateCheck();

		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

		getFetchContactTypeList();

		String df = dateformat.format(getDateExp());

		duplicate = duplicateCheck();
		if (duplicate) {
			CreateProofTable createProofTable = new CreateProofTable(getIdFor().toPlainString(), getIdTypeproof().toPlainString(),
					getIdnumberProof(), df, "insert", 0, idForMap.get(this.idFor), idTypeMap.get(this.idTypeproof));
			createProofTable.setCustomerIdProofId(null);

			createProofList.add(createProofTable);
			setBooidproofDatatable(true);
			/* setEnableCheckId(true); */
			setRenderSignature(true);
			clearProofInfo();
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('duplicateproof').show();");
		}

	}

	public String finalSave() throws AMGException, JRException, IOException {

		if(getBooRenderOtpPanel())
		{
			// check authorized by correct

			if(getOtpNo() == null && getOtpAuthorizedBy() == null && (getOtpAuthorizedPassword() == null || getOtpAuthorizedPassword().equalsIgnoreCase("")))
			{
				RequestContext.getCurrentInstance().execute("otpAuthorize.show();");
				return "";
			}

			if(getOtpNo() != null){
				//boolean checkAlreadyVerfied = icustomerRegistrationService.checkOtpVerified(getPkCustomerId());
				String rtnMessage = icustomerRegistrationService.verifyOtpNo(getOtpNo(), getPkCustomerId(), sessionStateManage.getUserName());

				if(rtnMessage!=null)
				{
					if(rtnMessage.equalsIgnoreCase("MATCH"))
					{
						// continue - still verification not done
					}else{
						if(getOtpAuthorizedBy() == null)
						{
							RequestContext.getCurrentInstance().execute("authorizedByEmpty.show();");
							return "";
						} else if(getOtpAuthorizedPassword() == null || getOtpAuthorizedPassword().equalsIgnoreCase(""))
						{
							RequestContext.getCurrentInstance().execute("authorizedPasswordEmpty.show();");
							return "";
						}else if (getOtpAuthorizedBy() != null && getOtpAuthorizedPassword() != null) {
							List<DebitAutendicationView> lstEmpLogin = new ArrayList<DebitAutendicationView>();

							lstEmpLogin = 	iPersonalRemittanceService.getdebitAutendicationListByUserId(getOtpAuthorizedBy(),getOtpAuthorizedPassword());				

							if (lstEmpLogin.size() != 0) {
								// continue
							} else {
								setOtpAuthorizedPassword(null);
								RequestContext.getCurrentInstance().execute("passwordcheck.show();");
								return "";
							}
						}
					}
				}else{
					RequestContext.getCurrentInstance().execute("otpAuthorize.show();");
					return "";
				}
			}else {
				if(getOtpAuthorizedBy() == null)
				{
					RequestContext.getCurrentInstance().execute("authorizedByEmpty.show();");
					return "";
				} else if(getOtpAuthorizedPassword() == null || getOtpAuthorizedPassword().equalsIgnoreCase(""))
				{
					RequestContext.getCurrentInstance().execute("authorizedPasswordEmpty.show();");
					return "";
				}else if (getOtpAuthorizedBy() != null && getOtpAuthorizedPassword() != null) {
					List<DebitAutendicationView> lstEmpLogin = new ArrayList<DebitAutendicationView>();

					lstEmpLogin = 	iPersonalRemittanceService.getdebitAutendicationListByUserId(getOtpAuthorizedBy(),getOtpAuthorizedPassword());				

					if (lstEmpLogin.size() != 0) {
						// continue - verification done user name and password matching 
					} else {
						setOtpAuthorizedPassword(null);
						RequestContext.getCurrentInstance().execute("passwordcheck.show();");
						return "";
					}
				}
			}		
		}

		String rtnStrin = "";

		// checking whether Signature required for Cooperate Users
		//Boolean corsSigIndCheck =  iPersonalRemittanceService.checkCorporateBranchForSignature(new BigDecimal(sessionStateManage.getBranchId()));		

		/*if(corsSigIndCheck){
			updateCustomer();
			setCustomerIDProofId(null);
			rtnStrin = "customerRegistrationSucessPage";
		}else{
			if (getDigitalSignature() != null && getDigitalSignature().length() != 0) {
				updateCustomer();
				setCustomerIDProofId(null);
				rtnStrin = "customerRegistrationSucessPage";
			} else {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('signatureMandatory').show();");
			}
		}*/

		if(!getSignatureCaptureRender()){
			if(getBooRenderOtpAuthPanel()){
				saveAuthorization();
			}
			updateCustomer();
			setCustomerIDProofId(null);
			rtnStrin = "customerRegistrationSucessPage";
		}else{
			if (getDigitalSignature() != null && getDigitalSignature().length() != 0) {
				if(getBooRenderOtpAuthPanel()){
					saveAuthorization();
				}
				updateCustomer();
				setCustomerIDProofId(null);
				rtnStrin = "customerRegistrationSucessPage";
			} else {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('signatureMandatory').show();");
			}
		}

		if (getIsfromCorporatePage() != null && getIsfromCorporatePage().equals(true)) {
			setIsfromCorporatePage(true);
			setIsFromCustomer(false);
			setIsFromWesternUnion(false);
		} else if(getIsFromWesternUnion() != null && getIsFromWesternUnion().equals(true)){
			setIsfromCorporatePage(false);
			setIsFromCustomer(false);
			setIsFromWesternUnion(true);
		} else if (getIsFromCustomer() != null && getIsFromCustomer().equals(true)) {
			setIsfromCorporatePage(false);
			setIsFromCustomer(true);
			setIsFromWesternUnion(false);
		} else{
			setIsfromCorporatePage(false);
			setIsFromCustomer(true);
			setIsFromWesternUnion(false);
		}

		return rtnStrin;
	}

	public void updateCustomer() throws AMGException, JRException, IOException {
		try {
			//icustomerRegistrationService.updateCustomer(getPkCustomerId(), getDigitalSignature());
			icustomerRegistrationService.updateCustomerSignOTP(getPkCustomerId(), getDigitalSignature(), getOtpNo(), getOtpAuthorizedBy(), getOtpRemarks());
			List<String> outPutList = icustomerRegistrationService.callProcedureUpdate(getPkCustomerId());

			if (outPutList.size() > 0) {
				if (outPutList != null && outPutList.get(0).equalsIgnoreCase(Constants.Yes)) {
					setBooProof(false);
					setSignaturePanelRender(true);
					RequestContext.getCurrentInstance().execute("migrationexception.show();");
					return;
				}
			}
			setBooProof(false);
			setSignaturePanelRender(false);

			if (getPepsindicator() != null && getPepsindicator().equalsIgnoreCase(Constants.Yes)) {
				setBooPepDescriptionReport(true);
			} else {
				setBooPepDescriptionReport(false);
			}
			// FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerRegistrationSucessPage.xhtml");
			// generateCustomerRegistrationReport();

		} catch (AMGException e) {
			log.error("Exception Occured While migration Data", e);
			setBooProof(false);
			setSignaturePanelRender(true);
			RequestContext.getCurrentInstance().execute("migrationexception.show();");
		}
	}

	public void getIDTypeProofValue(AjaxBehaviorEvent event) throws ParseException {
		setSelectedIdType(getIdTypeproof().toPlainString());
		setBooIdTypeCheck(false);

		if (getIdTypeproof().intValue() == new BigDecimal(getIdType()).intValue()) {
			setIdnumberProof(getIdNumber());
			setReadOnlyIdNumber(true);
		} else {
			setIdnumberProof(null);
			setReadOnlyIdNumber(false);
		}

		if (getSelectType() != null
				&& !getSelectType().equalsIgnoreCase(
						generalService.getComponentId(Constants.METHODTYPE, sessionStateManage.getLanguageId()).getFsBizComponentData()
						.getComponentDataId().toPlainString())) {
			setDateExp(new SimpleDateFormat("dd/MM/yyyy").parse(getExpirydate()));

		}

		String validity = generalService.getValidity(sessionStateManage.getCountryId(), new BigDecimal(getSelectedIdType()));

		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, new Integer(validity));
		Date today90 = cal.getTime();
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
		String finalDate = form.format(today90);
		setExpDateCheck(finalDate);
		String finalsDate = form.format(new Date());
		setMinDob(finalsDate);
		if (getSmartcardcheck()) {
			if (getIdTypeproof() != null
					&& getIdTypeproof().intValue() == generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId())
					.getFsBizComponentData().getComponentDataId().intValue()) {
				setRenderIdExpiryDateInputField(true);
				setRenderIdExpiryDateCalenderField(false);
				setDateExp(new SimpleDateFormat("dd/MM/yyyy").parse(getExpirydate()));
			} else {
				setRenderIdExpiryDateInputField(false);
				setRenderIdExpiryDateCalenderField(true);
				setDateExp(null);
			}
		}

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
		BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId();

		if (getIdTypeproof() != null && getIdTypeproof().intValue() == identityTpeId.intValue()
				|| getIdTypeproof().intValue() == idtypeCivilIdnew.intValue()) {

			boolean status = false;
			try {
				String returnString = generalService.getCivilIdStatus(new BigDecimal(getIdnumberProof()));
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

	/**
	 * This method going to back Page
	 * 
	 * @throws IOException
	 * 
	 */
	public void backScanPage() throws IOException {
		setCustomerIdProof();
		setBooProof(true);
		setSignaturePanelRender(false);
		setBooidproofDatatable(true);
		setRenderSignature(false);
		clearOtpDetails();
	}

	/**
	 * This method going to back Page
	 * 
	 * @throws IOException
	 * 
	 */
	public String backEmploymnetDetails() throws IOException {
		populateArticalDetails();
		popEmpState();
		populateRelationShip();
		clearSponsor();
		fetchSponsorDetails();
		fetchEmploymentInfo();
		setCustnormalImage(null);
		return "customerEmployeeInfoPage";
	}

	public void populateArticalDetails() {
		HashMap<String, BigDecimal> mapArtcile = icustomerRegistrationService.getCustomerArticleInfo(getPkCustomerId());
		articleData = branchpageService.getArtilces(sessionStateManage.getCountryId(), sessionStateManage.getLanguageId());
		if (articleData.size() > 0) {
			setArticleData(articleData);
		}
		setArticle(mapArtcile.get("ArticalMasterID"));
		levelData = branchpageService.getLevelData(getArticle(), sessionStateManage.getLanguageId());
		if (levelData.size() > 0) {
			setLevelData(levelData);
		}
		setLevel(mapArtcile.get("ArticalDetailsID"));
		lstIncomeRange = generalService.getIncomeRange(sessionStateManage.getCountryId(), getLevel());
		if (lstIncomeRange.size() > 0) {
			setLstIncomeRange(lstIncomeRange);
		}
		setIncomeRange(mapArtcile.get("IncomerangeID"));
	}

	// ////////////////////////////////////////////////////////Report Generation
	// Code Start
	// ///////////////////////////////////////////////////////////////////

	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;

	private JasperPrint jasperPrint;
	private List<PepReport> pepReportList;
	private List<CustomerLogReport> customerLogReportList;
	private List<CustomerMainReport> customerRegistrationReportList;
	private List<CustomerContactDetailView> customerContactDetailList;
	private List<CustomerEmployeeInfoView> customerEmployeeInfoList;
	private List<CustomerIdproofView> customerIdProofviewList;
	private List<CustomerMainReport> sponsorList;

	private List<CustomerInfoView> customerRegistrationList;
	private List<CustomerContactDetailView> customerContactDetail;
	private List<CustomerEmployeeInfoView> customerEmployeeInfo;
	private List<CustomerIdproofView> customerIdProof;
	private List<CustomerChangeLog> customerChangeLogList;
	
	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname.trim().concat(" ");
	}

	public void pepreportGeneration() {

		customerIdProofviewList = new CopyOnWriteArrayList<CustomerIdproofView>();
		//String customerFirstName = icustomerRegistrationService.fetchCustomerNameBasedonCustomerId(getPkCustomerId());
		
		List<Customer> lstcustomer = icustomerRegistrationService.getCustomerInfo(getPkCustomerId());
		
		String customerName = null;
		if(lstcustomer != null && lstcustomer.size() != 0){
			customerName = nullCheck(getFirstName()) + nullCheck(getMiddleName()) + nullCheck(getLastName());
		}
		
		
		customerIdProof = icustomerRegistrationService.findCustomerIdProof(getPkCustomerId());
		CustomerIdproofView customerIdProofObject = customerIdProof.get(0);
		pepReportList = new CopyOnWriteArrayList<PepReport>();
		PepReport pepReport = new PepReport();
		pepReport.setCivilId(customerIdProofObject.getIdProofInt());
		pepReport.setCountry(specialCustomerDealRequestService.getBankCountryNameForUpdate(sessionStateManage.getCountryId(),
				sessionStateManage.getLanguageId()));
		pepReport.setCountryBranch(sessionStateManage.getLocation());
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

		//String logoPath = realPath + logoPathDyn;
		pepReport.setLogoPath(logoPath);
		pepReport.setCustomerName(customerName);
		if (customerIdProofObject.getIdProofExpiredDate() != null) {
			pepReport.setValidUpto(new SimpleDateFormat("dd/MM/yyyy").format(customerIdProofObject.getIdProofExpiredDate()));
		}
		
		pepReport.setPepDeclarationBody("   "+"I,"+" "+ customerName +" "+Constants.PepDeclarationBody+" "+customerIdProofObject.getIdProofInt()+" "+" valid upto"+" "+pepReport.getValidUpto()+" "+"," + Constants.PepDeclarationBody1);
		pepReport.setPepNoteBody(Constants.PepNoteBody1);
		
		pepReportList.add(pepReport);
	}

	public void pepReportDataSource() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(pepReportList);
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/PEPDeclarationReport.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}

	public void generatePepDeclarationReport(ActionEvent actionEvent) throws JRException, IOException {
		ServletOutputStream servletOutputStream = null;
		try {
			pepreportGeneration();
			pepReportDataSource();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=PEPDeclarationReport.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		} finally {
			if (servletOutputStream != null) {
				servletOutputStream.close();
			}
		}

	}

	public void customerLogDefferenceDataSource() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(customerLogReportList);
		String reportPath = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("reports/design/customerlogdifferencenewreport.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}

	/*
	 * public void generateLogdifferenceReport(ActionEvent actionEvent) throws
	 * JRException, IOException{
	 * fetchUpdateLogInfo(getPkCustomerId(),getVerifyToken());
	 * customerLogDefferenceDataSource(); HttpServletResponse
	 * httpServletResponse
	 * =(HttpServletResponse)FacesContext.getCurrentInstance()
	 * .getExternalContext().getResponse();
	 * httpServletResponse.addHeader("Content-disposition",
	 * "attachment; filename=CustomerLogDifferenceReport.pdf");
	 * ServletOutputStream
	 * servletOutputStream=httpServletResponse.getOutputStream();
	 * JasperExportManager.exportReportToPdfStream(jasperPrint,
	 * servletOutputStream);
	 * FacesContext.getCurrentInstance().responseComplete();
	 * if(getPkCustomerId()!=null){ updateVerificationToken(getPkCustomerId());
	 * } }
	 */

	public void fetchUpdateLogInfo(BigDecimal customerId, String verificationToken) {
		customerLogReportList = new CopyOnWriteArrayList<CustomerLogReport>();
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String logoPath = realPath + Constants.LOGO_PATH;

		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}



		customerChangeLogList = icustomerRegistrationService.findCustomerChangeLog(customerId, verificationToken);
		for (CustomerChangeLog customerChangeLog : customerChangeLogList) {
			CustomerLogReport customerLogReport = new CustomerLogReport();
			customerLogReport.setFromColumnName(customerChangeLog.getFromColumnName());
			customerLogReport.setFromTableName(customerChangeLog.getFromTableName());
			customerLogReport.setNewValue(customerChangeLog.getNewValue());
			customerLogReport.setOldValue(customerChangeLog.getOldValue());
			customerLogReport.setLogoPath(logoPath);
			customerLogReportList.add(customerLogReport);
		}
	}

	public void customerRegistrationDataSource() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(customerRegistrationReportList);
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/CustomerRegistrationNewReport.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}

	public void generateCustomerRegistrationReport() throws JRException, IOException {
		ServletOutputStream servletOutputStream = null;
		try {
			fetchCustomerRegistrationInfo(getPkCustomerId());
			customerRegistrationDataSource();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=CustomerRegistrationReport.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		} finally {
			if (servletOutputStream != null) {
				servletOutputStream.close();
			}
		}

	}

	public void fetchCustomerRegistrationInfo(BigDecimal customerId) {

		customerRegistrationReportList = new CopyOnWriteArrayList<CustomerMainReport>();
		customerContactDetailList = new CopyOnWriteArrayList<CustomerContactDetailView>();
		customerEmployeeInfoList = new CopyOnWriteArrayList<CustomerEmployeeInfoView>();
		customerIdProofviewList = new CopyOnWriteArrayList<CustomerIdproofView>();
		sponsorList = new CopyOnWriteArrayList<CustomerMainReport>();

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String logoPathDyn =null;
		System.out.println("fetchCustomerRegistrationInfo :"+getCountryName()+"\t logo path :"+logoPathDyn);

		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}



		System.out.println("fetchCustomerRegistrationInfo :"+getCountryName()+"\t logo path :"+logoPathDyn+"\t logoPath:"+logoPath);

		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		log.info("logoPath :" + logoPath);
		log.info("subReportPath :" + subReportPath);
		customerRegistrationList = icustomerRegistrationService.findCustomerRegistration(customerId);
		customerContactDetail = icustomerRegistrationService.findCustomerContactDetails(customerId);
		customerEmployeeInfo = icustomerRegistrationService.findCustomerEmployeeInfo(customerId);
		customerIdProof = icustomerRegistrationService.findCustomerIdProof(customerId);

		for (CustomerContactDetailView customerContact : customerContactDetail) {

			CustomerContactDetailView customerContactDetailView = new CustomerContactDetailView();
			customerContactDetailView.setComponentDescription(customerContact.getComponentDescription());
			customerContactDetailView.setCountryName(customerContact.getCountryName());
			customerContactDetailView.setStateName(customerContact.getStateName());
			customerContactDetailView.setDistcritName(customerContact.getDistcritName());
			customerContactDetailView.setCityName(customerContact.getCityName());
			customerContactDetailView.setContactBlockNo(customerContact.getContactBlockNo());
			customerContactDetailView.setContactStreet(customerContact.getContactStreet());
			customerContactDetailView.setContactBuildingNo(customerContact.getContactBuildingNo());
			customerContactDetailView.setContactFlat(customerContact.getContactFlat());
			customerContactDetailView.setContactMobileNo(customerContact.getContactMobileNo());
			customerContactDetailView.setContactTelephone(customerContact.getContactTelephone());

			customerContactDetailList.add(customerContactDetailView);
		}
		for (CustomerEmployeeInfoView customerEmployee : customerEmployeeInfo) {

			CustomerEmployeeInfoView customerEmployeeInfoView = new CustomerEmployeeInfoView();
			customerEmployeeInfoView.setEmployeeTypeName(customerEmployee.getEmployeeTypeName());
			customerEmployeeInfoView.setEmployeeProofDesc(customerEmployee.getEmployeeProofDesc());
			customerEmployeeInfoView.setEmployeeName(customerEmployee.getEmployeeName());
			customerEmployeeInfoView.setStateName(customerEmployee.getStateName());
			customerEmployeeInfoView.setDistcritName(customerEmployee.getDistcritName());
			customerEmployeeInfoView.setCityName(customerEmployee.getCityName());
			customerEmployeeInfoView.setEmployeeArea(customerEmployee.getEmployeeArea());
			customerEmployeeInfoView.setEmployeeBlock(customerEmployee.getEmployeeBlock());
			customerEmployeeInfoView.setEmployeeStreet(customerEmployee.getEmployeeStreet());
			customerEmployeeInfoView.setEmployeeTelePhone(customerEmployee.getEmployeeTelePhone());
			customerEmployeeInfoView.setEmployeePostal(customerEmployee.getEmployeePostal());
			customerEmployeeInfoView.setDepartment(customerEmployee.getDepartment());

			customerEmployeeInfoList.add(customerEmployeeInfoView);
		}
		for (CustomerIdproofView customerIdproof : customerIdProof) {
			CustomerIdproofView customerIdproofView = new CustomerIdproofView();
			customerIdproofView.setIdProofFor(customerIdproof.getIdProofFor());
			customerIdproofView.setIdProofTypeDesc(customerIdproof.getIdProofTypeDesc());
			customerIdproofView.setIdProofInt(customerIdproof.getIdProofInt());
			customerIdproofView.setIdProofExpiredDate(customerIdproof.getIdProofExpiredDate());
			customerIdProofviewList.add(customerIdproofView);
		}
		for (CustomerInfoView customer : customerRegistrationList) {
			CustomerMainReport customerMainReport = new CustomerMainReport();
			String firstName = null;
			String middleName = null;
			String lastName = null;
			String shortName = null;

			// for First Name
			if (customer.getFirstNameLocal() == null && customer.getFirstName() == null) {
				firstName = " ";
			} else if (customer.getFirstNameLocal() == null && customer.getFirstName() != null) {
				firstName = customer.getFirstName();
			} else if (customer.getFirstNameLocal() != null && customer.getFirstName() == null) {
				firstName = customer.getFirstNameLocal();
			} else if (customer.getFirstNameLocal() != null && customer.getFirstName() != null) {
				firstName = customer.getFirstName() + "  " + "(" + customer.getFirstNameLocal() + ")";
			}

			// for middle Name
			if (customer.getMiddleNameLocal() == null && customer.getMiddleName() == null) {
				middleName = " ";
			} else if (customer.getMiddleNameLocal() == null && customer.getLastName() != null) {
				middleName = customer.getMiddleName();
			} else if (customer.getMiddleNameLocal() != null && customer.getMiddleName() == null) {
				middleName = customer.getMiddleNameLocal();
			} else if (customer.getMiddleNameLocal() != null && customer.getMiddleName() != null) {
				middleName = customer.getMiddleName() + "  " + "(" + customer.getMiddleNameLocal() + ")";
			}

			// for last Name
			if (customer.getLastNameLocal() == null && customer.getLastName() == null) {
				lastName = " ";
			} else if (customer.getLastNameLocal() == null && customer.getLastName() != null) {
				lastName = customer.getLastName();
			} else if (customer.getLastNameLocal() != null && customer.getLastName() == null) {
				lastName = customer.getLastNameLocal();
			} else if (customer.getLastNameLocal() != null && customer.getLastName() != null) {
				lastName = customer.getLastName() + "  " + "(" + customer.getLastNameLocal() + ")";
			}

			// for short Name
			if (customer.getShortNameLocal() == null && customer.getShortName() == null) {
				shortName = " ";
			} else if (customer.getShortNameLocal() == null && customer.getShortName() != null) {
				shortName = customer.getShortName();
			} else if (customer.getShortNameLocal() != null && customer.getShortName() == null) {
				shortName = customer.getShortNameLocal();
			} else if (customer.getShortNameLocal() != null && customer.getShortName() != null) {
				shortName = customer.getShortName() + "  " + "(" + customer.getShortNameLocal() + ")";
			}
			customerMainReport.setFirstName(firstName);
			customerMainReport.setMiddleName(middleName);
			customerMainReport.setLastName(lastName);
			customerMainReport.setShortName(shortName);
			String nationality = generalService.getNationalityName(sessionStateManage.getLanguageId(), customer.getNationality());
			if (nationality != null) {
				customerMainReport.setNationality(nationality);
			}

			if (customer.getArticalName().equalsIgnoreCase("ARTICLE 20")) {
				CustomerMainReport customerSponsarReport = new CustomerMainReport();
				customerSponsarReport.setSponsorName(customer.getSponsorName());
				customerSponsarReport.setRelationName(customer.getRelationName());
				sponsorList.add(customerSponsarReport);
			}

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String currentDate = dateFormat.format(new Date());
			
			if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
				customerMainReport.setCompanyName(Constants.COMPANY_NAME_KW);
			}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
				customerMainReport.setCompanyName(Constants.COMPANY_NAME_OM);
			}

			customerMainReport.setCurrentDate(currentDate);
			customerMainReport.setEmail(customer.getEmail());
			customerMainReport.setAlternativeEmail(customer.getAlterEmailId());
			customerMainReport.setGender(customer.getGender());
			customerMainReport.setDateOfBirth(customer.getDateOfBirth());
			customerMainReport.setMobileNo(customer.getMobile());
			customerMainReport.setCustomerReference(customer.getCustomerReference());
			if (customer.getIntroducedBy() == null) {
				customerMainReport.setIntroducedBy("N/A");
				customerMainReport.setIntroducedDate("N/A");
			} else {
				customerMainReport.setIntroducedBy(customer.getIntroducedBy());
				DateFormat dateFormat1 = new SimpleDateFormat(" dd/MM/yyyy");
				String datestring = dateFormat1.format(customer.getIntroducedDate());
				customerMainReport.setIntroducedDate(datestring);
			}
			if (customer.getPepIndicator() != null && customer.getPepIndicator().equalsIgnoreCase(Constants.Yes)) {
				customerMainReport.setPepIndicator(Constants.YES);
			} else if (customer.getPepIndicator() != null && customer.getPepIndicator().equalsIgnoreCase(Constants.No)) {
				customerMainReport.setPepIndicator(Constants.NO);
			}
			if (customer.getMedicalInsurenceIndicator() != null && customer.getMedicalInsurenceIndicator().equalsIgnoreCase(Constants.Yes)) {
				customerMainReport.setMedicalInsurence(Constants.YES);
			} else if (customer.getMedicalInsurenceIndicator() != null && customer.getMedicalInsurenceIndicator().equalsIgnoreCase(Constants.No)) {
				customerMainReport.setMedicalInsurence(Constants.NO);
			}
			customerMainReport.setCustomerBranch(customer.getBranchName());
			customerMainReport.setArtical(customer.getArticalName());
			customerMainReport.setLevel(customer.getArticalLevel());
			customerMainReport.setIncomeRange(customer.getIncomeRangeName());

			if (customer.getDailyTranscationLimit().intValue() == 0) {
				customerMainReport.setDailyLimit(null);
			} else {
				customerMainReport.setDailyLimit(customer.getDailyTranscationLimit());
			}
			if (customer.getWeeklyTransactionLimit().intValue() == 0) {
				customerMainReport.setWeeklyLimit(null);
			} else {
				customerMainReport.setWeeklyLimit(customer.getWeeklyTransactionLimit());
			}
			if (customer.getMonthlyTransactionLimit().intValue() == 0) {
				customerMainReport.setMonthlyLimit(null);
			} else {
				customerMainReport.setMonthlyLimit(customer.getMonthlyTransactionLimit());
			}
			if (customer.getAnnualTransactionLimit().intValue() == 0) {
				customerMainReport.setAnnualLimit(null);
			} else {
				customerMainReport.setAnnualLimit(customer.getAnnualTransactionLimit());
			}

			// customerMainReport.setSignatureSpecimenClob(customer.getSignatureSpecimenClob().toString());
			customerMainReport.setLogoPath(logoPath);

			// customerMainReport.setSignature(clobToString(customer.getSignatureSpecimenClob()));

			try {
				if(customer.getSignatureSpecimenClob() != null){
					customerMainReport.setSignature(customer.getSignatureSpecimenClob().getSubString(1,
							(int) customer.getSignatureSpecimenClob().length()));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			customerMainReport.setComponentDescription(customer.getComponentDescription() + "  " + "(" + customer.getTitleLocal() + ")");
			customerMainReport.setCountryName(customer.getCountryName());
			customerMainReport.setSubReport(subReportPath);
			customerMainReport.setContactDetailList(customerContactDetailList);
			customerMainReport.setEmployeeInfoList(customerEmployeeInfoList);
			customerMainReport.setIdProofList(customerIdProofviewList);
			customerMainReport.setSponsorList(sponsorList);
			customerRegistrationReportList.add(customerMainReport);
		}
	}

	// ////////////////////////////////////////////////////////Report Generation
	// Code End
	// ///////////////////////////////////////////////////////////////////

	public String searchClicked() {
		HttpSession session = sessionStateManage.getSession();
		session.setAttribute("smartcardCheck", getSmartcardcheck());
		session.setAttribute("request", "customerPersonalInfoBean");
		return "searchCustomer";
	}

	public void populateSearchValue() {
		HttpSession session = sessionStateManage.getSession();
		@SuppressWarnings("unchecked")
		String customer = (String) session.getAttribute("customerId");
		// BigDecimal IdNumber = (BigDecimal) session.getAttribute("IdNumber");
		if (customer != null) {
			setIntroducedBy(customer);
			session.removeAttribute("customerId");
		}

	}

	private Boolean renderAddMoreContactDetailsLink = false;
	private Boolean renderCustContactDetailsPanel = false;

	public Boolean getRenderCustContactDetailsPanel() {
		return renderCustContactDetailsPanel;
	}

	public void setRenderCustContactDetailsPanel(Boolean renderCustContactDetailsPanel) {
		this.renderCustContactDetailsPanel = renderCustContactDetailsPanel;
	}

	public Boolean getRenderAddMoreContactDetailsLink() {
		return renderAddMoreContactDetailsLink;
	}

	public void setRenderAddMoreContactDetailsLink(Boolean renderAddMoreContactDetailsLink) {
		this.renderAddMoreContactDetailsLink = renderAddMoreContactDetailsLink;
	}

	public void addMoreContactDetailsToDataTable() {
		clearContactDetail();
		setTelephoneCode(null);
		setMobileContact(null);
		setPlusSign(null);
		setContactDataTable(true);
		setBooContactDetailsButtonPanel(true);
		setPkCustomerContactDetails(null);
		// setRenContactMobile(false);
		// setRenContactTel(false);
		// setBooCheckMobileContact(false);
		// setBooCheckTelContact(false);

		if (contactList.size() > 0) {
			setContactDataTable(true);
		} else {
			setContactDataTable(false);
		}
		setRenderCustContactDetailsPanel(true);
		setRenderAddMoreContactDetailsLink(false);

	}

	public void clickOnokManual() throws IOException, ParseException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("customerregistrationmain.xhtml");
	}

	private BigDecimal identityTpeIdForDashBoard;

	public BigDecimal getIdentityTpeIdForDashBoard() {
		return identityTpeIdForDashBoard;
	}

	public void setIdentityTpeIdForDashBoard(BigDecimal identityTpeIdForDashBoard) {
		this.identityTpeIdForDashBoard = identityTpeIdForDashBoard;
	}

	public String showCustomerDetails() {
		populateCustomerDetails();
		return "customerManualPage";
	}

	public void populateCustomerDetails() {
		fetchCustomerPersonalInformation();
		setCustomerIdProof();
		setIdnumberProof(getIdNumber());
		setIdTypeproof(new BigDecimal(getIdType()));
		if (getSelectedIdType() != null && getSelectedIdType().equalsIgnoreCase(getIdentityTpeIdForDashBoard().toString())) {
			try {


				//setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), sessionStateManage.getCountryId()));

				if(sessionStateManage.getCountryName() != null && sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
					generateDob();
				}else if(sessionStateManage.getCountryName() != null && sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
					booOtherId = true;
				}else{
					generateDob();
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			setBooOtherId(true);
			setBooCivilId(false);
		}
		setIdentityTpeIdForDashBoard(null);
	}

	public String showContactDetails() {
		setMobile(getMobileContact());
		setMobileTemp(getMobileContact());
		fetchCustomerContactDetails();
		setMobileContact(getMobile());
		return "customerContactPage";
	}

	public String showEmployeementDetails() {
		populateArticalDetails();
		popEmpState();
		populateRelationShip();
		clearSponsor();
		fetchSponsorDetails();
		fetchEmploymentInfo();
		return "customerEmployeeInfoPage";
	}

	public String showDigitalSignature() {
		gotoCustomerDigitalSignaturePanel();
		return "customerSignaturePage";
	}

	public String showDocumentScanDetails() {
		gotoCustomerDocumentScanPage();
		return "customerSignaturePage";
	}

	private BigDecimal pageId = null;
	private String requiredDistrict = "N";
	private String requiredCity = "N";
	private String requiredBlock = "N";
	private String requiredStreet = "N";
	private String requiredFlate = "N";
	private String requiredMobile = "N";
	private String requiredTelephone = "N";
	private String requiredHouse = "N";
	private String requiredCountry = "N";
	private String requiredState = "N";

	private String requiredDistrictHome = "N";
	private String requiredCityHome = "N";
	private String requiredBlockHome = "N";
	private String requiredStreetHome = "N";
	private String requiredFlateHome = "N";
	private String requiredMobileHome = "N";
	private String requiredTelephoneHome = "N";
	private String requiredHouseHome = "N";
	private String requiredCountryHome = "N";
	private String requiredStateHome = "N";

	public BigDecimal getPageId() {
		return pageId;
	}

	public void setPageId(BigDecimal pageId) {
		this.pageId = pageId;
	}

	public String getRequiredDistrict() {
		return requiredDistrict;
	}

	public void setRequiredDistrict(String requiredDistrict) {
		this.requiredDistrict = requiredDistrict;
	}

	public String getRequiredCity() {
		return requiredCity;
	}

	public void setRequiredCity(String requiredCity) {
		this.requiredCity = requiredCity;
	}

	public String getRequiredBlock() {
		return requiredBlock;
	}

	public void setRequiredBlock(String requiredBlock) {
		this.requiredBlock = requiredBlock;
	}

	public String getRequiredStreet() {
		return requiredStreet;
	}

	public void setRequiredStreet(String requiredStreet) {
		this.requiredStreet = requiredStreet;
	}

	public String getRequiredFlate() {
		return requiredFlate;
	}

	public void setRequiredFlate(String requiredFlate) {
		this.requiredFlate = requiredFlate;
	}

	public String getRequiredMobile() {
		return requiredMobile;
	}

	public void setRequiredMobile(String requiredMobile) {
		this.requiredMobile = requiredMobile;
	}

	public String getRequiredTelephone() {
		return requiredTelephone;
	}

	public void setRequiredTelephone(String requiredTelephone) {
		this.requiredTelephone = requiredTelephone;
	}

	public String getRequiredHouse() {
		return requiredHouse;
	}

	public void setRequiredHouse(String requiredHouse) {
		this.requiredHouse = requiredHouse;
	}

	public String getRequiredDistrictHome() {
		return requiredDistrictHome;
	}

	public void setRequiredDistrictHome(String requiredDistrictHome) {
		this.requiredDistrictHome = requiredDistrictHome;
	}

	public String getRequiredCityHome() {
		return requiredCityHome;
	}

	public void setRequiredCityHome(String requiredCityHome) {
		this.requiredCityHome = requiredCityHome;
	}

	public String getRequiredBlockHome() {
		return requiredBlockHome;
	}

	public void setRequiredBlockHome(String requiredBlockHome) {
		this.requiredBlockHome = requiredBlockHome;
	}

	public String getRequiredStreetHome() {
		return requiredStreetHome;
	}

	public void setRequiredStreetHome(String requiredStreetHome) {
		this.requiredStreetHome = requiredStreetHome;
	}

	public String getRequiredFlateHome() {
		return requiredFlateHome;
	}

	public void setRequiredFlateHome(String requiredFlateHome) {
		this.requiredFlateHome = requiredFlateHome;
	}

	public String getRequiredMobileHome() {
		return requiredMobileHome;
	}

	public void setRequiredMobileHome(String requiredMobileHome) {
		this.requiredMobileHome = requiredMobileHome;
	}

	public String getRequiredTelephoneHome() {
		return requiredTelephoneHome;
	}

	public void setRequiredTelephoneHome(String requiredTelephoneHome) {
		this.requiredTelephoneHome = requiredTelephoneHome;
	}

	public String getRequiredHouseHome() {
		return requiredHouseHome;
	}

	public void setRequiredHouseHome(String requiredHouseHome) {
		this.requiredHouseHome = requiredHouseHome;
	}

	public String getRequiredCountry() {
		return requiredCountry;
	}

	public void setRequiredCountry(String requiredCountry) {
		this.requiredCountry = requiredCountry;
	}

	public String getRequiredCountryHome() {
		return requiredCountryHome;
	}

	public void setRequiredCountryHome(String requiredCountryHome) {
		this.requiredCountryHome = requiredCountryHome;
	}

	public String getRequiredState() {
		return requiredState;
	}

	public void setRequiredState(String requiredState) {
		this.requiredState = requiredState;
	}

	public String getRequiredStateHome() {
		return requiredStateHome;
	}

	public void setRequiredStateHome(String requiredStateHome) {
		this.requiredStateHome = requiredStateHome;
	}

	public void getRuleBehaviourForCountry() {

		try {
			List<String> componentNames = Arrays.asList("Country");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredCountry(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
		}
	}

	public void getRuleBehaviourForState() {

		try {
			List<String> componentNames = Arrays.asList("State");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredState(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
		}
	}

	public void getRuleBehaviourForDistrict() {

		try {
			List<String> componentNames = Arrays.asList("District");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredDistrict(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
		}
	}

	public void getRuleBehaviourForCity() {

		try {
			List<String> componentNames = Arrays.asList("City");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredCity(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
			log.info("Bisness Behaviour not found " + e);
		}
	}

	public void getRuleBehaviourForBlock() {

		try {
			List<String> componentNames = Arrays.asList("Block");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredBlock(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
			log.info("Bisness Behaviour not found " + e);
		}
	}

	public void getRuleBehaviourForStreet() {

		try {
			List<String> componentNames = Arrays.asList("Street");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredStreet(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
			log.info("Bisness Behaviour not found " + e);
		}
	}

	public void getRuleBehaviourForHouse() {

		try {
			List<String> componentNames = Arrays.asList("House");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredHouse(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
			log.info("Bisness Behaviour not found " + e);
		}
	}

	public void getRuleBehaviourForFlate() {

		try {
			List<String> componentNames = Arrays.asList("Flat");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredFlate(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
			log.info("Bisness Behaviour not found " + e);
		}
	}

	public void getRuleBehaviourForMobile() {

		try {
			List<String> componentNames = Arrays.asList("Mobile Contact");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredMobile(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
			log.info("Bisness Behaviour not found " + e);
		}
	}

	public void getRuleBehaviourForTelephone() {

		try {
			List<String> componentNames = Arrays.asList("Telephone Number");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredTelephone(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
			log.info("Bisness Behaviour not found " + e);
		}
	}

	public void getRuleBehaviourForCountryHome() {

		try {
			List<String> componentNames = Arrays.asList("Country Home");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredCountryHome(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
		}
	}

	public void getRuleBehaviourForStateHome() {

		try {
			List<String> componentNames = Arrays.asList("State Home");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredStateHome(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
		}
	}

	public void getRuleBehaviourForDistrictHome() {

		try {
			List<String> componentNames = Arrays.asList("District Home");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredDistrictHome(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
		}
	}

	public void getRuleBehaviourForCityHome() {

		try {
			List<String> componentNames = Arrays.asList("City Home");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredCityHome(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
			log.info("Bisness Behaviour not found " + e);
		}
	}

	public void getRuleBehaviourForBlockHome() {

		try {
			List<String> componentNames = Arrays.asList("Block Home");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredBlockHome(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
			log.info("Bisness Behaviour not found " + e);
		}
	}

	public void getRuleBehaviourForStreetHome() {

		try {
			List<String> componentNames = Arrays.asList("Street Home");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredStreetHome(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
			log.info("Bisness Behaviour not found " + e);
		}
	}

	public void getRuleBehaviourForHouseHome() {

		try {
			List<String> componentNames = Arrays.asList("House Home");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredHouseHome(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
			log.info("Bisness Behaviour not found " + e);
		}
	}

	public void getRuleBehaviourForFlateHome() {

		try {
			List<String> componentNames = Arrays.asList("Flat Home");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredFlateHome(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
			log.info("Bisness Behaviour not found " + e);
		}
	}

	public void getRuleBehaviourForMobileHome() {

		try {
			List<String> componentNames = Arrays.asList("Mobile Contact Home");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredMobile(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
			log.info("Bisness Behaviour not found " + e);
		}
	}

	public void getRuleBehaviourForTelephoneHome() {

		try {
			List<String> componentNames = Arrays.asList("Telephone Number Home");
			returnObject = generalService.getComponentBehavior(componentNames, sessionStateManage.getLevel(), sessionStateManage.getApplicationId(),
					sessionStateManage.getCompanyId(), getCountryId(), getPageId());
			for (Entry<String, BizComponentConfDetail> entry : returnObject.entrySet()) {
				setRequiredTelephoneHome(entry.getValue().getIsRequired());
			}
		} catch (Exception e) {
			log.info("Bisness Behaviour not found " + e);
		}
	}

	public void getpageID() {

		String pageName = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		pageName = pageName.substring(pageName.lastIndexOf('/') + 1, pageName.indexOf(".xhtml"));
		try {
			BigDecimal pageId = generalService.getPageId(pageName);

			if(pageId != null){
				setPageId(pageId);
			}			

		} catch (Exception e) {
			log.info("Page not found " + e);
		}

	}

	public void resetComponentBehaviour() {
		setRequiredBlock("N");
		setRequiredBlockHome("N");
		setRequiredCity("N");
		setRequiredCityHome("N");
		setRequiredDistrict("N");
		setRequiredDistrictHome("N");
		setRequiredFlate("N");
		setRequiredFlateHome("N");
		setRequiredHouse("N");
		setRequiredHouseHome("N");
		setRequiredMobile("N");
		setRequiredMobileHome("N");
		setRequiredStreet("N");
		setRequiredStreetHome("N");
		setRequiredTelephone("N");
		setRequiredTelephoneHome("N");
	}

	private Boolean isfromCorporatePage = false;
	private Boolean isFromCustomer = false;
	private Boolean isFromWesternUnion = false;

	public Boolean getIsfromCorporatePage() {
		return isfromCorporatePage;
	}

	public Boolean getIsFromCustomer() {
		return isFromCustomer;
	}

	public void setIsfromCorporatePage(Boolean isfromCorporatePage) {
		this.isfromCorporatePage = isfromCorporatePage;
	}

	public void setIsFromCustomer(Boolean isFromCustomer) {
		this.isFromCustomer = isFromCustomer;
	}

	public Boolean getIsFromWesternUnion() {
		return isFromWesternUnion;
	}
	
	public void setIsFromWesternUnion(Boolean isFromWesternUnion) {
		this.isFromWesternUnion = isFromWesternUnion;
	}

	/* @Autowired */
	CorporateSessionValues corporateSessionValues;

	@PostConstruct
	public void createBeans() {
		corporateSessionValues = new CorporateSessionValues();
	}

	public void gotoCorporatePage() {
		Date expDate = null;
		if (corporateSessionValues.getPidno() != null) {
			expDate = generalService.getValidExpiryDate(corporateSessionValues.getPidno());
		}
		corporateSessionValues.setPartnerCustExpDate(expDate);
		if (corporateSessionValues.getOccupationId() != null) {
			String occupation = generalService.getOccupationDesc(corporateSessionValues.getOccupationId(), sessionStateManage.getLanguageId());
			if (occupation != null) {
				corporateSessionValues.setOccupationName(occupation);
			}
		}

		HttpSession session = sessionStateManage.getSession();
		session.setAttribute("corporateRegistration", corporateSessionValues);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/corporateregistration.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void callProcedureForOldUpdate() throws AMGException, JRException, IOException {
		try {
			List<String> outPutList = icustomerRegistrationService.callProcedureUpdate(getPkCustomerId());

			if (outPutList.size() > 0) {
				if (outPutList != null && outPutList.get(0).equalsIgnoreCase(Constants.Yes)) {
					RequestContext.getCurrentInstance().execute("migrationexception.show();");
					return;
				}
			}
		} catch (AMGException e) {
			log.error("Exception Occured While migration Data", e);
			RequestContext.getCurrentInstance().execute("migrationexception.show();");
		}
	}

	/**
	 * * @author : Rabil
	 */

	public java.sql.Clob stringToClob(String source) throws Exception {
		try {
			return new javax.sql.rowset.serial.SerialClob(source.toCharArray());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * * added by nazish on 06-10-2015 for name count
	 */
	public void countWordEnglish(String name) {

		try {
			StringTokenizer words = new StringTokenizer(name);
			int count = 0;
			while (words.hasMoreTokens()) {
				count++;
				words.nextToken();
			}
			setWordCountEn(count);
		} catch (Exception e) {
			log.info("Exception in Word Count: " + e);
		}
	}

	public void countWordLocal(String name) {

		try {
			StringTokenizer words = new StringTokenizer(name);
			int count = 0;
			while (words.hasMoreTokens()) {
				count++;
				words.nextToken();
			}
			setWordCountAr(count);
		} catch (Exception e) {
			log.info("Exception in Word Count: " + e);
		}
	}

	public Boolean getBooCheckActiveCutomer() {
		return booCheckActiveCutomer;
	}

	public void setBooCheckActiveCutomer(Boolean booCheckActiveCutomer) {
		this.booCheckActiveCutomer = booCheckActiveCutomer;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getBoolRemarksforStoM() {
		return boolRemarksforStoM;
	}

	public void setBoolRemarksforStoM(Boolean boolRemarksforStoM) {
		this.boolRemarksforStoM = boolRemarksforStoM;
	}

	public String getSmartCardInd() {
		return smartCardInd;
	}

	public void setSmartCardInd(String smartCardInd) {
		this.smartCardInd = smartCardInd;
	}

	public boolean checkingOldDb() throws ParseException {
		boolean retValue = false;
		List<String> outprocedurevalues = new ArrayList<String>();

		try {
			outprocedurevalues = icustomerRegistrationService.getCustomerRefOrSave(new BigDecimal(getIdType()), getIdNumber(), Constants.Individual);

			if (outprocedurevalues != null && outprocedurevalues.size() != 0) {
				setAllow_Ind(outprocedurevalues.get(0));
				setCustRefNo((outprocedurevalues.get(1)) == null ? "" : (outprocedurevalues.get(1)));

				if (getAllow_Ind().equalsIgnoreCase(Constants.Yes)) {
					BigDecimal idtypeCivilI = generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId())
							.getFsBizComponentData().getComponentDataId();
					List<CustomerIdProof> customerIdProofList = icustomerRegistrationService.getCustomerIdProofCheck(idtypeCivilI, getIdNumber(),
							sessionStateManage.getCountryId());
					if (customerIdProofList.size() > 0) {
						setPkCustomerId(customerIdProofList.get(0).getFsCustomer().getCustomerId());
						retValue = false;
					}
				} else {
					retValue = true;
				}

			}

		} catch (AMGException e) {
			retValue = true;
			log.info("SQL EXCEPTION procedure: " + e);

			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}
		return retValue;
	}

	public boolean checkingOldDbForCard() throws ParseException {

		boolean retValue = false;
		List<String> outprocedurevalues = new ArrayList<String>();

		try {
			outprocedurevalues = icustomerRegistrationService.getCustomerRefOrSave(new BigDecimal(getIdType()), getIdNumber(), Constants.Individual);

			if (outprocedurevalues.size() != 0) {
				setAllow_Ind(outprocedurevalues.get(0));
				setCustRefNo((outprocedurevalues.get(1)) == null ? "" : (outprocedurevalues.get(1)));

				if (getAllow_Ind().equalsIgnoreCase(Constants.Yes)) {
					BigDecimal idtypeCivilI = generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId())
							.getFsBizComponentData().getComponentDataId();
					List<CustomerIdProof> customerIdProofList = icustomerRegistrationService.getCustomerIdProofCheck(idtypeCivilI, getIdNumber(),
							sessionStateManage.getCountryId());
					if (customerIdProofList.size() > 0) {
						setPkCustomerId(customerIdProofList.get(0).getFsCustomer().getCustomerId());
						retValue = false;
					}
				} else {
					retValue = true;

				}

			}

		} catch (AMGException e) {
			retValue = true;
			log.info("SQL EXCEPTION procedure: " + e);

			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}
		return retValue;
	}

	private String createdByContact = null;
	private Date createdDateContact = null;

	public String getCreatedByContact() {
		return createdByContact;
	}

	public void setCreatedByContact(String createdByContact) {
		this.createdByContact = createdByContact;
	}

	public Date getCreatedDateContact() {
		return createdDateContact;
	}

	public void setCreatedDateContact(Date createdDateContact) {
		this.createdDateContact = createdDateContact;
	}

	// //////////////////////////////////////////////////// Go To Remittance
	// Transaction
	// ///////////////////////////////////////////////////////////////////

	// @Autowired
	// PersonalRemittanceBean<T> personalRemittance;
	@Autowired
	ApplicationContext appContext;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;

	public void continueWithRemittanceTransaction() throws ParseException {

		log.info("Entering into continueWithRemittanceTransaction method");

		try {
			
			if(sessionStateManage.getUserName() != null){
				String userNameChk = sessionStateManage.getUserName().substring(0, 4);
				if(userNameChk.equalsIgnoreCase(Constants.ARMS)){
					log.error("Throw Error Message - USER NAME SHOULD BE EMPLOYEE LOGIN, NOT WITH ARMS");
					System.out.println("Throw Error Message - USER NAME SHOULD BE EMPLOYEE LOGIN, NOT WITH ARMS");
					//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "USER NAME SHOULD BE EMPLOYEE LOGIN, NOT WITH ARMS", ""));
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage("ARMS Users cannot do Personal Remittances", null));
					context.getExternalContext().getFlash().setKeepMessages(true);
					return;
				}
			}
			
			List<CustomerIdProof> customerDetailsList = iPersonalRemittanceService.getCustomerDetailsThroughCusReg(getIdNumber());

			PersonalRemittanceBean personalRemittancepage = (PersonalRemittanceBean) appContext.getBean("personalRemittanceBean");

			if (getIdNumber() != null && getIdType() != null) {
				// id number
				personalRemittancepage.setIdNumber(getIdNumber());
				// selected card
				if (getSelectType() != null && getSelectType().equalsIgnoreCase("28")) {
					personalRemittancepage.setSelectCardType(2);
					BigDecimal selectedIdType = new BigDecimal(getIdType());
					personalRemittancepage.setSelectCard(selectedIdType);
				} else {
					personalRemittancepage.setSelectCardType(1);
					BigDecimal selectedIdType = new BigDecimal(getIdType());
					personalRemittancepage.setSelectCard(selectedIdType);
				}
				// place order
				personalRemittancepage.setShowPlaceOrderScreen(true);
				// going to personal remittance
				personalRemittancepage.goFromOldSmartCardpanel();
				
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
			} else {
				// Id Number and Selected Card is not available
				exit();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		log.info("Exit into continueWithRemittanceTransaction method");
	}

	private String errorMsg;
	private Boolean mobileFetch = false;
	private Boolean renderLocalAddress = true;
	private Boolean renderHomeAddress = false;
	private Boolean renderRescan = false;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Boolean getMobileFetch() {
		return mobileFetch;
	}

	public void setMobileFetch(Boolean mobileFetch) {
		this.mobileFetch = mobileFetch;
	}

	public Boolean getRenderLocalAddress() {
		return renderLocalAddress;
	}

	public void setRenderLocalAddress(Boolean renderLocalAddress) {
		this.renderLocalAddress = renderLocalAddress;
	}

	public Boolean getRenderHomeAddress() {
		return renderHomeAddress;
	}

	public void setRenderHomeAddress(Boolean renderHomeAddress) {
		this.renderHomeAddress = renderHomeAddress;
	}

	public Boolean getRenderRescan() {
		return renderRescan;
	}

	public void setRenderRescan(Boolean renderRescan) {
		this.renderRescan = renderRescan;
	}

	public void addData() {

		CreateProofTable custProof = new CreateProofTable();

		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		String df = dateformat.format(getDateExp());
		custProof.setId_for(getIdFor().toString());
		custProof.setId_type(getIdTypeproof().toString());
		custProof.setId_number(getIdnumberProof());
		custProof.setIdFor(idForMap.get(this.idFor));
		custProof.setIdType(idTypeMap.get(this.idTypeproof));

		custProof.setDate_expiary(df);

		if (getCustomerIDProofId() != null) {
			custProof.setCustomerIdProofId(getCustomerIDProofId());
			if (dummiCustomerIdProofList.size() != 0) {
				CreateProofTable lstdata = dummiCustomerIdProofList.get(0);
				custProof.setCreatedByIdProof(lstdata.getCreatedByIdProof());
				custProof.setCreatedDateIdProof(lstdata.getCreatedDateIdProof());
			}

		} else {
			custProof.setCreatedByIdProof(sessionStateManage.getUserName());
			custProof.setCreatedDateIdProof(getCurrentTime());
		}

		createProofList.add(custProof);
		setBooidproofDatatable(true);
		setRenderModifyScan(false);
		setRenderSignature(true);
		clearProofInfo();
	}

	public void resetMobileFetch() {

		setBooCheckMobile(false);
		setSmartcardcheck(false);
		setBooManual(true);
		setBoosmartCardAppear(false);
		setRenderverication(false);
		setPkCustomerId(null);
		setBooMobileHide(false);
		setBooIdType(true);
		setMobileNoFetch(null);
		setBooMobilecheck(false);
		setMobileFetch(false);
		setBooEmailCheck(false);
		setSmartCardInd(null);
		setRemarks(null);
		setBoolRemarksforStoM(false);
		setCustomerIDProofId(null);

	}

	public void saveProofNo() {
		setErrorMsg(null);
		try {
			saveCustomerScanDetails();
			String errMsg=icustomerRegistrationService.callProcedureUpdateCustomerFromIdProof(getPkCustomerId());
			if(errMsg!=null && !errMsg.equalsIgnoreCase(""))
			{
				setErrorMsg(errMsg);
				RequestContext.getCurrentInstance().execute("saveerror.show();");
				return;
			}
			callProcedureForOldUpdate();
			gotoCustomerDigitalSignaturePanel();

		} catch (Exception e) {
			setErrorMsg(e.getMessage());
			log.info("if Not scanned overrite: " + e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}

	/***
	 * 
	 * CR 21-JAN-2016 add rescan facility by Nazish
	 * 
	 */

	public void mainRescan(CreateProofTable createProofTable) {

		List<IdentityTypeMaster> ocrStatusList = icustomerRegistrationService.getOcrList(new BigDecimal(createProofTable.getId_type()));
		if (ocrStatusList.size() > 0) {
			if (ocrStatusList.get(0).getOcrStstus().equalsIgnoreCase(Constants.Yes)) {
				reScanOcr(createProofTable);
			} else {
				rescanNonOcr(createProofTable);
			}
		}
	}

	@SuppressWarnings("static-access")
	public void reScanOcr(CreateProofTable createProofTable) {

		setCheckImageReject(false);

		StringBuffer urlBuffer = new StringBuffer();

		String ampersand = "&";

		String equals = "=";

		String sysDate = new SimpleDateFormat("dd/MM/yyyy").format(getCurrentTime());

		String[] dateStr = sysDate.split("/");

		String curDate = (dateStr[2] + dateStr[1] + dateStr[0]);
		String dobRescan = null;
		String dateExpiryRescan = null;

		if (getDob() != null) {

			String dobstr = new SimpleDateFormat("dd/MM/yyyy").format(getDob());
			String[] dateStr1 = dobstr.split("/");
			dobRescan = dateStr1[2] + dateStr1[1] + dateStr1[0];

		}

		if (getBirthdate() != null) {

			String[] dateStr1 = getBirthdate().split("/");

			dobRescan = dateStr1[2] + dateStr1[1] + dateStr1[0];
		}

		if (getShowDob() != null) {

			String[] dateStr1 = getShowDob().split("/");

			dobRescan = dateStr1[2] + dateStr1[1] + dateStr1[0];

		}

		if (createProofTable.getDate_expiary() != null) {

			String dateExp = createProofTable.getDate_expiary();
			String[] dateStr1 = dateExp.split("/");
			dateExpiryRescan = dateStr1[2] + dateStr1[1] + dateStr1[0];
		}

		BigDecimal civilIdNew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId()).getFsBizComponentData()
				.getComponentDataId();
		List<ArcmateScanMaster> arcmateList = icustomerRegistrationService.fetchArcmateMasterData(Constants.SCAN, Constants.OCR);
		List<ScanIdTypeMaster> scanIdList = null;
		if (getSmartcardcheck()) {
			scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(civilIdNew);
		} else {
			if (getSmartCardInd() != null && getSmartCardInd().equalsIgnoreCase(Constants.CUST_ACTIVE_INDICATOR)) {
				scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(civilIdNew);
			} else {
				scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(new BigDecimal(createProofTable.getId_type()));
			}

		}

		if (arcmateList.size() != 0 && scanIdList.size() != 0) {
			ArcmateScanMaster arcmateValue = arcmateList.get(0);
			ScanIdTypeMaster scanIdValue = scanIdList.get(0);
			String rootContext = "http://";
			String idNo = createProofTable.getId_number();
			urlBuffer.append(rootContext).append(arcmateValue.getIpAddress().trim()).append("/").append(arcmateValue.getContextPath().trim());
			if (arcmateValue.getUrlParamField1() != null) {
				urlBuffer.append(arcmateValue.getUrlParamField1().trim()).append(arcmateValue.getFieldAssigner().trim()).append(idNo);
			}
			if (arcmateValue.getUrlParamField2() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField2().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(idNo);
			}
			if (arcmateValue.getUrlParamField3() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField3().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(dobRescan);
			}
			if (arcmateValue.getUrlParamField4() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField4().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(dateExpiryRescan);
			}
			if (arcmateValue.getUrlParamField5() != null && scanIdValue.getIdTypeValue() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField5().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getIdTypeValue());
			}
			if (arcmateValue.getUrlParamField6() != null) {
				String engName = null;
				if (getSmartcardcheck()) {
					engName = getFullNameEn();
				} else {
					if (getMiddleName() != null && getMiddleName().length() != 0) {
						engName = getFirstName().trim() + " " + getLastName().trim();
					} else {
						engName = getFirstName().trim() + " " + getMiddleName().trim() + " " + getLastName().trim();
					}
				}

				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField6().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(engName);
			}

			if (arcmateValue.getUrlParamField7() != null) {
				String localName = null;
				if (getSmartcardcheck()) {
					localName = getFullNameAr();
				} else {
					if (getMiddleNamel() != null && getMiddleNamel().length() != 0) {
						localName = getFirstNamel().trim() + " " + getLastNamel().trim();
					} else {
						localName = getFirstNamel().trim() + " " + getMiddleNamel().trim() + " " + getLastNamel().trim();
					}
				}

				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField7().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(localName);
			}

			if (arcmateValue.getUrlParamField8() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(getNationality());
			}

			if (arcmateValue.getUrlParamField9() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField9().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(getNationality());
			}

			if (arcmateValue.getUrlParamField10() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField10().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(getGender());
			}

			if (arcmateValue.getUrlParamField11() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField11().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(sessionStateManage.getUserName());
			}

			if (arcmateValue.getUrlParamField12() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField12().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(curDate);
			}

			if (arcmateValue.getUrlParamField13() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField13().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append("EMOS");
			}

			if (arcmateValue.getUrlParamField14() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField14().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(sessionStateManage.getBranchId());
			}

			if (arcmateValue.getUrlParamField15() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField15().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append("IP");
			}

			System.out.println("Add Document of Rescan  OCR URL :  " + urlBuffer.toString());
			log.info("Add Document of Rescan OCR URL :  " + urlBuffer.toString());

			try {

				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

				context.redirect(urlBuffer.toString());

			} catch (Exception e) {

				log.info("Problem to redirect: " + e);
				log.info("Problem to redirect: " + e);
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../common/errorscanning.xhtml");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		} else {
			RequestContext.getCurrentInstance().execute("arcmateTable.show();");
		}

	}

	@SuppressWarnings("static-access")
	public void rescanNonOcr(CreateProofTable createProofTable) {

		StringBuffer urlBuffer = new StringBuffer();

		String idNumberNocr = null;
		Boolean ocrd = false;
		mapIdentityType = ruleEngine.getComponentData("Identity Type");

		BigDecimal identityTpeId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
				identityTpeId = entry.getKey();
				break;
			}
		}

		BigDecimal drivingLicenceId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.LICENSE_NO)) {
				drivingLicenceId = entry.getKey();
				break;
			}
		}

		BigDecimal bedounId = null;
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(Constants.BEDOUIN)) {
				bedounId = entry.getKey();
				break;
			}
		}

		BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId())
				.getFsBizComponentData().getComponentDataId();

		for (CreateProofTable createProof : createProofList) {

			if (createProof.getId_type() != null) {

				if (createProof.getId_type().equalsIgnoreCase(identityTpeId.toPlainString())
						|| createProof.getId_type().equalsIgnoreCase(idtypeCivilIdnew.toPlainString()) || bedounId != null
						&& createProof.getId_type().equalsIgnoreCase(bedounId.toPlainString())
						|| (drivingLicenceId != null && createProof.getId_type().equalsIgnoreCase(drivingLicenceId.toPlainString()))) {
					idNumberNocr = createProof.getId_number();
					ocrd = true;
					break;
				} else {
					ocrd = false;
				}
			}
		}

		List<ArcmateScanMaster> arcmateList = icustomerRegistrationService.fetchArcmateMasterData(Constants.MODIFY, Constants.NON_OCR);
		List<ScanIdTypeMaster> scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(new BigDecimal(createProofTable.getId_type()));

		if (arcmateList.size() != 0 && scanIdList.size() != 0) {
			ArcmateScanMaster arcmateValue = arcmateList.get(0);
			ScanIdTypeMaster scanIdValue = scanIdList.get(0);
			String rootContext = "http://";
			urlBuffer.append(rootContext).append(arcmateValue.getIpAddress().trim()).append("/").append(arcmateValue.getContextPath().trim());
			if (arcmateValue.getUrlParamField1() != null) {
				urlBuffer.append(arcmateValue.getUrlParamField1().trim());
			}

			if (arcmateValue.getUrlParamField2() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField2().trim());
			}

			if (arcmateValue.getUrlParamField3() != null && scanIdValue.getDocumentId() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField3().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getDocumentId());
			}

			if (arcmateValue.getUrlParamField4() != null && scanIdValue.getFolderId() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField4().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFolderId());
			}

			if (arcmateValue.getUrlParamField5() != null && scanIdValue.getUserName() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField5().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getUserName());
			}
			if (arcmateValue.getUrlParamField6() != null && scanIdValue.getPassword() != null) {
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField6().trim())
				.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getPassword());
			}

			if (ocrd) {

				if (arcmateValue.getUrlParamField7() != null) {
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField7().trim())
					.append(arcmateValue.getFieldAssigner().trim()).append(idNumberNocr);
				}

				if (arcmateValue.getUrlParamField8() != null && scanIdValue.getFileCategoryId() != null) {
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim())
					.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFileCategoryId());
				}

			} else {

				if (arcmateValue.getUrlParamField7() != null) {
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField7().trim())
					.append(arcmateValue.getFieldAssigner().trim()).append(createProofTable.getId_number());
				}

				if (arcmateValue.getUrlParamField8() != null && scanIdValue.getFileCategoryId() != null) {
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim())
					.append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFileCategoryId());
				}

			}

			System.out.println("Rescan Document of Non OCR URL :  " + urlBuffer.toString());
			log.info("Rescan Document of Non OCR URL :  " + urlBuffer.toString());

			try {

				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

				context.redirect(urlBuffer.toString());

			} catch (Exception e) {

				log.info("Problem to redirect: " + e);
				log.info("Problem to redirect: " + e);
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../common/errorscanning.xhtml");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} else {

			RequestContext.getCurrentInstance().execute("arcmateTable.show();");
		}
	}

	public void resetBusinessComponent() {
		setRequiredBlock("N");
		setRequiredBlockHome("N");
		setRequiredCity("N");
		setRequiredCityHome("N");
		setRequiredCountry("N");
		setRequiredCountryHome("N");
		setRequiredDistrict("N");
		setRequiredDistrictHome("N");
		setRequiredFlate("N");
		setRequiredFlateHome("N");
		setRequiredHouse("N");
		setRequiredHouseHome("N");
		setRequiredMobile("N");
		setRequiredMobileHome("N");
		setRequiredState("N");
		setRequiredStateHome("N");
		setRequiredStreet("N");
		setRequiredStreetHome("N");
		setRequiredTelephone("N");
		setRequiredTelephoneHome("N");
	}

	// DB scan Integration

	private static String convertImageToString(String fileName) {

		InputStream inputStream = null;

		String imageString = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			inputStream = new FileInputStream(fileName);

			byte[] buffer = new byte[1024];
			baos = new ByteArrayOutputStream();

			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}

			byte[] imageBytes = baos.toByteArray();

			imageString = Base64.encodeBase64String(imageBytes);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				baos.close();
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return imageString;
	}

	public void showImage(CreateProofTable createProof) {
		setCustpdfImage(null);
		setCustnormalImage(null);
		InputStream stream = null;
		try {
			if (createProof.getChecked()) {
				if (createProof.getScanImage() != null) {
					byte[] blobAsBytes = Base64.decodeBase64(createProof.getScanImage());
					stream = new ByteArrayInputStream(blobAsBytes);
					RequestContext context = RequestContext.getCurrentInstance();
					// String mimeType = "image/jpg";
					setCustnormalImage(new DefaultStreamedContent(stream));
					context.execute("PF('showimage').show();");

				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void populateImage(CreateProofTable createProof) {
		setCustpdfImage(null);
		setCustnormalImage(null);
		setViewImage(null);
		InputStream stream = null;
		setBooViewImg(false);
		setBlobId(null);
		try {
			if (createProof.getScanImage() != null) {
				byte[] blobAsBytes = Base64.decodeBase64(createProof.getScanImage());
				stream = new ByteArrayInputStream(blobAsBytes);
				RequestContext context = RequestContext.getCurrentInstance();
				// String mimeType = "image/jpg";
				setCustnormalImage(new DefaultStreamedContent(stream));
				setViewImage(createProof.getScanImage());
				context.execute("PF('showimage').show();");

			} else {

				// List<BigDecimal> outputValues = new ArrayList<BigDecimal>();

				List<DMSApplMap> listDms = icustomerRegistrationService.viewImage(getPkCustomerId(), createProof.getId_number(), new BigDecimal(
						createProof.getId_type()), new SimpleDateFormat("dd/MM/yyyy").parse(createProof.getDate_expiary()));

				if (listDms.size() > 0) {
					setBooViewImg(true);
					// BigDecimal scanYear = outputValues.get(0);
					BigDecimal docBlobId = listDms.get(0).getDocBlobId();
					setBlobId(docBlobId);
					icustomerRegistrationService.callBlobRemote(docBlobId, listDms.get(0).getDocFinYear());
					List<DmsDocBlobUpload> listBlobUpload = icustomerRegistrationService.getListDocTemData(null, docBlobId);
					if (listBlobUpload.size() > 0) {
						Blob image = listBlobUpload.get(0).getDocContent();
						byte[] blobAsBytes = image.getBytes(1, (int) image.length());
						stream = new ByteArrayInputStream(blobAsBytes);
						RequestContext context = RequestContext.getCurrentInstance();
						// String mimeType = "image/jpg";
						setCustnormalImage(new DefaultStreamedContent(stream));
						setViewImage(Base64.encodeBase64String(blobAsBytes));
						context.execute("PF('showimage').show();");
					} else {
						RequestContext.getCurrentInstance().execute("PF('imagenotfound').show();");
					}

				}

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private StreamedContent custpdfImage;
	private StreamedContent custnormalImage;
	private String viewImage = null;
	private Boolean booViewImg = false;
	private BigDecimal blobId = null;

	public StreamedContent getCustpdfImage() {
		return custpdfImage;
	}

	public void setCustpdfImage(StreamedContent custpdfImage) {
		this.custpdfImage = custpdfImage;
	}

	public StreamedContent getCustnormalImage() {
		return custnormalImage;
	}

	public void setCustnormalImage(StreamedContent custnormalImage) {
		this.custnormalImage = custnormalImage;
	}

	public String getViewImage() {
		return viewImage;
	}

	public void setViewImage(String viewImage) {
		this.viewImage = viewImage;
	}

	public String getIdFile() {
		return java.util.UUID.randomUUID().toString();
	}

	public Boolean getBooViewImg() {
		return booViewImg;
	}

	public void setBooViewImg(Boolean booViewImg) {
		this.booViewImg = booViewImg;
	}

	public BigDecimal getBlobId() {
		return blobId;
	}

	public void setBlobId(BigDecimal blobId) {
		this.blobId = blobId;
	}

	@SuppressWarnings("unchecked")
	private void saveDMSApplMap(CreateProofTable createProofTable, BigDecimal blobId) {

		try {
			DMSApplMap dmsApplMap = new DMSApplMap();
			// String
			// countryCode=generalService.getCountryCode(sessionStateManage.getCountryId());
			dmsApplMap.setApplicationCountryId(sessionStateManage.getCountryId());
			dmsApplMap.setDocFinYear(new BigDecimal(getDealYearbyDate()));
			dmsApplMap.setDocBlobId(blobId);
			dmsApplMap.setCustomerId(getPkCustomerId());
			dmsApplMap.setIdentityTypeId(new BigDecimal(createProofTable.getId_type()));
			dmsApplMap.setIdentityInt(createProofTable.getId_number());
			dmsApplMap.setIdentityExpiryDate(new SimpleDateFormat("dd/MM/yyyy").parse(createProofTable.getDate_expiary()));
			dmsApplMap.setCreatedBy(sessionStateManage.getUserName());
			dmsApplMap.setCreatedDate(getCurrentTime());
			generalService.saveOrUpdate((T) dmsApplMap);
		} catch (ParseException e) {
			log.error("Error saving in bloberror: " + e);
			RequestContext.getCurrentInstance().execute("bloberror.show();");
		}

	}

	@SuppressWarnings("unchecked")
	private void saveDMSDocBlobTemp(CreateProofTable createProofTable, BigDecimal blobId) {

		try {
			DmsDocumentBlobTemparory dmsDocBlobTemp = new DmsDocumentBlobTemparory();
			dmsDocBlobTemp.setCountryCode(new BigDecimal(Constants.COUNTRY_CODE_1));
			dmsDocBlobTemp.setDocFinYear(new BigDecimal(getDealYearbyDate()));
			dmsDocBlobTemp.setDocBlobId(blobId);
			dmsDocBlobTemp.setSeqNo(new BigDecimal(1));
			Blob documentContent = new javax.sql.rowset.serial.SerialBlob(decodeImage(createProofTable.getScanImage()));
			dmsDocBlobTemp.setDocumentContent(documentContent);
			dmsDocBlobTemp.setCreatedBy(sessionStateManage.getUserName());
			dmsDocBlobTemp.setCreatedDate(getCurrentTime());
			generalService.saveOrUpdate((T) dmsDocBlobTemp);
			icustomerRegistrationService.callSaveBlobDocsDob(blobId, new BigDecimal(getDealYear()));
			icustomerRegistrationService.delete(dmsDocBlobTemp.getTempSeqNo());
		} catch (SQLException e) {
			log.error("Error saving in bloberror: " + e);
			RequestContext.getCurrentInstance().execute("bloberror.show();");
		} catch (AMGException e) {
			log.error("Error saving in bloberror: " + e);
			RequestContext.getCurrentInstance().execute("bloberror.show();");
		}

	}

	/**
	 * Decodes the base64 string into byte array
	 *
	 * @param imageDataString
	 * @return byte array
	 */
	public static byte[] decodeImage(String imageDataString) {
		return Base64.decodeBase64(imageDataString);
	}

	public String imageToString(BufferedImage bImage, String path) {
		String imageString = null;

		String formatName = path.substring(path.lastIndexOf('.') + 1, path.length());

		// image to bytes
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bImage, "jpg", baos);
			baos.flush();
			byte[] imageAsRawBytes = baos.toByteArray();
			baos.close();

			// bytes to string
			imageString = Base64.encodeBase64String(imageAsRawBytes);
		} catch (IOException ex) {
			log.info("Image Conversion Exception: " + ex);
		}

		return imageString;
	}

	public void clearImg() {
		setCustnormalImage(null);
	}

	private String fileID;
	private String fileScanStatus;
	private Date idExpiryDateScan;
	private Boolean checkImageReject;
	private String complianceStatus;

	public String getFileID() {
		return fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public String getFileScanStatus() {
		return fileScanStatus;
	}

	public void setFileScanStatus(String fileScanStatus) {
		this.fileScanStatus = fileScanStatus;
	}

	public Date getIdExpiryDateScan() {
		return idExpiryDateScan;
	}

	public void setIdExpiryDateScan(Date idExpiryDateScan) {
		this.idExpiryDateScan = idExpiryDateScan;
	}

	public Boolean getCheckImageReject() {
		return checkImageReject;
	}

	public void setCheckImageReject(Boolean checkImageReject) {
		this.checkImageReject = checkImageReject;
	}

	public String getComplianceStatus() {
		return complianceStatus;
	}

	public void setComplianceStatus(String complianceStatus) {
		this.complianceStatus = complianceStatus;
	}

	@SuppressWarnings("unchecked")
	public void saveCustomerImageVerificationInfo() {
		try {
			CustomerImageVerification customerImage = new CustomerImageVerification();
			Customer customer = new Customer();
			customerImage.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
			customerImage.setCreatedBy(sessionStateManage.getUserName());
			customerImage.setCreationDate(getCurrentTime());
			customer.setCustomerId(getPkCustomerId());
			customerImage.setCustomer(customer);
			customerImage.setFileId(getFileID());
			customerImage.setFileScanStatus(getFileScanStatus());
			customerImage.setIdExpiryDate(getDateExp());
			customerImage.setIdNumber(getIdnumberProof());
			customerImage.setIdType(getIdTypeproof());
			if (getComplianceStatus() != null) {
				customerImage.setComplianceStatus(getComplianceStatus());
			}
			generalService.saveOrUpdate((T) customerImage);
		} catch (Exception e) {
			log.error("Exception Occured While saving Data in Customer Image Verification Table", e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}

	public void exitImage() throws AMGException {
		if (getBooViewImg()) {
			icustomerRegistrationService.callDeleteUploadImage(getBlobId());
		}
	}

	public List<BigDecimal> getContactPkList() {
		return contactPkList;
	}

	public void setContactPkList(List<BigDecimal> contactPkList) {
		this.contactPkList = contactPkList;
	}

	class CustomerIdProoofComp implements Comparator<CreateProofTable> {

		@Override
		public int compare(CreateProofTable e1, CreateProofTable e2) {
			return e1.getExpiaryDate().compareTo(e2.getExpiaryDate());
		}
	}

	//Customer debit card details start
	private String colBankCode;
	private List<ViewBankDetails> bankMasterList = new CopyOnWriteArrayList<ViewBankDetails>();
	private boolean booRenderSingleDebit = true;

	private BigDecimal colCardNoLength;
	private boolean booRenderMulDebit = false;
	private String colCardNo;
	private List<CustomerBank> lstDebitCard = new ArrayList<CustomerBank>();

	private String colNameofCard;
	private String colApprovalNo;
	private boolean booColApprovalNo=false;
	private Boolean booAuthozed = false;
	private String colAuthorizedby;
	private List<DebitAutendicationView> empllist = new ArrayList<DebitAutendicationView>();
	private String colpassword;

	private List<ViewBankDetails> localbankListForBankCode = new ArrayList<ViewBankDetails>();
	private BigDecimal populatedDebitCardNumber;
	private List<ViewBankDetails> localbankList = new ArrayList<ViewBankDetails>();
	private String customerFullName;

	private List<CustomerDebitCardDatatable> custDebitDataTableList = new CopyOnWriteArrayList<CustomerDebitCardDatatable>();
	//private List<ViewBankDetails> chequebankMasterList = new ArrayList<ViewBankDetails>();

	private String cyberPassword;
	private String colDebitCardPrefex;
	private String colDebitCardNo;
	private String singleDebitCardPrefex;
	private Boolean booRenderMultipleCardPrefix;
	private Boolean booRenderSingleeCardPrefix;
	private String debitCardNo;
	private BigDecimal debitCardNoLength;

	private List<BankPrefix> lstBankPrefix;
	private String remarksCode;
	private List<ViewRemarks> remarkList;
	private BigDecimal customerBankPkId;
	private String customerDeactiveSts;



	@Autowired
	ICustomerBankService icustomerBankService;
	@Autowired
	IEncrptionDescriptionService<T> encryptionDescriptionService;
	@Autowired
	IGLTransactionForADocument iglTransactionForADocument;





	public String getCustomerDeactiveSts() {
		return customerDeactiveSts;
	}

	public void setCustomerDeactiveSts(String customerDeactiveSts) {
		this.customerDeactiveSts = customerDeactiveSts;
	}

	public BigDecimal getCustomerBankPkId() {
		return customerBankPkId;
	}

	public void setCustomerBankPkId(BigDecimal customerBankPkId) {
		this.customerBankPkId = customerBankPkId;
	}

	public String getRemarksCode() {
		return remarksCode;
	}

	public void setRemarksCode(String remarksCode) {
		this.remarksCode = remarksCode;
	}

	public List<ViewRemarks> getRemarkList() {
		return remarkList;
	}

	public void setRemarkList(List<ViewRemarks> remarkList) {
		this.remarkList = remarkList;
	}

	public BigDecimal getDebitCardNoLength() {
		return debitCardNoLength;
	}

	public void setDebitCardNoLength(BigDecimal debitCardNoLength) {
		this.debitCardNoLength = debitCardNoLength;
	}

	public String getDebitCardNo() {
		return debitCardNo;
	}

	public void setDebitCardNo(String debitCardNo) {
		this.debitCardNo = debitCardNo;
	}


	public Boolean getBooRenderMultipleCardPrefix() {
		return booRenderMultipleCardPrefix;
	}

	public void setBooRenderMultipleCardPrefix(Boolean booRenderMultipleCardPrefix) {
		this.booRenderMultipleCardPrefix = booRenderMultipleCardPrefix;
	}

	public Boolean getBooRenderSingleeCardPrefix() {
		return booRenderSingleeCardPrefix;
	}

	public void setBooRenderSingleeCardPrefix(Boolean booRenderSingleeCardPrefix) {
		this.booRenderSingleeCardPrefix = booRenderSingleeCardPrefix;
	}

	public String getSingleDebitCardPrefex() {
		return singleDebitCardPrefex;
	}

	public void setSingleDebitCardPrefex(String singleDebitCardPrefex) {
		this.singleDebitCardPrefex = singleDebitCardPrefex;
	}

	public List<BankPrefix> getLstBankPrefix() {
		return lstBankPrefix;
	}

	public void setLstBankPrefix(List<BankPrefix> lstBankPrefix) {
		this.lstBankPrefix = lstBankPrefix;
	}

	public String getColDebitCardPrefex() {
		return colDebitCardPrefex;
	}

	public void setColDebitCardPrefex(String colDebitCardPrefex) {
		this.colDebitCardPrefex = colDebitCardPrefex;
	}

	public String getColDebitCardNo() {
		return colDebitCardNo;
	}

	public void setColDebitCardNo(String colDebitCardNo) {
		this.colDebitCardNo = colDebitCardNo;
	}

	public String getCyberPassword() {
		return cyberPassword;
	}

	public void setCyberPassword(String cyberPassword) {
		this.cyberPassword = cyberPassword;
	}

	/*public List<ViewBankDetails> getChequebankMasterList() {
			return chequebankMasterList;
		}

		public void setChequebankMasterList(List<ViewBankDetails> chequebankMasterList) {
			this.chequebankMasterList = chequebankMasterList;
		}*/

	public List<CustomerDebitCardDatatable> getCustDebitDataTableList() {
		return custDebitDataTableList;
	}

	public void setCustDebitDataTableList(
			List<CustomerDebitCardDatatable> custDebitDataTableList) {
		this.custDebitDataTableList = custDebitDataTableList;
	}

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public List<ViewBankDetails> getLocalbankList() {
		return localbankList;
	}

	public void setLocalbankList(List<ViewBankDetails> localbankList) {
		this.localbankList = localbankList;
	}

	public List<ViewBankDetails> getLocalbankListForBankCode() {
		return localbankListForBankCode;
	}

	public void setLocalbankListForBankCode(
			List<ViewBankDetails> localbankListForBankCode) {
		this.localbankListForBankCode = localbankListForBankCode;
	}

	public BigDecimal getPopulatedDebitCardNumber() {
		return populatedDebitCardNumber;
	}

	public void setPopulatedDebitCardNumber(BigDecimal populatedDebitCardNumber) {
		this.populatedDebitCardNumber = populatedDebitCardNumber;
	}

	public String getColBankCode() {
		return colBankCode;
	}

	public void setColBankCode(String colBankCode) {
		this.colBankCode = colBankCode;
	}

	public List<ViewBankDetails> getBankMasterList() {
		return bankMasterList;
	}

	public void setBankMasterList(List<ViewBankDetails> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

	public boolean isBooRenderSingleDebit() {
		return booRenderSingleDebit;
	}

	public void setBooRenderSingleDebit(boolean booRenderSingleDebit) {
		this.booRenderSingleDebit = booRenderSingleDebit;
	}

	public BigDecimal getColCardNoLength() {
		return colCardNoLength;
	}

	public void setColCardNoLength(BigDecimal colCardNoLength) {
		this.colCardNoLength = colCardNoLength;
	}

	public boolean isBooRenderMulDebit() {
		return booRenderMulDebit;
	}

	public void setBooRenderMulDebit(boolean booRenderMulDebit) {
		this.booRenderMulDebit = booRenderMulDebit;
	}

	public String getColCardNo() {
		return colCardNo;
	}

	public void setColCardNo(String colCardNo) {
		this.colCardNo = colCardNo;
	}

	public List<CustomerBank> getLstDebitCard() {
		return lstDebitCard;
	}

	public void setLstDebitCard(List<CustomerBank> lstDebitCard) {
		this.lstDebitCard = lstDebitCard;
	}

	public String getColNameofCard() {
		return colNameofCard;
	}

	public void setColNameofCard(String colNameofCard) {
		this.colNameofCard = colNameofCard;
	}

	public String getColApprovalNo() {
		return colApprovalNo;
	}

	public void setColApprovalNo(String colApprovalNo) {
		this.colApprovalNo = colApprovalNo;
	}

	public boolean isBooColApprovalNo() {
		return booColApprovalNo;
	}

	public void setBooColApprovalNo(boolean booColApprovalNo) {
		this.booColApprovalNo = booColApprovalNo;
	}


	public Boolean getBooAuthozed() {
		return booAuthozed;
	}

	public void setBooAuthozed(Boolean booAuthozed) {
		this.booAuthozed = booAuthozed;
	}

	public String getColAuthorizedby() {
		return colAuthorizedby;
	}

	public void setColAuthorizedby(String colAuthorizedby) {
		this.colAuthorizedby = colAuthorizedby;
	}

	public List<DebitAutendicationView> getEmpllist() {
		return empllist;
	}

	public void setEmpllist(List<DebitAutendicationView> empllist) {
		this.empllist = empllist;
	}

	public String getColpassword() {
		return colpassword;
	}

	public void setColpassword(String colpassword) {
		this.colpassword = colpassword;
	}

	public void populateCustomerBankDetails() {

		int cardlength = 0;

		localbankListForBankCode.clear();

		lstDebitCard.clear();
		setColAuthorizedby(null);
		setColCardNo(null);
		setColCardNoLength(null);
		setPopulatedDebitCardNumber(null);
		setColNameofCard(null);
		setColApprovalNo(null);
		setAuthorizedDate(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);

		setLstBankPrefix(null);

		setBooRenderSingleeCardPrefix(true);
		setBooRenderMultipleCardPrefix(false);
		setDebitCardNoLength(null);

		clearBank();

		if (getColBankCode() != null) {
			
			boolean OthersSts = checkKNETBanks();
			if(OthersSts){
				setColBankCode(null);
				setExceptionMessage(WarningHandler.showWarningMessage("lbl.bnkOthers", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return;
			}

			List<ViewBankDetails> lstViewBankDetails = icustomerBankService.getCustomerLocalBankListFromView(sessionStateManage.getCountryId(), getColBankCode());
			if(lstViewBankDetails!=null && lstViewBankDetails.size()!=0)
			{
				ViewBankDetails viewBankDetails = lstViewBankDetails.get(0);
				List<BankPrefix> lstBankPrefix = icustomerBankService.getBankPrefix(getColBankCode(), viewBankDetails.getChequeBankId());

				if(lstBankPrefix!=null)
				{
					cardlength = viewBankDetails.getDebitCardLength().intValue();
					setDebitCardNoLength(viewBankDetails.getDebitCardLength());
					setColCardNoLength(viewBankDetails.getDebitCardLength());
					if(lstBankPrefix.size()==1)
					{
						setBooRenderSingleeCardPrefix(true);
						setBooRenderMultipleCardPrefix(false);
						setSingleDebitCardPrefex(lstBankPrefix.get(0).getBankPrefix());
						int prefixLength=getSingleDebitCardPrefex().length();

						//setColCardNoLength(new  BigDecimal(cardlength-prefixLength));
					}
					if(lstBankPrefix.size()>1)
					{
						setBooRenderSingleeCardPrefix(false);
						setBooRenderMultipleCardPrefix(true);
						setLstBankPrefix(lstBankPrefix);
					}
					if(lstBankPrefix.size()==0)
					{
						RequestContext.getCurrentInstance().execute("noBankPrefix.show();");
						return;
					}
				}else
				{
					setBooRenderSingleeCardPrefix(true);
					setBooRenderMultipleCardPrefix(false);
					RequestContext.getCurrentInstance().execute("noBankPrefix.show();");
					return;
				}


			}
		} else {
			lstDebitCard.clear();
			setColAuthorizedby(null);
			setAuthorizedDate(null);
			setCreatedBy(null);
			setCreatedDate(null);
			setModifiedBy(null);
			setModifiedDate(null);
			setColCardNo(null);
			setColCardNoLength(null);
			setPopulatedDebitCardNumber(null);
			setColNameofCard(null);
			setColpassword(null);
			setColApprovalNo(null);
			setBooAuthozed(false);
			setBooRenderSingleDebit(true);
			setBooRenderMulDebit(false);
		}


	}
	public void findPrefixLength()
	{

	}
	public boolean checkingCardNumberLength() {

		if(getBooRenderSingleeCardPrefix())
		{
			if(getSingleDebitCardPrefex() != null && getDebitCardNo()!= null){
				String prefix=getSingleDebitCardPrefex();
				String debitCardno=getDebitCardNo();
				setColCardNo(prefix+debitCardno);
				//setColCardNoLength( new BigDecimal(prefix.length()+debitCardno.length()));
			}
		}
		if(getBooRenderMultipleCardPrefix())
		{
			if(getColDebitCardPrefex() != null && getDebitCardNo()!= null){
				String prefix=getColDebitCardPrefex();
				String debitCardno=getDebitCardNo();
				setColCardNo(prefix+debitCardno);
				//setColCardNoLength( new BigDecimal(prefix.length()+debitCardno.length()));
			}
		}

		if (getColCardNo() != null && getColCardNoLength() != null) {
			if ((getColCardNo().toString()).length() != getDebitCardNoLength().intValue()) {
				setDebitCardNo(null);
				setColCardNo(null);
				RequestContext.getCurrentInstance().execute("misMatchBankCardLength.show();");
				return false;
			}
		}

		BigDecimal rtnCustomerId= checkDebitcardRegistered();
		if(rtnCustomerId.compareTo(BigDecimal.ZERO)!=0)
		{
			List<CustomerIdProof> lstCustomerIdProof =icustomerBankService.getCustomerBasedOnId(rtnCustomerId);
			if(lstCustomerIdProof.size()!=0)
			{
				CustomerIdProof customerIdProof = lstCustomerIdProof.get(0);
				String firstName=customerIdProof.getFsCustomer().getFirstName();
				String secondName=customerIdProof.getFsCustomer().getMiddleName();
				String thirdName=customerIdProof.getFsCustomer().getLastName();

				setCustomerDeactiveSts(nullCheck(firstName) + nullCheck(secondName) + nullCheck(thirdName)+ " (" +customerIdProof.getIdentityInt()+")");
				/*+" ID type " + customerIdProof.getFsBizComponentDataByIdentityTypeId().getFsBusinessComponent().getComponentName());*/
				RequestContext.getCurrentInstance().execute("customerDeactive.show();");
				return false;
			}

		}
		/*else{

			List<CustomerBank> lstBank = icustomerBankService.checkDebitCardNoAlreadyExist(getColCardNo().toString());
			if(lstBank != null && lstBank.size() != 0){
				CustomerBank customerDebitCardDatatable = lstBank.get(0);

				setCustomerBankId(customerDebitCardDatatable.getCustomerBankId());
				setColNameofCard(customerDebitCardDatatable.getDebitCardName());
				setCreatedBy(customerDebitCardDatatable.getCreatedBy());
				setCreatedDate(customerDebitCardDatatable.getCreatedDate());
				setModifiedBy(customerDebitCardDatatable.getModifiedBy());
				setModifiedDate(customerDebitCardDatatable.getModifiedDate());
				setBooRenderClearDebitCard(false);
				firstNameCheck();

			}else{
				setCustomerBankId(null);
				setColNameofCard(null);
				setCreatedBy(null);
				setCreatedDate(null);
				setModifiedBy(null);
				setModifiedDate(null);
			}

		}*/

		return true;
	}


	public void populateCustKnetCardDetails() {
		for (CustomerBank customerBank : lstDebitCard) {
			if (customerBank.getDebitCard().equalsIgnoreCase(getColCardNo())) {
				if (customerBank.getAuthorizedBy() != null) {
					List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService.getdebitAutendicationList();
					setEmpllist(localEmpllist);
					setColAuthorizedby(customerBank.getAuthorizedBy());
					setColpassword(null);
					setBooAuthozed(true);
					break;
				} else {
					setColNameofCard(customerBank.getDebitCardName());
					setColAuthorizedby(null);
					setColpassword(null);
					setBooAuthozed(false);
				}
			}
		}

		if(getColNameofCard() != null){
			String errMsg = validateDebitCard();

			if(errMsg != null){
				setExceptionMessage(errMsg);
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		}
	}

	public void editDebitCardtoEnter() {
		setBooRenderSingleDebit(true);
		setBooRenderMulDebit(false);
		setColCardNo(null);
		setPopulatedDebitCardNumber(null);
	}

	// checking customer name and debit card name of card
	public void firstNameCheck() {

		if(getColBankCode()==null)
		{
			setExceptionMessage("Please Select Bank");
			RequestContext.getCurrentInstance().execute("debitCardNo.show();");
			return;
		}

		if(getColCardNo()==null)
		{
			RequestContext.getCurrentInstance().execute("debitCardNo.show();");
			setColNameofCard(getCustomerFullName());
			return;
		}

		String errMsg = validateDebitCard();

		if(errMsg != null){
			setExceptionMessage(errMsg);
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}else{
			if(getColNameofCard() != null && getCustomerFullName() != null){
				if (getCustomerFullName().trim().equalsIgnoreCase(getColNameofCard().trim())) {
					setColAuthorizedby(null);
					setColpassword(null);
					setBooAuthozed(false);
				} else {
					List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService.getdebitAutendicationList();
					setEmpllist(localEmpllist);
					setColAuthorizedby(null);
					setColpassword(null);
					setBooAuthozed(true);
					// populate alert msg if customer name not match
					setExceptionMessage(Constants.NameCheckAlertMsg);
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}
			}
		}
	}

	public void addNewBenificiary() {
		//setColBankTransferBankCode(null);
		setColBankCode(null);
		setColCardNo(null);
		setPopulatedDebitCardNumber(null);
		setColNameofCard(null);
		//setColCash(null);
		setColAuthorizedby(null);
		setColpassword(null);
		setColApprovalNo(null);
		setBooAuthozed(false);
		bankMasterList.clear();
		localbankList.clear();// From View V_EX_CBNK
		lstDebitCard.clear();

		// to fetch All Banks from View
		//getLocalBankListforIndicatorFromView();
		localbankList = generalService.getLocalBankListFromView(sessionStateManage.getCountryId());

		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<ViewBankDetails> lstofBank = new ArrayList<ViewBankDetails>();
		if (localbankList.size() != 0) {
			for (ViewBankDetails lstBank : localbankList) {
				if (!duplicateCheck.contains(lstBank.getChequeBankId())) {
					duplicateCheck.add(lstBank.getChequeBankId());
					lstofBank.add(lstBank);
				}
			}
		}
		setBankMasterList(lstofBank);
		setBooRenderSingleDebit(true);
		setBooRenderMulDebit(false);
	}


	// card number check calling procedure EX_P_VALIDATE_DEBITCARD
	public String validateDebitCard(){
		String errorMsg = null;
		try {
			HashMap<String, Object> inputValues = new HashMap<String, Object>();
			inputValues.put("P_APPLICATION_COUNTRY_ID", sessionStateManage.getCountryId());
			inputValues.put("P_CUSTOMER_ID", getPkCustomerId());
			inputValues.put("P_DEBIT_CARD", new BigDecimal(getColCardNo()));
			inputValues.put("P_DB_CARD_NAME", getColNameofCard());
			inputValues.put("P_BANK_CODE", getColBankCode());

			log.info("EX_P_VALIDATE_DEBITCARD INPUT: "+inputValues.toString());

			errorMsg = iPersonalRemittanceService.validateDebitCardNo(inputValues);

			log.info("EX_P_VALIDATE_DEBITCARD OUTPUT: "+errorMsg);
		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
		return errorMsg;
	}

	public void addCustomerDebitCard()
	{
		setBooRenderClearDebitCard(false);

		try{
			if(getSingleDebitCardPrefex()!=null)
			{

				List<BankPrefix> lstBankPrefix =icustomerBankService.checkBankprefixExist(getSingleDebitCardPrefex());
				if(lstBankPrefix==null || lstBankPrefix.size()==0)
				{
					RequestContext.getCurrentInstance().execute("prefixNotMatch.show();");
					return;
				}

			}

			List<CustomerDebitCardDatatable> custDebitDataTableList = new CopyOnWriteArrayList<CustomerDebitCardDatatable>();

			CustomerDebitCardDatatable customerDebitCardDatatable = new CustomerDebitCardDatatable();

			customerDebitCardDatatable.setCustomerBankPkId(getCustomerBankId());

			customerDebitCardDatatable.setColBankCode(getColBankCode());
			List<ViewBankDetails> lstViewBankDetails=  icustomerBankService.getChequeBnakIdFromView(getColBankCode());
			if(lstViewBankDetails!=null && lstViewBankDetails.size()>0)
			{
				ViewBankDetails viewBankDetails=lstViewBankDetails.get(0);
				customerDebitCardDatatable.setBankId(viewBankDetails.getChequeBankId());
				customerDebitCardDatatable.setShortDesc(viewBankDetails.getBankShortName());
				customerDebitCardDatatable.setFullDesc(viewBankDetails.getBankFullName());
			}

			customerDebitCardDatatable.setColCardNo(getColCardNo());
			customerDebitCardDatatable.setColNameofCard(getColNameofCard());

			customerDebitCardDatatable.setColMaskCardNo(maskCCNumber(getColCardNo()));

			if(getColAuthorizedby() != null){
				customerDebitCardDatatable.setColAuthorizedby(getColAuthorizedby());
				customerDebitCardDatatable.setAuthorizedDate(new Date());
			}

			customerDebitCardDatatable.setCreatedBy(getCreatedBy());
			customerDebitCardDatatable.setCreatedDate(getCreatedDate());
			customerDebitCardDatatable.setModifiedBy(getModifiedBy());
			customerDebitCardDatatable.setModifiedDate(getModifiedDate());

			if(getBooRenderSingleeCardPrefix())
			{
				customerDebitCardDatatable.setDebitCardPrefex(getSingleDebitCardPrefex());
				customerDebitCardDatatable.setDebitCardNo(getDebitCardNo());

				String lastFour = getDebitCardNo().substring(6);

				customerDebitCardDatatable.setBankPrefix(getSingleDebitCardPrefex());
				customerDebitCardDatatable.setBankSuffix(lastFour);
			}
			if(getBooRenderMultipleCardPrefix())
			{
				customerDebitCardDatatable.setDebitCardPrefex(getColDebitCardPrefex());
				customerDebitCardDatatable.setDebitCardNo(getDebitCardNo());

				String lastFour = getDebitCardNo().substring(6);

				customerDebitCardDatatable.setBankPrefix(getColDebitCardPrefex());
				customerDebitCardDatatable.setBankSuffix(lastFour);
			}

			boolean checkDupDebitCard=duplicateDebitCardNoCheck(customerDebitCardDatatable);

			if(!checkDupDebitCard)
			{
				List<CustomerDebitCardDatatable> existDataTableLst = getCustDebitDataTableList();
				setCustDebitDataTableList(null);
				custDebitDataTableList.add(customerDebitCardDatatable);
				if(existDataTableLst!=null)
				{
					custDebitDataTableList.addAll(existDataTableLst);
				}

				setCustDebitDataTableList(custDebitDataTableList);

			}else{
				RequestContext.getCurrentInstance().execute("duplicateCheck.show();");
			}
			clearDebitCard();
		}catch(NullPointerException e)
		{
			setExceptionMessage("Error occurred while adding debit card details"+e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}catch(Exception e)
		{
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}


	}



	public void addCustomerDebitCardDetailsFromContact()
	{
		//setCustDebitDataTableList(null);
		setRemarksCode(null);
		List<CustomerBank> localBankListinCollection = icustomerBankService.customerBanksAvailable(getPkCustomerId());
		if(localBankListinCollection!=null && localBankListinCollection.size()!=0)
		{
			List<CustomerDebitCardDatatable> custDebitDataTableList = new CopyOnWriteArrayList<CustomerDebitCardDatatable>();


			for(CustomerBank customerBank : localBankListinCollection)
			{
				CustomerDebitCardDatatable customerDebitCardDatatable = new CustomerDebitCardDatatable();

				customerDebitCardDatatable.setCustomerBankPkId(customerBank.getCustomerBankId());

				customerDebitCardDatatable.setColBankCode(customerBank.getBankCode());
				customerDebitCardDatatable.setBankId(customerBank.getFsBankMaster().getBankId());
				customerDebitCardDatatable.setCustomerBankPkId(customerBank.getCustomerBankId());

				List<ViewBankDetails> lstViewBankDetails=  icustomerBankService.getChequeBnakIdFromView(customerBank.getBankCode());
				if(lstViewBankDetails!=null && lstViewBankDetails.size()>0)
				{
					ViewBankDetails viewBankDetails=lstViewBankDetails.get(0);
					customerDebitCardDatatable.setShortDesc(viewBankDetails.getBankShortName());
					customerDebitCardDatatable.setFullDesc(viewBankDetails.getBankFullName());
				}

				customerDebitCardDatatable.setColCardNo(encryptionDescriptionService.getDECrypted(Constants.EncriptionKey,customerBank.getDebitCard()));
				customerDebitCardDatatable.setColNameofCard(customerBank.getDebitCardName());


				if(customerDebitCardDatatable.getColCardNo() != null){

					customerDebitCardDatatable.setColMaskCardNo(maskCCNumber(customerDebitCardDatatable.getColCardNo()));

					int total = customerDebitCardDatatable.getColCardNo().length();
					int startlen=6;
					if(total > 6){
						StringBuffer prefix = new StringBuffer(customerDebitCardDatatable.getColCardNo().substring(0,startlen));
						System.out.println("prefix" + prefix);
						StringBuffer debitCard = new StringBuffer(customerDebitCardDatatable.getColCardNo().substring(startlen,total));
						System.out.println("debitCard" + debitCard);
						customerDebitCardDatatable.setDebitCardPrefex(prefix.toString());
						customerDebitCardDatatable.setDebitCardNo(debitCard.toString());
					}

				}

				customerDebitCardDatatable.setCreatedBy(customerBank.getCreatedBy());
				customerDebitCardDatatable.setCreatedDate(customerBank.getCreatedDate());
				customerDebitCardDatatable.setModifiedBy(customerBank.getModifiedBy());
				customerDebitCardDatatable.setModifiedDate(customerBank.getModifiedDate());
				customerDebitCardDatatable.setColAuthorizedby(customerBank.getAuthorizedBy());
				customerDebitCardDatatable.setAuthorizedDate(customerBank.getAuthorizedDate());

				customerDebitCardDatatable.setBankPrefix(customerBank.getBankPrefix());
				customerDebitCardDatatable.setBankSuffix(customerBank.getBankSuffix());

				custDebitDataTableList.add(customerDebitCardDatatable);
			}

			List<CustomerDebitCardDatatable> addCustDebitDataTableList = new CopyOnWriteArrayList<CustomerDebitCardDatatable>();

			List<CustomerDebitCardDatatable> newCustDebitDataTableList = new CopyOnWriteArrayList<CustomerDebitCardDatatable>();


			List<CustomerDebitCardDatatable> existCustDebitDataTableList=getCustDebitDataTableList();
			if(existCustDebitDataTableList!=null)
			{
				newCustDebitDataTableList.addAll(existCustDebitDataTableList);


				for(int i=0;i<newCustDebitDataTableList.size();i++ )
				{
					CustomerDebitCardDatatable custDebitCardDatatable =newCustDebitDataTableList.get(i);
					if(custDebitCardDatatable.getCustomerBankPkId()!=null && getCustomerBankPkId()!=null)
					{
						if(getCustomerBankPkId().compareTo(custDebitCardDatatable.getCustomerBankPkId())==0)
						{
							newCustDebitDataTableList.remove(i);
						}
					}

				}
			}

			for(CustomerDebitCardDatatable customerDebitCardDatatable :custDebitDataTableList)
			{
				for(int i=0;i<newCustDebitDataTableList.size();i++ )
				{
					CustomerDebitCardDatatable custDebitCardDatatable =newCustDebitDataTableList.get(i);
					if(custDebitCardDatatable.getColCardNo().equalsIgnoreCase(customerDebitCardDatatable.getColCardNo()))
					{
						newCustDebitDataTableList.remove(i);
					}
				}
			}
			addCustDebitDataTableList.addAll(newCustDebitDataTableList);


			addCustDebitDataTableList.addAll(custDebitDataTableList);

			setCustDebitDataTableList(null);
			setCustDebitDataTableList(addCustDebitDataTableList);
		}else
		{
			setCustDebitDataTableList(null);
		}
		setCustomerBankPkId(null);

	}


	// to get the local bank list or customer bank list
	public void getLocalBankListforIndicator() {
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		//List<BigDecimal> duplicateCheck1 = new ArrayList<BigDecimal>();
		List<ViewBankDetails> lstofBank = new ArrayList<ViewBankDetails>();
		//List<ViewBankDetails> lstofBank1 = new ArrayList<ViewBankDetails>();
		bankMasterList.clear();
		//chequebankMasterList.clear();

		localbankList = generalService.getLocalBankListFromView(sessionStateManage.getCountryId());

		// cheque banks purpose 
		/*if(localbankList.size() != 0){
					chequebankMasterList.addAll(localbankList);
		}*/

		//lstoflocalbank = generalService.getLocalBankList(sessionmanage.getCountryId());
		/*List<ViewBankDetails> localBankListinCollection = icustomerBankService.customerBanks(getPkCustomerId(), getColBankCode());
				if (localBankListinCollection.size() > 0) {
					bankMasterList.addAll(localBankListinCollection);
				} else {
					bankMasterList.addAll(localbankList);
				}*/

		if (localbankList.size() > 0) {
			bankMasterList.addAll(localbankList);
		}

		if (bankMasterList.size() != 0) {
			for (ViewBankDetails lstBank : bankMasterList) {
				if (!duplicateCheck.contains(lstBank.getChequeBankId())) {
					duplicateCheck.add(lstBank.getChequeBankId());
					lstofBank.add(lstBank);
				}
			}
		}
		bankMasterList.clear();
		bankMasterList.addAll(lstofBank);


		/*if (chequebankMasterList.size() != 0) {
					for (ViewBankDetails lstBank : chequebankMasterList) {
						if (!duplicateCheck1.contains(lstBank.getChequeBankId())) {
							duplicateCheck1.add(lstBank.getChequeBankId());
							lstofBank1.add(lstBank);
						}
					}
				}*/
		//chequebankMasterList.clear();
		//chequebankMasterList.addAll(lstofBank1);

	}

	public String saveCustomerDebitCard()
	{
		try{
			setBooRenderClearDebitCard(false);
			gotoCustomerDocumentScanPage();
			setIdFor(new BigDecimal(48));
			setCheckImageReject(true);
			if (getSmartcardcheck()) {
				BigDecimal idtypeCivilId = generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId())
						.getFsBizComponentData().getComponentDataId();
				setIdTypeproof(idtypeCivilId);
				setIdnumberProof(getCivilId());
				// setDateExp(getExpDate());
			} else {
				if (getIdType() != null) {
					setIdTypeproof(new BigDecimal(getIdType()));
					setIdnumberProof(getIdNumber());
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
				}
			}
			saveCustomerDetailsToCustomerBank();
			return "customerSignaturePage";
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			return "";
		}


	}

	public void callCustomerDebitCardDetails()
	{
		setCustDebitDataTableList(null);
		clearDebitCard();
		addCustomerDebitCardDetailsFromContact();
		getLocalBankListforIndicator();

		if (getSmartcardcheck())
		{
			setCustomerFullName(nullCheck(getFullNameEn()) + nullCheck(getMiddleNameEn()) + nullCheck(getLastNameEn()));
		}else
		{
			setCustomerFullName(nullCheck(getFirstName()) + nullCheck(getMiddleName()) + nullCheck(getLastName()));
		}

		setColNameofCard(getCustomerFullName());
	}

	// back from scan page to bank card adding
	public String backtoBankCardAddition(){
		callCustomerDebitCardDetails();
		return "customerDebitCarnumberPage";
	}

	public void clearDebitCard()
	{
		setColBankCode(null);
		setColDebitCardPrefex(null);
		setSingleDebitCardPrefex(null);
		setDebitCardNo(null);
		setColApprovalNo(null);
		setBooAuthozed(false);
		setColAuthorizedby(null);
		setColNameofCard(null);
		setBooRenderMultipleCardPrefix(false);
		setBooRenderSingleeCardPrefix(true);
		setCustomerBankId(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setAuthorizedDate(null);
	}

	public void clearBank()
	{

		setColDebitCardPrefex(null);
		setSingleDebitCardPrefex(null);
		setDebitCardNo(null);
		setColApprovalNo(null);
		setBooAuthozed(false);
		setColAuthorizedby(null);
		setColNameofCard(getCustomerFullName());
		setBooRenderMultipleCardPrefix(false);
		setBooRenderSingleeCardPrefix(true);

	}

	public String backDebitCardPage()
	{
		setBooRenderClearDebitCard(false);
		clearDebitCard();
		addCustomerDebitCardDetailsFromContact();
		return "customerDebitCarnumberPage";
	}
	/*// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}*/


	/** TO STORE THE NEW BENIFICARY CUSTOMER DETAILS TO CUSTOMER BANK **/
	public void saveCustomerDetailsToCustomerBank() {

		List<CustomerDebitCardDatatable> custDebitDataTableList =getCustDebitDataTableList();
		if(custDebitDataTableList!=null && custDebitDataTableList.size()!=0)
		{

			// SAVE ALL LIST
			List<CustomerBank> lstCustBank = new ArrayList<CustomerBank>();

			for(CustomerDebitCardDatatable customerDebitCardDatatable :custDebitDataTableList)
			{
				CustomerBank customerBank = new CustomerBank();

				customerBank.setCustomerBankId(customerDebitCardDatatable.getCustomerBankPkId());

				customerBank.setCollectionMode(Constants.COLLECTIONMODE);

				BankMaster bankMaster = new BankMaster();
				bankMaster.setBankId(customerDebitCardDatatable.getBankId()); 
				customerBank.setFsBankMaster(bankMaster);

				customerBank.setBankCode(customerDebitCardDatatable.getColBankCode()); // this is fixed//generalService.getBankCode(getColBankid()));

				if(getPkCustomerId() != null){
					Customer customer = new Customer();
					customer.setCustomerId(getPkCustomerId());
					customerBank.setFsCustomer(customer);
				}

				if(customerDebitCardDatatable.getColAuthorizedby() != null){
					customerBank.setAuthorizedBy(customerDebitCardDatatable.getColAuthorizedby());
					customerBank.setAuthorizedDate(new Date());
					customerBank.setPassword(getCyberPassword());
				}

				customerBank.setDebitCard(encryptionDescriptionService.getENCrypted(Constants.EncriptionKey, customerDebitCardDatatable.getColCardNo().toString()));
				customerBank.setDebitCardName(customerDebitCardDatatable.getColNameofCard());
				customerBank.setIsActive(Constants.Yes);

				if(customerDebitCardDatatable.getCustomerBankPkId() != null){
					customerBank.setCreatedBy(customerDebitCardDatatable.getCreatedBy());
					customerBank.setCreatedDate(customerDebitCardDatatable.getCreatedDate());
					customerBank.setModifiedBy(sessionStateManage.getUserName());
					customerBank.setModifiedDate(new Date());
				}else{
					customerBank.setCreatedBy(sessionStateManage.getUserName());
					customerBank.setCreatedDate(new Date());
				}

				customerBank.setBankPrefix(customerDebitCardDatatable.getBankPrefix());
				customerBank.setBankSuffix(customerDebitCardDatatable.getBankSuffix());

				customerBank.setCustomerReference(iglTransactionForADocument.getCustomeReference(getPkCustomerId()));
				// customerBank.setModifiedBy(null);
				// customerBank.setModifiedDate(null);
				log.info("PK CUSTOMER BANK " + customerDebitCardDatatable.getCustomerBankPkId());

				lstCustBank.add(customerBank);
				//icustomerBankService.saveOrUpdate(customerBank);
				// RequestContext.getCurrentInstance().execute("locbankid.show();");
			}

			icustomerBankService.saveAllDebitCardsBanks(lstCustBank);
		}

	}

	public boolean duplicateDebitCardNoCheck(CustomerDebitCardDatatable customerDebitCardDatatable)
	{
		List<CustomerDebitCardDatatable> custDebitDataTableList =getCustDebitDataTableList();
		boolean checkDupDebitCard=false;
		if(custDebitDataTableList!=null && custDebitDataTableList.size()!=0)
		{
			for(CustomerDebitCardDatatable custDebitCardDatatable :custDebitDataTableList)
			{
				//BigDecimal lstDebitCardNo=	new BigDecimal(encryptionDescriptionService.getDECrypted(Constants.EncriptionKey,custDebitCardDatatable.getColCardNo().toString()));
				//BigDecimal toAddDebitCardNo=	new BigDecimal(encryptionDescriptionService.getDECrypted(Constants.EncriptionKey,customerDebitCardDatatable.getColCardNo().toString()));
				String lstDebitCardNo=custDebitCardDatatable.getColCardNo();
				String toAddDebitCardNo=customerDebitCardDatatable.getColCardNo();
				if(lstDebitCardNo.equalsIgnoreCase(toAddDebitCardNo))
				{
					checkDupDebitCard=true;
					break;
				}
			}
		}


		return checkDupDebitCard;
	}

	public String maskCCNumber(String ccnum){
		long starttime = System.currentTimeMillis();
		String masked = null;
		int total = ccnum.length();
		int startlen=6,endlen = 4;
		int masklen = total-(startlen + endlen) ;

		if(total > 6){
			StringBuffer maskedbuf = new StringBuffer(ccnum.substring(0,startlen));
			for(int i=0;i<masklen;i++) {
				maskedbuf.append('*');
			}
			maskedbuf.append(ccnum.substring(startlen+masklen, total));
			masked = maskedbuf.toString();
		}

		long endtime = System.currentTimeMillis();

		return masked;
	}

	public String findPrefix(String prefix,String debitCardNo)
	{
		int prefxLen=prefix.length();
		int debitCardLength=debitCardNo.length();
		String withoutPrefix=null;
		if(debitCardLength > prefxLen)
		{
			withoutPrefix=debitCardNo.substring(prefix.length(),debitCardNo.length()-1);
		}


		return withoutPrefix;
	}

	// delete the customer debit card
	public void removeDebitCard(CustomerDebitCardDatatable customerDebitCardDatatable)
	{
		try{
			if(customerDebitCardDatatable.getCustomerBankPkId()!=null)
			{
				String roleNameDesc=iPersonalRemittanceService.toFetchRoleName(new BigDecimal(sessionStateManage.getRoleId()));
				String roleName = roleNameDesc.trim();

				if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCH_MANAGER) || roleName.equalsIgnoreCase(Constants.USER_ROLE_MANAGER)) {
					//boolean checkDecatvieRecords= icustomerBankService.checkDebitCardWithDeActiveStatus(customerDebitCardDatatable.getColCardNo());
					//setCustomerBankPkId(null);
					/*if(checkDecatvieRecords){
						RequestContext.getCurrentInstance().execute("DeactivateRecordExist.show();");
						return;
					} else {
						setRemarkList(null);
						List<ViewRemarks> lstViewRemarks=icustomerBankService.getViewRemarks();
						setRemarkList(lstViewRemarks);
						setCustomerBankPkId(customerDebitCardDatatable.getCustomerBankPkId());
						RequestContext.getCurrentInstance().execute("remarks.show();");
					}*/

					setRemarkList(null);
					List<ViewRemarks> lstViewRemarks=icustomerBankService.getViewRemarks();
					setRemarkList(lstViewRemarks);
					setCustomerBankPkId(customerDebitCardDatatable.getCustomerBankPkId());
					RequestContext.getCurrentInstance().execute("remarks.show();");
				} else {
					RequestContext.getCurrentInstance().execute("NotAuthorized.show();");
					return;
				}

			}else {
				List<CustomerDebitCardDatatable> custDebitDataTableList = getCustDebitDataTableList();

				if(custDebitDataTableList!=null && custDebitDataTableList.size()!=0)
				{
					for(int i=0;i<custDebitDataTableList.size();i++ )
					{
						CustomerDebitCardDatatable custDebitCardDatatable =custDebitDataTableList.get(i);
						if(custDebitCardDatatable.getColCardNo().equalsIgnoreCase(customerDebitCardDatatable.getColCardNo()))
						{
							custDebitDataTableList.remove(i);
						}
					}
				}
				setCustDebitDataTableList(custDebitDataTableList);
			}

		}catch(Exception e)
		{
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

	}

	// edit the customer debit card name
	public void editCustomerCardName(CustomerDebitCardDatatable customerDebitCardDatatable){
		setCustomerBankId(customerDebitCardDatatable.getCustomerBankPkId());
		setColBankCode(customerDebitCardDatatable.getColBankCode());
		setColDebitCardPrefex(customerDebitCardDatatable.getDebitCardPrefex());
		setSingleDebitCardPrefex(customerDebitCardDatatable.getDebitCardPrefex());
		setDebitCardNo(customerDebitCardDatatable.getDebitCardNo());
		setColNameofCard(customerDebitCardDatatable.getColNameofCard());
		setCreatedBy(customerDebitCardDatatable.getCreatedBy());
		setCreatedDate(customerDebitCardDatatable.getCreatedDate());
		setModifiedBy(customerDebitCardDatatable.getModifiedBy());
		setModifiedDate(customerDebitCardDatatable.getModifiedDate());
		setColCardNo(customerDebitCardDatatable.getColCardNo());
		setBooRenderClearDebitCard(true);
		firstNameCheck();
		setBooRenderMultipleCardPrefix(false);
		setBooRenderSingleeCardPrefix(true);

		List<CustomerDebitCardDatatable> custDebitDataTableList = getCustDebitDataTableList();

		if(custDebitDataTableList!=null && custDebitDataTableList.size()!=0)
		{
			for(int i=0;i<custDebitDataTableList.size();i++ )
			{
				CustomerDebitCardDatatable custDebitCardDatatable =custDebitDataTableList.get(i);
				if(custDebitCardDatatable.getColCardNo().equalsIgnoreCase(customerDebitCardDatatable.getColCardNo()))
				{
					custDebitDataTableList.remove(i);
				}
			}
		}

		setCustDebitDataTableList(custDebitDataTableList);
	}

	public void remarkSelectedRecord()
	{
		if(getRemarksCode()==null)
		{
			setExceptionMessage("Please select remarks");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			return;
		}
		icustomerBankService.deactivateCustomerBank(getCustomerBankPkId(), sessionStateManage.getUserName(),getRemarksCode());
		addCustomerDebitCardDetailsFromContact();
	}

	public void cancelRemarks()
	{
		setRemarksCode(null);
	}

	public BigDecimal checkDebitcardRegistered()
	{

		String prefix = null;
		BigDecimal rtnCustomerId=BigDecimal.ZERO;
		if(getBooRenderMultipleCardPrefix()){
			prefix = getColDebitCardPrefex();
		}

		if(getBooRenderSingleeCardPrefix()){
			prefix = getSingleDebitCardPrefex();
		}

		if(getColCardNo() != null && !getColCardNo().equalsIgnoreCase("") && getColCardNo().length() > 12){
			String suffix = getColCardNo().substring(12);

			BigDecimal bankId = null;
			List<ViewBankDetails> lstViewBankDetails=  icustomerBankService.getChequeBnakIdFromView(getColBankCode());
			if(lstViewBankDetails!=null && lstViewBankDetails.size()>0)
			{
				ViewBankDetails viewBankDetails=lstViewBankDetails.get(0);
				bankId = viewBankDetails.getChequeBankId();
			}

			List<BigDecimal> lstCustomerId = icustomerBankService.checkDebitCardWithActiveStatus(getColCardNo(),prefix,suffix,bankId);

			if(lstCustomerId!=null)
			{
				for(BigDecimal customerId:lstCustomerId)
				{
					if(getPkCustomerId().compareTo(customerId)!=0)
					{
						rtnCustomerId=customerId;
					}
				}
			}
		}

		return rtnCustomerId;
	}
	/*public void deactiveCustomer()
	{

		boolean checkDecatvieRecords= icustomerBankService.checkDebitCardWithDeActiveStatus(getColCardNo());
		setCustomerBankPkId(null);
		if(checkDecatvieRecords)
		{
			RequestContext.getCurrentInstance().execute("DeactivateRecordExist.show();");
			return;
		}else
		{
			setRemarkList(null);
			List<ViewRemarks> lstViewRemarks=icustomerBankService.getViewRemarks();

			setRemarkList(lstViewRemarks);

			List<BigDecimal> customerBankPkId = icustomerBankService.getCustomerbankIdForDeactivatee(getColCardNo());

			if(customerBankPkId != null && customerBankPkId.size() != 0){
				if(customerBankPkId != null && customerBankPkId.size() == 1){
					setCustomerBankPkId(customerBankPkId.get(0));
					RequestContext.getCurrentInstance().execute("remarks.show();");
				}else{
					// multiple record
					setExceptionMessage("Multiple Records Available Please Contact IT Department");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
					return;
				}
			}else{
				// no record found
				setExceptionMessage("Record Not Available");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return;
			}


		}
	}*/

	// CHECK IF ANY DUPLIACTE AVAILABLE WHILE DOING TRANSACTION
	public String checkDuplicateDebitCard(){

		String errMsg = null;

		String prefix = null;
		if(getBooRenderMultipleCardPrefix()){
			prefix = getColDebitCardPrefex();
		}

		if(getBooRenderSingleeCardPrefix()){
			prefix = getSingleDebitCardPrefex();
		}

		if(getColCardNo() != null && !getColCardNo().equalsIgnoreCase("") && getColCardNo().length() > 12){
			String suffix = getColCardNo().substring(12);

			BigDecimal bankId = null;
			List<ViewBankDetails> lstViewBankDetails=  icustomerBankService.getChequeBnakIdFromView(getColBankCode());
			if(lstViewBankDetails!=null && lstViewBankDetails.size()>0)
			{
				ViewBankDetails viewBankDetails=lstViewBankDetails.get(0);
				bankId = viewBankDetails.getChequeBankId();
			}

			List<BigDecimal> customerBankPkId = icustomerBankService.getCustomerbankIdForDeactivatee(getColCardNo(),prefix,suffix,bankId);
			if(customerBankPkId != null && customerBankPkId.size() != 0){
				if(customerBankPkId != null && customerBankPkId.size() == 1){
					// allow if active
					errMsg = null;
				}else{
					// multiple record
					errMsg = "Multiple Records Available Please Contact IT Department";

					// deactive again with
					/*List<CustomerBank> customerBankList = icustomerBankService.fetchcustomerBanksDetails(getColCardNo(),prefix,suffix,bankId);

					for (CustomerBank customerDup : customerBankList) {
						if(customerDup.getFsCustomer().getCustomerId() != null && customerDup.getFsCustomer().getCustomerId().compareTo(getPkCustomerId())!= 0){
							icustomerBankService.deactivateCustomerBank(customerDup.getCustomerBankId(), sessionStateManage.getUserName(),getRemarksCode());
						}
					}*/
				}
			}
		}

		return errMsg;
	}

	public void deactiveCustomer()
	{

		String checkTrnx = checkDuplicateDebitCard();

		if(checkTrnx != null){
			setDebitCardNo(null);
			setExceptionMessage(checkTrnx);
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}else{
			/*boolean checkDecatvieRecords= icustomerBankService.checkDebitCardWithDeActiveStatus(getColCardNo());
			setCustomerBankPkId(null);
			if(checkDecatvieRecords)
			{
				RequestContext.getCurrentInstance().execute("DeactivateRecordExist.show();");
				return;
			}else
			{
				setRemarkList(null);
				List<ViewRemarks> lstViewRemarks=icustomerBankService.getViewRemarks();
				setRemarkList(lstViewRemarks);
				List<BigDecimal> customerBankPkId =icustomerBankService.getCustomerbankIdForDeactivatee(getColCardNo());
				setCustomerBankPkId(customerBankPkId.get(0));
				RequestContext.getCurrentInstance().execute("remarks.show();");
			}*/			
			setRemarkList(null);
			List<ViewRemarks> lstViewRemarks=icustomerBankService.getViewRemarks();
			setRemarkList(lstViewRemarks);


			String prefix = null;
			if(getBooRenderMultipleCardPrefix()){
				prefix = getColDebitCardPrefex();
			}

			if(getBooRenderSingleeCardPrefix()){
				prefix = getSingleDebitCardPrefex();
			}

			if(getColCardNo() != null && !getColCardNo().equalsIgnoreCase("") && getColCardNo().length() > 12){
				String suffix = getColCardNo().substring(12);

				BigDecimal bankId = null;
				List<ViewBankDetails> lstViewBankDetails=  icustomerBankService.getChequeBnakIdFromView(getColBankCode());
				if(lstViewBankDetails!=null && lstViewBankDetails.size()>0)
				{
					ViewBankDetails viewBankDetails=lstViewBankDetails.get(0);
					bankId = viewBankDetails.getChequeBankId();
				}

				List<BigDecimal> customerBankPkId =icustomerBankService.getCustomerbankIdForDeactivatee(getColCardNo(),prefix,suffix,bankId);
				setCustomerBankPkId(customerBankPkId.get(0));
				RequestContext.getCurrentInstance().execute("remarks.show();");
			}

		}
	}


	public void clearDebitCradNo()
	{
		setDebitCardNo(null);
		setColCardNo(null);
	}

	public void verifyPassword()
	{

		boolean check = checkingCardNumberLength();

		if(check){

			String checkTrnx = checkDuplicateDebitCard();

			if(checkTrnx != null){
				setDebitCardNo(null);
				setExceptionMessage(checkTrnx);
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}else{
				if(getColBankCode()== null)
				{
					RequestContext.getCurrentInstance().execute("bankCodeEmpty.show();");
					return;
				}

				if(getBooRenderMultipleCardPrefix())
				{
					if(getColDebitCardPrefex()==null)
					{
						RequestContext.getCurrentInstance().execute("bankPrefixEmpty.show();");
						return;
					}
				}


				if(getBooRenderSingleeCardPrefix())
				{
					if(getSingleDebitCardPrefex()==null)
					{
						RequestContext.getCurrentInstance().execute("bankPrefixEmpty.show();");
						return;
					}
				}
				if(getDebitCardNo()== null)
				{
					RequestContext.getCurrentInstance().execute("bankdebitCardNo.show();");
					return;
				}

				if(getColNameofCard()== null)
				{
					RequestContext.getCurrentInstance().execute("cardNameEmpty.show();");
					return;
				}

				if(getBooAuthozed())
				{
					if(getColAuthorizedby()==null)
					{
						RequestContext.getCurrentInstance().execute("authorizedByEmpty.show();");
						return;
					}

					if(getColpassword()==null)
					{
						RequestContext.getCurrentInstance().execute("authorizedPasswordEmpty.show();");
						return;
					}
				}

				if (getColAuthorizedby() != null) {
					List<DebitAutendicationView> lstEmpLogin = new ArrayList<DebitAutendicationView>();
					String userNames = getColAuthorizedby();

					lstEmpLogin = 	iPersonalRemittanceService.getdebitAutendicationListByUserId(getColAuthorizedby(),getColpassword());				

					if (lstEmpLogin.size() != 0) {
						addCustomerDebitCard();
					} else {
						setColpassword(null);
						RequestContext.getCurrentInstance().execute("passwordcheck.show();");
					}
				} else {
					addCustomerDebitCard();
				}
			}

		}

	}
	//Customer debit card details end


	//OTP implementation starts
	private BigDecimal otpNo;
	private Boolean booRenderOtpPanel;
	private Boolean otpSelection;
	private Boolean booRenderOtpLinkPanel;
	private DateTime prevouseClickedtime;
	private String otpAuthorizedBy;
	private String otpAuthorizedPassword;
	private String otpRemarks;
	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private List<DebitAutendicationView> branchManagerApproval = new ArrayList<DebitAutendicationView>();

	public DateTime getPrevouseClickedtime() {
		return prevouseClickedtime;
	}
	public void setPrevouseClickedtime(DateTime prevouseClickedtime) {
		this.prevouseClickedtime = prevouseClickedtime;
	}

	public Boolean getBooRenderOtpLinkPanel() {
		return booRenderOtpLinkPanel;
	}
	public void setBooRenderOtpLinkPanel(Boolean booRenderOtpLinkPanel) {
		this.booRenderOtpLinkPanel = booRenderOtpLinkPanel;
	}

	public Boolean getOtpSelection() {
		return otpSelection;
	}
	public void setOtpSelection(Boolean otpSelection) {
		this.otpSelection = otpSelection;
	}

	public BigDecimal getOtpNo() {
		return otpNo;
	}
	public void setOtpNo(BigDecimal otpNo) {
		this.otpNo = otpNo;
	}

	public Boolean getBooRenderOtpPanel() {
		return booRenderOtpPanel;
	}
	public void setBooRenderOtpPanel(Boolean booRenderOtpPanel) {
		this.booRenderOtpPanel = booRenderOtpPanel;
	}

	public String getOtpAuthorizedBy() {
		return otpAuthorizedBy;
	}
	public void setOtpAuthorizedBy(String otpAuthorizedBy) {
		this.otpAuthorizedBy = otpAuthorizedBy;
	}

	public String getOtpAuthorizedPassword() {
		return otpAuthorizedPassword;
	}
	public void setOtpAuthorizedPassword(String otpAuthorizedPassword) {
		this.otpAuthorizedPassword = otpAuthorizedPassword;
	}

	public String getOtpRemarks() {
		return otpRemarks;
	}
	public void setOtpRemarks(String otpRemarks) {
		this.otpRemarks = otpRemarks;
	}

	public List<DebitAutendicationView> getBranchManagerApproval() {
		return branchManagerApproval;
	}
	public void setBranchManagerApproval(List<DebitAutendicationView> branchManagerApproval) {
		this.branchManagerApproval = branchManagerApproval;
	}

	// authorization 
	public void fetchAuthorizationDetails(){
		setOtpAuthorizedBy(null);
		setOtpAuthorizedPassword(null);
		setOtpRemarks(null);
		branchManagerApproval.clear();
		List<DebitAutendicationView> localAuthlist = iPersonalRemittanceService.getdebitAutendicationList();
		if(localAuthlist != null && localAuthlist.size() != 0){
			branchManagerApproval.addAll(localAuthlist);
		}
	}

	private void populateCustomerOtpPanel(BigDecimal customerIdPk)
	{
		clearOtpDetails();
		clearOldOTPRetry();
		fetchAuthorizationDetails();
		try {
			boolean rtnValue =icustomerRegistrationService.getOtpdetails(customerIdPk);

			if(rtnValue)
			{
				setBooRenderOtpPanel(true);
			}else
			{
				setBooRenderOtpPanel(false);
			}
		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	public void checkOtpNo()
	{
		try {

			if(getOtpNo() != null){
				String rtnMessage=icustomerRegistrationService.verifyOtpNo(getOtpNo(), getPkCustomerId(), sessionStateManage.getUserName());
				if(rtnMessage!=null)
				{
					/*if(rtnMessage.equalsIgnoreCase("FAIL"))
					{
						setOtpNo(null);
						RequestContext.getCurrentInstance().execute("failToUpdate.show();");
					}else if(rtnMessage.equalsIgnoreCase("OTPNOTMATCH"))
					{
						setOtpNo(null);
						RequestContext.getCurrentInstance().execute("otpNotMatch.show();");
					}*/
					if(rtnMessage.equalsIgnoreCase("MATCH"))
					{
						//setOtpNo(null);
						//RequestContext.getCurrentInstance().execute("failToUpdate.show();");
						// continue
						setOtpAuthorizedBy(null);
						setOtpAuthorizedPassword(null);
						setOtpRemarks(null);
						setBooRenderOtpAuthPanel(false);
						setBooRenderOtpLinkPanel(false);
						setOtpSelection(false);
					}else if(rtnMessage.equalsIgnoreCase("OTPNOTMATCH"))
					{
						setOtpNo(null);
						RequestContext.getCurrentInstance().execute("otpNotMatch.show();");
					}
				}
			}

		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	public void selectOtpResend()
	{

		try {

			if(getOtpSelection())
			{
				//setOtpSelection(false);
				/*boolean checkAlreadyVerfied= icustomerRegistrationService.checkOtpVerified(getPkCustomerId());
				if(checkAlreadyVerfied)
				{
					setBooRenderOtpLinkPanel(false);
					setOtpSelection(false);
					RequestContext.getCurrentInstance().execute("alreadyVerified.show();");
					return;
				}else
				{
					setBooRenderOtpLinkPanel(true);
				}*/



				if(getOtpNo() != null){
					String rtnMessage;

					rtnMessage = icustomerRegistrationService.verifyOtpNo(getOtpNo(), getPkCustomerId(), sessionStateManage.getUserName());

					if(rtnMessage!=null)
					{
						if(rtnMessage.equalsIgnoreCase("MATCH"))
						{
							setBooRenderOtpLinkPanel(false);
							setOtpSelection(false);
							RequestContext.getCurrentInstance().execute("alreadyVerified.show();");
							return;
						}else if(rtnMessage.equalsIgnoreCase("OTPNOTMATCH"))
						{
							setBooRenderOtpLinkPanel(true);
						}
					}else{
						setBooRenderOtpLinkPanel(true);
					}
				}else{
					setBooRenderOtpLinkPanel(true);
				}
			}else
			{
				setOtpSelection(false);
				setBooRenderOtpLinkPanel(false);
			}
		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

	}
	private void clearOtpDetails()
	{
		setOtpNo(null);
		setBooRenderOtpPanel(false);
		setOtpSelection(false);
		setBooRenderOtpLinkPanel(false);
		setPrevouseClickedtime(null);

		setOtpAuthorizedBy(null);
		setOtpAuthorizedPassword(null);
		setOtpRemarks(null);
	}

	public void resendOtp()
	{

		try {

			String mobilePinGen;
			String mobilePinEncrypt;
			int otpAttempts = icustomerRegistrationService.getOTPRetryAttempts(getOtpNo(), getPkCustomerId());
			if(otpAttempts>=3)
			{
				RequestContext.getCurrentInstance().execute("maxLimit.show();");
				return;
			}
			if(getPrevouseClickedtime()!=null)
			{


				DateTime previouseTime=getPrevouseClickedtime();
				DateTime currentTime = new DateTime(new Date());
				//(Minutes.minutesBetween(previouseTime, currentTime).getMinutes())%60;
				int minutes=(Minutes.minutesBetween(previouseTime, currentTime).getMinutes())%60;
				if(minutes>=1)
				{
					//counterToClick++;
					String mobileNo = icustomerRegistrationService.getMobileNoBasedOnCustomerId(getPkCustomerId());

					BigDecimal languageId = sessionStateManage.getLanguageId()==null ? BigDecimal.ONE : sessionStateManage.getLanguageId();


					HashMap<String, String> inputValues = new HashMap<String, String>();

					inputValues.put("P_SENDER_ID", "AlMulla EXC");
					inputValues.put("P_MOBILE", mobileNo);

					inputValues.put("P_APPLNAME", "");
					inputValues.put("P_AL1COD", "");
					inputValues.put("P_ACNTCOD", "");
					inputValues.put("P_CREATOR", "");
					inputValues.put("P_LANGUAGE", sessionStateManage.getLanguageId().toString());
					inputValues.put("P_COMCOD", "");
					inputValues.put("P_DOCCOD", "");
					inputValues.put("P_DOCFYR", "");
					inputValues.put("P_DOCNO", "");

					List<String> outputvalues = icustomerRegistrationService.callProcedureToGenerateKioskPin();
					mobilePinGen = outputvalues.get(0) == null ? "" : outputvalues.get(0);
					mobilePinEncrypt = outputvalues.get(1) == null ? "" : outputvalues.get(1);
					log.info("Pin generation : "+mobilePinGen + " : " +mobilePinEncrypt);

					if(mobilePinGen != null && !mobilePinGen.equalsIgnoreCase("")){

						String otpSmsMessage = WarningHandler.showWarningMessage("lbl.smsOTPMessageForMobile", languageId);
						if(otpSmsMessage==null)
						{
							otpSmsMessage=WarningHandler.showWarningMessage("lbl.smsOTPMessageForMobile", BigDecimal.ONE);
						}

						inputValues.put("P_MESSAGE", otpSmsMessage + " : " + mobilePinGen);

						HashMap<String, String> outputValues = icustomerRegistrationService.callOTPSendProcedure(inputValues);

						if(outputValues != null && outputValues.size() != 0){
							if(outputValues.get("P_ERROR_MESSAGE") != null){
								setExceptionMessage(outputValues.get("P_ERROR_MESSAGE"));
								RequestContext.getCurrentInstance().execute("alertmsg.show();");
							}else{
								BigDecimal mobilePin = new BigDecimal(mobilePinGen);
								icustomerRegistrationService.updateOTPRetry(getOtpNo(), getPkCustomerId(), mobilePin);
							}
						}

					}else{
						setExceptionMessage("Mobile Pin not generated");
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
						return;
					}

					//icustomerRegistrationService.updateOTPRetry(getOtpNo(), getPkCustomerId());

					setPrevouseClickedtime(currentTime);
					setBooRenderOtpLinkPanel(true);
				}else
				{
					RequestContext.getCurrentInstance().execute("timeInterval.show();");
					return;
				}
			}else
			{
				DateTime currentTime = new DateTime(new Date());
				setPrevouseClickedtime(currentTime);
				setBooRenderOtpLinkPanel(true);
				//counterToClick++;
				String mobileNo=icustomerRegistrationService.getMobileNoBasedOnCustomerId(getPkCustomerId());

				BigDecimal languageId = sessionStateManage.getLanguageId()==null ? BigDecimal.ONE : sessionStateManage.getLanguageId();
				String otpSmsMessage=WarningHandler.showWarningMessage("lbl.smsOTPMessageForMobile", languageId);
				if(otpSmsMessage==null)
				{
					otpSmsMessage=WarningHandler.showWarningMessage("lbl.smsOTPMessageForMobile", BigDecimal.ONE);
				}

				HashMap<String, String> inputValues = new HashMap<String, String>();

				inputValues.put("P_SENDER_ID", "AlMulla EXC");
				inputValues.put("P_MOBILE", mobileNo);
				inputValues.put("P_APPLNAME", "");
				inputValues.put("P_AL1COD", "");
				inputValues.put("P_ACNTCOD", "");
				inputValues.put("P_CREATOR", "");
				inputValues.put("P_LANGUAGE", sessionStateManage.getLanguageId().toString());
				inputValues.put("P_COMCOD", "");
				inputValues.put("P_DOCCOD", "");
				inputValues.put("P_DOCFYR", "");
				inputValues.put("P_DOCNO", "");
				//inputValues.put("(P_SEQUENCE", "");

				List<String> outputvalues = icustomerRegistrationService.callProcedureToGenerateKioskPin();
				mobilePinGen = outputvalues.get(0) == null ? "" : outputvalues.get(0);
				mobilePinEncrypt = outputvalues.get(1) == null ? "" : outputvalues.get(1);
				log.info("Pin generation : "+mobilePinGen + " : " +mobilePinEncrypt);

				if(mobilePinGen != null && !mobilePinGen.equalsIgnoreCase("")){

					String otpSmsMessage1 = WarningHandler.showWarningMessage("lbl.smsOTPMessageForMobile", languageId);
					if(otpSmsMessage1==null)
					{
						otpSmsMessage1=WarningHandler.showWarningMessage("lbl.smsOTPMessageForMobile", BigDecimal.ONE);
					}

					inputValues.put("P_MESSAGE", otpSmsMessage1 + " : " + mobilePinGen);

					HashMap<String, String> outputValues = icustomerRegistrationService.callOTPSendProcedure(inputValues);

					if(outputValues != null && outputValues.size() != 0){
						if(outputValues.get("P_ERROR_MESSAGE") != null){
							setExceptionMessage(outputValues.get("P_ERROR_MESSAGE"));
							RequestContext.getCurrentInstance().execute("alertmsg.show();");
						}else{
							BigDecimal mobilePin = new BigDecimal(mobilePinGen);
							icustomerRegistrationService.updateOTPRetry(getOtpNo(), getPkCustomerId(), mobilePin);
						}
					}

				}else{
					setExceptionMessage("Mobile Pin not generated");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
					return;
				}

				//icustomerRegistrationService.updateOTPRetry(getOtpNo(), getPkCustomerId());

			}

		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

		setOtpSelection(false);
		setBooRenderOtpLinkPanel(false);
		//call procedure
	}

	public void clearOldOTPRetry()
	{
		try {
			icustomerRegistrationService.clearOldOTPRetry(getPkCustomerId());
		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// to get the accoMMYY value
	@Deprecated
	public static String getCurrentDateWithFormat() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		for (int i = 0; i < 12; i++) {
			if (i < 9) {
				data.put(i, "0" + String.valueOf(i + 1));
			} else {
				data.put(i, String.valueOf(i + 1));
			}
		}

		//String year = String.valueOf(new Date().getYear()).substring(1, 3);
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		String year =String.valueOf(calendar.get(Calendar.YEAR));

		System.out.println(Calendar.getInstance().get(Calendar.MONTH));
		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH)) + "/" + year;
	}

	// save authorization
	public void saveAuthorization(){
		// Authorized Log Insert and Fs_Customer Update OTP Verified By and Verified Date
		HashMap<String, Object> checkAlreadyVerfied;
		try {

			checkAlreadyVerfied = icustomerRegistrationService.checkOTPVerify(getPkCustomerId());

			BigDecimal otpNo = (BigDecimal) checkAlreadyVerfied.get("OTP_NO");

			if(otpNo != null && otpNo.compareTo(BigDecimal.ZERO) != 0)
			{
				AuthorizedLog authorizedLog = new AuthorizedLog();

				authorizedLog.setDocumentDate(new Date());
				authorizedLog.setAuthorizedBy(getOtpAuthorizedBy());

				if(getOtpRemarks() != null && !getOtpRemarks().equalsIgnoreCase("")){
					authorizedLog.setAmlRemarks(getOtpRemarks().concat(" - OTP : ").concat(otpNo.toPlainString()));
				}else{
					authorizedLog.setAmlRemarks("OTP : " +otpNo.toPlainString());
				}

				authorizedLog.setAuthorizedPassword(getOtpAuthorizedPassword());
				authorizedLog.setAuthorizedLogId(sessionStateManage.getEmployeeId());
				authorizedLog.setAuthorizedType("95");
				authorizedLog.setCreatedBy(sessionStateManage.getUserName());
				authorizedLog.setCreatedDate(new Date());

				try {
					authorizedLog.setAccountYearMonth(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				Customer customer = new Customer();
				customer.setCustomerId(getPkCustomerId());
				authorizedLog.setCustomerId(customer);

				CountryMaster applicationCountry = new CountryMaster();
				applicationCountry.setCountryId(sessionStateManage.getCountryId());
				authorizedLog.setAppCountryId(applicationCountry);

				CompanyMaster company = new CompanyMaster();
				company.setCompanyId(sessionStateManage.getCompanyId());
				authorizedLog.setCompanymaster(company);

				icustomerRegistrationService.saveAuthorizedLogForOTP(authorizedLog);

			}

		} catch (AMGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	//OTP implementation end
	
	public void callOMANSmartCardClientApp() throws Exception
	{
		String smartCardUrl=getOMANSmartCardAppUrl(null, null, Constants.OM_SMART_CARD_APPLICATION_TYPE, sessionStateManage.getUserName());

		HttpURLConnection servletConnection=null;
		try {

			URL url = new URL(smartCardUrl);
			servletConnection = (HttpURLConnection) url.openConnection();

			InputStream response = servletConnection.getInputStream();
			//servletConnection.setDoOutput(true);

		}catch(Exception e)
		{
			throw new Exception(e.getMessage());

		}finally{
			if(servletConnection!=null){
				servletConnection.disconnect();
			}
		}
	}

	public void processSmartCardOMANDetails() throws ParseException, AMGException, IOException
	{
		if (getCivilId() != null) {

			SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

			Calendar cal1 = Calendar.getInstance();
			cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
			Date today18 = cal1.getTime();
			if (dateformat.parse(getBirthdate()).before(today18)) {

				if (getExpirydate() != null && dateformat.parse(getExpirydate()).compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {

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
					outprocedurevalues = icustomerRegistrationService.getCustomerRefOrSave(identityTpeId, getCivilId(), Constants.Individual);
					if (outprocedurevalues.size() != 0) {
						setAllow_Ind(outprocedurevalues.get(0));
						setCustRefNo((outprocedurevalues.get(1)) == null ? "" : (outprocedurevalues.get(1)));

						if (getAllow_Ind().equalsIgnoreCase(Constants.Yes)) {

							smartCardInfoList = icustomerRegistrationService.getSmartCardData(getCivilId());

							if (smartCardInfoList.size() > 0) {

								setCivilId(smartCardInfoList.get(0).getIdno());								
								if (smartCardInfoList.get(0).getGender().equalsIgnoreCase("F")) {
									setTitle(generalService
											.getComponentId(Constants.TITLE_FOR_MRS_NAME, sessionStateManage.getLanguageId())
											.getFsBizComponentData().getComponentDataId().toString());
									setTitleEn(Constants.TITLE_FOR_MRS_NAME);
									setTitlel(Constants.LOCAL_TITLE_FOR_MRS);
									if (sessionStateManage.getLanguageId().toString().equalsIgnoreCase(Constants.ENGLISH_LANGUAGE_ID)) {
										setGender(Constants.GENDER_FEMALE);
									} else {
										setGender(Constants.GENDER_FEMALE_ARABIC);
									}
								} else if(smartCardInfoList.get(0).getGender().equalsIgnoreCase("M")) {
									setTitle(generalService
											.getComponentId(Constants.TITLE_FOR_MR_NAME, sessionStateManage.getLanguageId())
											.getFsBizComponentData().getComponentDataId().toString());
									setTitleEn(Constants.TITLE_FOR_MR_NAME);
									setTitlel(Constants.LOCAL_TITLE_FOR_MR);
									if (sessionStateManage.getLanguageId().toString().equalsIgnoreCase(Constants.ENGLISH_LANGUAGE_ID)) {
										setGender(Constants.GENDER_MALE);
									} else {
										setGender(Constants.GENDER_MALE_ARABIC);
									}
								}else{

									setTitleEn(null);
									setTitlel(null);
									setGender(null);
									setBooTitleKwt(false);
									setBooTitleOman(true);
								}

								// setBirthdate(new
								// SimpleDateFormat("dd/MM/yyyy").format(smartCardInfoList.get(0).getBirthdat()));
								// setTelephone1(smartCardInfoList.get(0).getTel1());
								setTelephone2(smartCardInfoList.get(0).getTel2());
								// setScardemail(smartCardInfoList.get(0).getEmailId());
								setBloodType(smartCardInfoList.get(0).getBloodTyp());
								setBuilding_no(smartCardInfoList.get(0).getBldgno());


								setScardfloor(smartCardInfoList.get(0).getFloorno());
								// setScardstreet(smartCardInfoList.get(0).getStreet());
								// setScarddistrict(smartCardInfoList.get(0).getDistrict());
								setsCardblock(smartCardInfoList.get(0).getBlockNo());
								setGuardianCivilId(smartCardInfoList.get(0).getGuardIdno());

								setAddressRef(smartCardInfoList.get(0).getAddrUniqKey());

								setCreatedByCustomer(smartCardInfoList.get(0).getCreator());
								setCreationDateCustomer(smartCardInfoList.get(0).getCrtdat());
								setSmartCardId(smartCardInfoList.get(0).getDmsIdMasId());
							}

							BigDecimal idtypeCivilIdnew = generalService
									.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId()).getFsBizComponentData()
									.getComponentDataId();
							if (getIdType() != null && idtypeCivilIdnew != null && getIdType().equalsIgnoreCase(identityTpeId.toString())
									|| getIdType().equalsIgnoreCase(idtypeCivilIdnew.toString())) {
								customerIdProofList = icustomerRegistrationService.getCustomerIdProofCheck(identityTpeId, getIdNumber(),
										sessionStateManage.getCountryId());

								if (customerIdProofList.size() == 0) {
									customerIdProofList = icustomerRegistrationService.getCustomerIdProofCheck(idtypeCivilIdnew,
											getIdNumber(), sessionStateManage.getCountryId());
								}

							}

							if (customerIdProofList.size() > 0) {

								if (customerIdProofList.get(0).getFsCustomer().getIsActive() != null
										&& customerIdProofList.get(0).getFsCustomer().getIsActive().equalsIgnoreCase(Constants.D)) {

									RequestContext.getCurrentInstance().execute("customeralreadydeactive.show();");
									// FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationmain.xhtml");
									return ;
								} else {

									setPkCustomerId(customerIdProofList.get(0).getFsCustomer().getCustomerId());

									getNationalityAlphacode();
									fetchSmartcardDataFromDab();
									setCustomerIdProof();
									setIdTypeproof(identityTpeId);
									setIdType(identityTpeId.toString());
									setDateExp(new SimpleDateFormat("dd/MM/yyyy").parse(getExpirydate()));
									setIdnumberProof(getCivilId());
									setReadOnlyIdNumber(true);
								}

							} else {
								getFetchContactTypeList();
								getNationalityAlphacode();
								setChangeDobPass(true);
								setIdTypeproof(identityTpeId);
								setIdType(identityTpeId.toString());
								setDateExp(new SimpleDateFormat("dd/MM/yyyy").parse(getExpirydate()));
								setIdnumberProof(getCivilId());
								setReadOnlyIdNumber(true);
							}

							BigDecimal nameLenght = icustomerRegistrationService.fetchNamelength(getNationality());
							String nameConcat = getFirstNameEn().trim() + " " + getMiddleNameEn().trim() + " " + getLastNameEn().trim();
							String nameConcatLocal = getFirstNameAr().trim() + " " + getMiddleNameArabic().trim() + " "
									+ getLastNameArabic().trim();
							countWordEnglish(nameConcat);
							countWordLocal(nameConcatLocal);
							if (nameLenght.intValue() != 0 && getWordCountEn() < nameLenght.intValue()) {
								setRenderNameCardEn(false);
								setRenderNameCardEnEdit(true);
							}
							if (nameLenght.intValue() != 0 && getWordCountAr() < nameLenght.intValue()) {
								setRenderNameCardlocal(false);
								setRenderNameCardlocalEdit(true);
							}

							String isActive = null;
							if (customerIdProofList != null && customerIdProofList.size() > 0)
								isActive = getActiveCustomerCheckFromView(sessionStateManage.getCountryId(), customerIdProofList.get(0)
										.getFsCustomer().getCustomerId());
							// activeCustomerList =
							// generalService.getActiveCustomerFromView(sessionStateManage.getCountryId(),
							// customerIdProofList.get(0).getFsCustomer().getCustomerId());
							System.out.println("Rabil activeCustomerList :" + isActive);
							if (isActive != null && isActive.equalsIgnoreCase("Y")) {
								RequestContext.getCurrentInstance().execute("actCustCheck.show();");
							} else {
								FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customersmartcardinfo.xhtml");
							}

						} else {
							setBooManual(false);
							setBoosmartCardAppear(true);
							setBooIdDetail(true);
							setRenderverication(false);
							FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationmain.xhtml");
							RequestContext.getCurrentInstance().execute("printCustomerReference.show();");
						}
					}

				} else {
					RequestContext.getCurrentInstance().execute("idexpiredcard.show();");
					return ;
				}

			}else {
				RequestContext.getCurrentInstance().execute("dobcheck.show();");
			}

		}
	}


	//To fetch the smarcard details from 
	public void fetchOmanSmartCardDisplay() throws AMGException
	{
		boolean recordFound = false;
		int i = 0;

		while(!recordFound)	{
			// System.out.println("while loop"+recordFound+"\t  I:"+i);

			if(i<=20000){	 
				List<ViewOMIDTemp> lstViewOMIDTemp=icustomerRegistrationService.getOMANSamrCardDetails(sessionStateManage.getUserName());
				if(lstViewOMIDTemp!=null && lstViewOMIDTemp.size()>0)
				{
					ViewOMIDTemp viewOMIDTemp =lstViewOMIDTemp.get(0);
					setCivilId(viewOMIDTemp.getCivilID().toString());
					setFullNameEn(viewOMIDTemp.getCardName());
					//setLastNameEn(viewOMIDTemp.getCardLFName());
					setNationalityLatin(viewOMIDTemp.getCardNtCd());
					setNationalityAr(viewOMIDTemp.getCardNtlcdAr());
					setGender(viewOMIDTemp.getCardGender());
					setGenderAr(viewOMIDTemp.getCardGenderAr());
					setNationality(viewOMIDTemp.getCardNtcdId());
					setAdditinal2(viewOMIDTemp.getCardSponsName()==null?"":viewOMIDTemp.getCardSponsName());
					setEmployer(viewOMIDTemp.getCardSponsName()==null?"":viewOMIDTemp.getCardSponsName());
					//setIssuedate(viewOMIDTemp.getc);
					setExpirydate(new SimpleDateFormat("dd/MM/yyyy").format(viewOMIDTemp.getCardExpireDate()));
					setBirthdate(new SimpleDateFormat("dd/MM/yyyy").format(viewOMIDTemp.getCardBirthDate()));
					//setIssuedate(new SimpleDateFormat("dd/MM/yyyy").format(issueDate));
					assignFirstMiddleLastNameForEn(viewOMIDTemp.getCardName());
					assignFirstMiddleLastNameForArbic(viewOMIDTemp.getCardLFName());
					assignTitlBanedOnGender(viewOMIDTemp.getCardGender(),viewOMIDTemp.getCardGenderAr());
					recordFound = true;
					break;
				}
			}
			i++;
			if( i == 19999){
				break;
			}
		}	
	}


	private void assignFirstMiddleLastNameForEn(String fullNameEn)
	{


		String firstName = "";
		String middleName = "";
		StringBuilder lastName = new StringBuilder();
		StringTokenizer tokenizer = new StringTokenizer(fullNameEn);

		int countToken = tokenizer.countTokens();
		for (int ii = 0; ii < countToken; ii++) {
			if (ii == 0) {
				firstName = tokenizer.nextToken();
				setFirstNameEn(firstName);
			} else if (ii == 1) {
				middleName = tokenizer.nextToken();
				setMiddleNameEn(middleName);
			} else {

				if (lastName != null && lastName.length() > 0) {
					lastName.append(" ").append(tokenizer.nextToken());
				} else {
					lastName.append(tokenizer.nextToken().trim());
				}
			}

		}
		String lstNameCheck = lastName == null ? "" : lastName.toString();

		if(lstNameCheck!=null)
		{
			setLastNameEn(lstNameCheck.trim());
		}


	}

	private void assignFirstMiddleLastNameForArbic(String fullArbicName)
	{
		setFullNameAr(fullArbicName);
		setArabicSurnameLocal(fullArbicName);
		StringTokenizer tokenizer = new StringTokenizer(fullArbicName);
		String firstName = "";
		String middleName = "";
		StringBuilder lastName = new StringBuilder();

		int countToken = tokenizer.countTokens();
		for (int ii = 0; ii < countToken; ii++) {
			if (ii == 0) {
				firstName = tokenizer.nextToken();
				setFirstNameAr(firstName);
			} else if (ii == 1) {
				middleName = tokenizer.nextToken();
				setMiddleNameArabic(middleName);
				setFatherNameArabic(middleName);
			} else {
				// lastName.append(tokenizer.nextToken()).append(" ");

				if (lastName != null && lastName.length() > 0) {
					lastName.append(" ").append(tokenizer.nextToken());
				} else {
					lastName.append(tokenizer.nextToken().trim());
				}

			}

		}

		String lstNameCheck = lastName == null ? "" : lastName.toString();

		if(lstNameCheck!=null)
		{
			setLastNameArabic(lastName == null ? "" : lastName.toString());
			setArabicGFName(lastName == null ? "" : lastName.toString());
		}

	}
	private void assignTitlBanedOnGender(String genderEn,String gederAr)
	{

		setGenderLatin(genderEn);
		if (genderEn!=null && genderEn.equalsIgnoreCase("F")) {
			setBooTitleKwt(false);
			setBooTitleOman(true);
			setTitle(generalService.getComponentId(Constants.TITLE_FOR_MRS_NAME, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString());
			setTitleEn(Constants.TITLE_FOR_MRS_NAME);
			setTitlel(Constants.LOCAL_TITLE_FOR_MRS);

			if (sessionStateManage.getLanguageId().toString().equalsIgnoreCase(Constants.ENGLISH_LANGUAGE_ID)) {
				setGender(Constants.GENDER_FEMALE);
			} else {
				setGender(Constants.GENDER_FEMALE_ARABIC);
			}

		}else if(genderEn!=null && genderEn.equalsIgnoreCase("M")){
			setBooTitleKwt(false);
			setBooTitleOman(true);
			setTitle(generalService.getComponentId(Constants.TITLE_FOR_MR_NAME, sessionStateManage.getLanguageId())
					.getFsBizComponentData().getComponentDataId().toString());
			setTitleEn(Constants.TITLE_FOR_MR_NAME);
			setTitlel(Constants.LOCAL_TITLE_FOR_MR);
			if (sessionStateManage.getLanguageId().toString().equalsIgnoreCase(Constants.ENGLISH_LANGUAGE_ID)) {
				setGender(Constants.GENDER_MALE);
			} else {
				setGender(Constants.GENDER_MALE_ARABIC);
			}
		}else{
			setTitleEn(null);
			setTitlel(null);
			setGender(null);
			setBooTitleKwt(false);
			setBooTitleOman(true);
		}

	}



	// Get URl for reading OMAN smard card
	public String getOMANSmartCardAppUrl(BigDecimal docno, BigDecimal finyear,String applicationType, String userName) {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		String servleturl = "http://" + ipAddress + ":8085/WUApp?documentNo=" + docno + "&documentFinanceYear=" + finyear+ "&applicationType=" + applicationType+ "&userName=" + userName;

		return servleturl;
	}

	public Boolean getBooTitleKwt() {
		return booTitleKwt;
	}
	public void setBooTitleKwt(Boolean booTitleKwt) {
		this.booTitleKwt = booTitleKwt;
	}

	public Boolean getBooTitleOman() {
		return booTitleOman;
	}
	public void setBooTitleOman(Boolean booTitleOman) {
		this.booTitleOman = booTitleOman;
	}

	public Boolean getBooRenderOtpAuthPanel() {
		return booRenderOtpAuthPanel;
	}
	public void setBooRenderOtpAuthPanel(Boolean booRenderOtpAuthPanel) {
		this.booRenderOtpAuthPanel = booRenderOtpAuthPanel;
	}

	public BigDecimal getOtpRetry() {
		return otpRetry;
	}
	public void setOtpRetry(BigDecimal otpRetry) {
		this.otpRetry = otpRetry;
	}

	public Date getOtpRetryDate() {
		return otpRetryDate;
	}
	public void setOtpRetryDate(Date otpRetryDate) {
		this.otpRetryDate = otpRetryDate;
	}

	public Date getOtpVerifiedDate() {
		return otpVerifiedDate;
	}
	public void setOtpVerifiedDate(Date otpVerifiedDate) {
		this.otpVerifiedDate = otpVerifiedDate;
	}

	public String getOtpVerifiedBy() {
		return otpVerifiedBy;
	}
	public void setOtpVerifiedBy(String otpVerifiedBy) {
		this.otpVerifiedBy = otpVerifiedBy;
	}
	
	public Boolean getBooMobileForOTP() {
		return booMobileForOTP;
	}
	public void setBooMobileForOTP(Boolean booMobileForOTP) {
		this.booMobileForOTP = booMobileForOTP;
	}

	public void enableAuthorization(){

		try {

			if(getOtpNo() != null){
				String rtnMessage;

				rtnMessage = icustomerRegistrationService.verifyOtpNo(getOtpNo(), getPkCustomerId(), sessionStateManage.getUserName());

				if(rtnMessage!=null)
				{
					if(rtnMessage.equalsIgnoreCase("MATCH"))
					{
						// dont display
						setOtpAuthorizedBy(null);
						setOtpAuthorizedPassword(null);
						setOtpRemarks(null);
						setBooRenderOtpAuthPanel(false);
					}else if(rtnMessage.equalsIgnoreCase("OTPNOTMATCH"))
					{
						setOtpAuthorizedBy(null);
						setOtpAuthorizedPassword(null);
						setOtpRemarks(null);
						setBooRenderOtpAuthPanel(true);
					}
				}else{
					setOtpAuthorizedBy(null);
					setOtpAuthorizedPassword(null);
					setOtpRemarks(null);
					setBooRenderOtpAuthPanel(true);
				}
			}else{
				setOtpAuthorizedBy(null);
				setOtpAuthorizedPassword(null);
				setOtpRemarks(null);
				setBooRenderOtpAuthPanel(true);
			}
		} catch (AMGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void gotoWesternUnionUpload() {

		try {

			WUTranxFileUploadBean wuTranxFileUploadBean = (WUTranxFileUploadBean)appContext.getBean("wuTranxFileUploadBean");

			wuTranxFileUploadBean.setIdNo(getIdNumber());
			if(getIdType() != null){
				wuTranxFileUploadBean.setIdType(new BigDecimal(getIdType()));
				wuTranxFileUploadBean.setSelectCard(new BigDecimal(getIdType()));
			}
			wuTranxFileUploadBean.setCustomerID(getPkCustomerId());
			wuTranxFileUploadBean.goFromOldSmartCardpanel();

			FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/wutranxfileuploaddetail.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// customer duplicate details
	public String customerDuplicateTypeId;
	public String customerDuplicateIdNo;
	public String customerDuplicateCustRef;

	public String getCustomerDuplicateTypeId() {
		return customerDuplicateTypeId;
	}
	public void setCustomerDuplicateTypeId(String customerDuplicateTypeId) {
		this.customerDuplicateTypeId = customerDuplicateTypeId;
	}

	public String getCustomerDuplicateIdNo() {
		return customerDuplicateIdNo;
	}
	public void setCustomerDuplicateIdNo(String customerDuplicateIdNo) {
		this.customerDuplicateIdNo = customerDuplicateIdNo;
	}

	public String getCustomerDuplicateCustRef() {
		return customerDuplicateCustRef;
	}
	public void setCustomerDuplicateCustRef(String customerDuplicateCustRef) {
		this.customerDuplicateCustRef = customerDuplicateCustRef;
	}

	@Autowired
	ApllicationMailer1 mailService;
	
	private void checkMailVerfication(BigDecimal customerIdPk)
	{ 
		try{
			StringBuffer engNameConcat = new StringBuffer();
			/* CBK Black list customer added by Rabil */
			if (getFirstName() != null) {
				engNameConcat.append(getFirstName().trim());
			}
			if (getMiddleName() != null) {
				engNameConcat.append(" ").append(getMiddleName().trim());
			}
			if (getLastName() != null) {
				engNameConcat.append(" ").append(getLastName().trim());
			}

			if(customerIdPk!=null) 
			{
				if(getEmail()!=null && !getEmail().equalsIgnoreCase(""))
				{
					List<String> customerList = icustomerRegistrationService.getCustomerEmailDetails(customerIdPk);

					if(customerList!=null && customerList.size()>0)
					{
						String customerEmail=customerList.get(0);
						String customerEmailVerifiedOn=null;
						if(customerList.size()>1)
						{
							customerEmailVerifiedOn=customerList.get(1);
						}
						
						if((customerEmail==null) || (customerEmail!=null  && !customerEmail.equalsIgnoreCase(getEmail()))) 
						{
							//Update customer table ,email verified on to null
							icustomerRegistrationService.updateCustomerEmailVerifiedOn(customerIdPk);
							
							//Send verification mail to new email
							sendEmail(customerIdPk, engNameConcat.toString());

						}else if((customerEmail.equalsIgnoreCase(getEmail()) && customerEmailVerifiedOn== null))
						{
							sendEmail(customerIdPk, engNameConcat.toString());
						}

					}
				}else
				{
					//Update customer table ,email verified on to null
					icustomerRegistrationService.updateCustomerEmailVerifiedOn(customerIdPk);
				}
				
			}else
			{
				//Send verification mail to customer
				sendEmail(customerIdPk, engNameConcat.toString());
			}
		}catch(Exception e)
		{
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

	}
	
	public void sendEmail(BigDecimal customerIdPk,String customerName) throws com.lowagie.text.DocumentException, IOException, AddressException, javax.mail.MessagingException, AMGException
	{
		if(getEmail()!=null && !getEmail().equals("")){
			String subject = "Activation Email";
			List<ApplicationSetup> lstApplicationSetup=iPersonalRemittanceService.getEmailFromAppSetup(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId());
			mailService.sendCustomerEmailVerification(lstApplicationSetup, getEmail(), subject, customerName,customerIdPk);
		}

	}
	
	// check for KNET  - OTHERS 10 not allowed
	public boolean checkKNETBanks(){
		boolean checkStatus = false;
		if(getColBankCode() != null && getColBankCode().equalsIgnoreCase("10")){
			checkStatus = true;
		}
		return checkStatus;
	}
	
}
