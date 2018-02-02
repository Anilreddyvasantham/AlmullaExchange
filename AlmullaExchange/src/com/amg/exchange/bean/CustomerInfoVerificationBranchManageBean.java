/*package com.amg.exchange.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.bean.RuleEngine;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CustCorporateAddlDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.DocumentImg;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.registration.service.IBranchPageService;
import com.amg.exchange.registration.service.ICorporateRegService;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.util.Constants;
//import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("customerInfoBranch")
@Scope("session")
public class CustomerInfoVerificationBranchManageBean<T>  { //extends AbstractReportBean implements Serializable {
	
	//Logger log = Logger.getLogger(CustomerInfoVerificationBranchManageBean.class);
	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 7599730867382515210L;
	
	*//**
	 * Declaring static variables for rule engine 
	 *//*
	private static final String CUSTOMERTYPE = "Individual";
	private static final String CIVILID = "Civil ID";
	private static final String GROUPID = "Almulla Group";
	private static final String EMPLOYMENTTYPE = "Un Employed";
	private static final String METHODTYPE = "Manual";
	private boolean renderBasicInfo = true;
	private boolean renderCustomerDetails = false;
	private boolean renderCustomeInfoEditable = false;
	private boolean renderCustomeInfoLocal = false;
    private boolean renderContactDetails = false;
    private boolean renderContactDetailsMatch = false;
    private boolean renderEmployeementInfo = false;
    private boolean renderIdProof = false;
    private boolean renderDigitalSign = false;
    private boolean renderIdNotReg  = false;
    private String methodTypeId =null;
    private String tokenNumber = null;
    private String idNumber = null;
    private BigDecimal customerId = null;
    private BigDecimal languageId ;
    private Date ExpiryDateforEdit;

    //customer details
    private String idType = null;
    private String title;
    private String titleL;
    private String firstName = null;
    private String middleName = null;
    private String lastName = null;
    private String shortName = null;
    private String firstNameL = null;
    private String middleNameL = null;
    private String lastNameL = null;
    private String shortNameL = null;
    private BigDecimal nationalityId = null;
    private String gender = null;
    private Date  idExpiryDate = null;
    private Date  dateOfBirth = null;
    private String  mobileNo = null;
    private String  email = null;
    private String amlStatus = null;
    private String numberOfHits = null;
    private Boolean approved = false;
	private String createdBy = null;
    private Date createdDate = null;
    private String tokenKey = null;
    private BigDecimal groupId = null;
    private BigDecimal companyId = null;
    private String alternateEmail = null;
   
	//contact details
    private BigDecimal contactTypeId = null;
    private BigDecimal  countryId = null;
    private BigDecimal  stateId = null;
    private BigDecimal  districtId = null;
    private BigDecimal  cityId = null;
    private String  localArea = null;
    private String streetNo = null;
    private String  telephoneNo = null;
    private String  flatNo = null;
    private String   blockNo= null;
  //  private List<ContactType> contactTypeList;
    
    Holding the component Behavior
	private Map<String, BussComponentConfDetail> mapComponentBehavior = new HashMap<String,BussComponentConfDetail>();
	
    private  Map<BigDecimal, String>  contactTypeMap   = new  HashMap<BigDecimal,String>();
    //employement info
    private String occupation = null;
	private String employer = null;
	private String employmentType = null;
	private String empcategory = null;
	private String originId = null;
	private String stateEmployment = null;
	private String postalCode = null;
    private BigDecimal empInfoCountry = null;
	private BigDecimal empInfoState = null;
	private BigDecimal empInfoDistrict = null;
	private String empInfoBlock = null;
	private String empInfoStreet = null;
	private BigDecimal empInfoCity = null;
	private String empInfoArea = null;
	private String empInfoOfficeTel = null;
	private String department = null;
	private BigDecimal empInfoEmploymentTypeId = null;
	private BigDecimal custEmployeementInfoId = null;
	
	private List<StateMasterDesc> lstStateEmpList = new ArrayList<StateMasterDesc>();
	private List<DistrictMasterDesc> lstDistrictEmpList = new ArrayList<DistrictMasterDesc>();
	private List<CityMasterDesc> lstCityEmpList = new ArrayList<CityMasterDesc>();
	
	private UploadedFile file;
	private StreamedContent downloadFile;  
	
	private BigDecimal image_id; 
	private boolean booUploadedOrNot;
	
	private BigDecimal idFor = null;
	private Date dateExp = null;
	public String getTitleL() {
		return titleL;
	}

	public void setTitleL(String titleL) {
		this.titleL = titleL;
	}
	private String idnumberProof = null;
	private Boolean booBrowsedFile = false;
	private Boolean enablePTbl;
	private String DATE_FORMAT = "dd/MM/yyyy";

	private String expDateAdder;
	private int saveUptoPanel = 1;
	private boolean readOnlyCustomerInfo = false;
	private boolean disableCustomerInfo=false;
	private boolean boosmartCardAppear = false;

	public int getSaveUptoPanel() {
		return saveUptoPanel;
	}

	public void setSaveUptoPanel(int saveUptoPanel) {
		this.saveUptoPanel = saveUptoPanel;
	}
	private Map<String, StreamedContent> imageMap = new HashMap<String, StreamedContent>();
	
	private String idTypeBranchPage = "";
	private BigDecimal idTypeproof = null;
	 Logger log = Logger.getLogger(CustomerInfoVerificationBranchManageBean.class);
	 
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	    
	
    private ContactDetail contactDetail = null;
//	private CorporateBusinessNature corpBussnsNature = null;
	private CustCorporateAddlDetail custCorpAddDetail = null;
	private CustomerIdProof custmrIdProof = null;
//	private IdentityType identityType = null;
	private Customer customer = null;
	private CountryMaster countryMstr = null;
	private CompanyMaster companyMstr = null;
//	private CustomerType custmrType = null;
	private LanguageType langType = null;
	private StateMaster stateMaster = null;
	private DistrictMaster districtMaster = null;
	private CountryMaster  countryMaster = null;
	private CountryMaster  countryMasterForNationality = null;
//	private ContactType  contactType = null;
	private CityMaster   cityMaster = null;
	private CompanyMaster companyMaster = null;
    private List<CountryMasterDesc>  countryList;
    private List<StateMasterDesc> lstState;
	private List<CityMasterDesc>lstCity;
	private List<DistrictMasterDesc>lstDistrict;
	private SessionStateManage sessionStateManage = new SessionStateManage();
	private List<AddContactDetailBean> contactList = new ArrayList<AddContactDetailBean>();
	private List<AddContactDetailBean> contactListDelete = new ArrayList<AddContactDetailBean>();
	private Map<BigDecimal,String>	idTypeMap = new HashMap<BigDecimal,String>();
//	private List<IdentityType> lstEmpInfoIdentityList = new ArrayList<IdentityType>();
	private List<AddCustomerIdProofBean> createProofList = new ArrayList<AddCustomerIdProofBean>();
	private List<AddCustomerIdProofBean> createProofListDelete = new ArrayList<AddCustomerIdProofBean>();
	private boolean enableContactDataTable = true;	
	private List<StateMasterDesc> stateList;
	private List<DistrictMasterDesc> districtList;
	private List<CityMasterDesc> cityList;
	private Map<BigDecimal,String>	idForMap = new HashMap<BigDecimal,String>();
	private Map<BigDecimal, String>	mapContactTypeList = new HashMap<BigDecimal, String>();
	Map<BigDecimal,String> mapCountryList = new HashMap<BigDecimal,String>();
	Map<BigDecimal,String> mapStateList = new HashMap<BigDecimal,String>();
	Map<BigDecimal,String> mapDistirictList = new HashMap<BigDecimal,String>();
	Map<BigDecimal,String> mapCityList = new HashMap<BigDecimal,String>();
	private List<CustomerIdProof> customerIdProofList = new ArrayList<CustomerIdProof>();
	private List<Customer> customerList = new  ArrayList<Customer>();
	private List<ContactDetail> contactDetailList = new ArrayList<ContactDetail>();
	private List<CustomerEmploymentInfo>   customerEmploymentInfoList = new ArrayList<CustomerEmploymentInfo>();
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private String userName=FacesContext.getCurrentInstance()
			.getExternalContext().getSessionMap().get("userName")
			.toString();
    private boolean renderIdProofVissibility = false;
	private BigDecimal customerIdToNominee = null;
	private boolean uploadFileVisibility = false;
    private String createdByCustomer;
    private Date createdDateCustomer;
    private String createdByEmployement;
    private Date createdDateEmployement;
    private String createdByIdProof;
    private Date createdDateIdProof;
    private String createdByContact;
    private Date createdDateContact;
	private boolean renderIdNumber = false;
	private boolean booEmploymentPanel = true;
	private Boolean booEmployed = false;
	private BigDecimal pkimage;
	private Boolean booIdTypeCheck = false;
	private BigDecimal selectedIdType = null;
	private boolean imageUploadCheck=false;
	private Boolean booContactDetailsDuplicate= false; //This is responsible to Check dupliacte Contact Details
	
	private String verifyToken = null;
	
	*//**Responsible to hold the identity Type from Rule Engine*//*
	Map<BigDecimal, String> mapIdentityType = new HashMap<BigDecimal, String>();
	//private String userName="AlMulla";
	//TODO
	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	IBranchPageService<T> branchpageService;
	
	@Autowired
	ICorporateRegService<T>   corpRegService;
    
	@Autowired
	RuleEngine<T> ruleEngine;
	
	// Timezone for application country based creation log
	private Date currentTime;
	
	

	public Date getCurrentTime() {
		
		Date objList = getGeneralService().getSysDateTimestamp(sessionStateManage.getCountryId());
		
		
		if(objList != null){
		currentTime = objList;}
		else{
			
			//currentTime.getTime();
			currentTime =null;
		}
		
		//Date dat = new Date(currentTime.getTime());
         
		
		List<Date> objList = getGeneralService().getSysDate(sessionStateManage.getCountryId());
		
		if(objList != null){
		currentTime = getGeneralService().getSysDate(sessionStateManage.getCountryId()).get(0);}
		else{
			currentTime =null;
		}
		
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
	
	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	
	public boolean isImageUploadCheck() {
		return imageUploadCheck;
	}

	public void setImageUploadCheck(boolean imageUploadCheck) {
		this.imageUploadCheck = imageUploadCheck;
	}

	public Boolean getBooIdTypeCheck() {
		return booIdTypeCheck;
	}

	public void setBooIdTypeCheck(Boolean booIdTypeCheck) {
		this.booIdTypeCheck = booIdTypeCheck;
	}

	public BigDecimal getSelectedIdType() {
		return selectedIdType;
	}

	public void setSelectedIdType(BigDecimal selectedIdType) {
		this.selectedIdType = selectedIdType;
	}

	public BigDecimal getPkimage() {
		return pkimage;
	}

	public void setPkimage(BigDecimal pkimage) {
		this.pkimage = pkimage;
	}

	public Boolean getBooEmployed() {
		return booEmployed;
	}

	public void setBooEmployed(Boolean booEmployed) {
		this.booEmployed = booEmployed;
	}

	public boolean isBooEmploymentPanel() {
		return booEmploymentPanel;
	}

	public void setBooEmploymentPanel(boolean booEmploymentPanel) {
		this.booEmploymentPanel = booEmploymentPanel;
	}

	public boolean isReadOnlyCustomerInfo() {
		return readOnlyCustomerInfo;
	}

	public void setReadOnlyCustomerInfo(boolean readOnlyCustomerInfo) {
		this.readOnlyCustomerInfo = readOnlyCustomerInfo;
	}

	public boolean isDisableCustomerInfo() {
		return disableCustomerInfo;
	}

	public void setDisableCustomerInfo(boolean disableCustomerInfo) {
		this.disableCustomerInfo = disableCustomerInfo;
	}

	public boolean isRenderIdNumber() {
		return renderIdNumber;
	}

	public void setRenderIdNumber(boolean renderIdNumber) {
		this.renderIdNumber = renderIdNumber;
	}
	public String getCreatedByCustomer() {
		return createdByCustomer;
	}

	public void setCreatedByCustomer(String createdByCustomer) {
		this.createdByCustomer = createdByCustomer;
	}

	public Date getCreatedDateCustomer() {
		return createdDateCustomer;
	}

	public void setCreatedDateCustomer(Date createdDateCustomer) {
		this.createdDateCustomer = createdDateCustomer;
	}

	public String getCreatedByEmployement() {
		return createdByEmployement;
	}

	public void setCreatedByEmployement(String createdByEmployement) {
		this.createdByEmployement = createdByEmployement;
	}

	public Date getCreatedDateEmployement() {
		return createdDateEmployement;
	}

	public void setCreatedDateEmployement(Date createdDateEmployement) {
		this.createdDateEmployement = createdDateEmployement;
	}

	public String getCreatedByIdProof() {
		return createdByIdProof;
	}

	public void setCreatedByIdProof(String createdByIdProof) {
		this.createdByIdProof = createdByIdProof;
	}

	public Date getCreatedDateIdProof() {
		return createdDateIdProof;
	}

	public void setCreatedDateIdProof(Date createdDateIdProof) {
		this.createdDateIdProof = createdDateIdProof;
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

	public boolean isRenderIdProofVissibility() {
		return renderIdProofVissibility;
	}

	public void setRenderIdProofVissibility(boolean renderIdProofVissibility) {
		this.renderIdProofVissibility = renderIdProofVissibility;
	}

	public boolean isUploadFileVisibility() {
		return uploadFileVisibility;
	}

	public void setUploadFileVisibility(boolean uploadFileVisibility) {
		this.uploadFileVisibility = uploadFileVisibility;
	}

	public Date getExpiryDateforEdit() {
		return ExpiryDateforEdit;
	}

	public void setExpiryDateforEdit(Date expiryDateforEdit) {
		ExpiryDateforEdit = expiryDateforEdit;
	}

	public boolean isRenderIdNotReg() {
		return renderIdNotReg;
	}

	public void setRenderIdNotReg(boolean renderIdNotReg) {
		this.renderIdNotReg = renderIdNotReg;
	}

	public String getExpDateAdder() {
		return expDateAdder;
	}

	public void setExpDateAdder(String expDateAdder) {
		this.expDateAdder = expDateAdder;
	}

	public List<StateMasterDesc> getLstStateEmpList() {
		popStateEmp();
		return lstStateEmpList;
	}

	public void setLstStateEmpList(List<StateMasterDesc> lstStateEmpList) {
		this.lstStateEmpList = lstStateEmpList;
	}

	public List<DistrictMasterDesc> getLstDistrictEmpList() {
		return lstDistrictEmpList;
	}

	public void setLstDistrictEmpList(List<DistrictMasterDesc> lstDistrictEmpList) {
		this.lstDistrictEmpList = lstDistrictEmpList;
	}

	public List<CityMasterDesc> getLstCityEmpList() {
		return lstCityEmpList;
	}

	public void setLstCityEmpList(List<CityMasterDesc> lstCityEmpList) {
		this.lstCityEmpList = lstCityEmpList;
	}

	public BigDecimal getGroupId() {
		return groupId;
	}

	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
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

	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}

	public String getAlternateEmail() {
		return alternateEmail;
	}

	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	public BigDecimal getCustEmployeementInfoId() {
		return custEmployeementInfoId;
	}

	public void setCustEmployeementInfoId(BigDecimal custEmployeementInfoId) {
		this.custEmployeementInfoId = custEmployeementInfoId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmpInfoOfficeTel() {
		return empInfoOfficeTel;
	}

	public void setEmpInfoOfficeTel(String empInfoOfficeTel) {
		this.empInfoOfficeTel = empInfoOfficeTel;
	}

	public String getEmpInfoArea() {
		return empInfoArea;
	}

	public void setEmpInfoArea(String empInfoArea) {
		this.empInfoArea = empInfoArea;
	}

	public BigDecimal getLanguageId() {
		return languageId;
	}

	public void setLanguageId(BigDecimal languageId) {
		this.languageId = languageId;
	}

	public ICorporateRegService<T> getCorpRegService() {
		return corpRegService;
	}

	public void setCorpRegService(ICorporateRegService<T> corpRegService) {
		this.corpRegService = corpRegService;
	}

	public boolean isRenderEmployeementInfo() {
		return renderEmployeementInfo;
	}

	public void setRenderEmployeementInfo(boolean renderEmployeementInfo) {
		this.renderEmployeementInfo = renderEmployeementInfo;
	}

	public String getAmlStatus() {
		return amlStatus;
	}

	public void setAmlStatus(String amlStatus) {
		this.amlStatus = amlStatus;
	}

	public String getNumberOfHits() {
		return numberOfHits;
	}

	public void setNumberOfHits(String numberOfHits) {
		this.numberOfHits = numberOfHits;
	}

	public BigDecimal getEmpInfoCountry() {
		return empInfoCountry;
	}

	public void setEmpInfoCountry(BigDecimal empInfoCountry) {
		this.empInfoCountry = empInfoCountry;
	}

	public BigDecimal getEmpInfoDistrict() {
		return empInfoDistrict;
	}

	public void setEmpInfoDistrict(BigDecimal empInfoDistrict) {
		this.empInfoDistrict = empInfoDistrict;
	}

	public BigDecimal getEmpInfoEmploymentTypeId() {
		return empInfoEmploymentTypeId;
	}

	public void setEmpInfoEmploymentTypeId(BigDecimal empInfoEmploymentTypeId) {
		this.empInfoEmploymentTypeId = empInfoEmploymentTypeId;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
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

	public BigDecimal getEmpInfoState() {
		return empInfoState;
	}

	public void setEmpInfoState(BigDecimal empInfoState) {
		this.empInfoState = empInfoState;
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

	public BigDecimal getEmpInfoCity() {
		return empInfoCity;
	}

	public void setEmpInfoCity(BigDecimal empInfoCity) {
		this.empInfoCity = empInfoCity;
	}

	public List<AddCustomerIdProofBean> getCreateProofList() {
		return createProofList;
	}

	public void setCreateProofList(List<AddCustomerIdProofBean> createProofList) {
		this.createProofList = createProofList;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public Boolean getEnablePTbl() {
		return enablePTbl;
	}

	public void setEnablePTbl(Boolean enablePTbl) {
		this.enablePTbl = enablePTbl;
	}

	public List<IdentityType> getLstEmpInfoIdentityList() {
		return lstEmpInfoIdentityList;
	}

	public void setLstEmpInfoIdentityList(List<IdentityType> lstEmpInfoIdentityList) {
		this.lstEmpInfoIdentityList = lstEmpInfoIdentityList;
	}

	public Date getDateExp() {
		return dateExp;
	}

	public void setDateExp(Date dateExp) {
		this.dateExp = dateExp;
	}

	public BigDecimal getIdFor() {
		return idFor;
	}

	public void setIdFor(BigDecimal idFor) {
		this.idFor = idFor;
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

	public String getIdTypeBranchPage() {
		return idTypeBranchPage;
	}

	public void setIdTypeBranchPage(String idTypeBranchPage) {
		this.idTypeBranchPage = idTypeBranchPage;
	}

	public BigDecimal getIdTypeproof() {
		return idTypeproof;
	}

	public void setIdTypeproof(BigDecimal idTypeproof) {
		this.idTypeproof = idTypeproof;
	}

	public IdentityType getIdentityType() {
		return identityType;
	}

	public void setIdentityType(IdentityType identityType) {
		this.identityType = identityType;
	}

	public IBranchPageService<T> getBranchpageService() {
		return branchpageService;
	}

	public void setBranchpageService(IBranchPageService<T> branchpageService) {
		this.branchpageService = branchpageService;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public List<AddContactDetailBean> getContactList() {
		return contactList;
	}

	public void setContactList(List<AddContactDetailBean> contactList) {
		this.contactList = contactList;
	}

	public boolean isEnableContactDataTable() {
		return enableContactDataTable;
	}

	public void setEnableContactDataTable(boolean enableContactDataTable) {
		this.enableContactDataTable = enableContactDataTable;
	}

	public boolean isRenderBasicInfo() {
		return renderBasicInfo;
	}

	public void setRenderBasicInfo(boolean renderBasicInfo) {
		this.renderBasicInfo = renderBasicInfo;
	}

	public boolean isRenderCustomeInfoEditable() {
		return renderCustomeInfoEditable;
	}

	public void setRenderCustomeInfoEditable(boolean renderCustomeInfoEditable) {
		this.renderCustomeInfoEditable = renderCustomeInfoEditable;
	}

	public boolean isRenderCustomeInfoLocal() {
		return renderCustomeInfoLocal;
	}

	public void setRenderCustomeInfoLocal(boolean renderCustomeInfoLocal) {
		this.renderCustomeInfoLocal = renderCustomeInfoLocal;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public boolean isRenderCustomerDetails() {
		return renderCustomerDetails;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMethodTypeId() {
		return methodTypeId;
	}

	public void setMethodTypeId(String methodTypeId) {
		this.methodTypeId = methodTypeId;
	}

	public String getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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

	public BigDecimal getCityId() {
		return cityId;
	}

	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
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

	public String getFirstNameL() {
		return firstNameL;
	}

	public void setFirstNameL(String firstNameL) {
		this.firstNameL = firstNameL;
	}

	public String getMiddleNameL() {
		return middleNameL;
	}

	public void setMiddleNameL(String middleNameL) {
		this.middleNameL = middleNameL;
	}

	public String getLastNameL() {
		return lastNameL;
	}

	public void setLastNameL(String lastNameL) {
		this.lastNameL = lastNameL;
	}

	public String getShortNameL() {
		return shortNameL;
	}

	public void setShortNameL(String shortNameL) {
		this.shortNameL = shortNameL;
	}

	public BigDecimal getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(BigDecimal nationalityId) {
		this.nationalityId = nationalityId;
	}
	public Date getIdExpiryDate() {
		return idExpiryDate;
	}

	public void setIdExpiryDate(Date idExpiryDate) {
		this.idExpiryDate = idExpiryDate;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public String getLocalArea() {
		return localArea;
	}

	public void setLocalArea(String localArea) {
		this.localArea = localArea;
	}

	public String getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getBlockNo() {
		return blockNo;
	}

	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}

	public boolean isRenderContactDetailsMatch() {
		return renderContactDetailsMatch;
	}

	public void setRenderContactDetailsMatch(boolean renderContactDetailsMatch) {
		this.renderContactDetailsMatch = renderContactDetailsMatch;
	}

	public boolean isRenderIdProof() {
		return renderIdProof;
	}

	public void setRenderIdProof(boolean renderIdProof) {
		this.renderIdProof = renderIdProof;
	}

	public boolean isRenderDigitalSign() {
		return renderDigitalSign;
	}

	public void setRenderDigitalSign(boolean renderDigitalSign) {
		this.renderDigitalSign = renderDigitalSign;
	}

	public boolean isRenderContactDetails() {
		return renderContactDetails;
	}

	public void setRenderContactDetails(boolean renderContactDetails) {
		this.renderContactDetails = renderContactDetails;
	}

	public void setRenderCustomerDetails(boolean renderCustomerDetails) {
		this.renderCustomerDetails = renderCustomerDetails;
	}
	
	
	public boolean isBoosmartCardAppear() {
		return boosmartCardAppear;
	}

	public void setBoosmartCardAppear(boolean boosmartCardAppear) {
		this.boosmartCardAppear = boosmartCardAppear;
	}

	
	*//**
	 * @return the booContactDetailsDuplicate
	 *//*
	public Boolean getBooContactDetailsDuplicate() {
		return booContactDetailsDuplicate;
	}

	*//**
	 * @param booContactDetailsDuplicate the booContactDetailsDuplicate to set
	 *//*
	public void setBooContactDetailsDuplicate(Boolean booContactDetailsDuplicate) {
		this.booContactDetailsDuplicate = booContactDetailsDuplicate;
	}

	public void panelSaveController() throws SerialException, IOException, SQLException, ParseException {
		if(getSaveUptoPanel() == 1) {
			saveCustomerInfo();
		} else if(getSaveUptoPanel() == 2) {
			saveContactDetails();
		} else if(getSaveUptoPanel() == 3){
			saveEmployeementInfo();
		} else {
			
		saveCustomerIdProof();	
		}
	}
	
	*//**
	 * method is responsible to populate List of countries from DB 
	 * @return
	 *//*
	public List<CountryMasterDesc> getCountryListFromDB() {
		countryList = new ArrayList<CountryMasterDesc>();
		countryList.addAll( getGeneralService().getCountryList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1")));
 		return countryList;
	}
	*//**
	 * method is responsible to populate List of countries from DB 
	 * @return
	 *//*
	public List<CountryMasterDesc> getCountryNameList() {
		 sessionStateManage = new SessionStateManage(); 
		 countryList = new ArrayList<CountryMasterDesc>();
		 countryList.addAll( getGeneralService().getCountryList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1")));
		
		for(CountryMasterDesc countryMasterDesc:countryList) {
			mapCountryList.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
		}
 		return countryList;
	}
	public List<CountryMasterDesc> getNationalityNameList() {
		SessionStateManage sessionStateManage = new SessionStateManage();
		List<CountryMasterDesc> nationalityList = getGeneralService().getNationalityList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"));
		return nationalityList;
	}
	
	*
	*method to get state from db and add all the state code and name will be mapped to hashMap
	
	public void popState() {
		lstState = new ArrayList<StateMasterDesc>();
		lstState.addAll(getGeneralService().getStateList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"),sessionStateManage.getCountryId()));
		for(StateMasterDesc stateMasterDesc:lstState) {
			mapStateList.put(stateMasterDesc.getFsStateMaster().getStateId(), stateMasterDesc.getStateName());
		}
	}
	public List<StateMasterDesc> getStateListFromDB() {
		popState();
		return lstState;
	}
	
	*
	*method to get District from db and add all the state code and name will be mapped to hashMap
	
	public void popDistrict(AjaxBehaviorEvent e) {
		lstDistrict = new ArrayList<DistrictMasterDesc>();
		lstDistrict.addAll(getGeneralService().getDistrictList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"),sessionStateManage.getCountryId(),getStateId()));
		for(DistrictMasterDesc districtMasterDesc:lstDistrict) {
			mapDistirictList.put(districtMasterDesc.getFsDistrictMaster().getDistrictId(), districtMasterDesc.getDistrict());
		}
	}
	public List<DistrictMasterDesc> getDistrictListFromDB() {
		return lstDistrict;
	}
	
	*
	*method to get city from db and add all the state code and name will be mapped to hashMap
	
	public void popCity(AjaxBehaviorEvent e) {
		lstCity = new ArrayList<CityMasterDesc>();
		lstCity.addAll(getGeneralService().getCityList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"),sessionStateManage.getCountryId(),getStateId(),getDistrictId()));
		for(CityMasterDesc cityMasterDesc:lstCity) {
			mapCityList.put(cityMasterDesc.getFsCityMaster().getCityId(), cityMasterDesc.getCityName());
		}
	}
	public List<CityMasterDesc> getCityListFromDB() {
		return lstCity;
		
	}
	public void idExpDateChange(SelectEvent event ) {
		for(AddCustomerIdProofBean element:createProofList) {
			if(element.getIdNumber().equals(getIdNumber())) {
				element.setIdExpiryDate(new SimpleDateFormat(DATE_FORMAT).format(getExpiryDateforEdit()));
			}
		}
		
	}
	
	*
	*method to get state from db  for empinfo
	
	public void popStateEmp() {
		setLstStateEmpList(null);
		sessionStateManage = new SessionStateManage();
		List<StateMasterDesc> statemasterDesc = getGeneralService().getStateList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"),sessionStateManage.getCountryId());
	    setLstStateEmpList(statemasterDesc);
	}
	
	*method to get district from db  for empinfo
	
	public void popDistrictEmp(AjaxBehaviorEvent e) {
		setLstDistrictEmpList(null);
		sessionStateManage = new SessionStateManage();
		List<DistrictMasterDesc> districtmasterDesc = getGeneralService().getDistrictList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"),sessionStateManage.getCountryId(),getEmpInfoState());
	    setLstDistrictEmpList(districtmasterDesc);
	}
	
	*method to get city from db  for empinfo
	
	public void popCityEmp(AjaxBehaviorEvent e) {
		setLstCityEmpList(null);
		sessionStateManage = new SessionStateManage();
		List<CityMasterDesc> citymasterDesc = getGeneralService().getCityList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"),sessionStateManage.getCountryId(),getEmpInfoState(),getEmpInfoDistrict());
	    setLstCityEmpList(citymasterDesc);
	}
	private BigDecimal individualIdType = new BigDecimal(1);
	
	 * method to check the type of the method
	 
	public void methodTypeCheck() {
		
		 if (getMethodTypeId().equalsIgnoreCase(getGeneralService().getComponentId(Constants.METHODTYPE,new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):""+1)).
				    getFsBizComponentData().getComponentDataId().toPlainString()) && getMethodTypeId() != null) {
			setRenderIdNumber(true);
			setBoosmartCardAppear(false);
		} else {
            setRenderIdNumber(false);
            setBoosmartCardAppear(true);
		}
		
	}
	//TODO
	
	 * method to fetch the record from db
	 * 
	 
	public void fetchCustomerInfo() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH,+0);
		Date today90 = cal.getTime();
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
		String finalDate = form.format(today90);
        setExpDateAdder(finalDate);
        SimpleDateFormat  smpDate = new SimpleDateFormat("dd/MM/yyyy");
		SessionStateManage sessionStateManage = new SessionStateManage();
		setLanguageId(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):""+1));
		setBooDobInput(true);
		setBooDobCalender(false);
		try {
			customerIdProofList= getBranchpageService().getCustomerIdProof(getIdNumber());
			if(customerIdProofList.size()>0) {
				//Means if image id will be null, Approve all will be disable, so disable true, 100 means no image
				if(customerIdProofList.size()==1 && customerIdProofList.get(0).getFsDocumentImg().getImgId().intValue()==100){
					setBooUploadedOrNot(true);
				} else {
					setBooUploadedOrNot(false);
				}
				getFetchContactTypeList();
				customerList = getBranchpageService().getCustomerInfoWithToken(customerIdProofList.get(0).getFsCustomer().getCustomerId(),getTokenNumber());
				if(customerList.size()>0) {
					if(customerList.get(0).getActivatedInd().equalsIgnoreCase("1")) {
						RequestContext.getCurrentInstance().execute("dlgApproved.show();");
					} else {
						setCustomerDetails();
						setCustomerIdProof();
						fillCountryList();
						fillStateList();
						fillDistrictList();
						fillCityList();
						setContactDetails();
						setEmployeementInfo();
					    setRenderCustomerDetails(true);
					    setRenderCustomeInfoEditable(true);
					    setRenderCustomeInfoLocal(true);
						setRenderBasicInfo(false);
						setRenderEmployeementInfo(false);
						setRenderIdProof(false);
						setReadOnlyCustomerInfo(true);
						setDisableCustomerInfo(true);
						setBooDobCalender(false);
						setBooDobInput(true);
						
					}
				}else {
					//setRenderIdNotReg(true);
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("notMatched.show();");
				}
			}else {
				//setRenderIdNotReg(true);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("notMatched.show();");
			}
	   
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
	public void setCustomerIdProof() {
		setCustomerId(customerIdProofList.get(0).getFsCustomer().getCustomerId());
		setExpiryDateforEdit(customerIdProofList.get(0).getIdentityExpiryDate());
		createProofList.clear();
		customerIdProofList = getBranchpageService().getCustomerIdProofList(getCustomerId());
		for(CustomerIdProof  customerIdProof:customerIdProofList ) {
			AddCustomerIdProofBean  addCustomerIdProofBean = new AddCustomerIdProofBean();
			addCustomerIdProofBean.setIdForName(idForMap.get(customerIdProof.getFsBizComponentDataByIdentityFor().getComponentDataId()));
			addCustomerIdProofBean.setIdForId(customerIdProof.getFsBizComponentDataByIdentityFor().getComponentDataId());
			addCustomerIdProofBean.setIdNumber(customerIdProof.getIdentityInt());
			addCustomerIdProofBean.setIdTypeName(idTypeMap.get(customerIdProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId()));
			addCustomerIdProofBean.setIdTypeId(customerIdProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId());
			addCustomerIdProofBean.setIdExpiryDate(new SimpleDateFormat(DATE_FORMAT).format(customerIdProof.getIdentityExpiryDate()));
			addCustomerIdProofBean.setImageId(customerIdProof.getFsDocumentImg().getImgId());
			addCustomerIdProofBean.setCustomerIdProofId(customerIdProof.getCustProofId());
			addCustomerIdProofBean.setCreatedByIdProof(customerIdProof.getCreatedBy());
			addCustomerIdProofBean.setCreatedDateIdProof(customerIdProof.getCreationDate());
			
			if(customerIdProof.getApprovedBy().equalsIgnoreCase("0") || customerIdProof.getApprovedBy() == null) {
				addCustomerIdProofBean.setChecked(false);
			}else{
				addCustomerIdProofBean.setChecked(true);
			}
			
			if(customerIdProof.getApprovedBy()!= null){
				if(!customerIdProof.getApprovedBy().equalsIgnoreCase("0")){
					addCustomerIdProofBean.setChecked(true);
				}
			} else {
				addCustomerIdProofBean.setChecked(false);
			}
			
			//createProofTable.setChecked(customerIdProof.getApprovedBy().equals("0")?false:true);
			if(customerIdProof.getFsDocumentImg().getImgId().intValue()!=100){
				createProofList.add(addCustomerIdProofBean);
			}
		}
		
	}
	*//***
	 * method is responsible for setting article details
	 *//*
	public void populateArticleLevel() {
		
		lstLevel = getBranchpageService().getLevelData(getArticle(),sessionStateManage.getLanguageId());
	}
	public void setCustomerDetails() {
		//customerList = getBranchpageService().getCustomerInfo(getCustomerId());
		//for(Customer customer: customerList) {
		 getArticleData();
		
			setNationalityId(customerList.get(0).getFsCountryMasterByNationality().getCountryId());
			setFirstName(customerList.get(0).getFirstName());
			setFirstNameL(customerList.get(0).getFirstNameLocal());
			setMiddleName(customerList.get(0).getMiddleName());
			setMiddleNameL(customerList.get(0).getMiddleNameLocal());
			setShortName(customerList.get(0).getShortName());
			setShortNameL(customerList.get(0).getShortNameLocal());
			setLastName(customerList.get(0).getLastName());
			setLastNameL(customerList.get(0).getLastNameLocal());
			setEmail(customerList.get(0).getEmail());
			setTitle(customerList.get(0).getTitle());
			setTitleL(customerList.get(0).getTitleLocal());
			setMobileNo(customerList.get(0).getMobile());
			setGender(customerList.get(0).getGender());
			setShowDob(new SimpleDateFormat("dd/MM/yyyy").format(customerList.get(0).getDateOfBirth()));
			setAlternateEmail(customerList.get(0).getAlterEmailId());
			setTokenNumber(customerList.get(0).getTokenKey());
			setCreatedByCustomer(customerList.get(0).getCreatedBy());
			setCreatedDateCustomer(customerList.get(0).getCreationDate());
			
			setDailyLimit(customerList.get(0).getDailyLimit());
			setWeeklyLimit(customerList.get(0).getWeeklyLimit());
			setMonthlyLimit(customerList.get(0).getMontlyLimit());
			setQuarterlyLimit(customerList.get(0).getQuaterlyLimit());
			setHalfyearly(customerList.get(0).getHalfYearly());
			setAnnualLimit(customerList.get(0).getAnnualLimit());
			
			*//**
			 * for first time article level is zero to void exception
			 *//*
			try{
				setArticle(customerList.get(0).getFsArticleDetails().getFsArticleMaster().getArticleId());
				populateArticleLevel();
				setLevel(customerList.get(0).getFsArticleDetails().getArticleDetailId());
				generateIncomeRange();
				setIncomeRange(customerList.get(0).getFsIncomeRangeMaster().getIncomeRangeId());
			} catch(Exception e){
				log.info("First Time Article ................ ");
			}
			setAmlStatus("pass");
			setNumberOfHits("0");
			if(customerList.get(0).getActivatedInd().equalsIgnoreCase("0")) {
				setApproved(false);
			}else {
				setApproved(true);
			}
	}
	//Get country list and store into local map object  
		private void fillCountryList(){
			
			if(mapCountryList.size()==0){
				mapCountryList.clear();
				for(CountryMasterDesc countryMasterDesc : getGeneralService().getCountryList(getLanguageId())){
					mapCountryList.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
				}
				
			}
		}
		//Get state list and store into local map object  
		private void fillStateList(){
			
			if(mapStateList.size()==0){
				mapStateList.clear();
				for(StateMasterDesc stateMasterDesc : getGeneralService().getStateList(languageId)){
					mapStateList.put(stateMasterDesc.getFsStateMaster().getStateId(), stateMasterDesc.getStateName());
				}
			}
		}
		//Get district list and store into local map object 
		private void fillDistrictList(){
			
			if(mapDistirictList.size()==0){
				mapDistirictList.clear();
				for(DistrictMasterDesc districtMasterDesc : getGeneralService().getDistrictList(getLanguageId())){
					mapDistirictList.put(districtMasterDesc.getFsDistrictMaster().getDistrictId(), districtMasterDesc.getDistrict());
				}
			}
		}
		//Get city list and store into local map object  
		private void fillCityList(){
			
			if(mapCityList.size()==0){
				mapCityList.clear();
				for(CityMasterDesc cityMasterDesc : getGeneralService().getCityList(getLanguageId())){
					mapCityList.put(cityMasterDesc.getFsCityMaster().getCityId(), cityMasterDesc.getCityName());
				}
			}
		}
		//to get contact type  list and store into local map object
		public void getFetchContactTypeList() {
			mapContactTypeList = ruleEngine.getComponentData("Contact Type");
			idForMap = ruleEngine.getComponentData("ID For");
			idTypeMap = ruleEngine.getComponentData("Identity Type");
			idForMap = ruleEngine.getComponentData("ID For");
			idTypeMap = ruleEngine.getComponentData("Identity Type");
		}
	public void setContactDetails() {
		
		contactDetailList.clear();
		contactDetailList.addAll(getBranchpageService().getCustomerContactDetails(getCustomerId()));
	
		if(contactDetailList.size()>0) {
			//AddContactDetailBean addContactDetailBean = new AddContactDetailBean();
			for (ContactDetail cdetail : contactDetailList) {
				
				AddContactDetailBean addContactDetailBean = new AddContactDetailBean(
						mapContactTypeList.get(cdetail.getFsBizComponentDataByContactTypeId().getComponentDataId()),
						cdetail.getArea(), mapCountryList.get(cdetail.getFsCountryMaster().getCountryId()),
						cdetail.getStreet(), cdetail.getBlock(),
						cdetail.getTelephone(), cdetail.getFlat(),
						mapStateList.get(cdetail.getFsStateMaster().getStateId()), mapDistirictList.get(cdetail.getFsDistrictMaster().getDistrictId()),
						mapCityList.get(cdetail.getFsCityMaster().getCityId()),
						false, false, cdetail
								.getFsBizComponentDataByContactTypeId()
								.getComponentDataId(), cdetail
								.getFsCountryMaster().getCountryId(), cdetail
								.getFsStateMaster().getStateId(), cdetail
								.getFsDistrictMaster().getDistrictId(), cdetail
								.getFsCityMaster().getCityId(),
						cdetail.getContactDetailId());
				addContactDetailBean.setCreatedByContact(cdetail.getCreatedBy());
				addContactDetailBean.setCreatedDateContact(cdetail.getCreationDate());
				contactList.add(addContactDetailBean);
				//setContactDataTable(true);
		}
		}
		
	}
	public void populateState() {
		sessionStateManage = new SessionStateManage();
		List<StateMasterDesc> statemasterDesc = getGeneralService().getStateList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"),getEmpInfoCountry());
	    setLstStateEmpList(statemasterDesc);
	}
	public void populateDistrict() {
		sessionStateManage = new SessionStateManage();
		List<DistrictMasterDesc> districtmasterDesc = getGeneralService().getDistrictList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"),getEmpInfoCountry(),getEmpInfoState());
	    setLstDistrictEmpList(districtmasterDesc);
	}
	public void populateCity() {
		sessionStateManage = new SessionStateManage();
		List<CityMasterDesc> citymasterDesc = getGeneralService().getCityList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"),getEmpInfoCountry(),getEmpInfoState(),getEmpInfoDistrict());
	    setLstCityEmpList(citymasterDesc);
	}
	public void setEmployeementInfo() {
	
		customerEmploymentInfoList.addAll(getBranchpageService().getCustomerEmploymentInfo(getCustomerId()));
		if(customerEmploymentInfoList != null  &&  customerEmploymentInfoList.size()>0) {
			
			setEmpInfoEmploymentTypeId(customerEmploymentInfoList.get(0).getFsBizComponentDataByEmploymentTypeId().getComponentDataId());
			setOccupation(customerEmploymentInfoList.get(0).getFsBizComponentDataByOccupationId().getComponentDataId().toPlainString());
			setEmployer(customerEmploymentInfoList.get(0).getEmployerName());
			setEmpInfoCountry(customerEmploymentInfoList.get(0).getFsCountryMaster().getCountryId());
			populateState();
			setEmpInfoState(customerEmploymentInfoList.get(0).getFsStateMaster().getStateId());
			populateDistrict();
			setEmpInfoDistrict(customerEmploymentInfoList.get(0).getFsDistrictMaster().getDistrictId());
			populateCity();
			setEmpInfoCity(customerEmploymentInfoList.get(0).getFsCityMaster().getCityId());
			setEmpInfoStreet(customerEmploymentInfoList.get(0).getStreet());
			setPostalCode(customerEmploymentInfoList.get(0).getPostal());
			setEmpInfoBlock(customerEmploymentInfoList.get(0).getBlock());
			setEmpInfoArea(customerEmploymentInfoList.get(0).getArea());
			setEmpInfoOfficeTel(customerEmploymentInfoList.get(0).getOfficeTelephone());
			setDepartment(customerEmploymentInfoList.get(0).getDepartment());
			setCustEmployeementInfoId(customerEmploymentInfoList.get(0).getCustEmpInfoId());
			setCreatedByEmployement(customerEmploymentInfoList.get(0).getCreatedBy());
			setCreatedDateEmployement(customerEmploymentInfoList.get(0).getCreationDate());
			*//**This boolean is responsible to render the panel depending upon Employment Type*//*
			if(getEmpInfoEmploymentTypeId().intValue() == getGeneralService().getComponentId(Constants.EMPLOYMENTTYPE, 
					sessionStateManage.getLanguageId()).
					getFsBizComponentData().getComponentDataId().intValue()){
				setBooEmploymentPanel(false);
			}else {
				setBooEmploymentPanel(true);
			}
		}else {
			setEmpInfoEmploymentTypeId(new BigDecimal(getGeneralService().getComponentId(Constants.EMPLOYMENTTYPE, 
					sessionStateManage.getLanguageId()).
					getFsBizComponentData().getComponentDataId().intValue()));
			*//**This boolean is responsible to render the panel depending upon Employment Type*//*
			setBooEmploymentPanel(false);
		}
	}
	
	 * method is responsible to clear basic info
	 * 
	 
	public void clearBasicInfo() {
		
		setMethodTypeId(null);
		setTokenNumber("");
		setIdNumber("");
	}
	
	 * method is responsible to back basic info and hide other panels
	 * 
	 
	public void backCustomerInfoToBasicInfo() {
	
		setRenderBasicInfo(true);
		setRenderCustomerDetails(false);
		setRenderCustomeInfoEditable(false);
		setRenderCustomeInfoLocal(false);
	}
	
	 * method is responsible to clear  customer info
	 * 
	 
	public void clearCustomerInfo() {

		setFirstName("");
		setMiddleName("");
		setLastName("");
		setShortName("");
		setFirstNameL("");
		setMiddleNameL("");
		setLastNameL("");
		setShortNameL("");
		setTitle("");
		setGender("");
		setNationalityId(null);
		setEmail("");
		setIdExpiryDate(null);
		setDateOfBirth(null);
		setMobileNo("");
		setArticle(null);
		setLevel(null);
		setIncomeRange(null);
		setDailyLimit(null);
		setWeeklyLimit(null);
		setMonthlyLimit(null);
		setHalfyearly(null);
		setAnnualLimit(null);
	}
	
	 * method is responsible to display (contact details) and hide other panels 
	 * 
	 
	public void nextContactDetailsTest() {
		
		setCountryId(sessionStateManage.getCountryId());
		setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), getCountryId()));
	
		setRenderIdProof(false);
		setRenderBasicInfo(false);
		setRenderCustomerDetails(false);
		setRenderCustomeInfoEditable(false);
		setRenderCustomeInfoLocal(false);
		setRenderContactDetails(false);
		setRenderContactDetailsMatch(true);
		setSaveUptoPanel(2);
	}
	
	 * method is responsible to add contact in a dataTable
	 * 
	 
	public void addContactDataTable() {
		
		setBooContactDetailsDuplicate(false);
		for (AddContactDetailBean contactDetails : contactList) {
		if(getContactTypeId().intValue() == contactDetails.getContactTypeId().intValue() && getCountryId().intValue() == contactDetails.getCountryId().intValue()
				&& getStateId().intValue() == contactDetails.getStateId().intValue() && getDistrictId().intValue() == contactDetails.getDistrictId().intValue()
				&& getCityId().intValue() == contactDetails.getCityId().intValue() && getLocalArea().equalsIgnoreCase(contactDetails.getArea())
				&& getStreetNo().equalsIgnoreCase(contactDetails.getStreet()) && getBlockNo().equalsIgnoreCase(contactDetails.getBlock())
				&& getFlatNo().equalsIgnoreCase(contactDetails.getFlat())){
			
			setBooContactDetailsDuplicate(true);
			break;
		}
		}
		
		if(!getBooContactDetailsDuplicate()) {
	    AddContactDetailBean addContact = new AddContactDetailBean();
	    addContact.setContType(mapContactTypeList.get(this.contactTypeId));
	    addContact.setContactTypeId(this.contactTypeId);
	    addContact.setCountryId(this.countryId);
	    addContact.setCountry(mapCountryList.get(this.countryId));
	    addContact.setStateId(this.stateId);
	    addContact.setState(mapStateList.get(this.stateId));
	    addContact.setDistrictId(this.districtId);
	    addContact.setDist(mapDistirictList.get(this.districtId));
	    addContact.setCityId(this.cityId);
	    addContact.setCity(mapCityList.get(this.cityId));
	    addContact.setArea(this.localArea);
	    addContact.setStreet(this.streetNo);
	    addContact.setFlat(this.flatNo);
	    addContact.setTel(this.telephoneNo);
	    addContact.setBlock(this.blockNo);
		contactList.add(addContact);
		setContactTypeId(new BigDecimal(0));
		setCountryId(new BigDecimal(0));
		setStateId(new BigDecimal(0));
		setDistrictId(new BigDecimal(0));
		setCityId(new BigDecimal(0));
		setLocalArea("");
		setStreetNo("");
		setBlockNo("");
		setTelephoneNo("");
		setFlatNo("");
		setEnableContactDataTable(true);
	}
	}
	*//**method is responsible to handle uploaded file**//*
	private InputStream inputStream;
	public void handleFileUpload(FileUploadEvent event) throws IOException, SerialException, SQLException {
		setFile(event.getFile());
		setImageUploadCheck(true);
		setInputStream(event.getFile().getInputstream());
		DocumentImg image = new DocumentImg();
		image.setImage(storeImage(inputStream));
		//CR for sysdate based on application country
		//image.setUploadDate(new Date());
		java.sql.Date tempDate= (java.sql.Date) new Date(getCurrentTime().getTime());
		image.setUploadDate(convertFromSQLDateToJAVADate(tempDate));
		image.setImageName(event.getFile().getFileName());
		image.setImgStatus("1");
		//CR for sysdate based on application country
		//image.setUploadDate(new Date());
		image.setUploadDate(getCurrentTime());
		getBranchpageService().saveImage(image);
		setPkimage(image.getImgId());
	}
	public void addRows() {
		setBooUploadedOrNot(false);
		boolean dublicate= false;
		setRenderIdProofVissibility(false);
	    try {
			for(AddCustomerIdProofBean addCustomerIdProofBean:createProofList) {
				
				if(getIdnumberProof().equals(addCustomerIdProofBean.getIdNumber())  ) {
					dublicate=true;
				
					break;
				} 
				if(getIdTypeproof().equals(addCustomerIdProofBean.getIdTypeId())){
					dublicate=true;
					
					break;
				}
			}
			if(!dublicate) {
			//InputStream inputStream = getFile().getInputstream();
				if(!isImageUploadCheck()){
					
					setUploadFileVisibility(true);
				}else if(getBooIdTypeCheck().equals(true)) {
					setBooIdTypeCheck(true);
				}else{
				
					String fileName = getFile().getFileName();
					
				if(fileName.contains(".doc") || fileName.contains(".docx") || fileName.contains(".jpg") || fileName.contains(".jpeg") || 
									fileName.contains(".png") || fileName.contains(".pdf")) {
				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
				String df = dateformat.format(this.dateExp);
				getFetchContactTypeList();
				
				CreateProofTable createProofTable = new CreateProofTable(this.idFor.toString(), this.idTypeproof.toString(), this.idnumberProof, df, "insert", 0, 
																	idForMap.get(this.idFor), idTypeMap.get(this.idTypeproof), image_id);
				this.idFor.toPlainString(), 
				 this.idTypeproof.toPlainString(), 
				 this.idnumberProof,
			 	df, "insert", 0, idForMap.get(this.idFor),
			 	idTypeMap.get(this.idTypeproof), image_id
				AddCustomerIdProofBean addCustomerIdProofBean = new AddCustomerIdProofBean();
				addCustomerIdProofBean.setIdForName(idForMap.get(getIdFor()));
				addCustomerIdProofBean.setIdForId(getIdFor());
				addCustomerIdProofBean.setIdTypeName(idTypeMap.get(getIdTypeproof()));
				addCustomerIdProofBean.setIdTypeId(getIdTypeproof());
				addCustomerIdProofBean.setIdNumber(getIdnumberProof());
				addCustomerIdProofBean.setIdExpiryDate(df);
				addCustomerIdProofBean.setImageId(getPkimage());
				createProofList.add(addCustomerIdProofBean);
				
				if(createProofList.size()>1){
					
					setBooVerifyAllRen(true);
					
				}else{
					
					setBooVerifyAllRen(false);
					
					
				}
		
				setEnablePTbl(true);
				
				//setExpDate(null);
				setDateExp(null);
				setIdTypeproof(null);
				setIdnumberProof("");
				setIdFor(null);
				setImageUploadCheck(false);
				setUploadFileVisibility(false);
				setBooIdTypeCheck(false);
				}else {	
					setUploadFileVisibility(true);
				 }
		        }	
			}else {
				setRenderIdProofVissibility(true);
			}
	} catch (Exception e) {
		e.printStackTrace();

	}
	}
	public void removeContactDetail(AddContactDetailBean bean) {
	
	contactList.remove(bean);
	if (bean.getContactDetailsId() != new BigDecimal(0)) {
	contactListDelete.add(bean);
	}
	}
	public void removeIdProof(AddCustomerIdProofBean proofTable) {
		createProofList.remove(proofTable);
		if (proofTable.getCustomerIdProofId() != new BigDecimal(0)) {	
		createProofListDelete.add(proofTable);
		}
		
		if(createProofList.size() == 0) {
			setBooVerifyAllRen(false);
			setApproved(false);
		}
	}
	
	 * method is responsible to clear contact details
	 * 
	 
	public void clearContactDetails() {
		
		setContactTypeId(new BigDecimal(0));
		setCountryId(new BigDecimal(0));
		setStateId(new BigDecimal(0));
		setDistrictId(new BigDecimal(0));
		setCityId(new BigDecimal(0));
		setLocalArea("");
		setStreetNo("");
		setBlockNo("");
		setTelephoneNo("");
		setFlatNo("");
	}
	
	 * method is responsible to display customer info
	 * 
	 
	public void backToCustomerInfo() {
	
		setRenderCustomeInfoEditable(true);
		setRenderCustomeInfoLocal(true);
		setRenderCustomerDetails(true);
		setRenderContactDetails(false);
		setRenderContactDetailsMatch(false);
		setRenderIdProof(false);
		setRenderBasicInfo(false);
		setBooContactDetailsDuplicate(false);
		
	}
	public void nextToEmpInfo() {
		
		setCountryId(sessionStateManage.getCountryId());
		setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), getCountryId()));
		
		setRenderBasicInfo(false);
		setRenderCustomerDetails(false);
		setRenderCustomeInfoEditable(false);
		setRenderCustomeInfoLocal(false);
		setRenderContactDetails(false);
		setRenderContactDetailsMatch(false);
		setRenderIdProof(false);
		setSaveUptoPanel(3);
		setRenderEmployeementInfo(true);
		setBooContactDetailsDuplicate(false);
	}
	public void bactToContactMatch() {
		setRenderCustomeInfoEditable(false);
		setRenderCustomeInfoLocal(false);
		setRenderCustomerDetails(false);
		setRenderContactDetails(false);
		setRenderContactDetailsMatch(true);
		setRenderIdProof(false);
		setRenderBasicInfo(false);
		setRenderEmployeementInfo(false);
		
	}
	
	 * method is responsible to display scan document
	 * 
	 
	public void nextToScanDoc() {
		
		setRenderBasicInfo(false);
		setRenderCustomerDetails(false);
		setRenderCustomeInfoEditable(false);
		setRenderCustomeInfoLocal(false);
		setRenderContactDetails(false);
		setRenderContactDetailsMatch(false);
		setRenderEmployeementInfo(false);
		setRenderIdProof(true);
	}
	public void backTOContact() {
	
		setRenderBasicInfo(false);
		setRenderCustomerDetails(false);
		setRenderCustomeInfoEditable(false);
		setRenderCustomeInfoLocal(false);
		setRenderContactDetails(false);
		setRenderContactDetailsMatch(true);
		setRenderEmployeementInfo(false);
		
	}
	public void nextToDigital() {
		setRenderBasicInfo(false);
		setRenderCustomerDetails(false);
		setRenderCustomeInfoEditable(false);
		setRenderCustomeInfoLocal(false);
		setRenderContactDetails(false);
		setRenderContactDetailsMatch(false);
		setRenderDigitalSign(true);
	}
	public void bactToEmpInfo() {
		setRenderBasicInfo(false);
		setRenderCustomerDetails(false);
		setRenderCustomeInfoEditable(false);
		setRenderCustomeInfoLocal(false);
		setRenderContactDetails(false);
		setRenderContactDetailsMatch(false);
		setRenderDigitalSign(false);
		setRenderIdProof(false);
		setRenderEmployeementInfo(true);
	}
	
	 * method is responsible to save the customer details 
	 
	public void saveCustomerInfo() throws ParseException {
        customer = new Customer();
        companyMaster = new CompanyMaster();
        langType = new LanguageType();
        countryMaster = new CountryMaster();
        countryMasterForNationality  = new CountryMaster();
        customer.setTokenKey(getTokenNumber());
        countryMaster.setCountryId(getNationalityId());
        countryMasterForNationality.setCountryId(getNationalityId());     
        companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(new BigDecimal(1));
		
		*//**Country ID save*//*
		countryMaster = new CountryMaster();
		countryMaster.setCountryId(sessionStateManage.getCountryId());
		
		
		
		*//**save company*//*
		companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(sessionStateManage.getCompanyId());
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("languageCode")) {
			setLanguageId(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("languageCode").toString().
																			equalsIgnoreCase("ar") ? new BigDecimal(2) : new BigDecimal(1));
		}
        *//**Customer Type*//*
		BizComponentData customerType = new BizComponentData();
		customerType.setComponentDataId(getGeneralService().getComponentId(Constants.CUSTOMERTYPE_INDU, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
		
		*//**TODO Hard coded Group ID*//*
		BizComponentData companyGroup = new BizComponentData();
		companyGroup.setComponentDataId(getGeneralService().getComponentId(Constants.GROUPID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
        langType.setLanguageId(getLanguageId());
        customer.setFsCountryMasterByCountryId(countryMaster);
        customer.setFsCountryMasterByNationality( countryMasterForNationality);
        customer.setFsLanguageType(langType);
        customer.setFsBizComponentDataByCustomerTypeId(customerType);
        //customer.setfsbiz(companyGroup);
        customer.setFsCompanyMaster(companyMaster);
        customer.setFirstName(getFirstName());
        customer.setLastName(getLastName() );
        customer.setFirstNameLocal(getFirstNameL());
        customer.setLastNameLocal(getLastNameL());
        customer.setMiddleName(getMiddleName());
        customer.setMiddleNameLocal(getMiddleNameL());
        customer.setShortName(getShortName());
        customer.setShortNameLocal(getShortNameL());
        customer.setTokenKey(getTokenNumber());
        customer.setGender(getGender());
        
        customer.setDailyLimit(getDailyLimit());
		
		
		customer.setWeeklyLimit(getWeeklyLimit());
		customer.setMontlyLimit(getMonthlyLimit());
		customer.setQuaterlyLimit(getQuarterlyLimit());
		customer.setHalfYearly(getHalfyearly());
		customer.setAnnualLimit(getAnnualLimit());
		customer.setSignatureSpecimen(getDigitalSignature());
        
        if(getBooChangeDob()){
        	
       	 customer.setDateOfBirth(getDateOfBirth());
        	  	
        }else{
        	
        	 customer.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(getShowDob()));	
          	
        }
       
        customer.setMobile(getMobileNo());
        customer.setEmail(getEmail());
        customer.setCustomerId(getCustomerId());
        customer.setTitle(getTitle());
        customer.setTitleLocal(getTitleL());
        customer.setAmlStatus(getAmlStatus());
        //CR for sysdate based on application country
        //customer.setAmlStatusLastUpdated(new Date());
        customer.setAmlStatusLastUpdated(getCurrentTime());
        customer.setAmlStatusUpdatedBy(userName);
        customer.setUpdatedBy(userName);
      //CR for sysdate based on application country
    	//customer.setLastUpdated(new Date());
        customer.setLastUpdated(getCurrentTime());
    	customer.setAlterEmailId(getAlternateEmail());
    	customer.setCreatedBy(getCreatedByCustomer());
    	java.sql.Date tempDate= (java.sql.Date) new Date(getCurrentTime().getTime());
		//image.setUploadDate(convertFromSQLDateToJAVADate(tempDate));
    	customer.setCreationDate(getCreatedDateCustomer());
    	Seeting Article ID
		ArticleDetails details = new ArticleDetails();
		details.setArticleDetailId(getLevel());
		customer.setFsArticleDetails(details);
		IncomeRangeMaster incomeRange = new IncomeRangeMaster();
		incomeRange.setIncomeRangeId(getIncomeRange());
		customer.setFsIncomeRangeMaster(incomeRange);
		
		
       //if(getApproved()){
			customer.setActivatedInd("1");
			//CR for sysdate based on application country
			//customer.setActivatedDate(new Date());
			customer.setActivatedDate(getCurrentTime());
			customer.setVerificationBy(userName);
			//CR for sysdate based on application country
			//customer.setVerificationDate(new Date());
			customer.setVerificationDate(getCurrentTime());
		//} else {
			customer.setActivatedInd("0");
			//CR for sysdate based on application country
			//customer.setInactivatedDate(new Date());
			customer.setInactivatedDate(getCurrentTime());
	//	}
        It will return true always, except the very first time  
        if(getCustomerId() != null) {
        	customer.setCustomerId(getCustomerId());
        	customer.setUpdatedBy(userName);
        	//CR for sysdate based on application country
        	//customer.setLastUpdated(new Date());
        	customer.setLastUpdated(getCurrentTime());
        	
        }
        getBranchpageService().saveOrUpdateVerifiedCustomer(customer);
        customerIdToNominee = customer.getCustomerId();
	}
   public void	saveContactDetails(){
	       customer = new Customer();
	       if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("languageCode")) {
				setLanguageId(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("languageCode").toString().
																				equalsIgnoreCase("ar") ? new BigDecimal(2) : new BigDecimal(1));
			}
	   try {
			for (AddContactDetailBean addContact : contactList) {
				contactDetail = new ContactDetail();
				customer.setCustomerId(getCustomerId());
				contactDetail.setFsCustomer(customer);
				langType = new LanguageType();
				countryMaster = new CountryMaster();
				stateMaster = new StateMaster();
				districtMaster = new DistrictMaster();
				cityMaster = new CityMaster();

				contactType = new ContactType();

				contactType.setContactTypeId(addContact.getContactTypeId());
				BizComponentData contactType = new BizComponentData();
				contactType.setComponentDataId(addContact.getContactTypeId());
				
				countryMaster.setCountryId(addContact.getCountryId());
				stateMaster.setStateId(addContact.getStateId());
				districtMaster.setDistrictId(addContact.getDistrictId());
				cityMaster.setCityId(addContact.getCityId());
				contactDetail.setFsBizComponentDataByContactTypeId(contactType);
				contactDetail.setFsCountryMaster(countryMaster);
				contactDetail.setFsDistrictMaster(districtMaster);
				contactDetail.setFsStateMaster(stateMaster);
				contactDetail.setFsCityMaster(cityMaster);
				langType.setLanguageId(getLanguageId());
				contactDetail.setFsLanguageType(langType);
				contactDetail.setAlterEmailId(getEmail());
				contactDetail.setArea(addContact.getArea());
				contactDetail.setBlock(addContact.getBlock());
				contactDetail.setStreet(addContact.getStreet());
				contactDetail.setFlat(addContact.getFlat());
				contactDetail.setApproved(userName);// doubt
				contactDetail.setTelephone(addContact.getTel());
				contactDetail.setContactDetailId(addContact
						.getContactDetailsId());
				if(contactDetail.getContactDetailId()!=null) {
				contactDetail.setCreatedBy(addContact.getCreatedByContact());
				contactDetail.setCreationDate(addContact.getCreatedDateContact());
				contactDetail.setUpdatedBy(userName);
				//CR for sysdate based on application country
				//contactDetail.setLastUpdated(new Date());
				contactDetail.setLastUpdated(getCurrentTime());
				contactDetail.setActiveStatus("1");
				}else{
					contactDetail.setCreatedBy(contactDetail.getCreatedBy());
					contactDetail.setCreationDate(contactDetail.getCreationDate());
					contactDetail.setActiveStatus("1");
				}
			
				getBranchpageService().saveOrUpdateContact(contactDetail);
			}
			for (AddContactDetailBean addContact : contactListDelete) {
				contactDetail = new ContactDetail();
				customer.setCustomerId(getCustomerId());
				contactDetail.setFsCustomer(customer);
				langType = new LanguageType();
				countryMaster = new CountryMaster();
				stateMaster = new StateMaster();
				districtMaster = new DistrictMaster();
				cityMaster = new CityMaster();
				contactType = new ContactType();
				contactType.setContactTypeId(addContact.getContactTypeId());
				BizComponentData contactType = new BizComponentData();
				contactType.setComponentDataId(addContact.getContactTypeId());
				
				countryMaster.setCountryId(addContact.getCountryId());
				stateMaster.setStateId(addContact.getStateId());
				districtMaster.setDistrictId(addContact.getDistrictId());
				cityMaster.setCityId(addContact.getCityId());
				contactDetail.setFsBizComponentDataByContactTypeId(contactType);
				contactDetail.setFsCountryMaster(countryMaster);
				contactDetail.setFsDistrictMaster(districtMaster);
				contactDetail.setFsStateMaster(stateMaster);
				contactDetail.setFsCityMaster(cityMaster);
				langType.setLanguageId(getLanguageId());
				contactDetail.setFsLanguageType(langType);
				contactDetail.setAlterEmailId(getEmail());
				contactDetail.setArea(addContact.getArea());
				contactDetail.setBlock(addContact.getBlock());
				contactDetail.setStreet(addContact.getStreet());
				contactDetail.setFlat(addContact.getFlat());
				contactDetail.setAlterEmailId(getEmail());// doubt
				contactDetail.setApproved(userName);// doubt
				contactDetail.setTelephone(addContact.getTel());
				contactDetail.setContactDetailId(addContact
						.getContactDetailsId());
				if(contactDetail.getContactDetailId()!=null) {
					contactDetail.setCreatedBy(addContact.getCreatedByContact());
					contactDetail.setCreationDate(addContact.getCreatedDateContact());
					contactDetail.setUpdatedBy(userName);
					
					//contactDetail.setLastUpdated(new Date());
					contactDetail.setActiveStatus("0");
					}
				getBranchpageService().saveOrUpdateContact(contactDetail);	
			}
		} catch (NullPointerException npexp) {
			log.info("contact details ................ ");
		} catch (Exception ioexp) {
			log.info("contact details ................ ");
		}
   }
   public void saveEmployeementInfo() {
	    CustomerEmploymentInfo custEmp = new CustomerEmploymentInfo();
	    if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("languageCode")) {
			setLanguageId(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("languageCode").toString().
																			equalsIgnoreCase("ar") ? new BigDecimal(2) : new BigDecimal(1));
		}
	   // BizComponentData employement = new BizComponentData();
		langType = new LanguageType();
		countryMaster = new CountryMaster();
		stateMaster = new StateMaster();
		districtMaster = new DistrictMaster();
		cityMaster = new CityMaster();
		customer = new Customer();
		customer.setCustomerId(getCustomerId());
		langType.setLanguageId(getLanguageId());
		countryMaster.setCountryId(getEmpInfoCountry());
		stateMaster.setStateId(getEmpInfoState());
		districtMaster.setDistrictId(getEmpInfoDistrict());
		cityMaster.setCityId(getEmpInfoCity());
		custEmp.setFsCustomer(customer);
		custEmp.setFsLanguageType(langType);
		custEmp.setFsCountryMaster(countryMaster);
		custEmp.setFsStateMaster(stateMaster);
		custEmp.setFsDistrictMaster(districtMaster);
		custEmp.setFsCityMaster(cityMaster);
		custEmp.setFsBizComponentDataByEmploymentTypeId(new BizComponentData(getEmpInfoEmploymentTypeId()));
		if(getEmpInfoEmploymentTypeId().intValue() == 53){
			
			custEmp.setFsBizComponentDataByOccupationId(new BizComponentData(new BigDecimal(56)));
			
		}else{
		
		custEmp.setFsBizComponentDataByOccupationId(new BizComponentData(new BigDecimal(getOccupation())));
       }
		custEmp.setEmployerName(getEmployer());
		custEmp.setDepartment(getDepartment());
		custEmp.setArea(getEmpInfoArea());
		custEmp.setBlock(getEmpInfoBlock());
		custEmp.setStreet(getEmpInfoStreet());
		custEmp.setOfficeTelephone(getEmpInfoOfficeTel());
		custEmp.setPostal(getPostalCode());
		custEmp.setCreatedBy(getCreatedByEmployement());
		custEmp.setCreationDate(getCreatedDateEmployement());
		custEmp.setCustEmpInfoId(getCustEmployeementInfoId());
		
		if(getCustEmployeementInfoId()!=null) {
			custEmp.setUpdatedBy(userName);
			//CR for sysdate based on application country
			//custEmp.setLastUpdated(new Date());
			custEmp.setLastUpdated(getCurrentTime());
		}

		*//**Responsible to save*//*
		if(getEmpInfoEmploymentTypeId().intValue() != getGeneralService().getComponentId(Constants.EMPLOYMENTTYPE, 
																sessionStateManage.getLanguageId()).
																getFsBizComponentData().getComponentDataId().intValue()) {
			getBranchpageService().saveOrUpdateEmpInfo(custEmp);
		} 
		
   }
   
   public String saveCustomerIdProof() throws ParseException {
	   if(createProofList.size()>0) {
		   saveCustomerInfo();
		   saveContactDetails();
		   saveEmployeementInfo();
		   
		   if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("languageCode")) {
				setLanguageId(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("languageCode").toString().
																				equalsIgnoreCase("ar") ? new BigDecimal(2) : new BigDecimal(1));
			}
		   BizComponentData customerType= new BizComponentData();
		   customerType.setComponentDataId(new BigDecimal("66"));
			customer = new Customer();
			customer.setCustomerId(getCustomerId());
			BizComponentData idProofFor= new BizComponentData();
			BizComponentData identityType= new BizComponentData();
			langType = new LanguageType();
			langType.setLanguageId(sessionStateManage.getLanguageId());
			for (AddCustomerIdProofBean addCustomerIdProofBean : createProofList){
				CustomerIdProof custProof = new CustomerIdProof();
				custProof.setFsCustomer(customer);
				custProof.setFsLanguageType(langType);
				custProof.setFsBizComponentDataByCustomerTypeId(customerType);
				idProofFor.setComponentDataId(addCustomerIdProofBean.getIdForId());
				custProof.setFsBizComponentDataByIdentityFor(idProofFor);
				identityType.setComponentDataId(addCustomerIdProofBean.getIdTypeId());
				custProof.setFsBizComponentDataByIdentityTypeId(identityType);
				DocumentImg imgDoc = new DocumentImg();
				imgDoc.setImgId(addCustomerIdProofBean.getImageId());
				custProof.setFsDocumentImg(imgDoc);
				custProof.setIdentityInt(addCustomerIdProofBean.getIdNumber());
				custProof.setIdentityStatus("1");
				if (addCustomerIdProofBean.getChecked()) {
					custProof.setApprovedBy(userName);
					//CR for sysdate based on application country
					//custProof.setApprovedDate(new Date());
					custProof.setApprovedDate(getCurrentTime());
				} else {
					custProof.setApprovedBy("0");
					//CR for sysdate based on application country
					//custProof.setApprovedDate(new Date());
					custProof.setApprovedDate(getCurrentTime());
				}
				Date expDate = new SimpleDateFormat("dd/MM/yyyy").parse(addCustomerIdProofBean.getIdExpiryDate());
				custProof.setIdentityExpiryDate(expDate);
				custProof.setCustProofId(addCustomerIdProofBean.getCustomerIdProofId());
	            if(addCustomerIdProofBean.getCustomerIdProofId()!= null) {
	            	custProof.setUpdatedBy(userName);
	            	//CR for sysdate based on application country
	            	//custProof.setLastUpdatedDate(new Date());
	            	custProof.setLastUpdatedDate(getCurrentTime());
	            	custProof.setCreatedBy(addCustomerIdProofBean.getCreatedByIdProof());
	            	custProof.setCreationDate(addCustomerIdProofBean.getCreatedDateIdProof());
	            } else {
	            	custProof.setCreatedBy(userName);
	            	//CR for sysdate based on application country
	    			//custProof.setCreationDate(new Date());
	            	custProof.setCreationDate(getCurrentTime());
	            }
	            getBranchpageService().saveOrUpdateCustomerIdProof(custProof);
			}
			
			for (AddCustomerIdProofBean addCustomerIdProofBean : createProofListDelete){
				CustomerIdProof custProof = new CustomerIdProof();
				custProof.setFsCustomer(customer);
				custProof.setFsLanguageType(langType);
				custProof.setFsBizComponentDataByCustomerTypeId(customerType);
				idProofFor.setComponentDataId(addCustomerIdProofBean.getIdForId());
				custProof.setFsBizComponentDataByIdentityFor(idProofFor);
				identityType.setComponentDataId(addCustomerIdProofBean.getIdTypeId());
				custProof.setFsBizComponentDataByIdentityTypeId(identityType);
				DocumentImg imgDoc = new DocumentImg();
				imgDoc.setImgId(addCustomerIdProofBean.getImageId());
				custProof.setFsDocumentImg(imgDoc);
				custProof.setIdentityInt(addCustomerIdProofBean.getIdNumber());
				custProof.setIdentityStatus("0");
				if (addCustomerIdProofBean.getChecked()) {
					custProof.setApprovedBy(userName);
					//CR for sysdate based on application country
					//custProof.setApprovedDate(new Date());
					custProof.setApprovedDate(getCurrentTime());
				} else {
					custProof.setApprovedBy("0");
					//CR for sysdate based on application country
					//custProof.setApprovedDate(new Date());
					custProof.setApprovedDate(getCurrentTime());
				}
				Date expDate = new SimpleDateFormat("dd/MM/yyyy").parse(addCustomerIdProofBean.getIdExpiryDate());
				custProof.setIdentityExpiryDate(expDate);
				custProof.setCreatedBy(userName);
				//CR for sysdate based on application country
				//custProof.setCreationDate(new Date());
				custProof.setCreationDate(getCurrentTime());
				custProof.setCustProofId(addCustomerIdProofBean.getCustomerIdProofId());
	
				getBranchpageService().saveOrUpdateCustomerIdProof(custProof);
			}
			if(getNominee()) {
				getNomineeRegistration().setNominatorId(customerIdToNominee, "first");
				return "nominee";
			} else {
				try {
					ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
					//context.invalidateSession();
					setRenderIdProof(false);
					setRenderBasicInfo(true);
					setRenderCustomerDetails(false);
					setRenderCustomeInfoEditable(false);
					setRenderCustomeInfoLocal(false);
					setRenderContactDetails(false);
					setRenderContactDetailsMatch(false);
					setRenderEmployeementInfo(false);
					context.redirect("../registration/successverification.xhtml");
					fetchCustomerInfo();
					setStateName(generalService.getStateName(sessionStateManage.getLanguageId(), sessionStateManage.getCountryId(), getEmpInfoState()));
					setDistrictName(generalService.getDistrictName(sessionStateManage.getLanguageId(), sessionStateManage.getCountryId(),getEmpInfoState(), getEmpInfoDistrict()));
					setCityName(generalService.getCityName(sessionStateManage.getLanguageId(), sessionStateManage.getCountryId(), getEmpInfoState(), getEmpInfoDistrict(), getEmpInfoCity()));
					setNationalityName(generalService.getNationalityName(sessionStateManage.getLanguageId(), sessionStateManage.getCountryId()));
					setTitleName(generalService.getTitleName(sessionStateManage.getLanguageId(),new BigDecimal(getTitle())));
					setArticleName(generalService.getArticleName(getArticle()));
					setLevelName(generalService.getLevelName(getLevel()));
					setIncomeRangeName(generalService.getIncomeRangeName(getIncomeRange()));
					setEmplTypeName(generalService.getEmplTypeName(sessionStateManage.getLanguageId(),getEmpInfoEmploymentTypeId()));
					setProfessionName(generalService.getProfessionName(sessionStateManage.getLanguageId(),new BigDecimal(getOccupation())));
					
				} catch(Exception e) {
					log.info("redirect"+e);
				}
				return null;
			}
	   }else {
		   setRenderIdProofVissibility(true);
		   return "";
	   }

	  
   }
   
   public StreamedContent downloadFile(BigDecimal imageId) {
		
	   
	   StreamedContent image = null;
	   try{
		  
		   image = getImage(imageId); 
	   } catch(Exception e){
		   log.info("streamContent"+e);
	   }
	   return image;
	}
   
  public StreamedContent getImage(BigDecimal imageId){
	try{
		if (imageId.toPlainString().length() > 0 && imageId!=null) {
			Blob blob = getBranchpageService().getImage(imageId).get(0).getImage();
			String imageNameWithExtention=getBranchpageService().getImage(imageId).get(0).getImageName();
			String imageExtention=imageNameWithExtention.substring(imageNameWithExtention.lastIndexOf("."));
			String imagename=imageNameWithExtention.substring(0,imageNameWithExtention.lastIndexOf(".")-1);
			int blobLength = (int) blob.length();
			byte[] blobAsBytes = blob.getBytes(1, blobLength);
			InputStream stream = new ByteArrayInputStream(blobAsBytes);
			downloadFile = new DefaultStreamedContent(stream, "image/jpg", imagename  + imageExtention);
		}
	} catch(Exception e){
		log.info("streamContent"+e);
	}
return downloadFile;
}

public Blob storeImage(InputStream inputStream) throws IOException, SerialException, SQLException {
   	return new javax.sql.rowset.serial.SerialBlob(readFully(inputStream));
}

public  byte[] readFully(InputStream input) throws IOException {
	byte[] buffer = new byte[8192];
    int bytesRead;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    
    while ((bytesRead = input.read(buffer)) != -1) {
    	output.write(buffer, 0, bytesRead);
    }
    return output.toByteArray();
}

 public void veryfiAllClick() {
	if(getApproved()){
		for (AddCustomerIdProofBean element : createProofList) {
			element.setChecked(true);
		}
	} else{
		for (AddCustomerIdProofBean element : createProofList) {
			element.setChecked(false);
		}
	}
}
public String getIdnumberProof() {
	return idnumberProof;
}

public void setIdnumberProof(String idnumberProof) {
	this.idnumberProof = idnumberProof;
}
*//**
 * Document Save
 * @throws SerialException
 * @throws IOException
 * @throws SQLException
 *//*
  public void saveDocument(String imagename,BigDecimal customerId,UploadedFile file) throws SerialException, IOException, SQLException{
		Blob blob = storeImage(file);
		int blobLength = (int) blob.length();  
		byte[] blobAsBytes = blob.getBytes(1, blobLength);
		
	
		InputStream stream=new ByteArrayInputStream(blobAsBytes);
		
    downloadFile = new DefaultStreamedContent(stream, "image/jpg", "image.jpg");
    
		*//**
		 * Image Saving Start
		 *//*
	//	DocumentImg document = new DocumentImg();
		try {
			document.setImage(storeImage(file));
		} catch (SerialException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		document.setUploadDate(new Date());
		document.setImageName(imagename);
		if(!isImageSave) {
			try {
				if(document.getImage().length()>0)
					getCorpRegService().updateImage(document, customerId);
				image_id = document.getImgId(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			getCorpRegService().saveImage(document);
			image_id = document.getImgId(); 
			setImage_id(image_id);
		}
		
  }

*//*************************************************Rule Engine Implementation***************************************************//*
*//**
 * This method will call from page, to get the behavior
 * @param componentName
 * @param type
 * @return
 *//*
public String viewBehaviorBean(String componentName, String type){
	if(mapComponentBehavior==null || mapComponentBehavior.size()==0){
		setPageIdIntoSession();
		prepareBehavior();
	}
	return new CollectionUtil().fetchBehavior(mapComponentBehavior, componentName, type);
}

*//**
 * Responsible to get the page ID
 *//*
private void setPageIdIntoSession(){
	String pageName = FacesContext.getCurrentInstance().getViewRoot().getViewId();
	pageName = pageName.substring(pageName.lastIndexOf('/')+1, pageName.indexOf(".xhtml"));
	
	try{
		BigDecimal pageId = getGeneralService().getPageId(pageName);
		new SessionStateManage().setSessionValue("pageId", pageId.toString());
	}catch(Exception e){
		log.info("Page id not found for pagename :: "+pageName+" :: "+e);
	}
}

*//**
 * Responsible to take data and prepare the behavior for each component 
 *//*
private void prepareBehavior(){
	SessionStateManage manage = new SessionStateManage(); 
	List<String> lstComponentName = Arrays.asList("Title",
																			"First Name",
																			"Middle Name",
																			"Last Name",
																			"Short Name",
																			"Local Title",
																			"Local First Name",
																			"Local Middle name",
																			"Local Last Name",
																			"Local Short Name",
																			"Nationality",
																			"Gender",
																			"Mobile No",
																			"Email",
																			"ID Expiary Date",
																			
																			"Employment Type",
																			"occupation",
																			"Employer Name",
																			"Country",
																			"Block",
																			"State",
																			"Street",
																			"District",
																			"Area",
																			"City",
																			"Telephone Number",
																			"Postal Code",
																			"Department",
																			"Alternate Email",
                                                                            "Date of Birth",
																			"Contact Type",
																			"Contact Country",
																			"Contact State",
																			"Contact District",
																			"Contact City",
																			"Contact Local Area",
																			"Contact Street No",
																			"Contact Telephone No",
																			"Contact Flat No",
																			"Contact Block No",
																			"Id Type",
																			"Id Number");
	mapComponentBehavior =  getGeneralService().getComponentBehavior(lstComponentName, manage.getLevel(),manage.getApplicationId(),manage.getCompanyId(),manage.getCountryId(),manage.getPageId());
}



*//*********************************************12/06/2014***************************************************//*
	
	private BigDecimal article = null;
	private BigDecimal level = null;
	private List<ArticleDetails> lstLevel = new ArrayList<ArticleDetails>(); 
	public List<ArticleDetails> getLstLevel() {
		return lstLevel;
	}

	public void setLstLevel(List<ArticleDetails> lstLevel) {
		this.lstLevel = lstLevel;
	}
	private Boolean nominee = false;
	
	@Autowired
	NomineeRegistration nomineeRegistration;
	
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
	
	public Boolean getNominee() {
		return nominee;
	}
	public void setNominee(Boolean nominee) {
		this.nominee = nominee;
	}
	
	*//**
	 * This method will call on on change of Article 
	 *//*
	public void generateLevel() {
		lstLevel = getGeneralService().getLevelData(getArticle());
	}
	
	public List<ArticleMaster> getArticleData() {
		List<ArticleMaster> lstArticles = getGeneralService().getArtilces();
		return lstArticles;
	}
    *//**
     * this method is responsible to clear all value it will call from branch home when user click on new customer info
     *//*
	public void resetValues() {
        setRenderIdNumber(false);
		setRenderIdProof(false);
		setRenderBasicInfo(true);
		setRenderCustomerDetails(false);
		setRenderCustomeInfoEditable(false);
		setRenderCustomeInfoLocal(false);
		setRenderContactDetails(false);
		setRenderContactDetailsMatch(false);
		setRenderEmployeementInfo(false);
		setRenderIdProofVissibility(false);
		setRenderIdNotReg(false);
		customerList.clear();
		contactDetailList.clear();
		contactListDelete.clear();
		contactList.clear();
		customerIdProofList.clear();
		createProofList.clear();
		customerEmploymentInfoList.clear();
		setNominee(false);
		setApproved(false);
		clearBasicInfo();
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../registration/customerinformationverification.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect: "+e);
		}
	}
	*//**
	 * This is responsible to render Panel, depending upon Employment Type selection
	 * @param event
	 *//*
	public void getEmploymentStatus(AjaxBehaviorEvent event) {
		*//***3 means unemployed and 0 means Select*//*
		if(getEmpInfoEmploymentTypeId().intValue() == getGeneralService().getComponentId(Constants.EMPLOYMENTTYPE, sessionStateManage.getLanguageId()).
													getFsBizComponentData().getComponentDataId().intValue()	|| getEmpInfoEmploymentTypeId().intValue() == 0) {
			setBooEmploymentPanel(false);
		} else {
			setBooEmploymentPanel(true);
		}
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public void getIDTypeValue(AjaxBehaviorEvent event) {
		setSelectedIdType(getIdTypeproof());
		setBooIdTypeCheck(false);
		
		if(getIdTypeproof().intValue()==19){
			System.out.println("==============PASSPORT=============");
				Calendar cal = new GregorianCalendar();
				cal.setTime(new Date());
				cal.add(Calendar.DAY_OF_MONTH, +90);
				Date today90 = cal.getTime();

				setDateExp(today90);
			}else{
			Calendar cal = new GregorianCalendar();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, +0);
			Date today90 = cal.getTime();
			setDateExp(today90);

			
			}
	}
	
	public void onblurId(AjaxBehaviorEvent event) {
		
		mapIdentityType = ruleEngine.getComponentData("Identity Type");
		BigDecimal identityTpeId = null; 
		for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			if(entry.getValue().equalsIgnoreCase(Constants.CIVILID)){
				identityTpeId = entry.getKey();
				break;
			}
		}
		if(getSelectedIdType().equals(identityTpeId)){
			
			boolean status = false;
			try{
				String returnString = getGeneralService().getCivilIdStatus(new BigDecimal(getIdnumberProof()));
				if(returnString.equalsIgnoreCase(Constants.Yes)){
					status = true;
				}else{
					status = false;
				}
			}catch(Exception e){
				status = false;
			}
			
			setBooIdTypeCheck(!status);
		} else {
			setBooIdTypeCheck(false);
		}
	}

public void changeGender(){
		
		Map<BigDecimal, String> mapGender = ruleEngine.getComponentData("Gender");
		Map<BigDecimal, String> mapTitle = ruleEngine.getComponentData("Title");
		
		System.out.println(mapGender);
		System.out.println(mapTitle);
		
		if(mapTitle.get(new BigDecimal(title))!=null){
			if(mapTitle.get(new BigDecimal(title)).trim().equalsIgnoreCase("Mr.")){
				for(BigDecimal key:mapGender.keySet()){
					if(mapGender.get(key).equalsIgnoreCase("male")){
						gender = key.toPlainString();
					}
				}
			}else if(mapTitle.get(new BigDecimal(title)).trim().equalsIgnoreCase("Mrs.")){
				for(BigDecimal key:mapGender.keySet()){
					if(mapGender.get(key).equalsIgnoreCase("female")){
						gender = key.toPlainString();
					}
				}
			}else{
				gender = "";
			}
		}else{
			gender = "";
		}
		
	}
	
	public void changeLocalTitle(){
		
		Map<BigDecimal, String> mapGender = ruleEngine.getComponentData("Gender");
		Map<BigDecimal, String> mapTitle = ruleEngine.getComponentData("Title");
		
		System.out.println(mapGender);
		System.out.println(mapTitle);
		
		if(mapGender.get(new BigDecimal(gender))!=null){
			if(mapGender.get(new BigDecimal(gender)).trim().equalsIgnoreCase("Male")){
				for(BigDecimal key:mapTitle.keySet()){
					if(mapTitle.get(key).equalsIgnoreCase("Mr.")){
						title = key.toPlainString();
					}
				}
			}else if(mapGender.get(new BigDecimal(gender)).trim().equalsIgnoreCase("Female")){
				for(BigDecimal key:mapTitle.keySet()){
					if(mapTitle.get(key).equalsIgnoreCase("Mrs.")){
						title = key.toPlainString();
					}
				}
			}else{
				title = "";
			}
		}else{
			title = "";
		}
		
	}
	
	private String successMessage = null;
	public String getSuccessMessage() {
		return getGeneralService().getMessage(sessionStateManage.getCountryId(), sessionStateManage.getLanguageId());
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	
	public void clickOnOK() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("customerinformationverification.xhtml");
	}

	public boolean isBooUploadedOrNot() {
		return booUploadedOrNot;
	}

	public void setBooUploadedOrNot(boolean booUploadedOrNot) {
		this.booUploadedOrNot = booUploadedOrNot;
	}
	
private String countryName = null;
private Boolean booChangeDob;
private Boolean renderDobChangeMessage = false;
	
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
	//private Constants constants = new Constants();
	public void mainSave() throws IOException, ParseException{
	
	if(getBooChangeDob()){
	
		for (AddCustomerIdProofBean data : createProofList) {
		
			if(data.getIdForId().toPlainString().equalsIgnoreCase(constants.CHANGE_DOB)) {
				//dobDocPresent = true;
			//	break;
				saveCustomerIdProof();
			}else{
				setRenderDobChangeMessage(true);
				
			}
		}
	}else{
		saveCustomerIdProof();
		
	}
	
	
	

	//}
	
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
	
	public void generateIncomeRange(){
		
		lstIncomeRange = getGeneralService().getIncomeRange(sessionStateManage.getCountryId(), getLevel());
	}
	
	public void enableDob() throws ParseException{
		System.out.println("dob==="+dateOfBirth);
		if(getBooChangeDob()){
			//setBooReadonly(false);
			setBooDobCalender(true);
			setBooDobInput(false);
			//setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse(getShowDob()));
		}else{
			setBooDobCalender(false);
			setBooDobInput(true);
		}
		
	}
	
	public Boolean getBooReadonly() {
		return booReadonly;
	}

	public void setBooReadonly(Boolean booReadonly) {
		this.booReadonly = booReadonly;
	}



	private Boolean booReadonly = true;
	private Boolean booDobInput= false;
	private Boolean booDobCalender= false;
	private String showDob;
	public Boolean getBooDobInput() {
		return booDobInput;
	}

	public void setBooDobInput(Boolean booDobInput) {
		this.booDobInput = booDobInput;
	}

	public Boolean getBooDobCalender() {
		return booDobCalender;
	}

	public void setBooDobCalender(Boolean booDobCalender) {
		this.booDobCalender = booDobCalender;
	}

	public String getShowDob() {
		return showDob;
	}

	public void setShowDob(String showDob) {
		this.showDob = showDob;
	}
	
	public void ShowFromBranchPage(String civil_id) {
		clearBasicInfo();
		clearContactDetails();
		clearCustomerInfo();
		
		
		setRenderIdNumber(true);
		
		setRenderBasicInfo(true);
	
		setRenderCustomerDetails(false);
		setRenderCustomeInfoEditable(false);
		setRenderCustomeInfoLocal(false);
		setRenderContactDetails(false);
		setRenderContactDetailsMatch(false);
		setRenderEmployeementInfo(false);
		setRenderIdProofVissibility(false);
		setRenderIdNotReg(false);
		customerList.clear();
		contactDetailList.clear();
		contactListDelete.clear();
		contactList.clear();
		customerIdProofList.clear();
		createProofList.clear();
		customerEmploymentInfoList.clear();
		setNominee(false);
		setApproved(false);
		setIdNumber(civil_id);
		
		//setMethodTypeId(constants.MANUAL);
}
	
	//CR Employee Salary Limit Fields
	
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
		
		public void comparingDailyWeekly(FacesContext context, UIComponent component, Object value){
			BigDecimal weeklyValue=(BigDecimal)value;
			System.out.println("!!!!!!!"+weeklyValue);
			System.out.println(" getDailyLimit : "+getDailyLimit()+" weeklyValue : "+weeklyValue);
			if(getDailyLimit()==null || weeklyValue==null){
				FacesMessage msg = new FacesMessage("Please Enter Daily and Then Weekly","Please Enter Daily and Then Weekly");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}else if(getDailyLimit().compareTo(weeklyValue)>0 || getDailyLimit().compareTo(weeklyValue)==0){
				setWeeklyLimit(null);
				FacesMessage msg = new FacesMessage("Please Enter Greater Than Daily","Please Enter Greater Than Daily");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
			
		}
		
		public void comparingWeeklyMonthly(FacesContext context, UIComponent component, Object value){
			BigDecimal monthlyValue=(BigDecimal)value;
			if(getWeeklyLimit()==null || monthlyValue==null){
				FacesMessage msg = new FacesMessage("Please Enter Weekly and Then Monthly","Please Enter Weekly and Then Monthly");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}else if(getWeeklyLimit().compareTo(monthlyValue)>0 || getWeeklyLimit().compareTo(monthlyValue)==0){
				setMonthlyLimit(null);
				FacesMessage msg = new FacesMessage("Please Enter Greater Than Weekly","Please Enter Greater Than Weekly");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
		}
		
		public void comparingMonthlyQuarterly(FacesContext context, UIComponent component, Object value){
			BigDecimal quarterlyValue=(BigDecimal)value;
			if(getMonthlyLimit()==null || quarterlyValue==null){
				FacesMessage msg = new FacesMessage("Please Enter Monthly and Then Quarterly","Please Enter Monthly and Then Quarterly");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}else if(getMonthlyLimit().compareTo(quarterlyValue)>0 || getMonthlyLimit().compareTo(quarterlyValue)==0){
				setQuarterlyLimit(null);
				FacesMessage msg = new FacesMessage("Please Enter Greater Than Monthly","Please Enter Greater Than Monthly");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
		}
		
		public void comparingQuarterlyHalfYearly(FacesContext context, UIComponent component, Object value){
			BigDecimal halfYearlyValue=(BigDecimal)value;
			if(getQuarterlyLimit()==null || halfYearlyValue==null){
				FacesMessage msg = new FacesMessage("Please Enter Quarterly and Then Half Yearly","Please Enter Quarterly and Then Half Yearly");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}else if(getQuarterlyLimit().compareTo(halfYearlyValue)>0 || getQuarterlyLimit().compareTo(halfYearlyValue)==0){
				setHalfyearly(null);
				FacesMessage msg = new FacesMessage("Please Enter Greater Than Quarterly","Please Enter Greater Than Quarterly");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
		}
		
		public void comparingHalfYearlyAnnually(FacesContext context, UIComponent component, Object value){
			BigDecimal annualyValue=(BigDecimal)value;
			System.out.println("!!!!!!!"+annualyValue);
			System.out.println(" getHalfyearly : "+getHalfyearly()+" annualyValue : "+annualyValue);
			if(getHalfyearly()==null || annualyValue==null){
				FacesMessage msg = new FacesMessage("Please Enter Half Yearly and Then Annual","Please Enter Half Yearly and Then Annual");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}else if(getHalfyearly().compareTo(annualyValue)>0 || getHalfyearly().compareTo(annualyValue)==0){
				setAnnualLimit(null);
				FacesMessage msg = new FacesMessage("Please Enter Greater Than HalfYearly","Please Enter Greater Than HalfYearly");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
		}
	
		private Boolean booVerifyAllRen= false;


		public Boolean getBooVerifyAllRen() {
			return booVerifyAllRen;
		}

		public void setBooVerifyAllRen(Boolean booVerifyAllRen) {
			this.booVerifyAllRen = booVerifyAllRen;
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
		
		List<Customer> matchMobile =  new ArrayList<Customer>();
		matchMobile.addAll(getGeneralService().getMobileCheck(sessionStateManage.getCountryId(), getMobileNo()));
			
		if (matchMobile.size()>0) {
			setBooMobilecheck(true);
			
		
		} else {
			
			setBooMobilecheck(false);
		
		}
		
	}

	public void checkEmail() {
		
		List<Customer> matchEmail =  new ArrayList<Customer>();
		matchEmail.addAll(getGeneralService().getEmailCheck(getEmail()));

		
		if (matchEmail.size()>0) {
			setBooEmailCheck(true);
			
		
		} else {
			
			setBooEmailCheck(false);
		
		}
	}
	
	// show the image while verifying
	
	private StreamedContent myImage;

		public StreamedContent getMyImage() {
			return myImage;
		}
		
		public void setMyImage(StreamedContent myImage) {
			this.myImage = myImage;
		}
		
		
		public void verifyValIfNO(AddCustomerIdProofBean createProof){
			createProof.setChecked(false);
			
		}
		
		public void showImage(AddCustomerIdProofBean createProof){
			BigDecimal imageId =  createProof.getImageId();
			
			setMyImage(null);
			InputStream stream=null;
			Blob blob=null;
			System.out.println("!!!!!!!!!!!!! image ID : "+imageId);
				try {
				blob = getBranchpageService().getImage(imageId).get(0).getImage();
				String imageNameWithExtention = getBranchpageService().getImage(imageId).get(0).getImageName();
				String imageExtention = imageNameWithExtention.substring(imageNameWithExtention.lastIndexOf("."));
				String imagename = imageNameWithExtention.substring(0,imageNameWithExtention.lastIndexOf(".") - 1);
				
				System.out.println("imageNameWithExtention!!!!!!!!!!!!"+imageNameWithExtention);
				System.out.println("imageExtention!!!!!!!!!!!!"+imageExtention);
				System.out.println("imagename!!!!!!!!!!!!"+imagename);
				int blobLength = (int) blob.length();
				byte[] blobAsBytes = blob.getBytes(1, blobLength);
				stream = new ByteArrayInputStream(blobAsBytes);
				myImage = new DefaultStreamedContent(stream, "image/jpg", imageNameWithExtention);
				setMyImage(myImage);
				if(createProof.getChecked()){
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('showimage').show();");
				}
		}catch(Exception e){
			System.out.println(e.getMessage()); 		
		}
	}
		

		//for implement Digital Signature
		  
		  private String digitalSignature=null;
		  private String signatureSpecimen;
		  private Boolean signaturePanelRender=false;
		  private Boolean renderSavebutton=false;
		public String getDigitalSignature() {
			return digitalSignature;
		}

		public void setDigitalSignature(String digitalSignature) {
			this.digitalSignature = digitalSignature;
		}

		public String getSignatureSpecimen() {
			return signatureSpecimen;
		}

		public void setSignatureSpecimen(String signatureSpecimen) {
			this.signatureSpecimen = signatureSpecimen;
		}

		public Boolean getSignaturePanelRender() {
			return signaturePanelRender;
		}

		public void setSignaturePanelRender(Boolean signaturePanelRender) {
			this.signaturePanelRender = signaturePanelRender;
		}

		public Boolean getRenderSavebutton() {
			return renderSavebutton;
		}

		public void setRenderSavebutton(Boolean renderSavebutton) {
			this.renderSavebutton = renderSavebutton;
		}
		
		
		public ICustomerRegistrationBranchService<T> getIcustomerRegistrationService() {
			return icustomerRegistrationService;
		}

		public void setIcustomerRegistrationService(
				ICustomerRegistrationBranchService<T> icustomerRegistrationService) {
			this.icustomerRegistrationService = icustomerRegistrationService;
		}
		
		private final String CUST_REG_RPT_FILE = Constants.CUSTOMER_REG_RPT_FILE;
		private final String CUST_LOG_RPT_FILE = Constants.CUSTOMER_LOG_DIFF_RPT_FILE;

		@Override
		protected String getCompileFileName() {
			List<Customer> custlist = new ArrayList<Customer>();
			custlist = getIcustomerRegistrationService().getVerificationToken(
					getCustomerId());
			String verificationToken = custlist.get(0).getVerificationTokenId();

			if (verificationToken == null) {
				//return CUST_REG_RPT_FILE;
			} else {
				return "";
			}
			return verificationToken;
		}

		@Override
		protected Map<String, Object> getReportParameters() {
			Map<String, Object> reportParameters = new HashMap<String, Object>();

			reportParameters.put("customerId", getCustomerId());

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

		public String getVerifyToken() {
			return verifyToken;
		}

		public void setVerifyToken(String verifyToken) {
			this.verifyToken = verifyToken;
		}

		public void tokenVerify() {
			String token = getVerifyToken();
			List<Customer> custlist = new ArrayList<Customer>();
			custlist = getIcustomerRegistrationService().getVerificationToken(
					getCustomerId());
			String verificationToken = custlist.get(0).getVerificationTokenId();

			if (verificationToken.equals(token)) {
				RequestContext.getCurrentInstance().execute("validToken.show();");
			} else {
				RequestContext.getCurrentInstance().execute("invalidToken.show();");
			}

		} 
		  
		public static java.util.Date convertFromSQLDateToJAVADate(
	            java.sql.Date sqlDate) {
	        java.util.Date javaDate = null;
	        if (sqlDate != null) {
	            javaDate = new Date(sqlDate.getTime());
	        }
	        return sqlDate;
	    }
}

	


*/