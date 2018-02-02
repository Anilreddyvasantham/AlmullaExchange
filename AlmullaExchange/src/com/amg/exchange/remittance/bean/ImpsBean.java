package com.amg.exchange.remittance.bean;

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

import com.amg.exchange.common.dto.BankMasterDTO;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.Imps;
import com.amg.exchange.remittance.service.IImpsService;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("impsBean")
@Scope("session")
public class ImpsBean<T> implements Serializable {
 
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(ImpsBean.class);
	private SessionStateManage session=new SessionStateManage();
	
	private BigDecimal beneBankId;
	private BigDecimal corrspBankId;
	private String correspBank;
	private String beneBank;
	private BigDecimal impsPk; 
	private String createdBy;
	

	private String modifiedBy;
	private Date createdDate;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private Boolean disableBank;
	private Boolean booRenderDataTable;
	private Boolean booSubmitPanel;
	private Boolean booRenderViewPanel;
	private Boolean booApprovePanel;
	private  String errorMessage;
	
	
	private List<BankMasterDTO> corrBankList;
	private List<BankMasterDTO> beneBankList;
	private List<ImpsDataTableBean> impsDataTableList=new CopyOnWriteArrayList<ImpsDataTableBean>();
	 private List<ImpsDataTableBean> impsViewList=new CopyOnWriteArrayList<ImpsDataTableBean>();
	 private List<ImpsDataTableBean> impsNewList=new  CopyOnWriteArrayList<ImpsDataTableBean>();
	
	@Autowired 
	IGeneralService   generalService;
	
	@	Autowired 
	IImpsService  iimpsService;
	
