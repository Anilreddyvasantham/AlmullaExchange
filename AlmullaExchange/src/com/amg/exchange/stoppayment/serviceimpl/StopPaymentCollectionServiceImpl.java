package com.amg.exchange.stoppayment.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.cancelreissue.model.RemittanceView;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.stoppayment.dao.StopPaymentColllectionDao;
import com.amg.exchange.stoppayment.model.RemittanceComplaint;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.stoppayment.service.IStopPaymentCollectionService;
import com.amg.exchange.treasury.viewModel.CurrencyMasterView;
import com.amg.exchange.util.AMGException;

@Service("stopPaymentCollectionService")
public class StopPaymentCollectionServiceImpl implements IStopPaymentCollectionService {

	@Autowired
	StopPaymentColllectionDao stopPaymentColllectionDao;
	
	@Override
	@Transactional
	public RemittanceApplication viewBean(BigDecimal transferNo) {
		return stopPaymentColllectionDao.viewBean(transferNo);
		
	}

	@Override
	@Transactional
	public RemittanceTransaction getTransactiondetailsbyDocumentNo(Long transferNo) {
		return stopPaymentColllectionDao.getTransactiondetailsbyDocumentNo(transferNo);
	}

	@Override
	@Transactional
	public String getProductName(BigDecimal bankId, BigDecimal remittanceModeId, BigDecimal deliveryModeId, BigDecimal corespondingCountryId, BigDecimal foreignCurrencyId) {
		return stopPaymentColllectionDao.getProductName(bankId,remittanceModeId,deliveryModeId,corespondingCountryId,foreignCurrencyId);
	}
	@Override
	@Transactional
	public List<Customer> getCustomerInfo(BigDecimal customerId) {
		return stopPaymentColllectionDao. getCustomerInfo(customerId);
	}@Override
	@Transactional
	public List<RemittanceTransaction> getRemittanceTransaction(BigDecimal countryId,Integer finYear,BigDecimal documentCode,BigDecimal companyId,BigDecimal documentId){
		return stopPaymentColllectionDao.getRemittanceTransaction(countryId, finYear, documentCode, companyId, documentId);
	}
	@Override
	@Transactional
	public BigDecimal getBanKId(String WU){
		return stopPaymentColllectionDao.getBanKId(WU);
	}
	@Override
	@Transactional
	public BigDecimal getRemittanceId(String cashProduct){
		return stopPaymentColllectionDao.getRemittanceId(cashProduct);
	}
	@Override
	@Transactional
	public void updateReceiptPaymentTableData( BigDecimal receiptPaymentPk){
		stopPaymentColllectionDao.updateReceiptPaymentTableData(receiptPaymentPk);
		
	}
	
	@Override
	@Transactional
	public BigDecimal getReceiptPaymentTablePk(BigDecimal customerId, BigDecimal documentNo) {
		return stopPaymentColllectionDao.getReceiptPaymentTablePk(customerId,documentNo);
	}
	@Override
	@Transactional
	public void updateRemittanceCompliantTableData( BigDecimal receiptPaymentPk){
		stopPaymentColllectionDao.updateRemittanceCompliantTableData(receiptPaymentPk);
		
	}
	
	@Override
	@Transactional
	public BigDecimal getRemittanceCompliantPk( BigDecimal documentNo) {
		return stopPaymentColllectionDao.getRemittanceCompliantPk(documentNo);
	}
	@Override
	@Transactional
	public BigDecimal getRemittanceId(BigDecimal languageId, String cashProduct){
		return stopPaymentColllectionDao.getRemittanceId(languageId,cashProduct);
	}
	@Override
	@Transactional
	public List<CountryBranch> getRemittanceTransaction(BigDecimal countryId){
		return stopPaymentColllectionDao.getRemittanceTransaction(countryId);
	}
	@Override
	@Transactional
	public List<RemittanceTransaction> getHighValueCusotmerList(BigDecimal branchId,String isActive){
		return stopPaymentColllectionDao.getHighValueCusotmerList(branchId,isActive);
	}
	@Override
	@Transactional
	public void updateRemittanceTransaction(List<BigDecimal> remittanceTransactionId,String userName){
		stopPaymentColllectionDao.updateRemittanceTransaction(remittanceTransactionId, userName);
	}

