package com.amg.exchange.wuh2h.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.TempCollectDetail;
import com.amg.exchange.foreigncurrency.model.TempCollection;
import com.amg.exchange.foreigncurrency.model.TempForeignCurrencyAdjust;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.wuh2h.bean.ReceiveReceiptView;
import com.amg.exchange.wuh2h.bean.WUStateCityModel;
import com.amg.exchange.wuh2h.bean.WUTermsAndConditions;
import com.amg.exchange.wuh2h.dao.IWUH2HDao;
import com.amg.exchange.wuh2h.model.DynamicFileds;
import com.amg.exchange.wuh2h.model.DynamicPurposeCode;
import com.amg.exchange.wuh2h.model.ViewWUSendTransaction;
import com.amg.exchange.wuh2h.model.WUCorridorCountry;
import com.amg.exchange.wuh2h.model.WUCorridorCurrency;
import com.amg.exchange.wuh2h.model.WURemittanceApplicationView;
import com.amg.exchange.wuh2h.model.WUReportTermsAndConditions;
import com.amg.exchange.wuh2h.service.IWUH2HService;

@Service
@Transactional
public class WUH2HServiceImpl implements IWUH2HService, Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Autowired
	IWUH2HDao wuh2hDao;
	
	@Override
	@Transactional
	public void saveCollection(Collect collection, List<CollectDetail> collectionDetailList){
		wuh2hDao.saveCollection(collection,collectionDetailList);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BigDecimal saveTempCollectionwithDetailsandTempCurrencyAdjust(TempCollection tempCollection, List<TempCollectDetail> tempDetailsList, List<TempForeignCurrencyAdjust> tempAdjustList) throws Exception {
		return wuh2hDao.saveTempCollectionwithDetailsandTempCurrencyAdjust(tempCollection,tempDetailsList,tempAdjustList);
	}
	
	@Override
	@Transactional
	public String getNameDescription(BigDecimal id, BigDecimal languageId, String option){
		return wuh2hDao.getNameDescription(id,languageId,option);
	}
	
	@Override
	@Transactional(rollbackFor = AMGException.class)
	public void saveAllApplTransaction(HashMap<String, Object> mapAllDetailPersonalRemittanceApplSave) throws AMGException{
		wuh2hDao.saveAllApplTransaction(mapAllDetailPersonalRemittanceApplSave);
	}
	
	@Override
	@Transactional
	public  List<CurrencyMaster> getWUh2HCurrencyList(BigDecimal applicationCurrencyId, BigDecimal customerCurrencyId){
		return wuh2hDao.getWUh2HCurrencyList(applicationCurrencyId,customerCurrencyId);
	}

	@Override
	public BigDecimal getRemitModeForCash() {
		// TODO Auto-generated method stub
		return wuh2hDao.getRemitModeForCash();
	}
	
	@Override
	@Transactional
	public List<ReceiveReceiptView> getReceiveReceiptData(BigDecimal collectionDocumentNo, BigDecimal collectionDocumentFinanceYear,BigDecimal collectionDocumentCode,BigDecimal companyId,BigDecimal applicatioCountryId){
		return wuh2hDao.getReceiveReceiptData(collectionDocumentNo,collectionDocumentFinanceYear,collectionDocumentCode,companyId,applicatioCountryId);
	}
	
	@Override
	@Transactional
	public List<WUStateCityModel> getWUStateList(String countryISO){
		return wuh2hDao.getWUStateList(countryISO);
	}
	
	@Override
	@Transactional	
	public List<WUStateCityModel> getWUCityList(String countryISO, String stateCode){
		return wuh2hDao.getWUCityList(countryISO, stateCode);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveCurrAdjustReceiptAndPayment(
			List<ForeignCurrencyAdjust> foreignCurrencyList,
			ReceiptPayment receiptPaymnet, List<Payment> payment)
			throws Exception {
		
		wuh2hDao.saveCurrAdjustReceiptAndPayment(foreignCurrencyList, receiptPaymnet, payment);
	}
	
	@Override
	@Transactional	
	public List<WUTermsAndConditions> getWUTermsAndConditions(){
		return wuh2hDao.getWUTermsAndConditions();
	}
	
	@Override
	@Transactional
	public String getWUEnrollReference(BigDecimal customerId) {
		return wuh2hDao.getWUEnrollReference(customerId);
		
	}

	@Override
	@Transactional
	public BigDecimal getCountryIDFromCode(String countryCode) {
		
		return wuh2hDao.getCountryIDFromCode(countryCode);
	}

	@Override
	@Transactional
	public String getCountryName(BigDecimal languageId, BigDecimal countryId) {
		
		return wuh2hDao.getCountryName(languageId, countryId);
	}
	@Override
	@Transactional
	public int updateWUEnrollCardNo(BigDecimal customerId, String wucardno) {
		
		return wuh2hDao.updateWUEnrollCardNo(customerId, wucardno);
	}
	
	@Override
	@Transactional
	public List<WUCorridorCountry> getWUDynamicFieldCountry(){
		return wuh2hDao.getWUDynamicFieldCountry();
	}
	
	@Override
	@Transactional
	public List<DynamicFileds> getWUDynamicFields(String isoCountry){
		return wuh2hDao.getWUDynamicFields(isoCountry);
	}
	
	@Override
	@Transactional
	public List<DynamicPurposeCode> getWUDynamicPurposeCode(String isoCountry){
		return wuh2hDao.getWUDynamicPurposeCode(isoCountry);
	}
	
	@Override
	@Transactional
	public List<WUCorridorCurrency> getWUCorridorCurrency(String isoCountry){
		return wuh2hDao.getWUCorridorCurrency(isoCountry);
	}
	
	@Override
	@Transactional
	public String getNextReferenceNo(){
		return wuh2hDao.getNextReferenceNo();
	}

	@Override
	@Transactional
	public List<RemittanceApplication> getRemittanceAppBasedonMtcNo(String mtcNo) {
		
		return wuh2hDao.getRemittanceAppBasedonMtcNo(mtcNo);
	}

	@Override
	@Transactional
	public void updateWuSendMoneyStoreSts(String mtcNo,String newpoints) {
		
		wuh2hDao.updateWuSendMoneyStoreSts(mtcNo,newpoints);
	}
	
	@Override
	@Transactional
	public List<ViewWUSendTransaction> getWUTxnInquiryList(BigDecimal customerNo,BigDecimal companyId,BigDecimal applicationCountryId, BigDecimal documentCode, BigDecimal documentFinanceYear) {
		return wuh2hDao.getWUTxnInquiryList(customerNo,companyId,applicationCountryId,documentCode,documentFinanceYear);
	}
	
	@Override
	@Transactional
	public List<ReceiveReceiptView> getWUReceiveTxnInquiryList(BigDecimal customerNo,BigDecimal companyId,BigDecimal applicationCountryId, BigDecimal documentCode, BigDecimal documentFinanceYear){
		return wuh2hDao.getWUReceiveTxnInquiryList(customerNo,companyId,applicationCountryId,documentCode,documentFinanceYear);
	}
	
	@Override
	@Transactional(rollbackFor = {AMGException.class,TransactionException.class},propagation=Propagation.REQUIRES_NEW)
	public HashMap<String, Object> saveAllRemittanceTransaction(HashMap<String, Object> mapAllDetailPersonalRemittanceSave) throws AMGException {
		return wuh2hDao.saveAllRemittanceTransaction(mapAllDetailPersonalRemittanceSave);
	}
	
	@Override
	@Transactional
	public List<WURemittanceApplicationView> getRecordsForWURemittanceReceiptReport(BigDecimal documentNo,BigDecimal financeYear,String documentCode,BigDecimal applicationCountryId, BigDecimal companyId){
		return wuh2hDao.getRecordsForWURemittanceReceiptReport(documentNo, financeYear, documentCode, applicationCountryId, companyId);
	}
	
	@Override
	@Transactional
	public List<ViewWUSendTransaction> getWUSendTxnInquiryListByTerminal(BigDecimal companyId,BigDecimal applicationCountryId, BigDecimal documentCode, BigDecimal documentFinanceYear,String terminalAddress) {
		return wuh2hDao.getWUSendTxnInquiryListByTerminal(companyId,applicationCountryId,documentCode,documentFinanceYear,terminalAddress);
	}
	
	@Override
	@Transactional
	public List<ReceiveReceiptView> getWUReceiveTxnInquiryListByTerminal(BigDecimal companyId,BigDecimal applicationCountryId, BigDecimal documentCode, BigDecimal documentFinanceYear,String terminalAddress){
		return wuh2hDao.getWUReceiveTxnInquiryListByTerminal(companyId,applicationCountryId,documentCode,documentFinanceYear,terminalAddress);
	}
	
	@Override
	@Transactional
	public List<WUReportTermsAndConditions> getWUReportTermsAndConditions(){
		return wuh2hDao.getWUReportTermsAndConditions();
	}
	
	@Override
	@Transactional
	public List<ReceiptPayment> getReceiveMtcNo(String mtcNo){
		return wuh2hDao.getReceiveMtcNo(mtcNo);
	}
	
	@Override
	@Transactional
	public String saveReceivetoOldByProc(BigDecimal countryId, BigDecimal companyId, BigDecimal documentId, BigDecimal financialYear,BigDecimal documentno){
		return wuh2hDao.saveReceivetoOldByProc(countryId, companyId, documentId, financialYear,documentno);
	}

	@Override
	@Transactional
	public List<RemittanceTransaction> getRemittanceTrnxBasedonMtcNo(
			String mtcNo) {
		return wuh2hDao.getRemittanceTrnxBasedonMtcNo(mtcNo);
	}
}