	public Boolean getBooApprovePanel() {
		return booApprovePanel;
	}
	public void setBooApprovePanel(Boolean booApprovePanel) {
		this.booApprovePanel = booApprovePanel;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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
	
	public BigDecimal getBeneBankId() {
		return beneBankId;
	}
	public void setBeneBankId(BigDecimal beneBankId) {
		this.beneBankId = beneBankId;
	}
	public BigDecimal getCorrspBankId() {
		return corrspBankId;
	}
	public void setCorrspBankId(BigDecimal corrspBankId) {
		this.corrspBankId = corrspBankId;
	}
	public List<BankMasterDTO> getCorrBankList() {
		return corrBankList;
	}
	public void setCorrBankList(List<BankMasterDTO> corrBankList) {
		this.corrBankList = corrBankList;
	}
	public List<BankMasterDTO> getBeneBankList() {
		return beneBankList;
	}
	public void setBeneBankList(List<BankMasterDTO> beneBankList) {
		this.beneBankList = beneBankList;
	}
	public BigDecimal getImpsPk() {
		return impsPk;
	}
	public void setImpsPk(BigDecimal impsPk) {
		this.impsPk = impsPk;
	}
	public List<ImpsDataTableBean> getImpsViewList() {
		return impsViewList;
	}
	public void setImpsViewList(List<ImpsDataTableBean> impsViewList) {
		this.impsViewList = impsViewList;
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
	public void clearRemarks(){
		setRemarks(null);
	}
	public Boolean getDisableBank() {
		return disableBank;
	}
	public void setDisableBank(Boolean disableBank) {
		this.disableBank = disableBank;
	}
	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}
	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}
	public Boolean getBooSubmitPanel() {
		return booSubmitPanel;
	}
	public void setBooSubmitPanel(Boolean booSubmitPanel) {
		this.booSubmitPanel = booSubmitPanel;
	}
	public Boolean getBooRenderViewPanel() {
		return booRenderViewPanel;
	}
	public void setBooRenderViewPanel(Boolean booRenderViewPanel) {
		this.booRenderViewPanel = booRenderViewPanel;
	}
	public String getCorrespBank() {
		return correspBank;
	}
	public void setCorrespBank(String correspBank) {
		this.correspBank = correspBank;
	}
	public String getBeneBank() {
		return beneBank;
	}
	public void setBeneBank(String beneBank) {
		this.beneBank = beneBank;
	}
	public List<ImpsDataTableBean> getImpsNewList() {
		return impsNewList;
	}
	public void setImpsNewList(List<ImpsDataTableBean> impsNewList) {
		this.impsNewList = impsNewList;
	}	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigationToImps(){
		setDisableBank(false);
		setBooRenderViewPanel(true);
		setBooSubmitPanel(false);
		setBooRenderDataTable(false);
		setBooApprovePanel(false);
		impsNewList.clear();
		
		clearAll();
		List<BankMasterDTO>  corrbanksList= generalService.getCoresBankListForApplicationCountry(session.getCountryId());
		setCorrBankList(corrbanksList);
		List<BankMasterDTO>  benebanksList= generalService.getBeneBankListForApplicationCountry(session.getCountryId());
		setBeneBankList(benebanksList);
		
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "impsmaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/impsmaster.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to  IMPS");
		}
		
	}
	public void remarkSelectedRecord(){
		for(ImpsDataTableBean  impsDTOBj:impsDataTableList){
			if(impsDTOBj.getRemarkCheck()!=null){
			if(impsDTOBj.getRemarkCheck().equals(true)){
				if(getRemarks()!=null){
					impsDTOBj.setRemarks(getRemarks());
					updateRecord(impsDTOBj);
					viewAll();
					setRemarks(null);
							}else{
					RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
				}
			}
			}
		}
		}
	
	
	public void clearAll(){
		impsDataTableList.clear();
		impsViewList.clear();
	
		setCorrspBankId(null);
		setBeneBankId(null);
		setCreatedDate(null);
		setCreatedBy(null);
		setImpsPk(null);
		setModifiedBy(null);
		setModifiedDate(null);
	}
	public void exitButton(){
		impsViewList.clear();
		log.info( "======exit method called===============");
		try {
			if (session.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../registration/branchhome.xhtml");
			}
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		
		}	

	}
	
 
 public List<ImpsDataTableBean> getImpsDataTableList() {
		return impsDataTableList;
	}
	public void setImpsDataTableList(List<ImpsDataTableBean> impsDataTableList) {
		this.impsDataTableList = impsDataTableList;
	}
	
	public void addRecords(){
		try{
		ImpsDataTableBean impsDataTableObj=new ImpsDataTableBean();
		
		impsDataTableObj.setBeneBankId(getBeneBankId());
		impsDataTableObj.setBeneBankCode( generalService.getBankCode( getBeneBankId()));
		impsDataTableObj.setBeneBankDesc(generalService.getBankName(getBeneBankId()) );
		
		impsDataTableObj.setCorrespBankId(getCorrspBankId() );
		impsDataTableObj.setCorrspBankCode( generalService.getBankCode( getCorrspBankId()));
		impsDataTableObj.setCorrespBankDesc(generalService.getBankName( getCorrspBankId()) );
		impsDataTableObj.setImpsPk(getImpsPk());
		
		if(getImpsPk()!=null){
			impsDataTableObj.setCreatedBy(getCreatedBy());
			impsDataTableObj.setCreatedDate( getCreatedDate());
			impsDataTableObj.setModifiedBy(session.getUserName() );
			impsDataTableObj.setModifiedDate( new Date());
			impsDataTableObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE );
			impsDataTableObj.setIsActive(Constants.U );
			impsDataTableObj.setApprovedBy(null);
			impsDataTableObj.setApprovedDate( null);
 			impsDataTableObj.setBooUpdate(false);
		}else{
			impsDataTableObj.setCreatedBy(session.getUserName());
			impsDataTableObj.setCreatedDate( new Date());
 			impsDataTableObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE );
 			impsDataTableObj.setIsActive(Constants.U );
 			impsDataTableObj.setCheckSave(true);
 			impsDataTableObj.setBooUpdate(true);
 			impsViewList.add(impsDataTableObj );
		}
		
		impsDataTableList.add( impsDataTableObj);
		setBooSubmitPanel(true);
		setBooRenderDataTable( true);
		setBeneBankId( null);
		
		}catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		
		}	
	}
	public void addToDataTable(){
		try{
		log.info( ":::::::::::::::::::::::::::::entered into add to data table::::::::::::::::::::");
		List<Imps>   impsDuplicateCheckList = iimpsService.getImpsRecordsBasedOnBankIds(getCorrspBankId(), getBeneBankId());
	
		if(impsDuplicateCheckList.size()>0){
			setBeneBankId(null);
	 		RequestContext.getCurrentInstance().execute("recordAlreadyExist.show();");
			return;
		}else if(impsDataTableList.size()!=0){
			for (ImpsDataTableBean  impsDTObj : impsDataTableList) {
				if(impsDTObj.getCorrespBankId().toString().equalsIgnoreCase(getCorrspBankId().toString())){
 				setBeneBankId(null);
 				RequestContext.getCurrentInstance().execute("recordAlreadyExist.show();");
				return;
				} 
			}
		}	
		addRecords();
		 
		}catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			}	
	}
	
	public void viewAll(){
	try{
		//impsViewList.clear();
		impsDataTableList.clear();
		
		List<Imps> viewList  =iimpsService.getAllRecordsFromDB();
		if(viewList.size()>0){
		
			setBooRenderDataTable( true);
			setBooSubmitPanel(true);
			for(Imps impsObj:viewList){
				ImpsDataTableBean impsDtObj=new ImpsDataTableBean();
				impsDtObj.setImpsPk(impsObj.getImpsId() );
				impsDtObj.setBeneBankId(impsObj.getBeneBankId().getBankId() );
				impsDtObj.setBeneBankDesc( generalService.getBankName(impsObj.getBeneBankId().getBankId() ));
				impsDtObj.setBeneBankCode(impsObj.getBeneBankCode() );
				impsDtObj.setCorrespBankId(impsObj.getCorrespondingBankId().getBankId() );
				impsDtObj.setCorrespBankDesc( generalService.getBankName(impsObj.getCorrespondingBankId().getBankId()) );
				impsDtObj.setCorrspBankCode(impsObj.getCorrespodentBankCode() );
				impsDtObj.setApplicationCountryId(impsObj.getApplicationCountryId().getCountryId());
				
				
				impsDtObj.setCreatedBy(impsObj.getCreatedBy() );
				impsDtObj.setCreatedDate(impsObj.getCreatedDate());
				impsDtObj.setModifiedBy(impsObj.getModifiedBy() );
				impsDtObj.setModifiedDate(impsObj.getModifiedDate());
				impsDtObj.setBooUpdate( false);
				 if(impsObj.getIsActive().equalsIgnoreCase( Constants.Yes)){
					impsDtObj.setApprovedBy(impsObj.getApprovedBy());
					impsDtObj.setApprovedDate(impsObj.getApprovedDate());
					impsDtObj.setDynamicLabelForActivateDeactivate( Constants.DEACTIVATE);
				} else if(impsObj.getIsActive().equalsIgnoreCase( Constants.D)){
					impsDtObj.setDynamicLabelForActivateDeactivate( Constants.ACTIVATE);
					impsDtObj.setRemarks(impsObj.getRemarks());
					}else if(impsObj.getIsActive().equalsIgnoreCase( Constants.U)){
						if(impsObj.getModifiedBy()==null&&impsObj.getModifiedDate()==null&&impsObj.getApprovedBy()==null&&impsObj.getApprovedDate()==null&&impsObj.getRemarks()==null){
								impsDtObj.setDynamicLabelForActivateDeactivate( Constants.DELETE);
						}else{
								impsDtObj.setDynamicLabelForActivateDeactivate( Constants.PENDING_FOR_APPROVE);
						}
						
					}
				 
				 impsDataTableList.add(impsDtObj );
			}
				impsDataTableList.addAll( impsViewList);
			 
 
			
		}else{
			RequestContext.getCurrentInstance().execute("norecordsfound.show();");
		}
	}catch(NullPointerException  e){
		setErrorMessage("Method Name:viewAll"+e.getMessage()); 
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}catch (Exception e) {
		setErrorMessage(e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");
	
	}		
	}
	
	public void saveAllRecords(){
	try{
		if (impsDataTableList.isEmpty()) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("norecordsfound.show();");
		} else {
			for(ImpsDataTableBean impsDTObj:impsDataTableList){
			
				Imps impsObj=new Imps();
				impsObj.setImpsId( impsDTObj.getImpsPk());
				impsObj.setBeneBankCode(impsDTObj.getBeneBankCode() );
				impsObj.setCorrespodentBankCode(impsDTObj.getCorrspBankCode() );
				impsObj.setCreatedBy(session.getUserName() );
				impsObj.setCreatedDate(new Date());
				
				BankMaster bankMaster=new BankMaster();
				bankMaster.setBankId(impsDTObj.getBeneBankId());
				impsObj.setBeneBankId(bankMaster);
				
				BankMaster bankMasterCorrsp=new BankMaster();
				bankMasterCorrsp.setBankId(impsDTObj.getCorrespBankId());
				impsObj.setCorrespondingBankId(bankMasterCorrsp);
			
				CountryMaster countryMasterObj=new CountryMaster();
				countryMasterObj.setCountryId( session.getCountryId());
				impsObj.setApplicationCountryId(countryMasterObj);
				
				impsObj.setIsActive(Constants.U);
				if(impsDTObj.getBooUpdate()){
				iimpsService.saveOrUpdate(impsObj );
				}
					

			}
			RequestContext.getCurrentInstance().execute("complete.show();");
		}
 
	}catch(NullPointerException  e){
		setErrorMessage("Method Name:saveAllRecords"+e.getMessage()); 
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}catch (Exception e) {
		setErrorMessage(e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");
	
	}		
	}
	public void updateRecord(ImpsDataTableBean impsDTObj){
	try{	
		Imps impsObj=new Imps();
		impsObj.setImpsId(impsDTObj.getImpsPk() );
		impsObj.setBeneBankCode(impsDTObj.getBeneBankCode() );
		impsObj.setCorrespodentBankCode(impsDTObj.getCorrspBankCode() );
		impsObj.setCreatedBy( impsDTObj.getCreatedBy() );
		impsObj.setCreatedDate( impsDTObj.getCreatedDate());
		impsObj.setModifiedBy( session.getUserName());
		impsObj.setModifiedDate( new Date());
		
		BankMaster bankMaster=new BankMaster();
		bankMaster.setBankId(impsDTObj.getBeneBankId());
		impsObj.setBeneBankId(bankMaster);
		
		BankMaster bankMasterCorrsp=new BankMaster();
		bankMasterCorrsp.setBankId(impsDTObj.getCorrespBankId());
		impsObj.setCorrespondingBankId(bankMasterCorrsp);
		
		CountryMaster countryMasterObj=new CountryMaster();
		countryMasterObj.setCountryId( impsDTObj.getApplicationCountryId());
		impsObj.setApplicationCountryId(countryMasterObj);
	
		if (impsDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			impsObj.setIsActive(Constants.U);
		} else if (impsDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			impsObj.setIsActive(Constants.D);
			impsObj.setRemarks(impsDTObj.getRemarks());

		}
		 
		iimpsService.saveOrUpdate(impsObj );
	}catch(NullPointerException  e){
		setErrorMessage("Method Name:updateRecord"+e.getMessage()); 
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}catch (Exception e) {
		setErrorMessage(e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");
	
	}			
	}

	public void checkStatusType(ImpsDataTableBean impsDtObj){
	try{
		if(impsDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
		RequestContext.getCurrentInstance().execute("pending.show();");
		return;
	}
	else if(impsDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
		impsDataTableList.remove( impsDtObj);
	}
	else if(impsDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)){
			setApprovedBy(impsDtObj.getApprovedBy());
			setApprovedDate( impsDtObj.getApprovedDate());
			impsDtObj.setRemarkCheck(true);
			RequestContext.getCurrentInstance().execute("remarks.show();");
		}else if(impsDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)){
	 
			impsDtObj.setActivateRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
		}else if(impsDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)&&impsDtObj.getModifiedBy()==null&&impsDtObj.getModifiedDate()==null&&impsDtObj.getRemarks()==null&&impsDtObj.getApprovedBy()==null&&impsDtObj.getApprovedDate()==null){
			impsDtObj.setPermanentDeleteCheck( true); 
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");			
		}else if(impsDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
				removeRecords(impsDtObj);
		}
	}catch(NullPointerException  e){
		setErrorMessage("Method Name:saveAllRecords"+e.getMessage()); 
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}
		
	}
	
	public void confirmPermanentDelete(){
		if(impsDataTableList.size()>0){
			for(ImpsDataTableBean impsDatatableObj:impsDataTableList){
			if(impsDatatableObj.getPermanentDeleteCheck()!=null){
			if(impsDatatableObj.getPermanentDeleteCheck().equals(true)){
				deleteRecordPermanently(impsDatatableObj);
				impsDataTableList.remove(impsDatatableObj);
			}
			}
	}
		}
	}
	
	public void deleteRecordPermanently(ImpsDataTableBean  impsDtObj){
		iimpsService.delete(impsDtObj.getImpsPk()) ;
			
		}
	public void confirmActivate(ImpsDataTableBean   datatableObj){
	try{
	iimpsService.activateRecord(datatableObj.getImpsPk(), session.getUserName());
	viewAll();
	}catch(Exception e){
		setErrorMessage("Method Name:confirmActivate"+e.getMessage()); 
		RequestContext.getCurrentInstance().execute("csp.show();");
	}
}
public void activateRecord(){
	if(impsDataTableList.size()>0){
	for(ImpsDataTableBean impsDatatableObj:impsDataTableList){
		if(impsDatatableObj.getActivateRecordCheck()!=null){
		if(impsDatatableObj.getActivateRecordCheck().equals(true)){
			confirmActivate(impsDatatableObj);
		}
		}
	}
	}
} 

	
	public void removeRecords(ImpsDataTableBean impsDtObj){
	try{
		log.info("Entered into removeRecords() method");
		if (impsDtObj.getImpsPk() == null) {
		  if (impsDtObj.getCheckSave().equals(true)) {
			  impsDataTableList.remove(impsDtObj);
			if (impsDataTableList.size() <= 0) {
				//setBooRenderDataTable(false);
				//setBooRenderSubmitPanel(false);
			}
		  }

		} else {
			if (impsDtObj.getIsActive().equalsIgnoreCase(Constants.U)) {
			}

			else {
				updateRecord(impsDtObj);
				impsDataTableList.clear();
				viewAll();
				impsDataTableList.addAll(impsViewList);
			}
		}
	}catch(Exception  e){
		setErrorMessage("Method Name:saveAllRecords"+e.getMessage()); 
		RequestContext.getCurrentInstance().execute("csp.show();");
	}
	}
	
	public void approveRecord(){
		try{
		 String approveMsg=iimpsService.approveRecord(getImpsPk(),session.getUserName());
		 if(approveMsg.equalsIgnoreCase("Success")){
			 iimpsService.approveRecord(getImpsPk(),session.getUserName());
			RequestContext.getCurrentInstance().execute("approv.show();");
		 }
		 else{
			 RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
		 }
		}catch(Exception  e){
			setErrorMessage("Method Name:approveRecord"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		}
	
	
	
	
	
	
	
	
	
	

	
	
	
	
}
