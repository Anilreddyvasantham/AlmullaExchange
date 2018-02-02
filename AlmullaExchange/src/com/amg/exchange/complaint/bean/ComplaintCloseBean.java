package com.amg.exchange.complaint.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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

import com.amg.exchange.bco.bean.BranchComplianceDataTable;
import com.amg.exchange.common.dto.ComplaintActionDTO;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.complaint.DTO.ComplaintMatrixDTO;
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
import com.amg.exchange.complaint.model.ComplaintTypeDesc;
import com.amg.exchange.complaint.model.ComplaintTypeMaster;
import com.amg.exchange.complaint.service.IComplaintActionService;
import com.amg.exchange.complaint.service.IComplaintAssignedService;
import com.amg.exchange.complaint.service.IComplaintRemarksService;
import com.amg.exchange.complaint.service.IComplaintService;
import com.amg.exchange.complaint.service.IComplaintTypeService;
import com.amg.exchange.registration.bean.ArticleLevelDataTable;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.service.ICountryMasterservice;
import com.amg.exchange.remittance.dao.IServiceGroupMasterDao;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleDetailsDesc;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IBankMasterService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("unused")
@Component("complaintClose")
@Scope("session")
public class ComplaintCloseBean<T> implements Serializable {

	private static final Logger LOG = Logger
			.getLogger(ComplaintMatrixBean.class);

	/*
	 * Autowire Declaration start here
	 */
	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IBankBranchDetailsService<T> bankBranchDetailsService;

	@Autowired
	IComplaintService<T> complaintService;
	
	@Autowired
	IComplaintAssignedService complaintAssignedService;

	@Autowired
	IBankMasterService<T> bankMasterService;

	@Autowired
	ICountryMasterservice countryMasterService;

	@Autowired
	IServiceGroupMasterService serviceMasterService;

	@Autowired
	IComplaintTypeService<T> complaintTypeService;

	@Autowired
	IComplaintActionService actionService;
	
	@Autowired
	ServiceCodeMasterService iserviceCodeMasterService;
	
	@Autowired
    IComplaintRemarksService<T> iComplaintRemarksService;
	
	@Autowired
	IComplaintRegisteredToRemittancesService<T> complaintRegisteredToRemittancesService;


	/*
	 * Autowire declaration end here
	 */

	private static final long serialVersionUID = 1L;

	/*
	 * Properties declaration start here
	 */
	private BigDecimal transferRef;
	private BigDecimal complaintRef;
	private Date logDate;
	private Date transferDate;
	private String transferLocation;
	private BigDecimal mobileNumber;
	private String customerName;
	private String productName;
	private String payableAt;
	private String location;
	private BigDecimal Amount;
	private String complaintFromId;
	private String complaintTypeId;
	private String priority;
	private String otherRemarks;
	
	private Date closeDate;
	
	private BigDecimal complaintActionId;
	private BigDecimal complaintRemarksCodeId;
	
	
	SessionStateManage sessionStateManage = new SessionStateManage();
	
	private List<ComplaintActionDesc> complaintActionList = new ArrayList<ComplaintActionDesc>();
	private List<ComplaintRemarksDesc> lstComplaintRemarksFromComplaintRemarks = new ArrayList<ComplaintRemarksDesc>();
	
	private List<ComplaintLog> complaintLogList = new ArrayList<ComplaintLog>();
	
	private Map<BigDecimal, String> mapComplaintActionList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapRemarksIdDescList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapRemarksIdCodeList = new HashMap<BigDecimal, String>();
	
	public BigDecimal getComplaintRef() {
		return complaintRef;
	}

	public void setComplaintRef(BigDecimal complaintRef) {
		this.complaintRef = complaintRef;
	}
	
	
	public BigDecimal getComplaintActionId() {
		return complaintActionId;
	}

	public void setComplaintActionId(BigDecimal complaintActionId) {
		this.complaintActionId = complaintActionId;
	}
	
	
	public Map<BigDecimal, String> getMapRemarksIdCodeList() {
		return mapRemarksIdCodeList;
	}

