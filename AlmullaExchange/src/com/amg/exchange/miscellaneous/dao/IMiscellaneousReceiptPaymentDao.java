package com.amg.exchange.miscellaneous.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.cancelreissue.model.RemittanceTrnxViewStopMiscModel;
import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.ReceiptPaymentApp;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.model.ViewReceiptPayment;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.miscellaneous.model.PaymentDetail;
import com.amg.exchange.miscellaneous.model.ViewVwRemittanceTransaction;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerInfoView;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;

public interface IMiscellaneousReceiptPaymentDao<T> {
	
	public List<ViewVwRemittanceTransaction> fetchTransactionalDetails(BigDecimal appCountryId,BigDecimal companyId, BigDecimal documentCode,BigDecimal remittanceYear,BigDecimal remiitanceNo);
	public List<ViewRemiitanceInfo> fetchTransactionalDetail(
			BigDecimal appCountryId, BigDecimal companyId,
			BigDecimal documentCode, BigDecimal remittanceYear,
			BigDecimal remiitanceNo);
	public List<ReceiptPayment> fetchReceiptPayment(BigDecimal appCountryId,BigDecimal companyId,BigDecimal documentYear,BigDecimal documentNo,String documentCode);
	public List<ReceiptPayment> fetchReceiptPaymentForUpdate(BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,BigDecimal DocumentYear, BigDecimal DocumentNo);
	
	public List<ReceiptPayment> fetchReceiptPaymentUnApprovedRecords(BigDecimal appCountryId, BigDecimal companyId,	BigDecimal documentCode, BigDecimal DocumentYear);
	public String getTelephoneNumber(BigDecimal customerRef);
	public List<Customer> fetchCustomer(BigDecimal  customerId);
	public List<ViewVwRemittanceTransaction> getAllTransactionList(BigDecimal customerReference) ;
	public List<ViewRemiitanceInfo> getAllTransactionsList(
			BigDecimal customerReference);
	public List<ForeignCurrencyAdjust> getCollectionDetailsFromCurrencyAdjust(BigDecimal collectiondocId, BigDecimal collectDocYear,BigDecimal collectDocNo);
	public  String getDenominationName(BigDecimal denominationId);
	public void saveOrUpdate(ReceiptPayment receiptPaymentObj);
	public BigDecimal getBankBranchIdBasedOnName(String branchName);
	public BigDecimal getSourceIdBasedOnName(String sourceName,BigDecimal languageId);
	public void saveOrUpdateApplReceiptPayment(ReceiptPaymentApp applRecPayObj);
	public void savePayment(Payment payment);
	public void savePaymentDetails(PaymentDetail paymentDetails);
	
	//for miscellaneousApproval
	public List<ReceiptPayment> checkDocumentNumberExist(BigDecimal documentCode, BigDecimal documentNo);
	public List<ViewVwRemittanceTransaction> checkTransactiontNumberExist(BigDecimal applicationCountry,BigDecimal companyId,BigDecimal documentCode,BigDecimal transactionYear,BigDecimal transactionNumber);
	public List<Customer> getCustomerDetails(BigDecimal customerReference);
	public void updateRecord(BigDecimal recpayPk, BigDecimal comissionAmt,
			BigDecimal chargesAmt, BigDecimal deliverChargesAmt,
			BigDecimal rateAdjust, BigDecimal otherAdj,BigDecimal netAmt, String remarks);
	
	public List<ReceiptPayment> getAllDocumentNumbers(BigDecimal documentCode,BigDecimal documentYear);
	public void saveRecords(HashMap<String, Object> saveRecords)throws AMGException;
	
	public List<Object> moveToOldEmosSystem(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo,BigDecimal oldRemitComId, BigDecimal oldRemitDocCode, BigDecimal oldRemitDocFinyr, BigDecimal oldRemitDocNo);
	
	public List<ReceiptPayment> getReceiptPaymentListById(BigDecimal receiptId);
	
	public List<RemittanceTransaction> getRemitTxnDetailsById(BigDecimal transactionId);
	
	public List<Object> moveToApproveRecordFromPaymentOldEmosSystem(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo, BigDecimal oldRemitDocFinyr, BigDecimal oldRemitDocNo) throws AMGException;
	
	public List<Object> moveToApproveRecordFromCollectionOldEmosSystem(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo, BigDecimal oldRemitDocFinyr, BigDecimal oldRemitDocNo)throws AMGException;
	
	public List<Collect> getCollectionListById(BigDecimal collectionId);
	
	public List<Payment> getPaymentListById(BigDecimal paymentId,BigDecimal documentCode);
	
public List<Document> getDocumentId(BigDecimal documentCode);
	
	public List<CompanyMaster> getCompanyCode(BigDecimal companyId);
	
	public List<PaymentMode> fetchPaymentmode(BigDecimal paymentModeId);
	
	public List<BigDecimal> getAllDocumentFinanceYearNumbers(BigDecimal documentCode,BigDecimal documentYear);
	
	public List<ViewReceiptPayment> viewReceiptPaymentForUpdate(
			BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,
			BigDecimal documentYear, BigDecimal documentNo);
	
	//public List<ViewReceiptPayment> getAllDocumentNumbersFromView(BigDecimal documentCode,BigDecimal documentYear);
	
	//public List<ViewReceiptPayment> getAllDocumentYearFromView(BigDecimal documentCode, BigDecimal documentYear);
	
	public HashMap<String, String> moveToApproveRecordFromCollectionOldEmosSystem(HashMap<String, String> inputValues)throws AMGException;
	
	public HashMap<String, String> moveToApproveRecordFromPaymentOldEmosSystem(HashMap<String, String> inputValues)throws AMGException;
	
	public BigDecimal toFetchCompanyCode(BigDecimal companyId);
	public List<UserFinancialYear> getFinanacilYearId(BigDecimal year);
	public List<CustomerInfoView> getCustomerDetailsBasedOnRef(
			BigDecimal custRef) ;
	
	public List<RemittanceTrnxViewStopMiscModel> fetchTransactionalDetailForMisc(BigDecimal appCountryId,BigDecimal companyId, BigDecimal documentCode,BigDecimal remittanceYear,BigDecimal remiitanceNo);
	
	public List<CollectDetail> getBeanCorrespondingBankList(BigDecimal docNo,BigDecimal docFinanceYear,BigDecimal docCode,BigDecimal companyId);
	
	public List<Payment> getPaymentDeatils(BigDecimal docNo,BigDecimal docFinanceYear,BigDecimal docCode,String recType,BigDecimal companyId);
	public String paymentModeDescription(String paymentCode,BigDecimal langId);
	public String bankDescription(String bankCode);
	public void deactivateRecord(BigDecimal recpayPk,String UserName);
	public List<ViewReceiptPayment> getReceiptPaymentForApproval(
			BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,
			BigDecimal documentYear, BigDecimal documentNo);
}
