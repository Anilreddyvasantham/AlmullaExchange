package com.amg.exchange.enquiry.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

import com.amg.exchange.bean.AddContactDetailBean;
import com.amg.exchange.bean.CreateProofTable;
import com.amg.exchange.common.bean.RuleEngine;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.enquiry.service.ICommonEnquiryService;
import com.amg.exchange.registration.model.ArcmateScanMaster;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.ScanIdTypeMaster;
import com.amg.exchange.registration.service.IBranchPageService;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.ReadScanProperties;
import com.amg.exchange.util.SessionStateManage;

@Component("registerCustomerEnquiry")
@Scope("session")
public class RegisterCustomerEnquiry implements Serializable{


	private static final long serialVersionUID = 2266652969563023055L;

	private SessionStateManage session =new SessionStateManage();

	private static final Logger log = Logger.getLogger(RegisterCustomerEnquiry.class);

	// All fields/Property  Names
	private String digitalSignature;
	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String shortName;
	private String title_local;
	private String firstName_Local;
	private String middleName_Local;
	private String lastName_Local;
	private String shortName_Local;
	private String nationality;
	private String gender;
	private String mobileNumber;
	private String email;
	private String alternateEmail;
	private String dateOfBirth;
	private String amlStatus;
	private BigDecimal noOfHits;
	private String customerIntroducedBy;
	private String medicalInsuranceIndicator;
	private String pepIndicator;
	private String artical;
	private String level;
	private String incomeRange;
	private BigDecimal dailyLimit;
	private BigDecimal weeklyLimit;
	private BigDecimal monthlyLimit;
	private BigDecimal quarterlyLimit;
	private BigDecimal halfyearlyLimit;
	private BigDecimal annualLimit;
	private String employeeType;
	private String profession;
	private String employeer;
	private String blockNo;
	private String streetNo;
	private String area;
	private String telephoneNo;
	private String state;
	private String district;
	private String city;
	private String postalCode;
	private String department;



	//FOR PANEL RENDERING 
	private Boolean customerInformationPanel=false;
	private Boolean contactDetailsPanel=false;
	private Boolean employeementDetailPanel=false;
	private Boolean ifUnemployeementPanel=false;
	private Boolean idProofPanel=false;
	private Boolean signaturePanel=false;



	private List<AddContactDetailBean> contactDetailList = new ArrayList<AddContactDetailBean>();
	private List<CreateProofTable> customerIdProofList = new ArrayList<CreateProofTable>();

	@Autowired
	ICommonEnquiryService commonEnquiryService;
	@Autowired
	IGeneralService<?> generalService;
	@Autowired
	RuleEngine<?> ruleEngine;
	@Autowired
	IBranchPageService<?> branchPageService;

	@Autowired
	ICustomerRegistrationBranchService<?> icustomerRegistrationService;

	private Map<BigDecimal, String> idTypeMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> idForMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> contactTypMap= new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> employeeTypeMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> occupationMap = new HashMap<BigDecimal, String>();

	public String getMedicalInsuranceIndicator() {
		return medicalInsuranceIndicator;
	}
	public void setMedicalInsuranceIndicator(String medicalInsuranceIndicator) {
		this.medicalInsuranceIndicator = medicalInsuranceIndicator;
	}

