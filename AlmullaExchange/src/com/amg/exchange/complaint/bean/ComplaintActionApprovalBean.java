package com.amg.exchange.complaint.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.dto.ComplaintActionDTO;
import com.amg.exchange.complaint.service.IComplaintActionService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("complaintActionApprovalBean")
@Scope("session")
public class ComplaintActionApprovalBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(ComplaintActionBean.class);
	private SessionStateManage session=new SessionStateManage();
	
	
	@Autowired
	IComplaintActionService icomplaintActionService;
	
	@Autowired
	ComplaintActionBean complaintActionBean;
	
	List<ComplaintActionDataTable> approvalList=new ArrayList<ComplaintActionDataTable>();
	
	public List<ComplaintActionDataTable> getApprovalList() {
		return approvalList;
	}
	public void setApprovalList(List<ComplaintActionDataTable> approvalList) {
		this.approvalList = approvalList;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
		
	public void  complaintActionApprovalPageNavigation() {
		
		getAllApprovalRecords();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "complaintactionapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/complaintactionapproval.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to Complaint Action approval");
		}

	}
	
		
	public void getAllApprovalRecords(){
		
		approvalList.clear();
		List<ComplaintActionDTO>  complaintActionList=icomplaintActionService.getAllUnApprovedRecords();
		
		for(ComplaintActionDTO complaintDTO:complaintActionList){
			
			
           ComplaintActionDataTable complaintDataTable=new ComplaintActionDataTable();
			
			complaintDataTable.setComplaintActionCode(complaintDTO.getComplaintActionCode());
			complaintDataTable.setComplaintActionPk(complaintDTO.getComplaintActionId());
			complaintDataTable.setIsActive(complaintDTO.getIsActive());
			complaintDataTable.setCreatedBy(complaintDTO.getCreatedBy() );
			complaintDataTable.setCreatedDate(complaintDTO.getCreatedDate());
			complaintDataTable.setModifiedBy(complaintDTO.getModifiedBy());
			complaintDataTable.setModifiedDate(complaintDTO.getModifiedDate());
			complaintDataTable.setActionGroup(complaintDTO.getActionGroup());
			if(complaintDTO.getActionGroup().equalsIgnoreCase(Constants.O)){
				complaintDataTable.setActionGroup(complaintDTO.getActionGroup());
				complaintDataTable.setActionGroupId(Constants.OPEN);
			}else if(complaintDTO.getActionGroup().equalsIgnoreCase(Constants.C)){
				complaintDataTable.setActionGroup(complaintDTO.getActionGroup());
				complaintDataTable.setActionGroupId(Constants.CLOSE);
			}else if(complaintDTO.getActionGroup().equalsIgnoreCase(Constants.P)){
				complaintDataTable.setActionGroup(complaintDTO.getActionGroup());
				complaintDataTable.setActionGroupId(Constants.PENDING);
			}
			complaintDataTable.setApplicationCountryId(complaintDTO.getApplicationCountryId());
			
			List<ComplaintActionDTO>  complaintActionDescList=icomplaintActionService.getAllUnApprovedRecordsFromDesc(complaintDataTable.getComplaintActionPk());
			for(ComplaintActionDTO complaintActionDTObj:complaintActionDescList){
				if(complaintActionDTObj.getLanguageId().intValue()==1){
					complaintDataTable.setComplaintActionFullDescription(complaintActionDTObj.getFullDescription());
					complaintDataTable.setComplaintActionShortDescription(complaintActionDTObj.getShortDescription());
					complaintDataTable.setComplaintActionDescEnglishPk(complaintActionDTObj.getComplaintActionDescId());
					
				}
				if(complaintActionDTObj.getLanguageId().intValue()==2){
					complaintDataTable.setComplaintActionArabicFullDescription( complaintActionDTObj.getFullDescription());
					complaintDataTable.setComplaintActionArabicShortDescription(complaintActionDTObj.getShortDescription());
					complaintDataTable.setComplaintActionDescArabicPk(complaintActionDTObj.getComplaintActionDescId());
				}
			}
			approvalList.add(complaintDataTable);
		}
		
	}
	
	public void gotoApproval(ComplaintActionDataTable complaintActionDataTable){
		if((complaintActionDataTable.getModifiedBy()==null ? complaintActionDataTable.getCreatedBy() : complaintActionDataTable.getModifiedBy()).equalsIgnoreCase(session.getUserName())){
			RequestContext.getCurrentInstance().execute("unapprove.show();");
	}else{
		complaintActionBean.setRenderDatatable(false);
		complaintActionBean.setRenderSubmitPanel(false);
		complaintActionBean.setRenderSaveButton(false);
		complaintActionBean.setRenderApprov(true);
		complaintActionBean.setReadOnly(true);
		complaintActionBean.setDisableActionGroup(true);
		try {

			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/complaintaction.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to Complaint Action approval");
		}
		complaintActionBean.setComplaintActionCode(complaintActionDataTable.getComplaintActionCode());
		complaintActionBean.setComplaintActionArabicFullDescription(complaintActionDataTable.getComplaintActionArabicFullDescription() );
		complaintActionBean.setComplaintActionArabicShortDescription(complaintActionDataTable.getComplaintActionArabicShortDescription());
		complaintActionBean.setComplaintActionFullDescription(complaintActionDataTable.getComplaintActionFullDescription());
		complaintActionBean.setComplaintActionShortDescription(complaintActionDataTable.getComplaintActionShortDescription());
		if(complaintActionDataTable.getActionGroup().equalsIgnoreCase(Constants.P)){
		complaintActionBean.setActionGroup(complaintActionDataTable.getActionGroup());
		complaintActionBean.setActionGroupId(complaintActionDataTable.getActionGroupId());
		}
		else if(complaintActionDataTable.getActionGroup().equalsIgnoreCase(Constants.O)){
			complaintActionBean.setActionGroup(complaintActionDataTable.getActionGroup());	
			complaintActionBean.setActionGroupId(complaintActionDataTable.getActionGroupId());
		}else if(complaintActionDataTable.getActionGroup().equalsIgnoreCase(Constants.C )){
			complaintActionBean.setActionGroup(complaintActionDataTable.getActionGroup());
			complaintActionBean.setActionGroupId(complaintActionDataTable.getActionGroupId());
		}
		complaintActionBean.setComplaintActionMasterPk( complaintActionDataTable.getComplaintActionPk());
		
		
	}
	}
	
	 public void clickOnOk() {
		 getAllApprovalRecords();
			log.info( "Entered into clickOnOk() method ");
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../complaint/complaintactionapproval.xhtml");
			} catch (Exception e) {
				log.error("This is Navigation problem in clickOnOk()");
			}
		} 
		
		public void cancel() {
			getAllApprovalRecords();
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../complaint/complaintactionapproval.xhtml");
			} catch (Exception e) {
				log.error("This is Navigation problem when click cancel()" + e);
			}
		}
		
		
	
	
	
	
	
}
