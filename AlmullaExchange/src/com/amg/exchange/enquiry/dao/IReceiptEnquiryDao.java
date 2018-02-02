package com.amg.exchange.enquiry.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.enquiry.model.ViewFundSummaryDetails;
import com.amg.exchange.miscellaneous.model.ViewVwRemittanceTransaction;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.treasury.model.TreasuryDealHeader;

public interface IReceiptEnquiryDao {

	public List<ViewVwRemittanceTransaction> toFetchAllDetils(BigDecimal documentFinancialYear, BigDecimal documentNo);

	public List<CountryMasterDesc> getBankCounty(BigDecimal exchangeCountry,BigDecimal languageId);

	public List<ViewFundSummaryDetails> toFetchAllDetilsFromView(BigDecimal exchangeCountry, BigDecimal bankCountry, BigDecimal bankId, BigDecimal currencyId,Date projectionDate);

	public List<TreasuryDealHeader> toFetchAllBankFromTT(BigDecimal bankCountry);

	public BigDecimal toFetchFcAmount(BigDecimal treasureDealId,String lineType);

	public List<CountryMasterDesc> getBusinessCounty(BigDecimal languageId, BigDecimal countryId);

	public List<Employee> toFetchEmployeeDetils(BigDecimal countryId, BigDecimal locationId, BigDecimal employeeId);

	public List<CountryBranch> getAllBranchDetails(BigDecimal countryId, BigDecimal locationId);

	public void saveAllValues(BigDecimal employeePk, BigDecimal toLocationId, BigDecimal webServiceUserName, String webServicePassword, String toLocationIpAddress);
	
	public List<ViewVwRemittanceTransaction> getCustomerReceiptDetails(BigDecimal collectionDocumentYear, BigDecimal collectionDocumentNo);

}
