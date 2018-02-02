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

import com.amg.exchange.common.dto.ComplaintActionDTO;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.complaint.model.ComplaintAction;
import com.amg.exchange.complaint.model.ComplaintActionDesc;
import com.amg.exchange.complaint.service.IComplaintActionService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("complaintActionBean")
@Scope("session")
public class ComplaintActionBean<T> implements Serializable {
 
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(ComplaintActionBean.class);
	private SessionStateManage session=new SessionStateManage();
	
	/*start of componend decalration*/
	private String complaintActionCode;
	private String complaintActionFullDescription;
	private String complaintActionShortDescription;
	private String complaintActionArabicShortDescription;
	private String complaintActionArabicFullDescription;
	private String actionGroup;
	private String actionGroupId;
	private String remarks;
	private String approvedBy;
 	private Date approvedDate;
 	private Boolean readOnly;
  
 	private String errorMessage;
 	
   	private List<ComplaintActionDataTable> compliantActionDTList=new CopyOnWriteArrayList<ComplaintActionDataTable>();
 	
		
   	
 
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getComplaintActionCode() {
		return complaintActionCode;
	}

	public void setComplaintActionCode(String complaintActionCode) {
		this.complaintActionCode = complaintActionCode;
	}

	public String getComplaintActionFullDescription() {
		return complaintActionFullDescription;
	}

	public void setComplaintActionFullDescription(
			String complaintActionFullDescription) {
		this.complaintActionFullDescription = complaintActionFullDescription;
	}

	public String getComplaintActionShortDescription() {
		return complaintActionShortDescription;
	}

	public void setComplaintActionShortDescription(
			String complaintActionShortDescription) {
		this.complaintActionShortDescription = complaintActionShortDescription;
	}

	public String getActionGroup() {
		return actionGroup;
	}

