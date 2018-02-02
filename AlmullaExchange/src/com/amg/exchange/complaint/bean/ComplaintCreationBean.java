package com.amg.exchange.complaint.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.complaint.customer.support.model.ComplaintLog;
import com.amg.exchange.complaint.model.ComplaintAssigned;
import com.amg.exchange.complaint.model.ComplaintAssignedDesc;
import com.amg.exchange.complaint.model.ComplaintMatrix;
import com.amg.exchange.complaint.model.ComplaintRemarksDesc;
import com.amg.exchange.complaint.model.ComplaintRemarksMaster;
import com.amg.exchange.complaint.model.ComplaintRemittanceViewModel;
import com.amg.exchange.complaint.model.ComplaintTypeDesc;
import com.amg.exchange.complaint.model.ComplaintTypeMaster;
import com.amg.exchange.complaint.service.IComplaintAssignedService;
import com.amg.exchange.complaint.service.IComplaintRemarksService;
import com.amg.exchange.complaint.service.IComplaintService;
import com.amg.exchange.complaint.service.IComplaintTypeService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.mail.ApplicationMailer;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("unused")
@Component("complaintCreation")
@Scope("session")
public class ComplaintCreationBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ComplaintCreationBean.class);

	// Create Session
	SessionStateManage sessionStateManage = new SessionStateManage();

	// Component variables
	// Main Panel Complaint creation
	private BigDecimal companyId;
	private String companyName;
	private BigDecimal remitdealYearId;
	private BigDecimal remitdealReference;
	private BigDecimal complaintdealYear;
	private BigDecimal complaintdealReference;
	private Date complaintLogDate;
	private Date effectiveMaxDate = new Date();
	private BigDecimal documentId;
	private String processIn = Constants.Yes;

	// Remittance Details
	private String countryName;
	private String bankName;
	private Date remittanceDate;
	private String branchName;
	private String serviceName;
	private String customerName;
	private String mobileNumber;
	private BigDecimal remitAmount;
	private String beneficiaryName;
	private String accountNumber;
	private String remittanceCompanyCode;
	private Date closedDate;
	private BigDecimal foriegnCurrencyId;
	private String currencyName;
	private BigDecimal customerReferenceNumber;
	private BigDecimal remitDocumentId;

	// Last Panel Complaint creation
	private String complaintLocationName;
	private BigDecimal complaintFromId;
	private String complaintFromName;
	private BigDecimal complaintTypeId;
	private String complaintTypeName;
	private BigDecimal complaintRemarksCodeId;
	private String complaintRemarksName;
	private String complaintRemarks;
	private BigDecimal complaintLogPk;

	// Normal variables
	private BigDecimal remitdealYear;
	private BigDecimal complaintdealYearId;
	private BigDecimal complaintLocationId;
	private BigDecimal countryId;
	private BigDecimal bankId;
	private BigDecimal branchId;
	private BigDecimal serviceId;
	private String customerId;

	// common variables
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;

	private String takenBy;
	private String actionTakenBy;
	private BigDecimal action;

	// Render variables
	private Boolean booRenderSaveOrExit = false;
	private Boolean booRenderDataTable = false;
	private Boolean booReadOnly = false;
	private Boolean booRenderEdit = false;
	private Boolean booRenderEditfalse = true;
	private BigDecimal complaintlogDealReference;
	private Boolean booSubmitPanel = false;

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	private Boolean booProcedureDialog = false;
	private String errMsg;

	// Component variables
	// Main Panel search operation
	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public String getProcessIn() {
		return processIn;
	}

	public void setProcessIn(String processIn) {
		this.processIn = processIn;
	}

	// Company Id
	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	// Company Name
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	// Remittance Deal Year ID
	public BigDecimal getRemitdealYearId() {
		return remitdealYearId;
	}

	public void setRemitdealYearId(BigDecimal remitdealYearId) {
		this.remitdealYearId = remitdealYearId;
	}

	// Remittance Deal Document reference Number
	public BigDecimal getRemitdealReference() {
		return remitdealReference;
	}

	public void setRemitdealReference(BigDecimal remitdealReference) {
		this.remitdealReference = remitdealReference;
	}

	// Complaint Deal Year
	public BigDecimal getComplaintdealYear() {
		return complaintdealYear;
	}

	public void setComplaintdealYear(BigDecimal complaintdealYear) {
		this.complaintdealYear = complaintdealYear;
	}

	// Complaint Deal Document reference Number
	public BigDecimal getComplaintdealReference() {
		BigDecimal value = new BigDecimal(getDocumentSerialId());
		return value;
	}

	public void setComplaintdealReference(BigDecimal complaintdealReference) {
		this.complaintdealReference = complaintdealReference;
	}

	// Complaint Log Date
	public Date getComplaintLogDate() {
		return complaintLogDate;
	}

	public void setComplaintLogDate(Date complaintLogDate) {
		this.complaintLogDate = complaintLogDate;
	}

	// Complaint Log Date Max Date Up to Current Date
	public Date getEffectiveMaxDate() {
		return effectiveMaxDate;
	}

	public void setEffectiveMaxDate(Date effectiveMaxDate) {
		this.effectiveMaxDate = effectiveMaxDate;
	}

	// Remittance Details Panel
	// Remittance Country Name
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	// Remittance Bank Name
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	// Remittance Date
	public Date getRemittanceDate() {
		return remittanceDate;
	}

	public void setRemittanceDate(Date remittanceDate) {
		this.remittanceDate = remittanceDate;
	}

	// Remittance Bank Branch Name
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	// Remittance Service Name
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	// Remittance Customer Name
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	// Remittance Mobile Number
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	// Remittance Amount
	public BigDecimal getRemitAmount() {
		return remitAmount;
	}

	public void setRemitAmount(BigDecimal remitAmount) {
		this.remitAmount = remitAmount;
	}

	// Remittance Beneficiary Name
	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	// Remittance Account Number
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	// Remittance Company Code
	public String getRemittanceCompanyCode() {
		return remittanceCompanyCode;
	}

	public void setRemittanceCompanyCode(String remittanceCompanyCode) {
		this.remittanceCompanyCode = remittanceCompanyCode;
	}

	// Closed Date
	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	// Remittance Foriegn Currency Id
	public BigDecimal getForiegnCurrencyId() {
		return foriegnCurrencyId;
	}

	public void setForiegnCurrencyId(BigDecimal foriegnCurrencyId) {
		this.foriegnCurrencyId = foriegnCurrencyId;
	}

	// Remittance Currency Name
	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	// Remittance CustomerReferenceNo
	public BigDecimal getCustomerReferenceNumber() {
		return customerReferenceNumber;
	}

	public void setCustomerReferenceNumber(BigDecimal customerReferenceNumber) {
		this.customerReferenceNumber = customerReferenceNumber;
	}

	// Last Panel remarks
	// Complaint Location Name
	public String getComplaintLocationName() {
		return complaintLocationName;
	}

	public void setComplaintLocationName(String complaintLocationName) {
		this.complaintLocationName = complaintLocationName;
	}

	// Complaint From fetch from Complaint Assigned Description
	public BigDecimal getComplaintFromId() {
		return complaintFromId;
	}

	public void setComplaintFromId(BigDecimal complaintFromId) {
		this.complaintFromId = complaintFromId;
	}

	// Complaint Type fetch from Complaint Type Description
	public BigDecimal getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(BigDecimal complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}

	// Complaint Remarks Code from Complaint Remarks Description
	public BigDecimal getComplaintRemarksCodeId() {
		return complaintRemarksCodeId;
	}

	public void setComplaintRemarksCodeId(BigDecimal complaintRemarksCodeId) {
		this.complaintRemarksCodeId = complaintRemarksCodeId;
	}

	// Complaint Remarks
	public String getComplaintRemarks() {
		return complaintRemarks;
	}

	public void setComplaintRemarks(String complaintRemarks) {
		this.complaintRemarks = complaintRemarks;
	}

	// Normal variables
	// Remittance Deal Year
	public BigDecimal getRemitdealYear() {
		return remitdealYear;
	}

	public void setRemitdealYear(BigDecimal remitdealYear) {
		this.remitdealYear = remitdealYear;
	}

	// Complaint Deal Year ID
	public BigDecimal getComplaintdealYearId() {
		return complaintdealYearId;
	}

	public void setComplaintdealYearId(BigDecimal complaintdealYearId) {
		this.complaintdealYearId = complaintdealYearId;
	}

	// Complaint Location Id
	public BigDecimal getComplaintLocationId() {
		return complaintLocationId;
	}

	public void setComplaintLocationId(BigDecimal complaintLocationId) {
		this.complaintLocationId = complaintLocationId;
	}

	// Remittance Country Id
	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	// Remittance Bank Id
	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	// Remittance Bank Branch Id
	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	// Remittance Customer Id
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	// Remittance Service Id
	public BigDecimal getServiceId() {
		return serviceId;
	}

	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	// Complaint From Name
	public String getComplaintFromName() {
		return complaintFromName;
	}

	public void setComplaintFromName(String complaintFromName) {
		this.complaintFromName = complaintFromName;
	}

	// Complaint Type Name
	public String getComplaintTypeName() {
		return complaintTypeName;
	}

	public void setComplaintTypeName(String complaintTypeName) {
		this.complaintTypeName = complaintTypeName;
	}

	// Complaint Remarks Name
	public String getComplaintRemarksName() {
		return complaintRemarksName;
	}

	public void setComplaintRemarksName(String complaintRemarksName) {
		this.complaintRemarksName = complaintRemarksName;
	}

	// Complaint complaintLogPk
	public BigDecimal getComplaintLogPk() {
		return complaintLogPk;
	}

	public void setComplaintLogPk(BigDecimal complaintLogPk) {
		this.complaintLogPk = complaintLogPk;
	}

	// Common Variables
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

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	// Rener Setters&getters
	public Boolean getBooRenderSaveOrExit() {
		return booRenderSaveOrExit;
	}

	public void setBooRenderSaveOrExit(Boolean booRenderSaveOrExit) {
		this.booRenderSaveOrExit = booRenderSaveOrExit;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public Boolean getBooReadOnly() {
		return booReadOnly;
	}

	public void setBooReadOnly(Boolean booReadOnly) {
		this.booReadOnly = booReadOnly;
	}

	public Boolean getBooRenderEdit() {
		return booRenderEdit;
	}

	public void setBooRenderEdit(Boolean booRenderEdit) {
		this.booRenderEdit = booRenderEdit;
	}

	public Boolean getBooRenderEditfalse() {
		return booRenderEditfalse;
	}

	public void setBooRenderEditfalse(Boolean booRenderEditfalse) {
		this.booRenderEditfalse = booRenderEditfalse;
	}

	public Boolean getBooSubmitPanel() {
		return booSubmitPanel;
	}

	public void setBooSubmitPanel(Boolean booSubmitPanel) {
		this.booSubmitPanel = booSubmitPanel;
	}

	public BigDecimal getComplaintlogDealReference() {
		return complaintlogDealReference;
	}

	public void setComplaintlogDealReference(BigDecimal complaintlogDealReference) {
		this.complaintlogDealReference = complaintlogDealReference;
	}

	public BigDecimal getRemitDocumentId() {
		return remitDocumentId;
	}

	public void setRemitDocumentId(BigDecimal remitDocumentId) {
		this.remitDocumentId = remitDocumentId;
	}

	// Auto wire required Services
	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IForeignCurrencyPurchaseService<T> iForeignCurrencyPurchaseService;

	@Autowired
	IBankBranchDetailsService<T> bankBranchDetailsService;

	@Autowired
	ISpecialCustomerDealRequestService<T> iSpecialCustomerDealRequestService;

	@Autowired
	IComplaintAssignedService iComplaintAssignedService;

	@Autowired
	IComplaintTypeService<T> iComplaintTypeService;

	@Autowired
	IComplaintRemarksService<T> iComplaintRemarksService;

	@Autowired
	IComplaintService<T> iComplaintService;

	// Lists to fetch Data from Data Base
	private List<CompanyMasterDesc> lstCompanyNamesFromCompanyMaster = new ArrayList<CompanyMasterDesc>();
	private List<UserFinancialYear> dealYearList = new ArrayList<UserFinancialYear>();
	private List<ComplaintAssignedDesc> lstComplaintFromCompAssTable = new ArrayList<ComplaintAssignedDesc>();
	private List<ComplaintTypeDesc> lstComplaintTypeFromComplaintType = new ArrayList<ComplaintTypeDesc>();
	private List<ComplaintRemarksDesc> lstComplaintRemarksFromComplaintRemarks = new ArrayList<ComplaintRemarksDesc>();
	private List<ComplaimtCreationDataTable> complaimtCreationDtList = new CopyOnWriteArrayList<ComplaimtCreationDataTable>();
	// Map to fetch Records From Based on Key
	private Map<BigDecimal, String> mapComplaintAssignedIdDescList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapComplaintAssignedIdCodecList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapComplaintAssignedIdList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapComplaintTypeIdDescList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapComplaintTypeIdCodeList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapRemarksIdDescList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapRemarksIdCodeList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapCountryNameList = new HashMap<BigDecimal, String>();

	// List get and setters
	public List<CompanyMasterDesc> getLstCompanyNamesFromCompanyMaster() {
		return lstCompanyNamesFromCompanyMaster;
	}

	public void setLstCompanyNamesFromCompanyMaster(List<CompanyMasterDesc> lstCompanyNamesFromCompanyMaster) {
		this.lstCompanyNamesFromCompanyMaster = lstCompanyNamesFromCompanyMaster;
	}

	public List<UserFinancialYear> getDealYearList() {
		return dealYearList;
	}

	public void setDealYearList(List<UserFinancialYear> dealYearList) {
		this.dealYearList = dealYearList;
	}

	public List<ComplaintAssignedDesc> getLstComplaintFromCompAssTable() {
		return lstComplaintFromCompAssTable;
	}

	public void setLstComplaintFromCompAssTable(List<ComplaintAssignedDesc> lstComplaintFromCompAssTable) {
		this.lstComplaintFromCompAssTable = lstComplaintFromCompAssTable;
	}

	public List<ComplaintTypeDesc> getLstComplaintTypeFromComplaintType() {
		return lstComplaintTypeFromComplaintType;
	}

	public void setLstComplaintTypeFromComplaintType(List<ComplaintTypeDesc> lstComplaintTypeFromComplaintType) {
		this.lstComplaintTypeFromComplaintType = lstComplaintTypeFromComplaintType;
	}

	public List<ComplaintRemarksDesc> getLstComplaintRemarksFromComplaintRemarks() {
		return lstComplaintRemarksFromComplaintRemarks;
	}

	public void setLstComplaintRemarksFromComplaintRemarks(List<ComplaintRemarksDesc> lstComplaintRemarksFromComplaintRemarks) {
		this.lstComplaintRemarksFromComplaintRemarks = lstComplaintRemarksFromComplaintRemarks;
	}

	public List<ComplaimtCreationDataTable> getComplaimtCreationDtList() {
		return complaimtCreationDtList;
	}

	public void setComplaimtCreationDtList(List<ComplaimtCreationDataTable> complaimtCreationDtList) {
		this.complaimtCreationDtList = complaimtCreationDtList;
	}

	public Map<BigDecimal, String> getMapRemarksIdDescList() {
		return mapRemarksIdDescList;
	}

	public void setMapRemarksIdDescList(Map<BigDecimal, String> mapRemarksIdDescList) {
		this.mapRemarksIdDescList = mapRemarksIdDescList;
	}

	public Map<BigDecimal, String> getMapRemarksIdCodeList() {
		return mapRemarksIdCodeList;
	}

	public void setMapRemarksIdCodeList(Map<BigDecimal, String> mapRemarksIdCodeList) {
		this.mapRemarksIdCodeList = mapRemarksIdCodeList;
	}

	public Map<BigDecimal, String> getMapComplaintAssignedIdDescList() {
		return mapComplaintAssignedIdDescList;
	}

	public void setMapComplaintAssignedIdDescList(Map<BigDecimal, String> mapComplaintAssignedIdDescList) {
		this.mapComplaintAssignedIdDescList = mapComplaintAssignedIdDescList;
	}

	public Map<BigDecimal, String> getMapComplaintAssignedIdCodecList() {
		return mapComplaintAssignedIdCodecList;
	}

	public void setMapComplaintAssignedIdCodecList(Map<BigDecimal, String> mapComplaintAssignedIdCodecList) {
		this.mapComplaintAssignedIdCodecList = mapComplaintAssignedIdCodecList;
	}

	public Map<BigDecimal, String> getMapComplaintTypeIdDescList() {
		return mapComplaintTypeIdDescList;
	}

	public void setMapComplaintTypeIdDescList(Map<BigDecimal, String> mapComplaintTypeIdDescList) {
		this.mapComplaintTypeIdDescList = mapComplaintTypeIdDescList;
	}

	public Map<BigDecimal, String> getMapComplaintTypeIdCodeList() {
		return mapComplaintTypeIdCodeList;
	}

	public void setMapComplaintTypeIdCodeList(Map<BigDecimal, String> mapComplaintTypeIdCodeList) {
		this.mapComplaintTypeIdCodeList = mapComplaintTypeIdCodeList;
	}

	public Map<BigDecimal, String> getMapComplaintAssignedIdList() {
		return mapComplaintAssignedIdList;
	}

	public void setMapComplaintAssignedIdList(Map<BigDecimal, String> mapComplaintAssignedIdList) {
		this.mapComplaintAssignedIdList = mapComplaintAssignedIdList;
	}

	public Map<BigDecimal, String> getMapCountryNameList() {
		return mapCountryNameList;
	}

	public void setMapCountryNameList(Map<BigDecimal, String> mapCountryNameList) {
		this.mapCountryNameList = mapCountryNameList;
	}

	private List<ComplaintMatrixDataTable> complaintMatrixList = new ArrayList<ComplaintMatrixDataTable>();
	private List<ServiceMasterDesc> serviceMasterDescList = new ArrayList<ServiceMasterDesc>();
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void complaintCreationNavigation() {
		// fetching all records in form load only
		complaimtCreationDtList.clear();
		clearAllFields();
		setBooReadOnly(false);
		setBooRenderDataTable(false);
		setBooRenderSaveOrExit(false);
		fetchRecordOnFormLoad();
		setBooRenderEditfalse(true);
		setBooRenderEdit(false);
		setBooSubmitPanel(false);

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "complaintcreation.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintcreation.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::complaintCreationNavigation");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void fetchRecordOnFormLoad() {

		// fetch All Company Names
		companyListFromDB();

		// fetch remit Deal Year
		fetchRemittanceDealYear();

		// fetch Log Date by Current Date
		complaintLogDateCurrentDate();

		// fetch Branch location From employee
		fetchEmployeeLocation();

		// fetch All Complaint From comes from Complaint Assigned Description
		// where appCountryid , language Id , isActive "Y" and LogCompaint "Y"
		fetchComplaintFromComplaintAssigned();

		// fetch Complaint Type Comes From Complaint Type Description where
		// appCountryid , language Id , isActive "Y"
		fetchComplaintTypeFromComplaintType();

		// fetch Complaint Remarks Comes From Complaint Remarks Description
		// where appCountryid , language Id , isActive "Y"
		fetchComplaintRemarksFromComplaintRemarks();

	}

	// Exit from this module
	public void exit() throws IOException {
		complaimtCreationDtList.clear();
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public List<CountryMasterDesc> getCountryNameList() {
		List<CountryMasterDesc> lstCountry = new ArrayList<>();
		try {
			lstCountry = generalService.getCountryList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1));
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::getCountryNameList");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return null;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;
		}
		// List<CountryMasterDesc>
		return lstCountry;
	}

	public List<BankMaster> getBankNameList() {
		List<BankMaster> lstBank = new ArrayList<>();
		try {
			lstBank = bankBranchDetailsService.getBankList();
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::getBankNameList");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return null;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;
		}
		return lstBank;
	}

	public List<ComplaintMatrixDataTable> getComplaintMatrixList() {
		return complaintMatrixList;
	}

	public void setComplaintMatrixList(

	List<ComplaintMatrixDataTable> complaintMatrixList) {
		/*
		 * for (int i = 0; i <= 4; i++) { ComplaintMatrixDataTable
		 * complaintMatrixDataTable = new ComplaintMatrixDataTable();
		 * 
		 * complaintMatrixList.add(complaintMatrixDataTable);
		 */
		this.complaintMatrixList = complaintMatrixList;
	}

	// }

	public List<ServiceMasterDesc> getServiceMasterDescList() {

		return generalService.getAllServiceDesc(sessionStateManage.getLanguageId());
	}

	@SuppressWarnings("unchecked")
	public void setServiceMasterDescList(List<ServiceMasterDesc> serviceMasterDescList) {
		this.generalService = (IGeneralService<T>) serviceMasterDescList;
	}

	// Fetch All Companies from Company Master
	public void companyListFromDB() {

		lstCompanyNamesFromCompanyMaster.clear();
		try {
			List<CompanyMasterDesc> lstCompanyNames = generalService.getAllCompanyList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
			if (lstCompanyNames.size() != 0) {
				lstCompanyNamesFromCompanyMaster.addAll(lstCompanyNames);

				for (CompanyMasterDesc companyMasterDesc : lstCompanyNames) {
					if (companyMasterDesc.getFsCompanyMaster().getCompanyId().compareTo(sessionStateManage.getCompanyId()) == 0) {
						setCompanyId(companyMasterDesc.getFsCompanyMaster().getCompanyId());
						setCompanyName(companyMasterDesc.getCompanyName());
					}

				}
			} else {
				setCompanyId(null);
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::companyListFromDB");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	// Fetch Remittance year , Complaint Year , Complaint Reference along with
	// Deal year Ids
	public void fetchRemittanceDealYear() {

		dealYearList.clear();

		try {

			List<UserFinancialYear> lstFinancialYear = iComplaintService.getAllDocumentYear();
			if (lstFinancialYear.size() != 0) {
				dealYearList.addAll(lstFinancialYear);
			}

			List<UserFinancialYear> lstCurrentDealYear = generalService.getDealYear(new Date());
			if (lstCurrentDealYear.size() != 0) {
				BigDecimal financialYear = lstCurrentDealYear.get(0).getFinancialYear();
				BigDecimal financialYearId = lstCurrentDealYear.get(0).getFinancialYearID();
				setRemitdealYear(financialYear);
				setRemitdealYearId(financialYearId);
				setComplaintdealYear(financialYear);
				setComplaintdealYearId(financialYearId);
				String compDealref = documentSerialID(Constants.Yes);
				if (compDealref != null) {
					setComplaintdealReference(new BigDecimal(compDealref));
				} else {
					setComplaintdealReference(null);
				}
			} else {
				setRemitdealYear(null);
				setRemitdealYearId(null);
				setComplaintdealYear(null);
				setComplaintdealYearId(null);
			}

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::fetchRemittanceDealYear");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	// Document Seriality for Complaint Creation Code = 66
	public String documentSerialID(String processIn) {
		try {
			HashMap<String, String> outPutValues = generalService.getNextDocumentRefNumber(sessionStateManage.getCountryId().intValue(), sessionStateManage.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_COMPLAINTCREATION), getComplaintdealYear().intValue(), processIn,
					sessionStateManage.getCountryBranchCode());

			String proceErrorMsg = outPutValues.get("PROCE_ERORRMSG");
			if (proceErrorMsg != null) {

				setBooProcedureDialog(true);
				setErrMsg(proceErrorMsg);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
				return "0";

			} else {
				setBooProcedureDialog(false);
				String documentSerialID = outPutValues.get("DOCNO");
				return documentSerialID;

			}

		} catch (NumberFormatException | AMGException e) {
			setErrMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
			return "0";
		}
		// String documentSerialID =
		// generalService.getNextDocumentReferenceNumber(sessionStateManage.getCountryId().intValue()
		// , sessionStateManage.getCompanyId().intValue(),
		// Integer.parseInt(Constants.DOCUMENT_CODE_FOR_COMPLAINTCREATION) ,
		// getComplaintdealYear().intValue() ,
		// processIn,sessionStateManage.getCountryBranchCode());
		// return documentSerialID;

	}

	// set Log Date Current Date
	public void complaintLogDateCurrentDate() {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dealDate = format.format(new Date());

		try {
			setComplaintLogDate(format.parse(dealDate));

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	// fetch Location Based on Login
	public void fetchEmployeeLocation() {

		try {
			List<Employee> empLocation = generalService.getEmployeelist(sessionStateManage.getCountryId(), new BigDecimal(sessionStateManage.getBranchId()), new BigDecimal(sessionStateManage.getRoleId()));

			if (empLocation.size() != 0) {
				String locationName = empLocation.get(0).getLocation();
				BigDecimal CountrybranchId = empLocation.get(0).getFsCountryBranch().getCountryBranchId();
				setComplaintLocationName(locationName);
				setComplaintLocationId(CountrybranchId);
			} else {
				setComplaintLocationName(null);
				setComplaintLocationId(null);
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::fetchEmployeeLocation");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	// fetching complaint from from Complaint Assigned table based on
	// appCountryId , isActive Y , LogComplaint Y , and Language Id
	public void fetchComplaintFromComplaintAssigned() {

		lstComplaintFromCompAssTable.clear();
		mapComplaintAssignedIdDescList.clear();
		mapComplaintAssignedIdCodecList.clear();
		mapComplaintAssignedIdList.clear();
		try {
			List<ComplaintAssignedDesc> lstComplaintFrom = iComplaintAssignedService.getAllComplaintAssignedForComplaintCreation(sessionStateManage.getCountryId(), new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1));
			if (lstComplaintFrom.size() != 0) {
				lstComplaintFromCompAssTable.addAll(lstComplaintFrom);
				for (ComplaintAssignedDesc complaintAssignedDesc : lstComplaintFrom) {
					mapComplaintAssignedIdDescList.put(complaintAssignedDesc.getComplaintAssigned().getComplaintAssignedId(), complaintAssignedDesc.getFullDescription());
					mapComplaintAssignedIdCodecList.put(complaintAssignedDesc.getComplaintAssigned().getComplaintAssignedId(), complaintAssignedDesc.getComplaintAssigned().getComplaintAssignedCode());
					// mapComplaintAssignedIdList.put(complaintAssignedDesc.getComplaintAssigned().getComplaintAssignedId(),
					// complaintAssignedDesc.getComplaintAssigned().getAssignTo());
				}
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::fetchComplaintFromComplaintAssigned");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	// fetch Complaint Type from Complaint Type Based on appCountryId , isActive
	// Y , and Language Id
	public void fetchComplaintTypeFromComplaintType() {

		lstComplaintTypeFromComplaintType.clear();
		mapComplaintTypeIdDescList.clear();
		mapComplaintTypeIdCodeList.clear();
		try {
			List<ComplaintTypeDesc> lstComplaintType = iComplaintTypeService.displayComplaintTypeForComplaintCreation(sessionStateManage.getCountryId(), new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1));
			if (lstComplaintType.size() != 0) {
				lstComplaintTypeFromComplaintType.addAll(lstComplaintType);
				for (ComplaintTypeDesc complaintTypeDesc : lstComplaintType) {
					mapComplaintTypeIdDescList.put(complaintTypeDesc.getComplaintType().getComplaintTypeId(), complaintTypeDesc.getFullDesc());
					mapComplaintTypeIdCodeList.put(complaintTypeDesc.getComplaintType().getComplaintTypeId(), complaintTypeDesc.getComplaintType().getComplaintTypeCode());
				}
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::fetchComplaintTypeFromComplaintType");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	// fetch Complaint Remarks where isActive "Y"
	public void fetchComplaintRemarksFromComplaintRemarks() {

		lstComplaintRemarksFromComplaintRemarks.clear();
		mapRemarksIdDescList.clear();
		mapRemarksIdCodeList.clear();
		try {
			List<ComplaintRemarksDesc> lstComplaintRemarks = iComplaintRemarksService.getAllComplaintRemarksForComplaintCreation(sessionStateManage.getCountryId(), new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1));
			if (lstComplaintRemarks.size() != 0) {
				lstComplaintRemarksFromComplaintRemarks.addAll(lstComplaintRemarks);
				for (ComplaintRemarksDesc complaintRemarksDesc : lstComplaintRemarks) {
					mapRemarksIdDescList.put(complaintRemarksDesc.getComplaintRemarks().getComplaintRemarksId(), complaintRemarksDesc.getFullDesc());
					mapRemarksIdCodeList.put(complaintRemarksDesc.getComplaintRemarks().getComplaintRemarksId(), complaintRemarksDesc.getComplaintRemarks().getComplaintRemarksCode());
				}
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::fetchComplaintRemarksFromComplaintRemarks");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	// fetch remittance Details from View "V_COMPLAINT_REMITTANCE"
	public void fetchComplaintremittanceDetails() {
		try {
			List<ComplaintLog> complaintLogList = iComplaintService.getDuplicateCHeckForCompalintCreation(sessionStateManage.getCountryId(), getRemitdealYear(), getRemitdealReference(), getCompanyId(), getClosedDate());
			if (complaintLogList.size() > 0) {
				RequestContext.getCurrentInstance().execute("remitDealRefAlreayExist.show();");
				setRemitdealReference(null);
				clearAllFields();
				return;
			} else {
				if (getCompanyId() != null && getRemitdealYear() != null && getRemitdealReference() != null) {

					List<ComplaintRemittanceViewModel> lstComplaintRemittance = iComplaintService.getComplaintRemittanceForComplaintCreation(getCompanyId(), getRemitdealYear(), getRemitdealReference());

					if (lstComplaintRemittance.size() != 0 && lstComplaintRemittance.size() == 1) {

						ComplaintRemittanceViewModel compRemitView = lstComplaintRemittance.get(0);

						setCountryId(compRemitView.getBankCountryId());
						setCountryName(compRemitView.getCountryName());
						setBankId(compRemitView.getBeneficiaryBankId());
						setBankName(compRemitView.getBeneficiaryBankName());
						setRemitDocumentId(compRemitView.getDocumentId());
						setRemittanceDate(compRemitView.getDocumentDate());
						setBranchId(compRemitView.getBeneficiaryBranchId());
						setBranchName(compRemitView.getBeneficiaryBranchName());
						setServiceId(compRemitView.getServiceId());
						setServiceName(compRemitView.getServiceName());
						setCustomerId(compRemitView.getCustomerId());
						setCustomerName(compRemitView.getCustomerName());
						setMobileNumber(compRemitView.getMobileNumber());
						setRemitAmount(compRemitView.getForeignTranxAmount());
						setBeneficiaryName(compRemitView.getBeneficiaryName());
						setAccountNumber(compRemitView.getBeneficiaryAccountNo());
						setForiegnCurrencyId(compRemitView.getForeignCurrencyId());
						setCurrencyName(compRemitView.getCurrencyName());
						setCustomerReferenceNumber(compRemitView.getCustomerReference());
						setComplaintFromId(null);
						setComplaintFromName(null);
						setComplaintTypeId(null);
						setComplaintTypeName(null);
						setComplaintRemarksCodeId(null);
						setComplaintRemarksName(null);
						setComplaintRemarks(null);
						setBooSubmitPanel(true);

					} else {

						setCountryId(null);
						setCountryName(null);
						setBankId(null);
						setBankName(null);
						setRemittanceDate(null);
						setBranchId(null);
						setBranchName(null);
						setServiceId(null);
						setServiceName(null);
						setCustomerId(null);
						setCustomerName(null);
						setMobileNumber(null);
						setRemitAmount(null);
						setBeneficiaryName(null);
						setAccountNumber(null);
						setDocumentNoForNoRecord(getRemitdealReference());
						RequestContext.getCurrentInstance().execute("noRecords.show();");
						setRemitdealReference(null);
						clearAllFields();
						return;

					}

				}

			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::fetchComplaintremittanceDetails");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void clearAllFields() {
		setRemitdealReference(null);
		setCountryId(null);
		setCountryName(null);
		setBankId(null);
		setBankName(null);
		setRemittanceDate(null);
		setBranchId(null);
		setBranchName(null);
		setServiceId(null);
		setServiceName(null);
		setCustomerId(null);
		setCustomerName(null);
		setMobileNumber(null);
		setRemitAmount(null);
		setBeneficiaryName(null);
		setAccountNumber(null);
		setComplaintFromId(null);
		setComplaintFromName(null);
		setComplaintTypeId(null);
		setComplaintTypeName(null);
		setComplaintRemarksCodeId(null);
		setComplaintRemarksName(null);
		setComplaintRemarks(null);
		setCurrencyName(null);
		setBooSubmitPanel(false);

	}

	// add records to data table
	public void addRecrodsToComplaintCreationDataTable() {

		ComplaimtCreationDataTable complaimtCreationDtObj = new ComplaimtCreationDataTable();
		// complaint creation added
		complaimtCreationDtObj.setCompanyId(getCompanyId());
		complaimtCreationDtObj.setRemittanceYear(getRemitdealYear());
		complaimtCreationDtObj.setRemittanceDocNo(getRemitdealReference());
		complaimtCreationDtObj.setRemitdocumentId(getRemitDocumentId());
		complaimtCreationDtObj.setDocumentId(new BigDecimal(Constants.DOCUMENT_CODE_FOR_COMPLAINTCREATION));
		complaimtCreationDtObj.setComplaintYear(getComplaintdealYearId());
		if (getBooRenderEdit().equals(true)) {
			complaimtCreationDtObj.setComplaintNo(getComplaintlogDealReference());
		} else {
			String docSerNo = documentSerialID(Constants.U);
			if (docSerNo.equalsIgnoreCase("0")) {
				complaimtCreationDtObj.setComplaintNo(BigDecimal.ZERO);
			} else {
				BigDecimal compDealref = new BigDecimal(docSerNo);
				complaimtCreationDtObj.setComplaintNo(compDealref);
			}

		}
		complaimtCreationDtObj.setLogDate(getComplaintLogDate());
		// remittance Details added
		complaimtCreationDtObj.setCountryId(getCountryId());
		complaimtCreationDtObj.setCountryName(getCountryName());
		complaimtCreationDtObj.setBankId(getBankId());
		complaimtCreationDtObj.setBankName(getBankName());
		complaimtCreationDtObj.setRemitDate(getRemittanceDate());
		complaimtCreationDtObj.setBranchId(getBranchId());
		complaimtCreationDtObj.setBranchName(getBranchName());
		complaimtCreationDtObj.setServiceId(getServiceId());
		complaimtCreationDtObj.setServiceName(getServiceName());
		complaimtCreationDtObj.setCustomerId(new BigDecimal(getCustomerId()));
		complaimtCreationDtObj.setCustomerName(getCustomerName());
		complaimtCreationDtObj.setMobileNumber(new BigDecimal(getMobileNumber()));
		complaimtCreationDtObj.setAmount(getRemitAmount());
		complaimtCreationDtObj.setBeneficiary(getBeneficiaryName());
		complaimtCreationDtObj.setAccountNumber(getAccountNumber());
		complaimtCreationDtObj.setForiegnCurrencyId(getForiegnCurrencyId());
		complaimtCreationDtObj.setCurrencyName(getCurrencyName());
		complaimtCreationDtObj.setCustomerReferenceNumber(getCustomerReferenceNumber());
		complaimtCreationDtObj.setCurrencyName(getCurrencyName());
		// Last Panel Complaint creation variables added
		complaimtCreationDtObj.setLocationId(getComplaintLocationId());
		complaimtCreationDtObj.setLocation(getComplaintLocationName());
		complaimtCreationDtObj.setComplaintFromId(getComplaintFromId());
		complaimtCreationDtObj.setComplaintFrom(mapComplaintAssignedIdDescList.get(getComplaintFromId()));
		complaimtCreationDtObj.setComplaintTypeId(getComplaintTypeId());
		complaimtCreationDtObj.setComplaintTypeName(mapComplaintTypeIdDescList.get(getComplaintTypeId()));
		complaimtCreationDtObj.setRemarksCodeId(getComplaintRemarksCodeId());
		complaimtCreationDtObj.setRemarksCode(mapRemarksIdDescList.get(getComplaintRemarksCodeId()));
		complaimtCreationDtObj.setRemarks(getComplaintRemarks());
		complaimtCreationDtObj.setComplaintLogPk(getComplaintLogPk());
		complaimtCreationDtObj.setApplicationCountryId(sessionStateManage.getCountryId());
		complaimtCreationDtObj.setIsActive(Constants.U);
		complaimtCreationDtObj.setCreatedBy(sessionStateManage.getUserName());
		complaimtCreationDtObj.setCreatedDate(new Date());
		complaimtCreationDtObj.setModifiedBy(getModifiedBy());
		complaimtCreationDtObj.setModifiedDate(getModifiedDate());
		/*
		 * String complaintType =
		 * iComplaintService.toCheckComplaintTypeFromUserProfile
		 * (sessionStateManage.getEmployeeId()); Boolean complaintTypeValue =
		 * CommonUtil.isNull(complaintType);
		 * 
		 * if (!complaintTypeValue) { List<ComplaintMatrix>
		 * lstComplaintMatrix=iComplaintService
		 * .toFetchBasedOnTheseCombination(sessionStateManage
		 * .getCountryId(),getCountryId
		 * (),getBankId(),getServiceId(),getComplaintTypeId
		 * (),getComplaintFromId()); if(lstComplaintMatrix.size()>0){ if
		 * (mapComplaintAssignedIdCodecList
		 * .get(getComplaintFromId()).equals("1")){
		 * complaimtCreationDtObj.setComplaintAssignedTo
		 * (Constants.ASSIGNED_CUSTOMER_HELP_DESK); } else if
		 * (mapComplaintAssignedIdCodecList
		 * .get(getComplaintFromId()).equals("2")) {
		 * complaimtCreationDtObj.setComplaintAssignedTo
		 * (Constants.ASSIGNED_CUSTOMER_SUPPORT_SERVICE); } else if
		 * (mapComplaintAssignedIdCodecList
		 * .get(getComplaintFromId()).equals("3")) {
		 * complaimtCreationDtObj.setComplaintAssignedTo
		 * (Constants.ASSIGNED_BRANCH); } } }
		 */

		complaimtCreationDtList.add(complaimtCreationDtObj);

		setBooRenderDataTable(true);
		setBooRenderSaveOrExit(true);
		setBooReadOnly(false);
		setBooRenderEditfalse(true);
		setBooRenderEdit(false);
		setBooSubmitPanel(false);
		clearAllFields();

	}

	public void duplicateCheckForDataTable() {
		try {
			if (complaimtCreationDtList.size() != 0) {
				for (ComplaimtCreationDataTable complaimtCreationDt : complaimtCreationDtList) {
					if (complaimtCreationDt.getRemittanceDocNo().equals(getRemitdealReference())) {
						setRemitdealReference(null);
						clearAllFields();
						setBooRenderSaveOrExit(true);
						setBooRenderDataTable(true);
						setBooRenderEditfalse(true);
						setBooRenderEdit(false);
						RequestContext.getCurrentInstance().execute("recordAlreadyExist.show();");
						return;
					}
				}
			}
			addRecrodsToComplaintCreationDataTable();
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::duplicateCheckForDataTable");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void saveComplaintAndSendingEmailToAssignedTo() {
		try {
			saveCompleteCreation();
			String complaintType = iComplaintService.toCheckComplaintTypeFromUserProfile(sessionStateManage.getEmployeeId());
			// Boolean complaintTypeValue = CommonUtil.isNull(complaintType);

			if (complaintType != null) {

				if (complaintType.equals(Constants.ASSIGNED_CUSTOMER_HELP_DESK)) {
					sendEmailToCustomerHelpDesk();
				} else if (complaintType.equals(Constants.ASSIGNED_CUSTOMER_SUPPORT_SERVICE)) {
					sendEmailToCustomerSupportService();
				} else if (complaintType.equals(Constants.ASSIGNED_BRANCH)) {
					sendEmailToBranch();
				}

			} else {
				sendEmailToBranch();
			}

			RequestContext.getCurrentInstance().execute("complete.show();");
			clearAllFields();
			return;
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveComplaintAndSendingEmailToAssignedTo");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	private void sendEmailToCustomerHelpDesk() {
		try {
			String Employee = iComplaintService.tofetchEmployeeIdBasedOnEmployeeCode(sessionStateManage.getEmployeeId());
			if (Employee != null) {
				applicationMailer.sendRegistrationMail(Employee, "Complaint Creation ", sessionStateManage.getUserName());

			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::sendEmailToCustomerHelpDesk");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	private void sendEmailToCustomerSupportService() {
		try {
			String Employee = iComplaintService.tofetchEmployeeIdBasedOnEmployeeCode(sessionStateManage.getEmployeeId());
			if (Employee != null) {
				applicationMailer.sendRegistrationMail(Employee, "Complaint Creation ", sessionStateManage.getUserName());

			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::sendEmailToCustomerSupportService");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	private void sendEmailToBranch() {
		try {
			String Employee = iComplaintService.tofetchEmployeeIdBasedOnEmployeeCode(sessionStateManage.getEmployeeId());
			if (Employee != null) {
				applicationMailer.sendRegistrationMail(Employee, "Complaint Creation ", sessionStateManage.getUserName());

			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::sendEmailToBranch");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	@Autowired
	ApplicationMailer applicationMailer;

	public void saveCompleteCreation() {
		try {
			for (ComplaimtCreationDataTable complaimtCreationDt : complaimtCreationDtList) {
				ComplaintLog complaintLog = new ComplaintLog();

				// Company Master
				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(complaimtCreationDt.getCompanyId());
				complaintLog.setRemittanceCompany(companyMaster);
				// getCompanyCode
				complaintLog.setRemittanceCompanyCode(mapRemarksIdCodeList.get(complaimtCreationDt.getRemarksCodeId()));
				complaintLog.setCompanyMaster(companyMaster);
				// Document Master
				Document remitdocument = new Document();
				remitdocument.setDocumentID(complaimtCreationDt.getRemitdocumentId());
				// getRemitDocumentCode
				complaintLog.setRemittanceDocumentCode(generalService.getDocument(complaimtCreationDt.getRemitdocumentId(), sessionStateManage.getLanguageId()).get(0).getDocumentCode().toString());
				// getRemitDealNumber
				complaintLog.setRemittanceDocumentNo(complaimtCreationDt.getRemittanceDocNo());
				// getRemitDealYear
				complaintLog.setRemittanceDocumentFinancialYear(remitdealYear);
				// getRemitDate
				complaintLog.setRemittanceDate(complaimtCreationDt.getRemitDate());
				// getremit ForiegnCurreny Amount
				complaintLog.setForiegnCurrencyAmount(complaimtCreationDt.getAmount());
				complaintLog.setRemittanceDocument(remitdocument);

				/*
				 * Document document=new Document();
				 * document.setDocumentID(generalService
				 * .getDocument(complaimtCreationDt
				 * .getDocumentId(),sessionStateManage
				 * .getLanguageId()).get(0).getDocumentID());
				 * //document.setDocumentID
				 * (complaimtCreationDt.getComplaintNo());
				 * complaintLog.setDocument(document);
				 */
				// get Complaint Document Code
				// complaintLog.setComplaintReference(complaimtCreationDt.getDocumentId());
				complaintLog.setDocument(complaimtCreationDt.getDocumentId());
				// get Complaint Document No
				complaintLog.setDocumentNumber(complaimtCreationDt.getComplaintNo());

				complaintLog.setComplaintReference(complaimtCreationDt.getComplaintNo());

				// Country Master
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(complaimtCreationDt.getCountryId());
				complaintLog.setCountry(countryMaster);

				CountryMaster appCountryMaster = new CountryMaster();
				appCountryMaster.setCountryId(complaimtCreationDt.getApplicationCountryId());
				complaintLog.setApplicationCountry(appCountryMaster);

				// Customer Details
				Customer customer = new Customer();
				customer.setCustomerId(complaimtCreationDt.getCustomerId());
				// getCustomer ReferEnce Number
				complaintLog.setCustomerReference(complaimtCreationDt.getCustomerReferenceNumber());
				// complaintLog.setCustomerReference(iForeignCurrencyPurchaseService.getCustomerAllDetails(complaimtCreationDt.getCustomerId()).get(0).getCustomerReference());
				complaintLog.setCustomer(customer);

				// Service Master
				ServiceMaster serviceMaster = new ServiceMaster();
				serviceMaster.setServiceId(complaimtCreationDt.getServiceId());
				complaintLog.setService(serviceMaster);

				// Bank Master
				BankMaster bankMaster = new BankMaster();
				bankMaster.setBankId(complaimtCreationDt.getBankId());
				complaintLog.setBank(bankMaster);

				// CountryBranch Master
				CountryBranch countryBranch = new CountryBranch();
				countryBranch.setCountryBranchId(complaimtCreationDt.getLocationId());
				// get country Branch Code
				complaintLog.setCountryBranchCode(iComplaintService.getCountryBranchCodeBasedOnBranchId(complaimtCreationDt.getLocationId()).get(0).getBranchId().toPlainString());
				complaintLog.setCountryBranch(countryBranch);

				// Complaint Assigned Master
				ComplaintAssigned complaintAssigned = new ComplaintAssigned();
				complaintAssigned.setComplaintAssignedId(complaimtCreationDt.getComplaintFromId());
				complaintLog.setComplaintFromId(complaintAssigned);

				complaintLog.setAssignedToId(complaintAssigned);
				
				List<ComplaintAssigned> lstofComplaintAssigned = iComplaintService.getComplaintAssigned(complaimtCreationDt.getComplaintFromId());
				
				if(lstofComplaintAssigned != null && lstofComplaintAssigned.size()>0){
					complaintLog.setAssignedToCode(lstofComplaintAssigned.get(0).getComplaintAssignedCode());
				}
				
				// complaintLog.setAssignedToCode(complaimtCreationDt.getComplaintAssignedTo());
				String complaintType = iComplaintService.toCheckComplaintTypeFromUserProfile(sessionStateManage.getEmployeeId());
				if (complaintType != null) {
					List<ComplaintMatrix> lstComplaintMatrix = iComplaintService.toFetchBasedOnTheseCombination(sessionStateManage.getCountryId(), complaimtCreationDt.getCountryId(), complaimtCreationDt.getBankId(), complaimtCreationDt.getServiceId(), complaimtCreationDt.getComplaintTypeId(),
							complaimtCreationDt.getComplaintFromId());
					if (lstComplaintMatrix.size() > 0) {
						complaintLog.setAssignedToId(complaintAssigned);
						if (complaintType.equalsIgnoreCase(Constants.ASSIGNED_CUSTOMER_HELP_DESK)) {
							complaintLog.setAssignedToCode(mapComplaintAssignedIdCodecList.get(complaimtCreationDt.getComplaintFromId()));
						} else if (complaintType.equalsIgnoreCase(Constants.ASSIGNED_CUSTOMER_SUPPORT_SERVICE)) {
							complaintLog.setAssignedToCode(mapComplaintAssignedIdCodecList.get(complaimtCreationDt.getComplaintFromId()));
						}
					} else {
						complaintLog.setAssignedToCode(mapComplaintAssignedIdCodecList.get(complaimtCreationDt.getComplaintFromId()));
					}
				}
				// getComplaintAssignedFromCode
				complaintLog.setComplaintFromCode(mapComplaintAssignedIdCodecList.get(complaimtCreationDt.getComplaintFromId()));
				complaintLog.setTokenById(complaintAssigned);
				// getComplaintTakenByCode
				complaintLog.setTokenByCode(mapComplaintAssignedIdCodecList.get(complaimtCreationDt.getComplaintFromId()));
				// Complaint Taken By Master
				ComplaintTypeMaster complaintTypeMaster = new ComplaintTypeMaster();
				complaintTypeMaster.setComplaintTypeId(complaimtCreationDt.getComplaintTypeId());
				// getComplaintTypeCode
				complaintLog.setComplaintTypeCode(mapComplaintTypeIdCodeList.get(complaimtCreationDt.getComplaintTypeId()));
				complaintLog.setComplaintType(complaintTypeMaster);

				// Remarks Master
				ComplaintRemarksMaster complaintRemarksMaster = new ComplaintRemarksMaster();
				complaintRemarksMaster.setComplaintRemarksId(complaimtCreationDt.getRemarksCodeId());
				complaintLog.setComplaintRemarks(complaintRemarksMaster);
				// getComplaintRemarksCode
				complaintLog.setComplaintRemarksCode(mapRemarksIdCodeList.get(complaimtCreationDt.getRemarksCodeId()));
				complaintLog.setLogDate(complaimtCreationDt.getLogDate());

				// complaint User Finance Year
				complaintLog.setComplaintFinancialYear(complaintdealYear);
				// getForienCurrenyName
				complaintLog.setForiegnCurrencyName(complaimtCreationDt.getCurrencyName());
				// getForien Currency Amount
				complaintLog.setForiegnCurrencyAmount(complaimtCreationDt.getAmount());
				// common variables
				complaintLog.setCreatedBy(complaimtCreationDt.getCreatedBy());
				complaintLog.setCreatedDate(complaimtCreationDt.getCreatedDate());
				complaintLog.setIsActive(complaimtCreationDt.getIsActive());
				complaintLog.setModifiedBy(complaimtCreationDt.getModifiedBy());
				complaintLog.setModifiedDate(complaimtCreationDt.getModifiedDate());
				complaintLog.setRemarks(complaimtCreationDt.getRemarks());
				complaintLog.setApprovedBy(complaimtCreationDt.getApprovedBy());
				complaintLog.setApprovedDate(complaimtCreationDt.getApprovedDate());
				complaintLog.setComplaintLogId(complaimtCreationDt.getComplaintLogPk());
				upDateRemitTranscation(complaimtCreationDt);
				iComplaintService.saveComplaintLog(complaintLog);
			}
			/*
			 * RequestContext.getCurrentInstance().execute("complete.show();");
			 * clearAllFields(); return;
			 */
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveCompleteCreation");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void upDateRemitTranscation(ComplaimtCreationDataTable dataTable) {
		/*
		 * String remitComplaintId=
		 * iComplaintService.tofetchDocumentIdBasedOnDocumentCode
		 * (dataTable.getRemittanceDocNo());
		 * 
		 * if(remitComplaintId != null){
		 * iComplaintService.updateRemiTransctionPrijectStatus(new
		 * BigDecimal(remitComplaintId),sessionStateManage.getUserName());
		 * }else{
		 * RequestContext.getCurrentInstance().execute("recordAlreadyExist.show();"
		 * ); return; }
		 */
		try {
			iComplaintService.updateRemitComplaintProblemStatus(dataTable.getRemittanceDocNo());
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::upDateRemitTranscation");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void clickOnOKSave() {
		complaimtCreationDtList.clear();
		clearAllFields();
		setBooRenderDataTable(false);
		setBooRenderSaveOrExit(false);
		setBooReadOnly(false);
		setBooRenderEditfalse(true);
		setBooRenderEdit(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintcreation.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void edit(ComplaimtCreationDataTable dataTable) {
		// Complaint creation
		setBooRenderEditfalse(false);
		setBooRenderEdit(true);
		setRemitdealReference(dataTable.getRemittanceDocNo());
		setComplaintdealReference(dataTable.getComplaintNo());
		setComplaintlogDealReference(dataTable.getComplaintNo());
		// remittance
		setCountryId(dataTable.getCountryId());
		setCountryName(dataTable.getCountryName());
		setBankId(dataTable.getBankId());
		setBankName(dataTable.getBankName());
		setRemittanceDate(dataTable.getRemitDate());
		setBranchId(dataTable.getBranchId());
		setBranchName(dataTable.getBranchName());
		setServiceId(dataTable.getServiceId());
		setServiceName(dataTable.getServiceName());
		setCustomerId(dataTable.getCustomerId().toPlainString());
		setCustomerName(dataTable.getCustomerName());
		setMobileNumber(dataTable.getMobileNumber().toPlainString());
		setRemitAmount(dataTable.getAmount());
		setBeneficiaryName(dataTable.getBeneficiary());
		setAccountNumber(dataTable.getAccountNumber());
		setCurrencyName(dataTable.getCurrencyName());
		// local Varialbels complaint
		setComplaintFromId(dataTable.getComplaintFromId());
		setComplaintFromName(dataTable.getComplaintFrom());
		setComplaintTypeId(dataTable.getComplaintTypeId());
		setComplaintTypeName(dataTable.getComplaintTypeName());
		setComplaintRemarksCodeId(dataTable.getRemarksCodeId());
		setComplaintRemarksName(dataTable.getRemarksCode());
		setComplaintRemarks(dataTable.getRemarks());
		setCreatedBy(dataTable.getCreatedBy());
		setCreatedDate(dataTable.getCreatedDate());
		setComplaintLogPk(dataTable.getComplaintLogPk());
		setModifiedBy(sessionStateManage.getUserName());
		setModifiedDate(new Date());
		setIsActive(Constants.U);
		setBooReadOnly(true);
		setBooSubmitPanel(true);
		complaimtCreationDtList.remove(dataTable);
		if (complaimtCreationDtList.size() == 0) {
			setBooRenderDataTable(false);
			setBooRenderSaveOrExit(false);
		}

	}

	public int getDocumentSerialId() {
		return Integer.parseInt(documentSerialID(processIn));
	}

	public void checkRemarksStatus() {
		try {
			Boolean remarksStatus = iComplaintService.isRemittanceStatus(getComplaintRemarksCodeId(), getDocumentId());
			if (remarksStatus == false) {
				RequestContext.getCurrentInstance().execute("alreadyTagged.show();");
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::checkRemarksStatus");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void disableSubmitButton() {
		setBooSubmitPanel(true);
	}

	private String complaintAssignedTo;
	private String complaintTypeTaken;
	private BigDecimal remitDocumnetId;

	public BigDecimal getRemitDocumnetId() {
		return remitDocumnetId;
	}

	public void setRemitDocumnetId(BigDecimal remitDocumnetId) {
		this.remitDocumnetId = remitDocumnetId;
	}

	public String getComplaintAssignedTo() {
		return complaintAssignedTo;
	}

	public void setComplaintAssignedTo(String complaintAssignedTo) {
		this.complaintAssignedTo = complaintAssignedTo;
	}

	public String getComplaintTypeTaken() {
		return complaintTypeTaken;
	}

	public void setComplaintTypeTaken(String complaintTypeTaken) {
		this.complaintTypeTaken = complaintTypeTaken;
	}

	private BigDecimal documentNoForNoRecord;

	public BigDecimal getDocumentNoForNoRecord() {
		return documentNoForNoRecord;
	}

	public void setDocumentNoForNoRecord(BigDecimal documentNoForNoRecord) {
		this.documentNoForNoRecord = documentNoForNoRecord;
	}

	public Boolean getBooProcedureDialog() {
		return booProcedureDialog;
	}

	public void setBooProcedureDialog(Boolean booProcedureDialog) {
		this.booProcedureDialog = booProcedureDialog;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
