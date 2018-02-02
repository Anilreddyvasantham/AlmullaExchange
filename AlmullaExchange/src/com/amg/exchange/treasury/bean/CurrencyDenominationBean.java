package com.amg.exchange.treasury.bean;

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
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.ICurrencyDenominationService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("currencyDenominationBean")
@Scope("session")
public class CurrencyDenominationBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER=Logger.getLogger(CurrencyDenominationBean.class);
	
	private BigDecimal countryId;
	private BigDecimal currencyId;
	private String denominationDesc;
	private BigDecimal denominationCode;
	private BigDecimal denomonationAmount;
	private String countryName;
	private String currencyName;
	private Boolean booRenderDataTable=false;
	private Boolean booSaveExit=false;
	private Boolean booAdd=false;
	private Boolean booRenderApprove=false;
	private Boolean booReadOnly=false;
	private BigDecimal denominationid;
	private String dynamicLabelForActivateDeactivate;
	private Boolean renderEditButton;
	private Boolean booSubmit=false;
	private Boolean booClear=false;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private String remarks;
	private boolean booEditButton;
	boolean editFlag;
	private Boolean booRenderDataTableApprove;
	private CurrencyDenominationDataTable currencyDenominationDtObj=null;
	private Boolean booRenderApproveButton=false;
	
	private Boolean booCheck=true;
	
	private List<CurrencyDenominationDataTable> currencyDenominationDtList=new CopyOnWriteArrayList<CurrencyDenominationDataTable>();
	private List<CurrencyDenominationDataTable> newCurrencyDenominationLT=new CopyOnWriteArrayList<CurrencyDenominationDataTable>();
	private List<CountryMasterDesc> listCountry = new ArrayList<CountryMasterDesc>();
	private List<CurrencyMaster> listCurrency = new ArrayList<CurrencyMaster>();
	private List<CurrencyDenominationDataTable> selectCurrencys;
	List<BigDecimal> lstApproved = new ArrayList<BigDecimal>();
	private List<CurrencyWiseDenomination> listCurrencywiseDenomination=new ArrayList<CurrencyWiseDenomination>();
	private Map<BigDecimal, String> listCountryMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> listCurrencyMap = new HashMap<BigDecimal, String>();
	
	SessionStateManage session = new SessionStateManage();
	
	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	ICurrencyDenominationService currencyDenominationService;
	
