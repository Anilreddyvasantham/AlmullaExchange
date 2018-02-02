package com.amg.exchange.wu.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cashtransfer.service.ICashTransferLToLService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.miscellaneous.service.IMiscellaneousReceiptPaymentService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.wu.model.VoyagerExceptionModel;
import com.amg.exchange.wu.model.WesternUnionTransfer;
import com.amg.exchange.wu.service.IWesternUnionService;

@Component("wuDenominationExceptionList")
@Scope("session")
public class WesternDenominationExceptionListBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log=Logger.getLogger(WesternDenominationExceptionListBean.class);


	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	ICashTransferLToLService cashTransferLToLService;
	@Autowired
	IWesternUnionService iwesternUnionService;
	@Autowired
	WesternUnionTransferBean<T> westernUnionTransferBean;
	@Autowired
	IMiscellaneousReceiptPaymentService<T> miscellaneousReceiptPaymentService;

	private List<CountryBranch> branchList;
	private HashMap<BigDecimal, BigDecimal> countryBranchMap;
	private List<Employee> employeeList;
	private BigDecimal branchId;
	private String userName;
	private String errorMessage;
	private Date fromDate;
	private List<WesternUnionTransfer> pendingTransApplicationList;
	private Date maxDate = new Date();
	private SessionStateManage session = new SessionStateManage();
	private String branchCode;


	public List<CountryBranch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<CountryBranch> branchList) {
		this.branchList = branchList;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}


	public List<WesternUnionTransfer> getPendingTransApplicationList() {
		return pendingTransApplicationList;
	}

	public void setPendingTransApplicationList(
			List<WesternUnionTransfer> pendingTransApplicationList) {
		this.pendingTransApplicationList = pendingTransApplicationList;
	}


	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}



	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void populateBranch() {
		setBranchList(null);
		setBranchId(null);
		List<CountryBranch> listSearchBranch  = generalService.getBranchDetails(session.getCountryId());
		if(listSearchBranch.size()>0){
			countryBranchMap = new HashMap<BigDecimal, BigDecimal>();
			for(CountryBranch branch :listSearchBranch){
				countryBranchMap.put(branch.getCountryBranchId(), branch.getBranchId());
			}
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


	public void exitFromScreen(){
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

	public void displayAllPendingTransaction()
	{
		setPendingTransApplicationList(null);
		List<WesternUnionTransfer>  lstWesternUnionTransfer= iwesternUnionService.getWUPendingTransactionList(getFromDate(),countryBranchMap.get(getBranchId()) , getUserName());
		setPendingTransApplicationList(lstWesternUnionTransfer);
	}

	public void gotoEnquiryPage(WesternUnionTransfer westernUnionTransfer)
	{

		
		if(westernUnionTransfer.getInorOut().equalsIgnoreCase("S"))
		{
			westernUnionTransferBean.setSendCompanyCode(westernUnionTransfer.getCompanyCode());
			westernUnionTransferBean.setSendDocumentFinanceYear(westernUnionTransfer.getDocumentFinanceYear());
			westernUnionTransferBean.setSendDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));
			westernUnionTransferBean.setSendDocumentNo(westernUnionTransfer.getDocumentNo());
			westernUnionTransferBean.setTxnType(1);
		}
		if(westernUnionTransfer.getInorOut().equalsIgnoreCase("R"))
		{
			westernUnionTransferBean.setReceiveCompanyCode(westernUnionTransfer.getCompanyCode());
			westernUnionTransferBean.setReceiveDocumentFinanceYear(westernUnionTransfer.getDocumentFinanceYear());
			westernUnionTransferBean.setReceiveDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));
			westernUnionTransferBean.setReceiveDocumentNo(westernUnionTransfer.getDocumentNo());
			westernUnionTransferBean.setTxnType(2);
		}

		try {
			westernUnionTransferBean.checkWUStatus();
		} catch (AMGException | InterruptedException e) {
			log.error("Error in gotoEnquiryPage() "+e);
		}

		// By rahamathali
		westernUnionTransferBean.setCashAmount(BigDecimal.ZERO);
		westernUnionTransferBean.setBooSaveAll(false);
		List<Customer> customerList = miscellaneousReceiptPaymentService.getCustomerDetails(westernUnionTransfer.getCustomerReference());
		if(customerList!=null && customerList.size()>0){
			westernUnionTransferBean.setCustomerNo(customerList.get(0).getCustomerId());
		}

	}

	public void clearData(){
		setFromDate(null);
		setBranchId(null);
		setUserName(null);
		setPendingTransApplicationList(null); 
	}

	public void clear(){
		clearData();
		try{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/wudenominationexceptionlist.xhtml");
		}catch(Exception exception){
			log.error("Error in Clear() "+exception);
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void wuDenominationExceptionListNavigation() {

		try {
			clearData();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "wudenominationexceptionlist.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/wudenominationexceptionlist.xhtml");
			populateBranch();
			setFromDate(new Date());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<VoyagerExceptionModel> lstExcMsg = new ArrayList<VoyagerExceptionModel>();

	public List<VoyagerExceptionModel> getLstExcMsg() {
		return lstExcMsg;
	}
	public void setLstExcMsg(List<VoyagerExceptionModel> lstExcMsg) {
		this.lstExcMsg = lstExcMsg;
	}

	// exception page
	public void wuExceptionNavigation() {

		try {
			clearData();
			displayAllErrorMessages();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "WesternUnionExceptionPage.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/WesternUnionExceptionPage.xhtml");
			populateBranch();
			setFromDate(new Date());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// fetch All error msg from EX_VOYAGER_EXCEPTION
	public void displayAllErrorMessages()
	{
		setPendingTransApplicationList(null);
		List<WesternUnionTransfer>  lstWesternUnionTransfer = iwesternUnionService.getAllWUException(getFromDate(),getBranchCode(), getUserName());
		setPendingTransApplicationList(lstWesternUnionTransfer);
	}

	// fetch All error msg from EX_VOYAGER_EXCEPTION
	public void displayErrorMessagesForWUMTC(WesternUnionTransfer  lstWesternUnionTransfer)
	{
		// fetch all exception messages
		lstExcMsg.clear();
		List<VoyagerExceptionModel> lstException = iwesternUnionService.getAllWUExceptionErrorMsg(lstWesternUnionTransfer);
		if(lstException != null && !lstException.isEmpty()){

			Iterator iterator = lstException.iterator();
			while (iterator.hasNext()) {
				Object[] row = (Object[])iterator.next();
				VoyagerExceptionModel lstExceptionMsg = new VoyagerExceptionModel();
				
				lstExceptionMsg.setCompanyCode(new BigDecimal(row[0].toString()));
				lstExceptionMsg.setDocumentCode(new BigDecimal(row[1].toString()));
				lstExceptionMsg.setDocumentFinanceYear(new BigDecimal(row[2].toString()));
				lstExceptionMsg.setDocumentNo(new BigDecimal(row[3].toString()));
				lstExceptionMsg.setMtcNo(row[7].toString());
				lstExceptionMsg.setExceptionReason(row[8].toString());
				

				lstExcMsg.add(lstExceptionMsg);
			}

		}
	}

	// clear Exception Details
	public void clearExceptionReason(){
		lstExcMsg.clear();
	}

	// approve button in WU Exception Page
	public void approveExceptionMsg(){

		try{
			if(lstExcMsg != null && !lstExcMsg.isEmpty()){
				// call Procedure EX_WU_UPDATE_New
				VoyagerExceptionModel selectedExceptionMsg = lstExcMsg.get(0);

				BigDecimal companyCode = selectedExceptionMsg.getCompanyCode();
				BigDecimal documentCode = selectedExceptionMsg.getDocumentCode();
				BigDecimal documentFinYear = selectedExceptionMsg.getDocumentFinanceYear();
				BigDecimal documentNo = selectedExceptionMsg.getDocumentNo();

				errorMessage = iwesternUnionService.updateWUTransferForApprovalException(companyCode, documentCode, documentFinYear, documentNo);

				if (errorMessage == null) {
					// approved
					displayAllErrorMessages();
					clearExceptionReason();
				} else {
					setErrorMessage(errorMessage);
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}

			}else{
				setErrorMessage("Select MTC Number"); 
				RequestContext.getCurrentInstance().execute("error.show();");
			}
		}catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}

	}



}
