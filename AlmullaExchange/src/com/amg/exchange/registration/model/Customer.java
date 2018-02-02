package com.amg.exchange.registration.model;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.CustomerBank;
import com.amg.exchange.remittance.model.Remittance;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.treasury.deal.supplier.model.TreasuryCustomerSupplier;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.Deal;
import com.amg.exchange.treasury.model.Nominee;
import com.amg.exchange.treasury.model.SpecialDeal;

/*******************************************************************************************************************

		 File		: Customer.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 5:03:37 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-JAN-2015 
 						By		: Nazish Ehsan Hashmi
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/

@Entity
@Table(name = "FS_CUSTOMER" )
public class Customer implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal customerId;
	private BizComponentData fsBizComponentDataByCustomerTypeId;
	private CompanyMaster fsCompanyMaster;
	private BizComponentData fsBizComponentDataByGroupId;
	private BizComponentData fsBizComponentDataByObjectiveId;
	private LanguageType fsLanguageType;
	private CountryMaster fsCountryMasterByNationality;
	private CountryMaster fsCountryMasterByCountryId;
	private String shortName;
	private String shortNameLocal;
	private String amlStatus;
	private BigDecimal numberOfHits;
	private String verificationBy;
	private Date verificationDate;
	private String amlStatusUpdatedBy;
	private Date amlStatusLastUpdated;
	private BigDecimal branchCode;
	private String activatedInd;
	private Date activatedDate;
	private Date inactivatedDate;
	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String titleLocal;
	private String firstNameLocal;
	private String middleNameLocal;
	private String lastNameLocal;
	private String gender;
	private Date dateOfBirth;
	private String alterEmailId;
	private String mobile;
	private String signatureSpecimen;
	private String fingerPrintImg;
	private String introducedBy;
	private String medicalInsuranceInd;
	private String companyName;
	private String companyNameLocal;
	private String email;
	private String crNo;
	private String placeOfBirth;
	private String countryOfBirth;
	private String fatherName;
	private String createdBy;
	private String updatedBy;
	private Date creationDate;
	private Date lastUpdated;
	private String tokenKey;
	private String contactPerson;
	private BigDecimal contactNumber;
	private ArticleDetails fsArticleDetails;
	private String isActive;
	private String amlRemarks;
	private String bcoRemarks;
	private String emosCustomer;
	private Date auditStatementDate;
	private BigDecimal auditGrossIncome;
	private Clob signatureSpecimenClob;
	private String remarks; 
	private String sundryDebtorReference;
	private String deactivatedBy;
	private Date deactivatedDate;
	private String civilId;
	
	//otp details
	/*private BigDecimal otpNo;
	private BigDecimal otpRetry;
	private Date otpRetryDate;
	private Date otpVerifiedDate;
	private String otpVerifiedBy;*/
	
	// These are not for java use
	//private BigDecimal identityFor;
	//private BigDecimal identityTypeId;
	//private String identityInt; 
	//private Date identityExpiryDate;
	


	/**
	 * Added following field for CR.
	 * DAILY_LIMIT 
	 * WEEKLY_LIMIT 
	 * MONTHLY_LIMIT 
	 * QUARTERLY_LIMIT
	 * HALF_YEARLY_LIMIT
	 * ANNUAL_LIMIT
	 * 
	 **/
	
	private BigDecimal dailyLimit;
	private BigDecimal weeklyLimit;
	private BigDecimal quaterlyLimit;
	private BigDecimal montlyLimit;
	private BigDecimal halfYearly;
	private BigDecimal annualLimit;
	private String verificationTokenId;
	
	/* Loyalty Ponits added */
	
	private BigDecimal loyaltyPoints;
	private BigDecimal customerReference;
	private String smartCardIndicator;
	
	private Date introducedDate;
	private Date lastTransactionDate;
	private String pepsIndicator;
	private BigDecimal interComcode;
	private BigDecimal interTrnfyr;
	private BigDecimal interTrnref;
	private String kioskPin; 
	private BigDecimal assocod;
	private Date emailVerifiedOn;
	
	private List<CustomerIdProof> fsCustomerIdProofs = new ArrayList<CustomerIdProof>();
	private List<CustomerEmploymentInfo> fsCustomerEmploymentInfos = new ArrayList<CustomerEmploymentInfo>();
	private List<CustomerLogin> fsCustomerLogins = new ArrayList<CustomerLogin>();
	private List<ContactDetail> fsContactDetails = new ArrayList<ContactDetail>();
	private List<CustCorporateAddlDetail> fsCustCorporateAddlDetails = new ArrayList<CustCorporateAddlDetail>();
	private List<Nominee> fsNomineesForNominatorCustId = new ArrayList<Nominee>();
	private List<Nominee> fsNomineesForNomineeCustId = new ArrayList<Nominee>();
	//private List<CustomerIdProof> fsCustomerIdProofsForRefCustomerId = new ArrayList<CustomerIdProof>();
	private Set<SpecialDeal> exSpecialDeals = new HashSet<SpecialDeal>(0);
	private Set<Deal> exDeals = new HashSet<Deal>(0);
	private List<CustomerSpecialDealRequest> customerSpeacialDealReqCustomer = new ArrayList<CustomerSpecialDealRequest>();
	private IncomeRangeMaster fsIncomeRangeMaster;
	private BigDecimal paidupCapital;
	private List<PartnerAuthorized> fsPartnerAuthorizeds = new ArrayList<PartnerAuthorized>();
	private List<PartnerAuthorized> fsPartnerAuthorizedForRefCustomerId = new ArrayList<PartnerAuthorized>();
