package com.amg.exchange.miscellaneous.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.amg.exchange.miscellaneous.dao.IMiscellaneousReceiptPaymentDao;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.miscellaneous.model.PaymentDetail;
import com.amg.exchange.miscellaneous.model.ViewVwRemittanceTransaction;
import com.amg.exchange.miscellaneous.service.IMiscellaneousReceiptPaymentService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerInfoView;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;

@SuppressWarnings("serial")
@Service("miscellaneousReceiptPaymentServiceImpl")
public class MiscellaneousReceiptPaymentServiceImpl<T> implements IMiscellaneousReceiptPaymentService<T>, Serializable {

	@Autowired
	IMiscellaneousReceiptPaymentDao<T> imiscellaneousDao;
	@Override
	@Transactional
	public List<ViewVwRemittanceTransaction> fetchTransactionalDetails(BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode, BigDecimal remittanceYear,BigDecimal remiitanceNo) {
		return imiscellaneousDao.fetchTransactionalDetails(appCountryId, companyId, documentCode, remittanceYear, remiitanceNo);
	}

	@Override
	@Transactional
	public List<ReceiptPayment> fetchReceiptPayment(BigDecimal appCountryId,BigDecimal companyId,BigDecimal documentYear,BigDecimal documentNo,String documentCode) {
		return imiscellaneousDao.fetchReceiptPayment(appCountryId, companyId, documentYear, documentNo,documentCode);
	}
@Transactional
	public List<Customer> fetchCustomer(BigDecimal customerId){
	return imiscellaneousDao.fetchCustomer(customerId);
	}
	 

	@Override
	@Transactional
	public List<ViewVwRemittanceTransaction> getAllTransactionList(BigDecimal customerReference) {
		 
		return imiscellaneousDao.getAllTransactionList(customerReference);
	}

	@Override
	@Transactional
	public List<ForeignCurrencyAdjust> getCollectionDetailsFromCurrencyAdjust(
			BigDecimal collectiondocId, BigDecimal collectDocYear,
			BigDecimal collectDocNo) {
	 
		return  imiscellaneousDao.getCollectionDetailsFromCurrencyAdjust(collectiondocId,collectDocYear,collectDocNo);
	}

	@Override
	@Transactional
	public  String getDenominationName(
			BigDecimal denominationId) {
	 
		return imiscellaneousDao.getDenominationName(denominationId);
	}

	@Override
	@Transactional
	public void saveOrUpdate(ReceiptPayment receiptPaymentObj) {
		imiscellaneousDao.saveOrUpdate(receiptPaymentObj);
		
	}

	@Override
	@Transactional
	public BigDecimal getBankBranchIdBasedOnName(String branchName) {
	 
		return imiscellaneousDao.getBankBranchIdBasedOnName(branchName);
	}

	@Override
	@Transactional
	public BigDecimal getSourceIdBasedOnName(String sourceName,BigDecimal languageId) {
	 
		return imiscellaneousDao.getSourceIdBasedOnName(sourceName,languageId);
	}

	@Override
	@Transactional
	public void saveOrUpdateApplReceiptPayment(ReceiptPaymentApp applRecPayObj) {
		imiscellaneousDao.saveOrUpdateApplReceiptPayment(applRecPayObj);
		
	}

	@Override
	@Transactional
	public void savePayment(Payment payment) {
		imiscellaneousDao.savePayment(payment);
		
	}

	@Override
	@Transactional
	public void savePaymentDetails(PaymentDetail paymentDetails) {
		imiscellaneousDao.savePaymentDetails(paymentDetails);
		
	}

	@Override
	@Transactional
	public List<ViewRemiitanceInfo> fetchTransactionalDetail(
			BigDecimal appCountryId, BigDecimal companyId,
			BigDecimal documentCode, BigDecimal remittanceYear,
			BigDecimal remiitanceNo) {
 		return imiscellaneousDao.fetchTransactionalDetail(appCountryId, companyId, documentCode, remittanceYear, remiitanceNo);
	}

	@Override
	@Transactional
	public List<ViewRemiitanceInfo> getAllTransactionsList(
			BigDecimal customerReference) {
		 	return imiscellaneousDao.getAllTransactionsList(customerReference);
	}

	
	//for miscellaneousApproval
	
		@Override
		@Transactional
		public List<ReceiptPayment> checkDocumentNumberExist(BigDecimal documentCode, BigDecimal documentNo){
			return imiscellaneousDao.checkDocumentNumberExist(documentCode,documentNo);
		}

		
		@Override
		@Transactional
		public List<ViewVwRemittanceTransaction> checkTransactiontNumberExist(BigDecimal applicationCountry,BigDecimal companyId,BigDecimal documentCode,BigDecimal transactionYear,BigDecimal transactionNumber){
			return imiscellaneousDao.checkTransactiontNumberExist(applicationCountry,companyId,documentCode,transactionYear,transactionNumber);
		}

