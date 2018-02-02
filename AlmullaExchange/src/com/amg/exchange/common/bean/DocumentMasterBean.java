package com.amg.exchange.common.bean;

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

import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IDocumentService;
import com.amg.exchange.remittance.bean.SwiftMasterMaintenanceDataTableBean;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("documentMasterBean")
@Scope("session")
public class DocumentMasterBean<T> implements Serializable {

	 
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(DocumentMasterBean.class);
	
	private BigDecimal documentCode;
	private String documentDescription;
	private BigDecimal documentMasterPk;

	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private Boolean booRenderSaveExit;
	private Boolean booRenderApproval;
	private Boolean hideSubmit;
	private Boolean hideEditButton;
	private Boolean hideClear;
	private String isActive;
	private Boolean booRenderDataTable;
	private Boolean booRenderSubmitPanel;
	private Boolean booRenderEditButton;
	private Boolean checkSave;
	private Boolean booCheckUpdate;
	private String dynamicAcivateDeactivate;
	private Boolean readOnly=false;
	private Boolean booApprovePanel;
	private String errorMessage;
	
	
	
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Boolean getBooRenderEditButton() {
		return booRenderEditButton;
	}

	public void setBooRenderEditButton(Boolean booRenderEditButton) {
		this.booRenderEditButton = booRenderEditButton;
	}


	private SessionStateManage session=new SessionStateManage();
	 
	private List<DocumentDatatableBean> listDT=new CopyOnWriteArrayList<DocumentDatatableBean>();
	


	private List<DocumentDatatableBean> viewDTList=new CopyOnWriteArrayList<DocumentDatatableBean>();
	
