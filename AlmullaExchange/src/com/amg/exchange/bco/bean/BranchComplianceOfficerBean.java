package com.amg.exchange.bco.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.bco.model.BranchComplianceOfficer;
import com.amg.exchange.bco.service.IBranchComplianceOfficerService;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.finscan.webservice.FNSServicesLookupSoapClient;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.DateUtil;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("unused")
@Component("bco")
@Scope("session")
public class BranchComplianceOfficerBean<T> implements Serializable {

	@Autowired
	IBranchComplianceOfficerService bcoService;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	ICustomerRegistrationBranchService<T> customerService;

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger
			.getLogger(BranchComplianceOfficerBean.class);

	// Properties

	private BigDecimal amlCheckId = null;
	private BigDecimal countryBranchId = null;
	private BigDecimal customerId = null;
	private BigDecimal applicationCountryId = null;
	private String customerReferenceCode = null;
	private String countryBranchCode = null;
	private String amlCode = null;
	private BigDecimal amlNumberOfHits = null;
	private Date amlClearDate = null;
	private String accountYearMonth = null;
	private String isActive = null;
	private String remarks = null;
	private String createdBy = null;
	private Date createdDate = null;
	private String modifiedBy = null;
	private Date modifiedDate = null;
	private String passport = null;
	private String status = null;

	private boolean selected = false;
	private boolean branchRemarksRender = false;
	private boolean branchPassportRender = false;
	private boolean branchStatusRender = false;
	private boolean submitRender = false;
	private boolean pendingDialogRender = false;
	
	private String Errmsg;

	public Map<BigDecimal, String> getCountryBranchList() {
		return countryBranchList;
	}

	public void setCountryBranchList(Map<BigDecimal, String> countryBranchList) {
		this.countryBranchList = countryBranchList;
	}

	// List Declaration
	private List<BranchComplianceDataTable> branchComplianceList = new ArrayList<BranchComplianceDataTable>();
	private List<BranchComplianceDataTable> headOfficeComplianceList = new ArrayList<BranchComplianceDataTable>();
	private List<BranchComplianceDataTable> selectedBranchList;
	private List<BranchComplianceDataTable> selectedHeadOfficeList;
	private List<Customer> customerList = new ArrayList<Customer>();
	List<CountryBranch> countryBranch = new ArrayList<CountryBranch>();

	private Map<BigDecimal, String> idTypeMap = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> countryBranchList = new HashMap<BigDecimal, String>();

	// Create Session
	SessionStateManage session = new SessionStateManage();
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// BCO Navigation
	public void bcoNavigation() {
		branchComplianceList.clear();
		setCountryBranchId(null);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "branchcomplianceofficer.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../bco/branchcomplianceofficer.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Head Office Navigation
	public void hocoNavigation() {
		headOfficeComplianceList.clear();
		setCountryBranchId(null);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "headofficecomplianceofficer.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../bco/headofficecomplianceofficer.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Exit from this module
	public void exit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		}
	}

	// Get Country Branch List
	public List<CountryBranch> getCountryBranch() {
		countryBranch = new ArrayList<CountryBranch>();
		countryBranch.addAll(generalService.getBranchDetails(session
				.getCountryId()));

		for (CountryBranch countryBranch1 : countryBranch) {
			countryBranchList.put(countryBranch1.getCountryBranchId(),
					countryBranch1.getBranchName());
		}
		return countryBranch;
	}

	public void setCountryBranch(List<CountryBranch> countryBranch) {
		this.countryBranch = countryBranch;
	}

	public void onchangeEnabled() {
		System.out.println("sssssssssssssss==" + isSelected());
	}

	// Branch Office Complaint List
	public List<BranchComplianceDataTable> getBranchList() {

		branchComplianceList.clear();
		BranchComplianceDataTable branchComplianceDataTable = null;

		List<BranchComplianceOfficer> branchComplainceList = bcoService
				.getBranchComplianceList(getCountryBranchId());

		for (BranchComplianceOfficer branchComplianceOfficer : branchComplainceList) {
			branchComplianceDataTable = new BranchComplianceDataTable();
			branchComplianceDataTable.setAmlCheckId(branchComplianceOfficer
					.getAmlCheckId());
			branchComplianceDataTable
					.setCountryBranchId(branchComplianceOfficer
							.getCountryBranchId().getCountryBranchId());
			branchComplianceDataTable.setCustomerId(branchComplianceOfficer
					.getCustomerId().getCustomerId());
			branchComplianceDataTable
					.setApplicationCountryId(branchComplianceOfficer
							.getApplicationCountryId().getCountryId());
			branchComplianceDataTable
					.setCustomerReferenceCode(branchComplianceOfficer
							.getCustomerReferenceCode());
			branchComplianceDataTable.setAmlCode(branchComplianceOfficer
					.getAmlCode());
			branchComplianceDataTable
					.setAmlNumberOfHits(branchComplianceOfficer
							.getAmlNumberOfHits());
			branchComplianceDataTable.setAmlClearDate(branchComplianceOfficer
					.getAmlClearDate());
			branchComplianceDataTable
					.setAccountYearMonth(branchComplianceOfficer
							.getAccountYearMonth());
			branchComplianceDataTable.setIsActive(branchComplianceOfficer
					.getIsActive());
			branchComplianceDataTable.setRemarks(branchComplianceOfficer
					.getRemarks());
			branchComplianceDataTable.setCreatedBy(branchComplianceOfficer
					.getCreatedBy());
			branchComplianceDataTable.setCreatedDate(branchComplianceOfficer
					.getCreatedDate());
			branchComplianceDataTable.setModifiedBy(branchComplianceOfficer
					.getModifiedBy());
			branchComplianceDataTable.setModifiedDate(branchComplianceOfficer
					.getModifiedDate());
			branchComplianceDataTable.setRemarks(branchComplianceOfficer
					.getRemarks());
			branchComplianceDataTable.setCountryId(branchComplianceOfficer
					.getCustomerId().getFsCountryMasterByNationality()
					.getCountryId());
			branchComplianceDataTable.setFirstName(branchComplianceOfficer
					.getCustomerId().getFirstName());

			branchComplianceDataTable.setNationality(generalService
					.getNationalityName(session.getLanguageId(),
							branchComplianceOfficer.getCustomerId()
									.getFsCountryMasterByNationality()
									.getCountryId()));

			List<CustomerIdProof> idlist = customerService
					.getActiveIdProofList(branchComplianceOfficer
							.getCustomerId().getCustomerId());
			List<BizComponentDataDesc> idTypeList = new ArrayList<BizComponentDataDesc>();

			if (idlist.size() > 0) {
				idTypeList = generalService.getComponentNameIndividual(
						session.getLanguageId(), idlist.get(0)
								.getFsBizComponentDataByIdentityTypeId()
								.getComponentDataId());
				branchComplianceDataTable.setIdNumber(idlist.get(0)
						.getIdentityInt());
			}
			if (idTypeList.size() > 0) {
				branchComplianceDataTable.setIdType(idTypeList.get(0)
						.getDataDesc());
			}

			branchComplianceList.add(branchComplianceDataTable);
		}

		return branchComplianceList;
	}

