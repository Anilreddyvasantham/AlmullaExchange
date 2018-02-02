package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.FundEstimationDetailsBeanDao;
import com.amg.exchange.treasury.model.FundEstimationDays;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.treasury.service.FundEstimationDetailsBeanService;
import com.amg.exchange.util.AMGException;

@Service("FundEstimationDetailsBeanService")
public class FundEstimationDetailsBeanServiceImpl implements FundEstimationDetailsBeanService{
	
	@Autowired
	FundEstimationDetailsBeanDao fundEstimationDetailsBeanDao;
	
	@Override
	@Transactional
	public List<FundEstimationDetails> getData(BigDecimal countryId) {
		return fundEstimationDetailsBeanDao.getData(countryId);
	}

	@Override
	@Transactional
	public String getCountryName(BigDecimal countryId) {
		return fundEstimationDetailsBeanDao.getCountryName(countryId);
	}

	@Override
	@Transactional
	public int getDecimalValue(BigDecimal currencyId) {
		return fundEstimationDetailsBeanDao.getDecimalValue(currencyId);
	}
	
	@Override
	@Transactional
	public BigDecimal getUsdPk(String currencyCode){
		return fundEstimationDetailsBeanDao.getUsdPk(currencyCode);
	}

	@Override
	@Transactional
	public HashMap<String, String> callFundEstimation(
			HashMap<String, String> inputValues) throws AMGException {
		
		return fundEstimationDetailsBeanDao.callFundEstimation(inputValues);
	}

	@Override
	@Transactional
	public List<FundEstimationDays> getFundEstimationDyas(
			HashMap<String, String> inputValues) throws AMGException {
		// TODO Auto-generated method stub
		return fundEstimationDetailsBeanDao.getFundEstimationDyas(inputValues);
	}

}
