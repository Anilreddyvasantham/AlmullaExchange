package com.amg.exchange.complaint.bean;

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
import com.amg.exchange.complaint.service.IComplaintAssignedService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("complaintAssignedBean")
@Scope("session")
public class ComplaintAssignedBean<T> implements Serializable {
	  private static final Logger log=Logger.getLogger(ComplaintAssignedBean.class);
	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  
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
	  private int count=0;
	  private BigDecimal countNoOfSave;
	  private Boolean booReadOnly=false;
	  private String logCompalintId;
	  private Boolean disableSubmitButton=false;
	  private List<ComplaintAssignedDataTable> complaintAssignedDTList=new CopyOnWriteArrayList<ComplaintAssignedDataTable>();
	  private List<ComplaintAssignedDataTable> complaintAssignedNewDTList=new CopyOnWriteArrayList<ComplaintAssignedDataTable>();
	  private List<ComplaintAssignedDTO> complaintAssignedDTO=new CopyOnWriteArrayList<ComplaintAssignedDTO>();
	  
	  private String errorMessage;
	  
	  public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
	  
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
	  public String getLogCompalintId() {
		  	  return logCompalintId;
		  }
	  public void setLogCompalintId(String logCompalintId) {
		  	  this.logCompalintId = logCompalintId;
		  }
	  
	  public Boolean getDisableSubmitButton() {
		  	  return disableSubmitButton;
	  }
	  public void setDisableSubmitButton(Boolean disableSubmitButton) {
		  	  this.disableSubmitButton = disableSubmitButton;
	  }

	  /* Component declaration End*/
	  
	  /*Auto wire declaration start*/

	  
	  
	  @Autowired
	  IComplaintAssignedService complaintAssignedService;
	  
	  @Autowired
	  IComplaintAssignedApproveActivateAndDeActivateService complaintAssignedApproveActivateAndDeActivatService;
	  
