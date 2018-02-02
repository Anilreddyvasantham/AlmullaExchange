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

import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.IRatesUpdateService;
import com.amg.exchange.treasury.service.IRemittanceModeService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.CollectionUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/*
 *  Author Rahamathali Shaik
 */
@Component("remitanceModeMasterBean")
@Scope("session")
public class RemittanceModeMasterBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(RemittanceModeMasterBean.class);
	private SessionStateManage session = new SessionStateManage();

	private BigDecimal serviceId = null;
	private String remitance = null;
	private String englishRemittanceMode = null;
	private String arabicRemittanceMode = null;
	private Boolean booRenderDataTable = false;
	private Boolean booRenderSaveExit = false;
	private Boolean booRemmittanceCheck = false;
	private Boolean booRemmittanceCheckForDataTable = false;
	private Boolean booDisableEdit=false;
	private Boolean booDisableClear=false;
	private Boolean booCheckUpdate=false;
	private Boolean booCheckDelete=false;
	private Boolean booPop=false;

	private List<ServiceMasterDesc> serviceMasterList = new ArrayList<ServiceMasterDesc>();
	private List<RemittanceModeMasterDataTable> remittanceModeMasterDTList = new CopyOnWriteArrayList<RemittanceModeMasterDataTable>();
	private Map<BigDecimal, String> serviceMasterMap = new HashMap<BigDecimal, String>();

	@Autowired
	IRemittanceModeService remittanceModeService;
	@SuppressWarnings("rawtypes")
	@Autowired
	IRatesUpdateService ratesUpdateService;

	public Boolean getBooRemmittanceCheckForDataTable() {
		return booRemmittanceCheckForDataTable;
	}

	public void setBooRemmittanceCheckForDataTable(Boolean booRemmittanceCheckForDataTable) {
		this.booRemmittanceCheckForDataTable = booRemmittanceCheckForDataTable;
	}

	public Boolean getBooRemmittanceCheck() {
		return booRemmittanceCheck;
	}

	public void setBooRemmittanceCheck(Boolean booRemmittanceCheck) {
		this.booRemmittanceCheck = booRemmittanceCheck;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public Boolean getBooRenderSaveExit() {
		return booRenderSaveExit;
	}

	public void setBooRenderSaveExit(Boolean booRenderSaveExit) {
		this.booRenderSaveExit = booRenderSaveExit;
	}

	public BigDecimal getServiceId() {
		return serviceId;
	}

	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	public String getRemitance() {
		return remitance;
	}

	public void setRemitance(String remitance) {
		this.remitance = remitance;
	}

	public String getEnglishRemittanceMode() {
		return englishRemittanceMode;
	}

	public void setEnglishRemittanceMode(String englishRemittanceMode) {
		this.englishRemittanceMode = englishRemittanceMode;
	}

	public String getArabicRemittanceMode() {
		return arabicRemittanceMode;
	}

	public void setArabicRemittanceMode(String arabicRemittanceMode) {
		this.arabicRemittanceMode = arabicRemittanceMode;
	}

	@SuppressWarnings("unchecked")
	public List<ServiceMasterDesc> getServiceMasterList() {
		  List<ServiceMasterDesc> serviceMasterList=null;
		  try{
		serviceMasterList = remittanceModeService.getServiceMastersActivateList(session.getLanguageId());

		for (ServiceMasterDesc serviceMas : serviceMasterList) {
			serviceMasterMap.put(serviceMas.getServiceMaster().getServiceId(), serviceMas.getLocalServiceDescription());
		}
		  }catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
		  }
		return serviceMasterList;
	}

	public void setServiceMasterList(List<ServiceMasterDesc> serviceMasterList) {
		this.serviceMasterList = serviceMasterList;
	}

	public List<RemittanceModeMasterDataTable> getRemittanceModeMasterDTList() {
		return remittanceModeMasterDTList;
	}

	public void setRemittanceModeMasterDTList(List<RemittanceModeMasterDataTable> remittanceModeMasterDTList) {
		this.remittanceModeMasterDTList = remittanceModeMasterDTList;
	}

	public void clickOnOKSave() {
		remittanceModeMasterDTList.clear();
		setBooRenderDataTable(false);
		setBooRenderSaveExit(false);

		setArabicRemittanceMode(null);
		setEnglishRemittanceMode(null);
		setRemitaDescArabicPk(null);
		setRemitancePk(null);
		setRemitaDescEngPk(null);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/RemittanceModeMaster.xhtml");
		} catch (IOException exception) {
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		}
	}

	public void exit() throws IOException {
		  try{
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		  }
	}

	public void clearAllFields() {
		setServiceId(null);
		setRemitance(null);
		setEnglishRemittanceMode(null);
		setEnglishRemittanceMode(null);
		setArabicRemittanceMode(null);
		setRemittanceDesc(null);

		setBooRemmittanceCheck(false);
		setRemittanceDesc(null);
		setRemitancePk(null);
		setRemitaDescEngPk(null);
		setRemitaDescArabicPk(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setBooRemmittanceCheckForDataTable(false);
		if (remittanceModeMasterDTList.size() > 0) {
			setSubmit(false);
		}
	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void remittanceModeMasterPageNavigation() {
		clearAllFields();
		setBooRenderDataTable(false);
		setBooRenderSaveExit(false);
		setBoohideSecod(false);
		setArabicRemittanceMode(null);
		setEnglishRemittanceMode(null);
		setRemitaDescArabicPk(null);
		setRemitancePk(null);
		setRemitaDescEngPk(null);
		setBoohideButton(true);
		setBooReadonly(false);
		setBooDisableClear(false);
		remittanceModeMasterDTList.clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "RemittanceModeMaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/RemittanceModeMaster.xhtml");
		} catch (Exception exception) {
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		}

	}

	public void editRecord(RemittanceModeMasterDataTable remittanceMasterDTObj) {
		  try{
		setServiceId(remittanceMasterDTObj.getServiceId());
		setRemitance(remittanceMasterDTObj.getRemitance());
		setEnglishRemittanceMode(remittanceMasterDTObj.getEnglishRemittanceMode());
		setArabicRemittanceMode(remittanceMasterDTObj.getArabicRemittanceMode());
		//setDynamicLabelForActivateDeactivate(remittanceMasterDTObj.getDynamicLabelForActivateDeactivate());
		setRemitancePk(remittanceMasterDTObj.getPkRmtnce());
		setRemitaDescEngPk(remittanceMasterDTObj.getPkRemDesc1());
		setRemitaDescArabicPk(remittanceMasterDTObj.getPkRemDesc2());
		setRenderEditButton(remittanceMasterDTObj.getRenderEditButton());
		setIsActive(Constants.U);
		setCheckSave(remittanceMasterDTObj.getCheckSave());
		setCreatedBy(remittanceMasterDTObj.getCreatedBy());
		setCreatedDate(remittanceMasterDTObj.getCreatedDate());
		setBooDisableClear(true);
		setBooDisableEdit(true);
		setBooCheckUpdate(true);

		if (remittanceMasterDTObj.getPkRmtnce() != null) {
			setModifiedBy(session.getUserName());
			setModifiedDate(new Date());
		}
		setApprovedBy(null);
		setApprovedDate(null);
		remittanceModeMasterDTList.remove(remittanceMasterDTObj);
		setSubmit(true);
		if (remittanceModeMasterDTList.size() <= 0) {
			setBooRenderSaveExit(false);
			setBooRenderDataTable(false);
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		  }
	}

	public void delete(RemittanceModeMasterDataTable remittanceMasterDTObj) {
		try {

			RemittanceModeMaster remittanceModeMaster = new RemittanceModeMaster();
			remittanceModeMaster.setRemittanceModeId(remittanceMasterDTObj.getPkRmtnce());
			ServiceMaster serviceMaster = new ServiceMaster();
			serviceMaster.setServiceId(remittanceMasterDTObj.getServiceId());
			remittanceModeMaster.setServiceMaster(serviceMaster);

			remittanceModeMaster.setRemittance(remittanceMasterDTObj.getRemitance());
			if (remittanceMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				remittanceModeMaster.setIsActive(Constants.U);

				 
			} else if (remittanceMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				remittanceModeMaster.setRemarks(remittanceMasterDTObj.getRemarks());
				remittanceModeMaster.setIsActive(Constants.D);

			}
			// remittanceModeMaster.setRemittanceDesEnglish(remittanceMasterDTObj.getEnglishRemittanceMode());
			if (remittanceMasterDTObj.getPkRmtnce() != null) {

				remittanceModeMaster.setCreatedBy(remittanceMasterDTObj.getCreatedBy());
				remittanceModeMaster.setCreatedDate(remittanceMasterDTObj.getCreatedDate());

				remittanceModeMaster.setModifiedBy(session.getUserName());
				remittanceModeMaster.setModifiedDate(new Date());

			} else {
				remittanceModeMaster.setCreatedBy(session.getUserName());
				remittanceModeMaster.setCreatedDate(new Date());
			}

		 
			remittanceModeService.saveRecord(remittanceModeMaster);

			RemittanceModeDescription remittanceModeDescription = new RemittanceModeDescription();
			LanguageType languageType = new LanguageType();
			languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			remittanceModeDescription.setLanguageType(languageType);
			remittanceModeDescription.setRemittanceDescription(remittanceMasterDTObj.getEnglishRemittanceMode());
			remittanceModeDescription.setRemittanceModeMaster(remittanceModeMaster);

			remittanceModeDescription.setRemittanceModeDescId(remittanceMasterDTObj.getPkRemDesc1());
			 
			remittanceModeService.saveRecordForDesc(remittanceModeDescription);

			RemittanceModeDescription remittanceModeDescription2 = new RemittanceModeDescription();
			LanguageType languageType2 = new LanguageType();
			languageType2.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			remittanceModeDescription2.setLanguageType(languageType2);
			remittanceModeDescription2.setRemittanceDescription(remittanceMasterDTObj.getArabicRemittanceMode());
			remittanceModeDescription2.setRemittanceModeMaster(remittanceModeMaster);

			remittanceModeDescription2.setRemittanceModeDescId(remittanceMasterDTObj.getPkRemDesc2());
			 

			remittanceModeService.saveRecordForDesc(remittanceModeDescription2);

		} catch (Exception exception) {
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrmsg(exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return ;
		}
	}

	public void removeRecord(RemittanceModeMasterDataTable remittanceMasterDTObj)throws Exception {
		if (remittanceMasterDTObj.getPkRmtnce() == null) {
			if (remittanceMasterDTObj.getCheckSave().equals(true)) {
				 
				remittanceModeMasterDTList.remove(remittanceMasterDTObj);
				if (remittanceModeMasterDTList.size() <= 0) {
					setBooRenderSaveExit(false);
					setBooRenderDataTable(false);
				}
			}
			// remittanceModeMasterDTList.remove(remittanceMasterDTObj);

		} else {
			remitDtObj = remittanceMasterDTObj;
			if (remitDtObj.getIsActive().equalsIgnoreCase(Constants.Yes)) {
				setApprovedDate(remitDtObj.getApprovedDate());
				setApprovedBy(remitDtObj.getApprovedBy());
				RequestContext.getCurrentInstance().execute("remarks.show();");
				remitDtObj.setBooCheckDelete(remitDtObj.getBooCheckDelete());
			} else if (remitDtObj.getIsActive().equalsIgnoreCase(Constants.D)) {
				remitDtObj.setBooCheckDelete(remitDtObj.getBooCheckDelete());
				delete(remittanceMasterDTObj);
				remittanceModeMasterDTList.clear();
				view();
			} else if (remitDtObj.getIsActive().equalsIgnoreCase(Constants.U)) {
					if(remitDtObj.getModifiedBy() ==null &&remitDtObj. getModifiedDate() ==null && remitDtObj.getRemarks() ==null&&remitDtObj.getApprovedBy()==null&&remitDtObj.getApprovedDate()==null){
						remitDtObj.setBooCheckDelete(true);
						RequestContext.getCurrentInstance().execute("permanentDelete.show();");
						
					}else{
						remitDtObj.setBooCheckDelete(true);
						RequestContext.getCurrentInstance().execute("pending.show();");
						
						/*remitDtObj.setBooCheckDelete(remitDtObj.getBooCheckDelete());
						remittanceModeMasterDTList.remove(remitDtObj );
						if(remittanceModeMasterDTList.size()==0){
							setBooRenderDataTable(false);
							setBooRenderSaveExit(false);
						}*/
						//RequestContext.getCurrentInstance().execute("recordExist.show();");
						//return;
					}
			}
		}
	}
	
	
	
	
	public void confirmPermanentDelete(){
		  try{
		if(remittanceModeMasterDTList.size()>0){
			for (RemittanceModeMasterDataTable  remittanceDatatable : remittanceModeMasterDTList) {
				if(remittanceDatatable.getBooCheckDelete() != null){
				  if(remittanceDatatable.getBooCheckDelete().equals(true)){
					remittanceModeDelete(remittanceDatatable);
					remittanceModeMasterDTList.remove(remittanceDatatable);
				}
				}
			}
		}
		  }catch(NullPointerException ne){
			    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			    setErrmsg("MethodName::confirmPermanentDelete");
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
			    }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrmsg(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
	 }

	public void remittanceModeDelete(RemittanceModeMasterDataTable remittanceMasterDTObj)throws Exception {
		RemittanceModeDescription remittanceModeDescription = new RemittanceModeDescription();
		LanguageType languageType = new LanguageType();
		languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
		remittanceModeDescription.setLanguageType(languageType);
		remittanceModeDescription.setRemittanceModeDescId(remittanceMasterDTObj.getPkRemDesc1());
		remittanceModeDescription.setRemittanceDescription(remittanceMasterDTObj.getEnglishRemittanceMode());
		remittanceModeService.delete(remittanceModeDescription);

		// Arabic
		RemittanceModeDescription remittanceModeDescription2 = new RemittanceModeDescription();
		LanguageType languageType2 = new LanguageType();
		languageType2.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
		remittanceModeDescription2.setLanguageType(languageType2);
		remittanceModeDescription2.setRemittanceModeDescId(remittanceMasterDTObj.getPkRemDesc2());
		remittanceModeDescription2.setRemittanceDescription(remittanceMasterDTObj.getArabicRemittanceMode());
		remittanceModeService.delete(remittanceModeDescription2);

		RemittanceModeMaster remittanceModeMaster = new RemittanceModeMaster();
		remittanceModeMaster.setRemittanceModeId(remittanceMasterDTObj.getPkRmtnce());
		ServiceMaster serviceMaster = new ServiceMaster();
		serviceMaster.setServiceId(remittanceMasterDTObj.getServiceId());
		remittanceModeMaster.setRemittance(remittanceMasterDTObj.getRemitance());
		remittanceModeMaster.setCreatedBy(remittanceMasterDTObj.getCreatedBy());
		remittanceModeMaster.setCreatedDate(remittanceMasterDTObj.getCreatedDate());

		/*remittanceModeMaster.setModifiedBy(session.getUserName());
		remittanceModeMaster.setModifiedDate(new Date());
		remittanceModeMaster.setApprovedBy(remittanceMasterDTObj.getApprovedBy());
		remittanceModeMaster.setApprovedDate(remittanceMasterDTObj.getApprovedDate());*/

		remittanceModeMaster.setIsActive(remittanceMasterDTObj.getIsActive());
		remittanceModeService.delete(remittanceModeMaster);
		
	}

	private List<RemittanceModeMasterDataTable> remitDTList = new CopyOnWriteArrayList<RemittanceModeMasterDataTable>();

	public List<RemittanceModeMasterDataTable> getRemitDTList() {
		return remitDTList;
	}

	public void setRemitDTList(List<RemittanceModeMasterDataTable> remitDTList) {
		this.remitDTList = remitDTList;
	}
	

	public void addRecordsToDataTable() {
		try{ 
		 
		
		setSubmit(false);
		if (remitDTList.size() == 0) {

			RemittanceModeMasterDataTable remittanceMasterDTObj = new RemittanceModeMasterDataTable();
			remittanceMasterDTObj.setServiceId(getServiceId());
			remittanceMasterDTObj.setServiceName(getServiceMasterList().get(0).getLocalServiceDescription());
			remittanceMasterDTObj.setServiceName(serviceMasterMap.get(getServiceId()));
			remittanceMasterDTObj.setPkRmtnce(getRemitancePk());
			remittanceMasterDTObj.setRemitance(getRemitance());
			remittanceMasterDTObj.setPkRemDesc1(getRemitaDescEngPk());
			remittanceMasterDTObj.setPkRemDesc2(getRemitaDescArabicPk());
			//19/05/2015
			List<RemittanceModeDescription> remitlistForCheck= remittanceModeService.getCheckRemittCode( getRemitance());
			if(remitlistForCheck.size()>0){
				for(RemittanceModeDescription remitDesc:remitlistForCheck){
					remittanceMasterDTObj.setPkRmtnce(remitDesc.getRemittanceModeMaster().getRemittanceModeId() );
					remittanceMasterDTObj.setCreatedBy(remitDesc.getRemittanceModeMaster().getCreatedBy() );
					remittanceMasterDTObj.setCreatedDate(remitDesc.getRemittanceModeMaster().getCreatedDate() );
					if(remitDesc.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID.toString())){
						remittanceMasterDTObj.setPkRemDesc1(remitDesc.getRemittanceModeDescId() );
						
					}else{
						 remittanceMasterDTObj.setPkRemDesc2(remitDesc.getRemittanceModeDescId());
						
					}
				}
				
			}
			//19/05/2015
			if (getRemitancePk() != null) {
				remittanceMasterDTObj.setCreatedBy(getCreatedBy());
				remittanceMasterDTObj.setCreatedDate(getCreatedDate());
				remittanceMasterDTObj.setRemarks(remarks);
				remittanceMasterDTObj.setApprovedBy(null);
				remittanceMasterDTObj.setApprovedDate(null);
				remittanceMasterDTObj.setBooCheckUpdate(getBooCheckUpdate());
				if (getIsActive() != Constants.Yes) {
					if( getRemitance()!=null){
						List<RemittanceModeDescription> listRemit=remittanceModeService.getCheckRemittCode( getRemitance());
					if(listRemit.size()>0){
					
					}else{
						remittanceMasterDTObj.setPkRmtnce(null);
					 
						remittanceMasterDTObj.setPkRemDesc1(null);
						remittanceMasterDTObj.setPkRemDesc2(null);
						remittanceMasterDTObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
						remittanceMasterDTObj.setCreatedBy(session.getUserName());
						remittanceMasterDTObj.setCreatedDate(new Date());
						remittanceMasterDTObj.setBooCheckUpdate(true);
						remittanceMasterDTObj.setCheckSave(true);
					}
					}
					remittanceMasterDTObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					remittanceMasterDTObj.setRenderEditButton(true);
					remittanceMasterDTObj.setIsActive(Constants.U);
					remittanceMasterDTObj.setModifiedBy(session.getUserName());
					remittanceMasterDTObj.setModifiedDate(new Date());

				} else if (getIsActive() != Constants.D) {
					remittanceMasterDTObj.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					remittanceMasterDTObj.setRenderEditButton(true);
					remittanceMasterDTObj.setIsActive(Constants.U);
					remittanceMasterDTObj.setModifiedBy(session.getUserName());
					remittanceMasterDTObj.setModifiedDate(new Date());
				
				}

			} else {
				if(remittanceMasterDTObj.getPkRmtnce()!=null){
					remittanceMasterDTObj.setModifiedBy(session.getUserName());
					remittanceMasterDTObj.setModifiedDate(new Date());
					remittanceMasterDTObj.setRenderEditButton(true);
					remittanceMasterDTObj.setIsActive(Constants.U);
					remittanceMasterDTObj.setCheckSave(true);
					remittanceMasterDTObj.setBooCheckUpdate(true);
					remittanceMasterDTObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				}
				else{
				remittanceMasterDTObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				remittanceMasterDTObj.setRenderEditButton(true);
				remittanceMasterDTObj.setIsActive(Constants.U);
				remittanceMasterDTObj.setCheckSave(true);
				remittanceMasterDTObj.setCreatedBy(session.getUserName());
				remittanceMasterDTObj.setCreatedDate(new Date());
				remittanceMasterDTObj.setBooCheckUpdate(true);
				}
			}

			remittanceMasterDTObj.setEnglishRemittanceMode(getEnglishRemittanceMode());
			remittanceMasterDTObj.setArabicRemittanceMode(getArabicRemittanceMode());

			
			remittanceModeMasterDTList.add(remittanceMasterDTObj);
		}
		if (remittanceModeMasterDTList.size() > 0) {
			if (remitDTList.size() > 0) {
				for (RemittanceModeMasterDataTable remitDt : remittanceModeMasterDTList) {
					for (RemittanceModeMasterDataTable remitDl : remitDTList) {
						if (remitDt.getRemitance().equals(remitDl.getRemitance())) {
							remitDTList.remove(remitDl);
						}
					}
				}

			}
			remittanceModeMasterDTList.addAll(remitDTList);
		} else {
			remittanceModeMasterDTList.addAll(remitDTList);
		}
		clearAllFields();
		setBooRenderDataTable(true);
		setBooRenderSaveExit(true);
		setBooDisableClear(false);
		setBooDisableEdit(false);
		}catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		}
	}

	public void saveDataTableRecods() {
		if (remittanceModeMasterDTList.isEmpty()) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		} else {
			try {
				for (RemittanceModeMasterDataTable remittanceMasterDTObj : remittanceModeMasterDTList) {
					RemittanceModeMaster remittanceModeMaster = new RemittanceModeMaster();
					remittanceModeMaster.setRemittanceModeId(remittanceMasterDTObj.getPkRmtnce());
					ServiceMaster serviceMaster = new ServiceMaster();
					serviceMaster.setServiceId(remittanceMasterDTObj.getServiceId());
					remittanceModeMaster.setRemittance(remittanceMasterDTObj.getRemitance());
					remittanceModeMaster.setServiceMaster(serviceMaster);
					/* save Functionality added 06/03/15 @Koti */
					if(remittanceMasterDTObj.getPkRmtnce()!=null){
					remittanceModeMaster.setCreatedBy(remittanceMasterDTObj.getCreatedBy());
					remittanceModeMaster.setCreatedDate(remittanceMasterDTObj.getCreatedDate());
					remittanceModeMaster.setModifiedBy(session.getUserName());
					remittanceModeMaster.setModifiedDate(new Date());
					}else{
						remittanceModeMaster.setCreatedBy(remittanceMasterDTObj.getCreatedBy());
						remittanceModeMaster.setCreatedDate(remittanceMasterDTObj.getCreatedDate());
						
					}
					remittanceModeMaster.setApprovedBy(remittanceMasterDTObj.getApprovedBy());
					remittanceModeMaster.setApprovedDate(remittanceMasterDTObj.getApprovedDate());
					remittanceModeMaster.setIsActive(remittanceMasterDTObj.getIsActive());
				 
					if(remittanceMasterDTObj.getBooCheckUpdate()){
					remittanceModeService.saveRecord(remittanceModeMaster);
					}
					// Eng
					RemittanceModeDescription remittanceModeDescription = new RemittanceModeDescription();
					LanguageType languageType = new LanguageType();
					languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
					remittanceModeDescription.setLanguageType(languageType);
					remittanceModeDescription.setRemittanceModeDescId(remittanceMasterDTObj.getPkRemDesc1());
					remittanceModeDescription.setRemittanceDescription(remittanceMasterDTObj.getEnglishRemittanceMode());
					remittanceModeDescription.setRemittanceModeMaster(remittanceModeMaster);
					if(remittanceMasterDTObj.getBooCheckUpdate()){
					remittanceModeService.saveRecordForDesc(remittanceModeDescription);
					}
					// Arabic
					RemittanceModeDescription remittanceModeDescription2 = new RemittanceModeDescription();
					LanguageType languageType2 = new LanguageType();
					languageType2.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
					remittanceModeDescription2.setLanguageType(languageType2);
					remittanceModeDescription2.setRemittanceModeDescId(remittanceMasterDTObj.getPkRemDesc2());
					remittanceModeDescription2.setRemittanceDescription(remittanceMasterDTObj.getArabicRemittanceMode());
					remittanceModeDescription2.setRemittanceModeMaster(remittanceModeMaster);
					if(remittanceMasterDTObj.getBooCheckUpdate()){
					remittanceModeService.saveRecordForDesc(remittanceModeDescription2);
					}

				}
			} catch (Exception exception) {
				  setErrmsg(exception.getMessage());
					log.info("exception.getMessage():::::::::::::::::::::::::::::::::"+exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
			} 
			setBooDisableClear(false);
			RequestContext.getCurrentInstance().execute("complete.show();");
		}
	}

	private List<RemittanceModeMaster> mstList = new ArrayList<RemittanceModeMaster>();
	private List<RemittanceModeDescription> desList = new ArrayList<RemittanceModeDescription>();

	public List<RemittanceModeMaster> getMstList() {
		return mstList;
	}

	public void setMstList(List<RemittanceModeMaster> mstList) {
		this.mstList = mstList;
	}

	public List<RemittanceModeDescription> getDesList() {
		return desList;
	}

	public void setDesList(List<RemittanceModeDescription> desList) {
		this.desList = desList;
	}

	public void checkRemittance() {
	 try{
		setSubmit(false);
	// hideSubmtButton();
		autopopDesc();
		setRemitancePk(null);
		setEnglishRemittanceMode(null);
		setArabicRemittanceMode(null);
		setRemitaDescEngPk(null);
		setRemitaDescArabicPk(null);
		 
	
		mstList = remittanceModeService.getRemitCheck(getRemitance());
		if (mstList.size() > 0) {
			setRemitance(null);
			RequestContext.getCurrentInstance().execute("codeExist.show();");	
		
			
			/*
			if (mstList.get(0).getIsActive().endsWith(Constants.Yes)) {
				//setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				setRenderEditButton(true);
				setCheckSave(false);
			} else if (mstList.get(0).getIsActive().endsWith(Constants.D)) {
				//setDynamicLabelForActivateDeactivate("Activate");
				setRenderEditButton(true);
				setCheckSave(false);
			}
			setCreatedBy(mstList.get(0).getCreatedBy());
			setCreatedDate(mstList.get(0).getCreatedDate());
			setBooCheckUpdate(true);

			desList = remittanceModeService.getRemittancDesc(mstList.get(0).getRemittanceModeId());
			setRemitancePk(mstList.get(0).getRemittanceModeId());
			for (RemittanceModeDescription remdesc : desList) {
				
				if (remdesc.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID)) {
					setRemitaDescEngPk(remdesc.getRemittanceModeDescId());
					setEnglishRemittanceMode(remdesc.getRemittanceDescription());
				} else {
					setRemitaDescArabicPk(remdesc.getRemittanceModeDescId());
					setArabicRemittanceMode(remdesc.getRemittanceDescription());
				}
			}

		*/}

		if (remittanceModeMasterDTList.size() > 0) {
			for (RemittanceModeMasterDataTable reMoMasObj : remittanceModeMasterDTList) {
				if (reMoMasObj.getRemitance().equalsIgnoreCase(getRemitance())) {
					setBooRemmittanceCheckForDataTable(true);
					/*
					 * setBooRemmittanceCheck(false); setRemitance(null);
					 * setServiceId(null); setEnglishRemittanceMode(null);
					 * setArabicRemittanceMode(null);
					 */
					/*
					 * setRemitancePk(null); setRemitaDescEngPk(null);
					 * setRemitaDescArabicPk(null);
					 */
					/*
					 * RequestContext.getCurrentInstance().execute(
					 * "datatable.show()");
					 */
					return;
				} else {
					setBooRemmittanceCheckForDataTable(false);
				}
			}
		}
	 }catch(Exception exception){
	   log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
		setErrmsg(exception.getMessage());
		RequestContext.getCurrentInstance().execute("exception.show();");
		return ;
	 }
		/*
		 * List<RemittanceModeMaster> matchRemittance = new
		 * ArrayList<RemittanceModeMaster>();
		 * matchRemittance.addAll(remittanceModeService
		 * .getRemittanceCheck(getRemitance()));
		 * 
		 * if (matchRemittance.size()>0) { setBooRemmittanceCheck(true);
		 * setRemitance(null); } else { setBooRemmittanceCheck(false); }
		 */
	}

	/*
	 * 
	 */

	public List<String> autoCompleteData(String qry){
		  List<String> list=null;
		  try{
		  if(getServiceId() == null){
			    RequestContext.getCurrentInstance().execute("service.show();");
			    return null;
		  }
		if (qry.length() > 0) {
			  list=remittanceModeService.getRemittancecodelist(qry,getServiceId());
		} else {

			return null;
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			//RequestContext.getCurrentInstance().execute("exception.show();");
			//return list;
		  }
		  return list;
	}

	private List<RemittanceModeMaster> remitancelist = new ArrayList<RemittanceModeMaster>();
	private List<RemittanceModeDescription> remittanceDesc = new ArrayList<RemittanceModeDescription>();

	public void autopopDesc() {
		  try{
		
		 
		remitancelist = remittanceModeService.getRemittanceDesc(getRemitance().trim(), getServiceId());
		// setIsActive(remitancelist.get(0).getIsActive());
		

		if (remitancelist.size() > 0) {
			if (remitancelist.get(0).getIsActive().equalsIgnoreCase(Constants.Yes)) {
				setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				setRenderEditButton(true);
				// setCheckSave(false);
			} else if (remitancelist.get(0).getIsActive().equalsIgnoreCase(Constants.D)) {
				setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				setRenderEditButton(true);
				// setCheckSave(false);
			} else if (remitancelist.get(0).getIsActive().equalsIgnoreCase(Constants.U)) {
				//setDynamicLabelForActivateDeactivate(Constants.DELETE);
				setRenderEditButton(true);
			}
			setRemitancePk(remitancelist.get(0).getRemittanceModeId());

			remittanceDesc = remittanceModeService.getRemittancDesc(remitancelist.get(0).getRemittanceModeId());
			setIsActive(remitancelist.get(0).getIsActive());
			setCreatedBy(remitancelist.get(0).getCreatedBy());
			setCreatedDate(remitancelist.get(0).getCreatedDate());
			//setApprovedBy(remitancelist.get(0).getApprovedBy());
			//setApprovedDate(remitancelist.get(0).getApprovedDate());

			if (remittanceDesc.size() > 0) {
				for (RemittanceModeDescription remittance : remittanceDesc) {
					int res = remittance.getLanguageType().getLanguageId().intValueExact();

					if (res == 1) {
						setRemitaDescEngPk(remittance.getRemittanceModeDescId());
						setEnglishRemittanceMode(remittance.getRemittanceDescription());

					} else {
						setArabicRemittanceMode(remittance.getRemittanceDescription());
						setRemitaDescArabicPk(remittance.getRemittanceModeDescId());

					}

				}

			} else {
				/*
				 * setArabicRemittanceMode(null); setRemitaDescArabicPk(null);
				 * setRemitaDescEngPk(null); setEnglishRemittanceMode(null);
				 */
			}
		} else {
			setRemitancePk(null);
			setRemserviceId(null);
			setEnglishRemittanceMode(null);
			setArabicRemittanceMode(null);
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		  }

	}

	private BigDecimal remitancePk;
	private BigDecimal remitaDescEngPk;
	private BigDecimal remitaDescArabicPk;
	private BigDecimal remserviceId;

	public BigDecimal getRemserviceId() {
		return remserviceId;
	}

	public void setRemserviceId(BigDecimal remserviceId) {
		this.remserviceId = remserviceId;
	}

	public BigDecimal getRemitancePk() {
		return remitancePk;
	}

	public void setRemitancePk(BigDecimal remitancePk) {
		this.remitancePk = remitancePk;
	}

	public BigDecimal getRemitaDescEngPk() {
		return remitaDescEngPk;
	}

	public void setRemitaDescEngPk(BigDecimal remitaDescEngPk) {
		this.remitaDescEngPk = remitaDescEngPk;
	}

	public BigDecimal getRemitaDescArabicPk() {
		return remitaDescArabicPk;
	}

	public void setRemitaDescArabicPk(BigDecimal remitaDescArabicPk) {
		this.remitaDescArabicPk = remitaDescArabicPk;
	}

	/*
	 * 
	 * public void update(){
	 * 
	 * if(remittanceModeMasterDTList.isEmpty()){ RequestContext context =
	 * RequestContext.getCurrentInstance();
	 * context.execute("dataNotFund.show();"); }else{
	 * 
	 * for(RemittanceModeMasterDataTable
	 * remittanceMasterDTObj:remittanceModeMasterDTList){ RemittanceModeMaster
	 * remittanceModeMaster=new RemittanceModeMaster();
	 * 
	 * ServiceMaster serviceMaster=new ServiceMaster();
	 * serviceMaster.setServiceId(remittanceMasterDTObj.getServiceId());
	 * remittanceModeMaster.setServiceMaster(serviceMaster);
	 * ///////////////////////////////////
	 * remittanceModeMaster.setRemittanceModeId(getRemitancePk());
	 * System.out.println("PKKKKKKKKKKKKKKKKKKKKKKKKKKK"+getRemitancePk());
	 * remittanceModeMaster.setRemittance(remittanceMasterDTObj.getRemitance());
	 * //remittanceModeMaster.setRemittanceDesEnglish(remittanceMasterDTObj.
	 * getEnglishRemittanceMode());
	 * remittanceModeMaster.setCreatedBy(session.getUserName());
	 * remittanceModeMaster.setCreatedDate(new Date());
	 * remittanceModeMaster.setIsActive("Y");
	 * 
	 * remittanceModeService.saveRecord(remittanceModeMaster);
	 * 
	 * 
	 * RemittanceModeDescription remittanceModeDescription=new
	 * RemittanceModeDescription(); LanguageType languageType=new
	 * LanguageType(); languageType.setLanguageId(new BigDecimal(1));
	 * ///////////////////////////////
	 * remittanceModeDescription.setRemittanceModeDescId(getRemitaDescEngPk());
	 * 
	 * remittanceModeDescription.setLanguageType(languageType);
	 * remittanceModeDescription
	 * .setRemittanceDescription(remittanceMasterDTObj.getEnglishRemittanceMode
	 * ());
	 * remittanceModeDescription.setRemittanceModeMaster(remittanceModeMaster);
	 * remittanceModeService.saveRecordForDesc(remittanceModeDescription);
	 * 
	 * RemittanceModeDescription remittanceModeDescription2=new
	 * RemittanceModeDescription(); LanguageType languageType2=new
	 * LanguageType(); languageType2.setLanguageId(new BigDecimal(2));
	 * ///////////////////////////
	 * remittanceModeDescription2.setRemittanceModeDescId
	 * (getRemitaDescArabicPk());
	 * 
	 * remittanceModeDescription2.setLanguageType(languageType2);
	 * remittanceModeDescription2
	 * .setRemittanceDescription(remittanceMasterDTObj.
	 * getArabicRemittanceMode());
	 * remittanceModeDescription2.setRemittanceModeMaster(remittanceModeMaster);
	 * remittanceModeService.saveRecordForDesc(remittanceModeDescription2);
	 * 
	 * RemittanceModeDescription remittanceModeDescription2=new
	 * RemittanceModeDescription(); LanguageType languageType2=new
	 * LanguageType(); languageType2.setLanguageId(session.getLanguageId());
	 * remittanceModeDescription.setLanguageType(languageType);
	 * 
	 * remittanceModeDescription.setLocalRemittanceDescription(remittanceMasterDTObj
	 * .getArabicRemittanceMode());
	 * remittanceModeDescription.setEnglishRemittanceDescription
	 * (remittanceMasterDTObj.getEnglishRemittanceMode());
	 * remittanceModeDescription.setRemittanceModeMaster(remittanceModeMaster);
	 * remittanceModeService.saveRecordForDesc(remittanceModeDescription);
	 * 
	 * }
	 * 
	 * 
	 * RequestContext.getCurrentInstance().execute("updatesucces.show();"); } }
	 */

	@Autowired
	ServiceCodeMasterService serviceMasterService;

	public void view() {
		  try{
		List<RemittanceModeMaster> remittanceList = remittanceModeService.viewAllRemittanceMode();
		if (remittanceList != null) {
			for (RemittanceModeMaster remittanceMode : remittanceList) {
				RemittanceModeMasterDataTable remitDt = new RemittanceModeMasterDataTable();
				remitDt.setRemitance(remittanceMode.getRemittance());
				remitDt.setServiceId(remittanceMode.getServiceMaster().getServiceId());

				remitDt.setServiceName(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"), remittanceMode.getServiceMaster().getServiceId()).get(0).getLocalServiceDescription());

				// remitDt.setServiceName(serviceMasterService.LocalServiceDescription(remittanceMoDes.getRemittanceModeMaster().getServiceMaster().getServiceId()).get(0).getLocalServiceDescription());
				// remitDt.setServiceName(remittanceMoDes.getRemittanceModeMaster().getServiceMaster().getServiceMasterDesc().toString());

				if (remittanceMode.getIsActive().equalsIgnoreCase(Constants.Yes)) {
					remitDt.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					remitDt.setApprovedBy(remittanceMode.getApprovedBy());
					remitDt.setApprovedDate(remittanceMode.getApprovedDate());
					remitDt.setRenderEditButton(true);
					remitDt.setCheckSave(false);
					remitDt.setBooCheckDelete(false);

				} else if (remittanceMode.getIsActive().equalsIgnoreCase(Constants.D)) {
					remitDt.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					remitDt.setRenderEditButton(true);
					remitDt.setCheckSave(false);
					
				} else if (remittanceMode.getIsActive().equalsIgnoreCase(Constants.U)&&remittanceMode.getModifiedBy()==null&&remittanceMode.getModifiedDate()==null&&remittanceMode.getApprovedBy()==null&&remittanceMode.getRemarks()==null) {
					remitDt.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					remitDt.setRenderEditButton(true);
					remitDt.setCheckSave(false);
					remitDt.setBooCheckDelete(false);

				}
				else{remitDt.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					remitDt.setRenderEditButton(true);
					remitDt.setCheckSave(false);
					remitDt.setBooCheckDelete(false);
				}
				remitDt.setBooDisableEdit(false);
				setBooDisableClear(false);
				remitDt.setBooCheckUpdate(false);
				remitDt.setIsActive(remittanceMode.getIsActive());
				remitDt.setCreatedBy(remittanceMode.getCreatedBy());
				remitDt.setModifiedBy(remittanceMode.getModifiedBy());
				remitDt.setModifiedDate(remittanceMode.getModifiedDate());

				remitDt.setCreatedDate(remittanceMode.getCreatedDate());
				remitDt.setPkRmtnce(remittanceMode.getRemittanceModeId());
				
				List<RemittanceModeDescription> remittanceModeDescriptionList = remittanceModeService.viewRemittanceDescBasedOnRemittanceId(remittanceMode.getRemittanceModeId());
				
				if(remittanceModeDescriptionList != null){
					for (RemittanceModeDescription  remittanceModeDescription  : remittanceModeDescriptionList) {
						if (remittanceModeDescription.getLanguageType().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))) {
							remitDt.setPkRemDesc1(remittanceModeDescription.getRemittanceModeDescId());
							remitDt.setEnglishRemittanceMode(remittanceModeDescription.getRemittanceDescription());

						} else if (remittanceModeDescription.getLanguageType().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
							remitDt.setPkRemDesc2(remittanceModeDescription.getRemittanceModeDescId());
							remitDt.setArabicRemittanceMode(remittanceModeDescription.getRemittanceDescription());

						}
					}
					
				}
				
				
		
				remitDTList.add(remitDt);
				
			}
			if (remitDTList.size() > 0) {
				addRecordsToDataTable();
				remitDTList.clear();
			} else {
				RequestContext.getCurrentInstance().execute("exist.show();");
			}
		} else {
			RequestContext.getCurrentInstance().execute("norecord.show();");
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		  }

	}

	 
	public List<RemittanceModeDescription> getRemittanceDesc() {
		return remittanceDesc;
	}

	public void setRemittanceDesc(List<RemittanceModeDescription> remittanceDesc) {
		this.remittanceDesc = remittanceDesc;
	}

	public List<RemittanceModeMaster> getRemitancelist() {
		return remitancelist;
	}

	public void setRemitancelist(List<RemittanceModeMaster> remitancelist) {
		this.remitancelist = remitancelist;
	}

	// app started
	public void remitPageApproval() {

		try {
			fetchData();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "RemittanceModeMasterApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RemittanceModeMasterApproval.xhtml");
		} catch (Exception exception) {
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		}
	}

	private List<RemittanceModeMaster> remiMstList = new ArrayList<RemittanceModeMaster>();
	private List<RemittanceModeMasterDataTable> remiModDT = new ArrayList<RemittanceModeMasterDataTable>();

	public List<RemittanceModeMaster> getRemiMstList() {
		return remiMstList;
	}

	public void setRemiMstList(List<RemittanceModeMaster> remiMstList) {
		this.remiMstList = remiMstList;
	}

	public List<RemittanceModeMasterDataTable> getRemiModDT() {

		return remiModDT;
	}

	public void fetchData() {
		  try{
		remiModDT.clear();
		remiMstList = remittanceModeService.getRemittanceModeApproval();
		for (RemittanceModeMaster remit : remiMstList) {
			RemittanceModeMasterDataTable dataTable = new RemittanceModeMasterDataTable();
			try {
				dataTable.setPkRmtnce(remit.getRemittanceModeId());
				dataTable.setServiceId(remit.getServiceMaster().getServiceId());
				/* remit.getServiceMaster().getServiceDescription() */
				// dataTable.setServiceName(remit.getServiceMaster().getServiceMasterDesc().toString());
				dataTable.setServiceName(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1"), remit.getServiceMaster().getServiceId()).get(0).getLocalServiceDescription());
				dataTable.setRemitance(remit.getRemittance());
				try{
				String englishName = remittanceModeService.getEngRemitDes(remit.getRemittanceModeId());
				String arabicName = remittanceModeService.getArbRemiDes(remit.getRemittanceModeId());

				dataTable.setEnglishRemittanceMode(englishName);

				dataTable.setArabicRemittanceMode(arabicName);
				}catch(Exception e){
					
				}
				dataTable.setCreatedBy(remit.getCreatedBy());
				dataTable.setCreatedDate(remit.getCreatedDate());
				dataTable.setModifiedBy(remit.getModifiedBy());
				dataTable.setModifiedDate(remit.getModifiedDate());
				dataTable.setIsActive(remit.getIsActive());
				dataTable.setPkRmtnce(remit.getRemittanceModeId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			remiModDT.add(dataTable);
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		  }
	}

	public void setRemiModDT(List<RemittanceModeMasterDataTable> remiModDT) {
		this.remiModDT = remiModDT;
	}

	public void navigationEdit(RemittanceModeMasterDataTable datatable) {
		  try{
		if((datatable.getModifiedBy()==null?datatable.getCreatedBy():datatable.getModifiedBy()).equalsIgnoreCase( session.getUserName())){
			RequestContext.getCurrentInstance().execute("username.show();");
		} else {
			setBooRenderDataTable(false);
			setBooRenderSaveExit(false);

			setBooReadonly(true);
			setBoohideButton(false);
			setBoohideSecod(true);
			setRemitance(datatable.getRemitance());
			setRemitancePk(datatable.getPkRmtnce());
			setServiceId(datatable.getServiceId());
			try{
			setEnglishRemittanceMode(remittanceModeService.getEngRemitDes(datatable.getPkRmtnce()));
			setArabicRemittanceMode(remittanceModeService.getArbRemiDes(datatable.getPkRmtnce()));
			}catch(NullPointerException ne){
				ne.printStackTrace();
			}
			setCreatedBy(datatable.getCreatedBy());
			setCreatedDate(datatable.getCreatedDate());
			
			
			// setIsActive(datatable.getIsActive());
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/RemittanceModeMaster.xhtml");
			} catch (IOException exception) {
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrmsg(exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return ;
			}
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		  }
	}

	public void approval() {
		  try{
		remiModDT.clear();
		/* userName = remittanceModeService.getCreatedName(); */
		/*if (getCreatedBy().equalsIgnoreCase(session.getUserName())) {
			RequestContext.getCurrentInstance().execute("username.show();");
		} else {*//*
				 * RemittanceModeMaster remittanceModeMaster = new
				 * RemittanceModeMaster();
				 * remittanceModeMaster.setRemittance(getRemitance());
				 * remittanceModeMaster
				 * .setServiceMaster(getServiceMasterList().get(0)
				 * .getServiceMaster());
				 * remittanceModeMaster.setRemittanceModeId(getRemitancePk());
				 * remittanceModeMaster.setApprovedBy(session.getUserName());
				 * remittanceModeMaster.setApprovedDate(new Date());
				 * remittanceModeMaster.setCreatedBy(getCreatedBy());
				 * remittanceModeMaster.setCreatedDate(new Date()); if
				 * (getRemitancePk() != null) {
				 * remittanceModeMaster.setModifiedBy(session.getUserName());
				 * remittanceModeMaster.setModifiedDate(new Date()); }
				 * remittanceModeMaster.setIsActive("Y"); remittanceModeMaster
				 * .setRemittanceModeDescription(getRemittanceDesc());
				 * remittanceModeMaster
				 * .setRemittanceModeDescription(getRemittanceDesc());
				 * remittanceModeService.saveRecord(remittanceModeMaster);
				 */
			
			String approveMsg=remittanceModeService.approveRecord(getRemitancePk(), session.getUserName());
			if(approveMsg.equalsIgnoreCase("Success")){
			RequestContext.getCurrentInstance().execute("approv.show();");
			}else{
				RequestContext.getCurrentInstance().execute("alreadyapprov.show();");	
			}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		  }
		//}
	}
	public void clickOnOkButton(){
		try {
			fetchData();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RemittanceModeMasterApproval.xhtml");
			 
		} catch (Exception exception) {
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		}
	}
	
	public void cancelDelete() {
		if(remittanceModeMasterDTList.size() > 0){
			for (RemittanceModeMasterDataTable dataTable : remittanceModeMasterDTList) {
				if(dataTable.getBooCheckDelete().equals(true)){
					dataTable.setBooCheckDelete(false);
				}
			}
		}
			
			
	}

	public void cancel() {
		remiModDT.clear();
		try {
			fetchData();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RemittanceModeMasterApproval.xhtml");
			/*
			 * FacesContext.getCurrentInstance().getExternalContext()
			 * .redirect("../registration/employeehome.xhtml");
			 */
		} catch (Exception exception) {
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		}
	}

	public void clickOnOk() {
		remiModDT.clear();
		try {
			fetchData();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RemittanceModeMasterApproval.xhtml");
		} catch (Exception exception) {
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		}
	}

	private String checkData;
	private boolean boohideButton = true;
	private boolean boohideSecod = false;
	private boolean booReadonly = false;

	private String createdBy = null;
	private Date createdDate = null;
	private String modifiedBy = null;
	private Date modifiedDate = null;
	private String isActive = null;
	private String userName;

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public String getUserName() {
		return userName;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCheckData() {
		return checkData;
	}

	public boolean isBoohideButton() {
		return boohideButton;
	}

	public boolean isBoohideSecod() {
		return boohideSecod;
	}

	public boolean isBooReadonly() {
		return booReadonly;
	}

	public void setCheckData(String checkData) {
		this.checkData = checkData;
	}

	public void setBoohideButton(boolean boohideButton) {
		this.boohideButton = boohideButton;
	}

	public void setBoohideSecod(boolean boohideSecod) {
		this.boohideSecod = boohideSecod;
	}

	public void setBooReadonly(boolean booReadonly) {
		this.booReadonly = booReadonly;
	}

	public void getAlldetailstoList() {
		  try{
		if (remittanceModeMasterDTList.size() != 0) {
			for (RemittanceModeMasterDataTable RemitDtObj : remittanceModeMasterDTList) {
				if (RemitDtObj.getRemitance().equals(getRemitance())) {
					setServiceId(null);
					setRemitance(null);
					setEnglishRemittanceMode(null);
					setArabicRemittanceMode(null);
					RequestContext.getCurrentInstance().execute("datatable.show();");
					setRemitancePk(null);
				}
			}
		}
		if (getRemitance() != null) {
			addRecordsToDataTable();
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		  }
	}

	private String dynamicLabelForActivateDeactivate;

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	private Boolean renderEditButton = false;

	public Boolean getRenderEditButton() {
		return renderEditButton;
	}

	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}

	private Boolean checkSave = false;

	public Boolean getCheckSave() {
		return checkSave;
	}

	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
	}

	private Boolean submit = false;

	public Boolean getSubmit() {
		return submit;
	}

	public void setSubmit(Boolean submit) {
		this.submit = submit;
	}

	public void hideSubmtButton() {
		setSubmit(true);
	}

	private String remarks;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void clickOnOKApproval() throws Exception{
		remitDtObj.setRemarks(getRemarks());
		delete(remitDtObj);
		remittanceModeMasterDTList.clear();
		view();
		remittanceModeMasterDTList.addAll(remitDTList);
		try {
			setRemarks(null);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/RemittanceModeMaster.xhtml");
		} catch (Exception exception) {
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		}

	}

	public void cancelFromActivation() {

		setRemarks(null);
		try {
			/*
			 * FacesContext .getCurrentInstance() .getExternalContext()
			 * .redirect( "../remittance/RemittanceModeMasterApproval.xhtml");
			 */
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/RemittanceModeMaster.xhtml");
		} catch (Exception exception) {
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return ;
		}
	}

	private Date approvedDate;
	private String errmsg;

	RemittanceModeMasterDataTable remitDtObj = new RemittanceModeMasterDataTable();

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	private String approvedBy;

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Boolean getBooDisableEdit() {
		return booDisableEdit;
	}

	public void setBooDisableEdit(Boolean booDisableEdit) {
		this.booDisableEdit = booDisableEdit;
	}

	public Boolean getBooDisableClear() {
		return booDisableClear;
	}

	public void setBooDisableClear(Boolean booDisableClear) {
		this.booDisableClear = booDisableClear;
	}

	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}

	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}

	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}

	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}
	
	
	/*public void checkRemittanceCode(){
		if( getRemitance()!=null){
		List<RemittanceModeDescription> listRemit=remittanceModeService.getCheckRemittCode( getRemitance());
	if(listRemit.size()>0){
		
		for(RemittanceModeDescription remitDesc:listRemit){
			setRemitancePk(remitDesc.getRemittanceModeMaster().getRemittanceModeId());
			setRemitance(remitDesc.getRemittanceModeMaster().getRemittance());
			 setCreatedBy(remitDesc.getRemittanceModeMaster().getCreatedBy() );
			 setCreatedDate(remitDesc.getRemittanceModeMaster().getCreatedDate() );
			if(remitDesc.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID.toString())){
				setRemitaDescEngPk(remitDesc.getRemittanceModeDescId() );
				
			}else{
				 setRemitaDescArabicPk(remitDesc.getRemittanceModeDescId());
				
			}
		}
	}else{
		setRemitancePk(null);
		setRemitaDescEngPk(null);
		setRemitaDescArabicPk( null);
	}
		}
	}*/
	 
	  public String getErrmsg() {
		    return errmsg;
	  }

	  public void setErrmsg(String errmsg) {
		    this.errmsg = errmsg;
	  }

public void checkRelationcodeisNumber(FacesContext context, UIComponent component, Object value) {
		try {
		BigDecimal val = new BigDecimal(value.toString());
		@SuppressWarnings("unused")
		boolean isNUmber = CollectionUtil.isIntegerValue(val);
		} catch (Exception e) {
		FacesMessage msg = new FacesMessage(" Relations Code should be number", " Relations Code should be number");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(msg);
		}
		}
}
