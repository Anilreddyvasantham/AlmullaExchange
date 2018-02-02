package com.amg.exchange.registration.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.ExternalContext;
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

import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.service.ICityMasterService;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component(value = "cityMasterBean")
@Scope("session")
public class CityMasterBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(CityMasterBean.class);

	private BigDecimal cityIdPk = null;
	private BigDecimal cityDescIdPk = null;
	private BigDecimal countryId = null;
	private String countryName = null;
	private String countryCode = null;
	private BigDecimal stateId = null;
	private String stateName = null;
	private String stateCode = null;
	private BigDecimal districtId = null;
	private String districtName = null;
	private String districtCode = null;
	private BigDecimal cityId = null;
	private String cityName = null;
	private String cityNameLocal = null;
	private String cityCode = null;
	private BigDecimal languageId = null;
	private String isActive = null;
	private String createdBy = null;
	private Date createdDate = null;
	private String modifiedBy = null;
	private Date modifiedDate = null;
	private String approvedBy = null;
	private Date approvedDate = null;
	private Boolean booRenderDataTable = false;
	private String dynamicLabelForActivateDeactivate;
	private String remarks = null;
	private Boolean disableSubmit = false;
	private String selectType=null;
	private Boolean radioRender = true;
	private Boolean manualRender = false;
	private Boolean fileRender = false;
	private Boolean editHide = false;
	private String approvedDateString = null;
	private Boolean editRecord = false;
	String errmsg;
	/* Responsible to populate State,District, City in emp Details */
	private List<CountryMasterDesc> countryList= new ArrayList<CountryMasterDesc>();
	private List<StateMasterDesc> stateList = new ArrayList<StateMasterDesc>();
	private List<DistrictMasterDesc> districtList = new ArrayList<DistrictMasterDesc>();
	private List<CityMasterDesc> cityList = new ArrayList<CityMasterDesc>();
	private List<CityMaster> cityMasterList = new ArrayList<CityMaster>();

	Map<BigDecimal, String> mapCountryList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapStateList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapDistirictList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapCityList = new HashMap<BigDecimal, String>();

	//Map<String, String> mapCountryListCode = new HashMap<String, String>();
	//Map<String, String> mapStateListCode = new HashMap<String, String>();
	Map<BigDecimal, String> mapDistrictListCode = new HashMap<BigDecimal, String>();

	private List<CityMasterDataTable> datatableList = new CopyOnWriteArrayList<CityMasterDataTable>();
	private List<CityMasterDataTable> datatableNewList = new CopyOnWriteArrayList<CityMasterDataTable>();

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	ICityMasterService<T> cityMasterService;

	SessionStateManage sessionStateManage = new SessionStateManage();


	public BigDecimal getCityIdPk() {
		return cityIdPk;
	}
	public void setCityIdPk(BigDecimal cityIdPk) {
		this.cityIdPk = cityIdPk;
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
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public BigDecimal getStateId() {
		return stateId;
	}
	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public BigDecimal getDistrictId() {
		return districtId;
	}
	public void setDistrictId(BigDecimal districtId) {
		this.districtId = districtId;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public BigDecimal getCityId() {
		return cityId;
	}
	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityNameLocal() {
		return cityNameLocal;
	}
	public void setCityNameLocal(String cityNameLocal) {
		this.cityNameLocal = cityNameLocal;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public BigDecimal getLanguageId() {
		return languageId;
	}
	public void setLanguageId(BigDecimal languageId) {
		this.languageId = languageId;
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
	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}
	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Boolean getDisableSubmit() {
		return disableSubmit;
	}
	public void setDisableSubmit(Boolean disableSubmit) {
		this.disableSubmit = disableSubmit;
	}
	public String getSelectType() {
		return selectType;
	}
	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}


	public Boolean getRadioRender() {
		return radioRender;
	}
	public void setRadioRender(Boolean radioRender) {
		this.radioRender = radioRender;
	}
	public Boolean getManualRender() {
		return manualRender;
	}
	public void setManualRender(Boolean manualRender) {
		this.manualRender = manualRender;
	}
	public Boolean getFileRender() {
		return fileRender;
	}
	public void setFileRender(Boolean fileRender) {
		this.fileRender = fileRender;
	}

	public Boolean getEditHide() {
		return editHide;
	}
	public void setEditHide(Boolean editHide) {
		this.editHide = editHide;
	}
	public String getApprovedDateString() {
		return approvedDateString;
	}
	public void setApprovedDateString(String approvedDateString) {
		this.approvedDateString = approvedDateString;
	}
	public Boolean getEditRecord() {
		return editRecord;
	}
	public void setEditRecord(Boolean editRecord) {
		this.editRecord = editRecord;
	}
	public List<CountryMasterDesc> getCountryList() {
		return countryList;
	}
	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}
	public List<StateMasterDesc> getStateList() {
		return stateList;
	}
	public void setStateList(List<StateMasterDesc> stateList) {
		this.stateList = stateList;
	}
	public List<DistrictMasterDesc> getDistrictList() {
		return districtList;
	}
	public void setDistrictList(List<DistrictMasterDesc> districtList) {
		this.districtList = districtList;
	}
	public List<CityMasterDesc> getCityList() {
		return cityList;
	}
	public void setCityList(List<CityMasterDesc> cityList) {
		this.cityList = cityList;
	}

	public List<CityMasterDataTable> getDatatableList() {
		return datatableList;
	}
	public void setDatatableList(List<CityMasterDataTable> datatableList) {
		this.datatableList = datatableList;
	}

	private Date currentTime = new Date();

	public Date getCurrentTime() {

		Date objList = generalService.getSysDateTimestamp(sessionStateManage.getCountryId());


		if(objList != null){
			currentTime = objList;}
		else{

			//currentTime.getTime();
			currentTime =null;
		}

		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public void populateCountryList(){
		setStateId( null);
		List<CountryMasterDesc> countryList = generalService.getCountryList(sessionStateManage.getLanguageId());
		if(countryList.size()!=0){
			for (CountryMasterDesc countryMasterDesc : countryList) {
				mapCountryList.put(countryMasterDesc.getFsCountryMaster().getCountryId(),countryMasterDesc.getFsCountryMaster().getCountryCode());
				//mapCountryListCode.put(countryMasterDesc.getFsCountryMaster().getCountryCode(), countryMasterDesc.getCountryName());
				//setCountryCode(countryMasterDesc.getFsCountryMaster().getCountryCode());
			}
			setCountryList(countryList);
		}
 
	}

	public void populateState() {
		setStateId( null);
		setDistrictList(null);
		if(getDistrictList() != null){
			districtList.clear();
		}
		clearCityDetails();
		setCountryCode(mapCountryList.get(getCountryId()));

		List<StateMasterDesc> lstState = generalService.getStateList(sessionStateManage.getLanguageId(),getCountryId());

		for (StateMasterDesc stateMasterDesc : lstState) {
			if(stateMasterDesc.getFsStateMaster() != null){
				mapStateList.put(stateMasterDesc.getFsStateMaster().getStateId(),stateMasterDesc.getFsStateMaster().getStateCode());
			}

			//mapStateListCode.put(stateMasterDesc.getFsStateMaster().getStateCode(), stateMasterDesc.getStateName());
			//setStateCode(stateMasterDesc.getFsStateMaster().getStateCode());
		}
		setStateList(lstState);
	}

	public void populateDistrict() {
		
		clearCityDetails();
		setStateCode(mapStateList.get(getStateId()));

		List<DistrictMasterDesc> lstDistrict = generalService.getDistrictList(sessionStateManage.getLanguageId(),getCountryId(), getStateId());

		for (DistrictMasterDesc districtMasterDesc : lstDistrict) {
			if(districtMasterDesc.getDistrict() != null){
				mapDistirictList.put(districtMasterDesc.getFsDistrictMaster().getDistrictId(), districtMasterDesc.getDistrict());
			}
			if(districtMasterDesc.getFsDistrictMaster() != null){
				mapDistrictListCode.put(districtMasterDesc.getFsDistrictMaster().getDistrictId(), districtMasterDesc.getFsDistrictMaster().getDistrictCode());
			}
			

		}
		setDistrictList(lstDistrict);

	}
	
	public void populateDistrictId() {

		List<DistrictMasterDesc> lstDistrict = generalService.getDistrictList(sessionStateManage.getLanguageId(),getCountryId(), getStateId());
		
		for (DistrictMasterDesc districtMasterDesc : lstDistrict) {
			if(districtMasterDesc.getDistrict() != null){
				mapDistirictList.put(districtMasterDesc.getFsDistrictMaster().getDistrictId(), districtMasterDesc.getDistrict());
			}
			if(districtMasterDesc.getFsDistrictMaster() != null){
				mapDistrictListCode.put(districtMasterDesc.getFsDistrictMaster().getDistrictId(), districtMasterDesc.getFsDistrictMaster().getDistrictCode());
			}
		}
		
	}
	
	

	public void clear(){

		setCountryId(null);
		setDistrictId(null);
		setStateId(null);
		setCityName(null);
		setCityNameLocal(null);
		setCityCode(null);
		setIsActive(null);
		setCityIdPk(null);
		setSelectType(null);
		setCountryCode(null);
		setStateCode(null);
		setDistrictCode(null);

	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigate(){

		try {
			clear();
			
			setCountryList(null);
			if(getCountryList() != null){
				countryList.clear();
			}
			
			setStateList(null);
			if(getStateList() != null){
				stateList.clear();
			}
			
			setDistrictList(null);
			if(getDistrictList() != null){
				districtList.clear();
			}
			
			mapCountryList.clear();
			mapStateList.clear();
			mapDistirictList.clear();
			mapDistrictListCode.clear();
			
			datatableList.clear();
			datatableNewList.clear();
			setRadioRender(true);
			setManualRender(false);
			setFileRender(false);
			setBooRenderDataTableFile(false);
			setBooRenderDataTable(false);
			setBooRenderApprovalDataTable(false);
			populateCountryList();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "citymaster.xhtml");
						ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../registration/citymaster.xhtml");
	

		} catch (Exception e) {
			LOG.info("Problem to redirect: " + e);
		}

	}

	public void saveDataTabel(){
		  try{
		boolean duplicate=false;
		boolean alreadyDT = false;
		if(getRecordAvaliable()){
			duplicate=true;
		}else if(datatableList.size() != 0){
			for(CityMasterDataTable cityMasterData:datatableList){
				if(cityMasterData.getCountryCode()!=null && cityMasterData.getStateCode()!=null && cityMasterData.getDistrictCode()!=null && cityMasterData.getCityCode()!=null){
					if(getCountryCode().equalsIgnoreCase(cityMasterData.getCountryCode()) && getStateCode().equalsIgnoreCase(cityMasterData.getStateCode())
							&& getDistrictCode().equalsIgnoreCase(cityMasterData.getDistrictCode())){
						
						if(cityMasterData.getCityName() != null && getCityName().equalsIgnoreCase(cityMasterData.getCityName())){
							duplicate=true;
							break;
						}
						
						if(cityMasterData.getCityNameLocal() != null && getCityNameLocal().equalsIgnoreCase(cityMasterData.getCityNameLocal())){
							duplicate=true;
							break;
						}
						
						if(getCityCode()!= null && getCityCode().equalsIgnoreCase(cityMasterData.getCityCode())){
							duplicate=true;
							break;
						}
						
					}
				}
			}
		}else{
			duplicate=false;
		}
		boolean alreadyDB = false;
		if (!alreadyDT) {
			  if(getCityIdPk()==null ){
				    List<CityMaster> alreadyExist=cityMasterService.toCheckBasedOnCountryBasedOnStateBasedOnDistBasedOnCityCode(getCountryCode(),getStateCode(),getDistrictId(),getCityCode());
				    if(alreadyExist != null && alreadyExist.size()!=0){
					      alreadyDB=true;
						  RequestContext.getCurrentInstance().execute("csp.show();");
						  setErrmsg("The City Code already exist");
						  setCityName(null);
						  setCityNameLocal(null); 
			  }else{
				    List<CityMasterDesc> listCityEng = cityMasterService.toFetchCityDesc(getCityName(),getCountryCode(),getStateCode(),getDistrictCode());
					List<CityMasterDesc> listrArb = cityMasterService.toFetchCityDesc(getCityNameLocal(),getCountryCode(),getStateCode(),getDistrictCode());
					if(listCityEng != null && listCityEng.size() !=0){
						  alreadyDB=true;
						  RequestContext.getCurrentInstance().execute("csp.show();");
						  setErrmsg("The City name already exist");
						  setCityName(null);
						  setCityNameLocal(null); 
					}else if (listrArb != null && listrArb.size() !=0) {
						  alreadyDB=true;
						  RequestContext.getCurrentInstance().execute("csp.show();");
						  setErrmsg("The City name already exist");
						  setCityName(null);
						  setCityNameLocal(null); 
					}
			  }
			  }
		}else{
			  alreadyDB=false;	  
		}

		if(!duplicate && !alreadyDB){
			  
			CityMasterDataTable cityMasterData = new CityMasterDataTable();
			
			cityMasterData.setCountryId(getCountryId());
			
			cityMasterData.setCountryCode(getCountryCode());
					
			cityMasterData.setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), getCountryId()));

			cityMasterData.setStateId(getStateId());
			
			cityMasterData.setStateCode(getStateCode());
			
			cityMasterData.setStateName(generalService.getStateName(sessionStateManage.getLanguageId(), getCountryId(),getStateId()));
			
			cityMasterData.setDistrictId(getDistrictId());
			
			cityMasterData.setDistrictCode(getDistrictCode());
			String districName=generalService.getDistrictName(sessionStateManage.getLanguageId(), getCountryId(),getStateId(), getDistrictId());
			cityMasterData.setDistrictName(districName);
			
			cityMasterData.setCityCode(getCityCode());

			cityMasterData.setCityName(getCityName());
			
			cityMasterData.setCityNameLocal(getCityNameLocal());
			
			cityMasterData.setCityDescIdEnglishPk(getEngCityMasterDescId());
			cityMasterData.setCityDescIdLocalPk(getArbCityMasterDescId());
			cityMasterData.setEngLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			cityMasterData.setArbLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			cityMasterData.setCreatedBy(getCreatedBy());
			cityMasterData.setCreatedDate(getCreatedDate());
			if(getCityIdPk()!=null){
				  if(getCityMasterDTOBJ() != null){
					    if(cityMasterData.getCountryCode().equals(cityMasterDTOBJ.getCountryCode()) && cityMasterData.getStateCode().equals(cityMasterDTOBJ.getStateCode())
						&& cityMasterData.getDistrictCode().equals(cityMasterDTOBJ.getDistrictCode()) && cityMasterData.getCityCode().equalsIgnoreCase(cityMasterDTOBJ.getCityCode())
						&& cityMasterData.getCityName().equalsIgnoreCase(cityMasterDTOBJ.getCityName()) && cityMasterData.getCityNameLocal().equalsIgnoreCase(cityMasterDTOBJ.getCityNameLocal())){
						      cityMasterData.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
						      cityMasterData.setIsActive(getIsActive());
						      cityMasterData.setModifiedBy(getModifiedBy());
						      cityMasterData.setModifiedDate(getModifiedDate());
						      cityMasterData.setApprovedBy(getApprovedBy());
						      cityMasterData.setApprovedDate(getApprovedDate());
						      cityMasterData.setRemarks(getRemarks());
						      cityMasterData.setCityIdPk(getCityIdPk());
					    }else{
						      cityMasterData.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						      cityMasterData.setIsActive(Constants.U);
							cityMasterData.setModifiedBy(sessionStateManage.getUserName());
							cityMasterData.setModifiedDate(new Date());
							cityMasterData.setApprovedBy(null);
							cityMasterData.setApprovedDate(null);
							cityMasterData.setRemarks(null);
							cityMasterData.setCityIdPk(getCityIdPk());
							cityMasterData.setIfEditClicked(true);
					    }
				  }
				
				
				/*cityMasterData.setCreatedBy(getCreatedBy());
				cityMasterData.setCreatedDate(getCreatedDate());
				cityMasterData.setModifiedBy(getModifiedBy());
				cityMasterData.setModifiedDate(getModifiedDate());
				cityMasterData.setApprovedBy(getApprovedBy());
				cityMasterData.setApprovedDate(getApprovedDate());
				cityMasterData.setIsActive(getIsActive());
				cityMasterData.setRemarks(getRemarks());
				cityMasterData.setCityIdPk(getCityIdPk());
				if(getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.Yes)){
					cityMasterData.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				}else if(getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.D)){
					cityMasterData.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				}else if(getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.U)){
					cityMasterData.setDynamicLabelForActivateDeactivate(Constants.DELETE);
				}
*/
			}else{
				/*cityMasterData.setCityIdPk(getCityMasterId());
				cityMasterData.setCityDescIdLocalPk(getEngCityMasterDescId());
				cityMasterData.setCityDescIdEnglishPk(getArbCityMasterDescId());
				cityMasterData.setEngLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
				cityMasterData.setArbLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
				cityMasterData.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				cityMasterData.setCreatedBy(sessionStateManage.getUserName());
				cityMasterData.setCreatedDate(new Date());
				cityMasterData.setIsActive(Constants.U);
				
				datatableNewList.add(cityMasterData);*/
				  cityMasterData.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				  cityMasterData.setIsActive(Constants.U);
				  cityMasterData.setCreatedBy(sessionStateManage.getUserName());
				  cityMasterData.setCreatedDate(new Date());
				  cityMasterData.setIfEditClicked(true);
			}

			datatableList.add(cityMasterData);
			 if (getCityIdPk() == null) {
				   datatableNewList.add(cityMasterData);
			    }
			setBooRenderDataTable(true);
			setBooRenderDataTableFile(false);
			setDisableSubmit(false);
			setEditHide(false);
			setBooRenderApprovalDataTable(false);

			clearFields();
			
		}else{
			//RequestContext.getCurrentInstance().execute("duplicate.show();");
			  alreadyDB=true;
			  RequestContext.getCurrentInstance().execute("csp.show();");
			  setErrmsg("The Record already exist");
			  setCityCode(null);
			  setCityName(null);
			  setCityNameLocal(null);
			  return;
		}
		  }catch(Exception exception){
			    setErrorMessage(exception.getMessage());
			    LOG.info("exception.getMessage()::::::::::::::::::::::::::::"+exception.getMessage());
			    RequestContext.getCurrentInstance().execute("exception.show();");
			    return;
		  }

	}

	public void view(){
		try{
		datatableList.clear();
		setRecordAvaliable(false); 

		if(getCountryCode() != null &&getStateCode() != null  && getDistrictCode() != null){
			
			cityMasterList = cityMasterService.viewAllRecord(getCountryCode(), getStateCode(), getDistrictCode());

			if(cityMasterList.size()>0 ){

				addRecordsToDatatable(cityMasterList);
				
				if(datatableList.size()!=0)
				{
					datatableList.addAll(datatableNewList);
				}
				
				setBooRenderDataTable(true);

			}else{

				if(datatableNewList.size()!=0)
				{
					datatableList.addAll(datatableNewList);
					setBooRenderDataTable(true);
					setEditHide(false);
				}else{
					setBooRenderDataTable(false);
					RequestContext.getCurrentInstance().execute("norecord.show();");
				}
			}
		}else{
			RequestContext.getCurrentInstance().execute("mandatoryfields.show();");
		}
		}catch(Exception exception){
			  setErrorMessage(exception.getMessage());
			    LOG.info("exception.getMessage()::::::::::::::::::::::::::::"+exception.getMessage());
			    RequestContext.getCurrentInstance().execute("exception.show();");
			    return;  
		}

	}

	private void addRecordsToDatatable(List<CityMaster> cityMasterList)
	{
		//clear();
		for(CityMaster cityMaster: cityMasterList){
			
			try{
				
				CityMasterDataTable masterDataTable = new CityMasterDataTable();
				
				masterDataTable.setCityIdPk(cityMaster.getCityId());

				masterDataTable.setCountryCode(cityMaster.getCountryCode());
				
				setCountryCode(cityMaster.getCountryCode());
				
				if(cityMaster.getCountryCode()!=null){
					popCountryId();
				}else{
					setCountryId(null);
				}
				if(getCountryId()!=null){
					masterDataTable.setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), getCountryId()));
				}

				masterDataTable.setStateCode(cityMaster.getStateCode());
				
				setStateCode(cityMaster.getStateCode());
				
				if(cityMaster.getStateCode()!=null){
					popStateId();
				}else{
					setStateId(null);
				}
				
				if(getStateId()!=null){
					masterDataTable.setStateName(generalService.getStateName(sessionStateManage.getLanguageId(), getCountryId() , getStateId()));
				}
				
				masterDataTable.setDistrictId(cityMaster.getFsDistrictMaster().getDistrictId());
				
				masterDataTable.setDistrictCode(cityMaster.getDistrictCode());
				
				populateDistrictId();
				
				masterDataTable.setDistrictName(mapDistirictList.get(cityMaster.getFsDistrictMaster().getDistrictId()));
				
				masterDataTable.setIsActive(cityMaster.getIsActive());

				masterDataTable.setCityCode(cityMaster.getCityCode());
				
				masterDataTable.setCreatedBy(cityMaster.getCreatedBy());
				
				masterDataTable.setCreatedDate(cityMaster.getCreatedDate());
				
				if(cityMaster.getModifiedBy()!=null){
					masterDataTable.setModifiedBy(cityMaster.getModifiedBy());
					masterDataTable.setModifiedDate(cityMaster.getModifiedDate());
				}
				
				if(cityMaster.getApprovedBy()!=null){
					masterDataTable.setApprovedBy(cityMaster.getApprovedBy());
					masterDataTable.setApprovedDate(cityMaster.getApprovedDate());
				}
				
				if(cityMaster.getRemarks()!=null){
					masterDataTable.setRemarks(cityMaster.getRemarks());
				}
				if(cityMaster.getIsActive() != null){
				if(cityMaster.getIsActive().equalsIgnoreCase(Constants.Yes)){
					masterDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				}else if(cityMaster.getIsActive().equalsIgnoreCase(Constants.D)){
					masterDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				}else if (cityMaster.getIsActive().equalsIgnoreCase(Constants.U) && cityMaster.getModifiedBy() == null && cityMaster.getModifiedDate() == null && cityMaster.getApprovedBy() == null
						&& cityMaster.getApprovedDate() == null && cityMaster.getRemarks() == null) {
					masterDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
				} else {
					masterDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				}
				}

				List<CityMasterDesc> cityDescList = cityMasterService.viewDescRecord(cityMaster.getCityId());
				for(CityMasterDesc descList:cityDescList){

					if(descList.getFsLanguageType().getLanguageId().intValue()==1){
						masterDataTable.setCityDescIdEnglishPk(descList.getCityMasterId());

						masterDataTable.setCityName(descList.getCityName());
						masterDataTable.setEngLanguageId(descList.getFsLanguageType().getLanguageId());
					}

					if(descList.getFsLanguageType().getLanguageId().intValue()==2){

						masterDataTable.setCityNameLocal(descList.getCityName());
						masterDataTable.setCityDescIdLocalPk(descList.getCityMasterId());
						masterDataTable.setArbLanguageId(descList.getFsLanguageType().getLanguageId());
					}

				}

				datatableList.add(masterDataTable);	
				
			}catch(Exception exception){
				  setErrorMessage(exception.getMessage());
				    LOG.info("exception.getMessage()::::::::::::::::::::::::::::"+exception.getMessage());
				    RequestContext.getCurrentInstance().execute("exception.show();");
				    return;  
			}

		}
		
		setBooRenderDataTableFile(false);
	}

	@SuppressWarnings("unchecked")
	public void saveMaster(){
		
		if(datatableList.size() != 0){
			  try{
			for(CityMasterDataTable dataTable:datatableList){
				  if(dataTable.getIfEditClicked().equals(true)){
				CityMaster cityMaster = new CityMaster();
				cityMaster.setCityCode(dataTable.getCityCode());
				DistrictMaster districtMaster = new DistrictMaster();
				//countryMaster.setCountryId(dataTable.getCountryId());
				//cityMaster.setFsCountryMaster(countryMaster);
				//stateMaster.setStateId(dataTable.getStateId());
				cityMaster.setCountryCode(dataTable.getCountryCode());
				cityMaster.setStateCode(dataTable.getStateCode());
				districtMaster.setDistrictId(dataTable.getDistrictId());
				cityMaster.setFsDistrictMaster(districtMaster);
				cityMaster.setIsActive(dataTable.getIsActive());
				cityMaster.setDistrictCode(dataTable.getDistrictCode());

				/*if(dataTable.getCityIdPk()!=null){*/
					cityMaster.setCityId(dataTable.getCityIdPk());
					cityMaster.setCreatedBy(dataTable.getCreatedBy());
					cityMaster.setCreatedDate(dataTable.getCreatedDate());
					cityMaster.setModifiedBy(dataTable.getModifiedBy());
					cityMaster.setModifiedDate(dataTable.getModifiedDate());
					cityMaster.setApprovedBy(dataTable.getApprovedBy());
					cityMaster.setApprovedDate(dataTable.getApprovedDate());

				/*}else{
					cityMaster.setCreatedBy(sessionStateManage.getUserName());
					cityMaster.setCreatedDate(getCurrentTime());

				}*/
				//generalService.saveOrUpdateWithPartialSave((T) cityMaster);

				CityMasterDesc desc = new CityMasterDesc();
				LanguageType languageType = new LanguageType();
				languageType.setLanguageId(new BigDecimal(1));
				desc.setFsLanguageType(languageType);
				desc.setCityName(dataTable.getCityName());
				desc.setCityMasterId(dataTable.getCityDescIdEnglishPk());
				desc.setFsCityMaster(cityMaster);
				//generalService.saveOrUpdateWithPartialSave((T) desc);
				CityMasterDesc desc1 = new CityMasterDesc();
				LanguageType languageType1 = new LanguageType();
				languageType1.setLanguageId(new BigDecimal(2));
				desc1.setFsLanguageType(languageType1);
				desc1.setCityName(dataTable.getCityNameLocal());
				 desc1.setCityMasterId(dataTable.getCityDescIdLocalPk());
				desc1.setFsCityMaster(cityMaster);
				//generalService.saveOrUpdateWithPartialSave((T) desc1);
				
				cityMasterService.saveAllRecorsToDB(cityMaster,desc,desc1);
				
				  }	

			}
			RequestContext.getCurrentInstance().execute("complete.show();");
			datatableNewList.clear();
			return;
			  }catch(Exception exception){
				    setErrorMessage(exception.getMessage());
				    LOG.info("exception.getMessage()::::::::::::::::::::::::::::"+exception.getMessage());
				    RequestContext.getCurrentInstance().execute("exception.show();");
				    return;
			  }
		}else{
			RequestContext.getCurrentInstance().execute("norecord.show();");
		}

	}

	public void editRecord(CityMasterDataTable dataTable){
		
		//clear();
		  setCityMasterDTOBJ(dataTable);
		setRecordAvaliable(false); 
		
		setBooRenderDataTableFile(false);
		setDisableSubmit(true);
		setEditHide(true);
		
		setCityIdPk(dataTable.getCityIdPk());
		
		setEngCityMasterDescId(dataTable.getCityDescIdEnglishPk());
		setArbCityMasterDescId(dataTable.getCityDescIdLocalPk());
		
		setCountryCode(dataTable.getCountryCode());
		popCountryId();
		//22/04/2016
		//popCountry();
		//setCountryId(getCountryId());
		
		setStateCode(dataTable.getStateCode());
		popStateId();
		populateStateList();
		setStateId(getStateId());
		
		popDistrict();
		setDistrictId(dataTable.getDistrictId());
		setDistrictCode(dataTable.getDistrictCode());
		
		setCityName(dataTable.getCityName());
		setCityNameLocal(dataTable.getCityNameLocal());
		setCityCode(dataTable.getCityCode());
		setIsActive(dataTable.getIsActive());
		setRemarks(dataTable.getRemarks());
		setCreatedBy(dataTable.getCreatedBy());
		setCreatedDate(dataTable.getCreatedDate());
		
		/*if(dataTable.getIsActive().equalsIgnoreCase(Constants.Yes)){
			setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
		}else if(dataTable.getIsActive().equalsIgnoreCase(Constants.D)){
			setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
		}else if(dataTable.getIsActive().equalsIgnoreCase(Constants.U)){
			setDynamicLabelForActivateDeactivate(Constants.DELETE);
		}*/
		
		setDynamicLabelForActivateDeactivate(dataTable.getDynamicLabelForActivateDeactivate());
		
		if(dataTable.getModifiedBy()!=null){
			setModifiedBy(dataTable.getModifiedBy());
			setModifiedDate(dataTable.getModifiedDate());
		}
		if(dataTable.getApprovedBy()!=null){
			setApprovedBy(dataTable.getApprovedBy());
			setApprovedDate(dataTable.getApprovedDate());
		}

		datatableList.remove(dataTable);

		datatableNewList.remove(dataTable);

		if(datatableList.size()==0){
			setBooRenderDataTable(false);
			setBooRenderApprovalDataTable(false);
		}


	}

	public void exit() throws IOException {
		  datatableList.clear();
		  datatableNewList.clear();
		if(sessionStateManage.getRoleId().equalsIgnoreCase(Constants.USER_ROLE_MANAGER)){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void okSaved(){

		clear();
		datatableList.clear();
		datatableNewList.clear();
		setBooRenderDataTable(false);
		setBooRenderDataTableFile(false);
		setBooRenderApprovalDataTable(false);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/citymaster.xhtml");
		} catch (Exception e) {
			LOG.info("Exception="+e);
		}
	}

	public void changeType(){
		setRadioRender(false);
		setBooRenderDataTableFile(false);
		setBooRenderDataTable(false);
		if(getSelectType().equalsIgnoreCase("manual")){
			setManualRender(true);
			setFileRender(false);
		}else{
			setManualRender(false);
			setFileRender(true);
		}

	}
	//for approval

	private List<CityMasterDataTable> cityListForAproval=new CopyOnWriteArrayList<CityMasterDataTable>();

	private List<CityMasterDataTable> cityListFoXlx=new CopyOnWriteArrayList<CityMasterDataTable>();	

	public List<CityMasterDataTable> getCityListForAproval() {
		return cityListForAproval;
	}



	public void setCityListForAproval(List<CityMasterDataTable> cityListForAproval) {
		this.cityListForAproval = cityListForAproval;
	}



	public List<CityMasterDataTable> getCityListFoXlx() {
		return cityListFoXlx;
	}
	public void setCityListFoXlx(List<CityMasterDataTable> cityListFoXlx) {
		this.cityListFoXlx = cityListFoXlx;
	}
	public void navigateToApprovalList() throws IOException{
		 
		  try {
				clear();
				
				setCountryList(null);
				if(getCountryList() != null){
					countryList.clear();
				}
				
				setStateList(null);
				if(getStateList() != null){
					stateList.clear();
				}
				
				setDistrictList(null);
				if(getDistrictList() != null){
					districtList.clear();
				}
				
				mapCountryList.clear();
				mapStateList.clear();
				mapDistirictList.clear();
				mapDistrictListCode.clear();
				
				datatableList.clear();
				datatableNewList.clear();
				setRadioRender(false);
				setManualRender(false);
				setFileRender(false);
				setBooRenderDataTableFile(false);
				setBooRenderDataTable(false);
				setBooRenderApprovalDataTable(false);
				populateCountryList();
				cityListFoXlx.clear();
				setCountryId( null);
				setStateId( null);
				setDistrictId( null);
			 
				loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "citymasterapprovallist.xhtml");
		FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/citymasterapprovallist.xhtml");
		  }catch(Exception e){
			    e.printStackTrace();
		  }
	}

	public void getUnApprovalRecords(){
		cityListForAproval.clear();
		if(getCountryCode() != null && getStateCode() != null && getDistrictCode() != null){
				
				cityMasterList = cityMasterService.toFetchRecordsForApproval(getCountryCode(), getStateCode(), getDistrictCode());

	if(cityMasterList !=null && cityMasterList.size()!=0 ){
		//List<CityMaster> cityMasterList=cityMasterService.viewUnApproveRecord();
		for(CityMaster cityMaster:cityMasterList){

			CityMasterDataTable masterDataTable = new CityMasterDataTable();
			masterDataTable.setCityIdPk(cityMaster.getCityId());
			masterDataTable.setCountryCode(cityMaster.getCountryCode());
			setCountryCode(cityMaster.getCountryCode());
			if(cityMaster.getCountryCode()!=null){
				popCountryId();
				masterDataTable.setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), getCountryId()));
			}else{
				setCountryId(null);
			}

			masterDataTable.setStateCode(cityMaster.getStateCode());
			setStateCode(cityMaster.getStateCode());
			if(cityMaster.getStateCode()!=null){
				popStateId();
				masterDataTable.setStateName(generalService.getStateName(sessionStateManage.getLanguageId(), getCountryId(),getStateId()));
			}

			masterDataTable.setDistrictId(cityMaster.getFsDistrictMaster().getDistrictId());
			masterDataTable.setDistrictName(generalService.getDistrictName(sessionStateManage.getLanguageId(), getCountryId(),getStateId(),masterDataTable.getDistrictId() ));
			masterDataTable.setDistrictCode(cityMaster.getDistrictCode());
			masterDataTable.setIsActive(cityMaster.getIsActive());

			masterDataTable.setCityCode(cityMaster.getCityCode());
			masterDataTable.setCreatedBy(cityMaster.getCreatedBy());
			masterDataTable.setCreatedDate(cityMaster.getCreatedDate());
			if(cityMaster.getModifiedBy()!=null){
				masterDataTable.setModifiedBy(cityMaster.getModifiedBy());
				masterDataTable.setModifiedDate(cityMaster.getModifiedDate());
			}

			if(cityMaster.getRemarks()!=null){
				masterDataTable.setRemarks(cityMaster.getRemarks());
			}

			List<CityMasterDesc> cityDescList = cityMasterService.viewDescRecord(cityMaster.getCityId());
			for(CityMasterDesc descList:cityDescList){

				if(descList.getFsLanguageType().getLanguageId().intValue()==1){
					masterDataTable.setCityDescIdEnglishPk(descList.getCityMasterId());

					masterDataTable.setCityName(descList.getCityName());
					masterDataTable.setEngLanguageId(descList.getFsLanguageType().getLanguageId());
				}

				if(descList.getFsLanguageType().getLanguageId().intValue()==2){

					masterDataTable.setCityNameLocal(descList.getCityName());
					masterDataTable.setCityDescIdLocalPk(descList.getCityMasterId());
					masterDataTable.setArbLanguageId(descList.getFsLanguageType().getLanguageId());
				}

			}

			cityListForAproval.add(masterDataTable);
			setBooRenderApprovalDataTable(true);
			setBooRenderDataTable(false);
		}
		}else{
			  setBooRenderApprovalDataTable(false);
			  setBooRenderDataTable(false);
		}

		}else{
			RequestContext.getCurrentInstance().execute("mandatoryfields.show();");
			return;
		}

	}

	public void goToApproval(CityMasterDataTable dataTable){

		/*if(CommonUtil.validateApprovedBy(sessionStateManage.getUserName(), dataTable.getCreatedBy())!= true){*/
			  if (!(dataTable.getModifiedBy() == null ? dataTable.getCreatedBy() : dataTable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())) {
			setCityIdPk(dataTable.getCityIdPk());
			setCountryCode(dataTable.getCountryCode());
			popCountryId();
			setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(), getCountryId()));
			setStateCode(dataTable.getStateCode());
			popStateId();
			setStateName(generalService.getStateName(sessionStateManage.getLanguageId(), getCountryId(),getStateId()));
			popDistrict();
			setDistrictId(dataTable.getDistrictId());
			//setDistrictName(mapDistirictList.get(dataTable.getDistrictId()));
			setDistrictName(generalService.getDistrictName(sessionStateManage.getLanguageId(), getCountryId(),getStateId(),dataTable.getDistrictId()));
			
			setDistrictCode(dataTable.getDistrictCode());
			setCityName(dataTable.getCityName());
			setCityNameLocal(dataTable.getCityNameLocal());
			setCityCode(dataTable.getCityCode());
			setCreatedBy(dataTable.getCreatedBy());
			setCreatedDate(dataTable.getCreatedDate());

			if(dataTable.getModifiedBy()!=null){
				setModifiedBy(dataTable.getModifiedBy());
				setModifiedDate(dataTable.getModifiedDate());
			}

			try {

				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../registration/citymasterapproval.xhtml");

			} catch (Exception e) {
				LOG.info("Exception= "+e);

			}

		}else{

			RequestContext.getCurrentInstance().execute("notabletoApprove.show();");

		}
	}


	public void approve(){

	 		cityMasterService.approve(getCityIdPk(), sessionStateManage.getUserName(),getCurrentTime());
			RequestContext.getCurrentInstance().execute("approvedsuccess.show();");

		 
	}

	public void clickOnOkApprovalForCityMaster(){
		  try {
				clearCityDetails();
				setBooRenderDataTable(false);
				//setBooAdd(false);
				//setBooSaveExit(false);
				try {
					  getUnApprovalRecords();
					  FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/citymasterapprovallist.xhtml");
				} catch (Exception e1) {
				}
			} catch (Exception e) {
			}
	}
	public void clickOKApproval(){
		  cityMasterService.softDelete(getCityIdPk(), sessionStateManage.getUserName(),getCurrentTime(),getRemarks());
		  view();

	}

	public void checkStatusType(CityMasterDataTable dataTable) throws IOException {
		if(dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
			RequestContext.getCurrentInstance().execute("pending.show();");
			return;     
		}else if(dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
			datatableList.remove(dataTable);
			datatableNewList.remove(dataTable);    
		}else if(dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)){
			setIsActive(dataTable.getIsActive());
			setCityIdPk(dataTable.getCityIdPk());
			setApprovedBy(dataTable.getApprovedBy());
			setApprovedDate(dataTable.getApprovedDate());
			if(dataTable.getApprovedDate()!=null){
				setApprovedDateString(new SimpleDateFormat("dd/MM/yyyy").format(dataTable.getApprovedDate()));
			}
			setRemarks(null);
			RequestContext.getCurrentInstance().execute("remarks.show();");
		}else if(dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)){
			dataTable.setActivateRecordCheck(true);
			cityMasterService.activate(dataTable.getCityIdPk(), sessionStateManage.getUserName(),getCurrentTime());
			view();      
		}
		else if (dataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && dataTable.getModifiedBy()==null && dataTable.getModifiedDate()==null && dataTable.getApprovedBy()==null && dataTable.getApprovedDate()==null && dataTable.getRemarks()==null) {
			  dataTable.setPermanetDeleteCheck(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");	
			return;
		}
		if(datatableList.size()==0){
			setBooRenderDataTable(false);
			//setBooAdd(true);
			//setBooApproval(false);
			//setBooReadOnly(false);    
		}
}
	
	public void cityMasterForConformDelete(){
		  if (datatableList.size() > 0) {
			      for (CityMasterDataTable cityMasterDt : datatableList) {
					if (cityMasterDt.getPermanetDeleteCheck() != null) {
						  if (cityMasterDt.getPermanetDeleteCheck().equals(true)) {
							    deleteRecordCityMasterAndDesc(cityMasterDt);
							    datatableList.remove(cityMasterDt);
						  }
					}
			      }
		    }
	}

	public void deleteRecordCityMasterAndDesc(CityMasterDataTable dataTable){
		  cityMasterService.deleteRecordPermentelyFromDb(dataTable.getCityIdPk(), dataTable.getCityDescIdEnglishPk(), dataTable.getCityDescIdLocalPk());
	}
	public void cancel(){
 
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/citymasterapprovallist.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private UploadedFile uploadedFile;
	private Boolean booRenderDataTableFile;
	private Boolean checkStatus=null;

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
	public void exportExcel(FileUploadEvent event) throws IOException, SerialException, SQLException {
		uploadedFile = event.getFile();
		uploadedFile.getFileName();
		setIfFileUpload(true);
	}

	public void uploadtoDatatable() {

		cityListFoXlx.clear();
		datatableList.clear();

		/*popCountry();
		popState();*/
		if(getIfFileUpload().equals(false)){
			  RequestContext.getCurrentInstance().execute("noFileUploded.show();");
				return;      
		}else if (!uploadedFile.getFileName().equalsIgnoreCase("CityMaster.xls")) {
			  RequestContext.getCurrentInstance().execute("changeFile.show();");
				setIfFileUpload(false);
				setUploadedFile(null);
	  }else{
		

		try {
			Workbook workbook = null;
			//Get the number of sheets in the xlsx file
			workbook = new HSSFWorkbook(uploadedFile.getInputstream());
			int numberOfSheets = workbook.getNumberOfSheets();
			//loop through each of the sheets
			for(int i=0; i < numberOfSheets; i++){
				//Get the nth sheet from the workbook
				Sheet sheet = workbook.getSheetAt(i);
				//every sheet has rows, iterate over them
				Iterator<Row> rowIterator = sheet.iterator();
				int rowNumber =1;
				while (rowIterator.hasNext()) 
				{
					Row row = rowIterator.next();
					if(rowNumber==1)
					{
						rowNumber++;
						continue;
					}
					//Every row has columns, get the column iterator and iterate over them
					Iterator<Cell> cellIterator = row.cellIterator();
					CityMasterDataTable cityMasterData = new CityMasterDataTable();
					while (cellIterator.hasNext()) 
					{
						//Get the Cell object
						Cell cell = cellIterator.next();
						//check the cell type and process accordingly
						switch(cell.getCellType()){
						case Cell.CELL_TYPE_STRING:
							  if(cell.getColumnIndex()==3){
									  String cityName=cell.getStringCellValue().trim();
									  System.out.println("cityName="+cityName);
									  cityMasterData.setCityName(cityName);
								}
							  if(cell.getColumnIndex()==4){
									  String cityLocalName=cell.getStringCellValue().trim();
									  System.out.println("cityLocalName="+cityLocalName);
									  cityMasterData.setCityNameLocal(cityLocalName);
								}
							  if(cell.getColumnIndex()==0){
								    String countryCode = cell.getStringCellValue().trim();
									  System.out.println("countryCode.intValue()="+countryCode);
									  cityMasterData.setCountryCode(countryCode);
								}
							  if(cell.getColumnIndex()==1){
								    String stateCode = cell.getStringCellValue().trim();
									  System.out.println("stateCode.intValue()="+stateCode);
									  cityMasterData.setStateCode(stateCode);
								}
							  if(cell.getColumnIndex()==5){
								    String cityCode = cell.getStringCellValue().trim();
									  System.out.println("cityCode.intValue()="+cityCode);
									  cityMasterData.setCityCode(cityCode);
								}
							break;
						case Cell.CELL_TYPE_NUMERIC:
							
							  if(cell.getColumnIndex()==2){
								    Double district = cell.getNumericCellValue();
									  System.out.println("district.intValue()="+district.intValue());
									  cityMasterData.setDistrictId(new BigDecimal(district.intValue()));
									  
								}
							break;
						case Cell.CELL_TYPE_BLANK:
						}

					}
					boolean duplicateRecordCheck=false;
							    if (cityListFoXlx != null && cityListFoXlx.size() != 0) {
								      for (CityMasterDataTable cityMasterObj : cityListFoXlx) {
										if (cityMasterObj.getCountryCode().equalsIgnoreCase(cityMasterData.getCountryCode()) && cityMasterObj.getStateCode().equalsIgnoreCase(cityMasterData.getStateCode())
												    && cityMasterObj.getDistrictId().equals(cityMasterData.getDistrictId()) && cityMasterObj.getCityCode().equalsIgnoreCase(cityMasterData.getCityCode())) {
											  duplicateRecordCheck=true;
											  cityMasterData.setCheckStatus("Duplicate"); 
										}
								      }
							    }
							    if(!duplicateRecordCheck){
								      System.out.println("getDistrictId()::::::::::::::::::::"+cityMasterData.getDistrictId());
								      System.out.println("getStateCode()::::::::::::::::::::"+cityMasterData.getStateCode()); 
								      List<CityMaster> cityMasterList = cityMasterService.viewRecordDetailsForCheck( cityMasterData.getCityCode(),cityMasterData.getCountryCode(), cityMasterData.getStateCode(), cityMasterData.getDistrictId());
								      System.out.println("getDistrictId()::::::::::::::::::::"+cityMasterData.getDistrictId());
								      System.out.println("getStateCode()::::::::::::::::::::"+cityMasterData.getStateCode());
								      String districtCode=cityMasterService.toFetchDestrictCodeBasedOnDistrictId(cityMasterData.getDistrictId(),cityMasterData.getStateCode());
								      System.out.println("districtCode::::::::::::::::::::"+districtCode);
								      List<CityMasterDesc> listCityEng = cityMasterService.toFetchCityDesc(cityMasterData.getCityName(),cityMasterData.getCountryCode(),cityMasterData.getStateCode(),districtCode);
									if(cityMasterList != null && cityMasterList.size() !=0 || listCityEng != null && listCityEng.size() !=0 ){
										  cityMasterData.setCheckStatus("Duplicate");   
									}else{
										  cityMasterData.setCheckStatus(Constants.New_Record);   
									}
							    }
					//end of cell iterator
					cityListFoXlx.add(cityMasterData);
					setBooRenderDataTableFile(true);
					setBooRenderDataTable(false);
				} //end of rows iterator
			} //end of sheets for loop
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
		viewExport();
	}
	int count=0;
	private BigDecimal countNoOfRecords;



	public BigDecimal getCountNoOfRecords() {
		return countNoOfRecords;
	}
	public void setCountNoOfRecords(BigDecimal countNoOfRecords) {
		this.countNoOfRecords = countNoOfRecords;
	}
	public void saveMasterFile(){
		setUploadedFile(null);

		DistrictMaster districtMaster = new DistrictMaster();
		for(CityMasterDataTable dataTable:cityListFoXlx){
			if(dataTable.getCheckStatus().equalsIgnoreCase(Constants.New_Record)){
				count=count+1;
				CityMaster cityMaster = new CityMaster();
				cityMaster.setCityCode(dataTable.getCityCode());

				//countryMaster.setCountryId(dataTable.getCountryId());
				//cityMaster.setFsCountryMaster(countryMaster);
				//stateMaster.setStateId(dataTable.getStateId());
				cityMaster.setCountryCode(dataTable.getCountryCode());
				cityMaster.setStateCode(dataTable.getStateCode());
				
				districtMaster.setDistrictId(dataTable.getDistrictId());
				cityMaster.setFsDistrictMaster(districtMaster);

				String districtCode=cityMasterService.toFetchDestrictCodeBasedOnDistrictId(dataTable.getDistrictId(),dataTable.getStateCode());
				cityMaster.setDistrictCode(districtCode);

				/*	if(dataTable.getCityIdPk()!=null){
				cityMaster.setCityId(dataTable.getCityIdPk());
				cityMaster.setCreatedBy(dataTable.getCreatedBy());
				cityMaster.setCreatedDate(dataTable.getCreatedDate());
				//cityMaster.setModifiedBy(sessionStateManage.getUserName());
				//cityMaster.setModifiedDate(new Date());
				cityMaster.setIsActive(dataTable.getIsActive());

			}else{*/
				cityMaster.setCreatedBy(sessionStateManage.getUserName());
				cityMaster.setCreatedDate(getCurrentTime());
				cityMaster.setIsActive("U");


				//}
				generalService.saveOrUpdateWithPartialSave((T) cityMaster);

				CityMasterDesc desc = new CityMasterDesc();
				LanguageType languageType = new LanguageType();
				languageType.setLanguageId(new BigDecimal(1));
				desc.setFsLanguageType(languageType);
				desc.setCityName(dataTable.getCityName());
				desc.setFsCityMaster(cityMaster);
				generalService.saveOrUpdateWithPartialSave((T) desc);
				CityMasterDesc desc1 = new CityMasterDesc();
				LanguageType languageType1 = new LanguageType();
				languageType1.setLanguageId(new BigDecimal(2));
				desc1.setFsLanguageType(languageType1);
				desc1.setCityName(dataTable.getCityNameLocal());
				desc1.setFsCityMaster(cityMaster);
				generalService.saveOrUpdateWithPartialSave((T) desc1);
				//
				datatableNewList.clear();
			}
		}

		//setCountNoOfRecords(new BigDecimal(count));
		//count=0;
		RequestContext.getCurrentInstance().execute("completeupload.show();");

	}



	public void viewExport(){




		/*

		cityMasterList = cityMasterService.viewAllRecord();

       for(CityMaster cityMaster: cityMasterList){

			CityMasterDataTable masterDataTable = new CityMasterDataTable();
			masterDataTable.setCityIdPk(cityMaster.getCityId());
			masterDataTable.setCountryId(cityMaster.getFsCountryMaster().getCountryId());
			populateCountryList();
			masterDataTable.setCountryName(mapCountryList.get(masterDataTable.getCountryId()));
			masterDataTable.setStateId(cityMaster.getFsStateMaster().getStateId());
			populateState();
			masterDataTable.setStateName(mapStateList.get(cityMaster.getFsStateMaster().getStateId()));
			masterDataTable.setDistrictId(cityMaster.getFsDistrictMaster().getDistrictId());
			populateDistrict();
			masterDataTable.setDistrictName(mapDistirictList.get(cityMaster.getFsDistrictMaster().getDistrictId()));
			masterDataTable.setIsActive(cityMaster.getIsActive());

			masterDataTable.setCityCode(cityMaster.getCityCode());
			masterDataTable.setCreatedBy(cityMaster.getCreatedBy());
			masterDataTable.setCreatedDate(cityMaster.getCreatedDate());
			if(cityMaster.getModifiedBy()!=null){
			masterDataTable.setModifiedBy(cityMaster.getModifiedBy());
			masterDataTable.setModifiedDate(cityMaster.getModifiedDate());
			}
			if(cityMaster.getApprovedBy()!=null){
			masterDataTable.setApprovedBy(cityMaster.getApprovedBy());
			masterDataTable.setApprovedDate(cityMaster.getApprovedDate());
			}
			if(cityMaster.getRemarks()!=null){
			masterDataTable.setRemarks(cityMaster.getRemarks());
			}
			if(cityMaster.getCityCode().equalsIgnoreCase(cityListFoXlx.get(0).getCityCode())){
				masterDataTable.setCheckStatus("Duplicate");
				}else {
					masterDataTable.setCheckStatus("New Record");
				}

			List<CityMasterDesc> cityDescList = cityMasterService.viewDescRecord(cityMaster.getCityId());
			for(CityMasterDesc descList:cityDescList){

				if(descList.getFsLanguageType().getLanguageId().intValue()==1){
					masterDataTable.setCityDescIdEnglishPk(descList.getCityMasterId());

					masterDataTable.setCityName(descList.getCityName());
					masterDataTable.setEngLanguageId(descList.getFsLanguageType().getLanguageId());
			}

			if(descList.getFsLanguageType().getLanguageId().intValue()==2){

				masterDataTable.setCityNameLocal(descList.getCityName());
				masterDataTable.setCityDescIdLocalPk(descList.getCityMasterId());
				masterDataTable.setArbLanguageId(descList.getFsLanguageType().getLanguageId());
			}

				}

			cityListFoXlx.add(masterDataTable);	



		}
		 */}

	/*Map<String, BigDecimal> mapCountryListView = new HashMap<String,BigDecimal>();
	Map<String, String> mapStateListView = new HashMap<String,String>();

	Map<String, String> mapCountryListNew = new HashMap<String,String>();
	Map<String, String> mapStateListNew = new HashMap<String,String>();*/

	public void popCountryId(){
		List<CountryMaster> countryIdList = cityMasterService.viewCountryId(getCountryCode());
		if(countryIdList.size()!=0){
			for (CountryMaster countryMaster : countryIdList) {
				setCountryId(countryMaster.getCountryId());
			}
		}
	}

	public void populateStateList() {
		List<StateMasterDesc> lstState = generalService.getStateList(sessionStateManage.getLanguageId(),getCountryId());

		for (StateMasterDesc stateMasterDesc : lstState) {
			//mapStateListView.put(stateMasterDesc.getFsStateMaster().getStateCode(), stateMasterDesc.getStateName());

		}
		setStateList(lstState);
	}

	public void popStateId(){
		List<StateMaster> satateIdList = cityMasterService.viewStateIdId(getCountryId() , getStateCode());
		if(satateIdList.size()!=0){
			for (StateMaster stateMaster : satateIdList) {
				setStateId(stateMaster.getStateId());
			}
		}
	}


	public void popCountry(){
		List<CountryMasterDesc> countryList = cityMasterService.getCountryList(sessionStateManage.getLanguageId());
		if(countryList.size()!=0){
			for (CountryMasterDesc countryMasterDesc : countryList) {
				/*	mapCountryList.put(countryMasterDesc.getFsCountryMaster().getCountryId(),countryMasterDesc.getFsCountryMaster().getCountryCode());
				mapCountryListNew.put(countryMasterDesc.getFsCountryMaster().getCountryCode(),countryMasterDesc.getCountryName());*/

			}
			setCountryList(countryList);

		}
	}

	/*public void popState() {
		List<StateMasterDesc> lstState = cityMasterService.getStateList(sessionStateManage.getLanguageId());

		for (StateMasterDesc stateMasterDesc : lstState) {

			//mapStateListNew.put(stateMasterDesc.getFsStateMaster().getStateCode(),stateMasterDesc.getStateName());

		}


	}*/

	public void popDistrict() {

		List<DistrictMasterDesc> lstDistrict = generalService.getDistrictList(sessionStateManage.getLanguageId(),getCountryId(), getStateId());


		setDistrictList(lstDistrict);

	}

	public List<String> autoCompleteCityCode(String query) {
		LOG.info("Entering into autoCompleteStateCode method");
		List<String> lstrecords = new ArrayList<String>();
		if (query.length() > 0) {
			if(getCountryCode() != null && getStateCode() != null && getDistrictCode() != null){
				lstrecords = cityMasterService.getCityListCode(getCountryCode(), getStateCode(), getDistrictCode(), query);
			}else{
				RequestContext.getCurrentInstance().execute("mandatoryfields.show();");
			}
		} 
		return lstrecords;
	}
	
	public void clearCityDetails(){

		setCityCode(null);
		setCityId(null);
		setCityName(null);
		setCityNameLocal(null);

	}
	
	public void fetchDistrictCode() {
		
		clearCityDetails();
		setDistrictCode(mapDistrictListCode.get(getDistrictId()));
	
	}
	
	public void fetchEngArbRecordsForCityCode(){
		
		setCityName(null);
		setCityNameLocal(null);
		
		if(getCityCode() != null){
			List<CityMasterDesc> lstData = cityMasterService.fetchDBBasedonCityCode(getCountryCode(), getStateCode(), getDistrictCode(), getCityCode());
			
			if (lstData.size() != 0) {
				  setCityCode(null);
				      RequestContext.getCurrentInstance().execute("datatable.show();");
				      return;
				  /*
				
				for (CityMasterDesc cityMasterDesc : lstData) {
					if(cityMasterDesc.getFsLanguageType().getLanguageId().compareTo(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))==0){
						setCityName(cityMasterDesc.getCityName());
						setEngCityMasterDescId(cityMasterDesc.getCityMasterId());
					}
					
					if(cityMasterDesc.getFsLanguageType().getLanguageId().compareTo(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))==0){
						setCityNameLocal(cityMasterDesc.getCityName());
						setArbCityMasterDescId(cityMasterDesc.getCityMasterId());
					}
					
					setCityMasterId(cityMasterDesc.getFsCityMaster().getCityId());
					setDynamicLabelForActivateDeactivate(cityMasterDesc.getFsCityMaster().getIsActive());
					setIsActive(cityMasterDesc.getFsCityMaster().getIsActive());
					setCreatedBy(cityMasterDesc.getFsCityMaster().getCreatedBy());
					setCreatedDate(cityMasterDesc.getFsCityMaster().getCreatedDate());
					setModifiedBy(cityMasterDesc.getFsCityMaster().getModifiedBy());
					setModifiedDate(cityMasterDesc.getFsCityMaster().getModifiedDate());
					setApprovedBy(cityMasterDesc.getFsCityMaster().getApprovedBy());
					setApprovedDate(cityMasterDesc.getFsCityMaster().getApprovedDate());
					setRemarks(cityMasterDesc.getFsCityMaster().getRemarks());
					setRecordAvaliable(true);
					
				}
				
			*/}else{
				setCityName(null);
				setEngCityMasterDescId(null);
				setCityNameLocal(null);
				setArbCityMasterDescId(null);
				setCityMasterId(null);
				setDynamicLabelForActivateDeactivate(null);
				setIsActive(null);
				setCreatedBy(null);
				setCreatedDate(null);
				setModifiedBy(null);
				setModifiedDate(null);
				setApprovedBy(null);
				setApprovedDate(null);
				setRemarks(null);
				setRecordAvaliable(false); 
			}
			
		}
		
	}
	
	private BigDecimal engCityMasterDescId;
	private BigDecimal arbCityMasterDescId;
	private BigDecimal cityMasterId;
	private Boolean recordAvaliable = false;
	
	
	public BigDecimal getEngCityMasterDescId() {
		return engCityMasterDescId;
	}
	public void setEngCityMasterDescId(BigDecimal engCityMasterDescId) {
		this.engCityMasterDescId = engCityMasterDescId;
	}
	
	public BigDecimal getArbCityMasterDescId() {
		return arbCityMasterDescId;
	}
	public void setArbCityMasterDescId(BigDecimal arbCityMasterDescId) {
		this.arbCityMasterDescId = arbCityMasterDescId;
	}
	
	public BigDecimal getCityMasterId() {
		return cityMasterId;
	}
	public void setCityMasterId(BigDecimal cityMasterId) {
		this.cityMasterId = cityMasterId;
	}
	
	public Boolean getRecordAvaliable() {
		return recordAvaliable;
	}
	public void setRecordAvaliable(Boolean recordAvaliable) {
		this.recordAvaliable = recordAvaliable;
	}
	
	public void clearListData(){
		clear();
		
		setStateList(null);
		if(getStateList() != null){
			stateList.clear();
		}
		
		setDistrictList(null);
		if(getDistrictList() != null){
			districtList.clear();
		}
		
		mapStateList.clear();
		mapDistirictList.clear();
		mapDistrictListCode.clear();
	}
	
	public void clearFields(){
		setCityName(null);
		setCityNameLocal(null);
		setCityCode(null);
		setCityIdPk(null);
	}

	  private CityMasterDataTable cityMasterDTOBJ = null;

	  public CityMasterDataTable getCityMasterDTOBJ() {
		    return cityMasterDTOBJ;
	  }

	  public void setCityMasterDTOBJ(CityMasterDataTable cityMasterDTOBJ) {
		    this.cityMasterDTOBJ = cityMasterDTOBJ;
	  }

	  private Boolean booRenderApprovalDataTable = false;
	  private Boolean ifEditClicked = false;
	  private Boolean IfFileUpload=false;
	  public Boolean getBooRenderApprovalDataTable() {
		    return booRenderApprovalDataTable;
	  }

	  public void setBooRenderApprovalDataTable(Boolean booRenderApprovalDataTable) {
		    this.booRenderApprovalDataTable = booRenderApprovalDataTable;
	  }
	  public String getErrmsg() {
	  	  return errmsg;
	  }
	  public void setErrmsg(String errmsg) {
	  	  this.errmsg = errmsg;
	  }
	  public Boolean getIfEditClicked() {
	  	  return ifEditClicked;
	  }
	  public void setIfEditClicked(Boolean ifEditClicked) {
	  	  this.ifEditClicked = ifEditClicked;
	  }
	  public Boolean getIfFileUpload() {
	  	  return IfFileUpload;
	  }
	  public void setIfFileUpload(Boolean ifFileUpload) {
	  	  IfFileUpload = ifFileUpload;
	  }
	
	  
	public void cancelRemarksForDialog(){
		setRemarks(null);
		setRadioRender(false);
		setManualRender(true);
		setFileRender(false);
		setBooRenderDataTableFile(false);
		setBooRenderDataTable(true);
		setBooRenderApprovalDataTable(false);
			try {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../registration/citymaster.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	
	
	  private String errorMessage;

	  public String getErrorMessage() {
		    return errorMessage;
	  }

	  public void setErrorMessage(String errorMessage) {
		    this.errorMessage = errorMessage;
	  }
	  
  

 
	 

		
	  
	  public void clearAllFields(){
		  setCountryId( null);
		  setStateId( null);
		  setDistrictId( null);
		  setCountryCode( null);
		  setStateCode( null);
		  setDistrictCode( null);
		  cityListForAproval.clear();
	  }
	  
	
	
	
}

	

