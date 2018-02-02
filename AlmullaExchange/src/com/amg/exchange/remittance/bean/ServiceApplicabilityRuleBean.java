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

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.ServiceApplicabilityRule;
import com.amg.exchange.remittance.model.ViewBankRuleAppField;
import com.amg.exchange.remittance.service.IBankServiceRuleMasterService;
import com.amg.exchange.remittance.service.IServiceApplicabilityRuleService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.service.DeliveryModeService;
import com.amg.exchange.treasury.service.IRemittanceModeService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component(value = "serviceApplicabilityRule")
@Scope("session")
public class ServiceApplicabilityRuleBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(ServiceApplicabilityRuleBean.class);


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
	private Boolean renderSubmit = false;
	private Boolean renderExit = false;
	private BigDecimal serviceApplicabilityRuleId;
	private Boolean checkYesOrNo = false;
	private String exceptionMessage;
	private String deliveryName;
	private String remittanceName;
	private Boolean visableDailog=false;	
	Boolean selectAll = false;

	public Boolean getSelectAll() {
		return selectAll;
	}
	public void setSelectAll(Boolean selectAll) {
		this.selectAll = selectAll;
	}

	public Boolean getVisableDailog() {
		return visableDailog;
	}
	public void setVisableDailog(Boolean visableDailog) {
		this.visableDailog = visableDailog;
	}
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public BigDecimal getServiceApplicabilityRuleId() {
		return serviceApplicabilityRuleId;
	}
	public void setServiceApplicabilityRuleId(BigDecimal serviceApplicabilityRuleId) {
		this.serviceApplicabilityRuleId = serviceApplicabilityRuleId;
	}

	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}

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

	public Boolean getRenderSubmit() {
		return renderSubmit;
	}
	public void setRenderSubmit(Boolean renderSubmit) {
		this.renderSubmit = renderSubmit;
	}

	public Boolean getRenderExit() {
		return renderExit;
	}
	public void setRenderExit(Boolean renderExit) {
		this.renderExit = renderExit;
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

	public Boolean getCheckYesOrNo() {
		return checkYesOrNo;
	}
	public void setCheckYesOrNo(Boolean checkYesOrNo) {
		this.checkYesOrNo = checkYesOrNo;
	}


	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IServiceApplicabilityRuleService iserviceApplicabilityService;
	@Autowired
	IBankServiceRuleMasterService ibankServiceRuleMasterService;
	@Autowired
	IRemittanceModeService remittanceModeService; 
	@Autowired
	DeliveryModeService deliveryModeService;


	Map<BigDecimal, String> mapCountryList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapCurrencyList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapRemittanceList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapDeliveryList = new HashMap<BigDecimal, String>();
	SessionStateManage sessionStateManage = new SessionStateManage();
	private List<CountryMasterDesc> lstCountryName = new ArrayList<CountryMasterDesc>();
	private List<CurrencyMaster> lstCurrencyName = new ArrayList<CurrencyMaster>();
	private List<RemittanceModeDescription> lstRemittanceName = new ArrayList<RemittanceModeDescription>();
	private List<DeliveryModeDesc> lstDeliveryName = new ArrayList<DeliveryModeDesc>();
	private List<ServiceApplicabilityRuleDataTable> applicabilityList = new ArrayList<ServiceApplicabilityRuleDataTable>();
	private List<ServiceApplicabilityRule> serviceApplicabilityRuleList=new ArrayList<ServiceApplicabilityRule>();


	public List<ServiceApplicabilityRule> getServiceApplicabilityRuleList() {
		return serviceApplicabilityRuleList;
	}

	public void setServiceApplicabilityRuleList(List<ServiceApplicabilityRule> serviceApplicabilityRuleList) {
		this.serviceApplicabilityRuleList = serviceApplicabilityRuleList;
	}

	public List<ServiceApplicabilityRuleDataTable> getApplicabilityList() {
		return applicabilityList;
	}

	public void setApplicabilityList(List<ServiceApplicabilityRuleDataTable> applicabilityList) {
		this.applicabilityList = applicabilityList;
	}

	public List<CountryMasterDesc> getLstCountryName() {
		return lstCountryName;
	}

	public void setLstCountryName(List<CountryMasterDesc> lstCountryName) {
		this.lstCountryName = lstCountryName;
	}

	public List<CurrencyMaster> getLstCurrencyName() {
		return lstCurrencyName;
	}

	public void setLstCurrencyName(List<CurrencyMaster> lstCurrencyName) {
		this.lstCurrencyName = lstCurrencyName;
	}

	public List<RemittanceModeDescription> getLstRemittanceName() {
		return lstRemittanceName;
	}

	public void setLstRemittanceName(List<RemittanceModeDescription> lstRemittanceName) {
		this.lstRemittanceName = lstRemittanceName;
	}

	public List<DeliveryModeDesc> getLstDeliveryName() {
		return lstDeliveryName;
	}

	public void setLstDeliveryName(List<DeliveryModeDesc> lstDeliveryName) {
		this.lstDeliveryName = lstDeliveryName;
	}

	// To get All Country List from CountryMasterDesc
	public void getCountryNameList() {
		try{
			lstCountryName.clear();
			List<CountryMasterDesc> countryList = generalService.getCountryList(sessionStateManage.getLanguageId());
			if(countryList.size()!=0){
				lstCountryName.addAll(countryList);

				for (CountryMasterDesc countryMasterDesc : countryList) {
					mapCountryList.put(countryMasterDesc.getFsCountryMaster().getCountryId(),countryMasterDesc.getCountryName());
				}
			}
		} catch (Exception e) {
			log.info("Saving Bank Branch time Error:  "+e);
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}

	}

	// To get currency list from database CurrencyMaster
	public void getCurrencyList() {
		lstCurrencyName.clear();
		setCurrencyId(null);
		try{
			List<CurrencyMaster> currencyList= generalService.getCurrencyList();

			if(currencyList.size()!=0){
				lstCurrencyName.addAll(currencyList);

				for(CurrencyMaster currencyMaster:currencyList){
					mapCurrencyList.put(currencyMaster.getCurrencyId(),currencyMaster.getCurrencyName());
				}
			}
		} catch (Exception e) {
			log.info("Saving Bank Branch time Error:  "+e);
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}

	/*public List<RemittanceModeDescription> getRemittanceList(){

		List<RemittanceModeDescription> remittanceList= new ArrayList<RemittanceModeDescription>();
		remittanceList.addAll(generalService.getRemittanceList(sessionStateManage.getLanguageId()));
		for(RemittanceModeDescription remittanceMode:remittanceList){

			mapRemittanceList.put(remittanceMode.getRemittanceModeMaster().getRemittanceModeId(), remittanceMode.getRemittanceDescription());

		}
		return remittanceList;

	}*/

	// To get Remittance list from Beneficiary Country Service based on application country , country and language
	public void getRemittanceList(){
		lstRemittanceName.clear();
		setRemittanceModeId(null);
		setRemittanceName(null);
		try{
			List<RemittanceModeDescription> remittanceList = ibankServiceRuleMasterService.getRemittance(sessionStateManage.getCountryId(),getCountryId(),sessionStateManage.getLanguageId());

			if(remittanceList.size()!=0){
				lstRemittanceName.addAll(remittanceList);
				for(RemittanceModeDescription remittanceMode : remittanceList){
					mapRemittanceList.put(remittanceMode.getRemittanceModeMaster().getRemittanceModeId() , remittanceMode.getRemittanceDescription());
				}
			}
		} catch (Exception e) {
			log.info("getRemittanceList time Error:  "+e);
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}

	}

	// To get Delivery list from Beneficiary Country Service based on application country , country , remittance and language
	public void getDeliveryList(){
		lstDeliveryName.clear();
		setDeliveryId(null);
		setDeliveryName(null);
		try{
			List<DeliveryModeDesc> deliveryList = ibankServiceRuleMasterService.getDeliverFromRemittance(sessionStateManage.getCountryId(),getCountryId(),getRemittanceModeId(),sessionStateManage.getLanguageId());

			if(deliveryList.size()!=0){
				lstDeliveryName.addAll(deliveryList);
				for(DeliveryModeDesc deliveryMode:deliveryList){
					mapDeliveryList.put(deliveryMode.getDeliveryMode().getDeliveryModeId(),deliveryMode.getEnglishDeliveryName());
				}
			}
		} catch (Exception e) {
			log.info("getDeliveryList time Error:  "+e);
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
		}
	}



	/*public void addDataTableList(){

				if(getMax().intValue()<getMin().intValue()){
					setRenderMaxMsg(true);
				}else{
					setRenderMaxMsg(false);
					//listserviceapplirule=iserviceApplicabilityService.searchrecord(getCountryId(), getCurrencyId(), getRemittanceModeId(), getDeliveryId());
					if(listserviceapplirule.size()!=0)
					{
						setRenderDataTable(false);
						System.out.println("$$$$$$$$$$$$$$$$"+listserviceapplirule.size());
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("check.show();");
					} else if(listserviceapplirule.size()!=0)
					{
						if(applicabilityList.size()!=0)
						{
							for(ServiceApplicabilityRuleDataTable datatable:applicabilityList){

						if (datatable.getCountryId().equals(getCountryId())
								&& (datatable.getCurrencyId())
										.equals(getCurrencyId())
								&& (datatable.getRemittanceModeId())
										.equals(getRemittanceModeId())
								&& (datatable.getDeliveryId())
										.equals(getDeliveryId()))
								{
									RequestContext context = RequestContext.getCurrentInstance();
									context.execute("check.show();");
								}

							}
						}


					} else {




				ServiceApplicabilityRuleDataTable serviceAppRule = new ServiceApplicabilityRuleDataTable();
				serviceAppRule.setCountryId(getCountryId());
				serviceAppRule.setCountryName(mapCountryList.get(getCountryId()));

				serviceAppRule.setCurrencyId(getCurrencyId());
				serviceAppRule.setCurrency(mapCurrencyList.get(getCurrencyId()));
				serviceAppRule.setDeliveryId(getDeliveryId());
				serviceAppRule.setDelivery(mapDeliveryList.get(getDeliveryId()));
				serviceAppRule.setRemittanceModeId(getRemittanceModeId());
				serviceAppRule.setRemittance(mapRemittanceList.get(getRemittanceModeId()));
				serviceAppRule.setFieldName(getFieldName());
				serviceAppRule.setFielDesc(getFielDesc());
				serviceAppRule.setNavigable(getNavigable());
				serviceAppRule.setMandatory(getMandatory());
				serviceAppRule.setFielType(getFielType());
				serviceAppRule.setFielLength(getFielLength());
				serviceAppRule.setValidation(getValidation());
				serviceAppRule.setMax(getMax());
				serviceAppRule.setMin(getMin());
				applicabilityList.add(serviceAppRule);
				setRenderDataTable(true);
				setCountryId(null);
				setCurrencyId(null);
				setRemittanceModeId(null);
				setDeliveryId(null);
				setFieldName(null);
				setFielDesc(null);
				setFielLength(null);
				setFielType(null);
				setNavigable(null);
				setMandatory(null);
				setValidation(null);
				setMin(null);
				setMax(null);
				}
				}


			}
	 */
	CountryMaster countryMaster =null;

	CurrencyMaster currencyMaster = null;
	RemittanceModeMaster remittanceMode = null;
	DeliveryMode deliveryMode = null;


	public void updateValidation(ServiceApplicabilityRuleDataTable dataTable){
		dataTable.setIsCheck(true);
	}

	/*public void save(){
		setExceptionMessage(null);
		//changes start 25/03/2015 
		boolean saveCheck=false;
		for(ServiceApplicabilityRuleDataTable serviceAppRule:applicabilityList){
			if(serviceAppRule.getNavigable()==null || serviceAppRule.getMandatory()==null ){
				saveCheck=true;
				break;
			}
		}
		if(!saveCheck)
		{ 
			//changes end25/03/2015 
			countryMaster = new CountryMaster();

			CountryMaster countryMasteAppCountry = new CountryMaster();
			currencyMaster = new CurrencyMaster();
			remittanceMode = new RemittanceModeMaster();
			deliveryMode = new DeliveryMode();
			int i=0;
			for(ServiceApplicabilityRuleDataTable serviceAppRule:applicabilityList){
				ServiceApplicabilityRule serviceApplicabilityRule = new ServiceApplicabilityRule();
				if(serviceAppRule.getServiceApplivcabilityRuleId()!=null){

					try{
						countryMaster.setCountryId(serviceAppRule.getCountryId());
						currencyMaster.setCurrencyId(serviceAppRule.getCurrencyId());
						remittanceMode.setRemittanceModeId(serviceAppRule.getRemittanceModeId());
						deliveryMode.setDeliveryModeId(serviceAppRule.getDeliveryId());
						countryMasteAppCountry.setCountryId(sessionStateManage.getCountryId());
						serviceApplicabilityRule.setApplicationCountryId(countryMasteAppCountry);
						serviceApplicabilityRule.setCountryId(countryMaster);
						serviceApplicabilityRule.setCurrencyId(currencyMaster);
						serviceApplicabilityRule.setDeliveryModeId(deliveryMode);
						serviceApplicabilityRule.setRemittanceModeId(remittanceMode);
						serviceApplicabilityRule.setServiceApplicabilityRuleId(serviceAppRule.getServiceApplivcabilityRuleId());
						serviceApplicabilityRule.setCreatedBy(serviceAppRule.getCreatedBy());
						serviceApplicabilityRule.setCreatedDate(serviceAppRule.getCreatedDate());
						serviceApplicabilityRule.setFieldName(serviceAppRule.getFieldName());
						serviceApplicabilityRule.setFieldDesc(serviceAppRule.getFielDesc());
						serviceApplicabilityRule.setNavicable(serviceAppRule.getNavigable());
						serviceApplicabilityRule.setMandatory(serviceAppRule.getMandatory());
						serviceApplicabilityRule.setFieldType(serviceAppRule.getFielType());
						serviceApplicabilityRule.setValidate(serviceAppRule.getValidation());
						serviceApplicabilityRule.setMinLenght(serviceAppRule.getMin());
						serviceApplicabilityRule.setIsActive(Constants.U); //Saving Status U
						serviceApplicabilityRule.setMaxLenght(serviceAppRule.getMax());
						serviceApplicabilityRule.setModifiedBy(sessionStateManage.getUserName());
						serviceApplicabilityRule.setModifiedDate(new Date());
						serviceApplicabilityRule.setLanguageId(serviceAppRule.getServiceApplanguageId());
						if(serviceAppRule.getFielLength() != null){
							serviceApplicabilityRule.setFieldLength(new BigDecimal(serviceAppRule.getFielLength()));
						}

						iserviceApplicabilityService.saveApplicability(serviceApplicabilityRule);

						 i=0;

					}catch(Exception e){
						i=1;
						setExceptionMessage(e.getMessage());
						break;
					}

				}
				if(serviceAppRule.getServiceApplivcabilityRuleId()==null){

					try{
						countryMasteAppCountry.setCountryId(sessionStateManage.getCountryId());
						serviceApplicabilityRule.setApplicationCountryId(countryMasteAppCountry);
						countryMaster.setCountryId(getCountryId());
						currencyMaster.setCurrencyId(getCurrencyId());
						remittanceMode.setRemittanceModeId(getRemittanceModeId());
						deliveryMode.setDeliveryModeId(getDeliveryId());
						serviceApplicabilityRule.setCountryId(countryMaster);
						serviceApplicabilityRule.setCurrencyId(currencyMaster);
						serviceApplicabilityRule.setDeliveryModeId(deliveryMode);
						serviceApplicabilityRule.setRemittanceModeId(remittanceMode);
						serviceApplicabilityRule.setCreatedBy(sessionStateManage.getUserName());
						serviceApplicabilityRule.setCreatedDate(new Date());
						serviceApplicabilityRule.setFieldName(serviceAppRule.getFieldName());
						serviceApplicabilityRule.setFieldDesc(serviceAppRule.getFielDesc());
						serviceApplicabilityRule.setNavicable(serviceAppRule.getNavigable());
						serviceApplicabilityRule.setMandatory(serviceAppRule.getMandatory());
						serviceApplicabilityRule.setFieldType(serviceAppRule.getFielType());
						serviceApplicabilityRule.setValidate(serviceAppRule.getValidation());
						serviceApplicabilityRule.setMinLenght(serviceAppRule.getMin());
						serviceApplicabilityRule.setIsActive(Constants.U); //Saving Status U
						serviceApplicabilityRule.setMaxLenght(serviceAppRule.getMax());
						serviceApplicabilityRule.setLanguageId(serviceAppRule.getServiceApplanguageId());
						if(serviceAppRule.getFielLength() != null){
							serviceApplicabilityRule.setFieldLength(new BigDecimal(serviceAppRule.getFielLength()));
						}

						iserviceApplicabilityService.saveApplicability(serviceApplicabilityRule);
						i=0;

					}catch(Exception e){
						i=1;
						setExceptionMessage(e.getMessage());
						break;
					}
				}

			}

			if(i==1){
				RequestContext.getCurrentInstance().execute("sqlexception.show();");
			}else{
				RequestContext.getCurrentInstance().execute("complete.show();");
			}

		}else{
			RequestContext.getCurrentInstance().execute("mandatorydia.show();");
		}
	}*/

	public void save(){
		setExceptionMessage(null);
		boolean saveCheck=false;
		int i = 0;

		List<ServiceApplicabilityRule> lstServiceApplRule = new ArrayList<ServiceApplicabilityRule>();

		lstServiceApplRule.clear();

		for(ServiceApplicabilityRuleDataTable serviceAppRule:applicabilityList){
			if(serviceAppRule.getNavigable()==null || serviceAppRule.getMandatory()==null ){
				saveCheck=true;
				break;
			}
		}
		if(!saveCheck)
		{			

			try{

				for(ServiceApplicabilityRuleDataTable serviceAppRule : applicabilityList){

					ServiceApplicabilityRule serviceApplicabilityRule = new ServiceApplicabilityRule();

					CountryMaster countryMasteAppCountry = new CountryMaster();
					countryMasteAppCountry.setCountryId(sessionStateManage.getCountryId());
					serviceApplicabilityRule.setApplicationCountryId(countryMasteAppCountry);

					if(serviceAppRule.getServiceApplivcabilityRuleId()!=null){

						serviceApplicabilityRule.setServiceApplicabilityRuleId(serviceAppRule.getServiceApplivcabilityRuleId());

						CountryMaster countryMaster = new CountryMaster();
						countryMaster.setCountryId(serviceAppRule.getCountryId());
						serviceApplicabilityRule.setCountryId(countryMaster);

						CurrencyMaster currencyMaster = new CurrencyMaster();
						currencyMaster.setCurrencyId(serviceAppRule.getCurrencyId());
						serviceApplicabilityRule.setCurrencyId(currencyMaster);

						RemittanceModeMaster remittanceMode = new RemittanceModeMaster();
						remittanceMode.setRemittanceModeId(serviceAppRule.getRemittanceModeId());
						serviceApplicabilityRule.setRemittanceModeId(remittanceMode);

						DeliveryMode deliveryMode = new DeliveryMode();
						deliveryMode.setDeliveryModeId(serviceAppRule.getDeliveryId());
						serviceApplicabilityRule.setDeliveryModeId(deliveryMode);

						serviceApplicabilityRule.setCreatedBy(serviceAppRule.getCreatedBy());
						serviceApplicabilityRule.setCreatedDate(serviceAppRule.getCreatedDate());

						serviceApplicabilityRule.setModifiedBy(sessionStateManage.getUserName());
						serviceApplicabilityRule.setModifiedDate(new Date());

					}else{

						CountryMaster countryMaster = new CountryMaster();
						countryMaster.setCountryId(getCountryId());
						serviceApplicabilityRule.setCountryId(countryMaster);

						CurrencyMaster currencyMaster = new CurrencyMaster();
						currencyMaster.setCurrencyId(getCurrencyId());
						serviceApplicabilityRule.setCurrencyId(currencyMaster);

						RemittanceModeMaster remittanceMode = new RemittanceModeMaster();
						remittanceMode.setRemittanceModeId(getRemittanceModeId());
						serviceApplicabilityRule.setRemittanceModeId(remittanceMode);

						DeliveryMode deliveryMode = new DeliveryMode();
						deliveryMode.setDeliveryModeId(getDeliveryId());
						serviceApplicabilityRule.setDeliveryModeId(deliveryMode);

						serviceApplicabilityRule.setCreatedBy(sessionStateManage.getUserName());
						serviceApplicabilityRule.setCreatedDate(new Date());

					}


					serviceApplicabilityRule.setFieldName(serviceAppRule.getFieldName());
					serviceApplicabilityRule.setFieldDesc(serviceAppRule.getFielDesc());
					serviceApplicabilityRule.setNavicable(serviceAppRule.getNavigable());
					serviceApplicabilityRule.setMandatory(serviceAppRule.getMandatory());
					serviceApplicabilityRule.setFieldType(serviceAppRule.getFielType());
					serviceApplicabilityRule.setValidate(serviceAppRule.getValidation());
					serviceApplicabilityRule.setMaxLenght(serviceAppRule.getMax());
					serviceApplicabilityRule.setMinLenght(serviceAppRule.getMin());
					serviceApplicabilityRule.setIsActive(Constants.U); //Saving Status U
					serviceApplicabilityRule.setLanguageId(serviceAppRule.getServiceApplanguageId());

					if(serviceAppRule.getFielLength() != null){
						serviceApplicabilityRule.setFieldLength(new BigDecimal(serviceAppRule.getFielLength()));
					}

					// add to list
					lstServiceApplRule.add(serviceApplicabilityRule);

				}

				if(lstServiceApplRule != null && lstServiceApplRule.size() != 0){
					iserviceApplicabilityService.saveServiceApplicabilityRuleList(lstServiceApplRule);
					i = 0;
				}


			}catch(AMGException e){
				i=1;
				setExceptionMessage(e.getMessage());
			}

			if(i==1){
				RequestContext.getCurrentInstance().execute("sqlexception.show();");
			}else{
				RequestContext.getCurrentInstance().execute("complete.show();");
			}

		}else{
			RequestContext.getCurrentInstance().execute("mandatorydia.show();");
		}
	}



	public void exit() throws IOException {
		if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void clear() {
		setCountryId(null);
		setCurrencyId(null);
		setRemittanceModeId(null);
		setDeliveryId(null);
		setLanguageId(null);
		setLanduageName(null);
		applicabilityList.clear();
		setLstLanguageTypes(null);
		setRenderDataTable(false);
		setVisableDailog(false);

	}			
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void clearCache() {

		clear();
		setVisableDailog(false);
		getCountryNameList();
		setRenderFirstPanel(true);
		setRenderDataTableApprove(false);
		setBooRenberDataTableApprovalList(false);
		setBooRenderApproveButton(false);
		applicabilityList.clear();
		setRenderDataTable(false);
		setLstLanguageTypes(null);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "serviceapplicabilityrule.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/serviceapplicabilityrule.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkMax(ServiceApplicabilityRuleDataTable serviceRuleData){


		if(serviceRuleData.getMax()!=null && serviceRuleData.getMin()!=null){

			if(serviceRuleData.getMax().intValue()<serviceRuleData.getMin().intValue()){
				serviceRuleData.setMax(null);
				RequestContext.getCurrentInstance().execute("maxgreater.show();");
			}


		}
	}


	public void checkMin(ServiceApplicabilityRuleDataTable serviceRuleData){


		if(serviceRuleData.getMax()!=null && serviceRuleData.getMin()!=null){

			if(serviceRuleData.getMax().intValue()<serviceRuleData.getMin().intValue()){
				serviceRuleData.setMin(null);
				RequestContext.getCurrentInstance().execute("maxgreater.show();");
			}


		}
	}


	//Ramakrishna code for serching record
	private List<ServiceApplicabilityRule> listserviceapplirule=new ArrayList<ServiceApplicabilityRule>();

	public void search()
	{

		applicabilityList.clear();


		if(getCountryId()!=null && getCurrencyId()!=null && getRemittanceModeId()!=null && getDeliveryId()!=null)
		{
			System.out.println("if loop");
			setRenderDataTable(false);
			setRenderDataTableApprove(false);
			setRenderFirstPanel(true);
			//setDeliveryName(null);
			//setRemittanceName(null);
			try{
				
				toFetchAllLanguages();
				List<ServiceApplicabilityRule> listserviceapplirule1=iserviceApplicabilityService.getRecord(sessionStateManage.getCountryId(), getCountryId(), getCurrencyId(), getRemittanceModeId(), getDeliveryId());

				if(listserviceapplirule1.size()>0)
				{
					
					setRenderDataTable(true);
					view();
					setRenderSubmit(true);
					setRenderExit(true);
					
				} else{
					
					List<ServiceApplicabilityRule> serviceList = iserviceApplicabilityService.getCopyRecord(sessionStateManage.getCountryId(),getRemittanceModeId(), getDeliveryId());
					
					if(serviceList.size()>0){
						setRenderDataTable(false);
						setVisableDailog(false);
						setRenderSubmit(false);
						setRenderExit(false);
						//setDeliveryId(serviceList.get(0).getDeliveryModeId().getDeliveryModeId());
						//getDeliveryList();
						//setDeliveryName(mapDeliveryList.get(serviceList.get(0).getDeliveryModeId().getDeliveryModeId()));
						//setRemittanceName(mapRemittanceList.get(serviceList.get(0).getRemittanceModeId().getRemittanceModeId()));
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("copy.show();");
						setVisableDailog(true);
					}else{
						serviceAppRule = new ServiceApplicabilityRuleDataTable();
						serviceAppRule.setCountryId(getCountryId());
						serviceAppRule.setCurrencyId(getCurrencyId());
						serviceAppRule.setDeliveryId(getDeliveryId());
						serviceAppRule.setRemittanceModeId(getRemittanceModeId());
						getDataListFromView();

					}
				}
					
			}catch(NullPointerException ne){
				log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setExceptionMessage("Method Name::search"); 
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return; 
			} catch (Exception e) {
				setExceptionMessage(e.getMessage());
				log.info("Method Name::search "+e);
				RequestContext.getCurrentInstance().execute("saveerror.show();");
				return;
			}
		}
		//setSecondPanal(true);


		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/serviceapplicabilityrule.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean secondPanal=false;




	public boolean isSecondPanal() {
		return secondPanal;
	}

	public void setSecondPanal(boolean secondPanal) {
		this.secondPanal = secondPanal;
	}	

	ServiceApplicabilityRuleDataTable serviceAppRule = null;

	public void getDataListFromView() {
		applicabilityList.clear();	
		setRenderDataTable(true);
		setRenderSubmit(true);
		setRenderExit(true);
		setRenderDataTableApprove(false);
		setBooRenberDataTableApprovalList(false);
		setBooRenderApproveButton(false);
		try{
			List<ViewBankRuleAppField> viewList = new ArrayList<ViewBankRuleAppField>();
			viewList.addAll(iserviceApplicabilityService.getViewData());

			if(viewList.size()>0){
				for(ViewBankRuleAppField viewData:viewList){

					ServiceApplicabilityRuleDataTable serviceAppRule = new ServiceApplicabilityRuleDataTable();
					serviceAppRule.setFieldName(viewData.getFieldName());
					serviceAppRule.setFielDesc(viewData.getFudDesc());
					List<LanguageType> langList=generalService.getAllLanguages();
					serviceAppRule.setLstLanguageTypes(langList);
					serviceAppRule.setFielType("N");
					serviceAppRule.setNavigable("N");
					serviceAppRule.setMandatory("N");
					serviceAppRule.setValidation("N");
					serviceAppRule.setServiceApplanguageId(null);
					applicabilityList.add(serviceAppRule);


				}
			}

		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setExceptionMessage("Method Name::getDataListFromView"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			log.info("Method Name::getDataListFromView "+e);
			RequestContext.getCurrentInstance().execute("saveerror.show();");
			return;
		}

	}


	public void onCellEdit(CellEditEvent event) {

		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		if(newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);

			setRenderDataTable(true);
			setRenderSubmit(true);
			setRenderExit(true);
			setRenderDataTableApprove(false);
			setRenderFirstPanel(true);
			setBooRenberDataTableApprovalList(false);
			setBooRenderApproveButton(false);
		}
	}

	public void view() {
		setVisableDailog(false);
		applicabilityList.clear();	
		setRenderDataTableApprove(false);
		setRenderDataTableApprove(false);
		setRenderFirstPanel(true);
		setSelectAll(false);
		if(lstApproved != null && !lstApproved.isEmpty()){
			lstApproved.clear();
		}
		try{
			if(getCountryId() != null && getCurrencyId() != null && getRemittanceModeId() != null && getDeliveryId() != null){
				toFetchAllLanguages();
				List<ServiceApplicabilityRule> viewListServiceRule = new ArrayList<ServiceApplicabilityRule>();
				viewListServiceRule.addAll(iserviceApplicabilityService.getViewDataServiceAppRule(sessionStateManage.getCountryId(), getCountryId(), getCurrencyId(), getRemittanceModeId(),getDeliveryId()));

				if(viewListServiceRule.size()>0){


					setRenderDataTable(true);
					setRenderSubmit(true);
					setRenderExit(true);

					for(ServiceApplicabilityRule viewData:viewListServiceRule){

						ServiceApplicabilityRuleDataTable serviceAppRule = new ServiceApplicabilityRuleDataTable();

						serviceAppRule.setFieldName(viewData.getFieldName());
						serviceAppRule.setFielDesc(viewData.getFieldDesc());
						serviceAppRule.setNavigable(viewData.getNavicable());
						serviceAppRule.setMandatory(viewData.getMandatory());
						serviceAppRule.setFielType(viewData.getFieldType());
						// serviceAppRule.setFielLength(viewData.getFieldLength().toPlainString());
						serviceAppRule.setValidation(viewData.getValidate());
						serviceAppRule.setMax(viewData.getMaxLenght());
						serviceAppRule.setMin(viewData.getMinLenght());
						serviceAppRule.setCountryId(viewData.getCountryId().getCountryId());
						serviceAppRule.setCurrencyId(viewData.getCurrencyId().getCurrencyId());
						serviceAppRule.setDeliveryId(viewData.getDeliveryModeId().getDeliveryModeId());
						serviceAppRule.setRemittanceModeId(viewData.getRemittanceModeId().getRemittanceModeId());

						serviceAppRule.setServiceApplivcabilityRuleId(viewData.getServiceApplicabilityRuleId());
						serviceAppRule.setCreatedBy(viewData.getCreatedBy());
						serviceAppRule.setCreatedDate(viewData.getCreatedDate());
						serviceAppRule.setModifiedBy(viewData.getModifiedBy());
						serviceAppRule.setModifiedDate(viewData.getModifiedDate());
						//toFetchAllLanguages();
						serviceAppRule.setLstLanguageTypes(lstLanguageTypes);
						if(viewData.getLanguageId() != null ){
							serviceAppRule.setServiceApplanguageId(viewData.getLanguageId());
						}else{
							serviceAppRule.setServiceApplanguageId(null);	  
						}
						serviceAppRule.setIsCheck(false);
						applicabilityList.add(serviceAppRule);

					}

				}else{
					applicabilityList.clear();
					lstLanguageTypes.clear();
					clear();  
					setRenderDataTable(false);
					RequestContext.getCurrentInstance().execute("norecord.show();");
				}
			}else{
				RequestContext.getCurrentInstance().execute("selectAll.show();");
				setRenderDataTable(false);
				return;
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setExceptionMessage("Method Name::view"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception e) {

			log.info("Method Name::viewr:  "+e);
			setExceptionMessage("Method Name::view"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("saveerror.show();");
			return;
		}

	}

	public void goOk(){


		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/serviceapplicabilityrule.xhtml");
			setRenderDataTable(true);

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	//added for approval by Nazish

	List<ServiceApplicabilityRuleDataTable> applicabilityListApprovalList = new ArrayList<ServiceApplicabilityRuleDataTable>();

	public List<ServiceApplicabilityRuleDataTable> getApplicabilityListApprovalList() {
		return applicabilityListApprovalList;
	}
	public void setApplicabilityListApprovalList(List<ServiceApplicabilityRuleDataTable> applicabilityListApprovalList) {
		this.applicabilityListApprovalList = applicabilityListApprovalList;
	}

	public void gotoApproval(ServiceApplicabilityRuleDataTable serviceAppRule) throws IOException{

		if(CommonUtil.validateApprovedBy(sessionStateManage.getUserName(), serviceAppRule.getCreatedBy())!= true){

			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/serviceapplicabilityrule.xhtml");
			clear();
			applicabilityListApprovalList.clear();
			applicabilityListApprove.clear();

			setRenderDataTable(false);
			setRenderDataTableApprove(true);
			setRenderFirstPanel(false);
			try{
				listserviceapplirule=iserviceApplicabilityService.searchrecord(sessionStateManage.getCountryId(), serviceAppRule.getCountryId(), serviceAppRule.getCurrencyId(), serviceAppRule.getRemittanceModeId(), serviceAppRule.getDeliveryId());
				if(listserviceapplirule.size()>0){

					for(ServiceApplicabilityRule viewData:listserviceapplirule){

						serviceAppRule = new ServiceApplicabilityRuleDataTable();

						serviceAppRule.setFieldName(viewData.getFieldName());
						serviceAppRule.setFielDesc(viewData.getFieldDesc());
						serviceAppRule.setNavigable(viewData.getNavicable());
						serviceAppRule.setMandatory(viewData.getMandatory());
						serviceAppRule.setFielType(viewData.getFieldType());
						//serviceAppRule.setFielLength(viewData.getFieldLength().toPlainString());
						serviceAppRule.setValidation(viewData.getValidate());
						serviceAppRule.setMax(viewData.getMaxLenght());
						serviceAppRule.setMin(viewData.getMinLenght());
						serviceAppRule.setCountryId(viewData.getCountryId().getCountryId());
						serviceAppRule.setCountryName(mapCountryList.get(viewData.getCountryId().getCountryId()));

						serviceAppRule.setCurrencyId(viewData.getCurrencyId().getCurrencyId());
						serviceAppRule.setCurrency(mapCurrencyList.get(viewData.getCurrencyId().getCurrencyId()));
						serviceAppRule.setDeliveryId(viewData.getDeliveryModeId().getDeliveryModeId());
						serviceAppRule.setDelivery(mapDeliveryList.get(viewData.getDeliveryModeId().getDeliveryModeId()));
						serviceAppRule.setRemittanceModeId(viewData.getRemittanceModeId().getRemittanceModeId());
						serviceAppRule.setRemittance(mapRemittanceList.get(viewData.getRemittanceModeId().getRemittanceModeId()));

						serviceAppRule.setServiceApplivcabilityRuleId(viewData.getServiceApplicabilityRuleId());
						serviceAppRule.setCreatedBy(viewData.getCreatedBy());
						serviceAppRule.setCreatedDate(viewData.getCreatedDate());
						serviceAppRule.setModifiedBy(viewData.getModifiedBy());
						serviceAppRule.setModifiedDate(viewData.getModifiedDate());
						applicabilityListApprove.add(serviceAppRule);


					}
				}
			}catch(NullPointerException ne){
				log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setExceptionMessage("Method Name::view"); 
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return; 
			} catch (Exception e) {

				log.info("Method: GotoApproval "+e);
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("saveerror.show();");
				return; 
			}
		}else{

			RequestContext.getCurrentInstance().execute("notabletoApprove.show();");
			//break;
		}
	}


	public void serviceApproval(){
		setBooRenberDataTableApprovalList(false);
		setBooRenderApproveButton(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "serviceapplicabilityruleapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/serviceapplicabilityruleapproval.xhtml");
			applicabilityListApprovalList.clear();
			clear();
			getCountryNameList();
			getCurrencyList();
			getRemittanceList();
			getDeliveryList();
			getApplicabilityListApprovalList();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Boolean renderDataTableApprove= false;
	private Boolean renderFirstPanel = false;




	public Boolean getRenderDataTableApprove() {
		return renderDataTableApprove;
	}

	public void setRenderDataTableApprove(Boolean renderDataTableApprove) {
		this.renderDataTableApprove = renderDataTableApprove;
	}



	public Boolean getRenderFirstPanel() {
		return renderFirstPanel;
	}

	public void setRenderFirstPanel(Boolean renderFirstPanel) {
		this.renderFirstPanel = renderFirstPanel;
	}



	List<ServiceApplicabilityRuleDataTable> applicabilityListApprove = new ArrayList<ServiceApplicabilityRuleDataTable>();




	public List<ServiceApplicabilityRuleDataTable> getApplicabilityListApprove() {
		return applicabilityListApprove;
	}

	public void setApplicabilityListApprove(
			List<ServiceApplicabilityRuleDataTable> applicabilityListApprove) {
		this.applicabilityListApprove = applicabilityListApprove;
	}

	public void approve() throws IOException{

		countryMaster = new CountryMaster();


		currencyMaster = new CurrencyMaster();
		remittanceMode = new RemittanceModeMaster();
		deliveryMode = new DeliveryMode();
		CountryMaster countryMasteAppCountry = new CountryMaster();

		for(ServiceApplicabilityRuleDataTable serviceAppRule:applicabilityListApprove){

			if(!(serviceAppRule.getModifiedBy() == null ?  serviceAppRule.getCreatedBy() : serviceAppRule.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())){

				try{
					ServiceApplicabilityRule serviceApplicabilityRule = new ServiceApplicabilityRule();

					serviceApplicabilityRule.setFieldName(serviceAppRule.getFieldName());
					serviceApplicabilityRule.setFieldDesc(serviceAppRule.getFielDesc());
					serviceApplicabilityRule.setNavicable(serviceAppRule.getNavigable());
					serviceApplicabilityRule.setMandatory(serviceAppRule.getMandatory());
					serviceApplicabilityRule.setFieldType(serviceAppRule.getFielType());
					serviceApplicabilityRule.setValidate(serviceAppRule.getValidation());
					serviceApplicabilityRule.setMinLenght(serviceAppRule.getMin());
					serviceApplicabilityRule.setIsActive(Constants.Yes);//Fard Coded Value
					serviceApplicabilityRule.setMaxLenght(serviceAppRule.getMax());
					serviceApplicabilityRule.setFieldLength(new BigDecimal(serviceAppRule.getFielLength()));
					countryMaster.setCountryId(serviceAppRule.getCountryId());
					currencyMaster.setCurrencyId(serviceAppRule.getCurrencyId());
					remittanceMode.setRemittanceModeId(serviceAppRule.getRemittanceModeId());
					deliveryMode.setDeliveryModeId(serviceAppRule.getDeliveryId());
					serviceApplicabilityRule.setCountryId(countryMaster);
					serviceApplicabilityRule.setCurrencyId(currencyMaster);
					serviceApplicabilityRule.setDeliveryModeId(deliveryMode);
					serviceApplicabilityRule.setRemittanceModeId(remittanceMode);
					countryMasteAppCountry.setCountryId(sessionStateManage.getCountryId());
					serviceApplicabilityRule.setApplicationCountryId(countryMasteAppCountry);
					serviceApplicabilityRule.setServiceApplicabilityRuleId(serviceAppRule.getServiceApplivcabilityRuleId());
					serviceApplicabilityRule.setCreatedBy(serviceAppRule.getCreatedBy());
					serviceApplicabilityRule.setCreatedDate(serviceAppRule.getCreatedDate());
					serviceApplicabilityRule.setModifiedBy(serviceAppRule.getModifiedBy());
					serviceApplicabilityRule.setModifiedDate(serviceAppRule.getModifiedDate());
					serviceApplicabilityRule.setApprovedBy(sessionStateManage.getUserName());
					serviceApplicabilityRule.setApprovedDate(new Date());

					iserviceApplicabilityService.saveApplicability(serviceApplicabilityRule);
					RequestContext.getCurrentInstance().execute("approvedsuccess.show();");

				}catch(NullPointerException ne){
					log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setExceptionMessage("Method Name::approve"); 
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return; 
				} catch (Exception e) {
					log.info("Method: approve "+e);
					setExceptionMessage(e.getMessage());
					RequestContext.getCurrentInstance().execute("saveerror.show();");
					return; 
				}
			}else{
				RequestContext.getCurrentInstance().execute("notabletoApprove.show();");
				break;
			}
		}
	}

	public void clearApprove() throws IOException{
		if(lstApproved != null && !lstApproved.isEmpty()){
			lstApproved.clear();
		}
		if(lstRemittanceName != null && !lstRemittanceName.isEmpty()){
			lstRemittanceName.clear();
		}
		if(lstDeliveryName != null && !lstDeliveryName.isEmpty()){
			lstDeliveryName.clear();
		}
		setSelectAll(false);
		setBooRenberDataTableApprovalList(false);
		setBooRenderApproveButton(false);
		setCountryId(null);
		setCurrencyId(null);
		setRemittanceModeId(null);
		setDeliveryId(null);
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/serviceapplicabilityruleapproval.xhtml");

	}

	public void copyServiceRule(){

		try{
			List<ServiceApplicabilityRule> serviceList = iserviceApplicabilityService.getCopyRecord(sessionStateManage.getCountryId(), getRemittanceModeId(), getDeliveryId());

			if(serviceList.size()>0){
				setRenderDataTable(false);
				setRenderSubmit(false);
				setRenderExit(false);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("copy.show();");

			}
			setCheckYesOrNo(false);

		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setExceptionMessage("Method Name::copyServiceRule"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception e) {

			log.info("Method: copyServiceRule "+e);
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
			return; 
		}
	}

	@SuppressWarnings("static-access")
	public void clickCopy() throws IOException{

		setVisableDailog(false);
		/*

		applicabilityList.clear();

			List<ServiceApplicabilityRule> serviceList = iserviceApplicabilityService.getCopyRecord(sessionStateManage.getCountryId(),getRemittanceModeId(), getDeliveryId());
			System.out.println("CALLEDPPPPPPPPPPPPPPPPPPPPPPPPPPP");
            if(serviceList.size()>0){
            	 setRenderDataTable(true);
				 setRenderSubmit(true);
				 setRenderExit(true);

				 for(ServiceApplicabilityRule viewData:serviceList){

					 serviceAppRule = new ServiceApplicabilityRuleDataTable();

					 serviceAppRule.setFieldName(viewData.getFieldName());
					 serviceAppRule.setFielDesc(viewData.getFieldDesc());
					 serviceAppRule.setNavigable(viewData.getNavicable());
					 serviceAppRule.setMandatory(viewData.getMandatory());
					 serviceAppRule.setFielType(viewData.getFieldType());

					 serviceAppRule.setValidation(viewData.getValidate());
					 serviceAppRule.setMax(viewData.getMaxLenght());
					 serviceAppRule.setMin(viewData.getMinLenght());

					 applicabilityList.add(serviceAppRule);


									 }
				 FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/serviceapplicabilityrule.xhtml");

            }

		 */

		dataTable.clear();
		try{
			@SuppressWarnings("unchecked")
			List<Object> applicabilitylist=iserviceApplicabilityService.isCombinationExist(getDeliveryId(),getRemittanceModeId(),sessionStateManage.getCountryId());



			for (int i = 0; i < applicabilitylist.size(); i++) {

				Object[] object = (Object[]) applicabilitylist.get(i);

				ServiceApplicabilityRuleCopyDataTable  copyDataTable = new ServiceApplicabilityRuleCopyDataTable();


				copyDataTable.setCountryId(new BigDecimal(String.valueOf(object[0])));
				copyDataTable.setCurrencyId(new BigDecimal(String.valueOf(object[1])));
				copyDataTable.setRemittenceModeid(new BigDecimal(String.valueOf(object[2])));
				copyDataTable.setDelivaryId(new BigDecimal(String.valueOf(object[3])));
				copyDataTable.setRemittenceMode(mapRemittanceList.get(copyDataTable.getRemittenceModeid()));
				copyDataTable.setCurrencyName(mapCurrencyList.get(copyDataTable.getCurrencyId()));
				copyDataTable.setDelivary(mapDeliveryList.get(copyDataTable.getDelivaryId()));
				copyDataTable.setCountryName(mapCountryList.get(copyDataTable.getCountryId()));
				copyDataTable.setApplicationCountryId(sessionStateManage.getCountryId());
				if(lstLanguageTypes != null && lstLanguageTypes.size() !=0){
					copyDataTable.setLstLanguageTypes(lstLanguageTypes);	  
				}else{
					copyDataTable.setLstLanguageTypes(null);
				}

				dataTable.add(copyDataTable);

			}

			setCheckYesOrNo(true);
			RequestContext context = RequestContext.getCurrentInstance();

			try {
				new Thread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			context.execute("copyOne.show();");

		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setExceptionMessage("Method Name::clickCopy"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception e) {

			log.info("Method: clickCopy "+e);
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
			return; 
		}
	}


	private List<ServiceApplicabilityRuleCopyDataTable> dataTable = new ArrayList<ServiceApplicabilityRuleCopyDataTable>();


	public void fetchAndDisplayFinalDatable(ServiceApplicabilityRuleCopyDataTable eventDataTable)
	{
		try{
			setVisableDailog(false);
			applicabilityList.clear();
			System.out.println(eventDataTable);
			List<Object> serviceList = iserviceApplicabilityService.fetchrecordsforDataTable(eventDataTable.getCountryId(), eventDataTable.getApplicationCountryId(), eventDataTable.getCurrencyId(), eventDataTable.getDelivaryId(), eventDataTable.getRemittenceModeid());
			// ServiceApplicabilityRuleDataTable
			setRenderDataTable(true);
			setRenderSubmit(true);
			setRenderExit(true);
			for (int i = 0; i < serviceList.size(); i++) {
				{
					Object[] object = (Object[]) serviceList.get(i);
					ServiceApplicabilityRuleDataTable	serviceAppRule = new ServiceApplicabilityRuleDataTable();
					if(object[0]!=null) {
						serviceAppRule.setFieldName(String.valueOf(object[0]));
					}
					if(object[1]!=null) {
						serviceAppRule.setFielDesc(String.valueOf(object[1]));
					}
					//if(checkYesOrNo){
					if(object[2]!=null) {
						serviceAppRule.setNavigable(String.valueOf(object[2]));
					}
					if(object[3]!=null) {
						serviceAppRule.setMandatory(String.valueOf(object[3]));
					}
					if(object[4]!=null) {
						serviceAppRule.setFielType(String.valueOf(object[4]));
					}
					if(object[5]!=null) {
						serviceAppRule.setValidation(String.valueOf(object[5]));
					}
					if(object[6]!=null) {
						serviceAppRule.setMax(new BigDecimal(String.valueOf(object[6])));
					}
					if(object[7]!=null) {
						serviceAppRule.setMin(new BigDecimal(String.valueOf(object[7])));
					}
					if(object[8]!=null) {
						serviceAppRule.setServiceApplanguageId(new BigDecimal(String.valueOf(object[8])));
					}
					//}
					serviceAppRule.setServiceApplivcabilityRuleId(null);
					serviceAppRule.setLstLanguageTypes(lstLanguageTypes);
					applicabilityList.add(serviceAppRule);
				}
				/*try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/serviceapplicabilityrule.xhtml");
			} catch (IOException e) {
			}*/
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setExceptionMessage("Method Name::fetchAndDisplayFinalDatable"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception e) {

			log.info("Method: fetchAndDisplayFinalDatable "+e);
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
			return; 
		}
	}	



	public List<ServiceApplicabilityRuleCopyDataTable> getDataTable() {
		return dataTable;
	}

	public void setDataTable(List<ServiceApplicabilityRuleCopyDataTable> dataTable) {
		this.dataTable = dataTable;
	}



	public void clickNo() throws IOException{
		try{
			setVisableDailog(false);
			/* setRenderDataTable(true);
		 setRenderSubmit(true);
		 setRenderExit(true);
		 getDataListFromView();
		 FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/serviceapplicabilityrule.xhtml");*/

			/*	applicabilityList.clear();

		List<ServiceApplicabilityRule> serviceList = iserviceApplicabilityService.getCopyRecord(sessionStateManage.getCountryId(),getRemittanceModeId(), getDeliveryId());

        if(serviceList.size()>0){
        	 setRenderDataTable(true);
			 setRenderSubmit(true);
			 setRenderExit(true);

			 for(ServiceApplicabilityRule viewData:serviceList){

				 serviceAppRule = new ServiceApplicabilityRuleDataTable();

				 serviceAppRule.setFieldName(viewData.getFieldName());
				 serviceAppRule.setFielDesc(viewData.getFieldDesc());
				 serviceAppRule.setNavigable(null);
				 serviceAppRule.setMandatory(null);
				 serviceAppRule.setFielType(null);

				 serviceAppRule.setValidation(null);
				 serviceAppRule.setMax(null);
				 serviceAppRule.setMin(null);

				 applicabilityList.add(serviceAppRule);


								 }
			 FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/serviceapplicabilityrule.xhtml");

        }*/

			dataTable.clear();

			@SuppressWarnings("unchecked")
			List<Object> applicabilitylist=iserviceApplicabilityService.isCombinationExist(getDeliveryId(),getRemittanceModeId(),sessionStateManage.getCountryId());



			for (int i = 0; i < applicabilitylist.size(); i++) {

				Object[] object = (Object[]) applicabilitylist.get(i);

				ServiceApplicabilityRuleCopyDataTable  copyDataTable = new ServiceApplicabilityRuleCopyDataTable();


				copyDataTable.setCountryId(new BigDecimal(String.valueOf(object[0])));
				copyDataTable.setCurrencyId(new BigDecimal(String.valueOf(object[1])));
				copyDataTable.setRemittenceModeid(new BigDecimal(String.valueOf(object[2])));
				copyDataTable.setDelivaryId(new BigDecimal(String.valueOf(object[3])));
				copyDataTable.setRemittenceMode(mapRemittanceList.get(copyDataTable.getRemittenceModeid()));
				copyDataTable.setCurrencyName(mapCurrencyList.get(copyDataTable.getCountryId()));
				copyDataTable.setDelivary(mapDeliveryList.get(copyDataTable.getDelivaryId()));
				copyDataTable.setCountryName(mapCountryList.get(copyDataTable.getCountryId()));
				copyDataTable.setApplicationCountryId(sessionStateManage.getCountryId());
				dataTable.add(copyDataTable);
				if(new BigDecimal(String.valueOf(object[0]))!=null && new BigDecimal(String.valueOf(object[1]))!=null){
					fetchAndDisplayFinalDatableNo(copyDataTable);
					break;
				}

			}

			setCheckYesOrNo(false);
			RequestContext context = RequestContext.getCurrentInstance();

			try {
				new Thread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setExceptionMessage("Method Name::clickNo"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception e) {

			log.info("Method: clickNo "+e);
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
			return; 
		}
		//context.execute("copyOne.show();");

	}

	public void fetchAndDisplayFinalDatableNo(ServiceApplicabilityRuleCopyDataTable eventDataTable)
	{
		applicabilityList.clear();
		System.out.println(eventDataTable);
		try{
			List<Object> serviceList = iserviceApplicabilityService.fetchrecordsforDataTable(eventDataTable.getCountryId(), eventDataTable.getApplicationCountryId(), eventDataTable.getCurrencyId(), eventDataTable.getDelivaryId(), eventDataTable.getRemittenceModeid());
			//toFetchAllLanguages();
			// ServiceApplicabilityRuleDataTable
			setRenderDataTable(true);
			setRenderSubmit(true);
			setRenderExit(true);
			for (int i = 0; i < serviceList.size(); i++) {
				{
					Object[] object = (Object[]) serviceList.get(i);
					serviceAppRule = new ServiceApplicabilityRuleDataTable();
					if(object[0]!=null) {
						serviceAppRule.setFieldName(String.valueOf(object[0]));
					}
					if(object[1]!=null) {
						serviceAppRule.setFielDesc(String.valueOf(object[1]));
					}

					serviceAppRule.setNavigable(null);

					serviceAppRule.setMandatory(null);

					serviceAppRule.setFielType(null);

					serviceAppRule.setValidation(null);

					serviceAppRule.setMax(null);

					serviceAppRule.setMin(null);
					serviceAppRule.setServiceApplivcabilityRuleId(null);
					serviceAppRule.setServiceApplanguageId(null);
					serviceAppRule.setLstLanguageTypes(lstLanguageTypes);

					applicabilityList.add(serviceAppRule);
				}
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setExceptionMessage("Method Name::fetchAndDisplayFinalDatableNo"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception e) {

			log.info("Method: fetchAndDisplayFinalDatableNo "+e);
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
			return; 
		}
	}

	public void okSaved(){

		clear();
		setRenderFirstPanel(true);
		setRenderDataTableApprove(false);
		setBooRenberDataTableApprovalList(false);
		setBooRenderApproveButton(false);
		applicabilityList.clear();
		setRenderDataTable(false);
		getCountryNameList();

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/serviceapplicabilityrule.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//un used method
	public void addRecordsToDataTable(){
		applicabilityList.clear();
		serviceApplicabilityRuleList.clear();
		List<ServiceApplicabilityRule> serviceList = iserviceApplicabilityService.getCopyRecord(sessionStateManage.getCountryId(),getRemittanceModeId(), getDeliveryId());

		serviceApplicabilityRuleList=iserviceApplicabilityService.getFetchReCordFormDB(sessionStateManage.getCountryId(),getCountryId(),getCurrencyId(),getRemittanceModeId(),getDeliveryId());


		if(serviceApplicabilityRuleList.size()==0){
			ServiceApplicabilityRuleDataTable serviceApplicabilityRuleDataTable=new ServiceApplicabilityRuleDataTable();
			serviceApplicabilityRuleDataTable.setCountryId(getCountryId());
			serviceApplicabilityRuleDataTable.setCurrencyId(getCurrencyId());
			serviceApplicabilityRuleDataTable.setDeliveryId(getDeliveryId());
			serviceApplicabilityRuleDataTable.setRemittanceModeId(getRemittanceModeId());
			serviceApplicabilityRuleDataTable.setCreatedBy(sessionStateManage.getUserName());
			serviceApplicabilityRuleDataTable.setCreatedDate(new Date());
			getDataListFromView();

		}else{
			view();	
		}
		if(serviceList.size()>0){
			setRenderDataTable(false);
			setRenderSubmit(false);
			setRenderExit(false);
			//setDeliveryId(serviceList.get(0).getDeliveryModeId().getDeliveryModeId());
			getDeliveryList();
			setDeliveryName(mapDeliveryList.get(serviceList.get(0).getDeliveryModeId().getDeliveryModeId()));
			setRemittanceName(mapRemittanceList.get(serviceList.get(0).getRemittanceModeId().getRemittanceModeId()));
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("copy.show();");

		}


	}
	//added koti 11/06/2015

	private Boolean booRenberDataTableApprovalList=false;
	private Boolean booRenderApproveButton;


	public Boolean getBooRenderApproveButton() {
		return booRenderApproveButton;
	}

	public void setBooRenderApproveButton(Boolean booRenderApproveButton) {
		this.booRenderApproveButton = booRenderApproveButton;
	}

	public Boolean getBooRenberDataTableApprovalList() {
		return booRenberDataTableApprovalList;
	}

	public void setBooRenberDataTableApprovalList(Boolean booRenberDataTableApprovalList) {
		this.booRenberDataTableApprovalList = booRenberDataTableApprovalList;
	}



	public void addRecordsToDataTableToApprove(){
		//if(getCountryId() !=null && getCurrencyId() !=null && getRemittanceModeId() !=null && getDeliveryId() !=null){
		if(applicabilityListApprovalList != null && !applicabilityListApprovalList.isEmpty()){
			applicabilityListApprovalList.clear();
		}

		if(lstApproved != null && !lstApproved.isEmpty()){
			lstApproved.clear();
		}

		try{
			//serviceApplicabilityRuleList.clear();
			List<ServiceApplicabilityRule> serviceApplicabilityList=iserviceApplicabilityService.getAllRecordsToFetchFromDb(sessionStateManage.getCountryId(),getCountryId(),getCurrencyId(),getRemittanceModeId(),getDeliveryId());
			if(serviceApplicabilityList.size()>0){
				for (ServiceApplicabilityRule serviceApplicabilityRule : serviceApplicabilityList) {
					/*if(!(serviceApplicabilityRule.getModifiedBy() == null ?  serviceApplicabilityRule.getCreatedBy() : serviceApplicabilityRule.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())){*/
					ServiceApplicabilityRuleDataTable serviceApplicabilityRuleDt=new ServiceApplicabilityRuleDataTable();
					serviceApplicabilityRuleDt.setCountryId(serviceApplicabilityRule.getCountryId().getCountryId());
					serviceApplicabilityRuleDt.setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(),serviceApplicabilityRule.getCountryId().getCountryId()));
					//serviceApplicabilityRuleDt.setCountryName(mapCountryList.get(serviceApplicabilityRule.getCountryId().getCountryId()));
					serviceApplicabilityRuleDt.setCurrencyId(serviceApplicabilityRule.getCurrencyId().getCurrencyId());
					serviceApplicabilityRuleDt.setCurrency(generalService.getCurrencyName(serviceApplicabilityRule.getCurrencyId().getCurrencyId()));
					//serviceApplicabilityRuleDt.setCurrency(mapCurrencyList.get(serviceApplicabilityRule.getCurrencyId().getCurrencyId()));
					serviceApplicabilityRuleDt.setRemittanceModeId(serviceApplicabilityRule.getRemittanceModeId().getRemittanceModeId());
					serviceApplicabilityRuleDt.setRemittance(remittanceModeService.getRemittanceDesc(serviceApplicabilityRule.getRemittanceModeId().getRemittanceModeId(),sessionStateManage.getLanguageId()));
					//serviceApplicabilityRuleDt.setRemittance(mapRemittanceList.get(serviceApplicabilityRule.getRemittanceModeId().getRemittanceModeId()));
					serviceApplicabilityRuleDt.setDeliveryId(serviceApplicabilityRule.getDeliveryModeId().getDeliveryModeId());
					serviceApplicabilityRuleDt.setDelivery(deliveryModeService.getDeliveryDesc(serviceApplicabilityRule.getDeliveryModeId().getDeliveryModeId(),sessionStateManage.getLanguageId()));
					//serviceApplicabilityRuleDt.setDelivery(mapDeliveryList.get(serviceApplicabilityRule.getDeliveryModeId().getDeliveryModeId()));
					serviceApplicabilityRuleDt.setServiceApplivcabilityRuleId(serviceApplicabilityRule.getServiceApplicabilityRuleId());
					serviceApplicabilityRuleDt.setFieldName(serviceApplicabilityRule.getFieldName());
					serviceApplicabilityRuleDt.setFielDesc(serviceApplicabilityRule.getFieldDesc());
					serviceApplicabilityRuleDt.setNavigable(serviceApplicabilityRule.getNavicable());
					serviceApplicabilityRuleDt.setMandatory(serviceApplicabilityRule.getMandatory());
					serviceApplicabilityRuleDt.setFielType(serviceApplicabilityRule.getFieldType());
					serviceApplicabilityRuleDt.setValidation(serviceApplicabilityRule.getValidate());
					serviceApplicabilityRuleDt.setMax(serviceApplicabilityRule.getMaxLenght());
					serviceApplicabilityRuleDt.setMin(serviceApplicabilityRule.getMinLenght());
					serviceApplicabilityRuleDt.setCreatedBy(serviceApplicabilityRule.getCreatedBy());
					serviceApplicabilityRuleDt.setCreatedDate(serviceApplicabilityRule.getCreatedDate());
					serviceApplicabilityRuleDt.setModifiedBy(serviceApplicabilityRule.getModifiedBy());
					serviceApplicabilityRuleDt.setModifiedDate(serviceApplicabilityRule.getModifiedDate());
					serviceApplicabilityRuleDt.setServiceApplivcabilityRuleId(serviceApplicabilityRule.getServiceApplicabilityRuleId());
					serviceApplicabilityRuleDt.setIsCheck(false);
					serviceApplicabilityRuleDt.setServiceApplanguageId(serviceApplicabilityRule.getLanguageId());
					String languageName=iserviceApplicabilityService.toFetchLanguageName(serviceApplicabilityRule.getLanguageId());
					serviceApplicabilityRuleDt.setLanduageName(languageName);
					serviceApplicabilityRuleDt.setUserName(serviceApplicabilityRule.getModifiedBy()==null?serviceApplicabilityRule.getCreatedBy() : serviceApplicabilityRule.getModifiedBy());
					if(serviceApplicabilityRule.getModifiedBy()==null?serviceApplicabilityRule.getCreatedBy().equalsIgnoreCase(sessionStateManage.getUserName()) : serviceApplicabilityRule.getModifiedBy().equalsIgnoreCase(sessionStateManage.getUserName())){
						serviceApplicabilityRuleDt.setDisableCheck(true);

					}else{
						serviceApplicabilityRuleDt.setDisableCheck(false);
					}

					applicabilityListApprovalList.add(serviceApplicabilityRuleDt);
					setBooRenberDataTableApprovalList(true);
					setBooRenderApproveButton(true);


					/*}else{
						RequestContext.getCurrentInstance().execute("noRecord.show();");
						setBooRenberDataTableApprovalList(false);
						setBooRenderApproveButton(false);
						return;
					}*/
				}


			}else{
				RequestContext.getCurrentInstance().execute("noRecord.show();");
				setBooRenberDataTableApprovalList(false);
				setBooRenderApproveButton(false);
				return;
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setExceptionMessage("Method Name::addRecordsToDataTableToApprove"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		} catch (Exception e) {

			log.info("Method: addRecordsToDataTableToApprove "+e);
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("saveerror.show();");
			return; 
		}
		//}
	}

	public void approvelCheck(ServiceApplicabilityRuleDataTable serviceApplicabilityRule) {
		if (!(serviceApplicabilityRule.getModifiedBy() == null ? serviceApplicabilityRule.getCreatedBy() : serviceApplicabilityRule.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())) {
			String approvalMsg = iserviceApplicabilityService.checkServiceApproveMultiUser(serviceApplicabilityRule.getServiceApplivcabilityRuleId(), sessionStateManage.getUserName());
			if (approvalMsg.equalsIgnoreCase("Success")) {
				RequestContext.getCurrentInstance().execute("approvedsucc.show();");
				return;
			}else{
				RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				return;	
			}
		} else {
			RequestContext.getCurrentInstance().execute("notapproved.show();");
			setBooRenberDataTableApprovalList(true);
			return;
		}
	}


	public void approvalAllRecords(){
		if(applicabilityListApprovalList.size() != 0){
			String list = "Fail";
			/*if(lstApproved!=null && lstApproved.size()==0)
			{
				RequestContext.getCurrentInstance().execute("slectOneRecord.show();");
				return; 
			}*/
			if(lstApproved.size() != 0){
				try{
					list=iserviceApplicabilityService.updateApproveRecords(lstApproved,sessionStateManage.getUserName());
				}catch(NullPointerException ne){
					log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setExceptionMessage("Method Name::approvalAllRecords"); 
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return; 
				} catch (Exception e) {

					log.info("Method: approvalAllRecords "+e);
					setExceptionMessage(e.getMessage());
					RequestContext.getCurrentInstance().execute("saveerror.show();");
					return; 
				}
				if(list.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("approvedsucc.show();");
					if(lstApproved != null && !lstApproved.isEmpty()){
						lstApproved.clear();
					}
					return;
				}else{
					RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
					return;
				}
			}else
			{
				RequestContext.getCurrentInstance().execute("slectOneRecord.show();");
				return; 
			}

		}


		/*for (ServiceApplicabilityRuleDataTable dataTable : selectChecks) {
			if(selectChecks !=null){
				if(dataTable.getFielType() !=null){
					lstApproved.add(dataTable.getServiceApplivcabilityRuleId());
				}
			}else{
				RequestContext.getCurrentInstance().execute("notapproved.show();");
			}
			lstApproved.add(dataTable.getServiceApplivcabilityRuleId());
		}
		if(lstApproved.size()>=1){
			String list;
			//iserviceApplicabilityService.updateApprovalRecords(lstApproved,sessionStateManage.getUserName());
			list=iserviceApplicabilityService.updateApproveRecords(lstApproved,sessionStateManage.getUserName());
			if(list.equalsIgnoreCase("Success")){
				RequestContext.getCurrentInstance().execute("approvedsucc.show();");
				lstApproved.clear();
				return;
			}else{
				RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				return;
			}
		}else{
			RequestContext.getCurrentInstance().execute("notapproved.show();");
			setBooRenberDataTableApprovalList(true);
			setBooRenderApproveButton(true);
		}*/
	}


	public void clickOnOkToApprovel(){
		applicabilityListApprovalList.clear();
		addRecordsToDataTableToApprove();
		try {
			//fetchRecordsDb();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/serviceapplicabilityruleapproval.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<ServiceApplicabilityRuleDataTable> selectChecks;
	List<BigDecimal> lstApproved = new CopyOnWriteArrayList<BigDecimal>();
	private Boolean isCheck=false;



	public Boolean getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}

	public List<ServiceApplicabilityRuleDataTable> getSelectChecks() {
		return selectChecks;
	}

	public void setSelectChecks(List<ServiceApplicabilityRuleDataTable> selectChecks) {
		this.selectChecks = selectChecks;
	}

	public void clickOnOkButton(){
		applicabilityListApprovalList.clear();
		addRecordsToDataTableToApprove();
		try {
			//fetchRecordsDb();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/serviceapplicabilityruleapproval.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selecatAndDeselectAll(){
		if(selectAll){
			for(ServiceApplicabilityRuleDataTable serviceAppApproval : applicabilityListApprovalList){
				if(!serviceAppApproval.getDisableCheck()){
					serviceAppApproval.setIsCheck(true);
					lstApproved.add(serviceAppApproval.getServiceApplivcabilityRuleId());
				}
			}
		}else{
			for(ServiceApplicabilityRuleDataTable serviceAppApproval : applicabilityListApprovalList){
				serviceAppApproval.setIsCheck(false);
			}
			if(lstApproved != null && !lstApproved.isEmpty()){
				lstApproved.clear();
			}
		}
	}

	public void addingServiceAppApprovalRecord(ServiceApplicabilityRuleDataTable serviceAppApprovalRecord){

		if(serviceAppApprovalRecord.getIsCheck()){
			lstApproved.add(serviceAppApprovalRecord.getServiceApplivcabilityRuleId());
		}else{
			for (BigDecimal selectedRec : lstApproved) {
				if(selectedRec.compareTo(serviceAppApprovalRecord.getServiceApplivcabilityRuleId())==0){
					lstApproved.remove(selectedRec);
				}
			}
		}
	}


	private BigDecimal languageId;
	private String landuageName;

	public BigDecimal getLanguageId() {
		return languageId;
	}

	public void setLanguageId(BigDecimal languageId) {
		this.languageId = languageId;
	}

	public String getLanduageName() {
		return landuageName;
	}

	public void setLanduageName(String landuageName) {
		this.landuageName = landuageName;
	}

	private List<LanguageType> lstLanguageTypes= new ArrayList<LanguageType>();

	public List<LanguageType> getLstLanguageTypes() {
		return lstLanguageTypes;
	}
	public void setLstLanguageTypes(List<LanguageType> lstLanguageTypes) {
		this.lstLanguageTypes = lstLanguageTypes;
	}

	public void toFetchAllLanguages(){
		try{
			lstLanguageTypes=generalService.getAllLanguages();      
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::"+exception.getMessage());
		}
	}


}
