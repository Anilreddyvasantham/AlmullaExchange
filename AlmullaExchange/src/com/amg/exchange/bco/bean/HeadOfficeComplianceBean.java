package com.amg.exchange.bco.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.bco.model.BranchComplianceOfficer;
import com.amg.exchange.bco.service.IBranchComplianceOfficerService;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.util.SessionStateManage;

@Component("headOffice")
@Scope("session")
public class HeadOfficeComplianceBean<T> implements Serializable {
	
	@Autowired
	IBranchComplianceOfficerService bcoService;
	
	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	ICustomerRegistrationBranchService<T> customerService;

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger
			.getLogger(HeadOfficeComplianceBean.class);
	
	
	//Rendering properties
	private boolean headOfficeRender=false;
	
	//Properties
	
	private BigDecimal amlCheckId=null;
	private BigDecimal countryBranchId=null;
	private BigDecimal customerId=null;
	private BigDecimal applicationCountryId=null;
	private String customerReferenceCode=null;
	private String countryBranchCode=null;
	private String amlCode=null;
	private BigDecimal amlNumberOfHits=null;
	private Timestamp amlClearDate=null;
	private String accountYearMonth=null;
	private String isActive=null;
	private String remarks=null;
	private String createdBy=null;
	private Date createdDate=null;
	private String modifiedBy=null;
	private Date modifiedDate=null;
	private String passport=null;
	private String status=null;
	
	private boolean selected=false;
	private boolean branchRemarksRender = false;
	private boolean branchPassportRender = false;
	private boolean branchStatusRender = false;
	private boolean submitRender = false;

	public Map<BigDecimal, String> getCountryBranchList() {
		return countryBranchList;
	}

	public void setCountryBranchList(Map<BigDecimal, String> countryBranchList) {
		this.countryBranchList = countryBranchList;
	}

	//List
	private List<BranchComplianceDataTable> branchComplianceList = new ArrayList<BranchComplianceDataTable>();
	private List<BranchComplianceDataTable> headOfficeComplianceList = new ArrayList<BranchComplianceDataTable>();
	private List<BranchComplianceDataTable> selectedBranchList;
	private List<BranchComplianceDataTable> selectedHeadOfficeList;
	private List<Customer> customerList = new ArrayList<Customer>();
	
	Map<BigDecimal, String> countryBranchList = new HashMap<BigDecimal, String>();
	List<CountryBranch> countryBranch = new ArrayList<CountryBranch>();
	SessionStateManage sessionStateManage = new SessionStateManage();
	
	SessionStateManage session = new SessionStateManage();
	
