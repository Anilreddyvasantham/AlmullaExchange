package com.amg.exchange.common.bean;

/*
 * Author Rahamathali Shaik
*/

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


//import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.dto.BankAccountTypeDTO;
//import com.amg.exchange.common.model.BankAccountType;
//import com.amg.exchange.common.model.BankAccountTypeDesc;
//import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IBankAccountTypeService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
//import com.sun.org.apache.bcel.internal.generic.NEW;


@Component("bankAccountTypeBean")
@Scope("session")
public class BankAccountTypeBean<T> {

	private static final Logger log=Logger.getLogger(BankAccountTypeBean.class);
	private SessionStateManage session=new SessionStateManage();
	private String accountTypeCode;
	private String accountTypeDescLocal;
	private String accountTypeDesc;
	private BigDecimal accountTypePk;
	private BigDecimal accountTypeDescPk;
	private BigDecimal accountTypeLocalDescPk;
	private BigDecimal engLanguageId;
	private BigDecimal arbLanguageId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private String remarks;
	private String dynamicLabelForActivateDeactivate;
	private String accountTypeCodeForDailog;
	private BigDecimal countNoOfSave;
	private int count=0;
	private BigDecimal selectType;
	
	
	private Boolean renderSaveButton=false;
	private Boolean renderDataTable=false;
	private Boolean readOnlyAccountTypeCode=false;
	private Boolean readOnlyAccountTypeDescLocal=false;
	private Boolean readOnlyAccountTypeDesc=false;
	private Boolean renderSavePanel=false;
	private Boolean renderUpdatePanel=false;
	private Boolean disableSubmitButton=false;
	private Boolean disableClearButton=false;
	private Boolean ifEditClicked=false;
	private Boolean disableEditButton=false;
	
