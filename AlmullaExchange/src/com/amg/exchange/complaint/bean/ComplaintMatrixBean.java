package com.amg.exchange.complaint.bean;

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

import com.amg.exchange.bco.bean.BranchComplianceDataTable;
import com.amg.exchange.common.dto.ComplaintActionDTO;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.complaint.DTO.ComplaintMatrixDTO;
import com.amg.exchange.complaint.model.CommunicationMethod;
import com.amg.exchange.complaint.model.CommunicationMethodDesc;
import com.amg.exchange.complaint.model.ComplaintAction;
import com.amg.exchange.complaint.model.ComplaintActionDesc;
import com.amg.exchange.complaint.model.ComplaintAssigned;
import com.amg.exchange.complaint.model.ComplaintAssignedDesc;
import com.amg.exchange.complaint.model.ComplaintMatrix;
import com.amg.exchange.complaint.model.ComplaintTypeDesc;
import com.amg.exchange.complaint.model.ComplaintTypeMaster;
import com.amg.exchange.complaint.service.IComplaintActionService;
import com.amg.exchange.complaint.service.IComplaintAssignedService;
import com.amg.exchange.complaint.service.IComplaintService;
import com.amg.exchange.complaint.service.IComplaintTypeService;
import com.amg.exchange.registration.bean.ArticleLevelDataTable;
import com.amg.exchange.registration.service.ICountryMasterservice;
import com.amg.exchange.remittance.dao.IServiceGroupMasterDao;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleDetailsDesc;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IBankMasterService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("unused")
@Component("complaint")
@Scope("session")
public class ComplaintMatrixBean<T> implements Serializable {

	private static final Logger LOG = Logger
			.getLogger(ComplaintMatrixBean.class);

	/*
	 * Autowire Declaration start here
	 */
	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IBankBranchDetailsService<T> bankBranchDetailsService;

	@Autowired
	IComplaintService<T> complaintService;

	@Autowired
	IComplaintAssignedService complaintAssignedService;

	@Autowired
	IBankMasterService<T> bankMasterService;

	@Autowired
	ICountryMasterservice countryMasterService;

	@Autowired
	IServiceGroupMasterService serviceMasterService;

	@Autowired
	IComplaintTypeService<T> complaintTypeService;

	@Autowired
	IComplaintActionService actionService;

	@Autowired
	ServiceCodeMasterService iserviceCodeMasterService;

	/*
	 * Autowire declaration end here
	 */

	private static final long serialVersionUID = 1L;

	/*
	 * Properties declaration start here
	 */
	private BigDecimal countryId;
	private BigDecimal bankId;
	private BigDecimal serviceId;
	private BigDecimal complaintTypeId;
	private BigDecimal complaintActionId;
	private BigDecimal applicationCountryId;

	private String errorMessage;

	private BigDecimal communicationMethodId;

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	private BigDecimal complaintDestinationId;
	private BigDecimal complaintTakenById;

	private String complaintTypeCode;
	private BigDecimal complaintTakenByCode;
	private BigDecimal complaintActionCode;
	private BigDecimal complaintDestinationCode;
	private BigDecimal communicationMethodCode;
	private String complaintTakenBy;
	private String complaintActionBy;

	private Boolean booRenderDataTable = false;
	private Boolean booRenderSaveExit = false;
	private Boolean readOnly;
	private List<ComplaintMatrixDataTable> complaintMatrixList = new CopyOnWriteArrayList<ComplaintMatrixDataTable>();
	private List<ServiceMasterDesc> serviceMasterDescList = new ArrayList<ServiceMasterDesc>();
	// private List<BankMaster> bankMasterList = new ArrayList<BankMaster>();
	private List<BankApplicability> bankMasterList = new ArrayList<BankApplicability>();

	private List<ComplaintTypeDesc> complaintTypeList = new ArrayList<ComplaintTypeDesc>();
	private List<CommunicationMethodDesc> communicationMethodList = new ArrayList<CommunicationMethodDesc>();
	private List<ComplaintAssignedDesc> complaintAssignedList = new ArrayList<ComplaintAssignedDesc>();
	private List<ComplaintActionDesc> complaintActionList = new ArrayList<ComplaintActionDesc>();

	private Boolean renderDataTable = false;
	private Boolean renderSubmit = false;

	private List<ComplaintAssignedDesc> destinationList = new ArrayList<ComplaintAssignedDesc>();

	private Map<BigDecimal, String> serviceMasterMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapBank = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapComplaintTypeList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapCommunicationMethodList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapComplaintAssignedList = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> mapComplaintActionList = new HashMap<BigDecimal, String>();

	public IBankBranchDetailsService<T> getBankBranchDetailsService() {
		return bankBranchDetailsService;
	}

	public void setBankBranchDetailsService(
			IBankBranchDetailsService<T> bankBranchDetailsService) {
		this.bankBranchDetailsService = bankBranchDetailsService;
	}

	public BigDecimal getComplaintTakenById() {
		return complaintTakenById;
	}

	public void setComplaintTakenById(BigDecimal complaintTakenById) {
		this.complaintTakenById = complaintTakenById;
	}

	public String getComplaintTypeCode() {
		return complaintTypeCode;
	}

	public void setComplaintTypeCode(String complaintTypeCode) {
		this.complaintTypeCode = complaintTypeCode;
	}

	public BigDecimal getComplaintTakenByCode() {
		return complaintTakenByCode;
	}

	public void setComplaintTakenByCode(BigDecimal complaintTakenByCode) {
		this.complaintTakenByCode = complaintTakenByCode;
	}

	public BigDecimal getComplaintActionCode() {
		return complaintActionCode;
	}

	public void setComplaintActionCode(BigDecimal complaintActionCode) {
		this.complaintActionCode = complaintActionCode;
	}

	public BigDecimal getComplaintDestinationCode() {
		return complaintDestinationCode;
	}

	public void setComplaintDestinationCode(BigDecimal complaintDestinationCode) {
		this.complaintDestinationCode = complaintDestinationCode;
	}

	public BigDecimal getCommunicationMethodCode() {
		return communicationMethodCode;
	}

	public void setCommunicationMethodCode(BigDecimal communicationMethodCode) {
		this.communicationMethodCode = communicationMethodCode;
	}

	public BigDecimal getComplaintDestinationId() {
		return complaintDestinationId;
	}

	public void setComplaintDestinationId(BigDecimal complaintDestinationId) {
		this.complaintDestinationId = complaintDestinationId;
	}

	public List<BankApplicability> getBankMasterList() {
		// popBank();
		return bankMasterList;
	}

