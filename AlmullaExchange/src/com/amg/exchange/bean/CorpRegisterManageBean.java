package com.amg.exchange.bean;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialException;
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
import org.springframework.stereotype.Component;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.amg.exchange.common.bean.RuleEngine;
import com.amg.exchange.common.model.Amlstatus;
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
import com.amg.exchange.mail.ApplicationMailer;
import com.amg.exchange.registration.bean.CorporateSessionValues;
import com.amg.exchange.registration.bean.CustomerPersonalInfoBean;
import com.amg.exchange.registration.model.ArcmateScanMaster;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CorpatePartnerInfoView;
import com.amg.exchange.registration.model.CorporateCustomerInfoView;
import com.amg.exchange.registration.model.CustCorporateAddlDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerContactDetailView;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerIdproofView;
import com.amg.exchange.registration.model.CustomerInfoView;
import com.amg.exchange.registration.model.DocumentImg;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.registration.model.PartnerAuthorized;
import com.amg.exchange.registration.model.ScanIdTypeMaster;
import com.amg.exchange.registration.service.IBranchPageService;
import com.amg.exchange.registration.service.ICompanyMasterservice;
import com.amg.exchange.registration.service.ICorporateRegService;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.registration.service.IEmployeeService;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.CollectionUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.ReadScanProperties;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings({ "unused"})
@Component("corpregisterBean")
@Scope("session")
public class CorpRegisterManageBean<T> implements Serializable {
	private static Logger log = Logger.getLogger(CorpRegisterManageBean.class);
	private static final long serialVersionUID = 1L;
	private Boolean renderSuccess = false;
	private BigDecimal dealYearId;
	private String userDealYear;
	private BigDecimal userDealYearId;
	private BigDecimal companyCode;
	private String dealYear;
	private String successMessage = null;
	private String countryName;
	private BigDecimal paidUpCapital;
	private Boolean rendermandatory = false;
	private Boolean renderTransactionalSetting = false;
	private String email1;
	private Boolean changeCrNumber = true;
	private Boolean immediate = false;
	private Boolean booMobilecheck = false;
	private Boolean booEmailCheck = false;
	private BigDecimal dailyLimit;
	private BigDecimal weeklyLimit;
	private BigDecimal monthlyLimit;
	private BigDecimal quarterlyLimit;
	private BigDecimal halfyearly;
	private BigDecimal annualLimit;
	private BigDecimal updateCustomerRefNo;
	private Boolean imageUploadChecckForComapny = false;
	private Boolean imageUploadChecckForPartner = false;
	private String buildingNo = null;
	private boolean enableScan = true;
	private boolean enableCheckId = false;
	private String dobS;
	private String expDateS;
	private String idNumberScan;
	private Date partnerCustExpDate;
	private boolean enablePartnerScan;
	private boolean enabledddCompanyDataTable = false;
	private Boolean enabledddPartnerAuthDataTable = false;
	private static final String COMPANYIDTYPE = "Identity Type";
	private String crno;
	private String compName;
	private String compNameL;
	private Date compRegDate;
	private Date dateAdderForReg = new Date();
	private boolean disableDate = false;
	private boolean disableAML = true;
	private String email;
	private BigDecimal location;
	private Part part;
	private List<CountryMaster> countryCodeWithId;
	private List<StateMaster> stateCodeWithId;
	private String contactPerson;
	private String contactNumber;
	private String hitCount;
	// private Integer tel;
	// contact Details
	private BigDecimal contType;
	private String area;
	private BigDecimal countryId;
	private String street;
	private String block;
	private String tel;
	private String flat;
	private BigDecimal stateId;
	private BigDecimal distId;
	private BigDecimal cityId;
	private BigDecimal contactDetailId = null;
	private Boolean duplicateContact = false;
	private Boolean contactListSizeVisibility = false;
	// Company Identity Document
	private String idno;
	private BigDecimal idtype;
	private Date idExpDate;
	private Part coPart;
	private String iDNoStatus;
	private String expDateAdder;
	private String expminDate;
	private Boolean coIdVisibility = false;
	private Boolean coIdTypeVisibility = false;
	private Boolean coExpDateVisibility = false;
	private Boolean coDublicateVisibility = false;
	private Boolean coUploadFileVisibility = false;
	private Boolean identityListVisibility = false;
	// Partners Details
	private String partName;
	private String partNameLocal;
	private String pidno;
	private BigDecimal pidtype;
	private Date pidExpDate;
	private BigDecimal partnerCustomerId;
	private Part ppart;
	private List<CustomerIdProof> partnerIdNoList;
	private String pIdNoStatus;
	private Boolean partnerUploadFileVisibility = false;
	private Date effectiveMinDate = new Date();
	private Part spart;
	private List<CustomerIdProof> authorSigIdNoList;
	private String sigIdNoStatus;
	private Boolean authourisedDublicateVisibility = false;
	private Boolean authorUploadFileVisibility = false;
	private Boolean authourisedListVisibility = false;
	//
	private BigDecimal primaryObjId;
	private BigDecimal seconObj;
	private BigDecimal custCorporateAdditionalId = null;
	private BigDecimal custCorporateAdditionalIdForBizNature = null;
	private BigDecimal customerIdProofIdForPartner = null;
	private BigDecimal customerIdProofIdForCompanyIdentityDoc = null;
	private BigDecimal customerIdProofIdForOwner = null;
	private BigDecimal bussNature;
	private String obj;
	private String errmsg;
	private BigDecimal corporateBusenessNatureId = null;
	/** Date Format Initialization */
	private final static String DATE_FORMAT = "dd/MM/yyyy";
	// Company Identity Document
	private boolean isImageSave = true;
	private List<CustomerIdProof> viewCustomerIdProof = new ArrayList<CustomerIdProof>();
	private Map<BigDecimal, String> mapObjectives = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> drpObjective1 = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> drpObjective2 = new HashMap<BigDecimal, String>();
	// image upload and download
	private UploadedFile identityfile;
	private UploadedFile partnerfile;
	private UploadedFile autherfile;
	private StreamedContent downloadFile;
	private StreamedContent downloadFileForCompany;
	private StreamedContent downloadFileForPartner;
	private StreamedContent downloadFileForOwner;
	// for customer type
	String partnerCustomer = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("partnerCust");
	String ownerCustomer = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ownerCust");
	String corporateCustomer = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("corporateCust");
	private BigDecimal image_id;
	private BigDecimal companyImageId;
	private BigDecimal partnerImageId;
	private BigDecimal ownerImageId;
	private boolean enabledSignatoriesDataTable;
	// Hard Coded Values
	private BigDecimal bdCompanyIdType = new BigDecimal(67);
	private BigDecimal bdAuthorizedIdType = new BigDecimal(3);
	private BigDecimal bdPartnerIdType = new BigDecimal(4);
	private Map<BigDecimal, String> mapCountryList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapDistrictList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapStateList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapCityList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapIdentityList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapCorporateBussineesList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapPartnerDetailsList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapAuthorSiganatureList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapObjectiveList = new HashMap<BigDecimal, String>();
	private List<AddContactDetailBean> contactListDelete = new ArrayList<AddContactDetailBean>();
	private List<AddPartnerDetailBean> partnerListDeleted = new ArrayList<AddPartnerDetailBean>();
	private List<AddAuthSignatoriesBean> authorisedListDeleted = new ArrayList<AddAuthSignatoriesBean>();
	private final ArrayList<AddCoIdentityDetailBean> identityListDeleted = new ArrayList<AddCoIdentityDetailBean>();
	private List<AddSecondaryObjectiveBean> secondryObjectiveListDeleted = new ArrayList<AddSecondaryObjectiveBean>();
	private List<AddNatureofBussnessBean> bussenessNatureListDeleted = new ArrayList<AddNatureofBussnessBean>();
	private BigDecimal customerId = null;
	private String id;
	private UIComponent tableForm;
	private List<Customer> empList;
	private String statusMessage;
	private String AMLStatus;
	private List<Amlstatus> coAMLList;
	private boolean renderValidate = true;
	private boolean renderObjective = false;
	private boolean renderContactDetails = false;
	private boolean renderCoIdentityDoc = false;
	private boolean renderPartnerDetail = false;
	private boolean renderOwnerDetails = false;
	private boolean coAMLPass = true;
	private boolean coAMLFail = false;
	private boolean coAMLCommon = true;
	private boolean coValdtBtn = true;
	private boolean coSave = true;
	private boolean readOnly = false;
	private boolean readOnlyCompanyIdDoc = false;
	private boolean disableCompIdDco = false;
	private boolean saveOrFetch = true;
	//private String userName = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userName").toString();
	private BigDecimal customerTypeId;
	private ContactDetail contdetail = null;
	private List<CustomerIdProof> coIdentyList;
	private CustCorporateAddlDetail custCorpAddDetail = null;
	private CustomerIdProof custmrIdProof = null;
	private Customer cust = null;
	private CountryMaster countryMstr = null;
	private CompanyMaster companyMstr = null;
	private LanguageType langType = null;
	private StateMaster stateMaster = null;
	private DistrictMaster districtMaster = null;
	private CountryMaster countryMaster = null;
	private CityMaster cityMaster = null;
	private CompanyMaster companyMaster = null;
	SessionStateManage sessionStateManage = new SessionStateManage();
	private List<CountryBean> countryList = new ArrayList<CountryBean>();
	private List<Customer> customerList;
	DocumentImg document = new DocumentImg();
	private String createdByCustomer;
	private Date createdDateCustomer;
	private String createdBySecondary;
	private Date createdDateSecondary;
	private String createdByBusseness;
	private Date createdDateBusseness;
	private String createdByContact;
	private Date createdDateContact;
	private String createdByComapany;
	private Date createdDateCompany;
	private String createdByPartner;
	private Date createdDatePartner;
	private String createdByOwner;
	private Date createdDateOwner;
	private boolean renderRegisterPartner = false;
	private String registerId;
	private boolean readOnlyPartnerName = false;
	// private boolean addPartner = false;
	private boolean renderImage = false;
	private BigDecimal article = null;
	private BigDecimal levelId = null;
	private List<ArticleDetails> lstLevel = new ArrayList<ArticleDetails>();
	private InputStream inputStream;
	private Date minDate;
	private boolean disableEndDate = true;
	private List<BigDecimal> seconObjList = new ArrayList<BigDecimal>();
	private Map<BigDecimal, String> mapPartnerIdentityType = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapAuthourIdentityType = new HashMap<BigDecimal, String>();
	private String ownerRegisterId;
	private Boolean partnerIdRegStatus = false;
	private Boolean ownerIdRegStatus = false;
	private Boolean duplicatePartnerDetails = false;
	private Boolean renderPartneListAvailability = false;
	private BigDecimal ownerCustomerId;
	private Boolean renderRegisterOwner = false;
	private Boolean duplicateOwnerDetails = false;
	private Boolean renderOwnerListAvailability = false;
	private Date currentTime;
	private String partnerContactNumber;
	private String partnerEmail;
	private Boolean booPartnerContactNumberReadOnly;
	private Boolean boopartnerEmailReadOnly;
	private boolean enableContactDataTable;
	private Boolean booUpdate;
	private BigDecimal incomeRange = null;
	private String occupation;
	private Date auditStatementDate;
	private BigDecimal auditGrossIncome;
	private Date auditMinDate;
	private Date auditMaxDate;
	
	private String exceptionMessage=null;
	private String userName = sessionStateManage.getUserName();
	
	private List<Customer> cutList;
	private final ArrayList<AddContactDetailBean> contactList = new ArrayList<AddContactDetailBean>();
	private final ArrayList<AddCoIdentityDetailBean> identityList = new ArrayList<AddCoIdentityDetailBean>();
	private final ArrayList<AddPartnerDetailBean> partnerList = new ArrayList<AddPartnerDetailBean>();
	private final ArrayList<AddNatureofBussnessBean> bussnessList = new ArrayList<AddNatureofBussnessBean>();
	private final ArrayList<AddAuthSignatoriesBean> authorisedList = new ArrayList<AddAuthSignatoriesBean>();
	private final ArrayList<AddSecondaryObjectiveBean> secondaryList = new ArrayList<AddSecondaryObjectiveBean>();
	private Map<BigDecimal, String> mapcomIdentityType = new HashMap<BigDecimal, String>();
	private List<StateMasterDesc> lstStates = new ArrayList<StateMasterDesc>();
	private List<DistrictMasterDesc> lstDistrict = new ArrayList<DistrictMasterDesc>();
	private List<CityMasterDesc> lstcity = new ArrayList<CityMasterDesc>();
	private List<UserFinancialYear> DealYearList = new ArrayList<UserFinancialYear>();
	private Map<BigDecimal, String> mapContactTypeList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> idTypeMap = new HashMap<BigDecimal, String>();
	
	private List<IncomeRangeMaster> lstIncomeRange = new ArrayList<IncomeRangeMaster>();
	
	@Autowired
	private ICorporateRegService<T> corpRegService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	private RuleEngine<T> ruleEngine;
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	private @Autowired
	ApplicationMailer mailService;
	private @Autowired
	IBranchPageService<T> branchServiec;
	@Autowired
	IEmployeeService iEmployeeService;
	@Autowired
	ApllicationMailer1 mailService1;
	/*@Autowired(required = true)
	JavaMailSender mailSender1;*/
	@Autowired
	ICompanyMasterservice icompanyMaster;
	
	
	
	
	public CorpRegisterManageBean() {
	}

	
	private Boolean checkPartnerDetailsRequired=false;

	public Boolean getCheckPartnerDetailsRequired() {
		return checkPartnerDetailsRequired;
	}

	public void setCheckPartnerDetailsRequired(Boolean checkPartnerDetailsRequired) {
		this.checkPartnerDetailsRequired = checkPartnerDetailsRequired;
	}
	
	public String getExpminDate() {
		return expminDate;
	}

