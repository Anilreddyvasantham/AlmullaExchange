package com.amg.exchange.stoppayment.serviceimpl;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.amg.exchange.stoppayment.dao.IStopPaymentDao;
import com.amg.exchange.stoppayment.model.RefundInquiryView;
import com.amg.exchange.stoppayment.model.RemittanceComplaint;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.stoppayment.model.RemittanceTranxBenificiary;
import com.amg.exchange.stoppayment.model.ViewRemittanceTransaction;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;
@Service("stopPaymentServiceImpl")
@Transactional
public class StopPaymentServiceImpl<T> implements IStopPaymentService<T> {

	@Autowired
	IStopPaymentDao<T> stopPaymentDao;
	
	@Override
	@Transactional
	public List<RemittanceApplication> viewDetails(BigDecimal docNo, BigDecimal appCountryId) {
		return stopPaymentDao.viewDetails(docNo, appCountryId);
	}

	@Override
	@Transactional
	public List<RemittanceTransaction> viewDetailsTransaction(
			BigDecimal transferNo, BigDecimal appCountryId) {
		return stopPaymentDao.viewDetailsTransaction(transferNo, appCountryId);
	}

	@Override
	@Transactional
	public List<RemittanceAppBenificiary> viewDetailsBeneficiary(
			BigDecimal remiitaceAppId) {
		
		return stopPaymentDao.viewDetailsBeneficiary(remiitaceAppId);
	}

	@Override
	@Transactional
	public List<ReceiptPayment> viewDetailsPayment(BigDecimal docNo) {
		return stopPaymentDao.viewDetailsPayment(docNo);
	}

	@Override
	@Transactional
	public void saveOrUpdate(T entity) {
	 stopPaymentDao.saveOrUpdate(entity);
		
	}

	@Override
	@Transactional
	public void saveOrUpdate(BigDecimal remiTrxId, String transactionStatus,String userName) {
		stopPaymentDao.saveOrUpdate(remiTrxId, transactionStatus, userName);
		
	}
	@Override
	@Transactional
	public List<RemittanceTranxBenificiary> viewDetailsTranxBeneficiary(BigDecimal remiitaceTrxId){
		return stopPaymentDao.viewDetailsTranxBeneficiary(remiitaceTrxId);
	}
	@Override
	@Transactional
	public RemittanceTransaction viewTransactiondetailsbyDocumentNo(BigDecimal transferNo, BigDecimal countryId,BigDecimal fyear,BigDecimal companyId) {
		return stopPaymentDao.viewTransactiondetailsbyDocumentNo(transferNo,countryId,fyear,companyId);
	}

	@Override
	@Transactional
	public RemittanceComplaint viewRemittanceComplaintbyDocumentNo(BigDecimal documentNo, BigDecimal financialYear) {
		return stopPaymentDao.viewRemittanceComplaintbyDocumentNo(documentNo,financialYear);
	}



