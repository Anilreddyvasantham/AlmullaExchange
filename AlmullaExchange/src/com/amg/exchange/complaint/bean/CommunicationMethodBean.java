package com.amg.exchange.complaint.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.dto.CommunicationMethodDTO;
import com.amg.exchange.complaint.service.ICommunicationMethodService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("unused")
@Component("communicationMethod")
@Scope("session")
public class CommunicationMethodBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(CommunicationMethodBean.class);
	private SessionStateManage session = new SessionStateManage();

	private String errorMessage;
	private String communicationMethodCode;
	private String email;
	private String communicationFullDesc;
	private String communicationShortDesc;
	private String communicationFullDescLocal;
	private String communicationShortDescLocal;
	private BigDecimal communicationMethodPk;
	private BigDecimal communicationMethodDescPk;
	private BigDecimal communicationMethodLocalDescPk;
	private BigDecimal countryId;
	private String emailId;
	private BigDecimal engLanguageId;
	private BigDecimal arbLanguageId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private String remarks;
	private String dynamicLabelForActivateDeactivate;
	private String accountTypeCodeForDailog;
	private String communicationMethodCodeForDailog;
	private BigDecimal countNoOfSave;
	private int count = 0;
	private BigDecimal selectType;
	private Boolean disableSubmitButton = false;
	private Boolean disableClearButton = false;
	private Boolean ifEditClicked = false;
	private Boolean disableEditButton = false;
	private Boolean renderSaveButton = false;
	private Boolean renderDataTable = false;
	private Boolean renderSavePanel = false;
	private Boolean renderUpdatePanel = false;

	private Boolean readOnlyCommunicationCode = false;
	private Boolean readOnlyEmail = false;
	private Boolean readOnlyCommunicationFullDesc = false;
	private Boolean readOnlyCommunicationFullDescLocal = false;
	private Boolean readOnlyCommunicationShortDesc = false;
	private Boolean readOnlyCommunicationShortDescLocal = false;

	private CommunicationMethodBeanDataTable dataTableObjForEditCompare;

	private List<CommunicationMethodBeanDataTable> communicationMethodList = new CopyOnWriteArrayList<CommunicationMethodBeanDataTable>();
	private List<CommunicationMethodBeanDataTable> communicationMethodNewList = new CopyOnWriteArrayList<CommunicationMethodBeanDataTable>();
	private List<CommunicationMethodDTO> saveRecordList = new CopyOnWriteArrayList<CommunicationMethodDTO>();

	@Autowired
	ICommunicationMethodService communicationMethodService;

	public CommunicationMethodBeanDataTable getDataTableObjForEditCompare() {
		return dataTableObjForEditCompare;
	}

	public void setDataTableObjForEditCompare(
			CommunicationMethodBeanDataTable dataTableObjForEditCompare) {
		this.dataTableObjForEditCompare = dataTableObjForEditCompare;
	}

	public List<CommunicationMethodBeanDataTable> getCommunicationMethodList() {
		return communicationMethodList;
	}

	public void setCommunicationMethodList(
			List<CommunicationMethodBeanDataTable> communicationMethodList) {
		this.communicationMethodList = communicationMethodList;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getCommunicationMethodCodeForDailog() {
		return communicationMethodCodeForDailog;
	}

	public void setCommunicationMethodCodeForDailog(
			String communicationMethodCodeForDailog) {
		this.communicationMethodCodeForDailog = communicationMethodCodeForDailog;
	}

	public Boolean getReadOnlyCommunicationCode() {
		return readOnlyCommunicationCode;
	}

	public void setReadOnlyCommunicationCode(Boolean readOnlyCommunicationCode) {
		this.readOnlyCommunicationCode = readOnlyCommunicationCode;
	}

	public Boolean getReadOnlyEmail() {
		return readOnlyEmail;
	}

	public void setReadOnlyEmail(Boolean readOnlyEmail) {
		this.readOnlyEmail = readOnlyEmail;
	}

	public Boolean getReadOnlyCommunicationFullDesc() {
		return readOnlyCommunicationFullDesc;
	}

	public void setReadOnlyCommunicationFullDesc(
			Boolean readOnlyCommunicationFullDesc) {
		this.readOnlyCommunicationFullDesc = readOnlyCommunicationFullDesc;
	}

	public Boolean getReadOnlyCommunicationFullDescLocal() {
		return readOnlyCommunicationFullDescLocal;
	}

	public void setReadOnlyCommunicationFullDescLocal(
			Boolean readOnlyCommunicationFullDescLocal) {
		this.readOnlyCommunicationFullDescLocal = readOnlyCommunicationFullDescLocal;
	}

	public Boolean getReadOnlyCommunicationShortDesc() {
		return readOnlyCommunicationShortDesc;
	}

	public void setReadOnlyCommunicationShortDesc(
			Boolean readOnlyCommunicationShortDesc) {
		this.readOnlyCommunicationShortDesc = readOnlyCommunicationShortDesc;
	}

	public Boolean getReadOnlyCommunicationShortDescLocal() {
		return readOnlyCommunicationShortDescLocal;
	}

	public void setReadOnlyCommunicationShortDescLocal(
			Boolean readOnlyCommunicationShortDescLocal) {
		this.readOnlyCommunicationShortDescLocal = readOnlyCommunicationShortDescLocal;
	}

	public String getCommunicationFullDescLocal() {
		return communicationFullDescLocal;
	}

	public void setCommunicationFullDescLocal(String communicationFullDescLocal) {
		this.communicationFullDescLocal = communicationFullDescLocal;
	}

	public String getCommunicationShortDescLocal() {
		return communicationShortDescLocal;
	}

	public void setCommunicationShortDescLocal(
			String communicationShortDescLocal) {
		this.communicationShortDescLocal = communicationShortDescLocal;
	}

	public BigDecimal getCommunicationMethodPk() {
		return communicationMethodPk;
	}

	public void setCommunicationMethodPk(BigDecimal communicationMethodPk) {
		this.communicationMethodPk = communicationMethodPk;
	}

	public BigDecimal getCommunicationMethodDescPk() {
		return communicationMethodDescPk;
	}

	public void setCommunicationMethodDescPk(
			BigDecimal communicationMethodDescPk) {
		this.communicationMethodDescPk = communicationMethodDescPk;
	}

	public BigDecimal getCommunicationMethodLocalDescPk() {
		return communicationMethodLocalDescPk;
	}

	public void setCommunicationMethodLocalDescPk(
			BigDecimal communicationMethodLocalDescPk) {
		this.communicationMethodLocalDescPk = communicationMethodLocalDescPk;
	}

	public SessionStateManage getSession() {
		return session;
	}

	public void setSession(SessionStateManage session) {
		this.session = session;
	}

	public BigDecimal getEngLanguageId() {
		return engLanguageId;
	}

	public void setEngLanguageId(BigDecimal engLanguageId) {
		this.engLanguageId = engLanguageId;
	}

	public BigDecimal getArbLanguageId() {
		return arbLanguageId;
	}

	public void setArbLanguageId(BigDecimal arbLanguageId) {
		this.arbLanguageId = arbLanguageId;
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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public String getAccountTypeCodeForDailog() {
		return accountTypeCodeForDailog;
	}

	public void setAccountTypeCodeForDailog(String accountTypeCodeForDailog) {
		this.accountTypeCodeForDailog = accountTypeCodeForDailog;
	}

	public BigDecimal getCountNoOfSave() {
		return countNoOfSave;
	}

	public void setCountNoOfSave(BigDecimal countNoOfSave) {
		this.countNoOfSave = countNoOfSave;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public BigDecimal getSelectType() {
		return selectType;
	}

	public void setSelectType(BigDecimal selectType) {
		this.selectType = selectType;
	}

	public Boolean getRenderSaveButton() {
		return renderSaveButton;
	}

	public void setRenderSaveButton(Boolean renderSaveButton) {
		this.renderSaveButton = renderSaveButton;
	}

	public Boolean getRenderDataTable() {
		return renderDataTable;
	}

	public void setRenderDataTable(Boolean renderDataTable) {
		this.renderDataTable = renderDataTable;
	}

	public Boolean getRenderSavePanel() {
		return renderSavePanel;
	}

	public void setRenderSavePanel(Boolean renderSavePanel) {
		this.renderSavePanel = renderSavePanel;
	}

	public Boolean getRenderUpdatePanel() {
		return renderUpdatePanel;
	}

	public void setRenderUpdatePanel(Boolean renderUpdatePanel) {
		this.renderUpdatePanel = renderUpdatePanel;
	}

	public String getCommunicationMethodCode() {
		return communicationMethodCode;
	}

	public void setCommunicationMethodCode(String communicationMethodCode) {
		this.communicationMethodCode = communicationMethodCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCommunicationFullDesc() {
		return communicationFullDesc;
	}

	public void setCommunicationFullDesc(String communicationFullDesc) {
		this.communicationFullDesc = communicationFullDesc;
	}

	public String getCommunicationShortDesc() {
		return communicationShortDesc;
	}

	public void setCommunicationShortDesc(String communicationShortDesc) {
		this.communicationShortDesc = communicationShortDesc;
	}

	public Boolean getDisableSubmitButton() {
		return disableSubmitButton;
	}

	public void setDisableSubmitButton(Boolean disableSubmitButton) {
		this.disableSubmitButton = disableSubmitButton;
	}

	public Boolean getDisableClearButton() {
		return disableClearButton;
	}

	public void setDisableClearButton(Boolean disableClearButton) {
		this.disableClearButton = disableClearButton;
	}

	public Boolean getIfEditClicked() {
		return ifEditClicked;
	}

	public void setIfEditClicked(Boolean ifEditClicked) {
		this.ifEditClicked = ifEditClicked;
	}

	public Boolean getDisableEditButton() {
		return disableEditButton;
	}

	public void setDisableEditButton(Boolean disableEditButton) {
		this.disableEditButton = disableEditButton;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void addRecords() {

		try {

			
			CommunicationMethodBeanDataTable communicationDTObjForEditCompare = getDataTableObjForEditCompare();
			setDisableSubmitButton(false);
			setDisableClearButton(false);
			setDisableEditButton(false);
			CommunicationMethodBeanDataTable communicationDTObj = new CommunicationMethodBeanDataTable();

			communicationDTObj
					.setCommunicationMethodPk(getCommunicationMethodPk());
			communicationDTObj
					.setCommunicationMethodDescPk(getCommunicationMethodDescPk());
			communicationDTObj
					.setCommunicationMethodLocalDescPk(getCommunicationMethodLocalDescPk());
			communicationDTObj
					.setCommunicationMethodCode(getCommunicationMethodCode());
			if (getEmail().equalsIgnoreCase(Constants.Yes)) {
				communicationDTObj.setEmailId(Constants.YES);
				communicationDTObj.setEmail(Constants.Yes);
			} else {
				communicationDTObj.setEmailId(Constants.NO);
				communicationDTObj.setEmail(Constants.No);
			}
			communicationDTObj.setCountryId(session.getCountryId());
			communicationDTObj
					.setCommunicationFullDesc(getCommunicationFullDesc());
			communicationDTObj
					.setCommunicationShortDesc(getCommunicationShortDesc());
			communicationDTObj
					.setCommunicationFullDescLocal(getCommunicationFullDescLocal());
			communicationDTObj
					.setCommunicationShortDescLocal(getCommunicationShortDescLocal());
			communicationDTObj.setEngLanguageId(getEngLanguageId());
			communicationDTObj.setArbLanguageId(getArbLanguageId());
			communicationDTObj.setCreatedBy(getCreatedBy());
			communicationDTObj.setCreatedDate(getCreatedDate());
			if (getCommunicationMethodPk() != null) {
				if (getIfEditClicked().equals(true)
						&& getDataTableObjForEditCompare() != null) {
					if (communicationDTObj.getCommunicationMethodCode()
							.equalsIgnoreCase(
									dataTableObjForEditCompare
											.getCommunicationMethodCode())
							&& communicationDTObj.getEmail().equalsIgnoreCase(
									dataTableObjForEditCompare.getEmail())
							&& communicationDTObj
									.getCommunicationFullDesc()
									.equalsIgnoreCase(
											dataTableObjForEditCompare
													.getCommunicationFullDesc())
							&& communicationDTObj
									.getCommunicationShortDesc()
									.equalsIgnoreCase(
											dataTableObjForEditCompare
													.getCommunicationShortDesc())
							&& communicationDTObj
									.getCommunicationFullDescLocal()
									.equalsIgnoreCase(
											dataTableObjForEditCompare
													.getCommunicationFullDescLocal())
							&& communicationDTObj
									.getCommunicationShortDescLocal()
									.equalsIgnoreCase(
											dataTableObjForEditCompare
													.getCommunicationShortDescLocal())) {
						communicationDTObj
								.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
						communicationDTObj.setIfEditClicked(false);
						communicationDTObj.setApprovedBy(getApprovedBy());
						communicationDTObj.setApprovedDate(getApprovedDate());
						communicationDTObj.setIsActive(getIsActive());
						communicationDTObj.setRemarks(getRemarks());
						communicationDTObj.setModifiedBy(getModifiedBy());
						communicationDTObj.setModifiedDate(getModifiedDate());
					} else {
						communicationDTObj
								.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						communicationDTObj.setIfEditClicked(true);
						communicationDTObj.setApprovedBy(null);
						communicationDTObj.setApprovedDate(null);
						communicationDTObj.setIsActive(Constants.U);
						communicationDTObj.setRemarks(null);
						communicationDTObj.setModifiedBy(session.getUserName());
						communicationDTObj.setModifiedDate(new Date());
					}
				}

			} else {
				communicationDTObj
						.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				communicationDTObj.setCreatedBy(session.getUserName());
				communicationDTObj.setCreatedDate(new Date());
				communicationDTObj.setIsActive(Constants.U);
				communicationDTObj.setIfEditClicked(true);
				communicationMethodNewList.add(communicationDTObj);
			}

			communicationMethodList.add(communicationDTObj);
			clearAllFields();
			setRenderSaveButton(true);
			setRenderDataTable(true);

		} catch (NullPointerException ne) {
			log.info("Method Name::addRecords" + ne.getMessage());
			setErrorMessage("Method Name::addRecords");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("Method Name::addRecords" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	public void addRecordsToDataTable() {

		try {
			List<CommunicationMethodDTO> bankAccountDuplicateCheckList = communicationMethodService
					.fetchCommunicationMethodCodeRecord(getCommunicationMethodCode());
			if (bankAccountDuplicateCheckList.size() > 0
					&& getIfEditClicked().equals(false)) {
				setCommunicationMethodCodeForDailog(getCommunicationMethodCode());
				setCommunicationMethodCode(null);
				clearAllFields();
				RequestContext.getCurrentInstance().execute(
						"recordAlreadyExist.show();");
				setDisableSubmitButton(false);
				setDisableClearButton(false);
				setDisableEditButton(false);
				return;
			} else if (communicationMethodList.size() != 0) {
				for (CommunicationMethodBeanDataTable communicationDTObj : communicationMethodList) {
					if (communicationDTObj.getCommunicationMethodCode()
							.equalsIgnoreCase(getCommunicationMethodCode())) {
						setCommunicationMethodCodeForDailog(getCommunicationMethodCode());
						setCommunicationMethodCode(null);
						clearAllFields();
						RequestContext.getCurrentInstance().execute(
								"recordAlreadyExist.show();");
						setDisableSubmitButton(false);
						setDisableClearButton(false);
						setDisableEditButton(false);
						return;
					}
				}
			}
			addRecords();
		} catch (NullPointerException ne) {
			log.info("Method Name::addRecordsToDataTable" + ne.getMessage());
			setErrorMessage("Method Name::addRecordsToDataTable");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("Method Name::addRecordsToDataTable"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	public void editRecord(CommunicationMethodBeanDataTable communicationDTObj) {

		try {
			setDisableSubmitButton(true);
			setDisableClearButton(true);
			setDisableEditButton(true);
			setDataTableObjForEditCompare(communicationDTObj);

			setCommunicationMethodCode(communicationDTObj
					.getCommunicationMethodCode());
			setEmail(communicationDTObj.getEmail());
			setCountryId(communicationDTObj.getCountryId());
			setCommunicationFullDesc(communicationDTObj
					.getCommunicationFullDesc());
			setCommunicationShortDesc(communicationDTObj
					.getCommunicationShortDesc());
			setCommunicationFullDescLocal(communicationDTObj
					.getCommunicationFullDescLocal());
			setCommunicationShortDescLocal(communicationDTObj
					.getCommunicationShortDescLocal());
			setCommunicationMethodPk(communicationDTObj
					.getCommunicationMethodPk());
			setCommunicationMethodDescPk(communicationDTObj
					.getCommunicationMethodDescPk());
			setCommunicationMethodLocalDescPk(communicationDTObj
					.getCommunicationMethodLocalDescPk());
			setEngLanguageId(communicationDTObj.getEngLanguageId());
			setArbLanguageId(communicationDTObj.getArbLanguageId());
			setCreatedBy(communicationDTObj.getCreatedBy());
			setCreatedDate(communicationDTObj.getCreatedDate());
			if (communicationDTObj.getEmailId().equalsIgnoreCase(Constants.YES)) {
				setEmail(Constants.Yes);
				setEmailId(communicationDTObj.getEmailId());
			} else {
				setEmail(Constants.No);
				setEmailId(communicationDTObj.getEmailId());
			}

			setIsActive(communicationDTObj.getIsActive());
			setRemarks(communicationDTObj.getRemarks());
			setApprovedBy(communicationDTObj.getApprovedBy());
			setApprovedDate(communicationDTObj.getApprovedDate());
			setDynamicLabelForActivateDeactivate(communicationDTObj
					.getDynamicLabelForActivateDeactivate());
			setModifiedBy(communicationDTObj.getModifiedBy());
			setModifiedDate(communicationDTObj.getModifiedDate());
			setIfEditClicked(true);
			communicationMethodList.remove(communicationDTObj);
			communicationMethodNewList.remove(communicationDTObj);
			if (communicationMethodList.size() == 0) {
				setRenderSaveButton(false);
				setRenderDataTable(false);
			}
		} catch (NullPointerException ne) {
			log.info("Method Name::editRecord" + ne.getMessage());
			setErrorMessage("Method Name::editRecord");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("Method Name::editRecord" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void clearAllFields() {

		setCommunicationMethodCode(null);
		setEmail(null);
		setEmailId(null);
		setCountryId(null);
		setCommunicationFullDesc(null);
		setCommunicationShortDesc(null);
		setCommunicationFullDescLocal(null);
		setCommunicationShortDescLocal(null);
		setCommunicationMethodPk(null);
		setCommunicationMethodDescPk(null);
		setCommunicationMethodLocalDescPk(null);
		setEngLanguageId(null);
		setArbLanguageId(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setIsActive(null);
		setRemarks(null);
		setDynamicLabelForActivateDeactivate(null);
		setIfEditClicked(false);
		setDataTableObjForEditCompare(null);
	}

	public void remarkSelectedRecord() {
		for (CommunicationMethodBeanDataTable communicationDTObj : communicationMethodList) {
			if (communicationDTObj.getRemarksCheck() != null) {
				if (communicationDTObj.getRemarksCheck().equals(true)) {
					if (getRemarks() != null) {
						communicationDTObj.setRemarks(getRemarks());
						updateRecord(communicationDTObj);
						viewAllrecords();
						clearAllFields();
					} else {
						RequestContext.getCurrentInstance().execute(
								"remarksEmpty.show();");
					}
				}
			}
		}
	}

	public void updateRecord(CommunicationMethodBeanDataTable communicationDTObj) {
		saveRecordList.clear();

		try {
			CommunicationMethodDTO communicationMethodDTO = new CommunicationMethodDTO();

			communicationMethodDTO.setCommunicationMethodPk(communicationDTObj
					.getCommunicationMethodPk());
			communicationMethodDTO
					.setCommunicationMethodCode(communicationDTObj
							.getCommunicationMethodCode());
			// communicationMethodDTO.setEmail(communicationDTObj.getEmail());
			if (communicationDTObj.getEmail().equalsIgnoreCase(Constants.YES)) {
				communicationMethodDTO.setEmailId(Constants.YES);
				communicationMethodDTO.setEmail(Constants.Yes);
			} else {
				communicationMethodDTO.setEmailId(Constants.NO);
				communicationMethodDTO.setEmail(Constants.No);
			}
			// communicationMethodDTO.setEmailId(communicationDTObj.getEmailId());
			communicationMethodDTO.setCountryId(communicationDTObj
					.getCountryId());

			communicationMethodDTO.setCreatedBy(communicationDTObj
					.getCreatedBy());
			communicationMethodDTO.setCreatedDate(communicationDTObj
					.getCreatedDate());
			communicationMethodDTO.setModifiedBy(session.getUserName());
			communicationMethodDTO.setModifiedDate(new Date());
			communicationMethodDTO.setIsActive(Constants.D);
			communicationMethodDTO.setApprovedBy(null);
			communicationMethodDTO.setApprovedDate(null);
			communicationMethodDTO.setRemarks(communicationDTObj.getRemarks());

			communicationMethodDTO
					.setCommunicationMethodDescPk(communicationDTObj
							.getCommunicationMethodDescPk());
			communicationMethodDTO
					.setCommunicationMethodLocalDescPk(communicationDTObj
							.getCommunicationMethodLocalDescPk());
			communicationMethodDTO.setArbLanguageId(communicationDTObj
					.getArbLanguageId());
			communicationMethodDTO.setEngLanguageId(communicationDTObj
					.getEngLanguageId());
			communicationMethodDTO.setCommunicationShortDesc(communicationDTObj
					.getCommunicationShortDesc());
			communicationMethodDTO.setCommunicationFullDesc(communicationDTObj
					.getCommunicationFullDesc());
			communicationMethodDTO
					.setCommunicationFullDescLocal(communicationDTObj
							.getCommunicationFullDescLocal());
			communicationMethodDTO
					.setCommunicationShortDescLocal(communicationDTObj
							.getCommunicationShortDescLocal());

			communicationMethodService.saveAllRecords(communicationMethodDTO);
		} catch (NullPointerException ne) {
			log.info("Method Name::updateRecord" + ne.getMessage());
			setErrorMessage("Method Name::updateRecord");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("Method Name::updateRecord" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void checkStatusType(
			CommunicationMethodBeanDataTable communicationDTObj) {
		if (communicationDTObj.getDynamicLabelForActivateDeactivate()
				.equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {

			RequestContext.getCurrentInstance().execute("pending.show();");
			return;
		} else if (communicationDTObj.getDynamicLabelForActivateDeactivate()
				.equalsIgnoreCase(Constants.REMOVE)) {
			communicationMethodList.remove(communicationDTObj);
			communicationMethodNewList.remove(communicationDTObj);
		} else if (communicationDTObj.getDynamicLabelForActivateDeactivate()
				.equalsIgnoreCase(Constants.DEACTIVATE)) {
			communicationDTObj.setRemarksCheck(true);
			setApprovedBy(communicationDTObj.getApprovedBy());
			setApprovedDate(communicationDTObj.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
			return;
		} else if (communicationDTObj.getDynamicLabelForActivateDeactivate()
				.equalsIgnoreCase(Constants.ACTIVATE)) {
			communicationDTObj.setActivateRecordCheck(true);
			RequestContext.getCurrentInstance().execute(
					"activateRecord.show();");
			return;
		} else if (communicationDTObj.getDynamicLabelForActivateDeactivate()
				.equalsIgnoreCase(Constants.DELETE)) {
			if (communicationDTObj.getModifiedBy() == null
					&& communicationDTObj.getModifiedDate() == null
					&& communicationDTObj.getApprovedBy() == null
					&& communicationDTObj.getApprovedDate() == null
					&& communicationDTObj.getRemarks() == null) {
				communicationDTObj.setPermanetDeleteCheck(true);
				RequestContext.getCurrentInstance().execute(
						"permanentDelete.show();");
				return;
			}
		}
		if (communicationMethodList.size() == 0) {
			setRenderSaveButton(false);
			setRenderDataTable(false);
		}
	}

	public void confirmPermanentDelete() {
		if (communicationMethodList.size() > 0) {
			for (CommunicationMethodBeanDataTable communicationDTObj : communicationMethodList) {
				if (communicationDTObj.getPermanetDeleteCheck() != null) {
					if (communicationDTObj.getPermanetDeleteCheck()
							.equals(true)) {
						deleteRecordPermanently(communicationDTObj);
						communicationMethodList.remove(communicationDTObj);
					}
				}
			}
		}
	}

	public void confirmActivate(
			CommunicationMethodBeanDataTable communicationDTObj) {
		communicationMethodService.activateRecord(
				communicationDTObj.getCommunicationMethodPk(),
				session.getUserName());
		viewAllrecords();
	}

	public void activateRecord() {
		if (communicationMethodList.size() > 0) {
			for (CommunicationMethodBeanDataTable communicationDTObj : communicationMethodList) {
				if (communicationDTObj.getActivateRecordCheck() != null) {
					if (communicationDTObj.getActivateRecordCheck()
							.equals(true)) {
						confirmActivate(communicationDTObj);
					}
				}
			}
		}
	}

	public void deleteRecordPermanently(
			CommunicationMethodBeanDataTable communicationDTObj) {
		communicationMethodService.deleteRecordPermanently(
				communicationDTObj.getCommunicationMethodPk(),
				communicationDTObj.getCommunicationMethodDescPk(),
				communicationDTObj.getCommunicationMethodLocalDescPk());
	}

	public void saveRecords() {

		// saveRecordList.clear();

		try {
			System.out.println("STARING TIME  :" + System.currentTimeMillis());
			for (CommunicationMethodBeanDataTable communicationDTObj : communicationMethodList) {
				if (communicationDTObj.getIfEditClicked().equals(true)) {
					count = count + 1;
					CommunicationMethodDTO communicationMethodDTO = new CommunicationMethodDTO();
					communicationMethodDTO
							.setCommunicationMethodPk(communicationDTObj
									.getCommunicationMethodPk());
					communicationMethodDTO
							.setCommunicationMethodCode(communicationDTObj
									.getCommunicationMethodCode());
					communicationMethodDTO.setEmail(communicationDTObj
							.getEmail());
					communicationMethodDTO.setEmailId(communicationDTObj
							.getEmailId());
					communicationMethodDTO.setCountryId(communicationDTObj
							.getCountryId());
					communicationMethodDTO.setCreatedBy(communicationDTObj
							.getCreatedBy());
					communicationMethodDTO.setCreatedDate(communicationDTObj
							.getCreatedDate());
					communicationMethodDTO.setModifiedBy(communicationDTObj
							.getModifiedBy());
					communicationMethodDTO.setModifiedDate(communicationDTObj
							.getModifiedDate());
					communicationMethodDTO.setIsActive(communicationDTObj
							.getIsActive());
					communicationMethodDTO.setApprovedBy(communicationDTObj
							.getApprovedBy());
					communicationMethodDTO.setApprovedDate(communicationDTObj
							.getApprovedDate());
					communicationMethodDTO.setRemarks(communicationDTObj
							.getRemarks());
					communicationMethodDTO
							.setCommunicationMethodDescPk(communicationDTObj
									.getCommunicationMethodDescPk());
					communicationMethodDTO
							.setCommunicationMethodLocalDescPk(communicationDTObj
									.getCommunicationMethodLocalDescPk());
					communicationMethodDTO.setArbLanguageId(new BigDecimal(
							Constants.ARABIC_LANGUAGE_ID));
					communicationMethodDTO.setEngLanguageId(new BigDecimal(
							Constants.ENGLISH_LANGUAGE_ID));
					communicationMethodDTO
							.setCommunicationShortDesc(communicationDTObj
									.getCommunicationShortDesc());
					communicationMethodDTO
							.setCommunicationShortDescLocal(communicationDTObj
									.getCommunicationShortDescLocal());
					communicationMethodDTO
							.setCommunicationFullDesc(communicationDTObj
									.getCommunicationFullDesc());
					communicationMethodDTO
							.setCommunicationFullDescLocal(communicationDTObj
									.getCommunicationFullDescLocal());

					communicationMethodService
							.saveAllRecords(communicationMethodDTO);
				}
			}
			System.out.println("END  TIME  :" + System.currentTimeMillis());
			setCountNoOfSave(new BigDecimal(count));
			count = 0;
			RequestContext.getCurrentInstance().execute("complete.show();");
			clearAllFields();
			communicationMethodList.clear();
			communicationMethodNewList.clear();
		} catch (NullPointerException ne) {
			log.info("Method Name::saveRecords" + ne.getMessage());
			setErrorMessage("Method Name::saveRecords");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("Method Name::saveRecords" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	public void exit() throws IOException {
		communicationMethodList.clear();
		communicationMethodNewList.clear();
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		}
	}

	public void viewAllrecords() {
		try {

			Boolean childRecordCheck = false;
			clearAllFields();
			setDisableClearButton(false);
			setDisableSubmitButton(false);
			setDisableEditButton(false);
			communicationMethodList.clear();
			List<CommunicationMethodDTO> lstCommunicationMethodDTO = communicationMethodService
					.viewRecords();
			if (lstCommunicationMethodDTO.size() > 0) {
				setRenderSaveButton(true);
				setRenderDataTable(true);
			} else {
				setRenderSaveButton(false);
				setRenderDataTable(false);
				RequestContext.getCurrentInstance()
						.execute("noRecords.show();");
				return;
			}
			for (CommunicationMethodDTO communicationMethodDTO : lstCommunicationMethodDTO) {
				CommunicationMethodBeanDataTable communicationDTObj = new CommunicationMethodBeanDataTable();
				communicationDTObj
						.setCommunicationMethodCode(communicationMethodDTO
								.getCommunicationMethodCode());
				communicationDTObj.setEmail(communicationMethodDTO.getEmail());
				communicationDTObj.setCountryId(communicationMethodDTO
						.getCountryId());
				communicationDTObj
						.setCommunicationMethodPk(communicationMethodDTO
								.getCommunicationMethodId());
				communicationDTObj.setCreatedBy(communicationMethodDTO
						.getCreatedBy());
				communicationDTObj.setCreatedDate(communicationMethodDTO
						.getCreatedDate());
				communicationDTObj.setModifiedBy(communicationMethodDTO
						.getModifiedBy());
				communicationDTObj.setModifiedDate(communicationMethodDTO
						.getModifiedDate());
				communicationDTObj.setApprovedBy(communicationMethodDTO
						.getApprovedBy());
				communicationDTObj.setApprovedDate(communicationMethodDTO
						.getApprovedDate());
				communicationDTObj.setIsActive(communicationMethodDTO
						.getIsActive());
				communicationDTObj.setRemarks(communicationMethodDTO
						.getRemarks());
				if (communicationMethodDTO.getEmail().equalsIgnoreCase(
						Constants.Yes)) {
					communicationDTObj.setEmailId(Constants.YES);
					communicationDTObj.setEmail(Constants.Yes);
				} else {
					communicationDTObj.setEmailId(Constants.NO);
					communicationDTObj.setEmail(Constants.No);
				}
				if (communicationMethodDTO.getIsActive().equalsIgnoreCase(
						Constants.Yes)) {
					communicationDTObj
							.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				} else if (communicationMethodDTO.getIsActive()
						.equalsIgnoreCase(Constants.D)) {
					communicationDTObj
							.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				} else if (communicationMethodDTO.getIsActive()
						.equalsIgnoreCase(Constants.U)
						&& communicationDTObj.getModifiedBy() == null
						&& communicationDTObj.getModifiedDate() == null
						&& communicationDTObj.getApprovedBy() == null
						&& communicationDTObj.getApprovedDate() == null
						&& communicationDTObj.getRemarks() == null) {
					communicationDTObj
							.setDynamicLabelForActivateDeactivate(Constants.DELETE);
				} else {
					communicationDTObj
							.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				}
				List<CommunicationMethodDTO> lstCommunicationMethodDTO1 = communicationMethodService
						.getAllRecordsRelatedCommunicationMethod(communicationMethodDTO
								.getCommunicationMethodId());
				if (lstCommunicationMethodDTO1.size() > 0) {
					childRecordCheck = true;
					for (CommunicationMethodDTO communicationMethodDTO1 : lstCommunicationMethodDTO1) {
						if (communicationMethodDTO1.getLanguageId().intValue() == 1) {
							communicationDTObj
									.setCommunicationFullDesc(communicationMethodDTO1
											.getCommunicationFullDesc());
							communicationDTObj
									.setCommunicationShortDesc(communicationMethodDTO1
											.getCommunicationShortDesc());
							communicationDTObj
									.setCommunicationMethodDescPk(communicationMethodDTO1
											.getCommunicationMethodDescId());
							communicationDTObj
									.setEngLanguageId(communicationMethodDTO1
											.getLanguageId());
						}
						if (communicationMethodDTO1.getLanguageId().intValue() == 2) {
							communicationDTObj
									.setCommunicationFullDescLocal(communicationMethodDTO1
											.getCommunicationFullDesc());
							communicationDTObj
									.setCommunicationShortDescLocal(communicationMethodDTO1
											.getCommunicationShortDesc());
							communicationDTObj
									.setCommunicationMethodLocalDescPk(communicationMethodDTO1
											.getCommunicationMethodDescId());
							communicationDTObj
									.setArbLanguageId(communicationMethodDTO1
											.getLanguageId());
						}
					}
				} else {
					childRecordCheck = false;
				}

				if (childRecordCheck == true) {
					communicationMethodList.add(communicationDTObj);
				}
			}
			communicationMethodList.addAll(communicationMethodNewList);
			clearAllFields();

			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../complaint/communicationMethod.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (NullPointerException ne) {
			log.info("Method Name::viewAllrecords" + ne.getMessage());
			setErrorMessage("Method Name::viewAllrecords");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("Method Name::viewAllrecords" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public List<String> populate(String query) {
		if (query.length() > 0) {
			return communicationMethodService
					.getCommunicationMethodCodeList(query);
		} else {
			return null;
		}
	}

	public void itemSelectPopulate() {
		// setCommunicationMethodCode(null);
		setEmail(null);
		setEmailId(null);
		setCountryId(null);
		setCommunicationFullDesc(null);
		setCommunicationShortDesc(null);
		setCommunicationFullDescLocal(null);
		setCommunicationShortDescLocal(null);
		setCommunicationMethodPk(null);
		setCommunicationMethodDescPk(null);
		setCommunicationMethodLocalDescPk(null);
		setEngLanguageId(null);
		setArbLanguageId(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setIsActive(null);
		setRemarks(null);

		setDynamicLabelForActivateDeactivate(null);
		setDisableSubmitButton(true);
		setDisableClearButton(true);
		setDisableEditButton(true);
		List<CommunicationMethodDTO> bankAccountDuplicateCheckList = communicationMethodService
				.fetchCommunicationMethodCodeRecord(getCommunicationMethodCode());
		if (bankAccountDuplicateCheckList.size() > 0
				&& getIfEditClicked().equals(false)) {
			setCommunicationMethodCodeForDailog(getCommunicationMethodCode());
			setCommunicationMethodCode(null);
			clearAllFields();
			RequestContext.getCurrentInstance().execute(
					"recordAlreadyExist.show();");
			setDisableSubmitButton(false);
			setDisableClearButton(false);
			setDisableEditButton(false);
			return;
		}
		/*
		 * List<CommunicationMethodDTO> lstCommunicationMethodDTO1 =
		 * communicationMethodService
		 * .fetchCommunicationMethodCodeRecord(getCommunicationMethodCode());
		 * if(lstCommunicationMethodDTO1.size()>0){
		 * if(lstCommunicationMethodDTO1
		 * .get(0).getIsActive().equalsIgnoreCase(Constants.Yes)){
		 * setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE); }else
		 * if(lstCommunicationMethodDTO1
		 * .get(0).getIsActive().equalsIgnoreCase(Constants.D)){
		 * setDynamicLabelForActivateDeactivate(Constants.ACTIVATE); }else
		 * if(lstCommunicationMethodDTO1
		 * .get(0).getIsActive().equalsIgnoreCase(Constants.U)&&
		 * lstCommunicationMethodDTO1.get(0).getModifiedBy()==null &&
		 * lstCommunicationMethodDTO1.get(0).getModifiedDate()==null &&
		 * lstCommunicationMethodDTO1.get(0).getApprovedBy()==null &&
		 * lstCommunicationMethodDTO1.get(0).getApprovedDate()==null &&
		 * lstCommunicationMethodDTO1.get(0).getRemarks()==null){
		 * setDynamicLabelForActivateDeactivate(Constants.DELETE); }else{
		 * setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
		 * } if(lstCommunicationMethodDTO1.get(0).getEmail().equalsIgnoreCase(
		 * Constants.Yes)){ setEmailId(Constants.YES); }else{
		 * setEmailId(Constants.NO); }
		 * setCommunicationMethodCode(lstCommunicationMethodDTO1
		 * .get(0).getCommunicationMethodCode());
		 * setEmail(lstCommunicationMethodDTO1.get(0).getEmail());
		 * setCountryId(lstCommunicationMethodDTO1.get(0).getCountryId());
		 * setCommunicationMethodPk
		 * (lstCommunicationMethodDTO1.get(0).getCommunicationMethodPk());
		 * setCreatedBy(lstCommunicationMethodDTO1.get(0).getCreatedBy());
		 * setCreatedDate(lstCommunicationMethodDTO1.get(0).getCreatedDate());
		 * setModifiedBy(lstCommunicationMethodDTO1.get(0).getModifiedBy());
		 * setModifiedDate(lstCommunicationMethodDTO1.get(0).getModifiedDate());
		 * setApprovedBy(lstCommunicationMethodDTO1.get(0).getApprovedBy());
		 * setApprovedDate(lstCommunicationMethodDTO1.get(0).getApprovedDate());
		 * setIsActive(lstCommunicationMethodDTO1.get(0).getIsActive());
		 * setRemarks(lstCommunicationMethodDTO1.get(0).getRemarks());
		 * 
		 * List<CommunicationMethodDTO>
		 * lstlstCommunicationMethodDTO1=communicationMethodService
		 * .getAllRecordsRelatedCommunicationMethod(getCommunicationMethodPk());
		 * for(CommunicationMethodDTO
		 * communicationMethodDTO:lstlstCommunicationMethodDTO1){
		 * if(communicationMethodDTO.getLanguageId().intValue()==1){
		 * setCommunicationFullDesc
		 * (communicationMethodDTO.getCommunicationFullDesc());
		 * setCommunicationShortDesc
		 * (communicationMethodDTO.getCommunicationShortDesc());
		 * setCommunicationMethodDescPk
		 * (communicationMethodDTO.getCommunicationMethodDescId());
		 * setEngLanguageId(communicationMethodDTO.getLanguageId()); }
		 * if(communicationMethodDTO.getLanguageId().intValue()==2){
		 * setCommunicationFullDescLocal
		 * (communicationMethodDTO.getCommunicationFullDesc());
		 * setCommunicationShortDescLocal
		 * (communicationMethodDTO.getCommunicationShortDesc());
		 * setCommunicationMethodLocalDescPk
		 * (communicationMethodDTO.getCommunicationMethodDescId());
		 * setArbLanguageId(communicationMethodDTO.getLanguageId()); } }
		 * 
		 * }
		 */
	}

	public void clearRecords() throws IOException {
		clearAllFields();
		// FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/communicationMethod.xhtml");
	}

	public void disableSubmit() {
		setDisableSubmitButton(true);
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToCommunicationMethodPage() {
		clearAllFields();
		communicationMethodList.clear();
		communicationMethodNewList.clear();
		setAccountTypeCodeForDailog(null);
		setDisableSubmitButton(false);
		setDisableClearButton(false);
		setRenderSaveButton(false);
		setDisableEditButton(false);
		setRenderDataTable(false);
		setReadOnlyCommunicationCode(false);
		setReadOnlyCommunicationFullDesc(false);
		setReadOnlyCommunicationShortDesc(false);
		setReadOnlyCommunicationFullDescLocal(false);
		setReadOnlyCommunicationShortDescLocal(false);
		setReadOnlyEmail(false);
		setRenderSavePanel(true);
		setRenderUpdatePanel(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "communicationMethod.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/communicationMethod.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void clearRemarks() {
		setRemarks(null);
	}

	/* APProval Started */
	public void communactionMethodApprovalPageNavigation() {
		clearAllFields();
		communicationMethodList.clear();
		communicationMethodNewList.clear();
		setAccountTypeCodeForDailog(null);
		setDisableSubmitButton(false);
		setDisableClearButton(false);
		setRenderSaveButton(false);
		setDisableEditButton(false);
		setRenderDataTable(false);
		setRenderSavePanel(false);
		setRenderUpdatePanel(false);
		fetchRecordforComplaintAssignedApproval();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "CommunicationMethodApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/CommunicationMethodApproval.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fetchRecordforComplaintAssignedApproval() {
		try {
			Boolean childRecordCheck = false;
			communicationMethodList.clear();
			communicationMethodNewList.clear();
			List<CommunicationMethodDTO> lstCommunicationMethodDTO = communicationMethodService
					.ApproveRecords();
			if (lstCommunicationMethodDTO.size() > 0) {
				for (CommunicationMethodDTO communicationMethodDTO : lstCommunicationMethodDTO) {
					CommunicationMethodBeanDataTable communicationDTObj = new CommunicationMethodBeanDataTable();
					communicationDTObj
							.setCommunicationMethodCode(communicationMethodDTO
									.getCommunicationMethodCode());
					communicationDTObj.setEmail(communicationMethodDTO
							.getEmail());
					communicationDTObj.setCountryId(communicationMethodDTO
							.getCountryId());
					communicationDTObj
							.setCommunicationMethodPk(communicationMethodDTO
									.getCommunicationMethodId());
					communicationDTObj.setCreatedBy(communicationMethodDTO
							.getCreatedBy());
					communicationDTObj.setCreatedDate(communicationMethodDTO
							.getCreatedDate());
					communicationDTObj.setModifiedBy(communicationMethodDTO
							.getModifiedBy());
					communicationDTObj.setModifiedDate(communicationMethodDTO
							.getModifiedDate());
					communicationDTObj.setApprovedBy(communicationMethodDTO
							.getApprovedBy());
					communicationDTObj.setApprovedDate(communicationMethodDTO
							.getApprovedDate());
					communicationDTObj.setIsActive(communicationMethodDTO
							.getIsActive());
					communicationDTObj.setRemarks(communicationMethodDTO
							.getRemarks());
					if (communicationMethodDTO.getEmail().equalsIgnoreCase(
							Constants.Yes)) {
						communicationDTObj.setEmailId(Constants.YES);
						communicationDTObj.setEmail(Constants.YES);
					} else {
						communicationDTObj.setEmailId(Constants.NO);
						communicationDTObj.setEmail(Constants.NO);
					}
					if (communicationMethodDTO.getIsActive().equalsIgnoreCase(
							Constants.Yes)) {
						communicationDTObj
								.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					} else if (communicationMethodDTO.getIsActive()
							.equalsIgnoreCase(Constants.D)) {
						communicationDTObj
								.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					} else if (communicationMethodDTO.getIsActive()
							.equalsIgnoreCase(Constants.U)
							&& communicationDTObj.getModifiedBy() == null
							&& communicationDTObj.getModifiedDate() == null
							&& communicationDTObj.getApprovedBy() == null
							&& communicationDTObj.getApprovedDate() == null
							&& communicationDTObj.getRemarks() == null) {
						communicationDTObj
								.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					} else {
						communicationDTObj
								.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}
					List<CommunicationMethodDTO> lstCommunicationMethodDTO1 = communicationMethodService
							.getAllRecordsApproveRelatedCommunicationMethod(communicationMethodDTO
									.getCommunicationMethodId());
					if (lstCommunicationMethodDTO1.size() > 0) {
						childRecordCheck = true;
						for (CommunicationMethodDTO communicationMethodDTO1 : lstCommunicationMethodDTO1) {
							if (communicationMethodDTO1.getLanguageId()
									.intValue() == 1) {
								communicationDTObj
										.setCommunicationFullDesc(communicationMethodDTO1
												.getCommunicationFullDesc());
								communicationDTObj
										.setCommunicationShortDesc(communicationMethodDTO1
												.getCommunicationShortDesc());
								communicationDTObj
										.setCommunicationMethodDescPk(communicationMethodDTO1
												.getCommunicationMethodDescId());
								communicationDTObj
										.setEngLanguageId(communicationMethodDTO1
												.getLanguageId());
							}
							if (communicationMethodDTO1.getLanguageId()
									.intValue() == 2) {
								communicationDTObj
										.setCommunicationFullDescLocal(communicationMethodDTO1
												.getCommunicationFullDesc());
								communicationDTObj
										.setCommunicationShortDescLocal(communicationMethodDTO1
												.getCommunicationShortDesc());
								communicationDTObj
										.setCommunicationMethodLocalDescPk(communicationMethodDTO1
												.getCommunicationMethodDescId());
								communicationDTObj
										.setArbLanguageId(communicationMethodDTO1
												.getLanguageId());
							}
						}
					} else {
						childRecordCheck = false;
					}

					if (childRecordCheck == true) {
						communicationMethodList.add(communicationDTObj);
					}

					communicationMethodList.addAll(communicationMethodNewList);
				}
			}
		} catch (NullPointerException ne) {
			log.info("Method Name::fetchRecordforComplaintAssignedApproval"
					+ ne.getMessage());
			setErrorMessage("Method Name::fetchRecordforComplaintAssignedApproval");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("Method Name::fetchRecordforComplaintAssignedApproval"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void approvelCheckForCommunicationMethodUser(
			CommunicationMethodBeanDataTable dataTable) {
		if (!(dataTable.getModifiedBy() == null ? dataTable.getCreatedBy()
				: dataTable.getModifiedBy()).equalsIgnoreCase(session
				.getUserName())) {
			setCommunicationMethodCode(dataTable.getCommunicationMethodCode());
			setCommunicationMethodPk(dataTable.getCommunicationMethodPk());
			setCountryId(dataTable.getCountryId());
			setCommunicationFullDesc(dataTable.getCommunicationFullDesc());
			setCommunicationShortDesc(dataTable.getCommunicationShortDesc());
			setCommunicationFullDescLocal(dataTable
					.getCommunicationFullDescLocal());
			setCommunicationShortDescLocal(dataTable
					.getCommunicationShortDescLocal());
			setIsActive(dataTable.getIsActive());
			setCreatedBy(dataTable.getCreatedBy());
			setCreatedDate(dataTable.getCreatedDate());
			setModifiedBy(dataTable.getModifiedBy());
			setModifiedDate(dataTable.getModifiedDate());
			if (dataTable.getEmailId().equalsIgnoreCase(Constants.YES)) {
				setEmail(Constants.Yes);
				setEmailId(Constants.YES);
			} else {
				setEmail(Constants.No);
				setEmailId(Constants.NO);
			}
			setRenderDataTable(false);
			setRenderSaveButton(false);
			setRenderSavePanel(false);
			setRenderUpdatePanel(true);
			setReadOnlyCommunicationCode(true);
			setReadOnlyCommunicationFullDesc(true);
			setReadOnlyCommunicationShortDesc(true);
			setReadOnlyCommunicationFullDescLocal(true);
			setReadOnlyCommunicationShortDescLocal(true);
			setReadOnlyEmail(true);
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../complaint/communicationMethod.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			RequestContext.getCurrentInstance().execute("notApproved.show();");
		}

	}

	public void approveRecord() {
		try {
			String approveMsg = communicationMethodService.approveReocrd(
					getCommunicationMethodPk(), session.getUserName());
			if (approveMsg.equalsIgnoreCase("Sucess")) {
				RequestContext.getCurrentInstance().execute("approve.show();");
			} else {
				RequestContext.getCurrentInstance().execute(
						"notapprove.show();");
			}

		} catch (Exception exception) {
			log.error("Error occured While Communication Method Type Record  :"
					+ exception.getMessage());
		}
	}

	public void communivationmethodApprovalOk() {
		communicationMethodList.clear();
		communicationMethodNewList.clear();
		setAccountTypeCodeForDailog(null);
		setDisableSubmitButton(false);
		setDisableClearButton(false);
		setRenderSaveButton(false);
		setDisableEditButton(false);
		setRenderDataTable(false);
		setRenderSavePanel(false);
		setRenderUpdatePanel(false);
		fetchRecordforComplaintAssignedApproval();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/CommunicationMethodApproval.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void communcationMethodCancel() {
		setAccountTypeCodeForDailog(null);
		setDisableSubmitButton(false);
		setDisableClearButton(false);
		setRenderSaveButton(false);
		setDisableEditButton(false);
		setRenderDataTable(false);
		setRenderSavePanel(false);
		setRenderUpdatePanel(false);
		fetchRecordforComplaintAssignedApproval();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/CommunicationMethodApproval.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void navigateToBankAccountTypeApprovalPage() {
		fetchRecordforComplaintAssignedApproval();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/CommunicationMethodApproval.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* APProval Ended */
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
