package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.treasury.model.FundEstimation;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.treasury.viewModel.TotalUsdRequirementView;

public interface ITotalUsdRequirementService<T> {
	
	
	public List<FundEstimationDetails> getSaleProjectionValues(BigDecimal countryId,String roleId,Date projectionDate);
	public List<FundEstimation> getTotalUsd(BigDecimal countryId,String roleId,Date projectionDate);
	
	/*
	 * This Method is added for Treasury – Sales Projections CR Point -9 begin
	 */
	
	
	public List <TotalUsdRequirementView>getSaleProjectionValuesChief();
	

	/*
	 * This Method is added for Treasury – Sales Projections CR Point -9 End
	 */
	

}