	public void bcoNavigation() {
		branchComplianceList.clear();
		setCountryBranchId(null);
		setBranchPassportRender(false);
		setBranchRemarksRender(false);
		setSubmitRender(false);
		try {
				
			FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../bco/branchcomplianceofficer.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Head Office Complaint	
	public void hocoNavigation() {
		branchComplianceList.clear();
		setCountryBranchId(null);
		setBranchStatusRender(false);
		setBranchRemarksRender(false);
		setSubmitRender(false);
		try {
			//getHeadOfficeList();
			FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../bco/headofficecomplianceofficer.xhtml");
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
	
	public List<CountryBranch> getCountryBranch() {
		countryBranch = new ArrayList<CountryBranch>();
		countryBranch.addAll(generalService.getBranchDetails( sessionStateManage.getCountryId()));

		for (CountryBranch countryBranch1 : countryBranch) {
			countryBranchList.put(countryBranch1.getCountryBranchId(),
					countryBranch1.getBranchName());
		}
		return countryBranch;
	}

	public void setCountryBranch(List<CountryBranch> countryBranch) {
		this.countryBranch = countryBranch;
	}

	
	//Properties get/set 
	public boolean isHeadOfficeRender() {
		return headOfficeRender;
	}

	public void setHeadOfficeRender(boolean headOfficeRender) {
		this.headOfficeRender = headOfficeRender;
	}
	
	public void onchangeEnabled() {
	   System.out.println("sssssssssssssss=="+isSelected());
	}
	
	//Branch Office Complaint List
	public List<BranchComplianceDataTable> getBranchList() {
		System.out.println("country branch id==========="+getCountryBranchId());
		branchComplianceList.clear();
		BranchComplianceDataTable branchComplianceDataTable = null;
		
		List<BranchComplianceOfficer> branchComplainceList = bcoService.getBranchComplianceList(getCountryBranchId());
		
		for (BranchComplianceOfficer branchComplianceOfficer : branchComplainceList) {
			branchComplianceDataTable = new BranchComplianceDataTable();
			
			branchComplianceDataTable.setAmlCheckId(branchComplianceOfficer.getAmlCheckId());
			branchComplianceDataTable.setCountryBranchId(branchComplianceOfficer.getCountryBranchId().getCountryBranchId());			
			branchComplianceDataTable.setCustomerId(branchComplianceOfficer.getCustomerId().getCustomerId());
			branchComplianceDataTable.setApplicationCountryId(branchComplianceOfficer.getApplicationCountryId().getCountryId());
			branchComplianceDataTable.setCustomerReferenceCode(branchComplianceOfficer.getCustomerReferenceCode());
			branchComplianceDataTable.setAmlCode(branchComplianceOfficer.getAmlCode());
			branchComplianceDataTable.setAmlNumberOfHits(branchComplianceOfficer.getAmlNumberOfHits());
			branchComplianceDataTable.setAmlClearDate(branchComplianceOfficer.getAmlClearDate());
			branchComplianceDataTable.setAccountYearMonth(branchComplianceOfficer.getAccountYearMonth());
			branchComplianceDataTable.setIsActive(branchComplianceOfficer.getIsActive());
			branchComplianceDataTable.setRemarks(branchComplianceOfficer.getRemarks());
			branchComplianceDataTable.setCreatedBy(branchComplianceOfficer.getCreatedBy());
			branchComplianceDataTable.setCreatedDate(branchComplianceOfficer.getCreatedDate());
			branchComplianceDataTable.setModifiedBy(branchComplianceOfficer.getModifiedBy());
			branchComplianceDataTable.setModifiedDate(branchComplianceOfficer.getModifiedDate());
			
			branchComplianceDataTable.setFirstName(branchComplianceOfficer.getCustomerId().getFirstName());
			
			branchComplianceList.add(branchComplianceDataTable);
		}
		if(branchComplianceList.size()==0){
			setBranchPassportRender(false);
			setBranchRemarksRender(false);
			setSubmitRender(false);
		}else{
			setBranchPassportRender(true);
			setBranchRemarksRender(true);
			setSubmitRender(true);
		}
		return branchComplianceList;
	}
	
	// Save Branch Compliance
	public void saveBranchCompliance(){
		
		System.out.println("Selected Branch List size =="+getBranchList().size());
		
		BranchComplianceOfficer branchComplianceOfficer =  null;
		CountryBranch countryBranch = null;
		Customer customer = null;
		CountryMaster countryMaster = null;
		CompanyMaster companyMaster = null;
		LanguageType languageType = null;
		ArticleDetails articleDetail = null;
		IncomeRangeMaster incomeRange = null;
		
		try{
		
			for (BranchComplianceDataTable datatable : getSelectedBranchList()) {
				//System.out.println("selected value is ========================"+datatable.getSelected());
				//if(datatable.getSelected()==true){
					branchComplianceOfficer = new BranchComplianceOfficer();
					branchComplianceOfficer.setAmlCheckId(datatable.getAmlCheckId());
					
					
					countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(datatable.getCountryBranchId());
					
					customer = new Customer();
					customer.setCustomerId(datatable.getCustomerId());
					
					countryMaster = new CountryMaster();
					countryMaster.setCountryId(datatable.getApplicationCountryId());
					
					branchComplianceOfficer.setCountryBranchId(countryBranch);
					branchComplianceOfficer.setAccountYearMonth(datatable.getAccountYearMonth());
					branchComplianceOfficer.setCustomerId(customer);
					branchComplianceOfficer.setApplicationCountryId(countryMaster);
					branchComplianceOfficer.setCustomerReferenceCode(datatable.getCustomerReferenceCode());
					branchComplianceOfficer.setAmlNumberOfHits(datatable.getAmlNumberOfHits());
					branchComplianceOfficer.setAmlClearDate(datatable.getAmlClearDate());
					branchComplianceOfficer.setIsActive(datatable.getIsActive());
					branchComplianceOfficer.setRemarks(datatable.getRemarks());
					branchComplianceOfficer.setCreatedBy(datatable.getCreatedBy());
					branchComplianceOfficer.setCreatedDate(datatable.getCreatedDate());
					branchComplianceOfficer.setModifiedBy(datatable.getModifiedBy());
					branchComplianceOfficer.setModifiedDate(datatable.getModifiedDate());		
					
					
					//conditional Data
					branchComplianceOfficer.setAmlCode("Escalate");
					branchComplianceOfficer.setRemarks("BCO");
					branchComplianceOfficer.setAmlClearBy(session.getUserName());
				
					bcoService.saveBranchCompliance(branchComplianceOfficer);
					
					customerList = customerService.getCustomerInfo(customer.getCustomerId());
					
					
					/* customer save */
					
					countryMaster = new CountryMaster();
					countryMaster.setCountryId(customerList.get(0).getFsCountryMasterByCountryId().getCountryId());
					
					companyMaster = new CompanyMaster();
					companyMaster.setCompanyId(customerList.get(0).getFsCompanyMaster().getCompanyId());
					
					languageType = new LanguageType();
					languageType.setLanguageId(customerList.get(0).getFsLanguageType().getLanguageId());
					
					//Customer Type
					BizComponentData customerType = new BizComponentData();
					customerType.setComponentDataId(getGeneralService()
							.getComponentId("Individual",
									languageType.getLanguageId())
							.getFsBizComponentData().getComponentDataId());
		
					//Group Id
					BizComponentData companyGroup = new BizComponentData();
					companyGroup.setComponentDataId(getGeneralService()
							.getComponentId("Almulla Group", languageType.getLanguageId())
							.getFsBizComponentData().getComponentDataId());
					
					// Nationality 
					CountryMaster nationality = new CountryMaster();
					nationality.setCountryId(customerList.get(0).getFsCountryMasterByNationality().getCountryId());
					
					// Article
					articleDetail = new ArticleDetails();
					articleDetail.setArticleDetailId(customerList.get(0).getFsArticleDetails().getArticleDetailId());
				
					// Income Range
					incomeRange = new IncomeRangeMaster();
					incomeRange.setIncomeRangeId(customerList.get(0).getFsIncomeRangeMaster().getIncomeRangeId());
					
					
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
					customer.setFirstNameLocal(customerList.get(0).getFirstNameLocal());
					customer.setLastNameLocal(customerList.get(0).getLastNameLocal());
					customer.setMiddleNameLocal(customerList.get(0).getMiddleNameLocal());
					customer.setShortNameLocal(customerList.get(0).getShortNameLocal());
					
					customer.setGender(customerList.get(0).getGender());
					customer.setAmlStatus(customerList.get(0).getAmlStatus());
					
					customer.setVerificationBy(customerList.get(0).getVerificationBy());
					customer.setVerificationDate(customerList.get(0).getVerificationDate());
					customer.setAmlStatusUpdatedBy(customerList.get(0).getAmlStatusUpdatedBy());
					customer.setAmlStatusLastUpdated(customerList.get(0).getAmlStatusLastUpdated());
					customer.setBranchCode(customerList.get(0).getBranchCode());
					customer.setActivatedInd(customerList.get(0).getActivatedInd());
					customer.setActivatedDate(customerList.get(0).getActivatedDate());
					customer.setInactivatedDate(customerList.get(0).getInactivatedDate());
					customer.setTitle(customerList.get(0).getTitle());
					customer.setDateOfBirth(customerList.get(0).getDateOfBirth());
					customer.setAlterEmailId(customerList.get(0).getAlterEmailId());
					customer.setMobile(customerList.get(0).getMobile());
					customer.setSignatureSpecimen(customerList.get(0).getSignatureSpecimen());
					customer.setFingerPrintImg(customerList.get(0).getFingerPrintImg());
					customer.setIntroducedBy(customerList.get(0).getIntroducedBy());
					customer.setIntroducedDate(customerList.get(0).getIntroducedDate());
					customer.setMedicalInsuranceInd(customerList.get(0).getMedicalInsuranceInd());
					customer.setCompanyName(customerList.get(0).getCompanyName());
					customer.setCompanyNameLocal(customerList.get(0).getCompanyNameLocal());
					customer.setEmail(customerList.get(0).getEmail());
					customer.setCrNo(customerList.get(0).getCrNo());
					customer.setPlaceOfBirth(customerList.get(0).getPlaceOfBirth());
					customer.setCountryOfBirth(customerList.get(0).getCountryOfBirth());
					customer.setFatherName(customerList.get(0).getFatherName());
					customer.setCreatedBy(customerList.get(0).getCreatedBy());
					customer.setCreationDate(customerList.get(0).getCreationDate());
					//customer.setUpdated(customerList.get(0).getu);
					customer.setUpdatedBy(customerList.get(0).getUpdatedBy());
					customer.setLastUpdated(customerList.get(0).getLastUpdated());
					customer.setTokenKey(customerList.get(0).getTokenKey());
					customer.setContactPerson(customerList.get(0).getContactPerson());
					customer.setContactNumber(customerList.get(0).getContactNumber());
					customer.setFsArticleDetails(articleDetail);
					customer.setIsActive(customerList.get(0).getIsActive());
					customer.setFsIncomeRangeMaster(incomeRange);
					customer.setPaidupCapital(customerList.get(0).getPaidupCapital());
					customer.setDailyLimit(customerList.get(0).getDailyLimit());
					customer.setWeeklyLimit(customerList.get(0).getWeeklyLimit());
					customer.setMontlyLimit(customerList.get(0).getMontlyLimit());
					customer.setHalfYearly(customerList.get(0).getHalfYearly());
					customer.setQuaterlyLimit(customerList.get(0).getQuaterlyLimit());
					customer.setAnnualLimit(customerList.get(0).getAnnualLimit());
					customer.setVerificationTokenId(customerList.get(0).getVerificationTokenId());
					customer.setLoyaltyPoints(customerList.get(0).getLoyaltyPoints());
					customer.setCustomerReference(customerList.get(0).getCustomerReference());
					customer.setSmartCardIndicator(customerList.get(0).getSmartCardIndicator());
					customer.setLastTransactionDate(customerList.get(0).getLastTransactionDate());
					customer.setPepsIndicator(customerList.get(0).getPepsIndicator());
					
					//conditional data
					customer.setNumberOfHits(datatable.getAmlNumberOfHits());
				
					customerService.saveCustomer(customer);
					
					branchComplianceOfficer.setAmlCheckId(null);
					branchComplianceOfficer.setAmlCode("COMP");
					bcoService.saveBranchCompliance(branchComplianceOfficer);
				}
				
			//}			
			
		}catch (Exception e){
			System.out.println("error==============="+e.getMessage());
		}
		
	}
	
	//Head Office Complaint List
	public List<BranchComplianceDataTable> getHeadOfficeList() {
		
		headOfficeComplianceList.clear();
		BranchComplianceDataTable branchComplianceDataTable = null;
		
		List<BranchComplianceOfficer> headOfficeList = bcoService.getHeadOfficeComplianceList(getCountryBranchId());
		
		for (BranchComplianceOfficer headOfficeComplianceOfficer : headOfficeList) {
			branchComplianceDataTable = new BranchComplianceDataTable();
			
			branchComplianceDataTable.setAmlCheckId(headOfficeComplianceOfficer.getAmlCheckId());
			branchComplianceDataTable.setCountryBranchId(headOfficeComplianceOfficer.getCountryBranchId().getCountryBranchId());			
			branchComplianceDataTable.setCustomerId(headOfficeComplianceOfficer.getCustomerId().getCustomerId());
			branchComplianceDataTable.setApplicationCountryId(headOfficeComplianceOfficer.getApplicationCountryId().getCountryId());
			branchComplianceDataTable.setCustomerReferenceCode(headOfficeComplianceOfficer.getCustomerReferenceCode());
			branchComplianceDataTable.setAmlCode(headOfficeComplianceOfficer.getAmlCode());
			branchComplianceDataTable.setAmlNumberOfHits(headOfficeComplianceOfficer.getAmlNumberOfHits());
			branchComplianceDataTable.setAmlClearDate(headOfficeComplianceOfficer.getAmlClearDate());
			branchComplianceDataTable.setAccountYearMonth(headOfficeComplianceOfficer.getAccountYearMonth());
			branchComplianceDataTable.setIsActive(headOfficeComplianceOfficer.getIsActive());
			branchComplianceDataTable.setRemarks(headOfficeComplianceOfficer.getRemarks());
			branchComplianceDataTable.setCreatedBy(headOfficeComplianceOfficer.getCreatedBy());
			branchComplianceDataTable.setCreatedDate(headOfficeComplianceOfficer.getCreatedDate());
			branchComplianceDataTable.setModifiedBy(headOfficeComplianceOfficer.getModifiedBy());
			branchComplianceDataTable.setModifiedDate(headOfficeComplianceOfficer.getModifiedDate());
			
			branchComplianceDataTable.setFirstName(headOfficeComplianceOfficer.getCustomerId().getFirstName());
			
			headOfficeComplianceList.add(branchComplianceDataTable);
		}
		return headOfficeComplianceList;
		
	}
	
	
	// Save Head Office Compliance
	public void saveHeadOfficeCompliance(){
		
	}

	public List<BranchComplianceDataTable> getBranchComplianceList() {	
		return branchComplianceList;
	}
	
	public void setBranchComplianceList(List<BranchComplianceDataTable> branchComplianceList) {
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

	public Timestamp getAmlClearDate() {
		return amlClearDate;
	}

	public void setAmlClearDate(Timestamp amlClearDate) {
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

	public void setSelectedBranchList(List<BranchComplianceDataTable> selectedBranchList) {
		this.selectedBranchList = selectedBranchList;
	}
	
	public List<BranchComplianceDataTable> getHeadOfficeComplianceList() {		
		return headOfficeComplianceList;
	}
	public void setHeadOfficeComplianceList(List<BranchComplianceDataTable> headOfficeComplianceList) {
		this.headOfficeComplianceList = headOfficeComplianceList;
	}

	public List<BranchComplianceDataTable> getSelectedHeadOfficeList() {
		return selectedHeadOfficeList;
	}

	public void setSelectedHeadOfficeList(List<BranchComplianceDataTable> selectedHeadOfficeList) {
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
	

}
