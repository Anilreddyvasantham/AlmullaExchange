package com.amg.exchange.treasury.bean;

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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.ServiceGroupMaster;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.CollectionUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("serviceCodeMasterBean")
@Scope("session")
public class ServiceCodeMasterBean<T> implements Serializable {

	private static final long serialVersionUID = 8347040472158186815L;
	private static final Logger log=Logger.getLogger(ServiceCodeMasterBean.class);
	private String serviceCode;
	private BigDecimal serviceGroup;
	private String serviceDescription = null;
	private String localServiceDescription = null;
	private Boolean booRenderSaveExit = false;
	private Boolean booRenderDataTable = false;
	private Boolean booServiceCodeCheck = false;
	private Boolean booServiceCodeCheckForDataTable = false;
	private Boolean booServiceCodeCheckForDuplicate = false;
	private BigDecimal serviceMasterDecsId;
	private BigDecimal serviceId;
	private Boolean disableEdit;
	private Boolean disableClear;
	private Boolean booCheckUpdate;
	private Boolean booCheckDelete=false;
	
	private ServiceCodeMasterDataTable serviceCodeMasterDTObj=null;

 
	 
	public ServiceCodeMasterDataTable getServiceCodeMasterDTObj() {
		return serviceCodeMasterDTObj;
	}

