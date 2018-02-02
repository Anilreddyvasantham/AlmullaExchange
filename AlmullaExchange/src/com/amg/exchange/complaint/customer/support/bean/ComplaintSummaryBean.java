package com.amg.exchange.complaint.customer.support.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.complaint.customer.support.model.ComplaintLog;
import com.amg.exchange.complaint.customer.support.model.ComplaintSummary;
import com.amg.exchange.complaint.customer.support.service.IComplaintSummaryService;
import com.amg.exchange.complaint.model.ComplaintTypeDesc;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("complaintSummaryBean")
@Scope("session")
public class ComplaintSummaryBean<T> implements Serializable {

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
	private BigDecimal pendingComplaint;

	private String companyName;
	private BigDecimal companyId;
	private String remittanceCode;
	
	 private BigDecimal remitdealYearId = null;
	  private BigDecimal remitdealReference;
	  private BigDecimal complaintdealYear;
	  private BigDecimal complaintdealReference;
	  private BigDecimal remitdealYear = null;
	  private BigDecimal complaintdealYearId;

	private List<UserFinancialYear> financialYearList = new ArrayList<>();

	private List<UserFinancialYear> dealYearList = new ArrayList<UserFinancialYear>();

	private Boolean booComplaintRequestToRemittance = false;

	private List<ComplaintSummaryDataTable> lstOfComplaintSummaryDataTables = new CopyOnWriteArrayList<ComplaintSummaryDataTable>();

	private List<ComplaintSummary> lstOfComplaintSummary = new ArrayList<ComplaintSummary>();
	private List<CompanyMasterDesc> lstCompanyNamesFromCompanyMaster = new ArrayList<CompanyMasterDesc>();
	
	private List<ComplaintLog> lstOfComplaintLogs = new ArrayList<ComplaintLog>();
	private List<ServiceMasterDesc> lstOfServiceMasterDescs = new ArrayList<ServiceMasterDesc>();
	private List<ComplaintTypeDesc> lstOfComplaintTypeDescs = new ArrayList<ComplaintTypeDesc>();
	private List<CountryMasterDesc> lstOfCountryMasterDescs = new ArrayList<CountryMasterDesc>();
	
	

	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	IComplaintSummaryService<T> complaintSummaryService;

	@Autowired
	IGeneralService<T> generalService;

	public ComplaintSummaryBean() {
		// TODO Auto-generated constructor stub
	}

	public List<UserFinancialYear> getDealYearList() {
		return dealYearList;
	}

