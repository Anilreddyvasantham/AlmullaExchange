package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.Imps;
import com.amg.exchange.remittance.service.IImpsService;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("impsApprovalBean")
@Scope("session")
public class ImpsApprovalBean<T> implements Serializable {

 private static final long serialVersionUID = 1L;
	
 	private static final Logger log=Logger.getLogger(ImpsApprovalBean.class);
	private SessionStateManage session=new SessionStateManage();
	
	List<ImpsDataTableBean> approvalList=new ArrayList<ImpsDataTableBean>();
	
	@Autowired 
	IImpsService iimpsService;
	@Autowired
	IGeneralService igeneralService;
	
	@Autowired
	ImpsBean impsBean;
	
	private String errorMessage;
	
	
	
	public List<ImpsDataTableBean> getApprovalList() {
		return approvalList;
	}


	public void setApprovalList(List<ImpsDataTableBean> approvalList) {
		this.approvalList = approvalList;
	}
	
	
	public void getAllUnApprovedRecords(){
		try{
		approvalList.clear();
		List<Imps>	 impsUnapprovelist=iimpsService.getAllUnApprovedRecords();
		for(Imps impsObj:impsUnapprovelist){
			ImpsDataTableBean impsdataTableObj=new ImpsDataTableBean();
			
			impsdataTableObj.setImpsPk(impsObj.getImpsId());
			impsdataTableObj.setBeneBankId(impsObj.getBeneBankId().getBankId() );
			impsdataTableObj.setBeneBankDesc(igeneralService.getBankName(impsObj.getBeneBankId().getBankId()  ) );
			impsdataTableObj.setBeneBankCode(igeneralService.getBankCode(impsObj.getBeneBankId().getBankId() ) );
			impsdataTableObj.setCorrespBankId(impsObj.getCorrespondingBankId().getBankId() );
			impsdataTableObj.setCorrspBankCode(igeneralService.getBankCode( impsObj.getCorrespondingBankId().getBankId()) );
			impsdataTableObj.setCorrespBankDesc( igeneralService.getBankName(impsObj.getCorrespondingBankId().getBankId()));
			impsdataTableObj.setApplicationCountryId(impsObj.getApplicationCountryId().getCountryId());
			impsdataTableObj.setCreatedBy(impsObj.getCreatedBy() );
			impsdataTableObj.setCreatedDate(impsObj.getCreatedDate() );
			impsdataTableObj.setModifiedBy(impsObj.getModifiedBy() );
			impsdataTableObj.setModifiedDate(impsObj.getModifiedDate());
			approvalList.add(impsdataTableObj );
			
		}
		}catch(NullPointerException  e){
			setErrorMessage("Method Name:getAllUnApprovedRecords"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		} 
	}
	
	
	public void gotoApproval(ImpsDataTableBean impsdataTableObj){
		try{
		if((impsdataTableObj.getModifiedBy()==null ? impsdataTableObj.getCreatedBy() : impsdataTableObj.getModifiedBy()).equalsIgnoreCase(session.getUserName())){
			RequestContext.getCurrentInstance().execute("username.show();");
	}else{
 
		 
		impsBean.setCorrespBank( impsdataTableObj.getCorrespBankDesc());
		impsBean.setBeneBank(impsdataTableObj.getBeneBankDesc());
		impsBean.setBeneBankId(impsdataTableObj.getBeneBankId());
		impsBean.setCreatedBy( impsdataTableObj.getCreatedBy());
		impsBean.setCreatedDate( impsdataTableObj.getCreatedDate());
		impsBean.setModifiedBy( impsdataTableObj.getModifiedBy());
		impsBean.setModifiedDate(impsdataTableObj.getModifiedDate() );
		impsBean.setCorrspBankId( impsdataTableObj.getCorrespBankId());
		impsBean.setImpsPk(impsdataTableObj.getImpsPk());
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/impsfinalapprove.xhtml");
		 
	}
		}catch(NullPointerException  e){
			setErrorMessage("Method Name:gotoApproval"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		} 
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigationToImpsApproval(){
		getAllUnApprovedRecords();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "impsapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/impsapproval.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to  IMPS");
		}
	}
	public void cancel() {
		getAllUnApprovedRecords();
		try {

			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/impsapproval.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to  IMPS");
		}
	
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
