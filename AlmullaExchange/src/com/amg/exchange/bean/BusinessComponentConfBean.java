package com.amg.exchange.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.BizComponentConfDetail;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.BizComponentDataRef;
import com.amg.exchange.common.model.BusinessComponent;
import com.amg.exchange.common.model.BusinessComponentConf;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.ComponentType;
import com.amg.exchange.common.model.ComponentTypeDetail;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.model.RuleApplicationMaster;
import com.amg.exchange.common.model.RulePageMaster;
import com.amg.exchange.common.service.IBusinessComponentConfService;
import com.amg.exchange.common.service.IBusinessComponentService;
import com.amg.exchange.common.service.IComponentTypeService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.util.SessionStateManage;

@Component("businesscomponentconf")
@Scope("session")
public class BusinessComponentConfBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(BusinessComponentConfBean.class);

	private String businessComponentName;
	private BigDecimal businessComponentTypeId;
	private boolean globalgridstatus;
	private String globalmessage;  
	private String isMultiple;
	private int minValue = 0;
	private int maxValue = 0;
	private String isNumeric = "N";
	private String isAlphabet = "Y";
	private String isSpecial = "N";
	private String isRequired = "Y";
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
	private Date currentTime;
	private List<BusnessComponentCompBean> lstCompData = new ArrayList<BusnessComponentCompBean>();
	private List<BussComponentLevelBean> lstLevelBean = new ArrayList<BussComponentLevelBean>();
	
	private BigDecimal componentId;
	private BigDecimal applicationId;
	private BigDecimal companyId;
	private BigDecimal countryId;
	private BigDecimal pageId;
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

	private boolean gridInput = true;
	private boolean gridBehavior = false;
	private boolean gridData = true;
	private boolean gridSubmit = false;
	
	private List<BusinessComponent> lstComponent = new ArrayList<BusinessComponent>();
	private List<RuleApplicationMaster> lstApplication = new ArrayList<RuleApplicationMaster>();
	private List<CompanyMasterDesc> lstCompany= new ArrayList<CompanyMasterDesc>();
	private List<CountryMasterDesc> lstCountry = new ArrayList<CountryMasterDesc>();
	private List<RulePageMaster> lstPage = new ArrayList<RulePageMaster>();
	private DualListModel<String> dualCompData = new DualListModel<String>();
	private Map<String,BusnessComponentCompBean> mapCompData = new HashMap<String,BusnessComponentCompBean>();
	private Map<String,BusnessComponentCompBean> mapDefaultCompData = new HashMap<String,BusnessComponentCompBean>();
	private List<String> lstDefaultCompData = new ArrayList<String>();
	private Map<BigDecimal, String> mapCountryList = new HashMap<BigDecimal, String>();
	
	private Map<BigDecimal, BizComponentDataRef> mapComponentDataRef = new HashMap<BigDecimal, BizComponentDataRef>();
	private List<LanguageType> lstLanguageType = new ArrayList<LanguageType>();
	private Map<String, BigDecimal> mapAllComponentData = new HashMap<String, BigDecimal>();
	private Map<BigDecimal, ComponentListBean> mapComponentListBean = new HashMap<BigDecimal, ComponentListBean>();
	
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	IBusinessComponentConfService<T> businessComponentConfService;

	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	IBusinessComponentService<T> businessComponentService;
	
	@Autowired
	IComponentTypeService<T> componentTypeService;

	
	public String getBusinessComponentName() {
		return businessComponentName;
	}
	
	public Map<BigDecimal, ComponentListBean> getMapComponentListBean() {
		return mapComponentListBean;
	}



	public void setMapComponentListBean(
			Map<BigDecimal, ComponentListBean> mapComponentListBean) {
		this.mapComponentListBean = mapComponentListBean;
	}



	public String getCreatedBy() {
		return createdBy;
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

	public BigDecimal getLanguageId() {
		return languageId;
	}

	public Map<BigDecimal, BizComponentDataRef> getMapComponentDataRef() {
		return mapComponentDataRef;
	}

	public List<LanguageType> getLstLanguageType() {
		return lstLanguageType;
	}
	
	public boolean isGridInput() {
		return gridInput;
	}

	public void setGridInput(boolean gridInput) {
		this.gridInput = gridInput;
	}

	public boolean isGridBehavior() {
		return gridBehavior;
	}

	public void setGridBehavior(boolean gridBehavior) {
		this.gridBehavior = gridBehavior;
	}

	public boolean isGridData() {
		return gridData;
	}

	public void setGridData(boolean gridData) {
		this.gridData = gridData;
	}

	public boolean isGridSubmit() {
		return gridSubmit;
	}

	public void setGridSubmit(boolean gridSubmit) {
		this.gridSubmit = gridSubmit;
	}

	public void setLstLanguageType(List<LanguageType> lstLanguageType) {
		this.lstLanguageType = lstLanguageType;
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

	public Map<String, BigDecimal> getMapAllComponentData() {
		return mapAllComponentData;
	}

	public void setMapAllComponentData(Map<String, BigDecimal> mapAllComponentData) {
		this.mapAllComponentData = mapAllComponentData;
	}

	public IBusinessComponentService<T> getBusinessComponentService() {
		return businessComponentService;
	}

	public void setBusinessComponentService(IBusinessComponentService<T> businessComponentService) {
		this.businessComponentService = businessComponentService;
	}

	public void setMapComponentDataRef(Map<BigDecimal, BizComponentDataRef> mapComponentDataRef) {
		this.mapComponentDataRef = mapComponentDataRef;
	}

	public void setLanguageId(BigDecimal languageId) {
		this.languageId = languageId;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Map<String, BusnessComponentCompBean> getMapCompData() {
		return mapCompData;
	}

	public void setMapCompData(Map<String, BusnessComponentCompBean> mapCompData) {
		this.mapCompData = mapCompData;
	}

	public Map<String, BusnessComponentCompBean> getMapDefaultCompData() {
		return mapDefaultCompData;
	}

	public void setMapDefaultCompData(
			Map<String, BusnessComponentCompBean> mapDefaultCompData) {
		this.mapDefaultCompData = mapDefaultCompData;
	}

	public List<String> getLstDefaultCompData() {
		return lstDefaultCompData;
	}

	public void setLstDefaultCompData(List<String> lstDefaultCompData) {
		this.lstDefaultCompData = lstDefaultCompData;
	}

	public BusinessComponent getBusinessComponent() {
		return businessComponent;
	}

	public void setBusinessComponent(BusinessComponent businessComponent) {
		this.businessComponent = businessComponent;
	}

	public Date getCurrentTime() {
		Date objList = getGeneralService().getSysDateTimestamp(sessionStateManage.getCountryId());
		
		if(objList != null){
		currentTime = objList;}
		else{
			currentTime =null;
		}
		
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public boolean getBcdatagridStatus() {
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

	public boolean isMultipleStatus() {
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
	
	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
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

	public IBusinessComponentConfService<T> getBusinessComponentConfService() {
		return businessComponentConfService;
	}

	public void setBusinessComponentConfService(
			IBusinessComponentConfService<T> businessComponentConfService) {
		this.businessComponentConfService = businessComponentConfService;
	}

	public BusinessComponentConf getBusinessComponentConf() {
		return businessComponentConf;
	}

	public void setBusinessComponentConf(BusinessComponentConf businessComponentConf) {
		this.businessComponentConf = businessComponentConf;
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

	public BigDecimal getComponentId() {
		return componentId;
	}

	public void setComponentId(BigDecimal componentId) {
		this.componentId = componentId;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getPageId() {
		return pageId;
	}

	public void setPageId(BigDecimal pageId) {
		this.pageId = pageId;
	}

	public List<BusinessComponent> getLstComponent() {
		return lstComponent;
	}

	public void setLstComponent(List<BusinessComponent> lstComponent) {
		this.lstComponent = lstComponent;
	}

	public List<RuleApplicationMaster> getLstApplication() {
		return lstApplication;
	}

	public void setLstApplication(List<RuleApplicationMaster> lstApplication) {
		this.lstApplication = lstApplication;
	}

	public List<CompanyMasterDesc> getLstCompany() {
		return lstCompany;
	}

	public void setLstCompany(List<CompanyMasterDesc> lstCompany) {
		this.lstCompany = lstCompany;
	}

	public List<CountryMasterDesc> getLstCountry() {
		return lstCountry;
	}

	public DualListModel<String> getDualCompData() {
		return dualCompData;
	}

	public void setDualCompData(DualListModel<String> dualCompData) {
		this.dualCompData = dualCompData;
	}

	public void setLstCountry(List<CountryMasterDesc> lstCountry) {
		this.lstCountry = lstCountry;
	}

	public List<RulePageMaster> getLstPage() {
		return lstPage;
	}

	public void setLstPage(List<RulePageMaster> lstPage) {
		this.lstPage = lstPage;
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}
	
	public List<BussComponentLevelBean> getLstLevelBean() {
		return lstLevelBean;
	}

	public void setLstLevelBean(List<BussComponentLevelBean> lstLevelBean) {
		this.lstLevelBean = lstLevelBean;
	}

	public Map<BigDecimal, String> getMapCountryList() {
		return mapCountryList;
	}

	public void setMapCountryList(Map<BigDecimal, String> mapCountryList) {
		this.mapCountryList = mapCountryList;
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
			LOG.error("Exception occured in BusinessComponentConfBean ::getComponentType method ", e);
		}
		LOG.info("Exit  into getComponentType method");
		return lstComponentType;
	}
		
	private boolean getBoolean(String data) {
		return data.equalsIgnoreCase("y") ? true : false;
	}

	//Get All Business component list
	public List<BusinessComponent> getAllBusinessComponent() {
		if (getLstComponent() == null || getLstComponent().size() == 0) {
			setLstComponent(getGeneralService().getAllComponentList());
		}
		return getLstComponent();
	}

	//Get All Application list
	public List<RuleApplicationMaster> getAllApplicationList() {
		LOG.info("Entering  into getAllApplicationList method");
		if (getLstApplication() == null || getLstApplication().size() == 0) {
			SessionStateManage ssManage = new SessionStateManage();
			setLanguageId(new BigDecimal(ssManage.isExists("languageId") ? ssManage.getSessionValue("languageId") : "1"));
			setLstApplication(getGeneralService().getAllApplicationList());
		}
		LOG.info("Exit  into getAllApplicationList method");
		return getLstApplication();
	}

	//Get All Company list
	public List<CompanyMasterDesc> getAllCompanyList() {
		LOG.info("Entering  into getAllCompanyList method");
		if (getLstCompany() == null || getLstCompany().size() == 0) {
			SessionStateManage sessionStateManage = new SessionStateManage();
			setLstCompany(getGeneralService().getAllCompanyList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
		}
		LOG.info("Exit  into getAllCompanyList method");
		return getLstCompany();
	}

	//Get All Country list
	public List<CountryMasterDesc> getAllCountryList() {
		LOG.info("Entering  into getAllCountryList method");
		if (getLstCountry() == null || getLstCountry().size() == 0) {
			SessionStateManage sessionStateManage = new SessionStateManage();
			setLstCountry(getGeneralService().getCountryList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
			if (mapCountryList.size() == 0) {
				for (CountryMasterDesc bean : getLstCountry()) {
					mapCountryList.put(bean.getFsCountryMaster().getCountryId(), bean.getCountryName());
				}
			}
		}
		LOG.info("Exit  into getAllCountryList method");
		return getLstCountry();
	}
	
	//Get All Page list
	public List<RulePageMaster> getAllPageList() {
		if (getLstPage() == null || getLstPage().size() == 0) {
			setLstPage(getGeneralService().getAllPageList());
		}
		return getLstPage();
	}
	
	private void hideUnwantedRow() {
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
		}
	}
	
	//Load default drop down list for selected component
	private void loadDefaultDropDownList() {
		LOG.info("Entering  into loadDefaultDropDownList method");
		// Reset collection objects
		List<BizComponentDataRef> lstBizComponentDataRef = null;
		mapDefaultCompData = new HashMap<String, BusnessComponentCompBean>();
		lstDefaultCompData = new ArrayList<String>();
		BusnessComponentCompBean busnessComponentCompBean = new BusnessComponentCompBean();
		// Load default drop down list
		try {
			// Fetch drop down from business component combo data by component
			// configuration id
			lstBizComponentDataRef = getBusinessComponentConfService().getBusinessComponentDataRef(
					getBusinessComponentConfService().getBusinessComponent(getComponentId()).getFsBusinessComponentConf().getComponentConfId(), getLanguageId());
			for (BizComponentDataRef bean : lstBizComponentDataRef) {
				busnessComponentCompBean = new BusnessComponentCompBean();
				busnessComponentCompBean.setBussComponentComboDataId(bean.getFsBizComponentData().getComponentDataId());
				busnessComponentCompBean.setComponentData(bean.getFsBizComponentData().getFsBizComponentDataDescs().get(0).getDataDesc());
				busnessComponentCompBean.setComponentid(null);
				busnessComponentCompBean.setCreatedBy(bean.getCreatedBy());
				busnessComponentCompBean.setCreatedTime(bean.getCreatedTime());
				busnessComponentCompBean.setBizComponentData(bean.getFsBizComponentData());
				busnessComponentCompBean.setBizComponentDataRef(bean);
				lstDefaultCompData.add(busnessComponentCompBean.getComponentData());
				mapDefaultCompData.put(busnessComponentCompBean.getComponentData(), busnessComponentCompBean);
			}
		} catch (Exception e) {
			LOG.error("Exception occured in BusinessComponentConfBean ::loadDefaultDropDownList method ", e);
		}
		LOG.info("Exit  into loadDefaultDropDownList method");
	}
	
	private void getLevelDataTable(){
		LOG.info("Entering  into getLevelDataTable method");
		lstLevelBean.clear();
		BussComponentLevelBean bean = new BussComponentLevelBean();
		try{
			List<Object[]> lstObject = getBusinessComponentConfService().getBusinessComponentTree(getComponentId());
			
	        for (Iterator<Object[]> it = lstObject.iterator(); it.hasNext();) {
	            Object[] row = (Object[]) it.next();
	            try{
		            bean = new BussComponentLevelBean();
		            bean.setApplicationId(row[0]==null?null:new BigDecimal(row[0].toString()));
		            bean.setApplicationName(row[1]==null?"*":row[1].toString());
		            bean.setCompanyId(row[2]==null?null:new BigDecimal(row[2].toString()));
		            bean.setCompanyName(row[3]==null?"*":row[3].toString());
		            bean.setCountryId(row[4]==null?null:new BigDecimal(row[4].toString()));
		            bean.setCountryName(mapCountryList.containsKey(bean.getCountryId())?mapCountryList.get(bean.getCountryId()):"*");
		            bean.setPageId(row[5]==null?null:new BigDecimal(row[5].toString()));
		            bean.setPageName(row[6]==null?"*":row[6].toString());
		            bean.setComponentConfId(row[7]==null?null:new BigDecimal(row[7].toString()));
		            lstLevelBean.add(bean);
	            }catch(Exception e1){
	            	LOG.error("Exception occured in BusinessComponentConfBean ::getLevelDataTable method ", e1);
	            }
	        	LOG.info("Exit  into getLevelDataTable method");
	        }
			
		}catch(Exception e){
			lstLevelBean.clear();
			LOG.error("Exception occured in BusinessComponentConfBean ::getLevelDataTable method ", e);
		}
		LOG.info("Exit  into getLevelDataTable method");
	}
	
	//Fetch business component detail by component name which is entered by user (For Modification purpose)
	public void getAllPatterns() {
		getLevelDataTable();
		setGridBehavior(false);
		setBcdatagridStatus(false);
		setGridSubmit(false);
	}

	public void getBusinessComponentDetail() {
		
		LOG.info("Entering  into getBusinessComponentDetail method");
		// Check if it is exist or not
		boolean isAvailable = false;
		BizComponentConfDetail bizComponentConfDetail = new BizComponentConfDetail();
		try {
			// Component behavior available by selection
			bizComponentConfDetail = getBusinessComponentConfService().getBusinessComponent(getComponentId(), getApplicationId(), getCompanyId(), getCountryId(), getPageId());
			isAvailable = true;
		} catch (Exception e) {
			LOG.error("Exception occured in BusinessComponentConfBean ::getBusinessComponentDetail method ", e);
			try {
				// Component default behavior availability
				bizComponentConfDetail = getBusinessComponentConfService().getBusinessComponent(getComponentId());
				isAvailable = true;
			} catch (Exception e1) {
				LOG.error("Exception occured in BusinessComponentConfBean ::getBusinessComponentDetail method ", e1);
				isAvailable = false;
			}
		}
		try {
			getComponentType();
		} catch (Exception e) {
			LOG.error("Exception occured in BusinessComponentConfBean ::getBusinessComponentDetail method ", e);
		}
		// If data is available then
		if (isAvailable) {
			// Set Business Component Data
			setBusinessComponent(bizComponentConfDetail.getFsBusinessComponentConf().getFsBusinessComponent());
			setBusinessComponentTypeId(getBusinessComponent().getFsComponentType().getComponentTypeId());
			setIsMultiple(getBusinessComponent().getIsMultiple());
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
			/* if(getIsMultiple().equals("Y")){
			 * hideUnwantedRow(false);
			 * }else{
			 * hideUnwantedRow(true);
			 * } */
			hideUnwantedRow();
			setGridBehavior(true);
			setBcdatagridStatus(true);
			setGridSubmit(true);
			// Assign fetched object into bean object
			this.businessComponentConf = bizComponentConfDetail.getFsBusinessComponentConf();
			this.bizComponentConfDetail = bizComponentConfDetail;
		} else {
			// If data not exist set all model object as null
			/* hideUnwantedRow(true); */
			hideUnwantedRow();
			setBusinessComponent(null);
			setGridBehavior(false);
			setBcdatagridStatus(false);
			setGridSubmit(false);
			this.businessComponentConf = null;
			this.bizComponentConfDetail = null;
		}
		// Check drop down data list
		if (isAvailable && getIsMultiple().equalsIgnoreCase("Y")) {
			loadDefaultDropDownList();
			List<BizComponentDataRef> lstBizComponentDataRef = null;
			try {
				// Fetch drop down list by business component conf id
				lstBizComponentDataRef = getBusinessComponentConfService().getBusinessComponentDataRef(bizComponentConfDetail.getFsBusinessComponentConf().getComponentConfId(), getLanguageId());
				if (lstBizComponentDataRef.size() > 0) {
					setBcdatagridStatus(true);
					setComponentComboData("");
					setCombComponentErrMsg("");
					lstCompData.clear();
					dualCompData.setSource(null);
					dualCompData.setTarget(null);
					List<String> tempList = new ArrayList<String>();
					// Append fetched values into BusnessComponentCompBean for
					// prepare data table
					BusnessComponentCompBean busnessComponentCompBean;
					for (BizComponentDataRef bizComponentDataRef : lstBizComponentDataRef) {
						if (bizComponentDataRef.getActive().equalsIgnoreCase("Y")) {
							busnessComponentCompBean = new BusnessComponentCompBean();
							busnessComponentCompBean.setSerialid(new BigDecimal(lstCompData.size() + 1));
							busnessComponentCompBean.setBussComponentComboDataId(bizComponentDataRef.getFsBizComponentData().getComponentDataId());
							busnessComponentCompBean.setComponentData(bizComponentDataRef.getFsBizComponentData().getFsBizComponentDataDescs().get(0).getDataDesc());
							busnessComponentCompBean.setCreatedBy(bizComponentDataRef.getCreatedBy());
							busnessComponentCompBean.setCreatedTime(bizComponentDataRef.getCreatedTime());
							busnessComponentCompBean.setLanguageId(null);
							busnessComponentCompBean.setComponentid(null);
							busnessComponentCompBean.setBizComponentDataRef(bizComponentDataRef);
							lstCompData.add(busnessComponentCompBean);
							mapCompData.put(busnessComponentCompBean.getComponentData(), busnessComponentCompBean);
							tempList.add(busnessComponentCompBean.getComponentData());
						}
					}
					List<String> srcList = new ArrayList<String>();
					srcList.addAll(lstDefaultCompData);
					srcList.removeAll(tempList);
					dualCompData = new DualListModel<String>(srcList, tempList);
				}
			} catch (Exception e) {
				LOG.error("Exception occured in BusinessComponentConfBean ::getBusinessComponentDetail method ", e);
			}
		} else {
			// If drop down not exist reset displayed value
			setBcdatagridStatus(false);
			setComponentComboData("");
			setCombComponentErrMsg("");
			lstCompData.clear();
			dualCompData.setSource(null);
			dualCompData.setTarget(null);
			mapDefaultCompData = new HashMap<String, BusnessComponentCompBean>();
			lstDefaultCompData = new ArrayList<String>();
			dualCompData = new DualListModel<String>(new ArrayList<String>(), new ArrayList<String>());
		}
		LOG.info("Exit  into getBusinessComponentDetail method");
	}
	
	
	//Drop down component list visibility control
	public void showComponentData(){
		
		lstCompData.clear();
		setComponentComboData("");
		setCombComponentErrMsg("");
		hideUnwantedRow();
	}
	
	public void applySelection(SelectEvent event) {
		BussComponentLevelBean bean = (BussComponentLevelBean) event.getObject();
		setApplicationId(bean.getApplicationId() == null ? new BigDecimal(0) : bean.getApplicationId());
		setCompanyId(bean.getCompanyId() == null ? new BigDecimal(0) : bean.getCompanyId());
		setCountryId(bean.getCountryId() == null ? new BigDecimal(0) : bean.getCountryId());
		setPageId(bean.getPageId() == null ? new BigDecimal(0) : bean.getPageId());
	}
	
	//Save or update 
	public void saveData() {
		LOG.info("Entering  into saveData method");
		SessionStateManage sessionStateManage = new SessionStateManage();
		// Fetch user name from session
		if (sessionStateManage.isExists("username")) {
			setCreatedBy(sessionStateManage.getSessionValue("username"));
		} else {
			setCreatedBy("ROOT");
		}
		if (getComponentId() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select Component", ""));
		} else if (getApplicationId() == null && getCompanyId() == null && getCountryId() == null && getPageId() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select Application/Company/Country/Page", ""));
		} else {
		}
	
		// Save business component configuration
		saveComponentConf();
		// Save business component configuration detail
		saveComponentConfDetail();
		// Is multiple value then store data table data into component combo
		// detail
		if (getIsMultiple().equalsIgnoreCase("Y")) {
			/* saveComponentComboDetail(); */
			saveComponentDataRef();
		}
		LOG.info("Exit  into saveData method");
	}
	
	//Save or update component conf information
	@SuppressWarnings("unchecked")
	private void saveComponentConf() {
		LOG.info("Entering  into saveComponentConf method");
		// Check data exist or not in configuration table
		boolean isExists = true;
		try {
			bizComponentConfDetail = getBusinessComponentConfService().getBusinessComponent(getComponentId(), getApplicationId(), getCompanyId(), getCountryId(), getPageId());
			businessComponentConf = bizComponentConfDetail.getFsBusinessComponentConf();
			businessComponent = businessComponentConf.getFsBusinessComponent();
			isExists = true;
		} catch (Exception e) {
			businessComponentConf = new BusinessComponentConf();
			bizComponentConfDetail = null;
			isExists = false;
		}
		businessComponentConf.setFsBusinessComponent(businessComponent);
		if (getApplicationId() != null && getApplicationId().intValue() != 0) {
			RuleApplicationMaster ruleApplicationMaster = new RuleApplicationMaster();
			ruleApplicationMaster.setApplicationId(getApplicationId());
			businessComponentConf.setFsRuleApplicationMaster(ruleApplicationMaster);
		} else {
			businessComponentConf.setFsRuleApplicationMaster(null);
		}
		if (getCompanyId() != null && getCompanyId().intValue() != 0) {
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(getCompanyId());
			businessComponentConf.setFsCompanyMaster(companyMaster);
		} else {
			businessComponentConf.setFsCompanyMaster(null);
		}
		if (getCountryId() != null && getCountryId().intValue() != 0) {
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(getCountryId());
			businessComponentConf.setFsCountryMaster(countryMaster);
		} else {
			businessComponentConf.setFsCountryMaster(null);
		}
		if (getPageId() != null && getPageId().intValue() != 0) {
			RulePageMaster rulePageMaster = new RulePageMaster();
			rulePageMaster.setPageId(getPageId());
			businessComponentConf.setFsRulePageMaster(rulePageMaster);
		} else {
			businessComponentConf.setFsRulePageMaster(null);
		}
		businessComponentConf.setLevelId(prepareLevelId());
		// If it is update not required
		if (!isExists) {
			businessComponentConf.setCreatedBy(getCreatedBy());
			businessComponentConf.setCreatedTime(getCurrentTime());
		}
		businessComponentConf.setUpdatedBy(getCreatedBy());
		businessComponentConf.setUpdatedTime(getCurrentTime());
		getBusinessComponentConfService().saveOrUpdate((T) businessComponentConf);
		LOG.info("Exit  into saveComponentConf method");
	}
	
	//Prepare level id
	private BigDecimal prepareLevelId() {
		BigDecimal ret = null;
		StringBuffer sbf = new StringBuffer();
		sbf.append(getApplicationId() == null || getApplicationId().intValue() == 0 ? 0 : 1);
		sbf.append(getCompanyId() == null || getCompanyId().intValue() == 0 ? 0 : 1);
		sbf.append(getCountryId() == null || getCountryId().intValue() == 0 ? 0 : 1);
		sbf.append(getPageId() == null || getPageId().intValue() == 0 ? 0 : 1);
		ret = new BigDecimal(Integer.parseInt(sbf.toString(), 2));
		return ret;
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
		getBusinessComponentConfService().saveOrUpdate((T) bizComponentConfDetail);
		LOG.info("Exit  into saveComponentConfDetail method");
	}
	
	//Save or update drop down list
	@SuppressWarnings({ "unchecked", "deprecation" })
	private void saveComponentDataRef() {
		LOG.info("Entering  into saveComponentDataRef method");
		// get data from BusnessComponentCompBean (Data table bean) and set to
		// Business component combo data model
		BizComponentData bizComponentData;
		Map<String, BizComponentDataRef> mapFinalList = new HashMap<String, BizComponentDataRef>();
		try {
			mapAllComponentData = getBusinessComponentConfService().getBusinessComponentData(getComponentId(), getLanguageId());
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).log(Priority.INFO, "Component Data Not Found!");
		}
		List<String> target = dualCompData.getTarget();
		try {
			for (BizComponentDataRef bean : getBusinessComponentConfService().getBusinessComponentDataRef(businessComponentConf.getComponentConfId(), getLanguageId())) {
				mapFinalList.put(bean.getFsBizComponentData().getFsBizComponentDataDescs().get(0).getDataDesc(), bean);
			}
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).log(Priority.INFO, "Component Data Not Found!");
		}
		for (String strTarget : target) {
			if (mapFinalList.containsKey(strTarget)) {
				bizComponentDataRef = mapFinalList.get(strTarget);
			} else {
				bizComponentDataRef = new BizComponentDataRef();
				bizComponentData = new BizComponentData();
				bizComponentData.setComponentDataId(mapAllComponentData.get(strTarget));
				bizComponentDataRef.setFsBusinessComponentConf(businessComponentConf);
				bizComponentDataRef.setFsBizComponentData(bizComponentData);
				bizComponentDataRef.setCreatedBy(getCreatedBy());
				bizComponentDataRef.setCreatedTime(getCurrentTime());
			}
			bizComponentDataRef.setActive("Y");
			bizComponentDataRef.setUpdatedBy(getCreatedBy());
			bizComponentDataRef.setUpdatedTime(getCurrentTime());
			getBusinessComponentService().saveOrUpdate((T) bizComponentDataRef);
			if (mapFinalList.containsKey(strTarget)) {
				mapFinalList.remove(strTarget);
			}
		}
		for (String key : mapFinalList.keySet()) {
			BizComponentDataRef bean = mapFinalList.get(key);
			bean.setActive("N");
			getBusinessComponentConfService().saveOrUpdate((T) bean);
		}
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
		isRequired = "Y";
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
		currentTime = null;
		lstCompData = new ArrayList<BusnessComponentCompBean>();
		lstLevelBean = new ArrayList<BussComponentLevelBean>();
		componentId = null;
		applicationId = null;
		companyId = null;
		countryId = null;
		pageId = null;
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
		gridInput = true;
		gridBehavior = false;
		gridData = true;
		gridSubmit = false;
		lstComponent = new ArrayList<BusinessComponent>();
		lstApplication = new ArrayList<RuleApplicationMaster>();
		lstCompany = new ArrayList<CompanyMasterDesc>();
		lstCountry = new ArrayList<CountryMasterDesc>();
		lstPage = new ArrayList<RulePageMaster>();
		dualCompData = new DualListModel<String>();
		mapCompData = new HashMap<String, BusnessComponentCompBean>();
		mapDefaultCompData = new HashMap<String, BusnessComponentCompBean>();
		lstDefaultCompData = new ArrayList<String>();
		mapCountryList = new HashMap<BigDecimal, String>();
		mapComponentDataRef = new HashMap<BigDecimal, BizComponentDataRef>();
		lstLanguageType = new ArrayList<LanguageType>();
		mapAllComponentData = new HashMap<String, BigDecimal>();
	}
	
	public void navigateBusinessComponentConfPage() throws IOException{
		reset();
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.redirect("../ruleengine/businesscomponentconf.xhtml");
	}
}