	public void setDealYearList(List<UserFinancialYear> dealYearList) {
		this.dealYearList = dealYearList;
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

	public IComplaintSummaryService<T> getComplaintSummaryService() {
		return complaintSummaryService;
	}

	public void setComplaintSummaryService(IComplaintSummaryService<T> complaintSummaryService) {
		this.complaintSummaryService = complaintSummaryService;
	}

	public List<ComplaintSummaryDataTable> getLstOfComplaintSummaryDataTables() {
		return lstOfComplaintSummaryDataTables;
	}

	public void setLstOfComplaintSummaryDataTables(List<ComplaintSummaryDataTable> lstOfComplaintSummaryDataTables) {
		this.lstOfComplaintSummaryDataTables = lstOfComplaintSummaryDataTables;
	}

	public List<ComplaintSummary> getLstOfComplaintSummary() {
		return lstOfComplaintSummary;
	}

	public void setLstOfComplaintSummary(List<ComplaintSummary> lstOfComplaintSummary) {
		this.lstOfComplaintSummary = lstOfComplaintSummary;
	}

	public Boolean getBooComplaintRequestToRemittance() {
		return booComplaintRequestToRemittance;
	}

	public void setBooComplaintRequestToRemittance(Boolean booComplaintRequestToRemittance) {
		this.booComplaintRequestToRemittance = booComplaintRequestToRemittance;
	}

	public List<UserFinancialYear> getFinancialYearList() {
		return financialYearList;
	}
	
	

	public List<ComplaintLog> getLstOfComplaintLogs() {
		return lstOfComplaintLogs;
	}

	public void setLstOfComplaintLogs(List<ComplaintLog> lstOfComplaintLogs) {
		this.lstOfComplaintLogs = lstOfComplaintLogs;
	}

	public void setFinancialYearList(List<UserFinancialYear> financialYearList) {
		this.financialYearList = financialYearList;
	}


	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<CompanyMasterDesc> getLstCompanyNamesFromCompanyMaster() {
		return lstCompanyNamesFromCompanyMaster;
	}

	public void setLstCompanyNamesFromCompanyMaster(List<CompanyMasterDesc> lstCompanyNamesFromCompanyMaster) {
		this.lstCompanyNamesFromCompanyMaster = lstCompanyNamesFromCompanyMaster;
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	

	public BigDecimal getRemitdealYear() {
		return remitdealYear;
	}

	public void setRemitdealYear(BigDecimal remitdealYear) {
		this.remitdealYear = remitdealYear;
	}

	public BigDecimal getComplaintdealYearId() {
		return complaintdealYearId;
	}

	public void setComplaintdealYearId(BigDecimal complaintdealYearId) {
		this.complaintdealYearId = complaintdealYearId;
	}

	public String getRemittanceCode() {
		return remittanceCode;
	}

	public void setRemittanceCode(String remittanceCode) {
		this.remittanceCode = remittanceCode;
	}
	
	

	public BigDecimal getRemitdealYearId() {
		return remitdealYearId;
	}

	public void setRemitdealYearId(BigDecimal remitdealYearId) {
		this.remitdealYearId = remitdealYearId;
	}

	public BigDecimal getRemitdealReference() {
		return remitdealReference;
	}

	public void setRemitdealReference(BigDecimal remitdealReference) {
		this.remitdealReference = remitdealReference;
	}

	public BigDecimal getComplaintdealYear() {
		return complaintdealYear;
	}

	public void setComplaintdealYear(BigDecimal complaintdealYear) {
		this.complaintdealYear = complaintdealYear;
	}

	public BigDecimal getComplaintdealReference() {
		return complaintdealReference;
	}

	public void setComplaintdealReference(BigDecimal complaintdealReference) {
		this.complaintdealReference = complaintdealReference;
	}
	
	public BigDecimal getPendingComplaint() {
		return pendingComplaint;
	}

	public void setPendingComplaint(BigDecimal pendingComplaint) {
		this.pendingComplaint = pendingComplaint;
	}

	
	public List<ServiceMasterDesc> getLstOfServiceMasterDescs() {
		return lstOfServiceMasterDescs;
	}

	public void setLstOfServiceMasterDescs(List<ServiceMasterDesc> lstOfServiceMasterDescs) {
		this.lstOfServiceMasterDescs = lstOfServiceMasterDescs;
	}

	public List<ComplaintTypeDesc> getLstOfComplaintTypeDescs() {
		return lstOfComplaintTypeDescs;
	}

	public void setLstOfComplaintTypeDescs(List<ComplaintTypeDesc> lstOfComplaintTypeDescs) {
		this.lstOfComplaintTypeDescs = lstOfComplaintTypeDescs;
	}

	public List<CountryMasterDesc> getLstOfCountryMasterDescs() {
		return lstOfCountryMasterDescs;
	}

	public void setLstOfCountryMasterDescs(List<CountryMasterDesc> lstOfCountryMasterDescs) {
		this.lstOfCountryMasterDescs = lstOfCountryMasterDescs;
	}
	
private String errorMessage;
	
	
	
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void complaintSummaryPageNavigation() {
		try {
			lstOfComplaintSummaryDataTables.clear();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "complaintsummary.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintsummary.xhtml");
			viewComplaintSummary();
		}catch (NullPointerException ne) {
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

	public void exitFromComplaintSummary() {

		try {

			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			lstOfComplaintSummaryDataTables.clear();
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::exitFromComplaintSummary");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void refreshComplaintSummaryPage() {
		try {
			lstOfComplaintSummaryDataTables.clear();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintsummary.xhtml");
			viewComplaintSummary();
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::refreshComplaintSummaryPage");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void searchComplaintSummary() {
		try{
		fetchComplaintfinanceYear();
		setCompanyName(null);
		setRemittanceCode(null);
		
		
		companyListFromDB();
		//fetchRemittanceDealYear();
		RequestContext.getCurrentInstance().execute("search.show();");
		setRemitdealReference(null);
		setRemitdealYearId(null);
return;
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::searchComplaintSummary");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void viewComplaintSummary() {

		try {
			lstOfComplaintSummary = getComplaintSummaryService().displayComplaintSummary();

			ComplaintSummaryDataTable complaintSummaryDataTable = null;
			if (lstOfComplaintSummary != null) {
				for (ComplaintSummary complaintSummary : lstOfComplaintSummary) {

					complaintSummaryDataTable = new ComplaintSummaryDataTable();
					complaintSummaryDataTable.setComplaintReferenceNo(complaintSummary.getIdNo());

					complaintSummaryDataTable.setBankFullName(complaintSummary.getBankFullName());
					complaintSummaryDataTable.setBankId(complaintSummary.getBankId());
					complaintSummaryDataTable.setComplaintTypeCode(complaintSummary.getComplaintTypeCode());
					complaintSummaryDataTable.setComplaintTypeDesc(complaintSummary.getComplaintTypeDesc());
					complaintSummaryDataTable.setComplaintTypeId(complaintSummary.getComplaintTypeId());
					complaintSummaryDataTable.setCountryId(complaintSummary.getCountryId());
					//complaintSummaryDataTable.setApplicationCountryId(complaintSummary.getApplicationCountryId());
					complaintSummaryDataTable.setCountryName(complaintSummary.getCountryName());
					complaintSummaryDataTable.setServiceDescription(complaintSummary.getServiceDescription());
					complaintSummaryDataTable.setServiceId(complaintSummary.getServiceId());
					
					List<ComplaintLog> totalComplaintList = complaintSummaryService.getTotalComplaints(complaintSummary.getCountryId(), complaintSummary.getServiceId(), complaintSummary.getBankId(), complaintSummary.getComplaintTypeId());
					List<ComplaintLog> pendinglComplaintList = complaintSummaryService.getPendingComplaints(complaintSummary.getCountryId(), complaintSummary.getServiceId(), complaintSummary.getBankId(), complaintSummary.getComplaintTypeId());
					
					complaintSummaryDataTable.setTotalComplaint(new BigDecimal(totalComplaintList.size()));
					complaintSummaryDataTable.setPendingComplaint(new BigDecimal(pendinglComplaintList.size()));
					
					lstOfComplaintSummaryDataTables.add(complaintSummaryDataTable);
				}

			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::viewComplaintSummary");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public String populateComplateSummary(ComplaintSummaryDataTable complaintSummaryDataTable) {

		ComplaintRegisteredToRemittancesBean<T> complaintRegisteredToRemittancesBean = new ComplaintRegisteredToRemittancesBean<T>();
try{
		complaintRegisteredToRemittancesBean.setBankFullName(complaintSummaryDataTable.getBankFullName());
		complaintRegisteredToRemittancesBean.setBankId(complaintSummaryDataTable.getBankId());
		complaintRegisteredToRemittancesBean.setComplaintTypeCode(complaintSummaryDataTable.getComplaintTypeCode());
		complaintRegisteredToRemittancesBean.setComplaintTypeDesc(complaintSummaryDataTable.getComplaintTypeDesc());
		complaintRegisteredToRemittancesBean.setComplaintTypeId(complaintSummaryDataTable.getComplaintTypeId());
		complaintRegisteredToRemittancesBean.setCountryId(complaintSummaryDataTable.getCountryId());
		complaintRegisteredToRemittancesBean.setCountryName(complaintSummaryDataTable.getCountryName());
		complaintRegisteredToRemittancesBean.setServiceDescription(complaintSummaryDataTable.getServiceDescription());
		complaintRegisteredToRemittancesBean.setServiceId(complaintSummaryDataTable.getServiceId());
		complaintRegisteredToRemittancesBean.setTotalComplaint(complaintSummaryDataTable.getTotalComplaint());
		complaintRegisteredToRemittancesBean.setIdNo(complaintSummaryDataTable.getComplaintReferenceNo());
		complaintRegisteredToRemittancesBean.setApplicationCountryId(complaintSummaryDataTable.getApplicationCountryId());
		complaintRegisteredToRemittancesBean.setNavFlag("1");

		HttpSession session = sessionStateManage.getSession();
		session.setAttribute("complaintRegisteredObject", complaintRegisteredToRemittancesBean);

		return "complaintRegisterToRemittance";
}catch (NullPointerException ne) {
	// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
	setErrorMessage("Method Name::populateComplateSummary");
	RequestContext.getCurrentInstance().execute("nullPointerId.show();");
	return null;
} catch (Exception exception) {
	// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
	setErrorMessage(exception.getMessage());
	RequestContext.getCurrentInstance().execute("error.show();");
	return null;
}
	}
	

	public void companyListFromDB() {

		lstCompanyNamesFromCompanyMaster.clear();
		try{
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
		}catch (NullPointerException ne) {
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
	
	

	public String searchComplateSummary() {
		try{
		lstOfComplaintLogs = getComplaintSummaryService().searchFromComplaintLog(getCompanyId(), getRemitdealReference(), getRemitdealYearId());
		
		
		
		if(lstOfComplaintLogs != null){
			for(ComplaintLog complaintLog : lstOfComplaintLogs){
				
				ComplaintRegisteredToRemittancesBean<T> complaintRegisteredToRemittancesBean = new ComplaintRegisteredToRemittancesBean<T>();
				complaintRegisteredToRemittancesBean.setBankFullName(complaintLog.getBank().getBankFullName());
				
				complaintRegisteredToRemittancesBean.setBankId(complaintLog.getBank().getBankId());
				complaintRegisteredToRemittancesBean.setComplaintTypeCode(complaintLog.getComplaintTypeCode());
				
				serviceDescription = getComplaintSummaryService().getServiceDesc(complaintLog.getService().getServiceId(),sessionStateManage.getLanguageId());
				if(serviceDescription != null)
				complaintRegisteredToRemittancesBean.setServiceDescription(serviceDescription);
				
				countryName = getComplaintSummaryService().getCountryName(complaintLog.getCountry().getCountryId(),sessionStateManage.getLanguageId());
				if(countryName != null)
				complaintRegisteredToRemittancesBean.setCountryName(countryName);
				
				complaintTypeDesc = getComplaintSummaryService().getCountryName(complaintLog.getComplaintType().getComplaintTypeId(),sessionStateManage.getLanguageId());
				if(complaintTypeDesc != null)
				complaintRegisteredToRemittancesBean.setComplaintTypeDesc(complaintTypeDesc);
				
				complaintRegisteredToRemittancesBean.setComplaintTypeId(complaintLog.getComplaintType().getComplaintTypeId());
				complaintRegisteredToRemittancesBean.setCountryId(complaintLog.getCountry().getCountryId());
				complaintRegisteredToRemittancesBean.setServiceId(complaintLog.getService().getServiceId());
				
				complaintRegisteredToRemittancesBean.setIdNo(complaintLog.getComplaintReference());
				complaintRegisteredToRemittancesBean.setApplicationCountryId(complaintLog.getApplicationCountry().getCountryId());
				
				complaintRegisteredToRemittancesBean.setRemittanceDocumentFinanceYr(getRemitdealYearId());
				
				complaintRegisteredToRemittancesBean.setRemittanceReference(getRemitdealReference());
				complaintRegisteredToRemittancesBean.setCompanyId(getCompanyId());
				
				
				complaintRegisteredToRemittancesBean.setNavFlag("2");


				HttpSession session = sessionStateManage.getSession();
				session.setAttribute("complaintRegisteredObject", complaintRegisteredToRemittancesBean);
				
			}
			
			return "complaintRegisterToRemittance";
		}else{
			
			setRemitdealReference(null);
			setRemitdealYearId(null);
					
			RequestContext.getCurrentInstance().execute("noRecords.show();");
			return "complaintSummary";
			
		}
		

		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::searchComplateSummary");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return null;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;
		}

		
	}
	
	public void clickOK(){
		try {
			lstOfComplaintSummaryDataTables.clear();
			setRemitdealReference(null);
			setRemitdealYearId(null);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintsummary.xhtml");
			viewComplaintSummary();
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::clickOK");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	
	
	public void fetchComplaintfinanceYear() {

		dealYearList.clear();

		try {
			
			   List<UserFinancialYear> lstFinancialYear = getComplaintSummaryService().getAllDocumentYear();
			      if(lstFinancialYear.size() != 0){
					dealYearList.addAll(lstFinancialYear);
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


}
