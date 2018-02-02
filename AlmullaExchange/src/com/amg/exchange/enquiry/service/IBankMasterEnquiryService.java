package com.amg.exchange.enquiry.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankContactDetails;
import com.amg.exchange.treasury.model.BankIndicatorDescription;

public interface IBankMasterEnquiryService {

	public List<BankIndicatorDescription> getAllBankIndicators(BigDecimal languageId);
	public List<BankAccountLength> getAllBankLengthRecords(BigDecimal bankId);
	public List<BankContactDetails> getAllBankContactDetailsRecords(BigDecimal bankId);
	public List<BankAccountDetails> getAllBankAccountDetailsRecords(BigDecimal bankId);
	public String getZoneName(BigDecimal zoneId,BigDecimal languageId);
	public String getBankIndicatorName(BigDecimal bankIndId,BigDecimal languageId);
	public List<BankApplicability> getBankMasterDetails(BigDecimal bankCountryId,BigDecimal bankIndId);
	public String getBankAccountTypeName(BigDecimal accounTypeId,BigDecimal languageId);
}
