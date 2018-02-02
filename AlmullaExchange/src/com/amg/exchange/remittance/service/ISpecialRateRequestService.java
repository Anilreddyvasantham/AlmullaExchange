package com.amg.exchange.remittance.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;

public interface ISpecialRateRequestService {
	
	public List<SpecialRateRequest> getSpecialRateRequestList();

	public void updateRecord(SpecialRateRequest specialRateRequest);

	public Boolean isSpotRate(BigDecimal documentNo);

	public List<SpecialRateRequest> toFetchAllDetialsFromSpecialRateReq(BigDecimal customerId, BigDecimal fcAmount, String createdDate, BigDecimal beneficiaryId, BigDecimal beneficiaryBankId);

	public List<SpecialRateRequest> toFetchSpotRate(BigDecimal customerId, BigDecimal fcAmount, String createdDate, BigDecimal beneficiaryId, BigDecimal beneficiaryBankId);

	public List<SpecialRateRequest> getActiveSpecialRateRequest(BigDecimal customerId, BigDecimal fcAmount, String createdDate, BigDecimal beneficiaryId, BigDecimal beneficiaryBankId);

	public List<CurrencyOtherInformation> getMinMaxRate(BigDecimal currencyId);
	
	public void updateSpecialRateRequest(BigDecimal SpecialRatePk,BigDecimal  fcAmount);
	
	public List<SpecialRateRequest>  fetchSpotRateRecords(BigDecimal customerId,  String createdDate, BigDecimal beneficiaryId, BigDecimal beneficiaryBankId);
	
	public List<SpecialRateRequest> fetchAllDetailsFromCustomerAndBranch(BigDecimal customerReferenceNo,BigDecimal branchId);

}
