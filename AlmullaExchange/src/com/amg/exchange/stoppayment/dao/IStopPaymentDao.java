package com.amg.exchange.stoppayment.dao;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.cancelreissue.model.RemittanceTrnxViewStopMiscModel;
import com.amg.exchange.cancelreissue.model.RemittanceView;
import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.cancelreissue.model.ViewRemittanceInquiryTransaction;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.PurposeOfRemittanceView;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.RemittanceTxnView;
import com.amg.exchange.stoppayment.model.RefundInquiryView;
import com.amg.exchange.stoppayment.model.RemittanceComplaint;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.stoppayment.model.RemittanceTranxBenificiary;
import com.amg.exchange.stoppayment.model.ViewRemittanceTransaction;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;

public interface IStopPaymentDao<T> {
	
	public List<RemittanceApplication> viewDetails(BigDecimal docNo, BigDecimal appCountryId);

	public List<RemittanceTransaction> viewDetailsTransaction(BigDecimal transferNo, BigDecimal appCountryId);

	public List<RemittanceAppBenificiary> viewDetailsBeneficiary(BigDecimal remiitaceAppId);

	public List<ReceiptPayment> viewDetailsPayment(BigDecimal docNo);

	public void saveOrUpdate(T entity);

	public void saveOrUpdate(BigDecimal remiTrxId, String transactionStatus, String userName);

	public List<RemittanceTranxBenificiary> viewDetailsTranxBeneficiary(BigDecimal remiitaceTrxId);

	public RemittanceTransaction viewTransactiondetailsbyDocumentNo(BigDecimal transferNo, BigDecimal countryId, BigDecimal fyear, BigDecimal companyId);

	public RemittanceComplaint viewRemittanceComplaintbyDocumentNo(BigDecimal documentNo, BigDecimal financialYear);

	public void updateTransactiondetailsbyTransactionId(BigDecimal remittanceTransactionId, String status, String userName);

	//public void updateCompliant(BigDecimal docuemtnNo, String finaceYear, int documentCodeForStoppayment, BigDecimal receiptNo, String status, String userName) throws Exception;

	public BigDecimal getWUbankId(String wU);

	public void saveAlltheDeatailsforCash(HashMap<String, Object> mapAllDetailForSave) throws Exception;

	public void saveAlltheDeatailsforCard(HashMap<String, Object> mapAllDetailForSave) throws Exception;

	public BigDecimal getcashRemittenceCode(String cash);

	public BeneficaryAccount getAccountDetails(BigDecimal beneficiaryId);

	public BeneficaryRelationship getRelationship(BigDecimal beneficiaryId);

	public RemittanceTxnView getRemittanceTransactionDetailsfromView(BigDecimal transferNo, BigDecimal countryId, BigDecimal fyear, BigDecimal companyId,BigDecimal documentCode);

	public CollectDetail getCollectionDetails(BigDecimal collectionDocId, BigDecimal collectionDocFinanceYear, BigDecimal collectionDocumentNo);

	public PaymentModeDesc getPaymentDescRec(BigDecimal paymentId , BigDecimal languageId);

	public BankMaster getArabicBankName(BigDecimal bankId);
	
	public List<CollectDetail> getCollectionDetail(BigDecimal collectionDocId,BigDecimal collectionDocFinanceYear, BigDecimal collectionDocumentNo);

	public BankMaster getBankMasterDetails(BigDecimal bankId);
	
	public BankBranch getBankBranchDetails(BigDecimal  branchId);
	
	public List<RemittanceComplaint> getRemitaComplaintDetails(BigDecimal documentYear, BigDecimal docmentNo,BigDecimal documentCode, BigDecimal companyId);
	
	public List<RemittanceApplication> getRemittanceApplication(BigDecimal documentYear, BigDecimal docmentNo,BigDecimal documentCode, BigDecimal companyId);
	
	public RemittanceTxnView getRemittanceTransactionDetailsfromViewData(BigDecimal transferNo, BigDecimal docYear, String documentCode,BigDecimal companyId);
	
	public ViewRemiitanceInfo getRemittanceTrnxDetailsFromView(BigDecimal transferNo, BigDecimal docYear, String documentCode,BigDecimal companyId);
	
	public Clob getSignatureOfRemitter(BigDecimal transferNo,BigDecimal docYear, String documentCode, BigDecimal companyId);

	public PurposeOfRemittanceView getPurposeOfRemittanceView(BigDecimal docNumber, BigDecimal docYear,BigDecimal documentCode, BigDecimal companyId);

