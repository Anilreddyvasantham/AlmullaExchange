package com.amg.exchange.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.codehaus.groovy.tools.shell.commands.ClearCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.BizComponentConfDetail;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.BizComponentDataRef;
import com.amg.exchange.common.model.BusinessComponent;
import com.amg.exchange.common.model.BusinessComponentConf;
import com.amg.exchange.common.model.ComponentType;
import com.amg.exchange.common.model.ComponentTypeDetail;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IBusinessComponentService;
import com.amg.exchange.common.service.IComponentTypeService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.util.SessionStateManage;

@Component("businesscomponent")
@RequestScoped
public class BusinessComponentBean<T> implements Serializable {

	private static final Logger LOG = Logger.getLogger(BusinessComponentBean.class);
	private static final long serialVersionUID = 1L;

	private String businessComponentName;
	private BigDecimal businessComponentTypeId;
	private boolean globalgridstatus;
	private String globalmessage;  
	private String isMultiple = "N";
	private int minValue;
	private int maxValue;
	private String isNumeric = "N";
	private String isAlphabet = "Y";
	private String isSpecial = "N";
	private String isRequired = "N";
	private String isReadOnly = "N";
	private String isEnable = "Y";
	private String isVisible = "Y";
	private String componentComboData;
	private boolean isMultipleStatus;
	private String combComponentErrMsg;
	private boolean bcdatagridStatus;
	private String createdBy;
	private BusinessComponent businessComponent;
	private BusinessComponentConf businessComponentConf;
	private BizComponentConfDetail bizComponentConfDetail;
	private BizComponentDataRef bizComponentDataRef;
	private BizComponentData bizComponentData;
	private BizComponentDataDesc bizComponentDataDesc;
	private Date currentTime;
	private BigDecimal languageId;
	
	private boolean rowMinLength = true;
	private boolean rowMaxLength = true;
	private boolean rowNumeric = true;
	private boolean rowAlpha = true;
	private boolean rowSpecial = true;
	private boolean rowRequired = true;
	private boolean rowReadOnly = true;
	private boolean rowEnable = true;
	private boolean rowVisible = true;
	
	private boolean rowMultiple = true;
	
	private List<BusnessComponentCompBean> lstCompDataActual = new ArrayList<BusnessComponentCompBean>();
	private List<BusnessComponentCompBean> lstCompData = new ArrayList<BusnessComponentCompBean>();
	private List<BusnessComponentCompBean> lstRemovedCompData = new ArrayList<BusnessComponentCompBean>();
	private Map<BigDecimal, BizComponentDataRef> mapComponentDataRef = new HashMap<BigDecimal, BizComponentDataRef>();
	private List<LanguageType> lstLanguageType = new ArrayList<LanguageType>();
	private List<String> lstColumnName = new ArrayList<String>();
	private Map<BigDecimal, ComponentListBean> mapComponentListBean = new HashMap<BigDecimal, ComponentListBean>();
	
	SessionStateManage sessionStateManage = new SessionStateManage();


	@Autowired
	IBusinessComponentService<T> businessComponentService;
	
	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	IComponentTypeService<T> componentTypeService;

	
	public String getBusinessComponentName() {
		return businessComponentName;
	}

	public Map<BigDecimal, BizComponentDataRef> getMapComponentDataRef() {
		return mapComponentDataRef;
	}

	public void setMapComponentDataRef(Map<BigDecimal, BizComponentDataRef> mapComponentDataRef) {
		this.mapComponentDataRef = mapComponentDataRef;
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public List<LanguageType> getLstLanguageType() {
		return lstLanguageType;
	}

	public void setLstLanguageType(List<LanguageType> lstLanguageType) {
		this.lstLanguageType = lstLanguageType;
	}
	
	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}
	
	public boolean isRowRequired() {
		return rowRequired;
	}

	public void setRowRequired(boolean rowRequired) {
		this.rowRequired = rowRequired;
	}

	public boolean isRowReadOnly() {
		return rowReadOnly;
	}

	public void setRowReadOnly(boolean rowReadOnly) {
		this.rowReadOnly = rowReadOnly;
	}

	public boolean isRowEnable() {
		return rowEnable;
	}

	public void setRowEnable(boolean rowEnable) {
		this.rowEnable = rowEnable;
	}

	public boolean isRowVisible() {
		return rowVisible;
	}

	public void setRowVisible(boolean rowVisible) {
		this.rowVisible = rowVisible;
	}