	public List<CreateProofTable> getCustomerIdProofList() {
		return customerIdProofList;
	}
	public void setCustomerIdProofList(List<CreateProofTable> customerIdProofList) {
		this.customerIdProofList = customerIdProofList;
	}
	public List<AddContactDetailBean> getContactDetailList() {
		return contactDetailList;
	}
	public void setContactDetailList(List<AddContactDetailBean> contactDetailList) {
		this.contactDetailList = contactDetailList;
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
	public String getTitle_local() {
		return title_local;
	}
	public void setTitle_local(String title_local) {
		this.title_local = title_local;
	}
	public String getFirstName_Local() {
		return firstName_Local;
	}
	public void setFirstName_Local(String firstName_Local) {
		this.firstName_Local = firstName_Local;
	}
	public String getMiddleName_Local() {
		return middleName_Local;
	}
	public void setMiddleName_Local(String middleName_Local) {
		this.middleName_Local = middleName_Local;
	}
	public String getLastName_Local() {
		return lastName_Local;
	}
	public void setLastName_Local(String lastName_Local) {
		this.lastName_Local = lastName_Local;
	}
	public String getShortName_Local() {
		return shortName_Local;
	}
	public void setShortName_Local(String shortName_Local) {
		this.shortName_Local = shortName_Local;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAmlStatus() {
		return amlStatus;
	}
	public void setAmlStatus(String amlStatus) {
		this.amlStatus = amlStatus;
	}
	public BigDecimal getNoOfHits() {
		return noOfHits;
	}
	public void setNoOfHits(BigDecimal noOfHits) {
		this.noOfHits = noOfHits;
	}
	public String getCustomerIntroducedBy() {
		return customerIntroducedBy;
	}
	public void setCustomerIntroducedBy(String customerIntroducedBy) {
		this.customerIntroducedBy = customerIntroducedBy;
	}


	public String getPepIndicator() {
		return pepIndicator;
	}
	public void setPepIndicator(String pepIndicator) {
		this.pepIndicator = pepIndicator;
	}
	public String getArtical() {
		return artical;
	}
	public void setArtical(String artical) {
		this.artical = artical;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getIncomeRange() {
		return incomeRange;
	}
	public void setIncomeRange(String incomeRange) {
		this.incomeRange = incomeRange;
	}
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
	public BigDecimal getHalfyearlyLimit() {
		return halfyearlyLimit;
	}
	public void setHalfyearlyLimit(BigDecimal halfyearlyLimit) {
		this.halfyearlyLimit = halfyearlyLimit;
	}
	public BigDecimal getAnnualLimit() {
		return annualLimit;
	}
	public void setAnnualLimit(BigDecimal annualLimit) {
		this.annualLimit = annualLimit;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getEmployeer() {
		return employeer;
	}
	public void setEmployeer(String employeer) {
		this.employeer = employeer;
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
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	public String getDigitalSignature() {
		return digitalSignature;
	}
	public void setDigitalSignature(String digitalSignature) {
		this.digitalSignature = digitalSignature;
	}
	public Boolean getSignaturePanel() {
		return signaturePanel;
	}
	public void setSignaturePanel(Boolean signaturePanel) {
		this.signaturePanel = signaturePanel;
	}
	public Boolean getCustomerInformationPanel() {
		return customerInformationPanel;
	}
	public void setCustomerInformationPanel(Boolean customerInformationPanel) {
		this.customerInformationPanel = customerInformationPanel;
	}
	public Boolean getContactDetailsPanel() {
		return contactDetailsPanel;
	}
	public void setContactDetailsPanel(Boolean contactDetailsPanel) {
		this.contactDetailsPanel = contactDetailsPanel;
	}
	public Boolean getEmployeementDetailPanel() {
		return employeementDetailPanel;
	}
	public void setEmployeementDetailPanel(Boolean employeementDetailPanel) {
		this.employeementDetailPanel = employeementDetailPanel;
	}
	public Boolean getIfUnemployeementPanel() {
		return ifUnemployeementPanel;
	}
	public void setIfUnemployeementPanel(Boolean ifUnemployeementPanel) {
		this.ifUnemployeementPanel = ifUnemployeementPanel;
	}
	public Boolean getIdProofPanel() {
		return idProofPanel;
	}
	public void setIdProofPanel(Boolean idProofPanel) {
		this.idProofPanel = idProofPanel;
	}


	// for goto contact details screen  
	public void gotoContactDetailPanel(){
		setCustomerInformationPanel(false);
		setContactDetailsPanel(true);
		setEmployeementDetailPanel(false);
		setIdProofPanel(false);
		setSignaturePanel(false);
	}

	public void gotoCustomerInformationPanel(){
		setCustomerInformationPanel(true);
		setContactDetailsPanel(false);
		setEmployeementDetailPanel(false);
		setIdProofPanel(false);
		setSignaturePanel(false);
	}

	public void gotoEmployeementDetailPanel(){
		setCustomerInformationPanel(false);
		setContactDetailsPanel(false);
		setEmployeementDetailPanel(true);
		setIdProofPanel(false);
		setSignaturePanel(false);
	}

	public void gotoIdProofPanel(){
		setCustomerInformationPanel(false);
		setContactDetailsPanel(false);
		setEmployeementDetailPanel(false);
		setIdProofPanel(true);
		setSignaturePanel(false);
	}

	public void gotoSignaturePanel(){
		setCustomerInformationPanel(false);
		setContactDetailsPanel(false);
		setEmployeementDetailPanel(false);
		setIdProofPanel(false);
		setSignaturePanel(true);
	}

	public void cancelFromRegisterCustomerEnquiryPage(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../search/customerenquiry.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void gotoRigisterCustomerEnquiryPage(BigDecimal customerId){
		clearValues();
		getFetchContactTypeList();
		gotoCustomerInformationPanel();
		fetchCustomerDetails(customerId);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/registerCustomerEnquiry.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	// to fetch customer data based on Customer id
	public void fetchCustomerDetails(BigDecimal customerId){
		String state=null;
		String district=null;
		String city=null;
		try {
			// Customer Information
			List<Customer> customerList = commonEnquiryService.getallCustomer(customerId);
			if(customerList.size()>0){
				//setDigitalSignature(customerList.get(0).getSignatureSpecimen());

				if(customerList.get(0).getSignatureSpecimenClob() != null){
					setDigitalSignature(customerList.get(0).getSignatureSpecimenClob().getSubString(1, (int) customerList.get(0).getSignatureSpecimenClob().length()));
				}


				if(customerList.get(0).getGender() != null && customerList.get(0).getGender().equalsIgnoreCase("Male")){
					setTitle(Constants.TITLE_FOR_MR_NAME);
				}else{
					setTitle(Constants.TITLE_FOR_MRS_NAME);
				}
				setFirstName(customerList.get(0).getFirstName());
				setMiddleName(customerList.get(0).getMiddleName());
				setLastName(customerList.get(0).getLastName());
				setShortName(customerList.get(0).getShortName());
				setTitle_local(customerList.get(0).getTitleLocal());
				setFirstName_Local(customerList.get(0).getFirstNameLocal());
				setMiddleName_Local(customerList.get(0).getMiddleNameLocal());
				setLastName_Local(customerList.get(0).getLastNameLocal());
				setShortName_Local(customerList.get(0).getShortNameLocal());
				String nationality = generalService.getNationalityName(session.getLanguageId(), customerList.get(0).getFsCountryMasterByNationality().getCountryId());
				if(nationality!=null){
					setNationality(nationality);
				}
				setGender(customerList.get(0).getGender());
				setMobileNumber(customerList.get(0).getMobile());
				setEmail(customerList.get(0).getEmail());
				setAlternateEmail(customerList.get(0).getAlterEmailId());
				if(customerList.get(0).getDateOfBirth()!=null){
					setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").format(customerList.get(0).getDateOfBirth()));
				}
				setAmlStatus(customerList.get(0).getAmlStatus());
				setNoOfHits(customerList.get(0).getNumberOfHits());
				setCustomerIntroducedBy(customerList.get(0).getIntroducedBy());

				if(customerList.get(0).getMedicalInsuranceInd()!=null){
					if(customerList.get(0).getMedicalInsuranceInd().equalsIgnoreCase(Constants.No)){
						setMedicalInsuranceIndicator(Constants.NO);
					}else if(customerList.get(0).getMedicalInsuranceInd().equalsIgnoreCase(Constants.Yes)){
						setMedicalInsuranceIndicator(Constants.YES);
					}
				}
				if(customerList.get(0).getPepsIndicator()!=null){
					if(customerList.get(0).getPepsIndicator().equalsIgnoreCase(Constants.No)){
						setPepIndicator(Constants.NO);
					}else if(customerList.get(0).getPepsIndicator().equalsIgnoreCase(Constants.Yes)){
						setPepIndicator(Constants.YES);
					}
				}

				setDailyLimit(customerList.get(0).getDailyLimit());
				setWeeklyLimit(customerList.get(0).getWeeklyLimit());
				setMonthlyLimit(customerList.get(0).getMontlyLimit());
				setQuarterlyLimit(customerList.get(0).getQuaterlyLimit());
				setHalfyearlyLimit(customerList.get(0).getHalfYearly());
				setAnnualLimit(customerList.get(0).getAnnualLimit());
				if(customerList.get(0).getFsArticleDetails() !=null){
					String artical = commonEnquiryService.getArticalName(customerList.get(0).getFsArticleDetails().getFsArticleMaster().getArticleId(),session.getLanguageId());
					//if(artical!=null){
					setArtical(artical);
					//}
					String level = commonEnquiryService.getlevelName(customerList.get(0).getFsArticleDetails().getArticleDetailId(),session.getLanguageId());
					//if(level!=null){
					setLevel(level);
				}
				if(customerList.get(0).getFsIncomeRangeMaster()!=null){
					String incomeRange = commonEnquiryService.getIncomeRangeName(customerList.get(0).getFsIncomeRangeMaster().getIncomeRangeId());
					//if(incomeRange!=null){
					setIncomeRange(incomeRange);
				}

			}

			//Customer Employee Information
			List<CustomerEmploymentInfo> employeeDetailList = commonEnquiryService.getEmployeementDetails(customerId);
			if(employeeDetailList.size()>0){
				setEmployeeType(employeeTypeMap.get(employeeDetailList.get(0).getFsBizComponentDataByEmploymentTypeId().getComponentDataId()));
				setProfession(occupationMap.get(employeeDetailList.get(0).getFsBizComponentDataByOccupationId().getComponentDataId()));
				if(employeeDetailList.get(0).getFsBizComponentDataByEmploymentTypeId().getComponentDataId().intValue()== 222){
					setIfUnemployeementPanel(false);
				}else{
					setIfUnemployeementPanel(true);
				}
				setEmployeer(employeeDetailList.get(0).getEmployerName());
				setBlockNo(employeeDetailList.get(0).getBlock());
				setStreetNo(employeeDetailList.get(0).getStreet());
				setArea(employeeDetailList.get(0).getArea());
				setTelephoneNo(employeeDetailList.get(0).getOfficeTelephone());
				state = generalService.getStateName(session.getLanguageId(), employeeDetailList.get(0).getFsCountryMaster().getCountryId(), employeeDetailList.get(0).getFsStateMaster().getStateId());
				if(state!=null){
					setState(state);
				}
				district = generalService.getDistrictName(session.getLanguageId(), employeeDetailList.get(0).getFsCountryMaster().getCountryId(), employeeDetailList.get(0).getFsStateMaster().getStateId(), employeeDetailList.get(0).getFsDistrictMaster().getDistrictId());
				if(district!=null){
					setDistrict(district);
				}
				city = generalService.getCityName(session.getLanguageId(), employeeDetailList.get(0).getFsCountryMaster().getCountryId(), employeeDetailList.get(0).getFsStateMaster().getStateId(), employeeDetailList.get(0).getFsDistrictMaster().getDistrictId(), employeeDetailList.get(0).getFsCityMaster().getCityId());
				if(city!=null){
					setCity(city);
				}
				setPostalCode(employeeDetailList.get(0).getPostal());
				setDepartment(employeeDetailList.get(0).getDepartment());
			}

			// Customer ID Proof Information
			List<CustomerIdProof> idProofList = commonEnquiryService.getCustomerIdProofDetails(customerId);
			if(idProofList.size()>0){
				for(CustomerIdProof idProof:idProofList){
					CreateProofTable createProofTable=new CreateProofTable();
					createProofTable.setIdFor(idForMap.get(idProof.getFsBizComponentDataByIdentityFor().getComponentDataId()));
					createProofTable.setIdType(idTypeMap.get(idProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId()));
					createProofTable.setId_number(idProof.getIdentityInt());
					createProofTable.setId_type(idProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId().toPlainString());
					createProofTable.setDate_expiary(new SimpleDateFormat("dd/MM/yyyy").format(idProof.getIdentityExpiryDate()));
					customerIdProofList.add(createProofTable);
				}
			}

			// Customer Contact Details 
			List<ContactDetail> contactList = commonEnquiryService.getContactDetails(customerId);
			if(contactList.size()>0){
				for(ContactDetail contact:contactList){
					AddContactDetailBean addContactDetailBean =new AddContactDetailBean();
					String contactTypeName = contactTypMap.get(contact.getFsBizComponentDataByContactTypeId().getComponentDataId());
					if(contactTypeName!=null){
						addContactDetailBean.setContType(contactTypeName);
					}
					String countryName = generalService.getCountryName(session.getLanguageId(), contact.getFsCountryMaster().getCountryId());
					if(countryName!=null){
						addContactDetailBean.setCountry(countryName);
					}
					state = generalService.getStateName(session.getLanguageId(), contact.getFsCountryMaster().getCountryId(), contact.getFsStateMaster().getStateId());
					if(state!=null){
						addContactDetailBean.setState(state);
					}
					district = generalService.getDistrictName(session.getLanguageId(), contact.getFsCountryMaster().getCountryId(), contact.getFsStateMaster().getStateId(), contact.getFsDistrictMaster().getDistrictId());
					if(district!=null){
						addContactDetailBean.setDist(district);
					}
					try{
						city = generalService.getCityName(session.getLanguageId(), contact.getFsCountryMaster().getCountryId(), contact.getFsStateMaster().getStateId(), contact.getFsDistrictMaster().getDistrictId(), contact.getFsCityMaster().getCityId());
						if(city!=null){
							addContactDetailBean.setCity(city);
						}
					}catch(Exception exception){
						log.error("Exception Occured City Having Null Values");
					}
					addContactDetailBean.setFlat(contact.getFlat());
					addContactDetailBean.setBuildingNo(contact.getBuildingNo());
					addContactDetailBean.setBlock(contact.getBlock());
					addContactDetailBean.setStreet(contact.getStreet());
					addContactDetailBean.setArea(contact.getArea());
					addContactDetailBean.setTel(contact.getTelephone());
					contactDetailList.add(addContactDetailBean);
				}
			}

		} catch (SQLException e) {
			
		}
	}

	//Clear All Values
	public void clearValues(){
		setDigitalSignature(null);
		setTitle(null);
		setFirstName(null);
		setMiddleName(null);
		setLastName(null);
		setShortName(null);
		setTitle_local(null);
		setFirstName_Local(null);
		setMiddleName_Local(null);
		setLastName_Local(null);
		setShortName_Local(null);
		setNationality(null);
		setMobileNumber(null);
		setEmail(null);
		setAlternateEmail(null);
		setDateOfBirth(null);
		setAmlStatus(null);
		setNoOfHits(null);
		setCustomerIntroducedBy(null);
		setMedicalInsuranceIndicator(null);
		setPepIndicator(null);
		setArtical(null);
		setLevel(null);
		setIncomeRange(null);
		setDailyLimit(null);
		setWeeklyLimit(null);
		setMonthlyLimit(null);
		setQuarterlyLimit(null);
		setHalfyearlyLimit(null);
		setAnnualLimit(null);
		setEmployeeType(null);
		setProfession(null);
		setEmployeer(null);
		setBlockNo(null);
		setStreetNo(null);
		setArea(null);
		setTelephoneNo(null);
		setState(null);
		setDistrict(null);
		setCity(null);
		setPostalCode(null);
		setDepartment(null);
		contactDetailList.clear();
		customerIdProofList.clear();
		idTypeMap.clear();
		idForMap.clear();
		contactTypMap.clear();
		employeeTypeMap.clear();
		occupationMap.clear();
	}

	// fetching data from rule engine and putting into a  Map object
	public void getFetchContactTypeList() {
		contactTypMap = ruleEngine.getComponentData("Contact Type");
		idForMap = ruleEngine.getComponentData("ID For");
		idTypeMap = ruleEngine.getComponentData("Identity Type");
		employeeTypeMap = ruleEngine.getComponentData("Employment Type");
		occupationMap = ruleEngine.getComponentData("Occupation");
	}

	/***
	 * 
	 *  add  by Nazish
	 * 
	 */
	@SuppressWarnings("static-access")
	public void viewScan(CreateProofTable createProofTable) {

		StringBuffer urlBuffer = new StringBuffer();

		List<ArcmateScanMaster> arcmateList = icustomerRegistrationService.fetchArcmateMasterData(Constants.VIEW, Constants.BOTH_VIEW);
		List<ScanIdTypeMaster> scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(new BigDecimal(createProofTable.getId_type()));


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
				urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim()).append(arcmateValue.getFieldAssigner().trim()).append(createProofTable.getId_number());
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
		}else{
			RequestContext.getCurrentInstance().execute("arcmateTable.show();");
		}

	}


}
