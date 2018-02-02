package com.amg.exchange.currency.inquiry.serviceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dto.CurrencyMasterDTO;
import com.amg.exchange.currency.inquiry.dao.ICurrencyEnquiryDao;
import com.amg.exchange.currency.inquiry.model.BranchDayTransactionView;
import com.amg.exchange.currency.inquiry.model.BranchWiseCurrencyStock;
import com.amg.exchange.currency.inquiry.model.UserStockView;
import com.amg.exchange.currency.inquiry.service.ICurrencyEnquiryService;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.treasury.model.CurrencyMaster;

@Service("currencyEnquiryService")
public class CurrencyEnquiryServiceImpl implements ICurrencyEnquiryService {

	@Autowired
	ICurrencyEnquiryDao currencyEnquiryDao;

	public ICurrencyEnquiryDao getCurrencyEnquiryDao() {
		return currencyEnquiryDao;
	}

	public void setCurrencyEnquiryDao(ICurrencyEnquiryDao currencyEnquiryDao) {
		this.currencyEnquiryDao = currencyEnquiryDao;
	}

	@Override
	@Transactional
	public List<Employee> getAllCashierList(BigDecimal branchId) {
		return getCurrencyEnquiryDao().getAllCashierList(branchId);
	}

	@Override
	@Transactional
	public List<CurrencyMasterDTO> getCurrencyListByUser(BigDecimal branchId, String userName) {
		return getCurrencyEnquiryDao().getCurrencyListByUser(branchId, userName);
	}

	@Override
	@Transactional
	public List<UserStockView> getDenominationListByUserCurrency(BigDecimal branchId, String userName, BigDecimal currencyId) {
		return getCurrencyEnquiryDao().getDenominationListByUserCurrency(branchId, userName, currencyId);
	}

	@Override
	@Transactional
	public List<CurrencyMaster> getCurrencyList() {
		return getCurrencyEnquiryDao().getCurrencyList();
	}

	@Override
	@Transactional
	public List<BranchWiseCurrencyStock> getBrachWiseCurrencyStockList(BigDecimal countryBranchId, BigDecimal currencyId, BigDecimal denomId) {
		return getCurrencyEnquiryDao().getBrachWiseCurrencyStockList(countryBranchId, currencyId, denomId);
	}

	@Override
	@Transactional
	public List<CountryBranch> getCountryBranchByCurrency() {
		return getCurrencyEnquiryDao().getCountryBranchByCurrency();
	}

	@Override
	@Transactional
	public List<CurrencyWiseDenomination> getDenomonationByCurrency(BigDecimal currencyId) {
		return getCurrencyEnquiryDao().getDenomonationByCurrency(currencyId);
	}

	@Override
	@Transactional
	public List<BranchDayTransactionView> getBranchDayTransaction(HashMap<String, Object> lstbranchDayTransactionInput){
		return getCurrencyEnquiryDao().getBranchDayTransaction(lstbranchDayTransactionInput);
	}
	@Override
	@Transactional
	public List<CollectDetail> getBranchDayCollectionDetail(BigDecimal collectionDocNo, BigDecimal collectionDocCode, BigDecimal collectionDocfinanceYear) {
		return getCurrencyEnquiryDao().getBranchDayCollectionDetail(collectionDocNo, collectionDocCode, collectionDocfinanceYear);
	}
	
	@Override
	@Transactional
	public List<Employee> getCashierList(BigDecimal branchId, BigDecimal roleId) {
		return getCurrencyEnquiryDao().getCashierList(branchId, roleId);
	}
	
	@Override
	@Transactional
	public List<CountryBranch> getBranchDetails(BigDecimal countryId, BigDecimal countryBranchId) {
		
		return getCurrencyEnquiryDao().getBranchDetails(countryId,countryBranchId);
	}
		
}