	// Save Branch Compliance
	public void saveBranchCompliance(BranchComplianceDataTable datatable) {

		BranchComplianceOfficer branchComplianceOfficer = null;
		CountryBranch countryBranch = null;
		Customer customer = null;
		CountryMaster countryMaster = null;
		CompanyMaster companyMaster = null;
		LanguageType languageType = null;
		ArticleDetails articleDetail = null;
		IncomeRangeMaster incomeRange = null;

		Calendar calender1 = Calendar.getInstance();
		Date startTime = calender1.getTime();
		long startTimeMilliSecond = startTime.getTime();

		String amlReturnStatus = null;
		String amlStatus = null;
		String amlhits = null;

		try {

			customer = new Customer();
			customer.setCustomerId(datatable.getCustomerId());

			customerList = customerService.getCustomerInfo(customer
					.getCustomerId());

			if (customerList.size() > 0) {

				// Customer information
				countryMaster = new CountryMaster();
				countryMaster.setCountryId(customerList.get(0)
						.getFsCountryMasterByCountryId().getCountryId());

				// Company Id
				companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(customerList.get(0)
						.getFsCompanyMaster().getCompanyId());

				// Language Type
				languageType = new LanguageType();
				languageType.setLanguageId(session.getLanguageId());

				// Customer Type
				BizComponentData customerType = new BizComponentData();
				customerType.setComponentDataId(getGeneralService()
						.getComponentId("Individual",
								languageType.getLanguageId())
						.getFsBizComponentData().getComponentDataId());

				// Group Id
				BizComponentData companyGroup = new BizComponentData();
				companyGroup.setComponentDataId(getGeneralService()
						.getComponentId("Almulla Group",
								languageType.getLanguageId())
						.getFsBizComponentData().getComponentDataId());

				// Nationality
				CountryMaster nationality = new CountryMaster();
				nationality.setCountryId(customerList.get(0)
						.getFsCountryMasterByNationality().getCountryId());

				// Article
				articleDetail = new ArticleDetails();
				articleDetail.setArticleDetailId(customerList.get(0)
						.getFsArticleDetails().getArticleDetailId());

				// Income Range
				incomeRange = new IncomeRangeMaster();
				incomeRange.setIncomeRangeId(customerList.get(0)
						.getFsIncomeRangeMaster().getIncomeRangeId());

				customer.setCustomerId(datatable.getCustomerId());
				customer.setFsCountryMasterByCountryId(countryMaster);
				customer.setFsCompanyMaster(companyMaster);

				customer.setFsBizComponentDataByCustomerTypeId(customerType);
				customer.setFsBizComponentDataByGroupId(companyGroup);
				customer.setFsCountryMasterByNationality(nationality);

				customer.setShortName(customerList.get(0).getShortName());
				customer.setMiddleName(customerList.get(0).getMiddleName());
				customer.setFirstName(customerList.get(0).getFirstName());
				customer.setLastName(customerList.get(0).getLastName());
				customer.setFirstNameLocal(customerList.get(0)
						.getFirstNameLocal());
				customer.setLastNameLocal(customerList.get(0)
						.getLastNameLocal());
				customer.setMiddleNameLocal(customerList.get(0)
						.getMiddleNameLocal());
				customer.setShortNameLocal(customerList.get(0)
						.getShortNameLocal());
				customer.setGender(customerList.get(0).getGender());
				customer.setAmlStatus(customerList.get(0).getAmlStatus());
				customer.setVerificationBy(customerList.get(0)
						.getVerificationBy());
				customer.setVerificationDate(customerList.get(0)
						.getVerificationDate());
				customer.setAmlStatusUpdatedBy(customerList.get(0)
						.getAmlStatusUpdatedBy());
				customer.setAmlStatusLastUpdated(customerList.get(0)
						.getAmlStatusLastUpdated());
				customer.setBranchCode(customerList.get(0).getBranchCode());
				customer.setActivatedInd(customerList.get(0).getActivatedInd());
				customer.setActivatedDate(customerList.get(0)
						.getActivatedDate());
				customer.setInactivatedDate(customerList.get(0)
						.getInactivatedDate());
				customer.setTitle(customerList.get(0).getTitle());
				customer.setDateOfBirth(customerList.get(0).getDateOfBirth());
				customer.setAlterEmailId(customerList.get(0).getAlterEmailId());
				customer.setMobile(customerList.get(0).getMobile());
				customer.setSignatureSpecimen(customerList.get(0)
						.getSignatureSpecimen());
				customer.setFingerPrintImg(customerList.get(0)
						.getFingerPrintImg());
				customer.setIntroducedBy(customerList.get(0).getIntroducedBy());
				customer.setIntroducedDate(customerList.get(0)
						.getIntroducedDate());
				customer.setMedicalInsuranceInd(customerList.get(0)
						.getMedicalInsuranceInd());
				customer.setCompanyName(customerList.get(0).getCompanyName());
				customer.setCompanyNameLocal(customerList.get(0)
						.getCompanyNameLocal());
				customer.setEmail(customerList.get(0).getEmail());
				customer.setCrNo(customerList.get(0).getCrNo());
				customer.setPlaceOfBirth(customerList.get(0).getPlaceOfBirth());
				customer.setCountryOfBirth(customerList.get(0)
						.getCountryOfBirth());
				customer.setFatherName(customerList.get(0).getFatherName());
				customer.setCreatedBy(customerList.get(0).getCreatedBy());
				customer.setCreationDate(customerList.get(0).getCreationDate());
				// customer.setUpdated(customerList.get(0).getu);
				customer.setUpdatedBy(customerList.get(0).getUpdatedBy());
				customer.setLastUpdated(customerList.get(0).getLastUpdated());
				customer.setTokenKey(customerList.get(0).getTokenKey());
				customer.setContactPerson(customerList.get(0)
						.getContactPerson());
				customer.setContactNumber(customerList.get(0)
						.getContactNumber());
				customer.setFsArticleDetails(articleDetail);
				customer.setIsActive(customerList.get(0).getIsActive());
				customer.setFsIncomeRangeMaster(incomeRange);
				customer.setPaidupCapital(customerList.get(0)
						.getPaidupCapital());
				customer.setDailyLimit(customerList.get(0).getDailyLimit());
				customer.setWeeklyLimit(customerList.get(0).getWeeklyLimit());
				customer.setMontlyLimit(customerList.get(0).getMontlyLimit());
				customer.setHalfYearly(customerList.get(0).getHalfYearly());
				customer.setQuaterlyLimit(customerList.get(0)
						.getQuaterlyLimit());
				customer.setAnnualLimit(customerList.get(0).getAnnualLimit());
				customer.setVerificationTokenId(customerList.get(0)
						.getVerificationTokenId());
				customer.setLoyaltyPoints(customerList.get(0)
						.getLoyaltyPoints());
				customer.setCustomerReference(customerList.get(0)
						.getCustomerReference());
				customer.setSmartCardIndicator(customerList.get(0)
						.getSmartCardIndicator());
				customer.setLastTransactionDate(customerList.get(0)
						.getLastTransactionDate());
				customer.setPepsIndicator(customerList.get(0)
						.getPepsIndicator());

				java.util.Date date = new java.util.Date();
				customer.setNumberOfHits(datatable.getAmlNumberOfHits());
				customer.setAmlStatusUpdatedBy(session.getUserName());
				Date currentDate = generalService.getSysDateTimestamp(session
						.getCountryId());
				customer.setAmlStatusLastUpdated(DateUtil
						.getLoginCountryCurrentDate(currentDate));

				// Customer Information end

				branchComplianceOfficer = new BranchComplianceOfficer();
				branchComplianceOfficer
						.setAmlCheckId(datatable.getAmlCheckId());

				countryBranch = new CountryBranch();
				countryBranch
						.setCountryBranchId(datatable.getCountryBranchId());

				countryMaster = new CountryMaster();
				countryMaster.setCountryId(datatable.getApplicationCountryId());

				branchComplianceOfficer.setCountryBranchId(countryBranch);
				branchComplianceOfficer.setAccountYearMonth(datatable
						.getAccountYearMonth());
				branchComplianceOfficer.setCustomerId(customer);
				branchComplianceOfficer.setApplicationCountryId(countryMaster);
				branchComplianceOfficer.setCustomerReferenceCode(datatable
						.getCustomerReferenceCode());

				branchComplianceOfficer.setAmlClearDate(datatable
						.getAmlClearDate());
				branchComplianceOfficer.setIsActive(datatable.getIsActive());
				branchComplianceOfficer.setRemarks(datatable.getRemarks());
				branchComplianceOfficer.setCreatedBy(datatable.getCreatedBy());
				branchComplianceOfficer.setCreatedDate(datatable
						.getCreatedDate());
				branchComplianceOfficer
						.setModifiedBy(datatable.getModifiedBy());
				branchComplianceOfficer.setModifiedDate(datatable
						.getModifiedDate());

				branchComplianceOfficer.setAmlClearDate(new Date());
				branchComplianceOfficer.setAmlClearBy(session.getUserName());
				branchComplianceOfficer.setRemarks(datatable.getRemarks());

				amlReturnStatus = getFinScanServiceStatus(customerList.get(0)
						.getCustomerId());
				// amlReturnStatus="pending-0";

				if (amlReturnStatus == null) {
					RequestContext.getCurrentInstance().execute(
							"amlerror.show();");
				} else {

					String[] parts = amlReturnStatus.split("-");
					amlStatus = parts[0];
					amlhits = parts[1];
					if (amlStatus != null) {
						if (amlStatus
								.equalsIgnoreCase(Constants.FINSCAN_STATUS_PENDING)) {
							// setMessage("pending");
							// waitProcess(100);

							amlReturnStatus = getFinScanServiceStatus(customerList
									.get(0).getCustomerId());
							if (amlReturnStatus == null) {
								RequestContext.getCurrentInstance().execute(
										"amlerror.show();");
							} else {

								String[] parts1 = amlReturnStatus.split("-");
								amlStatus = parts1[0];
								amlhits = parts1[1];

								if (amlStatus != null) {
									if (amlStatus
											.equalsIgnoreCase(Constants.FINSCAN_STATUS_PENDING)) {

										branchComplianceOfficer
												.setAmlCode(Constants.AML_STATUS_ESCALATE);
										branchComplianceOfficer
												.setAmlNumberOfHits(new BigDecimal(
														amlhits));

										// UPDATE AML CHECK
										bcoService
												.saveBranchCompliance(branchComplianceOfficer);

										// UPDATE CUSTOMER INFO
										customer.setAmlStatus(Constants.AML_STATUS_ESCALATE);
										customer.setNumberOfHits(new BigDecimal(
												amlhits));
										customer.setBcoRemarks(getRemarks());
										customerService.saveCustomer(customer);

										branchComplianceOfficer
												.setAmlCheckId(null);
										branchComplianceOfficer
												.setAmlCode(Constants.AML_STATUS_COMP);
										branchComplianceOfficer
												.setAmlNumberOfHits(new BigDecimal(
														amlhits));
										branchComplianceOfficer
												.setAmlClearBy(null);

										// INSERT STATUS TO COMP WITH SAME
										// CUSTOMER
										bcoService
												.saveBranchCompliance(branchComplianceOfficer);
										RequestContext.getCurrentInstance()
												.execute("amlescalate.show();");

									} else if (amlStatus
											.equalsIgnoreCase(Constants.FINSCAN_STATUS_PASS)) {

										branchComplianceOfficer
												.setAmlCode(Constants.AML_STATUS_PASS);
										branchComplianceOfficer
												.setAmlNumberOfHits(new BigDecimal(
														amlhits));

										// UPDATE AML CHECK
										bcoService
												.saveBranchCompliance(branchComplianceOfficer);

										customer.setAmlStatus(Constants.AML_STATUS_PASS);
										customer.setNumberOfHits(new BigDecimal(
												amlhits));

										// UPDATE CUSTOMER INFO
										customerService.saveCustomer(customer);
										RequestContext.getCurrentInstance()
												.execute("amlclear.show();");

									} else {
										RequestContext.getCurrentInstance()
												.execute("amlerror.show();");
									}
								}

							}

						} else if (amlStatus
								.equalsIgnoreCase(Constants.FINSCAN_STATUS_PASS)) {

							branchComplianceOfficer
									.setAmlCode(Constants.AML_STATUS_PASS);
							branchComplianceOfficer
									.setAmlNumberOfHits(new BigDecimal(amlhits));

							// UPDATE AML CHECK
							bcoService
									.saveBranchCompliance(branchComplianceOfficer);

							customer.setAmlStatus(Constants.AML_STATUS_PASS);
							customer.setNumberOfHits(new BigDecimal(amlhits));
							customer.setBcoRemarks(getRemarks());

							// UPDATE CUSTOMER INFO
							customerService.saveCustomer(customer);
							RequestContext.getCurrentInstance().execute(
									"amlclear.show();");
						} else if (amlStatus
								.equalsIgnoreCase(Constants.FINSCAN_STATUS_ERROR)) {
							RequestContext.getCurrentInstance().execute(
									"amlerror.show();");
						}
					} else {
						RequestContext.getCurrentInstance().execute(
								"amlerror.show();");
					}
					
					updateFinscanStatus(datatable.getCustomerId());
				}

			} else {

				RequestContext.getCurrentInstance().execute("amlerror.show();");
			}

		} catch (Throwable e) {
			LOG.error("Exception occured in saveBranchCompliance() method:", e);
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}

	}