		@Override
		@Transactional
		public List<Customer> getCustomerDetails(BigDecimal customerReference){
			return imiscellaneousDao.getCustomerDetails(customerReference);
		}

		@Override
		@Transactional
		public List<ReceiptPayment> fetchReceiptPaymentForUpdate(
				BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,
				BigDecimal  DocumentYear, BigDecimal DocumentNo) {
			 
			return imiscellaneousDao.fetchReceiptPaymentForUpdate(appCountryId,companyId,documentCode,DocumentYear,DocumentNo);
		}

		@Override
		@Transactional
		public List<ReceiptPayment> fetchReceiptPaymentUnApprovedRecords(
				BigDecimal appCountryId, BigDecimal companyId,
				BigDecimal documentCode, BigDecimal DocumentYear) {
			 
			return imiscellaneousDao.fetchReceiptPaymentUnApprovedRecords(appCountryId,companyId,documentCode,DocumentYear);
		}

		@Override
		@Transactional
		public String getTelephoneNumber(BigDecimal customerRef) {
 
			return imiscellaneousDao.getTelephoneNumber(customerRef);
		}

		@Override
		@Transactional
		public void updateRecord(BigDecimal recpayPk, BigDecimal comissionAmt,
				BigDecimal chargesAmt, BigDecimal deliverChargesAmt,
				BigDecimal rateAdjust, BigDecimal otherAdj,BigDecimal netAmt ,String remarks) {
			imiscellaneousDao.updateRecord(recpayPk,comissionAmt,chargesAmt,deliverChargesAmt,rateAdjust,otherAdj,netAmt,remarks);
			
		}
		

		@Override
		@Transactional
		public List<ReceiptPayment> getAllDocumentNumbers(BigDecimal documentCode,BigDecimal documentYear){
			return imiscellaneousDao.getAllDocumentNumbers(documentCode,documentYear);
		}
		
		
		@Override
		@Transactional(rollbackFor = AMGException.class)
		public void saveRecords(HashMap<String, Object> saveRecords)throws AMGException{
		  imiscellaneousDao.saveRecords(saveRecords);
		}
		
		@Override
		@Transactional
		public List<Object> moveToOldEmosSystem(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo, BigDecimal oldRemitComId, BigDecimal oldRemitDocCode, BigDecimal oldRemitDocFinyr, BigDecimal oldRemitDocNo) {
			return imiscellaneousDao.moveToOldEmosSystem(applicationCountryId, companyId, documentCode, documentFinanceYr, documentNo, oldRemitComId, oldRemitDocCode, oldRemitDocFinyr, oldRemitDocNo);
		}
		
		@Override
		@Transactional
		public List<ReceiptPayment> getReceiptPaymentListById(BigDecimal receiptId) {
			return imiscellaneousDao.getReceiptPaymentListById(receiptId);
		}
		
		@Override
		@Transactional
		public List<RemittanceTransaction> getRemitTxnDetailsById(BigDecimal transactionId) {
			return imiscellaneousDao.getRemitTxnDetailsById(transactionId);
		}
		
		@Override
		@Transactional
		public List<Object> moveToApproveRecordFromCollectionOldEmosSystem(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo, BigDecimal oldRemitDocFinyr, BigDecimal oldRemitDocNo)throws AMGException {
			return imiscellaneousDao.moveToApproveRecordFromCollectionOldEmosSystem(applicationCountryId, companyId, documentCode, documentFinanceYr, documentNo, oldRemitDocFinyr, oldRemitDocNo);
			
		}
		
		@Override
		@Transactional
		public List<Object> moveToApproveRecordFromPaymentOldEmosSystem(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo, BigDecimal oldRemitDocFinyr, BigDecimal oldRemitDocNo)throws AMGException {
			return imiscellaneousDao.moveToApproveRecordFromPaymentOldEmosSystem(applicationCountryId, companyId, documentCode, documentFinanceYr, documentNo, oldRemitDocFinyr, oldRemitDocNo);
			
		}
		
		@Override
		@Transactional
		public List<Collect> getCollectionListById(BigDecimal collectionId) {
			return imiscellaneousDao.getCollectionListById(collectionId);
		}
		
		@Override
		@Transactional
		public List<Payment> getPaymentListById(BigDecimal paymentId,BigDecimal documentCode) {
			return imiscellaneousDao.getPaymentListById(paymentId,documentCode);
		}
		

		@Override
		@Transactional
		public List<Document> getDocumentId(BigDecimal documentCode) {
			return imiscellaneousDao.getDocumentId(documentCode);
		}		
		@Override
		@Transactional
		public List<CompanyMaster> getCompanyCode(BigDecimal companyId) {
			return imiscellaneousDao.getCompanyCode(companyId);
		}
		
