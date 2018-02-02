package com.amg.exchange.common.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.ISourceOfIncomeService;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.remittance.bean.ImpsApprovalBean;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("sourceOfIncomeApprovalBean")
@Scope("session")
public class SourceOfIncomeApprovalBean<T> implements Serializable {
 
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(ImpsApprovalBean.class);
	private SessionStateManage session=new SessionStateManage();
	
	
  List<SourceOfIncomeDataTableBean> approvalList=new ArrayList<SourceOfIncomeDataTableBean>();
	
  @Autowired 
  ISourceOfIncomeService isourceOfIncomeService;
  
  @Autowired 
  SourceOfIncomeBean sourceOfIncomeBean;
  
  
  private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	private Boolean booVisible;

	public Boolean getBooVisible() {
		return booVisible;
	}

	public void setBooVisible(Boolean booVisible) {
		this.booVisible = booVisible;
	}
  
  
  
  public List<SourceOfIncomeDataTableBean> getApprovalList() {
		return approvalList;
	}

	public void setApprovalList(List<SourceOfIncomeDataTableBean> approvalList) {
		this.approvalList = approvalList;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigationToSourceIncomeApproval(){
		approvalList.clear();
		getAllUnApprovedRecords();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "sourceofincomeapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../common/sourceofincomeapproval.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to   Source Of income approval");
		}
	}
	public void goHome() throws IOException {
 		if (session.getRoleId().equalsIgnoreCase("1")) {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../registration/employeehome.xhtml");
	} else {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../registration/branchhome.xhtml");
	}
}
	
	public void gotoApproval(SourceOfIncomeDataTableBean sourceOfIncomeDataTableObj){
		
		try {
			
			if((sourceOfIncomeDataTableObj.getModifiedBy()==null ? sourceOfIncomeDataTableObj.getCreatedBy() : sourceOfIncomeDataTableObj.getModifiedBy()).equalsIgnoreCase(session.getUserName())){
				RequestContext.getCurrentInstance().execute("username.show();");
			}else{
			
			sourceOfIncomeBean.setReadOnly(true);
			sourceOfIncomeBean.setBooSubmitPanel( false);
			sourceOfIncomeBean.setBooRenderViewPanel(false);
			sourceOfIncomeBean.setBooRenderDataTable( false);
			sourceOfIncomeBean.setBooRenderApprove( true);
			
 
			sourceOfIncomeBean.setSourceCode(sourceOfIncomeDataTableObj.getSourceOfIncomeCode() );
			sourceOfIncomeBean.setSrcIncomeEngFullDesc( sourceOfIncomeDataTableObj.getSourceOfIncomeEngFullDesc());
			sourceOfIncomeBean.setSrcIncomeEngShortDesc(sourceOfIncomeDataTableObj.getSourceOfIncomeEngShortDesc() );
			sourceOfIncomeBean.setSrcIncomeArabicFullDesc(sourceOfIncomeDataTableObj.getSourceOfIncomeArabicFullDesc());
			sourceOfIncomeBean.setSrcIncomeArabicShortDesc(sourceOfIncomeDataTableObj.getSourceOfIncomeArabicShortDesc() );
			sourceOfIncomeBean.setSourceOfIncomePk( sourceOfIncomeDataTableObj.getSourceOfIncomePk());
			sourceOfIncomeBean.setSourceOfIncomeEngPk( sourceOfIncomeDataTableObj.getSourceOfIncomeEngPk());
			sourceOfIncomeBean.setSourceOfIncomeArabicPk(sourceOfIncomeDataTableObj.getSourceOfIncomeArabicPk());
			sourceOfIncomeBean.setCreatedBy(sourceOfIncomeDataTableObj.getCreatedBy());
			sourceOfIncomeBean.setCreatedDate( sourceOfIncomeDataTableObj.getCreatedDate());
			try {

				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../common/sourceofincome.xhtml");
			} catch (Exception e) {
				log.error("Error in Page Navigation to   Source Of income ");
			}
			}
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
		
	}
	
	public void getAllUnApprovedRecords(){
	List<SourceOfIncomeDescription> apprvList=	isourceOfIncomeService.getAllUnApprovedRecords();
	if(apprvList.size()>0){	
	
	for(SourceOfIncomeDescription sourceOfIncDesc:apprvList){
		SourceOfIncomeDataTableBean sourceOfIncomeDataTableObj=new SourceOfIncomeDataTableBean();
		sourceOfIncomeDataTableObj.setCompanyId( sourceOfIncDesc.getSourceOfIncomeId().getFsCompanyMaster().getCompanyId());
		sourceOfIncomeDataTableObj.setApplicationCountryId(sourceOfIncDesc.getSourceOfIncomeId().getFsCountryMaster().getCountryId() );
		sourceOfIncomeDataTableObj.setCreatedBy(sourceOfIncDesc.getSourceOfIncomeId().getCreatedBy() );
		sourceOfIncomeDataTableObj.setCreatedDate(sourceOfIncDesc.getSourceOfIncomeId().getCreationDate());
		sourceOfIncomeDataTableObj.setSourceOfIncomePk( sourceOfIncDesc.getSourceOfIncomeId().getSourceId());
		sourceOfIncomeDataTableObj.setModifiedBy(sourceOfIncDesc.getSourceOfIncomeId().getModifiedBy());
		sourceOfIncomeDataTableObj.setModifiedDate(sourceOfIncDesc.getSourceOfIncomeId().getModifiedDate());
		sourceOfIncomeDataTableObj.setSourceOfIncomeCode( sourceOfIncDesc.getSourceOfIncomeId().getSourceCode());
		
		 if(sourceOfIncDesc.getLanguageId().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID.toString())){
				sourceOfIncomeDataTableObj.setSourceOfIncomeEngPk( sourceOfIncDesc.getSourceOfIncomeDescId());
				sourceOfIncomeDataTableObj.setSourceOfIncomeEngFullDesc(sourceOfIncDesc.getSourceOfIncomeFullDesc());
				sourceOfIncomeDataTableObj.setSourceOfIncomeEngShortDesc(sourceOfIncDesc.getSourceOfIncomeShortDesc());
			  
			if(sourceOfIncomeDataTableObj.getSourceOfIncomeEngPk()!=null&&sourceOfIncomeDataTableObj.getSourceOfIncomeArabicPk()!=null){
				approvalList.add(sourceOfIncomeDataTableObj);
			} 
				 
			}
			List<SourceOfIncomeDescription> apprvList2=	isourceOfIncomeService.getAllUnApprovedRecords();
				for(SourceOfIncomeDescription srcIncomeDescObj2:apprvList2){
					if(sourceOfIncomeDataTableObj.getSourceOfIncomePk().toString().equals(srcIncomeDescObj2.getSourceOfIncomeId().getSourceId().toString())&&srcIncomeDescObj2.getLanguageId().getLanguageId().toString().equals(Constants.ARABIC_LANGUAGE_ID.toString())){
						
						 
						sourceOfIncomeDataTableObj.setSourceOfIncomeArabicPk(srcIncomeDescObj2.getSourceOfIncomeDescId() );
						sourceOfIncomeDataTableObj.setSourceOfIncomeArabicFullDesc(srcIncomeDescObj2.getSourceOfIncomeFullDesc() );
						sourceOfIncomeDataTableObj.setSourceOfIncomeArabicShortDesc(srcIncomeDescObj2.getSourceOfIncomeShortDesc());
						 
						if(sourceOfIncomeDataTableObj.getSourceOfIncomeEngPk()!=null&&sourceOfIncomeDataTableObj.getSourceOfIncomeArabicPk()!=null){
							approvalList.add(sourceOfIncomeDataTableObj);
							}  
		 
		 			
	}
	}
		 
	}
	}else{
		
	}
	}

	
	
	
	
	

}
