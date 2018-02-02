package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amg.exchange.treasury.dao.ITotalUsdRequirementDao;
import com.amg.exchange.treasury.model.FundEstimation;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.treasury.service.ITotalUsdRequirementService;
import com.amg.exchange.treasury.viewModel.TotalUsdRequirementView;

@Service
public class TotalUsdRequirementServiceImpl<T> implements ITotalUsdRequirementService<T> {
	
	@Autowired
	ITotalUsdRequirementDao<T> totalUsdRequirementDao;
	
	
	
	@Override
	@Transactional
	public List<FundEstimationDetails> getSaleProjectionValues(BigDecimal countryId,String roleId,Date projectionDate) {
		
		return totalUsdRequirementDao.getSaleProjectionValues(countryId, roleId,projectionDate);
	}

	@Override
	@Transactional
	public List<FundEstimation> getTotalUsd(BigDecimal countryId, String roleId,Date projectionDate) {
		// TODO Auto-generated method stub
		return totalUsdRequirementDao.getTotalUsd(countryId, roleId,projectionDate);
	}

	
	/*
	 * This Method is added for Treasury – Sales Projections CR Point -9 begin
	 */
	
	
	@Override
	@Transactional
	public List<TotalUsdRequirementView> getSaleProjectionValuesChief() {
		
		return totalUsdRequirementDao.getSaleProjectionValuesChief();
	}

	/*
	 * Above Method is added for Treasury – Sales Projections CR Point -9 End
	 */
	
	

	
}
