package com.amg.exchange.webservice;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amg.exchange.common.service.IGeneralService;

@Controller
@RequestMapping("/common/country")
public class CountryWSService<T> {
  
	@Autowired
	IGeneralService<T> generalService;
	
	protected final Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getAllCountryList(
			@RequestParam(value="params", required=true) String jsonString
			){
		Map<String, Object> map = new TreeMap<String, Object>();
		JSONObject json = new JSONObject();
		try{
			json = new JSONObject(jsonString);
			json = json.getJSONObject("input");
			Map<BigDecimal, String> mapObject = generalService.getAllCountry(new BigDecimal(json.getString("languageId")));
			map.put("data", mapObject );
			map.put("error", "0");
			map.put("total", mapObject.size());
		}catch(Exception e){
			logger.error("Component Behavior List JSON object conversion error ! "+e);
			map.put("error", "1");
		}
		 
		return map;
	}
}