	// Head Office Complaint List
	public List<BranchComplianceDataTable> getHeadOfficeList() {

		headOfficeComplianceList.clear();
		BranchComplianceDataTable branchComplianceDataTable = null;

		List<BranchComplianceOfficer> headOfficeList = bcoService
				.getHeadOfficeComplianceList(getCountryBranchId());

		for (BranchComplianceOfficer headOfficeComplianceOfficer : headOfficeList) {
			branchComplianceDataTable = new BranchComplianceDataTable();

			branchComplianceDataTable.setAmlCheckId(headOfficeComplianceOfficer
					.getAmlCheckId());
			branchComplianceDataTable
					.setCountryBranchId(headOfficeComplianceOfficer
							.getCountryBranchId().getCountryBranchId());
			branchComplianceDataTable.setCustomerId(headOfficeComplianceOfficer
					.getCustomerId().getCustomerId());
			branchComplianceDataTable
					.setApplicationCountryId(headOfficeComplianceOfficer
							.getApplicationCountryId().getCountryId());
			branchComplianceDataTable
					.setCustomerReferenceCode(headOfficeComplianceOfficer
							.getCustomerReferenceCode());
			branchComplianceDataTable.setAmlCode(headOfficeComplianceOfficer
					.getAmlCode());
			branchComplianceDataTable
					.setAmlNumberOfHits(headOfficeComplianceOfficer
							.getAmlNumberOfHits());
			branchComplianceDataTable.setAmlClearBy(headOfficeComplianceOfficer
					.getAmlClearBy());
			branchComplianceDataTable
					.setAccountYearMonth(headOfficeComplianceOfficer
							.getAccountYearMonth());
			branchComplianceDataTable.setIsActive(headOfficeComplianceOfficer
					.getIsActive());
			branchComplianceDataTable.setRemarks(headOfficeComplianceOfficer
					.getRemarks());
			branchComplianceDataTable.setCreatedBy(headOfficeComplianceOfficer
					.getCreatedBy());
			branchComplianceDataTable
					.setCreatedDate(headOfficeComplianceOfficer
							.getCreatedDate());
			branchComplianceDataTable.setModifiedBy(headOfficeComplianceOfficer
					.getModifiedBy());
			branchComplianceDataTable
					.setModifiedDate(headOfficeComplianceOfficer
							.getModifiedDate());
			branchComplianceDataTable.setRemarks(headOfficeComplianceOfficer
					.getRemarks());
			branchComplianceDataTable.setCountryId(headOfficeComplianceOfficer
					.getCustomerId().getFsCountryMasterByNationality()
					.getCountryId());
			branchComplianceDataTable.setFirstName(headOfficeComplianceOfficer
					.getCustomerId().getFirstName());
			branchComplianceDataTable.setNationality(generalService
					.getNationalityName(session.getLanguageId(),
							headOfficeComplianceOfficer.getCustomerId()
									.getFsCountryMasterByNationality()
									.getCountryId()));

			List<CustomerIdProof> idlist = customerService
					.getActiveIdProofList(headOfficeComplianceOfficer
							.getCustomerId().getCustomerId());
			List<BizComponentDataDesc> idTypeList = new ArrayList<BizComponentDataDesc>();
			if (idlist.size() > 0) {
				idTypeList = generalService.getComponentNameIndividual(
						session.getLanguageId(), idlist.get(0)
								.getFsBizComponentDataByIdentityTypeId()
								.getComponentDataId());
				branchComplianceDataTable.setIdNumber(idlist.get(0)
						.getIdentityInt());
			}
			if (idTypeList.size() > 0) {
				branchComplianceDataTable.setIdType(idTypeList.get(0)
						.getDataDesc());
			}

			headOfficeComplianceList.add(branchComplianceDataTable);
		}

		return headOfficeComplianceList;

	}

