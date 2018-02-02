package com.amg.exchange.common.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.ISourceOfIncomeService;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("sourceOfIncomeBean")
@Scope("session")
public class SourceOfIncomeBean<T> implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(SourceOfIncomeBean.class);
	private SessionStateManage session=new SessionStateManage();
	
	@Autowired
	ISourceOfIncomeService isourceOfIncomeService;
	
   
	private Date createdDate;
	private Date  modifiedDate;
    private Date approvedDate;
    private String createdBy;
    private String modifiedBy;
    private String approvedBy;
    private String remarks;
    private BigDecimal sourceOfIncomePk;
    private BigDecimal sourceOfIncomeEngPk;
    private BigDecimal sourceOfIncomeArabicPk;
    private String sourceCode;
    private String srcIncomeEngShortDesc;
    private String srcIncomeArabicFullDesc;
    private String srcIncomeEngFullDesc;
    private String isActive;
    private String srcIncomeArabicShortDesc;
    private BigDecimal applicationCountryId;
    private BigDecimal companyId;
    private String dynamicLabelForActivateDeactivate;
    private Boolean ifEditClicked=false;
    private Boolean booRenderDataTable=true;
    private Boolean booSubmitPanel;
    private Boolean hideClearButton;
    private Boolean hideEditButton;
    private Boolean hideSubmitButton=false;
    private Boolean readOnly=false;
    private Boolean booRenderViewPanel=true;
    private Boolean booRenderApprove=false;
    
      
    List<SourceOfIncomeDataTableBean> srcIncDTList=new CopyOnWriteArrayList<SourceOfIncomeDataTableBean>();
    List<SourceOfIncomeDataTableBean> srcIncViewDTList=new  ArrayList<SourceOfIncomeDataTableBean>();
     
    
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
	
    
    
    public String getSrcIncomeEngFullDesc() {
		return srcIncomeEngFullDesc;
	}
	public void setSrcIncomeEngFullDesc(String srcIncomeEngFullDesc) {
		this.srcIncomeEngFullDesc = srcIncomeEngFullDesc;
	}
	public String getSrcIncomeEngShortDesc() {
		return srcIncomeEngShortDesc;
	}
	public void setSrcIncomeEngShortDesc(String srcIncomeEngShortDesc) {
		this.srcIncomeEngShortDesc = srcIncomeEngShortDesc;
	}
	public String getSrcIncomeArabicFullDesc() {
		return srcIncomeArabicFullDesc;
	}
	public void setSrcIncomeArabicFullDesc(String srcIncomeArabicFullDesc) {
		this.srcIncomeArabicFullDesc = srcIncomeArabicFullDesc;
	}
	public String getSrcIncomeArabicShortDesc() {
		return srcIncomeArabicShortDesc;
	}
	public void setSrcIncomeArabicShortDesc(String srcIncomeArabicShortDesc) {
		this.srcIncomeArabicShortDesc = srcIncomeArabicShortDesc;
	}
    
    public List<SourceOfIncomeDataTableBean> getSrcIncViewDTList() {
		return srcIncViewDTList;
	}
	public void setSrcIncViewDTList(
			List<SourceOfIncomeDataTableBean> srcIncViewDTList) {
		this.srcIncViewDTList = srcIncViewDTList;
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
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
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
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public BigDecimal getSourceOfIncomePk() {
		return sourceOfIncomePk;
	}
	public void setSourceOfIncomePk(BigDecimal sourceOfIncomePk) {
		this.sourceOfIncomePk = sourceOfIncomePk;
	}
	 
	public List<SourceOfIncomeDataTableBean> getSrcIncDTList() {
		return srcIncDTList;
	}
	public void setSrcIncDTList(List<SourceOfIncomeDataTableBean> srcIncDTList) {
		this.srcIncDTList = srcIncDTList;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public BigDecimal getSourceOfIncomeEngPk() {
		return sourceOfIncomeEngPk;
	}
	public void setSourceOfIncomeEngPk(BigDecimal sourceOfIncomeEngPk) {
		this.sourceOfIncomeEngPk = sourceOfIncomeEngPk;
	}
	public BigDecimal getSourceOfIncomeArabicPk() {
		return sourceOfIncomeArabicPk;
	}
	public void setSourceOfIncomeArabicPk(BigDecimal sourceOfIncomeArabicPk) {
		this.sourceOfIncomeArabicPk = sourceOfIncomeArabicPk;
	}
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public Boolean getIfEditClicked() {
		return ifEditClicked;
	}
	public void setIfEditClicked(Boolean ifEditClicked) {
		this.ifEditClicked = ifEditClicked;
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
	public Boolean getHideClearButton() {
		return hideClearButton;
	}
	public void setHideClearButton(Boolean hideClearButton) {
		this.hideClearButton = hideClearButton;
	}
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public Boolean getHideEditButton() {
		return hideEditButton;
	}
	public void setHideEditButton(Boolean hideEditButton) {
		this.hideEditButton = hideEditButton;
	}
	public Boolean getReadOnly() {
		return readOnly;
	}
	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}
	public Boolean getBooRenderViewPanel() {
		return booRenderViewPanel;
	}
	public void setBooRenderViewPanel(Boolean booRenderViewPanel) {
		this.booRenderViewPanel = booRenderViewPanel;
	}
	public Boolean getBooRenderApprove() {
		return booRenderApprove;
	}
	public void setBooRenderApprove(Boolean booRenderApprove) {
		this.booRenderApprove = booRenderApprove;
	}
	public Boolean getHideSubmitButton() {
		return hideSubmitButton;
	}
	public void setHideSubmitButton(Boolean hideSubmitButton) {
		this.hideSubmitButton = hideSubmitButton;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;   	
	public void pageNavigationToSourceOfIncome(){
		setReadOnly(false);
		setBooRenderDataTable( false);
		setBooSubmitPanel( false);
		setHideClearButton( false);
		setHideEditButton( false);
		setBooRenderApprove(false);
		setBooRenderViewPanel(true);
		setHideSubmitButton( false);
		srcIncDTList.clear();
		clearAll();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "sourceofincome.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../common/sourceofincome.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to   Source Of Income");
		}
		
	}
	public void clearAll(){
		setReadOnly( false);
		setApprovedBy( null);
		setApprovedDate( null);
		setCreatedBy( null);
		setCreatedDate(null);
		setModifiedBy( null);
		setModifiedDate( null);
		setRemarks( null);
		setSourceOfIncomePk( null);
		setSourceCode( null);
		setSourceOfIncomeArabicPk( null);
		setSourceOfIncomeEngPk( null);
		setSrcIncomeArabicFullDesc( null);
		setSrcIncomeArabicShortDesc( null);
		setSrcIncomeEngFullDesc( null);
		setSrcIncomeEngShortDesc( null);
		setDynamicLabelForActivateDeactivate( null);
		setIsActive( null);
			
	}
	
	public void exitButton(){
				try {
			if (session.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../registration/branchhome.xhtml");
			}
		} catch (Exception e) {
			log.error("======Problem Ocuured in Exit Button=====");
		}

	}
	public List<String> autoComplete(String query){
		
		List<String> list = null;
		setBooVisible(false);
		try {
			list  = isourceOfIncomeService.getAutoCompleteSourceOfIncomeCode( query);
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return null;
		}
		
		return list ;
	}
	
	
	public void viewAll(){
		try {
			
			setBooVisible(false);
			setBooRenderDataTable( true);
			setBooSubmitPanel(true);
			setHideClearButton(false);
			setHideEditButton( false);
			setBooRenderApprove(false);
			setBooRenderViewPanel(true);
			setHideSubmitButton(false);
			log.info( "view called ===========");
			srcIncDTList.clear();
			//srcIncViewDTList.clear();
			Boolean childRecordCheck=false;
			List<SourceOfIncome> srcIncomeList=isourceOfIncomeService.getAllRecords();
			log.info( "view button clicked ======"+srcIncomeList.size());
			if(srcIncomeList.size()>0){
			for(SourceOfIncome sourceOfInc:srcIncomeList){
				
				SourceOfIncomeDataTableBean sourceOfIncomeDTObj=new SourceOfIncomeDataTableBean();
				sourceOfIncomeDTObj.setSourceOfIncomeCode(sourceOfInc.getSourceCode());
				sourceOfIncomeDTObj.setCreatedBy(sourceOfInc.getCreatedBy());
				sourceOfIncomeDTObj.setCreatedDate(sourceOfInc.getCreationDate());
				sourceOfIncomeDTObj.setCompanyId(sourceOfInc.getFsCompanyMaster().getCompanyId());
				sourceOfIncomeDTObj.setApplicationCountryId(sourceOfInc.getFsCountryMaster().getCountryId());
				sourceOfIncomeDTObj.setSourceOfIncomePk(sourceOfInc.getSourceId());
				sourceOfIncomeDTObj.setRemarks(sourceOfInc.getRemarks());
				sourceOfIncomeDTObj.setBooCheckUpdate(false);
				sourceOfIncomeDTObj.setIfEditClicked(false);
				//sourceOfIncomeDTObj.setBooHideEditButton(false);
				if(sourceOfInc.getIsActive().equalsIgnoreCase(Constants.Yes)){
					sourceOfIncomeDTObj.setDynamicLabelForActivateDeactivcate(Constants.DEACTIVATE);
					sourceOfIncomeDTObj.setApprovedBy(sourceOfInc.getApprovedBy());
					sourceOfIncomeDTObj.setApprovedDate(sourceOfInc.getApprovedDate());
				}else if(sourceOfInc.getIsActive().equalsIgnoreCase(Constants.D)){
					sourceOfIncomeDTObj.setDynamicLabelForActivateDeactivcate(Constants.ACTIVATE);
				}else if(sourceOfInc.getIsActive().equalsIgnoreCase(Constants.U)&&sourceOfInc.getApprovedBy()==null&&sourceOfInc.getApprovedDate()==null&&sourceOfInc.getModifiedBy()==null&&sourceOfInc.getModifiedDate()==null&&sourceOfInc.getRemarks()==null){
					//sourceOfIncomeDTObj.setPermanentDeleteCheck( true);
					sourceOfIncomeDTObj.setDynamicLabelForActivateDeactivcate(Constants.DELETE);
				}else{
					sourceOfIncomeDTObj.setDynamicLabelForActivateDeactivcate(Constants.PENDING_FOR_APPROVE);
				}
				List<SourceOfIncomeDescription> srcIncDescList =		isourceOfIncomeService.getSourceOfIncomeDesc(sourceOfInc.getSourceId() );
				if(srcIncDescList.size()>0){
					childRecordCheck=true;
					for(SourceOfIncomeDescription sourceOfIncDesc:srcIncDescList){
						if(Constants.ENGLISH_LANGUAGE_ID.equalsIgnoreCase(sourceOfIncDesc.getLanguageId().getLanguageId().toPlainString() )){
						sourceOfIncomeDTObj.setSourceOfIncomeEngFullDesc(sourceOfIncDesc.getSourceOfIncomeFullDesc());
						sourceOfIncomeDTObj.setSourceOfIncomeEngShortDesc(sourceOfIncDesc.getSourceOfIncomeShortDesc());
						sourceOfIncomeDTObj.setSourceOfIncomeEngPk(sourceOfIncDesc.getSourceOfIncomeDescId());
						sourceOfIncomeDTObj.setEngLanguageId(sourceOfIncDesc.getLanguageId().getLanguageId());
						}else{
							sourceOfIncomeDTObj.setSourceOfIncomeArabicFullDesc(sourceOfIncDesc.getSourceOfIncomeFullDesc());
							sourceOfIncomeDTObj.setSourceOfIncomeArabicShortDesc(sourceOfIncDesc.getSourceOfIncomeShortDesc());
							sourceOfIncomeDTObj.setSourceOfIncomeArabicPk(sourceOfIncDesc.getSourceOfIncomeDescId());
							sourceOfIncomeDTObj.setEngLanguageId(sourceOfIncDesc.getLanguageId().getLanguageId());
							
						}
					}
				}else{
					childRecordCheck=false;
				}
				if(childRecordCheck==true ){
					log.info( "COME TO INSIDE ADDED TO VIEWLIST");
					srcIncDTList.add(sourceOfIncomeDTObj );
				}
				
				
				
			}
			srcIncDTList.addAll(srcIncViewDTList);
			}else{
				RequestContext.getCurrentInstance().execute("norecordsfound.show();");
				
			}
			clearAll();
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return ;
		}
	}
	
	public void addRecords(){
		log.info( "addrecords() method called==============");
		SourceOfIncomeDataTableBean srcIncomeDataTable=new SourceOfIncomeDataTableBean();
		
		srcIncomeDataTable.setSourceOfIncomeCode(getSourceCode() );
		srcIncomeDataTable.setSourceOfIncomeEngPk(getSourceOfIncomeEngPk() );
		srcIncomeDataTable.setSourceOfIncomeArabicPk(getSourceOfIncomeArabicPk());
		srcIncomeDataTable.setSourceOfIncomePk(getSourceOfIncomePk());
		srcIncomeDataTable.setSourceOfIncomeEngFullDesc(getSrcIncomeEngFullDesc());
		srcIncomeDataTable.setSourceOfIncomeEngShortDesc(getSrcIncomeEngShortDesc());
		srcIncomeDataTable.setSourceOfIncomeArabicFullDesc(getSrcIncomeArabicFullDesc());
		srcIncomeDataTable.setSourceOfIncomeArabicShortDesc(getSrcIncomeArabicShortDesc());
		if(getSourceOfIncomePk()!=null){
			
			srcIncomeDataTable.setCreatedBy(getCreatedBy());
			srcIncomeDataTable.setCreatedDate(getCreatedDate());
			srcIncomeDataTable.setModifiedBy(session.getUserName());
			srcIncomeDataTable.setModifiedDate(new Date());
			srcIncomeDataTable.setApprovedBy(null);
			srcIncomeDataTable.setApprovedDate(null);
			srcIncomeDataTable.setIsActive(Constants.U);
			srcIncomeDataTable.setDynamicLabelForActivateDeactivcate( Constants.PENDING_FOR_APPROVE);
			srcIncomeDataTable.setRemarks(null);
			srcIncomeDataTable.setIfEditClicked(getIfEditClicked());
			srcIncomeDataTable.setBooCheckUpdate( false);		
			setHideEditButton( false);
		}else{		
			srcIncomeDataTable.setBooCheckUpdate(true);
			srcIncomeDataTable.setCreatedBy(session.getUserName());
			srcIncomeDataTable.setCreatedDate(new Date());
			srcIncomeDataTable.setDynamicLabelForActivateDeactivcate( Constants.REMOVE);
			srcIncomeDataTable.setIsActive(Constants.U);
			srcIncomeDataTable.setIfEditClicked( true);

			setHideEditButton( false);
			srcIncViewDTList.add(srcIncomeDataTable );
		}
		
		srcIncDTList.add(srcIncomeDataTable );
		setBooRenderDataTable( true);
		setHideClearButton(false);
		setBooSubmitPanel( true);
		setHideSubmitButton(false);
		
	   clearAll();
	}
	
	public void addRecordsToDataTable(){
		try {
			setBooVisible(false);
			log.info( ":::::::::::::::::::::::::::::entered into add to data table::::::::::::::::::::");
			List<SourceOfIncome>   srcIncomeDuplicateCheckList = isourceOfIncomeService.getSourceOfIncomeRecordsBanedOncode( getSourceCode());

			if(srcIncomeDuplicateCheckList.size()>0&& !getIfEditClicked()){
				setSrcIncomeEngFullDesc(null);
				setSrcIncomeEngShortDesc( null);
				setSrcIncomeArabicFullDesc( null);
				setSrcIncomeArabicShortDesc( null);
				setSourceCode( null);
				RequestContext.getCurrentInstance().execute("recordAlreadyExist.show();");
				return;
			}else if(srcIncDTList.size()!=0){
				for (SourceOfIncomeDataTableBean   srcIncomeDTObj : srcIncDTList) {
					if(srcIncomeDTObj.getSourceOfIncomeCode().equalsIgnoreCase( getSourceCode())){
						setSrcIncomeEngFullDesc(null);
						setSrcIncomeEngShortDesc( null);
						setSrcIncomeArabicFullDesc( null);
						setSrcIncomeArabicShortDesc( null);
						setSourceCode( null);
						RequestContext.getCurrentInstance().execute("recordAlreadyExist.show();");
						return;
					} 
				}
			}	
			addRecords();
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}
	
	public void saveDataTableRecords(){
		setBooVisible(false);
		try {
			if(srcIncDTList.size()>0){
			for(SourceOfIncomeDataTableBean sourceIcomedatatable:srcIncDTList){
				if(sourceIcomedatatable.getIfEditClicked()){
			SourceOfIncome sourceOfIncObj=new SourceOfIncome();
			sourceOfIncObj.setSourceId(sourceIcomedatatable.getSourceOfIncomePk());
			sourceOfIncObj.setCreatedBy(session.getUserName());;
			sourceOfIncObj.setCreationDate(new Date());
			if(sourceIcomedatatable.getSourceOfIncomePk()!=null){
			sourceOfIncObj.setModifiedBy(sourceIcomedatatable.getModifiedBy() );
			sourceOfIncObj.setModifiedDate(sourceIcomedatatable.getModifiedDate());
			}
			
			sourceOfIncObj.setIsActive(Constants.U );
			sourceOfIncObj.setSourceCode(sourceIcomedatatable.getSourceOfIncomeCode() );
			CountryMaster countryMaster=new CountryMaster();
			countryMaster.setCountryId(session.getCountryId());
			sourceOfIncObj.setFsCountryMaster(countryMaster );
			
			CompanyMaster companyMaster=new CompanyMaster();
			companyMaster.setCompanyId(session.getCompanyId());
			sourceOfIncObj.setFsCompanyMaster(companyMaster );
			
			SourceOfIncomeDescription sourceOfIncEngDesc=new SourceOfIncomeDescription();
			sourceOfIncEngDesc.setSourceOfIncomeFullDesc(sourceIcomedatatable.getSourceOfIncomeEngFullDesc());
			sourceOfIncEngDesc.setSourceOfIncomeShortDesc(sourceIcomedatatable.getSourceOfIncomeEngShortDesc());
			sourceOfIncEngDesc.setSourceOfIncomeDescId(sourceIcomedatatable.getSourceOfIncomeEngPk());
			 LanguageType langEng=new LanguageType();
			 langEng.setLanguageId( new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			 sourceOfIncEngDesc.setLanguageId(langEng);
			 sourceOfIncEngDesc.setSourceOfIncomeId(sourceOfIncObj);
			 
			SourceOfIncomeDescription sourceOfIncArabicDesc=new SourceOfIncomeDescription();
			if(sourceIcomedatatable.getSourceOfIncomeArabicPk() !=null)
			{
				sourceOfIncArabicDesc.setSourceOfIncomeDescId(sourceIcomedatatable.getSourceOfIncomeArabicPk());
			}
			
			sourceOfIncArabicDesc.setSourceOfIncomeShortDesc(sourceIcomedatatable.getSourceOfIncomeArabicShortDesc());
			sourceOfIncArabicDesc.setSourceOfIncomeFullDesc(sourceIcomedatatable.getSourceOfIncomeArabicFullDesc() );
			LanguageType langArabic=new LanguageType();
			langArabic.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			sourceOfIncArabicDesc.setLanguageId(langArabic);
			sourceOfIncArabicDesc.setSourceOfIncomeId(sourceOfIncObj);
 
			isourceOfIncomeService.saveOrUpdate(sourceOfIncObj, sourceOfIncEngDesc, sourceOfIncArabicDesc);
				}
			}
				RequestContext.getCurrentInstance().execute("complete.show();");
			
			}

			else{
				RequestContext.getCurrentInstance().execute("norecord.show();");
			
}
			clearAll();
			srcIncDTList.clear();
			srcIncViewDTList.clear();
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
		
	}
	public void updateRecord(SourceOfIncomeDataTableBean sourceOfIncomeDTObj){
		
		try {
			setBooVisible(false);
			SourceOfIncome sourceOfIncObj=new SourceOfIncome();
			sourceOfIncObj.setSourceId( sourceOfIncomeDTObj.getSourceOfIncomePk());
			sourceOfIncObj.setSourceCode(sourceOfIncomeDTObj.getSourceOfIncomeCode() );
			sourceOfIncObj.setCreatedBy(sourceOfIncomeDTObj.getCreatedBy() );
			sourceOfIncObj.setCreationDate( sourceOfIncomeDTObj.getCreatedDate());
			sourceOfIncObj.setModifiedBy( session.getUserName());
			sourceOfIncObj.setModifiedDate( new Date());
			sourceOfIncObj.setIsActive(Constants.D );
			sourceOfIncObj.setRemarks( sourceOfIncomeDTObj.getRemarks());
			CountryMaster countryMaster=new CountryMaster();
			countryMaster.setCountryId(sourceOfIncomeDTObj.getApplicationCountryId() );
			sourceOfIncObj.setFsCountryMaster(countryMaster );
			
			CompanyMaster companyMaster=new CompanyMaster();
			companyMaster.setCompanyId( sourceOfIncomeDTObj.getCompanyId());
			sourceOfIncObj.setFsCompanyMaster(companyMaster );
			
			SourceOfIncomeDescription sourceOfIncEngDesc=new SourceOfIncomeDescription();
			sourceOfIncEngDesc.setSourceOfIncomeDescId( sourceOfIncomeDTObj.getSourceOfIncomeEngPk());
			sourceOfIncEngDesc.setSourceOfIncomeFullDesc(sourceOfIncomeDTObj.getSourceOfIncomeEngFullDesc() );
			sourceOfIncEngDesc.setSourceOfIncomeShortDesc( sourceOfIncomeDTObj.getSourceOfIncomeEngShortDesc());		 
			LanguageType langEng=new LanguageType();
			langEng.setLanguageId( new BigDecimal( Constants.ENGLISH_LANGUAGE_ID ));
			sourceOfIncEngDesc.setLanguageId(langEng );
			sourceOfIncEngDesc.setSourceOfIncomeId(sourceOfIncObj);
			 
			SourceOfIncomeDescription sourceOfIncArabicDesc=new SourceOfIncomeDescription();
			sourceOfIncArabicDesc.setSourceOfIncomeDescId( sourceOfIncomeDTObj.getSourceOfIncomeArabicPk());
			sourceOfIncArabicDesc.setSourceOfIncomeShortDesc( sourceOfIncomeDTObj.getSourceOfIncomeArabicShortDesc());
			sourceOfIncArabicDesc.setSourceOfIncomeFullDesc(sourceOfIncomeDTObj.getSourceOfIncomeArabicFullDesc() );
			LanguageType langArabic=new LanguageType();
			langArabic.setLanguageId( new BigDecimal( Constants.ARABIC_LANGUAGE_ID ));
			sourceOfIncArabicDesc.setLanguageId(langArabic );
			sourceOfIncArabicDesc.setSourceOfIncomeId( sourceOfIncObj);
			
			isourceOfIncomeService.saveOrUpdate(sourceOfIncObj, sourceOfIncEngDesc, sourceOfIncArabicDesc);
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}	
		
		
		
	}
	
	
	public void checkStatusType(SourceOfIncomeDataTableBean sourceOfIncomeDTObj){
		try {
			setBooVisible(false);
			
			if(sourceOfIncomeDTObj.getDynamicLabelForActivateDeactivcate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
			
				RequestContext.getCurrentInstance().execute("pending.show();");
				return;
			}else  if(sourceOfIncomeDTObj.getDynamicLabelForActivateDeactivcate().equalsIgnoreCase(Constants.REMOVE)){
				srcIncDTList.remove(sourceOfIncomeDTObj);
				srcIncViewDTList.remove(sourceOfIncomeDTObj);
				}else if(sourceOfIncomeDTObj.getDynamicLabelForActivateDeactivcate().equalsIgnoreCase(Constants.DEACTIVATE)){
				 sourceOfIncomeDTObj.setRemarksCheck(true);
				 setApprovedBy(sourceOfIncomeDTObj.getApprovedBy());
				 setApprovedDate(sourceOfIncomeDTObj.getApprovedDate());
				 RequestContext.getCurrentInstance().execute("remarks.show();");
					return;
				}else if(sourceOfIncomeDTObj.getDynamicLabelForActivateDeactivcate().equalsIgnoreCase(Constants.ACTIVATE)){
					sourceOfIncomeDTObj.setActivateRecordCheck(true);
				RequestContext.getCurrentInstance().execute("activateRecord.show();");
				return;
				}else if(sourceOfIncomeDTObj.getDynamicLabelForActivateDeactivcate().equalsIgnoreCase(Constants.DELETE)){
			if(sourceOfIncomeDTObj.getModifiedBy()==null && sourceOfIncomeDTObj.getModifiedDate()==null
			 && sourceOfIncomeDTObj.getApprovedBy()==null && sourceOfIncomeDTObj.getApprovedDate()==null 
				 && sourceOfIncomeDTObj.getRemarks()==null){
				sourceOfIncomeDTObj.setPermanentDeleteCheck(true);
				 RequestContext.getCurrentInstance().execute("permanentDelete.show();");
				 return;
					}
 }
					/*if(compliantActionDTList.size()==0){
						setRenderSaveButton(false);
						setRenderDatatable(false);
					}*/
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
		}
	public void confirmPermanentDelete(){
		if(srcIncDTList.size()>0){
		for( SourceOfIncomeDataTableBean srcIncObj:srcIncDTList){
			if(srcIncObj.getPermanentDeleteCheck()!=null){
			if(srcIncObj.getPermanentDeleteCheck().equals(true)){
				deleteRecordPermanently(srcIncObj);
				srcIncDTList.remove(srcIncObj);
			}
			}
	}
		}
	}
	
	public void deleteRecordPermanently( SourceOfIncomeDataTableBean srcIncObj){

		setBooVisible(false);
		try {
			isourceOfIncomeService.deleteRecordPermanently( srcIncObj.getSourceOfIncomePk(),srcIncObj.getSourceOfIncomeEngPk(),srcIncObj.getSourceOfIncomeArabicPk());
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
			
		}
	
public void confirmActivate(SourceOfIncomeDataTableBean srcIncObj){
		try {
			setBooVisible(false);
			isourceOfIncomeService.activateRecord(srcIncObj.getSourceOfIncomePk(),session.getUserName());
			viewAll();
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}
	public void activateRecord(){
		if(srcIncDTList.size()>0){
			for( SourceOfIncomeDataTableBean srcIncObj:srcIncDTList){
			if(srcIncObj.getActivateRecordCheck()!=null){
			if(srcIncObj.getActivateRecordCheck().equals(true)){
				confirmActivate(srcIncObj);
			}
			}
		}
		}
	} 
	
	public void editRecord( SourceOfIncomeDataTableBean sourceOfIncdtObj){
		try {
			setBooVisible(false);
			setHideClearButton( true);
			setHideEditButton( true);
			setHideSubmitButton(true);
			setSourceCode( sourceOfIncdtObj.getSourceOfIncomeCode());
			setSourceOfIncomePk(sourceOfIncdtObj.getSourceOfIncomePk());
			setSourceOfIncomeEngPk(sourceOfIncdtObj.getSourceOfIncomeEngPk());
			setSourceOfIncomeArabicPk(sourceOfIncdtObj.getSourceOfIncomeArabicPk());
			setSrcIncomeEngFullDesc(sourceOfIncdtObj.getSourceOfIncomeEngFullDesc());
			setSrcIncomeEngShortDesc(sourceOfIncdtObj.getSourceOfIncomeEngShortDesc());
			setSrcIncomeArabicFullDesc(sourceOfIncdtObj.getSourceOfIncomeArabicFullDesc());
			setSrcIncomeArabicShortDesc( sourceOfIncdtObj.getSourceOfIncomeArabicShortDesc());
			setCreatedBy(sourceOfIncdtObj.getCreatedBy());
			setCreatedDate(sourceOfIncdtObj.getCreatedDate());
			if (sourceOfIncdtObj.getSourceOfIncomeEngPk()!=null){
				setModifiedBy(session.getUserName());
				setModifiedDate(new Date());
				setIsActive(Constants.U);
				setApprovedBy(null);
				setApprovedDate(null);
				setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
			}else{
				setModifiedBy(sourceOfIncdtObj.getModifiedBy());
				setModifiedDate(sourceOfIncdtObj.getModifiedDate());
				setIsActive(sourceOfIncdtObj.getIsActive());
				setApprovedBy(sourceOfIncdtObj.getApprovedBy());
				setApprovedDate(sourceOfIncdtObj.getApprovedDate());
				setDynamicLabelForActivateDeactivate(sourceOfIncdtObj.getDynamicLabelForActivateDeactivcate());
				 		}
				setRemarks(sourceOfIncdtObj.getRemarks());
				setIfEditClicked( true);
				srcIncDTList.remove(sourceOfIncdtObj);
				srcIncViewDTList.remove(sourceOfIncdtObj);
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
		
	}
	
	public void remarkSelectedRecord(){
		try {
			setBooVisible(false);
			for(SourceOfIncomeDataTableBean  srcInc :srcIncDTList){
				if(srcInc.getRemarksCheck()!=null){
				if(srcInc.getRemarksCheck().equals(true)){
					if(getRemarks()!=null){
						srcInc.setRemarks(getRemarks());
					updateRecord(srcInc);
					viewAll();
					clearAll();
					}else{
						RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
					}
				}
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
	
	public void clearRemarks(){
		setRemarks( null);
	}
	
	public void approve(){
		
		setBooVisible(false);
 
		try {
			
			String	approveMsg=isourceOfIncomeService.approveRecord( getSourceOfIncomePk(),  session.getUserName());
			if(approveMsg.equalsIgnoreCase("Success")){
					
				RequestContext.getCurrentInstance().execute("approve.show();");
			}
			else{
				RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
			}
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}
	
	public void populateAllFields(){
				 
		setSrcIncomeEngFullDesc(null);
		setSrcIncomeEngShortDesc( null);
		setSrcIncomeArabicFullDesc(null);
		setSrcIncomeArabicShortDesc( null);
		setSourceOfIncomePk( null);
		setSourceOfIncomeEngPk( null);
		setSourceOfIncomeArabicPk( null);
			 
		 if(getSourceCode()!=null){
		 List<SourceOfIncomeDescription> populationList=	isourceOfIncomeService.getSourceOfIncomeRecordsBanedOnCode(sourceCode);
		log.info( "list SIZE IS="+populationList.size());
		 if(populationList.size()>0){
			 log.info( "INSIDE="+populationList.size());
			for(SourceOfIncomeDescription sourceDesc:populationList){
				if(sourceDesc.getLanguageId().getLanguageId().toString().equalsIgnoreCase( Constants.ENGLISH_LANGUAGE_ID)){
					setSrcIncomeEngFullDesc( populationList.get( 0).getSourceOfIncomeFullDesc());
					setSrcIncomeEngShortDesc( populationList.get( 0).getSourceOfIncomeShortDesc());
				}	else{
					setSrcIncomeArabicFullDesc(populationList.get( 0).getSourceOfIncomeFullDesc() );
					setSrcIncomeArabicShortDesc( populationList.get( 0).getSourceOfIncomeShortDesc());
				}
			}
			RequestContext.getCurrentInstance().execute("recordAlreadyExist1.show();");
 
			return;
		}
	} 
	 }
	
	public void clearPopulatedData(){
		setSourceCode( null);
		setSrcIncomeEngShortDesc(null);
		setSrcIncomeEngFullDesc(null);
		setSrcIncomeArabicFullDesc(null);
		setSrcIncomeArabicShortDesc(null);
		setSourceOfIncomePk(null);
		setSourceOfIncomeEngPk( null);
		setSourceOfIncomeArabicPk( null);
	}
	
	
	
	
	
	
	
	

}