		@Override
		@Transactional
		public List<PaymentMode> fetchPaymentmode(BigDecimal paymentModeId) {
			return imiscellaneousDao.fetchPaymentmode(paymentModeId);
		}
		
		@Override
		@Transactional
		public List<BigDecimal> getAllDocumentFinanceYearNumbers(BigDecimal documentCode, BigDecimal documentYear) {
			return imiscellaneousDao.getAllDocumentFinanceYearNumbers(documentCode,documentYear);
		}
		
		@Override
		@Transactional
		public List<ViewReceiptPayment> viewReceiptPaymentForUpdate(BigDecimal appCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentYear, BigDecimal documentNo) {
			return imiscellaneousDao.viewReceiptPaymentForUpdate(appCountryId, companyId, documentCode, documentYear, documentNo);
		}
		
		/*@Override
		@Transactional
		public List<ViewReceiptPayment> getAllDocumentNumbersFromView(BigDecimal documentCode, BigDecimal documentYear) {
			return imiscellaneousDao.getAllDocumentNumbersFromView(documentCode, documentYear);
					
			
		}*/

		/*@Override
		@Transactional
		public List<ViewReceiptPayment> getAllDocumentYearFromView(
				BigDecimal documentCode, BigDecimal documentYear) {
			// TODO Auto-generated method stub
			return imiscellaneousDao.getAllDocumentYearFromView(documentCode, documentYear);
		}*/

		@Override
		@Transactional
		public HashMap<String, String> moveToApproveRecordFromCollectionOldEmosSystem(HashMap<String, String> inputValues)throws AMGException {
			return imiscellaneousDao.moveToApproveRecordFromCollectionOldEmosSystem(inputValues);
		}

		@Override
		@Transactional
		public HashMap<String, String> moveToApproveRecordFromPaymentOldEmosSystem(HashMap<String, String> inputValues) throws AMGException {
			return imiscellaneousDao.moveToApproveRecordFromPaymentOldEmosSystem(inputValues);
		}

		@Override
		@Transactional
		public BigDecimal toFetchCompanyCode(BigDecimal companyId) {
			return imiscellaneousDao.toFetchCompanyCode(companyId);
		}

		@Override
		@Transactional
		public List<UserFinancialYear> getFinanacilYearId(BigDecimal year) {
			return imiscellaneousDao.getFinanacilYearId(year);
		}

		@Override
		@Transactional
		public List<CustomerInfoView> getCustomerDetailsBasedOnRef(
				BigDecimal custRef) {
			return imiscellaneousDao.getCustomerDetailsBasedOnRef(custRef);
		}

		@Override
		@Transactional
		public List<RemittanceTrnxViewStopMiscModel> fetchTransactionalDetailForMisc(
				BigDecimal appCountryId, BigDecimal companyId,
				BigDecimal documentCode, BigDecimal remittanceYear,
				BigDecimal remiitanceNo) {
			return imiscellaneousDao.fetchTransactionalDetailForMisc(appCountryId, companyId, documentCode, remittanceYear, remiitanceNo);
		}
		
		@Override
		@Transactional
		public List<CollectDetail> getBeanCorrespondingBankList(BigDecimal docNo,BigDecimal docFinanceYear,BigDecimal docCode,BigDecimal companyId){
			
			return imiscellaneousDao.getBeanCorrespondingBankList(docNo,docFinanceYear,docCode,companyId);
		}
		
		@Override
		@Transactional
		public List<Payment> getPaymentDeatils(BigDecimal docNo,BigDecimal docFinanceYear,BigDecimal docCode,String recType,BigDecimal companyId){
			
			return imiscellaneousDao.getPaymentDeatils(docNo,docFinanceYear,docCode,recType,companyId);
		}
		
		@Override
		@Transactional
		public String paymentModeDescription(String paymentCode,BigDecimal langId){
			
			return imiscellaneousDao.paymentModeDescription(paymentCode,langId);
		}

		@Override
		@Transactional
		public String bankDescription(String bankCode){
			
			return imiscellaneousDao.bankDescription(bankCode);
		}

		@Override
		@Transactional
		public void deactivateRecord(BigDecimal recpayPk, String UserName) {
			 imiscellaneousDao.deactivateRecord(recpayPk, UserName);
			
		}

		@Override
		@Transactional
		public List<ViewReceiptPayment> getReceiptPaymentForApproval(
				BigDecimal appCountryId, BigDecimal companyId,
				BigDecimal documentCode, BigDecimal documentYear,
				BigDecimal documentNo) {
			
			return imiscellaneousDao.getReceiptPaymentForApproval(appCountryId, companyId, documentCode, documentYear, documentNo);
		}
}
