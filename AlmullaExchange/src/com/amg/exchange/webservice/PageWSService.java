package com.amg.exchange.webservice;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amg.exchange.common.service.IGeneralService;

@Controller
@RequestMapping("/common/page")
public class PageWSService<T> {
  
	@Autowired
	IGeneralService<T> generalService;
	
	protected final Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getAllCountryList(
			@RequestParam(value="pageName", required=true, defaultValue="1") String pageName
			){
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("data", generalService.getPageId(pageName));
		map.put("total", 2);
		
		return map;
	}
}