	public void setServiceCodeMasterDTObj(
			ServiceCodeMasterDataTable serviceCodeMasterDTObj) {
		this.serviceCodeMasterDTObj = serviceCodeMasterDTObj;
	}

	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}

	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}

	private List<ServiceCodeMasterDataTable> serviceCodeMasterDTList = new CopyOnWriteArrayList<ServiceCodeMasterDataTable>();
	private List<ServiceCodeMasterDataTable> serviceCodeMasterNewList = new CopyOnWriteArrayList<ServiceCodeMasterDataTable>();
	private SessionStateManage session = new SessionStateManage();
	List<LanguageType> langList = new ArrayList<LanguageType>();
	List<ServiceGroupMasterDesc> lstofServiceGroup = new ArrayList<ServiceGroupMasterDesc>();
	 
	ServiceMaster serviceMaster = new ServiceMaster();
	
	
	@Autowired
	ServiceCodeMasterService serviceCodeMasterService;

	@Autowired
	IGeneralService<T> generalService;

	public BigDecimal getServiceMasterDecsId() {
		return serviceMasterDecsId;
	}

	public void setServiceMasterDecsId(BigDecimal serviceMasterDecsId) {
		this.serviceMasterDecsId = serviceMasterDecsId;
	}

	public BigDecimal getServiceId() {
		return serviceId;
	}

	
	
	public BigDecimal getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(BigDecimal serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	public Boolean getBooServiceCodeCheckForDuplicate() {
		return booServiceCodeCheckForDuplicate;
	}

	public void setBooServiceCodeCheckForDuplicate(
			Boolean booServiceCodeCheckForDuplicate) {
		this.booServiceCodeCheckForDuplicate = booServiceCodeCheckForDuplicate;
	}

	
	public List<ServiceGroupMasterDesc> getLstofServiceGroup() {
		return lstofServiceGroup;
	}

	public void setLstofServiceGroup(List<ServiceGroupMasterDesc> lstofServiceGroup) {
		this.lstofServiceGroup = lstofServiceGroup;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public String getLocalServiceDescription() {
		return localServiceDescription;
	}

	public void setLocalServiceDescription(String localServiceDescription) {
		this.localServiceDescription = localServiceDescription;
	}

	public List<ServiceCodeMasterDataTable> getServiceCodeMasterDTList() {
		return serviceCodeMasterDTList;
	}

	public void setServiceCodeMasterDTList(
			List<ServiceCodeMasterDataTable> serviceCodeMasterDTList) {
		this.serviceCodeMasterDTList = serviceCodeMasterDTList;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void serviceCodeMasPageNavigation() {
		clearAllFields();
		setDisableClear(false);
		setBooRenderDataTable(false);
		setBooRenderSaveExit(false);
		setBooRenderApprove(false);
		setBooAdd(true);
		setApprove(false);
		setServiceGroup(null);
		setDisableEdit(false);
		setDisableClear(false);
		setDisableSubmitButton(false);
		serviceCodeMasterDTList.clear();
		serviceCodeListDt.clear();
		serviceCodeMasterNewList.clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "ServiceCodeMaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/ServiceCodeMaster.xhtml");
		} catch (Exception exception) {
			  log.info("Page Navigation:::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage());
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;  
		}
	}


	public Boolean getBooRenderSaveExit() {
		return booRenderSaveExit;
	}

	public void setBooRenderSaveExit(Boolean booRenderSaveExit) {
		this.booRenderSaveExit = booRenderSaveExit;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}
	
	public List<ServiceGroupMasterDesc> getServiceGroupList(){
		  try{
		  lstofServiceGroupMap.clear();
		lstofServiceGroup = serviceCodeMasterService.getServiceGroupDescription(session.getLanguageId());
		for (ServiceGroupMasterDesc serviceGroupMasterDesc : lstofServiceGroup) {
		    lstofServiceGroupMap.put(serviceGroupMasterDesc.getServiceGroupMasterId().getServiceGroupId(), serviceGroupMasterDesc.getServiceGroupDesc());
	  }
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage());
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return null;
		  }
	  return lstofServiceGroup;
		
	}

	public void clearAllFields() {
		setServiceCode(null);
		//setServiceGroup(null);
		setServiceDescLocalPk( null);
		setServiceMasterPk(null);
		setServiceDescPk( null);
		setServiceDescription(null);
		setLocalServiceDescription(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setApproveDate(null);
		setApprovedBy(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setRemarks("");
		setServiceCodeMasterDTObj(null);
	}
	
	List<ServiceCodeMasterDataTable> serviceCodeListDt=new CopyOnWriteArrayList<>();

	public List<ServiceCodeMasterDataTable> getServiceCodeListDt() {
	return serviceCodeListDt;
}

	public void setServiceCodeListDt(
		List<ServiceCodeMasterDataTable> serviceCodeListDt) {
	this.serviceCodeListDt = serviceCodeListDt;
}

	
	 private BigDecimal serviceMasterPk;
	 private BigDecimal serviceDescPk;
	 private BigDecimal serviceDescLocalPk;

	public void addRecordsToDataTable() {
		ServiceCodeMasterDataTable serviceCodeMaster = getServiceCodeMasterDTObj();
		if(serviceCodeMaster!=null){
		if(!serviceCodeMaster.getServiceCode().equalsIgnoreCase(getServiceCode()) || !serviceCodeMaster.getServiceDescription().equalsIgnoreCase(getServiceDescription())
		||	!serviceCodeMaster.getLocalServiceDescription().equalsIgnoreCase(getLocalServiceDescription())){
			addRecords(serviceCodeMaster);
		}else{
			serviceCodeMasterDTList.add(serviceCodeMaster);
			setServiceCodeMasterDTObj(null);
			}
		}
		if(getServiceMasterPk()==null){
			addNewRecord(serviceCodeMaster);
			
		}
		clearAllFields();
		setBooRenderDataTable(true);
		setBooRenderSaveExit(true);
		setDisableEdit(false);
		setDisableClear(false);
		setDisableSubmitButton(false);
		setBooCheckUpdate(false);
	}

	public void addNewRecord(ServiceCodeMasterDataTable serviceCodeMaster){
		
		  
		  ServiceCodeMasterDataTable serviceCodeMasterDTObj = new ServiceCodeMasterDataTable();
			serviceCodeMasterDTObj.setServiceCode(getServiceCode());
			serviceCodeMasterDTObj.setServiceGroup(getServiceGroup());
			serviceCodeMasterDTObj.setServiceGroupName(lstofServiceGroupMap.get(getServiceGroup()));
			serviceCodeMasterDTObj.setServiceDescription(getServiceDescription());
			serviceCodeMasterDTObj.setLocalServiceDescription(getLocalServiceDescription());
			serviceCodeMasterDTObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
			serviceCodeMasterDTObj.setRenderEditButton(true);
			serviceCodeMasterDTObj.setIsActive(Constants.U);
			serviceCodeMasterDTObj.setCheckSave(true);
			serviceCodeMasterDTObj.setCreatedBy(session.getUserName());
			serviceCodeMasterDTObj.setCreatedDate(new Date());
			serviceCodeMasterDTObj.setBooCheckUpdate(true);
				
			serviceCodeMasterDTList.add(serviceCodeMasterDTObj);
			
			serviceCodeMasterNewList.add(serviceCodeMasterDTObj);
	}
	
	public void addRecords(ServiceCodeMasterDataTable serviceCodeMaster){
		
		System.out.println("getServiceGroup() ========== >"+getServiceGroup());

			  	ServiceCodeMasterDataTable serviceCodeMasterDTObj = new ServiceCodeMasterDataTable();
				serviceCodeMasterDTObj.setServiceMasterPk(serviceCodeMaster.getServiceMasterPk());
				serviceCodeMasterDTObj.setServiceDescPk(serviceCodeMaster.getServiceDescPk());
				serviceCodeMasterDTObj.setServiceDescLocalPk(serviceCodeMaster.getServiceDescLocalPk());
				serviceCodeMasterDTObj.setServiceCode(getServiceCode());
				serviceCodeMasterDTObj.setServiceGroup(getServiceGroup());
				serviceCodeMasterDTObj.setServiceGroupName(lstofServiceGroupMap.get(getServiceGroup()));
				serviceCodeMasterDTObj.setServiceDescription(getServiceDescription());
				serviceCodeMasterDTObj.setLocalServiceDescription(getLocalServiceDescription());
				serviceCodeMasterDTObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				serviceCodeMasterDTObj.setRenderEditButton(serviceCodeMaster.getRenderEditButton());
				serviceCodeMasterDTObj.setCreatedBy(serviceCodeMaster.getCreatedBy());
				serviceCodeMasterDTObj.setCreatedDate(serviceCodeMaster.getCreatedDate());
				serviceCodeMasterDTObj.setBooCheckUpdate(getBooCheckUpdate());
				serviceCodeMasterDTObj.setModfiedBy(session.getUserName());
				serviceCodeMasterDTObj.setModifiedDate(new Date());
				serviceCodeMasterDTObj.setActivateBy(null);
				serviceCodeMasterDTObj.setActivateDate(null);
				serviceCodeMasterDTObj.setRemarks(null);
				serviceCodeMasterDTObj.setIsActive(Constants.U);
				
				serviceCodeMasterDTList.add(serviceCodeMasterDTObj);
				 
		  	if(serviceCodeMasterDTList.size()>0){
				 if(serviceCodeListDt.size()>0){
					 for(ServiceCodeMasterDataTable serviceDt1:serviceCodeMasterDTList){
					 for(ServiceCodeMasterDataTable serviceDt2:serviceCodeListDt){
					 if( serviceDt1.getServiceCode().toString().equals(serviceDt2.getServiceCode().toString())){
						 serviceCodeListDt.remove(serviceDt2 );
					 		}
					 	}
					 }
				 } 
				 serviceCodeMasterDTList.addAll(serviceCodeListDt ); 
			 }else{ 
					serviceCodeMasterDTList.addAll(serviceCodeListDt ); 
				}
	}
	
	List<ServiceMaster> matchServiceMasters = new ArrayList<ServiceMaster>();

 

	public List<String> populate(String query) throws Exception{
		  if(getServiceGroup() == null){
			    RequestContext.getCurrentInstance().execute("selectSerGroup.show();");
			    return null;
		  }
		//serviceCodeMasterService.getServiceCodeList(query);
		if (getServiceGroup() != null && query.length() > 0) {
			  return serviceCodeMasterService.getServiceCodeList(query,getServiceGroup());

		} else {
			return null;
		}
		  }
	  

	// To UPDATE
	public void saveOrupdate() {

		if (serviceCodeMasterDTList.isEmpty()) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		}else {
			try{	
						for (ServiceCodeMasterDataTable serviceCodeMasterDTObj : serviceCodeMasterDTList) {
							 
							serviceMaster.setServiceId(serviceCodeMasterDTObj.getServiceMasterPk());
							serviceMaster.setServiceCode(serviceCodeMasterDTObj.getServiceCode());
							ServiceGroupMaster serviceGroupMaster = new ServiceGroupMaster();
							serviceGroupMaster.setServiceGroupId(serviceCodeMasterDTObj.getServiceGroup());
							serviceMaster.setServiceGroupId(serviceGroupMaster);
							serviceMaster.setIsActive(serviceCodeMasterDTObj.getIsActive());
							serviceMaster.setRemarks(serviceCodeMasterDTObj.getRemarks());
							serviceMaster.setCreatedBy(serviceCodeMasterDTObj.getCreatedBy());
							serviceMaster.setCreatedDate(serviceCodeMasterDTObj.getCreatedDate());
							serviceMaster.setModifiedBy(serviceCodeMasterDTObj.getModfiedBy());
							serviceMaster.setModifiedDate(serviceCodeMasterDTObj.getModifiedDate());
							serviceMaster.setApprovedBy(serviceCodeMasterDTObj.getActivateBy());
							serviceMaster.setApprovedDate(serviceCodeMasterDTObj.getActivateDate());
							if(serviceCodeMasterDTObj.getBooCheckUpdate()){
							serviceCodeMasterService.saveRecord(serviceMaster);
							}
									ServiceMasterDesc serviceMasterDesc = new ServiceMasterDesc();
									LanguageType languageType = new LanguageType();
									languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID) );
									serviceMasterDesc.setLanguageType(languageType);
									serviceMasterDesc.setServiceMasterDecsId( serviceCodeMasterDTObj.getServiceDescPk());
									serviceMasterDesc.setLocalServiceDescription(serviceCodeMasterDTObj.getServiceDescription());
									serviceMasterDesc.setServiceMaster(serviceMaster);
									if(serviceCodeMasterDTObj.getBooCheckUpdate()){
									serviceCodeMasterService.save(serviceMasterDesc);
									}
									ServiceMasterDesc serviceMasterDesc2 = new ServiceMasterDesc();
									LanguageType languageType2 = new LanguageType();
									languageType2.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
									serviceMasterDesc2.setLanguageType(languageType2);
									serviceMasterDesc2.setServiceMasterDecsId( serviceCodeMasterDTObj.getServiceDescLocalPk());
									serviceMasterDesc2.setLocalServiceDescription(serviceCodeMasterDTObj.getLocalServiceDescription());
									serviceMasterDesc2.setServiceMaster(serviceMaster);
									if(serviceCodeMasterDTObj.getBooCheckUpdate()){
									serviceCodeMasterService.save(serviceMasterDesc2);
									}
						}
				}catch(NullPointerException nullPointerException){
					  log.info("nullPointerException.getMessage():::::::::::::::::::::::"+nullPointerException.getMessage());
					    setErrorMessage("Method Name::saveOrupdate");
					    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					    return;    
				}catch(Exception exception){ 
					  log.info("exception.getMessage():::::::::::::::::::::::"+exception.getMessage());
					    setErrorMessage(exception.getMessage());
					    RequestContext.getCurrentInstance().execute("error.show();");
					    return;  
				}
		 
			RequestContext.getCurrentInstance().execute("complete.show();");
			serviceCodeMasterDTList.clear(); 
			serviceCodeMasterNewList.clear();
			setServiceGroup(null);
		}

		
	}
	
	
	//nag delete
	public void delete(ServiceCodeMasterDataTable serviceCodeMasterDTObj) throws IOException {
			try{
				serviceMaster.setServiceId(serviceCodeMasterDTObj.getServiceMasterPk());
				serviceMaster.setServiceCode(serviceCodeMasterDTObj.getServiceCode());
				
				ServiceGroupMaster serviceGroupMaster = new ServiceGroupMaster();
				serviceGroupMaster.setServiceGroupId(serviceCodeMasterDTObj.getServiceGroup());
				serviceMaster.setServiceGroupId(serviceGroupMaster);
				
			    serviceMaster.setRemarks(serviceCodeMasterDTObj.getRemarks());
				serviceMaster.setModifiedBy(session.getUserName());
				serviceMaster.setModifiedDate(new Date());
				serviceMaster.setIsActive(Constants.D);
				serviceMaster.setCreatedBy(serviceCodeMasterDTObj.getCreatedBy());
				serviceMaster.setCreatedDate(serviceCodeMasterDTObj.getCreatedDate());
				serviceMaster.setApprovedBy(null);
				serviceMaster.setApprovedDate(null);
				
				serviceCodeMasterService.saveRecord(serviceMaster);

				ServiceMasterDesc serviceMasterDesc = new ServiceMasterDesc();
				 
						LanguageType languageType = new LanguageType();
						languageType.setLanguageId(new BigDecimal(1));
						serviceMasterDesc.setLanguageType(languageType);
						serviceMasterDesc.setServiceMasterDecsId( serviceCodeMasterDTObj.getServiceDescPk());
						serviceMasterDesc.setLocalServiceDescription(serviceCodeMasterDTObj.getServiceDescription());
						serviceMasterDesc.setServiceMaster(serviceMaster);

						serviceCodeMasterService.save(serviceMasterDesc);
						ServiceMasterDesc serviceMasterDesc2 = new ServiceMasterDesc();
						LanguageType languageType2 = new LanguageType();
						languageType2.setLanguageId(new BigDecimal(2));
						serviceMasterDesc2.setLanguageType(languageType2);
						serviceMasterDesc2.setServiceMasterDecsId( serviceCodeMasterDTObj.getServiceDescLocalPk());
						serviceMasterDesc2.setLocalServiceDescription(serviceCodeMasterDTObj.getLocalServiceDescription());
						serviceMasterDesc2.setServiceMaster(serviceMaster);

						serviceCodeMasterService.save(serviceMasterDesc2);

					 }catch(Exception exception){
						   log.info("delete():::::::::::::::::::::::"+exception.getMessage());
						    setErrorMessage(exception.getMessage());
						    RequestContext.getCurrentInstance().execute("error.show();");
						    return;  
					 }
		 }
		  
	//private Boolean isDelete=true;

