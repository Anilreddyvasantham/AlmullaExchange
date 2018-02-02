package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.AmiecAndBankMapping;
import com.amg.exchange.remittance.service.IAdditionalBankRuleAddService;
import com.amg.exchange.remittance.service.IAdditionalBankRuleAmiecService;
import com.amg.exchange.remittance.service.IAdditionalBankRuleMapService;
import com.amg.exchange.remittance.service.IAmiecAndBankMappingService;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
            
@Component("additionalBankMapping")
@Scope("session")
public class AdditionalBankMappingBean<T> implements Serializable {

	Logger log = Logger.getLogger(AdditionalBankMappingBean.class);

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IAdditionalBankRuleMapService additionalBankRuleMapService;

	@Autowired
	IAdditionalBankRuleAmiecService additionalBankRuleAmiecService;

	@Autowired
	IAdditionalBankRuleAddService additionalBankRuleAddService;

	@Autowired
	IAmiecAndBankMappingService amiecAndBankMappingService;

	private static final long serialVersionUID = 1L;

	private BigDecimal AdditionalBankRuleId;
	private String fieldName;
	private String flexField;
	private BigDecimal orderNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private boolean flag=false;
	private String approveBy;
	private Date approvDate;
	private String isActive;
	private BigDecimal countryId;
	private String remarks;
	private String dataActive;
	
	private BigDecimal countryForView;

	private Boolean editClicked=false;
	private BigDecimal additionalBankRuleDetailId;
	private String amiecCode;
	private String amiecDescription;
	private BigDecimal bankId;
	private String additionalData;
	private String additionalDescription;

	private String bankCode;
	private String bankDescription;

	private boolean dataTableRendered = false;
	private boolean amiecAndBankMappingRendered = false;
	private boolean savePanelRender = false;
	private boolean exitAdditionalBankRuleFinal = false;
	private Boolean disableEditAMICMapping=false;
	private Boolean disableClearButton=false;
	private Boolean duplicateCheckFlag=false;

	private String dupRecord;
	private boolean disableSubmit=false;

	private SessionStateManage sessionmanager = new SessionStateManage();

	@SuppressWarnings("unused")
	private List<CountryMasterDesc> countryList = new ArrayList<CountryMasterDesc>();

	@SuppressWarnings("unused")
	private List<AdditionalBankRuleMap> flexFieldList = new ArrayList<AdditionalBankRuleMap>();

	@SuppressWarnings("unused")
	private List<BankMaster> bankList = new ArrayList<BankMaster>();

	@SuppressWarnings("unused")
	private List<AdditionalBankRuleAmiec> amiecList = new ArrayList<AdditionalBankRuleAmiec>();

	private List<AdditionalBankRuleAddData> additionalBankList = new ArrayList<AdditionalBankRuleAddData>();
	private List<AdditionalBankRuleAmiec> listAmecode = new ArrayList<AdditionalBankRuleAmiec>();
	private List<AdditionalBankRuleAddData> listBankcode = new ArrayList<AdditionalBankRuleAddData>();
	private List<AdditionalBankRuleDataTable> additionalBankRuleDataListFinal = new CopyOnWriteArrayList<AdditionalBankRuleDataTable>();
	private List<AdditionalBankRuleDataTable> additionalBankRuleDataNewListFinal = new CopyOnWriteArrayList<AdditionalBankRuleDataTable>();
	private List<BankMaster> lisbanks=new ArrayList<BankMaster>();

	public List<AdditionalBankRuleDataTable> getAdditionalBankRuleDataListFinal() {
		return additionalBankRuleDataListFinal;
	}

	public void setAdditionalBankRuleDataListFinal(List<AdditionalBankRuleDataTable> additionalBankRuleDataListFinal) {
		this.additionalBankRuleDataListFinal = additionalBankRuleDataListFinal;
	}

	Map<BigDecimal, String> mapCountryList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapBankList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> amiecMapList = new HashMap<BigDecimal, String>();

	SessionStateManage sessionStateManage = new SessionStateManage();

	public BigDecimal getAdditionalBankRuleId() {
		return AdditionalBankRuleId;
	}

	public Boolean getDisableEditAMICMapping() {
		return disableEditAMICMapping;
	}

	
	public boolean isDataTableRendered() {
		return dataTableRendered;
	}

	
	public Boolean getDisableClearButton() {
		return disableClearButton;
	}

	public void setDisableClearButton(Boolean disableClearButton) {
		this.disableClearButton = disableClearButton;
	}

	public void setDataTableRendered(boolean dataTableRendered) {
		this.dataTableRendered = dataTableRendered;
	}

	public void setDisableEditAMICMapping(Boolean disableEditAMICMapping) {
		this.disableEditAMICMapping = disableEditAMICMapping;
	}


	public BigDecimal getCountryForView() {
		return countryForView;
	}

	public void setCountryForView(BigDecimal countryForView) {
		this.countryForView = countryForView;
	}

	public Boolean getEditClicked() {
		return editClicked;
	}

	public void setEditClicked(Boolean editClicked) {
		this.editClicked = editClicked;
	}

	public void setAmiecList(List<AdditionalBankRuleAmiec> amiecList) {
		this.amiecList = amiecList;
	}

	public List<AdditionalBankRuleAddData> getAdditionalBankList() {

		additionalBankList.addAll(additionalBankRuleAddService.getAdditionalBankList());
		return additionalBankList;
	}

