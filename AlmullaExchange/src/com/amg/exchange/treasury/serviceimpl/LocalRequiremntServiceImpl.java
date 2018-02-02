package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.ILocalRequirementDao;
import com.amg.exchange.treasury.model.FundEstimation;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.treasury.service.ILocalRequirementService;

@Service("localrequirementService") 
public class LocalRequiremntServiceImpl<T> implements ILocalRequirementService<T> {
	
	@Autowired
	ILocalRequirementDao<T> localRequirementDao;

	@Override
	@Transactional 
	public List<FundEstimationDetails> getFundEstimationDetailsList(BigDecimal fundEstimationId) {
		return localRequirementDao.getFundEstimationDetailsList(fundEstimationId);
	}

	@Override
	@Transactional 
	public List<FundEstimation> getFundEstimationList(BigDecimal countryId) {
		return localRequirementDao.getFundEstimationList(countryId);
	}

	@Override
	@Transactional 
	public List<FundEstimation> getFundEstimationListBasedOnCurrency(
			BigDecimal bankCountry) {
		return localRequirementDao.getFundEstimationListBasedOnCurrency(bankCountry);
	}

	@Override
	@Transactional 
	public List<FundEstimationDetails> getFundEstimationDetailsListBasedOnCountry(BigDecimal countryId) {
		return localRequirementDao.getFundEstimationDetailsListBasedOnCountry(countryId);
	}
	

}
