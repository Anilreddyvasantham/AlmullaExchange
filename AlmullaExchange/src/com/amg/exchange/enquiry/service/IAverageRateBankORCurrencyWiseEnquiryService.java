package com.amg.exchange.enquiry.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.enquiry.model.BankAccountDetailsView;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;

public interface IAverageRateBankORCurrencyWiseEnquiryService {

	public List<BankAccountDetails> getallBanksFromBankAccountDetailsList(BigDecimal countryId,BigDecimal bankId);
	
	public List<BankAccountDetailsView> getBankAccountDeatailsView(BigDecimal bankId,BigDecimal currencyId);
	
	public List<BankAccountDetailsView> getBankAccountDeatailsView(BigDecimal bankId,BigDecimal currencyId,BigDecimal countryId);
	
	public List<CustomerSpecialDealRequest> getCustomerRecords(BigDecimal bankID, BigDecimal currencnyId);
}
