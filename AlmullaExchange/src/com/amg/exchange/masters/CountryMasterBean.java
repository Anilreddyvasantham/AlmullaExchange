package com.amg.exchange.masters;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.registration.service.ICountryMasterservice;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("countrymaster")
@Scope("session")
public class CountryMasterBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CountryMasterBean.class);
	private int selectType;
	private BigDecimal countryId;
	private BigDecimal ecountryDescId;
	private BigDecimal lcountryDescId;
	private String countryCode;
	private String isoCode;
	private String englishDescption;
	private String localDescption;
	private String localNationality;
	private String englishNationality;
	private BigDecimal elangid;
	private BigDecimal llangid;
	private String businessCountry;
	private String stateStatus;
	private String telCode;
	private String countryAlpha2Code;
	private String countryAlpha3Code;
	private String isActive;
	private Date modifiedDate;
	private String modifiedBy;
	private Date approvedDate;
	private String approvedBy;
	private Date createdDate;
	private String createdBy;
	private Boolean booCountryCodeReadOnly;
	private boolean hideEdit;
	private boolean ispopulate;
	private Boolean booDatatableEnabled;
	private Boolean booSubmitButton;
	private String remarks;
	private String dynamicLabelForActivateDeactivate;
	private Boolean editFalg;
	private String errorMessage;
	private Boolean isModifiedRecord;
	private SessionStateManage session = new SessionStateManage();
	CountryMaster master = null;
	CountryMasterDesc desc = null;
	
	private Boolean booDatatableApprovalEnabled;
	private Boolean permanetDeleteCheck;
	// for upload
	protected Boolean booDtEnabled;
	
	private BigDecimal spilitIndicator;
	
	
	
	List<CountryMasterDesc> countryDescList = new ArrayList<CountryMasterDesc>();
	List<CountryMaster> countryMasterList = new ArrayList<CountryMaster>();
	List<CountryMasterDataTable> englishcountryList = new CopyOnWriteArrayList<CountryMasterDataTable>();
	List<CountryMasterDataTable> localcountryList = new CopyOnWriteArrayList<CountryMasterDataTable>();
	List<CountryMasterDataTable> countrymasterDataTableList = new ArrayList<CountryMasterDataTable>();
	List<CountryMasterDataTable> countrymasterDataTableApprovalList = new ArrayList<CountryMasterDataTable>();
	List<CountryMasterDataTable> uploadrDataTableList = new ArrayList<CountryMasterDataTable>();

	
	@Autowired
	ICountryMasterservice iCountryMasterservice;
	
	
	
	public BigDecimal getSpilitIndicator() {
		return spilitIndicator;
	}

	public void setSpilitIndicator(BigDecimal spilitIndicator) {
		this.spilitIndicator = spilitIndicator;
	}

	public Boolean getPermanetDeleteCheck() {
		return permanetDeleteCheck;
	}

	public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
		this.permanetDeleteCheck = permanetDeleteCheck;
	}

	public Boolean getBooDtEnabled() {
		return booDtEnabled;
	}

	public void setBooDtEnabled(Boolean booDtEnabled) {
		this.booDtEnabled = booDtEnabled;
	}

	public List<CountryMasterDataTable> getCountrymasterDataTableApprovalList() {
		return countrymasterDataTableApprovalList;
	}

	public void setCountrymasterDataTableApprovalList(List<CountryMasterDataTable> countrymasterDataTableApprovalList) {
		this.countrymasterDataTableApprovalList = countrymasterDataTableApprovalList;
	}

	public Boolean getBooDatatableApprovalEnabled() {
		return booDatatableApprovalEnabled;
	}

	public void setBooDatatableApprovalEnabled(Boolean booDatatableApprovalEnabled) {
		this.booDatatableApprovalEnabled = booDatatableApprovalEnabled;
	}

	public BigDecimal getEcountryDescId() {
		return ecountryDescId;
	}

	public void setEcountryDescId(BigDecimal ecountryDescId) {
		this.ecountryDescId = ecountryDescId;
	}

	public BigDecimal getLcountryDescId() {
		return lcountryDescId;
	}

	public void setLcountryDescId(BigDecimal lcountryDescId) {
		this.lcountryDescId = lcountryDescId;
	}

	public Boolean getIsModifiedRecord() {
		return isModifiedRecord;
	}

	public void setIsModifiedRecord(Boolean isModifiedRecord) {
		this.isModifiedRecord = isModifiedRecord;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getElangid() {
		return elangid;
	}

	public void setElangid(BigDecimal elangid) {
		this.elangid = elangid;
	}

	public BigDecimal getLlangid() {
		return llangid;
	}

	public void setLlangid(BigDecimal llangid) {
		this.llangid = llangid;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getCountryAlpha2Code() {
		return countryAlpha2Code;
	}

	public void setCountryAlpha2Code(String countryAlpha2Code) {
		this.countryAlpha2Code = countryAlpha2Code;
	}

	public String getCountryAlpha3Code() {
		return countryAlpha3Code;
	}

	public void setCountryAlpha3Code(String countryAlpha3Code) {
		this.countryAlpha3Code = countryAlpha3Code;
	}

	public List<CountryMasterDataTable> getCountrymasterDataTableList() {
		return countrymasterDataTableList;
	}

	public void setCountrymasterDataTableList(List<CountryMasterDataTable> countrymasterDataTableList) {
		this.countrymasterDataTableList = countrymasterDataTableList;
	}

	public String getStateStatus() {
		return stateStatus;
	}

	public void setStateStatus(String stateStatus) {
		this.stateStatus = stateStatus;
	}

	public int getSelectType() {
		return selectType;
	}

	public void setSelectType(int selectType) {
		this.selectType = selectType;
	}

	public Boolean getBooCountryCodeReadOnly() {
		return booCountryCodeReadOnly;
	}

	public void setBooCountryCodeReadOnly(Boolean booCountryCodeReadOnly) {
		this.booCountryCodeReadOnly = booCountryCodeReadOnly;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getEnglishDescption() {
		return englishDescption;
	}

	public void setEnglishDescption(String englishDescption) {
		this.englishDescption = englishDescption;
	}

	public String getLocalDescption() {
		return localDescption;
	}

	public void setLocalDescption(String localDescption) {
		this.localDescption = localDescption;
	}

	public String getLocalNationality() {
		return localNationality;
	}

	public void setLocalNationality(String localNationality) {
		this.localNationality = localNationality;
	}

	public String getEnglishNationality() {
		return englishNationality;
	}

	public void setEnglishNationality(String englishNationality) {
		this.englishNationality = englishNationality;
	}

	public String getBusinessCountry() {
		return businessCountry;
	}

	public void setBusinessCountry(String businessCountry) {
		this.businessCountry = businessCountry;
	}

	public String getTelCode() {
		return telCode;
	}

	public void setTelCode(String telCode) {
		this.telCode = telCode;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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

	public boolean isHideEdit() {
		return hideEdit;
	}

	public void setHideEdit(boolean hideEdit) {
		this.hideEdit = hideEdit;
	}

	public boolean isIspopulate() {
		return ispopulate;
	}

	public void setIspopulate(boolean ispopulate) {
		this.ispopulate = ispopulate;
	}

	public Boolean getBooDatatableEnabled() {
		return booDatatableEnabled;
	}

	public void setBooDatatableEnabled(Boolean booDatatableEnabled) {
		this.booDatatableEnabled = booDatatableEnabled;
	}

	public Boolean getBooSubmitButton() {
		return booSubmitButton;
	}

	public void setBooSubmitButton(Boolean booSubmitButton) {
		this.booSubmitButton = booSubmitButton;
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

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getEditFalg() {
		return editFalg;
	}

	public void setEditFalg(Boolean editFalg) {
		this.editFalg = editFalg;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {
		editFalg = false;
		setBooCountryCodeReadOnly(false);
		setBooDatatableEnabled(null);
		clearAll();
		countrymasterDataTableList.clear();
		LOGGER.info("Entering into pageNavigation");
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "countrymaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/countrymaster.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().execute("ratioSelect.show();");
		LOGGER.info("Exit  into pageNavigation");
	}

	public void saveClick() {
		editFalg = false;
		setBooCountryCodeReadOnly(false);
		setBooDatatableEnabled(null);
		clearAll();
		countrymasterDataTableList.clear();
		LOGGER.info("Entering into pageNavigation");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/countrymasterManual.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().execute("ratioSelect.show();");
		LOGGER.info("Exit  into pageNavigation");
	}

	// TO POPULATE THE RELATION CODE BASED ON GIVEN ENTRY
	public List<String> autoCompleteData(String query) {
		if (query.length() > 0) {
			List<CountryMasterDesc> countryMasterList = iCountryMasterservice.getAllComponent(query, session.getLanguageId());
			List<String> list = new ArrayList<String>();
			for (CountryMasterDesc objCountryMaster : countryMasterList) {
				list.add(objCountryMaster.getFsCountryMaster().getCountryCode() + " - " + objCountryMaster.getCountryName());
			}
			return list;
		} else {
			return null;
		}
	}

	public void populateCountryMaster() {
		LOGGER.info("Enter into populateArticleMaster method : countryMasterBean ");
		LOGGER.info("Country Code " + getCountryCode());
		ispopulate = true;
		List<CountryMasterDesc> descList = null;
		if (getCountryCode() != null && !countryCode.equals("")) {
			
			String[] s = getCountryCode().split("-");
			if(s[0] != null){
				setCountryCode(s[0].trim().replaceAll(" +", " "));
			}
			
			master = iCountryMasterservice.viewByCode(getCountryCode());
			setIsoCode(master.getCountryIsoCode());
			setCountryAlpha2Code(master.getCountryAlpha2Code());
			setCountryAlpha3Code(master.getCountryAlpha3Code());
			setTelCode(master.getCountryTelCode());
			setBusinessCountry(master.getBusinessCountry());
			setSpilitIndicator(master.getSpilitIndicator());
			setStateStatus(master.getStateStatus());
			
			setTelCode(master.getCountryTelCode());
			LOGGER.info("Country ID " + master.getCountryId());
			if (master.getCountryId() != null) {
				descList = iCountryMasterservice.viewDescriptionsById(master.getCountryId());
				for (CountryMasterDesc countryMasterDesc : descList) {
					LOGGER.info("Langauage Type" + countryMasterDesc.getFsLanguageType().toString());
					if (countryMasterDesc.getFsLanguageType().getLanguageId().equals(Constants.ONE)) {
						setEnglishDescption(countryMasterDesc.getCountryName());
						setEnglishNationality(countryMasterDesc.getNationality());
					}
					if (countryMasterDesc.getFsLanguageType().getLanguageId().equals(new BigDecimal(Constants.TWO))) {
						setLocalDescption(countryMasterDesc.getCountryName());
						setLocalNationality(countryMasterDesc.getNationality());
					}
				}
			}
		}
		LOGGER.info("Exit into populateArticleMaster method : countryMasterBean ");
	}

	public void clearAll() {
		LOGGER.info("Entering into clearAll method : countryMasterBean ");
		setSelectType(0);
		setCountryId(null);
		setCountryCode(null);
		setEcountryDescId(null);
		setLcountryDescId(null);
		setCountryCode(null);
		setIsoCode(null);
		setCountryAlpha2Code(null);
		setCountryAlpha3Code(null);
		setSpilitIndicator(null);
		setTelCode(null);
		setBusinessCountry(null);
		setStateStatus(null);
		setTelCode(null);
		setEnglishDescption(null);
		setEnglishNationality(null);
		setLocalDescption(null);
		setLocalNationality(null);
		setErrorMessage(null);
		setIsActive(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setRemarks(null);
		ispopulate = false;
		hideEdit = false;
		LOGGER.info("Exit into clearAll method : countryMasterBean ");
	}

	public void clickOK() {
		LOGGER.info("Enter into clickOK method ");
		
		
		uploadrDataTableList.clear();
		setBooDtEnabled(false);
		
		if(getSelectType()==0)
		{
			RequestContext.getCurrentInstance().execute("selectany.show();");
		}
		
		if (getSelectType() == 1) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/countrymasterManual.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (getSelectType() == 2) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/countrymasterUpload.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("Exit  into clickOK method ");
	}
	
	public void viewAll(){
		
		try{
			countrymasterDataTableList.clear();
			
			setBooDatatableEnabled(true);
			setBooSubmitButton(true);
			setBooCountryCodeReadOnly(false);
		
		countryMasterList = iCountryMasterservice.getCountryMasterList();
		
		CountryMasterDataTable dataTable = new CountryMasterDataTable();
		
		if (countryMasterList != null && !countryMasterList.isEmpty()) {
			for (CountryMaster masterObj : countryMasterList) {
				//boolean added = false;
				dataTable = new CountryMasterDataTable();
				dataTable.setCountryCode(masterObj.getCountryCode());
				dataTable.setIsoCode(masterObj.getCountryIsoCode());
				dataTable.setCountryAlpha2Code(masterObj.getCountryAlpha2Code());
				dataTable.setCountryAlpha3Code(masterObj.getCountryAlpha3Code());
				dataTable.setBusinessCountry(changeyesNo(masterObj.getBusinessCountry()));
				//dataTable.setStateStatus(Constants.Yes);
				dataTable.setStateStatus(masterObj.getStateStatus());
				dataTable.setTelCode(masterObj.getCountryTelCode());
				dataTable.setCountryId(masterObj.getCountryId());
				dataTable.setSpilitIndicator(masterObj.getSpilitIndicator());
				/*dataTable.setCreatedBy(masterObj.getCreatedBy());
				dataTable.setCreatedDate(masterObj.getCreatedDate());
				dataTable.setModifiedBy(masterObj.getModifiedBy());
				dataTable.setModifiedDate(masterObj.getModifiedDate());
				dataTable.setRemarks(masterObj.getRemarks());
				dataTable.setIsActive(masterObj.getIsActive());
				dataTable.setApprovedBy(masterObj.getApprovedBy());
				dataTable.setApprovedDate(masterObj.getApprovedDate());;*/
				
				if(masterObj.getIsActive() != null && masterObj.getIsActive().equalsIgnoreCase(Constants.Yes)){
					dataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				}else if(masterObj.getIsActive() != null && masterObj.getIsActive().equalsIgnoreCase(Constants.D)){
					dataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				}else if(masterObj.getIsActive() != null && masterObj.getIsActive().equalsIgnoreCase(Constants.U)){
					dataTable.setDynamicLabelForActivateDeactivate("");
				}else{
					dataTable.setDynamicLabelForActivateDeactivate("");
				}
				
				//dataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
				/*if(dataTable.getDynamicLabelForActivateDeactivate().equals(Constants.DELETE)) {
					if(dataTable.getModifiedBy()==null && dataTable.getModifiedDate()==null )
					{
						
					}
					else
					{
						dataTable.setDynamicLabelForActivateDeactivate("");
					}
				}*/
				countryDescList = iCountryMasterservice.viewById(masterObj.getCountryId());//();
				LOGGER.info(countryDescList == null);
				if (countryDescList != null && countryDescList.size() != 0) {
					for (CountryMasterDesc desc : countryDescList) {
						if (desc.getFsLanguageType().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID ))) {
							dataTable.setEcountryDescId(desc.getCountryMasterId());
							dataTable.setEnglishDescption(desc.getCountryName());
							dataTable.setEnglishNationality(desc.getNationality());
							if(desc.getFsLanguageType() != null ){
								dataTable.setElangid(desc.getFsLanguageType().getLanguageId());
							}else{
								dataTable.setElangid(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID ));
							}
							
						} else if (desc.getFsLanguageType().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
							dataTable.setLcountryDescId(desc.getCountryMasterId());
							dataTable.setLocalDescption(desc.getCountryName());
							dataTable.setLocalNationality(desc.getNationality());
							if(desc.getFsLanguageType() != null ){
								dataTable.setLlangid(desc.getFsLanguageType().getLanguageId());
							}else{
								dataTable.setElangid(new BigDecimal(Constants.ARABIC_LANGUAGE_ID ));
							}
							
						}
					}
				} else {

				}
				countrymasterDataTableList.add(dataTable);
			}
		}
		setHideEdit(false);
		clearAll();
		LOGGER.info("Exit into view method");
		
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::view");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception e) {
			LOGGER.info("Exception occured while insert data in CountryMasterBean" + e);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("csp.show();");
			setErrorMessage(e.getMessage());
			return;
		}
	}

	public void view() {
		
		try{
		clearAll();
		setBooDatatableEnabled(true);
		setBooSubmitButton(true);
		countryDescList = iCountryMasterservice.getCountryList();
		englishcountryList.clear();
		localcountryList.clear();
		countrymasterDataTableList.clear();
		if (countryDescList != null && !countryDescList.isEmpty()) {
			LOGGER.info("Country List size " + countryDescList.size());
			CountryMasterDataTable one = null;
			for (CountryMasterDesc desc : countryDescList) {
				one = null;
				one = new CountryMasterDataTable();
				one.setCountryId(desc.getFsCountryMaster().getCountryId());
				// one.setCountryDescId(desc.getCountryMasterId());
				LOGGER.info("Country ID " + desc.getFsCountryMaster().getCountryId());
				if (desc.getFsLanguageType().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID ))) {
					one.setEcountryDescId(desc.getCountryMasterId());
					one.setEnglishDescption(desc.getCountryName());
					LOGGER.info("Country Name " + desc.getCountryName());
					one.setEnglishNationality(desc.getNationality());
					LOGGER.info("Nationality " + desc.getNationality());
					one.setElangid(desc.getFsLanguageType().getLanguageId());
					englishcountryList.add(one);
				} else if (desc.getFsLanguageType().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
					one.setLcountryDescId(desc.getCountryMasterId());
					one.setLocalDescption(desc.getCountryName());
					LOGGER.info("Local Country Name " + desc.getCountryName());
					one.setLocalNationality(desc.getNationality());
					LOGGER.info("Local Nationality " + desc.getNationality());
					one.setLlangid(desc.getFsLanguageType().getLanguageId());
					localcountryList.add(one);
				}
				desc = null;
			}
/*			for (CountryMasterDataTable local : localcountryList) {
				LOGGER.info("========" + local);
			}
			for (CountryMasterDataTable eng : englishcountryList) {
				LOGGER.info("========" + eng);
			}
*/			countryMasterList = iCountryMasterservice.getCountryMasterList();
			LOGGER.info("Master list size " + countryMasterList.size());
			LOGGER.info("English list size " + englishcountryList.size());
			LOGGER.info("Local list size " + localcountryList.size());
			CountryMasterDataTable dataTable = new CountryMasterDataTable();
			/*for (CountryMaster cm : countryMasterList) {
				LOGGER.info("getCountryId " + cm.getCountryId());
				LOGGER.info("getCountryAlpha2Code " + cm.getCountryAlpha2Code());
				LOGGER.info("getCountryAlpha3Code " + cm.getCountryAlpha3Code());
			}*/
			if (countryMasterList != null && !countryMasterList.isEmpty()) {
				for (CountryMaster masterObj : countryMasterList) {
					boolean added = false;
					dataTable = new CountryMasterDataTable();
					dataTable.setCountryCode(masterObj.getCountryCode());
					dataTable.setIsoCode(masterObj.getCountryIsoCode());
					dataTable.setCountryAlpha2Code(masterObj.getCountryAlpha2Code());
					dataTable.setCountryAlpha3Code(masterObj.getCountryAlpha3Code());
					dataTable.setBusinessCountry(changeyesNo(masterObj.getBusinessCountry()));
					dataTable.setStateStatus(masterObj.getStateStatus());
					dataTable.setTelCode(masterObj.getCountryTelCode());
					dataTable.setCountryId(masterObj.getCountryId());
					dataTable.setSpilitIndicator(masterObj.getSpilitIndicator());
					/*dataTable.setCreatedBy(masterObj.getCreatedBy());
					dataTable.setCreatedDate(masterObj.getCreatedDate());
					dataTable.setModifiedBy(masterObj.getModifiedBy());
					dataTable.setModifiedDate(masterObj.getModifiedDate());
					dataTable.setRemarks(masterObj.getRemarks());
					dataTable.setIsActive(masterObj.getIsActive());
					dataTable.setApprovedBy(masterObj.getApprovedBy());
					dataTable.setApprovedDate(masterObj.getApprovedDate());;*/
					dataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					if(dataTable.getDynamicLabelForActivateDeactivate().equals(Constants.DELETE)) {
						if(dataTable.getModifiedBy()==null && dataTable.getModifiedDate()==null )
						{
							
						}
						else
						{
							dataTable.setDynamicLabelForActivateDeactivate("");
						}
					}
					for (CountryMasterDataTable engObj : englishcountryList) {
						for (CountryMasterDataTable localObj : localcountryList) {
							int result = engObj.getCountryId().compareTo(localObj.getCountryId());
							LOGGER.info("engObj " + engObj.getCountryId());
							LOGGER.info("localObj " + localObj.getCountryId());
							LOGGER.info("Result value is " + result);
							if (result == 0) {
								LOGGER.info("Country id ====================================>" + engObj.getCountryId());
								dataTable.setEnglishDescption(engObj.getEnglishDescption());
								LOGGER.info(engObj.getEnglishDescption());
								dataTable.setEnglishNationality(engObj.getEnglishNationality());
								LOGGER.info(engObj.getEnglishNationality());
								dataTable.setElangid(engObj.getElangid());
								dataTable.setEcountryDescId(engObj.getEcountryDescId());
								dataTable.setLocalDescption(localObj.getLocalDescption());
								LOGGER.info(localObj.getLocalDescption());
								dataTable.setLocalNationality(localObj.getLocalNationality());
								LOGGER.info(localObj.getLocalDescption());
								dataTable.setLlangid(localObj.getLlangid());
								dataTable.setLcountryDescId(localObj.getLcountryDescId());
								englishcountryList.remove(engObj);
								localcountryList.remove(localObj);
								LOGGER.info("size of englishcountryList" + englishcountryList.size());
								LOGGER.info("size of localcountryList" + localcountryList.size());
								LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@" + dataTable);
								countrymasterDataTableList.add(dataTable);
								added = true;
								break;
							}
							localObj = null;
						}
						engObj = null;
						if (added) {
							break;
						}
					}
					dataTable = null;
				}
			}
		}
		setHideEdit(false);
		LOGGER.info("Exit into view method");
		
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::view");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception e) {
			LOGGER.info("Exception occured while insert data in CountryMasterBean" + e);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("csp.show();");
			setErrorMessage(e.getMessage());
			return;
		}
	}

	public void addtoDataTable() {
		
		try{
		LOGGER.info("Entering into addtoDataTable method");
		LOGGER.info("Entering into add method");
		boolean alreadyDT = false;
		// Data table duplicatecheck
		if (countrymasterDataTableList.size() != 0) {
			for (CountryMasterDataTable dataTable : countrymasterDataTableList) {
				// Data table duplicate check - article code check
				if (dataTable.getCountryCode() != null && dataTable.getCountryCode().equals(getCountryCode())) {
					setErrorMessage("Country Code already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
					alreadyDT = true;
					break;
				}
				// Data table duplicate check - English description code check
				if (dataTable.getIsoCode() != null && dataTable.getIsoCode().equalsIgnoreCase(getIsoCode())) {
					setErrorMessage("Country ISO Code already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
					alreadyDT = true;
					break;
				}
				// Data table duplicate check - local description code check
				if (dataTable.getEnglishDescption() != null && dataTable.getEnglishDescption().equalsIgnoreCase(getEnglishDescption())) {
					setErrorMessage("Country English Country already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
					alreadyDT = true;
					break;
				}
				if (dataTable.getLocalDescption() != null && dataTable.getLocalDescption().equalsIgnoreCase(getLocalDescption())) {
					setErrorMessage("Country local Country already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
					alreadyDT = true;
					break;
				}
				if (dataTable.getEnglishNationality() != null && dataTable.getEnglishNationality().equalsIgnoreCase(getEnglishNationality())) {
					setErrorMessage("Country English Nationality already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
					alreadyDT = true;
					break;
				}
				if (dataTable.getLocalNationality() != null && dataTable.getLocalNationality().equalsIgnoreCase(getLocalNationality())) {
					setErrorMessage("Country Local Nationality already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
					alreadyDT = true;
					break;
				}
				if (dataTable.getCountryAlpha2Code() != null && dataTable.getCountryAlpha2Code().equalsIgnoreCase(getCountryAlpha2Code())) {
					setErrorMessage("Country Alpha2 Code already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
					alreadyDT = true;
					break;
				}
				if (dataTable.getCountryAlpha3Code() != null && dataTable.getCountryAlpha3Code().equalsIgnoreCase(getCountryAlpha3Code())) {
					setErrorMessage("Country Alpha3 Code already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
					alreadyDT = true;
					break;
				}
				if (dataTable.getTelCode() != null && dataTable.getTelCode().equalsIgnoreCase(getTelCode())) {
					setErrorMessage("Country TeleCode Code already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
					alreadyDT = true;
					break;
				}
			}
		}
		boolean alreadyDB = false;
		// Data table duplicatecheck
		if (!alreadyDT) {
			if (getCountryId() == null) {
				String[] s = getCountryCode().split("-");
				if(s[0] != null){
					setCountryCode(s[0].trim().replaceAll(" +", " "));
				}
				CountryMaster exist = iCountryMasterservice.viewByCode(getCountryCode());
				boolean local = iCountryMasterservice.checkDesciption(getLocalDescption());
				boolean english = iCountryMasterservice.checkDesciption(getEnglishDescption());
				boolean localNation = iCountryMasterservice.checkNationality(getLocalNationality());
				boolean englishNation = iCountryMasterservice.checkNationality(getEnglishNationality());
				boolean isoCheck = iCountryMasterservice.isoCheck(getIsoCode());
				boolean alpha2Check = iCountryMasterservice.alpha2Check(getCountryAlpha2Code());
				boolean alpha3Check = iCountryMasterservice.alpha3Check(getCountryAlpha2Code());
				boolean TeleCodeCheck = iCountryMasterservice.TeleCodeCheck(getTelCode());
				if (exist != null && exist.getCountryId() != null) {
					alreadyDB = true;
					setErrorMessage("Country Code already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
				} else if (local) {
					alreadyDB = true;
					setErrorMessage("Country Local Nationality already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
				} else if (english) {
					alreadyDB = true;
					setErrorMessage("Country English Nationality already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
				} else if (localNation) {
					alreadyDB = true;
					setErrorMessage("Country Local Nationality already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
				} else if (englishNation) {
					alreadyDB = true;
					setErrorMessage("Country English Nationality already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
				} else if (isoCheck) {
					alreadyDB = true;
					setErrorMessage("Country ISO Code already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
				} else if (alpha2Check) {
					alreadyDB = true;
					setErrorMessage("Country Alpha2 Code already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
				} else if (alpha3Check) {
					alreadyDB = true;
					setErrorMessage("Country Alpha3 Code already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
				} else if (TeleCodeCheck) {
					alreadyDB = true;
					setErrorMessage("Country Telephone Code already Exist");
					RequestContext.getCurrentInstance().execute("csp.show();");
				} else {
				}
			}
		}
		if (!alreadyDT && !alreadyDB) {
			CountryMasterDataTable dataTable = new CountryMasterDataTable();
			dataTable.setCountryId(getCountryId());
			
			String[] s = getCountryCode().split("-");
			if(s[0] != null){
				setCountryCode(s[0].trim().replaceAll(" +", " "));
			}
			
			dataTable.setCountryCode(getCountryCode());
			dataTable.setCountryAlpha2Code(getCountryAlpha2Code());
			dataTable.setCountryAlpha3Code(getCountryAlpha3Code());
			dataTable.setBusinessCountry(changeyesNo(getBusinessCountry()));
			dataTable.setSpilitIndicator(getSpilitIndicator());
			
			dataTable.setStateStatus(getStateStatus());
			
			dataTable.setIsoCode(getIsoCode());
			dataTable.setTelCode(getTelCode());
			dataTable.setEnglishNationality(getEnglishNationality());
			dataTable.setEnglishDescption(getEnglishDescption());
			dataTable.setLocalDescption(getLocalDescption());
			dataTable.setLocalNationality(getLocalNationality());
			dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(getIsActive()));
			if(dataTable.getDynamicLabelForActivateDeactivate().equals(Constants.DELETE)) {
				if(dataTable.getModifiedBy()==null && dataTable.getModifiedDate()==null )
				{
					
				}
				else
				{
					dataTable.setDynamicLabelForActivateDeactivate("");
				}
			}
			
			// In first Time
			if (!editFalg && !ispopulate) {
				dataTable.setElangid(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID ));
				dataTable.setLlangid(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
				dataTable.setCreatedBy(session.getUserName());
				dataTable.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				dataTable.setIsActive(Constants.U);
				dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(null));
			} else {
				if(getElangid() != null){
					dataTable.setElangid(getElangid());
				}else{
					dataTable.setElangid(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID ));
				}
				if(getLlangid() != null){
					dataTable.setLlangid(getLlangid());
				}else{
					dataTable.setLlangid(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
				}
				dataTable.setCreatedBy(getCreatedBy());
				dataTable.setCreatedDate(getCreatedDate());
				dataTable.setIsActive(getIsActive());
				dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(getIsActive()));
				if(dataTable.getDynamicLabelForActivateDeactivate().equals(Constants.DELETE)) {
					if(dataTable.getModifiedBy()==null && dataTable.getModifiedDate()==null )
					{
						
					}
					else
					{
						dataTable.setDynamicLabelForActivateDeactivate("");
					}
				}
			}
			dataTable.setIsModifiedRecord(true);
			dataTable.setModifiedBy(getModifiedBy());
			dataTable.setModifiedDate(getModifiedDate());
			dataTable.setApprovedDate(getApprovedDate());
			dataTable.setApprovedBy(getApprovedBy());
			dataTable.setRemarks(getRemarks());
			dataTable.setEcountryDescId(getEcountryDescId());
			dataTable.setLcountryDescId(getLcountryDescId());
			LOGGER.info("Entered value is ");
			LOGGER.info(dataTable);
			countrymasterDataTableList.add(0, dataTable);
			setBooDatatableEnabled(true);
			setBooSubmitButton(false);
			// setBooArticleCodeReadOnly(false);
			clearAll();
		}
		setHideEdit(false);
		setBooCountryCodeReadOnly(false);
		LOGGER.info("Exit into addtoDataTable method");
		
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::addTodataTable");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception e) {
			LOGGER.info("Exception occured while addToDataTable in CountryMasterBean" + e);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("csp.show();");
			setErrorMessage(e.getMessage());
			return;
		}
	}

	public void saveAll() {
		try{
		LOGGER.info("Entering into saveAll method");
		CountryMaster master = null;
		CountryMasterDesc desc = null;
		HashMap<String, Object> saveObject = new HashMap<String, Object>();
		List<CountryMaster> saveMasterList = new CopyOnWriteArrayList<CountryMaster>();
		List<CountryMasterDesc> saveDescList = new CopyOnWriteArrayList<CountryMasterDesc>();
		for (CountryMasterDataTable dataTable : countrymasterDataTableList) {
			master = null;
			desc = null;
			master = new CountryMaster();
			desc = new CountryMasterDesc();
			LOGGER.info("Input values *************************************************");
			LOGGER.info(dataTable);
			LOGGER.info("Input values *************************************************");
			master.setCountryId(dataTable.getCountryId());
			master.setCountryCode(dataTable.getCountryCode());
			master.setCountryAlpha2Code(dataTable.getCountryAlpha2Code());
			master.setCountryAlpha3Code(dataTable.getCountryAlpha3Code());
			master.setBusinessCountry(changeyesNo(dataTable.getBusinessCountry()));
			master.setStateStatus(dataTable.getStateStatus());
			master.setCountryIsoCode(dataTable.getIsoCode());
			master.setCountryTelCode(dataTable.getTelCode());
			master.setCreatedBy(session.getUserName());
			master.setCreatedDate(new Date());
			master.setIsActive(Constants.U);
			master.setSpilitIndicator(dataTable.getSpilitIndicator());
			
			/*master.setRemarks(dataTable.getRemarks());
			master.setCreatedBy(dataTable.getCreatedBy());
			master.setCreatedDate(dataTable.getCreatedDate());
			master.setModifiedBy(dataTable.getModifiedBy());
			master.setModifiedDate(dataTable.getModifiedDate());
			master.setApprovedBy(dataTable.getApprovedBy());
			master.setApprovedDate(dataTable.getApprovedDate());
			master.setIsActive(dataTable.getIsActive());*/
			master.setCountryActive(dataTable.getIsActive());
			if (dataTable.getIsModifiedRecord() != null && dataTable.getIsModifiedRecord()) {
				saveMasterList.add(master);
				for (int i = 0; i < 2; i++) {
					desc = null;
					desc = new CountryMasterDesc();
					desc.setFsCountryMaster(master);
					if (i == 0) {
						desc.setCountryName(dataTable.getEnglishDescption());
						desc.setNationality(dataTable.getEnglishNationality());
						desc.setCountryMasterId(dataTable.getEcountryDescId());
						LanguageType ltype = new LanguageType();
						if(dataTable.getElangid() != null){
							ltype.setLanguageId(dataTable.getElangid());
						}else{
							ltype.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID ));
						}
						
						desc.setFsLanguageType(ltype);
						desc.setNationality(dataTable.getEnglishNationality());
					}
					if (i == 1) {
						desc.setCountryName(dataTable.getLocalDescption());
						desc.setNationality(dataTable.getLocalNationality());
						desc.setCountryMasterId(dataTable.getLcountryDescId());
						LanguageType ltype = new LanguageType();
						if(dataTable.getLlangid() != null){
							ltype.setLanguageId(dataTable.getLlangid());
						}else{
							ltype.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
						}
						
						desc.setFsLanguageType(ltype);
						desc.setNationality(dataTable.getLocalNationality());
					}
					if (dataTable.getIsModifiedRecord()) {
						saveDescList.add(desc);
					}
				}
			}
		}
		if (!saveMasterList.isEmpty()) {
			saveObject.put("master", saveMasterList);
			saveObject.put("desc", saveDescList);
			try {
				
				iCountryMasterservice.save(saveObject);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("succsses.show();");
			} catch (Exception e) {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("failure.show();");
			}
		}
		LOGGER.info("Exit into saveAll method");
		
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::save");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception e) {
			LOGGER.info("Exception occured while insert data in CountryMasterBean" + e);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("csp.show();");
			setErrorMessage(e.getMessage());
			return;
		}
	}

	@Override
	public String toString() {
		return "CountryMasterBean [selectType=" + selectType + ", countryId=" + countryId + ", ecountryDescId=" + ecountryDescId + ", lcountryDescId=" + lcountryDescId + ", countryCode=" + countryCode + ", isoCode=" + isoCode + ", englishDescption=" + englishDescption + ", localDescption="
				+ localDescption + ", localNationality=" + localNationality + ", englishNationality=" + englishNationality + ", elangid=" + elangid + ", llangid=" + llangid + ", businessCountry=" + businessCountry + ", stateStatus=" + stateStatus + ", telCode=" + telCode + ", countryAlpha2Code="
				+ countryAlpha2Code + ", countryAlpha3Code=" + countryAlpha3Code + ", isActive=" + isActive + ", modifiedDate=" + modifiedDate + ", modifiedBy=" + modifiedBy + ", approvedDate=" + approvedDate + ", approvedBy=" + approvedBy + ", createdDate=" + createdDate + ", createdBy="
				+ createdBy + ", booCountryCodeReadOnly=" + booCountryCodeReadOnly + ", hideEdit=" + hideEdit + ", ispopulate=" + ispopulate + ", booDatatableEnabled=" + booDatatableEnabled + ", booSubmitButton=" + booSubmitButton + ", remarks=" + remarks + ", dynamicLabelForActivateDeactivate="
				+ dynamicLabelForActivateDeactivate + ", editFalg=" + editFalg + ", errorMessage=" + errorMessage + ", isModifiedRecord=" + isModifiedRecord + ", booDatatableApprovalEnabled=" + booDatatableApprovalEnabled + "]";
	}

	private String setreverActiveStatus(String status) {
		LOGGER.info("Entering into setreverActiveStatus method");
		String strStatus = null;
		if (status == null) {
			strStatus = Constants.REMOVE;
		} else if (status.equalsIgnoreCase(Constants.U)) {
			//strStatus = Constants.DELETE;
			strStatus = "";
		} else if (status.equalsIgnoreCase(Constants.D)) {
			strStatus = Constants.ACTIVATE;
		} else if (status.equalsIgnoreCase(Constants.Yes)) {
			strStatus = Constants.DEACTIVATE;
		}
		LOGGER.info("strStatus " + strStatus);
		LOGGER.info("Exit into setreverActiveStatus method");
		return strStatus;
	}

	public void editRecord(CountryMasterDataTable dataTable) {
		
		try{
		LOGGER.info("Entering into editRecord method");
		setEditFalg(true);
		setHideEdit(true);
		setCountryId(dataTable.getCountryId());
		setIsoCode(dataTable.getIsoCode());
		setEnglishDescption(dataTable.getEnglishDescption());
		setLocalDescption(dataTable.getLocalDescption());
		setEnglishNationality(dataTable.getEnglishNationality());
		setLocalNationality(dataTable.getLocalNationality());
		setCountryAlpha2Code(dataTable.getCountryAlpha2Code());
		setCountryAlpha3Code(dataTable.getCountryAlpha3Code());
		setCountryCode(dataTable.getCountryCode());
		setBusinessCountry(changeyesNo(dataTable.getBusinessCountry()));
		setStateStatus(dataTable.getStateStatus());	
		setSpilitIndicator(dataTable.getSpilitIndicator());
		
		
		
		
		setTelCode(dataTable.getTelCode());
		setApprovedBy(null);
		setApprovedDate(null);
		setRemarks(null);
		if (dataTable.getCountryId() == null) {
			setModifiedBy(null);
			setModifiedDate(null);
			editFalg = false;
		} else {
			setModifiedBy(session.getUserName());
			setModifiedDate(new Date());
			setBooCountryCodeReadOnly(true);
		}
		setIsActive(Constants.U);
		setCreatedDate(dataTable.getCreatedDate());
		setCreatedBy(dataTable.getCreatedBy());
		setElangid(dataTable.getElangid());
		setLlangid(dataTable.getLlangid());
		setEcountryDescId(dataTable.getEcountryDescId());
		setLcountryDescId(dataTable.getLcountryDescId());
		countrymasterDataTableList.remove(dataTable);
		LOGGER.info("Exit into editRecord method");
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::save");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception e) {
			LOGGER.info("Exception occured while edit data in CountryMasterBean" + e);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("csp.show();");
			setErrorMessage(e.getMessage());
			return;
		}
		
	}

	public void checkStatus(CountryMasterDataTable dataTable) {
		LOGGER.info("Entering into checkStatus method");
		if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			dataTable.setRemarksCheck(true);
			LOGGER.info("Approved By" + dataTable.getApprovedBy());
			LOGGER.info("Approved Date" + dataTable.getApprovedDate());
			setApprovedBy(dataTable.getApprovedBy());
			setApprovedDate(dataTable.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarksMsg.show();");
		} else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null && dataTable.getApprovedBy() == null && dataTable.getApprovedDate() == null && dataTable.getRemarks() == null) {
			dataTable.setPermanetDeleteCheck(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			return;
		} else {
			removefromDataTable(dataTable);
		}
		LOGGER.info("Exit into checkStatus method");
	}

	public void removefromDataTable(CountryMasterDataTable dataTable) {
		
		try{
		LOGGER.info("Entering into removefromDataTable method");
		if ((dataTable.getDynamicLabelForActivateDeactivate() != null && (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)) || dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE))) {
			if (dataTable.getCountryId() != null) {
				master = new CountryMaster();
				master.setCountryId(dataTable.getCountryId());
				if (dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null && dataTable.getApprovedBy() == null && dataTable.getApprovedDate() == null && dataTable.getRemarks() == null) {
					countrymasterDataTableList.remove(dataTable);
					try {
						List<CountryMasterDesc> list = iCountryMasterservice.viewById(master.getCountryId());
						List<CountryMasterDesc> countryDescList = new ArrayList<CountryMasterDesc>();
						for (CountryMasterDesc desc : list) {
							countryDescList.add(desc);
						}
						iCountryMasterservice.delete(master, countryDescList);
						RequestContext.getCurrentInstance().execute("deleteSuccess.show();");
					} catch (Exception e) {
						LOGGER.info("Exception occured while deleting the record " + e);
						RequestContext.getCurrentInstance().execute("deleteFailure.show();");
					}
				} else {
					RequestContext.getCurrentInstance().execute("alreadymodifed.show();");
				}
			} else {
				countrymasterDataTableList.remove(dataTable);
				if (countrymasterDataTableList.size() == 0) {
					setBooDatatableEnabled(false);
					clearAll();
				}
			}
		} else if ((dataTable.getDynamicLabelForActivateDeactivate() != null && (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) || dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE))) {
			CountryMaster master = null;
			CountryMasterDesc desc = null;
			LOGGER.info("Input values *************************************************");
			LOGGER.info(dataTable);
			LOGGER.info("Input values *************************************************");
			master = new CountryMaster();
			master.setCountryId(dataTable.getCountryId());
			master.setCountryCode(dataTable.getCountryCode());
			master.setCountryAlpha2Code(dataTable.getCountryAlpha2Code());
			master.setCountryAlpha3Code(dataTable.getCountryAlpha3Code());
			master.setCountryIsoCode(dataTable.getIsoCode());
			master.setCountryTelCode(dataTable.getTelCode());
			master.setBusinessCountry(changeyesNo(dataTable.getBusinessCountry()));
			master.setStateStatus(dataTable.getStateStatus());
			/*master.setIsActive(dataTable.getIsActive());*/
			master.setCountryActive(dataTable.getIsActive());
			master.setIsActive(dataTable.getIsActive());
			master.setSpilitIndicator(dataTable.getSpilitIndicator());
			/*master.setCreatedBy(dataTable.getCreatedBy());
			master.setCreatedDate(dataTable.getCreatedDate());
			master.setModifiedBy(dataTable.getModifiedBy());
			master.setModifiedDate(dataTable.getModifiedDate());
			master.setApprovedBy(dataTable.getApprovedBy());
			master.setApprovedDate(dataTable.getApprovedDate());
			master.setRemarks(dataTable.getRemarks());*/
			if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				dataTable.setIsActive(Constants.U);
				/*master.setIsActive(Constants.U);
				master.setCountryActive(Constants.U);
				master.setRemarks(null);
				master.setApprovedBy(null);
				master.setApprovedDate(null);
				master.setModifiedBy(session.getUserName());
				master.setModifiedDate(new Date());
				master.setModifiedDate(dataTable.getModifiedDate());*/
				//countrymasterDataTableList.remove(dataTable);
				dataTable.setDynamicLabelForActivateDeactivate("");
			} else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				dataTable.setIsActive(Constants.D);
				/*master.setIsActive(Constants.D);
				master.setCountryActive(Constants.D);
				dataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				master.setModifiedBy(session.getUserName());
				master.setModifiedDate(new Date());*/
			}
			try {
				List<CountryMasterDesc> saveDescList = new CopyOnWriteArrayList<CountryMasterDesc>();
				for (int i = 0; i < 2; i++) {
					desc = null;
					desc = new CountryMasterDesc();
					desc.setFsCountryMaster(master);
					if (i == 0) {
						desc.setCountryName(dataTable.getEnglishDescption());
						desc.setNationality(dataTable.getEnglishNationality());
						desc.setCountryMasterId(dataTable.getEcountryDescId());
						LanguageType ltype = new LanguageType();
						if(dataTable.getElangid() != null){
							ltype.setLanguageId(dataTable.getElangid());
						}else{
							ltype.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID ));
						}
						desc.setFsLanguageType(ltype);
						desc.setNationality(dataTable.getEnglishNationality());
					}
					if (i == 1) {
						desc.setCountryName(dataTable.getLocalDescption());
						desc.setNationality(dataTable.getLocalNationality());
						desc.setCountryMasterId(dataTable.getLcountryDescId());
						LanguageType ltype = new LanguageType();
						if(dataTable.getLlangid() != null){
							ltype.setLanguageId(dataTable.getLlangid());
						}else{
							ltype.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
						}
						desc.setFsLanguageType(ltype);
						desc.setNationality(dataTable.getEnglishNationality());
					}
					saveDescList.add(desc);
				}
				List<CountryMaster> saveMasterList = new CopyOnWriteArrayList<CountryMaster>();
				saveMasterList.add(master);
				HashMap<String, Object> saveObject = new HashMap<String, Object>();
				saveObject.put("master", saveMasterList);
				saveObject.put("desc", saveDescList);
				iCountryMasterservice.save(saveObject);
				viewAll();
			} catch (Exception e) {
				LOGGER.info("Exception occured while insert data in ArticleMasterBean" + e);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("failure.show();");
			}
		}
		
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::removefromDatatable");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception e) {
			LOGGER.info("Exception occured while remove from datatable data in CountryMasterBean" + e);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("csp.show();");
			setErrorMessage(e.getMessage());
			return;
		}
	}

	public void clickOnExit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void clickRemarkOK() throws IOException {
		LOGGER.info("Entering into clickOK method");
		LOGGER.info(getRemarks());
		if (getRemarks() != null && !getRemarks().equals("")) {
			for (CountryMasterDataTable dataTable : countrymasterDataTableList) {
				if (dataTable.getRemarksCheck() != null) {
					if (dataTable.getRemarksCheck().equals(true)) {
						dataTable.setRemarks(getRemarks());
						dataTable.setIsActive(Constants.D);
						/* dataTable.setIsModifiedRecord(true); */
						removefromDataTable(dataTable);
						setRemarks(null);
					}
				}
			}
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/countrymasterManual.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		}
		LOGGER.info("Exit into clickOK method");
	}

	/*************************************************************/
	// Approval screen.
	public void pageApprovalNavigation() {
		editFalg = false;
		LOGGER.info("Entering into pageApprovalNavigation method");
		setBooDatatableApprovalEnabled(false);
		countrymasterDataTableApprovalList.clear();
		viewApproval();
		if (session.getUserName() == null) {
		}
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "countrymasterapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/countrymasterapproval.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit  into pageApprovalNavigation method");
	}

	public void viewApproval() {		
		try{
		LOGGER.info("Entering into viewApproval method ");
		setBooDatatableEnabled(true);
		countryDescList = iCountryMasterservice.getunApproveCountryList();
		englishcountryList.clear();
		localcountryList.clear();
		countrymasterDataTableApprovalList.clear();
		if (countryDescList != null && !countryDescList.isEmpty()) {
			LOGGER.info("Country List size " + countryDescList.size());
			CountryMasterDataTable one = null;
			for (CountryMasterDesc desc : countryDescList) {
				one = null;
				one = new CountryMasterDataTable();
				one.setCountryId(desc.getFsCountryMaster().getCountryId());
				// one.setCountryDescId(desc.getCountryMasterId());
				LOGGER.info("Country ID " + desc.getFsCountryMaster().getCountryId());
				if (desc.getFsLanguageType().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID ))) {
					one.setEcountryDescId(desc.getCountryMasterId());
					one.setEnglishDescption(desc.getCountryName());
					LOGGER.info("Country Name " + desc.getCountryName());
					one.setEnglishNationality(desc.getNationality());
					LOGGER.info("Nationality " + desc.getNationality());
					one.setElangid(desc.getFsLanguageType().getLanguageId());
					englishcountryList.add(one);
				} else if (desc.getFsLanguageType().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
					one.setLcountryDescId(desc.getCountryMasterId());
					one.setLocalDescption(desc.getCountryName());
					LOGGER.info("Local Country Name " + desc.getCountryName());
					one.setLocalNationality(desc.getNationality());
					LOGGER.info("Local Nationality " + desc.getNationality());
					one.setLlangid(desc.getFsLanguageType().getLanguageId());
					localcountryList.add(one);
				}
				desc = null;
			}
			for (CountryMasterDataTable local : localcountryList) {
				LOGGER.info("========" + local);
			}
			for (CountryMasterDataTable eng : englishcountryList) {
				LOGGER.info("========" + eng);
			}
			countryMasterList = iCountryMasterservice.getCountryMasterListforApproval();
			LOGGER.info("Master list size " + countryMasterList.size());
			LOGGER.info("English list size " + englishcountryList.size());
			LOGGER.info("Local list size " + localcountryList.size());
			CountryMasterDataTable dataTable = new CountryMasterDataTable();
			for (CountryMaster cm : countryMasterList) {
				LOGGER.info("getCountryId " + cm.getCountryId());
				LOGGER.info("getCountryAlpha2Code " + cm.getCountryAlpha2Code());
				LOGGER.info("getCountryAlpha3Code " + cm.getCountryAlpha3Code());
				LOGGER.info("Country Code " + cm.getCountryCode());
			}
			if (countryMasterList != null && !countryMasterList.isEmpty()) {
				for (CountryMaster masterObj : countryMasterList) {
					boolean added = false;
					dataTable = new CountryMasterDataTable();
					dataTable.setCountryCode(masterObj.getCountryCode());
					dataTable.setIsoCode(masterObj.getCountryIsoCode());
					dataTable.setCountryAlpha2Code(masterObj.getCountryAlpha2Code());
					dataTable.setCountryAlpha3Code(masterObj.getCountryAlpha3Code());
					dataTable.setBusinessCountry(changeyesNo(masterObj.getBusinessCountry()));
					dataTable.setSpilitIndicator(masterObj.getSpilitIndicator());
					dataTable.setStateStatus(changeyesNo(masterObj.getStateStatus()));
					dataTable.setTelCode(masterObj.getCountryTelCode());
					dataTable.setCountryId(masterObj.getCountryId());
					/*dataTable.setCreatedBy(masterObj.getCreatedBy());
					dataTable.setCreatedDate(masterObj.getCreatedDate());
					dataTable.setModifiedBy(masterObj.getModifiedBy());
					dataTable.setModifiedDate(masterObj.getModifiedDate());
					dataTable.setRemarks(masterObj.getRemarks());
					dataTable.setIsActive(masterObj.getIsActive());
					dataTable.setApprovedBy(masterObj.getApprovedBy());
					dataTable.setApprovedDate(masterObj.getApprovedDate());
					dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(masterObj.getIsActive()));*/
					for (CountryMasterDataTable engObj : englishcountryList) {
						for (CountryMasterDataTable localObj : localcountryList) {
							int result = engObj.getCountryId().compareTo(localObj.getCountryId());
							LOGGER.info("engObj " + engObj.getCountryId());
							LOGGER.info("localObj " + localObj.getCountryId());
							LOGGER.info("Result value is " + result);
							if (result == 0) {
								LOGGER.info("Country id ====================================>" + engObj.getCountryId());
								dataTable.setEnglishDescption(engObj.getEnglishDescption());
								LOGGER.info(engObj.getEnglishDescption());
								dataTable.setEnglishNationality(engObj.getEnglishNationality());
								LOGGER.info(engObj.getEnglishNationality());
								dataTable.setElangid(engObj.getElangid());
								dataTable.setEcountryDescId(engObj.getEcountryDescId());
								dataTable.setLocalDescption(localObj.getLocalDescption());
								LOGGER.info(localObj.getLocalDescption());
								dataTable.setLocalNationality(localObj.getLocalNationality());
								LOGGER.info(localObj.getLocalDescption());
								dataTable.setLlangid(localObj.getLlangid());
								dataTable.setLcountryDescId(localObj.getLcountryDescId());
								englishcountryList.remove(engObj);
								localcountryList.remove(localObj);
								LOGGER.info("size of englishcountryList" + englishcountryList.size());
								LOGGER.info("size of localcountryList" + localcountryList.size());
								LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@" + dataTable);
								countrymasterDataTableApprovalList.add(dataTable);
								added = true;
								break;
							}
							localObj = null;
						}
						engObj = null;
						if (added) {
							break;
						}
					}
					dataTable = null;
				}
			}
		}
		LOGGER.info("Exit into viewApproval method");
		
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::viewApproval");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception e) {
			LOGGER.info("Exception occured while view Approval data in CountryMasterBean" + e);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("csp.show();");
			setErrorMessage(e.getMessage());
			return;
		}
	}

	public void gotoApproval(CountryMasterDataTable dataTable) {
		try{
		LOGGER.info("Entering into gotoApproval Method");
		LOGGER.info("session.getUserName() " + session.getUserName());
		LOGGER.info("dataTable Created By " + dataTable.getCreatedBy());
		if (CommonUtil.validateApprovedBy(session.getUserName(), dataTable.getCreatedBy())) {
			RequestContext.getCurrentInstance().execute("notAuth.show();");
		} else {
			setCountryId(dataTable.getCountryId());
			setIsoCode(dataTable.getIsoCode());
			setEnglishDescption(dataTable.getEnglishDescption());
			setLocalDescption(dataTable.getLocalDescption());
			setEnglishNationality(dataTable.getEnglishNationality());
			setLocalNationality(dataTable.getLocalNationality());
			setCountryAlpha2Code(dataTable.getCountryAlpha2Code());
			setCountryAlpha3Code(dataTable.getCountryAlpha3Code());
			setCountryCode(dataTable.getCountryCode());
			setBusinessCountry(dataTable.getBusinessCountry());
			setSpilitIndicator(dataTable.getSpilitIndicator());
			
			
			/*if (dataTable.getStateStatus() != null) {
				if (dataTable.getStateStatus().equals(Constants.Yes)) {
					setStateStatus(Constants.Yes);
				} else if (dataTable.getStateStatus().equals(Constants.No)) {
					setStateStatus(Constants.No);
				}
			}*/
			setStateStatus(dataTable.getStateStatus());
			setTelCode(dataTable.getTelCode());
			setCreatedDate(dataTable.getCreatedDate());
			setCreatedBy(dataTable.getCreatedBy());
			setElangid(dataTable.getElangid());
			setLlangid(dataTable.getLlangid());
			setEcountryDescId(dataTable.getEcountryDescId());
			setLcountryDescId(dataTable.getLcountryDescId());
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/countrymasterFinalApproval.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("Exit into gotoApproval Method");
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::gotoApproval");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception e) {
			LOGGER.info("Exception occured while goto Approval in CountryMasterBean" + e);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("csp.show();");
			setErrorMessage(e.getMessage());
			return;
		}
	}

	public void gotoFinalApproval() {
		LOGGER.info("Entering into gotoFinalApproval method");
		iCountryMasterservice.approveRecord(getCountryId(), session.getUserName());
		RequestContext.getCurrentInstance().execute("approve.show();");
		LOGGER.info("Exit into gotoFinalApproval method");
	}
	
	
	public void selectOption()
	{
		LOGGER.info("select Option" + getSelectType());
	}
	
	
	public String changeyesNo(String str)
	{
		LOGGER.info("Entering into changeyesNo method");
		LOGGER.info("value is" + str);
		String output= null;
		LOGGER.info("");
		if(str!=null && str.equals("Y"))
		{
			output= "Yes";
		}
		else if(str!=null && str.equals("Yes"))
		{
			output= "Y";
		}
		else if(str!=null && str.equals("N"))
		{
			output= "No";
		}
		else if(str!=null && str.equals("No"))
		{
			output= "N";
		}
		
		LOGGER.info("Output value is "+ output);
		return output;
	}
	
	public void cancelRemarks() {
		LOGGER.info("Entering into cancelRemarks method");
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/countrymasterManual.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into cancelRemarks method");
	}
	
	
	public void confirmPermanentDelete() {
		
		
		if (countrymasterDataTableList.size() > 0) {

			CountryMasterDataTable countryMasterDataTable = null;
			
			
			List<CountryMasterDataTable> tempist = new CopyOnWriteArrayList<CountryMasterDataTable>();
			
		//	Collections.copy(tempist,countrymasterDataTableList);
			
			tempist.clear();
			
			for (CountryMasterDataTable countryMasterDataTable2 : countrymasterDataTableList) {
				tempist.add(countryMasterDataTable2);
			}
			

			Iterator<CountryMasterDataTable> iter = tempist.iterator();
			while (iter.hasNext()) {
				countryMasterDataTable = iter.next();
				if (countryMasterDataTable.getPermanetDeleteCheck() != null && countryMasterDataTable.getPermanetDeleteCheck().equals(true)) {
					tempist.remove(countryMasterDataTable);
					delete(countryMasterDataTable);

					// listData.remove(deliveryModeDatatable);

				}
			}
			
			countrymasterDataTableList.clear();
			for (CountryMasterDataTable countryMasterDataTable2 : tempist) {
				countrymasterDataTableList.add(countryMasterDataTable2);
			}
		}
	}
	
	
	public void delete(CountryMasterDataTable countryMasterDataTable) {
		
		master = new CountryMaster();
		master.setCountryId(countryMasterDataTable.getCountryId());
		
		try {
			List<CountryMasterDesc> list = iCountryMasterservice.viewById(master.getCountryId());
			List<CountryMasterDesc> countryDescList = new ArrayList<CountryMasterDesc>();
			for (CountryMasterDesc desc : list) {
				countryDescList.add(desc);
			}
			iCountryMasterservice.delete(master, countryDescList);
		//	RequestContext.getCurrentInstance().execute("deleteSuccess.show();");
			return;
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::delete");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception e) {
			LOGGER.info("Exception occured while delete data in CountryMasterBean" + e);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("csp.show();");
			setErrorMessage(e.getMessage());
			return;
		}
		
	}
}


