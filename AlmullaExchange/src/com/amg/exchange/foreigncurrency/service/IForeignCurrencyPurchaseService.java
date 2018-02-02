package com.amg.exchange.foreigncurrency.service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyAdjustView;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjustApp;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyPurchaseReport;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.ReceiptPaymentApp;
import com.amg.exchange.foreigncurrency.model.ReceiptPaymentView;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.remittance.model.CashRate;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.AMGException;

public interface IForeignCurrencyPurchaseService<T> {
	
	public List<CurrencyMaster> getAllCurrency(BigDecimal contyId);

	public List<CurrencyWiseDenomination> getDenominationByCurrencyID(BigDecimal currencyId);

	public List<SourceOfIncome> getAllSourceOfIncome();

	public List<PurposeOfTransaction> getAllPurposeOfTransaction();

	public List<CustomerIdProof> dataCust(String id);

	public void save(T entity) throws AMGException;

	public void saveOrUpdate(T entity) throws AMGException;

	public void delete(T entity);

	public void saveCollect(Collect collect) throws AMGException;

	// Forget User Financial Year Table
	public List<UserFinancialYear> getUserFinancialYear(Date currentDate);

	public List<CurrencyMaster> getCurrencyById(BigDecimal currencyId);

	public List<String> getFsAvg(Object obj, BigDecimal currencyId);

	//public String getNextDocumentSerialNumber(int countryId, int companyId, int documentId, int financialYear, String processIn);

	// Rolewise exchange rate by subramanian 04-Dec-2014
	public List<CashRate> getRoleWiseExchangeRateByRoleId(BigDecimal locationId,BigDecimal saleCurrencyId);

	public List<String> getBetweenRolewiseExchangeRate(Object obj, BigDecimal roleId);

	public String getSignature(BigDecimal id);

	public List<CurrencyWiseDenomination> getDenominationByCountryIDCurrencyID(BigDecimal countryId, BigDecimal currencyId);

	// fetch all reocds from foreign currency adjust app Table for
	// ReportGeneration
	public List<ForeignCurrencyAdjustApp> getAllValuesForReportGenaration(BigDecimal customerIdNumber, String documentId, BigDecimal docYear);

	// fetch all reocds from receipt payment app Table for ReportGeneration
	public List<ReceiptPaymentApp> getReceiptPaymentForReportGeneration(BigDecimal documentNo);

	public BigDecimal getLocalCurrencyId(BigDecimal countryId);

	public List<ForeignCurrencyPurchaseReport> getFcPurchaseReportList(BigDecimal appcountryId, BigDecimal companyId, BigDecimal documentcode, BigDecimal financialYr, BigDecimal documentNo);

	public List<CollectDetail> getCustomerEnquiry(BigDecimal documentYear, BigDecimal documentNo);

	public List<UserFinancialYear> getAllDocumentYear();

	public List<Customer> getCustomerAllDetails(BigDecimal customerId);

	// added 29/05/15
	public List<ReceiptPayment> getReceipetData(BigDecimal documentFinanceYear, BigDecimal documentNo);

	public List<ReceiptPaymentApp> getReceipetDataApp(BigDecimal documentFinanceYear, BigDecimal documentNo);
	
	
	
	public List<ForeignCurrencyAdjustApp> getCurrencyAdjustRecords(BigDecimal documentFinanceYear, BigDecimal documentNo);

	public List<SourceOfIncomeDescription> getSourceofIncome(BigDecimal languageId);

	public List<CollectDetail> getFCPurchageCollectionDetails(String dOCUMENT_CODE_FOR_FCPURCHAGE, BigDecimal documentFinanceYear, BigDecimal documentNo);

	public List<ForeignCurrencyAdjust> getforeignCurrencyAdjustList(BigDecimal collectionId);

	public String getsourceofIncomeDesc(BigDecimal languageId, BigDecimal sourceId);

	public String getPurposeofTransaction(BigDecimal purposeId);

	public BigDecimal getcountrybasedonCurrency(BigDecimal currencyId);

	public String getInsertEmosFcPurchase(BigDecimal appcountryId, BigDecimal companyId, BigDecimal documentId, BigDecimal financialYr, BigDecimal documentNo) throws AMGException ;

	//Added by Rabil 	
	public BigDecimal saveCollectCollecDetailCurrAdjustAndFinalReceipt(Collect collection, List<CollectDetail> collectionDetailList,List<ForeignCurrencyAdjust> foreignCurrencyList,ReceiptPayment receiptPaymnet, List<Payment> payment) throws Exception; 

	public List<PaymentMode> getPaymentModeDetails(String paymentCode);
	
	public List<ReceiptPaymentView> getReceiptPaymentView(BigDecimal documentNo,BigDecimal documentYear,BigDecimal companyId,BigDecimal DocumentCode);
	
	public List<CurrencyAdjustView> getCurrencyAdjustViewRecords(BigDecimal documentNo,BigDecimal documentYear,BigDecimal companyId,BigDecimal DocumentCode);
	
	public BigDecimal getRoundedSaleAmountByFunc(BigDecimal tsaleAmount);
	
	public List<CustomerIdProof> fetchCustomerByIdentityInt(String idno);
	//Added by Anil
	public String getBranchName(BigDecimal countryBranchId);
	
}
