package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import com.amg.exchange.remittance.service.IBankServiceRuleMasterService;
import com.amg.exchange.remittance.service.IRoutingSetUpDetailsService;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.IBeneCountryServices;
import com.amg.exchange.treasury.service.IRatesUpdateService;
import com.amg.exchange.treasury.service.IRemittanceModeService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestApprovService;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("benecountryservice")
@Scope("session")
public class BeneCountryServicesBean<T> implements Serializable {
	private static final long serialVersionUID = -3943860350339577651L;
	private static final Logger logger = Logger.getLogger(BeneCountryServicesBean.class);
	@SuppressWarnings("unused")
	private List<CountryMasterDesc> listCountry = new ArrayList<CountryMasterDesc>();
	@SuppressWarnings("unused")
	private List<CurrencyMaster> listCurrency = new ArrayList<CurrencyMaster>();
	private List<ServiceMasterDesc> listService = new ArrayList<ServiceMasterDesc>();
	private List<RemittanceModeDescription> listremitacy = new ArrayList<RemittanceModeDescription>();
	private List<DeliveryModeDesc> listdelivery = new ArrayList<DeliveryModeDesc>();
	private List<BeneCountryService> lstbene = new ArrayList<BeneCountryService>();
	private List<BeneCountryServiceData> lstbeneCountryServiceData = new CopyOnWriteArrayList<BeneCountryServiceData>();
	private List<BeneCountryServiceData> lstNewRecords = new CopyOnWriteArrayList<BeneCountryServiceData>();
	private Map<BigDecimal, String> listCountryMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> listCurrencyMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> listServiceMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> listremitacyMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> listdeliveryMap = new HashMap<BigDecimal, String>();
	SessionStateManage sessionmanage = new SessionStateManage();
	private String englishDeliveryName;
	private BigDecimal countryId;
	private BigDecimal currencyId;
	private BigDecimal serviceId;
	private BigDecimal remitancyId;
	private BigDecimal deliveryId;
	private BigDecimal beneCountryServicesId = null; //
	private boolean disable = false;
	private boolean datatable = false;
	private Boolean renderEditButton;
	private Boolean booSubmitButton;
	private String countryName;
	private String countrycurr;
	private String serviceName;
	private String remittName;
	private String deliveryName;
	private String dynamicLabelForActivateDeactivate;
	private boolean booEditButton;
	private Boolean clearPanel;
	boolean editFlag;

	@Autowired
	IRoutingSetUpDetailsService<T> iroutingSetUpDetailsService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IBeneCountryServices<T> ibeneCountryServices;
	@Autowired
	IRatesUpdateService<T> iRatesUpdateService;
	@Autowired
	IRemittanceModeService remittanceModeService;
	
	@Autowired
	IBankServiceRuleMasterService ibankServiceRuleMasterService;
	@Autowired
	ISpecialCustomerDealRequestApprovService<T> CustomerSpecialDealRequest;

	public Boolean getBooSubmitButton() {
		return booSubmitButton;
	}

	public void setBooSubmitButton(Boolean booSubmitButton) {
		this.booSubmitButton = booSubmitButton;
	}

	public String getEnglishDeliveryName() {
		return englishDeliveryName;
	}

	public void setEnglishDeliveryName(String englishDeliveryName) {
		this.englishDeliveryName = englishDeliveryName;
	}

	public List<BeneCountryServiceData> getLstNewRecords() {
		return lstNewRecords;
	}

	public void setLstNewRecords(List<BeneCountryServiceData> lstNewRecords) {
		this.lstNewRecords = lstNewRecords;
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

	
	public boolean isBooEditButton() {
		return booEditButton;
	}

	public void setBooEditButton(boolean booEditButton) {
		this.booEditButton = booEditButton;
	}

	public BigDecimal getServiceId() {
		return serviceId;
	}

	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	public BigDecimal getRemitancyId() {
		return remitancyId;
	}

	public void setRemitancyId(BigDecimal remitancyId) {
		this.remitancyId = remitancyId;
	}

	
	public Boolean getClearPanel() {
		return clearPanel;
	}

	public void setClearPanel(Boolean clearPanel) {
		this.clearPanel = clearPanel;
	}

	public BigDecimal getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(BigDecimal deliveryId) {
		this.deliveryId = deliveryId;
	}

	

	public void setListCountry(List<CountryMasterDesc> listCountry) {
		this.listCountry = listCountry;
	}
	
	

	public void setListCurrency(List<CurrencyMaster> listCurrency) {
		this.listCurrency = listCurrency;
	}

	

	public void setListService(List<ServiceMasterDesc> listService) {
		this.listService = listService;
	}

	public List<RemittanceModeDescription> getListremitacy() {
		return listremitacy;
	}

	public void setListremitacy(List<RemittanceModeDescription> listremitacy) {
		this.listremitacy = listremitacy;
	}
	
	
	public void setListdelivery(List<DeliveryModeDesc> listdelivery) {
		this.listdelivery = listdelivery;
	}

	

	public boolean isEditFlag() {
		return editFlag;
	}

	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}
	

	public void popCurrency() {
		generalService.getCountryCurrencyList(getCountryId());
	}

	public List<BeneCountryService> getLstbene() {
		return lstbene;
	}

	public void setLstbene(List<BeneCountryService> lstbene) {
		this.lstbene = lstbene;
	}

	public List<BeneCountryServiceData> getLstbeneCountryServiceData() {
		return lstbeneCountryServiceData;
	}

