package com.amg.exchange.registration.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.bean.RuleEngine;
import com.amg.exchange.common.model.BusinessComponent;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.IdentityTypeMaster;
import com.amg.exchange.registration.service.IIdentityTypeMasterService;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component(value = "identityTypeBean")
@Scope("session")
public class IdentityTypeMasterBean<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(IdentityTypeMasterBean.class);
	
	@Autowired
	RuleEngine<T> ruleEngine;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IIdentityTypeMasterService<T> identityTypeMasterService;
	
	SessionStateManage sessionStateManage = new SessionStateManage();
	
	private Map<BigDecimal, String> idTypeMap = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapCountryList = new HashMap<BigDecimal, String>();
	private List<IdentityTypeMasterDataTable> identityTypeMasterList = new ArrayList<IdentityTypeMasterDataTable>();
	private List<IdentityTypeMasterDataTable> identityTypeMasterNewList = new ArrayList<IdentityTypeMasterDataTable>();

	List<IdentityTypeMaster> identityTypeList = new ArrayList<IdentityTypeMaster>();
	List<IdentityTypeMasterDataTable> listForApproval = new ArrayList<IdentityTypeMasterDataTable>();
	
	private BigDecimal identityTypeIdPk = null;
	private BigDecimal countryId = null;
	private String countryName = null;
	private BigDecimal businessComponentId = null;
	private String idType = null;
	private String validity = null;
	private BigDecimal languageId = null;
	private String isActive = null;
	private String idCheck = null;
	private String createdBy = null;
	private Date createdDate = null;
	private String modifiedBy = null;
	private Date modifiedDate = null;
	private String approvedBy = null;
	private Date approvedDate = null;
	private String ocrStatus = null;
	private String ocrStatusName = null;
	private Boolean booRenderDataTable = false;
	private String dynamicLabelForActivateDeactivate;
	private Boolean renderEditButton=false;
	private String remarks = null;
	private Boolean disableSubmit = false;
	private Boolean editHide = false;
	private String approvedDateString = null;
	private Boolean editRecord = false;
	private String customerType=null;
	private String customerTypeName =null;
	
	private String errorMessage ;
	
	private List<CountryMasterDesc> countryNameList= new ArrayList<CountryMasterDesc>();
	
	public BigDecimal getIdentityTypeIdPk() {
		return identityTypeIdPk;
	}


	public void setIdentityTypeIdPk(BigDecimal identityTypeIdPk) {
		this.identityTypeIdPk = identityTypeIdPk;
	}


	public BigDecimal getCountryId() {
		return countryId;
	}


	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}


	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}


	public BigDecimal getBusinessComponentId() {
		return businessComponentId;
	}


	public void setBusinessComponentId(BigDecimal businessComponentId) {
		this.businessComponentId = businessComponentId;
	}


	public String getIdType() {
		return idType;
	}


	public void setIdType(String idType) {
		this.idType = idType;
	}


	public String getValidity() {
		return validity;
	}


	public void setValidity(String validity) {
		this.validity = validity;
	}


	public BigDecimal getLanguageId() {
		return languageId;
	}


	public void setLanguageId(BigDecimal languageId) {
		this.languageId = languageId;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public String getIdCheck() {
		return idCheck;
	}


	public void setIdCheck(String idCheck) {
		this.idCheck = idCheck;
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


	public String getOcrStatus() {
		return ocrStatus;
	}


	public void setOcrStatus(String ocrStatus) {
		this.ocrStatus = ocrStatus;
	}


	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}


	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}


	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}


	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}


	public Boolean getRenderEditButton() {
		return renderEditButton;
	}


	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	

	public String getOcrStatusName() {
		return ocrStatusName;
	}


	public void setOcrStatusName(String ocrStatusName) {
		this.ocrStatusName = ocrStatusName;
	}


	public Boolean getDisableSubmit() {
		return disableSubmit;
	}


	public void setDisableSubmit(Boolean disableSubmit) {
		this.disableSubmit = disableSubmit;
	}


	public Boolean getEditHide() {
		return editHide;
	}


	public void setEditHide(Boolean editHide) {
		this.editHide = editHide;
	}


	public String getApprovedDateString() {
		return approvedDateString;
	}


	public void setApprovedDateString(String approvedDateString) {
		this.approvedDateString = approvedDateString;
	}


	public Boolean getEditRecord() {
		return editRecord;
	}


	public void setEditRecord(Boolean editRecord) {
		this.editRecord = editRecord;
	}


	public List<IdentityTypeMasterDataTable> getIdentityTypeMasterList() {
		return identityTypeMasterList;
	}


	public void setIdentityTypeMasterList(
			List<IdentityTypeMasterDataTable> identityTypeMasterList) {
		this.identityTypeMasterList = identityTypeMasterList;
	}
	
	
	public List<CountryMasterDesc> getCountryNameList() {
		return countryNameList;
	}


	public void setCountryNameList(List<CountryMasterDesc> countryNameList) {
		this.countryNameList = countryNameList;
	}


	public String getCustomerType() {
		return customerType;
	}


	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	

	public String getCustomerTypeName() {
		return customerTypeName;
	}


	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}




	private Date currentTime = new Date();
	
	

	public Date getCurrentTime() {
		
		Date objList = generalService.getSysDateTimestamp(sessionStateManage.getCountryId());
		
		
		if(objList != null){
		currentTime = objList;}
		else{
			
			//currentTime.getTime();
			currentTime =null;
		}
		
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void clearCach(){
	
		try {
			clear();
			setDisableSubmit(false);
			setBooRenderDataTable(false);
			identityTypeMasterList.clear();
			identityTypeMasterNewList.clear();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "identitytypemaster.xhtml");
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect("../registration/identitytypemaster.xhtml");
			populateCountryList();
		} catch (Exception e) {
			LOG.info("Problem to redirect: " + e);
		}
		
	}
	private void populateCountryList()
	{
		List<CountryMasterDesc> countryList = generalService.getBusinessCountryList(sessionStateManage.getLanguageId());
		if(countryList.size()!=0){
			//countryList.addAll(countryList);

			for (CountryMasterDesc countryMasterDesc : countryList) {
				mapCountryList.put(countryMasterDesc.getFsCountryMaster().getCountryId(),countryMasterDesc.getCountryName());
			}
			setCountryNameList(countryList);
		}
	}
	public void clear(){
		
		setIdentityTypeIdPk(null);
		setCountryId(null);
		setBusinessComponentId(null);
		setIdType(null);
		setValidity(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setOcrStatus(null);
		setIsActive(null);
		setEditHide(false);
		setApprovedDateString(null);
		setRemarks(null);
		setEditRecord(false);
		setCustomerType(null);
		setCustomerTypeName(null);
	}

	/*
	 * 
	 * Fetch ID Type
	 * 
	 * */
	public Map<BigDecimal, String> getIdTypeList() {
		idTypeMap.putAll(ruleEngine.getComponentData("Identity Type"));
		return idTypeMap;
	}
	
	/*
	 * method to get the country Name name and country code from dataBase
	 */
	public List<CountryMasterDesc> getApplicationCountryNameList() {
		
		List<CountryMasterDesc> objList = new ArrayList<CountryMasterDesc>();
		try {
			objList=  generalService.getBusinessCountryList(sessionStateManage.getCountryId());
		}  catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveDataTableRecods");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			//return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			//return;
		}
		
		

		return objList;
	}
	
	/*// To get All Country List from CountryMasterDesc
		public List<CountryMasterDesc> getCountryNameList() {
			
			List<CountryMasterDesc> countryList = generalService.getBusinessCountryList(sessionStateManage.getLanguageId());
			if(countryList.size()!=0){
				countryList.addAll(countryList);

				for (CountryMasterDesc countryMasterDesc : countryList) {
					mapCountryList.put(countryMasterDesc.getFsCountryMaster().getCountryId(),countryMasterDesc.getCountryName());
				}
			}
			return countryList;
			
		}*/
		
		public void saveDataTable(){
			boolean duplicate=false;
			for(IdentityTypeMasterDataTable identityMaster:identityTypeMasterList){
				if(getCountryId().intValue()== identityMaster.getCountryId().intValue() && getBusinessComponentId().intValue()== identityMaster.getBusinessComponentId().intValue() && 
						getCustomerType()!=null && getCustomerType().equalsIgnoreCase(identityMaster.getCustomerType())){
					duplicate=true;
					break;
				}
			}
			
			if(!duplicate){
				IdentityTypeMasterDataTable identityMasterData = new IdentityTypeMasterDataTable();
				identityMasterData.setCountryId(getCountryId());
				identityMasterData.setCountryName(mapCountryList.get(getCountryId()));
				identityMasterData.setBusinessComponentId(getBusinessComponentId());
				identityMasterData.setIdType(idTypeMap.get(getBusinessComponentId()));
				identityMasterData.setValidity(getValidity());
				identityMasterData.setOcrStatus(getOcrStatus());
				if(getOcrStatus().equalsIgnoreCase(Constants.Yes)){
					identityMasterData.setOcrStatusName("YES");
				}else{
					identityMasterData.setOcrStatusName("NO");
				}
				
				if(getCustomerType() != null){
					if(getCustomerType().equals("I")){
						identityMasterData.setCustomerType(identityMasterData.getCustomerType());
						identityMasterData.setCustomerTypeName("Individual");
					}else if(getCustomerType().equals("C")){
						identityMasterData.setCustomerType(identityMasterData.getCustomerType());
						identityMasterData.setCustomerTypeName("Corporate");
					}
				}
				
				identityMasterData.setCustomerType(getCustomerType());
				
				if(getIdentityTypeIdPk()!=null){
					identityMasterData.setCreatedBy(getCreatedBy());
					identityMasterData.setCreatedDate(getCreatedDate());
					identityMasterData.setModifiedBy(getModifiedBy());
					identityMasterData.setModifiedDate(getModifiedDate());
					identityMasterData.setApprovedBy(getApprovedBy());
					identityMasterData.setApprovedDate(getApprovedDate());
					identityMasterData.setIsActive(getIsActive());
					identityMasterData.setRemarks(getRemarks());
					identityMasterData.setIdentityTypeIdPk(getIdentityTypeIdPk());
					identityMasterData.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					identityMasterData.setEditRecord(true);
					
					}else{
						identityMasterData.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
						identityMasterData.setCreatedBy(sessionStateManage.getUserName());
						identityMasterData.setCreatedDate(new Date());
						identityMasterData.setIsActive(Constants.U);
						identityTypeMasterNewList.add(identityMasterData);
					}
				
				identityTypeMasterList.add(identityMasterData);
				setBooRenderDataTable(true);
				setDisableSubmit(false);
				setEditHide(false);
				
				clear();
			}else{
				
				RequestContext.getCurrentInstance().execute("duplicate.show();");
			}
			
		}
		
		
		public void checkStatusType(IdentityTypeMasterDataTable identityTypeData)
				throws IOException {

			if (identityTypeData.getDynamicLabelForActivateDeactivate()
					.equalsIgnoreCase(Constants.DEACTIVATE)) {
				setIsActive(identityTypeData.getIsActive());
				
				setIdentityTypeIdPk(identityTypeData.getIdentityTypeIdPk());
				
				setApprovedBy(identityTypeData.getApprovedBy());
				setApprovedDate(identityTypeData.getApprovedDate());
			    setRemarks(null);
				RequestContext.getCurrentInstance().execute("remarks.show();");

			}else if(identityTypeData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && identityTypeData.getModifiedBy()==null){
				try {
					identityTypeMasterService.delete(identityTypeData.getIdentityTypeIdPk());
					identityTypeMasterList.remove(identityTypeData);
					identityTypeMasterNewList.remove(identityTypeData);
					if(identityTypeMasterList.size()==0 && identityTypeMasterNewList.size()==0){
						setBooRenderDataTable(false);
					}
				
				RequestContext.getCurrentInstance().execute("deletemsg.show();");

				} catch (NullPointerException ne) {
					// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setErrorMessage("Method Name::saveDataTableRecods");
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return;
				} catch (Exception exception) {
					// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}
				  
				
				
			}else if(identityTypeData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE) ){

             setIdentityTypeIdPk(identityTypeData.getIdentityTypeIdPk());
				
				RequestContext.getCurrentInstance().execute("pending.show();");
				
		} else if (identityTypeData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {

			try {
				identityTypeMasterService.activate(identityTypeData.getIdentityTypeIdPk(), sessionStateManage.getUserName(), getCurrentTime());
				view();
			} catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::saveDataTableRecods");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}

		}else if(identityTypeData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
				identityTypeMasterList.remove(identityTypeData);
				identityTypeMasterNewList.remove(identityTypeData);
			}

		}
		
		public void editRecord(IdentityTypeMasterDataTable identityTypeData){
			clear();
			setDisableSubmit(true);
			setEditHide(true);
			setIdentityTypeIdPk(identityTypeData.getIdentityTypeIdPk());
			getCountryNameList();
            setCountryId(identityTypeData.getCountryId());
			setBusinessComponentId(identityTypeData.getBusinessComponentId());
		    setValidity(identityTypeData.getValidity());
		    setOcrStatus(identityTypeData.getOcrStatus());
		    setOcrStatusName(identityTypeData.getOcrStatusName());
		    setIsActive(identityTypeData.getIsActive());
		    setCreatedBy(identityTypeData.getCreatedBy());
		    setCreatedDate(identityTypeData.getCreatedDate());
		    setCustomerType(identityTypeData.getCustomerType());
		    
			if(identityTypeData.getIsActive().equalsIgnoreCase(Constants.Yes)){
				setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				}else if(identityTypeData.getIsActive().equalsIgnoreCase(Constants.D)){
					setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				}else if(identityTypeData.getIsActive().equalsIgnoreCase(Constants.U)){
					if(identityTypeData.getModifiedBy()!=null && identityTypeData.getModifiedDate()!=null){
						setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}else{
					setDynamicLabelForActivateDeactivate(Constants.DELETE);
					}
				}
		    setDynamicLabelForActivateDeactivate(identityTypeData.getDynamicLabelForActivateDeactivate());
		    if(identityTypeData.getModifiedBy()!=null){
		    setModifiedBy(identityTypeData.getModifiedBy());
		    setModifiedDate(identityTypeData.getModifiedDate());
		    }
		    if(identityTypeData.getApprovedBy()!=null){
		    	setApprovedBy(identityTypeData.getApprovedBy());
		    	setApprovedDate(identityTypeData.getApprovedDate());
		    	
		    }
		    
		    identityTypeMasterList.remove(identityTypeData);
		    identityTypeMasterNewList.remove(identityTypeData);
		    if(identityTypeMasterList.size()==0){
		    	setBooRenderDataTable(false);
		    }
			
			
		}
		
		private void addRecordsToDatatable(List<IdentityTypeMaster> identityTypeList)
		{
			for(IdentityTypeMaster identityMasterData:identityTypeList){
				
				IdentityTypeMasterDataTable masterDataTable = new IdentityTypeMasterDataTable();
				masterDataTable.setIdentityTypeIdPk(identityMasterData.getIdentityTypeId());
				masterDataTable.setCountryId(identityMasterData.getApplicationCountryId());
				masterDataTable.setCountryName(mapCountryList.get(identityMasterData.getApplicationCountryId()));
				masterDataTable.setBusinessComponentId(identityMasterData.getBusinessComponentId());
				masterDataTable.setIdType(idTypeMap.get(identityMasterData.getBusinessComponentId()));
				masterDataTable.setValidity(identityMasterData.getValidity());
				masterDataTable.setOcrStatus(identityMasterData.getOcrStstus());
				masterDataTable.setIsActive(identityMasterData.getIsActive());
				
				if(identityMasterData.getCustomerType() != null){
					if(identityMasterData.getCustomerType().equals("I")){
						masterDataTable.setCustomerType(identityMasterData.getCustomerType());
						masterDataTable.setCustomerTypeName("Individual");
					}else if(identityMasterData.getCustomerType().equals("C")){
						masterDataTable.setCustomerType(identityMasterData.getCustomerType());
						masterDataTable.setCustomerTypeName("Corporate");
					}
				}
				
				if(identityMasterData.getOcrStstus().equalsIgnoreCase(Constants.Yes)){
					masterDataTable.setOcrStatusName("YES");
				}else{
					masterDataTable.setOcrStatusName("NO");
				}
				masterDataTable.setCreatedBy(identityMasterData.getCreatedBy());
				masterDataTable.setCreatedDate(identityMasterData.getCreatedDate());
				if(identityMasterData.getModifiedBy()!=null){
				masterDataTable.setModifiedBy(identityMasterData.getModifiedBy());
				masterDataTable.setModifiedDate(identityMasterData.getModifiedDate());
				}
				if(identityMasterData.getApprovBy()!=null){
				masterDataTable.setApprovedBy(identityMasterData.getApprovBy());
				masterDataTable.setApprovedDate(identityMasterData.getApprovDate());
				}
				if(identityMasterData.getRemarks()!=null){
				masterDataTable.setRemarks(identityMasterData.getRemarks());
				}
				if(identityMasterData.getIsActive().equalsIgnoreCase(Constants.Yes)){
					masterDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					}else if(identityMasterData.getIsActive().equalsIgnoreCase(Constants.D)){
						masterDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					}else if(identityMasterData.getIsActive().equalsIgnoreCase(Constants.U)){
						if(identityMasterData.getModifiedBy() != null && identityMasterData.getModifiedDate() != null){
							masterDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						}else{
						masterDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
						}
					}
				identityTypeMasterList.add(masterDataTable);
				
				
			}
		}
		public void view(){
			try {
				clear();
				identityTypeMasterList.clear();
				identityTypeMasterNewList.clear();
				identityTypeList = identityTypeMasterService.view();
				
				if(identityTypeList.size()>0 ){
					
					addRecordsToDatatable(identityTypeList);
					if(identityTypeMasterNewList.size()!=0)
					{
						identityTypeMasterList.addAll(identityTypeMasterNewList);
					}
					setBooRenderDataTable(true);
					
				}else{
					
					if(identityTypeMasterNewList.size()!=0)
					{
						identityTypeMasterList.addAll(identityTypeMasterNewList);
						setBooRenderDataTable(true);
						setEditHide(false);
					}else{
						setBooRenderDataTable(false);
						RequestContext.getCurrentInstance().execute("norecord.show();");
					}
						
					
					
				}
				
			} catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::saveDataTableRecods");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}

			
		}
		
		@SuppressWarnings("unchecked")
		public void save(){
			try{
			for(IdentityTypeMasterDataTable identityMasterData:identityTypeMasterList){
				IdentityTypeMaster identityMaster = new IdentityTypeMaster();
				identityMaster.setApplicationCountryId(identityMasterData.getCountryId());
				identityMaster.setBusinessComponentId(identityMasterData.getBusinessComponentId());
				identityMaster.setIdentityType(idTypeMap.get(identityMasterData.getBusinessComponentId()));
				identityMaster.setIsActive(identityMasterData.getIsActive());
				identityMaster.setValidity(identityMasterData.getValidity());
				identityMaster.setOcrStstus(identityMasterData.getOcrStatus());
				identityMaster.setCustomerType(identityMasterData.getCustomerType());
				BusinessComponent businessCom = new BusinessComponent();
				businessCom.setComponentId(new BigDecimal(Constants.BIZ_COMPONENT_IDENTITY_TYPE));
				identityMaster.setBusinessComponent(businessCom);
				if(identityMasterData.getIdentityTypeIdPk()!=null){
					identityMaster.setIdentityTypeId(identityMasterData.getIdentityTypeIdPk());
					identityMaster.setCreatedBy(identityMasterData.getCreatedBy());
					identityMaster.setCreatedDate(identityMasterData.getCreatedDate());
					if(identityMasterData.getApprovedBy() !=null){
					identityMaster.setApprovDate(identityMasterData.getApprovedDate());
					identityMaster.setApprovBy(identityMasterData.getApprovedBy());
					}
					if(identityMasterData.getEditRecord()==true){
					identityMaster.setModifiedBy(sessionStateManage.getUserName());
					identityMaster.setModifiedDate(getCurrentTime());
					}
					
				}else{
					identityMaster.setCreatedBy(identityMasterData.getCreatedBy());
					identityMaster.setCreatedDate(getCurrentTime());
					
				}
				generalService.saveOrUpdate((T) identityMaster);
				
				identityTypeMasterNewList.clear();
			}
			RequestContext.getCurrentInstance().execute("complete.show();");
			}catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::saveDataTableRecods");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		}
		
		public void exit() throws IOException {
			   if(sessionStateManage.getRoleId().equalsIgnoreCase(Constants.USER_ROLE_MANAGER)){
					FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
				}else{
					FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
				}
		}
		
		public void okSaved(){
			
			clear();
		identityTypeMasterList.clear();
		identityTypeMasterNewList.clear();
		setBooRenderDataTable(false);
			
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/identitytypemaster.xhtml");
			} catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::saveDataTableRecods");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		}
		
		
	public void serviceApproval(){
			
			try {
				approvalList();
				loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "identitytypemasterapprovallist.xhtml");
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/identitytypemasterapprovallist.xhtml");
				
			
			} catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::serviceApproval");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
			
		}

		public List<IdentityTypeMasterDataTable> getListForApproval() {
			
			return listForApproval;
		}


	public void approvalList(){
		try{
			listForApproval.clear();
			List<IdentityTypeMaster> unApprovalTypeList = identityTypeMasterService.getUnApprovedList();
			
			if(unApprovalTypeList.size()>0){
				
				for(IdentityTypeMaster identityMasterData:unApprovalTypeList){
					
					IdentityTypeMasterDataTable masterDataTable = new IdentityTypeMasterDataTable();
					masterDataTable.setIdentityTypeIdPk(identityMasterData.getIdentityTypeId());
					masterDataTable.setCountryId(identityMasterData.getApplicationCountryId());
					populateCountryList();
					masterDataTable.setCountryName(mapCountryList.get(identityMasterData.getApplicationCountryId()));
					masterDataTable.setBusinessComponentId(identityMasterData.getBusinessComponentId());
					getIdTypeList();
					masterDataTable.setIdType(idTypeMap.get(identityMasterData.getBusinessComponentId()));
					masterDataTable.setValidity(identityMasterData.getValidity());
					masterDataTable.setOcrStatus(identityMasterData.getOcrStstus());
					if(identityMasterData.getOcrStstus().equalsIgnoreCase(Constants.Yes)){
						masterDataTable.setOcrStatusName("YES");
					}else{
						masterDataTable.setOcrStatusName("NO");
					}
					masterDataTable.setCreatedBy(identityMasterData.getCreatedBy());
					masterDataTable.setCreatedDate(identityMasterData.getCreatedDate());
					masterDataTable.setModifiedBy(identityMasterData.getModifiedBy());
					masterDataTable.setModifiedDate(identityMasterData.getModifiedDate());
					masterDataTable.setRemarks(identityMasterData.getRemarks());
					masterDataTable.setIsActive(identityMasterData.getIsActive());

					if(identityMasterData.getCustomerType() != null){
						if(identityMasterData.getCustomerType().equals("I")){
							masterDataTable.setCustomerType(identityMasterData.getCustomerType());
							masterDataTable.setCustomerTypeName("Individual");
						}else if(identityMasterData.getCustomerType().equals("C")){
							masterDataTable.setCustomerType(identityMasterData.getCustomerType());
							masterDataTable.setCustomerTypeName("Corporate");
						}
					}
					listForApproval.add(masterDataTable);
					
					
				}
				
			}

		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveDataTableRecods");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		
	}
	
	
	public void cancel(){
		//setRemarks(null);
		try {
			listForApproval.clear();
			approvalList();
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/identitytypemasterapprovallist.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void goToApproval(IdentityTypeMasterDataTable identityDataTable){
		
	if(CommonUtil.validateApprovedBy(sessionStateManage.getUserName(), identityDataTable.getCreatedBy())!= true){

		setIdentityTypeIdPk(identityDataTable.getIdentityTypeIdPk());
		setCountryId(identityDataTable.getCountryId());
		setCountryName(mapCountryList.get(identityDataTable.getCountryId()));
		setBusinessComponentId(identityDataTable.getBusinessComponentId());
		setIdType(idTypeMap.get(identityDataTable.getBusinessComponentId()));
		setValidity(identityDataTable.getValidity());
		setCreatedBy(identityDataTable.getCreatedBy());
		setCreatedDate(identityDataTable.getCreatedDate());
		setOcrStatusName(identityDataTable.getOcrStatusName());
		setCustomerType(identityDataTable.getCustomerType());
		if(identityDataTable.getCustomerType()!=null && identityDataTable.getCustomerType().equalsIgnoreCase(Constants.Individual)){
			setCustomerTypeName(Constants.CUSTOMERTYPE_INDU);
		}else if(identityDataTable.getCustomerType() !=null && identityDataTable.getCustomerType().equalsIgnoreCase(Constants.NonIndividual)){
			setCustomerTypeName(Constants.CUSTOMERTYPE_CORP);
		}
		
		if(identityDataTable.getOcrStatus().equalsIgnoreCase(Constants.Yes)){
			setOcrStatus("YES");
		}else{
			setOcrStatus("NO");
		}
		if(identityDataTable.getModifiedBy()!=null){
			setModifiedBy(identityDataTable.getModifiedBy());
			setModifiedDate(identityDataTable.getModifiedDate());
		}
		
		try {
			
			FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../registration/identitytypemasterapproval.xhtml");
		 
		} catch (Exception e) {
			e.printStackTrace();
			
		}

	}else{
			
			RequestContext.getCurrentInstance().execute("notabletoApprove.show();");
			
		}
}

	
	public void approve(){
		try {
			if(CommonUtil.validateApprovedBy(sessionStateManage.getUserName(), getCreatedBy())!= true){
				
				identityTypeMasterService.approve(getIdentityTypeIdPk(), sessionStateManage.getUserName(),getCurrentTime());
				RequestContext.getCurrentInstance().execute("approvedsuccess.show();");
				
			}else{
				RequestContext.getCurrentInstance().execute("notabletoApprove.show();");	
				
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveDataTableRecods");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		
		
	}

   public void clickOKApproval(){
	   try {
		   identityTypeMasterService.softDelete(getIdentityTypeIdPk(), sessionStateManage.getUserName(),getCurrentTime(),getRemarks());
		   view();
	} catch (NullPointerException ne) {
		// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		setErrorMessage("Method Name::saveDataTableRecods");
		RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		return;
	} catch (Exception exception) {
		// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		setErrorMessage(exception.getMessage());
		RequestContext.getCurrentInstance().execute("error.show();");
		return;
	}
	  
			
		}
		
  public void checkRecord(){
	  try {
		  List<IdentityTypeMaster> identityList = identityTypeMasterService.view();
			boolean duplicate = false;
			if(identityList.size()>0 ){
				
				for(IdentityTypeMaster identity:identityList){
					
					if(identity.getBusinessComponentId().intValue()==getBusinessComponentId().intValue() && identity.getApplicationCountryId().intValue()==getCountryId().intValue() &&
							identity.getCustomerType()!=null && identity.getCustomerType().equalsIgnoreCase(getCustomerType())){
						if(getIdentityTypeIdPk()!=null){
							if(getIdentityTypeIdPk().intValue()== identity.getIdentityTypeId().intValue()){
								duplicate = false;
							
							}else{
								duplicate = true;
								break;
							}
							
						}else{
						duplicate = true;
						break;
						}
						
					}else{
						duplicate = false;
						
						
					}

					
				}
				
							
			}
			if(!duplicate){
				saveDataTable();
			}else{
				RequestContext.getCurrentInstance().execute("recordavailable.show();");
			}
	} catch (NullPointerException ne) {
		// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		setErrorMessage("Method Name::saveDataTableRecods");
		RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		return;
	} catch (Exception exception) {
		// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		setErrorMessage(exception.getMessage());
		RequestContext.getCurrentInstance().execute("error.show();");
		return;
	}
		
  }
}