	public BigDecimal toFetchFinancialYear();

	public List<ReceiptPayment> toFetchAllDocumentNo(BigDecimal documentCode,BigDecimal remittanceYear);

	public List<ReceiptPayment> toFetchAllRecordBasedOnDocYearAndDocNum(BigDecimal companyId, BigDecimal documentCode,BigDecimal docFinYear, BigDecimal documentNum);

	public String toFetchCountryBranchName(BigDecimal countryId,BigDecimal countryBranchId);

	public List<RemittanceView> toFetchRecordsFromView(BigDecimal companyId,BigDecimal documentCode, BigDecimal transferFinanceYear,BigDecimal transferReference);

	public String moveToOldSystem(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo, BigDecimal oldRemitComId,BigDecimal oldRemitDocCode,BigDecimal oldRemitDocFinyr,BigDecimal oldRemitDocNo) ;

	public List<Collect> getCollectionListById(BigDecimal collectionId) ;

	public List<RemittanceTransaction> getRemitTxnDetailsById(BigDecimal transactionId) ;

	public String moveToOldSystemRefundRequest(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo, BigDecimal oldRemitComId,BigDecimal oldRemitDocCode,BigDecimal oldRemitDocFinyr,BigDecimal oldRemitDocNo) ;

	public String moveToOldSystemRefundPay(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo, BigDecimal oldRemitComId,BigDecimal oldRemitDocCode,BigDecimal oldRemitDocFinyr,BigDecimal oldRemitDocNo) ;

	public List<RemittanceTransaction> getRemitTxnDetailsFromViewById(BigDecimal transactionId);

	public List<UserFinancialYear> getTransferYearList();

	public void updateRemittanceComplaint(String stopPaymentStatus, BigDecimal complaintId,String userName);

	//public RemittanceComplaint viewRemittanceComplaint(BigDecimal documentNo);

	public ViewRemiitanceInfo getStopPaymentTrnxDetailsFromView(BigDecimal transferNo,BigDecimal dealYear,BigDecimal companyId,BigDecimal documentId);

	public String moveToOldSystemStopPay(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo) throws AMGException;
	
	//Added by Rabil on 01 Feb 2017
	public String moveToOldSystemStopPayStatusModification(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo,String canStatus,String loginUSer) throws AMGException;

	

	public CompanyMaster getCompanyCode(BigDecimal companyId);

	public Document getDocumentId(BigDecimal documentCode);
	
	public void updateRemitComplaint(BigDecimal cancelDocumentYear,BigDecimal cancelDocumentNo,BigDecimal remitComplaintId);
	
	public List<RefundInquiryView> fetchRefundApprovedRec(BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo);
	
	public boolean checkTransferForStopPayment(BigDecimal companyCode,BigDecimal documentCode, BigDecimal documentYear, BigDecimal documentNo);
	
	public HashMap<String, Object> fetchTransferForStopPayment(BigDecimal companyCode,BigDecimal documentCode, BigDecimal documentYear, BigDecimal documentNo, String formType) throws AMGException;
	
	public RemittanceTrnxViewStopMiscModel getRemitTrnxFromView(BigDecimal transferNo,BigDecimal dealYear,BigDecimal companyId,BigDecimal documentId);

	/** Remit Feed Back Update added by Rabil on 24 01 2017 */
	public String updateRemitFeedBackProcedure(HashMap<String,Object> inputValues) throws AMGException;
	
	public HashMap<String,String> validateRemitFeedBackProcedure(HashMap<String,Object> inputValues) throws AMGException;
	
	public ViewRemittanceInquiryTransaction getRemittanceTrnxInqDetailsFromView(BigDecimal transferNo, BigDecimal docYear, String documentCode,BigDecimal companyId);
	
	public ViewRemittanceInquiryTransaction getRemittanceTrnxInqDetailsMTcNoView(String mtcNo,BigDecimal companyId);
	
	public List<Employee> getBranchUserList();
	
	public List<ViewRemittanceInquiryTransaction> getWesternUnionList(BigDecimal locnId,String branchUser,String mtcnNum,Date fromDt,Date toDt);
	
	public BigDecimal getBuyRateMin(BigDecimal appCountryId,BigDecimal countryId,BigDecimal currencyId,BigDecimal countryBranchId,BigDecimal bankId,BigDecimal serviceIndicatorId);
	
	public BigDecimal getServiceMasterId(BigDecimal remittanceModeId);
}
