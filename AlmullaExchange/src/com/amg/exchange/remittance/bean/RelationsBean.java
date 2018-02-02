package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.model.RelationsDescription;
import com.amg.exchange.remittance.service.IRelationsTypeService;
import com.amg.exchange.util.CollectionUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("relationsBean")
@Scope("session")
public class RelationsBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(RelationsBean.class);
	private SessionStateManage session = new SessionStateManage();
	private String relationsId ;
	private String englishRelationsDesc = null;
	private String arabicRelationsDesc = null;
	private Boolean booRenderSave = false;
	private Boolean booRenderDataTable = false;
	private Boolean booRelationsForDataTable = false;
	private Boolean booRelationsCheck = false;
	private Boolean booCheckDelete = false;
	private Boolean booCheckUpdate = false;
	private Boolean booDisableClear = false;
	private Boolean booDisableEdit = false;
	private List<RelationsDatatable> relationsDatatableDTList = new CopyOnWriteArrayList<RelationsDatatable>();
	List<RelationsDatatable> relationsDatatablelist = new CopyOnWriteArrayList<>();
	@Autowired
	IRelationsTypeService relationsTypeService;
	@Autowired
	IGeneralService<T> generalService;

	public List<RelationsDatatable> getRelationsDatatableDTList() {
		return relationsDatatableDTList;
	}

	public void setRelationsDatatableDTList(List<RelationsDatatable> relationsDatatableDTList) {
		this.relationsDatatableDTList = relationsDatatableDTList;
	}

	public Boolean getBooRelationsForDataTable() {
		return booRelationsForDataTable;
	}

	public void setBooRelationsForDataTable(Boolean booRelationsForDataTable) {
		this.booRelationsForDataTable = booRelationsForDataTable;
	}

	public Boolean getBooRelationsCheck() {
		return booRelationsCheck;
	}

	public void setBooRelationsCheck(Boolean booRelationsCheck) {
		this.booRelationsCheck = booRelationsCheck;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public String getRelationsId() {
		return relationsId;
	}

	public void setRelationsId(String relationsId) {
		this.relationsId = relationsId;
	}

	public String getEnglishRelationsDesc() {
		return englishRelationsDesc;
	}

	public void setEnglishRelationsDesc(String englishRelationsDesc) {
		this.englishRelationsDesc = englishRelationsDesc;
	}

	public String getArabicRelationsDesc() {
		return arabicRelationsDesc;
	}

	public void setArabicRelationsDesc(String arabicRelationsDesc) {
		this.arabicRelationsDesc = arabicRelationsDesc;
	}

	public static Logger getLog() {
		return log;
	}

	public Boolean getBooRenderSave() {
		return booRenderSave;
	}

	public void setBooRenderSave(Boolean booRenderSave) {
		this.booRenderSave = booRenderSave;
	}

	public List<RelationsDatatable> getRelationsDatatablelist() {
		return relationsDatatablelist;
	}

	public void setRelationsDatatablelist(List<RelationsDatatable> relationsDatatablelist) {
		this.relationsDatatablelist = relationsDatatablelist;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void relationsPageNavigation() {
		relationsDT.clear();
		clearAllFields();
		setBooRenderDataTable(false);
		setBooRenderSave(false);
		setBoohideSecod(false);
		setBooReadonly(false);
		setBoohideButton(true);
		setBooDisableClear(false);
		relationsDatatablelist.clear();
		relationsDatatableDTList.clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "RelationShip.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RelationShip.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnOKSave() {
		relationsDatatableDTList.clear();
		setBooRenderDataTable(false);
		setBooRenderSave(false);
		setBooRelationsForDataTable(false);
		setBooRelationsCheck(false);
		clearAllFields();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RelationShip.xhtml");
		} catch (IOException e) {
			setErrmsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		
		}
	}

	public void clearAllFields() {
		setRelationsId(null);
		setArabicRelationsDesc(null);
		setEnglishRelationsDesc(null);
		setRelationsPk(null);
		setRelationEnglishPk(null);
		setRelationArabicPk(null);
		if (relationsDatatableDTList.size() > 0) {
			setBooSubmit(false);
		}
		setBooSubmit(false);
	}

	public void addRecordsToDataTable() {
		try{
		setBooSubmit(false);
		if (relationsDatatablelist.size() == 0) {
			List<RelationsDescription> listrel = relationsTypeService.getAllActiveInActive(getEnglishRelationsDesc());
			RelationsDatatable relationsDatatableObj = new RelationsDatatable();
			relationsDatatableObj.setRelationsId(getRelationsId());
			relationsDatatableObj.setRelationsPk(getRelationsPk());
			relationsDatatableObj.setRelationArabicPk(getRelationArabicPk());
			relationsDatatableObj.setRelationEnglishPk(getRelationEnglishPk());
			relationsDatatableObj.setEnglishRelationsDesc(getEnglishRelationsDesc());
			relationsDatatableObj.setArabicRelationsDesc(getArabicRelationsDesc());
			List<RelationsDescription> listForCheck = relationsTypeService.getCheckRelationCode(getRelationsId());
			if (listForCheck.size() > 0) {
				for (RelationsDescription relationdesc : listForCheck) {
					relationsDatatableObj.setRelationsPk(relationdesc.getRelations().getRelationsId());
					relationsDatatableObj.setCreatedBy(relationdesc.getRelations().getCreatedBy());
					relationsDatatableObj.setCreatedDate(relationdesc.getRelations().getCreatedDate());
					if (relationdesc.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID.toString())) {
						relationsDatatableObj.setRelationEnglishPk(relationdesc.getRelationsDescriptionId());
					} else {
						relationsDatatableObj.setRelationArabicPk(relationdesc.getRelationsDescriptionId());
					}
				}
			}
			if (getRelationsPk() != null) {
				if (getRelationsId() != null) {
					List<RelationsDescription> listCheckCode = relationsTypeService.getCheckRelationCode(getRelationsId());
					if (listCheckCode.size() > 0) {
						relationsDatatableObj.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
						relationsDatatableObj.setRenderEditButton(getRenderEditButton());
						relationsDatatableObj.setIsActive(getIsActive());
						relationsDatatableObj.setCheckSave(getCheckSave());
						relationsDatatableObj.setCreatedBy(getCreatedBy());
						relationsDatatableObj.setCreatedDate(getCreatedDate());
						relationsDatatableObj.setModifiedBy(getModifiedBy());
						relationsDatatableObj.setModifiedDate(getModifiedDate());
						relationsDatatableObj.setApprovedBy(getApprovedBy());
						relationsDatatableObj.setApprovedDate(getApprovedDate());
						relationsDatatableObj.setBooCheckUpdate(getBooCheckUpdate());
					} else {
						relationsDatatableObj.setRelationsPk(null);
						relationsDatatableObj.setRelationEnglishPk(null);
						relationsDatatableObj.setRelationArabicPk(null);
						relationsDatatableObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
						relationsDatatableObj.setBooCheckUpdate(true);
						relationsDatatableObj.setCheckSave(true);
						relationsDatatableObj.setIsActive(Constants.U);
						relationsDatatableObj.setCreatedBy(session.getUserName());
						relationsDatatableObj.setCreatedDate(new Date());
						relationsDatatableObj.setRenderEditButton(true);
					}
				}
			} else {
				if (relationsDatatableObj.getRelationsPk() != null) {
					relationsDatatableObj.setRenderEditButton(true);
					relationsDatatableObj.setIsActive(Constants.U);
					relationsDatatableObj.setCheckSave(true);
					relationsDatatableObj.setCreatedBy(session.getUserName());
					relationsDatatableObj.setCreatedDate(new Date());
					relationsDatatableObj.setBooCheckUpdate(true);
					relationsDatatableObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				} else {
					relationsDatatableObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
					relationsDatatableObj.setRenderEditButton(true);
					relationsDatatableObj.setIsActive(Constants.U);
					relationsDatatableObj.setCheckSave(true);
					relationsDatatableObj.setCreatedBy(session.getUserName());
					relationsDatatableObj.setCreatedDate(new Date());
					relationsDatatableObj.setBooCheckUpdate(true);
				}
			}
			if (listrel.size() != 0 && getRelationsPk() == null) {
				setRelationsId(null);
				setEnglishRelationsDesc(null);
				setArabicRelationsDesc(null);
				setBooRenderDataTable(false);
				RequestContext.getCurrentInstance().execute("succ.show();");
			} else {
				relationsDatatableDTList.add(relationsDatatableObj);
			}
		}
		if (relationsDatatableDTList.size() > 0) {
			if (relationsDatatablelist.size() > 0) {
				for (RelationsDatatable relationDt : relationsDatatableDTList) {
					for (RelationsDatatable relationListDt : relationsDatatablelist) {
						if (relationDt.getRelationsId().equals(relationListDt.getRelationsId())) {
							relationsDatatablelist.remove(relationListDt);
						}
					}
				}
			}
			relationsDatatableDTList.addAll(relationsDatatablelist);
		} else {
			relationsDatatableDTList.addAll(relationsDatatablelist);
		}
		clearAllFields();
		setBooRenderDataTable(true);
		setBooRenderSave(true);
		setBooDisableEdit(false);
		setBooDisableClear(false);
		}catch(Exception e){
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");

		}
	}

	// THIS METHOD IS USED FOR EDIT THE DATA TABLE RECORD
	public void editRecord(RelationsDatatable relationsDatatableObj) {
		setRelationsPk(relationsDatatableObj.getRelationsPk());
		setRelationEnglishPk(relationsDatatableObj.getRelationEnglishPk());
		setRelationArabicPk(relationsDatatableObj.getRelationArabicPk());
		setRelationsId(relationsDatatableObj.getRelationsId());
		 setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
		setArabicRelationsDesc(relationsDatatableObj.getArabicRelationsDesc());
		setEnglishRelationsDesc(relationsDatatableObj.getEnglishRelationsDesc());
		setRenderEditButton(relationsDatatableObj.getRenderEditButton());
		setBooCheckDelete(true);
		setIsActive(Constants.U);
		setBooCheckUpdate(true);
		setBooDisableEdit(true);
		setBooDisableClear(true);
		setCheckSave(relationsDatatableObj.getCheckSave());
		setCreatedBy(relationsDatatableObj.getCreatedBy());
		setCreatedDate(relationsDatatableObj.getCreatedDate());
		relationsDatatableDTList.remove(relationsDatatableObj);
		setBooSubmit(true);
		if (relationsDatatableDTList.size() == 0) {
			setBooRenderSave(false);
			setBooRenderDataTable(false);
		}
	}

	// THIS METHOD IS USED FOR REMOVE THE RECORD FROM DB IF EXISTED IN DB AND IF
	RelationsDatatable relationData = new RelationsDatatable();

	// NEW RECORD JUST DELETE FROM DATATABLE
	public void removeRecord(RelationsDatatable relationsDatatableObj) {
	try{
		if (relationsDatatableObj.getRelationsPk() == null) {
			if (relationsDatatableObj.getCheckSave().equals(true)) {
				relationsDatatableDTList.remove(relationsDatatableObj);
				if (relationsDatatableDTList.size() <= 0) {
					setBooRenderSave(false);
					setBooRenderDataTable(false);
				}
			}
		} else {
			delete(relationsDatatableObj);
			relationsDatatableDTList.clear();
			getAllDataRecords();
			relationsDatatableDTList.addAll(relationsDatatablelist);
		}
	}catch(NullPointerException  e){
		setErrmsg("Method Name:removeRecord"+e.getMessage()); 
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}
	catch(Exception e){
		setErrmsg(e.getMessage()); 
		RequestContext.getCurrentInstance().execute("csp.show();");
	} 
	}

	// THIS METHOD IS USED FOR SAVE RECORDS FROM DATA TABLE
	public void saveDataTableRecords() {
		
		
		boolean isModifiedorNewrecordAvailable = false;
		if (relationsDatatableDTList.isEmpty()) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		} else {
			try {
				for (RelationsDatatable relationsDatatableDtObj : relationsDatatableDTList) {
					Relations relations = new Relations();
					relations.setRelationsId(relationsDatatableDtObj.getRelationsPk());
					relations.setRelationsCode(relationsDatatableDtObj.getRelationsId());
					relations.setEnglishRelationsTypeDesc(relationsDatatableDtObj.getEnglishRelationsDesc());
					relations.setLocalRelationsTypeDesc(relationsDatatableDtObj.getArabicRelationsDesc());
					if (relationsDatatableDtObj.getRelationsPk() != null) {
						relations.setModifiedBy(session.getUserName());
						relations.setModifiedDate(new Date());
						relations.setCreatedBy(relationsDatatableDtObj.getCreatedBy());
						relations.setCreatedDate(relationsDatatableDtObj.getCreatedDate());
					} else {
						relations.setCreatedBy(session.getUserName());
						relations.setCreatedDate(new Date());
					}
					relations.setApprovedBy(relationsDatatableDtObj.getApprovedBy());
					relations.setApprovedDate(relationsDatatableDtObj.getApprovedDate());
					relations.setIsActive(relationsDatatableDtObj.getIsActive());
					relations.setRemarks(relationsDatatableDtObj.getRemarks());
					if (relationsDatatableDtObj.getBooCheckUpdate()) {
						isModifiedorNewrecordAvailable = true;
						relationsTypeService.saveRecord(relations);
					}
					RelationsDescription relationsDescription = new RelationsDescription();
					LanguageType languageType = new LanguageType();
					languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
					relationsDescription.setLanguageType(languageType);
					relationsDescription.setRelationsDescriptionId(relationsDatatableDtObj.getRelationEnglishPk());
					relationsDescription.setLocalRelationsDescription(relationsDatatableDtObj.getEnglishRelationsDesc());
					relationsDescription.setRelations(relations);
					if (relationsDatatableDtObj.getBooCheckUpdate()) {
						relationsTypeService.saveRecordForDesc(relationsDescription);
						isModifiedorNewrecordAvailable = true;
					}
					RelationsDescription relationsDescription1 = new RelationsDescription();
					LanguageType languageType1 = new LanguageType();
					languageType1.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
					relationsDescription1.setLanguageType(languageType1);
					/* added to populate the DB Values added @Koti 20/02/2015 */
					relationsDescription1.setRelationsDescriptionId(relationsDatatableDtObj.getRelationArabicPk());
					/* added to populate the DB Values added @Koti 20/02/2015 */
					relationsDescription1.setLocalRelationsDescription(relationsDatatableDtObj.getArabicRelationsDesc());
					relationsDescription1.setRelations(relations);
					if (relationsDatatableDtObj.getBooCheckUpdate()) {
						isModifiedorNewrecordAvailable = true;
						relationsTypeService.saveRecordForDesc(relationsDescription1);
					}
				}
			
			
			if(!isModifiedorNewrecordAvailable)
			{
				setErrmsg(" There is no new records or Modified records not available for save in the dataTable");
				RequestContext.getCurrentInstance().execute("csp.show();");
			
			}
			else {
			RequestContext.getCurrentInstance().execute("complete.show();");
			}
			} catch(NullPointerException  e){
				setErrmsg("Method Name:removeRecord"+e.getMessage()); 
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
			catch(Exception e){
				setErrmsg(e.getMessage()); 
				RequestContext.getCurrentInstance().execute("csp.show();");
			} 
		}
		//relationsDatatableDTList.clear();
	}

	public void exit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	List<Relations> relations = new ArrayList<Relations>();
	private String errmsg;

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

	public void checkRelations() {
		/* added to populate the DB Values added @Koti 20/02/2015 */
		boolean isNUmber = false;
		try {
			isNUmber = CollectionUtil.isIntegerValue(new BigDecimal(getRelationsId()));
		} catch (Exception e) {
			setErrmsg(" Relations Code should be number"+e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			setRelationsId(null);
			return;
		}
		if (!isNUmber) {
			setErrmsg(" Relations Code should be number");
			RequestContext.getCurrentInstance().execute("csp.show();");
			setRelationsId(null);
			return;
		}
		popUp();
		/* Ended to populate the DB Values added @Koti* 20/02/2015 */
		if (relationsDatatableDTList.size() > 0) {
			for (RelationsDatatable relationsDatatableObj : relationsDatatableDTList) {
				if (relationsDatatableObj.getRelationsId().equalsIgnoreCase(getRelationsId())) {
					setBooRelationsForDataTable(true);
					return;
				} else {
					setBooRelationsForDataTable(false);
				}
			}
		}
		List<Relations> relations = new ArrayList<Relations>();
		relations.addAll(relationsTypeService.getRealtion(getRelationsId()));
		if (relationsDatatableDTList.size() > 0) {
			for (RelationsDatatable relationsDt : relationsDatatableDTList) {
				if (relationsDt.getRelationsId().equalsIgnoreCase(getRelationsId())) {
					setBooRelationsForDataTable(true);
					setBooRelationsCheck(true);
					setRelationsCode(null);
				}
			}
		}
	}

	/* Added to populate the DB Values added @Koti 20/02/2015* */
	private String relationsCode;
	private List<RelationsDescription> relationsDescriptionlist = new ArrayList<RelationsDescription>();

	public String getRelationsCode() {
		return relationsCode;
	}

	public void setRelationsCode(String relationsCode) {
		this.relationsCode = relationsCode;
	}

	public List<RelationsDescription> getRelationsDescriptionlist() {
		return relationsDescriptionlist;
	}

	public void setRelationsDescriptionlist(List<RelationsDescription> relationsDescriptionlist) {
		this.relationsDescriptionlist = relationsDescriptionlist;
	}

	public void popUp() {
		setRelationsPk(null);
		setRelationEnglishPk(null);
		setRelationArabicPk(null);
		setEnglishRelationsDesc(null);
		setArabicRelationsDesc(null);
		try{
		relations = relationsTypeService.getRelationList(getRelationsId());
		if (relations.size() != 0) {
			if (relations.get(0).getIsActive().equalsIgnoreCase(Constants.Yes)) {
				// setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				setRenderEditButton(true);
				setCheckSave(false);
			} else if (relations.get(0).getIsActive().equalsIgnoreCase(Constants.D)) {
				// setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				setRenderEditButton(true);
				setCheckSave(false);
			}
			setCreatedBy(relations.get(0).getCreatedBy());
			setCreatedDate(relations.get(0).getCreatedDate());
			setRelationsPk(relations.get(0).getRelationsId());
			setIsActive(Constants.U);
			setBooCheckUpdate(true);
			setRenderEditButton(true);
			relationsDescriptionlist = relationsTypeService.getRelationsDescriptionList(relations.get(0).getRelationsId());
			for (RelationsDescription relationsDescription : relationsDescriptionlist) {
				if (relationsDescription.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID)) {
					setRelationEnglishPk(relationsDescription.getRelationsDescriptionId());
					setEnglishRelationsDesc(relationsDescription.getLocalRelationsDescription());
				} else {
					setRelationArabicPk(relationsDescription.getRelationsDescriptionId());
					setArabicRelationsDesc(relationsDescription.getLocalRelationsDescription());
				}
			}
			RequestContext.getCurrentInstance().execute("existed.show();");
		}
		
		 }catch(NullPointerException  e){
				setErrmsg("Method Name:removeRecord"+e.getMessage()); 
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
			catch(Exception e){
				setErrmsg(e.getMessage()); 
				RequestContext.getCurrentInstance().execute("csp.show();");
			} 
		
	}

	// TO POPULATE THE RELATION CODE BASED ON GIVEN ENTRY
	public List<String> autoCompleteData(String query) {
 
		if (query.length() > 0) {
			List<String> list = new ArrayList<String>();
 
			List<Relations> relations = relationsTypeService.getAllComponent(query);
		
			// changes done by mohan -Provide Drop Down in Relationship with
			// ability to create new delivery modes
			for (Relations objRelation : relations) {
				System.out.println(objRelation.getRelationsCode() + " - " + objRelation.getEnglishRelationsTypeDesc());
				list.add(objRelation.getRelationsCode());
			}
		
			
			return list;
		} else {
			return null;
		}
		
	}

	public void hideSubmitButton() {
		setBooSubmit(true);
	}

	private BigDecimal relationsPk;
	private BigDecimal relationEnglishPk;
	private BigDecimal relationArabicPk;
	private boolean booSubmit = false;
	private String remarks;

	public BigDecimal getRelationEnglishPk() {
		return relationEnglishPk;
	}

	public BigDecimal getRelationArabicPk() {
		return relationArabicPk;
	}

	public void setRelationEnglishPk(BigDecimal relationEnglishPk) {
		this.relationEnglishPk = relationEnglishPk;
	}

	public void setRelationArabicPk(BigDecimal relationArabicPk) {
		this.relationArabicPk = relationArabicPk;
	}

	public BigDecimal getRelationsPk() {
		return relationsPk;
	}

	public void setRelationsPk(BigDecimal relationsPk) {
		this.relationsPk = relationsPk;
	}

	/* Ended to populate the DB Values added @Koti 20/02/2015* */
	public void allDetailsToList() {
		if (relationsDatatableDTList.size() != 0) {
			for (RelationsDatatable relationsDtList : relationsDatatableDTList) {
				if (relationsDtList.getRelationsId().equals(getRelationsId())) {
					setRelationsId(null);
					setEnglishRelationsDesc(null);
					setArabicRelationsDesc(null);
					RequestContext.getCurrentInstance().execute("succ.show();");
				} else {
					if (relationsDtList.getEnglishRelationsDesc().equals(getEnglishRelationsDesc())) {
						setRelationsId(null);
						setEnglishRelationsDesc(null);
						setArabicRelationsDesc(null);
						RequestContext.getCurrentInstance().execute("succdesc.show();");
					}
				}
			}
		}
		if (getRelationsId() != null) {
			addRecordsToDataTable();
			relationsDatatablelist.clear();
		}
	}

	public void getAllDataRecords() {
		
		
		
		
		
		
		/*
		System.out.println("View() called================================");
		relationsDatatablelist.clear();
		List<RelationsDescription> reDesList = relationsTypeService.getAllRelationList();
		if (reDesList.size() > 0) {
			for (RelationsDescription relationsDesc : reDesList) {
				RelationsDatatable relationsDT = new RelationsDatatable();
				relationsDT.setRelationsId(relationsDesc.getRelations().getRelationsCode());
				relationsDT.setRelationsPk(relationsDesc.getRelations().getRelationsId());
				relationsDT.setIsActive(relationsDesc.getRelations().getIsActive());
				if (relationsDesc.getRelations().getIsActive().equalsIgnoreCase(Constants.Yes)) {
					relationsDT.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					relationsDT.setRenderEditButton(true);
					relationsDT.setCheckSave(false);
					relationsDT.setBooCheckDelete(false);
				} else if (relationsDesc.getRelations().getIsActive().equalsIgnoreCase(Constants.D)) {
					relationsDT.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					relationsDT.setRemarks(relationsDesc.getRelations().getRemarks());
					relationsDT.setRenderEditButton(true);
					relationsDT.setCheckSave(false);
					relationsDT.setBooCheckDelete(false);
				} else if (relationsDesc.getRelations().getIsActive().equalsIgnoreCase(Constants.U) && relationsDesc.getRelations().getModifiedBy() == null && relationsDesc.getRelations().getModifiedDate() == null && relationsDesc.getRelations().getApprovedBy() == null
						&& relationsDesc.getRelations().getApprovedDate() == null && relationsDesc.getRelations().getRemarks() == null) {
					relationsDT.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					// relationsDT.setRemarks(relationsDesc.getRelations().getRemarks());
					relationsDT.setRenderEditButton(true);
					relationsDT.setCheckSave(false);
					relationsDT.setBooCheckDelete(false);
				} else {
					relationsDT.setRenderEditButton(true);
					relationsDT.setBooCheckDelete(false);
					relationsDT.setCheckSave(false);
				}
				if (relationsDesc.getRelations().getRelationsId() != null) {
					relationsDT.setModifiedBy(relationsDesc.getRelations().getModifiedBy());
					relationsDT.setModifiedDate(relationsDesc.getRelations().getModifiedDate());
				}
				relationsDT.setApprovedBy(relationsDesc.getRelations().getApprovedBy());
				relationsDT.setApprovedDate(relationsDesc.getRelations().getApprovedDate());
				relationsDT.setCreatedBy(relationsDesc.getRelations().getCreatedBy());
				relationsDT.setCreatedDate(relationsDesc.getRelations().getCreatedDate());
				relationsDT.setBooDisableEdit(false);
				setBooDisableClear(false);
				relationsDT.setBooCheckUpdate(false);
				if (relationsDesc.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID)) {
					relationsDT.setRelationEnglishPk(relationsDesc.getRelationsDescriptionId());
					relationsDT.setEnglishRelationsDesc(relationsDesc.getLocalRelationsDescription());
					if (relationsDT.getRelationEnglishPk() != null && relationsDT.getArabicRelationsDesc() != null) {
						relationsDatatablelist.add(relationsDT);
					}
				}
				List<RelationsDescription> reDesList2 = relationsTypeService.getAllRelationList();
				for (RelationsDescription relationsDesc2 : reDesList2) {
					if (relationsDT.getRelationsId().equals(relationsDesc2.getRelations().getRelationsCode()) && relationsDesc2.getLanguageType().getLanguageId().toString().equals(Constants.ARABIC_LANGUAGE_ID)) {
						relationsDT.setRelationArabicPk(relationsDesc2.getRelationsDescriptionId());
						relationsDT.setArabicRelationsDesc(relationsDesc2.getLocalRelationsDescription());
						if (relationsDT.getArabicRelationsDesc() != null && relationsDT.getEnglishRelationsDesc() != null) {
							relationsDatatablelist.add(relationsDT);
						}
					}
				}
			}
		}
		if (relationsDatatablelist.size() > 0) {
			addRecordsToDataTable();
			relationsDatatablelist.clear();
		} else {
			RequestContext.getCurrentInstance().execute("exist.show();");
		}
	*/
		
	
	
	relationsDatatablelist.clear();
	
	try{
	List<Relations> relationsList = relationsTypeService.getAllRecords();
	
	
			if(relationsList!= null && relationsList.size()!=0)
			{
							
				
				for (Relations relation :relationsList) {
					
					RelationsDatatable relationsDT = new RelationsDatatable();
					
					relationsDT.setRelationsId(relation.getRelationsCode());
					relationsDT.setRelationsPk(relation.getRelationsId());
					relationsDT.setIsActive(relation.getIsActive());
					
					
					if (relation.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						relationsDT.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
						relationsDT.setRenderEditButton(true);
						relationsDT.setCheckSave(false);
						relationsDT.setBooCheckDelete(false);
					} else if (relation.getIsActive().equalsIgnoreCase(Constants.D)) {
						relationsDT.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
						relationsDT.setRemarks(relation.getRemarks());
						relationsDT.setRenderEditButton(true);
						relationsDT.setCheckSave(false);
						relationsDT.setBooCheckDelete(false);
					} else if (relation.getIsActive().equalsIgnoreCase(Constants.U) && relation.getModifiedBy() == null && relation.getModifiedDate() == null && relation.getApprovedBy() == null
							&& relation.getApprovedDate() == null && relation.getRemarks() == null) {
						relationsDT.setDynamicLabelForActivateDeactivate(Constants.DELETE);
						// relationsDT.setRemarks(relationsDesc.getRelations().getRemarks());
						relationsDT.setRenderEditButton(true);
						relationsDT.setCheckSave(false);
						relationsDT.setBooCheckDelete(false);
					} else {
						relationsDT.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						relationsDT.setRenderEditButton(true);
						relationsDT.setBooCheckDelete(false);
						relationsDT.setCheckSave(false);
					}
					
					if (relation.getRelationsId() != null) {
						relationsDT.setModifiedBy(relation.getModifiedBy());
						relationsDT.setModifiedDate(relation.getModifiedDate());
					}
					
					
					relationsDT.setApprovedBy(relation.getApprovedBy());
					relationsDT.setApprovedDate(relation.getApprovedDate());
					relationsDT.setCreatedBy(relation.getCreatedBy());
					relationsDT.setCreatedDate(relation.getCreatedDate());
					relationsDT.setBooDisableEdit(false);
					setBooDisableClear(false);
					relationsDT.setBooCheckUpdate(false);
					
					
					List<RelationsDescription> reDesList = relationsTypeService.getCheckRelationCode(relation.getRelationsCode());
					
					for (RelationsDescription desc :reDesList) {
						
					if (desc.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID)) {
						relationsDT.setRelationEnglishPk(desc.getRelationsDescriptionId());
						relationsDT.setEnglishRelationsDesc(desc.getLocalRelationsDescription());
					}
					if (desc.getLanguageType().getLanguageId().toString().equals(Constants.ARABIC_LANGUAGE_ID)) {
						
						relationsDT.setRelationArabicPk(desc.getRelationsDescriptionId());
						relationsDT.setArabicRelationsDesc(desc.getLocalRelationsDescription());
					}	
						
						
					}
					relationsDatatablelist.add(relationsDT);
				}
				
			
			}
			if (relationsDatatablelist.size() > 0) {
				addRecordsToDataTable();
				relationsDatatablelist.clear();
			} else {
				RequestContext.getCurrentInstance().execute("exist.show();");
			}
			
	}catch(NullPointerException  e){
		setErrmsg("Method Name:removeRecord"+e.getMessage()); 
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}
	catch(Exception e){
		setErrmsg(e.getMessage()); 
		RequestContext.getCurrentInstance().execute("csp.show();");
	} 
	
		
	
	}

	// TO DELETE THE RECORD FROM DB
	public void delete(RelationsDatatable relationsDatatableDtObj) {
		try {
			Relations relations = new Relations();
			relations.setRelationsId(relationsDatatableDtObj.getRelationsPk());
			/* ended to populate the DB Values added @Koti 20/02/2015 */
			relations.setRelationsCode(relationsDatatableDtObj.getRelationsId());
			relations.setEnglishRelationsTypeDesc(relationsDatatableDtObj.getEnglishRelationsDesc());
			relations.setLocalRelationsTypeDesc(relationsDatatableDtObj.getArabicRelationsDesc());
			if (relationsDatatableDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				relations.setIsActive(Constants.U);
			} else if (relationsDatatableDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				relations.setIsActive(Constants.D);
				relations.setRemarks(relationsDatatableDtObj.getRemarks());
			}
			if (relationsDatatableDtObj.getRelationsPk() != null) {
				relations.setModifiedDate(new Date());
				relations.setModifiedBy(session.getUserName());
			}
			relations.setCreatedBy(session.getUserName());
			relations.setCreatedDate(new Date());
			relationsTypeService.saveRecord(relations);
			RelationsDescription relationsDescription = new RelationsDescription();
			LanguageType languageType = new LanguageType();
			languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			relationsDescription.setLanguageType(languageType);
			/* added to populate the DB Values added @Koti 20/02/2015 */
			relationsDescription.setRelationsDescriptionId(relationsDatatableDtObj.getRelationEnglishPk());
			/* ended to populate the DB Values added @Koti 20/02/2015 */
			relationsDescription.setLocalRelationsDescription(relationsDatatableDtObj.getEnglishRelationsDesc());
			relationsDescription.setRelations(relations);
			relationsTypeService.saveRecordForDesc(relationsDescription);
			RelationsDescription relationsDescription1 = new RelationsDescription();
			LanguageType languageType1 = new LanguageType();
			languageType1.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			relationsDescription1.setLanguageType(languageType1);
			/* added to populate the DB Values added @Koti 20/02/2015 */
			relationsDescription1.setRelationsDescriptionId(relationsDatatableDtObj.getRelationArabicPk());
			/* added to populate the DB Values added @Koti 20/02/2015 */
			relationsDescription1.setLocalRelationsDescription(relationsDatatableDtObj.getArabicRelationsDesc());
			relationsDescription1.setRelations(relations);
			relationsTypeService.saveRecordForDesc(relationsDescription1);
		} catch(NullPointerException  e){
			setErrmsg("Method Name:removeRecord"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrmsg(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		} 
	}

	// }
	// app started
	public void relationsPageApproval() {
		approveList();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "RelationshipApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RelationshipApproval.xhtml");
		} catch (Exception e) {
		 log.info( "Exception Occured While Redirecting to Approval Screen");
		setErrmsg("Error While Re-directing "+e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	List<Relations> relilst = new ArrayList<Relations>();
	List<RelationsDatatable> relationsDT = new ArrayList<RelationsDatatable>();

	public List<RelationsDatatable> getRelationsDT() {
		return relationsDT;
	}

	public List<Relations> getRelilst() {
		return relilst;
	}

	public void setRelilst(List<Relations> relilst) {
		this.relilst = relilst;
	}

	public void setRelationsDT(List<RelationsDatatable> relationsDT) {
		this.relationsDT = relationsDT;
	}

	public void approveList() {
		relationsDT.clear();
		try{
		relilst = relationsTypeService.getRelationListApproval();
		if (relilst.size() > 0) {
			for (Relations relations : relilst) {
		
				RelationsDatatable datatable = new RelationsDatatable();
				datatable.setRelationsId(relations.getRelationsCode());
				datatable.setEnglishRelationsDesc(relationsTypeService.getEngRelation(relations.getRelationsId()));
				datatable.setArabicRelationsDesc(relationsTypeService.getArabicRelation(relations.getRelationsId()));
				datatable.setCreatedBy(relations.getCreatedBy());
				datatable.setCreatedDate(relations.getCreatedDate());
				datatable.setModifiedBy(relations.getModifiedBy());
				datatable.setModifiedDate(relations.getModifiedDate());
				datatable.setRelationsPk(relations.getRelationsId());
				datatable.setIsActive(relations.getIsActive());
				datatable.setRemarks(relations.getRemarks());
				relationsDT.add(datatable);
			
			}
		}
		}catch(NullPointerException  e){
			setErrmsg("Method approveList"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrmsg(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		} 
	}

	// THIS METHOD CALLED CLICK ON APPROVE
	public void navigationEdit(RelationsDatatable datatable) {
		relationsDT.clear();
		if ((datatable.getModifiedBy() == null ? datatable.getCreatedBy() : datatable.getModifiedBy()).equalsIgnoreCase(session.getUserName())) {
			RequestContext.getCurrentInstance().execute("username.show();");
		} else {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RelationShip.xhtml");
			} catch (IOException e) {
				setErrmsg("Error While  approving records"+e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
		 try{
			setBooReadonly(true);
			setBoohideButton(false);
			setBoohideSecod(true);
			setBooRenderDataTable(false);
			setBooRenderSave(false);
			setRelationsId(datatable.getRelationsId());
			setRelationsPk(datatable.getRelationsPk());
			setEnglishRelationsDesc(relationsTypeService.getEngRelation(datatable.getRelationsPk()));
			setArabicRelationsDesc(relationsTypeService.getArabicRelation(datatable.getRelationsPk()));
			setCreatedBy(datatable.getCreatedBy());
			setCreatedDate(datatable.getCreatedDate());
			setModifiedBy(datatable.getModifiedBy());
			setModifiedDate(datatable.getModifiedDate());
			setIsActive(datatable.getIsActive());
		 }catch(NullPointerException  e){
				setErrmsg("Method Name:navigationEdit"+e.getMessage()); 
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
			catch(Exception e){
				setErrmsg(e.getMessage()); 
				RequestContext.getCurrentInstance().execute("csp.show();");
			} 
		}
	}

	// THIS METHOD CALLED WHEN CLICK ON APPROVE BUTTON
	public void approval() {
		/*
		 * Relations relations = new Relations();
		 * relations.setRelationsId(getRelationsPk());
		 * relations.setRelationsCode(getRelationsId());
		 * relations.setApprovedBy(session.getUserName());
		 * relations.setApprovedDate(new Date());
		 * 
		 * relations.setCreatedBy(getCreatedBy());
		 * relations.setCreatedDate(getCreatedDate());
		 * 
		 * relations.setModifiedBy(getModifiedBy());
		 * relations.setModifiedDate(getModifiedDate());
		 * relations.setIsActive(Constants.Yes);
		 * 
		 * System.out.println(getRelationsPk() + "to Print the relations pk");
		 * relations.setEnglishRelationsTypeDesc(getEnglishRelationsDesc());
		 * 
		 * relations.setLocalRelationsTypeDesc(getArabicRelationsDesc());
		 * System.out.println("" + relations.getLocalRelationsTypeDesc());
		 * relationsTypeService.saveRecord(relations);
		 */
		String approveMsg = relationsTypeService.approveRecord(getRelationsPk(), session.getUserName());
		if (approveMsg.equalsIgnoreCase("Success")) {
			RequestContext.getCurrentInstance().execute("approv.show();");
		} else {
			RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
		}
	}

	public void clickOnOkButton() {
		approveList();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RelationshipApproval.xhtml");
		} catch (Exception e) {
			setErrmsg("Error While  approving records"+e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void cancel() {
		approveList();
		// relationsDT.clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RelationshipApproval.xhtml");
		} catch (Exception e) {
			setErrmsg("Error While  approval cancel"+e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void remarksDialogOk() {
		try {
			relationData.setRemarks(getRemarks());
			delete(relationData);
			setRemarks(null);
			relationsDatatableDTList.clear();
			getAllDataRecords();
			relationsDatatableDTList.addAll(relationsDatatablelist);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RelationShip.xhtml");
		} catch (Exception e) {
			setErrmsg("Error While Remarks"+e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	private String createdBy = null;
	private Date createdDate = null;
	private String modifiedBy = null;
	private Date modifiedDate = null;
	private String isActive = null;
	private String userName;
	private String dynamicLabelForActivateDeactivate;
	private Boolean checkSave;

	public Boolean getCheckSave() {
		return checkSave;
	}

	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

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

	private String checkData;
	private boolean boohideButton = true;
	private boolean boohideSecod = false;
	private boolean booReadonly = false;
	private Boolean renderEditButton = false;
	private String approvedBy;
	private Date approvedDate;

	public Boolean getRenderEditButton() {
		return renderEditButton;
	}

	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
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

	public void clickOnOk() {
		//approval();
		approveList();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RelationshipApproval.xhtml");
		} catch (Exception e) {
			setErrmsg("Error While  Approval "+e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public boolean isBooSubmit() {
		return booSubmit;
	}

	public void setBooSubmit(boolean booSubmit) {
		this.booSubmit = booSubmit;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void checkStatusType(RelationsDatatable relationCodeMasterDTObj) throws IOException {
		try{
		if (relationCodeMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			relationCodeMasterDTObj.setBooCheckDelete(relationCodeMasterDTObj.getBooCheckDelete());
			relationCodeMasterDTObj.setRemarkCheck(true);
			setApprovedBy(relationCodeMasterDTObj.getApprovedBy());
			setApprovedDate(relationCodeMasterDTObj.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
		} else if (relationCodeMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			relationCodeMasterDTObj.setBooCheckDelete(relationCodeMasterDTObj.getBooCheckDelete());
			removeRecord(relationCodeMasterDTObj);
		} else if (relationCodeMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && relationCodeMasterDTObj.getRemarks() == null && relationCodeMasterDTObj.getApprovedBy() == null && relationCodeMasterDTObj.getApprovedDate() == null
				&& relationCodeMasterDTObj.getModifiedBy() == null && relationCodeMasterDTObj.getModifiedDate() == null && relationCodeMasterDTObj.getRemarks() == null) {
			relationCodeMasterDTObj.setBooCheckDelete(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
		} else if (relationCodeMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			removeRecord(relationCodeMasterDTObj);
		} else {
			RequestContext.getCurrentInstance().execute("couldnot.show();");
		}
		}catch(NullPointerException  e){
			setErrmsg("Method Name:checkStatusType"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrmsg(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		} 
	}

	public void removeRecordFromDB(RelationsDatatable relationCodeMasterDTObj) {
		try{
		RelationsDescription relationsDescription = new RelationsDescription();
		LanguageType languageType = new LanguageType();
		languageType.setLanguageId(new BigDecimal(1));
		relationsDescription.setLanguageType(languageType);
		/* added to populate the DB Values added @Koti 20/02/2015 */
		relationsDescription.setRelationsDescriptionId(relationCodeMasterDTObj.getRelationEnglishPk());
		/* ended to populate the DB Values added @Koti 20/02/2015 */
		relationsDescription.setLocalRelationsDescription(relationCodeMasterDTObj.getEnglishRelationsDesc());
		// relationsDescription.setRelations(relations);
		relationsTypeService.delete(relationsDescription);
		RelationsDescription relationsDescription1 = new RelationsDescription();
		LanguageType languageType1 = new LanguageType();
		languageType1.setLanguageId(new BigDecimal(2));
		relationsDescription1.setLanguageType(languageType1);
		/* added to populate the DB Values added @Koti 20/02/2015 */
		relationsDescription1.setRelationsDescriptionId(relationCodeMasterDTObj.getRelationArabicPk());
		/* added to populate the DB Values added @Koti 20/02/2015 */
		relationsDescription1.setLocalRelationsDescription(relationCodeMasterDTObj.getArabicRelationsDesc());
		// relationsDescription1.setRelations(relations);
		relationsTypeService.delete(relationsDescription1);
		Relations relations = new Relations();
		relations.setRelationsId(relationCodeMasterDTObj.getRelationsPk());
		/* ended to populate the DB Values added @Koti 20/02/2015 */
		relations.setRelationsCode(relationCodeMasterDTObj.getRelationsId());
		relations.setEnglishRelationsTypeDesc(relationCodeMasterDTObj.getEnglishRelationsDesc());
		relations.setLocalRelationsTypeDesc(relationCodeMasterDTObj.getArabicRelationsDesc());
		if (relationCodeMasterDTObj.getRelationsPk() != null) {
			relations.setModifiedDate(new Date());
			relations.setModifiedBy(session.getUserName());
		}
		relations.setCreatedBy(session.getUserName());
		relations.setCreatedDate(new Date());
		relationsTypeService.delete(relations);
		relationsDatatableDTList.remove(relationCodeMasterDTObj);
		}catch(NullPointerException  e){
			setErrmsg("Method removeRecordFromDB"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrmsg(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		} 
	}

	public void confirmPermanentDelete() {
		if (relationsDatatableDTList.size() > 0) {
			for (RelationsDatatable relationsDatatable : relationsDatatableDTList) {
				if (relationsDatatable.getBooCheckDelete()) {
					removeRecordFromDB(relationsDatatable);
				}
			}
		}
	}

	public void remarkSelectedRecord() throws IOException {
		for (RelationsDatatable relationCodeMasterDTObj : relationsDatatableDTList) {
			if (relationCodeMasterDTObj.getRemarkCheck() != null) {
				if (relationCodeMasterDTObj.getRemarkCheck().equals(true)) {
					relationCodeMasterDTObj.setRemarks(getRemarks());
					removeRecord(relationCodeMasterDTObj);
					setRemarks(null);
				}
			}
		}
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

	public void cancelRemarks() {
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/RelationShip.xhtml");
		} catch (Exception e) {
			setErrmsg("Error While   Remarks cancel:"+e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	
	
	public void clickOnOkExisted(){
		setRelationArabicPk(null);
		setRelationEnglishPk(null);
		setRelationsPk(null);
		setRelationsId(null);
		setArabicRelationsDesc(null);
		setEnglishRelationsDesc(null);
	
	}
	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}

	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}

	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}

	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}

	public Boolean getBooDisableClear() {
		return booDisableClear;
	}

	public void setBooDisableClear(Boolean booDisableClear) {
		this.booDisableClear = booDisableClear;
	}

	public Boolean getBooDisableEdit() {
		return booDisableEdit;
	}

	public void setBooDisableEdit(Boolean booDisableEdit) {
		this.booDisableEdit = booDisableEdit;
	}
}