	public void setAdditionalBankList(
			List<AdditionalBankRuleAddData> additionalBankList) {
		this.additionalBankList = additionalBankList;
	}

	public void setFlexFieldList(List<AdditionalBankRuleMap> flexFieldList) {
		this.flexFieldList = flexFieldList;
	}

	public void setLstBank(List<BankMaster> bankList) {
		this.bankList = bankList;
	}

	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}

	public void setAdditionalBankRuleId(BigDecimal additionalBankRuleId) {
		AdditionalBankRuleId = additionalBankRuleId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFlexField() {
		return flexField;
	}

	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}

	public BigDecimal getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
	}

	
	public boolean isDisableSubmit() {
		return disableSubmit;
	}

	public void setDisableSubmit(boolean disableSubmit) {
		this.disableSubmit = disableSubmit;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public boolean isSavePanelRender() {
		return savePanelRender;
	}

	public void setSavePanelRender(boolean savePanelRender) {
		this.savePanelRender = savePanelRender;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDataActive() {
		return dataActive;
	}

	public void setDataActive(String dataActive) {
		this.dataActive = dataActive;
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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getAdditionalBankRuleDetailId() {
		return additionalBankRuleDetailId;
	}

	public void setAdditionalBankRuleDetailId(
			BigDecimal additionalBankRuleDetailId) {
		this.additionalBankRuleDetailId = additionalBankRuleDetailId;
	}

	public String getAmiecCode() {
		return amiecCode;
	}

	public void setAmiecCode(String amiecCode) {
		this.amiecCode = amiecCode;
	}

	public String getAmiecDescription() {
		return amiecDescription;
	}

	public void setAmiecDescription(String amiecDescription) {
		this.amiecDescription = amiecDescription;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public String getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}

	public String getAdditionalDescription() {
		return additionalDescription;
	}

	public void setAdditionalDescription(String additionalDescription) {
		this.additionalDescription = additionalDescription;
	}


	public String getDupRecord() {
		return dupRecord;
	}

	public void setDupRecord(String dupRecord) {
		this.dupRecord = dupRecord;
	}


	public List<CountryMasterDesc> getCountryList() {
		List<CountryMasterDesc> countryList = new ArrayList<>();
		countryList.addAll(generalService.getCountryList(sessionStateManage.getLanguageId()));
		for (CountryMasterDesc countryMaster : countryList) {
			mapCountryList.put(countryMaster.getCountryMasterId(),
					countryMaster.getCountryName());
		}
		return countryList;
	}

	public List<BankMaster> getBankList() {
		List<BankMaster> bankList = new ArrayList<>();
		bankList.addAll(generalService.getAllBankListFromBankMaster());
		for (BankMaster bankMaster : bankList) {
			mapBankList.put(bankMaster.getBankId(),
					bankMaster.getBankFullName());
		}
		return bankList;
	}

	public List<AdditionalBankRuleMap> getFlexFieldList() {
		List<AdditionalBankRuleMap> flexFieldList = new ArrayList<>();
		flexFieldList.addAll(additionalBankRuleMapService.getAllFlexField());
		return flexFieldList;
	}


	
	public void addAdditionalBankFinalTable() {
		
		duplicateRecordCheckInDB();
		checkDuplicate();
		setDisableEditAMICMapping(false);
		setDisableClearButton(false);
		if (getBankId() != null && getCountryId() != null && getFlexField()!=null) {
			setDisableSubmit(false);
			AdditionalBankRuleDataTable additionalBankRuleDataTable = new AdditionalBankRuleDataTable();
			additionalBankRuleDataTable.setPkamaieBankMapping(getPkEditfun());
			additionalBankRuleDataTable.setCountryId(getCountryId());
			additionalBankRuleDataTable.setCountryName(mapCountryList.get(getCountryId()));
			additionalBankRuleDataTable.setBankId(getBankId());
			additionalBankRuleDataTable.setBankName(mapBankList.get(getBankId()));
			additionalBankRuleDataTable.setFlexField(getFlexField());
			additionalBankRuleDataTable.setAmiecCode(getAmiecCode());
			additionalBankRuleDataTable.setAmiecDescription(getAmiecDescription());
			additionalBankRuleDataTable.setBankCode(getBankCode());
			additionalBankRuleDataTable.setBankDescription(getBankDescription());
			
			if (getPkEditfun()!= null) {
				additionalBankRuleDataTable.setDataActive(null);
				additionalBankRuleDataTable.setIsActive(Constants.U);
				additionalBankRuleDataTable.setCreatedBy(getCreatedBy());
				additionalBankRuleDataTable.setCreatedDate(getCreatedDate());
				additionalBankRuleDataTable.setApproveBy(null);
				additionalBankRuleDataTable.setApproveDate(null);
				additionalBankRuleDataTable.setModifiedBy(sessionmanager.getUserName());
				additionalBankRuleDataTable.setModifiedDate(new Date());
				additionalBankRuleDataTable.setRemarks(null);
				additionalBankRuleDataTable.setEditClicked(getEditClicked());
				
			} else {
				additionalBankRuleDataTable.setDataActive(Constants.REMOVE);
				additionalBankRuleDataTable.setIsActive( Constants.U);
				additionalBankRuleDataTable.setCreatedBy(sessionStateManage.getUserName());
				additionalBankRuleDataTable.setCreatedDate(new Date());
				additionalBankRuleDataTable.setEditClicked(true);
				additionalBankRuleDataNewListFinal.add(additionalBankRuleDataTable);
			}
			additionalBankRuleDataListFinal.add(additionalBankRuleDataTable);
			clearFinal();
		} 
		if (additionalBankRuleDataListFinal.size() != 0) {
			setSavePanelRender(true);
			setDataTableRendered(true);
		} else {
			setSavePanelRender(false);
			setDataTableRendered(false);
		}

	}

	public void saveAdditionalBankRuleFinal() {

		if (additionalBankRuleDataListFinal.size()!=0){
			try {
				for(AdditionalBankRuleDataTable amiecBankMappingObj : additionalBankRuleDataListFinal) {
					if(amiecBankMappingObj.getEditClicked().equals(true)){
					AmiecAndBankMapping amiecAndBankMapping = new AmiecAndBankMapping();
					amiecAndBankMapping.setAmiecAndBankMappingId(amiecBankMappingObj.getPkamaieBankMapping());
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(amiecBankMappingObj.getCountryId());
					amiecAndBankMapping.setCountryId(countryMaster);
					
					BankMaster bankMaster = new BankMaster();
					bankMaster.setBankId(amiecBankMappingObj.getBankId());
					amiecAndBankMapping.setBankId(bankMaster);
					
					amiecAndBankMapping.setFlexField(amiecBankMappingObj.getFlexField());
					amiecAndBankMapping.setAmiecCode(amiecBankMappingObj.getAmiecCode());
					amiecAndBankMapping.setAmiecDescription(amiecBankMappingObj.getAmiecDescription());
					amiecAndBankMapping.setBankCode(amiecBankMappingObj.getBankCode());
					amiecAndBankMapping.setBankDecription(amiecBankMappingObj.getBankDescription());
					
					amiecAndBankMapping.setCreatedBy(amiecBankMappingObj.getCreatedBy());
					amiecAndBankMapping.setCreatedDate(amiecBankMappingObj.getCreatedDate());
					amiecAndBankMapping.setModifiedBy(amiecBankMappingObj.getModifiedBy());
					amiecAndBankMapping.setModifiedDate(amiecBankMappingObj.getModifiedDate());
					amiecAndBankMapping.setApprovedBy(amiecBankMappingObj.getApproveBy());
					amiecAndBankMapping.setApprovedDate(amiecBankMappingObj.getApproveDate());
					amiecAndBankMapping.setRemarks(amiecBankMappingObj.getRemarks());
					amiecAndBankMapping.setIsActive(amiecBankMappingObj.getIsActive());
					
					amiecAndBankMappingService.save(amiecAndBankMapping);
					}
				}
			} catch (Exception e) {
				log.info("saving problam");
			}
			additionalBankRuleDataListFinal.clear();
			additionalBankRuleDataNewListFinal.clear();
			RequestContext.getCurrentInstance().execute("complete.show();");
			clearFinal();
		}
	}

	public void editFinalRecord(AdditionalBankRuleDataTable additionalBankRuleDataTableObj) {
		
		setDisableEditAMICMapping(true);
		setDisableSubmit(true);
		setEditClicked(true);
		setDisableClearButton(true);
		setPkEditfun(additionalBankRuleDataTableObj.getPkEditfun());
		
		setCreatedBy(additionalBankRuleDataTableObj.getCreatedBy());
		setCreatedDate(additionalBankRuleDataTableObj.getCreatedDate());
		setRemarks(additionalBankRuleDataTableObj.getRemarks());
		setCountryId(additionalBankRuleDataTableObj.getCountryId());
		setBankId(additionalBankRuleDataTableObj.getBankId());
		
		getFieldNameByFlexField(additionalBankRuleDataTableObj.getCountryId());
		popFlexEdit(additionalBankRuleDataTableObj.getCountryId());
		
		setFlexField(additionalBankRuleDataTableObj.getFlexField());
		
		popAmaicodeEdit(additionalBankRuleDataTableObj.getCountryId(),additionalBankRuleDataTableObj.getFlexField());
		
		setAmiecCode(additionalBankRuleDataTableObj.getAmiecCode());
		setAmiecDescription(additionalBankRuleDataTableObj.getAmiecDescription());
		
		popBankCodeEdit(additionalBankRuleDataTableObj.getCountryId(),additionalBankRuleDataTableObj.getBankId(),additionalBankRuleDataTableObj.getFlexField());
		
		setBankCode(additionalBankRuleDataTableObj.getBankCode());
		setBankDescription(additionalBankRuleDataTableObj.getBankDescription());
		
		if(additionalBankRuleDataTableObj.getPkEditfun()!=null){
			setModifiedBy(sessionmanager.getUserName());
			setModifiedDate(new Date());
			setIsActive(Constants.U);
			setApproveBy(null);
			setApprovDate(null);
			setDataActive(null);
		}else{
			setModifiedBy(additionalBankRuleDataTableObj.getModifiedBy());
			setModifiedDate(additionalBankRuleDataTableObj.getModifiedDate());
			setIsActive(additionalBankRuleDataTableObj.getIsActive());
			setApproveBy(additionalBankRuleDataTableObj.getApproveBy());
			setApprovDate(additionalBankRuleDataTableObj.getApproveDate());
			setDataActive(additionalBankRuleDataTableObj.getDataActive());
		}
		
		additionalBankRuleDataListFinal.remove(additionalBankRuleDataTableObj);
		additionalBankRuleDataNewListFinal.remove(additionalBankRuleDataTableObj);
		if (additionalBankRuleDataListFinal.size() != 0) {
			setSavePanelRender(true);
			setDataTableRendered(true);
		} else {
			setSavePanelRender(false);
			setDataTableRendered(false);
		}
	}

	public void popFlexEdit(BigDecimal countryId) {
		additionalBankRuleMaps = additionalBankRuleMapService.getAdditionalFlexField(countryId);
	}

	public void popAmaicodeEdit(BigDecimal countryId, String flexField) {
		listAmecode = amiecAndBankMappingService.getAmielist(countryId,flexField);
	}

	public void popBankCodeEdit(BigDecimal countryId, BigDecimal bankId,String flexField) {
		listBankcode = amiecAndBankMappingService.getListBankCode(countryId,bankId, flexField);
	}

	public void getFieldNameByFlexField(BigDecimal countryId) {
		List<AdditionalBankRuleMap> fieldList = additionalBankRuleMapService.getAdditionalFlexField(countryId);
		setFieldName(null);
		if (fieldList.size() != 0) {
			setFieldName(fieldList.get(0).getFieldName());
		}
	}

	public void clickOnOKFinalSaveExit() {
		setSavePanelRender(false);
		setDataTableRendered(false);
		additionalBankRuleDataListFinal.clear();
		additionalBankRuleDataNewListFinal.clear();
		clearFinal();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearFinal() {
	
		setBankId(null);
		setFlexField(null);
		setFieldName(null);
		setAmiecCode(null);
		setAmiecDescription(null);
		setBankCode(null);
		setBankDescription(null);
		setPkEditfun(null);
		setDataActive(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setApproveBy(null);
		setApprovDate(null);
		setRemarks(null);
		setCountryId(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setIsActive(null);
	
	}

	public void duplicateRecordCheckInDB(){
		List<AmiecAndBankMapping> amiecBankList=	amiecAndBankMappingService.dupliacteRecordCheckInDB(getCountryId(), getBankId(), getFlexField(), getAmiecCode(), getBankCode());
		if(amiecBankList.size()>0 && getEditClicked().equals(false)){
			RequestContext.getCurrentInstance().execute("existInDB.show();");
			clearFinal();
			return;
		}
	}
	public void getAmiecDescriptionByCode() {

		List<AdditionalBankRuleAmiec> amiecList = amiecAndBankMappingService.getpopDescription(getAmiecCode());
		setAmiecDescription(null);
		if (amiecList.size() != 0) {
			setAmiecDescription(amiecList.get(0).getAmiecDescription());
		}

	}

	public void getBankDescriptionByCode() {

		List<AdditionalBankRuleAddData> bankList = additionalBankRuleAddService.getBankDescription(getBankCode());
		setBankDescription(null);
		if (bankList.size() != 0) {

			setBankDescription(bankList.get(0).getAdditionalDescription());

		}

	}

	public void getFieldNameByFlexField() {
		popAmaicode();
		setFieldName(null);
		setBankCode(null);
		setBankDescription(null);
		setAmiecCode(null);
		setAmiecDescription(null);
		fieldsName=amiecAndBankMappingService.getFieldName(getFlexField(),getCountryId());
		try{
		if(!"".equalsIgnoreCase(fieldsName))
		{
		setFieldName(getFieldsName());
		}
		}
		catch(IndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}

	}

	public void cancelFromApproval() throws IOException{
		setRenderApproveCancelButtonPanel(false);
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AMIECAndBankMappingApproval.xhtml");
	}
	
	public void approveRecords(){
		try{
			String approveMsg=amiecAndBankMappingService.approveRecord(getAmiecBankMapPK(),sessionmanager.getUserName());
			if(approveMsg.equalsIgnoreCase("Sucess"))
			{
				RequestContext.getCurrentInstance().execute("approved.show();");
				return;
			}else
			{
				RequestContext.getCurrentInstance().execute("notApproved.show();");
				return;
			}
			
			}catch(Exception exception){
				exception.getMessage();
			}
			
	}
	
	public void clickOnOKGotoAmiecPanel() throws IOException{
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AMIECAndBankMappingApproval.xhtml");
	}
	
	public void additionalBankRulePageNavigationFour() {
		setRenderAddViewButtonPanel(true);
		setRenderApproveCancelButtonPanel(false);
		setRenderCountryId(true);
		setRenderBankId(true);
		setRenderFlexField(true);
		setRenderAlmullaCode(true);
		setRenderBankCode(true);
		setRenderBankIdForApprove(false);
		setRenderCountryIdForApprove(false);
		setRenderFlexFieldForApprove(false);
		setRenderAlmullaCodeForApprove(false);
		setRenderBankCodeForApprove(false);
		setCreatedByForApprove(null);
		setAmiecBankMapPK(null);
		setFieldsName(null);
		setDisableEditAMICMapping(false);	
		setDisableClearButton(false);
		setAmiecAndBankMappingRendered(false);
		setSavePanelRender(false);
		setDataTableRendered(false);
		setDisableSubmit(false);
		setEditClicked(false);
		additionalBankRuleDataListFinal.clear();
		clearFinal();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/amiecandbankmapping.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private Boolean booRenderSaveExit = false;

	public Boolean getBooRenderSaveExit() {
		return booRenderSaveExit;
	}

	public void setBooRenderSaveExit(Boolean booRenderSaveExit) {
		this.booRenderSaveExit = booRenderSaveExit;
	}

	private Boolean booRenderSave = false;

	public Boolean getBooRenderSave() {
		return booRenderSave;
	}

	public void setBooRenderSave(Boolean booRenderSave) {
		this.booRenderSave = booRenderSave;
	}
	private Boolean booRenderSaveOne = false;

	public Boolean getBooRenderSaveOne() {
		return booRenderSaveOne;
	}

	public void setBooRenderSaveOne(Boolean booRenderSaveOne) {
		this.booRenderSaveOne = booRenderSaveOne;
	}

	private List<AdditionalBankRuleMap> additionalBankRuleMaps = new ArrayList<AdditionalBankRuleMap>();

	public List<AdditionalBankRuleMap> getAdditionalBankRuleMaps() {
		return additionalBankRuleMaps;
	}

	public void setAdditionalBankRuleMaps( List<AdditionalBankRuleMap> additionalBankRuleMaps) {
		this.additionalBankRuleMaps = additionalBankRuleMaps;
	}

	public void popFlex() {
		setBankId(null);
		setFlexField(null);
		setFieldName(null);
		popBank();
		additionalBankRuleMaps = additionalBankRuleMapService.getAdditionalFlexField(getCountryId());
		setAdditionalBankRuleMaps(additionalBankRuleMaps);
	}

	public void populateAmiecDescription() {
		setDupRecord("");
		List<AdditionalBankRuleAmiec> additionalBankRuleAmiecList = new ArrayList<AdditionalBankRuleAmiec>();
		additionalBankRuleAmiecList = additionalBankRuleAmiecService.populateAmiecDescription(getCountryId(), getFlexField(),getAmiecCode());
		if (additionalBankRuleAmiecList.size() > 0) {
			setAmiecDescription(additionalBankRuleAmiecList.get(0).getAmiecDescription());
			setDupRecord("Yes");
			RequestContext.getCurrentInstance().execute("succ.show();");
			return;
		} else {
		}

	}


	// Additional Description
	public void populateAdditionalDescription() {

		setDupRecord("");
		List<AdditionalBankRuleAddData> additionalBankRuleAddData = new ArrayList<AdditionalBankRuleAddData>();
		additionalBankRuleAddData = additionalBankRuleAmiecService.populateAdditionalDescription(getCountryId(), getBankId(),getFlexField(), getAdditionalData());
		if (additionalBankRuleAddData.size() > 0) {
			setAdditionalDescription(additionalBankRuleAddData.get(0).getAdditionalDescription());
			setDupRecord("Yes");
			RequestContext.getCurrentInstance().execute("succ.show();");
			return;

		} else {
			setDupRecord("No");
		}

	}

	public boolean isAmiecAndBankMappingRendered() {
		return amiecAndBankMappingRendered;
	}

	public void setAmiecAndBankMappingRendered(
			boolean amiecAndBankMappingRendered) {
		this.amiecAndBankMappingRendered = amiecAndBankMappingRendered;
	}

	public String getBankDescription() {
		return bankDescription;
	}

	public void setBankDescription(String bankDescription) {
		this.bankDescription = bankDescription;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public boolean isExitAdditionalBankRuleFinal() {
		return exitAdditionalBankRuleFinal;
	}

	public void setExitAdditionalBankRuleFinal(
			boolean exitAdditionalBankRuleFinal) {
		this.exitAdditionalBankRuleFinal = exitAdditionalBankRuleFinal;
	}

	List<AdditionalBankRuleAddData> listBank = new ArrayList<AdditionalBankRuleAddData>();

	public List<AdditionalBankRuleAddData> getListBank() {
		return listBank;
	}

	public void setListBank(List<AdditionalBankRuleAddData> listBank) {
		this.listBank = listBank;
	}


	public void view() {
		setDisableSubmit(false);
		setDisableEditAMICMapping(false);
	
			List<AmiecAndBankMapping>	listamiecBankmapping = amiecAndBankMappingService.getBankAmiecAndBankMapping();
			if (listamiecBankmapping.size() > 0) {
				additionalBankRuleDataListFinal.clear();
			for (AmiecAndBankMapping amaec : listamiecBankmapping) {
				AdditionalBankRuleDataTable	additionalBankRuleDataTable = new AdditionalBankRuleDataTable();
				if (amaec.getIsActive().equalsIgnoreCase(Constants.Yes)) {
					additionalBankRuleDataTable.setDataActive(Constants.DEACTIVATE);
				} else if (amaec.getIsActive().equalsIgnoreCase(Constants.D)) {
					additionalBankRuleDataTable.setDataActive(Constants.ACTIVATE);
				}else if(amaec.getIsActive().equalsIgnoreCase(Constants.U)&& amaec.getModifiedBy()==null && amaec.getModifiedDate()==null
						&& amaec.getApprovedBy()==null && amaec.getApprovedDate()==null 
						&& amaec.getRemarks()==null){
					additionalBankRuleDataTable.setDataActive(Constants.DELETE);
				}else {
					additionalBankRuleDataTable.setDataActive("");
				}
				additionalBankRuleDataTable.setCountryId(amaec.getCountryId().getCountryId());
				additionalBankRuleDataTable.setCountryName(generalService.getCountryName(amaec.getCountryId().getCountryId()));
				additionalBankRuleDataTable.setBankId(amaec.getBankId().getBankId());
				additionalBankRuleDataTable.setBankName(mapBankList.get(amaec.getBankId().getBankId()));
				additionalBankRuleDataTable.setFlexField(amaec.getFlexField());
				additionalBankRuleDataTable.setAmiecCode(amaec.getAmiecCode());
				additionalBankRuleDataTable.setAmiecDescription(amaec.getAmiecDescription());
				additionalBankRuleDataTable.setBankCode(amaec.getBankCode());
				additionalBankRuleDataTable.setBankDescription(amaec.getBankDecription());
				additionalBankRuleDataTable.setPkEditfun(amaec.getAmiecAndBankMappingId());

				additionalBankRuleDataTable.setCreatedBy(amaec.getCreatedBy());
				additionalBankRuleDataTable.setCreatedDate(amaec.getCreatedDate());
				additionalBankRuleDataTable.setModifiedBy(amaec.getModifiedBy());
				additionalBankRuleDataTable.setModifiedDate(amaec.getModifiedDate());
				additionalBankRuleDataTable.setApproveBy(amaec.getApprovedBy());
				additionalBankRuleDataTable.setApproveDate(amaec.getApprovedDate());
				additionalBankRuleDataTable.setRemarks(amaec.getRemarks());
				additionalBankRuleDataListFinal.add(additionalBankRuleDataTable);

			}
			additionalBankRuleDataListFinal.addAll(additionalBankRuleDataNewListFinal);	
			setSavePanelRender(true);
			setDataTableRendered(true);
			clearFinal();
		} else{
			RequestContext.getCurrentInstance().execute("noRecords.show();");
			
		}

	}

	private BigDecimal pkEditfun = null;

	public BigDecimal getPkEditfun() {
		return pkEditfun;
	}

	public void setPkEditfun(BigDecimal pkEditfun) {
		this.pkEditfun = pkEditfun;
	}



	public List<AdditionalBankRuleAmiec> getListAmecode() {
		return listAmecode;
	}

	public void setListAmecode(List<AdditionalBankRuleAmiec> listAmecode) {
		this.listAmecode = listAmecode;
	}

	public List<AdditionalBankRuleAddData> getListBankcode() {
		return listBankcode;
	}

	public void setListBankcode(List<AdditionalBankRuleAddData> listBankcode) {
		this.listBankcode = listBankcode;
	}

	public void popAmaicode() {
		popBankCode();
		listAmecode = amiecAndBankMappingService.getAmielist(getCountryId(),getFlexField());

	}

	public void popBankCode() {
		listBankcode = amiecAndBankMappingService.getListBankCode( getCountryId(), getBankId(), getFlexField());

	}

	public void checkDuplicate() {
		if (additionalBankRuleDataListFinal.size() > 0) {
			for (AdditionalBankRuleDataTable addData : additionalBankRuleDataListFinal) {
				try {

					if (addData.getBankId().compareTo(getBankId()) == 0 && addData.getAmiecCode().equals(getAmiecCode())
							&& addData.getBankCode().equals(getBankCode()) && addData.getCountryId().compareTo(getCountryId())==0 && addData.getBankId().compareTo(getBankId())==0)  {
						RequestContext.getCurrentInstance().execute("datatable.show();");
						clearFinal();
						return;
					}
				} catch (NullPointerException e) {
					log.info("null pointer exception handled");
				}
			}
		}

	}


	// rahamthali ---->for approve

	private Boolean renderAddViewButtonPanel = false;
	private Boolean renderApproveCancelButtonPanel = false;
	private BigDecimal amiecBankMapPK;
	private String createdByForApprove;
	private Boolean renderCountryIdForApprove=false;
	private Boolean renderCountryId=false;
	private Boolean renderBankId=false;
	private Boolean renderBankIdForApprove=false;
	private Boolean renderFlexField=false;
	private Boolean renderFlexFieldForApprove=false;
	private Boolean renderAlmullaCode=false;
	private Boolean renderAlmullaCodeForApprove=false;
	private Boolean renderBankCode=false;
	private Boolean renderBankCodeForApprove=false;
	
	private String countryName;
	private String bankName;
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Boolean getRenderCountryIdForApprove() {
		return renderCountryIdForApprove;
	}

	public void setRenderCountryIdForApprove(Boolean renderCountryIdForApprove) {
		this.renderCountryIdForApprove = renderCountryIdForApprove;
	}

	public Boolean getRenderCountryId() {
		return renderCountryId;
	}

	public void setRenderCountryId(Boolean renderCountryId) {
		this.renderCountryId = renderCountryId;
	}

	public Boolean getRenderBankId() {
		return renderBankId;
	}

	public void setRenderBankId(Boolean renderBankId) {
		this.renderBankId = renderBankId;
	}

	public Boolean getRenderBankIdForApprove() {
		return renderBankIdForApprove;
	}

	public void setRenderBankIdForApprove(Boolean renderBankIdForApprove) {
		this.renderBankIdForApprove = renderBankIdForApprove;
	}

	public Boolean getRenderFlexField() {
		return renderFlexField;
	}

	public void setRenderFlexField(Boolean renderFlexField) {
		this.renderFlexField = renderFlexField;
	}

	public Boolean getRenderFlexFieldForApprove() {
		return renderFlexFieldForApprove;
	}

	public void setRenderFlexFieldForApprove(Boolean renderFlexFieldForApprove) {
		this.renderFlexFieldForApprove = renderFlexFieldForApprove;
	}

	public Boolean getRenderAlmullaCode() {
		return renderAlmullaCode;
	}

	public void setRenderAlmullaCode(Boolean renderAlmullaCode) {
		this.renderAlmullaCode = renderAlmullaCode;
	}

	public Boolean getRenderAlmullaCodeForApprove() {
		return renderAlmullaCodeForApprove;
	}

	public void setRenderAlmullaCodeForApprove(Boolean renderAlmullaCodeForApprove) {
		this.renderAlmullaCodeForApprove = renderAlmullaCodeForApprove;
	}

	public Boolean getRenderBankCode() {
		return renderBankCode;
	}

	public void setRenderBankCode(Boolean renderBankCode) {
		this.renderBankCode = renderBankCode;
	}

	public Boolean getRenderBankCodeForApprove() {
		return renderBankCodeForApprove;
	}

	public void setRenderBankCodeForApprove(Boolean renderBankCodeForApprove) {
		this.renderBankCodeForApprove = renderBankCodeForApprove;
	}

	public String getCreatedByForApprove() {
		return createdByForApprove;
	}

	public void setCreatedByForApprove(String createdByForApprove) {
		this.createdByForApprove = createdByForApprove;
	}

	public BigDecimal getAmiecBankMapPK() {
		return amiecBankMapPK;
	}

	public void setAmiecBankMapPK(BigDecimal amiecBankMapPK) {
		this.amiecBankMapPK = amiecBankMapPK;
	}

	public Boolean getRenderApproveCancelButtonPanel() {
		return renderApproveCancelButtonPanel;
	}

	public void setRenderApproveCancelButtonPanel(
			Boolean renderApproveCancelButtonPanel) {
		this.renderApproveCancelButtonPanel = renderApproveCancelButtonPanel;
	}

	public Boolean getRenderAddViewButtonPanel() {
		return renderAddViewButtonPanel;
	}

	public void setRenderAddViewButtonPanel(Boolean renderAddViewButtonPanel) {
		this.renderAddViewButtonPanel = renderAddViewButtonPanel;
	}

	// rahamth ----->for approving
	
	
	public void popBank()
	{
		lisbanks=amiecAndBankMappingService.getBanklist(getCountryId());
	}
	
	
	
			

	public List<BankMaster> getLisbanks() {
		return lisbanks;
	}

	public void setLisbanks(List<BankMaster> lisbanks) {
		this.lisbanks = lisbanks;
	}
	
	private String fieldsName;

	public String getFieldsName() {
		return fieldsName;
	}

	public void setFieldsName(String fieldsName) {
		this.fieldsName = fieldsName;
	}

	
	public void removRecData(AdditionalBankRuleDataTable additionalBankRuleDataTableObj)
	{
		if (additionalBankRuleDataTableObj.getPkamaieBankMapping() == null) {
			setCountryId(null);
			setBankId(null);
			setFlexField(null);
			setFieldName(null);
			setAmiecCode(null);
			setAmiecDescription(null);
			setBankCode(null);
			setBankDescription(null);
			setPkEditfun(null);
			additionalBankRuleDataListFinal.remove(additionalBankRuleDataTableObj);
		} 
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public void checkStatusType(AdditionalBankRuleDataTable additionalBankRule)
	{
		if(additionalBankRule.getDataActive().equalsIgnoreCase(Constants.REMOVE)){
			additionalBankRuleDataListFinal.remove(additionalBankRule);
			additionalBankRuleDataNewListFinal.remove(additionalBankRule);
		}else if (additionalBankRule.getDataActive().equalsIgnoreCase(Constants.DEACTIVATE)) {
			additionalBankRule.setRemarkCheck(true);
			setApprovDate(additionalBankRule.getApproveDate());
			setApproveBy(additionalBankRule.getApproveBy());
			RequestContext.getCurrentInstance().execute("remarks.show();");
			return;
		}else if (additionalBankRule.getDataActive().equalsIgnoreCase(Constants.ACTIVATE)) {
			additionalBankRule.setActRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;
		}else if (additionalBankRule.getDataActive().equalsIgnoreCase(Constants.DELETE)) {
			additionalBankRule.setPermanetDeleteCheck(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			return;
		}	
		
		if (additionalBankRuleDataListFinal.size() != 0) {
			setSavePanelRender(true);
			setDataTableRendered(true);
		} else {
			setSavePanelRender(false);
			setDataTableRendered(false);
		}
	}
	
	public void activateRecord(){
		if(additionalBankRuleDataListFinal.size()>0){
		for(AdditionalBankRuleDataTable additionalBankRule:additionalBankRuleDataListFinal){
			if(additionalBankRule.getActRecordCheck()!=null){
			if(additionalBankRule.getActRecordCheck().equals(true)){
				amiecAndBankMappingService.activateRecord(additionalBankRule.getPkEditfun(), sessionmanager.getUserName());
				view();
				return;
			}
			}
		}
		}
	}
	
	public void confirmPermanentDelete(){
		if(additionalBankRuleDataListFinal.size()>0){
		for(AdditionalBankRuleDataTable additionalBankRule:additionalBankRuleDataListFinal){
			if(additionalBankRule.getPermanetDeleteCheck()!=null){
			if(additionalBankRule.getPermanetDeleteCheck().equals(true)){
				amiecAndBankMappingService.deleteRecord(additionalBankRule.getPkEditfun());
				additionalBankRuleDataListFinal.remove(additionalBankRule);
				return;
			}
			}
	}
		}
	}
	
	public void remarkSelected()
	{
		for(AdditionalBankRuleDataTable additionalBankRule:additionalBankRuleDataListFinal){
			if(additionalBankRule.getRemarkCheck()!=null){
			if(additionalBankRule.getRemarkCheck().equals(true)){
				if(getRemarks()!=null){
				additionalBankRule.setRemarks(getRemarks());
				amiecAndBankMappingService.remarkRecord(additionalBankRule.getPkEditfun(), getRemarks(), sessionmanager.getUserName());
				setRemarks(null);
				setApprovDate(null);
				setApproveBy(null);
				view();
				clearFinal();
			try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/amiecandbankmapping.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
				}else{
					RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
					return;
				}
			}
			}
		}
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public Date getApprovDate() {
		return approvDate;
	}

	public void setApprovDate(Date approvDate) {
		this.approvDate = approvDate;
	}
	
	//Added by kani for Enquiry screenss begin
	
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void additionalBankALMCEnquiryPageNavigation() {
		
		try {
			enquiryView();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionmanager.getCountryId(), sessionmanager.getUserType(), sessionmanager.getUserName(), "AMIECAndBankMappingEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AMIECAndBankMappingEnquiry.xhtml ");
			
		}catch(NullPointerException ne){
			  log.info("Method Name::viewRecordAddBankData"+ne.getMessage());
			    return; 
		} catch (Exception exception) {
			log.info("Method Name::viewRecordAddBankData "+exception.getMessage());
			return;
		}
	}
	
	//Added by kani for isActive status in word Begin
		private String activeStatusInWord;
		
		
		public String getActiveStatusInWord() {
			return activeStatusInWord;
		}

		public void setActiveStatusInWord(String activeStatusInWord) {
			this.activeStatusInWord = activeStatusInWord;
		}

	
	
	public void enquiryView() {
	
			RequestContext.getCurrentInstance().execute("coutryCheck.show();");
	
			additionalBankRuleDataListFinal.clear();
			List<AmiecAndBankMapping>	listamiecBankmapping = amiecAndBankMappingService.getBankAmiecAndBankMappingEnquiry();
			
		
		if (listamiecBankmapping.size() > 0) {
			AdditionalBankRuleDataTable additionalBankRuleDataTable = null;

			for (AmiecAndBankMapping amaec : listamiecBankmapping) {
				
				additionalBankRuleDataTable = new AdditionalBankRuleDataTable();
				additionalBankRuleDataTable.setAdditionalBankRulePk(amaec.getAmiecAndBankMappingId());
				additionalBankRuleDataTable.setCountryId(amaec.getCountryId().getCountryId());
				additionalBankRuleDataTable.setCountryName(generalService.getCountryName(amaec.getCountryId().getCountryId()));
				additionalBankRuleDataTable.setBankId(amaec.getBankId().getBankId());
				additionalBankRuleDataTable.setBankName(mapBankList.get(amaec.getBankId().getBankId()));
				additionalBankRuleDataTable.setFlexField(amaec.getFlexField());
				additionalBankRuleDataTable.setAmiecCode(amaec.getAmiecCode());
				additionalBankRuleDataTable.setAmiecDescription(amaec.getAmiecDescription());
				additionalBankRuleDataTable.setBankCode(amaec.getBankCode());
				additionalBankRuleDataTable.setBankDescription(amaec.getBankDecription());
				additionalBankRuleDataTable.setPkamaieBankMapping(amaec.getAmiecAndBankMappingId());

				additionalBankRuleDataTable.setCreatedBy(amaec.getCreatedBy());
				additionalBankRuleDataTable.setCreatedDate(amaec.getCreatedDate());
				
				additionalBankRuleDataTable.setApproveBy(amaec.getApprovedBy());
				additionalBankRuleDataTable.setApproveDate(amaec.getApprovedDate());
				
					if(amaec.getIsActive()!=null){
					
					if(amaec.getIsActive().equalsIgnoreCase(Constants.Yes)){
						
						setActiveStatusInWord("Approved");
						additionalBankRuleDataTable.setActiveStatusInWord("Approved");
									
						
					 }else if(amaec.getIsActive().equalsIgnoreCase(Constants.D)){
						 
						 additionalBankRuleDataTable.setActiveStatusInWord(Constants.DELETE);
						 setActiveStatusInWord("Approved");
						
					 }
					 else{
						 additionalBankRuleDataTable.setActiveStatusInWord("Un-Approved");
						 setActiveStatusInWord("Un-Approved");			 
					 }
					}
				
				

				additionalBankRuleDataListFinal.add(additionalBankRuleDataTable);

				setSavePanelRender(true);
				setDataTableRendered(true);
			
				clearFinal();
			}
		} else {
			RequestContext.getCurrentInstance().execute("nodata.show()");
	}

	}
	
	
	public void clearRemarksBankMap(){
		setRemarks(null);
	}
	
	// Added by kani for Enquiry screens end
	
	
	

}
