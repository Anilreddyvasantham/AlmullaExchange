package com.amg.exchange.complaint.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.complaint.model.ComplaintEnquiryRemittanceView;
import com.amg.exchange.complaint.model.ComplaintPendingEnquiryView;
import com.amg.exchange.complaint.service.IcomplaintEnquiry;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("complaintEnquiryBean")
@Scope("session")
public class ComplaintEnquiryBean<T> implements Serializable {
	  private static final long serialVersionUID = 1L;
	  private BigDecimal receiptNumber;
	  private BigDecimal locationId;
	  private String locationName;
	  private Date logDate;
	  private BigDecimal dealYear;
	  private BigDecimal complaintTypeId;
	  private String complaintTypeCode;
	  private String complaintTypeDesc;
	  private BigDecimal complaintAssignedId;
	  private String complaintAssignedCode;
	  private String complaintAssignedDesc;
	  private BigDecimal takenById;
	  private String takenByCode;
	  private String takenByDesc;
	  private BigDecimal remarksId;
	  private String remarksCode;
	  private String remarksDesc;
	  private String priorityType;
	  private String remarks;
	  private String pendingWith;
	  private String createdBy;
	  private Date createdDate;
	  private String isActive;
	  
	  //Boolean Variables
	  private Boolean booRenderDataTable=false;
	  private Boolean booSaveOrExit=false;
	  private List<CountryBranch> countryBranch = new ArrayList<CountryBranch>();
	  private List<ComplaintEnquiryDataTable> complaintEnquiryDtList=new ArrayList<ComplaintEnquiryDataTable>();
	  private List<ComplainPendingEnquiryDatatable> complaintPendingList=new ArrayList<ComplainPendingEnquiryDatatable>();
	  private List<PendingCompalintFollowupDatatable> complaintFollowupList=new ArrayList<PendingCompalintFollowupDatatable>();
	  
	  Map<BigDecimal, String> mapCountryBranchList = new HashMap<BigDecimal, String>();
	  SessionStateManage session=new SessionStateManage();
	  
	  @Autowired
	  IGeneralService<T> generalService; 
	  
