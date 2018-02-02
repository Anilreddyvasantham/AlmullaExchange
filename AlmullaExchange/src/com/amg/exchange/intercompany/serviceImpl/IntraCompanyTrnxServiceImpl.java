package com.amg.exchange.intercompany.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.intercompany.dao.IntraCompanyDao;
import com.amg.exchange.intercompany.model.IntraTrnxModel;
import com.amg.exchange.intercompany.service.IntraCompanyService;
import com.amg.exchange.util.AMGException;

@Service("intraCompanyService")
public class IntraCompanyTrnxServiceImpl implements IntraCompanyService{
	
	@Autowired
	IntraCompanyDao intraCompanyDao;

	@Transactional
	@Override
	public List<IntraTrnxModel> fetchIntraTrnxBasedonCountry(String countryName) {
		return intraCompanyDao.fetchIntraTrnxBasedonCountry(countryName);
	}

	@Transactional
	@Override
	public void saveRecords(HashMap<String, Object> saveRecords) throws AMGException {
		intraCompanyDao.saveRecords(saveRecords);
	}

}
