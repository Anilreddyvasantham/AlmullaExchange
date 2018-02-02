package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleFlexFieldView;
import com.amg.exchange.remittance.service.IAdditionalBankRuleAddService;
import com.amg.exchange.remittance.service.IAdditionalBankRuleMapService;
import com.amg.exchange.remittance.service.IAmiecAndBankMappingService;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.service.IBankIndicatorService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;


/*
 * Author RAHAMATHALI SHAIK
 */
@Component("additionBankRuleApprovalBean")
@Scope("session")
public class AdditionBankRuleApprovalBean<T> {
	
	 
	private static final Logger log=Logger.getLogger(AdditionBankRuleApprovalBean.class);
	

	
	@Autowired
	IAdditionalBankRuleAddService additionalBankRuleAddService;
	
	@Autowired
	IAmiecAndBankMappingService  amiecAndBankMappingService;
	
	@Autowired
	IAdditionalBankRuleMapService additionalBankRuleMapService;
	
	@Autowired
	IGeneralService generalService;
	
	@Autowired
	AdditionalBankRuleBean additionalBankRuleBean;
	
	@Autowired
	IBankIndicatorService bankIndicatorService;
	
	List<AdditionBankRuleApprovalDataTable> addtionBankRuledataTableList=new ArrayList<AdditionBankRuleApprovalDataTable>();
	private List<AdditionalBankRuleFlexFieldView> lstflexfieldsfromview = new ArrayList<AdditionalBankRuleFlexFieldView>();
	private List<CountryMasterDesc> countryList = new ArrayList<CountryMasterDesc>();
	
	private List<BankApplicability> bankList=new ArrayList<BankApplicability>();

	
	Map<BigDecimal, String> mapCountryList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapBankList = new HashMap<BigDecimal, String>();
	
	SessionStateManage session=new SessionStateManage();
	
	public List<AdditionalBankRuleFlexFieldView> getLstflexfieldsfromview() {
		return lstflexfieldsfromview;
	}

	public void setLstflexfieldsfromview(List<AdditionalBankRuleFlexFieldView> lstflexfieldsfromview) {
		this.lstflexfieldsfromview = lstflexfieldsfromview;
	}

	public String getFlexField() {
		return flexField;
	}

	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}
	public List<AdditionBankRuleApprovalDataTable> getAddtionBankRuledataTableList() {

		return addtionBankRuledataTableList;
	}

	public void setAddtionBankRuledataTableList(
			List<AdditionBankRuleApprovalDataTable> addtionBankRuledataTableList) {
		this.addtionBankRuledataTableList = addtionBankRuledataTableList;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	public List<CountryMasterDesc> getCountryList() {
		List<CountryMasterDesc> countryList = new ArrayList<>();
		countryList.addAll(generalService.getCountryList(session.getLanguageId()));
		for (CountryMasterDesc countryMaster : countryList) {
			mapCountryList.put(countryMaster.getCountryMasterId(),countryMaster.getCountryName());
		}
		return countryList;
		 
	}

	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}
	
public void fetchAllFlexFields(){
log.info( "FlexFiled And Country Poplated:::::::::::::::::::::::::::::::::::::::::::::::");
		populateCountryList();
		List<AdditionalBankRuleFlexFieldView> lstflexfields = additionalBankRuleMapService.tofetchAllFlexFieldsFromView();
		if(lstflexfields.size() != 0){
			setLstflexfieldsfromview(lstflexfields);
		}
		    
	}

