package com.amg.exchange.intercompany.dao;

import java.util.HashMap;
import java.util.List;

import com.amg.exchange.intercompany.model.IntraTrnxModel;
import com.amg.exchange.util.AMGException;

public interface IntraCompanyDao {
	
	public List<IntraTrnxModel> fetchIntraTrnxBasedonCountry(String countryName);
	
	public void saveRecords(HashMap<String, Object> saveRecords)throws AMGException;

}
