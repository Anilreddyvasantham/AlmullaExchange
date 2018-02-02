package com.amg.exchange.enquiry.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.enquiry.dao.IAverageRateBankORCurrencyWiseEnquiryDao;
import com.amg.exchange.enquiry.model.BankAccountDetailsView;
import com.amg.exchange.enquiry.service.IAverageRateBankORCurrencyWiseEnquiryService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;

@Service
public class AverageRateBankORCurrencyWiseEnquiryServiceImpl implements IAverageRateBankORCurrencyWiseEnquiryService{

	@Autowired
	IAverageRateBankORCurrencyWiseEnquiryDao averageRateBankORCurrencyWiseEnquiryDao;
	
	@Override
	@Transactional
	public List<BankAccountDetails> getallBanksFromBankAccountDetailsList(BigDecimal countryId,BigDecimal bankId) {
		return averageRateBankORCurrencyWiseEnquiryDao.getallBanksFromBankAccountDetailsList(countryId,bankId);
	}
	
	@Override
	@Transactional
	public List<BankAccountDetailsView> getBankAccountDeatailsView(BigDecimal bankId,BigDecimal currencyId){
		return averageRateBankORCurrencyWiseEnquiryDao.getBankAccountDeatailsView(bankId,currencyId);
	}
	
	@Override
	@Transactional
	public List<CustomerSpecialDealRequest> getCustomerRecords(BigDecimal bankID, BigDecimal currencnyId){
		return averageRateBankORCurrencyWiseEnquiryDao.getCustomerRecords(bankID,currencnyId);
	}

	@Override
	@Transactional
	public List<BankAccountDetailsView> getBankAccountDeatailsView(
			BigDecimal bankId, BigDecimal currencyId, BigDecimal countryId) {
 
		return averageRateBankORCurrencyWiseEnquiryDao.getBankAccountDeatailsView(bankId,currencyId,countryId);
	}


}
