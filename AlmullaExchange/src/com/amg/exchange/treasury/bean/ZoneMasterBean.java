 package com.amg.exchange.treasury.bean;

import java.io.IOException;
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

import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.treasury.model.Zone;
import com.amg.exchange.treasury.model.ZoneMasterDesc;
import com.amg.exchange.treasury.service.IZoneMasterService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("zoneMasterBean")
@Scope("session")
public class ZoneMasterBean<T> implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER=Logger.getLogger(ZoneMasterBean.class);
	private String zoneCode;
	private String zoneDescription;
	private String zoneDescriptionLocal;
	private BigDecimal zoneId;
	private BigDecimal zoneMasterDescId;
	private Boolean booRenderSaveExit = false;
	private Boolean booRenderDataTable = false;
	private BigDecimal zoneIdpk;
	private BigDecimal zoneDescPk;
	private BigDecimal zoneDescLocalPk;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String approvedBy;
	private Date approvedDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String dynamicLabelForActivateDeactivate;
	private boolean renderEditButton;
	private String remarks;
	private Boolean booReadonly=false;
	private Boolean boohideButton=false;
	private Boolean boohideSecondButton=false;
	private Boolean booRenderApprove=false;
	private Boolean hideSubmitButton=false;
	private Boolean clearPanel=false;
	private Boolean booEditButton;
	private Boolean submit=false;
	private Boolean submitPanel;
	
	private String errorMessage;
	private List<ZoneMasterDataTable> zoneMasterDTList = new CopyOnWriteArrayList<ZoneMasterDataTable>();
	private List<ZoneMasterDataTable> zoneMasterDTNewList = new CopyOnWriteArrayList<ZoneMasterDataTable>();
	private SessionStateManage session = new SessionStateManage();
	private List<Zone> zones = new ArrayList<Zone>();
	private List<ZoneMasterDesc> zoneDescriptionList = new ArrayList<ZoneMasterDesc>();
	Zone zone = new Zone();
	SessionStateManage sessinstate = new SessionStateManage();
	@Autowired
	IZoneMasterService zoneMasterService;
	
	@Autowired
	IGeneralService<T> generalService;

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getZoneDescription() {
		return zoneDescription;
	}

	public void setZoneDescription(String zoneDescription) {
		this.zoneDescription = zoneDescription;
	}

	public String getZoneDescriptionLocal() {
		return zoneDescriptionLocal;
	}

	public void setZoneDescriptionLocal(String zoneDescriptionLocal) {
		this.zoneDescriptionLocal = zoneDescriptionLocal;
	}

	public BigDecimal getZoneId() {
		return zoneId;
	}

	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}

	public BigDecimal getZoneMasterDescId() {
		return zoneMasterDescId;
	}

	public void setZoneMasterDescId(BigDecimal zoneMasterDescId) {
		this.zoneMasterDescId = zoneMasterDescId;
	}

	public List<ZoneMasterDataTable> getZoneMasterDTList() {
		return zoneMasterDTList;
	}

	public void setZoneMasterDTList(List<ZoneMasterDataTable> zoneMasterDTList) {
		this.zoneMasterDTList = zoneMasterDTList;
	}

	public List<ZoneMasterDataTable> getZoneMasterDTNewList() {
		return zoneMasterDTNewList;
	}

	public void setZoneMasterDTNewList(List<ZoneMasterDataTable> zoneMasterDTNewList) {
		this.zoneMasterDTNewList = zoneMasterDTNewList;
	}

	public Boolean getBooRenderSaveExit() {
		return booRenderSaveExit;
	}

	public void setBooRenderSaveExit(Boolean booRenderSaveExit) {
		this.booRenderSaveExit = booRenderSaveExit;
	}

	public BigDecimal getZoneIdpk() {
		return zoneIdpk;
	}

	public void setZoneIdpk(BigDecimal zoneIdpk) {
		this.zoneIdpk = zoneIdpk;
	}

	public BigDecimal getZoneDescPk() {
		return zoneDescPk;
	}

	public void setZoneDescPk(BigDecimal zoneDescPk) {
		this.zoneDescPk = zoneDescPk;
	}

	public BigDecimal getZoneDescLocalPk() {
		return zoneDescLocalPk;
	}

	public void setZoneDescLocalPk(BigDecimal zoneDescLocalPk) {
		this.zoneDescLocalPk = zoneDescLocalPk;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public List<Zone> getZones() {
		return zones;
	}

	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}

	public List<ZoneMasterDesc> getZoneDescriptionList() {
		return zoneDescriptionList;
	}

	public void setZoneDescriptionList(List<ZoneMasterDesc> zoneDescriptionList) {
		this.zoneDescriptionList = zoneDescriptionList;
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

	

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isRenderEditButton() {
		return renderEditButton;
	}

	public void setRenderEditButton(boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

	public Boolean getBooReadonly() {
		return booReadonly;
	}

	public void setBooReadonly(Boolean booReadonly) {
		this.booReadonly = booReadonly;
	}

	
	public Boolean getBooRenderApprove() {
		return booRenderApprove;
	}

	public void setBooRenderApprove(Boolean booRenderApprove) {
		this.booRenderApprove = booRenderApprove;
	}

	public Boolean getBoohideButton() {
		return boohideButton;
	}

	public void setBoohideButton(Boolean boohideButton) {
		this.boohideButton = boohideButton;
	}

	
	public Boolean getBoohideSecondButton() {
		return boohideSecondButton;
	}

	public void setBoohideSecondButton(Boolean boohideSecondButton) {
		this.boohideSecondButton = boohideSecondButton;
	}

	
	public Boolean getHideSubmitButton() {
		return hideSubmitButton;
	}

	public void setHideSubmitButton(Boolean hideSubmitButton) {
		this.hideSubmitButton = hideSubmitButton;
	}
		

	public Boolean getBooEditButton() {
		return booEditButton;
	}

	public void setBooEditButton(Boolean booEditButton) {
		this.booEditButton = booEditButton;
	}

	public Boolean getClearPanel() {
		return clearPanel;
	}

	public void setClearPanel(Boolean clearPanel) {
		this.clearPanel = clearPanel;
	}
	public Boolean getSubmit() {
		return submit;
	}

	public void setSubmit(Boolean submit) {
		this.submit = submit;
	}
	

	public Boolean getSubmitPanel() {
		return submitPanel;
	}

	public void setSubmitPanel(Boolean submitPanel) {
		this.submitPanel = submitPanel;
	}
	
	public void addRecordsToDataTable() {
	LOGGER.info("Entering into addRecordsToDataTable method");
	setSubmitPanel(false);
	setClearPanel(false);
	setBooEditButton(false);
		if (zoneMasterDTNewList.size() == 0) {
			ZoneMasterDataTable zoneMasterDTObj = new ZoneMasterDataTable();
			zoneMasterDTObj.setZoneCode(getZoneCode());
			zoneMasterDTObj.setZoneIdpk(getZoneIdpk());
			zoneMasterDTObj.setZoneDescPk(getZoneDescPk());
			zoneMasterDTObj.setZoneDescLocalPk(getZoneDescLocalPk());
			zoneMasterDTObj.setZoneDescription(getZoneDescription());
			zoneMasterDTObj.setZoneDescriptionLocal(getZoneDescriptionLocal());

			
			if (getZoneIdpk() != null) {
				
				if(getModifiedBy() != null && getModifiedDate() != null && getIsActive().equals(Constants.U)){
					zoneMasterDTObj.setCreatedBy(getCreatedBy());
					zoneMasterDTObj.setCreatedDate(getCreatedDate());
					zoneMasterDTObj.setRenderEditButton(true);
					zoneMasterDTObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					zoneMasterDTObj.setIsActive(Constants.U);
					zoneMasterDTObj.setModifiedBy(session.getUserName());
					zoneMasterDTObj.setModifiedDate(new Date());
				}else{
					
					System.out.println(""+getRemarks());
					System.out.println(""+getApprovedBy());
					System.out.println(""+getApprovedDate());
					if(getApprovedBy()!=null && getApprovedDate() != null && getRemarks() != null && getIsActive().equals(Constants.Yes)){
						zoneMasterDTObj.setCreatedBy(getCreatedBy());
						zoneMasterDTObj.setCreatedDate(getCreatedDate());
						zoneMasterDTObj.setRenderEditButton(true);
						zoneMasterDTObj.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
						zoneMasterDTObj.setIsActive(Constants.U);
						zoneMasterDTObj.setModifiedBy(session.getUserName());
						zoneMasterDTObj.setModifiedDate(new Date());
					}else{
						zoneMasterDTObj.setCreatedBy(getCreatedBy());
						zoneMasterDTObj.setCreatedDate(getCreatedDate());
						zoneMasterDTObj.setRenderEditButton(true);
						zoneMasterDTObj.setDynamicLabelForActivateDeactivate(Constants.DELETE);
						zoneMasterDTObj.setIsActive(Constants.U);
						zoneMasterDTObj.setModifiedBy(session.getUserName());
						zoneMasterDTObj.setModifiedDate(new Date());
					}
					
				}
				
				
			}
				 else {
				zoneMasterDTObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				zoneMasterDTObj.setRenderEditButton(true);
				zoneMasterDTObj.setCreatedBy(session.getUserName());
				zoneMasterDTObj.setCreatedDate(new Date());
				zoneMasterDTObj.setIsActive(Constants.U);
			}
			zoneMasterDTList.add(zoneMasterDTObj);
		}
		if (zoneMasterDTList.size() > 0) {
			if (zoneMasterDTNewList.size() > 0) {
				for (ZoneMasterDataTable zoneMasterDTabel : zoneMasterDTList) {
					for (ZoneMasterDataTable zoneMasterDT : zoneMasterDTNewList) {
						if (zoneMasterDTabel.getZoneCode().equals(zoneMasterDT.getZoneCode())) {
							zoneMasterDTNewList.remove(zoneMasterDT);
						}
					}
				}
			}
			zoneMasterDTList.addAll(zoneMasterDTNewList);
		} else {
			zoneMasterDTList.addAll(zoneMasterDTNewList);

		}
		clearAllFields();
		setBooRenderSaveExit(true);
		setBooRenderDataTable(true);
		LOGGER.info("Exit into addRecordsToDataTable method");
	}

	public void clearAllFields() {
		LOGGER.info("Enter into clearAllFields method");
		setZoneCode(null);
		setZoneDescription(null);
		setZoneDescriptionLocal(null);
		setZoneIdpk(null);
		setZoneDescPk(null);
		setZoneDescLocalPk(null);
		LOGGER.info("Exit into clearAllFields method");
	}

	public void editRecord(ZoneMasterDataTable zoneMasterDTObj) {
		try {
			LOGGER.info("Entering into editRecord method");
			 submitButtonHide();
			setZoneCode(zoneMasterDTObj.getZoneCode());
			setZoneDescription(zoneMasterDTObj.getZoneDescription());
			setZoneDescriptionLocal(zoneMasterDTObj.getZoneDescriptionLocal());
			setZoneIdpk(zoneMasterDTObj.getZoneIdpk());
			setZoneDescPk(zoneMasterDTObj.getZoneDescPk());
			setZoneDescLocalPk(zoneMasterDTObj.getZoneDescLocalPk());
			setDynamicLabelForActivateDeactivate(zoneMasterDTObj.getDynamicLabelForActivateDeactivate());
			setRenderEditButton(zoneMasterDTObj.getRenderEditButton());
			setIsActive(zoneMasterDTObj.getIsActive());
			setCreatedBy(zoneMasterDTObj.getCreatedBy());
			setCreatedDate(zoneMasterDTObj.getCreatedDate());
			setModifiedBy(zoneMasterDTObj.getModifiedBy());
			setModifiedDate(zoneMasterDTObj.getModifiedDate());
			setApprovedBy(zoneMasterDTObj.getApprovedBy());
			setApprovedDate(zoneMasterDTObj.getApprovedDate());
			setRemarks(zoneMasterDTObj.getRemarks());
			
			if (zoneMasterDTObj.getZoneIdpk() != null) {
				setModifiedBy(session.getUserName());
				setModifiedDate(new Date());
				
			}
			
			zoneMasterDTList.remove(zoneMasterDTObj);
			zoneMasterDTNewList.remove(zoneMasterDTObj);
			setSubmitPanel(true);
			setClearPanel(true);
			setRenderEditButton(true);
			setBooEditButton(true);
			if (zoneMasterDTList.size() <= 0) {
				setHideSubmitButton(true);
				setBooRenderSaveExit(false);
				setBooRenderDataTable(false);
				
				
			}
			LOGGER.info("Exit into editRecord method");
		}  catch (NullPointerException ne) {
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

	public void removeRecord(ZoneMasterDataTable zoneMasterDTObj) {
		LOGGER.info("Entering into removeRecord method");
		if (zoneMasterDTObj.getZoneIdpk() == null) {
			zoneMasterDTList.remove(zoneMasterDTObj);
			zoneMasterDTNewList.remove(zoneMasterDTObj);
			if (zoneMasterDTList.size() <= 0) {
				setBooRenderSaveExit(false);
				setBooRenderDataTable(false);
			}
		
		}else{
				delete(zoneMasterDTObj);
				zoneMasterDTList.clear();
				view();
				zoneMasterDTList.addAll(zoneMasterDTNewList);
			}
		LOGGER.info("Exit into removeRecord method");
	}
	
		

	public void zoneMasterDelete(ZoneMasterDataTable zoneMasterDTObj){
		
		try {
			LOGGER.info("Entering into zoneMasterDelete method");
			ZoneMasterDesc zoneMasterDesc = new ZoneMasterDesc();
		LanguageType languageType = new LanguageType();
		languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
		zoneMasterDesc.setLanguageType(languageType);
		zoneMasterDesc.setZoneDescId(zoneMasterDTObj.getZoneDescPk());
		zoneMasterDesc.setZoneDescription(zoneMasterDTObj.getZoneDescription());
		
		zoneMasterService.deleteRecordDesc(zoneMasterDesc);

		ZoneMasterDesc zoneMasterDesc2 = new ZoneMasterDesc();
		languageType.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
		zoneMasterDesc2.setLanguageType(languageType);
		zoneMasterDesc2.setZoneDescId(zoneMasterDTObj.getZoneDescLocalPk());
		zoneMasterDesc2.setZoneDescription(zoneMasterDTObj.getZoneDescriptionLocal());
		
		zoneMasterService.deleteRecordDesc(zoneMasterDesc2);
		
		Zone zone = new Zone();
		zone.setZoneCode(zoneMasterDTObj.getZoneCode());
		zone.setZoneId(zoneMasterDTObj.getZoneIdpk());
		zone.setCreatedBy(zoneMasterDTObj.getCreatedBy());
		zone.setCreatedDate(zoneMasterDTObj.getCreatedDate());

		zone.setIsactive(zoneMasterDTObj.getIsActive());
		zoneMasterService.deleteRecord(zone);
		LOGGER.info("Exit into zoneMasterDelete method");
		}  catch (NullPointerException ne) {
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
	public void delete(ZoneMasterDataTable zoneMasterDTObj) {
		try {
			LOGGER.info("Entering into delete method");
			zone.setZoneCode(zoneMasterDTObj.getZoneCode());
			zone.setZoneId(zoneMasterDTObj.getZoneIdpk());
			zone.setCreatedBy(zoneMasterDTObj.getCreatedBy());
			zone.setCreatedDate(zoneMasterDTObj.getCreatedDate());
			if (zoneMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				zone.setApprovedBy(zoneMasterDTObj.getApprovedBy());
				zone.setApprovedDate(zoneMasterDTObj.getApprovedDate());
				zone.setIsactive(Constants.U);
				zone.setRemarks(null);

			} else if (zoneMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				zone.setIsactive(Constants.D);
				zone.setModifiedBy(session.getUserName());
				zone.setModifiedDate(new Date());
				zone.setRemarks(zoneMasterDTObj.getRemarks());
			}
			
			zoneMasterService.saveRecord(zone);

			ZoneMasterDesc zoneMasterDesc = new ZoneMasterDesc();
			LanguageType languageType = new LanguageType();
			languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			zoneMasterDesc.setLanguageType(languageType);
			zoneMasterDesc.setZoneDescId(zoneMasterDTObj.getZoneDescPk());
			zoneMasterDesc.setZoneDescription(zoneMasterDTObj.getZoneDescription());
			zoneMasterDesc.setZone(zone);
			zoneMasterService.saveRecordDesc(zoneMasterDesc);

			ZoneMasterDesc zoneMasterDesc2 = new ZoneMasterDesc();
			languageType.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			zoneMasterDesc2.setLanguageType(languageType);
			zoneMasterDesc2.setZoneDescId(zoneMasterDTObj.getZoneDescLocalPk());
			zoneMasterDesc2.setZoneDescription(zoneMasterDTObj.getZoneDescriptionLocal());
			zoneMasterDesc2.setZone(zone);
			zoneMasterService.saveRecordDesc(zoneMasterDesc2);
			LOGGER.info("Exit into delete method");
		}  catch (NullPointerException ne) {
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

	public void saveDataTableRecords() {
		
		try {
			LOGGER.info("Entering into saveDataTableRecords method");
			if (zoneMasterDTList.isEmpty()) {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("dataNotFund.show();");
			} else {
				
					for (ZoneMasterDataTable zoneMasterDTObj : zoneMasterDTList) {
						Zone zone = new Zone();
						zone.setZoneCode(zoneMasterDTObj.getZoneCode());
						zone.setZoneId(zoneMasterDTObj.getZoneIdpk());
						zone.setCreatedBy(zoneMasterDTObj.getCreatedBy());
						zone.setCreatedDate(zoneMasterDTObj.getCreatedDate());

						zone.setIsactive(zoneMasterDTObj.getIsActive());
						
							zone.setModifiedBy(zoneMasterDTObj.getModifiedBy());
							zone.setModifiedDate(zoneMasterDTObj.getModifiedDate());
							zone.setRemarks(zoneMasterDTObj.getRemarks());
					
						zone.setApprovedBy(zoneMasterDTObj.getApprovedBy());
						zone.setApprovedDate(zoneMasterDTObj.getApprovedDate());

						zoneMasterService.saveRecord(zone);

						ZoneMasterDesc zoneMasterDesc = new ZoneMasterDesc();
						LanguageType languageType = new LanguageType();
						languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
						zoneMasterDesc.setLanguageType(languageType);
						zoneMasterDesc.setZoneDescId(zoneMasterDTObj.getZoneDescPk());
						zoneMasterDesc.setZoneDescription(zoneMasterDTObj.getZoneDescription());
						zoneMasterDesc.setZone(zone);
						zoneMasterService.saveRecordDesc(zoneMasterDesc);

						ZoneMasterDesc zoneMasterDesc2 = new ZoneMasterDesc();
						languageType.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
						zoneMasterDesc2.setLanguageType(languageType);
						zoneMasterDesc2.setZoneDescId(zoneMasterDTObj.getZoneDescLocalPk());
						zoneMasterDesc2.setZoneDescription(zoneMasterDTObj.getZoneDescriptionLocal());
						zoneMasterDesc2.setZone(zone);
						zoneMasterService.saveRecordDesc(zoneMasterDesc2);

					}

				} 
				RequestContext.getCurrentInstance().execute("complete.show();");
			
			zoneMasterDTList.clear();
			LOGGER.info("Exit into saveDataTableRecords method");
			
		} catch(NullPointerException ne){
		LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::saveDataTableRecods"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
}catch(Exception exception){
 LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
  setErrorMessage(exception.getMessage()); 
  RequestContext.getCurrentInstance().execute("error.show();");
  return;       
}
		
		
	}

	public void clickOnOKSave() {
		LOGGER.info("Entering into clickOnOKSave method");
		setBooRenderDataTable(false);
		setBooRenderSaveExit(false);
		clearAllFields();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/Zone.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into clickOnOKSave method");
	}

	// auto Complete
	public List<String> autoComplete(String query) {
		try {
			LOGGER.info("Entering into autoComplete method");
			if (query.length() > 0) {
				return zoneMasterService.getZoneListCode(query);
			} else {
				LOGGER.info("Exit into autoComplete method");
				return null;
			}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::autoComplete");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return null;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;
		}
		
	}

	// duplicate checking for Zone Code

	public void checkZoneCode() {
		LOGGER.info("Entering into checkZoneCode method");
		if (zoneMasterDTList.size() > 0) {
			for (ZoneMasterDataTable zoneMasterDTOBJ : zoneMasterDTList) {
				if (zoneMasterDTOBJ.getZoneCode().equals(getZoneCode())) {
					clearAllFields();
					RequestContext.getCurrentInstance().execute("datatable.show();");
				}
			}
		}
		if (getZoneCode() != null) {
			addRecordsToDataTable();
		
		}
		LOGGER.info("Exit into checkZoneCode method");
	}

	// populate the db values based on code

	public void populate() {
		
		try {
			
			LOGGER.info("Entering into populate method");
			setZoneIdpk(null);
			setZoneDescPk(null);
			setZoneDescLocalPk(null);
			setZoneDescription(null);
			setZoneDescriptionLocal(null);
			zones = zoneMasterService.getZoneList(getZoneCode());
			if (zones.size() > 0) {
				if (zones.get(0).getIsactive().equalsIgnoreCase(Constants.Yes)) {
					setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					setRenderEditButton(true);

				} else if (zones.get(0).getIsactive().equalsIgnoreCase(Constants.D)) {
					setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					setRenderEditButton(true);

				} else if (zones.get(0).getIsactive().equalsIgnoreCase(Constants.U)) {
					setDynamicLabelForActivateDeactivate(Constants.DELETE);
					setRenderEditButton(true);

				}
				setCreatedBy(zones.get(0).getCreatedBy());
				setCreatedDate(zones.get(0).getCreatedDate());
				setZoneIdpk(zones.get(0).getZoneId());
				setIsActive(zones.get(0).getIsactive());
				zoneDescriptionList = zoneMasterService.getZoneDescriptionList(zones.get(0).getZoneId());
				for (ZoneMasterDesc zoneMasterDesc : zoneDescriptionList) {
					if (zoneMasterDesc.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID)) {
						setZoneDescPk(zoneMasterDesc.getZoneDescId());
						setZoneDescription(zoneMasterDesc.getZoneDescription());
					} else {
						setZoneDescLocalPk(zoneMasterDesc.getZoneDescId());
						setZoneDescriptionLocal(zoneMasterDesc.getZoneDescription());
					}
				}
			}
			LOGGER.info("Exit into populate method");
			
		} catch(NullPointerException ne){
		LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::populate"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
}catch(Exception exception){
 LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
  setErrorMessage(exception.getMessage()); 
  RequestContext.getCurrentInstance().execute("error.show();");
  return;       
}
		
	}

	// View Functionality

	public void view() {
		LOGGER.info("Entering into view method");
		zoneMasterDTNewList.clear();
		/*List<ZoneMasterDesc> zoneMasterDesLT = zoneMasterService.getAllZoneList();
		if (zoneMasterDesLT.size() > 0) {
			for (ZoneMasterDesc zoneMasterDesc : zoneMasterDesLT) {
				ZoneMasterDataTable zoneMasterDataTable = new ZoneMasterDataTable();
				zoneMasterDataTable.setZoneCode(zoneMasterDesc.getZone().getZoneCode());
				zoneMasterDataTable.setZoneIdpk(zoneMasterDesc.getZone().getZoneId());
				zoneMasterDataTable.setIsActive(zoneMasterDesc.getZone().getIsactive());
				if (zoneMasterDesc.getZone().getIsactive().equalsIgnoreCase(Constants.Yes)) {
					zoneMasterDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					
				} else if (zoneMasterDesc.getZone().getIsactive().equalsIgnoreCase(Constants.D)) {
					zoneMasterDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
			

				} else if (zoneMasterDesc.getZone().getIsactive().equalsIgnoreCase(Constants.U)) {
					if(zoneMasterDesc.getZone().getModifiedBy()!=null && zoneMasterDesc.getZone().getModifiedDate()!=null){
						zoneMasterDataTable.setDynamicLabelForActivateDeactivate("");
					}else{
					zoneMasterDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					}
				}
				zoneMasterDataTable.setRenderEditButton(true);
				zoneMasterDataTable.setRemarks(zoneMasterDesc.getZone().getRemarks());
				zoneMasterDataTable.setBooEditButton(false);
				zoneMasterDataTable.setApprovedBy(zoneMasterDesc.getZone().getApprovedBy());
				zoneMasterDataTable.setApprovedDate(zoneMasterDesc.getZone().getApprovedDate());
				zoneMasterDataTable.setModifiedBy(zoneMasterDesc.getZone().getModifiedBy());
				zoneMasterDataTable.setModifiedDate(zoneMasterDesc.getZone().getModifiedDate());
				zoneMasterDataTable.setCreatedBy(zoneMasterDesc.getZone().getCreatedBy());
				zoneMasterDataTable.setCreatedDate(zoneMasterDesc.getZone().getCreatedDate());
				
				if (zoneMasterDesc.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID)) {
					zoneMasterDataTable.setZoneDescPk(zoneMasterDesc.getZoneDescId());
					zoneMasterDataTable.setZoneDescription(zoneMasterDesc.getZoneDescription());
					if (zoneMasterDataTable.getZoneDescription() != null && zoneMasterDataTable.getZoneDescriptionLocal() != null) {
						zoneMasterDTNewList.add(zoneMasterDataTable);
					}
				}
				List<ZoneMasterDesc> zoneMasterDesc2 = zoneMasterService.getAllZoneList();
				for (ZoneMasterDesc zoneMsterDesc2 : zoneMasterDesc2) {

					if (zoneMasterDataTable.getZoneCode().equals(zoneMsterDesc2.getZone().getZoneCode()) && zoneMsterDesc2.getLanguageType().getLanguageId().toString().equals(Constants.ARABIC_LANGUAGE_ID)) {
						zoneMasterDataTable.setZoneDescLocalPk(zoneMsterDesc2.getZoneDescId());
						zoneMasterDataTable.setZoneDescriptionLocal(zoneMsterDesc2.getZoneDescription());
						if (zoneMasterDataTable.getZoneDescription() != null && zoneMasterDataTable.getZoneDescriptionLocal() != null) {
							zoneMasterDTNewList.add(zoneMasterDataTable);
						}
					}
				}
			}
		}
		if (zoneMasterDTNewList.size() > 0) {
			addRecordsToDataTable();
			zoneMasterDTNewList.clear();
		}
		LOGGER.info("Exit into view method");*/
	
		
	
		//private List<ZoneMasterDesc> zoneDescriptionList = new ArrayList<ZoneMasterDesc>();
	
	try {
		zoneMasterDTNewList.clear();
		
		List<Zone> zoneList = zoneMasterService.getAllZonesList();
	
		if (zoneList.size() > 0) {
			
			for (Zone zoneMaster : zoneList) {
				
				ZoneMasterDataTable zoneMasterDataTable = new ZoneMasterDataTable();
				zoneMasterDataTable.setZoneCode(zoneMaster.getZoneCode());
				zoneMasterDataTable.setZoneIdpk(zoneMaster.getZoneId());
				zoneMasterDataTable.setIsActive(zoneMaster.getIsactive());
				
				if (zoneMaster.getIsactive().equalsIgnoreCase(Constants.Yes)) {
					zoneMasterDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					
				} else if (zoneMaster.getIsactive().equalsIgnoreCase(Constants.D)) {
					zoneMasterDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
			

				} else if (zoneMaster.getIsactive().equalsIgnoreCase(Constants.U)) {
					if(zoneMaster.getModifiedBy()!=null && zoneMaster.getModifiedDate()!=null){
						zoneMasterDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}else{
					zoneMasterDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					}
				}
				
				zoneMasterDataTable.setRenderEditButton(true);
				zoneMasterDataTable.setRemarks(zoneMaster.getRemarks());
				zoneMasterDataTable.setBooEditButton(false);
				zoneMasterDataTable.setApprovedBy(zoneMaster.getApprovedBy());
				zoneMasterDataTable.setApprovedDate(zoneMaster.getApprovedDate());
				zoneMasterDataTable.setModifiedBy(zoneMaster.getModifiedBy());
				zoneMasterDataTable.setModifiedDate(zoneMaster.getModifiedDate());
				zoneMasterDataTable.setCreatedBy(zoneMaster.getCreatedBy());
				zoneMasterDataTable.setCreatedDate(zoneMaster.getCreatedDate());
				
				
				List<ZoneMasterDesc> zoneDescriptionList = zoneMasterService.getZoneDescriptionList(zoneMaster.getZoneId());
				
				
				for (ZoneMasterDesc desc :zoneDescriptionList) {
					
					if (desc.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID)) {
						zoneMasterDataTable.setZoneDescription(desc.getZoneDescription());
						zoneMasterDataTable.setZoneDescPk(desc.getZoneDescId());
					}
					
					if (desc.getLanguageType().getLanguageId().toString().equals(Constants.ARABIC_LANGUAGE_ID)) {
						zoneMasterDataTable.setZoneDescriptionLocal(desc.getZoneDescription());
						zoneMasterDataTable.setZoneDescLocalPk(desc.getZoneDescId());
					}
					
				}
				
				zoneMasterDTNewList.add(zoneMasterDataTable);
				
				
			}
			
		}
	
		if (zoneMasterDTNewList.size() > 0) {
			addRecordsToDataTable();
			zoneMasterDTNewList.clear();
		}
		LOGGER.info("Exit into view method");
	
	
	
	
	} catch(NullPointerException ne){
		LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::view"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
}catch(Exception exception){
 LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
  setErrorMessage(exception.getMessage()); 
  RequestContext.getCurrentInstance().execute("error.show();");
  return;       
}
		
	
	
	
	
	}

	public void checkStatus(ZoneMasterDataTable zoneMasterDTObj) {
		LOGGER.info("Entering into checkStatus method");
		if (zoneMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			zoneMasterDTObj.setRemarksCheck(true);
			setApprovedBy(zoneMasterDTObj.getApprovedBy());
			setApprovedDate(zoneMasterDTObj.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
		}else if (zoneMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)&&zoneMasterDTObj.getModifiedBy() ==null &&zoneMasterDTObj. getModifiedDate() ==null && zoneMasterDTObj.getRemarks() ==null&&zoneMasterDTObj.getApprovedBy()==null&&zoneMasterDTObj.getApprovedDate()==null) {
			zoneMasterDTObj.setPermanetDeleteCheck(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			return;
		}else if(zoneMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
			RequestContext.getCurrentInstance().execute("pending.show();");
			//pending
		}
		else {
			removeRecord(zoneMasterDTObj);
		}
		LOGGER.info("Exit into checkStatus method");
	}


	public void confirmPermanentDelete(){
		LOGGER.info("Entering into confirmPermanentDelete method");
		if(zoneMasterDTList.size()>0){
			for (ZoneMasterDataTable zoneMasterDataTable : zoneMasterDTList) {
				if(zoneMasterDataTable.getActivateRecordCheck()!=null){
					if(zoneMasterDataTable.getPermanetDeleteCheck().equals(true)){
						zoneMasterDelete(zoneMasterDataTable);
						zoneMasterDTList.remove(zoneMasterDataTable);
					}
				}
			}
		}
		LOGGER.info("Exit into confirmPermanentDelete method");
	}
	public void clickOkRemarks() {
		LOGGER.info("Entering into clickOkRemarks method");
		try{
			if(getRemarks()!=null && !getRemarks().equals("")) {
		for (ZoneMasterDataTable zoneMasterDTObj : zoneMasterDTList) {
			if (zoneMasterDTObj.getRemarksCheck() != null) {
				if (zoneMasterDTObj.getRemarksCheck().equals(true)) {
					zoneMasterDTObj.setRemarks(getRemarks());
					removeRecord(zoneMasterDTObj);
					setRemarks(null);
				}
			}
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/Zone.xhtml");
		} catch (Exception e) {
			// TODO: handle exception
		}
			}else
			{
				RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
			}
			
		}catch(NullPointerException ne){
		LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::pageNavigationApprve"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
}catch(Exception exception){
 LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
  setErrorMessage(exception.getMessage()); 
  RequestContext.getCurrentInstance().execute("error.show();");
  return;       
}
		LOGGER.info("Exit into clickOkRemarks method");
	}

	public void cancelRemarks() {
		LOGGER.info("Entering into cancelRemarks method");
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/Zone.xhtml");
		} catch(NullPointerException ne){
		LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::pageNavigationApprve"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
}catch(Exception exception){
 LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
  setErrorMessage(exception.getMessage()); 
  RequestContext.getCurrentInstance().execute("error.show();");
  return;       
}
		LOGGER.info("Exit into cancelRemarks method");
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
 public void pageNavigation(){
	 LOGGER.info("Entering into pageNavigation method");
	 setBooRenderDataTable(false);
	 setBooRenderSaveExit(false);
	 setBooReadonly(false);
	 setBoohideButton(true);
	 setClearPanel(false);
	 clearAllFields();
	 setBoohideSecondButton(false);
	 setBooRenderApprove(false);
	 zoneMasterDTList.clear();
	 try {
		 loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "Zone.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/Zone.xhtml");
		} catch(NullPointerException ne){
		LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::pageNavigationApprve"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
}catch(Exception exception){
 LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
  setErrorMessage(exception.getMessage()); 
  RequestContext.getCurrentInstance().execute("error.show();");
  return;       
}
	 LOGGER.info("Exit into pageNavigation method");
 }
	public void exit() throws IOException {
		List<RoleMaster> rolList = generalService.getRoleList(new BigDecimal(sessinstate.getRoleId()));
		if(rolList.get(0).getRoleName().equalsIgnoreCase(Constants.USER_ROLE_MANAGER)){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	public void submitButtonHide(){
		 LOGGER.info("Entering into submitButtonHide method");
		if(getZoneDescriptionLocal()!=null || getZoneDescription() !=null){
			setSubmit(true);
		}else{
			setSubmit(false);
		}
		 LOGGER.info("Exit into submitButtonHide method");
	}
	
	
	public void pageNavigationApprve(){
		 LOGGER.info("Entering into pageNavigationApprve method");
		setBoohideButton(false);
		setBoohideButton(false);
		setSubmit(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "ZoneMasterApprovel.xhtml");
			fetchRecordsDb();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/ZoneMasterApprovel.xhtml");
		} catch(NullPointerException ne){
		LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::pageNavigationApprve"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
}catch(Exception exception){
 LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
  setErrorMessage(exception.getMessage()); 
  RequestContext.getCurrentInstance().execute("error.show();");
  return;       
}
		 LOGGER.info("Exit into pageNavigationApprve method");
	}
	public void fetchRecordsDb(){
		LOGGER.info("Entering into fetchRecordsDb method");
		try{
			
			zoneMasterDTList.clear();
			List<ZoneMasterDesc> zoneMasterDesclist=zoneMasterService.getZoneMasterApprovel();
			if(zoneMasterDesclist.size()>0){
				for (ZoneMasterDesc zoneMasterDesc : zoneMasterDesclist) {
					ZoneMasterDataTable zoneMasterDataTable=new ZoneMasterDataTable();
					zoneMasterDataTable.setZoneCode(zoneMasterDesc.getZone().getZoneCode());
					zoneMasterDataTable.setZoneIdpk(zoneMasterDesc.getZone().getZoneId());
					//zoneMasterDataTable.setIsActive(zoneMasterDesc.getZone().getIsactive());
					zoneMasterDataTable.setModifiedBy(zoneMasterDesc.getZone().getModifiedBy());
					zoneMasterDataTable.setModifiedDate(zoneMasterDesc.getZone().getModifiedDate());
					zoneMasterDataTable.setCreatedBy(zoneMasterDesc.getZone().getCreatedBy());
					zoneMasterDataTable.setCreatedDate(zoneMasterDesc.getZone().getCreatedDate());
					if (zoneMasterDesc.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID)) {
						zoneMasterDataTable.setZoneDescPk(zoneMasterDesc.getZoneDescId());
						zoneMasterDataTable.setZoneDescription(zoneMasterDesc.getZoneDescription());
						if (zoneMasterDataTable.getZoneDescription() != null && zoneMasterDataTable.getZoneDescriptionLocal() != null) {
							zoneMasterDTList.add(zoneMasterDataTable);
						}
					}
					List<ZoneMasterDesc> zoneMasterDesc2 = zoneMasterService.getAllZoneList();
					for (ZoneMasterDesc zoneMsterDesc2 : zoneMasterDesc2) {

						if (zoneMasterDataTable.getZoneCode().equals(zoneMsterDesc2.getZone().getZoneCode()) && zoneMsterDesc2.getLanguageType().getLanguageId().toString().equals(Constants.ARABIC_LANGUAGE_ID)) {
							zoneMasterDataTable.setZoneDescLocalPk(zoneMsterDesc2.getZoneDescId());
							zoneMasterDataTable.setZoneDescriptionLocal(zoneMsterDesc2.getZoneDescription());
							if (zoneMasterDataTable.getZoneDescription() != null && zoneMasterDataTable.getZoneDescriptionLocal() != null) {
								zoneMasterDTList.add(zoneMasterDataTable);
							}
						}
					}
				
				}
			}
		} catch(NullPointerException ne){
		LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::fetchRecordsDb"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
}catch(Exception exception){
 LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
  setErrorMessage(exception.getMessage()); 
  RequestContext.getCurrentInstance().execute("error.show();");
  return;       
}
		 
		 LOGGER.info("Exit into fetchRecordsDb method");
	}
	
	public void approve(){
		 LOGGER.info("Entering into approve method");
		 try {
			 String approveMsg=zoneMasterService.approveRecord(getZoneIdpk(),session.getUserName());
			 if(approveMsg.equalsIgnoreCase("Success")){
				RequestContext.getCurrentInstance().execute("approve.show();");
				LOGGER.info("Exit into approve method");
			 }else{
				 RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				 
			 }
		} catch(NullPointerException ne){
		LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::approve"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
}catch(Exception exception){
 LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
  setErrorMessage(exception.getMessage()); 
  RequestContext.getCurrentInstance().execute("error.show();");
  return;       
}
		
			}
		
		
		
	
	public void approvelCheck(ZoneMasterDataTable	zoneMasterDataTable){
		 LOGGER.info("Entering into approvelCheck method");	
		 if(!(zoneMasterDataTable.getModifiedBy()==null?zoneMasterDataTable.getCreatedBy():zoneMasterDataTable.getModifiedBy()).equalsIgnoreCase(session.getUserName()))
		{
			setZoneCode(zoneMasterDataTable.getZoneCode());
			setZoneIdpk(zoneMasterDataTable.getZoneIdpk());
			setZoneDescription(zoneMasterDataTable.getZoneDescription());
			setZoneDescriptionLocal(zoneMasterDataTable.getZoneDescriptionLocal());
			setCreatedBy(zoneMasterDataTable.getCreatedBy());
			setCreatedDate(zoneMasterDataTable.getCreatedDate());
			setModifiedBy(zoneMasterDataTable.getModifiedBy());
			setModifiedDate(zoneMasterDataTable.getModifiedDate());
			setBoohideButton(false);
			setSubmit(false);
			setBoohideButton(false);
			setBooRenderDataTable(false);
			setBooRenderSaveExit(false);
			setBooRenderApprove(true);
			setBooReadonly(true);
			try {
				
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/Zone.xhtml");
				
			} catch(NullPointerException ne){
				LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			    setErrorMessage("Method Name::approvelCheck"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
	}catch(Exception exception){
	 LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
	  setErrorMessage(exception.getMessage()); 
	  RequestContext.getCurrentInstance().execute("error.show();");
	  return;       
	}
		}else{
		RequestContext.getCurrentInstance().execute("notApproved.show();");
		}
		LOGGER.info("Exit into approvelCheck method");
	}

	public void clickOnOKSaveApprove(){
		LOGGER.info("Entering into clickOnOKSaveApprove method");
		setZoneCode(null);
		setZoneDescription(null);
		setZoneDescriptionLocal(null);
		setZoneIdpk(null);
		setBooRenderDataTable(false);
		setBooRenderSaveExit(false);
		try {
			fetchRecordsDb();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/ZoneMasterApprovel.xhtml");

		} catch(NullPointerException ne){
		LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::clickOnOKSaveApprove"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
}catch(Exception exception){
 LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
  setErrorMessage(exception.getMessage()); 
  RequestContext.getCurrentInstance().execute("error.show();");
  return;       
}
		LOGGER.info("Exit into clickOnOKSaveApprove method");
	}
	
	public void cancel(){
		LOGGER.info("Entering into cancel method");
		try {

			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/ZoneMasterApprovel.xhtml");

		} catch(NullPointerException ne){
		LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::clickOnOKSaveApprove"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
}catch(Exception exception){
 LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
  setErrorMessage(exception.getMessage()); 
  RequestContext.getCurrentInstance().execute("error.show();");
  return;       
}
		LOGGER.info("Exit into cancel method");
	}
	
	public void clickOnOkButton(){
		try {
		fetchRecordsDb();
		

			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/ZoneMasterApprovel.xhtml");

		}catch(NullPointerException ne){
		LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::clickOnOkButton"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
}catch(Exception exception){
 LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
  setErrorMessage(exception.getMessage()); 
  RequestContext.getCurrentInstance().execute("error.show();");
  return;       
}
	}
	
	
	}
	