	public void setBankMasterList(List<BankApplicability> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

	public Map<BigDecimal, String> getMapBank() {
		return mapBank;
	}

	public void setMapBank(Map<BigDecimal, String> mapBank) {
		this.mapBank = mapBank;
	}

	// Create Session
	SessionStateManage sessionStateManage = new SessionStateManage();

	/*
	 * Properties getter and setter
	 */
	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getServiceId() {
		return serviceId;
	}

	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	public BigDecimal getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(BigDecimal complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}

	public List<ComplaintMatrixDataTable> getComplaintMatrixList() {
		return complaintMatrixList;
	}

	public BigDecimal getComplaintActionId() {
		return complaintActionId;
	}

	public void setComplaintActionId(BigDecimal complaintActionId) {
		this.complaintActionId = complaintActionId;
	}

	public BigDecimal getCommunicationMethodId() {
		return communicationMethodId;
	}

	public void setCommunicationMethodId(BigDecimal communicationMethodId) {
		this.communicationMethodId = communicationMethodId;
	}

	public Boolean getRenderDataTable() {
		return renderDataTable;
	}

	public void setRenderDataTable(Boolean renderDataTable) {
		this.renderDataTable = renderDataTable;
	}

	public Boolean getRenderSubmit() {
		return renderSubmit;
	}

	public void setRenderSubmit(Boolean renderSubmit) {
		this.renderSubmit = renderSubmit;
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

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	/*
	 * Properties declaration end here
	 */

	public String getComplaintTakenBy() {
		return complaintTakenBy;
	}

	public void setComplaintTakenBy(String complaintTakenBy) {
		this.complaintTakenBy = complaintTakenBy;
	}

	public String getComplaintActionBy() {
		return complaintActionBy;
	}

	public void setComplaintActionBy(String complaintActionBy) {
		this.complaintActionBy = complaintActionBy;
	}

	public List<CommunicationMethodDesc> getCommunicationMethodList() {

		communicationMethodList = complaintService
				.getCommunicationMethodList(sessionStateManage.getLanguageId());
		for (CommunicationMethodDesc CommMethodDesc : communicationMethodList) {
			mapCommunicationMethodList.put(CommMethodDesc
					.getCommunicationMethodId().getComMethodId(),
					CommMethodDesc.getFullDescription());
		}
		return communicationMethodList;
	}

	public void setCommunicationMethodList(
			List<CommunicationMethodDesc> communicationMethodList) {
		this.communicationMethodList = communicationMethodList;
	}

	public List<ComplaintTypeDesc> getComplaintTypeList() {

		complaintTypeList = complaintService
				.getComplaintTypeList(sessionStateManage.getLanguageId());
		for (ComplaintTypeDesc complaintType : complaintTypeList) {
			mapComplaintTypeList.put(complaintType.getComplaintType()
					.getComplaintTypeId(), complaintType.getFullDesc());
		}
		return complaintTypeList;
	}

	public void setComplaintTypeList(List<ComplaintTypeDesc> complaintTypeList) {
		this.complaintTypeList = complaintTypeList;
	}

	/*
	 * public List<ServiceMasterDesc> getServiceMasterDescList1() {
	 * 
	 * serviceMasterDescList =
	 * generalService.getAllServiceDesc(sessionStateManage.getLanguageId()); for
	 * (ServiceMasterDesc serviceMas : serviceMasterDescList) {
	 * List<BeneCountryService> list =
	 * generalService.getAllServiceDescByCountry(getCountryId());
	 * serviceMasterMap.put(serviceMas.getServiceMaster().getServiceId(),
	 * serviceMas.getLocalServiceDescription()); }
	 * 
	 * List<BeneCountryService> list =
	 * generalService.getAllServiceDescByCountry(getCountryId()); for
	 * (BeneCountryService beneCountryService : list) {
	 * serviceMasterMap.put(beneCountryService.getServiceId(),
	 * serviceMas.getLocalServiceDescription()); }
	 * 
	 * return serviceMasterDescList; }
	 */

	public List<ServiceMasterDesc> getServiceMasterDescList() {
		return serviceMasterDescList;
	}

	public void setServiceMasterDescList(
			List<ServiceMasterDesc> serviceMasterDescList) {
		this.serviceMasterDescList = serviceMasterDescList;
	}

	public List<CountryMasterDesc> getCountryNameList() {
		List<CountryMasterDesc> lstCountry = complaintService
				.getBusinessCountryList(new BigDecimal(sessionStateManage
						.isExists("languageId") ? sessionStateManage
						.getSessionValue("languageId") : "" + 1));
		return lstCountry;
	}

	public List<BankApplicability> getBankNameList() {
		// List<BankMaster> lstBank =
		// generalService.getBankList(getCountryId());

		List<BankApplicability> lstBank = complaintService
				.getCorrespondentBankList(getCountryId(),
						sessionStateManage.getCompanyId());

		return lstBank;
	}

	/*
	 * public void popBank() {
	 * 
	 * bankMasterList = new ArrayList<BankMaster>();
	 * 
	 * SessionStateManage sessionStateManage = new SessionStateManage();
	 * 
	 * bankMasterList.addAll(generalService.getAllBankList(new
	 * BigDecimal(sessionStateManage.isExists("languageId") ?
	 * sessionStateManage.getSessionValue("languageId") : "1"),getCountryId()));
	 * for (BankMaster bankmaster : bankMasterList) {
	 * mapBank.put(bankmaster.getBankId(), bankmaster.getBankFullName());
	 * 
	 * }
	 * 
	 * }
	 */

	public void popBank() {

		bankMasterList = new ArrayList<BankApplicability>();

		SessionStateManage sessionStateManage = new SessionStateManage();

		bankMasterList.addAll(complaintService.getCorrespondentBankList(
				getCountryId(), sessionStateManage.getCountryId()));

		for (BankApplicability bankApplicability : bankMasterList) {
			mapBank.put(bankApplicability.getBankMaster().getBankId(),
					bankApplicability.getBankMaster().getBankFullName());

		}

		popService();
	}

	public void popService() {

		List<BigDecimal> list1 = generalService
				.getAllServiceDescByCountry(getCountryId());
		serviceMasterDescList.clear();
		if (list1 != null) {
			for (BigDecimal serviceMas : list1) {

				List<ServiceMasterDesc> list2 = generalService.getServiceDesc(
						sessionStateManage.getLanguageId(), serviceMas);
				if (list2 != null) {
					for (ServiceMasterDesc serviceMasterDesc : list2) {
						serviceMasterDescList.add(serviceMasterDesc);

					}
				}
			}
		}
	}

	public List<ComplaintAssignedDesc> getComplaintAssignedList() {

		complaintAssignedList = complaintService
				.getComplaintAssignedList(sessionStateManage.getLanguageId());
		for (ComplaintAssignedDesc comAssignedDesc : complaintAssignedList) {
			mapComplaintAssignedList.put(comAssignedDesc.getComplaintAssigned()
					.getComplaintAssignedId(), comAssignedDesc
					.getFullDescription());
		}
		return complaintAssignedList;
	}

	public void setComplaintAssignedList(
			List<ComplaintAssignedDesc> complaintAssignedList) {
		this.complaintAssignedList = complaintAssignedList;
	}

	public List<ComplaintActionDesc> getComplaintActionList() {

		complaintActionList = complaintService
				.getComplaintActionList(sessionStateManage.getLanguageId());
		for (ComplaintActionDesc compAction : complaintActionList) {
			mapComplaintActionList.put(compAction.getComplaintAction()
					.getComplaintActionId(), compAction.getFullDescription());
		}
		return complaintActionList;
	}

	public void setComplaintActionList(
			List<ComplaintActionDesc> complaintActionList) {
		this.complaintActionList = complaintActionList;
	}

	public List<ComplaintAssignedDesc> getDestinationList() {

		List<ComplaintAssignedDesc> destinationList = complaintService
				.getComplaintAssignedList(sessionStateManage.getLanguageId());
		return destinationList;
	}

	public void setDestinationList(List<ComplaintAssignedDesc> destinationList) {
		this.destinationList = destinationList;
	}

	/*
	 * User Defined Methods start here
	 */

	public void addDataTable() {

		try {
			String serviceName = null;
			// complaintMatrixList.clear();
			setBooRenderClear(false);
			setBooEditButton(false);
			setBooSubmitPanel(false);
			ComplaintTypeMaster complaintType = null;
			ComplaintMatrixDataTable complaintDataTable = new ComplaintMatrixDataTable();

			complaintDataTable.setCountryId(getCountryId());
			complaintDataTable.setAppCountryId(sessionStateManage
					.getCountryId());
			complaintDataTable.setServiceId(getServiceId());
			complaintDataTable.setComplaintTypeId(getComplaintTypeId());
			complaintDataTable
					.setCommunicationMethod(getCommunicationMethodId());
			complaintDataTable.setBankId(getBankId());
			complaintDataTable.setComplaintActionId(getComplaintActionId());
			complaintDataTable.setComplaintTakenById(getComplaintTakenById());
			complaintDataTable
					.setComplaintDestinationId(getComplaintDestinationId());
			complaintDataTable.setComplaintMatrixPk(getComplaintMatrixPk());
			complaintDataTable.setRenderEditButton(true);

			complaintDataTable.setBankName(mapBank.get(getBankId()));
			complaintDataTable.setCountryName(generalService
					.getCountryName(getCountryId()));

			serviceName = iserviceCodeMasterService
					.LocalServiceDescriptionFromDB(
							new BigDecimal(
									sessionStateManage.isExists("languageId") ? sessionStateManage
											.getSessionValue("languageId")
											: "1"), getServiceId()).get(0)
					.getLocalServiceDescription();

			complaintDataTable.setServiceName(serviceName);

			complaintDataTable.setComplaintTypeCode(mapComplaintTypeList
					.get(getComplaintTypeId()));
			complaintDataTable.setComplaintTakenByCode(mapComplaintAssignedList
					.get(getComplaintTakenById()));
			complaintDataTable
					.setCommunicationMethodCode(mapCommunicationMethodList
							.get(getCommunicationMethodId()));
			complaintDataTable.setComplaintActionCode(mapComplaintActionList
					.get(getComplaintActionId()));
			complaintDataTable
					.setComplaintDestinationCode(mapComplaintAssignedList
							.get(getComplaintDestinationId()));

			complaintDataTable.setCreatedBy(getCreatedBy());
			complaintDataTable.setCreatedDate(getCreatedDate());
			if (getComplaintMatrixPk() != null) {
				if (getIfEditClicked().equals(true)
						&& getComplaintDtObj() != null) {
					if (complaintDataTable.getCountryId().equals(
							complaintDtObj.getCountryId())
							&& complaintDataTable.getBankId().equals(
									complaintDtObj.getBankId())
							&& complaintDataTable.getServiceId().equals(
									complaintDtObj.getServiceId())
							&& complaintDataTable.getComplaintTypeId().equals(
									complaintDtObj.getComplaintTypeId())
							&& complaintDataTable.getCommunicationMethod()
									.equals(complaintDtObj
											.getCommunicationMethod())
							&& complaintDataTable.getComplaintActionId()
									.equals(complaintDtObj
											.getComplaintActionId())
							&& complaintDataTable.getComplaintTakenById()
									.equals(complaintDtObj
											.getComplaintTakenById())
							&& complaintDataTable.getComplaintDestinationId()
									.equals(complaintDtObj
											.getComplaintDestinationId())) {
						complaintDataTable
								.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
						complaintDataTable.setIsActive(getIsActive());
						complaintDataTable.setModifiedBy(getModifiedBy());
						complaintDataTable.setModifiedDate(getModifiedDate());
						complaintDataTable.setApprovedBy(getApprovedBy());
						complaintDataTable.setApprovedDate(getApprovedDate());
						complaintDataTable.setRemarks(getRemarks());
						complaintDataTable.setIfEditClicked(true);
					} else {
						complaintDataTable
								.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						complaintDataTable.setIsActive(Constants.U);
						complaintDataTable.setModifiedBy(sessionStateManage
								.getUserName());
						complaintDataTable.setModifiedDate(new Date());
						complaintDataTable.setApprovedBy(null);
						complaintDataTable.setApprovedDate(null);
						complaintDataTable.setRemarks(null);
						complaintDataTable.setIfEditClicked(true);
					}
				}
			} else {
				complaintDataTable
						.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				complaintDataTable.setIsActive(Constants.U);
				complaintDataTable.setCreatedBy(sessionStateManage
						.getUserName());
				complaintDataTable.setCreatedDate(new Date());
				complaintDataTable.setIfEditClicked(true);
			}
			complaintMatrixList.add(complaintDataTable);
			
			if (getComplaintMatrixPk() == null) {
				complaintMatrixNewDtList.add(complaintDataTable);
			}
			setBooRenderDataTable(true);
			setBooRenderSaveExit(true);
			setBooAdd(true);
			setBooApprove(false);
			setBooReadOnly(false);
			clear();

		} catch (NullPointerException ne) {
			LOG.info("Method Name::addDataTable" + ne.getMessage());
			setErrorMessage("Method Name::addToDataTable");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			LOG.info("Method Name::addDataTable" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	public void checkDuplicateTakenByAndAction() {

		if (complaintMatrixList.size() > 0) {
			for (ComplaintMatrixDataTable dataTable : complaintMatrixList) {
				if (dataTable.getComplaintTakenById().equals(
						getComplaintTakenById())
						&& dataTable.getComplaintActionId().equals(
								getComplaintActionId())) {
					// clear();
					setComplaintTakenBy(dataTable.getComplaintTakenByCode());
					setComplaintActionBy(dataTable.getComplaintActionCode());
					RequestContext.getCurrentInstance().execute(
							"datatable.show();");
					return;
				}
			}
		}

	}

	public void duplicateCheckingComplaintMatrix() {
		
		try{

		List<ComplaintMatrix> list = complaintService.getComplaintMatrixList(
				sessionStateManage.getCountryId(), getBankId(), getServiceId(),
				getCountryId(), getComplaintTypeId());

		for (ComplaintMatrix complaintMatrix : list) {

			if (complaintMatrix.getcomplaintAssigned().getComplaintAssignedId() == getComplaintTakenById()
					&& complaintMatrix.getComplaintAction()
							.getComplaintActionId() == getComplaintActionId()) {
				setComplaintTakenBy(complaintMatrix.getcomplaintAssigned()
						.getComplaintAssignedCode());
				setComplaintActionBy(complaintMatrix.getComplaintAction()
						.getComplaintActionCode());
				RequestContext.getCurrentInstance()
						.execute("datatable.show();");
				break;
			}

		}

		/*
		 * if(complaintMatrixList.size()>0){ for (ComplaintMatrixDataTable
		 * dataTable : complaintMatrixList) {
		 * if(dataTable.getComplaintTakenById(
		 * ).equals(getComplaintTakenById())&&
		 * dataTable.getComplaintActionId().equals(getComplaintActionId())){
		 * //clear(); setComplaintTakenBy(dataTable.getComplaintTakenByCode());
		 * setComplaintActionBy(dataTable.getComplaintActionCode());
		 * RequestContext.getCurrentInstance().execute("datatable.show();");
		 * return; } } }
		 */
		addDataTable();
		
		}catch (NullPointerException ne) {
			LOG.info("Method Name::duplicateCheckingComplaintMatrix()" + ne.getMessage());
			setErrorMessage("Method Name::saveComplaintMatrix");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			LOG.info("Method Name::duplicateCheckingComplaintMatrix()"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void saveComplaintMatrix() {
		ComplaintMatrix complaintMatrix = null;
		CommunicationMethod communicationMethod = null;
		CountryMaster countryMaster = null;
		CountryMaster countryMaster2 = null;
		BankMaster bankMaster = null;
		ServiceMaster serviceMaster = null;
		ComplaintTypeMaster complaintType = null;
		ComplaintAction complaintAction = null;
		ComplaintAssigned complaintAssigned = null;

		try {

			if (complaintMatrixList.size() > 0) {
				for (ComplaintMatrixDataTable complaintMatrxDataObj : complaintMatrixList) {
					if (complaintMatrxDataObj.getIfEditClicked().equals(true)) {
						complaintMatrix = new ComplaintMatrix();

						complaintMatrix
								.setComplaintMatrixId(complaintMatrxDataObj
										.getComplaintMatrixPk());
						communicationMethod = new CommunicationMethod();
						communicationMethod
								.setComMethodId(complaintMatrxDataObj
										.getCommunicationMethod());
						complaintMatrix
								.setCommunicationMethod(communicationMethod);

						countryMaster = new CountryMaster();
						countryMaster.setCountryId(complaintMatrxDataObj
								.getCountryId());
						complaintMatrix.setFsCountry(countryMaster);
						// complaintMatrix.setApplicationCountry(countryMaster);

						countryMaster2 = new CountryMaster();
						countryMaster2.setCountryId(complaintMatrxDataObj
								.getAppCountryId());
						complaintMatrix.setApplicationCountry(countryMaster2);

						bankMaster = new BankMaster();
						bankMaster.setBankId(complaintMatrxDataObj.getBankId());
						complaintMatrix.setBankMaster(bankMaster);

						serviceMaster = new ServiceMaster();
						serviceMaster.setServiceId(complaintMatrxDataObj
								.getServiceId());
						complaintMatrix.setServiceMaster(serviceMaster);

						complaintType = new ComplaintTypeMaster();
						complaintType.setComplaintTypeId(complaintMatrxDataObj
								.getComplaintTypeId());
						complaintMatrix.setComplaintTypeMaster(complaintType);

						complaintAssigned = new ComplaintAssigned();
						complaintAssigned
								.setComplaintAssignedId(complaintMatrxDataObj
										.getComplaintTakenById());
						complaintMatrix.setComplaintAssigned(complaintAssigned);
						complaintMatrix
								.setComplaintDestinationId(complaintAssigned);

						complaintAction = new ComplaintAction();
						complaintAction
								.setComplaintActionId(complaintMatrxDataObj
										.getComplaintActionId());
						complaintMatrix.setComplaintAction(complaintAction);
						// complaintMatrix.setComplaintDestinationId(complaintMatrxDataObj.getComplaintDestinationId());

						complaintMatrix.setCreatedBy(complaintMatrxDataObj
								.getCreatedBy());
						complaintMatrix.setCreatedDate(complaintMatrxDataObj
								.getCreatedDate());
						complaintMatrix.setIsActive(complaintMatrxDataObj
								.getIsActive());

						complaintMatrix.setModifiedBy(complaintMatrxDataObj
								.getModifiedBy());
						complaintMatrix.setModifiedDate(complaintMatrxDataObj
								.getModifiedDate());

						complaintMatrix.setApprovedBy(complaintMatrxDataObj
								.getApprovedBy());
						complaintMatrix.setApprovedDate(complaintMatrxDataObj
								.getApprovedDate());
						complaintMatrix
								.setRemarks(complaintMatrix.getRemarks());

						complaintService.saveComplaintMatrix(complaintMatrix);

					}
				}
				RequestContext.getCurrentInstance().execute("complete.show();");
				setBooRenderDataTable(true);
				setBooRenderSaveExit(true);
				setBooAdd(true);
				setBooApprove(false);
				setBooReadOnly(false);
				complaintMatrixNewDtList.clear();
				complaintMatrixList.clear();
				clear();
				return;
			}
		} catch (NullPointerException ne) {
			LOG.info("Method Name::saveComplaintMatrix" + ne.getMessage());
			setErrorMessage("Method Name::saveComplaintMatrix");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			LOG.info("Method Name::saveComplaintMatrix"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void clickOnOKSave() {
		setBooRenderDataTable(false);
		setBooRenderSaveExit(false);
		setBooAdd(true);
		setBooApprove(false);
		setBooReadOnly(false);
		complaintMatrixList.clear();
		complaintMatrixNewDtList.clear();
		clear1();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/complaintmatrix.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fetchComplaintMatrix() {

		try {

			List<ComplaintMatrix> matrixList = complaintService
					.getComplaintMatrixList(getCountryId(), getBankId(),
							getServiceId(), sessionStateManage.getCountryId(),
							getComplaintTypeId());
			// List<ComplaintMatrix>
			// complaintMatrixLt=complaintService.getCheckForDbForRecord(getCountryId(),getBankId(),getServiceId(),getComplaintTypeId(),getComplaintTakenById(),getComplaintActionId(),sessionStateManage.getCountryId());
			if (matrixList.size() > 0) {
				clear1();
				RequestContext.getCurrentInstance().execute(
						"alReadyExist.show();");
				return;
			}

			/*
			 * if (matrixList.size() > 0) {
			 * setCountryId(matrixList.get(0).getFsCountry().getCountryId());
			 * setBankId(matrixList.get(0).getBankMaster().getBankId());
			 * setServiceId
			 * (matrixList.get(0).getServiceMaster().getServiceId());
			 * setCommunicationMethodId
			 * (matrixList.get(0).getCommunicationMethod().getComMethodId());
			 * setComplaintTypeId
			 * (matrixList.get(0).getComplaintTypeMaster().getComplaintTypeId
			 * ());
			 * setComplaintTakenById(matrixList.get(0).getcomplaintAssigned(
			 * ).getComplaintAssignedId());
			 * setComplaintActionId(matrixList.get(0
			 * ).getComplaintAction().getComplaintActionId());
			 * setComplaintDestinationId
			 * (matrixList.get(0).getComplaintDestinationId
			 * ().getComplaintAssignedId());
			 * setComplaintMatrixPk(matrixList.get(0).getComplaintMatrixId());
			 * setCreatedBy(matrixList.get(0).getCreatedBy());
			 * setCreatedDate(matrixList.get(0).getCreatedDate());
			 * setModifiedBy(matrixList.get(0).getModifiedBy());
			 * setModifiedDate(matrixList.get(0).getModifiedDate());
			 * setApprovedBy(matrixList.get(0).getApprovedBy());
			 * setApprovedDate(matrixList.get(0).getApprovedDate());
			 * setIsActive(matrixList.get(0).getIsActive());
			 * setRemarks(matrixList.get(0).getRemarks());
			 * if(matrixList.get(0).getIsActive
			 * ().equalsIgnoreCase(Constants.Yes)){
			 * setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE); }else
			 * if
			 * (matrixList.get(0).getIsActive().equalsIgnoreCase(Constants.D)) {
			 * setDynamicLabelForActivateDeactivate(Constants.ACTIVATE); }else
			 * if
			 * (matrixList.get(0).getIsActive().equalsIgnoreCase(Constants.U)&&
			 * matrixList
			 * .get(0).getModifiedBy()==null&&matrixList.get(0).getModifiedDate
			 * ()
			 * ==null&&matrixList.get(0).getApprovedBy()==null&&matrixList.get(
			 * 0).getApprovedDate()==null&&matrixList.get(0).getRemarks()==null)
			 * { setDynamicLabelForActivateDeactivate(Constants.DELETE); }else{
			 * setDynamicLabelForActivateDeactivate
			 * (Constants.PENDING_FOR_APPROVE); } }
			 */

		} catch (NullPointerException ne) {
			LOG.info("Method Name::fetchComplaintMatrix" + ne.getMessage());
			setErrorMessage("Method Name::fetchComplaintMatrix");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			LOG.info("Method Name::fetchComplaintMatrix"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	public void editComplaintMatrix(ComplaintMatrixDataTable dataTable) {

		try {
			// setBooRenderSaveExit(false); // When click edit option rendered
			// off save and exit
			setComplaintDtObj(dataTable);
			// setApplicationCountryId(dataTable.getAppCountryId());
			setAppCountryId(dataTable.getAppCountryId());
			setCountryId(dataTable.getCountryId());
			popBank();
			setBankId(dataTable.getBankId());
			setServiceId(dataTable.getServiceId());
			setCommunicationMethodId(dataTable.getCommunicationMethod());
			setComplaintTypeId(dataTable.getComplaintTypeId());
			setComplaintTakenById(dataTable.getComplaintTakenById());
			setComplaintActionId(dataTable.getComplaintActionId());
			setComplaintDestinationId(dataTable.getComplaintDestinationId());
			setComplaintMatrixPk(dataTable.getComplaintMatrixPk());
			setCreatedBy(dataTable.getCreatedBy());
			setCreatedDate(dataTable.getCreatedDate());
			setModifiedBy(dataTable.getModifiedBy());
			setModifiedDate(dataTable.getModifiedDate());
			setIsActive(dataTable.getIsActive());
			setRenderEditButton(dataTable.getRenderEditButton());
			setDynamicLabelForActivateDeactivate(dataTable
					.getDynamicLabelForActivateDeactivate());
			setIfEditClicked(true);
			setRenderEditButton(dataTable.getRenderEditButton());
			setApprovedBy(dataTable.getApprovedBy());
			setApprovedDate(dataTable.getApprovedDate());
			setRemarks(dataTable.getRemarks());

			complaintMatrixList.remove(dataTable);
			if (complaintMatrixList.size() == 0) {
				setBooRenderDataTable(false);
				setBooRenderSaveExit(false);
				setBooAdd(true);
				setBooApprove(false);
				setBooReadOnly(false);
			} else {
				setBooRenderClear(true);
				setBooSubmitPanel(true);
				setBooEditButton(true);
				setBooAdd(true);
				setBooApprove(false);
				setBooReadOnly(false);
			}

		} catch (NullPointerException ne) {
			LOG.info("Method Name::editComplaintMatrix" + ne.getMessage());
			setErrorMessage("Method Name::editComplaintMatrix");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			LOG.info("Method Name::editComplaintMatrix"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void complaintMatrixNavigation() {

		try {
			setBooRenderDataTable(false);
			setBooRenderSaveExit(false);
			setBooRenderClear(false);
			setBooSubmitPanel(false);
			setBooEditButton(false);
			setBooAdd(true);
			setBooApprove(false);
			setBooReadOnly(false);
			setBooRenderClear(false);
			complaintMatrixList.clear();
			complaintMatrixNewDtList.clear();
			clear();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "complaintmatrix.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/complaintmatrix.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exit() throws IOException {
		complaintMatrixList.clear();
		complaintMatrixNewDtList.clear();
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		}
	}

	public void clear() {
		setCountryId(null);
		setBankId(null);
		setServiceId(null);
		setCommunicationMethodId(null);
		setComplaintTypeId(null);
		setComplaintActionId(null);
		setComplaintDestinationId(null);
		setComplaintTakenById(null);
		setComplaintMatrixPk(null);

	}

	public void clear1() {
		setComplaintActionId(null);
		setComplaintTakenById(null);
	}

	/*
	 * User defined methods ends here
	 */

	/* Started koti Varible Declaration 05/08/15 */
	@Autowired
	ServiceCodeMasterService serviceCodeMasterService;
	private String serviceName;
	private BigDecimal appCountryId;
	private BigDecimal complaintMatrixPk;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private String remarks;

	private String dynamicLabelForActivateDeactivate;
	private Boolean booRenderClear = false;
	private Boolean ifEditClicked = false;
	private Boolean renderEditButton = false;
	private Boolean booSubmitPanel = false;
	private Boolean booEditButton = false;
	private Boolean booAdd = false;
	private Boolean booReadOnly = false;
	private Boolean booApprove = false;
	private List<ComplaintMatrixDataTable> complaintMatrixNewDtList = new CopyOnWriteArrayList<>();
	private ComplaintMatrixDataTable complaintDtObj = null;

	public ComplaintMatrixDataTable getComplaintDtObj() {
		return complaintDtObj;
	}

	public void setComplaintDtObj(ComplaintMatrixDataTable complaintDtObj) {
		this.complaintDtObj = complaintDtObj;
	}

	public Boolean getBooAdd() {
		return booAdd;
	}

	public void setBooAdd(Boolean booAdd) {
		this.booAdd = booAdd;
	}

	public Boolean getBooReadOnly() {
		return booReadOnly;
	}

	public void setBooReadOnly(Boolean booReadOnly) {
		this.booReadOnly = booReadOnly;
	}

	public Boolean getBooApprove() {
		return booApprove;
	}

	public void setBooApprove(Boolean booApprove) {
		this.booApprove = booApprove;
	}

	public List<ComplaintMatrixDataTable> getComplaintMatrixNewDtList() {
		return complaintMatrixNewDtList;
	}

	public void setComplaintMatrixNewDtList(
			List<ComplaintMatrixDataTable> complaintMatrixNewDtList) {
		this.complaintMatrixNewDtList = complaintMatrixNewDtList;
	}

	public Boolean getBooEditButton() {
		return booEditButton;
	}

	public void setBooEditButton(Boolean booEditButton) {
		this.booEditButton = booEditButton;
	}

	public Boolean getBooSubmitPanel() {
		return booSubmitPanel;
	}

	public void setBooSubmitPanel(Boolean booSubmitPanel) {
		this.booSubmitPanel = booSubmitPanel;
	}

	public Boolean getRenderEditButton() {
		return renderEditButton;
	}

	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}

	public Boolean getIfEditClicked() {
		return ifEditClicked;
	}

	public void setIfEditClicked(Boolean ifEditClicked) {
		this.ifEditClicked = ifEditClicked;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getBooRenderClear() {
		return booRenderClear;
	}

	public void setBooRenderClear(Boolean booRenderClear) {
		this.booRenderClear = booRenderClear;
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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public BigDecimal getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
	}

	public BigDecimal getComplaintMatrixPk() {
		return complaintMatrixPk;
	}

	public void setComplaintMatrixPk(BigDecimal complaintMatrixPk) {
		this.complaintMatrixPk = complaintMatrixPk;
	}

	public void clearService() {
		/*
		 * setServiceId(null); setServiceName(null); setComplaintTypeId(null);
		 * setComplaintTypeCode(null); setComplaintActionId(null);
		 * setComplaintActionCode(null); setCommunicationMethodId(null);
		 * setCommunicationMethodCode(null); setComplaintDestinationId(null);
		 * setComplaintDestinationCode(null); setComplaintTakenById(null);
		 * setComplaintTakenByCode(null); setComplaintMatrixPk(null);
		 */

	}

	public void clearComplaintType() {
		/*
		 * setComplaintTypeId(null); setComplaintTypeCode(null);
		 * setComplaintActionId(null); setComplaintActionCode(null);
		 * setCommunicationMethodId(null); setCommunicationMethodCode(null);
		 * setComplaintDestinationId(null); setComplaintDestinationCode(null);
		 * setComplaintTakenById(null); setComplaintTakenByCode(null);
		 * setComplaintMatrixPk(null);
		 */
	}

	public void clearForCompalintId() {

		// setComplaintMatrixPk(null);
	}

	private List<ComplaintMatrixDTO> complaintMatrixDTOList = new CopyOnWriteArrayList<ComplaintMatrixDTO>();

	public List<ComplaintMatrixDTO> getComplaintMatrixDTOList() {
		return complaintMatrixDTOList;
	}

	public void setComplaintMatrixDTOList(
			List<ComplaintMatrixDTO> complaintMatrixDTOList) {
		this.complaintMatrixDTOList = complaintMatrixDTOList;
	}

	// View Activate/DeActivate and HardDelete function Started
	public void viewAllComplaintMatrix() {

		try {
			complaintMatrixList.clear();
			// setBooRenderSaveExit(false);
			List<ComplaintMatrixDTO> lstComplaintMatrix = complaintService
					.getViewAllRecorsFromDb();
			if (lstComplaintMatrix.size() > 0) {
				setBooRenderDataTable(true);
				setBooRenderSaveExit(true);
				setBooAdd(true);
				setBooApprove(false);
				setBooReadOnly(false);

			} else {
				setBooRenderDataTable(false);
				setBooRenderSaveExit(false);
				setBooAdd(true);
				setBooApprove(false);
				setBooReadOnly(false);
				RequestContext.getCurrentInstance().execute("noRecord.show();");
				return;
			}
			for (ComplaintMatrixDTO complaintMatrixDTO : lstComplaintMatrix) {
				ComplaintMatrixDataTable complaintMatrixDTObj = new ComplaintMatrixDataTable();
				complaintMatrixDTObj.setAppCountryId(complaintMatrixDTO
						.getAppCountryId());
				complaintMatrixDTObj.setCountryId(complaintMatrixDTO
						.getCountryId());
				complaintMatrixDTObj.setServiceId(complaintMatrixDTO
						.getServiceId());
				complaintMatrixDTObj.setComplaintTypeId(complaintMatrixDTO
						.getComplaintTypeId());
				complaintMatrixDTObj.setCommunicationMethod(complaintMatrixDTO
						.getCommunicationMethodId());
				complaintMatrixDTObj.setBankId(complaintMatrixDTO.getBankId());
				complaintMatrixDTObj.setComplaintActionId(complaintMatrixDTO
						.getComplaintActionId());
				complaintMatrixDTObj.setComplaintTakenById(complaintMatrixDTO
						.getComplaintTakenById());
				complaintMatrixDTObj
						.setComplaintDestinationId(complaintMatrixDTO
								.getComplaintDestionId());
				complaintMatrixDTObj.setComplaintMatrixPk(complaintMatrixDTO
						.getCompalintMatrixPk());

				complaintMatrixDTObj.setBankName(generalService
						.getBankName(complaintMatrixDTO.getBankId()));
				complaintMatrixDTObj.setCountryName(generalService
						.getCountryName(complaintMatrixDTO.getCountryId()));
				complaintMatrixDTObj
						.setServiceName(iserviceCodeMasterService
								.LocalServiceDescriptionFromDB(
										new BigDecimal(
												sessionStateManage
														.isExists("languageId") ? sessionStateManage
														.getSessionValue("languageId")
														: "1"),
										complaintMatrixDTO.getServiceId())
								.get(0).getLocalServiceDescription());
				// complaintMatrixDTObj.setServiceName(serviceMasterMap.get(getServiceId()));
				complaintMatrixDTObj.setComplaintTypeCode(complaintService
						.getComplaintTypeDescDb(
								sessionStateManage.getLanguageId(),
								complaintMatrixDTO.getComplaintTypeId()).get(0)
						.getFullDesc());
				complaintMatrixDTObj.setComplaintTakenByCode(complaintService
						.getComplaintAssignedDesc(
								sessionStateManage.getLanguageId(),
								complaintMatrixDTO.getComplaintTakenById())
						.get(0).getFullDescription());
				complaintMatrixDTObj
						.setCommunicationMethodCode(complaintService
								.getCommunicationMethodDescDb(
										sessionStateManage.getLanguageId(),
										complaintMatrixDTO
												.getCommunicationMethodId())
								.get(0).getFullDescription());
				complaintMatrixDTObj.setComplaintActionCode(complaintService
						.getCompalintDescDb(sessionStateManage.getLanguageId(),
								complaintMatrixDTO.getComplaintActionId())
						.get(0).getFullDescription());
				complaintMatrixDTObj
						.setComplaintDestinationCode(complaintService
								.getComplaintAssignedDesc(
										sessionStateManage.getLanguageId(),
										complaintMatrixDTO
												.getComplaintDestionId())
								.get(0).getFullDescription());
				complaintMatrixDTObj.setCreatedBy(complaintMatrixDTO
						.getCreatedBy());
				complaintMatrixDTObj.setCreatedDate(complaintMatrixDTO
						.getCreatedDate());
				complaintMatrixDTObj.setModifiedBy(complaintMatrixDTO
						.getModifiedBy());
				complaintMatrixDTObj.setModifiedDate(complaintMatrixDTO
						.getModifiedDate());
				complaintMatrixDTObj.setApprovedBy(complaintMatrixDTO
						.getApprovedBy());
				complaintMatrixDTObj.setApprovedDate(complaintMatrixDTO
						.getApprovedDate());
				complaintMatrixDTObj
						.setRemarks(complaintMatrixDTO.getRemarks());
				complaintMatrixDTObj.setIsActive(complaintMatrixDTO
						.getIsActive());
				complaintMatrixDTObj.setRenderEditButton(true);
				complaintMatrixDTObj.setBooEditButton(false);
				if (complaintMatrixDTObj.getIsActive().equalsIgnoreCase(
						Constants.Yes)) {
					complaintMatrixDTObj
							.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				} else if (complaintMatrixDTObj.getIsActive().equalsIgnoreCase(
						Constants.D)) {
					complaintMatrixDTObj
							.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				} else if (complaintMatrixDTObj.getIsActive().equalsIgnoreCase(
						Constants.U)
						&& complaintMatrixDTObj.getModifiedBy() == null
						&& complaintMatrixDTObj.getModifiedDate() == null
						&& complaintMatrixDTObj.getApprovedBy() == null
						&& complaintMatrixDTObj.getApprovedDate() == null
						&& complaintMatrixDTObj.getRemarks() == null) {
					complaintMatrixDTObj
							.setDynamicLabelForActivateDeactivate(Constants.DELETE);
				} else {
					complaintMatrixDTObj
							.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				}
				complaintMatrixList.add(complaintMatrixDTObj);
			}
			complaintMatrixList.addAll(complaintMatrixNewDtList);
			clear();
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../complaint/complaintmatrix.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (NullPointerException ne) {
			LOG.info("Method Name::viewAllComplaintMatrix" + ne.getMessage());
			setErrorMessage("Method Name::viewAllComplaintMatrix");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			LOG.info("Method Name::viewAllComplaintMatrix"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void checkStatus(ComplaintMatrixDataTable complaintMatrixDtOBJ) {

		try {

			if (complaintMatrixDtOBJ.getDynamicLabelForActivateDeactivate()
					.equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
				RequestContext.getCurrentInstance().execute("pending.show();");
				return;
			} else if (complaintMatrixDtOBJ
					.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(
							Constants.REMOVE)) {
				complaintMatrixList.remove(complaintMatrixDtOBJ);
				complaintMatrixNewDtList.remove(complaintMatrixDtOBJ);
			} else if (complaintMatrixDtOBJ
					.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(
							Constants.DEACTIVATE)) {
				complaintMatrixDtOBJ.setRemarksCheck(true);
				setApprovedBy(complaintMatrixDtOBJ.getApprovedBy());
				setApprovedDate(complaintMatrixDtOBJ.getApprovedDate());
				RequestContext.getCurrentInstance().execute("remarks.show();");
			} else if (complaintMatrixDtOBJ
					.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(
							Constants.ACTIVATE)) {
				complaintMatrixDtOBJ.setActivateRecordCheck(true);
				RequestContext.getCurrentInstance().execute(
						"activateRecord.show();");
				return;
			} else if (complaintMatrixDtOBJ
					.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(
							Constants.DELETE)
					&& complaintMatrixDtOBJ.getModifiedBy() == null
					&& complaintMatrixDtOBJ.getModifiedDate() == null
					&& complaintMatrixDtOBJ.getApprovedBy() == null
					&& complaintMatrixDtOBJ.getApprovedDate() == null
					&& complaintMatrixDtOBJ.getRemarks() == null) {
				complaintMatrixDtOBJ.setPermanetDeleteCheck(true);
				RequestContext.getCurrentInstance().execute(
						"permanentDelete.show();");
				return;
			}
			if (complaintMatrixList.size() == 0) {
				setBooRenderDataTable(false);
				setBooRenderSaveExit(false);
				setBooAdd(true);
				setBooApprove(false);
				setBooReadOnly(false);
			}

		} catch (NullPointerException ne) {
			LOG.info("Method Name::checkStatus" + ne.getMessage());
			setErrorMessage("Method Name::checkStatus");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			LOG.info("Method Name::checkStatus" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void clickOkRemarks() {
		if (getRemarks() != null && !getRemarks().equals("")) {
			for (ComplaintMatrixDataTable compalMatrixDataTable : complaintMatrixList) {
				if (compalMatrixDataTable.getRemarksCheck() != null) {
					if (compalMatrixDataTable.getRemarksCheck().equals(true)) {
						compalMatrixDataTable.setRemarks(getRemarks());
						UpdateComplaintMatrixCode(compalMatrixDataTable);
						clear();
						viewAllComplaintMatrix();
						setRemarks(null);
					}
				}
			}

		} else {
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		}
	}

	public void cancelRemarks() {
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/complaintmatrix.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void UpdateComplaintMatrixCode(
			ComplaintMatrixDataTable complaintMatrxDataObj) {
		ComplaintMatrix complaintMatrix = null;
		CommunicationMethod communicationMethod = null;
		CountryMaster countryMaster = null;
		CountryMaster countryMaster2 = null;
		BankMaster bankMaster = null;
		ServiceMaster serviceMaster = null;
		ComplaintTypeMaster complaintType = null;
		ComplaintAction complaintAction = null;
		ComplaintAssigned complaintAssigned = null;

		try {

			complaintMatrix = new ComplaintMatrix();

			complaintMatrix.setComplaintMatrixId(complaintMatrxDataObj
					.getComplaintMatrixPk());
			communicationMethod = new CommunicationMethod();
			communicationMethod.setComMethodId(complaintMatrxDataObj
					.getCommunicationMethod());
			complaintMatrix.setCommunicationMethod(communicationMethod);

			countryMaster = new CountryMaster();
			countryMaster.setCountryId(complaintMatrxDataObj.getCountryId());
			complaintMatrix.setFsCountry(countryMaster);
			// complaintMatrix.setApplicationCountry(countryMaster);

			countryMaster2 = new CountryMaster();
			countryMaster2
					.setCountryId(complaintMatrxDataObj.getAppCountryId());
			complaintMatrix.setApplicationCountry(countryMaster2);

			bankMaster = new BankMaster();
			bankMaster.setBankId(complaintMatrxDataObj.getBankId());
			complaintMatrix.setBankMaster(bankMaster);

			serviceMaster = new ServiceMaster();
			serviceMaster.setServiceId(complaintMatrxDataObj.getServiceId());
			complaintMatrix.setServiceMaster(serviceMaster);

			complaintType = new ComplaintTypeMaster();
			complaintType.setComplaintTypeId(complaintMatrxDataObj
					.getComplaintTypeId());
			complaintMatrix.setComplaintTypeMaster(complaintType);

			complaintAssigned = new ComplaintAssigned();
			complaintAssigned.setComplaintAssignedId(complaintMatrxDataObj
					.getComplaintTakenById());
			complaintMatrix.setComplaintAssigned(complaintAssigned);
			complaintMatrix.setComplaintDestinationId(complaintAssigned);

			complaintAction = new ComplaintAction();
			complaintAction.setComplaintActionId(complaintMatrxDataObj
					.getComplaintActionId());
			complaintMatrix.setComplaintAction(complaintAction);
			// complaintMatrix.setComplaintDestinationId(complaintMatrxDataObj.getComplaintDestinationId());

			complaintMatrix.setCreatedBy(complaintMatrxDataObj.getCreatedBy());
			complaintMatrix.setCreatedDate(complaintMatrxDataObj
					.getCreatedDate());
			complaintMatrix.setIsActive(Constants.D);

			complaintMatrix.setModifiedBy(sessionStateManage.getUserName());
			complaintMatrix.setModifiedDate(new Date());
			complaintMatrix.setApprovedBy(null);
			complaintMatrix.setApprovedDate(null);
			complaintMatrix.setRemarks(getRemarks());

			complaintService.saveComplaintMatrix(complaintMatrix);

		} catch (NullPointerException ne) {
			LOG.info("Method Name::checkStatus" + ne.getMessage());
			setErrorMessage("Method Name::checkStatus");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			LOG.info("Method Name::checkStatus" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void activateRecord() {
		if (complaintMatrixList.size() > 0) {
			for (ComplaintMatrixDataTable complaintMatrixDtObj : complaintMatrixList) {
				if (complaintMatrixDtObj.getActivateRecordCheck() != null) {
					if (complaintMatrixDtObj.getActivateRecordCheck().equals(
							true)) {
						conformToDeActivteCompliantMatrix(complaintMatrixDtObj);
					}
				}
			}
		}
	}

	public void conformToDeActivteCompliantMatrix(
			ComplaintMatrixDataTable complaintMatrixDT) {
		complaintService.deActivateRecord(
				complaintMatrixDT.getComplaintMatrixPk(),
				sessionStateManage.getUserName());
		viewAllComplaintMatrix();
	}

	public void complaintMatrixConfirmPermanentDelete() {
		if (complaintMatrixList.size() > 0) {
			for (ComplaintMatrixDataTable complaintMatrixDtObj : complaintMatrixList) {
				if (complaintMatrixDtObj.getPermanetDeleteCheck() != null) {
					if (complaintMatrixDtObj.getPermanetDeleteCheck().equals(
							true)) {
						deleteRecordCompliantMatrix(complaintMatrixDtObj);
						complaintMatrixList.remove(complaintMatrixDtObj);
					}
				}
			}
		}
	}

	public void deleteRecordCompliantMatrix(
			ComplaintMatrixDataTable complaintMatrixDT) {
		complaintService.deleteRecordPermentely(complaintMatrixDT
				.getComplaintMatrixPk());
	}

	// View Activate/DeActivate and HardDelete function Started

	// Approval Started

	public void complaintMatrixApprovalNavigation() {
		setBooRenderSaveExit(false);
		setBooAdd(false);
		setBooApprove(false);
		setBooReadOnly(true);
		setBooRenderDataTable(true);
		clear();
		complaintMatrixList.clear();
		complaintMatrixNewDtList.clear();
		fetchRecordforComplaintMatrixApproval();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "ComplaintMatrixApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/ComplaintMatrixApproval.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fetchRecordforComplaintMatrixApproval() {

		try {
			complaintMatrixList.clear();
			complaintMatrixNewDtList.clear();
			List<ComplaintMatrixDTO> lstComplaintMatrixDTO = complaintService
					.displayAllComplaintMatrixMasterForApproval();
			if (lstComplaintMatrixDTO.size() > 0) {
				for (ComplaintMatrixDTO complaintMatrixDTO : lstComplaintMatrixDTO) {
					ComplaintMatrixDataTable complaintMatrixDTObj = new ComplaintMatrixDataTable();
					complaintMatrixDTObj.setAppCountryId(complaintMatrixDTO
							.getAppCountryId());
					complaintMatrixDTObj.setCountryId(complaintMatrixDTO
							.getCountryId());
					complaintMatrixDTObj.setServiceId(complaintMatrixDTO
							.getServiceId());
					complaintMatrixDTObj.setComplaintTypeId(complaintMatrixDTO
							.getComplaintTypeId());
					complaintMatrixDTObj
							.setCommunicationMethod(complaintMatrixDTO
									.getCommunicationMethodId());
					complaintMatrixDTObj.setBankId(complaintMatrixDTO
							.getBankId());
					complaintMatrixDTObj
							.setComplaintActionId(complaintMatrixDTO
									.getComplaintActionId());
					complaintMatrixDTObj
							.setComplaintTakenById(complaintMatrixDTO
									.getComplaintTakenById());
					complaintMatrixDTObj
							.setComplaintDestinationId(complaintMatrixDTO
									.getComplaintDestionId());
					complaintMatrixDTObj
							.setComplaintMatrixPk(complaintMatrixDTO
									.getCompalintMatrixPk());

					complaintMatrixDTObj.setBankName(generalService
							.getBankName(complaintMatrixDTO.getBankId()));
					complaintMatrixDTObj.setCountryName(generalService
							.getCountryName(complaintMatrixDTO.getCountryId()));
					complaintMatrixDTObj
							.setServiceName(iserviceCodeMasterService
									.LocalServiceDescriptionFromDB(
											new BigDecimal(
													sessionStateManage
															.isExists("languageId") ? sessionStateManage
															.getSessionValue("languageId")
															: "1"),
											complaintMatrixDTO.getServiceId())
									.get(0).getLocalServiceDescription());
					// complaintMatrixDTObj.setServiceName(serviceMasterMap.get(getServiceId()));
					complaintMatrixDTObj.setComplaintTypeCode(complaintService
							.getComplaintTypeDescDb(
									sessionStateManage.getLanguageId(),
									complaintMatrixDTO.getComplaintTypeId())
							.get(0).getFullDesc());
					complaintMatrixDTObj
							.setComplaintTakenByCode(complaintService
									.getComplaintAssignedDesc(
											sessionStateManage.getLanguageId(),
											complaintMatrixDTO
													.getComplaintTakenById())
									.get(0).getFullDescription());
					complaintMatrixDTObj
							.setCommunicationMethodCode(complaintService
									.getCommunicationMethodDescDb(
											sessionStateManage.getLanguageId(),
											complaintMatrixDTO
													.getCommunicationMethodId())
									.get(0).getFullDescription());
					complaintMatrixDTObj
							.setComplaintActionCode(complaintService
									.getCompalintDescDb(
											sessionStateManage.getLanguageId(),
											complaintMatrixDTO
													.getComplaintActionId())
									.get(0).getFullDescription());
					complaintMatrixDTObj
							.setComplaintDestinationCode(complaintService
									.getComplaintAssignedDesc(
											sessionStateManage.getLanguageId(),
											complaintMatrixDTO
													.getComplaintDestionId())
									.get(0).getFullDescription());
					complaintMatrixDTObj.setCreatedBy(complaintMatrixDTO
							.getCreatedBy());
					complaintMatrixDTObj.setCreatedDate(complaintMatrixDTO
							.getCreatedDate());
					complaintMatrixDTObj.setModifiedBy(complaintMatrixDTO
							.getModifiedBy());
					complaintMatrixDTObj.setModifiedDate(complaintMatrixDTO
							.getModifiedDate());
					complaintMatrixDTObj.setApprovedBy(complaintMatrixDTO
							.getApprovedBy());
					complaintMatrixDTObj.setApprovedDate(complaintMatrixDTO
							.getApprovedDate());
					complaintMatrixDTObj.setRemarks(complaintMatrixDTO
							.getRemarks());
					complaintMatrixDTObj.setIsActive(complaintMatrixDTO
							.getIsActive());

					complaintMatrixList.add(complaintMatrixDTObj);
				}
			}

		} catch (NullPointerException ne) {
			LOG.info("Method Name::checkStatus" + ne.getMessage());
			setErrorMessage("Method Name::checkStatus");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			LOG.info("Method Name::checkStatus" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void approvelCheckForComplaintMatrixUser(
			ComplaintMatrixDataTable dataTable) {

		try {
			if (!(dataTable.getModifiedBy() == null ? dataTable.getCreatedBy()
					: dataTable.getModifiedBy())
					.equalsIgnoreCase(sessionStateManage.getUserName())) {
				setCountryId(dataTable.getCountryId());

				setAppCountryId(dataTable.getAppCountryId());
				bankMasterList.addAll(complaintService
						.getCorrespondentBankList(getCountryId(),
								sessionStateManage.getCountryId()));
				// getBankMasterList();
				setBankId(dataTable.getBankId());
				popService();
				setServiceId(dataTable.getServiceId());
				serviceMasterDescList.addAll(generalService.getServiceDesc(
						sessionStateManage.getLanguageId(), serviceId));
				setServiceName(dataTable.getServiceName());
				setCommunicationMethodId(dataTable.getCommunicationMethod());
				setComplaintTypeId(dataTable.getComplaintTypeId());
				setComplaintTakenById(dataTable.getComplaintTakenById());

				setComplaintActionId(dataTable.getComplaintActionId());
				setComplaintDestinationId(dataTable.getComplaintDestinationId());
				setComplaintMatrixPk(dataTable.getComplaintMatrixPk());
				setCreatedBy(dataTable.getCreatedBy());
				setCreatedDate(dataTable.getCreatedDate());
				setModifiedBy(dataTable.getModifiedBy());
				setModifiedDate(dataTable.getModifiedDate());
				setIsActive(dataTable.getIsActive());
				setBooAdd(false);
				setBooApprove(true);
				setBooReadOnly(true);
				setBooRenderSaveExit(false);
				setBooRenderDataTable(false);
				try {
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("../complaint/complaintmatrix.xhtml");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				RequestContext.getCurrentInstance().execute(
						"notApproved.show();");
			}
		} catch (NullPointerException ne) {
			LOG.info("Method Name::approvelCheckForComplaintMatrixUser"
					+ ne.getMessage());
			setErrorMessage("Method Name::approvelCheckForComplaintMatrixUser");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			LOG.info("Method Name::approvelCheckForComplaintMatrixUser"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void complaintMatrixApproveRecord() {
		String approvalMsg = complaintService
				.checkComplaintMatrixMasterApproveMultiUser(
						getComplaintMatrixPk(),
						sessionStateManage.getUserName());
		if (approvalMsg.equalsIgnoreCase("Success")) {
			RequestContext.getCurrentInstance().execute("approve.show();");
		} else {
			RequestContext.getCurrentInstance()
					.execute("alreadyapprov.show();");
			return;
		}
	}

	public void clickOnOKApprove() {
		clear();
		setBooAdd(false);
		setBooRenderDataTable(false);
		setBooRenderSaveExit(false);
		setBooApprove(false);
		setBooReadOnly(false);

		try {
			fetchRecordforComplaintMatrixApproval();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/ComplaintMatrixApproval.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnOkButton() {
		complaintMatrixList.clear();
		fetchRecordforComplaintMatrixApproval();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/ComplaintMatrixApproval.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ComplaintMatrixApprovedByOhterPerson() {
		fetchRecordforComplaintMatrixApproval();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/ComplaintMatrixApproval.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void complaintMatrixCancel() {
		fetchRecordforComplaintMatrixApproval();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../complaint/ComplaintMatrixApproval.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disableSubmit() {
		setBooSubmitPanel(true);
	}

	/* Ended koti Varible Declaration 05/08/15 */

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