public void populateCountryList(){
	log.info("countrylist::::::::::::::::::::::::::::::::::::"+getCountryId());
	//List<BankApplicability> corrBanksList=generalService.getCoresBankList(getCountryId());
	List<BankApplicability> bankListforCorresServiceP = new ArrayList<>();
	try{
		BigDecimal pkCorresBankInd = null;
		BigDecimal pkServProBankInd = null;

		List<BankIndicator> corrBankIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_CORR_BANK);

		List<BankIndicator> servProBankIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_SERVICEPRO_BANK);

		if(corrBankIndlist.size() != 0){
			pkCorresBankInd = corrBankIndlist.get(0).getBankIndicatorId();
		}

		if(servProBankIndlist.size() != 0){
			pkServProBankInd = servProBankIndlist.get(0).getBankIndicatorId();
		}

		if(pkCorresBankInd != null){
			bankListforCorresServiceP.addAll(generalService.getBankListbyIndicator(getCountryId(), pkCorresBankInd));
		}
		
		if(pkServProBankInd != null){
			bankListforCorresServiceP.addAll(generalService.getBankListbyIndicator(getCountryId(), pkServProBankInd));
		}
		
		setBankList(bankListforCorresServiceP);
		
	} catch (Exception exception) {
		log.info("Method Name::getBankList "+exception.getMessage());
		RequestContext.getCurrentInstance().execute("exception.show();");  
	}
	log.info("countrylistsize::::::::::::::::::::::::::::::::::::"+bankListforCorresServiceP.size());
	
}

	
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToAdditionBankRuleApprovalPage() throws IOException{
 	 setCountryId(null);
 	 setFlexField( null);
 	 setBankId(null);
 	lstflexfieldsfromview.clear();
 	bankList.clear();
 	 
 	addtionBankRuledataTableList.clear();
 	loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "AdditionBankRuleApproval.xhtml");
	FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionBankRuleApproval.xhtml");
	}

	public void approveAMIECBank(AdditionBankRuleApprovalDataTable bankRuleAppDataTable) throws IOException{
		//for populating all fileds in AMIEC AND BANK MAPPING
		if((bankRuleAppDataTable.getModifiedBy()==null ? bankRuleAppDataTable.getCreatedBy() : bankRuleAppDataTable.getModifiedBy()).equalsIgnoreCase(session.getUserName())){
			RequestContext.getCurrentInstance().execute("notApproved.show();");
		}else{
		additionalBankRuleBean.setCountryId(bankRuleAppDataTable.getCountryId());
		additionalBankRuleBean.setCountryName(bankRuleAppDataTable.getCountryName());
		additionalBankRuleBean.setBankId(bankRuleAppDataTable.getBankId());
		additionalBankRuleBean.setBankName(bankRuleAppDataTable.getBankName());
		additionalBankRuleBean.setFlexField(bankRuleAppDataTable.getFlexField());
		additionalBankRuleBean.setFlexFieldName(bankRuleAppDataTable.getFieldName());
		additionalBankRuleBean.setFieldName(bankRuleAppDataTable.getFieldName());
		additionalBankRuleBean.setAdditionalData(bankRuleAppDataTable.getAdditionalData());
		additionalBankRuleBean.setAdditionalDescription(bankRuleAppDataTable.getAdditionalDescription());
				
				//hide panel for approving
		additionalBankRuleBean.setRenderAddButtonPanel(false);
		additionalBankRuleBean.setRenderApproveCancelButtonPanel(true);
		additionalBankRuleBean.setSaveAdditionalBankRule3(false);
		additionalBankRuleBean.setAdditionalBankRuleAddDataRendered(false);
		additionalBankRuleBean.setAddtionBankRulePK(bankRuleAppDataTable.getAddtionalBankRulePK());
		additionalBankRuleBean.setCreatedByForApprove(bankRuleAppDataTable.getCreatedBy());
		additionalBankRuleBean.setRenderCountryId(false);
		additionalBankRuleBean.setRenderBankId(false);
		additionalBankRuleBean.setRenderFlexField(false);
		additionalBankRuleBean.setRenderBankCode(false);
		additionalBankRuleBean.setRenderBankIdForApprove(true);
		additionalBankRuleBean.setRenderCountryIdForApprove(true);
		additionalBankRuleBean.setRenderFlexFieldForApprove(true);
		additionalBankRuleBean.setRenderBankCodeForApprove(true);
		additionalBankRuleBean.setRenderBankDesc(false);		
		additionalBankRuleBean.setRenderBankDescForApprove(true);
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankruleadditionaldata.xhtml");
		}
		}
	
	public void fetchDataForApproval(){
		addtionBankRuledataTableList.clear();
		if(getCountryId()!=null&&getFlexField()!=null&&getBankId()!=null){
		List<AdditionalBankRuleAddData> addtionBankRuleList=additionalBankRuleAddService.getDataForApprovalList(getCountryId(),getFlexField(),getBankId());
		log.info("country====="+getCountryId());
		log.info("flexfield====="+getFlexField());
		log.info("bankId====="+getBankId());
		log.info("search method called size ::::::::::::::::::::::::::::::::::"+addtionBankRuleList.size());
		if(addtionBankRuleList.size()>0){
		for(AdditionalBankRuleAddData addtionBankRule:addtionBankRuleList){
			AdditionBankRuleApprovalDataTable addtionBankRuleDataTable=new AdditionBankRuleApprovalDataTable();
			
			addtionBankRuleDataTable.setAddtionalBankRulePK(addtionBankRule.getAdditionalBankRuleDataId());
			addtionBankRuleDataTable.setFlexField(addtionBankRule.getFlexField());
			addtionBankRuleDataTable.setFieldName(amiecAndBankMappingService.getFlexFieldName(addtionBankRule.getFlexField()));
			addtionBankRuleDataTable.setCountryId(addtionBankRule.getCountryId().getCountryId());
			addtionBankRuleDataTable.setCountryName(generalService.getCountryName(session.getLanguageId() ,addtionBankRule.getCountryId().getCountryId()));
			addtionBankRuleDataTable.setBankId(addtionBankRule.getBankId().getBankId());
			addtionBankRuleDataTable.setBankName(generalService.getBankName(addtionBankRule.getBankId().getBankId()));
			addtionBankRuleDataTable.setAdditionalData(addtionBankRule.getAdditionalData());
			addtionBankRuleDataTable.setAdditionalDescription(addtionBankRule.getAdditionalDescription());
			addtionBankRuleDataTable.setCreatedBy(addtionBankRule.getCreatedBy());
			addtionBankRuleDataTable.setModifiedBy(addtionBankRule.getModifiedBy());
			addtionBankRuledataTableList.add(addtionBankRuleDataTable);
		}
		}else{
			RequestContext.getCurrentInstance().execute("notrecordfound.show();");
		}
		
		}else{
			RequestContext.getCurrentInstance().execute("pleaseselect.show();");
		}
		
		
	}
	
	
	//New Approval Code
	
	
	
	private BigDecimal countryId;
	private String flexField;
	private BigDecimal bankId;
	private BigDecimal noOfRecordsApproved;
	private BigDecimal noOfRecordsNotApprove;
	
	
	public void approve(){
		int count=0;
		if(addtionBankRuledataTableList.size()>0){
		for(AdditionBankRuleApprovalDataTable addtionBankRuleDataTable:addtionBankRuledataTableList){
			if((addtionBankRuleDataTable.getModifiedBy()==null ? addtionBankRuleDataTable.getCreatedBy() : addtionBankRuleDataTable.getModifiedBy()).equalsIgnoreCase(session.getUserName())){
		 
			}else{
				count=count+1;
				String approveMsg=additionalBankRuleAddService.approveRecord(addtionBankRuleDataTable.getAddtionalBankRulePK(),session.getUserName());
				}
		}
		if(count>0){
			setNoOfRecordsApproved(new BigDecimal(count));
			RequestContext.getCurrentInstance().execute("approved.show();");
		return;	
		}else{
			RequestContext.getCurrentInstance().execute("notApproved.show();");
		}
				
	}else{
		RequestContext.getCurrentInstance().execute("notrecordfound.show();");
	}
		
	}
	public void search(){
		log.info("search method called ::::::::::::::::::::::::::::::::::");
		fetchDataForApproval();
	}
	

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public List<BankApplicability> getBankList() {
		return bankList;
	}

	public void setBankList(List<BankApplicability> bankList) {
		this.bankList = bankList;
	}

	public BigDecimal getNoOfRecordsApproved() {
		return noOfRecordsApproved;
	}

	public void setNoOfRecordsApproved(BigDecimal noOfRecordsApproved) {
		this.noOfRecordsApproved = noOfRecordsApproved;
	}

	public BigDecimal getNoOfRecordsNotApprove() {
		return noOfRecordsNotApprove;
	}

	public void setNoOfRecordsNotApprove(BigDecimal noOfRecordsNotApprove) {
		this.noOfRecordsNotApprove = noOfRecordsNotApprove;
	}

 



	

	
}