	  SessionStateManage sessionStateManage=new SessionStateManage();
	  private ComplaintAssignedDataTable complaintDtObj=null;
	  
	  
	public ComplaintAssignedDataTable getComplaintDtObj() {
	  	  return complaintDtObj;
	  }
	  public void setComplaintDtObj(ComplaintAssignedDataTable complaintDtObj) {
	  	  this.complaintDtObj = complaintDtObj;
	  }
	  @Autowired
	  LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
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
		    	loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "complaintassigned.xhtml");
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintassigned.xhtml");      
		    } catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::complaintAssignedPageNavigation");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
	  }
	  public List<String> autoComplete(String query){
		    if (query.length() > 0) {
		    	try{
				return complaintAssignedService.getComplaintAssignedCodeList(query);
		    	}catch (NullPointerException ne) {
					// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setErrorMessage("Method Name::autoComplete");
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return null;
				} catch (Exception exception) {
					// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("error.show();");
					return null;
				}
			} else {
				return null;
			}
	  }

	  public void complaintAssignedToDataTable(){
		  try{
		    setBooClearPanel(false);
		    setBooEditButton(false);
		    setBooSubmitPanel(false);
		    ComplaintAssignedDataTable complaintAssignedDTObj=new ComplaintAssignedDataTable();
		    complaintAssignedDTObj.setApplicationCountryId(sessionStateManage.getCountryId());
		   // complaintAssignedDTObj.setComplaintAssignedCode(getComplaintAssignedCode());
		    complaintAssignedDTObj.setComplaintAssignedTo(getComplaintAssignedTo());
		   // complaintAssignedDTObj.setLogCompalint(getLogCompalint());
		    if(getLogCompalint().equalsIgnoreCase(Constants.Yes)){
			      complaintAssignedDTObj.setLogCompalintId(Constants.YES);
			      complaintAssignedDTObj.setLogCompalint(Constants.Yes);
				}else{
					  complaintAssignedDTObj.setLogCompalintId(Constants.NO);
					  complaintAssignedDTObj.setLogCompalint(Constants.No);
				}
		    complaintAssignedDTObj.setComplaintEnglishFullDescription(getComplaintEnglishFullDescription());
		    complaintAssignedDTObj.setComplaintArabicFullDescription(getComplaintArabicFullDescription());
		    complaintAssignedDTObj.setComplaintEnglishShortDescription(getComplaintEnglishShortDescription());
		    complaintAssignedDTObj.setComplaintArabicShortDescription(getComplaintArabicShortDescription());
		    complaintAssignedDTObj.setRenderEditButton(true);
		    complaintAssignedDTObj.setComplaintEnglishFullDescriptionPK(getComplaintEnglishFullDescriptionPK());
		    complaintAssignedDTObj.setEnglishLanguageId(getEnglishLanguageId());;
		    complaintAssignedDTObj.setComplaintArabicFullDescriptionPK(getComplaintArabicFullDescriptionPK());
		    complaintAssignedDTObj.setArabicLanguageId(getArabicLanguageId());
		    complaintAssignedDTObj.setCreatedBy(getCreatedBy());
		    complaintAssignedDTObj.setCreatedDate(getCreatedDate());
		    complaintAssignedDTObj.setComplaintAssignedId(getComplaintAssignedId());
		    if (getComplaintAssignedId() != null) {
			      if(getIfEditClicked().equals(true) && getComplaintDtObj() != null){
						 if((complaintAssignedDTObj.getComplaintAssignedTo().equalsIgnoreCase(complaintDtObj.getComplaintAssignedTo())
										&&complaintAssignedDTObj.getComplaintEnglishFullDescription().equalsIgnoreCase(complaintDtObj.getComplaintEnglishFullDescription())
										&&complaintAssignedDTObj.getComplaintEnglishShortDescription().equalsIgnoreCase(complaintDtObj.getComplaintEnglishShortDescription())
										&&complaintAssignedDTObj.getComplaintArabicFullDescription().equalsIgnoreCase(complaintDtObj.getComplaintArabicFullDescription())
										&&complaintAssignedDTObj.getComplaintArabicShortDescription().equalsIgnoreCase(complaintDtObj.getComplaintArabicShortDescription())
										&&complaintAssignedDTObj.getLogCompalint().equalsIgnoreCase(complaintDtObj.getLogCompalint()))){
							   complaintAssignedDTObj.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
							      complaintAssignedDTObj.setIsActive(getIsActive());
							      complaintAssignedDTObj.setModifiedBy(getModifiedBy());
							      complaintAssignedDTObj.setModifiedDate(getModifiedDate());
							      complaintAssignedDTObj.setApprovedBy(getApprovedBy());
							      complaintAssignedDTObj.setApprovedDate(getApprovedDate());
							      complaintAssignedDTObj.setRemarks(getRemarks());
					    
					  
				}else{
					      complaintAssignedDTObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					      complaintAssignedDTObj.setIsActive(Constants.U);
					      complaintAssignedDTObj.setModifiedBy(sessionStateManage.getUserName());
					      complaintAssignedDTObj.setModifiedDate(new Date());
					      complaintAssignedDTObj.setApprovedBy(null);
					      complaintAssignedDTObj.setApprovedDate(null);
					      complaintAssignedDTObj.setRemarks(null);
					      complaintAssignedDTObj.setIfEditClicked(true);	 
				}
					      }
		    }else{
			      complaintAssignedDTObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
			      complaintAssignedDTObj.setIsActive(Constants.U);
			      complaintAssignedDTObj.setCreatedBy(sessionStateManage.getUserName());
			      complaintAssignedDTObj.setCreatedDate(new Date());
			      complaintAssignedDTObj.setIfEditClicked(true);
		    }
		    complaintAssignedDTList.add(complaintAssignedDTObj);
		    
		    if(getComplaintAssignedId()==null){
			      complaintAssignedNewDTList.add(complaintAssignedDTObj);
		    }
		    setBooRenderDataTable(true);
		    setBooSaveOrExit(true);
		    setBooApproval(false);
		    setBooReadOnly(false);
		    clearAllFields();
		  }catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::complaintAssignedToDataTable");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		    }
		    
		    
		  public void duplicateChekingComplaintAssignedCode(){
			  try{
			   // List<ComplaintAssignedDTO> lstcomplaintAssignedDTOList=complaintAssignedService.getComPlaintAssignedAllValues(getComplaintAssignedCode());
				  List<ComplaintAssignedDTO> lstcomplaintAssignedDTOList=complaintAssignedService.getComPlaintAssignedAllValues(getComplaintAssignedTo());
				if(lstcomplaintAssignedDTOList !=null && lstcomplaintAssignedDTOList.size()>0 && getIfEditClicked().equals(false)){
					  clearAllFields();
					 RequestContext.getCurrentInstance().execute("datatable.show();");
					return;
				     
				}else if (complaintAssignedDTList.size()>0){
				  for (ComplaintAssignedDataTable dataTable : complaintAssignedDTList) {
					    //if(dataTable.getComplaintAssignedCode().equalsIgnoreCase(getComplaintAssignedCode())){
					  if(dataTable.getComplaintAssignedTo().equalsIgnoreCase(getComplaintAssignedTo())){
						      clearAllFields();
						      RequestContext.getCurrentInstance().execute("datatable.show();");
						      return;
					    }
			      }
			}
			
			if(getComplaintAssignedTo() != null){
				  complaintAssignedToDataTable();
			}
			  }catch (NullPointerException ne) {
					// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setErrorMessage("Method Name::duplicateChekingComplaintAssignedCode");
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return;
				} catch (Exception exception) {
					// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}
		  }
		    
	public void saveAllComplaintAssigned(){
		 try {
		   for (ComplaintAssignedDataTable comAssignedDataTableObj : complaintAssignedDTList) {
			     if(comAssignedDataTableObj.getIfEditClicked().equals(true)){
				 count=count+1;      
			     ComplaintAssignedDTO complaintAssignedDTO=new ComplaintAssignedDTO();
			     complaintAssignedDTO.setComplaintAssignedCode(comAssignedDataTableObj.getComplaintAssignedCode());
			     complaintAssignedDTO.setApplicationCountryId(comAssignedDataTableObj.getApplicationCountryId());
			     complaintAssignedDTO.setComplaintAssignedId(comAssignedDataTableObj.getComplaintAssignedId());
			     complaintAssignedDTO.setComplaintAssignedTo(comAssignedDataTableObj.getComplaintAssignedTo());
			     complaintAssignedDTO.setComplaintEnglishFullDescription(comAssignedDataTableObj.getComplaintEnglishFullDescription());
			     complaintAssignedDTO.setComplaintEnglishShortDescription(comAssignedDataTableObj.getComplaintEnglishShortDescription());
			     complaintAssignedDTO.setComplaintArabicFullDescription(comAssignedDataTableObj.getComplaintArabicFullDescription());
			     complaintAssignedDTO.setComplaintArabicShortDescription(comAssignedDataTableObj.getComplaintArabicShortDescription());
			     complaintAssignedDTO.setLogCompalint(comAssignedDataTableObj.getLogCompalint());
			     complaintAssignedDTO.setComplaintEnglishFullDescriptionId(comAssignedDataTableObj.getComplaintEnglishFullDescriptionPK());
			     complaintAssignedDTO.setComplaintArabicFullDescriptionId(comAssignedDataTableObj.getComplaintArabicFullDescriptionPK());
			     complaintAssignedDTO.setEnglishLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			     complaintAssignedDTO.setArabicLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			     complaintAssignedDTO.setCreatedBy(comAssignedDataTableObj.getCreatedBy());
			     complaintAssignedDTO.setCreatedDate(comAssignedDataTableObj.getCreatedDate());
			     complaintAssignedDTO.setModifiedBy(comAssignedDataTableObj.getModifiedBy());
			     complaintAssignedDTO.setModifiedDate(comAssignedDataTableObj.getModifiedDate());
			     complaintAssignedDTO.setApprovedBy(comAssignedDataTableObj.getApprovedBy());
			     complaintAssignedDTO.setApprovedDate(comAssignedDataTableObj.getApprovedDate());
			     complaintAssignedDTO.setIsActive(comAssignedDataTableObj.getIsActive());
			     complaintAssignedDTO.setRemarks(comAssignedDataTableObj.getRemarks());
			     complaintAssignedService.saveAllComplaintAssigenedRecords(complaintAssignedDTO);
			     
			     	} 
			     }
		   setCountNoOfSave(new BigDecimal(count));
		   count=0;
		   RequestContext.getCurrentInstance().execute("complete.show();");
		   clearAllFields();
		  /* complaintAssignedDTList.clear();
		   complaintAssignedNewDTList.clear();
		   setBooRenderDataTable(false);
		   setBooSaveOrExit(false);*/
		   setBooAdd(true);
		   setBooApproval(false);
		   setBooReadOnly(false);
	  } catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveAllComplaintAssigned");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	
	public void clickOnOKSave(){
		  complaintAssignedDTList.clear();
		   complaintAssignedNewDTList.clear();
		 clearAllFields();
		 setBooRenderDataTable(false);
		 setBooSaveOrExit(false);
		 setBooAdd(true);
		 setBooReadOnly(false);
		 setBooApproval(false);
		 try {
			   FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintassigned.xhtml");      
	  } catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::clickOnOKSave");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	public void ComplaintAssignedNotSaved(){
		  complaintAssignedDTList.clear();
		   complaintAssignedNewDTList.clear();
		 clearAllFields();
		 setBooRenderDataTable(false);
		 setBooSaveOrExit(false);
		 setBooAdd(true);
		 setBooReadOnly(false);
		 setBooApproval(false);
		 try {
			   FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintassigned.xhtml");      
	  } catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::ComplaintAssignedNotSaved");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	//view Started
	public void viewAllComplaintAssigned(){
		 try {
		  Boolean childRecordCheck=false;
		  clearAllFields();
		  complaintAssignedDTList.clear();
		  List<ComplaintAssignedDTO> lstComplaintAssignedDTO= complaintAssignedApproveActivateAndDeActivatService.displayAllComplaintAssinedMasterToView();
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
			   List<ComplaintAssignedDTO> lstComplaintAssignedDTO1=complaintAssignedApproveActivateAndDeActivatService.displayComplaintAssinedMasterAllFieldForApproval(complaintAssignedDTO.getComplaintAssignedId());
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
		 
			    FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintassigned.xhtml");      
	  } catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::viewAllComplaintAssigned");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	//Ended for View
	  public void edit(ComplaintAssignedDataTable complaintAssignedDTObj){
		  try{
		    setComplaintDtObj(complaintAssignedDTObj);
		    setApplicationCountryId(complaintAssignedDTObj.getApplicationCountryId());
		    setComplaintAssignedCode(complaintAssignedDTObj.getComplaintAssignedCode());
		    setComplaintAssignedId(complaintAssignedDTObj.getComplaintAssignedId());
		    setComplaintAssignedId(complaintAssignedDTObj.getComplaintAssignedId());
		    setComplaintAssignedTo(complaintAssignedDTObj.getComplaintAssignedTo());
		    setComplaintEnglishFullDescription(complaintAssignedDTObj.getComplaintEnglishFullDescription());
		    setComplaintEnglishShortDescription(complaintAssignedDTObj.getComplaintEnglishShortDescription());
		    setComplaintArabicFullDescription(complaintAssignedDTObj.getComplaintArabicFullDescription());
		    setComplaintArabicShortDescription(complaintAssignedDTObj.getComplaintArabicShortDescription());
		    setLogCompalint(complaintAssignedDTObj.getLogCompalint());
		    setComplaintEnglishFullDescriptionPK(complaintAssignedDTObj.getComplaintEnglishFullDescriptionPK());
		    setComplaintArabicFullDescriptionPK(complaintAssignedDTObj.getComplaintArabicFullDescriptionPK());
		    setRenderEditButton(complaintAssignedDTObj.getRenderEditButton());
		    setCreatedBy(complaintAssignedDTObj.getCreatedBy());
		    setCreatedDate(complaintAssignedDTObj.getCreatedDate());
		    setModifiedBy(complaintAssignedDTObj.getModifiedBy());
		    setModifiedDate(complaintAssignedDTObj.getModifiedDate());
		    setApprovedBy(complaintAssignedDTObj.getApprovedBy());
		    setApprovedDate(complaintAssignedDTObj.getApprovedDate());
		    setRemarks(complaintAssignedDTObj.getRemarks());
		    setDynamicLabelForActivateDeactivate(complaintAssignedDTObj.getDynamicLabelForActivateDeactivate());
		    setIsActive(complaintAssignedDTObj.getIsActive());
		    setEnglishLanguageId(complaintAssignedDTObj.getComplaintEnglishFullDescriptionPK());
		    setArabicLanguageId(complaintAssignedDTObj.getComplaintArabicFullDescriptionPK());
		    setIfEditClicked(true);
		    if(complaintAssignedDTObj.getLogCompalintId().equalsIgnoreCase(Constants.YES)){
				  setLogCompalint(Constants.Yes);
				  setLogCompalintId(complaintAssignedDTObj.getLogCompalintId());
			}else{
				  setLogCompalint(Constants.No);
				  setLogCompalintId(complaintAssignedDTObj.getLogCompalintId());
			}
		    complaintAssignedDTList.remove(complaintAssignedDTObj);
		    complaintAssignedNewDTList.remove(complaintAssignedDTObj);
		    if(complaintAssignedDTList.size()==0){
			    setBooRenderDataTable(false);
			    setBooSaveOrExit(false);
			    setBooAdd(true);
			    setBooApproval(false);
			    setBooReadOnly(false);
		    }else{
		    setBooEditButton(true);
		    setBooSubmitPanel(true);
		    setBooClearPanel(true);
		    setBooApproval(false);
		    setBooReadOnly(false);
		    }
		  } catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::edit");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
	  }
	  
	  public void checkStatus(ComplaintAssignedDataTable complaintAssignedDTObj){
		  try{
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
		  }catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::checkStatus");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
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
		  try{
		    complaintAssignedService.deleteRecordPermentely(dataTable.getComplaintAssignedId(),dataTable.getComplaintEnglishFullDescriptionPK(),dataTable.getComplaintArabicFullDescriptionPK());
		  }catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::deleteRecordCompliantAssigned");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
	  }
	 
	  public void UpdateComplaintAssigenedCode(ComplaintAssignedDataTable complaintAssignedDTObj){
		    try {
			ComplaintAssignedDTO complaintAssignedDTO=new ComplaintAssignedDTO(); 
			complaintAssignedDTO.setApplicationCountryId(complaintAssignedDTObj.getApplicationCountryId());
			complaintAssignedDTO.setComplaintAssignedCode(complaintAssignedDTObj.getComplaintAssignedCode());
			complaintAssignedDTO.setComplaintAssignedId(complaintAssignedDTObj.getComplaintAssignedId());
			//complaintAssignedDTO.setComplaintAssignedTo(complaintAssignedDTObj.getComplaintAssignedTo());
			complaintAssignedDTO.setLogCompalint(complaintAssignedDTObj.getLogCompalint());
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
			
			complaintAssignedService.getComplientAssignedUpdateRecord(complaintAssignedDTO);
		    } catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::UpdateComplaintAssigenedCode");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
	  }
	  public void clickOkRemarks(){
		  try{
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
	  }catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::clickOkRemarks");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	  }
	  public void conformToDeActivteCompliantAssigned(ComplaintAssignedDataTable dataTable){
		  try{
		    complaintAssignedService.deActivateRecord(dataTable.getComplaintAssignedId(),sessionStateManage.getUserName());
		    viewAllComplaintAssigned();
		  }catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::conformToDeActivteCompliantAssigned");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
	  }
	  public void activateRecord(){
		  try{
		    if(complaintAssignedDTList.size()>0){
			      for (ComplaintAssignedDataTable complaintAssignedDtObj : complaintAssignedDTList) {
				if(complaintAssignedDtObj.getActivateRecordCheck()!=null){
					  if(complaintAssignedDtObj.getActivateRecordCheck().equals(true)){
						    conformToDeActivteCompliantAssigned(complaintAssignedDtObj);    
					  }
				}
			      }
		    }
		  }catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::activateRecord");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
	  }
	  public void cancelRemarks() {
			setRemarks(null);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintassigned.xhtml");
			}catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::cancelRemarks");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		}
	  public void clearAllFields(){
		    setApplicationCountryId(null);
		    setComplaintAssignedCode(null);
		    setLogCompalint(null);
		    setComplaintEnglishFullDescription(null);
		    setComplaintArabicFullDescription(null);
		    setComplaintEnglishShortDescription(null);
		    setComplaintArabicShortDescription(null);
		    setComplaintAssignedTo(null);
		    setComplaintAssignedId(null);
		    setLogCompalint(null);
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
			} catch (NullPointerException ne) {
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
		    try {
		    fetchRecordforComplaintAssignedApproval();
		   
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/ComplaintAssignedApprovel.xhtml");      
		    } catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::complaintAssignedApprovalPageNavigation");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
	  }
	  
	  public void fetchRecordforComplaintAssignedApproval(){
		    Boolean childRecordCheck=false;
		    complaintAssignedDTList.clear();
		    complaintAssignedNewDTList.clear();
		    try{
		    List<ComplaintAssignedDTO> lstComplaintAssignedDTO= complaintAssignedService.getfetchRecordForApproval();
			  if(lstComplaintAssignedDTO.size()>0){
			  for (ComplaintAssignedDTO complaintAssignedDTO : lstComplaintAssignedDTO) {
				   ComplaintAssignedDataTable complaintAssignedObj=new ComplaintAssignedDataTable();
				   complaintAssignedObj.setApplicationCountryId(complaintAssignedDTO.getApplicationCountryId());
				   complaintAssignedObj.setComplaintAssignedCode(complaintAssignedDTO.getComplaintAssignedCode());
				   complaintAssignedObj.setComplaintAssignedId(complaintAssignedDTO.getComplaintAssignedId());
				   //complaintAssignedObj.setComplaintAssignedTo(complaintAssignedDTO.getComplaintAssignedTo());
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
				   if(complaintAssignedObj.getIsActive().equalsIgnoreCase(Constants.Yes)){
					     complaintAssignedObj.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				   }else if (complaintAssignedObj.getIsActive().equalsIgnoreCase(Constants.D)) {
					     complaintAssignedObj.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
			    }else if (complaintAssignedObj.getIsActive().equalsIgnoreCase(Constants.U)&&complaintAssignedObj.getModifiedBy()==null&&complaintAssignedObj.getModifiedDate()==null&&complaintAssignedObj.getApprovedBy()==null&&complaintAssignedObj.getApprovedDate()==null&&complaintAssignedObj.getRemarks()==null) {
				      complaintAssignedObj.setDynamicLabelForActivateDeactivate(Constants.DELETE);
			    }else{
				      complaintAssignedObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);      
			    }
				   List<ComplaintAssignedDTO> lstComplaintAssignedDTO1=complaintAssignedService.getAllRecordsRelatedComplaintAssignedApproval(complaintAssignedDTO.getComplaintAssignedId());
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
		    }catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::fetchRecordforComplaintAssignedApproval");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
	  }
	  
	  public void approvelCheckForComplientAssignedUser(ComplaintAssignedDataTable dataTable){
		    if(!(dataTable.getModifiedBy()==null?  dataTable.getCreatedBy():dataTable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName()))
			{try {
			  setComplaintAssignedCode(dataTable.getComplaintAssignedCode());
			  setComplaintAssignedId(dataTable.getComplaintAssignedId());
			  //setComplaintAssignedTo(dataTable.getComplaintAssignedTo());
			  setLogCompalint(dataTable.getLogCompalint());
			  setCreatedBy(dataTable.getCreatedBy());
			  setCreatedDate(dataTable.getCreatedDate());
			  setModifiedBy(dataTable.getModifiedBy());
			  setModifiedDate(dataTable.getModifiedDate());
			  setIsActive(dataTable.getIsActive());
			  setComplaintEnglishFullDescription(dataTable.getComplaintEnglishShortDescription());
			  setComplaintEnglishShortDescription(dataTable.getComplaintEnglishShortDescription());
			  setComplaintArabicFullDescription(dataTable.getComplaintArabicFullDescription());
			  setComplaintArabicShortDescription(dataTable.getComplaintArabicShortDescription());
			  setBooRenderDataTable(false);
			  setBooSaveOrExit(false);
			  setBooAdd(false);
			  setBooApproval(true);
			  setBooReadOnly(true);
			  
				    FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintassigned.xhtml"); 	
				} catch (NullPointerException ne) {
					// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setErrorMessage("Method Name::approvelCheckForComplientAssignedUser");
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return;
				} catch (Exception exception) {
					// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}
			}else{
			RequestContext.getCurrentInstance().execute("notApproved.show();");
			}
	  }
		/*public void complaintAssignedApproveRecord(){
			  String approvalMsg=complaintAssignedService.getApproveRecord(getComplaintAssignedId(),sessionStateManage.getUserName());
			  if(approvalMsg.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("approve.show();");
				 }else{
					 RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
					return; 
				 }
		}*/
		/*public void clickOnOKApprove(){
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
		}*/
		/*public void clickOnOkButton(){
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
		}*/
	  /*Approval Ended */
	  /*  Method declaration End*/
	  
		 /* ajax Calling start*/
	  
	  public void itemSelectPopulate(){
		setComplaintAssignedId(null);
		setComplaintEnglishFullDescription(null);
		setComplaintEnglishShortDescription(null);
		setComplaintArabicFullDescription(null);
		setComplaintArabicShortDescription(null);
		setApplicationCountryId(null);
		setLogCompalint(null);
		setLogCompalintId(null);
		//setComplaintAssignedTo(null);
		try{
		//List<ComplaintAssignedDTO> lstcomplaintAssignedDTOList=complaintAssignedService.getComPlaintAssignedAllValues(getComplaintAssignedCode());
			List<ComplaintAssignedDTO> lstcomplaintAssignedDTOList=complaintAssignedService.getComPlaintAssignedAllValues(getComplaintAssignedTo());
		if(lstcomplaintAssignedDTOList != null && lstcomplaintAssignedDTOList.size()>0 && getIfEditClicked().equals(false)){
			  clearAllFields();
			 RequestContext.getCurrentInstance().execute("datatable.show();");
			return;
		     
		}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::itemSelectPopulate");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		/*List<ComplaintAssignedDTO> lstcomplaintAssignedDTOList=complaintAssignedService.getComPlaintAssignedAllValues(getComplaintAssignedCode());
		if(lstcomplaintAssignedDTOList.size()>0){
			  if(lstcomplaintAssignedDTOList.get(0).getIsActive().equalsIgnoreCase(Constants.Yes)){
				    setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
			  }else if (lstcomplaintAssignedDTOList.get(0).getIsActive().equalsIgnoreCase(Constants.D)) {
				    setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
		    }else if (lstcomplaintAssignedDTOList.get(0).getIsActive().equalsIgnoreCase(Constants.U)&&lstcomplaintAssignedDTOList.get(0).getModifiedBy()==null&&lstcomplaintAssignedDTOList.get(0).getModifiedDate()==null&&lstcomplaintAssignedDTOList.get(0).getApprovedBy()==null&&lstcomplaintAssignedDTOList.get(0).getApprovedDate()==null&&lstcomplaintAssignedDTOList.get(0).getRemarks()==null) {
			      setDynamicLabelForActivateDeactivate(Constants.DELETE);
			}else{
				setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
			}
			  setComplaintAssignedCode(lstcomplaintAssignedDTOList.get(0).getComplaintAssignedCode());
			  setComplaintAssignedId(lstcomplaintAssignedDTOList.get(0).getComplaintAssignedId());
			  setComplaintAssignedTo(lstcomplaintAssignedDTOList.get(0).getComplaintAssignedTo());
			 // setLogCompalint(lstcomplaintAssignedDTOList.get(0).getLogCompalint());
			  if(lstcomplaintAssignedDTOList.get(0).getLogCompalint().equalsIgnoreCase(Constants.Yes)){
					  setLogCompalintId(Constants.Yes);
					  setLogCompalint(Constants.Yes);
				}else{
					  setLogCompalintId(Constants.No); 
					  setLogCompalint(Constants.No);
				}
			  setCreatedBy(lstcomplaintAssignedDTOList.get(0).getCreatedBy());
			  setCreatedDate(lstcomplaintAssignedDTOList.get(0).getCreatedDate());
			  setModifiedBy(lstcomplaintAssignedDTOList.get(0).getModifiedBy());
			  setModifiedDate(lstcomplaintAssignedDTOList.get(0).getModifiedDate());
			  setApprovedBy(lstcomplaintAssignedDTOList.get(0).getApprovedBy());
			  setApprovedDate(lstcomplaintAssignedDTOList.get(0).getApprovedDate());
			  setRemarks(lstcomplaintAssignedDTOList.get(0).getRemarks());
			  setIsActive(lstcomplaintAssignedDTOList.get(0).getIsActive());
			  
			  List<ComplaintAssignedDTO> complaintAssignedDTO1=complaintAssignedService.getcomplaintAssignedCodeBasedOnComplaintDescription(getComplaintAssignedId());
			  if(complaintAssignedDTO1.size()>0){
				    for (ComplaintAssignedDTO complaintAssignedDTO : complaintAssignedDTO1) {
					      if(complaintAssignedDTO.getLanguageId().intValue()==1){
						setComplaintEnglishFullDescription(complaintAssignedDTO.getFullDescription());
						setComplaintEnglishShortDescription(complaintAssignedDTO.getShortDescription());
						setComplaintEnglishFullDescriptionPK(complaintAssignedDTO.getComplaintAssignedDescId());
						setEnglishLanguageId(complaintAssignedDTO.getLanguageId());
					      }else{
						setComplaintArabicFullDescription(complaintAssignedDTO.getFullDescription());
						setComplaintArabicShortDescription(complaintAssignedDTO.getShortDescription());
						setComplaintArabicFullDescriptionPK(complaintAssignedDTO.getComplaintAssignedDescId());
						setArabicLanguageId(complaintAssignedDTO.getLanguageId());
					      }
					
			      }
			  }
		}*/
	  }
	  
	  public void disableSubmit(){
		   /* List<ComplaintAssignedDTO> lstcomplaintAssignedDTOLT=complaintAssignedService.getComPlaintAssignedIdDuplicateChecking(getComplaintAssignedTo());
		    if(lstcomplaintAssignedDTOLT.size()>0 && getIfEditClicked().equals(false)){
				  setComplaintAssignedTo(null);
				 RequestContext.getCurrentInstance().execute("assignedToAlreadyExist.show();");
				return;
		    
	  }*/
		    setBooSubmitPanel(true);
	  }
	 /*ajax Calling End*/
}
