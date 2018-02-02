package com.amg.exchange.currency.inquiry.serviceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.currency.inquiry.dao.ICountrywiseFundRequirementHighvalueEnquiryDao;
import com.amg.exchange.currency.inquiry.service.ICountrywiseFundRequirementHighvalueEnquiryService;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.TreasuryDealHeader;

@Service("countrywiseFundRequirementHighvalueEnquiryServiceImpl")
public class CountrywiseFundRequirementHighvalueEnquiryServiceImpl implements ICountrywiseFundRequirementHighvalueEnquiryService {
	@Autowired
	ICountrywiseFundRequirementHighvalueEnquiryDao countrywiseFundRequirementHighvalueEnquiryDao;

	public ICountrywiseFundRequirementHighvalueEnquiryDao getCountrywiseFundRequirementHighvalueEnquiryDao() {
		return countrywiseFundRequirementHighvalueEnquiryDao;
	}

	public void setCountrywiseFundRequirementHighvalueEnquiryDao(ICountrywiseFundRequirementHighvalueEnquiryDao countrywiseFundRequirementHighvalueEnquiryDao) {
		this.countrywiseFundRequirementHighvalueEnquiryDao = countrywiseFundRequirementHighvalueEnquiryDao;
	}

	@Override
	@Transactional
	public List<CustomerSpecialDealRequest> getCustomerDealRequest(BigDecimal applicationCountryId,Date requestDate, BigDecimal bankCountryId,String option) {
		// TODO Auto-generated method stub
		return getCountrywiseFundRequirementHighvalueEnquiryDao().getCustomerDealRequest(applicationCountryId,requestDate,bankCountryId,option);
	}
	
	@Override
	@Transactional
	public BankMaster getBankDetails(BigDecimal bankId) {
		// TODO Auto-generated method stub
		return getCountrywiseFundRequirementHighvalueEnquiryDao().getBankDetails(bankId);
	}
	
	@Override
	@Transactional
	public CurrencyMaster getCurrencyDetails(BigDecimal currencyId) {
		// TODO Auto-generated method stub
		return getCountrywiseFundRequirementHighvalueEnquiryDao().getCurrencyDetails(currencyId);
	}
	
	@Override
	@Transactional
	public CountryMaster getCountryDetails(BigDecimal countryId) {
		// TODO Auto-generated method stub
		return getCountrywiseFundRequirementHighvalueEnquiryDao().getCountryDetails(countryId);
	}
	
	@Override
	@Transactional
	public List<RemittanceTransaction> getRemittanceDetails(BigDecimal applicationCountryId, BigDecimal bankId, BigDecimal bankCountryId, BigDecimal customerId) {
		// TODO Auto-generated method stub
		return getCountrywiseFundRequirementHighvalueEnquiryDao().getRemittanceDetails(applicationCountryId, bankId, bankCountryId, customerId);
	}
	
	@Override
	@Transactional
	public TreasuryDealHeader getTreasuryDealDetails(BigDecimal dealDocumentNo, BigDecimal DealDocumentFinanceYr) {
		// TODO Auto-generated method stub
		return getCountrywiseFundRequirementHighvalueEnquiryDao().getTreasuryDealDetails(dealDocumentNo, DealDocumentFinanceYr);
	}

}
