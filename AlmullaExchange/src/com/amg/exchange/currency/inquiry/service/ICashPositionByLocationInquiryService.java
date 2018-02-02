package com.amg.exchange.currency.inquiry.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.currency.inquiry.model.CashDetailsView;
import com.amg.exchange.currency.inquiry.model.CashPositionByLocationView;
import com.amg.exchange.registration.model.CountryBranch;

public interface ICashPositionByLocationInquiryService {
	
	public List<CountryBranch> getCountryBranchList(BigDecimal appCountryId);
	
	public List<CashPositionByLocationView> getCashBalanceList(BigDecimal branchId);
	
	public List<CashPositionByLocationView> getCashBalanceBasedOnCtoBList(BigDecimal branchId,BigDecimal currencyId);
	
	public List<CashDetailsView> getCashBalanceBasedOnCtoBandCashierList(BigDecimal branchId,BigDecimal currencyId,String createdBy);
	
	public List<CashDetailsView> getCashBalanceBasedOnCtoBandCashierandModeList(BigDecimal branchId,BigDecimal currencyId,String createdBy,String mode);

}
