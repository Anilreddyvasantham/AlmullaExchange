package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.treasury.dao.ISpecialCustomerDealRequestDao;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.HighValueDealRequestView;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;

@Service
public class SpecialCustomerDealRequestServiceImpl<T> implements ISpecialCustomerDealRequestService {

	
	
	@Autowired
	ISpecialCustomerDealRequestDao<T> specialCustomerDealRequestDao; 
	
	public ISpecialCustomerDealRequestDao<T> getSpecialCustomerDealRequestDao() {
		return specialCustomerDealRequestDao;
	}

	public void setSpecialCustomerDealRequestDao(
			ISpecialCustomerDealRequestDao<T> specialCustomerDealRequestDao) {
		this.specialCustomerDealRequestDao = specialCustomerDealRequestDao;
	}

	@Override
	@Transactional
	public String getNextDocumentSerialNumber(int countryId, int companyId, int documentId, int financialYear, String processIn) {
		return getSpecialCustomerDealRequestDao().getNextDocumentSerialNumber(countryId,companyId,documentId,financialYear,processIn);
	}

	@Override
	@Transactional
	public List<UserFinancialYear> getUserFinancialYear(Date currentDate) {
		return getSpecialCustomerDealRequestDao().getUserFinancialYear(currentDate);
	}


	@Override
	@Transactional
	public List<CustomerIdProof> dataCust(String id) {
		return getSpecialCustomerDealRequestDao().dataCust(id);
	}


	@Override
	@Transactional
	public void saveData(CustomerSpecialDealRequest customerSpecialDealRequest) {
		getSpecialCustomerDealRequestDao().saveData(customerSpecialDealRequest);
	}


	@Override
	@Transactional
	public BigDecimal getCustomerDetails(BigDecimal customerId) {
		return getSpecialCustomerDealRequestDao().getCustomerDetails(customerId);
	}

	@Override
	@Transactional
	public List<CustomerSpecialDealRequest> getDocumentSerialityNumberFromDB() {
		return getSpecialCustomerDealRequestDao().getDocumentSerialityNumberFromDB(); 
	}

	@Override
	@Transactional
	public List<Document> getDocumentDescription(BigDecimal id,BigDecimal languageId) {
		return getSpecialCustomerDealRequestDao().getDocumentDescription(id,languageId);
	}
	
	@Override
	@Transactional
	public List<CustomerSpecialDealRequest> getCustSpDocNo(BigDecimal docFinyearId,BigDecimal docNO,BigDecimal companyId,BigDecimal documentId){
		return getSpecialCustomerDealRequestDao().getCustSpDocNo(docFinyearId,docNO,companyId,documentId);
	}

	@Override
	@Transactional
	public List<Customer> getCustomerIdentity(BigDecimal custID) {
		return getSpecialCustomerDealRequestDao().getCustomerIdentity(custID);
	}
	@Override
	@Transactional
	public int getValidUpTo(String custType) {
		 return getSpecialCustomerDealRequestDao().getValidUpTo(custType);
	}

	@Override
	@Transactional
	public String getBankCountryNameForUpdate(BigDecimal bankCountryId,BigDecimal languageId) {
		return getSpecialCustomerDealRequestDao().getBankCountryNameForUpdate(bankCountryId,languageId);
	}

	@Override
	@Transactional
	public String getBankNameForUpdate(BigDecimal bankId) {
		return getSpecialCustomerDealRequestDao().getBankNameForUpdate(bankId);
	}

	@Override
	@Transactional
	public String getCurrencyForUpdate(BigDecimal currencyId) {
		return getSpecialCustomerDealRequestDao().getCurrencyForUpdate(currencyId);
	}

	@Override
	@Transactional
	public String getBankAccountNumberForUpdate(BigDecimal bankAccountId) {
		return getSpecialCustomerDealRequestDao().getBankAccountNumberForUpdate(bankAccountId);
	}

	@Override
	@Transactional
	public String getCivilIdForUpadate(BigDecimal customerId) {
		return getSpecialCustomerDealRequestDao().getCivilIdForUpadate(customerId);
	}

	@Override
	@Transactional
	public String getUserFinancialYearForUpadate(BigDecimal financialYearId) {
		return getSpecialCustomerDealRequestDao().getUserFinancialYearForUpadate(financialYearId);
	}

	@Override
	@Transactional
	public String getCompanyNameForUpdate(BigDecimal companyId) {
		return getSpecialCustomerDealRequestDao().getCompanyNameForUpdate(companyId);
	}

	@Override
	@Transactional
	public String getDocumentNameForUpdate(BigDecimal documentId) {
		return getSpecialCustomerDealRequestDao().getDocumentNameForUpdate(documentId);
	}

	@Override
	@Transactional
	public void updateFCAmount(BigDecimal customerSpecialDealReqId,
			BigDecimal fcAmountForUpdate,String userName) {
		getSpecialCustomerDealRequestDao().updateFCAmount(customerSpecialDealReqId,fcAmountForUpdate,userName);
	}


	@Override
	@Transactional
	public List<CompanyMasterDesc> getAllCompanyList(BigDecimal companyId,BigDecimal languageId) {
		
		return getSpecialCustomerDealRequestDao().getAllCompanyList(companyId,languageId);
	}

	@Override
	@Transactional
	public List<BankApplicability> getBankAccordingToBankCountry(BigDecimal countryId) {
		return getSpecialCustomerDealRequestDao().getBankAccordingToBankCountry(countryId);
	}

	@Override
	@Transactional
	public List<BankMaster> getBankListBasedOnCountry(BigDecimal countryId) {
		return getSpecialCustomerDealRequestDao().getBankListBasedOnCountry(countryId);
	}

	@Override
	@Transactional
	public List<BankApplicability> populateBankAccordingToBankCountry(List bankList) {
		return getSpecialCustomerDealRequestDao().populateBankAccordingToBankCountry(bankList);
	}
	
	@Override
	@Transactional
	public BigDecimal getDocumentPk(BigDecimal documentCode){
		return getSpecialCustomerDealRequestDao().getDocumentPk(documentCode);
	}
	
	@Override
	@Transactional
	public List<BankAccountDetails> getCurrencyBasedOnCountry(BigDecimal bankCountryId){
		return getSpecialCustomerDealRequestDao().getCurrencyBasedOnCountry(bankCountryId);
	}
	
	
	@Override
	@Transactional
	public List<CurrencyOtherInformation> getCurrencyDetails(BigDecimal currencyId){
		return getSpecialCustomerDealRequestDao().getCurrencyDetails(currencyId);
	}
	
	@Override
	@Transactional
	public List<HighValueDealRequestView> getHighValueDealRequestDetails(BigDecimal application,BigDecimal currencyId){
		return getSpecialCustomerDealRequestDao().getHighValueDealRequestDetails(application,currencyId);
	}
	
	
}
