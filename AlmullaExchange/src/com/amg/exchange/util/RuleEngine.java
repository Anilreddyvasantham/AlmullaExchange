package com.amg.exchange.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

import com.amg.exchange.common.DeclareConstants;
import com.amg.exchange.common.model.BizComponentConfDetail;
import com.amg.exchange.common.service.IGeneralService;

public class RuleEngine<T> {

	private Map<String, BizComponentConfDetail> mapComponentBehavior = new HashMap<String,BizComponentConfDetail>();
	
	public RuleEngine() {
	
	}
	public void applyRuleEngine(IGeneralService<T> generalService, FacesContext context, String[] componentList,
			BigDecimal applicationId, BigDecimal companyId, BigDecimal countryId, BigDecimal pageId
			){
		
		UIComponent root = context.getViewRoot();
		Map<String, String> mapLabel = getLabels(root, componentList);
		List<String> lstComponentName = new ArrayList<String>(mapLabel.values());
		setMapComponentBehavior(generalService.getComponentBehavior(lstComponentName, null,applicationId,companyId,countryId,pageId));
		Map<String, BizComponentConfDetail> rules = new HashMap<String, BizComponentConfDetail>(); 
		rules.putAll(getMapComponentBehavior());
		setRules(context, root, mapLabel, rules);
	}
	
	private Map<String, String> getLabels(UIComponent root, String[] componentList){
		
		UIComponent component;
		Map<String, String> mapLabel = new HashMap<String, String>();
		for(String componentId: componentList){
			
			component = root.findComponent(componentId);
			if(component instanceof HtmlInputText){
				mapLabel.put(componentId, ((HtmlInputText)component).getLabel());
			}else if(component instanceof HtmlSelectOneMenu){
				mapLabel.put(componentId, ((HtmlSelectOneMenu)component).getLabel());
			}
		}
		return mapLabel;
	}
	
	private void setRules(FacesContext context, UIComponent root, Map<String, String> componentList, Map<String, BizComponentConfDetail> rules){
		
		UIComponent component;
		BizComponentConfDetail bizComponentConfDetail;
		HtmlInputText htmlInputText;
		HtmlSelectOneMenu htmlSelectOneMenu;
		Validator validator;
		Application application = context.getApplication();
		CollectionUtil collectionUtil = new CollectionUtil();
		
		for(Entry<String, String> entry:componentList.entrySet()){
			
			component = root.findComponent(entry.getKey());
			bizComponentConfDetail = rules.get(entry.getValue());
			
			if(component instanceof HtmlInputText){
				
				htmlInputText = (HtmlInputText)component;
				htmlInputText.setReadonly(bizComponentConfDetail.getIsReadOnly().equalsIgnoreCase("Y")?true:false);
				htmlInputText.setMaxlength(bizComponentConfDetail.getMaxValue().intValue());
				htmlInputText.setDisabled(bizComponentConfDetail.getIsEnable().equalsIgnoreCase("Y")?false:true);
				htmlInputText.setRequired(bizComponentConfDetail.getIsRequired().equalsIgnoreCase("Y")?true:false);
				validator = application.createValidator("RegularExpressionValidation");
				htmlInputText.addValidator(validator);
				htmlInputText.getAttributes().put("minValue", bizComponentConfDetail.getMinValue().intValue()+"");
				htmlInputText.getAttributes().put("maxValue", bizComponentConfDetail.getMaxValue().intValue()+"");
				
				if(entry.getKey().equalsIgnoreCase(DeclareConstants.EMAIL_COMPONENT_NAME)){
					htmlInputText.getAttributes().put("regexValue", DeclareConstants.REGEX_EMAIL);
				}else{
					htmlInputText.getAttributes().put("regexValue",
							collectionUtil.getPattern(
									bizComponentConfDetail.getIsNumeric().equals("Y"), 
									bizComponentConfDetail.getIsAlphabet().equals("Y"), 
									bizComponentConfDetail.getIsSpecial().equals("Y"))
							);
				}
				component = htmlInputText;
			}else if(component instanceof HtmlSelectOneMenu){
				
				htmlSelectOneMenu = (HtmlSelectOneMenu)component;
				htmlSelectOneMenu.setReadonly(bizComponentConfDetail.getIsReadOnly().equalsIgnoreCase("Y")?true:false);
				htmlSelectOneMenu.setDisabled(bizComponentConfDetail.getIsEnable().equalsIgnoreCase("Y")?false:true);
				htmlSelectOneMenu.setRequired(bizComponentConfDetail.getIsRequired().equalsIgnoreCase("Y")?true:false);
				component = htmlSelectOneMenu;
			}
		}
	}
	
	public Map<String, BizComponentConfDetail> getMapComponentBehavior() {
		return mapComponentBehavior;
	}

	public void setMapComponentBehavior(Map<String, BizComponentConfDetail> mapComponentBehavior) {
		this.mapComponentBehavior = mapComponentBehavior;
	}
}