	public void setMapRemarksIdCodeList(Map<BigDecimal, String> mapRemarksIdCodeList) {
		this.mapRemarksIdCodeList = mapRemarksIdCodeList;
	}
    
	public BigDecimal getComplaintRemarksCodeId() {
		return complaintRemarksCodeId;
	}

	public void setComplaintRemarksCodeId(BigDecimal complaintRemarksCodeId) {
		this.complaintRemarksCodeId = complaintRemarksCodeId;
	}
	
	

	public BigDecimal getAmount() {
		return Amount;
	}

	public void setAmount(BigDecimal amount) {
		Amount = amount;
	}

	public String getOtherRemarks() {
		return otherRemarks;
	}

	public void setOtherRemarks(String otherRemarks) {
		this.otherRemarks = otherRemarks;
	}

	public BigDecimal getTransferRef() {
		return transferRef;
	}

	public void setTransferRef(BigDecimal transferRef) {
		this.transferRef = transferRef;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public String getTransferLocation() {
		return transferLocation;
	}

	public void setTransferLocation(String transferLocation) {
		this.transferLocation = transferLocation;
	}

	public BigDecimal getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(BigDecimal mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPayableAt() {
		return payableAt;
	}

	public void setPayableAt(String payableAt) {
		this.payableAt = payableAt;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getComplaintFromId() {
		return complaintFromId;
	}

	public void setComplaintFromId(String complaintFromId) {
		this.complaintFromId = complaintFromId;
	}

	public String getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(String complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public List<ComplaintRemarksDesc> getLstComplaintRemarksFromComplaintRemarks() {
		
		
		lstComplaintRemarksFromComplaintRemarks.clear();
	    mapRemarksIdDescList.clear();
	    mapRemarksIdCodeList.clear();
	    List<ComplaintRemarksDesc> lstComplaintRemarks = iComplaintRemarksService.getAllComplaintRemarksForComplaintCreation(sessionStateManage.getCountryId(),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "" + 1));
	    if(lstComplaintRemarks.size() != 0){
		      lstComplaintRemarksFromComplaintRemarks.addAll(lstComplaintRemarks);
		      for (ComplaintRemarksDesc complaintRemarksDesc : lstComplaintRemarks) {
				mapRemarksIdDescList.put(complaintRemarksDesc.getComplaintRemarks().getComplaintRemarksId(), complaintRemarksDesc.getFullDesc());
				mapRemarksIdCodeList.put(complaintRemarksDesc.getComplaintRemarks().getComplaintRemarksId(), complaintRemarksDesc.getComplaintRemarks().getComplaintRemarksCode());
		      }
	    }

		
	    return lstComplaintRemarksFromComplaintRemarks;
  }

  public void setLstComplaintRemarksFromComplaintRemarks(List<ComplaintRemarksDesc> lstComplaintRemarksFromComplaintRemarks) {
	    this.lstComplaintRemarksFromComplaintRemarks = lstComplaintRemarksFromComplaintRemarks;
  }

	public List<ComplaintActionDesc> getComplaintActionList() {

		complaintActionList = complaintService.getComplaintActionList(sessionStateManage.getLanguageId());
		for (ComplaintActionDesc compAction : complaintActionList) {
			  mapComplaintActionList.put(compAction.getComplaintAction().getComplaintActionId(), compAction.getFullDescription());
	  }
		return complaintActionList;
	}

	public void setComplaintActionList(
			List<ComplaintActionDesc> complaintActionList) {
		this.complaintActionList = complaintActionList;
	}
	
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void complaintCloseNavigation() {

		try {
			clear();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "complaintclose.xhtml");
					FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../complaint/complaintclose.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exit() throws IOException {
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		}
	}
	
	public void displayComplaintLog() {
		//clear();
		complaintLogList = complaintService.getComplaintRemittance(getComplaintRef());

		if (complaintLogList.size() > 0) {

			for (ComplaintLog complaintLog : complaintLogList) {

				
				String customerName = generalService.getCustomerNameCustomerId(complaintLog.getCustomer().getCustomerId());
				setCustomerName(customerName);
				String mobileNo = complaintService.getMobileBasedOnCustomerId(complaintLog.getCustomer().getCustomerId());
				setMobileNumber(new BigDecimal(mobileNo));
				
				String branchName = complaintService.getBranchNameBasedOnBranchCode(new BigDecimal(complaintLog.getCountryBranchCode()));
				setTransferLocation(branchName);
				
				setLogDate(complaintLog.getLogDate());
				setTransferRef(complaintLog.getRemittanceDocumentNo());

				List<ComplaintAssignedDesc> assignlist = complaintService.getComplaintAssignedDesc(sessionStateManage.getLanguageId(), complaintLog.getComplaintFromId().getComplaintAssignedId());

				if (assignlist.size() > 0) {
					setComplaintFromId(assignlist.get(0).getFullDescription());
				}

				setTransferDate(complaintLog.getRemittanceDate());
				setAmount(complaintLog.getForiegnCurrencyAmount());
				List<ComplaintTypeDesc> typelist = complaintService.getComplaintTypeDescDb(sessionStateManage.getLanguageId(), complaintLog.getComplaintType().getComplaintTypeId());
				if (typelist.size() > 0) {
					setComplaintTypeId(typelist.get(0).getFullDesc());
				}

			}
		}
	}
	
	public void saveCloseComplaintLog(){
		
		ComplaintLog complaintLog = new ComplaintLog();
		
		complaintLogList = complaintService.getComplaintRemittance(getComplaintRef());
		
		if(complaintLogList.size()>0){
		
			//Company Master
		CompanyMaster companyMaster=new CompanyMaster();
		companyMaster.setCompanyId(complaintLogList.get(0).getCompanyMaster().getCompanyId());
		complaintLog.setRemittanceCompany(companyMaster);
		
		//getCompanyCode

		complaintLog.setCompanyMaster(companyMaster);
		complaintLog.setRemittanceCompanyCode(complaintLogList.get(0).getRemittanceCompanyCode());
		
		//Document Master
		Document remitdocument=new Document();
		remitdocument.setDocumentID(complaintLogList.get(0).getDocument());
		
		//getRemitDocumentCode
		complaintLog.setRemittanceDocumentCode(complaintLogList.get(0).getRemittanceDocumentCode());
		//getRemitDealNumber
		complaintLog.setRemittanceDocumentNo(complaintLogList.get(0).getRemittanceDocumentNo());
		//getRemitDealYear
		complaintLog.setRemittanceDocumentFinancialYear(complaintLogList.get(0).getRemittanceDocumentFinancialYear());
		//getRemitDate
		complaintLog.setRemittanceDate(complaintLogList.get(0).getRemittanceDate());
		//getremit ForiegnCurreny Amount
		complaintLog.setForiegnCurrencyAmount(complaintLogList.get(0).getForiegnCurrencyAmount());
		complaintLog.setRemittanceDocument(complaintLogList.get(0).getRemittanceDocument());		
		
		//complaintLog.setComplaintReference(complaimtCreationDt.getDocumentId());
		complaintLog.setDocument(complaintLogList.get(0).getDocument());
		//get Complaint Document No
		complaintLog.setDocumentNumber(complaintLogList.get(0).getDocumentNumber());
		complaintLog.setComplaintReference(complaintLogList.get(0).getComplaintReference());
		
		//Country Master 
		CountryMaster countryMaster=new CountryMaster();
		countryMaster.setCountryId(complaintLogList.get(0).getCountry().getCountryId());
		complaintLog.setCountry(countryMaster);

		CountryMaster appCountryMaster=new CountryMaster();
		appCountryMaster.setCountryId(complaintLogList.get(0).getApplicationCountry().getCountryId());
		complaintLog.setApplicationCountry(appCountryMaster);

		//Customer Details
		Customer customer=new Customer();
		customer.setCustomerId(complaintLogList.get(0).getCustomer().getCustomerId());
		//getCustomer ReferEnce Number
		complaintLog.setCustomerReference(complaintLogList.get(0).getCustomerReference());
		complaintLog.setCustomer(customer);

		//Service Master
		ServiceMaster serviceMaster=new ServiceMaster();
		serviceMaster.setServiceId(complaintLogList.get(0).getService().getServiceId());
		complaintLog.setService(serviceMaster);

		//Bank Master
		BankMaster bankMaster=new BankMaster();
		bankMaster.setBankId(complaintLogList.get(0).getBank().getBankId());
		complaintLog.setBank(bankMaster);

		//CountryBranch Master
		CountryBranch countryBranch=new CountryBranch();
		countryBranch.setCountryBranchId(complaintLogList.get(0).getCountryBranch().getCountryBranchId());
		//get country Branch Code
		complaintLog.setCountryBranchCode(complaintLogList.get(0).getCountryBranchCode());
		complaintLog.setCountryBranch(countryBranch);

		//Complaint Assigned Master
		ComplaintAssigned complaintAssigned=new ComplaintAssigned();
		complaintAssigned.setComplaintAssignedId(complaintLogList.get(0).getComplaintFromId().getComplaintAssignedId());
		complaintLog.setComplaintFromId(complaintAssigned);		
		
		List<ComplaintAssignedDesc> assignlist = complaintService.getComplaintAssignedDesc(sessionStateManage.getLanguageId(), complaintLogList.get(0).getComplaintFromId().getComplaintAssignedId());

		if (assignlist.size() > 0) {
			complaintLog.setAssignedToCode(assignlist.get(0).getComplaintAssigned().getComplaintAssignedCode());
		}
		complaintLog.setAssignedToId(complaintAssigned);		
		
		
		//getComplaintAssignedFromCode
		
		
		complaintLog.setComplaintFromCode(complaintLogList.get(0).getComplaintFromCode());
		//complaintLog.setTokenById(complaintAssigned);
		//getComplaintTakenByCode
		complaintLog.setTokenByCode(complaintLogList.get(0).getTokenByCode());
		//Complaint Taken By Master
		ComplaintTypeMaster complaintTypeMaster=new ComplaintTypeMaster();
		complaintTypeMaster.setComplaintTypeId(complaintLogList.get(0).getComplaintType().getComplaintTypeId());
		//getComplaintTypeCode
		complaintLog.setComplaintTypeCode(complaintLogList.get(0).getComplaintTypeCode());
		complaintLog.setComplaintType(complaintTypeMaster);

		//Remarks Master
		ComplaintRemarksMaster complaintRemarksMaster=new ComplaintRemarksMaster();
		complaintRemarksMaster.setComplaintRemarksId(getComplaintRemarksCodeId());
		complaintLog.setComplaintRemarks(complaintRemarksMaster);
		//getComplaintRemarksCode
		complaintLog.setComplaintRemarksCode(complaintLogList.get(0).getComplaintRemarksCode());
		complaintLog.setLogDate(complaintLogList.get(0).getLogDate());

		//complaint User Finance Year
		complaintLog.setComplaintFinancialYear(complaintLogList.get(0).getComplaintFinancialYear());
		//getForienCurrenyName
		complaintLog.setForiegnCurrencyName(complaintLogList.get(0).getForiegnCurrencyName());
		//getForien Currency Amount
		complaintLog.setForiegnCurrencyAmount(complaintLogList.get(0).getForiegnCurrencyAmount());
		//common variables
		complaintLog.setCreatedBy(complaintLogList.get(0).getCreatedBy());
		complaintLog.setCreatedDate(complaintLogList.get(0).getCreatedDate());
		complaintLog.setIsActive(complaintLogList.get(0).getIsActive());
		complaintLog.setModifiedBy(complaintLogList.get(0).getModifiedBy());
		complaintLog.setModifiedDate(complaintLogList.get(0).getModifiedDate());
		complaintLog.setRemarks(complaintLogList.get(0).getRemarks());
		complaintLog.setApprovedBy(complaintLogList.get(0).getApprovedBy());
		complaintLog.setApprovedDate(complaintLogList.get(0).getApprovedDate());
		complaintLog.setComplaintLogId(complaintLogList.get(0).getComplaintLogId());		
		complaintLog.setClosedDate(new Date());
		
		complaintService.updateCloseComplaint(complaintLog);
		
		
		//insert followup		
		List<ComplaintFollowup> followupList = complaintRegisteredToRemittancesService.getComplaintFollowUp(complaintLog.getCompanyMaster().getCompanyId(), getComplaintRef(), complaintLog.getComplaintFinancialYear());
		
		if(followupList !=null){
			for(ComplaintFollowup objComplaintFollowup : followupList){
				ComplaintFollowup complaintFollowup = new ComplaintFollowup();

				complaintFollowup.setCommunicationMathodCode(objComplaintFollowup.getCommunicationMathodCode());

				CommunicationMethod communicationMethod = new CommunicationMethod();
				communicationMethod.setComMethodId(objComplaintFollowup.getCommunicationMethodId().getComMethodId());
				complaintFollowup.setCommunicationMethodId(communicationMethod);

				ComplaintAssigned complaintAssigned1 = new ComplaintAssigned();
				complaintAssigned1.setComplaintAssignedId(objComplaintFollowup.getAssigntoId().getComplaintAssignedId());
				complaintFollowup.setAssigntoId(complaintAssigned1);

				complaintFollowup.setComplaintFinanceYear(objComplaintFollowup.getComplaintFinanceYear());

				Document document = new Document();
				document.setDocumentID(generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_COMPLAINTCREATION), sessionStateManage.getLanguageId()).get(0).getDocumentID());
				complaintFollowup.setDocumentId(document);

				CompanyMaster companyMaster1 = new CompanyMaster();
				companyMaster1.setCompanyId(sessionStateManage.getCompanyId());
				complaintFollowup.setCompanyId(companyMaster1);

				ComplaintAction complaintAction = new ComplaintAction();
				complaintAction.setComplaintActionId(getComplaintActionId());
				complaintFollowup.setComplaintActionId(complaintAction);

				ComplaintRemarksMaster complaintRemarksMaster1 = new ComplaintRemarksMaster();
				complaintRemarksMaster1.setComplaintRemarksId(getComplaintRemarksCodeId());
				complaintFollowup.setRemarksId(complaintRemarksMaster);

				complaintFollowup.setComplaintReference(objComplaintFollowup.getComplaintReference());

				CountryMaster countryMaster1 = new CountryMaster();
				countryMaster1.setCountryId(sessionStateManage.getCountryId());
				complaintFollowup.setApplicationCountryId(countryMaster1);

				complaintFollowup.setCreatedBy(objComplaintFollowup.getCreatedBy());
				complaintFollowup.setCreatedDate(objComplaintFollowup.getCreatedDate());
				complaintFollowup.setRemarks(objComplaintFollowup.getRemarks());
				complaintFollowup.setRemarksCode(objComplaintFollowup.getRemarksCode());
				complaintFollowup.setComplaintActionCode(objComplaintFollowup.getComplaintActionCode());
				complaintFollowup.setAssigntoCode(objComplaintFollowup.getAssigntoCode());
				complaintFollowup.setAssignDate(objComplaintFollowup.getAssignDate());
				complaintFollowup.setCommunicationMathodCode(objComplaintFollowup.getCommunicationMathodCode());
				complaintFollowup.setFollowupDate(objComplaintFollowup.getFollowupDate());
				
				complaintFollowup.setEmailTo(objComplaintFollowup.getEmailTo());
				complaintFollowup.setEmailTo(objComplaintFollowup.getEmailText());
				complaintFollowup.setEmailTo(objComplaintFollowup.getEmailSubject());
				
				complaintService.saveComplaintFollowup(complaintFollowup);
				
				RequestContext.getCurrentInstance().execute("complete.show();");
				
				
				/*try {
					
				} catch (Exception e) {
					// TODO: handle exception
				}*/
			}
			
		}
		
		
		}
	}
	
	
	public void clear(){
		setAmount(null);
		setComplaintActionId(null);
		setComplaintFromId(null);
		setComplaintRef(null);
		setTransferDate(null);
		setCustomerName(null);
		setLocation(null);
		setTransferLocation(null);
		setLogDate(null);
		setMobileNumber(null);
		setOtherRemarks(null);
		setPayableAt(null);
		setPriority(null);
		setProductName(null);
		setComplaintRemarksCodeId(null);
	}
	public void clickOnOKSave() {
		try {
			clear();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintclose.xhtml"); 
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
