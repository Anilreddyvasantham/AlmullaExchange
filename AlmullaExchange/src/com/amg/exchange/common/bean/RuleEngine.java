package com.amg.exchange.common.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectManyMenu;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.Validator;

import org.apache.log4j.Logger;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.DeclareConstants;
import com.amg.exchange.common.ServiceConfig;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.util.CollectionUtil;
import com.amg.exchange.util.JsonUtil;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("serial")
/*@ManagedBean(name = "ruleEngine")
@SessionScoped*/
@Component(value = "ruleEngine")
@Scope("session")
public class RuleEngine<T> implements Serializable {
	
	private static final Logger logger = Logger.getLogger(RuleEngine.class);
	
	@Autowired
	private RestClient restClient;
	
	@Autowired
	IGeneralService<T> generalService;

	private FacesContext context;
	private Application application;
	private HtmlInputText htmlInputText;
	private HtmlSelectOneMenu htmlSelectOneMenu;
	private HtmlSelectManyMenu htmlSelectManyMenu;
	private UIComponent component;
	private Validator validator;
	
	/*@ManagedProperty(value="#{msg}")*/
	private ResourceBundle resourceBundle;

	private CollectionUtil collectionUtil = new CollectionUtil();

	private List<String> lstComponentName = new ArrayList<String>();
	private Map<String, String> mapLabel = new HashMap<String, String>();
	private Map<String, UIComponent> mapComponentList = new HashMap<String, UIComponent>();
	private JSONObject jsonBehavior = new JSONObject();
	private String pageName = ""; 
	private boolean sop = true;
	
	public ResourceBundle getResourceBundle() {
/*		if (resourceBundle == null) {
		}*/
		FacesContext context = FacesContext.getCurrentInstance();
		resourceBundle = context.getApplication().getResourceBundle(context, "msg");
		return resourceBundle;
	}

