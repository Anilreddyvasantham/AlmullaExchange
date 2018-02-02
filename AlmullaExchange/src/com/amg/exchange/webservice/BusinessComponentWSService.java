package com.amg.exchange.webservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amg.exchange.common.model.BizComponentConfDetail;
import com.amg.exchange.common.service.IGeneralService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/common/business-component")
public class BusinessComponentWSService<T> {

	protected final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	IGeneralService<T> generalService;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/behavior/list", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getComponentBehaviorList(
			@RequestParam(value="params", required=true) String jsonInput
			){
		JSONObject json = new JSONObject();
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, BizComponentConfDetail> returnObject = null;
		Map<String, Map<String, Object>> jsonOutput = new HashMap<String,Map<String, Object>>();
		Map<String, Object> jsonOutputSub = new HashMap<String,Object>();
		Map<String, Object> map = new TreeMap<String, Object>();
		
		try{
			json = new JSONObject(jsonInput);
			json = json.getJSONObject("input");
			List<String> componentNames = objectMapper.readValue(json.getJSONArray("componentList").toString(),
					List.class
					);
			returnObject = generalService.getComponentBehavior(
													componentNames, 
												 	new BigDecimal(json.getString("levelId")) , 
												 	new BigDecimal(json.getString("applicationId")), 
									 			 	new BigDecimal(json.getString("companyId")), 
												 	new BigDecimal(json.getString("countryId")), 
												 	new BigDecimal(json.getString("pageId")));
			for(Entry<String, BizComponentConfDetail> entry:returnObject.entrySet()){

				jsonOutputSub = new HashMap<String,Object>();
				jsonOutputSub.put("IS_NUMERIC", entry.getValue().getIsNumeric());
				jsonOutputSub.put("IS_ALPHABET", entry.getValue().getIsAlphabet());
				jsonOutputSub.put("IS_SPECIAL", entry.getValue().getIsSpecial());
				jsonOutputSub.put("IS_REQUIRED", entry.getValue().getIsRequired());
				jsonOutputSub.put("IS_READ_ONLY", entry.getValue().getIsReadOnly());
				jsonOutputSub.put("IS_ENABLE", entry.getValue().getIsEnable());
				jsonOutputSub.put("IS_VISIBLE", entry.getValue().getIsVisible());
				jsonOutputSub.put("MIN_VALUE", entry.getValue().getMinValue().toPlainString());
				jsonOutputSub.put("MAX_VALUE", entry.getValue().getMaxValue().toPlainString());
				jsonOutputSub.put("CONF_ID", entry.getValue().getFsBusinessComponentConf().getComponentConfId().toPlainString());
				jsonOutputSub.put("LEVEL_ID", entry.getValue().getFsBusinessComponentConf().getLevelId().toPlainString());
				jsonOutputSub.put("IS_MULTIPLE", entry.getValue().getFsBusinessComponentConf().getFsBusinessComponent().getIsMultiple());
				jsonOutputSub.put("COMPONENT_ID", entry.getValue().getFsBusinessComponentConf().getFsBusinessComponent().getComponentId().toPlainString());
				
				//For getting drop down
				if(entry.getValue().getFsBusinessComponentConf().getFsBusinessComponent().getIsMultiple().equals("Y")){
					try{
						Map<BigDecimal, String> componentData = generalService.getAllComponentComboData(entry.getValue().getFsBusinessComponentConf().getComponentConfId(), new BigDecimal(json.getString("languageId")));
						jsonOutputSub.put("COMPONENT_DATA", componentData);
					}catch(Exception e){
						logger.info("Drop down data not found for component -> "+entry.getKey()+" :: "+e);
					}
				}
				jsonOutput.put(entry.getKey(), jsonOutputSub);
				map.put("error", "0"); 
			}
		}catch(Exception e){
			logger.error("Component Behavior List JSON object conversion error ! "+e);
			map.put("error", "1");
		}
		map.put("data", jsonOutput);
		map.put("total", jsonOutput.size());
		
		return map;
	}
}