	public boolean isRowMultiple() {
		return rowMultiple;
	}

	public void setRowMultiple(boolean rowMultiple) {
		this.rowMultiple = rowMultiple;
	}

	public Map<BigDecimal, ComponentListBean> getMapComponentListBean() {
		return mapComponentListBean;
	}

	public void setMapComponentListBean(Map<BigDecimal, ComponentListBean> mapComponentListBean) {
		this.mapComponentListBean = mapComponentListBean;
	}

	public List<BusnessComponentCompBean> getLstRemovedCompData() {
		return lstRemovedCompData;
	}

	public void setLstRemovedCompData(List<BusnessComponentCompBean> lstRemovedCompData) {
		this.lstRemovedCompData = lstRemovedCompData;
	}

	public List<String> getLstColumnName() {
		return lstColumnName;
	}

	public void setLstColumnName(List<String> lstColumnName) {
		this.lstColumnName = lstColumnName;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public BigDecimal getLanguageId() {
		return languageId;
	}

	public void setLanguageId(BigDecimal languageId) {
		this.languageId = languageId;
	}

	public BusinessComponentConf getBusinessComponentConf() {
		return businessComponentConf;
	}

	public void setBusinessComponentConf(BusinessComponentConf businessComponentConf) {
		this.businessComponentConf = businessComponentConf;
	}

	public boolean isRowMinLength() {
		return rowMinLength;
	}

	public void setRowMinLength(boolean rowMinLength) {
		this.rowMinLength = rowMinLength;
	}

	public boolean isRowMaxLength() {
		return rowMaxLength;
	}

	public BizComponentConfDetail getBizComponentConfDetail() {
		return bizComponentConfDetail;
	}

	public void setBizComponentConfDetail(BizComponentConfDetail bizComponentConfDetail) {
		this.bizComponentConfDetail = bizComponentConfDetail;
	}

	public BizComponentDataRef getBizComponentDataRef() {
		return bizComponentDataRef;
	}

	public void setBizComponentDataRef(BizComponentDataRef bizComponentDataRef) {
		this.bizComponentDataRef = bizComponentDataRef;
	}

	public BizComponentData getBizComponentData() {
		return bizComponentData;
	}

	public void setBizComponentData(BizComponentData bizComponentData) {
		this.bizComponentData = bizComponentData;
	}

	public BizComponentDataDesc getBizComponentDataDesc() {
		return bizComponentDataDesc;
	}

	public void setBizComponentDataDesc(BizComponentDataDesc bizComponentDataDesc) {
		this.bizComponentDataDesc = bizComponentDataDesc;
	}

	public void setRowMaxLength(boolean rowMaxLength) {
		this.rowMaxLength = rowMaxLength;
	}

	public boolean isRowNumeric() {
		return rowNumeric;
	}

	public void setRowNumeric(boolean rowNumeric) {
		this.rowNumeric = rowNumeric;
	}

	public boolean isRowAlpha() {
		return rowAlpha;
	}

	public void setRowAlpha(boolean rowAlpha) {
		this.rowAlpha = rowAlpha;
	}

	public boolean isRowSpecial() {
		return rowSpecial;
	}

	public void setRowSpecial(boolean rowSpecial) {
		this.rowSpecial = rowSpecial;
	}

	public boolean isMultipleStatus() {
		return isMultipleStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public BusinessComponent getBusinessComponent() {
		return businessComponent;
	}

	public void setBusinessComponent(BusinessComponent businessComponent) {
		this.businessComponent = businessComponent;
	}

	public Date getCurrentTime() {
		LOG.info("Entering into getCurrentTime method");
		Date objList = getGeneralService().getSysDateTimestamp(sessionStateManage.getCountryId());
		if (objList != null) {
			currentTime = objList;
		} else {
			currentTime = null;
		}
		LOG.info("Exit into getCurrentTime method");
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public boolean isBcdatagridStatus() {
		return bcdatagridStatus;
	}

	public void setBcdatagridStatus(boolean bcdatagridStatus) {
		this.bcdatagridStatus = bcdatagridStatus;
	}

	public String getCombComponentErrMsg() {
		return combComponentErrMsg;
	}

	public void setCombComponentErrMsg(String combComponentErrMsg) {
		this.combComponentErrMsg = combComponentErrMsg;
	}

	public String getComponentComboData() {
		return componentComboData;
	}

	public void setComponentComboData(String componentComboData) {
		this.componentComboData = componentComboData;
	}

	public boolean getMultipleStatus() {
		return isMultipleStatus;
	}

	public void setMultipleStatus(boolean isMultipleStatus) {
		this.isMultipleStatus = isMultipleStatus;
	}

	public List<BusnessComponentCompBean> getLstCompData() {
		return lstCompData;
	}

	public void setLstCompData(List<BusnessComponentCompBean> lstCompData) {
		this.lstCompData = lstCompData;
	}

	public String getIsMultiple() {
		return isMultiple;
	}

	public void setIsMultiple(String isMultiple) {
		this.isMultiple = isMultiple;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public String getIsNumeric() {
		return isNumeric;
	}

	public void setIsNumeric(String isNumeric) {
		this.isNumeric = isNumeric;
	}

	public String getIsAlphabet() {
		return isAlphabet;
	}

	public void setIsAlphabet(String isAlphabet) {
		this.isAlphabet = isAlphabet;
	}

	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public String getIsReadOnly() {
		return isReadOnly;
	}

	public void setIsReadOnly(String isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public void setBusinessComponentName(String businessComponentName) {
		this.businessComponentName = businessComponentName;
	}

	public BigDecimal getBusinessComponentTypeId() {
		return businessComponentTypeId;
	}

	public void setBusinessComponentTypeId(BigDecimal businessComponentTypeId) {
		this.businessComponentTypeId = businessComponentTypeId;
	}

	public boolean isGlobalgridstatus() {
		return globalgridstatus;
	}

	public void setGlobalgridstatus(boolean globalgridstatus) {
		this.globalgridstatus = globalgridstatus;
	}

	public String getGlobalmessage() {
		return globalmessage;
	}

	public void setGlobalmessage(String globalmessage) {
		this.globalmessage = globalmessage;
	}

	public IBusinessComponentService<T> getBusinessComponentService() {
		return businessComponentService;
	}

	public void setBusinessComponentService(
			IBusinessComponentService<T> businessComponentService) {
		this.businessComponentService = businessComponentService;
	}

	//Get All component type list
	public List<ComponentType> getComponentType() {
		LOG.info("Entering into getComponentType method");
		
		List<ComponentType> lstComponentType = null;
		ComponentListBean componentListBean;
		ComponentTypeDetail componentTypeDetail;
		try {
			lstComponentType = componentTypeService.getComponentTypeList();
			if (lstComponentType != null && lstComponentType.size() > 0) {
				int sno = 1;
				for (ComponentType componentType : lstComponentType) {
					componentTypeDetail = componentType.getComponentTypeDetail();
					componentListBean = new ComponentListBean();
					componentListBean.setComponentType(componentType);
					componentListBean.setComponentTypeDetail(componentTypeDetail);
					componentListBean.setMultiple(getBoolean(componentTypeDetail.getIsmultiple()));
					componentListBean.setVisibility(getBoolean(componentTypeDetail.getVisibility()));
					componentListBean.setMinlength(getBoolean(componentTypeDetail.getMinlength()));
					componentListBean.setMaxlength(getBoolean(componentTypeDetail.getMaxlength()));
					componentListBean.setNumeric(getBoolean(componentTypeDetail.getNumeric()));
					componentListBean.setAlphabet(getBoolean(componentTypeDetail.getAlphabet()));
					componentListBean.setSpecial(getBoolean(componentTypeDetail.getSpecial()));
					componentListBean.setMandatory(getBoolean(componentTypeDetail.getMandatory()));
					componentListBean.setReadonly(getBoolean(componentTypeDetail.getReadonly()));
					componentListBean.setEnable(getBoolean(componentTypeDetail.getEnable()));
					componentListBean.setDefaultValue(getBoolean(componentTypeDetail.getDefaultValue()));
					componentListBean.setSno(sno++);
					componentListBean.setComponentTypeName(componentType.getComponentTypeName());
					mapComponentListBean.put(componentType.getComponentTypeId(), componentListBean);
				}
			}
		} catch (Exception e) {
			LOG.error("Exception occured in BusinessComponentBean ::getComponentType method ", e);
		}
		LOG.info("Exit into getComponentType method");
		return lstComponentType;
	}
	
	private boolean getBoolean(String data){
		
		return data.equalsIgnoreCase("y")?true:false;
	}
	
	private void prepareDataTable(List<BizComponentData> lstBizComponentData) {
		LOG.info("Entering into prepareDataTable method");
		BusnessComponentCompBean busnessComponentCompBean;
		for (BizComponentData bizComponentData : lstBizComponentData) {
			busnessComponentCompBean = new BusnessComponentCompBean();
			busnessComponentCompBean.setSerialid(new BigDecimal(lstCompData.size() + 1));
			busnessComponentCompBean.setBussComponentComboDataId(bizComponentData.getComponentDataId());
			busnessComponentCompBean.setComponentData("");
			busnessComponentCompBean.setCreatedBy(bizComponentData.getCreatedBy());
			busnessComponentCompBean.setCreatedTime(bizComponentData.getCreateDate());
			/* busnessComponentCompBean.setLanguageId(bizComponentDataDesc.
			 * getFsLanguageType().getLanguageId()); */
			busnessComponentCompBean.setComponentid(busnessComponentCompBean.getBussComponentComboDataId().toPlainString());
			busnessComponentCompBean.setBizComponentData(bizComponentData);
			int i = 0;
			String[] strArrayColumnValues = new String[lstLanguageType.size()];
			String[] strArrayColumnLang = new String[lstLanguageType.size()];
			String[] strArrayColumnLangDir = new String[lstLanguageType.size()];
			Object[] objArrayDescObject = new Object[lstLanguageType.size()];
			boolean aviStatus = false;
			for (LanguageType langBean : lstLanguageType) {
				aviStatus = false;
				for (BizComponentDataDesc descBean : bizComponentData.getFsBizComponentDataDescs()) {
					if (langBean.getLanguageId().equals(descBean.getFsLanguageType().getLanguageId())) {
						aviStatus = true;
						strArrayColumnLang[i] = langBean.getLanguageId().toPlainString();
						strArrayColumnLangDir[i] = langBean.getDirection().equalsIgnoreCase("rtl") ? "right" : "left";
						objArrayDescObject[i] = descBean;
						strArrayColumnValues[i++] = descBean.getDataDesc();
					}
				}
				if (!aviStatus) {
					strArrayColumnLang[i] = langBean.getLanguageId().toPlainString();
					strArrayColumnLangDir[i] = langBean.getDirection().equalsIgnoreCase("rtl") ? "right" : "left";
					objArrayDescObject[i] = null;
					strArrayColumnValues[i++] = "";
				}
			}
			busnessComponentCompBean.setLstComponentDataDir(strArrayColumnLangDir);
			busnessComponentCompBean.setObjDescArray(objArrayDescObject);
			busnessComponentCompBean.setLanguageId(strArrayColumnLang);
			busnessComponentCompBean.setLstComponentData(strArrayColumnValues);
			lstCompData.add(busnessComponentCompBean);
		}
		LOG.info("Exit into prepareDataTable method");
	}
	
	//Fetch business component detail by component name which is entered by user (For Modification purpose)
	public void getBusinessComponentDetail() {
		
		LOG.info("Entering into getBusinessComponentDetail method");
		// Check if it is exist or not
		boolean isAvailable = false;
		BizComponentConfDetail bizComponentConfDetail = new BizComponentConfDetail();
		try {
			bizComponentConfDetail = getBusinessComponentService().getBusinessComponent(getBusinessComponentName().trim());
			isAvailable = true;
		} catch (Exception e) {
			isAvailable = false;
		}
		// Fetch All Language Type
		lstLanguageType = getGeneralService().getAllLanguages();
		lstColumnName.clear();
		for (LanguageType langBean : lstLanguageType) {
			lstColumnName.add(langBean.getLanguageName());
		}
		if (isAvailable) {
			// Set Business Component Data
			setBusinessComponent(bizComponentConfDetail.getFsBusinessComponentConf().getFsBusinessComponent());
			setBusinessComponentTypeId(getBusinessComponent().getFsComponentType().getComponentTypeId());
			setIsMultiple(getBusinessComponent().getIsMultiple());
			setBusinessComponentName(getBusinessComponent().getComponentName());
			// Set Business Component conf detail
			setIsNumeric(bizComponentConfDetail.getIsNumeric());
			setIsAlphabet(bizComponentConfDetail.getIsAlphabet());
			setIsSpecial(bizComponentConfDetail.getIsSpecial());
			setIsRequired(bizComponentConfDetail.getIsRequired());
			setIsReadOnly(bizComponentConfDetail.getIsReadOnly());
			setIsEnable(bizComponentConfDetail.getIsEnable());
			setIsVisible(bizComponentConfDetail.getIsVisible());
			setMinValue(bizComponentConfDetail.getMinValue().intValue());
			setMaxValue(bizComponentConfDetail.getMaxValue().intValue());
			if (getIsMultiple().equals("Y")) {
				/* hideUnwantedRow(false); */
			} else {
				/* hideUnwantedRow(true); */
			}
			hideUnwantedRow();
			// Assign fetched object into bean object
			this.businessComponentConf = bizComponentConfDetail.getFsBusinessComponentConf();
			this.bizComponentConfDetail = bizComponentConfDetail;
		} else {
			// If data not exist set all model object as null
			setBusinessComponent(null);
			this.businessComponentConf = null;
			this.bizComponentConfDetail = null;
		}
		// Check drop down data list
		if (isAvailable && getIsMultiple().equalsIgnoreCase("Y")) {
			/* List<BizComponentDataDesc> lstBizComponentDataDesc = null; */
			List<BizComponentData> lstBizComponentData = null;
			try {
				// Fetch drop down list by business component conf id
				SessionStateManage ssManage = new SessionStateManage();
				setLanguageId(new BigDecimal(ssManage.isExists("languageId") ? ssManage.getSessionValue("languageId") : "1"));
				LOG.info("------------------------- " + businessComponentConf.getComponentConfId());
				lstBizComponentData = getBusinessComponentService().getBusinessComponentData(businessComponentConf.getComponentConfId());
				LOG.info("`````````````````````````````````  " + lstBizComponentData);
				if (lstBizComponentData.size() > 0) {
					setComponentComboData("");
					setCombComponentErrMsg("");
					lstCompData.clear();
					lstRemovedCompData.clear();
					lstCompDataActual.clear();
					prepareDataTable(lstBizComponentData);
					lstCompDataActual.addAll(lstCompData);
				}
				setBcdatagridStatus(true);
			} catch (Exception e) {
				LOG.error("Exception occured in BusinessComponentBean ::getBusinessComponentDetail method ", e);
				setBcdatagridStatus(true);
				setComponentComboData("");
				setCombComponentErrMsg("");
				lstCompData.clear();
				lstRemovedCompData.clear();
				lstCompDataActual.clear();
			}
		} else {
			// If drop down not exist reset displayed value
			setBcdatagridStatus(false);
			setComponentComboData("");
			setCombComponentErrMsg("");
			lstCompData.clear();
			lstRemovedCompData.clear();
			lstCompDataActual.clear();
		}
	}
	
	public List<String> autoCompleteData(String query) {
		if (query.length() > 1) {
			return getBusinessComponentService().getAllBusinessComponent(query);
		} else {
			return null;
		}
	}
	
	public void removeComponentRow(BusnessComponentCompBean bean) {
		if (lstCompDataActual.contains(bean)) {
			lstRemovedCompData.add(bean);
		}
		lstCompData.remove(bean);
	}
	
	//Add component drop down data which is entered in a text box
	public void addComponentData() {
		LOG.info("Entering into addComponentData method");
		SessionStateManage ssManage = new SessionStateManage();
		setLanguageId(new BigDecimal(ssManage.isExists("languageId") ? ssManage.getSessionValue("languageId") : "1"));
		BusnessComponentCompBean busnessComponentCompBean = new BusnessComponentCompBean();
		busnessComponentCompBean.setSerialid(new BigDecimal(lstCompData.size() + 1));
		busnessComponentCompBean.setCreatedBy(getCreatedBy());
		busnessComponentCompBean.setCreatedTime(getCurrentTime());
		String[] tempStringArray = new String[lstLanguageType.size()];
		String[] tempStringArrayData = new String[lstLanguageType.size()];
		String[] tempStringArrayDataDir = new String[lstLanguageType.size()];
		Object[] objArrayDescObject = new Object[lstLanguageType.size()];
		int index = 0;
		for (LanguageType bean : lstLanguageType) {
			tempStringArrayData[index] = "";
			objArrayDescObject[index] = null;
			tempStringArrayDataDir[index] = bean.getDirection().equalsIgnoreCase("rtl") ? "right" : "left";
			tempStringArray[index++] = bean.getLanguageId().toPlainString();
		}
		busnessComponentCompBean.setLstComponentDataDir(tempStringArrayDataDir);
		busnessComponentCompBean.setObjDescArray(objArrayDescObject);
		busnessComponentCompBean.setLstComponentData(tempStringArrayData);
		busnessComponentCompBean.setLanguageId(tempStringArray);
		lstCompData.add(busnessComponentCompBean);
		LOG.info("Exit into addComponentData method");
	}
	
	//Drop down component list visibility control
	public void showComponentData(){
		
		lstCompData.clear();
		setComponentComboData("");
		setCombComponentErrMsg("");
		if(getIsMultiple().equals("Y")){
			setBcdatagridStatus(true);
			/*hideUnwantedRow(false);*/
		}else{
			setBcdatagridStatus(false);
			/*hideUnwantedRow(true);*/
		}
		hideUnwantedRow();
	}
	
/*	private void hideUnwantedRow(boolean currentStatus){
*/	
	private void hideUnwantedRow() {
		LOG.info("Entering into hideUnwantedRow method");
		ComponentListBean bean = mapComponentListBean.get(getBusinessComponentTypeId());
		if (bean != null) {
			setRowMinLength(bean.isMinlength());
			setRowMaxLength(bean.isMaxlength());
			setRowNumeric(bean.isNumeric());
			setRowAlpha(bean.isAlphabet());
			setRowSpecial(bean.isSpecial());
			setRowRequired(bean.isMandatory());
			setRowReadOnly(bean.isReadonly());
			setRowEnable(bean.isEnable());
			setRowVisible(bean.isVisibility());
			setRowMultiple(bean.isMultiple());
			setBcdatagridStatus(bean.isMultiple());
		}
		LOG.info("Exit into hideUnwantedRow method");
	}
	
	//Save or update 
	public void saveData() {
		LOG.info("Entering into saveData method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		// Fetch user name from session
		if (sessionStateManage.isExists("username")) {
			setCreatedBy(sessionStateManage.getSessionValue("username"));
		} else {
			setCreatedBy("ROOT");
		}
		int index = 1;
		boolean validStatus = true;
		List<String> allList = new ArrayList<String>();
		for (BusnessComponentCompBean bean : lstCompData) {
			if (allList.contains(bean.getLstComponentData()[0].trim().toLowerCase())) {
				validStatus = false;
				FacesContext.getCurrentInstance().addMessage("businesscomponentfrm:componentData",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "SNo : " + index + ", Language : English, Data : " + bean.getLstComponentData()[0] + " duplicate entry!"));
			}
			if (bean.getLstComponentData()[0].trim().equals("")) {
				validStatus = false;
				FacesContext.getCurrentInstance().addMessage("businesscomponentfrm:componentData", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "SNo : " + index + " English Data Required!"));
			}
			allList.add(bean.getLstComponentData()[0].trim().toLowerCase());
			index++;
		}
		if (validStatus) {
			// Prepare current time
		//	setCurrentTime(new Date());
			// Save business component
			saveComponentType();
			// Save business component configuration
			saveComponentConf();
			// Save business component configuration detail
			saveComponentConfDetail();
			// Is multiple value then store data table data into component combo
			// detail
			if (getIsMultiple().equalsIgnoreCase("Y")) {
				// saveComponentComboDetail();
				mapComponentDataRef = getBusinessComponentService().getAllComponentDataRef(businessComponentConf.getComponentConfId());
				saveComponentData();
				lstCompData.clear();
				lstRemovedCompData.clear();
				lstCompDataActual.clear();
			}
			getBusinessComponentDetail();
		}
		LOG.info("Exit into saveData method");
	}
	
	//Save or update component Data
	@SuppressWarnings("unchecked")
	private void saveComponentType() {
		LOG.info("Entering  into saveComponentType method");
		// Check component id exist or not
		boolean isExists = true;
		try {
			businessComponent = getBusinessComponentService().getBusinessComponent(getBusinessComponentName().trim()).getFsBusinessComponentConf().getFsBusinessComponent();
			isExists = true;
		} catch (Exception e) {
			isExists = false;
			businessComponent = new BusinessComponent();
			LOG.info("Component Name is new");
		}
		if (businessComponent == null) {
			businessComponent = new BusinessComponent();
		}
		ComponentType bcType = new ComponentType();
		bcType.setComponentTypeId(getBusinessComponentTypeId());
		businessComponent.setComponentName(getBusinessComponentName());
		businessComponent.setFsComponentType(bcType);
		businessComponent.setIsMultiple(getIsMultiple());
		businessComponent.setIsActive("Y");
		// If component data exits then no need to update created by and created
		// time info
		if (!isExists) {
			businessComponent.setCreatedBy(getCreatedBy());
			businessComponent.setCreatedTime(getCurrentTime());
		}
		businessComponent.setUpdatedBy(getCreatedBy());
		businessComponent.setUpdatedTime(getCurrentTime());
		getBusinessComponentService().saveOrUpdate((T) businessComponent);
		LOG.info("Exit  into saveComponentType method");
	}
	
	//Save or update component conf information
	@SuppressWarnings("unchecked")
	private void saveComponentConf(){
		LOG.info("Entering  into saveComponentConf method");
		//Check data exist or not in configuration table
		boolean isExists = true;
		if(businessComponentConf==null){
			businessComponentConf = new BusinessComponentConf();
			isExists = false;
		}
		businessComponentConf.setFsBusinessComponent(businessComponent);
		businessComponentConf.setFsRuleApplicationMaster(null);
		businessComponentConf.setFsCompanyMaster(null);
		businessComponentConf.setFsCountryMaster(null);
		businessComponentConf.setFsRulePageMaster(null);
		businessComponentConf.setLevelId(new BigDecimal(0));
		
		//If it is update not required
		if(!isExists){
			businessComponentConf.setCreatedBy(getCreatedBy());
			businessComponentConf.setCreatedTime(getCurrentTime());
		}
		businessComponentConf.setUpdatedBy(getCreatedBy());
		businessComponentConf.setUpdatedTime(getCurrentTime());
		
		getBusinessComponentService().saveOrUpdate((T)businessComponentConf);
		LOG.info("Exit  into saveComponentConf method");
	}
	
	//Save or update component conf detail info
	@SuppressWarnings("unchecked")
	private void saveComponentConfDetail() {
		LOG.info("Entering  into saveComponentConfDetail method");
		// Check data exists
		boolean isExists = true;
		if (bizComponentConfDetail == null) {
			bizComponentConfDetail = new BizComponentConfDetail();
			isExists = false;
		}
		bizComponentConfDetail.setFsBusinessComponentConf(businessComponentConf);
		bizComponentConfDetail.setIsNumeric(getIsNumeric());
		bizComponentConfDetail.setIsAlphabet(getIsAlphabet());
		bizComponentConfDetail.setIsSpecial(getIsSpecial());
		bizComponentConfDetail.setIsRequired(getIsRequired());
		bizComponentConfDetail.setIsReadOnly(getIsReadOnly());
		bizComponentConfDetail.setIsEnable(getIsEnable());
		bizComponentConfDetail.setIsVisible(getIsVisible());
		bizComponentConfDetail.setMinValue(new BigDecimal(getMinValue()));
		bizComponentConfDetail.setMaxValue(new BigDecimal(getMaxValue()));
		bizComponentConfDetail.setIsActive("Y");
		// If it is update not required
		if (!isExists) {
			bizComponentConfDetail.setCreatedBy(getCreatedBy());
			bizComponentConfDetail.setCreatedTime(getCurrentTime());
		}
		bizComponentConfDetail.setUpdatedBy(getCreatedBy());
		bizComponentConfDetail.setUpdatedTime(getCurrentTime());
		getBusinessComponentService().saveOrUpdate((T) bizComponentConfDetail);
		LOG.info("Exit  into saveComponentConfDetail method");
	}
	
	// Save or update Business Component Data
	@SuppressWarnings("unchecked")
	private void saveComponentData() {
		LOG.info("Entering  into saveComponentData method");
		List<BusnessComponentCompBean> lstAllComponentData = new ArrayList<BusnessComponentCompBean>();
		lstAllComponentData.addAll(lstCompData);
		lstAllComponentData.addAll(lstRemovedCompData);
		BizComponentData beanComponentData = new BizComponentData();
		for (BusnessComponentCompBean entity : lstAllComponentData) {
			beanComponentData = new BizComponentData();
			if (entity.getBizComponentData() != null) {
				beanComponentData = entity.getBizComponentData();
			} else {
				beanComponentData.setCreatedBy(getCreatedBy());
				beanComponentData.setCreateDate(getCurrentTime());
				beanComponentData.setFsBusinessComponent(businessComponent);
				beanComponentData.setComponentCode("-");
			}
			beanComponentData.setActive(lstRemovedCompData.contains(entity) ? "N" : "Y");
			beanComponentData.setModifiedBy(getCreatedBy());
			beanComponentData.setModifiedDate(getCurrentTime());
			getBusinessComponentService().saveOrUpdate((T) beanComponentData);
			saveComponentDataDesc(beanComponentData, entity);
			saveComponentDataRef(beanComponentData);
		}
		lstAllComponentData.clear();
		LOG.info("Exit  into saveComponentData method");
	}
	
	//Save or update component description
	@SuppressWarnings("unchecked")
	private void saveComponentDataDesc(BizComponentData beanComponentData, BusnessComponentCompBean entity) {
		LOG.info("Entering  into saveComponentDataDesc method");
		BizComponentDataDesc beanBizComponentDataDesc = null;
		for (int i = 0; i < entity.getLstComponentData().length; i++) {
			beanBizComponentDataDesc = new BizComponentDataDesc();
			if (entity.getObjDescArray()[i] != null) {
				beanBizComponentDataDesc = (BizComponentDataDesc) entity.getObjDescArray()[i];
			} else {
				beanBizComponentDataDesc.setFsLanguageType(new LanguageType(new BigDecimal(entity.getLanguageId()[i]), ""));
			}
			beanBizComponentDataDesc.setDataDesc(entity.getLstComponentData()[i]);
			beanBizComponentDataDesc.setFsBizComponentData(beanComponentData);
			getBusinessComponentService().saveOrUpdate((T) beanBizComponentDataDesc);
		}
		LOG.info("Exit  into saveComponentDataDesc method");
	}
	
	//Save or update component conf and data references
	@SuppressWarnings("unchecked")
	private void saveComponentDataRef(BizComponentData beanComponentData){
		LOG.info("Entering  into saveComponentDataRef method");
		
		BizComponentDataRef beanBizComponentDataRef = new BizComponentDataRef();
		if(mapComponentDataRef.containsKey(beanComponentData.getComponentDataId())){
			beanBizComponentDataRef = mapComponentDataRef.get(beanComponentData.getComponentDataId());
		}else{
			beanBizComponentDataRef.setFsBusinessComponentConf(businessComponentConf);
			beanBizComponentDataRef.setFsBizComponentData(beanComponentData);
			beanBizComponentDataRef.setActive("Y");
			beanBizComponentDataRef.setCreatedBy(getCreatedBy());
			beanBizComponentDataRef.setCreatedTime(getCurrentTime());
		}
		beanBizComponentDataRef.setUpdatedBy(getCreatedBy()); 
		beanBizComponentDataRef.setUpdatedTime(getCurrentTime());
		getBusinessComponentService().saveOrUpdate((T)beanBizComponentDataRef);
		LOG.info("Exit  into saveComponentDataRef method");
	}
	
	public void reset() {
		businessComponentName = "";
		businessComponentTypeId = null;
		globalgridstatus = false;
		globalmessage = "";
		isMultiple = "N";
		minValue = 0;
		maxValue = 0;
		isNumeric = "N";
		isAlphabet = "Y";
		isSpecial = "N";
		isRequired = "N";
		isReadOnly = "N";
		isEnable = "Y";
		isVisible = "Y";
		componentComboData = "";
		isMultipleStatus = false;
		combComponentErrMsg = "";
		bcdatagridStatus = false;
		createdBy = "";
		businessComponent = null;
		businessComponentConf = null;
		bizComponentConfDetail = null;
		bizComponentDataRef = null;
		bizComponentData = null;
		bizComponentDataDesc = null;
		currentTime = null;
		languageId = null;
		rowMinLength = true;
		rowMaxLength = true;
		rowNumeric = true;
		rowAlpha = true;
		rowSpecial = true;
		rowRequired = true;
		rowReadOnly = true;
		rowEnable = true;
		rowVisible = true;
		rowMultiple = true;
		lstCompDataActual = new ArrayList<BusnessComponentCompBean>();
		lstCompData = new ArrayList<BusnessComponentCompBean>();
		lstRemovedCompData = new ArrayList<BusnessComponentCompBean>();
		mapComponentDataRef = new HashMap<BigDecimal, BizComponentDataRef>();
		lstLanguageType = new ArrayList<LanguageType>();
		lstColumnName = new ArrayList<String>();
	}
	
	public void navigateBusinessComponentPage() throws IOException{
		reset();
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.redirect("../ruleengine/businesscomponent.xhtml");
	}
}