	@Autowired
	IDocumentService idocumentService;
	
	
	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}
	public List<DocumentDatatableBean> getListDT() {
		return listDT;
	}

	public void setListDT(List<DocumentDatatableBean> listDT) {
		this.listDT = listDT;
	}

	public BigDecimal getDocumentMasterPk() {
		return documentMasterPk;
	}

	public void setDocumentMasterPk(BigDecimal documentMasterPk) {
		this.documentMasterPk = documentMasterPk;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public Boolean getBooRenderSaveExit() {
		return booRenderSaveExit;
	}

	public void setBooRenderSaveExit(Boolean booRenderSaveExit) {
		this.booRenderSaveExit = booRenderSaveExit;
	}

	public Boolean getBooRenderApproval() {
		return booRenderApproval;
	}

	public void setBooRenderApproval(Boolean booRenderApproval) {
		this.booRenderApproval = booRenderApproval;
	}

	public Boolean getHideSubmit() {
		return hideSubmit;
	}

	public void setHideSubmit(Boolean hideSubmit) {
		this.hideSubmit = hideSubmit;
	}

	public Boolean getHideEditButton() {
		return hideEditButton;
	}

	public void setHideEditButton(Boolean hideEditButton) {
		this.hideEditButton = hideEditButton;
	}

	public Boolean getHideClear() {
		return hideClear;
	}

	public void setHideClear(Boolean hideClear) {
		this.hideClear = hideClear;
	}
	public String getDocumentDescription() {
		return documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}
	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public Boolean getBooRenderSubmitPanel() {
		return booRenderSubmitPanel;
	}

	public void setBooRenderSubmitPanel(Boolean booRenderSubmitPanel) {
		this.booRenderSubmitPanel = booRenderSubmitPanel;
	}
	


 

public void addRecordsToDatatable(){
	setHideSubmit(false);

	if (viewDTList.size() == 0) {

		  DocumentDatatableBean documentDatatableObj = new DocumentDatatableBean();
		  documentDatatableObj.setDocumentMasterPk(getDocumentMasterPk());
		  documentDatatableObj.setDocumentCode(getDocumentCode());
		  documentDatatableObj.setDocumentDescription( getDocumentDescription());
		  
		if (getDocumentMasterPk() != null) {
			//List<Document>  poplateDocList = idocumentService.getRecordFrmDB(getDocumentCode());
			//if(poplateDocList.get(0).getDocumentDesc().equalsIgnoreCase(getDocumentDescription())){
					
			documentDatatableObj.setDynamicActivateDeactivate(getDynamicAcivateDeactivate());
			documentDatatableObj.setBooRenderEditButton(true);
			documentDatatableObj.setIsActive(getIsActive());
			documentDatatableObj.setRemarks(getRemarks());
			documentDatatableObj.setCheckSave(getCheckSave());
			documentDatatableObj.setCreatedBy(getCreatedBy());
			documentDatatableObj.setCreatedDate(getCreatedDate());
			documentDatatableObj.setBooCheckUpdate(getBooCheckUpdate());
			//}else{
				
			//}
		} else {
			documentDatatableObj.setDynamicActivateDeactivate(Constants.REMOVE);
			documentDatatableObj.setBooRenderEditButton(true);
			documentDatatableObj.setIsActive(Constants.U);
			documentDatatableObj.setCheckSave(true);
			documentDatatableObj.setBooCheckUpdate(true);
			documentDatatableObj.setCreatedBy(session.getUserName());
			documentDatatableObj.setCreatedDate(new Date());
		}
		if (listDT.size() > 0) {
			for (DocumentDatatableBean docDTObj : listDT) {
				if (docDTObj.getDocumentCode().equals(getDocumentCode())&& docDTObj.getDocumentDescription().equals(getDocumentDescription())) {
					clearAll();
					docDTObj = null;
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("dataexist.show();");
				}

			}
			if (documentDatatableObj != null) {
				listDT.add(documentDatatableObj);
			}
		} else {
			listDT.add(documentDatatableObj);

		}

	}

	if (listDT.size() > 0) {
		if (viewDTList.size() > 0) {
			for (DocumentDatatableBean documentDt : listDT) {
				for (DocumentDatatableBean documenviewDt : viewDTList) {
					if (documentDt.getDocumentCode().equals(documenviewDt.getDocumentCode())&& documentDt.getDocumentDescription().equalsIgnoreCase(documenviewDt.getDocumentDescription())) {
						viewDTList.remove(documenviewDt);
					}
				}
			}
		}
		listDT.addAll(viewDTList);
	}

	else {
		listDT.addAll(viewDTList);

	}

	clearAll();
	setBooRenderSaveExit(true);
	setBooRenderDataTable(true);
	setHideClear(false);
	setHideEditButton(false);
	setBooRenderSubmitPanel(true);
	setHideSubmit(false);
	
}
public void duplicateCheck() {

	if (listDT.size() != 0) {
		for ( DocumentDatatableBean documentdt : listDT) {
			if (documentdt.getDocumentCode().equals(getDocumentCode())) {
				 setDocumentCode(null);
				 setDocumentDescription(null);
				 RequestContext.getCurrentInstance().execute("succ.show();");
			} else {
				if (documentdt.getDocumentDescription().equals(getDocumentDescription())) {
						setDocumentCode(null);
						setDocumentDescription(null);
						RequestContext.getCurrentInstance().execute("succ.show();");
				}

			}
		}

	}
	if (getDocumentCode()!= null&&getDocumentDescription()!=null) {
		 addRecordsToDatatable();
		 viewDTList.clear();
		 

	}

}

public void population(){
	setDocumentMasterPk(null);
 	setDocumentDescription(null);
	if(getDocumentCode()!=null){
	List<Document>  poplateDocumentList = idocumentService.getRecordFrmDB(getDocumentCode());
	if(poplateDocumentList.size()>0){
		for(Document documentObj:poplateDocumentList){
			setDocumentDescription(documentObj.getDocumentDesc());
			setDocumentMasterPk(documentObj.getDocumentID());
			setCreatedBy(documentObj.getCreatedBy());
			setCreatedDate(documentObj.getCreatedDate());
			setBooRenderEditButton(true);
			setIsActive(Constants.U);
			setModifiedBy(session.getUserName());
			setModifiedDate(new Date());
			setBooCheckUpdate(true);
			
			
		}
		
	}
	}
	}

public void saveDataTableRecords(){
	if ( listDT.isEmpty()) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("notfound.show();");
	} else {
	
	
		for(DocumentDatatableBean documentDTObj:listDT){
			try {
			Document documnetObj=new Document();
			
				documnetObj.setDocumentID(documentDTObj.getDocumentMasterPk());
				documnetObj.setDocumentCode(documentDTObj.getDocumentCode());
				documnetObj.setDocumentDesc(documentDTObj.getDocumentDescription());
				
				LanguageType languageType = new LanguageType();
				languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
				documnetObj.setFsLanguageType(languageType );
				 
			documnetObj.setIsActive(documentDTObj.getIsActive() );
			if(documentDTObj.getDocumentMasterPk()!=null){
				documnetObj.setCreatedBy(documentDTObj.getCreatedBy());
				documnetObj.setCreatedDate(documentDTObj.getCreatedDate());
				documnetObj.setModifiedBy(session.getUserName());
				documnetObj.setModifiedDate(new Date());
				}
			else{
				documnetObj.setCreatedBy(session.getUserName() );
				documnetObj.setCreatedDate(new Date());
				}
			
			if(documentDTObj.getBooCheckUpdate()){
				idocumentService.saveRecords(documnetObj);
			}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveDataTableRecords");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		}
		
		RequestContext.getCurrentInstance().execute("complete.show();");
	}
	
}

public void editRecord(DocumentDatatableBean documentDTObj){
	
	 setDocumentMasterPk(documentDTObj.getDocumentMasterPk());
	 setDocumentCode(documentDTObj.getDocumentCode());
	 setDocumentDescription(documentDTObj.getDocumentDescription());
	 setCreatedBy(documentDTObj.getCreatedBy());
	 setCreatedDate(documentDTObj.getCreatedDate());
	 setModifiedBy(documentDTObj.getModifiedBy());
	 setModifiedDate(documentDTObj.getModifiedDate());
	 setIsActive(Constants.U);
	 setDynamicAcivateDeactivate( Constants.PENDING_FOR_APPROVE);
	 setHideEditButton(true);
	 setBooCheckUpdate(true);
	 setHideClear(true);
	 setHideSubmit(true);
	 listDT.remove(documentDTObj);
	
}
		
	
	public void viewAllRecords(){
	log.info( "=================View Called==========================");
	try {
		 List<Document> documentList=idocumentService.getAllRecordsFromDB();if(documentList.size()>0){
			 for(Document documentObj:documentList){
				 DocumentDatatableBean documentDT=new DocumentDatatableBean();
				
				 documentDT.setDocumentCode(documentObj.getDocumentCode());
				 documentDT.setDocumentMasterPk(documentObj.getDocumentID());
				 documentDT.setDocumentDescription(documentObj.getDocumentDesc());
				 documentDT.setCreatedBy(documentObj.getCreatedBy());
				 documentDT.setCreatedDate(documentObj.getCreatedDate());
				 documentDT.setIsActive(documentObj.getIsActive());
				 documentDT.setModifiedBy(documentObj.getModifiedBy());
				 documentDT.setModifiedDate(documentObj.getModifiedDate());
				 documentDT.setHideEditButton(false);
				 documentDT.setBooCheckUpdate(false);
				 if(documentObj.getIsActive() !=null){
					 if(documentObj.getIsActive().equalsIgnoreCase(Constants.Yes)){
							documentDT.setDynamicActivateDeactivate(Constants.DEACTIVATE);
							documentDT.setApprovedBy(documentObj.getApprovedBy());
							documentDT.setApprovedDate( documentObj.getApprovedDate());
							documentDT.setBooCheckDelete(false);
							documentDT.setBooActivate(false);
						 }
						 else if(documentObj.getIsActive().equalsIgnoreCase(Constants.D)){
							 documentDT.setDynamicActivateDeactivate(Constants.ACTIVATE); 
							 documentDT.setRemarks(documentObj.getRemarks());
							 documentDT.setBooCheckDelete(false);
							 documentDT.setBooActivate(false);
						 }
						 else if(documentObj.getIsActive().equalsIgnoreCase(Constants.U)){
							 documentDT.setBooActivate(false);
							 if(documentObj.getModifiedBy()==null&&documentObj.getModifiedDate()==null&&documentObj.getApprovedBy()==null&&documentObj.getApprovedDate()==null&&documentObj.getRemarks()==null){
								 documentDT.setBooCheckDelete(false);
								 documentDT.setDynamicActivateDeactivate(Constants.DELETE); 
								 documentDT.setBooActivate(false);
							 }else{
								 documentDT.setBooCheckDelete(false);
								 documentDT.setDynamicActivateDeactivate(Constants.PENDING_FOR_APPROVE); 
								 documentDT.setBooActivate(false);
							     }
						 }
				 }
		
				 
				viewDTList.add(documentDT);
			 }
			 
			 
		 }
		 if(viewDTList.size()>0){
		   addRecordsToDatatable();
			viewDTList.clear(); 
		 }else{
			 RequestContext.getCurrentInstance().execute("exist.show();"); 
		 }
		 
	} catch (NullPointerException ne) {
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
	
public void updateRecord(DocumentDatatableBean documentDTObj){
	Document documnetObj=new Document();
	
	documnetObj.setDocumentID(documentDTObj.getDocumentMasterPk());
	documnetObj.setDocumentCode(documentDTObj.getDocumentCode());
	documnetObj.setDocumentDesc(documentDTObj.getDocumentDescription());
	LanguageType languageType = new LanguageType();
	languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
	documnetObj.setFsLanguageType(languageType );
	
	 
	if (documentDTObj.getDynamicActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
		documnetObj.setIsActive(Constants.U);
	} else if (documentDTObj.getDynamicActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
		documnetObj.setIsActive(Constants.D);
		documnetObj.setRemarks(documentDTObj.getRemarks());

	}

if(documentDTObj.getDocumentMasterPk()!=null){
	documnetObj.setCreatedBy(documentDTObj.getCreatedBy());
	documnetObj.setCreatedDate(documentDTObj.getCreatedDate());
	documnetObj.setModifiedBy(session.getUserName());
	documnetObj.setModifiedDate(new Date());
	documentDTObj.setRemarks(documentDTObj.getRemarks());
	}
 try {
	 idocumentService.saveRecords(documnetObj);
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
public void deleteRecordFromDB(DocumentDatatableBean documentDatatableBean){
		try {
			idocumentService.delete(documentDatatableBean.getDocumentMasterPk());
			listDT.remove(documentDatatableBean);
			
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::deleteRecordFromDB");
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
	if(listDT.size()>0){
		for (DocumentDatatableBean   datatableObj : listDT) {
			if(datatableObj.getBooCheckDelete()){
			deleteRecordFromDB(datatableObj);
		
			listDT.clear();
			viewAllRecords();
			listDT.addAll(viewDTList);
			}
			if(listDT.size()<=0){
			setBooRenderDataTable(false);
			setBooRenderSubmitPanel(false);
			}
		}
	}
 }
public void removeRecords(DocumentDatatableBean documentDTObj){
	log.info("Entered into removeRecord() method");
	if (documentDTObj.getDocumentMasterPk() == null) {
	  if (documentDTObj.getCheckSave().equals(true)) {
		 listDT.remove(documentDTObj);
		if (listDT.size() <= 0) {
			setBooRenderDataTable(false);
			setBooRenderSubmitPanel(false);
		}
	  }

	} else {
		if (documentDTObj.getIsActive().equalsIgnoreCase(Constants.U)) {
		}

		else {
			updateRecord(documentDTObj);
			listDT.clear();
			viewAllRecords();
			listDT.addAll(viewDTList);
		}
	}
	//listDT.remove(documentDTObj);
	if (listDT.size() == 0) {
		setBooRenderDataTable(false);
		setBooRenderSubmitPanel(false);
	}
	
	
	
}

	
	public void checkStatusType(DocumentDatatableBean documentDTObj){
		if(documentDTObj.getDynamicActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)){
			setApprovedBy(documentDTObj.getApprovedBy());
			setApprovedDate( documentDTObj.getApprovedDate());
			documentDTObj.setRemarkCheck(true);
			RequestContext.getCurrentInstance().execute("remarks.show();");
		}else if(documentDTObj.getDynamicActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)){
			//removeRecords(documentDTObj);
			documentDTObj.setBooActivate(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
		}else if(documentDTObj.getDynamicActivateDeactivate().equalsIgnoreCase(Constants.DELETE)){
			documentDTObj.setBooCheckDelete(true); 
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");			
		}else if(documentDTObj.getDynamicActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
			//documentDTObj.setBooCheckDelete(true); 
			RequestContext.getCurrentInstance().execute("pending.show();");			
		}else if(documentDTObj.getDynamicActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
			removeRecords(documentDTObj);
		}
		
	}
	
	public void activateRecord(){
		if(listDT.size()>0){
			for(DocumentDatatableBean  docMasterObj:listDT){
				if(docMasterObj.getBooActivate()){
					updateRecord(docMasterObj);
					listDT.clear();
					viewAllRecords();
					listDT.addAll(viewDTList);
				
				}
			}
			
			
		}
		
		
	}
	
	
	
	
	public void remarkSelectedRecord() throws IOException {
		log.info("Entered into remarkSelectedRecord() method");
		for (DocumentDatatableBean documentMasterdtObj : listDT) {
			if (documentMasterdtObj.getRemarkCheck() != null) {
				if (documentMasterdtObj.getRemarkCheck().equals(true)) {
					documentMasterdtObj.setRemarks(getRemarks());
					removeRecords(documentMasterdtObj);
					setRemarks(null);
				}
			}
		}

	}

	public void cancel() {
		 
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../common/documentapproval.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::cancel");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
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

	public void clickOnOk() {
		setDocumentCode(null);
		setDocumentDescription(null);
		setDocumentMasterPk(null);
		log.info( "Entered into clickOnOk() method ");
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../common/documentapproval.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::clickOnOk");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	
	public List<String> autoCompleteData(String query){
		if (query.length() > 0) {
			return idocumentService.getAllComponent(query);
		}
			 else {
			return null;
		}
		
	}
	
	
	public void callSaveMethod(){
		 clearAll();
		 listDT.clear();
		 setBooRenderDataTable(false);
		 setBooRenderSubmitPanel(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../common/documentmaster.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::callSaveMethod");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}
	
	public void clearAll(){
			
		setDocumentCode(null);
		setDocumentMasterPk(null); 
		setDocumentDescription(null);
		
		setModifiedBy(null);
		setModifiedDate(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setRemarks(null);
		
	}
	
	
	public void approveRecord(){
	 String approveMsg=idocumentService.approveRecord(getDocumentMasterPk(),session.getUserName());
	 if(approveMsg.equalsIgnoreCase("Success")){
		idocumentService.approveRecord(getDocumentMasterPk(),session.getUserName());
		RequestContext.getCurrentInstance().execute("approv.show();");
	 }
	 else{
		 RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
	 }
	}
	
	
	
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigationToDocument(){
		clearAll();
		viewDTList.clear();
		listDT.clear();
		setReadOnly(false);
		setBooApprovePanel(false);
		setBooRenderSaveExit(true);
		setBooRenderApproval(false);
		setBooRenderDataTable(false);
		setBooRenderSubmitPanel(false);
		setHideEditButton(false);
		 
		setHideClear(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "documentmaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/documentmaster.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::pageNavigationToDocument");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	
	
	public void exit() throws IOException{
		
	if (session.getRoleId().equalsIgnoreCase("1")) {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
	} else {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
	}
}

	public Boolean getCheckSave() {
		return checkSave;
	}

	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
	}

	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}

	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}

	public String getDynamicAcivateDeactivate() {
		return dynamicAcivateDeactivate;
	}

	public void setDynamicAcivateDeactivate(String dynamicAcivateDeactivate) {
		this.dynamicAcivateDeactivate = dynamicAcivateDeactivate;
	}

	public Boolean getBooApprovePanel() {
		return booApprovePanel;
	}

	public void setBooApprovePanel(Boolean booApprovePanel) {
		this.booApprovePanel = booApprovePanel;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public void cancelRemarks(){
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/documentmaster.xhtml");
		} catch (NullPointerException ne) {
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

	

	
	
	
	
	

}
