package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.FundEstimation;
import com.amg.exchange.treasury.model.FundEstimationDetails;

public interface ILocalRequirementService<T> {
	public List<FundEstimationDetails> getFundEstimationDetailsList(BigDecimal fundEstimationId);
	public List<FundEstimationDetails> getFundEstimationDetailsListBasedOnCountry(BigDecimal countryId);
	public List<FundEstimation> getFundEstimationList(BigDecimal countryId);
	public List<FundEstimation> getFundEstimationListBasedOnCurrency(BigDecimal bankCountry);

}