	// Save Head Office Compliance
	public void saveHeadOfficeCompliance(BranchComplianceDataTable datatable) {

		BranchComplianceOfficer branchComplianceOfficer = null;
		CountryBranch countryBranch = null;
		Customer customer = null;
		CountryMaster countryMaster = null;
		CompanyMaster companyMaster = null;
		LanguageType languageType = null;
		ArticleDetails articleDetail = null;
		IncomeRangeMaster incomeRange = null;

		/*
		 * Calendar calender1 = Calendar.getInstance(); Date startTime =
		 * calender1.getTime(); long startTimeMilliSecond = startTime.getTime();
		 */

		String amlReturnStatus = null;
		String amlStatus = null;
		String amlhits = null;
		customerList = new ArrayList<Customer>();

		try {

			customer = new Customer();
			customer.setCustomerId(datatable.getCustomerId());

			customerList = customerService.getCustomerInfo(customer
					.getCustomerId());

			if (customerList.size() > 0) {

				// Customer information
				countryMaster = new CountryMaster();
				countryMaster.setCountryId(customerList.get(0)
						.getFsCountryMasterByCountryId().getCountryId());

				// Company
				companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(customerList.get(0)
						.getFsCompanyMaster().getCompanyId());

				// Language Type
				languageType = new LanguageType();
				languageType.setLanguageId(session.getLanguageId());

				// Customer Type
				BizComponentData customerType = new BizComponentData();
				customerType.setComponentDataId(getGeneralService()
						.getComponentId("Individual",
								languageType.getLanguageId())
						.getFsBizComponentData().getComponentDataId());

				// Group Id
				BizComponentData companyGroup = new BizComponentData();
				companyGroup.setComponentDataId(getGeneralService()
						.getComponentId("Almulla Group",
								languageType.getLanguageId())
						.getFsBizComponentData().getComponentDataId());

				// Nationality
				CountryMaster nationality = new CountryMaster();
				nationality.setCountryId(customerList.get(0)
						.getFsCountryMasterByNationality().getCountryId());

				// Article
				articleDetail = new ArticleDetails();
				articleDetail.setArticleDetailId(customerList.get(0)
						.getFsArticleDetails().getArticleDetailId());

				// Income Range
				incomeRange = new IncomeRangeMaster();
				incomeRange.setIncomeRangeId(customerList.get(0)
						.getFsIncomeRangeMaster().getIncomeRangeId());

				customer.setCustomerId(datatable.getCustomerId());
				customer.setFsCountryMasterByCountryId(countryMaster);
				customer.setFsCompanyMaster(companyMaster);

				customer.setFsBizComponentDataByCustomerTypeId(customerType);
				customer.setFsBizComponentDataByGroupId(companyGroup);
				customer.setFsCountryMasterByNationality(nationality);

				customer.setShortName(customerList.get(0).getShortName());
				customer.setMiddleName(customerList.get(0).getMiddleName());
				customer.setFirstName(customerList.get(0).getFirstName());
				customer.setLastName(customerList.get(0).getLastName());
				customer.setFirstNameLocal(customerList.get(0)
						.getFirstNameLocal());
				customer.setLastNameLocal(customerList.get(0)
						.getLastNameLocal());
				customer.setMiddleNameLocal(customerList.get(0)
						.getMiddleNameLocal());
				customer.setShortNameLocal(customerList.get(0)
						.getShortNameLocal());

				customer.setGender(customerList.get(0).getGender());
				customer.setAmlStatus(customerList.get(0).getAmlStatus());

				customer.setVerificationBy(customerList.get(0)
						.getVerificationBy());
				customer.setVerificationDate(customerList.get(0)
						.getVerificationDate());
				customer.setAmlStatusUpdatedBy(customerList.get(0)
						.getAmlStatusUpdatedBy());
				customer.setAmlStatusLastUpdated(customerList.get(0)
						.getAmlStatusLastUpdated());
				customer.setBranchCode(customerList.get(0).getBranchCode());
				customer.setActivatedInd(customerList.get(0).getActivatedInd());
				customer.setActivatedDate(customerList.get(0)
						.getActivatedDate());
				customer.setInactivatedDate(customerList.get(0)
						.getInactivatedDate());
				customer.setTitle(customerList.get(0).getTitle());
				customer.setDateOfBirth(customerList.get(0).getDateOfBirth());
				customer.setAlterEmailId(customerList.get(0).getAlterEmailId());
				customer.setMobile(customerList.get(0).getMobile());
				customer.setSignatureSpecimen(customerList.get(0)
						.getSignatureSpecimen());
				customer.setFingerPrintImg(customerList.get(0)
						.getFingerPrintImg());
				customer.setIntroducedBy(customerList.get(0).getIntroducedBy());
				customer.setIntroducedDate(customerList.get(0)
						.getIntroducedDate());
				customer.setMedicalInsuranceInd(customerList.get(0)
						.getMedicalInsuranceInd());
				customer.setCompanyName(customerList.get(0).getCompanyName());
				customer.setCompanyNameLocal(customerList.get(0)
						.getCompanyNameLocal());
				customer.setEmail(customerList.get(0).getEmail());
				customer.setCrNo(customerList.get(0).getCrNo());
				customer.setPlaceOfBirth(customerList.get(0).getPlaceOfBirth());
				customer.setCountryOfBirth(customerList.get(0)
						.getCountryOfBirth());
				customer.setFatherName(customerList.get(0).getFatherName());
				customer.setCreatedBy(customerList.get(0).getCreatedBy());
				customer.setCreationDate(customerList.get(0).getCreationDate());
				// customer.setUpdated(customerList.get(0).getu);
				customer.setUpdatedBy(customerList.get(0).getUpdatedBy());
				customer.setLastUpdated(customerList.get(0).getLastUpdated());
				customer.setTokenKey(customerList.get(0).getTokenKey());
				customer.setContactPerson(customerList.get(0)
						.getContactPerson());
				customer.setContactNumber(customerList.get(0)
						.getContactNumber());
				customer.setFsArticleDetails(articleDetail);
				customer.setIsActive(customerList.get(0).getIsActive());
				customer.setFsIncomeRangeMaster(incomeRange);
				customer.setPaidupCapital(customerList.get(0)
						.getPaidupCapital());
				customer.setDailyLimit(customerList.get(0).getDailyLimit());
				customer.setWeeklyLimit(customerList.get(0).getWeeklyLimit());
				customer.setMontlyLimit(customerList.get(0).getMontlyLimit());
				customer.setHalfYearly(customerList.get(0).getHalfYearly());
				customer.setQuaterlyLimit(customerList.get(0)
						.getQuaterlyLimit());
				customer.setAnnualLimit(customerList.get(0).getAnnualLimit());
				customer.setVerificationTokenId(customerList.get(0)
						.getVerificationTokenId());
				customer.setLoyaltyPoints(customerList.get(0)
						.getLoyaltyPoints());
				customer.setCustomerReference(customerList.get(0)
						.getCustomerReference());
				customer.setSmartCardIndicator(customerList.get(0)
						.getSmartCardIndicator());
				customer.setLastTransactionDate(customerList.get(0)
						.getLastTransactionDate());
				customer.setPepsIndicator(customerList.get(0)
						.getPepsIndicator());

				java.util.Date date = new java.util.Date();
				customer.setNumberOfHits(datatable.getAmlNumberOfHits());
				customer.setAmlStatusUpdatedBy(session.getUserName());
				customer.setAmlStatusLastUpdated(new Date());

				// Customer Information end

				branchComplianceOfficer = new BranchComplianceOfficer();
				branchComplianceOfficer
						.setAmlCheckId(datatable.getAmlCheckId());

				countryBranch = new CountryBranch();
				countryBranch
						.setCountryBranchId(datatable.getCountryBranchId());

				countryMaster = new CountryMaster();
				countryMaster.setCountryId(datatable.getApplicationCountryId());

				branchComplianceOfficer.setCountryBranchId(countryBranch);
				branchComplianceOfficer.setAccountYearMonth(datatable
						.getAccountYearMonth());
				branchComplianceOfficer.setCustomerId(customer);
				branchComplianceOfficer.setApplicationCountryId(countryMaster);
				branchComplianceOfficer.setCustomerReferenceCode(datatable
						.getCustomerReferenceCode());
				branchComplianceOfficer.setAmlNumberOfHits(datatable
						.getAmlNumberOfHits());
				branchComplianceOfficer.setAmlClearDate(datatable
						.getAmlClearDate());
				branchComplianceOfficer.setIsActive(datatable.getIsActive());
				branchComplianceOfficer.setRemarks(datatable.getRemarks());
				branchComplianceOfficer.setCreatedBy(datatable.getCreatedBy());
				branchComplianceOfficer.setCreatedDate(datatable
						.getCreatedDate());
				branchComplianceOfficer
						.setModifiedBy(datatable.getModifiedBy());
				branchComplianceOfficer.setModifiedDate(datatable
						.getModifiedDate());

				branchComplianceOfficer.setAmlClearDate(new Date());
				branchComplianceOfficer.setAmlClearBy(session.getUserName());
				branchComplianceOfficer.setRemarks(datatable.getRemarks());

				amlReturnStatus = getFinScanServiceStatus(customer.getCustomerId());
				// AML Status Check here
				if (amlReturnStatus == null) {
					RequestContext.getCurrentInstance().execute(
							"amlerror.show();");
				} else {

					String[] parts = amlReturnStatus.split("-");
					amlStatus = parts[0];
					amlhits = parts[1];
					if (amlStatus
							.equalsIgnoreCase(Constants.FINSCAN_STATUS_FAIL)) {

						branchComplianceOfficer
								.setAmlCode(Constants.AML_STATUS_FAIL);
						branchComplianceOfficer
								.setAmlNumberOfHits(new BigDecimal(amlhits));

						// UPDATE AML CHECK
						bcoService
								.saveBranchCompliance(branchComplianceOfficer);

						// UPDATE CUSTOMER INFO
						customer.setAmlStatus(Constants.AML_STATUS_FAIL);
						customer.setNumberOfHits(new BigDecimal(amlhits));
						customer.setBcoRemarks(getRemarks());
						customerService.saveCustomer(customer);
						RequestContext.getCurrentInstance().execute(
								"amlfail.show();");

					} else if (amlStatus
							.equalsIgnoreCase(Constants.FINSCAN_STATUS_PASS)) {

						branchComplianceOfficer
								.setAmlCode(Constants.AML_STATUS_PASS);
						branchComplianceOfficer
								.setAmlNumberOfHits(new BigDecimal(amlhits));

						// UPDATE AML CHECK
						bcoService
								.saveBranchCompliance(branchComplianceOfficer);

						customer.setAmlStatus(Constants.AML_STATUS_PASS);
						customer.setNumberOfHits(new BigDecimal(amlhits));
						customer.setBcoRemarks(getRemarks());
						// UPDATE CUSTOMER INFO
						customerService.saveCustomer(customer);
						RequestContext.getCurrentInstance().execute(
								"amlclear.show();");
					}
					updateFinscanStatus(datatable.getCustomerId());
				}
			}

		} catch (Throwable e) {
			LOG.error(
					"Exception occured in saveHeadOfficeCompliance() method:",
					e);
			
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}

	}

	
	