/*	private List<TreasuryDealDetail> exTreasuryDealDetails = new ArrayList<TreasuryDealDetail>();*/
	
	private Set<TreasuryCustomerSupplier>  dealSupplier = new HashSet<TreasuryCustomerSupplier>();
	private List<SmartCardInfo> fsSmartCard = new ArrayList<SmartCardInfo>();
	private List<BeneficaryRelationship> beneficaryRelationship = new ArrayList<BeneficaryRelationship>();
	private List<SpecialRateRequest> specialRateRequest=new ArrayList<SpecialRateRequest>();
	private Set<Remittance> exRemittance = new HashSet<Remittance>(0);
	private Set<RemittanceApplication> exRemittanceApplication = new HashSet<RemittanceApplication>(0);
	//private Set<RemiittanceApplication> exRemiittanceApplication = new HashSet<RemiittanceApplication>(0);
	private List<CustomerBank> customerBank=new ArrayList<CustomerBank>();
	//private Set<BeneficaryMaster> exBenificiaryMaster = new HashSet<BeneficaryMaster>(0);
	public Customer() {
	}

	public Customer(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public Customer(BigDecimal customerId,
			BizComponentData fsBizComponentDataByCustomerTypeId,
			CompanyMaster fsCompanyMaster,
			BizComponentData fsBizComponentDataByGroupId,
			BizComponentData fsBizComponentDataByObjectiveId,
			LanguageType fsLanguageType,
			CountryMaster fsCountryMasterByNationality,
			CountryMaster fsCountryMasterByCountryId, String shortName,
			String shortNameLocal, String amlStatus, BigDecimal numberOfHits,
			String verificationBy, Date verificationDate,
			String amlStatusUpdatedBy, Date amlStatusLastUpdated,
			BigDecimal branchCode, String activatedInd, Date activatedDate,
			Date inactivatedDate, String title, String firstName,
			String middleName, String lastName, String titleLocal,
			String firstNameLocal, String middleNameLocal,
			String lastNameLocal, String gender, Date dateOfBirth,
			String alterEmailId, String mobile, String signatureSpecimen,
			String fingerPrintImg, String introducedBy,
			String medicalInsuranceInd, String companyName,
			String companyNameLocal, String email, String crNo,
			String placeOfBirth, String countryOfBirth, String fatherName,
			String createdBy, String updatedBy, Date creationDate,
			Date lastUpdated, String tokenKey, String contactPerson,
			BigDecimal contactNumber, ArticleDetails fsArticleDetails,
			String isActive, BigDecimal dailyLimit, BigDecimal weeklyLimit,
			BigDecimal quaterlyLimit, BigDecimal montlyLimit,
			BigDecimal halfYearly, BigDecimal annualLimit,
			String verificationTokenId, BigDecimal loyaltyPoints,
			BigDecimal customerReference, String smartCardIndicator,
			List<CustomerIdProof> fsCustomerIdProofs,
			List<CustomerEmploymentInfo> fsCustomerEmploymentInfos,
			List<CustomerLogin> fsCustomerLogins,
			List<ContactDetail> fsContactDetails,
			List<CustCorporateAddlDetail> fsCustCorporateAddlDetails,
			List<Nominee> fsNomineesForNominatorCustId,
			List<Nominee> fsNomineesForNomineeCustId,
			//List<CustomerIdProof> fsCustomerIdProofsForRefCustomerId,
			Set<SpecialDeal> exSpecialDeals, Set<Deal> exDeals,
			List<CustomerSpecialDealRequest> customerSpeacialDealReqCustomer,
			IncomeRangeMaster fsIncomeRangeMaster, BigDecimal paidupCapital,
			List<PartnerAuthorized> fsPartnerAuthorizeds,
			List<PartnerAuthorized> fsPartnerAuthorizedForRefCustomerId,
			Set<TreasuryCustomerSupplier> dealSupplier,
			List<SmartCardInfo> fsSmartCard,
			List<BeneficaryRelationship> beneficaryRelationship,
			List<SpecialRateRequest> specialRateRequest,
			Set<Remittance> exRemittance,
			Set<RemittanceApplication> exRemittanceApplication,
			//Set<RemiittanceApplication> exRemiittanceApplication,
			List<CustomerBank> customerBank,Date introducedDate,Date lastTransactionDate,String pepsIndicator,
			BigDecimal interComcode,BigDecimal interTrnfyr,BigDecimal interTrnref,String sundryDebtorReference, String emosCustomer,
			Date auditStatementDate, BigDecimal auditGrossIncome,String kioskPin,BigDecimal assocod,Date emailVerifiedOn) {
		super();
		this.customerId = customerId;
		this.fsBizComponentDataByCustomerTypeId = fsBizComponentDataByCustomerTypeId;
		this.fsCompanyMaster = fsCompanyMaster;
		this.fsBizComponentDataByGroupId = fsBizComponentDataByGroupId;
		this.fsBizComponentDataByObjectiveId = fsBizComponentDataByObjectiveId;
		this.fsLanguageType = fsLanguageType;
		this.fsCountryMasterByNationality = fsCountryMasterByNationality;
		this.fsCountryMasterByCountryId = fsCountryMasterByCountryId;
		this.shortName = shortName;
		this.shortNameLocal = shortNameLocal;
		this.amlStatus = amlStatus;
		this.numberOfHits = numberOfHits;
		this.verificationBy = verificationBy;
		this.verificationDate = verificationDate;
		this.amlStatusUpdatedBy = amlStatusUpdatedBy;
		this.amlStatusLastUpdated = amlStatusLastUpdated;
		this.branchCode = branchCode;
		this.activatedInd = activatedInd;
		this.activatedDate = activatedDate;
		this.inactivatedDate = inactivatedDate;
		this.title = title;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.titleLocal = titleLocal;
		this.firstNameLocal = firstNameLocal;
		this.middleNameLocal = middleNameLocal;
		this.lastNameLocal = lastNameLocal;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.alterEmailId = alterEmailId;
		this.mobile = mobile;
		this.signatureSpecimen = signatureSpecimen;
		this.fingerPrintImg = fingerPrintImg;
		this.introducedBy = introducedBy;
		this.medicalInsuranceInd = medicalInsuranceInd;
		this.companyName = companyName;
		this.companyNameLocal = companyNameLocal;
		this.email = email;
		this.crNo = crNo;
		this.placeOfBirth = placeOfBirth;
		this.countryOfBirth = countryOfBirth;
		this.fatherName = fatherName;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.creationDate = creationDate;
		this.lastUpdated = lastUpdated;
		this.tokenKey = tokenKey;
		this.contactPerson = contactPerson;
		this.contactNumber = contactNumber;
		this.fsArticleDetails = fsArticleDetails;
		this.isActive = isActive;
		this.dailyLimit = dailyLimit;
		this.weeklyLimit = weeklyLimit;
		this.quaterlyLimit = quaterlyLimit;
		this.montlyLimit = montlyLimit;
		this.halfYearly = halfYearly;
		this.annualLimit = annualLimit;
		this.verificationTokenId = verificationTokenId;
		this.loyaltyPoints = loyaltyPoints;
		this.customerReference = customerReference;
		this.smartCardIndicator = smartCardIndicator;
		this.fsCustomerIdProofs = fsCustomerIdProofs;
		this.fsCustomerEmploymentInfos = fsCustomerEmploymentInfos;
		this.fsCustomerLogins = fsCustomerLogins;
		this.fsContactDetails = fsContactDetails;
		this.fsCustCorporateAddlDetails = fsCustCorporateAddlDetails;
		this.fsNomineesForNominatorCustId = fsNomineesForNominatorCustId;
		this.fsNomineesForNomineeCustId = fsNomineesForNomineeCustId;
		//this.fsCustomerIdProofsForRefCustomerId = fsCustomerIdProofsForRefCustomerId;
		this.exSpecialDeals = exSpecialDeals;
		this.exDeals = exDeals;
		this.customerSpeacialDealReqCustomer = customerSpeacialDealReqCustomer;
		this.fsIncomeRangeMaster = fsIncomeRangeMaster;
		this.paidupCapital = paidupCapital;
		this.fsPartnerAuthorizeds = fsPartnerAuthorizeds;
		this.fsPartnerAuthorizedForRefCustomerId = fsPartnerAuthorizedForRefCustomerId;
		this.dealSupplier = dealSupplier;
		this.fsSmartCard = fsSmartCard;
		this.beneficaryRelationship = beneficaryRelationship;
		this.specialRateRequest = specialRateRequest;
		this.exRemittance = exRemittance;
		this.exRemittanceApplication = exRemittanceApplication;
		//this.exRemiittanceApplication = exRemiittanceApplication;
		this.customerBank = customerBank;
		this.introducedDate = introducedDate;
		this.lastTransactionDate = lastTransactionDate;
		this.pepsIndicator = pepsIndicator;
		this.interComcode = interComcode;
		this.interTrnfyr = interTrnfyr;
		this.interTrnref = interTrnref;
		this.sundryDebtorReference=sundryDebtorReference;
		this.emosCustomer = emosCustomer;
		this.auditStatementDate=auditStatementDate;
		this.auditGrossIncome=auditGrossIncome;
		this.kioskPin = kioskPin; 
		this.assocod = assocod;
		this.emailVerifiedOn=emailVerifiedOn;
	}

	@Id
	/*@TableGenerator(name="customerid",table="fs_customeridpk",pkColumnName="customeridkey",pkColumnValue="customeridvalue",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="customerid")
	*/
	@GeneratedValue(generator="fs_customer_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_customer_seq" ,sequenceName="FS_CUSTOMER_SEQ",allocationSize=1)
	@Column(name = "CUSTOMER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_TYPE_ID")
	public BizComponentData getFsBizComponentDataByCustomerTypeId() {
		return fsBizComponentDataByCustomerTypeId;
	}

	public void setFsBizComponentDataByCustomerTypeId(BizComponentData fsBizComponentDataByCustomerTypeId) {
		this.fsBizComponentDataByCustomerTypeId = fsBizComponentDataByCustomerTypeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return this.fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID")
	public BizComponentData getFsBizComponentDataByGroupId() {
		return fsBizComponentDataByGroupId;
	}

	public void setFsBizComponentDataByGroupId(BizComponentData fsBizComponentDataByGroupId) {
		this.fsBizComponentDataByGroupId = fsBizComponentDataByGroupId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OBJECTIVE_ID")
	public BizComponentData getFsBizComponentDataByObjectiveId() {
		return fsBizComponentDataByObjectiveId;
	}

	public void setFsBizComponentDataByObjectiveId(BizComponentData fsBizComponentDataByObjectiveId) {
		this.fsBizComponentDataByObjectiveId = fsBizComponentDataByObjectiveId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getFsLanguageType() {
		return this.fsLanguageType;
	}

	public void setFsLanguageType(LanguageType fsLanguageType) {
		this.fsLanguageType = fsLanguageType;
	}

/*	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}*/

	@Column(name = "SHORT_NAME", length = 200)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "SHORT_NAME_LOCAL", length = 200)
	public String getShortNameLocal() {
		return this.shortNameLocal;
	}

	public void setShortNameLocal(String shortNameLocal) {
		this.shortNameLocal = shortNameLocal;
	}

	@Column(name = "AML_STATUS", length = 15)
	public String getAmlStatus() {
		return this.amlStatus;
	}

	public void setAmlStatus(String amlStatus) {
		this.amlStatus = amlStatus;
	}

	@Column(name = "NUMBER_OF_HITS", precision = 10, scale = 0)
	public BigDecimal getNumberOfHits() {
		return this.numberOfHits;
	}

	public void setNumberOfHits(BigDecimal numberOfHits) {
		this.numberOfHits = numberOfHits;
	}

	@Column(name = "VERIFICATION_BY", length = 30)
	public String getVerificationBy() {
		return this.verificationBy;
	}

	public void setVerificationBy(String verificationBy) {
		this.verificationBy = verificationBy;
	}

	@Column(name = "VERIFICATION_DATE")
	public Date getVerificationDate() {
		return this.verificationDate;
	}

	public void setVerificationDate(Date verificationDate) {
		this.verificationDate = verificationDate;
	}

	@Column(name = "AML_STATUS_UPDATED_BY", length = 20)
	public String getAmlStatusUpdatedBy() {
		return this.amlStatusUpdatedBy;
	}

	public void setAmlStatusUpdatedBy(String amlStatusUpdatedBy) {
		this.amlStatusUpdatedBy = amlStatusUpdatedBy;
	}

	@Column(name = "AML_STATUS_LAST_UPDATED")
	public Date getAmlStatusLastUpdated() {
		return this.amlStatusLastUpdated;
	}

	public void setAmlStatusLastUpdated(Date amlStatusLastUpdated) {
		this.amlStatusLastUpdated = amlStatusLastUpdated;
	}

@Column(name = "BRANCH_CODE", precision = 6, scale = 0)
	public BigDecimal getBranchCode() {
		return this.branchCode;
	}

	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}

	@Column(name = "ACTIVATED_IND", length = 1)
	public String getActivatedInd() {
		return this.activatedInd;
	}

	public void setActivatedInd(String activatedInd) {
		this.activatedInd = activatedInd;
	}

	@Column(name = "ACTIVATED_DATE")
	public Date getActivatedDate() {
		return this.activatedDate;
	}

	public void setActivatedDate(Date activatedDate) {
		this.activatedDate = activatedDate;
	}

	@Column(name = "INACTIVATED_DATE")
	public Date getInactivatedDate() {
		return this.inactivatedDate;
	}

	public void setInactivatedDate(Date inactivatedDate) {
		this.inactivatedDate = inactivatedDate;
	}

	@Column(name = "TITLE", length = 10)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "FIRST_NAME", length = 200)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "MIDDLE_NAME", length = 200)
	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Column(name = "LAST_NAME", length = 200)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "TITLE_LOCAL", length = 200)
	public String getTitleLocal() {
		return this.titleLocal;
	}

	public void setTitleLocal(String titleLocal) {
		this.titleLocal = titleLocal;
	}

	@Column(name = "FIRST_NAME_LOCAL", length = 200)
	public String getFirstNameLocal() {
		return this.firstNameLocal;
	}

	public void setFirstNameLocal(String firstNameLocal) {
		this.firstNameLocal = firstNameLocal;
	}

	@Column(name = "MIDDLE_NAME_LOCAL", length = 200)
	public String getMiddleNameLocal() {
		return this.middleNameLocal;
	}

	public void setMiddleNameLocal(String middleNameLocal) {
		this.middleNameLocal = middleNameLocal;
	}

	@Column(name = "LAST_NAME_LOCAL", length = 200)
	public String getLastNameLocal() {
		return this.lastNameLocal;
	}

	public void setLastNameLocal(String lastNameLocal) {
		this.lastNameLocal = lastNameLocal;
	}

	@Column(name = "GENDER", length = 10)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "DATE_OF_BIRTH")
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/*@Column(name = "NATIONALITY", length = 100)
	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}*/

	@Column(name = "ALTER_EMAIL_ID", length = 200)
	public String getAlterEmailId() {
		return this.alterEmailId;
	}

	public void setAlterEmailId(String alterEmailId) {
		this.alterEmailId = alterEmailId;
	}

	@Column(name = "MOBILE", length = 12)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "SIGNATURE_SPECIMEN", length = 4000)
	public String getSignatureSpecimen() {
		return this.signatureSpecimen;
	}

	public void setSignatureSpecimen(String signatureSpecimen) {
		this.signatureSpecimen = signatureSpecimen;
	}

	@Column(name = "FINGER_PRINT_IMG", length = 200)
	public String getFingerPrintImg() {
		return this.fingerPrintImg;
	}

	public void setFingerPrintImg(String fingerPrintImg) {
		this.fingerPrintImg = fingerPrintImg;
	}

	@Column(name = "INTRODUCED_BY", length = 100)
	public String getIntroducedBy() {
		return this.introducedBy;
	}

	public void setIntroducedBy(String introducedBy) {
		this.introducedBy = introducedBy;
	}

	@Column(name = "MEDICAL_INSURANCE_IND", length = 100)
	public String getMedicalInsuranceInd() {
		return this.medicalInsuranceInd;
	}

	public void setMedicalInsuranceInd(String medicalInsuranceInd) {
		this.medicalInsuranceInd = medicalInsuranceInd;
	}

	@Column(name = "COMPANY_NAME", length = 200)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "COMPANY_NAME_LOCAL", length = 200)
	public String getCompanyNameLocal() {
		return this.companyNameLocal;
	}

	public void setCompanyNameLocal(String companyNameLocal) {
		this.companyNameLocal = companyNameLocal;
	}

	@Column(name = "EMAIL", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CR_NO", length = 20)
	public String getCrNo() {
		return this.crNo;
	}

	public void setCrNo(String crNo) {
		this.crNo = crNo;
	}

	@Column(name = "PLACE_OF_BIRTH", length = 100)
	public String getPlaceOfBirth() {
		return this.placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	@Column(name = "COUNTRY_OF_BIRTH", length = 100)
	public String getCountryOfBirth() {
		return this.countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	@Column(name = "FATHER_NAME", length = 200)
	public String getFatherName() {
		return this.fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	@Column(name = "CREATED_BY", length = 30)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "UPDATED_BY", length = 30)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "CREATION_DATE")
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "LAST_UPDATED")
	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomer")
	public List<CustomerIdProof> getFsCustomerIdProofs() {
		return this.fsCustomerIdProofs;
	}

	public void setFsCustomerIdProofs(List<CustomerIdProof> fsCustomerIdProofs) {
		this.fsCustomerIdProofs = fsCustomerIdProofs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomer")
	public List<CustomerEmploymentInfo> getFsCustomerEmploymentInfos() {
		return this.fsCustomerEmploymentInfos;
	}

	public void setFsCustomerEmploymentInfos(List<CustomerEmploymentInfo> fsCustomerEmploymentInfos) {
		this.fsCustomerEmploymentInfos = fsCustomerEmploymentInfos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomer")
	public List<CustomerLogin> getFsCustomerLogins() {
		return this.fsCustomerLogins;
	}

	public void setFsCustomerLogins(List<CustomerLogin> fsCustomerLogins) {
		this.fsCustomerLogins = fsCustomerLogins;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomer")
	public List<ContactDetail> getFsContactDetails() {
		return this.fsContactDetails;
	}

	public void setFsContactDetails(List<ContactDetail> fsContactDetails) {
		this.fsContactDetails = fsContactDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomer")
	public List<CustCorporateAddlDetail> getFsCustCorporateAddlDetails() {
		return fsCustCorporateAddlDetails;
	}

	public void setFsCustCorporateAddlDetails(
			List<CustCorporateAddlDetail> fsCustCorporateAddlDetails) {
		this.fsCustCorporateAddlDetails = fsCustCorporateAddlDetails;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NATIONALITY")
	public CountryMaster getFsCountryMasterByNationality() {
		return fsCountryMasterByNationality;
	}

	public void setFsCountryMasterByNationality(
			CountryMaster fsCountryMasterByNationality) {
		this.fsCountryMasterByNationality = fsCountryMasterByNationality;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMasterByCountryId() {
		return fsCountryMasterByCountryId;
	}

	public void setFsCountryMasterByCountryId(
			CountryMaster fsCountryMasterByCountryId) {
		this.fsCountryMasterByCountryId = fsCountryMasterByCountryId;
	}
	
	@Column(name = "TOKEN_KEY", length = 50)
	public String getTokenKey() {
		return this.tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}
	
	@Column(name = "CONTACT_PERSON", length = 50)
	public String getContactPerson() {
		return this.contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	
	@Column(name = "CONTACT_NUMBER", precision = 22, scale = 0)
	public BigDecimal getContactNumber() {
		return this.contactNumber;
	}

	public void setContactNumber(BigDecimal contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARTICLE_DETAIL_ID")
	public ArticleDetails getFsArticleDetails() {
		return this.fsArticleDetails;
	}

	public void setFsArticleDetails(ArticleDetails fsArticleDetails) {
		this.fsArticleDetails = fsArticleDetails;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomerByNomineeCustId")
	public List<Nominee> getFsNomineesForNominatorCustId() {
		return fsNomineesForNominatorCustId;
	}

	public void setFsNomineesForNominatorCustId(
			List<Nominee> fsNomineesForNominatorCustId) {
		this.fsNomineesForNominatorCustId = fsNomineesForNominatorCustId;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomerByNomineeCustId")
	public List<Nominee> getFsNomineesForNomineeCustId() {
		return fsNomineesForNomineeCustId;
	}

	public void setFsNomineesForNomineeCustId(
			List<Nominee> fsNomineesForNomineeCustId) {
		this.fsNomineesForNomineeCustId = fsNomineesForNomineeCustId;
	}

/*	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomerByRefCustomerId")
	public List<CustomerIdProof> getFsCustomerIdProofsForRefCustomerId() {
		return fsCustomerIdProofsForRefCustomerId;
	}

	public void setFsCustomerIdProofsForRefCustomerId(
			List<CustomerIdProof> fsCustomerIdProofsForRefCustomerId) {
		this.fsCustomerIdProofsForRefCustomerId = fsCustomerIdProofsForRefCustomerId;
	}*/
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomer")
	public Set<SpecialDeal> getExSpecialDeals() {
		return this.exSpecialDeals;
	}

	public void setExSpecialDeals(Set<SpecialDeal> exSpecialDeals) {
		this.exSpecialDeals = exSpecialDeals;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomer")
	public Set<Deal> getExDeals() {
		return this.exDeals;
	}

	public void setExDeals(Set<Deal> exDeals) {
		this.exDeals = exDeals;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customerSpeacialDealReqCustomer")
	public List<CustomerSpecialDealRequest> getCustomerSpeacialDealReqCustomer() {
		return customerSpeacialDealReqCustomer;
	}

	public void setCustomerSpeacialDealReqCustomer(
			List<CustomerSpecialDealRequest> customerSpeacialDealReqCustomer) {
		this.customerSpeacialDealReqCustomer = customerSpeacialDealReqCustomer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INCOME_RANGE_ID")
	public IncomeRangeMaster getFsIncomeRangeMaster() {
		return fsIncomeRangeMaster;
	}

	public void setFsIncomeRangeMaster(IncomeRangeMaster fsIncomeRangeMaster) {
		this.fsIncomeRangeMaster = fsIncomeRangeMaster;
	}
	@Column(name = "PAID_UP_CAPITAL", length = 50)
	public BigDecimal getPaidupCapital() {
		return paidupCapital;
	}

	public void setPaidupCapital(BigDecimal paidupCapital) {
		this.paidupCapital = paidupCapital;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomer")
	public List<PartnerAuthorized> getFsPartnerAuthorizeds() {
		return fsPartnerAuthorizeds;
	}

	public void setFsPartnerAuthorizeds(List<PartnerAuthorized> fsPartnerAuthorizeds) {
		this.fsPartnerAuthorizeds = fsPartnerAuthorizeds;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomerByRefCustomerId")
	public List<PartnerAuthorized> getFsPartnerAuthorizedForRefCustomerId() {
		return fsPartnerAuthorizedForRefCustomerId;
	}

	public void setFsPartnerAuthorizedForRefCustomerId(
			List<PartnerAuthorized> fsPartnerAuthorizedForRefCustomerId) {
		this.fsPartnerAuthorizedForRefCustomerId = fsPartnerAuthorizedForRefCustomerId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dealSupplierCustomer")
	public Set<TreasuryCustomerSupplier> getDealSupplier() {
		return dealSupplier;
	}

	public void setDealSupplier(Set<TreasuryCustomerSupplier> dealSupplier) {
		this.dealSupplier = dealSupplier;
	}
	@Column(name = "DAILY_TRANSACTION_LIMIT")
	public BigDecimal getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(BigDecimal dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
	@Column(name = "WEEKLY_TRANSACTION_LIMIT")
	public BigDecimal getWeeklyLimit() {
		return weeklyLimit;
	}

	public void setWeeklyLimit(BigDecimal weeklyLimit) {
		this.weeklyLimit = weeklyLimit;
	}
	@Column(name = "QUARTERLY_TRANSACTION_LIMIT")
	public BigDecimal getQuaterlyLimit() {
		return quaterlyLimit;
	}
	public void setQuaterlyLimit(BigDecimal quaterlyLimit) {
		this.quaterlyLimit = quaterlyLimit;
	}
	@Column(name = "MONTHLY_TRANSACTION_LIMIT")
	public BigDecimal getMontlyLimit() {
		return montlyLimit;
	}

	public void setMontlyLimit(BigDecimal montlyLimit) {
		this.montlyLimit = montlyLimit;
	}
	@Column(name = "HALF_YEARLY_TRANSACTION_LIMIT")
	public BigDecimal getHalfYearly() {
		return halfYearly;
	}

	public void setHalfYearly(BigDecimal halfYearly) {
		this.halfYearly = halfYearly;
	}
	@Column(name = "ANNUAL_TRANSACTION_LIMIT")
	public BigDecimal getAnnualLimit() {
		return annualLimit;
	}

	public void setAnnualLimit(BigDecimal annualLimit) {
		this.annualLimit = annualLimit;
	}
	
	@Column(name = "VERIFICATION_TOKEN")
	public String getVerificationTokenId() {
		return verificationTokenId;
	}

	public void setVerificationTokenId(String verificationTokenId) {
		this.verificationTokenId = verificationTokenId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomer")
	public List<SmartCardInfo> getFsSmartCard() {
		return fsSmartCard;
	}

	public void setFsSmartCard(List<SmartCardInfo> fsSmartCard) {
		this.fsSmartCard = fsSmartCard;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customerId")
	public List<BeneficaryRelationship> getBeneficaryRelationship() {
		return beneficaryRelationship;
	}

	public void setBeneficaryRelationship(
			List<BeneficaryRelationship> beneficaryRelationship) {
		this.beneficaryRelationship = beneficaryRelationship;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomer")
	public List<SpecialRateRequest> getSpecialRateRequest() {
		return specialRateRequest;
	}

	public void setSpecialRateRequest(List<SpecialRateRequest> specialRateRequest) {
		this.specialRateRequest = specialRateRequest;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomer")
	public Set<Remittance> getExRemittance() {
		return exRemittance;
	}
	public void setExRemittance(Set<Remittance> exRemittance) {
		this.exRemittance = exRemittance;
	}
	
	@Column(name = "LOYALTY_POINTS")
	public BigDecimal getLoyaltyPoints() {
		return loyaltyPoints;
	}
	public void setLoyaltyPoints(BigDecimal loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomer")
	public Set<RemittanceApplication> getExRemittanceApplication() {
		return exRemittanceApplication;
	}
	public void setExRemittanceApplication(
			Set<RemittanceApplication> exRemittanceApplication) {
		this.exRemittanceApplication = exRemittanceApplication;
	}

	@Column(name = "CUSTOMER_REFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomer")
	public Set<RemiittanceApplication> getExRemiittanceApplication() {
		return exRemiittanceApplication;
	}
	public void setExRemiittanceApplication(
			Set<RemiittanceApplication> exRemiittanceApplication) {
		this.exRemiittanceApplication = exRemiittanceApplication;
	}*/
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCustomer")
	public List<CustomerBank> getCustomerBank() {
		return customerBank;
	}
	public void setCustomerBank(List<CustomerBank> customerBank) {
		this.customerBank = customerBank;
	}

	@Column(name = "SMART_CARD_INDICATOR")
	public String getSmartCardIndicator() {
		return smartCardIndicator;
	}
	public void setSmartCardIndicator(String smartCardIndicator) {
		this.smartCardIndicator = smartCardIndicator;
	}
	
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "INTRODUCED_DATE")
	public Date getIntroducedDate() {
		return introducedDate;
	}
	public void setIntroducedDate(Date introducedDate) {
		this.introducedDate = introducedDate;
	}
	
	@Column(name = "LAST_TRANSACTION_DATE")
	public Date getLastTransactionDate() {
		return lastTransactionDate;
	}
	public void setLastTransactionDate(Date lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}

	@Column(name = "PEPS_INDICATOR")
	public String getPepsIndicator() {
		return pepsIndicator;
	}
	public void setPepsIndicator(String pepsIndicator) {
		this.pepsIndicator = pepsIndicator;
	}

	@Column(name = "INTR_COMCOD", length = 2)
	public BigDecimal getInterComcode() {
		return interComcode;
	}
	public void setInterComcode(BigDecimal interComcode) {
		this.interComcode = interComcode;
	}

	@Column(name = "INTR_TRNFYR", length = 4)
	public BigDecimal getInterTrnfyr() {
		return interTrnfyr;
	}
	public void setInterTrnfyr(BigDecimal interTrnfyr) {
		this.interTrnfyr = interTrnfyr;
	}

	@Column(name = "INTR_TRNREF", length = 8)
	public BigDecimal getInterTrnref() {
		return interTrnref;
	}
	public void setInterTrnref(BigDecimal interTrnref) {
		this.interTrnref = interTrnref;
	}

	@Column(name = "SUNDRY_DEPTOR_REFERENCE")
	public String getSundryDebtorReference() {
		return sundryDebtorReference;
	}
	public void setSundryDebtorReference(String sundryDebtorReference) {
		this.sundryDebtorReference = sundryDebtorReference;
	}
	
	@Column(name = "AML_REMARKS")
	public String getAmlRemarks() {
		return amlRemarks;
	}
	public void setAmlRemarks(String amlRemarks) {
		this.amlRemarks = amlRemarks;
	}
	
	@Column(name = "BCO_REMARKS")
	public String getBcoRemarks() {
		return bcoRemarks;
	}
	public void setBcoRemarks(String bcoRemarks) {
		this.bcoRemarks = bcoRemarks;
	}

	@Column(name = "EMOS_CUST")
	public String getEmosCustomer() {
		return emosCustomer;
	}
	public void setEmosCustomer(String emosCustomer) {
		this.emosCustomer = emosCustomer;
	}

	@Column(name = "AUDIT_STATEMENT_DT")
	public Date getAuditStatementDate() {
		return auditStatementDate;
	}
	public void setAuditStatementDate(Date auditStatementDate) {
		this.auditStatementDate = auditStatementDate;
	}

	@Column(name = "AUDIT_GROSS_INCOME")
	public BigDecimal getAuditGrossIncome() {
		return auditGrossIncome;
	}
	public void setAuditGrossIncome(BigDecimal auditGrossIncome) {
		this.auditGrossIncome = auditGrossIncome;
	}

	@Column(name = "SIGNATURE_SPECIMEN_CLOB")
	public Clob getSignatureSpecimenClob() {
		return signatureSpecimenClob;
	}
	public void setSignatureSpecimenClob(Clob signatureSpecimenClob) {
		this.signatureSpecimenClob = signatureSpecimenClob;
	}
	
	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="KIOSK_PIN")
	public String getKioskPin() {
		return kioskPin;
	}
	public void setKioskPin(String kioskPin) {
		this.kioskPin = kioskPin;
	}
	
	@Column(name="DELETED_USER")
	public String getDeactivatedBy() {
		return deactivatedBy;
	}
	public void setDeactivatedBy(String deactivatedBy) {
		this.deactivatedBy = deactivatedBy;
	}
	
	@Column(name="DELETED_DATE")
	public Date getDeactivatedDate() {
		return deactivatedDate;
	}
	public void setDeactivatedDate(Date deactivatedDate) {
		this.deactivatedDate = deactivatedDate;
	}

	@Column(name="IDENTITY_INT")
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}

	/*@Column(name="IDENTITY_FOR")
	public BigDecimal getIdentityFor() {
		return identityFor;
	}

	public void setIdentityFor(BigDecimal identityFor) {
		this.identityFor = identityFor;
	}

	@Column(name="IDENTITY_TYPE_ID")
	public BigDecimal getIdentityTypeId() {
		return identityTypeId;
	}

	public void setIdentityTypeId(BigDecimal identityTypeId) {
		this.identityTypeId = identityTypeId;
	}

	@Column(name="IDENTITY_EXPIRY_DATE")
	public Date getIdentityExpiryDate() {
		return identityExpiryDate;
	}

	public void setIdentityExpiryDate(Date identityExpiryDate) {
		this.identityExpiryDate = identityExpiryDate;
	}*/

	/*@Column(name = "OTP_NO")
	public BigDecimal getOtpNo() {
		return otpNo;
	}
	public void setOtpNo(BigDecimal otpNo) {
		this.otpNo = otpNo;
	}
	
	@Column(name = "OTP_RETRY")
	public BigDecimal getOtpRetry() {
		return otpRetry;
	}
	public void setOtpRetry(BigDecimal otpRetry) {
		this.otpRetry = otpRetry;
	}
	
	@Column(name = "OTP_RETRY_DATE")
	public Date getOtpRetryDate() {
		return otpRetryDate;
	}
	public void setOtpRetryDate(Date otpRetryDate) {
		this.otpRetryDate = otpRetryDate;
	}
	
	@Column(name = "OTP_VERIFIED_DT")
	public Date getOtpVerifiedDate() {
		return otpVerifiedDate;
	}
	public void setOtpVerifiedDate(Date otpVerifiedDate) {
		this.otpVerifiedDate = otpVerifiedDate;
	}
	
	@Column(name = "OTP_VERIFIED_BY")
	public String getOtpVerifiedBy() {
		return otpVerifiedBy;
	}
	public void setOtpVerifiedBy(String otpVerifiedBy) {
		this.otpVerifiedBy = otpVerifiedBy;
	}*/

	@Column(name = "ASSOCOD")
	public BigDecimal getAssocod() {
		return assocod;
	}
	public void setAssocod(BigDecimal assocod) {
		this.assocod = assocod;
	}
	
	@Column(name = "EMAIL_VERIFIED_ON")
	public Date getEmailVerifiedOn() {
		return emailVerifiedOn;
	}
	public void setEmailVerifiedOn(Date emailVerifiedOn) {
		this.emailVerifiedOn = emailVerifiedOn;
	}
	
	 
	
}
