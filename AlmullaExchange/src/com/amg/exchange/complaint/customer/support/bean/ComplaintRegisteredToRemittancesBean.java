package com.amg.exchange.complaint.customer.support.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.complaint.bean.EmailBodyDataTable;
import com.amg.exchange.complaint.customer.support.model.ComplaintFollowup;
import com.amg.exchange.complaint.customer.support.model.ComplaintLog;
import com.amg.exchange.complaint.customer.support.service.IComplaintRegisteredToRemittancesService;
import com.amg.exchange.complaint.model.CommunicationMethod;
import com.amg.exchange.complaint.model.CommunicationMethodDesc;
import com.amg.exchange.complaint.model.ComplaintAction;
import com.amg.exchange.complaint.model.ComplaintActionDesc;
import com.amg.exchange.complaint.model.ComplaintAssigned;
import com.amg.exchange.complaint.model.ComplaintAssignedDesc;
import com.amg.exchange.complaint.model.ComplaintMatrix;
import com.amg.exchange.complaint.model.ComplaintRemarksDesc;
import com.amg.exchange.complaint.model.ComplaintRemarksMaster;
import com.amg.exchange.complaint.service.IComplaintService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.mail.ApplicationMailer;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("complaintRegisteredToRemittancesBean")
@Scope("session")
public class ComplaintRegisteredToRemittancesBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal idNo;
	private BigDecimal serviceId;
	private BigDecimal bankId;
	private BigDecimal countryId;
	private BigDecimal complaintTypeId;
	private String complaintTypeCode;
	private String complaintTypeDesc;
	private String bankFullName;
	private String countryName;
	private String serviceDescription;
	private BigDecimal totalComplaint;
	private BigDecimal applicationCountryId;
	private BigDecimal remarksId;
	private BigDecimal followupActionId;
	private String followUpActionDesc;
	private String assignedTo;
	private String remarks;
	private String complaintAssignedTo;
	private String communicationMethod;
	private String communicationMethodCode;
	private BigDecimal companyId;
	private String complaintReference;
	private Date followUpDate;
	private String remarksCode;
	private BigDecimal assignedId;
	private String assignedToCode;
	private Date assignedDate;
	private BigDecimal communicationMethodId;
	private BigDecimal complaintActionId;
	private String complaintActionCode;
	private String emailTo;
	private String emailSubject;
	private String emailText;
	private String testKey;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private Boolean booCheckBox = false;
	private Boolean booDataTableEmailRender = false;
	private BigDecimal complaintdealYear;
	
	private BigDecimal remittanceReference;
	private BigDecimal remittanceDocumentFinanceYr;
	

	private Boolean status;
	
	private String navFlag;
	
	private String takenCode;

	private Boolean booComplaintRegisteredToRemittances = false;
	private Boolean booComplaintRegisteredToRemittancesDataTable = false;

	SessionStateManage sessionStateManage = new SessionStateManage();

	private List<ComplaintLog> lstOfComplaintLogs = new ArrayList<ComplaintLog>();
	private List<ComplaintMatrix> lstOfComplaintMatrixs = new ArrayList<ComplaintMatrix>();
	private List<ComplaintActionDesc> lstActionDescs = new ArrayList<ComplaintActionDesc>();
	private List<ComplaintRemarksDesc> listComplaintRemarksDesc = new ArrayList<ComplaintRemarksDesc>();
	private Map<BigDecimal,String> listComplaintRemarks = new HashMap<BigDecimal,String>();
	private List<ComplaintAssignedDesc> listComplaintAssignedDesc = new ArrayList<ComplaintAssignedDesc>();
	private List<CommunicationMethodDesc> listCommunicationMethodDesc = new ArrayList<CommunicationMethodDesc>();
	private List<ComplaintRemarksMaster> listComplaintRemarksMasters = new ArrayList<ComplaintRemarksMaster>();
	private List<UserFinancialYear> complaintdealYearList = new ArrayList<UserFinancialYear>();
	private List<EmailBodyDataTable> lstAllEmailData = new ArrayList<EmailBodyDataTable>();
	private List<ComplaintRegisteredToRemittancesDataTable> lstOsSelectedRegToRemittance = new ArrayList<ComplaintRegisteredToRemittancesDataTable>();
	private List<ComplaintRegisteredToRemittancesDataTable> lstOfComplaintRegisteredToRemittancesDataTables = new ArrayList<ComplaintRegisteredToRemittancesDataTable>();

	private List<ComplaintFollowup> lstOfComplaintFollowups = new ArrayList<ComplaintFollowup>();
	//List<BigDecimal> allRemitDocNum = new ArrayList<BigDecimal>();
	
	@Autowired
	IComplaintRegisteredToRemittancesService<T> complaintRegisteredToRemittancesService;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IForeignCurrencyPurchaseService<T> iForeignCurrencyPurchaseService;
	
	@Autowired
	IComplaintService<T> icomplaintService;
	
	@Autowired
	ApplicationMailer appMailer;

	public ComplaintRegisteredToRemittancesBean() {
	}
	
