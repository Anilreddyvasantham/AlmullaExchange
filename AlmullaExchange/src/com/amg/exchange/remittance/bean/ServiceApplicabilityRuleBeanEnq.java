package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.ServiceApplicabilityRule;
import com.amg.exchange.remittance.service.IServiceApplicabilityRuleService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component(value = "serviceApplicabilityRuleBeanEnq")
@Scope("session")
public class ServiceApplicabilityRuleBeanEnq<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	Logger log = Logger.getLogger(ServiceApplicabilityRuleBeanEnq.class);
	
	private BigDecimal countryId;
	private BigDecimal currencyId;
	private BigDecimal remittanceModeId;
	private BigDecimal deliveryId;
	private String fieldName;
	private String fielDesc;
	private String navigable;
	private String mandatory;
	private String fielType;
	private String fielLength;
	private String validation;
	private BigDecimal min;
	private BigDecimal max;
	private Boolean renderDataTable=false;
	private Boolean renderMaxMsg=false;
	private String deliveryName;
	private String remittanceName;
	private List<CountryMasterDesc> countryList;
	private Boolean exit=false;
	private String isActive;
	private String errorMessage=null;
	
	private List<ServiceApplicabilityRuleDataTable> listserviceapplirule=new ArrayList<ServiceApplicabilityRuleDataTable>();
	
	@Autowired
	IServiceApplicabilityRuleService serApplicabilityRuleService;
	
	@Autowired
	IGeneralService<T> generalService;
	
	SessionStateManage sessionStateManage = new SessionStateManage();
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}
	public BigDecimal getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(BigDecimal deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFielDesc() {
		return fielDesc;
	}
	public void setFielDesc(String fielDesc) {
		this.fielDesc = fielDesc;
	}
	public String getNavigable() {
		return navigable;
	}
	public void setNavigable(String navigable) {
		this.navigable = navigable;
	}
	public String getMandatory() {
		return mandatory;
	}
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	public String getFielType() {
		return fielType;
	}
	public void setFielType(String fielType) {
		this.fielType = fielType;
	}
	public String getFielLength() {
		return fielLength;
	}
	public void setFielLength(String fielLength) {
		this.fielLength = fielLength;
	}
	public String getValidation() {
		return validation;
	}
	public void setValidation(String validation) {
		this.validation = validation;
	}
	public BigDecimal getMin() {
		return min;
	}
	public void setMin(BigDecimal min) {
		this.min = min;
	}
	public BigDecimal getMax() {
		return max;
	}
	public void setMax(BigDecimal max) {
		this.max = max;
	}
	public Boolean getRenderDataTable() {
		return renderDataTable;
	}
	public void setRenderDataTable(Boolean renderDataTable) {
		this.renderDataTable = renderDataTable;
	}
	public Boolean getRenderMaxMsg() {
		return renderMaxMsg;
	}
	public void setRenderMaxMsg(Boolean renderMaxMsg) {
		this.renderMaxMsg = renderMaxMsg;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public String getRemittanceName() {
		return remittanceName;
	}
	public void setRemittanceName(String remittanceName) {
		this.remittanceName = remittanceName;
	}
	public List<ServiceApplicabilityRuleDataTable> getListserviceapplirule() {
		return listserviceapplirule;
	}
	public void setListserviceapplirule(List<ServiceApplicabilityRuleDataTable> listserviceapplirule) {
		this.listserviceapplirule = listserviceapplirule;
	}
	public List<CountryMasterDesc> getCountryList() {
		return countryList;
	}
	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}
	public Boolean getExit() {
		return exit;
	}
	public void setExit(Boolean exit) {
		this.exit = exit;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	Map<BigDecimal, String> mapCountryList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapCurrencyList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapRemittanceList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapDeliveryList = new HashMap<BigDecimal, String>();
	
	public List<CountryMasterDesc> getCountryNameList() {
		try{
		countryList = new ArrayList<CountryMasterDesc>();
		countryList.addAll(generalService.getCountryList(sessionStateManage.getLanguageId()));
		for (CountryMasterDesc countryMasterDesc : countryList) {
			mapCountryList.put(countryMasterDesc.getFsCountryMaster().getCountryId(),
					countryMasterDesc.getCountryName());
		}
		return countryList;
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::getCountryNameList "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return countryList;
		}
	}
	
	public List<CurrencyMaster> getCurrencyList() {
		
		List<CurrencyMaster> currencyList= new ArrayList<CurrencyMaster>();
		try{
		currencyList.addAll(generalService.getCurrencyList());
		for(CurrencyMaster currencyMaster:currencyList){	
			mapCurrencyList.put(currencyMaster.getCurrencyId(),currencyMaster.getCurrencyName());
		}		
		return currencyList;
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::getCurrencyList "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return currencyList;
		}
	}
	
	public List<RemittanceModeDescription> getRemittanceList(){
		List<RemittanceModeDescription> remittanceList= new ArrayList<RemittanceModeDescription>();
		try{
		remittanceList.addAll(generalService.getRemittanceList(sessionStateManage.getLanguageId()));
		for(RemittanceModeDescription remittanceMode:remittanceList){			
			mapRemittanceList.put(remittanceMode.getRemittanceModeMaster().getRemittanceModeId(), remittanceMode.getRemittanceDescription());
					}
		return remittanceList;
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::getRemittanceList "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return remittanceList;
		}
			}
	
	public List<DeliveryModeDesc> getDeliveryList(){
		List<DeliveryModeDesc> deliveryList= new ArrayList<DeliveryModeDesc>();
		try{
		deliveryList.addAll(generalService.getDeliveryModeList(sessionStateManage.getLanguageId()));
		for(DeliveryModeDesc deliveryMode:deliveryList){
			mapDeliveryList.put(deliveryMode.getDeliveryMode().getDeliveryModeId(),deliveryMode.getEnglishDeliveryName());
		}
				return deliveryList;
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::getDeliveryList "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return deliveryList;
		}
		}
	
	public void enquiry(){
	//	setExit(false);
		listserviceapplirule.clear();
		try{
		 List<ServiceApplicabilityRule> viewListServiceRule = new ArrayList<ServiceApplicabilityRule>();
		 
		 viewListServiceRule.addAll(serApplicabilityRuleService.getEnqDataServiceAppRule(sessionStateManage.getCountryId(), getCountryId(), getCurrencyId(), getRemittanceModeId(),getDeliveryId()));
		 		 if(viewListServiceRule.size()>0){
			
			 setRenderDataTable(true);
			 setExit(true);
			 
			 for(ServiceApplicabilityRule viewData:viewListServiceRule){
				 
				 ServiceApplicabilityRuleDataTable  serviceAppRule = new ServiceApplicabilityRuleDataTable();

				 serviceAppRule.setFieldName(viewData.getFieldName());
				 serviceAppRule.setFielDesc(viewData.getFieldDesc());
				 serviceAppRule.setNavigable(viewData.getNavicable());
				 serviceAppRule.setMandatory(viewData.getMandatory());
				 serviceAppRule.setFielType(viewData.getFieldType());
				
				 serviceAppRule.setValidation(viewData.getValidate());
				 serviceAppRule.setMax(viewData.getMaxLenght());
				 serviceAppRule.setMin(viewData.getMinLenght());
				 serviceAppRule.setCountryId(viewData.getCountryId().getCountryId());
				 serviceAppRule.setCountryId(viewData.getCountryId().getCountryId());
				 serviceAppRule.setCurrencyId(viewData.getCurrencyId().getCurrencyId());
				 serviceAppRule.setDeliveryId(viewData.getDeliveryModeId().getDeliveryModeId());
				 serviceAppRule.setRemittanceModeId(viewData.getRemittanceModeId().getRemittanceModeId());
				 serviceAppRule.setServiceApplivcabilityRuleId(viewData.getServiceApplicabilityRuleId());
				 serviceAppRule.setServiceApplanguageId(viewData.getLanguageId());
				 String languageName=serApplicabilityRuleService.toFetchLanguageName(viewData.getLanguageId());
				 serviceAppRule.setLanduageName(languageName);
				 serviceAppRule.setCreatedBy(viewData.getCreatedBy());
				 serviceAppRule.setCreatedDate(viewData.getCreatedDate());
				  if(viewData.getIsActive().equalsIgnoreCase(Constants.U)){
					 serviceAppRule.setIsActive("Un_Approve");
				 }else if(viewData.getIsActive().equalsIgnoreCase(Constants.Yes)){
					 serviceAppRule.setIsActive("Activated");
				 } else if(viewData.getIsActive().equalsIgnoreCase(Constants.D)){
					 serviceAppRule.setIsActive("Deleted");
				}
				 listserviceapplirule.add(serviceAppRule);
				
			 }

		 }else{
			 setRenderDataTable(false);
			// setExit(false);
			 RequestContext.getCurrentInstance().execute("norecord.show();");
		 }
		 }catch(NullPointerException ne){
			  log.info("Method Name::enquiry"+ne.getMessage());
			  setErrorMessage("Method Name::enquiry"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::enquiry "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		} 
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void clearCache() {		
		clear();
		setRenderDataTable(false);
		setExit(false);
				try {
					loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "serviceapplicabilityenq.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/serviceapplicabilityenq.xhtml");
			clear();
				} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clear() {
		setCountryId(null);
		setCurrencyId(null);
		setRemittanceModeId(null);
		setDeliveryId(null);	
	}
	
	public void exit() throws IOException {
		 if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			}else{
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
	}
}