	  @Autowired 
	  IcomplaintEnquiry complaintEnquiry;
	  
	  
	  private String errorMessage;
		
		
		
		
		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
	  public BigDecimal getReceiptNumber() {
	  	  return receiptNumber;
	  }
	  public void setReceiptNumber(BigDecimal receiptNumber) {
	  	  this.receiptNumber = receiptNumber;
	  }
	  public BigDecimal getLocationId() {
	  	  return locationId;
	  }
	  public void setLocationId(BigDecimal locationId) {
	  	  this.locationId = locationId;
	  }
	  public String getLocationName() {
	  	  return locationName;
	  }
	  public void setLocationName(String locationName) {
	  	  this.locationName = locationName;
	  }
	  public List<CountryBranch> getCountryBranch() {
	  	  return countryBranch;
	  }
	  public void setCountryBranch(List<CountryBranch> countryBranch) {
	  	  this.countryBranch = countryBranch;
	  }
	  public Date getLogDate() {
	  	  return logDate;
	  }
	  public void setLogDate(Date logDate) {
	  	  this.logDate = logDate;
	  }
	  public BigDecimal getDealYear() {
	  	  return dealYear;
	  }
	  public void setDealYear(BigDecimal dealYear) {
	  	  this.dealYear = dealYear;
	  }
	  public String getPriorityType() {
	  	  return priorityType;
	  }
	  public void setPriorityType(String priorityType) {
	  	  this.priorityType = priorityType;
	  }
	  public String getRemarks() {
	  	  return remarks;
	  }
	  public void setRemarks(String remarks) {
	  	  this.remarks = remarks;
	  }
	  public String getPendingWith() {
	  	  return pendingWith;
	  }
	  public void setPendingWith(String pendingWith) {
	  	  this.pendingWith = pendingWith;
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
	  public String getIsActive() {
	  	  return isActive;
	  }
	  public void setIsActive(String isActive) {
	  	  this.isActive = isActive;
	  }
	  public Boolean getBooRenderDataTable() {
	  	  return booRenderDataTable;
	  }
	  public void setBooRenderDataTable(Boolean booRenderDataTable) {
	  	  this.booRenderDataTable = booRenderDataTable;
	  }
	  public Boolean getBooSaveOrExit() {
	  	  return booSaveOrExit;
	  }
	  public void setBooSaveOrExit(Boolean booSaveOrExit) {
	  	  this.booSaveOrExit = booSaveOrExit;
	  }
	  public List<ComplaintEnquiryDataTable> getComplaintEnquiryDtList() {
	  	  return complaintEnquiryDtList;
	  }
	  public void setComplaintEnquiryDtList(List<ComplaintEnquiryDataTable> complaintEnquiryDtList) {
	  	  this.complaintEnquiryDtList = complaintEnquiryDtList;
	  }
	  public Map<BigDecimal, String> getMapCountryBranchList() {
	  	  return mapCountryBranchList;
	  }
	  public void setMapCountryBranchList(Map<BigDecimal, String> mapCountryBranchList) {
	  	  this.mapCountryBranchList = mapCountryBranchList;
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
	  public BigDecimal getComplaintAssignedId() {
	  	  return complaintAssignedId;
	  }
	  public void setComplaintAssignedId(BigDecimal complaintAssignedId) {
	  	  this.complaintAssignedId = complaintAssignedId;
	  }
	  public String getComplaintAssignedCode() {
	  	  return complaintAssignedCode;
	  }
	  public void setComplaintAssignedCode(String complaintAssignedCode) {
	  	  this.complaintAssignedCode = complaintAssignedCode;
	  }
	  public String getComplaintAssignedDesc() {
	  	  return complaintAssignedDesc;
	  }
	  public void setComplaintAssignedDesc(String complaintAssignedDesc) {
	  	  this.complaintAssignedDesc = complaintAssignedDesc;
	  }
	  public BigDecimal getTakenById() {
	  	  return takenById;
	  }
	  public void setTakenById(BigDecimal takenById) {
	  	  this.takenById = takenById;
	  }
	  public String getTakenByCode() {
	  	  return takenByCode;
	  }
	  public void setTakenByCode(String takenByCode) {
	  	  this.takenByCode = takenByCode;
	  }
	  public String getTakenByDesc() {
	  	  return takenByDesc;
	  }
	  public void setTakenByDesc(String takenByDesc) {
	  	  this.takenByDesc = takenByDesc;
	  }
	  public BigDecimal getRemarksId() {
	  	  return remarksId;
	  }
	  public void setRemarksId(BigDecimal remarksId) {
	  	  this.remarksId = remarksId;
	  }
	  public String getRemarksCode() {
	  	  return remarksCode;
	  }
	  public void setRemarksCode(String remarksCode) {
	  	  this.remarksCode = remarksCode;
	  }
	  public String getRemarksDesc() {
	  	  return remarksDesc;
	  }
	  public void setRemarksDesc(String remarksDesc) {
	  	  this.remarksDesc = remarksDesc;
	  }
	  
	  public List<ComplainPendingEnquiryDatatable> getComplaintPendingList() {
		return complaintPendingList;
	}
	public void setComplaintPendingList(
			List<ComplainPendingEnquiryDatatable> complaintPendingList) {
		this.complaintPendingList = complaintPendingList;
	}
	public List<PendingCompalintFollowupDatatable> getComplaintFollowupList() {
		return complaintFollowupList;
	}
	public void setComplaintFollowupList(
			List<PendingCompalintFollowupDatatable> complaintFollowupList) {
		this.complaintFollowupList = complaintFollowupList;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigationForComplaintEnquiry() {
		    complaintEnquiryDtList.clear();
		    setBooRenderDataTable(false);
		    setBooSaveOrExit(false);
		    clearAllFields();
		    //form loading display all Branches
		    try {
		    fetchAllBranches();
		    loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "ComplaintEnquiry.xhtml");
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/ComplaintEnquiry.xhtml");
		    } catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::pageNavigationForComplaintEnquiry");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
	  }
	  
	  
	  public void fetchAllBranches(){
		    mapCountryBranchList.clear();
		    countryBranch = new ArrayList<CountryBranch>();
		    try{
			countryBranch.addAll(generalService.getBranchDetails(session.getCountryId()));
			for (CountryBranch countryBranch1 : countryBranch) {
				  mapCountryBranchList.put(countryBranch1.getCountryBranchId(),countryBranch1.getBranchName());
			}
		    }catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::fetchAllBranches");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
	  }
	  
	  public void addRecordsToDataTable(){
		  complaintEnquiryDtList.clear();
		    try {
			List<ComplaintPendingEnquiryView> lstComplaintRemittanceView=complaintEnquiry.tofetchRecorsFromcomplaintView(getLocationId(),getReceiptNumber());
			if(lstComplaintRemittanceView.size()>0){
				  for (ComplaintPendingEnquiryView complaintPendingEnquiryView : lstComplaintRemittanceView) {
					ComplaintEnquiryDataTable complaintEnquiryDtObj=new ComplaintEnquiryDataTable();
					complaintEnquiryDtObj.setLocationId(complaintPendingEnquiryView.getLocationId());
					complaintEnquiryDtObj.setLocationName(mapCountryBranchList.get(complaintPendingEnquiryView.getLocationId()));
					complaintEnquiryDtObj.setLogDate(complaintPendingEnquiryView.getLogDate());
					complaintEnquiryDtObj.setReceiptNumber(complaintPendingEnquiryView.getComplaintReference());
					//complaint type Id
					complaintEnquiryDtObj.setComplaintTypeId(complaintPendingEnquiryView.getComplaintTypeId());
					complaintEnquiryDtObj.setComplaintTypeCode(complaintPendingEnquiryView.getComplaintTypeCode());
					complaintEnquiryDtObj.setComplaintTypeDesc(complaintPendingEnquiryView.getComplaintTypeDesc());
					complaintEnquiryDtObj.setDealYear(complaintPendingEnquiryView.getDealYear());
					//complaint Assigned
					complaintEnquiryDtObj.setComplaintAssignedId(complaintPendingEnquiryView.getAssignedId());
					complaintEnquiryDtObj.setComplaintAssignedCode(complaintPendingEnquiryView.getAssignedCode());
					complaintEnquiryDtObj.setComplaintAssignedDesc(complaintPendingEnquiryView.getComplaintAssignedDesc());
					//complaint Taken By
					complaintEnquiryDtObj.setTakenById(complaintPendingEnquiryView.getTekenById());
					complaintEnquiryDtObj.setTakenByCode(complaintPendingEnquiryView.getTakenByCode());
					complaintEnquiryDtObj.setTakenByDesc(complaintPendingEnquiryView.getTakenByDesc());
					//complaint Remarks 
					complaintEnquiryDtObj.setRemarksId(complaintPendingEnquiryView.getRemarksId());
					complaintEnquiryDtObj.setRemarksCode(complaintPendingEnquiryView.getRemarksCode());
					complaintEnquiryDtObj.setRemarksDesc(complaintPendingEnquiryView.getRemarksDesc());
					
					complaintEnquiryDtObj.setRemarks(complaintPendingEnquiryView.getRemarks());
					complaintEnquiryDtList.add(complaintEnquiryDtObj);
					setBooRenderDataTable(true);
					setBooSaveOrExit(true);
			      }
			}else{
					RequestContext.getCurrentInstance().execute("noRecords.show();");
					setBooRenderDataTable(true);
					setBooSaveOrExit(true);
					clearAllFields();
			}
		    } catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::addRecordsToDataTable");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		    
		/* ComplaintEnquiryDataTable complaintEnquiryDtObj = new ComplaintEnquiryDataTable();
		 complaintEnquiryDtObj.setLocationId(getLocationId());
		 complaintEnquiryDtObj.setLocationName(mapCountryBranchList.get(getLocationId()));
		 complaintEnquiryDtObj.setReceiptNumber(getReceiptNumber());
		 complaintEnquiryDtObj.setDealYear(new BigDecimal(2015));
		 complaintEnquiryDtObj.setComplaintType("STOP PAYMENT");
		 complaintEnquiryDtObj.setPriorityType("NORMAL");
		 complaintEnquiryDtObj.setRemarks("tranx cancelled 28/08/2015 .....refund");
		 complaintEnquiryDtObj.setPendingWith("BR");
		 complaintEnquiryDtObj.setCreatedBy("KANMANI1");
		 complaintEnquiryDtObj.setCreatedDate(new Date());
		 complaintEnquiryDtObj.setIsActive("Approved");*/
		// complaintEnquiryDtList.add(complaintEnquiryDtObj);
		
	  }
	  
	  public void clearAllFields(){
		    setLocationId(null);
		    setLocationName(null);
		    setReceiptNumber(null);
		    
	  }
	  public void exit(){
		    complaintEnquiryDtList.clear();
		    try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			}  catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::exit");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
	  }
	  public void cancel(){
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/ComplaintEnquiry.xhtml");
		    } catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::cancel");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				//return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			} 
	  }
	  
	  
	  public void viewFollowupEnquiry(ComplaintEnquiryDataTable complaineDatatTable){
		 if(complaineDatatTable.getSelect().equals(true)){
			 try{
		  setDealYear(complaineDatatTable.getDealYear());
		  setReceiptNumber(complaineDatatTable.getReceiptNumber());
			 }catch (NullPointerException ne) {
					// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setErrorMessage("Method Name::viewFollowupEnquiry");
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return;
				} 
		  }else{
			    complaineDatatTable.setSelect(false);
			    setDealYear(null);
				  setReceiptNumber(null);
		  }
		  
		    }
	   public void viewPendingComplainEnquiry(){
	  complaintPendingList.clear();
	  
	  try {
			List<ComplaintPendingEnquiryView> lstComplaintRemittanceView=complaintEnquiry.fetchRecorsFromcomplaintPendingView(getReceiptNumber());
			if(lstComplaintRemittanceView.size()>0){
				  for (ComplaintPendingEnquiryView complaintPendingEnquiryView : lstComplaintRemittanceView) {
					  ComplainPendingEnquiryDatatable complaintEnquiryDtObj=new ComplainPendingEnquiryDatatable();
					complaintEnquiryDtObj.setLogDate(complaintPendingEnquiryView.getLogDate());
					complaintEnquiryDtObj.setBrc(mapCountryBranchList.get(complaintPendingEnquiryView.getLocationId()));
					//complaint type Id
					/*complaintEnquiryDtObj.setComplaintTypeId(complaintPendingEnquiryView.getComplaintTypeId());*/
					complaintEnquiryDtObj.setComplainType(complaintPendingEnquiryView.getComplaintTypeDesc());
					//complaint Assigned
					complaintEnquiryDtObj.setRegidteredBy(complaintPendingEnquiryView.getComplaintAssignedDesc());
					complaintEnquiryDtObj.setClosedDate(complaintPendingEnquiryView.getClosedDate());
					//complaint Remarks 
		
					complaintEnquiryDtObj.setRemarks(complaintPendingEnquiryView.getRemarksDesc());
					complaintPendingList.add(complaintEnquiryDtObj);
					
			      }
				  
				  try {
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintpendingenquiry.xhtml");
			    } catch (Exception e) {
				      e.printStackTrace();
			    }  
		  
			}else{
				  RequestContext.getCurrentInstance().execute("dispalyForRemit.show();");
					setBooRenderDataTable(true);
					setBooSaveOrExit(true);
					clearAllFields();
					return;
			}
	  } catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::viewPendingComplainEnquiry");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
  }
  
