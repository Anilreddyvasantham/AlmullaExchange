/**
 * 
 */
package com.amg.exchange.currency.inquiry.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.TreasuryDealHeader;

/**
 * @author Subramaniam
 * 
 */
public interface ICountrywiseFundRequirementHighvalueEnquiryDao {

	public List<CustomerSpecialDealRequest> getCustomerDealRequest(BigDecimal applicationCountryId, Date requestDate, BigDecimal bankCountryId,String option);

	public List<RemittanceTransaction> getRemittanceDetails(BigDecimal applicationCountryId, BigDecimal bankId, BigDecimal bankCountryId, BigDecimal customerId);

	public BankMaster getBankDetails(BigDecimal bankId);

	public CurrencyMaster getCurrencyDetails(BigDecimal currencyId);

	public CountryMaster getCountryDetails(BigDecimal countryId);
	
	public TreasuryDealHeader getTreasuryDealDetails(BigDecimal dealDocumentNo, BigDecimal DealDocumentFinanceYr);

}
