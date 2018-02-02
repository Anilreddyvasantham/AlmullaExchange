package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.IParameterSecurityMaintainceService;
import com.amg.exchange.remittance.model.ParameterGrant;
import com.amg.exchange.remittance.model.ParameterMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("parameterSecurityMaintainceBean")
@Scope("session")
public class ParameterSecurityMaintainceBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ParameterSecurityMaintainceBean.class);
	private BigDecimal parameterId;
	private String parameterDesc;
	private BigDecimal countryBranchid;
	private String countryBranchName;
	private BigDecimal parameterGrantPk;
	private BigDecimal applicationCountryId;
	private String countryName;
	private BigDecimal userId;
	private String userName;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String remarks;
	private String isActive;
	private String recordId;
	private boolean selectCheckBox = false;
	private Boolean booRenderDataTable = false;
	private Boolean booRenderSaveExit = false;
	private Boolean submit = false;
	private List<ParameterSecurityMaintainceDataTable> parameterSecurityMaintainceDtlist = new ArrayList<ParameterSecurityMaintainceDataTable>();
	private List<CountryBranch> countryBranch = new ArrayList<CountryBranch>();
	private List<ParameterMaster> lstParameterMasters = new ArrayList<ParameterMaster>();
	Map<BigDecimal, String> countryBranchList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> parameterMasterMap = new HashMap<BigDecimal, String>();
	SessionStateManage session = new SessionStateManage();
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IParameterSecurityMaintainceService parameterSecurityMaintainceService;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getParameterId() {
		return parameterId;
	}

	public void setParameterId(BigDecimal parameterId) {
		this.parameterId = parameterId;
	}

	public String getParameterDesc() {
		return parameterDesc;
	}

	public void setParameterDesc(String parameterDesc) {
		this.parameterDesc = parameterDesc;
	}

	public BigDecimal getCountryBranchid() {
		return countryBranchid;
	}

	public void setCountryBranchid(BigDecimal countryBranchid) {
		this.countryBranchid = countryBranchid;
	}

	public String getCountryBranchName() {
		return countryBranchName;
	}

	public void setCountryBranchName(String countryBranchName) {
		this.countryBranchName = countryBranchName;
	}

	public List<ParameterSecurityMaintainceDataTable> getParameterSecurityMaintainceDtlist() {
		return parameterSecurityMaintainceDtlist;
	}

	public void setParameterSecurityMaintainceDtlist(List<ParameterSecurityMaintainceDataTable> parameterSecurityMaintainceDtlist) {
		this.parameterSecurityMaintainceDtlist = parameterSecurityMaintainceDtlist;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public BigDecimal getParameterGrantPk() {
		return parameterGrantPk;
	}

	public void setParameterGrantPk(BigDecimal parameterGrantPk) {
		this.parameterGrantPk = parameterGrantPk;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Boolean getBooRenderSaveExit() {
		return booRenderSaveExit;
	}

	public void setBooRenderSaveExit(Boolean booRenderSaveExit) {
		this.booRenderSaveExit = booRenderSaveExit;
	}

	public boolean isSelectCheckBox() {
		return selectCheckBox;
	}

	public void setSelectCheckBox(boolean selectCheckBox) {
		this.selectCheckBox = selectCheckBox;
	}

	public Boolean getSubmit() {
		return submit;
	}

	public void setSubmit(Boolean submit) {
		this.submit = submit;
	}

	public List<ParameterMaster> getLstParameterMasters() {
		return lstParameterMasters;
	}

	public void setLstParameterMasters(List<ParameterMaster> lstParameterMasters) {
		this.lstParameterMasters = lstParameterMasters;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public List<CountryBranch> getCountryBranch() {
		LOGGER.info("Entering into getCountryBranch method");
		countryBranch = new ArrayList<CountryBranch>();
		countryBranch.addAll(generalService.getBranchDetails(session.getCountryId()));
		for (CountryBranch countryBranch1 : countryBranch) {
			countryBranchList.put(countryBranch1.getCountryBranchId(), countryBranch1.getBranchName());
		}
		LOGGER.info("Exit into getCountryBranch method");
		return countryBranch;
	}

	public void setCountryBranch(List<CountryBranch> countryBranch) {
		this.countryBranch = countryBranch;
	}

	private Boolean booVisible;

	public Boolean getBooVisible() {
		return booVisible;
	}

	public void setBooVisible(Boolean booVisible) {
		this.booVisible = booVisible;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {
		try {
			setBooVisible(false);
			LOGGER.info("Entering into pageNavigation method");
			parameterSecurityMaintainceDtlist.clear();
			clear();
			setLstParameterMasters(null);
			setBooRenderDataTable(false);
			setBooRenderSaveExit(false);
			setSelectCheckBox(false);
			toFetchParameterMasterAllRecords();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "ParameterSecurityMaintaince.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/ParameterSecurityMaintaince.xhtml");
		} catch (Exception e) {
			LOGGER.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
		LOGGER.info("Exit into pageNavigation method");
	}

	public void toFetchParameterMasterAllRecords() {
		parameterMasterMap.clear();
		List<ParameterMaster> lstParameterMasters = parameterSecurityMaintainceService.tofetchAllRecordsFromParameterMaster();
		setLstParameterMasters(lstParameterMasters);
		for (ParameterMaster parameterMaster : lstParameterMasters) {
			parameterMasterMap.put(parameterMaster.getParameterMasterId(), parameterMaster.getRecordId());
		}
	}

	public void exit() {
		LOGGER.info("Entering into exit method");
		try {
			parameterSecurityMaintainceDtlist.clear();
			countryBranch.clear();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into exit method");
	}

	public void clear() {
		LOGGER.info("Entering into clear method");
		setParameterId(null);
		setParameterDesc(null);
		setCountryBranchid(null);
		setCountryBranchName("");
		setParameterGrantPk(null);
		setRecordId(null);
		setUserName(null);
		LOGGER.info("Exit into clear method");
	}

	public List<String> autoComplete(String query) {
		LOGGER.info("Entering into autoComplete method");
		if (query.length() > 0) {
			return parameterSecurityMaintainceService.getParameterCode(query);
		} else {
			LOGGER.info("Exit into autoComplete method");
			return null;
		}
	}

	public void popup() {
		try {
			setBooVisible(false);
			LOGGER.info("Entering into popup method");
			setParameterDesc(null);
			setParameterGrantPk(null);
			setCountryBranchid(null);
			List<ParameterMaster> parameterMasterList = parameterSecurityMaintainceService.getParameterRuleId(getParameterId());
			if (parameterMasterList.size() > 0) {
				setParameterDesc(parameterMasterList.get(0).getFullDesc());
				parameterSecurityMaintainceDtlist.clear();
				countryBranch.clear();
			}
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
		LOGGER.info("Exit into popup method");
	}

	public void add() {
		try {
			LOGGER.info("Entering into add method");
			parameterSecurityMaintainceDtlist.clear();
			setUserName(null);
			setBooVisible(false);
			if (getParameterId() != null && getParameterDesc() != null && getCountryBranchid() != null) {
				List<Employee> employeeList = parameterSecurityMaintainceService.getAllBranchList(getCountryBranchid());
				if (employeeList.size() > 0) {
					for (Employee employee : employeeList) {
						ParameterSecurityMaintainceDataTable parameterSecurityDataTable = new ParameterSecurityMaintainceDataTable();
						parameterSecurityDataTable.setParameterId(getParameterId());
						String parameterRecordId = parameterMasterMap.get(getParameterId());
						parameterSecurityDataTable.setParameterRecordId(parameterRecordId);
						parameterSecurityDataTable.setParameterDesc(getParameterDesc());
						parameterSecurityDataTable.setCountryBranchid(getCountryBranchid());
						parameterSecurityDataTable.setCountryBranchName(countryBranchList.get(getCountryBranchid()));
						parameterSecurityDataTable.setApplicationCountryId(session.getCountryId());
						parameterSecurityDataTable.setUserId(employee.getEmployeeId());
						parameterSecurityDataTable.setUserName(employee.getUserName());
						// List<ParameterGrant>
						// parameterGrantList=parameterSecurityMaintainceService.getAllDetailsFromDBAll(getCountryBranchid(),employee.getEmployeeId(),employee.getUserName());
						List<ParameterGrant> parameterGrantList = parameterSecurityMaintainceService.getAllDetailsFromDBAll(getCountryBranchid(), employee.getEmployeeId(), employee.getUserName(), parameterMasterMap.get(getParameterId()));
						if (parameterGrantList.size() > 0) {
							for (ParameterGrant parameterGrant : parameterGrantList) {
								parameterSecurityDataTable.setParameterGrantPk(parameterGrant.getParameterGrentId());
								if (parameterGrant.getIsActive().equalsIgnoreCase(Constants.Yes)) {
									parameterSecurityDataTable.setSelectCheckBox(true);
								} else {
									parameterSecurityDataTable.setSelectCheckBox(false);
								}
								parameterSecurityDataTable.setModifiedBy(parameterGrant.getModifiedBy());
								parameterSecurityDataTable.setModifiedDate(parameterGrant.getModifiedDate());
								parameterSecurityDataTable.setCreatedBy(parameterGrant.getCreatedBy());
								parameterSecurityDataTable.setCreatedDate(parameterGrant.getCreatedDate());
								parameterSecurityDataTable.setIsActive(getIsActive());
							}
						} else {
							parameterSecurityDataTable.setParameterGrantPk(getParameterGrantPk());
							parameterSecurityDataTable.setSelectCheckBox(false);
							parameterSecurityDataTable.setCreatedBy(session.getUserName());
							parameterSecurityDataTable.setCreatedDate(new Date());
							parameterSecurityDataTable.setIsActive(Constants.U);
						}
						parameterSecurityDataTable.setRemarks(getRemarks());
						parameterSecurityMaintainceDtlist.add(parameterSecurityDataTable);
					}
				}
			} else {
				RequestContext.getCurrentInstance().execute("noRecordFound.show();");
				setBooRenderDataTable(false);
				setBooRenderSaveExit(false);
				return;
			}
			setBooRenderDataTable(true);
			setBooRenderSaveExit(true);
			setSubmit(true);
			// clear();
			LOGGER.info("Exit into add method");
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void save() {
		setBooVisible(false);
		try {
			LOGGER.info("Entering into save method");
			if (parameterSecurityMaintainceDtlist.isEmpty()) {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("dataNotFund.show();");
			} else {
				try {
					for (ParameterSecurityMaintainceDataTable dataTable : parameterSecurityMaintainceDtlist) {
						if (dataTable.isSelectCheckBox()) {
							ParameterGrant parameterGrant = new ParameterGrant();
							parameterGrant.setParameterGrentId(dataTable.getParameterGrantPk());
							String recordId = parameterSecurityMaintainceService.toFetchRecordIdFromParameterMaster(dataTable.getParameterId());
							parameterGrant.setRecordId(recordId);
							parameterGrant.setRecordDescription(dataTable.getParameterDesc());
							CountryBranch countryBranch = new CountryBranch();
							countryBranch.setCountryBranchId(dataTable.getCountryBranchid());
							parameterGrant.setFsCountryBranch(countryBranch);
							CountryMaster countryMaster = new CountryMaster();
							countryMaster.setCountryId(dataTable.getApplicationCountryId());
							parameterGrant.setFsCountryMaster(countryMaster);
							Employee employee = new Employee();
							employee.setEmployeeId(dataTable.getUserId());
							parameterGrant.setUserId(employee);
							parameterGrant.setUserName(dataTable.getUserName());
							parameterGrant.setCreatedBy(dataTable.getCreatedBy());
							parameterGrant.setCreatedDate(dataTable.getCreatedDate());
							parameterGrant.setModifiedBy(dataTable.getModifiedBy());
							parameterGrant.setModifiedDate(dataTable.getModifiedDate());
							parameterGrant.setRemarks(dataTable.getRemarks());
							parameterGrant.setIsActive(Constants.Yes);
							parameterSecurityMaintainceService.save(parameterGrant);
						} else {
							if (dataTable.getParameterGrantPk() != null) {
								ParameterGrant parameterGrant = new ParameterGrant();
								parameterGrant.setParameterGrentId(dataTable.getParameterGrantPk());
								String recordId = parameterSecurityMaintainceService.toFetchRecordIdFromParameterMaster(dataTable.getParameterId());
								parameterGrant.setRecordId(recordId);
								parameterGrant.setRecordDescription(dataTable.getParameterDesc());
								CountryBranch countryBranch = new CountryBranch();
								countryBranch.setCountryBranchId(dataTable.getCountryBranchid());
								parameterGrant.setFsCountryBranch(countryBranch);
								CountryMaster countryMaster = new CountryMaster();
								countryMaster.setCountryId(dataTable.getApplicationCountryId());
								parameterGrant.setFsCountryMaster(countryMaster);
								Employee employee = new Employee();
								employee.setEmployeeId(dataTable.getUserId());
								parameterGrant.setUserId(employee);
								parameterGrant.setUserName(dataTable.getUserName());
								parameterGrant.setCreatedBy(dataTable.getCreatedBy());
								parameterGrant.setCreatedDate(dataTable.getCreatedDate());
								parameterGrant.setModifiedBy(session.getUserName());
								parameterGrant.setModifiedDate(new Date());
								parameterGrant.setRemarks(dataTable.getRemarks());
								parameterGrant.setIsActive(Constants.U);
								parameterSecurityMaintainceService.save(parameterGrant);
							} else {
								continue;
							}
						}
					}
					RequestContext.getCurrentInstance().execute("complete.show();");
					return;
				} catch (Exception e) {
					setBooVisible(true);
					RequestContext.getCurrentInstance().execute("error.show();");
					setErrorMessage("Exception ocurred" + e);
					return;
				}
			}
			LOGGER.info("Exit into save method");
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void clickOnOk() {
		LOGGER.info("Entering into clickOnOk method");
		parameterSecurityMaintainceDtlist.clear();
		clear();
		setBooRenderDataTable(false);
		setBooRenderSaveExit(false);
		setSelectCheckBox(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/ParameterSecurityMaintaince.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into clickOnOk method");
	}

	public void SelectCheckBoxList(ParameterSecurityMaintainceDataTable dataTableObj) {
		LOGGER.info("Entering into SelectCheckBoxList method");
		if (dataTableObj.isSelectCheckBox()) {
			setParameterGrantPk(dataTableObj.getParameterGrantPk());
			setIsActive(dataTableObj.getIsActive());
			setSubmit(false);
		} else {
			if (dataTableObj.getParameterGrantPk() != null) {
				setParameterGrantPk(dataTableObj.getParameterGrantPk());
				setIsActive(dataTableObj.getIsActive());
				setSubmit(false);
			} else {
				setSubmit(true);
			}
		}
		LOGGER.info("Exit into SelectCheckBoxList method");
	}

	public void searchRecord() {
		try {
			setBooVisible(false);
			parameterSecurityMaintainceDtlist.clear();
			List<Employee> employeeList = parameterSecurityMaintainceService.toFetchEmployeeRecords(getUserName(), getCountryBranchid());
			if (employeeList.size() != 0 && employeeList != null) {
				for (Employee employee : employeeList) {
					ParameterSecurityMaintainceDataTable parameterSecurityDataTable = new ParameterSecurityMaintainceDataTable();
					parameterSecurityDataTable.setParameterId(getParameterId());
					parameterSecurityDataTable.setParameterDesc(getParameterDesc());
					parameterSecurityDataTable.setCountryBranchid(getCountryBranchid());
					parameterSecurityDataTable.setCountryBranchName(countryBranchList.get(getCountryBranchid()));
					parameterSecurityDataTable.setApplicationCountryId(session.getCountryId());
					parameterSecurityDataTable.setUserId(employee.getEmployeeId());
					parameterSecurityDataTable.setUserName(employee.getUserName());
					List<ParameterGrant> parameterGrantList = parameterSecurityMaintainceService.getAllDetailsFromDBAll(getCountryBranchid(), employee.getEmployeeId(), employee.getUserName());
					if (parameterGrantList.size() > 0) {
						for (ParameterGrant parameterGrant : parameterGrantList) {
							parameterSecurityDataTable.setParameterGrantPk(parameterGrant.getParameterGrentId());
							if (parameterGrant.getIsActive().equalsIgnoreCase(Constants.Yes)) {
								parameterSecurityDataTable.setSelectCheckBox(true);
							} else {
								parameterSecurityDataTable.setSelectCheckBox(false);
							}
							parameterSecurityDataTable.setModifiedBy(parameterGrant.getModifiedBy());
							parameterSecurityDataTable.setModifiedDate(parameterGrant.getModifiedDate());
							parameterSecurityDataTable.setCreatedBy(parameterGrant.getCreatedBy());
							parameterSecurityDataTable.setCreatedDate(parameterGrant.getCreatedDate());
							parameterSecurityDataTable.setIsActive(getIsActive());
						}
					} else {
						parameterSecurityDataTable.setParameterGrantPk(getParameterGrantPk());
						parameterSecurityDataTable.setSelectCheckBox(false);
						parameterSecurityDataTable.setCreatedBy(session.getUserName());
						parameterSecurityDataTable.setCreatedDate(new Date());
						parameterSecurityDataTable.setIsActive(Constants.U);
					}
					parameterSecurityDataTable.setRemarks(getRemarks());
					parameterSecurityMaintainceDtlist.add(parameterSecurityDataTable);
				}
			}
			setBooRenderDataTable(true);
			setBooRenderSaveExit(true);
			setSubmit(true);
		} catch (Exception e) {
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}
}
