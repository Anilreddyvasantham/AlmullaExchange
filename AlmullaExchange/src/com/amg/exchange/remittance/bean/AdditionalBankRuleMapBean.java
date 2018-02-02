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

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.AdditionalBankRuleFlexFieldView;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.service.IAdditionalBankRuleAddService;
import com.amg.exchange.remittance.service.IAdditionalBankRuleAmiecService;
import com.amg.exchange.remittance.service.IAdditionalBankRuleMapService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("additionalBankRuleMapBean")
@Scope("session")
public class AdditionalBankRuleMapBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AdditionalBankRuleMapBean.class);

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IAdditionalBankRuleMapService additionalBankRuleMapService;

	@Autowired
	IAdditionalBankRuleAmiecService additionalBankRuleAmiecService;

	@Autowired
	IAdditionalBankRuleAddService additionalBankRuleAddService;

	SessionStateManage sessionStateManage = new SessionStateManage();

	private BigDecimal additionabankrulemapPK;
	private BigDecimal countryId;
	private String countryName;
	private String dBflexField;
	private String fieldName;
	//private String flexField;
	private String countryNameForBankMap;
	private BigDecimal orderNo;
	private String flexFieldForBankMap;
	private String flexFieldNameForBankMap;
	private String orderNoForBankMap;
	private String dynamicLabelForActivateDeactivateForBankMap;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private String approveBy;
	private Date approveDate;
	private Date activateDate;
	private String activateBy;
	private String errorMessage;
	private String remarksForBankMap;
	private Boolean editClikedBankMapRule=false;
	int i = 0;

	private Boolean editButton;
	private Boolean renderCountryIdBankMapForApprove = false;
	private Boolean renderFlexFieldBankMap = false;
	private Boolean booDBFlexField = false;
	private Boolean renderFlexFieldBankMapForApprove = false;
	private Boolean renderFlexNameBankMap = false;
	private Boolean renderFlexNameBankMapForApprove = false;
	private Boolean renderOrderNoBankMap = false;
	private Boolean renderOrderNoBankMapForApprove = false;
	private Boolean renderAddButtonPanelForBankMap = false;
	private Boolean renderApproveCancelButtonPanelForBankMap = false;
	private boolean additionalBankRuleMapRendered = false;
	private boolean saveAdditionalBankRule1 = false;
	private Boolean disableSubmitButtonForBankMap = false;
	private Boolean disableEditBankRuleMap = false;
	private Boolean renderCountryIdBankMap = false;
	private Boolean booNewFlexField = true;
	private Boolean clearPanel;

	private List<CountryMasterDesc> countryList = new ArrayList<CountryMasterDesc>();
	private List<AdditionalBankRuleMap> flexFieldList = new ArrayList<AdditionalBankRuleMap>();
	private List<AdditionalBankRuleDataTable> additionalBankRuleDataList1 = new CopyOnWriteArrayList<AdditionalBankRuleDataTable>();
	private List<AdditionalBankRuleDataTable> additionalBankRuleDataNewList1 = new CopyOnWriteArrayList<AdditionalBankRuleDataTable>();
	private List<AdditionalBankRuleMap> addingtoDataTable = new ArrayList<AdditionalBankRuleMap>();
	private List<AdditionalBankRuleMap> lstFlexFiledDB = new ArrayList<AdditionalBankRuleMap>();
	private List<AdditionalBankRuleMap> lstAllRecordsDB = new ArrayList<AdditionalBankRuleMap>();
	Map<BigDecimal, String> mapCountryList = new HashMap<BigDecimal, String>();
	public BigDecimal getAdditionabankrulemapPK() {
		return additionabankrulemapPK;
	}
	public void setAdditionabankrulemapPK(BigDecimal additionabankrulemapPK) {
		this.additionabankrulemapPK = additionabankrulemapPK;
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
	public String getdBflexField() {
		return dBflexField;
	}
	public void setdBflexField(String dBflexField) {
		this.dBflexField = dBflexField;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/*public String getFlexField() {
		return flexField;
	}
	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}*/
	public String getCountryNameForBankMap() {
		return countryNameForBankMap;
	}
	public void setCountryNameForBankMap(String countryNameForBankMap) {
		this.countryNameForBankMap = countryNameForBankMap;
	}
	public BigDecimal getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
	}
	public String getFlexFieldForBankMap() {
		return flexFieldForBankMap;
	}
	public void setFlexFieldForBankMap(String flexFieldForBankMap) {
		this.flexFieldForBankMap = flexFieldForBankMap;
	}
	public String getFlexFieldNameForBankMap() {
		return flexFieldNameForBankMap;
	}
	public void setFlexFieldNameForBankMap(String flexFieldNameForBankMap) {
		this.flexFieldNameForBankMap = flexFieldNameForBankMap;
	}
	public String getOrderNoForBankMap() {
		return orderNoForBankMap;
	}
	public void setOrderNoForBankMap(String orderNoForBankMap) {
		this.orderNoForBankMap = orderNoForBankMap;
	}
	public String getDynamicLabelForActivateDeactivateForBankMap() {
		return dynamicLabelForActivateDeactivateForBankMap;
	}
	public void setDynamicLabelForActivateDeactivateForBankMap(String dynamicLabelForActivateDeactivateForBankMap) {
		this.dynamicLabelForActivateDeactivateForBankMap = dynamicLabelForActivateDeactivateForBankMap;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getApproveBy() {
		return approveBy;
	}
	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	public Boolean getEditButton() {
		return editButton;
	}
	public void setEditButton(Boolean editButton) {
		this.editButton = editButton;
	}
	public Boolean getRenderCountryIdBankMapForApprove() {
		return renderCountryIdBankMapForApprove;
	}
	public void setRenderCountryIdBankMapForApprove(Boolean renderCountryIdBankMapForApprove) {
		this.renderCountryIdBankMapForApprove = renderCountryIdBankMapForApprove;
	}
	public Boolean getRenderFlexFieldBankMap() {
		return renderFlexFieldBankMap;
	}
	public void setRenderFlexFieldBankMap(Boolean renderFlexFieldBankMap) {
		this.renderFlexFieldBankMap = renderFlexFieldBankMap;
	}
	public Boolean getBooDBFlexField() {
		return booDBFlexField;
	}
	public void setBooDBFlexField(Boolean booDBFlexField) {
		this.booDBFlexField = booDBFlexField;
	}
	public Boolean getRenderFlexFieldBankMapForApprove() {
		return renderFlexFieldBankMapForApprove;
	}
	public void setRenderFlexFieldBankMapForApprove(Boolean renderFlexFieldBankMapForApprove) {
		this.renderFlexFieldBankMapForApprove = renderFlexFieldBankMapForApprove;
	}
	public Boolean getRenderFlexNameBankMap() {
		return renderFlexNameBankMap;
	}
	public void setRenderFlexNameBankMap(Boolean renderFlexNameBankMap) {
		this.renderFlexNameBankMap = renderFlexNameBankMap;
	}
	public Boolean getRenderFlexNameBankMapForApprove() {
		return renderFlexNameBankMapForApprove;
	}
	public void setRenderFlexNameBankMapForApprove(Boolean renderFlexNameBankMapForApprove) {
		this.renderFlexNameBankMapForApprove = renderFlexNameBankMapForApprove;
	}
	public Boolean getRenderOrderNoBankMap() {
		return renderOrderNoBankMap;
	}
	public void setRenderOrderNoBankMap(Boolean renderOrderNoBankMap) {
		this.renderOrderNoBankMap = renderOrderNoBankMap;
	}
	public Boolean getRenderOrderNoBankMapForApprove() {
		return renderOrderNoBankMapForApprove;
	}
	public void setRenderOrderNoBankMapForApprove(Boolean renderOrderNoBankMapForApprove) {
		this.renderOrderNoBankMapForApprove = renderOrderNoBankMapForApprove;
	}
	public Boolean getRenderAddButtonPanelForBankMap() {
		return renderAddButtonPanelForBankMap;
	}
	public void setRenderAddButtonPanelForBankMap(Boolean renderAddButtonPanelForBankMap) {
		this.renderAddButtonPanelForBankMap = renderAddButtonPanelForBankMap;
	}
	public Boolean getRenderApproveCancelButtonPanelForBankMap() {
		return renderApproveCancelButtonPanelForBankMap;
	}
	public void setRenderApproveCancelButtonPanelForBankMap(Boolean renderApproveCancelButtonPanelForBankMap) {
		this.renderApproveCancelButtonPanelForBankMap = renderApproveCancelButtonPanelForBankMap;
	}
	public boolean isAdditionalBankRuleMapRendered() {
		return additionalBankRuleMapRendered;
	}
	public void setAdditionalBankRuleMapRendered(boolean additionalBankRuleMapRendered) {
		this.additionalBankRuleMapRendered = additionalBankRuleMapRendered;
	}
	public boolean isSaveAdditionalBankRule1() {
		return saveAdditionalBankRule1;
	}
	public void setSaveAdditionalBankRule1(boolean saveAdditionalBankRule1) {
		this.saveAdditionalBankRule1 = saveAdditionalBankRule1;
	}
	public Boolean getDisableSubmitButtonForBankMap() {
		return disableSubmitButtonForBankMap;
	}
	public void setDisableSubmitButtonForBankMap(Boolean disableSubmitButtonForBankMap) {
		this.disableSubmitButtonForBankMap = disableSubmitButtonForBankMap;
	}
	public Boolean getDisableEditBankRuleMap() {
		return disableEditBankRuleMap;
	}
	public void setDisableEditBankRuleMap(Boolean disableEditBankRuleMap) {
		this.disableEditBankRuleMap = disableEditBankRuleMap;
	}
	public List<CountryMasterDesc> getCountryList() {
		return countryList;
	}
	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}
	public List<AdditionalBankRuleMap> getFlexFieldList() {
		return flexFieldList;
	}
	public void setFlexFieldList(List<AdditionalBankRuleMap> flexFieldList) {
		this.flexFieldList = flexFieldList;
	}
	public List<AdditionalBankRuleDataTable> getAdditionalBankRuleDataList1() {
		return additionalBankRuleDataList1;
	}
	public void setAdditionalBankRuleDataList1(List<AdditionalBankRuleDataTable> additionalBankRuleDataList1) {
		this.additionalBankRuleDataList1 = additionalBankRuleDataList1;
	}
	public List<AdditionalBankRuleDataTable> getAdditionalBankRuleDataNewList1() {
		return additionalBankRuleDataNewList1;
	}
	public void setAdditionalBankRuleDataNewList1(List<AdditionalBankRuleDataTable> additionalBankRuleDataNewList1) {
		this.additionalBankRuleDataNewList1 = additionalBankRuleDataNewList1;
	}
	public List<AdditionalBankRuleMap> getAddingtoDataTable() {
		return addingtoDataTable;
	}
	public void setAddingtoDataTable(List<AdditionalBankRuleMap> addingtoDataTable) {
		this.addingtoDataTable = addingtoDataTable;
	}
	public List<AdditionalBankRuleMap> getLstFlexFiledDB() {
		return lstFlexFiledDB;
	}
	public void setLstFlexFiledDB(List<AdditionalBankRuleMap> lstFlexFiledDB) {
		this.lstFlexFiledDB = lstFlexFiledDB;
	}
	public Map<BigDecimal, String> getMapCountryList() {
		return mapCountryList;
	}
	public void setMapCountryList(Map<BigDecimal, String> mapCountryList) {
		this.mapCountryList = mapCountryList;
	}

	public Boolean getRenderCountryIdBankMap() {
		return renderCountryIdBankMap;
	}

	public void setRenderCountryIdBankMap(Boolean renderCountryIdBankMap) {
		this.renderCountryIdBankMap = renderCountryIdBankMap;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<AdditionalBankRuleMap> getLstAllRecordsDB() {
		return lstAllRecordsDB;
	}
	public void setLstAllRecordsDB(List<AdditionalBankRuleMap> lstAllRecordsDB) {
		this.lstAllRecordsDB = lstAllRecordsDB;
	}
	public Boolean getBooNewFlexField() {
		return booNewFlexField;
	}
	public void setBooNewFlexField(Boolean booNewFlexField) {
		this.booNewFlexField = booNewFlexField;
	}
	public String getRemarksForBankMap() {
		return remarksForBankMap;
	}
	public void setRemarksForBankMap(String remarksForBankMap) {
		this.remarksForBankMap = remarksForBankMap;
	}
	public Boolean getEditClikedBankMapRule() {
		return editClikedBankMapRule;
	}
	public void setEditClikedBankMapRule(Boolean editClikedBankMapRule) {
		this.editClikedBankMapRule = editClikedBankMapRule;
	}
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
	public Boolean getClearPanel() {
		return clearPanel;
	}
	public void setClearPanel(Boolean clearPanel) {
		this.clearPanel = clearPanel;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// Navigation for Additional Bank Rule
	public void additionalBankRulePageNavigation() {
		//setBooEditableRecord(null);
		setRenderCountryIdBankMapForApprove(false);
		setRenderCountryIdBankMap(true);
		setCountryNameForBankMap(null);
		setRenderFlexFieldBankMapForApprove(false);
		setRenderFlexFieldBankMap(true);
		setFlexFieldNameForBankMap(null);
		setFlexFieldForBankMap(null);
		setRenderFlexNameBankMapForApprove(false);
		setRenderFlexNameBankMap(true);
		setRenderOrderNoBankMap(true);
		setRenderOrderNoBankMapForApprove(false);
		setOrderNoForBankMap(null);
		setRenderApproveCancelButtonPanelForBankMap(false);
		setRenderAddButtonPanelForBankMap(true);
		setAdditionabankrulemapPK(null);
		setClearPanel(false);
		//setCreatedByForBankRuleMap(null);
		// rahamath Additional Bank Rule Map Approval code
		setEditClikedBankMapRule(false);
		ClearAllRule1();
		setSaveAdditionalBankRule1(false);
		setAdditionalBankRuleMapRendered(false);
		//setBooRenderSaveExit(false);
		additionalBankRuleDataList1.clear();
		additionalBankRuleDataNewList1.clear();
		setDisableEditBankRuleMap(false);
		toFectchAllCountry();
		// calling flex fields view
		fetchAllFlexFields();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "additionalbankrulemap.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankrulemap.xhtml");
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("exception.getMessage():::::::::::::::::::::::::::::"+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	//clearAllFields
	public void ClearAllRule1() {
		additionalBankRuleDataNewList1.clear();
		if (additionalBankRuleDataList1.size() != 0) {
			additionalBankRuleDataList1.clear();
		}
		if (getLstAllRecordsDB().size() != 0) {
			lstAllRecordsDB.clear();
		}
		setCountryId(null);
		//setFlexField(null);
		setdBflexField(null);
		setFieldName(null);
		setOrderNo(null);
		setBooDBFlexField(false);
		setBooNewFlexField(true);
		setEditButton(false);
		setDisableSubmitButtonForBankMap(false);
		setDisableEditBankRuleMap(false);
		setDynamicLabelForActivateDeactivateForBankMap(null);
		setIsActive(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setApproveBy(null);
		setApproveDate(null);
		setRemarksForBankMap(null);
		setAdditionabankrulemapPK(null);
		setEditClikedBankMapRule(false);
		setModifiedBy(null);
		setModifiedDate(null);
	}
	//to fetch all counties on form loading
	public void toFectchAllCountry() {
		try {
			countryList = generalService.getCountryList(sessionStateManage.getLanguageId());
			for (CountryMasterDesc countryMaster : countryList) {
				mapCountryList.put(countryMaster.getCountryMasterId(), countryMaster.getCountryName());
			}
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("exception.getMessage():::::::::::::::::::::::::::::" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	// Fetch Flex field Data
	public void fetchData() {
		try {
			List<AdditionalBankRuleMap> lstflexfielddata = new ArrayList<AdditionalBankRuleMap>();
			lstflexfielddata = additionalBankRuleAmiecService.getLstofFlexFields(getCountryId());
			if (lstflexfielddata.size() > 1) {
				setdBflexField(null);
				//setFlexField(null);
				setLstFlexFiledDB(lstflexfielddata);
				setEditButton(true);
				setBooDBFlexField(true);
				setBooNewFlexField(false);
			} else if (lstflexfielddata.size() == 1) {
				setLstFlexFiledDB(lstflexfielddata);
				setdBflexField(null);
				//setFlexField(null);
				setEditButton(true);
				setBooDBFlexField(true);
				setBooNewFlexField(false);
			} else {
				setEditButton(false);
				setBooDBFlexField(false);
				setBooNewFlexField(true);
				setdBflexField(null);
				//setFlexField(null);
				setFieldName(null);
				setOrderNo(null);
				if (getLstFlexFiledDB() != null) {
					lstFlexFiledDB.clear();
				}
			}
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("exception.getMessage():::::::::::::::::::::::::::::" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}
	//fetch Db Records
	public void fetchDataBaseRecords() {
		try {
			/*if (getdBflexField() != null) {
				setFlexField(getdBflexField());
			}*/
			if (getCountryId() != null && getdBflexField() != null) {
				//if (getEditClikedBankMapRule().equals(false)) {
					List<AdditionalBankRuleMap> lstallRecordsDB = new ArrayList<AdditionalBankRuleMap>();
					lstallRecordsDB = additionalBankRuleAmiecService.getAllRecordbyCountryFlex(getCountryId(), getdBflexField());
					if (lstallRecordsDB.size() != 0) {
						for (AdditionalBankRuleMap additionalBankRuleMap : lstallRecordsDB) {
							if(additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.Yes)){
								// for disabling  Submit  button
								setDisableSubmitButtonForBankMap(true);
								setDisableEditBankRuleMap(true);
								// for activate  deactivate
								if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.Yes)) {
									setDynamicLabelForActivateDeactivateForBankMap(Constants.DEACTIVATE);
								} else if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.D)) {
									setDynamicLabelForActivateDeactivateForBankMap(Constants.ACTIVATE);
								} else if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.U) && additionalBankRuleMap.getModifiedBy() == null && additionalBankRuleMap.getModifiedDate() == null && additionalBankRuleMap.getApprovedBy() == null
										&& additionalBankRuleMap.getApprovedDate() == null && additionalBankRuleMap.getRemarks() == null) {
									setDynamicLabelForActivateDeactivateForBankMap(Constants.DELETE);
								} else {
									setDynamicLabelForActivateDeactivateForBankMap(Constants.PENDING_FOR_APPROVE);
								}

								setIsActive(additionalBankRuleMap.getIsActive());
								setCreatedBy(additionalBankRuleMap.getCreatedBy());
								setCreatedDate(additionalBankRuleMap.getCreatedDate());
								setApproveBy(additionalBankRuleMap.getApprovedBy());
								setApproveDate(additionalBankRuleMap.getApprovedDate());
								setRemarksForBankMap(additionalBankRuleMap.getRemarks());
								setAdditionabankrulemapPK(additionalBankRuleMap.getAdditionalBankRuleId());
								//setEditClikedBankMapRule(true);
								setModifiedBy(additionalBankRuleMap.getModifiedBy());
								setModifiedDate(additionalBankRuleMap.getModifiedDate());
								setCountryId(additionalBankRuleMap.getCountryId().getCountryId());
								//setFlexField(additionalBankRuleMap.getFlexField());
								setdBflexField(additionalBankRuleMap.getFlexField());
								setFieldName(additionalBankRuleMap.getFieldName());
								setOrderNo(additionalBankRuleMap.getOrderNo());
								break;
							}else {
								setFieldName(null);
								setOrderNo(null);
							}
						}
					} else {
						setFieldName(null);
						setOrderNo(null);
					}
				//}
			}
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("exception.getMessage():::::::::::::::::::::::::::::" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}
	//insert for new Record
	public void insertNewFlexField() {
		setBooDBFlexField(false);
		setBooNewFlexField(true);
		setEditButton(false);
		setdBflexField(null);
		//setFlexField(null);
		setFieldName(null);
		setOrderNo(null);
		setAdditionabankrulemapPK(null);
		clearAllRule1();
	}

	// Clear Methods
	public void clearAllRule1() {

		// setCountryId(null);
		//setFlexField(null);
		setdBflexField(null);
		setFieldName(null);
		setOrderNo(null);
		setAdditionabankrulemapPK(null);
		if (getLstFlexFiledDB().size() != 0) {
			lstFlexFiledDB.clear();
		}
		setBooDBFlexField(false);
		setBooNewFlexField(true);
		setEditButton(false);
		setCreatedBy(null);
		setCreatedDate(null);
		setApproveBy(null);
		setApproveDate(null);
		setEditClikedBankMapRule(false);

	}

	// hide submit button
	public void hideSubmitButton() {
		if (getOrderNo() != null || getFieldName() != null) {
			setDisableSubmitButtonForBankMap(true);
		} else {
			setDisableSubmitButtonForBankMap(false);
		}
	}

	// Add to Additional Bank Rule Data table
	public void addAdditionalBankRule1DataTable() {
		setDisableSubmitButtonForBankMap(false);
		setDisableEditBankRuleMap(false);
		try {
			List<AdditionalBankRuleMap> bankRuleMapList = additionalBankRuleMapService.duplicateCheckInDBBankMap(getCountryId(), getdBflexField(), getFieldName(), getOrderNo());
			if (bankRuleMapList.size() > 0 && getEditClikedBankMapRule().equals(false)) {
				clearAllRule1();
				RequestContext.getCurrentInstance().execute("recordExistInDB.show();");
				return;
			} else {
				duplicateCheckInDataTabelBankMap();
			}
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("exception.getMessage():::::::::::::::::::::::::::::" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	//duplicate checking
	public void duplicateCheckInDataTabelBankMap(){			
		if (additionalBankRuleDataList1.size() != 0) {
			i = 0;
			for (AdditionalBankRuleDataTable dtablefinal : additionalBankRuleDataList1) {
				if (dtablefinal.getFlexField().equalsIgnoreCase(getdBflexField())) {
					i = 1;
					RequestContext.getCurrentInstance().execute("recordExists.show();");
					clearAllRule1();
					break;
				} else {
					i = 0;
				}
			}
		} else {
			i = 0;
		}
		if (i == 0) {
			finaladdtoSaveUpdate();
		}
	}

	// datatable adding Final Save
	public void finaladdtoSaveUpdate() {
		try {
			setClearPanel(false);
			Boolean activecheck = false;
			AdditionalBankRuleDataTable additionalBankRuleDataTable = new AdditionalBankRuleDataTable();
			additionalBankRuleDataTable.setAdditionalBankRuleMapPk(getAdditionabankrulemapPK());
			additionalBankRuleDataTable.setCountryId(getCountryId());
			additionalBankRuleDataTable.setCountryName(generalService.getCountryName(getCountryId()));
			additionalBankRuleDataTable.setFieldName(getFieldName());
			additionalBankRuleDataTable.setFlexField(getdBflexField());
			additionalBankRuleDataTable.setOrderNo(getOrderNo());
			if (getAdditionabankrulemapPK() != null) {
				List<AdditionalBankRuleMap> lstAdditionalBankRuleMaps = additionalBankRuleMapService.tofetchAll(getCountryId());
				for (AdditionalBankRuleMap additionalBankRuleMap : lstAdditionalBankRuleMaps) {
					if (lstAdditionalBankRuleMaps.size() != 0) {
						if (additionalBankRuleMap.getFlexField().equalsIgnoreCase(getdBflexField()) && additionalBankRuleMap.getFieldName().equalsIgnoreCase(getFieldName()) && additionalBankRuleMap.getOrderNo().equals(getOrderNo())) {
							additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankMap(getDynamicLabelForActivateDeactivateForBankMap());
							additionalBankRuleDataTable.setIsActive(getIsActive());
							additionalBankRuleDataTable.setCreatedBy(getCreatedBy());
							additionalBankRuleDataTable.setCreatedDate(getCreatedDate());
							additionalBankRuleDataTable.setApproveBy(getApproveBy());
							additionalBankRuleDataTable.setApproveDate(getApproveDate());
							additionalBankRuleDataTable.setModifiedBy(getModifiedBy());
							additionalBankRuleDataTable.setModifiedDate(getModifiedDate());
							additionalBankRuleDataTable.setRemarks(getRemarksForBankMap());
							activecheck=true;
						} else {
							additionalBankRuleDataTable.setIsActive(Constants.U);
							additionalBankRuleDataTable.setCreatedBy(getCreatedBy());
							additionalBankRuleDataTable.setCreatedDate(getCreatedDate());
							additionalBankRuleDataTable.setApproveBy(null);
							additionalBankRuleDataTable.setApproveDate(null);
							additionalBankRuleDataTable.setModifiedBy(sessionStateManage.getUserName());
							additionalBankRuleDataTable.setModifiedDate(new Date());
							additionalBankRuleDataTable.setRemarks(null);
							additionalBankRuleDataTable.setEditClikedBankMapRule(getEditClikedBankMapRule());
						}
					}
					if(activecheck.equals(true)){
						additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankMap(getDynamicLabelForActivateDeactivateForBankMap());
					}else{
						additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankMap(Constants.PENDING_FOR_APPROVE);
					}
				}

			} else {
				additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankMap(Constants.REMOVE);
				additionalBankRuleDataTable.setIsActive(Constants.U);
				additionalBankRuleDataTable.setCreatedBy(sessionStateManage.getUserName());
				additionalBankRuleDataTable.setEditClikedBankMapRule(true);
				additionalBankRuleDataTable.setCreatedDate(new Date());
			}
			additionalBankRuleDataList1.add(additionalBankRuleDataTable);
			if (getAdditionabankrulemapPK() == null) {
				additionalBankRuleDataNewList1.add(additionalBankRuleDataTable);
			}
			setAdditionalBankRuleMapRendered(true);
			setSaveAdditionalBankRule1(true);
			clearAllRule1();
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("exception.getMessage():::::::::::::::::::::::::::::" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	// to click on view button
	public void getAdditionBankRuleMapRecords() {
		try {
			clearAllRule1();
			setDisableSubmitButtonForBankMap(false);
			additionalBankRuleDataList1.clear();
			if (getCountryId() != null) {
				List<AdditionalBankRuleMap> additionalBankRuleMapList = additionalBankRuleMapService.toFetchAdditionBankRuleMapRecordsForView(getCountryId());
				if (additionalBankRuleMapList != null && additionalBankRuleMapList.size() > 0) {
					for (AdditionalBankRuleMap additionalBankRuleMap : additionalBankRuleMapList) {
						AdditionalBankRuleDataTable additionalBankRuleDataTable = new AdditionalBankRuleDataTable();
						if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.Yes)) {
							additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankMap(Constants.DEACTIVATE);
						} else if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.D)) {
							additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankMap(Constants.ACTIVATE);
						} else if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.U) && additionalBankRuleMap.getModifiedBy() == null && additionalBankRuleMap.getModifiedDate() == null && additionalBankRuleMap.getApprovedBy() == null
								&& additionalBankRuleMap.getApprovedDate() == null && additionalBankRuleMap.getRemarks() == null) {
							additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankMap(Constants.DELETE);
						} else {
							additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankMap(Constants.PENDING_FOR_APPROVE);
						}
						setClearPanel(false);
						additionalBankRuleDataTable.setAdditionalBankRuleMapPk(additionalBankRuleMap.getAdditionalBankRuleId());
						additionalBankRuleDataTable.setCountryId(additionalBankRuleMap.getCountryId().getCountryId());
						additionalBankRuleDataTable.setCountryName(generalService.getCountryName(additionalBankRuleMap.getCountryId().getCountryId()));
						additionalBankRuleDataTable.setFieldName(additionalBankRuleMap.getFieldName());
						additionalBankRuleDataTable.setFlexField(additionalBankRuleMap.getFlexField());
						additionalBankRuleDataTable.setOrderNo(additionalBankRuleMap.getOrderNo());
						additionalBankRuleDataTable.setCreatedBy(additionalBankRuleMap.getCreatedBy());
						additionalBankRuleDataTable.setCreatedDate(additionalBankRuleMap.getCreatedDate());
						additionalBankRuleDataTable.setIsActive(additionalBankRuleMap.getIsActive());
						additionalBankRuleDataTable.setApproveBy(additionalBankRuleMap.getApprovedBy());
						additionalBankRuleDataTable.setApproveDate(additionalBankRuleMap.getApprovedDate());
						additionalBankRuleDataTable.setModifiedBy(additionalBankRuleMap.getModifiedBy());
						additionalBankRuleDataTable.setModifiedDate(additionalBankRuleMap.getModifiedDate());
						additionalBankRuleDataTable.setRemarks(additionalBankRuleMap.getRemarks());
						additionalBankRuleDataList1.add(additionalBankRuleDataTable);

					}
					setAdditionalBankRuleMapRendered(true);
					setSaveAdditionalBankRule1(true);

				} else {
					if (additionalBankRuleDataNewList1 != null && additionalBankRuleDataNewList1.size() == 0) {
						RequestContext.getCurrentInstance().execute("noDataFound.show();");
						return;
					}
				}
			} else {
				RequestContext.getCurrentInstance().execute("selectCountry.show();");
				return;
			}
			if (additionalBankRuleDataNewList1.size() != 0) {
				additionalBankRuleDataList1.addAll(additionalBankRuleDataNewList1);
			}
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("exception.getMessage():::::::::::::::::::::::::::::" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	// Edit record
	public void editRecordDTBankMap(AdditionalBankRuleDataTable addBankRule) {
		// for disabling Submit button
		setDisableSubmitButtonForBankMap(true);
		setDisableEditBankRuleMap(true);
		// for activate deactivate

		setCreatedBy(addBankRule.getCreatedBy());
		setCreatedDate(addBankRule.getCreatedDate());

		setRemarksForBankMap(addBankRule.getRemarks());
		setAdditionabankrulemapPK(addBankRule.getAdditionalBankRuleMapPk());
		setEditClikedBankMapRule(true);
		setClearPanel(true);
		setModifiedBy(addBankRule.getModifiedBy());
		setModifiedDate(addBankRule.getModifiedDate());
		setIsActive(addBankRule.getIsActive());
		setApproveBy(addBankRule.getApproveBy());
		setApproveDate(addBankRule.getApproveDate());
		setDynamicLabelForActivateDeactivateForBankMap(addBankRule.getDynamicLabelForActivateDeactivateForBankMap());
		/* if (addBankRule.getAdditionalBankRuleMapPk() != null) {
			      setModifiedBy(sessionStateManage.getUserName());
			      setModifiedDate(new Date());
			      setIsActive(Constants.U);
			      setApproveBy(null);
			      setApproveDate(null);
			      setDynamicLabelForActivateDeactivateForBankMap(null);
		    } else {
			      setModifiedBy(addBankRule.getModifiedBy());
			      setModifiedDate(addBankRule.getModifiedDate());
			      setIsActive(addBankRule.getIsActive());
			      setApproveBy(addBankRule.getApproveBy());
			      setApproveDate(addBankRule.getApproveDate());
			      setDynamicLabelForActivateDeactivateForBankMap(addBankRule.getDynamicLabelForActivateDeactivateForBankMap());
		    }*/
		// for activate deactivate
		setCountryId(addBankRule.getCountryId());
		//setFlexField(addBankRule.getFlexField());
		setdBflexField(addBankRule.getFlexField());
		setBooDBFlexField(false);
		setBooNewFlexField(true);
		setFieldName(addBankRule.getFieldName());
		setOrderNo(addBankRule.getOrderNo());
		additionalBankRuleDataList1.remove(addBankRule);
		additionalBankRuleDataNewList1.remove(addBankRule);
		if (additionalBankRuleDataList1.size() == 0) {
			setSaveAdditionalBankRule1(false);
			setAdditionalBankRuleMapRendered(false);
		}
	}
	//check status
	public void checkStatusTypeForBankMap(AdditionalBankRuleDataTable addBankRule) throws IOException {
		if(addBankRule.getDynamicLabelForActivateDeactivateForBankMap().equalsIgnoreCase(Constants.REMOVE)){
			additionalBankRuleDataList1.remove(addBankRule);
			additionalBankRuleDataNewList1.remove(addBankRule);
		}else if (addBankRule.getDynamicLabelForActivateDeactivateForBankMap().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
			RequestContext.getCurrentInstance().execute("pending.show();");
			return;	  
		}else if (addBankRule.getDynamicLabelForActivateDeactivateForBankMap().equalsIgnoreCase(Constants.DEACTIVATE)) {
			addBankRule.setRemarkCheckForBankMap(true);
			setActivateDate(addBankRule.getApproveDate());
			setActivateBy(addBankRule.getApproveBy());
			RequestContext.getCurrentInstance().execute("remarks.show();");
			return;
		}else if (addBankRule.getDynamicLabelForActivateDeactivateForBankMap().equalsIgnoreCase(Constants.ACTIVATE)) {
			addBankRule.setActivateRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;
		}else if (addBankRule.getDynamicLabelForActivateDeactivateForBankMap().equalsIgnoreCase(Constants.DELETE)) {
			addBankRule.setPermanetDeleteCheckForBankMap(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			return;
		}	
		if (additionalBankRuleDataList1.size() == 0) {
			setSaveAdditionalBankRule1(false);
			setAdditionalBankRuleMapRendered(false);
		}

	}

	public void confirmPermanentDeleteCancelMap(){
		if (additionalBankRuleDataList1.size() > 0) {
			for (AdditionalBankRuleDataTable addBankRule : additionalBankRuleDataList1) {
				if (addBankRule.getPermanetDeleteCheckForBankMap() != null) {
					if (addBankRule.getPermanetDeleteCheckForBankMap().equals(true)) {
						addBankRule.setPermanetDeleteCheckForBankMap(false);    
					}
				}
			}
		}    
	}

	public void confirmPermanentDeleteBankRuleMap() {
		if (additionalBankRuleDataList1.size() > 0) {
			for (AdditionalBankRuleDataTable addBankRule : additionalBankRuleDataList1) {
				if (addBankRule.getPermanetDeleteCheckForBankMap() != null) {
					if (addBankRule.getPermanetDeleteCheckForBankMap().equals(true)) {
						deleteRecordPermanentlyBankRuleMap(addBankRule);
						additionalBankRuleDataList1.remove(addBankRule);
					}
				}
			}
		}
	}

	public void deleteRecordPermanentlyBankRuleMap(AdditionalBankRuleDataTable addBankRule) {
		additionalBankRuleMapService.deleteBankRuleMapRecord(addBankRule.getAdditionalBankRuleMapPk());
	}

	public void activateRecordBankRuleMap() {
		if (additionalBankRuleDataList1.size() > 0) {
			for (AdditionalBankRuleDataTable addBankRule : additionalBankRuleDataList1) {
				if (addBankRule.getActivateRecordCheck() != null) {
					if (addBankRule.getActivateRecordCheck().equals(true)) {
						confirmActivateBankRuleMap(addBankRule);
					}
				}
			}
		}
	}

	public void confirmActivateBankRuleMap(AdditionalBankRuleDataTable addBankRule) {
		additionalBankRuleMapService.activateBankRuleMapRecord(addBankRule.getAdditionalBankRuleMapPk(), sessionStateManage.getUserName());
		getAdditionBankRuleMapRecords();
	}

	public void remarkSelectedRecordForBankMap() throws IOException {
		for (AdditionalBankRuleDataTable additionalBankMapDT : additionalBankRuleDataList1) {
			if (additionalBankMapDT.getRemarkCheckForBankMap() != null) {
				if (additionalBankMapDT.getRemarkCheckForBankMap().equals(true)) {
					if (getRemarksForBankMap() != null) {
						additionalBankMapDT.setRemarksForBankMap(getRemarksForBankMap());
						additionalBankRuleMapService.remarkBankRuleMapRecord(additionalBankMapDT.getAdditionalBankRuleMapPk(), getRemarksForBankMap(), sessionStateManage.getUserName());
						setRemarksForBankMap(null);
						setActivateDate(null);
						setActivateBy(null);
						getAdditionBankRuleMapRecords();
						try {
							FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankrulemap.xhtml");
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
						return;
					}

				}
			}
		}

	}

	public void clearRemarksBankMap() {
		setRemarksForBankMap(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankrulemap.xhtml");
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.error("Error Occured while Saving DeliveryMode and DeliveryModeDesc Records  :" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;

		}
	}

	// Save Additional Bank Rule Map
	public void saveAdditionalBankRule1() {
		if (additionalBankRuleDataList1.isEmpty()) {
			RequestContext.getCurrentInstance().execute("norecords.show();");
		} else {
			try {
				if (additionalBankRuleDataList1.size() > 0) {
					for (AdditionalBankRuleDataTable additionalBankRuleObj : additionalBankRuleDataList1) {

						if (additionalBankRuleObj.getEditClikedBankMapRule().equals(true)) {
							AdditionalBankRuleMap additionalBankRuleMap = new AdditionalBankRuleMap();

							additionalBankRuleMap.setAdditionalBankRuleId(additionalBankRuleObj.getAdditionalBankRuleMapPk());

							CountryMaster countryMaster = new CountryMaster();
							countryMaster.setCountryId(additionalBankRuleObj.getCountryId());
							additionalBankRuleMap.setCountryId(countryMaster);

							additionalBankRuleMap.setFlexField(additionalBankRuleObj.getFlexField());
							additionalBankRuleMap.setFieldName(additionalBankRuleObj.getFieldName());
							additionalBankRuleMap.setOrderNo(additionalBankRuleObj.getOrderNo());

							additionalBankRuleMap.setCreatedBy(additionalBankRuleObj.getCreatedBy());
							additionalBankRuleMap.setCreatedDate(additionalBankRuleObj.getCreatedDate());
							additionalBankRuleMap.setApprovedBy(additionalBankRuleObj.getApproveBy());
							additionalBankRuleMap.setApprovedDate(additionalBankRuleObj.getApproveDate());
							additionalBankRuleMap.setModifiedDate(additionalBankRuleObj.getModifiedDate());
							additionalBankRuleMap.setModifiedBy(additionalBankRuleObj.getModifiedBy());
							additionalBankRuleMap.setIsActive(additionalBankRuleObj.getIsActive());

							additionalBankRuleMapService.save(additionalBankRuleMap);
						}
					}
					additionalBankRuleDataList1.clear();
					additionalBankRuleDataNewList1.clear();
					RequestContext.getCurrentInstance().execute("complete.show();");
				}
			}catch(NullPointerException ne){
				  log.info("Method Name::saveAdditionalBankRule1"+ne.getMessage());
				  setErrorMessage("Method Name::saveAdditionalBankRule1"); 
				    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				    return; 
			} catch (Exception exception) {
				setErrorMessage(exception.getMessage());
				log.info("Method Name::saveAdditionalBankRule1 "+exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
			}
		}
		clearAdditionalMap();
	}

	// click on ok save
	public void clickOnOKSave() {
		// setNoOfSaveCount(null);
		// setAdditionalBankRuleAddDataRendered(false);
		setAdditionalBankRuleMapRendered(false);
		additionalBankRuleDataList1.clear();
		additionalBankRuleDataNewList1.clear();
		// setBooRenderSaveExit(false);
		setAdditionalBankRuleMapRendered(false);
		setSaveAdditionalBankRule1(false);
		setDisableEditBankRuleMap(false);
		setEditClikedBankMapRule(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankrulemap.xhtml");
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::clickOnOKSave" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void clearAdditionalMap() {
		setCountryId(null);
		//setFlexField(null);
		setdBflexField(null);
		setFieldName(null);
		setOrderNo(null);
		setAdditionabankrulemapPK(null);
		if (getLstFlexFiledDB().size() != 0) {
			lstFlexFiledDB.clear();
		}
		setBooDBFlexField(false);
		setBooNewFlexField(true);
		setEditButton(false);
		setCreatedBy(null);
		setCreatedDate(null);
		setApproveBy(null);
		setApproveDate(null);
		setEditClikedBankMapRule(false);
	}

	/* Approval Started */
	public void navigateToAdditionalBankRuleMapApprovalPage() {
		try {
			fetchDataForApproval();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "AdditionBankRuleMapApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionBankRuleMapApproval.xhtml");
		}catch(NullPointerException ne){
			  log.info("Method Name::fetchDataForApproval"+ne.getMessage());
			  setErrorMessage("Method Name::fetchDataForApproval"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (IOException exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::fetchDataForApproval:" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}
	private List<AdditionBankRuleMapApprovalDataTable>	addtionBankRuleMapDataTableList=new ArrayList<AdditionBankRuleMapApprovalDataTable>();

	public List<AdditionBankRuleMapApprovalDataTable> getAddtionBankRuleMapDataTableList() {
		return addtionBankRuleMapDataTableList;
	}

	public void setAddtionBankRuleMapDataTableList(
			List<AdditionBankRuleMapApprovalDataTable> addtionBankRuleMapDataTableList) {
		this.addtionBankRuleMapDataTableList = addtionBankRuleMapDataTableList;
	}

	public void fetchDataForApproval() {
		try {
			addtionBankRuleMapDataTableList.clear();
			List<AdditionalBankRuleMap> addtionaBankRuleMapList = additionalBankRuleAddService.getDataForApprovalForBankRuleMap();
			if (addtionaBankRuleMapList.size() > 0) {
				for (AdditionalBankRuleMap addtionBankMap : addtionaBankRuleMapList) {
					AdditionBankRuleMapApprovalDataTable approvalDataTable = new AdditionBankRuleMapApprovalDataTable();
					approvalDataTable.setAddtionBankRuleMapPK(addtionBankMap.getAdditionalBankRuleId());
					approvalDataTable.setFlexField(addtionBankMap.getFlexField());
					approvalDataTable.setFieldName(addtionBankMap.getFieldName());
					approvalDataTable.setCountryId(addtionBankMap.getCountryId().getCountryId());
					approvalDataTable.setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), addtionBankMap.getCountryId().getCountryId()));
					approvalDataTable.setOrderNo(addtionBankMap.getOrderNo().toString());
					approvalDataTable.setCreatedBy(addtionBankMap.getCreatedBy());
					approvalDataTable.setCreatedDate(addtionBankMap.getCreatedDate());
					approvalDataTable.setModifiedBy(addtionBankMap.getModifiedBy());
					approvalDataTable.setModifiedDate(addtionBankMap.getModifiedDate());
					addtionBankRuleMapDataTableList.add(approvalDataTable);
				}
			}
		}catch(NullPointerException ne){
			  log.info("Method Name::fetchDataForApproval"+ne.getMessage());
			  setErrorMessage("Method Name::fetchDataForApproval"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::fetchDataForApproval:" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	public void approveAdditionBankRuleMap(AdditionBankRuleMapApprovalDataTable bankRuleMapDataTable) {
		if ((bankRuleMapDataTable.getModifiedBy() == null ? bankRuleMapDataTable.getCreatedBy() : bankRuleMapDataTable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())) {
			RequestContext.getCurrentInstance().execute("notApproved.show();");
		} else {
			try {
			setRenderCountryIdBankMapForApprove(true);
			setRenderCountryIdBankMap(false);
			setAdditionalBankRuleMapRendered(false);
			setSaveAdditionalBankRule1(false);
			setCountryNameForBankMap(bankRuleMapDataTable.getCountryName());
			setRenderFlexFieldBankMapForApprove(true);
			setRenderFlexFieldBankMap(false);
			setFlexFieldNameForBankMap(bankRuleMapDataTable.getFieldName());
			setFlexFieldForBankMap(bankRuleMapDataTable.getFlexField());
			setRenderFlexNameBankMapForApprove(true);
			setRenderFlexNameBankMap(false);
			setRenderOrderNoBankMap(false);
			setRenderOrderNoBankMapForApprove(true);
			setOrderNoForBankMap(bankRuleMapDataTable.getOrderNo());
			setRenderApproveCancelButtonPanelForBankMap(true);
			setRenderAddButtonPanelForBankMap(false);
			setAdditionabankrulemapPK(bankRuleMapDataTable.getAddtionBankRuleMapPK());
			
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankrulemap.xhtml");
			
			}catch(NullPointerException ne){
				  log.info("Method Name::approveAdditionBankRuleMap"+ne.getMessage());
				  setErrorMessage("Method Name::approveAdditionBankRuleMap"); 
				    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				    return; 
			} catch (IOException exception) {
				setErrorMessage(exception.getMessage());
				log.info("Method Name::approveAdditionBankRuleMap:" + exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
			}
		}
	}
	//click on approve button
	public void approveRecordsBankMap() {

		try {
			String approveMsg = additionalBankRuleAddService.approveRecordForBankRuleMap(getAdditionabankrulemapPK(), sessionStateManage.getUserName());
			if (approveMsg.equalsIgnoreCase("Sucess")) {
				RequestContext.getCurrentInstance().execute("approved.show();");
				return;
			} else {
				RequestContext.getCurrentInstance().execute("notApproved.show();");
				return;
			}

		}catch(NullPointerException ne){
			  log.info("Method Name::approveRecordsBankMap"+ne.getMessage());
			  setErrorMessage("Method Name::approveRecordsBankMap"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		}catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::approveRecordsBankMap:" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}
	public void clickOnOKGotoBankMapPanel(){
		fetchDataForApproval();
		setRenderApproveCancelButtonPanelForBankMap(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionBankRuleMapApproval.xhtml");
		}catch(NullPointerException ne){
			  log.info("Method Name::clickOnOKGotoBankMapPanel"+ne.getMessage());
			  setErrorMessage("Method Name::clickOnOKGotoBankMapPanel"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (IOException exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::clickOnOKGotoBankMapPanel:" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;   
		}    
	}
	public void cancelFromApprovalBankMap() {
		fetchDataForApproval();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionBankRuleMapApproval.xhtml");
		}catch(NullPointerException ne){
			  log.info("Method Name::cancelFromApprovalBankMap"+ne.getMessage());
			  setErrorMessage("Method Name::cancelFromApprovalBankMap"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (IOException exception) {
			setErrorMessage(exception.getMessage());
			log.info("cancelFromApprovalBankMap:" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;   
		}
	}

	/* Approval Ended */

	public void exit() {
		additionalBankRuleDataList1.clear();
		additionalBankRuleDataNewList1.clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("exception.getMessage():::::::::::::::::::::::::::::" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	private List<AdditionalBankRuleFlexFieldView> lstflexfieldsfromview = new ArrayList<AdditionalBankRuleFlexFieldView>();

	public List<AdditionalBankRuleFlexFieldView> getLstflexfieldsfromview() {
		return lstflexfieldsfromview;
	}
	public void setLstflexfieldsfromview(List<AdditionalBankRuleFlexFieldView> lstflexfieldsfromview) {
		this.lstflexfieldsfromview = lstflexfieldsfromview;
	}

	// added on 21/12/2015 , new view added V_EX_INDIC to fetch flex_fields
	public void fetchAllFlexFields(){
		try{
		List<AdditionalBankRuleFlexFieldView> lstflexfields = additionalBankRuleMapService.tofetchAllFlexFieldsFromView();
		if(lstflexfields.size() != 0){
			setLstflexfieldsfromview(lstflexfields);
		}

		}catch(NullPointerException ne){
			  log.info("Method Name::fetchAllFlexFields"+ne.getMessage());
			  setErrorMessage("Method Name::fetchAllFlexFields"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::fetchAllFlexFields:" + exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;   
		}
	}
	
	//clear Functinatily added 12/01/2016 for cr
		public void clearAllFields(){
			  setAdditionabankrulemapPK(null);
			  setCountryId(null);
			  setFieldName(null);
			  setdBflexField(null);
			  setOrderNo(null);
			  setCreatedBy(null);
			  setCreatedDate(null);
			  setModifiedBy(null);
			  setModifiedDate(null);
			  setApproveBy(null);
			  setApproveDate(null);
			  setIsActive(null);
			  setDynamicLabelForActivateDeactivateForBankMap(null);
			  additionalBankRuleDataList1.clear();
			  additionalBankRuleDataNewList1.clear();
			  setAdditionalBankRuleMapRendered(false);
			  setSaveAdditionalBankRule1(false);
		}

}