private String errorMessage;
	
	
	
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getIdNo() {
		return idNo;
	}

	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}

	public BigDecimal getServiceId() {
		return serviceId;
	}

	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(BigDecimal complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}

	public String getComplaintTypeCode() {
		return complaintTypeCode;
	}

	public void setComplaintTypeCode(String complaintTypeCode) {
		this.complaintTypeCode = complaintTypeCode;
	}

	public String getComplaintTypeDesc() {
		return complaintTypeDesc;
	}

	public void setComplaintTypeDesc(String complaintTypeDesc) {
		this.complaintTypeDesc = complaintTypeDesc;
	}

	public String getBankFullName() {
		return bankFullName;
	}

	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public BigDecimal getTotalComplaint() {
		return totalComplaint;
	}

	public void setTotalComplaint(BigDecimal totalComplaint) {
		this.totalComplaint = totalComplaint;
	}
	
	public String getTakenCode() {
		return takenCode;
	}

	public void setTakenCode(String takenCode) {
		this.takenCode = takenCode;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public List<ComplaintLog> getLstOfComplaintLogs() {
		return lstOfComplaintLogs;
	}

	public void setLstOfComplaintLogs(List<ComplaintLog> lstOfComplaintLogs) {
		this.lstOfComplaintLogs = lstOfComplaintLogs;
	}

	public BigDecimal getRemarksId() {
		return remarksId;
	}

	public void setRemarksId(BigDecimal remarksId) {
		this.remarksId = remarksId;
	}

	public BigDecimal getFollowupActionId() {
		return followupActionId;
	}

	public void setFollowupActionId(BigDecimal followupActionId) {
		this.followupActionId = followupActionId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getComplaintAssignedTo() {
		return complaintAssignedTo;
	}

	public void setComplaintAssignedTo(String complaintAssignedTo) {
		this.complaintAssignedTo = complaintAssignedTo;
	}

	public String getCommunicationMethod() {
		return communicationMethod;
	}

	public void setCommunicationMethod(String communicationMethod) {
		this.communicationMethod = communicationMethod;
	}

	public List<ComplaintMatrix> getLstOfComplaintMatrixs() {
		return lstOfComplaintMatrixs;
	}

	public void setLstOfComplaintMatrixs(List<ComplaintMatrix> lstOfComplaintMatrixs) {
		this.lstOfComplaintMatrixs = lstOfComplaintMatrixs;
	}

	public String getFollowUpActionDesc() {
		return followUpActionDesc;
	}

	public void setFollowUpActionDesc(String followUpActionDesc) {
		this.followUpActionDesc = followUpActionDesc;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public List<ComplaintActionDesc> getLstActionDescs() {
		return lstActionDescs;
	}

	public void setLstActionDescs(List<ComplaintActionDesc> lstActionDescs) {
		this.lstActionDescs = lstActionDescs;
	}

	public List<ComplaintRemarksDesc> getListComplaintRemarksDesc() {
		return listComplaintRemarksDesc;
	}

	public void setListComplaintRemarksDesc(List<ComplaintRemarksDesc> listComplaintRemarksDesc) {
		this.listComplaintRemarksDesc = listComplaintRemarksDesc;
	}

	public String getCommunicationMethodCode() {
		return communicationMethodCode;
	}

	public void setCommunicationMethodCode(String communicationMethodCode) {
		this.communicationMethodCode = communicationMethodCode;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	
	

	public BigDecimal getRemittanceReference() {
		return remittanceReference;
	}

	public void setRemittanceReference(BigDecimal remittanceReference) {
		this.remittanceReference = remittanceReference;
	}

	public BigDecimal getRemittanceDocumentFinanceYr() {
		return remittanceDocumentFinanceYr;
	}

	public void setRemittanceDocumentFinanceYr(BigDecimal remittanceDocumentFinanceYr) {
		this.remittanceDocumentFinanceYr = remittanceDocumentFinanceYr;
	}

	public String getComplaintReference() {
		return complaintReference;
	}

	public void setComplaintReference(String complaintReference) {
		this.complaintReference = complaintReference;
	}

	public Date getFollowUpDate() {
		return followUpDate;
	}

	public void setFollowUpDate(Date followUpDate) {
		this.followUpDate = followUpDate;
	}

	public String getRemarksCode() {
		return remarksCode;
	}

	public void setRemarksCode(String remarksCode) {
		this.remarksCode = remarksCode;
	}

	public BigDecimal getAssignedId() {
		return assignedId;
	}

	public void setAssignedId(BigDecimal assignedId) {
		this.assignedId = assignedId;
	}

	public String getAssignedToCode() {
		return assignedToCode;
	}

	public void setAssignedToCode(String assignedToCode) {
		this.assignedToCode = assignedToCode;
	}

	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	public BigDecimal getCommunicationMethodId() {
		return communicationMethodId;
	}

	public void setCommunicationMethodId(BigDecimal communicationMethodId) {
		this.communicationMethodId = communicationMethodId;
	}

	public BigDecimal getComplaintActionId() {
		return complaintActionId;
	}

	public void setComplaintActionId(BigDecimal complaintActionId) {
		this.complaintActionId = complaintActionId;
	}

	public String getComplaintActionCode() {
		return complaintActionCode;
	}

	public void setComplaintActionCode(String complaintActionCode) {
		this.complaintActionCode = complaintActionCode;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailText() {
		return emailText;
	}

	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}

	public String getTestKey() {
		return testKey;
	}

	public void setTestKey(String testKey) {
		this.testKey = testKey;
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
	
	

	public String getNavFlag() {
		return navFlag;
	}

	public void setNavFlag(String navFlag) {
		this.navFlag = navFlag;
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
	
	


	public Boolean getBooCheckBox() {
		return booCheckBox;
	}

	public void setBooCheckBox(Boolean booCheckBox) {
		this.booCheckBox = booCheckBox;
	}

	public List<UserFinancialYear> getComplaintdealYearList() {
		return complaintdealYearList;
	}

	public void setComplaintdealYearList(List<UserFinancialYear> complaintdealYearList) {
		this.complaintdealYearList = complaintdealYearList;
	}

	public BigDecimal getComplaintdealYear() {
		return complaintdealYear;
	}

	public void setComplaintdealYear(BigDecimal complaintdealYear) {
		this.complaintdealYear = complaintdealYear;
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}
	
	public Boolean getBooDataTableEmailRender() {
		return booDataTableEmailRender;
	}

	public void setBooDataTableEmailRender(Boolean booDataTableEmailRender) {
		this.booDataTableEmailRender = booDataTableEmailRender;
	}

	public List<ComplaintRegisteredToRemittancesDataTable> getLstOsSelectedRegToRemittance() {
		return lstOsSelectedRegToRemittance;
	}

	public void setLstOsSelectedRegToRemittance(
			List<ComplaintRegisteredToRemittancesDataTable> lstOsSelectedRegToRemittance) {
		this.lstOsSelectedRegToRemittance = lstOsSelectedRegToRemittance;
	}

	public List<ComplaintRemarksDesc> getLstComplaintRemarksDesc() {
		listComplaintRemarks.clear();
		try{
		listComplaintRemarksDesc = getComplaintRegisteredToRemittancesService().getComplaintRemarksDescList(sessionStateManage.getLanguageId());
		if(listComplaintRemarksDesc.size() != 0){
			for (ComplaintRemarksDesc lstremrks : listComplaintRemarksDesc) {
				listComplaintRemarks.put(lstremrks.getComplaintRemarks().getComplaintRemarksId(), lstremrks.getFullDesc());
			}
		}
		
		return listComplaintRemarksDesc;
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::getLstComplaintRemarksDesc");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return null;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;
		}
	}
	
	

	public void fetchComplaintfinanceYear() {

		// complaintdealYearList.clear();

		try {

			List<UserFinancialYear> lstCurrentDealYear = generalService.getDealYear(new Date());
			if (lstCurrentDealYear.size() != 0) {
				BigDecimal financialYear = lstCurrentDealYear.get(0).getFinancialYear();
				setComplaintdealYear(financialYear);

			}

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::fetchComplaintfinanceYear");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public String documentSerialID(String processIn) {

		try{
		fetchComplaintfinanceYear();
		String documentSerialID = generalService.getNextDocumentReferenceNumber(sessionStateManage.getCountryId().intValue(), sessionStateManage.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_COMPLAINTCREATION), getComplaintdealYear().intValue(), processIn,sessionStateManage.getCountryBranchCode());
		return documentSerialID;

		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::documentSerialID");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return null;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;
		}
	}

	public List<ComplaintRegisteredToRemittancesDataTable> getLstOfComplaintRegisteredToRemittancesDataTables() {
		return lstOfComplaintRegisteredToRemittancesDataTables;
	}

	public void setLstOfComplaintRegisteredToRemittancesDataTables(List<ComplaintRegisteredToRemittancesDataTable> lstOfComplaintRegisteredToRemittancesDataTables) {
		this.lstOfComplaintRegisteredToRemittancesDataTables = lstOfComplaintRegisteredToRemittancesDataTables;
	}

	public IComplaintRegisteredToRemittancesService<T> getComplaintRegisteredToRemittancesService() {
		return complaintRegisteredToRemittancesService;
	}

	public void setComplaintRegisteredToRemittancesService(IComplaintRegisteredToRemittancesService<T> complaintRegisteredToRemittancesService) {
		this.complaintRegisteredToRemittancesService = complaintRegisteredToRemittancesService;
	}

	public Boolean getBooComplaintRegisteredToRemittances() {
		return booComplaintRegisteredToRemittances;
	}

	public void setBooComplaintRegisteredToRemittances(Boolean booComplaintRegisteredToRemittances) {
		this.booComplaintRegisteredToRemittances = booComplaintRegisteredToRemittances;
	}

	public Boolean getBooComplaintRegisteredToRemittancesDataTable() {
		return booComplaintRegisteredToRemittancesDataTable;
	}

	public void setBooComplaintRegisteredToRemittancesDataTable(Boolean booComplaintRegisteredToRemittancesDataTable) {
		this.booComplaintRegisteredToRemittancesDataTable = booComplaintRegisteredToRemittancesDataTable;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<ComplaintAssignedDesc> getListComplaintAssignedDesc() {
		return listComplaintAssignedDesc;
	}

	public void setListComplaintAssignedDesc(List<ComplaintAssignedDesc> listComplaintAssignedDesc) {
		this.listComplaintAssignedDesc = listComplaintAssignedDesc;
	}

	public List<CommunicationMethodDesc> getListCommunicationMethodDesc() {
		return listCommunicationMethodDesc;
	}

	public void setListCommunicationMethodDesc(List<CommunicationMethodDesc> listCommunicationMethodDesc) {
		this.listCommunicationMethodDesc = listCommunicationMethodDesc;
	}

	public List<EmailBodyDataTable> getLstAllEmailData() {
		return lstAllEmailData;
	}

	public void setLstAllEmailData(List<EmailBodyDataTable> lstAllEmailData) {
		this.lstAllEmailData = lstAllEmailData;
	}

	public void complaintRequestToRemittancePageNavigation() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintregisteredtoremittance.xhtml");
			setCommunicationMethod(null);
			setAssignedTo(null);
			setLstActionDescs(null);
			setFollowupActionId(null);
			setBooCheckBox(getBooCheckBox());

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::complaintRequestToRemittancePageNavigation");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void complaintRequestToRemittanceFollowupActionPageNavigation() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintregisteredtoremittancefollowup.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::complaintRequestToRemittanceFollowupActionPageNavigation");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void exitComplaintRequestToRemittanceFollowupAction() {

		try {
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintsummary.xhtml");
			clearAll();

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::exitComplaintRequestToRemittanceFollowupAction");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void complaintSummaryPageNavigation() {
		try {
			lstOsSelectedRegToRemittance.clear();
			clearAll();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintsummary.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::complaintSummaryPageNavigation");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void populateSearchValue() {
		HttpSession session = sessionStateManage.getSession();
		@SuppressWarnings("unchecked")
		ComplaintRegisteredToRemittancesBean<T> complaintRegisteredToRemittancesBean = (ComplaintRegisteredToRemittancesBean<T>) session.getAttribute("complaintRegisteredObject");

		try{
		if (complaintRegisteredToRemittancesBean != null) {

			setBankFullName(complaintRegisteredToRemittancesBean.getBankFullName());
			setBankId(complaintRegisteredToRemittancesBean.getBankId());
			setComplaintTypeCode(complaintRegisteredToRemittancesBean.getComplaintTypeCode());
			setComplaintTypeDesc(complaintRegisteredToRemittancesBean.getComplaintTypeDesc());
			setComplaintTypeId(complaintRegisteredToRemittancesBean.getComplaintTypeId());
			setCountryId(complaintRegisteredToRemittancesBean.getCountryId());
			setCountryName(complaintRegisteredToRemittancesBean.getCountryName());
			setServiceDescription(complaintRegisteredToRemittancesBean.getServiceDescription());
			setServiceId(complaintRegisteredToRemittancesBean.getServiceId());
			setTotalComplaint(complaintRegisteredToRemittancesBean.getTotalComplaint());
			setIdNo(complaintRegisteredToRemittancesBean.getIdNo());
			setBooCheckBox(false);
			setApplicationCountryId(complaintRegisteredToRemittancesBean.getApplicationCountryId());
			setNavFlag(complaintRegisteredToRemittancesBean.getNavFlag());
			if(complaintRegisteredToRemittancesBean.getRemittanceDocumentFinanceYr() !=null)
				setRemittanceDocumentFinanceYr(complaintRegisteredToRemittancesBean.getRemittanceDocumentFinanceYr());
			if(complaintRegisteredToRemittancesBean.getCompanyId()!=null)
				setCompanyId(complaintRegisteredToRemittancesBean.getCompanyId());
			
			if(complaintRegisteredToRemittancesBean.getRemittanceReference() !=null)
				setRemittanceReference(complaintRegisteredToRemittancesBean.getRemittanceReference());

			
			session.removeAttribute("complaintRegisteredObject");

			setBooComplaintRegisteredToRemittancesDataTable(true);
			setBooComplaintRegisteredToRemittances(true);
			
			fetchAllRemittanceDetails();

		}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::populateSearchValue");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void complaintRequestToFollowup(ComplaintRegisteredToRemittancesDataTable dataTableObj) {

		System.out.println("Boooooooooooooooo Checkkkkkkkkk ================ > " + dataTableObj.getBooCheckbox());
try{
		if(dataTableObj.getBooCheckbox()){
			lstOsSelectedRegToRemittance.add(dataTableObj);
		}else if(dataTableObj.getBooCheckbox() == false){
			for (int i = 0; i < lstOsSelectedRegToRemittance.size(); i++) {
				ComplaintRegisteredToRemittancesDataTable selectedrec = lstOsSelectedRegToRemittance.get(i);
				lstOsSelectedRegToRemittance.remove(selectedrec);
			}
		}
}catch (NullPointerException ne) {
	// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
	setErrorMessage("Method Name::complaintRequestToFollowup");
	RequestContext.getCurrentInstance().execute("nullPointerId.show();");
	return;
} catch (Exception exception) {
	// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
	setErrorMessage(exception.getMessage());
	RequestContext.getCurrentInstance().execute("error.show();");
	return;
}
	}

	public void actionComplaintRequestToRemittanceFollowUpAction() {
		
		try{

		for (ComplaintRegisteredToRemittancesDataTable list : lstOfComplaintRegisteredToRemittancesDataTables) {

			System.out.println("BOOLEAN CHECK " + list.getBooCheckbox());

			lstOfComplaintMatrixs = getComplaintRegisteredToRemittancesService().getComplaintMatrixs(sessionStateManage.getCountryId(),getCountryId(), getServiceId(), getComplaintTypeId(), getBankId());


			if( !list.getBooCheckbox().equals(true)){
				RequestContext.getCurrentInstance().execute("atleastone.show();");
				return;
			}
			if (lstOfComplaintMatrixs != null ) {
				

				setBooCheckBox(list.getBooCheckbox());

				for (ComplaintMatrix complaintMatrix : lstOfComplaintMatrixs) {

					lstActionDescs = getComplaintRegisteredToRemittancesService().getComplaintActionDesc(complaintMatrix.getComplaintAction().getComplaintActionId(), sessionStateManage.getLanguageId());

					if (lstActionDescs != null) {
						for (ComplaintActionDesc complaintActionDesc : lstActionDescs) {
							setComplaintActionId(complaintActionDesc.getComplaintAction().getComplaintActionId());
							setComplaintActionCode(complaintActionDesc.getComplaintAction().getComplaintActionCode());
						}

					}
					setLstActionDescs(lstActionDescs);

				}
				getLstComplaintRemarksDesc();
				populateDataClear();

				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintregisteredtoremittancefollowup.xhtml");

				} catch (Exception e) {

				}
			} else {
				RequestContext.getCurrentInstance().execute("noRecords.show();");
			}
		}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::actionComplaintRequestToRemittanceFollowUpAction");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void complaintActionDetails() {
		try{
		lstOfComplaintMatrixs = getComplaintRegisteredToRemittancesService().getComplaintMatrixs(sessionStateManage.getCountryId(),getCountryId(), getServiceId(), getComplaintTypeId(), getBankId());

		if (lstOfComplaintMatrixs != null) {

			for (ComplaintMatrix complaintMatrix : lstOfComplaintMatrixs) {

				listCommunicationMethodDesc = getComplaintRegisteredToRemittancesService().getCommunicationMethodDescList(complaintMatrix.getCommunicationMethod().getComMethodId(), sessionStateManage.getLanguageId());

				if (listCommunicationMethodDesc != null) {
					for (CommunicationMethodDesc communicationMethodDesc : listCommunicationMethodDesc) {
						setCommunicationMethod(communicationMethodDesc.getFullDescription());
						setCommunicationMethodId(communicationMethodDesc.getCommunicationMethodId().getComMethodId());
						setCommunicationMethodCode(communicationMethodDesc.getCommunicationMethodId().getComMethodCode());

					}

				}

				listComplaintAssignedDesc = getComplaintRegisteredToRemittancesService().getComplaintAssignedDescList(complaintMatrix.getcomplaintAssigned().getComplaintAssignedId(), sessionStateManage.getLanguageId());

				if (listComplaintAssignedDesc != null) {
					for (ComplaintAssignedDesc complaintAssignedDesc : listComplaintAssignedDesc) {

						setAssignedTo(complaintAssignedDesc.getFullDescription());
						setAssignedId(complaintAssignedDesc.getComplaintAssigned().getComplaintAssignedId());
						setAssignedToCode(complaintAssignedDesc.getComplaintAssigned().getComplaintAssignedCode());

					}

				}

			}
			
			fetchEmailData();

			/*try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintregisteredtoremittancefollowup.xhtml");
			} catch (Exception e) {

			}*/
		} else {
			RequestContext.getCurrentInstance().execute("noRecords.show();");
		}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::complaintActionDetails");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	
	public void saveComplaintFollowUp(){
		
		try{
		
		if(lstAllEmailData.size() != 0){
			int i = 0;
			for (EmailBodyDataTable lstdata : lstAllEmailData) {
				if(lstdata.getEmailBody() != null && !lstdata.getEmailBody().equalsIgnoreCase("")){
					i = 0;
				}else{
					i = 1;
					break;
				}
			}
			
			if(i==1){
				RequestContext.getCurrentInstance().execute("emailbodyError.show();");
			}else{
				saveAll();
			}
			
		}else{
			saveAll();
		}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveComplaintFollowUp");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	

	public void saveAll() {
		try{
		String emailTOData = null ;
		String emailCC = null ;

		for (ComplaintRegisteredToRemittancesDataTable list : lstOfComplaintRegisteredToRemittancesDataTables) {
			
			emailTOData = null ;
			emailCC = null ;

			if (list.getBooCheckbox().equals(true)) {
				fetchComplaintfinanceYear();

				ComplaintFollowup complaintFollowup = new ComplaintFollowup();

				complaintFollowup.setCommunicationMathodCode(getCommunicationMethodCode());

				CommunicationMethod communicationMethod = new CommunicationMethod();
				communicationMethod.setComMethodId(getCommunicationMethodId());
				complaintFollowup.setCommunicationMethodId(communicationMethod);

				ComplaintAssigned complaintAssigned = new ComplaintAssigned();
				complaintAssigned.setComplaintAssignedId(getAssignedId());
				complaintFollowup.setAssigntoId(complaintAssigned);

				complaintFollowup.setComplaintFinanceYear(getComplaintdealYear());

				Document document = new Document();
				document.setDocumentID(generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_COMPLAINTCREATION), sessionStateManage.getLanguageId()).get(0).getDocumentID());
				complaintFollowup.setDocumentId(document);

				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(sessionStateManage.getCompanyId());
				complaintFollowup.setCompanyId(companyMaster);

				ComplaintAction complaintAction = new ComplaintAction();
				complaintAction.setComplaintActionId(getComplaintActionId());
				complaintFollowup.setComplaintActionId(complaintAction);

				ComplaintRemarksMaster complaintRemarksMaster = new ComplaintRemarksMaster();
				complaintRemarksMaster.setComplaintRemarksId(getRemarksId());
				complaintFollowup.setRemarksId(complaintRemarksMaster);

				listComplaintRemarksMasters = getComplaintRegisteredToRemittancesService().getComplaintRemarksDescCode(getRemarksId());
				if (listComplaintRemarksMasters != null) {
					for (ComplaintRemarksMaster complaintRemarks : listComplaintRemarksMasters) {
						complaintFollowup.setRemarksCode(getRemarksCode());
						setRemarksCode(complaintRemarks.getComplaintRemarksCode());
					}
				}
				complaintFollowup.setRemarksCode(getRemarksCode());
				complaintFollowup.setComplaintReference(list.getComplaintReference());

				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(sessionStateManage.getCountryId());
				complaintFollowup.setApplicationCountryId(countryMaster);

				complaintFollowup.setCreatedBy(sessionStateManage.getUserName());
				complaintFollowup.setCreatedDate(new Date());
				complaintFollowup.setRemarks(getRemarks());
				complaintFollowup.setComplaintActionCode(getComplaintActionCode());
				complaintFollowup.setAssigntoCode(getAssignedToCode());
				setAssignedDate(new Date());
				complaintFollowup.setAssignDate(getAssignedDate());
				complaintFollowup.setCommunicationMathodCode(getCommunicationMethodCode());
				complaintFollowup.setFollowupDate(new Date());
				
				complaintFollowup.setEmailTo(getEmailTo());
				if(lstAllEmailData.size() != 0){
					for (EmailBodyDataTable lstdata : lstAllEmailData) {
						if(lstdata.getComplaintReferenceNo().compareTo(list.getComplaintReference())==0){
							
							emailCC = lstdata.getEmailCC() ;
							if(emailCC != null){
								complaintFollowup.setEmailSubject(emailCC);
							}
							
							emailTOData = " Dear Sir " + " " + listComplaintRemarks.get(getRemarksId()) + " " + getRemarks() + " " + lstdata.getEmailBody();
							complaintFollowup.setEmailText(emailTOData);
							
						}
					}
					
				}
				//save FollowUp
				getComplaintRegisteredToRemittancesService().saveOrUpdate(complaintFollowup);
				//to update in remittance Complaint based on doc num problem status is changed to "Y" To "E"
				icomplaintService.updateRemitComplaintProblemStatus(list.getRemittanceDocumentNo());
				//to update in Complaint Log based on Complaint Reference Assign Id , Code , Date Changed
				icomplaintService.updateAssignToAssignDate(list.getComplaintReference(),getAssignedId(),getAssignedToCode(),getAssignedDate());
				//triggering Email to EmailTO and EmailCC
				if(emailTOData != null){
					appMailer.sendRegistrationMail(getEmailTo(), emailTOData, sessionStateManage.getUserName());
					
					if(emailCC != null){
						appMailer.sendRegistrationMail(emailCC, emailTOData, sessionStateManage.getUserName());
					}
				}
				
			}
			
			lstOsSelectedRegToRemittance.clear();
			RequestContext.getCurrentInstance().execute("complete.show();");

		}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveAll");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void clearAll() {

		setServiceId(null);
		setBankId(null);
		setCountryId(null);
		setComplaintTypeId(null);
		setComplaintTypeCode(null);
		setComplaintTypeDesc(null);
		setBankFullName(null);
		setCountryName(null);
		setServiceDescription(null);
		setTotalComplaint(null);
		setApplicationCountryId(null);
		setRemarksId(null);
		setFollowupActionId(null);
		setFollowUpActionDesc(null);
		setAssignedTo(null);
		setRemarks(null);
		setComplaintAssignedTo(null);
		setCommunicationMethod(null);
		setCommunicationMethodCode(null);
		setCompanyId(null);
		setComplaintReference(null);
		setFollowUpDate(null);
		setRemarksCode(null);
		setAssignedId(null);
		setAssignedToCode(null);
		setAssignedDate(null);
		setCommunicationMethodId(null);
		setComplaintActionId(null);
		setComplaintActionCode(null);
		setEmailTo(null);
		setEmailSubject(null);
		setEmailText(null);
		setTestKey(null);
		lstAllEmailData.clear();

	}
	
	public void fetchEmailData(){
		
		lstAllEmailData.clear();
		try{
		Boolean emailcheck = icomplaintService.emailcheck(getCommunicationMethodId());
		
		if (emailcheck) {
			setBooDataTableEmailRender(true);
			for (ComplaintRegisteredToRemittancesDataTable lstSel : lstOsSelectedRegToRemittance) {
				
				EmailBodyDataTable emailData = new EmailBodyDataTable();
				
				emailData.setEmailRemittanceYear(lstSel.getRemittanceDocumentFinancialYear());
				emailData.setEmailRemittanceDocNo(lstSel.getRemittanceDocumentNo());
				emailData.setComplaintReferenceNo(lstSel.getComplaintReference());
				emailData.setEmailCC("");
				emailData.setEmailBody("");
				
				lstAllEmailData.add(emailData);

			}
			
			if(getAssignedTo().equalsIgnoreCase(Constants.BANK)){
				String emailbank = icomplaintService.basedonCountryandBankTogetEmail(getCountryId(), getBankId());
				if(emailbank != null){
					setEmailTo(emailbank);
				}else{
					setEmailTo(null);
				}
			}else{
				setEmailTo(null);
			}
			
		}else{
			lstAllEmailData.clear();
			setEmailTo(null);
			setBooDataTableEmailRender(false);
		}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::fetchEmailData");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	
	public void fetchAllRemittanceDetails(){
		lstOfComplaintRegisteredToRemittancesDataTables.clear();
try{
		if(getNavFlag().equals("1")){
			lstOfComplaintLogs = getComplaintRegisteredToRemittancesService().displayRemittancesRegisteredFromComplaintLog(getCountryId(), getServiceId(), getComplaintTypeId(), getBankId());
	
		}else if(getNavFlag().equals(Constants.TWO)){

			lstOfComplaintLogs = getComplaintRegisteredToRemittancesService().searchFromComplaintLog(getCompanyId(), getRemittanceReference(), getRemittanceDocumentFinanceYr());
			
			
		}
		
		if (lstOfComplaintLogs != null) {

			for (ComplaintLog complaintLog : lstOfComplaintLogs) {

				ComplaintRegisteredToRemittancesDataTable complaintRegisteredToRemittancesDataTable = new ComplaintRegisteredToRemittancesDataTable();

				complaintRegisteredToRemittancesDataTable.setComplaintLogId(complaintLog.getComplaintLogId());
				complaintRegisteredToRemittancesDataTable.setCustomerReference(complaintLog.getCustomerReference());
				complaintRegisteredToRemittancesDataTable.setRemittanceDocumentNo(complaintLog.getRemittanceDocumentNo());
				complaintRegisteredToRemittancesDataTable.setRemittanceDocumentFinancialYear(complaintLog.getRemittanceDocumentFinancialYear());
				complaintRegisteredToRemittancesDataTable.setRemittanceDocumentCode(complaintLog.getRemittanceDocumentCode());
				complaintRegisteredToRemittancesDataTable.setComplaintReference(complaintLog.getComplaintReference());
				if (complaintLog.getCountryBranchCode() != null) {
					List<CountryBranch> objList = getComplaintRegisteredToRemittancesService().getCountryBranchList(new BigDecimal(complaintLog.getCountryBranchCode()));

					if (objList != null) {
						for (CountryBranch countryBranch : objList) {
							complaintRegisteredToRemittancesDataTable.setCountryBranchName(countryBranch.getBranchName());
						}
					}
				}

				//System.out.println("complaintLog.getComplaintReference() ========== > " + complaintLog.getComplaintReference());

				//lstOfComplaintFollowups = getComplaintRegisteredToRemittancesService().getComplaintFollowUp(complaintLog.getCompanyMaster().getCompanyId(), complaintLog.getComplaintReference(), complaintLog.getComplaintFinancialYear());
				lstOfComplaintFollowups = getComplaintRegisteredToRemittancesService().getComplaintFollowUp(complaintLog.getCompanyMaster().getCompanyId(), complaintLog.getComplaintReference(), complaintLog.getComplaintFinancialYear());

				if (lstOfComplaintFollowups != null) {

					for (ComplaintFollowup complaintFollowup : lstOfComplaintFollowups) {

						complaintRegisteredToRemittancesDataTable.setAssignedDate(complaintFollowup.getFollowupDate());

						listCommunicationMethodDesc = getComplaintRegisteredToRemittancesService().getCommunicationMethodDescList(complaintFollowup.getCommunicationMethodId().getComMethodId(), sessionStateManage.getLanguageId());

						if (listCommunicationMethodDesc != null) {
							for (CommunicationMethodDesc communicationMethodDesc : listCommunicationMethodDesc) {
								complaintRegisteredToRemittancesDataTable.setCommunicationMethodName(communicationMethodDesc.getFullDescription());

							}

						}

						listComplaintAssignedDesc = getComplaintRegisteredToRemittancesService().getComplaintAssignedDescList(complaintFollowup.getAssigntoId().getComplaintAssignedId(), sessionStateManage.getLanguageId());

						if (listComplaintAssignedDesc != null) {
							for (ComplaintAssignedDesc complaintAssignedDesc : listComplaintAssignedDesc) {

								complaintRegisteredToRemittancesDataTable.setAssignedDescription(complaintAssignedDesc.getFullDescription());

							}

						}

					}
				}

				complaintRegisteredToRemittancesDataTable.setLogDate(complaintLog.getLogDate());
				complaintRegisteredToRemittancesDataTable.setRemarks(complaintLog.getRemarks());
				complaintRegisteredToRemittancesDataTable.setRemittanceDate(complaintLog.getRemittanceDate());
				complaintRegisteredToRemittancesDataTable.setForiegnCurrencyAmount(complaintLog.getForiegnCurrencyAmount());
				complaintRegisteredToRemittancesDataTable.setBooCheckbox(Boolean.FALSE);

				lstOfComplaintRegisteredToRemittancesDataTables.add(complaintRegisteredToRemittancesDataTable);
			}

		}
}catch (NullPointerException ne) {
	// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
	setErrorMessage("Method Name::fetchAllRemittanceDetails");
	RequestContext.getCurrentInstance().execute("nullPointerId.show();");
	return;
} catch (Exception exception) {
	// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
	setErrorMessage(exception.getMessage());
	RequestContext.getCurrentInstance().execute("error.show();");
	return;
}
	}
	
	public void populateDataClear(){
		setBooDataTableEmailRender(false);
		lstAllEmailData.clear();
		setEmailTo(null);
	}

}