 public void viewPendingFollowUpEnquiry(){
	 complaintFollowupList.clear();
	  
	  try {
			List<ComplaintEnquiryRemittanceView> lstComplaintRemittanceFollowupView=complaintEnquiry.fetchRecorsFromcomplaintFollowupEnquiryView(getReceiptNumber());
			if(lstComplaintRemittanceFollowupView.size()>0){
				  for (ComplaintEnquiryRemittanceView complaintFollowupEnquiryView : lstComplaintRemittanceFollowupView) {
					  PendingCompalintFollowupDatatable pendingFollowup=new PendingCompalintFollowupDatatable();
					  pendingFollowup.setAction(complaintFollowupEnquiryView.getComplaintActiondesc());
					  pendingFollowup.setAssignTo(complaintFollowupEnquiryView.getComplaintAssighedDesc());
					  pendingFollowup.setCommMethod(complaintFollowupEnquiryView.getCommunicationMethodDesc());
					  pendingFollowup.setDate(complaintFollowupEnquiryView.getFollowUpDate());
					  pendingFollowup.setFollowupUser(complaintFollowupEnquiryView.getComplaintAssighedDesc());
					  pendingFollowup.setRemarks(complaintFollowupEnquiryView.getComplaintremarksDesc());
					  complaintFollowupList.add(pendingFollowup);
					
			      }
				  
				  try {
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintfollowupenquiry.xhtml");
			    } catch (NullPointerException ne) {
					// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setErrorMessage("Method Name::back");
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return;
				} catch (Exception exception) {
					// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}
		  
			}else{
				  RequestContext.getCurrentInstance().execute("enqForRemit.show();");
					setBooRenderDataTable(true);
					setBooSaveOrExit(true);
					clearAllFields();
					return;
			}
	  }catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::viewPendingFollowUpEnquiry");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
  }
 
 public void back(){
	   try {
		      FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/ComplaintEnquiry.xhtml");
	    }catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::back");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
 }
 
 private List<ComplaintEnquiryDataTable> dtlDataTables;

public List<ComplaintEnquiryDataTable> getDtlDataTables() {
	  return dtlDataTables;
}
public void setDtlDataTables(List<ComplaintEnquiryDataTable> dtlDataTables) {
	  this.dtlDataTables = dtlDataTables;
}
 
}