	public void setLstbeneCountryServiceData(List<BeneCountryServiceData> lstbeneCountryServiceData) {
		this.lstbeneCountryServiceData = lstbeneCountryServiceData;
	}

	public BigDecimal getBeneCountryServicesId() {
		return beneCountryServicesId;
	}

	public void setBeneCountryServicesId(BigDecimal beneCountryServicesId) {
		this.beneCountryServicesId = beneCountryServicesId;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public boolean isDatatable() {
		return datatable;
	}

	public void setDatatable(boolean datatable) {
		this.datatable = datatable;
	}
	
	public List<CountryMasterDesc> getListCountry() {
		  List<CountryMasterDesc> countryList=null;
		  try{
		countryList = generalService.getCountryList(sessionmanage.getLanguageId());
		for (CountryMasterDesc countryMasterDesc : countryList) {
			listCountryMap.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
		}
		  }catch(Exception exception){
			    logger.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			  }
		return countryList;
	}

	public List<CurrencyMaster> getListCurrency() {
		  List<CurrencyMaster> listCurrency=null;
		  try{
		listCurrency = generalService.getCurrencyList();
		for (CurrencyMaster currencyMaster : listCurrency) {
			listCurrencyMap.put(currencyMaster.getCurrencyId(), currencyMaster.getCurrencyName());
		}
		  }catch(Exception exception){
			    logger.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage());     
		  }
		return listCurrency;
	}
	public List<ServiceMasterDesc> getListService() {
		  List<ServiceMasterDesc> listService = null;
		  try{
			    listService = ibeneCountryServices.lstServices(sessionmanage.getLanguageId());
		for (ServiceMasterDesc serviceMasterDesc : listService) {
			listServiceMap.put(serviceMasterDesc.getServiceMaster().getServiceId(), serviceMasterDesc.getLocalServiceDescription());
		}
		  }catch(Exception exception){
			    logger.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage());     
		  }
		return listService;
	}

	public List<DeliveryModeDesc> getListdelivery() {
		for (DeliveryModeDesc deliveryModeDesc : listdelivery) {
			listdeliveryMap.put(deliveryModeDesc.getDeliveryMode().getDeliveryModeId(), deliveryModeDesc.getEnglishDeliveryName());
		}
		return listdelivery;
	}

	public void popRemitanceMode() {
		  try{
		listremitacy = ibeneCountryServices.lstRemitancemode(getServiceId(), sessionmanage.getLanguageId());
		if(listremitacy.size() != 0){
			for (RemittanceModeDescription remittanceModeDescription : listremitacy) {
				listremitacyMap.put(remittanceModeDescription.getRemittanceModeMaster().getRemittanceModeId(), remittanceModeDescription.getRemittanceDescription());
			}
		}else{
			listremitacy.clear();
			listremitacyMap.clear();
		}
		  }catch(Exception exception){
			    logger.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			  }
		
	}
	
	public void popDeliveryMode(){
		try{
		setDeliveryId(null);
		List<DeliveryModeDesc> deliverydata = new ArrayList<DeliveryModeDesc>();
		//	deliverydata = iroutingSetUpDetailsService.getDeliveryMode(sessionmanage.getLanguageId(), getServiceId());
		//	deliverydata = ibankServiceRuleMasterService.getDeliverFromRemittance(sessionmanage.getCountryId(),getCountryId(),getRemitancyId(),sessionmanage.getLanguageId());
		
		deliverydata = ibankServiceRuleMasterService.getDeliveryFromRemittanceAndService(sessionmanage.getCountryId(),getCountryId(),getRemitancyId(),sessionmanage.getLanguageId(),getServiceId());
			if(deliverydata.size() != 0){
				listdelivery.clear();
				listdelivery.addAll(deliverydata);
				listServiceMap.clear();
				for (DeliveryModeDesc deliveryModeDesc : listdelivery) {
					listServiceMap.put(deliveryModeDesc.getDeliveryMode().getDeliveryModeId(), deliveryModeDesc.getEnglishDeliveryName());
				}
			}else {
				listdelivery.clear();
				listServiceMap.clear();
			}
		}catch(Exception exception){
			    logger.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			  }
	}

	public void save() {
		try{
		BeneCountryService beneservice = null;
		
		if (lstbeneCountryServiceData.size() > 0) {
			for (BeneCountryServiceData beneCountryServiceData : lstbeneCountryServiceData) {
				beneservice = new BeneCountryService();
				beneservice.setBeneCountrySeerviceId(beneCountryServiceData.getBnCntryservicepk());
				if (beneCountryServiceData.getBnCntryservicepk() != null) {
					beneservice.setCreateBy(beneCountryServiceData.getCreateBy());
					beneservice.setCreateDate(beneCountryServiceData.getCreateDate());
					beneservice.setModifiedBy(beneCountryServiceData.getModifiedBy());
					beneservice.setModifiedDate(beneCountryServiceData.getModifiedDate());
					beneservice.setRemarks(beneCountryServiceData.getRemarks());
					beneservice.setApprovedBy(beneCountryServiceData.getApprovedBy());
					beneservice.setApprovedDate(beneCountryServiceData.getApprovedDate());
					//beneservice.setIsActive(beneCountryServiceData.getIsActive());
				} else {
					beneservice.setCreateBy(beneCountryServiceData.getCreateBy());
					beneservice.setCreateDate(beneCountryServiceData.getCreateDate());
					
					//beneservice.setIsActive(Constants.U);
				}
				
				beneservice.setIsActive(beneCountryServiceData.getIsActive());
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(sessionmanage.getCountryId());
				beneservice.setApplicationCountryId(countryMaster);
				CurrencyMaster currency = new CurrencyMaster();
				currency.setCurrencyId(beneCountryServiceData.getCurrencyId());
				beneservice.setCurrencyId(currency);
				ServiceMaster service = new ServiceMaster();
				service.setServiceId(beneCountryServiceData.getServiceId());
				beneservice.setServiceId(service);
				RemittanceModeMaster remitance = new RemittanceModeMaster();
				remitance.setRemittanceModeId(beneCountryServiceData.getRemitanceId());
				beneservice.setRemitanceId(remitance);
				DeliveryMode delivery = new DeliveryMode();
				delivery.setDeliveryModeId(beneCountryServiceData.getDeliveryId());
				beneservice.setDeliveryId(delivery);
				CountryMaster beniCountryMaster = new CountryMaster();
				beniCountryMaster.setCountryId(beneCountryServiceData.getCountryId());
				beneservice.setBeneCountryId(beniCountryMaster);
				ibeneCountryServices.save(beneservice);
				beneservice = null;
			}
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("succsses.show();");
			/* } */
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("emptyData.show();");
			/* } */
		}
		}catch(NullPointerException ne){
		  logger.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::saveDataTableRecods"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
		}catch(Exception exception){
		  logger.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		}
	}

	public void add() {
		  try{
		lstbene.clear();
		boolean checkList = false;
		setBooEditButton(false);
		setClearPanel(false);
		if (!isEditFlag()) {
			lstbene = ibeneCountryServices.validateBene(sessionmanage.getCountryId(), getCountryId(), getCurrencyId(), getServiceId(), getRemitancyId(), getDeliveryId());
		}
		if (lstbene.size() == 0) {
			if (lstbeneCountryServiceData.size() != 0) {
				for (BeneCountryServiceData beneConrySerceData : lstbeneCountryServiceData) {
					if (beneConrySerceData.getCountryId().equals(getCountryId()) && beneConrySerceData.getCurrencyId().equals(getCurrencyId()) && beneConrySerceData.getServiceId().equals(getServiceId()) && beneConrySerceData.getRemitanceId().equals(getRemitancyId())
							&& beneConrySerceData.getDeliveryId().equals(getDeliveryId())) {
						checkList = true;
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("checkdt.show();");
						refresh1();
						break;
					}
				}
			}
			if (!checkList) {
				BeneCountryServiceData benecountry = new BeneCountryServiceData();
				// for update the record
				benecountry.setBnCntryservicepk(getBeneCountryPK());
				benecountry.setBnCntryservicepk(getBeneCountryServicesId());
				benecountry.setCountryName(listCountryMap.get(getCountryId()));
				benecountry.setCurrencyName(listCurrencyMap.get(getCurrencyId()));
				benecountry.setServiceCode(listServiceMap.get(getServiceId()));
				//benecountry.setRemitanceMode(getRemitancyId() + " - " + listremitacyMap.get(getRemitancyId()));
				//benecountry.setDeliveryMode(getDeliveryId() + " - " + listdeliveryMap.get(getDeliveryId()));
				benecountry.setRemitanceMode(listremitacyMap.get(getRemitancyId()));
				benecountry.setDeliveryMode(listdeliveryMap.get(getDeliveryId()));
				// Added by kani for Description along with code End
				benecountry.setCountryId(getCountryId());
				benecountry.setCurrencyId(getCurrencyId());
				benecountry.setServiceId(getServiceId());
				benecountry.setRemitanceId(getRemitancyId());
				benecountry.setDeliveryId(getDeliveryId());
				/*benecountry.setCreateBy(sessionmanage.getUserName());
				benecountry.setCreateDate(new Date());*/
				System.out.println("*************88status of the record");
				System.out.println(getIsActive());
				if(getBeneCountryPK() !=null ){
					benecountry.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					benecountry.setRenderEditButton(true);
					benecountry.setIsActive(Constants.U);
					benecountry.setCreateBy(getCreateBy());
					benecountry.setCreateDate(getCreateDate());
					benecountry.setModifiedBy(sessionmanage.getUserName());
					benecountry.setModifiedDate(new Date());
				}
				if(getBeneCountryPK() ==null){
					benecountry.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
					benecountry.setRenderEditButton(true);
					benecountry.setIsActive(Constants.U);
					benecountry.setCreateBy(sessionmanage.getUserName());
					benecountry.setCreateDate(new Date());
				}
				//benecountry.setDynamicLabelForActivateDeactivate(setreverActiveStatus(getIsActive()));
				/*if (getIsActive() != null && getIsActive().equals(Constants.Yes)) {
					benecountry.setRenderEditButton(true);
				} else if (getIsActive() == null || !getIsActive().equals(Constants.Yes) || !!getIsActive().equals(Constants.D)) {
					benecountry.setRenderEditButton(true);
				} else if (getIsActive() != null && getIsActive().equals(Constants.D)) {
					benecountry.setRenderEditButton(true);
				}*/
				System.out.println("*************88status of the record");
				//benecountry.setIsActive(getIsActive());
				lstbeneCountryServiceData.add(benecountry);
				if(getBeneCountryPK() ==null ){
					  lstNewRecords.add(benecountry);	  
				}
				//lstbeneCountryServiceData.addAll(lstNewRecords);
				setBooSubmitButton(false);
				setEditFlag(false);
				setDatatable(true);
				refresh1();
			}
		}/* else if (lstbene.size() > 0) {
			if (!lstbene.get(0).getIsActive().equals(Constants.Yes)) {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("pendingwithApproval.show();");
			} else {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("check.show();");
			}
			}*/
		else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("check.show();");
		}
			// refresh1();
		  }catch(NullPointerException ne){
		  logger.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::saveDataTableRecods"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
		  }catch(Exception exception){
		    logger.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		  }
	}

	
	public void refresh1() {
		setServiceId(null);
		setRemitancyId(null);
		setDeliveryId(null);
		setDisable(false);
		setBeneCountryPK(null);
		setBeneCountryServicesId(null);
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void refresh() {
		setCountryId(null);
		setCurrencyId(null);
		setServiceId(null);
		setRemitancyId(null);
		setDeliveryId(null);
		setBooEditButton(false);
		setClearPanel(false);
		setBooSubmitButton(false);
		lstbeneCountryServiceData.clear();
		setDatatable(false);
		lstNewRecords.clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionmanage.getCountryId(), sessionmanage.getUserType(), sessionmanage.getUserName(), "servicesforbenificiary.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/servicesforbenificiary.xhtml");
		} catch(Exception exception){
		    logger.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
	         }
	}

	public void clearAllFields() {
		setCountryId(null);
		setCurrencyId(null);
		setServiceId(null);
		setRemitancyId(null);
		setDeliveryId(null);
		setBeneCountryPK(null);
		listremitacyMap.clear();
		listremitacy.clear();
		listdeliveryMap.clear();
		listdelivery.clear();
		lstNewRecords.clear();
	}

	public void exit() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch(Exception exception){
		    logger.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		  }
	}




	public void editRecord(BeneCountryServiceData bean) throws Exception{
		setEditFlag(true);
		setBooSubmitButton(true);
		setBooEditButton(true);
		setClearPanel(true);
		System.out.println("**********************editRecord********************************");
		System.out.println(bean);
		System.out.println("getDeliveryMode" + bean.getDeliveryMode());
		System.out.println("************************editRecord******************************");
		setCountryId(bean.getCountryId());
		setCurrencyId(bean.getCurrencyId());
		getListService();
		setServiceId(bean.getServiceId());
		
		//setDeliveryName(bean.getDeliveryMode());
		
		// setRemitancyId(bean.getRemitanceId());
		setBeneCountryPK(bean.getBnCntryservicepk());
		setIsActive(bean.getIsActive());
		setCreateBy(bean.getCreateBy());
		setCreateDate(bean.getCreateDate());
		setModifiedBy(bean.getModifiedBy());
		setModifiedDate(bean.getModifiedDate());
		// setIsActive("U");
		popRemitanceMode();
		
		
		setRemitancyId(bean.getRemitanceId());
		System.out.println(bean.getDeliveryMode());
		popDeliveryMode();
		setDeliveryId(bean.getDeliveryId());
		List<BeneCountryService> tempList = ibeneCountryServices.validateBene(sessionmanage.getCountryId(), getCountryId(), getCurrencyId(), getServiceId(), getRemitancyId(), getDeliveryId());
		if (!tempList.isEmpty()) {
			setBeneCountryServicesId(tempList.get(0).getBeneCountrySeerviceId());
		}
		lstbeneCountryServiceData.remove(bean);
	}

	BigDecimal tempDeleteid = null;
	BeneCountryService deleterecord = null;
	BeneCountryServiceData deletebeneCountryServiceData = null;

	public void removeServiceBene(BeneCountryServiceData beneCountryServiceData) throws Exception{
		System.out.println("Entering into removeServiceBene method");
		// if Dynamic label is delete
		if ((beneCountryServiceData.getDynamicLabelForActivateDeactivate() != null && 
				(beneCountryServiceData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE))  || beneCountryServiceData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE))) {
			if (beneCountryServiceData.getBnCntryservicepk() != null) {
				System.out.println("Status " + beneCountryServiceData.getDynamicLabelForActivateDeactivate());
				System.out.println(beneCountryServiceData.getBnCntryservicepk());
				BeneCountryService beneservice = new BeneCountryService();
				beneservice.setBeneCountrySeerviceId(beneCountryServiceData.getBnCntryservicepk());
				
				if(beneCountryServiceData.getModifiedBy()==null && beneCountryServiceData.getModifiedDate()==null && beneCountryServiceData.getApprovedBy()==null && beneCountryServiceData.getApprovedDate()==null && beneCountryServiceData.getRemarks()==null) {
					beneCountryServiceData.setPermanetDeleteCheck(true);
					RequestContext.getCurrentInstance().execute("permanentDelete.show();");
					return;
				}
				else
					{
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(sessionmanage.getCountryId());
					beneservice.setApplicationCountryId(countryMaster);
					CurrencyMaster currency = new CurrencyMaster();
					currency.setCurrencyId(beneCountryServiceData.getCurrencyId());
					beneservice.setCurrencyId(currency);
					ServiceMaster service = new ServiceMaster();
					service.setServiceId(beneCountryServiceData.getServiceId());
					beneservice.setServiceId(service);
					RemittanceModeMaster remitance = new RemittanceModeMaster();
					remitance.setRemittanceModeId(beneCountryServiceData.getRemitanceId());
					beneservice.setRemitanceId(remitance);
					DeliveryMode delivery = new DeliveryMode();
					delivery.setDeliveryModeId(beneCountryServiceData.getDeliveryId());
					beneservice.setDeliveryId(delivery);
					CountryMaster beniCountryMaster = new CountryMaster();
					beniCountryMaster.setCountryId(beneCountryServiceData.getCountryId());
					beneservice.setBeneCountryId(beniCountryMaster);
					beneservice.setBeneCountrySeerviceId(beneCountryServiceData.getBnCntryservicepk());
					beneservice.setCreateBy(beneCountryServiceData.getCreateBy());
					beneservice.setCreateDate(beneCountryServiceData.getCreateDate());
					beneservice.setModifiedBy(sessionmanage.getUserName());
					beneservice.setModifiedDate(new Date());
					beneservice.setIsActive(Constants.U);
					beneCountryServiceData.setRenderEditButton(true);
					beneCountryServiceData.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					beneservice.setApprovedBy(null);
					beneservice.setApprovedDate(null);
					// modified for change for 'N' to 'U' end
					setModifiedBy(beneservice.getModifiedBy());
					setModifiedDate(beneservice.getModifiedDate());
					beneservice.setRemarks(getRemarks());
					setIsActive(beneservice.getIsActive());
					ibeneCountryServices.update(beneservice);
					
					//RequestContext.getCurrentInstance().execute("notdelet.show();");
				}
				
				
			} else {
				lstbeneCountryServiceData.remove(beneCountryServiceData);
				lstNewRecords.remove(beneCountryServiceData);
				if (lstbeneCountryServiceData.size() == 0) {
					lstbeneCountryServiceData.clear();
					setDatatable(false);
					lstNewRecords.clear();
				}
			}
		} else if ((beneCountryServiceData.getDynamicLabelForActivateDeactivate() != null && (beneCountryServiceData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) || beneCountryServiceData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE) || beneCountryServiceData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) )) {
			BeneCountryService beneservice = new BeneCountryService();
			
			beneservice.setBeneCountrySeerviceId(beneCountryServiceData.getBnCntryservicepk());
			if (beneCountryServiceData.getBnCntryservicepk() != null) {
			
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionmanage.getCountryId());
			beneservice.setApplicationCountryId(countryMaster);
			CurrencyMaster currency = new CurrencyMaster();
			currency.setCurrencyId(beneCountryServiceData.getCurrencyId());
			beneservice.setCurrencyId(currency);
			ServiceMaster service = new ServiceMaster();
			service.setServiceId(beneCountryServiceData.getServiceId());
			beneservice.setServiceId(service);
			RemittanceModeMaster remitance = new RemittanceModeMaster();
			remitance.setRemittanceModeId(beneCountryServiceData.getRemitanceId());
			beneservice.setRemitanceId(remitance);
			DeliveryMode delivery = new DeliveryMode();
			delivery.setDeliveryModeId(beneCountryServiceData.getDeliveryId());
			beneservice.setDeliveryId(delivery);
			CountryMaster beniCountryMaster = new CountryMaster();
			beniCountryMaster.setCountryId(beneCountryServiceData.getCountryId());
			beneservice.setBeneCountryId(beniCountryMaster);
			beneservice.setBeneCountrySeerviceId(beneCountryServiceData.getBnCntryservicepk());
			beneservice.setCreateBy(beneCountryServiceData.getCreateBy());
			beneservice.setCreateDate(beneCountryServiceData.getCreateDate());
			beneservice.setModifiedBy(sessionmanage.getUserName());
			beneservice.setModifiedDate(new Date());
			if (beneCountryServiceData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				beneservice.setIsActive(Constants.U);
				beneCountryServiceData.setRenderEditButton(true);
				beneCountryServiceData.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				//lstbeneCountryServiceData.remove(beneCountryServiceData);
				/* System.out.println("entering in to  Activate "); */
			} else if (beneCountryServiceData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				/* System.out.println("entering in to  DeActivate "); */
				beneservice.setIsActive(Constants.D);
				beneCountryServiceData.setRenderEditButton(true);
				beneCountryServiceData.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
			}
			beneservice.setApprovedBy(null);
			beneservice.setApprovedDate(null);
			// modified for change for 'N' to 'U' end
			setModifiedBy(beneservice.getModifiedBy());
			setModifiedDate(beneservice.getModifiedDate());
			beneservice.setRemarks(getRemarks());
			setIsActive(beneservice.getIsActive());
			ibeneCountryServices.update(beneservice);
			
			}
			view();
		}
		
		System.out.println("Exit into removeServiceBene method");
	}

	public void delete() throws IOException {
	
		deleterecord.setRemarks(getRemarks());
		// System.out.println("Remark value in delete "+deleterecord.getRemarks());
		ibeneCountryServices.update(deleterecord);
		lstbeneCountryServiceData.remove(deletebeneCountryServiceData);
		deleterecord = null;
		deletebeneCountryServiceData = null;
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/servicesforbenificiary.xhtml");
		} catch (IOException e) {
			//  Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void view() {
		  try{
		System.out.println("getCountryId " + getCountryId());
		System.out.println("getCurrencyId " + getCurrencyId());
		System.out.println("$$$$$user country id$$$$$" + sessionmanage.getCountryId());
		if (getCountryId() != null && getCurrencyId() != null) {
			lstbene.clear();
			lstbeneCountryServiceData.clear();
			lstbene = ibeneCountryServices.viewAllRecords(sessionmanage.getCountryId(), getCountryId(), getCurrencyId(),getServiceId());
			System.out.println("$$$$$$$$$$$" + sessionmanage.getCountryId() + "" + getCountryId());
			if (lstbene.size() > 0) {
				for (BeneCountryService element : lstbene) {
					BeneCountryServiceData benecountry = new BeneCountryServiceData();
					benecountry.setBnCntryservicepk(element.getBeneCountrySeerviceId());
					benecountry.setCountryName(listCountryMap.get(element.getBeneCountryId().getCountryId()));
					benecountry.setCurrencyName(listCurrencyMap.get(element.getCurrencyId().getCurrencyId()));
					try {
						benecountry.setServiceCode(iRatesUpdateService.getServiceName(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"), element.getServiceId().getServiceId()));
						//benecountry.setRemitanceMode(element.getRemitanceId().getRemittanceModeId() + " - " + remittanceModeService.getRemittanceDesc(element.getRemitanceId().getRemittanceModeId()));
						//benecountry.setDeliveryMode(element.getDeliveryId().getDeliveryModeId() + " - " + iRatesUpdateService.getdeliveryName(element.getDeliveryId().getDeliveryModeId()));
						benecountry.setRemitanceMode(remittanceModeService.getRemittanceDesc(element.getRemitanceId().getRemittanceModeId()));
						benecountry.setDeliveryMode(iRatesUpdateService.getdeliveryName(element.getDeliveryId().getDeliveryModeId()));
					} catch (Exception e) {
						System.out.println("Exception ocuured in view method : BeneCountryServicesBean");
					}
					benecountry.setCountryId(element.getBeneCountryId().getCountryId());
					benecountry.setCurrencyId(element.getCurrencyId().getCurrencyId());
					benecountry.setServiceId(element.getServiceId().getServiceId());
					benecountry.setRemitanceId(element.getRemitanceId().getRemittanceModeId());
					benecountry.setDeliveryId(element.getDeliveryId().getDeliveryModeId());
					benecountry.setCreateBy(element.getCreateBy());
					benecountry.setCreateDate(element.getCreateDate());
					benecountry.setModifiedBy(element.getModifiedBy());
					benecountry.setModifiedDate(element.getModifiedDate());
					benecountry.setIsActive(element.getIsActive());
					//benecountry.setDynamicLabelForActivateDeactivate(setreverActiveStatus(element.getIsActive()));
					benecountry.setApprovedBy(element.getApprovedBy());
					benecountry.setApprovedDate(element.getApprovedDate());
					benecountry.setRemarks(element.getRemarks());
					benecountry.setBooEditButton(false);
					if(element.getIsActive()!= null) {
					if (element.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						benecountry.setRenderEditButton(true);
						benecountry.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					}else if (element.getIsActive().equalsIgnoreCase(Constants.D)) {
						benecountry.setRenderEditButton(true);
						benecountry.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					}else if (element.getIsActive().equalsIgnoreCase(Constants.U)) {
						if(element.getModifiedBy() ==null && element.getModifiedDate() ==null){
						benecountry.setRenderEditButton(true);
						benecountry.setDynamicLabelForActivateDeactivate(Constants.DELETE);
						}else{
							benecountry.setRenderEditButton(true);
							benecountry.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						}
					}
					else {
						benecountry.setRenderEditButton(false);
					}
					}
					HashSet<BeneCountryServiceData> hs = new HashSet<BeneCountryServiceData>();
					hs.addAll(lstbeneCountryServiceData);
					lstbeneCountryServiceData.clear();
					lstbeneCountryServiceData.addAll(hs);
					lstbeneCountryServiceData.add(benecountry);
					setDatatable(true);
					setRemitancyId(null);
					setDeliveryId(null);
					setDisable(false);
					setBeneCountryPK(null);
					setBeneCountryServicesId(null);
				}
				
			}
			lstbeneCountryServiceData.addAll(lstNewRecords);
			setBooSubmitButton(false);
			/*else {
				  
				System.out.println("Starting ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				RequestContext.getCurrentInstance().execute("norecord.show();");
				System.out.println("Ending ^^^^^^^^%%#@#@#@#@#&&&&&&&&&&&&&&&&&");
				refresh1();
			}*/
		} else {
			RequestContext.getCurrentInstance().execute("chooseAnyOne.show();");
		}
		
		  }catch(NullPointerException ne){
				  logger.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				    setErrorMessage("Method Name::view"); 
				    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				    return; 
		  }catch(Exception exception){
		    logger.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		  }
	}

	// Added by kani for Beneficiary Service approval begin
	public void serviceBenificiaryApprovalNavigation() {
		System.out.println("Entering in to serviceBenificiaryApprovalNavigation");
		approvalBeanList();
		System.out.println("Entering in to serviceBenificiaryApprovalNavigation  after");
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionmanage.getCountryId(), sessionmanage.getUserType(), sessionmanage.getUserName(), "servicesforbenificiaryApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/servicesforbenificiaryApproval.xhtml");
		} catch (Exception exception) {
			  logger.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		}
	}

	// Added by kani for Beneficiary Service approval begin
	public void approvalBeanList() {
		  try{
		System.out.println("Inside approvalBeanList method  KKKKKKKKKKKK --->  " + sessionmanage.getCountryId());
		lstbene.clear();
		lstbeneCountryServiceData.clear();
		// lstbene = ibeneCountryServices.viewBean(sessionmanage.getCountryId(),
		// getCountryId(), getCurrencyId());
		lstbene = ibeneCountryServices.approvalBeanList(sessionmanage.getCountryId());
		System.out.println("approvalBeanList KKKKKKKKKKKKKKKKKKKKKKKKKKK --->   " + lstbene.size());
		if (lstbene.size() > 0) {
			for (BeneCountryService element : lstbene) {
				BeneCountryServiceData benecountry = new BeneCountryServiceData();
				benecountry.setBnCntryservicepk(element.getBeneCountrySeerviceId());
				benecountry.setCountryName(CustomerSpecialDealRequest.countryName(element.getBeneCountryId().getCountryId(), sessionmanage.getLanguageId()));
				System.out.println("KKKKKKKKKKKKKKKKKKKKKKKK    -->>" + benecountry.getCountryName());
				benecountry.setCurrencyName(generalService.getCurrencyName(element.getCurrencyId().getCurrencyId()));
				benecountry.setServiceCode(iRatesUpdateService.getServiceName(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"), element.getServiceId().getServiceId()));
				//benecountry.setRemitanceMode(element.getRemitanceId().getRemittanceModeId() + " - " + remittanceModeService.getRemittanceDesc(element.getRemitanceId().getRemittanceModeId()));
				//benecountry.setDeliveryMode(element.getDeliveryId().getDeliveryModeId() + " - " + iRatesUpdateService.getdeliveryName(element.getDeliveryId().getDeliveryModeId()));
				benecountry.setRemitanceMode(remittanceModeService.getRemittanceDesc(element.getRemitanceId().getRemittanceModeId()));
				benecountry.setDeliveryMode(iRatesUpdateService.getdeliveryName(element.getDeliveryId().getDeliveryModeId()));
				benecountry.setCountryId(element.getBeneCountryId().getCountryId());
				benecountry.setCurrencyId(element.getCurrencyId().getCurrencyId());
				benecountry.setServiceId(element.getServiceId().getServiceId());
				benecountry.setRemitanceId(element.getRemitanceId().getRemittanceModeId());
				benecountry.setDeliveryId(element.getDeliveryId().getDeliveryModeId());
				benecountry.setIsActive(element.getIsActive());
				benecountry.setCreateBy(element.getCreateBy());
				benecountry.setCreateDate(element.getCreateDate());
				benecountry.setModifiedBy(element.getModifiedBy());
				benecountry.setModifiedDate(element.getModifiedDate());
				benecountry.setBnCntryservicepk(benecountry.getBnCntryservicepk());
				benecountry.setRemarks(benecountry.getRemarks());
				System.out.println("Primary key KKKKKKKKKK ---> " + benecountry.getBnCntryservicepk());
				// beneservice.setBeneCountrySeerviceId(beneCountryServiceData.getBnCntryservicepk());
				HashSet<BeneCountryServiceData> hs = new HashSet<BeneCountryServiceData>();
				hs.addAll(lstbeneCountryServiceData);
				lstbeneCountryServiceData.clear();
				lstbeneCountryServiceData.addAll(hs);
				lstbeneCountryServiceData.add(benecountry);
			}
		}
		  }catch(NullPointerException ne){
		  logger.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::saveDataTableRecods"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
		  }catch(Exception exception){
	            logger.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		  }
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountrycurr() {
		return countrycurr;
	}

	public void setCountrycurr(String countrycurr) {
		this.countrycurr = countrycurr;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getRemittName() {
		return remittName;
	}

	public void setRemittName(String remittName) {
		this.remittName = remittName;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	private BigDecimal beneCountryPK;

	public BigDecimal getBeneCountryPK() {
		return beneCountryPK;
	}

	public void setBeneCountryPK(BigDecimal beneCountryPK) {
		this.beneCountryPK = beneCountryPK;
	}

	private String createBy;
	private Date createDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public void gotoBeneficiaryServiceApproval(BeneCountryServiceData bean) {
		/*if (CommonUtil.validateApprovedBy(sessionmanage.getUserName(), bean.getCreateBy())) {*/
		 if((bean.getModifiedBy()==null?bean.getCreateBy():bean.getModifiedBy()).equalsIgnoreCase(sessionmanage.getUserName())){
			RequestContext.getCurrentInstance().execute("notAuth.show();");
		} else {
			getListCountry();
			setCountryId(bean.getCountryId());
			getListCurrency();
			setCountryName(bean.getCountryName());
			setCurrencyId(bean.getCurrencyId());
			setCountrycurr(bean.getCurrencyName());
			getListService();
			setServiceId(bean.getServiceId());
			setServiceName(bean.getServiceCode());
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2" + getServiceId());
			getListremitacy();
			setRemitancyId(bean.getRemitanceId());
			setRemittName(bean.getRemitanceMode());
			getListdelivery();
			setDeliveryId(bean.getDeliveryId());
			setDeliveryName(bean.getDeliveryMode());
			setBeneCountryPK(bean.getBnCntryservicepk());
			setIsActive(bean.getIsActive());
			setCreateBy(bean.getCreateBy());
			setCreateDate(bean.getCreateDate());
			setModifiedBy(bean.getModifiedBy());
			setModifiedDate(bean.getModifiedDate());
			// setApprovedBy(bean.getApprovedBy());
			// setApprovedDate(bean.getApprovedDate());
			// setBeneCountrySeerviceId()
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/servicesforbenificiaryApproval2.xhtml");
			} catch(Exception exception){
				    logger.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				    setErrorMessage(exception.getMessage()); 
				    RequestContext.getCurrentInstance().execute("error.show();");
				    return;       
				  }
		}
	}

	public void approveSelectedBeneCountryService() {
		/*// lstbeneCountryServiceData.clear();
		// private List<BeneCountryServiceData> lstbeneCountryServiceData1=new
		// ArrayList<BeneCountryServiceData>();
		System.out.println("Entering in to approveSelectedBeneCountryService  KKKKKKKKKKKK  -->" + "username--->  " + sessionmanage.getUserName() + "create by ---> " + getCreateBy());
		
			System.out.println("Inside if approveSelectedBeneCountryService  KKKKKK");
			BeneCountryService beneservice = new BeneCountryService();
			beneservice.setBeneCountrySeerviceId(beneCountryPK);
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionmanage.getCountryId());
			beneservice.setApplicationCountryId(countryMaster);
			CurrencyMaster currency = new CurrencyMaster();
			currency.setCurrencyId(getCurrencyId());
			beneservice.setCurrencyId(currency);
			ServiceMaster service = new ServiceMaster();
			service.setServiceId(getServiceId());
			beneservice.setServiceId(service);
			RemittanceModeMaster remitance = new RemittanceModeMaster();
			remitance.setRemittanceModeId(getRemitancyId());
			beneservice.setRemitanceId(remitance);
			DeliveryMode delivery = new DeliveryMode();
			delivery.setDeliveryModeId(getDeliveryId());
			beneservice.setDeliveryId(delivery);
			
			 * CountryMaster beniCountryMaster = new CountryMaster();
			 * beniCountryMaster.setCountryId(getCountryId());
			 
			CountryMaster beniCountryMaster = new CountryMaster();
			beniCountryMaster.setCountryId(getCountryId());
			beneservice.setBeneCountryId(beniCountryMaster);
			beneservice.setCreateBy(getCreateBy());
			beneservice.setCreateDate(getCreateDate());
			beneservice.setModifiedBy(getModifiedBy());
			beneservice.setModifiedDate(getModifiedDate());
			if (beneservice.getBeneCountrySeerviceId() != null) {
				beneservice.setApprovedBy(sessionmanage.getUserName());
				beneservice.setApprovedDate(new Date());
				beneservice.setIsActive(Constants.Yes);
			}
			System.out.println(" save before ");*/
		try{
			String approvalMsg=ibeneCountryServices.approveRecord(getBeneCountryPK(), sessionmanage.getUserName());
			if(approvalMsg.equalsIgnoreCase("Success")){
			RequestContext.getCurrentInstance().execute("approve.show();");
			}else{
				RequestContext.getCurrentInstance().execute("alreadyapprov.show();");	
			}
		}catch(NullPointerException ne){
		  logger.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::approveSelectedBeneCountryService"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
	  }catch(Exception exception){
	    logger.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
	    setErrorMessage(exception.getMessage()); 
	    RequestContext.getCurrentInstance().execute("error.show();");
	    return;       
	  }
	
	
	}

	public void clickOnOk() {
		setCountryId(null);
		setCountryName(null);
		setBeneCountryServicesId(null);
		setCurrencyId(null);
		setCountrycurr(null);
		setServiceId(null);
		setServiceName(null);
		setRemitancyId(null);
		setRemittName(null);
		setDeliveryId(null);
		setDeliveryName(null);
		setIsActive(null);
		setCreateBy(null);
		setCreateDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setEditFlag(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/servicesforbenificiary.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void clickOnOkButton()throws Exception{
		 approvalBeanList();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/servicesforbenificiaryApproval.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void notAuthorized() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/servicesforbenificiaryApproval.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public void checkStatus(BeneCountryServiceData beneCountryServiceData) throws Exception {
		
		if (beneCountryServiceData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			beneCountryServiceData.setRemarksCheck(true);
			
			System.out.println("Approved By" +beneCountryServiceData.getApprovedBy());
			System.out.println("Approved Date" +beneCountryServiceData.getApprovedDate());
			
			setApprovedBy(beneCountryServiceData.getApprovedBy());
			setApprovedDate(beneCountryServiceData.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarksMsg.show();");
			return;
		} else {
			removeServiceBene(beneCountryServiceData);
		}
	}

	public void clickOK() throws Exception {
		
		System.out.println(getRemarks());
		
		if(getRemarks()!=null && !getRemarks().equals("")) {
		for (BeneCountryServiceData beneCountryServiceData : lstbeneCountryServiceData) {
			if (beneCountryServiceData.getRemarksCheck() != null) {
				if (beneCountryServiceData.getRemarksCheck().equals(true)) {
					beneCountryServiceData.setRemarks(getRemarks());
					removeServiceBene(beneCountryServiceData);
					setRemarks(null);
				}
			}
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/servicesforbenificiary.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		else
		{
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		}
	}
	public Boolean getRenderEditButton() {
		return renderEditButton;
	}

	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}
	
	private String setreverActiveStatus(String status) {

		String strStatus = null;
		
		if (status==null) {
			
			strStatus = Constants.REMOVE;
		}

		else if (status.equalsIgnoreCase(Constants.U)) {
			
			strStatus = Constants.DELETE;
		}
		else if (status.equalsIgnoreCase(Constants.D)) {

			strStatus = Constants.ACTIVATE;
		}
		else if (status.equalsIgnoreCase(Constants.Yes)) {
			strStatus = Constants.DEACTIVATE;
		}
		return strStatus;

	}
	
	public void clickClear(){
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/servicesforbenificiary.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void confirmPermanentDelete(){
		if(lstbeneCountryServiceData.size()>0){
		for (BeneCountryServiceData beneCountryServiceData : lstbeneCountryServiceData) {
			if(beneCountryServiceData.getActivateRecordCheck()!=null){
				if(beneCountryServiceData.getPermanetDeleteCheck().equals(true)){
					hardDelete(beneCountryServiceData);
					lstbeneCountryServiceData.remove(beneCountryServiceData);	
				}
			}
		}
		}
		}

public void hardDelete(BeneCountryServiceData data){
	ibeneCountryServices.delete(data.getBnCntryservicepk());
}
private String errorMessage;

public String getErrorMessage() {
	  return errorMessage;
}

public void setErrorMessage(String errorMessage) {
	  this.errorMessage = errorMessage;
}


}
