package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cashtransfer.service.ICashTransferLToLService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.enquiry.bean.RemittanceInquiryBean;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.remittance.model.RemittanceBranchWiseEnquiryView;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;

@Component("remittanceBranchWiseEnquiry")
@Scope("session")
public class RemittaneBranchWiseEnquiryBean<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(RemittaneBranchWiseEnquiryBean.class);
	private String errorMessage;
	private Date fromDate;
	private BigDecimal branchId;
	private String userName;
	private BigDecimal totalNetAmount;
	private Date maxDate = new Date();
	private List<CountryBranch> branchList;
	private List<Employee> employeeList;
	private List<RemittanceBranchWiseEnquiryDataTableBean> pendingTransApplicationList;
	private SessionStateManage session = new SessionStateManage();
	
	@Autowired
	IGeneralService generalService;
	@Autowired
	ICashTransferLToLService cashTransferLToLService;
	@Autowired
	IPersonalRemittanceService personalRemittanceService;
	@Autowired
	RemittanceInquiryBean remittanceInquiryBean;
	@Autowired
	FCSaleEnquiryBean fcSaleEnquiryBean;
	
	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}


	public BigDecimal getTotalNetAmount() {
		return totalNetAmount;
	}

	public void setTotalNetAmount(BigDecimal totalNetAmount) {
		this.totalNetAmount = totalNetAmount;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<RemittanceBranchWiseEnquiryDataTableBean> getPendingTransApplicationList() {
		return pendingTransApplicationList;
	}

	public void setPendingTransApplicationList(
			List<RemittanceBranchWiseEnquiryDataTableBean> pendingTransApplicationList) {
		this.pendingTransApplicationList = pendingTransApplicationList;
	}

	public List<CountryBranch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<CountryBranch> branchList) {
		this.branchList = branchList;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void gotoEnquiryPage(RemittanceBranchWiseEnquiryDataTableBean enquiryDataTableObj){
		if(enquiryDataTableObj.getApplicationType().equalsIgnoreCase("REMITTANCE")){
			gotoRemittanceEnquiry(enquiryDataTableObj);
		}else{
			gotoForeignCurrencySaleEnquiry(enquiryDataTableObj);
		}
	}
	
	public void gotoRemittanceEnquiry(RemittanceBranchWiseEnquiryDataTableBean enquiryDataTableObj){
		remittanceInquiryBean.clearAll();
		remittanceInquiryBean.setDocNumber(enquiryDataTableObj.getDocumentNo());
		log.info("Year Is  ===="+enquiryDataTableObj.getDocumentYear());
		remittanceInquiryBean.setReceiptNo(enquiryDataTableObj.getDocumentNo());
		remittanceInquiryBean.setDocYear(enquiryDataTableObj.getDocumentYear());
		remittanceInquiryBean.fetchData();
		remittanceInquiryBean.setRenderPanel(false);
		remittanceInquiryBean.setBackButtonRender(false);
		remittanceInquiryBean.setRenderForRemittanceBranchWiseEnquiry(true);
		remittanceInquiryBean.setExitButtonRender(false);
		remittanceInquiryBean.setRenderForHIGHVALUETrnx(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/remittanceinquiry.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void gotoForeignCurrencySaleEnquiry(RemittanceBranchWiseEnquiryDataTableBean enquiryDataTableObj){
		   try {
			    fcSaleEnquiryBean.clearAll();
				fcSaleEnquiryBean.setDocumentFinancialYear(enquiryDataTableObj.getDocumentYear());
			    fcSaleEnquiryBean.setDocumentNo(enquiryDataTableObj.getDocumentNo());
			    fcSaleEnquiryBean.fcsaleEnquiry();
			    fcSaleEnquiryBean.setRenderPanel(true);
			    fcSaleEnquiryBean.setRenderForRemittanceBranchWiseEnquiry(false);
			    fcSaleEnquiryBean.setRenderBackButtonForRemittanceBranchWiseEnquiry(true);
			    
			    FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/foreigncurrencysaleenquiry.xhtml");
		} catch (Exception e) {
			log.error("Problem Occured in FCSALEPageNavigation() method");
		}  
	}
	public void displayAllPendingTransaction(){
		try{
		List<RemittanceBranchWiseEnquiryView> remittanceBranchWiseEnqList =personalRemittanceService.showRemittanceBranchWise(getBranchId(), getUserName(), getFromDate());
			if(remittanceBranchWiseEnqList!=null && remittanceBranchWiseEnqList.size()>0){
				BigDecimal totalNetAmount = BigDecimal.ZERO;
				List<RemittanceBranchWiseEnquiryDataTableBean> pendingTransApplicationList =new ArrayList<RemittanceBranchWiseEnquiryDataTableBean>();
				for(RemittanceBranchWiseEnquiryView enquiryObj :remittanceBranchWiseEnqList){
					RemittanceBranchWiseEnquiryDataTableBean enquiryDataTableObj =new RemittanceBranchWiseEnquiryDataTableBean();
					enquiryDataTableObj.setDocumentYear(enquiryObj.getDocumnetFinanceYear());
					enquiryDataTableObj.setDocumentNo(enquiryObj.getDocumentNo());
					enquiryDataTableObj.setCustomerRef(enquiryObj.getCustomerReference());
					enquiryDataTableObj.setBeneficiaryName(enquiryObj.getBeneficiayName());
					enquiryDataTableObj.setBeneficiaryBank(enquiryObj.getBeneficiaryBank());
					enquiryDataTableObj.setBeneficiaryBranch(enquiryObj.getBeneficiayBranch());
					enquiryDataTableObj.setRoutingBank(enquiryObj.getRoutingBank());
					enquiryDataTableObj.setBankingChannel(enquiryObj.getDeliveryDescription());
					enquiryDataTableObj.setApplicationType(enquiryObj.getApplicationTypeDesc());
					enquiryDataTableObj.setFcAmount(enquiryObj.getForeignTranxAmount());
					enquiryDataTableObj.setExchangeRate(enquiryObj.getExchangeRateApplied());
					enquiryDataTableObj.setNetAmount(enquiryObj.getLocalNetTranxAmount());
					
					if(enquiryObj.getBeneficiaryBank()!=null && enquiryObj.getBeneficiayBranch()!=null){
					enquiryDataTableObj.setBeneficiaryBankAndBranch(enquiryObj.getBeneficiaryBank()+" / "+enquiryObj.getBeneficiayBranch());
					}
					if(enquiryObj.getRoutingBank()!=null && enquiryObj.getDeliveryDescription()!=null){
					enquiryDataTableObj.setBankService(enquiryObj.getRoutingBank()+" / "+enquiryObj.getDeliveryDescription());
					}
					if(enquiryObj.getForeignTranxAmount()!=null && enquiryObj.getExchangeRateApplied()!=null){
					enquiryDataTableObj.setFcAmountAndExchangeRate(enquiryObj.getForeignTranxAmount()+" @ "+enquiryObj.getExchangeRateApplied());
					}
					
					if(enquiryObj.getLocalNetTranxAmount()!=null){
						totalNetAmount = totalNetAmount.add(enquiryObj.getLocalNetTranxAmount());
					}
					pendingTransApplicationList.add(enquiryDataTableObj);
				}
				setPendingTransApplicationList(pendingTransApplicationList);
				setTotalNetAmount(totalNetAmount);
			}else{
				    setErrorMessage(WarningHandler.showWarningMessage("lbl.norecordfound", session.getLanguageId()));
			    	setPendingTransApplicationList(null);
					setTotalNetAmount(null);
			    	RequestContext.getCurrentInstance().execute("error.show();");
			}
		}catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    if(exception.getMessage()!=null){
		    	setErrorMessage(exception.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("error.show();");
		    }else{
		    	setErrorMessage("Exception :"+exception.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("error.show();");
		    }     
		 }
		
	}
	public void exitFromRemittanceBranchWiseEnqScreen(){
	 try {
			if (session.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		}catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    if(exception.getMessage()!=null){
			    	setErrorMessage(exception.getMessage()); 
			    	RequestContext.getCurrentInstance().execute("error.show();");
			    }else{
			    	setErrorMessage("Exception :"+exception.getMessage()); 
			    	RequestContext.getCurrentInstance().execute("error.show();");
			    }     
			 }
	}
	public void populateBranch() {
		setBranchList(null);
		setBranchId(null);
	   List<CountryBranch> listSearchBranch  = generalService.getBranchDetails(session.getCountryId());
		 if(listSearchBranch.size()>0){
			 setBranchList(listSearchBranch);
		 }
	}
	
	public void getAllUsers(){
		try{
		setEmployeeList(null);
		setUserName(null);
		List<Employee> cashierList1=generalService.getAllEmployeeListBasedonLocation(getBranchId());
		if(cashierList1!=null && cashierList1.size()>0){
			setEmployeeList(cashierList1);
		}
		}catch(Exception exception){
		    log.error("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    if(exception.getMessage()!=null){
		    	setErrorMessage(exception.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("error.show();");
		    }else{
		    	setErrorMessage("Exception :"+exception.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("error.show();");
		    }
		}
	}
	
	public void clear(){
		setFromDate(null);
		setBranchId(null);
		setUserName(null);
		setPendingTransApplicationList(null);
		setTotalNetAmount(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/remittanceBranchWiseEnquiry.xhtml");
		}catch(Exception exception){
		    log.error("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    if(exception.getMessage()!=null){
		    	setErrorMessage(exception.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("error.show();");
		    }else{
		    	setErrorMessage("Exception :"+exception.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("error.show();");
		    }
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigatingtoRemittanceBranchWiseEnq(){
		
		try {
			populateBranch();
			clear();
			setFromDate(new Date());
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "remittanceBranchWiseEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/remittanceBranchWiseEnquiry.xhtml");
		}catch(Exception exception){
		    log.error("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    if(exception.getMessage()!=null){
		    	setErrorMessage(exception.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("error.show();");
		    }else{
		    	setErrorMessage("Exception :"+exception.getMessage()); 
		    	RequestContext.getCurrentInstance().execute("error.show();");
		    }
		}
	}
	
}