	public void setActionGroup(String actionGroup) {
		this.actionGroup = actionGroup;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public List<ComplaintActionDataTable> getCompliantActionDTList() {
		return compliantActionDTList;
	}

	public void setCompliantActionDTList(List<ComplaintActionDataTable> compliantActionDTList) {
		this.compliantActionDTList = compliantActionDTList;
	}
	public String getComplaintActionArabicShortDescription() {
		return complaintActionArabicShortDescription;
	}

	public void setComplaintActionArabicShortDescription(
			String complaintActionArabicShortDescription) {
		this.complaintActionArabicShortDescription = complaintActionArabicShortDescription;
	}

	public String getComplaintActionArabicFullDescription() {
		return complaintActionArabicFullDescription;
	}

	public void setComplaintActionArabicFullDescription(
			String complaintActionArabicFullDescription) {
		this.complaintActionArabicFullDescription = complaintActionArabicFullDescription;
	}

	/* end of componend decalration*/

	 
	
	/*   AutoWire declaration Start   */

	@Autowired 
	IComplaintActionService iComplaintActionService;
	
	/*   AutoWire declaration Start   */
	
	private BigDecimal complaintActionMasterPk;
	private BigDecimal complaintActionDescEngPk;
	private BigDecimal complaintActionDescArabicPk;
	private BigDecimal englishLanguageId;
	private BigDecimal arabicLanguageId;
	private BigDecimal applicationCountryId;
	private String createdBy;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	private String isActive;
	private Boolean  disableClearButton;
	private Boolean disableEditButton;
	private Boolean disableSubmitButton;
	private Boolean ifEditClicked=false;
	private String dynamicLabelForActivateDeactivate;
	private String  complaintActionCodeForDailog;
	private Boolean renderSaveButton=true;
	private Boolean renderApprov=false;
	private Boolean renderSubmitPanel=false;
	private int count=0;
	private BigDecimal countNoOfSave;
	private Boolean disableActionGroup;
	
	public Boolean getRenderSaveButton() {
		return renderSaveButton;
		
	}

	public void setRenderSaveButton(Boolean renderSaveButton) {
		this.renderSaveButton = renderSaveButton;
	}

	public Boolean getRenderDatatable() {
		return renderDatatable;
	}

	public void setRenderDatatable(Boolean renderDatatable) {
		this.renderDatatable = renderDatatable;
	}

	private Boolean renderDatatable=false;
	private ComplaintActionDataTable complaintActionDatatableObj=null;
	private List<ComplaintActionDataTable> compliantActionViewList=new CopyOnWriteArrayList<ComplaintActionDataTable>();
	 

	
	
	public BigDecimal getComplaintActionMasterPk() {
		return complaintActionMasterPk;
	}

	public void setComplaintActionMasterPk(BigDecimal complaintActionMasterPk) {
		this.complaintActionMasterPk = complaintActionMasterPk;
	}

	public BigDecimal getComplaintActionDescEngPk() {
		return complaintActionDescEngPk;
	}

	public void setComplaintActionDescEngPk(BigDecimal complaintActionDescEngPk) {
		this.complaintActionDescEngPk = complaintActionDescEngPk;
	}

	public BigDecimal getComplaintActionDescArabicPk() {
		return complaintActionDescArabicPk;
	}

	public void setComplaintActionDescArabicPk(
			BigDecimal complaintActionDescArabicPk) {
		this.complaintActionDescArabicPk = complaintActionDescArabicPk;
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

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	 
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public List<ComplaintActionDataTable> getCompliantActionViewList() {
		return compliantActionViewList;
	}

	public void setCompliantActionViewList(List<ComplaintActionDataTable> compliantActionViewList) {
		this.compliantActionViewList = compliantActionViewList;
	}
	public ComplaintActionDataTable getComplaintActionDatatableObj() {
		return complaintActionDatatableObj;
	}

	public void setComplaintActionDatatableObj(
			ComplaintActionDataTable complaintActionDatatableObj) {
		this.complaintActionDatatableObj = complaintActionDatatableObj;
	}
	public Boolean getIfEditClicked() {
		return ifEditClicked;
	}

	public void setIfEditClicked(Boolean ifEditClicked) {
		this.ifEditClicked = ifEditClicked;
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

	public Boolean getDisableClearButton() {
		return disableClearButton;
	}

	public void setDisableClearButton(Boolean disableClearButton) {
		this.disableClearButton = disableClearButton;
	}

	public Boolean getDisableEditButton() {
		return disableEditButton;
	}

	public void setDisableEditButton(Boolean disableEditButton) {
		this.disableEditButton = disableEditButton;
	}

	public Boolean getDisableSubmitButton() {
		return disableSubmitButton;
	}

	public void setDisableSubmitButton(Boolean disableSubmitButton) {
		this.disableSubmitButton = disableSubmitButton;
	}

	public String getComplaintActionCodeForDailog() {
		return complaintActionCodeForDailog;
	}

	public void setComplaintActionCodeForDailog(
			String complaintActionCodeForDailog) {
		this.complaintActionCodeForDailog = complaintActionCodeForDailog;
	}
	
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public BigDecimal getCountNoOfSave() {
		return countNoOfSave;
	}

	public void setCountNoOfSave(BigDecimal countNoOfSave) {
		this.countNoOfSave = countNoOfSave;
	}
	public Boolean getRenderApprov() {
		return renderApprov;
	}

	public void setRenderApprov(Boolean renderApprov) {
		this.renderApprov = renderApprov;
	}

	public Boolean getRenderSubmitPanel() {
		return renderSubmitPanel;
	}

	public void setRenderSubmitPanel(Boolean renderSubmitPanel) {
		this.renderSubmitPanel = renderSubmitPanel;
	}

	public String getActionGroupId() {
		return actionGroupId;
	}

	public void setActionGroupId(String actionGroupId) {
		this.actionGroupId = actionGroupId;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Boolean getDisableActionGroup() {
		return disableActionGroup;
	}

	public void setDisableActionGroup(Boolean disableActionGroup) {
		this.disableActionGroup = disableActionGroup;
	}

	
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	//page navigation method when click the menu link
	public void  complaintActionPageNavigation() {
		setRenderDatatable(false);
		setRenderApprov(false);
		setRenderSaveButton(true);
		setRenderSubmitPanel(false);
		setDisableClearButton(false);
		setDisableEditButton(false);
		setReadOnly(false);
		setDisableActionGroup(false);
		clearAllFields();
		compliantActionDTList.clear();
		compliantActionViewList.clear();
		
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "complaintaction.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/complaintaction.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::complaintActionPageNavigation");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}
	//exit button code
	public void exit() {
		
		try {
			if (session.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../registration/branchhome.xhtml");
			}
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
 
	public void clearAllFields() {
		 
		setComplaintActionCode(null);
		setComplaintActionFullDescription(null);
		setComplaintActionShortDescription(null);
		setComplaintActionArabicFullDescription(null);
		setComplaintActionArabicShortDescription(null);
		setActionGroup(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setRemarks(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setIsActive(null);
		setComplaintActionMasterPk( null);
		setComplaintActionDescEngPk( null);
		setComplaintActionDescArabicPk(null);
	}
	//TO CLEAR ALL DATATABLE AND ALL FIELDS
	public void clearButton() {
		
		clearAllFields();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintaction.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::clearButton");
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
				return iComplaintActionService.getAutoComplete(query);
			}catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::clearButton");
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
	//TO EDIT THE FROM DATATABLE
	public void editRecord(ComplaintActionDataTable complaintActionDTObj){
		try{
		setDisableSubmitButton(true);
		setDisableClearButton(true);
		setDisableEditButton(true);
		setRenderSaveButton(true);
		setRenderSubmitPanel(true);
		setRenderDatatable(true);
		setComplaintActionDatatableObj(complaintActionDTObj);
		setComplaintActionMasterPk(complaintActionDTObj.getComplaintActionPk());
		setComplaintActionDescEngPk(complaintActionDTObj.getComplaintActionDescEnglishPk());
		setComplaintActionDescArabicPk(complaintActionDTObj.getComplaintActionDescArabicPk());
		if(complaintActionDTObj.getActionGroup().equalsIgnoreCase(Constants.O)){
			setActionGroup(complaintActionDTObj.getActionGroup());
			setActionGroupId(Constants.OPEN);
		}else if(complaintActionDTObj.getActionGroup().equalsIgnoreCase(Constants.C)){
			setActionGroup(complaintActionDTObj.getActionGroup());
			setActionGroupId(Constants.CLOSE);
		}
		else if(complaintActionDTObj.getActionGroup().equalsIgnoreCase(Constants.P)){
			setActionGroup(complaintActionDTObj.getActionGroup());
			setActionGroupId(Constants.PENDING);
		}
		setComplaintActionCode(complaintActionDTObj.getComplaintActionCode());
		setComplaintActionFullDescription(complaintActionDTObj.getComplaintActionFullDescription());
		setComplaintActionShortDescription(complaintActionDTObj.getComplaintActionShortDescription());
		setComplaintActionArabicFullDescription(complaintActionDTObj.getComplaintActionArabicFullDescription());
		setComplaintActionArabicShortDescription(complaintActionDTObj.getComplaintActionArabicShortDescription());
		setApplicationCountryId(complaintActionDTObj.getApplicationCountryId());
		setCreatedBy(complaintActionDTObj.getCreatedBy());
		setCreatedDate(complaintActionDTObj.getCreatedDate());
		setDynamicLabelForActivateDeactivate(complaintActionDTObj.getDynamicLabelForActivateDeactivate());
		compliantActionDTList.remove(complaintActionDTObj);
		if(complaintActionDTObj.getComplaintActionDescEnglishPk()!=null){
			setModifiedBy(session.getUserName());
			setModifiedDate(new Date());
			setIsActive(Constants.U);
			setApprovedBy(null);
			setApprovedDate(null);
			setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
		}else{
			setModifiedBy(complaintActionDTObj.getModifiedBy());
			setModifiedDate(complaintActionDTObj.getModifiedDate());
			setIsActive(complaintActionDTObj.getIsActive());
			setApprovedBy(complaintActionDTObj.getApprovedBy());
			setApprovedDate(complaintActionDTObj.getApprovedDate());
			setDynamicLabelForActivateDeactivate(complaintActionDTObj.getDynamicLabelForActivateDeactivate());
			 
		}
			
		setRemarks(complaintActionDTObj.getRemarks());
		setIfEditClicked(true);
		compliantActionDTList.remove(complaintActionDTObj);
		compliantActionViewList.remove(complaintActionDTObj);
		if(compliantActionDTList.size()==0){
			setRenderSaveButton(true);
			setRenderDatatable(false);
			setRenderSubmitPanel(false);
		}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::editRecord");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
			
	} 
	
	
	public void addRecords(){
		try{
		setDisableSubmitButton(false);
		setDisableClearButton(false);
		setDisableEditButton(false);
		ComplaintActionDataTable complaintActionDtObj=new ComplaintActionDataTable();
		complaintActionDtObj.setComplaintActionCode(getComplaintActionCode());
		complaintActionDtObj.setComplaintActionArabicFullDescription(getComplaintActionArabicFullDescription());
		complaintActionDtObj.setComplaintActionArabicShortDescription(getComplaintActionArabicShortDescription() );
		complaintActionDtObj.setComplaintActionFullDescription(getComplaintActionFullDescription());
		complaintActionDtObj.setComplaintActionShortDescription(getComplaintActionShortDescription());
		 
		if(getActionGroup().equalsIgnoreCase(Constants.O)){
			complaintActionDtObj.setActionGroup(getActionGroup() );
			complaintActionDtObj.setActionGroupId(Constants.OPEN);
			 
		}
		else if(getActionGroup().equalsIgnoreCase(Constants.C)){
			complaintActionDtObj.setActionGroup(getActionGroup() );
			complaintActionDtObj.setActionGroupId(Constants.CLOSE);
		}else if(getActionGroup().equalsIgnoreCase(Constants.P)){
		 
			complaintActionDtObj.setActionGroup(getActionGroup());
			complaintActionDtObj.setActionGroupId(Constants.PENDING);
		}
		
		complaintActionDtObj.setComplaintActionPk( getComplaintActionMasterPk());
		complaintActionDtObj.setComplaintActionDescArabicPk(getComplaintActionDescArabicPk());
		complaintActionDtObj.setComplaintActionDescEnglishPk(getComplaintActionDescEngPk());
		complaintActionDtObj.setArabicLanguageId(getArabicLanguageId());
		complaintActionDtObj.setEnglishLanguageId(getEnglishLanguageId());
	 
		if(getComplaintActionMasterPk()!=null){
			complaintActionDtObj.setApplicationCountryId(getApplicationCountryId() );
			complaintActionDtObj.setCreatedBy(getCreatedBy());
			complaintActionDtObj.setCreatedDate(getCreatedDate());
			complaintActionDtObj.setModifiedBy(session.getUserName());
			complaintActionDtObj.setModifiedDate(new Date());
			complaintActionDtObj.setApprovedBy(null);
			complaintActionDtObj.setApprovedDate(null);
			complaintActionDtObj.setIsActive(Constants.U);
			complaintActionDtObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
			complaintActionDtObj.setRemarks(null);
			setDisableEditButton(false);
			complaintActionDtObj.setIfClickEdit(getIfEditClicked());
		}else{
			complaintActionDtObj.setApplicationCountryId(session.getCountryId());
			complaintActionDtObj.setCreatedBy(session.getUserName());
			complaintActionDtObj.setCreatedDate(new Date());
			complaintActionDtObj.setIsActive(Constants.U);
			complaintActionDtObj.setIfClickEdit(true);
			setDisableEditButton(false);
			complaintActionDtObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
			compliantActionViewList.add(complaintActionDtObj);
			
		}
		compliantActionDTList.add(complaintActionDtObj);
		clearAllFields();
		setRenderDatatable(true);
		setRenderSubmitPanel(true);
		setDisableSubmitButton(false);
	}catch (NullPointerException ne) {
		// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		setErrorMessage("Method Name::addRecords");
		RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		return;
	} catch (Exception exception) {
		// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		setErrorMessage(exception.getMessage());
		RequestContext.getCurrentInstance().execute("error.show();");
		return;
	}
	}
	
	
	
	
	 
	public void addRecordsToDataTable() {
		try{
		List<ComplaintActionDTO>  complaintActionDuplicateCheckList=iComplaintActionService.getComplaintActionRecord(getComplaintActionCode());
		if(complaintActionDuplicateCheckList.size()>0 && getIfEditClicked().equals(false)){
			setComplaintActionCodeForDailog( getComplaintActionCode());
			setComplaintActionCode(null);
			clearAllFields();
			RequestContext.getCurrentInstance().execute("recordAlreadyExist.show();");
			setDisableSubmitButton(false);
			setDisableClearButton(false);
			setDisableEditButton(false);
			return;
		}else if(compliantActionDTList.size()!=0){
			for (ComplaintActionDataTable  complaintActionDTObj:compliantActionDTList) {
				if(complaintActionDTObj.getComplaintActionCode().equalsIgnoreCase(getComplaintActionCode())){
					setComplaintActionCodeForDailog( getComplaintActionCode());
					setComplaintActionCode(null);
					clearAllFields();
					RequestContext.getCurrentInstance().execute("recordAlreadyExist.show();");
					setDisableSubmitButton(false);
					setDisableClearButton(false);
					setDisableEditButton(false);
					return;
				} 
			}
		}	
		addRecords();
		 
		}catch (NullPointerException ne) {
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
	}
	
	public void saveRecords(){

		try{
		 log.info("STARING TIME  :"+System.currentTimeMillis());
		for(ComplaintActionDataTable  complaintActionDTObj:compliantActionDTList){
			if(complaintActionDTObj.getIfClickEdit().equals(true)){
				count=count+1;
			 ComplaintAction complaintAction=new ComplaintAction();
			 complaintAction.setComplaintActionId(complaintActionDTObj.getComplaintActionPk());
			 complaintAction.setComplaintActionCode(complaintActionDTObj.getComplaintActionCode());
			 complaintAction.setCreatedBy(complaintActionDTObj.getCreatedBy());
			 complaintAction.setCreatedDate(complaintActionDTObj.getCreatedDate());
			 complaintAction.setApprovedBy(complaintActionDTObj.getApprovedBy());
			 complaintAction.setApprovedDate(complaintActionDTObj.getApprovedDate());
			 complaintAction.setModifiedBy(complaintActionDTObj.getModifiedBy());
			 complaintAction.setModifiedDate(complaintActionDTObj.getModifiedDate());
			 complaintAction.setComplaintActionId(complaintActionDTObj.getComplaintActionPk());
			 if( Constants.OPEN.equalsIgnoreCase(complaintActionDTObj.getActionGroup())){
				 complaintAction.setActionGroup(complaintActionDTObj.getActionGroupId() );
				}
				else if(Constants.CLOSE.equalsIgnoreCase(getActionGroup())){

					 complaintAction.setActionGroup(complaintActionDTObj.getActionGroupId() );
					
				}else if(Constants.PENDING.equalsIgnoreCase(getActionGroup())){
					
					 complaintAction.setActionGroup(complaintActionDTObj.getActionGroupId() );
				}
			 
			 complaintAction.setActionGroup(complaintActionDTObj.getActionGroup());
			 complaintAction.setIsActive(complaintActionDTObj.getIsActive());
			 CountryMaster country=new CountryMaster();
			 country.setCountryId(complaintActionDTObj.getApplicationCountryId());
			 complaintAction.setApplicationCountryId(country);
			 
			 ComplaintActionDesc complaintActionDescObj1=new ComplaintActionDesc();
			 complaintActionDescObj1.setComplaintActionDescId(complaintActionDTObj.getComplaintActionDescEnglishPk());
			 complaintActionDescObj1.setFullDescription(complaintActionDTObj.getComplaintActionFullDescription());
			 complaintActionDescObj1.setShortDescription(complaintActionDTObj.getComplaintActionShortDescription());
			 complaintActionDescObj1.setComplaintAction(complaintAction);

			 LanguageType engLanguage=new LanguageType();
			 engLanguage.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID) );
			 complaintActionDescObj1.setLanguageId(engLanguage);
			 
			 ComplaintActionDesc complaintActionDescObj2=new ComplaintActionDesc();
			 complaintActionDescObj2.setComplaintActionDescId(complaintActionDTObj.getComplaintActionDescArabicPk());
			 complaintActionDescObj2.setFullDescription(complaintActionDTObj.getComplaintActionArabicFullDescription());
			 complaintActionDescObj2.setShortDescription(complaintActionDTObj.getComplaintActionArabicShortDescription());
			 complaintActionDescObj2.setComplaintAction(complaintAction);

			 LanguageType arabicLanguage=new LanguageType();
			 arabicLanguage.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			 complaintActionDescObj2.setLanguageId(arabicLanguage);
			 
						 
			iComplaintActionService.saveRecords(complaintAction, complaintActionDescObj1, complaintActionDescObj2);
			}
		}
		 log.info("END  TIME  :"+System.currentTimeMillis());
		setCountNoOfSave(new BigDecimal(count));
		count=0;
		RequestContext.getCurrentInstance().execute("complete.show();");
		clearAllFields();
		compliantActionDTList.clear();
		compliantActionViewList.clear();
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveRecords");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		
	}
	
	public void checkStatusType(ComplaintActionDataTable complaintActionDtObj){
		try{
		if(complaintActionDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
			
			RequestContext.getCurrentInstance().execute("pending.show();");
			return;
		}else  if(complaintActionDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
			compliantActionDTList.remove(complaintActionDtObj);
			compliantActionViewList.remove(complaintActionDtObj);
			}else if(complaintActionDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)){
				complaintActionDtObj.setRemarksCheck(true);
				setApprovedBy(complaintActionDtObj.getApprovedBy());
				setApprovedDate(complaintActionDtObj.getApprovedDate());
				RequestContext.getCurrentInstance().execute("remarks.show();");
				return;
			}else if(complaintActionDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)){
				complaintActionDtObj.setActivateRecordCheck(true);
				RequestContext.getCurrentInstance().execute("activateRecord.show();");
				return;
			}else if(complaintActionDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)){
				if(complaintActionDtObj.getModifiedBy()==null && complaintActionDtObj.getModifiedDate()==null
					&& complaintActionDtObj.getApprovedBy()==null && complaintActionDtObj.getApprovedDate()==null 
					&& complaintActionDtObj.getRemarks()==null){
					complaintActionDtObj.setPermanentDeleteCheck(true);
					RequestContext.getCurrentInstance().execute("permanentDelete.show();");
					return;
				}
			}
				if(compliantActionDTList.size()==0){
					setRenderSaveButton(false);
					setRenderDatatable(false);
				}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::checkStatusType");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		}
	
	
	
	public void confirmPermanentDelete(){
		try{
		if(compliantActionDTList.size()>0){
		for(ComplaintActionDataTable  complaintActionDTOBj:compliantActionDTList){
			if(complaintActionDTOBj.getPermanentDeleteCheck()!=null){
			if(complaintActionDTOBj.getPermanentDeleteCheck().equals(true)){
				deleteRecordPermanently(complaintActionDTOBj);
				compliantActionDTList.remove(complaintActionDTOBj);
			}
			}
	}
		}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::confirmPermanentDelete");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	
	public void deleteRecordPermanently(ComplaintActionDataTable complaintDtObj){
		try{
		iComplaintActionService.deleteRecordPermanently(complaintDtObj.getComplaintActionPk() ,complaintDtObj.getComplaintActionDescEnglishPk(),complaintDtObj.getComplaintActionDescArabicPk()) ;
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::deleteRecordPermanently");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		}
	
	
	public void remarkSelectedRecord(){
		try{
		for(ComplaintActionDataTable complaintActionDTOBj:compliantActionDTList){
			if(complaintActionDTOBj.getRemarksCheck()!=null){
			if(complaintActionDTOBj.getRemarksCheck().equals(true)){
				if(getRemarks()!=null){
					complaintActionDTOBj.setRemarks(getRemarks());
				updateRecord(complaintActionDTOBj);
				viewAll();
				clearAllFields();
				}else{
					RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
				}
			}
			}
		}
	}catch (NullPointerException ne) {
		// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		setErrorMessage("Method Name::remarkSelectedRecord");
		RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		return;
	} catch (Exception exception) {
		// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		setErrorMessage(exception.getMessage());
		RequestContext.getCurrentInstance().execute("error.show();");
		return;
	}
		}
	
	
	
	public void updateRecord(ComplaintActionDataTable complaintActionDTOBj){
		try{
		ComplaintAction complaintActionObj=new ComplaintAction();
		complaintActionObj.setComplaintActionCode(complaintActionDTOBj.getComplaintActionCode());
		complaintActionObj.setCreatedBy(complaintActionDTOBj.getCreatedBy());
		complaintActionObj.setCreatedDate(complaintActionDTOBj.getCreatedDate());
		complaintActionObj.setActionGroup(complaintActionDTOBj.getActionGroup());
		complaintActionObj.setRemarks( complaintActionDTOBj.getRemarks());
		complaintActionObj.setIsActive(Constants.D);
		complaintActionObj.setModifiedBy(session.getUserName());
		complaintActionObj.setModifiedDate(new Date());
		complaintActionObj.setApprovedBy(null);
		complaintActionObj.setApprovedDate( null);
		complaintActionObj.setComplaintActionId(complaintActionDTOBj.getComplaintActionPk());
		 
		CountryMaster countryMaster=new CountryMaster();
		countryMaster.setCountryId(complaintActionDTOBj.getApplicationCountryId());
		complaintActionObj.setApplicationCountryId(countryMaster);
		 
		ComplaintActionDesc complaintActionDescObj1=new ComplaintActionDesc();
		 complaintActionDescObj1.setComplaintActionDescId(complaintActionDTOBj.getComplaintActionDescEnglishPk());
		 complaintActionDescObj1.setFullDescription(complaintActionDTOBj.getComplaintActionFullDescription());
		 complaintActionDescObj1.setShortDescription(complaintActionDTOBj.getComplaintActionShortDescription());
		 complaintActionDescObj1.setComplaintAction(complaintActionObj);

		 LanguageType engLanguage=new LanguageType();
		 engLanguage.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID) );
		 complaintActionDescObj1.setLanguageId(engLanguage);
		 
		 ComplaintActionDesc complaintActionDescObj2=new ComplaintActionDesc();
		 complaintActionDescObj2.setComplaintActionDescId(complaintActionDTOBj.getComplaintActionDescArabicPk());
		 complaintActionDescObj2.setFullDescription(complaintActionDTOBj.getComplaintActionArabicFullDescription());
		 complaintActionDescObj2.setShortDescription(complaintActionDTOBj.getComplaintActionArabicShortDescription());
		 complaintActionDescObj2.setComplaintAction(complaintActionObj);

		 LanguageType arabicLanguage=new LanguageType();
		 arabicLanguage.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
		 complaintActionDescObj2.setLanguageId(arabicLanguage);
		 
		 iComplaintActionService.saveRecords(complaintActionObj , complaintActionDescObj1, complaintActionDescObj2);
		
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::updateRecord");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		
		
	}
	
	 
	
	public void viewAll(){
		try{
	Boolean childRecordCheck=false;
	setDisableClearButton(false);
	setDisableSubmitButton(false);
	setDisableEditButton(false);
	clearButton();
	compliantActionDTList.clear();
	List<ComplaintActionDTO> listDTO=iComplaintActionService.getAllRecords();
	if(listDTO.size()>0){
		setRenderSaveButton(true);
		setRenderDatatable(true);
		setRenderSubmitPanel(true);
	}else{
	 
		
		setRenderSaveButton(true);
		setRenderDatatable(false);
		setRenderSubmitPanel(false);
		RequestContext.getCurrentInstance().execute("noRecord.show();");
		return;
	}
		
	for(ComplaintActionDTO complaintDTO:listDTO){
			
			ComplaintActionDataTable complaintDataTable=new ComplaintActionDataTable();
			
			complaintDataTable.setComplaintActionCode(complaintDTO.getComplaintActionCode());
			complaintDataTable.setComplaintActionPk(complaintDTO.getComplaintActionId());
			 
			complaintDataTable.setRemarks(complaintDTO.getRemarks() );
			complaintDataTable.setIsActive(complaintDTO.getIsActive());
			complaintDataTable.setCreatedBy(complaintDTO.getCreatedBy() );
			complaintDataTable.setCreatedDate(complaintDTO.getCreatedDate());
			complaintDataTable.setModifiedBy(complaintDTO.getModifiedBy());
			complaintDataTable.setModifiedDate(complaintDTO.getModifiedDate());
			complaintDataTable.setApprovedBy(complaintDTO.getApprovedBy());
			complaintDataTable.setApprovedDate(complaintDTO.getApprovedDate());
			complaintDataTable.setIfClickEdit(false);
			if(complaintDTO.getActionGroup().equalsIgnoreCase(Constants.O)){
				complaintDataTable.setActionGroup(complaintDTO.getActionGroup() );
				complaintDataTable.setActionGroupId(Constants.OPEN);
			}
			else if(complaintDTO.getActionGroup().equalsIgnoreCase(Constants.C)){
			 
				complaintDataTable.setActionGroup(complaintDTO.getActionGroup());
				complaintDataTable.setActionGroupId(Constants.CLOSE);
			}else if(complaintDTO.getActionGroup().equalsIgnoreCase(Constants.P)){
				complaintDataTable.setActionGroupId(Constants.PENDING);
				complaintDataTable.setActionGroup(complaintDTO.getActionGroup());
			}
			
			complaintDataTable.setActionGroup(complaintDTO.getActionGroup());
			complaintDataTable.setApplicationCountryId(complaintDTO.getApplicationCountryId());
			
			if(complaintDataTable.getIsActive().equalsIgnoreCase(Constants.Yes)){
				complaintDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
			}else if(complaintDataTable.getIsActive().equalsIgnoreCase(Constants.D)){
				complaintDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
			}else if(complaintDataTable.getIsActive().equalsIgnoreCase(Constants.U)&&complaintDataTable.getApprovedBy()==null&&complaintDataTable.getApprovedDate()==null&&complaintDataTable.getModifiedBy()==null&&complaintDataTable.getModifiedDate()==null&&complaintDataTable.getRemarks()==null){
				complaintDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
			}else{
				complaintDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
			}
			
			List<ComplaintActionDTO> lstComplaintActionDTO1=iComplaintActionService.getAllComplaintActionChildRecords(complaintDTO.getComplaintActionId() );
			   if(lstComplaintActionDTO1.size()>0){
				     childRecordCheck = true;
				     for (ComplaintActionDTO ComplaintActionDTO1 : lstComplaintActionDTO1) {
					     if(ComplaintActionDTO1.getLanguageId().intValue()==1){
					    	 complaintDataTable.setComplaintActionFullDescription(ComplaintActionDTO1.getFullDescription());
					    	 complaintDataTable.setComplaintActionShortDescription(ComplaintActionDTO1.getShortDescription());
					    	 complaintDataTable.setComplaintActionDescEnglishPk(ComplaintActionDTO1.getComplaintActionDescId());
					    	 complaintDataTable.setEnglishLanguageId(ComplaintActionDTO1.getLanguageId());
	 					    	 
					     }else{
					    	 complaintDataTable.setComplaintActionArabicFullDescription(ComplaintActionDTO1.getFullDescription() );
					    	 complaintDataTable.setComplaintActionArabicShortDescription(ComplaintActionDTO1.getShortDescription() );
					    	 complaintDataTable.setArabicLanguageId(ComplaintActionDTO1.getLanguageId());
					    	 complaintDataTable.setComplaintActionDescArabicPk(ComplaintActionDTO1.getComplaintActionDescId());
					    							        
					     }
			      }
					
			      }else{
				childRecordCheck=false;	
			   }
			   if(childRecordCheck==true){
				   compliantActionDTList.add(complaintDataTable);
			   }
			
			
		}
	  
	compliantActionDTList.addAll(compliantActionViewList);
	clearAllFields();
	/*try {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintaction.xhtml");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	*/
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::viewAll");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void clearRemarks(){
		setRemarks(null);
	}	
	
	public void confirmActivate(ComplaintActionDataTable complaintActionObj){
		try{
		iComplaintActionService.activateRecord(complaintActionObj.getComplaintActionPk(), session.getUserName());
		viewAll();
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::confirmActivate");
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
		if(compliantActionDTList.size()>0){
		for(ComplaintActionDataTable complaintActionObj:compliantActionDTList){
			if(complaintActionObj.getActivateRecordCheck()!=null){
			if(complaintActionObj.getActivateRecordCheck().equals(true)){
				confirmActivate(complaintActionObj);
			}
			}
		}
		}
	} 

	public void approveRecord(){
			try{	
		String approvalMsg=iComplaintActionService.approveRecord(getComplaintActionMasterPk(), session.getUserName());
		if(approvalMsg.equalsIgnoreCase("Success")){
			
			RequestContext.getCurrentInstance().execute("approve.show();");
		}
		else{
			RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
		}
			}catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::approveRecord");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
	}
	
	 public void populateAllFields(){
		setComplaintActionDescArabicPk(null);
		 setComplaintActionDescEngPk(null);
		 setComplaintActionShortDescription(null);
		 setComplaintActionFullDescription(null);
		 setComplaintActionArabicFullDescription(null);
		 setComplaintActionArabicShortDescription(null);
		 setActionGroup(null);
		 setComplaintActionMasterPk(null);
		 if(getComplaintActionCode()!=null){
			 try{
		 List<ComplaintActionDTO> populationList=	iComplaintActionService.getParentRecordForPopulate( getComplaintActionCode());
		if(populationList.size()>0){
			RequestContext.getCurrentInstance().execute("recordAlreadyExist.show();");
			setComplaintActionCode(null);
			return;
		}
			 }catch (NullPointerException ne) {
					// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setErrorMessage("Method Name::populateAllFields");
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
	 
	 public void disableSubmit(){
		    setDisableSubmitButton(true);
	  }
	
/*public void populateAllFields(){
	 setComplaintActionDescArabicPk(null);
	 setComplaintActionDescEngPk(null);
	 setComplaintActionShortDescription(null);
	 setComplaintActionFullDescription(null);
	 setComplaintActionArabicFullDescription(null);
	 setComplaintActionArabicShortDescription(null);
	 setActionGroup(null);
	 setComplaintActionMasterPk(null);
	if(getComplaintActionCode()!=null){
		
	List<ComplaintActionDTO> populationList=	iComplaintActionService.getParentRecordForPopulate( getComplaintActionCode());
	for(ComplaintActionDTO complaintActionDTO:populationList){
		setComplaintActionCode(complaintActionDTO.getComplaintActionCode());
		setActionGroup(complaintActionDTO.getActionGroup());
		 
		List<ComplaintActionDTO> actionDesclist=  iComplaintActionService.getAllComplaintActionChildRecords(complaintActionDTO.getComplaintActionId());
		
		if(actionDesclist.size()>0) {
		for(ComplaintActionDTO complaintDescDTO:actionDesclist){
			
		 if(complaintDescDTO.getLanguageId().intValue()==1){
		setComplaintActionFullDescription(complaintDescDTO.getFullDescription());
		setComplaintActionShortDescription(complaintDescDTO.getShortDescription());
		
		 }else{
			 setComplaintActionArabicFullDescription(complaintDescDTO.getFullDescription() );
				setComplaintActionArabicShortDescription(complaintDescDTO.getShortDescription());
		 }
		}
		}
	}
	}
	 
	
		
		
		
	 
}*/
	
	
	
	

	

	 


	

	
	

}