	public void getAmlAcceptStatus(BranchComplianceDataTable datatable) {
		
		try{

		String amlReturnStatus = getFinScanServiceStatus(datatable.getCustomerId());
		String[] parts = amlReturnStatus.split("-");
		String amlStatus = parts[0];
		if(amlReturnStatus==null){
			RequestContext.getCurrentInstance().execute("amlerror.show();");
			return;
		}else{
			if (amlStatus.equalsIgnoreCase("pending")) {
				RequestContext.getCurrentInstance().execute("amlpending.show();");
				return;
			} else if (amlStatus.equalsIgnoreCase("pass")) {
				RequestContext.getCurrentInstance().execute("amlpass.show();");
				return;
			} else {
				RequestContext.getCurrentInstance().execute("amlerror.show();");
				return;
			}
		}
		
		}catch(Exception e){
			setErrmsg("Finscan Status:Finscan not connected..");
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}

	}
	
	public void getAmlAcceptStatusHeadOffice(BranchComplianceDataTable datatable) {
		
		try{
		String amlReturnStatus = getFinScanServiceStatus(datatable.getCustomerId());
		String[] parts = amlReturnStatus.split("-");
		String amlStatus = parts[0];
		
		if(amlReturnStatus==null){
			RequestContext.getCurrentInstance().execute("amlerror.show();");
			return;
		}else{
			if (amlStatus.equalsIgnoreCase("pass")) {
				RequestContext.getCurrentInstance().execute("amlpass.show();");
				return;
			} else if (amlStatus.equalsIgnoreCase("fail")) {
				RequestContext.getCurrentInstance().execute("amlfail.show();");
				return;
			} else {
				RequestContext.getCurrentInstance().execute("amlerror.show();");
				return;
			}
		}
		}catch(Exception e){
			setErrmsg("Finscan Status:Finscan not connected..");
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}

	}


