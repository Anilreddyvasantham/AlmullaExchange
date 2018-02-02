package com.amg.exchange.enquiry.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.enquiry.dao.IReceiptEnquiryDao;
import com.amg.exchange.enquiry.model.ViewFundSummaryDetails;
import com.amg.exchange.enquiry.service.IReceiptEnquiryService;
import com.amg.exchange.miscellaneous.model.ViewVwRemittanceTransaction;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.treasury.model.TreasuryDealHeader;

@Service("receiptEnquiryServiceImpl")
public class ReceiptEnquiryServiceImpl implements IReceiptEnquiryService {

	@Autowired
	IReceiptEnquiryDao receiptEnquiryDao;

	@Override
	@Transactional
	public List<ViewVwRemittanceTransaction> toFetchAllDetils(BigDecimal documentFinancialYear, BigDecimal documentNo) {
		return receiptEnquiryDao.toFetchAllDetils(documentFinancialYear,documentNo);
	}

	@Override
	@Transactional
	public List<CountryMasterDesc> getBankCounty(BigDecimal exchangeCountry,BigDecimal languageId) {
		return receiptEnquiryDao.getBankCounty(exchangeCountry,languageId);
	}

	@Override
	@Transactional
	public List<ViewFundSummaryDetails> toFetchAllDetilsFromView(BigDecimal exchangeCountry, BigDecimal bankCountry, BigDecimal bankId, BigDecimal currencyId,Date projectionDate) {
		return receiptEnquiryDao.toFetchAllDetilsFromView(exchangeCountry,bankCountry,bankId,currencyId,projectionDate);
	}

	@Override
	@Transactional
	public List<TreasuryDealHeader> toFetchAllBankFromTT(BigDecimal bankCountry) {
		return receiptEnquiryDao.toFetchAllBankFromTT(bankCountry);
	}

	@Override
	@Transactional
	public BigDecimal toFetchFcAmount(BigDecimal treasureDealId,String lineType) {
		return receiptEnquiryDao.toFetchFcAmount(treasureDealId,lineType);
	}

	@Override
	@Transactional
	public List<CountryMasterDesc> getBusinessCounty(BigDecimal languageId, BigDecimal countryId) {
		return receiptEnquiryDao.getBusinessCounty(languageId,countryId);
	}

	@Override
	@Transactional
	public List<Employee> toFetchEmployeeDetils(BigDecimal countryId, BigDecimal locationId, BigDecimal employeeId) {
		return receiptEnquiryDao.toFetchEmployeeDetils(countryId,locationId,employeeId);
	}

	@Override
	@Transactional
	public List<CountryBranch> getAllBranchDetails(BigDecimal countryId, BigDecimal locationId) {
		return receiptEnquiryDao.getAllBranchDetails(countryId,locationId);
	}

	@Override
	@Transactional
	public void saveAllValues(BigDecimal employeePk, BigDecimal toLocationId, BigDecimal webServiceUserName, String webServicePassword, String toLocationIpAddress) {
		receiptEnquiryDao.saveAllValues(employeePk,toLocationId,webServiceUserName,webServicePassword,toLocationIpAddress);
	}

	@Override
	@Transactional
	public List<ViewVwRemittanceTransaction> getCustomerReceiptDetails(
			BigDecimal collectionDocumentYear, BigDecimal collectionDocumentNo) {
		return receiptEnquiryDao.getCustomerReceiptDetails(collectionDocumentYear, collectionDocumentNo);
	}


}