	public void setExpminDate(String expminDate) {
		this.expminDate = expminDate;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public List<IncomeRangeMaster> getLstIncomeRange() {
		return lstIncomeRange;
	}

	public void setLstIncomeRange(List<IncomeRangeMaster> lstIncomeRange) {
		this.lstIncomeRange = lstIncomeRange;
	}

	public BigDecimal getIncomeRange() {
		return incomeRange;
	}

	public void setIncomeRange(BigDecimal incomeRange) {
		this.incomeRange = incomeRange;
	}

	public Boolean getBooPartnerContactNumberReadOnly() {
		return booPartnerContactNumberReadOnly;
	}

	public void setBooPartnerContactNumberReadOnly(Boolean booPartnerContactNumberReadOnly) {
		this.booPartnerContactNumberReadOnly = booPartnerContactNumberReadOnly;
	}

	public Boolean getBoopartnerEmailReadOnly() {
		return boopartnerEmailReadOnly;
	}

	public void setBoopartnerEmailReadOnly(Boolean boopartnerEmailReadOnly) {
		this.boopartnerEmailReadOnly = boopartnerEmailReadOnly;
	}

	public String getPartnerContactNumber() {
		return partnerContactNumber;
	}

	public void setPartnerContactNumber(String partnerContactNumber) {
		this.partnerContactNumber = partnerContactNumber;
	}

	public String getPartnerEmail() {
		return partnerEmail;
	}

	public void setPartnerEmail(String partnerEmail) {
		this.partnerEmail = partnerEmail;
	}

	public ApplicationMailer getMailService() {
		return mailService;
	}

	public void setMailService(ApplicationMailer mailService) {
		this.mailService = mailService;
	}

	public Date getCurrentTime() {
		Date objList = getGeneralService().getSysDateTimestamp(sessionStateManage.getCountryId());
		if (objList != null) {
			currentTime = objList;
		} else {
			currentTime = null;
		}
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public List<ArticleDetails> getLstLevel() {
		return lstLevel;
	}

	public String getPartNameLocal() {
		return partNameLocal;
	}

	public void setPartNameLocal(String partNameLocal) {
		this.partNameLocal = partNameLocal;
	}

	public Boolean getRenderOwnerListAvailability() {
		return renderOwnerListAvailability;
	}

	public void setRenderOwnerListAvailability(Boolean renderOwnerListAvailability) {
		this.renderOwnerListAvailability = renderOwnerListAvailability;
	}

	public Boolean getDuplicateOwnerDetails() {
		return duplicateOwnerDetails;
	}

	public void setDuplicateOwnerDetails(Boolean duplicateOwnerDetails) {
		this.duplicateOwnerDetails = duplicateOwnerDetails;
	}

	public Boolean getRenderRegisterOwner() {
		return renderRegisterOwner;
	}

	public void setRenderRegisterOwner(Boolean renderRegisterOwner) {
		this.renderRegisterOwner = renderRegisterOwner;
	}

	public BigDecimal getOwnerCustomerId() {
		return ownerCustomerId;
	}

	public void setOwnerCustomerId(BigDecimal ownerCustomerId) {
		this.ownerCustomerId = ownerCustomerId;
	}

	public Boolean getRenderPartneListAvailability() {
		return renderPartneListAvailability;
	}

	public void setRenderPartneListAvailability(Boolean renderPartneListAvailability) {
		this.renderPartneListAvailability = renderPartneListAvailability;
	}

	public Boolean getDuplicatePartnerDetails() {
		return duplicatePartnerDetails;
	}

	public void setDuplicatePartnerDetails(Boolean duplicatePartnerDetails) {
		this.duplicatePartnerDetails = duplicatePartnerDetails;
	}

	public Boolean getPartnerIdRegStatus() {
		return partnerIdRegStatus;
	}

	public void setPartnerIdRegStatus(Boolean partnerIdRegStatus) {
		this.partnerIdRegStatus = partnerIdRegStatus;
	}

	public Boolean getOwnerIdRegStatus() {
		return ownerIdRegStatus;
	}

	public void setOwnerIdRegStatus(Boolean ownerIdRegStatus) {
		this.ownerIdRegStatus = ownerIdRegStatus;
	}

	public String getOwnerRegisterId() {
		return ownerRegisterId;
	}

	public void setOwnerRegisterId(String ownerRegisterId) {
		this.ownerRegisterId = ownerRegisterId;
	}

	public List<BigDecimal> getSeconObjList() {
		return seconObjList;
	}

	public void setSeconObjList(List<BigDecimal> seconObjList) {
		this.seconObjList = seconObjList;
	}

	public boolean isDisableEndDate() {
		return disableEndDate;
	}

	public void setDisableEndDate(boolean disableEndDate) {
		this.disableEndDate = disableEndDate;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public BigDecimal getPartnerCustomerId() {
		return partnerCustomerId;
	}

	public void setPartnerCustomerId(BigDecimal partnerCustomerId) {
		this.partnerCustomerId = partnerCustomerId;
	}

	public Map<BigDecimal, String> getDrpObjective1() {
		return drpObjective1;
	}

	public void setDrpObjective1(Map<BigDecimal, String> drpObjective1) {
		this.drpObjective1 = drpObjective1;
	}

	public Map<BigDecimal, String> getDrpObjective2() {
		return drpObjective2;
	}

	public void setDrpObjective2(Map<BigDecimal, String> drpObjective2) {
		this.drpObjective2 = drpObjective2;
	}

	public UploadedFile getIdentityfile() {
		return identityfile;
	}

	public BigDecimal getLevelId() {
		return levelId;
	}

	public void setLevelId(BigDecimal levelId) {
		this.levelId = levelId;
	}

	public BigDecimal getArticle() {
		return article;
	}

	public void setArticle(BigDecimal article) {
		this.article = article;
	}

	public BigDecimal getCustCorporateAdditionalIdForBizNature() {
		return custCorporateAdditionalIdForBizNature;
	}

	public void setCustCorporateAdditionalIdForBizNature(BigDecimal custCorporateAdditionalIdForBizNature) {
		this.custCorporateAdditionalIdForBizNature = custCorporateAdditionalIdForBizNature;
	}

	public boolean isSaveOrFetch() {
		return saveOrFetch;
	}

	public void setSaveOrFetch(boolean saveOrFetch) {
		this.saveOrFetch = saveOrFetch;
	}

	public boolean isRenderImage() {
		return renderImage;
	}

	public void setRenderImage(boolean renderImage) {
		this.renderImage = renderImage;
	}

	/*
	 * public boolean isAddPartner() { return addPartner; } public void
	 * setAddPartner(boolean addPartner) { this.addPartner = addPartner; }
	 */
	public boolean isReadOnlyPartnerName() {
		return readOnlyPartnerName;
	}

	public void setReadOnlyPartnerName(boolean readOnlyPartnerName) {
		this.readOnlyPartnerName = readOnlyPartnerName;
	}

	public boolean isRenderRegisterPartner() {
		return renderRegisterPartner;
	}

	public void setRenderRegisterPartner(boolean renderRegisterPartner) {
		this.renderRegisterPartner = renderRegisterPartner;
	}

	public Map<BigDecimal, String> getMapObjectives() {
		return mapObjectives;
	}

	public void setMapObjectives(Map<BigDecimal, String> mapObjectives) {
		this.mapObjectives = mapObjectives;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public boolean isDisableCompIdDco() {
		return disableCompIdDco;
	}

	public void setDisableCompIdDco(boolean disableCompIdDco) {
		this.disableCompIdDco = disableCompIdDco;
	}

	public boolean isReadOnlyCompanyIdDoc() {
		return readOnlyCompanyIdDoc;
	}

	public void setReadOnlyCompanyIdDoc(boolean readOnlyCompanyIdDoc) {
		this.readOnlyCompanyIdDoc = readOnlyCompanyIdDoc;
	}

	public StreamedContent getDownloadFileForCompany() throws IOException {
		return downloadFileForCompany;
	}

	public void setDownloadFileForCompany(StreamedContent downloadFileForCompany) {
		this.downloadFileForCompany = downloadFileForCompany;
	}

	public StreamedContent getDownloadFileForPartner() {
		return downloadFileForPartner;
	}

	public void setDownloadFileForPartner(StreamedContent downloadFileForPartner) {
		this.downloadFileForPartner = downloadFileForPartner;
	}

	public StreamedContent getDownloadFileForOwner() {
		return downloadFileForOwner;
	}

	public void setDownloadFileForOwner(StreamedContent downloadFileForOwner) {
		this.downloadFileForOwner = downloadFileForOwner;
	}

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

	public Date getCreatedDateOwner() {
		return createdDateOwner;
	}

	public void setCreatedDateOwner(Date createdDateOwner) {
		this.createdDateOwner = createdDateOwner;
	}

	public Date getCreatedDateCustomer() {
		return createdDateCustomer;
	}

	public void setCreatedDateCustomer(Date createdDateCustomer) {
		this.createdDateCustomer = createdDateCustomer;
	}

	public Date getCreatedDateSecondary() {
		return createdDateSecondary;
	}

	public void setCreatedDateSecondary(Date createdDateSecondary) {
		this.createdDateSecondary = createdDateSecondary;
	}

	public Date getCreatedDateBusseness() {
		return createdDateBusseness;
	}

	public void setCreatedDateBusseness(Date createdDateBusseness) {
		this.createdDateBusseness = createdDateBusseness;
	}

	public Date getCreatedDateCompany() {
		return createdDateCompany;
	}

	public void setCreatedDateCompany(Date createdDateCompany) {
		this.createdDateCompany = createdDateCompany;
	}

	public Date getCreatedDatePartner() {
		return createdDatePartner;
	}

	public void setCreatedDatePartner(Date createdDatePartner) {
		this.createdDatePartner = createdDatePartner;
	}

	public String getCreatedByCustomer() {
		return createdByCustomer;
	}

	public void setCreatedByCustomer(String createdByCustomer) {
		this.createdByCustomer = createdByCustomer;
	}

	public String getCreatedBySecondary() {
		return createdBySecondary;
	}

	public void setCreatedBySecondary(String createdBySecondary) {
		this.createdBySecondary = createdBySecondary;
	}

	public String getCreatedByBusseness() {
		return createdByBusseness;
	}

	public void setCreatedByBusseness(String createdByBusseness) {
		this.createdByBusseness = createdByBusseness;
	}

	public String getCreatedByComapany() {
		return createdByComapany;
	}

	public void setCreatedByComapany(String createdByComapany) {
		this.createdByComapany = createdByComapany;
	}

	public String getCreatedByPartner() {
		return createdByPartner;
	}

	public void setCreatedByPartner(String createdByPartner) {
		this.createdByPartner = createdByPartner;
	}

	public String getCreatedByOwner() {
		return createdByOwner;
	}

	public void setCreatedByOwner(String createdByOwner) {
		this.createdByOwner = createdByOwner;
	}

	public BigDecimal getCompanyImageId() {
		return companyImageId;
	}

	public void setCompanyImageId(BigDecimal companyImageId) {
		this.companyImageId = companyImageId;
	}

	public BigDecimal getPartnerImageId() {
		return partnerImageId;
	}

	public void setPartnerImageId(BigDecimal partnerImageId) {
		this.partnerImageId = partnerImageId;
	}

	public BigDecimal getOwnerImageId() {
		return ownerImageId;
	}

	public void setOwnerImageId(BigDecimal ownerImageId) {
		this.ownerImageId = ownerImageId;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getHitCount() {
		return hitCount;
	}

	public void setHitCount(String hitCount) {
		this.hitCount = hitCount;
	}

	public BigDecimal getContactDetailId() {
		return contactDetailId;
	}

	public void setContactDetailId(BigDecimal contactDetailId) {
		this.contactDetailId = contactDetailId;
	}

	public BigDecimal getCustCorporateAdditionalId() {
		return custCorporateAdditionalId;
	}

	public void setCustCorporateAdditionalId(BigDecimal custCorporateAdditionalId) {
		this.custCorporateAdditionalId = custCorporateAdditionalId;
	}

	public BigDecimal getCustomerIdProofIdForPartner() {
		return customerIdProofIdForPartner;
	}

	public void setCustomerIdProofIdForPartner(BigDecimal customerIdProofIdForPartner) {
		this.customerIdProofIdForPartner = customerIdProofIdForPartner;
	}

	public BigDecimal getCustomerIdProofIdForCompanyIdentityDoc() {
		return customerIdProofIdForCompanyIdentityDoc;
	}

	public void setCustomerIdProofIdForCompanyIdentityDoc(BigDecimal customerIdProofIdForCompanyIdentityDoc) {
		this.customerIdProofIdForCompanyIdentityDoc = customerIdProofIdForCompanyIdentityDoc;
	}

	public BigDecimal getCustomerIdProofIdForOwner() {
		return customerIdProofIdForOwner;
	}

	public void setCustomerIdProofIdForOwner(BigDecimal customerIdProofIdForOwner) {
		this.customerIdProofIdForOwner = customerIdProofIdForOwner;
	}

	public BigDecimal getCorporateBusenessNatureId() {
		return corporateBusenessNatureId;
	}

	public void setCorporateBusenessNatureId(BigDecimal corporateBusenessNatureId) {
		this.corporateBusenessNatureId = corporateBusenessNatureId;
	}

	public boolean isRenderValidate() {
		return renderValidate;
	}

	public void setRenderValidate(boolean renderValidate) {
		this.renderValidate = renderValidate;
	}

	public boolean isRenderObjective() {
		return renderObjective;
	}

	public void setRenderObjective(boolean renderObjective) {
		this.renderObjective = renderObjective;
	}

	public boolean isRenderContactDetails() {
		return renderContactDetails;
	}

	public void setRenderContactDetails(boolean renderContactDetails) {
		this.renderContactDetails = renderContactDetails;
	}

	public boolean isRenderCoIdentityDoc() {
		return renderCoIdentityDoc;
	}

	public void setRenderCoIdentityDoc(boolean renderCoIdentityDoc) {
		this.renderCoIdentityDoc = renderCoIdentityDoc;
	}

	public boolean isRenderPartnerDetail() {
		return renderPartnerDetail;
	}

	public void setRenderPartnerDetail(boolean renderPartnerDetail) {
		this.renderPartnerDetail = renderPartnerDetail;
	}

	public boolean isRenderOwnerDetails() {
		return renderOwnerDetails;
	}

	public void setRenderOwnerDetails(boolean renderOwnerDetails) {
		this.renderOwnerDetails = renderOwnerDetails;
	}

	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}

	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}

	public Boolean getIdentityListVisibility() {
		return identityListVisibility;
	}

	public void setIdentityListVisibility(Boolean identityListVisibility) {
		this.identityListVisibility = identityListVisibility;
	}

	public Boolean getAuthourisedListVisibility() {
		return authourisedListVisibility;
	}

	public void setAuthourisedListVisibility(Boolean authourisedListVisibility) {
		this.authourisedListVisibility = authourisedListVisibility;
	}

	public Boolean getContactListSizeVisibility() {
		return contactListSizeVisibility;
	}

	public void setContactListSizeVisibility(Boolean contactListSizeVisibility) {
		this.contactListSizeVisibility = contactListSizeVisibility;
	}

	public boolean isDisableAML() {
		return disableAML;
	}

	public void setDisableAML(boolean disableAML) {
		this.disableAML = disableAML;
	}

	public Boolean getPartnerUploadFileVisibility() {
		return partnerUploadFileVisibility;
	}

	public void setPartnerUploadFileVisibility(Boolean partnerUploadFileVisibility) {
		this.partnerUploadFileVisibility = partnerUploadFileVisibility;
	}

	public Boolean getAuthorUploadFileVisibility() {
		return authorUploadFileVisibility;
	}

	public void setAuthorUploadFileVisibility(Boolean authorUploadFileVisibility) {
		this.authorUploadFileVisibility = authorUploadFileVisibility;
	}

	public Boolean getCoUploadFileVisibility() {
		return coUploadFileVisibility;
	}

	public void setCoUploadFileVisibility(Boolean coUploadFileVisibility) {
		this.coUploadFileVisibility = coUploadFileVisibility;
	}

	public void setIdentityfile(UploadedFile identityfile) {
		this.identityfile = identityfile;
	}

	public UploadedFile getPartnerfile() {
		return partnerfile;
	}

	public void setPartnerfile(UploadedFile partnerfile) throws IOException {
		this.partnerfile = partnerfile;
	}

	public UploadedFile getAutherfile() {
		return autherfile;
	}

	public void setAutherfile(UploadedFile autherfile) {
		this.autherfile = autherfile;
	}

	public boolean isDisableDate() {
		return disableDate;
	}

	public void setDisableDate(boolean disableDate) {
		this.disableDate = disableDate;
	}

	public Date getDateAdderForReg() {
		return dateAdderForReg;
	}

	public void setDateAdderForReg(Date dateAdderForReg) {
		this.dateAdderForReg = dateAdderForReg;
	}

	public Boolean getAuthourisedDublicateVisibility() {
		return authourisedDublicateVisibility;
	}

	public void setAuthourisedDublicateVisibility(Boolean authourisedDublicateVisibility) {
		this.authourisedDublicateVisibility = authourisedDublicateVisibility;
	}

	public IGeneralService<T> getiGeneralService() {
		return generalService;
	}

	public Boolean getDuplicateContact() {
		return duplicateContact;
	}

	public void setDuplicateContact(Boolean duplicateContact) {
		this.duplicateContact = duplicateContact;
	}

	public Boolean getCoDublicateVisibility() {
		return coDublicateVisibility;
	}

	public void setCoDublicateVisibility(Boolean coDublicateVisibility) {
		this.coDublicateVisibility = coDublicateVisibility;
	}

	public Boolean getCoIdVisibility() {
		return coIdVisibility;
	}

	public void setCoIdVisibility(Boolean coIdVisibility) {
		this.coIdVisibility = coIdVisibility;
	}

	public Boolean getCoIdTypeVisibility() {
		return coIdTypeVisibility;
	}

	public void setCoIdTypeVisibility(Boolean coIdTypeVisibility) {
		this.coIdTypeVisibility = coIdTypeVisibility;
	}

	public Boolean getCoExpDateVisibility() {
		return coExpDateVisibility;
	}

	public void setCoExpDateVisibility(Boolean coExpDateVisibility) {
		this.coExpDateVisibility = coExpDateVisibility;
	}

	public Map<BigDecimal, String> getMapPartnerDetailsList() {
		return mapPartnerDetailsList;
	}

	public void setMapPartnerDetailsList(Map<BigDecimal, String> mapPartnerDetailsList) {
		this.mapPartnerDetailsList = mapPartnerDetailsList;
	}

	public Map<BigDecimal, String> getMapAuthorSiganatureList() {
		return mapAuthorSiganatureList;
	}

	public void setMapAuthorSiganatureList(Map<BigDecimal, String> mapAuthorSiganatureList) {
		this.mapAuthorSiganatureList = mapAuthorSiganatureList;
	}

	public Map<BigDecimal, String> getMapObjectiveList() {
		return mapObjectiveList;
	}

	public void setMapObjectiveList(Map<BigDecimal, String> mapObjectiveList) {
		this.mapObjectiveList = mapObjectiveList;
	}

	public Map<BigDecimal, String> getMapCountryList() {
		return mapCountryList;
	}

	public void setMapCountryList(Map<BigDecimal, String> mapCountryList) {
		this.mapCountryList = mapCountryList;
	}

	public Map<BigDecimal, String> getMapDistrictList() {
		return mapDistrictList;
	}

	public void setMapDistrictList(Map<BigDecimal, String> mapDistrictList) {
		this.mapDistrictList = mapDistrictList;
	}

	public Map<BigDecimal, String> getMapStateList() {
		return mapStateList;
	}

	public void setMapStateList(Map<BigDecimal, String> mapStateList) {
		this.mapStateList = mapStateList;
	}

	public Map<BigDecimal, String> getMapCityList() {
		return mapCityList;
	}

	public void setMapCityList(Map<BigDecimal, String> mapCityList) {
		this.mapCityList = mapCityList;
	}

	public BigDecimal getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(BigDecimal customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public void setiGeneralService(IGeneralService<T> iGeneralService) {
		this.generalService = iGeneralService;
	}

	public ICorporateRegService<T> getCorpRegService() {
		return corpRegService;
	}

	public void setCorpRegService(ICorporateRegService<T> corpRegService) {
		this.corpRegService = corpRegService;
	}

	public String getiDNoStatus() {
		return iDNoStatus;
	}

	public void setiDNoStatus(String iDNoStatus) {
		this.iDNoStatus = iDNoStatus;
	}

	public BigDecimal getLocation() {
		return location;
	}

	public void setLocation(BigDecimal location) {
		this.location = location;
	}

	public boolean isEnableContactDataTable() {
		return enableContactDataTable;
	}

	public void setEnableContactDataTable(boolean enableContactDataTable) {
		this.enableContactDataTable = enableContactDataTable;
	}

	public boolean isEnabledSignatoriesDataTable() {
		return enabledSignatoriesDataTable;
	}

	public void setEnabledSignatoriesDataTable(boolean enabledSignatoriesDataTable) {
		this.enabledSignatoriesDataTable = enabledSignatoriesDataTable;
	}

	public String getpIdNoStatus() {
		return pIdNoStatus;
	}

	public void setpIdNoStatus(String pIdNoStatus) {
		this.pIdNoStatus = pIdNoStatus;
	}

	public String getSigIdNoStatus() {
		return sigIdNoStatus;
	}

	public void setSigIdNoStatus(String sigIdNoStatus) {
		this.sigIdNoStatus = sigIdNoStatus;
	}

	public BigDecimal getState() {
		return stateId;
	}

	public void setState(BigDecimal state) {
		this.stateId = state;
	}

	public BigDecimal getDist() {
		return distId;
	}

	public void setDist(BigDecimal dist) {
		this.distId = dist;
	}

	public BigDecimal getStateId() {
		return stateId;
	}

	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}

	public BigDecimal getDistId() {
		return distId;
	}

	public void setDistId(BigDecimal distId) {
		this.distId = distId;
	}

	public List<Amlstatus> getCoAMLList() {
		return coAMLList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public BigDecimal getCityId() {
		return cityId;
	}

	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}

	public void setCustomerId(BigDecimal bigDecimal) {
		this.customerId = bigDecimal;
	}

	public List<Customer> getCutList() {
		return cutList;
	}

	public boolean isCoAMLCommon() {
		return coAMLCommon;
	}

	public void setCoAMLCommon(boolean coAMLCommon) {
		this.coAMLCommon = coAMLCommon;
	}

	public ArrayList<AddContactDetailBean> getContactList() {
		return contactList;
	}

	public ArrayList<AddCoIdentityDetailBean> getIdentityList() {
		return identityList;
	}

	public ArrayList<AddPartnerDetailBean> getPartnerList() {
		return partnerList;
	}

	public ArrayList<AddNatureofBussnessBean> getBussnessList() {
		return bussnessList;
	}

	public ArrayList<AddAuthSignatoriesBean> getAuthorisedList() {
		return authorisedList;
	}

	public ArrayList<AddSecondaryObjectiveBean> getSecondaryList() {
		return secondaryList;
	}

	public List<Customer> getCutList(String id) {
		cutList = new ArrayList<Customer>();
		return cutList;
	}

	public void setCutList(List<Customer> cutList) {
		this.cutList = cutList;
	}

	public void setCountryList(List<CountryBean> countryList) {
		this.countryList = countryList;
	}

	public String getCrno() {
		return crno;
	}

	public void setCrno(String crno) {
		this.crno = crno;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getCompNameL() {
		return compNameL;
	}

	public void setCompNameL(String compNameL) {
		this.compNameL = compNameL;
	}

	public Date getCompRegDate() {
		return compRegDate;
	}

	public void setCompRegDate(Date compRegDate) {
		this.compRegDate = compRegDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFlat() {
		return flat;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}

	public BigDecimal getContType() {
		return contType;
	}

	public void setContType(BigDecimal contType) {
		this.contType = contType;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal country) {
		this.countryId = country;
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

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public BigDecimal getIdtype() {
		return idtype;
	}

	public void setIdtype(BigDecimal idtype) {
		this.idtype = idtype;
	}

	public Date getIdExpDate() {
		return idExpDate;
	}

	public void setIdExpDate(Date idExpDate) {
		this.idExpDate = idExpDate;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public BigDecimal getSeconObj() {
		return seconObj;
	}

	public void setSeconObj(BigDecimal seconObj) {
		this.seconObj = seconObj;
	}

	public BigDecimal getBussNature() {
		return bussNature;
	}

	public void setBussNature(BigDecimal string) {
		this.bussNature = string;
	}

	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	public List<Customer> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Customer> empList) {
		this.empList = empList;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getPidno() {
		return pidno;
	}

	public void setPidno(String pidno) {
		this.pidno = pidno;
	}

	public BigDecimal getPidtype() {
		return pidtype;
	}

	public void setPidtype(BigDecimal pidtype) {
		this.pidtype = pidtype;
	}

	public Date getPidExpDate() {
		return pidExpDate;
	}

	public void setPidExpDate(Date pidExpDate) {
		this.pidExpDate = pidExpDate;
	}

	public Part getPpart() {
		return ppart;
	}

	public void setPpart(Part ppart) {
		this.ppart = ppart;
	}

	public Part getSpart() {
		return spart;
	}

	public void setSpart(Part spart) {
		this.spart = spart;
	}

	public UIComponent getTableForm() {
		return tableForm;
	}

	public void setTableForm(UIComponent tableForm) {
		this.tableForm = tableForm;
	}

	public Boolean getBooUpdate() {
		return booUpdate;
	}

	public void setBooUpdate(Boolean booUpdate) {
		this.booUpdate = booUpdate;
	}

	public String getAMLStatus() {
		return AMLStatus;
	}

	public void setAMLStatus(String aMLStatus) {
		AMLStatus = aMLStatus;
	}

	public boolean isCoAMLPass() {
		return coAMLPass;
	}

	public void setCoAMLPass(boolean coAMLPass) {
		this.coAMLPass = coAMLPass;
	}

	public boolean isCoAMLFail() {
		return coAMLFail;
	}

	public void setCoAMLFail(boolean coAMLFail) {
		this.coAMLFail = coAMLFail;
	}

	public boolean isCoValdtBtn() {
		return coValdtBtn;
	}

	public void setCoValdtBtn(boolean coValdtBtn) {
		this.coValdtBtn = coValdtBtn;
	}

	public boolean isCoSave() {
		return coSave;
	}

	public void setCoSave(boolean coSave) {
		this.coSave = coSave;
	}

	public List<Amlstatus> getCoAMLList(String compName) {
		coAMLList = new ArrayList<Amlstatus>();
		coAMLList.addAll(getCorpRegService().getAMLStatusList(compName));
		return coAMLList;
	}

	public String getExpDateAdder() {
		return expDateAdder;
	}

	public void setExpDateAdder(String expDateAdder) {
		this.expDateAdder = expDateAdder;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public void setCoAMLList(List<Amlstatus> coAMLList) {
		this.coAMLList = coAMLList;
	}

	List<CountryBean> countrybean = new ArrayList<CountryBean>();

	public List<CountryBean> getCountryList1() {
		return countrybean;
	}

	public BigDecimal getPrimaryObjId() {
		return primaryObjId;
	}

	public void setPrimaryObjId(BigDecimal primaryObjId) {
		this.primaryObjId = primaryObjId;
	}

	public Map<BigDecimal, String> getMapCorporateBussineesList() {
		return mapCorporateBussineesList;
	}

	public void setMapCorporateBussineesList(Map<BigDecimal, String> mapCorporateBussineesList) {
		this.mapCorporateBussineesList = mapCorporateBussineesList;
	}

	public Map<BigDecimal, String> getMapSecoundryList() {
		return mapSecoundryList;
	}

	public void setMapSecoundryList(Map<BigDecimal, String> mapSecoundryList) {
		this.mapSecoundryList = mapSecoundryList;
	}

	Map<BigDecimal, String> mapSecoundryList = new HashMap<BigDecimal, String>();

	public Map<BigDecimal, String> getMapIdentityList() {
		return mapIdentityList;
	}

	public void setMapIdentityList(Map<BigDecimal, String> mapIdentityList) {
		this.mapIdentityList = mapIdentityList;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public BigDecimal getImage_id() {
		return image_id;
	}

	public void setImage_id(BigDecimal image_id) {
		this.image_id = image_id;
	}

	// to get partner type list and store into local map object
	public void getFetchPartnerIdentityTypeList() {
		mapPartnerIdentityType = ruleEngine.getComponentData("Partner Id Type");
	}

	// to get partner type list and store into local map object
	public void getFetchAuthorIdentityTypeList() {
		mapAuthourIdentityType = ruleEngine.getComponentData("Owner Id Type");
	}

	
	public Date getAuditStatementDate() {
		return auditStatementDate;
	}

	public void setAuditStatementDate(Date auditStatementDate) {
		this.auditStatementDate = auditStatementDate;
	}

	public BigDecimal getAuditGrossIncome() {
		return auditGrossIncome;
	}

	public void setAuditGrossIncome(BigDecimal auditGrossIncome) {
		this.auditGrossIncome = auditGrossIncome;
	}

	 public Date getAuditMinDate() {
			return auditMinDate;
		}

		public void setAuditMinDate(Date auditMinDate) {
			this.auditMinDate = auditMinDate;
		}
			
	public Date getAuditMaxDate() {
			return auditMaxDate;
		}

		public void setAuditMaxDate(Date auditMaxDate) {
			this.auditMaxDate = auditMaxDate;
		}

	/*
	 * method to add partner details in a dataTable
	 */
	public void addActionPartner() {
		// try {
		boolean duplicate = false;
		for (AddPartnerDetailBean addPartnerDetailBean : partnerList) {
			if (addPartnerDetailBean.getPidno().equals(getPidno())) {
				duplicate = true;
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('duplicateDetails').show();");
				break;
			}
		}
		if (!duplicate) {
			if (!getImageUploadChecckForPartner()) {
				setPartnerUploadFileVisibility(true);
			} else {
				AddPartnerDetailBean partner = new AddPartnerDetailBean();
				// getFetchPartnerIdentityTypeList();
				partner.setPidno(getPidno());
				partner.setPartName(getPartName());
				partner.setPartNameLocal(getPartNameLocal());
				partner.setImageId(getPartnerImageId());
				partner.setPartnerCustomerId(getPartnerCustomerId());
				partnerList.add(partner);
				setPartName("");
				setPidno("");
				setPartNameLocal("");
				setPartnerContactNumber("");
				setPartnerEmail("");
				setEnabledddPartnerAuthDataTable(true);
				setPartnerImageId(null);
				setPartnerIdRegStatus(false);
				setRenderRegisterPartner(false);
				setPartnerUploadFileVisibility(false);
				setDuplicatePartnerDetails(false);
				setRenderPartneListAvailability(false);
				setRegisterId("");
				partner.setPatnerPercentage(null);
				partner.setCheckedPartner(false);
				partner.setCheckedAuthorized(false);
				partner.setTransactionBehalf(false);
				partner.setEffectiveDate(null);
				partner.setEffectiveDateString(null);
				partner.setValidUpTo(null);
				partner.setValidUpToString(null);
			}
		} else {
			setDuplicatePartnerDetails(true);
		}
	}

	public void onNatureOfBusinessSelect(){
		/* 
		 * Here 322 is Wholesale FC Suppliers
		 * If Nature of business is " Wholesale FC Suppliers " then Partner details not mandatory
		 * 342 -For Asscociation
		 */
		if(getBussNature()!=null && (getBussNature().compareTo(new BigDecimal(322))==0 || getBussNature().compareTo(Constants.ASSO_ID)==0)){
			setCheckPartnerDetailsRequired(false);
		}else{
			setCheckPartnerDetailsRequired(true);
		} 
	}
	
	/*
	 * 
	 * method to delete the Authour record from manageBean
	 */
	public void removeAuthourSignatory(AddAuthSignatoriesBean bean) {
		authorisedList.remove(bean);
		if (bean.getCustomerIdProofId() != new BigDecimal(0)) {
			authorisedListDeleted.add(bean);
		}
		System.out.println("the record that has to be delete authourised" + authorisedListDeleted.size());
	}

	/*
	 * 
	 * method to delete the partner details record from manageBean
	 */
	public void removePartnerDetails(AddPartnerDetailBean bean) {
		partnerList.remove(bean);
		if (bean.getCustomerIdProofId() != new BigDecimal(0)) {
			partnerListDeleted.add(bean);
		}
		System.out.println("the record that has to be delete partner" + partnerListDeleted.size());
	}

	/**
	 * method to save Company -Corporate SAVE
	 * 
	 * @throws ParseException
	 */
	public void saveData() throws ParseException {/*
		try {
			// saveOwnerImage();
			cust = new Customer();
			countryMaster = new CountryMaster();
			*//** save company *//*
			companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(sessionStateManage.getCompanyId());
			*//** Objective id **//*
			BizComponentData objectiveId = new BizComponentData();
			objectiveId.setComponentDataId(getPrimaryObjId());
			// try {
			*//** Customer Type *//*
			BizComponentData customerType = new BizComponentData();
			customerType.setComponentDataId(getGeneralService().getComponentId(Constants.CUSTOMERTYPE_CORP, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
			BizComponentData companyGroup = new BizComponentData();
			companyGroup.setComponentDataId(getGeneralService().getComponentId(Constants.GROUPID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
			countryMaster.setCountryId(sessionStateManage.getCountryId());
			langType = new LanguageType();
			langType.setLanguageId((sessionStateManage.getLanguageId()));
			cust.setFsCountryMasterByCountryId(countryMaster);
			cust.setFsLanguageType(langType);
			cust.setCompanyName(getCompName());
			cust.setCompanyNameLocal(getCompNameL());
			cust.setEmail(getEmail());
			cust.setCrNo(getCrno());
			cust.setDateOfBirth(getCompRegDate());
			cust.setMobile(getContactNumber());
			cust.setPaidupCapital(getPaidUpCapital());
			// CR for Date based on application country
			// cust.setActivatedDate(new Date());
			cust.setActivatedDate(getCurrentTime());
			// cust.setActivatedInd("1");
			cust.setActivatedInd(Constants.CUST_ACTIVE_INDICATOR);
			countryMstr = new CountryMaster();
			countryMstr.setCountryId(sessionStateManage.getCountryId());
			// cust.setFsCountryMasterByCountryId(countryMstr);
			cust.setFsCountryMasterByNationality(countryMstr);
			cust.setFsBizComponentDataByGroupId(companyGroup);
			cust.setFsBizComponentDataByObjectiveId(objectiveId);
			cust.setFsBizComponentDataByCustomerTypeId(customerType);
			cust.setBranchCode(new BigDecimal(sessionStateManage.getBranchId()));
			cust.setIsActive(Constants.Yes);
			cust.setFsCompanyMaster(companyMaster);
			cust.setSmartCardIndicator(Constants.No);
			// cust.setAmlStatus(getAMLStatus());
			// cust.setNumberOfHits(1l);
			 Setting Article ID 
			ArticleDetails details = new ArticleDetails();
			details.setArticleDetailId(getLevelId());
			cust.setFsArticleDetails(details);
			String titlcorp = getGeneralService().getComponentId(Constants.TITLE_CORPORATE_MS, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString();
			if (titlcorp != null) {
				cust.setTitle(titlcorp);
			}
			// To Store Limit in DB
			
			 * cust.setDailyLimit(getDailyLimit());
			 * cust.setWeeklyLimit(getWeeklyLimit());
			 * cust.setMontlyLimit(getMonthlyLimit());
			 * cust.setQuaterlyLimit(getQuarterlyLimit());
			 * cust.setHalfYearly(getHalfyearly());
			 * cust.setAnnualLimit(getAnnualLimit());
			 
			if (getCustomerId() != null) {
				cust.setAmlStatusUpdatedBy(userName);
				cust.setUpdatedBy(userName);
				// CR for Date based on application country
				// cust.setLastUpdated(new Date());
				cust.setLastUpdated(getCurrentTime());
				cust.setAmlStatusUpdatedBy(userName);
				cust.setCreatedBy(getCreatedByCustomer());
				cust.setCreationDate(getCreatedDateCustomer());
				cust.setCustomerReference(getUpdateCustomerRefNo());
				cust.setCustomerId(getCustomerId());
			} else {
				BigDecimal custRef = icustomerRegistrationService.callProcedureCustReferenceNumber(getCompanyCodeByCompanyId(), Constants.DOCUMENT_CODE_FOR_CUSTOMER, getDealYearbyDate(), sessionStateManage.getBranchId());
				if (custRef != null) {
					cust.setCustomerReference(custRef);
				}
				cust.setCreatedBy(userName);
				// CR for Date based on application country
				// cust.setCreationDate(new Date());
				cust.setCreationDate(getCurrentTime());
				// cust.setCustomerReference(getCustomerRefNo());
			}
			// @@@ AML
			String amlReturnStatus = null;
			String amlStatus = null;
			String amlhits = null;
			amlReturnStatus = getAMLCheckStatus(cust);
			// amlReturnStatus ="PASS-0";
			if (amlReturnStatus == null) {
				cust.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);
				cust.setNumberOfHits(new BigDecimal(0));
			} else {
				String[] parts = amlReturnStatus.split("-");
				amlStatus = parts[0];
				amlhits = parts[1];
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_ERROR)) {
					cust.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);
					cust.setNumberOfHits(new BigDecimal(amlhits));
				}
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PENDING)) {
					cust.setAmlStatus(Constants.AML_STATUS_BCO);
					cust.setNumberOfHits(new BigDecimal(amlhits));
					// saveAmlStatus(customer);
				}
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PASS)) {
					cust.setAmlStatus(Constants.AML_STATUS_PASS);
					cust.setNumberOfHits(new BigDecimal(0));
				}
			}
			getCorpRegService().updateCorpCustomer(cust);
			setCustomerId(cust.getCustomerId());
			saveSecondaryObjective();
			saveBussnessNature();
			saveContact();
			saveCompanyIdentityDocument();
			savePartnerAuthorized();
			// Adding
			setRenderCoIdentityDoc(false);
			setRenderContactDetails(false);
			setRenderValidate(false);
			setRenderObjective(false);
			setCoValdtBtn(false);
			setRenderPartnerDetail(false);
			setRenderOwnerDetails(false);
			setRenderTransactionalSetting(false);
			setEnabledddCompanyDataTable(false);
			setEnablePartnerScan(false);
			setRenderSuccess(true);
			setEnabledddPartnerAuthDataTable(false);
			getAMLCheckStatus_afterSave(customerId);
			BigDecimal customerReference = null;
			List<Customer> custlist = corpRegService.getCustomerList(getCustomerId());
			if (custlist.size() > 0) {
				if (custlist.get(0).getCustomerReference() != null) {
					customerReference = custlist.get(0).getCustomerReference();
					setUpdateCustomerRefNo(customerReference);
					setCreatedByCustomer(custlist.get(0).getCreatedBy());
					setCreatedDateCustomer(custlist.get(0).getCreationDate());
				}
			}
			List<String> outPutList = icustomerRegistrationService.callProcedureUpdate(getCustomerId());
			if (outPutList.size() > 0) {
				if (outPutList.get(0).equalsIgnoreCase(Constants.Yes)) {
					RequestContext.getCurrentInstance().execute("migrationexception.show();");
				}
			}
			
			 * } catch (AMGException e) {
			 * 
			 * log.error("Exception Occured While Migration Data", e);
			 * RequestContext
			 * .getCurrentInstance().execute("migrationexception.show();");
			 
		} catch (Exception e) {
			log.info("problem in redirecting" + e);
			setRenderCoIdentityDoc(false);
			setRenderContactDetails(false);
			setRenderValidate(false);
			setRenderObjective(false);
			setCoValdtBtn(false);
			setRenderPartnerDetail(true);
			setRenderOwnerDetails(false);
			setRenderTransactionalSetting(false);
			setEnabledddCompanyDataTable(false);
			setEnablePartnerScan(false);
			setEnabledddPartnerAuthDataTable(true);
			setRenderSuccess(false);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	*/}

	// Bahrain Smart Card
	// Save data to AML Check - By Viswa
	public String getAMLCheckStatus(Customer customer) {
		String amlReturnStatus = null;
		String status = "  ";
		String CorpTitle = Constants.TITLE_CORPORATE_MS;
		String Corporate = Constants.Corporate;
		String companyRegDocNo = Constants.COMPANY_REGISTRATION_DOC;
		String countryId = sessionStateManage.getCountryId().toString();
		try {
			FNSServicesLookupSoapClient finscanService = new FNSServicesLookupSoapClient();
			amlReturnStatus = finscanService.amlServiceSearch("Organization", "UNKNOWN", "Active", "Yes", "Yes", "Yes", " ", " ", " ", " ", " ", " ", " ", "Full Source", "Yes", "Yes", "WC", "World-Check", "  ", "  ", "Country", countryId, "Date of Birth", "", "National ID Number", getCrno(),
					"Passport ID", "", getCompName(), getCompNameL(), "11434471-E", "TEST", "");
			System.out.println("AML Status is:" + amlReturnStatus);
		} catch (Exception e) {
			log.info("Aml Exception:" + e);
		}
		return amlReturnStatus;
	}

	public void getAMLCheckStatus_afterSave(BigDecimal customerId) {
		List<Customer> customerInfoList = new ArrayList<Customer>();
		String CorpTitle = Constants.TITLE_CORPORATE_MS;
		String Corporate = Constants.Corporate;
		String companyRegDocNo = Constants.COMPANY_REGISTRATION_DOC;
		String countryId = sessionStateManage.getCountryId().toString();
		String amlReturnStatus = null;
		String status = " ";
		String crNumber = null;
		String customerRef = null;
		try {
			FNSServicesLookupSoapClient finscanService = new FNSServicesLookupSoapClient();
			customerInfoList = icustomerRegistrationService.getCustomerInfo(customerId);
			if (customerInfoList.size() > 0) {
				List<CustomerIdProof> idproofList = icustomerRegistrationService.getCivilID(customerInfoList.get(0).getCustomerReference());
				if (idproofList.size() > 0) {
					crNumber = idproofList.get(0).getIdentityInt();
				}
				customerRef = customerInfoList.get(0).getCustomerReference().toString() + "-E";
				String companyName = customerInfoList.get(0).getCompanyName();
				String companyNameLocal = customerInfoList.get(0).getCompanyNameLocal();
				amlReturnStatus = finscanService.amlServiceSearch("Organization", "UNKNOWN", "Active", "Yes", "Yes", "Yes", " ", " ", " ", " ", " ", " ", " ", "Full Source", "Yes", "Yes", "WC", "World-Check", "  ", "  ", "Country", customerInfoList.get(0).getFsCountryMasterByCountryId()
						.getCountryId().toString(), "Date of Birth", "", "National ID Number", crNumber, "Passport ID", "", companyName, companyNameLocal, customerRef, "AMIEC", "");
			}
			System.out.println("AML Status is:" + amlReturnStatus);
		} catch (Exception e) {
			log.info("Aml Exception:" + e);
		}
	}

	
	
	/**
	 * 
	 * method to save contact details
	 */
	
	private Boolean booCheckMobileContact = false;
	private Boolean renContactMobile = false;
	private String plusSign=null;
	private String telephoneCode=null;
	private Boolean booCheckTelContact = false;
	
	private Boolean renContactTel = false;
	
	
	public Boolean getRenContactTel() {
		return renContactTel;
	}

	public void setRenContactTel(Boolean renContactTel) {
		this.renContactTel = renContactTel;
	}

	public Boolean getBooCheckTelContact() {
		return booCheckTelContact;
	}

	public void setBooCheckTelContact(Boolean booCheckTelContact) {
		this.booCheckTelContact = booCheckTelContact;
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

	public Boolean getRenContactMobile() {
		return renContactMobile;
	}

	public void setRenContactMobile(Boolean renContactMobile) {
		this.renContactMobile = renContactMobile;
	}

	public Boolean getBooCheckMobileContact() {
		return booCheckMobileContact;
	}

	public void setBooCheckMobileContact(Boolean booCheckMobileContact) {
		this.booCheckMobileContact = booCheckMobileContact;
	}

	public void telephoneCodeFromDB(){
		setPlusSign("+");
		System.out.println("CountryId==:    "+getCountryId());
	  setTelephoneCode(generalService.getTelephoneCountryBasedOnNationality(getCountryId()));
	}
	
	 public void enableTelNoContact(){
			if(getBooCheckTelContact()){
				telephoneCodeFromDB();
				setRenContactTel(true);
			}else{
				/*telephoneCodeFromDB();*/
				setRenContactTel(false);
			}
	
}
	 
	
	public void enableMobileNoContact(){
		if(getBooCheckMobileContact()){
			telephoneCodeFromDB();
			setRenContactMobile(true);
		}else{
			/*telephoneCodeFromDB();*/
			setRenContactMobile(false);
		}
		
	  }
	
	public void saveContact() {
		try {
			
			
			
			ContactDetail contdetail = new ContactDetail();
			contdetail.setContactDetailId(getContactDetailId());
			Customer cust = new Customer();
			cust.setCustomerId(getCustomerId());
			contdetail.setFsCustomer(cust);
			LanguageType langType = new LanguageType();
			langType.setLanguageId(sessionStateManage.getLanguageId());
			contdetail.setFsLanguageType(langType);
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionStateManage.getCountryId());
			contdetail.setFsCountryMaster(countryMaster);
			StateMaster stateMaster = new StateMaster();
			stateMaster.setStateId(getStateId());
			contdetail.setFsStateMaster(stateMaster);
			DistrictMaster districtMaster = new DistrictMaster();
			districtMaster.setDistrictId(getDistId());
			contdetail.setFsDistrictMaster(districtMaster);
			if (getCityId() != null) {
				CityMaster cityMaster = new CityMaster();
				cityMaster.setCityId(getCityId());
				contdetail.setFsCityMaster(cityMaster);
			}
			BizComponentData contactType = new BizComponentData();
			// for corporate contact type should be 49 - local address not home Country
			//contactType.setComponentDataId(getGeneralService().getComponentId(Constants.PERMANENT, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
			contactType.setComponentDataId(getGeneralService().getComponentId(Constants.RESIDENCE, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
			contdetail.setFsBizComponentDataByContactTypeId(contactType);
			contdetail.setArea(getArea());
			contdetail.setBlock(getBlock());
			contdetail.setStreet(getStreet());
			contdetail.setFlat(getFlat());
			contdetail.setApproved(userName);// doubt
			contdetail.setAlterEmailId(getEmail());
			
			if (getBooCheckTelContact() != null && getBooCheckTelContact()) {
				contdetail.setTelephone(getTel());
			} else {
				contdetail.setTelephone(null);
			}
			if (getBooCheckMobileContact() != null && getBooCheckMobileContact()) {
				contdetail.setMobile(getContactNumber());
			} else {
				contdetail.setMobile(null);
			}
			contdetail.setCreatedBy(userName);
			
			contdetail.setTelephoneCode(getTelephoneCode());
			// CR for Date based on application country
			// contdetail.setCreationDate(new Date());
			contdetail.setCreationDate(getCurrentTime());
			/*contdetail.setUpdatedBy(userName);*/
			// CR for Date based on application country
			// contdetail.setLastUpdated(new Date());
			/*contdetail.setLastUpdated(getCurrentTime());*/
			contdetail.setBuildingNo(getBuildingNo());
			contdetail.setActiveStatus(Constants.CUST_ACTIVE_INDICATOR);
			if (getContactDetailId() != null) {
				contdetail.setUpdatedBy(userName);
				// CR for Date based on application country
				// contdetail.setLastUpdated(new Date());
				contdetail.setLastUpdated(getCurrentTime());
				contdetail.setCreatedBy(getCreatedByContact());
				contdetail.setCreationDate(getCreatedDateContact());
				// contdetail.setActiveStatus("1");
			} else {
				contdetail.setCreatedBy(userName);
				// CR for Date based on application country
				// contdetail.setCreationDate(new Date());
				contdetail.setCreationDate(getCurrentTime());
				// contdetail.setActiveStatus("1");
			}
			getCorpRegService().saveCorporateContDtl(contdetail);
			
			getCorpRegService().updateEmail(getCustomerId(),getEmail());
		} catch (NullPointerException npexp) {
			log.info("problem in saving contact details" + npexp);
		} catch (Exception ioexp) {
			log.info("problem in saving contact details" + ioexp);
		}
	}

	/**
	 * method is responsible to save secondary objectives
	 */
	public void saveSecondaryObjective() {
		// try {
		Customer cust = new Customer();
		cust.setCustomerId(getCustomerId());
		CustCorporateAddlDetail custCorpAddDetail = null;
		BizComponentData secondaryObjective = null;
		System.out.println("the secondry object size is" + seconObjList.size());
		String data = null;
		getCorpRegService().deleteSecondaryObjective(getCustomerId());
		for (int i = 0; i < seconObjList.size(); i++) {
			
			custCorpAddDetail = new CustCorporateAddlDetail();
			data = null;
			secondaryObjective = new BizComponentData();
			System.out.println("-------------------|" + seconObjList.get(i) + "|");
			data = "" + seconObjList.get(i);
			System.out.println("-------------------|" + data + "|");
			if(data.length()==0) {
			continue;
			}
			secondaryObjective.setComponentDataId(new BigDecimal(data));
			langType = new LanguageType();
			langType.setLanguageId(sessionStateManage.getLanguageId());
			custCorpAddDetail.setFsLanguageType(langType);
			custCorpAddDetail.setFsCustomer(cust);
			custCorpAddDetail.setFsBizComponentDataByObjectiveId(secondaryObjective);
			// custCorpAddDetail.setAddlStatus("1");
			custCorpAddDetail.setAddlStatus(Constants.CUST_ACTIVE_INDICATOR);
			/*custCorpAddDetail.setCorpAddlId(getCustCorporateAdditionalId());*/
			custCorpAddDetail.setObjectiveType("SECONDARY");
			if (getCustCorporateAdditionalId() != null) {
				custCorpAddDetail.setUpdatedBy(userName);
				// CR For date based on application country
				// custCorpAddDetail.setLastUpdated(new Date());
				custCorpAddDetail.setLastUpdated(getCurrentTime());
				custCorpAddDetail.setCreatedBy(getCreatedBySecondary());
				custCorpAddDetail.setCreationDate(getCreatedDateSecondary());
			} else {
				custCorpAddDetail.setCreatedBy(userName);
				// CR For date based on application country
				// custCorpAddDetail.setCreationDate(new Date());
				custCorpAddDetail.setCreationDate(getCurrentTime());
			}
			getCorpRegService().saveOrUpdateSecondaryObjective(custCorpAddDetail);
		}
		/*
		 * } catch (NullPointerException npexp) { npexp.printStackTrace();
		 * //log.info("problem in saving secondary objective"+npexp); } catch
		 * (Exception ioexp) { ioexp.printStackTrace();
		 * //log.info("problem in saving secondary objective"+ioexp); }
		 */
	}

	/**
	 * 
	 * method to save business nature
	 */
	public void saveBussnessNature() {
		try {
			CustCorporateAddlDetail custCorpAddDetail = new CustCorporateAddlDetail();
			Customer cust = new Customer();
			cust.setCustomerId(getCustomerId());
			custCorpAddDetail.setFsCustomer(cust);
			BizComponentData bizNature = new BizComponentData();
			bizNature.setComponentDataId(getBussNature());
			custCorpAddDetail.setFsBizComponentDataByObjectiveId(bizNature);
			LanguageType langType = new LanguageType();
			langType.setLanguageId(sessionStateManage.getLanguageId());
			custCorpAddDetail.setFsLanguageType(langType);
			// custCorpAddDetail.setAddlStatus("1");
			custCorpAddDetail.setAddlStatus(Constants.CUST_ACTIVE_INDICATOR);
			/*custCorpAddDetail.setCorpAddlId(getCustCorporateAdditionalIdForBizNature());*/
			custCorpAddDetail.setObjectiveType("BUSINESS");
			if (getCustCorporateAdditionalIdForBizNature() != null) {
				custCorpAddDetail.setUpdatedBy(userName);
				// CR For date based on application country
				// custCorpAddDetail.setLastUpdated(new Date());
				custCorpAddDetail.setLastUpdated(getCurrentTime());
				custCorpAddDetail.setCreatedBy(getCreatedBySecondary());
				custCorpAddDetail.setCreationDate(getCreatedDateSecondary());
			} else {
				custCorpAddDetail.setCreatedBy(userName);
				// CR For date based on application country
				// custCorpAddDetail.setCreationDate(new Date());
				custCorpAddDetail.setCreationDate(getCurrentTime());
			}
			getCorpRegService().saveOrUpdateSecondaryObjective(custCorpAddDetail);
		} catch (NullPointerException npexp) {
			log.info("problem in saving businees nature" + npexp);
		} catch (Exception ioexp) {
			log.info("problem in saving business nature" + ioexp);
		}
	}

	/**
	 * method to save partner details
	 * 
	 * @throws ParseException
	 */
	public void savePartnerDetail() throws ParseException {/*
															 * //try {
															 * custmrIdProof =
															 * new
															 * CustomerIdProof
															 * (); //DocumentImg
															 * imgDoc = new
															 * DocumentImg();
															 * for
															 * (AddPartnerDetailBean
															 * addPartnerDetailBean
															 * : partnerList) {
															 * /
															 * /imgDoc.setImgId(
															 * addPartnerDetailBean
															 * .getImageId());
															 * cust = new
															 * Customer();
															 */
		/** partner customer id */
		/*
		 * Customer partnerCustomer = new Customer();
		 * partnerCustomer.setCustomerId
		 * (addPartnerDetailBean.getPartnerCustomerId());
		 * custmrIdProof.setFsCustomerByRefCustomerId(partnerCustomer);
		 *//** setting customerId **/
		/*
		 * cust.setCustomerId(getCustomerId());
		 * custmrIdProof.setFsCustomer(cust);
		 * //custmrIdProof.setFsDocumentImg(imgDoc); langType = new
		 * LanguageType();
		 * langType.setLanguageId(sessionStateManage.getLanguageId());
		 * custmrIdProof.setFsLanguageType(langType);
		 *//** Customer Type */
		/*
		 * BizComponentData customerType = new BizComponentData();
		 * customerType.setComponentDataId
		 * (getGeneralService().getComponentId(CUSTOMERTYPEFORPARTNER,
		 * sessionStateManage
		 * .getLanguageId()).getFsBizComponentData().getComponentDataId());
		 * custmrIdProof.setFsBizComponentDataByCustomerTypeId(customerType);
		 *//** Identity Type */
		/*
		 * BizComponentData identityType = new BizComponentData();
		 * identityType.setComponentDataId(addPartnerDetailBean.getpIdTypeId());
		 * custmrIdProof.setFsBizComponentDataByIdentityTypeId(identityType);
		 * custmrIdProof.setName(addPartnerDetailBean.getPartName());
		 * custmrIdProof.setIdentityInt(addPartnerDetailBean.getPidno());
		 * custmrIdProof
		 * .setCustProofId(addPartnerDetailBean.getCustomerIdProofId()); Date
		 * expDate = new
		 * SimpleDateFormat("dd/MM/yyyy").parse(addPartnerDetailBean
		 * .getPidExpDate()); custmrIdProof.setIdentityExpiryDate(expDate);
		 * custmrIdProof.setIdentityStatus("1");
		 * if(addPartnerDetailBean.getCustomerIdProofId() != null) {
		 * custmrIdProof.setUpdatedBy(userName); //CR for date based on
		 * application country //custmrIdProof.setLastUpdatedDate(new Date());
		 * custmrIdProof.setLastUpdatedDate(getCurrentTime());
		 * custmrIdProof.setCreatedBy(getCreatedByPartner());
		 * custmrIdProof.setCreationDate(getCreatedDatePartner()); }else{
		 * custmrIdProof.setApprovedBy(userName); //CR for date based on
		 * application country //custmrIdProof.setApprovedDate(new Date());
		 * custmrIdProof.setApprovedDate(getCurrentTime());
		 * custmrIdProof.setCreatedBy(userName); //CR for date based on
		 * application country //custmrIdProof.setCreationDate(new Date());
		 * custmrIdProof.setCreationDate(getCurrentTime()); }
		 * getCorpRegService().commonSaveOrUpdateIdProof(custmrIdProof); }
		 * for(AddPartnerDetailBean addPartnerDetailBean: partnerListDeleted) {
		 * //imgDoc.setImgId(addPartnerDetailBean.getImageId()); cust = new
		 * Customer();
		 *//** partner customer id */
		/*
		 * Customer partnerCustomer = new Customer();
		 * partnerCustomer.setCustomerId
		 * (addPartnerDetailBean.getPartnerCustomerId());
		 * custmrIdProof.setFsCustomerByRefCustomerId(partnerCustomer);
		 *//** setting customerId **/
		/*
		 * cust.setCustomerId(getCustomerId());
		 * custmrIdProof.setFsCustomer(cust);
		 * //custmrIdProof.setFsDocumentImg(imgDoc); langType = new
		 * LanguageType();
		 * langType.setLanguageId(sessionStateManage.getLanguageId());
		 * custmrIdProof.setFsLanguageType(langType);
		 *//** Customer Type */
		/*
		 * BizComponentData customerType = new BizComponentData();
		 * customerType.setComponentDataId
		 * (getGeneralService().getComponentId(CUSTOMERTYPEFORPARTNER
		 * ,sessionStateManage
		 * .getLanguageId()).getFsBizComponentData().getComponentDataId());
		 * custmrIdProof.setFsBizComponentDataByCustomerTypeId(customerType);
		 *//** Identity Type */
		/*
		 * BizComponentData identityType = new BizComponentData();
		 * identityType.setComponentDataId(addPartnerDetailBean.getpIdTypeId());
		 * custmrIdProof.setFsBizComponentDataByIdentityTypeId(identityType);
		 * custmrIdProof.setName(addPartnerDetailBean.getPartName());
		 * custmrIdProof.setIdentityInt(addPartnerDetailBean.getPidno());
		 * custmrIdProof
		 * .setCustProofId(addPartnerDetailBean.getCustomerIdProofId()); Date
		 * expDate = new
		 * SimpleDateFormat("dd/MM/yyyy").parse(addPartnerDetailBean
		 * .getPidExpDate()); custmrIdProof.setIdentityExpiryDate(expDate);
		 * custmrIdProof.setIdentityStatus("0");
		 * if(addPartnerDetailBean.getCustomerIdProofId() != null) {
		 * custmrIdProof.setUpdatedBy(userName); // CR for date based on
		 * application country //custmrIdProof.setLastUpdatedDate(new Date());
		 * custmrIdProof.setLastUpdatedDate(getCurrentTime());
		 * custmrIdProof.setCreatedBy(getCreatedByPartner());
		 * custmrIdProof.setCreationDate(getCreatedDatePartner()); }else{
		 * custmrIdProof.setApprovedBy(userName); // CR for date based on
		 * application country //custmrIdProof.setApprovedDate(new Date());
		 * custmrIdProof.setApprovedDate(getCurrentTime());
		 * custmrIdProof.setCreatedBy(userName); // CR for date based on
		 * application country //custmrIdProof.setCreationDate(new Date());
		 * custmrIdProof.setCreationDate(getCurrentTime()); }
		 * getCorpRegService().commonSaveOrUpdateIdProof(custmrIdProof); }
		 */}

	/*
	 * method to validate contact details,primary secondary,business nature
	 */
	public void validateDataTables() {
		if (contactList.size() == 0) {
		}
	}

	/*
	 * method to fill company identity document in dataTable at the time of
	 * fetching
	 */
	private void setCompanyIdProofDetail() throws SQLException, IOException {
		System.out.println("Called setCompanyIdProofDetail");
		// try{
		identityList.clear();
		List<CustomerIdProof> customerIdProof = getCorpRegService().getCustomerIdProof(sessionStateManage.getLanguageId(), getCustomerId(), getGeneralService().getComponentId(Constants.CUSTOMERTYPE_CORP, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
		if (customerIdProof.size() > 0) {
			getFetchCompanyIdentityTypeList();
			for (CustomerIdProof custIdProof : customerIdProof) {
				AddCoIdentityDetailBean addComBean = new AddCoIdentityDetailBean();
				setCustomerIdProofIdForCompanyIdentityDoc(customerIdProof.get(0).getCustProofId());
				if (custIdProof.getIdentityInt() != null) {
					addComBean.setIdno(custIdProof.getIdentityInt());
					if(custIdProof.getIdentityExpiryDate() != null){
						addComBean.setIdExpDate(new SimpleDateFormat("dd/MM/yyyy").format(custIdProof.getIdentityExpiryDate()));
						SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
						try {
							if(dateformat.parse(dateformat.format(custIdProof.getIdentityExpiryDate())).compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0){
								addComBean.setRenderReScan(true);
							}else{
								addComBean.setRenderReScan(false);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					
				}
				
				addComBean.setIdTypeId(custIdProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId());
				addComBean.setIdtype(mapcomIdentityType.get(custIdProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId()));
				addComBean.setCustomerIdProofId(custIdProof.getCustProofId());
				setIdExpDate(custIdProof.getIdentityExpiryDate());
				// addComBean.setImageId(custIdProof.getFsDocumentImg().getImgId());
				identityList.add(addComBean);
			}
		}
	}

	/**
	 * method to populate state
	 */
	public void populateState() {
		List<StateMasterDesc> statemasterDesc = getGeneralService().getStateList(sessionStateManage.getLanguageId(), getCountryId());
		setLstStates(statemasterDesc);
	}

	/**
	 * method to populate district
	 */
	public void populateDistrict() {
		List<DistrictMasterDesc> districtmasterDesc = getGeneralService().getDistrictList(sessionStateManage.getLanguageId(), getCountryId(), getStateId());
		setLstDistrict(districtmasterDesc);
	}

	/**
	 * method to populate city
	 */
	public void populateCity() {
		List<CityMasterDesc> citymasterDesc = getGeneralService().getCityList(sessionStateManage.getLanguageId(), getCountryId(), getStateId(), getDistId());
		setLstcity(citymasterDesc);
	}

	/*
	 * method to fill contact details in dataTable at the time of fetching
	 */
	private void setCustomerContactDetails() {
		try {
			// contactList.clear();
			List<ContactDetail> cdetail = getCorpRegService().getContactDetail(getCustomerId());
			if (cdetail.size() > 0) {
				ContactDetail contact = cdetail.get(0);
				// setContType(contact.getFsBizComponentDataByContactTypeId().getComponentDataId());
				setContactDetailId(contact.getContactDetailId());
				getCountryListFromDB();
				setCountryId(contact.getFsCountryMaster().getCountryId());
				populateState();
				if (contact.getFsStateMaster() != null) {
					setStateId(contact.getFsStateMaster().getStateId());
				}
				populateDistrict();
				if (contact.getFsDistrictMaster() != null) {
					setDistId(contact.getFsDistrictMaster().getDistrictId());
				}
				populateCity();
				if (contact.getFsCityMaster() != null) {
					setCityId(contact.getFsCityMaster().getCityId());
				}
				setEmail(contact.getAlterEmailId());
				setArea(contact.getArea());
				setStreet(contact.getStreet());
				setBlock(contact.getBlock());
				setFlat(contact.getFlat());
				setTel(contact.getTelephone());
				setContactNumber(contact.getMobile());
				telephoneCodeFromDB();
				if(contact.getTelephone()!=null) {
				//setTel(contact.getTelephone());
					setBooCheckTelContact(true);
					
					setRenContactTel(true);
				}
				if(contact.getMobile()!=null) {
				setContactNumber(contact.getMobile());
				setBooCheckMobileContact(true);
				setRenContactMobile(true);
				}else{
					setRenContactMobile(false);
				}
				setCreatedByContact(contact.getCreatedBy());
				setCreatedDateContact(contact.getCreationDate());
				setBuildingNo(contact.getBuildingNo());
			}
		} catch (NullPointerException npexp) {
			log.info("problem in fetching  contact details" + npexp);
		} catch (Exception e) {
			log.info("problem in fetching  contact details" + e);
		}
	}

	/*
	 * method to set secoundryObjective
	 */
	private void setSecondaryObjectiveDetails() {
		try {
			List<CustCorporateAddlDetail> custCorporateAddlDetail = getCorpRegService().getCustomerCorporateAdditionalDetail(sessionStateManage.getLanguageId(), getCustomerId(), "SECONDARY");
			System.out.println("the secondery list size is" + custCorporateAddlDetail.size());
			for (CustCorporateAddlDetail bean : custCorporateAddlDetail) {
				seconObjList.add(bean.getFsBizComponentDataByObjectiveId().getComponentDataId());
				setCustCorporateAdditionalId(bean.getCorpAddlId());
				setCreatedBySecondary(bean.getCreatedBy());
				setCreatedDateSecondary(bean.getCreationDate());
			}
			for (int i = 0; i < seconObjList.size(); i++) {
				System.out.println("the secondary objective is" + seconObjList.get(i));
			}
		} catch (NullPointerException npexp) {
			log.info("problem in fetching  secondary objective" + npexp);
		} catch (Exception e) {
			log.info("problem in fetching  secondary objective" + e);
		}
	}

	/*
	 * method to fill Corporate Business nature in dataTable at the time of
	 * fetching
	 */
	private void setNatureOfBusinessDetails() {
		try {
			List<CustCorporateAddlDetail> corporateBusinessNature = getCorpRegService().getCustomerCorporateAdditionalDetail(sessionStateManage.getLanguageId(), getCustomerId(), "BUSINESS");
			if (corporateBusinessNature.size() > 0) {
				setBussNature(corporateBusinessNature.get(0).getFsBizComponentDataByObjectiveId().getComponentDataId());
				setCustCorporateAdditionalIdForBizNature(corporateBusinessNature.get(0).getCorpAddlId());
				setCreatedByBusseness(corporateBusinessNature.get(0).getCreatedBy());
				setCreatedDateBusseness(corporateBusinessNature.get(0).getCreationDate());
			}
		} catch (NullPointerException npexp) {
			log.info("problem in fetching  nature of business" + npexp);
		} catch (Exception e) {
			log.info("problem in fetching  nature of business" + e);
		}
	}

	/**
	 * method is responsible for setting article details
	 */
	public void populateArticleLevel() {
		// lstLevel = getCorpRegService().getLevelData(getArticle());
		lstLevel = branchServiec.getLevelData(getArticle(), sessionStateManage.getLanguageId());
	}

	/*
	 * method to fetch the company registration details with cr no.
	 */
	
	public void fetchData() {
		/*
		 * method to get the registration status of the company
		 */
		// resetValues();
		/*
		 * getDealYear(); getDocuments(); getDocumentSerialID(processIn);
		 */
		getFetchCompanyIdentityTypeList();
		setCustomerId(null);
		try {
			customerList = new ArrayList<Customer>();
			customerList.addAll(getCorpRegService().getCompanyRegistrationStatus(sessionStateManage.getCountryId(), getCrno()));
			Calendar cal = new GregorianCalendar();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, +0);
			Date today90 = cal.getTime();
			SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
			String finalDate = form.format(today90);
			setExpminDate(finalDate);
			int year=cal.get(Calendar.YEAR);
			cal.set(year,11,31);
			Date auditMax = cal.getTime();
			setAuditMaxDate(auditMax);
			
			SimpleDateFormat smpDate = new SimpleDateFormat("dd/MM/yyyy");
			if (customerList.size() > 0) {
				setIdtype(null);
				//setIdExpDate(null);
				int cust_id = 0;
				setReadOnly(true);
			
				setCustomerId(customerList.get(0).getCustomerId());
				setCrno(customerList.get(0).getCrNo());
				setCompName(customerList.get(0).getCompanyName());
				setCompNameL(customerList.get(0).getCompanyNameLocal());
				setEmail(customerList.get(0).getEmail());
				if (customerList.get(0).getMobile() != null) {
					setContactNumber(customerList.get(0).getMobile());
				}
				setAuditGrossIncome(customerList.get(0).getAuditGrossIncome());
				setAuditStatementDate(customerList.get(0).getAuditStatementDate());
				// setContactPerson(customerList.get(0).getContactPerson());
				if (customerList.get(0).getPaidupCapital() != null) {
					setPaidUpCapital(customerList.get(0).getPaidupCapital());
				}
				setCreatedByCustomer(customerList.get(0).getCreatedBy());
				setCreatedDateCustomer(customerList.get(0).getCreationDate());
				setAMLStatus(customerList.get(0).getAmlStatus());
				if (customerList.get(0).getDateOfBirth() != null) {
					setCompRegDate(customerList.get(0).getDateOfBirth());
				}
				/*
				 * getCountryListFromDB();
				 * setLocation(customerList.get(0).getFsCountryMasterByCountryId
				 * ().getCountryId());
				 */
				if (customerList.get(0).getFsBizComponentDataByObjectiveId() != null) {
					setPrimaryObjId(customerList.get(0).getFsBizComponentDataByObjectiveId().getComponentDataId());
				}
				setHitCount("0");
				setCreatedDateCustomer(customerList.get(0).getCreationDate());
				setUpdateCustomerRefNo(customerList.get(0).getCustomerReference());
				setCustomerId(customerList.get(0).getCustomerId());
				/**
				 * for first time article level is zero to void exception
				 */
				if (customerList.get(0).getFsArticleDetails() != null) {
					setArticle(customerList.get(0).getFsArticleDetails().getFsArticleMaster().getArticleId());
					populateArticleLevel();
					setLevelId(customerList.get(0).getFsArticleDetails().getArticleDetailId());
					/*
					 * setDailyLimit(customerList.get(0).getDailyLimit());
					 * setWeeklyLimit(customerList.get(0).getWeeklyLimit());
					 * setMonthlyLimit(customerList.get(0).getMontlyLimit());
					 * setQuarterlyLimit
					 * (customerList.get(0).getQuaterlyLimit());
					 * setHalfyearly(customerList.get(0).getHalfYearly());
					 * setAnnualLimit(customerList.get(0).getAnnualLimit());
					 */
				}
				
				if(customerList.get(0).getFsIncomeRangeMaster()!=null)
				{
					generateIncomeRange();
					setIncomeRange(customerList.get(0).getFsIncomeRangeMaster().getIncomeRangeId());
				}
				
				setSecondaryObjectiveDetails();
				setNatureOfBusinessDetails();
				setCustomerContactDetails();
				setCompanyIdProofDetail();
				// setPartnerIdProofDetail();
				// setAuthroizedIdProofDetail();
				setPartnerAuthorized();
				setCoValdtBtn(false);
				setRenderValidate(true);
				// setRenderObjective(true);
				// setRenderImage(true);
				setEnabledddPartnerAuthDataTable(false);
				setSaveOrFetch(false);
				setDisableDate(true);
				setReadOnly(true);
				setRenderCoIdentityDoc(true);
				setRenderContactDetails(false);
				setRenderValidate(true);
				setRenderObjective(false);
				setRenderTransactionalSetting(false);
				setEnabledddCompanyDataTable(true);
				setChangeCrNumber(false);
				// setReadOnlyCompanyIdDoc(true);
				// setDisableCompIdDco(true);
				setEnableScan(true);
				setEnableCheckId(false);
				setRenderModifyScan(false);
				setRenderSuccess(false);
			} else { // added by nazish 30-01-2015 for put cr no then record
						// coming and put nre cr no remove cache
				setRenderValidate(true);
				setRenderObjective(false);
				setRenderContactDetails(false);
				setRenderCoIdentityDoc(false);
				setRenderPartnerDetail(false);
				setRenderOwnerDetails(false);
				setOwnerIdRegStatus(false);
				setRenderRegisterOwner(false);
				setAuthorUploadFileVisibility(false);
				setDuplicateOwnerDetails(false);
				setPartnerIdRegStatus(false);
				setRenderRegisterPartner(false);
				setPartnerUploadFileVisibility(false);
				setDuplicatePartnerDetails(false);
				setRenderPartneListAvailability(false);
				setRenderOwnerListAvailability(false);
				setRenderImage(false);
				setRenderTransactionalSetting(false);
				setCompName("");
				setCompNameL("");
				setCompRegDate(null);
				setEmail("");
				setCoValdtBtn(true);
				setLocation(new BigDecimal(0));
				setAMLStatus("");
				setContactPerson("");
				setContactNumber(null);
				setHitCount("");
				clearObjetive();
				clearContact();
				clearPartnerDetails();
				seconObjList.clear();
				partnerList.clear();
				authorisedList.clear();
				identityList.clear();
				setDownloadFileForCompany(null);
				setPaidUpCapital(new BigDecimal(0));
				setEnabledddCompanyDataTable(false);
				setEnabledddPartnerAuthDataTable(false);
				setImmediate(false);
				setDailyLimit(null);
				setWeeklyLimit(null);
				setMonthlyLimit(null);
				setHalfyearly(null);
				setAnnualLimit(null);
				setQuarterlyLimit(null);
				setIdentityListVisibility(false);
				setIdtype(null);
				setIdno(null);
				setArticle(null);
				setLevelId(null);
				setIncomeRange(null);
				setEnableScan(true);
				setEnableCheckId(false);
				setRenderModifyScan(false);
				setRenderSuccess(false);
				setAuditGrossIncome(null);
				setAuditStatementDate(null);
				setRenContactMobile(false);
				setRenContactTel(false);
				setAuditStatementDate(null);
				setAuditGrossIncome(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String cancel() {
		return "cancel";
	}

	/**
	 * This method will call on on change of Article
	 */
	/*
	 * @Autowired IBranchPageService<T> branchPageService;
	 */
	public void generateLevel() {
		// lstLevel = getCorpRegService().getLevelData(getArticle());
		lstLevel = branchServiec.getLevelData(getArticle(), sessionStateManage.getLanguageId());
	}

	public List<ArticleMasterDesc> getArticleData() {
		// List<ArticleMaster> lstArticles =
		// getCorpRegService().getArtilces(sessionStateManage.getCountryId());
		List<ArticleMasterDesc> lstArticles = getCorpRegService().getArtilces(sessionStateManage.getCountryId(), sessionStateManage.getLanguageId());
		return lstArticles;
	}

	/*
	 * public List<ArticleMaster> getArticleData() { List<ArticleMaster>
	 * lstArticles =
	 * getCorpRegService().getArtilces(sessionStateManage.getCountryId());
	 * return lstArticles; }
	 */
	public List<CountryBean> getCountryListFromDB() {
		countrybean.clear();
		List<CountryMasterDesc> lstCountryMasterDesc = getiGeneralService().getBusinessCountryList((sessionStateManage.getLanguageId()));
		for (CountryMasterDesc desc : lstCountryMasterDesc) {
			CountryBean cntryBean = new CountryBean(desc.getCountryMasterId(), desc.getCountryName());
			countrybean.add(cntryBean);
		}
		return countrybean;
	}

	/** method is responsible to handle uploaded file for company document **/
	public void handleFileUpload(FileUploadEvent event) throws IOException, SerialException, SQLException {
		setIdentityfile(event.getFile());
		setImageUploadChecckForComapny(true);
		setInputStream(event.getFile().getInputstream());
		saveDocument(event.getFile().getFileName(), getCustomerId(), getIdentityfile(), "company");
	}

	/** method is responsible to handle uploaded file for partner **/
	public void handleFileUploadForPartner(FileUploadEvent event) throws IOException, SerialException, SQLException {
		setPartnerfile(event.getFile());
		setImageUploadChecckForPartner(true);
		setInputStream(event.getFile().getInputstream());
		saveDocument(event.getFile().getFileName(), getCustomerId(), getPartnerfile(), "partner");
	}

	public boolean saveOwnerImage() throws SerialException, IOException, SQLException, ParseException {
		boolean checkForSave = true;
		double checkPercentage = 0;
		if (isRenderRegisterPartner() == false) {
			for (AddPartnerDetailBean element : partnerList) {
				
			if(element.getCheckedPartner())
			{
				if(element.getPatnerPercentage()==null){
					
					setImmediate(true);
					return false;
				}
				else
				{
				List<Customer> listCust=	corpRegService.getCustomerList(element.getPartnerCustomerId() );
				if(listCust.get(0).getIsActive().equalsIgnoreCase(Constants.Yes)){
				checkPercentage = checkPercentage + element.getPatnerPercentage().doubleValue();
			 }
				}
			}
			if(element.getCheckedAuthorized()) {
				
				if(element.getEffectiveDate()==null)
				{
					setImmediate(true);
					return false;
				}
				if(element.getValidUpTo()==null)
				{
					setImmediate(true);
					return false;
				}
			}
			
			
			
			}
			
			/*
			for (AddPartnerDetailBean element : partnerList) {
				
				if(element.getChecked()==null)
				{
					setRenderPartneListAvailability(true);
					return false;
				}
				
				if(element.getPatnerPercentage()==null)
				{
					setRenderPartneListAvailability(true);
					return false;
				}
				
				if(element.getCheckedAuthorized()==null)
				{
					setRenderPartneListAvailability(true);
					return false;
				}
				if(element.getEffectiveDate()==null)
				{
					setRenderPartneListAvailability(true);
					return false;
				}
				if(element.getValidUpTo()==null)
				{
					setRenderPartneListAvailability(true);
					return false;
				}
			}
			
			
			
			
			 if(checkForSave){ saveData(); }*/
			
			
			if(checkPercentage>100)
			{
				RequestContext.getCurrentInstance().execute("PatnerPercentageCheck.show();");
				setErrmsg("Total % are greater than 100");
				return false;
			}
			
		}
		if(checkForSave)
		{
			setImmediate(false);
		}
		return checkForSave;
	}

	// Store file in the database
	public Blob storeImage(UploadedFile file) throws IOException, SerialException, SQLException {
		InputStream stream = null;
		try {
			stream = file.getInputstream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new javax.sql.rowset.serial.SerialBlob(readFully(stream));
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

	public StreamedContent downloadFile(BigDecimal imageId) {
		StreamedContent image = null;
		try {
			image = getImage(imageId);
		} catch (Exception e) {
			log.info("problem in downloading file" + e);
		}
		return image;
	}

	public StreamedContent getImage(BigDecimal imageId) {
		try {
			if (imageId.toPlainString().length() > 0 && imageId != null) {
				Blob blob = getCorpRegService().getImage(imageId).get(0).getImage();
				String imageNameWithExtention = getCorpRegService().getImage(imageId).get(0).getImageName();
				String imageExtention = imageNameWithExtention.substring(imageNameWithExtention.lastIndexOf("."));
				String imagename = imageNameWithExtention.substring(0, imageNameWithExtention.lastIndexOf(".") - 1);
				int blobLength = (int) blob.length();
				byte[] blobAsBytes = blob.getBytes(1, blobLength);
				InputStream stream = new ByteArrayInputStream(blobAsBytes);
				downloadFile = new DefaultStreamedContent(stream, "image/jpg", imagename + imageExtention);
			}
		} catch (Exception e) {
			log.info("problem in geting image" + e);
		}
		return downloadFile;
	}

	public StreamedContent imageDownload() {
		/*
		 * Customer viewCustomer = null; CustomerEmploymentInfo
		 * viewCustomerEmpInfo = null; for(CustomerIdProof idProof:
		 * viewCustomerIdProof){ try{
		 * 
		 * log.info("idproof : "+idProof.getCustProofId()); Blob blob =
		 * idProof.getFsDocumentImg().getImage(); int blobLength = (int)
		 * blob.length(); byte[] blobAsBytes = blob.getBytes(1, blobLength);
		 * InputStream stream=new ByteArrayInputStream(blobAsBytes);
		 * downloadFile = new DefaultStreamedContent(stream, "image/jpg",
		 * idProof.getFsDocumentImg().getImgId().toPlainString()+".jpg"); }
		 * catch(Exception e) { log.error("image upload Error", e);
		 * e.printStackTrace(); } }
		 */
		// image upload end
		return null;
	}

	/**
	 * Document Save
	 * 
	 * @throws SerialException
	 * @throws IOException
	 * @throws SQLException
	 */
	public void saveDocument(String imagename, BigDecimal customerId, UploadedFile file, String idProofImageType) throws SerialException, IOException, SQLException {
		Blob blob = storeImage(file);
		int blobLength = (int) blob.length();
		byte[] blobAsBytes = blob.getBytes(1, blobLength);
		InputStream stream = new ByteArrayInputStream(blobAsBytes);
		if (idProofImageType.equalsIgnoreCase("company")) {
			setDownloadFileForCompany(new DefaultStreamedContent(stream, "image/jpg", "image.jpg"));
		} else if (idProofImageType.equalsIgnoreCase("partner")) {
			setDownloadFileForPartner(new DefaultStreamedContent(stream, "image/jpg", "image.jpg"));
		} else {
			setDownloadFileForOwner(new DefaultStreamedContent(stream, "image/jpg", "image.jpg"));
		}
		// downloadFile = new DefaultStreamedContent(stream, "image/jpg",
		// "image.jpg");
		/**
		 * Image Saving Start
		 */
		DocumentImg document = new DocumentImg();
		try {
			document.setImage(storeImage(file));
		} catch (SerialException e1) {
			log.info("problem in seting image" + e1);
		} catch (IOException e1) {
			log.info("problem in setting image" + e1);
		} catch (SQLException e1) {
			log.info("problem in setting image" + e1);
		}
		// CR for date based on application country
		// document.setUploadDate(new Date());
		document.setUploadDate(getCurrentTime());
		document.setImageName(imagename);
		if (!isImageSave) {
			try {
				if (document.getImage().length() > 0) {
					getCorpRegService().updateImage(document, customerId);
					if (idProofImageType.equalsIgnoreCase("company")) {
						setCompanyImageId(document.getImgId());
					} else if (idProofImageType.equalsIgnoreCase("partner")) {
						setPartnerImageId(document.getImgId());
					} else {
						setOwnerImageId(document.getImgId());
					}
				}
			} catch (SQLException e) {
				log.info("problem in setting image" + e);
			}
		} else {
			getCorpRegService().saveImage(document);
			if (idProofImageType.equalsIgnoreCase("company")) {
				setCompanyImageId(document.getImgId());
			} else if (idProofImageType.equalsIgnoreCase("partner")) {
				setPartnerImageId(document.getImgId());
			} else {
				setOwnerImageId(document.getImgId());
			}
		}
	}

	public void clearObjetive() {
		setBussNature(null);
		seconObjList.clear();
		setPrimaryObjId(null);
	}

	public void clearContact() {
		setContType(null);
		setStateId(null);
		setDistId(null);
		setCityId(null);
		setArea("");
		setStreet("");
		setBlock("");
		setTel("");
		setFlat("");
		setBuildingNo(null);
		setBooCheckMobileContact(null);
		setBooCheckTelContact(null);
		setEmail("");
		setBooEmailCheck(null);
	}

	public void clearCompanyDoc() {
		setIdExpDate(null);
		setCrno(null);
		setCompName("");
		setCompNameL("");
		setCompRegDate(null);
		setEmail("");
		setCoUploadFileVisibility(false);
		setRenderCoIdentityDoc(false);
		setEnabledddCompanyDataTable(false);
		setIdtype(null);
		setCoValdtBtn(true);
		setLocation(null);
		setContactPerson("");
		setContactNumber(null);
		setPaidUpCapital(new BigDecimal(0));
		setAuditStatementDate(null);
		setAuditGrossIncome(null);
	}

	public void clearPartnerDetails() {
		// setPartName("");
		setPidno("");
		setPidtype(new BigDecimal(0));
		setPidExpDate(null);
		setPartName("");
		setPartNameLocal("");
		setPartnerImageId(null);
		setPartnerContactNumber("");
		setPartnerEmail("");
		setOccupation(null);
		partnerList.clear();
		//partnerCustExpDate
		setPartnerCustExpDate(null);
		setPartnerUploadFileVisibility(false);
	}
	
	public void clearPartnerData() {
		setPidno(null);
		setPartName(null);
		setPartNameLocal(null);
		setPartnerCustExpDate(null);
		setPartnerContactNumber(null);
		setPartnerEmail(null);
		setOccupation(null);
		setPidtype(new BigDecimal(0));
		setPidExpDate(null);
		setPartnerImageId(null);
	}
	
	
	public void clearPartnerPopulateDetails() {
		// setPartName("");
		setPidno("");
		setPidtype(new BigDecimal(0));
		setPidExpDate(null);
		setPartName("");
		setPartNameLocal("");
		setPartnerImageId(null);
		setPartnerContactNumber("");
		setPartnerEmail("");
		setOccupation(null);
		setPartnerCustExpDate(null);
		setPartnerUploadFileVisibility(false);
	}

	public void clearTrnasactionSetting() {
		setDailyLimit(null);
		setWeeklyLimit(null);
		setMonthlyLimit(null);
		setHalfyearly(null);
		setAnnualLimit(null);
		setQuarterlyLimit(null);
		setArticle(null);
		setLevelId(null);
		setIncomeRange(null);
	}

	/**
	 * method is responsible to get partner customer details from db
	 */
	public void checkRegisterId() {
		setRenderRegisterPartner(false);
		List<CustomerIdProof> registerIdProofList = getCorpRegService().getRegisterId(getPidno(), sessionStateManage.getCountryId());
		// if(registerIdProofList.size()>0 &&
		// registerIdProofList.get(0).getFsCustomer().getActivatedInd().equalsIgnoreCase("1"))
		// {
		if (registerIdProofList.size() > 0 && registerIdProofList.get(0).getFsCustomer().getActivatedInd().equalsIgnoreCase(Constants.CUST_ACTIVE_INDICATOR)) {
			BigDecimal customerIdLocal = registerIdProofList.get(0).getFsCustomer().getCustomerId();
			setPartnerCustExpDate(registerIdProofList.get(0).getIdentityExpiryDate());
			
			List<Customer> custList = getCorpRegService().getCustomerNameFromCustomer(customerIdLocal);
			setPartName(custList.get(0).getFirstName() + " " + custList.get(0).getLastName());
			setPartNameLocal(custList.get(0).getFirstNameLocal() + " " + custList.get(0).getLastNameLocal());
			setPartnerContactNumber((custList.get(0).getMobile()));
			setPartnerEmail(custList.get(0).getEmail());
			// System.out.println("====Local name==="+custList.get(0).getFirstNameLocal());
			setPartnerCustomerId(custList.get(0).getCustomerId());
			// setReadOnlyPartnerName(true);
			setRenderRegisterPartner(false);
		} else {
			setRenderRegisterPartner(true);
			setPartName(null);
			setPartNameLocal(null);
			RequestContext.getCurrentInstance().execute("gotoCustomer.show();");
			return;
		}
	}
	
	
	private String selectedIdType = null;
	private Map<BigDecimal, String> mapIdentityType = new HashMap<BigDecimal, String>();
	

	public String getSelectedIdType() {
		return selectedIdType;
	}

	public void setSelectedIdType(String selectedIdType) {
		this.selectedIdType = selectedIdType;
	}
	
	
	

	public boolean idNumberCheck(){
		boolean status = false;
		if (getSelectedIdType() != null) {
			if (sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {

				mapIdentityType = ruleEngine.getComponentData("Identity Type");
				BigDecimal identityTpeId = null;
				for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
					if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
						identityTpeId = entry.getKey();
						break;
					}
				}
				if (getSelectedIdType() != null && getSelectedIdType().equalsIgnoreCase(identityTpeId.toPlainString())) {
					
					try {
						String returnString = generalService.getCivilIdStatus(new BigDecimal(getPidno()));
						if (returnString.equalsIgnoreCase("y")) {
							status = true;
						} else {
							status = false;
						}
					} catch (Exception e) {
						status = false;
					}
					
				}
			}
		}
		return status;
	}

	public void checkRegisterIdNew() throws ParseException {
	//	setSelectedIdType(new BigDecimal(28).toString());
	//	idNumberCheck();
		setPartName(null);
		setPartNameLocal(null);
		setPartnerCustExpDate(null);
		setPartnerContactNumber(null);
		setPartnerEmail(null);
		setOccupation(null);
		setRenderRegisterPartner(false);
		boolean verify = false;
		boolean civilidCheck = false;
		if(sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)){
			if(!civilIdValidation()) {
				RequestContext.getCurrentInstance().execute("civilCheck.show();");
				setErrmsg("Please enter valid Civil Id");
				setPartName(null);
				setPartNameLocal(null);
				setPartnerEmail(null);
				setPartnerContactNumber(null);
				setPartnerCustExpDate(null);	
				setOccupation(null);
				return;
			}
		}
		List<CustomerIdProof> registerIdProofList = getCorpRegService().getRegisterId(getPidno(), sessionStateManage.getCountryId());
		//registerIdProofList.clear();
		if (registerIdProofList.size() > 0) {
			for (CustomerIdProof regIdList : registerIdProofList) {
				
				if (regIdList.getFsBizComponentDataByCustomerTypeId().getComponentDataId().intValue() != getGeneralService().getComponentId(Constants.CUSTOMERTYPE_CORP, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()) {
					SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
					if (regIdList.getIdentityExpiryDate()!=null && dateformat.parse(dateformat.format(regIdList.getIdentityExpiryDate())).compareTo(dateformat.parse(dateformat.format(new Date()))) <= 0) {
						setPartnerCustExpDate(regIdList.getIdentityExpiryDate());
						verify = true;
					} else {
						setPartnerCustExpDate(regIdList.getIdentityExpiryDate());
						BigDecimal customerIdLocal = regIdList.getFsCustomer().getCustomerId();
						List<Customer> custList = getCorpRegService().getCustomerNameFromCustomer(customerIdLocal);
						setPartName(custList.get(0).getFirstName() + " " + custList.get(0).getLastName());
						setPartNameLocal(custList.get(0).getFirstNameLocal());
						/*setOccupation(custList.get(0).getFsCustomerEmploymentInfos().get(0).getFsBizComponentDataByOccupationId().getFsBizComponentDataDescs().get(0));*/
						// System.out.println("====Local name==="+custList.get(0).getFirstNameLocal());
						setPartnerCustomerId(custList.get(0).getCustomerId());
						// setReadOnlyPartnerName(true);
						setRenderRegisterPartner(false);
						if (custList.get(0).getMobile() == null || custList.get(0).getMobile().length() == 0) {
							setBooPartnerContactNumberReadOnly(false);
						} else {
							setPartnerContactNumber(custList.get(0).getMobile());
							setBooPartnerContactNumberReadOnly(true);
						}
						if (custList.get(0).getEmail() == null || custList.get(0).getEmail().length() == 0) {
							setBoopartnerEmailReadOnly(false);
						} else {
							setPartnerEmail(custList.get(0).getEmail());
							setBoopartnerEmailReadOnly(true);
						}
						try {
							BigDecimal occupationID = generalService.getOccupationId(regIdList.getFsCustomer().getCustomerId());
							if (occupationID != null) {
								String occupation = generalService.getOccupationDesc(occupationID,sessionStateManage.getLanguageId());
								if (occupation != null) {
									setOccupation(occupation);
								}else {
									setOccupation("UN-EMPLOYEE");
								}
							}else {
								setOccupation("UN-EMPLOYEE");
							}
						} catch (Exception e) {
							log.info("Exception occured while getting occupation " +e);
						}
						
						
						verify = false;
						break;
					}
				} else {
					setRenderRegisterPartner(true);
					setPartName(null);
					setPartNameLocal(null);
					RequestContext.getCurrentInstance().execute("gotoCustomer.show();");
					verify = false;
					break;
				}
			}
			if (verify) {
				setRenderRegisterPartner(false);
				setPartName(null);
				setPartNameLocal(null);
				RequestContext.getCurrentInstance().execute("partneridexpire.show();");
				setPartnerCustExpDate(null);
			}
		} else {
			setPartName(null);
			setPartNameLocal(null);
			setPartnerEmail(null);
			setPartnerContactNumber(null);
			setPartnerCustExpDate(null);	
			setOccupation(null);
			setRenderRegisterPartner(true);
			RequestContext.getCurrentInstance().execute("gotoCustomer.show();");
			return;
		}
	}

	public void redirectToBranch() {
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.invalidateSession();
			context.redirect("../registration/customerregistrationbranch.xhtml");
		} catch (Exception e) {
			log.info("problem in redirecting" + e);
		}
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	/**
	 * Responsible to fetch Image and populate in page
	 * 
	 * @param image
	 * @param fullImageName
	 * @throws SQLException
	 * @throws IOException
	 */
	public void showImageForCompany(Blob image, String fullImageName) throws SQLException, IOException {
		/* Just a confirmation, that there is a Image exist */
		if (fullImageName != null) {
			String imageExtention = fullImageName.substring(fullImageName.lastIndexOf("."));
			String imageName = fullImageName.substring(0, fullImageName.lastIndexOf(".") - 1);
			byte[] blobAsBytes = image.getBytes(1, (int) image.length());
			InputStream stream = new ByteArrayInputStream(blobAsBytes);
			setDownloadFileForCompany(new DefaultStreamedContent(stream, "image/jpg", imageName + imageExtention));
		}
	}

	/**
	 * Responsible to fetch Image and populate in page
	 * 
	 * @param image
	 * @param fullImageName
	 * @throws SQLException
	 * @throws IOException
	 */
	public void showImageForPartner(Blob image, String fullImageName) throws SQLException, IOException {
		/* Just a confirmation, that there is a Image exist */
		if (fullImageName != null) {
			String imageExtention = fullImageName.substring(fullImageName.lastIndexOf("."));
			String imageName = fullImageName.substring(0, fullImageName.lastIndexOf(".") - 1);
			byte[] blobAsBytes = image.getBytes(1, (int) image.length());
			InputStream stream = new ByteArrayInputStream(blobAsBytes);
			setDownloadFileForPartner(new DefaultStreamedContent(stream, "image/jpg", imageName + imageExtention));
		}
	}

	/**
	 * Responsible to fetch Image and populate in page
	 * 
	 * @param image
	 * @param fullImageName
	 * @throws SQLException
	 * @throws IOException
	 */
	public void showImageForOwner(Blob image, String fullImageName) throws SQLException, IOException {
		/* Just a confirmation, that there is a Image exist */
		if (fullImageName != null) {
			String imageExtention = fullImageName.substring(fullImageName.lastIndexOf("."));
			String imageName = fullImageName.substring(0, fullImageName.lastIndexOf(".") - 1);
			byte[] blobAsBytes = image.getBytes(1, (int) image.length());
			InputStream stream = new ByteArrayInputStream(blobAsBytes);
			setDownloadFileForOwner(new DefaultStreamedContent(stream, "image/jpg", imageName + imageExtention));
		}
	}

	@Autowired
	public CorpRegisterManageBean(IGeneralService<T> generalService) {
		setGeneralService(generalService);
	}

	private void clearSecurityQuestions() {
		drpObjective1 = new HashMap<BigDecimal, String>();
		drpObjective2 = new HashMap<BigDecimal, String>();
	}

	private void addObjectives(Map<BigDecimal, String> mapSQ) {
		drpObjective1.putAll(mapSQ);
		drpObjective2.putAll(mapSQ);
	}

	public Map<BigDecimal, String> getPrimaryObjective() {
		generateObjectives();
		return drpObjective1;
	}

	public void generateObjectiveList(AjaxBehaviorEvent event) {
		generateObjectives();
	}

	public void generateObjectives() {
		clearSecurityQuestions();
		Map<BigDecimal, String> mapTempSQList = new HashMap<BigDecimal, String>();
		if (getMapObjectives() == null || getMapObjectives().size() == 0) {
			try {
				mapObjectives = ruleEngine.getComponentData("Objectives");
			} catch (Exception e) {
			}
		}
		mapTempSQList.putAll(getMapObjectives());
		mapTempSQList.remove(getPrimaryObjId());
		mapTempSQList.remove(getSeconObj());
		addObjectives(mapTempSQList);
		if (getPrimaryObjId() != null && getPrimaryObjId().intValue() != 0) {
			drpObjective1.put(getPrimaryObjId(), getMapObjectives().get(getPrimaryObjId()));
		}
		if (getSeconObj() != null && getSeconObj().intValue() != 0) {
			drpObjective2.put(getSeconObj(), getMapObjectives().get(getSeconObj()));
		}
	}

	/**
	 * method to clear all values
	 */
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void resetValues() {
		setRenderValidate(true);
		setRenderObjective(false);
		setRenderContactDetails(false);
		setRenderCoIdentityDoc(false);
		setRenderPartnerDetail(false);
		setRenderOwnerDetails(false);
		setOwnerIdRegStatus(false);
		setRenderRegisterOwner(false);
		setAuthorUploadFileVisibility(false);
		setDuplicateOwnerDetails(false);
		setPartnerIdRegStatus(false);
		setRenderRegisterPartner(false);
		setPartnerUploadFileVisibility(false);
		setDuplicatePartnerDetails(false);
		setRenderPartneListAvailability(false);
		setRenderOwnerListAvailability(false);
		setRenderImage(false);
		setRenderTransactionalSetting(false);
		// setAddPartner(false);
		setCrno("");
		setCompName("");
		setCompNameL("");
		setCompRegDate(null);
		setEmail("");
		setCoValdtBtn(true);
		setLocation(new BigDecimal(0));
		setAMLStatus("");
		setContactPerson("");
		setContactNumber(null);
		setHitCount("");
		clearObjetive();
		clearContact();
		clearCompanyDoc();
		clearPartnerDetails();
		seconObjList.clear();
		partnerList.clear();
		authorisedList.clear();
		identityList.clear();
		setDownloadFileForCompany(null);
		setPaidUpCapital(new BigDecimal(0));
		setEnabledddCompanyDataTable(false);
		setEnabledddPartnerAuthDataTable(false);
		setImmediate(false);
		setDailyLimit(null);
		setWeeklyLimit(null);
		setMonthlyLimit(null);
		setHalfyearly(null);
		setAnnualLimit(null);
		setQuarterlyLimit(null);
		setIdentityListVisibility(false);
		setRenderSuccess(false);
		setAuditGrossIncome(null);
		setAuditStatementDate(null);
		Calendar cal = new GregorianCalendar();
		int year=cal.get(Calendar.YEAR);
		cal.set(year,11,31);
		Date auditMax = cal.getTime();
		setAuditMaxDate(auditMax);
		
		setCountryId(sessionStateManage.getCountryId());
		setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), getCountryId()));
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "corporateregistration.xhtml");
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../registration/corporateregistration.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
		}
	}

	/* *
	 * method is responsible to call on change *
	 */
	public void popEndDate(AddPartnerDetailBean partner) {
		setMinDate(partner.getEffectiveDate());
		setDisableEndDate(false);
	}

	public void defaultType() {
		System.out.println("called Default");
		setContType(new BigDecimal(50));
	}

	public String getSuccessMessage() {
		return getGeneralService().getMessage(sessionStateManage.getCountryId(), sessionStateManage.getLanguageId());
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public BigDecimal getPaidUpCapital() {
		return paidUpCapital;
	}

	public void setPaidUpCapital(BigDecimal paidUpCapital) {
		this.paidUpCapital = paidUpCapital;
	}

	public Boolean getRendermandatory() {
		return rendermandatory;
	}

	public void setRendermandatory(Boolean rendermandatory) {
		this.rendermandatory = rendermandatory;
	}

	public Boolean getRenderTransactionalSetting() {
		return renderTransactionalSetting;
	}

	public void setRenderTransactionalSetting(Boolean renderTransactionalSetting) {
		this.renderTransactionalSetting = renderTransactionalSetting;
	}

	public void nextCompanyIdentity() {
		/*if (getBooMobilecheck()!= null && getBooMobilecheck() == false && getBooEmailCheck()!= null &&getBooEmailCheck() == false) {*/
			setRenderCoIdentityDoc(true);
			setRenderContactDetails(false);
			setCoValdtBtn(false);
			setRenderValidate(true);
			setRenderObjective(false);
			setRenderPartnerDetail(false);
			setRenderOwnerDetails(false);
			setRenderTransactionalSetting(false);
			setEnabledddCompanyDataTable(false);
			setChangeCrNumber(false);
			setEnabledddPartnerAuthDataTable(false);
			setEnableScan(true);
			setRenderSuccess(false);
			getFetchCompanyIdentityTypeList();
		/*}*/
	}

	public Boolean nextToContact() {
		try {
			setCountryId(sessionStateManage.getCountryId());
			setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), getCountryId()));
			setRenderContactDetails(true);
			setCoValdtBtn(false);
			setRenderValidate(false);
			setRenderObjective(false);
			setRenderCoIdentityDoc(false);
			setRenderPartnerDetail(false);
			setRenderOwnerDetails(false);
			setEnabledddCompanyDataTable(false);
			setEnabledddPartnerAuthDataTable(false);
			setRenderSuccess(false);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean nextToObjective() throws ParseException {
		boolean objectivecheck = false;
		boolean verify = true;
		for (AddCoIdentityDetailBean addIdentitylist : identityList) {
			if (addIdentitylist.getIdExpDate() != null) {
				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
				if (dateformat.parse(addIdentitylist.getIdExpDate()).compareTo(dateformat.parse(dateformat.format(new Date()))) <= 0) {
					verify = false;
				} else {
					verify = true;
					break;
				}
			}
		}
		if (verify) {
			for (AddCoIdentityDetailBean addIdentitylist : identityList) {
				if (addIdentitylist.getIdtype().equalsIgnoreCase(Constants.COMPANY_REGISTRATION_DOC)) {
					setIdentityListVisibility(false);
					setRenderCoIdentityDoc(false);
					setRenderContactDetails(false);
					setRenderValidate(false);
					setCoValdtBtn(false);
					setRenderObjective(true);
					setRenderPartnerDetail(false);
					setRenderOwnerDetails(false);
					setRenderTransactionalSetting(false);
					setEnabledddCompanyDataTable(false);
					setEnabledddPartnerAuthDataTable(false);
					setRenderSuccess(false);
					objectivecheck = true;
					break;
				} else {
					setIdentityListVisibility(true);
					setRenderCoIdentityDoc(true);
					setRenderContactDetails(false);
					setRenderValidate(true);
					setCoValdtBtn(false);
					setRenderObjective(false);
					setRenderPartnerDetail(false);
					setRenderOwnerDetails(false);
					setRenderTransactionalSetting(false);
					setEnabledddCompanyDataTable(true);
					setEnabledddPartnerAuthDataTable(false);
					setRenderSuccess(false);
					objectivecheck = false;
				}
			}
		} else {
			objectivecheck = false;
			setEnableScan(false);
			setRenderModifyScan(false);
			RequestContext.getCurrentInstance().execute("idExpired.show();");
		}
		return objectivecheck;
	}

	public void backFromObjective() {
		setRenderCoIdentityDoc(true);
		setRenderContactDetails(false);
		setRenderValidate(true);
		setCoValdtBtn(false);
		setRenderObjective(false);
		setRenderPartnerDetail(false);
		setRenderOwnerDetails(false);
		setRenderTransactionalSetting(false);
		setEnabledddCompanyDataTable(true);
		setEnabledddPartnerAuthDataTable(false);
		setRenderSuccess(false);
		try {
			setCompanyIdProofDetail();
		} catch (Exception e) {
			log.info("Exception occured backFromObjective method");
		}
	
	}

	public Boolean nextToTrnasactionSetting1() {
		try {
			setRenderCoIdentityDoc(false);
			setRenderContactDetails(false);
			setRenderValidate(false);
			setCoValdtBtn(false);
			setRenderObjective(false);
			setRenderPartnerDetail(false);
			setRenderOwnerDetails(false);
			setRenderTransactionalSetting(true);
			setEnabledddCompanyDataTable(false);
			setEnabledddPartnerAuthDataTable(false);
			setRenderSuccess(false);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void backFromTrnasactionSetting() {
		setRenderCoIdentityDoc(false);
		setRenderContactDetails(true);
		setRenderValidate(false);
		setCoValdtBtn(false);
		setRenderObjective(false);
		setRenderPartnerDetail(false);
		setRenderOwnerDetails(false);
		setRenderTransactionalSetting(false);
		setEnabledddCompanyDataTable(false);
		setEnabledddPartnerAuthDataTable(false);
		setRenderSuccess(false);
		setCustomerContactDetails();
	}

	public void backFromContact() {
		setRenderCoIdentityDoc(false);
		setRenderContactDetails(false);
		setRenderValidate(false);
		setRenderObjective(true);
		setCoValdtBtn(false);
		setRenderPartnerDetail(false);
		setRenderOwnerDetails(false);
		setRenderTransactionalSetting(false);
		setEnabledddCompanyDataTable(false);
		setEnabledddPartnerAuthDataTable(false);
		setRenderSuccess(false);
		setSecondaryObjectiveDetails();
		setNatureOfBusinessDetails();
	}

	public ApllicationMailer1 getMailService1() {
		return mailService1;
	}

	public void setMailService1(ApllicationMailer1 mailService1) {
		this.mailService1 = mailService1;
	}

	/*public JavaMailSender getMailSender1() {
		return mailSender1;
	}

	public void setMailSender1(JavaMailSender mailSender1) {
		this.mailSender1 = mailSender1;
	}*/

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public void checkLimit() {
		String roleId1 = sessionStateManage.getRoleId();
		String sender = sessionStateManage.getUserName();
		String branchId = sessionStateManage.getBranchId();
		String employeeid = sessionStateManage.getEmployeeId().toString();
		String location1 = sessionStateManage.getLocation();
		String telephoneno = sessionStateManage.getTelephoneNumber();
		int roleId = Integer.parseInt(roleId1);
		String email1 = sessionStateManage.getEmail();
		List<Employee> empList = iEmployeeService.getEmployees();
		for (Employee emp : empList) {
			if (emp.getFsRoleMaster().getRoleId().intValue() == roleId) {
				int countryBranchId = emp.getFsCountryBranch().getCountryBranchId().intValue();
				// RequestContext context = RequestContext.getCurrentInstance();
				// context.execute("succ.show();");
				// MAIL GENERATION CODE
				List<Employee> newlist = iEmployeeService.getEmployees();
				ListIterator<Employee> le = newlist.listIterator();
				while (le.hasNext()) {
					Employee e1 = le.next();
					if (e1.getFsCountryBranch().getCountryBranchId().intValue() == countryBranchId && e1.getFsRoleMaster().getRoleId().intValue() < roleId) {
						if ((e1.getFsCountryBranch().getCountryBranchId().intValue() == countryBranchId) && (e1.getFsRoleMaster().getRoleId().intValue() == roleId - 1)) {
							System.out.println(e1.getFsRoleMaster().getRoleId());
							String emailId = e1.getEmail();
							String employeename = e1.getEmployeeName();
							System.out.println("staff mail id" + emailId);
							Random r = new Random();
							int tokennumber = r.nextInt(99999) + 10000;
							String tokennum = "" + tokennumber;
							// String customerid=""+getProfessionName();
							// mailService.sendTokenMail1(emailId, getEmail1(),
							// "test", customerid, tokennum);
							// String customerId=getPkCustomerId().toString();
							// String customerId=getCustomerId().toString();
							String customerId = getCrno();
							System.out.println(telephoneno);
							mailService1.sendTokenMail2(emailId, email1, "welcome", customerId, tokennum, sender, branchId, location1, telephoneno, employeename, email1);
						}
					}
				}
			}
		}
	}

	public Boolean nextToPartnetAuthorized() {
		// checkLimit();
		try {
			setRenderCoIdentityDoc(false);
			setRenderContactDetails(false);
			setRenderValidate(false);
			setRenderObjective(false);
			setCoValdtBtn(false);
			setRenderPartnerDetail(true);
			setRenderOwnerDetails(false);
			setRenderTransactionalSetting(false);
			setEnabledddCompanyDataTable(false);
			setEnablePartnerScan(true);
			setRenderSuccess(false);
			if (getCustomerId() != null) {
				setEnabledddPartnerAuthDataTable(true);
			} else {
				setEnabledddPartnerAuthDataTable(false);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void backFromPartnerAuthorized() {
		setRenderCoIdentityDoc(false);
		setRenderContactDetails(true);
		setRenderValidate(false);
		setCoValdtBtn(false);
		setRenderObjective(false);
		setRenderPartnerDetail(false);
		setRenderOwnerDetails(false);
		setRenderTransactionalSetting(false);
		setRenderPartnerDetail(false);
		setImmediate(false);
		setEnabledddCompanyDataTable(false);
		setEnabledddPartnerAuthDataTable(false);
		setRenderSuccess(false);
		clearPartnerPopulateDetails();
	}

	public boolean isEnabledddCompanyDataTable() {
		return enabledddCompanyDataTable;
	}

	public void setEnabledddCompanyDataTable(boolean enabledddCompanyDataTable) {
		this.enabledddCompanyDataTable = enabledddCompanyDataTable;
	}

	public Boolean getEnabledddPartnerAuthDataTable() {
		return enabledddPartnerAuthDataTable;
	}

	public void setEnabledddPartnerAuthDataTable(Boolean enabledddPartnerAuthDataTable) {
		this.enabledddPartnerAuthDataTable = enabledddPartnerAuthDataTable;
	}

	// to get partner type list and store into local map object
	public void getFetchCompanyIdentityTypeList() {
		
		/* Map<BigDecimal, String> temp = new HashMap<BigDecimal, String>();*/
		 
		 mapcomIdentityType = icustomerRegistrationService.getAllComponentComboDataForCustomer(sessionStateManage.getLanguageId(), "C", "Identity Type");
		 
		 log.info(mapcomIdentityType);
		 
		 
		/* mapcomIdentityType.clear();
		 
		 for (Map.Entry<BigDecimal, String> entry : temp.entrySet()) {
			    log.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			    
			    if( entry.getValue().equalsIgnoreCase(Constants.COMPANY_REGISTRATION_DOC))
			    {
			    	mapcomIdentityType.put(entry.getKey(), entry.getValue());
			    }
			}*/
		 
	
		/*mapcomIdentityType = ruleEngine.getComponentData("Identity Type");*/
		
		
	}
	
	public Map<BigDecimal, String> getMapcomIdentityType() {
		return mapcomIdentityType;
	}

	public void setMapcomIdentityType(Map<BigDecimal, String> mapcomIdentityType) {
		this.mapcomIdentityType = mapcomIdentityType;
	}

	public void addActionComDocument() {
		// try {
		boolean duplicate = false;
		for (AddCoIdentityDetailBean addIdentity : identityList) {
			if (addIdentity.getIdno() != null && getIdExpDate() != null) {
				if (addIdentity.getIdno().equalsIgnoreCase(getIdno())) {
					duplicate = true;
					setIdentityListVisibility(false);
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('duplicateDetails').show();");
					break;
				}
			} else {
				if (addIdentity.getIdTypeId().intValue() == getIdtype().intValue()) {
					duplicate = true;
					setIdentityListVisibility(false);
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('duplicateDetails').show();");
					break;
				}
			}
		}
		if (!duplicate) {
			if (!getImageUploadChecckForComapny()) {
				setCoUploadFileVisibility(true);
			} else {
				getFetchCompanyIdentityTypeList();
				AddCoIdentityDetailBean companyIdentity = new AddCoIdentityDetailBean();
				companyIdentity.setIdTypeId(this.idtype);
				companyIdentity.setIdtype(mapcomIdentityType.get(getIdtype()));
				companyIdentity.setImageId(getCompanyImageId());
				if (getIdtype().toString().equalsIgnoreCase(getGeneralService().getComponentId(Constants.COMPANY_REGISTRATION_DOC, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString())) {
					companyIdentity.setIdno(getIdno());
					companyIdentity.setIdExpDate(new SimpleDateFormat("dd/MM/yyyy").format(getIdExpDate()));
				}
				/*
				 * if(getIdtype().intValue() == Constants.COMPANY_REGISTRATION){
				 * companyIdentity.setIdno(getIdno());
				 * companyIdentity.setIdExpDate(new
				 * SimpleDateFormat("dd/MM/yyyy").format(getIdExpDate())); }
				 */
				identityList.add(companyIdentity);
				setIdtype(null);
				setImage_id(null);
				setIdExpDate(null);
				setIdentityListVisibility(false);
				setEnabledddCompanyDataTable(true);
				setCoUploadFileVisibility(false);
				setChangeCrNumber(false);
			}
		} else {
			setIdentityListVisibility(true);
		}
		/*
		 * } catch(Exception e) {
		 * log.info("problem in adding owner details to dataTable"+e); }
		 */
	}

	public void saveCompanyIdentityDocument() throws ParseException {
		try {
			for (AddCoIdentityDetailBean addIdentity : identityList) {
				CustomerIdProof custmrIdProof = new CustomerIdProof();
				Customer cust = new Customer();
				cust.setCustomerId(getCustomerId());
				custmrIdProof.setFsCustomer(cust);
				LanguageType langType = new LanguageType();
				langType.setLanguageId(sessionStateManage.getLanguageId());
				custmrIdProof.setFsLanguageType(langType);
				/** Customer Type */
				BizComponentData customerType = new BizComponentData();
				customerType.setComponentDataId(getGeneralService().getComponentId(Constants.CUSTOMERTYPE_CORP, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
				custmrIdProof.setFsBizComponentDataByCustomerTypeId(customerType);
				BizComponentData identityType = new BizComponentData();
				identityType.setComponentDataId((addIdentity.getIdTypeId()));
				custmrIdProof.setFsBizComponentDataByIdentityTypeId(identityType);
				if (addIdentity.getIdExpDate() != null && addIdentity.getIdTypeId().toString().equalsIgnoreCase(getGeneralService().getComponentId(Constants.COMPANY_REGISTRATION_DOC, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString())) {
					custmrIdProof.setIdentityInt(addIdentity.getIdno());
					Date idExpDate = new SimpleDateFormat("dd/MM/yyyy").parse(addIdentity.getIdExpDate());
					custmrIdProof.setIdentityExpiryDate(idExpDate);
				}
				
				System.out.println("getIdExpire Date :"+getIdExpDate()+"\t custmrIdProof.setIdentityExpiryDate( )"+custmrIdProof.getIdentityExpiryDate());
				custmrIdProof.setIdentityInt(addIdentity.getIdno());
				custmrIdProof.setApprovedBy(userName);
				// CR for sysdate based on application country
				// custmrIdProof.setApprovedDate(new Date());
				custmrIdProof.setApprovedDate(getCurrentTime());
				custmrIdProof.setCreatedBy(userName);
				// CR for sysdate based on application country
				// custmrIdProof.setCreationDate(new Date());
				custmrIdProof.setCreationDate(getCurrentTime());
				// custmrIdProof.setIdentityStatus("1");
				custmrIdProof.setIdentityStatus(Constants.CUST_ACTIVE_INDICATOR);
				// custmrIdProof.setCustProofId(getCustomerIdProofIdForCompanyIdentityDoc());
				System.out.println(addIdentity.isObjstatus() + "------------------" + addIdentity.isModified());
				if (addIdentity.getCustomerIdProofId() != null) {
					custmrIdProof.setUpdatedBy(userName);
					custmrIdProof.setCustProofId(addIdentity.getCustomerIdProofId());
					// CR for sysdate based on application country
					// custmrIdProof.setLastUpdatedDate(new Date());
					custmrIdProof.setLastUpdatedDate(getCurrentTime());
					//Update Expire Date
					Calendar cal = new GregorianCalendar();
					cal.setTime(new Date());
					cal.add(Calendar.DAY_OF_MONTH, +0);
					Date today90 = cal.getTime();
					SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
					String finalDate = form.format(today90);
					setExpminDate(finalDate);
					int year=cal.get(Calendar.YEAR);
					cal.set(year,11,31);
					Date auditMax = cal.getTime();
					setAuditMaxDate(auditMax);
					
					if(getIdExpDate()!=null){
					custmrIdProof.setIdentityExpiryDate(getIdExpDate());
					}/*commented by Rabil on 11 Sep 2017 as per OT,the validaity as per the user defined.else{
						custmrIdProof.setIdentityExpiryDate(auditMax);
					}*/
				} else {
					// custmrIdProof.setApprovedBy(userName);
					// CR for sysdate based on application country
					// custmrIdProof.setApprovedDate(new Date());
					//custmrIdProof.setApprovedDate(getCurrentTime());
					custmrIdProof.setCreatedBy(userName);
					// CR for sysdate based on application country
					// custmrIdProof.setCreationDate(new Date());
					custmrIdProof.setCreationDate(getCurrentTime());
				}
				getCorpRegService().saveCoIdentityDoc(custmrIdProof);
			}
		} catch (Exception e) {
			log.info("Excepption occured saving Company ID Document"+e);
		}
		/*
		 * for (AddCoIdentityDetailBean addIdentity : identityListDeleted) {
		 * System
		 * .out.println("the delete record company document is"+identityListDeleted
		 * .size());
		 * 
		 * custmrIdProof = new CustomerIdProof();
		 * 
		 * cust = new Customer(); cust.setCustomerId(getCustomerId());
		 * custmrIdProof.setFsCustomer(cust);
		 * 
		 * langType = new LanguageType();
		 * langType.setLanguageId(sessionStateManage.getLanguageId());
		 * custmrIdProof.setFsLanguageType(langType);
		 * 
		 * 
		 * custmrIdProof.setIdentityInt(addIdentity.getIdno());
		 * if(addIdentity.getIdTypeId()!=null){
		 * if(addIdentity.getIdTypeId().intValue() ==
		 * Constants.COMPANY_REGISTRATION){
		 * custmrIdProof.setIdentityInt(addIdentity.getIdno()); Date idExpDate =
		 * new SimpleDateFormat("dd/MM/yyyy").parse(addIdentity.getIdExpDate());
		 * custmrIdProof.setIdentityExpiryDate(idExpDate); }
		 * 
		 * }
		 * 
		 * DocumentImg imgDoc = new DocumentImg();
		 * imgDoc.setImgId(addIdentity.getImageId());
		 * custmrIdProof.setFsDocumentImg(imgDoc);
		 * 
		 * custmrIdProof.setApprovedBy(userName); //CR for date based on
		 * application country //custmrIdProof.setApprovedDate(new Date());
		 * custmrIdProof.setApprovedDate(getCurrentTime());
		 * custmrIdProof.setUpdatedBy(userName); //CR for date based on
		 * application country //custmrIdProof.setLastUpdatedDate(new Date());
		 * custmrIdProof.setLastUpdatedDate(getCurrentTime());
		 * custmrIdProof.setCreatedBy(userName); //CR for date based on
		 * application country //custmrIdProof.setCreationDate(new Date());
		 * custmrIdProof.setCreationDate(getCurrentTime());
		 * //custmrIdProof.setIdentityStatus("0");
		 * custmrIdProof.setIdentityStatus(Constants.CUST_INACTIVE_INDICATOR);
		 * 
		 * custmrIdProof.setCustProofId(addIdentity.getCustomerIdProofId());
		 * 
		 * corpRegService.updateCompanyIdentityDocument( custmrIdProof,
		 * custmrIdProof.getCustProofId()); }
		 */
	}

	/* Responsible to populate State,District, City in emp Details */
	public List<StateMasterDesc> getLstStates() {
		popState();
		return lstStates;
	}

	public void setLstStates(List<StateMasterDesc> lstStates) {
		this.lstStates = lstStates;
	}

	public List<DistrictMasterDesc> getLstDistrict() {
		return lstDistrict;
	}

	public void setLstDistrict(List<DistrictMasterDesc> lstDistrict) {
		this.lstDistrict = lstDistrict;
	}

	public List<CityMasterDesc> getLstcity() {
		return lstcity;
	}

	public void setLstcity(List<CityMasterDesc> lstcity) {
		this.lstcity = lstcity;
	}

	public void popState() {
		List<StateMasterDesc> stateMaster = getGeneralService().getStateList(sessionStateManage.getLanguageId(), sessionStateManage.getCountryId());
		setLstStates(stateMaster);
	}

	/*
	 * 
	 * method to get District from db and add all the state code and name will
	 * be mapped to hashMap
	 */
	public void popDistrict(AjaxBehaviorEvent e) {
		List<DistrictMasterDesc> lstDistrict = getGeneralService().getDistrictList(sessionStateManage.getLanguageId(), sessionStateManage.getCountryId(), getStateId());
		setLstDistrict(lstDistrict);
	}

	/**
	 * 
	 * method to get city from db and add all the state code and name will be
	 * mapped to hashMap
	 */
	public void popCity(AjaxBehaviorEvent e) {
		// SessionStateManage sessionStateManage = new SessionStateManage();
		List<CityMasterDesc> lstCity = getGeneralService().getCityList(sessionStateManage.getLanguageId(), sessionStateManage.getCountryId(), getStateId(), getDistId());
		setLstcity(lstCity);
	}

	public Boolean getChangeCrNumber() {
		return changeCrNumber;
	}

	public void setChangeCrNumber(Boolean changeCrNumber) {
		this.changeCrNumber = changeCrNumber;
	}

	public void changeIdtypeMethod(AjaxBehaviorEvent event) {
		if (getIdtype().toString().equalsIgnoreCase(getGeneralService().getComponentId(Constants.COMPANY_REGISTRATION_DOC, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString())) {
			setChangeCrNumber(true);
			setIdno(getCrno());
			Calendar cal = new GregorianCalendar();
			/*cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, +0);*/
			
			int year=cal.get(Calendar.YEAR);
			cal.set(year,11,31);
			Date today90 = cal.getTime();
			/** Rabil commented on 13/12/2016 */
			if(getIdExpDate()==null){
			setIdExpDate(today90);
			}
			/** Rabil commented end on 13/12/2016 */
			/*setExpDateAdder(finalDate);*/
		} else {
			setChangeCrNumber(false);
		}
	}

	/*
	 * public void changeIdtypeMethod(AjaxBehaviorEvent event) {
	 * 
	 * if(getIdtype().intValue() == Constants.COMPANY_REGISTRATION){
	 * setChangeCrNumber(true); setIdno(getCrno());
	 * 
	 * }else{ setChangeCrNumber(false); }
	 * 
	 * }
	 */
	public void partnerCheck(AddPartnerDetailBean partnerDeatil) {
		if (partnerDeatil.getCheckedPartner()) {
			partnerDeatil.setEditablePartner(true);
			partnerDeatil.setNotEditablePartner(false);
			partnerDeatil.setPatnerPercentage(null);
		} else {
			if(partnerDeatil.getPatnerPercentage()!=null)
			{
				partnerDeatil.setEditablePartner(false);
				partnerDeatil.setNotEditablePartner(true);
				partnerDeatil.setPatnerPercentage(null);
			}
			else{
			partnerDeatil.setEditablePartner(false);
			partnerDeatil.setNotEditablePartner(true);
			}
		}
	}

	public void authorizedCheck(AddPartnerDetailBean partnerDeatil) {
		if (partnerDeatil.getCheckedAuthorized()) {
			partnerDeatil.setEditableAuthorized(true);
			partnerDeatil.setNotEditableAuthorized(false);
			partnerDeatil.setEffectiveDate(null);
			partnerDeatil.setValidUpTo(null);
		} else {
			partnerDeatil.setEditableAuthorized(false);
			partnerDeatil.setNotEditableAuthorized(true);
			partnerDeatil.setValidUpToString(null);
			partnerDeatil.setEffectiveDateString(null);
		}
	}

	/**
	 * method to save partner details
	 * 
	 * @throws ParseException
	 */
	public void savePartnerAuthorized() throws ParseException {
		PartnerAuthorized partnerAuthorized =null;
		// DocumentImg imgDoc = new DocumentImg();
		for (AddPartnerDetailBean addPartnerDetailBean : partnerList) {
			
			partnerAuthorized = new PartnerAuthorized();
			// imgDoc.setImgId(addPartnerDetailBean.getImageId());
			cust = new Customer();
			/** partner customer id */
			Customer partnerCustomer = new Customer();
			partnerCustomer.setCustomerId(addPartnerDetailBean.getPartnerCustomerId());
			partnerAuthorized.setFsCustomerByRefCustomerId(partnerCustomer);
			/** setting customerId **/
			cust.setCustomerId(getCustomerId());
			partnerAuthorized.setFsCustomer(cust);
			// partnerAuthorized.setFsDocumentImg(imgDoc);
			langType = new LanguageType();
			langType.setLanguageId(sessionStateManage.getLanguageId());
			partnerAuthorized.setFsLanguageType(langType);
			partnerAuthorized.setPartnerAuthorizedId(addPartnerDetailBean.getCustomerIdProofId());
			// Date expDate = new
			// SimpleDateFormat("dd/MM/yyyy").parse(addPartnerDetailBean.getEffectiveDate());
			if (addPartnerDetailBean.getCheckedAuthorized()) {
				partnerAuthorized.setEffectiveDate(addPartnerDetailBean.getEffectiveDate());
				partnerAuthorized.setValidUpto(addPartnerDetailBean.getValidUpTo());
			}
			else
			{
				partnerAuthorized.setEffectiveDate(null);
				partnerAuthorized.setValidUpto(null);
			}
			if (addPartnerDetailBean.getCheckedPartner()) {
				System.out.println("percebtage =========  " + addPartnerDetailBean.getPatnerPercentage());
				partnerAuthorized.setPartnerPercentage(addPartnerDetailBean.getPatnerPercentage());
			}
			else
			{
				partnerAuthorized.setPartnerPercentage(null);
			}
			if (addPartnerDetailBean.getTransactionBehalf()) {
				// partnerAuthorized.setTransactionBehalf("1");
				partnerAuthorized.setTransactionBehalf(Constants.CUST_ACTIVE_INDICATOR);
			}
			else
			{
				partnerAuthorized.setTransactionBehalf(null);
			}
			// partnerAuthorized.setStatus("1");
			partnerAuthorized.setStatus(Constants.CUST_ACTIVE_INDICATOR);
			if (addPartnerDetailBean.getCustomerIdProofId() != null) {
				partnerAuthorized.setUpdatedBy(userName);
				// CR for sysdate based on application country
				// partnerAuthorized.setUpdatedDate(new Date());
				partnerAuthorized.setUpdatedDate(getCurrentTime());
				partnerAuthorized.setCreatedBy(addPartnerDetailBean.getCreatedBy());
				partnerAuthorized.setCreatedDate(addPartnerDetailBean.getCreatedDate());
			} else {
				partnerAuthorized.setCreatedBy(userName);
				// CR for sysdate based on application country
				// partnerAuthorized.setCreatedDate(new Date());
				partnerAuthorized.setCreatedDate(getCurrentTime());
			}
			getCorpRegService().savePartnerAuthorized(partnerAuthorized);
			addPartnerDetailBean= null;
		}
		for (AddPartnerDetailBean addPartnerDetailBean : partnerListDeleted) {
			// imgDoc.setImgId(addPartnerDetailBean.getImageId());
			cust = new Customer();
			/** partner customer id */
			Customer partnerCustomer = new Customer();
			partnerCustomer.setCustomerId(addPartnerDetailBean.getPartnerCustomerId());
			partnerAuthorized.setFsCustomerByRefCustomerId(partnerCustomer);
			/** setting customerId **/
			cust.setCustomerId(getCustomerId());
			partnerAuthorized.setFsCustomer(cust);
			// partnerAuthorized.setFsDocumentImg(imgDoc);
			langType = new LanguageType();
			langType.setLanguageId(sessionStateManage.getLanguageId());
			partnerAuthorized.setFsLanguageType(langType);
			partnerAuthorized.setEffectiveDate(addPartnerDetailBean.getEffectiveDate());
			partnerAuthorized.setValidUpto(addPartnerDetailBean.getValidUpTo());
			// partnerAuthorized.setStatus("0");
			partnerAuthorized.setStatus(Constants.CUST_INACTIVE_INDICATOR);
			if (addPartnerDetailBean.getCustomerIdProofId() != null) {
				partnerAuthorized.setCreatedBy(userName);
				// CR for sysdate based on application country
				// partnerAuthorized.setCreatedDate(new Date());
				partnerAuthorized.setCreatedDate(getCurrentTime());
			} else {
				partnerAuthorized.setUpdatedBy(userName);
				// CR for sysdate based on application country
				// partnerAuthorized.setUpdatedDate(new Date());
				partnerAuthorized.setUpdatedDate(getCurrentTime());
			}
			getCorpRegService().savePartnerAuthorized(partnerAuthorized);
		}
	}

	public void setPartnerAuthorized() {
		AddPartnerDetailBean addPartnerDetailBean = null;
		partnerList.clear();
		List<PartnerAuthorized> partnerAuthorizedList = getCorpRegService().getPartnerAuthorized(getCustomerId());
		if (partnerAuthorizedList.size() > 0) {
			int count = 0;
			getFetchPartnerIdentityTypeList();
			for (PartnerAuthorized partnerAuthorized : partnerAuthorizedList) {
				count = 0;
				if(partnerList!=null && partnerList.size() > 0){
					for(AddPartnerDetailBean lst : partnerList){
						if(getCorpRegService().getIdNumber(partnerAuthorized.getFsCustomerByRefCustomerId().getCustomerId()).compareTo(lst.getPidno())==0){
							count = count + 1;
							if(count > 0){
								break;
							}
						}
					}
				}
				if(count > 0){
					continue;
				}
				addPartnerDetailBean = new AddPartnerDetailBean();
				addPartnerDetailBean.setPartName(partnerAuthorized.getFsCustomerByRefCustomerId().getFirstName() + " " + partnerAuthorized.getFsCustomerByRefCustomerId().getLastName());
				addPartnerDetailBean.setPartNameLocal(partnerAuthorized.getFsCustomerByRefCustomerId().getFirstNameLocal() + " " + partnerAuthorized.getFsCustomerByRefCustomerId().getLastNameLocal());
				addPartnerDetailBean.setPidno(getCorpRegService().getIdNumber(partnerAuthorized.getFsCustomerByRefCustomerId().getCustomerId()));
				addPartnerDetailBean.setPatnerPercentage(partnerAuthorized.getPartnerPercentage());
				addPartnerDetailBean.setCreatedBy(partnerAuthorized.getCreatedBy());
				addPartnerDetailBean.setCreatedDate(partnerAuthorized.getCreatedDate());
				
				List<Customer> listCust=	corpRegService.getCustomerList(partnerAuthorized.getFsCustomerByRefCustomerId().getCustomerId());
				if(listCust.get(0).getIsActive().equalsIgnoreCase(Constants.Yes)){
				addPartnerDetailBean.setHighLightCustomer(true);
		 
				}
				
				if (partnerAuthorized.getPartnerPercentage() != null) {
					addPartnerDetailBean.setCheckedPartner(true);
				}
				if (partnerAuthorized.getEffectiveDate() != null && partnerAuthorized.getValidUpto() != null) {
					addPartnerDetailBean.setEffectiveDateString(new SimpleDateFormat("dd/MM/yyyy").format(partnerAuthorized.getEffectiveDate()));
					addPartnerDetailBean.setEffectiveDate(partnerAuthorized.getEffectiveDate());
					addPartnerDetailBean.setValidUpToString(new SimpleDateFormat("dd/MM/yyyy").format(partnerAuthorized.getValidUpto()));
					addPartnerDetailBean.setValidUpTo(partnerAuthorized.getValidUpto());
					addPartnerDetailBean.setCheckedAuthorized(true);
				}
				if (partnerAuthorized.getTransactionBehalf() != null) {
					addPartnerDetailBean.setTransactionBehalf(true);
				}
				// addPartnerDetailBean.setImageId(partnerAuthorized.getFsDocumentImg().getImgId());
				addPartnerDetailBean.setCustomerIdProofId(partnerAuthorized.getPartnerAuthorizedId());
				addPartnerDetailBean.setPartnerCustomerId(partnerAuthorized.getFsCustomerByRefCustomerId().getCustomerId());
				List<Customer> customerList = branchServiec.getCustomerInfo(partnerAuthorized.getFsCustomerByRefCustomerId().getCustomerId());
				if (customerList != null && customerList.size() != 0) {
					addPartnerDetailBean.setPartnerContactNumber(customerList.get(0).getMobile());
					addPartnerDetailBean.setPartnerEmail(customerList.get(0).getEmail());
					try {
						BigDecimal occupationID = generalService.getOccupationId(partnerAuthorized.getFsCustomerByRefCustomerId().getCustomerId());
						if (occupationID != null) {
							String occupation = generalService.getOccupationDesc(occupationID,sessionStateManage.getLanguageId());
							if (occupation != null) {
								addPartnerDetailBean.setOccupation(occupation);
							}else {
								addPartnerDetailBean.setOccupation("UN-EMPLOYEE");
							}
							
						}else{
							addPartnerDetailBean.setOccupation("UN-EMPLOYEE");
						}
					} catch (Exception e) {
						log.info("Exception occured while getting occupation " +e);
					}
					
					
				}
				partnerList.add(addPartnerDetailBean);
				count = 0;
			}
		}
	}

	public void removeCompanyDocument(AddCoIdentityDetailBean bean) {
		identityList.remove(bean);
		if (bean.getCustomerIdProofId() != new BigDecimal(0)) {
			identityListDeleted.add(bean);
		}
	}

	public Boolean getImmediate() {
		return immediate;
	}

	public void setImmediate(Boolean immediate) {
		this.immediate = immediate;
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

	public void checkMobile() {
		setBooMobilecheck(false);
		List<Customer> matchMobile = new ArrayList<Customer>();
		matchMobile.addAll(getGeneralService().getMobileCheck(sessionStateManage.getCountryId(), getContactNumber()));
		if (getCustomerId() != null) {
			customerList = getCorpRegService().getCompanyRegistrationStatus(sessionStateManage.getCountryId(), getCrno());
			if (getCustomerId().intValue() == customerList.get(0).getCustomerId().intValue() && getContactNumber().equalsIgnoreCase(customerList.get(0).getMobile())) {
				setBooMobilecheck(false);
			} else if (matchMobile.size() > 0) {
				setBooMobilecheck(true);
			}
		} else if (matchMobile.size() > 0) {
			setBooMobilecheck(true);
		}
	}

	public void checkEmail() {
		setBooEmailCheck(false);
		List<Customer> matchEmail = new ArrayList<Customer>();
		matchEmail.addAll(getGeneralService().getEmailCheck(getEmail()));
		if (getCustomerId() != null) {
			customerList = getCorpRegService().getCompanyRegistrationStatus(sessionStateManage.getCountryId(), getCrno());
			if (getCustomerId().intValue() == customerList.get(0).getCustomerId().intValue() && getEmail().equalsIgnoreCase(customerList.get(0).getEmail())) {
				setBooEmailCheck(false);
			} else if (matchEmail.size() > 0) {
				setBooEmailCheck(true);
			}
		} else if (matchEmail.size() > 0) {
			setBooEmailCheck(true);
		}
	}

	// CR Employee Salary Limit Fields
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

	public void comparingDailyWeekly(FacesContext context, UIComponent component, Object value) {
		BigDecimal weeklyValue = (BigDecimal) value;
		System.out.println("!!!!!!!" + weeklyValue);
		System.out.println(" getDailyLimit : " + getDailyLimit() + " weeklyValue : " + weeklyValue);
		if (getDailyLimit() == null) {
			FacesMessage msg = new FacesMessage("Please Enter Daily and Then Weekly", "Please Enter Daily and Then Weekly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getDailyLimit().compareTo(weeklyValue) > 0 || getDailyLimit().compareTo(weeklyValue) == 0) {
			setWeeklyLimit(null);
			FacesMessage msg = new FacesMessage("Please Enter Greater Than Daily", "Please Enter Greater Than Daily");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	public void comparingWeeklyMonthly(FacesContext context, UIComponent component, Object value) {
		BigDecimal monthlyValue = (BigDecimal) value;
		if (getWeeklyLimit() == null || monthlyValue == null) {
			FacesMessage msg = new FacesMessage("Please Enter Weekly and Then Monthly", "Please Enter Weekly and Then Monthly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getWeeklyLimit().compareTo(monthlyValue) > 0 || getWeeklyLimit().compareTo(monthlyValue) == 0) {
			setMonthlyLimit(null);
			FacesMessage msg = new FacesMessage("Please Enter Greater Than Weekly", "Please Enter Greater Than Weekly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	public void comparingMonthlyQuarterly(FacesContext context, UIComponent component, Object value) {
		BigDecimal quarterlyValue = (BigDecimal) value;
		if (getMonthlyLimit() == null || quarterlyValue == null) {
			FacesMessage msg = new FacesMessage("Please Enter Monthly and Then Quarterly", "Please Enter Monthly and Then Quarterly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getMonthlyLimit().compareTo(quarterlyValue) > 0 || getMonthlyLimit().compareTo(quarterlyValue) == 0) {
			setQuarterlyLimit(null);
			FacesMessage msg = new FacesMessage("Please Enter Greater Than Monthly", "Please Enter Greater Than Monthly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	public void comparingQuarterlyHalfYearly(FacesContext context, UIComponent component, Object value) {
		BigDecimal halfYearlyValue = (BigDecimal) value;
		if (getQuarterlyLimit() == null || halfYearlyValue == null) {
			FacesMessage msg = new FacesMessage("Please Enter Quarterly and Then Half Yearly", "Please Enter Quarterly and Then Half Yearly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getQuarterlyLimit().compareTo(halfYearlyValue) > 0 || getQuarterlyLimit().compareTo(halfYearlyValue) == 0) {
			setHalfyearly(null);
			FacesMessage msg = new FacesMessage("Please Enter Greater Than Quarterly", "Please Enter Greater Than Quarterly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	public void comparingHalfYearlyAnnually(FacesContext context, UIComponent component, Object value) {
		BigDecimal annualyValue = (BigDecimal) value;
		System.out.println("!!!!!!!" + annualyValue);
		System.out.println(" getHalfyearly : " + getHalfyearly() + " annualyValue : " + annualyValue);
		if (getHalfyearly() == null || annualyValue == null) {
			FacesMessage msg = new FacesMessage("Please Enter Half Yearly and Then Annual", "Please Enter Half Yearly and Then Annual");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getHalfyearly().compareTo(annualyValue) > 0 || getHalfyearly().compareTo(annualyValue) == 0) {
			setAnnualLimit(null);
			FacesMessage msg = new FacesMessage("Please Enter Greater Than HalfYearly", "Please Enter Greater Than HalfYearly");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	public BigDecimal getUpdateCustomerRefNo() {
		return updateCustomerRefNo;
	}

	public void setUpdateCustomerRefNo(BigDecimal updateCustomerRefNo) {
		this.updateCustomerRefNo = updateCustomerRefNo;
	}

	/*
	 * private List<Document> lstDocument = new ArrayList<Document>();
	 * List<UserFinancialYear> DealYearList = new
	 * ArrayList<UserFinancialYear>(); private String processIn = "U"; private
	 * BigDecimal customerRefNo;
	 * 
	 * private String documents; private String dealYear; private BigDecimal
	 * documentSerialityNo;
	 * 
	 * public String getProcessIn() { return processIn; }
	 * 
	 * public void setProcessIn(String processIn) { this.processIn = processIn;
	 * }
	 * 
	 * public BigDecimal getCustomerRefNo() {
	 * 
	 * return customerRefNo; }
	 * 
	 * public void setCustomerRefNo(BigDecimal customerRefNo) {
	 * this.customerRefNo = customerRefNo; }
	 * 
	 * 
	 * 
	 * public void setDealYear(String dealYear) { this.dealYear = dealYear; }
	 * 
	 * public BigDecimal getUpdateCustomerRefNo() { return updateCustomerRefNo;
	 * }
	 * 
	 * public void setUpdateCustomerRefNo(BigDecimal updateCustomerRefNo) {
	 * this.updateCustomerRefNo = updateCustomerRefNo; }
	 * 
	 * public BigDecimal getDocumentSerialityNo() { return documentSerialityNo;
	 * }
	 * 
	 * public void setDocumentSerialityNo(BigDecimal documentSerialityNo) {
	 * this.documentSerialityNo = documentSerialityNo; }
	 * 
	 * 
	 * 
	 * public void setDocuments(String documents) { this.documents = documents;
	 * }
	 * 
	 * public String getDocuments() {
	 * 
	 * lstDocument = generalService.getDocument(
	 * Constants.DOCUMENT_CODE_FOR_CUSTOMER,
	 * sessionStateManage.getLanguageId()); for (Document lstdoc : lstDocument)
	 * { setDocumentSerialityNo(lstdoc.getDocumentID());
	 * 
	 * } return documents; }
	 * 
	 * public String getDocumentSerialID(String processIn) {
	 * 
	 * String documentSerialID = generalService
	 * .getNextDocumentReferenceNumber(sessionStateManage
	 * .getCountryId().intValue(), sessionStateManage
	 * .getCompanyId().intValue(), getDocumentSerialityNo() .intValue(), new
	 * BigDecimal(dealYear).intValue(), processIn); setCustomerRefNo(new
	 * BigDecimal(documentSerialID)); return documentSerialID; }
	 * 
	 * public String getDealYear() { DealYearList =
	 * generalService.getDealYear(getCurrentTime()); if (DealYearList.size()>0)
	 * {
	 * 
	 * dealYear = DealYearList.get(0).getFinancialYear().toString();
	 * 
	 * setDealYear(dealYear); } return dealYear; }
	 */
	// comparing registration date and expire date in corpregistration
	public void comparingRegDateExpireDate() {
		/*
		 * if(getIdExpDate().compareTo(getCompRegDate())<=0){
		 * System.out.println("not applied"); setIdExpDate(null);
		 * RequestContext.getCurrentInstance().execute("datecheck.show();"); }
		 */
	}

	public Boolean getImageUploadChecckForComapny() {
		return imageUploadChecckForComapny;
	}

	public void setImageUploadChecckForComapny(Boolean imageUploadChecckForComapny) {
		this.imageUploadChecckForComapny = imageUploadChecckForComapny;
	}

	public Boolean getImageUploadChecckForPartner() {
		return imageUploadChecckForPartner;
	}

	public void setImageUploadChecckForPartner(Boolean imageUploadChecckForPartner) {
		this.imageUploadChecckForPartner = imageUploadChecckForPartner;
	}

	public String getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}

	public boolean getEnableScan() {
		return enableScan;
	}

	public void setEnableScan(boolean enableScan) {
		this.enableScan = enableScan;
	}

	public boolean isEnableCheckId() {
		return enableCheckId;
	}

	public void setEnableCheckId(boolean enableCheckId) {
		this.enableCheckId = enableCheckId;
	}

	public boolean getEnablePartnerScan() {
		return enablePartnerScan;
	}

	public void setEnablePartnerScan(boolean enablePartnerScan) {
		this.enablePartnerScan = enablePartnerScan;
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

	public Date getPartnerCustExpDate() {
		return partnerCustExpDate;
	}

	public void setPartnerCustExpDate(Date partnerCustExpDate) {
		this.partnerCustExpDate = partnerCustExpDate;
	}

	public void checkId() throws IOException, ParseException {
		boolean duplicate = duplicateCheck();
		if (!duplicate) {
			// setEnableScan(false);
			StringBuffer urlBuffer = new StringBuffer();
			String equals = "=";
			String rootContext = "http://192.168.100.195/IDAutomation/frmcheckID.ashx?ClientRefNO";
			urlBuffer.append(rootContext).append(equals).append(getIdno());
			try {
				URL knetRequest = new URL(urlBuffer.toString());
				HttpURLConnection httConn = null;
				BufferedReader in = null;
				httConn = (HttpURLConnection) knetRequest.openConnection();
				in = new BufferedReader(new InputStreamReader(httConn.getInputStream()));
				String str = in.readLine();
				System.out.println("IIIIIIIIIII== " + str);
				if (str.startsWith("DMS_Error")) {
					in.close();
					setEnableScan(false);
					RequestContext.getCurrentInstance().execute("scanRecord.show();");
				} else {
					String s22 = str;
					HashMap<String, String> map = new HashMap<String, String>();
					StringTokenizer stToken = new StringTokenizer(s22, ",");
					while (stToken.hasMoreTokens()) {
						String tNmae = stToken.nextToken();
						String arr[] = tNmae.split("=");
						if (arr.length == 1) {
							map.put(arr[0], "");
						} else {
							map.put(arr[0], arr[1]);
						}
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
						setEnableScan(false);
						RequestContext.getCurrentInstance().execute("scanRecord.show();");
					} else {
						String[] str11 = s22.split("=");
						String str22 = str11.toString();
						String[] str12 = str11[1].split(",");
						String[] str13 = str11[2].split(",");
						String[] str14 = str11[3].split(",");
						java.util.Date expiryDcheck = new SimpleDateFormat("yyyyMMdd").parse(str13[0].toString());
						java.util.Date birthDate = new SimpleDateFormat("yyyyMMdd").parse(str14[0].toString());
						String expiryDate1 = new SimpleDateFormat("dd/MM/yyyy").format(expiryDcheck);
						setIdNumberScan(str12[0]);
						setIdExpDate(new SimpleDateFormat("dd/MM/yyyy").parse(expiryDate1));
						SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
						getFetchCompanyIdentityTypeList();
						String df = dateformat.format(getIdExpDate());
						if (expiryDcheck.compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {
							duplicate = duplicateCheck();
							if (!duplicate) {
								AddCoIdentityDetailBean companyIdentity = new AddCoIdentityDetailBean();
								companyIdentity.setIdTypeId(this.idtype);
								companyIdentity.setIdtype(mapcomIdentityType.get(getIdtype()));
								companyIdentity.setImageId(null);
								if (getIdtype().toString().equalsIgnoreCase(getGeneralService().getComponentId(Constants.COMPANY_REGISTRATION_DOC, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString())) {
									companyIdentity.setIdno(getIdno());
									companyIdentity.setIdExpDate(new SimpleDateFormat("dd/MM/yyyy").format(getIdExpDate()));
								}
								/*
								 * if(getIdtype().intValue() ==
								 * Constants.COMPANY_REGISTRATION){
								 * companyIdentity.setIdno(getIdno());
								 * companyIdentity.setIdExpDate(new
								 * SimpleDateFormat
								 * ("dd/MM/yyyy").format(getIdExpDate())); }
								 */
								identityList.add(companyIdentity);
								clearProofInfo();
								setEnableScan(true);
							} else {
								RequestContext context = RequestContext.getCurrentInstance();
								context.execute("PF('duplicateDetails').show();");
							}
						} else {
							setEnableScan(true);
						}
					}
				}
				in.close();
			} catch (MalformedURLException e) {
				log.info("Connection Problem: " + e);
			} catch (IOException ie) {
				log.info("Connection Problem: " + ie);
			}
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('duplicateDetails').show();");
		}
	}

	public void scan() throws IOException, ParseException {
		setDobS(null);
		/*
		 * if(getIdExpDate()!=null){
		 * 
		 * String dateExp = new
		 * SimpleDateFormat("dd/MM/yyyy").format(getIdExpDate()); String[]
		 * dateStr = dateExp.split("/");
		 * setExpDateS(dateStr[2]+dateStr[1]+dateStr[0]);
		 * 
		 * 
		 * }else{
		 * RequestContext.getCurrentInstance().execute("expDatemand.show();"); }
		 */
		setEnableScan(true);
		setEnableCheckId(false);
		RequestContext.getCurrentInstance().execute("scanwait.show();");
	}

	public void checkId_localTest() throws IOException, ParseException {
		// setEnableScan(false);
		// StringBuffer urlBuffer = new StringBuffer();
		// String equals = "=";
		boolean duplicate = duplicateCheck();
		if (!duplicate) {
			try {
				String str = "IdNo=102266,ExprireDate=20171231,BirthDate=20150501";
				// String str="DMS_Error";
				// http://192.168.100.195/IDAutomation/frmmain.aspx?ClientRefNO=304121301179&IdNo=304121301179&BirthDate=20150407&ExpireDate=20150407&IdType=NewID&Creator=EXCHDBA&CreationDate=20150407&CreatedApp=EMOS
				System.out.println("IIIIIIIIIII== " + str);
				if (str.startsWith("DMS_Error")) {
					setEnableScan(false);
					RequestContext.getCurrentInstance().execute("scanRecord.show();");
				} else {
					String s22 = str;
					HashMap<String, String> map = new HashMap<String, String>();
					StringTokenizer stToken = new StringTokenizer(s22, ",");
					while (stToken.hasMoreTokens()) {
						String tNmae = stToken.nextToken();
						String arr[] = tNmae.split("=");
						if (arr.length == 1) {
							map.put(arr[0], "");
						} else {
							map.put(arr[0], arr[1]);
						}
					}
					boolean valueCheck = false;
					for (String keyValue : map.keySet()) {
						String value = map.get(keyValue);
						if (value.equalsIgnoreCase("")) {
							valueCheck = true;
							break;
						}
					}
					/*
					 * java.util.Date expiryDcheck =null;
					 * if(map.get("ExpireDate")!=null &&
					 * map.get("ExpireDate").equalsIgnoreCase("")){ expiryDcheck
					 * = new
					 * SimpleDateFormat("yyyyddmm").parse(map.get("ExpireDate"
					 * )); String expiryDate1 = new
					 * SimpleDateFormat("dd/MM/yyyy").format(expiryDcheck);
					 * setDateExp(new SimpleDateFormat("dd/MM/yyyy")
					 * .parse(expiryDate1)); }
					 * 
					 * if(map.get("BirthDate")!=null &&
					 * map.get("BirthDate").equalsIgnoreCase("")){
					 * java.util.Date birthDate = new
					 * SimpleDateFormat("yyyyddmm").parse(map.get("BirthDate"));
					 * }
					 * 
					 * if(map.get("IdNo")!=null &&
					 * map.get("IdNo").equalsIgnoreCase("")){
					 * setIdNumberScan(map.get("IdNo")); }
					 */
					if (valueCheck) {
						setEnableScan(false);
						RequestContext.getCurrentInstance().execute("scanRecord.show();");
					} else {
						String[] str11 = s22.split("=");
						String str22 = str11.toString();
						String[] str12 = str11[1].split(",");
						String[] str13 = str11[2].split(",");
						String[] str14 = str11[3].split(",");
						/*
						 * SimpleDateFormat sdf = new
						 * SimpleDateFormat("yyyyMMdd"); sdf.format(date)
						 */
						java.util.Date expiryDcheck = new SimpleDateFormat("yyyyMMdd").parse(str13[0].toString());
						java.util.Date birthDate = new SimpleDateFormat("yyyyMMdd").parse(str14[0].toString());
						String expiryDate1 = new SimpleDateFormat("dd/MM/yyyy").format(expiryDcheck);
						setIdNumberScan(str12[0]);
						setIdExpDate(new SimpleDateFormat("dd/MM/yyyy").parse(expiryDate1));
						SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
						getFetchCompanyIdentityTypeList();
						String df = dateformat.format(getIdExpDate());
						if (expiryDcheck.compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {
							duplicate = duplicateCheck();
							if (!duplicate) {
								AddCoIdentityDetailBean companyIdentity = new AddCoIdentityDetailBean();
								// companyIdentity.setCustomerIdProofId();
								companyIdentity.setIdTypeId(this.idtype);
								companyIdentity.setIdtype(mapcomIdentityType.get(getIdtype()));
								companyIdentity.setImageId(null);
								if (getIdtype().toString().equalsIgnoreCase(getGeneralService().getComponentId(Constants.COMPANY_REGISTRATION_DOC, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString())) {
									companyIdentity.setIdno(getIdno());
									companyIdentity.setIdExpDate(new SimpleDateFormat("dd/MM/yyyy").format(getIdExpDate()));
								}
								/*
								 * if(getIdtype().intValue() ==
								 * Constants.COMPANY_REGISTRATION){
								 * companyIdentity.setIdno(getIdno());
								 * companyIdentity.setIdExpDate(new
								 * SimpleDateFormat
								 * ("dd/MM/yyyy").format(getIdExpDate())); }
								 */
								identityList.add(companyIdentity);
								if (identityList.size() != 0) {
									setEnabledddCompanyDataTable(true);
								}
								clearProofInfo();
								setEnableScan(true);
							} else {
								RequestContext context = RequestContext.getCurrentInstance();
								context.execute("PF('duplicateDetails').show();");
							}
						} else {
							setEnableScan(true);
						}
					}
				}
			} catch (NullPointerException npe) {
				log.info("Connection Problem: " + npe);
			}
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('duplicateDetails').show();");
		}
	}

	/*private boolean duplicateCheck() {
		boolean duplicate = false;
		for (AddCoIdentityDetailBean addIdentity : identityList) {
			if (addIdentity.getIdno() != null && getIdExpDate() != null) {
				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
				if (addIdentity.getIdno().equalsIgnoreCase(getIdno()) && addIdentity.getIdTypeId().intValue() == getIdtype().intValue() && addIdentity.getIdExpDate().equalsIgnoreCase(dateformat.format(getIdExpDate()))) {
					duplicate = true;
					break;
				} else {
					log.info("######" + addIdentity.getIdExpDate());
					ArrayList<String> dateList = new ArrayList<String>();
					long now = System.currentTimeMillis();
					java.util.Date date = new java.util.Date(now);
					log.info("System Date" + date);
					System.out.println(addIdentity.getIdExpDate());
					for (String retval : addIdentity.getIdExpDate().split("/")) {
						log.info(retval);
						dateList.add(retval);
					}
					Calendar cal = Calendar.getInstance();
					cal.set(Integer.parseInt(dateList.get(2)), Integer.parseInt(dateList.get(1)) - 1, Integer.parseInt(dateList.get(0)));
					Date d = cal.getTime();
					log.info("Converted Date" + d);
					int compareDate = date.compareTo(d);
					log.info("Compare Result" + compareDate);
					if (compareDate <= 0 && addIdentity.getIdtype().equals(Constants.COMPANY_REGISTRATION_DOC)) {
					
					if (compareDate <= 0 && getIdtype().intValue()== Integer.parseInt(Constants.COMPANY_REGISTRATION_DOC)) {
						duplicate = true;
						break;
					}
				}
			} else {
				if (addIdentity.getIdTypeId().intValue() == getIdtype().intValue()) {
					duplicate = true;
					break;
				}
			}
		}
		return duplicate;
	}*/

	private boolean duplicateCheck() {
		boolean duplicate = false;
		for (AddCoIdentityDetailBean addIdentity : identityList) {
			if (addIdentity.getIdno() != null && getIdExpDate() != null) {
				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
				if (getIdExpDate() != null && addIdentity.getIdExpDate() != null && addIdentity.getIdno().equalsIgnoreCase(getIdno()) && addIdentity.getIdTypeId().intValue() == getIdtype().intValue() && addIdentity.getIdExpDate().equalsIgnoreCase(dateformat.format(getIdExpDate()))) {
					duplicate = true;
					break;
				}
				if(addIdentity.getIdno().equalsIgnoreCase(getIdno()) && addIdentity.getIdTypeId().intValue() == getIdtype().intValue()) {
					log.info("######" + addIdentity.getIdExpDate());
					ArrayList<String> dateList = new ArrayList<String>();
					long now = System.currentTimeMillis();
					java.util.Date date = new java.util.Date(now);
					log.info("System Date" + date);
					System.out.println(addIdentity.getIdExpDate());
					if(addIdentity.getIdExpDate() != null){
						for (String retval : addIdentity.getIdExpDate().split("/")) {
							log.info(retval);
							dateList.add(retval);
						}
						
						Calendar cal = Calendar.getInstance();
						cal.set(Integer.parseInt(dateList.get(2)), Integer.parseInt(dateList.get(1)) - 1, Integer.parseInt(dateList.get(0)));
						Date d = cal.getTime();
						log.info("Converted Date" + d);
						int compareDate = date.compareTo(d);
						log.info("Compare Result" + compareDate);
						if (compareDate <= 0) {
							duplicate = true;
							break;
						}
					}
				}
			} else {
				if (addIdentity.getIdTypeId().intValue() == getIdtype().intValue()) {
					duplicate = true;
					break;
				}
			}
		}
		return duplicate;
	}
	
	
	
	public void redirectToScanningBrowser() {
		setEnableScan(true);
		StringBuffer urlBuffer = new StringBuffer();
		String ampersand = "&";
		String equals = "=";
		String sysDate = new SimpleDateFormat("dd/MM/yyyy").format(getCurrentTime());
		String[] dateStr = sysDate.split("/");
		String curDate = (dateStr[2] + dateStr[1] + dateStr[0]);
		// New URL to be constructed
		// http://192.168.100.195/IDAutomation/frmmain.aspx?ClientRefNO=304121301179&IdNo=304121301179&BirthDate=20150407&ExpireDate=20150407
		// &IdType=NewID&Creator=EXCHDBA&CreationDate=20150407&CreatedApp=EMOS
		getFetchCompanyIdentityTypeList();
		String strIdType = mapcomIdentityType.get(getIdtype());
		String idType = null;
		/*
		 * if
		 * (getSelectType().equalsIgnoreCase(getGeneralService().getComponentId
		 * (METHODTYPE, sessionStateManage.getLanguageId())
		 * .getFsBizComponentData().getComponentDataId() .toPlainString())) {
		 * 
		 * if(strIdType.equalsIgnoreCase("CIVIL ID")) { idType = "IdType=OldID";
		 * }else if(strIdType.equalsIgnoreCase("LICENSE NO")) { idType =
		 * "IdType=NewDrivingLicense"; }else
		 * if(strIdType.equalsIgnoreCase("PASSPORT")) { idType =
		 * "IdType=Passport"; }else if(strIdType.equalsIgnoreCase("BEDOUIN")) {
		 * idType = "IdType=Bedoun"; }else { idType = "IdType=Other"; }
		 * 
		 * }else {
		 * 
		 * if(strIdType.equalsIgnoreCase("CIVIL ID")) { idType = "IdType=NewID";
		 * }else if(strIdType.equalsIgnoreCase("LICENSE NO")) { idType =
		 * "IdType=NewDrivingLicense"; }else
		 * if(strIdType.equalsIgnoreCase("PASSPORT")) { idType =
		 * "IdType=Passport"; }else if(strIdType.equalsIgnoreCase("BEDOUIN")) {
		 * idType = "IdType=Bedoun"; }else { idType = "IdType=Other"; } }
		 */
		if (strIdType.equalsIgnoreCase("CIVIL ID")) {
			idType = "IdType=NewID";
		} else if (strIdType.equalsIgnoreCase("LICENSE NO")) {
			idType = "IdType=NewDrivingLicense";
		} else if (strIdType.equalsIgnoreCase("PASSPORT")) {
			idType = "IdType=Other";
		} else if (strIdType.equalsIgnoreCase("BEDOUIN")) {
			idType = "IdType=Bedoun";
		} else {
			idType = "IdType=Other";
		}
		String idNo = "IdNo";
		String birthDate = "BirthDate";
		String expiryDate = "ExpireDate";
		// String idType = "IdType=NewID";
		String creator = "Creator";
		String creationDate = "CreationDate";
		String createdApp = "CreatedApp=EMOS";
		String rootContext = "http://192.168.100.195/IDAutomation/frmmain.aspx?ClientRefNO";
		urlBuffer.append(rootContext).append(equals).append(getCrno()).append(ampersand).append(idNo).append(equals).append(getCrno()).append(ampersand).append(expiryDate).append(equals).append(getExpDateS()).append(ampersand).append(idType) // For
																																																													// ID
																																																													// Type
				.append(ampersand).append(creator).append(equals).append(userName) // For
																					// Creator
				.append(ampersand).append(creationDate).append(equals).append(curDate) // For
																						// Creation
																						// Date
				.append(ampersand).append(createdApp); // For Created By
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect(urlBuffer.toString());
		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
		}
	}

	private void getFetchContactTypeList() {
		mapContactTypeList = ruleEngine.getComponentData("Identity Type");
		// .idForMap = ruleEngine.getComponentData("ID For");
		idTypeMap = ruleEngine.getComponentData("Company CR Number");
	}

	private void clearProofInfo() {
		setIdtype(null);
		setIdExpDate(null);
	}

	public void partnerCheckId() throws IOException, ParseException {
		boolean duplicate = duplicateCheckForPartner();
		if (!duplicate) {
			// setEnableScan(false);
			StringBuffer urlBuffer = new StringBuffer();
			String equals = "=";
			String rootContext = "http://192.168.100.195/IDAutomation/frmcheckID.ashx?ClientRefNO";
			urlBuffer.append(rootContext).append(equals).append(getPidno());
			try {
				URL knetRequest = new URL(urlBuffer.toString());
				HttpURLConnection httConn = null;
				BufferedReader in = null;
				httConn = (HttpURLConnection) knetRequest.openConnection();
				in = new BufferedReader(new InputStreamReader(httConn.getInputStream()));
				String str = in.readLine();
				System.out.println("IIIIIIIIIII== " + str);
				if (str.startsWith("DMS_Error")) {
					in.close();
					setEnablePartnerScan(false);
					RequestContext.getCurrentInstance().execute("scanRecord.show();");
				} else {
					String s22 = str;
					HashMap<String, String> map = new HashMap<String, String>();
					StringTokenizer stToken = new StringTokenizer(s22, ",");
					while (stToken.hasMoreTokens()) {
						String tNmae = stToken.nextToken();
						String arr[] = tNmae.split("=");
						if (arr.length == 1) {
							map.put(arr[0], "");
						} else {
							map.put(arr[0], arr[1]);
						}
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
						setEnablePartnerScan(false);
						RequestContext.getCurrentInstance().execute("scanRecord.show();");
					} else {
						String[] str11 = s22.split("=");
						String str22 = str11.toString();
						String[] str12 = str11[1].split(",");
						String[] str13 = str11[2].split(",");
						String[] str14 = str11[3].split(",");
						java.util.Date expiryDcheck = new SimpleDateFormat("yyyyMMdd").parse(str13[0].toString());
						java.util.Date birthDate = new SimpleDateFormat("yyyyMMdd").parse(str14[0].toString());
						String expiryDate1 = new SimpleDateFormat("dd/MM/yyyy").format(expiryDcheck);
						setIdNumberScan(str12[0]);
						setPartnerCustExpDate(new SimpleDateFormat("dd/MM/yyyy").parse(expiryDate1));
						SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
						getFetchCompanyIdentityTypeList();
						String df = dateformat.format(getPartnerCustExpDate());
						if (expiryDcheck.compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {
							duplicate = duplicateCheckForPartner();
							if (!duplicate) {
								AddPartnerDetailBean partner = new AddPartnerDetailBean();
								// getFetchPartnerIdentityTypeList();
								partner.setPidno(getPidno());
								partner.setPartName(getPartName());
								partner.setPartNameLocal(getPartNameLocal());
								partner.setImageId(getPartnerImageId());
								partner.setPartnerCustomerId(getPartnerCustomerId());
								partnerList.add(partner);
								setPartName("");
								setPidno("");
								setPartNameLocal("");
								setPartnerContactNumber("");
								setPartnerEmail("");
								if (partnerList.size() != 0) {
									setEnabledddPartnerAuthDataTable(true);
								}
								setEnablePartnerScan(false);
							} else {
								RequestContext context = RequestContext.getCurrentInstance();
								context.execute("PF('duplicateDetails').show();");
							}
						} else {
							setEnablePartnerScan(true);
						}
					}
				}
				in.close();
			} catch (MalformedURLException e) {
				log.info("Connection Problem: " + e);
			} catch (IOException ie) {
				log.info("Connection Problem: " + ie);
			}
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('duplicateDetails').show();");
		}
	}

	private boolean duplicateCheckForPartner() {
		boolean duplicate = false;
		for (AddPartnerDetailBean addPartnerDetailBean : partnerList) {
			if (addPartnerDetailBean.getPidno().equals(getPidno())) {
				duplicate = true;
				break;
			}
		}
		return duplicate;
	}

	public void partnerDateSelect(SelectEvent event) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			setPartnerCustExpDate(format.parse(format.format(event.getObject())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void partnerCheckId_localTest() throws IOException, ParseException {
		// setEnableScan(false);
		// StringBuffer urlBuffer = new StringBuffer();
		// String equals = "=";
		boolean duplicate = duplicateCheckForPartner();
		if (!duplicate) {
			try {
				// String str =
				// "IdNo=111,ExprireDate=20150411,BirthDate=20150501";
				String str = "DMS_Error";
				// http://192.168.100.195/IDAutomation/frmmain.aspx?ClientRefNO=304121301179&IdNo=304121301179&BirthDate=20150407&ExpireDate=20150407&IdType=NewID&Creator=EXCHDBA&CreationDate=20150407&CreatedApp=EMOS
				System.out.println("IIIIIIIIIII== " + str);
				if (str.startsWith("DMS_Error")) {
					setEnablePartnerScan(false);
					RequestContext.getCurrentInstance().execute("scanRecord.show();");
				} else {
					String s22 = str;
					HashMap<String, String> map = new HashMap<String, String>();
					StringTokenizer stToken = new StringTokenizer(s22, ",");
					while (stToken.hasMoreTokens()) {
						String tNmae = stToken.nextToken();
						String arr[] = tNmae.split("=");
						if (arr.length == 1) {
							map.put(arr[0], "");
						} else {
							map.put(arr[0], arr[1]);
						}
					}
					boolean valueCheck = false;
					for (String keyValue : map.keySet()) {
						String value = map.get(keyValue);
						if (value.equalsIgnoreCase("")) {
							valueCheck = true;
							break;
						}
					}
					/*
					 * java.util.Date expiryDcheck =null;
					 * if(map.get("ExpireDate")!=null &&
					 * map.get("ExpireDate").equalsIgnoreCase("")){ expiryDcheck
					 * = new
					 * SimpleDateFormat("yyyyddmm").parse(map.get("ExpireDate"
					 * )); String expiryDate1 = new
					 * SimpleDateFormat("dd/MM/yyyy").format(expiryDcheck);
					 * setDateExp(new SimpleDateFormat("dd/MM/yyyy")
					 * .parse(expiryDate1)); }
					 * 
					 * if(map.get("BirthDate")!=null &&
					 * map.get("BirthDate").equalsIgnoreCase("")){
					 * java.util.Date birthDate = new
					 * SimpleDateFormat("yyyyddmm").parse(map.get("BirthDate"));
					 * }
					 * 
					 * if(map.get("IdNo")!=null &&
					 * map.get("IdNo").equalsIgnoreCase("")){
					 * setIdNumberScan(map.get("IdNo")); }
					 */
					if (valueCheck) {
						setEnablePartnerScan(false);
						RequestContext.getCurrentInstance().execute("scanRecord.show();");
					} else {
						String[] str11 = s22.split("=");
						String str22 = str11.toString();
						String[] str12 = str11[1].split(",");
						String[] str13 = str11[2].split(",");
						String[] str14 = str11[3].split(",");
						/*
						 * SimpleDateFormat sdf = new
						 * SimpleDateFormat("yyyyMMdd"); sdf.format(date)
						 */
						java.util.Date expiryDcheck = new SimpleDateFormat("yyyyMMdd").parse(str13[0].toString());
						java.util.Date birthDate = new SimpleDateFormat("yyyyMMdd").parse(str14[0].toString());
						String expiryDate1 = new SimpleDateFormat("dd/MM/yyyy").format(expiryDcheck);
						setIdNumberScan(str12[0]);
						setPartnerCustExpDate(new SimpleDateFormat("dd/MM/yyyy").parse(expiryDate1));
						SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
						getFetchCompanyIdentityTypeList();
						String df = dateformat.format(getPartnerCustExpDate());
						if (expiryDcheck.compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {
							duplicate = duplicateCheckForPartner();
							if (!duplicate) {
								AddPartnerDetailBean partner = new AddPartnerDetailBean();
								// getFetchPartnerIdentityTypeList();
								partner.setPidno(getPidno());
								partner.setPartName(getPartName());
								partner.setPartNameLocal(getPartNameLocal());
								partner.setImageId(getPartnerImageId());
								partner.setPartnerCustomerId(getPartnerCustomerId());
								partner.setPartnerContactNumber(getPartnerContactNumber());
								partner.setPartnerEmail(getPartnerEmail());
								partnerList.add(partner);
								setPartName("");
								setPidno("");
								setPartNameLocal("");
								setPartnerContactNumber("");
								setPartnerEmail("");
								if (partnerList.size() != 0) {
									setEnabledddPartnerAuthDataTable(true);
								}
								setEnablePartnerScan(true);
							} else {
								RequestContext context = RequestContext.getCurrentInstance();
								context.execute("PF('duplicateDetails').show();");
							}
						} else {
							setEnableScan(true);
						}
					}
				}
			} catch (NullPointerException npe) {
				log.info("Connection Problem: " + npe);
			}
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('duplicateDetails').show();");
		}
	}

	public void scanForPartner() throws IOException, ParseException {
		/*
		 * if(getDob()!=null){
		 * 
		 * String dobstr = new SimpleDateFormat("dd/MM/yyyy").format(getDob());
		 * String[] dateStr = dobstr.split("/");
		 * setDobS(dateStr[2]+dateStr[1]+dateStr[0]);
		 * 
		 * }
		 * 
		 * if(getBirthdate()!=null){
		 * 
		 * String[] dateStr = getBirthdate().split("/");
		 * 
		 * setDobS(dateStr[2]+dateStr[1]+dateStr[0]); }
		 * 
		 * if(getShowDob()!=null){
		 * 
		 * String[] dateStr = getShowDob().split("/");
		 * 
		 * setDobS(dateStr[2]+dateStr[1]+dateStr[0]);
		 * 
		 * }
		 */
		setDobS(null);
		if (getPartnerCustExpDate() != null) {
			String dateExp = new SimpleDateFormat("dd/MM/yyyy").format(getPartnerCustExpDate());
			String[] dateStr = dateExp.split("/");
			setExpDateS(dateStr[2] + dateStr[1] + dateStr[0]);
		} else {
			RequestContext.getCurrentInstance().execute("expDatemand.show();");
		}
		setEnablePartnerScan(true);
		RequestContext.getCurrentInstance().execute("scanwaitForPartner.show();");
	}

	public void redirectToScanningBrowserForPartner() {
		setEnableScan(true);
		StringBuffer urlBuffer = new StringBuffer();
		String ampersand = "&";
		String equals = "=";
		String sysDate = new SimpleDateFormat("dd/MM/yyyy").format(getCurrentTime());
		String[] dateStr = sysDate.split("/");
		String curDate = (dateStr[2] + dateStr[1] + dateStr[0]);
		// New URL to be constructed
		// http://192.168.100.195/IDAutomation/frmmain.aspx?ClientRefNO=304121301179&IdNo=304121301179&BirthDate=20150407&ExpireDate=20150407
		// &IdType=NewID&Creator=EXCHDBA&CreationDate=20150407&CreatedApp=EMOS
		String strIdType = idTypeMap.get(getPidno());
		String idType = null;
		if (strIdType.equalsIgnoreCase("CIVIL ID")) {
			idType = "IdType=NewID";
		} else if (strIdType.equalsIgnoreCase("LICENSE NO")) {
			idType = "IdType=NewDrivingLicense";
			/*
			 * }else if(strIdType.equalsIgnoreCase("PASSPORT")) { idType =
			 * "IdType=Other";
			 */
		} else if (strIdType.equalsIgnoreCase("BEDOUIN")) {
			idType = "IdType=Bedoun";
		} else {
			idType = "IdType=Other";
		}
		String idNo = "IdNo";
		String birthDate = "BirthDate";
		String expiryDate = "ExpireDate";
		// String idType = "IdType=NewID";
		String creator = "Creator";
		String creationDate = "CreationDate";
		String createdApp = "CreatedApp=EMOS";
		String rootContext = "http://192.168.100.195/IDAutomation/frmmain.aspx?ClientRefNO";
		urlBuffer.append(rootContext).append(equals).append(getPidno()).append(ampersand).append(idNo).append(equals).append(getPidno()).append(ampersand).append(expiryDate).append(equals).append(getExpDateS()).append(ampersand).append(idType) // For
																																																													// ID
																																																													// Type
				.append(ampersand).append(creator).append(equals).append(userName) // For
																					// Creator
				.append(ampersand).append(creationDate).append(equals).append(curDate) // For
																						// Creation
																						// Date
				.append(ampersand).append(createdApp); // For Created By
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect(urlBuffer.toString());
		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
		}
	}

	public Boolean renderModifyScan = false;

	public Boolean getRenderModifyScan() {
		return renderModifyScan;
	}

	public void setRenderModifyScan(Boolean renderModifyScan) {
		this.renderModifyScan = renderModifyScan;
	}


	public void checkIdNonOcr() throws IOException, ParseException {
		ReadScanProperties readScan = new ReadScanProperties();
		boolean duplicate = duplicateCheck();
		if (!duplicate) {
			// setEnableScan(false);
			StringBuffer urlBuffer = new StringBuffer();
			/*String equals = "=";
			String and = "&";
			String ampersand = "&";
			String rootContext = readScan.getPropertiesValue().get("urlchecknonocr");
			;
			String usernamevalue = readScan.getPropertiesValue().get("usernamevalue");
			String passwordvalue = readScan.getPropertiesValue().get("passwordvalue");
			String username = readScan.getPropertiesValue().get("username");
			String password = readScan.getPropertiesValue().get("password");
			String arcmateField_DocID = readScan.getPropertiesValue().get("ArcmateField_DocID");
			String indexfield = readScan.getPropertiesValue().get("indexfield");
			String commercialRegistration_ArcMateField_FileCategoryID = readScan.getPropertiesValue().get("CommercialRegistration_ArcMateField_FileCategoryID");
			String civilid20 = readScan.getPropertiesValue().get("civilid20");
			String fileCatgoryId = readScan.getPropertiesValue().get("FileCategoryID");
			String arcMateField_FileCategoryID = readScan.getPropertiesValue().get("ArcMateField_FileCategoryID");
			String urlfileroot = readScan.getPropertiesValue().get("urlfileid");
			// CheckDocumentExistsScript Action
			urlBuffer.append(rootContext).append(ampersand).append(username).append(equals).append(usernamevalue).append(ampersand).append(password).append(equals).append(passwordvalue).append(ampersand).append(civilid20).append(equals).append(getCrno());*/
			
			 List<ArcmateScanMaster> arcmateList = icustomerRegistrationService.fetchArcmateMasterData(Constants.CHECK_DOCUMENT, Constants.NON_OCR);
		     List<ScanIdTypeMaster>  scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(getIdtype());
		    
		        if(arcmateList.size() != 0 && scanIdList.size() != 0){
		        	ArcmateScanMaster arcmateValue = arcmateList.get(0);
		        	ScanIdTypeMaster scanIdValue = scanIdList.get(0);
				String rootContext = "http://";
				//String idNo = getIdnumberProof();
				urlBuffer.append(rootContext).append(arcmateValue.getIpAddress().trim()).append("/").append(arcmateValue.getContextPath().trim());
				if(arcmateValue.getUrlParamField1()!=null){
					urlBuffer.append(arcmateValue.getUrlParamField1().trim());
				}
				
				if(arcmateValue.getUrlParamField2()!=null){
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField2().trim());
				}
				
				if(arcmateValue.getUrlParamField3()!=null && scanIdValue.getDocumentId()!=null){
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField3().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getDocumentId());
				}
				if(arcmateValue.getUrlParamField4()!=null && scanIdValue.getUserName()!=null){
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField4().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getUserName());
				}
				if(arcmateValue.getUrlParamField5()!=null && scanIdValue.getPassword()!=null){
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField5().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getPassword());
				}
				
				if(arcmateValue.getUrlParamField6()!=null){
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField6().trim()).append(arcmateValue.getFieldAssigner().trim()).append(getCrno());
				}
			
			try {
				URL knetRequest = new URL(urlBuffer.toString());
				log.info("URL for Document Exists: "+urlBuffer.toString());
				HttpURLConnection httConn = null;
				BufferedReader in = null;
				httConn = (HttpURLConnection) knetRequest.openConnection();
				in = new BufferedReader(new InputStreamReader(httConn.getInputStream()));
				String str = in.readLine();
				if (str.equalsIgnoreCase("0")) {
					in.close();
					/*
					 * RequestContext context =
					 * RequestContext.getCurrentInstance();
					 * context.execute("PF('adddocument').show();");
					 */
					// setEnableScan(false);
					// setEnableCheckId(true);
					// RequestContext.getCurrentInstance().execute("scanRecord.show();");
					// AddDocument
					RequestContext.getCurrentInstance().execute("scanwait.show();");
				} else if (str.startsWith("Doc ID")) {
					// CheckFileExistsScript
					String[] s1 = str.split("=");
					in.close();
					StringBuffer urlBuffer1 = new StringBuffer();
					
					List<ArcmateScanMaster> arcmateListFile = icustomerRegistrationService.fetchArcmateMasterData(Constants.CHECK_FILE, Constants.NON_OCR);
				    
				    
				        if(arcmateListFile.size() != 0 && scanIdList.size() != 0){
				        	ArcmateScanMaster arcmate = arcmateListFile.get(0);
				        	ScanIdTypeMaster scanId = scanIdList.get(0);
						urlBuffer1.append(rootContext).append(arcmate.getIpAddress().trim()).append("/").append(arcmate.getContextPath().trim());
						if(arcmate.getUrlParamField1()!=null){
							urlBuffer1.append(arcmate.getUrlParamField1().trim());
						}
						
						if(arcmate.getUrlParamField2()!=null){
							urlBuffer1.append(arcmate.getFieldSeprator().trim()).append(arcmate.getUrlParamField2().trim());
						}
						
						if(arcmate.getUrlParamField3()!=null && scanId.getDocumentId()!=null){
							urlBuffer1.append(arcmate.getFieldSeprator().trim()).append(arcmate.getUrlParamField3().trim()).append(arcmate.getFieldAssigner().trim()).append(scanId.getDocumentId());
						}
						if(arcmate.getUrlParamField4()!=null && scanId.getUserName()!=null){
							urlBuffer1.append(arcmate.getFieldSeprator().trim()).append(arcmate.getUrlParamField4().trim()).append(arcmate.getFieldAssigner().trim()).append(scanId.getUserName());
						}
						if(arcmate.getUrlParamField5()!=null && scanId.getPassword()!=null){
							urlBuffer1.append(arcmate.getFieldSeprator().trim()).append(arcmate.getUrlParamField5().trim()).append(arcmate.getFieldAssigner().trim()).append(scanId.getPassword());
						}
					/*if (getIdTypeproof().intValue() == generalService.getComponentId(Constants.PASSPORT, sessionStateManage.getLanguageId())
							.getFsBizComponentData().getComponentDataId().intValue()) {*/
						if(arcmate.getUrlParamField6()!=null && scanId.getFileCategoryId()!=null){
							urlBuffer1.append(arcmate.getFieldSeprator().trim()).append(arcmate.getUrlParamField6().trim()).append(arcmate.getFieldAssigner().trim()).append(scanId.getFileCategoryId());
						}
						if(arcmate.getUrlParamField7()!=null){
							urlBuffer1.append(arcmate.getFieldSeprator().trim()).append(arcmate.getUrlParamField7().trim()).append(arcmate.getFieldAssigner().trim()).append(s1[1]);
						}
					}
					
					try {
						URL request = new URL(urlBuffer1.toString());
						log.info("URL for File Exist Exists: "+urlBuffer1.toString());
						HttpURLConnection httConn1 = null;
						BufferedReader in1 = null;
						httConn1 = (HttpURLConnection) request.openConnection();
						in1 = new BufferedReader(new InputStreamReader(httConn1.getInputStream()));
						String str1 = in1.readLine();
						if (str1.startsWith("File ID")) {
							//setRenderModifyScan(true);
							in1.close();
							SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
							getFetchCompanyIdentityTypeList();
							String df = dateformat.format(getIdExpDate());
							if (getIdExpDate().compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {
								duplicate = duplicateCheck();
								if (!duplicate) {
									AddCoIdentityDetailBean companyIdentity = new AddCoIdentityDetailBean();
									companyIdentity.setIdTypeId(this.idtype);
									companyIdentity.setIdtype(mapcomIdentityType.get(getIdtype()));
									companyIdentity.setImageId(null);
									if (getIdtype().toString().equalsIgnoreCase(getGeneralService().getComponentId(Constants.COMPANY_REGISTRATION_DOC, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString())) {
										companyIdentity.setIdno(getIdno());
										companyIdentity.setIdExpDate(new SimpleDateFormat("dd/MM/yyyy").format(getIdExpDate()));
									}
									companyIdentity.setRenderReScan(true);
									identityList.add(companyIdentity);
									clearProofInfo();
									setEnabledddCompanyDataTable(true);
									setEnableScan(true);
								} else {
									RequestContext context = RequestContext.getCurrentInstance();
									context.execute("PF('duplicateDetails').show();");
								}
							} else {
								setEnableScan(true);
								setEnableCheckId(true);
								setRenderModifyScan(true);
								RequestContext.getCurrentInstance().execute("idExpired.show();");
							}
						} else {
							setEnableScan(true);
							setEnableCheckId(true);
							setRenderModifyScan(true);
							RequestContext.getCurrentInstance().execute("filenotfounds.show();");
						}
					} catch (MalformedURLException e) {
						log.info("Connection Problem: " + e);
					}
				}
			} catch (IOException ie) {
				log.info("Connection Problem: " + ie);
			}
		        }else{
					RequestContext.getCurrentInstance().execute("arcmateTable.show();");
				}
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('duplicateDetails').show();");
		}
		        
		
	}

	//@SuppressWarnings("static-access")
	public void addDocumentBrowser() {
		//ReadScanProperties readScan = new ReadScanProperties();
		StringBuffer urlBuffer = new StringBuffer();
		/* String equals = "=";
		String and = "&";
		String ampersand = "&";
		String rootContext = readScan.getPropertiesValue().get("urlchecknonocr");
		String rootContextadd = readScan.getPropertiesValue().get("urladddoc");
		String idtype = readScan.getPropertiesValue().get("idtype");
		String newid = readScan.getPropertiesValue().get("newid");
		String usernamevalue = readScan.getPropertiesValue().get("usernamevalue");
		String passwordvalue = readScan.getPropertiesValue().get("passwordvalue");
		String username = readScan.getPropertiesValue().get("username");
		String password = readScan.getPropertiesValue().get("password");
		String arcmateField_DocID = readScan.getPropertiesValue().get("ArcmateField_DocID");
		String indexfield = readScan.getPropertiesValue().get("indexfield");
		String commercialRegistration_ArcMateField_FileCategoryID = readScan.getPropertiesValue().get("CommercialRegistration_ArcMateField_FileCategoryID");
		String civilid20 = readScan.getPropertiesValue().get("civilid20");
		String passport = readScan.getPropertiesValue().get("passport");
		String gccnationalid = readScan.getPropertiesValue().get("gccnationalid");
		String fileCatgoryId = readScan.getPropertiesValue().get("FileCategoryID");
		String arcMateField_FileCategoryID = readScan.getPropertiesValue().get("ArcMateField_FileCategoryID");
		urlBuffer.append(rootContextadd).append(ampersand).append(civilid20).append(equals).append(getCrno()).append(ampersand).append(fileCatgoryId).append(equals).append(commercialRegistration_ArcMateField_FileCategoryID);
		System.out.println("Add Document of Non OCR URL :  " + urlBuffer.toString());
		log.info("Add Document of Non OCR URL :  " + urlBuffer.toString());
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect(urlBuffer.toString());
		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
		}*/
		
		List<ArcmateScanMaster> arcmateList = icustomerRegistrationService.fetchArcmateMasterData(Constants.SCAN, Constants.NON_OCR);
		List<ScanIdTypeMaster> scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(getIdtype());
		

		if(arcmateList.size() != 0 && scanIdList.size() != 0){
			ArcmateScanMaster arcmateValue = arcmateList.get(0);
			ScanIdTypeMaster scanIdValue = scanIdList.get(0);
			String rootContext = "http://";
			urlBuffer.append(rootContext).append(arcmateValue.getIpAddress().trim()).append("/").append(arcmateValue.getContextPath().trim());
			if(arcmateValue.getUrlParamField1()!=null){
				urlBuffer.append(arcmateValue.getUrlParamField1().trim());
			}
			if(arcmateValue.getUrlParamField2()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField2().trim());
			}
			if(arcmateValue.getUrlParamField3()!=null && scanIdValue.getDocumentId()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField3().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getDocumentId());
			}
			
			if(arcmateValue.getUrlParamField4()!=null && scanIdValue.getFolderId()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField4().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFolderId());
			}
			
			if(arcmateValue.getUrlParamField5()!=null && scanIdValue.getUserName()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField5().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getUserName());
			}
			if(arcmateValue.getUrlParamField6()!=null && scanIdValue.getPassword()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField6().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getPassword());
			}

			if(arcmateValue.getUrlParamField7()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField7().trim()).append(arcmateValue.getFieldAssigner().trim()).append(getCrno());
			}
			
			if(arcmateValue.getUrlParamField8()!=null && scanIdValue.getFileCategoryId()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFileCategoryId());
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
	}

	public void modifyScan() {
		setEnableCheckId(false);
		setEnableScan(true);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('modifyscan').show();");
	}

	public void modifyNonOcr() {
		ReadScanProperties readScan = new ReadScanProperties();
		StringBuffer urlBuffer = new StringBuffer();
		/*String equals = "=";
		String and = "&";
		String ampersand = "&";
		String rootContext = readScan.getPropertiesValue().get("urlmodifydoc");
		String rootContextadd = readScan.getPropertiesValue().get("urladddoc");
		String idtype = readScan.getPropertiesValue().get("idtype");
		String newid = readScan.getPropertiesValue().get("newid");
		String usernamevalue = readScan.getPropertiesValue().get("usernamevalue");
		String passwordvalue = readScan.getPropertiesValue().get("passwordvalue");
		String username = readScan.getPropertiesValue().get("username");
		String password = readScan.getPropertiesValue().get("password");
		String arcmateField_DocID = readScan.getPropertiesValue().get("ArcmateField_DocID");
		String indexfield = readScan.getPropertiesValue().get("indexfield");
		String commercialRegistration_ArcMateField_FileCategoryID = readScan.getPropertiesValue().get("CommercialRegistration_ArcMateField_FileCategoryID");
		String civilid20 = readScan.getPropertiesValue().get("civilid20");
		String passport = readScan.getPropertiesValue().get("passport");
		String gccnationalid = readScan.getPropertiesValue().get("gccnationalid");
		String fileCatgoryId = readScan.getPropertiesValue().get("FileCategoryID");
		String arcMateField_FileCategoryID = readScan.getPropertiesValue().get("ArcMateField_FileCategoryID");*/
		/*
		 * for (AddCoIdentityDetailBean addIdentity : identityList) {
		 * if(addIdentity.getIdno()!=null){
		 * urlBuffer.append(rootContext).append(
		 * ampersand).append(civilid20).append
		 * (equals).append(addIdentity.getIdno())
		 * .append(ampersand).append(fileCatgoryId).append(equals).append(
		 * commercialRegistration_ArcMateField_FileCategoryID); break; } }
		 */
		/*urlBuffer.append(rootContext).append(ampersand).append(username).append(equals).append(usernamevalue).append(ampersand).append(password)
		.append(equals).append(passwordvalue).append(ampersand).append(civilid20).append(equals).append(getIdno()).append(ampersand).append(fileCatgoryId).append(equals).append(commercialRegistration_ArcMateField_FileCategoryID);*/
		
		List<ArcmateScanMaster> arcmateList = icustomerRegistrationService.fetchArcmateMasterData(Constants.MODIFY, Constants.NON_OCR);
		List<ScanIdTypeMaster> scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(getIdtype());
		

		if(arcmateList.size() != 0 && scanIdList.size() != 0){
			ArcmateScanMaster arcmateValue = arcmateList.get(0);
			ScanIdTypeMaster scanIdValue = scanIdList.get(0);
			String rootContext = "http://";
			urlBuffer.append(rootContext).append(arcmateValue.getIpAddress().trim()).append("/").append(arcmateValue.getContextPath().trim());
			if(arcmateValue.getUrlParamField1()!=null){
				urlBuffer.append(arcmateValue.getUrlParamField1().trim());
			}
			if(arcmateValue.getUrlParamField2()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField2().trim());
			}
			if(arcmateValue.getUrlParamField3()!=null && scanIdValue.getDocumentId()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField3().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getDocumentId());
			}
			
			if(arcmateValue.getUrlParamField4()!=null && scanIdValue.getFolderId()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField4().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFolderId());
			}
			
			if(arcmateValue.getUrlParamField5()!=null && scanIdValue.getUserName()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField5().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getUserName());
			}
			if(arcmateValue.getUrlParamField6()!=null && scanIdValue.getPassword()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField6().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getPassword());
			}
			if(arcmateValue.getUrlParamField7()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField7().trim()).append(arcmateValue.getFieldAssigner().trim()).append(getCrno());
			}
			
			if(arcmateValue.getUrlParamField8()!=null && scanIdValue.getFileCategoryId()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFileCategoryId());
			}
		
		System.out.println("Modify Document of Non OCR URL :  " + urlBuffer.toString());
		log.info("Modify Document of Non OCR URL :  " + urlBuffer.toString());
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect(urlBuffer.toString());
		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
		}
		}else{
			RequestContext.getCurrentInstance().execute("arcmateTable.show();");
		}
	}

	public void savePartner() throws ParseException {
		boolean duplicate = duplicateCheckForPartner();
		if (!duplicate) {
			SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
			getFetchCompanyIdentityTypeList();
			/*String df = dateformat.format(getPartnerCustExpDate());*/
			if (getPartnerCustExpDate()!=null && getPartnerCustExpDate().compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {
				duplicate = duplicateCheckForPartner();
				if (!duplicate) {
					AddPartnerDetailBean partner = new AddPartnerDetailBean();
					// getFetchPartnerIdentityTypeList();
					partner.setPidno(getPidno());
					partner.setPartName(getPartName());
					partner.setPartNameLocal(getPartNameLocal());
					partner.setImageId(getPartnerImageId());
					partner.setPartnerCustomerId(getPartnerCustomerId());
					partner.setPartnerContactNumber(getPartnerContactNumber());
					partner.setPartnerEmail(getPartnerEmail());
					/*if (!getBooPartnerContactNumberReadOnly()) {
						partner.setIspartnerMobileAlreadyExist(true);
					}
					if (!getBoopartnerEmailReadOnly()) {
						partner.setIspartneremailAlreadyExist(true);
					}*/
					partner.setOccupation(getOccupation());
					partnerList.add(partner);
					setEnabledddPartnerAuthDataTable(true);
					setPartName("");
					setPidno("");
					setPartNameLocal("");
					setPartnerContactNumber("");
					setPartnerEmail("");
					setBooPartnerContactNumberReadOnly(null);
					setBoopartnerEmailReadOnly(null);
					setOccupation("");
					setPartnerCustExpDate(null);
				} else {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('duplicateDetails').show();");
				}
			} else {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('partnerIdExpired').show();");
			}
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('duplicateDetails').show();");
		}
	}

	public void checkingOldDbData() {
		List<String> outprocedurevalues = new ArrayList<String>();
		setExceptionMessage(null);
		try {
			
			outprocedurevalues = corpRegService.getCustomerRefOrSave(getGeneralService().getComponentId(Constants.COMPANY_REGISTRATION_DOC, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId(), getCrno(), Constants.NonIndividual);
			if (outprocedurevalues.size() != 0) {
				fetchData();
			}
		} catch (AMGException e) {
			CollectionUtil collUtil = new CollectionUtil();
			System.out.println("Exception :"+e.getMessage());
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}
	}

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
		String returnString = generalService.validateMobileTelephone(sessionStateManage.getCountryAlphaTwoCode(), value.toString(), Constants.RESIDENCE_CONTACT);
		if (returnString.equalsIgnoreCase(Constants.Yes)) {
		} else {
			FacesMessage msg = new FacesMessage("Residence", returnString);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	
	public void viewScan(AddCoIdentityDetailBean companyIdentity) {
		//ReadScanProperties readScan = new ReadScanProperties();
		StringBuffer urlBuffer = new StringBuffer();
		/*String equals = "=";
		String ampersand = "&";
		String rootContextview = readScan.getPropertiesValue().get("urlviewbrowser");
		String civilid20 = readScan.getPropertiesValue().get("civilid20");
		String fileCatgoryId = readScan.getPropertiesValue().get("FileCategoryID");
		String usernamevalue = readScan.getPropertiesValue().get("usernamevalue");
		String passwordvalue = readScan.getPropertiesValue().get("passwordvalue");
		String username = readScan.getPropertiesValue().get("username");
		String password = readScan.getPropertiesValue().get("password");
		String commercialRegistration_ArcMateField_FileCategoryID = readScan.getPropertiesValue().get("CommercialRegistration_ArcMateField_FileCategoryID");
		urlBuffer.append(rootContextview).append(ampersand).append(username).append(equals).append(usernamevalue).append(ampersand).append(password).append(equals).append(passwordvalue).append(ampersand).append(fileCatgoryId).append(equals).append(commercialRegistration_ArcMateField_FileCategoryID)
				.append(ampersand).append(civilid20).append(equals).append(companyIdentity.getIdno());*/
		
		List<ArcmateScanMaster> arcmateList = icustomerRegistrationService.fetchArcmateMasterData(Constants.VIEW, Constants.BOTH_VIEW);
		List<ScanIdTypeMaster> scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(companyIdentity.getIdTypeId());
		

		if(arcmateList.size() != 0 && scanIdList.size() != 0){
			ArcmateScanMaster arcmateValue = arcmateList.get(0);
			ScanIdTypeMaster scanIdValue = scanIdList.get(0);
			String rootContext = "http://";
			urlBuffer.append(rootContext).append(arcmateValue.getIpAddress().trim()).append("/").append(arcmateValue.getContextPath().trim());
			if(arcmateValue.getUrlParamField1()!=null){
				urlBuffer.append(arcmateValue.getUrlParamField1().trim());
			}
			if(arcmateValue.getUrlParamField2()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField2().trim());
			}
			if(arcmateValue.getUrlParamField3()!=null && scanIdValue.getDocumentId()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField3().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getDocumentId());
			}
			
			if(arcmateValue.getUrlParamField4()!=null && scanIdValue.getFolderId()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField4().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFolderId());
			}
			
			if(arcmateValue.getUrlParamField5()!=null && scanIdValue.getUserName()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField5().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getUserName());
			}
			if(arcmateValue.getUrlParamField6()!=null && scanIdValue.getPassword()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField6().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getPassword());
			}
			
			if(arcmateValue.getUrlParamField7()!=null && scanIdValue.getFileCategoryId()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField7().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFileCategoryId());
			}

			if(arcmateValue.getUrlParamField8()!=null){
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim()).append(arcmateValue.getFieldAssigner().trim()).append(companyIdentity.getIdno());

		} 
		
		System.out.println("SCANNED VIEW URL :  " + urlBuffer.toString());
		log.info("SCANNED VIEW URL :  " + urlBuffer.toString());
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect(urlBuffer.toString());
		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
		}
		}else{
			
		}
	}

	public Boolean getRenderSuccess() {
		return renderSuccess;
	}

	public void setRenderSuccess(Boolean renderSuccess) {
		this.renderSuccess = renderSuccess;
	}

	public void saveCorpData() {
		setErrmsg(null);
		try {
			Customer cust = new Customer();
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionStateManage.getCountryId());
			cust.setFsCountryMasterByCountryId(countryMaster);
			/** save company */
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(sessionStateManage.getCompanyId());
			cust.setFsCompanyMaster(companyMaster);
			/** Objective id **/
			if (getPrimaryObjId() != null) {
				BizComponentData objectiveId = new BizComponentData();
				objectiveId.setComponentDataId(getPrimaryObjId());
				cust.setFsBizComponentDataByObjectiveId(objectiveId);
			}
			/** Customer Type */
			BizComponentData customerType = new BizComponentData();
			customerType.setComponentDataId(getGeneralService().getComponentId(Constants.CUSTOMERTYPE_CORP, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
			cust.setFsBizComponentDataByCustomerTypeId(customerType);
			BizComponentData companyGroup = new BizComponentData();
			companyGroup.setComponentDataId(getGeneralService().getComponentId(Constants.GROUPID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
			cust.setFsBizComponentDataByGroupId(companyGroup);
			LanguageType langType = new LanguageType();
			langType.setLanguageId((sessionStateManage.getLanguageId()));
			cust.setFsLanguageType(langType);
			cust.setCompanyName(getCompName());
			cust.setCompanyNameLocal(getCompNameL());
			cust.setEmail(getEmail());
			cust.setCrNo(getCrno());
			cust.setDateOfBirth(getCompRegDate());
			cust.setMobile(getContactNumber());
			cust.setPaidupCapital(getPaidUpCapital());
			// CR for Date based on application country
			// cust.setActivatedDate(new Date());
			cust.setActivatedDate(getCurrentTime());
			// cust.setActivatedInd("1");
			cust.setActivatedInd(Constants.CUST_ACTIVE_INDICATOR);
			CountryMaster countryMstr = new CountryMaster();
			countryMstr.setCountryId(sessionStateManage.getCountryId());
			cust.setFsCountryMasterByNationality(countryMstr);
			cust.setBranchCode(new BigDecimal(sessionStateManage.getBranchId()));
			cust.setIsActive(Constants.Yes);
			cust.setSmartCardIndicator(Constants.No);
			// cust.setAmlStatus(getAMLStatus());
			// cust.setNumberOfHits(1l);
			/* Setting Article ID */
			if (getLevelId() != null) {
				ArticleDetails details = new ArticleDetails();
				details.setArticleDetailId(getLevelId());
				cust.setFsArticleDetails(details);
				if(getIncomeRange()!=null) {
				IncomeRangeMaster fsIncomeRangeMaster = new IncomeRangeMaster();
				fsIncomeRangeMaster.setIncomeRangeId(getIncomeRange());
				cust.setFsIncomeRangeMaster(fsIncomeRangeMaster);
				}
				
				// To Store Limit in DB
				/*cust.setDailyLimit(getDailyLimit());
				cust.setWeeklyLimit(getWeeklyLimit());
				cust.setMontlyLimit(getMonthlyLimit());
				cust.setQuaterlyLimit(getQuarterlyLimit());
				cust.setHalfYearly(getHalfyearly());
				cust.setAnnualLimit(getAnnualLimit());*/
			}else{
				ArticleDetails details = new ArticleDetails();
				details.setArticleDetailId(new BigDecimal(16)); //For Others
				cust.setFsArticleDetails(details);
				if(getIncomeRange()!=null) {
				IncomeRangeMaster fsIncomeRangeMaster = new IncomeRangeMaster();
				fsIncomeRangeMaster.setIncomeRangeId(getIncomeRange());
				cust.setFsIncomeRangeMaster(fsIncomeRangeMaster);
				}
			}
			String titlcorp = getGeneralService().getComponentId(Constants.TITLE_CORPORATE_MS, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString();
			if (titlcorp != null) {
				cust.setTitle(titlcorp);
			}
			if (getCustomerId() != null) {
				cust.setAmlStatusUpdatedBy(userName);
				cust.setUpdatedBy(userName);
				cust.setLastUpdated(getCurrentTime());
				cust.setAmlStatusUpdatedBy(userName);
				cust.setCreatedBy(getCreatedByCustomer());
				cust.setCreationDate(getCreatedDateCustomer());
				cust.setCustomerReference(getUpdateCustomerRefNo());
				cust.setCustomerId(getCustomerId());
			} else {
				cust.setCreatedBy(userName);
				cust.setCreationDate(getCurrentTime());
				BigDecimal custRef = icustomerRegistrationService.callProcedureCustReferenceNumber(getCompanyCodeByCompanyId(), Constants.DOCUMENT_CODE_FOR_CUSTOMER, getDealYearbyDate(), sessionStateManage.getBranchId());
				if (custRef != null) {
					cust.setCustomerReference(custRef);
				}
			}
			// @@@ AML
			/*String amlReturnStatus = null;
			String amlStatus = null;
			String amlhits = null;
			amlReturnStatus = getAMLCheckStatus(cust);
			// amlReturnStatus ="PASS-0";
			if (amlReturnStatus == null) {
				cust.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);
				cust.setNumberOfHits(new BigDecimal(0));
			} else {
				String[] parts = amlReturnStatus.split("-");
				amlStatus = parts[0];
				amlhits = parts[1];
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_ERROR)) {
					cust.setAmlStatus(Constants.FINSCAN_STATUS_ERROR);
					cust.setNumberOfHits(new BigDecimal(amlhits));
				}
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PENDING)) {
					cust.setAmlStatus(Constants.AML_STATUS_BCO);
					cust.setNumberOfHits(new BigDecimal(amlhits));
					// saveAmlStatus(customer);
				}
				if (amlStatus.equalsIgnoreCase(Constants.FINSCAN_STATUS_PASS)) {
					cust.setAmlStatus(Constants.AML_STATUS_PASS);
					cust.setNumberOfHits(new BigDecimal(0));
				}
			}*/
			
			cust.setAuditGrossIncome(getAuditGrossIncome());
			cust.setAuditStatementDate(getAuditStatementDate());
			
			getCorpRegService().updateCorpCustomer(cust);
			setCustomerId(cust.getCustomerId());
			// setUpdateCustomerRefNo(cust.getCustomerReference());
			List<Customer> custlist = corpRegService.getCustomerList(getCustomerId());
			if (custlist.size() > 0) {
				if (custlist.get(0).getCustomerReference() != null) {
					BigDecimal customerReference = custlist.get(0).getCustomerReference();
					setUpdateCustomerRefNo(customerReference);
					setCreatedByCustomer(custlist.get(0).getCreatedBy());
					setCreatedDateCustomer(custlist.get(0).getCreationDate());
				}
			}
			saveCompanyIdentityDocument();
		} catch (Exception e) {
			log.info("problem in Saving FS_CUSTOMER table" + e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
			setErrmsg(e.getMessage());
		}
	}
	
	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public void saveCorpRegPage() {
		try {
			if (nextToObjective()) {
				// saveOwnerImage();
				saveCorpData();
			}
		} catch (Exception e) {
			log.info("problem in redirecting" + e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}

	public void saveCorpRegObjectivePage() {
		try {
			
			onNatureOfBusinessSelect();
			
			if (seconObjList != null && seconObjList.size() == 1) {
				/*BigDecimal check= (BigDecimal)seconObjList.get(0);*/
				/*log.info("Check value is " + check);*/
				
				/*for (String iterable_element : seconObjList) {
				
					System.out.println(iterable_element);
					System.out.println(iterable_element);
					System.out.println(iterable_element);
					System.out.println(iterable_element);
				}*/
				
				for (int i = 0; i < seconObjList.size(); i++) {
					
					String temp = ""+ seconObjList.get(0);
					
					log.info("seconObj " + temp);
					log.info("seconObj's length"+temp.length());
					if(temp.length()==0)
					{
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrmsg("Please select Secondary Objective");
						return;
					}
					
					
				}
				/*if (check==1) {
					RequestContext.getCurrentInstance().execute("csp.show();");
					setErrmsg("Please select Secondary Objective");
					return;
				}*/
			}
			if (nextToContact()) {
				setCompanyIdProofDetail();
				saveCorpData();
				saveSecondaryObjective();
				saveBussnessNature();
			}
		} catch (Exception e) {
			log.info("problem in redirecting" + e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}
	
	
	public String commonCorpRegistrationSubmit() throws AMGException{
		String returnString="";
			setRenderCoIdentityDoc(false);
			setRenderContactDetails(false);
			setRenderValidate(false);
			setRenderObjective(false);
			setCoValdtBtn(false);
			setRenderPartnerDetail(false);
			setRenderOwnerDetails(false);
			setRenderTransactionalSetting(false);
			setEnabledddCompanyDataTable(false);
			setEnablePartnerScan(false);
			setEnabledddPartnerAuthDataTable(false);
			//setRenderSuccess(true);
			returnString = "corporateSucessPage";
			
			List<String> outPutList = icustomerRegistrationService.callProcedureUpdate(getCustomerId());
			if (outPutList.size() > 0) {
				if (outPutList.get(0).equalsIgnoreCase(Constants.Yes)) {
					RequestContext.getCurrentInstance().execute("migrationexception.show();");
				}
			}
		return returnString;
	}

	public String saveCorpRegPartnerAuthorizedPage() {
		String returnString =null;
		try {
			if(getCheckPartnerDetailsRequired()){
				if(partnerList != null && partnerList.size() != 0){
					if (saveOwnerImage()) {
						savePartnerAuthorized();
						//getAMLCheckStatus_afterSave(customerId);
						BigDecimal customerReference = null;
						List<Customer> custlist = corpRegService.getCustomerList(getCustomerId());
						if (custlist.size() > 0) {
							if (custlist.get(0).getCustomerReference() != null) {
								customerReference = custlist.get(0).getCustomerReference();
								setUpdateCustomerRefNo(customerReference);
								setCreatedByCustomer(custlist.get(0).getCreatedBy());
								setCreatedDateCustomer(custlist.get(0).getCreationDate());
							}
							
							ArrayList<AddPartnerDetailBean> newPartnerList = new ArrayList<AddPartnerDetailBean>();
							if (partnerList != null) {
								newPartnerList.addAll(partnerList);
								log.info("partnerList details" + partnerList.size());
							}
							List<CustomerIdProof> proofList = null;
							if (partnerList != null && partnerList.size() != 0) {
								for (AddPartnerDetailBean partner : partnerList) {
									log.info(partner.getCustomerIdProofId());
									log.info("pidno" + partner.getPidno());
									proofList = branchServiec.getCustomerIdProof(partner.getPidno());
									if (proofList != null && proofList.size() != 0) {
										List<Customer> customerList = branchServiec.getCustomerInfo(proofList.get(0).getFsCustomer().getCustomerId());
										/*boolean checkExist = false;
										Customer customerMobileandEmailUpdate = customerList.get(0);
										if (partner.getIspartneremailAlreadyExist() != null && partner.getIspartneremailAlreadyExist()) {
											log.info("partner.getPartnerEmail() " + partner.getPartnerEmail());
											customerMobileandEmailUpdate.setEmail(partner.getPartnerEmail());
											checkExist = true;
										}
										if (partner.getIspartnerMobileAlreadyExist() != null && partner.getIspartnerMobileAlreadyExist()) {
											log.info("partner.getPartnerEmail() " + partner.getPartnerEmail());
											customerMobileandEmailUpdate.setMobile(partner.getPartnerContactNumber());
											checkExist = true;
										}
										log.info("Partner mobile and email updated before");
										if (checkExist) {
											icustomerRegistrationService.saveCustomer(customerMobileandEmailUpdate);
											log.info("Partner mobile and email updated successfully");
										}*/
										if (customerList != null && customerList.size() != 0 && customerList.get(0).getEmail()!=null) {
											log.info("Customer Mail id" + customerList.get(0).getEmail());
											log.info("Customer Name " + customerList.get(0).getFirstName() + " " + customerList.get(0).getMiddleName() + " " + customerList.get(0).getLastName());
											
											String compnayName = icompanyMaster.viewById(sessionStateManage.getCompanyId(),sessionStateManage.getLanguageId());
											getMailService().sendRegistrationMailtoPartner(newPartnerList,customerList.get(0).getEmail(), "Successfully Registered", custlist.get(0).getCompanyName() , getCrno(),compnayName);
										}
									}
								}
							}
							returnString = commonCorpRegistrationSubmit();
						}
					}
				}else{
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrmsg("Please Add Partner to Proceed");
						return "";
					}	
			}else{
				returnString = commonCorpRegistrationSubmit();
			}
			
		} catch (Exception e) {
			log.info("problem in redirecting" + e);
			setRenderCoIdentityDoc(false);
			setRenderContactDetails(false);
			setRenderValidate(false);
			setRenderObjective(false);
			setCoValdtBtn(false);
			setRenderPartnerDetail(true);
			setRenderOwnerDetails(false);
			setRenderTransactionalSetting(false);
			setEnabledddCompanyDataTable(false);
			setEnablePartnerScan(false);
			setEnabledddPartnerAuthDataTable(true);
			setRenderSuccess(false);
			returnString = " ";
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
		return returnString;
	}
	
	

	public void saveCorpRegContactPage() {
		try {
			
			if((getBooCheckTelContact()==null && getBooCheckMobileContact()==null) || ((getBooCheckTelContact()!=true && getBooCheckMobileContact()!=true)))
			{
				RequestContext.getCurrentInstance().execute("telCheck.show();");
				setErrmsg("Please select Mobile Or Telephone");
				return;
			}
			if(getBooCheckTelContact()!=null && getBooCheckTelContact() && getTel()==null || (getBooCheckTelContact()!=null && getBooCheckTelContact() && getTel()!=null && getTel().length()==0 ))
			{
				RequestContext.getCurrentInstance().execute("telCheck.show();");
				setErrmsg("Please enter Telephone number");
				return;
			}
			
			if(getBooCheckMobileContact()!=null && getBooCheckMobileContact() && getContactNumber()==null || (getBooCheckMobileContact()!=null && getBooCheckMobileContact() && getContactNumber()!=null && getContactNumber().length()==0 ))
			{
				RequestContext.getCurrentInstance().execute("telCheck.show();");
				setErrmsg("Please enter Mobile number");
				return;
			}
			
			/*if(getEmail()==null || (getEmail()!=null && getEmail().length()==0))
			{
				RequestContext.getCurrentInstance().execute("telCheck.show();");
				setErrmsg("Please enter Email Id");
				return;
			}*/
			if(getArea()==null || (getArea()!=null && getArea().length()==0)) {
				RequestContext.getCurrentInstance().execute("telCheck.show();");
				setErrmsg("Please enter Area");
				return;
			}
			if (nextToPartnetAuthorized()) {
				saveContact();
			}
		} catch (Exception e) {
			log.info("problem in redirecting" + e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}

	public void saveCorpRegLimitAdditionalPage() {
		try {
			if (nextToPartnetAuthorized()) {
				setCompanyIdProofDetail();
				saveCorpData();
			}
		} catch (Exception e) {
			log.info("problem in redirecting" + e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}


	public String getDealYear() {
		return dealYear;
	}

	public void setDealYear(String dealYear) {
		this.dealYear = dealYear;
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

	public String getDealYearbyDate() {
		try {
			DealYearList = generalService.getDealYear(new Date());
			if (DealYearList != null) {
				if (getUserDealYear() == null) {
					dealYear = DealYearList.get(0).getFinancialYear().toString();
					dealYearId = DealYearList.get(0).getFinancialYearID();
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
	
	public void generateIncomeRange() {

		lstIncomeRange = getGeneralService().getIncomeRange(
				sessionStateManage.getCountryId(), getLevelId());
	}
	
	
	public List<String> autoCompleteArea(String query) {
		if (query.length() > 0) {
			return icustomerRegistrationService.getAreaforCorporate(query.toLowerCase());
		} else {
			return null;
		}
	}
	
	public boolean civilIdValidation() {

		boolean status = false;
		try {
			String returnString = getGeneralService()
					.getCivilIdStatus(new BigDecimal(getPidno()));
			if (returnString.equalsIgnoreCase("y")) {
				status = true;
			} else {
				status = false;
			}
		} catch (Exception e) {
			status = false;
		}
		
		return status;

}
	
	public void onDateSelect(SelectEvent event) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dealDate=format.format(event.getObject());
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(format.parse(dealDate));
			cal.add(Calendar.DATE, 1); 
			setAuditMinDate(cal.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void exit() throws IOException  {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/corporatehome.xhtml");
	}	
//////////////////////////////////////////////////////////Report Generation Code  Start  ///////////////////////////////////////////////////////////////////
	

@Autowired
ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
		
	
private JasperPrint jasperPrint;
private List<CorporateCompanyIdentityDocument> companyIdDocumentList;
private List<CorporatePartnerDetails> partnerDetailsList;
private List<CorporateRegistrationReport> corporateRegistrationList;

private List<CustomerInfoView> customerRegistrationList;
private List<CustomerContactDetailView> customerContactDetail;
private List<CustomerIdproofView> customerIdProof;
private List<CorpatePartnerInfoView> corporatePartnerInfo;
private List<CorporateCustomerInfoView> corporateCustomerInfo;


public void fetchCorporateRegistrationData() {
	companyIdDocumentList = new ArrayList<CorporateCompanyIdentityDocument>();
	partnerDetailsList = new ArrayList<CorporatePartnerDetails>();
	corporateRegistrationList = new ArrayList<CorporateRegistrationReport>();
	customerIdProof = new ArrayList<CustomerIdproofView>();
	corporatePartnerInfo = new ArrayList<CorpatePartnerInfoView>();
	corporateCustomerInfo = new ArrayList<CorporateCustomerInfoView>();
	
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
	
	
	
	
	String subReportPath = realPath + Constants.SUB_REPORT_PATH;
	
	customerRegistrationList = icustomerRegistrationService.findCustomerRegistration(getCustomerId());
	customerContactDetail = icustomerRegistrationService.findCustomerContactDetails(getCustomerId());
	customerIdProof= icustomerRegistrationService.findCustomerIdProof(getCustomerId());
	corporatePartnerInfo = icustomerRegistrationService.findCorporatePartnerInfo(getCustomerId());
	corporateCustomerInfo= icustomerRegistrationService.findCorporateCustomerInfo(getCustomerId());

	for(CorpatePartnerInfoView partnerObj: corporatePartnerInfo){
		CorporatePartnerDetails partnerDetails = new CorporatePartnerDetails();
		partnerDetails.setPartnerIdNumber(new BigDecimal(partnerObj.getIdNumber()));
		String  partnerName = null;
		if(partnerObj.getCustomerNameLocal()==null && partnerObj.getCustomerName()==null){
			partnerName=" ";
		}else if(partnerObj.getCustomerNameLocal()==null && partnerObj.getCustomerName()!=null){
			partnerName=partnerObj.getCustomerName();
		}else if(partnerObj.getCustomerNameLocal()!=null && partnerObj.getCustomerName()==null){
			partnerName=partnerObj.getCustomerNameLocal();
		}else if(partnerObj.getCustomerNameLocal()!=null && partnerObj.getCustomerName()!=null){
			partnerName=partnerObj.getCustomerName()+"  "+"("+partnerObj.getCustomerNameLocal()+")";
		}
		partnerDetails.setPartnerName(partnerName);
		partnerDetails.setExpiryDate(partnerObj.getIdProofExpDate());
		
		if(partnerObj.getPartnerPercentage()!=null){
			partnerDetails.setPartnerDetails(Constants.YES);
			partnerDetails.setPercentage(partnerObj.getPartnerPercentage().toString()+" %");
		}else{
			partnerDetails.setPartnerDetails(Constants.NO);
		}
		
		if(partnerObj.getEffectiveDate()!=null && partnerObj.getValidUpto()!=null){
			partnerDetails.setAuthorizedSignatory(Constants.YES);	
		}else{
			partnerDetails.setAuthorizedSignatory(Constants.NO);
		}
		partnerDetails.setEffectiveDate(partnerObj.getEffectiveDate());
		partnerDetails.setValidUpTo(partnerObj.getValidUpto());
		partnerDetails.setMobileNo(partnerObj.getMobile());
		partnerDetails.setEmail(partnerObj.getEmail());
		partnerDetails.setDesignation(partnerObj.getDesignation());
		partnerDetailsList.add(partnerDetails);	
	}

	for(CustomerIdproofView corpProof :customerIdProof){
		CorporateCompanyIdentityDocument corporateIdproof= new CorporateCompanyIdentityDocument();
		corporateIdproof.setCompanyIdNumber(corpProof.getIdProofInt());
		corporateIdproof.setCompanyIdType(corpProof.getIdProofTypeDesc());
		corporateIdproof.setCompanyIdExpiryDate(corpProof.getIdProofExpiredDate());
		companyIdDocumentList.add(corporateIdproof);
	}
	String secondaryObjective =null;
	
	CorporateRegistrationReport corporateRegReport = new CorporateRegistrationReport();
	
	for(CorporateCustomerInfoView customerObjective : corporateCustomerInfo){
		if(customerObjective.getObjectiveType()!=null && customerObjective.getObjectiveType().trim().equalsIgnoreCase("BUSINESS")){
			corporateRegReport.setNatureOfBusiness(customerObjective.getObjectiveDesc());
		}
		if(customerObjective.getObjectiveType()!=null && customerObjective.getObjectiveType().trim().equalsIgnoreCase("SECONDARY")){
			secondaryObjective = (secondaryObjective!=null?secondaryObjective+", ":"")+customerObjective.getObjectiveDesc();
		}
	}
	
	corporateRegReport.setSecondaryObjective(secondaryObjective);
	CustomerInfoView customerView = customerRegistrationList.get(0);
	
	corporateRegReport.setPrimaryObjective(customerView.getObjectiveDesc());
	corporateRegReport.setLogoPath(logoPath);
	corporateRegReport.setSubReport(subReportPath);
	corporateRegReport.setCrNumber(customerView.getCrNo());
	if(customerView.getCustomerReference() !=null){
	corporateRegReport.setCustomerRef(customerView.getCustomerReference().toString());
	}
	String  companyName = null;
	if(customerView.getCompanyNameLocal()==null && customerView.getCompanyName()==null){
		companyName=" ";
	}else if(customerView.getCompanyNameLocal()==null && customerView.getCompanyName()!=null){
		companyName=customerView.getCompanyName();
	}else if(customerView.getCompanyNameLocal()!=null && customerView.getCompanyName()==null){
		companyName=customerView.getCompanyNameLocal();
	}else if(customerView.getCompanyNameLocal()!=null && customerView.getCompanyName()!=null){
		companyName=customerView.getCompanyName()+"  "+"("+customerView.getCompanyNameLocal()+")";
	}
	corporateRegReport.setCompanyName(companyName);
	corporateRegReport.setCompanyRegDate(customerView.getDateOfBirth());
	String countryName = specialCustomerDealRequestService.getBankCountryNameForUpdate(sessionStateManage.getCountryId(),sessionStateManage.getLanguageId());
	corporateRegReport.setLocation(countryName);
	String currencyCode = generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));
	if(currencyCode!=null){
	if(customerView.getPaidUpCapital()!=null && customerView.getAuditGrossIncome()!=null){
	corporateRegReport.setPaidUpCapital(customerView.getPaidUpCapital().toString()+"  ("+currencyCode+")");
	corporateRegReport.setAuditGrossIncome(customerView.getAuditGrossIncome().toString()+"  ("+currencyCode+")");	
	}
	}else if(customerView.getPaidUpCapital()!=null && customerView.getAuditGrossIncome()!=null){
		corporateRegReport.setPaidUpCapital(customerView.getPaidUpCapital().toString());
		corporateRegReport.setAuditGrossIncome(customerView.getAuditGrossIncome().toString());	
	}
	corporateRegReport.setAuditStatementDate(customerView.getAuditStatementDate());
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	String currentDate = dateFormat.format(new Date()); 
	corporateRegReport.setCurrentDate(currentDate);
	corporateRegReport.setBranchName(customerView.getBranchName());
	corporateRegReport.setSignature(customerView.getSignature());
	corporateRegReport.setCompanyIdDocumentList(companyIdDocumentList);
	corporateRegReport.setPartnerDetailsList(partnerDetailsList);
	
	CustomerContactDetailView contactDetails = customerContactDetail.get(0);	
	corporateRegReport.setOfcState(contactDetails.getStateName());
	corporateRegReport.setOfcDistrict(contactDetails.getDistcritName());
	corporateRegReport.setOfcCity(contactDetails.getCityName());
	corporateRegReport.setOfcLocalArea(contactDetails.getContactArea());
	corporateRegReport.setOfcBlockNo(contactDetails.getContactBlockNo());
	corporateRegReport.setOfcStreetNo(contactDetails.getContactStreet());
	corporateRegReport.setOfcBuildNo(contactDetails.getContactBuildingNo());
	corporateRegReport.setOfficeNo(contactDetails.getContactFlat());
	corporateRegReport.setOfcMobileNo(contactDetails.getContactMobileNo());
	corporateRegReport.setOfcTelephone(contactDetails.getContactTelephone());
	corporateRegReport.setOfcEmail(contactDetails.getAlterEmailId());
	
	corporateRegistrationList.add(corporateRegReport);
}
public void fillDataToJasperFile() throws JRException {
	JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(corporateRegistrationList);
	String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/CorporateRegistrationReport.jasper");
	jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
}
public void generateCorporateRegistrationReport1(ActionEvent actionEvent)throws JRException, IOException {
	fetchCorporateRegistrationData();
	fillDataToJasperFile();
	HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	httpServletResponse.addHeader("Content-disposition","attachment; filename=CorporateRegistrationReport.pdf");
	ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
	JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
	FacesContext.getCurrentInstance().responseComplete();
}


//////////////////////////////////////////////////////////Report Generation Code  End ///////////////////////////////////////////////////////////////////	

@Autowired CustomerPersonalInfoBean<T> customerRegBean;

private Boolean isfromCorporatePage =false;

public Boolean getIsfromCorporatePage() {
	return isfromCorporatePage;
}

public void setIsfromCorporatePage(Boolean isfromCorporatePage) {
	this.isfromCorporatePage = isfromCorporatePage;
}

public void gotoCustomerRegistrationManualPage(){
		try {
			String idtypeCivilId = generalService.getComponentId(Constants.CIVILID,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString();
			String manual = generalService.getComponentId(Constants.METHODTYPE,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().toString();
			setIsfromCorporatePage(true);
			customerRegBean.setSelectType(manual);
			customerRegBean.setIdTypeValues(icustomerRegistrationService. getAllComponentComboDataForCustomer( sessionStateManage.getLanguageId(),Constants.Individual,Constants.COMPANYIDTYPE));
			customerRegBean.setIdType(idtypeCivilId);
			customerRegBean.setIdNumber(getPidno());
			customerRegBean.setSelectedIdType(idtypeCivilId);
			customerRegBean.setBooIdTypeCheck(false);
			customerRegBean.appearCarddetail();
			customerRegBean.setIsfromCorporatePage(true);
			customerRegBean.setIsFromCustomer(false);
			customerRegBean.setBooCheckMobile(false);
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("request", "corporateRegistration");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationmain.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
}

public void gotoCorporatePage(){
	try {
		
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationmain.xhtml");
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public void callPrerenderMethod() throws ParseException{

	HttpSession session = sessionStateManage.getSession();
	@SuppressWarnings("unchecked")
	CorporateSessionValues corporateObj = (CorporateSessionValues) session.getAttribute("corporateRegistration");
	if (corporateObj != null) {
		setErrmsg(null);
		setPidno(corporateObj.getPidno());
		setPartName(corporateObj.getPartName());
		setPartNameLocal(corporateObj.getPartNameLocal());
		setPartnerCustExpDate(corporateObj.getPartnerCustExpDate());
		setPartnerContactNumber(corporateObj.getPartnerContactNumber());
		setPartnerEmail(corporateObj.getPartnerEmail());
		setOccupation(corporateObj.getOccupationName());
		List<CustomerIdProof> registerIdProofList = getCorpRegService().getRegisterId(getPidno(), sessionStateManage.getCountryId());
		if (registerIdProofList.size() > 0 && registerIdProofList.get(0).getFsCustomer().getActivatedInd().equalsIgnoreCase(Constants.CUST_ACTIVE_INDICATOR)) {
		setPartnerCustomerId(registerIdProofList.get(0).getFsCustomer().getCustomerId());
		
		}
		session.removeAttribute("corporateRegistration");

	}
	
}

public void clearValue(){
	setPidno(null);
}



public void reScan(AddCoIdentityDetailBean addCompanyDocument){
	

	
	StringBuffer urlBuffer = new StringBuffer();
	/*String equals = "=";
	String and = "&";
	String ampersand = "&";
	String rootContext = readScan.getPropertiesValue().get("urlmodifydoc");
	String rootContextadd = readScan.getPropertiesValue().get("urladddoc");
	String idtype = readScan.getPropertiesValue().get("idtype");
	String newid = readScan.getPropertiesValue().get("newid");
	String usernamevalue = readScan.getPropertiesValue().get("usernamevalue");
	String passwordvalue = readScan.getPropertiesValue().get("passwordvalue");
	String username = readScan.getPropertiesValue().get("username");
	String password = readScan.getPropertiesValue().get("password");
	String arcmateField_DocID = readScan.getPropertiesValue().get("ArcmateField_DocID");
	String indexfield = readScan.getPropertiesValue().get("indexfield");
	String commercialRegistration_ArcMateField_FileCategoryID = readScan.getPropertiesValue().get("CommercialRegistration_ArcMateField_FileCategoryID");
	String civilid20 = readScan.getPropertiesValue().get("civilid20");
	String passport = readScan.getPropertiesValue().get("passport");
	String gccnationalid = readScan.getPropertiesValue().get("gccnationalid");
	String fileCatgoryId = readScan.getPropertiesValue().get("FileCategoryID");
	String arcMateField_FileCategoryID = readScan.getPropertiesValue().get("ArcMateField_FileCategoryID");*/

/*	urlBuffer.append(rootContext).append(ampersand).append(username).append(equals).append(usernamevalue).append(ampersand).append(password)
	.append(equals).append(passwordvalue).append(ampersand).append(civilid20).append(equals).append(addCompanyDocument.getIdno()).append(ampersand).append(fileCatgoryId).append(equals).append(commercialRegistration_ArcMateField_FileCategoryID);*/
	
	List<ArcmateScanMaster> arcmateList = icustomerRegistrationService.fetchArcmateMasterData(Constants.MODIFY, Constants.NON_OCR);
	List<ScanIdTypeMaster> scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(addCompanyDocument.getIdTypeId());
	

	if(arcmateList.size() != 0 && scanIdList.size() != 0){
		ArcmateScanMaster arcmateValue = arcmateList.get(0);
		ScanIdTypeMaster scanIdValue = scanIdList.get(0);
		String rootContext = "http://";
		urlBuffer.append(rootContext).append(arcmateValue.getIpAddress().trim()).append("/").append(arcmateValue.getContextPath().trim());
		if(arcmateValue.getUrlParamField1()!=null){
			urlBuffer.append(arcmateValue.getUrlParamField1().trim());
		}
		
		if(arcmateValue.getUrlParamField2()!=null){
			urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField2().trim());
		}
		
		if(arcmateValue.getUrlParamField3()!=null && scanIdValue.getDocumentId()!=null){
			urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField3().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getDocumentId());
		}
		
		if(arcmateValue.getUrlParamField4()!=null && scanIdValue.getFolderId()!=null){
			urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField4().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFolderId());
		}
		
		if(arcmateValue.getUrlParamField5()!=null && scanIdValue.getUserName()!=null){
			urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField5().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getUserName());
		}
		if(arcmateValue.getUrlParamField6()!=null && scanIdValue.getPassword()!=null){
			urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField6().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getPassword());
		}
	
		if(arcmateValue.getUrlParamField7()!=null){
			urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField7().trim()).append(arcmateValue.getFieldAssigner().trim()).append(addCompanyDocument.getIdno());
		}
		
		if(arcmateValue.getUrlParamField8()!=null && scanIdValue.getFileCategoryId()!=null){
			urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFileCategoryId());
		}

	
	System.out.println("Rescan Document of Non OCR URL :  " + urlBuffer.toString());
	log.info("Rescan Document of Non OCR URL :  " + urlBuffer.toString());
	try {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.redirect(urlBuffer.toString());
	} catch (Exception e) {
		log.info("Problem to redirect: " + e);
	}
	}else{
		RequestContext.getCurrentInstance().execute("arcmateTable.show();");
	}
	
}

public String getExceptionMessage() {
	return exceptionMessage;
}

public void setExceptionMessage(String exceptionMessage) {
	this.exceptionMessage = exceptionMessage;
}

}
	
