package com.amg.exchange.common.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.InsuranceMasterDescription;
import com.amg.exchange.common.service.IInsuranceSetUpService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("insuranceSetUpInquiryBean")
@Scope("session")
public class InsuranceSetUpInquiryBean<T> implements Serializable {

	 	private static final long serialVersionUID = 1L;
	 	public static final Logger log = Logger.getLogger(InsuranceSetUpInquiryBean.class);
	 	SessionStateManage sessionStateManage=new SessionStateManage();
	 	
	 	
	 	private Date fromDate;
	 	private Date toDate;
	 	private String errorMsg;
	 	
	 	
	 	@Autowired
	 	IInsuranceSetUpService iInsuranceSetupService;
	 	
	 	List<InsuranceSetUpDataTableBean> inquiryDTList=new ArrayList<InsuranceSetUpDataTableBean>();
	 	
	 	public List<InsuranceSetUpDataTableBean> getInquiryDTList() {
			return inquiryDTList;
		}

		public void setInquiryDTList(List<InsuranceSetUpDataTableBean> inquiryDTList) {
			this.inquiryDTList = inquiryDTList;
		}

		public void medicalInsuranceInquiry(){
			try{
			inquiryDTList.clear();
	 		log.info("======================="+getFromDate());
	 		log.info("======================="+getToDate());
	 		List<InsuranceMasterDescription> inquirylist = iInsuranceSetupService.medicalInsuranceInquiry(getFromDate(),getToDate());
	 		if(inquirylist.size()>0){
	 		for(InsuranceMasterDescription insuranceDescObj:inquirylist){
	 		log.info("inquirylist size============"+inquirylist.size());
	 		 InsuranceSetUpDataTableBean insuarnceDTObj=new InsuranceSetUpDataTableBean();
			 
	 		 SimpleDateFormat fromdate = new SimpleDateFormat("dd/MM/yyyy");			  			  
			 insuarnceDTObj.setEffectiveFromDate(fromdate.format(insuranceDescObj.getInsuranceMasterId().getFromDate()) );
	 		 
			 SimpleDateFormat todate = new SimpleDateFormat("dd/MM/yyyy");
			 insuarnceDTObj.setEffectiveToDate(todate.format(insuranceDescObj.getInsuranceMasterId().getToDate() ) );
			 
			  
			 insuarnceDTObj.setFromAmount(insuranceDescObj.getInsuranceMasterId().getFromAmount());
			 insuarnceDTObj.setToAmount(insuranceDescObj.getInsuranceMasterId().getToAmount() );
			 insuarnceDTObj.setLoyaltyPoints(insuranceDescObj.getInsuranceMasterId().getInsuranceLoyaltyPoints() );
			 insuarnceDTObj.setInsuranceDays(insuranceDescObj.getInsuranceMasterId().getInsuranceDays() );
			 insuarnceDTObj.setInsuranceAdditionalAmount(insuranceDescObj.getInsuranceMasterId().getInsuranceAdditionalAmount());
			 insuarnceDTObj.setInsuranceAmount( insuranceDescObj.getInsuranceMasterId().getInsuranceAmount());
			 insuarnceDTObj.setCreatedBy(insuranceDescObj.getInsuranceMasterId().getCreatedBy());
			 insuarnceDTObj.setCreatedDate(insuranceDescObj.getInsuranceMasterId().getCreatedDate());
			 insuarnceDTObj.setModifiedBy(insuranceDescObj.getInsuranceMasterId().getModifiedBy() );
			 insuarnceDTObj.setModifiedDate(insuranceDescObj.getInsuranceMasterId().getModifiedDate() );
			 if(insuranceDescObj.getInsuranceMasterId().getIsActive().equalsIgnoreCase(Constants.U)){
				 if(insuranceDescObj.getInsuranceMasterId().getModifiedBy()!=null&&insuranceDescObj.getInsuranceMasterId().getModifiedDate()!=null){
				   insuarnceDTObj.setIsActive("ACTIVATED");
				 }else{
					 insuarnceDTObj.setIsActive("UNAPPROVED");
				 }
			 }
			 else if(insuranceDescObj.getInsuranceMasterId().getIsActive().equalsIgnoreCase(Constants.Yes)){
				 insuarnceDTObj.setIsActive("APPROVED");
			 }else if(insuranceDescObj.getInsuranceMasterId().getIsActive().equalsIgnoreCase(Constants.D)){
				 
				insuarnceDTObj.setIsActive("DEACTIVATED");
			 }
			 
			 
			 insuarnceDTObj.setInsuranceSetUpPk(insuranceDescObj.getInsuranceMasterId().getInsuranceSetUpId() );
			 if(insuranceDescObj.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID.toString())){
				 insuarnceDTObj.setInsuranceMsgOne(insuranceDescObj.getInsurancePrimaryMessage() );	
				 insuarnceDTObj.setInsuranceMsgTwo(insuranceDescObj.getInsuranceSecondaryMessage() );
				 insuarnceDTObj.setInsuranceMasterDescPk(insuranceDescObj.getInsuranceMasterDescId() );
				  
				if(insuarnceDTObj.getInsuranceMasterDescPk()!=null&&insuarnceDTObj.getInsuranceMasterDescArabicPk()!=null){
					inquiryDTList.add(insuarnceDTObj);
				} 
					 
				}
			List<InsuranceMasterDescription> appList2=iInsuranceSetupService.medicalInsuranceInquiry(getFromDate(),getToDate() );	 
					for(InsuranceMasterDescription InsuranceDescObj2:appList2){
						if(insuarnceDTObj.getInsuranceSetUpPk().toString().equals(InsuranceDescObj2.getInsuranceMasterId().getInsuranceSetUpId().toString())&&InsuranceDescObj2.getLanguageType().getLanguageId().toString().equals(Constants.ARABIC_LANGUAGE_ID.toString())){
							
							insuarnceDTObj.setInsuranceArabicMsgOne(InsuranceDescObj2.getInsurancePrimaryMessage());
							insuarnceDTObj.setInsuranceArabicMsgTwo(InsuranceDescObj2.getInsuranceSecondaryMessage());
							insuarnceDTObj.setInsuranceMasterDescArabicPk(InsuranceDescObj2.getInsuranceMasterDescId() );
							 
							 
							if(insuarnceDTObj.getInsuranceMasterDescPk()!=null&&insuarnceDTObj.getInsuranceMasterDescArabicPk()!=null){
								inquiryDTList.add(insuarnceDTObj);
								}  
			 			 				
		}
		
				
	}
	
	
	
}
	 		}else{
	 			RequestContext.getCurrentInstance().execute("norecord.show();");
	 		}
			}catch(NullPointerException  e){ 
					setErrorMsg("medicalInsuranceInquiry :");
					RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
				}
			catch (Exception e) {
					setErrorMsg(e.getMessage());
					RequestContext.getCurrentInstance().execute("csp.show();");
				}
	}

		public Date getFromDate() {
			return fromDate;
		}

		public void setFromDate(Date fromDate) {
			this.fromDate = fromDate;
		}

		public Date getToDate() {
			return toDate;
		}

		public void setToDate(Date toDate) {
			this.toDate = toDate;
		}
		@Autowired
		LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
		public void pageNavigationToInquiry(){
			clearAll();
			try {
				loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "medicalinsuranceinquiry.xhtml");
				FacesContext.getCurrentInstance().getExternalContext().redirect("../common/medicalinsuranceinquiry.xhtml");
			} catch (IOException e) {
				 
				 log.error("==================exception occured ============");
			}
		}

public void clearAll(){
	
	setFromDate(null);
	setToDate(null);
	inquiryDTList.clear();
}

public void exit(){
	clearAll();
	if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} catch (IOException e) {
			 
			 log.error("===========Navigation problemin exit() method==========");
		}
	} else {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		} catch (IOException e) {
			 log.error("===========Navigation problem exit() ==========");
			 
		}
	}
}

public String getErrorMsg() {
	return errorMsg;
}

public void setErrorMsg(String errorMsg) {
	this.errorMsg = errorMsg;
}
	 	 
	
 
	
	
	
}