	// Get AML Status
	public String getFinScanServiceStatus(BigDecimal customerId) {
		String amlReturnStatus = null;
		String idNumber = null;
		String status = "  ";
		String fullName = null;
		String fullNameLocal = null;
		customerList = new ArrayList<Customer>();
		try {
			FNSServicesLookupSoapClient finscanService = new FNSServicesLookupSoapClient();

			customerList = customerService.getCustomerInfo(customerId);

			if (customerList.size() > 0) {
				fullName = customerList.get(0).getFirstName() + " "
						+ customerList.get(0).getMiddleName() + " "
						+ customerList.get(0).getLastName();
				fullNameLocal = customerList.get(0).getFirstNameLocal() + " "
						+ customerList.get(0).getMiddleNameLocal() + " "
						+ customerList.get(0).getLastNameLocal();

				List<CustomerIdProof> idproofList = customerService
						.getCivilID(customerList.get(0).getCustomerReference());
				if (idproofList.size() > 0) {
					idNumber = idproofList.get(0).getIdentityInt();
				}

				amlReturnStatus = finscanService.amlServiceSearch("Individual",
						customerList.get(0).getGender(), "Active", "Yes",
						"Yes", "Yes", " ", " ", " ", " ", " ", " ", " ",
						"Full Source", "Yes", "Yes", "WC", "World-Check", "  ",
						"  ", "Country", customerList.get(0)
								.getFsCountryMasterByNationality()
								.getCountryId().toString(), "Date of Birth",
						customerList.get(0).getDateOfBirth().toString(),
						"National ID Number", idNumber, "Passport ID", "  ",
						fullName, fullNameLocal, "100061-E", "Test", status);
			}

			System.out.println("AML Status is:" + amlReturnStatus);

		} catch (Throwable e) {

			LOG.error("Aml Check Exception:", e);
		}
		return amlReturnStatus;

	}
	
