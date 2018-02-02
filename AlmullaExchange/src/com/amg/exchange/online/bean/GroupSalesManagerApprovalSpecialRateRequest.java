/*package com.amg.exchange.online.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.mail.ApplicationMailer;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.service.IGroupSalesManagerApprovalSpecialRateRequestService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.util.SessionStateManage;

@Component("groupSalesManagerApprovalSpecialRateRequest")
@Scope("session")
public class GroupSalesManagerApprovalSpecialRateRequest implements Serializable{

	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(GroupSalesManagerApprovalSpecialRateRequest.class);
	//Page level Variables
	private BigDecimal rateOfferedIdPk;
	private Date dateOfRequest;
	private String branch;
	private String currency;
	private BigDecimal amount;
	private BigDecimal rateOffered;
	private String transctionConcluded;
	private String emailId;
	//common Variables
	private String errorMessage;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	
	SessionStateManage session= new SessionStateManage();
	
	//DataTable List
	private List<GroupSalesManagerApprovalSpecialRateReqDataTable> lstGroupSalesMgrSpecialRateRequest=new ArrayList<GroupSalesManagerApprovalSpecialRateReqDataTable>();
	
	//service Declaritions
	@Autowired
	IGroupSalesManagerApprovalSpecialRateRequestService groupSalesManagerApprovalSpecialRateRequestService;
	
	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	ApplicationMailer applicationMailer;
	
	public BigDecimal getRateOfferedIdPk() {
		return rateOfferedIdPk;
	}
	public void setRateOfferedIdPk(BigDecimal rateOfferedIdPk) {
		this.rateOfferedIdPk = rateOfferedIdPk;
	}
	public Date getDateOfRequest() {
		return dateOfRequest;
	}
	public void setDateOfRequest(Date dateOfRequest) {
		this.dateOfRequest = dateOfRequest;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getRateOffered() {
		return rateOffered;
	}
	public void setRateOffered(BigDecimal rateOffered) {
		this.rateOffered = rateOffered;
	}
	public String getTransctionConcluded() {
		return transctionConcluded;
	}
	public void setTransctionConcluded(String transctionConcluded) {
		this.transctionConcluded = transctionConcluded;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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
	public List<GroupSalesManagerApprovalSpecialRateReqDataTable> getLstGroupSalesMgrSpecialRateRequest() {
		return lstGroupSalesMgrSpecialRateRequest;
	}
	public void setLstGroupSalesMgrSpecialRateRequest(
			List<GroupSalesManagerApprovalSpecialRateReqDataTable> lstGroupSalesMgrSpecialRateRequest) {
		this.lstGroupSalesMgrSpecialRateRequest = lstGroupSalesMgrSpecialRateRequest;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	//Page Navigation
	public void groupSalesManagerApprovalPageNavigation(){
		lstGroupSalesMgrSpecialRateRequest.clear();
		toFetchAllRecords();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/GroupSalesManagerApprovalSpecialRateRequest.xhtml");
		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("groupSalesManagerApprovalPageNavigation  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
		}
	}
	
	//clearAllFields
	public void clearAllFields(){
		setRateOffered(null);
		setTransctionConcluded(null);
	}
	
	//Fetch records from Db
	public void toFetchAllRecords(){
		lstGroupSalesMgrSpecialRateRequest.clear();
		try{
			List<RatePlaceOrder> lstRatePlaceOrders=groupSalesManagerApprovalSpecialRateRequestService.fetchAllRecrds();
			if(lstRatePlaceOrders.size()>0){
				for (RatePlaceOrder ratePlaceOrder : lstRatePlaceOrders) {
					GroupSalesManagerApprovalSpecialRateReqDataTable dataTable=new GroupSalesManagerApprovalSpecialRateReqDataTable();
				//	if(ratePlaceOrder.getCreatedDate().getMinutes()<15 && ratePlaceOrder.getCreatedDate().getMinutes()>=15){
						dataTable.setRateOfferedIdPk(ratePlaceOrder.getRatePlaceOrderId());
						dataTable.setDateOfRequest(ratePlaceOrder.getCreatedDate());
						if(ratePlaceOrder.getCountryBranchId() != null){
						List<CountryBranch> list=generalService.getBranchDetailsForToLocation(session.getCountryId(), ratePlaceOrder.getCountryBranchId());
						if(list.size() != 0){
						dataTable.setBranch(list.get(0).getBranchName());
						}
						}
						if(ratePlaceOrder.getCurrencyId() != null){
						dataTable.setCurrency(generalService.getCurrencyName(ratePlaceOrder.getCurrencyId()));
						}
						dataTable.setAmount(ratePlaceOrder.getRemitType());
						dataTable.setRateOffered(getRateOffered());
						dataTable.setTransctionConcluded(getTransctionConcluded());
						dataTable.setCreatedBy(ratePlaceOrder.getCreatedBy());
						dataTable.setCreatedDate(ratePlaceOrder.getCreatedDate());
						dataTable.setEmailId(ratePlaceOrder.getCustomerEmail());
						//dataTable.setIsActive(ratePlaceOrder.get);
						lstGroupSalesMgrSpecialRateRequest.add(dataTable);
					}
				}
			//}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::toFetchAllRecords");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
			}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
			}
	}

	public void save(){
		if(lstGroupSalesMgrSpecialRateRequest.isEmpty()){
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		}else{
			try{
				for (GroupSalesManagerApprovalSpecialRateReqDataTable groupSalesMgrObj : lstGroupSalesMgrSpecialRateRequest) {
						groupSalesManagerApprovalSpecialRateRequestService.approveAllRecords(groupSalesMgrObj.getRateOfferedIdPk(),groupSalesMgrObj.getRateOffered(),groupSalesMgrObj.getTransctionConcluded(),session.getUserName(),new Date());
						if (groupSalesMgrObj.getEmailId() != null) {
							applicationMailer.sendRegistrationMail(groupSalesMgrObj.getEmailId(), "Pin Generation ",session.getUserName());
						}
				}
				RequestContext.getCurrentInstance().execute("complete.show();");
			}catch(NullPointerException ne){
				log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("save::toFetchAllRecords");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return; 
				}catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage()); 
				RequestContext.getCurrentInstance().execute("error.show();");
				return;       
				}
		}
	}
	
	public void clickOnOKSave(){
		lstGroupSalesMgrSpecialRateRequest.clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/GroupSalesManagerApprovalSpecialRateRequest.xhtml");
		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("clickOnOKSave  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
		}
	}
}
*/