	public void setResourceBundle(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	public RestClient getRestClient() {
		return restClient;
	}

	public void setRestClient(RestClient restClient) {
		this.restClient = restClient;
	}

	public FacesContext getContext() {
		return context;
	}

	public void setContext(FacesContext context) {
		this.context = context;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public HtmlInputText getHtmlInputText() {
		return htmlInputText;
	}

	public void setHtmlInputText(HtmlInputText htmlInputText) {
		this.htmlInputText = htmlInputText;
	}

	public HtmlSelectOneMenu getHtmlSelectOneMenu() {
		return htmlSelectOneMenu;
	}

	public void setHtmlSelectOneMenu(HtmlSelectOneMenu htmlSelectOneMenu) {
		this.htmlSelectOneMenu = htmlSelectOneMenu;
	}

	public UIComponent getComponent() {
		return component;
	}

	public void setComponent(UIComponent component) {
		this.component = component;
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public CollectionUtil getCollectionUtil() {
		return collectionUtil;
	}

	public void setCollectionUtil(CollectionUtil collectionUtil) {
		this.collectionUtil = collectionUtil;
	}

	public List<String> getLstComponentName() {
		return lstComponentName;
	}

	public void setLstComponentName(List<String> lstComponentName) {
		this.lstComponentName = lstComponentName;
	}

	public Map<String, String> getMapLabel() {
		return mapLabel;
	}

	public void setMapLabel(Map<String, String> mapLabel) {
		this.mapLabel = mapLabel;
	}

	public Map<String, UIComponent> getMapComponentList() {
		return mapComponentList;
	}

	public void setMapComponentList(Map<String, UIComponent> mapComponentList) {
		this.mapComponentList = mapComponentList;
	}

	public void init(){
		
		//Set page id into session
		long l2 = 0L;
		long l3 = 0L;
		long l4 = 0L;
		long l5 = 0L;
		long l1 = System.currentTimeMillis();
		try{
			getResourceBundle();
			int status = setPageIdIntoSession();
			if(status==1){
				l2 = System.currentTimeMillis();
				context = FacesContext.getCurrentInstance(); 
				application = context.getApplication();
				getList(context.getViewRoot());
				l3 = System.currentTimeMillis();
				lstComponentName = new ArrayList<String>(mapLabel.values());
				getRules();
				l4 = System.currentTimeMillis();
				
				if(jsonBehavior==null || !jsonBehavior.has("data") || (jsonBehavior.has("error") && jsonBehavior.getInt("error")==1)){
					logger.info("Rule information not found for this page......!");
				}else{
					setRules();
				}
				l5 = System.currentTimeMillis();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(sop){
			/*System.out.println("---------------------------------------------------------");
			System.out.println("Start : 0");
			System.out.println("Set Page ID : "+(l2-l1));
			System.out.println("Prepare Component List : "+(l3-l2));
			System.out.println("Get Rules : "+(l3-l4));
			System.out.println("Set Rules : "+(l4-l5));
			System.out.println("Total : "+(l5-l1));
			System.out.println("---------------------------------------------------------");
			System.out.println("all component name ---------------------------- "+lstComponentName);
*/		}
	}
	
	public void getList(UIComponent root){
		
		for(UIComponent component: root.getChildren()){
			
			if(component instanceof HtmlInputText){
				htmlInputText = (HtmlInputText)component;
				mapLabel.put(htmlInputText.getId(), htmlInputText.getLabel());
				mapComponentList.put(htmlInputText.getId(), component);
			}else if(component instanceof HtmlSelectOneMenu){
				htmlSelectOneMenu = (HtmlSelectOneMenu)component;
				mapLabel.put(htmlSelectOneMenu.getId(), htmlSelectOneMenu.getLabel());
				mapComponentList.put(htmlSelectOneMenu.getId(), component);
			}else if(component instanceof HtmlSelectManyMenu){
				htmlSelectManyMenu = (HtmlSelectManyMenu)component;
				mapLabel.put(htmlSelectManyMenu.getId(), htmlSelectManyMenu.getLabel());
				mapComponentList.put(htmlSelectManyMenu.getId(), component);
			}else if(component instanceof Calendar){
				Calendar calendar = (Calendar)component;
				mapLabel.put(calendar.getId(), calendar.getLabel());
				mapComponentList.put(calendar.getId(), component);
			}else if(component instanceof HtmlInputTextarea){
				HtmlInputTextarea htmlInputTextarea = (HtmlInputTextarea)component;
				mapLabel.put(htmlInputTextarea.getId(), htmlInputTextarea.getLabel());
				mapComponentList.put(htmlInputTextarea.getId(), component);
			}else if(component instanceof HtmlOutputText){
				//output.add((String)((HtmlOutputText)component).getValue());
			}else if(component.getChildren() !=null && component.getChildren().size()>0){
				getList(component);
			}
		}
	}
	
	public void getRules(){

		SessionStateManage manage = new SessionStateManage();
		
		try{
			JSONObject postParams = new JSONObject();
			JSONObject jsonSub = new JSONObject();
			jsonSub.put("levelId", "0");
			jsonSub.put("languageId", manage.getLanguageId().toPlainString());
			jsonSub.put("applicationId", manage.getApplicationId().toPlainString());
			jsonSub.put("companyId", manage.getCompanyId().toPlainString());
			jsonSub.put("countryId", manage.getCountryId().toPlainString());
			jsonSub.put("pageId", manage.getPageId().toPlainString());
			
			JSONArray array = new JSONArray();
			for(String value:lstComponentName){
				array.put(value);
			}
			jsonSub.put("componentList", array);
			
			postParams.put("input", jsonSub);
			jsonBehavior = restClient.postRequest(ServiceConfig.FETCH_RULE, postParams);
			
		}catch(Exception e){
			logger.error("Rule fetch failed :: Page ID -> "+manage.getPageId());
		}
	}
	
	private String formatComponentName(String source, int size){
		
		String[] strarr = source.split("_");
		if(strarr.length==size){
			source = source.substring(0, source.lastIndexOf('_'));
		}
		return source;
	}
	
	private void setRules(){
		
		UIComponent component;
		Validator validator;
		CollectionUtil collectionUtil = new CollectionUtil();
		JSONObject jsonTemp = new JSONObject();
		JSONObject jsonTempSub = new JSONObject();
		RuleBean ruleBean = null;
		String businessComponent = "";
		String lblName = "";
		String panelName = "";
		String lblMsgName = "";
		SessionStateManage sessionStateManage = new SessionStateManage();
		try{
			jsonTemp = (JSONObject)jsonBehavior.getJSONObject("data");
		}catch(Exception e){
			logger.error("Unable to parse data value ");
		}
		
		for(Entry<String, UIComponent> entry:mapComponentList.entrySet()){
			
			component = (UIComponent)entry.getValue();
			
			
			lblName = component.getClientId().replace(":", ":lbl_");
			//lblName = formatComponentName(lblName, 3);
			
			panelName = lblName.replace("lbl_", "panel_");
			//panelName = formatComponentName(panelName, 3);
			
			lblMsgName = component.getClientId();
			lblMsgName = formatComponentName(lblMsgName, 2);
			
			try{
				lblMsgName = lblMsgName.replace(":", ":lbl.").split(":")[1];
			}catch(Exception e){}
			
			setMandatorySymbol(false, lblName, lblMsgName);
			
			
			if(mapLabel.containsKey(entry.getKey())){
				businessComponent = mapLabel.get(entry.getKey());
			}else{
				businessComponent = "";
				continue;
			}
			
			if(jsonTemp.has(businessComponent)){
				try{
					jsonTempSub = (JSONObject)jsonTemp.getJSONObject(businessComponent);
				}catch(Exception e){
					logger.error("Unable to fetch rule values for Component :-> "+businessComponent);
					continue;
				}
				try{
					ruleBean = new RuleBean(jsonTempSub);
				}catch(JSONException e){
					logger.error("Unable to parse component :-> "+businessComponent);
					continue;
				}catch(NumberFormatException e){
					logger.error("Unable to parse min/max value for component :-> "+businessComponent);
				}
			}else{
				//logger.error("Rule not found for component :-> "+businessComponent);
				continue;
			}
			setVisibility(ruleBean.isVisible(), panelName);
			setVisibility(ruleBean.isVisible(), lblName);
			setMandatorySymbol(ruleBean.isRequired(), lblName, lblMsgName);
			
			if(component instanceof Calendar){

				Calendar calendar = (Calendar)component;
				
				calendar.setReadonly(ruleBean.isReadOnly());
				calendar.setDisabled(!ruleBean.isEnable());
				calendar.setRequired(ruleBean.isRequired());
				calendar.setRendered(ruleBean.isVisible());
				calendar.setPattern(DeclareConstants.DATE_PATTERN);
				if(resourceBundle.containsKey(lblMsgName) && resourceBundle.containsKey("lbl.validationprefix")){
					calendar.setRequiredMessage(resourceBundle.getString("lbl.validationprefix")+" "+resourceBundle.getString(lblMsgName));
				}
				component = calendar;
			}else if(component instanceof HtmlInputTextarea){

				HtmlInputTextarea htmlInputTextarea = (HtmlInputTextarea)component;
				
				htmlInputTextarea.setReadonly(ruleBean.isReadOnly());
				htmlInputTextarea.setDisabled(!ruleBean.isEnable());
				htmlInputTextarea.setRequired(ruleBean.isRequired());
				htmlInputTextarea.setRendered(ruleBean.isVisible());
				if(resourceBundle.containsKey(lblMsgName) && resourceBundle.containsKey("lbl.validationprefix")){
					htmlInputTextarea.setRequiredMessage(resourceBundle.getString("lbl.validationprefix")+" "+resourceBundle.getString(lblMsgName));
				}
				component = htmlInputTextarea;
			}else if(component instanceof HtmlInputText){
				
				htmlInputText = (HtmlInputText)component;
				htmlInputText.setReadonly(ruleBean.isReadOnly());
				htmlInputText.setMaxlength(ruleBean.getMaxValue());
				htmlInputText.setDisabled(!ruleBean.isEnable());
				htmlInputText.setRequired(ruleBean.isRequired());
				htmlInputText.setRendered(ruleBean.isVisible());
				if(resourceBundle.containsKey(lblMsgName) && resourceBundle.containsKey("lbl.validationprefix")){
					htmlInputText.setRequiredMessage(resourceBundle.getString("lbl.validationprefix")+" "+resourceBundle.getString(lblMsgName));
				}
				validator = application.createValidator("RegularExpressionValidation");
				htmlInputText.addValidator(validator);
				
				if(htmlInputText.getLang()!=null && htmlInputText.getLang().equalsIgnoreCase("ar")){
					htmlInputText.setDir("RTL");
					htmlInputText.setStyle(htmlInputText.getStyle()+";text-align:right");
					htmlInputText.getAttributes().put("regexValue", 
							collectionUtil.getArabicPattern(
									ruleBean.isNumeric(), 
									ruleBean.isAlphabet(), 
									ruleBean.isSpecial())
								);
				}else if(entry.getKey().equalsIgnoreCase(DeclareConstants.EMAIL_COMPONENT_NAME)){
					htmlInputText.getAttributes().put("regexValue", DeclareConstants.REGEX_EMAIL);
				}else{
					htmlInputText.getAttributes().put("minValue", ruleBean.getMinValue());
					htmlInputText.getAttributes().put("maxValue", ruleBean.getMaxValue());
					htmlInputText.getAttributes().put("regexValue",
							sessionStateManage.getLanguageId().intValue()==1?
							collectionUtil.getPattern(
									ruleBean.isNumeric(), 
									ruleBean.isAlphabet(), 
									ruleBean.isSpecial()
									):
							collectionUtil.getArabicPattern(
									ruleBean.isNumeric(), 
									ruleBean.isAlphabet(), 
									ruleBean.isSpecial()
									)
							);
				}
				
				//Hide lbl Component
				/*setVisibility(ruleBean.isVisible(), lblName);*/

				component = htmlInputText;
			}else if(component instanceof HtmlSelectOneMenu){
				
				htmlSelectOneMenu = (HtmlSelectOneMenu)component; 
				htmlSelectOneMenu.setReadonly(ruleBean.isReadOnly());
				htmlSelectOneMenu.setDisabled(!ruleBean.isEnable());
				htmlSelectOneMenu.setRequired(ruleBean.isRequired());
				htmlSelectOneMenu.setRendered(ruleBean.isVisible());
				try{
					if(htmlSelectOneMenu.getChildren().size()==0){
						if(jsonTempSub.has("COMPONENT_DATA") && jsonTempSub.getJSONObject("COMPONENT_DATA").length()>0){
							htmlSelectOneMenu.getChildren().clear();
							htmlSelectOneMenu.getChildren().add(getSelectedItems(jsonTempSub));
						}
					}
				}catch(Exception e){}
				if(resourceBundle.containsKey(lblMsgName) && resourceBundle.containsKey("lbl.validselectt")){
					htmlSelectOneMenu.setRequiredMessage(resourceBundle.getString("lbl.validselectt")+" "+resourceBundle.getString(lblMsgName));
				}
				
				component = htmlSelectOneMenu;
			}else if(component instanceof HtmlSelectManyMenu){
				
				htmlSelectManyMenu = (HtmlSelectManyMenu)component; 
				htmlSelectManyMenu.setReadonly(ruleBean.isReadOnly());
				htmlSelectManyMenu.setDisabled(!ruleBean.isEnable());
				htmlSelectManyMenu.setRequired(ruleBean.isRequired());
				htmlSelectManyMenu.setRendered(ruleBean.isVisible());
				try{
					if(htmlSelectManyMenu.getChildren().size()==0){
						if(jsonTempSub.has("COMPONENT_DATA") && jsonTempSub.getJSONObject("COMPONENT_DATA").length()>0){
							htmlSelectManyMenu.getChildren().clear();
							htmlSelectManyMenu.getChildren().add(getSelectedItems(jsonTempSub));
						}
					}
				}catch(Exception e){}
				if(resourceBundle.containsKey(lblMsgName) && resourceBundle.containsKey("lbl.validselectt")){
					htmlSelectManyMenu.setRequiredMessage(resourceBundle.getString("lbl.validselectt")+" "+resourceBundle.getString(lblMsgName));
				}
				
				component = htmlSelectManyMenu;
			}
		}
	}
	
	private void setVisibility(boolean visibility, String labelName){
		
		/*if(!visibility){*/
			UIComponent lblComponent = context.getViewRoot().findComponent(labelName);
			if(lblComponent!=null){
				lblComponent.setRendered(visibility);
			}else{
				lblComponent = context.getViewRoot().findComponent(labelName.split(":")[1]);
				if(lblComponent!=null){
					lblComponent.setRendered(visibility);
				}
			}
		/*}*/
	}
	
	private void setMandatorySymbol(boolean required, String lblName, String lblMsgName){
		
		UIComponent lblComponent = context.getViewRoot().findComponent(lblName);
		if(resourceBundle.containsKey(lblMsgName)){
			
			String symbol = required?" *":"";
			if(lblComponent!=null){
				if(lblComponent instanceof HtmlOutputText){
					HtmlOutputText htmlOutputText = (HtmlOutputText)lblComponent;
					htmlOutputText.setValue(resourceBundle.getString(lblMsgName)+symbol);
				}else if(lblComponent instanceof HtmlOutputLabel){
					HtmlOutputLabel htmlOutputLabel = (HtmlOutputLabel)lblComponent;
					htmlOutputLabel.setValue(resourceBundle.getString(lblMsgName)+symbol);
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private UISelectItems getSelectedItems(JSONObject jsonDropDownData){
		
		UISelectItems items = new UISelectItems();
		SelectItem item = new SelectItem();
		try{
			List comboList = new ArrayList();
			item.setValue("");
			if(resourceBundle.containsKey("lbl.select")){
				item.setLabel(resourceBundle.getString("lbl.select"));
			}else{
				item.setLabel("Select");
			}
			comboList.add(item);
			jsonDropDownData = jsonDropDownData.getJSONObject("COMPONENT_DATA");
			Map<String,String> mapDropDowndata = JsonUtil.parseMapObject(jsonDropDownData);
			mapDropDowndata = JsonUtil.sortMapByValueAsc(mapDropDowndata);
			for(Entry<String, String> entry:mapDropDowndata.entrySet()){
				item = new SelectItem();
				item.setValue(entry.getKey());
				item.setLabel(entry.getValue());
				comboList.add(item);
			}
			items.setValue(comboList);
		}catch(Exception e){
			logger.info("Component Data Not Found :: "+e);
		}
		return items;
	}
	
	private int setPageIdIntoSession(){
		
		int ret;
		SessionStateManage sessionStateManage = new SessionStateManage();
		pageName = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		pageName = pageName.substring(pageName.lastIndexOf('/')+1, pageName.indexOf(".xhtml"));
		try{
			BigDecimal pageId = generalService.getPageId(pageName);
			
			if(pageId != null){
				if(sessionStateManage.getPageId().equals(pageId)){
					ret = 1;
				}else{
					sessionStateManage.setSessionValue("pageId", pageId.toString());
					ret = 1;
				}
			}else{
				ret = 0;
				sessionStateManage.setSessionValue("pageId", "0");
			}
		}catch(Exception e){
			ret = 0;
			sessionStateManage.setSessionValue("pageId", "0");
			logger.error("Page id not found for pagename :: "+pageName+" :: "+e);
		}
		return ret;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getComponentDataList(String componentId){
		
		Map mapComponentData = new LinkedHashMap();
		try{
			UIComponent component = FacesContext.getCurrentInstance().getViewRoot().findComponent(componentId);
			if(component!=null && component instanceof HtmlSelectOneMenu){
				HtmlSelectOneMenu htmlSelectOneMenu = (HtmlSelectOneMenu)component;
				List<UIComponent> items = (List<UIComponent>)htmlSelectOneMenu.getChildren();
				for(UIComponent itemComponent:items){
					if(itemComponent instanceof UISelectItems){
						UISelectItems uiSelectItems = (UISelectItems)itemComponent;
						if(uiSelectItems!=null){
							Set<Entry> it = (Set<Entry>)uiSelectItems.getValue();
							for(Iterator iterator = it.iterator();iterator.hasNext();){
								Entry entry = (Entry) iterator.next();
								mapComponentData.put(entry.getKey(), entry.getValue());
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.info("Unable to fetch drop down data for "+componentId+" --> because :: "+e);
			e.printStackTrace();
		}
		return mapComponentData;
	}
	
	public Map<BigDecimal, String> getComponentData(String componentName){
		
		SessionStateManage manage = new SessionStateManage();
		
		return generalService.getComponentData(
				componentName, 
				null, 
				manage.getApplicationId(), 
				manage.getCompanyId(), 
				manage.getCountryId(), 
				manage.getPageId(), 
				manage.getLanguageId());
	}
	
	public String getCivilIdStatus(BigDecimal civilID){
		
		return generalService.getCivilIdStatus(civilID);
	}
}