	@Override
	@Transactional
	public void updateTransactiondetailsbyTransactionId(BigDecimal remittanceTransactionId, String status, String userName) {
		stopPaymentDao.updateTransactiondetailsbyTransactionId(remittanceTransactionId,status,userName);
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.stoppayment.service.IStopPaymentService#updateCompliant(java.math.BigDecimal, int, int, java.math.BigDecimal, java.lang.String, java.lang.String)
	 */
/*	@Override
	@Transactional
	public void updateCompliant(BigDecimal docuemtnNo, String finaceYear, int documentCodeForStoppayment, BigDecimal receiptNo, String status, String userName) throws Exception {
		stopPaymentDao.updateCompliant(docuemtnNo,finaceYear,documentCodeForStoppayment,receiptNo,status,userName);
	}*/

	@Override
	@Transactional
	public BigDecimal getWUbankId(String WU) {
	
		return stopPaymentDao.getWUbankId(WU);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveAlltheDeatailsforCash(HashMap<String, Object> mapAllDetailForSave) throws Exception {
		stopPaymentDao.saveAlltheDeatailsforCash(mapAllDetailForSave);
		
	}

	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveAlltheDeatailsforCard(HashMap<String, Object> mapAllDetailForSave) throws Exception {
		stopPaymentDao.saveAlltheDeatailsforCard(mapAllDetailForSave);
		
	}

	@Override
	@Transactional
	public BigDecimal getcashRemittenceCode(String cash) {
		return stopPaymentDao.getcashRemittenceCode(cash);
	}

	@Override
	@Transactional
	public BeneficaryAccount getAccountDetails(BigDecimal beneficiaryId) {
		return stopPaymentDao.getAccountDetails(beneficiaryId);
	}

	@Override
	@Transactional
	public BeneficaryRelationship getRelationship(BigDecimal beneficiaryId) {
		return stopPaymentDao.getRelationship(beneficiaryId);
	}

	@Override
	@Transactional
	public RemittanceTxnView getRemittanceTransactionDetailsfromView(BigDecimal transferNo, BigDecimal countryId, BigDecimal fyear, BigDecimal companyId,BigDecimal documentCode) {
		return stopPaymentDao.getRemittanceTransactionDetailsfromView(transferNo,countryId,fyear,companyId,documentCode);
	}

	@Override
	@Transactional
	public CollectDetail getCollectionDetails(BigDecimal collectionDocId, BigDecimal collectionDocFinanceYear, BigDecimal collectionDocumentNo ){
		return stopPaymentDao.getCollectionDetails(collectionDocId,collectionDocFinanceYear,collectionDocumentNo);
	}
	@Override
	@Transactional
	public PaymentModeDesc getPaymentDescRec(BigDecimal paymentId , BigDecimal languageId){
		return stopPaymentDao.getPaymentDescRec(paymentId,languageId);
	}
	
	@Override
	@Transactional
	public BankMaster getArabicBankName(BigDecimal bankId) {
		return stopPaymentDao.getArabicBankName(bankId);
	}

	@Override
	@Transactional
	public List<CollectDetail> getCollectionDetail(BigDecimal collectionDocId,
			BigDecimal collectionDocFinanceYear, BigDecimal collectionDocumentNo) {
 
		return stopPaymentDao.getCollectionDetail(collectionDocId,collectionDocFinanceYear,collectionDocumentNo);
	}

	@Override
	@Transactional
	public BankMaster getBankMasterDetails(BigDecimal bankId) {
 
		return stopPaymentDao.getBankMasterDetails(bankId);
	}

	@Override
	@Transactional
	public BankBranch getBankBranchDetails(BigDecimal branchId) {
 
		return stopPaymentDao.getBankBranchDetails(branchId);
	}

	@Override
	@Transactional
	public List<RemittanceComplaint> getRemitaComplaintDetails(
			BigDecimal documentYear, BigDecimal docmentNo,
			BigDecimal documentCode, BigDecimal companyId) {
	 
		return stopPaymentDao. getRemitaComplaintDetails(documentYear,docmentNo,documentCode,companyId);
	}

	@Override
	@Transactional
	public List<RemittanceApplication> getRemittanceApplication(
			BigDecimal documentYear, BigDecimal docmentNo,
			BigDecimal documentCode, BigDecimal companyId) {
 
		return stopPaymentDao.getRemittanceApplication(documentYear,docmentNo,documentCode,companyId);
	}

	@Override
	@Transactional
	public RemittanceTxnView getRemittanceTransactionDetailsfromViewData(
			BigDecimal transferNo, BigDecimal docYear, String documentCode,BigDecimal companyId) {
	 
		return stopPaymentDao.getRemittanceTransactionDetailsfromViewData(transferNo,docYear,documentCode,companyId);
	}

	@Override
	@Transactional
	public ViewRemiitanceInfo getRemittanceTrnxDetailsFromView(
			BigDecimal transferNo, BigDecimal docYear, String documentCode,
			BigDecimal companyId) {
 
		return stopPaymentDao.getRemittanceTrnxDetailsFromView( transferNo,docYear, documentCode,companyId);
	}

	@Override
	@Transactional
	public Clob getSignatureOfRemitter(BigDecimal transferNo,
			BigDecimal docYear, String documentCode, BigDecimal companyId) {
 
		return stopPaymentDao.getSignatureOfRemitter(  transferNo, docYear,  documentCode,  companyId);
	}

	@Override
	@Transactional
	public PurposeOfRemittanceView getPurposeOfRemittanceView(BigDecimal docNumber, BigDecimal docYear,BigDecimal documentCode, BigDecimal companyId) {
		return stopPaymentDao.getPurposeOfRemittanceView(docNumber, docYear, documentCode, companyId);
	}

	@Override
	@Transactional
	public BigDecimal toFetchFinancialYear() {
		return stopPaymentDao.toFetchFinancialYear();
	}

	@Override
	@Transactional
	public List<ReceiptPayment> toFetchAllDocumentNo(BigDecimal documentCode,BigDecimal remittanceYear) {
		return stopPaymentDao.toFetchAllDocumentNo(documentCode, remittanceYear);
	}

	@Override
	@Transactional
	public List<ReceiptPayment> toFetchAllRecordBasedOnDocYearAndDocNum(BigDecimal companyId, BigDecimal documentCode, BigDecimal docFinYear,BigDecimal documentNum) {
		return stopPaymentDao.toFetchAllRecordBasedOnDocYearAndDocNum(companyId, documentCode, docFinYear, documentNum);
	}

	@Override
	@Transactional
	public String toFetchCountryBranchName(BigDecimal countryId,BigDecimal countryBranchId) {
		return stopPaymentDao.toFetchCountryBranchName(countryId, countryBranchId);
	}

	@Override
	@Transactional
	public List<RemittanceView> toFetchRecordsFromView(BigDecimal companyId,BigDecimal documentCode, BigDecimal transferFinanceYear,BigDecimal transferReference) {
		return stopPaymentDao.toFetchRecordsFromView(companyId, documentCode, transferFinanceYear, transferReference);
	}
	
	@Override
	@Transactional
	public String moveToOldSystem(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo, BigDecimal oldRemitComId,BigDecimal oldRemitDocCode,BigDecimal oldRemitDocFinyr,BigDecimal oldRemitDocNo){
		return stopPaymentDao.moveToOldSystem(applicationCountryId, companyId, documentCode, documentFinanceYr,documentNo,oldRemitComId,oldRemitDocCode,oldRemitDocFinyr,oldRemitDocNo);
	}

	@Override
	@Transactional
	public List<Collect> getCollectionListById(BigDecimal collectionId){
		return stopPaymentDao.getCollectionListById(collectionId);
	}
	
	@Override
	@Transactional
	public List<RemittanceTransaction> getRemitTxnDetailsById(BigDecimal transactionId){
		return stopPaymentDao.getRemitTxnDetailsById(transactionId);
	}
	
	@Override
	@Transactional
	public String moveToOldSystemRefundRequest(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo, BigDecimal oldRemitComId,BigDecimal oldRemitDocCode,BigDecimal oldRemitDocFinyr,BigDecimal oldRemitDocNo){
		return stopPaymentDao.moveToOldSystemRefundRequest(applicationCountryId, companyId, documentCode, documentFinanceYr,documentNo,oldRemitComId,oldRemitDocCode,oldRemitDocFinyr,oldRemitDocNo);
	}
	
	@Override
	@Transactional
	public String moveToOldSystemRefundPay(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo, BigDecimal oldRemitComId,BigDecimal oldRemitDocCode,BigDecimal oldRemitDocFinyr,BigDecimal oldRemitDocNo){
		return stopPaymentDao.moveToOldSystemRefundPay(applicationCountryId, companyId, documentCode, documentFinanceYr,documentNo,oldRemitComId,oldRemitDocCode,oldRemitDocFinyr,oldRemitDocNo);
	}
	
	@Override
	@Transactional
	public List<RemittanceTransaction> getRemitTxnDetailsFromViewById(BigDecimal transactionId){
		return stopPaymentDao.getRemitTxnDetailsFromViewById(transactionId);
	}
	
	@Override
	@Transactional
	public List<UserFinancialYear> getTransferYearList() {
		return stopPaymentDao.getTransferYearList();
	}
	
	@Override
	@Transactional
	public void updateRemittanceComplaint(String stopPaymentStatus, BigDecimal complaintId,String userName) {
		stopPaymentDao.updateRemittanceComplaint(stopPaymentStatus, complaintId,  userName);
		
	}
	
	/*@Override
	@Transactional
	public RemittanceComplaint viewRemittanceComplaint(BigDecimal documentNo) {
		return stopPaymentDao.viewRemittanceComplaint(documentNo);
	}*/
	
	@Override
	@Transactional
	public ViewRemiitanceInfo getStopPaymentTrnxDetailsFromView(BigDecimal transferNo,BigDecimal dealYear,BigDecimal companyId,BigDecimal documentId) {
		return stopPaymentDao.getStopPaymentTrnxDetailsFromView(transferNo,dealYear,companyId,documentId);
	}
	@Override
	@Transactional
	public String moveToOldSystemStopPay(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo) throws AMGException{
		return stopPaymentDao.moveToOldSystemStopPay(companyCode, documentCode, documentFinanceYr, documentNo);
	}
	
	@Override
	@Transactional
	public CompanyMaster getCompanyCode(BigDecimal companyId) {
		return stopPaymentDao.getCompanyCode(companyId);
	}
	
	@Override
	@Transactional
	public Document getDocumentId(BigDecimal documentCode) {
		return stopPaymentDao.getDocumentId(documentCode);
	}

	@Override
	@Transactional
	public void updateRemitComplaint(BigDecimal cancelDocumentYear,BigDecimal cancelDocumentNo,BigDecimal remitComplaintId) {
		stopPaymentDao.updateRemitComplaint(cancelDocumentYear, cancelDocumentNo, remitComplaintId);
	}

	@Override
	@Transactional
	public List<RefundInquiryView> fetchRefundApprovedRec(BigDecimal companyId,
			BigDecimal documentCode, BigDecimal documentFinanceYr,
			BigDecimal documentNo) {
		return stopPaymentDao.fetchRefundApprovedRec(companyId, documentCode, documentFinanceYr, documentNo);
	}

	@Override
	@Transactional
	public boolean checkTransferForStopPayment(BigDecimal companyCode,
			BigDecimal documentCode, BigDecimal documentYear,
			BigDecimal documentNo) {
		return stopPaymentDao.checkTransferForStopPayment(companyCode, documentCode, documentYear, documentNo);
	}

	@Override
	@Transactional
	public HashMap<String, Object> fetchTransferForStopPayment(BigDecimal companyCode,
			BigDecimal documentCode, BigDecimal documentYear,
			BigDecimal documentNo, String formType) throws AMGException{
		return stopPaymentDao.fetchTransferForStopPayment(companyCode, documentCode, documentYear, documentNo, formType) ;
	}

	@Override
	@Transactional
	public RemittanceTrnxViewStopMiscModel getRemitTrnxFromView(BigDecimal transferNo, BigDecimal dealYear, BigDecimal companyId,BigDecimal documentId) {
		return stopPaymentDao.getRemitTrnxFromView(transferNo, dealYear, companyId, documentId) ;
	}

	@Override
	@Transactional
	public String updateRemitFeedBackProcedure(HashMap<String,Object> inputValues) throws AMGException{
		// TODO Auto-generated method stub
		return stopPaymentDao.updateRemitFeedBackProcedure(inputValues);
	}

	@Override
	@Transactional
	public HashMap<String, String> validateRemitFeedBackProcedure(HashMap<String, Object> inputValues) throws AMGException {
		// TODO Auto-generated method stub
		return stopPaymentDao.validateRemitFeedBackProcedure(inputValues);
	}

	@Override
	@Transactional
	public String moveToOldSystemStopPayStatusModification(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr,
			BigDecimal documentNo, String canStatus, String loginUSer) throws AMGException {
		return stopPaymentDao.moveToOldSystemStopPayStatusModification(companyCode, documentCode, documentFinanceYr, documentNo, canStatus, loginUSer);
	}

	@Override
	@Transactional
	public ViewRemittanceInquiryTransaction getRemittanceTrnxInqDetailsFromView(BigDecimal transferNo, BigDecimal docYear, String documentCode,BigDecimal companyId) {
		return stopPaymentDao.getRemittanceTrnxInqDetailsFromView(transferNo, docYear, documentCode, companyId);
	}
	
	@Override
	@Transactional
	public ViewRemittanceInquiryTransaction getRemittanceTrnxInqDetailsMTcNoView(String mtcNo,BigDecimal companyId) {
		return stopPaymentDao.getRemittanceTrnxInqDetailsMTcNoView(mtcNo,companyId);
	}
	
	@Override
	@Transactional
	public List<Employee> getBranchUserList() {
		return stopPaymentDao.getBranchUserList();
	}
	
	@Override
	@Transactional
	public List<ViewRemittanceInquiryTransaction> getWesternUnionList(BigDecimal locnId,String branchUser,String mtcnNum,Date fromDt,Date toDt) {
		return stopPaymentDao.getWesternUnionList(locnId,branchUser,mtcnNum,fromDt,toDt);
	}
	
	@Override
	@Transactional
	public BigDecimal getBuyRateMin(BigDecimal appCountryId,BigDecimal countryId,BigDecimal currencyId,BigDecimal countryBranchId,BigDecimal bankId,BigDecimal serviceIndicatorId){
		return stopPaymentDao.getBuyRateMin(appCountryId, countryId, currencyId, countryBranchId, bankId, serviceIndicatorId);
	}
	
	
	public BigDecimal getServiceMasterId(BigDecimal remittanceModeId) {
		return stopPaymentDao.getServiceMasterId(remittanceModeId);
	}
	
}