	private String errorMessage;
	
	
	
	
	@Autowired
	IBankAccountTypeService bankAccountTypeService;
	
	
	private List<BankAccountTypeBeanDataTable> bankAccountTypeList=new CopyOnWriteArrayList<BankAccountTypeBeanDataTable>();
	private List<BankAccountTypeBeanDataTable> bankAccountTypeNewList=new CopyOnWriteArrayList<BankAccountTypeBeanDataTable>();
	private List<BankAccountTypeDTO> saveRecordList = new CopyOnWriteArrayList<BankAccountTypeDTO>();

	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Boolean getDisableEditButton() {
		return disableEditButton;
	}
	public void setDisableEditButton(Boolean disableEditButton) {
		this.disableEditButton = disableEditButton;
	}
	public BigDecimal getSelectType() {
		return selectType;
	}
	public void setSelectType(BigDecimal selectType) {
		this.selectType = selectType;
	}
	public BigDecimal getCountNoOfSave() {
		return countNoOfSave;
	}
	public void setCountNoOfSave(BigDecimal countNoOfSave) {
		this.countNoOfSave = countNoOfSave;
	}
	public Boolean getIfEditClicked() {
		return ifEditClicked;
	}
	public void setIfEditClicked(Boolean ifEditClicked) {
		this.ifEditClicked = ifEditClicked;
	}
	public Boolean getDisableClearButton() {
		return disableClearButton;
	}
	public void setDisableClearButton(Boolean disableClearButton) {
		this.disableClearButton = disableClearButton;
	}
	public String getAccountTypeCodeForDailog() {
		return accountTypeCodeForDailog;
	}
	public void setAccountTypeCodeForDailog(String accountTypeCodeForDailog) {
		this.accountTypeCodeForDailog = accountTypeCodeForDailog;
	}
	public Boolean getDisableSubmitButton() {
		return disableSubmitButton;
	}
	public void setDisableSubmitButton(Boolean disableSubmitButton) {
		this.disableSubmitButton = disableSubmitButton;
	}
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public BigDecimal getEngLanguageId() {
		return engLanguageId;
	}
	public void setEngLanguageId(BigDecimal engLanguageId) {
		this.engLanguageId = engLanguageId;
	}
	public BigDecimal getArbLanguageId() {
		return arbLanguageId;
	}
	public void setArbLanguageId(BigDecimal arbLanguageId) {
		this.arbLanguageId = arbLanguageId;
	}
	public BigDecimal getAccountTypeLocalDescPk() {
		return accountTypeLocalDescPk;
	}
	public void setAccountTypeLocalDescPk(BigDecimal accountTypeLocalDescPk) {
		this.accountTypeLocalDescPk = accountTypeLocalDescPk;
	}
	public Boolean getRenderSavePanel() {
		return renderSavePanel;
	}
	public void setRenderSavePanel(Boolean renderSavePanel) {
		this.renderSavePanel = renderSavePanel;
	}
	public Boolean getRenderUpdatePanel() {
		return renderUpdatePanel;
	}
	public void setRenderUpdatePanel(Boolean renderUpdatePanel) {
		this.renderUpdatePanel = renderUpdatePanel;
	}
	public Boolean getReadOnlyAccountTypeCode() {
		return readOnlyAccountTypeCode;
	}
	public void setReadOnlyAccountTypeCode(Boolean readOnlyAccountTypeCode) {
		this.readOnlyAccountTypeCode = readOnlyAccountTypeCode;
	}
	public Boolean getReadOnlyAccountTypeDescLocal() {
		return readOnlyAccountTypeDescLocal;
	}
	public void setReadOnlyAccountTypeDescLocal(Boolean readOnlyAccountTypeDescLocal) {
		this.readOnlyAccountTypeDescLocal = readOnlyAccountTypeDescLocal;
	}
	public Boolean getReadOnlyAccountTypeDesc() {
		return readOnlyAccountTypeDesc;
	}
	public void setReadOnlyAccountTypeDesc(Boolean readOnlyAccountTypeDesc) {
		this.readOnlyAccountTypeDesc = readOnlyAccountTypeDesc;
	}
	public Boolean getRenderSaveButton() {
		return renderSaveButton;
	}
	public void setRenderSaveButton(Boolean renderSaveButton) {
		this.renderSaveButton = renderSaveButton;
	}
	public Boolean getRenderDataTable() {
		return renderDataTable;
	}
	public void setRenderDataTable(Boolean renderDataTable) {
		this.renderDataTable = renderDataTable;
	}
	public List<BankAccountTypeBeanDataTable> getBankAccountTypeList() {
		return bankAccountTypeList;
	}
	public void setBankAccountTypeList(
			List<BankAccountTypeBeanDataTable> bankAccountTypeList) {
		this.bankAccountTypeList = bankAccountTypeList;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public BigDecimal getAccountTypePk() {
		return accountTypePk;
	}
	public void setAccountTypePk(BigDecimal accountTypePk) {
		this.accountTypePk = accountTypePk;
	}
	public BigDecimal getAccountTypeDescPk() {
		return accountTypeDescPk;
	}
	public void setAccountTypeDescPk(BigDecimal accountTypeDescPk) {
		this.accountTypeDescPk = accountTypeDescPk;
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
	public String getAccountTypeCode() {
		return accountTypeCode;
	}
	public void setAccountTypeCode(String accountTypeCode) {
		this.accountTypeCode = accountTypeCode;
	}
	public String getAccountTypeDescLocal() {
		return accountTypeDescLocal;
	}
	public void setAccountTypeDescLocal(String accountTypeDescLocal) {
		this.accountTypeDescLocal = accountTypeDescLocal;
	}
	public String getAccountTypeDesc() {
		return accountTypeDesc;
	}
	public void setAccountTypeDesc(String accountTypeDesc) {
		this.accountTypeDesc = accountTypeDesc;
	}

	
public void addRecords(){

	setDisableSubmitButton(false);
	setDisableClearButton(false);
	setDisableEditButton(false);
	BankAccountTypeBeanDataTable bankAccountTypeDTObj=new BankAccountTypeBeanDataTable();
	bankAccountTypeDTObj.setAccountTypeCode(getAccountTypeCode());
	bankAccountTypeDTObj.setAccountTypeDesc(getAccountTypeDesc());
	bankAccountTypeDTObj.setAccountTypeDescLocal(getAccountTypeDescLocal());
	bankAccountTypeDTObj.setAccountTypePk(getAccountTypePk());
	bankAccountTypeDTObj.setAccountTypeDescPk(getAccountTypeDescPk());
	bankAccountTypeDTObj.setAccountTypeLocalDescPk(getAccountTypeLocalDescPk());
	bankAccountTypeDTObj.setEngLanguageId(getEngLanguageId());
	bankAccountTypeDTObj.setArbLanguageId(getArbLanguageId());
	if(getAccountTypePk()!=null){
		bankAccountTypeDTObj.setCreatedBy(getCreatedBy());
		bankAccountTypeDTObj.setCreatedDate(getCreatedDate());
		bankAccountTypeDTObj.setModifiedBy(session.getUserName());
		bankAccountTypeDTObj.setModifiedDate(new Date());
		bankAccountTypeDTObj.setApprovedBy(null);
		bankAccountTypeDTObj.setApprovedDate(null);
		bankAccountTypeDTObj.setIsActive(Constants.U);
		bankAccountTypeDTObj.setRemarks(null);
		bankAccountTypeDTObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
		bankAccountTypeDTObj.setIfEditClicked(getIfEditClicked());
	}else{
		bankAccountTypeDTObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
		bankAccountTypeDTObj.setCreatedBy(session.getUserName());
		bankAccountTypeDTObj.setCreatedDate(new Date());
		bankAccountTypeDTObj.setIsActive(Constants.U);
		bankAccountTypeDTObj.setIfEditClicked(true);
		bankAccountTypeNewList.add(bankAccountTypeDTObj);
	}
	
	bankAccountTypeList.add(bankAccountTypeDTObj);
	clearAllFields();
	setRenderSaveButton(true);
	setRenderDataTable(true);
	

}


	public void addRecordsToDataTable(){
		List<BankAccountTypeDTO> bankAccountDuplicateCheckList=bankAccountTypeService.fetchAccTypeCodeRecord(getAccountTypeCode());
			if(bankAccountDuplicateCheckList.size()>0 && getIfEditClicked().equals(false)){
				setAccountTypeCodeForDailog(getAccountTypeCode());
				setAccountTypeCode(null);
				clearAllFields();
				RequestContext.getCurrentInstance().execute("recordAlreadyExist.show();");
				setDisableSubmitButton(false);
				setDisableClearButton(false);
				setDisableEditButton(false);
				return;
			}else if(bankAccountTypeList.size()!=0){
				for (BankAccountTypeBeanDataTable bankAccountTypeDTObj:bankAccountTypeList) {
					if(bankAccountTypeDTObj.getAccountTypeCode().equalsIgnoreCase(getAccountTypeCode())){
						setAccountTypeCodeForDailog(getAccountTypeCode());
						setAccountTypeCode(null);
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
		
	}
		
	
	public void editRecord(BankAccountTypeBeanDataTable bankAccountTypeDTObj){
		setDisableSubmitButton(true);
		setDisableClearButton(true);
		setDisableEditButton(true);
		setAccountTypeCode(bankAccountTypeDTObj.getAccountTypeCode());
		setAccountTypeDescLocal(bankAccountTypeDTObj.getAccountTypeDescLocal());
		setAccountTypeDesc(bankAccountTypeDTObj.getAccountTypeDesc());
		setAccountTypePk(bankAccountTypeDTObj.getAccountTypePk());
		setAccountTypeDescPk(bankAccountTypeDTObj.getAccountTypeDescPk());
		setAccountTypeLocalDescPk(bankAccountTypeDTObj.getAccountTypeLocalDescPk());
		setEngLanguageId(bankAccountTypeDTObj.getEngLanguageId());
		setArbLanguageId(bankAccountTypeDTObj.getArbLanguageId());
		setCreatedBy(bankAccountTypeDTObj.getCreatedBy());
		setCreatedDate(bankAccountTypeDTObj.getCreatedDate());
		if(bankAccountTypeDTObj.getAccountTypeDescPk()!=null){
			setModifiedBy(session.getUserName());
			setModifiedDate(new Date());
			setIsActive(Constants.U);
			setApprovedBy(null);
			setApprovedDate(null);
			setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
		}else{
			setModifiedBy(bankAccountTypeDTObj.getModifiedBy());
			setModifiedDate(bankAccountTypeDTObj.getModifiedDate());
			setIsActive(bankAccountTypeDTObj.getIsActive());
			setApprovedBy(bankAccountTypeDTObj.getApprovedBy());
			setApprovedDate(bankAccountTypeDTObj.getApprovedDate());
			setDynamicLabelForActivateDeactivate(bankAccountTypeDTObj.getDynamicLabelForActivateDeactivate());
		}
		
		
		setRemarks(bankAccountTypeDTObj.getRemarks());
		setIfEditClicked(true);
		bankAccountTypeList.remove(bankAccountTypeDTObj);
		bankAccountTypeNewList.remove(bankAccountTypeDTObj);
		if(bankAccountTypeList.size()==0){
			setRenderSaveButton(false);
			setRenderDataTable(false);
		}
	}
	
	public void clearAllFields(){
		
		setAccountTypeCode(null);
		setAccountTypeDescLocal(null);
		setAccountTypeDesc(null);
		setAccountTypePk(null);
		setAccountTypeDescPk(null);
		setAccountTypeLocalDescPk(null);
		setEngLanguageId(null);
		setArbLanguageId(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setIsActive(null);
		setRemarks(null);
		setDynamicLabelForActivateDeactivate(null);
		setIfEditClicked(false);
		
	}
	public void remarkSelectedRecord(){
		for(BankAccountTypeBeanDataTable bankAccountTypeDTObj:bankAccountTypeList){
			if(bankAccountTypeDTObj.getRemarksCheck()!=null){
			if(bankAccountTypeDTObj.getRemarksCheck().equals(true)){
				if(getRemarks()!=null){
				bankAccountTypeDTObj.setRemarks(getRemarks());
				updateRecord(bankAccountTypeDTObj);
				viewAllrecords();
				clearAllFields();
				}else{
					RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
				}
			}
			}
		}
		}

	public void updateRecord(BankAccountTypeBeanDataTable bankAccountTypeDTObj){
		saveRecordList.clear();
		
			try{
				BankAccountTypeDTO bankAccountTypeDTO=new BankAccountTypeDTO();
				
				bankAccountTypeDTO.setBankAccountTypeId(bankAccountTypeDTObj.getAccountTypePk());
				bankAccountTypeDTO.setBankAccountTypeCode(bankAccountTypeDTObj.getAccountTypeCode());
				bankAccountTypeDTO.setCreatedBy(bankAccountTypeDTObj.getCreatedBy());
				bankAccountTypeDTO.setCreatedDate(bankAccountTypeDTObj.getCreatedDate());
				bankAccountTypeDTO.setModifiedBy(session.getUserName());
				bankAccountTypeDTO.setModifiedDate(new Date());
				bankAccountTypeDTO.setIsActive(Constants.D);
				bankAccountTypeDTO.setApprovedBy(null);
				bankAccountTypeDTO.setApprovedDate(null);
				bankAccountTypeDTO.setRemarks(bankAccountTypeDTObj.getRemarks());
				
				
				bankAccountTypeDTO.setBankAccountTypeDescEnglishId(bankAccountTypeDTObj.getAccountTypeDescPk());
				bankAccountTypeDTO.setBankAccountTypeDescArabicId(bankAccountTypeDTObj.getAccountTypeLocalDescPk());
				bankAccountTypeDTO.setArabicLanguageId(bankAccountTypeDTObj.getArbLanguageId());
				bankAccountTypeDTO.setEnglishLanguageId(bankAccountTypeDTObj.getEngLanguageId());
				bankAccountTypeDTO.setBankAccountTypeEnglishDesc(bankAccountTypeDTObj.getAccountTypeDesc());
				bankAccountTypeDTO.setBankAccountTypeArabicDesc(bankAccountTypeDTObj.getAccountTypeDescLocal());
				
			/*BankAccountType bankAccountType=new BankAccountType();
			bankAccountType.setBankAccountTypeId(bankAccountTypeDTObj.getAccountTypePk());
			bankAccountType.setBankAccountTypeCode(bankAccountTypeDTObj.getAccountTypeCode());
			bankAccountType.setCreatedBy(bankAccountTypeDTObj.getCreatedBy());
			bankAccountType.setCreatedDate(bankAccountTypeDTObj.getCreatedDate());
			bankAccountType.setApprovedBy(null);
			bankAccountType.setApprovedDate(null);
			bankAccountType.setRemarks(bankAccountTypeDTObj.getRemarks());
			bankAccountType.setModifiedBy(session.getUserName());
			bankAccountType.setModifiedDate(new Date());
			bankAccountType.setIsActive(Constants.D);*/
			
			
			//for english description
			/*BankAccountTypeDesc bankAccountTypeDesc1=new BankAccountTypeDesc();
			bankAccountTypeDesc1.setBankAccountTypeId(bankAccountType);
			bankAccountTypeDesc1.setBankAccountTypeDescId(bankAccountTypeDTObj.getAccountTypeDescPk());
			bankAccountTypeDesc1.setBankAccountTypeDesc(bankAccountTypeDTObj.getAccountTypeDesc());
			LanguageType languageType1=new LanguageType();
			languageType1.setLanguageId(bankAccountTypeDTObj.getEngLanguageId());
			bankAccountTypeDesc1.setLanguageId(languageType1);*/
			
			//for Arabic description
			/*BankAccountTypeDesc bankAccountTypeDesc2=new BankAccountTypeDesc();
			bankAccountTypeDesc2.setBankAccountTypeId(bankAccountType);
			bankAccountTypeDesc2.setBankAccountTypeDescId(bankAccountTypeDTObj.getAccountTypeLocalDescPk());
			bankAccountTypeDesc2.setBankAccountTypeDesc(bankAccountTypeDTObj.getAccountTypeDescLocal());
			LanguageType languageType2=new LanguageType();
			languageType2.setLanguageId(bankAccountTypeDTObj.getArbLanguageId());
			bankAccountTypeDesc2.setLanguageId(languageType2);*/
			
			//bankAccountTypeService.saveRecords(bankAccountType, bankAccountTypeDesc1, bankAccountTypeDesc2);
			//	saveRecordList.add(bankAccountTypeDTO);
			bankAccountTypeService.saveAllRecords(bankAccountTypeDTO);
			}catch(Exception exception){
				log.error("Error Occured While Updating BankAccountTypeRecord :"+exception.getMessage());
			}
	}
	
	public void checkStatusType(BankAccountTypeBeanDataTable bankAccountTypeDTObj){
	if(bankAccountTypeDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
		
		RequestContext.getCurrentInstance().execute("pending.show();");
		return;
	}else  if(bankAccountTypeDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
			bankAccountTypeList.remove(bankAccountTypeDTObj);
			bankAccountTypeNewList.remove(bankAccountTypeDTObj);
		}else if(bankAccountTypeDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)){
			bankAccountTypeDTObj.setRemarksCheck(true);
			setApprovedBy(bankAccountTypeDTObj.getApprovedBy());
			setApprovedDate(bankAccountTypeDTObj.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
			return;
		}else if(bankAccountTypeDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)){
			bankAccountTypeDTObj.setActivateRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;
		}else if(bankAccountTypeDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)){
			if(bankAccountTypeDTObj.getModifiedBy()==null && bankAccountTypeDTObj.getModifiedDate()==null
				&& bankAccountTypeDTObj.getApprovedBy()==null && bankAccountTypeDTObj.getApprovedDate()==null 
				&& bankAccountTypeDTObj.getRemarks()==null){
				bankAccountTypeDTObj.setPermanetDeleteCheck(true);
				RequestContext.getCurrentInstance().execute("permanentDelete.show();");
				return;
			}
		}
			if(bankAccountTypeList.size()==0){
				setRenderSaveButton(false);
				setRenderDataTable(false);
			}
	}
	
	public void confirmPermanentDelete(){
		if(bankAccountTypeList.size()>0){
		for(BankAccountTypeBeanDataTable bankAccountTypeDTObj:bankAccountTypeList){
			if(bankAccountTypeDTObj.getPermanetDeleteCheck()!=null){
			if(bankAccountTypeDTObj.getPermanetDeleteCheck().equals(true)){
				deleteRecordPermanently(bankAccountTypeDTObj);
				bankAccountTypeList.remove(bankAccountTypeDTObj);
			}
			}
	}
		}
	}
	
	public void confirmActivate(BankAccountTypeBeanDataTable bankAccountTypeDTObj){
		bankAccountTypeService.activateRecord(bankAccountTypeDTObj.getAccountTypePk(), session.getUserName());
		viewAllrecords();
	}
	public void activateRecord(){
		if(bankAccountTypeList.size()>0){
		for(BankAccountTypeBeanDataTable bankAccountTypeDTObj:bankAccountTypeList){
			if(bankAccountTypeDTObj.getActivateRecordCheck()!=null){
			if(bankAccountTypeDTObj.getActivateRecordCheck().equals(true)){
				confirmActivate(bankAccountTypeDTObj);
			}
			}
		}
		}
	}
	public void approveRecord(){
		try{
		String approveMsg=bankAccountTypeService.approveReocrd(getAccountTypePk(), session.getUserName());
		if(approveMsg.equalsIgnoreCase("Sucess"))
		{
			RequestContext.getCurrentInstance().execute("approve.show();");
		}else
		{
			RequestContext.getCurrentInstance().execute("notapprove.show();");
		}
		
		}catch(Exception exception){
			log.error("Error occured While Approving Bank Account Type Record  :"+exception.getMessage());
		}
	}
	public void deleteRecordPermanently(BankAccountTypeBeanDataTable bankAccountTypeDTObj){
		bankAccountTypeService.deleteRecordPermanently(bankAccountTypeDTObj.getAccountTypePk(), bankAccountTypeDTObj.getAccountTypeDescPk(), bankAccountTypeDTObj.getAccountTypeLocalDescPk());
	}
	public void saveRecords(){
		
		//saveRecordList.clear();
		
		try{
			System.out.println("STARING TIME  :"+System.currentTimeMillis());
		for(BankAccountTypeBeanDataTable bankAccountTypeDTObj:bankAccountTypeList){
			if(bankAccountTypeDTObj.getIfEditClicked().equals(true)){
				count=count+1;
			BankAccountTypeDTO bankAccountTypeDTO=new BankAccountTypeDTO();
			bankAccountTypeDTO.setBankAccountTypeId(bankAccountTypeDTObj.getAccountTypePk());
			bankAccountTypeDTO.setBankAccountTypeCode(bankAccountTypeDTObj.getAccountTypeCode());
			bankAccountTypeDTO.setCreatedBy(bankAccountTypeDTObj.getCreatedBy());
			bankAccountTypeDTO.setCreatedDate(bankAccountTypeDTObj.getCreatedDate());
			bankAccountTypeDTO.setModifiedBy(bankAccountTypeDTObj.getModifiedBy());
			bankAccountTypeDTO.setModifiedDate(bankAccountTypeDTObj.getModifiedDate());
			bankAccountTypeDTO.setIsActive(bankAccountTypeDTObj.getIsActive());
			bankAccountTypeDTO.setApprovedBy(bankAccountTypeDTObj.getApprovedBy());
			bankAccountTypeDTO.setApprovedDate(bankAccountTypeDTObj.getApprovedDate());
			bankAccountTypeDTO.setRemarks(bankAccountTypeDTObj.getRemarks());
			bankAccountTypeDTO.setBankAccountTypeDescEnglishId(bankAccountTypeDTObj.getAccountTypeDescPk());
			bankAccountTypeDTO.setBankAccountTypeDescArabicId(bankAccountTypeDTObj.getAccountTypeLocalDescPk());
			bankAccountTypeDTO.setArabicLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			bankAccountTypeDTO.setEnglishLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			bankAccountTypeDTO.setBankAccountTypeEnglishDesc(bankAccountTypeDTObj.getAccountTypeDesc());
			bankAccountTypeDTO.setBankAccountTypeArabicDesc(bankAccountTypeDTObj.getAccountTypeDescLocal());
			
			/*BankAccountType bankAccountType=new BankAccountType();
			bankAccountType.setBankAccountTypeId(bankAccountTypeDTObj.getAccountTypePk());
			bankAccountType.setBankAccountTypeCode(bankAccountTypeDTObj.getAccountTypeCode());
			bankAccountType.setCreatedBy(bankAccountTypeDTObj.getCreatedBy());
			bankAccountType.setCreatedDate(bankAccountTypeDTObj.getCreatedDate());
			bankAccountType.setModifiedBy(bankAccountTypeDTObj.getModifiedBy());
			bankAccountType.setModifiedDate(bankAccountTypeDTObj.getModifiedDate());
			bankAccountType.setIsActive(bankAccountTypeDTObj.getIsActive());
			bankAccountType.setApprovedBy(bankAccountTypeDTObj.getApprovedBy());
			bankAccountType.setApprovedDate(bankAccountTypeDTObj.getApprovedDate());
			bankAccountType.setRemarks(bankAccountTypeDTObj.getRemarks());
			*/
			
			//for english description
			/*BankAccountTypeDesc bankAccountTypeDesc1=new BankAccountTypeDesc();
			bankAccountTypeDesc1.setBankAccountTypeDescId(bankAccountTypeDTObj.getAccountTypeDescPk());
			bankAccountTypeDesc1.setBankAccountTypeId(bankAccountType);
			LanguageType languageId=new LanguageType();
			languageId.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			bankAccountTypeDesc1.setLanguageId(languageId);
			bankAccountTypeDesc1.setBankAccountTypeDesc(bankAccountTypeDTObj.getAccountTypeDesc());*/
			
			//for Arabic description
			/*BankAccountTypeDesc bankAccountTypeDesc2=new BankAccountTypeDesc();
			bankAccountTypeDesc2.setBankAccountTypeDescId(bankAccountTypeDTObj.getAccountTypeLocalDescPk());
			bankAccountTypeDesc2.setBankAccountTypeId(bankAccountType);
			LanguageType languageId1=new LanguageType();
			languageId1.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			bankAccountTypeDesc2.setLanguageId(languageId1);
			bankAccountTypeDesc2.setBankAccountTypeDesc(bankAccountTypeDTObj.getAccountTypeDescLocal());*/
			 
			//bankAccountTypeService.saveRecords(bankAccountType, bankAccountTypeDesc1, bankAccountTypeDesc2);
		//	saveRecordList.add(bankAccountTypeDTO);
			bankAccountTypeService.saveAllRecords(bankAccountTypeDTO);
			}
		}
		System.out.println("END  TIME  :"+System.currentTimeMillis());
		setCountNoOfSave(new BigDecimal(count));
		count=0;
		RequestContext.getCurrentInstance().execute("complete.show();");
		clearAllFields();
		bankAccountTypeList.clear();
		bankAccountTypeNewList.clear();
		}catch(Exception exception){
			log.error("Error Occured while Saving BankAccountType and BankAccountTypeDesc Records  :"+exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(exception.toString());
			return;
		}
		
	}
	
	public void exit() throws IOException{

		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		}
	}

	public void viewAllrecords(){
		try {
			
			Boolean childRecordCheck=false;
			clearAllFields();
			setDisableClearButton(false);
			setDisableSubmitButton(false);
			setDisableEditButton(false);
			bankAccountTypeList.clear();
			List<BankAccountTypeDTO> lstBankAccountTypeDTO=bankAccountTypeService.viewRecords();
			if(lstBankAccountTypeDTO.size()>0){
				setRenderSaveButton(true);
				setRenderDataTable(true);
			}else{
				setRenderSaveButton(false);
				setRenderDataTable(false);
				RequestContext.getCurrentInstance().execute("noRecords.show();");
				return;
			}
			
			
			BankAccountTypeBeanDataTable bankAccountTypeDTObj = null;
			for(BankAccountTypeDTO bankAccountTypeDTO:lstBankAccountTypeDTO){
			 bankAccountTypeDTObj=new BankAccountTypeBeanDataTable();
			bankAccountTypeDTObj.setAccountTypeCode(bankAccountTypeDTO.getBankAccountTypeCode());
			bankAccountTypeDTObj.setAccountTypePk(bankAccountTypeDTO.getBankAccountTypeId());
				bankAccountTypeDTObj.setCreatedBy(bankAccountTypeDTO.getCreatedBy());
				bankAccountTypeDTObj.setCreatedDate(bankAccountTypeDTO.getCreatedDate());
				bankAccountTypeDTObj.setModifiedBy(bankAccountTypeDTO.getModifiedBy());
				bankAccountTypeDTObj.setModifiedDate(bankAccountTypeDTO.getModifiedDate());
				bankAccountTypeDTObj.setApprovedBy(bankAccountTypeDTO.getApprovedBy());
				bankAccountTypeDTObj.setApprovedDate(bankAccountTypeDTO.getApprovedDate());
				bankAccountTypeDTObj.setIsActive(bankAccountTypeDTO.getIsActive());
				bankAccountTypeDTObj.setRemarks(bankAccountTypeDTO.getRemarks());
				
				if(bankAccountTypeDTO.getIsActive().equalsIgnoreCase(Constants.Yes)){
				bankAccountTypeDTObj.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				}else if(bankAccountTypeDTO.getIsActive().equalsIgnoreCase(Constants.D)){
					bankAccountTypeDTObj.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				}else if(bankAccountTypeDTO.getIsActive().equalsIgnoreCase(Constants.U) && bankAccountTypeDTObj.getModifiedBy()==null && bankAccountTypeDTObj.getModifiedDate()==null
						&& bankAccountTypeDTObj.getApprovedBy()==null && bankAccountTypeDTObj.getApprovedDate()==null 
						&& bankAccountTypeDTObj.getRemarks()==null){
					bankAccountTypeDTObj.setDynamicLabelForActivateDeactivate(Constants.DELETE);
				}else {
					bankAccountTypeDTObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				}
				List<BankAccountTypeDTO> lstBankAccountTypeDTO1=bankAccountTypeService.getAllRecordsRelatedBankAccountType(bankAccountTypeDTO.getBankAccountTypeId());
				if(lstBankAccountTypeDTO1.size()>0){
					childRecordCheck = true;
				for(BankAccountTypeDTO bankAccountTypeDTO1:lstBankAccountTypeDTO1){
					if(bankAccountTypeDTO1.getLanguageId().intValue()==1){
						bankAccountTypeDTObj.setAccountTypeDesc(bankAccountTypeDTO1.getBankAccountTypeDesc());
						bankAccountTypeDTObj.setAccountTypeDescPk(bankAccountTypeDTO1.getBankAccountTypeDescId());
						bankAccountTypeDTObj.setEngLanguageId(bankAccountTypeDTO1.getLanguageId());
					}
					if(bankAccountTypeDTO1.getLanguageId().intValue()==2){
						bankAccountTypeDTObj.setAccountTypeDescLocal(bankAccountTypeDTO1.getBankAccountTypeDesc());
						bankAccountTypeDTObj.setAccountTypeLocalDescPk(bankAccountTypeDTO1.getBankAccountTypeDescId());
						bankAccountTypeDTObj.setArbLanguageId(bankAccountTypeDTO1.getLanguageId());
					}
				}
				}else{
					childRecordCheck = false;
				}

			if(childRecordCheck == true){	
			bankAccountTypeList.add(bankAccountTypeDTObj);
			}
			}
			bankAccountTypeList.addAll(bankAccountTypeNewList);
			clearAllFields();
		} catch (Exception exception) {
			setBooVisble(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(exception.toString());
			return;
		}
		
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/bankaccounttype.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public List<String> populate(String query) {
		if (query.length() > 0) {
			return bankAccountTypeService.getServiceCodeList(query);
		} else {
			return null;
		}
	}
	
	public Boolean booVisble;
	
	
	
	public Boolean getBooVisble() {
		return booVisble;
	}
	public void setBooVisble(Boolean booVisble) {
		this.booVisble = booVisble;
	}
	public void itemSelectPopulate(){
		
		
		try {
		
		setAccountTypeDescLocal(null);
		setAccountTypeDesc(null);
		setAccountTypePk(null);
		setAccountTypeDescPk(null);
		setAccountTypeLocalDescPk(null);
		setEngLanguageId(null);
		setArbLanguageId(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setIsActive(null);
		setRemarks(null);
		setDynamicLabelForActivateDeactivate(null);
		setDisableSubmitButton(true);
		/*setDisableClearButton(true)*/;
		
		setDisableEditButton(true);
		List<BankAccountTypeDTO> lstBankAccountTypeDTO = bankAccountTypeService.fetchAccTypeCodeRecord(getAccountTypeCode());
		if(lstBankAccountTypeDTO.size()>0){
			if(lstBankAccountTypeDTO.get(0).getIsActive().equalsIgnoreCase(Constants.Yes)){
				setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
			 }else if(lstBankAccountTypeDTO.get(0).getIsActive().equalsIgnoreCase(Constants.D)){
				 	setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
			 }else if(lstBankAccountTypeDTO.get(0).getIsActive().equalsIgnoreCase(Constants.U)&& lstBankAccountTypeDTO.get(0).getModifiedBy()==null && lstBankAccountTypeDTO.get(0).getModifiedDate()==null
						&& lstBankAccountTypeDTO.get(0).getApprovedBy()==null && lstBankAccountTypeDTO.get(0).getApprovedDate()==null 
						&& lstBankAccountTypeDTO.get(0).getRemarks()==null){
				 setDynamicLabelForActivateDeactivate(Constants.DELETE);
				}else{
					setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				}
			setAccountTypeCode(lstBankAccountTypeDTO.get(0).getBankAccountTypeCode());
			setAccountTypePk(lstBankAccountTypeDTO.get(0).getBankAccountTypeId());
			setCreatedBy(lstBankAccountTypeDTO.get(0).getCreatedBy());
			setCreatedDate(lstBankAccountTypeDTO.get(0).getCreatedDate());
			setModifiedBy(lstBankAccountTypeDTO.get(0).getModifiedBy());
			setModifiedDate(lstBankAccountTypeDTO.get(0).getModifiedDate());
			setApprovedBy(lstBankAccountTypeDTO.get(0).getApprovedBy());
			setApprovedDate(lstBankAccountTypeDTO.get(0).getApprovedDate());
			setIsActive(lstBankAccountTypeDTO.get(0).getIsActive());
			setRemarks(lstBankAccountTypeDTO.get(0).getRemarks());
			
			List<BankAccountTypeDTO> lstBankAccountTypeDTO1=bankAccountTypeService.getAllRecordsRelatedBankAccountType(lstBankAccountTypeDTO.get(0).getBankAccountTypeId());
			for(BankAccountTypeDTO bankAccountTypeDTO:lstBankAccountTypeDTO1){
				if(bankAccountTypeDTO.getLanguageId().intValue()==1){
					setAccountTypeDesc(bankAccountTypeDTO.getBankAccountTypeDesc());
					setAccountTypeDescPk(bankAccountTypeDTO.getBankAccountTypeDescId());
					setEngLanguageId(bankAccountTypeDTO.getLanguageId());
				}
				if(bankAccountTypeDTO.getLanguageId().intValue()==2){
					setAccountTypeDescLocal(bankAccountTypeDTO.getBankAccountTypeDesc());
					setAccountTypeLocalDescPk(bankAccountTypeDTO.getBankAccountTypeDescId());
					setArbLanguageId(bankAccountTypeDTO.getLanguageId());
				}
			}
			
		}
		
		} catch (Exception exception) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(exception.toString());
			return;
		}
	}
	
	public void clearRecords() throws IOException{
		clearAllFields();
		FacesContext.getCurrentInstance().getExternalContext().redirect("../common/bankaccounttype.xhtml");
	}
	
	public void disableSubmit(){
		setDisableSubmitButton(true);
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToBankAccountTypePage(){
		clearAllFields();
		bankAccountTypeList.clear();
		bankAccountTypeNewList.clear();
		setAccountTypeCodeForDailog(null);
		setDisableSubmitButton(false);
		setDisableClearButton(false);
		setRenderSaveButton(false);
		setDisableEditButton(false);
		setRenderDataTable(false);
		setReadOnlyAccountTypeCode(false);
		setReadOnlyAccountTypeDescLocal(false);
		setReadOnlyAccountTypeDesc(false);
		setRenderSavePanel(true);
		setRenderUpdatePanel(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/bankaccounttype.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void navigateToBankAcType(){
		setSelectType(null);
		setIfFileUpload(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "bankaccounttypemain.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/bankaccounttypemain.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void navigateToFileUploadPage(){
		  bankAccountTypeList.clear();
		  setIfFileUpload(false);
          setRenderSaveButton(false);
      	setRenderDataTable(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/bankaccounttypefileupload.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void gotoSelectedPage(){
		// 29  manual entry
		if(getSelectType().compareTo(new BigDecimal((Constants.MANUAL).trim()))==0)
		{
			navigateToBankAccountTypePage();
		}//28 means file upload
		else if(getSelectType().compareTo(new BigDecimal((Constants.FILE_UPLOAD).trim()))==0){
			navigateToFileUploadPage();
		}
	}
	
	
	//for file uploading 
	private UploadedFile uploadedFile;
	private Boolean ifFileUpload=false;
	
	

	
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}
	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	public Boolean getIfFileUpload() {
		return ifFileUpload;
	}
	public void setIfFileUpload(Boolean ifFileUpload) {
		this.ifFileUpload = ifFileUpload;
	}
	public void exportExcel(FileUploadEvent event) throws IOException, SerialException, SQLException {
		uploadedFile = event.getFile();
		uploadedFile.getFileName();
		setIfFileUpload(true);
		}
	
	public void uploadtoDatatable() {
		if(getIfFileUpload().equals(false)){
			RequestContext.getCurrentInstance().execute("noFileUploded.show();");
			return;
		}else if(!uploadedFile.getFileName().equalsIgnoreCase("BankAccountTypeExcel.xls")){
			RequestContext.getCurrentInstance().execute("changeFile.show();");
			setIfFileUpload(false);
			setUploadedFile(null);
		}else{
		
		bankAccountTypeList.clear();
	   try {
            //Create Workbook instance for xlsx/xls file input stream
            Workbook workbook = null;
         workbook = new HSSFWorkbook(uploadedFile.getInputstream());
             
            //Get the number of sheets in the xlsx file
            int numberOfSheets = workbook.getNumberOfSheets();
             
            //loop through each of the sheets
            for(int i=0; i < numberOfSheets; i++){
                 
                //Get the nth sheet from the workbook
                Sheet sheet = workbook.getSheetAt(i);
                 
                //every sheet has rows, iterate over them
                Iterator<Row> rowIterator = sheet.iterator();
                int rowNumber =1;
                while (rowIterator.hasNext()) 
                {
                    //Get the row object
                    Row row = rowIterator.next();
                    if(rowNumber==1)
    				{
    					rowNumber++;
    					continue;
    				}
                    //Every row has columns, get the column iterator and iterate over them
                    Iterator<Cell> cellIterator = row.cellIterator();
                    BankAccountTypeBeanDataTable bankAccountType= new BankAccountTypeBeanDataTable();
                      
                    while (cellIterator.hasNext()) 
                    {
                        //Get the Cell object
                        Cell cell = cellIterator.next();
                         
                        //check the cell type and process accordingly
                        switch(cell.getCellType()){
                        
                        case Cell.CELL_TYPE_STRING:
			                        	if(cell.getColumnIndex()==0){
			                        		String	accountTypeCode = cell.getStringCellValue().trim();
			                        		bankAccountType.setAccountTypeCode(accountTypeCode);
			                        		List<BankAccountTypeDTO> listSize=bankAccountTypeService.fetchAccTypeCodeRecord(accountTypeCode);
			                        		if(listSize.size()>0){
			                        			bankAccountType.setStatusCheck(Constants.DUPLICATE);
			                        			listSize.clear();
			                        		}else{
			                        			bankAccountType.setStatusCheck(Constants.New_Record);
			                        		}
			                        	}
			                        	if(cell.getColumnIndex()==1){
			                        		String accountTypeEngDesc = cell.getStringCellValue().trim();
			                        		bankAccountType.setAccountTypeDesc(accountTypeEngDesc);
			                        	}
			                        	if(cell.getColumnIndex()==2){
			                        		String accountTypeLocalDesc = cell.getStringCellValue().trim();
			                        		bankAccountType.setAccountTypeDescLocal(accountTypeLocalDesc);
			                        	}
			                       /* 	if(cell.getColumnIndex()==3){
			                        		String createdBy = cell.getStringCellValue().trim();
			                        		bankAccountType.setCreatedBy(createdBy);
			                        	}
			                        	if(cell.getColumnIndex()==4){
			                        		String createdDate = cell.getStringCellValue().trim();
			                        		bankAccountType.setCreatedDate(new Date(createdDate));
			                        	}*/
			                        	break;
                        case Cell.CELL_TYPE_NUMERIC:
			                      /*  	if(cell.getColumnIndex()==0){
			                        		double bankAccountTypeId =   cell.getNumericCellValue();
			                        		bankAccountType.setAccountTypePk(new BigDecimal(bankAccountTypeId));
			                        	}
			                        	if(cell.getColumnIndex()==6){
			                        		Double languageId = cell.getNumericCellValue();
			                        		cityMasterData.setStateId(new BigDecimal(d));
			                        	}
			                        	break;*/
                        	
						case Cell.CELL_TYPE_BLANK:
										break;
                        }
                    } //end of cell iterator
                    bankAccountTypeList.add(bankAccountType);
                    
                } //end of rows iterator
                setRenderSaveButton(true);
            	setRenderDataTable(true); 
            } //end of sheets for loop
             
	               
        } catch (IOException e) {
            e.printStackTrace();
        }
		}
	}
	
	//for saving Excel File Data
	public void savefileUploadRecords(){
		saveRecordList.clear();		
		try{
		for(BankAccountTypeBeanDataTable bankAccountTypeDTObj:bankAccountTypeList){
			if(bankAccountTypeDTObj.getStatusCheck().equalsIgnoreCase(Constants.New_Record)){
				count=count+1;
				
				BankAccountTypeDTO bankAccountTypeDTO=new BankAccountTypeDTO();
				//bankAccountTypeDTO.setBankAccountTypeId(bankAccountTypeDTObj.getAccountTypePk());
				bankAccountTypeDTO.setBankAccountTypeCode(bankAccountTypeDTObj.getAccountTypeCode());
				bankAccountTypeDTO.setCreatedBy(session.getUserName());
				bankAccountTypeDTO.setCreatedDate(new Date());
				bankAccountTypeDTO.setModifiedBy(null);
				bankAccountTypeDTO.setModifiedDate(null);
				bankAccountTypeDTO.setIsActive(Constants.U);
				bankAccountTypeDTO.setApprovedBy(null);
				bankAccountTypeDTO.setApprovedDate(null);
				bankAccountTypeDTO.setRemarks(null);
				
				
				//bankAccountTypeDTO.setBankAccountTypeDescEnglishId(bankAccountTypeDTObj.getAccountTypeDescPk());
				//bankAccountTypeDTO.setBankAccountTypeDescArabicId(bankAccountTypeDTObj.getAccountTypeLocalDescPk());
				bankAccountTypeDTO.setArabicLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
				bankAccountTypeDTO.setEnglishLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
				bankAccountTypeDTO.setBankAccountTypeEnglishDesc(bankAccountTypeDTObj.getAccountTypeDesc());
				bankAccountTypeDTO.setBankAccountTypeArabicDesc(bankAccountTypeDTObj.getAccountTypeDescLocal());
				
			/*BankAccountType bankAccountType=new BankAccountType();
			bankAccountType.setBankAccountTypeCode(bankAccountTypeDTObj.getAccountTypeCode());
			bankAccountType.setCreatedBy(session.getUserName());
			bankAccountType.setCreatedDate(new Date());
			bankAccountType.setIsActive(Constants.U);*/
			
			
			//for english description
			/*BankAccountTypeDesc bankAccountTypeDesc1=new BankAccountTypeDesc();
			bankAccountTypeDesc1.setBankAccountTypeId(bankAccountType);
			LanguageType languageId=new LanguageType();
			languageId.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			bankAccountTypeDesc1.setLanguageId(languageId);
			bankAccountTypeDesc1.setBankAccountTypeDesc(bankAccountTypeDTObj.getAccountTypeDesc());*/
			
			//for Arabic description
			/*BankAccountTypeDesc bankAccountTypeDesc2=new BankAccountTypeDesc();
			bankAccountTypeDesc2.setBankAccountTypeId(bankAccountType);
			LanguageType languageId1=new LanguageType();
			languageId1.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			bankAccountTypeDesc2.setLanguageId(languageId1);
			bankAccountTypeDesc2.setBankAccountTypeDesc(bankAccountTypeDTObj.getAccountTypeDescLocal());*/
			 
			//bankAccountTypeService.saveRecords(bankAccountType, bankAccountTypeDesc1, bankAccountTypeDesc2);
			//	saveRecordList.add(bankAccountTypeDTO);
				bankAccountTypeService.saveAllRecords(bankAccountTypeDTO);
			}
		}
	//	bankAccountTypeService.saveAllRecords(saveRecordList);
		setCountNoOfSave(new BigDecimal(count));
		count=0;
		bankAccountTypeList.clear();
		setUploadedFile(null);
		RequestContext.getCurrentInstance().execute("fileUploadSave.show();");
		return;
		}catch(Exception exception){
			log.error("Error Occured while Saving BankAccountType and BankAccountTypeDesc Records  :"+exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(exception.toString());
			return;
		}
		
	
	}
	public void gotoBack(){
		navigateToBankAcType();
	}
	
	public void clearRemarks(){
		setRemarks(null);
	}
	

	

}