public void getAllDetailsToList() {
	
	//System.out.println("serviceCodeMasterDTObj.getServiceGroup() ==== > "+serviceCodeMasterDTList);
	try{
			if(serviceCodeMasterDTList.size()!=0){
			for (ServiceCodeMasterDataTable  serviceDT : serviceCodeMasterDTList) {
				if(serviceDT.getServiceCode().toString().equals(getServiceCode())){
					
					setServiceDescPk( null);
				   	setServiceMasterPk( null);
				   	setServiceDescLocalPk( null);
				    setServiceDescription( null);
					setServiceCode( null);
					setDisableSubmitButton(false);
					//setServiceGroup(null);
					setLocalServiceDescription(null);
					  RequestContext.getCurrentInstance().execute("succ1.show();");
					  setServiceCodeMasterDTObj(null);
					  return;
				} 
				
					}
				}
				addRecordsToDataTable();
	}catch(Exception exception){
		  log.info("getAllDetailsToList():::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage());
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;  
	}
	}

	public void exitDialog() {
		 if(serviceCodeMasterDTList.size()>0){
		setBooRenderDataTable(true);
		setBooRenderSaveExit(true);
		 setServiceCode( null);
		 setServiceGroup(null);
		 setLocalServiceDescription( null);
		 setServiceDescription( null);
		 }else{
			 setBooRenderDataTable(false);
			 setBooRenderSaveExit(false);
			 setServiceCode( null);
			 setServiceGroup(null);
			 setLocalServiceDescription( null);
			 setServiceDescription( null);
		 }
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/ServiceCodeMaster.xhtml");
		} catch (IOException exception) {
			  log.info("exitDialog():::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage());
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;  
		}
	}
	 	public void updateRecord() {
			 
			if(serviceCodeMasterDTList.size()>0){
			setBooRenderDataTable(true);
			setBooRenderSaveExit(true);
	 	
			}else{  setBooRenderDataTable(false);
			setBooRenderSaveExit(false);
			}
			
	 	
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../treasury/ServiceCodeMaster.xhtml");
			} catch (IOException exception) {
				  log.info("updateRecord():::::::::::::::::::::::"+exception.getMessage());
				    setErrorMessage(exception.getMessage());
				    RequestContext.getCurrentInstance().execute("error.show();");
				    return; 
			}


		
	}

	public void clickOnOKSaveExit() {
		serviceCodeMasterDTList.clear();
		setBooRenderDataTable(false);
		clearAllFields();
		setBooRenderSaveExit(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/ServiceCodeMaster.xhtml");
		} catch (IOException exception) {
			  log.info("clickOnOKSaveExit():::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage());
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return; 
		}
	}

	public void exit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		}
	}

	public String getServiceCode() {
		return serviceCode;

	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	List<ServiceMasterDesc> listservicedDesc = new ArrayList<>();

	private boolean updateExist=true;
	
	public void checkserviceCode() {/*
		List<ServiceMaster> serviceMasterList=null;
		ServiceCodeMasterDataTable serviceCodeMaster1=new ServiceCodeMasterDataTable();
		
		serviceMasterList = serviceCodeMasterService.getServiceCheck(getServiceCode());
	
		if (serviceMasterList.size() > 0) {
			//RequestContext.getCurrentInstance().execute("succ.show();");
			if(serviceMasterList.get(0).getIsActive().equalsIgnoreCase(Constants.Yes)){
				setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				setRenderEditButton(true);
				setCheckSave(false);
		 }else if(serviceMasterList.get(0).getIsActive().equalsIgnoreCase(Constants.D)){
			 	setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
			 	setRenderEditButton(true);
			 	setCheckSave(false);
		 }else if(serviceMasterList.get(0).getIsActive().equalsIgnoreCase(Constants.U)&& serviceMasterList.get(0).getModifiedBy()==null && serviceMasterList.get(0).getModifiedDate()==null
					&& serviceMasterList.get(0).getApprovedBy()==null && serviceMasterList.get(0).getApprovedDate()==null 
					&& serviceMasterList.get(0).getRemarks()==null)
		 	{
			 	setDynamicLabelForActivateDeactivate(Constants.DELETE);
			 	setRenderEditButton(true);
			 	setCheckSave(false);
		 	}else{
			 	setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
			 	setRenderEditButton(true);
			 	setCheckSave(false);
		 }
			setServiceMasterPk(serviceMasterList.get(0).getServiceId());
			setServiceCode(getServiceCode()); 
			setServiceGroup(getServiceGroup());			
			setBooCheckUpdate(true);
			setIsActive(serviceMasterList.get(0).getIsActive());
			setRemarks(serviceMasterList.get(0).getRemarks());
			setCreatedBy(serviceMasterList.get(0).getCreatedBy());
			setCreatedDate(serviceMasterList.get(0).getCreatedDate());
			setModifiedBy(serviceMasterList.get(0).getModifiedBy());
			setModifiedDate(serviceMasterList.get(0).getModifiedDate());
			setApprovedBy(serviceMasterList.get(0).getApprovedBy());
			setApproveDate(serviceMasterList.get(0).getApprovedDate());
			
			listservicedDesc=serviceCodeMasterService.LocalServiceDescription(serviceMasterList.get(0).getServiceId());
			 
			for (ServiceMasterDesc servicemasterdesc : listservicedDesc) {
				 
				if (servicemasterdesc.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID)) {
					setServiceDescPk( servicemasterdesc.getServiceMasterDecsId());
					setServiceDescription(servicemasterdesc.getLocalServiceDescription());
					
				} else {
					setLocalServiceDescription(servicemasterdesc.getLocalServiceDescription());
					setServiceDescLocalPk( servicemasterdesc.getServiceMasterDecsId());
					
				}
			//	RequestContext.getCurrentInstance().execute("succ.show();");
				 
			}
			
			// for setting data edit check
			serviceCodeMaster1.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
			serviceCodeMaster1.setRenderEditButton(getRenderEditButton());
			serviceCodeMaster1.setCheckSave(getCheckSave());
			serviceCodeMaster1.setServiceMasterPk(getServiceMasterPk());
			serviceCodeMaster1.setServiceCode(getServiceCode());
			serviceCodeMaster1.setServiceGroup(getServiceGroup());
			serviceCodeMaster1.setIsActive(getIsActive());
			serviceCodeMaster1.setRemarks(getRemarks());
			serviceCodeMaster1.setCreatedBy(getCreatedBy());
			serviceCodeMaster1.setCreatedDate(getCreatedDate());
			serviceCodeMaster1.setModfiedBy(getModifiedBy());
			serviceCodeMaster1.setModifiedDate(getModifiedDate());
			serviceCodeMaster1.setActivateBy(getApprovedBy());
			serviceCodeMaster1.setBooCheckUpdate(getBooCheckUpdate());
			serviceCodeMaster1.setActivateDate(getApproveDate());
			serviceCodeMaster1.setServiceDescPk(getServiceDescPk());
			serviceCodeMaster1.setServiceDescLocalPk(getServiceDescLocalPk());
			serviceCodeMaster1.setServiceDescription(getServiceDescription());
			serviceCodeMaster1.setLocalServiceDescription(getLocalServiceDescription());
			setServiceCodeMasterDTObj(serviceCodeMaster1);
		}

		if (serviceCodeMasterDTList.size() > 0) {
			for (ServiceCodeMasterDataTable serciceCodeDT : serviceCodeMasterDTList) {
				if (serciceCodeDT.getServiceCode().equals(getServiceCode())) {

					setBooRenderDataTable(true);
					setBooServiceCodeCheckForDataTable(true);
					setBooServiceCodeCheckForDuplicate(false);
					setBooServiceCodeCheck(false);
					serviceMasterList.clear();
					setUpdateExist(false);
					
				} else {
						setBooServiceCodeCheckForDataTable(false);
				}
			}

		}

	*/
		
		try{
		 List<ServiceMaster> serviceMasterList = serviceCodeMasterService.getServiceCheck(getServiceCode());
		 if(serviceMasterList.size()>0){
			 setServiceCode(null);
			RequestContext.getCurrentInstance().execute("recordExist.show();"); 
		 }
		}catch(Exception exception){
			  log.info("checkserviceCode():::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage());
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return; 
		}
	
	}
	

	private String approvedBy;
	private Date approveDate;
	
	private String modifiedBy;
	private Date modifiedDate;
	

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

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public boolean isUpdateExist() {
		return updateExist;
	}

	public void setUpdateExist(boolean updateExist) {
		this.updateExist = updateExist;
	}

	public Boolean getbooServiceCodeCheck() {
		return booServiceCodeCheck;
	}

	public void setBooServiceCodeCheck(Boolean booServiceCodeCheck) {
		this.booServiceCodeCheck = booServiceCodeCheck;
	}

	public Boolean getBooServiceCodeCheckForDataTable() {
		return booServiceCodeCheckForDataTable;
	}

	public void setBooServiceCodeCheckForDataTable(
			Boolean booServiceCodeCheckForDataTable) {
		this.booServiceCodeCheckForDataTable = booServiceCodeCheckForDataTable;
	}

	public void clickOnOKSave() {
		serviceCodeMasterDTList.clear();
		clear();
		setBooRenderDataTable(false);
		setBooRenderSaveExit(false);
		setBooServiceCodeCheckForDataTable(false);
		setBooServiceCodeCheck(false);

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/ServiceCodeMaster.xhtml");
		} catch (IOException exception) {
			  log.info("clickOnOKSave():::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage());
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return; 
		}
	}

	public void editRecord(ServiceCodeMasterDataTable serviceCodeMasterDTObj) {
		try{
	System.out.println("serviceCodeMasterDTObj.getServiceGroup() ==== > "+serviceCodeMasterDTObj.getServiceGroup());
		
		setServiceMasterPk( serviceCodeMasterDTObj.getServiceMasterPk());
		setServiceDescPk(serviceCodeMasterDTObj.getServiceDescPk());
		setServiceDescLocalPk(serviceCodeMasterDTObj.getServiceDescLocalPk());
		setServiceCode(serviceCodeMasterDTObj.getServiceCode());
		setServiceGroup(serviceCodeMasterDTObj.getServiceGroup());
		setServiceDescription(serviceCodeMasterDTObj.getServiceDescription());
		setLocalServiceDescription(serviceCodeMasterDTObj.getLocalServiceDescription());
		setRemarks(serviceCodeMasterDTObj.getRemarks());
		setRenderEditButton(serviceCodeMasterDTObj.getRenderEditButton());
		setIsActive(serviceCodeMasterDTObj.getIsActive());
		setCheckSave(serviceCodeMasterDTObj.getCheckSave());
		setCreatedBy(serviceCodeMasterDTObj.getCreatedBy());
		setCreatedDate(serviceCodeMasterDTObj.getCreatedDate());
		setModifiedBy(serviceCodeMasterDTObj.getModfiedBy());
		setModifiedDate(serviceCodeMasterDTObj.getModifiedDate());
		setApprovedBy(serviceCodeMasterDTObj.getActivateBy());
		setApproveDate(serviceCodeMasterDTObj.getActivateDate());
		setDisableEdit(true);
		setDisableClear(true);
		setBooCheckUpdate(true);
		setServiceCodeMasterDTObj(null);
		if( serviceCodeMasterDTObj.getServiceMasterPk()!=null){
		setServiceCodeMasterDTObj(serviceCodeMasterDTObj);
		}else{
		setServiceCodeMasterDTObj(null);
		}
	
		serviceCodeMasterDTList.remove(serviceCodeMasterDTObj);
		serviceCodeMasterNewList.remove(serviceCodeMasterDTObj);
		setDisableSubmitButton(true);
		
		
		if(serviceCodeMasterDTList.size()<=0){
			setBooRenderSaveExit(false);
			setBooRenderDataTable(false);
		}
		}catch(Exception exception){
			  log.info("edit():::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage());
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return; 
		}
	}

	
	public BigDecimal getServiceMasterPk() {
		return serviceMasterPk;
	}

	public void setServiceMasterPk(BigDecimal serviceMasterPk) {
		this.serviceMasterPk = serviceMasterPk;
	}

	public BigDecimal getServiceDescPk() {
		return serviceDescPk;
	}

	public void setServiceDescPk(BigDecimal serviceDescPk) {
		this.serviceDescPk = serviceDescPk;
	}

	public BigDecimal getServiceDescLocalPk() {
		return serviceDescLocalPk;
	}

	public void setServiceDescLocalPk(BigDecimal serviceDescLocalPk) {
		this.serviceDescLocalPk = serviceDescLocalPk;
	}

	 
	
public void clear()	 {
		setServiceMasterPk( null);
		setServiceDescPk( null);
		setServiceDescLocalPk(null);
		setLocalServiceDescription( null);
		setServiceCode( null);
		//setServiceGroup(null);
		setServiceDescription( null);
		setModifiedBy(null);
		setModifiedDate(null);
		/*if(serviceCodeMasterDTList.size()>0){
			setDisableSubmitButton(false);
		}*/
	}

public void clearAllField(){
	  setServiceMasterPk( null);
		setServiceDescPk( null);
		setServiceDescLocalPk(null);
		setLocalServiceDescription( null);
		setServiceCode( null);
		setServiceGroup(null);
		setServiceDescription( null);
		setModifiedBy(null);
		setModifiedDate(null);  
}

public void clearButton(){
	setServiceMasterPk( null);
	setServiceDescPk( null);
	setServiceDescLocalPk(null);
//	setLocalServiceDescription( null);
//	setServiceCode( null);
	setServiceGroup(null);
//	setServiceDescription( null);
	setModifiedBy(null);
	setModifiedDate(null);
	if(serviceCodeMasterDTList.size()>0){
		setDisableSubmitButton(false);
	}
}
public void getAllDataTableRecords(){
	  try{
	serviceCodeMasterDTList.clear();
	if(getServiceGroup() != null){
		  List<ServiceMaster> listServiceMasters=serviceCodeMasterService.toFetchServiceRecordsServiceMaster(getServiceGroup());
		  if(listServiceMasters.size() !=0){
			    for (ServiceMaster serviceMaster : listServiceMasters) {
						  ServiceCodeMasterDataTable serviceDT = new ServiceCodeMasterDataTable();
						  serviceDT.setServiceCode(serviceMaster.getServiceCode());
						  serviceDT.setServiceGroup(serviceMaster.getServiceGroupId().getServiceGroupId());
						  serviceDT.setServiceGroupName(lstofServiceGroupMap.get(serviceMaster.getServiceGroupId().getServiceGroupId()));
						  serviceDT.setBooCheckUpdate(false);
						  serviceDT.setDisableEdit(false);
						  setDisableClear(false);
						  if(serviceMaster.getIsActive().equalsIgnoreCase(Constants.Yes)){
								serviceDT.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
									serviceDT.setRenderEditButton(true);
							 }else if(serviceMaster.getIsActive().equalsIgnoreCase(Constants.D)){
								 	serviceDT.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
								 	serviceDT.setRenderEditButton(true);
								 	serviceDT.setRemarks(serviceMaster.getRemarks());
							 }else if(serviceMaster.getIsActive().equalsIgnoreCase(Constants.U)&& serviceMaster.getModifiedBy()==null && serviceMaster.getModifiedDate()==null
									&& serviceMaster.getApprovedBy()==null && serviceMaster.getApprovedDate()==null 
									&& serviceMaster.getRemarks()==null)  
							 	{
								 	serviceDT.setDynamicLabelForActivateDeactivate(Constants.DELETE);
								 	serviceDT.setRenderEditButton(true);
							 }else{
								 serviceDT.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
								 serviceDT.setRenderEditButton(true);
							 }
						serviceDT.setRemarks(serviceMaster.getRemarks());
						serviceDT.setIsActive(serviceMaster.getIsActive());
						serviceDT.setCreatedBy(serviceMaster.getCreatedBy());
						serviceDT.setCreatedDate(serviceMaster.getCreatedDate());
						serviceDT.setServiceMasterPk(serviceMaster.getServiceId());
						serviceDT.setActivateBy(serviceMaster.getApprovedBy());
						serviceDT.setActivateDate(serviceMaster.getApprovedDate());
						serviceDT.setModfiedBy(serviceMaster.getModifiedBy());
						serviceDT.setModifiedDate(serviceMaster.getModifiedDate());
						List<ServiceMasterDesc> lstServiceMasterDescs=serviceCodeMasterService.getAllServiceList(serviceMaster.getServiceId());
						if(lstServiceMasterDescs.size() !=0 && lstServiceMasterDescs != null){
							  for (ServiceMasterDesc serviceMasterDesc : lstServiceMasterDescs) {
								    if(serviceMasterDesc.getLanguageType().getLanguageId().intValue()==1){ 
									      serviceDT.setServiceDescPk(serviceMasterDesc.getServiceMasterDecsId());
										serviceDT.setServiceDescription(serviceMasterDesc.getLocalServiceDescription());
										serviceDT.setEnglishLanguageId(serviceMasterDesc.getLanguageType().getLanguageId());
								    }else{
									      serviceDT.setServiceDescLocalPk(serviceMasterDesc.getServiceMasterDecsId());
										serviceDT.setLocalServiceDescription(serviceMasterDesc.getLocalServiceDescription());
										serviceDT.setArabicLanguageId(serviceMasterDesc.getLanguageType().getLanguageId());
						  }
							  
						}
							  					  
		    }
						serviceCodeMasterDTList.add(serviceDT);
			      
		  }		    
		  }
		  serviceCodeMasterDTList.addAll(serviceCodeMasterNewList);
		    
	}else{
		  if(serviceCodeMasterNewList != null && serviceCodeMasterNewList.size() !=0){
			    serviceCodeMasterDTList.addAll(serviceCodeMasterNewList);
				    
		  }else{
		  RequestContext.getCurrentInstance().execute("selectSerGroup.show();");  
		  }
	}
	 setBooRenderDataTable(true);
		setBooRenderSaveExit(true); 	
	}catch(Exception exception){
		  log.info("getAllDataTableRecords():::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage());
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return; 
	}
}
	/*List<ServiceMasterDesc>  list = serviceCodeMasterService.getAllServiceList(getServiceGroup());
		if(list.size()>0){
	 
		
		     for(ServiceMasterDesc serviceDesc:list){
		
		    		ServiceCodeMasterDataTable serviceDT=new ServiceCodeMasterDataTable();
		    		serviceDT.setServiceCode(serviceDesc.getServiceMaster().getServiceCode() );
		    		
		    		
		    	
		    		serviceDT.setServiceGroup(serviceDesc.getServiceMaster().getServiceGroupId().getServiceGroupId());
		    		serviceDT.setServiceGroupName(lstofServiceGroupMap.get(serviceDesc.getServiceMaster().getServiceGroupId().getServiceGroupId()));
			
			if(serviceDesc.getServiceMaster().getIsActive().equalsIgnoreCase(Constants.Yes)){
					serviceDT.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
						serviceDT.setRenderEditButton(true);
				 }else if(serviceDesc.getServiceMaster().getIsActive().equalsIgnoreCase(Constants.D)){
					 	serviceDT.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					 	serviceDT.setRenderEditButton(true);
					 	serviceDT.setRemarks(serviceDesc.getServiceMaster().getRemarks());
				 }else if(serviceDesc.getServiceMaster().getIsActive().equalsIgnoreCase(Constants.U)&& serviceDesc.getServiceMaster().getModifiedBy()==null && serviceDesc.getServiceMaster().getModifiedDate()==null
						&& serviceDesc.getServiceMaster().getApprovedBy()==null && serviceDesc.getServiceMaster().getApprovedDate()==null 
						&& serviceDesc.getServiceMaster().getRemarks()==null)  
				 	{
					 	serviceDT.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					 	serviceDT.setRenderEditButton(true);
				 }else{
					 serviceDT.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					 serviceDT.setRenderEditButton(true);
				 }
			serviceDT.setRemarks(serviceDesc.getServiceMaster().getRemarks());
			serviceDT.setIsActive(serviceDesc.getServiceMaster().getIsActive());
			serviceDT.setCreatedBy(serviceDesc.getServiceMaster().getCreatedBy());
			serviceDT.setCreatedDate(serviceDesc.getServiceMaster().getCreatedDate());
			serviceDT.setServiceMasterPk(serviceDesc.getServiceMaster().getServiceId());
			serviceDT.setActivateBy(serviceDesc.getServiceMaster().getApprovedBy());
			serviceDT.setActivateDate(serviceDesc.getServiceMaster().getApprovedDate());
			if(serviceDesc.getServiceMaster().getServiceId()!=null){
			serviceDT.setModfiedBy(serviceDesc.getServiceMaster().getModifiedBy());
			serviceDT.setModifiedDate(serviceDesc.getServiceMaster().getModifiedDate());
			}
			serviceDT.setBooCheckUpdate(false);
			serviceDT.setDisableEdit(false);
			setDisableClear(false);
			if(serviceDesc.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID)){
				
			serviceDT.setServiceDescPk(serviceDesc.getServiceMasterDecsId());
			serviceDT.setServiceDescription(serviceDesc.getLocalServiceDescription());
			if(serviceDT.getServiceDescPk()!=null&&serviceDT.getServiceDescLocalPk()!=null){
				serviceCodeListDt.add(serviceDT);
				 
				}
			}
		//	List<ServiceMasterDesc>  list1 = serviceCodeMasterService.getAllServiceList(getServiceGroup());
				for(ServiceMasterDesc serviceDesc1:list){
					if(serviceDT.getServiceCode().toString().equals(serviceDesc1.getServiceMaster().getServiceCode()) && serviceDesc1.getLanguageType().getLanguageId().toString().equals(Constants.ARABIC_LANGUAGE_ID)){
						serviceDT.setServiceDescLocalPk(serviceDesc1.getServiceMasterDecsId());
						serviceDT.setLocalServiceDescription(serviceDesc1.getLocalServiceDescription());
						if(serviceDT.getServiceDescPk()!=null&&serviceDT.getServiceDescLocalPk()!=null){
						serviceCodeListDt.add(serviceDT);
						 
						}
					}
			}
				
				if(serviceDT.getServiceDescPk()!=null&&serviceDT.getServiceDescLocalPk()!=null){
					  serviceCodeListDt.add(serviceDT);
					 
					}
				serviceCodeMasterDTList.add(serviceDT);	
			
		}
		     
		    
		     //serviceCodeListDt.clear();
		     setBooRenderDataTable(true);
				setBooRenderSaveExit(true);
	
		}else{
			RequestContext.getCurrentInstance().execute("exist.show();");
		}*/
		 
	

List<ServiceCodeMasterDataTable>  approveList =new ArrayList<ServiceCodeMasterDataTable>();

public List<ServiceCodeMasterDataTable> getApproveList() {
	
	return approveList;
}

public void setApproveList(List<ServiceCodeMasterDataTable> approveList) {
	this.approveList = approveList;
}


public void approveServices(){
	  try{
	approveList.clear();
	List<ServiceMasterDesc> listapprove = serviceCodeMasterService.getAllServicesForApprove();
	if(listapprove.size()>0){
		 
		
	     for(ServiceMasterDesc serviceDesc:listapprove){
	
	    		ServiceCodeMasterDataTable serviceDTApprov=new ServiceCodeMasterDataTable();
		
	         serviceDTApprov.setServiceCode(serviceDesc.getServiceMaster().getServiceCode() );
	         
	         serviceDTApprov.setServiceGroup(serviceDesc.getServiceMaster().getServiceGroupId().getServiceGroupId());
	         String serviceName=serviceCodeMasterService.toFetchServiceGroupName(serviceDesc.getServiceMaster().getServiceGroupId().getServiceGroupId(),session.getLanguageId());
	         serviceDTApprov.setServiceGroupName(serviceName);
	         serviceDTApprov.setServiceMasterPk(serviceDesc.getServiceMaster().getServiceId());
	         serviceDTApprov.setCreatedBy(serviceDesc.getServiceMaster().getCreatedBy());
	         serviceDTApprov.setCreatedDate(serviceDesc.getServiceMaster().getCreatedDate());
	         serviceDTApprov.setModfiedBy(serviceDesc.getServiceMaster().getModifiedBy());
	         serviceDTApprov.setModifiedDate(serviceDesc.getServiceMaster().getModifiedDate());

		if(serviceDesc.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID.toString())){
			
			serviceDTApprov.setServiceDescPk(serviceDesc.getServiceMasterDecsId());
			serviceDTApprov.setServiceDescription(serviceDesc.getLocalServiceDescription());
		if(serviceDTApprov.getServiceDescPk()!=null&&serviceDTApprov.getServiceDescLocalPk()!=null){
			approveList.add(serviceDTApprov);
			 
			}
		}
		List<ServiceMasterDesc>  list1 = serviceCodeMasterService.getAllServicesForApprove();
			for(ServiceMasterDesc serviceDesc1:list1){
				if(serviceDTApprov.getServiceCode().toString().equals(serviceDesc1.getServiceMaster().getServiceCode())&&serviceDesc1.getLanguageType().getLanguageId().toString().equals(Constants.ARABIC_LANGUAGE_ID.toString())){
					serviceDTApprov.setServiceDescLocalPk(serviceDesc1.getServiceMasterDecsId());
					serviceDTApprov.setLocalServiceDescription(serviceDesc1.getLocalServiceDescription());
					if(serviceDTApprov.getServiceDescPk()!=null&&serviceDTApprov.getServiceDescLocalPk()!=null){
						approveList.add(serviceDTApprov);
					 
					}
				}
				
				
		}
		
			
	
	
		
	
		
		
	}

	
	
	}
	  }catch(NullPointerException nullPointerException){
		  log.info("nullPointerException.getMessage():::::::::::::::::::::::"+nullPointerException.getMessage());
		    setErrorMessage("Method Name::approveServices");
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return;    
		}
	  catch(Exception exception){
		    log.info("approveServices():::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage());
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return; 
	  }
}

private boolean approve=false;
private boolean booAdd=true;
private boolean booRenderApprove=false;
private String createdBy;
private Date createdDate;
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



public boolean isApprove() {
	return approve;
}

public void setApprove(boolean approve) {
	this.approve = approve;
}

public boolean isBooAdd() {
	return booAdd;
}

public boolean isBooRenderApprove() {
	return booRenderApprove;
}

public void setBooRenderApprove(boolean booRenderApprove) {
	this.booRenderApprove = booRenderApprove;
}

public void setBooAdd(boolean booAdd) {
	this.booAdd = booAdd;
}

public void gotoServiceApproval(ServiceCodeMasterDataTable datatable) {
	  try{
	if((datatable.getModfiedBy()==null?datatable.getCreatedBy():datatable.getModfiedBy()).equalsIgnoreCase( session.getUserName())){
		RequestContext.getCurrentInstance().execute("notApproved.show();");
	}else{
		
	setServiceCode(datatable.getServiceCode() );
	setServiceGroup(datatable.getServiceGroup());
	setServiceMasterPk( datatable.getServiceMasterPk());
	setServiceDescription(datatable.getServiceDescription() );	
	setLocalServiceDescription(datatable.getLocalServiceDescription() );
	setCreatedBy(datatable.getCreatedBy());
	setCreatedDate(datatable.getCreatedDate() );
	setApprove(true);
	setBooRenderSaveExit(false);
	setBooAdd( false);
	setBooRenderApprove(true);
	setBooRenderDataTable(false);
	//bankApprovalList.clear();
	try {
		
		FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../treasury/ServiceCodeMaster.xhtml");
	 
	} catch (Exception e) {
		  log.info("gotoServiceApproval():::::::::::::::::::::::"+e.getMessage());
		    setErrorMessage(e.getMessage());
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;
		
	}
		
	}
	  }catch(Exception exception){
		    log.info("gotoServiceApproval():::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage());
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;
	  }
}


public void populateValues() {
	approveList.clear();
	approveServices();

	try {
		loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "ServiceCodeMasterApprove.xhtml");
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../remittance/ServiceCodeMasterApprove.xhtml");
	} catch (IOException exception) {
		  log.info("populateValues():::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage());
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;
	}
}
 
public void clickOnOkButton(){
	approveServices();

	try {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../remittance/ServiceCodeMasterApprove.xhtml");
	} catch (IOException exception) {
		  log.info("populateValues():::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage());
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;
	}
}
public void approveSelectedService(){
	
	//if(!getCreatedBy().equalsIgnoreCase((session.getUserName()))){
		/*
	
	serviceMaster.setServiceId(getServiceMasterPk());
	 
	serviceMaster.setServiceCode(getServiceCode());

	serviceMaster.setServiceDescription(getServiceDescription());
	serviceMaster.setApprovedBy(session.getUserName());
	serviceMaster.setApprovedDate(new Date());

	 
	serviceMaster.setIsActive("Y");
	if(getServiceMasterPk()!=null){
	serviceMaster.setModifiedDate(new Date() );
	serviceMaster.setModifiedBy(session.getUserName() );
    }
	serviceMaster.setCreatedBy(getCreatedBy());
	serviceMaster.setCreatedDate(getCreatedDate());
	
	serviceCodeMasterService.saveRecord(serviceMaster);
	*/
	try{
	String approveMsg=serviceCodeMasterService.approveReord(getServiceMasterPk(), session.getUserName());
	if(approveMsg.equalsIgnoreCase("Success")){
	RequestContext.getCurrentInstance().execute("approve.show();");
	
	}
else{
	RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
	}
	}catch(Exception exception){
		  log.info("approveSelectedService():::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage());
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;
	}
	
}
public void clickOnOKSaveApprove(){
	setServiceCode( null);
	setServiceGroup(null);	
	setServiceDescription( null);
	setLocalServiceDescription( null);
	setCreatedBy(null);
	setCreatedDate( null);
	setServiceMasterPk(null);
	approveServices();
	try {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../remittance/ServiceCodeMasterApprove.xhtml");
	} catch (IOException exception) {
		  log.info("clickOnOKSaveApprove():::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage());
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;
	}
}

private String dynamicLabelForActivateDeactivate;

public String getDynamicLabelForActivateDeactivate() {
	return dynamicLabelForActivateDeactivate;
}

public void setDynamicLabelForActivateDeactivate(
		String dynamicLabelForActivateDeactivate) {
	this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
}

//rahamath code changes CR-05/03/2015
private Boolean disableSubmitButton=false;
private Boolean renderEditButton=false;
private String isActive;
private Boolean checkSave;

public Boolean getCheckSave() {
	return checkSave;
}

public void setCheckSave(Boolean checkSave) {
	this.checkSave = checkSave;
}

public String getIsActive() {
	return isActive;
}

public void setIsActive(String isActive) {
	this.isActive = isActive;
}

public Boolean getRenderEditButton() {
	return renderEditButton;
}

public void setRenderEditButton(Boolean renderEditButton) {
	this.renderEditButton = renderEditButton;
}

public Boolean getDisableSubmitButton() {
	return disableSubmitButton;
}

public void setDisableSubmitButton(Boolean disableSubmitButton) {
	this.disableSubmitButton = disableSubmitButton;
}

public void hideSubmitButton(){
	
	if(getLocalServiceDescription()!=null || getServiceDescription()!=null){
		setDisableSubmitButton(true);
		}else{
			setDisableSubmitButton(false);
		}
}

//rahamath code changes CR-05/03/2015


//Dailog Box code
private String remarks="";
private Date activateDate;
private String activateBy;



public Date getActivateDate() {
	return activateDate;
}

public void setActivateDate(Date activateDate) {
	this.activateDate = activateDate;
}

public String getActivateBy() {
	return activateBy;
}

public void setActivateBy(String activateBy) {
	this.activateBy = activateBy;
}

public String getRemarks() {
	return remarks;
}

public void setRemarks(String remarks) {
	this.remarks = remarks;
}

public void checkStatusType(ServiceCodeMasterDataTable serviceCodeMasterDTObj)  throws IOException {
	try{
	if(serviceCodeMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)){
		serviceCodeMasterDTObj.setRemarkCheck(true);
		setActivateDate(serviceCodeMasterDTObj.getActivateDate());
		setActivateBy(serviceCodeMasterDTObj.getActivateBy());
		RequestContext.getCurrentInstance().execute("remarks.show();");
		return;
		}else if(serviceCodeMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)){
			serviceCodeMasterDTObj.setBooCheckDelete(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			return;
		}else if(serviceCodeMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)){
			serviceCodeMasterDTObj.setActivateRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;
		}else if(serviceCodeMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
			serviceCodeMasterDTList.remove(serviceCodeMasterDTObj);
			serviceCodeMasterNewList.remove(serviceCodeMasterDTObj);
		}
	}catch(Exception exception){
		  log.info("checkStatusType():::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage());
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;
	}
}

public void remarkSelectedRecord() throws IOException{
	  try{
	for(ServiceCodeMasterDataTable serviceCodeMasterDTObj:serviceCodeMasterDTList){
		if(serviceCodeMasterDTObj.getRemarkCheck().equals(true)){
			if(!getRemarks().equals("")){
			serviceCodeMasterDTObj.setRemarks(getRemarks());
			serviceCodeMasterDTObj.setActivateBy(null);
			serviceCodeMasterDTObj.setActivateDate(null);
			delete(serviceCodeMasterDTObj);
			setRemarks("");
			getAllDataTableRecords();
			cancel();
		}else{
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
			return;
		}
	}
	}
	  }catch(Exception exception){
		    log.info("remarkSelectedRecord():::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage());
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;
	  }
}
//added koti 10/04/2015
public void cancel(){
	setRemarks("");
	try {
		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("../treasury/ServiceCodeMaster.xhtml");
	} catch (IOException exception) {
		  log.info("cancel():::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage());
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;
	}
}

public void activateRecord(){
	  try{
	if(serviceCodeMasterDTList.size()>0){
	for(ServiceCodeMasterDataTable serviceCodeMasterDTObj:serviceCodeMasterDTList){
		if(serviceCodeMasterDTObj.getActivateRecordCheck()!=null){
		if(serviceCodeMasterDTObj.getActivateRecordCheck().equals(true)){
			serviceCodeMasterService.activateRecord(serviceCodeMasterDTObj.getServiceMasterPk(), session.getUserName());
			getAllDataTableRecords();
			return;
		}
		}
	}
	}
	  }catch(Exception exception){
		    log.info("activateRecord():::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage());
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;
	  }
}

public void deleteRecordPermanently(ServiceCodeMasterDataTable serviceCodeMasterDTObj){
	serviceCodeMasterService.deleteRecordPermanently(serviceCodeMasterDTObj.getServiceMasterPk(),serviceCodeMasterDTObj.getServiceDescLocalPk(),serviceCodeMasterDTObj.getServiceDescPk());
}



public void confirmPermanentDelete(){
	  try{
	if(serviceCodeMasterDTList.size()>0){
		for (ServiceCodeMasterDataTable  serviceModeDatatable : serviceCodeMasterDTList) {
			if(serviceModeDatatable.getBooCheckDelete()!=null){
			if(serviceModeDatatable.getBooCheckDelete().equals(true)){
			deleteRecordPermanently(serviceModeDatatable);
			serviceCodeMasterDTList.remove(serviceModeDatatable);
			}
		}
	}
	}
	  }catch(Exception exception){
		    log.info("confirmPermanentDelete():::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage());
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;
	  }
 }

public Boolean getDisableEdit() {
	return disableEdit;
}

public void setDisableEdit(Boolean disableEdit) {
	this.disableEdit = disableEdit;
}

public Boolean getDisableClear() {
	return disableClear;
}

public void setDisableClear(Boolean disableClear) {
	this.disableClear = disableClear;
}

public Boolean getBooCheckUpdate() {
	return booCheckUpdate;
}

public void setBooCheckUpdate(Boolean booCheckUpdate) {
	this.booCheckUpdate = booCheckUpdate;
}

private Map<BigDecimal, String> lstofServiceGroupMap = new HashMap<BigDecimal, String>();
private String serviceGroupName;



public Map<BigDecimal, String> getLstofServiceGroupMap() {
	  return lstofServiceGroupMap;
}

public void setLstofServiceGroupMap(Map<BigDecimal, String> lstofServiceGroupMap) {
	  this.lstofServiceGroupMap = lstofServiceGroupMap;
}

public String getServiceGroupName() {
	  return serviceGroupName;
}

public void setServiceGroupName(String serviceGroupName) {
	  this.serviceGroupName = serviceGroupName;
}

		public void checkRelationcodeisNumber(FacesContext context, UIComponent component, Object value) {
			try {
			BigDecimal val = new BigDecimal(value.toString());
			@SuppressWarnings("unused")
			boolean isNUmber = CollectionUtil.isIntegerValue(val);
				} catch (Exception e) {
			FacesMessage msg = new FacesMessage(" Service Code should be number", " Service Code should be number");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
			}
		}

		private BigDecimal englishLanguageId;
		private BigDecimal arabicLanguageId;



	  public BigDecimal getEnglishLanguageId() {
	  	  return englishLanguageId;
	  }

	  public void setEnglishLanguageId(BigDecimal englishLanguageId) {
	  	  this.englishLanguageId = englishLanguageId;
	  }

	  public BigDecimal getArabicLanguageId() {
	  	  return arabicLanguageId;
	  }

	  public void setArabicLanguageId(BigDecimal arabicLanguageId) {
	  	  this.arabicLanguageId = arabicLanguageId;
	  }
		
	  private String errorMessage;

	  public String getErrorMessage() {
		    return errorMessage;
	  }

	  public void setErrorMessage(String errorMessage) {
		    this.errorMessage = errorMessage;
	  }
	
	
}
