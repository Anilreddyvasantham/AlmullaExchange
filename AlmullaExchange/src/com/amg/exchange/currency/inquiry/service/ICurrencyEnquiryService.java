package com.amg.exchange.currency.inquiry.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.common.dto.CurrencyMasterDTO;
import com.amg.exchange.currency.inquiry.model.BranchDayTransactionView;
import com.amg.exchange.currency.inquiry.model.BranchWiseCurrencyStock;
import com.amg.exchange.currency.inquiry.model.UserStockView;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.treasury.model.CurrencyMaster;

public interface ICurrencyEnquiryService {

	public List<Employee> getAllCashierList(BigDecimal branchId);

	public List<CurrencyMasterDTO> getCurrencyListByUser(BigDecimal branchId, String userName);

	public List<UserStockView> getDenominationListByUserCurrency(BigDecimal branchId, String userName, BigDecimal currencyId);

	public List<CurrencyMaster> getCurrencyList();

	public List<BranchWiseCurrencyStock> getBrachWiseCurrencyStockList(BigDecimal countryBranchId, BigDecimal currencyId, BigDecimal denomId);

	public List<CountryBranch> getCountryBranchByCurrency();

	public List<CurrencyWiseDenomination> getDenomonationByCurrency(BigDecimal currencyId);
	
	public List<BranchDayTransactionView> getBranchDayTransaction(HashMap<String, Object> lstbranchDayTransactionInput);
	
	public List<CollectDetail> getBranchDayCollectionDetail(BigDecimal collectionDocNo, BigDecimal collectionDocCode, BigDecimal collectionDocfinanceYear) ;
	
	public List<Employee> getCashierList(BigDecimal branchId,BigDecimal roleId);
	
	public List<CountryBranch> getBranchDetails(BigDecimal countryId,BigDecimal countryBranchId);

}
