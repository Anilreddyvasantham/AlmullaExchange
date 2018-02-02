package com.amg.exchange.common.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IDocumentService;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("documentApprovalBean")
@Scope("session")
public class DocumentApprovalBean<T> {
	private static Logger log = Logger.getLogger(DocumentApprovalBean.class);
	private SessionStateManage session=new SessionStateManage();
	
	private List<DocumentDatatableBean> approvalList=new ArrayList<DocumentDatatableBean>();
	
	@Autowired
	IDocumentService idocumentService;
	
	@Autowired
	DocumentMasterBean documentMasterBean;
	
	public List<DocumentDatatableBean> getApprovalList() {
		return approvalList;
	}

	public void setApprovalList(List<DocumentDatatableBean> approvalList) {
		this.approvalList = approvalList;
	}
	 
	public void getAllRecordsForApproval(){
		approvalList.clear();
		 List<Document>   documentList=idocumentService.getAllUnApprovedRecordsFromDB();
		if(documentList.size()>0){
			for(Document document:documentList){
				
				DocumentDatatableBean documentDTObj=new DocumentDatatableBean();
				documentDTObj.setDocumentMasterPk(document.getDocumentID());
				documentDTObj.setDocumentCode(document.getDocumentCode() );
				documentDTObj.setDocumentDescription(document.getDocumentDesc());
				documentDTObj.setCreatedBy(document.getCreatedBy() );
				documentDTObj.setCreatedDate(document.getCreatedDate());
				documentDTObj.setModifiedBy(document.getModifiedBy());
				documentDTObj.setModifiedDate(document.getModifiedDate());
				approvalList.add(documentDTObj);		
			}
		}
		 
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToDocumentApprovalPage() throws IOException{
		getAllRecordsForApproval();
		loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "documentapproval.xhtml");
		FacesContext.getCurrentInstance().getExternalContext().redirect("../common/documentapproval.xhtml");
	}
	
	public void gotoApproval(DocumentDatatableBean documentDTObj) throws IOException{
		if((documentDTObj.getModifiedBy()==null ? documentDTObj.getCreatedBy() : documentDTObj.getModifiedBy()).equalsIgnoreCase(session.getUserName())){
			RequestContext.getCurrentInstance().execute("username.show();");
	}else{
		documentMasterBean.setReadOnly(true);
		documentMasterBean.setBooRenderSubmitPanel(false);
		documentMasterBean.setBooRenderDataTable(false);
		documentMasterBean.setBooRenderSaveExit(false);
		documentMasterBean.setBooApprovePanel(true);
		documentMasterBean.setDocumentMasterPk(documentDTObj.getDocumentMasterPk());
		documentMasterBean.setDocumentCode(documentDTObj.getDocumentCode()); 
		documentMasterBean.setDocumentDescription( documentDTObj.getDocumentDescription());
		documentMasterBean.setCreatedBy(documentDTObj.getCreatedBy());
		documentMasterBean.setCreatedDate(documentDTObj.getCreatedDate());
		documentMasterBean.setModifiedBy(documentDTObj.getModifiedBy());
		documentMasterBean.setModifiedDate(documentDTObj.getModifiedDate());
		FacesContext.getCurrentInstance().getExternalContext().redirect("../common/documentmaster.xhtml");
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
	
	public void clickOKButton() {
		getAllRecordsForApproval();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../common/documentapproval.xhtml");
		} catch (Exception e) {
			log.info("Navigation Problem When ClickOnOkButton()");
		}
	}

	
}
