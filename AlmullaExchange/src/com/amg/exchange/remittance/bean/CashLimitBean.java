package com.amg.exchange.remittance.bean;

import java.io.IOException;
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
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.remittance.model.CashLimit;
import com.amg.exchange.remittance.model.CashLimitType;
import com.amg.exchange.remittance.service.ICashLimitService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("cashlimitBean")
@Scope("session")
public class CashLimitBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CashLimitBean.class);
	private SessionStateManage session = new SessionStateManage();
	private Boolean editFalg;
	private Boolean booDatatableEnabled;
	private Boolean booSubmitButton;
	private String remarks;
	private String dynamicLabelForActivateDeactivate;
	private boolean hideEdit;
	private boolean ispopulate;
	private Boolean permanetDeleteCheck = false;
	private BigDecimal cashLimitId;
	private BigDecimal countryId;
	private BigDecimal limitTypeId;
	private BigDecimal limit1;
	private BigDecimal limit2;
	private String isActive;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date modifiedDate;
	private String countryName;
	private String cashLimitTypeDescription;
	private String roleLimitType;
	private String exceptionMessage;
	
	private String errmsg;
	
	private List<CashLimitDataTable> cashLimitTableList = new ArrayList<CashLimitDataTable>();
	private List<CountryMasterDesc> countryList = new ArrayList<CountryMasterDesc>();
	private List<CashLimitType> cashLimitType = new ArrayList<CashLimitType>();
	private Map<BigDecimal, String> countryMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> cashLimitMap = new HashMap<BigDecimal, String>();
	
	private List<RoleMaster> roleMasterList = new ArrayList<RoleMaster>();
	/*
	 * private Map<BigDecimal, String> cashLimitTypeMap = new
	 * HashMap<BigDecimal, String>();
	 */
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	ICashLimitService cashlimitservice;
	

	
	
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public List<RoleMaster> getRoleMasterList() {
		
		roleMasterList =cashlimitservice.getRoleMasterList();
		
		return roleMasterList;
	}

	public void setRoleMasterList(List<RoleMaster> roleMasterList) {
		this.roleMasterList = roleMasterList;
	}

	

	public String getRoleLimitType() {
		return roleLimitType;
	}

	public void setRoleLimitType(String roleLimitType) {
		this.roleLimitType = roleLimitType;
	}

	public String getCashLimitTypeDescription() {
		return cashLimitTypeDescription;
	}

	public void setCashLimitTypeDescription(String cashLimitTypeDescription) {
		this.cashLimitTypeDescription = cashLimitTypeDescription;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public List<CashLimitDataTable> getCashLimitTableList() {
		return cashLimitTableList;
	}

	public void setCashLimitTableList(List<CashLimitDataTable> cashLimitTableList) {
		this.cashLimitTableList = cashLimitTableList;
	}

	public List<CountryMasterDesc> getCountryList() {
		countryList = generalService.getCountryList(session.getLanguageId());
		for (CountryMasterDesc bankMaster : countryList) {
			countryMap.put(bankMaster.getFsCountryMaster().getCountryId(), bankMaster.getCountryName());
		}
		return countryList;
	}

	public List<CashLimitType> getCashLimitType() {
		return cashLimitType;
	}

	public void setCashLimitType(List<CashLimitType> cashLimitType) {
		this.cashLimitType = cashLimitType;
	}

	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}

	public Boolean getEditFalg() {
		return editFalg;
	}

	public void setEditFalg(Boolean editFalg) {
		this.editFalg = editFalg;
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

	public Boolean getPermanetDeleteCheck() {
		return permanetDeleteCheck;
	}

	public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
		this.permanetDeleteCheck = permanetDeleteCheck;
	}

	public BigDecimal getCashLimitId() {
		return cashLimitId;
	}

	public void setCashLimitId(BigDecimal cashLimitId) {
		this.cashLimitId = cashLimitId;
	}


	public BigDecimal getLimitTypeId() {
		return limitTypeId;
	}

	public void setLimitTypeId(BigDecimal limitTypeId) {
		this.limitTypeId = limitTypeId;
	}

	public BigDecimal getLimit1() {
		return limit1;
	}

	public void setLimit1(BigDecimal limit1) {
		this.limit1 = limit1;
	}

	public BigDecimal getLimit2() {
		return limit2;
	}

	public void setLimit2(BigDecimal limit2) {
		this.limit2 = limit2;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {
		try{
		editFalg = false;
		LOGGER.info("Entering into pageNavigation");
		clearAll();
		clearList();
		prepareValues();
		setBooDatatableEnabled(false);
		if (session.getUserName() == null) {
		}
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "cashlimit.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/cashlimit.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit  into pageNavigation");
		}catch(Exception e){
			if(e.getMessage()!=null){
				setExceptionMessage("Exception :"+e.getMessage());
			}else{
				setExceptionMessage("Exception :"+e);
			}
		}
	}

	private void clearList() {
		if (cashLimitTableList != null) {
			cashLimitTableList.clear();
		}
		if (countryList != null) {
			countryList.clear();
		}
		if (cashLimitType != null) {
			cashLimitType.clear();
		}
		if (cashLimitMap != null) {
			cashLimitMap.clear();
		}
		/*
		 * if (cashLimitTypeMap != null) { cashLimitTypeMap.clear(); }
		 */
		if (countryMap != null) {
			countryMap.clear();
		}
	}

	public void clickOnExit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void add() {
		try{
		LOGGER.info("Entering into add method");
		boolean alreadyDT = false;
		// Data table duplicatecheck
		if (cashLimitTableList.size() != 0) {
			for (CashLimitDataTable cashDataTable : cashLimitTableList) {
				System.out.println("duplicate check");
				if (getCountryId() != null && getLimitTypeId() != null && cashDataTable.getCountryId().equals(getCountryId()) && cashDataTable.getLimitTypeId().equals(getLimitTypeId())) {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("duplicate.show();");
					return;
				}
			}
		}
		boolean alreadyDB = false;
		// Data table duplicatecheck
		if (!alreadyDT) {
			System.out.println("Dbcheck");
		}
		if (!alreadyDT && !alreadyDB) {
			CashLimitDataTable dataTable = new CashLimitDataTable();
			int compare = getLimit1().compareTo(getLimit2());
			if (compare == 1 || compare == 0) {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("greater.show();");
				return;
			}
			dataTable.setCashLimitId(getCashLimitId());
			dataTable.setCountryId(getCountryId());
			dataTable.setCountryName(countryMap.get(getCountryId()));
			dataTable.setCashLimitTypeDescription(cashLimitMap.get(getLimitTypeId()));
			System.out.println("############getCashLimitId###########" + getLimitTypeId());
			System.out.println("#######################" + cashLimitMap.get(getLimitTypeId()));
			
			dataTable.setRoleLimitType(getRoleLimitType());
			dataTable.setLimitTypeId(getLimitTypeId());
			dataTable.setCashLimitId(getCashLimitId());
			dataTable.setLimit1(getLimit1());
			dataTable.setLimit2(getLimit2());
			dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(getIsActive()));
			dataTable.setIsActive(Constants.Yes);
			if (dataTable.getDynamicLabelForActivateDeactivate().equals(Constants.DELETE)) {
				if (dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null) {
				} else {
					dataTable.setDynamicLabelForActivateDeactivate("");
				}
			}
			// In first Time
			if (!editFalg && !ispopulate) {
				dataTable.setCreatedBy(session.getUserName());
				dataTable.setCreatedDate(new Date());
				dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(null));
			} else {
				dataTable.setCreatedBy(getCreatedBy());
				dataTable.setCreatedDate(getCreatedDate());
				dataTable.setIsActive(getIsActive());
				dataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(getIsActive()));
				/* dataTable.setIsActive(Constants.U); */
				dataTable.setModifiedBy(session.getUserName());
				dataTable.setModifiedDate(new Date());
				if (dataTable.getDynamicLabelForActivateDeactivate().equals(Constants.DELETE)) {
					if (dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null) {
					} else {
						dataTable.setDynamicLabelForActivateDeactivate("");
					}
				}
			}
			/*
			 * dataTable.setApprovedDate(getApprovedDate());
			 * dataTable.setApprovedBy(getApprovedBy());
			 */
			
			dataTable.setRoleLimitType(getRoleLimitType());
			dataTable.setRemarks(getRemarks());
			// dataTable.setcompanyDescId(getcompanyDescId());
			/*
			 * dataTable.setCompanyEnglishDescId(getCompanyEnglishDescId());
			 * dataTable.setCompanyLocalDescId(getCompanyLocalDescId());
			 */
			LOGGER.info("Entered value is ");
			LOGGER.info(dataTable);
			cashLimitTableList.add(dataTable);
			setBooDatatableEnabled(true);
			setBooSubmitButton(false);
			// setBoocompanyCodeReadOnly(false);
			clearAll();
		}
		setHideEdit(false);
		LOGGER.info("Exit into add method");
		}catch(Exception e){
			if(e.getMessage()!=null){
				setExceptionMessage("Exception :"+e.getMessage());
			}else{
				setExceptionMessage("Exception :"+e);
			}
		}
	}

	private String setreverActiveStatus(String status) {
		LOGGER.info("Entering into setreverActiveStatus method");
		String strStatus = null;
		if (status == null) {
			strStatus = Constants.REMOVE;
		} else if (status.equalsIgnoreCase(Constants.U)) {
			strStatus = Constants.DELETE;
		} else if (status.equalsIgnoreCase(Constants.D)) {
			strStatus = Constants.ACTIVATE;
		} else if (status.equalsIgnoreCase(Constants.Yes)) {
			strStatus = "";
		}
		LOGGER.info("strStatus " + strStatus);
		LOGGER.info("Exit into setreverActiveStatus method");
		return strStatus;
	}

	public void clearAll() {
		LOGGER.info("Entering into clearAll method");
		setCashLimitId(null);
		setCashLimitType(null);
		setCountryId(null);
		setLimit1(null);
		setLimit2(null);
		setIsActive(null);
		setCreatedDate(null);
		setCreatedBy(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setIsActive(null);
		setRemarks(null);
		setLimitTypeId(null);
		setCountryName(null);
		setCashLimitTypeDescription(null);
		setRoleLimitType(null);
		ispopulate = false;
		LOGGER.info("Exit into clearAll method");
	}

	private void clearAllexceptcandl() {
		LOGGER.info("Entering into clearAll method");
		/*
		 * setCashLimitId(null); setCashLimitType(null);
		 */
		setLimit1(null);
		setLimit2(null);
		setIsActive(null);
		setCreatedDate(null);
		setCreatedBy(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setIsActive(null);
		setRemarks(null);
		setCountryName(null);
		setCashLimitTypeDescription(null);
		setRoleLimitType(null);
		ispopulate = false;
		LOGGER.info("Exit into clearAll method");
	}

	public void save() {
		try{
		LOGGER.info("Entering into save method");
		CashLimit cashLimit = null;
		for (CashLimitDataTable dataTable : cashLimitTableList) {
			cashLimit = new CashLimit();
			cashLimit.setCashLimitId(dataTable.getCashLimitId());
			CountryMaster cm = new CountryMaster();
			cm.setCountryId(dataTable.getCountryId());
			cashLimit.setCountry(cm);
			CashLimitType clt = new CashLimitType();
			clt.setLimitTypeId(dataTable.getLimitTypeId());
			cashLimit.setLimitType(clt);
		/*	cashLimit.setRoleLimitType(dataTable.getLimitType());*/
			cashLimit.setRoleLimitType(dataTable.getRoleLimitType());
			cashLimit.setLimit1(dataTable.getLimit1());
			cashLimit.setLimit2(dataTable.getLimit2());
			cashLimit.setCreatedBy(dataTable.getCreatedBy());
			cashLimit.setCreatedDate(dataTable.getCreatedDate());
			cashLimit.setIsActive(dataTable.getIsActive());
			cashLimit.setModifiedBy(dataTable.getModifiedBy());
			cashLimit.setModifiedDate(dataTable.getModifiedDate());
			cashLimit.setRemarks(dataTable.getRemarks());
			cashLimit.setCashLimitId(dataTable.getCashLimitId());
			System.out.println("#######################################");
			System.out.println(dataTable);
			System.out.println("#######################################");
			cashlimitservice.save(cashLimit);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("succsses.show();");
		}
		LOGGER.info("Exit into save method");
		}catch(Exception e){
			if(e.getMessage()!=null){
				setExceptionMessage("Exception :"+e.getMessage());
			}else{
				setExceptionMessage("Exception :"+e);
			}
		}
	}

	public void populateDetails() {
		try{
		LOGGER.info("Entering into populateDetails method");
		LOGGER.info("Country Id" + getCountryId());
		LOGGER.info("Limit Id " + getLimitTypeId());
		ispopulate = true;
		List<CashLimit> cashLimitList = new ArrayList<CashLimit>();
		cashLimitList = cashlimitservice.populateDetails(getCountryId(), getLimitTypeId());
		if (cashLimitList != null) {
			LOGGER.info("Records already exist");
			setCashLimitId(cashLimitList.get(0).getCashLimitId());
			setCountryId(cashLimitList.get(0).getCountry().getCountryId());
			setLimitTypeId(cashLimitList.get(0).getLimitType().getLimitTypeId());
			setRoleLimitType(cashLimitList.get(0).getRoleLimitType());
			setLimit1(cashLimitList.get(0).getLimit1());
			setLimit2(cashLimitList.get(0).getLimit2());
			setCreatedBy(cashLimitList.get(0).getCreatedBy());
			setCreatedDate(cashLimitList.get(0).getCreatedDate());
			setIsActive(cashLimitList.get(0).getIsActive());
			setModifiedBy(cashLimitList.get(0).getModifiedBy());
			setModifiedDate(cashLimitList.get(0).getModifiedDate());
			setRemarks(cashLimitList.get(0).getRemarks());
			setCashLimitId(cashLimitList.get(0).getCashLimitId());
			setCashLimitTypeDescription(cashLimitMap.get(cashLimitList.get(0).getLimitType().getLimitTypeId()));
		} else {
			clearAllexceptcandl();
		}
		LOGGER.info("Exit into populateDetails method");
		}catch(Exception e){
			if(e.getMessage()!=null){
				setExceptionMessage("Exception :"+e.getMessage());
			}else{
				setExceptionMessage("Exception :"+e);
			}
		}
	}

	public void prepareValues() {
		try{
		cashLimitType = cashlimitservice.getCashLimitType();
		for (CashLimitType clt : cashLimitType) {
			cashLimitMap.put(clt.getLimitTypeId(), clt.getLimitTypeDesc());
		}
		roleMasterList= cashlimitservice.getRoleMasterList();
		}catch(Exception e){
			if(e.getMessage()!=null){
				setExceptionMessage("Exception :"+e.getMessage());
			}else{
				setExceptionMessage("Exception :"+e);
			}
		}
	}

	public void editRecord(CashLimitDataTable dataTable) {
		try{
		setEditFalg(true);
		setHideEdit(true);
		prepareValues();
		LOGGER.info("Entering into editRecord method");
		setCashLimitId(dataTable.getCashLimitId());
		setCountryId(dataTable.getCountryId());
		setLimitTypeId(dataTable.getLimitTypeId());
		setRoleLimitType(dataTable.getRoleLimitType());
		setLimit1(dataTable.getLimit1());
		setLimit2(dataTable.getLimit2());
		setCreatedBy(dataTable.getCreatedBy());
		setCreatedDate(dataTable.getCreatedDate());
		setIsActive(dataTable.getIsActive());
		setModifiedBy(dataTable.getModifiedBy());
		setModifiedDate(dataTable.getModifiedDate());
		setRemarks(dataTable.getRemarks());
		setCashLimitId(dataTable.getCashLimitId());
		if (dataTable.getCashLimitId() == null) {
			editFalg = false;
		} else {
			setModifiedBy(session.getUserName());
			setModifiedDate(new Date());
		}
		setCashLimitTypeDescription(cashLimitMap.get(dataTable.getLimitTypeId()));
		cashLimitTableList.remove(dataTable);
		LOGGER.info("Exit into editRecord method");
		if (cashLimitTableList.isEmpty()) {
			setBooDatatableEnabled(false);
		}
		}catch(Exception e){
			if(e.getMessage()!=null){
				setExceptionMessage("Exception :"+e.getMessage());
			}else{
				setExceptionMessage("Exception :"+e);
			}
		}
	}

	public void viewbasedOnCountry() {
		
		try{
		LOGGER.info("Entering into viewbasedOnCountry method");
		if (getCountryId() == null) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("country.show();");
			return;
		}
		LOGGER.info("country Id" + getCountryId());
		List<CashLimit> cashLimitList  = cashlimitservice.viewbasedOnCountry(getCountryId());
		cashLimitTableList.clear();
		if (cashLimitList != null && !cashLimitList.isEmpty()) {
			prepareValues();
			CashLimitDataTable dataTable = null;
			for (CashLimit cashLimit : cashLimitList) {
				dataTable = new CashLimitDataTable();
				System.out.println(cashLimit.getLimit1());
				System.out.println(cashLimit.getLimit2());
				dataTable.setCashLimitId(cashLimit.getCashLimitId());
				dataTable.setCountryId(cashLimit.getCountry().getCountryId());
				dataTable.setLimitTypeId(cashLimit.getLimitType().getLimitTypeId());
				dataTable.setLimit1(cashLimit.getLimit1());
				dataTable.setLimit2(cashLimit.getLimit2());
				dataTable.setCreatedBy(cashLimit.getCreatedBy());
				dataTable.setCreatedDate(cashLimit.getCreatedDate());
				dataTable.setIsActive(cashLimit.getIsActive());
				dataTable.setModifiedBy(cashLimit.getModifiedBy());
				dataTable.setModifiedDate(cashLimit.getModifiedDate());
				dataTable.setRemarks(cashLimit.getRemarks());
				dataTable.setRoleLimitType(cashLimit.getRoleLimitType());
				LOGGER.info(cashLimit.getCashLimitId());
				dataTable.setCashLimitId(cashLimit.getCashLimitId());
				LOGGER.info("cashLimitList.get(0).getLimitType().getLimitTypeId()  " + cashLimit.getLimitType().getLimitTypeId());
				dataTable.setCashLimitTypeDescription(cashLimitMap.get(cashLimit.getLimitType().getLimitTypeId()));
				dataTable.setCountryName(generalService.getCountryName(session.getLanguageId(), cashLimit.getCountry().getCountryId()));
				cashLimitTableList.add(dataTable);
				setBooDatatableEnabled(true);
			}
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("datanotfound.show();");
			setBooDatatableEnabled(false);
		}
		LOGGER.info("Exit into viewbasedOnCountry method");
		}catch(Exception e){
			if(e.getMessage()!=null){
				setExceptionMessage("Exception :"+e.getMessage());
			}else{
				setExceptionMessage("Exception :"+e);
			}
		}
	}

	public void checkStatus(CashLimitDataTable dataTable) {
		try{
		cashLimitTableList.remove(dataTable);
		if (cashLimitTableList.size() == 0) {
			setBooDatatableEnabled(false);
			clearAll();
		}
		}catch(Exception e){
			if(e.getMessage()!=null){
				setExceptionMessage("Exception :"+e.getMessage());
			}else{
				setExceptionMessage("Exception :"+e);
			}
		}
	}
	
	public void checkLimit()
	{
		try{
		if(getLimit1()==null)
		{
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter Limit1");
			return;
		}
		
		if(getLimit2()==null)
		{
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter Limit2");
			return;
		}
		
		if(getLimit1()!=null && getLimit2()!=null)
		{
			if(getLimit1().compareTo(getLimit2())!=-1)
			{
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Please enter Limit2");
				return;
			}
		}
		}catch(Exception e){
			if(e.getMessage()!=null){
				setExceptionMessage("Exception :"+e.getMessage());
			}else{
				setExceptionMessage("Exception :"+e);
			}
		}
	}
}