private String errorMessage;
	
	
	
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	
	public Boolean getBooCheck() {
		return booCheck;
	}
	public void setBooCheck(Boolean booCheck) {
		this.booCheck = booCheck;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public String getDenominationDesc() {
		return denominationDesc;
	}
	public void setDenominationDesc(String denominationDesc) {
		this.denominationDesc = denominationDesc;
	}
	public BigDecimal getDenomonationAmount() {
		return denomonationAmount;
	}
	public void setDenomonationAmount(BigDecimal denomonationAmount) {
		this.denomonationAmount = denomonationAmount;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public BigDecimal getDenominationCode() {
		return denominationCode;
	}
	public void setDenominationCode(BigDecimal denominationCode) {
		this.denominationCode = denominationCode;
	}

	//To get All Countries to the List
	public List<CountryMasterDesc> getListCountry() {
		listCountry=generalService.getCountryList(session.getLanguageId());
		for (CountryMasterDesc countryMasterDesc : listCountry) {
			listCountryMap.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
		}
		return listCountry;
	}
	public void setListCountry(List<CountryMasterDesc> listCountry) {
		this.listCountry = listCountry;
	}
	
	public Map<BigDecimal, String> getListCountryMap() {
		return listCountryMap;
	}
	public void setListCountryMap(Map<BigDecimal, String> listCountryMap) {
		this.listCountryMap = listCountryMap;
	}
	
	public Map<BigDecimal, String> getListCurrencyMap() {
		return listCurrencyMap;
	}
	public void setListCurrencyMap(Map<BigDecimal, String> listCurrencyMap) {
		this.listCurrencyMap = listCurrencyMap;
	}
	
	public List<CurrencyMaster> getListCurrency() {
		/* To get the all the currency 
		 listCurrency=generalService.getCurrencyList();
		for (CurrencyMaster currencyMaster : listCurrency) {
			listCurrencyMap.put(currencyMaster.getCurrencyId(), currencyMaster.getCurrencyName());
		}*/
		return listCurrency;
	}
	public void setListCurrency(List<CurrencyMaster> listCurrency) {
		this.listCurrency = listCurrency;
	}
	
	public List<CurrencyDenominationDataTable> getCurrencyDenominationDtList() {
		return currencyDenominationDtList;
	}
	public void setCurrencyDenominationDtList(List<CurrencyDenominationDataTable> currencyDenominationDtList) {
		this.currencyDenominationDtList = currencyDenominationDtList;
	}
	
	public List<CurrencyDenominationDataTable> getNewCurrencyDenominationLT() {
		return newCurrencyDenominationLT;
	}
	public void setNewCurrencyDenominationLT(List<CurrencyDenominationDataTable> newCurrencyDenominationLT) {
		this.newCurrencyDenominationLT = newCurrencyDenominationLT;
	}
	
	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}
	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}
	
	public BigDecimal getDenominationid() {
		return denominationid;
	}
	public void setDenominationid(BigDecimal denominationid) {
		this.denominationid = denominationid;
	}
	
	
	public Boolean getBooSaveExit() {
		return booSaveExit;
	}
	public void setBooSaveExit(Boolean booSaveExit) {
		this.booSaveExit = booSaveExit;
	}
	
	
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public Boolean getRenderEditButton() {
		return renderEditButton;
	}
	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}
	
	public Boolean getBooSubmit() {
		return booSubmit;
	}
	public void setBooSubmit(Boolean booSubmit) {
		this.booSubmit = booSubmit;
	}
	
	public Boolean getBooClear() {
		return booClear;
	}
	public void setBooClear(Boolean booClear) {
		this.booClear = booClear;
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
	
	public List<CurrencyWiseDenomination> getListCurrencywiseDenomination() {
		return listCurrencywiseDenomination;
	}
	public void setListCurrencywiseDenomination(
			List<CurrencyWiseDenomination> listCurrencywiseDenomination) {
		this.listCurrencywiseDenomination = listCurrencywiseDenomination;
	}
	
	
	public CurrencyDenominationDataTable getCurrencyDenominationDtObj() {
		return currencyDenominationDtObj;
	}
	public void setCurrencyDenominationDtObj(
			CurrencyDenominationDataTable currencyDenominationDtObj) {
		this.currencyDenominationDtObj = currencyDenominationDtObj;
	}
	
	
	public boolean isBooEditButton() {
		return booEditButton;
	}
	public void setBooEditButton(boolean booEditButton) {
		this.booEditButton = booEditButton;
	}
	
	public Boolean getBooAdd() {
		return booAdd;
	}
	public void setBooAdd(Boolean booAdd) {
		this.booAdd = booAdd;
	}
	
	public Boolean getBooRenderApprove() {
		return booRenderApprove;
	}
	public void setBooRenderApprove(Boolean booRenderApprove) {
		this.booRenderApprove = booRenderApprove;
	}
	
	
	public Boolean getBooReadOnly() {
		return booReadOnly;
	}
	public void setBooReadOnly(Boolean booReadOnly) {
		this.booReadOnly = booReadOnly;
	}
	
	public boolean isEditFlag() {
		return editFlag;
	}
	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}
	
	public Boolean getBooRenderDataTableApprove() {
		return booRenderDataTableApprove;
	}
	public void setBooRenderDataTableApprove(Boolean booRenderDataTableApprove) {
		this.booRenderDataTableApprove = booRenderDataTableApprove;
	}
	
	
	public List<CurrencyDenominationDataTable> getSelectCurrencys() {
		return selectCurrencys;
	}
	public void setSelectCurrencys(
			List<CurrencyDenominationDataTable> selectCurrencys) {
		this.selectCurrencys = selectCurrencys;
	}
	public Boolean getBooRenderApproveButton() {
		return booRenderApproveButton;
	}
	public void setBooRenderApproveButton(Boolean booRenderApproveButton) {
		this.booRenderApproveButton = booRenderApproveButton;
	}
	//add Records to datatable
	public void addRecordsToDataTable(){
		LOGGER.info("Entering into addRecordsToDataTable method");
		listCurrencywiseDenomination.clear();
		setBooEditButton(false);
		boolean checkList = false;
		if (!isEditFlag()) {
			try{
				listCurrencywiseDenomination=currencyDenominationService.getAllValidateBean(getCountryId(),getCurrencyId(),getDenomonationAmount());
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
			
		}
		
		if(listCurrencywiseDenomination.size()==0){
			if(currencyDenominationDtList.size()!=0){
				for (CurrencyDenominationDataTable currencyDenominationDT : currencyDenominationDtList) {
				if(currencyDenominationDT.getCountryId().equals(getCountryId()) && currencyDenominationDT.getCurrencyId().equals(getCurrencyId())&&currencyDenominationDT.getDenomonationAmount().equals(getDenomonationAmount())){
					checkList = true;
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("checkdt.show();");
					setDenominationDesc(null);
					setDenomonationAmount(null);
					setDenominationid(null);
					setDenominationCode(null);
					setBooClear(false);
					setBooSubmit(false);
					return;
				}
			}
		}
		if(!checkList){
		CurrencyDenominationDataTable currencyDenominationDataTable=new CurrencyDenominationDataTable();
		currencyDenominationDataTable.setDenominationid(getDenominationid());
		currencyDenominationDataTable.setCountryName(listCountryMap.get(getCountryId()));
		currencyDenominationDataTable.setCountryId(getCountryId());
		currencyDenominationDataTable.setCurrencyId(getCurrencyId());
		currencyDenominationDataTable.setCurrencyName(listCurrency.get(0).getCurrencyName());
		currencyDenominationDataTable.setDenominationCode(getDenominationCode());
			if(getDenomonationAmount().intValue()<1){
			currencyDenominationDataTable.setDenomonationAmount(GetRound.roundBigDecimal(getDenomonationAmount(), (listCurrency.get(0).getDecinalNumber()).intValue()));
			int j=10;
			int value=(int) Math.pow(j, listCurrency.get(0).getDecinalNumber().intValue());
			for (int i = 0; i <= listCurrency.get(0).getDecinalNumber().intValue(); i++) {
				currencyDenominationDataTable.setDenominationDesc(GetRound.roundBigDecimal(getDenomonationAmount().multiply(new BigDecimal(value)), 0)+" "+currencyDenominationService.getAllDenominationcurrencyDesc(getCountryId(),getCurrencyId()));
			}
			//to be hard coded for decimal numbers
			/*if(getCountryId() !=null && getCurrencyId() !=null && listCurrency.get(0).getDecinalNumber().intValue()==1){
				currencyDenominationDataTable.setDenominationDesc(GetRound.roundBigDecimal(getDenomonationAmount().multiply(new BigDecimal(10)), 0)+" "+currencyDenominationService.getAllDenominationcurrencyDesc(getCountryId(),getCurrencyId()));
				}else if (getCountryId() !=null && getCurrencyId() !=null && listCurrency.get(0).getDecinalNumber().intValue()==2) {
					currencyDenominationDataTable.setDenominationDesc(GetRound.roundBigDecimal(getDenomonationAmount().multiply(new BigDecimal(100)), 0)+" "+currencyDenominationService.getAllDenominationcurrencyDesc(getCountryId(),getCurrencyId()));
				}else if (getCountryId() !=null && getCurrencyId() !=null && listCurrency.get(0).getDecinalNumber().intValue()==3) {
					currencyDenominationDataTable.setDenominationDesc(GetRound.roundBigDecimal(getDenomonationAmount().multiply(new BigDecimal(1000)), 0)+" "+currencyDenominationService.getAllDenominationcurrencyDesc(getCountryId(),getCurrencyId()));
				}else if (getCountryId() !=null && getCurrencyId() !=null && listCurrency.get(0).getDecinalNumber().intValue()==4) {
					currencyDenominationDataTable.setDenominationDesc(GetRound.roundBigDecimal(getDenomonationAmount().multiply(new BigDecimal(10000)), 0)+" "+currencyDenominationService.getAllDenominationcurrencyDesc(getCountryId(),getCurrencyId()));
				}*/
		}else{
			currencyDenominationDataTable.setDenomonationAmount(GetRound.roundBigDecimal(getDenomonationAmount(),0));
			if(getCountryId() !=null && getCurrencyId() !=null){
				try {
					currencyDenominationDataTable.setDenominationDesc(GetRound.roundBigDecimal(getDenomonationAmount(),0)+" "+currencyDenominationService.getAllDenominationDesc(getCountryId(),getCurrencyId()));
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
				
				}
		}
		currencyDenominationDataTable.setCreatedBy(getCreatedBy());
		currencyDenominationDataTable.setCreatedDate(getCreatedDate());
		currencyDenominationDataTable.setRenderEditButton(true);
		if(getDenominationid()!=null){
			if(newCurrencyDenominationLT.size() ==0){
				if(currencyDenominationDtList.size() !=0){
					if(currencyDenominationDtObj !=null){
						if(currencyDenominationDataTable.getCountryName().equals(currencyDenominationDtObj.getCountryName())&&currencyDenominationDataTable.getCurrencyName().equals(currencyDenominationDtObj.getCurrencyName())&&currencyDenominationDataTable.getDenominationDesc().equals(currencyDenominationDtObj.getDenominationDesc())&&currencyDenominationDataTable.getDenomonationAmount().equals(currencyDenominationDtObj.getDenomonationAmount())){
							currencyDenominationDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
							currencyDenominationDataTable.setModifiedBy(getModifiedBy());
							currencyDenominationDataTable.setModifiedDate(getModifiedDate());
							currencyDenominationDataTable.setApprovedBy(getApprovedBy());
							currencyDenominationDataTable.setApprovedDate(getApprovedDate());
							currencyDenominationDataTable.setRemarks(getRemarks());
							currencyDenominationDataTable.setIsActive(getIsActive());
						}else{
							currencyDenominationDataTable.setDynamicLabelForActivateDeactivate("");
							currencyDenominationDataTable.setModifiedBy(session.getUserName());
							currencyDenominationDataTable.setModifiedDate(new Date());
							currencyDenominationDataTable.setApprovedBy(null);
							currencyDenominationDataTable.setApprovedDate(null);
							currencyDenominationDataTable.setRemarks(null);
							currencyDenominationDataTable.setIsActive(Constants.U);
						}
					}else{
						currencyDenominationDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
						currencyDenominationDataTable.setModifiedBy(getModifiedBy());
						currencyDenominationDataTable.setModifiedDate(getModifiedDate());
						currencyDenominationDataTable.setApprovedBy(getApprovedBy());
						currencyDenominationDataTable.setApprovedDate(getApprovedDate());
						currencyDenominationDataTable.setRemarks(getRemarks());
						currencyDenominationDataTable.setIsActive(getIsActive());
					}
					
				}else{
					currencyDenominationDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					currencyDenominationDataTable.setModifiedBy(getModifiedBy());
					currencyDenominationDataTable.setModifiedDate(getModifiedDate());
					currencyDenominationDataTable.setApprovedBy(getApprovedBy());
					currencyDenominationDataTable.setApprovedDate(getApprovedDate());
					currencyDenominationDataTable.setRemarks(getRemarks());
					currencyDenominationDataTable.setIsActive(getIsActive());
				}
			}
			
		}else{
			currencyDenominationDataTable.setRenderEditButton(true);
			currencyDenominationDataTable.setIsActive(Constants.U);
			currencyDenominationDataTable.setCreatedBy(session.getUserName());
			currencyDenominationDataTable.setCreatedDate(new Date());
			currencyDenominationDataTable.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
		}
		currencyDenominationDtList.add(currencyDenominationDataTable);
		if(getDenominationid()==null){
			newCurrencyDenominationLT.add(currencyDenominationDataTable);
		}
		setBooRenderDataTable(true);
		setBooSaveExit(true);
		setBooClear(false);
		setBooSubmit(false);
		setBooRenderApprove(false);
		setBooRenderDataTableApprove(false);
		setBooRenderApproveButton(false);
		setEditFlag(false);
		clearAllFields();
	}
		}
		else{
			RequestContext context = RequestContext.getCurrentInstance();
			clearAllFields();
			context.execute("check.show();");
			setBooSubmit(false);
		}
		LOGGER.info("Exit into addRecordsToDataTable method");
	}

	//Clear All Fields
	public void clear(){
		LOGGER.info("Entering into clear method");
		setCountryId(null);
		setCountryName(null);
		setCurrencyId(null);
		setCurrencyName(null);
		clearAllFields();
		LOGGER.info("Exit into clear method");
	}
	public void clearAllFields(){
		LOGGER.info("Entering into clearAllFields method");
		setDenominationDesc(null);
		setDenomonationAmount(null);
		setDenominationid(null);
		setDenominationCode(null);
		LOGGER.info("Exit into clearAllFields method");
	}
	
	//Page Navigation
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void currencyDenominationNavigation(){
		LOGGER.info("Entering into currencyDenominationNavigation method");
		clearAllFields();
		clear();
		setBooRenderDataTable(false);
		setBooSaveExit(false);
		setBooClear(false);
		setBooSubmit(false);
		setBooEditButton(false);
		setBooRenderApprove(false);
		setBooRenderDataTableApprove(false);
		setBooRenderApproveButton(false);
		setBooAdd(true);
		setBooReadOnly(false);
		currencyDenominationDtList.clear();
		newCurrencyDenominationLT.clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "CurrencyDenomination.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/CurrencyDenomination.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::currencyDenominationNavigation");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		LOGGER.info("Exit into currencyDenominationNavigation method");
	}

	public void edit(CurrencyDenominationDataTable currencyDenominationDtObj){
		LOGGER.info("Entering into edit method");
		setBooSubmit(true);
		setBooClear(true);
		setEditFlag(true);
		setCurrencyDenominationDtObj(currencyDenominationDtObj);
		setDenominationid(currencyDenominationDtObj.getDenominationid());
		setCurrencyId(currencyDenominationDtObj.getCurrencyId());
		setCurrencyName(currencyDenominationDtObj.getCurrencyName());
		setCountryId(currencyDenominationDtObj.getCountryId());
		setCountryName(currencyDenominationDtObj.getCountryName());
		setDenominationDesc(currencyDenominationDtObj.getDenominationDesc());
		setDenominationCode(currencyDenominationDtObj.getDenominationCode());
		setDenomonationAmount(currencyDenominationDtObj.getDenomonationAmount());
		setRenderEditButton(currencyDenominationDtObj.getRenderEditButton());
		setDynamicLabelForActivateDeactivate(currencyDenominationDtObj.getDynamicLabelForActivateDeactivate());
		setCreatedBy(currencyDenominationDtObj.getCreatedBy());
		setCreatedDate(currencyDenominationDtObj.getCreatedDate());
		setModifiedBy(currencyDenominationDtObj.getModifiedBy());
		setModifiedDate(currencyDenominationDtObj.getModifiedDate());
		setApprovedBy(currencyDenominationDtObj.getApprovedBy());
		setApprovedDate(currencyDenominationDtObj.getApprovedDate());
		setIsActive(currencyDenominationDtObj.getIsActive());
		setRemarks(currencyDenominationDtObj.getRemarks());
		setBooEditButton(true);
		currencyDenominationDtList.remove(currencyDenominationDtObj);
		newCurrencyDenominationLT.remove(currencyDenominationDtObj);
		LOGGER.info("Exit into edit method");
	}
	public void hideButton(){
		LOGGER.info("Entering into hideButton method");
		if(getDenominationDesc() !=null || getDenomonationAmount() !=null){
			setBooSubmit(true);
		}else{
			setBooSubmit(false);
		}
		LOGGER.info("Exit into hideButton method");
	}
	public void save(){
		LOGGER.info("Entering into save method");
		if (currencyDenominationDtList.isEmpty()) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		} else {
			try {
				for (CurrencyDenominationDataTable currencyDtObj : currencyDenominationDtList) {
					CurrencyWiseDenomination currencyWiseDenomination=new CurrencyWiseDenomination();
					currencyWiseDenomination.setDenominationId(currencyDtObj.getDenominationid());
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(currencyDtObj.getCountryId());
					currencyWiseDenomination.setFsCountryMaster(countryMaster);
					CurrencyMaster currency = new CurrencyMaster();
					currency.setCurrencyId(currencyDtObj.getCurrencyId());
					currencyWiseDenomination.setExCurrencyMaster(currency);
					currencyWiseDenomination.setDenominationDesc(currencyDtObj.getDenominationDesc());
					currencyWiseDenomination.setDenominationAmount(currencyDtObj.getDenomonationAmount());
					currencyWiseDenomination.setCreatedBy(currencyDtObj.getCreatedBy());
					currencyWiseDenomination.setCreatedDate(currencyDtObj.getCreatedDate());
					currencyWiseDenomination.setModifiedBy(currencyDtObj.getModifiedBy());
					currencyWiseDenomination.setModifiedDate(currencyDtObj.getModifiedDate());
					currencyWiseDenomination.setApprovedBy(currencyDtObj.getApprovedBy());
					currencyWiseDenomination.setApprovedDate(currencyDtObj.getApprovedDate());
					currencyWiseDenomination.setRemarks(currencyDtObj.getRemarks());
					currencyWiseDenomination.setIsActive(currencyDtObj.getIsActive());
					currencyWiseDenomination.setDenominationCode(currencyDtObj.getDenominationCode());
					currencyDenominationService.save(currencyWiseDenomination);
				}
				RequestContext.getCurrentInstance().execute("complete.show();");
				newCurrencyDenominationLT.clear();
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
		}
		LOGGER.info("Exit into save method");
	}
	
	public void clickOnOKSave(){
		LOGGER.info("Entering into clickOnOKSave method");
		currencyDenominationDtList.clear();
		setBooRenderDataTable(false);
		setBooSaveExit(false);
		setBooRenderApprove(false);
		setBooRenderDataTableApprove(false);
		setBooRenderApproveButton(false);
		clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/CurrencyDenomination.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::clickOnOKSave");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		LOGGER.info("Exit into clickOnOKSave method");
	}
	public void view(){
		LOGGER.info("Entering into view method");
		currencyDenominationDtList.clear();
		listCurrencywiseDenomination.clear();
		if(getCountryId() !=null && getCurrencyId() !=null){
			try{
			List<CurrencyWiseDenomination> currencyWiseDenominationList=currencyDenominationService.getAllListToDb(getCountryId(),getCurrencyId());
			if(currencyWiseDenominationList.size()>0){
				
				for (CurrencyWiseDenomination currencyWiseDenomination : currencyWiseDenominationList) {
					CurrencyDenominationDataTable currencyDenominationDTObj=new CurrencyDenominationDataTable();
					currencyDenominationDTObj.setDenominationid(currencyWiseDenomination.getDenominationId());
					currencyDenominationDTObj.setCountryId(currencyWiseDenomination.getFsCountryMaster().getCountryId());
					currencyDenominationDTObj.setCountryName(listCountryMap.get(currencyWiseDenomination.getFsCountryMaster().getCountryId()));
					currencyDenominationDTObj.setCurrencyId(currencyWiseDenomination.getExCurrencyMaster().getCurrencyId());
					currencyDenominationDTObj.setCurrencyName(generalService.getCurrencyName(currencyWiseDenomination.getExCurrencyMaster().getCurrencyId()));
					currencyDenominationDTObj.setDenominationDesc(currencyWiseDenomination.getDenominationDesc());
					currencyDenominationDTObj.setDenomonationAmount(currencyWiseDenomination.getDenominationAmount());
					currencyDenominationDTObj.setDenominationCode(currencyWiseDenomination.getDenominationCode());
					currencyDenominationDTObj.setRenderEditButton(true);
					currencyDenominationDTObj.setCreatedBy(currencyWiseDenomination.getCreatedBy());
					currencyDenominationDTObj.setCreatedDate(currencyWiseDenomination.getCreatedDate());
					currencyDenominationDTObj.setModifiedBy(currencyWiseDenomination.getModifiedBy());
					currencyDenominationDTObj.setModifiedDate(currencyWiseDenomination.getModifiedDate());
					currencyDenominationDTObj.setApprovedBy(currencyWiseDenomination.getApprovedBy());
					currencyDenominationDTObj.setApprovedDate(currencyWiseDenomination.getApprovedDate());
					currencyDenominationDTObj.setRemarks(currencyWiseDenomination.getRemarks());
					currencyDenominationDTObj.setIsActive(currencyWiseDenomination.getIsActive());
					currencyDenominationDTObj.setBooEditButton(false);
					setBooEditButton(false);
					if(currencyDenominationDTObj.getIsActive() != null){
					if(currencyDenominationDTObj.getIsActive().equalsIgnoreCase(Constants.Yes)){
						currencyDenominationDTObj.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					}else if (currencyDenominationDTObj.getIsActive().equalsIgnoreCase(Constants.D)) {
						currencyDenominationDTObj.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					}else if (currencyDenominationDTObj.getIsActive().equalsIgnoreCase(Constants.U)) {
						if(currencyWiseDenomination.getModifiedBy()==null && currencyWiseDenomination.getModifiedDate() ==null){
							currencyDenominationDTObj.setDynamicLabelForActivateDeactivate(Constants.DELETE);
						}else{
							currencyDenominationDTObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						}
					}
					}
					currencyDenominationDtList.add(currencyDenominationDTObj);
				}
				
				clearAllFields();
				setBooRenderDataTable(true);
				setBooSaveExit(true);
				setBooRenderApprove(false);
				setBooRenderDataTableApprove(false);
				setBooRenderApproveButton(false);
				
				currencyDenominationDtList.addAll(newCurrencyDenominationLT);
			}else{
				RequestContext.getCurrentInstance().execute("noRecord.show();");
				  currencyDenominationDtList.addAll(newCurrencyDenominationLT);	 
			}
			}catch (NullPointerException ne) {
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
			else{
				  
			RequestContext.getCurrentInstance().execute("chooseAnyOne.show();");
		}
		LOGGER.info("Entering into view method");
	}
	public void exit(){
		LOGGER.info("Entering into exit method");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
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
		LOGGER.info("Exit into exit method");
	}
	
	public void checkStatus(CurrencyDenominationDataTable currencyDenominationDtObj){
		LOGGER.info("Entering into checkStatus method");
		if(currencyDenominationDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)){
			currencyDenominationDtObj.setRemarksCheck(true);
			setIsActive(currencyDenominationDtObj.getIsActive());
			currencyDenominationDtObj.setRemarks(getRemarks());
			setApprovedBy(currencyDenominationDtObj.getApprovedBy());
			setApprovedDate(currencyDenominationDtObj.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
			return;
		}else if ((currencyDenominationDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE))&&currencyDenominationDtObj.getModifiedBy()==null&&currencyDenominationDtObj.getModifiedDate()==null&&currencyDenominationDtObj.getApprovedBy()==null&&currencyDenominationDtObj.getApprovedDate()==null&&currencyDenominationDtObj.getRemarks()==null) {
			currencyDenominationDtObj.setPermanetDeleteCheck(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			return;
		}else if ((currencyDenominationDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE))) {
			//currencyDenominationDtObj.setPermanetDeleteCheck(true);
			RequestContext.getCurrentInstance().execute("pending.show();");
			return;
		}
		removeRecord(currencyDenominationDtObj);
		LOGGER.info("Exit into checkStatus method");
	}
	public void removeRecord(CurrencyDenominationDataTable currencyDenominationDtObj){
		LOGGER.info("Entering into removeRecord method");
		if(currencyDenominationDtObj.getDenominationid() ==null){
			currencyDenominationDtList.remove(currencyDenominationDtObj);
			newCurrencyDenominationLT.remove(currencyDenominationDtObj);
			if(currencyDenominationDtList.size()<=0){
				setBooSaveExit(false);
				setBooRenderDataTable(false);
				setBooRenderApprove(false);
				setBooRenderDataTableApprove(false);
				setBooRenderApproveButton(false);
			}
		}else{
			delete(currencyDenominationDtObj);
			currencyDenominationDtList.clear();
			view();
		}
		LOGGER.info("Exit into removeRecord method");
	}
	
	public void delete(CurrencyDenominationDataTable currencyDenominationDtObj){
		LOGGER.info("Entering into delete method");
		currencyDenominationDtList.clear();
		try {
			CurrencyWiseDenomination currencyWiseDenomination=new CurrencyWiseDenomination();
			currencyWiseDenomination.setDenominationId(currencyDenominationDtObj.getDenominationid());
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(currencyDenominationDtObj.getCountryId());
			currencyWiseDenomination.setFsCountryMaster(countryMaster);
			CurrencyMaster currency = new CurrencyMaster();
			currency.setCurrencyId(currencyDenominationDtObj.getCurrencyId());
			currencyWiseDenomination.setExCurrencyMaster(currency);
			currencyWiseDenomination.setDenominationDesc(currencyDenominationDtObj.getDenominationDesc());
			currencyWiseDenomination.setDenominationAmount(currencyDenominationDtObj.getDenomonationAmount());
			currencyWiseDenomination.setDenominationCode(currencyDenominationDtObj.getDenominationCode());
			
			if(currencyDenominationDtObj.getDynamicLabelForActivateDeactivate() !=null){
				if(currencyDenominationDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)){
					currencyWiseDenomination.setIsActive(Constants.D);
					currencyWiseDenomination.setRemarks(currencyDenominationDtObj.getRemarks());
				}else if (currencyDenominationDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
					currencyWiseDenomination.setIsActive(Constants.U);
				}/*else if (currencyDenominationDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE) ) {
					currencyWiseDenomination.setIsActive(Constants.D);
				}
				else if (currencyDenominationDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.De)) {
					currencyWiseDenomination.setIsActive(Constants.D);
				}*/
			}
			
			if(currencyDenominationDtObj.getDenominationid()!=null){
				currencyWiseDenomination.setDenominationId(currencyDenominationDtObj.getDenominationid());
				currencyWiseDenomination.setCreatedBy(currencyDenominationDtObj.getCreatedBy());
				currencyWiseDenomination.setCreatedDate(currencyDenominationDtObj.getCreatedDate());
				currencyWiseDenomination.setModifiedBy(session.getUserName());
				currencyWiseDenomination.setModifiedDate(new Date());
				currencyDenominationService.save(currencyWiseDenomination);
			}
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
		LOGGER.info("Exit into delete method");
	}
	public void remarkSelectedRecord(){
		LOGGER.info("Entering into remarkSelectedRecord method");
		try {
		if (getRemarks()!=null && !getRemarks().equals("")) {
			for (CurrencyDenominationDataTable currencyDenominationDtObj : currencyDenominationDtList) {
				if(currencyDenominationDtObj.getRemarksCheck() != null){
					if(currencyDenominationDtObj.getRemarksCheck().equals(true)){
						currencyDenominationDtObj.setRemarks(getRemarks()); 
						removeRecord(currencyDenominationDtObj);
						setRemarks(null);
					}
				}
			}
			
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/CurrencyDenomination.xhtml");
		
		}else{
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		}
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::currencyDenominationApprovel");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		
		LOGGER.info("Exit into remarkSelectedRecord method");
	}
	public void cancelFromActivation(){
		LOGGER.info("Entering into cancelFromActivation method");
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/CurrencyDenomination.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::currencyDenominationApprovel");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		LOGGER.info("Exit into cancelFromActivation method");
	}
	
	public void confirmPermanentDelete(){
		LOGGER.info("Entering into confirmPermanentDelete method");
		if(currencyDenominationDtList.size()>0){
			for (CurrencyDenominationDataTable currencyDenominationDTObj : currencyDenominationDtList) {
				if(currencyDenominationDTObj.getActivateRecordCheck() != null){
					if(currencyDenominationDTObj.getPermanetDeleteCheck().equals(true)){
						hardDelete(currencyDenominationDTObj);
						currencyDenominationDtList.remove(currencyDenominationDTObj);
					}
				}
			}
		}
		LOGGER.info("Exit into confirmPermanentDelete method");	
	}
	
	public void hardDelete(CurrencyDenominationDataTable currencyDenominationDTObj){
		LOGGER.info("Entering into hardDelete method");
		try{
			
			currencyDenominationService.deleteRecordPermanetly(currencyDenominationDTObj.getDenominationid());
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::currencyDenominationApprovel");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		
		LOGGER.info("Exit into hardDelete method");
	}
	
	//Approvel Starting
	public void currencyDenominationApprovel(){
		LOGGER.info("Entering into currencyDenominationApprovel method");
		setBooRenderApprove(true);
		setBooRenderDataTableApprove(false);
		setBooAdd(false);
		setBooSaveExit(false);
		setBooRenderApproveButton(false);
		clear();
		clearAllFields();
		currencyDenominationDtList.clear();
		newCurrencyDenominationLT.clear();
		try {
			//fetchRecordsDb();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "CurrencyDenominationApprovel.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/CurrencyDenominationApprovel.xhtml");
		}  catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::currencyDenominationApprovel");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		LOGGER.info("Exit into currencyDenominationApprovel method");
	}
	//Tofetch Record form Db to get Approvel
	public void fetchRecordsDb(){
		LOGGER.info("Entering into fetchRecordsDb method");
		currencyDenominationDtList.clear();
		listCurrencywiseDenomination.clear();
		try{
		List<CurrencyWiseDenomination> currencyWiseDenominationList=currencyDenominationService.getAllRecordsTofetchDB();
		if(currencyWiseDenominationList.size()>0){
			for (CurrencyWiseDenomination currencyWiseDenomination : currencyWiseDenominationList) {
				CurrencyDenominationDataTable currencyDenominationDTObj=new CurrencyDenominationDataTable();
				currencyDenominationDTObj.setDenominationid(currencyWiseDenomination.getDenominationId());
				currencyDenominationDTObj.setCountryId(currencyWiseDenomination.getFsCountryMaster().getCountryId());
				currencyDenominationDTObj.setCountryName(currencyDenominationService.CountryNameList(currencyWiseDenomination.getFsCountryMaster().getCountryId(),session.getLanguageId()));
				currencyDenominationDTObj.setCurrencyId(currencyWiseDenomination.getExCurrencyMaster().getCurrencyId());
				currencyDenominationDTObj.setCurrencyName(generalService.getCurrencyName(currencyWiseDenomination.getExCurrencyMaster().getCurrencyId()));
				currencyDenominationDTObj.setDenominationDesc(currencyWiseDenomination.getDenominationDesc());
				currencyDenominationDTObj.setDenomonationAmount(currencyWiseDenomination.getDenominationAmount());
				currencyDenominationDTObj.setRenderEditButton(true);
				currencyDenominationDTObj.setIsActive(currencyWiseDenomination.getIsActive());
				currencyDenominationDTObj.setCreatedBy(currencyWiseDenomination.getCreatedBy());
				currencyDenominationDTObj.setCreatedDate(currencyWiseDenomination.getCreatedDate());
				currencyDenominationDTObj.setModifiedBy(currencyWiseDenomination.getModifiedBy());
				currencyDenominationDTObj.setModifiedDate(currencyWiseDenomination.getModifiedDate());
				currencyDenominationDTObj.setDenominationCode(currencyWiseDenomination.getDenominationCode());
				currencyDenominationDtList.add(currencyDenominationDTObj);
			}
		}
	} catch (NullPointerException ne) {
		// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		setErrorMessage("Method Name::fetchRecordsDb");
		RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		return;
	} catch (Exception exception) {
		// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		setErrorMessage(exception.getMessage());
		RequestContext.getCurrentInstance().execute("error.show();");
		return;
	}
		LOGGER.info("Exit into fetchRecordsDb method");
		}
		
		//Approvel Checking for Users
		public void approvelCheck(CurrencyDenominationDataTable currencyDenominationDtObj){
			LOGGER.info("Entering into approvelCheck method");
			if (!(currencyDenominationDtObj.getModifiedBy()==null?currencyDenominationDtObj.getCreatedBy():currencyDenominationDtObj.getModifiedBy()).equalsIgnoreCase(session.getUserName())) {
				try {
				setCountryId(currencyDenominationDtObj.getCountryId());
				setCountryName(currencyDenominationDtObj.getCountryName());
				setCurrencyId(currencyDenominationDtObj.getCurrencyId());
				setCurrencyName(currencyDenominationDtObj.getCurrencyName());
				setDenominationid(currencyDenominationDtObj.getDenominationid());
				setDenominationCode(currencyDenominationDtObj.getDenominationCode());
				setDenominationDesc(currencyDenominationDtObj.getDenominationDesc());
				setDenomonationAmount(currencyDenominationDtObj.getDenomonationAmount());
				setCreatedBy(currencyDenominationDtObj.getCreatedBy());
				setCreatedDate(currencyDenominationDtObj.getCreatedDate());
				setModifiedBy(currencyDenominationDtObj.getModifiedBy());
				setModifiedDate(currencyDenominationDtObj.getModifiedDate());
				setIsActive(currencyDenominationDtObj.getIsActive());
				setBooAdd(false);
				setBooSaveExit(false);
				setBooRenderDataTable(false);
				setBooRenderApprove(true);
				setBooRenderApproveButton(false);
				setBooRenderDataTableApprove(false);
				setBooReadOnly(true);
				
					FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/CurrencyDenomination.xhtml");
				} catch (NullPointerException ne) {
					// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setErrorMessage("Method Name::approvelCheck");
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return;
				} catch (Exception exception) {
					// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}
			}else{
				RequestContext.getCurrentInstance().execute("notApproved.show();");	
			}
			LOGGER.info("Exit into approvelCheck method");
		}
		
		//approve for Record
		public void approve(){
			LOGGER.info("Entering into approve method");
			currencyDenominationService.approveRecord(getDenominationid(),session.getUserName());
			RequestContext.getCurrentInstance().execute("approve.show();");
			LOGGER.info("Exit into approve method");
			return;
		}
		
		public void clickOnSave(){
			LOGGER.info("Entering into clickOnSave method");
			currencyDenominationDtList.clear();
			addRecorsToDataTableForApproval();
			try {
					//fetchRecordsDb();
					
					/*setBooRenderApprove(true);
					setBooRenderDataTableApprove(true);
					setBooRenderApproveButton(true);*/
					FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/CurrencyDenominationApprovel.xhtml");
				}catch (NullPointerException ne) {
					// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
					setErrorMessage("Method Name::clickOnSave");
					RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					return;
				} catch (Exception exception) {
					// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
					setErrorMessage(exception.getMessage());
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}
			
			LOGGER.info("Exit into clickOnSave method");
			
		}
		
		public void cancel(){
			LOGGER.info("Entering into cancel method");
			try {
				//clear();
				clearAllFields();
				addRecorsToDataTableForApproval();
				setBooRenderDataTable(false);
				setBooRenderDataTableApprove(true);
				setBooRenderApproveButton(false);
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/CurrencyDenominationApprovel.xhtml");
			} catch (NullPointerException ne) {
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
			LOGGER.info("Exit into cancel method");
		}
	public void popupCurrency(){
		LOGGER.info("Entering into popupCurrency method");
		listCurrency=currencyDenominationService.getBasedOnCountyCurrency(getCountryId());
		LOGGER.info("Exit into popupCurrency method");
	}
	public void addRecorsToDataTableForApproval(){
		LOGGER.info("Entering into addRecorsToDataTableForApproval method");
		if (getCountryId() !=null && getCurrencyId() !=null) {
			currencyDenominationDtList.clear();
			listCurrencywiseDenomination.clear();
			try{
			List<CurrencyWiseDenomination> currencyWiseDenominationList=currencyDenominationService.getAllRecordsToApproveFromDb(getCountryId(),getCurrencyId());
			if(currencyWiseDenominationList.size()>0){
				for (CurrencyWiseDenomination currencyWiseDenomination : currencyWiseDenominationList) {
					/*if(!(currencyWiseDenomination.getModifiedBy() == null ?  currencyWiseDenomination.getCreatedBy() : currencyWiseDenomination.getModifiedBy()).equalsIgnoreCase(session.getUserName())){*/
						CurrencyDenominationDataTable currencyDenominationDTObj=new CurrencyDenominationDataTable();
						currencyDenominationDTObj.setDenominationid(currencyWiseDenomination.getDenominationId());
						currencyDenominationDTObj.setCountryId(currencyWiseDenomination.getFsCountryMaster().getCountryId());
						currencyDenominationDTObj.setCountryName(currencyDenominationService.CountryNameList(currencyWiseDenomination.getFsCountryMaster().getCountryId(),session.getLanguageId()));
						currencyDenominationDTObj.setCurrencyId(currencyWiseDenomination.getExCurrencyMaster().getCurrencyId());
						currencyDenominationDTObj.setCurrencyName(generalService.getCurrencyName(currencyWiseDenomination.getExCurrencyMaster().getCurrencyId()));
						currencyDenominationDTObj.setDenominationDesc(currencyWiseDenomination.getDenominationDesc());
						currencyDenominationDTObj.setDenominationCode(currencyWiseDenomination.getDenominationCode());
						currencyDenominationDTObj.setDenomonationAmount(currencyWiseDenomination.getDenominationAmount());
						currencyDenominationDTObj.setRenderEditButton(true);
						currencyDenominationDTObj.setIsActive(currencyWiseDenomination.getIsActive());
						currencyDenominationDTObj.setCreatedBy(currencyWiseDenomination.getCreatedBy());
						currencyDenominationDTObj.setCreatedDate(currencyWiseDenomination.getCreatedDate());
						currencyDenominationDTObj.setModifiedBy(currencyWiseDenomination.getModifiedBy());
						currencyDenominationDTObj.setModifiedDate(currencyWiseDenomination.getModifiedDate());
						currencyDenominationDtList.add(currencyDenominationDTObj);
				}
				setBooRenderDataTableApprove(true);
				setBooRenderApproveButton(true);
				setBooAdd(false);
				setBooSaveExit(false);
					}else{
						RequestContext.getCurrentInstance().execute("noRecord.show();");
						setBooRenderDataTableApprove(false);
						setBooRenderApproveButton(false);
						return;
					}
			
		}catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::addRecorsToDataTableForApproval");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
				}
			/*}else{
				RequestContext.getCurrentInstance().execute("noRecord.show();");
				setBooRenderDataTableApprove(false);
				setBooRenderApproveButton(false);
				return;
			}*/
				
				
		else{
			RequestContext.getCurrentInstance().execute("selectAnyOne.show();");
		}
		LOGGER.info("Exit into addRecorsToDataTableForApproval method");
	}
	
	
	public void approval(){
		for (CurrencyDenominationDataTable dataTable : selectCurrencys) {
			if(selectCurrencys !=null){
				if(dataTable.getDenomonationAmount() !=null){
					lstApproved.add(dataTable.getDenominationid());
				}
			}
		}
		if(lstApproved.size()>=1){
			String list;
			try {
				
			
			list=currencyDenominationService.approvalAllRecord(lstApproved,session.getUserName());
			if(list.equalsIgnoreCase("Success")){
			RequestContext.getCurrentInstance().execute("approved.show();");
			setBooRenderDataTableApprove(true);
			setBooRenderApproveButton(true);
			lstApproved.clear();
			return;
			}else{
				RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				return;
			}
			}catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::approval");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		}else{
			RequestContext.getCurrentInstance().execute("approvednot.show();");
			setBooRenderDataTableApprove(true);
			setBooRenderApproveButton(true);
		}
	}
	
	public void clickOnOkButton(){
		currencyDenominationDtList.clear();
		addRecorsToDataTableForApproval();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/CurrencyDenominationApprovel.xhtml");
		} catch (NullPointerException ne) {
			// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::clickOnOkButton");
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
	
	
