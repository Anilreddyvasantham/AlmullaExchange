/**
 * 
 */
package com.amg.exchange.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;
import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IDistrictMasterService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.bean.AdditionBankRuleWithAlMullaCodeApprovalDataTable;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Subramaniam
 * 
 */
@Component("districtMasterBean")
@Scope("session")
public class DistrictMasterBean<T> implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(DistrictMasterBean.class);
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer selectType;
	private BigDecimal districtId;
	private BigDecimal districtDescId;
	private BigDecimal localDistrictDescId;
	private BigDecimal countryId;
	private String countryName;
	private String stateName;
	private BigDecimal stateId;
	private String districtCode;
	private String localDistrictName;
	private String englishDistrictName;
	private BigDecimal englishLanguageId;
	private BigDecimal localLanguageId;
	// private String cityStatus;
	private String remarks = "";
	private String isActive;
	private String dynamicLabelForActivateDeactivate;
	private Date approvedDate;
	private String approvedBy;
	private String createdBy;
	private String updatedBy;
	private Date creationDate;
	private String stateCode;
	private Date lastUpdated;
	private Boolean booEdit = false;
	private Boolean boodisable = false;
	private Boolean booBtnClear = false;
	private UploadedFile uploadedFile;
	private Boolean booRenderDataTableFile;
	private Boolean checkStatus = null;
	private boolean booDistrictDetails = false;
	private boolean booDistrictDetailsapproval = false;
	private boolean booSearchApproval = false;
	private boolean booReadOnly=false;

	private String errorMessage ;
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	SessionStateManage sessionStateManage = new SessionStateManage();

	DistrictMaster districtMaster = new DistrictMaster();

	private List<DistrictMasterDesc> lstFromDb = new ArrayList<DistrictMasterDesc>();
	private List<DistrictMaster> lstmasterFromDb = new ArrayList<DistrictMaster>();

	private List<DistrictMasterDataTable> lstDistrictMasterDataTables = new CopyOnWriteArrayList<DistrictMasterDataTable>();
	private List<DistrictMasterDataTable> lstDistrictMasterDataTablesNewList = new CopyOnWriteArrayList<DistrictMasterDataTable>();
	List<DistrictMasterDataTable> districtMasterListDt = new ArrayList<>();

	private List<CountryMasterDesc> listCountry = new ArrayList<CountryMasterDesc>();
	private List<StateMasterDesc> lststate = new ArrayList<StateMasterDesc>();

	private List<StateMaster> lstOfStateMaster = new ArrayList<StateMaster>();
	
	DistrictMasterDataTable districtMasterDataTable = new DistrictMasterDataTable();

	@Autowired
	IGeneralService<T> iGeneralService;
	@Autowired
	IDistrictMasterService<T> iDistrictMasterService;

	public DistrictMasterBean() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getStateId() {
		return stateId;
	}

	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getLocalDistrictName() {
		return localDistrictName;
	}

	public void setLocalDistrictName(String localDistrictName) {
		this.localDistrictName = localDistrictName;
	}

	public String getEnglishDistrictName() {
		return englishDistrictName;
	}

	public void setEnglishDistrictName(String englishDistrictName) {
		this.englishDistrictName = englishDistrictName;
	}

	public BigDecimal getLocalDistrictDescId() {
		return localDistrictDescId;
	}

	public void setLocalDistrictDescId(BigDecimal localDistrictDescId) {
		this.localDistrictDescId = localDistrictDescId;
	}

	

	public DistrictMasterDataTable getDistrictMasterDataTable() {
		return districtMasterDataTable;
	}

	public void setDistrictMasterDataTable(DistrictMasterDataTable districtMasterDataTable) {
		this.districtMasterDataTable = districtMasterDataTable;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateName() {
		return stateName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public BigDecimal getDistrictDescId() {
		return districtDescId;
	}

	public void setDistrictDescId(BigDecimal districtDescId) {
		this.districtDescId = districtDescId;
	}
	
	public BigDecimal getDistrictId() {
		return districtId;
	}

	public void setDistrictId(BigDecimal districtId) {
		this.districtId = districtId;
	}

	public Integer getSelectType() {
		return selectType;
	}

	public void setSelectType(Integer selectType) {
		this.selectType = selectType;
	}

	public List<CountryMasterDesc> getListCountry() {
		return listCountry;
	}

	public void setListCountry(List<CountryMasterDesc> listCountry) {
		this.listCountry = listCountry;
	}

	public List<CountryMasterDesc> getLstCountry() {
		try{
		listCountry = getiGeneralService().getCountryList(sessionStateManage.getLanguageId());
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::popStatelist");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			//return ;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			//return;
		}
		return listCountry;
	}

	/*
	 * public void setLstCountry(List<CountryMasterDesc> lstCountry) {
	 * this.lstCountry = lstCountry; }
	 */

	public BigDecimal getEnglishLanguageId() {
		return englishLanguageId;
	}

	public boolean isBooSearchApproval() {
		return booSearchApproval;
	}

	public void setBooSearchApproval(boolean booSearchApproval) {
		this.booSearchApproval = booSearchApproval;
	}

	public void setEnglishLanguageId(BigDecimal englishLanguageId) {
		this.englishLanguageId = englishLanguageId;
	}

	public BigDecimal getLocalLanguageId() {
		return localLanguageId;
	}

	public void setLocalLanguageId(BigDecimal localLanguageId) {
		this.localLanguageId = localLanguageId;
	}

	public List<StateMasterDesc> getLststate() {
		return lststate;
	}

	public void setLststate(List<StateMasterDesc> lststate) {
		this.lststate = lststate;
	}

	public void popStatelist() {

		try {
			lststate = getiGeneralService().getStateList(sessionStateManage.getLanguageId(), getCountryId());
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::popStatelist");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		
		/*
		 * for (StateMasterDesc stateMasterDesc : lstState) {
		 * mapStateList.put(stateMasterDesc.getFsStateMaster().getStateId(),
		 * stateMasterDesc.getFsStateMaster().getStateCode());
		 * //mapStateListCode
		 * .put(stateMasterDesc.getFsStateMaster().getStateCode(),
		 * stateMasterDesc.getStateName());
		 * //setStateCode(stateMasterDesc.getFsStateMaster().getStateCode()); }
		 * setLststate(lststate); //lststate =
		 * getiGeneralService().getStateList(sessionStateManage.getLanguageId(),
		 * getCountryId());
		 */}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public boolean isBooDistrictDetails() {
		return booDistrictDetails;
	}

	public void setBooDistrictDetails(boolean booDistrictDetails) {
		this.booDistrictDetails = booDistrictDetails;
	}

	public boolean isBooDistrictDetailsapproval() {
		return booDistrictDetailsapproval;
	}

	public void setBooDistrictDetailsapproval(boolean booDistrictDetailsapproval) {
		this.booDistrictDetailsapproval = booDistrictDetailsapproval;
	}

	public List<DistrictMasterDesc> getLstFromDb() {
		return lstFromDb;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public Boolean getBooRenderDataTableFile() {
		return booRenderDataTableFile;
	}

	public void setBooRenderDataTableFile(Boolean booRenderDataTableFile) {
		this.booRenderDataTableFile = booRenderDataTableFile;
	}

	public Boolean getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Boolean checkStatus) {
		this.checkStatus = checkStatus;
	}

	public List<DistrictMasterDataTable> getLstDistrictMasterDataTables() {
		return lstDistrictMasterDataTables;
	}

	public void setLstDistrictMasterDataTables(List<DistrictMasterDataTable> lstDistrictMasterDataTables) {
		this.lstDistrictMasterDataTables = lstDistrictMasterDataTables;
	}

	public List<DistrictMasterDataTable> getLstDistrictMasterDataTablesNewList() {
		return lstDistrictMasterDataTablesNewList;
	}

	public void setLstDistrictMasterDataTablesNewList(List<DistrictMasterDataTable> lstDistrictMasterDataTablesNewList) {
		this.lstDistrictMasterDataTablesNewList = lstDistrictMasterDataTablesNewList;
	}

	public void setLstFromDb(List<DistrictMasterDesc> lstFromDb) {
		this.lstFromDb = lstFromDb;
	}

	public List<StateMaster> getLstOfStateMaster() {
		return lstOfStateMaster;
	}

	public void setLstOfStateMaster(List<StateMaster> lstOfStateMaster) {
		this.lstOfStateMaster = lstOfStateMaster;
	}

	public IGeneralService<T> getiGeneralService() {
		return iGeneralService;
	}

	public void setiGeneralService(IGeneralService<T> iGeneralService) {
		this.iGeneralService = iGeneralService;
	}

	public IDistrictMasterService<T> getiDistrictMasterService() {
		return iDistrictMasterService;
	}

	public void setiDistrictMasterService(IDistrictMasterService<T> iDistrictMasterService) {
		this.iDistrictMasterService = iDistrictMasterService;
	}

	public List<DistrictMasterDataTable> getDistrictMasterListDt() {
		return districtMasterListDt;
	}

	public void setDistrictMasterListDt(List<DistrictMasterDataTable> districtMasterListDt) {
		this.districtMasterListDt = districtMasterListDt;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void approvalPageNavigation() {

		try {

			setCountryId(null);
			setStateId(null);
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "districtmasterapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/districtmasterapproval.xhtml");

			setSelectAll(false);
			setBooDistrictDetails(false);
			setBooDistrictDetailsapproval(false);
			setBooSearchApproval(true);
			// approveView();
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::approvalPageNavigation");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void addRecordsToDataTable() {
		setBooReadOnly( false);
		boolean alreadyDT = false;
		// Data table duplicatecheck
		if (lstDistrictMasterDataTables.size() != 0) {
			for (DistrictMasterDataTable districtMasterDataTable : lstDistrictMasterDataTables) {
				// Data table duplicate check - article code check
				if (districtMasterDataTable.getDistrictCode() != null && districtMasterDataTable.getDistrictCode().equals(getDistrictCode())) {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("articleCodeExist.show();");
					alreadyDT = true;
					break;
				}
				// Data table duplicate check - English description code check
				if (districtMasterDataTable.getEnglishDistrictName() != null && districtMasterDataTable.getEnglishDistrictName().equalsIgnoreCase(getEnglishDistrictName())) {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("EnglishDescriptionExist.show();");
					alreadyDT = true;
					setEnglishDistrictName(null);
					setLocalDistrictName(null);
					break;
				}
				// Data table duplicate check - local description code check
				if (districtMasterDataTable.getLocalDistrictName() != null && districtMasterDataTable.getLocalDistrictName().equalsIgnoreCase(getLocalDistrictName())) {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("LocalDescriptionExist.show();");
					setEnglishDistrictName(null);
					setLocalDistrictName(null);
					alreadyDT = true;
					break;
				}
			}
		}

		boolean alreadyDB = false;
		// Data table duplicatecheck
		if (!alreadyDT) {
			if (getDistrictId() == null) {
				try {
					List<DistrictMaster> exist = getiDistrictMasterService().getDistrictBasedOnDistrictCode(getDistrictCode(), getStateId());
					if (exist != null) {
						alreadyDB = true;
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("articleCodeExist.show();");

					}

					if (exist != null) {
						List<DistrictMasterDesc> local = getiDistrictMasterService().checkLocalDescription(getLocalDistrictName(), exist.get(0).getDistrictId());
						List<DistrictMasterDesc> english = getiDistrictMasterService().checkEnglishDescription(getEnglishDistrictName(), exist.get(0).getDistrictId());
						if (local != null && local.size() > 0) {
							alreadyDB = true;
							RequestContext context = RequestContext.getCurrentInstance();
							context.execute("EnglishDescriptionExist.show();");
							setEnglishDistrictName(null);
							setLocalDistrictName(null);
						} else if (english != null && english.size() > 0) {
							alreadyDB = true;
							RequestContext context = RequestContext.getCurrentInstance();
							context.execute("LocalDescriptionExist.show();");
							setEnglishDistrictName(null);
							setLocalDistrictName(null);
						}
					}
				} catch (NullPointerException ne) {
					// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setErrorMessage("Method Name::addRecordsToDataTable");
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return;
				} catch (Exception exception) {
					// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}
				
			

			} else {
				alreadyDB = false;
			}
		}

		
		if (!alreadyDT && !alreadyDB) {
			if (districtMasterListDt.size() == 0) {

				/*List<DistrictMaster> exist = getiDistrictMasterService().viewMasterRecords(getStateId());
				//if (exist != null && exist.size() != 0) {
					List<DistrictMasterDesc> english = getiDistrictMasterService().checkEnglishDescription(getEnglishDistrictName(), exist.get(0).getDistrictId());
					if (english != null) {
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("EnglishDescriptionExist.show();");
						setEnglishDistrictName(null);
						setLocalDistrictName(null);
						// setCityStatus(null);
						return;
					} else {*/
						DistrictMasterDataTable districtMasterDataTable = new DistrictMasterDataTable();
try{
						districtMasterDataTable.setDistrictId(getDistrictId());
						districtMasterDataTable.setDistrictDescId(getDistrictDescId());
						districtMasterDataTable.setDistrictArabicDescId(getLocalDistrictDescId());
						districtMasterDataTable.setDistrictCode(getDistrictCode());
						districtMasterDataTable.setStateId(getStateId());
						districtMasterDataTable.setEnglishDistrictName(getEnglishDistrictName());
						districtMasterDataTable.setLocalDistrictName(getLocalDistrictName());
						districtMasterDataTable.setEnglishLanguageTypeId(getEnglishLanguageId());
						districtMasterDataTable.setLocalLanguageTypeId(getLocalLanguageId());

						districtMasterDataTable.setCreatedBy(getCreatedBy());
						districtMasterDataTable.setCreationDate(getCreationDate());
						districtMasterDataTable.setCountryId(getCountryId());

						List<StateMaster> stateList = getiDistrictMasterService().getCountryFromState(getStateId());

						if (stateList != null) {
							districtMasterDataTable.setStateCode(stateList.get(0).getStateCode());
						}

						String stateName = getiDistrictMasterService().getStateName(sessionStateManage.getLanguageId(), getStateId());

						if (stateName != null)
							districtMasterDataTable.setStateName(stateName);

						if (getDistrictId() != null) {

							if (lstDistrictMasterDataTablesNewList.size() == 0 && (lstDistrictMasterDataTables.size() == 0 || getDistrictMasterDataTable() != null)) {

								districtMasterDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
								setBooEdit(true);
								districtMasterDataTable.setDistrictActive(Constants.U);
								districtMasterDataTable.setUpdatedBy(sessionStateManage.getUserName());
								districtMasterDataTable.setLastUpdated(new Date());

								if (getDistrictMasterDataTable() != null) {
									if ((districtMasterDataTable.getDistrictCode().equalsIgnoreCase(getDistrictCode()) && districtMasterDataTable.getEnglishDistrictName().equalsIgnoreCase(getEnglishDistrictName()) && districtMasterDataTable.getLocalDistrictName().equalsIgnoreCase(
											getLocalDistrictName()))) {

										districtMasterDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
										districtMasterDataTable.setDistrictActive(getIsActive());
										districtMasterDataTable.setUpdatedBy(getUpdatedBy());
										districtMasterDataTable.setLastUpdated(getLastUpdated());
										districtMasterDataTable.setApprovedBy(getApprovedBy());
										districtMasterDataTable.setApprovedDate(getApprovedDate());
										// complaintTypeDataTable.setRemarks(getRemarks());

									} else {
										districtMasterDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
										setBooEdit(true);
										districtMasterDataTable.setDistrictActive(Constants.U);
										districtMasterDataTable.setUpdatedBy(sessionStateManage.getUserName());
										districtMasterDataTable.setLastUpdated(new Date());
										districtMasterDataTable.setApprovedBy(null);
										districtMasterDataTable.setApprovedDate(null);
										// complaintTypeDataTable.setRemarks(null);

									}
								}

							} else {
								districtMasterDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
								districtMasterDataTable.setDistrictActive(getIsActive());
								districtMasterDataTable.setUpdatedBy(getUpdatedBy());
								districtMasterDataTable.setLastUpdated(getLastUpdated());
								districtMasterDataTable.setApprovedBy(getApprovedBy());
								districtMasterDataTable.setApprovedDate(getApprovedDate());
								// districtMasterDataTable.setRemarks(getRemarks());
							}

						} else {
							districtMasterDataTable.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
							districtMasterDataTable.setDistrictActive(Constants.U);
							districtMasterDataTable.setCreatedBy(sessionStateManage.getUserName());
							districtMasterDataTable.setCreationDate(new Date());

						}

						lstDistrictMasterDataTables.add(districtMasterDataTable);

						if (getDistrictId() == null) {
							lstDistrictMasterDataTablesNewList.add(districtMasterDataTable);
						}
					}catch (NullPointerException ne) {
						// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
						setErrorMessage("Method Name::addRecordsToDataTable");
						RequestContext.getCurrentInstance().execute("nullPointerId.show();");
						return;
					} catch (Exception exception) {
						// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
						setErrorMessage(exception.getMessage());
						RequestContext.getCurrentInstance().execute("error.show();");
						return;
					}
				//}

			}
			if (lstDistrictMasterDataTables.size() > 0) {

				if (districtMasterListDt.size() > 0) {
					for (DistrictMasterDataTable districtMasterDataTable1 : lstDistrictMasterDataTables) {
						for (DistrictMasterDataTable districtMasterDataTable2 : districtMasterListDt) {
							if (districtMasterDataTable1.getDistrictCode().toString().equals(districtMasterDataTable2.getDistrictCode().toString())) {
								districtMasterListDt.remove(districtMasterDataTable2);
							}

						}

					}

				}
				lstDistrictMasterDataTables.addAll(districtMasterListDt);
			} else {
				lstDistrictMasterDataTables.addAll(districtMasterListDt);

			}
			clearAllFields();
			setBooDistrictDetails(true);
			setBooBtnClear(false);
			setBoodisable(false);
			setBooEdit(false);

		}

	}

	public void searchApprove() {
		setCountryId(null);
		setStateId(null);
		setBooDistrictDetails(false);
		setBooDistrictDetailsapproval(false);
		setBooSearchApproval(true);

	}

	public void approveView() {

		if(lstApproved != null && !lstApproved.isEmpty()){
			lstApproved.clear();
		}
		setSelectAll(false);
		lstDistrictMasterDataTables.clear();
		if (getCountryId() == null || getStateId() == null) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("viewSearch.show();");
			return;
		} else {
try{
			lstOfStateMaster = getiDistrictMasterService().getStateMasterId(getCountryId(), getStateId());

			if (lstOfStateMaster != null) {
				for (StateMaster state : lstOfStateMaster) {
					lstmasterFromDb = getiDistrictMasterService().approveViewMasterRecords(state.getStateId(), Constants.U);
					if (lstmasterFromDb != null) {
						for (DistrictMaster tempObj : lstmasterFromDb) {

							
							DistrictMasterDataTable dataTable = new DistrictMasterDataTable();
							dataTable.setDistrictId(tempObj.getDistrictId());
							dataTable.setApprovedBy(tempObj.getApprovedBy());
							dataTable.setApprovedDate(tempObj.getApprovedDate());
							dataTable.setDistrictCode(tempObj.getDistrictCode());
							dataTable.setUpdatedBy(tempObj.getUpdatedBy());
							dataTable.setLastUpdated(tempObj.getLastUpdated());
							
							dataTable.setDistrictActive(tempObj.getDistrictActive());
							dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(tempObj.getDistrictActive()));
							dataTable.setCreationDate(tempObj.getCreationDate());
							dataTable.setCreatedBy(tempObj.getCreatedBy());
							

							dataTable.setStateId(tempObj.getFsStateMaster().getStateId());
							dataTable.setStateCode(tempObj.getFsStateMaster().getStateCode());

							List<StateMaster> stateList = getiDistrictMasterService().getCountryFromState(tempObj.getFsStateMaster().getStateId());

							if (stateList != null) {
								dataTable.setCountryId(stateList.get(0).getFsCountryMaster().getCountryId());

							}
							String stateName = getiDistrictMasterService().getStateName(sessionStateManage.getLanguageId(), tempObj.getFsStateMaster().getStateId());

							if (stateName != null)
								dataTable.setStateName(stateName);

							lstFromDb = getiDistrictMasterService().viewById(tempObj.getDistrictId());
							LOGGER.info(lstFromDb == null);
							if (lstFromDb != null && lstFromDb.size() != 0) {
								for (DistrictMasterDesc districtMasterDesc : lstFromDb) {
									if (districtMasterDesc.getFsLanguageType().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))) {
										dataTable.setEnglishLanguageTypeId(districtMasterDesc.getFsLanguageType().getLanguageId());
										dataTable.setEnglishDistrictName(districtMasterDesc.getDistrict());
										dataTable.setDistrictDescId(districtMasterDesc.getDistrictDescId());
									} else if (districtMasterDesc.getFsLanguageType().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
										dataTable.setLocalLanguageTypeId(districtMasterDesc.getFsLanguageType().getLanguageId());
										dataTable.setLocalDistrictName(districtMasterDesc.getDistrict());
										dataTable.setDistrictArabicDescId(districtMasterDesc.getDistrictDescId());
									}
								}
							} else {

							}

							dataTable.setIsCheck(false);
							if(dataTable.getUpdatedBy()==null?dataTable.getCreatedBy().equalsIgnoreCase(sessionStateManage.getUserName()) : dataTable.getUpdatedBy().equalsIgnoreCase(sessionStateManage.getUserName())){
								dataTable.setDisableCheck(true);
							}else{
								dataTable.setDisableCheck(false);
							}
							
							lstDistrictMasterDataTables.add(dataTable);
							
							setBooDistrictDetails(true);
							setBooDistrictDetailsapproval(false);
							setBooSearchApproval(false);
							setBooBtnClear(false);
							setBoodisable(false);
							setBooEdit(false);
						}

					} else {
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("dataNotFund.show();");
						return;
					}

				}
			}
}catch (NullPointerException ne) {
	// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
	setErrorMessage("Method Name::approveView");
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

	public void view() {
		setBooReadOnly( false);
		
		if (getCountryId() == null || getStateId() == null) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("viewSearch.show();");
			return;
		} else {
try{
	

			lstDistrictMasterDataTables.clear();
			
			lstOfStateMaster = getiDistrictMasterService().getStateMasterId(getCountryId(), getStateId());

			if (lstOfStateMaster != null) {
				for (StateMaster state : lstOfStateMaster) {
					lstmasterFromDb = getiDistrictMasterService().viewMasterRecords(state.getStateId());
					for (DistrictMaster tempObj : lstmasterFromDb) {
						//tempObj=null;
						if (tempObj.getDistrictId() != null) {

							DistrictMasterDataTable dataTable = new DistrictMasterDataTable();
							dataTable.setDistrictId(tempObj.getDistrictId());
							dataTable.setApprovedBy(tempObj.getApprovedBy());
							dataTable.setApprovedDate(tempObj.getApprovedDate());
							dataTable.setDistrictCode(tempObj.getDistrictCode());
							dataTable.setUpdatedBy(tempObj.getUpdatedBy());
							dataTable.setLastUpdated(tempObj.getLastUpdated());
							dataTable.setDistrictActive(tempObj.getDistrictActive());
							dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(tempObj.getDistrictActive()));
							dataTable.setCreationDate(tempObj.getCreationDate());
							dataTable.setCreatedBy(tempObj.getCreatedBy());

							dataTable.setStateId(tempObj.getFsStateMaster().getStateId());
							dataTable.setStateCode(tempObj.getFsStateMaster().getStateCode());

							List<StateMaster> stateList = getiDistrictMasterService().getCountryFromState(tempObj.getFsStateMaster().getStateId());

							if (stateList != null) {
								dataTable.setCountryId(stateList.get(0).getFsCountryMaster().getCountryId());

							}
							String stateName = getiDistrictMasterService().getStateName(sessionStateManage.getLanguageId(), tempObj.getFsStateMaster().getStateId());

							if (stateName != null)
								dataTable.setStateName(stateName);

							lstFromDb = getiDistrictMasterService().viewById(tempObj.getDistrictId());
							LOGGER.info(lstFromDb == null);
							if (lstFromDb != null && lstFromDb.size() != 0) {
								for (DistrictMasterDesc districtMasterDesc : lstFromDb) {
									if (districtMasterDesc.getFsLanguageType().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))) {
										dataTable.setEnglishLanguageTypeId(districtMasterDesc.getFsLanguageType().getLanguageId());
										dataTable.setEnglishDistrictName(districtMasterDesc.getDistrict());
										dataTable.setDistrictDescId(districtMasterDesc.getDistrictDescId());
									} else if (districtMasterDesc.getFsLanguageType().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
										dataTable.setLocalLanguageTypeId(districtMasterDesc.getFsLanguageType().getLanguageId());
										dataTable.setLocalDistrictName(districtMasterDesc.getDistrict());
										dataTable.setDistrictArabicDescId(districtMasterDesc.getDistrictDescId());
									}
								}
							} else {

							}
							LOGGER.info(dataTable);
							lstDistrictMasterDataTables.add(dataTable);
						}
					}
				}
			}

			LOGGER.info(lstFromDb.size());
			setBooDistrictDetails(true);
			setBooBtnClear(false);
			setBoodisable(false);
			setBooEdit(false);
			LOGGER.info("Exit into view method");
		}		
	catch (NullPointerException ne) {
		// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		setErrorMessage("Method Name::view");
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

	private String setreverActiveStatus(String status) {

		LOGGER.info("Enter in active status method ");

		String strStatus = null;
		if (status == null) {
			strStatus = Constants.REMOVE;
		} else if (status.equalsIgnoreCase(Constants.U)) {
			strStatus = Constants.DELETE;
		} else if (status.equalsIgnoreCase(Constants.D)) {
			strStatus = Constants.ACTIVATE;
		} else if (status.equalsIgnoreCase(Constants.Yes)) {
			strStatus = Constants.DEACTIVATE;
		}
		LOGGER.info("Exit from active status method ");
		return strStatus;

	}


	public void clear() {
		clearAllFields();
		setCountryId(null);
		setStateId(null);
		// setBooDistrictDetails(true);
	}

	public void clearAllFields() {
		setSelectType(0);
		getLstCountry();
		getLststate();
		setBooReadOnly( false);
		setDistrictCode(null);
		setEnglishDistrictName(null);
		setLocalDistrictName(null);
		setCreatedBy(null);
		setLastUpdated(null);
		setCreationDate(null);
		setUpdatedBy(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setIsActive(null);
		setDistrictDescId(null);
		setDistrictId(null);
		setLocalDistrictDescId(null);
		setLocalLanguageId(null);
		setEnglishLanguageId(null);

		setBooBtnClear(false);
		setBoodisable(false);
		setBooEdit(false);
		
		//setBooDistrictDetails(false);

	}

	public void pageNavigationMode() {
		try {
			
			if (getSelectType() == 2) {
				
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/districtmasterfileupload.xhtml");
				lstDistrictMasterDataTables.clear();
				setBooDistrictDetails(false);
				setBooBtnClear(false);
				setBoodisable(false);
				setBooEdit(false);

			} else if (getSelectType() == 1) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/districtmaster.xhtml");
				lstDistrictMasterDataTables.clear();
				setBooDistrictDetails(false);
				setCountryId(null);
				setStateId(null);
				clearAllFields();
				
			}

		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::pageNavigationMode");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void exit() {
		LOGGER.info("Enter in exit method ");

		try {
			if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
				lstDistrictMasterDataTables.clear();
				setBooDistrictDetails(false);
				setBooBtnClear(false);
				setBoodisable(false);
				setBooEdit(false);
				clearAllFields();
				setCountryId(null);
				setStateId(null);

			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
				lstDistrictMasterDataTables.clear();
				setBooDistrictDetails(false);
				clearAllFields();
				setCountryId(null);
				setStateId(null);
			}
			
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::exit");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

		LOGGER.info("Exit from exit method ");
	}

	public void save() {

		if (lstDistrictMasterDataTables.isEmpty()) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		} else {

			try {

				for (DistrictMasterDataTable districtMasterDataTable : lstDistrictMasterDataTables) {

					districtMaster.setDistrictId(districtMasterDataTable.getDistrictId());

					StateMaster stateMaster = new StateMaster();
					stateMaster.setStateId(districtMasterDataTable.getStateId());
					districtMaster.setFsStateMaster(stateMaster);

					districtMaster.setDistrictCode(districtMasterDataTable.getDistrictCode());
					districtMaster.setStateCode(districtMasterDataTable.getStateCode());				

					districtMaster.setUpdatedBy(districtMasterDataTable.getUpdatedBy());
					districtMaster.setLastUpdated(districtMasterDataTable.getLastUpdated());				
					districtMaster.setCreatedBy(districtMasterDataTable.getCreatedBy());
					districtMaster.setCreationDate(districtMasterDataTable.getCreationDate());
					districtMaster.setDistrictActive(districtMasterDataTable.getDistrictActive());
					districtMaster.setApprovedBy(districtMasterDataTable.getApprovedBy());
					districtMaster.setApprovedDate(districtMasterDataTable.getApprovedDate());

					DistrictMasterDesc districtMasterDesc = new DistrictMasterDesc();

					LanguageType englishLanguageType = new LanguageType();
					englishLanguageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
					districtMasterDesc.setFsLanguageType(englishLanguageType);
					districtMasterDesc.setDistrictDescId(districtMasterDataTable.getDistrictDescId());
					districtMasterDesc.setDistrict(districtMasterDataTable.getEnglishDistrictName());
					districtMasterDesc.setFsDistrictMaster(districtMaster);

					DistrictMasterDesc districtMasterDesc1 = new DistrictMasterDesc();

					LanguageType arabicLanguageType = new LanguageType();
					arabicLanguageType.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
					districtMasterDesc1.setFsLanguageType(arabicLanguageType);

					districtMasterDesc1.setDistrictDescId(districtMasterDataTable.getDistrictArabicDescId());
					districtMasterDesc1.setDistrict(districtMasterDataTable.getLocalDistrictName());
					districtMasterDesc1.setFsDistrictMaster(districtMaster);


					if(districtMasterDataTable.getDynamicLabelForActivateDeactivate() != null && !districtMasterDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase("Duplicate")){
						insertDistrictMasterAndDesc(districtMaster, districtMasterDesc, districtMasterDesc1);
					}

				}

			} catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::save");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
			RequestContext.getCurrentInstance().execute("complete.show();");
		}

	}

	private void insertDistrictMasterAndDesc(DistrictMaster districtMaster, DistrictMasterDesc districtMasterDesc, DistrictMasterDesc districtMasterDesc1) {
		try{
		getiDistrictMasterService().saveRecord(districtMaster);
		getiDistrictMasterService().save(districtMasterDesc);
		getiDistrictMasterService().save(districtMasterDesc1);
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::insertDistrictMasterAndDesc");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void clickOk() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/districtmaster.xhtml");
			lstDistrictMasterDataTables.clear();
			setBooDistrictDetails(false);
			setBooBtnClear(false);
			setBoodisable(false);
			setBooEdit(false);
			clearAllFields();
			setCountryId(null);
			setStateId(null);
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::clickOk");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void deleteRecordPermanently(DistrictMasterDataTable districtMasterDataTable) {

		try{
		getiDistrictMasterService().delete(districtMasterDataTable.getDistrictId(), districtMasterDataTable.getDistrictDescId(), districtMasterDataTable.getDistrictArabicDescId());
		RequestContext.getCurrentInstance().execute("delete.show();");
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::deleteRecordPermanently");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void delete(DistrictMasterDataTable districtMasterDataTable) {
		try {
		districtMaster.setDistrictId(districtMasterDataTable.getDistrictId());
		districtMaster.setDistrictCode(districtMasterDataTable.getDistrictCode());

		StateMaster stateMaster = new StateMaster();
		stateMaster.setStateId(districtMasterDataTable.getStateId());
		districtMaster.setFsStateMaster(stateMaster);

		districtMaster.setStateCode(districtMasterDataTable.getStateCode());

		if (districtMasterDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase("Activate")) {
			districtMaster.setDistrictActive(Constants.U);
			districtMaster.setRemarks(null);
			districtMaster.setUpdatedBy(sessionStateManage.getUserName());
			districtMaster.setLastUpdated(new Date());
			districtMaster.setCreatedBy(districtMasterDataTable.getCreatedBy());
			districtMaster.setCreationDate(districtMasterDataTable.getCreationDate());
			districtMaster.setApprovedBy(null);
			districtMaster.setApprovedDate(null);
		} else if (districtMasterDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase("DeActivate")) {
			districtMaster.setDistrictActive(Constants.D);
			districtMaster.setRemarks(getRemarks());
			districtMaster.setUpdatedBy(sessionStateManage.getUserName());
			districtMaster.setLastUpdated(new Date());
			districtMaster.setCreatedBy(districtMasterDataTable.getCreatedBy());
			districtMaster.setCreationDate(districtMasterDataTable.getCreationDate());
			districtMaster.setApprovedBy(null);
			districtMaster.setApprovedDate(null);
		}

		
			getiDistrictMasterService().saveRecord(districtMaster);
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::delete");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void checkStatusType(DistrictMasterDataTable districtMasterDataTable) throws IOException {

		try{
		if (districtMasterDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			districtMasterDataTable.setRemarksCheck(true);

			setRemarks("");
			setApprovedBy(districtMasterDataTable.getApprovedBy());
			setApprovedDate(districtMasterDataTable.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
			return;
		} else if (districtMasterDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)) {

			if (districtMasterDataTable.getLastUpdated() != null && districtMasterDataTable.getUpdatedBy() != null) {
				districtMasterDataTable.setBooCheckDelete(true);
				RequestContext.getCurrentInstance().execute("pending.show();");
				return;
			} else {
				districtMasterDataTable.setBooCheckDelete(true);
				RequestContext.getCurrentInstance().execute("permanentDelete.show();");
				return;
			}

		} else if (districtMasterDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			districtMasterDataTable.setActivateRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;
		} else if (districtMasterDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			lstDistrictMasterDataTables.remove(districtMasterDataTable);

		}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::checkStatusType");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void confirmPermanentDelete() {
		try{
		if (lstDistrictMasterDataTables.size() > 0) {
			for (DistrictMasterDataTable districtMasterDataTable : lstDistrictMasterDataTables) {
				if (districtMasterDataTable.getBooCheckDelete() != null) {
					if (districtMasterDataTable.getBooCheckDelete().equals(true)) {				
						
						deleteRecordPermanently(districtMasterDataTable);
						lstDistrictMasterDataTables.remove(districtMasterDataTable);
						// cancel();
						clickOk();
						view();
					}
				}
			}
		}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::confirmPermanentDelete");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void activateRecord() {
try{
		if (lstDistrictMasterDataTables.size() > 0) {
			for (DistrictMasterDataTable districtMasterDataTable : lstDistrictMasterDataTables) {
				if (districtMasterDataTable.getActivateRecordCheck() != null) {
					if (districtMasterDataTable.getActivateRecordCheck().equals(true)) {
						getiDistrictMasterService().activateRecord(districtMasterDataTable.getDistrictId(), sessionStateManage.getUserName());

						view();

					}
				}
			}
		}
	} catch (NullPointerException ne) {
		// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		setErrorMessage("Method Name::activateRecord");
		RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		return;
	} catch (Exception exception) {
		// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		setErrorMessage(exception.getMessage());
		RequestContext.getCurrentInstance().execute("error.show();");
		return;
	}
	}

	public void remarkSelectedRecord() throws IOException {
		try{
		for (DistrictMasterDataTable districtMasterDataTable : lstDistrictMasterDataTables) {
			if (districtMasterDataTable.getRemarksCheck().equals(true)) {
				if (!getRemarks().equals("")) {
					// districtMasterDataTable.setRemarks(getRemarks());
					setRemarks(getRemarks());
					districtMasterDataTable.setApprovedBy(null);
					districtMasterDataTable.setApprovedDate(null);
					delete(districtMasterDataTable);
					// setRemarks("");
					view();
					// clickOk();
				} else {
					RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
					return;
				}
			}
		}
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::remarkSelectedRecord");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void remarkCancel() {

	}

	public void cancel() {

		try {

			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/districtmasterapproval.xhtml");
			setBooDistrictDetails(true);
			setBooDistrictDetailsapproval(false);
			setBooBtnClear(false);
			setBoodisable(false);
			setBooEdit(false);
			approveView();
			// searc
		}  catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::cancel");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void approve(DistrictMasterDataTable districtMasterDataTable) {

		LOGGER.info("Enter in approve validation view method ");

		System.out.println("districtMasterDataTable.getCreatedBy() ==== > " + districtMasterDataTable.getCreatedBy());
		 if(!(districtMasterDataTable.getUpdatedBy()==null?districtMasterDataTable.getCreatedBy():districtMasterDataTable.getUpdatedBy()).equalsIgnoreCase(sessionStateManage.getUserName())){
		//if (!districtMasterDataTable.getCreatedBy().equalsIgnoreCase(sessionStateManage.getUserName())) {
			setBooDistrictDetails(false);
			setBooDistrictDetailsapproval(true);

			setDistrictCode(districtMasterDataTable.getDistrictCode());
			setLocalDistrictName(districtMasterDataTable.getLocalDistrictName());
			setEnglishDistrictName(districtMasterDataTable.getEnglishDistrictName());
			setStateId(districtMasterDataTable.getStateId());
			setStateName(districtMasterDataTable.getStateName());
			setCountryId(districtMasterDataTable.getCountryId());
			setDistrictId(districtMasterDataTable.getDistrictId());

		} else {
			RequestContext.getCurrentInstance().execute("notValidUser.show();");

		}
		LOGGER.info("Exit from approve validation view method ");

	}

	public void approveRecordSave() {
		try{
		getiDistrictMasterService().approveRecord(getDistrictId(), sessionStateManage.getUserName());
		RequestContext.getCurrentInstance().execute("complete.show();");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::approveRecordSave");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void clickApproveOK() {

		LOGGER.info("Enter in approve ok method ");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/districtmasterapproval.xhtml");
			setBooDistrictDetails(true);
			setBooDistrictDetailsapproval(false);
			searchApprove();
		}  catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::clickApproveOK");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		LOGGER.info("Exit from approve ok method ");
	}

	public Boolean getBooEdit() {
		return booEdit;
	}

	public void setBooEdit(Boolean booEdit) {
		this.booEdit = booEdit;
	}

	public Boolean getBoodisable() {
		return boodisable;
	}

	public void setBoodisable(Boolean boodisable) {
		this.boodisable = boodisable;
	}

	public Boolean getBooBtnClear() {
		return booBtnClear;
	}

	public void setBooBtnClear(Boolean booBtnClear) {
		this.booBtnClear = booBtnClear;
	}

	public void editRecord(DistrictMasterDataTable districtMasterDataTable) {
		try{
		setBooReadOnly(true);
		setBooBtnClear(true);
		setBoodisable(true);
		setBooEdit(true);
		getLstCountry();
		setDistrictId(districtMasterDataTable.getDistrictId());
		setDistrictCode(districtMasterDataTable.getDistrictCode());
		setDynamicLabelForActivateDeactivate(districtMasterDataTable.getDynamicLabelForActivateDeactivate());

		setStateName(districtMasterDataTable.getStateName());
		setCountryId(districtMasterDataTable.getCountryId());
		popStatelist();
		getLststate();
		setStateId(districtMasterDataTable.getStateId());
		setStateCode(districtMasterDataTable.getStateCode());
		// setCityStatus(districtMasterDataTable.getCityStatus());
		setApprovedBy(null);
		setApprovedDate(null);
		setRemarks(null);

		if (districtMasterDataTable.getDistrictId() == null) {
			setLastUpdated(null);
			setUpdatedBy(null);

		} else {
			setUpdatedBy(sessionStateManage.getUserName());
			setLastUpdated(new Date());
		}

		setCreatedBy(districtMasterDataTable.getCreatedBy());
		setCreationDate(districtMasterDataTable.getCreationDate());
		setEnglishLanguageId(districtMasterDataTable.getEnglishLanguageTypeId());
		setEnglishDistrictName(districtMasterDataTable.getEnglishDistrictName());
		setDistrictDescId(districtMasterDataTable.getDistrictDescId());
		// setLocalDistrictDescId(districtMasterDataTable.getDistrictArabicDescId());
		setLocalLanguageId(districtMasterDataTable.getLocalLanguageTypeId());
		setLocalDistrictName(districtMasterDataTable.getLocalDistrictName());
		setLocalDistrictDescId(districtMasterDataTable.getDistrictArabicDescId());
		setIsActive(districtMasterDataTable.getDistrictActive());

		lstDistrictMasterDataTables.remove(districtMasterDataTable);
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::editRecord");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void uploadtoDatatable() {

		if (uploadedFile != null && getCountryId() !=null) {
			lstDistrictMasterDataTables.clear();
			try {

				Workbook workbook = null;
				workbook = new HSSFWorkbook(uploadedFile.getInputstream());

				int numberOfSheets = workbook.getNumberOfSheets();

				for (int i = 0; i < numberOfSheets; i++) {

					Sheet sheet = workbook.getSheetAt(i);

					// every sheet has rows, iterate over them
					Iterator<Row> rowIterator = sheet.iterator();
					int rowNumber = 1;
					while (rowIterator.hasNext()) {

						Row row = rowIterator.next();
						if (rowNumber == 1) {
							rowNumber++;
							continue;
						}

						// Every row has columns, get the column iterator and
						// iterate over them
						Iterator<Cell> cellIterator = row.cellIterator();
						DistrictMasterDataTable districtMasterDataTable = new DistrictMasterDataTable();

						while (cellIterator.hasNext()) {
							// Get the Cell object
							Cell cell = cellIterator.next();

							// check the cell type and process accordingly
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:

								if (cell.getColumnIndex() == 2) {
									String englishDistrictName = cell.getStringCellValue().trim();
									System.out.println("District Name=" + englishDistrictName);
									districtMasterDataTable.setEnglishLanguageTypeId(new BigDecimal(1));
									districtMasterDataTable.setEnglishDistrictName(englishDistrictName);
								}
								if (cell.getColumnIndex() == 3) {
									String localDistrictName = cell.getStringCellValue().trim();
									System.out.println("District Local Name=" + localDistrictName);
									districtMasterDataTable.setLocalLanguageTypeId(new BigDecimal(2));
									districtMasterDataTable.setLocalDistrictName(localDistrictName);
								}
								
								if (cell.getColumnIndex() == 0) {
									String d = cell.getStringCellValue().trim();
									System.out.println("State Code =" + d);
									districtMasterDataTable.setStateCode(d);
								}
								if (cell.getColumnIndex() == 1) {
									String d = cell.getStringCellValue().trim();
									System.out.println("District Code =" + d);
									districtMasterDataTable.setDistrictCode(d);
								}
								

								break;
						/*	case Cell.CELL_TYPE_NUMERIC:
								if (cell.getColumnIndex() == 1) {
									Double d = cell.getNumericCellValue();// .trim();
									System.out.println("District Code =" + d);
									districtMasterDataTable.setDistrictCode(new BigDecimal(d.intValue()).toPlainString());
								}*/

								//break;
							case Cell.CELL_TYPE_BLANK:

							}

						}

						List<StateMaster> objList = getiDistrictMasterService().getStateListBasedOnCountryId(getCountryId(), districtMasterDataTable.getStateCode());
						
						if(objList != null){
							
							for(StateMaster stateMaster : objList){
								List<StateMasterDesc> stateList = getiDistrictMasterService().getStateNameBasedOnId(stateMaster.getStateId());

								if (stateList != null) {
									districtMasterDataTable.setStateName(stateList.get(0).getStateName());
									districtMasterDataTable.setStateId(stateList.get(0).getFsStateMaster().getStateId());

								}
							}
							
							
						}
					
						

						System.out.println("getDistrictId() === > " + districtMasterDataTable.getDistrictId());
						List<DistrictMasterDesc> districtMasterDescList = getiDistrictMasterService().getDistrictListBasedOnDistrictCode(districtMasterDataTable.getDistrictCode(),districtMasterDataTable.getStateCode(),districtMasterDataTable.getEnglishDistrictName());

						if (districtMasterDescList.size() > 0) {
							districtMasterDataTable.setDynamicLabelForActivateDeactivate("Duplicate");
						} else {
							districtMasterDataTable.setDynamicLabelForActivateDeactivate("New Record");
							districtMasterDataTable.setDistrictId(null);

						}

						districtMasterDataTable.setCreatedBy(sessionStateManage.getUserName());
						districtMasterDataTable.setCreationDate(new Date());
						districtMasterDataTable.setDistrictActive(Constants.U);
						// districtMasterDataTable.setCityStatus("Y");

						lstDistrictMasterDataTables.add(districtMasterDataTable);
						setBooDistrictDetails(true);

					} // end of rows iterator

				} // end of sheets for loop

			}  catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::uploadtoDatatable");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		} else {
			RequestContext.getCurrentInstance().execute("uploadFileEmpty.show();");
			return;
		}
	}

	public void exportExcel(FileUploadEvent event) throws IOException, SerialException, SQLException {
		uploadedFile = event.getFile();
		uploadedFile.getFileName();

	}

	public void clearDataTable() {
		lstDistrictMasterDataTables.clear();
		setBooDistrictDetails(false);
		districtMasterListDt.clear();

	}

	// duplicate checking
	public void checkDistrictCode() {
		LOGGER.info("Entering into checkStateCode method");
		try{
		List<DistrictMaster> exist = getiDistrictMasterService().getDistrictBasedOnDistrictCode(getDistrictCode(), getStateId());
		if (exist != null) {
			setDistrictCode(null);
			RequestContext.getCurrentInstance().execute("datatable.show();");
			return;
		}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::checkDistrictCode");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		LOGGER.info("Exit into checkStateCode method");
	}

	public void checkEnglishDistrictDescription() {

		try{
		List<DistrictMaster> exist = getiDistrictMasterService().getDistrictBasedOnDistrictCode(getDistrictCode(), getStateId());
		if (exist != null) {
			List<DistrictMasterDesc> english = getiDistrictMasterService().checkEnglishDescription(getEnglishDistrictName(), exist.get(0).getDistrictId());
			if (english != null && english.size() > 0) {
				// alreadyDB = true;
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("EnglishDescriptionExist.show();");
				setEnglishDistrictName(null);
				setLocalDistrictName(null);
			}
		}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::checkEnglishDistrictDescription");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void cancelRemarks() {
		setRemarks(null);

	}

	private List<BigDecimal> lstApproved = new CopyOnWriteArrayList<BigDecimal>();
	Boolean selectAll = false;
	
	
	
	public Boolean getSelectAll() {
		return selectAll;
	}

	public void setSelectAll(Boolean selectAll) {
		this.selectAll = selectAll;
	}

	public void selecatAndDeselectAll(){
		if(selectAll){
			for(DistrictMasterDataTable districtMasterDataTable : lstDistrictMasterDataTables){
				if(!districtMasterDataTable.getDisableCheck()){
					districtMasterDataTable.setIsCheck(true);
					lstApproved.add(districtMasterDataTable.getDistrictId());
				}
			}
		}else{
			for(DistrictMasterDataTable districtMasterDataTable : lstDistrictMasterDataTables){
				districtMasterDataTable.setIsCheck(false);
			}
			if(lstApproved != null && !lstApproved.isEmpty()){
				lstApproved.clear();
			}
		}
	}
	
	public void addingServiceAppApprovalRecord(DistrictMasterDataTable districtMasterDataTable){

		if(districtMasterDataTable.getIsCheck()){
				lstApproved.add(districtMasterDataTable.getDistrictId());
		}else{
			for (BigDecimal selectedRec : lstApproved) {
				if(selectedRec.compareTo(districtMasterDataTable.getDistrictId())==0){
					lstApproved.remove(selectedRec);
				}
			}
		}
	}



public void approvalAllRecords(){
	try{
	if(lstDistrictMasterDataTables.size() != 0){
		String list = "Fail";
		if(lstApproved.size() != 0){
			list=getiDistrictMasterService().approveRecord(lstApproved,sessionStateManage.getUserName());
			if(list.equalsIgnoreCase("Success")){
				RequestContext.getCurrentInstance().execute("approvedsucc.show();");
				if(lstApproved != null && !lstApproved.isEmpty()){
					lstApproved.clear();
				}
				return;
			}else{
				RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				return;
			}
		}

	}
	} catch (NullPointerException ne) {
		// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		setErrorMessage("Method Name::approvalAllRecords");
		RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		return;
	} catch (Exception exception) {
		// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		setErrorMessage(exception.getMessage());
		RequestContext.getCurrentInstance().execute("error.show();");
		return;
	}
}

public boolean isBooReadOnly() {
	return booReadOnly;
}

public void setBooReadOnly(boolean booReadOnly) {
	this.booReadOnly = booReadOnly;
}
	
}