	public String updateFinscanStatus(BigDecimal customerId) {
		String amlReturnStatus = null;
		String idNumber = null;
		String status = "  ";
		String fullName = null;
		String fullNameLocal = null;
		customerList = new ArrayList<Customer>();
		try {
			FNSServicesLookupSoapClient finscanService = new FNSServicesLookupSoapClient();

			customerList = customerService.getCustomerInfo(customerId);

			if (customerList.size() > 0) {
				fullName = customerList.get(0).getFirstName() + " "
						+ customerList.get(0).getMiddleName() + " "
						+ customerList.get(0).getLastName();
				fullNameLocal = customerList.get(0).getFirstNameLocal() + " "
						+ customerList.get(0).getMiddleNameLocal() + " "
						+ customerList.get(0).getLastNameLocal();

				List<CustomerIdProof> idproofList = customerService
						.getCivilID(customerList.get(0).getCustomerReference());
				if (idproofList.size() > 0) {
					idNumber = idproofList.get(0).getIdentityInt();
				}

				amlReturnStatus = finscanService.amlServiceSearch("Individual",
						customerList.get(0).getGender(), "Active", "Yes",
						"Yes", "Yes", " ", " ", " ", " ", " ", " ", " ",
						"Full Source", "Yes", "Yes", "WC", "World-Check", "  ",
						"  ", "Country", customerList.get(0)
								.getFsCountryMasterByNationality()
								.getCountryId().toString(), "Date of Birth",
						customerList.get(0).getDateOfBirth().toString(),
						"National ID Number", idNumber, "Passport ID", "  ",
						fullName, fullNameLocal, "100061-E", "AMIEC", status);
			}

			System.out.println("AML Status is:" + amlReturnStatus);

		} catch (Throwable e) {

			LOG.error("Aml Check Exception:", e);
		}
		return amlReturnStatus;

	}

	public List<BranchComplianceDataTable> getBranchComplianceList() {
		return branchComplianceList;
	}

