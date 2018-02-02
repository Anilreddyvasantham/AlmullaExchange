package com.amg.exchange.currency.inquiry.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.currency.inquiry.dao.ICashPositionByLocationInquiryDao;
import com.amg.exchange.currency.inquiry.model.CashDetailsView;
import com.amg.exchange.currency.inquiry.model.CashPositionByLocationView;
import com.amg.exchange.currency.inquiry.service.ICashPositionByLocationInquiryService;
import com.amg.exchange.registration.model.CountryBranch;
@Service("cashPositionByLocationInquiryServiceImpl")
public class CashPositionByLocationInquiryServiceImpl implements  ICashPositionByLocationInquiryService {
	
	@Autowired 
	ICashPositionByLocationInquiryDao cashPositionByLocationInquiryDao;
	
	
	
	public ICashPositionByLocationInquiryDao getCashPositionByLocationInquiryDao() {
		return cashPositionByLocationInquiryDao;
	}



	public void setCashPositionByLocationInquiryDao(ICashPositionByLocationInquiryDao cashPositionByLocationInquiryDao) {
		this.cashPositionByLocationInquiryDao = cashPositionByLocationInquiryDao;
	}


	@Override
	@Transactional
	public List<CountryBranch> getCountryBranchList(BigDecimal appCountryId){
		return getCashPositionByLocationInquiryDao().getCountryBranchList(appCountryId);
	}
	@Override
	@Transactional
	public List<CashPositionByLocationView> getCashBalanceList(BigDecimal branchId){
		return getCashPositionByLocationInquiryDao().getCashBalanceList(branchId);
	}
	@Override
	@Transactional
	public List<CashPositionByLocationView> getCashBalanceBasedOnCtoBList(BigDecimal branchId,BigDecimal currencyId){
		return getCashPositionByLocationInquiryDao().getCashBalanceBasedOnCtoBList(branchId, currencyId);
	}
	@Override
	@Transactional
	public List<CashDetailsView> getCashBalanceBasedOnCtoBandCashierList(BigDecimal branchId,BigDecimal currencyId,String createdBy){
		return getCashPositionByLocationInquiryDao().getCashBalanceBasedOnCtoBandCashierList(branchId, currencyId,createdBy);
	}
	@Override
	@Transactional
	public List<CashDetailsView> getCashBalanceBasedOnCtoBandCashierandModeList(BigDecimal branchId,BigDecimal currencyId,String createdBy,String mode){
		return getCashPositionByLocationInquiryDao().getCashBalanceBasedOnCtoBandCashierandModeList(branchId, currencyId,createdBy,mode);
		
	}

}
