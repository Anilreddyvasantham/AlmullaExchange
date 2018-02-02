package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.HighValueDealRequestView;

public interface ISpecialCustomerDealRequestService<T> {
public String getNextDocumentSerialNumber(int countryId,int companyId,int documentId,int financialYear,String processIn);
public List<UserFinancialYear> getUserFinancialYear(Date currentDate);
public List<CustomerIdProof> dataCust(String id);
public void saveData(CustomerSpecialDealRequest customerSpecialDealRequest);
public BigDecimal getCustomerDetails(BigDecimal customerId);
public List<CustomerSpecialDealRequest> getDocumentSerialityNumberFromDB();
public List<Document> getDocumentDescription(BigDecimal id,BigDecimal languageId);
public int getValidUpTo(String custType);
public List<Customer> getCustomerIdentity(BigDecimal custID);
public List<CustomerSpecialDealRequest> getCustSpDocNo(BigDecimal docFinyearId,BigDecimal docNO,BigDecimal companyId,BigDecimal documentId);
public void updateFCAmount(BigDecimal customerSpecialDealReqId,BigDecimal fcAmountForUpdate,String userName);
public List<BankApplicability> getBankAccordingToBankCountry(BigDecimal countryId);


//Getting values for Update
public String getBankCountryNameForUpdate(BigDecimal bankCountryId,BigDecimal languageId);
public String getBankNameForUpdate(BigDecimal bankId);
public String getCurrencyForUpdate(BigDecimal currencyId);
public String getBankAccountNumberForUpdate(BigDecimal bankAccountId);
public String getCivilIdForUpadate(BigDecimal customerId);
public String getUserFinancialYearForUpadate(BigDecimal financialYearId);
public String getCompanyNameForUpdate(BigDecimal companyId);
public String getDocumentNameForUpdate(BigDecimal documentId);
public List<CompanyMasterDesc> getAllCompanyList(BigDecimal companyId,BigDecimal languageId);


//For CR
public List<BankMaster> getBankListBasedOnCountry(BigDecimal countryId);
public List<BankApplicability> populateBankAccordingToBankCountry(List<BankMaster> bankList);

public BigDecimal getDocumentPk(BigDecimal documentCode);

public List<BankAccountDetails> getCurrencyBasedOnCountry(BigDecimal bankCountryId);

public List<CurrencyOtherInformation> getCurrencyDetails(BigDecimal currencyId);





//for high value deal request enquiry

public List<HighValueDealRequestView> getHighValueDealRequestDetails(BigDecimal application,BigDecimal currencyId);

}