	public void setBranchComplianceList(
			List<BranchComplianceDataTable> branchComplianceList) {
		this.branchComplianceList = branchComplianceList;
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public BigDecimal getAmlCheckId() {
		return amlCheckId;
	}

	public void setAmlCheckId(BigDecimal amlCheckId) {
		this.amlCheckId = amlCheckId;
	}

	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public String getCustomerReferenceCode() {
		return customerReferenceCode;
	}

	public void setCustomerReferenceCode(String customerReferenceCode) {
		this.customerReferenceCode = customerReferenceCode;
	}

	public String getCountryBranchCode() {
		return countryBranchCode;
	}

	public void setCountryBranchCode(String countryBranchCode) {
		this.countryBranchCode = countryBranchCode;
	}

	public String getAmlCode() {
		return amlCode;
	}

	public void setAmlCode(String amlCode) {
		this.amlCode = amlCode;
	}

	public BigDecimal getAmlNumberOfHits() {
		return amlNumberOfHits;
	}

	public void setAmlNumberOfHits(BigDecimal amlNumberOfHits) {
		this.amlNumberOfHits = amlNumberOfHits;
	}

	public Date getAmlClearDate() {
		return amlClearDate;
	}

	public void setAmlClearDate(Date amlClearDate) {
		this.amlClearDate = amlClearDate;
	}

	public String getAccountYearMonth() {
		return accountYearMonth;
	}

	public void setAccountYearMonth(String accountYearMonth) {
		this.accountYearMonth = accountYearMonth;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<BranchComplianceDataTable> getSelectedBranchList() {
		return selectedBranchList;
	}

	public void setSelectedBranchList(
			List<BranchComplianceDataTable> selectedBranchList) {
		this.selectedBranchList = selectedBranchList;
	}

	public List<BranchComplianceDataTable> getHeadOfficeComplianceList() {
		return headOfficeComplianceList;
	}

	public void setHeadOfficeComplianceList(
			List<BranchComplianceDataTable> headOfficeComplianceList) {
		this.headOfficeComplianceList = headOfficeComplianceList;
	}

	public List<BranchComplianceDataTable> getSelectedHeadOfficeList() {
		return selectedHeadOfficeList;
	}

	public void setSelectedHeadOfficeList(
			List<BranchComplianceDataTable> selectedHeadOfficeList) {
		this.selectedHeadOfficeList = selectedHeadOfficeList;
	}

	public boolean isBranchRemarksRender() {
		return branchRemarksRender;
	}

	public void setBranchRemarksRender(boolean branchRemarksRender) {
		this.branchRemarksRender = branchRemarksRender;
	}

	public boolean isBranchPassportRender() {
		return branchPassportRender;
	}

	public void setBranchPassportRender(boolean branchPassportRender) {
		this.branchPassportRender = branchPassportRender;
	}

	public boolean isSubmitRender() {
		return submitRender;
	}

	public void setSubmitRender(boolean submitRender) {
		this.submitRender = submitRender;
	}

	public boolean isBranchStatusRender() {
		return branchStatusRender;
	}

	public void setBranchStatusRender(boolean branchStatusRender) {
		this.branchStatusRender = branchStatusRender;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Map<BigDecimal, String> getIdTypeMap() {
		return idTypeMap;
	}

	public void setIdTypeMap(Map<BigDecimal, String> idTypeMap) {
		this.idTypeMap = idTypeMap;
	}

	public boolean isPendingDialogRender() {
		return pendingDialogRender;
	}

	public void setPendingDialogRender(boolean pendingDialogRender) {
		this.pendingDialogRender = pendingDialogRender;
	}

	public void showPendingDialog() throws InterruptedException {
		RequestContext.getCurrentInstance().execute("amlpending.show();");
		// Thread.sleep(36000);
		// RequestContext.getCurrentInstance().execute(
		// "amlpending.hide();");
	}

	/*
	 * public static void main(String args[]){ try{
	 * 
	 * FNSServicesLookupSoapClient fNSServicesLookupSoapClient = new
	 * FNSServicesLookupSoapClient(); String amlStatus ="  " ;
	 * 
	 * //finscanService.amlServiceSearch(sSearchType, sGender, sClientStatus,
	 * sAddClient, sUpdateUserFields, sReturnComplianceRecords, sAddressLine1,
	 * sAddressLine2, sAddressLine3, sAddressLine4, sAddressLine5,
	 * sAddressLine6, sAddressLine7, sCategoryName, sIsMandatory, sIsSelected,
	 * sListCode, sListName, sCategories, sComplianceLists, sUserField1Label,
	 * sUserField1Value, sUserField2Label, sUserField2Value, sUserField3Label,
	 * sUserField3Value, sUserField4Label, sUserField4Value, sNameLine,
	 * sArabicName, sClientID, sClientSearchType, sAmlSts)
	 * 
	 * 
	 * String amlResultStatus = fNSServicesLookupSoapClient.amlServiceSearch(
	 * "Individual" ,"Male","Active", "Yes" ,"Yes" ,"Yes" , " " ," " ," " , " "
	 * ," " ," " , " " ,"Full Source" ,"Yes" , "Yes" ,"WC" ,"World-Check" , "  "
	 * ,"  " ,"Country" , "Pakistan" ,"Date of Birth" ,"19830107" ,
	 * "National ID Number" ,"283010702201" ,"Passport ID" ,
	 * 
	 * 
	 * //"I12345-E" ,"TEST"); "  " ,"MOHAMMED IRFAN" ,"  " , "100061-E"
	 * ,"AMIEC",amlStatus);
	 * 
	 * 
	 * 
	 * System.out.println(
	 * "============================================PRINT=============="
	 * +amlResultStatus);
	 * 
	 * }catch(Exception e){ System.out.println(e); } }
	 */

	private double progress = 0d;
	private String message = null;

	public String waitProcess(int count) throws InstantiationException,
			IllegalAccessException, InterruptedException, IOException {

		setMessage("Start");
		for (int i = 0; i < count; i++) {
			progress++;
			message = "processing [" + i + "]";
			Thread.sleep(1000);
		}
		setMessage("Complete");
		return "result";
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void refreshPage() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String refreshpage = fc.getViewRoot().getViewId();

		ViewHandler ViewH = fc.getApplication().getViewHandler();
		UIViewRoot UIV = ViewH.createView(fc, refreshpage);
		UIV.setViewId(refreshpage);
		fc.setViewRoot(UIV);
	}

	public String getErrmsg() {
		return Errmsg;
	}

	public void setErrmsg(String errmsg) {
		Errmsg = errmsg;
	}
	
	

}
