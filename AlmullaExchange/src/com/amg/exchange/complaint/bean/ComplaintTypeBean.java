package com.amg.exchange.complaint.bean;

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

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.complaint.model.ComplaintTypeDesc;
import com.amg.exchange.complaint.model.ComplaintTypeMaster;
import com.amg.exchange.complaint.service.IComplaintTypeService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("complaintType")
@Scope("session")
public class ComplaintTypeBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(ComplaintTypeBean.class);

	private BigDecimal complaintTypeId;
	private BigDecimal complaintTypeDescId;
	private BigDecimal engComplaintTypeDescId;
	private BigDecimal localComplaintTypeDescId;
	private String fullEngComplaintTypeDesc;
	private String fullLocalComplaintTypeDesc;
	private String shortEngComplaintTypeDesc;
	private String shortLocalComplaintTypeDesc;
	private BigDecimal appCountryId;
	private String complaintCode;
	private String sendBulk;
	private BigDecimal osDays;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks = "";
	private BigDecimal localLanguageId;
	private BigDecimal englishLanguageId;
	private String dynamicLabelForActivateDeactivate;

	private Boolean ifEditClicked = false;

	private Boolean booEdit;
	private Boolean isdisable;
	private Boolean btnClear;
	private Boolean disableSubmitButton;
	private Boolean booSubmitPanel;

	private Boolean booComplaintTypeDetail;
	private Boolean booComplaintTypeDataTable;

	private String errorMessage;

	private ComplaintTypeDataTable complaintTypeDataTable = null;

	private List<ComplaintTypeDataTable> lstComplaintTypeNewDataTables = new CopyOnWriteArrayList<ComplaintTypeDataTable>();
	private List<ComplaintTypeDataTable> lstComplaintTypeDataTables = new CopyOnWriteArrayList<ComplaintTypeDataTable>();

	private List<ComplaintTypeMaster> lstofComplaintType = new ArrayList<ComplaintTypeMaster>();
	private List<ComplaintTypeDesc> lstofComplaintTypeDesc = new ArrayList<ComplaintTypeDesc>();

	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	IComplaintTypeService<T> complaintTypeService;

	public BigDecimal getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(BigDecimal complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getComplaintTypeDescId() {
		return complaintTypeDescId;
	}

	public void setComplaintTypeDescId(BigDecimal complaintTypeDescId) {
		this.complaintTypeDescId = complaintTypeDescId;
	}

	public BigDecimal getEngComplaintTypeDescId() {
		return engComplaintTypeDescId;
	}

	public void setEngComplaintTypeDescId(BigDecimal engComplaintTypeDescId) {
		this.engComplaintTypeDescId = engComplaintTypeDescId;
	}

	public BigDecimal getLocalComplaintTypeDescId() {
		return localComplaintTypeDescId;
	}

	public void setLocalComplaintTypeDescId(BigDecimal localComplaintTypeDescId) {
		this.localComplaintTypeDescId = localComplaintTypeDescId;
	}

	public String getFullEngComplaintTypeDesc() {
		return fullEngComplaintTypeDesc;
	}

	public void setFullEngComplaintTypeDesc(String fullEngComplaintTypeDesc) {
		this.fullEngComplaintTypeDesc = fullEngComplaintTypeDesc;
	}

	public String getFullLocalComplaintTypeDesc() {
		return fullLocalComplaintTypeDesc;
	}

	public void setFullLocalComplaintTypeDesc(String fullLocalComplaintTypeDesc) {
		this.fullLocalComplaintTypeDesc = fullLocalComplaintTypeDesc;
	}

	public String getShortEngComplaintTypeDesc() {
		return shortEngComplaintTypeDesc;
	}

	public void setShortEngComplaintTypeDesc(String shortEngComplaintTypeDesc) {
		this.shortEngComplaintTypeDesc = shortEngComplaintTypeDesc;
	}

	public String getShortLocalComplaintTypeDesc() {
		return shortLocalComplaintTypeDesc;
	}

	public void setShortLocalComplaintTypeDesc(
			String shortLocalComplaintTypeDesc) {
		this.shortLocalComplaintTypeDesc = shortLocalComplaintTypeDesc;
	}

	public BigDecimal getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
	}

	public String getComplaintCode() {
		return complaintCode;
	}

	public void setComplaintCode(String complaintCode) {
		this.complaintCode = complaintCode;
	}

	public String getSendBulk() {
		return sendBulk;
	}

	public void setSendBulk(String sendBulk) {
		this.sendBulk = sendBulk;
	}

	public BigDecimal getOsDays() {
		return osDays;
	}

	public void setOsDays(BigDecimal osDays) {
		this.osDays = osDays;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getBooEdit() {
		return booEdit;
	}

	public void setBooEdit(Boolean booEdit) {
		this.booEdit = booEdit;
	}

	public Boolean getIsdisable() {
		return isdisable;
	}

	public void setIsdisable(Boolean isdisable) {
		this.isdisable = isdisable;
	}

	public Boolean getBtnClear() {
		return btnClear;
	}

	public void setBtnClear(Boolean btnClear) {
		this.btnClear = btnClear;
	}

	public Boolean getDisableSubmitButton() {
		return disableSubmitButton;
	}

	public void setDisableSubmitButton(Boolean disableSubmitButton) {
		this.disableSubmitButton = disableSubmitButton;
	}

	public Boolean getBooComplaintTypeDetail() {
		return booComplaintTypeDetail;
	}

	public void setBooComplaintTypeDetail(Boolean booComplaintTypeDetail) {
		this.booComplaintTypeDetail = booComplaintTypeDetail;
	}

	public Boolean getBooComplaintTypeDataTable() {
		return booComplaintTypeDataTable;
	}

	public void setBooComplaintTypeDataTable(Boolean booComplaintTypeDataTable) {
		this.booComplaintTypeDataTable = booComplaintTypeDataTable;
	}

	public BigDecimal getLocalLanguageId() {
		return localLanguageId;
	}

	public void setLocalLanguageId(BigDecimal localLanguageId) {
		this.localLanguageId = localLanguageId;
	}

	public BigDecimal getEnglishLanguageId() {
		return englishLanguageId;
	}

	public void setEnglishLanguageId(BigDecimal englishLanguageId) {
		this.englishLanguageId = englishLanguageId;
	}

	public List<ComplaintTypeDataTable> getLstComplaintTypeDataTables() {
		return lstComplaintTypeDataTables;
	}

	public void setLstComplaintTypeDataTables(
			List<ComplaintTypeDataTable> lstComplaintTypeDataTables) {
		this.lstComplaintTypeDataTables = lstComplaintTypeDataTables;
	}

	public IComplaintTypeService<T> getComplaintTypeService() {
		return complaintTypeService;
	}

	public void setComplaintTypeService(
			IComplaintTypeService<T> complaintTypeService) {
		this.complaintTypeService = complaintTypeService;
	}

	public Boolean getBooSubmitPanel() {
		return booSubmitPanel;
	}

	public void setBooSubmitPanel(Boolean booSubmitPanel) {
		this.booSubmitPanel = booSubmitPanel;
	}

	public Boolean getIfEditClicked() {
		return ifEditClicked;
	}

	public void setIfEditClicked(Boolean ifEditClicked) {
		this.ifEditClicked = ifEditClicked;
	}

	public List<ComplaintTypeDataTable> getLstComplaintTypeNewDataTables() {
		return lstComplaintTypeNewDataTables;
	}

	public void setLstComplaintTypeNewDataTables(
			List<ComplaintTypeDataTable> lstComplaintTypeNewDataTables) {
		this.lstComplaintTypeNewDataTables = lstComplaintTypeNewDataTables;
	}

	public ComplaintTypeDataTable getComplaintTypeDataTable() {
		return complaintTypeDataTable;
	}

	public void setComplaintTypeDataTable(
			ComplaintTypeDataTable complaintTypeDataTable) {
		this.complaintTypeDataTable = complaintTypeDataTable;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void complaintTypeCreationNavigation() {

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "complainttypemaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/complainttypemaster.xhtml");
			setBooComplaintTypeDataTable(false);
			setBooComplaintTypeDetail(true);
			lstComplaintTypeDataTables.clear();
			lstComplaintTypeNewDataTables.clear();
			clearAll();

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::complaintTypeCreationNavigation");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void complaintTypeApprovalNavigation() {

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "complainttypeapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/complainttypeapproval.xhtml");
			setBooComplaintTypeDataTable(true);
			setBooComplaintTypeDetail(false);
			approveViewComplaintTypeMethod();

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::complaintTypeApprovalNavigation");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void duplicateChekingComplaintTypeCode() {
		if (lstComplaintTypeDataTables.size() > 0) {
			for (ComplaintTypeDataTable dataTable : lstComplaintTypeDataTables) {
				if (dataTable.getComplaintCode().equalsIgnoreCase(
						getComplaintCode())) {
					clearAll();
					RequestContext.getCurrentInstance().execute(
							"datatable.show();");
					return;
				}
			}
		}

		if (getComplaintCode() != null) {
			addRecordsToDataTable();
		}
	}

	public void addRecordsToDataTable() {
		try {
			setBooSubmitPanel(false);
			ComplaintTypeDataTable complaintTypeDataTable = new ComplaintTypeDataTable();
			complaintTypeDataTable.setAppCountryId(sessionStateManage
					.getCountryId());
			complaintTypeDataTable.setComplaintCode(getComplaintCode());

			if (getSendBulk() != null) {
				if (getSendBulk().equals(Constants.Yes)) {
					complaintTypeDataTable.setSendBulk(Constants.Yes);
					complaintTypeDataTable.setSendBulkName(Constants.YES);
				} else if (getSendBulk().equals(Constants.No)) {
					complaintTypeDataTable.setSendBulk(Constants.No);
					complaintTypeDataTable.setSendBulkName(Constants.NO);
				}
			}

			complaintTypeDataTable.setOsDays(getOsDays());

			complaintTypeDataTable
					.setEngComplaintTypeDescId(getEngComplaintTypeDescId());
			complaintTypeDataTable
					.setLocalComplaintTypeDescId(getLocalComplaintTypeDescId());

			complaintTypeDataTable.setLocalLanguageId(getLocalLanguageId());
			complaintTypeDataTable.setEnglishLanguageId(getEnglishLanguageId());
			complaintTypeDataTable
					.setFullEngComplaintTypeDesc(getFullEngComplaintTypeDesc());
			complaintTypeDataTable
					.setFullLocalComplaintTypeDesc(getFullLocalComplaintTypeDesc());
			complaintTypeDataTable
					.setShortEngComplaintTypeDesc(getShortEngComplaintTypeDesc());
			complaintTypeDataTable
					.setShortLocalComplaintTypeDesc(getShortLocalComplaintTypeDesc());

			complaintTypeDataTable.setCreatedBy(getCreatedBy());
			complaintTypeDataTable.setCreatedDate(getCreatedDate());
			complaintTypeDataTable.setComplaintTypeId(getComplaintTypeId());

			if (getComplaintTypeId() != null) {

				/*
				 * if(lstComplaintTypeNewDataTables.size()==0 &&
				 * (lstComplaintTypeDataTables.size()!= 0 ||
				 * getComplaintTypeDataTable() != null)){
				 * complaintTypeDataTable.
				 * setDynamicLabelForActivateDeactivate(Constants
				 * .PENDING_FOR_APPROVE); setBooEdit(true);
				 * complaintTypeDataTable.setIsActive(Constants.U);
				 * complaintTypeDataTable
				 * .setModifiedBy(sessionStateManage.getUserName());
				 * complaintTypeDataTable.setModifiedDate(new Date());
				 * complaintTypeDataTable.setApprovedBy(null);
				 * complaintTypeDataTable.setApprovedDate(null);
				 * complaintTypeDataTable.setRemarks(null);
				 */

				if (getIfEditClicked().equals(true)) {
					if ((complaintTypeDataTable.getComplaintCode()
							.equalsIgnoreCase(
									complaintTypeDataTable.getComplaintCode())
							&& complaintTypeDataTable
									.getFullEngComplaintTypeDesc()
									.equalsIgnoreCase(
											getFullEngComplaintTypeDesc())
							&& complaintTypeDataTable
									.getShortEngComplaintTypeDesc()
									.equalsIgnoreCase(
											getShortEngComplaintTypeDesc())
							&& complaintTypeDataTable
									.getFullLocalComplaintTypeDesc()
									.equalsIgnoreCase(
											getFullLocalComplaintTypeDesc()) && complaintTypeDataTable
							.getShortLocalComplaintTypeDesc().equalsIgnoreCase(
									getShortLocalComplaintTypeDesc()))) {

						complaintTypeDataTable
								.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
						complaintTypeDataTable.setIsActive(getIsActive());
						complaintTypeDataTable.setModifiedBy(getModifiedBy());
						complaintTypeDataTable
								.setModifiedDate(getModifiedDate());
						complaintTypeDataTable.setApprovedBy(getApprovedBy());
						complaintTypeDataTable
								.setApprovedDate(getApprovedDate());
						complaintTypeDataTable.setRemarks(getRemarks());

					} else {
						complaintTypeDataTable
								.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						setBooEdit(true);
						complaintTypeDataTable.setIsActive(Constants.U);
						complaintTypeDataTable.setModifiedBy(sessionStateManage
								.getUserName());
						complaintTypeDataTable.setModifiedDate(new Date());
						complaintTypeDataTable.setApprovedBy(null);
						complaintTypeDataTable.setApprovedDate(null);
						complaintTypeDataTable.setRemarks(null);

					}
				}
				/*
				 * }else{
				 * complaintTypeDataTable.setDynamicLabelForActivateDeactivate
				 * (getDynamicLabelForActivateDeactivate());
				 * complaintTypeDataTable.setIsActive(getIsActive());
				 * complaintTypeDataTable.setModifiedBy(getModifiedBy());
				 * complaintTypeDataTable.setModifiedDate(getModifiedDate());
				 * complaintTypeDataTable.setApprovedBy(getApprovedBy());
				 * complaintTypeDataTable.setApprovedDate(getApprovedDate());
				 * complaintTypeDataTable.setRemarks(getRemarks()); }
				 */
			} else {
				complaintTypeDataTable
						.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				complaintTypeDataTable.setIsActive(Constants.U);
				complaintTypeDataTable.setCreatedBy(sessionStateManage
						.getUserName());
				complaintTypeDataTable.setCreatedDate(new Date());

			}
			lstComplaintTypeDataTables.add(complaintTypeDataTable);

			if (getComplaintTypeId() == null) {
				lstComplaintTypeNewDataTables.add(complaintTypeDataTable);
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::addRecordsToDataTable");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		setBooComplaintTypeDataTable(true);
		setBooComplaintTypeDetail(true);

		clearAll();

	}

	public void saveAllComplaintTypeMethod() {

		try {
			for (ComplaintTypeDataTable complaintTypeDataTable : lstComplaintTypeDataTables) {

				ComplaintTypeMaster complaintTypeMaster = new ComplaintTypeMaster();

				complaintTypeMaster.setComplaintTypeId(complaintTypeDataTable
						.getComplaintTypeId());

				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(complaintTypeDataTable
						.getAppCountryId());
				complaintTypeMaster.setAppCountryId(countryMaster);

				complaintTypeMaster.setComplaintTypeCode(complaintTypeDataTable
						.getComplaintCode());
				complaintTypeMaster.setSendBulk(complaintTypeDataTable
						.getSendBulk());
				complaintTypeMaster.setOsDays(complaintTypeDataTable
						.getOsDays());
				complaintTypeMaster.setCreatedBy(complaintTypeDataTable
						.getCreatedBy());
				complaintTypeMaster.setCreatedDate(complaintTypeDataTable
						.getCreatedDate());
				complaintTypeMaster.setModifiedBy(complaintTypeDataTable
						.getModifiedBy());
				complaintTypeMaster.setModifiedDate(complaintTypeDataTable
						.getModifiedDate());
				complaintTypeMaster.setApprovedBy(complaintTypeDataTable
						.getApprovedBy());
				complaintTypeMaster.setApprovedDate(complaintTypeDataTable
						.getApprovedDate());
				complaintTypeMaster.setIsActive(complaintTypeDataTable
						.getIsActive());
				complaintTypeMaster.setRemarks(complaintTypeDataTable
						.getRemarks());

				/*
				 * try { getComplaintTypeService().saveAndUpdateComplaintType(
				 * complaintTypeMaster);
				 * 
				 * } catch (Exception e) { }
				 */
				ComplaintTypeDesc complaintTypeEnglishDesc = new ComplaintTypeDesc();

				LanguageType englishLanguageType = new LanguageType();
				englishLanguageType.setLanguageId(new BigDecimal(
						Constants.ENGLISH_LANGUAGE_ID));
				complaintTypeEnglishDesc.setLanguageId(englishLanguageType);
				complaintTypeEnglishDesc.setFullDesc(complaintTypeDataTable
						.getFullEngComplaintTypeDesc());
				complaintTypeEnglishDesc.setShortDesc(complaintTypeDataTable
						.getShortEngComplaintTypeDesc());
				complaintTypeEnglishDesc
						.setComplaintTypeDescId(complaintTypeDataTable
								.getEngComplaintTypeDescId());
				complaintTypeEnglishDesc.setComplaintType(complaintTypeMaster);

				/*
				 * try {
				 * 
				 * getComplaintTypeService().saveAndUpdateComplaintTypeDesc(
				 * complaintTypeEnglishDesc); } catch (Exception e) { }
				 */
				ComplaintTypeDesc complaintTypeArbicDesc = new ComplaintTypeDesc();

				LanguageType arabicLanguageType = new LanguageType();
				arabicLanguageType.setLanguageId(new BigDecimal(
						Constants.ARABIC_LANGUAGE_ID));
				complaintTypeArbicDesc.setLanguageId(arabicLanguageType);

				complaintTypeArbicDesc.setFullDesc(complaintTypeDataTable
						.getFullLocalComplaintTypeDesc());
				complaintTypeArbicDesc.setShortDesc(complaintTypeDataTable
						.getShortLocalComplaintTypeDesc());
				complaintTypeArbicDesc
						.setComplaintTypeDescId(complaintTypeDataTable
								.getLocalComplaintTypeDescId());
				complaintTypeArbicDesc.setComplaintType(complaintTypeMaster);
				
				
				insertComplaintTypeAndDesc(complaintTypeMaster,
						complaintTypeEnglishDesc, complaintTypeArbicDesc);

				/*
				 * getComplaintTypeService().saveAndUpdateComplaintType(
				 * complaintTypeMaster);
				 * getComplaintTypeService().saveAndUpdateComplaintTypeDesc
				 * (complaintTypeEnglishDesc);
				 * getComplaintTypeService().saveAndUpdateComplaintTypeDesc
				 * (complaintTypeArbicDesc);
				 */

			}
			RequestContext.getCurrentInstance().execute("complete.show();");

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveAllComplaintTypeMethod");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	private void insertComplaintTypeAndDesc(
			ComplaintTypeMaster complaintTypeMaster,
			ComplaintTypeDesc complaintTypeEnglishDesc,
			ComplaintTypeDesc complaintTypeArabicDesc) {

		getComplaintTypeService().saveAndUpdateComplaintType(
				complaintTypeMaster);
		getComplaintTypeService().saveAndUpdateComplaintTypeDesc(
				complaintTypeEnglishDesc);
		getComplaintTypeService().saveAndUpdateComplaintTypeDesc(
				complaintTypeArabicDesc);

	}

	public void viewComplaintTypeMethod() {

		Boolean childRecordCheck = false;
		lstComplaintTypeDataTables.clear();
		clearAll();
		try {
			lstofComplaintType = getComplaintTypeService()
					.displayAllComplaintTypeToView(
							sessionStateManage.getCountryId());

			if (lstofComplaintType != null) {
				setBooComplaintTypeDataTable(true);
				setBooComplaintTypeDetail(true);

				for (ComplaintTypeMaster complaintTypeMaster : lstofComplaintType) {
					ComplaintTypeDataTable complaintTypeDataTable = new ComplaintTypeDataTable();

					complaintTypeDataTable
							.setComplaintTypeId(complaintTypeMaster
									.getComplaintTypeId());
					complaintTypeDataTable.setComplaintCode(complaintTypeMaster
							.getComplaintTypeCode());

					if (complaintTypeMaster.getSendBulk() != null) {
						if (complaintTypeMaster.getSendBulk().equals(
								Constants.Yes)) {
							complaintTypeDataTable.setSendBulk(Constants.Yes);
							complaintTypeDataTable
									.setSendBulkName(Constants.YES);
						} else if (complaintTypeMaster.getSendBulk().equals(
								Constants.No)) {
							complaintTypeDataTable.setSendBulk(Constants.No);
							complaintTypeDataTable
									.setSendBulkName(Constants.NO);
						}
					}

					complaintTypeDataTable.setOsDays(complaintTypeMaster
							.getOsDays());
					complaintTypeDataTable.setAppCountryId(complaintTypeMaster
							.getAppCountryId().getCountryId());

					complaintTypeDataTable.setModifiedBy(complaintTypeMaster
							.getModifiedBy());
					complaintTypeDataTable.setModifiedDate(complaintTypeMaster
							.getModifiedDate());
					complaintTypeDataTable.setCreatedBy(complaintTypeMaster
							.getCreatedBy());
					complaintTypeDataTable.setCreatedDate(complaintTypeMaster
							.getCreatedDate());
					complaintTypeDataTable.setApprovedBy(complaintTypeMaster
							.getApprovedBy());
					complaintTypeDataTable.setApprovedDate(complaintTypeMaster
							.getApprovedDate());
					complaintTypeDataTable.setRemarks(complaintTypeMaster
							.getRemarks());
					complaintTypeDataTable.setIsActive(complaintTypeMaster
							.getIsActive());

					if (complaintTypeDataTable.getIsActive() != null) {
						if (complaintTypeDataTable.getIsActive()
								.equalsIgnoreCase(Constants.Yes)) {
							complaintTypeDataTable
									.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
						} else if (complaintTypeDataTable.getIsActive()
								.equalsIgnoreCase(Constants.D)) {
							complaintTypeDataTable
									.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
						} else if (complaintTypeDataTable.getIsActive()
								.equalsIgnoreCase(Constants.U)
								&& complaintTypeDataTable.getModifiedBy() == null
								&& complaintTypeDataTable.getModifiedDate() == null
								&& complaintTypeDataTable.getApprovedBy() == null
								&& complaintTypeDataTable.getApprovedDate() == null
								&& complaintTypeDataTable.getRemarks() == null) {
							complaintTypeDataTable
									.setDynamicLabelForActivateDeactivate(Constants.DELETE);
						} else {
							setBooEdit(true);
							complaintTypeDataTable
									.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						}
					}
					lstofComplaintTypeDesc = getComplaintTypeService()
							.displayAllComplaintTypeDesc(
									complaintTypeMaster.getComplaintTypeId());
					if (lstofComplaintTypeDesc != null) {
						for (ComplaintTypeDesc complaintTypeDesc : lstofComplaintTypeDesc) {
							childRecordCheck = true;
							if (complaintTypeDesc
									.getLanguageId()
									.getLanguageId()
									.equals(new BigDecimal(
											Constants.ENGLISH_LANGUAGE_ID))) {
								complaintTypeDataTable
										.setEnglishLanguageId(complaintTypeDesc
												.getLanguageId()
												.getLanguageId());
								complaintTypeDataTable
										.setFullEngComplaintTypeDesc(complaintTypeDesc
												.getFullDesc());
								complaintTypeDataTable
										.setShortEngComplaintTypeDesc(complaintTypeDesc
												.getShortDesc());
								complaintTypeDataTable
										.setEngComplaintTypeDescId(complaintTypeDesc
												.getComplaintTypeDescId());

							} else if (complaintTypeDesc
									.getLanguageId()
									.getLanguageId()
									.equals(new BigDecimal(
											Constants.ARABIC_LANGUAGE_ID))) {
								complaintTypeDataTable
										.setLocalLanguageId(complaintTypeDesc
												.getLanguageId()
												.getLanguageId());
								complaintTypeDataTable
										.setFullLocalComplaintTypeDesc(complaintTypeDesc
												.getFullDesc());
								complaintTypeDataTable
										.setShortLocalComplaintTypeDesc(complaintTypeDesc
												.getShortDesc());
								complaintTypeDataTable
										.setLocalComplaintTypeDescId(complaintTypeDesc
												.getComplaintTypeDescId());

							}

						}
					} else {
						childRecordCheck = false;
					}

					if (childRecordCheck == true) {
						lstComplaintTypeDataTables.add(complaintTypeDataTable);
					}
				}

				lstComplaintTypeDataTables
						.addAll(lstComplaintTypeNewDataTables);
				clearAll();

			} else {
				RequestContext.getCurrentInstance()
						.execute("noRecords.show();");
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::viewComplaintTypeMethod");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		/*
		 * try {
		 * FacesContext.getCurrentInstance().getExternalContext().redirect(
		 * "../complaint/complainttypemaster.xhtml"); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */

	}

	public void editComplaintTypeMethod(
			ComplaintTypeDataTable complaintTypeDataTable) {

		try {
			setComplaintTypeId(complaintTypeDataTable.getComplaintTypeId());
			setAppCountryId(complaintTypeDataTable.getAppCountryId());
			setComplaintCode(complaintTypeDataTable.getComplaintCode());

			if (complaintTypeDataTable.getSendBulk() != null) {
				if (complaintTypeDataTable.getSendBulk().equals(Constants.Yes)) {
					setSendBulk(Constants.Yes);
				} else if (complaintTypeDataTable.getSendBulk().equals(
						Constants.No)) {
					setSendBulk(Constants.No);
				}
			}

			setOsDays(complaintTypeDataTable.getOsDays());
			setCreatedBy(complaintTypeDataTable.getCreatedBy());
			setCreatedDate(complaintTypeDataTable.getCreatedDate());
			setModifiedBy(complaintTypeDataTable.getModifiedBy());
			setModifiedDate(complaintTypeDataTable.getModifiedDate());
			setApprovedBy(complaintTypeDataTable.getApprovedBy());
			setApprovedDate(complaintTypeDataTable.getApprovedDate());
			setIsActive(complaintTypeDataTable.getIsActive());
			setRemarks(complaintTypeDataTable.getRemarks());

			setEngComplaintTypeDescId(complaintTypeDataTable
					.getEngComplaintTypeDescId());
			setEnglishLanguageId(complaintTypeDataTable.getEnglishLanguageId());
			setFullEngComplaintTypeDesc(complaintTypeDataTable
					.getFullEngComplaintTypeDesc());
			setShortEngComplaintTypeDesc(complaintTypeDataTable
					.getShortEngComplaintTypeDesc());

			setDynamicLabelForActivateDeactivate(complaintTypeDataTable
					.getDynamicLabelForActivateDeactivate());

			setLocalComplaintTypeDescId(complaintTypeDataTable
					.getLocalComplaintTypeDescId());
			setLocalLanguageId(complaintTypeDataTable.getLocalLanguageId());
			setFullLocalComplaintTypeDesc(complaintTypeDataTable
					.getFullLocalComplaintTypeDesc());
			setShortLocalComplaintTypeDesc(complaintTypeDataTable
					.getShortLocalComplaintTypeDesc());

			setIfEditClicked(true);
			lstComplaintTypeDataTables.remove(complaintTypeDataTable);
			setBooEdit(true);
			setBtnClear(true);
			setIsdisable(true);
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::editComplaintTypeMethod");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void approveViewComplaintTypeMethod() {
		lstComplaintTypeDataTables.clear();

		try {
			lstofComplaintType = getComplaintTypeService()
					.displayComplaintTypeForApprove(
							sessionStateManage.getCountryId(), Constants.U);

			if (lstofComplaintType != null) {

				for (ComplaintTypeMaster complaintTypeMaster : lstofComplaintType) {

					ComplaintTypeDataTable complaintTypeDataTable = new ComplaintTypeDataTable();

					complaintTypeDataTable
							.setComplaintTypeId(complaintTypeMaster
									.getComplaintTypeId());
					complaintTypeDataTable.setComplaintCode(complaintTypeMaster
							.getComplaintTypeCode());

					if (complaintTypeMaster.getSendBulk() != null) {
						if (complaintTypeMaster.getSendBulk().equals(
								Constants.Yes)) {
							complaintTypeDataTable.setSendBulk(Constants.Yes);
							complaintTypeDataTable
									.setSendBulkName(Constants.YES);
						} else if (complaintTypeMaster.getSendBulk().equals(
								Constants.No)) {
							complaintTypeDataTable.setSendBulk(Constants.No);
							complaintTypeDataTable
									.setSendBulkName(Constants.NO);
						}
					}
					complaintTypeDataTable.setOsDays(complaintTypeMaster
							.getOsDays());
					complaintTypeDataTable.setAppCountryId(complaintTypeMaster
							.getAppCountryId().getCountryId());

					complaintTypeDataTable.setModifiedBy(complaintTypeMaster
							.getModifiedBy());
					complaintTypeDataTable.setModifiedDate(complaintTypeMaster
							.getModifiedDate());
					complaintTypeDataTable.setCreatedBy(complaintTypeMaster
							.getCreatedBy());
					complaintTypeDataTable.setCreatedDate(complaintTypeMaster
							.getCreatedDate());
					complaintTypeDataTable.setApprovedBy(complaintTypeMaster
							.getApprovedBy());
					complaintTypeDataTable.setApprovedDate(complaintTypeMaster
							.getApprovedDate());
					complaintTypeDataTable.setIsActive(complaintTypeMaster
							.getIsActive());

					complaintTypeDataTable.setRemarks(complaintTypeMaster
							.getRemarks());

					lstofComplaintTypeDesc = getComplaintTypeService()
							.displayAllComplaintTypeDesc(
									complaintTypeMaster.getComplaintTypeId());
					if (lstofComplaintTypeDesc != null) {
						for (ComplaintTypeDesc complaintTypeDesc : lstofComplaintTypeDesc) {

							if (complaintTypeDesc
									.getLanguageId()
									.getLanguageId()
									.equals(new BigDecimal(
											Constants.ENGLISH_LANGUAGE_ID))) {
								complaintTypeDataTable
										.setEnglishLanguageId(complaintTypeDesc
												.getLanguageId()
												.getLanguageId());
								complaintTypeDataTable
										.setFullEngComplaintTypeDesc(complaintTypeDesc
												.getFullDesc());
								complaintTypeDataTable
										.setShortEngComplaintTypeDesc(complaintTypeDesc
												.getShortDesc());
								complaintTypeDataTable
										.setEngComplaintTypeDescId(complaintTypeDesc
												.getComplaintTypeDescId());

							} else if (complaintTypeDesc
									.getLanguageId()
									.getLanguageId()
									.equals(new BigDecimal(
											Constants.ARABIC_LANGUAGE_ID))) {
								complaintTypeDataTable
										.setLocalLanguageId(complaintTypeDesc
												.getLanguageId()
												.getLanguageId());
								complaintTypeDataTable
										.setFullLocalComplaintTypeDesc(complaintTypeDesc
												.getFullDesc());
								complaintTypeDataTable
										.setShortLocalComplaintTypeDesc(complaintTypeDesc
												.getShortDesc());
								complaintTypeDataTable
										.setLocalComplaintTypeDescId(complaintTypeDesc
												.getComplaintTypeDescId());

							}

						}
					}

					lstComplaintTypeDataTables.add(complaintTypeDataTable);
					setBooComplaintTypeDataTable(true);
					setBooComplaintTypeDetail(false);

					clearAll();

				}
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::approveViewComplaintTypeMethod");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void editComplaintTypeForApproveMethod(
			ComplaintTypeDataTable complaintTypeDataTable) {
		try {

			if ((complaintTypeDataTable.getModifiedBy() == null ? complaintTypeDataTable
					.getCreatedBy() : complaintTypeDataTable.getModifiedBy())
					.equalsIgnoreCase(sessionStateManage.getUserName())) {
				RequestContext.getCurrentInstance().execute(
						"notValidUser.show();");
			} else {

				setComplaintTypeId(complaintTypeDataTable.getComplaintTypeId());
				setAppCountryId(complaintTypeDataTable.getAppCountryId());
				setComplaintCode(complaintTypeDataTable.getComplaintCode());

				if (complaintTypeDataTable.getSendBulk() != null) {
					if (complaintTypeDataTable.getSendBulk().equals(
							Constants.Yes)) {
						setSendBulk(Constants.Yes);
					} else if (complaintTypeDataTable.getSendBulk().equals(
							Constants.No)) {
						setSendBulk(Constants.No);
					}
				}

				setOsDays(complaintTypeDataTable.getOsDays());
				setCreatedBy(complaintTypeDataTable.getCreatedBy());
				setCreatedDate(complaintTypeDataTable.getCreatedDate());
				setModifiedBy(complaintTypeDataTable.getModifiedBy());
				setModifiedDate(complaintTypeDataTable.getModifiedDate());
				setApprovedBy(complaintTypeDataTable.getApprovedBy());
				setApprovedDate(complaintTypeDataTable.getApprovedDate());
				setIsActive(complaintTypeDataTable.getIsActive());
				setRemarks(complaintTypeDataTable.getRemarks());

				setEngComplaintTypeDescId(complaintTypeDataTable
						.getEngComplaintTypeDescId());
				setEnglishLanguageId(complaintTypeDataTable
						.getEnglishLanguageId());
				setFullEngComplaintTypeDesc(complaintTypeDataTable
						.getFullEngComplaintTypeDesc());
				setShortEngComplaintTypeDesc(complaintTypeDataTable
						.getShortEngComplaintTypeDesc());

				setLocalComplaintTypeDescId(complaintTypeDataTable
						.getLocalComplaintTypeDescId());
				setLocalLanguageId(complaintTypeDataTable.getLocalLanguageId());
				setFullLocalComplaintTypeDesc(complaintTypeDataTable
						.getFullLocalComplaintTypeDesc());
				setShortLocalComplaintTypeDesc(complaintTypeDataTable
						.getShortLocalComplaintTypeDesc());

				lstComplaintTypeDataTables.remove(complaintTypeDataTable);
				setBooComplaintTypeDataTable(false);
				setBooComplaintTypeDetail(true);
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::editComplaintTypeForApproveMethod");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void approveComplaintTypeMethod() {
		try {
			getComplaintTypeService().approveRecord(getComplaintTypeId(),
					sessionStateManage.getUserName(), Constants.Yes);
			RequestContext.getCurrentInstance().execute("complete.show();");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::approveComplaintTypeMethod");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void clearAll() {

		setAppCountryId(null);
		setComplaintCode(null);
		setComplaintTypeDescId(null);
		setComplaintTypeId(null);
		setOsDays(null);
		setSendBulk(null);
		setFullEngComplaintTypeDesc(null);
		setFullLocalComplaintTypeDesc(null);
		setShortEngComplaintTypeDesc(null);
		setShortLocalComplaintTypeDesc(null);
		setComplaintTypeDescId(null);
		setLocalComplaintTypeDescId(null);
		setEngComplaintTypeDescId(null);
		setRemarks(null);

		setBooEdit(false);
		setBtnClear(false);
		setIsdisable(false);
		setDisableSubmitButton(false);

		setIfEditClicked(false);
	}

	public void checkStatusType(ComplaintTypeDataTable complaintTypeDataTable)
			throws IOException {

		try {

			if (complaintTypeDataTable.getDynamicLabelForActivateDeactivate()
					.equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
				// setBooEdit(t);
				RequestContext.getCurrentInstance().execute("pending.show();");
				return;
			} else if (complaintTypeDataTable
					.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(
							Constants.DEACTIVATE)) {

				complaintTypeDataTable.setRemarksCheck(true);
				setRemarks(null);
				setApprovedBy(complaintTypeDataTable.getApprovedBy());
				setApprovedDate(complaintTypeDataTable.getApprovedDate());
				RequestContext.getCurrentInstance().execute("remarks.show();");
				return;
			} else if (complaintTypeDataTable
					.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(
							Constants.DELETE)) {
				complaintTypeDataTable.setBooCheckDelete(true);
				RequestContext.getCurrentInstance().execute(
						"permanentDelete.show();");
				return;
			} else if (complaintTypeDataTable
					.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(
							Constants.ACTIVATE)) {
				complaintTypeDataTable.setActivateRecordCheck(true);
				RequestContext.getCurrentInstance().execute(
						"activateRecord.show();");
				return;
			} else if (complaintTypeDataTable
					.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(
							Constants.REMOVE)) {
				lstComplaintTypeDataTables.remove(complaintTypeDataTable);
				lstComplaintTypeNewDataTables.clear();

			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::checkStatusType");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void confirmPermanentDelete() {
		try {
			if (lstComplaintTypeDataTables.size() > 0) {
				for (ComplaintTypeDataTable complaintTypeDataTable : lstComplaintTypeDataTables) {
					if (complaintTypeDataTable.getBooCheckDelete() != null) {
						if (complaintTypeDataTable.getBooCheckDelete().equals(
								true)) {

							delete(complaintTypeDataTable);

						}
					}
				}
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::checkStatusType");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void conformToDeActivteCompliantType(
			ComplaintTypeDataTable complaintTypeDataTable) {
		try {
			getComplaintTypeService().activateComplaintType(
					complaintTypeDataTable.getComplaintTypeId(),
					sessionStateManage.getUserName());
			RequestContext.getCurrentInstance().execute("update.show();");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::conformToDeActivteCompliantType");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void activateRecord() {

		try {
			if (lstComplaintTypeDataTables.size() > 0) {
				for (ComplaintTypeDataTable complaintTypeDataTable : lstComplaintTypeDataTables) {
					if (complaintTypeDataTable.getActivateRecordCheck() != null) {
						if (complaintTypeDataTable.getActivateRecordCheck()
								.equals(true)) {

							conformToDeActivteCompliantType(complaintTypeDataTable);

						}
					}
				}
			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::activateRecord");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void remarkSelectedRecord() throws IOException {

		try {
			for (ComplaintTypeDataTable complaintTypeDataTable : lstComplaintTypeDataTables) {
				if (complaintTypeDataTable.getRemarksCheck().equals(true)) {
					if (!getRemarks().equals("")) {
						complaintTypeDataTable.setRemarks(getRemarks());
						complaintTypeDataTable.setApprovedBy(null);
						complaintTypeDataTable.setApprovedDate(null);
						complaintTypeDataTable.setRemarksCheck(true);
						update(complaintTypeDataTable);
						lstComplaintTypeDataTables.clear();

					} else {
						RequestContext.getCurrentInstance().execute(
								"remarksEmpty.show();");
						return;

					}
				}

			}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::remarkSelectedRecord");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void delete(ComplaintTypeDataTable complaintTypeDataTable) {

		try {
			getComplaintTypeService().deleteComplaintType(
					complaintTypeDataTable.getComplaintTypeId(),
					complaintTypeDataTable.getEngComplaintTypeDescId(),
					complaintTypeDataTable.getLocalComplaintTypeDescId());
			RequestContext.getCurrentInstance().execute("update.show();");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::remarkSelectedRecord");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void update(ComplaintTypeDataTable complaintTypeDataTable)
			throws IOException {

		try {

			ComplaintTypeMaster complaintTypeMaster = new ComplaintTypeMaster();

			complaintTypeMaster.setComplaintTypeId(complaintTypeDataTable
					.getComplaintTypeId());
			complaintTypeMaster.setComplaintTypeCode(complaintTypeDataTable
					.getComplaintCode());
			complaintTypeMaster.setSendBulk(complaintTypeDataTable
					.getSendBulk());
			complaintTypeMaster.setOsDays(complaintTypeDataTable.getOsDays());

			CountryMaster countryMaster = new CountryMaster();
			countryMaster
					.setCountryId(complaintTypeDataTable.getAppCountryId());
			complaintTypeMaster.setAppCountryId(countryMaster);

			complaintTypeMaster.setCreatedBy(complaintTypeDataTable
					.getCreatedBy());
			complaintTypeMaster.setCreatedDate(complaintTypeDataTable
					.getCreatedDate());
			// complaintTypeDataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(complaintTypeMaster.getIsActive()));
			complaintTypeMaster.setRemarks(complaintTypeMaster.getRemarks());

			complaintTypeMaster.setModifiedBy(sessionStateManage.getUserName());
			complaintTypeMaster.setModifiedDate(new Date());
			complaintTypeMaster.setApprovedBy(null);
			complaintTypeMaster.setApprovedDate(null);
			complaintTypeMaster.setRemarks(complaintTypeDataTable.getRemarks());
			complaintTypeMaster.setIsActive(Constants.D);
			complaintTypeMaster.setCreatedBy(complaintTypeDataTable
					.getCreatedBy());
			complaintTypeMaster.setCreatedDate(complaintTypeDataTable
					.getCreatedDate());

			getComplaintTypeService().saveAndUpdateComplaintType(
					complaintTypeMaster);

			RequestContext.getCurrentInstance().execute("update.show();");

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::remarkSelectedRecord");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void exit() {
		log.info("Enter in exit method ");

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
			lstComplaintTypeDataTables.clear();
			lstComplaintTypeNewDataTables.clear();
			setBooComplaintTypeDataTable(false);
			setBooComplaintTypeDetail(false);
			clearAll();

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::remarkSelectedRecord");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

		log.info("Exit from exit method ");
	}

	public List<String> autoComplete(String query) {
		if (query.length() > 0) {
			return getComplaintTypeService().autoCompleteList(query);
		} else {
			return null;
		}
	}

	public void autoCompletePopulateValue() {

		lstofComplaintType = getComplaintTypeService()
				.displayComplaintTypeBasedOnComplaintTypeCode(
						getComplaintCode());

		if (lstofComplaintType.size() > 0) {
			setComplaintCode(null);
			RequestContext.getCurrentInstance().execute("codeExist.show();");
		} else {

			setOsDays(null);
			setSendBulk(null);
			setFullEngComplaintTypeDesc(null);
			setFullLocalComplaintTypeDesc(null);
			setShortEngComplaintTypeDesc(null);
			setShortLocalComplaintTypeDesc(null);
			setComplaintTypeDescId(null);
			setEngComplaintTypeDescId(null);
			setLocalComplaintTypeDescId(null);
			setRemarks(null);
		}

	}

	public void disableSubmit() {
		setIsdisable(true);
	}

	// Negative Value checking
	public void negativeValueChecking(FacesContext context,
			UIComponent component, Object value) {
		BigDecimal negativeValue = (BigDecimal) value;

		if (negativeValue.intValue() <= 0) {
			FacesMessage msg = new FacesMessage(
					"Please Enter Only Positive Values, Zero Not Allowed",
					"Please Enter Only Positive Values, Zero Not Allowed");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

}
