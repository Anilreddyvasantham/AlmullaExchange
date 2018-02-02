package com.amg.exchange.complaint.approve.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.complaint.DTO.ComplaintAssignedDTO;
import com.amg.exchange.complaint.approve.service.IComplaintAssignedApproveActivateAndDeActivateService;
import com.amg.exchange.complaint.bean.ComplaintAssignedDataTable;
import com.amg.exchange.complaint.service.IComplaintAssignedService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("complaintAssignedApproveBean")
@Scope("session")
public class ComplaintAssignedApproveActivateAndDeactivateBean<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger log=Logger.getLogger(ComplaintAssignedApproveActivateAndDeactivateBean.class);
	  
	 /* Component declaration Start*/
	  private BigDecimal applicationCountryId;
	  private String complaintAssignedCode;
	  private BigDecimal complaintAssignedId;
	  private String complaintAssignedTo;
	  private String logCompalint;
	  private String complaintEnglishFullDescription;
	  private String complaintArabicFullDescription;
	  private String complaintEnglishShortDescription;
	  private String complaintArabicShortDescription;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String remarks;
	  private String isActive;
	  private Boolean booSaveOrExit=false;
	  private Boolean booRenderDataTable=false;
	  private Boolean renderEditButton=false;
	  private String dynamicLabelForActivateDeactivate;
	  private Boolean booEditButton=false;
	  private Boolean booSubmitPanel=false;
	  private Boolean booClearPanel=false;
	  private BigDecimal complaintEnglishFullDescriptionPK;
	  private BigDecimal complaintArabicFullDescriptionPK;
	  private BigDecimal englishLanguageId;
	  private BigDecimal arabicLanguageId;
	  private Boolean booAdd=false;
	  private Boolean booApproval=false;
	  private Boolean ifEditClicked=false;
	  private BigDecimal countNoOfSave;
	  private Boolean booReadOnly=false;
	  private String logCompalintId;
	  private List<ComplaintAssignedDataTable> complaintAssignedDTList=new CopyOnWriteArrayList<ComplaintAssignedDataTable>();
	  private List<ComplaintAssignedDataTable> complaintAssignedNewDTList=new CopyOnWriteArrayList<ComplaintAssignedDataTable>();
	  private List<ComplaintAssignedDTO> complaintAssignedDTO=new CopyOnWriteArrayList<ComplaintAssignedDTO>();
	  SessionStateManage sessionStateManage=new SessionStateManage();
	  private ComplaintAssignedDataTable complaintDtObj=null;
	  
	  
	  @Autowired 
	  IComplaintAssignedApproveActivateAndDeActivateService complaintAssignedApproveActivateAndDeActivateService;
	  
	  @Autowired
	  IComplaintAssignedService complaintAssignedService ;
	  
	  
	  public BigDecimal getApplicationCountryId() {
	  	  return applicationCountryId;
	  }
	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
	  	  this.applicationCountryId = applicationCountryId;
	  }
	  
	  public String getComplaintAssignedCode() {
	  	  return complaintAssignedCode;
	  }
	  public void setComplaintAssignedCode(String complaintAssignedCode) {
	  	  this.complaintAssignedCode = complaintAssignedCode;
	  }
	  public BigDecimal getComplaintAssignedId() {
	  	  return complaintAssignedId;
	  }
	  public void setComplaintAssignedId(BigDecimal complaintAssignedId) {
	  	  this.complaintAssignedId = complaintAssignedId;
	  }
	  public String getComplaintEnglishFullDescription() {
	  	  return complaintEnglishFullDescription;
	  }
	  public void setComplaintEnglishFullDescription(String complaintEnglishFullDescription) {
	  	  this.complaintEnglishFullDescription = complaintEnglishFullDescription;
	  }
	  public String getComplaintArabicFullDescription() {
	  	  return complaintArabicFullDescription;
	  }
	  public void setComplaintArabicFullDescription(String complaintArabicFullDescription) {
	  	  this.complaintArabicFullDescription = complaintArabicFullDescription;
	  }
	  public String getComplaintEnglishShortDescription() {
	  	  return complaintEnglishShortDescription;
	  }
	  public void setComplaintEnglishShortDescription(String complaintEnglishShortDescription) {
	  	  this.complaintEnglishShortDescription = complaintEnglishShortDescription;
	  }
	  public String getComplaintArabicShortDescription() {
	  	  return complaintArabicShortDescription;
	  }
	  public void setComplaintArabicShortDescription(String complaintArabicShortDescription) {
	  	  this.complaintArabicShortDescription = complaintArabicShortDescription;
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
	  public String getRemarks() {
	  	  return remarks;
	  }
	  public void setRemarks(String remarks) {
	  	  this.remarks = remarks;
	  }
	  public String getIsActive() {
	  	  return isActive;
	  }
	  public void setIsActive(String isActive) {
	  	  this.isActive = isActive;
	  }
	  public Boolean getBooSaveOrExit() {
	  	  return booSaveOrExit;
	  }
	  public void setBooSaveOrExit(Boolean booSaveOrExit) {
	  	  this.booSaveOrExit = booSaveOrExit;
	  }
	  public Boolean getBooRenderDataTable() {
	  	  return booRenderDataTable;
	  }
	  public void setBooRenderDataTable(Boolean booRenderDataTable) {
	  	  this.booRenderDataTable = booRenderDataTable;
	  }
	  
	  public List<ComplaintAssignedDataTable> getComplaintAssignedDTList() {
	  	  return complaintAssignedDTList;
	  }
	  public void setComplaintAssignedDTList(List<ComplaintAssignedDataTable> complaintAssignedDTList) {
	  	  this.complaintAssignedDTList = complaintAssignedDTList;
	  }
	  public Boolean getRenderEditButton() {
	  	  return renderEditButton;
	  }
	  public void setRenderEditButton(Boolean renderEditButton) {
	  	  this.renderEditButton = renderEditButton;
	  }
	  public String getDynamicLabelForActivateDeactivate() {
	  	  return dynamicLabelForActivateDeactivate;
	  }
	  public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
	  	  this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	  }
	  public String getComplaintAssignedTo() {
	  	  return complaintAssignedTo;
	  }
	  public void setComplaintAssignedTo(String complaintAssignedTo) {
	  	  this.complaintAssignedTo = complaintAssignedTo;
	  }
	  public String getLogCompalint() {
	  	  return logCompalint;
	  }
	  public void setLogCompalint(String logCompalint) {
	  	  this.logCompalint = logCompalint;
	  }
	  public Boolean getBooEditButton() {
	  	  return booEditButton;
	  }
	  public void setBooEditButton(Boolean booEditButton) {
	  	  this.booEditButton = booEditButton;
	  }
	  public Boolean getBooSubmitPanel() {
	  	  return booSubmitPanel;
	  }
	  public void setBooSubmitPanel(Boolean booSubmitPanel) {
	  	  this.booSubmitPanel = booSubmitPanel;
	  }
	  public Boolean getBooClearPanel() {
	  	  return booClearPanel;
	  }
	  public void setBooClearPanel(Boolean booClearPanel) {
	  	  this.booClearPanel = booClearPanel;
	  }
	  public BigDecimal getComplaintEnglishFullDescriptionPK() {
	  	  return complaintEnglishFullDescriptionPK;
	  }
	  public void setComplaintEnglishFullDescriptionPK(BigDecimal complaintEnglishFullDescriptionPK) {
	  	  this.complaintEnglishFullDescriptionPK = complaintEnglishFullDescriptionPK;
	  }
	  public BigDecimal getComplaintArabicFullDescriptionPK() {
	  	  return complaintArabicFullDescriptionPK;
	  }
	  public void setComplaintArabicFullDescriptionPK(BigDecimal complaintArabicFullDescriptionPK) {
	  	  this.complaintArabicFullDescriptionPK = complaintArabicFullDescriptionPK;
	  }
	  public List<ComplaintAssignedDataTable> getComplaintAssignedNewDTList() {
	  	  return complaintAssignedNewDTList;
	  }
	  public void setComplaintAssignedNewDTList(List<ComplaintAssignedDataTable> complaintAssignedNewDTList) {
	  	  this.complaintAssignedNewDTList = complaintAssignedNewDTList;
	  }
	  public List<ComplaintAssignedDTO> getComplaintAssignedDTO() {
		  	  return complaintAssignedDTO;
	  }
	  public void setComplaintAssignedDTO(List<ComplaintAssignedDTO> complaintAssignedDTO) {
		  	  this.complaintAssignedDTO = complaintAssignedDTO;
	  }
	  public BigDecimal getEnglishLanguageId() {
		  	  return englishLanguageId;
	 }
	  public void setEnglishLanguageId(BigDecimal englishLanguageId) {
		  	  this.englishLanguageId = englishLanguageId;
	  }
	  public BigDecimal getArabicLanguageId() {
		  	  return arabicLanguageId;
	  }
	  public void setArabicLanguageId(BigDecimal arabicLanguageId) {
		  	  this.arabicLanguageId = arabicLanguageId;
	  }
	  public Boolean getBooAdd() {
		  	  return booAdd;
	  }
	  public void setBooAdd(Boolean booAdd) {
		  	  this.booAdd = booAdd;
	  }
	  public Boolean getBooApproval() {
		  	  return booApproval;
	  }
	  public void setBooApproval(Boolean booApproval) {
		  	  this.booApproval = booApproval;
	  }
	  public Boolean getIfEditClicked() {
		  	  return ifEditClicked;
	  }
	  public void setIfEditClicked(Boolean ifEditClicked) {
		  	  this.ifEditClicked = ifEditClicked;
          }

	  public BigDecimal getCountNoOfSave() {
	  	  return countNoOfSave;
	  }
	  public void setCountNoOfSave(BigDecimal countNoOfSave) {
	  	  this.countNoOfSave = countNoOfSave;
	  }
	  public Boolean getBooReadOnly() {
		  	  return booReadOnly;
	  }
	  public void setBooReadOnly(Boolean booReadOnly) {
		  	  this.booReadOnly = booReadOnly;
	  }
	  
	public ComplaintAssignedDataTable getComplaintDtObj() {
	  	  return complaintDtObj;
	  }
	  public void setComplaintDtObj(ComplaintAssignedDataTable complaintDtObj) {
	  	  this.complaintDtObj = complaintDtObj;
	  }
	  
	  
	  public String getLogCompalintId() {
	  	  return logCompalintId;
	  }
	  public void setLogCompalintId(String logCompalintId) {
	  	  this.logCompalintId = logCompalintId;
	  }
	  /*  Method declaration start*/
	  public void complaintAssignedPageNavigation(){
		    setBooSaveOrExit(false);
		    setBooRenderDataTable(false);
		    setBooClearPanel(false);
		    setBooSubmitPanel(false);
		    setBooAdd(true);
		    setBooApproval(false);
		    setBooEditButton(false);
		    setBooReadOnly(false);
		    complaintAssignedDTList.clear();
		    complaintAssignedNewDTList.clear();
		    clearAllFields();
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintassigned.xhtml");      
		    } catch (Exception e) {
			 e.printStackTrace();
		    }
	  }
	
	//view Started
	public void viewAllComplaintAssigned(){
		  Boolean childRecordCheck=false;
		  clearAllFields();
		  complaintAssignedDTList.clear();
		  List<ComplaintAssignedDTO> lstComplaintAssignedDTO= complaintAssignedApproveActivateAndDeActivateService.displayAllComplaintAssinedMasterToView();
		  if(lstComplaintAssignedDTO.size()>0){
			setBooRenderDataTable(true);
			setBooSaveOrExit(true);
			setBooAdd(true);
			setBooApproval(false);
			setBooReadOnly(false);
		  }else{
			    setBooRenderDataTable(false);
				setBooSaveOrExit(false);
				setBooAdd(true);
				setBooApproval(false);
				setBooReadOnly(false);
				RequestContext.getCurrentInstance().execute("noRecords.show();");
				return;    
		  }
		  for (ComplaintAssignedDTO complaintAssignedDTO : lstComplaintAssignedDTO) {
			   ComplaintAssignedDataTable complaintAssignedObj=new ComplaintAssignedDataTable();
			   complaintAssignedObj.setApplicationCountryId(complaintAssignedDTO.getApplicationCountryId());
			   complaintAssignedObj.setComplaintAssignedCode(complaintAssignedDTO.getComplaintAssignedCode());
			   complaintAssignedObj.setComplaintAssignedId(complaintAssignedDTO.getComplaintAssignedId());
			   complaintAssignedObj.setComplaintAssignedTo(complaintAssignedDTO.getComplaintAssignedTo());
			  // complaintAssignedObj.setLogCompalint(complaintAssignedDTO.getLogCompalint());
			   if(complaintAssignedDTO.getLogCompalint().equalsIgnoreCase(Constants.Yes)){
				     complaintAssignedObj.setLogCompalintId(Constants.YES);
				     complaintAssignedObj.setLogCompalint(Constants.Yes);
				}else{
					  complaintAssignedObj.setLogCompalintId(Constants.NO); 
					  complaintAssignedObj.setLogCompalint(Constants.No); 
				}
			   complaintAssignedObj.setCreatedBy(complaintAssignedDTO.getCreatedBy());
			   complaintAssignedObj.setCreatedDate(complaintAssignedDTO.getCreatedDate());
			   complaintAssignedObj.setModifiedBy(complaintAssignedDTO.getModifiedBy());
			   complaintAssignedObj.setModifiedDate(complaintAssignedDTO.getModifiedDate());
			   complaintAssignedObj.setApprovedBy(complaintAssignedDTO.getApprovedBy());
			   complaintAssignedObj.setApprovedDate(complaintAssignedDTO.getApprovedDate());
			   complaintAssignedObj.setRemarks(complaintAssignedDTO.getRemarks());
			   complaintAssignedObj.setIsActive(complaintAssignedDTO.getIsActive());
			   complaintAssignedObj.setRenderEditButton(true);
			   complaintAssignedObj.setBooEditButton(false);
			   if(complaintAssignedObj.getIsActive().equalsIgnoreCase(Constants.Yes)){
				     complaintAssignedObj.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
			   }else if (complaintAssignedObj.getIsActive().equalsIgnoreCase(Constants.D)) {
				     complaintAssignedObj.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
		    }else if (complaintAssignedObj.getIsActive().equalsIgnoreCase(Constants.U)&&complaintAssignedObj.getModifiedBy()==null&&complaintAssignedObj.getModifiedDate()==null&&complaintAssignedObj.getApprovedBy()==null&&complaintAssignedObj.getApprovedDate()==null&&complaintAssignedObj.getRemarks()==null) {
			      complaintAssignedObj.setDynamicLabelForActivateDeactivate(Constants.DELETE);
		    }else{
			      complaintAssignedObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);      
		    }
			   List<ComplaintAssignedDTO> lstComplaintAssignedDTO1= complaintAssignedApproveActivateAndDeActivateService.displayComplaintAssinedMasterAllFieldForApproval(complaintAssignedDTO.getComplaintAssignedId());
			   if(lstComplaintAssignedDTO1.size()>0){
				     childRecordCheck = true;
				     for (ComplaintAssignedDTO complaintAssignedDTO1 : lstComplaintAssignedDTO1) {
					     if(complaintAssignedDTO1.getLanguageId().intValue()==1){
						       complaintAssignedObj.setComplaintEnglishFullDescription(complaintAssignedDTO1.getFullDescription());
						       complaintAssignedObj.setComplaintEnglishShortDescription(complaintAssignedDTO1.getShortDescription());
						       complaintAssignedObj.setComplaintEnglishFullDescriptionPK(complaintAssignedDTO1.getComplaintAssignedDescId());
						       complaintAssignedObj.setEnglishLanguageId(complaintAssignedDTO1.getLanguageId());    
					     }else{
						       complaintAssignedObj.setComplaintArabicFullDescription(complaintAssignedDTO1.getFullDescription());
						       complaintAssignedObj.setComplaintArabicShortDescription(complaintAssignedDTO1.getShortDescription());
						       complaintAssignedObj.setComplaintArabicFullDescriptionPK(complaintAssignedDTO1.getComplaintAssignedDescId());
						       complaintAssignedObj.setArabicLanguageId(complaintAssignedDTO1.getLanguageId());
					     }
			      }
					
			      }else{
				childRecordCheck=false;	
			   }
			   if(childRecordCheck==true){
			   complaintAssignedDTList.add(complaintAssignedObj);
			   }
			   
	  }
		complaintAssignedDTList.addAll(complaintAssignedNewDTList);
		clearAllFields();
		  try {
			    FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintassigned.xhtml");      
	  } catch (Exception e) {
		   e.printStackTrace();
	  }
	}
	//Ended for View
	  
	  public void checkStatus(ComplaintAssignedDataTable complaintAssignedDTObj){
		    if(complaintAssignedDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
			      RequestContext.getCurrentInstance().execute("pending.show();");
				return;     
		    }else if(complaintAssignedDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
			      complaintAssignedDTList.remove(complaintAssignedDTObj);
			      complaintAssignedNewDTList.remove(complaintAssignedDTObj);    
		    }else if(complaintAssignedDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)){
			        complaintAssignedDTObj.setRemarksCheck(true);
			        setApprovedBy(complaintAssignedDTObj.getApprovedBy());
				setApprovedDate(complaintAssignedDTObj.getApprovedDate());
				RequestContext.getCurrentInstance().execute("remarks.show();");
		    }else if(complaintAssignedDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)){
			      	complaintAssignedDTObj.setActivateRecordCheck(true);
				RequestContext.getCurrentInstance().execute("activateRecord.show();");
				return;      
		    }
		    else if (complaintAssignedDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)&&complaintAssignedDTObj.getModifiedBy()==null&&complaintAssignedDTObj.getModifiedDate()==null&&complaintAssignedDTObj.getApprovedBy()==null&&complaintAssignedDTObj.getApprovedDate()==null&&complaintAssignedDTObj.getRemarks()==null) {
			      complaintAssignedDTObj.setPermanetDeleteCheck(true);
				RequestContext.getCurrentInstance().execute("permanentDelete.show();");
				return;			      
		    }
		    if(complaintAssignedDTList.size()==0){
			      setBooRenderDataTable(false);
				    setBooSaveOrExit(false);
				    setBooAdd(true);
				    setBooApproval(false);
				    setBooReadOnly(false);    
		    }
	  }
	  public void CompleteAssignedConfirmPermanentDelete(){
		    if(complaintAssignedDTList.size()>0){
			      for (ComplaintAssignedDataTable complaintAssignedDtObj : complaintAssignedDTList) {
				if(complaintAssignedDtObj.getPermanetDeleteCheck()!=null){
					  if(complaintAssignedDtObj.getPermanetDeleteCheck().equals(true)){
						    deleteRecordCompliantAssigned(complaintAssignedDtObj);
						    complaintAssignedDTList.remove(complaintAssignedDtObj);
					  }
				}
			      }
		    }   
	  }
	  public void deleteRecordCompliantAssigned(ComplaintAssignedDataTable dataTable){
		  complaintAssignedApproveActivateAndDeActivateService.deleteComplaintAssinedMaster(dataTable.getComplaintAssignedId(),dataTable.getComplaintEnglishFullDescriptionPK(),dataTable.getComplaintArabicFullDescriptionPK());
	  }
	 
	  public void UpdateComplaintAssigenedCode(ComplaintAssignedDataTable complaintAssignedDTObj){
		    try {
			ComplaintAssignedDTO complaintAssignedDTO=new ComplaintAssignedDTO(); 
			complaintAssignedDTO.setApplicationCountryId(complaintAssignedDTObj.getApplicationCountryId());
			complaintAssignedDTO.setComplaintAssignedCode(complaintAssignedDTObj.getComplaintAssignedCode());
			complaintAssignedDTO.setComplaintAssignedId(complaintAssignedDTObj.getComplaintAssignedId());
			complaintAssignedDTO.setComplaintAssignedTo(complaintAssignedDTObj.getComplaintAssignedTo());
			//complaintAssignedDTO.setLogCompalint(complaintAssignedDTObj.getLogCompalint());
			if(complaintAssignedDTObj.getLogCompalint().equalsIgnoreCase(Constants.YES)){
				  complaintAssignedDTO.setLogCompalintId(Constants.YES);
				  complaintAssignedDTO.setLogCompalint(Constants.Yes);
			}else{
				  complaintAssignedDTO.setLogCompalintId(Constants.NO); 
				  complaintAssignedDTO.setLogCompalint(Constants.No); 
			}
			complaintAssignedDTO.setCreatedBy(complaintAssignedDTObj.getCreatedBy());
			complaintAssignedDTO.setCreatedDate(complaintAssignedDTObj.getCreatedDate());
			complaintAssignedDTO.setModifiedBy(sessionStateManage.getUserName());
			complaintAssignedDTO.setModifiedDate(new Date());
			complaintAssignedDTO.setApprovedBy(null);
			complaintAssignedDTO.setApprovedDate(null);
			complaintAssignedDTO.setRemarks(complaintAssignedDTObj.getRemarks());
			complaintAssignedDTO.setIsActive(Constants.D);
			complaintAssignedDTO.setComplaintEnglishFullDescriptionId(complaintAssignedDTObj.getComplaintEnglishFullDescriptionPK());
			complaintAssignedDTO.setComplaintArabicFullDescriptionId(complaintAssignedDTObj.getComplaintArabicFullDescriptionPK());
			complaintAssignedDTO.setEnglishLanguageId(complaintAssignedDTObj.getEnglishLanguageId());
			complaintAssignedDTO.setArabicLanguageId(complaintAssignedDTObj.getArabicLanguageId());
			complaintAssignedDTO.setComplaintEnglishFullDescription(complaintAssignedDTObj.getComplaintEnglishFullDescription());
			complaintAssignedDTO.setComplaintEnglishShortDescription(complaintAssignedDTObj.getComplaintEnglishShortDescription());
			complaintAssignedDTO.setComplaintArabicFullDescription(complaintAssignedDTObj.getComplaintArabicFullDescription());
			complaintAssignedDTO.setComplaintArabicShortDescription(complaintAssignedDTObj.getComplaintArabicShortDescription());
			
			complaintAssignedApproveActivateAndDeActivateService.UpdateComplaintAssinedMaster(complaintAssignedDTO);
		    } catch (Exception exception) {
			      log.error("Error Occured while Saving ComplaintAssigned and ComplaintAssignedDesc Records  :"+exception.getMessage());
				    RequestContext.getCurrentInstance().execute("notSaved.show();");
				    return;
		    }
	  }
	  public void clickOkRemarks(){
		 if(getRemarks() != null && !getRemarks().equals("")){
			   for (ComplaintAssignedDataTable compalAssignedDataTable : complaintAssignedDTList) {
				    if(compalAssignedDataTable.getRemarksCheck() != null){
					 if(compalAssignedDataTable.getRemarksCheck().equals(true)) {
						   compalAssignedDataTable.setRemarks(getRemarks());
							UpdateComplaintAssigenedCode(compalAssignedDataTable);
							clearAllFields();
							viewAllComplaintAssigned();
							setRemarks(null);	   
					 }
				    }
		    }
		
		 }else{
		   RequestContext.getCurrentInstance().execute("remarksEmpty.show();");    
		 }
	  }
	  public void conformToDeActivteCompliantAssigned(ComplaintAssignedDataTable dataTable){
		  complaintAssignedApproveActivateAndDeActivateService.deActivateComplaintAssinedMaster(dataTable.getComplaintAssignedId(),sessionStateManage.getUserName());
		    viewAllComplaintAssigned();
	  }
	  public void activateRecord(){
		    if(complaintAssignedDTList.size()>0){
			      for (ComplaintAssignedDataTable complaintAssignedDtObj : complaintAssignedDTList) {
				if(complaintAssignedDtObj.getActivateRecordCheck()!=null){
					  if(complaintAssignedDtObj.getActivateRecordCheck().equals(true)){
						    conformToDeActivteCompliantAssigned(complaintAssignedDtObj);    
					  }
				}
			      }
		    }
	  }
	  public void cancelRemarks() {
			setRemarks(null);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintassigned.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	  public void clearAllFields(){
		    setApplicationCountryId(null);
		    setComplaintAssignedCode(null);
		    setComplaintAssignedTo(null);
		    setLogCompalint(null);
		    setComplaintEnglishFullDescription(null);
		    setComplaintArabicFullDescription(null);
		    setComplaintEnglishShortDescription(null);
		    setComplaintArabicShortDescription(null);
		    setComplaintAssignedTo(null);
		    setComplaintAssignedId(null);
		    setLogCompalint(null);
		    setLogCompalintId(null);
		    setEnglishLanguageId(null);
		    setArabicLanguageId(null);
		    setComplaintArabicFullDescriptionPK(null);
		    setComplaintEnglishFullDescriptionPK(null);
		    setIfEditClicked(false);
	  }
	  
	  //Exit into Home
	  public void exit(){
		    complaintAssignedDTList.clear();
		    complaintAssignedNewDTList.clear();
		    try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}    
	  }
	  @Autowired
	  LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	  /*Approval Started */
	  public void complaintAssignedApprovalPageNavigation(){
		    setBooAdd(false);
		    setBooApproval(false);
		    setBooSaveOrExit(false);
		    setBooRenderDataTable(true);
		    setBooClearPanel(false);
		    setBooSubmitPanel(false);
		    setBooReadOnly(false);
		    complaintAssignedDTList.clear();
		    complaintAssignedNewDTList.clear();
		    fetchRecordforComplaintAssignedApproval();
		    try {
		    	loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "ComplaintAssignedApprovel.xhtml");
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/ComplaintAssignedApprovel.xhtml");      
		    } catch (Exception e) {
			 e.printStackTrace();
		    }
	  }
	  
	  public void fetchRecordforComplaintAssignedApproval(){
		    Boolean childRecordCheck=false;
		    complaintAssignedDTList.clear();
		    complaintAssignedNewDTList.clear();
		    List<ComplaintAssignedDTO> lstComplaintAssignedDTO= complaintAssignedApproveActivateAndDeActivateService.displayAllComplaintAssinedMasterForApproval();
			  if(lstComplaintAssignedDTO.size()>0){
			  for (ComplaintAssignedDTO complaintAssignedDTO : lstComplaintAssignedDTO) {
				   ComplaintAssignedDataTable complaintAssignedObj=new ComplaintAssignedDataTable();
				   complaintAssignedObj.setApplicationCountryId(complaintAssignedDTO.getApplicationCountryId());
				   complaintAssignedObj.setComplaintAssignedCode(complaintAssignedDTO.getComplaintAssignedCode());
				   complaintAssignedObj.setComplaintAssignedId(complaintAssignedDTO.getComplaintAssignedId());
				   complaintAssignedObj.setComplaintAssignedTo(complaintAssignedDTO.getComplaintAssignedTo());
				   complaintAssignedObj.setLogCompalint(complaintAssignedDTO.getLogCompalint());
				   complaintAssignedObj.setCreatedBy(complaintAssignedDTO.getCreatedBy());
				   complaintAssignedObj.setCreatedDate(complaintAssignedDTO.getCreatedDate());
				   complaintAssignedObj.setModifiedBy(complaintAssignedDTO.getModifiedBy());
				   complaintAssignedObj.setModifiedDate(complaintAssignedDTO.getModifiedDate());
				   complaintAssignedObj.setApprovedBy(complaintAssignedDTO.getApprovedBy());
				   complaintAssignedObj.setApprovedDate(complaintAssignedDTO.getApprovedDate());
				   complaintAssignedObj.setRemarks(complaintAssignedDTO.getRemarks());
				   complaintAssignedObj.setIsActive(complaintAssignedDTO.getIsActive());
				   complaintAssignedObj.setRenderEditButton(true);
				   if(complaintAssignedDTO.getLogCompalint().equalsIgnoreCase(Constants.Yes)){
					     complaintAssignedObj.setLogCompalintId(Constants.YES);
					     complaintAssignedObj.setLogCompalint(Constants.YES);
					}else{
						  complaintAssignedObj.setLogCompalintId(Constants.NO); 
						  complaintAssignedObj.setLogCompalint(Constants.NO); 
					}
				   if(complaintAssignedObj.getIsActive().equalsIgnoreCase(Constants.Yes)){
					     complaintAssignedObj.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				   }else if (complaintAssignedObj.getIsActive().equalsIgnoreCase(Constants.D)) {
					     complaintAssignedObj.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
			    }else if (complaintAssignedObj.getIsActive().equalsIgnoreCase(Constants.U)&&complaintAssignedObj.getModifiedBy()==null&&complaintAssignedObj.getModifiedDate()==null&&complaintAssignedObj.getApprovedBy()==null&&complaintAssignedObj.getApprovedDate()==null&&complaintAssignedObj.getRemarks()==null) {
				      complaintAssignedObj.setDynamicLabelForActivateDeactivate(Constants.DELETE);
			    }else{
				      complaintAssignedObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);      
			    }
				   List<ComplaintAssignedDTO> lstComplaintAssignedDTO1=complaintAssignedApproveActivateAndDeActivateService.displayComplaintAssinedMasterAllFieldForApproval(complaintAssignedDTO.getComplaintAssignedId());
				   if(lstComplaintAssignedDTO1.size()>0){
					     childRecordCheck = true;
					     for (ComplaintAssignedDTO complaintAssignedDTO1 : lstComplaintAssignedDTO1) {
						     if(complaintAssignedDTO1.getLanguageId().intValue()==1){
							       complaintAssignedObj.setComplaintEnglishFullDescription(complaintAssignedDTO1.getFullDescription());
							       complaintAssignedObj.setComplaintEnglishShortDescription(complaintAssignedDTO1.getShortDescription());
							       complaintAssignedObj.setComplaintEnglishFullDescriptionPK(complaintAssignedDTO1.getComplaintAssignedDescId());
							       complaintAssignedObj.setEnglishLanguageId(complaintAssignedDTO1.getLanguageId());    
						     }else{
							       complaintAssignedObj.setComplaintArabicFullDescription(complaintAssignedDTO1.getFullDescription());
							       complaintAssignedObj.setComplaintArabicShortDescription(complaintAssignedDTO1.getShortDescription());
							       complaintAssignedObj.setComplaintArabicFullDescriptionPK(complaintAssignedDTO1.getComplaintAssignedDescId());
							       complaintAssignedObj.setArabicLanguageId(complaintAssignedDTO1.getLanguageId());
						     }
				      }
						
				      }else{
					childRecordCheck=false;	
				   }
				   if(childRecordCheck==true){
				   complaintAssignedDTList.add(complaintAssignedObj);
				   }
				   }
			  }
			 
	  }
	  
	  public void approvelCheckForComplientAssignedUser(ComplaintAssignedDataTable dataTable){
		    if(!(dataTable.getModifiedBy()==null?  dataTable.getCreatedBy():dataTable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName()))
			{
			  setComplaintAssignedCode(dataTable.getComplaintAssignedCode());
			  setComplaintAssignedId(dataTable.getComplaintAssignedId());
			  setComplaintAssignedTo(dataTable.getComplaintAssignedTo());
			 // setLogCompalint(dataTable.getLogCompalint());
			  if(dataTable.getLogCompalintId().equalsIgnoreCase(Constants.YES )){
					setLogCompalint(Constants.Yes);
					setLogCompalintId(Constants.YES);
			      }else{
					setLogCompalint(Constants.No);
					setLogCompalintId(Constants.NO);
			      }
			  setCreatedBy(dataTable.getCreatedBy());
			  setCreatedDate(dataTable.getCreatedDate());
			  setModifiedBy(dataTable.getModifiedBy());
			  setModifiedDate(dataTable.getModifiedDate());
			  setIsActive(dataTable.getIsActive());
			  setComplaintEnglishFullDescription(dataTable.getComplaintEnglishFullDescription());
			  setComplaintEnglishShortDescription(dataTable.getComplaintEnglishShortDescription());
			  setComplaintArabicFullDescription(dataTable.getComplaintArabicFullDescription());
			  setComplaintArabicShortDescription(dataTable.getComplaintArabicShortDescription());
			  setBooRenderDataTable(false);
			  setBooSaveOrExit(false);
			  setBooAdd(false);
			  setBooApproval(true);
			  setBooReadOnly(true);
			  try {
				    FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/CompalintAssignedApprovalPage.xhtml"); 	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
			RequestContext.getCurrentInstance().execute("notApproved.show();");
			}
	  }
		public void complaintAssignedApproveRecord(){
			  String approvalMsg = complaintAssignedApproveActivateAndDeActivateService.checkComplaintAssinedMasterApproveMultiUser(getComplaintAssignedId(),sessionStateManage.getUserName());
			  if(approvalMsg.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("approve.show();");
				 }else{
					 RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
					return; 
				 }
		}
		public void clickOnOKApprove(){
			  clearAllFields();
			  setBooAdd(false);
			  setBooRenderDataTable(false);
			  setBooSaveOrExit(false);
			  setBooApproval(false);
			  setBooReadOnly(false);
			    try {
				      fetchRecordforComplaintAssignedApproval();
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/ComplaintAssignedApprovel.xhtml");      
			    } catch (Exception e) {
				 e.printStackTrace();
			    }
		}
		public void clickOnOkButton(){
			  complaintAssignedDTList.clear();
			  fetchRecordforComplaintAssignedApproval();
			  try {
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/ComplaintAssignedApprovel.xhtml");      
			    } catch (Exception e) {
				 e.printStackTrace();
			    }
		}
		public void ComplaintAssignedApprovedByOhterPerson(){
			  fetchRecordforComplaintAssignedApproval();
			  try {
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/ComplaintAssignedApprovel.xhtml");      
			    } catch (Exception e) {
				 e.printStackTrace();
			    }  
		}
		public void complaintAssignedCancel(){
			  fetchRecordforComplaintAssignedApproval();
			  try {
				      FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/ComplaintAssignedApprovel.xhtml");      
			    } catch (Exception e) {
				 e.printStackTrace();
			    } 
		}
	  /*Approval Ended */
	  /*  Method declaration End*/
	
}
