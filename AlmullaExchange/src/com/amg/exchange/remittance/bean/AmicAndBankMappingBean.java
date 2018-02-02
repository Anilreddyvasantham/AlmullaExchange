package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.dto.BankMasterDTO;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.complaint.bean.ComplaintEnquiryDataTable;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.AmiecAndBankMapping;
import com.amg.exchange.remittance.service.IAdditionalBankRuleMapService;
import com.amg.exchange.remittance.service.IAmiecAndBankMappingService;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("amicAndBankMappingBean")
@Scope("session")
public class AmicAndBankMappingBean<T> implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private BigDecimal countryId;
	private String flexField;
	private Boolean renderDataTable =false;
	private String warningMessage;
	
	private SessionStateManage sessionStateManage = new SessionStateManage();
	
	private List<AdditionalBankRuleMap> listFlexifield;
	private List<CountryMasterDesc> listCountryDesc;
	private List<AmiecAddValueToDataTable> amiecAddValueToList;
	
	
	
	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IAdditionalBankRuleMapService additionalBankRuleMapService;
	
	@Autowired
	IAmiecAndBankMappingService amiecAndBankMappingService;
	
	
	
	
	public String getWarningMessage() {
		return warningMessage;
	}

	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getFlexField() {
		return flexField;
	}


	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}


	public List<AdditionalBankRuleMap> getListFlexifield() {
		return listFlexifield;
	}


	public void setListFlexifield(List<AdditionalBankRuleMap> listFlexifield) {
		this.listFlexifield = listFlexifield;
	}

	public IAdditionalBankRuleMapService getAdditionalBankRuleMapService() {
		return additionalBankRuleMapService;
	}


	public void setAdditionalBankRuleMapService(
			IAdditionalBankRuleMapService additionalBankRuleMapService) {
		this.additionalBankRuleMapService = additionalBankRuleMapService;
	}

	public List<CountryMasterDesc> getListCountryDesc() {
		return listCountryDesc;
	}


	public void setListCountryDesc(List<CountryMasterDesc> listCountryDesc) {
		this.listCountryDesc = listCountryDesc;
	}

	public List<AmiecAddValueToDataTable> getAmiecAddValueToList() {
		return amiecAddValueToList;
	}


	public void setAmiecAddValueToList(
			List<AmiecAddValueToDataTable> amiecAddValueToList) {
		this.amiecAddValueToList = amiecAddValueToList;
	}

	public Boolean getRenderDataTable() {
		return renderDataTable;
	}


	public void setRenderDataTable(Boolean renderDataTable) {
		this.renderDataTable = renderDataTable;
	}

	public void popFlex() {
		setFlexField(null);
		setAmiecAddValueToList(null);
		setRenderDataTable(false);
		
		 List<AdditionalBankRuleMap> additionalBankRuleMaps = additionalBankRuleMapService.getAdditionalFlexField(getCountryId());
		if(additionalBankRuleMaps.size()>0){
			setListFlexifield(additionalBankRuleMaps);
		}
	}

	public void popCountryList() {

		List<CountryMasterDesc> countryList = additionalBankRuleMapService.getCountryList(sessionStateManage.getLanguageId());
		if(countryList.size()>0){
			setListCountryDesc(countryList);
		}
		setFlexField(null);
		setRenderDataTable(false);
		setAmiecAddValueToList(null);
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void amiecPageNavigation() {
		try {
			clear();
			popCountryList();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "amicandbankmappingnew.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/amicandbankmappingnew.xhtml ");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fetchAmiecDetails(){
		try{
			
			System.out.println("Enter Fetching Details : fetchAmiecDetails()");

			HashMap<BigDecimal, String> bankNames = new HashMap<BigDecimal, String>();
			HashMap<BigDecimal, Object> additionalBankRuleBankRec = new HashMap<BigDecimal, Object>();
			List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

			bankNames.clear();

			List<BankMaster> lstBankMasters = generalService.getAllBankList(sessionStateManage.getLanguageId(), getCountryId());
			if(lstBankMasters != null && !lstBankMasters.isEmpty()){
				for (BankMaster bankMaster : lstBankMasters) {
					bankNames.put(bankMaster.getBankId(),bankMaster.getBankFullName());
				}
			}

			HashMap<String, Object> mapAmiecAndBankMapWithCode = new HashMap<String, Object>();
			List<String> duplicateCheckForCode = new ArrayList<String>();
			List<BigDecimal> duplicateCheckForBank = new ArrayList<BigDecimal>();
			HashMap<String, HashMap<BigDecimal, Object>> mapAmiecAndBankMap = new HashMap<String, HashMap<BigDecimal, Object>>();

			List<AmiecAndBankMapping> lstAmiecAndBankMappingTable = additionalBankRuleMapService.fetchAllDataFromAmiecTableByCountryFlexField(getCountryId(), getFlexField());
			if(lstAmiecAndBankMappingTable != null && !lstAmiecAndBankMappingTable.isEmpty()){

				for (AmiecAndBankMapping amiecAndBankMapping : lstAmiecAndBankMappingTable) {
					if(amiecAndBankMapping.getAmiecCode() != null && !duplicateCheckForCode.contains(amiecAndBankMapping.getAmiecCode())){
						List<AmiecAndBankMapping> lstAABMap = new ArrayList<AmiecAndBankMapping>();
						for (AmiecAndBankMapping amiecBankMap : lstAmiecAndBankMappingTable) {
							if(amiecBankMap.getAmiecCode() != null && amiecBankMap.getAmiecCode().equalsIgnoreCase(amiecAndBankMapping.getAmiecCode())){
								lstAABMap.add(amiecBankMap);
							}
						}
						mapAmiecAndBankMapWithCode.put(amiecAndBankMapping.getAmiecCode(),lstAABMap);
						duplicateCheckForCode.add(amiecAndBankMapping.getAmiecCode());
					}
				}
				
				duplicateCheckForCode.clear();
			}

			if(mapAmiecAndBankMapWithCode != null && !mapAmiecAndBankMapWithCode.isEmpty()){
				for (Map.Entry<String, Object> entry : mapAmiecAndBankMapWithCode.entrySet()) {
					HashMap<BigDecimal, Object> mapAmiecAndBankMapWithBank = new HashMap<BigDecimal, Object>();
					if(!duplicateCheckForCode.contains(entry.getKey())){
						List<AmiecAndBankMapping> lstAmiecAndBankMap = (List<AmiecAndBankMapping>) entry.getValue();
						for (AmiecAndBankMapping amiecAndBankMapping : lstAmiecAndBankMap) {
							if(amiecAndBankMapping.getBankId() !=null && !duplicateCheckForBank.contains(amiecAndBankMapping.getBankId().getBankId())){
								List<AmiecAndBankMapping> lstAABMap = new ArrayList<AmiecAndBankMapping>();
								for (AmiecAndBankMapping amiecBankMap : lstAmiecAndBankMap) {
									if(amiecBankMap.getBankId() != null && amiecBankMap.getBankId().getBankId().compareTo(amiecAndBankMapping.getBankId().getBankId())==0){
										lstAABMap.add(amiecBankMap);
										break;
									}
								}
								mapAmiecAndBankMapWithBank.put(amiecAndBankMapping.getBankId().getBankId(),lstAABMap);
								duplicateCheckForBank.add(amiecAndBankMapping.getBankId().getBankId());
							}
						}
						duplicateCheckForBank.clear();
						mapAmiecAndBankMap.put(entry.getKey(),mapAmiecAndBankMapWithBank);
						duplicateCheckForCode.add(entry.getKey());
					}
				}
			}


			List<AmiecAddValueToDataTable> amiecAddValueToList = new ArrayList<AmiecAddValueToDataTable>();
			List<AdditionalBankRuleAmiec> listAmiec = amiecAndBankMappingService.getAmielist(getCountryId(), getFlexField()); // multiple
			List<BankMasterDTO> listAdd = additionalBankRuleMapService.getBanlList(getCountryId(), getFlexField()); //1
			
			List<AdditionalBankRuleAddData> listBankS = additionalBankRuleMapService.fetchAllDataFromAdditionalBankRule(getCountryId(),getFlexField()); // multiple
			
			
			if(listAmiec.size()>0){
				additionalBankRuleBankRec.clear();
				setRenderDataTable(true);
				for(AdditionalBankRuleAmiec addlist:listAmiec){

					AmiecAddValueToDataTable amiecAddValue = new AmiecAddValueToDataTable();
					amiecAddValue.setAmiecCode(addlist.getAmiecCode());
					amiecAddValue.setAmiecDesc(addlist.getAmiecDescription());
					amiecAddValue.setCountryId(getCountryId());
					amiecAddValue.setFlexiField(getFlexField());

					List<AdditionalBankAmiecTemp> addtionalbankAmiecTempList = new ArrayList<AdditionalBankAmiecTemp>();

					if(listAdd!=null && listAdd.size()>0){
						for(BankMasterDTO additionRuledata:listAdd){
							AdditionalBankAmiecTemp tempObj = new AdditionalBankAmiecTemp();
							tempObj.setBankId(additionRuledata.getBankId());
							tempObj.setBankName(bankNames.get(additionRuledata.getBankId()));
							if(!duplicateCheck.contains(additionRuledata.getBankId())){
								//List<AdditionalBankRuleAddData> listBankS = additionalBankRuleMapService.getBankDetailsAndDescription(getCountryId(),additionRuledata.getBankId(),getFlexField()); // multiple
								if(listBankS!=null && listBankS.size()>0){
									List<AdditionalBankRuleAddData> lstAddBankRule = new ArrayList<AdditionalBankRuleAddData>();
									for (AdditionalBankRuleAddData addBankRule : listBankS) {
										if(addBankRule.getBankId() != null && addBankRule.getBankId().getBankId().compareTo(additionRuledata.getBankId())==0){
											lstAddBankRule.add(addBankRule);
										}
									}
									tempObj.setAddtionalBankRuleList(lstAddBankRule);
									additionalBankRuleBankRec.put(additionRuledata.getBankId(), lstAddBankRule);
								}else{
									additionalBankRuleBankRec.put(additionRuledata.getBankId(), listBankS);
								}
								duplicateCheck.add(additionRuledata.getBankId());
							}else{
								tempObj.setAddtionalBankRuleList((List<AdditionalBankRuleAddData>) additionalBankRuleBankRec.get(additionRuledata.getBankId()));
							}

							//for updating time check
							//List<AmiecAndBankMapping>   amiecAndBankMappingList  = additionalBankRuleMapService.checkRecordAvailableInAmiecAndBankMappingTable(getCountryId(), getFlexField(), addlist.getAmiecCode(), addlist.getAmiecDescription(), additionRuledata.getBankId()); //1
							if(mapAmiecAndBankMap!=null && mapAmiecAndBankMap.size()>0){
								HashMap<BigDecimal, Object> amiecObjWithCode =  mapAmiecAndBankMap.get(addlist.getAmiecCode());
								if(amiecObjWithCode !=null && !amiecObjWithCode.isEmpty()){
									for (Entry<BigDecimal, Object> entry : amiecObjWithCode.entrySet()) {
										List<AmiecAndBankMapping> lstAmiecAndBankMap = (List<AmiecAndBankMapping>) entry.getValue();
										for (AmiecAndBankMapping amiecCodeBank : lstAmiecAndBankMap) {
											if(amiecCodeBank.getBankId() != null && additionRuledata.getBankId() != null && amiecCodeBank.getBankId().getBankId().compareTo(additionRuledata.getBankId())==0){
												tempObj.setAmiecAndBankMappingPK(amiecCodeBank.getAmiecAndBankMappingId());
												tempObj.setAddtionalFieldDesc(amiecCodeBank.getBankCode());
											}
										}
									}
									// addtionalbankAmiecTempList.add(tempObj);
								}
								// addtionalbankAmiecTempList.add(tempObj);
							}
							addtionalbankAmiecTempList.add(tempObj);
						}
					}
					amiecAddValue.setAddtionalbankAmiecTempList(addtionalbankAmiecTempList);
					amiecAddValueToList.add(amiecAddValue);
				}
				setAmiecAddValueToList(amiecAddValueToList);
			}else{
				setRenderDataTable(false);
				setWarningMessage(null);
				setWarningMessage("No Records Found");
				RequestContext.getCurrentInstance().execute("warningDailog.show();");
			}
		}catch(Exception exception){
			if(exception.getMessage() !=null){
				setWarningMessage(exception.getMessage()); 
				RequestContext.getCurrentInstance().execute("error.show();");
				return;   
			}else{
				setWarningMessage(exception.toString()); 
				RequestContext.getCurrentInstance().execute("error.show();");
				return; 	
			}
		}
		System.out.println("Exist Fetching Details : fetchAmiecDetails() ");
	}

	public void clear(){
		setRenderDataTable(false);
		setFlexField(null);
		setCountryId(null);
		
		if(amiecAddValueToList!=null && amiecAddValueToList.size()>0){
			amiecAddValueToList.clear();
		}
	}
	
	public void ifAddtionalFieldUpate(AdditionalBankAmiecTemp addtionTempObj){
		addtionTempObj.setAddtionalFieldDesc(addtionTempObj.getAddtionalFieldDesc());
	}
	
	
	public void saveData(){
		try{
		List<AmiecAndBankMapping>   amiecAndBankMappingList = new ArrayList<AmiecAndBankMapping>();
		List<AmiecAndBankMapping>   amiecAndBankMappingUpdateList = new ArrayList<AmiecAndBankMapping>();
			
			for(AmiecAddValueToDataTable amicAdddata:amiecAddValueToList){
	
			
			
				for(AdditionalBankAmiecTemp addtionalObj:amicAdddata.getAddtionalbankAmiecTempList()){
					
						if(addtionalObj.getAddtionalFieldDesc()!=null){
						
						if(addtionalObj.getAmiecAndBankMappingPK()!=null){	
							AmiecAndBankMapping amiecAndBankMapping = new AmiecAndBankMapping();
							amiecAndBankMapping.setAmiecAndBankMappingId(addtionalObj.getAmiecAndBankMappingPK());
							amiecAndBankMapping.setFlexField(amicAdddata.getFlexiField());
							amiecAndBankMapping.setAmiecCode(amicAdddata.getAmiecCode());
							amiecAndBankMapping.setAmiecDescription(amicAdddata.getAmiecDesc());
							//amiecAndBankMapping.setBankCode(additionalBankRuleMapService.getBankCode(addtionalObj.getAddtionalFieldDesc()));
							//amiecAndBankMapping.setBankDecription(addtionalObj.getAddtionalFieldDesc());
							
							amiecAndBankMapping.setBankCode(addtionalObj.getAddtionalFieldDesc());
							List<AdditionalBankRuleAddData> listBankS = additionalBankRuleMapService.getBankDetailsAndDescription(getCountryId(),addtionalObj.getBankId(),getFlexField());
							for (AdditionalBankRuleAddData additionalBankRuleAddData : listBankS) {
								if(additionalBankRuleAddData.getAdditionalData().equalsIgnoreCase(addtionalObj.getAddtionalFieldDesc())){
									amiecAndBankMapping.setBankDecription(additionalBankRuleAddData.getAdditionalDescription());
									break;
								}
										
							}
						
							amiecAndBankMappingUpdateList.add(amiecAndBankMapping);
						}else{
							AmiecAndBankMapping amiecAndBankMapping = new AmiecAndBankMapping();
							CountryMaster countryMaster = new CountryMaster();
							countryMaster.setCountryId(amicAdddata.getCountryId());
							amiecAndBankMapping.setCountryId(countryMaster);
							
							BankMaster bankMaster = new BankMaster();
							bankMaster.setBankId(addtionalObj.getBankId());
							amiecAndBankMapping.setBankId(bankMaster);
							
							amiecAndBankMapping.setFlexField(amicAdddata.getFlexiField());
							amiecAndBankMapping.setAmiecCode(amicAdddata.getAmiecCode());
							amiecAndBankMapping.setAmiecDescription(amicAdddata.getAmiecDesc());
							
							//amiecAndBankMapping.setBankCode(additionalBankRuleMapService.getBankCode(addtionalObj.getAddtionalFieldDesc()));
							//amiecAndBankMapping.setBankDecription(addtionalObj.getAddtionalFieldDesc());
							
							amiecAndBankMapping.setBankCode(addtionalObj.getAddtionalFieldDesc());
							List<AdditionalBankRuleAddData> listBankS = additionalBankRuleMapService.getBankDetailsAndDescription(getCountryId(),addtionalObj.getBankId(),getFlexField());
							for (AdditionalBankRuleAddData additionalBankRuleAddData : listBankS) {
								if(additionalBankRuleAddData.getAdditionalData().equalsIgnoreCase(addtionalObj.getAddtionalFieldDesc())){
									amiecAndBankMapping.setBankDecription(additionalBankRuleAddData.getAdditionalDescription());
									break;
								}
										
							}
							
							
							amiecAndBankMapping.setCreatedBy(sessionStateManage.getUserName());
							amiecAndBankMapping.setCreatedDate(new Date());
							amiecAndBankMapping.setApprovedBy(sessionStateManage.getUserName());
							amiecAndBankMapping.setApprovedDate(new Date());
							
							amiecAndBankMapping.setIsActive(Constants.Yes);
							amiecAndBankMappingList.add(amiecAndBankMapping);
							}
						}
				}
			
			}
			
			boolean mainSave=false;
			boolean update =false;
			
			if(amiecAndBankMappingUpdateList!=null && amiecAndBankMappingUpdateList.size()>0){
				additionalBankRuleMapService.updateRecord(amiecAndBankMappingUpdateList, sessionStateManage.getUserName());
				update =true;
			}
			
			if(amiecAndBankMappingList!=null && amiecAndBankMappingList.size()>0){
				additionalBankRuleMapService.saveRecord(amiecAndBankMappingList);
				mainSave=true;
			}
			if(update || mainSave){
				RequestContext.getCurrentInstance().execute("complete.show();");
				clear();
				popCountryList();
			}else{ 
				setWarningMessage(null);
				setWarningMessage("Please Select Atleast One Record For Save");
				RequestContext.getCurrentInstance().execute("warningDailog.show();");
			}
		}catch(Exception exception){
			if(exception.getMessage() !=null){
			setWarningMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;   
			}else{
				setWarningMessage(exception.toString()); 
				RequestContext.getCurrentInstance().execute("error.show();");
				return; 	
			}
			}

	}
	
	public void exit() throws IOException {
		   if(sessionStateManage.getRoleId().equalsIgnoreCase(Constants.USER_ROLE_MANAGER)){
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			}else{
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		   
		   if(amiecAddValueToList!=null && amiecAddValueToList.size()>0){
			   amiecAddValueToList.clear();
		   }
	}

}