	@Override
	@Transactional
	public RemittanceView getRemittanceTransactionFromView(
			BigDecimal countryId, Integer finYear, BigDecimal documentCode,
			BigDecimal companyId, BigDecimal documentId) {

		return stopPaymentColllectionDao.getRemittanceTransactionFromView(countryId,finYear,documentCode,companyId,documentId);
	}
	
	
	@Override
	@Transactional
	public void saveAlltheDeatailsforCash(HashMap<String, Object> mapAllDetailForSave) throws Exception {
		stopPaymentColllectionDao.saveAlltheDeatailsforCash(mapAllDetailForSave);
	}
	
	@Override
	@Transactional
	public void updateTransactiondetailsbyTransactionId(BigDecimal remittanceTransactionId, String status, String userName) {
		stopPaymentColllectionDao.updateTransactiondetailsbyTransactionId(remittanceTransactionId, status, userName);
	}
	

	@Override
	@Transactional
	public String getCompanyName(BigDecimal companyId, BigDecimal languageId){
		return stopPaymentColllectionDao.getCompanyName(companyId, languageId);
	}
	
	@Override
	@Transactional
	public void saveAlltheDeatailsforCard(HashMap<String, Object> mapAllDetailForSave) throws Exception {
		// TODO Auto-generated method stub
		
		stopPaymentColllectionDao.saveAlltheDeatailsforCard(mapAllDetailForSave);
		
	}

	@Override
	@Transactional
	public boolean checkRemittanceCancelationStatus(BigDecimal companyId,
			BigDecimal documentId, BigDecimal documentCode,
			BigDecimal documentNo, BigDecimal caneDocFinYear) {
		
		return stopPaymentColllectionDao.checkRemittanceCancelationStatus(companyId, documentId, documentCode, documentNo, caneDocFinYear);
	}

	@Override
	@Transactional
	public List<CurrencyMasterView> getCurrencyDetails(BigDecimal currencyId) {
		
		return stopPaymentColllectionDao.getCurrencyDetails(currencyId);
	}

	@Override
	@Transactional
	public List<ReceiptPayment> checkRecieptPaymentExist(BigDecimal transferYear,BigDecimal TransferrefNo) {
		return stopPaymentColllectionDao.checkRecieptPaymentExist(transferYear, TransferrefNo);
	}

	@Override
	@Transactional(rollbackFor = AMGException.class)
	public void saveOrUpDateAllValues(HashMap<String, Object> inputValues) throws AMGException{
		stopPaymentColllectionDao.saveOrUpDateAllValues(inputValues);
	}

	@Override
	@Transactional
	public BigDecimal toFetchRemitTrnxPk(BigDecimal companyId,
			BigDecimal countryId, BigDecimal documentId,
			BigDecimal documentNum, BigDecimal docFinYear) {
		return stopPaymentColllectionDao.toFetchRemitTrnxPk(companyId, countryId, documentId, documentNum, docFinYear);
	}

	@Override
	@Transactional
	public BigDecimal toFetchDocumentPk(BigDecimal documentCode) {
		return stopPaymentColllectionDao.toFetchDocumentPk(documentCode);
	}

	@Override
	@Transactional(rollbackFor = AMGException.class)
	public HashMap<String, Object> toFetchRefundDetails(HashMap<String, Object> inputParamters) throws AMGException{
		return stopPaymentColllectionDao.toFetchRefundDetails(inputParamters);
	}

	@Override
	@Transactional
	public List<RemittanceComplaint> checkRemittanceComplaintExist(BigDecimal transferYear, BigDecimal TransferrefNo) {
		return stopPaymentColllectionDao.checkRemittanceComplaintExist(transferYear, TransferrefNo);
	}
	
	@Override
	@Transactional
	public List<RemittanceComplaint> getRefundRequestExist(BigDecimal companyID,BigDecimal documentID,BigDecimal docNumber,BigDecimal docFinanceYearId,String cancelStatus) {
		return stopPaymentColllectionDao.getRefundRequestExist(companyID, documentID, docNumber, docFinanceYearId, cancelStatus);
	}
	
	@Override
	@Transactional
	public BigDecimal roundingTotalNetAmountbyFunction(BigDecimal applcountryId, BigDecimal totalNetAmount, String roundstatus) throws AMGException {
		// TODO Auto-generated method stub
		return stopPaymentColllectionDao.roundingTotalNetAmountbyFunction(applcountryId, totalNetAmount, roundstatus);
	}

	
	@Override
	@Transactional
	public void saveOrUpdate(BigDecimal companyID,BigDecimal documentID,BigDecimal docNumber,BigDecimal docFinanceYearId,String cancelStatus,BigDecimal remittanceYear,String username){
		stopPaymentColllectionDao.saveOrUpdate(companyID,documentID,docNumber,docFinanceYearId,cancelStatus,remittanceYear,username);
	}
}
